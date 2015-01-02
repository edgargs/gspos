package mifarma.ptoventa.matriz.mantenimientos.productos;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaJTable;
import mifarma.common.FarmaRowEditorModel;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.matriz.mantenimientos.productos.references.ConstantsProducto;
import mifarma.ptoventa.matriz.mantenimientos.productos.references.DBProducto;
import mifarma.ptoventa.matriz.mantenimientos.productos.references.VariablesProducto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2007 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaProductosDist.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCORTEZ    12.11.2007   Creación<br>
 * <br>
 * @author Jorge Luis Cortez Alvarez<br>
 * @version 1.0<br>
 *
 */
public class DlgListaProductosDist extends JDialog {
    /* ************************************************************************ */
    /*                          DECLARACION PROPIEDADES                         */
    /* ************************************************************************ */
    private static final Logger log = LoggerFactory.getLogger(DlgListaProductosDist.class);

    private JFrame myParentFrame;
    private FarmaTableModel tableModelListaProductos;
    private FarmaRowEditorModel rowEditorModel;
    private FarmaJTable tblListaProductos = new FarmaJTable();
    private JTextFieldSanSerif txtCantidad;
    private DefaultCellEditor defaultCellEditor;

    private ArrayList arrayDescripcion = new ArrayList();
    private final int DIG_COD = 3;
    private final int COL_COD = 0;
    private final int COL_DESC = 2;
    private final int COL_UNIPRES = 3;
    private final int COL_DESCLAB = 4;
    private final int COL_LIBRE = 5;
    private final int COL_BLOQUEADO = 6;

    private String FILTRO_COL = "1";

    private String cCodBusq = "";

    /**
     * iNDICADOR DE ESTADO DE GRABADO
     * @author dubilluz
     * @since  17.09.2007
     */

    private final String EST_DEFINITIVO = "D";
    private final String EST_TEMPORAL = "T";

    double vCantProds = 0;

    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
    private JButton btnBuscar = new JButton();
    private JButtonLabel btnLineaQS = new JButtonLabel();
    private JTextFieldSanSerif txtDescLaboratorio = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtCodLab = new JTextFieldSanSerif();
    private JPanelTitle pnlTitulo = new JPanelTitle();
    private JButtonLabel btnListado = new JButtonLabel();
    private JScrollPane srcListadoProductos = new JScrollPane();
    //private JTable tblListaProductos = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF4 = new JLabelFunction();
    private JTextFieldSanSerif txtCodProducto = new JTextFieldSanSerif();
    private JButtonLabel btnProducto = new JButtonLabel();
    private JLabelFunction lblF3 = new JLabelFunction();
    private JLabelWhite lblRedistribuido = new JLabelWhite();
    private JLabelWhite lblRedistribuidoT = new JLabelWhite();
    private JLabelFunction lblF2 = new JLabelFunction();
    private JLabelFunction lblF1 = new JLabelFunction();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JLabelWhite lblCantProd = new JLabelWhite();
    private JLabelWhite lblCantLocales_ = new JLabelWhite();
    private JLabelFunction lblF5 = new JLabelFunction();
    private JLabel lblTipoFiltro = new JLabel();

    //Agregado por DVELIZ 18.09.08
    private JLabelFunction lblEnter = new JLabelFunction();
    /* ************************************************************************ */
    /*                          CONSTRUCTORES                                   */
    /* ************************************************************************ */

    public DlgListaProductosDist() {
        this(null, "", false);
    }

