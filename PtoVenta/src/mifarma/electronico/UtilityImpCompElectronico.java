package mifarma.electronico;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import javax.swing.JDialog;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaPrintService;
import mifarma.common.FarmaPrintServiceTicket;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.electronico.impresion.FacadeComprobanteElectronico;
import mifarma.electronico.impresion.dao.ConstantesDocElectronico;
import mifarma.electronico.impresion.reference.DBComprobanteElectronico;

import mifarma.ptoventa.administracion.impresoras.reference.DBImpresoras;
import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.caja.reference.VariablesVirtual;
import mifarma.ptoventa.convenioBTLMF.reference.UtilityConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.fidelizacion.reference.UtilityFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;

import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import printerFarma.FarmaPrinterFacade;

import printerUtil.FarmaPrinterConstants;


/**
 * Copyright (c) 2014 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : ImprimeComprobanteElectronico.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * CHUANES     01.09.2014  Creación<br>
 * <br>
 * @author Cesar Alfredo Huanes Bautista<br>
 * @version 1.0<br>
 *
 */
public class UtilityImpCompElectronico {
    private static final Logger log = LoggerFactory.getLogger(UtilityImpCompElectronico.class);
    FacadeComprobanteElectronico facade = new FacadeComprobanteElectronico();
    // KMONCADA 13.01.2015 DATOS DE IMPRESORA TERMICA
    private String tipoImpTermica;
    private String nomImpTermica;
    private String rutaImpTermica;
    private String rutaFileTestigo;


    public UtilityImpCompElectronico()throws Exception {
        super();
        cargarDatosImpresoras();
        rutaImpTermica = getRuta();
        
    }
    
    /**
     * Metodo que obtiene datos de la impresora termica configurada a la IP.
     * 
     * @since 13.01.2015 KMONCADA
     * @throws Exception
     */
    private void cargarDatosImpresoras()throws Exception{
        List lstDatosImpresora = DBImpresoras.getDatosImpresoraTerminca(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, FarmaVariables.vIpPc);
        if(lstDatosImpresora.size()>0){
            Map datoImpresora  = (HashMap)lstDatosImpresora.get(0);
            tipoImpTermica = (String)datoImpresora.get("TIPO");
            nomImpTermica = (String)datoImpresora.get("IMPRESORA");
        }else{
            throw new Exception("Error al consultar datos de impresora termica.\n Verifique configuración en \"Mantenimiento de Impresoras\".");
        }
        
    }
    
    /**
     * Imprimir texto de experto de ahorro
     * @author ASOSA
     * @since 10/03/2015
     * @kind PTOSYAYAYAYA
     * @param printer
     * @param codMarca
     */
    /* LO COMENTAMOS PORQUE DERREPENTE CAMBIAN DE NUEVO DE OPINION Y HAYA Q UTILIZARLO.
    public void imprimirTextoExpert(FarmaPrinterFacade printer,
                                    String codMarca) {
        boolean flag = UtilityFidelizacion.getIndImpresionTextExpert(VariablesFidelizacion.numPedVtaTextExpert, 
                                                          VariablesFidelizacion.secCompPagoTextExpert);
        if (flag) {
            
            String cod = UtilityInventario.obtenerParametroString(ConstantsFidelizacion.TAB_491);   //A, T
            String valor = "";            
            String texto02 = "Experta del";
            
            UtilityFidelizacion.getTextExpertImpresion(VariablesFidelizacion.numPedVtaTextExpert, 
                                                        VariablesFidelizacion.secCompPagoTextExpert);
            
            if (VariablesFidelizacion.texto03Expert.equals("M")) {
                texto02 = "Experto del";
            }
            
            if (cod.equals("A")) {
                valor = (VariablesPuntos.frmPuntos.getTarjetaBean().getAhorroTotal() == null) ? "" : VariablesPuntos.frmPuntos.getTarjetaBean().getAhorroTotal().toString();                
                GregorianCalendar d = new GregorianCalendar();                
                valor = "Llevas S/." + valor + " soles de ahorro en los ultimos 12 meses";  //
                //valor = "Llevas S/." + valor + " soles de ahorro";
            } else if (cod.equals("T")) {
                valor = VariablesFidelizacion.texto02Expert;
            }
            
            printer.printGeneral(" ", 
                                 "0", 
                                 "I", 
                                 true, 
                                 0);
            printer.printGeneral(" ", 
                                 "0", 
                                 "I", 
                                 true, 
                                 0);
            printer.printGeneral(VariablesFidelizacion.texto01Expert, 
                                 "1", 
                                 "C", 
                                 true, 
                                 0);*/
            //printer.printImagenGeneral(codMarca, "C:\\farmaventa\\imagenes\\logo-experto.jpg");
            /*
            printer.printGeneral("eres un", 
                                 "1", 
                                 "I", 
                                 true, 
                                 0);
            */
            /*
            printer.printGeneral(texto02,   //Expert@ del
                                 "1", 
                                 "C", 
                                 false, 
                                 0);
            printer.printGeneral("Ahorro", 
                                 "1", 
                                 "C", 
                                 false, 
                                 0);
            printer.printGeneral(valor, 
                                 "0", 
                                 "C", 
                                 true, 
                                 0);
            printer.printGeneral(" ", 
                                 "0", 
                                 "I", 
                                 true, 
                                 0);
            printer.printGeneral(" ", 
                                 "0", 
                                 "I", 
                                 true, 
                                 0);
        }        
        
        VariablesFidelizacion.numPedVtaTextExpert = "";
        VariablesFidelizacion.secCompPagoTextExpert = "";
    }
    */
    /**
     * Imprimir texto de experto de ahorro
     * @author ASOSA
     * @since 10/03/2015
     * @kind PTOSYAYAYAYA
     * @param printer
     * @param codMarca
     */
    public void imprimirTextoExpert(FarmaPrinterFacade printer,
                                    String codMarca,
                                    FarmaPrintServiceTicket vPrintArchivo,
                                     String pRutaFile) {
        boolean flag = UtilityFidelizacion.getIndImpresionTextExpert(VariablesFidelizacion.numPedVtaTextExpert, 
                                                          VariablesFidelizacion.secCompPagoTextExpert);
        List listComanda = new ArrayList();
        if (flag) {
            
            listComanda = UtilityFidelizacion.getDatosImpresionExpert(VariablesFidelizacion.numPedVtaTextExpert, 
                                                          VariablesFidelizacion.secCompPagoTextExpert);
            try{
                impresionTermica(listComanda, null);
            }catch(Exception ex){
                log.error("", ex);
            }
            /* Map mapComanda = new HashMap();
            String valor, tamanio, alineacion, bold, ajuste;
            boolean isNegrita = false;
            for (int i = 0; i < listComanda.size(); i++) {
                mapComanda = (HashMap)listComanda.get(i);
                valor = reemplazarCaracterRaros(((String)mapComanda.get("VALOR")));
                tamanio = ((String)mapComanda.get("TAMANIO"));
                alineacion = ((String)mapComanda.get("ALINEACION"));
                bold = ((String)mapComanda.get("BOLD"));
                //isNegrita = (!"N".equalsIgnoreCase(bold))?true: false;
                if (!"N".equalsIgnoreCase(bold))
                    isNegrita = true;
                else
                    isNegrita = false;
                ajuste = ((String)mapComanda.get("AJUSTE"));
                int lineado = 0;
                if ("S".equalsIgnoreCase(ajuste)) {
                    lineado = 1;
                }

                if ("-".equalsIgnoreCase(valor)) {
                    printer.printLineDotted(30);
                    if (pRutaFile != null) {
                        vPrintArchivo.printLine("--------------------------------------------------------------------",
                                                true);
                    }
                } else {
                    printer.printGeneral(valor, tamanio, alineacion, isNegrita, lineado);
                    if (pRutaFile != null) {
                        vPrintArchivo.printLine(valor, true); //
                    }
                }
            } */
            
        }        
        
        VariablesFidelizacion.numPedVtaTextExpert = "";
        VariablesFidelizacion.secCompPagoTextExpert = "";
    }

    public boolean printDocumento(String pNumPedidoVta, String pSecCompPago, boolean isReimpresion, boolean isCobro) throws Exception {
        List listComanda = new ArrayList();
        listComanda = DBComprobanteElectronico.getDatosImpresion(FarmaVariables.vVersion, pNumPedidoVta, pSecCompPago, isReimpresion);
        
        VariablesFidelizacion.numPedVtaTextExpert = pNumPedidoVta;
        VariablesFidelizacion.secCompPagoTextExpert = pSecCompPago;
        
        if (listComanda.size() > 0) {
            //boolean rest = impresionDocumentoEnTermica(listComanda, true, rutaFileTestigo, isCobro);
            boolean rest = impresionTermica(listComanda,rutaFileTestigo);
            if (!rest) {
                VariablesCaja.vEstadoSinComprobanteImpreso = "S";
            } else {
                VariablesCaja.vEstadoSinComprobanteImpreso = "N";
            }
        } else {
            VariablesCaja.vEstadoSinComprobanteImpreso = "S";
            throw new Exception("Alerta Local:\n" +
                    "La cadena para Impresión es vacía." + "Por favor llamar a Mesa de Ayuda.");
        }
        boolean pImpResult = true; 
        return pImpResult;
    }

