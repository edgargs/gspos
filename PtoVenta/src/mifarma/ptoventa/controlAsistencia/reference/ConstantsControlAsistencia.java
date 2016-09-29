package mifarma.ptoventa.controlAsistencia.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;

public class ConstantsControlAsistencia {
    
    
    public static final String REP_LUNES="1";
    public static final String REP_MARTES_VIERNES="2@3@4@5";
    public static final String REP_SABADO="6";
    public static final String REP_DOMINGO="7";
    
    public static final String COD_ROL="000";
    public static final String COD_USU="000";
    public static final String COD_HORA="000";
    public static final String COD_DESCANSO="246";
    public static final String COD_VACACIONES="248";
    public static final String COD_SUBSIDIO="249";
    public static final String COD_CESADO="250";
    public static final String NOM_DESCANSO="DESCANSO";
    public static final String NOM_VACACIONES="VACACIONES";
    public static final String NOM_SUBSIDIO="SUBSIDIO";
    public static final String NOM_CESADO="CESADO";
    public static final String FORMT_HORA="--:--/--:--";
    public static final String HORA_DEFAULT="00:00"+"-"+"00:00";
    public static final int COD_LIMITE=7;
    public static final int LOG_MAX=8;
    public static final int NUM_CERO=0;
    public static final String COD_JEF="011";
    public static final String COD_VEN="010";
    public static final String COD_CAJ="009";
    public static final String JEF_LOCAL="JEF.LOCAL";
    public static final String TEN_VEND="TEC.VENDE";
    public static final String CAJE="CAJE.";
    
    public static final String ROL_CONTROL_INTERNO = "058";
    public static final String ROL_GNC = "059";
    public static final String ROL_DERMOCONSULTOR = "060";    
    
    
    public static final FarmaColumnData[] columnsListaHorario =
    { new FarmaColumnData("Nº Horario", 80, JLabel.CENTER),
      new FarmaColumnData("Desde", 150, JLabel.CENTER),
      new FarmaColumnData("Hasta", 150, JLabel.CENTER),
      new FarmaColumnData("Fecha Creación",120, JLabel.CENTER),
      new FarmaColumnData("Estado",100, JLabel.CENTER),
      new FarmaColumnData("",0, JLabel.CENTER)
      };
    public static final Object[] defaultValuescolumnsListaHorario = {  " ", " ", " ", " ", " "," "};
    public static final FarmaColumnData[] ColumnListaDetHorario={
    new FarmaColumnData("PERFIL",85,JLabel.LEFT),
    new FarmaColumnData("USUARIO",85,JLabel.LEFT),
    new FarmaColumnData("LUNES",75,JLabel.CENTER),
    new FarmaColumnData("MARTES",75,JLabel.CENTER),
    new FarmaColumnData("MIERCOLES",75,JLabel.CENTER),
    new FarmaColumnData("JUEVES",75,JLabel.CENTER),
    new FarmaColumnData("VIERNES",75,JLabel.CENTER),
    new FarmaColumnData( "SABADO",75,JLabel.CENTER),
    new FarmaColumnData("DOMINGO",75,JLabel.CENTER)
    
    };
    public static final Object[] defaultValuescolumnsDetHorario = {  " ", " ", " ", " ", " "," "," "," "," "};
    
    public static String INGRESARDNI="Ingresar Dni";
    
    public static String INGRESARHUELLA="Escanear Huella";

    
    public static final FarmaColumnData[] columnsListaMaracion =
    { new FarmaColumnData("DOC_IDENTIDAD", 120, JLabel.CENTER),
      new FarmaColumnData("DATOS_PERSONALES", 150, JLabel.LEFT),
      new FarmaColumnData("FECHA",70, JLabel.CENTER),
      new FarmaColumnData("ENTRADA",130, JLabel.CENTER), 
      new FarmaColumnData("SALIDA",130, JLabel.CENTER),
      new FarmaColumnData("IND_SALIDA",0, JLabel.CENTER),
      new FarmaColumnData("CANT_MARCACION",0, JLabel.CENTER),
      new FarmaColumnData("FECHA_SUGERIDA",0, JLabel.CENTER),
      new FarmaColumnData("HORA_SUGERIDA",0, JLabel.CENTER),
     
      };

    public static final Object[] defaultValuescolumnsListaMaracion = { " ", " ", " ", " ", " ", " "," "," "," "};
    public static String NULLO="-";
    public static String INDSALIDA_1="0";
    public static String INDSALIDA_2="1";
    public static String IND_HABILITA="A";
    public static String IND_COMPARA="I";
    
    public static String IND_APROBACION="2";
    public static String VALOR_REG_SALIDA="4";
    public static String COD_TIPO_SOLICITUD="19";
    public static String COD_SUBTIPO_SOLICITUD="20";
    
    public static final FarmaColumnData[] columnsListaPlantilla =
    {
        new FarmaColumnData("Nº", 0, JLabel.CENTER),
        new FarmaColumnData("Nombre Plantilla", 180, JLabel.LEFT),
        new FarmaColumnData("Fecha Creación",125, JLabel.CENTER), 
        new FarmaColumnData("Usuario Creación",140, JLabel.CENTER),
        new FarmaColumnData("Estado",130, JLabel.CENTER),
        new FarmaColumnData("",0, JLabel.CENTER)
      };

