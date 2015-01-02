package mifarma.ptoventa.inventariociclico.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : ConstantsInvCiclico.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      23.10.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class ConstantsInvCiclico {
    public ConstantsInvCiclico() {
    }

    /**
     * Columnas de la lista de productos inicio.
     * @author Edgar Rios Navarro
     * @since 23.10.2006
     */
    public static final FarmaColumnData[] columnsListaProductosInicio =
    { new FarmaColumnData("Codigo", 50, JLabel.CENTER), new FarmaColumnData("Descripcion", 200, JLabel.LEFT),
      new FarmaColumnData("Laboratorio", 150, JLabel.LEFT), new FarmaColumnData("Presentacion", 100, JLabel.LEFT),
      new FarmaColumnData("Unid. Venta", 100, JLabel.LEFT),
        //    new FarmaColumnData("U. Vends.", 50, JLabel.RIGHT),
        new FarmaColumnData("Stock", 75, JLabel.RIGHT),
        //new FarmaColumnData("Monto Tot.", 70, JLabel.RIGHT),
        new FarmaColumnData("Tip", 30, JLabel.CENTER), new FarmaColumnData("", 0, JLabel.CENTER) //UNIDAD PRESENTACION
        } ;

    /**
     * Valores por defecto de la lista de productos inicio.
     * @author Edgar Rios Navarro
     * @since 23.10.2006
     */
    public static final Object[] defaultValuesListaProductosInicio = { " ", " ", " ", " ", " ", " ", " ", " " };

    /**
     * Columnas de la lista de productos restantes para el invetario ciclico.
     * @author Edgar Rios Navarro
     * @since 24.10.2006
     */
    public static final FarmaColumnData[] columnsListaProductosInv =
    { new FarmaColumnData("Sel.", 30, JLabel.CENTER), new FarmaColumnData("Codigo", 50, JLabel.CENTER),
      new FarmaColumnData("Descripcion", 200, JLabel.LEFT), new FarmaColumnData("Presentacion", 100, JLabel.LEFT),
      new FarmaColumnData("Unid. Venta", 100, JLabel.LEFT), new FarmaColumnData("Stock", 75, JLabel.RIGHT),
        //new FarmaColumnData("U. Vends.", 50, JLabel.RIGHT),
        //new FarmaColumnData("Monto Tot.", 70, JLabel.RIGHT),
        new FarmaColumnData("CodLab", 0, JLabel.RIGHT) };

    /**
     * Valores por defecto de la lista de productos restantes para el invetario ciclico.
     * @author Edgar Rios Navarro
     * @since 24.10.2006
     */
    public static final Object[] defaultValuesListaProductosInv = { " ", " ", " ", " ", " ", " ", " " };


    public static final FarmaColumnData[] columnsListaDiferenciasConsolidado =
    { new FarmaColumnData("Codigo", 65, JLabel.CENTER), new FarmaColumnData("Descripcion", 220, JLabel.LEFT),
      new FarmaColumnData("Unid Presentacion", 110, JLabel.LEFT),
      new FarmaColumnData("Stock Actual", 75, JLabel.RIGHT), new FarmaColumnData("Diferencia", 75, JLabel.RIGHT),
      new FarmaColumnData("Precio", 65, JLabel.RIGHT), new FarmaColumnData("Laboratorio", 150, JLabel.LEFT) };

    public static final Object[] defaultValuesListaDiferenciasConsolidado = { " ", " ", " ", " ", " ", " ", " " };

    public static String vNombreInHashtableDiferencias = "IND_CAMPO_ORDENAR_DIFERENCIAS";
    public static String[] vCodDiferencia = { "0", "1", "2", "3", "4", "5", "6" };
    public static String[] vDescCampoDiferencia =
    { "Codigo", "Descripcion", "Unidad Presentacion", "Stock Actual", "Diferencia", "Precio", "Laboratorio" };

    public static final String TIPO_OPERACION_TOMA_INV = "I";
    public static final String TIPO_OPERACION_CONSULTA_HIST = "H";
    public static final String TIPO_FARMA = "01";
    public static final String TIPO_NO_FARMA = "02";
    public static final String TIPO_TODOS = "03";
    public static final String TIPO_PRINCIPIO_ACTIVO = "1";
    public static final String TIPO_ACCION_TERAPEUTICA = "2";
    public static final String TIPO_LABORATORIO = "3";

    public static final FarmaColumnData[] columnsTomasInventario =
    { new FarmaColumnData("Nro Toma", 65, JLabel.LEFT), new FarmaColumnData("Tipo Toma", 90, JLabel.LEFT),
      new FarmaColumnData("Fecha Inicio", 125, JLabel.LEFT), new FarmaColumnData("Estado", 90, JLabel.LEFT),
      new FarmaColumnData("", 0, JLabel.LEFT) };

    public static final Object[] defaultValuesTomasInventario = { " ", " ", " ", " ", " " };

    public static final FarmaColumnData[] columnsLaboratoriosToma =
    { new FarmaColumnData("Codigo", 80, JLabel.LEFT), new FarmaColumnData("Laboratorio", 250, JLabel.LEFT),
      new FarmaColumnData("Estado", 100, JLabel.LEFT), };

    public static final Object[] defaultValuesLaboratoriosToma = { " ", " ", " " };

    public static final FarmaColumnData[] columnsListaProductosXLaboratorio =
    { new FarmaColumnData("Codigo", 70, JLabel.CENTER), new FarmaColumnData("Descripcion", 235, JLabel.LEFT),
      new FarmaColumnData("Unid. Presentacion", 120, JLabel.LEFT), new FarmaColumnData("C. Ent", 45, JLabel.RIGHT),
      new FarmaColumnData("C. Frac", 45, JLabel.RIGHT), new FarmaColumnData("Valor Frac", 0, JLabel.RIGHT),
      new FarmaColumnData("Unid. Venta", 0, JLabel.RIGHT), };
    public static final Object[] defaultValuesListaProductosXLaboratorio = { " ", " ", " ", " ", " ", " ", " " };

    public static final FarmaColumnData[] columnsListaMovsKardex =
    { new FarmaColumnData("Fecha", 110, JLabel.CENTER), new FarmaColumnData("Descripcion", 182, JLabel.LEFT),
      new FarmaColumnData("Tip. Doc", 60, JLabel.LEFT), new FarmaColumnData("Num. Doc.", 90, JLabel.CENTER),
      new FarmaColumnData("Mov", 40, JLabel.RIGHT), new FarmaColumnData("Val. Fracc.", 60, JLabel.RIGHT) };
    public static final Object[] defaultListaMovsKardex = { " ", " ", " ", " ", " ", " " };

    public static final FarmaColumnData[] columnsListaDiferenciasProductos =
    { new FarmaColumnData("Codigo", 65, JLabel.CENTER), new FarmaColumnData("Descripcion", 220, JLabel.LEFT),
      new FarmaColumnData("Unid Presentacion", 110, JLabel.LEFT),
      new FarmaColumnData("Stock Actual", 75, JLabel.RIGHT), new FarmaColumnData("Diferencia", 75, JLabel.RIGHT),
      new FarmaColumnData("Precio", 65, JLabel.RIGHT), };

    public static final Object[] defaultValuesListaDiferenciasProductos = { " ", " ", " ", " ", " ", " " };

}
