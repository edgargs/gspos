package mifarma.ptoventa.ce.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JDialog;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaUtility;

import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.PrintConsejo;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : UtilityCajaElectronica.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      11.08.2005   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class UtilityCajaElectronica {
    private static final Logger log = LoggerFactory.getLogger(UtilityCajaElectronica.class);

    /**
     * Constructor
     */
    public UtilityCajaElectronica() {
    }

    public static boolean obtieneIndicadorVB_ForUpdate(String pSecMovCaja, String pTipVB) {
        ArrayList myArray = new ArrayList();
        String indVB = new String();
        try {
            DBCajaElectronica.obtieneIndVB_ForUpadte(myArray, pSecMovCaja, pTipVB);
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            return false;
        } finally {
            if (myArray.size() > 0) {
                indVB = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
                if (pTipVB.equalsIgnoreCase(ConstantsCajaElectronica.TIPO_VB_CAJERO))
                    VariablesCajaElectronica.vIndVBCajero = indVB;
                else if (pTipVB.equalsIgnoreCase(ConstantsCajaElectronica.TIPO_VB_QF))
                    VariablesCajaElectronica.vIndVBQF = indVB;
                return true;
            } else
                return false;
        }
    }

    public static boolean getIndicadorVB(String pSecMovCaja, String pTipVB) {
        boolean retorno = false;
        retorno = obtieneIndicadorVB_ForUpdate(pSecMovCaja, pTipVB);
        FarmaUtility.liberarTransaccion();
        return retorno;
    }

    public static boolean obtieneIndicadorVBCierreDia_ForUpdate(String pFechaCierreDia) {
        ArrayList myArray = new ArrayList();
        String indVBCierreDia = new String();
        try {
            DBCajaElectronica.obtieneIndVBCierreDia_ForUpadte(myArray, pFechaCierreDia);
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            return false;
        } finally {
            if (myArray.size() > 0) {
                indVBCierreDia = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
                VariablesCajaElectronica.vIndVBCierreDia = indVBCierreDia;
                return true;
            } else
                return false;
        }
    }

    public static boolean getIndicadorVBCierreDia(String pFechaCierreDia) {
        boolean retorno = false;
        retorno = obtieneIndicadorVBCierreDia_ForUpdate(pFechaCierreDia);
        FarmaUtility.liberarTransaccion();
        return retorno;
    }

    public static void actualizaGuiaCotizacionCompetencia(JDialog pJDialog) throws SQLException {
        /*try
    {*/
        DBCajaElectronica.actualizaFechaGuiaCotizaComp(VariablesCajaElectronica.vFechaCierreDia,
                                                       VariablesCajaElectronica.vNumerodeNota);
        /* FarmaUtility.aceptarTransaccion();
    } catch (SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
      log.error("",sql);
      FarmaUtility.showMessage(pJDialog,"Error al actualizar la fecha de CE de la guia", null);
    }  */
    }

    /***************COMPROBANTES DE CIERRE DE TURNO******************/
    public static String obtieneIndicadorComprobatesValidosUsuario(JDialog pJDialog, String pSecMovCaja) {
        String ind = new String();
        try {
            ind = DBCajaElectronica.obtenerIndicadorComprobantesValidosUsuario(pSecMovCaja);
        } catch (SQLException sql) {
            ind = "";
            log.error("", sql);
            FarmaUtility.showMessage(pJDialog,
                                     "Error al obtener el indicador de comprobantes\nvalidos para el movimiento de caja.",
                                     null);
        }
        return ind;
    }

    /*public static void actualizaInfoComprobantesCierreTurno(JDialog pJDialog,
                                                          String pSecMovCaja,
                                                          String pBoletaIni,
                                                          String pBoletaFin,
                                                          String pFacturaIni,
                                                          String pFacturaFin,
                                                          String pIndCompValidos)
  {
    try
    {
      DBCajaElectronica.actualizaComprobantesCierreTurno(pSecMovCaja,
                                                         pBoletaIni,
                                                         pBoletaFin,
                                                         pFacturaIni,
                                                         pFacturaFin,
                                                         pIndCompValidos);
      FarmaUtility.aceptarTransaccion();
    } catch(SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
      log.error("",sql);
      FarmaUtility.showMessage(pJDialog,"Error al actualizar rango de comprobantes cierre de turno: \n" + sql.getMessage(),null);
    }
  }*/

    public static void actualizaIndicadorCompValidosCT(JDialog pJDialog, String pSecMovCaja, String pIndCompValidos) {
        try {
            DBCajaElectronica.actualizaIndicadorCompValidosCierreTurno(pSecMovCaja, pIndCompValidos);
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(pJDialog,
                                     "Error al actualizar indicador de comprobantes validos cierre de turno: \n" +
                    sql.getMessage(), null);
        }
    }

    /*public static void obtieneComprobantaesMinMaxIngresoUsuario(JDialog pJDialog, String pSecMovCaja)
  {
    ArrayList myArray = new ArrayList();
    try
    {
      DBCajaElectronica.obtieneComprobantesValidosMovCaja(myArray, pSecMovCaja);
    } catch(SQLException sql)
    {
      myArray = new ArrayList();
			log.error("",sql);
      FarmaUtility.showMessage(pJDialog,"Error al obtener información de comprobantes cierre de turno: \n" + sql.getMessage(),null);
    }
    if(myArray.size()==1)
    {
      VariablesCajaElectronica.vBoletaMinGeneral = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
      VariablesCajaElectronica.vBoletaMaxGeneral = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
      VariablesCajaElectronica.vFacturaMinGeneral = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
      VariablesCajaElectronica.vFacturaMaxGeneral = ((String)((ArrayList)myArray.get(0)).get(3)).trim();
    } else
    {
      VariablesCajaElectronica.vBoletaMinGeneral = "";
      VariablesCajaElectronica.vBoletaMaxGeneral = "";
      VariablesCajaElectronica.vFacturaMinGeneral = "";
      VariablesCajaElectronica.vFacturaMaxGeneral = "";
    }
  }*/

    /*public static void obtieneComprobantesMinMaxUsuario(JDialog pJDialog, String pSecMovCaja)
  {
    try
    {
      VariablesCajaElectronica.compMinMax = DBCajaElectronica.obtieneComprobanteMinMax(pSecMovCaja);
    } catch(SQLException sql)
    {
      VariablesCajaElectronica.compMinMax = new ArrayList();
			log.error("",sql);
      FarmaUtility.showMessage(pJDialog,"Error al obtener información de comprobantes : \n" + sql.getMessage(),null);
    }
    if(VariablesCajaElectronica.compMinMax.size()==1)
    {
      VariablesCajaElectronica.vBoletaMin = ((String)((ArrayList)VariablesCajaElectronica.compMinMax.get(0)).get(0)).trim();
      VariablesCajaElectronica.vBoletaMax = ((String)((ArrayList)VariablesCajaElectronica.compMinMax.get(0)).get(1)).trim();
      VariablesCajaElectronica.vFacturaMin = ((String)((ArrayList)VariablesCajaElectronica.compMinMax.get(0)).get(2)).trim();
      VariablesCajaElectronica.vFacturaMax = ((String)((ArrayList)VariablesCajaElectronica.compMinMax.get(0)).get(3)).trim();
    } else
    {
      VariablesCajaElectronica.vBoletaMin = "";
      VariablesCajaElectronica.vBoletaMax = "";
      VariablesCajaElectronica.vFacturaMin = "";
      VariablesCajaElectronica.vFacturaMax = "";
    }
  }*/

    /***************COMPROBANTES DE CIERRE DE DIA*****************/
    public static String obtieneIndicadorComprobatesValidosDia(JDialog pJDialog, String pFechaCierreDia) {
        String ind = new String();
        try {
            ind = DBCajaElectronica.obtenerIndicadorComprobantesValidosCierreDia(pFechaCierreDia);
        } catch (SQLException sql) {
            ind = "";
            log.error("", sql);
            FarmaUtility.showMessage(pJDialog,
                                     "Error al obtener el indicador de comprobantes\nvalidos para el cierre de dia.",
                                     null);
        }
        return ind;
    }

    /*public static void actualizaInfoComprobantesCierreDia(JDialog pJDialog,
                                                        String pFechaCierreDia,
                                                        String pBoletaIni,
                                                        String pBoletaFin,
                                                        String pFacturaIni,
                                                        String pFacturaFin,
                                                        String pIndCompValidos)
  {
    try
    {
      DBCajaElectronica.actualizaComprobantesCierreDia(pFechaCierreDia,
                                                       pBoletaIni,
                                                       pBoletaFin,
                                                       pFacturaIni,
                                                       pFacturaFin,
                                                       pIndCompValidos);
      FarmaUtility.aceptarTransaccion();
    } catch(SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
      log.error("",sql);
      FarmaUtility.showMessage(pJDialog,"Error al actualizar rango de comprobantes cierre de dia: \n" + sql.getMessage(),null);
    }
  }*/

    public static void actualizaIndicadorCompValidosCD(JDialog pJDialog, String pFechaCierreDia,
                                                       String pIndCompValidos) {
        try {
            DBCajaElectronica.actualizaIndicadorCompValidosCierreDia(pFechaCierreDia, pIndCompValidos);
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(pJDialog,
                                     "Error al actualizar indicador de comprobantes validos cierre de dia: \n" +
                    sql.getMessage(), null);
        }
    }

    /*public static void obtieneComprobantesMinMaxIngresoDia(JDialog pJDialog, String pFechaCierreDia)
  {
    ArrayList myArray = new ArrayList();
    try
    {
      DBCajaElectronica.obtieneComprobantesValidosCierreDia(myArray, pFechaCierreDia);
    } catch(SQLException sql)
    {
      myArray = new ArrayList();
			log.error("",sql);
      FarmaUtility.showMessage(pJDialog,"Error al obtener información de comprobantes cierre de dia: \n" + sql.getMessage(),null);
    }
    if(myArray.size()==1)
    {
      VariablesCajaElectronica.vBoletaMinGeneral = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
      VariablesCajaElectronica.vBoletaMaxGeneral = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
      VariablesCajaElectronica.vFacturaMinGeneral = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
      VariablesCajaElectronica.vFacturaMaxGeneral = ((String)((ArrayList)myArray.get(0)).get(3)).trim();
    } else
    {
      VariablesCajaElectronica.vBoletaMinGeneral = "";
      VariablesCajaElectronica.vBoletaMaxGeneral = "";
      VariablesCajaElectronica.vFacturaMinGeneral = "";
      VariablesCajaElectronica.vFacturaMaxGeneral = "";
    }
  }*/

    /*public static void obtieneComprobantesMinMaxDia(JDialog pJDialog, String pFechaCierreDia)
  {
    try
    {
      VariablesCajaElectronica.compMinMaxCD = DBCajaElectronica.obtieneComprobanteMinMaxCD(pFechaCierreDia);
    } catch(SQLException sql)
    {
      VariablesCajaElectronica.compMinMaxCD = new ArrayList();
			log.error("",sql);
      FarmaUtility.showMessage(pJDialog,"Error al obtener información de comprobantes : \n" + sql.getMessage(),null);
    }
    if(VariablesCajaElectronica.compMinMaxCD.size()==1)
    {
      VariablesCajaElectronica.vBoletaMin = ((String)((ArrayList)VariablesCajaElectronica.compMinMaxCD.get(0)).get(0)).trim();
      VariablesCajaElectronica.vBoletaMax = ((String)((ArrayList)VariablesCajaElectronica.compMinMaxCD.get(0)).get(1)).trim();
      VariablesCajaElectronica.vFacturaMin = ((String)((ArrayList)VariablesCajaElectronica.compMinMaxCD.get(0)).get(2)).trim();
      VariablesCajaElectronica.vFacturaMax = ((String)((ArrayList)VariablesCajaElectronica.compMinMaxCD.get(0)).get(3)).trim();
    } else
    {
      VariablesCajaElectronica.vBoletaMin = "";
      VariablesCajaElectronica.vBoletaMax = "";
      VariablesCajaElectronica.vFacturaMin = "";
      VariablesCajaElectronica.vFacturaMax = "";
    }
  }*/

    public static ArrayList obtieneRangoComprobantesCorrectos(JDialog pJDialog, String pTipoIngresoComprobantes) {
        ArrayList myArray = new ArrayList();
        try {
            if (pTipoIngresoComprobantes.equals(ConstantsCajaElectronica.TIP_INGRESO_COMP_CT)) {
                DBCajaElectronica.obtieneRangoComprobantesMovCaja(myArray, VariablesCajaElectronica.vSecMovCaja);
            } else if (pTipoIngresoComprobantes.equals(ConstantsCajaElectronica.TIP_INGRESO_COMP_CD)) {
                DBCajaElectronica.obtieneRangoComprobantesCierreDia(myArray, VariablesCajaElectronica.vFechaCierreDia);
            }
            log.debug("obtieneRangoComprobantesCorrectos : " + myArray);
        } catch (SQLException sql) {
            log.error("", sql);
            myArray.clear();
            FarmaUtility.showMessage(pJDialog,
                                     "Ocurrio un error al obtener los rangos de comprobantes correctos.\n " + sql.getMessage(),
                                     null);
        }
        return myArray;
    }

    public static ArrayList obtieneRangoComprobantesUsuario(JDialog pJDialog, String pTipoIngresoComprobantes) {
        ArrayList myArray = new ArrayList();
        try {
            if (pTipoIngresoComprobantes.equals(ConstantsCajaElectronica.TIP_INGRESO_COMP_CT)) {
                DBCajaElectronica.obtieneListaRangoCompMovCajaUsuario(myArray, VariablesCajaElectronica.vSecMovCaja);
            } else if (pTipoIngresoComprobantes.equals(ConstantsCajaElectronica.TIP_INGRESO_COMP_CD)) {
                DBCajaElectronica.obtieneListaRangoCompCierreDiaUsuario(myArray,
                                                                        VariablesCajaElectronica.vFechaCierreDia);
            }
            log.debug("obtieneRangoComprobantesUsuario : " + myArray);
        } catch (SQLException sql) {
            log.error("", sql);
            myArray.clear();
            FarmaUtility.showMessage(pJDialog,
                                     "Ocurrio un error al obtener los rangos de comprobantes de usuario.\n " +
                                     sql.getMessage(), null);
        }
        return myArray;
    }

    /**
     * imprime los declarados por cajero
     * @author Javier Callo
     * @since 16.01.2009
     */

    //MARCO FAJARDO cambio: lentitud impresora termica 08/04/09
    public static void imprimeSobresDeclarados(JDialog pDialogo, String pSecMovCaja) {
        //PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);
        String pIndProsegur = FarmaConstants.INDICADOR_N;
        boolean indImp = false;
        //if(servicio != null)
        //{
        try {
            //SEG_F_CHAR_IND_PROSEGUR
            pIndProsegur = DBCajaElectronica.getIndProsegur().trim();
            if (pIndProsegur.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
                //String pTipoImp = DBCaja.obtieneTipoImprConsejo();JCHAVEZ 03.07.2009 se comentó para obtener el tipo de impresora por IP
                String pCodSobre = "";
                //for (int i = 0; i < servicio.length; i++)
                //{
                //PrintService impresora = servicio[i];
                //String pNameImp = impresora.getName().toString().trim();

                //if (pNameImp.indexOf(vIndExisteImpresora) != -1)
                //{
                String vIndImpre = DBCaja.obtieneIndImpresion();
                log.debug("vIndImpre :" + vIndImpre);
                if (!vIndImpre.equals("N")) {
                    ArrayList pLista = new ArrayList();
                    DBCajaElectronica.getSobreDeclarados(pSecMovCaja, pLista); //antes
                    //DBCajaElectronica.getSobreDeclarados_03(pSecMovCaja,pLista); //ASOSA, 26.07.2010

                    for (int f = 0; f < pLista.size(); f++) {
                        pCodSobre = FarmaUtility.getValueFieldArrayList(pLista, f, 0);
                        String html = DBCajaElectronica.getHtmlSobreDeclarados(pSecMovCaja, pCodSobre); //antes
                        //String html = DBCajaElectronica.getHtmlSobreDeclarados_02(pSecMovCaja,pCodSobre); //ASOSA, 26.07.2010
                        log.debug("html:" + html);
                        PrintConsejo.imprimirHtml(html.trim(), VariablesPtoVenta.vImpresoraActual,
                                                  VariablesPtoVenta.vTipoImpTermicaxIp); //JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
                        indImp = true;
                    }
                    if (indImp)
                        FarmaUtility.showMessage(pDialogo, "Recoger Voucher de sobres declarados.", null);
                    //break;
                }
                //}
                //}
            }

        } catch (SQLException sqlException) {
            //log.error("",sqlException);
            log.error(null, sqlException);
            FarmaUtility.showMessage(pDialogo, "Error al obtener los datos de los sobres a imprimir.", null);

        }

        //}
    }

    /**
     * se valida la la forma de pago por visa manual
     * @AUTHOR JQUISPE
     * @SINCE  14.04.2010
     */
    public static boolean verificarVisaManual() {
        String pRes = "";
        try {
            pRes = DBCajaElectronica.verificaVisaManual();
        } catch (SQLException sql) {
            log.error("", sql);
            pRes = "N";
        }

        if (pRes.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;

        return false;
    }

    /*****************/
    public static boolean validarLongitudTarj(String vNroTarj) {
        String resultado = "N";
        boolean bResp = false;
        try {
            resultado =
                    DBCajaElectronica.validarLongitudTarj(vNroTarj); //(codCliente,codConvenio, FarmaConstants.INDICADOR_S);
        } catch (SQLException ex) {
            log.error("", ex);
            return bResp;
        }

        if (resultado.equals("S"))
            bResp = true;

        return bResp;
    }

    /*
     *
     * Jquispe 20.12.2010
     *
     **/

    public static void getIndicadorCierreCajQuimico(JDialog dialogo) {
        try {
            String res = DBCajaElectronica.getIndicadorCierreCajQuimico();

            if (res.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                VariablesCajaElectronica.indUsu_Cierre_Caj_QF = true;
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(dialogo,
                                     "No se obtuvo el Indicador de Adm. \n para que pueda dar VB cajero y VB QF",
                                     null);
            VariablesCajaElectronica.indUsu_Cierre_Caj_QF = false;
        }
    }


    public static boolean isAdmLocal(String pSecu, JDialog dialogo) {

        try {
            String res = DBCajaElectronica.isAdmLocal(pSecu);

            if (res.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException sql) {
            FarmaUtility.showMessage(dialogo, "Error al verificar el rol de Usuario como Administrador", null);
            return false;
        }

    }

    public static boolean getIndImpreRemito_Matricial() {
        String pRes = "";
        try {
            pRes = DBCajaElectronica.getIndImpreRemitoMatricial();
        } catch (SQLException sql) {
            log.error("", sql);
            pRes = "N";
        }

        if (pRes.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            return true;

        return false;
    }

}
