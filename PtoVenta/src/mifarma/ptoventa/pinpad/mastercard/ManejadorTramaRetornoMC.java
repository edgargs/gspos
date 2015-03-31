package mifarma.ptoventa.pinpad.mastercard;

import java.io.File;

import java.lang.reflect.Field;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.pinpad.reference.DBPinpad;
import mifarma.ptoventa.pinpad.reference.HiloImpresion;
import mifarma.ptoventa.pinpad.visa.VariablesPinpad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.com.hiper.hcomhepsJava.HcomHepsJava;


public class ManejadorTramaRetornoMC {
    private static final Logger log = LoggerFactory.getLogger(ManejadorTramaRetornoMC.class);
    private String nroTarjetaBruto;
    private String lblDatoPedidoEnv = "";

    public static boolean imprVoucher(String trama) {
        log.debug("MC- ingreso a imprimir voucher");
        String[] segmentos = trama.split(String.valueOf((char)0x0D) + ((char)0x0A));
        List lstVoucher = new ArrayList();
        StringBuffer textoImpr = new StringBuffer();
        int cantImpr = 0;
        log.debug("MC-Ingresa a un FOR con cantidad de repeticiones:   " + segmentos.length);
        for (int i = 0; i < segmentos.length; i++) {
            String seg = segmentos[i];
            if (seg.length() > 0) { //String inicial = seg.substring(0, 1);
                String temp = "";
                //imprime con el tipo de letra A
                if (seg.startsWith("A")) {
                    temp = seg.substring(1, seg.length());

                    Map<String, String> map = new HashMap<String, String>();
                    map.put("VALOR", temp);
                    map.put("TAMANIO", "0");
                    map.put("ALINEACION", "I");
                    map.put("BOLD", "N");
                    map.put("AJUSTE", "S");
                    lstVoucher.add(map);

                    temp = temp.replaceAll(" ", "&nbsp;");
                    textoImpr.append("<div class=\"tipoLetraA\">" + temp + " </div>");
                }
                //imprime con el tipo de letra B
                else if (seg.startsWith("B")) {
                    temp = seg.substring(1, seg.length());
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("VALOR", temp);
                    map.put("TAMANIO", "0");
                    map.put("ALINEACION", "I");
                    map.put("BOLD", "S");
                    map.put("AJUSTE", "S");
                    lstVoucher.add(map);
                    temp = temp.replaceAll(" ", "&nbsp;");
                    textoImpr.append("<div class=\"tipoLetraB\">" + temp + "</div>");
                }
                //imprime con el tipo de letra A Inv
                else if (seg.startsWith("C")) {
                    temp = seg.substring(1, seg.length());
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("VALOR", temp);
                    map.put("TAMANIO", "0");
                    map.put("ALINEACION", "I");
                    map.put("BOLD", "N");
                    map.put("AJUSTE", "S");
                    temp = temp.replaceAll(" ", "&nbsp;");
                    textoImpr.append("<div class=\"tipoLetraAInv\">" + temp + "</div>");
                }
                //imprime con el tipo de letra B Iv
                else if (seg.startsWith("D")) {
                    temp = seg.substring(1, seg.length());
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("VALOR", temp);
                    map.put("TAMANIO", "0");
                    map.put("ALINEACION", "I");
                    map.put("BOLD", "S");
                    map.put("AJUSTE", "S");
                    lstVoucher.add(map);
                    temp = temp.replaceAll(" ", "&nbsp;");
                    textoImpr.append("<div class=\"tipoLetraBInv\">" + temp + "</div>");
                }
                //imprime salto de linea
                else if (seg.startsWith(String.valueOf((char)0x0E))) {
                    temp = seg.replaceAll(String.valueOf((char)0x0E), "");
                    StringBuffer temp2 = new StringBuffer();
                    //si se repite algun caracter
                    if (temp.length() > 0) {
                        if (temp.startsWith(String.valueOf((char)0x1B))) {
                            temp = temp.substring(4, temp.length());
                            if (temp.length() > 0) {
                                String repetir = temp.substring(0, 1);
                                for (int j = 0; j < 38; j++) {
                                    temp2.append(repetir);
                                }
                            }
                        }

                        Map<String, String> map = new HashMap<String, String>();
                        map.put("VALOR", temp);
                        map.put("TAMANIO", "0");
                        map.put("ALINEACION", "I");
                        map.put("BOLD", "N");
                        map.put("AJUSTE", "S");
                        lstVoucher.add(map);

                        temp = temp2.toString().replaceAll(" ", "&nbsp;");
                        textoImpr.append("<div class=\"tipoLetraA\">" + temp + "</div>");
                    }
                    if (cantImpr == 0) {
                        StringBuffer temp3 = new StringBuffer();
                        temp3.append(DBPinpad.obtenerCabeceraVoucher("2")); //2:OPERADOR_MC
                        temp3.append(textoImpr);
                        textoImpr = temp3;
                        List lstAux = DBPinpad.lstObtenerCabVoucher("2");
                        if (lstAux != null) {
                            for (int k = 0; k < lstAux.size(); k++) {
                                lstVoucher.add(k, lstAux.get(k));
                            }
                        }

                    }
                    imprimir(lstVoucher);
                    //imprimir(textoImpr.toString());
                    cantImpr++;
                    textoImpr = new StringBuffer();
                    lstVoucher = new ArrayList();
                } else {

                    Map<String, String> map = new HashMap<String, String>();
                    map.put("VALOR", seg);
                    map.put("TAMANIO", "0");
                    map.put("ALINEACION", "I");
                    map.put("BOLD", "N");
                    map.put("AJUSTE", "S");
                    lstVoucher.add(map);

                    textoImpr.append("<div class=\"tipoLetraA\">" + seg + "</div>");
                }
            }
        }
        if (lstVoucher.size() > 0) {
            imprimir(lstVoucher);
        }
        /*if(!"".equals(textoImpr.toString())){
            imprimir(textoImpr.toString());
        }*/
        return true;
    }

