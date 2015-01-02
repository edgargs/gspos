package com.gs.mifarma.componentes;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LinearGradientPaint;
import java.awt.Toolkit;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PopupAlert extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(PopupAlert.class);
    private LinearGradientPaint lpg;
    private PopupConstants pC = new PopupConstants();

    private ArrayList pMensaje = new ArrayList();

    public int pTipoMensaje = 0;
    public String pTitulo = "";
    public int pSegundos = 0;


    JTextArea titulo = new JTextArea();
    JTextArea texto = new JTextArea();
    JLabel imagen;

    Frame myParentFrame;

    public void addMsg(String pMsg) {
        pMensaje.add(pMsg.trim());
    }

    public PopupAlert(int pTipoMensaje, String pTitulo, int pSegundos, Frame parent, boolean modal) {
        super(parent, pTitulo, modal);
        myParentFrame = parent;
        setUndecorated(true);
        setSize(400, 120);
        // size of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // height of the task bar
        Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
        int taskBarSize = scnMax.bottom;

        setLocation(screenSize.width - getWidth(), screenSize.height - taskBarSize - getHeight());

        // background paint
        lpg =
new LinearGradientPaint(0, 0, 0, getHeight() / 2, new float[] { 0f, 0.3f, 1f }, new Color[] { new Color(1f, 1f, 1f),
                                                                                              new Color(1f, 1f, 1f),
                                                                                              new Color(1f, 1f, 1f) });

        this.pTipoMensaje = pTipoMensaje;
        this.pTitulo = pTitulo;
        this.pSegundos = pSegundos;
        // blue background panel
        setContentPane(new BackgroundPanel());


    }

    public void open() {

        Container c = getContentPane();
        c.setLayout(new GridBagLayout());

        GridBagConstraints constraintsImg = new GridBagConstraints();
        constraintsImg.gridx = 0;
        constraintsImg.gridy = 0;
        constraintsImg.fill = GridBagConstraints.BOTH;

        GridBagConstraints constraintsTitulo = new GridBagConstraints();
        constraintsTitulo.gridx = 1;
        constraintsTitulo.gridy = 0;
        constraintsTitulo.fill = GridBagConstraints.BOTH;

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.gridheight = 2;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;


        ImageIcon img;
        if (pTipoMensaje == pC.NOTIFICACION)
            img = new ImageIcon(PopupAlert.class.getResource("/mifarma/ptoventa/imagenes/hint.png"));
        else {
            if (pTipoMensaje == pC.ALERTA)
                img = new ImageIcon(PopupAlert.class.getResource("/mifarma/ptoventa/imagenes/warn2.png"));
            else
                img = new ImageIcon(PopupAlert.class.getResource("/mifarma/ptoventa/imagenes/warn.png"));
        }


        imagen = new JLabel(img);
        //final JTextArea titulo = new JTextArea();
        imagen.setOpaque(true);

        texto.setBackground(new Color(152, 197, 228));
        texto.setFont(new Font("Verdana", Font.BOLD, 13));
        texto.setForeground(new Color(18, 86, 131));


        /*
        texto.setText("  "+"Se tiene 4 Pedidos Pendientes\n" +
                      "  "+"Para cobrar.");
        */
        String pCadena = "";
        for (int pos = 0; pos < pMensaje.size(); pos++) {
            pCadena = pCadena + "  " + pMensaje.get(pos).toString().trim();
            if (pMensaje.size() > 1) {
                if (!((pos + 1) == pMensaje.size())) {
                    pCadena = pCadena + "\n";
                }
            }
        }

        texto.setText(pCadena);


        texto.setEditable(false);
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);

        imagen.setBackground(new Color(152, 197, 228));


        titulo.setText("\n" +
                "   " + pTitulo + "\n");
        titulo.setBackground(new Color(152, 197, 228));
        titulo.setFont(new Font("Verdana", Font.BOLD, 17));
        titulo.setForeground(new Color(18, 86, 131));

        titulo.setEditable(false);
        titulo.setLineWrap(true);
        titulo.setWrapStyleWord(true);

        c.add(imagen, constraintsImg);
        c.add(titulo, constraintsTitulo);
        c.add(texto, constraints);

        try {
            mostrar();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private class BackgroundPanel extends JPanel {
        public BackgroundPanel() {
            setOpaque(true);
        }


        /* protected void paintComponent(final Graphics g) {
      Graphics2D g2d = (Graphics2D) g;
      // background
      g2d.setPaint(lpg);
      g2d.fillRect(0, 0, getWidth() - 2, getHeight() - 2);
      g2d.setColor(new Color(0,53,88)); //border color
      // border
      g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
    }*/
    }

    public void mostrar() throws Exception {
        setSize(0, 0);
        SwingWorker<Boolean, Void> workerOpen = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                abrir();
                // Here we can return some object of whatever type
                // we specified for the first template parameter.
                // (in this case we're auto-boxing 'true').
                for (int i = pSegundos; i > 0; i--) {
                    if ((i % 2) == 1) {
                        texto.setForeground(new Color(152, 197, 228));
                        texto.setBackground(new Color(18, 86, 131));

                        titulo.setForeground(new Color(152, 197, 228));
                        titulo.setBackground(new Color(18, 86, 131));

                        imagen.setBackground(new Color(18, 86, 131));
                    } else {
                        texto.setBackground(new Color(152, 197, 228));
                        texto.setForeground(new Color(18, 86, 131));

                        titulo.setBackground(new Color(152, 197, 228));
                        titulo.setForeground(new Color(18, 86, 131));

                        imagen.setBackground(new Color(152, 197, 228));

                    }
                    repaint();

                    Thread.sleep(1000);
                }
                ocultar();

                return true;
            }

            // Can safely update the GUI from this method.

            protected void done() {

                /*  boolean status;
          try {
           // Retrieve the return value of doInBackground.
           //status = get();
           //statusLabel.setText('Completed with status: ' + status);

          } catch (InterruptedException e) {
           // This is thrown if the thread's interrupted.
          } catch (ExecutionException e) {
           // This is thrown if we throw an exception
           // from doInBackground.
          } */
                setVisible(false);
            }

            private void abrir() throws Exception {

                for (int i = 40; i > 0; i--) {
                    Thread.sleep(10);
                    //setSize(400, 120);
                    setSize(getWidth() + 10, getHeight() + 3);
                    // size of the screen
                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    // height of the task bar
                    Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
                    int taskBarSize = scnMax.bottom;
                    setLocation(screenSize.width - getWidth(), screenSize.height - taskBarSize - getHeight());
                    repaint();

                }
            }

            private void ocultar() throws Exception {
                for (int i = 40; i > 0; i--) {
                    Thread.sleep(10);
                    setSize(getWidth() - 10, getHeight() - 3);
                    // size of the screen
                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    // height of the task bar
                    Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
                    int taskBarSize = scnMax.bottom;
                    setLocation(screenSize.width - getWidth(), screenSize.height - taskBarSize - getHeight());
                    repaint();

                }
            }

        };

        workerOpen.execute();


        setVisible(true);

    }

}
