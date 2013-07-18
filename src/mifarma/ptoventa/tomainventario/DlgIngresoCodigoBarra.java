package mifarma.ptoventa.tomainventario;

    import com.gs.mifarma.componentes.JButtonFunction;
    import com.gs.mifarma.componentes.JButtonLabel;
    import com.gs.mifarma.componentes.JLabelFunction;
    import com.gs.mifarma.componentes.JLabelOrange;
    import com.gs.mifarma.componentes.JLabelWhite;
    import com.gs.mifarma.componentes.JPanelHeader;
    import com.gs.mifarma.componentes.JPanelTitle;
    import com.gs.mifarma.componentes.JPanelWhite;

    import com.gs.mifarma.componentes.JTextFieldSanSerif;

    import java.awt.BorderLayout;
    import java.awt.Dimension;
    import java.awt.Frame;

    import java.awt.Rectangle;

    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;

    import java.awt.event.KeyAdapter;
    import java.awt.event.KeyEvent;
    import java.awt.event.WindowAdapter;
    import java.awt.event.WindowEvent;

    import javax.swing.JDialog;

    import java.sql.SQLException;
    import java.util.ArrayList;

    import java.util.Collections;

    import javax.swing.JFrame;
    import javax.swing.JScrollPane;

    import javax.swing.JTable;

    import mifarma.common.DlgLogin;
    import mifarma.common.FarmaConstants;
    import mifarma.common.FarmaGridUtils;
    import mifarma.common.FarmaSearch;
    import mifarma.common.FarmaTableModel;

    import mifarma.common.FarmaUtility;
    import mifarma.common.FarmaVariables;


