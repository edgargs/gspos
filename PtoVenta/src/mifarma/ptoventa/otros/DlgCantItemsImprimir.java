package mifarma.ptoventa.otros;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JDialog;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.otros.reference.VariablesOtros;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgSelecLocal.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCALLO      20.10.2008   Creación<br>
 * <br>
 * @author JCALLO<br>
 * @version 1.0<br>
 *
 */
public class DlgCantItemsImprimir extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgCantItemsImprimir.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle pnlTitle1 = new JPanelTitle();

    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblENTER = new JLabelFunction();

    private JButtonLabel btnCantItems = new JButtonLabel();

    private JTextFieldSanSerif txtCantItems = new JTextFieldSanSerif();


    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgCantItemsImprimir() {
        this(null, "", false);
    }

    public DlgCantItemsImprimir(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(280, 110));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Imprimir Historial");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(5, 5, 260, 40));
        pnlTitle1.setBackground(Color.white);
        pnlTitle1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(170, 50, 95, 20));
        lblENTER.setText("[ ENTER ] Aceptar");
        lblENTER.setBounds(new Rectangle(40, 50, 120, 20));

        btnCantItems.setBounds(new Rectangle(10, 10, 80, 20));
        btnCantItems.setText("Cant. Items :");
        btnCantItems.setForeground(new Color(255, 140, 34));
        btnCantItems.setMnemonic('C');
        btnCantItems.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCantItems_actionPerformed(e);
            }
        });


        txtCantItems.setBounds(new Rectangle(90, 10, 50, 20));
        txtCantItems.setLengthText(2);
        txtCantItems.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCantItems_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtCantItems_keyTyped(e);
            }
        });

        jContentPane.add(lblENTER, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(pnlTitle1, null);
        pnlTitle1.add(btnCantItems, null);
        pnlTitle1.add(txtCantItems, null);

        this.getContentPane().add(jContentPane, BorderLayout.CENTER);


    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initCabecera();
        FarmaVariables.vAceptar = false;
        try {
            int cantItems =
                Math.min(VariablesOtros.paramCantImprimirXReg, VariablesOtros.vTableModelHist.getRowCount());
            //log.debug(" cantItems : "+cantItems);
            txtCantItems.setText("" + cantItems);
        } catch (Exception e) {
            log.debug(" error : " + e.getMessage());
        }


    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initCabecera() {

    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */


    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtCantItems);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }


    private void txtCantItems_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtCantItems_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCantItems, e);
    }

    private void btnCantItems_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCantItems);
    }


    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            txtCantItems.setText(txtCantItems.getText().trim());
            if (txtCantItems.getText().length() < 1) { // este caso que eliminara la fecha y hora
                FarmaUtility.showMessage(this, "Debe especificar la cantidad a imprimir", txtCantItems);
            } else {
                if (CantidadValida(txtCantItems.getText())) {
                    int canItems = Integer.parseInt(txtCantItems.getText());

                    if (canItems > VariablesOtros.vTableModelHist.getRowCount()) {
                        FarmaUtility.showMessage(this,
                                                 "Cantidad no valido !\nDicha cantidad es mayor a la disponible.",
                                                 txtCantItems);
                    } else if (canItems > VariablesOtros.paramCantMaxImprimirXHist) {
                        FarmaUtility.showMessage(this,
                                                 "Cantidad no valido !\nLa cantidad maxima permitida es " + VariablesOtros.paramCantMaxImprimirXHist,
                                                 txtCantItems);
                    } else {
                        VariablesOtros.vCantItemsImprimir = canItems;
                        cerrarVentana(true);
                    }
                } else {
                    FarmaUtility.showMessage(this, "Cantidad no valida !", txtCantItems);
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


    /**
     * Metodo encargado de validar cantidad ingresado
     *@autor jcallo
     *@since 20.10.2008
     */
    public boolean CantidadValida(String cantidad) {
        boolean flag = false;
        try {
            int cant = Integer.parseInt(cantidad);

            if (cant > 0) {
                //VariablesOtros.vCantItemsImprimir = cant;
                flag = true;
            }
        } catch (Exception e) {
            flag = false;
            log.debug("ERROR : " + e);
        }
        return flag;
    }

}
