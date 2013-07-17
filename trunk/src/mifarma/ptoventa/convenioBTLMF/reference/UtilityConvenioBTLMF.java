package mifarma.ptoventa.convenioBTLMF.reference;

import java.awt.Frame;
import java.awt.event.KeyEvent;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.print.PrintService;

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbarcode.encode.InvalidAtributeException;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaPRNUtility;
import mifarma.common.FarmaPrintService;
import mifarma.common.FarmaPrintServiceTicket;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.DocumentRendererConsejo;
import mifarma.ptoventa.caja.reference.UtilityBarCode;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.convenioBTLMF.DlgListaConveniosBTLMF;
import mifarma.ptoventa.convenioBTLMF.DlgMsjeImpresionCompBTLMF;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

public class UtilityConvenioBTLMF {

	private static final Log log = LogFactory.getLog(UtilityConvenioBTLMF.class);

    public UtilityConvenioBTLMF() {

    }

    public static boolean indDatoConvenio(String pCodigoConvenio,
                                          JDialog pDialogo, Object pObjeto) {
        boolean resul = false;
        String indConv = "";
        String indProdConv = "";

        try {
            indConv = DBConvenioBTLMF.pideDatoConvenio(pCodigoConvenio);
            System.out.println("INDICADOR PIDE DATO CONV = " + indConv);
            if (indConv.equalsIgnoreCase("S"))
            {
                 resul = true;
            }
            else
            if (indConv.equalsIgnoreCase("T"))
            {
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
            }
            else
            if (indConv.equalsIgnoreCase("P"))
            {
            	  VariablesConvenioBTLMF.vAceptar = true;
            	  resul = false;
            	  //FarmaUtility.showMessage(pDialogo, "El convenio no tiene datos registrados. e\n", pObjeto);
            }

        } catch (SQLException sql) {
            sql.printStackTrace();
            FarmaUtility.showMessage(pDialogo,
                                     "Error en buscar si debe mostrase datos convenios\n" +
                    sql.getMessage(), pObjeto);
            resul = true;
        }
        return resul;

    }

    public static List listaDatosConvenio(String pCodConvenio,
                                          JDialog pDialogo, Object pObjeto) {
        List lista = null;
        try {
            lista = DBConvenioBTLMF.listaDatosConvenio(pCodConvenio);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            FarmaUtility.showMessage(pDialogo,
                                     "Error al obtener datos del convenio!!!",
                                     pObjeto);
        }
        System.out.println("ListaDatConv" + lista);
        return lista;
    }

    public static Map obtienePantallaMensaje(String pNroResolucion,
                                             String pPosicion,
                                             JDialog pDialogo,
                                             Object pObjeto) {
        Map map = null;
        try {
            map =
DBConvenioBTLMF.obtienePantallaMensaje(pNroResolucion, pPosicion);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            FarmaUtility.showMessage(pDialogo,
                                     "Error al obtener datos de la pantalla!!!",
                                     pObjeto);
        }
        System.out.println("Map Pantalla:" + map);
        return map;
    }

    public static String obtieneDocVerificacion(String pCodConvenio,
                                                String pFlgRetencion,
                                                String pNomBenificiario,
                                                JDialog pDialogo,
                                                Object pObjeto) {
        String msg = "";

        try {
            msg =
DBConvenioBTLMF.ObtieneDocVerificacion(pCodConvenio, pFlgRetencion,
                                       pNomBenificiario);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            FarmaUtility.showMessage(pDialogo,
                                     "Error al obtener datos de Documentos de verificación!!!",
                                     "");
        }

        System.out.println("msg:" + msg);
        return msg;
    }


    public static void listaMensaje(ArrayList lista, String pCodConvenio,
                                    String pFlgRetencion, JDialog pDialogo,
                                    Object pObjeto) {

        try {
            DBConvenioBTLMF.listaMensajes(lista, pCodConvenio, pFlgRetencion);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            FarmaUtility.showMessage(pDialogo,
                                     "Error al obtener datos de Documentos de verificación!!!",
                                     "");
        }


    }


    public static Map obtieneBenificiario(String pCodConvenio, String pDni,
                                          JDialog pDialogo) {
        Map benif = null;

        try {
            benif = DBConvenioBTLMF.obtieneBenificiario(pCodConvenio, pDni);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            FarmaUtility.showMessage(pDialogo, "Error al buscar Benificiario",
                                     "");
        }


        System.out.println("benif:" + benif);
        return benif;
    }

    public static String existeBenificiario(String pCodConvenio, String pDni,
            JDialog pDialogo) {
		String benif = null;

		try {
		benif = DBConvenioBTLMF.existeBenificiario(pCodConvenio, pDni);

		} catch (SQLException sqlException) {
		sqlException.printStackTrace();
		FarmaUtility.showMessage(pDialogo, "Error al buscar Benificiario",
		       "");
		}


		System.out.println("benif:" + benif);
		return benif;
    }




    public static Map obtenerTarjeta(String pCodBarra, JDialog pDialogo) {
        Map benif = null;

        try {
            benif = DBConvenioBTLMF.obtenerTarjeta(pCodBarra);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            FarmaUtility.showMessage(pDialogo,
                                     "Error al buscar Benificiario con Tarjeta",
                                     "");
        }


        System.out.println("msg:" + benif.get(ConstantsConvenioBTLMF.COL_DNI));
        return benif;
    }


    public static Map obtenerCliente(String pCodCliente, JDialog pDialogo) {
        Map cliente = null;

        try {
            cliente = DBConvenioBTLMF.obtenerCliente(pCodCliente);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            FarmaUtility.showMessage(pDialogo, "Error al obtener cliente", "");
        }

        System.out.println("msg:" +
                           cliente.get(ConstantsConvenioBTLMF.COL_DNI));
        return cliente;
    }


    public static Map obtieneDiagnostico(String pCodConvenio, String pCODCIE10,
                                         JDialog pDialogo) {
        Map benif = null;

        try {
            benif = DBConvenioBTLMF.obtieneDiagnostico(pCODCIE10);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            FarmaUtility.showMessage(pDialogo, "Error al buscar Diagnòstico",
                                     "");
        }

        System.out.println("msg:" +
                           benif.get(ConstantsConvenioBTLMF.COL_COD_CIE_10));
        return benif;
    }


    public static Map obtieneMedico(String pCodConvenio, String pCodMedico,
                                    JDialog pDialogo) {
        Map medico = null;

        try {
            medico = DBConvenioBTLMF.obtieneMedico(pCodConvenio, pCodMedico);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            FarmaUtility.showMessage(pDialogo, "Error al buscar Médico", "");
        }

        System.out.println("msg:" +
                           medico.get(ConstantsConvenioBTLMF.COL_NUM_CMP));
        return medico;
    }

    public static Map obtenerConvenio(String pCodConvenio, JDialog pDialogo) {
        Map medico = null;

        try {
           medico = DBConvenioBTLMF.obtenerConvenio(pCodConvenio);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            FarmaUtility.showMessage(pDialogo, "Error al obtener convenio"+sqlException.getMessage(),
                                     null);


        }

System.out.println("msg:" +
                               medico.get(ConstantsConvenioBTLMF.COL_COD_MEDICO));
        return medico;

    }

    public static void filtraDescripcion(KeyEvent evento,
                                         FarmaTableModel tableModelo,
                                         ArrayList listaDatos,
                                         JTextField jtext, int columna) {

        ArrayList listaConvenio = filtraListaDato(evento, listaDatos, jtext, columna);

        copiaTablaModelo(tableModelo, listaConvenio, false);

    }

    public static void filtraDescripcion2(KeyEvent evento,
								          FarmaTableModel tableModelo,
								          ArrayList listaDatos,
								          JTextField jtext, int columna) {

			ArrayList listaConvenio = filtraListaDato2(evento, listaDatos, jtext, columna);

			copiaTablaModelo(tableModelo, listaConvenio, false);

  }



    private static void copiaTablaModelo(FarmaTableModel pTableModel,
                                         ArrayList lista, boolean pWithCheck) {
        System.out.println("<<<<<<<<<<<<<Metdo: copiaTablaModelo >>>>>>>>> " +
                           lista);

        if (pTableModel != null)
        {

            pTableModel.clearTable();

            ArrayList myArray = null;
            pTableModel.clearTable();

            for (int i = 0; i < lista.size(); i++)
            {
                String[] arg = (String[])lista.get(i);


                if (arg.length > 0)
                {
                    myArray = new ArrayList();
                    for (int y = 0; y < arg.length; y++)
                    {
                        myArray.add(arg[y]);
                    }
                    pTableModel.insertRow(myArray);
                }


            }

        }
    }

    private static ArrayList filtraListaDato(KeyEvent e, ArrayList listaTodo,
                                             JTextField pTextoDeBusqueda,
                                             int pColumna) {
        System.out.println("<<<<<<<<<<<<Metodo: filtraListaDato  >>>>>>>>>>>>>>>" +
                           pTextoDeBusqueda.getText().trim());
        System.out.println("<<<<<<<<<<<<Tamano::::  >>>>>>>>>>>>>>>" +
                           listaTodo.size());

        ArrayList lista = new ArrayList();


        if ((e.getKeyChar() != KeyEvent.CHAR_UNDEFINED) &&
            ((e.getKeyCode() != KeyEvent.VK_ESCAPE))) {


            String vFindText = pTextoDeBusqueda.getText().toUpperCase();

            String vCodigo = "";
            String vDescrip = "";
            String vDescripcion = "";


            for (int k = 0; k < listaTodo.size(); k++) {


                vCodigo = ((String[])listaTodo.get(k))[0];
                vDescrip = ((String[])listaTodo.get(k))[1];
                vDescripcion = vDescrip;

                System.out.println("SvCodigo:" + vCodigo);
                System.out.println("SvDescripcion:" + vDescripcion);


                if (vDescrip.length() >= vFindText.length()) {
                    vDescrip = vDescrip.substring(0, vFindText.length());
                    if (vFindText.trim().equalsIgnoreCase(vDescrip.trim())) {
                        String[] dato = (String[])listaTodo.get(k);
                        lista.add(dato);
                    }
                }
            }
        }

        return lista;

    }

    private static ArrayList filtraListaDato2(KeyEvent e, ArrayList listaTodo,
            JTextField pTextoDeBusqueda,
            int pColumna) {
							System.out.println("<<<<<<<<<<<<Metodo: filtraListaDato2  >>>>>>>>>>>>>>>" +
							pTextoDeBusqueda.getText().trim());
							System.out.println("<<<<<<<<<<<<Tamano::::  >>>>>>>>>>>>>>>" +
							listaTodo.size());

							ArrayList lista = new ArrayList();


							if ((e.getKeyChar() != KeyEvent.CHAR_UNDEFINED) &&
							((e.getKeyCode() != KeyEvent.VK_ESCAPE))) {


							String vFindText = pTextoDeBusqueda.getText().toUpperCase();


							String vCodigo = "";
							String vDescrip = "";
							String vDescripcion = "";


							for (int k = 0; k < listaTodo.size(); k++)
							{


								vCodigo = ((String[])listaTodo.get(k))[0];
								vDescrip = ((String[])listaTodo.get(k))[1];
								vDescripcion = vDescrip;

								//System.out.println("SvCodigo:" + vCodigo);
								//System.out.println("SvDescripcion:" + vDescripcion);


					            if(vDescrip.toUpperCase().indexOf(vFindText.toUpperCase())!=-1){


					            	String[] dato = (String[])listaTodo.get(k);

									lista.add(dato);
					            }
//								if (vDescrip.length() >= vFindText.length()) {
//								vDescrip = vDescrip.substring(0, vFindText.length());
//								if (vFindText.trim().equalsIgnoreCase(vDescrip.trim()))
//								 {
//									String[] dato = (String[])listaTodo.get(k);
//									lista.add(dato);
//								 }
//								}
							}
						}

			return lista;

  }


    public static boolean esTarjetaConvenio(String dato) {

        System.out.println("<<<<<<<metodo:esTarjeta>>>>>>>>>>");
        boolean retorno = false;
        String numero = "002";
        if (dato.trim().length() > 13) {
            String subCodigo = dato.trim().substring(2, 5);

            System.out.println("SubCodigo::" + subCodigo);

            if (numero.equals(subCodigo)) {
                retorno = true;
            }
        }

        return retorno;
    }


    public static boolean existeTarjeta(String dato, JDialog dialog) {
        System.out.println("<<<<<<<<<<<<Metodo: existeTarjeta>>>>>>>>>>>>");
        Map tarjeta = null;
        boolean retorno = false;
        tarjeta = UtilityConvenioBTLMF.obtenerTarjeta(dato.trim(), dialog);
        if (tarjeta.get(ConstantsConvenioBTLMF.COL_COD_BARRA) != null) {
            retorno = true;
            VariablesConvenioBTLMF.vCodCliente =
                    (String)tarjeta.get(ConstantsConvenioBTLMF.COL_COD_CLIENTE);
            VariablesConvenioBTLMF.vCodConvenioAux =
                    (String)tarjeta.get(ConstantsConvenioBTLMF.COL_COD_CONVENIO);
            System.out.println("vCreacionCliente:::>" +
                               VariablesConvenioBTLMF.vCreacionCliente);
        }

        return retorno;
    }

