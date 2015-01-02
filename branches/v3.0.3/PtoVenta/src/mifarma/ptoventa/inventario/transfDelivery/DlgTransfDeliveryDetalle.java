package mifarma.ptoventa.inventario.transfDelivery;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

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

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.DlgListaImpresorasInv;
import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.inventario.transfDelivery.reference.ConstantsTranfDelivery;
import mifarma.ptoventa.inventario.transfDelivery.reference.DBTranfDelivery;
import mifarma.ptoventa.inventario.transfDelivery.reference.VariablesTranfDelivery;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgTransfDeliveryDetalle.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCALLO      20.10.2008   Creación<br>
 * <br>
 * @author JCALLO<br>
 * @version 1.0<br>
 *
 */
public class DlgTransfDeliveryDetalle extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgTransfDeliveryDetalle.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;
    String vEstadoNota;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnllHeader1 = new JPanelHeader();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JPanelTitle pnlTitlefooter = new JPanelTitle();
    private JScrollPane scrListaProductos = new JScrollPane();
    private JTable tblListaProductos = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelWhite lblDestino = new JLabelWhite();
    private JLabelWhite lblFechaPedido = new JLabelWhite();
    private JButtonLabel btnRelacionProductos = new JButtonLabel();

    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblENTER = new JLabelFunction();

    private JLabelWhite lblFechaPedido_T = new JLabelWhite();
    private JLabelWhite lblNumPedidoTransf_T = new JLabelWhite();
    private JLabelWhite lblNumPedidoTransf = new JLabelWhite();
    private JLabelWhite lblDestino_T = new JLabelWhite();

    private JLabelWhite lblStock_T = new JLabelWhite();
    private JLabelWhite lblStock = new JLabelWhite();

    private JLabelWhite lblUnidVenta_T = new JLabelWhite();
    private JLabelWhite lblUnidVenta = new JLabelWhite();

    private JButtonLabel lblCantProductos = new JButtonLabel();
    private JButtonLabel lblCantProductosT = new JButtonLabel();

    private JLabel lblError = new JLabel();
    private ArrayList listaSecResp; //ASOSA, 19.07.2010
    ArrayList tableModel_02; //ASOSA, 19.07.2010

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgTransfDeliveryDetalle() {
        this(null, "", false);
    }

    public DlgTransfDeliveryDetalle(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
            FarmaUtility.centrarVentana(this);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(742, 425));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Detalle Pedido Transferencia Delivery");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnllHeader1.setBounds(new Rectangle(10, 10, 715, 40));
        pnlTitle1.setBounds(new Rectangle(10, 75, 715, 25));
        pnlTitlefooter.setBounds(new Rectangle(235, 320, 490, 25));
        scrListaProductos.setBounds(new Rectangle(10, 100, 715, 220));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(625, 350, 100, 20));
        lblDestino.setText("Destino : ");
        lblDestino.setBounds(new Rectangle(470, 10, 60, 15));
        lblFechaPedido.setText("Fec. Pedido : ");
        lblFechaPedido.setBounds(new Rectangle(225, 10, 75, 15));
        btnRelacionProductos.setText("Relacion de Productos a Transferir");
        btnRelacionProductos.setBounds(new Rectangle(5, 5, 205, 15));
        btnRelacionProductos.setMnemonic('R');
        btnRelacionProductos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnRelacionProductos_keyPressed(e);
            }
        });
        btnRelacionProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionProductos_actionPerformed(e);
            }
        });
        lblF11.setText("[ F11 ] Generar Transferencia");
        lblF11.setBounds(new Rectangle(420, 350, 180, 20));
        lblENTER.setText("[ ENTER ] Ingresar Lote y Fec Vcto");
        lblENTER.setBounds(new Rectangle(180, 350, 210, 20));
        lblFechaPedido_T.setBounds(new Rectangle(300, 10, 100, 15));
        lblNumPedidoTransf_T.setBounds(new Rectangle(90, 10, 90, 15));
        lblNumPedidoTransf.setText("No. Pedido :");
        lblNumPedidoTransf.setBounds(new Rectangle(10, 10, 70, 15));
        lblDestino_T.setBounds(new Rectangle(530, 10, 145, 15));
        lblCantProductos.setText("Cantidad de Productos");
        lblCantProductos.setBounds(new Rectangle(475, 5, 130, 15));
        lblCantProductosT.setBounds(new Rectangle(615, 5, 45, 15));

        lblError.setText("");
        lblError.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblError.setForeground(Color.RED);
        lblError.setBounds(new Rectangle(10, 55, 705, 20));

        lblStock.setText("Stock actual en Local :");
        lblStock.setBounds(new Rectangle(10, 5, 130, 15));
        //lblStock.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblStock_T.setBounds(new Rectangle(140, 5, 70, 15));
        lblStock_T.setFont(new Font("SansSerif", Font.BOLD, 13));

        lblUnidVenta.setText("Unid. Vta Local:");
        lblUnidVenta.setBounds(new Rectangle(245, 5, 95, 15));
        lblUnidVenta_T.setBounds(new Rectangle(335, 5, 115, 15));
        lblUnidVenta_T.setFont(new Font("SansSerif", Font.BOLD, 13));

        jContentPane.add(lblENTER, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        scrListaProductos.getViewport().add(tblListaProductos, null);
        jContentPane.add(scrListaProductos, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(pnlTitlefooter, null);
        jContentPane.add(pnllHeader1, null);


        jContentPane.add(lblError, null);
        pnlTitle1.add(lblCantProductosT, null);
        pnlTitle1.add(lblCantProductos, null);
        pnlTitle1.add(btnRelacionProductos, null);
        pnlTitlefooter.add(lblStock, null);
        pnlTitlefooter.add(lblUnidVenta, null);
        pnlTitlefooter.add(lblUnidVenta_T, null);
        pnlTitlefooter.add(lblStock_T, null);
        pnllHeader1.add(lblDestino_T, null);
        pnllHeader1.add(lblNumPedidoTransf, null);
        pnllHeader1.add(lblNumPedidoTransf_T, null);
        pnllHeader1.add(lblFechaPedido_T, null);
        pnllHeader1.add(lblFechaPedido, null);
        pnllHeader1.add(lblDestino, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initTable();
        cargarDetalle();
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsTranfDelivery.columnsListaProductosTransfDetalle, ConstantsTranfDelivery.columnsListaProductosTransfDetalle,
                                    0);
        FarmaUtility.initSimpleList(tblListaProductos, tableModel,
                                    ConstantsTranfDelivery.columnsListaProductosTransfDetalle);
    }

    private void cargaListaProductos() {
        try {
            DBTranfDelivery.cargaDetalleTransfDelivery(tableModel, VariablesTranfDelivery.vCodLocalDel,
                                                       VariablesTranfDelivery.vNumPedidoTransf,
                                                       VariablesTranfDelivery.vSecGrupo,
                                                       VariablesTranfDelivery.vCodLocalDestino,
                                                       VariablesTranfDelivery.vSecTrans);


            VariablesTranfDelivery.vFlagErrorValFrac = false;
            VariablesTranfDelivery.vFlagErrorStock = false;
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                tableModel.setValueAt("", i, 5);
                tableModel.setValueAt("", i, 6);

                if (tableModel.getValueAt(i, 12).toString().equalsIgnoreCase("N")) {
                    VariablesTranfDelivery.vFlagErrorValFrac = true;
                    VariablesTranfDelivery.vErrorValFracProducto =
                            tableModel.getValueAt(i, 0).toString() + " - " + tableModel.getValueAt(i, 1).toString();
                    VariablesTranfDelivery.vMsgErrorValFracProducto =
                            tableModel.getValueAt(i, 0).toString() + " - " + tableModel.getValueAt(i, 1).toString() +
                            " " + "\nValFrac local :" + tableModel.getValueAt(i, 10).toString() +
                            "\nValFrac Delivery :" + tableModel.getValueAt(i, 11).toString();
                }

                if (tableModel.getValueAt(i, 15).toString().equalsIgnoreCase("N")) {
                    VariablesTranfDelivery.vFlagErrorStock = true;
                    /*VariablesTranfDelivery.vErrorStockProducto = tableModel.getValueAt(i,0).toString()+" - "+
					tableModel.getValueAt(i,1).toString();
					VariablesTranfDelivery.vMsgErrorStockProducto = tableModel.getValueAt(i,0).toString()+" - "+
					tableModel.getValueAt(i,1).toString()+" "+
					"\nValFrac local :"+tableModel.getValueAt(i,10).toString()+"\nValFrac Delivery :"+tableModel.getValueAt(i,11).toString();*/
                }
            }


        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar la lista de productos : \n" +
                    sql.getMessage(), btnRelacionProductos);
        }
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void btnRelacionProductos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(btnRelacionProductos);
    }

    private void btnRelacionProductos_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaProductos, null, 0);

        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN ||
            e.getKeyCode() == KeyEvent.VK_PAGE_UP || e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            //e.consume();
            int row = tblListaProductos.getSelectedRow();
            if (row >= 0) {
                lblStock_T.setText(tableModel.getValueAt(row, 7).toString().trim());
                lblUnidVenta_T.setText(tableModel.getValueAt(row, 8).toString().trim());
            }


        }
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        listaSecResp = new ArrayList(); //ASOSA, 19.07.2010
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(btnRelacionProductos);
        lblCantProductosT.setText("" + tblListaProductos.getRowCount());
        if (tblListaProductos.getRowCount() > 0) {
            tblListaProductos.setRowSelectionInterval(0, 0);
            lblStock_T.setText(tableModel.getValueAt(0, 7).toString().trim());
            lblUnidVenta_T.setText(tableModel.getValueAt(0, 8).toString().trim());
        }

        //ver si tiene error en al menos un producto del valFrac de local con delivery
        if (VariablesTranfDelivery.vFlagErrorValFrac) {
            lblF11.setVisible(false);
            lblENTER.setVisible(false);
            lblError.setText("ERROR : valor fraccion de local diferente a delivery en el producto " +
                             VariablesTranfDelivery.vErrorValFracProducto);
            FarmaUtility.showMessage(this, "ERROR !\nvalor fraccion de local diferente a delivery en el producto.\n" +
                    VariablesTranfDelivery.vMsgErrorValFracProducto, btnRelacionProductos);

        } else if (VariablesTranfDelivery.vFlagErrorStock) { //sino tiene errores
            lblF11.setVisible(false);
            lblENTER.setVisible(false);
            lblError.setText("ERROR : stock no disponible de algun(os) producto(s) del pedido de transferencia !");
            FarmaUtility.showMessage(this,
                                     "ERROR !\nstock no disponible de algun(os) producto(s) del pedido de transferencia !",
                                     btnRelacionProductos);
        } else {
            //comprometeer stock de los productos a procesar
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                separaStockProducto(i);
            }
        }

    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        //FarmaGridUtils.aceptarTeclaPresionada(e,tblListaProductos,null,0);
        if (e.getKeyCode() == KeyEvent.VK_ENTER && !VariablesTranfDelivery.vFlagErrorValFrac &&
            !VariablesTranfDelivery.vFlagErrorStock) {
            int row = tblListaProductos.getSelectedRow();
            if (row < 0) {
                FarmaUtility.showMessage(this, "Seleccione el Producto!", null);
            } else {
                //VariablesTranfDelivery.vFlagDialogAbierto

                /* JCALLO 05.11.2008*/
                /*DlgIngresoDetalleProd DlgIngresoDetalleProd = new DlgIngresoDetalleProd(myParentFrame,"",true);
              DlgIngresoDetalleProd.setVisible(true);*/
                /* --- */

                VariablesTranfDelivery.vCodProducto = tableModel.getValueAt(row, 0).toString().trim();
                VariablesTranfDelivery.vDescProducto = tableModel.getValueAt(row, 1).toString().trim();
                VariablesTranfDelivery.vStockProducto = tableModel.getValueAt(row, 7).toString().trim();
                VariablesTranfDelivery.vUnidadVentaProducto = tableModel.getValueAt(row, 8).toString().trim();
                VariablesTranfDelivery.vFecStock = tableModel.getValueAt(row, 9).toString().trim();
                VariablesTranfDelivery.vLabProducto = tableModel.getValueAt(row, 3).toString().trim();
                VariablesTranfDelivery.vValFracProducto = tableModel.getValueAt(row, 10).toString().trim();
                VariablesTranfDelivery.vCantProductoTransf = tableModel.getValueAt(row, 4).toString().trim();

                VariablesTranfDelivery.vLoteProdTransf = tableModel.getValueAt(row, 5).toString().trim();
                VariablesTranfDelivery.vFechaVctoProd = tableModel.getValueAt(row, 6).toString().trim();
                //VariablesTranfDelivery.vValFracProdTransf = tableModel.getValueAt(row,11).toString().trim();
                FarmaVariables.vAceptar = false;
                DlgIngresoLoteFecVcto dlgIngresoLoteFecVcto = new DlgIngresoLoteFecVcto(myParentFrame, "", true);
                dlgIngresoLoteFecVcto.setVisible(true);

                if (FarmaVariables.vAceptar) {
                    tableModel.setValueAt(VariablesTranfDelivery.vLoteProdTransf, row, 5);
                    tableModel.setValueAt(VariablesTranfDelivery.vFechaVctoProd, row, 6);
                    tblListaProductos.repaint();
                }

            }

        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e) &&
                   !VariablesTranfDelivery.vFlagErrorValFrac && !VariablesTranfDelivery.vFlagErrorStock) {


            if (validarDatos()) {
                FarmaVariables.vAceptar = false;
                DlgTransfDeliveryTransporte dlgTransfDeliveryTransporte =
                    new DlgTransfDeliveryTransporte(myParentFrame, "", true);
                dlgTransfDeliveryTransporte.setVisible(true);
                if (FarmaVariables.vAceptar) {
                    //tblListaProductos.removeAll();

                    FarmaVariables.vAceptar = false;
                    DlgListaImpresorasInv dlgListaImpresorasInv =
                        new DlgListaImpresorasInv(this.myParentFrame, "", true);
                    dlgListaImpresorasInv.setVisible(true);

                    if (!FarmaVariables.vAceptar) {
                        return;
                    } else {
                        FarmaVariables.vAceptar = false;
                        //DlgGenerarTransfDelivery dlgGenerarTransfDelivery = new DlgGenerarTransfDelivery(myParentFrame,"",true,tblListaProductos);
                        fusionarListas(); //INI ASOSA, 31.08.2010
                        DlgGenerarTransfDelivery dlgGenerarTransfDelivery =
                            new DlgGenerarTransfDelivery(myParentFrame, "", true, tblListaProductos,
                                                         tableModel_02); //FIN ASOSA, 31.08.2010
                        dlgGenerarTransfDelivery.setVisible(true);
                        if (FarmaVariables.vAceptar) {
                            cerrarVentana(true);
                        }
                    }


                }
            } else {
                FarmaUtility.showMessage(this, "Debe Ingresar el Lote y la Fecha de Vencimiento\n" +
                        "de cada producto de la transferencia a realizar !", btnRelacionProductos);
            }

        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            //recuperando stock de todos los productos
            if (!VariablesTranfDelivery.vFlagErrorValFrac && !VariablesTranfDelivery.vFlagErrorStock) {
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    //recuperaStock(i); antes
                    //recuperaStock(i); //ASOSA, 20.07.2010
                    fusionarListas(); ////ASOSA, 31.08.2010
                    recuperaStock_02(i); //ASOSA, 31.08.2010
                }
            }

            cerrarVentana(false);
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

    private void cargarDetalle() {

        lblNumPedidoTransf_T.setText(VariablesTranfDelivery.vNumPedidoTransf);
        lblFechaPedido_T.setText(VariablesTranfDelivery.vFecPedidoTransf);
        lblDestino_T.setText(VariablesTranfDelivery.vLocalDestino);

        cargaListaProductos();

    }

    private boolean anular() {
        boolean retorno;
        try {
            DBInventario.anularTransferencia(VariablesInventario.vNumNota);
            FarmaUtility.aceptarTransaccion();
            retorno = true;
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            if (sql.getErrorCode() == 20002)
                FarmaUtility.showMessage(this, "La Fracción Actual no permite anular esta Guia.\n" +
                        sql, btnRelacionProductos);
            else {
                log.error("", sql);
                FarmaUtility.showMessage(this, "Ha ocurrido un error al anular la transferencia : \n" +
                        sql.getMessage(), btnRelacionProductos);
            }
            retorno = false;
        }

        return retorno;
    }

    private boolean validarDatos() {
        boolean retorno = true;

        //if(tableModel.getRowCount()<1) retorno false;

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 5).toString().trim().length() < 1 ||
                tableModel.getValueAt(i, 6).toString().trim().length() < 1) {
                retorno = false;
            }
        }

        return retorno;
    }

    private boolean validaGuias() {
        boolean retorno;
        if (!vEstadoNota.equals(FarmaConstants.INDICADOR_N)) {
            try {
                ArrayList secGuia = DBInventario.getSecuencialGuia(VariablesInventario.vNumNota, "");
                if (secGuia.size() == 0) {
                    retorno = false;
                    FarmaUtility.showMessage(this, "No existe guías pendientes de impresión.", btnRelacionProductos);
                } else {
                    retorno = true;
                }
            } catch (SQLException e) {
                retorno = false;
                log.error("", e);
                FarmaUtility.showMessage(this, "No se ha podido determinar las guías pendientes.\n" +
                        e, btnRelacionProductos);
            }
        } else {
            retorno = false;
            FarmaUtility.showMessage(this, "Esta transferencia está anulada.", btnRelacionProductos);
        }
        return retorno;
    }

    /*private boolean reimprimir()
  {
    boolean retorno = false;
    if(cargaCabecera())
      try
      {
        //FarmaUtility.liberarTransaccion();
        //Imprimir Comprobantes
        VariablesInventario.vNumNotaEs = VariablesInventario.vNumNota;
        UtilityInventario.procesoImpresionComprobante(this, tblListaProductos);
        retorno = true;
      }catch(SQLException sql)
      {
        FarmaUtility.liberarTransaccion();
        log.error("",sql);
        FarmaUtility.showMessage(this,"Ha ocurrido un error al grabar la transferencia.\n"+sql.getMessage(),btnRelacionProductos);
        retorno = false;
      }catch(Exception exc)
      {
        FarmaUtility.liberarTransaccion();
	log.error("",exc);
        FarmaUtility.showMessage(this, "Error en la aplicacion al grabar la transferencia.\n"+exc.getMessage(),btnRelacionProductos);
        retorno = false;
      }
    return retorno;
  }*/

    /*private boolean cargaCabecera()
  {
    boolean retorno;
    try
    {
      ArrayList cabecera = DBInventario.getCabeceraGuiaTrans(VariablesInventario.vNumNota);
      if(cabecera.size() > 0)
      {
        cabecera = (ArrayList)cabecera.get(0);

        VariablesInventario.vDestino_Transf = cabecera.get(0).toString();
        VariablesInventario.vRucDestino_Transf = cabecera.get(1).toString();
        VariablesInventario.vDirecDestino_Transf = cabecera.get(2).toString();
        VariablesInventario.vTransportista_Transf = cabecera.get(3).toString();
        VariablesInventario.vRucTransportista_Transf = cabecera.get(4).toString();
        VariablesInventario.vCodDestino_Transf = cabecera.get(5).toString();

        retorno = true;
      }else
      {
        retorno = false;
        FarmaUtility.showMessage(this,"No se cargó los datos de cabecera de la guía.\n",btnRelacionProductos);
      }
    }catch(SQLException e)
    {
      log.error("",e);
      retorno = false;
      FarmaUtility.showMessage(this,"Error al obtener los datos de cabecera de la guía.\n"+e,btnRelacionProductos);
    }

    return retorno;
  }*/

    /* private boolean cargaLoginOper()
  {
    String numsec = FarmaVariables.vNuSecUsu ;
    String idusu = FarmaVariables.vIdUsu ;
    String nomusu = FarmaVariables.vNomUsu ;
    String apepatusu = FarmaVariables.vPatUsu ;
    String apematusu = FarmaVariables.vMatUsu ;

    try{
      DlgLogin dlgLogin = new DlgLogin(myParentFrame,ConstantsPtoVenta.MENSAJE_LOGIN,true);
      dlgLogin.setRolUsuario(FarmaConstants.ROL_OPERADOR_SISTEMAS);
      dlgLogin.setVisible(true);
      FarmaVariables.vNuSecUsu  = numsec ;
      FarmaVariables.vIdUsu  = idusu ;
      FarmaVariables.vNomUsu  = nomusu ;
      FarmaVariables.vPatUsu  = apepatusu ;
      FarmaVariables.vMatUsu  = apematusu ;
    } catch (Exception e)
    {
      FarmaVariables.vNuSecUsu  = numsec ;
      FarmaVariables.vIdUsu  = idusu ;
      FarmaVariables.vNomUsu  = nomusu ;
      FarmaVariables.vPatUsu  = apepatusu ;
      FarmaVariables.vMatUsu  = apematusu ;
      FarmaVariables.vAceptar = false;
      log.error("",e);
      FarmaUtility.showMessage(this,"Ocurrio un error al validar rol de usuariario \n : " + e.getMessage(),null);
    }
    return FarmaVariables.vAceptar;
  }*/


    private boolean recuperaStock(int row) {
        boolean valor = true;
        try {
            actualizaStkComprometidoProd(tableModel.getValueAt(row, 0).toString().trim(),
                                         Integer.parseInt(tableModel.getValueAt(row, 4).toString().trim()),
                                         ConstantsInventario.INDICADOR_D,
                                         ConstantsInventario.TIP_OPERACION_RESPALDO_BORRAR,
                                         Integer.parseInt(tableModel.getValueAt(row, 4).toString().trim()),
                                         tableModel.getValueAt(row, 10).toString().trim());
        } catch (Exception e) {
            log.info("ERROR : " + e);
            valor = false;
        }
        return valor;
    }

    private boolean separaStockProducto(int row) {
        boolean valor = true;
        try {
            actualizaStkComprometidoProd_02(tableModel.getValueAt(row, 0).toString().trim(),
                                            Integer.parseInt(tableModel.getValueAt(row, 4).toString().trim()),
                                            ConstantsInventario.INDICADOR_A,
                                            ConstantsInventario.TIP_OPERACION_RESPALDO_SUMAR,
                                            Integer.parseInt(tableModel.getValueAt(row, 4).toString().trim()),
                                            tableModel.getValueAt(row, 10).toString().trim(), ""); //ASOSA, 19.07.2010
            listaSecResp.add(VariablesInventario.secRespStk); //ASOSA, 19.07.2010
        } catch (Exception e) {
            log.info("ERROR : " + e);
            valor = false;
        }
        return valor;
    }

    private void actualizaStkComprometidoProd(String pCodigoProducto, int pCantidadStk, String pTipoStkComprometido,
                                              String pTipoRespaldoStock, int pCantidadRespaldo, String pValFracProd) {
        try {
            //log.info("123456");
            DBInventario.actualizaStkComprometidoProd(pCodigoProducto, pCantidadStk, pTipoStkComprometido);
            //log.info("23456");
            DBPtoVenta.ejecutaRespaldoStock(pCodigoProducto, "", pTipoRespaldoStock, pCantidadRespaldo,
                                            Integer.parseInt(pValFracProd), ConstantsPtoVenta.MODULO_TRANSFERENCIA);
            //log.info("3456");
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this,
                                     "Error al Actualizar Stock del Producto.\n Ponganse en contacto con el area de Sistemas\n" +
                    sql.getMessage(), btnRelacionProductos);
        }
    }


    //-----------------------=- ASOSA, 19.07.2001 -=------------------------//

    private void actualizaStkComprometidoProd_02(String pCodigoProducto, int pCantidadStk, String pTipoStkComprometido,
                                                 String pTipoRespaldoStock, int pCantidadRespaldo, String pValFracProd,
                                                 String secRespaldo) {
        VariablesInventario.secRespStk = "0";
        /*
            try
            {
                            VariablesInventario.secRespStk=""; //ASOSA, 26.08.2010
                    VariablesInventario.secRespStk = DBVentas.operarResStkAntesDeCobrar(pCodigoProducto,
                                                                            String.valueOf(pCantidadStk),
                                                                            //VariablesInventario.vValFrac_Transf,
                                                                            pValFracProd,   //ASOSA, 31.08.2010
                                                                            secRespaldo,
                                                                            ConstantsPtoVenta.MODULO_TRANSFERENCIA);
                    FarmaUtility.aceptarTransaccion();
            }catch (SQLException sql)
            {
                    FarmaUtility.liberarTransaccion();
                    log.error("",sql);
                    FarmaUtility.showMessage(this, "Error al Actualizar Stock del Producto.\n Ponganse en contacto con el area de Sistemas\n" + sql.getMessage() ,btnRelacionProductos);
            }
        */
    }

    /**
     * Adiciona los secuenciales a la lista mostrada para que luego se pueda descomprometer
     * @author ASOSA
     * @since 19.07.2010
     */
    private void fusionarListas() {
        /*        new FarmaColumnData( "Codigo", 50, JLabel.LEFT ),
        new FarmaColumnData( "Descripcion", 200, JLabel.LEFT ),
        new FarmaColumnData( "Unidad", 90, JLabel.LEFT ),
        new FarmaColumnData( "Laboratorio", 150, JLabel.LEFT ),
        new FarmaColumnData( "Cantidad", 60, JLabel.RIGHT ),
        new FarmaColumnData( "Lote", 80, JLabel.RIGHT ),
        new FarmaColumnData( "Fec. Vcto", 70, JLabel.RIGHT )*/
        tableModel_02 = new ArrayList();
        for (int c = 0; c < tableModel.getRowCount(); c++) {
            ArrayList lista01 = new ArrayList();
            String cod = FarmaUtility.getValueFieldArrayList(tableModel.data, c, 0).trim();
            String desc = FarmaUtility.getValueFieldArrayList(tableModel.data, c, 1).trim();
            String unid = FarmaUtility.getValueFieldArrayList(tableModel.data, c, 2).trim();
            String lab = FarmaUtility.getValueFieldArrayList(tableModel.data, c, 3).trim();
            String cant = FarmaUtility.getValueFieldArrayList(tableModel.data, c, 4).trim();
            String lote = FarmaUtility.getValueFieldArrayList(tableModel.data, c, 5).trim();
            String FecVcto = FarmaUtility.getValueFieldArrayList(tableModel.data, c, 6).trim();
            String campo7 = FarmaUtility.getValueFieldArrayList(tableModel.data, c, 7).trim();
            String campo8 = FarmaUtility.getValueFieldArrayList(tableModel.data, c, 8).trim();
            String campo9 = FarmaUtility.getValueFieldArrayList(tableModel.data, c, 9).trim();
            String campo10 = FarmaUtility.getValueFieldArrayList(tableModel.data, c, 10).trim();
            String campo11 = FarmaUtility.getValueFieldArrayList(tableModel.data, c, 11).trim();
            String campo12 = FarmaUtility.getValueFieldArrayList(tableModel.data, c, 12).trim();
            String campo13 = FarmaUtility.getValueFieldArrayList(tableModel.data, c, 13).trim();
            String campo14 = FarmaUtility.getValueFieldArrayList(tableModel.data, c, 14).trim();
            String campo15 = FarmaUtility.getValueFieldArrayList(tableModel.data, c, 15).trim();
            String secResp = listaSecResp.get(c).toString();
            lista01.add(cod);
            lista01.add(desc);
            lista01.add(unid);
            lista01.add(lab);
            lista01.add(cant);
            lista01.add(lote);
            lista01.add(FecVcto);
            lista01.add(campo7);
            lista01.add(campo8);
            lista01.add(campo9);
            lista01.add(campo10);
            lista01.add(campo11);
            lista01.add(campo12);
            lista01.add(campo13);
            lista01.add(campo14);
            lista01.add(campo15);
            lista01.add(secResp);
            tableModel_02.add(lista01);
        }
    }

    private boolean recuperaStock_02(int row) {
        boolean valor = true;
        try {
            String cod = FarmaUtility.getValueFieldArrayList(tableModel_02, row, 0).trim();
            String secResp = FarmaUtility.getValueFieldArrayList(tableModel_02, row, 16).trim();
            String campo10 = FarmaUtility.getValueFieldArrayList(tableModel_02, row, 10).trim();
            actualizaStkComprometidoProd_02(cod, 0, ConstantsInventario.INDICADOR_D,
                                            ConstantsInventario.TIP_OPERACION_RESPALDO_BORRAR, 0, campo10, secResp);
        } catch (Exception e) {
            log.info("ERROR : " + e);
            valor = false;
        }
        return valor;
    }

}
