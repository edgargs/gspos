package mifarma.ptoventa.reportes;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reportes.reference.VariablesReporte;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgGrafico extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgGrafico.class);

    private Frame myParentFrame;
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private GridLayout gridLayout1 = new GridLayout();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JLabelFunction jLabelFunction2 = new JLabelFunction();
    private JTextField jTextField1 = new JTextField();
    private GridLayout gridLayout2 = new GridLayout();
    private JLabel jLabel1 = new JLabel();

    public DlgGrafico() {
        this(null, "", false);
    }

    public DlgGrafico(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void initialize() {
        cargaGrafico();
        FarmaVariables.vAceptar = false;
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(766, 522));
        this.getContentPane().setLayout(gridLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Grafico");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jPanelTitle1.setBounds(new Rectangle(5, 10, 750, 445));
        jPanelTitle1.setBackground(Color.white);
        jPanelTitle1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        jPanelTitle1.setLayout(gridLayout2);
        jLabelFunction2.setBounds(new Rectangle(660, 465, 95, 20));
        jLabelFunction2.setText("[ ESC ] Escape");

        jTextField1.setBounds(new Rectangle(770, 470, 20, 15));
        jTextField1.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                jTextField1_keyReleased(e);
            }
        });
        //jTextField1.setVisible(false);
        jLabel1.setText("jLabel1");
        jPanelWhite1.add(jLabelFunction2, null);
        jPanelWhite1.add(jPanelTitle1, null);
        jPanelWhite1.add(jTextField1, null);
        this.getContentPane().add(jPanelWhite1, null);
    }


    private void this_windowOpened(WindowEvent e) {


        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(jTextField1);
        // jTextField1.setVisible(false);
    }

    private void chkKeyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    public void cargaGrafico() {
        if (VariablesReporte.vDataSet_Reporte != null) {
            JFreeChart chart =
                ChartFactory.createBarChart3D("Reporte Ventas por Vendedor", "Vendedores", "Procentaje", VariablesReporte.vDataSet_Reporte,
                                              PlotOrientation.VERTICAL, true, true, false);

            //JFrame frame1=new JFrame("Reporte Ventas por Vendedor");
            ChartPanel panel = new ChartPanel(chart);

            //FarmaUtility.showMessage(this, "dibujando " + jPanelWhite1.getComponentCount(),null);
            //5, 10, 750, 445
            //panel.setBounds(6,11,748,443);
            //panel.setVisible(true);
            //panel.validate();
            //jPanelWhite1.add(panel,null);
            jPanelTitle1.add(panel);
            //jPanelWhite1.repaint();
            //FarmaUtility.showMessage(this, "dibujando " + jPanelWhite1.getComponentCount(),null);
            //  jPanelWhite1.setVisible(true);

            //frame1.getContentPane().add(panel);
            //frame1.setSize(300,300);
            //frame1.setVisible(true);
        }
    }

    private void jTextField1_keyReleased(KeyEvent e) {
        chkKeyPressed(e);
    }
}
