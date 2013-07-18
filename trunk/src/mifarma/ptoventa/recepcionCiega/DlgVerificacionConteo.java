package mifarma.ptoventa.recepcionCiega;

import java.awt.BorderLayout;
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

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;

import mifarma.common.*;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import javax.swing.JFrame;

import mifarma.ptoventa.administracion.impresoras.DlgDatosImpresoras;
import mifarma.ptoventa.administracion.impresoras.reference.VariablesImpresoras;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.inventario.DlgRecepcionProductosIngresoCantidad;
import mifarma.ptoventa.recepcionCiega.reference.DBRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.ConstantsRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.UtilityRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.VariablesRecepCiega;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;

public class DlgVerificacionConteo extends JDialog{
    Frame myParentFrame;
    FarmaTableModel tableModel;    
    
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JButtonLabel btnRelacionProductos = new JButtonLabel();
    private JScrollPane scrListaProductos = new JScrollPane();
    private JTable tblListaProductos = new JTable();
    private JLabelFunction lblEnter = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JPanelHeader pnlObservacion = new JPanelHeader();
    private JLabelWhite lblObservacion = new JLabelWhite();
    private JLabelFunction lblF5 = new JLabelFunction();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JButtonLabel btnBuscar = new JButtonLabel();
    private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();


    private int COL_CODIGO = 0;
    private int COL_DESC_PROD = 1;
    private int COL_UNIDAD = 2;
    private int COL_LAB = 3;
    private int COL_CANT = 4;
    private int COL_CODIGO_2 = 5;
    private int COL_SEC_CONTEO = 6;

    // **************************************************************************
    // Constructores
    // ************************************************************************** 
    public DlgVerificacionConteo() {
        this(null, "", false);
       
    }
    
