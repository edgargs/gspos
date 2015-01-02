package mifarma.ptoventa.controlingreso.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : ConstantsControlIngreso.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * PAMEGHINO      10.10.2007   Creación<br>
 * <br>
 * @author PAULO CESAR AMEGHINO ROJAS<br>
 * @version 1.0<br>
 *
 */
public class ConstantsControlIngreso {
    public ConstantsControlIngreso() {
    }

    //PUERTO
    public static final String PUERTO = "1521";

    //PREPROD - PROD
    public static final String USUARIO_BD = "apps";

    //TEST
    //public static final String USUARIO_BD = "apps_prueba";

    //Lista Filtro
    public static final FarmaColumnData columnsListaFiltro[] =
    { new FarmaColumnData("Codigo", 70, JLabel.LEFT), new FarmaColumnData("Descripcion", 250, JLabel.LEFT), };

    public static final Object[] defaultValuesListaFiltro = { " ", " " };

    //Lista Maestros
    public static final FarmaColumnData columnsListaMaestros[] =
    { new FarmaColumnData("Codigo", 70, JLabel.LEFT), new FarmaColumnData("Descripcion", 220, JLabel.LEFT), };

    public static final Object[] defaultValuesListaMaestros = { " ", " " };

    public static final String[] FILTROS_PRODUCTOS_CODIGO = { "1", "2" };
    public static final String[] FILTROS_PRODUCTOS_DESCRIPCION = { "PRINCIPIO ACTIVO", "LABORATORIO" };

    //CONSTANTES DE LISTA DE MAESTROS
    public static final String LISTA_UNID_PRESENTACION = "1";
    public static final String LISTA_UNID_FRACCION = "2";
    public static final String LISTA_LAB = "3";
    public static final String LISTA_PROV_1 = "4";
    public static final String LISTA_PROV_2 = "5";
    public static final String LISTA_FACT_PREC = "6";
    public static final String LISTA_ACC_TERAP = "7";
    public static final String LISTA_TIPO_VTA = "8";
    public static final String LISTA_UNID_FRACCIONAMIENTO = "9";
    public static final String LISTA_CARGOS = "10";

    /**
     * NUEVAS VARIABLES PARA MANTENIMIENTO DE PROVEEDORES
     * @author Luis Reque Orellana
     * @since 03.04.2007
     * */
    public static final String LISTA_CTA_ASOC = "11";
    public static final String LISTA_GPO_TES = "12";
    public static final String LISTA_COND_PAGO = "13";
    public static final String LISTA_VIA_PAGO = "14";
    public static final String LISTA_MONEDA = "15";

    /**
     * NUEVAS VARIABLES PARA MANTENIMIENTO DE CONVENIOS
     * @author Luis Reque Orellana
     * @since 04.04.2007
     * */
    public static final String LISTA_EMPRESAS = "16";
    public static final String LISTA_CAMPOS_CONVENIO = "17";

    public static final String LISTA_TIPO_PRODUCTO = "18";

    /**
     * Listados Maestro de Control Equipos.
     * @author Edgar Rios Navarro
     * @since 27.07.2007
     */
    public static final String LISTA_SOP_LOCAL = "19";
    public static final String LISTA_SOP_ALMACEN = "20";
    public static final String LISTA_SOP_PROVEEDOR = "21";
    public static final String LISTA_TRANSPORTISTA = "22";

    public static final String LISTA_LINEA_QS = "23";

    public static final String MENSAJE_LOGIN = "Acceso a Administracion Central";

    /**
     * Estado de Pendiente.
     */
    public static final String ESTADO_PENDIENTE = "P";

    /**
     * Estado de Terminado.
     */
    public static final String ESTADO_TERMINADO = "T";

    /**
     * Estado de Terminado.
     */
    public static final String ESTADO_APROBADO = "A";

    /**
     * Estado de Rechazado.
     */
    public static final String ESTADO_RECHAZADO = "R";

    /**
     * Codigo de Local de Venta Institucional.
     */
    public static final String COD_LOCAL_VTA_INST = "998";

    /**
     * Se añadieron columnas de entrada y fecha  2
     * @author  dubilluz
     * @since   23.11.2007
     */
    //Lista Filtro
    public static final FarmaColumnData columnsListaRegistro[] =
    { new FarmaColumnData("DNI", 70, JLabel.LEFT), new FarmaColumnData("Nombre", /*450*/280, JLabel.LEFT),
      new FarmaColumnData("Hora Ingreso", 100, JLabel.LEFT), new FarmaColumnData("Hora Salida", 100, JLabel.LEFT),
      new FarmaColumnData("Hora Ingreso(2)", 100, JLabel.LEFT),
      new FarmaColumnData("Hora Salida (2)", 100, JLabel.LEFT), new FarmaColumnData("orden", 0, JLabel.LEFT) };

    public static final Object[] defaultValuesListaRegistro = { " ", " ", " ", " ", " ", " ", " " };

    public static final String CMB_TIPO_REG = "TIPO_REGISTRO";
    public static final String TIPO_ENTRADA = "01";
    public static final String TIPO_SALIDA = "02";


    /**
     * Ingreso Temperatura
     * @AUTHOR :JCORTEZ
     * @SINCE : 11.02.09
     * */

    public static final FarmaColumnData columnsListaHistTemp[] =
    { new FarmaColumnData("Sec", 30, JLabel.CENTER), new FarmaColumnData("Fecha. Creacion", 124, JLabel.CENTER),
      new FarmaColumnData("Usu. Crea", 110, JLabel.CENTER), new FarmaColumnData("Area Venta C°", 103, JLabel.CENTER),
      new FarmaColumnData("Almacen C°", 103, JLabel.CENTER),
      new FarmaColumnData("Refrigerador C°", 103, JLabel.CENTER) };
    public static final Object[] defaultValuesListaHistTemp = { " ", " ", " ", " ", " " };

    public static final String MENSAJE_ROL = "Usted no cuenta con el rol adecuado.";
    public static final String ROL_QF_ADMINLOCAL = "011";
    public static final String ROL_CAJERO = "009";
    public static final String ROL_VENDEDOR = "010";
}
