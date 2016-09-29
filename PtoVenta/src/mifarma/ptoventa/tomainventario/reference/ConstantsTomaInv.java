package mifarma.ptoventa.tomainventario.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;


public class ConstantsTomaInv {
    public ConstantsTomaInv() {
    }

    public static final String TIPO_OPERACION_TOMA_INV = "I";

    public static final String TIPO_OPERACION_CONSULTA_HIST = "H";
    /*
       * 22/03/2006   Paulo   Variables creadas para identificar el tipo de laboratorio
  */
    public static final String TIPO_FARMA = "01";
    public static final String TIPO_NO_FARMA = "02";
    public static final String TIPO_TODOS = "03";
    public static final String TIPO_PRINCIPIO_ACTIVO = "1";
    public static final String TIPO_ACCION_TERAPEUTICA = "2";
    public static final String TIPO_LABORATORIO = "3";


    public static final FarmaColumnData[] columnsListaLaboratorios =
    { new FarmaColumnData("Sel", 40, JLabel.CENTER), new FarmaColumnData("Codigo", 75, JLabel.CENTER),
      new FarmaColumnData("Laboratorio", 290, JLabel.LEFT),
        /*
       * 22/03/2006   Paulo   Agregacion de una nueva columna para la lista de laboratorios
       */
        new FarmaColumnData("Tipo", 0, JLabel.LEFT), };

    public static final Object[] defaultValuesListaLaboratorios = { " ", " ", " ", " " };

    public static final FarmaColumnData[] columnsHistoricoTomas =
    { new FarmaColumnData("Nro Toma", 65, JLabel.LEFT), new FarmaColumnData("Tipo Inv.", 90, JLabel.LEFT),
      new FarmaColumnData("Tipo Toma", 80, JLabel.LEFT), new FarmaColumnData("Fecha Inicio", 125, JLabel.LEFT),
      new FarmaColumnData("Fecha Fin", 125, JLabel.LEFT), new FarmaColumnData("Estado", 80, JLabel.LEFT), };

    public static final Object[] defaultValuesHistoricoTomas = { " ", " ", " ", " ", " ", " " };

    public static final FarmaColumnData[] columnsTomasInventario =
    { new FarmaColumnData("Nro Toma", 65, JLabel.LEFT), new FarmaColumnData("Tipo Toma", 90, JLabel.LEFT),
      new FarmaColumnData("Fecha Inicio", 125, JLabel.LEFT), new FarmaColumnData("Estado", 90, JLabel.LEFT),
      new FarmaColumnData("", 0, JLabel.LEFT), //};
        /**
       * Usuario de Creacion de la Toma
       * @author : dubilluz
       * @since  : 11.07.2007
       */
        new FarmaColumnData("", 0, JLabel.LEFT) };

    public static final Object[] defaultValuesTomasInventario = { " ", " ", " ", " ", " " };

    public static final FarmaColumnData[] columnsLaboratoriosToma =
    { new FarmaColumnData("Codigo", 80, JLabel.LEFT), new FarmaColumnData("Laboratorio", 250, JLabel.LEFT),
      new FarmaColumnData("Estado", 100, JLabel.LEFT), };

    public static final Object[] defaultValuesLaboratoriosToma = { " ", " ", " " };

    public static final FarmaColumnData[] columnsListaProductosXLaboratorio =
    { new FarmaColumnData("Codigo",55, JLabel.CENTER), 
      new FarmaColumnData("Descripcion", 230, JLabel.LEFT),
      new FarmaColumnData("Unid. Presentacion", 120, JLabel.LEFT),
      new FarmaColumnData("C. Ent", 70, JLabel.RIGHT),
      new FarmaColumnData("C. Frac", 70, JLabel.RIGHT),
       new FarmaColumnData("Valor Frac", 0, JLabel.RIGHT),
      
         new FarmaColumnData("Unid. Venta", 0, JLabel.RIGHT),
    new FarmaColumnData("", 0, JLabel.RIGHT)
      };

    public static final Object[] defaultValuesListaProductosXLaboratorio = { " ", " ", " ",
                                                                             " ", " ", " ", 
                                                                             " "," "};

