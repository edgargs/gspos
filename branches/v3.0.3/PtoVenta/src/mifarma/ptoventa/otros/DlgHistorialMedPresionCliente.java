package mifarma.ptoventa.otros;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.otros.reference.ConstantsOtros;
import mifarma.ptoventa.otros.reference.DBOtros;
import mifarma.ptoventa.otros.reference.PrintMedPresion;
import mifarma.ptoventa.otros.reference.VariablesOtros;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaLocalesMot.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCALLO      21.10.2008   Creación<br>
 * <br>
 * @author Javier Callo Quispe<br>
 * @version 1.0<br>
 *
 */
public class DlgHistorialMedPresionCliente extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgHistorialMedPresionCliente.class);

    FarmaTableModel tableModel;
    private Frame myParentFrame;
    private final int COL_DNI = 0;
    private final int COL_APE_PAT = 1;
    private final int COL_APE_MAT = 2;
    private final int COL_NOM_CLI = 3;
    private final int COL_FEC_NAC = 4;
    private final int COL_SEX_CLI = 5;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnlHeader1 = new JPanelHeader();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JScrollPane scrLocales = new JScrollPane();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JTable tblHistorico = new JTable();
    private JLabelFunction lblF11 = new JLabelFunction();

    private JLabelWhite lblDNI = new JLabelWhite();
    private JLabelWhite lblDNI_T = new JLabelWhite();
    private JLabelWhite lblNombCliente = new JLabelWhite();
    private JLabelWhite lblNombCliente_T = new JLabelWhite();
    private JLabelWhite lblEdad = new JLabelWhite();
    private JLabelWhite lblEdad_T = new JLabelWhite();
    private JLabelWhite lblSexo = new JLabelWhite();
    private JLabelWhite lblSexo_T = new JLabelWhite();
    private JLabelWhite lblhistorialCliente = new JLabelWhite();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgHistorialMedPresionCliente() {
        this(null, "", false);
    }


    public DlgHistorialMedPresionCliente(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(borderLayout1);

        jContentPane.setSize(new Dimension(415, 410));
        this.setSize(new Dimension(415, 410));
        this.setTitle("Historial de medida de Presion");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlHeader1.setBounds(new Rectangle(10, 10, 390, 75));
        pnlTitle1.setBounds(new Rectangle(10, 95, 390, 25));

        scrLocales.setBounds(new Rectangle(10, 120, 390, 224));
        tblHistorico.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblHistorico_keyPressed(e);
            }
        });


        lblhistorialCliente = new JLabelWhite();
        lblhistorialCliente.setBounds(new Rectangle(11, 4, 207, 17));
        lblhistorialCliente.setText("Historial de Medida de Presiones");

        lblSexo.setBounds(new Rectangle(160, 45, 40, 20));
        lblSexo.setText("Sexo :");

        lblSexo_T.setBounds(new Rectangle(205, 45, 87, 20));
        lblSexo_T.setText("");


        lblEdad.setBounds(new Rectangle(5, 45, 55, 20));
        lblEdad.setText("Fec. Nac.:");

        lblEdad_T.setBounds(new Rectangle(60, 45, 64, 20));
        lblEdad_T.setText("");

        lblEsc.setBounds(new Rectangle(280, 349, 120, 20));
        lblEsc.setText("[ ESC ] Cerrar");


        lblF11.setBounds(new Rectangle(150, 349, 120, 20));
        lblF11.setText("[ F11 ] Imprimir");

        lblDNI.setText("DNI :");
        lblDNI.setBounds(new Rectangle(5, 5, 31, 20));

        lblDNI_T.setText("");
        lblDNI_T.setBounds(new Rectangle(36, 4, 106, 20));

        lblNombCliente.setText("Cliente :");
        lblNombCliente.setBounds(new Rectangle(5, 25, 51, 20));

        lblNombCliente_T.setText("");
        lblNombCliente_T.setBounds(new Rectangle(56, 25, 274, 20));


        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        scrLocales.getViewport().add(tblHistorico, null);
        jContentPane.add(scrLocales, null);

        pnlHeader1.add(lblDNI, null);
        pnlHeader1.add(lblDNI_T, null);
        pnlHeader1.add(lblNombCliente, null);
        pnlHeader1.add(lblNombCliente_T, null);
        pnlHeader1.add(lblEdad, null);
        pnlHeader1.add(lblEdad_T, null);
        pnlHeader1.add(lblSexo, null);
        pnlHeader1.add(lblSexo_T, null);

        pnlTitle1.add(lblhistorialCliente, null);

        jContentPane.add(pnlHeader1, null);
        jContentPane.add(pnlTitle1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTable();

    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    public void initTable() {
        FarmaUtility.initSimpleList(tblHistorico, VariablesOtros.vTableModelHist,
                                    ConstantsOtros.columnsListaHistMedPresion);

        FarmaUtility.ordenar(tblHistorico, VariablesOtros.vTableModelHist, 4, FarmaConstants.ORDEN_DESCENDENTE);

        cargaDatosCliente();

    }

    private void cargaDatosCliente() {
        ArrayList vListaDatosDNI = new ArrayList();
        try {
            DBOtros.getDatosExisteDNI(vListaDatosDNI, VariablesOtros.vDniCliente);
            /*
                   * no buscara en matriz y no traera ninguna informacion
                   * dubilluz 26.07.2009
		  if (vListaDatosDNI.size() < 1) {//si no hay datos del cliente
			  if(VariablesOtros.vIndLineaMatriz.equals(FarmaConstants.INDICADOR_S)){//busca en matriz los datos	     		
				log.debug("**** BUSCANDO EN MATRIZ E INSERTANDO EN LOCAL SI NO HAY");
				buscaMatrizActualizaDatosLocal(VariablesOtros.vDniCliente);
				vListaDatosDNI = new ArrayList();
				DBOtros.getDatosExisteDNI(vListaDatosDNI, VariablesOtros.vDniCliente);
			  }
		  }
                  */
            if (vListaDatosDNI.size() > 0) {
                lblDNI_T.setText(FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_DNI));

                String aux =
                    (FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_NOM_CLI).equalsIgnoreCase("N")) ? " " :
                    FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_NOM_CLI) + " ";
                aux +=
(FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_APE_PAT).equalsIgnoreCase("N")) ? " " :
FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_APE_PAT) + " ";
                aux +=
(FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_APE_MAT).equalsIgnoreCase("N")) ? " " :
FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_APE_MAT) + " ";
                lblNombCliente_T.setText(aux);
                aux =
(FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_FEC_NAC).equalsIgnoreCase("N")) ? " " :
FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_FEC_NAC) + " ";
                lblEdad_T.setText(aux);

                aux = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_SEX_CLI);
                if (aux.equalsIgnoreCase("M")) {
                    lblSexo_T.setText("MASCULINO");
                } else if (aux.equalsIgnoreCase("F")) {
                    lblSexo_T.setText("FEMENINO");
                } else {
                    lblSexo_T.setText("");
                }

            }
        } catch (SQLException sql) {
            log.error("", sql);
            log.info("HORROR");
        }
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        //lblDNI_T.setText(VariablesCtrlMotorizado.vAlias_Motorizado);
        //lblNombMotorizado_T.setText(VariablesCtrlMotorizado.vNombre_Motorizado);
        FarmaUtility.centrarVentana(this);

    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void tblHistorico_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        //FarmaGridUtils.aceptarTeclaPresionada(e,tblHistorico,null,0);
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e) && !VariablesOtros.vFlagDialogAbierto) {

            VariablesOtros.vFlagDialogAbierto = true;

            int rowSel = tblHistorico.getRowCount();
            if (rowSel >= 0) {

                FarmaVariables.vAceptar = false;

                DlgCantItemsImprimir dlgCantItemsImprimir = new DlgCantItemsImprimir(myParentFrame, "", true);
                dlgCantItemsImprimir.setVisible(true);
                if (FarmaVariables.vAceptar) {
                    imprimirHistorial(VariablesOtros.vCantItemsImprimir);
                    cerrarVentana(true);
                }
            }

            VariablesOtros.vFlagDialogAbierto = false;

        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }


    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void buscaMatrizActualizaDatosLocal(String pDni) {

        ArrayList array = new ArrayList();

        try {
            //CORREGIR ESTE METODO
            DBOtros.getDatosExisteDNI_Matriz(array, pDni, FarmaConstants.INDICADOR_N);
            log.debug("Datos Cli en Matriz " + array);
            log.debug("Tam size:" + array.size());

            /**verificando si en matriz el dni es $**/
            if (array.size() > 0) {

                VariablesOtros.vDniCliente = FarmaUtility.getValueFieldArrayList(array, 0, 0).trim();
                VariablesOtros.vApePatCliente = FarmaUtility.getValueFieldArrayList(array, 0, 1).trim();
                VariablesOtros.vApeMatCliente = FarmaUtility.getValueFieldArrayList(array, 0, 2).trim();
                VariablesOtros.vNomCliente = FarmaUtility.getValueFieldArrayList(array, 0, 3).trim();
                VariablesOtros.vFecNacimiento = FarmaUtility.getValueFieldArrayList(array, 0, 4).trim();
                VariablesOtros.vSexo = FarmaUtility.getValueFieldArrayList(array, 0, 5).trim();
                VariablesOtros.vDireccion = FarmaUtility.getValueFieldArrayList(array, 0, 6).trim();
                VariablesOtros.vTelefono = FarmaUtility.getValueFieldArrayList(array, 0, 7).trim();

                VariablesOtros.vIndEstado = "A";
                //Este metodo de se encargara de insertar y/o actualizar
                //insertarClienteFidelizacion
                DBOtros.insertarClienteLocal();
                FarmaUtility.aceptarTransaccion();

            }
            //VariablesOtros.vDniCliente = "";
            //VariablesFidelizacion.vNumTarjeta =  "";
            VariablesOtros.vApePatCliente = "";
            VariablesOtros.vApeMatCliente = "";
            VariablesOtros.vNomCliente = "";
            VariablesOtros.vFecNacimiento = "";
            VariablesOtros.vSexo = "";
            VariablesOtros.vDireccion = "";
            VariablesOtros.vTelefono = "";

        } catch (SQLException e) {
            FarmaUtility.liberarTransaccion();
            log.error("", e);
        }
    }

    //MARCO FAJARDO cambio: lentitud impresora termica 08/04/09

    private void imprimirHistorial(int CantidadItems) {
        //PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);
        // if(servicio != null) {
        try {
            String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
            //String pTipoImp = DBCaja.obtieneTipoImprConsejo();JCHAVEZ 03.07.2009 se comentó para obtener el tipo de impresora por IP

            //for (int i = 0; i < servicio.length; i++) {
            //PrintService impresora = servicio[i];
            //String pNameImp = impresora.getName().toString().trim();

            //if (pNameImp.indexOf(vIndExisteImpresora) != -1) {

            PrintMedPresion.imprimir(generarHtmlImprimir(CantidadItems), VariablesPtoVenta.vImpresoraActual,
                                     VariablesPtoVenta.vTipoImpTermicaxIp); //JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
            //break;	
            // }
            //}	
        } catch (SQLException sqlException) {
            FarmaUtility.showMessage(this, "Error al ", null);
        }
        //}
    }


    private String generarHtmlImprimir(int CantidadItems) {
        StringBuffer html = new StringBuffer("");
        try {
            //obteniendo la cabecera para imprimir		
            String cab =
                DBOtros.getCabHtmlImprimir(FarmaVariables.vIPBD, VariablesOtros.vDniCliente, lblNombCliente_T.getText().trim());
            //obteniendo el pie del doc a imprimir
            String Pie = DBOtros.getPieHtmlImprimir();
            /*** obteniendo parametros de base de datos ***/

            /*String MaxItemsImprimir = DBOtros.getCantMaxItemsImpresion();
		//separando los valores de los parametros
		String valores[] = MaxItemsImprimir.split(",");
		String defCantImpRegistro = "";
		String CantMaxImpHistorial = "";
		if( valores.length > 1){
			defCantImpRegistro = valores[0];
			CantMaxImpHistorial = valores[0];
		}else{
			defCantImpRegistro = "4";
			CantMaxImpHistorial = "10";
		}
		
		int intDefCanItemsImp = Integer.parseInt(defCantImpRegistro);
		int intCantMaxImpHist = Integer.parseInt(CantMaxImpHistorial);*/

            /*** fin de obteniendo parametros de base de datos ***/

            //obteniendo la cantidad de items a imprimir
            int totalItems =
                Math.min(VariablesOtros.paramCantMaxImprimirXHist, VariablesOtros.vTableModelHist.getRowCount());
            totalItems = Math.min(CantidadItems, totalItems);

            StringBuffer detalleHisto = new StringBuffer("");

            for (int i = 0; i < totalItems; i++) {
                detalleHisto.append("<tr class='historico' NOWRAP><td>" +
                                    VariablesOtros.vTableModelHist.getValueAt(i, 0).toString() +
                                    "</TD>").append("<td align='right'>" +
                                                    VariablesOtros.vTableModelHist.getValueAt(i, 1).toString() +
                                                    "<font size='1'> mmHg</font></TD>").append("<td align='right'>" +
                                                                                               VariablesOtros.vTableModelHist.getValueAt(i,
                                                                                                                                         2).toString() +
                                                                                               "<font size='1'> mmHg</font></TD>").append("<td>" +
                                                                                                                                          VariablesOtros.vTableModelHist.getValueAt(i,
                                                                                                                                                                                    3).toString() +
                                                                                                                                          "</TD>").append("</tr>");
            }

            //construimos el DOC FINAL A IMPRIMIR

            html.append(cab).append(detalleHisto.toString()).append(Pie);

            //log.info("HTML GENERADO : "+html.toString());

        } catch (SQLException e) {
            log.info("error al generar el html a imprimir");
        }
        return html.toString();
    }


}