    static private void imprimir(List lstTrama) {
        HiloImpresion hilo = new HiloImpresion();
        hilo.lstimpresion = lstTrama;
        hilo.start();
    }

    static private void imprimir(String texto) {
        StringBuffer textoImpr = new StringBuffer();
        textoImpr.append("<html>\n" +
                "<head>\n" +
                "	<style>\n" +
                "		.tipoLetraA\n" +
                "		{	color: black;\n" +
                "			font: 10px Consolas;\n" +
                "                   width: 240px;\n" +
                "		}\n" +
                "		.tipoLetraB\n" +
                "		{	color: black;\n" +
                "			font: 10px bold Consolas;\n" +
                "                   width: 240px;\n" +
                "		}\n" +
                "		.tipoLetraAInv\n" +
                "		{	font: 10px Consolas;\n" +
                //"			background: black;\n" +
                //"			color: white;\n" +
                "                   width: 240px;\n" +
                "		}\n" +
                "		.tipoLetraBInv\n" +
                "		{	font: 10px bold Consolas;\n" +
                //"                 background: black;\n" +
                //"			color: white;\n" +
                "                   width: 240px;\n" +
                "		}\n" +
                "	</style>\n" +
                "</head>" + "<body>" + "<br/><br/><br/>");
        textoImpr.append(texto);
        textoImpr.append("<br/><br/>.</body>" + "</html>");

        HiloImpresion hilo = new HiloImpresion();
        hilo.textoImpr = textoImpr.toString();
        hilo.start();
    }

    public Map<String, String> solicitudBinTarjeta(Double monto) {
        Map<String, String> resp = new HashMap<String, String>();

        //clear();
        //setField("ecr_aplicacion",VariablesPinpadMC.APLICACION);
        //setField("ecr_transaccion",VariablesPinpadMC.ECR_TRX_COMPRA);
        //setField("ecr_amount",devolverNumSinPuntoDecimal(monto));
        //setField("ecr_currency_code",VariablesPinpadMC.ECR_CURR_CODE_SOLES);
        //setField("ecr_data_adicional3",VariablesPinpadMC.APLICACION);
        //SendTran();

        resp.put("response_code", "00");
        resp.put("card", "123456");
        resp.put("ecr_aplicacion", VariablesPinpadMC.APLICACION);
        resp.put("ecr_transaccion", VariablesPinpadMC.ECR_TRX_COMPRA);
        return resp;
    }

    public Map<String, String> procesoCompra(Double monto) {
        Map<String, String> resp = new HashMap<String, String>();
        configLibreriasMC();
        HcomHepsJava hcom = new HcomHepsJava();

        try {
            hcom.clear();
            hcom.setField("ecr_aplicacion", VariablesPinpadMC.APLICACION);
            hcom.setField("ecr_transaccion", VariablesPinpadMC.ECR_TRX_COMPRA);
            hcom.setField("ecr_amount", devolverNumSinPuntoDecimal(monto));
            hcom.setField("ecr_currency_code", VariablesPinpadMC.ECR_CURR_CODE_SOLES);
            //hcom.setField("ecr_cod_mozo",codMozo);
            //hcom.setField("ecr_amount_tip","100");
            log.debug("Envia trama al pinpad");
            int rpt = hcom.sendTrama();
            log.debug("Respuesta de sendTrama:" + rpt);
            logTrama(hcom);
            resp.put("rpt", rpt + "");
            resp.put("response_code", recortarCadena(hcom.getField("response_code"), 2));
            resp.put("amount", devolverNumSinPuntoDecimal(monto));
            resp.put("approval_code", recortarCadena(hcom.getField("approval_code"), 6));
            resp.put("message", recortarCadena(hcom.getField("message"), 21));
            resp.put("card", recortarCadena(hcom.getField("card"), 19));
            resp.put("card_id", recortarCadena(hcom.getField("card_id"), 4));
            resp.put("month", recortarCadena(hcom.getField("month"), 2));
            resp.put("amount_quota", recortarCadena(hcom.getField("amount_quota"), 8));
            resp.put("credit_type", recortarCadena(hcom.getField("credit_type"), 1));
            resp.put("client_name", hcom.getField("client_name"));
            resp.put("ecr_currency_code", VariablesPinpadMC.ECR_CURR_CODE_SOLES);
            resp.put("ecr_aplication", VariablesPinpadMC.APLICACION);
            resp.put("ecr_transaccion", VariablesPinpadMC.ECR_TRX_COMPRA);
            resp.put("merchant_id", recortarCadena(hcom.getField("merchant_id"), 8));
            resp.put("print_data", hcom.getField("print_data"));

        } catch (Exception ex) {
            resp = null;
            log.error("Error al procesar trama MC", ex);
        }
        return resp;
    }