import mifarma.ptoventa.recepcionCiega.reference.DBRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.VariablesRecepCiega;
import mifarma.ptoventa.tomainventario.reference.DBTomaInv;
    import mifarma.ptoventa.tomainventario.reference.VariablesTomaInv;
    import mifarma.ptoventa.tomainventario.reference.ConstantsTomaInv;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;

    public class DlgIngresoCodigoBarra extends JDialog {
        Frame myParentFrame;
            
        FarmaTableModel tableModelProductosConteo;
        
        private JTable tblRelacionProductosConteo = new JTable();
        private JTable myJTable;
        
        private static final Logger log = LoggerFactory.getLogger(DlgIngresoCodigoBarra.class);
        
        private JPanelWhite jContentPane = new JPanelWhite();
        private BorderLayout borderLayout1 = new BorderLayout();
        private JButtonFunction jButtonFunction1 = new JButtonFunction();
        //private JPanelTitle jPanelTitleBuscar = new JPanelTitle();
        private JPanelHeader jPanelTitleBuscar = new JPanelHeader();    
        private JButtonLabel btnBuscar = new JButtonLabel();
        private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();
        private JPanelTitle jPanelTitle2 = new JPanelTitle();
        private JScrollPane scrProductos = new JScrollPane();
        private JPanelTitle jPanelTitle1 = new JPanelTitle();
        private JPanelTitle jPanelTitle3 = new JPanelTitle();
        private JLabelFunction lblCambiarCantidad = new JLabelFunction();
        private JLabelFunction lblEliminar = new JLabelFunction();
        private JLabelFunction lblGuardarConteo = new JLabelFunction();
    private JLabelFunction lblSalir = new JLabelFunction();
        private JLabelWhite jLabelWhite1 = new JLabelWhite();
        private JButtonLabel btnProductos = new JButtonLabel();

    //columnas tabla
    private static final int Col_Cod_Prod = 0;
    private static final int Col_Desc_Prod = 1;
    private static final int Col_Desc_Unid_Presente = 2;
    private static final int Col_Cantidad = 3;
    private static final int Col_Val_Frac_Conteo = 4;
    /*
    private static final int Col_Aux_Conteo = 5;
    private static final int Col_Cod_Barra = 6;
    */
    private static final int Col_Aux_Conteo = 7;
    private static final int Col_Cod_Barra = 8;    
    private JLabelFunction jLabelFunction1 = new JLabelFunction();

    private static final int Col_Cod_lab = 5;


    // **************************************************************************
        // Constructores
        // **************************************************************************

        public DlgIngresoCodigoBarra() {
            this(null, "", false);
        }

        public DlgIngresoCodigoBarra(Frame parent, String title, 
                                        boolean modal) {
            super(parent, title, modal);
            try {
                jbInit();
                initialize();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void jbInit() throws Exception {
            this.setSize(new Dimension(778, 414));
            this.getContentPane().setLayout(borderLayout1);
            this.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE  );
            this.setTitle("Lectura de Productos");
            this.addWindowListener(new WindowAdapter() {
                        public void windowOpened(WindowEvent e) {
                            this_windowOpened(e);
                        }
                        public void windowClosing(WindowEvent e)
                        {
                          this_windowClosing(e);
                        }
                    });
            tblRelacionProductosConteo.addKeyListener(new KeyAdapter() {
                        public void keyReleased(KeyEvent e) {
                            tblRelacionProductosConteo_keyReleased(e);
                        }

                        public void keyPressed(KeyEvent e) {
                            tblRelacionProductosConteo_keyPressed(e);
                        }
                    });
            jPanelTitleBuscar.setBounds(new Rectangle(5, 5, 760, 40));
            btnBuscar.setText("Ingresar Código Barra:");
            btnBuscar.setMnemonic('i');
            btnBuscar.setBounds(new Rectangle(15, 10, 135, 20));
            btnBuscar.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            btnBuscar_actionPerformed(e);
                        }
                    });
            
            txtBuscar.setBounds(new Rectangle(165, 10, 235, 20));
            txtBuscar.setLengthText(15);        
            //**--
            txtBuscar.addKeyListener(new KeyAdapter() {
                        public void keyPressed(KeyEvent e) {
                            txtBuscar_keyPressed(e);
                        }

                        public void keyReleased(KeyEvent e) {
                            txtBuscar_keyReleased(e);
                        }
                        public void keyTyped(KeyEvent e) {
                            txtBuscar_keyTyped(e);
                        }
                    });
            //---
            txtBuscar.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            //|txtBuscar_actionPerformed(e);
                        }
                    });
            jPanelTitle2.setBounds(new Rectangle(5, 50, 760, 20));
            scrProductos.setBounds(new Rectangle(5, 70, 760, 260));
            jPanelTitle3.setBounds(new Rectangle(5, 330, 760, 20));
            lblCambiarCantidad.setBounds(new Rectangle(285, 360, 150, 20));
            lblCambiarCantidad.setText("[ + ] Modificar Cantidad");
            lblCambiarCantidad.setVisible(true);
            lblEliminar.setBounds(new Rectangle(280, 385, 95, 20));
            lblEliminar.setText("[ F5 ] Eliminar");
            lblEliminar.setVisible(false);
            lblGuardarConteo.setBounds(new Rectangle(145, 385, 130, 20));
            lblGuardarConteo.setText("[ F1 ] Guardar Conteo");
            lblGuardarConteo.setVisible(false);
        lblSalir.setBounds(new Rectangle(445, 360, 85, 20));
            lblSalir.setText("[ Esc ] Salir");
            lblSalir.setVisible(true);


            btnProductos.setText("Productos");
            btnProductos.setMnemonic('p');
            btnProductos.setBounds(new Rectangle(5, 0, 75, 20));
            btnProductos.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            btnProductos_actionPerformed(e);
                        }
                    });
        jLabelFunction1.setBounds(new Rectangle(135, 360, 140, 20));
        jLabelFunction1.setText("[ F5 ] Eliminar Ingreso");
        jPanelTitleBuscar.add(txtBuscar, null);
            jPanelTitleBuscar.add(btnBuscar, null);
        jContentPane.add(jLabelFunction1, null);
        jContentPane.add(lblSalir, null);
        jContentPane.add(lblGuardarConteo, null);
            jContentPane.add(lblEliminar, null);
        jContentPane.add(lblCambiarCantidad, null);
        jContentPane.add(jPanelTitle3, null);
            scrProductos.getViewport().add(tblRelacionProductosConteo, null);
            jContentPane.add(scrProductos, null);

            jPanelTitle2.add(btnProductos, null);
            jContentPane.add(jPanelTitle2, null);
            jContentPane.add(jPanelTitleBuscar, null);
            this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        }

        // **************************************************************************
        // Métodos de inicialización
        // **************************************************************************
        private void initialize() {
            FarmaVariables.vAceptar = false;
            initTableListaProdConteo();
            log.error("principio no");
        }

        private void initTableListaProdConteo() 
        {
            tableModelProductosConteo = 
                    new FarmaTableModel(ConstantsTomaInv.columnsListaProductosConteo, 
                                        ConstantsTomaInv.defaultValuesListaProductosConteo, 
                                        0);
            FarmaUtility.initSimpleList(tblRelacionProductosConteo, 
                                        tableModelProductosConteo, 
                                        ConstantsTomaInv.columnsListaProductosConteo);
            actualizaListaConteoToma();
            setJTable(tblRelacionProductosConteo,txtBuscar);
            
        }
        
        private void btnBuscar_actionPerformed(ActionEvent e) {
            FarmaUtility.moveFocus(txtBuscar);
        }

        private void this_windowClosing(WindowEvent e)
        {
            FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
        }
        
        private void this_windowOpened(WindowEvent e) {
            FarmaUtility.centrarVentana(this);
            FarmaUtility.moveFocus(txtBuscar);
        }
        
        private void cerrarVentana(boolean pAceptar)
        {
          FarmaVariables.vAceptar = pAceptar;
          this.setVisible(false);
          this.dispose();
        }
        
       
        private void chkKeyPressed(KeyEvent e) {
            
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                                          
                      cerrarVentana(false);
                   
            }      
        }
        
        private void txtBuscar_keyReleased(KeyEvent e) {
            chkKeyReleased(e);
        }
        
        private void txtBuscar_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e,tblRelacionProductosConteo,txtBuscar,1);
            
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {    
               e.consume();
               accionEnter();
        } 
        else
            if (e.getKeyCode() == KeyEvent.VK_F5) {    
                   e.consume();
                   eliminaFilaSeleccionada();
                
            } 
            chkKeyPressed(e);
        }    

        
        private void chkKeyReleased(KeyEvent e) {

            FarmaUtility.admitirDigitos(txtBuscar,e);
        }
        
      

        private void btnProductos_actionPerformed(ActionEvent e) {
            FarmaUtility.moveFocus(tblRelacionProductosConteo);
      
        }
           
        private void ingresaProducto() {
            //  22.12.09 INGRESA PRODUCTO 
            try{
                    DBTomaInv.insertAuxConteo(VariablesTomaInv.vSecToma,
                                                          VariablesTomaInv.vSecAuxConteo,
                                                          VariablesTomaInv.vCodProducto,
                                                          VariablesTomaInv.vCodBarraIngresado,                                      
                                                          VariablesTomaInv.vCantEnteraIngresada,
                                                          VariablesTomaInv.vCantFracIngresada,
                                                          VariablesTomaInv.vIndProcesoToma );
                log.error("Insertar Auxiliar Conteo: "+VariablesTomaInv.vSecToma+
                          " - "+VariablesTomaInv.vCodProducto);
                }catch(SQLException sql){
                    sql.printStackTrace();
                    FarmaUtility.showMessage(this,"Error al Ingresar Producto. \n"+
                                                  sql.getMessage(),txtBuscar);
                }
            
        }
                

    private void txtBuscar_keyTyped(KeyEvent e) {        
        FarmaUtility.admitirDigitos(txtBuscar,e);
        if(e.getKeyChar() == '+')
        {
            modificarCantidadIngreso();
        }        
        
    }
    /**
     * 
     */
    private void modificarCantidadIngreso(){

        try {
            System.out.println("Modificará el Producto");
            //muestraInfoProd();
            if (tblRelacionProductosConteo.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, 
                                         "No hay productos para modificar.\n¡Verifique!", 
                                         txtBuscar);
                return;
            }
            getDatosModificar();
            muestraInfoProd(FarmaConstants.INDICADOR_N);
            if (FarmaVariables.vAceptar) {
                if (!VariablesTomaInv.vCantEnteraIngresada.equalsIgnoreCase("") || 
                    !VariablesTomaInv.vCantFracIngresada.equalsIgnoreCase("")) {
                    if (VariablesTomaInv.vIndEliminaProd.equalsIgnoreCase("S")) {
                        eliminaProdConteo(VariablesTomaInv.vSecToma, 
                                          VariablesTomaInv.vSecAuxConteoTemp.trim());
                        VariablesTomaInv.vIndEliminaProd = "N";
                        log.error("delete");
                    } else {
                        updateDatosConteo();
                        log.error("Update");
                        FarmaUtility.aceptarTransaccion();
                    }
                    actualizaListaConteoToma();
                    limpiarVariables();
                }
            }
            VariablesTomaInv.vIndActualizaProd = "N";
            setJTable(tblRelacionProductosConteo,txtBuscar);
        }
        catch (SQLException sql) {
            
                sql.printStackTrace();            
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this,"Error al Actualizar el Producto.\n" +
                    sql.getMessage(),txtBuscar);
        }        
    }
    
    /*    
    private void validaCodBarra(String vCodBarra){
        VariablesRecepCiega.vLastCodBarra = vCodBarra;
        try {
            vCodBarra = DBRecepCiega.obtieneCodigoProductoBarra(vCodBarra.trim());                
            System.err.println("consulta cod barra despues: "+vCodBarra);
        } catch (SQLException sql) {
            sql.printStackTrace();
            log.error(null,sql);
        }
        if (vCodBarra.trim().equalsIgnoreCase("000000")) {
            VariablesRecepCiega.vIndNoFound = "S";
            System.out.println("indNoFound1: "+VariablesRecepCiega.vIndNoFound);
          return;
        }
       
    }
    */    
        

        
    

        private void tblRelacionProductosConteo_keyReleased(KeyEvent e) {
            //
        }
       
        private void tblRelacionProductosConteo_keyPressed(KeyEvent e)
        {   
          
            chkKeyPressed(e);
        }
        
        
   private void  accionEnter(){
        String codBarra = txtBuscar.getText().toString().trim();

        if (codBarra.trim().length() == 0) {
            FarmaUtility.showMessage(this, 
                                     "Ingrese Código de Barra. \n", 
                                     txtBuscar);
            return;
        } else if (codBarra.trim().length() > 14 || 
                   codBarra.trim().length() < 6) {
            FarmaUtility.showMessage(this, 
                                     "Ingrese Código de Barra y/o Producto Correcto. \n", 
                                     txtBuscar);
            FarmaUtility.moveFocus(txtBuscar);
        } else {

            if (codBarra.trim().length() > 6) { //codigo de barra
                VariablesTomaInv.vCodProducto = obtieneCodigoProducto(codBarra.trim());
                VariablesTomaInv.vCodBarraIngresado = codBarra.trim();
                if (!VariablesTomaInv.vCodProducto.equalsIgnoreCase("000000")) {
                    //VariablesTomaInv.vCodBarraIngresado = obtieneCodigoProducto(codBarra.trim());
                    log.info("VariablesTomaInv.vCodBarraIngresado:"+VariablesTomaInv.vCodBarraIngresado);
                    log.info("VariablesTomaInv.vCodProducto:"+VariablesTomaInv.vCodProducto);
                    muestraInfoProd(FarmaConstants.INDICADOR_S);
                    if (FarmaVariables.vAceptar) {
                        VariablesTomaInv.vIndIngresaProd = "S";
                        //  21.12.09
                        //INGRESO EN TABLA AUXILIAR AUX_LGT_PROD_TOMA_CONTEOS
                        log.info("vIndIngresaProd: " + 
                                 VariablesTomaInv.vIndIngresaProd);
                        if (VariablesTomaInv.vIndIngresaProd.equalsIgnoreCase("S")) {
                            log.info("INGRESO PROD: " + 
                                     VariablesTomaInv.vCodProducto);
                            //getAuxConteo();
                            VariablesTomaInv.vContadorFila++;
                            VariablesTomaInv.vIndProcesoToma = "N";
                            ingresaProducto();
                            FarmaUtility.aceptarTransaccion();

                            txtBuscar.setText("");
                            FarmaUtility.moveFocus(txtBuscar);
                            actualizaListaConteoToma();
                            limpiarVariables();
                        }
                    }
                    
                } else {
                    
                    /*
                    FarmaUtility.showMessage(this, 
                                             "No existe el código de Barra. \nPor favor Ingrese una descripción del Producto.", 
                                             txtBuscar);
                    */
                    
                    double pNumeroValido = 0;
                    boolean pIsValido = false;
                    try {
                        pNumeroValido = 
                                Double.parseDouble(VariablesTomaInv.vCodBarraIngresado.trim());
                        System.err.println("Numero Valido");
                        pIsValido = true;
                    } catch (Exception e) {
                        System.err.println("Numero NO ES VALIDO");
                    }
                    
                    if (pIsValido) {
                        /*String pDatoProducto = 
                            FarmaUtility.ShowInput(this, "No existe el código de Barra. \nPor favor Ingrese una descripción del Producto.");
                        */
                        String pDatoProducto = ingresoGlosa();
                        //  30.12.09 ENVIA ALERTA
                        enviaCorreoCodBarraNoExiste(VariablesTomaInv.vSecToma, 
                                                    VariablesTomaInv.vCodBarraIngresado, 
                                                    pDatoProducto);
                        FarmaUtility.moveFocus(txtBuscar);
                    }
                }
            }
        }
        
        setJTable(tblRelacionProductosConteo,txtBuscar);
    }
   
   public String ingresoGlosa(){
       String pValor =  FarmaUtility.ShowInput(this, "No existe el código de Barra. \nPor favor Ingrese una descripción del Producto.");
       if(pValor.trim().length()==0){
           FarmaUtility.showMessage(this, 
                                    "Por favor ingrese una descripción del producto.\nEsto es Oblogitario.\nGracias", 
                                    txtBuscar);   
           return ingresoGlosa();
       }
       else
           return pValor;
   }
   
    private void setJTable(JTable pJTable,JTextFieldSanSerif pText) {
      pText.setText("");
      if(pJTable.getRowCount() > 0){
        FarmaGridUtils.showCell(pJTable, 0, 0);
        FarmaUtility.setearActualRegistro(pJTable,pText,1);
      }
      FarmaUtility.moveFocus(pText);
    }    
    
    private String obtieneCodigoProducto(String pCodBarra) {
        String codProducto = "";
        try {
            codProducto = 
                    DBTomaInv.obtieneCodigoProductoBarra(pCodBarra.trim());
        } catch (SQLException sql) {
            sql.printStackTrace();
            FarmaUtility.showMessage(this, 
                                     "Ocurrió un error al obtener código del producto para su código de barra.\n" +
                    sql.getMessage(), null);
        }
        return codProducto;
    }
    
    private void muestraInfoProd(String pIndNuevoIngreso)
    {  
        
        
        if(pIndNuevoIngreso.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
        {
            
            //Se validará si es un Entero para ingresar automaticamente el valor.
            //
           
           ArrayList pDatos =  new ArrayList();
           boolean  pIngresoProductoEntero  =  false;
           try {
               DBTomaInv.getInfoProd(pDatos, VariablesTomaInv.vCodProducto);
           }
           catch (SQLException e) {
               pDatos = new ArrayList();
           }
           
           if(pDatos.size()==1){
                    VariablesTomaInv.vDescripcion = FarmaUtility.getValueFieldArrayList(pDatos,0,1);
                    VariablesTomaInv.vUnidadPresentacion = FarmaUtility.getValueFieldArrayList(pDatos,0,2);
                    VariablesTomaInv.vFraccionLocal = FarmaUtility.getValueFieldArrayList(pDatos,0,3);
                    VariablesTomaInv.vUnidadVenta = FarmaUtility.getValueFieldArrayList(pDatos,0,4);
                    VariablesTomaInv.vLaboratorio_Barra = FarmaUtility.getValueFieldArrayList(pDatos,0,5);
                    
                    System.out.println("VariablesTomaInv.vCodBarraIngresado :"+VariablesTomaInv.vCodBarraIngresado );
                    System.out.println("VariablesTomaInv.vCodProducto :"+VariablesTomaInv.vCodProducto );
                    System.out.println("VariablesTomaInv.vDescripcion :"+VariablesTomaInv.vDescripcion );
                    System.out.println("VariablesTomaInv.vUnidadPresentacion :"+VariablesTomaInv.vUnidadPresentacion );
                    System.out.println("VariablesTomaInv.vFraccionLocal :"+VariablesTomaInv.vFraccionLocal );
                    System.out.println("VariablesTomaInv.vUnidadVenta :"+VariablesTomaInv.vUnidadVenta );
                    System.out.println("VariablesTomaInv.vLaboratorio_Barra :"+VariablesTomaInv.vLaboratorio_Barra );
                    
                    
                    if(Integer.parseInt(VariablesTomaInv.vFraccionLocal.trim())==1){
                        System.err.println("Ingreso automatico de producto Entero");
                        pIngresoProductoEntero  =  true;
                        VariablesTomaInv.vCantEnteraIngresada = "1";
                        VariablesTomaInv.vCantFracIngresada = "0";
                        VariablesTomaInv.vIndActualizaProd = "N";
                        VariablesTomaInv.vIndIngresaProd = "S";
                        FarmaVariables.vAceptar = true;
                    }
                    
            }
           
            //////
           if(!pIngresoProductoEntero){
                DlgIngresoCantFracCodBarra dlgIngresoCant = new DlgIngresoCantFracCodBarra(myParentFrame,"",true);
                dlgIngresoCant.setVisible(true);
            }
                
        }
        else if(pIndNuevoIngreso.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
            // Sirve para actualizar
            VariablesTomaInv.vIndActualizaProd = "S";
            DlgIngresoCantFracCodBarra dlgIngresoCant = new DlgIngresoCantFracCodBarra(myParentFrame,"",true);
            dlgIngresoCant.setVisible(true);
        }
        else{
            if(tblRelacionProductosConteo.getRowCount() <= 0) return;
            DlgIngresoCantFracCodBarra dlgIngresoCant = new DlgIngresoCantFracCodBarra(myParentFrame,"",true);
            dlgIngresoCant.setVisible(true);
        }
    }

    private void getAuxConteo(){
        try{
                String SecAuxConteo = DBTomaInv.getSecAuxConteo(VariablesTomaInv.vSecToma);
                
                VariablesTomaInv.vSecAuxConteo = Integer.parseInt(SecAuxConteo.trim());
            }catch(SQLException sql){
                sql.printStackTrace();  
                FarmaUtility.showMessage(this,"Error al Obtener Secuencial para el Conteo de Productos.\n" +
                    sql.getMessage(),txtBuscar);
            }
        
    }
    private void actualizaListaConteoToma(){
        try{
        DBTomaInv.obtieneListaConteoToma(tableModelProductosConteo,
                                              VariablesTomaInv.vSecToma);
                          
            }catch(SQLException sql){
                sql.printStackTrace();
                FarmaUtility.showMessage(this,"Problemas al Mostrar Lista Productos en conteo."+sql.getMessage(),txtBuscar);
            }
    }
    
    //Elimina conteo Actual al dar Escape
    private void eliminarConteoActual(){
        if(tblRelacionProductosConteo.getRowCount() <= 0) return;
        
        int nroFilas = tblRelacionProductosConteo.getRowCount();
        ArrayList arrayProducto = new ArrayList();
            
        int vFila = tblRelacionProductosConteo.getSelectedRow();
                
    }
    
    private void getDatosModificar(){
        if(tblRelacionProductosConteo.getRowCount() <= 0) return;
        
        int vFila = tblRelacionProductosConteo.getSelectedRow();
        
        VariablesTomaInv.vCodProductoTemp = FarmaUtility.getValueFieldArrayList(tableModelProductosConteo.data,vFila,Col_Cod_Prod);
        VariablesTomaInv.vDescripcionTemp = FarmaUtility.getValueFieldArrayList(tableModelProductosConteo.data,vFila,Col_Desc_Prod);
        VariablesTomaInv.vUnidadPresentacionTemp = FarmaUtility.getValueFieldArrayList(tableModelProductosConteo.data,vFila,Col_Desc_Unid_Presente);
        VariablesTomaInv.vCantEnteraTemp = FarmaUtility.getValueFieldArrayList(tableModelProductosConteo.data,vFila,Col_Cantidad);
        VariablesTomaInv.vCantFracTemp = FarmaUtility.getValueFieldArrayList(tableModelProductosConteo.data,vFila,Col_Val_Frac_Conteo);
        //VariablesTomaInv.vSecAuxConteoTemp = FarmaUtility.getValueFieldJTable(tableModelProductosConteo.data,vFila,Col_Aux_Conteo);
        VariablesTomaInv.vSecAuxConteoTemp             
            = FarmaUtility.getValueFieldArrayList(tableModelProductosConteo.data,vFila,Col_Aux_Conteo);
        VariablesTomaInv.vCodBarraTemp = FarmaUtility.getValueFieldArrayList(tableModelProductosConteo.data,vFila,Col_Cod_Barra);
        VariablesTomaInv.vNomLab = FarmaUtility.getValueFieldArrayList(tableModelProductosConteo.data,vFila,Col_Cod_lab);
        log.error("vSecAuxConteoTemp: "+VariablesTomaInv.vSecAuxConteoTemp);
        log.error("vCodProductoTemp: "+VariablesTomaInv.vCodProductoTemp);
        
        // 28.12.09
        // vCantEnteraTemp - vCantEnteraIngresada   Valor Anterior, nuevo valor                
                
    }
    
    private void updateDatosConteo() throws SQLException{
        //try{            
        DBTomaInv.updateProdConteo(VariablesTomaInv.vSecToma,
                                   VariablesTomaInv.vSecAuxConteoTemp.trim(),
                                   VariablesTomaInv.vCantEnteraIngresada,
                                   VariablesTomaInv.vCantFracIngresada);            
        /*}
        catch (SQLException sql){
            sql.printStackTrace();            
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this,"Error al Actualizar el Producto.\n" +
                sql.getMessage(),txtBuscar);
        } 
        */
    }
    
    private void eliminaProdConteo(String pSecToma,String pSecAuxConteo){
        try{
            DBTomaInv.delProdConteo(pSecToma,
                                    pSecAuxConteo.trim());
            
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this,"Se eliminó el producto con éxito",txtBuscar);
        }
        catch (SQLException sql){
            sql.printStackTrace();
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this,"Error al Eliminar el Producto.\n" +
                sql.getMessage(),txtBuscar);
        }
        
    }   
    private void limpiarVariables(){
        VariablesTomaInv.vCodBarraTemp = "";
        VariablesTomaInv.vCodProductoTemp = "";
        VariablesTomaInv.vDescripcionTemp = "";
        VariablesTomaInv.vUnidadPresentacionTemp = "";
        VariablesTomaInv.vFraccionLocalTemp = "";
        VariablesTomaInv.vCantEnteraTemp = "";
        VariablesTomaInv.vCantFracTemp = "";
        VariablesTomaInv.vSecAuxConteoTemp = "";
     //  ----------
        VariablesTomaInv.vCodBarraIngresado = "";
        VariablesTomaInv.vCodProducto = "";
        VariablesTomaInv.vDescripcion = "";
        VariablesTomaInv.vUnidadPresentacion = "";
        VariablesTomaInv.vFraccionLocal = "";
        VariablesTomaInv.vUnidadVenta = "";
        VariablesTomaInv.vCantEnteraIngresada = "";
        VariablesTomaInv.vCantFracIngresada = "";
    }
 private void enviaCorreoCodBarraNoExiste(String pSecTomaInv, 
                                             String pCodBarra,String pGlosaProducto){
        try{
            DBTomaInv.enviaCorreoCodBarraNoExiste(pSecTomaInv,pCodBarra,pGlosaProducto.trim());
            FarmaUtility.aceptarTransaccion();
        }catch(SQLException sql){
           sql.printStackTrace();
              FarmaUtility.liberarTransaccion();
           FarmaUtility.showMessage(this,"OcurriÓ un error al enviar correo para Código de Barra no Existe.\n"+sql.getMessage(),tblRelacionProductosConteo);
          }        
        
    }    
    /**
     * Elimina Fila Seleccionada 
     * @author  
     * @since  30.12.2009
     */
    private void eliminaFilaSeleccionada()
    {
         boolean flagDel = FarmaUtility.rptaConfirmDialogDefaultNo(this,"Está seguro de eliminar el Producto.\n¡Verifique!");
             if(flagDel) {
                try {
                    if (tblRelacionProductosConteo.getRowCount() <= 0)
                        return;
        
                    int vFila = tblRelacionProductosConteo.getSelectedRow();
        
                    String pSecAuxToma = 
                        FarmaUtility.getValueFieldArrayList(tableModelProductosConteo.data, 
                                                            vFila, Col_Aux_Conteo);
                    log.error("pSecAuxToma: " + pSecAuxToma);
                    log.error("pSecTomaInv: " + VariablesTomaInv.vSecToma);
                    eliminaProdConteo(VariablesTomaInv.vSecToma, pSecAuxToma);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                 actualizaListaConteoToma();
                 limpiarVariables();
             }
     }
}
