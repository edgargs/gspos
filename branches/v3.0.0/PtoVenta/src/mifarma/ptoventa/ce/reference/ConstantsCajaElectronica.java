package mifarma.ptoventa.ce.reference;


import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : ConstantsCajaElectronica.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      31.07.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */

public class ConstantsCajaElectronica {
	public ConstantsCajaElectronica() {
	}

  public static final String MOVIMIENTO_APERTURA = "A";
  public static final String MOVIMIENTO_CIERRE = "C";
  public static final String MOVIMIENTO_REGISTRO_VENTA = "R";
  
/*-----BEGIN ERIOS BLOCK-----*/
  //DlgCuadraturas
  public static final FarmaColumnData[] columnsListaCuadraturas = {
                  new FarmaColumnData("Id.", 70, JLabel.CENTER),
                  new FarmaColumnData("Desc. Cuadratura", 280, JLabel.LEFT),
                  new FarmaColumnData("Total S/.", 76, JLabel.RIGHT),
                  new FarmaColumnData("Tipo", 0, JLabel.CENTER)
  };

  public static final Object[] defaultValuesListaCuadraturas = { " ", " ", " "," "};

  //DlgDatosCuadratura
  public static final FarmaColumnData[] columnsListaCuadraturaDat = {
                  new FarmaColumnData("Desc.", 200, JLabel.LEFT),
                  new FarmaColumnData("Dato", 170, JLabel.RIGHT),
                  new FarmaColumnData("Codigo", 0, JLabel.CENTER ),
                  new FarmaColumnData("TI_DATO", 0, JLabel.CENTER ),
                  new FarmaColumnData("IN_SOLO_LECTURA", 0, JLabel.CENTER ),
                  new FarmaColumnData("IN_OBLIGATORIO", 0, JLabel.CENTER ) };
  
  
  public static final Object[] defaultValuesListaCuadraturaDat = { " ", " ", " ", " ", " ", " "};
   
  public static final Boolean[] editableListaCuadraturaDat = { new Boolean(false), 
                                                             new Boolean(true),
                                                             new Boolean(false),
                                                             new Boolean(false),
                                                             new Boolean(false),
                                                             new Boolean(false)};
  
  //DlgDatosEfectivoRendido
  public static final FarmaColumnData[] columnsListaEfectivoRendidoDat = {
                  new FarmaColumnData("Desc.", 200, JLabel.LEFT),
                  new FarmaColumnData("Dato", 170, JLabel.RIGHT),
                  new FarmaColumnData("Codigo", 0, JLabel.CENTER ),
                  new FarmaColumnData("TI_DATO", 0, JLabel.CENTER ),
                  new FarmaColumnData("IN_SOLO_LECTURA", 0, JLabel.CENTER ),
                  new FarmaColumnData("IN_OBLIGATORIO", 0, JLabel.CENTER ) };
  
  
  public static final Object[] defaultValuesListaEfectivoRendidoDat = { " ", " ", " ", " ", " ", " "};
   
  public static final Boolean[] editableListaEfectivoRendidoDat = { new Boolean(false), 
                                                             new Boolean(true),
                                                             new Boolean(false),
                                                             new Boolean(false),
                                                             new Boolean(false),
                                                             new Boolean(false)};
  /*------END ERIOS BLOCK------*/
  
  /***************LMESIA******************/
  
  public static final String NOM_HASHTABLE_CMBTURNO = "NOM_HASHTABLE_CMBTURNO";
  public static final String RESULT_BUSCAR_MOV_CAJA_SIN_VB = "1";//SE ENCONTRO MOV CAJA SIN VB DE NI UN TIPO
  public static final String RESULT_BUSCAR_MOV_CAJA_CON_VBC = "2";//SE ENCONTRO MOV CAJA CON SOLO VB DE CAJERO
  public static final String RESULT_BUSCAR_MOV_CAJA_CON_VBQ = "3";//SE ENCONTRO MOV CAJA CON VB DE QF
  public static final String RESULT_BUSCAR_MOV_CAJA_SIN_DATA = "4";//NO SE ENCONTRO MOV CAJA 
  
  public static final int SIN_VB_CAJERO_QF = 0;
  public static final int CON_VB_CAJERO = 1;
  public static final int CON_VB_QF = 2;
  public static final int CON_VB_CIERRE_DIA_QF = 3;
  public static final int SIN_VB_CIERRE_DIA_QF = 4;
  
  public static final String TIP_INGRESO_COMP_CT = "01";
  public static final String TIP_INGRESO_COMP_CD = "02";
  
  public static final String COD_ENT_FINAN_BCP = "001";
  public static final String COD_ENT_FINAN_INTERBANK = "002";
  public static final String COD_ENT_FINAN_SCOTIABANK = "003";
  public static final String COD_ENT_FINAN_FINANCIERO = "004";
  
  public static final String FORMA_PAGO_DSCT_PERSONAL = "00019";
  
  public static final FarmaColumnData[] columnsListaCuadraturasCierreTurno = {
                  new FarmaColumnData("Cod.", 50, JLabel.CENTER),
                  new FarmaColumnData("Desc. Cuadratura", 205, JLabel.LEFT),
                  new FarmaColumnData("Monto S/.", 78, JLabel.RIGHT)
  };

  public static final Object[] defaultValuesListaCuadraturasCierreTurno = { " ", " ", " "};
  
  public static final FarmaColumnData[] columsListaFormasPagoCierre = 
  {
    new FarmaColumnData("Forma Pago",135,JLabel.LEFT),
    new FarmaColumnData("Moneda",70,JLabel.LEFT),
    new FarmaColumnData("Monto",65,JLabel.RIGHT),
    new FarmaColumnData("Total(S/.)",70,JLabel.RIGHT),
    new FarmaColumnData("",0,JLabel.LEFT),//codigo de forma pago
  };
  public static final Object[] defaultListaFormasPagoCierre = {" "," "," "," ",""};
  
  public static final String TIPO_VB_CAJERO = "01";
  public static final String TIPO_VB_QF = "02";
  
