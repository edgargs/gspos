package com.gs.mifarma.worker;


import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.JProgressBar;

import mifarma.common.FarmaUtility;

import mifarma.ptoventa.FrmEconoFar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Ventana de progreso
 * @author ERIOS
 * @since 15.07.2013
 */
public abstract class JDialogProgress extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(JDialogProgress.class);

    private JPanel jPanel1 = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JLabel jLabel1 = new JLabel();

    private JFarmaWorker trabajador;
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JLabel jLabel2 = new JLabel();
    private JProgressBar jProgressBar2 = new JProgressBar();

    public JDialogProgress() {
        super();
        try {
            jbInit();
        } catch (Exception e) {
            log.error("",e);
        }
    }

    public JDialogProgress(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
    
        } catch (Exception e) {
            log.error("",e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(339, 157));
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.setTitle("Proceso en ejecución. Espere...");
        
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }

                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }
                });
        
        jPanel1.setLayout(borderLayout1);
        jLabel1.setText("Progreso");
        jLabel1.setFont(new Font("SansSerif", 1, 12));
        ImageIcon imagen = new ImageIcon(FrmEconoFar.class.getResource("/mifarma/ptoventa/imagenes/cargando.gif"));
        jLabel2 = new JLabel(imagen);
        jLabel2.setBounds(new Rectangle(0, 0, 320, 95));
        jPanel1.add(jLabel1, BorderLayout.NORTH);
        jPanelWhite1.add(jLabel2, null);
        jPanel1.add(jPanelWhite1, BorderLayout.CENTER);
        jPanel1.add(jProgressBar2, BorderLayout.SOUTH);
        this.getContentPane().add(jPanel1, null);
    }

    public void mostrar() {
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void cerrar() {
        setVisible(false);
        dispose();
    }
    
    private void this_windowOpened(WindowEvent e) {
        iniciaProcesoProgressBar();
    }  
    
    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, 
                                 "Acción prohibida, esperar que termine el proceso.", 
                                 null);
    }
    
    private void iniciaProcesoProgressBar(){
        if (trabajador == null) {
            trabajador = new JFarmaWorker(this,jLabel1,jProgressBar2);

            //Agrego un Listener para el cambio de la propiedad "progress"
            trabajador.addPropertyChangeListener(new PropertyChangeListener() {
                        public void propertyChange(PropertyChangeEvent evt) {
                            if ("progress".equals(evt.getPropertyName())) {
                                jLabel1.setText(evt.getNewValue() + 
                                                      " %");
                            }
                        }
                    });
        }
        trabajador.execute();
    }

    
    public abstract void ejecutaProceso();
}
