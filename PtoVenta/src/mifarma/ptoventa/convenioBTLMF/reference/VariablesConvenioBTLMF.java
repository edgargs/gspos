package mifarma.ptoventa.convenioBTLMF.reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class VariablesConvenioBTLMF {
    private static final Logger log = LoggerFactory.getLogger(VariablesConvenioBTLMF.class);

    public VariablesConvenioBTLMF() {
    }

    public static ArrayList vArrayList_ListaConvenio = new ArrayList();
    public static ArrayList vArrayList_DatosConvenio = new ArrayList();

    public static String vCodConvenio = "";
    public static String vCodConvenioAux = "";
    public static String vCodCliente = "";
    public static String vNomConvenio = "";
    public static String vNumDocIdent = "";
    public static String vTextoCliente = "";
    public static String vNomCliente = "";
    public static String vApeCliente = "";
    public static String vNomContratante = "";
    public static String vFlgRetencion = "";
    public static String vCodTipoCampo = "";
    public static String vFlgCreacionCliente = "";
    public static String vFlgTipoConvenio = "";
    public static String vBuscarNombreBenif = "";
    public static String vLineaCredito = "";
    public static String vRuc = "";
    public static String vInstitucion = "";
    public static String vDireccion = "";
    public static String vNumOrdeCompra = "";
    public static String vPuntoLlegada = "";


    public static String vEstado = "";
    public static String vDatoLCredSaldConsumo = "";
    public static String vMontoSaldo = "";


    public static String vCodConvenioRel = "";
    public static String vNomConvenioRel = "";
    public static String vFlgCreacionClienteRel = "";
    public static String vFlgTipoConvenioRel = "";
    public static String vFlgDataRimac = "";
    public static String vFlgValidaLincreBenef = "";

    public static String vFlgImprimeImportes = "";
    public static String vIndVtaComplentaria = "";

    public static boolean vHayDatosIngresadosConvenioBTLMF = false;


    //Datos de Convenio BTL Y MF
    public static String vInd_in_beneficiario = "";
    public static String vInd_in_repartidor = "";
    public static String vInd_in_diagnostico = "";
    public static String vInd_in_medico = "";
    public static int vInd_in_adicionales = 0;

    //dubilluz 26.10.2011
    public static List vDatosConvenio = null;
    public static List vDatosConvenioIngresar = new ArrayList();
    public static ArrayList listaDatos = new ArrayList();
    public static Map listaDatosNoEditables = new HashMap();

    public static int vPaginaActual = 0;
    public static boolean vAceptar = false;

    //Datos del Benificiario

    public static String vNombre = "";
    public static String vApellidoPat = "";
    public static String vApellidoMat = "";
    public static String vDni = "";
    public static String vTelefono = "";
    public static String vEmail = "";
    public static String vFechaNac = "";
    public static String vCreacionCliente = "";

    //Datos del Medico
    public static String vCodigoMedico = "";
    public static String vNombreMedico = "";
    public static String vApeMedico = "";

    //Datos del Diagnostico
    public static String vCodCIE10 = "";
    public static String vDescDiagnostico = "";


    public static String vCodigo = "";
    public static String vDescripcion = "";
    public static String vCodigoAux = "";

    //
    public static double vImpSubTotal = 0;

    //
    public static boolean vConsSaldoCredBenif = false;

    // Flag para el listado de datos adicionales

    public static String vFlg_lista = "";

    //Jquispe 16.02.2012 , arreglo que carga los clientes de convenios.
    //DJARA 07.04.2014 , Comentado porque no debe cargar precios de convenios
    //public static ArrayList vDatosPreciosConv = new ArrayList();

    //Jquispe
    public static boolean vValidaPrecio = false;

    //Jquispe
    public static String vNew_Prec_Conv = "";
    public static double vValorSelCopago = -1;

    //Datos del Comprobante de Pago
    public static ArrayList vArray_ListaComprobante = new ArrayList();
    public static String vNumCompPago = "";
    public static String vSecCompPago = "";
    public static String vTipoCompPago = "";
    public static String vTipoCompPagoAux = "";

    public static String vValIgvCompPago = "";
    public static String vValNetoCompPago = "";
    public static String vValCopagoCompPago = "";
    public static String vValIgvCompCoPago = "";
    public static String vNumCompPagoRef = "";
    public static String vTipClienConvenio = "";
    public static String vFlgImprDatAdic = "";
    public static String vCodTipoConvenio = "";
    public static String vValRedondeoCompPago = "";


    public static boolean vFlgImprimirCompBoleta = true;
    public static boolean vFlgImprimirCompFactura = true;
    public static boolean vFlgImprimirCompGuia = true;
    public static boolean vFlgImprimirCompTicket = true;


    //Datos del detalle de comprobante de Pago.
    public static ArrayList vArray_ListaDetComprobante = new ArrayList();

    // Lista de datos adicionales de convenio por Pedido.
    public static List vArrayList_DatosConvenioAdic = new ArrayList();


    // LIMPIAR VARIABLES DE CONVENIO

    public static void limpiaVariablesBTLMF() {
        log.debug(".... LIMPIANDO VARIABLES CONVENIOS BTL MF...");
        vArrayList_ListaConvenio = new ArrayList();
        vArrayList_DatosConvenio = new ArrayList();

        vCodConvenio = "";
        vCodConvenioAux = "";
        vCodCliente = "";
        vNomConvenio = "";
        vNumDocIdent = "";
        vTextoCliente = "";
        vNomCliente = "";
        vApeCliente = "";
        vNomContratante = "";
        vFlgRetencion = "";
        vCodTipoCampo = "";
        vFlgCreacionCliente = "";
        vFlgTipoConvenio = "";
        vBuscarNombreBenif = "";
        vCodConvenioRel = "";
        vNomConvenioRel = "";
        vFlgCreacionClienteRel = "";
        vFlgTipoConvenioRel = "";
        vFlgDataRimac = "";
        vFlgValidaLincreBenef = "";
        vFlgImprimeImportes = "";
        vIndVtaComplentaria = "";

        vHayDatosIngresadosConvenioBTLMF = false;
        //Datos de Convenio BTL Y MF
        vInd_in_beneficiario = "";
        vInd_in_repartidor = "";
        vInd_in_diagnostico = "";
        vInd_in_medico = "";
        vInd_in_adicionales = 0;
        //dubilluz 26.10.2011
        vDatosConvenio = null;
        vDatosConvenioIngresar = new ArrayList();
        listaDatos = new ArrayList();
        listaDatosNoEditables = new HashMap();
        vPaginaActual = 0;
        vAceptar = false;
        //Datos del Benificiario
        vNombre = "";
        vApellidoPat = "";
        vApellidoMat = "";
        vDni = "";
        vTelefono = "";
        vEmail = "";
        vFechaNac = "";
        vCreacionCliente = "";
        vCodigoMedico = "";
        vNombreMedico = "";
        vApeMedico = "";
        vCodCIE10 = "";
        vDescDiagnostico = "";
        vCodigo = "";
        vDescripcion = "";
        vCodigoAux = "";
        //
        vImpSubTotal = 0;
        vConsSaldoCredBenif = false;
        // Flag para el listado de datos adicionales
        vFlg_lista = "";
        //Jquispe 16.02.2012 , arreglo que carga los clientes de convenios.
        //DJARA 07.04.2014 , Comentado porque no debe cargar precios de convenios
        //vDatosPreciosConv = new ArrayList();
        //Jquispe
        vValidaPrecio = false;
        //Jquispe
        vNew_Prec_Conv = "";
        //Datos del Comprobante de Pago
        vArray_ListaComprobante = new ArrayList();
        vNumCompPago = "";
        vSecCompPago = "";
        vTipoCompPago = "";
        vValIgvCompPago = "";
        vValNetoCompPago = "";
        vValCopagoCompPago = "";
        vValIgvCompCoPago = "";
        vNumCompPagoRef = "";
        vTipClienConvenio = "";
        vFlgImprDatAdic = "";
        vValRedondeoCompPago = "";

        //Datos del detalle de comprobante de Pago.
        vArray_ListaDetComprobante = new ArrayList();
        // Lista de datos adicionales de convenio por Pedido.
        vArrayList_DatosConvenioAdic = new ArrayList();
        log.debug(".... **** FIN **** LIMPIANDO VARIABLES CONVENIOS BTL MF...");

        vFlgImprimirCompBoleta = true;
        vFlgImprimirCompFactura = true;
        vFlgImprimirCompGuia = true;
        vFlgImprimirCompTicket = true;


        //Datos de linea credito del Beneficiario
        vLineaCredito = "";
        vDatoLCredSaldConsumo = "";
        vMontoSaldo = "";
        vRuc = "";
        vInstitucion = "";
        vDni = "";
        VariablesConvenioBTLMF.vNombre = "";
        VariablesConvenioBTLMF.vApellidoPat = "";
        vNomClienteDigitado = "";


        VariablesConvenioBTLMF.vNumOrdeCompra = "";
        VariablesConvenioBTLMF.vPuntoLlegada = "";
    }

    public static void limpiarVariablesGlobales() {

        VariablesConvenioBTLMF.vCodConvenio = "";
        VariablesConvenioBTLMF.vCodCliente = "";
        VariablesConvenioBTLMF.vNomConvenio = "";
        VariablesConvenioBTLMF.vFlgCreacionCliente = "";
        VariablesConvenioBTLMF.vNombre = "";
        VariablesConvenioBTLMF.vApellidoPat = "";
        VariablesConvenioBTLMF.vDni = "";

        VariablesConvenioBTLMF.vCodConvenioRel = "";
        VariablesConvenioBTLMF.vNomConvenioRel = "";
        VariablesConvenioBTLMF.vFlgCreacionClienteRel = "";
        VariablesConvenioBTLMF.vFlgTipoConvenioRel = "";

        VariablesConvenioBTLMF.vMontoSaldo = "";

        VariablesConvenioBTLMF.vNew_Prec_Conv = "";

        VariablesConvenioBTLMF.vTipoCompPago = "";

        VariablesConvenioBTLMF.vNumCompPago = "";

        vImpSubTotal = 0;

    }
    public static String Mensaje = "";

    //CHUANES 28.03.2014
    public static String codDiagnostico = "";
    public static String descripDiagno = "";
    //CHUANES 31.01.2014
    public static String vNomClienteDigitado = "";


}
