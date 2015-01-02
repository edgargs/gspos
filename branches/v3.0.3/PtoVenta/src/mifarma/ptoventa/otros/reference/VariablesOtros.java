package mifarma.ptoventa.otros.reference;

import java.util.ArrayList;

import mifarma.common.FarmaTableModel;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : VariablesInventario.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      14.02.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class VariablesOtros {

    public static boolean vFlagModAdmin = false;

    public static boolean vFlagDialogAbierto = false;
    public static boolean vFlagTeclaFxxPresionado = false;

    public static int paramCantImprimirXReg = 4;
    public static int paramCantMaxImprimirXHist = 10;

    public static int vCantItemsImprimir = 4;

    public static String vMensajeMiFarmaImpresion = " mensaje MIFARMA";

    // JCALLO doc validos de identificacion
    public static String vDocValidos = "";

    // JCALLO rango de valores validos de la MP
    public static String vValMPValidos = "";

    public static boolean vIndExisteCliente = false;

    public static ArrayList vDataCliente = new ArrayList();

    public static String vDniCliente = "";
    public static String vDniCliente_bk = ""; //JCHAVEZ 06102009
    public static String vApePatCliente = "";
    public static String vApeMatCliente = "";
    public static String vNomCliente = "";
    public static String vFecNacimiento = "";
    public static String vFecNacimiento_bk = ""; //JCHAVEZ 06102009
    public static String vSexo = "";
    public static String vDireccion = "";
    public static String vTelefono = "";
    public static String vEmail = "";
    public static String vIndEstado = "";


    public static String vCodCli = "";
    public static String vCodGrupoCia = "001";

    public static String vNumReg = "";


    public static String vIndLineaMatriz = "";
    public static boolean vSexoExists;
    public static boolean vFlagMandatory;

    //jcallo 27.10.2008 variables de medida de presion
    public static String vMaxSistolica = "";
    public static String vMinDiastolica = "";

    //jcallo 27.10.2008 variables de medida de presion
    public static String vFecIni = "";
    public static String vFecFin = "";

    //tableModel historico
    public static FarmaTableModel vTableModelHist;

    public VariablesOtros() {

    }

    public static String Tip_doc = "";
    public static String Desc_Tip_doc = "";
    public static String Num_Doc = "";
    public static String Usu_Confir = "";
}