    public Map<String, String> anulacionCompra(Double monto, String numReferencia) {
        Map<String, String> resp = new HashMap<String, String>();
        configLibreriasMC();
        HcomHepsJava hcom = new HcomHepsJava();

        try {
            hcom.clear();
            hcom.setField("ecr_aplicacion", VariablesPinpadMC.APLICACION);
            hcom.setField("ecr_transaccion", VariablesPinpadMC.ECR_TRX_ANUL_COMPRA);
            hcom.setField("ecr_amount", devolverNumSinPuntoDecimal(monto));
            hcom.setField("ecr_data_adicional", numReferencia);
            hcom.sendTrama();

            resp.put("response_code", recortarCadena(hcom.getField("response_code"), 2));
            resp.put("amount", hcom.getField("amount"));
            resp.put("approval_code", recortarCadena(hcom.getField("approval_code"), 6));
            resp.put("message", recortarCadena(hcom.getField("message"), 21));
            resp.put("card", recortarCadena(hcom.getField("message"), 19));
            resp.put("month", recortarCadena(hcom.getField("month"), 2));
            resp.put("amount_quota", recortarCadena(hcom.getField("amount_quota"), 8));
            resp.put("credit_type", recortarCadena(hcom.getField("credit_type"), 2));
            resp.put("client_name", hcom.getField("client_name"));
            resp.put("currency_code", recortarCadena(hcom.getField("currency_code"), 3));
            resp.put("ecr_aplication", recortarCadena(hcom.getField("ecr_aplication"), 3));
            resp.put("ecr_transaccion", recortarCadena(hcom.getField("ecr_transaccion"), 2));
            resp.put("merchant_id", recortarCadena(hcom.getField("merchant_id"), 8));
            resp.put("print_data", hcom.getField("print_data"));
        } catch (Exception ex) {
            resp = null;
            log.debug(ex.toString());
        }
        return resp;
    }

    public Map<String, String> reimpresion(String numReferencia) {
        Map<String, String> resp = new HashMap<String, String>();
        configLibreriasMC();
        HcomHepsJava hcom = new HcomHepsJava();

        try {
            hcom.clear();
            hcom.setField("ecr_aplicacion", VariablesPinpadMC.APLICACION);
            hcom.setField("ecr_transaccion", VariablesPinpadMC.ECR_TRX_REIMPRESION);
            hcom.setField("ecr_data_adicional", numReferencia);
            hcom.sendTrama();

            resp.put("response_code", recortarCadena(hcom.getField("response_code"), 2));
            resp.put("message", recortarCadena(hcom.getField("message"), 21));
            resp.put("currency_code", recortarCadena(hcom.getField("currency_code"), 3));
            resp.put("ecr_aplication", recortarCadena(hcom.getField("ecr_aplication"), 3));
            resp.put("ecr_transaccion", recortarCadena(hcom.getField("ecr_transaccion"), 2));
            resp.put("merchant_id", recortarCadena(hcom.getField("merchant_id"), 8));
            resp.put("print_data", hcom.getField("print_data"));
        } catch (Exception ex) {
            resp = null;
            log.debug(ex.toString());
        }
        return resp;
    }

    public Map<String, String> reporteDetallado() {
        Map<String, String> resp = new HashMap<String, String>();
        configLibreriasMC();
        HcomHepsJava hcom = new HcomHepsJava();

        try {
            hcom.clear();
            hcom.setField("ecr_aplicacion", VariablesPinpadMC.APLICACION);
            hcom.setField("ecr_transaccion", VariablesPinpadMC.ECR_TRX_REPORT_DET);
            hcom.sendTrama();

            resp.put("response_code", recortarCadena(hcom.getField("response_code"), 2));
            resp.put("message", recortarCadena(hcom.getField("message"), 21));
            resp.put("currency_code", recortarCadena(hcom.getField("currency_code"), 3));
            resp.put("ecr_aplication", recortarCadena(hcom.getField("ecr_aplication"), 3));
            resp.put("ecr_transaccion", recortarCadena(hcom.getField("ecr_transaccion"), 2));
            resp.put("merchant_id", recortarCadena(hcom.getField("merchant_id"), 8));
            resp.put("print_data", hcom.getField("print_data"));
        } catch (Exception e) {
            resp = null;
            log.debug(e.toString());
        }
        return resp;
    }

