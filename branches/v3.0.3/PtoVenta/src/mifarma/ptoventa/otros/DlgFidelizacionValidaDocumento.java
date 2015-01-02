package mifarma.ptoventa.otros;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
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

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JTextField;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.fidelizacion.reference.ConstantsFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.DBFidelizacion;
import mifarma.ptoventa.otros.reference.DBOtros;
import mifarma.ptoventa.otros.reference.VariablesOtros;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgFidelizacionValidaDocumento extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgFidelizacionValidaDocumento.class);

    Frame myParentFrame;

    FarmaTableModel tableModel;

    private JPanelWhite jContentPane = new JPanelWhite();

    private BorderLayout borderLayout1 = new BorderLayout();


    private JLabelFunction lblModificar = new JLabelFunction();

    private JLabelFunction lblsc = new JLabelFunction();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JLabelWhite jLabelWhite2 = new JLabelWhite();
    private JComboBox cboDocumento = new JComboBox();
    private JTextField txtDocumento = new JTextField();


    private int vHeight = 0;
    private int vHeightPanel1 = 0;
    private JEditorPane jdMens = new JEditorPane();

    //private JLabelFunction lblF1 = new JLabelFunction();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgFidelizacionValidaDocumento() {
        this(null, "", false);
    }

    public DlgFidelizacionValidaDocumento(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(410, 364));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Valida Documento");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        jContentPane.setLayout(null);
        lblModificar.setBounds(new Rectangle(90, 90, 105, 20));
        lblModificar.setText("[F11] Aceptar");
        lblsc.setBounds(new Rectangle(200, 90, 95, 20));
        lblsc.setText("[Esc]Salir");
        //lblF1.setBounds(new Rectangle(10, 260, 135, 20));
        //lblF1.setText("[ F1 ] Prueba Ticket");
        //jContentPane.add(lblF1, null);
        jPanelTitle1.setBounds(new Rectangle(10, 210, 385, 120));
        jLabelWhite1.setText("Tipo de Documento");
        jLabelWhite1.setBounds(new Rectangle(20, 15, 115, 25));
        jLabelWhite2.setText("Nº de Documento");
        jLabelWhite2.setBounds(new Rectangle(30, 45, 95, 20));
        cboDocumento.setBounds(new Rectangle(150, 20, 145, 20));
        cboDocumento.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cboDocumento_keyPressed(e);
            }
        });
        txtDocumento.setBounds(new Rectangle(150, 50, 145, 20));
        txtDocumento.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtDocumento_keyTyped(e);
            }

            public void keyReleased(KeyEvent e) {
                txtDocumento_keyReleased(e);
            }

            public void keyPressed(KeyEvent e) {
                txtDocumento_keyPressed(e);
            }
        });
        jdMens.setBounds(new Rectangle(10, 10, 380, 190));
        jdMens.setEditable(false);
        jPanelTitle1.add(txtDocumento, null);
        jPanelTitle1.add(cboDocumento, null);
        jPanelTitle1.add(jLabelWhite2, null);
        jPanelTitle1.add(jLabelWhite1, null);
        jPanelTitle1.add(lblModificar, null);
        jPanelTitle1.add(lblsc, null);
        jContentPane.add(jdMens, null);
        jContentPane.add(jPanelTitle1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTable();

    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTable() {

        //JCORTEZ 05.10.09 Se carga Mensake HTML
        cargarMensaje();
        cargar_cmbTipo();
    }

    private void cargar_cmbTipo() {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        FarmaLoadCVL.loadCVLFromSP(cboDocumento, ConstantsFidelizacion.NOM_HASTABLE_CMB_TIP_DOC,
                                   "PTOVENTA_FIDELIZACION.FID_F_TIP_DOC(?)", parametros, true, true);
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(cboDocumento);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {


        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            //JCORTEZ 05.10.09
            obtenerDatos();

        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }


    }


    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void obtenerDatos() {
        // public static String Num_Doc = "";

        VariablesOtros.Tip_doc =
                FarmaLoadCVL.getCVLCode(ConstantsFidelizacion.NOM_HASTABLE_CMB_TIP_DOC, cboDocumento.getSelectedIndex());
        log.info("VariablesOtros.Tip_doc-->" + VariablesOtros.Tip_doc);

        VariablesOtros.Desc_Tip_doc =
                FarmaLoadCVL.getCVLDescription(ConstantsFidelizacion.NOM_HASTABLE_CMB_TIP_DOC, VariablesOtros.Tip_doc);
        VariablesOtros.Num_Doc = txtDocumento.getText().trim();

        log.info("VariablesOtros.vDniCliente-->" + VariablesOtros.vDniCliente);
        log.info("VariablesOtros.Num_Doc-->" + VariablesOtros.Num_Doc);
        log.info("VariablesOtros.Tip_doc-->" + VariablesOtros.Tip_doc);
        if (VariablesOtros.Num_Doc.equalsIgnoreCase(VariablesOtros.vDniCliente_bk.trim())) {

            String numsec = FarmaVariables.vNuSecUsu;
            String idusu = FarmaVariables.vIdUsu;
            String nomusu = FarmaVariables.vNomUsu;
            String apepatusu = FarmaVariables.vPatUsu;
            String apematusu = FarmaVariables.vMatUsu;
            String rol = "";
            String pIndValidaClaveConfirmacion = "";
            try {
                rol = DBFidelizacion.obtieneRolConfirmacin();
                pIndValidaClaveConfirmacion = DBFidelizacion.getIndConfClaveReniec().trim();
            } catch (SQLException e) {
                log.error("", e);
                rol = FarmaConstants.ROL_ADMLOCAL;
                pIndValidaClaveConfirmacion = "S";
                //FarmaUtility.showMessage(this, "Error al obtener rol de confirmacion",txtDocumento);
            }

            DlgLogin dlgLogin = new DlgLogin(myParentFrame, "", true);
            dlgLogin.setRolUsuario(rol.trim());
            dlgLogin.setVisible(true);

            if (pIndValidaClaveConfirmacion.trim().equalsIgnoreCase("S")) {
                if (FarmaVariables.vAceptar) {
                    log.info("VariablesOtros.Desc_Tip_doc-->" + VariablesOtros.Desc_Tip_doc);
                    log.info("VariablesOtros.vNomCliente-->" + VariablesOtros.vNomCliente);
                    if (VariablesOtros.vNomCliente.trim().length() < 1)
                        FarmaUtility.showMessage(this, "Datos vacios", txtDocumento);
                    else {
                        FarmaVariables.dlgLogin = dlgLogin;
                        FarmaVariables.vAceptar = false;
                        VariablesOtros.Usu_Confir = FarmaVariables.vNuSecUsu.trim();
                        enviarCorreo();
                        cerrarVentana(true);
                    }
                    FarmaVariables.vNuSecUsu = numsec;
                    FarmaVariables.vIdUsu = idusu;
                    FarmaVariables.vNomUsu = nomusu;
                    FarmaVariables.vPatUsu = apepatusu;
                    FarmaVariables.vMatUsu = apematusu;
                } //else  cerrarVentana(false);


            } else {
                FarmaVariables.vAceptar = false;
                VariablesOtros.Usu_Confir = FarmaVariables.vNuSecUsu.trim();
                enviarCorreo();
                cerrarVentana(true);
            }
        } else {
            //Lo que coloco aqi
            //VariablesFidelizacion.Num_Doc
            //Lo que tengo ANTES
            //(VariablesFidelizacion.vDniCliente.trim())){
            /*
                 El documento ingresado (No. XXXXXXX) es diferente al digitado en la pantalla anterior (No. YYYYYY).
                 Por favor verifique nuevamente!!
                  * */
            FarmaUtility.showMessage(this,
                                     "El documento ingresado (Nº. " + VariablesOtros.Num_Doc + " ) es diferente \n" +
                    " al digitado en la pantalla anterior (Nº. " + VariablesOtros.vDniCliente_bk + " ) \n" +
                    "Por favor verifique nuevamente!!", txtDocumento);
            //"El Nº Documento ingresado no es igual al registrado anteriormente",txtDocumento);
            //cerrarVentana(false);
        }

    }

    private void enviarCorreo() {
        try {
            DBOtros.enviaCorreoConfirmacion(VariablesOtros.Tip_doc, VariablesOtros.Num_Doc, VariablesOtros.vNomCliente,
                                            VariablesOtros.vFecNacimiento);
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException sql) {
            cerrarVentana(false);
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrio un error al enviar correo de confirmacion.\n" +
                    sql.getMessage(), txtDocumento);
            // log.debug("Ocurrio un error al enviar correo de confirmacion");
        }
    }

    private void cargarMensaje() {
        String mensaje = "";

        try {
            mensaje = DBFidelizacion.getMsg();
            log.info("mensaje" + mensaje);
        } catch (Exception e) {
            log.error("", e);
            mensaje = "";
            log.info("Entro al catch");
        }
        //lblMensaje.setText(mensaje);
        jdMens.setText(mensaje);
        if (mensaje != null) {
            /*lblMensaje.setContentType("text/html");
            lblMensaje.setText(mensaje);*/
            jdMens.setContentType("text/html");
            jdMens.setText(mensaje);
        }
        log.info("mensaje;" + mensaje);
        if (mensaje.trim().length() > 0) {

            //calculoTamanoLogin(mensaje);
            //this.setSize(new Dimension(337, vHeight));
        }

    }


    private void calculoTamanoLogin(String vMensaje) {
        if (vMensaje.equalsIgnoreCase("")) {
            vHeight = 0;
            vHeightPanel1 = 0;
            return;
        }

        int vFilasM = 0;
        log.debug("Donde <b>: " + vMensaje.indexOf("<b>"));
        log.debug("Donde </b>: " + vMensaje.indexOf("</b>"));

        String mMens = vMensaje.substring(vMensaje.indexOf("<b>") + 3, vMensaje.indexOf("</b>"));
        if (mMens.length() % 28 > 0) {
            vFilasM = mMens.length() / 28;
            vFilasM += 3;
        } else {
            vFilasM = mMens.length() / 28;
            vFilasM += 2;
        }
        log.debug("FILAS:" + vFilasM);
        //Dimensión del JEditorPane lblMensaje
        //lblMensaje.setSize(355,26*vFilasM);

        //seteo variable Panel 1
        vHeightPanel1 = (165 + 26 * vFilasM) + 50;
        //seteo variable de la Altura LOGIN
        vHeight = (165 + (26 * vFilasM) + 45);

        jPanelTitle1.setSize(305, vHeightPanel1);
        log.debug("vHeight: " + vHeight);
        log.debug("jp1: " + jPanelTitle1.getSize().getHeight());
        // log.debug("lblMensaje: "+ lblMensaje.getSize().getHeight());

        log.debug("Mensaje: " + mMens);
        log.debug("Total Carac: " + mMens.length());

    }


    private void cboDocumento_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (cboDocumento.getSelectedIndex() > -1) {
                FarmaUtility.moveFocus(txtDocumento);
            }
        }
        chkKeyPressed(e);
    }

    private void txtDocumento_keyTyped(KeyEvent e) {

        FarmaUtility.admitirDigitos(txtDocumento, e);
    }


    private void txtDocumento_keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            // if(txtDocumento.getText().trim().length()>0){
            FarmaUtility.moveFocus(cboDocumento);
            //}
        }
        chkKeyPressed(e);
    }

    private void txtDocumento_keyReleased(KeyEvent e) {
    }
}
