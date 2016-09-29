package mifarma.ptoventa.administracion.impresoras.reference;

import java.util.ArrayList;
import java.util.Map;

import javax.swing.JDialog;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaPRNUtility;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.convenioBTLMF.reference.DBConvenio;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.puntos.reference.VariablesPuntos;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Formatos para comprobantes de FASA
 * @author ERIOS
 * @since 20.01.2016
 */
public class FormatoImpresionB implements StrategyImpresion {
    
    private static final Logger log = LoggerFactory.getLogger(FormatoImpresionB.class);
    
    public FormatoImpresionB() {
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
                                            String vPrctEmpresa, int margen, String pValTotalBruto,
                                            String pValTotalAfecto, String pValTotalDescuento, int numDocumento) {
        if(vEsPedidoConvenio){
            return formatoFacturaConvenio(pJDialog, pDetalleComprobante, pValTotalNeto, pNumComprobante, pValIgvComPago,
                                       pValCopagoCompPago, "pValIgvCompCoPago", pNumCompCoPago, "", true, pImprDatAdic,
                                       pTipoClienteConvenio, pCodTipoConvenio, pFechaBD, pRefTipComp,
                                       pValRedondeoComPago,margen);
        }else{
            return formatoFacturaNormal(pJDialog, pFechaBD, pDetalleComprobante, pValTotalBruto, pValTotalNeto,
                                       pValTotalAfecto, pValTotalDescuento, pValIgvComPago, pPorcIgv, pValRedondeoComPago,
                                       pNumComprobante, pRazonSocial, pNumeroRUC, pDireccionDestino, pValTotalAhorro,
                                       "", true, margen,numDocumento);
        }
    }

