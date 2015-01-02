package mifarma.ptoventa.delivery;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JDialog;

import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.delivery.reference.ConstantsDelivery;
import mifarma.ptoventa.delivery.reference.DBDelivery;
import mifarma.ptoventa.delivery.reference.VariablesDelivery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgBuscaDni.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      11.04.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgBuscaDni extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgBuscaDni.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JTextFieldSanSerif txtDniApellido = new JTextFieldSanSerif();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JButtonLabel jButtonLabel1 = new JButtonLabel();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgBuscaDni() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    public DlgBuscaDni(Frame parent, String title, boolean modal) {
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
        this.setTitle("DNI");
        this.setSize(new Dimension(299, 129));
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(10, 10, 275, 50));
        txtDniApellido.setBounds(new Rectangle(95, 15, 170, 20));

        txtDniApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtDni_keyPressed(e);
            }
        });
        lblEsc.setBounds(new Rectangle(190, 70, 95, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblF11.setBounds(new Rectangle(75, 70, 105, 20));
        lblF11.setText("[ F11 ] Aceptar");
        jButtonLabel1.setText("DNI / Apellido");
        jButtonLabel1.setBounds(new Rectangle(5, 15, 85, 15));
        jButtonLabel1.setMnemonic('D');
        jButtonLabel1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonLabel1_actionPerformed(e);
            }
        });
        pnlTitle1.add(jButtonLabel1, null);
        pnlTitle1.add(txtDniApellido, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(pnlTitle1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        txtDniApellido.setDocument(new FarmaLengthText(30));
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(txtDniApellido);
        FarmaUtility.centrarVentana(this);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void txtDni_keyPressed(KeyEvent e) {
        chkKeyPressed(e);

    }


    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e) || e.getKeyCode() == KeyEvent.VK_ENTER) {
            txtDniApellido.setText(txtDniApellido.getText().trim().toUpperCase());
            String textoBusqueda = txtDniApellido.getText().trim().toUpperCase();
            if (textoBusqueda.length() >= 3) {
                char primerkeyChar = textoBusqueda.charAt(0);
                char ultimokeyChar = textoBusqueda.charAt(textoBusqueda.length() - 1);
                // Si el primer y último character son numeros asumimos que es DNI
                if (!Character.isLetter(primerkeyChar) && !Character.isLetter(ultimokeyChar) &&
                    textoBusqueda.length() > 7) {
                    VariablesDelivery.vCantidadLista = ConstantsDelivery.CANTIDADMENOR1;
                    buscaCliente(ConstantsDelivery.TIPO_BUSQUEDA_DNI, textoBusqueda);
                    log.debug(ConstantsDelivery.CANTIDADMENOR1);
                } else {
                    VariablesDelivery.vCantidadLista = ConstantsDelivery.CANTIDADMAYOR1;
                    buscaCliente(ConstantsDelivery.TIPO_BUSQUEDA_APELLIDO, textoBusqueda);
                    log.debug(ConstantsDelivery.CANTIDADMAYOR1);
                }
            } else
                FarmaUtility.showMessage(this, "Ingrese 3 caracteres como minimo para realizar la busqueda",
                                         txtDniApellido);
            if (FarmaVariables.vAceptar)
                cerrarVentana(true);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void jButtonLabel1_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtDniApellido);

    }

    private void txtApellido_actionPerformed(ActionEvent e) {

    }

    private void jButtonLabel2_actionPerformed(ActionEvent e) {
        //FarmaUtility.moveFocus(txtApellido);
        //txtDni.setEnabled(false);
    }

    void muestraListaClientesBusqueda() {
        DlgListaClienteBusqueda dlgListaClienteBusqueda = new DlgListaClienteBusqueda(myParentFrame, "", true);
        dlgListaClienteBusqueda.setVisible(true);
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void buscaCliente(String pTipoBusqueda, String pBusqueda) {
        VariablesDelivery.vTipoBusqueda = pTipoBusqueda;
        VariablesDelivery.vDni_Apellido_Busqueda = pBusqueda;
        cargaListaClientes();
    }

    private void cargaListaClientes() {
        log.debug("VariablesDelivery.vCodCli : " + VariablesDelivery.vCodCli);
        if (VariablesDelivery.vCantidadLista.equals(ConstantsDelivery.CANTIDADMAYOR1)) {
            muestraListaClientesBusqueda();
        } else {

            try {
                log.debug("VariablesDelivery.vDni_Apellido_Busqueda : " + VariablesDelivery.vDni_Apellido_Busqueda);
                log.debug("VariablesDelivery.vTipoBusqueda" + VariablesDelivery.vTipoBusqueda);
                DBDelivery.obtieneClientesEnArreglo(VariablesDelivery.vInfoClienteBusqueda,
                                                    VariablesDelivery.vDni_Apellido_Busqueda,
                                                    VariablesDelivery.vTipoBusqueda);
                if (VariablesDelivery.vInfoClienteBusqueda.size() > 0) {
                    VariablesDelivery.vCodCli =
                            ((String)((ArrayList)VariablesDelivery.vInfoClienteBusqueda.get(0)).get(0)).trim();
                } else
                    VariablesDelivery.vCodCli = "";
                log.debug("VariablesDelivery.vCodCli : " + VariablesDelivery.vCodCli);
                log.debug("Arreglo" + VariablesDelivery.vInfoClienteBusqueda);

            } catch (SQLException sqlException) {
                //VariablesDelivery.infoClienteBusqueda= new ArrayList();
                log.error("", sqlException);
            }
            cerrarVentana(true);
        }
    }
}
