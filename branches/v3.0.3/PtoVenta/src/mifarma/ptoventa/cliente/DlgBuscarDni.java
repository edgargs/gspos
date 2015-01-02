package mifarma.ptoventa.cliente;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.campAcumulada.reference.DBCampAcumulada;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgBuscarDni extends JDialog {
    @SuppressWarnings("compatibility:-2208895987879323218")
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DlgBuscarDni.class);

    private Frame myParentFrame;

    private JLabelFunction lblEnter = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JFormattedTextField txt_buscar;
    private ArrayList<Object> resultado = new ArrayList<Object>();
    private MaskFormatter mascara1;
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();

    public DlgBuscarDni() {
        this(null, "", false);
    }

    public DlgBuscarDni(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(314, 123));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Buscar DNI");
        mascara1 = new MaskFormatter("########");
        lblEnter.setText("[Enter] Buscar");
        lblEnter.setBounds(new Rectangle(95, 70, 95, 20));
        lblEnter.setSize(new Dimension(95, 20));
        lblEnter.setFocusable(false);
        lblEsc.setText("[ Esc ] Salir");
        lblEsc.setBounds(new Rectangle(195, 70, 90, 20));
        lblEsc.setSize(new Dimension(90, 20));
        lblEsc.setPreferredSize(new Dimension(67, 19));
        lblEsc.setMinimumSize(new Dimension(67, 19));
        lblEsc.setMaximumSize(new Dimension(67, 19));
        lblEsc.setFocusable(false);
        setTxt_buscar(new JFormattedTextField(mascara1));
        getTxt_buscar().setBounds(new Rectangle(85, 15, 155, 20));
        getTxt_buscar().setSize(new Dimension(155, 20));
        getTxt_buscar().addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txt_buscar_keyPressed(e);
            }
        });
        jLabelWhite1.setText("Ingrese DNI:");
        jLabelWhite1.setBounds(new Rectangle(10, 10, 70, 30));
        jPanelHeader1.setBounds(new Rectangle(10, 10, 285, 50));
        jPanelHeader1.setSize(new Dimension(285, 50));
        jPanelHeader1.add(jLabelWhite1, null);
        jPanelHeader1.add(getTxt_buscar(), null);
        jPanelWhite1.add(jPanelHeader1, null);
        jPanelWhite1.add(lblEnter, null);
        jPanelWhite1.add(lblEsc, null);
        this.getContentPane().add(jPanelWhite1, BorderLayout.CENTER);
        FarmaUtility.centrarVentana(this);
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            String vdni = getTxt_buscar().getText().trim();
            if (vdni.length() == 8) {
                try {
                    DBCampAcumulada.getDatosExisteDNI(resultado, vdni);
                    if (resultado.size() > 0)
                        cerrarVentana(true);
                    else {
                        FarmaUtility.showMessage(this, "No se encontro el numero de DNI indicado", null);
                        DlgRegistroCliente dlgRegistroCliente = new DlgRegistroCliente(myParentFrame, "", true);
                        dlgRegistroCliente.setDNI(vdni);
                        dlgRegistroCliente.setVisible(true);
                        if (FarmaVariables.vAceptar)
                            chkKeyPressed(e);
                    }
                } catch (Exception ex) {
                    log.error("", ex);
                }
            } else
                FarmaUtility.showMessage(this, "El formato del DNI es incorrecto", null);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    public void cerrarVentana(boolean aceptar) {
        FarmaVariables.vAceptar = aceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void txt_buscar_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    public ArrayList<Object> retornarResultado() {
        return resultado;
    }

    public JFormattedTextField getTxt_buscar() {
        return txt_buscar;
    }

    public void setTxt_buscar(JFormattedTextField txt_buscar) {
        this.txt_buscar = txt_buscar;
    }
}
