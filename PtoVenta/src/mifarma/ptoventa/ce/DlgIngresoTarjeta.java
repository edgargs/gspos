package mifarma.ptoventa.ce;


//import com.jgoodies.forms.layout.FormLayout;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
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

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;

import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.ce.reference.DBCajaElectronica;
import mifarma.ptoventa.ce.reference.VariablesCajaElectronica;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2010 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgTarjeCred.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCORTEZ      29.01.2010   Creación<br>
 * <br>
 * @AUTHOR JORGE CORTEZ ALVAREZ<br>
 * @version 1.0<br>
 *
 */
public class DlgIngresoTarjeta extends JDialog {
    //Variables para el pago con tarjeta
    private static final Logger log = LoggerFactory.getLogger(DlgIngresoTarjeta.class);

    private String bin = "";
    private String desc_prod = "";
    private String codfp = "";
    private String descfp = "";
    private String nroTarjeta = "";

    private Frame myParentFrame;
    private JPanelWhite pnlFondo = new JPanelWhite();
    private JPanel pnlInfo = new JPanel();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JLabelOrange jLabelOrange5 = new JLabelOrange();
    private JTextFieldSanSerif txtnrotarj = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtdni = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtcodvou = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtlote = new JTextFieldSanSerif();
    private JLabelOrange jLabelOrange6 = new JLabelOrange();
    private JLabelOrange lbltip = new JLabelOrange();
    private JLabelOrange lblmon = new JLabelOrange();
    private JButtonLabel jButtonLabel1 = new JButtonLabel();
    private JButtonLabel jButtonLabel2 = new JButtonLabel();
    private JButtonLabel jButtonLabel3 = new JButtonLabel();
    private JButtonLabel jButtonLabel4 = new JButtonLabel();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JLabelFunction jLabelFunction2 = new JLabelFunction();

    public DlgIngresoTarjeta() {
        this(null, "", false);
    }

