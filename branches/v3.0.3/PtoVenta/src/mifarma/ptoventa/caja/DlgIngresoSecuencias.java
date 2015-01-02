package mifarma.ptoventa.caja;


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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRadioButton;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgIngresoSecuencias extends JDialog {
    // **************************************************************************
    // Constructores
    // **************************************************************************

    private static final Logger log = LoggerFactory.getLogger(DlgIngresoSecuencias.class);

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

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    public DlgIngresoSecuencias() {
        this(null, "", false);
    }

    public DlgIngresoSecuencias(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(441, 290));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Ingreso de Secuencias");
        this.addWindowListener(new WindowAdapter() {
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
        txtSecIni.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtSecIni_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtSecIni_keyTyped(e);
            }
        });
        txtSecFin.setBounds(new Rectangle(200, 105, 130, 20));
        txtSecFin.addKeyListener(new KeyAdapter() {
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
        txtCantCorregir.addKeyListener(new KeyAdapter() {
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
        rbtAscendente.addKeyListener(new KeyAdapter() {
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
        rbtDescendente.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                rbtDescendente_keyPressed(e);
            }
        });
        lblOpciones.setBounds(new Rectangle(20, 215, 105, 20));
        lblOpciones.setText("Opciones");
        lblOpciones.setBackground(Color.white);
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
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

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

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
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

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

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

                    if (UtilityCaja.pValidaComprobantesProcesoSAP(secIni, secFin, this, txtSecIni, myParentFrame)) {

                        estComp = this.verificaComprobantes(secIni, secFin, VariablesCaja.vTipComp);
                        estCorr =
                                this.verificaCorreccion(secIni, secFin, VariablesCaja.vTipComp, txtCantCorregir.getText().trim(),
                                                        indDir);

                        if (estComp == 2) {
                            FarmaUtility.showMessage(this,
                                                     "No es posible corregir los comprobantes seleccionados." + "\nAlguno(s) de los comprobantes a corregirse no existen en el sistema." +
                                                     "\nVerifique por favor!!!", txtSecIni);
                            return;
                        }

                        if (estCorr == 1) {
                            FarmaUtility.showMessage(this,
                                                     "No es posible corregir los comprobantes seleccionados." + "\nLos nuevos comprobantes a generarse ya existen en el sistema." +
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

                            FarmaUtility.showMessage(this, "Usted corregira los comprobantes\n" +
                                    "       " + serie + "-" + txtSecIni.getText().trim() + "  al  " + serie + "-" +
                                    txtSecFin.getText().trim() + "\npor los comprobantes\n" +
                                    "       " + VariablesCaja.vNumComp.substring(0, 3) + "-" +
                                    newSecIni.substring(newSecIni.length() - 7, newSecIni.length()) + "  al  " +
                                    VariablesCaja.vNumComp.substring(0, 3) + "-" +
                                    newSecFin.substring(newSecFin.length() - 7, newSecFin.length()), txtSecIni);
                            if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                            "Esta seguro de corregir los comprobantes?") ==
                                true) {
                                FarmaUtility.showMessage(this,
                                                         "Este proceso podria tomar varios minutos.Espere por Favor!!!",
                                                         txtSecIni);
                                DBCaja.corregirComprobantes(secIni, secFin, txtCantCorregir.getText().trim(), indDir);
                                FarmaUtility.aceptarTransaccion();
                                FarmaUtility.showMessage(this, "La operación se realizó correctamente", txtSecIni);
                                cerrarVentana(true);
                            }
                        }
                    }
                } catch (Exception ex) {
                    FarmaUtility.liberarTransaccion();
                    FarmaUtility.showMessage(this, "Error al corregir comprobantes: \n" +
                            ex.getMessage(), txtSecIni);
                    log.error("", ex);
                    cerrarVentana(false);
                }
        }
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void mostrarDatos() {
        txtSecIni.setText(VariablesCaja.vNumComp.substring(3, 10));
        txtSecFin.setText(VariablesCaja.vNumComp.substring(3, 10));
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

    int verificaCorreccion(String pSecInicial, String pSecFinal, String pTipo, String pCantidad,
                           String pDireccion) throws SQLException {
        return DBCaja.verificaCorrecion(pSecInicial, pSecFinal, pTipo, pCantidad, pDireccion);
    }

    int verificaComprobantes(String pSecInicial, String pSecFinal, String pTipo) throws SQLException {
        return DBCaja.verificaComprobantes(pSecInicial, pSecFinal, pTipo);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnSecuencialInicial_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtSecIni);
    }

    private void rbtAscendente_actionPerformed(ActionEvent e) {
    }


}
