package mifarma.ptoventa.administracion.impresoras.reference;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
public class ImpresoraTicket {
    
    private static final Logger log = LoggerFactory.getLogger(ImpresoraTicket.class);

    private int anchoLinea;
    private final int ANCHO_LINEA_DEFAULT = 42;
    public static final int ANCHO_LINEA__TM4950 = 40;
        
    public ImpresoraTicket() {
        super();
        this.anchoLinea = ANCHO_LINEA_DEFAULT;        
    }
    
    public ImpresoraTicket(int pAnchoLinea) {
        super();
        this.anchoLinea = pAnchoLinea;        
    }
    
    public boolean imprimir(ArrayList<String> pTexto, String pModelo, String pRutaImpresora, boolean vImprimeTestigo, String pNumComprobante, String pIndActualizaImpr, String strNumPedido){    
        boolean vImpr=false;
        if(pModelo == null) pModelo = " ";
        switch(pModelo){
        case "TMU950":
            vImpr = imprimirTMU950(pTexto,pRutaImpresora);
           break; 
        case "TMU950DK":
            vImpr = imprimirTMU950DK(pRutaImpresora);
           break; 
        default:
            vImpr = imprimirGenerico(pTexto,pRutaImpresora);
            break;
        }
        
        if(vImprimeTestigo){
            try {
                /*String strNumPedido="";
                if(pIndActualizaImpr.equals("C")){
                    strNumPedido=VariablesCaja.vNumPedVta;
                }else if(pIndActualizaImpr.equals("A")){
                    strNumPedido=VariablesCaja.vNumPedVta_Anul;
                }*/
                DBCaja.actualizaFechaImpr(strNumPedido,pNumComprobante,pIndActualizaImpr);
            } catch (SQLException e) {
                log.error("",e);
            }
            imprimirTestigo(pTexto, pNumComprobante, pIndActualizaImpr);            
        }
        return vImpr;
    }
    
