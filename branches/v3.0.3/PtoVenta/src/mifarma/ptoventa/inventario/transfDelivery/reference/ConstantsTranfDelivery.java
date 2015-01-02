package mifarma.ptoventa.inventario.transfDelivery.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : ConstantsTranfDelivery.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCALLO      20.10.2008   Creación<br>
 * <br>
 * @author JCALLO<br>
 * @version 1.0<br>
 *
 */
public class ConstantsTranfDelivery {
    public ConstantsTranfDelivery() {
    }

    public static final FarmaColumnData[] columnsListaTransDelivery =
    { new FarmaColumnData("Nro Pedido", 80, JLabel.LEFT), new FarmaColumnData("Local Destino", 150, JLabel.LEFT),
      new FarmaColumnData("Items", 50, JLabel.RIGHT), new FarmaColumnData("Fecha Pedido", 120, JLabel.LEFT) };

    public static final Object[] defaultcolumnsListaTransDelivery = { " ", " ", " ", " " };

    public static final FarmaColumnData columnsListaProductosTransfDetalle[] =
    { new FarmaColumnData("Codigo", 50, JLabel.LEFT), new FarmaColumnData("Descripcion", 200, JLabel.LEFT),
      new FarmaColumnData("Unidad", 90, JLabel.LEFT), new FarmaColumnData("Laboratorio", 150, JLabel.LEFT),
      new FarmaColumnData("Cantidad", 60, JLabel.RIGHT), new FarmaColumnData("Lote", 80, JLabel.RIGHT),
      new FarmaColumnData("Fec. Vcto", 70, JLabel.RIGHT)
        //new FarmaColumnData( "Fec. Pedido", 70, JLabel.RIGHT )
        } ;

    public static final Object[] defaultcolumnsListaProductosTransfDetalle = { " ", " ", " ", " ", " ", " ", " " };

    public static final String ESTADO_GENERADO = "G";

}
