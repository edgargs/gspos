package mifarma.ptoventa.reference;

import mifarma.common.*;
import javax.swing.JLabel;

/**
* Copyright (c) 2006 MIFARMA S.A.C.<br>
* <br>
* Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
* Nombre de la Aplicación : ConstantsPtoVenta.java<br>
* <br>
* Histórico de Creación/Modificación<br>
* LMESIA      22.02.2006   Creación<br>
* <br>
* @author Luis Mesia Rivera<br>
* @version 1.0<br>
*
*/

public class ConstantsPtoVenta
{
  public ConstantsPtoVenta()
  {
  }
  
  //TODAS
  public static final String PUERTO = "1521";

// IP DE SERVIDOR LOCAL (TEST)
//  public static final String IP_BD = "";
// IP DE TEST

// USUARIO DE SERVIDOR DE LOCAL (TEST)
  //public static final String USUARIO_BD = "idupventa";
  //public static final String CLAVE_BD = "idupventa";

  //public static final String USUARIO_BD = "ptoventa";
  //public static final String CLAVE_BD = "ptoventa01";

  // USUARIO DE PTOVENTA_PRUEBA
  //public static final String USUARIO_BD = "ptoventa";
 // public static final String SID = "XE";
  
  // USUARIO DE PTOVENTA PREPROD
  //public static final String USUARIO_BD = "ptoventa";
  //public static final String CLAVE_BD = "alemania753";
  //public static final String SID = "XE";
  
  // USUARIO DE PTOVENTA MATRIZ PRE PROD
  //public static final String USUARIO_BD = "ptoventa";
  //public static final String SID = "MFPREPROD01";
  
  // USUARIO DE PTOVENTA PROD
  public static final String USUARIO_BD = "idupventa";
 public static final String SID = "XE";
  
  // USUARIO DE PTOVENTA MATRIZ
//  public static final String USUARIO_BD = "ptoventa";
//  public static final String SID = "MFPROD01";

  //Lista Maestros
  public static final FarmaColumnData columnsListaMaestros[] = {
    new FarmaColumnData( "Codigo", 85, JLabel.LEFT ),
    new FarmaColumnData( "Descripcion", 220, JLabel.LEFT ),
    new FarmaColumnData( "RUC", 0, JLabel.CENTER ),
	};


   //Lista Maestros Transferencias
  public static final FarmaColumnData columnsListaMaestrosTransf[] = {
    new FarmaColumnData( "Lote", 100, JLabel.LEFT ),
    new FarmaColumnData( "Fecha de Vencimiento", 220, JLabel.LEFT ),
    };

  public static final Object[] defaultValuesListaMaestros = {" ", " ", " "};

 //Lista Filtro
  public static final FarmaColumnData columnsListaFiltro[] = {
    new FarmaColumnData( "Codigo", 70, JLabel.CENTER ),
	  new FarmaColumnData( "Descripcion", 250, JLabel.LEFT ),
	};

  public static final Object[] defaultValuesListaFiltro = {" ", " "};

  public static final String[] FILTROS_PRODUCTOS_CODIGO = {"1","2","3"};
  public static final String[] FILTROS_PRODUCTOS_DESCRIPCION = {"PRINCIPIO ACTIVO","ACCION TERAPEUTICA","LABORATORIO"};

  public static final String HASHTABLE_CATEGORIAS_FILTRO_PRODUCTO = "CATEGORIAS_FILTRO_PRODUCTO";

  public static final String TIP_OPERACION_RESPALDO_SUMAR = "S";
  public static final String TIP_OPERACION_RESPALDO_BORRAR = "B";
  //public static final String TIP_OPERACION_RESPALDO_LIMPIAR = "L";
  public static final String TIP_OPERACION_RESPALDO_EJECUTAR = "E";
  public static final String TIP_OPERACION_RESPALDO_ACTUALIZAR = "A";
  public static final String TIP_OPERACION_RESPALDO_ACTUALIZAR_PEDIDO = "P";

