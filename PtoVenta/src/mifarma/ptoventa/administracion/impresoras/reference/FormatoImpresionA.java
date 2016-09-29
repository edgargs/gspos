package mifarma.ptoventa.administracion.impresoras.reference;

import java.util.ArrayList;
import java.util.Map;

import javax.swing.JDialog;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaPRNUtility;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.convenioBTLMF.reference.ConstantsConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.DBConvenio;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.puntos.reference.VariablesPuntos;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Formatos para comprobantes de MIFARMA
 * @author ERIOS
 * @since 20.01.2016
 */
public class FormatoImpresionA implements StrategyImpresion {
    
    private static final Logger log = LoggerFactory.getLogger(FormatoImpresionA.class);
    
    public FormatoImpresionA() {
        super();
    }

    @Override
    public ArrayList<String> formatoFactura(JDialog pJDialog, ArrayList pDetalleComprobante, String pValTotalNeto,
                                            String pNumComprobante, String pValIgvComPago, String pValCopagoCompPago,
                                            String pPorcIgv, String pNumCompCoPago, String pValTotalAhorro,
                                            boolean vEsPedidoConvenio, String pImprDatAdic,
                                            String pTipoClienteConvenio, String pCodTipoConvenio, String pFechaBD,
                                            String pRefTipComp, String pValRedondeoComPago, String pRazonSocial,
                                            String pDireccionDestino, String pNumeroRUC, String vPrctBeneficiario,
                                            String vPrctEmpresa, int margen,
                                            String pValTotalBruto, String pValTotalAfecto, String pValTotalDescuento,
                                            int numDocumento) {
        if(vEsPedidoConvenio){
            return formatoFacturaConvenio(pJDialog, pDetalleComprobante, pValTotalNeto, pNumComprobante, pValIgvComPago,
                                       pValCopagoCompPago, "pValIgvCompCoPago", pNumCompCoPago, "", true, pImprDatAdic,
                                       pTipoClienteConvenio, pCodTipoConvenio, pFechaBD, pRefTipComp,
                                       pValRedondeoComPago,margen);
        }else{
            return formatoFacturaNormal(pJDialog, pFechaBD, pDetalleComprobante, pValTotalBruto, pValTotalNeto,
                                       pValTotalAfecto, pValTotalDescuento, pValIgvComPago, pPorcIgv, pValRedondeoComPago,
                                       pNumComprobante, pRazonSocial, pNumeroRUC, pDireccionDestino, pValTotalAhorro,
                                       "", true, margen);
        }
    }
    