    public boolean impresionDocumentoEnTermica(List listComanda, boolean isPonerLogo, String pRutaFile, boolean isCobro                                               
                                               ) throws Exception {

        FarmaPrinterFacade printer = new FarmaPrinterFacade(tipoImpTermica, rutaImpTermica, false, "", ""); //manda imprimir segun el modelo de impresora
        FarmaPrintServiceTicket vPrintArchivo = null;
        if (pRutaFile != null) {
            vPrintArchivo = new FarmaPrintServiceTicket(666, pRutaFile, false);
            vPrintArchivo.startPrintService();
        }

        if (!printer.startPrintService()) {
            throw new Exception("No se pudo iniciar la Impresión del Documento.\nVerifique su impresora Termica por favor.");
        } else {
            String codMarca = "";
            printer.inicializate(); //INICIALIZAR LA IMPRESORA--VALORES POR DEFECTO
            
            /*
            if (isPonerLogo) {
                codMarca =
                    DBComprobanteElectronico.getMarcaLocal(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal);
                if (codMarca == null) {
                    throw new Exception("Error al consultar Marca del Local");
                }
                imprimirTextoExpert(printer, codMarca, vPrintArchivo, pRutaFile);   //ASOSA - 10/03/2015 - PTOSYAYAYAYA
                // IMPRESION DE LOGO DE MARCA
                printer.printLogo(codMarca);
            }
            */
            
            Map mapComanda = new HashMap();
            String valor, tamanio, alineacion, bold, ajuste;
            boolean isNegrita = false;
            for (int i = 0; i < listComanda.size(); i++) {
                mapComanda = (HashMap)listComanda.get(i);
                valor = reemplazarCaracterRaros(((String)mapComanda.get("VALOR")));
                tamanio = ((String)mapComanda.get("TAMANIO"));
                alineacion = ((String)mapComanda.get("ALINEACION"));
                bold = ((String)mapComanda.get("BOLD"));
                //isNegrita = (!"N".equalsIgnoreCase(bold))?true: false;
                if (!"N".equalsIgnoreCase(bold))
                    isNegrita = true;
                else
                    isNegrita = false;
                ajuste = ((String)mapComanda.get("AJUSTE"));
                int lineado = 0;
                if ("S".equalsIgnoreCase(ajuste)) {
                    lineado = 1;
                }

                if ("-".equalsIgnoreCase(valor)) {
                    printer.printLineDotted(30);
                    if (pRutaFile != null) {
                        vPrintArchivo.printLine("--------------------------------------------------------------------",
                                                true);
                    }
                } else {
                    printer.printGeneral(valor, tamanio, alineacion, isNegrita, lineado);
                    if (pRutaFile != null) {
                        vPrintArchivo.printLine(valor, true); //
                    }
                }
            }
            //imprimirTextoExpert(printer, codMarca);   //ASOSA - 10/03/2015 - PTOSYAYAYAYA - DECIDIERON NO PONERLO ACA, PORSIAKSO LO COMENTO PORQ CAPAS SE LES OCURRE VOLVER A PONERLO
            // FIN DE IMPRESION
            printer.endPrintService(isCobro);
            if (pRutaFile != null) {
                vPrintArchivo.endPrintService();
            }

            return true;
        }
    }

    private String reemplazarCaracterRaros(String pText) {
        if (pText != null) {
            pText = pText.replaceAll("Á", "A");
            pText = pText.replaceAll("É", "E");
            pText = pText.replaceAll("Í", "I");
            pText = pText.replaceAll("Ó", "O");
            pText = pText.replaceAll("Ú", "U");
            pText = pText.replaceAll("á", "a");
            pText = pText.replaceAll("é", "e");
            pText = pText.replaceAll("í", "i");
            pText = pText.replaceAll("ó", "o");
            pText = pText.replaceAll("ú", "u");
            pText = pText.replaceAll("ñ", "n");
            pText = pText.replaceAll("Ñ", "N");
            pText = pText.replaceAll("°", "");
        }
        return pText;
    }

    /**
     * Prueba de gabeta con facturacion electronica
     * @since 13.01.2015 KMONCADA
     */
    public void abrirGabeta(){
        FarmaPrinterFacade printer = new FarmaPrinterFacade(tipoImpTermica, rutaImpTermica, false, "", ""); 
        log.info("inicio abrir gabeta electronico");
        printer.startPrintService();
        printer.abrirGabeta();
        log.info("fin abrir gabeta electronico");
    }
    
    public boolean printDocumentoMALO(JDialog pJDialog, String pNumPedidoVta, String pSecCompPago, String pTipoOld,
                                      String pTipoClienteConvenio, String pNumComprobante) throws Exception {

        if (pNumComprobante == null || pNumComprobante.trim().length() > 0) {
            String pTipoDocumento = "";

            if (pTipoOld.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET)) {
                pTipoDocumento = ConstantsVentas.TIPO_COMP_BOLETA;
            } else {
                if (pTipoOld.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_TICKET_FACT)) {
                    pTipoDocumento = ConstantsVentas.TIPO_COMP_FACTURA;
                } else
                    pTipoDocumento = pTipoOld;
            }

            ArrayList<ArrayList<String>> lstCabecera = null;
            ArrayList<String> lstPiePag = null;
            ArrayList<ArrayList<String>> lstDetalle = null;
            ArrayList<ArrayList<String>> lstMontos = null;
            ArrayList<ArrayList<String>> lstNotas = null;
            ArrayList<ArrayList<String>> lstFormaPago = null;
            ArrayList<ArrayList<String>> lstDelivery = null;
            ArrayList<ArrayList<String>> lstEmpresa = null;

            ArrayList lstConvenio = null;
            Map<String, Object> ax =
                facade.obtieneDatosImpresion(pNumPedidoVta, pSecCompPago, pTipoDocumento, pTipoClienteConvenio,
                                             lstPiePag, lstCabecera, lstDetalle, lstMontos, lstNotas, lstFormaPago,
                                             lstDelivery, lstEmpresa);

            lstCabecera = (ArrayList<ArrayList<String>>)ax.get("2");
            lstPiePag = (ArrayList<String>)ax.get("1");
            lstDetalle = (ArrayList<ArrayList<String>>)ax.get("3");
            lstMontos = (ArrayList<ArrayList<String>>)ax.get("4");
            lstNotas = (ArrayList<ArrayList<String>>)ax.get("5");
            lstFormaPago = (ArrayList<ArrayList<String>>)ax.get("6");
            lstDelivery = (ArrayList<ArrayList<String>>)ax.get("7");
            lstEmpresa = (ArrayList<ArrayList<String>>)ax.get("8");
            log.debug("CABECERA" + lstCabecera.size());
            log.debug("DETALLE" + lstDetalle.size());
            log.debug("MONTOS" + lstMontos.size());
            log.debug("NOTAS" + lstNotas.size());

            log.debug("PIE DE PAGINA" + lstPiePag.size());
            log.debug("FORMA PAGO" + lstFormaPago.size());


            /**DATOS DEL EMISOR*/
            String rucEmisor = ((ArrayList)lstCabecera.get(0)).get(0).toString().trim();
            String nomComercialEmisor = ((ArrayList)lstCabecera.get(0)).get(1).toString().trim();
            String razSocialEmisor = ((ArrayList)lstCabecera.get(0)).get(2).toString().trim();
            String dirEmisor = ((ArrayList)lstCabecera.get(0)).get(3).toString().trim();
            String telEmisor = ((ArrayList)lstCabecera.get(0)).get(4).toString().trim();
            String depEmisor = ((ArrayList)lstCabecera.get(0)).get(5).toString().trim();
            String proEmisor = ((ArrayList)lstCabecera.get(0)).get(6).toString().trim();
            String disEmisor = ((ArrayList)lstCabecera.get(0)).get(7).toString().trim();
            log.debug("RUC EMISOR " + rucEmisor);
            /**DATOS DEL LOCAL*/
            String codLocal = ((ArrayList)lstCabecera.get(0)).get(8).toString().trim();
            ;
            String desCortaLocal = ((ArrayList)lstCabecera.get(0)).get(9).toString();
            String dirLocal = ((ArrayList)lstCabecera.get(0)).get(10).toString();

            /**DATOS DEL RECEPTOR*/
            String rucReceptor = ((ArrayList)lstCabecera.get(0)).get(11).toString().trim();
            String codTipIndeRecep = ((ArrayList)lstCabecera.get(0)).get(12).toString().trim();
            String razSocialRecep = ((ArrayList)lstCabecera.get(0)).get(13).toString().trim();
            String dirReceptor = ((ArrayList)lstCabecera.get(0)).get(14).toString().trim();
            String numDocIdenUsua = ((ArrayList)lstCabecera.get(0)).get(15).toString().trim();

            /**DATOS DE PROPIOS DEL DOCUMENTOS*/
            String nomDocumento = ((ArrayList)lstCabecera.get(0)).get(16).toString().trim();
            String serieCorrelativo = ((ArrayList)lstCabecera.get(0)).get(17).toString().trim();
            String codTipMotivoNota = ((ArrayList)lstCabecera.get(0)).get(18).toString().trim();
            String numCompPagoRef = ((ArrayList)lstCabecera.get(0)).get(19).toString().trim();
            String sustAnulacion = ((ArrayList)lstCabecera.get(0)).get(20).toString();
            String fecCreaCompPago = ((ArrayList)lstCabecera.get(0)).get(21).toString();
            String fecEmision = ((ArrayList)lstCabecera.get(0)).get(22).toString();
            log.debug(" " + codTipMotivoNota);
            log.debug(" " + numCompPagoRef);
            log.debug(" " + sustAnulacion);
            log.debug(" " + fecCreaCompPago);
            log.debug(" " + fecEmision);
            /**TRAMA DEL CODIGO PDF417*/
            String tramaPDF417 = ((ArrayList)lstCabecera.get(0)).get(23).toString().trim();
            /**NUMERO DE DOCUMENTO DE CO-PAGO REFERENCIAL*/
            String numCompCoPagoRef = ((ArrayList)lstCabecera.get(0)).get(24).toString().trim();
            String tipPedVta = ((ArrayList)lstCabecera.get(0)).get(25).toString().trim();
            String tipComPagoRef = ((ArrayList)lstCabecera.get(0)).get(26).toString().trim();
            String tipCompPago = ((ArrayList)lstCabecera.get(0)).get(27).toString().trim();
            String nomCompRef = ((ArrayList)lstCabecera.get(0)).get(28).toString().trim();
            /**CANTIDAD DE CUPONES*/
            String cantCupones = ((ArrayList)lstCabecera.get(0)).get(29).toString().trim();
            /**CODIGO DE MARCA*/
            String codMarca = ((ArrayList)lstCabecera.get(0)).get(30).toString().trim();
            /**TIPO DE COMPROBANTE REFERENCIAL CUANDO ES NOTA DE CREDITO*/
            String tipCompPagoRef = ((ArrayList)lstCabecera.get(0)).get(31).toString().trim();
            String ordCompra = "";
            String codpoliza = "";
            String nompoliza = "";
            /**DATO DE VENTA EMPRESA*/
            if (lstEmpresa.size() > 0) {
                ordCompra = ((ArrayList)lstEmpresa.get(0)).get(0).toString().trim();
                codpoliza = ((ArrayList)lstEmpresa.get(0)).get(1).toString().trim();
                nompoliza = ((ArrayList)lstEmpresa.get(0)).get(2).toString().trim();
            }
            /**DATOS DE DELIVERY*/
            String nomCliDeli = "";
            String diCliDeli = "";
            String dirEnvDeli = "";
            String refDirDeli = "";
            String rucCliDeli = "";
            String razCliDeli = "";
            if (lstDelivery.size() > 0) {
                nomCliDeli = ((ArrayList)lstDelivery.get(0)).get(0).toString().trim();
                diCliDeli = ((ArrayList)lstDelivery.get(0)).get(1).toString().trim();
                dirEnvDeli = ((ArrayList)lstDelivery.get(0)).get(2).toString().trim();
                refDirDeli = ((ArrayList)lstDelivery.get(0)).get(3).toString().trim();
                rucCliDeli = ((ArrayList)lstDelivery.get(0)).get(4).toString().trim();
                razCliDeli = ((ArrayList)lstDelivery.get(0)).get(5).toString().trim();
            }

