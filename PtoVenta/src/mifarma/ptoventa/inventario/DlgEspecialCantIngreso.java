package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
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

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;

import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.VariablesInventario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgEspecialCantIngreso.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCORTEZ      09.09.2008   Creación<br>
 * <br>
 * @author Jorge Cortez Alvarez<br>
 * @version 1.0<br>
 *
 */

public class DlgEspecialCantIngreso extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgEspecialCantIngreso.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private int cantSug = 0;

    private BorderLayout borderLayout1 = new BorderLayout(); //  @jve:decl-index=0:
    private JPanelWhite jContentPane = new JPanelWhite(); //  @jve:decl-index=0:visual-constraint="10,265"
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelWhite lblCodigo_T = new JLabelWhite();
    private JLabelWhite lblDescripcion_T = new JLabelWhite();
    private JLabelWhite lblCodigo = new JLabelWhite();
    private JLabelWhite lblDescripcion = new JLabelWhite();
    private JLabelWhite lblUnidad = new JLabelWhite();
    private JLabelWhite lblUnidad_T = new JLabelWhite();
    private JTextFieldSanSerif txtCantidad = new JTextFieldSanSerif();
    private JLabelWhite lblLaboratorio_T = new JLabelWhite();
    private JLabelWhite lblLaboratorio = new JLabelWhite();
    private JButtonLabel btnCantidad = new JButtonLabel();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgEspecialCantIngreso() {
        this(null, "", false);
    }

    public DlgEspecialCantIngreso(Frame parent, String title, boolean modal) {
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
        jContentPane.setSize(new Dimension(809, 124));
        this.setSize(new Dimension(783, 146));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Ingreso de Cantidad");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(5, 5, 760, 68));
        pnlTitle1.setBackground(Color.white);
        pnlTitle1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(561, 85, 90, 20));
        lblF11.setText("[ ENTER ] Aceptar");
        lblF11.setBounds(new Rectangle(419, 85, 110, 20));
        lblCodigo_T.setText("Codigo :");
        lblCodigo_T.setBounds(new Rectangle(7, 15, 63, 15));
        lblCodigo_T.setForeground(new Color(255, 130, 14));
        lblDescripcion_T.setText("Producto :");
        lblDescripcion_T.setBounds(new Rectangle(122, 15, 80, 15));
        lblDescripcion_T.setForeground(new Color(255, 130, 14));
        lblCodigo.setBounds(new Rectangle(66, 15, 54, 15));
        lblCodigo.setForeground(new Color(255, 140, 34));
        lblDescripcion.setBounds(new Rectangle(185, 15, 363, 15));
        lblDescripcion.setText("");
        lblDescripcion.setForeground(new Color(255, 140, 34));
        lblUnidad.setBounds(new Rectangle(390, 15, 99, 15));
        lblUnidad.setForeground(new Color(255, 140, 34));
        lblUnidad_T.setText("");
        lblUnidad_T.setBounds(new Rectangle(374, 17, 13, 15));
        lblUnidad_T.setForeground(new Color(255, 130, 14));
        txtCantidad.setBounds(new Rectangle(68, 42, 60, 20));
        txtCantidad.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCantidad_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtCantidad_keyTyped(e);
            }
        });
        lblLaboratorio_T.setText("Laboratorio : ");
        lblLaboratorio_T.setBounds(new Rectangle(491, 16, 100, 15));
        lblLaboratorio_T.setForeground(new Color(255, 130, 14));
        lblLaboratorio.setBounds(new Rectangle(565, 15, 184, 15));
        lblLaboratorio.setForeground(new Color(255, 140, 34));
        btnCantidad.setText("Cantidad :");
        btnCantidad.setBounds(new Rectangle(7, 43, 75, 15));
        btnCantidad.setMnemonic('C');
        btnCantidad.setForeground(new Color(255, 130, 14));
        btnCantidad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCantidad_actionPerformed(e);
            }
        });
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(pnlTitle1, null);
        pnlTitle1.add(btnCantidad, null);
        pnlTitle1.add(lblLaboratorio, null);
        pnlTitle1.add(lblLaboratorio_T, null);
        pnlTitle1.add(txtCantidad, null);
        pnlTitle1.add(lblUnidad_T, null);
        pnlTitle1.add(lblUnidad, null);
        pnlTitle1.add(lblDescripcion, null);
        pnlTitle1.add(lblCodigo, null);
        pnlTitle1.add(lblDescripcion_T, null);
        pnlTitle1.add(lblCodigo_T, null);
        //this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setContentPane(jContentPane);
        //
        txtCantidad.setDocument(new FarmaLengthText(6));
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initCabecera();
        /*calcularCantMax();*/
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initCabecera() {
        lblCodigo.setText(VariablesInventario.vCodProd_esp);
        //lblDescripcion.setText(VariablesInventario.vNomProd_esp);
        //lblUnidad.setText(VariablesInventario.vUnidMed_esp);
        lblDescripcion.setText(VariablesInventario.vNomProd_esp.trim() + " / " + VariablesInventario.vUnidMed_esp);
        lblLaboratorio.setText(VariablesInventario.vNomLab_esp);
        txtCantidad.setText(VariablesInventario.vCantIng.trim());

    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */


    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtCantidad);

    }

    private void txtCantidad_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtCantidad_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCantidad, e);
    }

    private void btnCantidad_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCantidad);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            aceptaCantidadIngresada();
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

    private boolean validarCampos() {
        boolean retorno = true;
        if (txtCantidad.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Debe Ingresar la Cantidad.", txtCantidad);
            retorno = false;
        }
        return retorno;
    }

    private void aceptaCantidadIngresada() {
        if (validarCampos()) {
            if (!validaCantidadIngreso()) {
                FarmaUtility.showMessage(this, "Ingrese una cantidad correcta.", txtCantidad);
                return;
            }

            VariablesInventario.vCantIng = Integer.parseInt(txtCantidad.getText().trim()) + "";
            if (!VariablesInventario.flag_modificarCantidad) {
                agregarProducto();
            } else {
                modificarCantidad();
            }
            cerrarVentana(true);
        }
    }

    /**
     *
     */
    private void agregarProducto() {
        ArrayList array = new ArrayList();
        array.add(VariablesInventario.vCodProd_esp);
        array.add(VariablesInventario.vNomProd_esp);
        array.add(VariablesInventario.vUnidMed_esp);
        array.add(VariablesInventario.vNomLab_esp);
        array.add(VariablesInventario.vStkFisico_esp);
        array.add(VariablesInventario.vCantIng);
        array.add(VariablesInventario.vPrecVta_esp);
        array.add(VariablesInventario.vValFrac_esp);

        //arrayProductos.add(array);
        VariablesInventario.vArrayListaProdsEsp.add(array);
        log.debug("array agregado " + array);
        log.debug("VariablesInventario.vArrayProductosEspeciales " + VariablesInventario.vArrayProductosEspeciales);

    }

    /**
     * metodo encargado de modificar la cantidad ingresada de un producto
     *@author jcallo
     *@since  17.10.2008
     */
    private void modificarCantidad() {


        ArrayList array = new ArrayList();
        array = (ArrayList)VariablesInventario.vArrayProductosEspeciales.get(VariablesInventario.vPosi);
        array.set(5, VariablesInventario.vCantIng);
        log.debug("cantidad modificada del producto " + array);
        log.debug("VariablesInventario.vArrayProductosEspeciales " + VariablesInventario.vArrayProductosEspeciales);
    }

    private boolean validaCantidadIngreso() {
        boolean valor = false;
        String cantIngreso = txtCantidad.getText().trim();
        try {
            if (FarmaUtility.isInteger(cantIngreso) && Integer.parseInt(cantIngreso) > 0) {
                valor = true;
            }
        } catch (Exception e) {
            valor = false;
            log.debug("valor incorrecto:" + cantIngreso + " ERROR: " + e);
        }
        return valor;
    }

    /*private boolean validaCantidadMaxima()
  {
    boolean valor = true;
    int cantIngreso = Integer.parseInt(txtCantidad.getText().trim());
    if (cantIngreso <= cantSug)
      valor = false;
    return valor;
  }*/


    /*private void calcularCantMax()
  {
    cantSug = Integer.parseInt(VariablesInventario.vCantMax_PedRep);
  }*/

}  //  @jve:decl-index=0:visual-constraint="10,10"
