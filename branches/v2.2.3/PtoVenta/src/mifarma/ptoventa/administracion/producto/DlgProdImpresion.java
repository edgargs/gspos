package mifarma.ptoventa.administracion.producto;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.producto.reference.ConstantsPrecios;
import mifarma.ptoventa.administracion.producto.reference.FacadeProducto;
import mifarma.ptoventa.caja.reference.PrintConsejo;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.UtilityVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgProdImpresion extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgProdImpresion.class);
    private Frame myParentFrame;
    FarmaTableModel tableModel;
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JScrollPane scrListaPrecProdCamb = new JScrollPane();
    private JTable tblProductos = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblImprimir = new JLabelFunction();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JButtonLabel jButtonLabel1 = new JButtonLabel();
    private JTextFieldSanSerif txtProducto = new JTextFieldSanSerif();
    private JButton btnBuscar = new JButton();
    private FacadeProducto facadeProducto = new FacadeProducto();

    public DlgProdImpresion() {
        this(null, "", false);
    }

    public DlgProdImpresion(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
    
    private void jbInit() throws Exception {
        this.setSize(new Dimension(552, 375));
        this.getContentPane().setLayout( null );
        this.setTitle("Impresión de Stickers de precios");
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                }
        });
        jPanelTitle1.setBounds(new Rectangle(10, 10, 525, 50));
        jPanelWhite1.setBounds(new Rectangle(0, 0, 550, 350));
        scrListaPrecProdCamb.setBounds(new Rectangle(10, 70, 525, 240));
        lblEsc.setBounds(new Rectangle(420, 320, 117, 19));
        lblEsc.setText("[ ESC ] Salir");
        lblImprimir.setBounds(new Rectangle(290, 320, 117, 19));
        lblImprimir.setText("[ F11 ] Imprimir");
        
        jButtonLabel1.setText("Producto :");
        jButtonLabel1.setBounds(new Rectangle(15, 20, 75, 15));
        jButtonLabel1.setMnemonic('p');
        jButtonLabel1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jButtonLabel1_actionPerformed(e);
                    }
                });
        
        txtProducto.setBounds(new Rectangle(95, 15, 295, 20));
        txtProducto.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                    txtProducto_keyPressed(e);
                }
                });
       
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(415, 10, 80, 25));
        btnBuscar.setMnemonic('b');
        btnBuscar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnBuscar_actionPerformed(e);
                    }
                });
        btnBuscar.addKeyListener(new KeyAdapter(){
                public void keyPressed(KeyEvent e){
                    txtProducto_keyPressed(e);
                }
            });
        btnBuscar.addFocusListener(new FocusListener(){

                @Override
                public void focusGained(FocusEvent e) {
                }

                @Override
                public void focusLost(FocusEvent e) {
                    FarmaUtility.moveFocus(txtProducto);
                }
            });
        scrListaPrecProdCamb.getViewport().add(tblProductos, null);
        jPanelWhite1.add(lblEsc, null);

        jPanelWhite1.add(lblImprimir, null);
        jPanelWhite1.add(scrListaPrecProdCamb, null);
        jPanelWhite1.add(jPanelTitle1, null);
        jPanelTitle1.add(txtProducto, null);
        jPanelTitle1.add(jButtonLabel1, null);
        jPanelTitle1.add(btnBuscar, null);
        this.getContentPane().add(jPanelWhite1, null);
    }
    
    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************
    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(this.txtProducto);
    }
    
    private void jButtonLabel1_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(this.txtProducto);
    }
    
    private void txtProducto_keyPressed(KeyEvent e) {
        //log.debug(e.getKeyCode() +"("+ e.getKeyChar()+")");
        FarmaGridUtils.aceptarTeclaPresionada(e, tblProductos,txtProducto, 1);
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
        
            validarBusqueda();
            
        }else if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
                cerrarVentana();    
        }else if(mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)){
                imprimirSticker();
        }
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        buscarProductos();
    }

    private void cerrarVentana(){
      this.setVisible(false);
      this.dispose();
    }
    
    private void initialize(){
      FarmaVariables.vAceptar = false;
      initTable();  
    }
    private void initTable(){
        tableModel = new FarmaTableModel(ConstantsPrecios.columnsProductosImpresion, ConstantsPrecios.defaultValuesProductosImpresion,0);
        FarmaUtility.initSimpleList(tblProductos, tableModel,ConstantsPrecios.columnsProductosImpresion);
        tblProductos.getTableHeader().setReorderingAllowed(false);
    }
    
    private void buscarProductos(){
        tableModel.clearTable();
        tableModel.data = new ArrayList();
        if(txtProducto.getText().trim().length() >= 3){
            try{
                ArrayList lista = facadeProducto.obtenerProductosPorDescripcion(txtProducto.getText().trim());    
                if(lista.size() > 0){
                    tableModel.data = lista;
                }else{
                        FarmaUtility.showMessage(this, "No se encontraron productos con la descripción ingresada.", null);
                    }
            }catch(Exception e){
                log.error("ocurrió el siguiente error al buscar productos : ",e);
                FarmaUtility.showMessage(this, 
                    "Error al buscar los productos. \n " + e.getMessage(), txtProducto);
                }
            
            
        }
        limpiarCampo();
    }
 
    private void imprimirSticker(){
        ArrayList lista = new ArrayList();
        int selectedRow = tblProductos.getSelectedRow();   
        if(selectedRow >= 0){
            String codigo = tblProductos.getValueAt(selectedRow, 0).toString();
            try {
                String codigoEPL = facadeProducto.obtenerCodigoEPLPorProducto(codigo);
                codigoEPL = codigoEPL.replace("]","").replace("[","").replace("?", "\n");
                
                boolean flag=UtilityCaja.nombreImpSticker();
                if (flag==true){
                    PrintConsejo.imprimirStickerEnLote(codigoEPL, VariablesPtoVenta.vImpresoraSticker);
                    //FarmaUtility.showMessage(this, "Se realizó la impresión del sticker, recoja la impresión.", txtProducto);
                }
            } catch (Exception e) {
                log.error("Ocurrió el siguiente error al imprimir el sticker : " ,e);
                FarmaUtility.showMessage(this, 
                    "Ocurrió un error al obtener los datos del producto \n " + e.getMessage(), txtProducto);
            }
            
        }
    } 
     
     
    private void buscarProductoCodigoBarra(String pCodBarra){
        pCodBarra=txtProducto.getText().trim();
        tableModel.clearTable();
        tableModel.data = new ArrayList();
        if(txtProducto.getText().trim().length() >= 3){
            try{
                ArrayList lista = facadeProducto.obtenerProductosCodigoBarra(pCodBarra);  
                if(lista.size() > 0){
                    tableModel.data = lista;
                    tblProductos.setRowSelectionInterval(0, 0);//selecciona el registro bucado
                    imprimirSticker();
                    tblProductos.clearSelection();
                }else{
                        FarmaUtility.showMessage(this, "No se encontraron productos con El codigo de Barra ingresada.", null);
                    }
            }catch(Exception e){
                log.error("ocurrió el siguiente error al buscar productos : ",e);
                FarmaUtility.showMessage(this, 
                    "Error al buscar los productos. \n " + e.getMessage(), txtProducto);
                }
            
            limpiarCampo();
        } 
    }
    private void limpiarCampo(){
        txtProducto.setText("");
    }
    
    private void validarBusqueda(){
       
        String ingreso=txtProducto.getText().trim();
        String cadena=caracteres(ingreso);//validando que no haya caracteres especiales solo numero o alfabeticos.
        char primerCar=cadena.charAt(0);
        char segundCar=cadena.charAt(1);
      
        if(Character.isLetter(primerCar) && Character.isDigit(segundCar)){            
            cadena=cadena.substring(1);
            txtProducto.setText(cadena);
        }
        
        if(cadena.length()>=3){
            
        if(!UtilityVentas.isNumerico(cadena)){
            buscarProductos();
        }
   
        else  {
           // cadena=cadena.substring(1);
            String cadenaValida="";
            char vCharacter;
            for(int i=0;i<cadena.length();i++){
                vCharacter=cadena.charAt(i);
                if(!Character.isLetter(vCharacter)){
                    cadenaValida=cadenaValida +vCharacter;
                }
                 
            }
            buscarProductoCodigoBarra(cadenaValida); 

        }
        
        
        
            
        }
    }
    
    
     /**
        *
        * @param cadena Cadena de caracteres a validar.
        * @return Retorna la cadena pasada como parametro sin caracteres especiales.
        */
        public static String caracteres(String cadena)
        {
         if(cadena.length()>0)
           {
               if(!cadena.substring(cadena.length()-1).matches("[A-Za-z0-9]"))
               {
                cadena=cadena.substring(0,cadena.length()-1);
                return cadena;
               }
              }
         return cadena;
        }
   
   

}