            String datosConvenio = null;
            if (pTipoClienteConvenio != null && !(pTipoClienteConvenio.equals(""))) {
                if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(new JDialog(), null) &&
                    VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF) {
                    if (!tipPedVta.equals(ConstantesDocElectronico.TIPVTAINSTU)) {
                        datosConvenio =
                                facade.imprimeDatosConvenio(pNumPedidoVta, pSecCompPago, pTipoDocumento, pTipoClienteConvenio);
                        if (datosConvenio != null && datosConvenio.length() > 0) {
                            List<String> items = Arrays.asList(datosConvenio.split("Ã"));
                            lstConvenio = new ArrayList(items);
                        }

                    }
                }
            }


            /**DETALLES DEL ITEM*/

            String codProd = "";
            String desProd = "";
            String canProd = "";
            String preUnit = "";
            String descItem = "";
            String monItem = "";
            String indProVirtual = "";
            String tiProdVirtual = "";

            /**MONTOS GLOBALES*/

            String opGrab = ((ArrayList)lstMontos.get(0)).get(0).toString();
            String opInaf = ((ArrayList)lstMontos.get(0)).get(1).toString();
            String opExon = ((ArrayList)lstMontos.get(0)).get(2).toString();
            String opGrat = ((ArrayList)lstMontos.get(0)).get(3).toString();
            String impIgv = ((ArrayList)lstMontos.get(0)).get(4).toString();
            String monDes = ((ArrayList)lstMontos.get(0)).get(5).toString();
            String monTot = ((ArrayList)lstMontos.get(0)).get(6).toString();
            String redond = ((ArrayList)lstMontos.get(0)).get(7).toString();
            String ahorro = ((ArrayList)lstMontos.get(0)).get(8).toString();
            // DUBILLUZ 03.10.2014
            String valorCoPago = ((ArrayList)lstMontos.get(0)).get(9).toString();
            String pctoCoPagoEmpresa = ((ArrayList)lstMontos.get(0)).get(10).toString();
            String impTotalPagarConvEmpresa = ((ArrayList)lstMontos.get(0)).get(11).toString();
            String indCoPagoEmpresa = ((ArrayList)lstMontos.get(0)).get(12).toString();
            String indCoPagoCliente = ((ArrayList)lstMontos.get(0)).get(13).toString();
            String pCodServicio = ((ArrayList)lstMontos.get(0)).get(14).toString();
            String pDescServicio = ((ArrayList)lstMontos.get(0)).get(15).toString();
            //CHUANES 23/10/2014
            String totalPagarEmpresa = ((ArrayList)lstMontos.get(0)).get(16).toString();
            String totalPagar = ((ArrayList)lstMontos.get(0)).get(17).toString();
            // DUBILLUZ 03.10.2014
            /**FORMA DE PAGO*/
            String codFormaPago = "";
            String tipMoneda = "";
            String montEfectivo = "";
            String montVuelto = "";
            String desPago = "";

            /**NOTAS DEL DOCUMENTO*/
            String son = ((ArrayList)lstNotas.get(0)).get(0).toString();
            String montoLetras = ((ArrayList)lstNotas.get(0)).get(1).toString();
            String montoMoneda = ((ArrayList)lstNotas.get(0)).get(2).toString();


            /**INICIA LA IMPRESION-SE INVOCA A LA CLASE GESTORA DE IMPRESION */

            FarmaPrinterFacade printer;

            try {
                printer = new FarmaPrinterFacade(this.getModelo(), this.getRuta(), false, "", "");
                String ruta = UtilityPtoVenta.obtieneDirectorioComprobantes();
                Date vFecImpr = new Date();
                String fechaImpresion;
                String DATE_FORMAT = "yyyyMMdd";
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                fechaImpresion = sdf.format(vFecImpr);
                ruta =
ruta + fechaImpresion + "_" + nomDocumento.substring(0, 1) + "_E_" + VariablesCaja.vNumPedVta + "_" +
 serieCorrelativo + ".TXT";
                FarmaPrintServiceTicket vPrintArchivo = new FarmaPrintServiceTicket(666, ruta, false);
                vPrintArchivo.startPrintService();

                if (!printer.startPrintService()) {
                    VariablesCaja.vEstadoSinComprobanteImpreso = "S";
                    FarmaUtility.showMessage(pJDialog, ConstantesDocElectronico.ERRORIMP, null);
                    return false;

                } else {
                    printer.inicializate();
                    /**IMPRESION DEL LOGO*/
                    //CESAR HUANES
                    //24/10/2014
                    printer.printLogo(codMarca);
                    /**DATOS DEL EMISOR*/

                    //printer.printLineDoubleSize(nomComercialEmisor);
                    vPrintArchivo.printLine(nomComercialEmisor, true); //

                    printer.printLineCenter(razSocialEmisor + printer.printEspacioBlanco(1) +
                                            ConstantesDocElectronico.RUCEMISOR + rucEmisor);
                    vPrintArchivo.printLine(razSocialEmisor + printer.printEspacioBlanco(1) +
                                            ConstantesDocElectronico.RUCEMISOR + rucEmisor, true); //

                    printer.printLineCenter(dirEmisor);
                    vPrintArchivo.printLine(dirEmisor, true); //
                    printer.printLineCenter(disEmisor);
                    vPrintArchivo.printLine(disEmisor, true); //
                    printer.printLineCenter(ConstantesDocElectronico.TLFEMISOR + telEmisor);
                    vPrintArchivo.printLine(ConstantesDocElectronico.TLFEMISOR + telEmisor, true); //
                    /**DATOS DEL LOCAL*/
                    printer.printLine(codLocal + " " + dirLocal);
                    vPrintArchivo.printLine(codLocal + " " + dirLocal, true); //
                    /**DATOS DEL DOCUMENTO*/
                    if (this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOSTAR)) {
                        printer.printLineBold("                       \t" + nomDocumento);
                        vPrintArchivo.printLine("                       \t" + nomDocumento, true); //
                    } else {
                        printer.printLineBold("               \t" + nomDocumento);
                        vPrintArchivo.printLine("               \t" + nomDocumento, true); //
                    }
                    printer.printLineCenter(serieCorrelativo);
                    vPrintArchivo.printLine(serieCorrelativo, true); //
                    printer.printLine(ConstantesDocElectronico.FECHAEMISION + fecEmision);
                    vPrintArchivo.printLine(ConstantesDocElectronico.FECHAEMISION + fecEmision, true); //
                    /**DOCUMENTO DE REFERENCIA*/
                    if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(new JDialog(), null) &&
                        VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF) {

                        if ((numCompCoPagoRef.trim() != ConstantesDocElectronico.CERO || numCompCoPagoRef != null) &&
                            tipComPagoRef.equals(ConstantsPtoVenta.TIP_COMP_GUIA)) {
                            printer.printLine(ConstantesDocElectronico.NUMGUIA + numCompCoPagoRef.trim());
                            vPrintArchivo.printLine(ConstantesDocElectronico.NUMGUIA + numCompCoPagoRef.trim(),
                                                    true); //
                        }
                    }
                    if (tipPedVta.equals(ConstantesDocElectronico.TIPVTAINSTU)) {
                        if (!ordCompra.equals(ConstantesDocElectronico.CERO) && !"".equals(ordCompra)) {
                            printer.printLine(ConstantesDocElectronico.ORDCOMP + ordCompra);
                            vPrintArchivo.printLine(ConstantesDocElectronico.ORDCOMP + ordCompra, true); //
                        }
                        if (!codpoliza.equals(ConstantesDocElectronico.CERO) && !"".equals(codpoliza)) {
                            printer.printLine(ConstantesDocElectronico.POLIZA + codpoliza + "-" + nompoliza);
                            vPrintArchivo.printLine(ConstantesDocElectronico.POLIZA + codpoliza + "-" + nompoliza,
                                                    true); //
                        }
                    }
                    /**MOTIVO DE ANUlACION*/
                    if (tipCompPago.equals(ConstantesDocElectronico.TIPNOTACREDITO)) {
                        printer.printLine(ConstantesDocElectronico.MOTIVO + sustAnulacion);
                        vPrintArchivo.printLine(ConstantesDocElectronico.MOTIVO + sustAnulacion, true); //
                        if (!numCompPagoRef.equals(ConstantesDocElectronico.CERO) && numCompPagoRef != null) {
                            printer.printLine(ConstantesDocElectronico.DOCREF + nomCompRef + numCompPagoRef);
                            vPrintArchivo.printLine(ConstantesDocElectronico.DOCREF + nomCompRef + numCompPagoRef,
                                                    true); //
                        }
                    }
                    printer.printLineDotted(30);
                    vPrintArchivo.printLine("--------------------------------------------------------------------",
                                            true); //
                    /**DETALLE DEL DOCUMENTO*/
                    if (this.getModelo().equalsIgnoreCase(FarmaPrinterConstants.MODELOSTAR)) {
                        printer.printLineBold(ConstantesDocElectronico.CODIGO + "    \t" +
                                              ConstantesDocElectronico.DESCRIPCION + "           \t" +
                                              ConstantesDocElectronico.CANTIDAD + "  \t" +
                                              ConstantesDocElectronico.PREUNIT + " \t" +
                                              ConstantesDocElectronico.DESCUENT + " \t" +
                                              ConstantesDocElectronico.IMPORTE);
                        vPrintArchivo.printLine(ConstantesDocElectronico.CODIGO + "    \t" +
                                                ConstantesDocElectronico.DESCRIPCION + "           \t" +
                                                ConstantesDocElectronico.CANTIDAD + "  \t" +
                                                ConstantesDocElectronico.PREUNIT + " \t" +
                                                ConstantesDocElectronico.DESCUENT + " \t" +
                                                ConstantesDocElectronico.IMPORTE, true); //
                    } else {
                        printer.printLineBold(ConstantesDocElectronico.CODIGO + "   \t" +
                                              ConstantesDocElectronico.DESCRIPCION + "   \t" +
                                              ConstantesDocElectronico.CANTIDAD + "  " +
                                              ConstantesDocElectronico.PREUNIT + " " +
                                              ConstantesDocElectronico.DESCUENT + " " +
                                              ConstantesDocElectronico.IMPORTE);
                        vPrintArchivo.printLine(ConstantesDocElectronico.CODIGO + "   \t" +
                                                ConstantesDocElectronico.DESCRIPCION + "   \t" +
                                                ConstantesDocElectronico.CANTIDAD + "  " +
                                                ConstantesDocElectronico.PREUNIT + " " +
                                                ConstantesDocElectronico.DESCUENT + " " +
                                                ConstantesDocElectronico.IMPORTE, true); //
                    }
                    printer.printLineDotted(30);
                    vPrintArchivo.printLine("--------------------------------------------------------------------",
                                            true); //

                    if (lstDetalle != null && lstDetalle.size() > 0) {

                        //String indCoPagoCliente
                        //String pCodServicio

                        if (indCoPagoCliente.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                            printer.printLine(printer.alineaDetalle(pCodServicio, pDescServicio, "1", monTot,
                                                                    ConstantesDocElectronico.CERODECIMAL, monTot));
                            vPrintArchivo.printLine(printer.alineaDetalle(pCodServicio, pDescServicio, "1", monTot,
                                                                          ConstantesDocElectronico.CERODECIMAL,
                                                                          monTot), true); //
                        } else {
                            for (int i = 0; i < lstDetalle.size(); i++) {
                                codProd = ((ArrayList)lstDetalle.get(i)).get(0).toString(); //codigo de producto
                                desProd = ((ArrayList)lstDetalle.get(i)).get(1).toString(); // descripcion del producto
                                canProd = ((ArrayList)lstDetalle.get(i)).get(2).toString(); // cantidad del producto
                                preUnit =
                                        ((ArrayList)lstDetalle.get(i)).get(3).toString(); //precio unitario (PUEDE INCLUIR O NO IGV SEGUN EL TIPO COMPROBANTE)
                                descItem =
                                        ((ArrayList)lstDetalle.get(i)).get(4).toString(); //descuento del item (PUEDE INCLUIR O NO IGV SEGUN EL TIPO COMPROBANTE)
                                monItem =
                                        ((ArrayList)lstDetalle.get(i)).get(5).toString(); //monto del item (PUEDE INCLUIR O NO IGV SEGUN EL TIPO COMPROBANTE)
                                printer.printLine(printer.alineaDetalle(codProd, desProd, canProd, preUnit, descItem,
                                                                        monItem));
                                vPrintArchivo.printLine(printer.alineaDetalle(codProd, desProd, canProd, preUnit,
                                                                              descItem, monItem), true); //
                                indProVirtual =
                                        ((ArrayList)lstDetalle.get(i)).get(6).toString(); //Indicador de producto virtual
                                tiProdVirtual =
                                        ((ArrayList)lstDetalle.get(i)).get(7).toString(); //Indicador de producto virtual
                                //verificamos si el producto es virtual
                                if (i == 0 && indProVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                                    VariablesCaja.vIndPedidoConProdVirtualImpresion = true;
                                else
                                    VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
                            }
                        }
                    }
                    boolean pIndImprRedondeo = false;
                    if (!indCoPagoEmpresa.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                        pIndImprRedondeo = true;

                        printer.printLineDotted(30);
                        vPrintArchivo.printLine("--------------------------------------------------------------------",
                                                true);
                        //CESAR HUANES 23/10/2014
                        //SI HAY REDONDEO SE IMPRIME EL TOTAL
                        if (!redond.equalsIgnoreCase(ConstantesDocElectronico.CERODECIMAL)) {
                            //printer.printLineRigth(ConstantesDocElectronico.TOTAL+ConstantesDocElectronico.SOLES+printer.alineaMontos(monTot));
                            //vPrintArchivo.printLine(ConstantesDocElectronico.TOTAL+ConstantesDocElectronico.SOLES+printer.alineaMontos(monTot),true);

                            printer.printLineRigth(ConstantesDocElectronico.REDONDEO + ConstantesDocElectronico.SOLES +
                                                   printer.alineaMontos(redond));
                            vPrintArchivo.printLine(ConstantesDocElectronico.REDONDEO +
                                                    ConstantesDocElectronico.SOLES + printer.alineaMontos(redond),
                                                    true);

                        }
                        printer.printLineRigth(ConstantesDocElectronico.TOTALPAGAR + ConstantesDocElectronico.SOLES +
                                               printer.alineaMontos(totalPagar));
                        vPrintArchivo.printLine(ConstantesDocElectronico.TOTALPAGAR + ConstantesDocElectronico.SOLES +
                                                printer.alineaMontos(totalPagar), true);
                    }

                    /**MONTOS TOTALES*/
                    printer.printLineDotted(30);
                    vPrintArchivo.printLine("--------------------------------------------------------------------",
                                            true);
                    if (!opGrab.equalsIgnoreCase(ConstantesDocElectronico.CERODECIMAL)) {
                        printer.printLineRigth(ConstantesDocElectronico.OPGRABADAS + ConstantesDocElectronico.SOLES +
                                               printer.alineaMontos(opGrab));
                        vPrintArchivo.printLine(ConstantesDocElectronico.OPGRABADAS + ConstantesDocElectronico.SOLES +
                                                printer.alineaMontos(opGrab), true);
                    }
                    if (!opInaf.equalsIgnoreCase(ConstantesDocElectronico.CERODECIMAL)) {
                        printer.printLineRigth(ConstantesDocElectronico.OPINAFECTAS + ConstantesDocElectronico.SOLES +
                                               printer.alineaMontos(opInaf));
                        vPrintArchivo.printLine(ConstantesDocElectronico.OPINAFECTAS + ConstantesDocElectronico.SOLES +
                                                printer.alineaMontos(opInaf), true);
                    }
                    if (!opExon.equalsIgnoreCase(ConstantesDocElectronico.CERODECIMAL)) {
                        printer.printLineRigth(ConstantesDocElectronico.OPEXONERDAS + ConstantesDocElectronico.SOLES +
                                               printer.alineaMontos(opExon));
                        vPrintArchivo.printLine(ConstantesDocElectronico.OPEXONERDAS + ConstantesDocElectronico.SOLES +
                                                printer.alineaMontos(opExon), true);
                    }
                    if (!opGrat.equalsIgnoreCase(ConstantesDocElectronico.CERODECIMAL)) {
                        printer.printLineRigth(ConstantesDocElectronico.OPGRATUITAS + ConstantesDocElectronico.SOLES +
                                               printer.alineaMontos(opGrat));
                        vPrintArchivo.printLine(ConstantesDocElectronico.OPGRATUITAS + ConstantesDocElectronico.SOLES +
                                                printer.alineaMontos(opGrat), true);
                    }
                    if (!impIgv.equalsIgnoreCase(ConstantesDocElectronico.CERODECIMAL)) {
                        printer.printLineRigth(ConstantesDocElectronico.IMPIGV + 
                                               UtilityVentas.obtenerIgvLocal() + "%:" + //ASOSA - 25/06/2015 - IGVAZONIA
                                               ConstantesDocElectronico.SOLES +
                                               printer.alineaMontos(impIgv));
                        vPrintArchivo.printLine(ConstantesDocElectronico.IMPIGV + 
                                                UtilityVentas.obtenerIgvLocal() + "%:" + //ASOSA - 25/06/2015 - IGVAZONIA
                                                ConstantesDocElectronico.SOLES +
                                                printer.alineaMontos(impIgv), true);
                    }
                    if (!monDes.equalsIgnoreCase(ConstantesDocElectronico.CERODECIMAL)) {
                        printer.printLineRigth(ConstantesDocElectronico.DESCUENTO + ConstantesDocElectronico.SOLES +
                                               printer.alineaMontos(monDes));
                        vPrintArchivo.printLine(ConstantesDocElectronico.DESCUENTO + ConstantesDocElectronico.SOLES +
                                                printer.alineaMontos(monDes), true);
                    }
                    if (!monTot.equalsIgnoreCase(ConstantesDocElectronico.CERODECIMAL)) {
                        if (indCoPagoEmpresa.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                            printer.printLineRigth(ConstantesDocElectronico.IMP_TOTAL +
                                                   ConstantesDocElectronico.SOLES + printer.alineaMontos(monTot));
                            vPrintArchivo.printLine(ConstantesDocElectronico.IMP_TOTAL +
                                                    ConstantesDocElectronico.SOLES + printer.alineaMontos(monTot),
                                                    true);
                        } else {

                            printer.printLineRigth("IMPORTE TOTAL:" + ConstantesDocElectronico.SOLES +
                                                   printer.alineaMontos(totalPagar));
                            vPrintArchivo.printLine("IMPORTE TOTAL:" + ConstantesDocElectronico.SOLES +
                                                    printer.alineaMontos(totalPagar), true);
                            printer.printLineDotted(30);


                        }
                    }

                    // printer.printLineDotted(30);
                    //vPrintArchivo.printLine("--------------------------------------------------------------------",true);
                    /* DUBILLUZ 03.10.2014 */
                    if (indCoPagoEmpresa.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                        printer.printLineDotted(30);
                        vPrintArchivo.printLine("--------------------------------------------------------------------",
                                                true);
                        printer.printLineRigth(ConstantesDocElectronico.CO_PAGO.replace("#", pctoCoPagoEmpresa) +
                                               ConstantesDocElectronico.SOLES + printer.alineaMontos(valorCoPago));
                        vPrintArchivo.printLine(ConstantesDocElectronico.CO_PAGO.replace("#", pctoCoPagoEmpresa) +
                                                ConstantesDocElectronico.SOLES + printer.alineaMontos(valorCoPago),
                                                true);
                        //CESAR HUANES 23/10/2014
                        //SI HAY REDONDEO ENTONCES SE IMPRIME EL TOTAL
                        /*if(!redond.equalsIgnoreCase(ConstantesDocElectronico.CERODECIMAL)){
        printer.printLineRigth(ConstantesDocElectronico.TOTAL+ConstantesDocElectronico.SOLES+printer.alineaMontos(impTotalPagarConvEmpresa));
        vPrintArchivo.printLine(ConstantesDocElectronico.TOTAL+ConstantesDocElectronico.SOLES+printer.alineaMontos(impTotalPagarConvEmpresa),true);
        }

        //totalPagarEmpresa
        if(!redond.equalsIgnoreCase(ConstantesDocElectronico.CERODECIMAL)){
            printer.printLineDotted(30);
            printer.printLineRigth(ConstantesDocElectronico.REDONDEO+ConstantesDocElectronico.SOLES+printer.alineaMontos(redond));
            vPrintArchivo.printLine(ConstantesDocElectronico.REDONDEO+ConstantesDocElectronico.SOLES+printer.alineaMontos(redond),true);
        }*/

                        printer.printLineRigth(ConstantesDocElectronico.TOTALPAGAR + ConstantesDocElectronico.SOLES +
                                               printer.alineaMontos(impTotalPagarConvEmpresa));
                        vPrintArchivo.printLine(ConstantesDocElectronico.TOTALPAGAR + ConstantesDocElectronico.SOLES +
                                                printer.alineaMontos(impTotalPagarConvEmpresa), true);

                        printer.printLineDotted(30);
                        vPrintArchivo.printLine("--------------------------------------------------------------------",
                                                true);
                    }
                    /* DUBILLUZ 03.10.2014 */
                    /**FORMA DE PAGO*/

                    if (lstFormaPago != null && lstFormaPago.size() > 0 &&
                        // NOTA DE CREDITO NO MOSTRARA FORMA DE PAGO
                        // DUBILLUZ 05.11.2014
                        // CAMBIO AUTORIZADO POR DAVID JAVAR indicado a LAIS.
                        !(pTipoDocumento.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_NOTA_CREDITO))) {
                        for (int i = 0; i < lstFormaPago.size(); i++) {

                            codFormaPago = ((ArrayList)lstFormaPago.get(i)).get(0).toString().trim();
                            tipMoneda = ((ArrayList)lstFormaPago.get(i)).get(1).toString().trim();
                            montEfectivo = ((ArrayList)lstFormaPago.get(i)).get(2).toString().trim();
                            montVuelto = ((ArrayList)lstFormaPago.get(i)).get(3).toString().trim();
                            desPago = ((ArrayList)lstFormaPago.get(i)).get(4).toString().trim();

                            //String indCoPagoEmpresa=((ArrayList)lstMontos.get(0)).get(12).toString();
                            //String indCoPagoCliente=((ArrayList)lstMontos.get(0)).get(13).toString();
                            if (indCoPagoEmpresa.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N) &&
                                indCoPagoCliente.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                                if (codFormaPago.trim().equalsIgnoreCase(ConstantsCaja.FORMA_PAGO_EFECTIVO_DOLARES) ||
                                    codFormaPago.trim().equalsIgnoreCase(ConstantsCaja.FORMA_PAGO_EFECTIVO_SOLES)) {
                                    if (codFormaPago.trim().equalsIgnoreCase(ConstantsCaja.FORMA_PAGO_EFECTIVO_SOLES)) {
                                        printer.printLineRigth(desPago + printer.alineaMontos(montEfectivo));
                                        vPrintArchivo.printLine(desPago + printer.alineaMontos(montEfectivo), true);
                                    } else {
                                        printer.printLineRigth(ConstantesDocElectronico.TIPCAMBIO +
                                                               FarmaVariables.vTipCambio + " - " + desPago +
                                                               printer.alineaMontos(montEfectivo));
                                        vPrintArchivo.printLine(ConstantesDocElectronico.TIPCAMBIO +
                                                                FarmaVariables.vTipCambio + " - " + desPago +
                                                                printer.alineaMontos(montEfectivo), true);
                                    }

                                } else {
                                    printer.printLineRigth(desPago + printer.alineaMontos(montEfectivo));
                                    vPrintArchivo.printLine(desPago + printer.alineaMontos(montEfectivo), true);
                                }
                                // ///////////////////////////
                                // Se imprime el Vuelto
                                if (FarmaUtility.getDecimalNumber(montVuelto) > 0)
                                    printer.printLineRigth(ConstantesDocElectronico.VUELTO +
                                                           ConstantesDocElectronico.SOLES +
                                                           printer.alineaMontos(montVuelto));
                                // ///////////////////////////
                                // Se imprime el Vuelto
                            } else {
                                //COPAGO DEL CLIENTE
                                if (indCoPagoEmpresa.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N) &&
                                    indCoPagoCliente.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {

                                    if (codFormaPago.trim().equalsIgnoreCase(ConstantsCaja.FORMA_PAGO_EFECTIVO_DOLARES) ||
                                        codFormaPago.trim().equalsIgnoreCase(ConstantsCaja.FORMA_PAGO_EFECTIVO_SOLES)) {
                                        if (codFormaPago.trim().equalsIgnoreCase(ConstantsCaja.FORMA_PAGO_EFECTIVO_SOLES)) {

                                            printer.printLineRigth(desPago + printer.alineaMontos(montEfectivo));
                                            vPrintArchivo.printLine(desPago + printer.alineaMontos(montEfectivo),
                                                                    true);
                                        } else {
                                            printer.printLineRigth(ConstantesDocElectronico.TIPCAMBIO +
                                                                   FarmaVariables.vTipCambio + " - " + desPago +
                                                                   printer.alineaMontos(montEfectivo));
                                            vPrintArchivo.printLine(ConstantesDocElectronico.TIPCAMBIO +
                                                                    FarmaVariables.vTipCambio + " - " + desPago +
                                                                    printer.alineaMontos(montEfectivo), true);
                                        }

                                    } else {
                                        if (codFormaPago.trim().equalsIgnoreCase(ConstantsCaja.FORMA_PAGO_CREDITO_EMPRESA))
                                            log.debug("NO MUESTRA FORMA DE PAGO CREDITO EMPRESA ");
                                        else {
                                            printer.printLineRigth(desPago + printer.alineaMontos(montEfectivo));
                                            vPrintArchivo.printLine(desPago + printer.alineaMontos(montEfectivo),
                                                                    true);
                                        }
                                    }
                                    // ///////////////////////////
                                    // Se imprime el Vuelto
                                    if (FarmaUtility.getDecimalNumber(montVuelto) > 0) {
                                        printer.printLineRigth(ConstantesDocElectronico.VUELTO +
                                                               ConstantesDocElectronico.SOLES +
                                                               printer.alineaMontos(montVuelto));
                                        vPrintArchivo.printLine(ConstantesDocElectronico.VUELTO +
                                                                ConstantesDocElectronico.SOLES +
                                                                printer.alineaMontos(montVuelto), true);
                                    }
                                    // ///////////////////////////
                                    // Se imprime el Vuelto
                                } else {
                                    //COPAGO DE LA EMPRESA
                                    if (codFormaPago.trim().equalsIgnoreCase(ConstantsCaja.FORMA_PAGO_CREDITO)) {
                                        printer.printLineRigth(desPago + printer.alineaMontos(montEfectivo));
                                        vPrintArchivo.printLine(desPago + printer.alineaMontos(montEfectivo), true);
                                    }
                                }
                            }

                        }
                    }
                    /**MONTO EN LETRAS*/
                    printer.printLine(son + montoLetras + " " + montoMoneda);
                    vPrintArchivo.printLine(son + montoLetras + " " + montoMoneda, true);
                    /**AHORRO*/
                    //cesar huanes
                    //27/10/214 CUANDO ES NOTA DE CREDITO NO SE DEBE IMPRIMIR EL AHORRO
                    //if(!ahorro.equals(ConstantesDocElectronico.CERODECIMAL)&&tipCompPago.equalsIgnoreCase(ConstantesDocElectronico.NOTACREDITO)){
                    //DUBILLUZ 05.11.2014
                    //Correccion reportado por LAIS SALIA EL AHORRO DONDE NO CORRESPONDIA
                    if (!tipCompPago.equalsIgnoreCase(ConstantesDocElectronico.NOTACREDITO))
                        if (!ahorro.equals(ConstantesDocElectronico.CERODECIMAL)) {
                            printer.printLineBold(ConstantesDocElectronico.AHORRO + "" +
                                                  ConstantesDocElectronico.SOLES + ahorro);
                            vPrintArchivo.printLine(ConstantesDocElectronico.AHORRO + "" +
                                                    ConstantesDocElectronico.SOLES + ahorro, true);
                        }
                    /**DATOS DE CONVENIO*/

                    if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(new JDialog(), null) &&
                        VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF) {

                        if (lstConvenio != null && lstConvenio.size() > 0) {
                            /**DATOS DE LA FACTURA SI ES CONVENIO*/
                            if (tipCompPago.equalsIgnoreCase(ConstantesDocElectronico.BOLETA) ||
                                tipCompPago.equalsIgnoreCase(ConstantesDocElectronico.TICKETBOLETA) ||
                                (tipCompPago.equalsIgnoreCase(ConstantesDocElectronico.NOTACREDITO) &&
                                 tipCompPagoRef.equalsIgnoreCase(ConstantesDocElectronico.TIPCOMPAGOREFBOLETA))) {
                                if (!razSocialRecep.equals(ConstantesDocElectronico.CERO) &&
                                    !"".equals(razSocialRecep)) {
                                    printer.printLine(ConstantesDocElectronico.NOMBRECLIENTE + razSocialRecep);
                                    vPrintArchivo.printLine(ConstantesDocElectronico.NOMBRECLIENTE + razSocialRecep,
                                                            true);
                                }
                                if (!numDocIdenUsua.equals(ConstantesDocElectronico.CERO) &&
                                    !"".equals(numDocIdenUsua)) {
                                    printer.printLine(ConstantesDocElectronico.DNI + numDocIdenUsua);
                                    vPrintArchivo.printLine(ConstantesDocElectronico.DNI + numDocIdenUsua, true);
                                }

                            }
                            if (tipCompPago.equalsIgnoreCase(ConstantesDocElectronico.FACTUA) ||
                                tipCompPago.equalsIgnoreCase(ConstantesDocElectronico.TICKETFACTURA) ||
                                (tipCompPago.equalsIgnoreCase(ConstantesDocElectronico.NOTACREDITO) &&
                                 tipCompPagoRef.equalsIgnoreCase(ConstantesDocElectronico.TIPCOMPAGOREFFACTURA))) {
                                if (!razSocialRecep.equals(ConstantesDocElectronico.CERO) &&
                                    !"".equals(razSocialRecep)) {
                                    printer.printLine(ConstantesDocElectronico.RAZSOCIACLIENTE + razSocialRecep);
                                    vPrintArchivo.printLine(ConstantesDocElectronico.RAZSOCIACLIENTE + razSocialRecep,
                                                            true);
                                }
                                if (!rucReceptor.equals(ConstantesDocElectronico.CERO) && !"".equals(rucReceptor)) {
                                    printer.printLine(ConstantesDocElectronico.RUCCLIENTE + rucReceptor);
                                    vPrintArchivo.printLine(ConstantesDocElectronico.RUCCLIENTE + rucReceptor, true);
                                }
                            }
                            if (!dirReceptor.equals(ConstantesDocElectronico.CERO) && !"".equals(dirReceptor)) {
                                printer.printLine(ConstantesDocElectronico.DIRCLIENTE + dirReceptor);
                                vPrintArchivo.printLine(ConstantesDocElectronico.DIRCLIENTE + dirReceptor, true);
                            }
                            printer.stepLine();
                            for (int i = 0; i < lstConvenio.size(); i++) {
                                String dato = lstConvenio.get(i).toString();
                                if (dato != null && dato.length() != 0) {
                                    printer.printLine(dato);
                                    vPrintArchivo.printLine(dato, true);
                                }
                            }
                        }
                        if (tipPedVta.equals(ConstantesDocElectronico.TIPVTAINSTU)) {
                            /**DATOS DEL CLIENTE*/
                            if (tipCompPago.equalsIgnoreCase(ConstantesDocElectronico.BOLETA) ||
                                tipCompPago.equalsIgnoreCase(ConstantesDocElectronico.TICKETBOLETA) ||
                                (tipCompPago.equalsIgnoreCase(ConstantesDocElectronico.NOTACREDITO) &&
                                 tipCompPagoRef.equalsIgnoreCase(ConstantesDocElectronico.TIPCOMPAGOREFBOLETA))) {
                                if (!razSocialRecep.equals(ConstantesDocElectronico.CERO) &&
                                    !"".equals(razSocialRecep)) {
                                    printer.printLine(ConstantesDocElectronico.NOMBRECLIENTE + razSocialRecep);
                                    vPrintArchivo.printLine(ConstantesDocElectronico.NOMBRECLIENTE + razSocialRecep,
                                                            true);
                                }
                                if (!numDocIdenUsua.equals(ConstantesDocElectronico.CERO) &&
                                    !"".equals(numDocIdenUsua)) {
                                    printer.printLine(ConstantesDocElectronico.DNI + numDocIdenUsua);
                                    vPrintArchivo.printLine(ConstantesDocElectronico.DNI + numDocIdenUsua, true);
                                }

                            }
                            if (tipCompPago.equalsIgnoreCase(ConstantesDocElectronico.FACTUA) ||
                                tipCompPago.equalsIgnoreCase(ConstantesDocElectronico.TICKETFACTURA) ||
                                (tipCompPago.equalsIgnoreCase(ConstantesDocElectronico.NOTACREDITO) &&
                                 tipCompPagoRef.equalsIgnoreCase(ConstantesDocElectronico.TIPCOMPAGOREFFACTURA))) {
                                if (!razSocialRecep.equals(ConstantesDocElectronico.CERO) &&
                                    !"".equals(razSocialRecep)) {
                                    printer.printLine(ConstantesDocElectronico.RAZSOCIACLIENTE + razSocialRecep);
                                    vPrintArchivo.printLine(ConstantesDocElectronico.RAZSOCIACLIENTE + razSocialRecep,
                                                            true);
                                }
                                if (!rucReceptor.equals(ConstantesDocElectronico.CERO) && !"".equals(rucReceptor)) {
                                    printer.printLine(ConstantesDocElectronico.RUCCLIENTE + rucReceptor);
                                    vPrintArchivo.printLine(ConstantesDocElectronico.RUCCLIENTE + rucReceptor, true);
                                }
                            }
                            if (!dirReceptor.equals(ConstantesDocElectronico.CERO) && !"".equals(dirReceptor)) {
                                printer.printLine(ConstantesDocElectronico.DIRCLIENTE + dirReceptor);
                                vPrintArchivo.printLine(ConstantesDocElectronico.DIRCLIENTE + dirReceptor, true);
                            }

                        }

                    } else {
                        if (tipPedVta.equals(ConstantesDocElectronico.TIPVTAMESON)) //VENTA NORMAL
                        /**DATOS DEL CLIENTE*/ {
                            if (tipCompPago.equalsIgnoreCase(ConstantesDocElectronico.BOLETA) ||
                                tipCompPago.equalsIgnoreCase(ConstantesDocElectronico.TICKETBOLETA) ||
                                (tipCompPago.equalsIgnoreCase(ConstantesDocElectronico.NOTACREDITO) &&
                                 tipCompPagoRef.equalsIgnoreCase(ConstantesDocElectronico.TIPCOMPAGOREFBOLETA))) {
                                if (!razSocialRecep.equals(ConstantesDocElectronico.CERO) &&
                                    !"".equals(razSocialRecep)) {
                                    printer.printLine(ConstantesDocElectronico.NOMBRECLIENTE + razSocialRecep);
                                    vPrintArchivo.printLine(ConstantesDocElectronico.NOMBRECLIENTE + razSocialRecep,
                                                            true);
                                }
                                if (!numDocIdenUsua.equals(ConstantesDocElectronico.CERO) &&
                                    !"".equals(numDocIdenUsua)) {
                                    printer.printLine(ConstantesDocElectronico.DNI + numDocIdenUsua);
                                    vPrintArchivo.printLine(ConstantesDocElectronico.DNI + numDocIdenUsua, true);
                                }

                            }
                            if (tipCompPago.equalsIgnoreCase(ConstantesDocElectronico.FACTUA) ||
                                tipCompPago.equalsIgnoreCase(ConstantesDocElectronico.TICKETFACTURA) ||
                                (tipCompPago.equalsIgnoreCase(ConstantesDocElectronico.NOTACREDITO) &&
                                 tipCompPagoRef.equalsIgnoreCase(ConstantesDocElectronico.TIPCOMPAGOREFFACTURA))) {
                                if (!razSocialRecep.equals(ConstantesDocElectronico.CERO) &&
                                    !"".equals(razSocialRecep)) {
                                    printer.printLine(ConstantesDocElectronico.RAZSOCIACLIENTE + razSocialRecep);
                                    vPrintArchivo.printLine(ConstantesDocElectronico.RAZSOCIACLIENTE + razSocialRecep,
                                                            true);
                                }
                                if (!rucReceptor.equals(ConstantesDocElectronico.CERO) && !"".equals(rucReceptor)) {
                                    printer.printLine(ConstantesDocElectronico.RUCCLIENTE + rucReceptor);
                                    vPrintArchivo.printLine(ConstantesDocElectronico.RUCCLIENTE + rucReceptor, true);
                                }
                            }
                            if (!ConstantesDocElectronico.CERO.equals(dirReceptor) && !"".equals(dirReceptor)) {
                                printer.printLine(ConstantesDocElectronico.DIRCLIENTE + dirReceptor);
                                vPrintArchivo.printLine(ConstantesDocElectronico.DIRCLIENTE + dirReceptor, true);
                            }
                        }
                        if (tipPedVta.equals(ConstantesDocElectronico.TIPVTADELIV)) { //DELIVERY NOMAL SIN  CONVENIO
                            //cesarhuanes
                            //27/10.2014 no es necesario por que esta redundando.

                            double montoBoleta = Double.parseDouble(totalPagar);
                            /*if(nomCliDeli!=null && !"".equals(nomCliDeli) && !ConstantesDocElectronico.CERO.equals(nomCliDeli)){
               printer.printLine(ConstantesDocElectronico.NOMCLIENTE+nomCliDeli);
               vPrintArchivo.printLine(ConstantesDocElectronico.NOMCLIENTE+nomCliDeli,true);
           }
           if(diCliDeli!=null && !"".equals(diCliDeli) && !ConstantesDocElectronico.CERO.equals(diCliDeli)){
               printer.printLine(ConstantesDocElectronico.DIRCLIENTE+diCliDeli);
               vPrintArchivo.printLine(ConstantesDocElectronico.DIRCLIENTE+diCliDeli,true);
           }
           if(dirEnvDeli!=null && !"".equals(dirEnvDeli)&& !ConstantesDocElectronico.CERO.equals(dirEnvDeli)){
           printer.printLine(ConstantesDocElectronico.DIRENVIO+dirEnvDeli);
               vPrintArchivo.printLine(ConstantesDocElectronico.DIRENVIO+dirEnvDeli,true);
           }
            if(refDirDeli!=null && !"".equals(refDirDeli)&& !ConstantesDocElectronico.CERO.equals(refDirDeli)){
           printer.printLine(ConstantesDocElectronico.REFERENCIA+refDirDeli);
               vPrintArchivo.printLine(ConstantesDocElectronico.REFERENCIA+refDirDeli,true);
           }*/
                            if (tipCompPago.equalsIgnoreCase(ConstantesDocElectronico.BOLETA) ||
                                tipCompPago.equalsIgnoreCase(ConstantesDocElectronico.TICKETBOLETA) ||
                                (tipCompPago.equalsIgnoreCase(ConstantesDocElectronico.NOTACREDITO) &&
                                 tipCompPagoRef.equalsIgnoreCase(ConstantesDocElectronico.TIPCOMPAGOREFBOLETA))) {
                                if (montoBoleta >= ConstantesDocElectronico.IMPLIMITE) {
                                    if (!razCliDeli.equals(ConstantesDocElectronico.CERO) && !"".equals(razCliDeli)) {
                                        printer.printLine(ConstantesDocElectronico.NOMBRECLIENTE + razCliDeli);
                                        vPrintArchivo.printLine(ConstantesDocElectronico.NOMBRECLIENTE + razCliDeli,
                                                                true);
                                    }
                                    if (!rucReceptor.equals(ConstantesDocElectronico.CERO) &&
                                        !"".equals(rucReceptor)) {
                                        printer.printLine(ConstantesDocElectronico.DNI + rucReceptor);
                                        vPrintArchivo.printLine(ConstantesDocElectronico.DNI + rucReceptor, true);
                                    }
                                    if (!ConstantesDocElectronico.CERO.equals(dirReceptor) &&
                                        !"".equals(dirReceptor)) {
                                        printer.printLine(ConstantesDocElectronico.DIRCLIENTE + dirReceptor);
                                        vPrintArchivo.printLine(ConstantesDocElectronico.DIRCLIENTE + dirReceptor,
                                                                true);
                                    }
                                } else {
                                    if (nomCliDeli != null && !"".equals(nomCliDeli) &&
                                        !ConstantesDocElectronico.CERO.equals(nomCliDeli)) {
                                        printer.printLine(ConstantesDocElectronico.NOMCLIENTE + nomCliDeli);
                                        vPrintArchivo.printLine(ConstantesDocElectronico.NOMCLIENTE + nomCliDeli,
                                                                true);
                                    }
                                    if (diCliDeli != null && !"".equals(diCliDeli) &&
                                        !ConstantesDocElectronico.CERO.equals(diCliDeli)) {
                                        printer.printLine(ConstantesDocElectronico.DIRCLIENTE + diCliDeli);
                                        vPrintArchivo.printLine(ConstantesDocElectronico.DIRCLIENTE + diCliDeli, true);
                                    }
                                }


                            }
                            if (tipCompPago.equalsIgnoreCase(ConstantesDocElectronico.FACTUA) ||
                                tipCompPago.equalsIgnoreCase(ConstantesDocElectronico.TICKETFACTURA) ||
                                (tipCompPago.equalsIgnoreCase(ConstantesDocElectronico.NOTACREDITO) &&
                                 tipCompPagoRef.equalsIgnoreCase(ConstantesDocElectronico.TIPCOMPAGOREFFACTURA))) {

                                if (razCliDeli != null && !"".equals(razCliDeli) &&
                                    !ConstantesDocElectronico.CERO.equals(razCliDeli)) {
                                    printer.printLine(ConstantesDocElectronico.RAZSOCIACLIENTE + razCliDeli);
                                    vPrintArchivo.printLine(ConstantesDocElectronico.RAZSOCIACLIENTE + razCliDeli,
                                                            true);
                                }

                                if (rucCliDeli != null && !"".equals(rucCliDeli) &&
                                    !ConstantesDocElectronico.CERO.equals(rucCliDeli)) {
                                    printer.printLine(ConstantesDocElectronico.RUCCLIENTE + rucCliDeli);
                                    vPrintArchivo.printLine(ConstantesDocElectronico.RUCCLIENTE + rucCliDeli, true);
                                }
                                if (!ConstantesDocElectronico.CERO.equals(diCliDeli) && !"".equals(dirReceptor)) {
                                    printer.printLine(ConstantesDocElectronico.DIRCLIENTE + diCliDeli);
                                    vPrintArchivo.printLine(ConstantesDocElectronico.DIRCLIENTE + dirReceptor, true);
                                }


                            }


                        }


                    }

                    /**IMPRESION DEL MENSAJE DE REGARGA O PRODUCTO VIRTUAL*/

                    if (VariablesCaja.vIndPedidoConProdVirtualImpresion && 
                        VariablesVentas.vDniRimac.equals("") //ASOSA - 15/01/2015 - RIMAC
                        ) {
                        printer.stepLine();
                        this.prinInforVirtual(printer, tiProdVirtual, null, null, null, null, null, pNumPedidoVta,
                                              codProd, vPrintArchivo);

                    }
                    
                    //INI ASOSA - 12/01/2015 - RIMAC
                    if (UtilityCaja.get_ind_ped_con_rimac(pNumPedidoVta)) {
                        this.prinInforRimac(printer, tiProdVirtual, null, null, null, null, null, pNumPedidoVta,
                                                  codProd, vPrintArchivo);
                    }                    
                    //FIN ASOSA - 12/01/2015 - RIMAC
                    
                    

                    /**PDF417*/
                    printer.stepLine();
                    printer.printCodePDF417(tramaPDF417);
                    vPrintArchivo.printLine(">>>>>> printCodePDF417 <<<<", true);
                    printer.stepLine();
                    /**PIE DE PAGINA*/

                    for (int i = 0; i < lstPiePag.size(); i++) {
                        String dato = lstPiePag.get(i).toString();

                        if (dato != null && dato.length() != 0) {
                            if (i == 2) {
                                dato = dato.concat(nomDocumento);
                            }
                            printer.printLineCenter(dato);
                            vPrintArchivo.printLine(dato, true);

                        }
                    }
                    printer.printLineDotted(30);
                    printer.printLineCenter(FarmaVariables.vVersion + " - " + ConstantesDocElectronico.USUARIO +
                                            FarmaVariables.vIdUsu + " - " + ConstantesDocElectronico.CAJA +
                                            VariablesCaja.vNumCaja);
                    vPrintArchivo.printLine(FarmaVariables.vVersion + " - " + ConstantesDocElectronico.USUARIO +
                                            FarmaVariables.vIdUsu + " - " + ConstantesDocElectronico.CAJA +
                                            VariablesCaja.vNumCaja, true);
                    /**IMPRIME LA CANTIDAD DE CUPONES*/
                    int cantCupo = ConstantesDocElectronico.CANTCUPON;
                    if (cantCupo != 0) {
                        if (cantCupo > 1) {
                            printer.printLineDotted(30);
                            printer.printLineBold(ConstantesDocElectronico.GANO + cantCupo +
                                                  ConstantesDocElectronico.CUPONES);
                            vPrintArchivo.printLine("--------------------------------------------------------------------",
                                                    true);
                            vPrintArchivo.printLine(ConstantesDocElectronico.GANO + cantCupo +
                                                    ConstantesDocElectronico.CUPONES, true);
                        } else {
                            printer.printLineBold(ConstantesDocElectronico.GANO + cantCupo +
                                                  ConstantesDocElectronico.CUPON);
                            vPrintArchivo.printLine(ConstantesDocElectronico.GANO + cantCupo +
                                                    ConstantesDocElectronico.CUPON, true);
                        }
                    }
                    /**IMPRIME LOS DATOS PARA LA NOTA DE CREDITO*/
                    if (tipCompPago.equalsIgnoreCase(ConstantesDocElectronico.TIPNOTACREDITO)) {
                        this.prinDatosCliente(printer, vPrintArchivo);
                    }

                    // dubilluz 07.10.2014
                    if (lstDetalle != null && lstDetalle.size() > 0) {
                        //String indCoPagoCliente
                        //String pCodServicio
                        if (indCoPagoCliente.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                            printer.printLineDotted(30);
                            vPrintArchivo.printLine("--------------------------------------------------------------------",
                                                    true);

                            for (int i = 0; i < lstDetalle.size(); i++) {
                                codProd = ((ArrayList)lstDetalle.get(i)).get(0).toString(); //codigo de producto
                                desProd = ((ArrayList)lstDetalle.get(i)).get(1).toString(); // descripcion del producto
                                canProd = ((ArrayList)lstDetalle.get(i)).get(2).toString(); // cantidad del producto
                                preUnit =
                                        ((ArrayList)lstDetalle.get(i)).get(3).toString(); //precio unitario (PUEDE INCLUIR O NO IGV SEGUN EL TIPO COMPROBANTE)
                                descItem =
                                        ((ArrayList)lstDetalle.get(i)).get(4).toString(); //descuento del item (PUEDE INCLUIR O NO IGV SEGUN EL TIPO COMPROBANTE)
                                monItem =
                                        ((ArrayList)lstDetalle.get(i)).get(5).toString(); //monto del item (PUEDE INCLUIR O NO IGV SEGUN EL TIPO COMPROBANTE)

                                printer.printLine(printer.alineaDetalle(codProd, desProd, canProd, "", descItem, ""));
                                vPrintArchivo.printLine(printer.alineaDetalle(codProd, desProd, canProd, "", descItem,
                                                                              ""), true);
                                indProVirtual =
                                        ((ArrayList)lstDetalle.get(i)).get(6).toString(); //Indicador de producto virtual
                                tiProdVirtual =
                                        ((ArrayList)lstDetalle.get(i)).get(7).toString(); //Indicador de producto virtual
                                //verificamos si el producto es virtual
                                if (i == 0 && indProVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                                    VariablesCaja.vIndPedidoConProdVirtualImpresion = true;
                                else
                                    VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
                            }
                            printer.printLineDotted(30);
                            vPrintArchivo.printLine("--------------------------------------------------------------------",
                                                    true);
                        }
                    }
                    // dubilluz 07.10.2014

                    printer.endPrintService(false);
                    vPrintArchivo.endPrintService();

                    VariablesCaja.vEstadoSinComprobanteImpreso = "N";
                    try {
                        DBCaja.actualizaFechaImpr(VariablesCaja.vNumPedVta, pNumComprobante, "C");
                    } catch (SQLException e) {
                        log.error("", e);
                    }


                    /*// FarmaUtility.showMessage(null, nomDocumento+"-"+serieCorrelativo, null);
        UtilityCaja.actualizaEstadoPedido(pNumPedidoVta, ConstantsCaja.ESTADO_COBRADO); */ //17.10.2014
                    ConstantesDocElectronico.CANTCUPON = 0;
                    return true;
                }
            } catch (SQLException e) {
                VariablesCaja.vEstadoSinComprobanteImpreso = "S";
                log.error("", e);
                FarmaUtility.showMessage(pJDialog, ConstantesDocElectronico.ERRORIMP, null);
                return false;
            }
        } else {
            throw new Exception("El número de comprobante electronico NO ES VALIDO.\n" +
                    "[" + pNumComprobante + "], comuniquese con Sistemas...!!");

        }


    } //CIERRA EL METODO BOOLEAN DE IMPRESION

    /**
     * CHUANES
     * Obtiene el Nombre de la Impresora Termica
     * 08/09/2014
     * */
    public String getNombreTermica() throws SQLException {
        //String nombre = DBImpresoras.getNombreImpTermica();

        //return nombre;
        return this.nomImpTermica;
    }

    public String getModelo() throws SQLException {
        /*String modelo = DBImpresoras.getModeloImpTermica();
        return modelo;*/
        return this.tipoImpTermica;
    }


    /**
     * CHUANES
     * Obtiene la ruta de impresora Termica
     * 08/09/2014
     * */
    public String getRuta() throws SQLException {
        String ruta = null;
        PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null, null);

        if (servicio != null) {
            try {
                for (int i = 0; i < servicio.length; i++) {
                    PrintService impresora = servicio[i];
                    String pRuta = impresora.getName().toString().trim();
                    String pNombre = UtilityCaja.retornaUltimaPalabra(pRuta, "\\");

                    if (pNombre.trim().toUpperCase().equalsIgnoreCase(this.getNombreTermica().trim().toUpperCase())) {
                        boolean resultado = pRuta.startsWith("\\");
                        if (resultado) {
                            ruta = pRuta;
                        } else {
                            ruta = "\\" + "\\" + FarmaVariables.vIpPc + "\\" + pRuta;
                        }

                    }
                }

            } catch (Exception e) {
                log.error("", e);
            }
        }
        return ruta;
    }

    /**
     * CHUANES
     * Imprime Datos de Recarga Virtual
     * 08/09/2014
     * */
    public void prinInforVirtual(FarmaPrinterFacade printer, String pTipoProdVirtual, String pCodigoAprobacion,
                                 String pNumeroTarjeta, String pNumeroPin, String pNumeroTelefono, String pMonto,
                                 String pNumPedido, String pCodProd, FarmaPrintServiceTicket vPrintArchivo) {

        if (pTipoProdVirtual.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_TARJETA)) {
            printer.printLine(ConstantesDocElectronico.CODAPROB + pCodigoAprobacion);
            vPrintArchivo.printLine(ConstantesDocElectronico.CODAPROB + pCodigoAprobacion, true);
            printer.printLine(ConstantesDocElectronico.NUMTARJETA + pNumeroTarjeta);
            vPrintArchivo.printLine(ConstantesDocElectronico.NUMTARJETA + pNumeroTarjeta, true);
            printer.printLine(ConstantesDocElectronico.NUMPIN + pNumeroPin);
            vPrintArchivo.printLine(ConstantesDocElectronico.NUMPIN + pNumeroPin, true);
        } else if (pTipoProdVirtual.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_RECARGA)) {
            UtilityCaja.obtieneDescImpresion(pNumPedido, pCodProd);
            ArrayList array = (ArrayList)(VariablesVirtual.vArrayList_InfoProvRecarga.get(0));
            for (int i = 0; i < array.size(); i++) {
                if (((String)array.get(i)).trim().length() > 0) {
                    printer.printLine(((String)(array.get(i))).trim());
                    vPrintArchivo.printLine(((String)(array.get(i))).trim(), true);
                }
            }
        }

    }
    
    /**
     * obtieneDescImprRimac
     * @author ASOSA
     * @since 12/01/2015
     * @type RIMAC
     * @param printer
     * @param pTipoProdVirtual
     * @param pCodigoAprobacion
     * @param pNumeroTarjeta
     * @param pNumeroPin
     * @param pNumeroTelefono
     * @param pMonto
     * @param pNumPedido
     * @param pCodProd
     * @param vPrintArchivo
     */
    public void prinInforRimac(FarmaPrinterFacade printer, String pTipoProdVirtual, String pCodigoAprobacion,
                                 String pNumeroTarjeta, String pNumeroPin, String pNumeroTelefono, String pMonto,
                                 String pNumPedido, String pCodProd, FarmaPrintServiceTicket vPrintArchivo) {

            UtilityCaja.obtieneDescImprRimac(pNumPedido, pCodProd);
            ArrayList array = (ArrayList)(VariablesVirtual.vArrayList_InfoProvRecarga.get(0));
            for (int i = 0; i < array.size(); i++) {
                if (((String)array.get(i)).trim().length() > 0) {
                    printer.printLine(((String)(array.get(i))).trim());
                    vPrintArchivo.printLine(((String)(array.get(i))).trim(), true);
                }
            }
        

    }
    

    public void prinDatosCliente(FarmaPrinterFacade printer, FarmaPrintServiceTicket vPrintArchivo) {
        printer.printLineDotted(30);
        printer.printLineCenter(ConstantesDocElectronico.DATOSCLIENTE);
        printer.stepLine();
        printer.printLine(ConstantesDocElectronico.NOMBRE + "______________________________________________");
        printer.stepLine();
        printer.printLine(ConstantesDocElectronico.DNI + "_________________________________________________");
        printer.stepLine();
        printer.stepLine();
        printer.printLine(ConstantesDocElectronico.FIRMA + "_______________________________________________");


        vPrintArchivo.printLine("--------------------------------------------------------------------", true);
        vPrintArchivo.printLine(ConstantesDocElectronico.DATOSCLIENTE, true);
        vPrintArchivo.printLine(ConstantesDocElectronico.NOMBRE + "______________________________________________",
                                true);
        vPrintArchivo.printLine(ConstantesDocElectronico.DNI + "_________________________________________________",
                                true);
        vPrintArchivo.printLine(ConstantesDocElectronico.FIRMA + "_______________________________________________",
                                true);


    }

    private boolean imprimirTMU950DK(String pRutaImpresora) {
        FarmaPrintService vPrint = new FarmaPrintService(66, pRutaImpresora, false);
        if (!vPrint.startPrintService()) {
            return false;
        } else {

            //Abrimos caja
            //27+112+0+25+250
            vPrint.printLine((char)27 + "p" + (char)0 + (char)25 + (char)250, false);

            vPrint.endPrintServiceSinCompletar();

            return true;
        }
    }

    private boolean imprimirTSP700DK(String pRutaImpresora) {
        FarmaPrintService vPrint = new FarmaPrintService(66, pRutaImpresora, false);
        if (!vPrint.startPrintService()) {
            return false;
        } else {

            //Abrimos caja
            //Star      TSP-700 27,07,11,55,07
            vPrint.printLine("" + (char)27 + (char)7 + (char)11 + (char)55 + (char)7, false);

            vPrint.endPrintServiceSinCompletarDelivery();

            return true;
        }
    }
    
    public void setRutaFileTestigo(String rutaFileTestigo) {
        this.rutaFileTestigo = rutaFileTestigo;
    }
    
    public boolean impresionTermica(List listDocumento, String pRutaFile)throws Exception {
        FarmaPrinterFacade printer = new FarmaPrinterFacade(tipoImpTermica, rutaImpTermica, false, "", ""); //manda imprimir segun el modelo de impresora
        FarmaPrintServiceTicket vPrintArchivo = null;
        if (pRutaFile != null) {
            vPrintArchivo = new FarmaPrintServiceTicket(666, pRutaFile, false);
            vPrintArchivo.startPrintService();
        }
        if (!printer.startPrintService()) {
            throw new Exception("No se pudo iniciar la Impresión del Documento.\nVerifique su impresora Termica por favor.");
        } else {
            //INICIALIZAR LA IMPRESORA--VALORES POR DEFECTO
            printer.inicializate(); 
            
            for (int i = 0; i < listDocumento.size(); i++) {
                log.info("LINEA --> "+(HashMap)listDocumento.get(i));
                printer.printer((HashMap)listDocumento.get(i));
                if ("-".equalsIgnoreCase(((String)((HashMap)listDocumento.get(i)).get("VALOR")))) {
                    printer.printLineDotted(30);
                    if (pRutaFile != null) {
                        vPrintArchivo.printLine("--------------------------------------------------------------------",
                                                true);
                    }
                } else {
                    
                    if (pRutaFile != null) {
                        vPrintArchivo.printLine(((String)((HashMap)listDocumento.get(i)).get("VALOR")), true); //
                    }
                }
            }
            printer.endPrintService();
            if (pRutaFile != null) {
                vPrintArchivo.endPrintService();
            }
        }
        return true;
    }
    
    /**
     * Realiza la justificacion de la linea, segun el tamaño de la letra.
     * @author KMONCADA 
     * @since 01.06.2015
     * @param valor VALOR A JUSTIFICAR
     * @param tamanio tamaño de la fuente a imprimir
     * @return
     */
    private ArrayList aplicarJustificacion(String valor, String tamanio){
        ArrayList lstValor = new ArrayList();
        int tamanioTexto = Integer.parseInt(tamanio);
        int cantidadCaracteres = 64;
        
        switch(tamanioTexto){
            case 0 : cantidadCaracteres = 64; break;
            case 1 : cantidadCaracteres = 48; break;
            case 2 : cantidadCaracteres = 24; break;
            case 3 : cantidadCaracteres = 16; break;
            case 4 : cantidadCaracteres = 12; break;
            case 5 : cantidadCaracteres = 9; break;
            case 6 : cantidadCaracteres = 8; break;
        }
        
        String val = valor;
        if(val.length() > cantidadCaracteres){
            String aux = val;
            String lineaAux = "";
            String palabra = "";
            while(aux.length()!=0){
                int espacioBlanco = aux.indexOf(" ");
                if(espacioBlanco!=-1){
                    palabra = aux.substring(0,espacioBlanco);
                    if((lineaAux.length()+palabra.length()) < cantidadCaracteres){
                        if(lineaAux.length()==0){
                            lineaAux = palabra;
                        }else{
                            lineaAux = lineaAux+" "+palabra;
                        }
                    }else{
                        //lstValor.add(lineaAux);
                        lstValor.add(finalizaJustifica(lineaAux,cantidadCaracteres));
                        lineaAux = palabra;
                    }
                    aux = aux.substring(espacioBlanco+1);
                }else{
                    lineaAux = lineaAux+" "+aux;
                    lstValor.add(finalizaJustifica(lineaAux,cantidadCaracteres));
                    aux = "";
                }
            }
        }else{
            lstValor.add(valor);
        }
        return lstValor;    
    }
    
    /**
     * Completa con espacios entre las palabras para ocupar el espacio de la linea segun la cantidad de caracteres
     * por el tamaño de la letra a imprimir
     * @author KMONCADA
     * @since 01.06.2015
     * @param valor valor a justificar
     * @param cantidadCaracteres cantidad por cada linea 
     * @return texto completado con espacios
     */
    private String finalizaJustifica(String valor, int cantidadCaracteres){
        String letra = valor;
        if(letra.trim().length() != cantidadCaracteres){
            int diferencia = cantidadCaracteres - letra.trim().length();
            String espaciosAnt = "";
            String espacios = " ";
            String palabras[] = letra.split(" ");
            System.err.println(palabras.length);
            int posFinal=0;
            System.err.println("diferencia "+diferencia );
            while (diferencia>0){
                letra = "";
                posFinal=0;
                espaciosAnt = espaciosAnt+" ";
                espacios = espacios+" ";
                for(int i=0;i<palabras.length;i++){
                    if(i==0){
                        letra = palabras[i];
                    }else{
                        letra = letra + espacios + palabras[i];
                        diferencia--;
                        if(diferencia<1){
                            break;
                        }
                    }
                    posFinal = i;
                }
            }
            if((posFinal+1)!=palabras.length){
                System.err.println("no llego ");
                for(int k=(posFinal+2);k<palabras.length;k++){
                    letra = letra + espaciosAnt + palabras[k]; 
                }
            }
        }
            return letra;
    }
}