    public static final FarmaColumnData[] columnsListaProductosTomaLote =
    { new FarmaColumnData("Codigo",50, JLabel.CENTER), 
      new FarmaColumnData("Descripcion", 140, JLabel.LEFT),
      new FarmaColumnData("Unid. Presentacion",70, JLabel.LEFT),
      new FarmaColumnData("Valor Frac", 0, JLabel.RIGHT),
      //LTAVARA 03/02/2016 PROYECTO MAYORISTA
      new FarmaColumnData( "Lote", 110, JLabel.LEFT ),
      new FarmaColumnData( "Fech. Vencimiento",80, JLabel.LEFT ),
      //FIN PROYECTO MAYORI
      new FarmaColumnData("C. Ent", 60, JLabel.RIGHT),
      new FarmaColumnData("C. Frac", 60, JLabel.RIGHT),
       
      };

    public static final Object[] defaultValuesListaProductosTomaLote = { " ", " ", " ", " ", " "," ", " "};


    public static final FarmaColumnData[] columnsListaDiferenciasProductos =
    { new FarmaColumnData("Codigo", 55, JLabel.CENTER), new FarmaColumnData("Descripcion", 220, JLabel.LEFT),
      new FarmaColumnData("Unid Presentacion", 100, JLabel.LEFT),
    new FarmaColumnData("Lote", 70, JLabel.LEFT),
      new FarmaColumnData("Stock Actual", 70, JLabel.RIGHT), new FarmaColumnData("Diferencia", 75, JLabel.RIGHT),
      new FarmaColumnData("Precio", 60, JLabel.RIGHT), };

    public static final Object[] defaultValuesListaDiferenciasProductos = { " ", " "," ", " ", " ", " ", " " };

    public static final FarmaColumnData[] columnsListaLaboratoriosIxL =
    { new FarmaColumnData("Codigo", 65, JLabel.CENTER), new FarmaColumnData("Descripcion", 250, JLabel.LEFT),
      new FarmaColumnData("Dirección", 270, JLabel.LEFT), new FarmaColumnData("Teléfono", 130, JLabel.LEFT) };

    public static final Object[] defaultValuesListaLaboratoriosIxL = { " ", " ", " ", " " };

    public static final FarmaColumnData[] columnsListaProductosIxL =
    { new FarmaColumnData("Codigo", 70, JLabel.CENTER), new FarmaColumnData("Descripcion", 225, JLabel.LEFT),
      new FarmaColumnData("Unidad Compra", 110, JLabel.LEFT), new FarmaColumnData("Unidad Venta", 110, JLabel.LEFT),
      new FarmaColumnData("Stk Min.", 45, JLabel.RIGHT), new FarmaColumnData("Ent", 45, JLabel.RIGHT),
      new FarmaColumnData("Frac", 45, JLabel.RIGHT), new FarmaColumnData("Precio", 55, JLabel.RIGHT), };

    public static final Object[] defaultValuesListaProductosIxL = { " ", " ", " ", " ", " ", " ", " ", " " };

    public static final FarmaColumnData[] columnsListaDiferenciasConsolidado =
    { new FarmaColumnData("Codigo", 60, JLabel.CENTER), 
      new FarmaColumnData("Descripcion", 200, JLabel.LEFT),
      new FarmaColumnData("Unid Presentacion", 110, JLabel.LEFT),
     new FarmaColumnData("Lote", 70, JLabel.LEFT), 
      new FarmaColumnData("Stock Actual", 75, JLabel.RIGHT), 
      new FarmaColumnData("Diferencia", 75, JLabel.RIGHT),
      new FarmaColumnData("Precio", 70, JLabel.RIGHT), 
      new FarmaColumnData("Laboratorio", 150, JLabel.LEFT) };

    public static final Object[] defaultValuesListaDiferenciasConsolidado = {" ", " ", " ", " ", " ", " ", " ", " " };


