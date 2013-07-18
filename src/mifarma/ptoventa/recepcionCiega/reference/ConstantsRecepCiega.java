package mifarma.ptoventa.recepcionCiega.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;
import mifarma.common.FarmaConstants;

public class ConstantsRecepCiega {
    public ConstantsRecepCiega() {
    }
    
    
    /**
     * Se lista las entregas de mercaderia
     *@AUTHOR   
     *@SINCE  16.11.09
     * */
     public static final FarmaColumnData columnsListaRecepcion[] = {
      new FarmaColumnData( "N° Ingreso", 90, JLabel.CENTER ),
      new FarmaColumnData( "Fecha Ingreso", 130, JLabel.CENTER ),
      new FarmaColumnData( "Usuario Creacion", 110, JLabel.CENTER ),
      new FarmaColumnData( "Cant Guias", 80, JLabel.CENTER ),
      new FarmaColumnData( "Estado", 120, JLabel.LEFT ),
      /*new FarmaColumnData( "", 0, JLabel.LEFT ),//est
      new FarmaColumnData( "", 0, JLabel.LEFT ),//orden
      new FarmaColumnData( "", 0, JLabel.LEFT ),//BULTOS
      new FarmaColumnData( "", 0, JLabel.LEFT )*/};//PRESINTOS
     
     public static final Object[] defaultValuesListaRecepcion = {" "," "," "," "," "};
     
    public static final FarmaColumnData columnsListaGuias[] = {
     new FarmaColumnData( "Sel", 25, JLabel.CENTER ), //0
     new FarmaColumnData( "Guia", 95, JLabel.LEFT ),  //1 
     new FarmaColumnData( "Num. Entrega", 95, JLabel.LEFT ), //2
     new FarmaColumnData( "Fecha", 130, JLabel.LEFT ),  //3
    /* new FarmaColumnData( "", 0, JLabel.LEFT ),//NumNotaEst 4
     new FarmaColumnData( "", 0, JLabel.LEFT ),//Estado //5
    new FarmaColumnData( "Pag.", 0, JLabel.CENTER ), //6
    new FarmaColumnData( "Items", 0, JLabel.CENTER ), //7
    new FarmaColumnData( "Prods", 0, JLabel.CENTER )*/ //8
     };
    public static final Object[] defaultValuesListaGuias = {" "," "," "," "," "," "," "," "," "};
    
    
    public static final FarmaColumnData columnsListaGuias2[] = {
     new FarmaColumnData( "Guia", 95, JLabel.LEFT ),
     new FarmaColumnData( "Num. Entrega", 95, JLabel.LEFT ),
     new FarmaColumnData( "Fecha", 130, JLabel.LEFT ),
     /*new FarmaColumnData( "", 0, JLabel.LEFT ),//NumNotaEst
     new FarmaColumnData( "", 0, JLabel.LEFT ),//Estado
     new FarmaColumnData( "", 0, JLabel.LEFT ),//NumGuiaRem
     new FarmaColumnData( "Pag.", 0, JLabel.CENTER ),
     new FarmaColumnData( "Items", 0, JLabel.CENTER ),
     new FarmaColumnData( "Prods", 0, JLabel.CENTER )*/};
    
    public static final Object[] defaultValuesListaGuias2 = {" "," "," "," "," "," "," "," "," "};
    //DlgDetalleGuia
     public static final FarmaColumnData columnsListaDetalleGuia[] = {
                          new FarmaColumnData("Codigo", 50, JLabel.LEFT),
                          new FarmaColumnData("Descripcion", 240, JLabel.LEFT),
                          new FarmaColumnData("Unidad", 120, JLabel.LEFT),
                          new FarmaColumnData("Laboratorio", 165, JLabel.LEFT),
                          new FarmaColumnData("Cant.", 40, JLabel.RIGHT),
                          /*new FarmaColumnData("OK", 40, JLabel.RIGHT),
                          new FarmaColumnData("Difer.", 40, JLabel.RIGHT),
                          new FarmaColumnData("Afect.", 40, JLabel.CENTER),
                          new FarmaColumnData("Guia", 0, JLabel.CENTER),
                          new FarmaColumnData("Val Frac.", 0, JLabel.CENTER),
                          new FarmaColumnData("stk fis", 0, JLabel.CENTER),
                          new FarmaColumnData("sec det", 0, JLabel.CENTER), 
                          new FarmaColumnData("", 0, JLabel.LEFT),
                          new FarmaColumnData("Pag.", 0, JLabel.CENTER)*/
       };

      public static final Object[] defaultValuesListaDetalleGuia = { " ", " ", " ", " "," "};
    
