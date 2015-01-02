package mifarma.ptoventa.cliente.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : ConstantsCliente.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      23.02.2006   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class ConstantsCliente {

    public ConstantsCliente() {
    }

    //Lista de Clientes Juridicos
    public static final FarmaColumnData columnsListaClientesJuridicos[] =
    { new FarmaColumnData("Codigo", 70, JLabel.CENTER), new FarmaColumnData("Documento", 100, JLabel.CENTER),
      new FarmaColumnData("Cliente", 220, JLabel.LEFT), new FarmaColumnData("Direccion", 280, JLabel.LEFT), };
    public static final Object[] defaultValuesListaClientesJuridicos = { " ", " ", " ", " " };

    public static final FarmaColumnData columnsListaTarjetasClientes[] =
    { new FarmaColumnData("Operador", 70, JLabel.CENTER), new FarmaColumnData("Numero", 100, JLabel.LEFT),
      new FarmaColumnData("Nombre Tarjeta", 150, JLabel.LEFT),
      new FarmaColumnData("Fecha Vencimiento", 150, JLabel.LEFT), new FarmaColumnData("Codigo", 1, JLabel.LEFT),
      new FarmaColumnData("Mes", 1, JLabel.LEFT), new FarmaColumnData("Año", 1, JLabel.LEFT), };
    public static final Object[] defaultValuesListaTarjetasClientes = { " ", " ", " ", " ", " ", " ", " " };

    public static final String TIPO_BUSQUEDA_RUC = "1";
    public static final String TIPO_BUSQUEDA_RAZSOC = "2";
    public static final String TIPO_BUSQUEDA_DNI = "3";
    public static final String TIPO_BUSQUEDA_NOMBRE = "4";
    public static final String TIPO_JURIDICO = "02";
    public static final String TIPO_NATURAL = "01";
    public static final String RESULTADO_GRABAR_CLIENTE_EXITO = "1";
    public static final String RESULTADO_GRABAR_CLIENTE_ERROR = "2";
    public static final String RESULTADO_RUC_VALIDO = "TRUE";
    public static final String RESULTADO_RUC_INVALIDO = "FALSE";
    public static final String ACCION_INSERTAR = "I";
    public static final String ACCION_MODIFICAR = "M";

}