    public static final Object[] defaultValuescolumnsListaPlantilla = { " ", " ", " ", " ", " ", " "};
    
    public static final FarmaColumnData[] columnsListaPlantillaDetalle={
        new FarmaColumnData("PERFIL",110,JLabel.CENTER),
      
        new FarmaColumnData( "LUNES",82,JLabel.CENTER),
        new FarmaColumnData("MARTES",82,JLabel.CENTER),
        new FarmaColumnData( "MIERCOLES",82,JLabel.CENTER),
        new FarmaColumnData("JUEVES",82,JLabel.CENTER),
        new FarmaColumnData("VIERNES",82,JLabel.CENTER),
        new FarmaColumnData( "SABADO",82,JLabel.CENTER),
        new FarmaColumnData("DOMINGO",82,JLabel.CENTER),
    };
    public static final Object[] defaultValuescolumnsListaPlantDet = { " ", " ", " ", " ", " "," "," "," "};
    public static final Object[] defaultColumnNamePlantMante(){
        Object[] colNameDetMan={
        "CODIGO",
        "PERFIL",
        "LUNES",
        "MARTES",
        "MIERCOLES",
        "JUEVES",
        "VIERNES",
        "SABADO",
        "DOMINGO",
        "COD_LUNES",
        "COD_MARTES",
        "COD_MIERCO",
        "COD_JUEVES",
        "COD_VIER",
        "COD_SAB",
        "COD_DOMI",
        "COD_SEC",
        "REF_LUNES",
        "REF_MARTES",
        "REF_MIERCO",
        "REF_JUEVES",
        "REF_VIER",
        "REF_SAB",
        "REF_DOMI",
        "CANT_HRS"   
        };
        return colNameDetMan;
    }
    
    public static final String ACCION_INSERTAR="I";
    public static final String ACCION_MODIFICAR="M";
    public static final FarmaColumnData[] columnsListaSolicitud={
    
      new FarmaColumnData("Dni", 70, JLabel.CENTER), 
      new FarmaColumnData("Usuario", 100, JLabel.LEFT), 
      new FarmaColumnData("Fecha Inicio", 85, JLabel.CENTER), 
      new FarmaColumnData("Fecha Fin", 85, JLabel.CENTER),
      new FarmaColumnData("Estado Solicitud", 100, JLabel.CENTER) ,
      new FarmaColumnData("Tipo Solicitud",130, JLabel.LEFT) ,
      new FarmaColumnData("Sub Tipo Solicitud",120, JLabel.CENTER) 
      
    };
    public static final Object[] defaultListaSolicitud={"","","","","","",""};
      
    public static final FarmaColumnData[] columnsListaInasistencia={
    
      new FarmaColumnData("Dni", 70, JLabel.CENTER), 
      new FarmaColumnData("Nombre", 280, JLabel.LEFT), 
      new FarmaColumnData("Nro Inasistencias", 120, JLabel.CENTER), 
      
    };
    public static final Object[] defaultListaInasistencia={"","",""};
    
    public static final FarmaColumnData[] columnsListaTurno =
    { new FarmaColumnData("", 0, JLabel.CENTER),
      new FarmaColumnData("Descripción", 110, JLabel.CENTER),
      new FarmaColumnData("Inicio",80, JLabel.CENTER), 
      new FarmaColumnData("Fin",80, JLabel.CENTER),
      new FarmaColumnData("Min.Refrigerio",100, JLabel.CENTER),
      new FarmaColumnData("Hrs. Labor",100, JLabel.RIGHT)
      };

    public static final Object[] defaultValuescolumnsListaTurno = { " ", " ", " ", " ", " ", " "};
    
    
    
    
    
    public static final String RESULTADO_GRABAR_CLIENTE_EXITO = "TRUE";
    public static final String RESULTADO_GRABAR_CLIENTE_ERROR = "FALSE";
 //SOLICITUD 19/09/2015
    public static final int CANTIDAD_DIGITOS=200;
    public static final String TIPO_VACACIONES="1";
    public static final String TIPO_SUBSIDIO="2";
    public static final String TIPO_CESE="3";
    public static final String TIPO_CORRECCION="5";
    public static final String DES_TIPO_SUBSIDIO="Tipo Subsidio:";
    public static final String DES_TIPO_CESE="Tipo Cese:";
    public static final int IND_CODIGO=10;
    public static final String FTP="FTP";
    //INI ASOSA - 11/09/2015 - CTRLASIST
    public static final int ID_TAB_IND_ACTIVO_NEW_CTRL_ASIST = 737;
    public static final int ID_TAB_IND_ACTIVO_REG_MARC_SALIDA = 724;
    public static final String ID_ENTRADA = "E";
    public static final String ID_SALIDA = "S";
    public static final String IND_REGULARIZAR = "R";    
    //FIN ASOSA - 14/09/2015 - CTRLASIST
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

    public static final String NOM_HASTABLE_CMBESTSOL = "CO_EST_SOL"; 
    public static final String[] vCodEstadoSol = {"", "C", "E", "A", "R", "G"};
    public static final String[] vDescEstadoSol = {"TODOS", "CREADO", "ENVIADO", "APROBADO", "RECHAZADO", "REGISTRADO"};

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
    public static final String COD_ENTRADA="01";
    public static final String COD_SALIDA="02";
    
}
