package mifarma.ptoventa.inventario;


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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JDialog;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.ventas.reference.DBVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgGuiaIngresoCantidad extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgGuiaIngresoCantidad.class);

    Frame myParentFrame;
    private boolean productoFraccionado = false;

    private String descUnidPres = "";
    private String stkProd = "";
    private String valFracProd = "";
    private String descUnidVta = "";

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnlHeader1 = new JPanelHeader();
    private JLabelWhite lblDescripcion_T = new JLabelWhite();
    private JLabelWhite lblStockActual_T = new JLabelWhite();
    private JLabelWhite lblLaboratorio_T = new JLabelWhite();
    private JLabelWhite lblStockActual = new JLabelWhite();
    private JLabelWhite lblLaboratorio = new JLabelWhite();
    private JLabelWhite lblDescripcion = new JLabelWhite();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel btnEntero = new JButtonLabel();
    private JTextFieldSanSerif txtEntero = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtPrecioUnitario = new JTextFieldSanSerif();
    private JLabelWhite lblPrecioUnitario_T = new JLabelWhite();
    private JTextFieldSanSerif txtFechaVencimiento = new JTextFieldSanSerif();
    private JLabelWhite lblFechaVenc_T = new JLabelWhite();
    private JTextFieldSanSerif txtLote = new JTextFieldSanSerif();
    private JLabelWhite lblLote = new JLabelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JTextFieldSanSerif txtFraccion = new JTextFieldSanSerif();
    private JLabelOrange lblFrac_T = new JLabelOrange();
    private JLabelOrange lblValorFrac_T = new JLabelOrange();
    private JLabelOrange lblValorFrac = new JLabelOrange();
    private JLabelOrange lblUnidPresent_T = new JLabelOrange();
    private JLabelOrange lblUnidVenta_T = new JLabelOrange();
    private JLabelOrange lblUnidPresent = new JLabelOrange();
    private JLabelOrange lblUnidVenta = new JLabelOrange();
    private JTextFieldSanSerif txtPrecioTotal = new JTextFieldSanSerif();
    private JLabelOrange lblPrecioTotal_T = new JLabelOrange();
    private JLabelOrange lblFraccion = new JLabelOrange();
    private JLabelOrange lblEntero = new JLabelOrange();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgGuiaIngresoCantidad() {
        this(null, "", false);
    }

    public DlgGuiaIngresoCantidad(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(428, 381));
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
        pnlHeader1.setBounds(new Rectangle(10, 10, 400, 150));
        pnlHeader1.setBackground(Color.white);
        pnlHeader1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        lblDescripcion_T.setText("Descripción:");
        lblDescripcion_T.setBounds(new Rectangle(10, 15, 75, 15));
        lblDescripcion_T.setForeground(new Color(255, 130, 14));
        lblStockActual_T.setText("Stock Actual:");
        lblStockActual_T.setBounds(new Rectangle(10, 90, 75, 15));
        lblStockActual_T.setForeground(new Color(255, 130, 14));
        lblLaboratorio_T.setText("Laboratorio:");
        lblLaboratorio_T.setBounds(new Rectangle(10, 65, 70, 15));
        lblLaboratorio_T.setForeground(new Color(255, 130, 14));
        lblStockActual.setBounds(new Rectangle(90, 90, 55, 15));
        lblStockActual.setFont(new Font("SansSerif", 0, 11));
        lblStockActual.setForeground(new Color(255, 130, 14));
        lblLaboratorio.setBounds(new Rectangle(90, 65, 280, 15));
        lblLaboratorio.setFont(new Font("SansSerif", 0, 11));
        lblLaboratorio.setForeground(new Color(255, 130, 14));
        lblDescripcion.setBounds(new Rectangle(90, 15, 280, 15));
        lblDescripcion.setFont(new Font("SansSerif", 0, 11));
        lblDescripcion.setForeground(new Color(255, 130, 14));
        pnlTitle1.setBounds(new Rectangle(10, 175, 400, 130));
        pnlTitle1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlTitle1.setBackground(Color.white);
        btnEntero.setText("Entero");
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
        txtPrecioUnitario.setBounds(new Rectangle(220, 90, 70, 20));
        txtPrecioUnitario.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtPrecioUnitario_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtPrecioUnitario_keyTyped(e);
            }
        });
        lblPrecioUnitario_T.setText("Precio Fraccion");
        lblPrecioUnitario_T.setBounds(new Rectangle(220, 70, 85, 15));
        lblPrecioUnitario_T.setForeground(new Color(255, 130, 14));
        txtFechaVencimiento.setBounds(new Rectangle(115, 90, 75, 20));
        txtFechaVencimiento.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaVencimiento_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaVencimiento_keyReleased(e);
            }
        });
        lblFechaVenc_T.setText("Fecha Venc.");
        lblFechaVenc_T.setBounds(new Rectangle(115, 70, 70, 15));
        lblFechaVenc_T.setForeground(new Color(255, 130, 14));
        txtLote.setBounds(new Rectangle(15, 90, 70, 20));
        txtLote.setLengthText(15);
        txtLote.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtLote_keyPressed(e);
            }
        });
        lblLote.setText("Lote");
        lblLote.setBounds(new Rectangle(15, 70, 70, 15));
        lblLote.setForeground(new Color(255, 130, 14));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(325, 315, 85, 20));
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(215, 315, 105, 20));
        txtFraccion.setBounds(new Rectangle(210, 40, 60, 20));
        txtFraccion.setLengthText(6);
        txtFraccion.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFraccion_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtFraccion_keyTyped(e);
            }
        });
        lblFrac_T.setText("Fraccion");
        lblFrac_T.setBounds(new Rectangle(210, 20, 50, 15));
        lblValorFrac_T.setText("Valor Frac:");
        lblValorFrac_T.setBounds(new Rectangle(10, 115, 70, 15));
        lblValorFrac.setBounds(new Rectangle(90, 115, 55, 15));
        lblValorFrac.setFont(new Font("SansSerif", 0, 11));
        lblUnidPresent_T.setText("Presentacion:");
        lblUnidPresent_T.setBounds(new Rectangle(170, 40, 79, 15));
        lblUnidVenta_T.setText("Unid. Venta:");
        lblUnidVenta_T.setBounds(new Rectangle(170, 90, 79, 15));
        lblUnidPresent.setBounds(new Rectangle(260, 40, 125, 15));
        lblUnidPresent.setFont(new Font("SansSerif", 0, 11));
        lblUnidVenta.setBounds(new Rectangle(260, 90, 125, 15));
        lblUnidVenta.setFont(new Font("SansSerif", 0, 11));
        txtPrecioTotal.setBounds(new Rectangle(320, 90, 70, 20));
        txtPrecioTotal.setEditable(false);
        lblPrecioTotal_T.setText("Precio Total");
        lblPrecioTotal_T.setBounds(new Rectangle(320, 70, 70, 15));
        lblFraccion.setBounds(new Rectangle(275, 40, 115, 20));
        lblFraccion.setFont(new Font("SansSerif", 0, 11));
        lblFraccion.setForeground(new Color(43, 141, 39));
        lblEntero.setBounds(new Rectangle(80, 40, 115, 20));
        lblEntero.setFont(new Font("SansSerif", 0, 11));
        lblEntero.setForeground(new Color(43, 141, 39));
        pnlHeader1.add(lblUnidVenta, null);
        pnlHeader1.add(lblUnidPresent, null);
        pnlHeader1.add(lblUnidVenta_T, null);
        pnlHeader1.add(lblUnidPresent_T, null);
        pnlHeader1.add(lblValorFrac, null);
        pnlHeader1.add(lblValorFrac_T, null);
        pnlHeader1.add(lblDescripcion, null);
        pnlHeader1.add(lblLaboratorio, null);
        pnlHeader1.add(lblStockActual, null);
        pnlHeader1.add(lblLaboratorio_T, null);
        pnlHeader1.add(lblStockActual_T, null);
        pnlHeader1.add(lblDescripcion_T, null);
        pnlTitle1.add(lblEntero, null);
        pnlTitle1.add(lblFraccion, null);
        pnlTitle1.add(lblPrecioTotal_T, null);
        pnlTitle1.add(txtPrecioTotal, null);
        pnlTitle1.add(lblFrac_T, null);
        pnlTitle1.add(txtFraccion, null);
        pnlTitle1.add(lblLote, null);
        pnlTitle1.add(txtLote, null);
        pnlTitle1.add(lblFechaVenc_T, null);
        pnlTitle1.add(txtFechaVencimiento, null);
        pnlTitle1.add(lblPrecioUnitario_T, null);
        pnlTitle1.add(txtPrecioUnitario, null);
        pnlTitle1.add(txtEntero, null);
        pnlTitle1.add(btnEntero, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(pnlHeader1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        //
        txtEntero.setLengthText(6);
        txtFechaVencimiento.setLengthText(10);
        txtPrecioUnitario.setLengthText(9); //<100000.000
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initCabecera();
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initCabecera() {
        muestraInfoProd();

        lblDescripcion.setText(VariablesInventario.vNomProd);
        try {
            lblStockActual.setText(DBInventario.getStkDisponibleProd(VariablesInventario.vCodProd));
        } catch (SQLException sql) {
            lblStockActual.setText(VariablesInventario.vStkFisico);
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener el stock disponible del producto : \n" +
                    sql.getMessage(), txtEntero);
        }

        lblLaboratorio.setText(VariablesInventario.vNomLab);
        lblValorFrac.setText(VariablesInventario.vValFrac_Guia);

        if (!VariablesInventario.vValFrac_Guia.equals("1"))
            productoFraccionado = true;

        if (productoFraccionado) {
            int cant = 0;
            if (!VariablesInventario.vCant.trim().equals("")) {
                cant = Integer.parseInt(VariablesInventario.vCant.trim());
            }
            int frac = Integer.parseInt(VariablesInventario.vValFrac_Guia.trim());

            int valFrac = cant % frac;
            int valCant = cant / frac;
            txtEntero.setText(valCant + "");
            txtFraccion.setText(valFrac + "");
        } else {
            txtEntero.setText(VariablesInventario.vCant);
            txtFraccion.setText("0");
            txtFraccion.setEditable(false);
            //ERIOS 14.07.2015 Se oculta ingreso de fraccion
            lblFrac_T.setVisible(false);
            txtFraccion.setVisible(false);
            lblFraccion.setVisible(false);
            lblPrecioUnitario_T.setText("Precio Entero");
        }

        txtLote.setText(VariablesInventario.vLote);
        txtFechaVencimiento.setText(VariablesInventario.vFechaVec);

        if (VariablesInventario.vPrecUnit.trim().equals("")) {
            txtPrecioUnitario.setText("0.00");
        } else {
            txtPrecioUnitario.setText(VariablesInventario.vPrecUnit);
        }
        
        calculaPrecioTotal();

    }
    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void btnEntero_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtEntero);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        if (!productoFraccionado) {
            txtFraccion.setEditable(false);
        }
        FarmaUtility.moveFocus(txtEntero);
    }

    private void txtEntero_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!txtFraccion.isEditable()) {
                FarmaUtility.moveFocus(txtLote);
            } else {
                FarmaUtility.moveFocus(txtFraccion);
            }
            calculaPrecioTotal();
        } else
            chkKeyPressed(e);
    }

    private void txtEntero_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtEntero, e);
    }

    private void txtFraccion_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtLote);
            calculaPrecioTotal();
        } else
            chkKeyPressed(e);
    }

    private void txtFraccion_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtFraccion, e);
    }

    private void txtLote_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtFechaVencimiento);
        } else
            chkKeyPressed(e);
    }

    private void txtFechaVencimiento_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtPrecioUnitario);
        else
            chkKeyPressed(e);
    }

    private void txtFechaVencimiento_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaVencimiento, e);
    }

    private void txtPrecioUnitario_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(txtEntero);
            calculaPrecioTotal();
        }else
            chkKeyPressed(e);
    }

    private void txtPrecioUnitario_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtPrecioUnitario, e);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F11(e)) {
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
        if (txtEntero.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Debe Ingresar la Cantidad.", txtEntero);
            retorno = false;
        } else if (txtFraccion.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Debe Ingresar la fraccion.", txtFraccion);
            retorno = false;
        } else if (txtLote.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Debe Ingresar el Lote.", txtLote);
            retorno = false;
        } else if (txtFechaVencimiento.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Debe Ingresar la Fecha de Vencimiento.", txtFechaVencimiento);
            retorno = false;
        } else if (!FarmaUtility.validaFecha(txtFechaVencimiento.getText().trim(), "dd/MM/yyyy")) {
            FarmaUtility.showMessage(this, "Corrija la Fecha de Vencimiento.", txtFechaVencimiento);
            retorno = false;
        } else if (txtPrecioUnitario.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Debe Ingresar el Precio Unitario.", txtPrecioUnitario);
            retorno = false;
        } else if (!FarmaUtility.validateDecimal(this, txtPrecioUnitario, "Corrija el Precio Unitario.", false))
            retorno = false;
        else if (Double.parseDouble(txtPrecioUnitario.getText().trim()) >= 100000.000) {
            FarmaUtility.showMessage(this, "El Precio Unitario, no debe ser mayor de 99,999.999 .", txtPrecioUnitario);
            retorno = false;
        } else {
            try {
                String fecha = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
                Date sysdate = FarmaUtility.getStringToDate(fecha, "dd/MM/yyyy"), fechaVec =
                    FarmaUtility.getStringToDate(txtFechaVencimiento.getText().trim(), "dd/MM/yyyy");
                if (!sysdate.before(fechaVec)) {
                    FarmaUtility.showMessage(this,
                                             "La Fecha de Vencimiento debe ser posterior a la fecha de hoy: " + fecha +
                                             " .", txtFechaVencimiento);
                    retorno = false;
                }
            } catch (SQLException sql) {
                log.error("", sql);
                FarmaUtility.showMessage(this, "Error al obtener la fecha del sistema : \n" +
                        sql.getMessage(), txtEntero);
            }

        }

        if (VariablesInventario.vTipoOrigen.equals(ConstantsPtoVenta.LISTA_MAESTRO_COMPETENCIA) && retorno)
            if (Double.parseDouble(txtPrecioUnitario.getText()) < 0.01) {
                FarmaUtility.showMessage(this, "Debe ingresar un Precio Mayor de 0.01", txtPrecioUnitario);
                retorno = false;
            }
        return retorno;
    }

    private void cargarDatos() {
        VariablesInventario.vCant = "" + VariablesInventario.vCantIngreso;
        VariablesInventario.vLote = txtLote.getText().trim();
        VariablesInventario.vFechaVec = txtFechaVencimiento.getText().trim();
        VariablesInventario.vPrecUnit = txtPrecioUnitario.getText().trim();

        int cant = Integer.parseInt(VariablesInventario.vCant);
        double precio = Double.parseDouble(VariablesInventario.vPrecUnit);
        VariablesInventario.vTotal = (cant * precio) + "";
    }

    private void aceptaCantidadIngresada() {
        if (validarCampos()) {
            if (!validaCantIngreso()) {                
                return;
            }
            //ERIOS 14.07.2015 Validacion precio
            if(!validaPrecioCompetencia(VariablesInventario.vCodProd)){
                return;
            }
            cargarDatos();
            cerrarVentana(true);
        }
    }

    private boolean validaCantIngreso() {
        boolean valor = true;

        calculaCantidadIngreso();

        if (VariablesInventario.vCantIngreso > 999999) {
            valor = false;
            FarmaUtility.showMessage(this, "La cantidad a ingresar excede el valor máximo de ingreso.", txtEntero);
        }else if(VariablesInventario.vCantIngreso == 0){
            valor = false;
            FarmaUtility.showMessage(this, "Debe ingresar una cantidad valida.", txtEntero);
        }else if (txtFraccion.getText().trim().equalsIgnoreCase(VariablesInventario.vValFrac_Guia)) {
            valor = false;
            FarmaUtility.showMessage(this, "La cantidad de Fraccion no puede ser igual al Valor de Fraccion",
                                     txtEntero);
        } else if (Integer.parseInt(txtFraccion.getText().trim()) > Integer.parseInt(VariablesInventario.vValFrac_Guia)) {
            valor = false;
            FarmaUtility.showMessage(this, "La cantidad de Fraccion ingresada no puede ser mayor al valor de Fraccion",
                                     txtFraccion);
        }

        return valor;
    }

    private void muestraInfoProd() {

        ArrayList myArray = new ArrayList();
        obtieneInfoProdEnArrayList(myArray);

        if (myArray.size() == 1) {
            stkProd = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
            descUnidPres = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
            valFracProd = ((String)((ArrayList)myArray.get(0)).get(2)).trim();
            descUnidVta = ((String)((ArrayList)myArray.get(0)).get(6)).trim();
        } else {
            stkProd = "";
            descUnidPres = "";
            valFracProd = "";
            descUnidVta = "";
            FarmaUtility.showMessage(this, "Error al obtener Informacion del Producto", txtEntero);
        }

        lblStockActual.setText(stkProd);
        lblUnidPresent.setText(descUnidPres);
        VariablesInventario.vValFrac_Guia = valFracProd;
        //lblValorFrac.setText(valFracProd);
        lblUnidVenta.setText(descUnidVta);
        //ERIOS 14.07.2015 Muestra descripcion de entero y fraccion
        lblEntero.setText(descUnidPres);        
        lblFraccion.setText(descUnidVta);
    }

    private void obtieneInfoProdEnArrayList(ArrayList pArrayList) {
        String codProd = VariablesInventario.vCodProd;
        try {
            DBVentas.obtieneInfoProducto(pArrayList, codProd);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener informacion del producto en Arreglo. \n" +
                    sql.getMessage(), txtEntero);
        }
    }

    private boolean validaPrecioCompetencia(String pCodProd) {
        boolean bRetorno = true;
        double precio = calculaPrecioTotal();
        if (VariablesInventario.vTipoOrigen.equals(ConstantsPtoVenta.LISTA_MAESTRO_COMPETENCIA)){
            String vRetorno;
            try {
                vRetorno = DBInventario.validaPrecioCompetencia(pCodProd,precio/VariablesInventario.vCantIngreso);
            } catch (SQLException e) {
                log.error("",e);
                vRetorno = e.getMessage();
            }
            if(!vRetorno.equals("S")){
                bRetorno = false;
                FarmaUtility.showMessage(this, vRetorno, txtPrecioUnitario);
            }
        }
        return bRetorno;
    }

    /**
     * Muestra precio total
     * @author ERIOS
     * @since 14.07.2015
     */
    private double calculaPrecioTotal() {
        calculaCantidadIngreso();
        
        double precio = FarmaUtility.getDecimalNumber(txtPrecioUnitario.getText().trim());
        double precioTotal = VariablesInventario.vCantIngreso * precio;
        txtPrecioTotal.setText(FarmaUtility.formatNumber(precioTotal));
        
        return precioTotal;
    }

    private void calculaCantidadIngreso() {
        String cantidad, fraccion;
        cantidad = txtEntero.getText().trim();
        fraccion = txtFraccion.getText().trim();

        if (cantidad.equals(""))
            cantidad = "0";
        if (fraccion.equals(""))
            fraccion = "0";
        VariablesInventario.vCantIngreso =
                Integer.parseInt(cantidad) * Integer.parseInt(VariablesInventario.vValFrac_Guia.trim()) +
                Integer.parseInt(fraccion);
    }
}
