package mifarma.ptoventa.administracion.mantenimiento;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
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

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.mantenimiento.reference.DBMantenimiento;
import mifarma.ptoventa.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgParametros.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      12.07.2006   Creación<br>
 * ASOLIS     04.11.2008   Modificación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgParametros extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgParametros.class);
    Frame myParentFrame;
    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JPanelWhite pnlTitle1 = new JPanelWhite();
    private JLabelOrange lblActImp = new JLabelOrange();
    private JTextFieldSanSerif txtImpReporte = new JTextFieldSanSerif();
    private JLabelOrange lblImpReporte = new JLabelOrange();
    private JPanelWhite pnlTitle2 = new JPanelWhite();
    private JLabelOrange lblMinPendientes = new JLabelOrange();
    private JTextFieldSanSerif txtMinPendientes = new JTextFieldSanSerif();
    private JLabelOrange lblActMin = new JLabelOrange();
    private JButtonLabel btnImpReporte = new JButtonLabel();
    private JButtonLabel btnMinPendientes = new JButtonLabel();
    private JPanelWhite pnlTitle3 = new JPanelWhite();
    private JButtonLabel btnMinPendientes1 = new JButtonLabel();
    private JLabelOrange lblindPrecioActual = new JLabelOrange();
    private JLabelOrange lblPrecioactual = new JLabelOrange();
    private JComboBox cmbCambioPrecio = new JComboBox();
    private JPanelWhite pnlTitle4 = new JPanelWhite();
    private JLabelOrange lblModeloImpActual = new JLabelOrange();
    private JLabelOrange lblindModeloImpActual = new JLabelOrange();
    private JButtonLabel btnModeloImp = new JButtonLabel();
    private JComboBox cmbCambioImpresora = new JComboBox();


    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgParametros() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    public DlgParametros(Frame parent, String title, boolean modal) {
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
        this.setTitle("Mantenimiento Parametros");
        this.setSize(new Dimension(393, 474));
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        lblEsc.setBounds(new Rectangle(280, 415, 95, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblF11.setBounds(new Rectangle(165, 415, 105, 20));
        lblF11.setText("[ F11 ] Aceptar");
        pnlTitle1.setBounds(new Rectangle(10, 10, 365, 90));
        pnlTitle1.setBorder(BorderFactory.createTitledBorder("Impresora Reporte"));
        lblActImp.setText("Actual:");
        lblActImp.setBounds(new Rectangle(10, 20, 50, 20));
        txtImpReporte.setBounds(new Rectangle(60, 50, 290, 20));
        txtImpReporte.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtImpReporte_keyPressed(e);
            }
        });
        lblImpReporte.setBounds(new Rectangle(60, 20, 290, 20));
        pnlTitle2.setBounds(new Rectangle(10, 110, 365, 90));
        pnlTitle2.setBorder(BorderFactory.createTitledBorder("Minutos Pedidos Pendientes"));
        lblMinPendientes.setBounds(new Rectangle(60, 20, 65, 20));
        txtMinPendientes.setBounds(new Rectangle(60, 50, 65, 20));
        txtMinPendientes.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtMinPendientes_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtMinPendientes_keyTyped(e);
            }
        });
        lblActMin.setText("Actual:");
        lblActMin.setBounds(new Rectangle(10, 20, 50, 20));
        btnImpReporte.setText("Nuevo:");
        btnImpReporte.setBounds(new Rectangle(10, 50, 45, 20));
        btnImpReporte.setForeground(new Color(255, 130, 14));
        btnImpReporte.setMnemonic('N');
        btnImpReporte.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnImpReporte_actionPerformed(e);
            }
        });
        btnMinPendientes.setText("Nuevo:");
        pnlTitle2.setBorder(BorderFactory.createTitledBorder("Minutos Pedidos Pendientes"));
        btnMinPendientes.setBounds(new Rectangle(10, 50, 45, 20));
        btnMinPendientes.setForeground(new Color(255, 130, 14));
        btnMinPendientes.setMnemonic('U');
        btnMinPendientes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnMinPendientes_actionPerformed(e);
            }
        });
        pnlTitle3.setBounds(new Rectangle(10, 210, 365, 90));
        pnlTitle3.setBorder(BorderFactory.createTitledBorder("Indicador de Cambio Precio"));
        btnMinPendientes1.setText("Nuevo:");
        btnMinPendientes1.setBounds(new Rectangle(10, 50, 45, 20));
        btnMinPendientes1.setForeground(new Color(255, 130, 14));
        btnMinPendientes1.setMnemonic('e');
        btnMinPendientes1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnMinPendientes_actionPerformed(e);

            }
        });
        lblindPrecioActual.setBounds(new Rectangle(60, 20, 65, 20));
        lblPrecioactual.setText("Actual:");
        lblPrecioactual.setBounds(new Rectangle(10, 20, 50, 20));
        cmbCambioPrecio.setBounds(new Rectangle(60, 50, 70, 20));
        cmbCambioPrecio.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmCambioPrecio_keyPressed(e);
            }
        });
        pnlTitle4.setBounds(new Rectangle(10, 310, 365, 90));
        pnlTitle4.setBorder(BorderFactory.createTitledBorder("Tipo de Impresora Térmica"));
        btnModeloImp.setText("Modelo");
        btnModeloImp.setBounds(new Rectangle(10, 50, 45, 20));
        btnModeloImp.setForeground(new Color(255, 130, 14));
        btnModeloImp.setMnemonic('M');

        btnModeloImp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnModeloImp_actionPerformed(e);
            }
        });

        lblindModeloImpActual.setBounds(new Rectangle(60, 20, 65, 20));
        lblModeloImpActual.setText("Actual:");
        lblModeloImpActual.setBounds(new Rectangle(10, 20, 50, 20));


        cmbCambioImpresora.setBounds(new Rectangle(60, 50, 70, 20));
        cmbCambioImpresora.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbCambioImpresora_keyPressed(e);
            }


        });

        pnlTitle4.add(btnModeloImp, null);
        pnlTitle4.add(cmbCambioImpresora, null);
        pnlTitle4.add(lblModeloImpActual, null);
        pnlTitle4.add(lblindModeloImpActual, null);
        pnlTitle3.add(btnMinPendientes1, null);
        pnlTitle3.add(lblindPrecioActual, null);
        pnlTitle3.add(lblPrecioactual, null);
        pnlTitle3.add(cmbCambioPrecio, null);
        pnlTitle2.add(btnMinPendientes, null);
        pnlTitle2.add(lblMinPendientes, null);
        pnlTitle2.add(txtMinPendientes, null);
        pnlTitle2.add(lblActMin, null);
        pnlTitle1.add(btnImpReporte, null);
        pnlTitle1.add(lblImpReporte, null);
        pnlTitle1.add(txtImpReporte, null);
        pnlTitle1.add(lblActImp, null);
        jContentPane.add(pnlTitle4, null);
        jContentPane.add(pnlTitle3, null);
        jContentPane.add(pnlTitle2, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        //
        txtImpReporte.setLengthText(30);
        txtMinPendientes.setLengthText(3);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        cargaComboIndCambioPrecio();
        cargaComboIndModelo_Impresora();
        initParametros();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void cargaComboIndCambioPrecio() {
        String codigo[] = { "N", "S" }, valor[] = { "NO", "SI" };
        FarmaLoadCVL.loadCVLfromArrays(cmbCambioPrecio, "CambioPrecio", codigo, valor, true);
    }

    private void cargaComboIndModelo_Impresora() {
        ArrayList parametros = new ArrayList();
        FarmaLoadCVL.loadCVLFromSP(cmbCambioImpresora, ConstantsPtoVenta.NOM_HASTABLE_CMBMODELO_IMPRESORA,
                                   "Farma_Gral.GET_LISTA_MODELO_IMPRESORA", parametros, true, true);


    }


    private void initParametros() {
        try {
            ArrayList parametros = new ArrayList();
            DBMantenimiento.getParametrosLocal(parametros);
            if (parametros.size() > 0) {
                parametros = (ArrayList)parametros.get(0);

                String impresora = parametros.get(0).toString();
                String minutos = parametros.get(1).toString();
                String indCambioPrecio = parametros.get(2).toString();
                String indCambioModelo_Impresora = parametros.get(3).toString();

                lblImpReporte.setText(impresora);
                txtImpReporte.setText(impresora);
                lblMinPendientes.setText(minutos);
                txtMinPendientes.setText(minutos);
                lblindPrecioActual.setText(indCambioPrecio);
                cmbCambioPrecio.setSelectedItem(indCambioPrecio);
                lblindModeloImpActual.setText(indCambioModelo_Impresora);
                cmbCambioImpresora.setSelectedItem(indCambioModelo_Impresora);
                log.debug("veerificaModeloImpresora: " + parametros);
            } else {
                FarmaUtility.showMessage(this, "No hay información del local.", null);
                cerrarVentana(false);
            }
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ha ocurrido un error al recuperar la información.\n" +
                    e, null);
            cerrarVentana(false);
        }
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(txtImpReporte);
        FarmaUtility.centrarVentana(this);
        if (!FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_OPERADOR_SISTEMAS)) {
            cmbCambioPrecio.setEnabled(false);
            cmbCambioImpresora.setEnabled(false);

        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnImpReporte_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtImpReporte);
    }

    private void txtImpReporte_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtMinPendientes);
        else
            chkKeyPressed(e);
    }


    private void btnMinPendientes_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbCambioPrecio);
    }

    private void txtMinPendientes_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            if (cmbCambioPrecio.isEnabled())
                FarmaUtility.moveFocus(cmbCambioPrecio);
            else
                FarmaUtility.moveFocus(txtImpReporte);
        else
            chkKeyPressed(e);
    }

    private void txtMinPendientes_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtMinPendientes, e);
    }

    private void btnModeloImp_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbCambioImpresora);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, btnImpReporte);
            else
                funcionF11();
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

    private void funcionF11() {
        if (validarCampos())
            if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                            "¿Está seguro de actualizar los datos?"))
                if (grabar()) {
                    FarmaUtility.showMessage(this,
                                             "Se actualizó los parámetros. Deberán salir del sistema para que surtan efecto.",
                                             null);
                    cerrarVentana(true);
                }
    }

    private boolean validarCampos() {
        boolean retorno = true;

        if (txtImpReporte.getText().trim().length() == 0) {
            retorno = false;
            FarmaUtility.showMessage(this, "Debe ingresar la ruta de la Impresora de Reportes.", txtImpReporte);
        } else if (txtMinPendientes.getText().trim().length() == 0) {
            retorno = false;
            FarmaUtility.showMessage(this, "Debe ingresar los minutos para borrar los pedidos pendientes.",
                                     txtMinPendientes);
        } else if (!FarmaUtility.isInteger(txtMinPendientes.getText().trim())) {
            retorno = false;
            FarmaUtility.showMessage(this, "Debe ingresar un valor númerico.", txtMinPendientes);
        }

        return retorno;
    }

    private boolean grabar() {
        boolean retorno;
        try {
            DBMantenimiento.actualizaParametrosLocal(txtImpReporte.getText().trim(), txtMinPendientes.getText().trim(),
                                                     FarmaLoadCVL.getCVLCode("CambioPrecio",
                                                                             cmbCambioPrecio.getSelectedIndex()),
                                                     FarmaLoadCVL.getCVLCode(ConstantsPtoVenta.NOM_HASTABLE_CMBMODELO_IMPRESORA,
                                                                             cmbCambioImpresora.getSelectedIndex()));
            FarmaUtility.aceptarTransaccion();


            retorno = true;
        } catch (SQLException e) {
            FarmaUtility.liberarTransaccion();
            log.error("", e);
            retorno = false;
            FarmaUtility.showMessage(this, "Ha ocurrido un error al intentar actualzar los datos.\n" +
                    e, txtImpReporte);
        }
        return retorno;
    }

    private void cmCambioPrecio_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(cmbCambioImpresora);
        else
            chkKeyPressed(e);
    }

    private void cmbCambioImpresora_keyPressed(KeyEvent e) {


        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtImpReporte);
        else
            chkKeyPressed(e);

    }

}