    private ArrayList<String> formatoFacturaNormal(JDialog pJDialog, String pFechaBD, ArrayList pDetalleComprobante,
                                       String pValTotalBruto, String pValTotalNeto, String pValTotalAfecto,
                                       String pValTotalDcto, String pValTotalIgv, String pPorcIgv, String pValRedondeo,
                                       String pNumComprobante, String pNomImpreso, String pNumDocImpreso,
                                       String pDirImpreso, String pValTotalAhorro, String pRuta, boolean bol,
                                                   int margen, int numDocumento){

        String indProdVirtual = "";
        int nroArticulos = 0;

        
        VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
        ArrayList<String> vPrint = new ArrayList<>();
        String vMargen = FarmaPRNUtility.llenarBlancos(margen);
            try {
                String dia = pFechaBD.substring(0, 2);
                String mesLetra = FarmaUtility.devuelveMesEnLetras(Integer.parseInt(pFechaBD.substring(3, 5)));
                String ano = pFechaBD.substring(6, 10);
                String hora = pFechaBD.substring(11, 19);
                

                vPrint.add(vMargen + " ");
                
                //LOCAL
                ArrayList lstDirecMatriz = FarmaUtility.splitString(VariablesPtoVenta.vDireccionMatriz, 32);

                vPrint.add(vMargen + " ");
                
                vPrint.add(vMargen + " ");
                
                vPrint.add(vMargen + " ");
                
                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(50) + lstDirecMatriz.get(0).toString() +
                                 FarmaPRNUtility.llenarBlancos(10) + "No. " + pNumComprobante.substring(0, 3) + "-" +
                                 pNumComprobante.substring(3, 10));
                
                //SENIOR(ES)-SI LA LONGITUD DE NOMBRE IMPRESO ES MAYOR A  40 SE CORTA EN EL ULTIMO ESPACIO EN BLANCO Y LA PALABRA SOBRANTE
                //SE IMPRIME EN EL SIGUIENTE REGLON
                if (pNomImpreso.length() > 40) {
                    int posBlanc =
                        pNomImpreso.lastIndexOf(" ", pNomImpreso.length()); //SE OBTIENE LA POSCION EN BLANCO
                    String lastNomImpreso =
                        pNomImpreso.substring(posBlanc, pNomImpreso.length()); //SE OBTIENE LA ULTIMA PALABRA
                    pNomImpreso = pNomImpreso.substring(0, posBlanc);
                    vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(10) +
                                     FarmaPRNUtility.alinearIzquierda(pNomImpreso.trim(), 40) +
                                     lstDirecMatriz.get(1).toString());
                    
                    vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(10) +
                                     FarmaPRNUtility.alinearIzquierda(lastNomImpreso.trim(), 40) +
                                     lstDirecMatriz.get(2).toString());
                    
                } else {
                    vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(10) +
                                     FarmaPRNUtility.alinearIzquierda(pNomImpreso.trim(), 40) +
                                     lstDirecMatriz.get(1).toString());
                    
                    vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(50) + lstDirecMatriz.get(2).toString());
                    
                }

                //DIRECCION
                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(10) +
                                 FarmaPRNUtility.alinearIzquierda(pDirImpreso.trim(), 60));
                
                vPrint.add(vMargen + " ");
                
                //RUC y FECHA
                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(10) + pNumDocImpreso.trim() +
                                 FarmaPRNUtility.llenarBlancos(73) + dia + " de " + mesLetra + " del " + ano +
                                 "     " + hora);
                
                // ESPACIOS ENTRE LA CABECERA Y EL DETALLE DE LA FACTURA
                vPrint.add(vMargen + " ");
                
                vPrint.add(vMargen + " ");
                
                vPrint.add(vMargen + " ");
                
                vPrint.add(vMargen + " ");
                
                vPrint.add(vMargen + " ");
                
                vPrint.add(vMargen + " ");
                
                vPrint.add(vMargen + " ");
                
                int linea = 0;
                double pMontoOld = 0, pMontoNew = 0, pMontoDescuento = 0;
                
                for (int i = 0; i < pDetalleComprobante.size(); i++) {
                    nroArticulos++; //= nroArticulos + Integer.parseInt(((ArrayList)pDetalleComprobante.get(i)).get(0).toString());
                    vPrint.add(vMargen + " " +
                                     FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(6)).trim(),
                                                                      6) + FarmaPRNUtility.llenarBlancos(5) +
                                     FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),
                                                                      60) + FarmaPRNUtility.llenarBlancos(3) +
                                     FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim(),
                                                                    11) + FarmaPRNUtility.llenarBlancos(5) +
                            //UNIDAD DE MEDIDA
                            //FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),14) + "   " +
                            // LABORATORIO
                            //FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),20) + FarmaPRNUtility.llenarBlancos(2) +
                            FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(4)).trim(),
                                                           13) + FarmaPRNUtility.llenarBlancos(14) +
                            FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),
                                                           10));


                    linea += 1;
                    indProdVirtual = FarmaUtility.getValueFieldArrayList(pDetalleComprobante, i, 8);
                    //verifica que solo se imprima un producto virtual en el comprobante
                    if (i == 0 && indProdVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                        VariablesCaja.vIndPedidoConProdVirtualImpresion = true;
                    else
                        VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
                }

                if (VariablesCaja.vIndPedidoConProdVirtualImpresion &&
                    VariablesVentas.vDniRimac.equals("")    //ASOSA - 16/01/2015 - RIMAC
                    ) {
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
                        for (int j = linea; j < 10; j++) {
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

                
                //ERIOS 25.07.2008 imprime el monto ahorrado.
                double auxTotalDcto = FarmaUtility.getDecimalNumber(pValTotalAhorro);
                boolean isImprimeAhorroAntiguo = true;
                if(VariablesPuntos.frmPuntos!=null && VariablesPuntos.frmPuntos.getTarjetaBean()!=null){
                    isImprimeAhorroAntiguo = false;
                }
                if (auxTotalDcto > 0 && numDocumento == 0 && isImprimeAhorroAntiguo) {
                    
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

                vPrint.add(vMargen + " ");
                
                vPrint.add(vMargen + " ");
                

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
                

                if (!VariablesCaja.vImprimeFideicomizo) {
                    vPrint.add(vMargen + " ");
                    
                    vPrint.add(vMargen + " ");
                    
                }

                if (VariablesCaja.vImprimeFideicomizo) {
                    String[] lineas = VariablesCaja.vCadenaFideicomizo.trim().split("@");
                    
                    if (lineas.length > 0) {
                        for (int i = 0; i < lineas.length; i++) {
                            if (lineas[i].trim().length() > 0) {
                                
                                vPrint.add(vMargen + "" + lineas[i].trim());
                                
                            }
                        }
                    }
                }

                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(95) + FarmaPRNUtility.alinearDerecha(pPorcIgv, 6) + "%");
                vPrint.add(vMargen + " ");
                
                vPrint.add(vMargen + "     " + FarmaPRNUtility.alinearDerecha(nroArticulos, 10) +
                                 FarmaPRNUtility.llenarBlancos(65) +
                        //FarmaPRNUtility.alinearDerecha(pValTotalBruto,10) + FarmaPRNUtility.llenarBlancos(10) +
                        "S/. " + FarmaPRNUtility.alinearDerecha(pValTotalIgv, 10) + FarmaPRNUtility.llenarBlancos(25) +
                        "S/. " + FarmaPRNUtility.alinearDerecha(pValTotalNeto, 10));
                

                //líneas necesarias para que al imprimir la 2da factura hacia adelante, se imprima en la posición correcta.
                vPrint.add(vMargen + " "); //Linea 39
                
                vPrint.add(vMargen + " "); //Linea 40
                

                //vPrint.endPrintServiceSinCompletarDelivery();
                
                
                VariablesCaja.vEstadoSinComprobanteImpreso = "N";
            } catch (Exception e) {
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
        
        String indProdVirtual = "";
        int nroArticulos = 0;
        
        VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
        float subTotal = 0;
        float montoIGV = 0;
        float SumSubTotal = 0;
        double SumMontoIGV = 0;

        String pNomImpreso = VariablesConvenioBTLMF.vInstitucion;
        String pDirImpreso = VariablesConvenioBTLMF.vDireccion;
        String pNumDocImpreso = VariablesConvenioBTLMF.vRuc;

        ArrayList<String> vPrint = new ArrayList<>();
            String vMargen = FarmaPRNUtility.llenarBlancos(margen);
            try {

                String dia = pFechaBD.substring(0, 2);
                String mesLetra = FarmaUtility.devuelveMesEnLetras(Integer.parseInt(pFechaBD.substring(3, 5)));
                String ano = pFechaBD.substring(6, 10);
                String hora = pFechaBD.substring(11, 19);
                
                vPrint.add(vMargen + " ");
                
                //LOCAL
                ArrayList lstDirecMatriz = FarmaUtility.splitString(VariablesPtoVenta.vDireccionMatriz, 32);

                vPrint.add(vMargen + " ");
                
                vPrint.add(vMargen + " ");
                
                vPrint.add(vMargen + " ");
                
                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(50) + lstDirecMatriz.get(0).toString() +
                                 FarmaPRNUtility.llenarBlancos(10) + "No. " + pNumComprobante.substring(0, 3) + "-" +
                                 pNumComprobante.substring(3, 10));
                
                //SENIOR(ES)-SI LA LONGITUD DE NOMBRE IMPRESO ES MAYOR A  40 SE CORTA EN EL ULTIMO ESPACIO EN BLANCO Y LA PALABRA SOBRANTE
                //SE IMPRIME EN EL SIGUIENTE REGLON
                if (pNomImpreso.length() > 40) {
                    int posBlanc =
                        pNomImpreso.lastIndexOf(" ", pNomImpreso.length()); //SE OBTIENE LA POSCION EN BLANCO
                    String lastNomImpreso =
                        pNomImpreso.substring(posBlanc, pNomImpreso.length()); //SE OBTIENE LA ULTIMA PALABRA
                    pNomImpreso = pNomImpreso.substring(0, posBlanc);
                    vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(10) +
                                     FarmaPRNUtility.alinearIzquierda(pNomImpreso.trim(), 40) +
                                     lstDirecMatriz.get(1).toString());
                    
                    vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(10) +
                                     FarmaPRNUtility.alinearIzquierda(lastNomImpreso.trim(), 40) +
                                     lstDirecMatriz.get(2).toString());
                    
                } else {
                    vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(10) +
                                     FarmaPRNUtility.alinearIzquierda(pNomImpreso.trim(), 40) +
                                     lstDirecMatriz.get(1).toString());
                    
                    vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(50) + lstDirecMatriz.get(2).toString());
                    
                }

                //DIRECCION
                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(10) +
                                 FarmaPRNUtility.alinearIzquierda(pDirImpreso.trim(), 60));
                
                vPrint.add(vMargen + " ");
                
                //RUC y FECHA
                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(10) + pNumDocImpreso.trim() +
                                 FarmaPRNUtility.llenarBlancos(73) + dia + " de " + mesLetra + " del " + ano +
                                 "     " + hora);
                
                // ESPACIOS ENTRE LA CABECERA Y EL DETALLE DE LA FACTURA
                vPrint.add(vMargen + " ");
                
                vPrint.add(vMargen + " ");
                
                vPrint.add(vMargen + " ");
                
                //CHUANES 2.2.8 SE IMPRIME EL NUMERO DE GUIA
                //SE VALIDA PORQUE ALGUNOS CONVENIOS NO GENERAN GUIAS POR LO TANTO pNumCompCoPago ES NULLO O VACIO
                if (pNumCompCoPago == null || pNumCompCoPago.equals("") ||
                    !pRefTipComp.equals(ConstantsPtoVenta.TIP_COMP_GUIA)) {
                    vPrint.add(vMargen + " ");
                    
                } else {
                    
                    vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(73) + "No.Guia " + pNumCompCoPago.substring(0, 3) +
                                     "-" + pNumCompCoPago.substring(3, 10));
                    
                }
                vPrint.add(vMargen + " ");
                
                vPrint.add(vMargen + " ");
                

                vPrint.add(vMargen + " ");
                
                int linea = 0;
                double pMontoOld = 0, pMontoNew = 0, pMontoDescuento = 0;
                
                int vNroEspacio = 0;
                for (int i = 0; i < pDetalleComprobante.size(); i++) {
                    nroArticulos++; //= nroArticulos + Integer.parseInt(((ArrayList)pDetalleComprobante.get(i)).get(0).toString());
                    vPrint.add(vMargen + " " +
                                     FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(6)).trim(),
                                                                      6) + FarmaPRNUtility.llenarBlancos(5) +
                                     FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),
                                                                      60) + FarmaPRNUtility.llenarBlancos(3) +
                                     FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim(),
                                                                    11) + FarmaPRNUtility.llenarBlancos(5) +
                            //UNIDAD DE MEDIDA
                            //FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),14) + "   " +
                            // LABORATORIO
                            //FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),20) + FarmaPRNUtility.llenarBlancos(2) +
                            FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(4)).trim(),
                                                           13) + FarmaPRNUtility.llenarBlancos(14) +
                            FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),
                                                           10));

                    
                    linea += 1;
                    subTotal =
                            new Double(FarmaUtility.getDecimalNumber(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim())).floatValue();
                    montoIGV =
                            new Double(FarmaUtility.getDecimalNumber(((String)((ArrayList)pDetalleComprobante.get(i)).get(18)).trim())).floatValue();
                    SumMontoIGV = SumMontoIGV + montoIGV;
                    
                    SumSubTotal = SumSubTotal + subTotal;

                    //linea += 1;
                    indProdVirtual = FarmaUtility.getValueFieldArrayList(pDetalleComprobante, i, 8);

                    if (VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                        linea++;
                    }
                }
                //MODIFICADO POR DVELIZ 13.10.08
                //
                for (int j = linea; j < ConstantsPtoVenta.TOTAL_LINEAS_POR_BOLETA; j++)
                    vPrint.add(vMargen + " ");

                //*************************************INFORMACION DEL CONVENIO***********************************************//

                double porcCopago =
                    Math.round((FarmaUtility.getDecimalNumber(pValCopagoCompPago) / (FarmaUtility.getDecimalNumber(pValTotalNeto) +
                                                                                     FarmaUtility.getDecimalNumber(pValCopagoCompPago))) *
                               100);
                SumMontoIGV = SumMontoIGV - ((SumMontoIGV * porcCopago) / 100);
                double ValCopagoCompPagoSinIGV = ((SumSubTotal * porcCopago) / 100);

                vPrint.add(vMargen + "      " + FarmaPRNUtility.alinearIzquierda(" ", 85) + "        " +
                                 "    Sub Total   S/. " +
                                 FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(SumSubTotal), 10));

                double pValTotalNetoRedondeo =
                    FarmaUtility.getDecimalNumber(pValTotalNeto) + FarmaUtility.getDecimalNumber(pValRedondeoComPago);

                pValTotalNetoRedondeo =
                        FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(pValTotalNetoRedondeo, 2)); //CHUANES 12.03.2014 SE PONE EL FORMATO DE 2 DECIMALES AL VALOR REDONDEADO

                //ERIOS 12.09.2013 Imprime direccion local
                String vLinea = "", vLineaDirecLocal1 = "", vLineaDirecLocal2 = "", vLineaDirecLocal3 = "";
                if (VariablesPtoVenta.vIndDirLocal) {
                    ArrayList lstDirecLocal =
                        FarmaUtility.splitString("NUEVA DIRECCION: " + FarmaVariables.vDescCortaDirLocal, 46);
                    vLineaDirecLocal1 = lstDirecLocal.get(0).toString();
                    vLineaDirecLocal2 = ((lstDirecLocal.size() > 1) ? lstDirecLocal.get(1).toString() : "");
                    vLineaDirecLocal3 = ((lstDirecLocal.size() > 2) ? lstDirecLocal.get(2).toString() : "");
                }

                if (pCodTipoConvenio.equals("1")) {
                    vLinea =
                            FarmaPRNUtility.alinearIzquierda("  SON: " + FarmaPRNUtility.montoEnLetras(pValTotalNetoRedondeo),
                                                             85) + "            " + "Coaseguro(" +
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
                            FarmaPRNUtility.alinearIzquierda("  Documento de Referencia Nro " + pNumCompCoPago + " " + "(" +
                                                             FarmaUtility.formatNumber(porcCopago, "") + "%)" + " - " +
                                                             "S/." + pValCopagoCompPago, 85) + vLineaDirecLocal3;
                    vPrint.add(vMargen + vLinea);
                    
                } else {
                    vLinea =
                            FarmaPRNUtility.alinearIzquierda("  SON: " + FarmaPRNUtility.montoEnLetras(pValTotalNetoRedondeo),
                                                             85) + "            ";
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
                linea = 0;
                if (pImprDatAdic.equals("1")) {
                    if (VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic != null &&
                        VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.size() > 0) {
                        
                        vPrint.add(vMargen + "  Datos Adicionales");
                        linea++;
                        String lineaInfAdic = "";
                        for (int j = 0; j < VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.size(); j++) {
                            Map datosAdicConv = (Map)VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.get(j);

                            String pNombCampo = (String)datosAdicConv.get("NOMBRE_CAMPO");
                            String pDesCampo = (String)datosAdicConv.get("DESCRIPCION_CAMPO");
                            String vFlgImprime = (String)datosAdicConv.get("FLG_IMPRIME");
                            String vCodCampo = (String)datosAdicConv.get("COD_CAMPO");

                            
                            if (vFlgImprime.equals("1") && pNombCampo != null && pDesCampo != null) {
                                //se imprimen dos informaciones en una linea
                                String temp =
                                    FarmaPRNUtility.alinearIzquierda("  - " + pNombCampo + ": " + pDesCampo, 60);

                                if ("".equalsIgnoreCase(lineaInfAdic)) { //si no existe linea, se coloca esta
                                    lineaInfAdic = temp;
                                } else { //si existe una linea, se coloca la siguiente anexa, se imprime y luego se resetea
                                    lineaInfAdic = lineaInfAdic + temp;
                                    
                                    vPrint.add(vMargen + lineaInfAdic);
                                    linea++;
                                    lineaInfAdic = "";
                                    //vPrint.add(vMargen + "  - "+pNombCampo +  ":    "+pDesCampo+" ",true);
                                }
                            }
                            
                        }
                    }
                }


                if (pCodTipoConvenio.equals("1")) {
                    //               vPrintArchivo.printLine("  Documento de Referencia Nro: "+pNumCompCoPago+" ",true);
                    //               vPrint.add(vMargen + "  Documento de Referencia Nro "+pNumCompCoPago+": ",true);
                    //               vPrintArchivo.printLine("  Doc refe de la Empresa Monto:S/." + pValCopagoCompPago +" y ("+FarmaUtility.formatNumber(porcCopago,"")+"%)",true);
                    //               vPrint.add(vMargen + "  Doc refe de la Empresa Monto:S/." + pValCopagoCompPago +" y ("+FarmaUtility.formatNumber(porcCopago,"")+"%)",true);
                }
                for (int j = linea; j < ConstantsPtoVenta.TOTAL_LINEAS_POR_BOLETA; j++)
                    vPrint.add(vMargen + " ");

                vLinea =
                        " REDO:" + pValRedondeoComPago + " CAJERO:" + VariablesCaja.vNomCajeroImpreso + " " + VariablesCaja.vApePatCajeroImpreso +
                        " " + " CAJA:" + VariablesCaja.vNumCajaImpreso + " TURNO:" +
                        VariablesCaja.vNumTurnoCajaImpreso + " VEND:" + VariablesCaja.vNomVendedorImpreso + " " +
                        VariablesCaja.vApePatVendedorImpreso;
                vLinea = FarmaPRNUtility.alinearIzquierda(vLinea, 85);
                vPrint.add(vMargen + vLinea);

                
                //líneas necesarias para que al imprimir la 2da factura hacia adelante, se imprima en la posición correcta.
                vPrint.add(vMargen + " ");
                
                vPrint.add(vMargen + " ");
                
                String pPorcIgv =
                    FarmaUtility.formatNumber(100 * ((pValTotalNetoRedondeo / (pValTotalNetoRedondeo - SumMontoIGV)) -
                                                     1));
                double vIgvRed =
                    Math.round(Double.parseDouble(pPorcIgv)); //Cesar Huanes --redondeo al numero mas cercano, siempre sera  18.
                String valor = String.valueOf(vIgvRed) + "0";                
                
                //ERIOS 03.03.2015 El modifica el calculo de subtotal
                SumMontoIGV = FarmaUtility.getDecimalNumber(pValIgvComPago);
                
                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(85) + FarmaPRNUtility.alinearDerecha(valor, 6) + "%");
                
                vPrint.add(vMargen + "     " + FarmaPRNUtility.alinearDerecha(nroArticulos, 10) +
                                 FarmaPRNUtility.llenarBlancos(65) +
                        "S/. " + FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(SumMontoIGV, 2), 10) +
                        FarmaPRNUtility.llenarBlancos(25) + "S/. " +
                        FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(pValTotalNetoRedondeo), 10));
                

                //vPrint.endPrintServiceSinCompletar();
                
                
                VariablesCaja.vEstadoSinComprobanteImpreso = "N";
            } catch (Exception e) {
                log.error("", e);
                VariablesCaja.vEstadoSinComprobanteImpreso = "S";
                log.info("**** Fecha :" + pFechaBD);
                log.info("**** CORR :" + VariablesCaja.vNumPedVta);
                log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
                log.info("**** IP :" + FarmaVariables.vIpPc);
                log.info("Error al imprimir Factura: " + e);

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
        
        VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
        float subTotal = 0;
        float SumSubTotal = 0;
        float montoIGV = 0;
        double SumMontoIGV = 0;

        ArrayList<String> vPrint = new ArrayList<>();
        String vMargen = FarmaPRNUtility.llenarBlancos(margen);
        
            try {
                
                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(30) + " ");
                
                vPrint.add(vMargen + " ");
                
                

                //ERIOS 12.09.2013 Imprime direccion local
                /*if(VariablesPtoVenta.vIndDirLocal)    //ERIOS 11.11.2013 No hay espacio la guia
                {   vPrint.add(vMargen + "     "+"NUEVA DIRECCION: "+FarmaVariables.vDescCortaDirLocal,true);
                }
                else
                {*/
                vPrint.add(vMargen + "");
                
                //}

                vPrint.add(vMargen + " ");
                
                vPrint.add(vMargen + " ");
                
                vPrint.add(vMargen + " ");
                
                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(100) + "   No. " + pNumComprobante.substring(0, 3) +
                                 "-" + pNumComprobante.substring(3, 10));
                
                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(11) + FarmaPRNUtility.alinearIzquierda(pFechaBD, 60));
                
                //JMIRANDA 22.08.2011 Cambio para verificar si imprime
                if (UtilityVentas.getIndImprimeCorrelativo()) {
                    vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(11) + pFechaBD + "   CORR." +
                                     VariablesCaja.vNumPedVta);
                } else {
                    vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(11) + " ");
                }

                vPrint.add(vMargen + " ");
                
                vPrint.add(vMargen + " ");
                
                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(11) +
                                 FarmaPRNUtility.alinearIzquierda(VariablesConvenioBTLMF.vDireccion.trim(), 60));
                
                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(11) + VariablesConvenioBTLMF.vInstitucion.trim());
                
                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(65) + VariablesConvenioBTLMF.vRuc.trim());
                
                vPrint.add(vMargen + " ");
                
                vPrint.add(vMargen + " ");
                
                vPrint.add(vMargen + " ");
                
                vPrint.add(vMargen + " ");
                
                int linea = 0;

                //imprime el detalle de los productos del comprobante
                for (int i = 0; i < pDetalleComprobante.size(); i++) {
                    //Agregado por DVELIZ 13.10.08
                    String punitario = " ";
                    String valor = " ";

                    String colSubTotal = " ";
                    if (VariablesConvenioBTLMF.vFlgImprimeImportes.equals("1")) {
                        valor = ((String)((ArrayList)pDetalleComprobante.get(i)).get(16)).toString().trim();
                        
                        if (valor.equals("0.000"))
                            valor = " ";
                        
                        colSubTotal = (String)((ArrayList)pDetalleComprobante.get(i)).get(5);
                        punitario = (String)((ArrayList)pDetalleComprobante.get(i)).get(4).toString().trim();
                    }

                    vPrint.add(vMargen + "" +
                                     FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(6)).trim(),
                                                                      6) + " " + //Codigo
                            FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim(),
                                                           11) + "   " + //Cant
                            FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim(),
                                                             27) + " " + //Descripcion
                            FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),
                                                             11) + "  " + //Presentacion
                            FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim(),
                                                             16) + "  " + //Prec. Unit.
                            FarmaPRNUtility.

                            alinearDerecha(punitario, 10) + " " +
                            //Agregado por DVELIZ 10.10.08
                            FarmaPRNUtility.

                            alinearDerecha(valor, 8) + "" + //Precio Unit.
                            FarmaPRNUtility.alinearDerecha(colSubTotal.trim(), 10)); //Monto Total

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

                for (int j = linea; j < ConstantsPtoVenta.TOTAL_LINEAS_POR_BOLETA; j++)
                    vPrint.add(vMargen + " ");


                //*************************************INFORMACION DEL CONVENIO***********************************************//
                double porcCopagoBenef = 0;
                double porcCopagoEmpresa = 0;
                double ValCopagoCompPagoSinIGV = 0;

                porcCopagoBenef = FarmaUtility.getDecimalNumber(vPrctBeneficiario);
                porcCopagoEmpresa = FarmaUtility.getDecimalNumber(vPrctEmpresa);

                ValCopagoCompPagoSinIGV =
                        FarmaUtility.getDecimalNumber(pValCopagoCompPago) - FarmaUtility.getDecimalNumber(pValIgvComCoPago);

                String vRefTipComp = "";

                if (pRefTipComp.equals(ConstantsVentas.TIPO_COMP_BOLETA))
                    vRefTipComp = "BOL";
                if (pRefTipComp.equals(ConstantsVentas.TIPO_COMP_FACTURA))
                    vRefTipComp = "FAC";
                if (pRefTipComp.equals(ConstantsVentas.TIPO_COMP_GUIA))
                    vRefTipComp = "GUIA";
                if (pRefTipComp.equals(ConstantsVentas.TIPO_COMP_TICKET))
                    vRefTipComp = "TKB";

                String vLinea;
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
                        double pValTotalNetoRedondeo =
                            FarmaUtility.getDecimalNumber(pValTotalNeto) + FarmaUtility.getDecimalNumber(pValRedondeoComPago);

                        vPrint.add(vMargen + FarmaPRNUtility.alinearIzquierda("  Institución: " +
                                                                          VariablesConvenioBTLMF.vInstitucion.trim().toUpperCase(),
                                                                          65));
                        vPrint.add(vMargen + FarmaPRNUtility.alinearIzquierda("  Convenio: " +
                                                                          VariablesConvenioBTLMF.vNomConvenio.trim().toUpperCase(),
                                                                          65) + "     " + " " +
                                         "          IGV    S/. " +
                                         FarmaPRNUtility.alinearDerecha(pValIgvComPago, 10));
                        if (VariablesConvenioBTLMF.vNomCliente.trim().equals("")) {
                            if (VariablesConvenioBTLMF.vNomClienteDigitado.trim().equals("")) {
                                
                                vPrint.add(vMargen + FarmaPRNUtility.alinearIzquierda("  Beneficiario: " + "*", 65) +
                                                 "     " + " " + "          ---------------------");
                            } else {
                                vPrint.add(vMargen + FarmaPRNUtility.alinearIzquierda("  Beneficiario: " +
                                                                                  VariablesConvenioBTLMF.vNomClienteDigitado,
                                                                                  65) + "     " + " " +
                                                 "          ---------------------");
                            }
                        } else {
                            vPrint.add(vMargen + FarmaPRNUtility.alinearIzquierda("  Beneficiario: " +
                                                                              VariablesConvenioBTLMF.vNomCliente, 65) +
                                             "     " + " " + "          ---------------------");
                        }
                        vPrint.add(vMargen + "  " + FarmaPRNUtility.alinearIzquierda(" ", 65) + " " +
                                         "        Total    S/. " +
                                         FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(pValTotalNetoRedondeo),
                                                                        10));

                        if (VariablesConvenioBTLMF.vNomCliente.trim().equals("")) {
                            if (VariablesConvenioBTLMF.vNomClienteDigitado.trim().equals("")) {
                                
                                vPrint.add(vMargen + FarmaPRNUtility.alinearIzquierda("  Beneficiario: " + "*", 65) +
                                                 "     " + " " + "          ---------------------");
                            } 
                        } 
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

                //se imprime la cabecera de la infomación del convenio
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

                        //if(VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.size() > 4 )
                        //{ nroDatosAdi = 4;
                        //}

                        //Se imprime la informacion adicional del convenio
                        String lineaInfAdic = "";
                        for (int j = 0; j < nroDatosAdi; j++) {
                            Map datosAdicConv = (Map)VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.get(j);

                            String pCodigoCampo = (String)datosAdicConv.get("COD_CAMPO");
                            String pNombCampo = (String)datosAdicConv.get("NOMBRE_CAMPO");

                            String pDesCampo = (String)datosAdicConv.get("DESCRIPCION_CAMPO");
                            String vFlgImprime = (String)datosAdicConv.get("FLG_IMPRIME");


                            if (vFlgImprime.equals("1") && pNombCampo != null && pDesCampo != null) {
                                //se imprimen dos informaciones en una linea
                                String temp =
                                    FarmaPRNUtility.alinearIzquierda("  - " + pNombCampo + ": " + pDesCampo, 60);

                                if ("".equalsIgnoreCase(lineaInfAdic)) { //si no existe linea, se coloca esta

                                    lineaInfAdic = temp;

                                } else { //si existe una linea, se coloca la siguiente anexa, se imprime y luego se resetea

                                    lineaInfAdic = lineaInfAdic + temp;
                                    vPrint.add(vMargen + lineaInfAdic);
                                    lineaInfAdic = "";

                                }
                            }
                            //  }
                            //}
                        }
                        //si al terminar de imprimir quedaron datos, imprimir los mismo
                        if (!"".equalsIgnoreCase(lineaInfAdic)) {
                            vPrint.add(vMargen + lineaInfAdic);
                            lineaInfAdic = "";
                        }
                    }
                }

                //LLEIVA 12-Nov-2013 - La cant de las lineas para la guia es 42, si falta se completa con lineas en blanco
                linea = vPrint.size();
                if (linea < 40) {
                    int dif = 40 - linea;
                    for (int i = 0; i < dif; i++) {
                        vPrint.add(vMargen + " ");
                    }
                }

                //vPrint.endPrintServiceSinCompletarDelivery();
                
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
        return null;
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
