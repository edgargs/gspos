package mifarma.ptoventa.inventario;


/* GUI JAVA*/


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

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

import java.lang.Exception;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.FacadeInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/* SQL y Utilitarios JAVA */


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgMercaderiaDirectaBuscar.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LRUIZ      16.05.2013   Creación<br>
 * <br>
 * @author Luis Ruiz Peralta<br>
 * @version 1.0<br>
 *
 */
public class DlgMercaderiaDirectaBuscar extends JDialog 
{
  /* ********************************************************************** */
  /*                        DECLARACION PROPIEDADES                         */
  /* ********************************************************************** */
  private static final Logger log = LoggerFactory.getLogger(DlgMercaderiaDirectaBuscar.class);

  private Frame myParentFrame;
  private FarmaTableModel tableModel;
  private String fechaIni;
  private String fechaFin;
  
  private BorderLayout borderLayout1             = new BorderLayout();
  private JPanelWhite jContentPane               = new JPanelWhite();
  private JPanelTitle pnlTitle1                  = new JPanelTitle();
  private JScrollPane scrListaTransferencias     = new JScrollPane();
  private JTable tblListaOrdenCompra             = new JTable();
  private JButtonLabel btnRelacionOrdenCompra    = new JButtonLabel();
  private JLabelFunction lblEsc                  = new JLabelFunction();
  private JLabelFunction lblF2                   = new JLabelFunction();
  private JPanelHeader pnlCriterioBusqueda       = new JPanelHeader();
  private JButton btnBuscar                      = new JButton();
  private JTextFieldSanSerif txtFechaIni         = new JTextFieldSanSerif();
 private JTextFieldSanSerif txtFechaFin          =new JTextFieldSanSerif();//change by Cesar Huanes
  private JButtonLabel btnRandoFec               = new JButtonLabel();
  private JButtonLabel btnBuscarDesc             = new JButtonLabel();
  private JTextFieldSanSerif txtBuscar           = new JTextFieldSanSerif();
  private JLabelFunction lblF8                   = new JLabelFunction();
  private JLabelFunction lblF1                   = new JLabelFunction();
  private FacadeInventario facadeOrdenCompra     = new FacadeInventario();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */
 
  /**
   * Constructor
   **/
  
 public DlgMercaderiaDirectaBuscar() {
    this(null, "", false);
  }

    /**
     * Constructor
     * @param parent Objeto Frame de la Aplicación.
     * @param title Título de la Ventana.
     * @param modal Tipo de Ventana.
     */
    
