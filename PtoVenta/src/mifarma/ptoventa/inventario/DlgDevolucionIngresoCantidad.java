package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
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
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;

import mifarma.common.FarmaUtility;
import static mifarma.common.FarmaUtility.moveFocus;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.dto.NotaEsCabDetDTO;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgDevolucionIngresoCantidad.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      26.06.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgDevolucionIngresoCantidad extends JDialog implements KeyListener {

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgDevolucionIngresoCantidad.class);

    private Frame myParentFrame;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelHeader pnlHeader1 = new JPanelHeader();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JLabelWhite lblStock = new JLabelWhite();
    private JLabelWhite lblFechaHoraActual = new JLabelWhite();
    private JLabelWhite lblUnidades = new JLabelWhite();
    private JLabelWhite lblUnidadesD = new JLabelWhite();
    private JLabelWhite lblCodigo = new JLabelWhite();
    private JLabelWhite lblCodigoD = new JLabelWhite();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JLabelWhite lblDescripcion = new JLabelWhite();
    private JLabelWhite lblDescripcionD = new JLabelWhite();
    private JLabelWhite lblLaboratorio = new JLabelWhite();
    private JLabelWhite lblLaboratorioD = new JLabelWhite();
    private JLabelWhite lblUnidad = new JLabelWhite();
    private JLabelWhite lblUnidadD = new JLabelWhite();
    private JLabelWhite lblValorFrac = new JLabelWhite();
    private JLabelWhite lblValorFracD = new JLabelWhite();
    private JButtonLabel btnCantidad = new JButtonLabel();
    private JTextFieldSanSerif txtCantidad = new JTextFieldSanSerif();
    private JLabelWhite lblFraccion = new JLabelWhite();
    private JTextFieldSanSerif txtFraccion = new JTextFieldSanSerif();
    private JLabelWhite lblFechaVenc = new JLabelWhite();
    private JTextFieldSanSerif txtFechaVenc = new JTextFieldSanSerif();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelWhite lblLote = new JLabelWhite();
    private JTextFieldSanSerif txtLote = new JTextFieldSanSerif();

    private boolean productoFraccionado = false;
    private int cantInic = 0;
    private NotaEsCabDetDTO notaEsCabDetDTO = null;

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgDevolucionIngresoCantidad() {
        this(null, "", false);
    }

    public DlgDevolucionIngresoCantidad(Frame parent, String title, boolean modal) {

        super(parent, title, modal);
        myParentFrame = parent;

        try {
            this.addKeyListener(this);
            setFocusable(true);
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


        this.setSize(new Dimension(435, 306));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Ingreso de Cantidad");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });

        pnlHeader1.setBounds(new Rectangle(10, 10, 410, 40));
        pnlHeader1.setBackground(Color.white);
        pnlHeader1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));

        lblStock.setText("Stock del Producto al:");
        lblStock.setBounds(new Rectangle(15, 10, 130, 15));
        lblStock.setForeground(new Color(255, 130, 14));
        lblFechaHoraActual.setFont(new Font("SansSerif", 0, 11));
        lblFechaHoraActual.setBounds(new Rectangle(140, 10, 100, 15));
        lblFechaHoraActual.setForeground(new Color(255, 130, 14));

        lblUnidades.setText("Unidades:");
        lblUnidades.setBounds(new Rectangle(255, 10, 55, 15));
        lblUnidades.setForeground(new Color(255, 130, 14));
        lblUnidadesD.setFont(new Font("SansSerif", 0, 11));
        lblUnidadesD.setBounds(new Rectangle(315, 10, 55, 15));
        lblUnidadesD.setForeground(new Color(255, 130, 14));

        lblCodigo.setText("Codigo:");
        lblCodigo.setBounds(new Rectangle(15, 15, 70, 15));
        lblCodigo.setForeground(new Color(255, 130, 14));
        lblCodigoD.setFont(new Font("SansSerif", 0, 11));
        lblCodigoD.setBounds(new Rectangle(65, 15, 70, 15));
        lblCodigoD.setForeground(new Color(255, 130, 14));

        pnlTitle1.setBounds(new Rectangle(10, 60, 410, 185));
        pnlTitle1.setBackground(Color.white);
        pnlTitle1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));

        lblDescripcion.setText("Descripcion:");
        lblDescripcion.setBounds(new Rectangle(15, 40, 180, 15));
        lblDescripcion.setForeground(new Color(255, 130, 14));
        lblDescripcionD.setFont(new Font("SansSerif", 0, 11));
        lblDescripcionD.setBounds(new Rectangle(92, 40, 150, 15));
        lblDescripcionD.setForeground(new Color(255, 130, 14));

        lblLaboratorio.setText("Laboratorio:");
        lblLaboratorio.setBounds(new Rectangle(15, 65, 70, 15));
        lblLaboratorio.setForeground(new Color(255, 130, 14));
        lblLaboratorioD.setFont(new Font("SansSerif", 0, 11));
        lblLaboratorioD.setBounds(new Rectangle(90, 65, 150, 15));
        lblLaboratorioD.setForeground(new Color(255, 130, 14));

        lblUnidad.setText("Unidad:");
        lblUnidad.setBounds(new Rectangle(15, 90, 70, 15));
        lblUnidad.setForeground(new Color(255, 130, 14));
        lblUnidadD.setFont(new Font("SansSerif", 0, 11));
        lblUnidadD.setBounds(new Rectangle(63, 90, 70, 15));
        lblUnidadD.setForeground(new Color(255, 130, 14));

        lblValorFrac.setText("Valor Frac.");
        lblValorFrac.setBounds(new Rectangle(170, 90, 70, 15));
        lblValorFrac.setForeground(new Color(255, 130, 14));
        lblValorFracD.setFont(new Font("SansSerif", 0, 11));
        lblValorFracD.setBounds(new Rectangle(235, 90, 70, 15));
        lblValorFracD.setForeground(new Color(255, 130, 14));

        btnCantidad.setText("Entero");
        btnCantidad.setBounds(new Rectangle(20, 130, 60, 15));
        btnCantidad.setMnemonic('E');
        btnCantidad.setForeground(new Color(255, 130, 14));
        btnCantidad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnEntero_actionPerformed(e);
            }
        });

        txtCantidad.setLengthText(6);
        txtCantidad.setBounds(new Rectangle(20, 150, 60, 20));
        txtCantidad.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCantidad_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtCantidad_keyTyped(e);
            }
        });

        lblFraccion.setText("Fraccion");
        lblFraccion.setBounds(new Rectangle(90, 130, 60, 15));
        lblFraccion.setForeground(new Color(255, 130, 14));


        txtFraccion.setLengthText(6);
        txtFraccion.setBounds(new Rectangle(90, 150, 95, 20));
        txtFraccion.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFraccion_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtFraccion_keyTyped(e);
            }
        });

        lblFechaVenc.setText("Fecha Venc.");
        lblFechaVenc.setBounds(new Rectangle(265, 130, 85, 15));
        lblFechaVenc.setForeground(new Color(255, 130, 14));

        txtFechaVenc.setLengthText(10);
        txtFechaVenc.setBounds(new Rectangle(265, 150, 100, 20));
        txtFechaVenc.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaVenc_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaVenc_keyReleased(e);
            }
        });

        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(330, 250, 90, 20));
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(205, 250, 110, 20));

        lblLote.setText("Lote");
        lblLote.setBounds(new Rectangle(195, 130, 60, 15));
        lblLote.setForeground(new Color(255, 130, 14));

        txtLote.setLengthText(15);
        txtLote.setBounds(new Rectangle(195, 150, 60, 20));
        txtLote.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtLote_keyPressed(e);
            }
        });


        jContentPane.add(pnlHeader1, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        pnlTitle1.add(lblCodigo, null);
        pnlTitle1.add(lblCodigoD, null);
        pnlTitle1.add(lblDescripcion, null);
        pnlTitle1.add(lblDescripcionD, null);
        pnlTitle1.add(lblLaboratorio, null);
        pnlTitle1.add(lblLaboratorioD, null);
        pnlTitle1.add(lblLote, null);
        pnlHeader1.add(lblStock, null);
        pnlHeader1.add(lblFechaHoraActual, null);
        pnlHeader1.add(lblUnidades, null);
        pnlHeader1.add(lblUnidadesD, null);
        pnlTitle1.add(lblUnidad, null);
        pnlTitle1.add(lblUnidadD, null);
        pnlTitle1.add(lblValorFrac, null);
        pnlTitle1.add(lblValorFracD, null);
        pnlTitle1.add(txtLote, null);
        pnlTitle1.add(txtCantidad, null);
        pnlTitle1.add(btnCantidad, null);
        pnlTitle1.add(lblFraccion, null);
        pnlTitle1.add(txtFraccion, null);
        pnlTitle1.add(lblFechaVenc, null);
        pnlTitle1.add(txtFechaVenc, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);

    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        cantInic = FarmaUtility.trunc(FarmaUtility.getDecimalNumber(VariablesInventario.vCant_Transf));
        FarmaVariables.vAceptar = false;

    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initCabecera() {
        /* lblFechaHoraActual.setText(notaEsCabDetDTO.getFechaHoraTransfFrm());
        lblUnidadesD.setText(String.valueOf(notaEsCabDetDTO.getCantUnidadForm()));
        lblCodigoD.setText(notaEsCabDetDTO.getCodProd());
        lblDescripcionD.setText(notaEsCabDetDTO.getDescProdFrm());
        lblLaboratorioD.setText(notaEsCabDetDTO.getDescLab());
        lblUnidadD.setText(String.valueOf(notaEsCabDetDTO.getDescUnidaVta()));
        lblValorFracD.setText(String.valueOf(notaEsCabDetDTO.getValFracTransfFrm()));
        */

        lblFechaHoraActual.setText(VariablesInventario.vfechaStock);
        lblUnidadesD.setText(VariablesInventario.vStockDispo);
        lblCodigoD.setText(VariablesInventario.vCodigo);
        lblDescripcionD.setText(VariablesInventario.vDescrip);
        lblLaboratorioD.setText(VariablesInventario.vLaborat);
        lblUnidadD.setText(VariablesInventario.vDescUniPrese);
        lblValorFracD.setText(VariablesInventario.vValFrac);
        //Cesar Huanes 27.02.2014
        txtCantidad.setText(""); //limpiamos la caja y luego entra ala condicion
        if (VariablesInventario.vFlagCantMov == true) {
            txtCantidad.setText(String.valueOf(String.valueOf(VariablesInventario.vCantMovi))); //Seteamos la candidad al editar
            VariablesInventario.vCantMovi = 0; //Inicialiamos la cantidad
        }
        if (VariablesInventario.vFlagCantMov == false) {
            txtCantidad.setText("");
        }


        if (!VariablesInventario.vValFrac.equals("1")) {
            productoFraccionado = true;
        }
        if (productoFraccionado) {
            int cant = 0;
            if (!VariablesInventario.vCant_Transf.trim().equals("")) {
                cant = Integer.parseInt(VariablesInventario.vCant_Transf.trim());
            }
            int frac = Integer.parseInt(VariablesInventario.vValFrac);
            int valFrac = cant % frac;
            int valCant = cant / frac;
            txtCantidad.setText(valCant + "");
            txtFraccion.setText(valFrac + "");
        } else {
            txtFraccion.setText("0");
        }

        //cambio para obtener la fecha de vencimiento
        txtFechaVenc.setText(devFechaVto());

        VariablesInventario.vFechVenc = devFechaVto();

    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        txtFraccion.setEditable(false);

        moveFocus(txtCantidad);
        initCabecera();
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", txtCantidad);
    }

    private void btnEntero_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCantidad);
    }

    private void txtCantidad_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCantidad, e);
        chkKeyPressed(e);
    }

    private void txtFraccion_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            moveFocus(txtFechaVenc);
        } else {
            chkKeyPressed(e);
        }
    }

    private void txtFraccion_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtFraccion, e);
    }

    private void txtFechaVenc_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            moveFocus(txtCantidad);
        } else {
            chkKeyPressed(e);
        }
    }

    private void txtFechaVenc_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaVenc, e);
    }

    private void txtCantidad_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            moveFocus(txtLote);
        } else {
            chkKeyPressed(e);
        }
    }

    private void txtLote_keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            moveFocus(txtFechaVenc);
            txtLote.setText(txtLote.getText().toUpperCase());
        } else {
            chkKeyPressed(e);
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        //int teclaPresionada=e.getKeyCode();
        //log.debug("Tecla Presionada: code: "+teclaPresionada+" char:"+ e.getKeyChar());
        chkKeyPressed(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
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

    private void aceptaCantidadIngresada() {
        if (validarCampos()) {

            if (!validaCantidadIngreso()) {
                FarmaUtility.showMessage(this, "La fraccion ingresada no puede ser mayor al producto.", txtFraccion);
                return;
            }
            if (!validaStockActual()) {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this, "La cantidad ingresada no puede ser mayor al Stock del Producto.",
                                         txtCantidad);
                lblUnidades.setText("" + (Integer.parseInt(VariablesInventario.vStk_Prod) + cantInic));
                return;
            }
            if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                            "¿Está seguro de guardar los datos?")) {
                cargarDatos();
                cerrarVentana(true);
            }
        }
    }

    private boolean validarCampos() {
        boolean retorno = true;
        if (txtCantidad.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Debe Ingresar la Cantidad.", txtCantidad);
            retorno = false;
        } else if (txtFechaVenc.getText().trim().length() != 0) {
            if (!FarmaUtility.validaFecha(txtFechaVenc.getText().trim(), "dd/MM/yyyy")) {
                FarmaUtility.showMessage(this, "Corrija la Fecha de Vencimiento.", txtFechaVenc);
                retorno = false;
            }
        }

        return retorno;
    }

    private boolean validaCantidadIngreso() {
        boolean valor = false;
        String cantIngreso = txtCantidad.getText().trim();
        String fraccion = txtFraccion.getText().trim();
        if (fraccion.equals(""))
            fraccion = "0";
        if (FarmaUtility.isInteger(cantIngreso) && Integer.parseInt(cantIngreso) >= 0)
            valor = true;
        if (FarmaUtility.isInteger(fraccion) && Integer.parseInt(fraccion) >= 0)
            valor = true;
        if (Integer.parseInt(cantIngreso) == 0 && Integer.parseInt(fraccion) == 0)
            valor = false;
        if ((!fraccion.equals("")) && (Integer.parseInt(fraccion) > Integer.parseInt(lblValorFracD.getText()))) {
            valor = false;
        }
        return valor;
    }

    private boolean validaStockActual() {
        boolean valor = false;
        obtieneStockProducto();
        String cantidad, fraccion;
        cantidad = txtCantidad.getText().trim();
        fraccion = txtFraccion.getText().trim();
        if (fraccion.equals(""))
            fraccion = "0";
        VariablesInventario.vCantIngreso =
                Integer.parseInt(cantidad) * Integer.parseInt(lblValorFracD.getText()) + Integer.parseInt(fraccion);

        int ss = (Integer.parseInt(VariablesInventario.vStk_Prod) + cantInic);

        if ((Integer.parseInt(VariablesInventario.vStk_Prod) + cantInic) >= VariablesInventario.vCantIngreso)
            valor = true;
        return valor;
    }


    private void obtieneStockProducto() {
        ArrayList myArray = new ArrayList();
        obtieneStockProducto_ForUpdate(myArray);
        if (myArray.size() == 1) {
            VariablesInventario.vStk_Prod = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
        } else {
            FarmaUtility.showMessage(this, "Error al obtener Stock del Producto", null);
            cerrarVentana(false);
        }
    }


    private void obtieneStockProducto_ForUpdate(ArrayList pArrayList) {
        try {
            //DBInventario.obtieneStockProducto_ForUpdate(pArrayList, notaEsCabDetDTO.getCodProd());
            DBInventario.obtieneStockProducto_ForUpdate(pArrayList, VariablesInventario.vCodigo);

            FarmaUtility.liberarTransaccion();
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ha ocurrido un error al obtener el stock del producto : \n" +
                    sql.getMessage(), txtCantidad);
        }
    }

    private String devFechaVto() {

        String fechaVto = "";
        try {
            fechaVto = DBInventario.devFechaVto(lblCodigo.getText().trim(), txtLote.getText().trim());
        } catch (SQLException sql) {
            log.error("", sql);
        }

        return fechaVto;
    }

    private void cargarDatos() {
        /*
      notaEsCabDetDTO.setCantMov(VariablesInventario.vCantIngreso);
      notaEsCabDetDTO.setNumLoteProd(txtLote.getText().trim());
      notaEsCabDetDTO.setFecVtoProd(txtFechaVenc.getText().trim());
    */
        VariablesInventario.vCantMovi = VariablesInventario.vCantIngreso;
        VariablesInventario.vMunLote = txtLote.getText().trim();
        VariablesInventario.vFechVentprod = txtFechaVenc.getText().trim();

        //  int cant = notaEsCabDetDTO.getCantMov();

        int cant = VariablesInventario.vCantMovi;
        // double prec = FarmaUtility.getDecimalNumber(notaEsCabDetDTO.getPrecVta());
        double prec = FarmaUtility.getDecimalNumber(VariablesInventario.vPrecVtaVig);

        /*  notaEsCabDetDTO.setValPrecioTotal((cant*prec)+"");
      notaEsCabDetDTO.setSecRespalStock(0);
    */
        double resultado = (cant * prec);
        VariablesInventario.vValPrectotal = resultado;
        VariablesInventario.secResStock = 0;
    }

    void setNotaEsCabDetDTO(NotaEsCabDetDTO notaEsCabDetDTO) {
        this.notaEsCabDetDTO = notaEsCabDetDTO;
    }
}