    public Map<String, String> reporteTotales() {
        Map<String, String> resp = new HashMap<String, String>();
        configLibreriasMC();
        HcomHepsJava hcom = new HcomHepsJava();

        try {
            hcom.clear();
            hcom.setField("ecr_aplicacion", VariablesPinpadMC.APLICACION);
            hcom.setField("ecr_transaccion", VariablesPinpadMC.ECR_TRX_REPORT_TOT);
            hcom.sendTrama();

            resp.put("response_code", recortarCadena(hcom.getField("response_code"), 2));
            resp.put("message", recortarCadena(hcom.getField("message"), 21));
            resp.put("currency_code", recortarCadena(hcom.getField("currency_code"), 3));
            resp.put("ecr_aplication", recortarCadena(hcom.getField("ecr_aplication"), 3));
            resp.put("ecr_transaccion", recortarCadena(hcom.getField("ecr_transaccion"), 2));
            resp.put("merchant_id", recortarCadena(hcom.getField("merchant_id"), 8));
            resp.put("print_data", hcom.getField("print_data"));
        } catch (Exception e) {
            resp = null;
            log.debug(e.toString());
        }
        return resp;
    }

    public Map<String, String> cierre(String codComercio) {
        Map<String, String> resp = new HashMap<String, String>();
        configLibreriasMC();
        HcomHepsJava hcom = new HcomHepsJava();

        try {
            hcom.clear();
            hcom.setField("ecr_aplicacion", VariablesPinpadMC.APLICACION);
            hcom.setField("ecr_transaccion", VariablesPinpadMC.ECR_TRX_CIERRE);
            hcom.setField("ecr_data_adicional", codComercio);
            hcom.sendTrama();

            resp.put("response_code", recortarCadena(hcom.getField("response_code"), 2));
            resp.put("message", recortarCadena(hcom.getField("message"), 21));
            resp.put("currency_code", recortarCadena(hcom.getField("currency_code"), 3));
            resp.put("ecr_aplication", recortarCadena(hcom.getField("ecr_aplication"), 3));
            resp.put("ecr_transaccion", recortarCadena(hcom.getField("ecr_transaccion"), 2));
            resp.put("merchant_id", recortarCadena(hcom.getField("merchant_id"), 8));
            resp.put("print_data", hcom.getField("print_data"));
        } catch (Exception ex) {
            resp = null;
            log.debug(ex.toString());
        }
        return resp;
    }

    public Map<String, String> reporteDetalladoCierre() {
        Map<String, String> resp = new HashMap<String, String>();
        configLibreriasMC();
        HcomHepsJava hcom = new HcomHepsJava();

        try {
            hcom.clear();
            hcom.setField("ecr_aplicacion", VariablesPinpadMC.APLICACION);
            hcom.setField("ecr_transaccion", VariablesPinpadMC.ECR_TRX_REPORT_DET_CIERRE);
            hcom.sendTrama();

            resp.put("response_code", recortarCadena(hcom.getField("response_code"), 2));
            resp.put("message", recortarCadena(hcom.getField("message"), 21));
            resp.put("currency_code", recortarCadena(hcom.getField("currency_code"), 3));
            resp.put("ecr_aplication", recortarCadena(hcom.getField("ecr_aplication"), 3));
            resp.put("ecr_transaccion", recortarCadena(hcom.getField("ecr_transaccion"), 2));
            resp.put("merchant_id", recortarCadena(hcom.getField("merchant_id"), 8));
            resp.put("print_data", hcom.getField("print_data"));
        } catch (Exception e) {
            resp = null;
            log.debug(e.toString());
        }
        return resp;
    }

    public Map<String, String> reporteTotalesCierre(String codComercio) {
        Map<String, String> resp = new HashMap<String, String>();
        configLibreriasMC();
        HcomHepsJava hcom = new HcomHepsJava();

        try {
            hcom.clear();
            hcom.setField("ecr_aplicacion", VariablesPinpadMC.APLICACION);
            hcom.setField("ecr_transaccion", VariablesPinpadMC.ECR_TRX_REPORT_TOT_CIERRE);
            hcom.setField("ecr_data_adicional", codComercio);
            hcom.sendTrama();

            resp.put("response_code", recortarCadena(hcom.getField("response_code"), 2));
            resp.put("message", recortarCadena(hcom.getField("message"), 21));
            resp.put("currency_code", recortarCadena(hcom.getField("currency_code"), 3));
            resp.put("ecr_aplication", recortarCadena(hcom.getField("ecr_aplication"), 3));
            resp.put("ecr_transaccion", recortarCadena(hcom.getField("ecr_transaccion"), 2));
            resp.put("merchant_id", recortarCadena(hcom.getField("merchant_id"), 8));
            resp.put("print_data", hcom.getField("print_data"));
        } catch (Exception e) {
            resp = null;
            log.debug(e.toString());
        }
        return resp;
    }