    public static boolean existeCliente(String pCodCliente, JDialog dialog) {
        System.out.println("<<<<<<<<<<<<Metodo: existeCliente>>>>>>>>>>>>");
        Map benif = null;
        boolean retorno = false;
        benif = UtilityConvenioBTLMF.obtenerCliente(pCodCliente, dialog);

        if (benif.get(ConstantsConvenioBTLMF.COL_COD_CLIENTE) != null) {
            retorno = true;
            VariablesConvenioBTLMF.vDni =
                    (String)benif.get(ConstantsConvenioBTLMF.COL_DNI);
            VariablesConvenioBTLMF.vNombre =
                    (String)benif.get(ConstantsConvenioBTLMF.COL_DES_NOM_CLIENTE);

            VariablesConvenioBTLMF.vDescripcion =    VariablesConvenioBTLMF.vNombre;
            log.debug("NombreBeneficiario:"+VariablesConvenioBTLMF.vDescripcion);

            VariablesConvenioBTLMF.vApellidoPat =
                    (String)benif.get(ConstantsConvenioBTLMF.COL_DES_APE_CLIENTE);

            VariablesConvenioBTLMF.vLineaCredito =
                (String)benif.get(ConstantsConvenioBTLMF.COL_LCREDITO);

            VariablesConvenioBTLMF.vEstado =(String)benif.get(ConstantsConvenioBTLMF.COL_ESTADO);


            String numPoliza     = (String)benif.get(ConstantsConvenioBTLMF.COL_NUM_POLIZA);
            String numPlan 	    = (String)benif.get(ConstantsConvenioBTLMF.COL_NUM_PLAN);
            String codAsegurado  = (String)benif.get(ConstantsConvenioBTLMF.COL_COD_ASEGURADO);
            String numItem       = (String)benif.get(ConstantsConvenioBTLMF.COL_NUM_IEM);
            String prt           = (String)benif.get(ConstantsConvenioBTLMF.COL_PRT);
            String numContrado   = (String)benif.get(ConstantsConvenioBTLMF.COL_NUM_CONTRATO);
            String tipoAsegurado = (String)benif.get(ConstantsConvenioBTLMF.COL_TIPO_ASEGURADO);

            VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_NUM_POLIZA,numPoliza);
            VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_NUM_PLAN,numPlan);
            VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_COD_ASEGURADO,codAsegurado);
            VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_NUM_IEM,numItem);
            VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_NOMB_CLIENTE,VariablesConvenioBTLMF.vNombre);
            VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_PRT,prt);
            VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_NUM_CONTRATO,numContrado);
            VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_TIPO_ASEGURADO,tipoAsegurado);


        }

        return retorno;
    }


    public static boolean existeConvenio(String pCodConvenio, JDialog dialog) {
        System.out.println("<<<<<<<<<<<<Metodo: existeConvenio>>>>>>>>>>>>");
        Map convenio = null;
        boolean retorno = false;
        convenio = UtilityConvenioBTLMF.obtenerConvenio(pCodConvenio, dialog);

        if (convenio.get(ConstantsConvenioBTLMF.COL_COD_CONVENIO) != null) {
            retorno = true;

            VariablesConvenioBTLMF.vCodConvenio =
                    (String)convenio.get(ConstantsConvenioBTLMF.COL_COD_CONVENIO);
            VariablesConvenioBTLMF.vNomConvenio =
                    (String)convenio.get(ConstantsConvenioBTLMF.COL_DES_CONVENIO);
            VariablesConvenioBTLMF.vCodConvenioRel =
                    (String)convenio.get(ConstantsConvenioBTLMF.COL_COD_CONVENIO_REL);
            VariablesConvenioBTLMF.vFlgCreacionCliente =
                    (String)convenio.get(ConstantsConvenioBTLMF.COL_FLG_CREACION_CLIENTE);
            VariablesConvenioBTLMF.vFlgTipoConvenio =
                    (String)convenio.get(ConstantsConvenioBTLMF.COL_FLG_TIPO_CONVENIO);


        }

        System.out.println("vCodConvenio=" +
                           VariablesConvenioBTLMF.vCodConvenio);
        System.out.println("vNomConvenio=" +
                           VariablesConvenioBTLMF.vNomConvenio);
        System.out.println("vCodConvenioRel=" +
                           VariablesConvenioBTLMF.vCodConvenioRel);
        System.out.println("vFlgTipoConvenio=" +
                           VariablesConvenioBTLMF.vFlgTipoConvenio);
        System.out.println("vFlgCreacionCliente=" +
                           VariablesConvenioBTLMF.vFlgCreacionCliente);


        return retorno;
    }


    /**
     * Imprimir Mensaje
     * @author Fredy Ramirez
     * @since  09/11/2011
     */
    public static

    void imprimirMensaje(String pDni, JDialog pDialogo, Object pObject) {
        try {
            String vMensaje = DBConvenioBTLMF.imprimirMensaje(pDni);
            imprimirMensaje(vMensaje, VariablesPtoVenta.vImpresoraActual,
                            VariablesPtoVenta.vTipoImpTermicaxIp);
            FarmaUtility.showMessage(pDialogo,
                                     "No existe Beneficiario para este convenio.", pObject);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            FarmaUtility.showMessage(pDialogo, "Error al imprimir el mensaje",
                                     pObject);
        }
    }

    /**
     * metodo encargado de imprimirMensaje
     * @param pConsejos
     * @param pImpresora
     * @param pTipoImprConsejo
     */
    private static void imprimirMensaje(String pConsejos,
                                        PrintService pImpresora,
                                        String pTipoImprConsejo) {
        DocumentRendererConsejo dr = new DocumentRendererConsejo(pImpresora);
        JEditorPane editor = new JEditorPane();

        try {
            // Marcamos el editor para que use HTML
            editor.setContentType("text/html");
            editor.setText(pConsejos);
            dr.print(editor, pTipoImprConsejo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * metodo encargado de imprimirMensaje
     * @param pConsejos
     * @param pImpresora
     * @param pTipoImprConsejo
     */
    private static void imprimirPedido(String pConsejos,
                                       PrintService pImpresora,
                                       String pTipoImprConsejo,
                                       String pCodigoBarraConv,
                                       String pFlgCodigoBarra) {
        DocumentRendererConsejo dr = new DocumentRendererConsejo(pImpresora);
        JEditorPane editor = new JEditorPane();
        int cantIntentosLectura = 10;

        try {
            // Se crea la imagen
             createImageCode(pCodigoBarraConv, cantIntentosLectura);
            // Marcamos el editor para que use HTML
            editor.setContentType("text/html");
            editor.setText(pConsejos);
            dr.print(editor, pTipoImprConsejo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createImageCode(String pNameImage,
                                        int cantIntentoLectura) throws InvalidAtributeException {
        UtilityBarCode uBCode = new UtilityBarCode();
        if (pNameImage != null) {
            if (pNameImage.trim().length() > 0)
                //uBCode.generarBarcodeCode39(pNameImage);
                uBCode.generarBarcode128(pNameImage, cantIntentoLectura);
        }
    }

    /**
     * Imprimir Pedido
     * @author Fredy Ramirez
     * @since  09/11/2011
     */
    private static void imprimirVoucher(JDialog pDialogo, Object pObject,
                           String pNroPedidoVta, String pCodConvenio) {
        try {
            System.out.println("<<<<<<<<<<<<<<<<<Metodo : imprimirPedidoVta>>>>>>>>><");
            System.out.println("pNroPedidoVta :" + pNroPedidoVta);

            Map convenio =
                (Map)DBConvenioBTLMF.obtenerConvenioXPedido(pNroPedidoVta);
            String vCodigoBarra = DBConvenioBTLMF.obtieneCodigoBarraConv();
            String vFlgCodigoBarra = (String)convenio.get("FLG_COD_BARRA");
            String vCodTipoConvenio = (String)convenio.get("COD_TIPO_CONVENIO");


            System.out.println("vCodigoBarra :" + vCodigoBarra);
            System.out.println("vFlgCodigoBarra :" + vFlgCodigoBarra);
            Map vMensaje = null;

	            vMensaje =  (Map)DBConvenioBTLMF.imprimirVoucher(pNroPedidoVta, vCodigoBarra);

	            String msgUno = (String)vMensaje.get("MESAJEHTML_UNO");
	            String msgVtaUno = (String)vMensaje.get("MESAJEHTML_VTA_UNO");

	            String msgDos = (String)vMensaje.get("MESAJEHTML_DOS");
	            String msgTres = (String)vMensaje.get("MESAJEHTML_TRES");
	            String msgCuatro = (String)vMensaje.get("MESAJEHTML_CUATRO");
	            String msgVtaDos = (String)vMensaje.get("MESAJEHTML_VTA_DOS");


	            System.out.println("msgUno   :" + msgUno);
	            System.out.println("msgVtaUno   :" + msgVtaUno);
	            System.out.println("msgDos   :" + msgDos);
	            System.out.println("msgTres  :" + msgTres);
	            System.out.println("msgCuatro:" + msgCuatro);
	            System.out.println("msgVtaDos:" + msgVtaDos);

	            System.out.println("VariablesPtoVenta.vImpresoraActual:" +  VariablesPtoVenta.vImpresoraActual);

	          if ((msgUno+msgVtaUno+msgDos+msgTres+msgCuatro+msgVtaDos).trim().length() >0 )
	          {
	             imprimirPedido(msgUno + msgVtaUno + msgDos + msgTres + msgCuatro + msgVtaDos,
	                           VariablesPtoVenta.vImpresoraActual,
	                           VariablesPtoVenta.vTipoImpTermicaxIp, vCodigoBarra,
	                           vFlgCodigoBarra);

	             FarmaUtility.showMessage(pDialogo,
	                                     "Se imprimio correctamente el voucher",
	                                     pObject);
	          }


        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            FarmaUtility.showMessage(pDialogo, "Error al imprimir", pObject);
        }
    }


    public static String consultarSaldCreditoBenif(Object pDialogo) {

    	log.debug("Metodo: consultarSaldCreditoBenif");
    	String resp = "N";
        String montoCosumo = "";
        double montoConsumo = 0;
        double LineCredito = 0;
        double montoSaldo = 0;
        try {
        	montoConsumo = DBConvenioBTLMF.obtieneComsumoBenif(FarmaConstants.INDICADOR_S);

        	System.out.println("montoCosumo>>>>>>>>>>>>>>>>>>>><"+montoConsumo);
            //montoConsumo =  FarmaUtility.getDecimalNumber(montoCosumo);
        	LineCredito  =  FarmaUtility.getDecimalNumber(VariablesConvenioBTLMF.vLineaCredito);
        	montoSaldo   =  LineCredito - montoConsumo;

        	VariablesConvenioBTLMF.vMontoSaldo = FarmaUtility.formatNumber(montoSaldo);

	        	log.debug("LCré:S/."+FarmaUtility.formatNumber(LineCredito));
	        	log.debug("Sald:S/."+FarmaUtility.formatNumber(montoSaldo));
	        	log.debug("Cons:S/."+FarmaUtility.formatNumber(montoConsumo));

            VariablesConvenioBTLMF.vDatoLCredSaldConsumo  = "LCrédito:S/. "+FarmaUtility.formatNumber(LineCredito)+
                                                            "    Sald:S/. "+FarmaUtility.formatNumber(montoSaldo)+
                                                            "    Cons:S/. "+FarmaUtility.formatNumber(montoConsumo);



        	log.debug("VariablesConvenioBTLMF.vDatoLCredSaldConsumo:"+VariablesConvenioBTLMF.vDatoLCredSaldConsumo);






        } catch (SQLException sqlException) {
            sqlException.printStackTrace();

            if (pDialogo instanceof JDialog) {

                FarmaUtility.showMessage((JDialog)pDialogo,
                                         sqlException.getMessage(), null);

            } else {
                FarmaUtility.showMessage((JFrame)pDialogo,
                                         sqlException.getMessage(), null);
            }

        }
        return resp;
    }


    public static boolean esActivoConvenioBTLMF(JDialog pDialogo,
                                                Object pObjeto) {
        boolean resul = false;
        String esActivoConv = "";
        try {
            esActivoConv = DBConvenioBTLMF.esActivoConvenioBTLMF();
            System.out.println("esActivoConv " + esActivoConv);
            if (esActivoConv.equalsIgnoreCase("S")) {
                resul = true;
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
            FarmaUtility.showMessage(pDialogo,
                                     "Error al obtener de la base de datos el estado convenio BTLMF" +
                                     sql.getMessage(), pObjeto);
            resul = true;
        }
        return resul;

    }


    public static String obtieneFormaPago(JDialog pDialogo,
            Object pObjeto,String pCodFormaPago) {
			boolean resul = false;
			String descripcion = "";
			try {
				descripcion = DBConvenioBTLMF.obtieneFormaPago(pCodFormaPago);

			} catch (SQLException sql) {
			sql.printStackTrace();
			FarmaUtility.showMessage(pDialogo,
			 "Error al obtener de la base de datos el estado convenio BTLMF" +
			 sql.getMessage(), pObjeto);
			resul = true;
			}
			return descripcion;

    }

    public static double obtieneMontoCredito(JDialog pDialogo,
            Object pObjeto,Double monto,String nroPedido,String codConvenio)
    {
			double montoCredito = 0;
			try
			{
				montoCredito = DBConvenioBTLMF.obtieneMontoCredito(monto, nroPedido,codConvenio);

			}
			catch (SQLException sql)
			{
			  sql.printStackTrace();
			  FarmaUtility.showMessage(pDialogo,
			  "Error al obtener de la base de el monto credito convenio BTLMF" +
			  sql.getMessage(), pObjeto);
			}
			return montoCredito;

    }
    public static void Busca_Estado_ProdConv() {
        long tmpIni, tmpFin;

        tmpIni = System.currentTimeMillis();
        int pos  = -1;
        System.out.println("VariablesVentas.vCod_Prod:" +VariablesVentas.vCod_Prod);

        if(VariablesConvenioBTLMF.vDatosPreciosConv != null && VariablesConvenioBTLMF.vDatosPreciosConv.size() > 0)
        {
	         pos =
	            busqueda_recursiva_bin(VariablesVentas.vCod_Prod, 0, VariablesConvenioBTLMF.vDatosPreciosConv.size());

	        tmpFin = System.currentTimeMillis();
	        System.out.println("Tiempo Recursivo  : " + (tmpFin - tmpIni) +
	                           " milisegundos");
	        System.out.println("pos  : " + pos);
	        if (pos == -1)
	        {
	            VariablesVentas.vEstadoProdConvenio = "E";
	        } else
	        {

	            VariablesVentas.vEstadoProdConvenio =  FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.vDatosPreciosConv,
	                                                   pos,
	                                                   2).toString().trim();
	        }
        }
        else
        {
        	    VariablesVentas.vEstadoProdConvenio  = "P";
        }


        System.out.println("VariablesVentas.vEstadoProdConvenio:" +VariablesVentas.vEstadoProdConvenio);
    }


    public static String Conv_Buscar_Precio() {

        long tmpIni, tmpFin;

        tmpIni = System.currentTimeMillis();

        int pos =
            busqueda_recursiva_bin(VariablesVentas.vCod_Prod, 0, VariablesConvenioBTLMF.vDatosPreciosConv.size());

        tmpFin = System.currentTimeMillis();


        System.out.println("VariablesVentas.vCod_Prod:" + VariablesVentas.vCod_Prod);
        System.out.println("Tamaño  VariablesConvenioBTLMF.vDatosPreciosConv:" + VariablesConvenioBTLMF.vDatosPreciosConv.size());

        System.out.println("Tiempo Recursivo  : " + (tmpFin - tmpIni) +
                           " milisegundos");

        System.out.println("pos  : " + pos);

        if (pos == -1) {
            VariablesVentas.vEstadoProdConvenio = "E";
            return VariablesVentas.vVal_Prec_Pub;
        }
        VariablesVentas.vEstadoProdConvenio =
                FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.vDatosPreciosConv,
                                                    pos, 2).toString().trim();
        /*String estado = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.vDatosPreciosConv,pos,5).toString().trim();

            if (estado == "I")
                 return FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.vDatosPreciosConv,pos, 3).toString().trim();
            else
                 return FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.vDatosPreciosConv,pos, 4).toString().trim();*/

        return FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.vDatosPreciosConv,
                                                   pos, 1).toString().trim();
    }

    public static int busqueda_recursiva_bin(String cod_prod, int i, int j) {
        int medio = 0;
        System.out.println("ciclo_recursivo");

        System.out.println("VariablesConvenioBTLMF.vDatosPreciosConv:"+VariablesConvenioBTLMF.vDatosPreciosConv.size());

        String cod_prod_buscar = "";
        if (i > j)
            return -1;
        medio = (i + j) / 2;
        cod_prod_buscar =
                FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.vDatosPreciosConv,
                                                    medio,
                                                    0).toString().trim();
        System.out.println("cod_prod_buscar:"+cod_prod_buscar);
        System.out.println("cod_prod:"+cod_prod);
        if(cod_prod_buscar.trim().equals(""))
        {
        	return -1;
        }
        else
        if (Integer.parseInt(cod_prod_buscar) < Integer.parseInt(cod_prod))
            return busqueda_recursiva_bin(cod_prod, medio + 1, j);
        else if (Integer.parseInt(cod_prod_buscar) >
                 Integer.parseInt(cod_prod))
            return busqueda_recursiva_bin(cod_prod, i, medio - 1);
        else
            return medio;


        // VariablesConvenioBTLMF.vDatosPreciosConv
        /*if (posi == 0) {
             return VariablesVentas.vVal_Prec_Pub;
         } else {

             if (cod_prod ==
                 (FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.vDatosPreciosConv,
                                                      posi,
                                                      2)).toString().trim()) {
                 return (FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.vDatosPreciosConv,posi,5).toString().trim() == "I") ?
                        FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.vDatosPreciosConv,
                                                            posi, 3).trim() :
                        FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.vDatosPreciosConv,
                                                            posi, 4).trim();

             } else {
                 return busqueda_recursiva(cod_prod, posi - 1);
             }
         }*/
        //return  busqueda_recursiva(String cod_prod , int posi-1)
    }



    public static void procesoImpresionComprobante(JDialog pJDialog, Object pObjectFocus)
    {
        long tmpT1,tmpT2;
        long tmpInicio,tmpFinal;
        log.debug("******PROCESO IMPRESION COMPROBANTES DEL CONVENIO********");
        tmpInicio = System.currentTimeMillis();

      try{

    	UtilityCaja.obtieneInfoCajero(VariablesCaja.vSecMovCaja);
        //cambiando el estado de pedido al estado C -- que es estado IMPRESO y COBRADO
        tmpT1 = System.currentTimeMillis();
          //JMIRANDA 23/07/09 posee Throws SQLException
        UtilityCaja.actualizaEstadoPedido(VariablesCaja.vNumPedVta, ConstantsCaja.ESTADO_COBRADO);

        boolean solicitaDatos =      UtilityConvenioBTLMF.indDatoConvenio(VariablesConvenioBTLMF.vCodConvenio,null,null);
        if(solicitaDatos && !listaDatosConvenioAdic(pJDialog, pObjectFocus))
        {
		  FarmaUtility.liberarTransaccion();
		  FarmaUtility.showMessage(pJDialog, "No se pudo determinar los datos adicionales del convenio. Verifique!!!.", pObjectFocus);
		  return;
		}


        log.debug("Imprimiendo comprobantes ... ");
        tmpT1 = System.currentTimeMillis();
        String fechaCreacionComp = "";
        String RefTipComp   = "";


        if (obtieneCompPago(pJDialog, "", null))
         {

        	for (int j = 0 ; j < VariablesConvenioBTLMF.vArray_ListaComprobante.size(); j++)
        	{

        	   VariablesConvenioBTLMF.vNumCompPago        = ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(0)).trim();
        	   VariablesConvenioBTLMF.vSecCompPago        = ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(1)).trim();
        	   VariablesConvenioBTLMF.vTipoCompPago       = ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(2)).trim();
        	   VariablesConvenioBTLMF.vValIgvCompPago     = ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(3)).trim();
        	   VariablesConvenioBTLMF.vValNetoCompPago    = ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(4)).trim();
        	   VariablesConvenioBTLMF.vValCopagoCompPago  = ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(5)).trim();
        	   VariablesConvenioBTLMF.vValIgvCompCoPago   = ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(6)).trim();
        	   VariablesConvenioBTLMF.vNumCompPagoRef     = ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(7)).trim();
        	   VariablesConvenioBTLMF.vTipClienConvenio   = ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(8)).trim();
        	   VariablesConvenioBTLMF.vFlgImprDatAdic     = ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(9)).trim();
        	   VariablesConvenioBTLMF.vCodTipoConvenio    = ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(10)).trim();
        	                        fechaCreacionComp     = ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(11)).trim();
                                        RefTipComp            = ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(12)).trim();
                   VariablesConvenioBTLMF.vValRedondeoCompPago= ((String)((ArrayList)VariablesConvenioBTLMF.vArray_ListaComprobante.get(j)).get(13)).trim();

        	      log.debug("VariablesConvenioBTLMF.vNumCompPago      :  "+VariablesConvenioBTLMF.vNumCompPago);
        	      log.debug("VariablesConvenioBTLMF.vSecCompPago      :  "+VariablesConvenioBTLMF.vSecCompPago);
        	      log.debug("VariablesConvenioBTLMF.vTipoCompPago     :  "+VariablesConvenioBTLMF.vTipoCompPago);
        	      log.debug("VariablesConvenioBTLMF.vValIgvCompPago   :  "+VariablesConvenioBTLMF.vValIgvCompPago);
        	      log.debug("VariablesConvenioBTLMF.vValNetoCompPago  :  "+VariablesConvenioBTLMF.vValNetoCompPago);
        	      log.debug("VariablesConvenioBTLMF.vValCopagoCompPago:  "+VariablesConvenioBTLMF.vValCopagoCompPago);
        	      log.debug("VariablesConvenioBTLMF.vValIgvCompCoPago :  "+VariablesConvenioBTLMF.vValIgvCompCoPago);
        	      log.debug("VariablesConvenioBTLMF.vNumCompPagoRef   :  "+VariablesConvenioBTLMF.vNumCompPagoRef);
        	      log.debug("VariablesConvenioBTLMF.vTipClienConvenio :  "+VariablesConvenioBTLMF.vTipClienConvenio);
        	      log.debug("VariablesConvenioBTLMF.vFlgImprDatAdic   :  "+VariablesConvenioBTLMF.vFlgImprDatAdic);
        	      log.debug("VariablesConvenioBTLMF.vCodTipoConvenio  :  "+VariablesConvenioBTLMF.vCodTipoConvenio);
        	      log.debug("fechaCreacionComp                        :  "+fechaCreacionComp);
                      log.debug("RefTipComp                               :  "+RefTipComp);
                      log.debug("VariablesConvenioBTLMF.vValRedondeoCompPago  :  "+VariablesConvenioBTLMF.vValRedondeoCompPago);

         	       UtilityCaja.actualizaComprobanteImpreso(VariablesConvenioBTLMF.vSecCompPago,VariablesConvenioBTLMF.vTipoCompPago,  VariablesConvenioBTLMF.vNumCompPago);


		           //JMIRANDA 23/07/09 Posee try-catch interno. Envia Error via Email
		           if(!obtieneDetalleComp(pJDialog,VariablesConvenioBTLMF.vSecCompPago,VariablesConvenioBTLMF.vTipoCompPago,VariablesConvenioBTLMF.vTipClienConvenio, pObjectFocus))
		           {
		           	   FarmaUtility.liberarTransaccion();
		               FarmaUtility.showMessage(pJDialog,"No se pudo obtener el detalle del comprobante a imprimir. Verifique!!!",pObjectFocus);
		               return;
		           }

		           log.debug("VariablesConvenioBTLMF.vSecCompPago : " + VariablesConvenioBTLMF.vSecCompPago);
		              //JMIRANDA 23/07/09 Posee try-catch interno. Envia Error via Email
		           if(!UtilityCaja.obtieneTotalesComprobante(pJDialog, VariablesConvenioBTLMF.vSecCompPago, pObjectFocus))
		           {
				  	    FarmaUtility.liberarTransaccion();
				  	    FarmaUtility.showMessage(pJDialog, "No se pudo determinar los Totales del Comprobante. Verifique!!!.", pObjectFocus);
				  	    return;
				   }



		           tmpT1 = System.currentTimeMillis();
		           //JMIRANDA 23/07/09 Posee try-catch interno. Envia Error via Email
		           //Comentado//VariablesCaja.vRutaImpresora = obtieneRutaImpresora(secImprLocal.trim());
		           tmpT2 = System.currentTimeMillis();
		           log.debug("Tiempo 9: Obtiene Ruta Impresora:"+(tmpT2 - tmpT1)+" milisegundos");
		           tmpT1 = System.currentTimeMillis();

		            //JMIRANDA 23/07/09 Posee try-catch interno. Envia Error via Email
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
					                                              fechaCreacionComp,
		                                                                      RefTipComp,
                                                                                      VariablesConvenioBTLMF.vValRedondeoCompPago
		                                                        );
		           tmpT2 = System.currentTimeMillis();
		           log.debug("Tiempo 10: Imprime Comprobante:"+(tmpT2 - tmpT1)+" milisegundos");

		          }

           }

          FarmaUtility.aceptarTransaccion();
          log.debug("FIN imprimiendo comprobantes ... ");
          tmpT2 = System.currentTimeMillis();
          log.debug("Tiempo 11: Fin de Impresion de Comprobantes:"+(tmpT2 - tmpT1)+" milisegundos");

          tmpFinal = System.currentTimeMillis();
          if(VariablesCaja.vEstadoSinComprobanteImpreso.equals("N"))
          {
              log.debug("T18-Tiempo Final de Metodo de Impresion: Obtiene unidades Camp.xCliente localmente:"+(tmpFinal-tmpInicio)+" milisegundos");
	          FarmaUtility.showMessage(pJDialog,"Pedido Cobrado con éxito. \n" +
	                          "Comprobantes Impresos con éxito "+"",pObjectFocus);
          }
          else
          {
        	  log.debug("T18-Tiempo Final de Metodo de Impresion: Obtiene unidades Camp.xCliente localmente:"+(tmpFinal-tmpInicio)+" milisegundos");
              FarmaUtility.showMessage(pJDialog,"Pedido Cobrado con éxito." +
                               "\nCOMPROBANTES NO IMPRESOS, Verifique Impresora: "+VariablesCaja.vRutaImpresora+
                               "\nReimprima Comprobante, Correlativo :"+ VariablesCaja.vNumPedVta ,pObjectFocus);
          }



      } catch(SQLException sql)
      {

    	 sql.printStackTrace();
         FarmaUtility.liberarTransaccion();
         log.error(null,sql);
         FarmaUtility.showMessage(pJDialog, "Error en BD al Imprimir los Comprobantes del Pedido.\n" + sql,pObjectFocus);
         //JMIRANDA 22/07/09 envia via email el error generado cuando no imprime
         UtilityCaja.enviaErrorCorreoPorDB(sql.toString(),VariablesCaja.vNumPedVta_Anul);
      } catch(Exception e)
      {
    	 e.printStackTrace();
         FarmaUtility.liberarTransaccion();
         log.error(null,e);
         FarmaUtility.showMessage(pJDialog, "Error en la Aplicacion al Imprimir los Comprobantes del Pedido.\n" + e,pObjectFocus);
         //JMIRANDA 22/07/09 envia via email el error generado cuando no imprime
         UtilityCaja.enviaErrorCorreoPorDB(e.toString(),VariablesCaja.vNumPedVta_Anul);
      }
    }


    private static boolean obtieneDetalleComp(JDialog pJDialog, String pSecCompPago,String pTipoCompPago,String pTipCliConv, Object pObjectFocus)
    {
      VariablesConvenioBTLMF.vArray_ListaDetComprobante = new ArrayList();
      boolean  valor = true;
      long tmpT1,tmpT2;
      tmpT1 = System.currentTimeMillis();
      try
      {
        DBConvenioBTLMF.obtieneDetalleCompPagos(VariablesConvenioBTLMF.vArray_ListaDetComprobante,pSecCompPago, pTipoCompPago,pTipCliConv);
        if(VariablesConvenioBTLMF.vArray_ListaDetComprobante.size() == 0)
        {
      	  FarmaUtility.liberarTransaccion();
          FarmaUtility.showMessage(pJDialog,"No se pudo determinar el detalle del Pedido. Verifique!!!.",pObjectFocus);
          valor = false;
        }
        System.err.println("VariablesConvenioBTLMF.vArray_ListaDetComprobante : " + VariablesConvenioBTLMF.vArray_ListaDetComprobante.size());
        valor = true;
      } catch(SQLException sql)
      {
        FarmaUtility.liberarTransaccion();
        FarmaUtility.showMessage(pJDialog,"Error al obtener Detalle de Impresion de Comprobante.",pObjectFocus);
        log.info("Error al obtener Detalle de Impresion de Comprobante imprimir");
        log.error(null,sql);
        valor =false;
        UtilityCaja.enviaErrorCorreoPorDB(sql.toString(),null);
      }

      tmpT2 = System.currentTimeMillis();
      log.debug("Tiempo 4: Det.Comp Pago:"+(tmpT2 - tmpT1)+" milisegundos");


      return valor;
    }

    public static boolean obtieneCompPago(JDialog pJDialog, String pTipClienteConv,Object pObjectFocus)
    {
      VariablesCaja.vArrayList_DetalleImpr = new ArrayList();
      boolean  valor = true;
      long tmpT1,tmpT2;
      tmpT1 = System.currentTimeMillis();
      try
      {
        DBConvenioBTLMF.obtieneCompPagos(VariablesConvenioBTLMF.vArray_ListaComprobante, pTipClienteConv);
        if(VariablesConvenioBTLMF.vArray_ListaComprobante.size() == 0)
        {
      	  FarmaUtility.liberarTransaccion();
          FarmaUtility.showMessage(pJDialog,"No se pudo determinar los datos del comprobante. Verifique!!!.",pObjectFocus);
          valor = false;
        }
        System.err.println("VariablesConvenioBTLMF.vArray_ListaComprobante : " + VariablesConvenioBTLMF.vArray_ListaComprobante.size());
        valor = true;
      } catch(SQLException sql)
      {
        FarmaUtility.liberarTransaccion();
        FarmaUtility.showMessage(pJDialog,"Error al obtener los datos de Impresion del Comprobante.",pObjectFocus);
        log.info("Error al obtener los datos  de Impresion del Comprobante a imprimir");
        log.error(null,sql);
        valor =false;
        UtilityCaja.enviaErrorCorreoPorDB(sql.toString(),null);
      }
      tmpT2 = System.currentTimeMillis();
      log.debug("Tiempo 4: Det.Comp Pago:"+(tmpT2 - tmpT1)+" milisegundos");
      return valor;
    }