    //DlgOrdenar
    public static final String[] vDescColum  = {"GUIA","NUM_ENTREGA","FECHA"};
    public static final String[] vCodColum ={"1","2","3"};
    public static final String NOM_HASTABLE_CMBCOLUMN ="CMB_COLUMN";
    
    public static final String[] vDescOrden ={FarmaConstants.ORDEN_ASCENDENTE,FarmaConstants.ORDEN_DESCENDENTE};
    public static final String[] vCodOrden = {"ASC","DESC"};
    public static final String NOM_HASTABLE_CMBORDEN="CMB_ORDERN";
    
    public static final FarmaColumnData columnsListaGuiasEntre[] = {
    new FarmaColumnData( "Guia", 90, JLabel.LEFT ),
    new FarmaColumnData( "Num. Entrega", 90, JLabel.LEFT ),
    new FarmaColumnData( "Fecha", 100, JLabel.LEFT ),
    new FarmaColumnData( "NumNota", 90, JLabel.LEFT ),//NumNotaEst
    new FarmaColumnData( "Pag.", 40, JLabel.CENTER ),
    new FarmaColumnData( "Items", 40, JLabel.CENTER ),
    new FarmaColumnData( "Prods", 40, JLabel.CENTER ),
    new FarmaColumnData( "Estado", 70, JLabel.CENTER )};//Estado
    
    public static final Object[] defaultValuesListaGuiasEntre= {" "," "," "," "," "," "," "," "};
    
    // 
    
    public static final FarmaColumnData[] columnsListaProductosSegundoConteo =
    {     
     // new FarmaColumnData("Código Barra",90,JLabel.LEFT),
      new FarmaColumnData("Código",50,JLabel.CENTER),
      new FarmaColumnData("Descripción Producto",230,JLabel.LEFT),
      new FarmaColumnData("Unidad",90,JLabel.LEFT),
      new FarmaColumnData("Laboratorio",240,JLabel.LEFT),
      new FarmaColumnData("Cantidad",55,JLabel.LEFT)
      /*new FarmaColumnData("CodProd",0,JLabel.LEFT),
      new FarmaColumnData("SecConteo",0,JLabel.LEFT)*/
    };

    public static final Object[] defaultcolumnsListaProductosSegundoConteo = {" ", " ",  " ", " ", " "," "," "};
    
    
    public static final FarmaColumnData[] columnsListaGuiasPendientes =
    {     
      new FarmaColumnData("Num. Guía",90,JLabel.LEFT),
      new FarmaColumnData("Num. Entrega",100,JLabel.LEFT),
      new FarmaColumnData("Fecha",100,JLabel.LEFT)
    };

    public static final Object[] defaultcolumnsListaGuiasPendientes = { " ", " ", " "};
    
    public static final FarmaColumnData[] columnsListaProductosFaltantes =
    {     
    //  new FarmaColumnData("Cod. Barra",70,JLabel.LEFT),
    new FarmaColumnData("Código",50,JLabel.LEFT),
      new FarmaColumnData("Desc. Producto",200,JLabel.LEFT),
      new FarmaColumnData("Unidad",90,JLabel.LEFT),
      new FarmaColumnData("Laboratorio",180,JLabel.LEFT),
      new FarmaColumnData("Contada",62,JLabel.LEFT),
      new FarmaColumnData("Entregada",67,JLabel.LEFT),
    //  new FarmaColumnData("Diferencia",0,JLabel.LEFT),
      new FarmaColumnData("Nro. Entrega",100,JLabel.LEFT)
    };

    public static final Object[] defaultcolumnsListaProductosFaltantes = {  " ", " ", " ", " ", " ", " "," "," "," "};
    
    public static final FarmaColumnData[] columnsListaProductosSobrantes =
    {     
    //  new FarmaColumnData("Cod. Barra",70,JLabel.LEFT),
    new FarmaColumnData("Código",50,JLabel.LEFT),
      new FarmaColumnData("Desc. Producto",200,JLabel.LEFT),
      new FarmaColumnData("Unidad",90,JLabel.LEFT),
      new FarmaColumnData("Laboratorio",178,JLabel.LEFT),
      new FarmaColumnData("Contada",62,JLabel.LEFT), //82
      new FarmaColumnData("Entregada",67,JLabel.LEFT),
    //   new FarmaColumnData("Diferencia",0,JLabel.LEFT),
      new FarmaColumnData("Nro. Entrega",100,JLabel.LEFT)
    };

    public static final Object[] defaultcolumnsListaProductosSobrantes= { " ", " ", " ", " ", " "," "," "," "," "};
    
