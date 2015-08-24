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
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FormatoImpresion {

    private static final Logger log = LoggerFactory.getLogger(FormatoImpresion.class);

    public FormatoImpresion() {
        super();
    }

    public ArrayList<String> formatoGuiaBTL(JDialog pJDialog, ArrayList pDetalleComprobante, String pValTotalNeto,
                                            String pNumComprobante, String pValIgvComPago, String pValCopagoCompPago,
                                            String pValIgvComCoPago, String pNumCompCoPago, String pRuta, boolean bol,
                                            String pImprDatAdic, String pTipoClienteConvenio, String pCodTipoConvenio,
                                            String pFechaBD, String pRefTipComp, String pValRedondeoComPago,
                                            String pRazonSocial, String pDireccionDestino, String pNumeroRUC,
                                            String vPrctBeneficiario, String vPrctEmpresa, int margen) {
        ArrayList<String> vPrint = new ArrayList<>();
        //Configuracion
        /*
        Columnas: 136
        Filas: 45
        Cabecera: 10 + 1 lineas
        Detalle: 26 + 1 lineas
        Pie: 7 lineas
        */
        //Inicio
        String pNomImpreso = "";
        String pDirImpreso = "";

        String tipvta = "03";
        String vRefTipComp = "";

        if (pRefTipComp.equals(ConstantsVentas.TIPO_COMP_BOLETA))
            vRefTipComp = "BOL";
        if (pRefTipComp.equals(ConstantsVentas.TIPO_COMP_FACTURA))
            vRefTipComp = "FAC";
        if (pRefTipComp.equals(ConstantsVentas.TIPO_COMP_GUIA))
            vRefTipComp = "GUIA";
        if (pRefTipComp.equals(ConstantsVentas.TIPO_COMP_TICKET))
            vRefTipComp = "TKB";


        log.debug("IMPRIMIR GUIA No : " + pNumComprobante);

        VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
        float subTotal = 0;
        float SumSubTotal = 0;
        float montoIGV = 0;
        double SumMontoIGV = 0;

        //ERIOS 2.4.3 margen derecho
        //int margen = 8;
        String vMargen = FarmaPRNUtility.llenarBlancos(margen);

        log.debug("vRutaImpresora : " + VariablesCaja.vRutaImpresora);
        log.debug("Ruta : " + pRuta);

        log.debug("VariablesCaja.vNumPedVta:" + VariablesCaja.vNumPedVta);

        try {
            //IMPRIME LA DIRRECION DEL LOCAL
            String lineaDirecMatriz = "";
            if (VariablesPtoVenta.vIndDirMatriz) {
                lineaDirecMatriz = FarmaPRNUtility.alinearIzquierda(VariablesPtoVenta.vDireccionMatriz, 90);
            }

            //DIRECCION LOCAL
            String lineaDirecLocal = "";
            if (VariablesPtoVenta.vIndDirLocal) {
                lineaDirecLocal =
                        FarmaPRNUtility.alinearIzquierda("NUEVA DIR: " + FarmaVariables.vDescCortaDirLocal, 69);
            }

            //FECHA DE EMISION
            String dia = pFechaBD.substring(0, 2);
            String mesLetra = FarmaUtility.devuelveMesEnLetras(Integer.parseInt(pFechaBD.substring(3, 5)));
            String ano = pFechaBD.substring(6, 10);
            String hora = pFechaBD.substring(11, 19);
            String lineaFecha = dia + " de " + mesLetra + " del " + ano + "     " + hora;

            //DATOS DESTINO Y COMPROBANTE
            String lineaDatosDestino =
                FarmaPRNUtility.llenarBlancos(15) + FarmaPRNUtility.alinearIzquierda(VariablesConvenioBTLMF.vCodConvenio +
                                                                                     "-" + pRazonSocial, 70) +
                FarmaPRNUtility.llenarBlancos(15);


            if (VariablesVentas.vTipoPedido.equalsIgnoreCase("03")) { //RHERRERA 26-06-2014
                /*1*/vPrint.add(vMargen + "");
                /*2*/vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(67) + lineaDirecLocal);
                /*3*/vPrint.add(vMargen + "");
                /*4*/vPrint.add(vMargen + lineaDirecMatriz);
                /*5*/vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(15) + lineaFecha);
                //TODO PUNTO PARTIDA
                /*6*/ //vPrint.add(vMargen+"");
                vPrint.add(vMargen + pDireccionDestino);
                /*7*/vPrint.add(vMargen + lineaDatosDestino + "No. " + pNumComprobante.substring(0, 3) + "-" +
                                pNumComprobante.substring(3, 10));
                //NOMBRE O RAZON SOCIAL
                /*8*/vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(22) + pRazonSocial);
                //DIRECCION  DEL PUNTO DE LLEGADA
                /*9*/
                     //KMONCADA 14.04.2015 NRO DE COMP.ELECTRONICO NO SE MOSTRABA COMPLETO
                 if(pNumCompCoPago.indexOf("-") != -1){
                     vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(26) +
                                     FarmaPRNUtility.alinearIzquierda(VariablesConvenioBTLMF.vPuntoLlegada, 60) +
                                     FarmaPRNUtility.llenarBlancos(20) + vRefTipComp + ":" +
                                     //pNumCompCoPago.substring(0, 3) + "-" + pNumCompCoPago.substring(3, 10));
                                     //pNumCompCoPago.substring(0, 4) + "-" + pNumCompCoPago.substring(4));
                                     pNumCompCoPago);
                 }else{
                     if(pNumCompCoPago.length()<9){
                        vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(26) +
                                     FarmaPRNUtility.alinearIzquierda(VariablesConvenioBTLMF.vPuntoLlegada, 60) +
                                     FarmaPRNUtility.llenarBlancos(20) + vRefTipComp + ":" +
                                     pNumCompCoPago.substring(0, 3) + "-" + pNumCompCoPago.substring(3, 10));
                                     //pNumCompCoPago.substring(0, 4) + "-" + pNumCompCoPago.substring(4));
                     }else{
                         vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(26) +
                                      FarmaPRNUtility.alinearIzquierda(VariablesConvenioBTLMF.vPuntoLlegada, 60) +
                                      FarmaPRNUtility.llenarBlancos(20) + vRefTipComp + ":" +
                                      pNumCompCoPago.substring(0, 4) + "-" + pNumCompCoPago.substring(4));
                     }
                 }
                //NUMERO DE RUC Y MOTIVO (TODO)
                /*10*/
                //RHERRERA
                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(10) + pNumeroRUC +
                           FarmaPRNUtility.llenarBlancos(40) +
                           FarmaPRNUtility.alinearIzquierda("OC: " + VariablesConvenioBTLMF.vNumOrdeCompra, 20));
                /*11*/vPrint.add(vMargen + "");
            } else { /// FLUJO NORMAL


                /*1*/vPrint.add(vMargen + "");
                /*2*/vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(67) + lineaDirecLocal);
                /*3*/vPrint.add(vMargen + "");
                /*4*/vPrint.add(vMargen + lineaDirecMatriz);
                /*5*/vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(15) + lineaFecha);
                //TODO PUNTO PARTIDA
                /*6*/vPrint.add(vMargen + "");
                /*7*/vPrint.add(vMargen + lineaDatosDestino + "No. " + pNumComprobante.substring(0, 3) + "-" +
                                pNumComprobante.substring(3, 10));
                //NOMBRE O RAZON SOCIAL
                /*8*/vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(22) + pRazonSocial);
                //DIRECCION  DEL PUNTO DE LLEGADA
                /*9*/vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(26) +
                                FarmaPRNUtility.alinearIzquierda(pDireccionDestino, 60));
                //NUMERO DE RUC Y MOTIVO (TODO)
                /*10*/
                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(10) + pNumeroRUC);
                /*11*/vPrint.add(vMargen + "");

            }
            if (VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL)) {
                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(10) +
                           FarmaPRNUtility.alinearIzquierda("CODIGO", 11) + FarmaPRNUtility.llenarBlancos(1) +
                           FarmaPRNUtility.alinearIzquierda("LAB", 7) + FarmaPRNUtility.llenarBlancos(1) +
                           FarmaPRNUtility.alinearIzquierda("PRODUCTO", 35) + FarmaPRNUtility.llenarBlancos(5) +
                           FarmaPRNUtility.alinearIzquierda("CANTIDAD", 10) + FarmaPRNUtility.llenarBlancos(20) +
                           FarmaPRNUtility.alinearDerecha("LOTE.", 15) + FarmaPRNUtility.llenarBlancos(1) +
                           FarmaPRNUtility.alinearDerecha("FCH VENCIMIET", 15));

            } else {
                /*12*/vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(10) +
                                 FarmaPRNUtility.alinearIzquierda("CODIGO", 15) + FarmaPRNUtility.llenarBlancos(5) +
                                 FarmaPRNUtility.alinearIzquierda("PRODUCTO", 35) + FarmaPRNUtility.llenarBlancos(5) +
                                 FarmaPRNUtility.alinearIzquierda("CANTIDAD", 10) + FarmaPRNUtility.llenarBlancos(20) +
                                 FarmaPRNUtility.alinearDerecha("P.UNITARIO", 15) + FarmaPRNUtility.llenarBlancos(1) +
                                 FarmaPRNUtility.alinearDerecha("P.SUBTOTAL", 15));
            }
            /*

                //JMIRANDA 22.08.2011 Cambio para verificar si imprime
                if(UtilityVentas.getIndImprimeCorrelativo())
                {   vPrint.add(FarmaPRNUtility.llenarBlancos(11) + pFechaBD + "   CORR." + VariablesCaja.vNumPedVta);
                }
                else
                {   vPrint.add(FarmaPRNUtility.llenarBlancos(11) + " " );
                }        */

            int linea = 0;

            //imprime el detalle de los productos del comprobante
            for (int i = 0; i < pDetalleComprobante.size(); i++) {
                String punitario = " ";
                String colSubTotal = " ";

                //RHERRERA. 26/06/2014.
                // VALIDACION PARA IMPRIMIR LOTE Y FECH VECIMIENTO.
                if (VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL)) {
                    colSubTotal =
                            ((String)((ArrayList)pDetalleComprobante.get(i)).get(20)).trim(); // hace referencia al FECHA VENCIMIENTO
                    punitario =
                            ((String)((ArrayList)pDetalleComprobante.get(i)).get(19)).trim(); // HACE REFERENCIA AL LOTE DEL PROD.
                } else { // PROCESO NORMAL
                    if (VariablesConvenioBTLMF.vFlgImprimeImportes.equals("1")) {
                        colSubTotal = (String)((ArrayList)pDetalleComprobante.get(i)).get(5);
                        punitario = (String)((ArrayList)pDetalleComprobante.get(i)).get(4).toString().trim();
                    }
                }
                String codigoProd = ((String)((ArrayList)pDetalleComprobante.get(i)).get(6)).trim();
                String descProd = ((String)((ArrayList)pDetalleComprobante.get(i)).get(15)).trim();
                String cantVenta = ((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim();
                String lab = "(" + ((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim() + ")";
                String precioUnit = punitario;
                String precioSubTotal = colSubTotal;

                String lineaDetalle;
                if (VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL)) {
                    lineaDetalle =
                            FarmaPRNUtility.llenarBlancos(10) + FarmaPRNUtility.alinearIzquierda(codigoProd, 11) +
                            FarmaPRNUtility.llenarBlancos(1) + FarmaPRNUtility.alinearIzquierda(lab, 7) +
                            FarmaPRNUtility.llenarBlancos(1) + FarmaPRNUtility.alinearIzquierda(descProd, 35) +
                            FarmaPRNUtility.llenarBlancos(5) + FarmaPRNUtility.alinearIzquierda(cantVenta, 10) +
                            FarmaPRNUtility.llenarBlancos(20) + FarmaPRNUtility.alinearDerecha(precioUnit, 15) +
                            FarmaPRNUtility.llenarBlancos(1) + FarmaPRNUtility.alinearDerecha(precioSubTotal, 15);
                } else {
                    lineaDetalle =
                            FarmaPRNUtility.llenarBlancos(10) + FarmaPRNUtility.alinearIzquierda(codigoProd, 15) +
                            FarmaPRNUtility.llenarBlancos(5) + FarmaPRNUtility.alinearIzquierda(descProd, 35) +
                            FarmaPRNUtility.llenarBlancos(5) + FarmaPRNUtility.alinearIzquierda(cantVenta, 10) +
                            FarmaPRNUtility.llenarBlancos(20) + FarmaPRNUtility.alinearDerecha(precioUnit, 15) +
                            FarmaPRNUtility.llenarBlancos(1) + FarmaPRNUtility.alinearDerecha(precioSubTotal, 15);
                }
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

            //Detalle guias por convenio
            for (int j = linea; j < 5; j++) {
                vPrint.add(vMargen + " ");
                linea += 1;
            }


            //*************************************INFORMACION DEL CONVENIO***********************************************//

            double porcCopagoBenef = 0;
            double porcCopagoEmpresa = 0;
            double ValCopagoCompPagoSinIGV = 0;

            porcCopagoBenef = FarmaUtility.getDecimalNumber(vPrctBeneficiario);
            porcCopagoEmpresa = FarmaUtility.getDecimalNumber(vPrctEmpresa);

            double pValTotalNetoRedondeo =
                FarmaUtility.getDecimalNumber(pValTotalNeto) + FarmaUtility.getDecimalNumber(pValRedondeoComPago);
            /*if(pTipoClienteConvenio.equals("1")){
                    ValCopagoCompPagoSinIGV  =  ((SumSubTotal*porcCopagoEmpresa)/100);
               }else{
                   ValCopagoCompPagoSinIGV  =  ((SumSubTotal*porcCopagoBenef)/100);
               }               */

            ValCopagoCompPagoSinIGV =
                    FarmaUtility.getDecimalNumber(pValCopagoCompPago) - FarmaUtility.getDecimalNumber(pValIgvComCoPago);

            //SumMontoIGV = SumMontoIGV-  ValCopagoCompPagoSinIGV;
            double neto = SumSubTotal - ValCopagoCompPagoSinIGV;
            //SumMontoIGV = pValTotalNetoRedondeo -  neto;
            /*
                String vRefTipComp = "";

                if (pRefTipComp.equals(ConstantsVentas.TIPO_COMP_BOLETA)) vRefTipComp = "BOL";
                if (pRefTipComp.equals(ConstantsVentas.TIPO_COMP_FACTURA)) vRefTipComp = "FAC";
                if (pRefTipComp.equals(ConstantsVentas.TIPO_COMP_GUIA)) vRefTipComp = "GUIA";
                if (pRefTipComp.equals(ConstantsVentas.TIPO_COMP_TICKET)) vRefTipComp = "TKB";
*/
            if (VariablesConvenioBTLMF.vFlgImprimeImportes.equals("1")) {

                pValTotalNetoRedondeo =
                        FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(pValTotalNetoRedondeo, 2));

                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(116) +
                           FarmaPRNUtility.alinearDerecha("---------------", 15));
                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(100) + "Sub Total   S/. " +
                           FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(SumSubTotal), 15));
                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(100) + "Coaseguro   S/. " +
                           FarmaPRNUtility.alinearDerecha("-" + FarmaUtility.formatNumber(ValCopagoCompPagoSinIGV),
                                                          15));
                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(100) + "SUB TOTAL   S/. " +
                           FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(neto), 15));
                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(100) + "     IGV    S/. " +
                           FarmaPRNUtility.alinearDerecha(pValIgvComPago, 15));
                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(116) +
                           FarmaPRNUtility.alinearDerecha("---------------", 15));
                vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(100) + "   TOTAL    S/. " +
                           FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(pValTotalNetoRedondeo), 15));
            } else {
                for (int j = linea; j < 5 + 7; j++) {
                    vPrint.add(vMargen + " ");
                    linea += 1;
                }
            }

            String lineaInstitucion = "";
            String lineaConvenio = "";
            String lineaBeneficiario = "";
            String lineaReferencia = "";

            lineaInstitucion = "  I N S T I T U C I O N: " + VariablesConvenioBTLMF.vInstitucion.trim().toUpperCase();
            lineaConvenio = "  Convenio: " + VariablesConvenioBTLMF.vNomConvenio.trim().toUpperCase();
            lineaBeneficiario = muestraBeneficiario();

            //FLG_TIPO_CONVENIO: 1=Credito; 2=Descuento
            //COD_TIPO_CONVENIO: 1=Copago; 3=Desc. Planilla
            if (pCodTipoConvenio.equals("1") || pCodTipoConvenio.equals("3")) {
                if (!vRefTipComp.equals("")) {
                    lineaInstitucion =
                            lineaInstitucion + "  (" + FarmaUtility.formatNumber(porcCopagoEmpresa, "") + "%)";
                }
                if (vRefTipComp != null && !vRefTipComp.trim().equals("")) {
                    //ERIOS 2.4.3 Siempre imprime monto de comp. referencia
                    //if (VariablesConvenioBTLMF.vFlgImprimeImportes.equals("1")){

                    lineaReferencia =
                            "  #REF: " + vRefTipComp + " " + FarmaPRNUtility.alinearIzquierda(pNumCompCoPago + " (" +
                                                                                              FarmaUtility.formatNumber(porcCopagoEmpresa,
                                                                                                                        "") +
                                                                                              "%)" + " - " + "S/." +
                                                                                              pValCopagoCompPago, 65) +
                            " ";
                    //}else{
                    //    lineaReferencia = "  #REF: "+vRefTipComp+" " + FarmaPRNUtility.alinearIzquierda(pNumCompCoPago,65);
                    //}
                }
            }

            //RHERRERA. 26/06/2014
            // IMPRESION VENTA EMPRESA, SIN DETALLE DE CONVENIO

            if (VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL)) {

                for (int j = linea; j < 5 + 6 + 5; j++) {
                    vPrint.add(vMargen + " ");
                    linea += 1;
                }
                linea = linea - 17;
            } else { //------ SINO ES ASI EL PROCESO NORMAL

                vPrint.add(vMargen + lineaInstitucion);
                vPrint.add(vMargen + lineaConvenio);
                vPrint.add(vMargen + lineaBeneficiario);
                vPrint.add(vMargen + lineaReferencia);

                //se imprime la cabecera de la infomación del convenio

                vPrint.add(vMargen + "  ");
                /*vPrint.add(" REDO: " + pValRedondeoComPago +
                                " CAJERO: " + VariablesCaja.vNomCajeroImpreso + " " + VariablesCaja.vApePatCajeroImpreso + " " +
                                " CAJA: " + VariablesCaja.vNumCajaImpreso +
                                " TURNO: " + VariablesCaja.vNumTurnoCajaImpreso +
                                " VEND: " + VariablesCaja.vNomVendedorImpreso + " " +VariablesCaja.vApePatVendedorImpreso  );*/


                if (pImprDatAdic.equals("1")) {
                    linea = imprimeDatosAdicionales(vPrint, vMargen, ConstantsVentas.TIPO_COMP_GUIA);
                }

            } //--------


            //log.debug("Nro de Lineas::"+vPrint.getActualLine());
            for (int i = linea; i <= 6; i++) {
                vPrint.add(vMargen + " ");
            }

            /*vPrint.add(" SON:" + FarmaPRNUtility.alinearIzquierda(FarmaPRNUtility.montoEnLetras(pValTotalNetoRedondeo).trim(),65) + "" +
                                "  Coaseguro("+FarmaUtility.formatNumber(porcCopago,0)+"%)" +
                                "    S/. " + FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(ValCopagoCompPagoSinIGV),10)+"");*/
            //vPrint.add(vMargen+" ");
            vPrint.add(vMargen + "      " +
                       VariablesVentas.vMensajeBotiquinBTLVtaInstitucional); //kmoncada 10.07.14 impresion de poliza en vta empresa
            vPrint.add(vMargen + " ");
            vPrint.add(vMargen + " ");
            vPrint.add(vMargen + " ");
            vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(134) + "X");
            vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(105) + "Vta. Sujeta a Confirmacion");
            vPrint.add(vMargen + " ");
            //vPrint.add(vMargen+" ");
            vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(100) + VariablesPtoVenta.vVersion);

            log.info("Fin al imprimir la GUIA: " + pNumComprobante);
            VariablesCaja.vEstadoSinComprobanteImpreso = "N";


        } catch (Exception e) {
            log.error("", e);
            VariablesCaja.vEstadoSinComprobanteImpreso = "S";
            log.info("**** Fecha :" + pFechaBD);
            log.info("**** CORR :" + VariablesCaja.vNumPedVta);
            log.info("**** NUMERO COMPROBANTE :" + pNumComprobante);
            log.info("**** IP :" + FarmaVariables.vIpPc);
            log.info("Error al imprimir la Guia: " + e);
            //JMIRANDA 23/07/09 Envia Error al Imprimir a Email
            //enviaErrorCorreoPorDB(e.toString(),VariablesCaja.vNumPedVta);
        }

        //Fin
        return vPrint;
    }

    public ArrayList<String> formatoFacturaBTL(JDialog pJDialog, ArrayList pDetalleComprobante, String pValTotalNeto,
                                               String pNumComprobante, String pValIgvComPago,
                                               String pValCopagoCompPago, String pPorcIgv, String pNumCompCoPago,
                                               String pValTotalAhorro, boolean vEsPedidoConvenio, String pImprDatAdic,
                                               String pTipoClienteConvenio, String pCodTipoConvenio, String pFechaBD,
                                               String pRefTipComp, String pValRedondeoComPago, String pRazonSocial,
                                               String pDireccionDestino, String pNumeroRUC, String vPrctBeneficiario,
                                               String vPrctEmpresa, int margen) {
        //Corresponde a: formatoFacturaC
        ArrayList<String> vPrint = new ArrayList<>();

        /***Declaracion de varibles***/
        String indProdVirtual = "";
        int nroArticulos = 0;
        //ERIOS 2.4.3 margen derecho
        //int margen = 8;
        String vMargen = FarmaPRNUtility.llenarBlancos(margen);

        float subTotal = 0;
        float montoIGV = 0;
        float SumSubTotal = 0;
        double SumMontoIGV = 0;
        String pNomImpreso = pRazonSocial;
        String pDirImpreso = pDireccionDestino;
        String pNumDocImpreso = pNumeroRUC;
        VariablesCaja.vIndPedidoConProdVirtualImpresion = false;

        /////////////////////////////////////////////////////////////////////////////
        String vRefTipComp = "";
        if (pRefTipComp.equals(ConstantsVentas.TIPO_COMP_BOLETA))
            vRefTipComp = "BOL";
        if (pRefTipComp.equals(ConstantsVentas.TIPO_COMP_FACTURA))
            vRefTipComp = "FAC";
        if (pRefTipComp.equals(ConstantsVentas.TIPO_COMP_GUIA))
            vRefTipComp = "GUIA";
        if (pRefTipComp.equals(ConstantsVentas.TIPO_COMP_TICKET))
            vRefTipComp = "TKB";

        log.debug("Imprimiendo Numero de Comprobante: " + pNumComprobante);

        try {
            //Obteniendo datos de la fecha
            String dia = pFechaBD.substring(0, 2);
            String mesLetra = FarmaUtility.devuelveMesEnLetras(Integer.parseInt(pFechaBD.substring(3, 5)));
            String ano = pFechaBD.substring(6, 10);
            String hora = pFechaBD.substring(11, 19);


            vPrint.add(vMargen + " ");
            //DIRECCION LOCAL
            String lineaDirecLocal = "";
            if (VariablesPtoVenta.vIndDirLocal) {
                lineaDirecLocal =
                        FarmaPRNUtility.alinearIzquierda("NUEVA DIR: " + FarmaVariables.vDescCortaDirLocal, 42);
            }
            vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(50) + lineaDirecLocal);

            vPrint.add(vMargen + " ");

            //ESPACIOS EN BLANCO
            vPrint.add(vMargen + " ");

            vPrint.add(vMargen + " ");

            //IMPRIME LA DIRRECION DE MATRIZ
            String lineaDirecMatriz = "";
            if (VariablesPtoVenta.vIndDirMatriz) {
                lineaDirecMatriz =
                        FarmaPRNUtility.llenarBlancos(20) + FarmaPRNUtility.alinearIzquierda(VariablesPtoVenta.vDireccionMatriz,
                                                                                             69);
            }
            vPrint.add(vMargen + lineaDirecMatriz);

            //FECHA
            vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(15) + dia + " de " + mesLetra + " del " + ano +
                       "     " + hora);

            //SENORES Y NUMERO DE DOCUMENTO
            vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(15) +
                       FarmaPRNUtility.alinearIzquierda(pNomImpreso.trim(), 60) + FarmaPRNUtility.llenarBlancos(10) +
                       "No. " + pNumComprobante.substring(0, 3) + "-" + pNumComprobante.substring(3, 10));

            String vOC = "";
            if (VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL)) {
                vOC = "OC: " + VariablesConvenioBTLMF.vNumOrdeCompra;
            }
            //RUC Y OC
            vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(15) + pNumDocImpreso.trim() +
                       FarmaPRNUtility.alinearIzquierda("", 60) + FarmaPRNUtility.llenarBlancos(15) + vOC);

            //DIRRECION  Y NUMERO DE GUIA DE REMISION
            String vGuia = "";
            if (pNumCompCoPago != null && !pNumCompCoPago.equals("") &&
                pRefTipComp.equals(ConstantsPtoVenta.TIP_COMP_GUIA)) {
                vGuia = vRefTipComp + ":" + pNumCompCoPago.substring(0, 3) + "-" + pNumCompCoPago.substring(3, 10);
            }
            vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(15) +
                       FarmaPRNUtility.alinearIzquierda(pDirImpreso.trim(), 60) + FarmaPRNUtility.llenarBlancos(26) +
                       vGuia);

            // ESPACIOS ENTRE LA CABECERA Y EL DETALLE DE LA FACTURA
            vPrint.add(vMargen + " ");

            vPrint.add(vMargen + " ");


            int linea = 0;

            for (int i = 0; i < pDetalleComprobante.size(); i++) {
                nroArticulos++;

                String lab = "(" + ((String)((ArrayList)pDetalleComprobante.get(i)).get(3)).trim() + ")";

                if (VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL)) {

                    vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(3) +
                               FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim(),
                                                                6) + FarmaPRNUtility.llenarBlancos(5) +
                               FarmaPRNUtility.alinearIzquierda(lab + " " +
                                                                ((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim() +
                                                                " " +
                                                                ((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),
                                                                60) + FarmaPRNUtility.llenarBlancos(9) +
                               FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(4)).trim(),
                                                              11) + FarmaPRNUtility.llenarBlancos(8) +
                               FarmaPRNUtility.llenarBlancos(14) +
                               FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),
                                                              10));

                } else {

                    vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(3) +
                               FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(0)).trim(),
                                                                6) + FarmaPRNUtility.llenarBlancos(5) +
                               FarmaPRNUtility.alinearIzquierda(((String)((ArrayList)pDetalleComprobante.get(i)).get(1)).trim() +
                                                                " " +
                                                                ((String)((ArrayList)pDetalleComprobante.get(i)).get(2)).trim(),
                                                                60) + FarmaPRNUtility.llenarBlancos(9) +
                               FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(4)).trim(),
                                                              11) + FarmaPRNUtility.llenarBlancos(8) +
                               FarmaPRNUtility.llenarBlancos(14) +
                               FarmaPRNUtility.alinearDerecha(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim(),
                                                              10));
                }
                linea += 1;

                indProdVirtual = FarmaUtility.getValueFieldArrayList(pDetalleComprobante, i, 8);
                //verifica que solo se imprima un producto virtual en el comprobante
                if (i == 0 && indProdVirtual.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                    VariablesCaja.vIndPedidoConProdVirtualImpresion = true;
                } else {
                    VariablesCaja.vIndPedidoConProdVirtualImpresion = false;
                }

                if (vEsPedidoConvenio) {
                    subTotal =
                            new Double(FarmaUtility.getDecimalNumber(((String)((ArrayList)pDetalleComprobante.get(i)).get(5)).trim())).floatValue();
                    montoIGV =
                            new Double(FarmaUtility.getDecimalNumber(((String)((ArrayList)pDetalleComprobante.get(i)).get(18)).trim())).floatValue();
                    SumMontoIGV = SumMontoIGV + montoIGV;
                    SumSubTotal = SumSubTotal + subTotal;
                }
            }

            if (VariablesCaja.vIndPedidoConProdVirtualImpresion) {
                vPrint.add(vMargen + " ");

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

            //CIERRE DEL FOR DE DETALLE
            for (int j = linea; j < 5; j++) {
                vPrint.add(vMargen + " ");
                linea++;
            }

            double pValTotalNetoRedondeo = 0;
            double neto = 0;

            //*************************************INFORMACION DEL CONVENIO***********************************************//
            if (vEsPedidoConvenio) {
                double porcCopagoBenef = FarmaUtility.getDecimalNumber(vPrctBeneficiario);
                double porcCopagoEmpresa = FarmaUtility.getDecimalNumber(vPrctEmpresa);

                double ValCopagoCompPagoSinIGV = ((SumSubTotal * porcCopagoBenef) / 100);

                if (VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL)) { //RHERRERA 26-06-2014
                    vPrint.add(vMargen);
                } else {
                    vPrint.add(vMargen + "      " + FarmaPRNUtility.alinearIzquierda(" ", 85) + "        " +
                               "    Sub Total   S/. " +
                               FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(SumSubTotal), 10));
                }

                double dblTotalNeto = FarmaUtility.getDecimalNumber(pValTotalNeto);
                double dblRedondeoComPago = FarmaUtility.getDecimalNumber(pValRedondeoComPago);

                pValTotalNetoRedondeo = dblTotalNeto + dblRedondeoComPago;
                //CHUANES 12.03.2014 SE PONE EL FORMATO DE 2 DECIMALES AL VALOR REDONDEADO
                pValTotalNetoRedondeo = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(pValTotalNetoRedondeo, 2));
                SumMontoIGV = FarmaUtility.getDecimalNumber(pValIgvComPago);
                if (VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL)) {
                    //RHERRERA 04-08-2014
                    neto = (dblTotalNeto + dblRedondeoComPago) - SumMontoIGV ; 
                } else {
                    //ERIOS 03.03.2015 El modifica el calculo de subtotal
                    neto = pValTotalNetoRedondeo - SumMontoIGV;
                }

                String vLinea = "";

                if (VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL)) { //RHERRERA 26-06-2014
                    //10
                    for (int j = linea; j < 10; j++) {
                        vPrint.add(vMargen + " ");
                        linea++;
                    }
                    linea = linea - 10;

                } else {

                    //PROCESO NORMAL...26062014
                    String lineaInstitucion = "";
                    String lineaConvenio = "";
                    String lineaBeneficiario = "";
                    String lineaReferencia = "";

                    lineaInstitucion =
                            "  I N S T I T U C I O N: " + VariablesConvenioBTLMF.vInstitucion.trim().toUpperCase();
                    lineaConvenio = "  Convenio: " + VariablesConvenioBTLMF.vNomConvenio.trim().toUpperCase();
                    lineaBeneficiario = muestraBeneficiario();

                    if (pCodTipoConvenio.equals("1") || pCodTipoConvenio.equals("3")) {
                        vLinea =
                                FarmaPRNUtility.llenarBlancos(85) + "            " + "Coaseguro(" + FarmaUtility.formatNumber(porcCopagoBenef,
                                                                                                                              2) +
                                "%)    S/. " +
                                FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(ValCopagoCompPagoSinIGV), 10);
                        vPrint.add(vMargen + vLinea);

                        String vPorcCopago = "";
                        if (VariablesConvenioBTLMF.vFlgImprimeImportes.equals("1")) {
                            vPorcCopago =
                                    "(" + FarmaUtility.formatNumber(porcCopagoBenef, "") + "%)" + " - " + "S/." + pValCopagoCompPago;
                        }
                        lineaReferencia =
                                "  #REF: " + vRefTipComp + " " + FarmaPRNUtility.alinearIzquierda(pNumCompCoPago +
                                                                                                  vPorcCopago, 50);

                    } else {
                        vLinea = FarmaPRNUtility.llenarBlancos(85) + "            ";
                        vPrint.add(vMargen + vLinea);
                    }

                    lineaInstitucion =
                            lineaInstitucion + "  (" + FarmaUtility.formatNumber(porcCopagoEmpresa, "") + "%)";

                    vPrint.add(vMargen + lineaInstitucion);
                    vPrint.add(vMargen + lineaConvenio);
                    //vPrint.add(vMargen+lineaBeneficiario);
                    vPrint.add(vMargen + lineaReferencia + " " + lineaBeneficiario);

                    if (pImprDatAdic.equals("1")) {
                        linea = imprimeDatosAdicionales(vPrint, vMargen, ConstantsVentas.TIPO_COMP_FACTURA);
                    }

                    for (int j = linea; j < 3; j++)
                        vPrint.add(vMargen + " ");

                } //FIN DEL PROCESO VALIDACION RHERRERA 26062014
            } else {
                for (int j = linea; j < 5 + 5; j++) {
                    vPrint.add(vMargen + " ");
                    linea++;
                }
                //Ahorro
                double auxTotalDcto = FarmaUtility.getDecimalNumber(pValTotalAhorro);
                if (auxTotalDcto > 0) {
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

                /****DISTRIBUICION GRATUITA****/
                if (VariablesCaja.vIndDistrGratuita.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                    vPrint.add(vMargen + FarmaPRNUtility.alinearIzquierda(" - DISTRIBUCION GRATUITA - ", 60));

                }
                if (VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_MESON) ||
                    VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL)) {
                    VariablesVentas.vTituloDelivery = "";
                } else {
                    VariablesVentas.vTituloDelivery =
                            ""; //" - PEDIDO DELIVERY - " ; //ERIOS 2.4.7 No se imprime referencia a Delivery
                }

                String datoAdic =
                    " REDO:" + pValRedondeoComPago + " CAJERO:" + VariablesCaja.vNomCajeroImpreso + " " + VariablesCaja.vApePatCajeroImpreso +
                    " " + " CAJA:" + VariablesCaja.vNumCajaImpreso + " TURNO:" + VariablesCaja.vNumTurnoCajaImpreso +
                    " VEND:" + VariablesCaja.vNomVendedorImpreso + " " + VariablesCaja.vApePatVendedorImpreso;
                datoAdic = FarmaPRNUtility.alinearIzquierda(datoAdic, 85);
                vPrint.add(vMargen + datoAdic);

                String vLinea;
                vLinea =
                        " Forma(s) de pago: " + VariablesCaja.vFormasPagoImpresion + FarmaPRNUtility.llenarBlancos(11) +
                        VariablesVentas.vTituloDelivery;
                vLinea = FarmaPRNUtility.alinearIzquierda(vLinea, 135);
                if ( //VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_MESON) ||
                    !VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL)) {
                    vPrint.add(vMargen + vLinea);
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
                } else {
                    //vPrint.add(vMargen+" ");
                }

                pValTotalNetoRedondeo = FarmaUtility.getDecimalNumber(pValTotalNeto);
                SumMontoIGV = FarmaUtility.getDecimalNumber(pValIgvComPago);
                neto = pValTotalNetoRedondeo - SumMontoIGV;
            }

            // KMONCADA 30.06.2014 IMPRESION DE POLIZA
            if ( //VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_MESON) ||
                VariablesVentas.vTipoPedido.equalsIgnoreCase(ConstantsVentas.TIPO_PEDIDO_INSTITUCIONAL)) {
                String redondeo = " REDO:" + pValRedondeoComPago;
                vPrint.add(redondeo + ".  " + VariablesVentas.vMensajeBotiquinBTLVtaInstitucional);
                vPrint.add(vMargen);
            }

            /*****IMPRIME EL MONTO TOTAL EL LETRAS******/
            String vLinea =
                " SON: " + FarmaPRNUtility.alinearIzquierda(FarmaPRNUtility.montoEnLetras(pValTotalNetoRedondeo), 85);
            vPrint.add(vMargen + vLinea);


            /*******IMPRIME IGV Y MONTO NETO*******/
            vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(15) + FarmaPRNUtility.llenarBlancos(15) +
                       FarmaPRNUtility.llenarBlancos(40) + "%   " + FarmaPRNUtility.alinearDerecha(pPorcIgv, 10));
            vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(15) +
                       FarmaPRNUtility.alinearDerecha("S/. " + FarmaUtility.formatNumber(neto), 15) +
                       FarmaPRNUtility.llenarBlancos(40) + "S/. " +
                       FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(SumMontoIGV), 10) +
                       FarmaPRNUtility.llenarBlancos(35) + "S/. " +
                       FarmaPRNUtility.alinearDerecha(FarmaUtility.formatNumber(pValTotalNetoRedondeo), 10));

            vPrint.add(vMargen + " ");
            vPrint.add(vMargen + FarmaPRNUtility.llenarBlancos(100) + VariablesPtoVenta.vVersion);


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


        //Fin
        return vPrint;
    }

    public static void main(String[] args) {
        VariablesPtoVenta.vIndDirMatriz = true;
        VariablesPtoVenta.vDireccionCortaMatriz = "Cal. V.Alzamora 147 Urb Sta Catalina,     La Victoria, Lima";
        VariablesPtoVenta.vIndDirLocal = true;
        FarmaVariables.vDescCortaDirLocal = "BTL019 Jesus Maria, Lima Peru";

        VariablesConvenioBTLMF.vInstitucion = "EDGAR RIOS NAVARRO";
        VariablesConvenioBTLMF.vDireccion = "Av. Union MZ L Lt 39, Chaclacayo";
        VariablesConvenioBTLMF.vRuc = "10418540341";

        VariablesConvenioBTLMF.vFlgImprimeImportes = "1";
        ArrayList pDetalleComprobante = new ArrayList();
        ArrayList pDetalle = new ArrayList();
        pDetalle.add(0, "5");
        pDetalle.add(1, "");
        pDetalle.add(2, "");
        pDetalle.add(3, "");
        pDetalle.add(4, "10.00");
        pDetalle.add(5, "50.00");
        pDetalle.add(6, "854855");
        pDetalle.add(7, "");
        pDetalle.add(8, "");
        pDetalle.add(9, "");
        pDetalle.add(10, "");
        pDetalle.add(11, "");
        pDetalle.add(12, "");
        pDetalle.add(13, "");
        pDetalle.add(14, "");
        pDetalle.add(15, "PRODUCTO ABD / TABLETA 10MG");
        pDetalle.add(16, "");
        pDetalle.add(17, "");
        pDetalle.add(18, "1.80");
        pDetalleComprobante.add(pDetalle);

        VariablesPtoVenta.vVersion = "v2.4.3";

        FormatoImpresion formatoImpresion = new FormatoImpresion();
        Impresora impresora = new Impresora();
        VariablesPtoVenta.vDireccionMatriz =
                "Calle Victor Alzamora 1340, Urb. la anpilla , La votria lima, peru tierra solas";
        ArrayList<String> vPrint =
            formatoImpresion.formatoGuiaBTL(null, pDetalleComprobante, "100.00", "5061233456", "18.00", "0.00", "XXX",
                                            "num", "C:\\RUTA.TXT", false, "1", "CLI", "04", "10/01/2010 12:09:23",
                                            "BOL", "0.05", VariablesConvenioBTLMF.vInstitucion.trim(),
                                            VariablesConvenioBTLMF.vDireccion.trim(),
                                            VariablesConvenioBTLMF.vRuc.trim(), "0", "100", 0);
        impresora.imprimir(vPrint, "\\\\10.18.1.179\\GUIAS", false, "", "", "", "", 66);
        //impresora.imprimir(vPrint,"C:\\GUIAS.TXT");
    }

    private int imprimeDatosAdicionales(ArrayList<String> vPrint, String vMargen, String pTipoComp) {
        int linea = 0;
        if (VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic != null &&
            VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.size() > 0) {

            String lineaInfAdic = "";
            String temp = "";
            boolean primeraLinea = true;
            for (int j = 0; j < VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.size(); j++) {
                Map datosAdicConv = (Map)VariablesConvenioBTLMF.vArrayList_DatosConvenioAdic.get(j);

                String pNombCampo = (String)datosAdicConv.get("NOMBRE_CAMPO");
                String pDesCampo = (String)datosAdicConv.get("DESCRIPCION_CAMPO");
                String vFlgImprime = (String)datosAdicConv.get("FLG_IMPRIME");
                //String vCodCampo    = (String)datosAdicConv.get("COD_CAMPO");
                if (pTipoComp.equals(ConstantsVentas.TIPO_COMP_GUIA)) {
                    vFlgImprime = "1";
                }
                if (vFlgImprime.equals("1") && pNombCampo != null && pDesCampo != null) {
                    if (primeraLinea) {
                        vPrint.add(vMargen + "  Datos Adicionales");
                        linea++;
                        primeraLinea = false;
                    }
                    //se imprimen dos informaciones en una linea
                    temp = FarmaPRNUtility.alinearIzquierda("  - " + pNombCampo + ": " + pDesCampo, 60);

                    if ("".equalsIgnoreCase(lineaInfAdic)) { //si no existe linea, se coloca esta
                        lineaInfAdic = temp;
                    } else { //si existe una linea, se coloca la siguiente anexa, se imprime y luego se resetea
                        lineaInfAdic = lineaInfAdic + temp;

                        vPrint.add(vMargen + lineaInfAdic);
                        linea++;
                        lineaInfAdic = "";
                    }
                }

            }
            //si al terminar de imprimir quedaron datos, imprimir los mismo
            if (!"".equalsIgnoreCase(lineaInfAdic)) {
                vPrint.add(vMargen + lineaInfAdic);
                linea++;
                lineaInfAdic = "";
            }

        }
        return linea;
    }

    /**
     * Muestra nombre de beneficiario
     * @author ERIOS
     * @since 2.4.7
     * @return
     */
    public static String muestraBeneficiario() {
        String lineaBeneficiario;
        lineaBeneficiario = "  Beneficiario: " + VariablesConvenioBTLMF.vNomCliente;
        if (VariablesConvenioBTLMF.vNomCliente.trim().equals("")) {
            if (VariablesConvenioBTLMF.vNomClienteDigitado.trim().equals("")) {
                //lineaBeneficiario = "  Beneficiario: " +"Conv. no cuenta con Beneficiario";
                lineaBeneficiario = "  Beneficiario: " + "*";
            } else {
                lineaBeneficiario = "  Beneficiario: " + VariablesConvenioBTLMF.vNomClienteDigitado;
            }
        }
        return lineaBeneficiario;
    }
}