    public static final FarmaColumnData[] columnsListaDiferenciasConsolidadoDiario =
    { new FarmaColumnData("Codigo", 65, JLabel.CENTER), new FarmaColumnData("Descripcion", 220, JLabel.LEFT),
      new FarmaColumnData("Unid Presentacion", 110, JLabel.LEFT),
      new FarmaColumnData("Stock Actual", 75, JLabel.RIGHT), new FarmaColumnData("Diferencia", 75, JLabel.RIGHT),
      new FarmaColumnData("Precio", 75, JLabel.RIGHT), new FarmaColumnData("Laboratorio", 150, JLabel.RIGHT),
      new FarmaColumnData("ValFrac", 0, JLabel.RIGHT) };

    public static final Object[] defaultValuesListaDiferenciasConsolidadoDiario =
    { " ", " ", " ", " ", " ", " ", " ", " " };


    public static final FarmaColumnData[] columnsListaDiferenciasConsolidados =
    { new FarmaColumnData("Codigo", 65, JLabel.CENTER), new FarmaColumnData("Descripcion", 220, JLabel.LEFT),
      new FarmaColumnData("Unid Presentacion", 110, JLabel.LEFT),
        //new FarmaColumnData("Stock Actual", 75, JLabel.RIGHT),
        //new FarmaColumnData("Diferencia", 75, JLabel.RIGHT),
        //new FarmaColumnData("Precio", 65, JLabel.RIGHT),
        new FarmaColumnData("Laboratorio", 150, JLabel.LEFT)
        //new FarmaColumnData("Fecha", 150, JLabel.LEFT)
        } ;

    public static final Object[] defaultValuesListaDiferenciasConsolidados =
    { " ", " ", " ", " " }; //, " ", " "," " };


    public static String vNombreInHashtableDiferencias = "IND_CAMPO_ORDENAR_DIFERENCIAS";
    public static String[] vCodDiferencia = { "0", "1", "2", "3", "4", "5", "6" };
    public static String[] vDescCampoDiferencia =
    { "Codigo", "Descripcion", "Unidad Presentacion", "Stock Actual", "Diferencia", "Precio", "Laboratorio" };

    /**
     *
     */
    public static String NOM_HASTABLE_CMBESTADO_LAB = "CMBESTADO";


    public static final FarmaColumnData columnsListaProductosConteo[] =
    { new FarmaColumnData("Código Prod.", 75, JLabel.LEFT), new FarmaColumnData("Descripción", 150, JLabel.LEFT),
      new FarmaColumnData("Unid. Presentación", 110, JLabel.LEFT), new FarmaColumnData("Cant.Ent", 60, JLabel.RIGHT),
      new FarmaColumnData("Cant.Frac", 60, JLabel.RIGHT), new FarmaColumnData("Laboratorio", 120, JLabel.LEFT),
      new FarmaColumnData("Ingreso", 60, JLabel.CENTER),
    new FarmaColumnData( "", 0, JLabel.RIGHT ),
    new FarmaColumnData( "", 0, JLabel.RIGHT ),
      new FarmaColumnData( "Lote", 50, JLabel.LEFT ),
      new FarmaColumnData( "Fech. Vencimiento",75, JLabel.LEFT )
        } ;

    public static final Object[] defaultValuesListaProductosConteo = { " ", " ", " ", " ", " ", " ", " ", " ", " "," "," "};


    /**
     * Diferencias TOtales
     * dubilluz 29.12.2009
     */
    public static final FarmaColumnData[] columnsListaDiferenciasTotales =
    { new FarmaColumnData("Laboratorio", 130, JLabel.LEFT), new FarmaColumnData("Codigo", 65, JLabel.CENTER),
      new FarmaColumnData("Descripcion", 180, JLabel.LEFT), new FarmaColumnData("Unid Presentacion", 110, JLabel.LEFT),
      new FarmaColumnData("LOTE", 70, JLabel.RIGHT), new FarmaColumnData("Stock Actual", 75, JLabel.RIGHT),
      new FarmaColumnData("Diferencia", 75, JLabel.RIGHT),
      new FarmaColumnData("Precio", 65, JLabel.RIGHT)
        //OCULTOS
        //codigo_de_lab
        //Columna de Orden
        } ;

    public static final Object[] defaultValuesListaDiferenciasTotales =
    { " ", " ", " ", " ", " ", " ", " ", " ", " " , " "};

}