public static void imprimeComprobantePago(JDialog pJDialog,
            ArrayList pDetalleComprobante,
            ArrayList pTotalesComprobante,
            String pTipCompPago,
            String pNumComprobante,
            String pValTotalNeto,
            String pValIgvComPago,
	        String pValCopagoCompPago,
	        String pValIgvCompCoPago,
	        String pNumCompRef,
	        String pImprDatAdic,
	        String pTipoClienteConvenio,
	        String pCodTipoConvenio,
	        String pFechaCreacionComp,
                String pRefTipComp,
                String pValRedondeoCompPago                          
            ) throws Exception {


		/**
		* Ruta para la generecion del archivo
		* @author JCORTEZ
		* @since 06.07.09
		* */
		String ruta ="";
		ruta=DBCaja.ObtieneDirectorio();

		//Se agrega la Fecha al archivo Impreso.
		//JMIRANDA  07/07/2009
		Date vFecImpr = new Date();
		String fechaImpresion;

		String DATE_FORMAT = "yyyyMMdd";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		// System.out.println("Today is " + sdf.format(vFecImpr));
		fechaImpresion =  sdf.format(vFecImpr);

		System.out.println("fecha : " +fechaImpresion);


		VariablesCaja.vRutaImpresora = UtilityConvenioBTLMF.obtieneRutaImpresora(pTipCompPago);

		VariablesConvenioBTLMF.vTipoCompPagoAux = pTipCompPago;

		if (pTipCompPago.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_BOLETA) ){

			//Imprimiendo mensajes

		   if(VariablesConvenioBTLMF.vFlgImprimirCompBoleta)
		   {
			DlgMsjeImpresionCompBTLMF dlgLogin = new DlgMsjeImpresionCompBTLMF(new Frame(),ConstantsPtoVenta.MENSAJE_LOGIN,true);
        	dlgLogin.setVisible(true);
        	VariablesConvenioBTLMF.vFlgImprimirCompBoleta = false;
		   }
		    ruta=ruta+fechaImpresion+"_"+"B_"+VariablesCaja.vNumPedVta+"_"+pNumComprobante+".TXT";

		   //impresion
		   imprimeBoleta(pJDialog,
					  pDetalleComprobante,
					  pValTotalNeto,
					  pNumComprobante,
					  pValIgvComPago,
			          pValCopagoCompPago,
			          pValIgvCompCoPago,
			          pNumCompRef,
					  ruta,
					  true,
					  pImprDatAdic,
					  pTipoClienteConvenio,
					  pCodTipoConvenio,
					  pFechaCreacionComp,
                                          pValRedondeoCompPago);


		}else if (pTipCompPago.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET)){  //JCORTEZ  25.03.09

			  if(VariablesConvenioBTLMF.vFlgImprimirCompTicket)
			   {
				DlgMsjeImpresionCompBTLMF dlgLogin = new DlgMsjeImpresionCompBTLMF(new Frame(),ConstantsPtoVenta.MENSAJE_LOGIN,true);
                                dlgLogin.setVisible(true);
                                VariablesConvenioBTLMF.vFlgImprimirCompTicket = false;
			   }


			ruta=ruta+fechaImpresion+"_"+"TB_"+VariablesCaja.vNumPedVta+"_"+pNumComprobante+".TXT";
			//Imprimiendo mensajes

			//impresion
			imprimeTicket(pJDialog,
						  pDetalleComprobante,
						  pValTotalNeto,
						  pNumComprobante,
						  pValIgvComPago,
				          pValCopagoCompPago,
				          pValIgvCompCoPago,
				          pNumCompRef,
						  ruta,true,pImprDatAdic,
						  pTipoClienteConvenio,
						  pCodTipoConvenio,
						  pFechaCreacionComp,
                                                  pValRedondeoCompPago);



                //FIN DE QUE SE HAYA COBRADO EXITOSAMENTE
                 imprimirVoucher(pJDialog, null,VariablesCaja.vNumPedVta,VariablesConvenioBTLMF.vCodConvenio);




      }
	  else if (pTipCompPago.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_GUIA))
	  {

		  if(VariablesConvenioBTLMF.vFlgImprimirCompGuia)
		   {
		    DlgMsjeImpresionCompBTLMF dlgLogin = new DlgMsjeImpresionCompBTLMF(new Frame(),ConstantsPtoVenta.MENSAJE_LOGIN,true);
                    dlgLogin.setVisible(true);
                    VariablesConvenioBTLMF.vFlgImprimirCompGuia = false;
		   }
			ruta=ruta+fechaImpresion+"_"+"G_"+VariablesCaja.vNumPedVta+"_"+pNumComprobante+".TXT";

			//impresion
			imprimeGuia(pJDialog,
						pDetalleComprobante,
						pValTotalNeto,
						pNumComprobante,
						pValIgvComPago,
				        pValCopagoCompPago,
				        pValIgvCompCoPago,
				        pNumCompRef,
			            ruta,
			            true,
			            pImprDatAdic,
			            pTipoClienteConvenio,
			            pCodTipoConvenio,
			            pFechaCreacionComp,
                                    pRefTipComp,
                                    pValRedondeoCompPago);



	  }
      else if ( pTipCompPago.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_FACTURA) )
     {
      if(VariablesConvenioBTLMF.vFlgImprimirCompFactura)
      {
    	  DlgMsjeImpresionCompBTLMF dlgLogin = new DlgMsjeImpresionCompBTLMF(new Frame(),ConstantsPtoVenta.MENSAJE_LOGIN,true);
    	  dlgLogin.setVisible(true);
    	  VariablesConvenioBTLMF.vFlgImprimirCompFactura = false;
      }

      ruta=ruta+fechaImpresion+"_"+"F_"+VariablesCaja.vNumPedVta+"_"+pNumComprobante+".TXT";

	 //impresion
	  imprimeFactura(pJDialog,
				     pDetalleComprobante,
				     pValTotalNeto,
				     pNumComprobante,
				     pValIgvComPago,
		             pValCopagoCompPago,
		             pValIgvCompCoPago,
		             pNumCompRef,
				     ruta,
				     true,
				     pImprDatAdic,
				     pTipoClienteConvenio,
				     pCodTipoConvenio,
				     pFechaCreacionComp,
                                     pValRedondeoCompPago
				     );
    }
}