  public static final String TIP_OPERACION_MOV_CAJA_CONSULTA = "C";
  public static final String TIP_OPERACION_MOV_CAJA_REGISTRAR_VENTA = "R";


   public static final String TIP_MOV_CAJA_CIERRE = "C";
   public static final String TIP_MOV_CAJA_APERTURA = "A";
   public static final String TIP_MOV_CAJA_ARQUEO = "R";

  

  public static final String[] FILTROS_TIPO_PRODUCTO_CODIGO = {"S","N"};
  public static final String[] FILTROS_TIPO_PRODUCTO_DESCRIPCION = {"FARMA","NO FARMA"};

  public static final String HASHTABLE_TIPO_PRODUCTO_FILTRO = "TIPO_PRODUCTO_FILTRO";


  public static final String TIP_COMP_BOLETA = "01";
  public static final String TIP_COMP_FACTURA = "02";
  public static final String TIP_COMP_GUIA = "03";
  public static final String TIP_COMP_NOTA_CREDITO = "04";
  public static final String TIP_COMP_TICKET = "05";

  public static final String LISTA_LOCAL = "1";
  public static final String LISTA_MATRIZ = "2";
  public static final String LISTA_PROVEEDOR_CE = "3A";
  public static final String LISTA_PROVEEDOR = "3";
  public static final String LISTA_COMPETENCIA = "4";
  public static final String LISTA_OPERADORES_TARJETA = "5";
  public static final String LISTA_LOTE = "6";
  public static final String LISTA_LABORATORIO = "7";
  public static final String LISTA_TRABAJADOR = "8";
  public static final String LISTA_TRABAJADOR_LOCAL = "9";
  public static final String LISTA_CAJERO = "10";
  public static final String LISTA_NUMERO_CUENTA = "11";
  public static final String LISTA_CAJEROS_DIA_VENTA = "12";
  public static final String LISTA_TRANSPORTISTA = "13";
  public static final String LISTA_CLIENTES_CONVENIO = "14";
  public static final String LISTA_TRANSP_CIEGA="15"; //ASOSA 05.04.2010
  
  public static final String LISTA_MAESTRO_LOCAL = "01";
  public static final String LISTA_MAESTRO_MATRIZ = "02";
  public static final String LISTA_MAESTRO_PROVEEDOR = "03";
  public static final String LISTA_MAESTRO_COMPETENCIA = "04";
  //LLEIVA 29-Oct-2013
  public static final String LISTA_MAESTRO_RECETARIO = "05";

  public static final String TIP_NUMERA_MOV_CAJA = "010";

   public static final String TIP_LIST_MAESTRO_ORD = "0";
   public static final String TIP_LIST_MAESTRO_TRANSF = "1";
  
  	public static final FarmaColumnData columnsListaMovimientos[] = {
            new FarmaColumnData("Sec", 80, JLabel.LEFT),
            new FarmaColumnData("Dia Venta", 80, JLabel.CENTER),
            new FarmaColumnData("Caja", 45, JLabel.CENTER),
            new FarmaColumnData("Turno", 50, JLabel.CENTER),
            new FarmaColumnData("Movimiento", 70, JLabel.LEFT),
            new FarmaColumnData("Cajero", 120, JLabel.LEFT),
            new FarmaColumnData("Cierre Turno", 120, JLabel.RIGHT),
            new FarmaColumnData("Fecha Movimiento", 120, JLabel.RIGHT),
            new FarmaColumnData("tipMov", 0, JLabel.RIGHT),
            new FarmaColumnData("numCaja", 0, JLabel.RIGHT),
            new FarmaColumnData("numTurnoCaja", 0, JLabel.RIGHT),
            new FarmaColumnData("secMovCajaOrigen", 0, JLabel.RIGHT),
            new FarmaColumnData("Orden", 0, JLabel.RIGHT),
      };


	public static final Object[] defaultValuesListaMovimientos = {" "," "," ", " ", " "," "," ", " ", " ", " ", " ", " ", " " };


