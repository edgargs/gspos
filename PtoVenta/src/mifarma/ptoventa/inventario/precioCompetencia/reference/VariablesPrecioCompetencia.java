package mifarma.ptoventa.inventario.precioCompetencia.reference;

import java.io.File;
import java.util.ArrayList;

/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo     : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : VariablesPrecioCompetencia.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * CLARICO    09.12.2014   Creación<br>
 * <br>
 * @author Celso Ever Larico Mullisaca<br>
 * @version 1.0<br>
 *
 */
public class VariablesPrecioCompetencia {
    public VariablesPrecioCompetencia() {
    }
    
    public static boolean vFlagDialogAbierto = false;
    public static boolean vFlagTeclaFxxPresionado = false;
    
    public static String vCodLocalDel = "";
    
    public static String vNumPedidoTransf = "";    
    public static String vCodLocalDestino = "";
    public static String vSecGrupo = "";
    public static String vSecTrans = "";
    public static String vLocalDestino = "";
    public static String vFecPedidoTransf = "";
  
    public static ArrayList vArrayProductosCotizados = new ArrayList();
    
    /*** variables de la cabecera***/
    public static String vFec = "";    
    public static String vCodTipoCotizacion = "";
    public static String vCodCompetidor = "";
    public static String vDescTipoCotizacion = "";
    public static String vDescCompetidor = "";
    /*** fin variables de la cabecera***/
    
    public static String vCodSustento = "";
    
    //DlgCotizacionIngresoCantidad
    public static String vCodProd = "";
    public static String vNomProd = "";
    public static String vUnidMed = "";
    public static String vStkFisico = "";
    public static String vNomLab = "";
    public static String vCantGuia = "";
    public static String vCant = "";
    public static String vCantFrac = "";
    public static int vStock = 0;
    public static String vFrac = "";
    public static String vImagen = "";
    public static String vPrecUnit = "";
    public static String vTotal = "";
    public static String vValFrac = "";
    
    public static int vPos = 0;
    
    public static int vCantIngreso = 0;
    
    public static File vFileImagen ;
}