    public DlgVerificacionConteo(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
	myParentFrame = parent;
	try {
                jbInit();
		initialize();
		
     
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************    
    private void jbInit() throws Exception {
        this.setSize(new Dimension(704, 488));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Verificación de Conteo");
        //  02.12.09
        this.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE  );        
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }

                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }
                });
        pnlTitle.setBounds(new Rectangle(5, 60, 685, 25));
        btnRelacionProductos.setText("Relación de Códigos de Productos");
        btnRelacionProductos.setBounds(new Rectangle(10, 5, 335, 15));
        btnRelacionProductos.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnRelacionProductos_actionPerformed(e);
                    }
                });
        btnRelacionProductos.setMnemonic('R');
        scrListaProductos.setBounds(new Rectangle(5, 85, 685, 320));
      /*  scrListaProductos.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        scrListaProductos_keyPressed(e);
                    }
                });*/
        tblListaProductos.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        tblListaProductos_keyPressed(e);
                    }
                } );
        lblEnter.setBounds(new Rectangle(380, 410, 185, 20));
        lblEnter.setText("[ Enter ] Ingreso Cantidad");
        lblF11.setBounds(new Rectangle(495, 435, 195, 20));
        lblF11.setText("[ F11 ] Finalizar Verificación");
        lblEsc.setBounds(new Rectangle(575, 410, 115, 20));
        lblEsc.setText("[ Esc ] Salir ");
        pnlObservacion.setBounds(new Rectangle(5, 5, 685, 55));
        lblObservacion.setText("Sr(a). Jefe de Local, Verifique la cantidad recibida de los siguientes productos");
        lblObservacion.setBounds(new Rectangle(10, 5, 490, 15));
        lblF5.setBounds(new Rectangle(370, 435, 115, 20));
        lblF5.setText("[ F5 ] Imprimir");
        lblF1.setBounds(new Rectangle(140, 330, 180, 20));
        lblF1.setText("[ F1 ] Completar con ceros");
        lblF1.setVisible(false);
        btnBuscar.setText("Buscar: ");
        btnBuscar.setMnemonic('B');
        btnBuscar.setBounds(new Rectangle(10, 30, 50, 15));
        btnBuscar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnBuscar_actionPerformed(e);
                    }
                });
        txtBuscar.setBounds(new Rectangle(60, 25, 245, 20));
        txtBuscar.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtBuscar_keyPressed(e);
                    }

                    public void keyReleased(KeyEvent e) {
                        txtBuscar_keyReleased(e);
                    }
                });
        jContentPane.add(lblF1, null);
        jContentPane.add(lblF5, null);
        pnlObservacion.add(txtBuscar, null);
        pnlObservacion.add(btnBuscar, null);
        pnlObservacion.add(lblObservacion, null);
        jContentPane.add(pnlObservacion, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEnter, null);
        scrListaProductos.getViewport().add(tblListaProductos, null);
        jContentPane.add(scrListaProductos, null);
        pnlTitle.add(btnRelacionProductos, null);
        jContentPane.add(pnlTitle, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);


    }
    
    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************
    private void initialize() {
       FarmaVariables.vAceptar = false;
       FarmaUtility.centrarVentana(this);
       initTable();
       setJTable(tblListaProductos,txtBuscar);
    }
    
    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTable() {
            tableModel = new FarmaTableModel(
                            ConstantsRecepCiega.columnsListaProductosSegundoConteo,
                            ConstantsRecepCiega.defaultcolumnsListaProductosSegundoConteo, 0);
            FarmaUtility.initSimpleList(tblListaProductos, tableModel,
                            ConstantsRecepCiega.columnsListaProductosSegundoConteo);
            cargaListaProductos();
        //   22.05.2010
        //FarmaUtility.moveFocus(tblListaProductos);  
    }
    
    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************
    private void this_windowOpened(WindowEvent e) {
           FarmaUtility.centrarVentana(this);
        //  02.12.09 mover foco a table
        FarmaUtility.moveFocus(txtBuscar);
        boolean vRpta = false;
           /*try{               
               DBRecepCiega.actualizaIndSegundoConteo();
               FarmaUtility.aceptarTransaccion();          
           }
           catch(SQLException sql){
               sql.printStackTrace();
               FarmaUtility.showMessage(this,"Ocurrió un error al actualizar el indicador de segundo conteo en la recepción : \n",null); 
           }
           */
           //  20.03.2010 valida
           if(alertaMaxDiferencias()){
              vRpta =  FarmaUtility.rptaConfirmDialogDefaultNo(this,"La Recepción Actual tiene muchas diferencias.\n" +
                       "Verifique el Detalle de las Entregas por si faltan Asociar o si requiere Desasociar.\n" +
                       "Se Recomienda que verifique antes de ingresar las cantidades.\n"+"\n"+
                       "¿Desea continuar con la verificación de todas maneras?");
             if(!vRpta){
                 if (
                     UtilityRecepCiega.updateEstadoRecep(
                                                         ConstantsRecepCiega.EstadoRevisado,
                                                         VariablesRecepCiega.vNro_Recepcion.trim(),
                                                         this,                                                         
                                                         txtBuscar
                                                         )) {
                     FarmaUtility.aceptarTransaccion(); 
                    cerrarVentana(false);                                                                       
                 }
                 else {
                     FarmaUtility.showMessage(this,"No se pudo modificar el estado en la Recepción.\n" + 
                                                                                                   "Vuelva a Intentarlo.\n",txtBuscar);
                 }
             }
           }  
       
    }
    private void this_windowClosing(WindowEvent e) {
            FarmaUtility.showMessage(this,
                            "Debe presionar la tecla ESC para cerrar la ventana.", txtBuscar);
            System.out.println("cerra ventana");
    }
   
    private void tblListaProductos_keyPressed(KeyEvent e) {        
        chkKeyPressed(e);
    }
    private void btnRelacionProductos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtBuscar);
    }
    
    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************
    private void chkKeyPressed(KeyEvent e) {    
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (tblListaProductos.getRowCount() > 0){                
                //  22.05.2010
                int pFila = tblListaProductos.getSelectedRow();
                //   VariablesRecepCiega.vCod_Barra = this.tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 0).toString().trim();
          //      FarmaUtility.getValueFieldArrayList(tableModelProductos.data,fila,5)     
                   
                   VariablesRecepCiega.vDesc_Producto =FarmaUtility.getValueFieldArrayList(tableModel.data,tblListaProductos.getSelectedRow(),COL_DESC_PROD).trim();// this.tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 0).toString().trim();
                   VariablesRecepCiega.vUnidad = FarmaUtility.getValueFieldArrayList(tableModel.data,tblListaProductos.getSelectedRow(),COL_UNIDAD).trim();//this.tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 1).toString().trim();
                   VariablesRecepCiega.vCodProd =FarmaUtility.getValueFieldArrayList(tableModel.data,tblListaProductos.getSelectedRow(),COL_CODIGO).trim();// this.tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 4).toString().trim();
                   VariablesRecepCiega.vSecConteo = FarmaUtility.getValueFieldArrayList(tableModel.data,tblListaProductos.getSelectedRow(),COL_SEC_CONTEO).trim();//this.tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 5).toString().trim();
                   System.out.println("VariablesRecepCiega.vCod_Barra: " + VariablesRecepCiega.vCod_Barra);
                   System.out.println("VariablesRecepCiega.vDesc_Producto: " + VariablesRecepCiega.vDesc_Producto);
                   System.out.println("VariablesRecepCiega.vUnidad : " + VariablesRecepCiega.vUnidad );
                   DlgIngresoCantVerificaConteo vIngresoCantidad = new DlgIngresoCantVerificaConteo(myParentFrame, "", 
                                                            true);
                   vIngresoCantidad.setVisible(true);

                   if (FarmaVariables.vAceptar)
                   {   
                     //  cargaListaProductos();
                       tableModel.setValueAt(VariablesRecepCiega.vCantidadVerificaConteo, tblListaProductos.getSelectedRow(),COL_CANT);
                   //    tblListaProductos.setValueAt(VariablesRecepCiega.vCantidadVerificaConteo, tblListaProductos.getSelectedRow(), 3); 
                       cargaListaProductos();
                            //  22.05.2010 SELECCIONAR ÚLTIMO MODIFICADO
                            FarmaGridUtils.showCell(tblListaProductos,pFila,COL_DESC_PROD);                            
                       FarmaVariables.vAceptar = false;                       
                   }                   
            }
         

        } else if (e.getKeyCode() == KeyEvent.VK_F11) {
            funcionF11();
                  
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            //COLOCAR IND_SEGUNDO_CONTEO N
            if (
                UtilityRecepCiega.updateEstadoRecep(
                                                    ConstantsRecepCiega.EstadoRevisado,
                                                    VariablesRecepCiega.vNro_Recepcion.trim(),
                                                    this,
                                                    txtBuscar
                                                    )) {
                FarmaUtility.aceptarTransaccion();          
                System.err.println("Dio escape actualiza estado a Revisado");
               cerrarVentana(false);
            }
            else {
                FarmaUtility.showMessage(this,"No se pudo modificar el estado en la Recepción.\n" +                                                                                           
                                                                                          "Vuelva a Intentarlo.\n",txtBuscar);
            }
          // FarmaUtility.showMessage(this,"Para salir de la venta debe finalizar presionando la tecla F11 \n",null);    
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            if (this.tableModel.getRowCount() > 0){
                UtilityCaja.imprimeVoucherDiferencias(this);               
                System.out.println("Imprimio vouvher");    
            }
        }/*else if (e.getKeyCode() == KeyEvent.VK_F1) {
            if (tableModel.getRowCount() > 0){
                System.out.println("Completa con ceros");    
                if (existenProductosACompletarConCeros() ){
                    if (FarmaUtility.rptaConfirmDialog(this,"¿Está seguro de completar los registros no contados con ceros?")) 
                    {
                        rellenarConCeros();
                     
                    }
                }
                
            }
           
        }*///se comento a petición de Pedro Yovera
     }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************
    private boolean existenProductosACompletarConCeros(){
        int numElementos = 1;
        int numBlancos =0;
        boolean encontrado = false;

        while (numElementos <= tableModel.getRowCount() && !encontrado) {
          if (FarmaUtility.getValueFieldArrayList(tableModel.data,numElementos-1,COL_CANT).trim().equalsIgnoreCase("")){
              numBlancos++;    
              encontrado = true;
          }
          numElementos++;
        }
        if (numBlancos > 0 ){
            return  true;
        }
        return false;
    }
    private void rellenarConCeros(){
        try {
            for(int i=0; i<this.tableModel.getRowCount(); i++)  {
                String codProducto =FarmaUtility.getValueFieldArrayList(tableModel.data,i,COL_CODIGO_2).trim();
                String secProducto = FarmaUtility.getValueFieldArrayList(tableModel.data,i,COL_SEC_CONTEO).trim();
                DBRecepCiega.rellenaConCerosCantNoIngresada(codProducto,secProducto);      
            }
          FarmaUtility.aceptarTransaccion();
          cargaListaProductos();  
               FarmaUtility.showMessage(this,"La operación se realizó correctamente",txtBuscar);
        } catch (SQLException sql) {
          FarmaUtility.liberarTransaccion();
          sql.printStackTrace();
          FarmaUtility.showMessage(this,"Ocurrió un error al completar con ceros: \n"+ sql.getMessage(), txtBuscar);
        }
    }
    private void cargaListaProductos(){
        try {
            
                DBRecepCiega.getListaProdVerificacionConteo(tableModel,VariablesRecepCiega.vNro_Recepcion);
                if (tblListaProductos.getRowCount() > 0)
                {
                    FarmaUtility.ordenar(tblListaProductos, tableModel, COL_DESC_PROD,FarmaConstants.ORDEN_ASCENDENTE); 
                    tblListaProductos.repaint();
                }
                FarmaUtility.moveFocus(txtBuscar);
             /*   else
                    FarmaUtility.showMessage(this,"No se ha encontrado diferencias en el conteo \n para finalizar con el conteo presione la tecla F11",null);   */
                    
        } catch (SQLException sql) {
            sql.printStackTrace();
                FarmaUtility.showMessage(this,"Ocurrió un error al cargar la lista de productos : \n",txtBuscar);   
        }
    }
    private void actualizaCantRecepXEntrega(){
        try{
            DBRecepCiega.actualizaCantidadRecepPorEntrega();
            FarmaUtility.aceptarTransaccion();         
        }
        catch(SQLException sql){
            FarmaUtility.liberarTransaccion();
            sql.printStackTrace();
            FarmaUtility.showMessage(this,"Ocurrió un error al actualizar la cantidad recepcionada por entrega: \n",txtBuscar);   
        }
    }
    private boolean verificaExisteGuiasPendientes(){
        
        try{
            return DBRecepCiega.verificaExistenGuiasPendientes();
        }
        catch(SQLException sql){
            sql.printStackTrace();
            FarmaUtility.showMessage(this,"Ocurrió un error al verificar la existencia de guías pendientes : \n",txtBuscar);   
        }
        return true;
    }
    private void mostrarListaGuiasPendientes(){
        DlgListaGuiasPendientes vListaGuiasPendientes = new DlgListaGuiasPendientes(myParentFrame, "", 
                                                     true);
        vListaGuiasPendientes.setVisible(true);
    }
    private  void deshasociarGuiasDeRecepcion(){
        try{
            DBRecepCiega.eliminaGuiasPendienteDeRecepcion(); 
            FarmaUtility.aceptarTransaccion(); 
            System.out.println("entro a desasociar!");
        }
        catch(SQLException sql){
            FarmaUtility.liberarTransaccion();
            sql.printStackTrace();
            FarmaUtility.showMessage(this,"Ocurrió un error al eliminar guías pendientes de la recepción: \n",txtBuscar);   
        }    
    }   
    private void afectarGuiasDeRecepcion(){
        System.out.println("Afectar guias");
        try{
            DBRecepCiega.afectaProductosDeEntregas();
            FarmaUtility.aceptarTransaccion();         
        }
        catch(SQLException sql) {
            FarmaUtility.liberarTransaccion();
            sql.printStackTrace();
            FarmaUtility.showMessage(this,"Ocurrió un error al afectar los productos de las entregas seleccionadas: \n",txtBuscar);   
        }
            
    }
    private void muestraDiferenciasFinales(){
        System.out.println("Muestra la ventana con las muestras finales");
        DlgDiferenciasFinales vDiferenciasFinal = new DlgDiferenciasFinales (this.myParentFrame,"",true);
        vDiferenciasFinal.setVisible(true);
    }
    private void enviaCorreoDeDiferencias(){
        System.out.println("enviando correo con diferencias");
        try{
            DBRecepCiega.enviaCorreoDeDiferencias();            
        }
        catch(SQLException sql) {
            sql.printStackTrace();
            FarmaUtility.showMessage(this,"Ocurrió un error al enviar correo con diferencias encontradas en el conteo. \n",txtBuscar);   
        }
    }
    private void cerrarVentana(boolean pAceptar) {
            FarmaVariables.vAceptar = pAceptar;
            this.setVisible(false);
            this.dispose();
    }

    //  20.03.2010 valida el máximo de Productos con diferencias
    private boolean alertaMaxDiferencias(){
        boolean flag = false;
        try {
            int maxProd = 0;
            maxProd = DBRecepCiega.getMaxProdVerificacion();
            System.out.println("maxProd: "+maxProd);
            System.out.println("tblListaProductos.getRowCount(): "+tblListaProductos.getRowCount());
            if(maxProd < tblListaProductos.getRowCount()){
                flag = true;
            }
        } catch (SQLException e) {
            FarmaUtility.showMessage(this,"Error al obetener indicador para Alerta de máximos Productos.",txtBuscar);            
        }
        return flag;
    }
   
    private void funcionF11(){
        System.out.println("Guarda cambios en segundo conteo");
       /* boolean vRpta = true;
        if (alertaMaxDiferencias()) {
            vRpta = FarmaUtility.rptaConfirmDialogDefaultNo(this,"La Recepción Actual tiene muchas diferencias.\n" +
                    "Verifique el Detalle de las Entregas por si faltan Asociar o si requiere Desasociar.\n" +
                    "¿Desea finalizar la verificación de todas maneras?");
        }        
        if (vRpta) {*/
            if (existenProductosACompletarConCeros() ){
                FarmaUtility.showMessage(this,"Existen productos que no han sido contados,\n para finalizar deberá completar con ceros los \n productos no contados\n",txtBuscar); 
            }else{
                /*   - 10.05.2010
                if (!FarmaUtility.rptaConfirmDialog(this, "¿Está seguro de confirmar la cantidad ingresada para cada producto?,\n si continúa ya no podrá modificar las cantidades. \n Desea continuar con el proceso?")) //{
                    return;    
                    actualizaCantRecepXEntrega();  //de acuerdo a la cantidad contada de cada producto, se actualiza en la tabla LGT_NOTA_DET el campo CANT_RECEPCIONADA segun el algoritmo (tiene mas prioridad el que tiene menor cantidad en el campo CANT_ENVIADIA_MAtRIZ)          
                    if (verificaExisteGuiasPendientes()) { // en cada existan guias que sus productos no han sido contado, o simplemente la cantidad contada no cubre algunas entregas
                    System.out.println("Esisten guias pendientes");
                        mostrarListaGuiasPendientes(); 
                        if (FarmaVariables.vAceptar){                                
                            deshasociarGuiasDeRecepcion();   
                            FarmaVariables.vAceptar = false;
                        }    
                    }            
                    afectarGuiasDeRecepcion(); 
                    //enviaCorreoDeDiferencias(); //  01.02.10 se va comentar para enviar mediante JOB
                    try{
                        DBRecepCiega.actualizaIndSegundoConteo();
                        FarmaUtility.aceptarTransaccion();          
                    }
                    catch(SQLException sql){
                        FarmaUtility.liberarTransaccion();
                        sql.printStackTrace();
                        FarmaUtility.showMessage(this,"Ocurrió un error al actualizar el indicador de segundo conteo en la recepción : \n",null); 
                    }
                    
                    cerrarVentana(true);
                    muestraDiferenciasFinales();  
                */
                if(FarmaUtility.rptaConfirmDialog(this, "¿Está seguro de confirmar la cantidad ingresada " +
                    "para cada producto?\n Si continúa ya no podrá modificar las cantidades." +
                    "\n\n Desea continuar con el proceso?"))
                {
                    accionConfirmaVerificacion();
                }           
            }
       //}        
    }
    
    public void accionConfirmaVerificacion(){
        boolean verificaExisteGuiasPendientes = false;
        //de acuerdo a la cantidad contada de cada producto, se actualiza en la tabla LGT_NOTA_DET el campo CANT_RECEPCIONADA segun el algoritmo (tiene mas prioridad el que tiene menor cantidad en el campo CANT_ENVIADIA_MAtRIZ)          
        try{
            
            DBRecepCiega.actualizaCantidadRecepPorEntrega();
            verificaExisteGuiasPendientes = DBRecepCiega.verificaExistenGuiasPendientes();
            
            VariablesRecepCiega.vAfectaSobranteNuevo = DBRecepCiega.getIndAfectaSobrantesFaltantesNuevo();
            
            if (verificaExisteGuiasPendientes){ // en cada existan guias que sus productos no han sido contado, o simplemente la cantidad contada no cubre algunas entregas
            
            /*   22.07.2011
             * A partir de ahora ya no se liberara las Guías Pendientes estás se afectarán con cero*/                  
                if(VariablesRecepCiega.vAfectaSobranteNuevo.equalsIgnoreCase("S")){
                    afectarEntregasPendientes();  
                }
                else{    
                    //  esta desactivado la opción Nueva afectara como antes
                         System.out.println("Existen guias pendientes");
                             mostrarListaGuiasPendientes(); 
                             if (FarmaVariables.vAceptar){                                
                                 DBRecepCiega.eliminaGuiasPendienteDeRecepcion(); 
                                 System.out.println("entro a desasociar!");
                                 FarmaVariables.vAceptar = false;
                             }    
                }
       
            }            
            
            //  09.08.2011 Solo afectará sobrantes con la versión nueva.
            if(VariablesRecepCiega.vAfectaSobranteNuevo.equalsIgnoreCase("S")){
               afectarEntregasSobrantes();
            }
            
            System.out.println("Afectar guias");
            DBRecepCiega.afectaProductosDeEntregas();
            
            DBRecepCiega.actualizaIndSegundoConteo();
            FarmaUtility.aceptarTransaccion();
            
            cerrarVentana(true);
        }
        catch(SQLException sql){
            FarmaUtility.liberarTransaccion();
            sql.printStackTrace();
            FarmaUtility.showMessage(this,"Ocurrió un error al actualizar la cantidad recepcionada por entrega: \n",txtBuscar);   
        }        
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtBuscar);
    }

    private void txtBuscar_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e,tblListaProductos,txtBuscar,COL_DESC_PROD); 
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            //  23.07.2010 ERROR PRODUCTOS IGUALES VUELVE A BUSCAR
            /*  if (!(FarmaUtility.findTextInJTable(tblListaProductos,txtBuscar.getText().trim(), 0, 0)) )
             {
               FarmaUtility.showMessage(this,"¡Producto No Encontrado según Criterio de Búsqueda!", txtBuscar);
               return;
             }*/
        }
        chkKeyPressed(e);
    }
    
    private void setJTable(JTable pJTable,JTextFieldSanSerif pText) {
      if(pJTable.getRowCount() > 0){
        FarmaGridUtils.showCell(pJTable, 0, COL_CODIGO);
        FarmaUtility.setearActualRegistro(pJTable,pText,COL_DESC_PROD);
      }
      FarmaUtility.moveFocus(pText);
    }

    private void txtBuscar_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e,tblListaProductos,txtBuscar,COL_DESC_PROD);
    }
    
    private void afectarEntregasPendientes() throws SQLException{
        DBRecepCiega.afectarEntregasPendientesBD();
    }
    
    private void afectarEntregasSobrantes() throws SQLException{
        DBRecepCiega.afectarEntregasSobrantesBD();
    }
}
