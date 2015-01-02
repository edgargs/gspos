package mifarma.ptoventa.inventariodiario;

import com.gs.mifarma.componentes.*;

import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;

import java.awt.*;
import java.awt.event.*;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.*;

import mifarma.common.*;

import mifarma.ptoventa.*;
import mifarma.ptoventa.inventariodiario.reference.ConstantsInvDiario;
import mifarma.ptoventa.inventariodiario.reference.DBInvDiario;
import mifarma.ptoventa.inventariodiario.reference.VariablesInvDiario;
import mifarma.ptoventa.reference.*;
import mifarma.ptoventa.tomainventario.reference.*;
import mifarma.ptoventa.ventas.reference.UtilityVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgPedidoDiario extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgPedidoDiario.class);

    FarmaTableModel tableModelListaProductos;
    Frame myParentFrame;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblListaProductosPedido = new JTable();
    private JButtonLabel btnRelacionProductos = new JButtonLabel();
    private JButtonLabel btnProductos = new JButtonLabel();
    private JTextFieldSanSerif txtProductos = new JTextFieldSanSerif();
    private JLabelFunction lblEscape = new JLabelFunction();
    private JPanelTitle jPanelTitle2 = new JPanelTitle();
    private JButtonLabel lblMontoTotal = new JButtonLabel();
    private JLabelFunction lblF8 = new JLabelFunction();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JLabelWhite lblDctoT = new JLabelWhite();
    private JLabelWhite jLabelWhite3 = new JLabelWhite();
    private JLabelWhite lblVentaT = new JLabelWhite();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JLabelFunction jLabelFunction2 = new JLabelFunction();
    private JLabelFunction jLabelFunction3 = new JLabelFunction();
    private JLabelWhite jLabelWhite5 = new JLabelWhite();
    private JLabelWhite jLabelWhite6 = new JLabelWhite();
    private JLabelWhite lblRedondeo = new JLabelWhite();
    private JLabelWhite lblDiferencia = new JLabelWhite();
    private JLabelWhite lblDiferenciaV = new JLabelWhite();
    private double DiferenciaGlobal =0;

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgPedidoDiario() {
        this(null, "", false);
    }

    public DlgPedidoDiario(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("",e);
        }
    }
    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(649, 471));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Pedido para Generar Boleta");
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }
                });
        jPanelHeader1.setBounds(new Rectangle(10, 10, 625, 90));
        jPanelHeader1.setLayout(null);
        jPanelTitle1.setBounds(new Rectangle(10, 100, 625, 25));
        jPanelTitle1.setLayout(null);
        jScrollPane1.setBounds(new Rectangle(10, 125, 625, 255));
        
        btnRelacionProductos.setText("Listado de Productos");
        btnRelacionProductos.setBounds(new Rectangle(5, 0, 215, 25));
        
        btnProductos.setText("Productos :");
        btnProductos.setMnemonic('p');
        btnProductos.setBounds(new Rectangle(15, 5, 65, 20));
        btnProductos.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnProductos_actionPerformed(e);
                    }
                });
        txtProductos.setBounds(new Rectangle(100, 5, 330, 20));
        txtProductos.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtProductos_keyPressed(e);
                    }

                    public void keyReleased(KeyEvent e) {
                        txtProductos_keyReleased(e);
                    }
                });
        lblEscape.setBounds(new Rectangle(515, 415, 110, 20));
        lblEscape.setText("[ ESCAPE ] Cerrar");
        jPanelTitle2.setBounds(new Rectangle(10, 380, 625, 25));
        jPanelTitle2.setLayout(null);
        lblMontoTotal.setBounds(new Rectangle(510, 0, 95, 25));
        lblMontoTotal.setText("0");
        
        lblF8.setText("[ F8 ] Exportar a Excel");
        lblF8.setBounds(new Rectangle(405, 365, 170, 20));
        lblF8.setVisible(false);
        jLabelWhite1.setText("Total Dscto. Personal S/.");
        jLabelWhite1.setBounds(new Rectangle(15, 55, 150, 20));
        jLabelWhite1.setFont(new Font("SansSerif", 1, 12));
        lblDctoT.setText("0");
        lblDctoT.setBounds(new Rectangle(160, 55, 90, 20));
        lblDctoT.setFont(new Font("SansSerif", 1, 12));
        jLabelWhite3.setText("Total Venta S/.");
        jLabelWhite3.setBounds(new Rectangle(15, 35, 115, 15));
        jLabelWhite3.setFont(new Font("SansSerif", 1, 12));
        lblVentaT.setText("0");
        lblVentaT.setBounds(new Rectangle(135, 35, 90, 15));
        lblVentaT.setFont(new Font("SansSerif", 1, 12));
        jLabelFunction1.setBounds(new Rectangle(60, 415, 170, 20));
        jLabelFunction1.setText("[ F3 ] Agregar Dcto Personal");
        jLabelFunction2.setBounds(new Rectangle(240, 415, 140, 20));
        jLabelFunction2.setText("[ F5 ] Eliminar Producto");
        jLabelFunction3.setBounds(new Rectangle(390, 415, 117, 19));
        jLabelFunction3.setText("[ F11 ] Aceptar");
        jLabelWhite5.setText("Total S/.");
        jLabelWhite5.setBounds(new Rectangle(460, 0, 50, 25));
        jLabelWhite6.setText("Redondeo S/.");
        jLabelWhite6.setBounds(new Rectangle(30, 0, 80, 25));
        lblRedondeo.setText("0");
        lblRedondeo.setBounds(new Rectangle(110, 0, 80, 25));
        lblDiferencia.setText("Existe una diferencia de S/.");
        lblDiferencia.setBounds(new Rectangle(275, 60, 165, 15));
        lblDiferencia.setFont(new Font("SansSerif", 1, 12));
        lblDiferenciaV.setText("0");
        lblDiferenciaV.setBounds(new Rectangle(435, 60, 95, 15));
        lblDiferenciaV.setFont(new Font("SansSerif", 1, 12));
        jContentPane.add(jLabelFunction3, null);
        jContentPane.add(jLabelFunction2, null);
        jContentPane.add(jLabelFunction1, null);
        jContentPane.add(lblF8, null);
        jPanelTitle2.add(lblRedondeo, null);
        jPanelTitle2.add(jLabelWhite6, null);
        jPanelTitle2.add(jLabelWhite5, null);
        jPanelTitle2.add(lblMontoTotal, null);
        jContentPane.add(jPanelTitle2, null);
        jScrollPane1.getViewport().add(tblListaProductosPedido, null);
        jContentPane.add(jScrollPane1, null);
        jPanelTitle1.add(btnRelacionProductos, null);
        jContentPane.add(jPanelTitle1, null);
        jContentPane.add(jPanelHeader1, null);
        jContentPane.add(lblEscape, null);
        jPanelHeader1.add(lblDiferenciaV, null);
        jPanelHeader1.add(lblDiferencia, null);
        jPanelHeader1.add(lblVentaT, null);
        jPanelHeader1.add(jLabelWhite3, null);
        jPanelHeader1.add(jLabelWhite1, null);
        jPanelHeader1.add(txtProductos, null);
        jPanelHeader1.add(btnProductos, null);
        jPanelHeader1.add(lblDctoT, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_AUDITORIA) && 
            FarmaVariables.vEconoFar_Matriz)
            lblF8.setVisible(true);
        initTableListaDiferenciasConsolidado();
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTableListaDiferenciasConsolidado() {
        tableModelListaProductos = 
                new FarmaTableModel(ConstantsInvDiario.columnsListaDetTemporal, 
                                    ConstantsInvDiario.defaultValuesListaDetTemporal, 
                                    0);
        FarmaUtility.initSimpleList(tblListaProductosPedido, 
                                    tableModelListaProductos, 
                                    ConstantsInvDiario.columnsListaDetTemporal);                
        
        cargaListaDetTemporal(VariablesInvDiario.vSecPedidoTemporal);

        
    }

    // **************************************************************************
    // Metodos de eventos   
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtProductos);
        lblDiferencia.setVisible(false); 
         lblDiferenciaV.setVisible(false); 
        operaTotales();

    }

    private void txtProductos_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, 
                                              tblListaProductosPedido, 
                                              txtProductos, 1);
        chkKeyPressed(e);
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            eliminaPedidoTemporal();
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            asignarTrabajador();
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            eliminaProducto();
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            
            generarPedido();
        }
    }

    private void cerrarVentana(boolean pAceptar)
    {
      FarmaVariables.vAceptar = pAceptar;
      this.setVisible(false);
      this.dispose();
    }

    private void btnProductos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtProductos);
    }

    private void txtProductos_keyReleased(KeyEvent e) {
        chkKeyReleased(e);
    }

    private void chkKeyReleased(KeyEvent e) {
        operaTotales();
        FarmaGridUtils.buscarDescripcion(e, tblListaProductosPedido, 
                                         txtProductos, 1);
        
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cargaListaDetTemporal(String pNumPedidoTemporal_in) {
        try {
            DBInvDiario.cargaDetallePedTemporal(tableModelListaProductos,
                                                pNumPedidoTemporal_in);
            if (tblListaProductosPedido.getRowCount() > 0)
                FarmaUtility.ordenar(tblListaProductosPedido, 
                                     tableModelListaProductos, 1, 
                                     FarmaConstants.ORDEN_ASCENDENTE);
           /* lblMontoTotal.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblListaProductosPedido, 
                                                                                  5)));*/
        } catch (SQLException sql) {
            log.error("",sql);
            FarmaUtility.showMessage(this, 
                                     "Error al obtener el detalle de Pedido Temporal :\n" +
                    sql.getMessage(), txtProductos);
        }
    }
    
   
    

    /**
     * Algoritmo para asignar los trabajadores 
     * @author dubilluz
     * @since  15.06.2009
     */
    public void asignarTrabajador(){
        DlgIngresoTrabajadores dlgIngresoTrab = 
            new DlgIngresoTrabajadores(myParentFrame, "", true);
        dlgIngresoTrab.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
        }
        
    }
    
    private void operaTotales()
    {
        double dTotalVenta=0,dTotalDcto=0,dRedondeo=0,dDiferencia=0;
        
        for(int i=0;i<tableModelListaProductos.data.size();i++)
        {
            dTotalVenta += FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(tableModelListaProductos.data,i,5));
        }
        
        for(int i=0;i<VariablesInvDiario.vListaTrabParaDescuento.size();i++)
        {
            log.debug("",VariablesInvDiario.vListaTrabParaDescuento.get(i));
            dTotalDcto += FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(VariablesInvDiario.vListaTrabParaDescuento,i,3));
        }        
        
        //dDiferencia = dTotalVenta - dTotalDcto;
        
        //lblVentaT.setText(FarmaUtility.formatNumber(dTotalVenta));   
        lblDctoT.setText(FarmaUtility.formatNumber(dTotalDcto));
        
        double totalNetoRedondeado =0;
        totalNetoRedondeado = UtilityVentas.Redondear(dTotalVenta, 2);//redondeo a 2 cifras pero no a ajustado a .05 o 0.00
        log.debug("redondeando a 2 cifras totalNetoRedondeado:"+totalNetoRedondeado);
        double totalNetoRedNUEVO =  UtilityVentas.ajustarMonto(totalNetoRedondeado, 3);
        totalNetoRedondeado = UtilityVentas.ajustarMonto(totalNetoRedondeado, 2);    
        log.debug("ajustando monto a 2 cifras totalNetoRedondeado:"+totalNetoRedondeado);
        
        dRedondeo =  totalNetoRedondeado - dTotalVenta;
        lblRedondeo.setText(FarmaUtility.formatNumber(dRedondeo));
        log.debug("totalNetoRedondeado:"+totalNetoRedondeado);
        log.debug("dTotalVenta:"+dTotalVenta);
        
        lblVentaT.setText(FarmaUtility.formatNumber(totalNetoRedondeado));   
        VariablesInvDiario.vTotalPedido = FarmaUtility.formatNumber(totalNetoRedondeado);
        
        lblMontoTotal.setText(FarmaUtility.formatNumber(totalNetoRedondeado));   
        dDiferencia = totalNetoRedondeado - dTotalDcto;
        DiferenciaGlobal=dDiferencia;
        if(dDiferencia>0){
           lblDiferencia.setVisible(true); 
            lblDiferenciaV.setVisible(true); 
           lblDiferenciaV.setText(FarmaUtility.formatNumber(dDiferencia));   
        }

         
    }
    
    public void eliminaProducto(){
        if(tblListaProductosPedido.getRowCount()>0){
            int vFila = tblListaProductosPedido.getSelectedRow();
            if(com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "¿Seguro de quitar el producto del pedido?"))
            {

                try {
                    DBInvDiario.eliminaDetallePedTemporal(VariablesInvDiario.vSecPedidoTemporal, 
                                                          FarmaUtility.getValueFieldArrayList(tableModelListaProductos.data, 
                                                                                              vFila, 
                                                                                              0));
                    FarmaUtility.aceptarTransaccion();

                    tableModelListaProductos.deleteRow(vFila);
                    tblListaProductosPedido.repaint();
                    if (tableModelListaProductos.getRowCount() > 0) {
                        FarmaGridUtils.showCell(tblListaProductosPedido, 0, 
                                                0);
                    }
                    operaTotales();
                } catch (SQLException e) {
                    FarmaUtility.liberarTransaccion();
                    log.error("",e);
                    FarmaUtility.showMessage(this,"Ocurrió un error al eliminar el producto del pedido.\n"+e.getMessage(),txtProductos);
                }
            }
        }
    }
    
    public void eliminaPedidoTemporal(){
        if(com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "¿Seguro que desea salir del pedido?"))
        {
            try {
                DBInvDiario.eliminaPedTemporal(VariablesInvDiario.vSecPedidoTemporal);
                FarmaUtility.aceptarTransaccion();
                limpiaVariables();
                cerrarVentana(false);
            } catch (SQLException e) {
                FarmaUtility.liberarTransaccion();
                log.error("",e);
                FarmaUtility.showMessage(this,"Ocurrió un error al eliminar el pedido.\n"+e.getMessage(),txtProductos);
            }
        }
    }
    
    public void limpiaVariables(){
       // VariablesInvDiario.vSecPedidoTemporal= "";
        VariablesInvDiario.vListaTrabParaDescuento = new ArrayList();
        VariablesInvDiario.vAccion = "";
        VariablesInvDiario.dTotalDescTrab = 0;
        VariablesInvDiario.dTotalProds = 0;
        VariablesInvDiario.vListadoProductosDiferenciaSeleccionadas =  new ArrayList();
    }
    
    public void generarPedido()
    {
        if(tableModelListaProductos.getRowCount()>0) {
            
            if(DiferenciaGlobal == 0){ 
            try
            {
                DBInvDiario.generaPedidoInvDiario(
                                          VariablesInvDiario.vSecPedidoTemporal,
                                          FarmaUtility.getDecimalNumber(lblVentaT.getText())+"",
                                          FarmaUtility.getDecimalNumber(lblRedondeo.getText())+"",
                                          VariablesInvDiario.vListaTrabParaDescuento
                                         );
                FarmaUtility.aceptarTransaccion();
                limpiaVariables();
                cerrarVentana(true);
            }
            catch (SQLException e)
            {
               FarmaUtility.liberarTransaccion();
               log.error("",e);
               if(e.getErrorCode()==20003){
                   FarmaUtility.showMessage(this, 
                                            "El monto asignado a los Trabajadores\n es menor que el pedido",
                                            txtProductos);
               }
               else{
               FarmaUtility.showMessage(this, 
                                        "Ocurrió un error al generar el Pedido.\n" +
                       e.getMessage(),txtProductos);
               }
            }
            }
            else
            {
                FarmaUtility.showMessage(this,  "Falta completar el monto total de descuento.\n",txtProductos);    
            }
        }
    }
}
