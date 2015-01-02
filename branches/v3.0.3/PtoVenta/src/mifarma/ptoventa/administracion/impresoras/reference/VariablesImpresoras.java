package mifarma.ptoventa.administracion.impresoras.reference;

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

public class VariablesImpresoras {
    public VariablesImpresoras() {
    }

    public static String vSecImprLocal = "";
    public static String vSecImprLocal2 = "";

    public static String vDescImprLocal = "";

    public static String vTipComp = "";

    public static String vDescComp = "";

    public static String vNumSerie = "";

    public static String vRutaImpr = "";

    public static String vNumComp = "";

    /*    JMIRANDA 25/06/2009
    cod_grupo_cia, cod_local, sec_impr_loc_term,
      desc_impr_local, ruta_impr, tipo_impr_termica,
      est_impr_local, fec_crea_impr_local, usu_crea_impr_local,
      fec_mod_impr_local, usu_mod_impr_local*/
    //variables para Impresoras Termicas
    public static String vSecImprLocalTerm = "";
    public static String vDescImprLocalTerm = "";
    public static String vEstImprLocalTerm = "";
    public static String vMarcaImprLocalTerm = "";

    /* cod_grupo_cia, cod_local, sec_impr_local,
           num_serie_local, tip_comp, desc_impr_local,
           num_comp, ruta_impr, est_impr_local, fec_crea_impr_local,
           usu_crea_impr_local, fec_mod_impr_local, usu_mod_impr_local,
           tipo_impresora, serie_imp */

    //JCORTEZ 14.04.09
    public static String vTipoComp = "";
    //JCORTEZ 05.06.09
    public static String vSecImpr = "";
    public static String vSecIP = "";

    //JCHAVEZ 25.06.09.n
    public static String vSecIPImprTerm = "";

    public static String vModeloImpresora = "";
    public static String vSerieImpresora = "";

    public static void limpiar() {

        vSecImprLocal = "";
        vTipComp = "%";
        vDescComp = "";
        vNumSerie = "";
        vRutaImpr = "";
        vSecImprLocal = "";
        vDescImprLocal = "";
        vNumComp = "";
        vModeloImpresora = "";
        vSerieImpresora = "";
    }

    public static void limpiarImpresoraTermica() {
        vSecImprLocalTerm = "";
        vDescImprLocalTerm = "";
        vEstImprLocalTerm = "";
        vMarcaImprLocalTerm = "";
    }

}