private static void imprimeFactura(JDialog   pJDialog,
						        ArrayList pDetalleComprobante,
						        String    pValTotalNeto,
						        String    pNumComprobante,
						        String    pValIgvComPago,
						        String    pValCopagoCompPago,
						        String    pValIgvComCoPago,
						        String    pNumCompCoPago,
						        String    pRuta,
						        boolean   bol,
						        String pImprDatAdic,
						        String pTipoClienteConvenio,
						        String pCodTipoConvenio,
						        String pFechaBD,
						        String pValRedondeoComPago
						        ) throws Exception {
		System.out.println("IMPRIMIR FACTURA No : " + pNumComprobante);

		VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
		float subTotal = 0;
		float montoIGV = 0;
		float SumSubTotal = 0;
		double SumMontoIGV = 0;


		String pNomImpreso = " ";
		String pDirImpreso = " ";

		//Comentado por FRAMIREZ
		//FarmaPrintService vPrint = new FarmaPrintService(36, VariablesCaja.vRutaImpresora, false);
		//VariablesCaja.vRutaImpresora = "/\\/10.11.1.54/reporte1";
		FarmaPrintService vPrint = new FarmaPrintService(36,VariablesCaja.vRutaImpresora , false);

		//JCORTEZ 16.07.09 Se genera archivo linea por linea
		FarmaPrintServiceTicket vPrintArchivo = new FarmaPrintServiceTicket(666, pRuta, false);
		vPrintArchivo.startPrintService();

		System.out.println("vRutaImpresora : " +VariablesCaja.vRutaImpresora);
		System.out.println("Ruta : " +pRuta);
		//  if ( !vPrint.startPrintService() )  throw new Exception("Error en Impresora. Verifique !!!");
		System.out.println("VariablesCaja.vNumPedVta:" + VariablesCaja.vNumPedVta);
		if (!vPrint.startPrintService())
		{
            VariablesCaja.vEstadoSinComprobanteImpreso="S";
			log.info("**** Fecha :"+ pFechaBD);
			log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
			log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
			log.info("**** IP :" + FarmaVariables.vIpPc);
			log.info("ERROR DE IMPRESORA : No se pudo imprimir la boleta");
		}

		else {
		try {
		vPrint.activateCondensed();

	    String dia = pFechaBD.substring(0,2);
	    String mesLetra=FarmaUtility.devuelveMesEnLetras(Integer.parseInt(pFechaBD.substring(3,5)));
	    String ano = pFechaBD.substring(6,10);
	    String hora ="";// pFechaBD.substring(11,19);


		if(VariablesPtoVenta.vIndDirMatriz){
		vPrint.printLine(FarmaPRNUtility.llenarBlancos(30) +"Dom.Fiscal: "+ VariablesPtoVenta.vDireccionMatriz ,true);
		vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(30) +"Dom.Fiscal: "+ VariablesPtoVenta.vDireccionMatriz ,true);
		}
		//JMIRANDA 22.08.2011 Cambio para verificar si imprime
		if(UtilityVentas.getIndImprimeCorrelativo()){
		vPrint.printLine(FarmaPRNUtility.llenarBlancos(11) + pFechaBD + "   CORR." + VariablesCaja.vNumPedVta,true);
		vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(11) + pFechaBD + "   CORR." + VariablesCaja.vNumPedVta,true);
		}
		else{
		vPrint.printLine(FarmaPRNUtility.llenarBlancos(17) +  VariablesConvenioBTLMF.vInstitucion.toUpperCase().trim() ,true);
		vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(17) +  VariablesConvenioBTLMF.vInstitucion.toUpperCase().trim() ,true);
		}

	        vPrint.printLine(FarmaPRNUtility.llenarBlancos(17) + FarmaPRNUtility.alinearIzquierda(VariablesConvenioBTLMF.vDireccion.trim(),70),true);
                vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(17) + FarmaPRNUtility.alinearIzquierda(VariablesConvenioBTLMF.vDireccion.trim(),70),true);

		vPrint.printLine(FarmaPRNUtility.llenarBlancos(17) + VariablesConvenioBTLMF.vRuc.trim(),true);
		       vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(17) +VariablesConvenioBTLMF.vRuc.trim(),true);

		vPrint.printLine(FarmaPRNUtility.llenarBlancos(17) + dia + " de " + mesLetra + " del " + ano + "     " + hora  + FarmaPRNUtility.llenarBlancos(50) + "No. " + pNumComprobante.substring(0,3) + "-" + pNumComprobante.substring(3,10),true);
		       vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(17) + dia + " de " + mesLetra + " del " + ano + "     " + hora  + FarmaPRNUtility.llenarBlancos(50) + "No. " + pNumComprobante.substring(0,3) + "-" + pNumComprobante.substring(3,10),true);

		vPrint.printLine(FarmaPRNUtility.llenarBlancos(12) + "     ",true);
		       vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(12) + "     ",true);

		vPrint.printLine(" ",true);
		       vPrintArchivo.printLine(" ",true);

		int linea = 0;

		int vNroEspacio = 0;
		for (int i=0; i<pDetalleComprobante.size(); i++)
		{
			vPrint.printLine(" " +
                    FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(6)).trim(),6) + " " +
                    FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim(),11) + "   " +
                    FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),38) + "   " +
                    FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),14) + "   " +
                    FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),20) + FarmaPRNUtility.llenarBlancos(2) +
                    FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(4)).trim(),13) + FarmaPRNUtility.llenarBlancos(4) +
                    FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),10)
                      ,true
                   );

              vPrintArchivo.printLine(" " +
                    FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(6)).trim(),6) + " " +
                    FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim(),11) + "   " +
                    FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),38) + "   " +
                    FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),14) + "   " +
                    FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),20) + FarmaPRNUtility.llenarBlancos(2) +
                    FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(4)).trim(),13) + FarmaPRNUtility.llenarBlancos(4) +
                    FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),10)
                      ,true
                   );

			System.out.println("SubTotal String:::"+((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim());
			linea += 1;
			subTotal =new Double(FarmaUtility.getDecimalNumber(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim())).floatValue();
			montoIGV =new Double(FarmaUtility.getDecimalNumber(((String)((ArrayList)pDetalleComprobante.get(i)).get(18)).trim())).floatValue();
			SumMontoIGV= SumMontoIGV +montoIGV;
			System.out.println("SubTotal:"+subTotal);
			SumSubTotal = SumSubTotal + subTotal;
		}


		System.out.println("SumSubTotal:"+SumSubTotal);

		//*************************************INFORMACION DEL CONVENIO***********************************************//

	    double porcCopago  = Math.round((FarmaUtility.getDecimalNumber(pValCopagoCompPago)/(FarmaUtility.getDecimalNumber(pValTotalNeto)+FarmaUtility.getDecimalNumber(pValCopagoCompPago)))*100);
               SumMontoIGV = SumMontoIGV-((SumMontoIGV*porcCopago)/100);
        double ValCopagoCompPagoSinIGV  =  ((SumSubTotal*porcCopago)/100);

    	vPrint.printLine("      " + FarmaPRNUtility.alinearIzquierda(" ",85) + "        " +
		"    Sub Total   S/. " + FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(SumSubTotal),10),true);
		vPrintArchivo.printLine("      " + FarmaPRNUtility.alinearIzquierda(" ",85) + "        " +
		"    Sub Total   S/. " + FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(SumSubTotal),10),true);

		    double pValTotalNetoRedondeo  = FarmaUtility.getDecimalNumber(pValTotalNeto)+FarmaUtility.getDecimalNumber(pValRedondeoComPago);

		if(pCodTipoConvenio.equals("1"))
		{

			vPrint.printLine(FarmaPRNUtility.alinearIzquierda("  SON: "+FarmaPRNUtility.montoEnLetras(pValTotalNetoRedondeo),85) +"            " +
			"Coaseguro("+FarmaUtility.formatNumber(porcCopago,0)+"%)    S/. " + FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(ValCopagoCompPagoSinIGV),10),true);
			vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda("  SON: "+FarmaPRNUtility.montoEnLetras(pValTotalNetoRedondeo),85) +"            " +
					"Coaseguro("+FarmaUtility.formatNumber(porcCopago,"")+"%)    S/. " + FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(ValCopagoCompPagoSinIGV),10),true);

			vPrint.printLine(FarmaPRNUtility.alinearIzquierda("              " + "     ",85) +"                       ---------------------",true);
			vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda("              " + "     ",85) +"                       ---------------------",true);

			vPrint.printLine(FarmaPRNUtility.alinearIzquierda("  Institución: "+VariablesConvenioBTLMF.vInstitucion.toUpperCase().trim()+ "  ("+FarmaUtility.formatNumber(100-porcCopago,"")+")%",85) +"                                  "+FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(SumSubTotal-ValCopagoCompPagoSinIGV),10),true);
			vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda("  Institución: " +VariablesConvenioBTLMF.vInstitucion.toUpperCase().trim()+ "  ("+FarmaUtility.formatNumber(100-porcCopago,"")+")%",85) +"                                    "+ FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(SumSubTotal-ValCopagoCompPagoSinIGV),10),true);
			vPrint.printLine(FarmaPRNUtility.alinearIzquierda("  Convenio: " + VariablesConvenioBTLMF.vNomConvenio.toUpperCase().trim(),85) +"                       ",true);
			vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda("  Convenio: " + VariablesConvenioBTLMF.vNomConvenio.toUpperCase(),85) +"                       ",true);

			vPrint.printLine(FarmaPRNUtility.alinearIzquierda("  Beneficiario: " + VariablesConvenioBTLMF.vNomCliente,85)+"             " + " " +
			"                    ",true);
			vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda("  Beneficiario: " + VariablesConvenioBTLMF.vNomCliente,85) +"               " +  " " +
			"                    ",true);

			vPrint.printLine(FarmaPRNUtility.alinearIzquierda("  Documento de Referencia Nro "+pNumCompCoPago+": ",85) +"                       ",true);
			vPrint.printLine(FarmaPRNUtility.alinearIzquierda("  Doc refe de la Empresa Monto:S/." + pValCopagoCompPago +" y ("+FarmaUtility.formatNumber(porcCopago,"")+"%)",85)+"                       ",true);
			vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda("  Documento de Referencia Nro: "+pNumCompCoPago+" ",85) +"                        ",true);
			vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda("  Doc refe de la Empresa Monto:S/." + pValCopagoCompPago +" y ("+FarmaUtility.formatNumber(porcCopago,"")+"%)",85) +"                       ",true);

		}
		else
		{
			vPrint.printLine(FarmaPRNUtility.alinearIzquierda("  SON: "+FarmaPRNUtility.montoEnLetras(pValTotalNetoRedondeo),85) +"            " ,true);
			vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda("  SON: "+FarmaPRNUtility.montoEnLetras(pValTotalNetoRedondeo),85) +"            ",true);

			vPrint.printLine(FarmaPRNUtility.alinearIzquierda("              " + "     ",85) +"                       ---------------------",true);
			vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda("              " + "     ",85) +"                       ---------------------",true);

			vPrint.printLine(FarmaPRNUtility.alinearIzquierda("  Institución: "+VariablesConvenioBTLMF.vInstitucion.toUpperCase().trim()+ "  ("+FarmaUtility.formatNumber(100-porcCopago,"")+")%",85) +"                                  ",true);
			vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda("  Institución: " +VariablesConvenioBTLMF.vInstitucion.toUpperCase().trim()+ "  ("+FarmaUtility.formatNumber(100-porcCopago,"")+")%",85) +"                                    ",true);
			vPrint.printLine(FarmaPRNUtility.alinearIzquierda("  Convenio: " + VariablesConvenioBTLMF.vNomConvenio.toUpperCase().trim(),85) +"                       ",true);
			vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda("  Convenio: " + VariablesConvenioBTLMF.vNomConvenio.toUpperCase(),85) +"                       ",true);

		    vPrint.printLine(FarmaPRNUtility.alinearIzquierda("  Beneficiario: " + VariablesConvenioBTLMF.vNomCliente,85)+"             " + " " +
			"                    ",true);
			vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda("  Beneficiario: " + VariablesConvenioBTLMF.vNomCliente,85) +"               " +  " " +
			"                    ",true);

		}

              int var = 0;
              if (pImprDatAdic.equals("1"))
	       {
		    if (VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic != null &&  VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.size() > 0)
		    {
                       var = 3;
                    }
               }

                if (linea == 5) vNroEspacio = 3-var;
                if (linea == 4) vNroEspacio = 4-var;
                if (linea == 3) vNroEspacio = 5-var;
                if (linea == 2) vNroEspacio = 6-var;
                if (linea == 1) vNroEspacio = 7-var;

                 for (int c= 0; c < vNroEspacio; c++)
                 {
                    vPrintArchivo.printLine(" " + FarmaPRNUtility.alinearIzquierda(" ",65) + " ",true);
                            vPrint.printLine("  " + FarmaPRNUtility.alinearIzquierda(" ",65),true);
                 }

   	         if (pImprDatAdic.equals("1"))
		    {
		            if (VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic != null &&  VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.size() > 0)
		           {
		                    vPrintArchivo.printLine("  Datos Adicionales " +  " ",true);
		                vPrint.printLine("  Datos Adicionales",true);

		                    for (int j = 0; j < VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.size(); j++)
		            {
		                            Map datosAdicConv = (Map)VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.get(j);

		                            String pNombCampo   = (String)datosAdicConv.get("NOMBRE_CAMPO");
		                            String pDesCampo    = (String)datosAdicConv.get("DESCRIPCION_CAMPO");
		                            String vFlgImprime  = (String)datosAdicConv.get("FLG_IMPRIME");
		                            String vCodCampo    = (String)datosAdicConv.get("COD_CAMPO");



		                            log.debug("pDesCampo   :"+pNombCampo);
		                            log.debug("pNombCampo  :"+pNombCampo);
		                            log.debug("vFlgImprime :"+vFlgImprime);
		                            log.debug("vCodCampo :"+vCodCampo);

		                             if(vFlgImprime.equals("1") || vFlgImprime.equals("2"))
		                             {
		                                 if (vCodCampo.equals(ConstantsConvenioBTLMF.COD_NOMB_TITULAR) || vCodCampo.equals(ConstantsConvenioBTLMF.COD_NRO_ATENCION))
		                                 {
		                                     vPrintArchivo.printLine("  - "+pNombCampo +  "    "+pDesCampo+" ",false);
		                                     vPrint.printLine("  - "+pNombCampo +  "    "+pDesCampo+" ",false);
		                                 }

		                             }

		            }
		        }
		    }

		vPrintArchivo.printLine("  " + FarmaPRNUtility.alinearIzquierda(" ",65) + " ",true);
		vPrint.printLine("   " + FarmaPRNUtility.alinearIzquierda(" ",65),true);
		vPrintArchivo.printLine("  " + FarmaPRNUtility.alinearIzquierda(" ",65) + " ",true);
		 vPrint.printLine("   " + FarmaPRNUtility.alinearIzquierda(" ",65),true);

		    

		vPrint.printLine("     " + FarmaPRNUtility.alinearIzquierda("                                                       "+FarmaUtility.formatNumber(SumSubTotal-ValCopagoCompPagoSinIGV),85) + FarmaUtility.formatNumber(SumMontoIGV,2) +
		"               "+FarmaUtility.formatNumber(pValTotalNetoRedondeo)+"          ",true);
		vPrintArchivo.printLine("     " + FarmaPRNUtility.alinearIzquierda("                                                       "+FarmaUtility.formatNumber(SumSubTotal-ValCopagoCompPagoSinIGV),85) + FarmaUtility.formatNumber(SumMontoIGV,2) +
		"               "+FarmaUtility.formatNumber(pValTotalNetoRedondeo)+"          ",true);

		vPrintArchivo.printLine(" " + FarmaPRNUtility.alinearIzquierda(" ",65) + " ",true);
			vPrint.printLine("  " + FarmaPRNUtility.alinearIzquierda(" ",65),true);



//		vPrintArchivo.printLine("  Institución: " +VariablesConvenioBTLMF.vInstitucion.toUpperCase() + " ",true);
//		vPrint.printLine("  Institución: " +VariablesConvenioBTLMF.vInstitucion.toUpperCase().trim(),true);
//		vPrintArchivo.printLine("  Convenio: " + VariablesConvenioBTLMF.vNomConvenio.toUpperCase(),true);
//		vPrint.printLine("  Convenio: " + VariablesConvenioBTLMF.vNomConvenio.toUpperCase().trim(),true);
//		vPrintArchivo.printLine("  Beneficiario: " + VariablesConvenioBTLMF.vNomCliente,true);
//		vPrint.printLine("  Beneficiario: " + VariablesConvenioBTLMF.vNomCliente,true);

		if(pCodTipoConvenio.equals("1"))
		{
//		 vPrintArchivo.printLine("  Documento de Referencia Nro: "+pNumCompCoPago+" ",true);
//		 vPrint.printLine("  Documento de Referencia Nro "+pNumCompCoPago+": ",true);
//		 vPrintArchivo.printLine("  Doc refe de la Empresa Monto:S/." + pValCopagoCompPago +" y ("+FarmaUtility.formatNumber(porcCopago,"")+"%)",true);
//		 vPrint.printLine("  Doc refe de la Empresa Monto:S/." + pValCopagoCompPago +" y ("+FarmaUtility.formatNumber(porcCopago,"")+"%)",true);
		}




		vPrint.printLine(" REDO: " + pValRedondeoComPago +
		" CAJERO: " + VariablesCaja.vNomCajeroImpreso + " " + VariablesCaja.vApePatCajeroImpreso + " " +
		" CAJA: " + VariablesCaja.vNumCajaImpreso +
		" TURNO: " + VariablesCaja.vNumTurnoCajaImpreso +
		" VEND: " + VariablesCaja.vNomVendedorImpreso + " " +VariablesCaja.vApePatVendedorImpreso  ,true);
		vPrintArchivo.printLine(" REDO: " + pValRedondeoComPago +
		" CAJERO: " + VariablesCaja.vNomCajeroImpreso + " " + VariablesCaja.vApePatCajeroImpreso + " " +
		" CAJA: " + VariablesCaja.vNumCajaImpreso +
		" TURNO: " + VariablesCaja.vNumTurnoCajaImpreso +
		" VEND: " + VariablesCaja.vNomVendedorImpreso + " " +VariablesCaja.vApePatVendedorImpreso  ,true);


		vPrint.deactivateCondensed();
		vPrint.endPrintService();
		vPrintArchivo.endPrintService();

		log.info("Fin al imprimir la boleta: " + pNumComprobante);
		VariablesCaja.vEstadoSinComprobanteImpreso="N";

		//JCORTEZ 16.07.09 Se guarda fecha de impresion por comprobantes
		DBCaja.actualizaFechaImpr(VariablesCaja.vNumPedVta,pNumComprobante,"C");
		log.debug("Guardando fecha impresion cobro..."+pNumComprobante);
		}
		                catch(SQLException sql)
		                      {
		                        sql.printStackTrace();
		                        VariablesCaja.vEstadoSinComprobanteImpreso="S";
		                        System.out.println("Error de BD "+ sql.getMessage());

		                          log.info("**** Fecha :"+ pFechaBD);
		                          log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
		                          log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
		                          log.info("**** IP :" + FarmaVariables.vIpPc);
		                          log.info("Error al imprimir la Factura : " + sql.getMessage());
		                          log.error(null,sql);
		                          //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
		                           // enviaErrorCorreoPorDB(sql.toString(),VariablesCaja.vNumPedVta);
		                      }

		                        catch(Exception e){
		                          e.printStackTrace();
		                          VariablesCaja.vEstadoSinComprobanteImpreso="S";
		                          log.info("**** Fecha :"+ pFechaBD);
		                          log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
		                          log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
		                          log.info("**** IP :" + FarmaVariables.vIpPc);
		                          log.info("Error al imprimir la Factura: "+e);
		                          //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
		                            //enviaErrorCorreoPorDB(e.toString(),VariablesCaja.vNumPedVta);
		                      }


		            }

}

