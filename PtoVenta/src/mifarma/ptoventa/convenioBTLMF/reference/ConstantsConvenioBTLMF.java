package mifarma.ptoventa.convenioBTLMF.reference;

import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;


public class ConstantsConvenioBTLMF {
    public ConstantsConvenioBTLMF() {
    }


    //Lista Convenios
    public static final FarmaColumnData columnsListaConvenios[] =
    { new FarmaColumnData("Código", 100, JLabel.CENTER), new FarmaColumnData("Descripción", 481, JLabel.LEFT), };
    public static final Object[] defaultValuesListaConvenios = { " ", " ", " ", " " };

    //Datos Convenio
    public static final FarmaColumnData columnsConvenio[] =
    { new FarmaColumnData("Descripción", 481, JLabel.LEFT), new FarmaColumnData("Ind_beneficiario", 10, JLabel.LEFT),
      new FarmaColumnData("Ind_repartidor", 10, JLabel.LEFT), new FarmaColumnData("Ind_diagnostico", 10, JLabel.LEFT),
      new FarmaColumnData("Ind_medico", 10, JLabel.LEFT), new FarmaColumnData("Ind_adicional", 10, JLabel.LEFT) };
    public static final Object[] defaultValuesConvenio = { " ", " ", " ", " ", " ", " " };


    //Benificiario
    public static final FarmaColumnData columnsListaBenificiario[] =
    { new FarmaColumnData("DNI", 100, JLabel.CENTER), new FarmaColumnData("Nombre", 320, JLabel.LEFT),
      new FarmaColumnData("Linea Crédito", 100, JLabel.LEFT), new FarmaColumnData("Estado", 60, JLabel.LEFT), };

