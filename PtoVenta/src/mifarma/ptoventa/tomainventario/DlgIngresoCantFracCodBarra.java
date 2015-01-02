package mifarma.ptoventa.tomainventario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
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

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.tomainventario.reference.DBTomaInv;
import mifarma.ptoventa.tomainventario.reference.VariablesTomaInv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgIngresoCantFracCodBarra extends JDialog {
    Frame myParentFrame;

    FarmaTableModel tableModel;
    private static final Logger log = LoggerFactory.getLogger(DlgIngresoCantFracCodBarra.class);
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelWhite pnlTitle1 = new JPanelWhite();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelWhite lblCodigoBarra_T = new JLabelWhite();
    private JLabelWhite lblCodigoBarra = new JLabelWhite();
    private JLabelWhite lblDescProducto_T = new JLabelWhite();
    private JLabelWhite lblDescProducto = new JLabelWhite();
    private JLabelWhite lblUnidad_T = new JLabelWhite();
    private JLabelWhite lblValFraccion = new JLabelWhite();
    private JTextFieldSanSerif txtCantidad = new JTextFieldSanSerif();
    private JButtonLabel btnCantidad = new JButtonLabel();
    private JButtonLabel btnFraccion = new JButtonLabel();
    private JTextFieldSanSerif txtFraccion = new JTextFieldSanSerif();
    private JButtonLabel btnUnidad = new JButtonLabel();
    private JLabelWhite lblUnidadValFrac = new JLabelWhite();
    private JButtonLabel btnValFraccion = new JButtonLabel();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();


    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgIngresoCantFracCodBarra() {
        this(null, "", false);
    }

    public DlgIngresoCantFracCodBarra(Frame parent, String title, boolean modal) {
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

    private void jbInit() throws Exception {
        this.setSize(new Dimension(583, 294));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Ingreso de Cantidad - Fracción");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(5, 5, 565, 220));
        pnlTitle1.setBackground(Color.white);
        pnlTitle1.setBorder(BorderFactory.createLineBorder(new Color(225, 130, 14), 1));

        lblF11.setBounds(new Rectangle(160, 235, 110, 20));
        lblF11.setText("[ Enter ] Aceptar");
        lblEsc.setBounds(new Rectangle(280, 235, 110, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblCodigoBarra_T.setText("Código Barra:");
        lblCodigoBarra_T.setForeground(new Color(225, 130, 14));
        lblCodigoBarra_T.setBounds(new Rectangle(15, 20, 80, 15));
        lblCodigoBarra.setFont(new Font("SansSerif", 0, 11));
        lblCodigoBarra.setForeground(new Color(225, 130, 14));
        lblCodigoBarra.setText("");
        lblCodigoBarra.setBounds(new Rectangle(125, 20, 150, 20));
        lblDescProducto_T.setText("Producto:");
        lblDescProducto_T.setForeground(new Color(225, 130, 14));
        lblDescProducto_T.setBounds(new Rectangle(15, 45, 95, 20));
        lblDescProducto.setFont(new Font("SansSerif", 0, 11));
        lblDescProducto.setForeground(new Color(225, 130, 14));
        lblDescProducto.setText("producto");
        lblDescProducto.setBounds(new Rectangle(125, 50, 425, 15));

        lblUnidad_T.setText("Unidad");
        lblUnidad_T.setBounds(new Rectangle(10, 60, 95, 15));
        lblUnidad_T.setForeground(new Color(225, 130, 14));
        lblValFraccion.setText("ValFrac");
        lblValFraccion.setBounds(new Rectangle(125, 140, 200, 15));
        lblValFraccion.setFont(new Font("SansSerif", 0, 11));
        lblValFraccion.setForeground(new Color(225, 130, 14));
        txtCantidad.setBounds(new Rectangle(75, 170, 80, 20));
        txtCantidad.setLengthText(4);
        //txtCantidad.setBounds(new Rectangle(110, 90, 80, 20));
        txtCantidad.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCantidad_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtCantidad_keyTyped(e);
            }

            public void keyReleased(KeyEvent e) {
                txtCantidad_keyReleased(e);
            }
        });
        txtFraccion.setLengthText(4);
        btnCantidad.setText("Cantidad");
        btnCantidad.setBounds(new Rectangle(15, 170, 55, 20));
        btnCantidad.setForeground(new Color(255, 130, 14));
        //btnCantidad.setBounds(new Rectangle(10, 90, 60, 20));
        // btnCantidad.setForeground(new Color(255, 130, 14));
        btnCantidad.setMnemonic('c');
        btnCantidad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCantidad_actionPerformed(e);
            }
        });

        btnFraccion.setText("Fracción");
        btnFraccion.setMnemonic('f');
        btnFraccion.setBounds(new Rectangle(185, 175, 55, 15));
        btnFraccion.setForeground(new Color(255, 130, 14));
        btnFraccion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnFraccion_actionPerformed(e);
            }
        });
        txtFraccion.setBounds(new Rectangle(245, 170, 80, 20));
        txtFraccion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtFraccion_actionPerformed(e);
            }
        });

        txtFraccion.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFraccion_keyPressed(e);
            }
        });
        btnUnidad.setText("Laboratorio:");
        btnUnidad.setForeground(new Color(225, 130, 20));
        btnUnidad.setBounds(new Rectangle(15, 75, 140, 20));
        //
        lblUnidadValFrac.setFont(new Font("SansSerif", 0, 11));
        lblUnidadValFrac.setForeground(new Color(225, 130, 14));
        lblUnidadValFrac.setText("");
        lblUnidadValFrac.setBounds(new Rectangle(155, 75, 245, 15));
        btnValFraccion.setText("Valor Fracción: ");
        btnValFraccion.setForeground(new Color(225, 130, 14));
        btnValFraccion.setBounds(new Rectangle(15, 140, 115, 15));

        jLabel1.setBounds(new Rectangle(120, 105, 215, 20));
        jLabel1.setForeground(new Color(225, 130, 14));
        jLabel2.setText("Unidad Venta :");
        jLabel2.setBounds(new Rectangle(15, 105, 120, 20));
        jLabel2.setForeground(new Color(225, 130, 14));
        jLabel2.setFont(new Font("Dialog", 1, 11));
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(pnlTitle1, null);
        pnlTitle1.add(jLabel2, null);
        pnlTitle1.add(jLabel1, null);
        pnlTitle1.add(btnValFraccion, null);
        pnlTitle1.add(btnUnidad, null);
        pnlTitle1.add(txtFraccion, null);
        pnlTitle1.add(btnFraccion, null);
        pnlTitle1.add(btnCantidad, null);
        pnlTitle1.add(txtCantidad, null);
        pnlTitle1.add(lblValFraccion, null);
        pnlTitle1.add(lblUnidad_T, null);
        pnlTitle1.add(lblDescProducto, null);
        pnlTitle1.add(lblDescProducto_T, null);
        pnlTitle1.add(lblCodigoBarra, null);
        pnlTitle1.add(lblCodigoBarra_T, null);
        pnlTitle1.add(lblUnidadValFrac, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);

        //oculto
        lblDescProducto.setVisible(true);
        lblUnidad_T.setVisible(false);
        lblValFraccion.setVisible(true);
        lblDescProducto_T.setVisible(true);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        mostrarDatos();
    };
    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtCantidad);
    }

    private void txtCantidad_keyTyped(KeyEvent e) {

    }

    private void txtCantidad_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            ingresoCantidadFraccion("ENTERO", txtFraccion.isEnabled());
        }
        chkKeyPressed(e);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnCantidad_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCantidad);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        /*
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    ingresoCantidadFraccion();

            } else
            */
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            VariablesTomaInv.vIndIngresaProd = "N"; //no ingresa Producto
            limpiarVariables();
            FarmaVariables.vAceptar = false;
            this.setVisible(false);
        }
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void mostrarDatos() {
        ArrayList pDatosUpd = new ArrayList();
        try {
            if (VariablesTomaInv.vIndActualizaProd.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                //actualiza Producto
                DBTomaInv.getInfoProd(pDatosUpd, VariablesTomaInv.vCodProductoTemp);
                VariablesTomaInv.vDescripcion = FarmaUtility.getValueFieldArrayList(pDatosUpd, 0, 1);
                VariablesTomaInv.vUnidadPresentacion = FarmaUtility.getValueFieldArrayList(pDatosUpd, 0, 2);
                VariablesTomaInv.vFraccionLocal = FarmaUtility.getValueFieldArrayList(pDatosUpd, 0, 3);
                VariablesTomaInv.vUnidadVenta = FarmaUtility.getValueFieldArrayList(pDatosUpd, 0, 4);
                VariablesTomaInv.vCodBarraIngresado = VariablesTomaInv.vCodBarraTemp;
                VariablesTomaInv.vCodProducto = VariablesTomaInv.vCodProductoTemp;
                lblCodigoBarra.setText(VariablesTomaInv.vCodBarraIngresado);
                lblDescProducto.setText(VariablesTomaInv.vCodProducto + " - " + VariablesTomaInv.vDescripcion + " - " +
                                        VariablesTomaInv.vUnidadPresentacion);
                lblUnidadValFrac.setText(VariablesTomaInv.vNomLab);
                lblValFraccion.setText(VariablesTomaInv.vFraccionLocal);
                jLabel1.setText(VariablesTomaInv.vUnidadVenta.trim());
                txtCantidad.setText("");
                if (Integer.parseInt(VariablesTomaInv.vFraccionLocal.trim()) == 1) {
                    txtFraccion.setText("0");
                    txtFraccion.setEnabled(false);
                } else {
                    txtFraccion.setText("");
                    txtFraccion.setEnabled(true);
                }
                FarmaUtility.moveFocus(txtCantidad);
            } else {
                //VariablesInventario.vCodProducto
                ArrayList pDatos = new ArrayList();
                //   try {

                DBTomaInv.getInfoProd(pDatos, VariablesTomaInv.vCodProducto);

                //VariablesTomaInv.vCodBarraIngresado = "";
                //VariablesTomaInv.vCodProducto = "";

                if (pDatos.size() == 1) {
                    /*
                   p.COD_PROD                   || 'Ã' || 0
                   NVL(P.DESC_PROD,' ')               || 'Ã' || 1
                   NVL(P.DESC_UNID_PRESENT,' ') || 'Ã' || 2
                   PL.VAL_FRAC_LOCAL            || 'Ã' || 3
                   NVL(PL.UNID_VTA,' ')      || 'Ã' || 4
                   lab.nom_lab 5
                     * */

                    VariablesTomaInv.vDescripcion = FarmaUtility.getValueFieldArrayList(pDatos, 0, 1);
                    VariablesTomaInv.vUnidadPresentacion = FarmaUtility.getValueFieldArrayList(pDatos, 0, 2);
                    VariablesTomaInv.vFraccionLocal = FarmaUtility.getValueFieldArrayList(pDatos, 0, 3);
                    VariablesTomaInv.vUnidadVenta = FarmaUtility.getValueFieldArrayList(pDatos, 0, 4);
                    VariablesTomaInv.vLaboratorio_Barra = FarmaUtility.getValueFieldArrayList(pDatos, 0, 5);

                    log.debug("VariablesTomaInv.vCodBarraIngresado :" + VariablesTomaInv.vCodBarraIngresado);
                    log.debug("VariablesTomaInv.vCodProducto :" + VariablesTomaInv.vCodProducto);
                    log.debug("VariablesTomaInv.vDescripcion :" + VariablesTomaInv.vDescripcion);
                    log.debug("VariablesTomaInv.vUnidadPresentacion :" + VariablesTomaInv.vUnidadPresentacion);
                    log.debug("VariablesTomaInv.vFraccionLocal :" + VariablesTomaInv.vFraccionLocal);
                    log.debug("VariablesTomaInv.vUnidadVenta :" + VariablesTomaInv.vUnidadVenta);
                    log.debug("VariablesTomaInv.vLaboratorio_Barra :" + VariablesTomaInv.vLaboratorio_Barra);

                    lblCodigoBarra.setText(VariablesTomaInv.vCodBarraIngresado);
                    lblDescProducto.setText(VariablesTomaInv.vCodProducto + " - " + VariablesTomaInv.vDescripcion +
                                            " - " + VariablesTomaInv.vUnidadPresentacion.trim());
                    lblUnidadValFrac.setText(VariablesTomaInv.vLaboratorio_Barra);
                    jLabel1.setText(VariablesTomaInv.vUnidadVenta.trim());
                    lblValFraccion.setText(VariablesTomaInv.vFraccionLocal.trim());

                    txtCantidad.setText("");
                    if (Integer.parseInt(VariablesTomaInv.vFraccionLocal.trim()) == 1) {
                        txtFraccion.setText("0");
                        txtFraccion.setEnabled(false);
                    } else {
                        txtFraccion.setText("");
                        txtFraccion.setEnabled(true);
                    }

                    FarmaUtility.moveFocus(txtCantidad);
                }

                /*}catch (SQLException sql) {

                 log.error("",sql);
                FarmaUtility.showMessage(this,"Ocurrió un error al obtener los datos del Producto.\n"+ sql.getMessage(),txtCantidad);
            }*/
            }
        } catch (SQLException sql) {

            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al obtener los datos del Producto.\n" +
                    sql.getMessage(), txtCantidad);
        }

    }

    private boolean isInteger(String pCadena) {
        int pNumero = 0;
        try {
            pNumero = Integer.parseInt(pCadena.trim());
            if (pNumero >= 0)
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        VariablesTomaInv.vIndActualizaProd = "N";
        this.setVisible(false);
        this.dispose();
    }


    private void txtCantidad_keyReleased(KeyEvent e) {

    }


    private void ingresoCantidadFraccion(String pTipoIngreso, boolean pIndHablitadoFraccion) {
        String pCantidad, pFraccion;
        pCantidad = txtCantidad.getText().trim();
        pFraccion = txtFraccion.getText().trim();

        //////////////////////
        if (pTipoIngreso.trim().equalsIgnoreCase("ENTERO")) {
            if (pCantidad.trim().length() <= 0) {
                FarmaUtility.showMessage(this, "Debe ingresar la cantidad.", txtCantidad);
                return;
            }
            if (!isInteger(pCantidad.trim())) {
                FarmaUtility.showMessage(this, "Cantidad ingresada es Incorrecta.\n¡Verifique!", txtCantidad);
                return;
            }


            if (!pIndHablitadoFraccion) {
                if (pCantidad.trim().equalsIgnoreCase("0") && pFraccion.trim().equalsIgnoreCase("0")) {
                    if (VariablesTomaInv.vIndActualizaProd.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                        boolean flagDel =
                            JConfirmDialog.rptaConfirmDialogDefaultNo(this, "Está seguro de eliminar el Producto.\n¡Verifique!");
                        if (flagDel) {
                            VariablesTomaInv.vIndEliminaProd = "S";
                            cerrarVentana(false);
                        } else
                            return;
                    } else {
                        FarmaUtility.showMessage(this,
                                                 "Ud. no puede ingresar un producto con Cantidad Entera y Fracción igual a Cero.",
                                                 txtCantidad);
                        return;
                    }
                }

                VariablesTomaInv.vCantEnteraIngresada = pCantidad.trim();
                VariablesTomaInv.vCantFracIngresada = pFraccion.trim();
                log.info("VariablesTomaInv.vCantEnteraIngresada:" + VariablesTomaInv.vCantEnteraIngresada);
                log.info("VariablesTomaInv.vCantFracIngresada:" + VariablesTomaInv.vCantFracIngresada);

                VariablesTomaInv.vIndIngresaProd = "S";
                log.info("vIndIngresaProd: " + VariablesTomaInv.vIndIngresaProd);
                cerrarVentana(true);
            } else {
                FarmaUtility.moveFocus(txtFraccion);
            }
        } else if (pTipoIngreso.trim().equalsIgnoreCase("FRACCION")) {
            if (pCantidad.trim().length() <= 0) {
                FarmaUtility.showMessage(this, "Debe ingresar la cantidad.", txtCantidad);
                return;
            }
            if (!isInteger(pCantidad.trim())) {
                FarmaUtility.showMessage(this, "Cantidad ingresada es Incorrecta.\n¡Verifique!", txtCantidad);
                return;
            }
            if (pCantidad.trim().equalsIgnoreCase("0") && pFraccion.trim().equalsIgnoreCase("0")) {
                if (VariablesTomaInv.vIndActualizaProd.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                    boolean flagDel =
                        JConfirmDialog.rptaConfirmDialogDefaultNo(this, "Está seguro de eliminar el Producto.\n¡Verifique!");
                    if (flagDel) {
                        VariablesTomaInv.vIndEliminaProd = "S";
                        cerrarVentana(false);
                    } else
                        return;
                } else {
                    FarmaUtility.showMessage(this,
                                             "Ud. no puede ingresar un producto con Cantidad Entera y Fracción igual a Cero.",
                                             txtCantidad);
                    return;
                }
            }

            if (pFraccion.trim().length() <= 0) {
                FarmaUtility.showMessage(this, "Debe de ingresar cantidad en Fracción.", txtFraccion);
                return;
            }
            if (!isInteger(pFraccion.trim())) {
                FarmaUtility.showMessage(this, "Cantidad Fracción ingresada es Incorrecta.\n¡Verifique!", txtFraccion);
                return;
            } else if (pFraccion.trim().equalsIgnoreCase(VariablesTomaInv.vFraccionLocal)) {
                FarmaUtility.showMessage(this, "La cantidad de Fraccion no puede ser igual al Valor de Fraccion",
                                         txtFraccion);
                return;
            } else if (Integer.parseInt(pFraccion.trim()) > Integer.parseInt(VariablesTomaInv.vFraccionLocal)) {
                FarmaUtility.showMessage(this,
                                         "La cantidad de Fraccion ingresada no puede ser mayor al valor de Fraccion",
                                         txtFraccion);
                return;
            }


            VariablesTomaInv.vCantEnteraIngresada = pCantidad.trim();
            VariablesTomaInv.vCantFracIngresada = pFraccion.trim();
            log.info("VariablesTomaInv.vCantEnteraIngresada:" + VariablesTomaInv.vCantEnteraIngresada);
            log.info("VariablesTomaInv.vCantFracIngresada:" + VariablesTomaInv.vCantFracIngresada);

            VariablesTomaInv.vIndIngresaProd = "S";
            log.info("vIndIngresaProd: " + VariablesTomaInv.vIndIngresaProd);
            cerrarVentana(true);


        }
    }

    /*private void ingresaFraccion(){
            if(pFraccion.trim().length()<=0){
                FarmaUtility.showMessage(this,"Debe de ingresar cantidad en Fracción.",txtFraccion);
               return;
            }

            if(!isInteger(pFraccion.trim())){
                FarmaUtility.showMessage(this,"Cantidad Fracción ingresada es Incorrecta.\n¡Verifique!",txtFraccion);
               return;
            }
    }*/

    private void limpiarVariables() {

    }

    private void txtFraccion_actionPerformed(ActionEvent e) {
        // FarmaUtility.moveFocus(txtFraccion);
    }


    private void txtFraccion_keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            ingresoCantidadFraccion("FRACCION", txtFraccion.isEnabled());
        }
        chkKeyPressed(e);

    }

    private void btnFraccion_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFraccion);
    }


}