    public static final FarmaColumnData[] columnsListaProductosTransferencia =
    {         
      new FarmaColumnData("Desc. Producto",200,JLabel.LEFT),
      new FarmaColumnData("Unidad",90,JLabel.LEFT),
      new FarmaColumnData("Cantidad",82,JLabel.LEFT),
      new FarmaColumnData("Lote",100,JLabel.LEFT),    
      new FarmaColumnData("Fecha Vcto.",100,JLabel.LEFT)
    };

    public static final Object[] defaultcolumnsListaProductosTransferencia= { " ", " ", " ", " "," "," ", " ", " "," ",""};
    
    public static final FarmaColumnData[] columnsListaProductos =
    { 
        new FarmaColumnData( "Sel", 31, JLabel.CENTER ),
        new FarmaColumnData( "Codigo", 45, JLabel.LEFT ),
        new FarmaColumnData( "Descripcion", 220, JLabel.LEFT ),
        new FarmaColumnData( "Unidad", 105, JLabel.LEFT ),
        new FarmaColumnData( "Laboratorio", 180, JLabel.LEFT )       
    };






    public static final Object[] defaultcolumnsListaProductos=   { " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", 
    " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
    " "};
    
    public static final FarmaColumnData columnsListaImpresoras[] = {
              new FarmaColumnData( "Sec.", 60, JLabel.LEFT ),
        new FarmaColumnData( "Descripcion", 200, JLabel.LEFT ),
              new FarmaColumnData( "Num. Comp.", 80, JLabel.LEFT ),     
    };

    public static final Object[] defaultValuesListaImpresoras = {" ", " ", " "};
    
    public static final String NOM_HASHTABLE_CMBTIPO_TRANSF = "cmbTipoTransf";
    public static final String NOM_HASHTABLE_CMBTIPO_TRANSF_INTERNO = "cmbTipoTransfInterno";
    public static final String NOM_HASHTABLE_CMBMOTIVO_TRANSF = "cmbMotivoTransf";
    
    
    
    // 

    public static final FarmaColumnData columnsListaProductosConteo[] = {
      new FarmaColumnData( "Descripción", 170, JLabel.LEFT ),
      new FarmaColumnData( "Unidad", 80, JLabel.LEFT ),
      new FarmaColumnData( "Cantidad", 60, JLabel.RIGHT ),
      new FarmaColumnData( "Lote 1", 80, JLabel.LEFT ),
      new FarmaColumnData( "Venc. 1",70,JLabel.LEFT),
      new FarmaColumnData( "Lote 2", 80, JLabel.LEFT ),
      new FarmaColumnData( "Venc. 2",70,JLabel.LEFT),
      new FarmaColumnData( "secAux",0,JLabel.RIGHT),     //7
      new FarmaColumnData( "",0,JLabel.RIGHT), //codBarra  8
      new FarmaColumnData( "",0,JLabel.RIGHT) //NroBloque  9
    };    
          public static final Object[] defaultValuesListaProductosConteo = {" "," "," "," ", " "," "," ", " ", " ", " " };
          
    public static final String AuxConteoNumera = "075";
    
    public static final String EstadoEmitido = "E";
    public static final String EstadoConteo = "C";
    public static final String EstadoEspera = "P";
    public static final String EstadoRevisado = "R";
    public static final String EstadoVerificacion = "V";//VERIFICACION DE CONTEO
    public static final String EstadoAfectadoTotal = "T";
    public static final String EstadoAfectadoParcial = "L"; // AUN NO SE UTILIZA ESTE ESTADO
 
 //  17.03.2010 COLUMNAS LISTA TRANSPORTISTA
    public static final FarmaColumnData columnsListaTransp[] = {
     new FarmaColumnData( "N° Ingreso", 90, JLabel.CENTER ), //0
     new FarmaColumnData( "Fecha Ingreso", 130, JLabel.CENTER ), //1
     new FarmaColumnData( "Usuario", 120, JLabel.CENTER ),       //2
     new FarmaColumnData( "Transportista", 160, JLabel.LEFT ),   //3
     new FarmaColumnData( "Placa", 65, JLabel.LEFT ),            //4
     new FarmaColumnData( "Estado", 80, JLabel.LEFT ),             //5
     new FarmaColumnData( "Cant. Guías", 80, JLabel.RIGHT )       //6
     //cod_est 7
     //ordena 8     
     };
    public static final Object[] defaultValuesListaTrans = {" "," "," "," "," "," "," "," "," "};
    
    public static boolean error_du = false;
}