  //DlgHistoricoCierreDia
  public static final FarmaColumnData[] columnsListaHistCierreDia = {
    new FarmaColumnData("Día de Cierre", 90, JLabel.CENTER),
    new FarmaColumnData("Usuario", 195, JLabel.LEFT),
    new FarmaColumnData("Fecha V°B°", 110, JLabel.LEFT),
    new FarmaColumnData("Fecha V°B° Cont.", 110, JLabel.CENTER),//ind VB Contable
    new FarmaColumnData("Fecha Proceso", 110, JLabel.LEFT),
    new FarmaColumnData("Fecha Archivo", 110, JLabel.LEFT),
    new FarmaColumnData("", 0, JLabel.LEFT),//sec usu local
    new FarmaColumnData("", 0, JLabel.LEFT),//ind VB cierre dia
    new FarmaColumnData("", 0, JLabel.LEFT),//tipo cambio
    new FarmaColumnData("", 0, JLabel.LEFT),//dia de cierre campo ordenamiento
    /**
     * Indicador de VB Contable
     * @author : dubilluz
     * @since  : 06.09.2007
     */
    new FarmaColumnData("", 0, JLabel.LEFT)//ind VB Contable
  }; 
  public static final Object[] defaultValuesListaHistCierreDia = { " ", " ", " ", " ", " ", "", "", "","",""};
  
  //DlgCierreDia
  public static final FarmaColumnData[] columsListaFormasPago = 
  {
    new FarmaColumnData("Forma Pago",135,JLabel.LEFT),
    new FarmaColumnData("Moneda",70,JLabel.LEFT),
    new FarmaColumnData("Monto",65,JLabel.RIGHT),
    new FarmaColumnData("Total(S/.)",70,JLabel.RIGHT),
    new FarmaColumnData("",0,JLabel.LEFT),//codigo de forma pago
  };
  public static final Object[] defaultListaFormasPago = {" "," "," "," ",""};
  
  public static final FarmaColumnData[] columnsListaCuadraturasCierreDia = {
    new FarmaColumnData("Cod.", 50, JLabel.CENTER),
    new FarmaColumnData("Desc. Cuadratura", 205, JLabel.LEFT),
    new FarmaColumnData("Monto S/.", 78, JLabel.RIGHT)
  };
  public static final Object[] defaultValuesListaCuadraturasCierreDia = { " ", " ", " "};
  
  public static final FarmaColumnData[] columsListaEfectivoRecaudado = 
  {
    new FarmaColumnData("Forma Pago",135,JLabel.LEFT),
    new FarmaColumnData("Moneda",70,JLabel.LEFT),
    new FarmaColumnData("Monto",65,JLabel.RIGHT),
    new FarmaColumnData("Total(S/.)",70,JLabel.RIGHT),
    new FarmaColumnData("",0,JLabel.LEFT),//codigo de forma pago
  };
  public static final Object[] defaultListaEfectivoRecaudado = {" "," "," "," ",""};
  
  public static final FarmaColumnData[] columsListaEfectivoRendido = 
  {
    new FarmaColumnData("Cod.",50,JLabel.LEFT),
    new FarmaColumnData("Descripcion",205,JLabel.LEFT),
    new FarmaColumnData("Monto",78,JLabel.RIGHT),
  };
  public static final Object[] defaultListaEfectivoRendido = {" "," "," "};
  
  public static final FarmaColumnData[] columnsListaCabReclamosProv = {
    new FarmaColumnData("Pedido", 90, JLabel.LEFT),
    new FarmaColumnData("Comp.", 85, JLabel.LEFT),
    new FarmaColumnData("Num Comp.", 90, JLabel.LEFT),
    new FarmaColumnData("Fecha", 130, JLabel.LEFT),
    new FarmaColumnData("Total(S/.)", 80, JLabel.RIGHT),
    new FarmaColumnData("Proveedor", 80, JLabel.LEFT),
    new FarmaColumnData("",0,JLabel.LEFT),
    new FarmaColumnData("",0,JLabel.LEFT)
  };
	public static final Object[] defaultListaCabReclamosProv = {" ", " ", " ", " ", " ", " ", " ", " "};

  public static final FarmaColumnData[] columnsListaDetReclamosProv = {
    new FarmaColumnData("Codigo",60,JLabel.CENTER),
    new FarmaColumnData("Producto",140,JLabel.LEFT),
    new FarmaColumnData("Tipo",68,JLabel.LEFT),
    new FarmaColumnData("Monto",65,JLabel.RIGHT),
    new FarmaColumnData("Cod Aprob.",70,JLabel.CENTER),
    new FarmaColumnData("Tarjeta/Celular",110,JLabel.LEFT),
    new FarmaColumnData("Num Pin",98,JLabel.LEFT),
  };
  public static final Object[] defaultListaDetReclamosProv = {" ", " ", " ", " "," "," "};
  
  public static final FarmaColumnData[] columsListaRangoComprobantes = 
  {
    /*new FarmaColumnData("Tipo Comp.",90,JLabel.LEFT),
    new FarmaColumnData("Num. Serie",90,JLabel.LEFT),
    new FarmaColumnData("Rango Ini.",100,JLabel.LEFT),
    new FarmaColumnData("Rango Fin.",100,JLabel.LEFT),
    new FarmaColumnData("",0,JLabel.LEFT),
    new FarmaColumnData("",0,JLabel.LEFT)*/
    new FarmaColumnData("Tipo Comp.",90,JLabel.LEFT),// 0
    new FarmaColumnData("Num. Serie",90,JLabel.LEFT),// 1
    new FarmaColumnData("Rango Ini.",100,JLabel.LEFT),// 2
    new FarmaColumnData("Rango Fin.",100,JLabel.LEFT),// 3
    new FarmaColumnData("Mont.Ini.",90,JLabel.LEFT),// 4
    new FarmaColumnData("Mont.Fin.",90,JLabel.LEFT),// 5
        new FarmaColumnData("",0,JLabel.LEFT),// 6
        new FarmaColumnData("",0,JLabel.LEFT)     // 7                                       
  };
  public static final Object[] defaultListaRangoComprobantes = {" ", " "," ", " "," "," ", " ", " "};
  