private static void imprimeTicket(JDialog   pJDialog,
        ArrayList pDetalleComprobante,
        String    pValTotalNeto,
        String    pNumComprobante,
        String    pValIgvComPago,
        String    pValCopagoCompPago,
        String    pValIgvComCoPago,
        String    pNumCompCoPago,
        String    pRuta,
	boolean   bol,
        String pImprDatAdic,
        String pTipoClienteConvenio,
	String    pCodTipoConvenio,
	String    pFechaBD,
        String    pValRedondeoComPago) throws Exception {
		System.out.println("IMPRIMIR TICKET No : " + pNumComprobante);

		String  pNomImpreso = " ";

		VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
		float subTotal = 0;
		float SumSubTotal = 0;
		double TotalAhorro = 0;

		//FarmaPrintServiceTicket vPrint = new FarmaPrintServiceTicket(666, VariablesCaja.vRutaImpresora, false);
		//VariablesCaja.vRutaImpresora = "/\\/10.11.1.54/ticket";
		FarmaPrintServiceTicket vPrint = new FarmaPrintServiceTicket(666, VariablesCaja.vRutaImpresora, false);

	    //JCORTEZ 16.07.09 Se genera archivo linea por linea
	    FarmaPrintServiceTicket vPrintArchivo = new FarmaPrintServiceTicket(666,pRuta,false);
	    vPrintArchivo.startPrintService();
	    System.out.println("vRutaImpresora : " +VariablesCaja.vRutaImpresora);
		System.out.println("Ruta : " +pRuta);

		//  if ( !vPrint.startPrintService() )  throw new Exception("Error en Impresora. Verifique !!!");
		System.out.println("VariablesCaja.vNumPedVta:" + VariablesCaja.vNumPedVta);
		if (!vPrint.startPrintService())
		{
			VariablesCaja.vEstadoSinComprobanteImpreso="S";
			log.info("**** Fecha :"+ pFechaBD);
			log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
			log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
			log.info("**** IP :" + FarmaVariables.vIpPc);
			log.info("ERROR DE IMPRESORA : No se pudo imprimir la boleta");
		}

		else {
		try {


	         //JCHAVEZ 03.07.2009.sn
	         System.out.println("Seteando el Color ...");
	         Date fechaJava = new Date();
	         //çSystem.out.println("fecha : " +fechaJava);
	         //System.out.println(fechaJava.getTime().);
	         int dia=0;
	         int resto= dia % 2;
	         System.out.println("resto : " +resto);
	         if (resto ==0&&VariablesPtoVenta.vIndImprimeRojo)
	            vPrint.printLine((char)27+"4",true );  //rojo
	         else
	            vPrint.printLine((char)27+"5",true );  //negro
	         //JCHAVEZ 03.07.2009.en

	         log.info("imprime datos de cabecera de impresion");
	           vPrint.printLine(FarmaPRNUtility.llenarBlancos(12)+ " BOTICAS MIFARMA"+FarmaPRNUtility.llenarBlancos(12),true);
	         vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(12)+ " BOTICAS MIFARMA"+FarmaPRNUtility.llenarBlancos(12),true);
	           vPrint.printLine(FarmaPRNUtility.llenarBlancos(1)+ " TICKET - MIFARMA S.A.C." + " " +  "RUC: "+FarmaVariables.vNuRucCia,true);
	         vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1)+ " TICKET - MIFARMA S.A.C." + " " +  "RUC: "+FarmaVariables.vNuRucCia,true);
	           vPrint.printLine("Central: "+VariablesPtoVenta.vDireccionMatriz,true);
	         vPrintArchivo.printLine("Central: "+VariablesPtoVenta.vDireccionMatriz,true);

	       //JMIRANDA 22.08.2011 Cambio para verificar si imprime
	         if(UtilityVentas.getIndImprimeCorrelativo()){
	           vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + "Telf: 2130760 "+"          "+"CORR. "+VariablesCaja.vNumPedVta,true);
	         vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1) + "Telf: 2130760 "+"          "+"CORR. "+VariablesCaja.vNumPedVta,true);
	         }
	         else{
	           vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + "Telf: 2130760 ",true);
	         vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1) + "Telf: 2130760 ",true);
	         }
	         vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + "T"+FarmaVariables.vCodLocal+ " " + FarmaVariables.vDescCortaDirLocal,true);
	         vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1) + "T"+FarmaVariables.vCodLocal+ " " + FarmaVariables.vDescCortaDirLocal,true);

	            //vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + "D. "+FarmaVariables.vDescCortaDirLocal,true);
	            vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + "Serie: "+FarmaPRNUtility.alinearIzquierda(VariablesCaja.vSerieImprLocalTicket,20)+"    " + FarmaPRNUtility.alinearDerecha(VariablesCaja.vNumCaja,7)+"-"+VariablesCaja.vNumTurnoCajaImpreso.trim(),true);
	         vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1) + "Serie: "+FarmaPRNUtility.alinearIzquierda(VariablesCaja.vSerieImprLocalTicket,20)+"    " + FarmaPRNUtility.alinearDerecha(VariablesCaja.vNumCaja,7)+"-"+VariablesCaja.vNumTurnoCajaImpreso.trim(),true);
	            //vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + FarmaPRNUtility.alinearDerecha("CORR. "+VariablesCaja.vNumPedVta,16),true);
	            //vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + "Fecha:"+pFechaBD + FarmaPRNUtility.alinearDerecha("CAJA:"+VariablesCaja.vNumCaja,7)+" "+FarmaPRNUtility.alinearDerecha("TURNO:"+VariablesCaja.vNumTurnoCajaImpreso,7) ,true);
	            //vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + "Fecha:"+pFechaBD + FarmaPRNUtility.alinearDerecha(VariablesCaja.vNumCaja,7)+"-"+FarmaPRNUtility.alinearDerecha(VariablesCaja.vNumTurnoCajaImpreso,7) ,true);

	         vPrint.printLine(/*FarmaPRNUtility.llenarBlancos(1) +*/ "Fecha:"+pFechaBD+FarmaPRNUtility.llenarBlancos(1)+FarmaPRNUtility.alinearDerecha("Nro: "+pNumComprobante.substring(0,3) + "-" + pNumComprobante.substring(3,10),16) ,true);
	         vPrintArchivo.printLine(/*FarmaPRNUtility.llenarBlancos(1) +*/ "Fecha:"+pFechaBD+FarmaPRNUtility.llenarBlancos(1)+FarmaPRNUtility.alinearDerecha("Nro: "+pNumComprobante.substring(0,3) + "-" + pNumComprobante.substring(3,10),16),true);

	         if(pNomImpreso.trim().length()>0)
	            vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + FarmaPRNUtility.alinearIzquierda("CLIENTE:"+pNomImpreso.trim(),41),true);
	               vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1) + FarmaPRNUtility.alinearIzquierda("CLIENTE:"+pNomImpreso.trim(),41),true);

	            //vPrint.printLine("==========================================" ,true);
	            vPrint.printLine(" Cant."+"   "+"Descripcion"+"       Dscto"+"   Importe" ,true);
	          vPrintArchivo.printLine(" Cant."+"   "+"Descripcion"+"          Dscto"+"   Importe" ,true);

	         log.info("fin de impresion de cabecera");
	 	     int linea = 0;
	 	     log.info("Inicio de impresion Detalle");

		for (int i=0; i<pDetalleComprobante.size(); i++)
		{
		//Agregado por DVELIZ 13.10.08

            //Agregado por DVELIZ 13.10.08
            String valor = ((String)((ArrayList)pDetalleComprobante.get(i)).get(16)).toString().trim();
            log.info("Fila detalle "+ i+ ") "+ valor);
            if(valor.equals("0.000")) valor = " ";
            //fin DVELIZ
            log.info("Detalle "+i+")"+ (ArrayList)pDetalleComprobante.get(i) );
            System.out.println("valor 2:"+valor);
            log.info("valor "+valor);
            //JMIRANDA 06.10.09

            double valor1 =  (UtilityVentas.Redondear(FarmaUtility.getDecimalNumber(valor),2));
            log.error("valor1: "+valor1);
            if(valor1==0.0){
                valor = "";
            }
            else{
                valor = Double.toString(valor1);
            }
            log.error("valorXXX: "+valor);
            vPrint.printLine("" +
                             //9
                             //FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim(),9) + "  " +
                             //SE ESTA CENTRANDO LA CANTIDAD COMPRADA
                             //DUBILLUZ 09/07/2009
                            UtilityCaja.pFormatoLetra(FarmaUtility.getValueFieldArrayList(pDetalleComprobante,i,0),9," ")+ "  " +
                             //20
                            //VERSION 2
                            FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),27) +
                            "       "+
                            //UNIDAD
                            //FarmaPRNUtility.alinearIzquierda( "      "+ ((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),15) + "  " +
                            FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),11) + " " +
                            //LAB
                            FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),9) + " "+
                            //JMIRANDA 06.10.09
                            //AHORRO
                            FarmaPRNUtility.alinearDerecha(valor,5) + "  " +
                            //FarmaPRNUtility.alinearDerecha(UtilityVentas.Redondear(FarmaUtility.getDecimalNumber(valor),2),5) + "  " +

                            //PRECIO
                            FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),10)
                            //FarmaPRNUtility.alinearDerecha("12,151.30",10)
                             ,true);


				            vPrintArchivo.printLine( "" +
				            		        UtilityCaja.pFormatoLetra(FarmaUtility.getValueFieldArrayList(pDetalleComprobante,i,0),9," ")+ "  " +
				                            FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),27) +
				                            "       "+
				                            FarmaPRNUtility.alinearIzquierda( "      "+ ((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),15) + "  " +
				                            FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),11) + " "+
				            //JMIRANDA 06.10.09
				            FarmaPRNUtility.alinearDerecha(UtilityVentas.Redondear(FarmaUtility.getDecimalNumber(valor),2),5) + "  " +
				                            FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),10)
				                             ,true);
									System.out.println("SubTotal String:::"+((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim());
					linea += 1;
					subTotal =new Double(FarmaUtility.getDecimalNumber(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim())).floatValue();

					System.out.println("SubTotal:"+subTotal);


					SumSubTotal = SumSubTotal + subTotal;
					TotalAhorro = TotalAhorro + valor1;
					}


		System.out.println("SumSubTotal:"+SumSubTotal);

		//*************************************INFORMACION DEL CONVENIO***********************************************//

		double igv =FarmaUtility.getDecimalNumber(pValIgvComPago)+FarmaUtility.getDecimalNumber(pValIgvComCoPago);
		double total =SumSubTotal;
		double porcCopago = Math.round((FarmaUtility.getDecimalNumber(pValCopagoCompPago)/(FarmaUtility.getDecimalNumber(pValTotalNeto)+FarmaUtility.getDecimalNumber(pValCopagoCompPago)))*100);
		double porcCopagoBenif =100-(FarmaUtility.getDecimalNumber(pValCopagoCompPago)/(FarmaUtility.getDecimalNumber(pValTotalNeto)+FarmaUtility.getDecimalNumber(pValCopagoCompPago)))*100;



		vPrint.printLine("                Sub Total   S/. " + FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(SumSubTotal),10),true);
		vPrintArchivo.printLine("                Sub Total   S/. " + FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(SumSubTotal),10),true);

		if(pCodTipoConvenio.equals("1"))
        {

		  if (pTipoClienteConvenio.equals("1"))
		  {
		    vPrint.printLine("            CoPago("+FarmaUtility.formatNumber(porcCopagoBenif,0)+"%)    S/. " + FarmaPRNUtility.alinearDerecha(pValTotalNeto,10),true);
		    vPrintArchivo.printLine("            CoPago("+FarmaUtility.formatNumber(porcCopagoBenif,"")+"%)    S/. " + FarmaPRNUtility.alinearDerecha(pValTotalNeto,10),true);
		  }
		  else
		  {
			 vPrint.printLine("            Credito("+FarmaUtility.formatNumber(porcCopago,0)+"%)    S/. " + FarmaPRNUtility.alinearDerecha(pValCopagoCompPago,10),true);
			 vPrintArchivo.printLine("            Credito("+FarmaUtility.formatNumber(porcCopago,"")+"%)    S/. " + FarmaPRNUtility.alinearDerecha(pValCopagoCompPago,10),true);

		  }
        }

		log.info("Imprimiendo Redondeo y total");
        vPrint.printLine("------------------------------------------" ,true);
            vPrintArchivo.printLine("------------------------------------------",true);                    
        double pValTotalNetoRedondeo  = FarmaUtility.getDecimalNumber(pValTotalNeto)+FarmaUtility.getDecimalNumber(pValRedondeoComPago);                    
        vPrint.printLine("Red. :S/.  " + pValRedondeoComPago + "    Total:S/.  " + FarmaUtility.formatNumber(pValTotalNetoRedondeo),true);
           vPrintArchivo.printLine("Red. :S/.  " + pValRedondeoComPago + "    Total:S/.  " + FarmaUtility.formatNumber(pValTotalNetoRedondeo),true);
        vPrint.printLine("==========================================" ,true);
        vPrintArchivo.printLine("==========================================" ,true);

        vPrint.printLine("     USTED HA AHORRADO : S/." +FarmaUtility.formatNumber(TotalAhorro),true);
        vPrintArchivo.printLine("    USTED HA AHORRADO : S/." +FarmaUtility.formatNumber(TotalAhorro),true);

		vPrintArchivo.printLine(" " + FarmaPRNUtility.alinearIzquierda(" ",23) + " ",true);
		vPrint.printLine("  " + FarmaPRNUtility.alinearIzquierda(" ",23),true);
		vPrintArchivo.printLine("  Convenio: " +VariablesConvenioBTLMF.vNomConvenio.toUpperCase(),true);
		vPrint.printLine("  Convenio: " + VariablesConvenioBTLMF.vNomConvenio.toUpperCase(),true);
		vPrintArchivo.printLine("    Benef.: " + VariablesConvenioBTLMF.vNomCliente,true);
		vPrint.printLine("    Benef.: " + VariablesConvenioBTLMF.vNomCliente,true);


		vPrintArchivo.printLine("       DNI: " + VariablesConvenioBTLMF.vDni,true);
		vPrint.printLine("       DNI: " + VariablesConvenioBTLMF.vDni,true);

		if(pCodTipoConvenio.equals("1"))
	    {
				vPrintArchivo.printLine("  Documento de Referencia Nro:"+pNumCompCoPago+" " +  " ",true);
				vPrint.printLine("  Documento de Referencia Nro:"+pNumCompCoPago,true);
				vPrintArchivo.printLine("  Doc ref de la Empresa Monto:S/." + pValCopagoCompPago,true);
				vPrintArchivo.printLine(" y ("+FarmaUtility.formatNumber(porcCopago,"")+"%)",true);
				vPrint.printLine("  Doc ref de la Empresa Monto:S/."+pValCopagoCompPago,true);
				vPrint.printLine(" y ("+FarmaUtility.formatNumber(porcCopago,"")+"%)",true);
	    }

		 if (pImprDatAdic.equals("1"))
		 {

		    log.debug("VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic   :"+VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic);

            if (VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic != null && VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.size() > 0)
            {
            	vPrintArchivo.printLine("  Datos Adicionales " +  " ",true);
    		 	vPrint.printLine("  Datos Adicionales",true);
				for (int j = 0; j < VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.size(); j++)
		        {
					Map datosAdicConv = (Map)VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.get(j);

					String pNombCampo     = (String)datosAdicConv.get("NOMBRE_CAMPO");
					String pDesCampo    = (String)datosAdicConv.get("DESCRIPCION_CAMPO");
					String vFlgImprime   = (String)datosAdicConv.get("FLG_IMPRIME");

					log.debug("pDesCampo   :"+pNombCampo);
					log.debug("pNombCampo  :"+pNombCampo);
					log.debug("vFlgImprime :"+vFlgImprime);
					 if(vFlgImprime.equals("1") || vFlgImprime.equals("2"))
					 {
					   vPrintArchivo.printLine("  - "+pNombCampo +  "    "+pDesCampo+" ",true);
					   vPrint.printLine("  - "+pNombCampo +  "    "+pDesCampo+" ",true);
					 }
		        }
            }
        }

		vPrintArchivo.printLine(" " + FarmaPRNUtility.alinearIzquierda(" ",65) + " ",true);
		vPrint.printLine("  " + FarmaPRNUtility.alinearIzquierda(" ",65),true);

		VariablesCaja.vFormasPagoImpresion = DBCaja.obtieneFormaPagoPedido();


		 log.info("Imprimiendo Tipo de Cambio");
         log.info("VariablesCaja.vFormasPagoImpresion:"+ VariablesCaja.vFormasPagoImpresion);

         log.info("Imprimiendo Formas de Pago");
         int pos= VariablesCaja.vFormasPagoImpresion.indexOf("Tipo Cambio: ");
         String tcambio,fpago;
         String pCajero = "CJ: " + FarmaVariables.vIdUsu ;


         if (pos != -1)
         {
             tcambio =
                     VariablesCaja.vFormasPagoImpresion.substring(pos);
             fpago =
                     VariablesCaja.vFormasPagoImpresion.substring(0, pos -
                                                                  1);
             //vPrint.printLine(pCajero+" Forma(s) de pago: " + fpago ,true);
             vPrint.printLine(pCajero+" / " + fpago ,true);
             vPrintArchivo.printLine(pCajero+" / " + fpago ,true);
             vPrint.printLine(tcambio ,true);
             vPrintArchivo.printLine(tcambio ,true);
         }
         else {
             //vPrint.printLine(pCajero+" Forma(s) de pago: " + VariablesCaja.vFormasPagoImpresion ,true);
             vPrint.printLine(pCajero+" / " + VariablesCaja.vFormasPagoImpresion ,true);
             vPrintArchivo.printLine(pCajero+" / " + VariablesCaja.vFormasPagoImpresion ,true);
         }


		log.info("Fin al imprimir el Ticket: " + pNumComprobante);
		VariablesCaja.vEstadoSinComprobanteImpreso="N";


		 log.info("Imprimiendo el mensaje final de ticket");
         vPrint.printLine("   No se aceptan devoluciones de dinero." ,true);
         vPrintArchivo.printLine("   No se aceptan devoluciones de dinero.",true);
         //Mensaje JULIO  JMIRANDA 13.11.2009
         vPrint.printLine("  Cambio de mercadería únicamente dentro  " ,true);
         vPrint.printLine("  de las 48 horas siguientes a la compra.",true);
         vPrint.printLine("   Indispensable presentar comprobante",true);
         vPrintArchivo.printLine(" Cambio de mercadería únicamente dentro  " ,true);
         vPrintArchivo.printLine("  de las 48 horas siguientes a la compra.",true);
         vPrintArchivo.printLine("   Indispensable presentar comprobante",true);

         //JCORTEZ 07.09.09 Se obtiene mensaje predeterminado
         String mensaje=DBCaja.obtieneMensajeTicket();
         if(!mensaje.equalsIgnoreCase("N")){
             vPrint.printLine("          "+mensaje,true);
             vPrintArchivo.printLine("          "+mensaje,true);
         }

         if(VariablesCaja.vImprimeFideicomizo){
             String[] lineas = VariablesCaja.vCadenaFideicomizo.trim().split("@");
             String pCadena = "";
             if(lineas.length>0){
                 for(int i=0;i<lineas.length;i++){
                     pCadena += lineas[i] + " ";
                 }
                 //PAra ticket debe ser todo en UNA SOLA LINEA
                 vPrint.printLine(""+pCadena.trim(),true);
                 vPrintArchivo.printLine(""+pCadena.trim(),true);
             }
             else{
             vPrint.printLine(""+VariablesCaja.vCadenaFideicomizo.trim(),true);
             vPrintArchivo.printLine(""+VariablesCaja.vCadenaFideicomizo.trim(),true);
             }
         }

        vPrint.deactivateCondensed();
        vPrint.endPrintService();
 	  	vPrintArchivo.endPrintService();

 	  	log.info("Fin al imprimir la Ticket: " + pNumComprobante);
        VariablesCaja.vEstadoSinComprobanteImpreso = "N";

		//JCORTEZ 16.07.09 Se guarda fecha de impresion por comprobantes
		DBCaja.actualizaFechaImpr(VariablesCaja.vNumPedVta,pNumComprobante,"C");
		log.debug("Guardando fecha impresion cobro..."+pNumComprobante);
		}
		catch(SQLException sql)
                {
		        sql.printStackTrace();
		        VariablesCaja.vEstadoSinComprobanteImpreso="S";
		        System.out.println("Error de BD "+ sql.getMessage());

		          log.info("**** Fecha :"+ pFechaBD);
		          log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
		          log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
		          log.info("**** IP :" + FarmaVariables.vIpPc);
		          log.info("Error al imprimir el Ticket : " + sql.getMessage());
		          log.error(null,sql);
		          //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
		           // enviaErrorCorreoPorDB(sql.toString(),VariablesCaja.vNumPedVta);
		 }
                 catch(Exception e){
		          e.printStackTrace();
		          VariablesCaja.vEstadoSinComprobanteImpreso="S";
		          log.info("**** Fecha :"+ pFechaBD);
		          log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
		          log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
		          log.info("**** IP :" + FarmaVariables.vIpPc);
		          log.info("Error al imprimir la Ticket: "+e);
		          //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
		            //enviaErrorCorreoPorDB(e.toString(),VariablesCaja.vNumPedVta);
		      }


		}

}

