package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JDialog;

import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.UtilityInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgPedidoReposicionIngresoCantidad extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgPedidoReposicionIngresoCantidad.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private int cantSug = 0;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnlHeader1 = new JPanelHeader();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelWhite lblStock_T = new JLabelWhite();
    private JLabelWhite lblFechaHoraActual = new JLabelWhite();
    private JLabelWhite lblUnidades_T = new JLabelWhite();
    private JLabelWhite lblUnidades = new JLabelWhite();
    private JLabelWhite lblCodigo_T = new JLabelWhite();
    private JLabelWhite lblDescripcion_T = new JLabelWhite();
    private JLabelWhite lblCodigo = new JLabelWhite();
    private JLabelWhite lblDescripcion = new JLabelWhite();
    private JLabelWhite lblUnidad = new JLabelWhite();
    private JLabelWhite lblUnidad_T = new JLabelWhite();
    private JLabelWhite lblValorFrac = new JLabelWhite();
    private JLabelWhite lblValorFrac_T = new JLabelWhite();
    private JTextFieldSanSerif txtCantidad = new JTextFieldSanSerif();
    private JLabelWhite lblLaboratorio_T = new JLabelWhite();
    private JLabelWhite lblLaboratorio = new JLabelWhite();
    private JButtonLabel btnCantidad = new JButtonLabel();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgPedidoReposicionIngresoCantidad() {
        this(null, "", false);
    }

    public DlgPedidoReposicionIngresoCantidad(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(435, 296));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Ingreso de Cantidad");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlHeader1.setBounds(new Rectangle(10, 10, 410, 40));
        pnlHeader1.setBackground(Color.white);
        pnlHeader1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlTitle1.setBounds(new Rectangle(10, 60, 410, 170));
        pnlTitle1.setBackground(Color.white);
        pnlTitle1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(330, 240, 90, 20));
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(205, 240, 110, 20));
        lblStock_T.setText("Stock del Producto al:");
        lblStock_T.setBounds(new Rectangle(15, 10, 110, 15));
        lblStock_T.setFont(new Font("SansSerif", 0, 11));
        lblStock_T.setForeground(new Color(255, 130, 14));
        lblFechaHoraActual.setBounds(new Rectangle(125, 10, 100, 15));
        lblFechaHoraActual.setForeground(new Color(255, 130, 14));
        lblUnidades_T.setText("Unidades:");
        lblUnidades_T.setBounds(new Rectangle(255, 10, 55, 15));
        lblUnidades_T.setFont(new Font("SansSerif", 0, 11));
        lblUnidades_T.setForeground(new Color(255, 130, 14));
        lblUnidades.setBounds(new Rectangle(305, 10, 85, 15));
        lblUnidades.setForeground(new Color(255, 130, 14));
        lblCodigo_T.setText("Código");
        lblCodigo_T.setBounds(new Rectangle(15, 15, 70, 15));
        lblCodigo_T.setForeground(new Color(255, 130, 14));
        lblDescripcion_T.setText("Descripción");
        lblDescripcion_T.setBounds(new Rectangle(90, 15, 70, 15));
        lblDescripcion_T.setForeground(new Color(255, 130, 14));
        lblCodigo.setBounds(new Rectangle(15, 39, 70, 15));
        lblCodigo.setFont(new Font("SansSerif", 0, 11));
        lblCodigo.setForeground(new Color(255, 130, 14));
        lblDescripcion.setBounds(new Rectangle(90, 40, 285, 15));
        lblDescripcion.setFont(new Font("SansSerif", 0, 11));
        lblDescripcion.setForeground(new Color(255, 130, 14));
        lblUnidad.setBounds(new Rectangle(15, 85, 125, 15));
        lblUnidad.setFont(new Font("SansSerif", 0, 11));
        lblUnidad.setForeground(new Color(255, 130, 14));
        lblUnidad_T.setText("Unidad");
        lblUnidad_T.setBounds(new Rectangle(15, 62, 70, 15));
        lblUnidad_T.setForeground(new Color(255, 130, 14));
        lblValorFrac.setBounds(new Rectangle(145, 85, 70, 15));
        lblValorFrac.setFont(new Font("SansSerif", 0, 11));
        lblValorFrac.setForeground(new Color(255, 130, 14));
        lblValorFrac_T.setText("Valor Frac.");
        lblValorFrac_T.setBounds(new Rectangle(145, 60, 70, 15));
        lblValorFrac_T.setForeground(new Color(255, 130, 14));
        txtCantidad.setBounds(new Rectangle(45, 135, 60, 20));
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCantidad_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtCantidad_keyTyped(e);
            }
        });
        lblLaboratorio_T.setText("Laboratorio");
        lblLaboratorio_T.setBounds(new Rectangle(220, 60, 75, 15));
        lblLaboratorio_T.setForeground(new Color(255, 130, 14));
        lblLaboratorio.setBounds(new Rectangle(220, 85, 175, 15));
        lblLaboratorio.setFont(new Font("SansSerif", 0, 11));
        lblLaboratorio.setForeground(new Color(255, 130, 14));
        btnCantidad.setText("Cantidad");
        btnCantidad.setBounds(new Rectangle(45, 115, 75, 15));
        btnCantidad.setMnemonic('C');
        btnCantidad.setForeground(new Color(255, 130, 14));
        btnCantidad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCantidad_actionPerformed(e);
            }
        });
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        pnlTitle1.add(btnCantidad, null);
        pnlTitle1.add(lblLaboratorio, null);
        pnlTitle1.add(lblLaboratorio_T, null);
        pnlTitle1.add(txtCantidad, null);
        pnlTitle1.add(lblValorFrac_T, null);
        pnlTitle1.add(lblValorFrac, null);
        pnlTitle1.add(lblUnidad_T, null);
        pnlTitle1.add(lblUnidad, null);
        pnlTitle1.add(lblDescripcion, null);
        pnlTitle1.add(lblCodigo, null);
        pnlTitle1.add(lblDescripcion_T, null);
        pnlTitle1.add(lblCodigo_T, null);
        jContentPane.add(pnlTitle1, null);
        pnlHeader1.add(lblUnidades, null);
        pnlHeader1.add(lblUnidades_T, null);
        pnlHeader1.add(lblFechaHoraActual, null);
        pnlHeader1.add(lblStock_T, null);
        jContentPane.add(pnlHeader1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        //
        txtCantidad.setDocument(new FarmaLengthText(6));
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initCabecera();
        calcularCantMax();
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initCabecera() {
        lblFechaHoraActual.setText(VariablesInventario.vFechaHora_PedRep);
        lblUnidades.setText(VariablesInventario.vStkFisico_PedRep);
        lblCodigo.setText(VariablesInventario.vCodProd_PedRep);
        lblDescripcion.setText(VariablesInventario.vNomProd_PedRep);
        lblUnidad.setText(VariablesInventario.vUnidMed_PedRep);
        lblValorFrac.setText(VariablesInventario.vValFrac_PedRep);
        lblLaboratorio.setText(VariablesInventario.vNomLab_PedRep);

        txtCantidad.setText(VariablesInventario.vCant_PedRep.trim());
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
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
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
            } else if (validaCantidadMaxima()) {
                FarmaUtility.showMessage(this,
                                         "Debe ingresar una cantidad Menor o Igual a la cantidad maxima aceptada por el Sistema: " +
                                         cantSug, txtCantidad);
                return;
            }
            ///26.09.2007 DUBILLUZ MODIFICACION
            else if (validaIngresoExistsAdicional()) {
                FarmaUtility.showMessage(this,
                                         "Si ingreso un adicional debe de ingresar la cantidad maxima :" + VariablesInventario.vCantMax_PedRep,
                                         txtCantidad);
                return;
            }
            /////
            //if(com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,"¿Está seguro de guardar los datos?"))
            {
                VariablesInventario.vCant_PedRep = txtCantidad.getText().trim();
                if (UtilityInventario.guardarCantidadIngresada(this, txtCantidad)) {
                    cerrarVentana(true);
                }
            }
        }
    }

    private boolean validaCantidadIngreso() {
        boolean valor = false;
        String cantIngreso = txtCantidad.getText().trim();
        if (FarmaUtility.isInteger(cantIngreso))
            valor = true;
        return valor;
    }

    private boolean validaCantidadMaxima() {
        boolean valor = true;
        int cantIngreso = Integer.parseInt(txtCantidad.getText().trim());
        if (cantIngreso <= cantSug)
            valor = false;
        return valor;
    }


    private void calcularCantMax() {
        cantSug = Integer.parseInt(VariablesInventario.vCantMax_PedRep);
    }

    /**
     * Valida si Ingreso Adicional la cantidad que coloque debe ser la maxima
     * @author dubilluz
     * @since  26.09.2007
     */
    private boolean validaIngresoExistsAdicional() {
        boolean valor = true;
        double canAdicional = FarmaUtility.getDecimalNumber(VariablesInventario.vCantAdicional.trim());
        double canPed = FarmaUtility.getDecimalNumber(txtCantidad.getText().trim());
        log.debug("canAdicional : " + canAdicional);
        log.debug("canPed       : " + canPed);
        if (canAdicional > 0) {
            log.debug("VariablesInventario.vCant_PedRep : " +
                      FarmaUtility.getDecimalNumber(VariablesInventario.vCant_PedRep.trim()));
            log.debug("VariablesInventario.vCantMax_PedRep : " +
                      FarmaUtility.getDecimalNumber(VariablesInventario.vCantMax_PedRep.trim()));
            if (canPed == FarmaUtility.getDecimalNumber(VariablesInventario.vCantMax_PedRep.trim()))
                return false;
            else
                return true;
        }
        return false;
    }

}