  /***************LMESIA******************/

/******************************PAULO*********************************/
  public static final String CUADRATURA_ANULADO_PENDIENTE = "001";
  public static final String CUADRATURA_REGULARIZACION_ANULADO_PENDIENTE = "002";
  public static final String CUADRATURA_DELIVERY_PENDIENTE  = "003";
  public static final String CUADRATURA_COBRO_DELIVERY_PENDIENTE  = "004";
  public static final String CUADRATURA_ANULADO_DELIVERY_PENDIENTE  = "005";
  public static final String CUADRATURA_COMP_MANUAL = "006";
  public static final String CUADRATURA_REGULARIZACION_COMP_MANUAL = "007";
  public static final String CUADRATURA_DINERO_FALSO = "008";
  public static final String CUADRATURA_ROBO = "009";  
  public static final String CUADRATURA_DEFICIT_CAJERO = "010";  
  public static final String CUADRATURA_DEP_VENTA = "011";
  public static final String CUADRATURA_SERVICIOS_BASICOS = "012";
  public static final String CUADRATURA_REEMBOLSO_CCH = "013";
  public static final String CUADRATURA_PAGO_PLANILLA = "014";
  public static final String CUADRATURA_COTIZA_COMPETENCIA = "015";
  public static final String CUADRATURA_ENTREGAS_RENDIR = "016";
  public static final String CUADRATURA_ROBO_ASALTO = "017";
  public static final String CUADRATURA_DINERO_FALSO_CD = "018";
  public static final String CUADRATURA_OTROS_DESEMBOLSOS = "019";
  public static final String CUADRATURA_FONDO_SENCILLO =  "020";
  public static final String CUADRATURA_DCTO_PERSONAL =  "021";
  public static final String CUADRATURA_DEFICIT_QF = "022";  
  public static final String CUADRATURA_DELIVERY_PERDIDO = "023";  
  public static final String CUADRATURA_ADELANTO = "024";  
  public static final String CUADRATURA_GRATIFICACION = "025";  
  public static final String CUADRATURA_COTIZA_COMPETENCIA_CAJERO = "032"; //ASOSA, 11.08.2010
  

  public static final FarmaColumnData[] columsListaFormasPagoEntrega = 
  {
    new FarmaColumnData("Codigo",80,JLabel.CENTER),
    new FarmaColumnData("Forma de Pago",200,JLabel.LEFT),
    new FarmaColumnData("",0,JLabel.LEFT),//CODIGO OPERADOR TARJETA
    new FarmaColumnData("",0,JLabel.LEFT),//INDICADOR DE TARJETA DE CREDITO
    new FarmaColumnData("",0,JLabel.LEFT),//INDICADOR FORMA PAGO CUPON
    new FarmaColumnData("",0,JLabel.LEFT),//INDICADOR CREDITO
  };
  
  public static final Object[] defaultListaFormasPagoEntrega = {" ", " ", " "};

  
  public static final FarmaColumnData[] columsListaDetallePago = 
  {
    new FarmaColumnData("Codigo",50,JLabel.LEFT),
    new FarmaColumnData("Forma de Pago",140,JLabel.LEFT),
    new FarmaColumnData("Cant",40,JLabel.LEFT),
    new FarmaColumnData("Moneda",75,JLabel.LEFT),
    new FarmaColumnData("Monto",75,JLabel.RIGHT),
    new FarmaColumnData("Total",75,JLabel.RIGHT),
    new FarmaColumnData("Lote",80,JLabel.CENTER),
    
    new FarmaColumnData("Ind.Sobre",60,JLabel.CENTER),
    
    new FarmaColumnData("Sobre",100,JLabel.CENTER),
    new FarmaColumnData("",0,JLabel.LEFT),//sec
    new FarmaColumnData("",0,JLabel.LEFT),//mon
    new FarmaColumnData("",0,JLabel.LEFT),//ind
    new FarmaColumnData("",0,JLabel.LEFT),//indicador automatico
    new FarmaColumnData("",0,JLabel.LEFT)//SecFormaPagoORigen DUBILLUZ 14.01.2009
    //new FarmaColumnData("",0,JLabel.LEFT),//indicador si se asociara a Sobre DUBILLUZ 14.01.2009
  };
  
  public static final Object[] defaultListaDetallePago = {" "," ", " "," "," "," "," "," "," "," "," "," "};
  
  public static final FarmaColumnData[] columsListaCuadraturasIngreso = 
  {
    new FarmaColumnData("Sel", 30, JLabel.CENTER),
    new FarmaColumnData("Tipo",68,JLabel.LEFT),
    new FarmaColumnData("Comprobante",80,JLabel.LEFT),
    new FarmaColumnData("Fecha",120,JLabel.LEFT),
    new FarmaColumnData("Importe",60,JLabel.RIGHT),
    new FarmaColumnData("Importe Total",85,JLabel.RIGHT),
    new FarmaColumnData("Vuelto",80,JLabel.RIGHT),
    new FarmaColumnData("",0,JLabel.LEFT),//TIP
    new FarmaColumnData("",0,JLabel.LEFT),//SERIE
    new FarmaColumnData("",0,JLabel.LEFT),//COMP
  };
  public static final Object[] defaultListaCuadraturasIngreso = {" ", " "," "," "," "," "," "," "," "," " };
  
  public static final FarmaColumnData[] columsListaEliminacionDineroFalso = 
  {
    new FarmaColumnData("Sel", 30, JLabel.CENTER),
    new FarmaColumnData("Tipo Dinero",100,JLabel.LEFT),
    new FarmaColumnData("Tipo Moneda",100,JLabel.LEFT),
    new FarmaColumnData("Importe",85,JLabel.RIGHT),
    new FarmaColumnData("Serie",80,JLabel.LEFT),
    new FarmaColumnData("",0, JLabel.RIGHT), //relleno
    new FarmaColumnData("",0, JLabel.RIGHT), //relleno
    new FarmaColumnData("",0, JLabel.RIGHT), //relleno
    new FarmaColumnData("",0,JLabel.RIGHT),//sec
    new FarmaColumnData("",0,JLabel.LEFT),//vb
    new FarmaColumnData("",0,JLabel.LEFT),//secC
  };
  public static final Object[] defaultListaEliminacionDineroFalso = {" "," "," "," "," "," "," "," "," "," "," " };
  
  public static final FarmaColumnData[] columsListaEliminacionRobo =
  {
    new FarmaColumnData("Sel", 30, JLabel.CENTER),
    new FarmaColumnData("Codigo",80,JLabel.LEFT),
    new FarmaColumnData("Descripcion",100,JLabel.RIGHT),
    new FarmaColumnData("Importe",80,JLabel.LEFT),
    new FarmaColumnData("Total",80,JLabel.LEFT),
    new FarmaColumnData("",0, JLabel.RIGHT), //relleno
    new FarmaColumnData("",0, JLabel.RIGHT), //relleno
    new FarmaColumnData("",0, JLabel.RIGHT), //relleno
    new FarmaColumnData("",0,JLabel.RIGHT),//sec
    new FarmaColumnData("",0,JLabel.LEFT),//vb
    new FarmaColumnData("",0,JLabel.LEFT),//secC
  };
  public static final Object[] defaultListaEliminacionRobo = {" ", " "," "," "," "," "," "," "," "," "," " };

