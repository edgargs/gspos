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
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JRadioButton;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.inventario.reference.DBInventario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgIngresoSecuenciasGuias.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      26.06.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */

public class DlgIngresoSecuenciasGuias extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgIngresoSecuenciasGuias.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JLabelWhite lblIngreseCriterioCorreccion = new JLabelWhite();
    private JLabelWhite lblTipDoc_T = new JLabelWhite();
    private JLabelWhite lblTipDoc = new JLabelWhite();
    private JButtonLabel btnSecuencialInicial = new JButtonLabel();
    private JLabelWhite jLabelWhite4 = new JLabelWhite();
    private JLabelWhite lblSerieIni = new JLabelWhite();
    private JLabelWhite lblSerieFin = new JLabelWhite();
    private JTextFieldSanSerif txtSecIni = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtSecFin = new JTextFieldSanSerif();
    private JLabelWhite lblCantCorregir_T = new JLabelWhite();
    private JTextFieldSanSerif txtCantCorregir = new JTextFieldSanSerif();
    private JRadioButton rbtAscendente = new JRadioButton();
    private JRadioButton rbtDescendente = new JRadioButton();
    private JLabelFunction lblOpciones = new JLabelFunction();
    private JLabelFunction lblCorregir = new JLabelFunction();
    private JLabelFunction lblSalir = new JLabelFunction();
    private ButtonGroup btngOrden = new ButtonGroup();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgIngresoSecuenciasGuias() {
        this(null, "", false);
    }

    public DlgIngresoSecuenciasGuias(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(441, 290));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Ingreso de Secuencias");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setLayout(null);
        jPanelTitle1.setBounds(new Rectangle(20, 15, 395, 190));
        jPanelTitle1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        jPanelTitle1.setBackground(Color.white);
        lblIngreseCriterioCorreccion.setText("Ingrese criterio de corrección :");
        lblIngreseCriterioCorreccion.setBounds(new Rectangle(5, 0, 185, 20));
        lblIngreseCriterioCorreccion.setForeground(new Color(255, 130, 14));
        lblTipDoc_T.setText("Tipo de Documento :");
        lblTipDoc_T.setBounds(new Rectangle(30, 35, 130, 20));
        lblTipDoc_T.setForeground(new Color(255, 130, 14));
        lblTipDoc.setText("Boleta");
        lblTipDoc.setBounds(new Rectangle(175, 35, 130, 20));
        lblTipDoc.setForeground(new Color(255, 130, 14));
        btnSecuencialInicial.setText("Secuencial Inicial :");
        btnSecuencialInicial.setBounds(new Rectangle(15, 75, 110, 20));
        btnSecuencialInicial.setForeground(new Color(255, 130, 14));
        btnSecuencialInicial.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        btnSecuencialInicial.setMnemonic('s');
        btnSecuencialInicial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnSecuencialInicial_actionPerformed(e);
            }
        });
        jLabelWhite4.setText("Secuencial Final :");
        jLabelWhite4.setBounds(new Rectangle(15, 105, 105, 20));
        jLabelWhite4.setForeground(new Color(255, 130, 14));
        lblSerieIni.setText("900 -");
        lblSerieIni.setBounds(new Rectangle(135, 75, 50, 20));
        lblSerieIni.setForeground(new Color(255, 130, 14));
        lblSerieFin.setText("900 -");
        lblSerieFin.setBounds(new Rectangle(135, 105, 50, 20));
        lblSerieFin.setForeground(new Color(255, 130, 14));
        txtSecIni.setBounds(new Rectangle(200, 75, 130, 20));
        txtSecIni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtSecIni_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtSecIni_keyTyped(e);
            }
        });
        txtSecFin.setBounds(new Rectangle(200, 105, 130, 20));
        txtSecFin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtSecFin_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtSecFin_keyTyped(e);
            }
        });
        lblCantCorregir_T.setText("Cantidad a corregir :");
        lblCantCorregir_T.setBounds(new Rectangle(15, 135, 120, 20));
        lblCantCorregir_T.setForeground(new Color(255, 130, 14));
        txtCantCorregir.setBounds(new Rectangle(145, 135, 115, 20));
        txtCantCorregir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCantCorregir_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtCantCorregir_keyTyped(e);
            }
        });
        rbtAscendente.setText("Ascendente");
        rbtAscendente.setBounds(new Rectangle(265, 135, 100, 20));
        rbtAscendente.setMnemonic('a');
        rbtAscendente.setBackground(Color.white);
        rbtAscendente.setForeground(new Color(255, 130, 14));
        rbtAscendente.setFont(new Font("SansSerif", 1, 11));
        rbtAscendente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rbtAscendente_actionPerformed(e);
            }
        });
        rbtAscendente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                rbtAscendente_keyPressed(e);
            }
        });
        rbtDescendente.setText("Descendente");
        rbtDescendente.setBounds(new Rectangle(265, 155, 100, 20));
        rbtDescendente.setMnemonic('d');
        rbtDescendente.setBackground(Color.white);
        rbtDescendente.setForeground(new Color(255, 130, 14));
        rbtDescendente.setFont(new Font("SansSerif", 1, 11));
        rbtDescendente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                rbtDescendente_keyPressed(e);
            }
        });
        lblOpciones.setBounds(new Rectangle(20, 215, 105, 20));
        lblOpciones.setText("Opciones");
        lblCorregir.setBounds(new Rectangle(210, 235, 100, 20));
        lblCorregir.setText("[F11] Corregir");
        lblSalir.setBounds(new Rectangle(320, 235, 90, 20));
        lblSalir.setText("[ESC] Cerrar");
        jPanelTitle1.add(rbtDescendente, null);
        jPanelTitle1.add(rbtAscendente, null);
        jPanelTitle1.add(txtCantCorregir, null);
        jPanelTitle1.add(lblCantCorregir_T, null);
        jPanelTitle1.add(txtSecFin, null);
        jPanelTitle1.add(txtSecIni, null);
        jPanelTitle1.add(lblSerieFin, null);
        jPanelTitle1.add(lblSerieIni, null);
        jPanelTitle1.add(jLabelWhite4, null);
        jPanelTitle1.add(lblTipDoc, null);
        jPanelTitle1.add(lblTipDoc_T, null);
        jPanelTitle1.add(lblIngreseCriterioCorreccion, null);
        jPanelTitle1.add(btnSecuencialInicial, null);
        jContentPane.add(lblSalir, null);
        jContentPane.add(lblCorregir, null);
        jContentPane.add(lblOpciones, null);
        jContentPane.add(jPanelTitle1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        btngOrden.add(rbtAscendente);
        btngOrden.add(rbtDescendente);
        this.setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtSecIni);
        mostrarDatos();
    }

    private void txtSecIni_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            txtSecIni.setText(FarmaUtility.caracterIzquierda(txtSecIni.getText().trim(), 7, "0"));
            FarmaUtility.moveFocus(txtSecFin);
        } else {
            chkKeyPressed(e);
        }
    }

    private void txtSecFin_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            txtSecFin.setText(FarmaUtility.caracterIzquierda(txtSecFin.getText().trim(), 7, "0"));
            FarmaUtility.moveFocus(txtCantCorregir);
        } else {
            chkKeyPressed(e);
        }
    }

    private void txtCantCorregir_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(rbtAscendente);
        } else {
            chkKeyPressed(e);
        }
    }

    private void rbtAscendente_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtSecIni);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            FarmaUtility.moveFocus(rbtDescendente);
        } else {
            chkKeyPressed(e);
        }
    }

    private void rbtDescendente_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtSecIni);
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            FarmaUtility.moveFocus(rbtAscendente);
        } else {
            chkKeyPressed(e);
        }
    }

    private void txtSecIni_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtSecIni, e);
    }

    private void txtSecFin_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtSecFin, e);
    }

    private void txtCantCorregir_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCantCorregir, e);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnSecuencialInicial_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtSecIni);
    }

    private void rbtAscendente_actionPerformed(ActionEvent e) {
    }
    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (datosValidados())

                try {
                    String indDir = "";
                    if (rbtAscendente.isSelected()) {
                        indDir = "1";
                    }
                    if (rbtDescendente.isSelected()) {
                        indDir = "2";
                    }
                    int estComp = 0;
                    int estCorr = 0;
                    double nuevaSecIni = 0;
                    double nuevaSecFin = 0;
                    String newSecIni = "";
                    String newSecFin = "";
                    String secIni = "";
                    String secFin = "";
                    String serie = VariablesCaja.vNumComp.substring(0, 3);

                    secIni = lblSerieIni.getText().trim() + txtSecIni.getText().trim();
                    secFin = lblSerieIni.getText().trim() + txtSecFin.getText().trim();

                    estComp = this.verificaComprobantes(secIni, secFin);
                    estCorr = this.verificaCorreccion(secIni, secFin, txtCantCorregir.getText().trim(), indDir);

                    if (estComp == 2) {
                        FarmaUtility.showMessage(this,
                                                 "No es posible corregir las guías seleccionadas." + "\nAlguna(s) de las guía a corregirse no existen en el sistema." +
                                                 "\nVerifique por favor!!!", txtSecIni);
                        return;
                    }

                    if (estCorr == 1) {
                        FarmaUtility.showMessage(this,
                                                 "No es posible corregir las guías seleccionadas." + "\nLas nuevas guías a generarse ya existen en el sistema." +
                                                 "\nVerifique por favor!!!", txtSecIni);
                    } else if (estCorr == 2) {

                        if (indDir.equals("1")) {
                            nuevaSecIni =
                                    Double.parseDouble(serie + secIni) + Double.parseDouble(txtCantCorregir.getText().trim());
                            nuevaSecFin =
                                    Double.parseDouble(serie + secFin) + Double.parseDouble(txtCantCorregir.getText().trim());
                        } else if (indDir.equals("2")) {
                            nuevaSecIni =
                                    Double.parseDouble(serie + secIni) - Double.parseDouble(txtCantCorregir.getText().trim());
                            nuevaSecFin =
                                    Double.parseDouble(serie + secFin) - Double.parseDouble(txtCantCorregir.getText().trim());
                        }
                        newSecIni = FarmaUtility.formatNumber(nuevaSecIni, "0000000000");
                        newSecFin = FarmaUtility.formatNumber(nuevaSecFin, "0000000000");

                        FarmaUtility.showMessage(this, "Usted corregirá las guías\n" +
                                "       " + serie + "-" + txtSecIni.getText().trim() + "  al  " + serie + "-" +
                                txtSecFin.getText().trim() + "\npor las guías\n" +
                                "       " + VariablesCaja.vNumComp.substring(0, 3) + "-" +
                                newSecIni.substring(newSecIni.length() - 7, newSecIni.length()) + "  al  " +
                                VariablesCaja.vNumComp.substring(0, 3) + "-" +
                                newSecFin.substring(newSecFin.length() - 7, newSecFin.length()), txtSecIni);
                        if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                        "Esta seguro de corregir las guías?") ==
                            true) {

                            FarmaUtility.showMessage(this,
                                                     "Este proceso podria tomar varios minutos.Espere por Favor!!!",
                                                     txtSecIni);

                            DBInventario.corregirGuias(secIni, secFin, txtCantCorregir.getText().trim(), indDir);

                            FarmaUtility.aceptarTransaccion();
                            FarmaUtility.showMessage(this, "La operación se realizó correctamente", txtSecIni);
                            cerrarVentana(true);

                        }
                    }
                } catch (SQLException ex) {
                    FarmaUtility.liberarTransaccion();
                    log.error("", ex);
                    FarmaUtility.showMessage(this, "Error al corregir comprobantes: \n" +
                            ex.getMessage(), txtSecIni);

                    cerrarVentana(false);
                }
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

    private void mostrarDatos() {
        txtSecIni.setText(VariablesCaja.vNumComp.substring(3));
        txtSecFin.setText(VariablesCaja.vNumComp.substring(3));
        lblSerieIni.setText(VariablesCaja.vNumComp.substring(0, 3));
        lblSerieFin.setText(VariablesCaja.vNumComp.substring(0, 3));
        lblTipDoc.setText(VariablesCaja.vDesComp);
    }

    private boolean datosValidados() {
        boolean rpta = true;

        if (txtSecIni.getText().trim().length() == 0) {
            rpta = false;
            FarmaUtility.showMessage(this, "Debe ingresar una secuencia inicial válida", txtSecIni);
        }

        else if (txtSecFin.getText().trim().length() == 0) {
            rpta = false;
            FarmaUtility.showMessage(this, "Debe ingresar una secuencia final válida", txtSecFin);
        }

        else if (txtCantCorregir.getText().trim().length() == 0) {
            rpta = false;
            FarmaUtility.showMessage(this, "Debe ingresar una cantidad válida", txtCantCorregir);
        }

        else if (txtCantCorregir.getText().trim().equals("0")) {
            rpta = false;
            FarmaUtility.showMessage(this, "Debe ingresar una cantidad diferente a cero", txtCantCorregir);
        }

        else if (!rbtAscendente.isSelected() && !rbtDescendente.isSelected()) {
            rpta = false;
            FarmaUtility.showMessage(this, "Debe seleccionar una opcion de ordenamiento", rbtAscendente);
        }

        else if (Integer.parseInt(txtSecIni.getText().trim()) > Integer.parseInt(txtSecFin.getText().trim())) {
            rpta = false;
            FarmaUtility.showMessage(this, "La secuencia inicial debe ser menor a la secuencia final", txtSecIni);
        }

        return rpta;
    }

    int verificaCorreccion(String pSecInicial, String pSecFinal, String pCantidad,
                           String pDireccion) throws SQLException {
        return DBInventario.verificaCorrecionGuia(pSecInicial, pSecFinal, pCantidad, pDireccion);
    }

    int verificaComprobantes(String pSecInicial, String pSecFinal) throws SQLException {
        return DBInventario.verificaGuias(pSecInicial, pSecFinal);
    }

}
