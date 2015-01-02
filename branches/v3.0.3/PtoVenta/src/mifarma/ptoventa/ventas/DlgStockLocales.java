package mifarma.ptoventa.ventas;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConnectionRemoto;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.UtilityInventario;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgStockLocales extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgStockLocales.class);

    private Frame myParentFrame;
    private FarmaTableModel tableModelStockLocalesPreferidos;
    private FarmaTableModel tableModelStockDemasLocales;
    private String stkProd = "";
    private String descUnidVta = "";
    private JTable myJTable;

    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private GridLayout gridLayout1 = new GridLayout();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    JPanel pnlRelacion = new JPanel();
    XYLayout xYLayout2 = new XYLayout();
    private JButtonLabel btnStockLocales = new JButtonLabel();
    private JPanelTitle pnlDatosCliente = new JPanelTitle();
    private JLabelWhite lblProducto = new JLabelWhite();
    private JLabelWhite lblLaboratorioT = new JLabelWhite();
    private JLabelWhite lblProductoT = new JLabelWhite();
    private JLabelWhite lblLaboratorio = new JLabelWhite();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JScrollPane srcStockLocalesPreferidos = new JScrollPane();
    private JTable tblStockLocalesPreferidos = new JTable();
    JPanel pnlRelacion1 = new JPanel();
    XYLayout xYLayout3 = new XYLayout();
    private JScrollPane jScrollPane3 = new JScrollPane();
    private JButtonLabel btnDemasLocales = new JButtonLabel();
    private JScrollPane srcDemasLocales = new JScrollPane();
    private JTable tblStockDemasLocales = new JTable();
    JLabelFunction lblEsc = new JLabelFunction();
    private JLabelWhite lblUnidadPresentacion = new JLabelWhite();
    private JLabelWhite lblUnidadPresentacionT = new JLabelWhite();
    JLabelFunction lblF1 = new JLabelFunction();
    private JPanelWhite pnlWhite5 = new JPanelWhite();
    private JLabelWhite lblStockLimaT = new JLabelWhite();
    private JLabelWhite lblStockAlmacenT = new JLabelWhite();
    private JPanelTitle pnlTitle5 = new JPanelTitle();
    private JLabelWhite lblStockAlmacen = new JLabelWhite();
    private JLabelWhite lblStockLima = new JLabelWhite();
    private JLabelWhite lblStockProvincias = new JLabelWhite();
    private JLabelWhite lblStockProvinciaT = new JLabelWhite();

    /**
     * Constantes para los INdicadores de Stock
     * @author : dubilluz
     * @since  : 20.08.2007
     */
    private int POS_INDICADOR_LIMA = 0;
    private int POS_INDICADOR_PROVINCIA = 1;
    private int POS_INDICADOR_ALAMACEN = 2;

    public DlgStockLocales() {
        this(null, "", false);
    }

    public DlgStockLocales(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(501, 435));
        this.getContentPane().setLayout(gridLayout1);
        this.setTitle("Lista de Stock en Locales");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        jPanelTitle1.setBounds(new Rectangle(25, 25, 1, 1));
        pnlRelacion.setBackground(new Color(255, 130, 14));
        pnlRelacion.setLayout(xYLayout2);
        pnlRelacion.setFont(new Font("SansSerif", 0, 11));
        pnlRelacion.setBounds(new Rectangle(10, 145, 475, 25));
        btnStockLocales.setText("Stock Locales Preferidos :");
        btnStockLocales.setMnemonic('S');
        btnStockLocales.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnStockLocales_actionPerformed(e);
            }
        });
        pnlDatosCliente.setBounds(new Rectangle(10, 10, 475, 70));
        pnlDatosCliente.setBackground(Color.white);
        pnlDatosCliente.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        lblProducto.setBounds(new Rectangle(85, 10, 335, 15));
        lblProducto.setForeground(new Color(255, 130, 14));
        lblLaboratorioT.setText("Laboratorio :");
        lblLaboratorioT.setBounds(new Rectangle(5, 30, 90, 15));
        lblLaboratorioT.setForeground(new Color(255, 130, 14));
        lblProductoT.setText("Producto :");
        lblProductoT.setBounds(new Rectangle(5, 10, 120, 15));
        lblProductoT.setForeground(new Color(255, 130, 14));
        lblLaboratorio.setBounds(new Rectangle(85, 30, 335, 15));
        lblLaboratorio.setForeground(new Color(255, 130, 14));
        srcStockLocalesPreferidos.setBounds(new Rectangle(10, 170, 475, 210));
        tblStockLocalesPreferidos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblStockLocalesPreferidos_keyPressed(e);
            }
        });
        pnlRelacion1.setBackground(new Color(255, 130, 14));
        pnlRelacion1.setLayout(xYLayout3);
        pnlRelacion1.setFont(new Font("SansSerif", 0, 11));
        pnlRelacion1.setBounds(new Rectangle(10, 430, 475, 25));
        btnDemasLocales.setText("Stock Demas Locales :");
        btnDemasLocales.setMnemonic('D');
        /*btnDemasLocales.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          btnDemasLocales_actionPerformed(e);
        }
      });*/
        srcDemasLocales.setBounds(new Rectangle(10, 455, 475, 80));
        /*tblStockDemasLocales.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          tblStockDemasLocales_keyPressed(e);
        }
      });*/
        lblEsc.setText("[ Esc ]  Cerrar");
        lblEsc.setBounds(new Rectangle(390, 385, 95, 20));
        lblUnidadPresentacion.setText("Unidad de Presenatcion :");
        lblUnidadPresentacion.setBounds(new Rectangle(5, 50, 150, 15));
        lblUnidadPresentacion.setForeground(new Color(255, 130, 14));
        lblUnidadPresentacionT.setBounds(new Rectangle(150, 50, 295, 15));
        lblUnidadPresentacionT.setForeground(new Color(255, 130, 14));
        lblF1.setText("[ F1 ]  Consulta Local");
        lblF1.setBounds(new Rectangle(230, 385, 145, 20));
        pnlWhite5.setBounds(new Rectangle(10, 115, 315, 20));
        pnlWhite5.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        lblStockLimaT.setBounds(new Rectangle(20, 0, 50, 20));
        lblStockLimaT.setForeground(Color.black);
        lblStockLimaT.setHorizontalAlignment(SwingConstants.CENTER);
        lblStockAlmacenT.setBounds(new Rectangle(240, 0, 55, 20));
        lblStockAlmacenT.setForeground(Color.black);
        lblStockAlmacenT.setHorizontalAlignment(SwingConstants.CENTER);
        pnlTitle5.setBounds(new Rectangle(10, 95, 315, 20));
        lblStockAlmacen.setText("Stock Almacen");
        lblStockAlmacen.setBounds(new Rectangle(215, 5, 100, 15));
        lblStockAlmacen.setHorizontalAlignment(SwingConstants.CENTER);
        lblStockLima.setText("Stock Lima");
        lblStockLima.setBounds(new Rectangle(15, 5, 85, 15));
        lblStockProvincias.setText("Stock Provincias");
        lblStockProvincias.setBounds(new Rectangle(105, 5, 100, 15));
        lblStockProvinciaT.setBounds(new Rectangle(130, 0, 50, 20));
        lblStockProvinciaT.setForeground(Color.black);
        lblStockProvinciaT.setHorizontalAlignment(SwingConstants.CENTER);
        pnlWhite5.add(lblStockProvinciaT, null);
        pnlWhite5.add(lblStockLimaT, null);
        pnlWhite5.add(lblStockAlmacenT, null);
        pnlTitle5.add(lblStockAlmacen, null);
        pnlTitle5.add(lblStockLima, null);
        pnlTitle5.add(lblStockProvincias, null);
        pnlRelacion1.add(jScrollPane3, new XYConstraints(0, 25, 620, 120));
        pnlRelacion1.add(btnDemasLocales, new XYConstraints(10, 5, 165, 15));
        pnlDatosCliente.add(lblUnidadPresentacionT, null);
        pnlDatosCliente.add(lblUnidadPresentacion, null);
        pnlDatosCliente.add(lblProducto, null);
        pnlDatosCliente.add(lblLaboratorioT, null);
        pnlDatosCliente.add(lblProductoT, null);
        pnlDatosCliente.add(lblLaboratorio, null);
        jPanelWhite1.add(pnlTitle5, null);
        jPanelWhite1.add(pnlWhite5, null);
        jPanelWhite1.add(lblF1, null);
        jPanelWhite1.add(lblEsc, null);
        srcDemasLocales.getViewport().add(tblStockDemasLocales, null);
        jPanelWhite1.add(srcDemasLocales, null);
        jPanelWhite1.add(pnlRelacion1, null);
        srcStockLocalesPreferidos.getViewport().add(tblStockLocalesPreferidos, null);
        jPanelWhite1.add(srcStockLocalesPreferidos, null);
        jPanelWhite1.add(pnlDatosCliente, null);
        jPanelWhite1.add(pnlRelacion, null);
        pnlRelacion.add(jScrollPane1, new XYConstraints(0, 25, 620, 120));
        pnlRelacion.add(btnStockLocales, new XYConstraints(10, 5, 165, 15));
        jPanelWhite1.add(jPanelTitle1, null);
        this.getContentPane().add(jPanelWhite1, null);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        setJTable(tblStockLocalesPreferidos);
        if (obtieneIndVerStockLocales()) {
            if (obtieneIndLineaActual().trim().equalsIgnoreCase("S")) //Verificara si aun hay linea
                actualizaIndLinea();
            else // No hay linea
                FarmaUtility.showMessage(this,
                                         "No se encontro linea con Matriz.\n Si desea pongase en contacto con el Operador de Sistemas",
                                         tblStockLocalesPreferidos);
            ////////////////////////////////////////DUBILLUZ

            if (UtilityInventario.obtieneIndLinea(this)) {
                initTableStockLocalesPreferidos();
                obtieneInfoProdLocalesAlmacen(VariablesVentas.vCod_Prod);
            }
        } else {
            FarmaUtility.showMessage(this,
                                     "Esta funcionalidad no esta permitida en su local. \n Pongase en contacto con el Operador de Sistemas",
                                     tblStockLocalesPreferidos);
        }
    } //initTableStockDemasLocales();

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTableStockLocalesPreferidos() {
        tableModelStockLocalesPreferidos =
                new FarmaTableModel(ConstantsVentas.columnsListaStockLocalesPreferidos, ConstantsVentas.defaultValuesListaStockLocalesPreferidos,
                                    0);
        FarmaUtility.initSimpleList(tblStockLocalesPreferidos, tableModelStockLocalesPreferidos,
                                    ConstantsVentas.columnsListaStockLocalesPreferidos);
        cargaListaStockLocalesPreferidos();
    }

    /*private void initTableStockDemasLocales()
  {
    tableModelStockDemasLocales = new FarmaTableModel(ConstantsVentas.columnsListaStockDemasLocales, ConstantsVentas.defaultValuesListaStockDemasLocales, 0);
    FarmaUtility.initSimpleList(tblStockDemasLocales, tableModelStockDemasLocales, ConstantsVentas.columnsListaStockDemasLocales);
    cargaListaStockDemasLocales();
  }*/

    private void cargaListaStockLocalesPreferidos() {
        String codProd = VariablesVentas.vCod_Prod;
        try {
            //Aqui se verifica si se consultara el IndVerStockLocales
            //05.11.2007  dubilluz  modificacion
            if (VariablesPtoVenta.vRevisarIndStockLocales.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                log.debug("Fue invocado en el Kardex.");
                DBVentas.cargaListaStockLocalesPreferidos(tableModelStockLocalesPreferidos, codProd,
                                                          FarmaConstants.INDICADOR_S);
            } else {
                log.debug("Fue invocado en la lista de Productos.");
                DBVentas.cargaListaStockLocalesPreferidos(tableModelStockLocalesPreferidos, codProd,
                                                          FarmaConstants.INDICADOR_S);
                //DBVentas.cargaListaStockLocalesPreferidos(tableModelStockLocalesPreferidos,
                //                                          VariablesVentas.vCod_Prod,
                //                                          FarmaConstants.INDICADOR_S);
            }
            FarmaUtility.ordenar(tblStockLocalesPreferidos, tableModelStockLocalesPreferidos, 0,
                                 FarmaConstants.ORDEN_ASCENDENTE);
            FarmaConnectionRemoto.closeConnection();
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrio un error al listar el stock de los locales preferidos \n" +
                    sql.getMessage(), tblStockLocalesPreferidos);
        }
    }

    /*private void cargaListaStockDemasLocales()
  {
    try
    {
      DBVentas.cargaListaStockDemasLocales(tableModelStockDemasLocales,VariablesInventario.vCodProd);
      FarmaUtility.ordenar(tblStockDemasLocales,tableModelStockDemasLocales,0,FarmaConstants.ORDEN_ASCENDENTE);
    } catch(SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this,"Ocurrio un error al listar el stock de los demas locales \n"+ sql.getMessage(),tblStockLocalesPreferidos);
    }
  }*/

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void btnStockLocales_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocusJTable(tblStockLocalesPreferidos);
        setJTable(tblStockLocalesPreferidos);
    }

    /*private void btnDemasLocales_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocusJTable(tblStockDemasLocales);
    setJTable(tblStockDemasLocales);
  }*/

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocusJTable(tblStockLocalesPreferidos);
        muestraDatosCabecera();
        setJTable(tblStockLocalesPreferidos);
    }

    private void tblStockLocalesPreferidos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    /*private void tblStockDemasLocales_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }*/

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {
            obtieneInfoProd();
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void muestraDatosCabecera() {
        lblProducto.setText(VariablesVentas.vCod_Prod + " - " + VariablesVentas.vDesc_Prod);
        lblLaboratorio.setText(VariablesVentas.vNom_Lab);
        lblUnidadPresentacionT.setText(VariablesVentas.vUnid_Vta);
    }
    /*
  private void muestraInfoProd()
  {
    ArrayList myArray = new ArrayList();
    obtieneInfoProdEnArrayList(myArray);
    if(myArray.size() == 1)
    {
      stkProd = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
      descUnidVta = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
      log.debug(stkProd);
      log.debug(descUnidVta);
    } else
    {
      stkProd = "";
      descUnidVta = "";
      FarmaUtility.showMessage(this, "Error al obtener Informacion del Producto", myJTable);
    }
    myJTable.setValueAt(stkProd, myJTable.getSelectedRow(),2);
    myJTable.setValueAt(descUnidVta, myJTable.getSelectedRow(),3);
    myJTable.repaint();
  }
  */

    private String obtieneInfoProd() {
        String codProd = VariablesVentas.vCod_Prod;
        String cadena = "";
        String stockProd = "";
        String unidadVenta = "";
        VariablesVentas.vCodLocalDestino = ((String)myJTable.getValueAt(myJTable.getSelectedRow(), 0)).trim();
        log.debug("VariablesVentas.vCodLocalDestino : " + VariablesVentas.vCodLocalDestino);
        try {
            cadena = DBVentas.obtieneInfoStock(codProd, FarmaConstants.INDICADOR_S);
            stockProd = cadena.substring(0, cadena.indexOf("Ã")).trim();
            unidadVenta = cadena.substring(cadena.indexOf("Ã") + 1).trim();

            myJTable.setValueAt(stockProd, myJTable.getSelectedRow(), 2);
            myJTable.setValueAt(unidadVenta, myJTable.getSelectedRow(), 3);
            myJTable.repaint();

            log.debug(cadena);
            log.debug(stockProd);
            log.debug(unidadVenta);

            FarmaConnectionRemoto.closeConnection();
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener informacion del producto en Arreglo. \n" +
                    sql.getMessage(), myJTable);
            return null;
        } catch (Exception e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "No se encontro coneccion con el local", myJTable);
            return null;
        }
        return cadena;
    }

    private void obtieneInfoProdLocalesAlmacen(String pCodProd) {
        String cadena = "";
        //String indLocales = "" ;
        String indLima = "";
        String indProvincia = "";
        String indAlmacen = "";
        try {
            cadena = DBVentas.obtieneIndicadorStock(pCodProd, FarmaConstants.INDICADOR_S);
            String[] listaIndicadorStocks = cadena.split("Ã");

            indLima = listaIndicadorStocks[POS_INDICADOR_LIMA];
            indProvincia = listaIndicadorStocks[POS_INDICADOR_PROVINCIA];
            indAlmacen = listaIndicadorStocks[POS_INDICADOR_ALAMACEN];
            /*indLocales = cadena.substring(0,cadena.indexOf("Ã")).trim();
      indAlmacen = cadena.substring(cadena.indexOf("Ã")+1).trim();*/

            lblStockLimaT.setText(indLima);
            lblStockProvinciaT.setText(indProvincia);
            lblStockAlmacenT.setText(indAlmacen);

            log.debug(cadena);
            /*log.debug(indLima);
      log.debug(indAlmacen);*/

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener informacion del producto en Arreglo. \n" +
                    sql.getMessage(), tblStockLocalesPreferidos);
            //lblStockLocales.setText("-");
            //lblStockAlmacen.setText("-");

        } catch (Exception e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "No se encontro coneccion con el local", tblStockLocalesPreferidos);
        }
    }


    private void setJTable(JTable pJTable) {
        myJTable = pJTable;
        if (pJTable.getRowCount() > 0) {
            FarmaGridUtils.showCell(pJTable, 0, 0);
        }
    }

    /**Nuevo
     * @Autor: Paulo Cesar Ameghino Rojas
     * @Fecha:  11/06/2007
     * */

    /*public void actualizaIndLinea()
   {
    try
    {
      DBInventario.actualizaIndLinea();
      FarmaUtility.aceptarTransaccion();
    } catch(SQLException sql)
    {
      FarmaUtility.liberarTransaccion();
      log.error("",sql);
      FarmaUtility.showMessage(this,"Ocurrió un error al actualizar el estado en linea : \n" + sql.getMessage(),null);
    }
    cerrarVentana(true);
   }*/

    public boolean obtieneIndVerStockLocales() {
        try {
            //Aqui se verifica si se consultara el IndVerStockLocales
            //05.11.2007  dubilluz  modificacion
            if (VariablesPtoVenta.vRevisarIndStockLocales.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                log.debug("Fue invocado en el Kardex , no revisara el indicador de ver Stock locales");
                return true;
            } else {
                log.debug("Revisa el indicador de ver Stock locales");
                String indVerLocales = DBVentas.obtieneIndVerStockLocales();
                if (indVerLocales.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                    return true;
                } else
                    return false;
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener el indicador de ver stock de locales." + sql.getMessage(),
                                     tblStockLocalesPreferidos);
            return false;
        }
    }

    /**
     * Actualiza el Indicador en linea en el local
     * @author dubilluz
     * @since  24.09.2007
     */
    public void actualizaIndLinea() {
        int t_estimado = Integer.parseInt(time_estimado_consulta().trim()) * 1000;
        int t_actual = Integer.parseInt(time_diferencia_actual().trim());
        log.debug("t_actual 1 : " + t_actual);
        t_actual = Integer.parseInt(time_diferencia_actual().trim());
        log.debug("t_actual 2 : " + t_actual);
        log.debug("Valor Estimado : " + t_estimado);
        log.debug("Valor Obtenido : " + t_actual);
        try {
            if (t_actual > t_estimado) {
                DBInventario.actualizaIndLinea(FarmaConstants.INDICADOR_N, t_actual - t_estimado);
                FarmaUtility.aceptarTransaccion();
                log.debug("Tiempo de conexion es lenta.Duracion es de " + t_actual + " milisegundos.");
                FarmaUtility.showMessage(this,
                                         "No se encontro linea con Matriz.\n Si desea pongase en contacto con el Operador de Sistemas",
                                         tblStockLocalesPreferidos);
            }
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al actualizar el estado en linea : \n" +
                    sql.getMessage(), null);
        }

    }

    /**
     * Obtiene el Tiempo que demoro la consulta actual
     * @author dubilluz
     * @since  12.09.2007
     */
    public String time_diferencia_actual() {
        Date fecha1 = new Date();
        long milisegundos1 = fecha1.getTime();
        log.debug("Consulta a delivery :" + consulta_delivey());
        Date fecha2 = new Date();
        long milisegundos2 = fecha2.getTime();
        return "" + (milisegundos2 - milisegundos1);
    }

    /**
     * Tiempo estimado de consulta a Delivery
     * @author dubilluz
     * @since  24.09.2007
     */
    public String time_estimado_consulta() {
        String time = "";
        try {
            time = DBInventario.getTimeEstimado();
        } catch (SQLException sql) {
            log.error("", sql);
        }

        return time.trim();

    }

    /**
     * Realiza la Consulta a Delivery
     * @author dubilluz
     * @since  24.09.2007
     */
    public String consulta_delivey() {
        log.debug("Consultando a Delivery");
        String prueba = "";
        try {
            prueba = DBInventario.consulta_Delivery("Select sysdate from dual", FarmaConstants.INDICADOR_S);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al realizar consulta de linea." + sql.getMessage(),
                                     tblStockLocalesPreferidos);
            return "-1";
        }
        return prueba;
    }

    /**
     * Obtiene Ind Linea Actual
     * @author dubilluz
     * @since  24.09.2007
     */
    public String obtieneIndLineaActual() {
        log.debug("Obtiene el Ind Linea Acual");
        String vInd = "";
        try {
            vInd = DBInventario.getIndLinea();
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al obtener indicador en linea." + sql.getMessage(),
                                     tblStockLocalesPreferidos);
        }
        log.debug("Indicado Linea :" + vInd);
        return vInd;
    }


}