    public Map<String, String> multiProducto(String codProducto1, Double monto1, String codProducto2, Double monto2,
                                             String codProducto3, Double monto3, String codProducto4, Double monto4,
                                             String codProducto5, Double monto5) {
        Map<String, String> resp = new HashMap<String, String>();

        //clear();
        //setField("ecr_aplicacion",VariablesPinpadMC.APLICACION);
        //setField("ecr_transaccion",VariablesPinpadMC.ECR_TRX_MULTIPROD);
        //setField("ecr_producto1",codProducto1);
        //setField("ecr_amount1",devolverNumSinPuntoDecimal(monto1));
        //setField("ecr_producto2",codProducto2);
        //setField("ecr_amount2",devolverNumSinPuntoDecimal(monto2));
        //setField("ecr_producto3",codProducto3);
        //setField("ecr_amount3",devolverNumSinPuntoDecimal(monto3));
        //setField("ecr_producto4",codProducto4);
        //setField("ecr_amount4",devolverNumSinPuntoDecimal(monto4));
        //setField("ecr_producto5",codProducto5);
        //setField("ecr_amount5",devolverNumSinPuntoDecimal(monto5));
        //setField("ecr_currency_code",VariablesPinpadMC.ECR_CURR_CODE_SOLES);
        //SendTran();

        resp.put("response_code", "00");
        resp.put("amount", "29412");
        resp.put("approval_code", "204471");
        resp.put("message", "  AP204471  REF0240");
        resp.put("card", "123456******1929");
        resp.put("card_id", "MAST");
        resp.put("month", "1000");
        resp.put("amount_quota", "5000");
        resp.put("credit_type", "00");
        resp.put("client_name", "LUIS ENRIQUE LEIVA BAZAN");
        resp.put("ecr_currency_code", VariablesPinpadMC.ECR_CURR_CODE_SOLES);
        resp.put("ecr_aplicacion", VariablesPinpadMC.APLICACION);
        resp.put("ecr_transaccion", VariablesPinpadMC.ECR_TRX_MULTIPROD);
        resp.put("merchant_id", "29999949");
        resp.put("print_data", "Print Data de Compra Multiproducto");
        return resp;
    }

    public Map<String, String> anulacionMultiproducto(Double monto, String numReferencia) {
        Map<String, String> resp = new HashMap<String, String>();

        //clear();
        //setField("ecr_aplicacion",VariablesPinpadMC.APLICACION);
        //setField("ecr_transaccion",VariablesPinpadMC.ECR_TRX_ANUL_COMPRA);
        //setField("ecr_amount",codComercio);
        //setField("ecr_data_adicional",numReferencia);
        //SendTran();

        resp.put("response_code", "00");
        resp.put("amount", "29412");
        resp.put("approval_code", "345612");
        resp.put("message", "  AP345612  REF0241");
        resp.put("card", "123456******1929");
        resp.put("month", "02");
        resp.put("amount_quota", "5000");
        resp.put("credit_type", "00");
        resp.put("client_name", "LUIS ENRIQUE LEIVA BAZAN");
        resp.put("currency_code", VariablesPinpadMC.ECR_CURR_CODE_SOLES);
        resp.put("ecr_aplicacion", VariablesPinpadMC.APLICACION);
        resp.put("ecr_transaccion", VariablesPinpadMC.ECR_TRX_ANUL_COMPRA);
        resp.put("merchant_id", "29999949");
        resp.put("print_data", "Print Data de anulacionMultiproducto");
        return resp;
    }

    public Map<String, String> avanceEfectivo(Double montoCompra) {
        Map<String, String> resp = new HashMap<String, String>();

        //clear();
        //setField("ecr_aplicacion",VariablesPinpadMC.APLICACION);
        //setField("ecr_transaccion",VariablesPinpadMC.ECR_TRX_DISP_EFECT);
        //setField("ecr_amount",codComercio);
        //setField("ecr_currency_code",VariablesPinpadMC.ECR_CURR_CODE_SOLES);
        //SendTran();

        resp.put("response_code", "00");
        resp.put("amount", "15000");
        resp.put("approval_code", "1000");
        resp.put("message", "378912");
        resp.put("card", "123456******1929");
        resp.put("card_id", "MAST");
        resp.put("month", "02");
        resp.put("amount_quota", "5000");
        resp.put("credit_type", "00");
        resp.put("client_name", "LUIS ENRIQUE LEIVA BAZAN");
        resp.put("ecr_currency_code", VariablesPinpadMC.ECR_CURR_CODE_SOLES);
        resp.put("ecr_aplicacion", VariablesPinpadMC.APLICACION);
        resp.put("ecr_transaccion", VariablesPinpadMC.ECR_TRX_DISP_EFECT);
        resp.put("merchant_id", "29999949");
        resp.put("print_data", "Print Data avanceEfectivo");
        return resp;
    }

    public Map<String, String> anulacionAvance(Double monto, String numReferencia) {
        Map<String, String> resp = new HashMap<String, String>();

        //clear();
        //setField("ecr_aplicacion",VariablesPinpadMC.APLICACION);
        //setField("ecr_transaccion",VariablesPinpadMC.ECR_TRX_ANUL_COMPRA);
        //setField("ecr_amount",codComercio);
        //setField("ecr_data_adicional",numReferencia);
        //SendTran();

        resp.put("response_code", "00");
        resp.put("amount", "2000");
        resp.put("approval_code", "108912");
        resp.put("message", "  AP2108912  REF0100");
        resp.put("card", "123456******1929");
        resp.put("month", "02");
        resp.put("amount_quota", "5000");
        resp.put("credit_type", "00");
        resp.put("client_name", "LUIS ENRIQUE LEIVA BAZAN");
        resp.put("currency_code", VariablesPinpadMC.ECR_CURR_CODE_SOLES);
        resp.put("ecr_aplicacion", VariablesPinpadMC.APLICACION);
        resp.put("ecr_transaccion", VariablesPinpadMC.ECR_TRX_ANUL_COMPRA);
        resp.put("merchant_id", "29999949");
        resp.put("print_data", "Print Data anulacionAvance");
        return resp;
    }