  //MOTIVOS KARDEX
  public static final String MOT_KARDEX_VENTA_NORMAL = "001";
  public static final String MOT_KARDEX_VENTA_DELIVERY = "002";
  public static final String MOT_KARDEX_VENTA_ESPECIAL = "003";
  public static final String MOT_KARDEX_ANULACION_INGRESO = "010";
  public static final String MOT_KARDEX_ANULACION_TRANSFERENCIA = "108";
  public static final String MOT_KARDEX_INGRESO_MATRIZ = "101";
  public static final String MOT_KARDEX_COTIZACION_COMPETENCIA = "102";
  public static final String MOT_KARDEX_INGRESO_PROVEEDOR = "103";
  public static final String MOT_KARDEX_INGRESO_LOCAL = "104";
  public static final String MOT_KARDEX_DEVOLUCION_VENTA = "106";
  public static final String MOT_KARDEX_INGRESO_MANUAL_MATRIZ = "110";
  public static final String MOT_KARDEX_RECEPCION_PROVEEDOR = "111";
  //LLEIVA - 29-Oct-2013
  public static final String MOT_KARDEX_RECEPCION_RECETARIO = "112";
  public static final String MOT_KARDEX_AJUSTE_DIFERENCIA = "506";
  public static final String MOT_KARDEX_SALIDA_PROVEEDOR = "211";   

  //TIPOS DOCUMENTOS KARDEX
  public static final String TIP_DOC_KARDEX_VENTA = "01";
  public static final String TIP_DOC_KARDEX_GUIA_ES = "02";
  public static final String TIP_DOC_KARDEX_AJUSTE = "03";

  //TIPOS DE CAJA LOCAL
  public static final String TIP_CAJA_TRADICIONAL = "T";
  public static final String TIP_CAJA_MULTIFUNCIONAL = "M";

  //TOTAL DE LINEAS X COMPROBANTE
  public static final int TOTAL_LINEAS_POR_BOLETA = 5;
  public static final int TOTAL_LINEAS_POR_FACTURA = 7;

  //
  public static final String TIP_NOTA_INGRESO = "01";
  public static final String TIP_NOTA_SALIDA = "02";
  public static final String TIP_NOTA_RECEPCION = "03";
  
   public static final String TIP_ACC_LISTA_COMP_CAJA = "1";
   public static final String TIP_ACC_LISTA_COMP_REP = "2";
   
  public static final String COD_MATRIZ = "010";
  public static final String COD_USU_MATRIZ = "999";
  public static final String CLAVE_MATRIZ = "999";
  public static final String MENSAJE_MATRIZ = "Esta función no está habilitada en Matriz.";
  
  public static final String MENSAJE_LOGIN = "Acceso al FarmaVenta";
  
  public static final String MODULO_VENTAS = "V";
  public static final String MODULO_TRANSFERENCIA = "T";
  
  public static final String COD_LOCAL_DELIVERY = "999";
  
  public static final String NOM_HASTABLE_CMBMODELO_IMPRESORA = "CMB_MODELO_IMPRESORA";  
  
    public static final String NOM_HASTABLE_CMBFORMATO_IMPRESION = "CMB_FORMATO_IMPRESION";  

  public static final int TOTAL_LINEAS_FACTURA_GUIA = 24; 
  //CREACION DE USUARIO ASISTENTE AUDITORIA
  
  public static final String ROL_ASISTENTE_AUDITORIA="036";
    
    //LLEIVA 13/Sep/2013 Constantes de formas de pago a utilizar
    public static final String FORPAG_VISA_POS="00003";
    public static final String FORPAG_MC_POS="00006";
    public static final String FORPAG_VISA_PINPAD="00083";
    public static final String FORPAG_MC_PINPAD="00084";
    public static final String FORPAG_CMR="00024";
    public static final String FORPAG_DINERS="00087";
    public static final String FORPAG_AMEX="00088";
}