    public DlgIngresoTarjeta(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        this.myParentFrame = parent;
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(388, 254));
        this.getContentPane().setLayout(null);
        this.setTitle("Ingreso Tarjeta");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        pnlFondo.setBounds(new Rectangle(0, 0, 385, 230));
        pnlFondo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pnlFondo_keyPressed(e);
            }
        });
        pnlInfo.setBounds(new Rectangle(5, 25, 370, 170));
        pnlInfo.setBackground(Color.white);
        pnlInfo.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlInfo.setLayout(null);
        pnlInfo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pnlInfo_keyPressed(e);
            }
        });
        pnlTitle.setBounds(new Rectangle(5, 5, 370, 20));
        pnlTitle.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pnlTitle_keyPressed(e);
            }
        });
        jLabelWhite1.setText("Datos Tarjeta :");
        jLabelWhite1.setBounds(new Rectangle(5, 0, 100, 20));
        jLabelWhite1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jLabelWhite1_keyPressed(e);
            }
        });
        jLabelOrange5.setText("Monto : S/.");
        jLabelOrange5.setBounds(new Rectangle(15, 140, 79, 15));
        jLabelOrange5.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jLabelOrange5_keyPressed(e);
            }
        });
        txtnrotarj.setBounds(new Rectangle(115, 10, 135, 20));
        txtnrotarj.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtnrotarj_keyTyped(e);
            }

            public void keyPressed(KeyEvent e) {
                txtnrotarj_keyPressed(e);
            }
        });
        txtnrotarj.setDocument(new FarmaLengthText(20));
        txtdni.setBounds(new Rectangle(115, 60, 95, 20));
        txtdni.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtdni_keyTyped(e);
            }

            public void keyPressed(KeyEvent e) {
                txtdni_keyPressed(e);
            }
        });
        txtdni.setDocument(new FarmaLengthText(8));
        txtcodvou.setBounds(new Rectangle(115, 85, 135, 20));
        txtcodvou.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtcodvou_keyTyped(e);
            }

            public void keyPressed(KeyEvent e) {
                txtcodvou_keyPressed(e);
            }
        });
        txtcodvou.setDocument(new FarmaLengthText(20));
        txtlote.setBounds(new Rectangle(115, 110, 135, 20));
        txtlote.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtlote_keyTyped(e);
            }

            public void keyPressed(KeyEvent e) {
                txtlote_keyPressed(e);
            }
        });
        txtlote.setDocument(new FarmaLengthText(3));
        jLabelOrange6.setText("Tipo Tarjeta :");
        jLabelOrange6.setBounds(new Rectangle(15, 40, 80, 15));
        jLabelOrange6.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jLabelOrange6_keyPressed(e);
            }
        });
        lbltip.setText("[TIPO]");
        lbltip.setBounds(new Rectangle(115, 35, 245, 15));
        lbltip.setForeground(new Color(43, 141, 39));
        lbltip.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                lbltip_keyPressed(e);
            }
        });
        lblmon.setText("[MONTO]");
        lblmon.setBounds(new Rectangle(115, 140, 55, 15));
        lblmon.setForeground(new Color(49, 141, 43));
        lblmon.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                lblmon_keyPressed(e);
            }
        });
        jButtonLabel1.setText("Nro. Tarjeta :");
        jButtonLabel1.setBounds(new Rectangle(15, 15, 75, 15));
        jButtonLabel1.setForeground(new Color(255, 130, 14));
        jButtonLabel1.setMnemonic('n');
        jButtonLabel1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jButtonLabel1_keyPressed(e);
            }
        });
        jButtonLabel1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonLabel1_actionPerformed(e);
            }
        });
        jButtonLabel2.setText("DNI :");
        jButtonLabel2.setBounds(new Rectangle(15, 65, 35, 15));
        jButtonLabel2.setForeground(new Color(255, 130, 14));
        jButtonLabel2.setMnemonic('d');
        jButtonLabel2.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jButtonLabel2_keyPressed(e);
            }
        });
        jButtonLabel2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonLabel2_actionPerformed(e);
            }
        });
        jButtonLabel3.setText("Código Voucher :");
        jButtonLabel3.setBounds(new Rectangle(15, 90, 95, 15));
        jButtonLabel3.setForeground(new Color(255, 130, 14));
        jButtonLabel3.setMnemonic('c');
        jButtonLabel3.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jButtonLabel3_keyPressed(e);
            }
        });
        jButtonLabel3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonLabel3_actionPerformed(e);
            }
        });
        jButtonLabel4.setText("Lote :");
        jButtonLabel4.setBounds(new Rectangle(15, 115, 40, 15));
        jButtonLabel4.setForeground(new Color(255, 130, 14));
        jButtonLabel4.setMnemonic('l');
        jButtonLabel4.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jButtonLabel4_keyPressed(e);
            }
        });
        jButtonLabel4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonLabel4_actionPerformed(e);
            }
        });
        jLabelFunction1.setBounds(new Rectangle(275, 200, 100, 20));
        jLabelFunction1.setText("[ Esc ] Cerrar");
        jLabelFunction2.setBounds(new Rectangle(165, 200, 105, 20));
        jLabelFunction2.setText("[ F11 ] Aceptar");
        pnlTitle.add(jLabelWhite1, null);
        pnlFondo.add(jLabelFunction2, null);
        pnlFondo.add(jLabelFunction1, null);
        pnlFondo.add(pnlTitle, null);
        pnlInfo.add(jButtonLabel4, null);
        pnlInfo.add(jButtonLabel3, null);
        pnlInfo.add(jButtonLabel2, null);
        pnlInfo.add(jButtonLabel1, null);
        pnlInfo.add(lblmon, null);
        pnlInfo.add(lbltip, null);
        pnlInfo.add(jLabelOrange6, null);
        pnlInfo.add(txtlote, null);
        pnlInfo.add(txtcodvou, null);
        pnlInfo.add(txtdni, null);
        pnlInfo.add(txtnrotarj, null);
        pnlInfo.add(jLabelOrange5, null);
        pnlFondo.add(pnlInfo, null);
        this.getContentPane().add(pnlFondo, null);
    }

    private void initialize() {

    }

    private void inicializarVariables() {
        bin = "";
        desc_prod = "";
        codfp = "";
        descfp = "";
        nroTarjeta = "";
        txtnrotarj.setText("");
        txtdni.setText("");
        txtcodvou.setText("");
        txtlote.setText("");
        lbltip.setText("");
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtnrotarj);
        lblmon.setText(VariablesCajaElectronica.vMontPago);
        inicializarVariables();
    }

    private void chkkeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (validaCampos()) {
                if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                "Está seguro de cambiar la forma de pago?")) {
                    //if(VariablesCajaElectronica.vIndTotalCubierto){
                    procesarFormaPago(); //crea backup y nueva forma de pago;
                    cerrarVentana(true);
                    /*}else
                  FarmaUtility.showMessage(this, "El monto aún no esta completo.", tblFormaPago);
                }*/
                }
            }
        }

    }

    private void txtnrotarj_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtnrotarj, e);
    }

    private void txtdni_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtdni, e);
    }

    private void txtcodvou_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtcodvou, e);
    }

    private void txtlote_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtlote, e);
    }

    private void jLabelOrange6_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void jLabelOrange5_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void txtnrotarj_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (validarCamposSimilares(txtnrotarj.getText(), txtnrotarj, txtdni, "Nro. de Tarjeta")) {
                if (buscarInfotarjeta()) {
                    lbltip.setText(desc_prod + "/" + descfp);
                } else {
                    inicializarVariables();
                }
            }
        } else
            chkkeyPressed(e);
    }

    private boolean buscarInfotarjeta() {
        boolean flag = true;
        ArrayList lista = new ArrayList();
        try {
            DBCajaElectronica.obtenerInfoTarjeta(lista, txtnrotarj.getText().trim(),
                                                 ConstantsCaja.TIPO_ORIGEN_PAGO_POS);
        } catch (SQLException e) {
            log.debug("ERROR en buscarInfotarjeta: " + e.getMessage());
            log.error("", e);
        }
        if (lista.size() > 0) {
            ArrayList reg = (ArrayList)lista.get(0);
            bin = (String)reg.get(0);
            desc_prod = (String)reg.get(1);
            codfp = (String)reg.get(2);
            descfp = (String)reg.get(3);
            nroTarjeta = txtnrotarj.getText();
            log.debug("bin: " + bin);
            log.debug("desc_prod: " + desc_prod);
            log.debug("codfp: " + codfp);
            log.debug("descfp: " + descfp);
        } else {
            bin = "";
            desc_prod = "";
            codfp = "";
            descfp = "";
            nroTarjeta = "";
            flag = false;
            FarmaUtility.showMessage(this, "Tipo de tarjeta desconocido", txtnrotarj);
        }
        return flag;
    }

    private void lbltip_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void txtdni_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtdni.getText().trim().equalsIgnoreCase("")) {
                FarmaUtility.moveFocus(txtcodvou);
            } else {
                if (FarmaUtility.isDouble(txtdni.getText())) {
                    if (Double.parseDouble(txtdni.getText().trim()) != 0 && txtdni.getText().trim().length() == 8) {
                        FarmaUtility.moveFocus(txtcodvou);
                    } else {
                        FarmaUtility.showMessage(this, "DNI incorrecto", txtdni);
                        txtdni.setText("");
                    }
                } else {
                    FarmaUtility.showMessage(this, "Si va a ingresar DNI debe ser valido", txtdni);
                    txtdni.setText("");
                }
            }
        } else
            chkkeyPressed(e);
    }

    private void txtcodvou_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            validarCamposSimilares(txtcodvou.getText(), txtcodvou, txtlote, "Cod. Voucher");
        } else
            chkkeyPressed(e);
    }

    private boolean validarCamposSimilares(String contenido, Object obj01, Object obj, String msg) {
        boolean flag = true;
        if (!contenido.trim().equalsIgnoreCase("")) {
            if (Double.parseDouble(contenido.trim()) > 0) {
                if (FarmaUtility.isDouble(contenido)) {
                    FarmaUtility.moveFocus(obj);
                } else {
                    flag = false;
                    FarmaUtility.showMessage(this, "Ingrese " + msg + " valido", obj01);
                }
            } else {
                flag = false;
                FarmaUtility.showMessage(this, "Ingrese " + msg + " valido", obj01);
            }
        } else {
            flag = false;
            FarmaUtility.showMessage(this, "Ingrese " + msg, obj01);
        }
        return flag;
    }

    private void txtlote_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!txtlote.getText().trim().equalsIgnoreCase("")) {
                if (Double.parseDouble(txtlote.getText().trim()) != 0) {
                    txtlote.setText(FarmaUtility.caracterIzquierda(txtlote.getText(), 3, "0"));
                    FarmaUtility.moveFocus(txtnrotarj);
                } else {
                    FarmaUtility.showMessage(this, "Ingrese Lote valido", txtlote);
                    txtlote.setText("");
                }
            } else {
                FarmaUtility.showMessage(this, "Ingrese Lote", txtlote);
            }
        } else
            chkkeyPressed(e);
    }


    private void procesarFormaPago() {
        //solo cuentas soles
        if (validaCampos()) {
            grabarFormaPagoPedidoBackup(VariablesCajaElectronica.vCodFPago, VariablesCajaElectronica.vNumPedVta);
            agregarNuevaFormaPago(VariablesCajaElectronica.vNumPedVta, codfp,
                                  FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesCajaElectronica.vMontPago),
                                                            3),
                                  FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesCajaElectronica.vMontPago),
                                                            3), "01", "0.00", "0.00", txtnrotarj.getText().trim(), "",
                                  "", txtdni.getText().trim(), txtcodvou.getText().trim(), txtlote.getText().trim(),
                                  "0");
        }

    }

    private void grabarFormaPagoPedidoBackup(String pCodFormaPago, String pNumPedVta) {
        try {
            DBCajaElectronica.grabaFormaPagoPedidoBackup(pCodFormaPago, pNumPedVta);
            //FarmaUtility.aceptarTransaccion();
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this, "Error al grabar backup de forma de pago del pedido." + e.getMessage(),
                                     txtlote);
            //FarmaUtility.moveFocusJTable(tblProductos);
            FarmaUtility.moveFocus(txtlote);
        }
    }

    private void agregarNuevaFormaPago(String pNumPedVta, String codFP, String pImPago, String pImTotalPago,
                                       String pTipMoneda, String pTipoCambio, String pVuelto, String pNumTarj,
                                       String pFecVencTarj, String pNomCliTarj, String pdnix, String pCodvou,
                                       String pLote, String pCantCupon) {
        try {
            DBCajaElectronica.grabaNuevaFormaPagoPedido(codFP, pNumPedVta, pImPago, pTipMoneda, pTipoCambio, pVuelto,
                                                        pImTotalPago, pNumTarj, pFecVencTarj, pNomCliTarj, pdnix,
                                                        pCodvou, pLote, pCantCupon);
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this, "Error al grabar nueva forma de pago del pedido." + e.getMessage(),
                                     txtlote);
            //FarmaUtility.moveFocusJTable(tblProductos);
            FarmaUtility.moveFocus(txtlote);
        }

    }

    private boolean validaCampos() {
        boolean flag = true;
        if (txtnrotarj.getText().trim().equalsIgnoreCase("")) {
            flag = false;
            FarmaUtility.showMessage(this, "Ingrese Nro. de Tarjeta", txtnrotarj);
        } else if (Double.parseDouble(txtnrotarj.getText().trim()) == 0) {
            flag = false;
            FarmaUtility.showMessage(this, "Ingrese Nro. de Tarjeta valido", txtnrotarj);
            txtnrotarj.setText("");
        } /*else if(!(txtnrotarj.getText().trim()).equalsIgnoreCase(nroTarjeta)){
            flag=false;
            //FarmaUtility.showMessage(this,"No debio cambiar el Nro. de Tarjeta",txtnrotarj);
            FarmaUtility.showMessage(this,"Nro. de Tarjeta incorrecto",txtnrotarj);
            inicializarVariables();
        }*/ else if (txtdni.getText().trim().equalsIgnoreCase("")) {
            flag = false;
            FarmaUtility.showMessage(this, "Ingrese DNI", txtdni);
        } else if (txtcodvou.getText().trim().equalsIgnoreCase("")) {
            flag = false;
            FarmaUtility.showMessage(this, "Ingrese Cod. Voucher", txtcodvou);
        } else if (txtlote.getText().trim().equalsIgnoreCase("")) {
            flag = false;
            FarmaUtility.showMessage(this, "Ingrese Lote", txtlote);
        } /*else if(bin.equalsIgnoreCase("")){
            flag=false;
            FarmaUtility.showMessage(this,"Ingrese Nro. de Tarjeta valido",txtnrotarj);
            txtnrotarj.setText("");
        }*/ else if (Double.parseDouble(txtdni.getText().trim()) == 0 && txtdni.getText().trim().length() > 0) {
            flag = false;
            FarmaUtility.showMessage(this, "DNI incorrecto, debe tener 8 digitos.", txtdni);
            txtdni.setText("");
        } else if (Double.parseDouble(txtcodvou.getText().trim()) == 0) {
            flag = false;
            FarmaUtility.showMessage(this, "Ingrese Cod. Voucher valido", txtcodvou);
            txtcodvou.setText("");
        } else if (Double.parseDouble(txtlote.getText().trim()) == 0) {
            flag = false;
            FarmaUtility.showMessage(this, "Ingrese Lote valido", txtlote);
            txtlote.setText("");
        }
        return flag;
    }

    private void lblmon_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void pnlInfo_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void pnlTitle_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void jLabelWhite1_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void pnlFondo_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void jButtonLabel1_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void jButtonLabel2_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void jButtonLabel3_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void jButtonLabel4_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void jButtonLabel1_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtnrotarj);
    }

    private void jButtonLabel2_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtdni);
    }

    private void jButtonLabel3_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtcodvou);
    }

    private void jButtonLabel4_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtlote);
    }


}