    public Map<String, String> consultaSaldo(String codServicio) {
        Map<String, String> resp = new HashMap<String, String>();

        //clear();
        //setField("ecr_aplicacion",VariablesPinpadMC.APLICACION);
        //setField("ecr_transaccion",VariablesPinpadMC.ECR_TRX_CONS_PAGO_SERV);
        //setField("ecr_cod_servicio",codComercio);
        //setField("ecr_currency_code",VariablesPinpadMC.ECR_CURR_CODE_SOLES);
        //SendTran();

        resp.put("response_code", "00");
        resp.put("message", "CONSULTA SALDO");
        resp.put("card", "123456******1929");
        resp.put("card_id", "MAST");
        resp.put("client_name", "LUIS ENRIQUE LEIVA BAZAN");
        resp.put("ecr_currency_code", VariablesPinpadMC.ECR_CURR_CODE_SOLES);
        resp.put("ecr_aplicacion", VariablesPinpadMC.APLICACION);
        resp.put("ecr_transaccion", VariablesPinpadMC.ECR_TRX_CONS_PAGO_SERV);
        resp.put("ecr_cod_servicio", codServicio);
        resp.put("merchant_id", "codServicio");
        resp.put("print_data", "Print Data consultaSaldo");
        return resp;
    }

    public Map<String, String> pagoServicios(String codServicio, Double monto) {
        Map<String, String> resp = new HashMap<String, String>();

        //clear();
        //setField("ecr_aplicacion",VariablesPinpadMC.APLICACION);
        //setField("ecr_transaccion",VariablesPinpadMC.ECR_TRX_PAGO_SERV);
        //setField("ecr_cod_servicio",codServicio);
        //setField("ecr_amount",codComercio);
        //setField("ecr_currency_code",VariablesPinpadMC.ECR_CURR_CODE_SOLES);
        //SendTran();

        resp.put("response_code", "00");
        resp.put("amount", "15000");
        resp.put("approval_code", "389015");
        resp.put("message", "  AP389015  REF0261");
        resp.put("card", "123456******1929");
        resp.put("card_id", "MAST");
        resp.put("client_name", "LUIS ENRIQUE LEIVA BAZAN");
        resp.put("ecr_currency_code", VariablesPinpadMC.ECR_CURR_CODE_SOLES);
        resp.put("ecr_aplicacion", VariablesPinpadMC.APLICACION);
        resp.put("ecr_transaccion", VariablesPinpadMC.ECR_TRX_PAGO_SERV);
        resp.put("ecr_cod_servicio", codServicio);
        resp.put("merchant_id", "29999949");
        resp.put("print_data", "Print Data pagoServicios");
        return resp;
    }

    public Map<String, String> anulacionPagoServicios(Double monto, String numReferencia) {
        Map<String, String> resp = new HashMap<String, String>();

        //clear();
        //setField("ecr_aplicacion",VariablesPinpadMC.APLICACION);
        //setField("ecr_transaccion",VariablesPinpadMC.ECR_TRX_ANUL_PAGO);
        //setField("ecr_amount",codComercio);
        //setField("ecr_data_adicional",numReferencia);
        //SendTran();

        resp.put("response_code", "00");
        resp.put("amount", "10000");
        resp.put("approval_code", "022396");
        resp.put("message", "  AP022396  REF0680");
        resp.put("card", "123456******1929");
        resp.put("card_id", "MAST");
        resp.put("ecr_currency_code", VariablesPinpadMC.ECR_CURR_CODE_SOLES);
        resp.put("ecr_aplicacion", VariablesPinpadMC.APLICACION);
        resp.put("ecr_transaccion", VariablesPinpadMC.ECR_TRX_ANUL_PAGO);
        resp.put("merchant_id", "29999949");
        resp.put("print_data", "Print Data anulacionPagoServicios");
        return resp;
    }

    public Map<String, String> propina(String numReferencia, Double propina, String codMozo) {
        Map<String, String> resp = new HashMap<String, String>();

        //clear();
        //setField("ecr_aplicacion",VariablesPinpadMC.APLICACION);
        //setField("ecr_transaccion",VariablesPinpadMC.ECR_TRX_ING_PROPINA);
        //setField("ecr_amount",codComercio);
        //setField("ecr_cod_mozo",codMozo);
        //setField("ecr_data_adicional",numReferencia);
        //SendTran();

        resp.put("response_code", "00");
        resp.put("message", "NO HAY PROPINA");
        resp.put("ecr_transaccion", VariablesPinpadMC.ECR_TRX_ING_PROPINA);
        resp.put("merchant_id", "29999949");
        return resp;
    }

