package mifarma.ptoventa.tomainventario;


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
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.DlgIngresarLote;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.tomainventario.reference.ConstantsTomaInv;
import mifarma.ptoventa.tomainventario.reference.DBTomaInv;
import mifarma.ptoventa.tomainventario.reference.VariablesTomaInv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgIngresoCantidadTomaLote extends JDialog {
    Frame myParentFrame;
    
    private static final Logger log = LoggerFactory.getLogger(DlgIngresoCantidadToma.class);


    private JPanelWhite jContentPane = new JPanelWhite();

    private BorderLayout borderLayout2 = new BorderLayout();

    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    
    private JPanelTitle jPanelLista = new JPanelTitle();

    private JScrollPane jScrollLista = new JScrollPane();
    
    private JTable tblRelacionProductosTomaLote = new JTable();
    
    FarmaTableModel tableModelProductosTomaLote;
    
    private JButtonLabel btnRelacionProductoTomaLote = new JButtonLabel();

    private JLabel jLabel1 = new JLabel();

    private JLabel jLabel2 = new JLabel();

    private JLabel lblCodigo = new JLabel();

    private JLabel lblDescripcion = new JLabel();

    private JLabel jLabel5 = new JLabel();

    private JLabel lblUnidadPresentacion = new JLabel();

    private JLabel jLabel7 = new JLabel();

    private JLabel lblLaboratorio = new JLabel();

    private JButtonLabel jButtonLabel1 = new JButtonLabel();

    private JTextFieldSanSerif txtEntero = new JTextFieldSanSerif();
    
    private JTextFieldSanSerif txtLote = new JTextFieldSanSerif();
    private JButtonLabel btnLote = new JButtonLabel();
    private JTextFieldSanSerif txtFechaLote = new JTextFieldSanSerif();
    private JButtonLabel btnFechaLote = new JButtonLabel();

    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JLabelFunction lblF1 = new JLabelFunction();

    private JLabelFunction jLabelFunction2 = new JLabelFunction();
    
    private JLabelFunction lblModificarCantidad = new JLabelFunction();
    private JLabel lblTValorFraccion = new JLabel();
    private JLabel lblValorFraccion = new JLabel();
    private JTextFieldSanSerif txtFraccion = new JTextFieldSanSerif();
    private JButtonLabel lblFraccion = new JButtonLabel();
    private JLabel lblUnidadVenta = new JLabel();
    private JLabel jLabel6 = new JLabel();


    int Col_Entero=6;
    int Col_Fraccion=7;
    int Col_Lote=4;
    int Col_Fecha_Lote=5;
    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgIngresoCantidadTomaLote() {
        this(null, "", false);
    }

    public DlgIngresoCantidadTomaLote(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(626, 500));
        this.getContentPane().setLayout(borderLayout2);
        this.setTitle("Ingreso de Cantidad Lote");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        jPanelWhite1.setBounds(new Rectangle(15, 15, 585, 220));
        jPanelWhite1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        jPanelWhite1.setLayout(null);
        jPanelLista.setBounds(new Rectangle(15, 250, 585, 25));
        jPanelLista.setLayout(null);
        jScrollLista.setBounds(new Rectangle(15, 275, 585, 145));
        jLabel1.setText("Codigo :");
        jLabel1.setBounds(new Rectangle(25, 15, 50, 15));
        jLabel1.setFont(new Font("SansSerif", 1, 11));
        jLabel1.setForeground(new Color(43, 141, 39));
        jLabel2.setText("Descripcion :");
        jLabel2.setBounds(new Rectangle(25, 40, 85, 15));
        jLabel2.setFont(new Font("SansSerif", 1, 11));
        jLabel2.setForeground(new Color(43, 141, 39));
        lblCodigo.setText("914005");
        lblCodigo.setBounds(new Rectangle(135, 15, 60, 15));
        lblCodigo.setFont(new Font("SansSerif", 1, 11));
        lblCodigo.setForeground(new Color(255, 130, 14));
        lblDescripcion.setText("ACTIVEL SINGLES");
        lblDescripcion.setBounds(new Rectangle(135, 40, 205, 15));
        lblDescripcion.setFont(new Font("SansSerif", 1, 11));
        lblDescripcion.setForeground(new Color(255, 130, 14));
        jLabel5.setText("Unid de Presentacion :");
        jLabel5.setBounds(new Rectangle(25, 65, 125, 15));
        jLabel5.setFont(new Font("SansSerif", 1, 11));
        jLabel5.setForeground(new Color(43, 141, 39));
        lblUnidadPresentacion.setText("CJA/20");
        lblUnidadPresentacion.setBounds(new Rectangle(170, 65, 170, 15));
        lblUnidadPresentacion.setFont(new Font("SansSerif", 1, 11));
        lblUnidadPresentacion.setForeground(new Color(255, 130, 14));
        jLabel7.setText("Laboratorio :");
        jLabel7.setBounds(new Rectangle(25, 150, 80, 15));
        jLabel7.setFont(new Font("SansSerif", 1, 11));
        jLabel7.setForeground(new Color(43, 141, 39));
        lblLaboratorio.setText("3M PERU S.A.");
        lblLaboratorio.setBounds(new Rectangle(135, 150, 240, 15));
        lblLaboratorio.setFont(new Font("SansSerif", 1, 11));
        lblLaboratorio.setForeground(new Color(255, 130, 14));
        jButtonLabel1.setText("Entero : ");
        jButtonLabel1.setBounds(new Rectangle(25, 165, 75, 20));
        jButtonLabel1.setForeground(new Color(255, 130, 14));
        jButtonLabel1.setMnemonic('e');
        jButtonLabel1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonLabel1_actionPerformed(e);
            }
        });
        txtEntero.setBounds(new Rectangle(25, 190, 60, 20));
        txtEntero.setDocument(new FarmaLengthText(5));
        txtEntero.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtEntero_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtCantidad_keyTyped(e);
            }
        });
        txtLote.setBounds(new Rectangle(175, 190, 90, 20));
        txtLote.setLengthText(100);
        txtLote.setEnabled(false);
        txtLote.setEditable(false);
        txtLote.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtLote_keyPressed(e);
            }


            });
        btnLote.setText("Lote:");
        btnLote.setBounds(new Rectangle(180, 170, 40, 15));
        btnLote.setForeground(new Color(255, 130, 14));
        //   btnLote.setMnemonic('l');
        btnLote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
             //   btnLote_actionPerformed(e);
            }

            });

        txtFechaLote.setBounds(new Rectangle(280, 190, 90, 20));
        txtFechaLote.setLengthText(10);
        txtFechaLote.setEnabled(false);
        txtFechaLote.setEditable(false);
        txtFechaLote.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaLote_keyPressed(e);
            }
            });
        btnFechaLote.setText("Fecha Venci.:");
        btnFechaLote.setBounds(new Rectangle(280, 170, 80, 15));
        btnFechaLote.setForeground(new Color(255, 130, 14));
        // btnFechaLote.setMnemonic('h');
        btnFechaLote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
             //   btnFechaLote_actionPerformed(e);
                    btnFechaLote_actionPerformed(e);
                }

            });
        lblF1.setBounds(new Rectangle(50, 440, 115, 20));
        lblF1.setText("[ F1 ] Registrar Lote");
        lblF1.setVisible(true);
        lblModificarCantidad.setBounds(new Rectangle(185, 440, 140, 20));
        lblModificarCantidad.setText("[ + ] Modificar Cantidad");
        lblModificarCantidad.setVisible(true);
        jLabelFunction1.setBounds(new Rectangle(345, 440, 100, 20));
        jLabelFunction1.setText("[ Enter ] Aceptar");
        jLabelFunction2.setBounds(new Rectangle(465, 440, 100, 20));
        jLabelFunction2.setText("[ ESCAPE ] Cerrar");
        lblTValorFraccion.setText("CJA/20");
        lblTValorFraccion.setBounds(new Rectangle(135, 125, 170, 15));
        lblTValorFraccion.setFont(new Font("SansSerif", 1, 11));
        lblTValorFraccion.setForeground(new Color(255, 130, 14));
        lblValorFraccion.setText("Valor Fraccion :");
        lblValorFraccion.setBounds(new Rectangle(25, 125, 85, 15));
        lblValorFraccion.setFont(new Font("SansSerif", 1, 11));
        lblValorFraccion.setForeground(new Color(43, 141, 39));
        txtFraccion.setBounds(new Rectangle(100, 190, 60, 20));
        txtFraccion.setDocument(new FarmaLengthText(5));
        txtFraccion.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFraccion_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtCantidad_keyTyped(e);
            }
        });
        lblFraccion.setText("Fraccion :");
        lblFraccion.setBounds(new Rectangle(100, 165, 75, 20));
        lblFraccion.setForeground(new Color(255, 130, 14));
        lblFraccion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonLabel1_actionPerformed(e);
            }
        });
        lblUnidadVenta.setText("CJA/20");
        lblUnidadVenta.setBounds(new Rectangle(120, 95, 170, 15));
        lblUnidadVenta.setFont(new Font("SansSerif", 1, 11));
        lblUnidadVenta.setForeground(new Color(255, 130, 14));
        jLabel6.setText("Unid de Venta :");
        jLabel6.setBounds(new Rectangle(25, 95, 125, 15));
        jLabel6.setFont(new Font("SansSerif", 1, 11));
        jLabel6.setForeground(new Color(43, 141, 39));
        
        btnRelacionProductoTomaLote.setText("Relacion de Producto por Lote");
        btnRelacionProductoTomaLote.setBounds(new Rectangle(10, 0, 200, 25));
        btnRelacionProductoTomaLote.setMnemonic('r');
        btnRelacionProductoTomaLote.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnRelacionProductoLote_actionPerformed(e);
                }
            });

        jPanelWhite1.add(jLabel6, null);
        jPanelWhite1.add(lblUnidadVenta, null);
        jPanelWhite1.add(lblFraccion, null);
        jPanelWhite1.add(txtFraccion, null);
        jPanelWhite1.add(lblValorFraccion, null);
        jPanelWhite1.add(lblTValorFraccion, null);
        jPanelWhite1.add(txtEntero, null);
        jPanelWhite1.add(txtLote, null);
        jPanelWhite1.add(txtFechaLote, null);
        jPanelWhite1.add(btnFechaLote, null);
        jPanelWhite1.add(jButtonLabel1, null);
        jPanelWhite1.add(lblLaboratorio, null);
        jPanelWhite1.add(jLabel7, null);
        jPanelWhite1.add(lblUnidadPresentacion, null);
        jPanelWhite1.add(jLabel5, null);
        jPanelWhite1.add(lblDescripcion, null);
        jPanelWhite1.add(lblCodigo, null);
        jPanelWhite1.add(jLabel2, null);
        jPanelWhite1.add(jLabel1, null);
        jPanelWhite1.add(btnLote, null);
        jContentPane.add(jLabelFunction2, null);
        jContentPane.add(jLabelFunction1, null);
        jContentPane.add(lblModificarCantidad, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(jPanelWhite1, null);
        jScrollLista.getViewport().add(tblRelacionProductosTomaLote, null);
        jContentPane.add(jScrollLista, null);
        jPanelLista.add(btnRelacionProductoTomaLote, null);
        jContentPane.add(jPanelLista, null);


        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTableListaProductosXLote();
        
        if(VariablesTomaInv.vIndLoteMayorista.equals("S")){
            txtLote.setEnabled(true);
            txtLote.setEditable(true);
            }
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTableListaProductosXLote() {
        tableModelProductosTomaLote =
                new FarmaTableModel(ConstantsTomaInv.columnsListaProductosTomaLote, ConstantsTomaInv.defaultValuesListaProductosTomaLote,
                                    0);
        FarmaUtility.initSimpleList(tblRelacionProductosTomaLote, tableModelProductosTomaLote,
                                    ConstantsTomaInv.columnsListaProductosTomaLote);
        //permite que no se muevan las columnas de Jtable
        tblRelacionProductosTomaLote.getTableHeader().setReorderingAllowed(false);
        cargaListaProductosTomaLote();
    }
    private void cargaListaProductosTomaLote() {
        try {
            DBTomaInv.getListaProductoTomaLote(tableModelProductosTomaLote);
            if (tblRelacionProductosTomaLote.getRowCount() > 0)
                FarmaUtility.ordenar(tblRelacionProductosTomaLote, tableModelProductosTomaLote, 1,
                                     FarmaConstants.ORDEN_ASCENDENTE);
            log.debug("se cargo la lista de de productos x Lote");
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al obtener la lista de productos x lote: \n" +
                    sql.getMessage(), txtEntero);
        }
    }
    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        VariablesTomaInv.vIndActualizaProd="N";
        mostrarDatos();
        
        if (VariablesTomaInv.vFraccion.equalsIgnoreCase("1")) {
            txtFraccion.setEditable(false);
            txtFraccion.setText("0");
        }
        FarmaUtility.moveFocus(txtEntero);
        
        if(VariablesTomaInv.vIndLoteMayorista.equals("S")){
             txtLote.setEnabled(true);
             txtLote.setEditable(true);
            }
    }

    private void txtCantidad_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtEntero, e);


    }

    private void txtFraccion_keyPressed(KeyEvent e) {
       /* if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            chkKeyPressed(e);
        } else {
            chkKeyPressed(e);
        }*/
       if (e.getKeyCode() == KeyEvent.VK_ENTER) {
           if (!txtLote.isEditable()  || //En el proceso de modificar el campo lote muestra inactivo
                VariablesTomaInv.vIndActualizaProd.equalsIgnoreCase(FarmaConstants.INDICADOR_S)
                )
               chkKeyPressed(e);
           else
               FarmaUtility.moveFocus(txtLote);
       } else
           chkKeyPressed(e);
       
        
        FarmaGridUtils.aceptarTeclaPresionada(e, tblRelacionProductosTomaLote,null, 1);
        if (e.getKeyChar() == '+') {
            VariablesTomaInv.vIndActualizaProd="S";
            mostrarDatos();
        }
        
    }

    private void txtEntero_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!txtFraccion.isEditable() )
                chkKeyPressed(e);
            else
                FarmaUtility.moveFocus(txtFraccion);
        } else
            chkKeyPressed(e);
        
        FarmaGridUtils.aceptarTeclaPresionada(e, tblRelacionProductosTomaLote,null, 1);
        if (e.getKeyChar() == '+') {
            VariablesTomaInv.vIndActualizaProd="S";
             mostrarDatos();
        }
    }
    private void txtFechaLote_keyPressed(KeyEvent e) {
       
    }
    private void txtLote_keyPressed(KeyEvent e) {
        chkKeyPressed(e);

        FarmaGridUtils.aceptarTeclaPresionada(e, tblRelacionProductosTomaLote,null, 1);
        if (e.getKeyChar() == '+') {
            VariablesTomaInv.vIndActualizaProd="S";
            mostrarDatos();
        }
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            e.consume();
            this.setVisible(false);
            cerrarVentana(true);
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (datosValidados()) {
                try {
                    ingresarCantidad();
                    FarmaUtility.aceptarTransaccion();
                    if (VariablesTomaInv.vIndActualizaProd.equals(FarmaConstants.INDICADOR_S)) { // si relizo la actualizacion
                    FarmaUtility.showMessage(this, "Se realizo la actualizacion de cantidad con exito", txtEntero);
                        VariablesTomaInv.vIndActualizaProd=FarmaConstants.INDICADOR_N;
                        cargaListaProductosTomaLote();
                        
                    }else if(VariablesTomaInv.vIndActualizaProd.equals(FarmaConstants.INDICADOR_N)){
                        // Registra la producto x lote  
                        FarmaUtility.showMessage(this, "Se realizo el registro de cantidad con exito", txtEntero);
                            cargaListaProductosTomaLote();
                        }
                    //Actualiza la lista
                   // initTableListaProductosXLote() ;
                    mostrarDatos();
                } catch (SQLException sql) {
                    
                    FarmaUtility.liberarTransaccion();
                    log.error("", sql);
                    if (sql.getErrorCode() == 20000) {
                        FarmaUtility.showMessage(this, "Ya existe el lote en la lista", txtEntero);
                        txtLote.setText("");
                    } else
                        FarmaUtility.showMessage(this, "Error al registrar la cantidad : \\n\" " + sql.getMessage(), txtEntero);
                    
                }
            }
        } else if (UtilityPtoVenta.verificaVK_F1(e)) {
            DlgIngresarLote ingresarLote =
                new DlgIngresarLote(myParentFrame, "Registrar Lote", true,txtLote.getText().trim(),true);
            ingresarLote.codprodxx = VariablesTomaInv.vCodProd;
            ingresarLote.setVisible(true);
        }
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void mostrarDatos() {
            
        lblCodigo.setText(VariablesTomaInv.vCodProd);
        lblDescripcion.setText(VariablesTomaInv.vDesProd);
        lblUnidadPresentacion.setText(VariablesTomaInv.vUnidPresentacion);
        lblUnidadVenta.setText(VariablesTomaInv.vUnidVta);
        lblTValorFraccion.setText(VariablesTomaInv.vFraccion.trim());
        lblLaboratorio.setText(VariablesTomaInv.vNomLab);
        
        if (VariablesTomaInv.vIndActualizaProd.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
 
            ArrayList pDatos = new ArrayList();
            if (tblRelacionProductosTomaLote.getRowCount() <= 0)
                return;

            int vFila = tblRelacionProductosTomaLote.getSelectedRow();
            txtLote.setEnabled(false); // no se debe modificar el lote
            txtLote.setEditable(false);
            String valorEntero=FarmaUtility.getValueFieldArrayList(tableModelProductosTomaLote.data, vFila, Col_Entero);
            txtEntero.setText(valorEntero.replaceAll(",", ""));

        
            String valor =FarmaUtility.getValueFieldArrayList(tableModelProductosTomaLote.data, vFila, Col_Fraccion);
            FarmaUtility.moveFocus(txtEntero);
            if(valor==null || "".equals(valor.trim()) //&& !txtFraccion.isEditable()
            ){
                txtFraccion.setText("0");
            }else{
                txtFraccion.setText(valor);
            }
            txtLote.setText(FarmaUtility.getValueFieldArrayList(tableModelProductosTomaLote.data, vFila, Col_Lote));
            txtFechaLote.setText(FarmaUtility.getValueFieldArrayList(tableModelProductosTomaLote.data, vFila, Col_Fecha_Lote));
         
        } else if (VariablesTomaInv.vIndActualizaProd.equalsIgnoreCase(FarmaConstants.INDICADOR_N)){
            if(VariablesTomaInv.vIndLoteMayorista.equals("S")){
                 txtLote.setEnabled(true);
                 txtLote.setEditable(true);
                }
            txtEntero.setText("");
            FarmaUtility.moveFocus(txtEntero);
            if (VariablesTomaInv.vFraccion.equalsIgnoreCase("1")) {
                txtFraccion.setEditable(false);
                txtFraccion.setText("0");
            }else {
                txtFraccion.setText("");
            }
            txtLote.setText("");
            txtFechaLote.setText("");
        }
    }

    
    private void ingresarCantidad() throws SQLException {
        int entero = Integer.parseInt(txtEntero.getText().trim());
        int fraccion = Integer.parseInt(txtFraccion.getText().trim());
        log.debug("entero : " + entero);
        log.debug("fraccion : " + fraccion);
        int cantidad = entero * Integer.parseInt(VariablesTomaInv.vFraccion) + fraccion;
        String cantidadCompleta = "" + cantidad;
        log.debug("cantidad : " + cantidadCompleta);
        DBTomaInv.ingresaCantidadProdInv(cantidadCompleta);
     
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private boolean datosValidados() {
        boolean rpta = true;
        String pIndExisteLote="N";
        String pLote= txtLote.getText().trim();
        if (txtEntero.getText().trim().length() == 0) {
            rpta = false;
            FarmaUtility.showMessage(this, "Ingrese la cantidad entera", txtEntero);
        } else if (txtFraccion.getText().trim().length() == 0 && txtFraccion.isEditable()) {
            rpta = false;
            FarmaUtility.showMessage(this, "Ingrese la cantidad fraccion", txtFraccion);
        } else if (txtFraccion.getText().trim().equalsIgnoreCase(VariablesTomaInv.vFraccion)) {
            rpta = false;
            FarmaUtility.showMessage(this, "La cantidad de Fraccion no puede ser igual al Valor de Fraccion",
                                     txtEntero);
        } else if (Integer.parseInt(txtFraccion.getText().trim()) > Integer.parseInt(VariablesTomaInv.vFraccion)) {
            rpta = false;
            FarmaUtility.showMessage(this, "La cantidad de Fraccion ingresada no puede ser mayor al valor de Fraccion",
                                     txtFraccion);
        } else if (pLote.length() == 0 && txtLote.isEditable() && 
                   VariablesTomaInv.vIndActualizaProd.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
            rpta=false;
            FarmaUtility.showMessage(this, "Debe ingresar el lote.", txtLote);
        } else if (pLote.length()> 0 && txtLote.isEditable()&&
            VariablesTomaInv.vIndActualizaProd.equalsIgnoreCase(FarmaConstants.INDICADOR_N)
            ) {
            try {
                pIndExisteLote= DBTomaInv.getExisteLotePorProducto(VariablesTomaInv.vCodProd,pLote);
                log.info("existe lote:"+pIndExisteLote);
                if(pIndExisteLote.equals("N")){
                        rpta = false;
                        FarmaUtility.showMessage(this, "El lote ingresado no existe", txtLote);
                    }
                VariablesTomaInv.vLoteIngresado=pLote;
            } catch (SQLException e) {
                rpta = false;
                FarmaUtility.showMessage(this, "Ocurrio un error al validar el lote.", txtLote);
                
                log.info("error validar lote por producto" + e.getMessage());
        }
        }else if (VariablesTomaInv.vIndActualizaProd.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
            // CUANDO ACTUALIZA EL LOTE ES EL MISMO
            VariablesTomaInv.vLoteIngresado=pLote;
            }

        return rpta;
    }

    private void jButtonLabel1_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtEntero);
    }

    private void btnRelacionProductoLote_actionPerformed(ActionEvent e) {
    }

    private void btnFechaLote_actionPerformed(ActionEvent e) {
    }
}
