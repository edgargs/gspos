package mifarma.ptoventa.administracion.impresoras.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;


/**
 * Copyright (c) 2009 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaIPSImpresora.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCHAVEZ 26.06.2009 Modificación<br>
 * <br>
 * @version 1.0<br>
 *
 */

public class ConstantsImpresoras {
    public ConstantsImpresoras() {
    }

    public static final FarmaColumnData[] columnsListaImpresoras =
    { new FarmaColumnData("Cod. Imp.", 70, JLabel.CENTER), new FarmaColumnData("Desc. Imp", 120, JLabel.LEFT),
      new FarmaColumnData("Comprobante", 80, JLabel.LEFT), new FarmaColumnData("Serie", 40, JLabel.CENTER),
      new FarmaColumnData("Nro. de comprobante", 120, JLabel.CENTER),
      new FarmaColumnData("Cola de impresión", 140, JLabel.LEFT), new FarmaColumnData("Estado", 60, JLabel.CENTER),
      new FarmaColumnData("tip comp", 0, JLabel.LEFT), new FarmaColumnData("Serie Impresora", 0, JLabel.LEFT) };

    public static final Object[] defaultValuesListaImpresoras = { " ", " ", " ", " ", " ", " ", " ", " ", " " };
    //JMIRANDA 26/06/2009
    public static final FarmaColumnData[] columnsListaImpresorasTermicas =
    { new FarmaColumnData("Sec. Imp.", 70, JLabel.CENTER), new FarmaColumnData("Desc. Imp", 140, JLabel.LEFT),
      new FarmaColumnData("Marca", 90, JLabel.LEFT), new FarmaColumnData("Estado", 90, JLabel.LEFT) };

    public static final Object[] defaultValuesListaImpresorasTermicas = { " ", " ", " ", " ", " ", " " };


    public static final FarmaColumnData[] columnsListaImpresorasCaja =
    { new FarmaColumnData("Nro. de Caja", 70, JLabel.CENTER), new FarmaColumnData("Nro. Prn", 80, JLabel.LEFT),
      new FarmaColumnData("Comprobante", 80, JLabel.CENTER), new FarmaColumnData("Estado", 60, JLabel.LEFT), };

    public static final Object[] defaultValuesListaImpresorasCaja = { " ", " ", " ", " " };

    /**
     * Listado de IPs por impresora
     * @AUTHOR JCORTEZ
     * @SINCE  05.06.09*/
    // KMONCADA CAMBIOS DE FORMULARIO DE MUESTRA DE DATOS
    public static final FarmaColumnData[] columnsListaIPS =
    { new FarmaColumnData("Sec", 35, JLabel.CENTER), new FarmaColumnData("Nro. IP", 140, JLabel.LEFT), //60
        new FarmaColumnData("Impresora Boletas", 200, JLabel.LEFT),
        new FarmaColumnData("Tipo Boletas", 0, JLabel.LEFT),
        new FarmaColumnData("Impresora Facturas", 200, JLabel.LEFT),
        new FarmaColumnData("Tipo Facturas", 0, JLabel.LEFT), //JCHAVEZ 25.06.09.n
        new FarmaColumnData("Impresora Termica", 180, JLabel.LEFT) //JCHAVEZ 25.06.09.n
        } ;

    public static final Object[] defaultValuesListaIPS = { " ", " ", " ", " ", " ", " ", " " }; //JCG (3)

    /**
     * Listado de Impresoras disponibles
     * @AUTHOR JCORTEZ
     * @SINCE  05.06.09*/

    public static final FarmaColumnData[] columnsListaImpr =
    { new FarmaColumnData("Sec", 30, JLabel.CENTER), new FarmaColumnData("Nro.Ser", 50, JLabel.CENTER),
      new FarmaColumnData("Descripcion", 130, JLabel.LEFT), new FarmaColumnData("Ruta", 175, JLabel.LEFT),
      new FarmaColumnData("Tipo", 120, JLabel.LEFT), new FarmaColumnData("", 0, JLabel.LEFT) }; //tip comp
    public static final Object[] defaultValuesListaImpr = { " ", " ", " ", " ", " ", " " };

    /**
     * Listado de Impresoras térmicas disponibles
     * @AUTHOR JCHAVEZ
     * @SINCE  25.06.09*/
    public static final FarmaColumnData[] columnsListaImpresoraTermica =
    { new FarmaColumnData("Sec", 30, JLabel.CENTER), new FarmaColumnData("Descripcion", 150, JLabel.LEFT),
      new FarmaColumnData("Modelo", 100, JLabel.LEFT) };
    public static final Object[] defaultValuesListaImpresoraTermica = { " ", " ", " " };

}