private static void imprimeGuia(JDialog   pJDialog,
        ArrayList pDetalleComprobante,
        String    pValTotalNeto,
        String    pNumComprobante,
        String    pValIgvComPago,
        String    pValCopagoCompPago,
        String    pValIgvComCoPago,
        String    pNumCompCoPago,
        String    pRuta,
        boolean   bol,String pImprDatAdic,
        String    pTipoClienteConvenio,
        String    pCodTipoConvenio,
        String    pFechaBD,
        String    pRefTipComp,
        String    pValRedondeoComPago) throws Exception

      {

                String  pNomImpreso = "";
	        String  pDirImpreso = "";

		System.out.println("IMPRIMIR GUIA No : " + pNumComprobante);

		VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
		float subTotal = 0;
		float SumSubTotal = 0;
		float montoIGV = 0;
		double SumMontoIGV = 0;

                //Comentado por FRAMIREZ
		//FarmaPrintService vPrint = new FarmaPrintService(30, VariablesCaja.vRutaImpresora, false);
		//VariablesCaja.vRutaImpresora = "/\\/10.11.1.54/reporte1";
		FarmaPrintService vPrint = new FarmaPrintService(36,VariablesCaja.vRutaImpresora, false);

		//JCORTEZ 16.07.09 Se genera archivo linea por linea
		FarmaPrintServiceTicket vPrintArchivo = new FarmaPrintServiceTicket(666, pRuta, false);
		vPrintArchivo.startPrintService();

		System.out.println("vRutaImpresora : " +VariablesCaja.vRutaImpresora);
		System.out.println("Ruta : " +pRuta);

		//  if ( !vPrint.startPrintService() )  throw new Exception("Error en Impresora. Verifique !!!");
		System.out.println("VariablesCaja.vNumPedVta:" + VariablesCaja.vNumPedVta);
		if (!vPrint.startPrintService())
		{
                    VariablesCaja.vEstadoSinComprobanteImpreso="S";
		    log.info("**** Fecha :"+ pFechaBD);
		    log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
		    log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
		    log.info("**** IP :" + FarmaVariables.vIpPc);
		    log.info("ERROR DE IMPRESORA : No se pudo imprimir la boleta");
		}
    	else {
		try {

		vPrint.activateCondensed();

		if(VariablesPtoVenta.vIndDirMatriz){
			vPrint.printLine(FarmaPRNUtility.llenarBlancos(30) +" ",true);
			vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(30) +" ",true);
			vPrint.printLine(FarmaPRNUtility.llenarBlancos(30) +" ",true);
			vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(30) +" ",true);
			vPrint.printLine(FarmaPRNUtility.llenarBlancos(30) +"Dom.Fiscal: "+ VariablesPtoVenta.vDireccionMatriz ,true);
			vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(30) +"Dom.Fiscal: "+ VariablesPtoVenta.vDireccionMatriz ,true);

		}
		//JMIRANDA 22.08.2011 Cambio para verificar si imprime
		if(UtilityVentas.getIndImprimeCorrelativo())
		{
		 vPrint.printLine(FarmaPRNUtility.llenarBlancos(11) + pFechaBD + "   CORR." + VariablesCaja.vNumPedVta,true);
		 vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(11) + pFechaBD + "   CORR." + VariablesCaja.vNumPedVta,true);
		}
		else
		{
		 vPrint.printLine(FarmaPRNUtility.llenarBlancos(11) + " " ,true);
		 vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(11) + " " ,true);
		}

		vPrint.printLine(FarmaPRNUtility.llenarBlancos(11) + FarmaPRNUtility.alinearIzquierda(pNomImpreso.trim(),60),true);
		vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(11) + FarmaPRNUtility.alinearIzquierda(pNomImpreso.trim(),60),true);
  	        vPrint.printLine(FarmaPRNUtility.llenarBlancos(11) + FarmaPRNUtility.alinearIzquierda(pNomImpreso.trim(),60),true);
		vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(11) + FarmaPRNUtility.alinearIzquierda(pNomImpreso.trim(),60),true);
		vPrint.printLine(FarmaPRNUtility.llenarBlancos(11) + FarmaPRNUtility.alinearIzquierda(pFechaBD,60) + "   No. " + pNumComprobante.substring(0,3) + "-" + pNumComprobante.substring(3,10),true);
		vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(11) + FarmaPRNUtility.alinearIzquierda(pFechaBD,60) + "   No. " + pNumComprobante.substring(0,3) + "-" + pNumComprobante.substring(3,10),true);

		vPrint.printLine(FarmaPRNUtility.llenarBlancos(11) +VariablesConvenioBTLMF.vInstitucion.trim(),true);
		vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(11) +VariablesConvenioBTLMF.vInstitucion.trim(),true);
		vPrint.printLine(FarmaPRNUtility.llenarBlancos(11) +VariablesConvenioBTLMF.vRuc.trim(),true);
                vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(11) + VariablesConvenioBTLMF.vRuc.trim(),true);

                vPrint.printLine(FarmaPRNUtility.llenarBlancos(11) + FarmaPRNUtility.alinearIzquierda(VariablesConvenioBTLMF.vDireccion.trim(),60),true);
	        vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(11) + FarmaPRNUtility.alinearIzquierda(VariablesConvenioBTLMF.vDireccion.trim(),60),true);

		vPrint.printLine(" ",true);
		vPrintArchivo.printLine(" ",true);
		vPrint.printLine(" ",true);
		vPrintArchivo.printLine(" ",true);
		vPrint.printLine(" ",true);
		vPrintArchivo.printLine(" ",true);
		vPrint.printLine(" ",true);
		vPrintArchivo.printLine(" ",true);


		int linea = 0;
		for (int i=0; i<pDetalleComprobante.size(); i++)
		{
		    //Agregado por DVELIZ 13.10.08

			String punitario  = " ";
			String valor  = " ";

			String colSubTotal = " ";
			if (VariablesConvenioBTLMF.vFlgImprimeImportes.equals("1"))
			{
				valor = ((String)((ArrayList)pDetalleComprobante.get(i)).get(16)).toString().trim();
				System.out.println("valor 1:"+valor);
				if(valor.equals("0.000")) valor = " ";
				//fin DVELIZ
				System.out.println("Deta "+ (ArrayList)pDetalleComprobante.get(i) );
				System.out.println("valor 2:"+valor);
				colSubTotal = (String)((ArrayList)pDetalleComprobante.get(i)).get(5);
				punitario = (String)((ArrayList)pDetalleComprobante.get(i)).get(4).toString().trim();
			}

			vPrint.printLine("" +
		        FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(6)).trim(),6) + " " +
			FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim(),11) + "   " +
			FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),27) + " " +
			FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),11) + "  " +
			FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),16) + "  " +

			FarmaPRNUtility.alinearDerecha(punitario,10) + " " +
			//Agregado por DVELIZ 10.10.08

			FarmaPRNUtility.alinearDerecha(valor,8) + "" +
			FarmaPRNUtility.alinearDerecha(colSubTotal.trim(),10),true);



			vPrintArchivo.printLine("" +
		        FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(6)).trim(),6) + " " +
			FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim(),11) + "   " +
			FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),27) + " " +
			FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),11) + "  " +
			FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),16) + "  " +
			FarmaPRNUtility.alinearDerecha(punitario,10) + " " +
			//Agregado por DVELIZ 10.10.08
			FarmaPRNUtility.alinearDerecha(valor,8) + "" +
			FarmaPRNUtility.alinearDerecha(colSubTotal.trim(),10),true);

			linea += 1;
			if (VariablesConvenioBTLMF.vFlgImprimeImportes.equals("1"))
			{
				subTotal =new Double(FarmaUtility.getDecimalNumber(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim())).floatValue();
				System.out.println("SubTotal:"+subTotal);
				SumSubTotal = SumSubTotal + subTotal;
				montoIGV =new Double(FarmaUtility.getDecimalNumber(((String)((ArrayList)pDetalleComprobante.get(i)).get(18)).trim())).floatValue();
				SumMontoIGV= SumMontoIGV +montoIGV;

			}
		}


		System.out.println("SumSubTotal:"+SumSubTotal);

		       //*************************************INFORMACION DEL CONVENIO***********************************************//

		    double porcCopago = 0;
		    double ValCopagoCompPagoSinIGV = 0;

                    String vRefTipComp = "";

                    if (pRefTipComp.equals(ConstantsVentas.TIPO_COMP_BOLETA)) vRefTipComp = "BOL";
                    if (pRefTipComp.equals(ConstantsVentas.TIPO_COMP_FACTURA)) vRefTipComp = "FAC";
                    if (pRefTipComp.equals(ConstantsVentas.TIPO_COMP_GUIA)) vRefTipComp = "GUIA";
                    if (pRefTipComp.equals(ConstantsVentas.TIPO_COMP_TICKET)) vRefTipComp = "TKB";



		      if (VariablesConvenioBTLMF.vFlgImprimeImportes.equals("1"))
			  {
		            porcCopago  = Math.round((FarmaUtility.getDecimalNumber(pValCopagoCompPago)/(FarmaUtility.getDecimalNumber(pValTotalNeto)+FarmaUtility.getDecimalNumber(pValCopagoCompPago)))*100);
		            SumMontoIGV = SumMontoIGV-((SumMontoIGV*porcCopago)/100);
		            ValCopagoCompPagoSinIGV  =  ((SumSubTotal*porcCopago)/100);



					vPrint.printLine("      " + FarmaPRNUtility.alinearIzquierda(" ",65) + " " +
					"    Sub Total   S/. " + FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(SumSubTotal),10),true);
					vPrintArchivo.printLine("     " + FarmaPRNUtility.alinearIzquierda(" ",65) + " " +
					"    Sub Total   S/. " + FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(SumSubTotal),10),true);

			  }
			  if(pCodTipoConvenio.equals("1"))
			  {
				if (VariablesConvenioBTLMF.vFlgImprimeImportes.equals("1"))
				{
				        
				        double pValTotalNetoRedondeo  = FarmaUtility.getDecimalNumber(pValTotalNeto)+FarmaUtility.getDecimalNumber(pValRedondeoComPago);
                                        vPrint.printLine(" SON:" + FarmaPRNUtility.alinearIzquierda(FarmaPRNUtility.montoEnLetras(pValTotalNetoRedondeo).trim(),65) + "" +
					"  Coaseguro("+FarmaUtility.formatNumber(porcCopago,0)+"%)    S/. " + FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(ValCopagoCompPagoSinIGV),10)+"",true);
				        vPrint.printLine(FarmaPRNUtility.alinearIzquierda("  ",65) + "         " +
					"       ---------------------",true);
					vPrintArchivo.printLine(" SON:" + FarmaPRNUtility.alinearIzquierda(FarmaPRNUtility.montoEnLetras(pValTotalNetoRedondeo).trim(),65)+" "+ "" +
					"  Coaseguro("+FarmaUtility.formatNumber(porcCopago,"")+"%)    S/. " + FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(ValCopagoCompPagoSinIGV),10),true);
					vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda(" ",65) + "          " + " " +
					"       ---------------------",true);
					vPrint.printLine(FarmaPRNUtility.alinearIzquierda("  Institución: " + VariablesConvenioBTLMF.vInstitucion.trim().toUpperCase()+"  ("+FarmaUtility.formatNumber(100-porcCopago,"")+")%", 65)+"                       S/. " + FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(SumSubTotal-ValCopagoCompPagoSinIGV),10),true);
					vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda("  Institución: " + VariablesConvenioBTLMF.vInstitucion.trim().toUpperCase()+"  ("+FarmaUtility.formatNumber(100-porcCopago,"")+")%",65)+"                             S/. " + FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(SumSubTotal-ValCopagoCompPagoSinIGV),10),true);

				        vPrint.printLine(FarmaPRNUtility.alinearIzquierda("  Convenio: " + VariablesConvenioBTLMF.vNomConvenio.trim().toUpperCase(),65) +"     " +  " " +
					"          IGV    S/. " + FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(SumMontoIGV),10),true);
					vPrint.printLine(FarmaPRNUtility.alinearIzquierda("  Beneficiario: " +VariablesConvenioBTLMF.vNomCliente,65) +"     " +  " " +
					"          ---------------------",true);
					vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda("  Convenio: " + VariablesConvenioBTLMF.vNomConvenio.trim().toUpperCase(),65) +"     " +  " " +
					"          IGV    S/. " + FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(SumMontoIGV),10),true);
					vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda("  Beneficiario: " +VariablesConvenioBTLMF.vNomCliente,65) +"     " +  " " +
									"          ---------------------",true);     
                                        
				        vPrint.printLine("  #REF: "+vRefTipComp+" " + FarmaPRNUtility.alinearIzquierda(pNumCompCoPago+ "("+FarmaUtility.formatNumber(porcCopago,"")+")"+" - " + "S/."+pValCopagoCompPago,65) + " " +
				        " Total    S/. " + FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(pValTotalNetoRedondeo),10),true);
				        vPrintArchivo.printLine("  #REF: "+vRefTipComp+" " + FarmaPRNUtility.alinearIzquierda(pNumCompCoPago+ "("+FarmaUtility.formatNumber(porcCopago,"")+")"+" - " + "S/."+pValCopagoCompPago,65) + " " +
				        " Total    S/. " + FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(pValTotalNetoRedondeo),10),true);

				}
				else
				{
				    vPrint.printLine(FarmaPRNUtility.alinearIzquierda("  Institución: " + VariablesConvenioBTLMF.vInstitucion.trim().toUpperCase()+"  ("+FarmaUtility.formatNumber(100-porcCopago,"")+")%", 65)+"                      ",true);
				    vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda("  Institución: " + VariablesConvenioBTLMF.vInstitucion.trim().toUpperCase()+"  ("+FarmaUtility.formatNumber(100-porcCopago,"")+")%",65)+"                            ",true);
				    vPrint.printLine(FarmaPRNUtility.alinearIzquierda("  Convenio: " + VariablesConvenioBTLMF.vNomConvenio.trim().toUpperCase(),65) +"     " +  " " +
				    "          ",true);
				    vPrint.printLine(FarmaPRNUtility.alinearIzquierda("  Beneficiario: " +VariablesConvenioBTLMF.vNomCliente,65) +"     " +  " " +
				    "          ",true);
				    vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda("  Convenio: " + VariablesConvenioBTLMF.vNomConvenio.trim().toUpperCase(),65) +"     " +  " " +
				    "          ",true);
				    vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda("  Beneficiario: " +VariablesConvenioBTLMF.vNomCliente,65) +"     " +  " " +"          ",true);

				    vPrint.printLine("  #REF: "+vRefTipComp+" " + FarmaPRNUtility.alinearIzquierda(pNumCompCoPago,65),true);
				    vPrintArchivo.printLine("  #REF: "+vRefTipComp+" " + FarmaPRNUtility.alinearIzquierda(pNumCompCoPago,65),true);
				}
			}
			else
			{

			    if (VariablesConvenioBTLMF.vFlgImprimeImportes.equals("1"))
			    {
			    vPrint.printLine(FarmaPRNUtility.alinearIzquierda("  Institución: " + VariablesConvenioBTLMF.vInstitucion.trim().toUpperCase(), 65),true);
			    vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda("  Institución: " + VariablesConvenioBTLMF.vInstitucion.trim().toUpperCase(),65)+"                          ",true);

			    vPrint.printLine(FarmaPRNUtility.alinearIzquierda("  Convenio: " + VariablesConvenioBTLMF.vNomConvenio.trim().toUpperCase(),65) +"     " +  " " +
			    "          IGV    S/. " + FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(SumMontoIGV),10),true);
			    vPrint.printLine(FarmaPRNUtility.alinearIzquierda("  Beneficiario: " +VariablesConvenioBTLMF.vNomCliente,65) +"     " +  " " +
			    "          ---------------------",true);
			    vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda("  Convenio: " + VariablesConvenioBTLMF.vNomConvenio.trim().toUpperCase(),65) +"     " +  " " +
			    "          IGV    S/. " + FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(SumMontoIGV),10),true);
			    vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda("  Beneficiario: " +VariablesConvenioBTLMF.vNomCliente,65) +"     " +  " " +
			    "          ---------------------",true);
			    
                            double pValTotalNetoRedondeo  = FarmaUtility.getDecimalNumber(pValTotalNeto)+FarmaUtility.getDecimalNumber(pValRedondeoComPago);
                            
                            vPrint.printLine("  " + FarmaPRNUtility.alinearIzquierda(" ",65) + " " +  "        Total    S/. " + FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(pValTotalNetoRedondeo),10),true);
			    vPrintArchivo.printLine("   " + FarmaPRNUtility.alinearIzquierda(" ",65) + " " +"        Total    S/. " + FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(pValTotalNetoRedondeo),10),true);
                            }
                            else
                            {
                                vPrint.printLine(FarmaPRNUtility.alinearIzquierda("  Institución: " + VariablesConvenioBTLMF.vInstitucion.trim().toUpperCase(), 65),true);
                                vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda("  Institución: " + VariablesConvenioBTLMF.vInstitucion.trim().toUpperCase(),65)+"                          ",true);

                                vPrint.printLine(FarmaPRNUtility.alinearIzquierda("  Convenio: " + VariablesConvenioBTLMF.vNomConvenio.trim().toUpperCase(),65) +"     " +  " " +
                                " ",true);
                                vPrint.printLine(FarmaPRNUtility.alinearIzquierda("  Beneficiario: " +VariablesConvenioBTLMF.vNomCliente,65) +"     " +  " " +
                                "          ",true);
                                vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda("  Convenio: " + VariablesConvenioBTLMF.vNomConvenio.trim().toUpperCase(),65) +"     " +  " " +
                                "                     ",true);
                                vPrintArchivo.printLine(FarmaPRNUtility.alinearIzquierda("  Beneficiario: " +VariablesConvenioBTLMF.vNomCliente,65) +"     " +  " " +
                                                                "          ",true);


                            }


			}


  	    vPrintArchivo.printLine(" ",true);
		   vPrint.printLine("  ",true);

		vPrint.printLine(" REDO: " + pValRedondeoComPago +
		" CAJERO: " + VariablesCaja.vNomCajeroImpreso + " " + VariablesCaja.vApePatCajeroImpreso + " " +
		" CAJA: " + VariablesCaja.vNumCajaImpreso +
		" TURNO: " + VariablesCaja.vNumTurnoCajaImpreso +
		" VEND: " + VariablesCaja.vNomVendedorImpreso + " " +VariablesCaja.vApePatVendedorImpreso  ,true);
		vPrintArchivo.printLine(" REDO: " + pValRedondeoComPago +
		" CAJERO: " + VariablesCaja.vNomCajeroImpreso + " " + VariablesCaja.vApePatCajeroImpreso + " " +
		" CAJA: " + VariablesCaja.vNumCajaImpreso +
		" TURNO: " + VariablesCaja.vNumTurnoCajaImpreso +
		" VEND: " + VariablesCaja.vNomVendedorImpreso + " " +VariablesCaja.vApePatVendedorImpreso  ,true);


		if (pImprDatAdic.equals("1"))
		{

		  if (VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic != null && VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.size() > 0)
		  {
			vPrintArchivo.printLine("  Datos Adicionales",true);
			vPrint.printLine("  Datos Adicionales",true);

			int nroDatosAdi = VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.size();

		   // if (VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.size() > 4 )
		   // {
		   // 	nroDatosAdi = 4;
		  // }

		  for (int j = 0; j < nroDatosAdi; j++)
	          {
                                        Map datosAdicConv = (Map)VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.get(j);

                                        String pCodigoCampo     = (String)datosAdicConv.get("COD_CAMPO");
                                        String pNombCampo     = (String)datosAdicConv.get("NOMBRE_CAMPO");

                                        String pDesCampo    = (String)datosAdicConv.get("DESCRIPCION_CAMPO");
                                        String vFlgImprime   = (String)datosAdicConv.get("FLG_IMPRIME");

                                        log.debug("pDesCampo   :"+pCodigoCampo);
                                        log.debug("pNombCampo  :"+pNombCampo);
                                        log.debug("vFlgImprime :"+vFlgImprime);

                         if (!pCodigoCampo.trim().equalsIgnoreCase(ConstantsConvenioBTLMF.COD_DATO_CONV_BENIFICIARIO))
                         {
                             if (pCodigoCampo.equals(ConstantsConvenioBTLMF.COD_NOMB_TITULAR) || pCodigoCampo.equals(ConstantsConvenioBTLMF.COD_NRO_ATENCION)
                                 || pCodigoCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_MEDICO)
                                )
                             {
                                 vPrintArchivo.printLine("  - "+pNombCampo +  "    "+pDesCampo+" ",true);
                                 vPrint.printLine("  - "+pNombCampo +  "    "+pDesCampo+" ",true);
                             }
                         }
                   }
		  }
		}
		System.out.println("Nro de Lineas::"+vPrint.getActualLine());

		vPrint.deactivateCondensed();
		vPrint.endPrintService();
		vPrintArchivo.endPrintService();

		log.info("Fin al imprimir la GUIA: " + pNumComprobante);
		VariablesCaja.vEstadoSinComprobanteImpreso="N";

		//JCORTEZ 16.07.09 Se guarda fecha de impresion por comprobantes
		DBCaja.actualizaFechaImpr(VariablesCaja.vNumPedVta,pNumComprobante,"C");
		log.debug("Guardando fecha impresion cobro..."+pNumComprobante);
		}
		                catch(SQLException sql)
		                      {
		                        sql.printStackTrace();
		                        VariablesCaja.vEstadoSinComprobanteImpreso="S";
		                        System.out.println("Error de BD "+ sql.getMessage());

		                          log.info("**** Fecha :"+ pFechaBD);
		                          log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
		                          log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
		                          log.info("**** IP :" + FarmaVariables.vIpPc);
		                          log.info("Error al imprimir la Guia : " + sql.getMessage());
		                          log.error(null,sql);
		                          //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
		                           // enviaErrorCorreoPorDB(sql.toString(),VariablesCaja.vNumPedVta);
		                      }

		                        catch(Exception e){
		                          e.printStackTrace();
		                          VariablesCaja.vEstadoSinComprobanteImpreso="S";
		                          log.info("**** Fecha :"+ pFechaBD);
		                          log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
		                          log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
		                          log.info("**** IP :" + FarmaVariables.vIpPc);
		                          log.info("Error al imprimir la Guia: "+e);
		                          //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
		                            //enviaErrorCorreoPorDB(e.toString(),VariablesCaja.vNumPedVta);
		                      }


		            }

}


    private static void imprimeBoleta(JDialog   pJDialog,
            ArrayList pDetalleComprobante,
            String    pValTotalNeto,
            String    pNumComprobante,
            String    pValIgvComPago,
            String    pValCopagoCompPago,
            String    pValIgvComCoPago,
            String    pNumCompCoPago,
            String    pRuta,
            boolean   bol,
            String    pImprDatAdic,
            String    pTipoClienteConvenio,
            String    pCodTipoConvenio,
            String    pFechaBD,
            String    pValRedondeoComPago                          
             ) throws Exception {


    	    String pNomImpreso = "";
    	    String pDirImpreso = "";

			System.out.println("IMPRIMIR BOLETA No : " + pNumComprobante);

			VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
			float subTotal = 0;
			float SumSubTotal = 0;

			FarmaPrintService vPrint = new FarmaPrintService(24, VariablesCaja.vRutaImpresora, false);

			//JCORTEZ 16.07.09 Se genera archivo linea por linea
			FarmaPrintServiceTicket vPrintArchivo = new FarmaPrintServiceTicket(666, pRuta, false);
			vPrintArchivo.startPrintService();

			System.out.println("Ruta : " +pRuta);
			//  if ( !vPrint.startPrintService() )  throw new Exception("Error en Impresora. Verifique !!!");
			System.out.println("VariablesCaja.vNumPedVta:" + VariablesCaja.vNumPedVta);
			if (!vPrint.startPrintService())
			{
	            VariablesCaja.vEstadoSinComprobanteImpreso="S";
				log.info("**** Fecha :"+ pFechaBD);
				log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
				log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
				log.info("**** IP :" + FarmaVariables.vIpPc);
				log.info("ERROR DE IMPRESORA : No se pudo imprimir la boleta");
			}

			else {
			try {
			vPrint.activateCondensed();
			if(VariablesPtoVenta.vIndDirMatriz){
			vPrint.printLine(FarmaPRNUtility.llenarBlancos(30) +"Dom.Fiscal: "+ VariablesPtoVenta.vDireccionMatriz ,true);
			vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(30) +"Dom.Fiscal: "+ VariablesPtoVenta.vDireccionMatriz ,true);
			}
			//JMIRANDA 22.08.2011 Cambio para verificar si imprime
			if(UtilityVentas.getIndImprimeCorrelativo()){
			vPrint.printLine(FarmaPRNUtility.llenarBlancos(11) + pFechaBD + "   CORR." + VariablesCaja.vNumPedVta,true);
			vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(11) + pFechaBD + "   CORR." + VariablesCaja.vNumPedVta,true);
			}
			else{
			vPrint.printLine(FarmaPRNUtility.llenarBlancos(11) + pFechaBD ,true);
			vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(11) + pFechaBD ,true);
			}
			vPrint.printLine(FarmaPRNUtility.llenarBlancos(11) + FarmaPRNUtility.alinearIzquierda(pNomImpreso.trim(),60),true);
			vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(11) + FarmaPRNUtility.alinearIzquierda(pNomImpreso.trim(),60),true);

			vPrint.printLine(FarmaPRNUtility.llenarBlancos(11) + FarmaPRNUtility.alinearIzquierda(pDirImpreso.trim(),60) + "   No. " + pNumComprobante.substring(0,3) + "-" + pNumComprobante.substring(3,10),true);
			vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(11) + FarmaPRNUtility.alinearIzquierda(pDirImpreso.trim(),60) + "   No. " + pNumComprobante.substring(0,3) + "-" + pNumComprobante.substring(3,10),true);
			vPrint.printLine(" ",true);
			vPrintArchivo.printLine(" ",true);
			vPrint.printLine(" ",true);
			vPrintArchivo.printLine(" ",true);
			int linea = 0;


			for (int i=0; i<pDetalleComprobante.size(); i++)
			{
			    //Agregado por DVELIZ 13.10.08
				String valor = ((String)((ArrayList)pDetalleComprobante.get(i)).get(16)).toString().trim();
				System.out.println("valor 1:"+valor);
				if(valor.equals("0.000")) valor = " ";
				//fin DVELIZ
				System.out.println("Deta "+ (ArrayList)pDetalleComprobante.get(i) );
				System.out.println("valor 2:"+valor);
				vPrint.printLine("" +
				FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim(),11) + "   " +
				FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),27) + " " +
				FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),11) + "  " +
				FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),16) + "  " +
				FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(4)).trim(),10) + " " +
				//Agregado por DVELIZ 10.10.08
				FarmaPRNUtility.alinearDerecha(valor,8) + "" +
				FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),10),true);

				vPrintArchivo.printLine("" +
				FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim(),11) + "   " +
				FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),27) + " " +
				FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),11) + "  " +
				FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),16) + "  " +
				FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(4)).trim(),10) + " " +
				//Agregado por DVELIZ 10.10.08
				FarmaPRNUtility.alinearDerecha(valor,8) + "" +
				FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),10),true);

				System.out.println("SubTotal String:::"+((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim());
				linea += 1;
				subTotal =new Double(FarmaUtility.getDecimalNumber(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim())).floatValue();

				System.out.println("SubTotal:"+subTotal);


				SumSubTotal = SumSubTotal + subTotal;
			}


			System.out.println("SumSubTotal:"+SumSubTotal);

			//*************************************INFORMACION DEL CONVENIO***********************************************//

        	double igv =FarmaUtility.getDecimalNumber(pValIgvComPago)+FarmaUtility.getDecimalNumber(pValIgvComCoPago);


            double porcCopago = Math.round((FarmaUtility.getDecimalNumber(pValCopagoCompPago)/(FarmaUtility.getDecimalNumber(pValTotalNeto)+FarmaUtility.getDecimalNumber(pValCopagoCompPago)))*100);

          	vPrint.printLine("      " + FarmaPRNUtility.alinearIzquierda(" ",65) + " " +
			"    Sub Total   S/. " + FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(SumSubTotal),10),true);
			vPrintArchivo.printLine("      " + FarmaPRNUtility.alinearIzquierda(" ",65) + " " +
			"    Sub Total   S/. " + FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(SumSubTotal),10),true);

			if(pCodTipoConvenio.equals("1"))
			{
				vPrint.printLine("    " + FarmaPRNUtility.alinearIzquierda(" ",65) + " " +
				"  CoPago("+FarmaUtility.formatNumber(porcCopago,0)+"%)    S/. " + FarmaPRNUtility.alinearDerecha(pValCopagoCompPago,10),true);
				vPrint.printLine("     " + FarmaPRNUtility.alinearIzquierda(" ",65) + " " +
						"          ---------------------",true);
				vPrintArchivo.printLine("    " + FarmaPRNUtility.alinearIzquierda(" ",65) + " " +
				"  CoPago("+FarmaUtility.formatNumber(porcCopago,"")+"%)    S/. " + FarmaPRNUtility.alinearDerecha(pValCopagoCompPago,10),true);
				vPrintArchivo.printLine("     " + FarmaPRNUtility.alinearIzquierda(" ",65) + " " +
						"          ---------------------",true);
			}

			vPrint.printLine("      " + FarmaPRNUtility.alinearIzquierda(" ",65) + " " +
			"                    " + FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(SumSubTotal-FarmaUtility.getDecimalNumber(pValCopagoCompPago)),10),true);
			vPrintArchivo.printLine("      " + FarmaPRNUtility.alinearIzquierda(" ",65) + " " +
			"                    " + FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(SumSubTotal-FarmaUtility.getDecimalNumber(pValCopagoCompPago)),10),true);



			    vPrint.printLine("     " + FarmaPRNUtility.alinearIzquierda(" ",65) + " " +
					"          IGV    S/. " + FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(igv),10),true);
					vPrint.printLine("     " + FarmaPRNUtility.alinearIzquierda(" ",65) + " " +
							"          ---------------------",true);
					vPrintArchivo.printLine("     " + FarmaPRNUtility.alinearIzquierda(" ",65) + " " +
					"          IGV    S/. " + FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(igv),10),true);
					vPrintArchivo.printLine("     " + FarmaPRNUtility.alinearIzquierda(" ",65) + " " +
							"          ---------------------",true);

                        double pValTotalNetoRedondeo  = FarmaUtility.getDecimalNumber(pValTotalNeto)+FarmaUtility.getDecimalNumber(pValRedondeoComPago);
                            
			vPrint.printLine(" SON:" + FarmaPRNUtility.alinearIzquierda(FarmaPRNUtility.montoEnLetras(pValTotalNetoRedondeo).trim(),65) + " " +
			"          Total    S/. " + FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(pValTotalNetoRedondeo),10),true);
			vPrintArchivo.printLine(" SON:" + FarmaPRNUtility.alinearIzquierda(FarmaPRNUtility.montoEnLetras(pValTotalNetoRedondeo).trim(),65) + " " +
			"          Total    S/. " + FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(pValTotalNetoRedondeo),10),true);


			vPrintArchivo.printLine(" " + FarmaPRNUtility.alinearIzquierda(" ",65) + " ",true);
			vPrint.printLine("  " + FarmaPRNUtility.alinearIzquierda(" ",65),true);

			vPrintArchivo.printLine("  Convenio: " + FarmaPRNUtility.alinearIzquierda(VariablesConvenioBTLMF.vNomConvenio.toUpperCase(),65) + " ",true);
			vPrint.printLine("  Convenio: " + FarmaPRNUtility.alinearIzquierda(VariablesConvenioBTLMF.vNomConvenio.toUpperCase(),65),true);
			vPrintArchivo.printLine("  Beneficiario: " + FarmaPRNUtility.alinearIzquierda(VariablesConvenioBTLMF.vNomCliente,65) + " ",true);
			vPrint.printLine("  Beneficiario: " + FarmaPRNUtility.alinearIzquierda(VariablesConvenioBTLMF.vNomCliente,65),true);

			if(pCodTipoConvenio.equals("1"))
			{
			  vPrintArchivo.printLine("  Documento de Referencia Nro: "+pNumCompCoPago+" " +  " ",true);
			  vPrint.printLine("  Documento de Referencia Nro "+pNumCompCoPago+": " +  " ",true);
			  vPrintArchivo.printLine("  Doc refe de la Empresa Monto:S/." + pValCopagoCompPago +" y ("+FarmaUtility.formatNumber(porcCopago,"")+"%)",true);
			  vPrint.printLine("  Doc refe de la Empresa Monto:S/." + pValCopagoCompPago +" y ("+FarmaUtility.formatNumber(porcCopago,"")+"%)",true);
			}
			if (pImprDatAdic.equals("1"))
			{
				if (VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic != null && VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.size() > 0)
				{
					vPrintArchivo.printLine("  Datos Adicionales",true);
					vPrint.printLine("  Datos Adicionales",true);
					for (int j = 0; j < VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.size(); j++)
			        {
						Map datosAdicConv = (Map)VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.get(j);

						String pNombCampo     = (String)datosAdicConv.get("NOMBRE_CAMPO");
						String pDesCampo    = (String)datosAdicConv.get("DESCRIPCION_CAMPO");
						String vFlgImprime   = (String)datosAdicConv.get("FLG_IMPRIME");

						log.debug("pDesCampo   :"+pNombCampo);
						log.debug("pNombCampo  :"+pNombCampo);
						log.debug("vFlgImprime :"+vFlgImprime);

						 if(vFlgImprime.equals("1") || vFlgImprime.equals("2"))
						 {
						   vPrintArchivo.printLine("  - "+pNombCampo +  "    "+pDesCampo+" ",true);
						   vPrint.printLine("  - "+pNombCampo +  "    "+pDesCampo+" ",true);
						 }
					 }
			    }
			}

			vPrintArchivo.printLine(" " + FarmaPRNUtility.alinearIzquierda(" ",65) + " ",true);
			vPrint.printLine("  " + FarmaPRNUtility.alinearIzquierda(" ",65),true);

			vPrint.printLine(" REDO: " + pValRedondeoComPago +
			" CAJERO: " + VariablesCaja.vNomCajeroImpreso + " " + VariablesCaja.vApePatCajeroImpreso + " " +
			" CAJA: " + VariablesCaja.vNumCajaImpreso +
			" TURNO: " + VariablesCaja.vNumTurnoCajaImpreso +
			" VEND: " + VariablesCaja.vNomVendedorImpreso + " " +VariablesCaja.vApePatVendedorImpreso  ,true);
			vPrintArchivo.printLine(" REDO: " + pValRedondeoComPago +
			" CAJERO: " + VariablesCaja.vNomCajeroImpreso + " " + VariablesCaja.vApePatCajeroImpreso + " " +
			" CAJA: " + VariablesCaja.vNumCajaImpreso +
			" TURNO: " + VariablesCaja.vNumTurnoCajaImpreso +
			" VEND: " + VariablesCaja.vNomVendedorImpreso + " " +VariablesCaja.vApePatVendedorImpreso  ,true);

			vPrint.deactivateCondensed();
			vPrint.endPrintService();
			vPrintArchivo.endPrintService();

			log.info("Fin al imprimir la boleta: " + pNumComprobante);
			VariablesCaja.vEstadoSinComprobanteImpreso="N";

			//JCORTEZ 16.07.09 Se guarda fecha de impresion por comprobantes
			DBCaja.actualizaFechaImpr(VariablesCaja.vNumPedVta,pNumComprobante,"C");
			log.debug("Guardando fecha impresion cobro..."+pNumComprobante);
			}
			                catch(SQLException sql)
			                      {
			                        //sql.printStackTrace();
			                        VariablesCaja.vEstadoSinComprobanteImpreso="S";
			                        System.out.println("Error de BD "+ sql.getMessage());

			                          log.info("**** Fecha :"+ pFechaBD);
			                          log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
			                          log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
			                          log.info("**** IP :" + FarmaVariables.vIpPc);
			                          log.info("Error al imprimir la boleta : " + sql.getMessage());
			                          log.error(null,sql);
			                          //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
			                           // enviaErrorCorreoPorDB(sql.toString(),VariablesCaja.vNumPedVta);
			                      }

			                        catch(Exception e){
			                          VariablesCaja.vEstadoSinComprobanteImpreso="S";
			                          log.info("**** Fecha :"+ pFechaBD);
			                          log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
			                          log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
			                          log.info("**** IP :" + FarmaVariables.vIpPc);
			                          log.info("Error al imprimir la boleta: "+e);
			                          //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
			                            //enviaErrorCorreoPorDB(e.toString(),VariablesCaja.vNumPedVta);
			                      }


			            }

}

    private static String obtieneRutaImpresora(String pTipCompPag) throws SQLException
    {
      return DBConvenioBTLMF.obtieneRutaImpresoraVenta(pTipCompPag);
    }



    public static boolean listaDatosConvenioAdic(JDialog pJDialog, Object pObjectFocus)
    {
      VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic = new ArrayList();
      boolean  valor = true;
      long tmpT1,tmpT2;
      tmpT1 = System.currentTimeMillis();
      List lista = new ArrayList();
      try
      {
    	lista = DBConvenioBTLMF.listaDatosConvenioAdicXpedido();

    	VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic = lista;
        if(lista.size() == 0)
        {
      	  FarmaUtility.liberarTransaccion();
          FarmaUtility.showMessage(pJDialog,"No se pudo determinar el listado de datos adicionales del convenio. Verifique!!!.",pObjectFocus);
          valor = false;
        }
        System.err.println("VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic : " + VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.size());
        valor = true;
      } catch(SQLException sql)
      {
        FarmaUtility.liberarTransaccion();
        FarmaUtility.showMessage(pJDialog,"Error al obtener los datos adicionales del convenio.",pObjectFocus);
        log.info("Error al obtener los datos adicionales del convenio");
        log.error(null,sql);
        valor =false;
        UtilityCaja.enviaErrorCorreoPorDB(sql.toString(),null);
      }

      tmpT2 = System.currentTimeMillis();
      log.debug("Tiempo 4: Det.Comp Pago:"+(tmpT2 - tmpT1)+" milisegundos");

      return valor;
    }


    public static Map obtieneConvenioXpedido(String nroPedido,JDialog pDialogo)
    {
		Map pedidoConvenio = null;

		try
		 {
			pedidoConvenio = DBConvenioBTLMF.obtenerConvenioXPedido(VariablesCaja.vNumPedVta_Anul);

	  	 }
		catch (SQLException sqlException)
		{
		 sqlException.printStackTrace();
		 FarmaUtility.showMessage(pDialogo, "Error al obtener los datos del pedido convenio",pDialogo);
		}
		return pedidoConvenio;
    }

    public static String esDiaVencimientoReceta(JDialog pDialogo,
            Object pObjeto,String fechVencimietnoRecta)
    {
			String res= null;

			try
			{
				res = DBConvenioBTLMF.esDiaVigenciaReceta(fechVencimietnoRecta);

			} catch (SQLException sql)
			{
				sql.printStackTrace();
				FarmaUtility.showMessage(pDialogo,
				"Error al validar la fecha de vigencia de la receta" +
				sql.getMessage(), pObjeto);
			}

		return res;
    }


    public static String obtieneTipoConvenio(JDialog pDialogo,
            Object pObjeto,String pCodConvenio)
    {
			String resTipoConvenio= null;

			try
			{
				resTipoConvenio = DBConvenioBTLMF.geTipoConvenio(pCodConvenio);

			} catch (SQLException sql)
			{
				sql.printStackTrace();
				FarmaUtility.showMessage(pDialogo,
				"Error al obtener el tipo Convenio" +
				sql.getMessage(), pObjeto);
			}

		return resTipoConvenio;
    }

    public static Map obtieneMsgCompPagoImpr(JDialog pDialogo,
            Object pObjeto)
    {
			Map resTipoConvenio= null;

			try
			{
				resTipoConvenio = DBConvenioBTLMF.obtieneMsgCompPagoImpr();

			} catch (SQLException sql)
			{
				sql.printStackTrace();
				FarmaUtility.showMessage(pDialogo,
				"Error al obtener el mensaje de Impresion" +
				sql.getMessage(), pObjeto);
			}

		return resTipoConvenio;
    }

    public static boolean existeProductosConvenio(JDialog pDialogo,
            Object pObjeto)
    {
			boolean  resPu = false;
			String  res = "";

			try
			{
				res = DBConvenioBTLMF.existeProdConvenio();

				if(res.equalsIgnoreCase("S"))
				{
					resPu = true;
				}

			} catch (SQLException sql)
			{
				sql.printStackTrace();
				FarmaUtility.showMessage(pDialogo,
				"Error al verificar la existencia de productos en convenio" +
				sql.getMessage(), pObjeto);
			}

		return resPu;
    }

    public static void  aceptarTransaccionRempota(FarmaTableModel pTableModel,
            Object pObjeto,String pIndCloseConecction)
    {

			try
			{
				 DBConvenioBTLMF.aceptarTransaccionRempota(pTableModel, pIndCloseConecction);
			} catch (SQLException sql)
			{
				sql.printStackTrace();
//				//FarmaUtility.showMessage(pDialogo,
//				"Error al verificar la existencia de productos en convenio" +
//				sql.getMessage(), pObjeto);
			}


    }


    public static void  liberarTransaccionRempota(FarmaTableModel pTableModel,
            Object pObjeto,String pIndCloseConecction)
    {

			try
			{
				 DBConvenioBTLMF.liberarTransaccionRempota(pTableModel, pIndCloseConecction);
			} catch (SQLException sql)
			{
				sql.printStackTrace();
//				//FarmaUtility.showMessage(pDialogo,
//				"Error al verificar la existencia de productos en convenio" +
//				sql.getMessage(), pObjeto);
			}


    }


    public static boolean esMontoPrecioCero(String monto,JDialog pDialogo)
    {
      boolean result =  false;

    	if(FarmaUtility.getDecimalNumber(monto) == 0)
    	{
		 FarmaUtility.showMessage(pDialogo,"El precio venta del producto convenio es cero" , null);
		 result = true;
    	}

    	return result;
    }

    public static String indValidaLineaCredito(JDialog pDialogo)
    {

    	String  result = "";
    	try
		{
    	 result =  DBConvenioBTLMF.validaLineaCredito();
		}
    	catch(SQLException sql)
    	{
    		sql.printStackTrace();
    	}

    	return result;
    }


    public static boolean esCompCredito(JDialog pDialogo)
    {

    	String  result = "";
    	boolean resp = true ;
    	try
		{
    	 result =  DBConvenioBTLMF.esCompCredito();

    	 if (result.equals("N"))
    	 {
    		 resp = false;
    	 }

		}
    	catch(SQLException sql)
    	{
    		sql.printStackTrace();
    	}

    	return resp;
    }

    public static String indConvenioBTLMF(JDialog pDialogo)
    {
    	String indConvenioBTLMF = "";
            try
            {
            	 Map vtaPedido = (Map)DBConvenioBTLMF.obtenerConvenioXPedido(VariablesCaja.vNumPedVta_Anul);
                 indConvenioBTLMF = (String)vtaPedido.get("IND_CONV_BTL_MF");


            }
		    catch(SQLException sql)
		  	{
		  		sql.printStackTrace();
		  	}


    	return indConvenioBTLMF;
    }


