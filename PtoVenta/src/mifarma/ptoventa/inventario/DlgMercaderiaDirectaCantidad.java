package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
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

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.FarmaColumnData;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.FacadeInventario;
import mifarma.ptoventa.inventario.reference.UtilityInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.recepcionCiega.reference.DBRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.UtilityRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.VariablesRecepCiega;
import mifarma.ptoventa.recetario.DlgResumenRecetarioMagistral;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.UtilityVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgMercaderiaDirectaCantidad extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    Frame myParentFrame;
    private boolean productoFraccionado = false;
    private static final Logger log = LoggerFactory.getLogger(DlgResumenRecetarioMagistral.class);
    private Integer precepTotal = 0;
    private String totalProd = "";
    private Double precInput = 0.0;
    private String descUnidVta = "";
    String strCodigoOrdCompDetalle = "1";

    private JPanelHeader pnlHeader1 = new JPanelHeader();
    private JLabelWhite lblDescripcion_T = new JLabelWhite();
    private JLabelWhite lblCodeProduct = new JLabelWhite();
    private JPanelTitle pnlDatosIngreso = new JPanelTitle();
    private JButtonLabel btnRecep = new JButtonLabel();
    private JTextFieldSanSerif txtEntero = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtPrecioUnitario = new JTextFieldSanSerif();
    private JLabelWhite lblIGV = new JLabelWhite();
    private JTextFieldSanSerif txtIGV = new JTextFieldSanSerif();
    private JLabelWhite lblPrecioUnita = new JLabelWhite();
    private JTextFieldSanSerif txtFraccion = new JTextFieldSanSerif();
    private JLabelOrange lblCantOC = new JLabelOrange();
    private JLabelOrange lblCodProducto = new JLabelOrange();
    private JLabelOrange lblUnidVenta_T = new JLabelOrange();
    private JLabelOrange lblDescripProd = new JLabelOrange();
    private JLabelOrange lblUnidVenta = new JLabelOrange();

    private JTextFieldSanSerif txtLote = new JTextFieldSanSerif();
    private JButtonLabel btnLote = new JButtonLabel();
    private JTextFieldSanSerif txtFechaVenc = new JTextFieldSanSerif();
    private JButtonLabel btnFechaVenc = new JButtonLabel();

    private FacadeInventario facadeInventario = new FacadeInventario();

    //kmoncada
    private int cantAdicPermitidaOCProd = 0;
    private int valInnerPackProd = 1;

    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblF5 = new JLabelFunction();
    private JScrollPane jspCantidadLote = new JScrollPane();
    private JTable tblListaCantidadLote = new JTable();
    private FarmaTableModel tableModel;
    private ArrayList vListCantidadLote = new ArrayList();
    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgMercaderiaDirectaCantidad() {
        this(null, "", false);
    }

    public DlgMercaderiaDirectaCantidad(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        //VariablesPtoVenta.vTipoLocalVenta = "X";
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

        this.getContentPane().setLayout(null);
        this.setTitle("Cantidad Recibida - Mercadería Directa");
        this.setDefaultCloseOperation(0);
        //this.setSize(new Dimension(624, 258));

        this.setSize(new Dimension(629, 458));
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        //VariablesPtoVenta.vTipoLocalVenta = "X";


        pnlHeader1.setBounds(new Rectangle(10, 10, 472, 100));
        pnlDatosIngreso.setBounds(new Rectangle(10, 130, 472, 80));
        lblDescripProd.setBounds(new Rectangle(95, 40, 332, 20));
        btnRecep.setBounds(new Rectangle(15, 20, 55, 15));
        txtEntero.setBounds(new Rectangle(15, 40, 55, 20));
        lblCantOC.setBounds(new Rectangle(285, 20, 50, 15));
        txtFraccion.setBounds(new Rectangle(285, 40, 75, 20));
        lblPrecioUnita.setBounds(new Rectangle(380, 20, 55, 15));
        txtPrecioUnitario.setBounds(new Rectangle(380, 40, 95, 20));
        lblIGV.setBounds(new Rectangle(495, 20, 20, 15));
        txtIGV.setBounds(new Rectangle(495, 40, 85, 20));
        //lblF11.setBounds(new Rectangle(265, 230, 105, 20));
        //lblEsc.setBounds(new Rectangle(400, 230, 85, 20));

        pnlHeader1.setBackground(Color.white);
        pnlHeader1.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlHeader1.setBounds(new Rectangle(5, 5, 605, 100));
        lblDescripcion_T.setText("Descripción:");
        lblDescripcion_T.setBounds(new Rectangle(10, 40, 75, 15));
        lblDescripcion_T.setForeground(new Color(255, 130, 14));
        lblCodeProduct.setBounds(new Rectangle(105, 15, 105, 15));
        lblCodeProduct.setFont(new Font("SansSerif", 0, 11));
        lblCodeProduct.setForeground(new Color(255, 130, 14));
        lblCodeProduct.setSize(new Dimension(105, 20));
        pnlDatosIngreso.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlDatosIngreso.setBackground(Color.white);
        pnlDatosIngreso.setBounds(new Rectangle(5, 110, 605, 80));
        btnRecep.setText("Cant. Rec.");
        btnRecep.setMnemonic('E');
        btnRecep.setForeground(new Color(255, 130, 14));
        btnRecep.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnEntero_actionPerformed(e);
            }
        });

        txtEntero.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                    txtEntero_keyPressed(e);
                }

            public void keyTyped(KeyEvent e) {
                txtEntero_keyTyped(e);
            }
        });
        txtPrecioUnitario.setLengthText(9);
        txtPrecioUnitario.setHorizontalAlignment(SwingConstants.LEFT);
        txtPrecioUnitario.setEditable(false);
        txtPrecioUnitario.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtPrecioUnitario_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtPrecioUnitario_keyTyped(e);
            }
        });
        txtPrecioUnitario.setHorizontalAlignment(SwingConstants.CENTER);
        lblIGV.setText("IGV");
        lblIGV.setForeground(new Color(255, 130, 14));
        lblPrecioUnita.setText("Prec. Unit.");
        lblPrecioUnita.setForeground(new Color(255, 130, 14));
        txtFraccion.setLengthText(6);
        txtFraccion.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFraccion_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtFraccion_keyTyped(e);
            }
        });
        txtFraccion.setHorizontalAlignment(SwingConstants.CENTER);
        lblCantOC.setText("Cant. O/C");
        lblCodProducto.setText("Cod. Producto :");
        lblCodProducto.setBounds(new Rectangle(10, 15, 95, 15));
        lblUnidVenta_T.setText("Unid. Venta:");
        lblUnidVenta_T.setBounds(new Rectangle(10, 65, 79, 15));
        lblDescripProd.setFont(new Font("SansSerif", 0, 11));
        lblUnidVenta.setBounds(new Rectangle(95, 65, 130, 15));
        lblUnidVenta.setFont(new Font("SansSerif", 0, 11));
        lblUnidVenta.setSize(new Dimension(130, 20));
        txtLote.setBounds(new Rectangle(90, 40, 70, 20));
        txtLote.setLengthText(20);

        txtLote.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                    txtLote_keyPressed(e);
                }

            public void keyTyped(KeyEvent e) {
                txtLote_keyTyped(e);
            }

            public void keyReleased(KeyEvent e) {
                txtLote_keyReleased(e);
            }
        });
        btnLote.setText("Lote");
        btnLote.setBounds(new Rectangle(90, 20, 50, 15));
        btnLote.setForeground(new Color(255, 130, 14));
        btnLote.setMnemonic('l');

        btnLote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //btnLote_actionPerformed(e);
            }
        });
        txtFechaVenc.setBounds(new Rectangle(175, 40, 90, 20));
        txtFechaVenc.setLengthText(30);

        txtFechaVenc.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                    txtFechaVenc_keyPressed(e);
                }

            public void keyReleased(KeyEvent e) {
                txtFechaVenc_keyReleased(e);
            }

            public void keyTyped(KeyEvent e) {
                txtFechaVenc_keyTyped(e);
            }
        });
        btnFechaVenc.setText("Fecha Venc.");
        btnFechaVenc.setBounds(new Rectangle(175, 20, 70, 15));
        btnFechaVenc.setForeground(new Color(255, 130, 14));
        btnFechaVenc.setMnemonic('f');

        btnFechaVenc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //btnFechaVenc_actionPerformed(e);
            }
        });

        
        lblEsc.setText("[ Esc ] Cerrar");
        lblF11.setText("[ F11 ] Aceptar");
        lblF5.setText("[ F5 ] Borrar");
        jspCantidadLote.setBounds(new Rectangle(5, 200, 605, 185));
        pnlHeader1.add(lblUnidVenta, null);
        pnlHeader1.add(lblDescripProd, null);
        pnlHeader1.add(lblUnidVenta_T, null);
        pnlHeader1.add(lblCodProducto, null);
        pnlHeader1.add(lblCodeProduct, null);
        pnlHeader1.add(lblDescripcion_T, null);

        pnlDatosIngreso.add(lblCantOC, null);
        pnlDatosIngreso.add(txtFraccion, null);
        pnlDatosIngreso.add(lblPrecioUnita, null);
        pnlDatosIngreso.add(lblIGV, null);
        pnlDatosIngreso.add(txtPrecioUnitario, null);
        pnlDatosIngreso.add(txtEntero, null);
        pnlDatosIngreso.add(btnRecep, null);
        pnlDatosIngreso.add(txtIGV, null);
        pnlDatosIngreso.add(btnLote, null);
        pnlDatosIngreso.add(txtLote, null);


        pnlDatosIngreso.add(btnFechaVenc, null);
        pnlDatosIngreso.add(txtFechaVenc, null);
        jspCantidadLote.getViewport().add(tblListaCantidadLote, null);
        
        this.getContentPane().add(pnlHeader1, null);
        this.getContentPane().add(pnlDatosIngreso, null);
        this.getContentPane().add(lblF11, null);
        this.getContentPane().add(lblEsc, null);
        this.getContentPane().add(lblF5,null);
        txtEntero.setLengthText(6);
        txtIGV.setLengthText(3);
        txtIGV.setEditable(false);
        txtIGV.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtIGV_Pressed(e);


            }

            public void keyTyped(KeyEvent e) {
                txtIGV_keyTyped(e);
            }
        });

        txtLote.setEnabled(false);
        txtFechaVenc.setEnabled(false);


        txtIGV.setHorizontalAlignment(SwingConstants.CENTER);
        
        //if (VariablesPtoVenta.vTipoLocalVenta.equals(ConstantsPtoVenta.TIPO_LOCAL_VTA_MAYORISTA)) {
        //KMONCADA 05.08.2016 [PROYECTO M] FUNCION PARA VALIDAR LOCAL MAYORISTA
        if (UtilityPtoVenta.isLocalVentaMayorista()) {
            txtLote.setEnabled(true);
            txtFechaVenc.setEnabled(true);
            this.getContentPane().add(jspCantidadLote, null);
            jspCantidadLote.setVisible(true);
            lblF11.setBounds(new Rectangle(475, 395, 137, 20));
            lblF5.setBounds(new Rectangle(300, 395, 137, 20));
            lblEsc.setBounds(new Rectangle(5, 400, 137, 20));
            
            this.setSize(new Dimension(639, 468));
        }
        else{
            jspCantidadLote.setVisible(false);
            lblF11.setBounds(new Rectangle(365, 200, 105, 20));
            //lblF5.setBounds(new Rectangle(365, 200, 105, 20));
            lblEsc.setBounds(new Rectangle(500, 200, 85, 20));
            this.setSize(new Dimension(630, 265));
        }


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

        lblCodeProduct.setText(VariablesInventario.vCodProducto.trim());
        lblDescripProd.setText(VariablesInventario.vDescProd.trim());
        lblUnidVenta.setText(VariablesInventario.vUnidMedida.trim());
        txtFraccion.setText(VariablesInventario.vCantPedida.trim());
        txtPrecioUnitario.setText(VariablesInventario.vPrecUnit.trim());
        txtIGV.setText(UtilityVentas.obtenerIgvLocal()); //ASOSA - 25/06/2015 - IGVAZONIA
        
        tblListaCantidadLote.getTableHeader().setReorderingAllowed(false);
        tblListaCantidadLote.getTableHeader().setResizingAllowed(false);
        tableModel =
                new FarmaTableModel(ConstantsInventario.columnsMA_CantLote, ConstantsInventario.defaultMA_CantLote,
                                    0);
        FarmaUtility.initSimpleList(tblListaCantidadLote, tableModel, ConstantsInventario.columnsMA_CantLote);
        
        
    }
    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void btnEntero_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtEntero);
    }

    private void this_windowOpened(WindowEvent e) {
        listaTabla();
        FarmaUtility.centrarVentana(this);
        if (!productoFraccionado) {
            txtFraccion.setEditable(false);
        }
        FarmaUtility.moveFocus(txtEntero);
    }

    private void txtEntero_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaCantidadLote, null, 0);
        
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(txtLote.isEnabled())
                FarmaUtility.moveFocus(txtLote);
            else{
                ingresoCantidad();
            }
        } else {
            chkKeyPressed(e);
        }
    }

    private void txtEntero_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtEntero, e);
    }

    private void txtFraccion_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtPrecioUnitario);
        } else
            chkKeyPressed(e);
    }

    private void txtFraccion_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtFraccion, e);
    }

    private void txtPrecioUnitario_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtIGV);
        else
            chkKeyPressed(e);
    }

    private void txtPrecioUnitario_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtPrecioUnitario, e);
    }

    private void txtIGV_Pressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtIGV);
        else
            chkKeyPressed(e);
    }

    private void txtIGV_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtIGV, e);
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
        } 
        else if (e.getKeyCode() == KeyEvent.VK_F5) {
            eliminarCantidadLote();
        } 
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (JConfirmDialog.rptaConfirmDialog(this, "¿Está seguro de no grabar ningún cambio realizado?")) {
                cerrarVentana(false);
            }
        }
    }

    private void cerrarVentana(boolean vAceptar) {
        FarmaVariables.vAceptar = vAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void setDetalleOrdenCompraRecep(boolean vIsLocalMayorista) {
        VariablesInventario.vCodProdOrdComp = lblCodeProduct.getText().trim();
        VariablesInventario.vCantEntregada = txtEntero.getText().trim();
        VariablesInventario.vCantPedOrdComp = txtFraccion.getText().trim();
        VariablesInventario.vPrecioUnit = txtPrecioUnitario.getText().trim();
        VariablesInventario.vPrecioIGV = txtIGV.getText().trim();
        VariablesInventario.vSEC = strCodigoOrdCompDetalle.trim();
        
        //if (VariablesPtoVenta.vTipoLocalVenta.equals(ConstantsPtoVenta.TIPO_LOCAL_VTA_MAYORISTA)) {
        //KMONCADA 05.08.2016 [PROYECTO M] FUNCION PARA VALIDAR LOCAL MAYORISTA
        if (UtilityPtoVenta.isLocalVentaMayorista()) {
            VariablesInventario.vLote = this.txtLote.getText().trim().toUpperCase();
            VariablesInventario.vFechVenc = this.txtFechaVenc.getText().trim().toUpperCase();
        }
        
        ArrayList vAux = new ArrayList();
        
        vAux.add(txtEntero.getText().trim()); // 0
        vAux.add(txtLote.getText().trim());// 1
        vAux.add(txtFechaVenc.getText().trim());// 2
        vAux.add(" ");// FECHA REGISTRO // 3
        vAux.add(lblCodeProduct.getText().trim());// 4
        vAux.add(txtFraccion.getText().trim());// 5
        vAux.add(txtPrecioUnitario.getText().trim());// 6
        vAux.add(txtIGV.getText().trim());// 7
        vAux.add(strCodigoOrdCompDetalle.trim());// 8
        vAux.add(vListCantidadLote.size()+1+"");//SECUENCIA// 9
        
        vListCantidadLote.add(vAux.clone());
        
        if(!vIsLocalMayorista){
            aceptaCantidadIngresada();
        }
                
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void ingresoCantidad() {
        boolean rpta = false;
        rpta = validaIngresoCantidad();
        if(rpta){
            if(getSumUnidades()+ Integer.parseInt(txtEntero.getText().trim())<=Integer.parseInt(txtFraccion.getText().trim()))
                rpta = true;
            else{
                FarmaUtility.showMessage(this, "La suma de las cantidad ingresadas superan la Cantidad de O/C", txtEntero);
                rpta = false;
            }
        }
        ///////////////////////////////////////////////////
        //if (VariablesPtoVenta.vTipoLocalVenta.equals(ConstantsPtoVenta.TIPO_LOCAL_VTA_MAYORISTA) && rpta) {
        //KMONCADA 05.08.2016 [PROYECTO M] FUNCION PARA VALIDAR LOCAL MAYORISTA
        if (UtilityPtoVenta.isLocalVentaMayorista() && rpta) {
            rpta = validaDatosLote();
            if (rpta) {
                setDetalleOrdenCompraRecep(true);
                listaTabla();
                txtEntero.setText("");
                txtLote.setText("");
                txtFechaVenc.setText("");
            }
        } else if (rpta) {
            setDetalleOrdenCompraRecep(false);
            listaTabla();
            txtEntero.setText("");
            txtLote.setText("");
            txtFechaVenc.setText("");
            cerrarVentana(true);
        }
        
        FarmaUtility.moveFocus(txtEntero);
        ///////////////////////////////////////////////////
    }
    

    /**
     * Cantidad adicional permitada al proveedor por orden de compra.
     * @author kmoncada
     * @since 15.07.2014
     * @param cantAdicPermitidaOCProd
     */
    public void setCantAdicPermitidaOCProd(int cantAdicPermitidaOCProd) {
        this.cantAdicPermitidaOCProd = cantAdicPermitidaOCProd;
    }

    /**
     * Valor de InnerPack del producto
     * @author kmoncada
     * @since 15.07.2014
     * @param valInnerPackProd
     */
    public void setValInnerPackProd(int valInnerPackProd) {
        this.valInnerPackProd = valInnerPackProd;
    }

    private void txtLote_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaCantidadLote, null, 0);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            //if (VariablesPtoVenta.vTipoLocalVenta.equals(ConstantsPtoVenta.TIPO_LOCAL_VTA_MAYORISTA)) {
            //KMONCADA 05.08.2016 [PROYECTO M] FUNCION PARA VALIDAR LOCAL MAYORISTA
            if (UtilityPtoVenta.isLocalVentaMayorista()) {
                try {
                    txtFechaVenc.setText(DBRecepCiega.obtieneFechaVencimiento((txtLote.getText() == null ? "" :
                                                                               txtLote.getText())));
                } catch (SQLException f) {
                    txtFechaVenc.setText("");
                    log.error("ERROR: " + f);
                }
                FarmaUtility.moveFocus(txtFechaVenc);
            } else {
                FarmaUtility.moveFocus(txtFraccion);
            }
        } else {
            chkKeyPressed(e);
        }
    }

    private void txtFechaVenc_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaCantidadLote, null, 0);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                ingresoCantidad();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_F11) {
            chkKeyPressed(e);
        }
    }

    private void txtLote_keyTyped(KeyEvent e) {
        //chkKeyPressed(e);
    }

    private void txtFechaVenc_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtFechaVenc, e);
    }

    private void txtLote_keyReleased(KeyEvent e) {
        //chkKeyPressed(e);
    }

    private void txtFechaVenc_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaVenc, e);
    }

    public boolean validaDatosLote() {
        boolean rpta = true;
        if (txtLote.getText().trim().length() == 0) {
            rpta = false;
            FarmaUtility.showMessage(this, "¡Ingrese lote correcto!", txtLote);
        } else {
            VariablesRecepCiega.vLote = txtLote.getText().toString().trim().toUpperCase();
        }
        if (rpta) {
            rpta = validaDatosFechaVenc();
        }
        return rpta;
    }

    public boolean validaDatosFechaVenc() {
        boolean rpta = true;
        String FechaVenc = txtFechaVenc.getText().trim();

        if (FechaVenc.length() == 0) {
            rpta = false;
            FarmaUtility.showMessage(this, "¡Ingrese Fecha Vencimiento correcta!", txtFechaVenc);
        } else {
            try {
                if (!UtilityRecepCiega.validarFecha(FechaVenc)) {
                    rpta = false;
                    FarmaUtility.showMessage(this, "Formato de fecha inicial inválido", FechaVenc);
                } else if (rpta) {
                    String sFechaActual = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());

                    Date dFechaVenc = FarmaUtility.getStringToDate(FechaVenc, "dd/MM/yyyy");
                    Date dFechaActual = FarmaUtility.getStringToDate(sFechaActual, "dd/MM/yyyy");
                    if (!dFechaActual.before(dFechaVenc)) {
                        rpta = false;
                        FarmaUtility.showMessage(this, "la fecha de vencimiento debe ser mayor a la fecha actual",
                                                 FechaVenc);
                    } else {
                        VariablesRecepCiega.vFechaVcto = txtFechaVenc.getText();
                    }
                }
            } catch (Exception e) {
                rpta = false;
                log.error("", e);
            }
        }
        return rpta;
    }

    

    public void setPListCantidadLote(ArrayList pListCantidadLote) {
        this.vListCantidadLote = pListCantidadLote;
    }

    public ArrayList getPListCantidadLote() {
        return vListCantidadLote;
    }

    private boolean validaIngresoCantidad() {
        boolean rpta = false;
        
        if (txtEntero.getText().trim().equals("")) {
            FarmaUtility.showMessage(this, "Ingrese cantidad", null);
            FarmaUtility.moveFocus(txtEntero);
            return false;
        }
        
        int ptotalProd = Integer.parseInt(VariablesInventario.vRecepTotal) + Integer.parseInt(txtEntero.getText());
        //INI ASOSA - 24/09/2014
        if (VariablesInventario.vEstadoItem.equals("N")) {
            ptotalProd = Integer.parseInt(txtEntero.getText());
        }
        //FIN ASOSA - 24/09/2014
        //kmoncada 15.07.2014 agrega la cantidad permitida adicional por el proveedor a la cantidad pedida
        precepTotal =
                Integer.parseInt(VariablesInventario.vCantPedida); // + cantAdicPermitidaOCProd; ASOSA - 28/08/2014
        //I - EMAQUERA - 23/12/2015
        double precRecep = FarmaUtility.getDecimalNumber(txtPrecioUnitario.getText());
        precInput = FarmaUtility.getDecimalNumber(VariablesInventario.vPrecUnit);
        //F - 23/12/2015
        //INI ASOSA - 28/08/2014
        int cantMultWithIP = 0;
        double cantMultWithoutIP = 0;

        String cadena = UtilityInventario.obtenerParamTwoString(ConstantsInventario.COD_NUM_CANT_MULT);

        String[] valores = cadena.split("Ã");

        if (valores[0].trim().equals("")) {
            cantMultWithIP = 1;
        } else {
            cantMultWithIP = (int)Integer.parseInt(valores[0]);
        }

        if (valores[1].trim().equals("")) {
            cantMultWithoutIP = 1.0;
        } else {
            cantMultWithoutIP = (double)Double.parseDouble(valores[1]);
        }

        int cantPedida = (int)Integer.parseInt(txtFraccion.getText());

        //FIN ASOSA - 28/08/2014

        if (Integer.parseInt(txtEntero.getText()) == 0) {
            FarmaUtility.showMessage(this,
                                     "Debe ingresar cantidades superiores a " + Integer.parseInt(txtEntero.getText()) +
                                     "  unidades", null);
            FarmaUtility.moveFocus(txtEntero);

        } 
        else if (precRecep == 0.0 || precRecep == 0) {
            FarmaUtility.showMessage(this, "Verifique, El precio es: " + precRecep + " soles", null);
        }
        else {
            //GFonseca 10.12.2013 Inner pack (indica cuantas unidades por paquete)
            if (ptotalProd >= cantPedida) {
                if (valInnerPackProd > 1) {
                    int cantPaquetes = 0;
                    cantPaquetes = ptotalProd % valInnerPackProd;
                    if (cantPaquetes > 0) {
                        FarmaUtility.showMessage(this,
                                                 "Debe ingresar cantidades multiplos de " + valInnerPackProd + "  unidades",
                                                 null);
                        FarmaUtility.moveFocus(txtEntero);
                    } else {
                        if ((((double)ptotalProd / (double)cantPedida) <= (double)cantMultWithIP) ||
                            (cantMultWithIP == 1 && ptotalProd == cantPedida)) { //ASOSA - 28/08/2014
                            rpta = true;
                            //FarmaUtility.showMessage(this, "Se agrego correctamente ", null);
                        } else {
                            FarmaUtility.showMessage(this,
                                                     "Máximo puede recepcionarse la cantidad pedida X " + cantMultWithIP +
                                                     "\n En este caso maximo " + (cantPedida * cantMultWithIP), null);
                            FarmaUtility.moveFocus(txtEntero);
                        }

                    }
                } else {
                    //INI ASOSA - 28/08/2014

                    int cantidadMaxExceso = (int)(precepTotal * cantMultWithoutIP);
                    if (ptotalProd <= cantidadMaxExceso) {
                        rpta = true;
                    } else {
                        FarmaUtility.showMessage(this, "Máximo puede recepcionarse " + cantidadMaxExceso + " Unidades",
                                                 null);
                        FarmaUtility.moveFocus(txtEntero);
                    }

                    //FIN ASOSA - 28/08/2014
                }
            } else {

                if (valInnerPackProd >= 1) {
                    int cantPaquetes = 0;
                    cantPaquetes = ptotalProd % valInnerPackProd;
                    if (cantPaquetes > 0) {
                        FarmaUtility.showMessage(this,
                                                 "Debe ingresar cantidades multiplos de " + valInnerPackProd + "  unidades",
                                                 null);
                        FarmaUtility.moveFocus(txtEntero);
                    } else {
                        rpta = true;

                    }
                } else {
                    rpta = true;
                }
            }
        }
        return rpta;
    }

    private void aceptaCantidadIngresada() {
        int ctd = 0;
        for(int i=0;i<vListCantidadLote.size();i++)
            ctd+= Integer.parseInt(FarmaUtility.getValueFieldArrayList(vListCantidadLote, i, 0));
        
        if(ctd>Integer.parseInt(txtFraccion.getText())){
            FarmaUtility.showMessage(this,"No puede ingresar una cantidad mayor de la esperada en la OC.",txtEntero);
        }
        else{
            if(vListCantidadLote.size()==0){
                FarmaUtility.showMessage(this,"No ingreso ningúna cantidad, si tenia datos " +
                                         "antes ingresados estos se van a eliminar.",txtEntero);
            }
            
            if (JConfirmDialog.rptaConfirmDialog(this, "¿Está seguro de grabar los datos?")) {
                cerrarVentana(true);
            }
        }
    }
    
    public void listaTabla(){
        //for(int i=0;i<tableModel.data.size();i++)
        //tableModel.data.remove(i);
        tableModel.data = vListCantidadLote;
        tblListaCantidadLote.repaint();
        jspCantidadLote.getViewport().add(tblListaCantidadLote, null);
        FarmaUtility.ordenar(tblListaCantidadLote, tableModel, 9, FarmaConstants.ORDEN_DESCENDENTE);
        this.repaint();
    }
    
    public int getSumUnidades(){
        int ctd = 0;
        for(int i=0;i<tableModel.data.size();i++)
            ctd+= Integer.parseInt(FarmaUtility.getValueFieldArrayList(tableModel.data, i, 0));
        return ctd;
    }

    private void eliminarCantidadLote() {
        int pos = tblListaCantidadLote.getSelectedRow();
        if(pos>=0){
            if (JConfirmDialog.rptaConfirmDialog(this, "¿Está seguro de eliminar la fila seleccionada?")) {
                txtEntero.setText("");
                txtLote.setText("");
                txtFechaVenc.setText("");
                String pSec = FarmaUtility.getValueFieldArrayList(tableModel.data, pos,9);
                for(int i=0;i<vListCantidadLote.size();i++){
                    if(FarmaUtility.getValueFieldArrayList(vListCantidadLote,i,9).trim().equalsIgnoreCase(pSec))
                        vListCantidadLote.remove(i);
                }
                listaTabla();
                FarmaUtility.showMessage(this,"Se eliminó correctamente.",txtEntero);
            }
        }
        else
            FarmaUtility.showMessage(this,"Debe seleccionar la fila que desea eliminar.",txtEntero);
    }
    /*
    vAux.add(txtEntero.getText().trim()); // 0
    vAux.add(txtLote.getText().trim());// 1
    vAux.add(txtFechaVenc.getText().trim());// 2
    vAux.add(" ");// FECHA REGISTRO // 3
    vAux.add(lblCodeProduct.getText().trim());// 4
    vAux.add(txtFraccion.getText().trim());// 5
    vAux.add(txtPrecioUnitario.getText().trim());// 6
    vAux.add(txtIGV.getText().trim());// 7
    vAux.add(strCodigoOrdCompDetalle.trim());// 8
    vAux.add(vListCantidadLote.size()+1+"");//SECUENCIA// 9
     * */
}