    public DlgListaProductosDist(JFrame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
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
        //  this.setSize(new Dimension(798, 524));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Nuevo Pedido de Distribución");
        this.setDefaultCloseOperation(0);
        this.setBounds(new Rectangle(10, 10, 798, 589));
        this.setSize(new Dimension(980, 495));
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });

        tblListaProductos.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                tblListaProductos_keyReleased(e);
            }

            public void keyPressed(KeyEvent e) {
                tblListaProductos_keyPressed(e);
            }
        });
        pnlCriterioBusqueda.setBounds(new Rectangle(5, 5, 965, 35));
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(355, 5, 80, 20));
        btnBuscar.setMnemonic('B');
        btnBuscar.setFont(new Font("SansSerif", 1, 11));
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        btnLineaQS.setText("Laboratorio : ");
        btnLineaQS.setBounds(new Rectangle(5, 5, 75, 20));
        btnLineaQS.setMnemonic('L');
        btnLineaQS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLineaQS_actionPerformed(e);
            }
        });
        txtDescLaboratorio.setBounds(new Rectangle(145, 5, 205, 20));
        txtDescLaboratorio.setEditable(false);
        txtDescLaboratorio.setHorizontalAlignment(JTextField.LEFT);
        txtCodLab.setBounds(new Rectangle(80, 5, 60, 20));
        txtCodLab.setLengthText(3);
        txtCodLab.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCodLab_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtCodLab_keyTyped(e);
            }
        });
        pnlTitulo.setBounds(new Rectangle(5, 45, 965, 20));
        btnListado.setText("Lista de Productos");
        btnListado.setBounds(new Rectangle(5, 0, 110, 20));
        srcListadoProductos.setBounds(new Rectangle(5, 65, 965, 360));

        lblEsc.setBounds(new Rectangle(755, 435, 95, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblF4.setBounds(new Rectangle(545, 435, 100, 20));
        lblF4.setText("[F4] Ver Todos");
        txtCodProducto.setBounds(new Rectangle(505, 5, 250, 20));
        txtCodProducto.setLengthText(100);
        txtCodProducto.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtProducto_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtProducto_keyReleased(e);
            }
        });
        btnProducto.setText("Producto : ");
        btnProducto.setBounds(new Rectangle(440, 5, 60, 20));
        btnProducto.setMnemonic('p');
        btnProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnProducto_actionPerformed(e);
            }
        });

        lblF3.setBounds(new Rectangle(455, 435, 80, 20));
        lblF3.setText("[ F3] Ver OC");
        lblRedistribuido.setBounds(new Rectangle(545, 0, 230, 20));
        lblRedistribuido.setBackground(new Color(43, 141, 39));
        lblRedistribuido.setOpaque(true);
        lblRedistribuidoT.setText("Producto Redistribuido");
        lblRedistribuidoT.setBounds(new Rectangle(50, 0, 145, 20));
        lblRedistribuidoT.setOpaque(true);
        lblRedistribuidoT.setBackground(new Color(43, 141, 39));
        lblF2.setBounds(new Rectangle(355, 435, 90, 20));
        lblF2.setText("[F2] Filtrar");
        lblF1.setBounds(new Rectangle(215, 435, 130, 20));
        lblF1.setText("[F1] Generar Pedido");
        lblCantProd.setBounds(new Rectangle(750, 0, 55, 20));
        lblCantLocales_.setBounds(new Rectangle(615, 0, 135, 20));
        lblCantLocales_.setText("Cantidad de Productos:");
        lblF5.setBounds(new Rectangle(655, 435, 90, 20));
        lblF5.setText("[ F5 ] Ordenar");
        //Agregado por DVELIZ 18.09.08
        lblEnter.setBounds(new Rectangle(25, 435, 180, 20));
        lblEnter.setText("[ Enter ] Configurar PVM");

        lblTipoFiltro.setBounds(new Rectangle(450, 0, 145, 20));
        lblTipoFiltro.setFont(new Font("SansSerif", 1, 11));
        lblTipoFiltro.setBackground(new Color(44, 146, 24));
        lblTipoFiltro.setOpaque(true);
        lblTipoFiltro.setHorizontalAlignment(SwingConstants.CENTER);
        lblTipoFiltro.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        lblTipoFiltro.setForeground(Color.white);
        lblTipoFiltro.setText("Productos Cadena");
        pnlCriterioBusqueda.add(btnProducto, null);
        pnlCriterioBusqueda.add(txtCodProducto, null);
        pnlCriterioBusqueda.add(txtCodLab, null);
        pnlCriterioBusqueda.add(txtDescLaboratorio, null);
        pnlCriterioBusqueda.add(btnBuscar, null);
        pnlCriterioBusqueda.add(btnLineaQS, null);
        //Agregado por DVELIZ 18.09.08
        jContentPane.add(lblF5, null);

        jContentPane.add(lblF1, null);
        jContentPane.add(lblF2, null);
        jContentPane.add(lblF3, null);
        jContentPane.add(lblF4, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblEnter, null);
        srcListadoProductos.getViewport().add(tblListaProductos, null);
        jContentPane.add(srcListadoProductos, null);
        jContentPane.add(pnlTitulo, null);
        jContentPane.add(pnlCriterioBusqueda, null);
        lblRedistribuido.add(lblRedistribuidoT, null);
        pnlTitulo.add(lblTipoFiltro, null);
        pnlTitulo.add(lblCantLocales_, null);
        pnlTitulo.add(lblCantProd, null);
        pnlTitulo.add(lblRedistribuido, null);
        pnlTitulo.add(btnListado, null);
        lblRedistribuido.setVisible(false);
        lblRedistribuidoT.setVisible(false);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {

        initTableListaProductos();

    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTableListaProductos() {

        tableModelListaProductos =
                new FarmaTableModel(ConstantsProducto.columnsListaProductos, ConstantsProducto.defaultValuesListaProductos,
                                    0);
        FarmaUtility.initSimpleList(tblListaProductos, tableModelListaProductos,
                                    ConstantsProducto.columnsListaProductos);

        listarProductos(ConstantsProducto.vFiltroListaProducto);
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        VariablesProducto.vTipoListado = "";
        VariablesProducto.vTipoValidarFiltro = "";
        lblTipoFiltro.setVisible(false);
        lblF1.setVisible(false);
        FarmaUtility.centrarVentana(this);
        FarmaUtility.setearPrimerRegistro(tblListaProductos, txtCodProducto, 2);
        FarmaUtility.moveFocus(txtCodProducto);

    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void btnLineaQS_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCodLab);
    }

    private void txtCodLineaQS_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

        } else
            chkKeyPressed(e);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        seleccionarLaboratorio();
    }

    private void txtProducto_keyPressed(KeyEvent e) {

        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaProductos, txtCodProducto, 2);

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            log.debug("ANTES DE LEVANTAR FILTRO :" + VariablesProducto.vIndNuevo);
            if (ExistTemporalAceptado()) {
                FarmaUtility.showMessage(this, "Existen pedidos guardados sin confirmar. Verifique!!!",
                                         txtCodProducto);
            } else {
                e.consume();
                if (tblListaProductos.getSelectedRow() > -1) {
                    log.debug("QUE EXISTA FILAS");
                    if (!(FarmaUtility.findTextInJTable(tblListaProductos, txtCodProducto.getText().trim(), 0, 2))) {
                        FarmaUtility.showMessage(this, "Producto No Encontrado según Criterio de Búsqueda !!!",
                                                 txtCodProducto);
                        return;
                    } else {
                        log.debug("SELECCIONA PRODUCTO");
                        /**JCORTEZ
            * 17/01/2007
            * **/
                        //if(VariablesProducto.vTipoListado.equalsIgnoreCase(ConstantsProducto.listDist)){
                        seleccionaProducto();
                        // }
                    }
                }

            }
        }
        chkKeyPressed(e);
    }


    private void txtCodLab_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCodLab, e);
    }

    private void txtProducto_keyReleased(KeyEvent e) {
        if (tblListaProductos.getRowCount() >= 0) {
            FarmaGridUtils.buscarDescripcion(e, tblListaProductos, txtCodProducto, 2);
        }
    }

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {
            /* if(!VariablesProducto.vTipoParametros){//JCORTEZ 11.09.2008
      if(!ExistTemporalPendiente()){ //si no hay pendientes en aux_det
          if(com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,"Esta seguro de distribuir los productos trabajados?")){
          agregarProductosTemporal();
          generarPedidoDistribucion();
          }
        }else{
          FarmaUtility.showMessage(this,"No existen productos para generar el pedido",txtCodProducto);
        }
      }else{
       FarmaUtility.showMessage(this,"funcion no habilitada",txtCodProducto);
      }*/
        }
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {
            // if(VariablesProducto.vTipoListado.equalsIgnoreCase(ConstantsProducto.listDist)){
            filtrar();
            //}
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            verOC();
        } else if (e.getKeyCode() == KeyEvent.VK_F4) {
            VariablesProducto.vTipoValidarFiltro = "";
            VariablesProducto.cCodLab = "";
            VariablesProducto.vIndNuevo = "";
            VariablesProducto.vTipoListado = ConstantsProducto.listDist;
            lblTipoFiltro.setVisible(false);
            listarProductos(ConstantsProducto.vFiltroListaProducto);
            txtCodLab.setText("");
            txtDescLaboratorio.setText("");
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            ordenar();
        } else if (e.getKeyCode() == KeyEvent.VK_F6) {
            //listamos los productos en almacen
            /* lblTipoFiltro.setVisible(true);
      VariablesProducto.vTipoListado=ConstantsProducto.listAlmac;
      listarProductosAlmacen(ConstantsProducto.vFiltroListaProducto);*/
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "¿Desea salir del modulo?")) {
                cerrarVentana(true);
            }

        }

    }

    private void txtCodLab_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            btnBuscar.doClick();
        }
        chkKeyPressed(e);
    }

    private void btnProducto_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCodProducto);
    }

    private void tblListaProductos_keyReleased(KeyEvent e) {
        //lblDescLab.setText(tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(),COL_DESCLAB).toString());
    }

    private void tblListaProductos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    /**
     * Listado de Productos para el pedido
     */
    private void listarProductos(String filtro) {
        try {
            DBProducto.cargaListaProductosDist(tableModelListaProductos, filtro, VariablesProducto.vIndNuevo,
                                               FILTRO_COL);
            lblCantProd.setText("" + tblListaProductos.getRowCount());
            if (tblListaProductos.getRowCount() > 0) {
                FarmaUtility.ordenar(tblListaProductos, tableModelListaProductos, COL_COD,
                                     FarmaConstants.ORDEN_ASCENDENTE);

            }

        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ha ocurrido un error al listar los productos.\n" +
                    e.getMessage(), txtCodLab);
        }
    }

    private void habilitaObjetos(boolean pAccion) {
        txtCodLab.setEditable(pAccion);
        btnBuscar.setEnabled(pAccion);
    }

    /**
     * Se realiza la busqueda del laboratorio para filtrar los productos
     * */
    private void seleccionarLaboratorio() {

        if (txtCodLab.getText().trim().length() > 0) {

            //filtro
            limpiarFiltros();
            cCodBusq = ConstantsProducto.LABORATORIO;

            txtCodLab.setText(FarmaUtility.completeWithSymbol(txtCodLab.getText(), 3, "0", "I"));
            if (txtCodLab.getText().trim().length() == DIG_COD) {
                log.debug("BUSCAR LAB");
                obtieneDescripcion(txtCodLab.getText().trim());
                log.debug("OBTENIDO");
                if (arrayDescripcion.size() > 0) {
                    log.debug("DESCRIPCION DEL LABORATORIO :" + arrayDescripcion);
                    txtCodLab.setText(((String)((ArrayList)arrayDescripcion.get(0)).get(COL_COD)).trim());
                    txtDescLaboratorio.setText(((String)((ArrayList)arrayDescripcion.get(0)).get(1)).trim());
                    VariablesProducto.cCodLab = txtCodLab.getText().trim();
                    log.debug("ENVIANDO FILTRO LAB :" + VariablesProducto.cCodLab);
                    /**
              *
              **/
                    //listarProductos(txtCodLab.getText().trim());
                    if (VariablesProducto.vTipoListado.equalsIgnoreCase(ConstantsProducto.listDist)) {
                        listarProductosAlmacen(txtCodLab.getText().trim());
                    } else {
                        listarProductos(txtCodLab.getText().trim());
                    }
                    /*if(tblListaProductos.getRowCount()>0){
               FarmaUtility.showMessage(this,"Productos agregados al pedido",txtCodLab);
              }else{
              FarmaUtility.showMessage(this,"No existen productos",txtCodLab);
              }*/
                    log.debug("COD LABORATORIO FILTRO:" + txtCodLab.getText().trim());
                    lblF4.setVisible(true);
                    FarmaUtility.moveFocus(txtCodProducto);
                } else {
                    listarProductos(txtCodLab.getText().trim());
                    FarmaUtility.showMessage(this, "No se obtuvo resultados de la busqueda", txtCodLab);
                    txtCodLab.setText("000");
                    txtCodProducto.setText("");
                    FarmaUtility.moveFocus(txtCodLab);
                    txtDescLaboratorio.setText("");
                }
            }
        } else {

            VariablesProducto.cFiltroListado = ConstantsProducto.LABORATORIO;
            DlgListado dlgListado = new DlgListado(myParentFrame, "", true);
            dlgListado.setVisible(true);

            if (FarmaVariables.vAceptar) {
                txtCodLab.setText(VariablesProducto.cCodLab);
                txtDescLaboratorio.setText(VariablesProducto.cDesLab);
                FarmaUtility.moveFocus(txtCodProducto);
                /**
             *
            **/
                // listarProductos(VariablesProducto.cCodLab);
                if (VariablesProducto.vTipoListado.equalsIgnoreCase(ConstantsProducto.listDist)) {
                    listarProductosAlmacen(txtCodLab.getText().trim());
                } else {
                    listarProductos(txtCodLab.getText().trim());
                }

                if (tblListaProductos.getRowCount() > 0) {
                    FarmaUtility.showMessage(this, "Productos agregados al pedido", txtCodLab);
                } else {
                    FarmaUtility.showMessage(this, "No existen productos", txtCodLab);
                    txtCodProducto.setText("");
                }
                lblF4.setVisible(true);
            }
        }

    }

    /**
     * se limpia las variables de laboratorio
     * */
    private void limpiarFiltros() {

        VariablesProducto.cCodLab = "";
        VariablesProducto.cDesLab = "";
    }

    /**
     * Se obtiene la descripcion del elemento ingresado
     * */
    private void obtieneDescripcion(String vCodigo) {
        try {
            arrayDescripcion.clear();
            //ingreso de filtro
            if (cCodBusq.equalsIgnoreCase(ConstantsProducto.LABORATORIO)) {
                DBProducto.obtieneDescripcionLaboratorio(arrayDescripcion, vCodigo);
            } else {
                //FarmaUtility.showMessage(this,"",txtCodigoDestino);
            }

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrio un error al buscar elemento.\n" +
                    sql.getMessage(), txtCodLab);
        }
    }

    /**
     * Se distribuye el producto seleccionado en temporal
     * */
    private void seleccionaProducto() {

        int row = tblListaProductos.getSelectedRow();
        if (row > -1) {
            cargarDatos(row);
            //se genera el numero de pedido temporal
            if (ExistTemporalPendiente()) {
                log.debug("NO EXISTE PEDIDO TEMPORAL");
                generarNumTemporal();
            }

            log.debug("ANTES DE LEVANTAR FILTRO :" + VariablesProducto.vIndNuevo);
            DlgProductosDist dlgproductos = new DlgProductosDist(myParentFrame, "", true);
            dlgproductos.setVisible(true);

            if (FarmaVariables.vAceptar) { //limiamos y volvemos a cargar
                log.debug("VariablesProducto.cCodLab " + VariablesProducto.cCodLab);

                if (!VariablesProducto.cCodLab.equalsIgnoreCase("")) {
                    log.debug("1");
                    listarProductos(VariablesProducto.cCodLab);
                } else {
                    log.debug("2");
                    if (VariablesProducto.vIndNuevo.equalsIgnoreCase(ConstantsProducto.listAlmac)) {
                        listarProductosAlmacen(ConstantsProducto.vFiltroListaProducto);
                        lblTipoFiltro.setVisible(true);
                        VariablesProducto.vTipoValidarFiltro = ConstantsProducto.listAlmac;
                    } else {
                        listarProductos(ConstantsProducto.vFiltroListaProducto);
                        lblTipoFiltro.setVisible(false);
                        VariablesProducto.vTipoValidarFiltro = "";
                    }
                    VariablesProducto.cCodLab = "";

                    /* listarProductos(ConstantsProducto.vFiltroListaProducto);
        VariablesProducto.cCodLab="";
          if(tblListaProductos.getRowCount() > 0)
          {
            FarmaUtility.ordenar(tblListaProductos,tableModelListaProductos,COL_COD,FarmaConstants.ORDEN_ASCENDENTE);
          }*/
                }

                FarmaUtility.ordenar(tblListaProductos, tableModelListaProductos,
                                     Integer.parseInt(VariablesProducto.vColumna), VariablesProducto.vOrden);
                tblListaProductos.repaint();
                //FarmaUtility.setearPrimerRegistro(tblListaProductos,txtCodProducto,2);

                if (VariablesProducto.vOrden.equalsIgnoreCase(""))
                    VariablesProducto.vOrden = "desc";

                if (VariablesProducto.vColumna.equalsIgnoreCase(""))
                    VariablesProducto.vColumna = "5";

                log.debug("Columna Orden -->" + VariablesProducto.vOrden);
                log.debug("Columna columna -->" + VariablesProducto.vColumna);
                log.debug("Producto -->" + VariablesProducto.cCodProd);

                /*FarmaUtility.ordenar(tblListaProductos,tableModelListaProductos,
                             Integer.parseInt(VariablesProducto.vColumna),
                             VariablesProducto.vOrden);*/
                if (tblListaProductos.getRowCount() > 0) {
                    tblListaProductos.repaint();
                    FarmaUtility.findTextInJTable(tblListaProductos, VariablesProducto.cCodProd.trim(), 0, 0);
                }
                FarmaUtility.moveFocus(txtCodProducto);
                //FarmaUtility.setearPrimerRegistro(tblListaProductos,txtCodProducto,2);

            }

        }

    }

    /**
     * Se genera el numero para el pedido temporal
     * */
    private void generarNumTemporal() {

        try {
            VariablesProducto.vNumPedidoDistGenerado_aux =
                    FarmaSearch.getNuSecNumeracion(ConstantsProducto.AUX_SECNUMERA, 10);
            log.debug("Numero de pedido Temporal Generado :" + VariablesProducto.vNumPedidoDistGenerado_aux);
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this,
                                     "Error: no se puede grabar la distribución temporal en los locales." + sql.getMessage(),
                                     null);
            log.error("", sql);
        }

    }

    /**
     * Se carga el detalle del producto
     **/
    private void cargarDatos(int row) {

        VariablesProducto.cCodProd = FarmaUtility.getValueFieldJTable(tblListaProductos, row, COL_COD);
        VariablesProducto.cDescProd = FarmaUtility.getValueFieldJTable(tblListaProductos, row, COL_DESC);
        VariablesProducto.cUnidPres = FarmaUtility.getValueFieldJTable(tblListaProductos, row, COL_UNIPRES);
        //String stock=(String)tblListaProductos.getValueAt(row, COL_LIBRE);
        String stock_libre = (String)tblListaProductos.getValueAt(row, COL_LIBRE);
        String stock_bloqueado = (String)tblListaProductos.getValueAt(row, COL_BLOQUEADO);
        String stock = "";
        //stock=(""+(int)(FarmaUtility.getDecimalNumber(stock.trim())));
        int stock_int =
            ((int)(FarmaUtility.getDecimalNumber(stock_libre.trim())) + (int)(FarmaUtility.getDecimalNumber(stock_bloqueado.trim())));
        stock = ("" + stock_int);
        VariablesProducto.vCantStock = Integer.parseInt("" + stock);
        VariablesProducto.cDesLab = FarmaUtility.getValueFieldJTable(tblListaProductos, row, COL_DESCLAB);

    }

    /**
     *Se muestra las ordenes de compra del producto
     **/
    private void verOC() {
        if (tblListaProductos.getRowCount() >= 0) {
            int row = tblListaProductos.getSelectedRow();
            if (row > -1) {
                cargarDatos(row);
                DlgOCProdDist dlgoc = new DlgOCProdDist(myParentFrame, "", true);
                dlgoc.setVisible(true);
            }
        }
    }

    /**
     * Se genera el pedido temporal
     * */
    private void generarPedidoTemporal() {

        try {

            //se genera el pedido temporal guardar
            log.debug("Numero de pedido Temporal :" + VariablesProducto.vNumPedidoDistGenerado_aux);
            DBProducto.insertarCabeceraPedidoAux(VariablesProducto.vNumPedidoDistGenerado_aux);

            //se actualiza el secuancial del pedido temporal
            FarmaSearch.updateNumera(ConstantsProducto.AUX_SECNUMERA);
            FarmaUtility.aceptarTransaccion();

        } catch (SQLException sql) {
            if (sql.getErrorCode() == 20001) {
                FarmaUtility.showMessage(this, "Existe un pedido sin confirmar. Verifique!!!", tblListaProductos);
            } else {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this,
                                         "Error: no se puede grabar la distribución temporal en los locales." + sql.getMessage(),
                                         null);
                FarmaUtility.moveFocusJTable(tblListaProductos);
                log.error("", sql);
            }
        }
    }

    /**
     * Se genera el pedido de distribucion real
     * */
    private void generarPedidoDistribucion() {

        try {

            VariablesProducto.vNumPedidoDistGenerado = FarmaSearch.getNuSecNumeracion(ConstantsProducto.SECNUMERA, 10);
            //se genera el pedido temporal guardar
            log.debug("Numero de pedido generado :" + VariablesProducto.vNumPedidoDistGenerado);
            DBProducto.insertarPedidoDistribucion(VariablesProducto.vNumPedidoDistGenerado);

            FarmaSearch.updateNumera(ConstantsProducto.SECNUMERA);
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this, "Se guardo el pedido de distribucion exitosamente.", null);
            cerrarVentana(true);
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this, "Error: no se puede generar el pedido de distribución" + sql.getMessage(),
                                     null);
            FarmaUtility.moveFocusJTable(tblListaProductos);
            log.error("", sql);
        }

    }

    /**
     * Se agrega el producto al pedido temporal
     * */
    private void agregarProductosTemporal() {

        try {

            String revisado = DBProducto.getExistPedTemporalPend();
            log.debug("RETORNO :" + revisado);
            if (revisado.equalsIgnoreCase("TRUE")) {
                generarPedidoTemporal();
                FarmaUtility.aceptarTransaccion();
            }
            cerrarVentana(true);
        } catch (SQLException sql) {
            if (sql.getErrorCode() == 20001) {
                FarmaUtility.showMessage(this, "ya existe el pedido. Debe confirmarlo", tblListaProductos);
            } else {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this,
                                         "Error: no se puede generar el pedido de distribución" + sql.getMessage(),
                                         null);
                FarmaUtility.moveFocusJTable(tblListaProductos);
                log.error("", sql);
            }
        }

    }

    /**
     * Valida la existencia de productos en el pedido temporal
     * */
    private boolean ExistTemporalAceptado() {

        boolean existe = false;
        try {
            String revisado = DBProducto.getExistPedTemporalAcep();
            log.debug("RETORNO :" + revisado);
            if (revisado.equalsIgnoreCase("TRUE")) {
                existe = true;
            }
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ha ocurrido un al verificar la existencia de pedidos temporales",
                                     txtCodProducto);
        }
        return existe;
    }

    /**
     * Valida la existencia de produtos en el pedido temporal pendiente
     * */
    private boolean ExistTemporalPendiente() {

        boolean existe = false;
        try {
            String revisado = DBProducto.getExistPedTemporalPend();
            log.debug("RETORNO :" + revisado);
            if (revisado.equalsIgnoreCase("FALSE")) {
                //no hay pendientes
                existe = true;
            }
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ha ocurrido un al verificar la existencia de pedidos temporales",
                                     txtCodProducto);
        }
        return existe;
    }


    /**
     * Se lista los productos por tipo Filtro (trabajados,nuevos, no nuevos)
     * */
    private void filtrar() {

        DlgFiltro dlgfiltro = new DlgFiltro(myParentFrame, "", true);
        dlgfiltro.setVisible(true);

        if (FarmaVariables.vAceptar) {
            if (!VariablesProducto.cCodLab.equalsIgnoreCase("")) {
                log.debug("lab");
                log.debug("tipo de filtro :" + VariablesProducto.vIndNuevo);
                //listarProductos(VariablesProducto.cCodLab);

                if (VariablesProducto.vIndNuevo.equalsIgnoreCase(ConstantsProducto.listAlmac)) {
                    listarProductosAlmacen(VariablesProducto.cCodLab);
                    lblTipoFiltro.setVisible(true);
                    VariablesProducto.vTipoValidarFiltro = ConstantsProducto.listAlmac;
                } else {
                    listarProductos(VariablesProducto.cCodLab);
                    lblTipoFiltro.setVisible(false);
                    VariablesProducto.vTipoValidarFiltro = "";
                }

                VariablesProducto.vIndNuevo = "";
                FarmaUtility.setearPrimerRegistro(tblListaProductos, txtCodProducto, 2);
            } else {
                log.debug("todoss");
                VariablesProducto.cCodLab = "";
                log.debug("tipo de filtro :" + VariablesProducto.vIndNuevo);
                //listarProductos(ConstantsProducto.vFiltroListaProducto);

                if (VariablesProducto.vIndNuevo.equalsIgnoreCase(ConstantsProducto.listAlmac)) {
                    listarProductosAlmacen(ConstantsProducto.vFiltroListaProducto);
                    lblTipoFiltro.setVisible(true);
                    VariablesProducto.vTipoValidarFiltro = ConstantsProducto.listAlmac;
                } else {
                    listarProductos(ConstantsProducto.vFiltroListaProducto);
                    lblTipoFiltro.setVisible(false);
                    VariablesProducto.vTipoValidarFiltro = "";
                }
                FarmaUtility.setearPrimerRegistro(tblListaProductos, txtCodProducto, 2);
            }

        }
    }

    /**
     * Se ordena el listado de productos
     * */
    private void ordenar() {

        VariablesProducto.Tipo_Orden = ConstantsProducto.PRODUCTO;
        DlgOrdenar dlgordenar = new DlgOrdenar(myParentFrame, "", true);
        dlgordenar.setVisible(true);

        if (FarmaVariables.vAceptar) {
            FarmaUtility.ordenar(tblListaProductos, tableModelListaProductos,
                                 Integer.parseInt(VariablesProducto.vColumna), VariablesProducto.vOrden);
            tblListaProductos.repaint();
            FarmaUtility.setearPrimerRegistro(tblListaProductos, txtCodProducto, 2);
        }

    }

    /**
     * Se lista los productos de la cadena que tengan stock en almacen
     * @author: JCORTEZ
     * @since : 16/01/2007
     * */
    private void listarProductosAlmacen(String filtro) {

        try {
            tableModelListaProductos.clearTable();
            DBProducto.cargaListaProductosAlmc(tableModelListaProductos, filtro);
            lblCantProd.setText("" + tblListaProductos.getRowCount());
            if (tblListaProductos.getRowCount() > 0) {
                FarmaUtility.ordenar(tblListaProductos, tableModelListaProductos, COL_COD,
                                     FarmaConstants.ORDEN_ASCENDENTE);
            }

        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ha ocurrido un error al listar los productos.\n" +
                    e.getMessage(), txtCodLab);
        }

    }


}