    private ArrayList<String> formatoFacturaNormal(JDialog pJDialog, String pFechaBD, ArrayList pDetalleComprobante,
                                       String pValTotalBruto, String pValTotalNeto, String pValTotalAfecto,
                                       String pValTotalDcto, String pValTotalIgv, String pPorcIgv, String pValRedondeo,
                                       String pNumComprobante, String pNomImpreso, String pNumDocImpreso,
                                       String pDirImpreso, String pValTotalAhorro, String pRuta, boolean bol,
                                                   int margen){
        String indProdVirtual = "";

        VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
        
        ArrayList<String> vPrint = new ArrayList<>();

        String vMargen = FarmaPRNUtility.llenarBlancos(margen);

            try {
                String dia = pFechaBD.substring(0, 2);
                String mesLetra = FarmaUtility.devuelveMesEnLetras(Integer.parseInt(pFechaBD.substring(3, 5)));
                String ano = pFechaBD.substring(6, 10);
                String hora = pFechaBD.substring(11, 19);
                
                if (VariablesPtoVenta.vIndDirMatriz) {
                    ArrayList lstDirecMatriz = FarmaUtility.splitString(VariablesPtoVenta.vDireccionMatriz, 32);

                    vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(54) + lstDirecMatriz.get(0).toString());
                    
                    vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(54) + lstDirecMatriz.get(1).toString());
                    
                    vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(17) +
                                     FarmaPRNUtility.alinearIzquierda(FarmaVariables.vCodLocal + " - " +
                                                                      FarmaVariables.vDescLocal, 37) +
                                     lstDirecMatriz.get(2).toString());
                    
                } else {
                    vPrint.add(vMargen + " ");
                    
                    vPrint.add(vMargen + " ");
                    
                    //JMIRANDA 22.08.2011 Cambio para verificar si imprime
                    if (UtilityVentas.getIndImprimeCorrelativo()) {
                        vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(17) + FarmaVariables.vCodLocal + " - " +
                                         FarmaVariables.vDescLocal + FarmaPRNUtility.llenarBlancos(35) + "CORR." +
                                         VariablesCaja.vNumPedVta);
                    } else {
                        vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(17) + FarmaVariables.vCodLocal + " - " +
                                         FarmaVariables.vDescLocal + FarmaPRNUtility.llenarBlancos(35));
                    }
                }

                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(17) +
                                 FarmaPRNUtility.alinearIzquierda(pNomImpreso.trim(), 70));

                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(17) + pDirImpreso.trim());
                
                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(12) + "     " + pNumDocImpreso.trim());
                
                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(17) + dia + " de " + mesLetra + " del " + ano +
                                 "     " + hora + FarmaPRNUtility.llenarBlancos(50) + "No. " +
                                 pNumComprobante.substring(0, 3) + "-" + pNumComprobante.substring(3, 10));
                
                vPrint.add(vMargen + " ");
                
                vPrint.add(vMargen + " ");
                
                int linea = 0;
                
                for (int i = 0; i < pDetalleComprobante.size(); i++) {
                    
                    String codigoProd = ((String)((ArrayList)pDetalleComprobante.get(i)).get(6)).trim();
                    String descProd = ((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim();
                    String unidVta = ((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim();
                    String cantVenta = ((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim();
                    String lab = ((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim();
                    String precioSubTotal = ((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim();
                    String precioUnit = ((String)((ArrayList)pDetalleComprobante.get(i)).get(4)).trim();
                    
                    String lineaDetalle;
                    if(UtilityPtoVenta.isLocalVentaMayorista()){                    
                        String loteProd = ((String)((ArrayList)pDetalleComprobante.get(i)).get(7)).trim();
                        String fechaVenc = ((String)((ArrayList)pDetalleComprobante.get(i)).get(14)).trim();
                        
                        lineaDetalle = " " +
                                     FarmaPRNUtility.alinearIzquierda(codigoProd, 6) + " " +
                                     FarmaPRNUtility.alinearDerecha(cantVenta, 11) + "   " +
                                     FarmaPRNUtility.alinearIzquierda(descProd, 30) + "   " +
                                     FarmaPRNUtility.alinearIzquierda(unidVta, 10) + "   " +
                                     FarmaPRNUtility.alinearIzquierda(lab, 10) + FarmaPRNUtility.llenarBlancos(1) +
                                     FarmaPRNUtility.alinearIzquierda(loteProd, 10) + FarmaPRNUtility.llenarBlancos(1) +
                                     FarmaPRNUtility.alinearIzquierda(fechaVenc, 10) + FarmaPRNUtility.llenarBlancos(2) +
                                     FarmaPRNUtility.alinearDerecha(precioUnit, 13) + FarmaPRNUtility.llenarBlancos(4) +
                                     FarmaPRNUtility.alinearDerecha(precioSubTotal, 10);
                    }else{
                        lineaDetalle = " " +
                                     FarmaPRNUtility.alinearIzquierda(codigoProd, 6) + " " +
                                     FarmaPRNUtility.alinearDerecha(cantVenta, 11) + "   " +
                                     FarmaPRNUtility.alinearIzquierda(descProd, 38) + "   " +
                                     FarmaPRNUtility.alinearIzquierda(unidVta, 14) + "   " +
                                     FarmaPRNUtility.alinearIzquierda(lab, 20) + FarmaPRNUtility.llenarBlancos(2) +
                                     FarmaPRNUtility.alinearDerecha(precioUnit, 13) + FarmaPRNUtility.llenarBlancos(4) +
                                     FarmaPRNUtility.alinearDerecha(precioSubTotal, 10);
                    }
                    vPrint.add(vMargen + lineaDetalle);

                    linea += 1;
                    indProdVirtual = FarmaUtility.getValueFieldArrayList(pDetalleComprobante, i, 8);
                    //verifica que solo se imprima un producto virtual en el comprobante
                    if (i == 0 && indProdVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                        VariablesCaja.vIndPedidoConProdVirtualImpresion = true;
                    else
                        VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
                }

                if (VariablesCaja.vIndPedidoConProdVirtualImpresion) {
                    vPrint.add(vMargen + "");
                    
                    UtilityCaja.impresionInfoVirtual(vPrint,
                                         FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 9),
                                         //tipo prod virtual
                            FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 13), //codigo aprobacion
                            FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 11), //numero tarjeta
                            FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 12), //numero pin
                            FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 10), //numero telefono
                            FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 5), //monto
                            VariablesCaja.vNumPedVta, //Se añadio el parametro
                            FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 6)); //cod_producto

                    linea = linea + 4;
                }

                if (VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                    linea++;
                }

                //MODIFICADO POR DVELIZ 13.10.08
                //
                if (!VariablesVentas.vEsPedidoConvenio) {
                    if (pDetalleComprobante.size() < 10) {
                        for (int j = linea; j <= 10; j++) {
                            vPrint.add(vMargen + " ");
                        }
                    }
                } else {
                    for (int j = linea; j <= ConstantsPtoVenta.TOTAL_LINEAS_POR_FACTURA; j++)
                        vPrint.add(vMargen + " ");
                }
                //*************************************INFORMACION DEL CONVENIO*************************************************//
                //*******************************************INICIO************************************************************//

                if (VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                    
                        
                        ArrayList aInfoPedConv = new ArrayList();
                        DBConvenio.obtieneInfoPedidoConv(aInfoPedConv, VariablesCaja.vNumPedVta,
                                                         "" + FarmaUtility.getDecimalNumber(pValTotalNeto));

                        for (int i = 0; i < aInfoPedConv.size(); i++) {
                            ArrayList registro = (ArrayList)aInfoPedConv.get(i);
                            //JCORTEZ 10/10/2008 Se muestra informacion de convenio si no es de tipo competencia
                            String Ind_Comp = ((String)registro.get(8)).trim();
                            if (Ind_Comp.equalsIgnoreCase("N")) {
                                
                                vPrint.add(vMargen + FarmaPRNUtility.alinearIzquierda(" Titular Cliente: " +
                                                                                  ((String)registro.get(4)).trim(),
                                                                                  60) + " " +
                                        FarmaPRNUtility.alinearIzquierda("Co-Pago: " +
                                                                         ((String)registro.get(3)).trim() + " %", 25));
                                /* 07.03.2008 ERIOS Si se tiene el valor del credito disponible, se muestra en el comprobante */
                                String vCredDisp = ((String)registro.get(7)).trim();
                                if (vCredDisp.equals("")) {
                                    vPrint.add(vMargen +  //FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+vCoPago,60)+" "+
                                            FarmaPRNUtility.alinearIzquierda(" Credito: S/. " +
                                                                             ((String)registro.get(5)).trim(), 60) +
                                            " " +
                                            FarmaPRNUtility.alinearIzquierda("A Cuenta: S/. " + ((String)registro.get(6)).trim(),
                                                                             25));
                                } else {
                                    vPrint.add(vMargen +  //FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+vCoPago,60)+" "+
                                            FarmaPRNUtility.alinearIzquierda(" Credito: S/. " +
                                                                             ((String)registro.get(5)).trim(), 60) +
                                            " " +
                                            FarmaPRNUtility.alinearIzquierda("A Cuenta: S/. " + ((String)registro.get(6)).trim(),
                                                                             25) + " " +
                                            FarmaPRNUtility.alinearIzquierda("Cred Disp: S/." + vCredDisp, 25));
                                }
                            }
                        }


                } 
                //*********************************************FIN*************************************************************//
                //*************************************INFORMACION DEL CONVENIO***********************************************//

                //MODIFICADO POR DVELIZ 02.10.08
                //vPrint.add(vMargen + " "+VariablesFidelizacion.vNomClienteImpr);


                //ERIOS 25.07.2008 imprime el monto ahorrado.
                double auxTotalDcto = FarmaUtility.getDecimalNumber(pValTotalAhorro);
                boolean isImprimeAhorroAntiguo = true;
                if(VariablesPuntos.frmPuntos!=null && VariablesPuntos.frmPuntos.getTarjetaBean()!=null){
                    isImprimeAhorroAntiguo = false;
                }
                if (auxTotalDcto > 0 && isImprimeAhorroAntiguo) {
                    
                    //JCORTEZ 02.09.2009 Se muestra mensaje distinto si es fidelizado o no.
                    String obtenerMensaje = "";
                    String indFidelizado = "";
                    
                    if (VariablesFidelizacion.vNumTarjeta.trim().length() > 0) {
                        indFidelizado = "S";
                    } else {
                        indFidelizado = "N";
                    }
                    
                    obtenerMensaje = UtilityCaja.obtenerMensaAhorro(pJDialog, indFidelizado);
                    vPrint.add(vMargen + "" + obtenerMensaje + " " + " S/. " + pValTotalAhorro);
                    
                } else {
                    vPrint.add(vMargen + " ");
                }

                if (VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                    vPrint.add(vMargen + FarmaPRNUtility.alinearIzquierda(" - DISTRIBUCION GRATUITA - ", 60));
                }
                if (VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_MESON) ||
                    VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL)) {
                    VariablesVentas.vTituloDelivery = "";
                } else
                    VariablesVentas.vTituloDelivery =
                            ""; //" - PEDIDO DELIVERY - " ; //ERIOS 2.4.7 No se imprime referencia a Delivery

                
                //ERIOS 12.09.2013 Imprime direccion local
                String vLinea = "", vLineaDirecLocal1 = "", vLineaDirecLocal2 = "", vLineaDirecLocal3 = "";
                if (VariablesPtoVenta.vIndDirLocal) {
                    ArrayList lstDirecLocal =
                        FarmaUtility.splitString("NUEVA DIRECCION: " + FarmaVariables.vDescCortaDirLocal, 46);
                    vLineaDirecLocal1 = lstDirecLocal.get(0).toString();
                    vLineaDirecLocal2 = ((lstDirecLocal.size() > 1) ? lstDirecLocal.get(1).toString() : "");
                    vLineaDirecLocal3 = ((lstDirecLocal.size() > 2) ? lstDirecLocal.get(2).toString() : "");
                }

                vLinea = " SON: " + FarmaPRNUtility.alinearIzquierda(FarmaPRNUtility.montoEnLetras(pValTotalNeto), 67);
                vLinea = FarmaPRNUtility.alinearIzquierda(vLinea, 85) + vLineaDirecLocal1;
                vPrint.add(vMargen + vLinea);

                vLinea =
                        " REDO:" + pValRedondeo + " CAJERO:" + VariablesCaja.vNomCajeroImpreso + " " + VariablesCaja.vApePatCajeroImpreso +
                        " " + " CAJA:" + VariablesCaja.vNumCajaImpreso + " TURNO:" +
                        VariablesCaja.vNumTurnoCajaImpreso + " VEND:" + VariablesCaja.vNomVendedorImpreso + " " +
                        VariablesCaja.vApePatVendedorImpreso;
                vLinea = FarmaPRNUtility.alinearIzquierda(vLinea, 85) + vLineaDirecLocal2;
                vPrint.add(vMargen + vLinea);

                vLinea =
                        " Forma(s) de pago: " + VariablesCaja.vFormasPagoImpresion + FarmaPRNUtility.llenarBlancos(11) +
                        VariablesVentas.vTituloDelivery;
                vLinea = FarmaPRNUtility.alinearIzquierda(vLinea, 85) + vLineaDirecLocal3;
                vPrint.add(vMargen + vLinea);

                //dubilluz
                if (!VariablesCaja.vImprimeFideicomizo) {
                    vPrint.add(vMargen + " ");
                    vPrint.add(vMargen + " ");
                }

                vPrint.add(vMargen + "     " + "00000" + FarmaPRNUtility.llenarBlancos(12) +
                                 FarmaPRNUtility.alinearDerecha(pValTotalBruto, 10) +
                                 FarmaPRNUtility.llenarBlancos(10) +
                                 FarmaPRNUtility.alinearDerecha(pValTotalDcto, 10) +
                                 FarmaPRNUtility.llenarBlancos(10) +
                                 FarmaPRNUtility.alinearDerecha(pValTotalAfecto, 10) +
                                 FarmaPRNUtility.llenarBlancos(10) + FarmaPRNUtility.alinearDerecha(pPorcIgv, 6) +
                                 FarmaPRNUtility.llenarBlancos(11) + FarmaPRNUtility.alinearDerecha(pValTotalIgv, 10) +
                                 FarmaPRNUtility.llenarBlancos(8) + "S/. " +
                                 FarmaPRNUtility.alinearDerecha(pValTotalNeto, 10));
                /*dubilluz 2011.09.16*/

                if (VariablesCaja.vImprimeFideicomizo) {
                    String[] lineas = VariablesCaja.vCadenaFideicomizo.trim().split("@");
                    if (lineas.length > 0) {
                        for (int i = 0; i < lineas.length; i++) {
                            vPrint.add(vMargen + "" + lineas[i].trim());
                        }
                    } else {
                        vPrint.add(vMargen + "" + VariablesCaja.vCadenaFideicomizo.trim());
                    }
                }
                VariablesCaja.vEstadoSinComprobanteImpreso = "N";
            }
            catch (Exception e) {
                log.error("",e);
                VariablesCaja.vEstadoSinComprobanteImpreso = "S";
                log.info("**** Fecha :" + pFechaBD);
                log.info("**** CORR :" + VariablesCaja.vNumPedVta);
                log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
                log.info("**** IP :" + FarmaVariables.vIpPc);
                log.error("Error al imprimir Factura: ",e);

            }

        return vPrint;
    }

    private ArrayList<String> formatoFacturaConvenio(JDialog pJDialog, ArrayList pDetalleComprobante, String pValTotalNeto,
                                       String pNumComprobante, String pValIgvComPago, String pValCopagoCompPago,
                                       String pValIgvComCoPago, String pNumCompCoPago, String pRuta, boolean bol,
                                       String pImprDatAdic, String pTipoClienteConvenio, String pCodTipoConvenio,
                                       String pFechaBD, String pRefTipComp,
                                                     String pValRedondeoComPago,int margen){
         VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
         float subTotal = 0;
         float montoIGV = 0;
         float SumSubTotal = 0;
         double SumMontoIGV = 0;
    
        String vRefTipComp = "";
        if (pRefTipComp.equals(ConstantsVentas.TIPO_COMP_BOLETA))
            vRefTipComp = "BOL";
        if (pRefTipComp.equals(ConstantsVentas.TIPO_COMP_FACTURA))
            vRefTipComp = "FAC";
        if (pRefTipComp.equals(ConstantsVentas.TIPO_COMP_GUIA))
            vRefTipComp = "GUIA";
        if (pRefTipComp.equals(ConstantsVentas.TIPO_COMP_TICKET))
            vRefTipComp = "TKB";
        
         ArrayList<String> vPrint = new ArrayList<>();
        String vLinea = "";
        String vMargen = FarmaPRNUtility.llenarBlancos(margen);
             try {
                 
                 String dia = pFechaBD.substring(0, 2);
                 String mesLetra = FarmaUtility.devuelveMesEnLetras(Integer.parseInt(pFechaBD.substring(3, 5)));
                 String ano = pFechaBD.substring(6, 10);
                 String hora = ""; // pFechaBD.substring(11,19);
    
                 if (VariablesPtoVenta.vIndDirMatriz) {
                     vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(30) + VariablesPtoVenta.vDireccionMatriz);
                 }
                 //JMIRANDA 22.08.2011 Cambio para verificar si imprime
                 if (UtilityVentas.getIndImprimeCorrelativo()) {
                     vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(11) + pFechaBD + "   CORR." +
                                      VariablesCaja.vNumPedVta);
                 } else {
                     vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(17) +
                                      VariablesConvenioBTLMF.vInstitucion.toUpperCase().trim());
                 }
    
                 vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(17) +
                                  FarmaPRNUtility.alinearIzquierda(VariablesConvenioBTLMF.vDireccion.trim(), 70));
    
                 vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(17) + VariablesConvenioBTLMF.vRuc.trim());
                 
                 vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(17) + dia + " de " + mesLetra + " del " + ano +
                                  "     " + hora + FarmaPRNUtility.llenarBlancos(50) + "No. " +
                                  pNumComprobante.substring(0, 3) + "-" + pNumComprobante.substring(3, 10));
                 String vOC = "";
                 if (VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL)) {
                     vOC = "OC: " + VariablesConvenioBTLMF.vNumOrdeCompra;
                 }
                 String vGuia = "";
                 if (pNumCompCoPago != null && !pNumCompCoPago.equals("") &&
                     pRefTipComp.equals(ConstantsPtoVenta.TIP_COMP_GUIA)) {
                     vGuia = vRefTipComp + ":" + pNumCompCoPago.substring(0, 3) + "-" + pNumCompCoPago.substring(3, 10);
                 }
                 vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(17) + FarmaPRNUtility.llenarBlancos(77)+vOC+"  "+vGuia);
                 
                 vPrint.add(vMargen + " ");
                 
                 int linea = 0;
    
                 int vNroEspacio = 0;
                 for (int i = 0; i < pDetalleComprobante.size(); i++) {
                     String codigoProd = ((String)((ArrayList)pDetalleComprobante.get(i)).get(6)).trim();
                     String descProd = ((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim();
                     String unidVta = ((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim();
                     String cantVenta = ((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim();
                     String lab = ((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim();
                     String precioSubTotal = ((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim();
                     String precioUnit = ((String)((ArrayList)pDetalleComprobante.get(i)).get(4)).trim();
                     
                     String lineaDetalle;
                     if(VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL)){                    
                         String loteProd = ((String)((ArrayList)pDetalleComprobante.get(i)).get(7)).trim();
                         String fechaVenc = ((String)((ArrayList)pDetalleComprobante.get(i)).get(14)).trim();
                         
                         lineaDetalle = " " +
                                      FarmaPRNUtility.alinearIzquierda(codigoProd, 6) + " " +
                                      FarmaPRNUtility.alinearDerecha(cantVenta, 11) + "   " +
                                      FarmaPRNUtility.alinearIzquierda(descProd, 30) + "   " +
                                      FarmaPRNUtility.alinearIzquierda(unidVta, 10) + "   " +
                                      FarmaPRNUtility.alinearIzquierda(lab, 10) + FarmaPRNUtility.llenarBlancos(1) +
                                      FarmaPRNUtility.alinearIzquierda(loteProd, 10) + FarmaPRNUtility.llenarBlancos(1) +
                                      FarmaPRNUtility.alinearIzquierda(fechaVenc, 10) + FarmaPRNUtility.llenarBlancos(2) +
                                      FarmaPRNUtility.alinearDerecha(precioUnit, 13) + FarmaPRNUtility.llenarBlancos(4) +
                                      FarmaPRNUtility.alinearDerecha(precioSubTotal, 10);
                     }else{
                         lineaDetalle = " " +
                                      FarmaPRNUtility.alinearIzquierda(codigoProd, 6) + " " +
                                      FarmaPRNUtility.alinearDerecha(cantVenta, 11) + "   " +
                                      FarmaPRNUtility.alinearIzquierda(descProd, 38) + "   " +
                                      FarmaPRNUtility.alinearIzquierda(unidVta, 14) + "   " +
                                      FarmaPRNUtility.alinearIzquierda(lab, 20) + FarmaPRNUtility.llenarBlancos(2) +
                                      FarmaPRNUtility.alinearDerecha(precioUnit, 13) + FarmaPRNUtility.llenarBlancos(4) +
                                      FarmaPRNUtility.alinearDerecha(precioSubTotal, 10);
                     }
                     vPrint.add(vMargen + lineaDetalle);
    
                     linea += 1;
                     subTotal =
                             new Double(FarmaUtility.getDecimalNumber(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim())).floatValue();
                     montoIGV =
                             new Double(FarmaUtility.getDecimalNumber(((String)((ArrayList)pDetalleComprobante.get(i)).get(18)).trim())).floatValue();
                     SumMontoIGV = SumMontoIGV + montoIGV;
                     
                     SumSubTotal = SumSubTotal + subTotal;
                 }
    
                 double porcCopago =
                     Math.round((FarmaUtility.getDecimalNumber(pValCopagoCompPago) / (FarmaUtility.getDecimalNumber(pValTotalNeto) +
                                                                                      FarmaUtility.getDecimalNumber(pValCopagoCompPago))) *
                                100);
                 SumMontoIGV = SumMontoIGV - ((SumMontoIGV * porcCopago) / 100);
                 double ValCopagoCompPagoSinIGV = ((SumSubTotal * porcCopago) / 100);
                 
                 
                 double pValTotalNetoRedondeo =
                     FarmaUtility.getDecimalNumber(pValTotalNeto) + FarmaUtility.getDecimalNumber(pValRedondeoComPago);
                 pValTotalNetoRedondeo =
                         FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(pValTotalNetoRedondeo, 2)); //CHUANES 12.03.2014 SE PONE EL FORMATO DE 2 DECIMALES AL VALOR REDONDEADO
                 
                 if (VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL)){
                     for (int j = linea; j <= ConstantsPtoVenta.TOTAL_LINEAS_POR_FACTURA; j++)
                         vPrint.add(vMargen + " ");

                     vPrint.add(vMargen + "      " +
                                VariablesVentas.vMensajeBotiquinBTLVtaInstitucional); //kmoncada 10.07.14 impresion de poliza en vta empresa
                 }else{
                     //*************************************INFORMACION DEL CONVENIO***********************************************//
                     vPrint.add(vMargen + "      " + FarmaPRNUtility.alinearIzquierda(" ", 85) + "        " +
                                      "    Sub Total   S/. " +
                                      FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(SumSubTotal), 10));
                         
                     //ERIOS 12.09.2013 Imprime direccion local
                     String vLineaDirecLocal1 = "", vLineaDirecLocal2 = "", vLineaDirecLocal3 = "";
                     if (VariablesPtoVenta.vIndDirLocal) {
                         ArrayList lstDirecLocal =
                             FarmaUtility.splitString("NUEVA DIRECCION: " + FarmaVariables.vDescCortaDirLocal, 46);
                         vLineaDirecLocal1 = lstDirecLocal.get(0).toString();
                         vLineaDirecLocal2 = ((lstDirecLocal.size() > 1) ? lstDirecLocal.get(1).toString() : "");
                         vLineaDirecLocal3 = ((lstDirecLocal.size() > 2) ? lstDirecLocal.get(2).toString() : "");
                     }
        
                     if (pCodTipoConvenio.equals("1") || pCodTipoConvenio.equals("3")) {
                         vLinea =
                                 FarmaPRNUtility.llenarBlancos(85) + "            " + "Coaseguro(" +
                                 FarmaUtility.formatNumber(porcCopago, 0) + "%)    S/. " +
                                 FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(ValCopagoCompPagoSinIGV), 10);
                         vPrint.add(vMargen + vLinea);
        
                         vLinea =
                                 FarmaPRNUtility.alinearIzquierda("              " + "     ", 85) + "                       ---------------------";
                         vPrint.add(vMargen + vLinea);
        
                         vLinea =
                                 FarmaPRNUtility.alinearIzquierda("  Institución: " + VariablesConvenioBTLMF.vInstitucion.toUpperCase().trim() +
                                                                  "  (" + FarmaUtility.formatNumber(100 - porcCopago, "") +
                                                                  ")%", 85) + "                                  " +
                                 FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(SumSubTotal -
                                                                                          ValCopagoCompPagoSinIGV), 10);
                         vPrint.add(vMargen + vLinea);
        
                         vLinea =
                                 FarmaPRNUtility.alinearIzquierda("  Convenio: " + VariablesConvenioBTLMF.vNomConvenio.toUpperCase().trim(),
                                                                  85) + vLineaDirecLocal1;
                         vPrint.add(vMargen + vLinea);
        
                         vLinea =
                                 FarmaPRNUtility.alinearIzquierda("  Beneficiario: " + VariablesConvenioBTLMF.vNomCliente, 85) +
                                 vLineaDirecLocal2;
                         vPrint.add(vMargen + vLinea);
        
                         vLinea =
                                 FarmaPRNUtility.alinearIzquierda("  Documento de Referencia Nro : " + pNumCompCoPago, 85) +
                                 vLineaDirecLocal3;
                         vPrint.add(vMargen + vLinea);
        
                         vLinea =
                                 FarmaPRNUtility.alinearIzquierda("  Doc refe de la Empresa Monto:S/." + pValCopagoCompPago +
                                                                  " y (" + FarmaUtility.formatNumber(porcCopago, "") + "%)",
                                                                  85) + "                       ";
                         vPrint.add(vMargen + vLinea);
        
                     } else {
                         vLinea =
                                 FarmaPRNUtility.llenarBlancos(85) + "            ";
                         vPrint.add(vMargen + vLinea);
        
                         vLinea =
                                 FarmaPRNUtility.alinearIzquierda("              " + "     ", 85) + "                       ---------------------";
                         vPrint.add(vMargen + vLinea);
        
                         vLinea =
                                 FarmaPRNUtility.alinearIzquierda("  Institución: " + VariablesConvenioBTLMF.vInstitucion.toUpperCase().trim() +
                                                                  "  (" + FarmaUtility.formatNumber(100 - porcCopago, "") +
                                                                  ")%", 85) + vLineaDirecLocal1;
                         vPrint.add(vMargen + vLinea);
        
                         vLinea =
                                 FarmaPRNUtility.alinearIzquierda("  Convenio: " + VariablesConvenioBTLMF.vNomConvenio.toUpperCase().trim(),
                                                                  85) + vLineaDirecLocal2;
                         vPrint.add(vMargen + vLinea);
        
                         String lineaBeneficiario = FormatoImpresionC.muestraBeneficiario();
                         vLinea = FarmaPRNUtility.alinearIzquierda(lineaBeneficiario, 85) + vLineaDirecLocal3;
                         vPrint.add(vMargen + vLinea);
                     }
        
                     int var = 0;
                     if (pImprDatAdic.equals("1")) {
                         if (VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic != null &&
                             VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.size() > 0) {
                             var = 3;
                         }
                     }
        
                     if (linea == 5)
                         vNroEspacio = 3 - var;
                     if (linea == 4)
                         vNroEspacio = 4 - var;
                     if (linea == 3)
                         vNroEspacio = 5 - var;
                     if (linea == 2)
                         vNroEspacio = 6 - var;
                     if (linea == 1)
                         vNroEspacio = 7 - var;
        
                     for (int c = 0; c < vNroEspacio; c++) {
                         vPrint.add(vMargen + "  " + FarmaPRNUtility.alinearIzquierda(" ", 65));
                     }
        
                     if (pImprDatAdic.equals("1")) {
                         if (VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic != null &&
                             VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.size() > 0) {
                             vPrint.add(vMargen + "  Datos Adicionales");
        
                             for (int j = 0; j < VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.size(); j++) {
                                 Map datosAdicConv = (Map)VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.get(j);
        
                                 String pNombCampo = (String)datosAdicConv.get("NOMBRE_CAMPO");
                                 String pDesCampo = (String)datosAdicConv.get("DESCRIPCION_CAMPO");
                                 String vFlgImprime = (String)datosAdicConv.get("FLG_IMPRIME");
                                 String vCodCampo = (String)datosAdicConv.get("COD_CAMPO");
        
        
                                 if (vFlgImprime.equals("1") || vFlgImprime.equals("2")) {
                                     if (vCodCampo.equals(ConstantsConvenioBTLMF.COD_NOMB_TITULAR) ||
                                         vCodCampo.equals(ConstantsConvenioBTLMF.COD_NRO_ATENCION)) {
                                         vPrint.add(vMargen + "  - " + pNombCampo + "    " + pDesCampo + " ");
                                     }
                                 }
                             }
                         }
                     }
                 }
                 
                 /*****IMPRIME EL MONTO TOTAL EL LETRAS******/
                 vLinea =
                     " SON: " + FarmaPRNUtility.alinearIzquierda(FarmaPRNUtility.montoEnLetras(pValTotalNetoRedondeo), 85);
                 vPrint.add(vMargen + vLinea);
                 
                 vPrint.add(vMargen + "   " + FarmaPRNUtility.alinearIzquierda(" ", 65));
                 vPrint.add(vMargen + "   " + FarmaPRNUtility.alinearIzquierda(" ", 65));
    
                 //LLEIVA 26-Nov-2013 Añade linea de Porc de IGV
                 vPrint.add(vMargen + FarmaPRNUtility.alinearIzquierda(" ", 90) + 
                                  "" + UtilityVentas.obtenerIgvLocal() + //ASOSA - 25/06/2015 - IGVAZONIA
                                  "%");
    
                 //ERIOS 03.03.2015 El modifica el calculo de subtotal
                 SumMontoIGV = FarmaUtility.getDecimalNumber(pValIgvComPago);
                 double neto = pValTotalNetoRedondeo - SumMontoIGV;
                 vLinea = "     " +
                                  FarmaPRNUtility.alinearIzquierda("                                                       " +
                                                                   FarmaUtility.formatNumber(neto),85) 
                          + FarmaUtility.formatNumber(SumMontoIGV, 2) +"               " 
                          + FarmaUtility.formatNumber(pValTotalNetoRedondeo) + "          ";
                 vPrint.add(vMargen + vLinea);
    
                 vPrint.add(vMargen + "  " + FarmaPRNUtility.alinearIzquierda(" ", 65));
    
    
                 if (pCodTipoConvenio.equals("1")) {
                     //           vPrintArchivo.printLine("  Documento de Referencia Nro: "+pNumCompCoPago+" ",true);
                     //           vPrint.add(vMargen + "  Documento de Referencia Nro "+pNumCompCoPago+": ",true);
                     //           vPrintArchivo.printLine("  Doc refe de la Empresa Monto:S/." + pValCopagoCompPago +" y ("+FarmaUtility.formatNumber(porcCopago,"")+"%)",true);
                     //           vPrint.add(vMargen + "  Doc refe de la Empresa Monto:S/." + pValCopagoCompPago +" y ("+FarmaUtility.formatNumber(porcCopago,"")+"%)",true);
                 }
    
    
                 vPrint.add(vMargen + " REDO: " + pValRedondeoComPago + " CAJERO: " + VariablesCaja.vNomCajeroImpreso +
                                  " " + VariablesCaja.vApePatCajeroImpreso + " " + " CAJA: " +
                                  VariablesCaja.vNumCajaImpreso + " TURNO: " + VariablesCaja.vNumTurnoCajaImpreso +
                                  " VEND: " + VariablesCaja.vNomVendedorImpreso + " " +
                                  VariablesCaja.vApePatVendedorImpreso);
    
                 VariablesCaja.vEstadoSinComprobanteImpreso = "N";
    
             } catch (Exception e) {
                 log.error("", e);
                 VariablesCaja.vEstadoSinComprobanteImpreso = "S";
                 log.info("**** Fecha :" + pFechaBD);
                 log.info("**** CORR :" + VariablesCaja.vNumPedVta);
                 log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
                 log.info("**** IP :" + FarmaVariables.vIpPc);
                 log.info("Error al imprimir la Factura: " + e);
             }
             
         return vPrint;                                                         
    }
    
    @Override
    public ArrayList<String> formatoGuia(JDialog pJDialog, ArrayList pDetalleComprobante, String pValTotalNeto,
                                         String pNumComprobante, String pValIgvComPago, String pValCopagoCompPago,
                                         String pValIgvComCoPago, String pNumCompCoPago, String pRuta, boolean bol,
                                         String pImprDatAdic, String pTipoClienteConvenio, String pCodTipoConvenio,
                                         String pFechaBD, String pRefTipComp, String pValRedondeoComPago,
                                         String pRazonSocial, String pDireccionDestino, String pNumeroRUC,
                                         String vPrctBeneficiario, String vPrctEmpresa, int margen) {

        String pNomImpreso = "";

        VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
        float subTotal = 0;
        float SumSubTotal = 0;
        float montoIGV = 0;
        double SumMontoIGV = 0;
        
        String vRefTipComp = "";
        
        if (pRefTipComp.equals(ConstantsVentas.TIPO_COMP_BOLETA))
            vRefTipComp = "BOL";
        if (pRefTipComp.equals(ConstantsVentas.TIPO_COMP_FACTURA))
            vRefTipComp = "FAC";
        if (pRefTipComp.equals(ConstantsVentas.TIPO_COMP_GUIA))
            vRefTipComp = "GUIA";
        if (pRefTipComp.equals(ConstantsVentas.TIPO_COMP_TICKET))
            vRefTipComp = "TKB";

        ArrayList<String> vPrint = new ArrayList<>();
        String vLinea;
        String vMargen = FarmaPRNUtility.llenarBlancos(margen);
            try {


                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(30) + " ");

                if (VariablesPtoVenta.vIndDirMatriz) {
                    vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(30) + VariablesPtoVenta.vDireccionMatriz);
                } else {
                    vPrint.add(vMargen + " ");
                }

                //ERIOS 12.09.2013 Imprime direccion local
                if (VariablesPtoVenta.vIndDirLocal) {
                    vPrint.add(vMargen + "     " + "NUEVA DIRECCION: " + FarmaVariables.vDescCortaDirLocal);
                } else {
                    vPrint.add(vMargen + "");
                }

                //JMIRANDA 22.08.2011 Cambio para verificar si imprime
                if (UtilityVentas.getIndImprimeCorrelativo()) {
                    vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(11) + pFechaBD + "   CORR." +
                                     VariablesCaja.vNumPedVta);
                } else {
                    vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(11) + " ");
                }

                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(11) +
                                 FarmaPRNUtility.alinearIzquierda(pNomImpreso.trim(), 60));
                
                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(11) +
                                 FarmaPRNUtility.alinearIzquierda(pNomImpreso.trim(), 60));
                
                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(11) + FarmaPRNUtility.alinearIzquierda(pFechaBD, 60) +
                                 "   No. " + pNumComprobante.substring(0, 3) + "-" + pNumComprobante.substring(3, 10));
                
                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(11) + VariablesConvenioBTLMF.vInstitucion.trim());
                
                //NUMERO DE RUC
                vLinea = FarmaPRNUtility.llenarBlancos(11) + VariablesConvenioBTLMF.vRuc.trim();
                if(VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL)){
                    vLinea += FarmaPRNUtility.llenarBlancos(40) +
                           FarmaPRNUtility.alinearIzquierda("OC: " + VariablesConvenioBTLMF.vNumOrdeCompra, 20);
                }
                vPrint.add(vMargen + vLinea);
                
                //DIRECCION
                vLinea = FarmaPRNUtility.llenarBlancos(11) +
                                 FarmaPRNUtility.alinearIzquierda(VariablesConvenioBTLMF.vDireccion.trim(), 60);
                if(VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL)){
                    vLinea += FarmaPRNUtility.llenarBlancos(20) + 
                              vRefTipComp + ":" + pNumCompCoPago;
                }
                vPrint.add(vMargen + vLinea);
                
                vPrint.add(vMargen + " ");
                
                vPrint.add(vMargen + " ");
                
                vPrint.add(vMargen + " ");
                
                vPrint.add(vMargen + " ");
                

                int linea = 0;
                for (int i = 0; i < pDetalleComprobante.size(); i++) {
                    //Agregado por DVELIZ 13.10.08

                    String punitario = " ";
                    String valor = " ";

                    String colSubTotal = " ";
                    if (VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL)) {
                        colSubTotal =
                                ((String)((ArrayList)pDetalleComprobante.get(i)).get(20)).trim(); // hace referencia al FECHA VENCIMIENTO
                        punitario =
                                ((String)((ArrayList)pDetalleComprobante.get(i)).get(19)).trim(); // HACE REFERENCIA AL LOTE DEL PROD.
                    } else {
                        if (VariablesConvenioBTLMF.vFlgImprimeImportes.equals("1")) {
                            valor = ((String)((ArrayList)pDetalleComprobante.get(i)).get(16)).toString().trim();
                            
                            if (valor.equals("0.000"))
                                valor = " ";
                            
                            colSubTotal = (String)((ArrayList)pDetalleComprobante.get(i)).get(5);
                            punitario = (String)((ArrayList)pDetalleComprobante.get(i)).get(4).toString().trim();
                        }
                    }
                    String codigoProd = ((String)((ArrayList)pDetalleComprobante.get(i)).get(6)).trim();
                    String descProd = ((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim();
                    String unidVta = ((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim();
                    String cantVenta = ((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim();
                    String lab = "(" + ((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim() + ")";
                    
                    String lineaDetalle;
                    lineaDetalle =   FarmaPRNUtility.alinearIzquierda(codigoProd, 6) + " " +
                                     FarmaPRNUtility.alinearDerecha(cantVenta, 11) + "   " +
                                     FarmaPRNUtility.alinearIzquierda(descProd, 27) + " " +
                                     FarmaPRNUtility.alinearIzquierda(unidVta, 11) + "  " +
                                     FarmaPRNUtility.alinearIzquierda(lab, 16) + "  " + 
                                     FarmaPRNUtility.alinearDerecha(punitario, 10) + " " +                            
                                     FarmaPRNUtility.alinearDerecha(valor, 8) + "" + 
                                     FarmaPRNUtility.alinearDerecha(colSubTotal.trim(), 10);

                    vPrint.add(vMargen + lineaDetalle);
                    
                    linea += 1;
                    if (VariablesConvenioBTLMF.vFlgImprimeImportes.equals("1")) {
                        subTotal =
                                new Double(FarmaUtility.getDecimalNumber(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim())).floatValue();
                        
                        SumSubTotal = SumSubTotal + subTotal;
                        montoIGV =
                                new Double(FarmaUtility.getDecimalNumber(((String)((ArrayList)pDetalleComprobante.get(i)).get(18)).trim())).floatValue();
                        SumMontoIGV = SumMontoIGV + montoIGV;

                    }
                }

                if (VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL)) {
                    for (int j = linea; j < 5 + 7; j++) {
                        vPrint.add(vMargen + " ");
                        linea += 1;
                    }

                    vPrint.add(vMargen + "      " +
                               VariablesVentas.vMensajeBotiquinBTLVtaInstitucional); //kmoncada 10.07.14 impresion de poliza en vta empresa
                }else{
                    //*************************************INFORMACION DEL CONVENIO***********************************************//
                    double porcCopagoBenef = 0;
                    double porcCopagoEmpresa = 0;
                    double ValCopagoCompPagoSinIGV = 0;
    
                    porcCopagoBenef = FarmaUtility.getDecimalNumber(vPrctBeneficiario);
                    porcCopagoEmpresa = FarmaUtility.getDecimalNumber(vPrctEmpresa);
    
                    ValCopagoCompPagoSinIGV =
                            FarmaUtility.getDecimalNumber(pValCopagoCompPago) - FarmaUtility.getDecimalNumber(pValIgvComCoPago);
    
                    if (VariablesConvenioBTLMF.vFlgImprimeImportes.equals("1")) {
    
                        vLinea = FarmaPRNUtility.llenarBlancos(69)+
                                FarmaPRNUtility.alinearDerecha("Sub Total   S/. ",22) +
                                FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(SumSubTotal), 10);
                        vPrint.add(vMargen + vLinea);
                    }
    
                    // KMONCADA 17.11.2014 MOSTRAR DOCUMENTO DE REFERENCIA
                    if (pCodTipoConvenio.equals("1") || pCodTipoConvenio.equals("3")) {
                        if (VariablesConvenioBTLMF.vFlgImprimeImportes.equals("1")) {
    
                            double pValTotalNetoRedondeo =
                                FarmaUtility.getDecimalNumber(pValTotalNeto) + FarmaUtility.getDecimalNumber(pValRedondeoComPago);
                            pValTotalNetoRedondeo =
                                    FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(pValTotalNetoRedondeo,
                                                                                            2)); //CHUANES 12.03.2014 SE PONE EL FORMATO DE 2 DECIMALES AL VALOR REDONDEADO
                            
                            vLinea = FarmaPRNUtility.alinearIzquierda(" SON:" +FarmaPRNUtility.montoEnLetras(pValTotalNetoRedondeo).trim(),69)+
                                    FarmaPRNUtility.alinearDerecha("Coaseguro(" + FarmaUtility.formatNumber(porcCopagoEmpresa, 0) + "%)" + "   S/. ",22)+
                                    FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(ValCopagoCompPagoSinIGV),10);
                            vPrint.add(vMargen + vLinea);
                            
                            vLinea = FarmaPRNUtility.llenarBlancos(78)+"---------------------";
                            vPrint.add(vMargen + vLinea);
                            
                            vLinea = FarmaPRNUtility.alinearIzquierda("  Institución: " +
                                                                              VariablesConvenioBTLMF.vInstitucion.trim().toUpperCase() +
                                                                              "  (" +
                                                                              FarmaUtility.formatNumber(100 - porcCopagoEmpresa,
                                                                                                        "") + ")%",69)+
                                    FarmaPRNUtility.alinearDerecha("S/. ",22)+
                                    FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(SumSubTotal -ValCopagoCompPagoSinIGV),10);
                            vPrint.add(vMargen + vLinea);
                            
                            vLinea = FarmaPRNUtility.alinearIzquierda("  Convenio: " +
                                                                              VariablesConvenioBTLMF.vNomConvenio.trim().toUpperCase(),
                                                                              69)+
                                    FarmaPRNUtility.alinearDerecha("IGV   S/. ",22)+
                                    FarmaPRNUtility.alinearDerecha(pValIgvComPago, 10);
                            vPrint.add(vMargen + vLinea);
                            
                            vLinea = FarmaPRNUtility.alinearIzquierda("  Beneficiario: " +VariablesConvenioBTLMF.vNomCliente, 78)+"---------------------";
                            vPrint.add(vMargen + vLinea);
                            
                            String lineaReferencia = " ";
                            if (vRefTipComp != null && !vRefTipComp.trim().equals("")) {
                                lineaReferencia = "  #REF: " + vRefTipComp + " " +pNumCompCoPago + "(" +
                                                                                  FarmaUtility.formatNumber(porcCopagoEmpresa,
                                                                                                            "") + ")" +
                                                                                  " - " + "S/." + pValCopagoCompPago;                             
                            }
                            vLinea = FarmaPRNUtility.alinearIzquierda(lineaReferencia,69)+
                            FarmaPRNUtility.alinearDerecha("Total   S/. ",22)+
                            FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(pValTotalNetoRedondeo), 10);
                            vPrint.add(vMargen + vLinea);
                            
                        } else {
                            vPrint.add(vMargen + FarmaPRNUtility.alinearIzquierda("  Institución: " +
                                                                              VariablesConvenioBTLMF.vInstitucion.trim().toUpperCase() +
                                                                              "  (" +
                                                                              FarmaUtility.formatNumber(100 - porcCopagoEmpresa,
                                                                                                        "") + ")%", 65) +
                                             "                      ");
                            vPrint.add(vMargen + FarmaPRNUtility.alinearIzquierda("  Convenio: " +
                                                                              VariablesConvenioBTLMF.vNomConvenio.trim().toUpperCase(),
                                                                              65) + "     " + " " + "          ");
                            vPrint.add(vMargen + FarmaPRNUtility.alinearIzquierda("  Beneficiario: " +
                                                                              VariablesConvenioBTLMF.vNomCliente, 65) +
                                             "     " + " " + "          ");
                            
                            if (vRefTipComp != null && !vRefTipComp.trim().equals("")) {
                                vPrint.add(vMargen + "  #REF: " + vRefTipComp + " " +
                                                 FarmaPRNUtility.alinearIzquierda(pNumCompCoPago, 65));
                            }
                        }
                    } else {
    
                        if (VariablesConvenioBTLMF.vFlgImprimeImportes.equals("1")) {
                            vPrint.add(vMargen + FarmaPRNUtility.alinearIzquierda("  Institución: " +
                                                                              VariablesConvenioBTLMF.vInstitucion.trim().toUpperCase(),
                                                                              65));
    
                            vPrint.add(vMargen + FarmaPRNUtility.alinearIzquierda("  Convenio: " +
                                                                              VariablesConvenioBTLMF.vNomConvenio.trim().toUpperCase(),
                                                                              65) + "     " + " " +
                                             "          IGV    S/. " +
                                             FarmaPRNUtility.alinearDerecha(pValIgvComPago, 10));
                            vPrint.add(vMargen + FarmaPRNUtility.alinearIzquierda("  Beneficiario: " +
                                                                              VariablesConvenioBTLMF.vNomCliente, 65) +
                                             "     " + " " + "          ---------------------");
    
                            double pValTotalNetoRedondeo =
                                FarmaUtility.getDecimalNumber(pValTotalNeto) + FarmaUtility.getDecimalNumber(pValRedondeoComPago);
    
                            vPrint.add(vMargen + "  " + FarmaPRNUtility.alinearIzquierda(" ", 65) + " " +
                                             "        Total    S/. " +
                                             FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(pValTotalNetoRedondeo),
                                                                            10));
                        } else {
                            vPrint.add(vMargen + FarmaPRNUtility.alinearIzquierda("  Institución: " +
                                                                              VariablesConvenioBTLMF.vInstitucion.trim().toUpperCase(),
                                                                              65));
    
                            vPrint.add(vMargen + FarmaPRNUtility.alinearIzquierda("  Convenio: " +
                                                                              VariablesConvenioBTLMF.vNomConvenio.trim().toUpperCase(),
                                                                              65) + "     " + " " + " ");
                            vPrint.add(vMargen + FarmaPRNUtility.alinearIzquierda("  Beneficiario: " +
                                                                              VariablesConvenioBTLMF.vNomCliente, 65) +
                                             "     " + " " + "          ");
    
    
                        }
    
                    }
                }

                vPrint.add(vMargen + "  ");

                vPrint.add(vMargen + " REDO: " + pValRedondeoComPago + " CAJERO: " + VariablesCaja.vNomCajeroImpreso +
                                 " " + VariablesCaja.vApePatCajeroImpreso + " " + " CAJA: " +
                                 VariablesCaja.vNumCajaImpreso + " TURNO: " + VariablesCaja.vNumTurnoCajaImpreso +
                                 " VEND: " + VariablesCaja.vNomVendedorImpreso + " " +
                                 VariablesCaja.vApePatVendedorImpreso);
                if (pImprDatAdic.equals("1")) {

                    if (VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic != null &&
                        VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.size() > 0) {
                        
                        vPrint.add(vMargen + "  Datos Adicionales");

                        int nroDatosAdi = VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.size();

                        for (int j = 0; j < nroDatosAdi; j++) {
                            Map datosAdicConv = (Map)VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.get(j);

                            String pCodigoCampo = (String)datosAdicConv.get("COD_CAMPO");
                            String pNombCampo = (String)datosAdicConv.get("NOMBRE_CAMPO");

                            String pDesCampo = (String)datosAdicConv.get("DESCRIPCION_CAMPO");
                            String vFlgImprime = (String)datosAdicConv.get("FLG_IMPRIME");

                            
                            if (vFlgImprime.equals("1")) {
                            
                                vPrint.add(vMargen + "  - " + pNombCampo + "    " + pDesCampo + " ");
                            }
                            
                        }
                    }
                }


                
                VariablesCaja.vEstadoSinComprobanteImpreso = "N";

            } catch (Exception e) {
                log.error("", e);
                VariablesCaja.vEstadoSinComprobanteImpreso = "S";
                log.info("**** Fecha :" + pFechaBD);
                log.info("**** CORR :" + VariablesCaja.vNumPedVta);
                log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
                log.info("**** IP :" + FarmaVariables.vIpPc);
                log.info("Error al imprimir la Guia: " + e);
            }

        return vPrint;

    }

    @Override
    public ArrayList<String> formatoBoleta(JDialog pJDialog, String pFechaBD, ArrayList pDetalleComprobante,
                                           String pValTotalNeto, String pValRedondeo, String pNumComprobante,
                                           String pNomImpreso, String pDirImpreso, String pValTotalAhorro,
                                           String pRuta, boolean bol, int margen, boolean vEsPedidoConvenio,
                                           String pImprDatAdic, String pTipoClienteConvenio, String pCodTipoConvenio,
                                           String pValRedondeoCompPago, String pValIgvComPago,
                                           String pValCopagoCompPago, String pValIgvCompCoPago, String pNumCompRef) {
        String indProdVirtual = "";
        VariablesCaja.vIndPedidoConProdVirtualImpresion = false;

        ArrayList<String> vPrint = new ArrayList<>();
            String vMargen = FarmaPRNUtility.llenarBlancos(margen);
            try {

                if (VariablesPtoVenta.vIndDirMatriz) {
                    vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(30) + VariablesPtoVenta.vDireccionCortaMatriz);
                }
                //JMIRANDA 22.08.2011 Cambio para verificar si imprime
                if (UtilityVentas.getIndImprimeCorrelativo()) {
                    vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(11) + pFechaBD + "   CORR." +
                                     VariablesCaja.vNumPedVta);
                } else {
                    vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(11) + pFechaBD);
                }
                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(11) +
                                 FarmaPRNUtility.alinearIzquierda(pNomImpreso.trim(), 60));

                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(11) +
                                 FarmaPRNUtility.alinearIzquierda(pDirImpreso.trim(), 60) + "   No. " +
                                 pNumComprobante.substring(0, 3) + "-" + pNumComprobante.substring(3, 10));
                vPrint.add(vMargen + " ");
                
                vPrint.add(vMargen + " ");
                
                int linea = 0;
                
                //INI ASOSA - 20/07/2015 - IGVAZNIA
                String codProd = "";
                String partAranc = "";                
                //FIN ASOSA-  20/07/2015 - IGVAZNIA
                for (int i = 0; i < pDetalleComprobante.size(); i++) {
                
                    String valor = ((String)((ArrayList)pDetalleComprobante.get(i)).get(16)).toString().trim();
                    
                    if (valor.equals("0.000"))
                        valor = " ";
                    
                    //INI ASOSA - 20/07/2015 - IGVAZNIA
                    codProd = ((String)((ArrayList)pDetalleComprobante.get(i)).get(6)).trim();
                    partAranc = UtilityCaja.obtenerPartidaArancelaria(codProd);
                    if (partAranc != null && !partAranc.trim().equals("")) {
                        partAranc = VariablesCaja.espacioBlancoBoleta + partAranc;
                    } else {
                        partAranc = "";
                    }
                    
                    //String codigoProd = ((String)((ArrayList)pDetalleComprobante.get(i)).get(6)).trim();
                    String descProd = ((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim();
                    String unidVta = ((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim();
                    String cantVenta = ((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim();
                    String lab = ((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim();
                    String precioSubTotal = ((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim();
                    String precioUnit = ((String)((ArrayList)pDetalleComprobante.get(i)).get(4)).trim();
                    
                    String lineaDetalle;
                    if(UtilityPtoVenta.isLocalVentaMayorista()){
                        String loteProd = ((String)((ArrayList)pDetalleComprobante.get(i)).get(7)).trim();
                        String fechaVenc = ((String)((ArrayList)pDetalleComprobante.get(i)).get(14)).trim();
                        
                        lineaDetalle = FarmaPRNUtility.alinearDerecha(cantVenta, 11) + "   " +
                                     FarmaPRNUtility.alinearIzquierda(descProd, 27) + " " +
                                     FarmaPRNUtility.alinearIzquierda(unidVta, 11) + "  " + 
                                     FarmaPRNUtility.alinearIzquierda(lab, 5) + " " +
                                     FarmaPRNUtility.alinearIzquierda(loteProd, 10) + " " +
                                     FarmaPRNUtility.alinearIzquierda(fechaVenc, 10) + "  " +
                            FarmaPRNUtility.alinearDerecha(valor, 8) + "" +
                            FarmaPRNUtility.alinearDerecha(precioSubTotal, 10) ;
                    }else{
                        lineaDetalle = FarmaPRNUtility.alinearDerecha(cantVenta, 11) + "   " +
                                     FarmaPRNUtility.alinearIzquierda(descProd, 27) + " " +
                                     FarmaPRNUtility.alinearIzquierda(unidVta, 11) + "  " + 
                                     FarmaPRNUtility.alinearIzquierda(lab, 16) + "  " +
                                     FarmaPRNUtility.alinearDerecha(precioUnit, 10) + " " +
                            FarmaPRNUtility.alinearDerecha(valor, 8) + "" +
                            FarmaPRNUtility.alinearDerecha(precioSubTotal, 10) ;
                    }
                    vPrint.add(vMargen + lineaDetalle);
                    
                    linea += 1;
                    indProdVirtual = FarmaUtility.getValueFieldArrayList(pDetalleComprobante, i, 8);
                    //verifica que solo se imprima un producto virtual en el comprobante
                    if (i == 0 && indProdVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                        VariablesCaja.vIndPedidoConProdVirtualImpresion = true;
                    else
                        VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
                }

                if (VariablesCaja.vIndPedidoConProdVirtualImpresion) {
                    vPrint.add(vMargen + "");
                    
                    UtilityCaja.impresionInfoVirtual(vPrint,
                                         FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 9),
                                         //tipo prod virtual
                            FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 13), //codigo aprobacion
                            FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 11), //numero tarjeta
                            FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 12), //numero pin
                            FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 10), //numero telefono
                            FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 5), //monto
                            VariablesCaja.vNumPedVta, //Se añadio el parametro
                            FarmaUtility.getValueFieldArrayList(pDetalleComprobante, 0, 6)); //cod_producto

                    linea = linea + 4;
                }

                if (VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                    linea++;
                }
                //MODIFICADO POR DVELIZ 13.10.08
                //
                if (!VariablesVentas.vEsPedidoConvenio) {
                    if (pDetalleComprobante.size() < 8) {
                        for (int j = linea; j <= 8; j++) {
                            if (!VariablesCaja.vImprimeFideicomizo) {
                                vPrint.add(vMargen + " ");
                            }
                        }
                    }
                } else {
                    for (int j = linea; j <= ConstantsPtoVenta.TOTAL_LINEAS_POR_BOLETA; j++)
                        if (!VariablesCaja.vImprimeFideicomizo)
                            vPrint.add(vMargen + " ");
                }

                //*************************************INFORMACION DEL CONVENIO*************************************************//
                //*******************************************INICIO************************************************************//

                if (VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                    
                        ArrayList aInfoPedConv = new ArrayList();
                        DBConvenio.obtieneInfoPedidoConv(aInfoPedConv, VariablesCaja.vNumPedVta,
                                                         "" + FarmaUtility.getDecimalNumber(pValTotalNeto));

                        for (int i = 0; i < aInfoPedConv.size(); i++) {
                            ArrayList registro = (ArrayList)aInfoPedConv.get(i);
                            //JCORTEZ 10/10/2008 Se muestra informacion de convenio si no es de tipo competencia
                            String Ind_Comp = ((String)registro.get(8)).trim();
                            if (Ind_Comp.equalsIgnoreCase("N")) {
                                vPrint.add(vMargen + FarmaPRNUtility.alinearIzquierda(" Titular Cliente: " +
                                                                                  ((String)registro.get(4)).trim(),
                                                                                  60) + " " +
                                        FarmaPRNUtility.alinearIzquierda("Co-Pago: " +
                                                                         ((String)registro.get(3)).trim() + " %", 25));
                                /* 07.03.2008 ERIOS Si se tiene el valor del credito disponible, se muestra en el comprobante */
                                String vCredDisp = ((String)registro.get(7)).trim();
                                if (vCredDisp.equals("")) {
                                    vPrint.add(vMargen +  //FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+vCoPago,60)+" "+
                                            FarmaPRNUtility.alinearIzquierda(" Credito: S/. " +
                                                                             ((String)registro.get(5)).trim(), 60) +
                                            " " +
                                            FarmaPRNUtility.alinearIzquierda("A Cuenta: S/. " + ((String)registro.get(6)).trim(),
                                                                             25));
                                } else {
                                    vPrint.add(vMargen +  //FarmaPRNUtility.alinearIzquierda(" Credito: S/. "+vCoPago,60)+" "+
                                            FarmaPRNUtility.alinearIzquierda(" Credito: S/. " +
                                                                             ((String)registro.get(5)).trim(), 60) +
                                            " " +
                                            FarmaPRNUtility.alinearIzquierda("A Cuenta: S/. " + ((String)registro.get(6)).trim(),
                                                                             25) + " " +
                                            FarmaPRNUtility.alinearIzquierda("Cred Disp: S/." + vCredDisp, 25));
                                }
                            }
                        }

                }

                //ERIOS 25.07.2008 imprime el monto ahorrado.
                double auxTotalDcto = FarmaUtility.getDecimalNumber(pValTotalAhorro);

                //DUBILLUZ 22.08.2008 MSG DE CUPONES
                String msgCumImpresos = " ";
                if (VariablesCaja.vNumCuponesImpresos > 0) {
                    String msgNumCupon = "";
                    if (VariablesCaja.vNumCuponesImpresos == 1) {
                        msgNumCupon = "CUPON";
                    } else {
                        msgNumCupon = "CUPONES";
                    }
                    msgCumImpresos = " UD. GANO " + VariablesCaja.vNumCuponesImpresos + " " + msgNumCupon;
                }

                boolean isImprimeAhorroAntiguo = true;
                if(VariablesPuntos.frmPuntos!=null && VariablesPuntos.frmPuntos.getTarjetaBean()!=null){
                    isImprimeAhorroAntiguo = false;
                }
                if (auxTotalDcto > 0 && isImprimeAhorroAntiguo) {
                    
                    String obtenerMensaje = "";
                    String indFidelizado = "";
                    
                    if (VariablesFidelizacion.vNumTarjeta.trim().length() > 0) {
                        indFidelizado = "S";
                    } else {
                        indFidelizado = "N";
                    }
                    
                    obtenerMensaje = UtilityCaja.obtenerMensaAhorro(pJDialog, indFidelizado);
                    vPrint.add(vMargen + "" + obtenerMensaje + " " + " S/. " + pValTotalAhorro + "  " + msgCumImpresos);
                    
                } else {
                    if (VariablesCaja.vNumCuponesImpresos > 0) {
                        vPrint.add(vMargen + "                         " + msgCumImpresos);
                        
                    } else {
                        vPrint.add(vMargen + " ");
                        
                    }
                }

                //*********************************************FIN*************************************************************//
                //*************************************INFORMACION DEL CONVENIO***********************************************//

                VariablesVentas.vTipoPedido = DBCaja.obtieneTipoPedido();
                VariablesCaja.vFormasPagoImpresion = DBCaja.obtieneFormaPagoPedido();

                if (VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                    vPrint.add(vMargen + FarmaPRNUtility.alinearIzquierda(" - DISTRIBUCION GRATUITA - ", 60));
                }
                if (VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_MESON) ||
                    VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL)) {
                    VariablesVentas.vTituloDelivery = "";
                } else
                    VariablesVentas.vTituloDelivery =
                            ""; //" - PEDIDO DELIVERY - " ; //ERIOS 2.4.7 No se imprime referencia a Delivery

                
                vPrint.add(vMargen + " SON: " +
                                 FarmaPRNUtility.alinearIzquierda(FarmaPRNUtility.montoEnLetras(pValTotalNeto).trim(),
                                                                  65) + " " + " Total Venta   S/. " +
                                 FarmaPRNUtility.alinearDerecha(pValTotalNeto, 10));
               
                vPrint.add(vMargen + " REDO: " + pValRedondeo + " CAJERO: " + VariablesCaja.vNomCajeroImpreso + " " +
                                 VariablesCaja.vApePatCajeroImpreso + " " + " CAJA: " + VariablesCaja.vNumCajaImpreso +
                                 " TURNO: " + VariablesCaja.vNumTurnoCajaImpreso + " VEND: " +
                                 VariablesCaja.vNomVendedorImpreso + " " + VariablesCaja.vApePatVendedorImpreso);
                
                vPrint.add(vMargen + " Forma(s) de pago: " + VariablesCaja.vFormasPagoImpresion +
                                 FarmaPRNUtility.llenarBlancos(11) + VariablesVentas.vTituloDelivery);
                
                /*dubilluz 2011.09.16*/
                if (VariablesCaja.vImprimeFideicomizo) {
                    String[] lineas = VariablesCaja.vCadenaFideicomizo.trim().split("@");
                    if (lineas.length > 0) {
                        for (int i = 0; i < lineas.length; i++) {
                            vPrint.add(vMargen + "" + lineas[i].trim());
                        }
                    } else {
                        vPrint.add(vMargen + "" + VariablesCaja.vCadenaFideicomizo.trim());
                    }
                }
                /*FIN dubilluz 2011.09.16*/

                VariablesCaja.vEstadoSinComprobanteImpreso = "N";

            } catch (Exception e) {
                log.error("",e);
                VariablesCaja.vEstadoSinComprobanteImpreso = "S";
                log.info("**** Fecha :" + pFechaBD);
                log.info("**** CORR :" + VariablesCaja.vNumPedVta);
                log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
                log.info("**** IP :" + FarmaVariables.vIpPc);
                log.error("Error al imprimir la boleta: ",e);
            }

        
        return vPrint;
    }
    
    @Override
    public ArrayList<String> formatoNotaCredito(JDialog pJDialog, ArrayList pDetalleComprobante, String pValTotalNeto,
                                            String pNumComprobante, String pValIgvComPago, String pValCopagoCompPago,
                                            String pPorcIgv, String pNumCompCoPago, String pValTotalAhorro,
                                            boolean vEsPedidoConvenio, String pImprDatAdic,
                                            String pTipoClienteConvenio, String pCodTipoConvenio, String pFechaBD,
                                            String pRefTipComp, String pValRedondeoComPago, String pRazonSocial,
                                            String pDireccionDestino, String pNumeroRUC, String vPrctBeneficiario,
                                            String vPrctEmpresa, int margen, String pValTotalBruto,
                                            String pValTotalAfecto, String pValTotalDescuento, int numDocumento) {
        return null;
    }
}
