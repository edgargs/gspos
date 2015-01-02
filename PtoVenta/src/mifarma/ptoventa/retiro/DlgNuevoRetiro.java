package mifarma.ptoventa.retiro;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

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

import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JDialog;

import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.retiro.reference.DBRetiro;
import mifarma.ptoventa.retiro.reference.VariablesRetiro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2009 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgNuevoRetiro <br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ASOSA 07.12.2009 Creación<br>
 * <br>
 * @author Alfredo Sosa Dordán<br>
 * @version 1.0<br>
 *
 */

public class DlgNuevoRetiro extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgNuevoRetiro.class);

    private JPanelWhite pnlFondo = new JPanelWhite();
    private JPanelHeader pnlgreen = new JPanelHeader();
    private JButtonLabel btnlocal = new JButtonLabel();
    private JTextFieldSanSerif txtcodlocal = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtnomlocal = new JTextFieldSanSerif();
    private JPanelTitle pnlorange = new JPanelTitle();
    private JButtonLabel btnfecha = new JButtonLabel();
    private JTextFieldSanSerif txtfecha = new JTextFieldSanSerif();
    private JButtonLabel btncorreo = new JButtonLabel();
    private JTextFieldSanSerif txtcorreo = new JTextFieldSanSerif();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JButtonLabel btnturno = new JButtonLabel();
    private JButtonLabel btncaja = new JButtonLabel();
    private JTextFieldSanSerif txtnroturno = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtnrocaja = new JTextFieldSanSerif();
    private JLabelWhite jLabelWhite2 = new JLabelWhite();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JLabelFunction jLabelFunction2 = new JLabelFunction();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgNuevoRetiro() {
        this(null, "", false);
    }

    public DlgNuevoRetiro(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
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
        this.setSize(new Dimension(395, 283));
        this.getContentPane().setLayout(null);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                this_keyPressed(e);
            }
        });
        pnlFondo.setBounds(new Rectangle(0, 0, 390, 260));
        pnlFondo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pnlFondo_keyPressed(e);
            }
        });
        pnlgreen.setBounds(new Rectangle(10, 65, 365, 155));
        pnlgreen.setBackground(Color.white);
        pnlgreen.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlgreen.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pnlgreen_keyPressed(e);
            }
        });
        btnlocal.setText("Local : ");
        btnlocal.setBounds(new Rectangle(10, 10, 45, 15));
        btnlocal.setMnemonic('l');
        btnlocal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnlocal_actionPerformed(e);
            }
        });
        btnlocal.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnlocal_keyPressed(e);
            }
        });
        txtcodlocal.setBounds(new Rectangle(50, 10, 35, 20));
        txtcodlocal.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtcodlocal_keyPressed(e);
            }
        });
        txtcodlocal.setDocument(new FarmaLengthText(3));
        txtnomlocal.setBounds(new Rectangle(90, 10, 135, 20));
        txtnomlocal.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtnomlocal_keyPressed(e);
            }
        });
        pnlorange.setBounds(new Rectangle(10, 15, 365, 40));
        pnlorange.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pnlorange_keyPressed(e);
            }
        });
        btnfecha.setText("Fecha : ");
        btnfecha.setBounds(new Rectangle(25, 20, 50, 15));
        btnfecha.setMnemonic('f');
        btnfecha.setForeground(new Color(255, 130, 14));
        btnfecha.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnfecha_actionPerformed(e);
            }
        });
        btnfecha.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnfecha_keyPressed(e);
            }
        });
        txtfecha.setBounds(new Rectangle(75, 15, 95, 20));
        txtfecha.setMinimumSize(new Dimension(10, 10));
        txtfecha.setMaximumSize(new Dimension(10, 10));
        txtfecha.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtfecha_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtfecha_keyReleased(e);
            }
        });
        txtfecha.setDocument(new FarmaLengthText(10));
        btncorreo.setText("Correo : ");
        btncorreo.setBounds(new Rectangle(20, 55, 60, 15));
        btncorreo.setMnemonic('c');
        btncorreo.setForeground(new Color(255, 130, 14));
        btncorreo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btncorreo_actionPerformed(e);
            }
        });
        btncorreo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btncorreo_keyPressed(e);
            }
        });
        txtcorreo.setBounds(new Rectangle(75, 50, 110, 20));
        txtcorreo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtcorreo_keyPressed(e);
            }
        });
        jLabelWhite1.setText("@quimicasuiza.com");
        jLabelWhite1.setBounds(new Rectangle(190, 55, 115, 15));
        jLabelWhite1.setForeground(new Color(255, 130, 14));
        jLabelWhite1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jLabelWhite1_keyPressed(e);
            }
        });
        btnturno.setText("Turno : ");
        btnturno.setBounds(new Rectangle(25, 90, 50, 15));
        btnturno.setMnemonic('t');
        btnturno.setForeground(new Color(255, 130, 14));
        btnturno.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnturno_actionPerformed(e);
            }
        });
        btnturno.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnturno_keyPressed(e);
            }
        });
        btncaja.setText("Caja : ");
        btncaja.setBounds(new Rectangle(35, 120, 35, 15));
        btncaja.setMnemonic('a');
        btncaja.setForeground(new Color(255, 130, 14));
        btncaja.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btncaja_actionPerformed(e);
            }
        });
        btncaja.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btncaja_keyPressed(e);
            }
        });
        txtnroturno.setBounds(new Rectangle(75, 85, 40, 20));
        txtnroturno.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtnroturno_keyPressed(e);
            }
        });
        txtnrocaja.setBounds(new Rectangle(75, 115, 40, 20));
        txtnrocaja.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtnrocaja_keyPressed(e);
            }
        });
        jLabelWhite2.setText("dd/mm/yyyy");
        jLabelWhite2.setBounds(new Rectangle(175, 20, 70, 15));
        jLabelWhite2.setForeground(new Color(255, 130, 14));
        jLabelWhite2.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jLabelWhite2_keyPressed(e);
            }
        });
        jLabelFunction1.setBounds(new Rectangle(205, 230, 100, 20));
        jLabelFunction1.setText("[ ESC ] SALIR");
        jLabelFunction1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jLabelFunction1_keyPressed(e);
            }
        });
        jLabelFunction2.setBounds(new Rectangle(70, 230, 117, 19));
        jLabelFunction2.setText("[ F11 ] ACEPTAR");
        jLabelFunction2.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jLabelFunction2_keyPressed(e);
            }
        });
        pnlorange.add(txtcodlocal, null);
        pnlorange.add(txtnomlocal, null);
        pnlorange.add(btnlocal, null);
        pnlFondo.add(jLabelFunction2, null);
        pnlFondo.add(pnlorange, null);
        pnlgreen.add(jLabelWhite2, null);
        pnlgreen.add(txtnrocaja, null);
        pnlgreen.add(txtnroturno, null);
        pnlgreen.add(btncaja, null);
        pnlgreen.add(btnturno, null);
        pnlgreen.add(jLabelWhite1, null);
        pnlgreen.add(txtcorreo, null);
        pnlgreen.add(btncorreo, null);
        pnlgreen.add(txtfecha, null);
        pnlgreen.add(btnfecha, null);
        pnlFondo.add(pnlgreen, null);
        pnlFondo.add(jLabelFunction1, null);
        this.getContentPane().add(pnlFondo, null);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        ocultarEtiquetas();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    public void ocultarEtiquetas() {
        if (!VariablesRetiro.TIP_CIERRE.equalsIgnoreCase("T")) {
            btnturno.setVisible(false);
            txtnroturno.setVisible(false);
            btncaja.setVisible(false);
            txtnrocaja.setVisible(false);

        }
        txtnomlocal.setEnabled(false);
    }


    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(txtcodlocal);
        FarmaUtility.centrarVentana(this);
    }

    private void txtcodlocal_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            String codloc = txtcodlocal.getText().trim();
            if (!codloc.equals("")) {
                if (validarCodigo(codloc)) {
                    buscarLocal();
                } else {
                    txtcodlocal.setText("");
                    FarmaUtility.showMessage(this, "Ingrese solo numeros en el codigo de local", txtcodlocal);
                }
            } else {
                FarmaUtility.showMessage(this, "Ingrese código de local", txtcodlocal);
            }
        } else
            chkKeyPressed(e);
    }

    private void btnlocal_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtcodlocal);
    }

    private void btnfecha_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtfecha);
    }

    private void btncorreo_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtcorreo);
    }

    private void btnturno_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtnroturno);
    }

    private void btncaja_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtnrocaja);
    }

    private void btnlocal_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtfecha_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtcorreo);
        } else
            chkKeyPressed(e);
    }

    private void txtcorreo_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtnroturno);
        } else
            chkKeyPressed(e);
    }

    private void txtnroturno_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtnrocaja);
        } else
            chkKeyPressed(e);
    }

    private void txtnrocaja_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnaceptar_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnsalir_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtfecha_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtfecha, e);
    }

    private void jLabelFunction2_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void jLabelFunction1_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void pnlFondo_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void pnlorange_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void pnlgreen_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void jLabelWhite2_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void jLabelWhite1_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnfecha_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btncorreo_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnturno_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btncaja_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtnomlocal_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (validaCampos()) {
                if (validarNumero(txtnroturno.getText())) {
                    if (validarNumero(txtnrocaja.getText())) {
                        if (validarRetiro()) {
                            insertRequerRetiroVB();
                        } else {
                            FarmaUtility.showMessage(this, "Fecha incorrecta, no existe VB para la misma", txtfecha);
                        }
                    } else {
                        txtnrocaja.setText("");
                        FarmaUtility.showMessage(this, "Ingrese un nro de caja correcto", txtnrocaja);
                    }
                } else {
                    txtnroturno.setText("");
                    FarmaUtility.showMessage(this, "Ingrese un nro de turno correcto", txtnroturno);
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

    public boolean validarRetiro() {
        boolean flag = true;
        if (!VariablesRetiro.TIP_CIERRE.trim().equalsIgnoreCase("T")) {
            VariablesRetiro.COD_LOCAL = txtcodlocal.getText().trim();
            VariablesRetiro.FEC_INI = txtfecha.getText().trim();
            String ind = "";
            try {
                ind = DBRetiro.validarRetiroDC();
            } catch (SQLException e) {
                FarmaUtility.showMessage(this, "ERROR en Validar: \n" +
                        e.getMessage(), null);
                log.error("", e);
            }
            if (ind.trim().equalsIgnoreCase("SI")) {
                flag = false;
            }
        }
        return flag;
    }

    public boolean validaCampos() {
        boolean flag = true;
        if (txtnomlocal.getText().trim().equals("")) {
            flag = false;
            FarmaUtility.showMessage(this, "Ingrese codigo de local", txtcodlocal);
        } else if (txtfecha.getText().equals("")) {
            flag = false;
            FarmaUtility.showMessage(this, "Ingrese fecha", txtfecha);
        } else if (!FarmaUtility.validaFecha(txtfecha.getText(), "dd/MM/yyyy")) {
            flag = false;
            txtfecha.setText("");
            FarmaUtility.showMessage(this, "Ingrese fecha inicial correctamente", txtfecha);
        } else if (txtcorreo.getText().trim().equals("")) {
            flag = false;
            FarmaUtility.showMessage(this, "Ingrese correo electronico", txtcorreo);
        } else if (VariablesRetiro.TIP_CIERRE.equalsIgnoreCase("T")) {
            if (txtnroturno.getText().trim().equals("")) {
                flag = false;
                FarmaUtility.showMessage(this, "Ingrese nro de Turno", txtnroturno);
            } else if (txtnrocaja.getText().trim().equals("")) {
                flag = false;
                FarmaUtility.showMessage(this, "Ingrese nro de Caja", txtnrocaja);
            }
        }
        return flag;
    }

    public void buscarLocal() {
        VariablesRetiro.COD_LOCAL = txtcodlocal.getText();
        String nombrelocal = "";
        try {
            nombrelocal = DBRetiro.obtenerNombreLocal();
            //FarmaUtility.aceptarTransaccion();
        } catch (SQLException e) {
            //FarmaUtility.liberarTransaccion();
            log.error("", e);
            nombrelocal = "";
        }
        if (nombrelocal.trim() == "") {
            txtcodlocal.setText("");
            FarmaUtility.showMessage(this, "No existe local con ese codigo", txtcodlocal);
        } else {
            txtnomlocal.setText(nombrelocal);
            FarmaUtility.moveFocus(txtfecha);
        }
    }

    public void insertRequerRetiroVB() {
        String[] datos = txtfecha.getText().split("/");
        VariablesRetiro.DIA = datos[0];
        VariablesRetiro.MES = datos[1];
        VariablesRetiro.ANIO = datos[2];
        VariablesRetiro.CORREO = txtcorreo.getText();
        if (VariablesRetiro.TIP_CIERRE.equalsIgnoreCase("T")) {
            VariablesRetiro.COD_TUR = txtnroturno.getText();
            VariablesRetiro.COD_CAJA = txtnrocaja.getText();
        }
        try {
            DBRetiro.insertarNuevoRetiro();
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this, "Operación exitosa", txtcodlocal);
            cerrarVentana(true);
        } catch (SQLException e) {
            FarmaUtility.liberarTransaccion();
            log.error("", e);
            log.info("Error al grabar:" + e.getMessage());
            FarmaUtility.showMessage(this, "Error al registrar.\n" +
                    e.getMessage(), txtcodlocal);
        }
    }

    private boolean validarNumero(String num) {
        boolean flag = true;
        if (VariablesRetiro.TIP_CIERRE.equalsIgnoreCase("T")) {
            if (!(FarmaUtility.isInteger(num) && Integer.parseInt(num) > 0))
                flag = false;
        }
        return flag;
    }

    private boolean validarCodigo(String num) {
        boolean flag = true;
        if (!(FarmaUtility.isInteger(num) && Integer.parseInt(num) > 0))
            flag = false;
        return flag;
    }

}