    public static final Object[] defaultValuesListaBenificiario =
    { " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };

    //Repartidor
    public static final FarmaColumnData columnsListaRepartidor[] =
    { new FarmaColumnData("Código", 100, JLabel.LEFT), new FarmaColumnData("Repartidor", 450, JLabel.LEFT) };
    public static final Object[] defaultValuesListaRepartidor = { " ", " " };

    //Medico
    //ERIOS 2.4.7 Se agrega codigo de medico
    public static final FarmaColumnData columnsListaMedico[] =
    { new FarmaColumnData("Código", 100, JLabel.CENTER), new FarmaColumnData("Nombre y Apellido", 460, JLabel.LEFT),
      new FarmaColumnData("", 0, JLabel.LEFT) //Codigo
        } ;
    public static final Object[] defaultValuesListaMedico = { " ", " ", " " };

    //Clinica
    public static final FarmaColumnData columnsListaClinica[] = { new

        FarmaColumnData("Código", 100, JLabel.CENTER), new FarmaColumnData("Clínica", 460, JLabel.LEFT), };
    public static final Object[] defaultValuesListaClinica = { " ", " " };

    //ERIOS 2.4.6 Seleccion multi-diagnostico
    //Datos Diagnostico
    public static final FarmaColumnData columnsListaDiagnostico[] =
    { new FarmaColumnData("Sel", 30, JLabel.CENTER), new FarmaColumnData("CIE 10", 80, JLabel.CENTER),
      new FarmaColumnData("Diagnóstico", 460, JLabel.LEFT), new FarmaColumnData("", 0, JLabel.LEFT) //Codigo
        } ;
    public static final Object[] defaultValuesListaDiagnostico = { " ", " ", " ", " " };


    //Datos Adicionales
    public static final FarmaColumnData columnsListaDatosAdic[] =
    { new FarmaColumnData("Código", 100, JLabel.CENTER), new FarmaColumnData("Descripción", 481, JLabel.LEFT), };
    public static final Object[] defaultValuesListaDatosAdic = { " ", " " };


    public static final String NOM_HASTABLE_CONVENIO = "CMB_CONVENIO";

    public static final String FLG_DOC_RETENCION = "1";
    public static final String FLG_DOC_VERIFICACION = "0";
    public static final String FLG_DOC_SOLUCION = "2";

    /**
     * DUBILLUZ 26.10.2011
     * CONSTANTES  de los nombres del Map de los datos del Convenio.
     */
    public static final String COL_CODIGO_CAMPO = "COD_TIPO_CAMPO";
    public static final String COL_NOMBRE_CAMPO = "DES_TIPO_CAMPO";
    public static final String COL_LONG_MAX = "CTD_LONG_MAX";
    public static final String COL_LONG_MIN = "CTD_LONG_MIN";
    public static final String COL_CREA_OBJ = "IND_CREA_OBJETO";
    public static final String COL_EDITABLE = "EDITABLE";
    public static final String COL_INVOCA_LISTA = "INVOCA_LISTA";
    public static final String COL_TIPO_OBJ = "CLASE_OBJETO";
    public static final String COL_ORDEN = "ORDEN";
    public static final String COL_VALOR_IN = "DATO_INGRESADO";
    public static final String COL_POS = "POSICION";
    public static final String COL_CAMPO_MSJ = "DESC_CAMPO_MENSAJE";
    public static final String COL_FLG_LISTA = "FLG_LISTA";
    public static final String COL_COD_VALOR_IN = "COD_VALOR_IN";


    /*
    ********************************
      DATOS DE INGRESO DE CONVENIO
    ********************************
    INGRESO_TEXTO
      JTextField
    LISTA_PANTALLA
      JTextField - Este Ingresara el Codigo de busqueda
      DlgPantallaBusqueda
                 - Este Tendra un TextField y una Tabla
    LISTA_COMBO
      JCombox - Que listara las opciones.
    PANTALLA_SELECCION
      Tendra una pantalla Vacia para Seleccionar y llenar los datos.
             Solo para uso de Diagnostico UIE.

     * */
    public static final String OBJ_IN_TEXTO = "INGRESO_TEXTO";
    public static final String OBJ_LISTA_PANTALLA = "LISTA_PANTALLA";
    public static final String OBJ_LISTA_COMBO = "LISTA_COMBO";
    public static final String OBJ_PANT_SELECCION = "PANTALLA_SELECCION";

    /**
     * FRAMIREZ 27.10.2011
     * CONSTANTES  los nombres del los campos de la Pantalla de Mensajes
     */
    public static final String COL_FACTOR_ALTO = "FACTOR_ALTURA";
    public static final String COL_FACTOR_ANCHO = "FACTOR_ANCHO";

    public static final String PANTALLA_POS_IZQ = "I";
    public static final String PANTALLA_POS_DER = "D";
    public static final String PANTALLA_POS_ABA = "A";

    public static final String PANTALLA_ABA_PIXEL_ALTO = "PANT_ABA_PIX_ALTO";
    public static final String PANTALLA_ABA_PIXEL_ANCHO = "PANT_ABA_PIX_ANCHO";
    public static final String PANTALLA_DER_PIXEL_ALTO = "PANT_DER_PIX_ALTO";
    public static final String PANTALLA_DER_PIXEL_ANCHO = "PANT_DER_PIX_ANCHO";
    public static final String PANTALLA_IZQ_PIXEL_ALTO = "PANT_IZQ_PIX_ALTO";
    public static final String PANTALLA_IZQ_PIXEL_ANCHO = "PANT_IZQ_PIX_ANCHO";

    public static final String COD_DATO_CONV_DIAGNOSTICO_UIE = "D_004";
    public static final String COD_DATO_CONV_BENIFICIARIO = "D_000";
    public static final String COD_DATO_CONV_FEC_VENCIMIENTO = "D_003";
    public static final String COD_DATO_CONV_REPARTIDOR = "D_001";
    public static final String COD_DATO_CONV_MEDICO = "D_005";
    public static final String COD_DATO_CONV_ORIG_RECETA = "D_002";
    public static final String COD_DATO_NOMBRE_BENEFICIARIO = "0000000011";


    /**
     * FRAMIREZ 16.11.2011
     * CONSTANTES  de los nombres del Map de los datos del Convenio.
     */

    public static final String COL_COD_CONVENIO = "COD_CONVENIO";
    public static final String COL_DES_CONVENIO = "DES_CONVENIO";
    public static final String COL_COD_CONVENIO_REL = "COD_CONVENIO_REL";
    public static final String COL_FLG_CREACION_CLIENTE = "FLG_CREACION_CLIENTE";
    public static final String COL_FLG_TIPO_CONVENIO = "FLG_TIPO_CONVENIO";
    public static final String COL_FLG_VALIDA_LINCRE_BENEF = "FLG_VALIDA_LINCRE_BENEF";
    public static final String COL_FLG_DATA_RIMAC = "FLG_DATA_RIMAC";
    public static final String COL_FLG_IMPRIME_IMPORTES = "FLG_IMPRIME_IMPORTES";
    public static final String COL_IND_VTA_COMPLEMENTARIA = "IND_VTA_COMPLEMENTARIA";
    public static final String COL_RUC = "RUC";
    public static final String COL_INSTITUCION = "INSTITUCION";
    public static final String COL_DIRECCION = "DIRECCION";
    public static final String COL_MTO_LINEA_CRED_BASE = "MTO_LINEA_CRED_BASE";


    public static final String FLG_TIP_CONV_CONTADO = "2";
    public static final String FLG_TIP_CONV_CREDITO = "3";


    /**
     * FRAMIREZ 16.11.2011
     * CONSTANTES  de los nombres del Map de los datos del Benificiario.
     */

    public static final String COL_COD_BARRA = "COD_BARRA";
    public static final String COL_COD_CLIENTE = "COD_CLIENTE";
    public static final String COL_DNI = "DNI";
    public static final String COL_DES_NOM_CLIENTE = "DES_NOM_CLIENTE";
    public static final String COL_DES_APE_CLIENTE = "DES_APE_CLIENTE";
    public static final String COL_FLG_CREA_CLIENTE = "FLG_CREACION";

    public static final String COL_NUM_POLIZA = "NUM_POLIZA";
    public static final String COL_NUM_PLAN = "NUM_PLAN";
    public static final String COL_COD_ASEGURADO = "COD_ASEGURADO";
    public static final String COL_NUM_IEM = "NUM_ITEM";
    public static final String COL_PRT = "PRT";
    public static final String COL_NUM_CONTRATO = "NUM_CONTRATO";
    public static final String COL_TIPO_ASEGURADO = "TIPO_ASEGURADO";
    public static final String COL_LCREDITO = "LCREDITO";
    public static final String COL_ESTADO = "ESTADO";


    /**
     * FRAMIREZ 16.11.2011
     * CONSTANTES  de los nombres del Map de los datos del Medico.
     */

    public static final String COL_COD_MEDICO = "COD_MEDICO";
    public static final String COL_NUM_CMP = "NUM_CMP";

    public static final String COL_DES_NOM_MEDICO = "DES_NOM_MEDICO";
    public static final String COL_DES_APE_MEDICO = "DES_APE_MEDICO";

    /**
     * FRAMIREZ 16.11.2011
     * CONSTANTES  de los nombres del Map de los datos del Diagnostico.
     */

    public static final String COL_COD_CIE_10 = "COD_CIE_10";
    public static final String COL_DES_DIAGNOSTICO = "DES_DIAGNOSTICO";
    public static final String COD_DIAGNOSTICO = "COD_DIAGNOSTICO";


    /**
     * FRAMIREZ 16.11.2011
     * CONSTANTES  de las clases de objetos a validar.
     */

    public static final String CLASE_DNI = "1";
    public static final String CLASE_EMAIL = "2";
    public static final String CLASE_FECHA = "3";
    public static final String CLASE_TELEFONO = "4";
    public static final String CLASE_FECHA_NAC = "5";
    public static final String CLASE_DATO_ADICIONAL = "6";
    public static final String CLASE_OTROS = "7";


    /**
     * FRAMIREZ 16.11.2011
     * CONSTANTES flag para la creacion de clientes.
     *
     *
     */

    public static final String FLG_CREACION_BENIF = "C"; //C: CREACION DE CLIENTES
    public static final String FLG_SOLICITUD_BENIF = "S"; //S: SOLICITUD PARA LA CREACION DE CLIENTES


    /**
     * FRAMIREZ 16.11.2011
     * CONSTANTES de codigos para los datos adicionales no editables
     *
     *
     */

    public static final String CODIGO_NUM_POLIZA = "0000000015";
    public static final String CODIGO_NUM_PLAN = "0000000016";
    public static final String CODIGO_COD_ASEGURADO = "0000000017";
    public static final String CODIGO_NUM_IEM = "0000000018";
    public static final String CODIGO_NOMB_CLIENTE = "0000000019";
    public static final String CODIGO_PRT = "0000000020";
    public static final String CODIGO_NUM_CONTRATO = "0000000021";
    public static final String CODIGO_TIPO_ASEGURADO = "0000000022";


    /**
     * FRAMIREZ 28.11.2011
     * CONSTANTES para activar la funcionalides del modulo convenio BTLMF
     *
     *
     */

    public static final String ID_TBL_GENERAL_CONV_BTLMF = "391";

    public static final String COD_FORMA_PAGO_CREDITO = "00080";

    public static final String COD_NOMB_TITULAR = "0000000006";
    public static final String COD_NRO_ATENCION = "0000000013";
    public static final String COD_MEDICO = "0000000034";

    /**
     * Convenio Competencia
     * @author ERIOS
     * @since 11.11.2013
     */
    public static final String COD_CONV_COMPETENCIA = "0000000834";


}
