package mifarma.ptoventa.ventas;


import com.gs.mifarma.componentes.JConfirmDialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import mifarma.common.FarmaSecurity;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.electronico.epos.reference.DBEpos;

import mifarma.ptoventa.administracion.impresoras.DlgListaImpresoras;
import mifarma.ptoventa.ventas.reference.DBVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgMensajeImpresion extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgMensajeImpresion.class);
    @SuppressWarnings("compatibility:5305877824751256928")
    private static final long serialVersionUID = 1L;

    FarmaSecurity vSecurityLogin;
    int parametro = -1;
    JLabel lblParametro;

    JTextField txtUsuario = new JTextField();
    JPasswordField txtClave = new JPasswordField();
    JButton btnAceptar = new JButton();
    JLabel lblClave = new JLabel();

    JPanel pnlAbajo = new JPanel();
    JEditorPane jEditorPaneAba = new JEditorPane();
    String htmlIzquierdo = "", htmlDerecho = "", htmlAbajo = "";

    Dimension pantalla = null;

    private int vNumDocImpreso;
    private String vTipoComprobante;
    private String vNumeroDocumento;
    private String vNumPedVta;

    private boolean isMsjContingencia = false;
    private boolean isReimpresion = false;
    private boolean bMostrarMensaje = true;

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgMensajeImpresion() {
        this(null, "", false);
    }

    Frame myParentFrame;

    public DlgMensajeImpresion(JDialog parent, String title, boolean modal) {
        super(parent, title, modal);
        //myParentFrame = parent;
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
        this.setFocusable(true);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }

        });

        pantalla = Toolkit.getDefaultToolkit().getScreenSize();

        jEditorPaneAba.setContentType("text/html");
        jEditorPaneAba.setText(htmlAbajo);
        jEditorPaneAba.setEditable(false);
        jEditorPaneAba.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 9));

        jEditorPaneAba.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jEditorPaneAba_keyPressed(e);
            }
        });
        this.getContentPane().setLayout(new GridLayout(0, 1));


        pnlAbajo.setLayout(new GridLayout());
        pnlAbajo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jEditorPaneDec_keyPressed(e);
            }
        });
        //Panel Abajo
        pnlAbajo.setLayout(new GridBagLayout());
        pnlAbajo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pnlAbajo_keyPressed(e);
            }
        });
        pnlAbajo.add(jEditorPaneAba);
        this.getContentPane().add(pnlAbajo);
        //  this.setBounds(new Rectangle(50,50,pantalla.width-690,pantalla.height-160));
        this.setSize(pantalla.width, pantalla.height);
        //this.setSize(pantalla);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setModal(true);
        this.setTitle("Mensaje de Impresion");

    }
    // **************************************************************************
    // Initialize
    // **************************************************************************

    private void initialize() {
        // TODO Auto-generated method stub
        //cargaMensajes();
        //jEditorPaneDec.setText(htmlDerecho);
        //jEditorPaneAba.setText(htmlAbajo);
        //jEditorPaneIzq.setText(htmlIzquierdo);
    }
    // **************************************************************************
    // Open y Close
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        cargaMensajes();
        //jEditorPaneDec.setText(htmlDerecho);
        jEditorPaneAba.setText(htmlAbajo);

        FarmaUtility.centrarVentana(this);
        jEditorPaneAba.requestFocus();

        if (!bMostrarMensaje) {
            cerrarVentana(false);
        }
    }

    private void this_windowClosing(WindowEvent e) {


    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    // **************************************************************************
    // KEY PRESSED
    // *************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        log.debug("tecla  +  " + e.getKeyChar());
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (JConfirmDialog.rptaConfirmDialog(this, "¿Está seguro que va imprimir?")) {
                // KMONCADA 01.12.2014 VALIDACION DE DOCUMENTOS PENDIENTES DE IMPRESION
                boolean existePendiente = false;
                String cadena = "";
                try {
                    Map lstComprobantes =
                        DBEpos.validaImpresionPendiente(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal,
                                                        vNumeroDocumento, vTipoComprobante);
                    for (int i = 0; i < lstComprobantes.size(); i++) {
                        String val = (String)lstComprobantes.get("COMPROBANTE");
                        if (!"N".equalsIgnoreCase(val)) {
                            existePendiente = true;
                            cadena = cadena + "\n" +
                                    val;
                        }
                    }
                } catch (Exception ex) {
                    log.info(ex.getMessage());
                }
                //KMONCADA EN CASO DE NO EXISTIR CONTINUA CON COBRO.
                if (!existePendiente) {
                    cerrarVentana(true);
                } else {
                    FarmaUtility.showMessage(this, "Existen comprobantes pendientes de impresion:" + cadena, null);
                }

            }
        }
        //KMONCADA 02.12.2014 ACTUALIZACION DE CORRELATIVO DE COMPROBANTES PRE-IMPRESOS PARA MENSAJE DE CONTIGENCIA
        if (e.getKeyCode() == KeyEvent.VK_F5 && isMsjContingencia) {
            DlgListaImpresoras dlgListaImpresoras = new DlgListaImpresoras(myParentFrame, "", true);
            dlgListaImpresoras.setVisible(true);
            cargaMensajes();
            jEditorPaneAba.setText(htmlAbajo);
        }

        //KMONCADA 02.12.2014 ENVIO A RE-IMPRESION
        if (e.getKeyCode() == KeyEvent.VK_F6) {
            //EN CASO DEL MENSAJE PREVIO DE IMPRESION DE COMPROBANTE DE PRE-IMPRESO
            if (htmlAbajo.contains("Presione [F6]")) {
                cerrarVentana(false);
            }
        }
    }


    private void pnlAbajo_keyPressed(KeyEvent e) {
        log.debug("tecla Abajo +  " + e.getKeyChar());
        chkKeyPressed(e);
    }

    private void jEditorPaneDec_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void jEditorPaneAba_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }


    // **************************************************************************
    // LOGICA DE NEGOCIO
    // **************************************************************************

    public void cargaMensajes() {
        Map map = obtieneMsgImpresion(this, null);
        if (map != null) {
            htmlAbajo = map.get("UNO").toString() + map.get("DOS") + map.get("TRES") + map.get("CUATRO");
        }
    }

    private Map obtieneMsgImpresion(JDialog pDialogo, Object pObjeto) {
        Map resTipo = null;

        try {
            // KMONCADA 02.12.2014 EVALUA SI EL MENSAJE A MOSTRAR EN POR CONTIGENCIA O IMPRESION
            if (isMsjContingencia) {
                resTipo =
                        DBEpos.obtieneMsgContingencia(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, vNumPedVta,
                                                      vTipoComprobante);
            } else {
                resTipo =
                        DBVentas.obtieneMsgImpFactura(vTipoComprobante, vNumDocImpreso, vNumeroDocumento, vNumPedVta, isReimpresion);
            }

        } catch (SQLException sql) {
            bMostrarMensaje = false;
            log.error("", sql);
            FarmaUtility.showMessage(pDialogo, "Error al obtener el mensaje de impresión:\n" +
                    sql.getMessage(), pObjeto);
        }

        return resTipo;
    }


    public void setVNumDocImpreso(int vNumDocImpreso) {
        this.vNumDocImpreso = vNumDocImpreso;
    }

    public void setVTipoComprobante(String vTipoComprobante) {
        this.vTipoComprobante = vTipoComprobante;
    }

    public void setVNumeroDocumento(String vNumeroDocumento) {
        this.vNumeroDocumento = vNumeroDocumento;
    }

    public void setVNumPedVta(String vNumPedVta) {
        this.vNumPedVta = vNumPedVta;
    }

    /**
     * INDICADOR DE MENSAJE DE CONTIGENCIA
     * @param isMsjContingencia
     */
    public void setIsMsjContingencia(boolean isMsjContingencia) {
        this.isMsjContingencia = isMsjContingencia;
    }

    public void setIsReimpresion(boolean isReimpresion) {
        this.isReimpresion = isReimpresion;
    }

    public boolean getMostrarMensaje() {
        return bMostrarMensaje;
    }
}