    public Map<String, String> reporteMozo() {
        Map<String, String> resp = new HashMap<String, String>();

        //clear();
        //setField("ecr_aplicacion",VariablesPinpadMC.APLICACION);
        //setField("ecr_transaccion",VariablesPinpadMC.ECR_TRX_REPORT_MOZO);
        //SendTran();

        resp.put("response_code", "00");
        resp.put("message", "RESERVADO");
        resp.put("ecr_transaccion", VariablesPinpadMC.ECR_TRX_REPORT_MOZO);
        resp.put("merchant_id", "29999949");
        resp.put("print_data", "Reporte Mozo Print Data reporteMozo");
        return resp;
    }

    public Map<String, String> reimpresionLote(String numLote) {
        Map<String, String> resp = new HashMap<String, String>();
        configLibreriasMC();
        HcomHepsJava hcom = new HcomHepsJava();

        try {
            hcom.clear();
            hcom.setField("ecr_aplicacion", VariablesPinpadMC.APLICACION);
            hcom.setField("ecr_transaccion", VariablesPinpadMC.ECR_TRX_REIMPR_LOTE);
            hcom.setField("ecr_data_adicional2", numLote);
            hcom.sendTrama();

            resp.put("response_code", recortarCadena(hcom.getField("response_code"), 2));
            resp.put("message", recortarCadena(hcom.getField("message"), 21));
            resp.put("ecr_transaccion", recortarCadena(hcom.getField("ecr_transaccion"), 2));
            resp.put("merchant_id", recortarCadena(hcom.getField("merchant_id"), 8));
            resp.put("print_data", hcom.getField("print_data"));
        } catch (Exception e) {
            resp = null;
            log.debug(e.toString());
        }
        return resp;
    }

    private String devolverNumSinPuntoDecimal(Double m) {
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.');
        DecimalFormat formateador = new DecimalFormat("####.00", simbolos);
        return formateador.format(m).replace(".", "");
    }

    /**
     * Guarda el contenido de la trama retornada a la base de datos
     * @author LLEIVA
     * @since 17-Julio-2013
     */
    public Integer guardarTramaProcesoCompraBD(Map<String, String> parametros, String codFormaPago) {
        try {
            log.debug("MC- ingreso a guardar Trama");
            if (!DBPinpad.guardarTramaPinpadMC(parametros.get("response_code"), parametros.get("amount"),
                                               parametros.get("approval_code"), parametros.get("message"),
                                               nroTarjetaBruto, //parametros.get("card"),
                    parametros.get("card_id"), parametros.get("month"), parametros.get("amount_quota"),
                    parametros.get("credit_type"), parametros.get("client_name"), parametros.get("ecr_currency_code"),
                    parametros.get("ecr_aplication"), parametros.get("ecr_transaccion"), parametros.get("merchant_id"),
                    parametros.get("print_data"), VariablesCaja.vNumPedVta, codFormaPago))
                return VariablesPinpad.RET_NOK;
        } catch (Exception e) {
            log.debug(e.toString());
            return VariablesPinpad.RET_NOK;
        }
        return VariablesPinpad.RET_OK;
    }

    /**
     * Guarda el contenido de la trama retornada a la base de datos
     * @author LLEIVA
     * @since 17-Julio-2013
     */
    public Integer guardarTramaAnulacionBD(Map<String, String> parametros, String formaPago) {
        try {
            if (!DBPinpad.guardarTramaAnulPinpadMC(parametros.get("response_code"), parametros.get("amount"),
                                                   parametros.get("approval_code"), parametros.get("message"),
                                                   parametros.get("card"), parametros.get("month"),
                                                   parametros.get("amount_quota"), parametros.get("credit_type"),
                                                   parametros.get("client_name"), parametros.get("ecr_currency_code"),
                                                   parametros.get("ecr_aplication"), parametros.get("ecr_transaccion"),
                                                   parametros.get("merchant_id"), parametros.get("print_data"),
                                                   lblDatoPedidoEnv, //VariablesCaja.vNumPedVta
                    formaPago))
                return VariablesPinpad.RET_NOK;
        } catch (Exception e) {
            log.debug(e.toString());
            return VariablesPinpad.RET_NOK;
        }
        return VariablesPinpad.RET_OK;
    }

    /**
     * Guarda el contenido de la trama retornada a la base de datos
     * @author LLEIVA
     * @since 17-Julio-2013
     */
    public Integer guardarTramaReimpresionBD(Map<String, String> parametros) {
        try {
            if (!DBPinpad.guardarTramaReimpPinpadMC(parametros.get("response_code"), parametros.get("message"),
                                                    parametros.get("ecr_currency_code"),
                                                    parametros.get("ecr_aplication"),
                                                    parametros.get("ecr_transaccion"), parametros.get("merchant_id"),
                                                    parametros.get("print_data"), VariablesCaja.vNumPedVta))
                return VariablesPinpad.RET_NOK;
        } catch (Exception e) {
            log.debug(e.toString());
            return VariablesPinpad.RET_NOK;
        }
        return VariablesPinpad.RET_OK;
    }

