package mifarma.ptoventa.otros;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.main.FrmEconoFar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgAcercaDe.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      11.09.2013   Creación<br>
 * <br>
 * @author ERIOS<br>
 * @version 1.0<br>
 *
 */
public class DlgAcercaDe extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgAcercaDe.class);

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle pnlTitle1 = new JPanelTitle();

    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelOrange lblSistema = new JLabelOrange();
    private JLabel jLabel1 = new JLabel();

    private ArrayList<Integer> codigoKonami = new ArrayList<Integer>();
    private ArrayList<Integer> combinacionTeclas = new ArrayList<Integer>();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JLabel lblVersion = new JLabel();
    private JLabel lblCompilacion = new JLabel();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgAcercaDe() {
        this(null, "", false);
    }

    public DlgAcercaDe(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
            FarmaUtility.centrarVentana(this);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        jContentPane.setSize(new Dimension(280, 110));
        this.setSize(new Dimension(299, 186));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Acerca de ...");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                this_keyPressed(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(5, 5, 275, 120));
        pnlTitle1.setBackground(Color.white);
        pnlTitle1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(190, 130, 95, 20));


        lblSistema.setText("FarmaVenta");
        lblSistema.setBounds(new Rectangle(50, 10, 170, 35));
        lblSistema.setFont(new Font("SansSerif", 1, 19));
        lblSistema.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("Version:");
        jLabel1.setBounds(new Rectangle(25, 50, 60, 20));
        jLabel2.setText("Compilacion:");
        jLabel2.setBounds(new Rectangle(25, 70, 75, 20));
        jLabel3.setText("Copyright (c) 2005,2015 MIFARMA S.A.C.");
        jLabel3.setBounds(new Rectangle(5, 100, 265, 15));
        jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
        lblVersion.setBounds(new Rectangle(125, 50, 120, 20));
        lblCompilacion.setBounds(new Rectangle(125, 70, 115, 20));
        jContentPane.add(lblEsc, null);
        pnlTitle1.add(lblCompilacion, null);
        pnlTitle1.add(lblVersion, null);
        pnlTitle1.add(jLabel3, null);
        pnlTitle1.add(jLabel2, null);
        pnlTitle1.add(jLabel1, null);
        pnlTitle1.add(lblSistema, null);
        jContentPane.add(pnlTitle1, null);

        this.getContentPane().add(jContentPane, BorderLayout.CENTER);


    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        
        String fechaSistema;
        try {
            fechaSistema = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            String year = fechaSistema.substring(2+1+2+1);
            jLabel3.setText("Copyright (c) 2005,"+year+" MIFARMA S.A.C.");
        } catch (SQLException e) {
            ;
        }
        
        
        codigoKonami.add(38);
        codigoKonami.add(38);
        codigoKonami.add(40);
        codigoKonami.add(40);
        codigoKonami.add(37);
        codigoKonami.add(39);
        codigoKonami.add(37);
        codigoKonami.add(39);
        codigoKonami.add(66);
        codigoKonami.add(65);

        mostrarVersion();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void mostrarVersion() {
        lblSistema.setText(FarmaVariables.vNombreModulo);
        lblVersion.setText(FarmaVariables.vVersion);
        lblCompilacion.setText(FarmaVariables.vCompilacion);
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */


    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        //mostrarKonami();
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {

        log.debug("" + e.getKeyCode());
        agregaTecla(e.getKeyCode());
        evaluaCombinacion();
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

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


    private void this_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void agregaTecla(int i) {
        combinacionTeclas.add(i);
    }

    private void evaluaCombinacion() {
        int tam = combinacionTeclas.size();
        boolean valor = true;
        if (tam == codigoKonami.size()) {
            for (int i = 0; i < tam; i++) {
                if (!combinacionTeclas.get(i).equals(codigoKonami.get(i))) {
                    valor = false;
                    combinacionTeclas.clear();
                    log.debug("Limpia combinaciones de teclas.");
                    break;
                }
            }
            if (valor) {
                combinacionTeclas.clear();
                //FarmaUtility.showMessage(this, "KONAMI", null);
                mostrarKonami();
            }
        }
    }

    private void mostrarKonami() {

        SwingWorker<String, Integer> worker = new SwingWorker<String, Integer>() {

            ImageIcon imagen = new ImageIcon(FrmEconoFar.class.getResource("/mifarma/ptoventa/imagenes/ryu.png"));
            JLabel dialogIcon = new JLabel(imagen);
            JLabel powered =
                new JLabel("<HTML><FONT COLOR=YELLOW>powered by ERIOS (edgar.gs@gmail.com)</FONT><BR><BR></HTML>");
            JFrame pantalla = new JFrame();
            JPanel panel = new JPanel();

            void mostrar() {
                powered.setHorizontalAlignment(SwingConstants.CENTER);

                panel.setLayout(new BorderLayout());
                panel.setBackground(Color.RED);
                panel.add(powered);

                pantalla.getContentPane().add(dialogIcon, BorderLayout.CENTER);
                pantalla.getContentPane().add(panel, BorderLayout.SOUTH);
                pantalla.setUndecorated(true);
                pantalla.setExtendedState(JFrame.MAXIMIZED_BOTH);
                // Set the window to 55% opaque (45% translucent).
                //pantalla.setOpacity(0.55f);
                pantalla.setOpacity(0.75f);
                pantalla.setAlwaysOnTop(true);
                pantalla.setVisible(true);
            }

            @Override
            protected String doInBackground() throws InterruptedException {
                mostrar();
                Thread.sleep(5 * 1000);
                return null;
            }

            @Override
            public void done() {
                pantalla.setVisible(false);
                pantalla.dispose();
            }
        };
        worker.execute();

    }
}
