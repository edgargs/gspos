package mifarma.ptoventa.inventariociclico;


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

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import mifarma.common.FarmaConnection;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.DlgIngresarLote;
import mifarma.ptoventa.inventariociclico.reference.ConstantsInvCiclico;
import mifarma.ptoventa.inventariociclico.reference.DBInvCiclico;
import mifarma.ptoventa.inventariociclico.reference.VariablesInvCiclico;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.tomainventario.reference.DBTomaInv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgIngresoCantidadInvCiclico extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgIngresoCantidadInvCiclico.class);
    FarmaTableModel tableModelProductosTomaCiclicoLote;
    FarmaTableModel tableModelKardex;

    Frame myParentFrame;

    private JPanelWhite jContentPane = new JPanelWhite();

    private BorderLayout borderLayout2 = new BorderLayout();

    private JPanelWhite jPanelWhite1 = new JPanelWhite();

    private JLabel jLabel1 = new JLabel();


    private JLabel lblCodigo = new JLabel();

    private JLabel lblDescripcion = new JLabel();

    private JLabel jLabel5 = new JLabel();

    private JLabel lblUnidadPresentacion = new JLabel();

    private JLabel jLabel7 = new JLabel();

    private JLabel lblLaboratorio = new JLabel();

    private JButtonLabel lblEntero = new JButtonLabel();

    private JTextFieldSanSerif txtEntero = new JTextFieldSanSerif();

    private JLabelFunction jLabelFunction1 = new JLabelFunction();

    private JLabelFunction jLabelFunction2 = new JLabelFunction();
    private JLabelFunction jLblFncRegistraLote = new JLabelFunction();
    private JLabelFunction jLblFncModifyProd = new JLabelFunction();
    private JLabelFunction jLblIngresMasterPack = new JLabelFunction();
    
    private JLabel lblTValorFraccion = new JLabel();
    private JLabel lblValorFraccion = new JLabel();
    private JTextFieldSanSerif txtFraccion = new JTextFieldSanSerif();
    private JButtonLabel lblFraccion = new JButtonLabel();
    private JLabel lblUnidadVenta = new JLabel();
    private JLabel jLabel6 = new JLabel();
    private JScrollPane srcKArdex = new JScrollPane();
    private JTable tblKardex = new JTable();
    private JPanelTitle pnllTitle1 = new JPanelTitle();
    private JButtonLabel btnRelacionMovimiento = new JButtonLabel();
    private JButtonLabel lblLote = new JButtonLabel();
    private JButtonLabel lblFecVenci = new JButtonLabel();
    private JTextField txtLote = new JTextField();
    private JTextField txtFecVenci = new JTextField();
    private JTextField txtMasterPack = new JTextField();
    private JButtonLabel lblMasterPack = new JButtonLabel();
    private JPanelTitle pnlTitle2 = new JPanelTitle();
    private JScrollPane jScrPnProdLote = new JScrollPane();
    private JTable tblRelacionProdLote = new JTable();
    private JButtonLabel btnRelacionProductoLote = new JButtonLabel();
    
    

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgIngresoCantidadInvCiclico() {
        this(null, "", false);
    }

    public DlgIngresoCantidadInvCiclico(Frame parent, String title, boolean modal) {
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
        if(UtilityPtoVenta.isLocalVentaMayorista()){
            this.setSize(new Dimension(672, 532));
            jLabelFunction1.setBounds(new Rectangle(430, 475, 105, 20));
            jLabelFunction2.setBounds(new Rectangle(565, 475, 85, 20));
        }else{
            this.setSize(new Dimension(672, 400));
            jLabelFunction1.setBounds(new Rectangle(370, 335, 105, 20));
            jLabelFunction2.setBounds(new Rectangle(480, 335, 85, 20));  
        }
            
       /* this.setSize(new Dimension(590, 532));
        jLabelFunction1.setBounds(new Rectangle(370, 475, 105, 20));
        jLabelFunction2.setBounds(new Rectangle(480, 475, 85, 20));*/
        
        jLabelFunction1.setText("[ Enter ] Aceptar");
        jLabelFunction2.setText("[ ESC ] Cerrar");    
        this.getContentPane().setLayout(borderLayout2);
        this.setTitle("Ingreso de Cantidad");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        jPanelWhite1.setBounds(new Rectangle(10, 15, 640, 190));
        jPanelWhite1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        jPanelWhite1.setLayout(null);    
        jLabel1.setText("Codigo :");
        jLabel1.setBounds(new Rectangle(15, 15, 50, 15));
        jLabel1.setFont(new Font("SansSerif", 1, 11));
        jLabel1.setForeground(new Color(43, 141, 39));
        lblCodigo.setText("914005");
        lblCodigo.setBounds(new Rectangle(95, 15, 60, 15));
        lblCodigo.setFont(new Font("SansSerif", 1, 11));
        lblCodigo.setForeground(new Color(255, 130, 14));
        lblDescripcion.setText("ACTIVEL SINGLES");
        lblDescripcion.setBounds(new Rectangle(155, 15, 250, 15));
        lblDescripcion.setFont(new Font("SansSerif", 1, 11));
        lblDescripcion.setForeground(new Color(255, 130, 14));
        jLabel5.setText("Unid de Presentacion :");
        jLabel5.setBounds(new Rectangle(15, 40, 125, 15));
        jLabel5.setFont(new Font("SansSerif", 1, 11));
        jLabel5.setForeground(new Color(43, 141, 39));
        lblUnidadPresentacion.setText("CJA/20");
        lblUnidadPresentacion.setBounds(new Rectangle(155, 40, 170, 15));
        lblUnidadPresentacion.setFont(new Font("SansSerif", 1, 11));
        lblUnidadPresentacion.setForeground(new Color(255, 130, 14));
        jLabel7.setText("Laboratorio :");
        jLabel7.setBounds(new Rectangle(15, 115, 80, 15));
        jLabel7.setFont(new Font("SansSerif", 1, 11));
        jLabel7.setForeground(new Color(43, 141, 39));
        lblLaboratorio.setText("3M PERU S.A.");
        lblLaboratorio.setBounds(new Rectangle(155, 115, 240, 15));
        lblLaboratorio.setFont(new Font("SansSerif", 1, 11));
        lblLaboratorio.setForeground(new Color(255, 130, 14));
        lblEntero.setText("Entero : ");
        lblEntero.setBounds(new Rectangle(135, 140, 50, 20));
        lblEntero.setForeground(new Color(255, 130, 14));
        lblEntero.setMnemonic('e');
        lblEntero.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblEntero_actionPerformed(e);
            }
        });
        txtEntero.setBounds(new Rectangle(130, 160, 60, 20));
        txtEntero.setDocument(new FarmaLengthText(5));

        txtEntero.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtEntero_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtCantidad_keyTyped(e);
            }
        });
        


        jLblFncRegistraLote.setBounds(new Rectangle(20, 475, 120, 20));
        jLblFncRegistraLote.setText("[F1] Registrar Lote");
        jLblIngresMasterPack.setBounds(new Rectangle(175, 475, 130, 20));
        jLblIngresMasterPack.setText("[F2] Ing. Master Pack");
        jLblFncModifyProd.setBounds(new Rectangle(320, 475, 85, 20));
        jLblFncModifyProd.setText("[+] Mod Prod.");        
        lblTValorFraccion.setText("CJA/20");
        lblTValorFraccion.setBounds(new Rectangle(155, 90, 170, 15));
        lblTValorFraccion.setFont(new Font("SansSerif", 1, 11));
        lblTValorFraccion.setForeground(new Color(255, 130, 14));
        lblValorFraccion.setText("Valor Fraccion :");
        lblValorFraccion.setBounds(new Rectangle(15, 90, 85, 15));
        lblValorFraccion.setFont(new Font("SansSerif", 1, 11));
        lblValorFraccion.setForeground(new Color(43, 141, 39));
        txtFraccion.setBounds(new Rectangle(230, 160, 70, 20));
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
        lblFraccion.setBounds(new Rectangle(235, 140, 60, 20));
        lblFraccion.setForeground(new Color(255, 130, 14));
        lblFraccion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblEntero_actionPerformed(e);
            }
        });
        lblUnidadVenta.setText("CJA/20");
        lblUnidadVenta.setBounds(new Rectangle(155, 65, 170, 15));
        lblUnidadVenta.setFont(new Font("SansSerif", 1, 11));
        lblUnidadVenta.setForeground(new Color(255, 130, 14));
        jLabel6.setText("Unid de Venta :");
        jLabel6.setBounds(new Rectangle(15, 65, 85, 15));
        jLabel6.setFont(new Font("SansSerif", 1, 11));
        jLabel6.setForeground(new Color(43, 141, 39));
        
        if(UtilityPtoVenta.isLocalVentaMayorista()){            
            srcKArdex.setBounds(new Rectangle(10, 235, 640, 90));
            
        }else{
            srcKArdex.setBounds(new Rectangle(10, 235, 640, 90));
            
        }

        pnllTitle1.setBounds(new Rectangle(10, 210, 640, 25));
        btnRelacionMovimiento.setText("Relación de Movimientos del Producto");
        btnRelacionMovimiento.setBounds(new Rectangle(15, 5, 215, 15));
        btnRelacionMovimiento.setMnemonic('R');
        btnRelacionMovimiento.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionMovimiento_actionPerformed(e);
            }
        });
        
        btnRelacionProductoLote.setText("Relación de Productos por Lote");
        btnRelacionProductoLote.setBounds(new Rectangle(15, 5, 215, 15));
       /* btnRelacionProductoLote.setMnemonic('a');
        btnRelacionProductoLote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
             //   btnRelacionProductoLote_actionPerformed(e);
            }
        });

*/
        lblLote.setText("Lote:");
        lblLote.setBounds(new Rectangle(385, 140, 40, 20));
        lblLote.setForeground(new Color(255, 130, 14));
        lblLote.setFont(new Font("SansSerif", 1, 11));
        lblFecVenci.setText("Fecha Vencimiento:");
        lblFecVenci.setBounds(new Rectangle(505, 140, 115, 20));
        lblFecVenci.setForeground(new Color(255, 130, 14));
        lblFecVenci.setFont(new Font("SansSerif", 1, 11));
        txtLote.setBounds(new Rectangle(335, 160, 140, 20));        
        txtLote.setDocument(new FarmaLengthText(15));
        txtLote.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtLote_keyPressed(e);
            }
            
            public void keyReleased(KeyEvent e) {
                FarmaUtility.ponerMayuscula(e, txtLote);
            }

        });        
        txtFecVenci.setBounds(new Rectangle(505, 160, 115, 20));
        txtFecVenci.setBackground(new Color(237, 237, 237));
        txtMasterPack.setBounds(new Rectangle(15, 160, 70, 20));
        txtMasterPack.setBackground(new Color(237, 237, 237));
        txtMasterPack.setDocument(new FarmaLengthText(4));
        txtMasterPack.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtMasterPack_keyPressed(e);
            }
            
            public void keyTyped(KeyEvent e) {
                txtCantidad_keyTyped(e);
            }            

        });
        lblMasterPack.setText("Master Pack:");
        lblMasterPack.setBounds(new Rectangle(15, 140, 80, 20));
        lblMasterPack.setForeground(new Color(255, 130, 14)); 
        lblMasterPack.setMnemonic('M');
        lblMasterPack.setFont(new Font("SansSerif", 1, 11));
        lblMasterPack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblMasterPack_actionPerformed(e);
            }
        });

        pnlTitle2.setBounds(new Rectangle(10, 330, 640, 30));
        pnlTitle2.setBackground(new Color(255, 130, 14));
        jScrPnProdLote.setBounds(new Rectangle(10, 360, 640, 110));
        jPanelWhite1.add(lblMasterPack, null);
        jPanelWhite1.add(txtMasterPack, null);
        jPanelWhite1.add(txtFecVenci, null);
        jPanelWhite1.add(txtLote, null);
        jPanelWhite1.add(lblFecVenci, null);
        jPanelWhite1.add(lblLote, null);
        jPanelWhite1.add(jLabel6, null);
        jPanelWhite1.add(lblUnidadVenta, null);
        jPanelWhite1.add(lblFraccion, null);
        jPanelWhite1.add(txtFraccion, null);
        jPanelWhite1.add(lblValorFraccion, null);
        jPanelWhite1.add(lblTValorFraccion, null);
        jPanelWhite1.add(txtEntero, null);
        jPanelWhite1.add(lblEntero, null);
        jPanelWhite1.add(lblLaboratorio, null);
        jPanelWhite1.add(jLabel7, null);
        jPanelWhite1.add(lblUnidadPresentacion, null);
        jPanelWhite1.add(jLabel5, null);
        jPanelWhite1.add(lblDescripcion, null);
        jPanelWhite1.add(lblCodigo, null);
        jPanelWhite1.add(jLabel1, null);
        pnllTitle1.add(btnRelacionMovimiento, null);
        pnlTitle2.add(btnRelacionProductoLote, null);
        jScrPnProdLote.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        tblRelacionProdLote.getTableHeader().setReorderingAllowed(false);
        tblRelacionProdLote.getTableHeader().setResizingAllowed(false);        
        tblRelacionProdLote.setName("tblRelacionProdLote");
        jScrPnProdLote.getViewport().add(tblRelacionProdLote, null);
        jContentPane.add(jScrPnProdLote, null);
        jContentPane.add(pnlTitle2, null);
        jContentPane.add(pnllTitle1, null);
        srcKArdex.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        tblKardex.getTableHeader().setReorderingAllowed(false);
        tblKardex.getTableHeader().setResizingAllowed(false);        
        srcKArdex.getViewport().add(tblKardex, null);
        jContentPane.add(srcKArdex, null);
        jContentPane.add(jLabelFunction2, null);
        jContentPane.add(jLabelFunction1, null);
        jContentPane.add(jLblFncRegistraLote, null);
        jContentPane.add(jLblIngresMasterPack, null);
        jContentPane.add(jLblFncModifyProd, null);
        jContentPane.add(jPanelWhite1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);

        //Asignacion de componentes para mayorista: 27/01/2016
        txtFecVenci.setEnabled(false);
        txtMasterPack.setEnabled(false);
   
        tblRelacionProdLote.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblRelacionProdLote_keyPressed(e);
            }

        }); 
        
        tblKardex.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                chkKeyPressed(e);
            }

        }); 
        
        
        if(!UtilityPtoVenta.isLocalVentaMayorista()){            
            txtMasterPack.setText("");
            txtMasterPack.setEnabled(false);
            txtMasterPack.setVisible(false);        
            txtLote.setText("");
            txtLote.setEnabled(false);
            txtLote.setVisible(false);                  
            txtFecVenci.setText("");
            txtFecVenci.setEnabled(false);
            txtFecVenci.setVisible(false);                  
            
            lblMasterPack.setVisible(false); 
            lblLote.setVisible(false); 
            lblFecVenci.setVisible(false); 
    
            jLblFncRegistraLote.setVisible(false);
            jLblIngresMasterPack.setVisible(false);
            jLblFncModifyProd.setVisible(false);
            btnRelacionProductoLote.setVisible(false);
            pnlTitle2.setVisible(false);
            jScrPnProdLote.setVisible(false);
            
            
            

        }

    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************	

    private void initialize() {
        initTable();
        if(UtilityPtoVenta.isLocalVentaMayorista()){
            initTableListaProductosXLote();
        }
    }

    private void initTableListaProductosXLote() {
        tableModelProductosTomaCiclicoLote =
                new FarmaTableModel(ConstantsInvCiclico.columnsListaProductosXLote, ConstantsInvCiclico.defaultValuesListaProductosXLote,
                                    0);
        FarmaUtility.initSimpleList(tblRelacionProdLote, tableModelProductosTomaCiclicoLote,ConstantsInvCiclico.columnsListaProductosXLote);
       cargaListaProductosXLote();
    }    

    private void initTable() {
        tableModelKardex =
                new FarmaTableModel(ConstantsInvCiclico.columnsListaMovsKardex, ConstantsInvCiclico.defaultListaMovsKardex,
                                    0);
        FarmaUtility.initSimpleList(tblKardex, tableModelKardex, ConstantsInvCiclico.columnsListaMovsKardex);
    }
    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaConnection.closeConnection();
        VariablesInvCiclico.vActionAdd=0;
        FarmaUtility.centrarVentana(this);
        mostrarDatos();
        cargaListaMovimientos();
        if (VariablesInvCiclico.vFraccion.equalsIgnoreCase("1")) {
            txtFraccion.setEnabled(false);
            txtFraccion.setEditable(false);
            txtFraccion.setText("0");
        }
        
        if(UtilityPtoVenta.isLocalVentaMayorista()){ 
            txtEntero.setText("");
        
            String reqLote= VariablesInvCiclico.vReqLote;            
            if(reqLote.equalsIgnoreCase("N")){
                txtLote.setEnabled(false);
                txtLote.setText("");
                txtLote.setBackground(new Color(237, 237, 237));
                
            }else{
                txtLote.setEnabled(true);
                txtLote.setText("");
                txtLote.setBackground(new Color(255, 255, 255));
                
            } 

            
            
        }

        FarmaUtility.moveFocus(txtEntero);
    }

    private void txtCantidad_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtEntero, e);
    }

    private void txtFraccion_keyPressed(KeyEvent e) {
        String sFraccionIngresada="";
        sFraccionIngresada=txtFraccion.getText().trim();
        
        if(UtilityPtoVenta.isLocalVentaMayorista()){
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            String sFraccion ="";
                sFraccion=txtFraccion.getText();
                if(sFraccion.equalsIgnoreCase("")){
                    FarmaUtility.showMessage(this, "Ingrese una Fraccion.", txtFraccion);
                }else{
                    
                    if(Integer.parseInt(sFraccionIngresada) > Integer.parseInt(VariablesInvCiclico.vFraccion)){
                        FarmaUtility.showMessage(this, "La cantidad ingresada: " +sFraccionIngresada+" no puede ser mayor al valor de Fraccion",
                                                 txtFraccion);
                             
                    }else{
     
                        if(VariablesInvCiclico.vActionAdd==1){
                        String sLote=txtLote.getText();                         
                         int   cantidad=0;
                         
                            int entero = Integer.parseInt(txtEntero.getText().trim());
                            int fraccion = Integer.parseInt(txtFraccion.getText().trim());
                            log.debug("entero : " + entero);
                            log.debug("fraccion : " + fraccion);
                            cantidad = entero * Integer.parseInt(VariablesInvCiclico.vFraccion) + fraccion;  
 
                            if (VariablesInvCiclico.vFraccion.equalsIgnoreCase("1")) {
                                txtFraccion.setEditable(false);
                                txtFraccion.setText("0");

                            }else{
                                    try {
                                        int iExcecSql=0;
                                        iExcecSql=DBInvCiclico.modificaLoteXProductoToma(sLote,cantidad);
                                        if(iExcecSql==1){
                                            FarmaUtility.aceptarTransaccion();
                                            FarmaUtility.showMessage(this, "Se modificaron los datos correctamente." , tblRelacionProdLote);
                                            cargaListaProductosXLote();
                                            VariablesInvCiclico.vActionAdd=0;
                                            txtEntero.setText("");
                                            
                                           String reqLote= VariablesInvCiclico.vReqLote;            
                                           if(reqLote.equalsIgnoreCase("S")){ 
                                               txtLote.setEnabled(true);
                                               txtLote.setText("");
                                               txtLote.setBackground(new Color(255, 255, 255)); 
                                           }else{                                        
                                               txtLote.setEnabled(false);
                                               txtLote.setText("");
                                               txtLote.setBackground(new Color(237, 237, 237));                                       
                                           } 
                                            FarmaUtility.moveFocus(txtEntero);
                                            txtFecVenci.setText("");
                                            if(txtFraccion.isEnabled()){
                                                txtFraccion.setText("");
                                            }else{
                                                txtFraccion.setText("0");
                                            }
                                            VariablesInvCiclico.vActionAdd=0;
                                           // FarmaUtility.moveFocusJTable(tblRelacionProdLote);
                                        }else{
                                            FarmaUtility.liberarTransaccion();
                                            FarmaUtility.showMessage(this, "Se produjo un error al modificar los datos" , this);
                                        }
                                        
                                    } catch (SQLException sql) {
                                        FarmaUtility.showMessage(this, "Ocurrió un error al modificar la cantidad del lote." +
                                                sql.getMessage(), this);    
                                    }
                                
                                    //FarmaUtility.moveFocus(txtFraccion);
                                }
                        
                        }else{
                            String reqLote= VariablesInvCiclico.vReqLote;            
                            if(reqLote.equalsIgnoreCase("S")){
                                FarmaUtility.moveFocus(txtLote);
                            }else{
                                FarmaUtility.showMessage(this, "El producto es sin lote, actualize la cantidad" , this);
                                //chkKeyPressed(e);
                            }   
                            
                            
                        }    
                        
                  
                        
                    } 
                    
                    
                }
      
            }else{
                    chkKeyPressed(e);
                }
        
            FarmaGridUtils.aceptarTeclaPresionada(e, tblRelacionProdLote, null, 1);
        }else{
            
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                chkKeyPressed(e);
            } else {
                chkKeyPressed(e);
            }
                    
        }
        

    }

    private void txtEntero_keyPressed(KeyEvent e) {
        
       if(UtilityPtoVenta.isLocalVentaMayorista()){
          
          String sEntero="";
          sEntero=txtEntero.getText();
          
       if (e.getKeyCode() == KeyEvent.VK_ENTER) {   
          
                      if(sEntero.equalsIgnoreCase("")){
                          FarmaUtility.showMessage(this, "Ingrese un Entero.", txtEntero);
                          
                      }else{
                         
                          if(VariablesInvCiclico.vActionAdd==1){
                          String sLote=txtLote.getText();                         
                           int   cantidad=0;
                              cantidad=Integer.parseInt(sEntero);
                              if (VariablesInvCiclico.vFraccion.equalsIgnoreCase("1")) {
                                  txtFraccion.setEditable(false);
                                  txtFraccion.setText("0");
                            try {
                                int iExcecSql=0;
                                iExcecSql=DBInvCiclico.modificaLoteXProductoToma(sLote,cantidad);
                                if(iExcecSql==1){
                                    FarmaUtility.aceptarTransaccion();
                                    FarmaUtility.showMessage(this, "Se modificaron los datos correctamente." , tblRelacionProdLote);
                                    cargaListaProductosXLote();
                                    txtEntero.setText("");
                                    FarmaUtility.moveFocus(txtEntero);
                                    txtFecVenci.setText("");
                                    if(txtFraccion.isEnabled()){
                                        txtFraccion.setText("0");
                                    }
                                    
                                    String reqLote= VariablesInvCiclico.vReqLote;            
                                    if(reqLote.equalsIgnoreCase("S")){ 
                                        txtLote.setEnabled(true);
                                        txtLote.setText("");
                                        txtLote.setBackground(new Color(255, 255, 255)); 
                                    }else{                                        
                                        txtLote.setEnabled(false);
                                        txtLote.setText("");
                                        txtLote.setBackground(new Color(237, 237, 237));                                       
                                    } 

                                    VariablesInvCiclico.vActionAdd=0;
                                   // FarmaUtility.moveFocusJTable(tblRelacionProdLote);
                                }else{
                                    FarmaUtility.liberarTransaccion();
                                    FarmaUtility.showMessage(this, "Se produjo un error al modificar los datos" , this);
                                }
                                
                            } catch (SQLException sql) {
                                FarmaUtility.showMessage(this, "Ocurrió un error al modificar la cantidad del lote." +
                                        sql.getMessage(), this);    
                            }
                              }else{
                                  
                                      FarmaUtility.moveFocus(txtFraccion);
                                  }
      
                          }else{
                              
                                  if (!txtFraccion.isEditable()){
                                  
                                     String reqLote= VariablesInvCiclico.vReqLote;            
                                     if(reqLote.equalsIgnoreCase("S")){                                        
                                         FarmaUtility.moveFocus(txtLote);
                                         
                                     }else{
                                         
                                         if (tblRelacionProdLote.getRowCount() >= 1){
                                            FarmaUtility.showMessage(this, "El producto no requiere lote, modifique la cantidad. " , this);
                                         }else{
                                            chkKeyPressed(e);  
                                         }
                                        
                                     }                                  
                                  }else{
                                      FarmaUtility.moveFocus(txtFraccion);   
                                  } 

                              }

                      }
                      
            
                    }else{                      
                       chkKeyPressed(e);                                 
                   }
           FarmaGridUtils.aceptarTeclaPresionada(e, tblRelacionProdLote, null, 1);           
       } else{
           
               if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                   if (!txtFraccion.isEditable()){
                      chkKeyPressed(e);       
                  } else
                       FarmaUtility.moveFocus(txtFraccion);
               } else
                   chkKeyPressed(e);
                FarmaGridUtils.aceptarTeclaPresionada(e, tblKardex, null, 1);
           }

    }
    

        
    private void tblRelacionProdLote_keyPressed(KeyEvent e) {
        //FarmaGridUtils.aceptarTeclaPresionada(e, tblRelacionProdLote, txtEntero, 5);
            chkKeyPressed(e);
       /* if(tblRelacionProdLote.isFocusable()){
                FarmaUtility.showMessage(this, "El registro esta seleccionado", this);
            }*/
        
    }
    private void txtMasterPack_keyPressed(KeyEvent e) {
        
        if(UtilityPtoVenta.isLocalVentaMayorista()){
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            String sMasterPack ="";
                sMasterPack=txtMasterPack.getText();
                if(sMasterPack.equalsIgnoreCase("")){
                    FarmaUtility.showMessage(this, "Ingrese un MasterPack.", txtMasterPack);
                }else{
                    String reqLote= VariablesInvCiclico.vReqLote;            
                    if(reqLote.equalsIgnoreCase("S")){                       
                        FarmaUtility.moveFocus(txtLote);
                    }else{
                        chkKeyPressed(e);
                    }                   
                }
      
            }else{
                    chkKeyPressed(e);
                }
        
            FarmaGridUtils.aceptarTeclaPresionada(e, tblRelacionProdLote, null, 1);
        }else{
            
                chkKeyPressed(e);
        
        }
        

    } 
    
    
    
    
    private void txtLote_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblRelacionProdLote, null, 1);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            
            String sLote="";
            sLote=txtLote.getText();
            if(sLote.equalsIgnoreCase("")){
                FarmaUtility.showMessage(this, "Ingrese un lote.", txtLote);
            }else{

                try {
                    String validaExisteLote="";
                    validaExisteLote=DBTomaInv.getExisteLotePorProducto(VariablesInvCiclico.vCodProd,sLote);
                    if(validaExisteLote.equalsIgnoreCase("S")){
                        chkKeyPressed(e); 
                    }else{
                        FarmaUtility.showMessage(this, "El lote:"+sLote+" no esta registrado.", txtLote);
                        
                    }
  
                } catch (SQLException sql) {
                    
                    FarmaUtility.showMessage(this, "Ocurrió un error al validar el lote : \n" +
                            sql.getMessage(), txtLote);                    
                }
               
            }
                        
        } else
            chkKeyPressed(e);
    }
    
    


    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

            cerrarVentana(true);
            
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(UtilityPtoVenta.isLocalVentaMayorista()){ 
            // if(!tblRelacionProdLote.isFocusable()){
            if(VariablesInvCiclico.vActionAdd==0){ 
                
            //Valida si exite Lote
            String sValidaLote="";
            String sLote="";
                sLote=txtLote.getText();

                try {
                    sValidaLote=DBInvCiclico.getValidaLoteXToma(sLote);
                    if(sValidaLote.equalsIgnoreCase("S")){
                    
                        FarmaUtility.showMessage(this, "El lote existe en la lista. \n" , this);
                        
                    }else{
                            
                            String vLote=txtLote.getText();                            
                            int cantidad=0;                            

                        if(txtMasterPack.isEnabled()){
                            String vMasterPack="";
                            int iMasterPack=0;
                            vMasterPack=txtMasterPack.getText();
                            iMasterPack=Integer.parseInt(vMasterPack);
                            int unidadMasterPack=0;
                            int valFacProd=0;
                            valFacProd =Integer.parseInt(VariablesInvCiclico.vFraccion);
                            String sCodProd=VariablesInvCiclico.vCodProd;
                            unidadMasterPack=DBInvCiclico.getProdTieneMasterPack(sCodProd);
                            cantidad=iMasterPack*unidadMasterPack*valFacProd;
                            
                        }else{
                            
                            int entero = Integer.parseInt(txtEntero.getText().trim());
                            int fraccion = Integer.parseInt(txtFraccion.getText().trim());
                            log.debug("entero : " + entero);
                            log.debug("fraccion : " + fraccion);
                            cantidad = entero * Integer.parseInt(VariablesInvCiclico.vFraccion) + fraccion;  
                        }

                        if (tblRelacionProdLote.getRowCount()<1 && (!txtLote.isEnabled())){
                             vLote=DBInvCiclico.getValueProdNoRequiLote();  
                         }


                        
                        int ExceSql=0;
                          ExceSql= DBInvCiclico.grabarLoteXProductoCiclico(vLote,cantidad);
                        if(ExceSql==1){
                            FarmaUtility.aceptarTransaccion();
                            FarmaUtility.showMessage(this, "Se grabaron los datos correctamente." , tblRelacionProdLote);
                            cargaListaProductosXLote();
                            txtEntero.setText("");
                            FarmaUtility.moveFocus(txtEntero);
                           if(txtMasterPack.isEnabled()){
                                 txtMasterPack.setEnabled(false);
                                 txtMasterPack.setText("");
                                 txtMasterPack.setBackground(new Color(237, 237, 237));
                                  
                                 txtEntero.setEnabled(true);                                 
                                 txtEntero.setBackground(new Color(255, 255, 255));   
       
                             }
                            if(txtFraccion.isEnabled()){
                                txtFraccion.setText("");
                            }else{
                                txtFraccion.setText("0");
                            }
                            txtLote.setText("");

                            //FarmaUtility.moveFocusJTable(tblRelacionProdLote);
                        }else{
                            FarmaUtility.liberarTransaccion();
                            FarmaUtility.showMessage(this, "Se produjo un error al insertar los datos" , this);
                        }

                        //Ingreso de datos
                        
                         //FarmaUtility.showMessage(this, "Continuza el registro ....: \n" , this);
                        }
                    
                } catch (SQLException sql) {
                    
                    
                    FarmaUtility.showMessage(this, "Ocurrió un error al registrar la cantidad : \n" +
                            sql.getMessage(), this);
  
                }

                System.out.println("Ingresar la cantidad para mayorista");
            } 
            
            }else{
                if (datosValidados()) {
                    try {
                        ingresarCantidad();
                        FarmaUtility.aceptarTransaccion();
                        cerrarVentana(true);
                    } catch (SQLException sql) {
                        FarmaUtility.liberarTransaccion();
                        log.error("Ocurrió un error al registrar la cantidad : \n", sql);
                        FarmaUtility.showMessage(this, "Ocurrió un error al registrar la cantidad : \n" +
                                sql.getMessage(), txtEntero);
                        cerrarVentana(false);
                    }
                }                                
            }
       
        }else
            if(UtilityPtoVenta.isLocalVentaMayorista()){
        
                            if(UtilityPtoVenta.verificaVK_F1(e)) {
                                if(txtLote.isEnabled()){
                                    DlgIngresarLote dlgIngresarLote =
                                        new DlgIngresarLote (this.myParentFrame, "", true, txtLote.getText(), UtilityPtoVenta.isLocalVentaMayorista()); 
                                    dlgIngresarLote.codprodxx = VariablesInvCiclico.vCodProd;                   
                                    dlgIngresarLote.setVisible(true);
                                }else{
                                    FarmaUtility.showMessage(this, "Valide las condiciones para habilitar e ingresar el lote.", this);    
                                }
                            }else if(UtilityPtoVenta.verificaVK_F2(e)){
                                int iTieneMasterPack=0;
                                String sCodProd=VariablesInvCiclico.vCodProd;
                                
                                if(!txtFraccion.isEditable()){
                                    
                                    try {
                                        iTieneMasterPack= DBInvCiclico.getProdTieneMasterPack(sCodProd);
                                        if(iTieneMasterPack>0){
                                            
                                            if(VariablesInvCiclico.vActionAdd==0){  
                                                txtMasterPack.setEnabled(true);
                                                txtMasterPack.setBackground(new Color(255, 255, 255)); 
                                                txtEntero.setEnabled(false);
                                                txtEntero.setText("");
                                                txtEntero.setBackground(new Color(237, 237, 237)); 
                                                txtLote.setText("");
                                                FarmaUtility.moveFocus(txtMasterPack);                                                
                                            }else if(VariablesInvCiclico.vActionAdd==1){
                                                VariablesInvCiclico.vActionAdd=0;
                                                txtMasterPack.setEnabled(true);
                                                txtMasterPack.setBackground(new Color(255, 255, 255)); 
                                                txtEntero.setEnabled(false);
                                                txtEntero.setText("");
                                                txtEntero.setBackground(new Color(237, 237, 237)); 
                                                FarmaUtility.moveFocus(txtMasterPack);
                                                
                                                txtFecVenci.setText("");
                                                
                                                    txtEntero.setText("");                                                
                                                    String reqLote= VariablesInvCiclico.vReqLote;            
                                                    if(reqLote.equalsIgnoreCase("N")){
                                                        txtLote.setEnabled(false);
                                                        txtLote.setText("");
                                                        txtLote.setBackground(new Color(237, 237, 237));
                                                        
                                                    }else{
                                                        txtLote.setEnabled(true);
                                                        txtLote.setText("");
                                                        txtLote.setBackground(new Color(255, 255, 255));
                                                        
                                                    } 
                                                    
                                                if (VariablesInvCiclico.vFraccion.equalsIgnoreCase("1")) {
                                                    txtFraccion.setEnabled(false);
                                                    txtFraccion.setEditable(false);
                                                    txtFraccion.setText("0");
                                                }
                                                    
                                            }
                                            
                                    
                                        }else{
                                            FarmaUtility.showMessage(this, "El Producto:"+sCodProd+" no tiene MasterPack.", this);    
                                            txtEntero.setEnabled(true);
                                            txtEntero.setBackground(new Color(255, 255, 255)); 
                                            
                                           
                                        }
                                        
                                    } catch (SQLException sql) {
                                        log.error("Ocurrió un error al validar si el producto tiene MasterPack: \n", sql);
                                        FarmaUtility.showMessage(this, "Ocurrió un error al validar si el producto tiene MasterPack: \n" +
                                                sql.getMessage(), this);
                                                       
                                    }   
                                }else{
                                    
                                    FarmaUtility.showMessage(this, "El Producto:"+sCodProd+" no permite ingresar MasterPack.", this); 
                                    
                                }
                        
                            }else if(e.getKeyCode() == KeyEvent.VK_ADD){
                               // FarmaUtility.showMessage(this, "inicia [+]", this); 
                               if (tblRelacionProdLote.getRowCount()<1){
                                                  FarmaUtility.showMessage(this, "No contiene ningun registro a modificar. " , this);
                               }else{
                                       txtMasterPack.setEnabled(false);
                                       txtMasterPack.setText("");
                                       txtMasterPack.setBackground(new Color(237, 237, 237));                
                                       txtLote.setEnabled(false);
                                       txtLote.setText("");
                                       txtLote.setBackground(new Color(237, 237, 237));
                                       txtEntero.setEnabled(true); 
                                       txtEntero.setBackground(new Color(255, 255, 255));
                                       
                                                       
                                       FarmaUtility.moveFocus(txtEntero);
                                       if(tieneRegistroSeleccionado(tblRelacionProdLote)){
                                           
                                           String sEntero="";
                                           
                                           sEntero=tblRelacionProdLote.getValueAt(tblRelacionProdLote.getSelectedRow(), 5).toString().trim();
                                            sEntero=sEntero.replaceAll(",",""); 

                                           txtEntero.setText(sEntero);
                                           
                                           
                                           
                                           txtLote.setText(tblRelacionProdLote.getValueAt(tblRelacionProdLote.getSelectedRow(), 3).toString().trim());
                                           txtFecVenci.setText(tblRelacionProdLote.getValueAt(tblRelacionProdLote.getSelectedRow(), 4).toString().trim());
                                           if(txtFraccion.isEnabled()){
                                               txtFraccion.setText(tblRelacionProdLote.getValueAt(tblRelacionProdLote.getSelectedRow(), 6).toString().trim());        
                                           }
                                          
                                       
                                       }
                                       VariablesInvCiclico.vActionAdd=1;
                                   
                                   }
                                
                                
                                  
                                    
                            
                            }
            } 
    }
    

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************
    
    private boolean tieneRegistroSeleccionado(JTable pTabla) {
        boolean rpta = false;
        if (pTabla.getSelectedRow() != -1) {
            rpta = true;
        }
        return rpta;
    }

    private void mostrarDatos() {
        lblCodigo.setText(VariablesInvCiclico.vCodProd);
        lblDescripcion.setText(VariablesInvCiclico.vDesProd);
        lblUnidadPresentacion.setText(VariablesInvCiclico.vUnidPresentacion);
        lblUnidadVenta.setText(VariablesInvCiclico.vUnidVta);
        lblLaboratorio.setText(VariablesInvCiclico.vNomLab);
        
        if(UtilityPtoVenta.isLocalVentaMayorista()){ 
            txtFraccion.setText("");
        }else{
            String sEntero=VariablesInvCiclico.vCantEntera.trim();
            sEntero=sEntero.replaceAll(",",""); 
            txtEntero.setText(sEntero);
            txtFraccion.setText(VariablesInvCiclico.vCantFraccion.trim());
            
        }

        lblTValorFraccion.setText(VariablesInvCiclico.vFraccion.trim());
    }
    
    private void cargaListaProductosXLote() {
        try {
            DBInvCiclico.getListaProdsXLoteXToma(tableModelProductosTomaCiclicoLote);
            if (tblRelacionProdLote.getRowCount() > 0){
                FarmaUtility.ordenar(tblRelacionProdLote, tableModelProductosTomaCiclicoLote, 1,
                                     FarmaConstants.ORDEN_ASCENDENTE);
            log.debug("se cargo la lista de de prods");            
            }
  
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al obtener la lista de productos por lote: \n" +
                    sql.getMessage(), txtLote);
        }
    }    

    private void ingresarCantidad() throws SQLException {
        int entero = Integer.parseInt(txtEntero.getText().trim());
        int fraccion = Integer.parseInt(txtFraccion.getText().trim());
        log.debug("entero : " + entero);
        log.debug("fraccion : " + fraccion);
        int cantidad = entero * Integer.parseInt(VariablesInvCiclico.vFraccion) + fraccion;
        String cantidadCompleta = "" + cantidad;
        log.debug("cantidad : " + cantidadCompleta);
        DBInvCiclico.ingresaCantidadProdInv(cantidadCompleta);
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private boolean datosValidados() {
        boolean rpta = true;
        if (txtEntero.getText().trim().length() == 0) {
            rpta = false;
            FarmaUtility.showMessage(this, "Ingrese la cantidad entera", txtEntero);
        } else if (txtFraccion.getText().trim().length() == 0 && txtFraccion.isEditable()) {
            rpta = false;
            FarmaUtility.showMessage(this, "Ingrese la cantidad fraccion", txtFraccion);
        } else if (txtFraccion.getText().trim().equalsIgnoreCase(VariablesInvCiclico.vFraccion)) {
            rpta = false;
            FarmaUtility.showMessage(this, "La cantidad de Fraccion no puede ser igual al Valor de Fraccion",
                                     txtEntero);
        } else if (Integer.parseInt(txtFraccion.getText().trim()) > Integer.parseInt(VariablesInvCiclico.vFraccion)) {
            rpta = false;
            FarmaUtility.showMessage(this, "La cantidad de Fraccion ingresada no puede ser mayor al valor de Fraccion",
                                     txtFraccion);
        }
        return rpta;
    }

    private void lblEntero_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtEntero);
    }
    
    private void lblMasterPack_actionPerformed(ActionEvent e) {
        if(txtMasterPack.isEnabled()){
                FarmaUtility.moveFocus(txtMasterPack);    
            }
        
    }    

    private void cargaListaMovimientos() {
        try {
            DBInvCiclico.getListaMovsKardex(tableModelKardex);
            if (tblKardex.getRowCount() > 0) {
                FarmaUtility.ordenar(tblKardex, tableModelKardex, 1, FarmaConstants.ORDEN_DESCENDENTE);
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar la lista de movimientos : \n" +
                    sql.getMessage(), txtEntero);
        }
    }

    private void btnRelacionMovimiento_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocusJTable(tblKardex);
    }
  /*  private void btnRelacionProductoLote_actionPerformed(ActionEvent e) {
        txtMasterPack.setText("");
        txtEntero.setText("");
        txtFraccion.setText("0");
        txtLote.setText("");
        txtFecVenci.setText("");
        FarmaUtility.moveFocusJTable(tblRelacionProdLote);
    }    */     
    

}
