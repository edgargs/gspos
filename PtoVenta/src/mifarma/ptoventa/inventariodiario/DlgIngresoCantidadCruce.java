package mifarma.ptoventa.inventariodiario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventariodiario.reference.DBInvDiario;
import mifarma.ptoventa.inventariodiario.reference.VariablesInvDiario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgIngresoCantidadCruce extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgIngresoCantidadCruce.class);

    FarmaTableModel tableModelKardex;

    Frame myParentFrame;

    private BorderLayout borderLayout2 = new BorderLayout();
    private JTextFieldSanSerif txtCruce = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtCruceCant = new JTextFieldSanSerif();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JLabelFunction jLabelFunction2 = new JLabelFunction();

    private JLabel jLabel1 = new JLabel();
    private JLabel lblProducto = new JLabel();
    private JLabel lblDescripcion = new JLabel();
    private JLabel lblpresentacionCruce = new JLabel();
    private JLabel lblProductoCruce = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel lblPresentacion = new JLabel();

    private JButtonLabel lblDescripcion2 = new JButtonLabel();
    private JButtonLabel lblCantidadCruce = new JButtonLabel();
    private JButtonLabel lblCantidad = new JButtonLabel();
    private JButtonLabel lblAccion1 = new JButtonLabel();
    private JButtonLabel lblAccion2 = new JButtonLabel();
    private JButtonLabel btnRelacionMovimiento = new JButtonLabel();
    private JPanelTitle pnllTitle1 = new JPanelTitle();
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JComboBox cmbAccion1 = new JComboBox();
    private JComboBox cmbAccion2 = new JComboBox();
    private JLabel lblStockDisponible1 = new JLabel();
    private JLabel lblStockDisponible2 = new JLabel();
    private JLabel lblStockTitulo = new JLabel();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgIngresoCantidadCruce() {
        this(null, "", false);
    }

    public DlgIngresoCantidadCruce(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initCombo();
            //initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(681, 281));
        this.getContentPane().setLayout(borderLayout2);
        this.setTitle("Cruce de productos");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        jPanelWhite1.setBounds(new Rectangle(15, 40, 650, 175));
        jPanelWhite1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        jPanelWhite1.setLayout(null);
        jLabel1.setText("1. ");
        jLabel1.setBounds(new Rectangle(10, 30, 25, 15));
        jLabel1.setFont(new Font("SansSerif", 1, 11));
        jLabel1.setForeground(new Color(43, 141, 39));
        lblProducto.setText("914005");
        lblProducto.setBounds(new Rectangle(35, 30, 60, 15));
        lblProducto.setFont(new Font("SansSerif", 1, 11));
        lblProducto.setForeground(new Color(255, 130, 14));

        lblDescripcion.setText("Descripcion");
        lblDescripcion.setBounds(new Rectangle(120, 30, 225, 15));
        lblDescripcion.setFont(new Font("SansSerif", 1, 11));
        lblDescripcion.setForeground(new Color(255, 130, 14));


        lblDescripcion2.setText("Descripción");
        lblDescripcion2.setBounds(new Rectangle(120, 100, 210, 15));
        lblDescripcion2.setFont(new Font("SansSerif", 1, 11));
        lblDescripcion2.setForeground(new Color(255, 130, 14));

        jLabelFunction1.setBounds(new Rectangle(385, 225, 120, 20));
        jLabelFunction1.setText("[ F11 ] Aceptar");
        jLabelFunction2.setBounds(new Rectangle(530, 225, 120, 20));
        jLabelFunction2.setText("[ ESCAPE ] Cerrar");

        lblProductoCruce.setText("987654");
        lblProductoCruce.setBounds(new Rectangle(35, 100, 65, 15));
        lblProductoCruce.setFont(new Font("SansSerif", 1, 11));
        lblProductoCruce.setForeground(new Color(255, 130, 14));
        jLabel2.setText("2. ");
        jLabel2.setBounds(new Rectangle(10, 100, 30, 15));
        jLabel2.setFont(new Font("SansSerif", 1, 11));
        jLabel2.setForeground(new Color(43, 141, 39));
        lblpresentacionCruce.setText("presentación cruce");
        lblpresentacionCruce.setBounds(new Rectangle(35, 135, 335, 15));
        lblpresentacionCruce.setFont(new Font("SansSerif", 1, 11));
        lblpresentacionCruce.setForeground(new Color(255, 130, 14));

        cmbAccion1.setBounds(new Rectangle(550, 25, 85, 20));
        cmbAccion1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbAccion1_keyPressed(e);
            }
        });

        cmbAccion2.setBounds(new Rectangle(550, 95, 85, 20));
        cmbAccion2.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbAccion2_keyPressed(e);
            }
        });

        ///cantidad 1
        txtCruce.setBounds(new Rectangle(550, 60, 85, 20));
        txtCruce.setDocument(new FarmaLengthText(8));
        txtCruce.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCruce_keyPressed(e);
            }
            //public void keyTyped(KeyEvent e)
            //{
            //txtCantidad_keyTyped(e);
            //}
        });

        ///cantidad 2
        txtCruceCant.setBounds(new Rectangle(550, 130, 85, 20));
        txtCruceCant.setDocument(new FarmaLengthText(8));
        txtCruceCant.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCruceCant_keyPressed(e);
            }
            //public void keyTyped(KeyEvent e)
            //{
            //txtCantidad_keyTyped(e);
            //}
        });


        lblAccion1.setText("Acción :");
        lblAccion1.setBounds(new Rectangle(480, 95, 55, 20));
        lblAccion1.setFont(new Font("SansSerif", 1, 11));
        lblAccion1.setForeground(new Color(43, 141, 39));

        lblAccion2.setText("Acción :");
        lblAccion2.setBounds(new Rectangle(480, 30, 55, 15));
        lblAccion2.setFont(new Font("SansSerif", 1, 11));
        lblAccion2.setForeground(new Color(43, 141, 39));


        lblCantidadCruce.setText("Cantidad :");
        lblCantidadCruce.setBounds(new Rectangle(480, 130, 65, 20));
        lblCantidadCruce.setFont(new Font("SansSerif", 1, 11));
        lblCantidadCruce.setForeground(new Color(43, 141, 39));

        lblCantidad.setText("Cantidad :");
        lblCantidad.setBounds(new Rectangle(480, 60, 55, 20));
        lblCantidad.setFont(new Font("SansSerif", 1, 11));
        lblCantidad.setForeground(new Color(43, 141, 39));

        //lblStockDisponible1.setText("Stock 1");
        lblStockDisponible1.setBounds(new Rectangle(390, 50, 45, 15));
        lblStockDisponible1.setFont(new Font("SansSerif", 1, 11));
        lblStockDisponible1.setForeground(new Color(255, 130, 14));

        //lblStockDisponible2.setText("Stock 2");
        lblStockDisponible2.setBounds(new Rectangle(390, 115, 45, 15));
        lblStockDisponible2.setFont(new Font("SansSerif", 1, 11));
        lblStockDisponible2.setForeground(new Color(255, 130, 14));

        lblStockTitulo.setText("Stock Disponible");
        lblStockTitulo.setBounds(new Rectangle(370, 15, 105, 15));
        lblStockTitulo.setFont(new Font("SansSerif", 1, 11));
        lblStockTitulo.setForeground(new Color(43, 141, 39));

        lblPresentacion.setText("presentación");
        lblPresentacion.setBounds(new Rectangle(35, 60, 335, 15));
        lblPresentacion.setFont(new Font("SansSerif", 1, 11));
        lblPresentacion.setForeground(new Color(255, 130, 14));
        pnllTitle1.setBounds(new Rectangle(15, 15, 650, 25));
        btnRelacionMovimiento.setText("Productos a cruzar");
        btnRelacionMovimiento.setBounds(new Rectangle(5, 5, 215, 15));


        jPanelWhite1.add(lblPresentacion, null);
        jPanelWhite1.add(lblDescripcion2, null);
        jPanelWhite1.add(lblCantidadCruce, null);
        jPanelWhite1.add(lblAccion1, null);
        jPanelWhite1.add(lblCantidad, null);
        jPanelWhite1.add(lblAccion2, null);
        jPanelWhite1.add(txtCruce, null);
        jPanelWhite1.add(txtCruceCant, null);
        jPanelWhite1.add(cmbAccion1, null);
        jPanelWhite1.add(cmbAccion2, null);
        jPanelWhite1.add(jLabel2, null);
        jPanelWhite1.add(lblProductoCruce, null);
        jPanelWhite1.add(lblStockDisponible1, null);
        jPanelWhite1.add(lblStockDisponible2, null);
        jPanelWhite1.add(lblStockTitulo, null);
        jPanelWhite1.add(lblpresentacionCruce, null);
        jPanelWhite1.add(lblDescripcion, null);
        jPanelWhite1.add(lblProducto, null);
        jPanelWhite1.add(jLabel1, null);
        pnllTitle1.add(btnRelacionMovimiento, null);
        jContentPane.add(pnllTitle1, null);
        jContentPane.add(jLabelFunction2, null);
        jContentPane.add(jLabelFunction1, null);
        jContentPane.add(jPanelWhite1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************	

    private void cmbAccion1_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            if (cmbAccion1.getSelectedItem().equals("Resta"))
                cmbAccion2.setSelectedItem("Suma");
            else
                cmbAccion2.setSelectedItem("Resta");

            FarmaUtility.moveFocus(txtCruce);
        } else {
            chkKeyPressed(e);
        }
    }

    private void cmbAccion2_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtCruceCant);

            if (cmbAccion2.getSelectedItem().equals("Resta"))
                cmbAccion1.setSelectedItem("Suma");
            else
                cmbAccion1.setSelectedItem("Resta");


        } else {
            chkKeyPressed(e);
        }
    }

    private void initCombo() {
        cmbAccion1.addItem("Suma");
        cmbAccion1.addItem("Resta");
        cmbAccion2.addItem("Resta");
        cmbAccion2.addItem("Suma");
        txtCruceCant.setText("0");
        txtCruce.setText("0");
    }


    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(cmbAccion1);
        FarmaUtility.centrarVentana(this);
        mostrarDatos();

    }

    private void txtCruce_keyPressed(KeyEvent e) {

        int ValFracMax1 = 0;
        int ValFracMax2 = 0;

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            try {

                ValFracMax1 = DBInvDiario.ObtenerValFracMaxProd(VariablesInvDiario.vCodProd);
                ValFracMax2 = DBInvDiario.ObtenerValFracMaxProd(VariablesInvDiario.vCodigoAux);
                //log.debug("ValFracMax1" + ValFracMax1);
                //log.debug("ValFracMax2" + ValFracMax2);

            } catch (SQLException sql) {
                //FarmaUtility.showMessage(this,  "Ocurrió un error ", txtCruce);
            }


            //completar el monto dadas los valores de fraccion
            if (txtCruce.getText().trim().length() != 0 && txtCruce.isEditable() &&
                Integer.parseInt(txtCruce.getText()) > 0) {
                //verifica los valores de fraccion

                int mod =
                    (Integer.parseInt(txtCruce.getText()) * Integer.parseInt(VariablesInvDiario.vValFracAux) * ValFracMax1) %
                    (Integer.parseInt(VariablesInvDiario.vValFrac) * ValFracMax2);
                int div =
                    (Integer.parseInt(txtCruce.getText()) * Integer.parseInt(VariablesInvDiario.vValFracAux) * ValFracMax1) /
                    (Integer.parseInt(VariablesInvDiario.vValFrac) * ValFracMax2);

                //log.debug("DIV : " + mod);
                //log.debug("DIV : "  + div);

                if (cmbAccion1.getSelectedItem().equals("Suma")) {
                    //if( !(div  > VariablesInvDiario.vStockProdD) )
                    //{
                    if (mod == 0) {
                        txtCruceCant.setText(div + "");
                        FarmaUtility.moveFocus(cmbAccion2);
                    } else {
                        FarmaUtility.showMessage(this, "La cantidad ingresada no es correcta de acuerdo a la fracción",
                                                 txtCruce);
                        FarmaUtility.moveFocus(txtCruce);
                        txtCruceCant.setText("0");
                        txtCruce.setText("0");
                    }
                    //}
                    //else
                    //{
                    //    FarmaUtility.showMessage(this, "No hay stock suficiente", txtCruce);
                    //}
                }

                else {
                    //if( !(Double.parseDouble(txtCruce.getText())  > VariablesInvDiario.vStockProdD) )
                    //{

                    if (mod == 0) {
                        txtCruceCant.setText(div + "");
                        FarmaUtility.moveFocus(cmbAccion2);
                    } else {
                        FarmaUtility.showMessage(this, "La cantidad ingresada no es correcta de acuerdo a la fracción",
                                                 txtCruce);
                        FarmaUtility.moveFocus(txtCruce);
                        txtCruceCant.setText("0");
                        txtCruce.setText("0");
                    }

                    //}
                    //else
                    //{
                    //     FarmaUtility.showMessage(this, "No hay stock disponible para el producto 1", txtCruce);
                    //     txtCruceCant.setText("0");
                    //     txtCruce.setText("0");
                    //}

                }

            }

            else {
                FarmaUtility.showMessage(this, "Ingrese una cantidad valida", txtCruce);
                FarmaUtility.moveFocus(txtCruce);
            }


        } else {
            chkKeyPressed(e);
        }
    }

    private void txtCruceCant_keyPressed(KeyEvent e) {

        int ValFracMax1 = 0;
        int ValFracMax2 = 0;

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {


            try {

                ValFracMax1 = DBInvDiario.ObtenerValFracMaxProd(VariablesInvDiario.vCodProd);
                ValFracMax2 = DBInvDiario.ObtenerValFracMaxProd(VariablesInvDiario.vCodigoAux);
                //log.debug("ValFracMax1" + ValFracMax1);
                //log.debug("ValFracMax2" + ValFracMax2);

            } catch (SQLException sql) {
                //FarmaUtility.showMessage(this,  "Ocurrió un error ", txtCruce);
            }


            //completar el monto dados los valores de fraccion
            if (txtCruceCant.getText().trim().length() != 0 && txtCruceCant.isEditable() &&
                Integer.parseInt(txtCruceCant.getText()) > 0) {
                //verifica los valores de fraccion
                int mod =
                    (Integer.parseInt(txtCruceCant.getText()) * Integer.parseInt(VariablesInvDiario.vValFrac) * ValFracMax2) %
                    (Integer.parseInt(VariablesInvDiario.vValFracAux) * ValFracMax1);
                int div =
                    (Integer.parseInt(txtCruceCant.getText()) * Integer.parseInt(VariablesInvDiario.vValFrac) * ValFracMax2) /
                    (Integer.parseInt(VariablesInvDiario.vValFracAux) * ValFracMax1);


                if (cmbAccion1.getSelectedItem().equals("Resta")) {
                    //if( !(div  > VariablesInvDiario.vStockProdCruceD) )
                    //{
                    if (mod == 0) {

                        txtCruce.setText(div + "");
                        FarmaUtility.moveFocus(cmbAccion1);

                    } else {
                        FarmaUtility.showMessage(this, "La cantidad ingresada no es correcta de acuerdo a la fracción",
                                                 txtCruce);
                        FarmaUtility.moveFocus(txtCruce);
                        txtCruceCant.setText("0");
                        txtCruce.setText("0");
                    }
                    //}
                    //else
                    //{
                    //   FarmaUtility.showMessage(this, "No hay stock disponible para el producto 1", txtCruce);
                    //}
                } else {

                    //if(!(Double.parseDouble(txtCruceCant.getText())  > VariablesInvDiario.vStockProdCruceD))
                    //{

                    if (mod == 0) {

                        txtCruce.setText(div + "");
                        FarmaUtility.moveFocus(cmbAccion1);

                    } else {
                        FarmaUtility.showMessage(this, "La cantidad ingresada no es correcta de acuerdo a la fracción",
                                                 txtCruce);
                        FarmaUtility.moveFocus(txtCruce);
                        txtCruceCant.setText("0");
                        txtCruce.setText("0");
                    }
                    //}
                    //else
                    //{
                    //    FarmaUtility.showMessage(this, "No hay stock disponible para el producto 2", txtCruce);
                    //    FarmaUtility.moveFocus(txtCruce);
                    //}
                }

            } else {
                FarmaUtility.showMessage(this, "No hay stock suficiente", txtCruce);
                txtCruceCant.setText("0");
                txtCruce.setText("0");
            }

            chkKeyPressed(e);
        } else {
            chkKeyPressed(e);
        }
    }


    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            FarmaVariables.vAceptar = true;
            this.setVisible(false);
            this.dispose();
            e.consume();
            //cerrarVentana(true);
        }

        else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (datosValidados()) {
                try {
                    ingresarCantidad();
                    FarmaUtility.aceptarTransaccion();
                    cerrarVentana(false);
                } catch (SQLException sql) {
                    FarmaUtility.liberarTransaccion();
                    log.error("", sql);
                    FarmaUtility.showMessage(this, "Ocurrió un error al registrar la cantidad : \n" +
                            sql.getMessage(), txtCruce);
                    cerrarVentana(false);
                }
            }
        }
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************


    private void mostrarDatos() {
        lblProducto.setText(VariablesInvDiario.vCodProd);
        lblDescripcion.setText(VariablesInvDiario.vDesProd);
        lblPresentacion.setText(VariablesInvDiario.vUnidPresentacion + "                 Valor Frac. : " +
                                VariablesInvDiario.vValFrac);

        lblProductoCruce.setText(VariablesInvDiario.vCodigoAux);
        lblDescripcion2.setText(VariablesInvDiario.vNomProdAux);
        lblpresentacionCruce.setText(VariablesInvDiario.vUnidPresentaAux + "                 Valor Frac. :  " +
                                     VariablesInvDiario.vValFracAux);

        //mostrar stock disponible actualizado
        try {

            String stockusadoprod1 = DBInvDiario.obtenerStockUsadoCruce(VariablesInvDiario.vCodProd);
            String stockusadoprod2 = DBInvDiario.obtenerStockUsadoCruce(VariablesInvDiario.vCodigoAux);

            //log.debug(" stock de producto a cargar D : " + VariablesInvDiario.vStockProdD);
            //log.debug(" stock usado D : " + Double.parseDouble(stockusadoprod1));
            //log.debug(" suma D : " + (VariablesInvDiario.vStockProdD - Double.parseDouble(stockusadoprod1)));
            if ((VariablesInvDiario.vStockProdD - Double.parseDouble(stockusadoprod1)) > 0 &&
                (VariablesInvDiario.vStockProdCruceD - Double.parseDouble(stockusadoprod2)) > 0) {
                lblStockDisponible1.setText((VariablesInvDiario.vStockProdD - Double.parseDouble(stockusadoprod1)) +
                                            "");
                lblStockDisponible2.setText((VariablesInvDiario.vStockProdCruceD -
                                             Double.parseDouble(stockusadoprod2)) + "");

                VariablesInvDiario.vStockProdD = VariablesInvDiario.vStockProdD - Double.parseDouble(stockusadoprod1);
                VariablesInvDiario.vStockProdCruceD =
                        VariablesInvDiario.vStockProdCruceD - Double.parseDouble(stockusadoprod2);
            } else {
                log.debug("No hay stock disponible para cruce");
            }

        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al ingresar el stock usado de los cruce : \n" +
                    sql.getMessage(), txtCruce);
        }

    }

    private void ingresarCantidad() throws SQLException {

        String Cantidad = txtCruce.getText().trim();
        String Cantidadcruce = txtCruceCant.getText().trim();
        String AccionInicial = cmbAccion1.getSelectedItem().toString();
        String indStocComp = "";

        log.debug("TOMA INVENTARIO DIARIO: ENTRO AGREGA CRUCE" + VariablesInvDiario.vCodProd);

        try {

            if (AccionInicial.equalsIgnoreCase("Suma")) {
                indStocComp = DBInvDiario.obtenerIndStockComprometido(VariablesInvDiario.vCodigoAux, Cantidad);
            } else {
                indStocComp = DBInvDiario.obtenerIndStockComprometido(VariablesInvDiario.vCodProd, Cantidadcruce);
            }

            if (true) //indStocComp.equalsIgnoreCase("N"))
            {
                //String val1 =Integer.parseInt(Cantidad) * Integer.parseInt(VariablesInvDiario.vValFrac) + "";
                //String val2 =Integer.parseInt(Cantidadcruce) * Integer.parseInt(VariablesInvDiario.vValFracAux) + "";
                DBInvDiario.agregarCruce(VariablesInvDiario.vCodProd, VariablesInvDiario.vCodigoAux,
                                         VariablesInvDiario.vCodLabProdCruce, VariablesInvDiario.vUnidPresentacion,
                                         VariablesInvDiario.vUnidPresentaAux, VariablesInvDiario.vValFrac,
                                         VariablesInvDiario.vValFracAux, Cantidad, Cantidadcruce, AccionInicial);
                VariablesInvDiario.vAceptarCruce = true;
            } else {
                FarmaUtility.showMessage(this, "Ocurrió un error de stock comprometido ", txtCruce);
            }

        } catch (SQLException sql) {
            VariablesInvDiario.vAceptarCruce = false;
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al registrar el cruce : \n" +
                    sql.getMessage(), txtCruce);
        }

    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private boolean datosValidados() {
        boolean rpta = true;


        return rpta;
    }

}
