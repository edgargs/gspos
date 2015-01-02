package mifarma.ptoventa.otros;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConnectionRemoto;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
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
public class DlgRegMedidaPresion extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgRegMedidaPresion.class);

    FarmaTableModel tableModel;
    private Frame myParentFrame;
    private final int COL_COD = 0;
    private final int COL_DESC = 1;

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnlHeader1 = new JPanelHeader();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JScrollPane scrLocales = new JScrollPane();
    private JTable tblHistorico = new JTable();

    private JTextFieldSanSerif txtMaxSistolica = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtMinDiastolica = new JTextFieldSanSerif();

    private JLabelWhite lblDNI = new JLabelWhite();
    private JLabelWhite lblDNI_T = new JLabelWhite();
    private JLabelWhite lblNombCliente = new JLabelWhite();
    private JLabelWhite lblNombCliente_T = new JLabelWhite();
    private JLabelWhite lblEdad = new JLabelWhite();
    private JLabelWhite lblEdad_T = new JLabelWhite();
    private JLabelWhite lblSexo = new JLabelWhite();
    private JLabelWhite lblSexo_T = new JLabelWhite();

    private JLabelWhite lblhistorialCliente = new JLabelWhite();

    private JButtonLabel btnMaxSistolica = new JButtonLabel();
    private JButtonLabel btnMinDiastolica = new JButtonLabel();

    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblEnter = new JLabelFunction();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgRegMedidaPresion() {
        this(null, "", false);
    }


    public DlgRegMedidaPresion(Frame parent, String title, boolean modal) {
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
        jContentPane.setSize(new Dimension(420, 450));
        this.setSize(new Dimension(420, 450));
        this.setTitle("Registro de medida de Presion");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });

        lblhistorialCliente = new JLabelWhite();
        lblhistorialCliente.setBounds(new Rectangle(11, 4, 207, 17));
        lblhistorialCliente.setText("Historial de Medida de Presiones");

        lblDNI.setText("DNI :");
        lblDNI.setBounds(new Rectangle(5, 5, 31, 20));
        lblDNI_T.setText("");
        lblDNI_T.setBounds(new Rectangle(35, 5, 106, 20));

        lblNombCliente.setText("Cliente :");
        lblNombCliente.setBounds(new Rectangle(5, 25, 51, 20));
        lblNombCliente_T.setText("");
        lblNombCliente_T.setBounds(new Rectangle(55, 25, 274, 20));

        lblEdad.setBounds(new Rectangle(6, 50, 50, 20));
        lblEdad.setText("Fec. Nac.:");
        lblEdad_T.setBounds(new Rectangle(60, 50, 64, 20));
        lblEdad_T.setText("");

        lblSexo.setBounds(new Rectangle(165, 50, 40, 20));
        lblSexo.setText("Sexo :");
        lblSexo_T.setBounds(new Rectangle(200, 50, 87, 20));
        lblSexo_T.setText("");

        pnlHeader1.setBounds(new Rectangle(10, 10, 390, 110));
        pnlTitle1.setBounds(new Rectangle(10, 125, 390, 25));
        pnlTitle1.add(lblhistorialCliente, null);
        scrLocales.setBounds(new Rectangle(10, 150, 390, 231));


        btnMaxSistolica.setBounds(new Rectangle(5, 75, 80, 20));
        btnMaxSistolica.setText("Max. Sistolica :");
        btnMaxSistolica.setMnemonic('S');
        btnMaxSistolica.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnMaxSistolica_actionPerformed(e);
            }
        });

        btnMinDiastolica.setBounds(new Rectangle(182, 77, 89, 15));
        btnMinDiastolica.setText("Min. Diastolica :");
        btnMinDiastolica.setMnemonic('D');
        btnMinDiastolica.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnMinDiastolica_actionPerformed(e);
            }
        });

        txtMaxSistolica.setBounds(new Rectangle(89, 75, 52, 20));
        txtMaxSistolica.setText("");
        txtMaxSistolica.setLengthText(3);
        txtMaxSistolica.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtMaxSistolica_keyTyped(e);
            }

            public void keyPressed(KeyEvent e) {
                txtMaxSistolica_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtMaxSistolica_keyReleased(e);
            }
        });

        txtMinDiastolica.setBounds(new Rectangle(276, 75, 55, 19));
        txtMinDiastolica.setText("");
        txtMinDiastolica.setLengthText(3);
        txtMinDiastolica.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtMinDiastolica_keyTyped(e);
            }

            public void keyPressed(KeyEvent e) {
                txtMinDiastolica_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtMinDiastolica_keyReleased(e);
            }
        });


        lblEnter.setBounds(new Rectangle(160, 390, 120, 20));
        lblEnter.setText("[ F11 ] Aceptar");

        lblEsc.setBounds(new Rectangle(285, 390, 115, 20));
        lblEsc.setText("[ ESC ] Cerrar");


        jContentPane.add(lblEnter, null);
        jContentPane.add(lblEsc, null);
        scrLocales.getViewport().add(tblHistorico, null);
        jContentPane.add(scrLocales, null);

        pnlHeader1.add(txtMaxSistolica, null);
        pnlHeader1.add(btnMaxSistolica, null);
        pnlHeader1.add(lblDNI, null);

        pnlHeader1.add(lblDNI_T, null);
        pnlHeader1.add(lblNombCliente, null);
        pnlHeader1.add(lblNombCliente_T, null);
        pnlHeader1.add(btnMinDiastolica, null);
        pnlHeader1.add(txtMinDiastolica, null);
        pnlHeader1.add(lblEdad, null);
        pnlHeader1.add(lblEdad_T, null);
        pnlHeader1.add(lblSexo, null);
        pnlHeader1.add(lblSexo_T, null);
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
        //cargan la variable indicador de linea con matriz
        VariablesOtros.vIndLineaMatriz =
                FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_N).trim();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    public void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsOtros.columnsListaHistMedPresion, ConstantsOtros.defaultListaListaHistMedPresion,
                                    0);

        VariablesOtros.vTableModelHist =
                new FarmaTableModel(ConstantsOtros.columnsListaHistMedPresion, ConstantsOtros.defaultListaListaHistMedPresion,
                                    0);

        FarmaUtility.initSimpleList(tblHistorico, VariablesOtros.vTableModelHist,
                                    ConstantsOtros.columnsListaHistMedPresion);
        cargarDatosCliente();
        cargaHistoricoMPCliente();

        cargaRangoValoresValidos();

        FarmaUtility.ordenar(tblHistorico, VariablesOtros.vTableModelHist, 4, FarmaConstants.ORDEN_DESCENDENTE);

    }

    private void cargarDatosCliente() {
        log.debug("seteando valores:" + VariablesOtros.vDniCliente);
        lblDNI_T.setText(VariablesOtros.vDniCliente);
        lblNombCliente_T.setText(VariablesOtros.vNomCliente + " " + VariablesOtros.vApePatCliente + " " +
                                 VariablesOtros.vApeMatCliente);
        lblEdad_T.setText(VariablesOtros.vFecNacimiento);
        if (VariablesOtros.vSexo.equalsIgnoreCase("M")) {
            lblSexo_T.setText("MASCULINO");
        } else if (VariablesOtros.vSexo.equalsIgnoreCase("F")) {
            lblSexo_T.setText("FEMENINO");
        } else {
            lblSexo_T.setText("");
        }
    }

    private void cargaHistoricoMPCliente() {
        try {
            VariablesOtros.vTableModelHist.clearTable(); //limpiando tablemodel
            DBOtros.getHistoricoMedPresion(VariablesOtros.vTableModelHist, VariablesOtros.vDniCliente);
        } catch (SQLException e) {
            //FarmaUtility.showMessage(this, "error al registrar la medida de Presion :"+e, txtDNI);
            FarmaUtility.showMessage(this, "error al buscar historico en Local :" + e, txtMaxSistolica);
        }
        log.debug("total en local : " + VariablesOtros.vTableModelHist.getRowCount());

        tableModel.clearTable(); //limpiando la data
        traerHistorialDeMatriz(tableModel, VariablesOtros.vDniCliente);
        int nFilas = tableModel.getRowCount();
        log.debug("total de registro en matriz : " + nFilas);
        if (nFilas > 0) {
            for (int i = 0; i < nFilas; i++) {
                VariablesOtros.vTableModelHist.insertRow(tableModel.getRow(i));
            }
        }

    }

    private void cargaRangoValoresValidos() {
        try {
            VariablesOtros.vValMPValidos = DBOtros.getRangoValoresValidosMP();
        } catch (SQLException e) {
            //FarmaUtility.showMessage(this, "error al registrar la medida de Presion :"+e, txtDNI);
            FarmaUtility.showMessage(this, "error al buscar historico en Local :" + e, txtMaxSistolica);
        }
        log.debug("rango de valores validos : " + VariablesOtros.vValMPValidos);
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        //lblDNI_T.setText(VariablesCtrlMotorizado.vAlias_Motorizado);
        //lblNombMotorizado_T.setText(VariablesCtrlMotorizado.vNombre_Motorizado);
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtMaxSistolica);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnMaxSistolica_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtMaxSistolica);
    }

    private void btnMinDiastolica_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtMinDiastolica);
    }


    private void txtMaxSistolica_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtMaxSistolica, e);
    }

    private void txtMaxSistolica_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblHistorico, null, 0);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtMinDiastolica);
        } else {
            chkKeyPressed(e);
        }
    }

    private void txtMaxSistolica_keyReleased(KeyEvent e) {
        //FarmaGridUtils.buscarDescripcion(e, tblHistorico,txtMaxSistolica, COL_DESC);
    }


    private void txtMinDiastolica_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtMinDiastolica, e);
    }

    private void txtMinDiastolica_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblHistorico, null, 0);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtMaxSistolica);
        } else {
            chkKeyPressed(e);
        }
    }

    private void txtMinDiastolica_keyReleased(KeyEvent e) {
        //FarmaGridUtils.buscarDescripcion(e, tblHistorico,txtMaxSistolica, COL_DESC);
        //chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        //FarmaGridUtils.aceptarTeclaPresionada(e,tblHistorico,null,0);
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (validarDatos()) {

                VariablesOtros.vMaxSistolica = txtMaxSistolica.getText();
                VariablesOtros.vMinDiastolica = txtMinDiastolica.getText();

                if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                "Desea Registrar Datos de la Medida Presion ?")) {
                    if (grabarRegMedidaPresion()) { //si logra registrar correctamente tonces se imprimira
                        /**despues de grabar obtener de nuevo el listado del historial para imprimir**/
                        cargaHistoricoMPCliente();
                        FarmaUtility.ordenar(tblHistorico, VariablesOtros.vTableModelHist, 4,
                                             FarmaConstants.ORDEN_DESCENDENTE);
                        /**fin**/
                        imprimirMedidaPresion();
                    }
                    cerrarVentana(true);
                }
            }
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

    private boolean validarDatos() {
        txtMaxSistolica.setText(txtMaxSistolica.getText().trim());
        txtMinDiastolica.setText(txtMinDiastolica.getText().trim());

        boolean flag = true;
        int max = 0;
        int min = 0;

        try {
            max = Integer.parseInt(txtMaxSistolica.getText());
        } catch (NumberFormatException e) {
            //  flag = false;
            FarmaUtility.showMessage(this, "Valor no valido !", txtMaxSistolica);

            return false;
        }

        try {
            min = Integer.parseInt(txtMinDiastolica.getText());
        } catch (NumberFormatException e) {
            //flag = false;
            FarmaUtility.showMessage(this, "Valor no valido !", txtMinDiastolica);

            return false;
        }


        try {
            if (VariablesOtros.vValMPValidos != null && VariablesOtros.vValMPValidos.length() > 0) {
                String valMPValidos[] = VariablesOtros.vValMPValidos.split(",");
                int minimo = 10; //valores por defecto
                int maximo = 300; //valores por defecto
                if (valMPValidos.length >= 2) {
                    minimo = Integer.parseInt(valMPValidos[0]); //minimo
                    maximo = Integer.parseInt(valMPValidos[1]); //maximo
                }
                if (min < minimo || min > maximo) {
                    //flag=false;
                    FarmaUtility.showMessage(this,
                                             "Valor no valido\nesta fuera del rango permitido [" + VariablesOtros.vValMPValidos.trim() +
                                             "] !", txtMinDiastolica);
                    return false;
                }
                if (max < minimo || max > maximo) {
                    //flag=false;
                    FarmaUtility.showMessage(this,
                                             "Valor no valido\nesta fuera del rango permitido [" + VariablesOtros.vValMPValidos.trim() +
                                             "] !", txtMaxSistolica);
                    return false;
                }
            } else {
                //flag=false;
                FarmaUtility.showMessage(this, "rango de valores permitidos no cargado !", txtMaxSistolica);
                return false;
            }

            if (max < 0) {
                //flag = false;
                FarmaUtility.showMessage(this, "Valor no valido de Max. sistolica !", txtMaxSistolica);
                return false;
            }
            if (min < 0) {
                //flag = false;
                FarmaUtility.showMessage(this, "Valor no valido de Min. diastolica !", txtMinDiastolica);
                return false;
            }

            if (min >= max) {
                //flag = false;
                FarmaUtility.showMessage(this, "Min. Diastolica debe ser menor a Max. Sistolica !", txtMaxSistolica);
                return false;
            }

        } catch (NumberFormatException e) {
            flag = false;
            FarmaUtility.showMessage(this, "Valor no valido !", txtMinDiastolica);

        } catch (Exception e) {
            flag = false;
            FarmaUtility.showMessage(this, "Valor no valido !", txtMaxSistolica);
        }
        return flag;
    }

    private boolean grabarRegMedidaPresion() {
        try {
            VariablesOtros.vNumReg = DBOtros.insertarMedidaPresionEnLocal().trim();
            //insertando en matriz
            if (VariablesOtros.vIndLineaMatriz.equals(FarmaConstants.INDICADOR_S)) {
                DBOtros.insertarMedidaPresionEnMatriz(FarmaConstants.INDICADOR_N);
                //commit en matriz
                FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
            }
            //commit en local
            FarmaUtility.aceptarTransaccion();
            return true;
        } catch (SQLException e) {
            FarmaUtility.liberarTransaccion();
            if (VariablesOtros.vIndLineaMatriz.equals(FarmaConstants.INDICADOR_S)) {
                FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
            }
            FarmaUtility.showMessage(this, "error al registrar la medida de Presion :" + e, txtMaxSistolica);
            return false;
        }
    }

    //MARCO FAJARDO cambio: lentitud impresora termica 08/04/09

    private void imprimirMedidaPresion() {
        //PrintService[] servicio = PrintServiceLookup.lookupPrintServices(null,null);
        //if(servicio != null) {
        try {
            String vIndExisteImpresora = DBCaja.obtieneNameImpConsejos();
            // String pTipoImp = DBCaja.obtieneTipoImprConsejo();JCHAVEZ 03.07.2009 se comentó para obtener el tipo de impresora por IP

            //for (int i = 0; i < servicio.length; i++) {
            //PrintService impresora = servicio[i];
            //String pNameImp = impresora.getName().toString().trim();

            //if (pNameImp.indexOf(vIndExisteImpresora) != -1) {

            PrintMedPresion.imprimir(generarHtmlImprimir(), VariablesPtoVenta.vImpresoraActual,
                                     VariablesPtoVenta.vTipoImpTermicaxIp); //JCHAVEZ 03.07.2009 se reemplaza la variable pTipoImp por la constante VariablesPtoVenta.vTipoImpTermicaxIp
            //break;	
            //}
            //}	
        } catch (SQLException sqlException) {
            FarmaUtility.showMessage(this, "Error al ", null);
        }
        //}
    }

    private String generarHtmlImprimir() {
        StringBuffer html = new StringBuffer("");
        try {
            //obteniendo la cabecera para imprimir		
            String cab =
                DBOtros.getCabHtmlImprimir(FarmaVariables.vIPBD, VariablesOtros.vDniCliente, VariablesOtros.vNomCliente +
                                           " " + VariablesOtros.vApePatCliente + " " + VariablesOtros.vApeMatCliente);
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
            int totalItems =
                Math.min(VariablesOtros.paramCantImprimirXReg, VariablesOtros.vTableModelHist.getRowCount());

            StringBuffer detalleHisto = new StringBuffer("");

            for (int i = 0; i < totalItems; i++) {
                detalleHisto.append("<tr class='historico' nowrap><td>" +
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

            log.info("HTML GENERADO : " + html.toString());

        } catch (SQLException e) {
            log.info("error al generar el html a imprimir");
        }
        return html.toString();
    }

    private void traerHistorialDeMatriz(FarmaTableModel farmaTblModel, String pDNI) {
        try {
            if (VariablesOtros.vIndLineaMatriz.equals(FarmaConstants.INDICADOR_S)) {
                log.info("invocando procedimiento de matriz traer datos de matriz a local");
                DBOtros.getDatosMedPresMatriz(farmaTblModel, pDNI, FarmaConstants.INDICADOR_N);
            }

        } catch (SQLException e) {
            FarmaUtility.liberarTransaccion();
            //FarmaUtility.showMessage(this, "error al registrar la medida de Presion :"+e, txtDNI);
            log.info("ERROR AL TRAER INFORMACION DE MATRIZ");
        } finally {
            FarmaConnectionRemoto.closeConnection();
        }
    }

}