    private boolean imprimirTMU950(ArrayList<String> pTextos, String pRutaImpresora) {
        FarmaPrintService vPrint = new FarmaPrintService(66, pRutaImpresora, false);
        if ( !vPrint.startPrintService() ) {
            return false;
        }else{
            //Si NO cortamos papel, retorna 10, avanza 12
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
    
    private boolean imprimirTestigo(ArrayList<String> pTextos,String pNumComprobante, String pIndActualizaImpr){
        String ruta;
        try {
            ruta=UtilityPtoVenta.obtieneDirectorioComprobantes();
        } catch (SQLException e) {
            ruta="";
        }
        Date vFecImpr = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String fechaImpresion =  sdf.format(vFecImpr);                
        
        String vNombreTestigo;
        if(pIndActualizaImpr.equals("C")){
            vNombreTestigo=ruta+fechaImpresion+"_T_"+VariablesCaja.vNumPedVta+"_"+pNumComprobante+".TXT";
        }else if(pIndActualizaImpr.equals("A")){
            vNombreTestigo=ruta+fechaImpresion+"_T_"+VariablesCaja.vNumPedVta_Anul+"_"+pNumComprobante+"_Anul.TXT";
        }else{
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
    
    public void generarDocumento(JDialog   pJDialog,
                                 ArrayList<String> vPrint, String pNomImpreso, String pFechaBD, String pNumComprobante, ArrayList pDetalleComprobante, String pValTotalNeto,
                                 String pValTotalAhorro, String pValRedondeo, String pModelo,
                                 boolean vIndImpresionAnulado) throws SQLException {
        switch(pModelo){
        case "TMU950":
            anchoLinea = ANCHO_LINEA__TM4950;
           break; 
        default:
            anchoLinea = ANCHO_LINEA_DEFAULT;
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
        vPrint.add(centrarLinea("TICKET - "+ VariablesPtoVenta.vRazonSocialCia));
        vPrint.add(centrarLinea("RUC: "+FarmaVariables.vNuRucCia));
        String strDir1 = VariablesPtoVenta.vDireccionCortaMatriz.substring(0, anchoLinea);
        String strDir2 = VariablesPtoVenta.vDireccionCortaMatriz.substring(anchoLinea);
        vPrint.add(centrarLinea(strDir1.trim()));
        vPrint.add(centrarLinea(strDir2.trim()));
        
        if(UtilityVentas.getIndImprimeCorrelativo()){
            vPrint.add(centrarLinea("Telf: "+VariablesPtoVenta.vTelefonoCia+"          "+"CORR. "+VariablesCaja.vNumPedVta));     
        }else{
            vPrint.add(centrarLinea("Telf: "+VariablesPtoVenta.vTelefonoCia));     
        }
        
        vPrint.add("T"+FarmaVariables.vCodLocal+ " " + FarmaVariables.vDescCortaDirLocal);     
        
        //DATOS DEL TICKET
            switch(pModelo){
            case "TMU950":
                datosTicketTMU950(pJDialog,vPrint,pFechaBD,pNumComprobante,pNomImpreso,pDetalleComprobante,pValTotalNeto,pValTotalAhorro,pValRedondeo);
               break; 
            default:
                datosTicketDefault(pJDialog,vPrint,pFechaBD,pNumComprobante,pNomImpreso,pDetalleComprobante,pValTotalNeto,pValTotalAhorro,pValRedondeo);
                break;
            }
                      
        //DATOS PIE DE PAGINA
        int pos= VariablesCaja.vFormasPagoImpresion.indexOf("Tipo Cambio: ");
        String tcambio,fpago;
        String pCajero = "CJ: " + FarmaVariables.vIdUsu ;
        vPrint.add(pCajero);
        if (pos != -1)
        {
            tcambio = VariablesCaja.vFormasPagoImpresion.substring(pos);
            fpago = VariablesCaja.vFormasPagoImpresion.substring(0, pos - 1);
            
            //CVILCA 28.10.2013
            vPrint.add(tcambio );                    
        }
        
        vPrint.add(FarmaPRNUtility.llenarBlancos(10) + VariablesVentas.vTituloDelivery );
        
        String msgCumImpresos = " ";
        if(VariablesCaja.vNumCuponesImpresos>0){
            String msgNumCupon = "";
          if(VariablesCaja.vNumCuponesImpresos==1){
              msgNumCupon = "CUPON";
          }
          else{
              msgNumCupon = "CUPONES";
          }
          msgCumImpresos = " UD. GANO "+VariablesCaja.vNumCuponesImpresos+ " "+msgNumCupon;
        }
        
        if(VariablesCaja.vNumCuponesImpresos>0){
           vPrint.add("                         "+msgCumImpresos);                   
        }
        
        vPrint.add(centrarLinea("No se aceptan devoluciones de dinero." ));
        vPrint.add(centrarLinea("Cambio de mercadería únicamente dentro" ));
        vPrint.add(centrarLinea("de las 48 horas siguientes a la compra."));
        vPrint.add(centrarLinea("Indispensable presentar comprobante."));                
        vPrint.add(" ");                
        
        if(VariablesCaja.vImprimeFideicomizo){
            String[] lineas = VariablesCaja.vCadenaFideicomizo.trim().split("@");
            String pCadena = "";
            if(lineas.length>0){
                for(int i=0;i<lineas.length;i++){
                    pCadena += lineas[i] + " ";
                }                        
                vPrint.add(""+pCadena.trim());                        
            }
            else{
                vPrint.add(""+VariablesCaja.vCadenaFideicomizo.trim());                        
            }
        }
        
        //LLEIVA 04-Oct-2013 Modificacion
        vPrint.add(centrarLinea("--","-"));
        //ERIOS 28.10.2013 Imprime pagina web
        if(VariablesPtoVenta.vIndImprWeb.equals(FarmaConstants.INDICADOR_S)){
            if(FarmaVariables.vCodCia.equals("001"))
                vPrint.add(centrarLinea("www.mifarma.com.pe","*"));
            else if(FarmaVariables.vCodCia.equals("002"))
                vPrint.add(centrarLinea("www.fasa.com.pe","*"));
            else if(FarmaVariables.vCodCia.equals("003"))
                vPrint.add(centrarLinea("www.btl.com.pe","*"));
        }
        //ERIOS 12.09.2013 Imprime central delivery        
        String mensaje=DBCaja.obtieneMensajeTicket();
        if(!mensaje.equalsIgnoreCase("N")){
            vPrint.add(centrarLinea(mensaje));
        }
        
        if(vIndImpresionAnulado){
            vPrint.add(centrarLinea("=","="));
            vPrint.add(centrarLinea("...COMPROBANTE ANULADO...","*"));
            vPrint.add(centrarLinea("=","="));
        }
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

    private void datosTicketDefault(JDialog pJDialog,ArrayList<String> vPrint, String pFechaBD, String pNumComprobante, 
                                    String pNomImpreso, ArrayList pDetalleComprobante, String pValTotalNeto, String pValTotalAhorro, String pValRedondeo) throws SQLException {
        vPrint.add(centrarLinea("Serie: "+FarmaPRNUtility.alinearIzquierda(VariablesCaja.vSerieImprLocalTicket,20)+"    " + FarmaPRNUtility.alinearDerecha(VariablesCaja.vNumCaja,7)+"-"+VariablesCaja.vNumTurnoCajaImpreso.trim()));        
        vPrint.add("Fecha:"+pFechaBD+FarmaPRNUtility.llenarBlancos(1)+FarmaPRNUtility.alinearDerecha("Nro: "+pNumComprobante.substring(0,3) + "-" + pNumComprobante.substring(3,10),16));
        
        if(pNomImpreso.trim().length()>0){
            vPrint.add(FarmaPRNUtility.llenarBlancos(1) + FarmaPRNUtility.alinearIzquierda("CLIENTE:"+pNomImpreso.trim(),41));     
        }
        
        vPrint.add(" Cant."+"   "+"Descripcion"+"       Dscto"+"   Importe" );     
        
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

        if(VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        {
            try
            {
                ArrayList aInfoPedConv = new ArrayList();
        
                DBConvenio.obtieneInfoPedidoConv(aInfoPedConv,VariablesCaja.vNumPedVta, ""+FarmaUtility.getDecimalNumber(pValTotalNeto));

                vPrint.add("------------------------------------------");        
        
                for(int i=0; i<aInfoPedConv.size(); i++)
                {
                    ArrayList registro = (ArrayList) aInfoPedConv.get(i);
        
                    String Ind_Comp=((String)registro.get(8)).trim();
                    if(Ind_Comp.equalsIgnoreCase("N")){
                        vPrint.add(FarmaPRNUtility.alinearIzquierda("Titular Cliente: "+((String)registro.get(4)).trim(),41)+"\n "+
                                         FarmaPRNUtility.alinearIzquierda("Co-Pago: "+((String)registro.get(3)).trim()+" %",20));
        
                        String vCredDisp = ((String)registro.get(7)).trim();
                        if(vCredDisp.equals(""))
                        {
                          vPrint.add(
                                           FarmaPRNUtility.alinearIzquierda("Credito: S/."+((String)registro.get(5)).trim(),18)+" "+
                                           FarmaPRNUtility.alinearDerecha("A Cuenta: S/."+((String)registro.get(6)).trim(),21));                                                   
                        }else
                        {
                          vPrint.add(
                                           FarmaPRNUtility.alinearIzquierda("Credito: S/."+((String)registro.get(5)).trim(),18)+" "+
                                           FarmaPRNUtility.alinearDerecha("A Cuenta: S/."+((String)registro.get(6)).trim(),21));
                            vPrint.add("Cred Disp: S/."+vCredDisp);                                    
                        } 
                    } 
                }
            }catch(SQLException sql)
            {
                VariablesCaja.vEstadoSinComprobanteImpreso="S";
            
                log.info("**** Fecha :"+ pFechaBD);
                log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                log.info("**** NUMERO COMPROBANTE BOLETA:" + pNumComprobante);
                log.info("**** IP :" + FarmaVariables.vIpPc);
                log.info("Error al obtener informacion del Pedido Convenio ");
                log.info("Error al imprimir la BOLETA 2: ");
                log.error(null,sql);
            
                UtilityCaja.enviaErrorCorreoPorDB(sql.toString(),VariablesCaja.vNumPedVta);
            }catch(Exception e){
            
                VariablesCaja.vEstadoSinComprobanteImpreso="S";
            
                log.info("**** Fecha :"+ pFechaBD);
                log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                log.info("**** NUMERO COMPROBANTE BOLETA :" + pNumComprobante);
                log.info("**** IP :" + FarmaVariables.vIpPc);
                log.info("Error al imprimir la BOLETA 3: "+e);
            
                UtilityCaja.enviaErrorCorreoPorDB(e.toString(),VariablesCaja.vNumPedVta);
            }
        }
        
        double auxTotalDcto = FarmaUtility.getDecimalNumber(pValTotalAhorro);

        VariablesVentas.vTipoPedido = DBCaja.obtieneTipoPedido();
        VariablesCaja.vFormasPagoImpresion = DBCaja.obtieneFormaPagoPedido();

        if (VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        {
            vPrint.add(FarmaPRNUtility.alinearIzquierda(" - DISTRIBUCION GRATUITA - ",10));                    
        }

        if( VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_MESON) ||
          VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL) )
        {
            VariablesVentas.vTituloDelivery = "" ;
        } else VariablesVentas.vTituloDelivery = " - PEDIDO DELIVERY - " ;
        
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
        
        vPrint.add("------------------------------------------");
        vPrint.add("Red. :S/.  " + pValRedondeo + "    Total:S/.  " + pValTotalNeto);
        vPrint.add("==========================================");  
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
        if(listaFP != null && listaFP.size() > 0){
            for(int i = 0; i < listaFP.size(); i++){
                
                String formaPago = ((ArrayList)listaFP.get(i)).get(0).toString();
                String importe = ((ArrayList)listaFP.get(i)).get(1).toString();
                log.info("forma de pago : " + formaPago);
                log.info("importe : " + importe);
                vPrint.add(FarmaPRNUtility.alinearIzquierda(formaPago,20) + FarmaPRNUtility.alinearDerecha(importe,15));
                vuelto = vuelto + FarmaUtility.getDecimalNumber(((ArrayList)listaFP.get(i)).get(2).toString().trim());
            }
        }
        log.info("vuelto : " + vuelto);
        vPrint.add("                   -----------------------");
        vPrint.add(FarmaPRNUtility.alinearIzquierda("Total a pagar",20)+ "S/. "+FarmaPRNUtility.alinearDerecha(pValTotalNeto,11));
        vPrint.add(FarmaPRNUtility.alinearIzquierda("Vuelto",20)+ "S/. " +FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(vuelto),11));
        //CVILCA 28.10.2013 - FIN
    }
    
    private void datosTicketTMU950(JDialog pJDialog,ArrayList<String> vPrint, String pFechaBD, String pNumComprobante, 
                                    String pNomImpreso, ArrayList pDetalleComprobante, String pValTotalNeto, String pValTotalAhorro, String pValRedondeo) throws SQLException {
        vPrint.add("Serie: "+FarmaPRNUtility.alinearIzquierda(VariablesCaja.vSerieImprLocalTicket,20) + FarmaPRNUtility.alinearDerecha(VariablesCaja.vNumCaja+"-"+VariablesCaja.vNumTurnoCajaImpreso.trim(),13));        
        vPrint.add("Fec:"+pFechaBD+FarmaPRNUtility.llenarBlancos(1)+FarmaPRNUtility.alinearDerecha("Nro:"+pNumComprobante.substring(0,3) + "-" + pNumComprobante.substring(3,10),16));
        
        if(pNomImpreso.trim().length()>0){
            vPrint.add(" CLIENTE:"+pNomImpreso.trim());     
        }
        
        vPrint.add("Cant.    Descripcion     Dscto   Importe");     
        
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
                             FarmaPRNUtility.alinearDerecha(FarmaUtility.getValueFieldArrayList(pDetalleComprobante,i,0),5)+ "    " +
                             FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),27) + "    "
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

        if(VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        {
            try
            {
                ArrayList aInfoPedConv = new ArrayList();
        
                DBConvenio.obtieneInfoPedidoConv(aInfoPedConv,VariablesCaja.vNumPedVta, ""+FarmaUtility.getDecimalNumber(pValTotalNeto));

                vPrint.add("----------------------------------------");        
        
                for(int i=0; i<aInfoPedConv.size(); i++)
                {
                    ArrayList registro = (ArrayList) aInfoPedConv.get(i);
        
                    String Ind_Comp=((String)registro.get(8)).trim();
                    if(Ind_Comp.equalsIgnoreCase("N")){
                        vPrint.add(FarmaPRNUtility.alinearIzquierda("Titular Cliente: "+((String)registro.get(4)).trim(),41)+"\n "+
                                         FarmaPRNUtility.alinearIzquierda("Co-Pago: "+((String)registro.get(3)).trim()+" %",20));
        
                        String vCredDisp = ((String)registro.get(7)).trim();
                        if(vCredDisp.equals(""))
                        {
                          vPrint.add(
                                           FarmaPRNUtility.alinearIzquierda("Credito: S/."+((String)registro.get(5)).trim(),18)+" "+
                                           FarmaPRNUtility.alinearDerecha("A Cuenta: S/."+((String)registro.get(6)).trim(),21));                                                   
                        }else
                        {
                          vPrint.add(
                                           FarmaPRNUtility.alinearIzquierda("Credito: S/."+((String)registro.get(5)).trim(),18)+" "+
                                           FarmaPRNUtility.alinearDerecha("A Cuenta: S/."+((String)registro.get(6)).trim(),21));
                            vPrint.add("Cred Disp: S/."+vCredDisp);                                    
                        } 
                    } 
                }
            }catch(SQLException sql)
            {
                VariablesCaja.vEstadoSinComprobanteImpreso="S";
            
                log.info("**** Fecha :"+ pFechaBD);
                log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                log.info("**** NUMERO COMPROBANTE BOLETA:" + pNumComprobante);
                log.info("**** IP :" + FarmaVariables.vIpPc);
                log.info("Error al obtener informacion del Pedido Convenio ");
                log.info("Error al imprimir la BOLETA 2: ");
                log.error(null,sql);
            
                UtilityCaja.enviaErrorCorreoPorDB(sql.toString(),VariablesCaja.vNumPedVta);
            }catch(Exception e){
            
                VariablesCaja.vEstadoSinComprobanteImpreso="S";
            
                log.info("**** Fecha :"+ pFechaBD);
                log.info("**** CORR :"+ VariablesCaja.vNumPedVta);
                log.info("**** NUMERO COMPROBANTE BOLETA :" + pNumComprobante);
                log.info("**** IP :" + FarmaVariables.vIpPc);
                log.info("Error al imprimir la BOLETA 3: "+e);
            
                UtilityCaja.enviaErrorCorreoPorDB(e.toString(),VariablesCaja.vNumPedVta);
            }
        }
        
        double auxTotalDcto = FarmaUtility.getDecimalNumber(pValTotalAhorro);

        VariablesVentas.vTipoPedido = DBCaja.obtieneTipoPedido();
        VariablesCaja.vFormasPagoImpresion = DBCaja.obtieneFormaPagoPedido();

        if (VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        {
            vPrint.add(FarmaPRNUtility.alinearIzquierda(" - DISTRIBUCION GRATUITA - ",10));                    
        }

        if( VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_MESON) ||
          VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL) )
        {
            VariablesVentas.vTituloDelivery = "" ;
        } else VariablesVentas.vTituloDelivery = " - PEDIDO DELIVERY - " ;
        
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
        
        vPrint.add("----------------------------------------");
        vPrint.add("Red. :S/.  " + pValRedondeo + "    Total:S/.  " + pValTotalNeto);
        vPrint.add("========================================");
        
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
        if(listaFP != null && listaFP.size() > 0){
            for(int i = 0; i < listaFP.size(); i++){
                
                String formaPago = ((ArrayList)listaFP.get(i)).get(0).toString();
                String importe = ((ArrayList)listaFP.get(i)).get(1).toString();
                log.info("forma de pago : " + formaPago);
                log.info("importe : " + importe);
                vPrint.add(FarmaPRNUtility.alinearIzquierda(formaPago,20) + FarmaPRNUtility.alinearDerecha(importe,15));
                vuelto = vuelto + FarmaUtility.getDecimalNumber(((ArrayList)listaFP.get(i)).get(2).toString().trim());
            }
        }
        log.info("vuelto : " + vuelto);
        vPrint.add("                   -----------------------");
        vPrint.add(FarmaPRNUtility.alinearIzquierda("Total a pagar",20)+ "S/. " +FarmaPRNUtility.alinearDerecha(pValTotalNeto,11));
        vPrint.add(FarmaPRNUtility.alinearIzquierda("Vuelto",20)+ "S/. " +FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(vuelto),11));
        //CVILCA 28.10.2013 - FIN
    }
}