    /**
     * Guarda el contenido de la trama retornada a la base de datos
     * @author LLEIVA
     * @since 17-Julio-2013
     */
    public Integer guardarTramaRepDetalladoBD(Map<String, String> parametros) {
        try {
            if (!DBPinpad.guardarTramaRepDetPinpadMC(parametros.get("response_code"), parametros.get("message"),
                                                     parametros.get("ecr_currency_code"),
                                                     parametros.get("ecr_aplication"),
                                                     parametros.get("ecr_transaccion"), parametros.get("merchant_id"),
                                                     parametros.get("print_data"), VariablesCaja.vNumPedVta))
                return VariablesPinpad.RET_NOK;
        } catch (Exception e) {
            log.debug(e.toString());
            return VariablesPinpad.RET_NOK;
        }
        return VariablesPinpad.RET_OK;
    }

    /**
     * Guarda el contenido de la trama retornada a la base de datos
     * @author LLEIVA
     * @since 17-Julio-2013
     */
    public Integer guardarTramaRepTotalBD(Map<String, String> parametros) {
        try {
            if (!DBPinpad.guardarTramaRepTotPinpadMC(parametros.get("response_code"), parametros.get("message"),
                                                     parametros.get("ecr_currency_code"),
                                                     parametros.get("ecr_aplication"),
                                                     parametros.get("ecr_transaccion"), parametros.get("merchant_id"),
                                                     parametros.get("print_data"), VariablesCaja.vNumPedVta))
                return VariablesPinpad.RET_NOK;
        } catch (Exception e) {
            log.debug(e.toString());
            return VariablesPinpad.RET_NOK;
        }
        return VariablesPinpad.RET_OK;
    }

    /**
     * Guarda el contenido de la trama retornada a la base de datos
     * @author LLEIVA
     * @since 17-Julio-2013
     */
    public Integer guardarTramaCierreBD(Map<String, String> parametros) {
        try {
            if (!DBPinpad.guardarTramaCierrePinpadMC(parametros.get("response_code"), parametros.get("message"),
                                                     parametros.get("ecr_currency_code"),
                                                     parametros.get("ecr_aplication"),
                                                     parametros.get("ecr_transaccion"), parametros.get("merchant_id"),
                                                     parametros.get("print_data"), VariablesCaja.vNumPedVta))
                return VariablesPinpad.RET_NOK;
        } catch (Exception e) {
            log.debug(e.toString());
            return VariablesPinpad.RET_NOK;
        }
        return VariablesPinpad.RET_OK;
    }

    /**
     * Actualiza el flag de la transacción realizada para indicar si no se pudo anular por que el lote se encontraba cerrado
     * @author LLEIVA
     * @since 11-Dic-2013
     */
    public Integer guardarIndAnulTransCerr(String numPed, String tipoTarj) {
        try {
            if (!DBPinpad.guardarIndAnulTransCerr(numPed, tipoTarj))
                return VariablesPinpad.RET_NOK;
        } catch (Exception e) {
            log.debug(e.toString());
            return VariablesPinpad.RET_NOK;
        }
        return VariablesPinpad.RET_OK;
    }

    /**
     * Verifica la configuración de las librerias usadas por Mastercard
     * @author ERIOS
     * @since 18.09.2013
     */
    private void configLibreriasMC() {
        String libpath = System.getProperty("java.library.path");

        String ps = File.pathSeparator;
        //TODO leer de BBDD

        if (!libpath.contains(VariablesPinpadMC.libpathMC)) {
            libpath = libpath + ps + VariablesPinpadMC.libpathMC + ps;
            System.setProperty("java.library.path", libpath);
            log.debug("Configura Librerias MC---> " + libpath);
        }

        try {
            Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
            fieldSysPath.setAccessible(true);
            fieldSysPath.set(null, null);
        } catch (NoSuchFieldException e) {
            log.error("NoSuchFieldException", e);
        } catch (IllegalAccessException e) {
            log.error("IllegalAccessException", e);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private String recortarCadena(String cadena, int longitud) {
        if (cadena != null && longitud > 0) {
            int longCadena = cadena.length();
            if (longitud <= longCadena)
                cadena = cadena.substring(0, longitud);
        }
        return cadena;
    }

    public void setNroTarjetaBruto(String nroTarjetaBruto) {
        this.nroTarjetaBruto = nroTarjetaBruto;
    }

    public String getNroTarjetaBruto() {
        return nroTarjetaBruto;
    }

    public void setLblDatoPedidoEnv(String lblDatoPedidoEnv) {
        this.lblDatoPedidoEnv = lblDatoPedidoEnv;
    }

    public String getLblDatoPedidoEnv() {
        return lblDatoPedidoEnv;
    }

    private void logTrama(HcomHepsJava hcom) {
        try {
            log.debug("response_code:" + hcom.getField("response_code"));
            log.debug("amount:" + hcom.getField("amount"));
            log.debug("approval_code:" + hcom.getField("approval_code"));
            log.debug("message:" + hcom.getField("message"));
            log.debug("card:" + hcom.getField("card"));
            log.debug("card_id:" + hcom.getField("card_id"));
            log.debug("month:" + hcom.getField("month"));
            log.debug("amount_quota:" + hcom.getField("amount_quota"));
            log.debug("credit_type:" + hcom.getField("credit_type"));
            log.debug("client_name:" + hcom.getField("client_name"));
            log.debug("merchant_id:" + hcom.getField("merchant_id"));
            //log.debug("print_data",      hcom.getField("print_data"));
        } catch (Exception e) {
            log.debug("", e);
        }
    }
}