  public static final FarmaColumnData[] columsListaEliminacionOtrasCuadraturas =
  {
    new FarmaColumnData("Sel", 30, JLabel.CENTER),
    new FarmaColumnData("Tipo",80,JLabel.LEFT),
    new FarmaColumnData("Serie",50,JLabel.RIGHT),
    new FarmaColumnData("Comprobante",80,JLabel.LEFT),
    new FarmaColumnData("Importe",80,JLabel.LEFT),
    new FarmaColumnData("Importe Total",85,JLabel.LEFT),
    new FarmaColumnData("Vuelto",80,JLabel.LEFT),
    new FarmaColumnData("",0,JLabel.RIGHT),//tip
    new FarmaColumnData("",0,JLabel.LEFT),//sec
    new FarmaColumnData("",0,JLabel.LEFT),//vb
    new FarmaColumnData("",0,JLabel.LEFT),//secC
  };
  public static final Object[] defaultListaEliminacionOtrasCuadraturas = {" "," ", " "," "," "," "," "," "," "," "," " };
  
  //DlgEfectivoRendido
  public static final FarmaColumnData[] columnsListaEfectivoRendido = {
                  new FarmaColumnData("Id.", 70, JLabel.CENTER),
                  new FarmaColumnData("Desc. Efectivo Rendido", 300, JLabel.LEFT),
                  new FarmaColumnData("Total S/.", 76, JLabel.RIGHT),
                  new FarmaColumnData("", 0, JLabel.LEFT)
  };
  public static final Object[] defaultValuesListaEfectivoRendido = { " ", " ", " "," "};
  
  //DlgCotizacionCompetencia
  public static final FarmaColumnData[] columnsListaCotizacionCompetencia = {
                  new FarmaColumnData("Sel", 30, JLabel.CENTER),
                  new FarmaColumnData("Tipo", 85, JLabel.CENTER),
                  new FarmaColumnData("Comprobante", 100, JLabel.LEFT),
                  new FarmaColumnData("Total S/.", 80, JLabel.RIGHT),
                  new FarmaColumnData("N. Guia", 80, JLabel.LEFT),
                  new FarmaColumnData("Fecha", 90, JLabel.LEFT),
                  new FarmaColumnData("Empresa", 90, JLabel.CENTER),
                  new FarmaColumnData("",0, JLabel.LEFT),//TipoComprobante
  };
  public static final Object[] defaultValuesListaCotizacionCompetencia = {" ", " ", " ", " "," "," "," "};
  
