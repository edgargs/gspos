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
 * CHUANES      01.09.2014  Creación<br>
 * KMONCADA     08.06.2016 MODIFICACION - SE LIMPIO CODIGO
 * <br>
 * @author Cesar Alfredo Huanes Bautista<br>
 * @version 1.0<br>
 *
 */
public class UtilityImpCompElectronico {
    private static final Logger log = LoggerFactory.getLogger(UtilityImpCompElectronico.class);
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
     * @author KMONCADA
     * @since 12.05.2016
     * @param tipoImpTermica
     * @param nomImpTermica
     * @throws Exception
     */
    public UtilityImpCompElectronico(String tipoImpTermica, String nomImpTermica)throws Exception {
        this.tipoImpTermica = tipoImpTermica;
        this.nomImpTermica = nomImpTermica;
        this.rutaImpTermica = getRuta();
        
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
    
    public boolean imprimirComprobantePago(String pNumPedidoVta, String pSecCompPago, boolean isReimpresion) throws Exception {
        List listComanda = new ArrayList();
        listComanda = DBComprobanteElectronico.getDatosImpresion(FarmaVariables.vVersion, pNumPedidoVta, pSecCompPago, isReimpresion);
        
        VariablesFidelizacion.numPedVtaTextExpert = pNumPedidoVta;
        VariablesFidelizacion.secCompPagoTextExpert = pSecCompPago;
        
        if (listComanda.size() > 0) {
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
    
    /**
     * CHUANES
     * Obtiene el Nombre de la Impresora Termica
     * 08/09/2014
     * */
    public String getNombreTermica() throws SQLException {
        return this.nomImpTermica;
    }

    public String getModelo() throws SQLException {
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
    
    public boolean impresionMatricial(List listDocumento, String pRutaFile){
        boolean isImprimio = true;
        FarmaPrintService vPrint = new FarmaPrintService(24, VariablesCaja.vRutaImpresora, false);
        FarmaPrintServiceTicket vPrintArchivo = null;
        if (pRutaFile != null) {
            vPrintArchivo = new FarmaPrintServiceTicket(666, pRutaFile, false);
            vPrintArchivo.startPrintService();
        }
        if (!vPrint.startPrintService()) {
            isImprimio = false;
            VariablesCaja.vEstadoSinComprobanteImpreso = "S";
        }else{
            vPrint.activateCondensed();
            for (int i = 0; i < listDocumento.size(); i++) {
                String linea = (String)((HashMap)listDocumento.get(i)).get("VALOR");
                vPrint.printLine(linea, true);
                if (pRutaFile != null) {
                    vPrintArchivo.printLine(linea, true); //
                }
            }
            vPrint.deactivateCondensed();
            vPrint.endPrintService();
            vPrintArchivo.endPrintService();
            VariablesCaja.vEstadoSinComprobanteImpreso = "N";
            isImprimio = true;
        }
        return isImprimio;
    }
}
