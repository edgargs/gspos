package mifarma.ptoventa.recepcionCiega;


import com.gs.mifarma.componentes.JButtonLabel;
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
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JDialog;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.recepcionCiega.reference.DBRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.UtilityRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.VariablesRecepCiega;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgIngresoProdTransferencia extends JDialog {
    Frame myParentFrame;
    private static final Logger log = LoggerFactory.getLogger(DlgIngresoProdTransferencia.class);

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelWhite pnlDatos = new JPanelWhite();
    private JLabelWhite lblProducto_T = new JLabelWhite();
    private JLabelWhite lblUnidad_T = new JLabelWhite();
    private JTextFieldSanSerif txtCantidad = new JTextFieldSanSerif();
    private JLabelWhite lblProducto = new JLabelWhite();
    private JLabelWhite lblUnidad = new JLabelWhite();
    private JTextFieldSanSerif txtLote = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaVcto = new JTextFieldSanSerif();
    private JLabelFunction lblEnter = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JButtonLabel btnCantidad = new JButtonLabel();
    private JButtonLabel btnLote = new JButtonLabel();
    private JButtonLabel btnFechaVcto = new JButtonLabel();


    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgIngresoProdTransferencia() {
        this(null, "", false);

    }

    public DlgIngresoProdTransferencia(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(345, 240));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Ingreso de Cantidad");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlDatos.setBounds(new Rectangle(5, 5, 330, 170));
        pnlDatos.setBackground(Color.white);
        pnlDatos.setBorder(BorderFactory.createLineBorder(new Color(225, 130, 14), 1));
        lblProducto_T.setText("Producto");
        lblProducto_T.setForeground(new Color(225, 130, 14));
        lblProducto_T.setBounds(new Rectangle(15, 15, 80, 15));
        lblUnidad_T.setText("Unidad");
        lblUnidad_T.setForeground(new Color(225, 130, 14));
        lblUnidad_T.setBounds(new Rectangle(15, 45, 70, 15));
        txtCantidad.setBounds(new Rectangle(105, 75, 90, 20));
        txtCantidad.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCantidad_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtCantidad_keyTyped(e);
            }
        });
        txtCantidad.setLengthText(6);
        lblProducto.setFont(new Font("SansSerif", 0, 11));
        lblProducto.setForeground(new Color(225, 130, 14));
        lblProducto.setText("producto");
        lblProducto.setBounds(new Rectangle(105, 15, 215, 15));

        lblUnidad.setText("unidad");
        lblUnidad.setBounds(new Rectangle(105, 45, 70, 15));
        lblUnidad.setFont(new Font("SansSerif", 0, 11));
        lblUnidad.setForeground(new Color(225, 130, 14));
        txtLote.setBounds(new Rectangle(105, 105, 90, 20));
        txtLote.setLengthText(10);
        txtLote.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtLote_keyPressed(e);
            }


        });
        txtFechaVcto.setLengthText(10);
        txtFechaVcto.setBounds(new Rectangle(105, 135, 90, 20));
        txtFechaVcto.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaVcto_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaVcto_keyReleased(e);
            }

            public void keyTyped(KeyEvent e) {
                txtFechaVcto_keyTyped(e);
            }
        });
        lblEnter.setBounds(new Rectangle(90, 185, 117, 20));
        lblEnter.setText("[ F11 ] Aceptar");
        lblEsc.setBounds(new Rectangle(220, 185, 110, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        btnCantidad.setText("Cantidad");
        btnCantidad.setBounds(new Rectangle(10, 75, 60, 20));
        btnCantidad.setBackground(new Color(225, 134, 14));
        btnCantidad.setForeground(new Color(223, 134, 14));
        btnCantidad.setMnemonic('c');

        btnCantidad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCantidad_actionPerformed(e);
            }
        });
        btnLote.setText("Lote");
        btnLote.setBounds(new Rectangle(10, 110, 75, 15));
        btnLote.setForeground(new Color(225, 134, 14));
        btnLote.setMnemonic('l');
        btnLote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLote_actionPerformed(e);
            }
        });
        btnFechaVcto.setText("Fecha Vcto.");
        btnFechaVcto.setBounds(new Rectangle(10, 140, 75, 15));
        btnFechaVcto.setForeground(new Color(225, 134, 14));
        btnFechaVcto.setMnemonic('f');
        btnFechaVcto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnFechaVcto_actionPerformed(e);
            }
        });
        pnlDatos.add(btnFechaVcto, null);
        pnlDatos.add(btnLote, null);
        pnlDatos.add(btnCantidad, null);
        pnlDatos.add(txtFechaVcto, null);
        pnlDatos.add(txtLote, null);
        pnlDatos.add(lblUnidad, null);
        pnlDatos.add(lblProducto, null);
        pnlDatos.add(txtCantidad, null);
        pnlDatos.add(lblUnidad_T, null);
        pnlDatos.add(lblProducto_T, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblEnter, null);
        jContentPane.add(pnlDatos, null);
        this.getContentPane().add(jContentPane, null);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        FarmaUtility.centrarVentana(this);
        mostrarDatos();
    };

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtCantidad);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void txtCantidad_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCantidad, e);
    }

    private void txtCantidad_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            /**
             * JMIRANDA 08.01.10
             * validar que no sea cero
             * */
            if (txtCantidad.getText().trim().equalsIgnoreCase("0")) {
                FarmaUtility.showMessage(this, "No puede transferir 0 unidades.", txtCantidad);
                return;
            }
            if (txtCantidad.getText().trim().length() > 0) {
                if (txtLote.isEditable())
                    FarmaUtility.moveFocus(txtLote);
            }
        }
        chkKeyPressed(e);
    }

    private void txtLote_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtLote.getText().trim().length() > 0) {
                FarmaUtility.moveFocus(txtFechaVcto);
            }
        }
        chkKeyPressed(e);
    }


    private void txtFechaVcto_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtFechaVcto.getText().trim().length() > 0) {
                FarmaUtility.moveFocus(txtCantidad);
            }
        }
        chkKeyPressed(e);
    }


    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        String pCodigoProducto = "";
        int pCantidadStk = 0;
        String pTipoStkComprometido = "";
        String pTipoRespaldoStock = "";
        int pCantidadRespaldo = 0;
        String secRespaldo = ""; //ASOSA, 21.07.2010
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (datosValidados()) {
                if (VariablesRecepCiega.vIndModificarIngresoCantProdTranf) {
                    int cantIngresada_old = VariablesRecepCiega.vCantIngreso;
                    int cantIngresada = Integer.parseInt(txtCantidad.getText().trim());
                    if (!existeStockDisponible(VariablesRecepCiega.vCodProd, cantIngresada,
                                               VariablesRecepCiega.vLote)) {
                        FarmaUtility.showMessage(this, "No existe stock disponible", txtCantidad);
                    } else {

                        //inicio-dubilluz
                        int cantidadEnteroTotal = 0;
                        int i = 0;
                        String codProd = "", cantidadParcial = "";
                        if (VariablesRecepCiega.vTableModelProdTranf != null) {
                            while (i < VariablesRecepCiega.vTableModelProdTranf.getRowCount()) {
                                codProd = (String)VariablesRecepCiega.vTableModelProdTranf.getValueAt(i, 5);
                                cantidadParcial = (String)VariablesRecepCiega.vTableModelProdTranf.getValueAt(i, 2);
                                if (codProd.equalsIgnoreCase(VariablesRecepCiega.vCodProd)) {
                                    cantidadEnteroTotal += Integer.parseInt(cantidadParcial.trim());
                                }
                                i++;
                            }
                        }

                        log.info("cantidadEnteroTotal:" + cantidadEnteroTotal);

                        //fin
                        //cantIngresada_old = cantidadEnteroTotal;

                        int cantidadNewPblRespaldo = 0;


                        if (cantIngresada_old > cantIngresada) {
                            if (cantidadEnteroTotal > 0) {
                                cantidadNewPblRespaldo = cantidadEnteroTotal - cantIngresada;
                            }
                            /*actualizaStkComprometidoProd(VariablesRecepCiega.vCodProd,
                                                         (cantIngresada_old -
                                                          cantIngresada),
                                                         ConstantsInventario.INDICADOR_D,
                                                         ConstantsInventario.TIP_OPERACION_RESPALDO_ACTUALIZAR,
                                                        cantidadNewPblRespaldo*Integer.parseInt(VariablesRecepCiega.vValFrac));*/
                            actualizaStkComprometidoProd_02(VariablesRecepCiega.vCodProd, //ASOSA, 21.07.2010
                                    (cantIngresada_old - cantIngresada), ConstantsInventario.INDICADOR_D,
                                    ConstantsInventario.TIP_OPERACION_RESPALDO_ACTUALIZAR,
                                    cantIngresada * Integer.parseInt(VariablesRecepCiega.vValFrac), secRespaldo);
                        } else if (cantIngresada_old < cantIngresada) {
                            if (cantidadEnteroTotal > 0) {
                                cantidadNewPblRespaldo = cantidadEnteroTotal + cantIngresada - cantIngresada_old;
                            }
                            /*actualizaStkComprometidoProd(VariablesRecepCiega.vCodProd,
                                                         (cantIngresada -
                                                          cantIngresada_old),
                                                         ConstantsInventario.INDICADOR_A,
                                                         ConstantsInventario.TIP_OPERACION_RESPALDO_ACTUALIZAR,
                                                        cantidadNewPblRespaldo*Integer.parseInt(VariablesRecepCiega.vValFrac));*/
                            actualizaStkComprometidoProd_02(VariablesRecepCiega.vCodProd, //ASOSA, 21.07.2010
                                    (cantIngresada - cantIngresada_old), ConstantsInventario.INDICADOR_A,
                                    ConstantsInventario.TIP_OPERACION_RESPALDO_ACTUALIZAR,
                                    cantIngresada * Integer.parseInt(VariablesRecepCiega.vValFrac), secRespaldo);
                        }

                        VariablesRecepCiega.vCantIngreso = cantIngresada;
                        VariablesRecepCiega.vLote = txtLote.getText().toString().trim();
                        VariablesRecepCiega.vFechaVcto = txtFechaVcto.getText().toString().trim();
                        VariablesRecepCiega.vCantProdTransferir =
                                "" + cantIngresada * Integer.parseInt(VariablesRecepCiega.vValFrac);
                        log.debug("VariablesRecepCiega.vCantIngreso: " + VariablesRecepCiega.vCantIngreso);
                        log.debug("VariablesRecepCiega.vValFrac: " + VariablesRecepCiega.vValFrac);
                        log.debug("VariablesRecepCiega.vCantProdTransferir: " +
                                  VariablesRecepCiega.vCantProdTransferir);
                        cerrarVentana(true);
                    }


                } else {
                    log.debug("Ingreso cantidad valida: " + txtCantidad.getText().toString());
                    String cantidad = txtCantidad.getText();
                    boolean productoFraccionado = false;
                    int fraccion = 0;

                    if (!VariablesRecepCiega.vValFrac.equals("1"))
                        productoFraccionado = true;

                    if (productoFraccionado) {
                        int cant = 0;
                        int frac = Integer.parseInt(VariablesRecepCiega.vValFrac.trim());
                        int valFrac = cant % frac;
                        int valCant = cant / frac;
                        fraccion = valFrac;

                    } else {
                        fraccion = 0;
                    }

                    VariablesRecepCiega.vCantIngreso = Integer.parseInt(cantidad);
                    VariablesRecepCiega.vLote = txtLote.getText().toString().trim();
                    VariablesRecepCiega.vFechaVcto = txtFechaVcto.getText().toString().trim();
                    VariablesRecepCiega.vCantProdTransferir =
                            "" + Integer.parseInt(cantidad) * Integer.parseInt(VariablesRecepCiega.vValFrac);
                    log.debug("VariablesRecepCiega.vCantIngreso: " + VariablesRecepCiega.vCantIngreso);
                    log.debug("VariablesRecepCiega.vValFrac: " + VariablesRecepCiega.vValFrac);
                    log.debug("VariablesRecepCiega.vCantProdTransferir: " + VariablesRecepCiega.vCantProdTransferir);

                    if (!existeProductoEnLista()) {
                        if (!existeStockDisponible(VariablesRecepCiega.vCodProd, VariablesRecepCiega.vCantIngreso,
                                                   VariablesRecepCiega.vLote)) {
                            FarmaUtility.showMessage(this, "No existe stock disponible", txtCantidad);
                        } else {

                            int cant_total_trans = obtieneCantidadTotalTrans();
                            log.info("cant_total_trans:" + cant_total_trans);
                            if (cant_total_trans == 0) {
                                /*actualizaStkComprometidoProd(VariablesRecepCiega.vCodProd,
                                                                 VariablesRecepCiega.vCantIngreso,
                                                                 ConstantsInventario.INDICADOR_A,
                                                                 ConstantsInventario.TIP_OPERACION_RESPALDO_SUMAR,
                                                                 Integer.parseInt(VariablesRecepCiega.vCantProdTransferir)
                                                            );*/
                                actualizaStkComprometidoProd_02(VariablesRecepCiega.vCodProd, //ASOSA, 21.07.2010
                                        VariablesRecepCiega.vCantIngreso, ConstantsInventario.INDICADOR_A,
                                        ConstantsInventario.TIP_OPERACION_RESPALDO_SUMAR,
                                        Integer.parseInt(VariablesRecepCiega.vCantProdTransferir), "");

                            } else {

                                //Obtiendo la cantidad Entero total de los productos
                                //para modificar el pblRespaldoStock
                                VariablesRecepCiega.vCantIngreso = cant_total_trans + Integer.parseInt(cantidad);
                                VariablesRecepCiega.vLote = txtLote.getText().toString().trim();
                                VariablesRecepCiega.vFechaVcto = txtFechaVcto.getText().toString().trim();
                                VariablesRecepCiega.vCantProdTransferir =
                                        "" + VariablesRecepCiega.vCantIngreso * Integer.parseInt(VariablesRecepCiega.vValFrac);
                                VariablesRecepCiega.vCantIngreso = Integer.parseInt(cantidad);
                                log.debug("INGRESA OTRO LOTE VariablesRecepCiega.vCantIngreso: " +
                                          VariablesRecepCiega.vCantIngreso);
                                log.debug("INGRESA OTRO LOTEVariablesRecepCiega.vValFrac: " +
                                          VariablesRecepCiega.vValFrac);
                                log.debug("INGRESA OTRO LOTEVariablesRecepCiega.vCantProdTransferir: " +
                                          VariablesRecepCiega.vCantProdTransferir);

                                /*actualizaStkComprometidoProd(VariablesRecepCiega.vCodProd,
                                                                 VariablesRecepCiega.vCantIngreso,
                                                                 ConstantsInventario.INDICADOR_A,
                                                                 ConstantsInventario.TIP_OPERACION_RESPALDO_ACTUALIZAR,
                                                                 Integer.parseInt(VariablesRecepCiega.vCantProdTransferir)); */
                                actualizaStkComprometidoProd_02(VariablesRecepCiega.vCodProd, //ASOSA, 21.07.2010
                                        VariablesRecepCiega.vCantIngreso, ConstantsInventario.INDICADOR_A,
                                        ConstantsInventario.TIP_OPERACION_RESPALDO_ACTUALIZAR,
                                        Integer.parseInt(VariablesRecepCiega.vCantProdTransferir), secRespaldo);
                            }

                            cerrarVentana(true);
                        }
                    }
                }


            }
        }

    }


    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private boolean existeProductoEnLista() {
        boolean encontrado = false;
        boolean valor = false;
        int i = 0;
        String codProd = "";
        String codLote = "";
        if (VariablesRecepCiega.vTableModelProdTranf != null) {
            while (!encontrado && i < VariablesRecepCiega.vTableModelProdTranf.getRowCount()) {
                codProd = (String)VariablesRecepCiega.vTableModelProdTranf.getValueAt(i, 5);
                codLote = (String)VariablesRecepCiega.vTableModelProdTranf.getValueAt(i, 3);
                if (codProd.equalsIgnoreCase(VariablesRecepCiega.vCodProd) &&
                    codLote.equalsIgnoreCase(VariablesRecepCiega.vLote))
                    encontrado = true;
                if (!encontrado)
                    i++;
                log.debug("codProd-->" + codProd);
                log.debug("codLote-->" + codLote);
            }

            if (encontrado) {
                valor = encontrado;
                FarmaUtility.showMessage(this, "Ya existe el producto y lote ingresado.\nPor favor verifique.",
                                         txtCantidad);
            }


        }
        return valor;
    }

    private void actualizaStkComprometidoProd(String pCodigoProducto, int pCantidadEntero, String pTipoStkComprometido,
                                              String pTipoRespaldoStock, int pCantidadTrans) {
        try {
            DBInventario.actualizaStkComprometidoProd(pCodigoProducto,
                                                      pCantidadEntero * Integer.parseInt(VariablesRecepCiega.vValFrac),
                                                      pTipoStkComprometido);
            DBPtoVenta.ejecutaRespaldoStock(pCodigoProducto, "", pTipoRespaldoStock, pCantidadTrans,
                                            Integer.parseInt(VariablesRecepCiega.vValFrac),
                                            ConstantsPtoVenta.MODULO_TRANSFERENCIA);
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this,
                                     "Error al Actualizar Stock del Producto.\n Ponganse en contacto con el area de Sistemas\n" +
                    sql.getMessage(), txtCantidad);
        }
    }

    private boolean datosValidados() {
        boolean rpta = true;
        if (txtCantidad.getText().trim().length() < 1) {
            FarmaUtility.showMessage(this, "Ingrese la cantidad", txtCantidad);
            rpta = false;
        }
        /**
             * JMIRANDA 08.01.10
             * validar que no sea cero
             * */
        else if (txtCantidad.getText().trim().equalsIgnoreCase("0")) {
            FarmaUtility.showMessage(this, "No puede transferir 0 unidades.", txtCantidad);
            rpta = false;
        }
        /*else if (txtLote.getText().trim().length()<1){
            FarmaUtility.showMessage(this, "Ingrese el lote", txtLote);
            rpta = false;
        }*/ else if (txtFechaVcto.getText().trim().length() < 1) {
            if (UtilityRecepCiega.indNoTieneFechaSap(VariablesRecepCiega.vNumIngreso, VariablesRecepCiega.vCodProd)) {
                //deja pasar
                rpta = true;
            } else {
                FarmaUtility.showMessage(this, "Ingrese fecha", txtFechaVcto);
                rpta = false;
            }
        } else if (!validaCantidad()) {
            rpta = false;
        } else if (txtFechaVcto.getText().trim().length() == 0) {
            if (!UtilityRecepCiega.indNoTieneFechaSap(VariablesRecepCiega.vNumIngreso, VariablesRecepCiega.vCodProd)) {
                rpta = false;
                FarmaUtility.showMessage(this, "Ingrese fecha valida.", txtFechaVcto);
            } else {
                rpta = true;
            }

        }
        /*else if(!UtilityRecepCiega.validarFecha(txtFechaVcto.getText().trim()) ||
        !validaFecha(txtFechaVcto.getText().trim(), "")){
             rpta = false;
            FarmaUtility.showMessage(this, "Ingrese fecha valida.", txtFechaVcto);
        }*/
        /*else if(!UtilityRecepCiega.indFechaVencTransf(VariablesRecepCiega.vCodProd,txtFechaVcto.getText().trim())
        && VariablesRecepCiega.vMotivoTransferencia.equalsIgnoreCase("F1")){
            FarmaUtility.showMessage(this, "El producto no está fuera de política de canje.", txtFechaVcto);
            rpta = false;
        }*/
        //JMIRANDA 22.03.2010 VALIDA PROD
        else if (!UtilityRecepCiega.indFechaCanjeProd(VariablesRecepCiega.vCodProd, txtFechaVcto.getText().trim(),
                                                      txtLote.getText().trim()) &&
                 VariablesRecepCiega.vMotivoTransferencia.equalsIgnoreCase("F1")) {
            FarmaUtility.showMessage(this, "El producto no está fuera de política de canje.", txtFechaVcto);
            rpta = false;
        }
        //JMIRANDA 21.03.2010
        /*else if(!UtilityRecepCiega.indLoteValido(VariablesRecepCiega.vNumIngreso,
                    VariablesRecepCiega.vCodProd,txtLote.getText().toUpperCase().trim())){
            //valido si lote es correcto
            rpta = false;
            FarmaUtility.showMessage(this, "El lote no es correcto.", txtFechaVcto);
        }*/
        /*
        /**
         * JMIRANDA 11.02.10
         * VALIDA FECHA TRANSF
         * * /
        else if(!UtilityRecepCiega.indFechaVencTransf(VariablesRecepCiega.vCodProd,txtFechaVcto.getText().trim())
        && VariablesRecepCiega.vMotivoTransferencia.equalsIgnoreCase("F1")){
            FarmaUtility.showMessage(this, "El producto no está fuera de política de canje.", txtFechaVcto);
            rpta = false;
        }
        else{
          try
          {
            String fecha = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            Date sysdate = FarmaUtility.getStringToDate(fecha,"dd/MM/yyyy"),
                fechaVec = FarmaUtility.getStringToDate(txtFechaVcto.getText().trim(),"dd/MM/yyyy");
            //JMIRANDA 15.02.10
            /*if(!sysdate.before(fechaVec))
            {
              FarmaUtility.showMessage(this,"La Fecha de Vencimiento debe ser posterior a la fecha de hoy: "+fecha+" ."
              ,txtFechaVcto);
              rpta = false;
            } * /
          }catch(SQLException sql)
          {
           log.error("",sql);
           FarmaUtility.showMessage(this,"Error al obtener la fecha del sistema : \n" + sql.getMessage(),txtFechaVcto);
          }
      * /
    //}  / *if(!FarmaUtility.isFechaValida(this,txtFechaVcto.getText().trim(),"Ingrese una fecha correcta")){
            rpta = false;
        }*/
        return rpta;
    }

    private void mostrarDatos() {

        if (VariablesRecepCiega.vIndModificarIngresoCantProdTranf) {
            lblProducto.setText(VariablesRecepCiega.vDesc_Producto);
            lblUnidad.setText(VariablesRecepCiega.vUnidad);
            txtCantidad.setText(VariablesRecepCiega.vCantIngreso + "");
            txtLote.setText(VariablesRecepCiega.vLote);
            txtFechaVcto.setText(VariablesRecepCiega.vFechaVcto);
            txtLote.setEditable(false);
            txtFechaVcto.setEditable(false);
        } else if (VariablesRecepCiega.vIndBuscaProducto) {
            this.lblProducto.setText(VariablesRecepCiega.vDesc_Producto);
            this.lblUnidad.setText(VariablesRecepCiega.vUnidad);
            txtCantidad.setText("");
            txtFechaVcto.setText("");
            txtLote.setText("");
        } else {
            ArrayList myArray = new ArrayList();
            try {
                DBRecepCiega.obtieneInfoProducto(myArray, VariablesRecepCiega.vCodProd);
            } catch (SQLException sql) {
                log.error("", sql);
                FarmaUtility.showMessage(this, "Ocurrió un error al obtener los datos del producto.\n" +
                        sql.getMessage(), null);
            }

            if (myArray.size() == 1) {
                VariablesRecepCiega.vDesc_Producto = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
                VariablesRecepCiega.vUnidad = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
                VariablesRecepCiega.vValPrecVta = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
                VariablesRecepCiega.vValFrac = ((String)((ArrayList)myArray.get(0)).get(3)).trim();
                this.lblProducto.setText(VariablesRecepCiega.vDesc_Producto);
                this.lblUnidad.setText(VariablesRecepCiega.vUnidad);
            }
        }
    }

    private boolean existeStockDisponible(String codProd, int cantidad, String lote) {
        boolean valor = false;
        int i = 0;
        int cant_prod = 0;
        int cant_total = 0;
        String codLote;
        if (VariablesRecepCiega.vTableModelProdTranf != null) {
            while (i < VariablesRecepCiega.vTableModelProdTranf.getRowCount()) {
                codProd = (String)VariablesRecepCiega.vTableModelProdTranf.getValueAt(i, 5);
                codLote = (String)VariablesRecepCiega.vTableModelProdTranf.getValueAt(i, 3);
                if (codProd.equalsIgnoreCase(VariablesRecepCiega.vCodProd) && !codLote.equalsIgnoreCase(lote)) {
                    log.debug("codProd -- cant_prod-->" + codProd + "  " +
                              FarmaUtility.getValueFieldArrayList(VariablesRecepCiega.vTableModelProdTranf.data, i,
                                                                  2));
                    cant_prod =
                            cant_prod + Integer.parseInt(FarmaUtility.getValueFieldArrayList(VariablesRecepCiega.vTableModelProdTranf.data,
                                                                                             i, 2));
                }
                i++;
            }
            log.debug("cant_prod total existeStockDisponible:" + cant_prod);
        }
        cant_total = cant_prod + cantidad;
        try {
            valor = DBRecepCiega.verificaStockDisponible(VariablesRecepCiega.vCodProd, cant_total + "");
        } catch (SQLException sql) {
            valor = false;
            log.error("", sql);
            FarmaUtility.showMessage(this, "No existe stock disponible.\n" +
                    sql.getMessage(), null);
        }
        return valor;
    }

    private int obtieneCantidadTotalTrans() {
        int i = 0;
        int cant_prod = 0;
        String codProd;

        if (VariablesRecepCiega.vTableModelProdTranf != null) {
            log.info("VariablesRecepCiega.vTableModelProdTranf: " + VariablesRecepCiega.vTableModelProdTranf.data);
            while (i < VariablesRecepCiega.vTableModelProdTranf.getRowCount()) {
                codProd = (String)VariablesRecepCiega.vTableModelProdTranf.getValueAt(i, 5);
                if (codProd.equalsIgnoreCase(VariablesRecepCiega.vCodProd)) {
                    log.info("codProd -- cant_prod-->" + codProd + "  " +
                             FarmaUtility.getValueFieldArrayList(VariablesRecepCiega.vTableModelProdTranf.data, i, 2));
                    cant_prod =
                            cant_prod + Integer.parseInt(FarmaUtility.getValueFieldArrayList(VariablesRecepCiega.vTableModelProdTranf.data,
                                                                                             i, 2));
                }
                i++;
            }
            log.info("cant_prod total " + cant_prod);
        }
        return cant_prod;
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void txtFechaVcto_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCantidad, e);
    }

    private void txtFechaVcto_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaVcto, e);
    }


    private void btnCantidad_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCantidad);
    }

    private void btnLote_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtLote);
    }

    private void btnFechaVcto_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFechaVcto);
    }

    private boolean validaCantidad() {
        boolean rpta = false;
        try {
            if (FarmaUtility.isInteger(txtCantidad.getText().trim())) {
                if (Integer.parseInt(txtCantidad.getText().trim()) > 0) {
                    rpta = true;
                }
            } else {
                FarmaUtility.showMessage(this, "Ingrese Cantidad Correcta", txtCantidad);
                rpta = false;
            }
        } catch (Exception e) {
            FarmaUtility.showMessage(this, "Ingrese Cantidad Correcta", txtCantidad);
            rpta = false;
        }
        return rpta;
    }
    //JMIRANDA VALIDA FECHA

    private boolean validaFecha(String pFecha, String pHora) {
        //pFecha.trim().equalsIgnoreCase("");
        boolean flag = false;
        Date fec = null;
        if (pHora.trim().equalsIgnoreCase("")) {
            pHora = "00:00:00";
        }
        try {
            if (FarmaUtility.isFechaValida(pFecha)) {
                fec = FarmaUtility.obtiene_fecha(pFecha, pHora);
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /****************************************** ASOSA, 21.07.2010 ********************************************************/

    //copiado para ser modificado unicamente lo de stkcomprometido asumiendo que lo demas esta bien
    private void actualizaStkComprometidoProd_02(String pCodigoProducto, int pCantidadEntero,
                                                 String pTipoStkComprometido, String pTipoRespaldoStock,
                                                 int pCantidadTrans, String secRespaldo) {
        VariablesRecepCiega.secRepStk = "0";
        /*
        try {
            VariablesRecepCiega.secRepStk=""; //ASOSA, 26.08.2010
            VariablesRecepCiega.secRepStk=DBVentas.operarResStkAntesDeCobrar(pCodigoProducto,   //ASOSA, 21.07.2010
                                                                             String.valueOf(pCantidadTrans),
                                                                             VariablesRecepCiega.vValFrac,
                                                                             secRespaldo,
                                                                             ConstantsPtoVenta.MODULO_TRANSFERENCIA);

            FarmaUtility.aceptarTransaccion();
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("",sql);
            FarmaUtility.showMessage(this,
                                     "Error al Actualizar Stock del Producto.\n Ponganse en contacto con el area de Sistemas\n" +
                    sql.getMessage(), txtCantidad);
        }
        */
    }

}