  //DlgListaEliminacionEfectivoRendido
  public static final FarmaColumnData[] columnsEliminacionDepositoporVenta = {
                  new FarmaColumnData("Sel", 30, JLabel.CENTER),
                  new FarmaColumnData("Forma Pago", 125, JLabel.LEFT),
                  new FarmaColumnData("T.Moneda", 60, JLabel.LEFT),
                  new FarmaColumnData("Importe", 80, JLabel.RIGHT),
                  new FarmaColumnData("I. Total S/.",85, JLabel.RIGHT),
                  new FarmaColumnData("E.Financiera", 110, JLabel.CENTER),
                  new FarmaColumnData("F.Operacion",120, JLabel.LEFT),
                  new FarmaColumnData("N.Operacion",100, JLabel.LEFT),
                  new FarmaColumnData("N.Cuenta", 120, JLabel.LEFT),
                  new FarmaColumnData("Agencia",90, JLabel.LEFT),
                  new FarmaColumnData("Glosa",280, JLabel.LEFT),
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.LEFT),//sec
                  new FarmaColumnData("",0, JLabel.LEFT),//feccierre
  };
  public static final Object[] defaultValuesEliminacionDepositoporVenta = {" "," "," ", " ", " ", " ", " "," "," ", " ", " "," "," "," "};
  
  public static final FarmaColumnData[] columnsEliminacionServiciosBasicos = {
                  new FarmaColumnData("Sel", 30, JLabel.CENTER),
                  new FarmaColumnData("Servicio",90, JLabel.LEFT),
                  new FarmaColumnData("Proveedor",120, JLabel.LEFT),
                  new FarmaColumnData("Documento", 80, JLabel.LEFT),
                  new FarmaColumnData("T.Moneda", 80, JLabel.LEFT),
                  new FarmaColumnData("Importe", 80, JLabel.RIGHT),
                  new FarmaColumnData("I. Total S/.",85, JLabel.RIGHT),
                  new FarmaColumnData("F.Documento",90, JLabel.LEFT),
                  new FarmaColumnData("F.Operacion",120, JLabel.LEFT),
                  new FarmaColumnData("F.Vencimiento",90, JLabel.LEFT),
                  new FarmaColumnData("Titular", 90, JLabel.LEFT),
                  new FarmaColumnData("Glosa",150, JLabel.LEFT),
                  new FarmaColumnData("",0, JLabel.LEFT),//sec
                  new FarmaColumnData("",0, JLabel.LEFT),//feccierre
  };
  public static final Object[] defaultValuesEliminacionServiciosBasicos = {" "," "," ", " ", " ", " ", " "," "," ", " ", " "," "," "," "};

  public static final FarmaColumnData[] columnsEliminacionReembolsoCajaCh = {
                  new FarmaColumnData("Sel", 30, JLabel.CENTER),
                  new FarmaColumnData("T.Moneda", 80, JLabel.LEFT),
                  new FarmaColumnData("Importe", 80, JLabel.RIGHT),
                  new FarmaColumnData("I. Total S/.",85, JLabel.RIGHT),
                  new FarmaColumnData("Cod.Autorizacion",150, JLabel.LEFT),
                  new FarmaColumnData("Glosa", 150, JLabel.LEFT),
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.LEFT),//sec
                  new FarmaColumnData("",0, JLabel.LEFT),//feccierre
  };
  public static final Object[] defaultValuesEliminacionReembolsoCajaCh = {" "," "," ", " ", " ", " ", " "," "," ", " ", " "," "," "," "};
  
  public static final FarmaColumnData[] columnsEliminacionPagoPlanilla = {
                  new FarmaColumnData("Sel", 30, JLabel.CENTER),
                  new FarmaColumnData("Moneda", 80, JLabel.LEFT),
                  new FarmaColumnData("Importe", 80, JLabel.RIGHT),
                  new FarmaColumnData("I. Total S/.",85, JLabel.RIGHT),
                  new FarmaColumnData("Trabajador Local",180, JLabel.LEFT),
                  new FarmaColumnData("C.Autorizacion",120, JLabel.LEFT),
                  new FarmaColumnData("Glosa", 150, JLabel.LEFT),
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.LEFT),//sec
                  new FarmaColumnData("",0, JLabel.LEFT),//feccierre
  };
  public static final Object[] defaultValuesEliminacionPagoPlanilla = {" "," "," ", " ", " ", " ", " "," "," ", " ", " "," "," "," "};
  
  public static final FarmaColumnData[] columnsEliminacionCotizacionCompetencia = {
                  new FarmaColumnData("Sel", 30, JLabel.CENTER),
                  new FarmaColumnData("Documento", 80, JLabel.LEFT),
                  new FarmaColumnData("Tipo", 80, JLabel.LEFT),
                  new FarmaColumnData("Fecha", 90, JLabel.LEFT),
                  new FarmaColumnData("Total", 80, JLabel.LEFT),
                  new FarmaColumnData("I. Total S/.",85, JLabel.RIGHT),
                  new FarmaColumnData("Glosa",150, JLabel.LEFT), 
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.LEFT),//nota
                  new FarmaColumnData("",0, JLabel.LEFT),//sec
                  new FarmaColumnData("",0, JLabel.LEFT),//feccierre
                  
  };
  public static final Object[] defaultValuesEliminacionCotizacionCompetencia = {" "," "," ", " ", " ", " ", " "," "," ", " ", " "," "," "," "};
  
  public static final FarmaColumnData[] columnsEliminacionEntregasRendir = {
                  new FarmaColumnData("Sel", 30, JLabel.CENTER),
                  new FarmaColumnData("Moneda", 80, JLabel.LEFT),
                  new FarmaColumnData("Importe", 80, JLabel.RIGHT),
                  new FarmaColumnData("I. Total S/.",85, JLabel.RIGHT),
                  new FarmaColumnData("F.Operacion",120, JLabel.LEFT),                                    
                  new FarmaColumnData("Trabajador",180, JLabel.LEFT),
                  new FarmaColumnData("C.Autorizacion",120, JLabel.LEFT),
                  new FarmaColumnData("Glosa",150, JLabel.LEFT),
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.LEFT),//sec
                  new FarmaColumnData("",0, JLabel.LEFT),//feccierre
  };
  public static final Object[] defaultValuesEliminacionEntregasRendir = {" "," "," ", " ", " ", " ", " "," "," ", " ", " "," "," "," "};
  
  public static final FarmaColumnData[] columnsEliminacionRoboPorAsalto = {
                  new FarmaColumnData("Sel", 30, JLabel.CENTER),
                  new FarmaColumnData("Forma Pago",150, JLabel.LEFT),
                  new FarmaColumnData("Moneda", 80, JLabel.LEFT),
                  new FarmaColumnData("Importe", 80, JLabel.RIGHT),
                  new FarmaColumnData("I. Total S/.",85, JLabel.RIGHT),
                  new FarmaColumnData("Glosa.",150, JLabel.LEFT),
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.LEFT),//sec
                  new FarmaColumnData("",0, JLabel.LEFT),//feccierre
  };
  public static final Object[] defaultValuesEliminacionRoboPorAsalto = {" "," "," ", " ", " ", " ", " "," "," ", " ", " "," "," "," "};
  
  public static final FarmaColumnData[] columnsEliminacionDineroFalso = {
                  new FarmaColumnData("Sel", 30, JLabel.CENTER),
                  new FarmaColumnData("T. Dinero", 80, JLabel.LEFT),
                  new FarmaColumnData("T. Moneda", 80, JLabel.LEFT),
                  new FarmaColumnData("Importe", 80, JLabel.RIGHT),
                  new FarmaColumnData("I. Total S/.",85, JLabel.RIGHT),
                  new FarmaColumnData("Serie",130, JLabel.LEFT),
                  new FarmaColumnData("I. Parcial", 82, JLabel.RIGHT),
                  new FarmaColumnData("Trabajador Local", 150, JLabel.LEFT),
                  new FarmaColumnData("Glosa.",150, JLabel.LEFT),
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.LEFT),//sec
                  new FarmaColumnData("",0, JLabel.LEFT),//feccierre
  };
  public static final Object[] defaultValuesEliminacionDineroFalso = {" "," "," ", " ", " ", " ", " "," "," ", " ", " "," "," "," "};
  
  public static final FarmaColumnData[] columnsEliminacionOtrosDesembolsos = {
                  new FarmaColumnData("Sel", 30, JLabel.CENTER),
                  new FarmaColumnData("T.Documento", 85, JLabel.CENTER),
                  new FarmaColumnData("Documento",130, JLabel.LEFT),
                  new FarmaColumnData("T. Moneda", 80, JLabel.LEFT),
                  new FarmaColumnData("Importe.", 75, JLabel.RIGHT),
                  new FarmaColumnData("I. Total S/.",85, JLabel.RIGHT),
                  new FarmaColumnData("F.Documento", 90, JLabel.LEFT),
                  new FarmaColumnData("F.Operacion", 120, JLabel.LEFT),
                  new FarmaColumnData("Ruc", 90, JLabel.LEFT),
                  new FarmaColumnData("Razon Social", 120, JLabel.LEFT),
                  new FarmaColumnData("Glosa.",150, JLabel.LEFT),
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.LEFT),//sec
                  new FarmaColumnData("",0, JLabel.LEFT),//feccierre
  };
  public static final Object[] defaultValuesEliminacionOtrosDesembolsos = {" "," "," ", " ", " ", " ", " "," "," ", " ", " "," "," "," "};
  
  public static final FarmaColumnData[] columnsEliminacionFondoLocalNuevo = {
                  new FarmaColumnData("Sel", 30, JLabel.CENTER),
                  new FarmaColumnData("T. Moneda", 80, JLabel.LEFT),
                  new FarmaColumnData("Importe", 80, JLabel.RIGHT),
                  new FarmaColumnData("I. Total S/.",85, JLabel.RIGHT),
                  new FarmaColumnData("Local", 130, JLabel.CENTER),
                  new FarmaColumnData("C.Autorizacion", 120, JLabel.LEFT),
                  new FarmaColumnData("Glosa.",150, JLabel.LEFT),
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.LEFT),//sec
                  new FarmaColumnData("",0, JLabel.LEFT),//feccierre
  };
  public static final Object[] defaultValuesEliminacionFondoLocalNuevo = {" "," "," ", " ", " ", " ", " "," "," ", " ", " "," "," "," "};
  
  public static final FarmaColumnData[] columnsEliminacionDctoPersonal = {
                  new FarmaColumnData("Sel", 30, JLabel.CENTER),
                  new FarmaColumnData("T.Comprobante", 80, JLabel.LEFT),
                  new FarmaColumnData("N. Pedido",120, JLabel.LEFT),
                  new FarmaColumnData("T. Moneda", 80, JLabel.LEFT),               
                  new FarmaColumnData("Importe", 80, JLabel.RIGHT),
                  new FarmaColumnData("I. Total S/.",85, JLabel.RIGHT),
                  new FarmaColumnData("I. Parcial", 82, JLabel.RIGHT),
                  new FarmaColumnData("Trabajador Local", 150, JLabel.LEFT),
                  new FarmaColumnData("Glosa.",150, JLabel.LEFT),
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.LEFT),//sec
                  new FarmaColumnData("",0, JLabel.LEFT),//feccierre
  };
  public static final Object[] defaultValuesEliminacionDctoPersonal = {" "," "," ", " ", " ", " ", " "," "," ", " ", " "," "," "," "};
  
  public static final FarmaColumnData[] columsListaDetalleDineroFalso = 
  {
    new FarmaColumnData("Usuario", 70, JLabel.CENTER),
    new FarmaColumnData("Nombre", 90, JLabel.CENTER),
    new FarmaColumnData("Caja",50,JLabel.CENTER),
    new FarmaColumnData("Turno",50, JLabel.CENTER), 
    new FarmaColumnData("Tipo Dinero",100,JLabel.LEFT),
    new FarmaColumnData("Tipo Moneda",100,JLabel.LEFT),
    new FarmaColumnData("Importe",85,JLabel.RIGHT),
    new FarmaColumnData("Importe Total S/.",100,JLabel.RIGHT),
    new FarmaColumnData("Serie",80,JLabel.LEFT),
  };
  public static final Object[] defaultListaDetalleDineroFalso = {" "," "," "," "," "," "," "," "};
  
  public static final FarmaColumnData[] columsListaDetalleRobo =
  {
    new FarmaColumnData("Usuario", 70, JLabel.CENTER),
    new FarmaColumnData("Nombre", 90, JLabel.CENTER),
    new FarmaColumnData("Caja",50,JLabel.CENTER),
    new FarmaColumnData("Turno",50, JLabel.CENTER), 
    new FarmaColumnData("Forma Pago",125,JLabel.LEFT),
    new FarmaColumnData("Importe",80,JLabel.RIGHT),
    new FarmaColumnData("Importe Total S/.",100,JLabel.RIGHT),
  };
  public static final Object[] defaultListaDetalleRobo = {" ", " "," "," "," "," "};
  
  public static final FarmaColumnData[] columsListaDetalleDeficitCajero =
  {
    new FarmaColumnData("Usuario", 70, JLabel.CENTER),
    new FarmaColumnData("Nombre", 90, JLabel.CENTER),
    new FarmaColumnData("Caja",50,JLabel.CENTER),
    new FarmaColumnData("Turno",50, JLabel.CENTER), 
    new FarmaColumnData("Importe",80,JLabel.RIGHT),
    new FarmaColumnData("Importe Total S/.",100,JLabel.RIGHT),
  };
  public static final Object[] defaultListaDetalleDeficitCajero = {" ", " "," "," "," "};

  public static final FarmaColumnData[] columsListaDetalleOtrasCuadraturas =
  {
    new FarmaColumnData("Usuario", 70, JLabel.CENTER),
    new FarmaColumnData("Nombre", 90, JLabel.CENTER),
    new FarmaColumnData("Caja",50,JLabel.CENTER),
    new FarmaColumnData("Turno",50, JLabel.CENTER), 
    new FarmaColumnData("Tipo",80,JLabel.LEFT),
    new FarmaColumnData("Serie",50,JLabel.CENTER),
    new FarmaColumnData("Comprobante",80,JLabel.LEFT),
    new FarmaColumnData("Importe",80,JLabel.RIGHT),
    new FarmaColumnData("Importe Total S/.",100,JLabel.RIGHT),
    new FarmaColumnData("Vuelto",80,JLabel.RIGHT),
  };
  public static final Object[] defaultListaDetalleOtrasCuadraturas = {" "," ", " "," "," "," "," "," "," "};
  
  public static final FarmaColumnData[] columsListaDetalleFormasPago =
  {
    new FarmaColumnData("Usuario", 50, JLabel.CENTER),
    new FarmaColumnData("Nombre", 90, JLabel.CENTER),
    new FarmaColumnData("Caja",50,JLabel.CENTER),
    new FarmaColumnData("Turno",50, JLabel.CENTER), 
    new FarmaColumnData("Voucher",75,JLabel.RIGHT),
    new FarmaColumnData("Importe",80,JLabel.RIGHT),
    new FarmaColumnData("Importe Total S/.",100,JLabel.RIGHT),
    
  };
  public static final Object[] defaultListaDetalleFormasPago = {" "," ", " "," "," "," "};
  
  public static final FarmaColumnData[] columnsEliminacionAdelanto = {
                  new FarmaColumnData("Sel", 30, JLabel.CENTER),
                  new FarmaColumnData("Moneda", 80, JLabel.LEFT),
                  new FarmaColumnData("Importe", 80, JLabel.RIGHT),
                  new FarmaColumnData("I. Total S/.",85, JLabel.RIGHT),
                  new FarmaColumnData("Trabajador Local",180, JLabel.LEFT),
                  new FarmaColumnData("C.Autorizacion",120, JLabel.LEFT),
                  new FarmaColumnData("Glosa", 150, JLabel.LEFT),
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.LEFT),//sec
                  new FarmaColumnData("",0, JLabel.LEFT),//feccierre
  };
  public static final Object[] defaultValuesEliminacionAdelanto = {" "," "," ", " ", " ", " ", " "," "," ", " ", " "," "," "," "};

  public static final FarmaColumnData[] columnsEliminacionGratificacion = {
                  new FarmaColumnData("Sel", 30, JLabel.CENTER),
                  new FarmaColumnData("Moneda", 80, JLabel.LEFT),
                  new FarmaColumnData("Importe", 80, JLabel.RIGHT),
                  new FarmaColumnData("I. Total S/.",85, JLabel.RIGHT),
                  new FarmaColumnData("Trabajador Local",180, JLabel.LEFT),
                  new FarmaColumnData("C.Autorizacion",120, JLabel.LEFT),
                  new FarmaColumnData("Glosa", 150, JLabel.LEFT),
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.LEFT),//sec
                  new FarmaColumnData("",0, JLabel.LEFT),//feccierre
  };
  public static final Object[] defaultValuesEliminacionGratificacion = {" "," "," ", " ", " ", " ", " "," "," ", " ", " "," "," "," "};


  public static final FarmaColumnData[] columnsEliminacionDeliveryPerdido = {
                  new FarmaColumnData("Sel", 30, JLabel.CENTER),
                  new FarmaColumnData("Tipo", 80, JLabel.LEFT),
                  new FarmaColumnData("Comprobante",120, JLabel.LEFT),
                  new FarmaColumnData("T. Moneda", 80, JLabel.LEFT),               
                  new FarmaColumnData("Importe", 80, JLabel.RIGHT),
                  new FarmaColumnData("I. Total S/.",85, JLabel.RIGHT),
                  new FarmaColumnData("I.Perdido S/.",85, JLabel.RIGHT),
                  new FarmaColumnData("Proveedor",120, JLabel.LEFT),
                  new FarmaColumnData("Glosa", 150, JLabel.LEFT),
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                  new FarmaColumnData("",0, JLabel.LEFT),//sec
                  new FarmaColumnData("",0, JLabel.LEFT),//feccierre
  };
  public static final Object[] defaultValuesEliminacionDeliveryPerdido = {" "," "," ", " ", " ", " ", " "," "," ", " ", " "," "," "," "};


  
  public static final int INDICE_COLUMNA_SEC_CUAD_CD = 12 ; 
  public static final int INDICE_COLUMNA_FECHA_CUAD_CD = 13 ; 
  public static final int INDICE_COLUMNA_SEC_MOV_CAJA = 8 ; 
  public static final int INDICE_COLUMNA_IND_VB = 9 ; 
  public static final int INDICE_COLUMNA_SEC_CUADRATURA = 10 ; 
  
  public static final String  CAMPO_FORMA_PAGO = "001" ;
  public static final String  CAMPO_TIPO_SERVICIO = "002" ;
  public static final String  CAMPO_NOMBRE_PROVEEDOR = "003" ;
  public static final String  CAMPO_TIPO_DOCUMENTO = "004" ;
  public static final String  CAMPO_SERIE_DOCUMENTO = "005" ;
  public static final String  CAMPO_NUMERO_DOCUMENTO = "006" ;
  public static final String  CAMPO_TIPO_COMPROBANTE = "007" ;
  public static final String  CAMPO_SERIE_COMPROBANTE = "008" ;
  public static final String  CAMPO_NUMERO_COMPROBANTE = "009" ;
  public static final String  CAMPO_TIPO_DINERO = "010" ;
  public static final String  CAMPO_TIPO_MONEDA = "011" ;
  public static final String  CAMPO_IMPORTE = "012" ;
  public static final String  CAMPO_VUELTO = "013" ;
  public static final String  CAMPO_ENTIDAD_FINANCIERA = "014" ;
  public static final String  CAMPO_NUMERO_CUENTA = "015" ;
  public static final String  CAMPO_FECHA_DOCUMENTO = "016" ;
  public static final String  CAMPO_FECHA_OPERACION = "017" ;
  public static final String  CAMPO_FECHA_VENCIMIENTO = "018" ;
  public static final String  CAMPO_NUMERO_OPERACION = "019" ;
  public static final String  CAMPO_SERIE_DINERO = "020" ;
  public static final String  CAMPO_NOMBRE_AGENCIA = "021" ;
  public static final String  CAMPO_TITULAR = "022" ;
  public static final String  CAMPO_IMPORTE_PARCIAL = "023" ;
  public static final String  CAMPO_TRABAJADOR = "024" ;
  public static final String  CAMPO_RUC = "025" ;
  public static final String  CAMPO_RAZON_SOCIAL = "026" ;
  public static final String  CAMPO_LOCAL_NUEVO = "027" ;
  public static final String  CAMPO_TRABAJADOR_LOCAL = "028" ;
  public static final String  CAMPO_CODIGO_AUTORIZACION = "029" ;
  public static final String  CAMPO_GLOSA = "030" ;
  public static final String  CAMPO_MONTO_PERDIDO = "031" ;
  public static final String  CAMPO_NUMERO_PEDIDO = "032" ;
  public static final String  CODIGO_SERVICIO_DELIVERY = "003" ;
  public static final int CON_VB_CONTABLE = 6;
  public static final String  CODIGO_FP_SOLES = "00001" ;
  public static final String  CODIGO_FP_DOLARES = "00002";
  public static final String  IND_DEBITO = "DB";
  
  
  /******************************PAULO*********************************/
  
 /**
  * Columnas para lista de trabajadores y se mostrara el codigo de RR.HH
  * @author dubilluz
  * @since  22.11.2007
  */
  public static final FarmaColumnData columnsListaTrabajadore[] = {
    new FarmaColumnData( "",3, JLabel.CENTER ),
	  new FarmaColumnData( "Descripcion", 250, JLabel.LEFT ),
    new FarmaColumnData( "Codigo", 70, JLabel.CENTER )
	};  
  public static final Object[] defaultValuesListaTrabajadore = {" ", " "," "};  
  
  
  /**************************Cambio en formas de PAgo*****************************/

  public static final FarmaColumnData columnsListaCambioFormaPago[] = {
    new FarmaColumnData( "Nro.Pedido", 85, JLabel.CENTER ),
    new FarmaColumnData( "T", 20, JLabel.CENTER ),
    new FarmaColumnData( "No Comprobante", 100, JLabel.CENTER ),
    new FarmaColumnData( "Fecha", 120, JLabel.CENTER),
    new FarmaColumnData( "Cliente", 180, JLabel.LEFT ),    
    new FarmaColumnData( "Estado", 85, JLabel.LEFT ),    
    new FarmaColumnData( "Monto", 0, JLabel.RIGHT)    
    };
        
    public static final Object[] defaultValuesListaCambioFormaPago = {" "," "," ", " ", " ", " "," "," "};
    
    
      public static final FarmaColumnData columnsListaFormasPago[] = {
      new FarmaColumnData("Código", 60, JLabel.CENTER ),
      new FarmaColumnData("Descripción", 180, JLabel.LEFT ),
      new FarmaColumnData("Importe", 0, JLabel.RIGHT ),
      new FarmaColumnData("Moneda", 0, JLabel.LEFT ),
      new FarmaColumnData("Vuelto", 0, JLabel.RIGHT ),
      new FarmaColumnData("Importe Total", 0, JLabel.RIGHT ),
      new FarmaColumnData("Cajero",0 , JLabel.LEFT)
      //new FarmaColumnData("",0, JLabel.LEFT),//TipCambio
     // new FarmaColumnData("",0, JLabel.LEFT)//indTarjeta
      };
    
    public static final Object[] defaultValuesListaFormasPago = {" "," "," "," "," "," "," "};
    
    public static String NOM_HASTABLE_CMBFILTROVENTA = "CMB_FILTRO_VENTA";
    public static final String[] vCodColumna ={ "X","S", "N"};
    public static final String[] vDescColumna ={ "Seleccione","Tarjeta", "Efectivo"};
    
    public static final FarmaColumnData columnsListaFormasPago2[] = {
    new FarmaColumnData("Código", 50, JLabel.CENTER ),
    new FarmaColumnData("Descripción", 180, JLabel.LEFT ),
    new FarmaColumnData("Monto", 80, JLabel.RIGHT ),
    new FarmaColumnData("Total", 80, JLabel.LEFT )};
    
    public static final Object[] defaultValuesListaFormasPago2 = {" "," "," "," "};
    
    public static final FarmaColumnData[] columnsEliminacionCotizacionCompetencia_Turno = {   //ASOSA, 12.08.2010
                    new FarmaColumnData("Sel", 30, JLabel.CENTER),
                    new FarmaColumnData("Documento", 80, JLabel.LEFT),
                    new FarmaColumnData("Tipo", 80, JLabel.LEFT),
                    new FarmaColumnData("Fecha", 90, JLabel.LEFT),
                    new FarmaColumnData("Total", 80, JLabel.LEFT),
                    new FarmaColumnData("I. Total S/.",85, JLabel.RIGHT),
                    new FarmaColumnData("Glosa",150, JLabel.LEFT), 
                    new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                    new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                    new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                    new FarmaColumnData("",0, JLabel.RIGHT), //relleno
                    new FarmaColumnData("",0, JLabel.LEFT),//nota
                    new FarmaColumnData("",0, JLabel.LEFT),//sec
                    new FarmaColumnData("",0, JLabel.LEFT),//feccierre
    };
    public static final Object[] defaultValuesEliminacionCotizacionCompetencia_Turno = {" "," "," ", " ", " ", " ", " "," "," ", " ", " "," "," "," "};   //ASOSA, 12.08.2010
    
    /*Fondo de Sencillo*/
    public static final String      TIT_RECIBO_SENCILLO         = "Recibo de Sencillo";
    public static final String      TIT_PAGO_SENCILLO           = "Pago de Sencillo";
    public static final String      HASHTABLE_TIPOS_ETV         = "TIPOS_ETV";    
    public static final String[]    TIPOS_ETV_CODIGO            = {"1","2"};
    public static final String[]    TIPOS_ETV_DESCRIPCION       = {"HERMES","PROSEGUR"};
    public static final String      PRESIONE_ESC                =  "Debe presionar la tecla ESC para cerrar la ventana.";
    public static final String      PAGO_SENCILLO_PENDIENTE     =  "Tiene un Pago de Sencillo pendiente";
    public static final String      NO_PAGO_SENCILLO_PENDIENTE  =  "No tiene Pago de Sencillo pendiente";
    public static final String      OCURRIO_ERROR_CARGAR        =  "Ocurrió un error al cargar información. Consulte con el administrador";
    public static final String      EXITO_GRABAR                =  "Se grabaron los datos exitosamente";
    public static final String      OCURRIO_ERROR_GRABAR        =  "Ocurrió un error al grabar los datos.";
    public static final String      FALTA_INFO                  =  "Falta Ingresar Información.";
    public static final String      MONTO_MAYOR_TOTAL           =  "El Monto ingresado supera al total. Verifique";
    public static final String      CONFIRM_GRABAR              =  "¿Está seguro de grabar los datos?";
    public static final char        TIPO_SENCILLO_RECIBO        =  '1';
    public static final char        TIPO_SENCILLO_PAGO          =  '2';
    
    //LLEIVA 04-Oct-2013 CONSTANTES PARA GUARDAR CONCILIACION DE VENTAS Y ANULACIONES
    public static final String GUARD_CONC_VENTA_PINPAD_VISA = "VPV";
    public static final String GUARD_CONC_VENTA_PINPAD_MASTERCARD = "VPM";
    public static final String GUARD_CONC_VENTA_PINPAD_DINERS = "VPD";
    public static final String GUARD_CONC_VENTA_PINPAD_AMEX = "VPA";
    public static final String GUARD_CONC_ANULACION_PINPAD_VISA = "APV";
    public static final String GUARD_CONC_ANULACION_PINPAD_MASTERCARD = "APM";
    public static final String GUARD_CONC_ANULACION_PINPAD_DINERS = "APD";
    public static final String GUARD_CONC_ANULACION_PINPAD_AMEX = "APA";

    public static final FarmaColumnData[] columnsListaAnulPendRegularizar = {
    new FarmaColumnData("Fec.Dia Vta", 70, JLabel.CENTER),
    new FarmaColumnData("Usuario", 70, JLabel.CENTER),
    new FarmaColumnData("Nombre",120, JLabel.CENTER),
    new FarmaColumnData("Caja",50,JLabel.CENTER),
    new FarmaColumnData("Turno",50, JLabel.CENTER), 
    new FarmaColumnData("Tipo",80,JLabel.LEFT),
    new FarmaColumnData("Serie",50,JLabel.CENTER),
    new FarmaColumnData("Comprobante",80,JLabel.LEFT),
    new FarmaColumnData("Importe",80,JLabel.RIGHT),
    new FarmaColumnData("Importe Total S/.",100,JLabel.RIGHT)
    };
    
    public static final Object[] defaultValuesListaAnulPendRegularizar = {" "," "," ", " ", " "," "," "," "," "," "};
    
    
}
