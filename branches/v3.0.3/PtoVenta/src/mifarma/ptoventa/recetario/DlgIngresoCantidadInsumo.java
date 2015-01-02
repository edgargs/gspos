package mifarma.ptoventa.recetario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.recetario.reference.FacadeRecetario;
import mifarma.ptoventa.recetario.reference.UtilityRecetario;
import mifarma.ptoventa.recetario.reference.VariablesRecetario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgIngresoCantidadInsumo.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      12.04.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgIngresoCantidadInsumo extends JDialog {

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgIngresoCantidadInsumo.class);
    private FacadeRecetario facadeRecetario;

    private Frame myParentFrame;
    private boolean productoFraccionado = false;

    private String codInsumo = "";

    private String descUnidPres = "";
    private String stkProd = "";
    private String valFracProd = "";
    private String descUnidVta = "";
    private String codUnidOrig = "";

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnlHeader1 = new JPanelHeader();
    private JLabelWhite lblDescripcion_T = new JLabelWhite();
    private JLabelWhite lblDescripcion = new JLabelWhite();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel btnEntero = new JButtonLabel();
    private JTextFieldSanSerif txtEntero = new JTextFieldSanSerif();
    private JLabelWhite lblPrecioUnitario_T = new JLabelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelOrange lblUnidVenta_T = new JLabelOrange();
    private JLabelOrange lblUnidVenta = new JLabelOrange();
    private JLabelOrange lblPrecio = new JLabelOrange();
    private JLabelOrange lbl_UniMedidaVenta = new JLabelOrange();
    private JComboBox<String> cmb_UniMedidaVenta = new JComboBox<String>();
    private JLabelOrange lbl_porcentaje = new JLabelOrange();
    private JTextFieldSanSerif txt_porcentaje = new JTextFieldSanSerif();
    private JLabel lbl_simboloPorc = new JLabel();

    private ArrayList<ArrayList<String>> unidRelacionadas;

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgIngresoCantidadInsumo() {
        this(null, "", false);
    }

    public DlgIngresoCantidadInsumo(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            FarmaUtility.centrarVentana(this);
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(428, 314));
        this.getContentPane().setLayout(null);
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Ingrese Cantidad Solicitada");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlHeader1.setBounds(new Rectangle(10, 10, 400, 125));
        pnlHeader1.setBackground(Color.white);
        pnlHeader1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        lblDescripcion_T.setText("Descripción:");
        lblDescripcion_T.setBounds(new Rectangle(10, 15, 75, 15));
        lblDescripcion_T.setForeground(new Color(255, 130, 14));
        lblDescripcion.setBounds(new Rectangle(90, 15, 280, 15));
        lblDescripcion.setFont(new Font("SansSerif", 0, 11));
        lblDescripcion.setForeground(new Color(255, 130, 14));
        pnlTitle1.setBounds(new Rectangle(10, 150, 400, 85));
        pnlTitle1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlTitle1.setBackground(Color.white);
        btnEntero.setText("Cantidad:");
        btnEntero.setBounds(new Rectangle(15, 20, 60, 15));
        btnEntero.setMnemonic('E');
        btnEntero.setForeground(new Color(255, 130, 14));
        btnEntero.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnEntero_actionPerformed(e);
            }
        });
        txtEntero.setBounds(new Rectangle(15, 40, 60, 20));
        txtEntero.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtEntero_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtEntero_keyTyped(e);
            }
        });
        lblPrecioUnitario_T.setText("Precio Unit. (S/.)");
        lblPrecioUnitario_T.setBounds(new Rectangle(10, 85, 95, 15));
        lblPrecioUnitario_T.setForeground(new Color(255, 130, 14));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(325, 250, 85, 20));
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(215, 250, 105, 20));
        lblUnidVenta_T.setText("Unid. Medida:");
        lblUnidVenta_T.setBounds(new Rectangle(10, 50, 79, 15));
        lblUnidVenta.setBounds(new Rectangle(95, 50, 200, 15));
        lblUnidVenta.setFont(new Font("SansSerif", 0, 11));
        lblPrecio.setFont(new Font("SansSerif", 0, 11));
        lblPrecio.setBounds(new Rectangle(95, 85, 200, 15));
        lbl_UniMedidaVenta.setText("Unid.Medida Venta:");
        lbl_UniMedidaVenta.setBounds(new Rectangle(130, 20, 125, 15));
        cmb_UniMedidaVenta.setBounds(new Rectangle(130, 40, 125, 20));
        cmb_UniMedidaVenta.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmb_UniMedidaVenta_keyPressed(e);
            }
        });
        cmb_UniMedidaVenta.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                cmb_UniMedidaVenta_itemStateChanged(e);
            }
        });
        lbl_porcentaje.setText("Porcentaje");
        lbl_porcentaje.setBounds(new Rectangle(300, 20, 80, 15));
        lbl_porcentaje.setEnabled(false);
        txt_porcentaje.setBounds(new Rectangle(300, 40, 40, 20));
        txt_porcentaje.setEnabled(false);
        //txt_porcentaje.setLengthText(2);
        txt_porcentaje.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txt_porcentaje_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txt_porcentaje_keyTyped(e);
            }
        });
        txt_porcentaje.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                txt_porcentaje_focusLost(e);
            }
        });
        lbl_simboloPorc.setText("%");
        lbl_simboloPorc.setBounds(new Rectangle(350, 40, 25, 20));
        lbl_simboloPorc.setEnabled(false);
        pnlHeader1.add(lblPrecio, null);
        pnlHeader1.add(lblUnidVenta, null);
        pnlHeader1.add(lblUnidVenta_T, null);
        pnlHeader1.add(lblDescripcion, null);
        pnlHeader1.add(lblDescripcion_T, null);
        pnlHeader1.add(lblPrecioUnitario_T, null);
        pnlTitle1.add(lbl_simboloPorc, null);
        pnlTitle1.add(txt_porcentaje, null);
        pnlTitle1.add(lbl_porcentaje, null);
        pnlTitle1.add(cmb_UniMedidaVenta, null);
        pnlTitle1.add(lbl_UniMedidaVenta, null);
        pnlTitle1.add(txtEntero, null);
        pnlTitle1.add(btnEntero, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(pnlHeader1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);

        txtEntero.setLengthText(6);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initCabecera() {

    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void btnEntero_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtEntero);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtEntero);
    }

    private void txtEntero_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtEntero, e);
    }

    private void txtEntero_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            txtEntero.transferFocus();
            e.consume();
        } else
            chkKeyPressed(e);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        txt_porcentaje.setText(txt_porcentaje.getText().trim());
        if (e.getKeyCode() == KeyEvent.VK_ENTER) { //cerrarVentana(true);
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            cerrarVentana(true);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        if (pAceptar) {
            if (!txtEntero.getText().trim().equals("") && UtilityRecetario.validarNDecimales(txtEntero.getText(), 3) &&
                FarmaUtility.getDecimalNumber(txtEntero.getText().trim()) > 0) {
                Double cantInsumos = FarmaUtility.getDecimalNumber(txtEntero.getText().trim());
                Double factorConv =
                    facadeRecetario.getFactorConversion(this.codInsumo, this.getCodUnidMedidaElegida());
                Double porc = 1.0;
                boolean flagPorc = false;

                //si el porcentaje esta activo validar su contenido
                if (txt_porcentaje.isEnabled()) {
                    if (UtilityRecetario.validarNDecimales(this.getPorcentaje(), 3)) {
                        if (this.getPorcentaje() == null || this.getPorcentaje().equals("") ||
                            this.getPorcentaje().equals("0")) {
                            porc = 1.0;
                            flagPorc = true;
                        } else {
                            porc = FarmaUtility.getDecimalNumber(this.getPorcentaje()) / 100;
                            if (porc > 0 && porc <= 100)
                                flagPorc = true;
                            else
                                flagPorc = false;
                        }
                    }
                    if (!flagPorc)
                        FarmaUtility.showMessage(this,
                                                 "El porcentaje solo puede tomar un valor entre 0 y 100% con tres decimales.",
                                                 null);
                } else
                    flagPorc = true;
                if (flagPorc) {
                    VariablesRecetario.vCantInsumosCodSelec = Double.toString(cantInsumos * factorConv * porc);
                    this.setVisible(false);
                    this.dispose();
                    FarmaVariables.vAceptar = pAceptar;
                }
            } else {
                FarmaUtility.showMessage(this,
                                         "La cantidad de productos solo puede tomar un valor con tres decimales.",
                                         null);
            }
        } else {
            VariablesRecetario.vCantInsumosCodSelec = "";
            this.setVisible(false);
            this.dispose();
            FarmaVariables.vAceptar = pAceptar;
        }
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    public void setCodInsumo(String codInsumo) {
        this.codInsumo = codInsumo;

        ArrayList<ArrayList<String>> tmpArrayInf = null;

        tmpArrayInf = facadeRecetario.obtenerInformacionInsumo(codInsumo);
        if (tmpArrayInf.size() > 0) {
            lblDescripcion.setText(((ArrayList)tmpArrayInf.get(0)).get(0).toString().trim());
            lblUnidVenta.setText(((ArrayList)tmpArrayInf.get(0)).get(1).toString().trim());
            lblPrecio.setText(((ArrayList)tmpArrayInf.get(0)).get(2).toString().trim());
            codUnidOrig = ((ArrayList)tmpArrayInf.get(0)).get(3).toString().trim();

            //se llena las unidades de medida relacionadas
            this.unidRelacionadas = facadeRecetario.listadoUnidadesRelacionadas(codUnidOrig);
            if (this.unidRelacionadas != null) {
                boolean flagPorcGR = false;
                ArrayList<String> tmpArrayPorcGR = null;
                boolean flagPorcML = false;
                ArrayList<String> tmpArrayPorcML = null;

                for (int i = 0; i < this.unidRelacionadas.size(); i++) {
                    String temp = ((ArrayList)this.unidRelacionadas.get(i)).get(1).toString().trim();
                    cmb_UniMedidaVenta.addItem(temp);
                    if (temp.equals("GRAMO") && flagPorcGR == false) {
                        flagPorcGR = true;
                        tmpArrayPorcGR = this.unidRelacionadas.get(i);
                    }
                    if (temp.equals("MILILITRO") && flagPorcML == false) {
                        flagPorcML = true;
                        tmpArrayPorcML = this.unidRelacionadas.get(i);
                    }
                }
                if (flagPorcGR) {
                    cmb_UniMedidaVenta.addItem("%GR");
                    this.unidRelacionadas.add(tmpArrayPorcGR);
                }
                if (flagPorcML) {
                    cmb_UniMedidaVenta.addItem("%ML");
                    this.unidRelacionadas.add(tmpArrayPorcML);
                }
            } else {
                cmb_UniMedidaVenta.addItem(lblUnidVenta.getText());
            }

            cmb_UniMedidaVenta.setSelectedItem(lblUnidVenta.getText());
            txt_porcentaje.setText("");
            verificarEstadoPorc();
        } else {
            FarmaUtility.showMessage(this, "No se pudo mostrar informacion del insumo.", null);
        }
    }

    public void setFacade(FacadeRecetario facadeRecetario) {
        this.facadeRecetario = facadeRecetario;
    }

    private void cmb_UniMedidaVenta_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_porcentaje.isEnabled())
                txt_porcentaje.grabFocus();
            else
                txtEntero.grabFocus();
        } else
            chkKeyPressed(e);
    }

    private void cmb_UniMedidaVenta_itemStateChanged(ItemEvent e) {
        verificarEstadoPorc();
    }

    private void verificarEstadoPorc() { //cambiar esto despues por el indicador de porcentaje
        if (cmb_UniMedidaVenta.getSelectedItem().toString().equals("%GR") ||
            cmb_UniMedidaVenta.getSelectedItem().toString().equals("%ML")) {
            lbl_porcentaje.setEnabled(true);
            txt_porcentaje.setEnabled(true);
            lbl_simboloPorc.setEnabled(true);
            lbl_porcentaje.setFocusable(true);
            txt_porcentaje.setFocusable(true);
            lbl_simboloPorc.setFocusable(true);
        } else {
            lbl_porcentaje.setEnabled(false);
            txt_porcentaje.setEnabled(false);
            lbl_simboloPorc.setEnabled(false);
            lbl_porcentaje.setFocusable(false);
            txt_porcentaje.setFocusable(false);
            lbl_simboloPorc.setFocusable(false);
            txt_porcentaje.setText("");
        }
    }

    public String getCodUnidMedidaElegida() {
        ArrayList<String> temp = this.unidRelacionadas.get(cmb_UniMedidaVenta.getSelectedIndex());
        return temp.get(0).trim();
    }

    public String getDescUnidMedidaElegida() { //ArrayList<String> temp = this.unidRelacionadas.get(cmb_UniMedidaVenta.getSelectedIndex());
        //return temp.get(1).trim();
        return cmb_UniMedidaVenta.getSelectedItem().toString();
    }

    public String getDescUnidMedidaOrig() {
        return lblUnidVenta.getText();
    }

    public String getPorcentaje() {
        return txt_porcentaje.getText();
    }

    public String getCantidadIngresada() {
        return txtEntero.getText();
    }

    private void txt_porcentaje_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_porcentaje.transferFocus();
            e.consume();
        } else
            chkKeyPressed(e);
    }

    private void txt_porcentaje_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txt_porcentaje, e);
    }

    public void cargarDatosInsumo(String cant, String unidMedida, String porc) {
        txtEntero.setText(cant);
        cmb_UniMedidaVenta.setSelectedItem(unidMedida);
        txt_porcentaje.setText(porc);
        verificarEstadoPorc();
    }

    private void txt_porcentaje_focusLost(FocusEvent e) {
        txtEntero.grabFocus();
    }
}
