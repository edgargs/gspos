package mifarma.ptoventa.otros;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
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

import mifarma.common.FarmaConnectionRemoto;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.otros.reference.ConstantsOtros;
import mifarma.ptoventa.otros.reference.DBOtros;
import mifarma.ptoventa.otros.reference.UtilityOtros;
import mifarma.ptoventa.otros.reference.VariablesOtros;

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
public class DlgBuscarHistorialXDni extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgBuscarHistorialXDni.class);

    private Frame myParentFrame;
    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnlHeader1 = new JPanelHeader();

    private JButtonLabel btnDNI = new JButtonLabel();
    private JTextFieldSanSerif txtDNI = new JTextFieldSanSerif();

    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblENTER = new JLabelFunction();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgBuscarHistorialXDni() {
        this(null, "", false);
    }


    public DlgBuscarHistorialXDni(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(280, 120));
        this.setTitle("Buscar Historia Presion X DNI");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });

        btnDNI.setBounds(new Rectangle(5, 5, 31, 20));
        btnDNI.setText("DNI");
        btnDNI.setMnemonic('D');
        btnDNI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDNI_actionPerformed(e);
            }
        });


        txtDNI.setText("");
        txtDNI.setBounds(new Rectangle(35, 5, 152, 20));
        txtDNI.setLengthText(20);
        txtDNI.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtDNI_keyTyped(e);
            }

            public void keyPressed(KeyEvent e) {
                txtDNI_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtDNI_keyReleased(e);
            }
        });

        pnlHeader1.setBounds(new Rectangle(10, 10, 250, 40));

        pnlHeader1.add(btnDNI, null);
        pnlHeader1.add(txtDNI, null);

        lblENTER.setBounds(new Rectangle(10, 60, 120, 20));
        lblENTER.setText("[ ENTER ] Aceptar");

        lblEsc.setBounds(new Rectangle(150, 60, 110, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        jContentPane.add(pnlHeader1, null);
        jContentPane.setBounds(new Rectangle(0, 0, 170, 50));
        jContentPane.add(lblENTER, null);
        jContentPane.add(lblEsc, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTable();

        try {
            VariablesOtros.vDocValidos = DBOtros.obtenerParamDocIden();
        } catch (SQLException e) {
            FarmaUtility.showMessage(this, "Error al obtener Parametro de Doc validos !", txtDNI);
        }

        //verificando si hay conexion a matriz
        VariablesOtros.vIndLineaMatriz =
                FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_N).trim();

    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    public void initTable() {
        VariablesOtros.vTableModelHist =
                new FarmaTableModel(ConstantsOtros.columnsListaHistMedPresion, ConstantsOtros.defaultListaListaHistMedPresion,
                                    0);
        //FarmaUtility.initSimpleList(tblListaPedidos,tableModel,ConstantsOtros.columnsListaMedPresion);
        tableModel =
                new FarmaTableModel(ConstantsOtros.columnsListaHistMedPresion, ConstantsOtros.defaultListaListaHistMedPresion,
                                    0);
        cargarDatosCliente();
    }

    private void cargarDatosCliente() {

        log.debug("seteando valores:" + VariablesOtros.vDniCliente);
        txtDNI.setText(VariablesOtros.vDniCliente);

    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        //lblDNI_T.setText(VariablesCtrlMotorizado.vAlias_Motorizado);
        //lblNombMotorizado_T.setText(VariablesCtrlMotorizado.vNombre_Motorizado);
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtDNI);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnDNI_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtDNI);
    }

    private void txtDNI_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtDNI, e);
    }


    private void txtDNI_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }


    private void txtDNI_keyReleased(KeyEvent e) {
        //FarmaGridUtils.buscarDescripcion(e, tblListaLocales,txtMaxSistolica, COL_DESC);
        //chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        //FarmaGridUtils.aceptarTeclaPresionada(e,tblListaLocales,null,0);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            log.debug("BUSCANDO HISTORIAL POR DNI");
            txtDNI.setText(txtDNI.getText().trim());
            if (txtDNI.getText().length() > 0) {
                if (UtilityOtros.validarDocIndentificacion(txtDNI.getText(), VariablesOtros.vDocValidos)) {
                    buscarHistorialXDni(txtDNI.getText());
                    if (VariablesOtros.vTableModelHist.getRowCount() > 0) {
                        VariablesOtros.vDniCliente = txtDNI.getText();
                        cerrarVentana(true);
                    } else {
                        FarmaUtility.showMessage(this, "EL Cliente no Tiene Historial", txtDNI);
                    }
                } else {
                    FarmaUtility.showMessage(this, "DNI No Valido !", txtDNI);
                }
            } else {
                FarmaUtility.showMessage(this, "Debe Especificar el DNI a Buscar", txtDNI);
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

    private void traerHistorialDeMatriz(FarmaTableModel farmaTblModel, String pDNI) {
        try {
            if (VariablesOtros.vIndLineaMatriz.equals(FarmaConstants.INDICADOR_S)) {
                log.debug("invocando procedimiento de matriz traer datos de matriz a local");
                DBOtros.getDatosMedPresMatriz(farmaTblModel, pDNI, FarmaConstants.INDICADOR_N);
            }

        } catch (SQLException e) {
            FarmaUtility.liberarTransaccion();
            //FarmaUtility.showMessage(this, "error al registrar la medida de Presion :"+e, txtDNI);
            log.error("", e);
        } finally {
            FarmaConnectionRemoto.closeConnection();
        }
    }

    /*private void actualizarHistoricoEnLocal(){	
	try {		
		DBOtros.actualizarMedidaPresion();
	} catch (SQLException e) {
		FarmaUtility.liberarTransaccion();
		FarmaUtility.showMessage(this, "error al registrar la medida de Presion :"+e, txtDNI);
	}
  }*/

    private void buscarHistorialXDni(String pDNI) {
        try {
            VariablesOtros.vTableModelHist.clearTable(); //limpiando tablemodel
            DBOtros.getHistoricoMedPresion(VariablesOtros.vTableModelHist, pDNI);
        } catch (SQLException e) {
            //FarmaUtility.showMessage(this, "error al registrar la medida de Presion :"+e, txtDNI);
            FarmaUtility.showMessage(this, "error al buscar historico en Local :" + e, txtDNI);
        }
        log.info("total en local : " + VariablesOtros.vTableModelHist.getRowCount());

        tableModel.clearTable(); //limpiando la data
        traerHistorialDeMatriz(tableModel, pDNI);
        int nFilas = tableModel.getRowCount();
        log.info("total de registro en matriz : " + nFilas);
        if (nFilas > 0) {
            for (int i = 0; i < nFilas; i++) {
                VariablesOtros.vTableModelHist.insertRow(tableModel.getRow(i));
            }
        }
    }

}
