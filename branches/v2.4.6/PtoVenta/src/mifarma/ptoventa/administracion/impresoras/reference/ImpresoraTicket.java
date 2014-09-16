package mifarma.ptoventa.administracion.impresoras.reference;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import java.util.Map;

import javax.swing.JDialog;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaPRNUtility;
import mifarma.common.FarmaPrintService;
import mifarma.common.FarmaPrintServiceTicket;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.convenio.reference.DBConvenio;
import mifarma.ptoventa.convenioBTLMF.reference.ConstantsConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.recaudacion.reference.FacadeRecaudacion;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : ImpresoraTicket.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      13.06.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class ImpresoraTicket
{    
    private static final Logger log = LoggerFactory.getLogger(ImpresoraTicket.class);
    private int anchoLinea;
    private final int ANCHO_LINEA_DEFAULT = 42;
    public static final int ANCHO_LINEA__TM4950 = 40;
        
    public ImpresoraTicket()
    {
        super();
        this.anchoLinea = ANCHO_LINEA_DEFAULT;        
    }
    
    public ImpresoraTicket(int pAnchoLinea)
    {
        super();
        this.anchoLinea = pAnchoLinea;        
    }
    
    public boolean imprimir(ArrayList<String> pTexto, 
                            String pModelo, 
                            String pRutaImpresora, 
                            boolean vImprimeTestigo, 
                            String pNumComprobante, 
                            String pIndActualizaImpr, 
                            String strNumPedido,
                            String tipoComp)
    {
        boolean vImpr=false;
        if(pModelo == null)
            pModelo = " ";
        
        switch(pModelo)
        {
            case "TMU950":  vImpr = imprimirTMU950(pTexto,pRutaImpresora);
                            break; 
            case "TMU950DK":vImpr = imprimirTMU950DK(pRutaImpresora);
                            break; 
            default:        vImpr = imprimirGenerico(pTexto,pRutaImpresora);
                            break;
        }
		log.info("¿¿IMPRIMIRO?? : "+vImpr);        
        if(vImprimeTestigo)
        {
            try
            {
                /*String strNumPedido="";
                if(pIndActualizaImpr.equals("C")){
                    strNumPedido=VariablesCaja.vNumPedVta;
                }else if(pIndActualizaImpr.equals("A")){
                    strNumPedido=VariablesCaja.vNumPedVta_Anul;
                }*/
                DBCaja.actualizaFechaImpr(strNumPedido,pNumComprobante,pIndActualizaImpr);
            }
            catch (SQLException e)
            {
                log.error("",e);
            }
            imprimirTestigo(pTexto, pNumComprobante, pIndActualizaImpr, tipoComp);            
        }
		log.info("retornas: "+vImpr);
        return vImpr;
    }
    
    private boolean imprimirTMU950(ArrayList<String> pTextos, String pRutaImpresora) {
        FarmaPrintService vPrint = new FarmaPrintService(66, pRutaImpresora, false);
        if ( !vPrint.startPrintService() ) {
            return false;
        }else{
            //
            
            //Contrario, retorna 8, avanza 10
            
            //Espacios de retorno de papel
            /*for(int i=0;i<8;i++){
                vPrint.printLine((char)27+"e"+(char)1,false);
            }*/
            
            //Character spacing
            //vPrint.printLine((char)27+" "+(char)6,false);
            
            //Font
            //vPrint.printLine((char)27+"!"+(char)1,false);
            
            //Metodo de cortar manualmente el texto
            /*for(String linea:pTextos){
                ArrayList<String> tmpLinea = FarmaUtility.splitString(linea, 40);
                for(String tmp:tmpLinea){
                    String cadena = FarmaPRNUtility.alinearIzquierda(tmp, 40);
                    vPrint.printLine(cadena+cadena,true);    
                }
            }*/
            //Metodo seteando la impresora a PARALELO(Receipt, Journal)
            vPrint.printLine((char)27+"z"+(char)1,false);
            for(String linea:pTextos){
                vPrint.printLine(linea,true);    
            }
            vPrint.printLine((char)27+"z"+(char)0,false);
            
            //Espacios para correr el papel
            for(int i=0;i<10;i++){
                vPrint.printLine((char)27+"d"+(char)1,false);
            }
            //Cotar papel
            vPrint.printLine((char)27+"i",false);             
            
            vPrint.endPrintServiceSinCompletar();
            
            return true;
        }
    }

    public boolean abrirGabeta(String pModelo, String pRutaImpresora){    
        boolean vImpr=false;
        if(pModelo == null) pModelo = " ";
        switch(pModelo){
        case "TMU950":
            vImpr = imprimirTMU950DK(pRutaImpresora);
           break; 
        case "TSP700":
            vImpr = imprimirTSP700DK(pRutaImpresora);
           break; 
        default:
            break;
        }
        
        return vImpr;
    }
    
    private boolean imprimirTMU950DK(String pRutaImpresora) {
        FarmaPrintService vPrint = new FarmaPrintService(66, pRutaImpresora, false);
        if ( !vPrint.startPrintService() ) {
            return false;
        }else{
            
            //Abrimos caja
            //27+112+0+25+250
            vPrint.printLine((char)27+"p"+(char)0+(char)25+(char)250,false);
            
            vPrint.endPrintServiceSinCompletar();
            
            return true;
        }
    }
    
    private boolean imprimirTSP700DK(String pRutaImpresora) {
        FarmaPrintService vPrint = new FarmaPrintService(66, pRutaImpresora, false);
        if ( !vPrint.startPrintService() ) {
            return false;
        }else{
            
            //Abrimos caja
            //Star      TSP-700 27,07,11,55,07
            vPrint.printLine(""+(char)27+(char)7+(char)11+(char)55+(char)7,false);
            
            vPrint.endPrintServiceSinCompletarDelivery();
            
            return true;
        }
    }
    
    private boolean imprimirGenerico(ArrayList<String> pTextos, String pRutaImpresora) {
        FarmaPrintServiceTicket vPrint = new FarmaPrintServiceTicket(666, pRutaImpresora, false);
        if ( !vPrint.startPrintService() ) {
            return false;
        }else{            
            for(String linea:pTextos){
                vPrint.printLine(linea,true);    
            }            
            vPrint.endPrintService();
            return true;
        }
    }
    
    private boolean imprimirTestigo(ArrayList<String> pTextos,
                                    String pNumComprobante, 
                                    String pIndActualizaImpr,
                                    String tipoComp)
    {
        String ruta;
        try
        {
            ruta=UtilityPtoVenta.obtieneDirectorioComprobantes();
        }
        catch (SQLException e)
        {
            ruta="";
        }
        Date vFecImpr = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String fechaImpresion =  sdf.format(vFecImpr);                
        
        String vNombreTestigo;
        if(pIndActualizaImpr.equals("C"))
        {
            if("06".equalsIgnoreCase(tipoComp))
                vNombreTestigo=ruta+fechaImpresion+"_TF_"+VariablesCaja.vNumPedVta+"_"+pNumComprobante+".TXT";
            else
                vNombreTestigo=ruta+fechaImpresion+"_T_"+VariablesCaja.vNumPedVta+"_"+pNumComprobante+".TXT";
        }
        else if(pIndActualizaImpr.equals("A"))
        {
            if("06".equalsIgnoreCase(tipoComp))
                vNombreTestigo=ruta+fechaImpresion+"_TF_"+VariablesCaja.vNumPedVta_Anul+"_"+pNumComprobante+"_Anul.TXT";
            else
                vNombreTestigo=ruta+fechaImpresion+"_T_"+VariablesCaja.vNumPedVta_Anul+"_"+pNumComprobante+"_Anul.TXT";
        }
        else
        {
            vNombreTestigo=ruta+fechaImpresion+"_T_Comprobante.TXT";
        }
        
        return imprimirGenerico(pTextos, vNombreTestigo);
    }
    
    public static void main(String[] args){
        ArrayList<String> texto = new ArrayList<String>();
        String ruta = "\\\\10.18.1.179\\TICKET01";
        texto.add("***************************************1");
        /*texto.add("PRUEBA DE IMPRESION TICKETERA");
        texto.add("NOMBRE : "+"EDGAR RIOS NAVARRO");
        texto.add(" ");
        texto.add("***************************************2");  
        texto.add("Esto es una prueba de la impresora de ticket's. Esta linea tiene muchas letras.");  
        texto.add("***************************************0");  */ 
        
        ImpresoraTicket ticketera = new ImpresoraTicket();
        //ticketera.imprimir(texto, "TMU950", ruta, false, "", "");
        ticketera.abrirGabeta("TSP700",ruta);
    }
    
    public void generarDocumento(JDialog pJDialog,
                                 ArrayList<String> vPrint, 
                                 String pNomImpreso, 
                                 String pRUCImpreso,
                                 String pFechaBD, 
                                 String pNumComprobante, 
                                 ArrayList pDetalleComprobante, 
                                 String pValTotalNeto,
                                 String pValTotalAhorro, 
                                 String pValRedondeo, 
                                 String pModelo,
                                 boolean vIndImpresionAnulado,
                                 String tipoCompr,
                                 int    valortick) throws SQLException{
                                 
                generarDocumento(pJDialog,
                                 vPrint, 
                                 pNomImpreso, 
                                 pRUCImpreso,
                                 pFechaBD, 
                                 pNumComprobante, 
                                 pDetalleComprobante, 
                                 pValTotalNeto,
                                 pValTotalAhorro, 
                                 pValRedondeo, 
                                 pModelo,
                                 vIndImpresionAnulado,
                                 tipoCompr,
                                 valortick,
                                 false,
                                 "",
                                 "",
                                 "",
                                 "",
                                 "",
                                 "",
                                 "",
                                 "",
                                 "",
                                 "s"); 
    }
    
    public void generarDocumentoConvenio(JDialog pJDialog,
                                 ArrayList<String> vPrint, 
                                 String pNomImpreso, 
                                 String pRUCImpreso,
                                 String pFechaBD, 
                                 String pNumComprobante, 
                                 ArrayList pDetalleComprobante, 
                                 String pValTotalNeto,
                                 String pValTotalAhorro, 
                                 String pValRedondeo, 
                                 String pModelo,
                                 boolean vIndImpresionAnulado,
                                 String tipoCompr,
                                 int    valortick,
                                 //boolean pConvenio,
                                 String    pValCopagoCompPago,
                                 String    pCodTipoConvenio,
                                 String pTipoClienteConvenio,
                                 String    pValRedondeoComPago,
                                 String pRefTipComp,
                                 String    pNumCompCoPago,
                                 String pImprDatAdic,
                                 String vPrctBeneficiario,
                                 String vPrctEmpresa,
                                 String vIndImprimeEmpresa) throws SQLException{
                                 
                generarDocumento(pJDialog,
                                 vPrint, 
                                 pNomImpreso, 
                                 pRUCImpreso,
                                 pFechaBD, 
                                 pNumComprobante, 
                                 pDetalleComprobante, 
                                 pValTotalNeto,
                                 pValTotalAhorro, 
                                 pValRedondeo, 
                                 pModelo,
                                 vIndImpresionAnulado,
                                 tipoCompr,
                                 valortick,
                                 true,
                                 pValCopagoCompPago,
                                 pCodTipoConvenio,
                                 pTipoClienteConvenio,
                                 pValRedondeoComPago,
                                 pRefTipComp,
                                 pNumCompCoPago,
                                 pImprDatAdic,
                                vPrctBeneficiario,
                                vPrctEmpresa,
                                 vIndImprimeEmpresa); 
    }
    
    private void generarDocumento(JDialog pJDialog,
                                 ArrayList<String> vPrint, 
                                 String pNomImpreso, 
                                 String pRUCImpreso,
                                 String pFechaBD, 
                                 String pNumComprobante, 
                                 ArrayList pDetalleComprobante, 
                                 String pValTotalNeto,
                                 String pValTotalAhorro, 
                                 String pValRedondeo, 
                                 String pModelo,
                                 boolean vIndImpresionAnulado,
                                 String tipoCompr,
                                 int    valortick,
                                  boolean pConvenio,
                                  String    pValCopagoCompPago,
                                  String    pCodTipoConvenio,
                                  String pTipoClienteConvenio,
                                  String    pValRedondeoComPago,
                                  String pRefTipComp,
                                  String    pNumCompCoPago,
                                  String pImprDatAdic,
                                  String vPrctBeneficiario,
                                  String vPrctEmpresa,
                                  String vIndImprimeEmpresa
                                  ) throws SQLException
    {
        switch(pModelo)
        {
            case "TMU950":  anchoLinea = ANCHO_LINEA__TM4950;
                            break; 
            default:        anchoLinea = ANCHO_LINEA_DEFAULT;
                            break;
        }
        
        Calendar fechaJava = Calendar.getInstance();        
        int dia=fechaJava.get(Calendar.DAY_OF_MONTH);
        int resto= dia % 2;
        
        if (resto ==0&&VariablesPtoVenta.vIndImprimeRojo)
            vPrint.add((char)27+"4");  //rojo
        else
            vPrint.add((char)27+"5");  //negro
        
        //DATOS DE CABECERA
        vPrint.add(centrarLinea("BOTICAS "+VariablesPtoVenta.vNombreMarcaCia));
        if("06".equalsIgnoreCase(tipoCompr))
            vPrint.add(centrarLinea("FACTURA - "+ VariablesPtoVenta.vRazonSocialCia));
        else
            vPrint.add(centrarLinea("TICKET - "+ VariablesPtoVenta.vRazonSocialCia));

        vPrint.add(centrarLinea("RUC: "+FarmaVariables.vNuRucCia));
        
        String strDir1 = VariablesPtoVenta.vDireccionCortaMatriz.substring(0, anchoLinea);
        String strDir2 = VariablesPtoVenta.vDireccionCortaMatriz.substring(anchoLinea);
        vPrint.add(centrarLinea(strDir1.trim()));
        vPrint.add(centrarLinea(strDir2.trim()));
        
        if(UtilityVentas.getIndImprimeCorrelativo())
        {   vPrint.add(centrarLinea("Telf: "+VariablesPtoVenta.vTelefonoCia+"          "+"CORR. "+VariablesCaja.vNumPedVta));     
        }
        else
        {   vPrint.add(centrarLinea("Telf: "+VariablesPtoVenta.vTelefonoCia));     
        }
        
        vPrint.add("T"+FarmaVariables.vCodLocal+ " " + FarmaVariables.vDescCortaDirLocal);     
        
        vPrint.add("Serie: "+FarmaPRNUtility.alinearIzquierda(VariablesCaja.vSerieImprLocalTicket,20) + FarmaPRNUtility.alinearDerecha(VariablesCaja.vNumCaja+"-"+VariablesCaja.vNumTurnoCajaImpreso.trim(),13));        
        vPrint.add("Fec:"+pFechaBD+FarmaPRNUtility.llenarBlancos(1)+FarmaPRNUtility.alinearDerecha("Nro:"+pNumComprobante.substring(0,3) + "-" + pNumComprobante.substring(3,10),16));
        
        VariablesVentas.vTipoPedido = DBCaja.obtieneTipoPedido();

        //DATOS DEL TICKET
        switch(pModelo)
        {   case "TMU950":  datosTicketTMU950(pJDialog,             vPrint,
                                              pFechaBD,             pNumComprobante,
                                              pNomImpreso,          pRUCImpreso,
                                              pDetalleComprobante,  pValTotalNeto,
                                              pValTotalAhorro,      pValRedondeo,
                                              valortick);
                            break; 
            default:        datosTicketDefault(pJDialog,            vPrint,
                                               pFechaBD,            pNumComprobante,
                                               pNomImpreso,         pRUCImpreso,
                                               pDetalleComprobante, pValTotalNeto,
                                               pValTotalAhorro,     pValRedondeo,
                                               valortick);
                            break;
        }
        if (VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        {
            vPrint.add(FarmaPRNUtility.alinearIzquierda(" - DISTRIBUCION GRATUITA - ",10));                    
        }

        if( VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_MESON) ||
          VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL) )
        {
            VariablesVentas.vTituloDelivery = "" ;
        } else VariablesVentas.vTituloDelivery = " - PEDIDO DELIVERY - " ;
        
        //Ahorro
        //if(valortick == 0){
            double auxTotalDcto = FarmaUtility.getDecimalNumber(pValTotalAhorro);
            if(auxTotalDcto > 0)
            {            
                String obtenerMensaje="";
                String indFidelizado="";
            
                if(VariablesFidelizacion.vNumTarjeta.trim().length()>0){
                    indFidelizado="S";
                }else 
                {   indFidelizado="N"; }
            
                obtenerMensaje=UtilityCaja.obtenerMensaAhorro(pJDialog,indFidelizado);
                vPrint.add(""+obtenerMensaje+" "+"S/. "+pValTotalAhorro);                    
            }
        //}
        
        double subTotal = 0;
        double SumSubTotal = 0;
        for (int i=0; i<pDetalleComprobante.size(); i++) {
            subTotal = FarmaUtility.getDecimalNumber(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim());            
            SumSubTotal = SumSubTotal + subTotal;
        }
        String vTotal = "";
        if(pConvenio){
            vTotal = FarmaUtility.formatNumber(SumSubTotal);
        }else{
            vTotal = pValTotalNeto; 
        }
        
        vPrint.add(centrarLinea("-","-"));
        vPrint.add("Red. :S/.  " + pValRedondeo + "    Total:S/.  " + vTotal);
        vPrint.add(centrarLinea("=","="));
                
        //*************************************INFORMACION DEL CONVENIO***********************************************//
        double porcCopagoEmpresa=0,porcCopagoBenef=0;
        if(pConvenio){
                
            //double igv =FarmaUtility.getDecimalNumber(pValIgvComPago)+FarmaUtility.getDecimalNumber(pValIgvComCoPago);
            //double total =SumSubTotal;
            porcCopagoBenef = FarmaUtility.getDecimalNumber(vPrctBeneficiario);
            porcCopagoEmpresa = FarmaUtility.getDecimalNumber(vPrctEmpresa);        
                    
            //FLG_TIPO_CONVENIO: 1=Credito; 2=Descuento
            //COD_TIPO_CONVENIO: 1=Copago; 3=Desc. Planilla
            //if(pCodTipoConvenio.equals("1") || pCodTipoConvenio.equals("3"))
            if (pCodTipoConvenio.equals("1")) {

                if (pTipoClienteConvenio.equals("1")) {
                    vPrint.add(FarmaPRNUtility.alinearIzquierda("CoPago(" + FarmaUtility.formatNumber(porcCopagoBenef, "") + "%)    ",20) +"S/."+
                               FarmaPRNUtility.alinearDerecha(pValTotalNeto, 12));
                } else {
                    vPrint.add(FarmaPRNUtility.alinearIzquierda("Credito(" + FarmaUtility.formatNumber(porcCopagoEmpresa, "") + "%)    ",20) +"S/."+
                               FarmaPRNUtility.alinearDerecha(pValCopagoCompPago, 12));
                }
            }
            //vPrint.add(centrarLinea("-", "-"));

            double pValTotalNetoRedondeo =
                FarmaUtility.getDecimalNumber(pValTotalNeto) + FarmaUtility.getDecimalNumber(pValRedondeoComPago);
            //vPrint.printLine("Red. :S/.  " + pValRedondeoComPago,true);
            vPrint.add("Total a pagar:      " + "S/." +
                       FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(pValTotalNetoRedondeo), 12));
        }
        
        //CVILCA 28.10.2013 - INICIO
        // COLOCANDO LAS FORMAS DE PAGO
        FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();
        ArrayList listaFP = new ArrayList();    
        Double vuelto = new Double(0);
        try{
            listaFP = facadeRecaudacion.obtenerDetallePedidoFomasPago(VariablesCaja.vNumPedVta);    
        }catch(Exception e){
            log.error("",e);
            }
            
            //RHERRERA
            String asterix="";
            if (valortick >0)
                    asterix="(*)" ;
            
        //////////////////////////////////////////////////    
        if(listaFP != null && listaFP.size() > 0){
            for(int i = 0; i < listaFP.size(); i++){
                
                String formaPago = ((ArrayList)listaFP.get(i)).get(0).toString()+asterix;
                String importe = ((ArrayList)listaFP.get(i)).get(1).toString();
                vPrint.add(FarmaPRNUtility.alinearIzquierda(formaPago,20) + FarmaPRNUtility.alinearDerecha(importe,15));
                vuelto = vuelto + FarmaUtility.getDecimalNumber(((ArrayList)listaFP.get(i)).get(2).toString().trim());
            }
        }
        
        vPrint.add("                   ---------------------");
        //vPrint.add(FarmaPRNUtility.alinearIzquierda("Total a pagar",20)+ "S/. "+FarmaPRNUtility.alinearDerecha(pValTotalNeto,11));
        vPrint.add(FarmaPRNUtility.alinearIzquierda("Vuelto"+asterix,20)+ "S/. " +FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(vuelto),11));
        //CVILCA 28.10.2013 - FIN
        
        if(pConvenio){
            //ERIOS 11.11.2013 Imprime Datos Convenio
            if(!VariablesConvenioBTLMF.vCodConvenio.equals(ConstantsConvenioBTLMF.COD_CONV_COMPETENCIA)){
                vPrint.add(centrarLinea("-","-"));

                //ERIOS 2.4.3 Imprime Ruc y Razon Social
                if (pCodTipoConvenio.equals("1") || pCodTipoConvenio.equals("3")) {
                    if (vIndImprimeEmpresa.equals("1")) {
                        vPrint.add("Ruc Cliente  : " + VariablesConvenioBTLMF.vRuc.trim().toUpperCase());
                        vPrint.add("Razon Social : " + VariablesConvenioBTLMF.vInstitucion.trim().toUpperCase());
                        vPrint.add("");
                    }
                }

                vPrint.add("Convenio: " + VariablesConvenioBTLMF.vNomConvenio.toUpperCase());

                String nomBenef;
                String dniBenef = null;

                if (VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic != null &&  VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.size() > 0){
                    for (int j = 0; j < VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.size(); j++){
                       Map datosAdicConv = (Map)VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.get(j);    
                        String vCodCampo    = (String)datosAdicConv.get("COD_CAMPO");
                        if(vCodCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_BENIFICIARIO)){
                            if (j == 0) {
                                nomBenef = (String)datosAdicConv.get("DESCRIPCION_CAMPO");
                                dniBenef = (String)datosAdicConv.get("COD_VALOR_IN");
    
                                vPrint.add("Benef: " + nomBenef.toUpperCase());
                            }
                            if (j == 1) {
                                dniBenef = (String)datosAdicConv.get("DESCRIPCION_CAMPO");
    
                                vPrint.add("       DNI: " + dniBenef.toUpperCase());
                            }
                        }
                    }
                } else {

                    vPrint.add("  NO CUENTA CON DATOS DE BENEFICIARIO");
                }

                if (pCodTipoConvenio.equals("1")) {
                    String vRefTipComp = "";

                    if (pRefTipComp.equals(ConstantsVentas.TIPO_COMP_BOLETA))
                        vRefTipComp = "BOL";
                    if (pRefTipComp.equals(ConstantsVentas.TIPO_COMP_FACTURA))
                        vRefTipComp = "FAC";
                    if (pRefTipComp.equals(ConstantsVentas.TIPO_COMP_GUIA))
                        vRefTipComp = "GUIA";
                    if (pRefTipComp.equals(ConstantsVentas.TIPO_COMP_TICKET))
                        vRefTipComp = "TKB";

                    vPrint.add("#REF: " + vRefTipComp + " " + pNumCompCoPago + " " + "(" +
                               FarmaUtility.formatNumber(porcCopagoEmpresa, "") + "%)" + " - S/. " + pValCopagoCompPago);

                }

                if (pImprDatAdic.equals("1")) {
                    if (VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic != null &&
                        VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.size() > 0) {

                        vPrint.add("  Datos Adicionales");
                        for (int j = 0; j < VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.size(); j++) {
                            Map datosAdicConv = (Map)VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.get(j);

                            String pNombCampo = (String)datosAdicConv.get("NOMBRE_CAMPO");
                            String pDesCampo = (String)datosAdicConv.get("DESCRIPCION_CAMPO");
                            String vFlgImprime = (String)datosAdicConv.get("FLG_IMPRIME");

                            if (vFlgImprime.equals("1") || vFlgImprime.equals("2")) {

                                vPrint.add("  - " + pNombCampo + "    " + pDesCampo + " ");
                            }
                        }
                    }
                }
                //ERIOS 2.4.3 Imprime
                if (pCodTipoConvenio.equals("1")) {
                    if (pTipoClienteConvenio.equals("1")) {
                        vPrint.add("B E N E F I C I A R I O - (" + FarmaUtility.formatNumber(porcCopagoBenef, "") +
                                   "%)");

                    } else {
                        vPrint.add("I N S T I T U C I O N - (" + FarmaUtility.formatNumber(porcCopagoEmpresa, "") + "%)");

                    }
                } else if (pCodTipoConvenio.equals("3")) {
                    vPrint.add("I N S T I T U C I O N - (" + FarmaUtility.formatNumber(porcCopagoEmpresa, "") + "%)");
                }

                if (dniBenef != null && !dniBenef.trim().equals("")) {
                    vPrint.add("");
                    vPrint.add("DNI: " + dniBenef);
                }
            }
        }
            
        //DATOS PIE DE PAGINA
        vPrint.add("");
        VariablesCaja.vFormasPagoImpresion = DBCaja.obtieneFormaPagoPedido();
        
        int pos= VariablesCaja.vFormasPagoImpresion.indexOf("Tipo Cambio: ");
        String tcambio,fpago;        
        String pCajero = "CJ: " + FarmaVariables.vIdUsu ;        
        vPrint.add(pCajero);
        
        if(pNomImpreso != null && pNomImpreso.trim().length()>0)
        {
            if(pRUCImpreso != null && pRUCImpreso.trim().length()>0)
            {
                vPrint.add("RAZON SOCIAL: "+pNomImpreso.trim());         
            }else{
                vPrint.add("CLIENTE: "+pNomImpreso.trim()); 
            }
            
        }
        if(pRUCImpreso != null && pRUCImpreso.trim().length()>0)
        {
            vPrint.add("RUC: "+pRUCImpreso.trim());     
        }
        
        if (pos != -1)
        {
            tcambio = VariablesCaja.vFormasPagoImpresion.substring(pos);
            fpago = VariablesCaja.vFormasPagoImpresion.substring(0, pos - 1);
            
            //CVILCA 28.10.2013
            vPrint.add(tcambio );                    
        }
        
        vPrint.add(FarmaPRNUtility.llenarBlancos(10) + VariablesVentas.vTituloDelivery );
        
        String msgCumImpresos = " ";
        if(VariablesCaja.vNumCuponesImpresos>0)
        {
            String msgNumCupon = "";
            if(VariablesCaja.vNumCuponesImpresos==1)
            {   msgNumCupon = "CUPON";
            }
            else
            {   msgNumCupon = "CUPONES";
            }
            msgCumImpresos = " UD. GANO "+VariablesCaja.vNumCuponesImpresos+ " "+msgNumCupon;
        }
        
        if(VariablesCaja.vNumCuponesImpresos>0)
        {   vPrint.add(centrarLinea(msgCumImpresos,"*"));                   
        }
        
        vPrint.add(centrarLinea("No se aceptan devoluciones de dinero." ));
        vPrint.add(centrarLinea("Cambio de mercaderia unicamente dentro" ));
        vPrint.add(centrarLinea("de las 48 horas siguientes a la compra."));
        vPrint.add(centrarLinea("Indispensable presentar comprobante."));                
        //vPrint.add(" ");                
        
        if(VariablesCaja.vImprimeFideicomizo)
        {
            String[] lineas = VariablesCaja.vCadenaFideicomizo.trim().split("@");
            String pCadena = "";
            if(lineas.length>0)
            {
                for(int i=0;i<lineas.length;i++)
                {   pCadena += lineas[i] + " ";
                }                        
                vPrint.add(""+pCadena.trim());                        
            }
            else
            {
                vPrint.add(""+VariablesCaja.vCadenaFideicomizo.trim());                        
            }
        }
        
        //LLEIVA 04-Oct-2013 Modificacion
        vPrint.add(centrarLinea("--","-"));
        //ERIOS 28.10.2013 Imprime pagina web
        if(!FarmaConstants.INDICADOR_N.equalsIgnoreCase(VariablesPtoVenta.vIndImprWeb))
        {
            vPrint.add(centrarLinea(VariablesPtoVenta.vIndImprWeb,"*"));
        }
        //ERIOS 12.09.2013 Imprime central delivery        
        String mensaje=DBCaja.obtieneMensajeTicket();
        if(!mensaje.equalsIgnoreCase("N"))
        {
            vPrint.add(centrarLinea(mensaje));
        }
        
        if(vIndImpresionAnulado)
        {
            vPrint.add(centrarLinea("=","="));
            vPrint.add(centrarLinea("...COMPROBANTE ANULADO...","*"));
            vPrint.add(centrarLinea("=","="));
        }
        //ERIOS 2.4.0 Impresion de version
        vPrint.add(VariablesPtoVenta.vVersion);
    }
    
    private String centrarLinea(String pCadena){
        return  centrarLinea(pCadena," ");
    }
    
    public static String centrarLinea(String pCadena,int anchoLinea){
        return  (new ImpresoraTicket(anchoLinea)).centrarLinea(pCadena," ");
    }
    
    public static String centrarLinea(String pCadena,String pCaracter,int anchoLinea){
        return (new ImpresoraTicket(anchoLinea)).centrarLinea(pCadena,pCaracter);
    }
    
    private String centrarLinea(String pCadena,String pCaracter){
        int pTamaño = pCadena.trim().length();
        int numeroPos = (int)Math.floor((anchoLinea - pTamaño)/2);
        String pCadenaNew = "";
        for(int i=0;i<numeroPos;i++){
            pCadenaNew += pCaracter;
        }
        pCadenaNew += pCadena.trim();
        pTamaño =  anchoLinea - pCadenaNew.length();
        
        for(int i=0;i<pTamaño;i++){
            pCadenaNew += pCaracter;
        }
        
        return  pCadenaNew;
    }

    private void datosTicketDefault(JDialog pJDialog,               ArrayList<String> vPrint, 
                                    String pFechaBD,                String pNumComprobante, 
                                    String pNomImpreso,             String pRUCImpreso,
                                    ArrayList pDetalleComprobante,  String pValTotalNeto, 
                                    String pValTotalAhorro,         String pValRedondeo,
                                    int valortick) throws SQLException
    {
        //vPrint.add("------------------------------------------");   
        vPrint.add(centrarLinea("-","-"));
        vPrint.add("Cant.    Descripcion     Dscto   Importe");     
        //vPrint.add("------------------------------------------");      
        vPrint.add(centrarLinea("-","-"));
        
        int linea = 0;                    
        for (int i=0; i<pDetalleComprobante.size(); i++){
            String valor = ((String)((ArrayList)pDetalleComprobante.get(i)).get(16)).toString().trim();
        
            if(valor.equals("0.000")) valor = " ";
        
            double valor1 =  (UtilityVentas.Redondear(FarmaUtility.getDecimalNumber(valor),2));
        
            if(valor1==0.0){
                valor = "";
            }else{
                valor = Double.toString(valor1);
            }
        
            vPrint.add("" +
                             UtilityCaja.pFormatoLetra(FarmaUtility.getValueFieldArrayList(pDetalleComprobante,i,0),9," ")+ "  " +
                             FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),27) + 
                             "       "+ 
                             //UNIDAD                            
                             FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),11) + " " +
                             //LAB                             
                             FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),9) + " "+
                             //AHORRO         
                             FarmaPRNUtility.alinearDerecha(valor,5) + "  " +
                             //PRECIO
                             FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),10)
                             );                                
                     
                                               
            linea += 1;
            String indProdVirtual = FarmaUtility.getValueFieldArrayList(pDetalleComprobante, i, 8);
            //verifica que solo se imprima un producto virtual en el comprobante
            if(i==0 && indProdVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
              VariablesCaja.vIndPedidoConProdVirtualImpresion = true;
            else
              VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
        }

        //  RECARGAS VIRTUALES
        if(VariablesCaja.vIndPedidoConProdVirtualImpresion)
        {
        
            vPrint.add("");                  
            
            UtilityCaja.impresionInfoVirtualTicket(vPrint,
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 9),//tipo prod virtual
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 13),//codigo aprobacion
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 11),//numero tarjeta
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 12),//numero pin
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 10),//numero telefono
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 5),//monto
                                 VariablesCaja.vNumPedVta,//Se añadio el parametro
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 6));//cod_producto
            linea = linea + 4;                    
        }
        
        if (VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        {
          linea++;
        }
        
    }
    
    private void datosTicketTMU950( JDialog pJDialog,               ArrayList<String> vPrint, 
                                    String pFechaBD,                String pNumComprobante, 
                                    String pNomImpreso,             String pRUCImpreso,
                                    ArrayList pDetalleComprobante,  String pValTotalNeto,
                                    String pValTotalAhorro,         String pValRedondeo,
                                    int     valortick) throws SQLException
    {
        //vPrint.add("----------------------------------------");    
        vPrint.add(centrarLinea("-","-"));
        vPrint.add("Cant.     Descripcion    Dscto   Importe");     
        //vPrint.add("----------------------------------------");     
        vPrint.add(centrarLinea("-","-"));
        
        int linea = 0;                    
        for (int i=0; i<pDetalleComprobante.size(); i++){
            String valor = ((String)((ArrayList)pDetalleComprobante.get(i)).get(16)).toString().trim();
        
            if(valor.equals("0.000")) valor = " ";
        
            double valor1 =  (UtilityVentas.Redondear(FarmaUtility.getDecimalNumber(valor),2));
        
            if(valor1==0.0){
                valor = "";
            }else{
                valor = Double.toString(valor1);
            }
        
            vPrint.add("" +
                             centrarLinea(FarmaUtility.getValueFieldArrayList(pDetalleComprobante,i,0),9)+ " " +
                             FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),27) + "   "
                       );
            vPrint.add("  " +
                             //UNIDAD                            
                             FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),11) + " " +
                             //LAB                             
                             FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),9) + " "+
                             //AHORRO         
                             FarmaPRNUtility.alinearDerecha(valor,5) + " " +
                             //PRECIO
                             FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),10)
                             );                                
                     
                                               
            linea += 1;
            String indProdVirtual = FarmaUtility.getValueFieldArrayList(pDetalleComprobante, i, 8);
            //verifica que solo se imprima un producto virtual en el comprobante
            if(i==0 && indProdVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
              VariablesCaja.vIndPedidoConProdVirtualImpresion = true;
            else
              VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
        }
        
        //  RECARGAS VIRTUALES
        if(VariablesCaja.vIndPedidoConProdVirtualImpresion)
        {
        
            vPrint.add("");                  
            
            UtilityCaja.impresionInfoVirtualTicket(vPrint,
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 9),//tipo prod virtual
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 13),//codigo aprobacion
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 11),//numero tarjeta
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 12),//numero pin
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 10),//numero telefono
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 5),//monto
                                 VariablesCaja.vNumPedVta,//Se añadio el parametro
                                 FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 6));//cod_producto
            linea = linea + 4;                    
        }
        
        if (VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        {
          linea++;
        }
        
    }
}