public static boolean imprimeMensajeTicketAnulacion(String cajero, String turno,
            String numpedido, String cod_igv,
            String ruta, boolean valor,
            String pIndReimpresion,
            String numComprob)throws Exception {

		// try
		// {
		//String pTipoImp = DBCaja.obtieneTipoImprConsejo();JCHAVEZ 03.07.2009 se comentó para obtener el tipo de impresora por IP
		//String vIndImpre = DBCaja.obtieneIndImpresion();
		String vIndImpre  = "S";
		boolean vResultado = false;
		if (!vIndImpre.equals("N"))
		{


		String htmlTicket = DBConvenioBTLMF.ImprimeMensajeAnulacion(cajero,turno,numpedido,cod_igv,pIndReimpresion,numComprob);

		if (!htmlTicket.equals("N"))
		{
		ArrayList myArray = null;
		StringTokenizer st = null;
		myArray = new ArrayList();
		st = new StringTokenizer(htmlTicket, "Ã");
		while (st.hasMoreTokens()) {
		myArray.add(st.nextToken());
		}
		int cajaUsuario;
		cajaUsuario = DBCaja.obtieneNumeroCajaUsuarioAux();
		VariablesCaja.vNumCaja = new Integer(cajaUsuario).toString();
		boolean existeImpresorasVenta = true;
		ArrayList myArrayaux = new ArrayList();
		System.out.println("cajausuario : " + cajaUsuario);
		String secImprLocal = "";
		secImprLocal = VariablesCaja.vSecImprLocalTicket;
		VariablesCaja.vRutaImpresora = obtieneRutaImpresora(secImprLocal);

		FarmaPrintServiceTicket vPrint = new FarmaPrintServiceTicket(66, VariablesCaja.vRutaImpresora, false);
		FarmaPrintServiceTicket vPrintArchivo = new FarmaPrintServiceTicket(66, ruta, false);
		log.info("..start impresora ticketera: "+ VariablesCaja.vRutaImpresora);
		vPrint.startPrintService_DU();
		log.info("..start ruta Archivo: "+ ruta);
		vPrintArchivo.startPrintService_DU();
		//JCHAVEZ 03.07.2009.sn
		log.info("Seteando el Color ...");
		Date fechaJava = new Date();
		log.info("fecha : " +fechaJava);
		log.info(""+ fechaJava.getDate());
		int dia=fechaJava.getDate();
		int resto= dia % 2;
		log.info("resto : " +resto);
		if(resto ==0&&VariablesPtoVenta.vIndImprimeRojo){
		    vPrint.printLine((char)27+"4",true ); //rojo
		    vPrintArchivo.printLine((char)27+"4",true ); //rojo
		}
		else
		{
		    vPrint.printLine((char)27+"5",true ); //negro
		    vPrintArchivo.printLine((char)27+"5",true ); //negro
		}
		//JCHAVEZ 03.07.2009.en

		    log.info("imprime datos de cabecera de impresion");
		    vPrint.printLine(FarmaPRNUtility.llenarBlancos(1)+ "----------Anulación de Pedido----------",true);
		    vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1)+ "----------Anulación de Pedido----------",true);
		    vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + "Local:  " + FarmaVariables.vCodLocal+" - "+FarmaVariables.vDescCortaLocal,true);
		    vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1) + "Local:  " + FarmaVariables.vCodLocal+" - "+FarmaVariables.vDescCortaLocal,true);
		    vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + "Fecha de creación: " + myArray.get(7) ,true);
		    vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1) + "Fecha de creación: " + myArray.get(7) ,true);
		    vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + "Numero de Ticket: "+myArray.get(1),true);
		    vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1) + "Numero de Ticket: "+myArray.get(1),true);
		    vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + "Fecha de Anulación: "+myArray.get(2),true);
		    vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1) + "Fecha de Anulación: "+myArray.get(2),true);
		    vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + "Caja: "+myArray.get(3) + " Turno: " + myArray.get(4),true);
		    vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1) + "Caja: "+myArray.get(3) + " Turno: " + myArray.get(4),true);
		    vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + "Usuario: " +myArray.get(5),true);
		    vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1) + "Usuario: " +myArray.get(5),true);
		    vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + "Monto: " + myArray.get(6) ,true);
		    vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1) + "Monto: " + myArray.get(6) ,true);
		    //JQUISPE 25.03.2010
		    vPrint.printLine(FarmaPRNUtility.llenarBlancos(1) + "Motivo: " + myArray.get(9) ,true);
		    vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1) + "Motivo: " + myArray.get(9) ,true);

		   // vPrint.printLine(FarmaPRNUtility.llenarBlancos(1)+ "                                           ",true);
		    vPrint.printLine(FarmaPRNUtility.llenarBlancos(1)+ "----------Anulación de Pedido----------",true);
		    vPrintArchivo.printLine(FarmaPRNUtility.llenarBlancos(1)+ "----------Anulación de Pedido----------",true);

		    log.info("..End Service Ticketera");
		    vPrint.endPrintService();
		    log.info("..End Service Archivo");
		    vPrintArchivo.endPrintService();

		    vResultado = true;

		//JCORTEZ 16.07.09 Se guarda fecha de anulacion por comprobantes
		DBCaja.actualizaFechaImpr(numpedido,""+myArray.get(8),"A");
		log.info("Guardando fecha impresion Anulacion ..."+myArray.get(8));
		}


		}

		return vResultado;
}



}
