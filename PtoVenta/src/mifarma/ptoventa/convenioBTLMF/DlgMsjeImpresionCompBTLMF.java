package mifarma.ptoventa.convenioBTLMF;

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

import mifarma.ptoventa.convenioBTLMF.reference.UtilityConvenioBTLMF;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2011 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgMensajeBTLMF.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * FRAMIREZ      12.11.2011  Creación<br>
 * <br>
 * @author Fredy Ramirez C.<br>
 * @version 1.0<br>
 *
 */

public class DlgMsjeImpresionCompBTLMF extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgMsjeImpresionCompBTLMF.class);

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

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgMsjeImpresionCompBTLMF() {
        this(null, "", false);
    }

    Frame myParentFrame;

    public DlgMsjeImpresionCompBTLMF(Frame parent, String title, boolean modal) {
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

    }
    // **************************************************************************
    // Initialize
    // **************************************************************************

    private void initialize() {
        // TODO Auto-generated method stub
        cargaMensajes();
        //jEditorPaneDec.setText(htmlDerecho);
        jEditorPaneAba.setText(htmlAbajo);
        //jEditorPaneIzq.setText(htmlIzquierdo);
    }
    // **************************************************************************
    // Open y Close
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        jEditorPaneAba.requestFocus();
    }

    private void this_windowClosing(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    // **************************************************************************
    // KEY PRESSED
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        log.debug("tecla  +  " + e.getKeyChar());
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "Esta seguro que va imprimir?")) {
                cerrarVentana(true);
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

        Map map = UtilityConvenioBTLMF.obtieneMsgCompPagoImpr(this, null);

        htmlAbajo = map.get("UNO").toString() + map.get("DOS") + map.get("TRES") + map.get("CUATRO");

        //           htmlAbajo  =     "  <table cellpadding=0 cellspacing=0 align=left> "+
        //
        //					        "   <tr> "+
        //					        "    <td></td> "+
        //					        "    <td width=638 height=383 bgcolor=white style='border:.75pt solid black; "+
        //					        "    vertical-align:top;background:white'><span "+
        //					        "    style='position:absolute;mso-ignore:vglayout;z-index:1'> "+
        //					        "    <table cellpadding=0 cellspacing=0 width=100%> "+
        //					        "     <tr> "+
        //					        "      <td> "+
        //					        "      <div v:shape=_x0000_s1026 style='padding:4.35pt 7.95pt 4.35pt 7.95pt' "+
        //					        "      class=shape> "+
        //					        "      <p class=MsoNormal align=center style='text-align:center'><b><span lang=ES "+
        //					        "      style='font-family:Arial,sans-serif'>*****************************************************************************************<o:p></o:p></span></b></p>"+
        //					        "      <p class=MsoNormal align=center style='text-align:center'><span lang=ES "+
        //					        "      style='font-size:48.0pt;font-family:Arial,sans-serif;color:#C00000'>" +
        //					        "      <font color=#C00000 size =8 face=Arial>¡¡ "+
        //					        "      ALERTA !!</font><o:p></o:p></span></p> "+
        //					        "      <p class=MsoNormal align=center style='text-align:center'><b><span lang=ES "+
        //					        "      style='font-size:18;font-family:Arial,sans-serif;color:#C00000'>" +
        //					        "      <font color=#C00000 size =6 face=Arial>LEER "+
        //					        "      LAS INDICACIONES</font><o:p></o:p></span></b></p>"+
        //					        "      <p class=MsoNormal align=left style='text-align:center'><b><span lang=ES "+
        //					        "      style='font-size:26.0pt;font-family:Arial,sans-serif'><o:p><font color=#139128 size =8 face=Arial>&nbsp "+desComprobantePago+"- 1 Imprimir.</font></o:p></span></b></p> "+
        //					        "      <p class=MsoNormal><b><span lang=ES style='font-family:Arial,sans-serif; "+
        //					        "      color:#139128'> </span></b><b><span lang=ES style='font-size:18.0pt; "+
        //					        "      font-family:Arial,sans-serif;color:#139128'>" +
        //					        "     <font color=#139128 size =5 face=Arial>Revisar:</font>" +
        //					        "      <o:p></o:p></span></b></p> "+
        //					        "      <p class=MsoListParagraph style='margin-left:22.5pt;text-indent:-18.0pt; "+
        //					        "      mso-list:l0 level1 lfo1'><![if !supportLists]><span lang=ES "+
        //					        "      style='font-size:18.0pt;font-family:Symbol;color:#139128'><span "+
        //					        "      style='mso-list:Ignore'>·<span style='font:7.0pt Times New Roman'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+
        //					        "      </span></span></span><![endif]><b><span lang=ES style='font-size:18.0pt; "+
        //					        "      font-family:Arial,sans-serif;color:#139128'>" +
        //					        "     <font color=#139128 size =5 face=Arial>Si esta prendida la "+
        //					        "      impresora de "+desComprobantePago+".</font>" +
        //					        	"  <o:p></o:p></span></b></p> "+
        //					        "      <p class=MsoListParagraph style='margin-left:22.5pt;text-indent:-18.0pt; "+
        //					        "      mso-list:l0 level1 lfo1'><![if !supportLists]><span lang=ES "+
        //					        "      style='font-size:18.0pt;font-family:Symbol;color:#139128'><span "+
        //					        "      style='mso-list:Ignore'>·<span style='font:7.0pt Times New Roman'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+
        //					        "      </span></span></span><![endif]><b><span lang=ES style='font-size:18.0pt; "+
        //					        "      font-family:Arial,sans-serif;color:#139128'><font color=#139128 size =5 face=Arial>Si cuenta con papel para "+
        //					        "      imprimir "+desComprobantePago+".</font><o:p></o:p></span></b></p> "+
        //					        "      <p class=MsoListParagraph style='margin-left:22.5pt;text-indent:-18.0pt; "+
        //					        "      mso-list:l0 level1 lfo1'><![if !supportLists]><span lang=ES "+
        //					        "      style='font-size:18.0pt;font-family:Symbol;color:#139128'><span "+
        //					        "      style='mso-list:Ignore'>·<span style='font:7.0pt Times New Roman'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+
        //					        "      </span></span></span><![endif]><b><span lang=ES style='font-size:18.0pt; "+
        //					        "      font-family:Arial,sans-serif;color:#139128'>" +
        //					        "  <font color=#139128 size =5 face=Arial>Si el Correlativo del "+
        //					        "      comprobante sea el mismo con el sistema.</font><o:p></o:p></span></b></p> "+
        //					        "      <p class=MsoNormal><span lang=ES style='font-family:Arial,sans-serif'>*****************************************************************************************<o:p></o:p></span>" +
        //					        "      <H3>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" +
        //					        "          &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" +
        //					        "          &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" +
        //					        "          &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp; Presione [Enter] para continuar...</H3></o:p></p> "+
        //					        "      </div> "+
        //					        "      <![if !mso]></td> "+
        //					        "     </tr> "+
        //					        "    </table> "+
        //					        "    </span></td> "+
        //					        "   </tr> "+
        //					        "  </table> ";
        //


    }


}
