package mifarma.ptoventa.inventario;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;

import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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

import javax.swing.JDialog;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.dto.NotaEsCabDTO;
import mifarma.ptoventa.inventario.dto.NotaEsCabDetDTO;
import mifarma.ptoventa.inventario.dto.OrdenCompraCabDTO;
import mifarma.ptoventa.inventario.dto.OrdenCompraDetDTO;
import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.FacadeInventario;
import mifarma.ptoventa.inventario.reference.UtilityInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.recetario.DlgDatosPacienteMedico;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.ventas.reference.DBVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgDevolucionDetalleOC.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      26.06.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgDevolucionDetalleOC extends JDialog implements KeyListener{
    
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    
    private static final Logger log = LoggerFactory.getLogger(DlgDevolucionDetalleOC.class);
    
    private Frame myParentFrame;
    private FarmaTableModel tableModel;
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelHeader pnlHeader = new JPanelHeader();
    private JButtonLabel btnBuscarOC = new JButtonLabel();
    private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel btnRelacionProductos = new JButtonLabel();
    private JScrollPane scrListaProductos = new JScrollPane();
    private JTable tblListaProductos = new JTable();
    private JLabelFunction lblEnter = new JLabelFunction();
    private JLabelFunction lblBuscar = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private ArrayList listaDetOrdenesCompra = new ArrayList();
    
    private FacadeInventario facadeInventario = null;
    //private static ArrayList arrayProductos = new ArrayList();
    private NotaEsCabDTO notaEsCabDTO = null;
    
    String stkProd = "";
    String indProdCong = "";
    //private Object notaEsCabDetDTO;
    
    //GFonseca 16.12.2013 mostrar mensaje de que no se puede buscar.
    private int contBus=1;

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */
    
    public DlgDevolucionDetalleOC() {
        this(null, "", false);
    }

    public DlgDevolucionDetalleOC(Frame parent, String title, boolean modal) {
        
        super(parent, title, modal);
        myParentFrame = parent;
        
        try {
            this.addKeyListener(this);
            setFocusable(true);
            jbInit();
            initialize();
            FarmaUtility.centrarVentana(this);
            
        } catch (Exception e) {
            log.error("",e);
        }
    }

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */  
    
    private void jbInit() throws Exception {
        
        this.setSize(new Dimension(766, 438));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Lista de Productos por Orden de Compra");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter()
          {
            public void windowOpened(WindowEvent e)
            {
              this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e)
            {
              this_windowClosing(e);
            }
          });
        
        btnBuscarOC.setText("Ordenes de Compra:");
        btnBuscarOC.setBounds(new Rectangle(15, 15, 160, 15));
        btnBuscarOC.setMnemonic('B');
        btnBuscarOC.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              btnBuscarOC_actionPerformed(e);
            }
          });        
        
        pnlHeader.setBounds(new Rectangle(10, 15, 745, 45));
        txtBuscar.setBounds(new Rectangle(160, 13, 330, 20));
        txtBuscar.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                    txtBuscar_keyPressed(e);
                }

            public void keyReleased(KeyEvent e){
              txtBuscar_keyReleased(e);
            }
          });

 
        pnlTitle1.setBounds(new Rectangle(10, 70, 745, 25));
        
        btnRelacionProductos.setText("Relacion de Productos");
        btnRelacionProductos.setBounds(new Rectangle(5, 5, 135, 15));
        btnRelacionProductos.setMnemonic('R');
        btnRelacionProductos.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                    btnRelacionProductos_actionPerformed(e);
                }
          });

        btnRelacionProductos.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    btnRelacionProductos_keyPressed(e);
                }
            });
        scrListaProductos.setBounds(new Rectangle(10, 95, 745, 270));
        scrListaProductos.setBackground(new Color(255, 130, 14));        
        scrListaProductos.getViewport().add(tblListaProductos, null);
        tblListaProductos.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
              tblListaProductos_keyPressed(e);
            }
          });
        
        
        lblEnter.setText("[ ENTER ] Seleccionar");
        lblEnter.setBounds(new Rectangle(15, 375, 145, 20));

        lblBuscar.setText("[ F3 ] Buscar OC");
        lblBuscar.setBounds(new Rectangle(170, 375, 145, 20));
        
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(665, 375, 90, 20));

        
        pnlHeader.add(btnBuscarOC, null);
        pnlHeader.add(txtBuscar, null);
        pnlTitle1.add(btnRelacionProductos, null);
        jContentPane.add(scrListaProductos, null);
        jContentPane.add(pnlHeader, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(lblEnter, null);
        jContentPane.add(lblBuscar, null);
        jContentPane.add(lblEsc, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */    
    
    private void initialize(){
      initTable();
      FarmaVariables.vAceptar = false;
      //setJTable(tblListaProductos,txtBuscar);
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */
    
    private void initTable(){
      tblListaProductos.getTableHeader().setReorderingAllowed(false);
      tblListaProductos.getTableHeader().setResizingAllowed(false);
      tableModel = new FarmaTableModel(ConstantsInventario.columnsListaOrdenesCompraDet,
                                       ConstantsInventario.defaultValuesListaOrdenesCompraDet,
                                       0);
      FarmaUtility.initSimpleList(tblListaProductos,
                                  tableModel,
                                  ConstantsInventario.columnsListaOrdenesCompraDet);  
      if(tblListaProductos.getRowCount()>0){
        FarmaGridUtils.showCell(tblListaProductos,0,0);  
      }   
    } 
    
    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */
    
    private void this_windowOpened(WindowEvent e){
      FarmaUtility.centrarVentana(this);
      buscarOrdenCompra();
    }
    
    private void this_windowClosing(WindowEvent e){
      FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }   
    
    private void btnBuscarOC_actionPerformed(ActionEvent e){
      FarmaUtility.moveFocus(tblListaProductos);
    }


    private void txtBuscar_keyReleased(KeyEvent e){
      FarmaGridUtils.buscarDescripcion(e,tblListaProductos,txtBuscar,2);
      if(tblListaProductos.getSelectedRow()>-1)
        muestraInfoProd();
      
    }
    
    private void btnRelacionProductos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaProductos);
    }

    private void btnRelacionProductos_keyPressed(KeyEvent e) {
        FarmaUtility.moveFocus(tblListaProductos);
    }

    private void txtBuscar_keyPressed(KeyEvent e){          
        chkKeyPressed(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        FarmaUtility.moveFocus(tblListaProductos);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        FarmaUtility.moveFocus(tblListaProductos);
    }
    
    private void tblListaProductos_keyPressed(KeyEvent e){         
        chkKeyPressed(e);
    }   
    
    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */
    
    private void chkKeyPressed(KeyEvent e){
    
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
                e.consume();
            
            if (tblListaProductos.getSelectedRow() != -1){   
                seleccionarProducto();                
            }else{
            FarmaUtility.showMessage(this, "Seleccione un registro!!!", txtBuscar);
            }   
                        
        }else if(e.getKeyCode() == KeyEvent.VK_F3){
            buscarOrdenCompra();
        }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
        
            if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "¿Está seguro de cerrar la ventana.\n" +
                                               "Asegúrese de presionar F11 para grabar todos los datos antes de salir?")){
                 if(!UtilityInventario.indNuevaTransf()){
                   //cancelaOperacion();
                 }        
                //GFonseca 16.12.2013 inicializa el array de datos antes de cerrar la ventana.
                VariablesInventario.vArrayProductos = new ArrayList(); 
                cerrarVentana(false);                
            }
    
       }
    }    
    
    private void cerrarVentana(boolean pAceptar){
                  FarmaVariables.vAceptar = pAceptar;
                  VariablesInventario.vKeyPress = null;
                  this.setVisible(false);
      this.dispose();
    }
    
    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */
    
    public void cargaDetalleProducto(NotaEsCabDetDTO notaEsCabDetDTO){
         // Cargar dato        
          int filaSelec = tblListaProductos.getSelectedRow();
          notaEsCabDetDTO.setCodProd(tblListaProductos.getValueAt(filaSelec,0).toString());
          // Obtener Datos
          ArrayList obtenerProducto = facadeInventario.listarProductosPorOrdenCompra(notaEsCabDTO.getCodOrdenCompra(),notaEsCabDetDTO);
          notaEsCabDetDTO.setDescProdFrm(((ArrayList)obtenerProducto.get(0)).get(1).toString().trim());
          notaEsCabDetDTO.setCantUnidadForm(Integer.parseInt(((ArrayList)obtenerProducto.get(0)).get(2).toString()));
          notaEsCabDetDTO.setStockProFrm(((ArrayList)obtenerProducto.get(0)).get(3).toString().trim());
          notaEsCabDetDTO.setDescUnidaVta(((ArrayList)obtenerProducto.get(0)).get(4).toString());
          notaEsCabDetDTO.setIndProdAfect(((ArrayList)obtenerProducto.get(0)).get(5).toString());
          notaEsCabDetDTO.setDescLab(((ArrayList)obtenerProducto.get(0)).get(6).toString());
          notaEsCabDetDTO.setIndProdCongFrm(((ArrayList)obtenerProducto.get(0)).get(7).toString());
          notaEsCabDetDTO.setFechaHoraTransfFrm(((ArrayList)obtenerProducto.get(0)).get(8).toString());
          
          double aux=0.00;
          aux+=FarmaUtility.getDecimalNumber((((ArrayList)obtenerProducto.get(0)).get(9).toString().trim()));
          notaEsCabDetDTO.setPrecVta(aux+"");
          notaEsCabDetDTO.setValFracTransfFrm(((ArrayList)obtenerProducto.get(0)).get(10).toString());
        
          String productoBuscar = notaEsCabDetDTO.getDescProdFrm().toUpperCase();
          String pCodigoBarra;
          
          // revisando codigo de barra
          char primerkeyChar = productoBuscar.charAt(0);
          char segundokeyChar;
          boolean pCambioDeBusqueda = false;
          if(productoBuscar.length() > 1)
            segundokeyChar = productoBuscar.charAt(1);
          else
            segundokeyChar = primerkeyChar;
          
          char ultimokeyChar = productoBuscar.charAt(productoBuscar.length()-1);
          if ( productoBuscar.length()>6 && (!Character.isLetter(primerkeyChar) && (!Character.isLetter(segundokeyChar) && (!Character.isLetter(ultimokeyChar))))) {
              pCodigoBarra = productoBuscar;
              try {
                 productoBuscar =  DBVentas.obtieneCodigoProductoBarra(pCodigoBarra);
              } catch (SQLException q) {
                  log.error("",q);
                 productoBuscar = "000000";
              }
              pCambioDeBusqueda = true;                 
          }
          if (productoBuscar.equalsIgnoreCase("000000")) {
              FarmaUtility.showMessage(this, "No existe producto relacionado con el Código de Barra. Verifique!!!", txtBuscar);
              return;
          } 
          
          String pCodigoBusqueda =  notaEsCabDetDTO.getDescProdFrm();
          
          if(pCambioDeBusqueda){
              pCodigoBusqueda = productoBuscar.trim();
          }
          
          if (!(FarmaUtility.findTextInJTable(tblListaProductos,pCodigoBusqueda, 1, 2)) ){
             FarmaUtility.showMessage(this,"Producto No Encontrado según Criterio de Búsqueda !!!", txtBuscar);
             return;
           }
        
    }
    
    private void seleccionarProducto(){
        boolean seleccion = tblListaProductos.getSelectedRow()==-1?true:false;
        
      if(!seleccion){
          NotaEsCabDetDTO notaEsCabDetDTO = new NotaEsCabDetDTO();
          cargaDetalleProducto(notaEsCabDetDTO);
          
            if(!validaStockDisponible(notaEsCabDetDTO)){
              return;
            }
            if(!validaProductoTomaInventario(notaEsCabDetDTO)){
              FarmaUtility.showMessage(this, "El Producto se encuentra Congelado por Toma de Inventario", txtBuscar);
              return;
            }
            DlgDevolucionIngresoCantidad dlgDevolucionIngresoCantidad = new DlgDevolucionIngresoCantidad(myParentFrame,"",true);
            dlgDevolucionIngresoCantidad.setNotaEsCabDetDTO(notaEsCabDetDTO);
            dlgDevolucionIngresoCantidad.setVisible(true);
            if(FarmaVariables.vAceptar){
    
              //seleccionaProducto(); 
              agregarProducto_02(notaEsCabDetDTO); 
              //FarmaUtility.setCheckValue(tblListaProductos,false);
              FarmaVariables.vAceptar = false;
              //tblListaProductos.setRowSelectionInterval(VariablesInventario.vPos,VariablesInventario.vPos);
              
              VariablesInventario.vArrayTransferenciaProductos = VariablesInventario.vArrayProductos;
              cerrarVentana(true);                 
            }
      }
      else
      {
        //deseleccionaProducto();
        //borrarProducto();
        FarmaUtility.setCheckValue(tblListaProductos,false);
      }
      
    }
    
    private void agregarProducto_02(NotaEsCabDetDTO notaEsCabDetDTO){
      ArrayList array = new ArrayList();
      array.add(notaEsCabDetDTO.getCodProd()); 
      array.add(notaEsCabDetDTO.getDescProdFrm()); 
      array.add(String.valueOf(notaEsCabDetDTO.getDescUnidaVta()));
      array.add(notaEsCabDetDTO.getDescLab()); 
      array.add(notaEsCabDetDTO.getCantMov()+"");
      array.add(notaEsCabDetDTO.getPrecVta()); 
      array.add(notaEsCabDetDTO.getFecVtoProd()); 
      array.add(notaEsCabDetDTO.getNumLoteProd());
      array.add(notaEsCabDetDTO.getStockProFrm()); 
      array.add(notaEsCabDetDTO.getValFracTransfFrm()); 
      array.add(notaEsCabDetDTO.getValPrecioTotal());
      array.add(notaEsCabDetDTO.getFechaHoraTransfFrm()); 
      array.add(notaEsCabDetDTO.getSecRespalStock()+"");
      VariablesInventario.vArrayProductos.add(array);
    }
    
    private boolean validaProductoTomaInventario(NotaEsCabDetDTO notaEsCabDetDTO){
      //if(indProdCong.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) return true;
      if(notaEsCabDetDTO.getIndProdCongFrm().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) return true;
      else return false;
    }
    
    private boolean validaStockDisponible(NotaEsCabDetDTO notaEsCabDetDTO){
      if(FarmaUtility.isInteger(notaEsCabDetDTO.getStockProFrm()) && Integer.parseInt(notaEsCabDetDTO.getStockProFrm()) > 0) 
        return true;
      else 
        return false;
    }

    public void iniciarCampos(){
        //GFonseca 16.12.2013 mostrar mensaje de que no se puede buscar.
        if(contBus == 1){
            txtBuscar.setText(notaEsCabDTO.getCodOrdenCompra());
            txtBuscar.setEnabled(false);      
            listaDetOrdenesCompra = facadeInventario.listarProductosPorOrdenCompra(notaEsCabDTO.getCodOrdenCompra(),new NotaEsCabDetDTO());
            if(listaDetOrdenesCompra != null && listaDetOrdenesCompra.size()>0){
                tableModel.data = listaDetOrdenesCompra;
            }
            contBus++;
        }else{
            FarmaUtility.showMessage(this, "No se puede buscar nuevamente hasta salir y volver a entrar a la pantalla.", txtBuscar);
        }
    }
    
    private void muestraInfoProd(){
      /*
      if(tblListaProductos.getRowCount() <= 0) return;
      ArrayList myArray = new ArrayList();
      obtieneInfoProdEnArrayList(myArray);
      if(myArray.size() == 1){
        
        notaEsCabDetDTO.setStockProFrm(((String)((ArrayList)myArray.get(0)).get(0)).trim());
        notaEsCabDetDTO.setIndProdCongFrm(((String)((ArrayList)myArray.get(0)).get(1)).trim());
        notaEsCabDetDTO.setFechaHoraTransfFrm(((String)((ArrayList)myArray.get(0)).get(2)).trim());
        notaEsCabDetDTO.setValFracTransfFrm(tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),9).toString());
        
      }else{
        stkProd = "";
        indProdCong = "";
        FarmaUtility.showMessage(this, "Error al obtener Informacion del Producto", txtBuscar);
      }
      tblListaProductos.setValueAt(stkProd, tblListaProductos.getSelectedRow(), 5);
      tblListaProductos.setValueAt(indProdCong, tblListaProductos.getSelectedRow(), 8);
      
      tblListaProductos.repaint();*/
      
    }
    
    private void obtieneInfoProdEnArrayList(ArrayList pArrayList){
      /*
        try{
            
          if(tblListaProductos.getSelectedRow()>-1){
              String codProd =  notaEsCabDetDTO.getCodProd();
              ((String)(tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),1))).trim();
              DBInventario.obtieneInfoProducto(pArrayList, codProd);
              DBInventario.obtieneInfoProducto(pArrayList, notaEsCabDetDTO.getCodProd());
          }
            
        }catch(SQLException sql){
          log.error("",sql);
          FarmaUtility.showMessage(this,"Ocurrió un error al obtener información del producto : \n" + sql.getMessage(),txtBuscar);
        }*/
    }

    private void buscarOrdenCompra() {
        if(notaEsCabDTO.getCodOrdenCompra() != null){
            iniciarCampos();
        }else{
            DlgDevolucionesListaOC dlgListaOrdenesCompra = new DlgDevolucionesListaOC(myParentFrame,"",true);
            dlgListaOrdenesCompra.setFacadeInventario(facadeInventario);
            dlgListaOrdenesCompra.setNotaEsCabDTO(notaEsCabDTO);
            dlgListaOrdenesCompra.setVisible(true);  
            if(FarmaVariables.vAceptar){
                tableModel.clearTable();
                iniciarCampos();
                FarmaVariables.vAceptar = false;                               
            }
        }
    }

    void setFacadeInventario(FacadeInventario facadeInventario) {
        this.facadeInventario = facadeInventario;
    }

    void setNotaEsCabDTO(NotaEsCabDTO notaEsCabDTO) {
        this.notaEsCabDTO = notaEsCabDTO;
    }

}
