package mifarma.ptoventa.reportes.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;
import mifarma.common.FarmaConstants;


public class ConstantsReporte {
    public ConstantsReporte() {
    }
    public static final FarmaColumnData columnsListaRegistroVentas[] =
    { new FarmaColumnData("Correlativo", 85, JLabel.CENTER), new FarmaColumnData("T", 25, JLabel.CENTER),
      new FarmaColumnData("No Comprobante", 100, JLabel.CENTER), new FarmaColumnData("Fecha", 120, JLabel.CENTER),
      new FarmaColumnData("Cliente", 180, JLabel.LEFT), new FarmaColumnData("Estado", 80, JLabel.LEFT),
      new FarmaColumnData("Monto", 100, JLabel.RIGHT),
        //    new FarmaColumnData( "Direccion", 100, JLabel.LEFT ),
        new FarmaColumnData("Ruc", 100, JLabel.RIGHT), new FarmaColumnData("Hora", 0, JLabel.RIGHT),
        new FarmaColumnData("Usuario", 0, JLabel.RIGHT), new FarmaColumnData("Estado", 0, JLabel.RIGHT),
        new FarmaColumnData("Fecha", 0, JLabel.CENTER), new FarmaColumnData("T/Compr", 0, JLabel.CENTER), };

    public static final Object[] defaultValuesListaRegistroVentas =
    { " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };

    /**
     * datos a mostrar en el detalle del reporte de los pedidos de venta
     * @autor jcallo
     * @since 14.10.2008
     * **/
    public static final FarmaColumnData columnsListaDetalleVentas[] =
    { new FarmaColumnData("Codigo", 50, JLabel.CENTER), new FarmaColumnData("Cant", 40, JLabel.RIGHT),
      new FarmaColumnData("Descripcion", 180, JLabel.LEFT), new FarmaColumnData("Unidad", 90, JLabel.LEFT),
      new FarmaColumnData("Laboratorio", 120, JLabel.LEFT), new FarmaColumnData("P.Unit", 70, JLabel.RIGHT),
      new FarmaColumnData("Dscto", 70, JLabel.RIGHT), new FarmaColumnData("Importe", 100, JLabel.RIGHT),
      new FarmaColumnData("Total", 0, JLabel.RIGHT), new FarmaColumnData("Bruto", 0, JLabel.RIGHT),
      new FarmaColumnData("Dcto", 0, JLabel.RIGHT), new FarmaColumnData("Igv", 0, JLabel.RIGHT),
      new FarmaColumnData("Neto", 0, JLabel.RIGHT), new FarmaColumnData("", 0, JLabel.RIGHT), //usuario
        } ;

    public static final Object[] defaultValuesListaDetalleVentas =
    { " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };

    public static final FarmaColumnData columnsListaComprobantes[] =
    { new FarmaColumnData("Tipo", 120, JLabel.CENTER), new FarmaColumnData("Numero", 100, JLabel.CENTER),
      new FarmaColumnData("Fecha", 120, JLabel.CENTER),
        //new FarmaColumnData( "Bruto", 75, JLabel.RIGHT ),
        new FarmaColumnData("Cant Items", 75, JLabel.RIGHT),
        // new FarmaColumnData( "Dscto", 100, JLabel.RIGHT ),//
        new FarmaColumnData("Sub Total", 75, JLabel.RIGHT),
        // new FarmaColumnData( "IGV", 100, JLabel.RIGHT ),//
        new FarmaColumnData("Valor IGV", 75, JLabel.RIGHT), new FarmaColumnData("Redondeo", 75, JLabel.RIGHT),
        new FarmaColumnData("Total S/.", 75, JLabel.RIGHT), new

        FarmaColumnData("", 0, JLabel.RIGHT), new FarmaColumnData("", 0, JLabel.RIGHT),
        new FarmaColumnData("", 0, JLabel.RIGHT), new FarmaColumnData("", 0, JLabel.RIGHT), //SEC_COMP_PAGO
        new FarmaColumnData("", 0, JLabel.RIGHT), //IND_ELECTRONICO
        } ;

    public static final Object[] defaultValuesListaComprobantes =
    { " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " " }; //RHERRERA

    public static final FarmaColumnData columnsListaComprobantesDetalle[] =
    { new FarmaColumnData("Codigo", 85, JLabel.CENTER), new FarmaColumnData("Producto", 180, JLabel.LEFT),
      new FarmaColumnData("Unidad", 150, JLabel.LEFT), new FarmaColumnData("Precio", 100, JLabel.RIGHT),
      new FarmaColumnData("% Dcto", 100, JLabel.RIGHT), new FarmaColumnData("P.Venta", 100, JLabel.RIGHT),
      new FarmaColumnData("Cantidad", 100, JLabel.RIGHT), new FarmaColumnData("Total", 100, JLabel.RIGHT),
      new FarmaColumnData("ZAN", 100, JLabel.RIGHT), };

    public static final Object[] defaultValuesListaComprobantesDetalle =
    { " ", " ", " ", " ", " ", " ", " ", " ", " " };

    public static final FarmaColumnData columnsListaFormasdePago[] =
    { new FarmaColumnData("Codigo", 85, JLabel.CENTER), new FarmaColumnData("Forma Pago", 150, JLabel.LEFT),
      new FarmaColumnData("Total", 80, JLabel.RIGHT), };

    public static final Object[] defaultValuesListaListaFormasdePago = { " ", " ", " " };

    public static final FarmaColumnData columnsListaDetalledeVentas[] =
    { new FarmaColumnData("Correlativo", 85, JLabel.CENTER), new FarmaColumnData("Tipo", 55, JLabel.CENTER),
      new FarmaColumnData("No Comprobante", 100, JLabel.CENTER), new FarmaColumnData("Fecha", 80, JLabel.CENTER),
      new FarmaColumnData("Descripcion", 150, JLabel.LEFT), new FarmaColumnData("Unidad", 120, JLabel.LEFT),
      new FarmaColumnData("Cantidad", 55, JLabel.RIGHT), new FarmaColumnData("Venta", 60, JLabel.RIGHT),
      new FarmaColumnData("Fecha", 0, JLabel.CENTER), };
    public static final Object[] defaultValuesListaDetalledeVentas = { " ", " ", " ", " ", " ", " ", " ", " ", " " };

    public static final FarmaColumnData columnsListaResumenProductosVendidos[] =
    { new FarmaColumnData("Codigo", 85, JLabel.CENTER), new FarmaColumnData("Descripcion", 200, JLabel.LEFT),
      new FarmaColumnData("Unidad", 150, JLabel.LEFT), new FarmaColumnData("Cantidad", 70, JLabel.RIGHT),
      new FarmaColumnData("Venta", 70, JLabel.RIGHT), };
    public static final Object[] defaultValuesListaResumenProductosVendidos = { " ", " ", " ", " ", " " };

    public static final FarmaColumnData columnsListaVentasVendedor[] =
    { new FarmaColumnData("Codigo", 60, JLabel.CENTER), // 1
        new FarmaColumnData("Vendedor", 160, JLabel.LEFT), // 2
        new FarmaColumnData("Tot.C.IGV.", 80, JLabel.RIGHT), // 3
        new FarmaColumnData("Tot.S.IGV", 80, JLabel.RIGHT), // 4
        new FarmaColumnData("GG", 80, JLabel.RIGHT), // 5
        new FarmaColumnData("G", 80, JLabel.RIGHT), // 6
        //ERIOS 2.3.3 Nueva columna GP
        new FarmaColumnData("GP", 80, JLabel.RIGHT), // 7
        new FarmaColumnData("Otros", 80, JLabel.RIGHT), // 8
        new FarmaColumnData("Servicios", 80, JLabel.RIGHT), // 9
        new FarmaColumnData("%", 80, JLabel.RIGHT), // 10
        //ERIOS 02.04.2014 Nuevos Campos (EDMUNDO)
        new FarmaColumnData("Sub Total.", 80, JLabel.RIGHT), // 11
        new FarmaColumnData("Calidad Inv.(*)", 80, JLabel.CENTER), // 12
        new FarmaColumnData("At. Cliente (*)", 80, JLabel.CENTER), // 13
        new FarmaColumnData("Total.", 80, JLabel.RIGHT), // 14
        new

        FarmaColumnData("Num.Ped.", 80, JLabel.RIGHT), // 15
        new FarmaColumnData("ind_tipo_fila", 0, JLabel.RIGHT), // 16
        new FarmaColumnData("Correlativo", 0, JLabel.RIGHT), // 17
        new FarmaColumnData("orden", 0, JLabel.RIGHT), // 18
        new FarmaColumnData("login", 0, JLabel.RIGHT) // 19
        } ;
    public static final Object[] defaultValuesListaVentasVendedor =
    { " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };

    public static final FarmaColumnData columnsListaDetalleVentasVendedor[] =
    { new FarmaColumnData("Correlativo", 80, JLabel.CENTER), new FarmaColumnData("Codigo", 60, JLabel.CENTER),
      new FarmaColumnData("Descripcion", 160, JLabel.LEFT), new FarmaColumnData("Unidad", 90, JLabel.LEFT),
      new FarmaColumnData("Cantidad", 70, JLabel.RIGHT), new FarmaColumnData("Venta", 70, JLabel.RIGHT),
      new FarmaColumnData("Zan", 60, JLabel.RIGHT), };

    public static final Object[] defaultValuesListaDetalleVentasVendedor = { " ", " ", " ", " ", " ", " ", " " };

    public static final FarmaColumnData columnsListaVentasProducto[] =
    { new FarmaColumnData("Codigo", 60, JLabel.CENTER), new FarmaColumnData("Descripcion", 205, JLabel.LEFT),
      new FarmaColumnData("Unidad", 100, JLabel.LEFT), new FarmaColumnData("Laboratorio", 190, JLabel.LEFT),
      new FarmaColumnData("Cantidad", 55, JLabel.RIGHT), new FarmaColumnData("Venta", 65, JLabel.RIGHT),
      new FarmaColumnData("Stock", 65, JLabel.RIGHT), };

    public static final Object[] defaultValuesListaVentasProducto = { " ", " ", " ", " ", " ", " ", " " };


    public static final FarmaColumnData columnsListaVentasDiaMes[] =
    { new FarmaColumnData("Periodo", 140, JLabel.LEFT), new FarmaColumnData("Pedidos", 80, JLabel.RIGHT),
      new FarmaColumnData("Vta. Inc. IGV", 100, JLabel.RIGHT), new FarmaColumnData("Vale Promedio", 100, JLabel.RIGHT),
      new FarmaColumnData("Und X Vale", 100, JLabel.RIGHT), new FarmaColumnData("Pr Prom x Und", 100, JLabel.RIGHT),
      new FarmaColumnData("DiaSem", 0, JLabel.RIGHT) };

    public static final Object[] defaultValuesListaVentasDiaMes = { " ", " ", " ", " ", " ", " ", " " };

    public static final FarmaColumnData columnsListaVentasPorDia[] =
    { new FarmaColumnData("Fecha", 73, JLabel.CENTER), //90
        new FarmaColumnData("Pedidos", 45, JLabel.RIGHT), //70
        new FarmaColumnData("Tic.Ini", 79, JLabel.CENTER), //JCHAVEZ 13.07.2009.n
        new FarmaColumnData("Bol.Ini", 79, JLabel.CENTER), new FarmaColumnData("Fac.Ini", 79, JLabel.CENTER),
        new FarmaColumnData("Tick", 45, JLabel.RIGHT), //JCHAVEZ 13.07.2009.n 60
        new FarmaColumnData("Bol.", 45, JLabel.RIGHT), new FarmaColumnData("Fac.", 45, JLabel.RIGHT),
        new FarmaColumnData("Tick.Fac", 50, JLabel.RIGHT), //JCHAVEZ 13.07.2009.n 60
        new FarmaColumnData("Tick.Anul", 55, JLabel.RIGHT), //JCHAVEZ 13.07.2009.n  80
        new FarmaColumnData("Bol.Anul", 55, JLabel.RIGHT), new FarmaColumnData("Fact.Anul", 55, JLabel.RIGHT),
        new FarmaColumnData("TF Anul", 55, JLabel.RIGHT), new FarmaColumnData("Total", 70, JLabel.RIGHT),
        /**
     * @author : dubilluz
     * @author : 28.08.2007
     */
        new FarmaColumnData("Fechaordenar", 0, JLabel.CENTER) };
    public static final Object[] defaultValuesListaVentasPorDia =
    { " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
      " " }; //JCHAVEZ 13.07.2009.n agregué 3 elementos

    public static final FarmaColumnData columnsListaDetalladoResumenVta[] =
    { new FarmaColumnData("Fecha", 130, JLabel.CENTER), new FarmaColumnData("T", 20, JLabel.LEFT),
      new FarmaColumnData("Nro Comprobante", 100, JLabel.RIGHT), new FarmaColumnData("Estado", 80, JLabel.CENTER),
      new FarmaColumnData("Items", 50, JLabel.RIGHT), new FarmaColumnData("Unid Vta", 90, JLabel.LEFT),
      new FarmaColumnData("Prec Vta", 60, JLabel.RIGHT), new FarmaColumnData("Prec Total", 60, JLabel.RIGHT),
      new FarmaColumnData("Usuario", 90, JLabel.LEFT) };
    public static final Object[] defaultValuesListaDetalladoResumenVta =
    { " ", " ", " ", " ", " ", " ", " ", " ", " " };

    public static final String VNOMBREINHASHTABLEVENDEDOR = "IND_CAMPO_ORDENAR_VENDEDOR";
    public static final String VNOMBREINHASHTABLEREGISTROVENTAS = "IND_CAMPO_ORDENAR_REGISTROVENTAS";
    public static final String VNOMBREINHASHTABLEVTASPRODUCTO = "IND_CAMPO_ORDENAR_VTASPRODUCTO";
    public static final String VNOMBREINHASHTABLEDETALLEVENTAS = "IND_CAMPO_ORDENAR_DETALLEVENTAS";
    public static final String VNOMBREINHASHTABLERESUMENVENTAS = "IND_CAMPO_ORDENAR_RESUMENVENTAS";
    public static final String VNOMBREINHASHTABLEDETALLEVENTASVENDEDOR = "IND_CAMPO_ORDENAR_DETALLEVENTASVENDEDOR";
    public static final String VNOMBREINHASHTABLECORRECCIONCOMPROBANTES = "IND_CAMPO_ORDENAR_CORRECCIONCOMPROBANTES";

    public static final String TIP_COMP_BOLETA = "01";
    public static final String TIP_COMP_FACTURA = "02";
    public static final String TIP_COMP_GUIA = "03";
    public static final String TIP_PEDIDO_DELIVERY = "D";
    public static final String TIP_PEDIDO_INSTITUCIONAL = "I";
    public static final String TIP_COMP_TICKET = "05"; //JCHAVEZ 13.07.2009.n
    public static final String TIP_COMP_TICKET_FACT = "06"; //LLEIVA 05.Feb.2014.n

    public static final String TIP_FILTRO = "FILTRAR";

    public static final String[] ORDEN = { "ASC", "DESC" };
    public static final String[] ORDENVAL = { FarmaConstants.ORDEN_ASCENDENTE, FarmaConstants.ORDEN_DESCENDENTE };

    public static final String VNOMBREINHASHTABLERESUMENPORDIA = "IND_CAMPO_ORDENAR_RESUMEN_POR_DIA";

    //Histórico de Creación/Modificación
    //ERIOS      27.03.2005   Creación
    //DlgVentasPorHora
    public static final FarmaColumnData columnsListaVentasHora[] =
    { new FarmaColumnData("Horarios", 120, JLabel.CENTER), new FarmaColumnData("Pedidos", 100, JLabel.RIGHT),
      new FarmaColumnData("Venta", 100, JLabel.RIGHT), new FarmaColumnData("Vta. Promedio", 100, JLabel.RIGHT),
      new FarmaColumnData("Unids. Prom.", 100, JLabel.RIGHT), new FarmaColumnData("Precio Prom.", 100, JLabel.RIGHT) };

    public static final Object[] defaultValuesListaVentasHora = { " ", " ", " ", " ", " ", " " };


    public static String vVentasDiaMes = "VentasDiasMes";

    public static String[] vOrden = { "ASC", "DESC" };
    public static String[] vOrdenval = { FarmaConstants.ORDEN_ASCENDENTE, FarmaConstants.ORDEN_DESCENDENTE };

    //Histórico de Creación/Modificación
    //ERIOS      27.03.2005   Creación
    //DlgVentasPorHora
    public static String vVentasPorHora = "VentasporHora";
    public static String[] vCodDiaSemana = { "1", "2", "3", "4", "5", "6", "7" };
    public static String[] vDescDiaSemana =
    { "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo" };

    public static String vNombreInHashtableporHora = "IND_CAMPO_ORDENAR_HORA";
    public static String[] vCodCampoporHora = { "0", "1", "2", "3", "4", "5" };
    public static String[] vDescCampoporHora =
    { "Horarios", "Pedidos", "Venta", "Vta. Promedio", "Unids. Prom.", "Precio Prom." };

    //Histórico de Creación/Modificación
    //ERIOS      06.07.2006   Creación
    //DlgProductoFaltaCero
    public static final FarmaColumnData columnsListaFaltaCero[] =
    { new FarmaColumnData("Codigo", 60, JLabel.CENTER), new FarmaColumnData("Descripcion", 210, JLabel.LEFT),
      new FarmaColumnData("Unidad", 105, JLabel.LEFT), new FarmaColumnData("Laboratorio", 195, JLabel.LEFT),
      new FarmaColumnData("Cantidad", 55, JLabel.RIGHT), new FarmaColumnData("DescUnidOrd", 0, JLabel.RIGHT) };

    public static final Object[] defaultValuesListaFaltaCero = { " ", " ", " ", " ", " ", " " };

    public static final FarmaColumnData columnsListaDetFaltaCero[] =
    { new FarmaColumnData("Solicitante", 210, JLabel.LEFT), new FarmaColumnData("Fecha", 120, JLabel.LEFT),
      new FarmaColumnData("FechaOrd", 0, JLabel.LEFT), new FarmaColumnData("SecUsu", 0, JLabel.LEFT) };

    public static final Object[] defaultValuesListaDetFaltaCero = { " ", " ", " ", " " };


    public static final FarmaColumnData columnsListaProveedoresProdVta[] =
    { new FarmaColumnData("Codigo", 50, JLabel.LEFT), new FarmaColumnData("Laboratorio", 230, JLabel.LEFT),
      new FarmaColumnData("Total", 100, JLabel.RIGHT), new FarmaColumnData("Cantidad", 100, JLabel.RIGHT) };
    public static final Object[] defaultValuesListaProveedoresProdVta = { " ", " ", " ", " " };

    public static final FarmaColumnData columnsListaConsolidadoVtasProducto[] =
    { new FarmaColumnData("Vendedor", 180, JLabel.LEFT), new FarmaColumnData("Unid Vendidas", 120, JLabel.RIGHT),
      new FarmaColumnData("Total S/.", 110, JLabel.RIGHT) };
    public static final Object[] defaultValuesListaListaConsolidadoVtasProducto = { " ", " ", " " };


    //Histórico de Creación/Modificación
    //ERIOS      17.07.2006   Creación
    //DlgProductosABC
    public static final FarmaColumnData columnsListaProdABC[] =
    { new FarmaColumnData("Codigo", 60, JLabel.CENTER), new FarmaColumnData("Descripcion", 200, JLabel.LEFT),
      new FarmaColumnData("Unidad", 105, JLabel.LEFT), new FarmaColumnData("Laboratorio", 150, JLabel.LEFT),
      new FarmaColumnData("Stock", 70, JLabel.RIGHT), new FarmaColumnData("Unid. Vend.", 55, JLabel.RIGHT),
      new FarmaColumnData("Monto", 70, JLabel.RIGHT), new FarmaColumnData("Tipo", 40, JLabel.CENTER)
        //new FarmaColumnData( "DescUnidOrd", 0, JLabel.RIGHT),
        } ;

    public static final Object[] defaultValuesListaProdABC = { " ", " ", " ", " ", " ", " ", " ", " " };

    //Histórico de Creación/Modificación
    //Paulo      29.08.2006   Creación
    //DlgProductosABC
    public static String vProductosABC = "ProductosABC";
    public static String[] vCodTipo = { "A", "B", "C" };
    public static String[] vDescTipo = { "Tipo A", "Tipo B", "Tipo C" };

    //Histórico de Creación/Modificación
    //Paulo      29.08.2006   Creación
    //DlgProductosABC
    public static String vNombreInHashtableABC = "IND_CAMPO_ORDENAR_ABC";
    public static String[] vCodABC = { "0", "1", "2", "3", "4", "5", "6", "7" };
    public static String[] vDescCampoABC =
    { "Codigo", "Descripcion", "Unidad", "Laboratorio", "Stock", "Unid Vend", "Monto", "Tipo" };

    public static final FarmaColumnData columnsListaPedAnuNoCob[] =
    { new FarmaColumnData("Número", 80, JLabel.CENTER), new FarmaColumnData("Fecha", 120, JLabel.LEFT),
      new FarmaColumnData("Cliente", 220, JLabel.LEFT), new FarmaColumnData("Monto Neto", 85, JLabel.RIGHT),
      new FarmaColumnData("", 0, JLabel.LEFT), new FarmaColumnData("", 0, JLabel.LEFT),
      new FarmaColumnData("", 0, JLabel.LEFT) };

    public static final Object[] defaultValuesListaPedAnuNoCob = { " ", " ", " ", " " };

    public static final FarmaColumnData columnsListaUnidVtaLocal[] =
    { new FarmaColumnData("Código", 60, JLabel.CENTER), new FarmaColumnData("Descripción", 220, JLabel.LEFT),
      new FarmaColumnData("Unidad Ptcion.", 90, JLabel.LEFT), new FarmaColumnData("Laboratorio", 150, JLabel.LEFT),
      new FarmaColumnData("Stock", 70, JLabel.RIGHT), new FarmaColumnData("Farma", 65, JLabel.CENTER),
      new FarmaColumnData("Periodo", 70, JLabel.RIGHT), new FarmaColumnData("Vta. Periodo", 80, JLabel.RIGHT),
      new FarmaColumnData("# Dias Vta.", 80, JLabel.RIGHT), new FarmaColumnData("Vta. Rep.", 80, JLabel.RIGHT),
      new FarmaColumnData("Vta. X Día", 80, JLabel.RIGHT), new FarmaColumnData("Días Inv.", 70, JLabel.RIGHT), };

    public static final Object[] defaultValuesListaUnidVtaLocal =
    { " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };

    public static final String VNOMBREINHASHTABLEUNIDVTALOCAL = "IND_CAMPO_ORDENAR_UNIDVTALOCAL";

    /**
 * Cambio
 * @Autor: Luis Reque
 * @Fecha: 09/05/2007
 * */

    /**
     * MOdificado
     * @author : dubilluz
     * @since  : 21.08.2007
     */
    public static final FarmaColumnData columnsListaProdSinVtaNDias[] = { new

        FarmaColumnData("Código", 60, JLabel.CENTER), new FarmaColumnData("Descripción", 220, JLabel.LEFT),
        new FarmaColumnData("Unidad.Ptcion.", 90, JLabel.LEFT), new FarmaColumnData("Laboratorio", 150, JLabel.LEFT),
        new FarmaColumnData("Stock", 60, JLabel.RIGHT), new FarmaColumnData("Farma", 60, JLabel.CENTER),
        new FarmaColumnData( /*"Per. Sin Vta."*/"DíasSinVta", 70, JLabel.RIGHT),
        new FarmaColumnData( /*"Per. Stock"*/"DíasConStk", 80, JLabel.RIGHT),
        //new FarmaColumnData("Stock Libre", 80, JLabel.RIGHT ),
        //new FarmaColumnData("Stock Tras.", 80, JLabel.RIGHT ),
        new FarmaColumnData("Unid.Vta.", /*80*/0, JLabel.RIGHT), new FarmaColumnData("Per.Vta", /*70*/0, JLabel.RIGHT),
        new FarmaColumnData("U.V.Per.", /*70*/0, JLabel.RIGHT),
        //new FarmaColumnData("# Result", 70, JLabel.RIGHT ),
        //new FarmaColumnData("Result", 70, JLabel.RIGHT ),
        } ;

    public static final Object[] defaultValuesListaProdSinVtaNDias =
    { " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };

    public static final String VNOMBREINHASHTABLEPRODSINVTANDIAS = "IND_CAMPO_ORDENAR_PRODSINVTANDIAS";

    /** Descripcion del listado de las formas de pago por pedido
     * @Author: JCORTEZ
     * @Since: 04/0/07
     */
    public static final FarmaColumnData columnsListaFormasPago[] =
    { new FarmaColumnData("Código", 80, JLabel.CENTER), new FarmaColumnData("Descripción", 180, JLabel.LEFT),
      new FarmaColumnData("Importe", 80, JLabel.RIGHT), new FarmaColumnData("Moneda", 80, JLabel.LEFT),
      new FarmaColumnData("Vuelto", 75, JLabel.RIGHT), new FarmaColumnData("Importe Total", 130, JLabel.RIGHT),
      new FarmaColumnData("Cajero", 100, JLabel.LEFT), };

    public static final Object[] defaultValuesListaFormasPago = { " ", " ", " ", " ", " ", " ", " " };

    public static String ACCION_MOSTRAR_TOTALES = "TOTALES";
    public static String ACCION_MOSTRAR_MEZON = "MEZON";
    public static String ACCION_MOSTRAR_DELIVERY = "DELIVERY";
    public static String ACCION_MOSTRAR_INSTITUCIONAL = "INSTITUCIONAL";
    // TIPOS DE VENTA
    // 01 --Mezon
    // 02 --Delivery
    // 03 --Vta Institucional
    public static String Tipo_Venta_Mezon = "01";
    public static String Tipo_Venta_Delivery = "02";
    public static String Tipo_Venta_Institucional = "03";

    /** Descripcion del listado de reporte de guias
     * @Author: LLEIVA
     * @Since: 31/Mar/2014
     */
    public static final FarmaColumnData columnsListaReporteGuia[] =
    { new FarmaColumnData("Num. Guía", 120, JLabel.LEFT), new FarmaColumnData("Fecha", 100, JLabel.CENTER),
      new FarmaColumnData("Cant", 80, JLabel.CENTER), new FarmaColumnData("Val. Bruto", 80, JLabel.RIGHT),
      new FarmaColumnData("Tipo Guia", 140, JLabel.RIGHT), };

    public static final Object[] defaultValuesListaReporteGuia = { " ", " ", " ", " ", " " };


    /**
     *
     */
    public static final FarmaColumnData columnsListaRegistroVentasDeliv[] =
    { new FarmaColumnData("Tip Pedido", 80, JLabel.LEFT), //0--
        new FarmaColumnData("Correlativo", 80, JLabel.CENTER), //1-0
        new FarmaColumnData("T", 20, JLabel.CENTER), //2-1
        new FarmaColumnData("Num. Comp.", 100, JLabel.CENTER), //3-2
        new FarmaColumnData("Fecha", 120, JLabel.CENTER), //4--3
        new FarmaColumnData("Cliente Atendido", 180, JLabel.LEFT), //5--4
        new FarmaColumnData("Telef.", 80, JLabel.LEFT), //6--5
        new FarmaColumnData("Monto", 100, JLabel.RIGHT), //7--6
        new FarmaColumnData("Conv.", 40, JLabel.CENTER), //8--7
        new FarmaColumnData("Estado", 100, JLabel.LEFT), //9--8
        new FarmaColumnData("Estado", 0, JLabel.CENTER), //10--9
        new FarmaColumnData("Usuario", 100, JLabel.LEFT), //11--10
        new FarmaColumnData("Cant.Items", 60, JLabel.CENTER), //12--11
        new FarmaColumnData("Ruc", 85, JLabel.CENTER), //13--12
        new FarmaColumnData("Dir.Envio", 0, JLabel.LEFT), //14--13
        new FarmaColumnData("Ref.Dir.Envio", 0, JLabel.LEFT), //15--14
        new FarmaColumnData("Motorizado", 130, JLabel.CENTER), //16--15
        new FarmaColumnData("Obs.", 0, JLabel.RIGHT), //17--16
        new FarmaColumnData("Nombre de", 0, JLabel.RIGHT), //18--17
        new FarmaColumnData("Dir.Cliente", 0, JLabel.RIGHT), //19--18
        } ;

    public static final Object[] defaultValuesListaRegistroVentasDeliv =
    { " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };


}
