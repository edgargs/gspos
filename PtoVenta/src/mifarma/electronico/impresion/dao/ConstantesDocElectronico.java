package mifarma.electronico.impresion.dao;

/**
 * Copyright (c) 2014 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : ConstantesDocElectronico.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * CHUANES     01.09.2014  Creación<br>
 * <br>
 * @author Cesar Alfredo Huanes Bautista<br>
 * @version 1.0<br>
 *
 */
public class ConstantesDocElectronico {
    public ConstantesDocElectronico() {
        super();
    }

    public final static String RUCEMISOR = "Ruc:";
    public final static String TLFEMISOR = "Telf:";
    public final static String NUMGUIA = "Num Guia:";

    public final static String ORDCOMP = "Num Ord Compra:";
    public final static String POLIZA = "Num Poliza:";

    public final static String RUCCLIENTE = "Ruc Cliente:";
    public final static String NOMBRECLIENTE = "Nombre del Cliente: ";
    public final static String RAZSOCIACLIENTE = "Razon Social:";
    public final static String DIRCLIENTE = "Direccion:";

    public final static String NOMCLIENTE = "Nombre Cliente:";
    public final static String REFERENCIA = "Referencia:";
    public final static String DIRENVIO = "Dir. Envio:";

    public final static String CODIGO = "CODIGO";
    public final static String DESCRIPCION = "DESCRIPCION";
    public final static String CANTIDAD = "CANT.";
    public final static String PREUNIT = "PRE.UNIT.";
    public final static String DESCUENT = "DSCTO.";
    public final static String IMPORTE = "IMPORTE";
    public final static double IMPLIMITE = 700.00;


    public final static String OPGRABADAS = "OP. GRAVADAS:";
    public final static String OPINAFECTAS = "OP. INAFECTAS:";
    public final static String OPEXONERDAS = "OP. EXONERADAS:";
    public final static String OPGRATUITAS = "OP. GRATUITAS:";
    public final static String IMPIGV = "IGV-18%:";
    public final static String DESCUENTO = "DESCUENTO:";
    public final static String REDONDEO = "REDONDEO:";
    public final static String TOTAL = "TOTAL:";

    // Para COPAGO EMPRESA
    public final static String IMP_TOTAL = "IMPORTE TOTAL:";
    public final static String TOTALPAGAR = "TOTAL A PAGAR:";

    public final static String EFECTSOLES = "EFECTIVO SOLES:";
    public final static String EFECTDOLARES = "EFECTIVO DOLARES:";
    public final static String DSCPERSONAL = "DESCUENTO PERSONAL:";
    public final static String VTACREEDITO = "VENTA AL CREDITO";
    public final static String TIPCAMBIO = "TIPO DE CAMBIO:";
    public final static String AHORRO = "EN LA COMPRA USTED AHORRO:";
    public final static String SOLES = "S/.";
    public final static String DOLLAR = "$.";

    public final static String VUELTO = "VUELTO:";
    public final static String FECHAEMISION = "Fecha de Emision:";

    public final static String CAJA = "CAJA:";
    public final static String USUARIO = "USUARIO:";
    public final static String IMPOK = "Recoger Comprobante Electronico";

    public final static String ERRORIMP = "No se puede Imprimir el Documento Electronico.";
    public final static String ERRORCONX = "Error ,Verifique la conexion de la impresora.";
    public final static String ERRORDATOS = "Error al obtener los datos del Documento Electronico.";
    public final static String ERRORNOM = "Verifique el nombre del modelo \n" +
        "de la impresora en Base de Datos ";
    public final static String ERRORACCESO = "No se puede acceder a la ruta.";
    public final static String ERRORASIGNA = "En su computadora no se encuentra  instalada\n" +
        "a una impresora termica de nombre ";
    public final static String ERRDATOSIMP = "Error al obtener los  datos\n" +
        "de modelo y ruta de la impresora termica";
    public final static String ERRORNOTCRED = "La nota de Credito no es Electronica, no se puede Imprimir";
    public final static String MSGAYUDA = "Reportar a Mesa de Ayuda";
    /*public final static String START="START";
    public final static String EPSON="EPSON";*/

    public final static String INDELECTRONICO = "TRUE";
    public final static boolean FLAGACTIVOEPOST = true;
    public final static String CODAPROB = "Codigo de Aprobacion";
    public final static String NUMTARJETA = "Numero de Tarjeta";
    public final static String NUMPIN = "Numero de Pin";


    public final static String DATOSCLIENTE = "DATOS DEL CLIENTE";
    public final static String NOMBRE = "NOMBRE:";
    public final static String DNI = "DNI:";
    public final static String FIRMA = "FIRMA:";
    public final static String TIPNOTACREDITO = "04";
    public final static String TIPVTAINSTU = "03";
    public final static String TIPVTAMESON = "01";
    public final static String TIPVTADELIV = "02";
    public final static String MOTIVO = "MOTIVO ANULACION:";
    public final static String CERODECIMAL = "0.00";
    public final static String CERO = "0";
    public final static String DOCREF = "DOC.REF: ";
    public final static String GANO = "POR SU COMPRA USTED GANO:";
    public static int CANTCUPON = 0;
    public final static String CUPONES = "CUPONES.";
    public final static String CUPON = "CUPON.";
    public static String lstPedidos[];
    //Dubilluz 03.10.2014
    public final static String CO_PAGO = "COPAGO(#%):";
    //Dubilluz 03.10.2014
    public final static String BOLETA = "01";
    public final static String TICKETBOLETA = "05";
    public final static String FACTUA = "02";
    public final static String TICKETFACTURA = "06";
    public final static String NOTACREDITO = "04";
    public final static String TIPCOMPAGOREFBOLETA = "1";
    public final static String TIPCOMPAGOREFFACTURA = "3";
}