    public DlgMercaderiaDirectaBuscar(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initializeTable();
            FarmaUtility.centrarVentana(this);
        }catch(Exception e) {
            log.error("",e);
        }
    }

  /* ************************************************************************ */
  /*                                  METODO jbInit                           */
  /* ************************************************************************ */

    /**
     * Implementa la Ventana con todos sus Objetos
     */    
    private void jbInit() throws Exception
    {
        this.setSize(new Dimension(735, 484));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Buscar Ord. Comp. - Mercaderia Directa");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(10, 105, 700, 25));
        scrListaTransferencias.setBounds(new Rectangle(10, 130, 700, 260));
        btnRelacionOrdenCompra.setText("Relación de Ordenes de Compra");
        btnRelacionOrdenCompra.setBounds(new Rectangle(5, 5, 265, 15));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(620, 430, 90, 20));
        lblF2.setText("[ F2 ] Ver Detalle Recepción Ord. Compr.");
        lblF2.setBounds(new Rectangle(310, 405, 255, 20));
        pnlCriterioBusqueda.setBounds(new Rectangle(10, 10, 700, 85));
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(475, 15, 95, 20));
        btnBuscar.setMnemonic('b');
        btnBuscar.setFont(new Font("SansSerif", 1, 11));
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
              btnBuscar_actionPerformed(e);
            }
          });
      /*Change by Cesar Huanes*/
      txtFechaFin.setBounds(new Rectangle(345,15,101,19));
      txtFechaFin.addKeyListener(new KeyAdapter(){
          public void keyPressed(KeyEvent e)
          {
             txtFechaFin_keyPressed(e);
          
          }
          public void keyReleased(KeyEvent e)
          {
              txtFechaFin_keyReleased(e);
                      
                  }
      });
      txtFechaFin.setDocument(new FarmaLengthText(10));
        txtFechaIni.setBounds(new Rectangle(220, 15, 101, 19));
        txtFechaIni.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e)
            {
              txtFechaIni_keyPressed(e);
            }
            public void keyReleased(KeyEvent e)
            {
              txtFechaIni_keyReleased(e);
            }
          });
        txtFechaIni.setDocument(new FarmaLengthText(10));
        btnRandoFec.setText("Rango de Fechas :");
        btnRandoFec.setBounds(new Rectangle(110, 15, 100 , 20));
        btnRandoFec.setMnemonic('f');
        btnRandoFec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
              btnRandoFec_actionPerformed(e);
            }
          });
        btnBuscarDesc.setText("RUC de Proveedor :");
        btnBuscarDesc.setBounds(new Rectangle(85, 50, 135, 20));
        btnBuscarDesc.setMnemonic('o');
        btnBuscarDesc.setPreferredSize(new Dimension(145, 20));
        btnBuscarDesc.setSize(new Dimension(155, 20));
        btnBuscarDesc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
              btnBuscarDesc_actionPerformed(e);
            }
          });
        txtBuscar.setBounds(new Rectangle(220, 50, 300, 20));
        txtBuscar.addKeyListener(new KeyAdapter() {
        public void keyPressed(KeyEvent e)
        {
            txtBuscar_keyPressed(e);
        }
        public void keyReleased(KeyEvent e)
        {
          txtBuscar_keyReleased(e);
        }
      });
        txtBuscar.setDocument(new FarmaLengthText(30));
        lblF8.setText("[ F8 ] Exportar a Excel");
        lblF8.setVisible(false);
        lblF8.setBounds(new Rectangle(400, 400, 170, 20));
        lblF1.setBounds(new Rectangle(10, 405, 270, 20));
        lblF1.setText("[ F1 ] Nuevo Ingreso Recepción Ord. Compr.");
        pnlCriterioBusqueda.add(txtBuscar, null);
        pnlCriterioBusqueda.add(btnBuscarDesc, null);
        pnlCriterioBusqueda.add(btnBuscar, null);
        pnlCriterioBusqueda.add(txtFechaFin,null);//Change By Cesar Huanes
        pnlCriterioBusqueda.add(txtFechaIni, null);  
        pnlCriterioBusqueda.add(btnRandoFec, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(lblF8, null);
        jContentPane.add(pnlCriterioBusqueda, null);
        jContentPane.add(lblF2, null);
        jContentPane.add(lblEsc, null);
        scrListaTransferencias.getViewport().add(tblListaOrdenCompra, null);
        jContentPane.add(scrListaTransferencias, null);
        pnlTitle1.add(btnRelacionOrdenCompra, null);
        jContentPane.add(pnlTitle1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
  }
  
  /* ************************************************************************ */
  /*                                  METODO initialize                       */
  /* ************************************************************************ */

  private void initializeTable() {
      tableModel = new FarmaTableModel(ConstantsInventario.columnsListaResumenOrden,
                                       ConstantsInventario.defaultListaResumenOrden,
                                       0);
      FarmaUtility.initSimpleList(tblListaOrdenCompra, 
                                  tableModel,
                                  ConstantsInventario.columnsListaResumenOrden);
      FarmaVariables.vAceptar = false;
  }
  
  /* ************************************************************************ */
  /*                            METODOS INICIALIZACION                        */
  /* ************************************************************************ */ 
  private void cargaListaOrdenCompra() {
      tableModel.clearTable();
      ArrayList tmpListOrdenCompra       = new ArrayList();      
    //  tmpListOrdenCompra                 = facadeOrdenCompra.listarOrdenCompra(fechaIni);
    tmpListOrdenCompra= facadeOrdenCompra.listaOrdenCompraByFecha(fechaFin,fechaIni);
      if ( tmpListOrdenCompra.size() > 0 ){
            tableModel.data = tmpListOrdenCompra;
            tblListaOrdenCompra.setRowSelectionInterval(0,0);
      }else{
          FarmaUtility.showMessage(this, 
                                   "No existe información de la Orden de Compra en la fecha indicada.", 
                                   null);
      }      
  }
  
  /* ************************************************************************ */
  /*                            METODOS DE EVENTOS                            */
  /* ************************************************************************ */

  private void this_windowOpened(WindowEvent e) {
      FarmaUtility.moveFocus(txtFechaIni);
      try {
            String fechaActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            String fechaAtrasada = FarmaSearch.getFechaHoraAtrasadaBD(FarmaConstants.FORMATO_FECHA,30);//Change by Cesar Huanes
            txtFechaIni.setText(fechaAtrasada);
            txtFechaFin.setText(fechaActual);//Change by Cesar Huanes
        
      }catch(SQLException sql) {
            FarmaUtility.showMessage(this, 
                                     "No existe información de la Orden de Compra en la fecha indicada.",
                                     sql.getMessage());
          }
  }

  private void this_windowClosing(WindowEvent e) {
    FarmaUtility.showMessage(this, 
                             "Debe presionar la tecla ESC para cerrar la ventana.",
                             btnRelacionOrdenCompra);
  }
  
  private void btnRandoFec_actionPerformed(ActionEvent e) {
      FarmaUtility.moveFocus(txtFechaIni);
  }
  
  private void txtFechaIni_keyPressed(KeyEvent e) {
      if(e.getKeyCode() == KeyEvent.VK_ENTER){
          FarmaUtility.moveFocus(txtFechaFin);
      }else 
          chkKeyPressed(e);
  }
  
  private void txtFechaIni_keyReleased(KeyEvent e) {
    FarmaUtility.dateComplete(txtFechaIni,e);
  }
  
/*Change by Cesar Huanes*/
    private void txtFechaFin_keyPressed(KeyEvent e)
  {
    if(e.getKeyCode() == KeyEvent.VK_ENTER){
      btnBuscar.doClick();
    }
    else 
      chkKeyPressed(e);
  }
  
  private void txtFechaFin_keyReleased(KeyEvent e)
  {
    FarmaUtility.dateComplete(txtFechaFin,e);
  }
  
  private void txtBuscar_keyReleased(KeyEvent e) {
      FarmaGridUtils.buscarDescripcion(e, tblListaOrdenCompra, txtBuscar, 2);
  }

  private void btnBuscar_actionPerformed(ActionEvent e) {
    if(validarCampos()) {
        fechaIni = txtFechaIni.getText().trim();
        fechaFin=txtFechaFin.getText().trim();
        
        cargaListaOrdenCompra();
        FarmaUtility.moveFocus(txtFechaIni);
    }  
  }
  
  private void btnBuscarDesc_actionPerformed(ActionEvent e) {
    FarmaUtility.moveFocus(txtBuscar);
  }
  
  private void txtBuscar_keyPressed(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          e.consume();
          if (tblListaOrdenCompra.getSelectedRow() >= 0) {
              if (!(FarmaUtility.findTextInJTable(tblListaOrdenCompra, txtBuscar.getText().trim(), 2, 3))) {
                  FarmaUtility.showMessage(this, "No se encontro Ord. Compra según Criterio de Búsqueda", txtBuscar);
                  return;
                }
            }
          }
	chkKeyPressed(e);
  }
  
  /* ************************************************************************ */
  /*                     METODOS AUXILIARES DE EVENTOS                        */
  /* ************************************************************************ */

  private void chkKeyPressed(KeyEvent e) {
    FarmaGridUtils.aceptarTeclaPresionada(e, tblListaOrdenCompra, txtBuscar, 2);
      if(e.getKeyCode() == KeyEvent.VK_ENTER) {
          if (!FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_SUPERVISOR_VENTAS) ) { 
                if(FarmaVariables.vEconoFar_Matriz)
                  FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,btnRelacionOrdenCompra);
                else {
                    VariablesInventario.vArrayIngresoMercaderiaDirecta = new ArrayList();
                    cargaListaOrdenCompra();                    
                }
          }
          
      }else if (UtilityPtoVenta.verificaVK_F2(e)) {
          listaDetaRecepOrdComp();
          
      }else if(UtilityPtoVenta.verificaVK_F1(e)){
         ingreRecepOrdCompra();
          
      }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
           cerrarVentana(false);
        }
  }
  /*Cesar Huanes 10/12/13 validacion de errores  */
  private void ingreRecepOrdCompra(){
      
      int selectedRow=tblListaOrdenCompra.getSelectedRow();
  
      if(selectedRow>=0){
          try{
      VariablesInventario.vNumOrdenCompra = tableModel.getValueAt(tblListaOrdenCompra.getSelectedRow(), 0).toString();
      VariablesInventario.vImporteTotal   = tableModel.getValueAt(tblListaOrdenCompra.getSelectedRow(), 5).toString();
      VariablesInventario.vCodProveedor   = tableModel.getValueAt(tblListaOrdenCompra.getSelectedRow(), 8).toString();
      VariablesInventario.vDescProveedor  = tableModel.getValueAt(tblListaOrdenCompra.getSelectedRow(), 3).toString();
      VariablesInventario.vCodFormaPago   = tableModel.getValueAt(tblListaOrdenCompra.getSelectedRow(), 9).toString();
      VariablesInventario.vDescFormaPago  = tableModel.getValueAt(tblListaOrdenCompra.getSelectedRow(), 10).toString();
      VariablesInventario.vRUCProveedor   = tableModel.getValueAt(tblListaOrdenCompra.getSelectedRow(), 11).toString();
      VariablesInventario.vEstadoOrdComp  = tableModel.getValueAt(tblListaOrdenCompra.getSelectedRow(), 12).toString();
      VariablesInventario.vCantItem       = tableModel.getValueAt(tblListaOrdenCompra.getSelectedRow(), 4).toString();
      
      if(VariablesInventario.vEstadoOrdComp.equals("T")) {
         
          FarmaUtility.showMessage(this, "La Ord. de Compra " + VariablesInventario.vNumOrdenCompra + 
                                         " completo la recepción de productos y se encuentra cerrada.", null);              
      } else{
      DlgMercaderiaDirectaCabecera dlgMDirectaIngresoCabecera = new DlgMercaderiaDirectaCabecera(myParentFrame,"",true);
      dlgMDirectaIngresoCabecera.setFacade(facadeOrdenCompra);
      dlgMDirectaIngresoCabecera.setVisible(true);  
          
      if(FarmaVariables.vAceptar) {
           cargaListaOrdenCompra();
           FarmaVariables.vAceptar = false;
       }
          }
          }catch(Exception e){
              FarmaUtility.showMessage(this, 
                  "Ocurrió un error al obtener los datos de orden de compra\n " + e.getMessage(),null);  
          }
      }      else{
          FarmaUtility.showMessage(this, "Debe selecccionar un registro" ,null);  
      }
      
  }
  
  private void listaDetaRecepOrdComp(){
      int selectedRow=tblListaOrdenCompra.getSelectedRow();
      
      if(selectedRow>=0){
          try{
      VariablesInventario.vNumOrdenCompra = tableModel.getValueAt(tblListaOrdenCompra.getSelectedRow(), 0).toString();
      VariablesInventario.vCodProveedor   = tableModel.getValueAt(tblListaOrdenCompra.getSelectedRow(), 8).toString();
      
      DlgMercaderiaDirectaLista dlgLista = new DlgMercaderiaDirectaLista(myParentFrame, "", true);
      dlgLista.setVisible(true);
          
          }catch(Exception e){
              FarmaUtility.showMessage(this, 
                  "Ocurrió un error al obtener los datos de orden de mercaderia directa\n " + e.getMessage(),null);  
          }
      
      }
      else{
          FarmaUtility.showMessage(this, 
              "Debe selecccionar un registro" ,null);  
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
      if(txtFechaIni.getText().trim().equals("")) {
          retorno = false;
          FarmaUtility.showMessage(this,"Ingrese Fecha de Inicio.",txtFechaIni);
        }else if(txtFechaFin.getText().trim().equals("")) {
         retorno=false;
         FarmaUtility.showMessage(this, "Ingrese Fecha de Fin. ", txtFechaFin);
      }else if(!FarmaUtility.validaFecha(txtFechaIni.getText(),"dd/MM/yyyy")){
          retorno = false;
          FarmaUtility.showMessage(this,"Formato Incorrecto de Fecha.",txtFechaIni);
      }    else if(!FarmaUtility.validaFecha(txtFechaFin.getText(),"dd/MM/yyyy"))
    {
      retorno = false;
      FarmaUtility.showMessage(this,"Formato Incorrecto de Fecha.",txtFechaFin);
    }
    else if(!FarmaUtility.validarRangoFechas(this,txtFechaIni,txtFechaFin,false,true,true,true))
      retorno = false;
          
    return retorno;
  }     

}
  