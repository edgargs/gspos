package mifarma.ptoventa.recepcionCiega;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
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

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.UtilityInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.recepcionCiega.reference.ConstantsRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.DBRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.VariablesRecepCiega;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;
import static mifarma.ptoventa.reference.UtilityPtoVenta.limpiaCadenaAlfanumerica;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgConteoProdTransferencia extends JDialog {

    Frame myParentFrame;
    private static final Logger log = LoggerFactory.getLogger(DlgConteoProdTransferencia.class);

    FarmaTableModel tableModelProductos;
    // private ArrayList myArray = new ArrayList();
    private String titulo = "";
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout2 = new BorderLayout();
    private JButtonLabel btnBuscar = new JButtonLabel();
    private JTextFieldSanSerif txtProducto = new JTextFieldSanSerif();
    private JPanelTitle pnlTitle2 = new JPanelTitle();
    private JButtonLabel btnListaProductos = new JButtonLabel();
    private JScrollPane srcListaProductos = new JScrollPane();
    private JTable tblListaProductos = new JTable();
    private JPanelHeader pnlHeader1 = new JPanelHeader();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction lblF2 = new JLabelFunction();


    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgConteoProdTransferencia() {
        this(null, "", false);
    }

    public DlgConteoProdTransferencia(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        titulo = title;
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
        this.setSize(new Dimension(641, 423));
        this.setTitle(titulo);
        this.getContentPane().setLayout(borderLayout2);
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        btnBuscar.setText("Ingresar Código de Barra ó Producto:");
        btnBuscar.setMnemonic('i');
        btnBuscar.setBounds(new Rectangle(10, 15, 215, 20));
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        txtProducto.setBounds(new Rectangle(235, 15, 240, 20));
        txtProducto.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtProducto_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtProducto_keyReleased(e);
            }

            public void keyTyped(KeyEvent e) {
                txtProducto_keyTyped(e);
            }
        });
        txtProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtProducto_actionPerformed(e);
            }
        });
        txtProducto.setLengthText(14);
        pnlTitle2.setBounds(new Rectangle(5, 60, 625, 30));
        btnListaProductos.setText("Relación de Productos");
        btnListaProductos.setMnemonic('R');
        btnListaProductos.setBounds(new Rectangle(10, 5, 180, 20));
        btnListaProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListaProductos_actionPerformed(e);
            }
        });
        srcListaProductos.setBounds(new Rectangle(5, 90, 625, 260));
        tblListaProductos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaProductos_keyPressed(e);
            }
        });
        pnlHeader1.setBounds(new Rectangle(5, 10, 625, 45));
        lblEsc.setBounds(new Rectangle(515, 365, 115, 20));
        lblEsc.setText("[ Esc ] Salir ");
        lblF11.setBounds(new Rectangle(390, 365, 117, 20));
        lblF11.setText("[ F11 ] Finalizar");
        lblF1.setBounds(new Rectangle(235, 365, 145, 20));
        lblF1.setText("[ F1 ] Buscar Producto");
        lblF2.setBounds(new Rectangle(80, 365, 145, 20));
        lblF2.setText("[ F2 ] Eliminar Producto");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlHeader1.add(btnBuscar, null);
        pnlHeader1.add(txtProducto, null);
        jContentPane.add(lblF2, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(pnlHeader1, null);
        srcListaProductos.getViewport().add(tblListaProductos, null);
        jContentPane.add(srcListaProductos, null);
        pnlTitle2.add(btnListaProductos, null);
        jContentPane.add(pnlTitle2, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTable();
        limpiaVariables();
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTable() {
        this.tableModelProductos =
                new FarmaTableModel(ConstantsRecepCiega.columnsListaProductosTransferencia, ConstantsRecepCiega.defaultcolumnsListaProductosTransferencia,
                                    0);
        FarmaUtility.initSimpleList(this.tblListaProductos, tableModelProductos,
                                    ConstantsRecepCiega.columnsListaProductosTransferencia);

        if (tblListaProductos.getSelectedRow() > -1)
            FarmaUtility.setearPrimerRegistro(tblListaProductos, txtProducto, 2);
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(txtProducto);
        FarmaUtility.centrarVentana(this);

    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(this.txtProducto);
    }

    private void txtProducto_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaProductos, null, 0);
        chkKeyPressed(e);
    }

    private void txtProducto_keyReleased(KeyEvent e) {
        chkKeyReleased(e);
    }

    private void txtProducto_keyTyped(KeyEvent e) {
        chkKeyTyped(e);
    }

    private void txtProducto_actionPerformed(ActionEvent e) {
    }

    private void tblListaProductos_keyPressed(KeyEvent e) {
        log.debug("tblListaProductos_keyPressed");
        chkKeyPressed(e);

    }

    private void btnListaProductos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocusJTable(this.tblListaProductos);
    }
    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            //ERIOS 03.07.2013 Limpia la caja de texto
            limpiaCadenaAlfanumerica(txtProducto);
            String codBarra = txtProducto.getText().toString().trim();
            if (codBarra.trim().length() == 0) {
                FarmaUtility.showMessage(this, "Ingrese Código de Barra y/o Producto. \n", txtProducto);
                return;
            } else if (codBarra.trim().length() > 14 || codBarra.trim().length() < 6) {
                FarmaUtility.showMessage(this, "Ingrese Código de Barra y/o Producto Correcto. \n", txtProducto);
                FarmaUtility.moveFocus(txtProducto);
            } else {

                if (codBarra.trim().length() > 6) { //codigo de barra
                    VariablesRecepCiega.vCodProd = obtieneCodigoProducto();
                    if (!VariablesRecepCiega.vCodProd.equalsIgnoreCase("000000")) {
                        muestraIngresoDatosProducto();
                    } else {
                        FarmaUtility.showMessage(this, "No existe el código de Barra. \n", txtProducto);
                        FarmaUtility.moveFocus(txtProducto);
                    }
                } else {
                    if (existeProducto()) {
                        VariablesRecepCiega.vCodProd = txtProducto.getText().trim();
                        muestraIngresoDatosProducto();
                    } else {
                        FarmaUtility.showMessage(this, "No existe el código de producto. \n", txtProducto);
                        FarmaUtility.moveFocus(txtProducto);
                    }

                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (!com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "¿Está seguro que desea Salir?"))
                return;
            //cancelaOperacion(); antes
            cancelaOperacion_02(); //ASOSA, 21.07.2010
            cerrarVentana(false);

        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            generaTransferencia();
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {
            buscarProducto();
            setJTable(tblListaProductos, txtProducto);
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {
            if (tblListaProductos.getRowCount() > 0) {
                if (!com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                 "¿Está seguro de eliminar el producto de la transferencia?"))
                    return;
                eliminarProducto();
                FarmaUtility.showMessage(this, "Se eliminó satisfactoriamente el producto. \n", txtProducto);
                setJTable(tblListaProductos, txtProducto);
            }

        }
        //JMIRANDA 12.05.2010
        else if (e.getKeyChar() == '+') {
            modificarProducto();
        }
        limpiaVariables();
        /* if (tableModelProductos.getRowCount()>0 ){
            FarmaGridUtils.showCell(tblListaProductos, 0, 0);
        }*/
    }

    private void chkKeyReleased(KeyEvent e) {
        //FarmaUtility.admitirDigitos(this.txtProducto,e);
    }

    private void chkKeyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtProducto, e);
        //chkKeyPressed(e);
        /*if(e.getKeyChar() == '+'){
        e.consume();
        //JMIRANDA 12.05.10
            modificarProducto();
            /*if(txtProducto.getText().trim().length()>14){
                FarmaUtility.showMessage(this,"Código de Barra no es correcto!",txtProducto);
            } -- * /
        }*/
    }
    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void limpiaVariables() {
        VariablesRecepCiega.vDesc_Producto = "";
        VariablesRecepCiega.vUnidad = "";
        VariablesRecepCiega.vCantIngreso = 0;
        VariablesRecepCiega.vLote = "";
        VariablesRecepCiega.vFechaVcto = "";
        VariablesRecepCiega.vCodProd = "";
        VariablesRecepCiega.vValPrecVta = "";
        VariablesRecepCiega.vValFrac = "";
        VariablesRecepCiega.vCantProdTransferir = "";
    }

    private boolean existeProducto() {
        VariablesRecepCiega.vCodProd = txtProducto.getText().trim();
        boolean existe = false;
        try {
            existe = DBRecepCiega.existeProducto(VariablesRecepCiega.vCodProd);
        } catch (SQLException ex) {
            VariablesRecepCiega.vCodProd = "";
            existe = false;
            log.error("", ex);
            FarmaUtility.showMessage(this, "Error al validar producto. \n", txtProducto);
        }
        return existe;
    }

    private String obtieneCodigoProducto() {
        String codProducto = "";
        try {
            codProducto = DBRecepCiega.obtieneCodigoProductoBarra(this.txtProducto.getText().toString().trim());
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al actualizar el registro.\n" +
                    sql.getMessage(), null);
        }
        return codProducto;
    }

    private void muestraIngresoDatosProducto() {
        FarmaVariables.vAceptar = false;

        DlgIngresoProdTransferencia vIngresoCantidad = new DlgIngresoProdTransferencia(this.myParentFrame, "", true);
        vIngresoCantidad.setVisible(true);
        String secRespaldo = VariablesRecepCiega.secRepStk; //ASOSA, 21.07.2010

        if (FarmaVariables.vAceptar) {
            txtProducto.setText("");
            FarmaUtility.moveFocus(txtProducto);
            //actualizaListaProductos(); antes
            actualizaListaProductos(secRespaldo); //ASOSA, 21.07.2010
        }

    }

    private void actualizaListaProductos(String secRespaldo) {

        if (!existeProductoEnArreglo()) { //verifica si el producto ingresado ya fue contado, si el producto y el lote coinciden solo se incrementa la cantidad, en caso coincidan en lote y producto, la fecha de vencimiento tb debe coincidir
            ArrayList auxArray = new ArrayList();

            auxArray.add(VariablesRecepCiega.vDesc_Producto);
            auxArray.add(VariablesRecepCiega.vUnidad);
            auxArray.add(VariablesRecepCiega.vCantIngreso + "");
            auxArray.add(VariablesRecepCiega.vLote);
            auxArray.add(VariablesRecepCiega.vFechaVcto);
            auxArray.add(VariablesRecepCiega.vCodProd);
            auxArray.add(VariablesRecepCiega.vValPrecVta);
            int cant = Integer.parseInt(VariablesRecepCiega.vCantProdTransferir);
            double prec = FarmaUtility.getDecimalNumber(VariablesRecepCiega.vValPrecVta);
            auxArray.add((cant * prec) + ""); //total
            auxArray.add(VariablesRecepCiega.vValFrac);
            auxArray.add(VariablesRecepCiega.vCantProdTransferir);

            auxArray.add(secRespaldo); //ASOSA, 21.07.2010
            tableModelProductos.insertRow((ArrayList)auxArray.clone());
            tblListaProductos.repaint();

            VariablesRecepCiega.vTableModelProdTranf = null;
            VariablesRecepCiega.vTableModelProdTranf = tableModelProductos;
        }
        VariablesRecepCiega.vIndBuscaProducto = false;
        VariablesRecepCiega.vIndModificarIngresoCantProdTranf = false;
    }

    private void cancelaOperacion() {
        String codProd = "";
        String codProdTmp = "";
        String cantidad = "";
        String val_frac = "";
        boolean existe = false;
        /*
      for(int i=0; i<tableModelProductos.getRowCount(); i++) {
        codProd = FarmaUtility.getValueFieldArrayList(tableModelProductos.data,i,5).trim();
        cantidad = FarmaUtility.getValueFieldArrayList(tableModelProductos.data,i,2).trim();
        val_frac =
              FarmaUtility.getValueFieldArrayList(tableModelProductos.data,
                                                  i,
                                                  8).trim();

        if (existeProductoEnlista(codProd,i+1)){
            actualizaStkComprometidoProd(codProd,
                                     Integer.parseInt(cantidad),
                                     ConstantsInventario.INDICADOR_D,
                                     ConstantsInventario.TIP_OPERACION_RESPALDO_ACTUALIZAR,
                                     Integer.parseInt(cantidad), val_frac.trim());
        }else{
            actualizaStkComprometidoProd(codProd, Integer.parseInt(cantidad),
                                             ConstantsInventario.INDICADOR_D,
                                             ConstantsInventario.TIP_OPERACION_RESPALDO_BORRAR,
                                             Integer.parseInt(cantidad),val_frac.trim());
        }
      }
        */
        //tableModelProductos.clearTable();

        ///////////////////
        ArrayList pListaProductos = (ArrayList)tableModelProductos.data.clone();
        ArrayList pListaElemento = new ArrayList();
        ArrayList pListaAgrupada = new ArrayList();
        String codProdAg = "";
        int cantidadAg = 0;
        boolean pNuevoElemento = true;
        for (int i = 0; i < pListaProductos.size(); i++) {
            codProd = FarmaUtility.getValueFieldArrayList(pListaProductos, i, 5);
            cantidad = FarmaUtility.getValueFieldArrayList(pListaProductos, i, 2).trim();
            val_frac = FarmaUtility.getValueFieldArrayList(pListaProductos, i, 8).trim();
            pListaElemento = new ArrayList();
            if (pListaAgrupada.size() == 0) {
                pListaElemento.add(codProd.trim());
                pListaElemento.add(cantidad.trim());
                pListaElemento.add(val_frac.trim());
                pListaAgrupada.add(pListaElemento);
            } else {
                for (int j = 0; j < pListaAgrupada.size(); j++) {
                    codProdAg = FarmaUtility.getValueFieldArrayList(pListaAgrupada, j, 0);
                    cantidadAg = Integer.parseInt(FarmaUtility.getValueFieldArrayList(pListaAgrupada, j, 1).trim());
                    if (codProdAg.trim().equalsIgnoreCase(codProd.trim())) {
                        pNuevoElemento = false;
                        cantidadAg += Integer.parseInt(cantidad.trim());
                        pListaElemento = new ArrayList();
                        pListaElemento.add(codProdAg.trim());
                        pListaElemento.add(cantidadAg + "");
                        pListaElemento.add(val_frac.trim());
                        pListaAgrupada.set(j, pListaElemento);
                    }
                }

                if (pNuevoElemento) {
                    pListaElemento = new ArrayList();
                    pListaElemento.add(codProd.trim());
                    pListaElemento.add(cantidad.trim() + "");
                    pListaElemento.add(val_frac.trim());
                    pListaAgrupada.add(pListaElemento);
                }
            }


        }

        log.info("pListaAgrupada:" + pListaAgrupada);
        for (int i = 0; i < pListaAgrupada.size(); i++) {
            codProd = FarmaUtility.getValueFieldArrayList(pListaAgrupada, i, 0).trim();
            cantidad = FarmaUtility.getValueFieldArrayList(pListaAgrupada, i, 1).trim();
            val_frac = FarmaUtility.getValueFieldArrayList(pListaAgrupada, i, 2).trim();
            actualizaStkComprometidoProd(codProd, Integer.parseInt(cantidad), ConstantsInventario.INDICADOR_D,
                                         ConstantsInventario.TIP_OPERACION_RESPALDO_BORRAR,
                                         Integer.parseInt(cantidad.trim()) * Integer.parseInt(val_frac.trim()),
                                         val_frac.trim());
        }
        tableModelProductos.clearTable();
        //JMIRANDA 12.05.10
        limpiaVariables();
        VariablesRecepCiega.vTableModelProdTranf = null;

    }

    private void actualizaStkComprometidoProd(String pCodigoProducto, int pCantidadStk, String pTipoStkComprometido,
                                              String pTipoRespaldoStock, int pCantidadRespaldo, String pValFrac) {
        try {
            DBInventario.actualizaStkComprometidoProd(pCodigoProducto, pCantidadStk * Integer.parseInt(pValFrac),
                                                      pTipoStkComprometido);
            DBPtoVenta.ejecutaRespaldoStock(pCodigoProducto, "", pTipoRespaldoStock, pCantidadRespaldo,
                                            Integer.parseInt(pValFrac), ConstantsPtoVenta.MODULO_TRANSFERENCIA);
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this,
                                     "Error al Actualizar Stock del Producto.\n Ponganse en contacto con el area de Sistemas\n" +
                    sql.getMessage(), txtProducto);
        }
    }

    private boolean existeProductoEnArreglo() {
        boolean encontrado = false;
        int i = 0;
        while (!encontrado && i < this.tableModelProductos.getRowCount()) {
            String codProd =
                FarmaUtility.getValueFieldArrayList(tableModelProductos.data, i, 5).trim(); //(String)this.tableModelProductos.getValueAt(1,5);//((String)((ArrayList)this.myArray.get(i)).get(5)).trim();
            String codLote =
                FarmaUtility.getValueFieldArrayList(tableModelProductos.data, i, 3).trim(); // (String)this.tableModelProductos.getValueAt(1,3);//((String)((ArrayList)this.myArray.get(i)).get(3)).trim();

            if (codProd.equalsIgnoreCase(VariablesRecepCiega.vCodProd) &&
                codLote.equalsIgnoreCase(VariablesRecepCiega.vLote))
                encontrado = true;
            if (!encontrado)
                i++;
        }
        if (encontrado) {
            int cantAnterior = Integer.parseInt((String)tableModelProductos.getValueAt(i, 2));
            int cantNueva = Integer.parseInt(VariablesRecepCiega.vCantProdTransferir);
            tableModelProductos.setValueAt((VariablesRecepCiega.vCantIngreso) /*cantAnterior+cantNueva*/ + "", i, 2);
            tableModelProductos.setValueAt(VariablesRecepCiega.vLote, i, 3);
            tableModelProductos.setValueAt(VariablesRecepCiega.vFechaVcto, i, 4);
            int cant = Integer.parseInt(VariablesRecepCiega.vCantProdTransferir);
            double prec = FarmaUtility.getDecimalNumber(VariablesRecepCiega.vValPrecVta);
            tableModelProductos.setValueAt((cant * prec) + "", i, 7);
            tableModelProductos.setValueAt(VariablesRecepCiega.vCantProdTransferir, i, 9);

        }

        return encontrado;
    }
    /*
    private void clonarListadoProductos()
    {
      ArrayList arrayClone = new ArrayList();
      for(int i=0;i<myArray.size();i++)
      {
        ArrayList aux = (ArrayList) ((ArrayList)myArray.get(i)).clone();
        arrayClone.add(aux);
      }
      this.tableModelProductos.data = arrayClone;
    }*/

    private void generaTransferencia() {

        if (tableModelProductos.getRowCount() > 0) {
            DlgTransferenciasTransporte vTransporte = new DlgTransferenciasTransporte(this.myParentFrame, "", true);
            vTransporte.setVisible(true);

            if (FarmaVariables.vAceptar) {
                grabar();
                cerrarVentana(true);
            }
        } else {
            FarmaUtility.showMessage(this, "Ingrese productos para generar la transferencia.\n", null);
        }

    }

    private void buscarProducto() {
        VariablesRecepCiega.vIndBuscaProducto = true;
        DlgListaProductos vListaProducto = new DlgListaProductos(this.myParentFrame, "", true);
        vListaProducto.setVisible(true);
        VariablesRecepCiega.vIndBuscaProducto = false;
        String secRespaldo = VariablesRecepCiega.secRepStk; //ASOSA, 21.07.2010
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
            //actualizaListaProductos(); antes
            actualizaListaProductos(secRespaldo); //ASOSA, 21.07.2010
        }

    }

    private boolean grabar() {
        boolean retorno;
        int valFracProd;
        String indFraccionamiento = "";
        try {
            double suma = 0.00;
            for (int p = 0; p < this.tableModelProductos.getRowCount(); p++) {
                suma +=
                        FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(tableModelProductos.data, p, 7).trim());
                suma = FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(suma));
            }
            log.debug("VariablesRecepCiega.vTipoDestino_Transf :" + VariablesRecepCiega.vTipoDestino_Transf);
            log.debug("VariablesRecepCiega.vCodDestino_Transf :" + VariablesRecepCiega.vCodDestino_Transf);
            log.debug("VariablesRecepCiega.vMotivo_Transf :" + VariablesRecepCiega.vMotivo_Transf);
            log.debug("VariablesRecepCiega.vDestino_Transf :" + VariablesRecepCiega.vDestino_Transf);
            log.debug("VariablesRecepCiega.vRucDestino_Transf :" + VariablesRecepCiega.vRucDestino_Transf);
            log.debug("VariablesRecepCiega.vDirecDestino_Transf :" + VariablesRecepCiega.vDirecDestino_Transf);
            log.debug("VariablesRecepCiega.vTransportista_Transf :" + VariablesRecepCiega.vTransportista_Transf);
            log.debug("VariablesRecepCiega.vRucTransportista_Transf :" + VariablesRecepCiega.vRucTransportista_Transf);
            log.debug("VariablesRecepCiega.vDirecTransportista_Transf :" +
                      VariablesRecepCiega.vDirecTransportista_Transf);
            log.debug("VariablesRecepCiega.vPlacaTransportista_Transf :" +
                      VariablesRecepCiega.vPlacaTransportista_Transf);
            log.debug(" tblListaProductos.getRowCount() :" + tableModelProductos.getRowCount());
            log.debug("VFarmaUtility.sumColumTable(tblListaProductos,7) :" + suma);
            log.debug("VariablesRecepCiega.vMotivo_Transf_Interno :" + VariablesRecepCiega.vMotivo_Transf_Interno);


            String numera =
                DBInventario.agregarCabeceraTransferencia(VariablesRecepCiega.vTipoDestino_Transf, VariablesRecepCiega.vCodDestino_Transf,
                                                          VariablesRecepCiega.vMotivo_Transf,
                                                          VariablesRecepCiega.vDestino_Transf,
                                                          VariablesRecepCiega.vRucDestino_Transf,
                                                          VariablesRecepCiega.vDirecDestino_Transf,
                                                          VariablesRecepCiega.vTransportista_Transf,
                                                          VariablesRecepCiega.vRucTransportista_Transf,
                                                          VariablesRecepCiega.vDirecTransportista_Transf,
                                                          VariablesRecepCiega.vPlacaTransportista_Transf,
                                                          tableModelProductos.getRowCount() + "", suma + "",
                                                          VariablesRecepCiega.vMotivo_Transf_Interno);

            for (int i = 0; i < tableModelProductos.getRowCount(); i++) {

                String codProd = FarmaUtility.getValueFieldArrayList(tableModelProductos.data, i, 5).trim();
                String precUnit =
                    FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(tableModelProductos.data, i,
                                                                                      6).trim()) + "";
                String precTotal = FarmaUtility.getValueFieldArrayList(tableModelProductos.data, i, 7).trim();
                String cantMov = FarmaUtility.getValueFieldArrayList(tableModelProductos.data, i, 2).trim();
                String fecVcto = FarmaUtility.getValueFieldArrayList(tableModelProductos.data, i, 4).trim();
                String numLote = FarmaUtility.getValueFieldArrayList(tableModelProductos.data, i, 3).trim();
                String valFrac = FarmaUtility.getValueFieldArrayList(tableModelProductos.data, i, 8).trim();
                String canTrans = FarmaUtility.getValueFieldArrayList(tableModelProductos.data, i, 9).trim();
                String secResp =
                    FarmaUtility.getValueFieldArrayList(tableModelProductos.data, i, 10).trim(); //ASOSA, 21.07.2010
                //agrega el detalle y también agrega al kardex
                /*DBInventario.agregarDetalleTransferencia(numera,codProd,precUnit,precTotal,  antes ASOSA, 21.07.2010
                                                   //canTrans,
                                                   Integer.parseInt(cantMov)*Integer.parseInt(valFrac)+"",
                                                   fecVcto,numLote,
          VariablesRecepCiega.vTipoDestino_Transf,
          VariablesRecepCiega.vCodDestino_Transf,valFrac,indFraccionamiento
            ,VariablesRecepCiega.vMotivo_Transf  );*/
                DBInventario.agregarDetalleTransferencia_02(numera, codProd, precUnit, precTotal, //ASOSA, 21.07.2010
                        //canTrans,
                        Integer.parseInt(cantMov) * Integer.parseInt(valFrac) + "", fecVcto, numLote,
                        VariablesRecepCiega.vTipoDestino_Transf, VariablesRecepCiega.vCodDestino_Transf, valFrac,
                        indFraccionamiento, VariablesRecepCiega.vMotivo_Transf, secResp);
                //    DBRecepCiega.actualizaCantidadRecepcionada(codProd,cantMov);
                DBRecepCiega.insertaDetalleTransferencia(codProd, cantMov, fecVcto, numLote, numera);

            }
            DBInventario.grabaInicioFinCreaTransferencia(numera,
                                                         "F"); //JCHAVEZ 10122009 registra fecha fin de creacion de transferencia

            log.debug("VariablesInventario.vTipoFormatoImpresion : " + VariablesInventario.vTipoFormatoImpresion);
            DBInventario.grabaInicioFinGuiasTransferencia(numera,
                                                          "I"); //JCHAVEZ 10122009 registra fecha inicio de generar guias e imprimirlas
            DBInventario.generarGuiasTransferencia(numera, VariablesInventario.vTipoFormatoImpresion,
                                                   tableModelProductos.getRowCount() + "");
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this, "Transferencia generada!", tblListaProductos);
            //Imprimir Comprobantes
            VariablesInventario.vNumNotaEs = numera;
            //JMIRANDA 16.02.10  INDICADOR TRANSFERENCIA POR RECEPCIÓN CIEGA.
            VariablesInventario.vIndTransfRecepCiega = true;
            // JQUISPE 11.05.2010
            UtilityInventario.procesoImpresionGuias(this, tblListaProductos,
                                                    VariablesInventario.vTipoFormatoImpresion);
            //UtilityInventario.procesoImpresionComprobante(this, tableModelProductos);
            DBInventario.grabaInicioFinGuiasTransferencia(numera,
                                                          "F"); //JCHAVEZ 10122009 registra fecha fin de generar guias e imprimirlas
            FarmaUtility.aceptarTransaccion(); //JCHAVEZ 10122009
            retorno = true;
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ha ocurrido un error al grabar la transferencia.\n" +
                    sql.getMessage(), btnListaProductos);
            retorno = false;
        } catch (Exception exc) {
            FarmaUtility.liberarTransaccion();
            log.error("", exc);
            FarmaUtility.showMessage(this, "Error en la aplicacion al grabar la transferencia.\n" +
                    exc.getMessage(), btnListaProductos);
            retorno = false;
        }
        return retorno;
    }

    private void eliminarProducto() {
        if (tableModelProductos.getRowCount() > 0) {
            String codProducto =
                FarmaUtility.getValueFieldArrayList(tableModelProductos.data, tblListaProductos.getSelectedRow(),
                                                    5).trim();
            String cantidad =
                FarmaUtility.getValueFieldArrayList(tableModelProductos.data, tblListaProductos.getSelectedRow(),
                                                    2).trim();
            String val_frac =
                FarmaUtility.getValueFieldArrayList(tableModelProductos.data, tblListaProductos.getSelectedRow(),
                                                    8).trim();
            String secRespaldo = //ASOSA, 21.07.2010
                FarmaUtility.getValueFieldArrayList(tableModelProductos.data, tblListaProductos.getSelectedRow(),
                                                    10).trim();
            // VariablesRecepCiega.vCantProdTransferir = ""+ Integer.parseInt(cantidad) * Integer.parseInt(VariablesRecepCiega.vValFrac)/* + fraccion*/;//txtCantidad.getText().toString().trim();
            if (existeProductoEnLista(codProducto)) {


                int cantidadEnteroTotal = 0;
                int i = 0;
                String codProd = "", cantidadParcial = "";
                if (tableModelProductos != null) {
                    while (i < tableModelProductos.getRowCount()) {
                        codProd = (String)tableModelProductos.getValueAt(i, 5);
                        cantidadParcial = (String)tableModelProductos.getValueAt(i, 2);
                        if (codProd.equalsIgnoreCase(codProducto)) {
                            cantidadEnteroTotal += Integer.parseInt(cantidadParcial.trim());
                        }
                        i++;
                    }
                }

                log.info("cantidadEnteroTotal:" + cantidadEnteroTotal);
                log.info("cantidad:" + cantidad);
                log.info("val_frac:" + val_frac);

                /*actualizaStkComprometidoProd(codProducto,
                                             Integer.parseInt(cantidad),
                                             ConstantsInventario.INDICADOR_D,
                                             ConstantsInventario.TIP_OPERACION_RESPALDO_ACTUALIZAR,
                                             (cantidadEnteroTotal -
                                              Integer.parseInt(cantidad)) *
                                             Integer.parseInt(val_frac),val_frac);*/
                actualizaStkComprometidoProd_02(codProducto, //ASOSA, 21.07.2010
                        Integer.parseInt(cantidad), ConstantsInventario.INDICADOR_D,
                        ConstantsInventario.TIP_OPERACION_RESPALDO_ACTUALIZAR,
                        (cantidadEnteroTotal - Integer.parseInt(cantidad)) * Integer.parseInt(val_frac),
                        val_frac.trim(), secRespaldo);
            } else {
                /* actualizaStkComprometidoProd(codProducto,
                                             Integer.parseInt(cantidad),
                                             ConstantsInventario.INDICADOR_D,
                                             ConstantsInventario.TIP_OPERACION_RESPALDO_BORRAR,
                                             Integer.parseInt(cantidad) *
                                             Integer.parseInt(val_frac),
                                             val_frac);*/
                actualizaStkComprometidoProd_02(codProducto, //ASOSA, 21.07.2010
                        0, ConstantsInventario.INDICADOR_D, ConstantsInventario.TIP_OPERACION_RESPALDO_BORRAR, 0,
                        val_frac.trim(), secRespaldo);
            }
            tableModelProductos.deleteRow(tblListaProductos.getSelectedRow());
        }
    }

    private boolean existeProductoEnLista(String pCodProd) {
        int contador = 0;
        int i = 0;
        if (tableModelProductos != null) {
            while (i < tableModelProductos.getRowCount()) {
                String codProd = (String)tableModelProductos.getValueAt(i, 5);
                if (tblListaProductos.getSelectedRow() != i) {
                    if (codProd.equalsIgnoreCase(pCodProd)) {
                        contador = contador + 1;
                    }

                }
                i++;
            }
        }
        if (contador > 0)
            return true;
        return false;
    }

    private boolean existeProductoEnlista(String pCodProd, int posicion) {
        int i = posicion;
        boolean encontrado = false;
        if (tableModelProductos != null) {
            while (i < tableModelProductos.getRowCount() && !encontrado) {
                String codProd = (String)tableModelProductos.getValueAt(i, 5);
                if (codProd.equalsIgnoreCase(pCodProd)) {
                    encontrado = true;
                }

                i++;
            }
        }
        return encontrado;
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void setJTable(JTable pJTable, JTextFieldSanSerif pText) {
        if (pJTable.getRowCount() > 0) {
            FarmaGridUtils.showCell(pJTable, 0, 0);
            FarmaUtility.setearActualRegistro(pJTable, null, 0);
        }
        FarmaUtility.moveFocus(pText);
    }

    //JMIRANDA 12.05.2010

    private void modificarProducto() {
        log.debug("Apunta a tblRelacionProductosConteo");
        VariablesRecepCiega.vIndModificarIngresoCantProdTranf = true;
        int fila = tblListaProductos.getSelectedRow();
        if (fila > -1) {
            VariablesRecepCiega.vCodProd =
                    FarmaUtility.getValueFieldArrayList(tableModelProductos.data, fila, 5); //(String)this.tableModelProductos.getValueAt(fila,5).toString();
            VariablesRecepCiega.vDesc_Producto =
                    FarmaUtility.getValueFieldArrayList(tableModelProductos.data, fila, 0);
            VariablesRecepCiega.vUnidad = FarmaUtility.getValueFieldArrayList(tableModelProductos.data, fila, 1);
            VariablesRecepCiega.vCantIngreso =
                    Integer.parseInt(FarmaUtility.getValueFieldArrayList(tableModelProductos.data, fila, 2));
            VariablesRecepCiega.vLote = FarmaUtility.getValueFieldArrayList(tableModelProductos.data, fila, 3);
            VariablesRecepCiega.vFechaVcto = FarmaUtility.getValueFieldArrayList(tableModelProductos.data, fila, 4);
            VariablesRecepCiega.vCantProdTransferir =
                    FarmaUtility.getValueFieldArrayList(tableModelProductos.data, fila, 9);
            VariablesRecepCiega.vValFrac = FarmaUtility.getValueFieldArrayList(tableModelProductos.data, fila, 8);
            muestraIngresoDatosProducto();
            VariablesRecepCiega.vIndModificarIngresoCantProdTranf = false;
        }
    }

    /*******************************************ASOSA, 21.07.2010********************************************/

    //copiado para ser modificado unicamente lo de stkcomprometido asumiendo que lo demas esta bien
    private void actualizaStkComprometidoProd_02(String pCodigoProducto, int pCantidadStk, String pTipoStkComprometido,
                                                 String pTipoRespaldoStock, int pCantidadRespaldo, String pValFrac,
                                                 String secRespaldo) {
        VariablesRecepCiega.secRepStk = "0";
        /*
      try
      {
          VariablesRecepCiega.secRepStk=""; //ASOSA, 26.08.2010

          VariablesRecepCiega.secRepStk= "0";
          DBVentas.operarResStkAntesDeCobrar(pCodigoProducto,
                                                                           String.valueOf(pCantidadRespaldo),
                                                                           pValFrac,
                                                                           secRespaldo,
                                                                           ConstantsPtoVenta.MODULO_TRANSFERENCIA);
        FarmaUtility.aceptarTransaccion();
      }catch (SQLException sql)
      {
        FarmaUtility.liberarTransaccion();
        log.error("",sql);
        FarmaUtility.showMessage(this, "Error al Actualizar Stock del Producto.\n Ponganse en contacto con el area de Sistemas\n" + sql.getMessage() ,txtProducto);
      }
                                                                       */
    }

    private void cancelaOperacion_02() {
        String codProd = "";
        String codProdTmp = "";
        String cantidad = "";
        String val_frac = "";
        boolean existe = false;
        String secResp = ""; //ASOSA, 21.07.2010

        ArrayList pListaProductos = (ArrayList)tableModelProductos.data.clone();
        ArrayList pListaElemento = new ArrayList();
        ArrayList pListaAgrupada = new ArrayList();
        String codProdAg = "";
        int cantidadAg = 0;
        boolean pNuevoElemento = true;
        for (int i = 0; i < pListaProductos.size(); i++) {
            codProd = FarmaUtility.getValueFieldArrayList(pListaProductos, i, 5);
            cantidad = FarmaUtility.getValueFieldArrayList(pListaProductos, i, 2).trim();
            val_frac = FarmaUtility.getValueFieldArrayList(pListaProductos, i, 8).trim();
            secResp = FarmaUtility.getValueFieldArrayList(pListaProductos, i, 10).trim(); //ASOSA, 21.07.2010
            pListaElemento = new ArrayList();
            if (pListaAgrupada.size() == 0) {
                pListaElemento.add(codProd.trim());
                pListaElemento.add(cantidad.trim());
                pListaElemento.add(val_frac.trim());
                pListaElemento.add(secResp.trim()); //ASOSA, 21.07.2010
                pListaAgrupada.add(pListaElemento);
            } else {
                for (int j = 0; j < pListaAgrupada.size(); j++) {
                    codProdAg = FarmaUtility.getValueFieldArrayList(pListaAgrupada, j, 0);
                    cantidadAg = Integer.parseInt(FarmaUtility.getValueFieldArrayList(pListaAgrupada, j, 1).trim());
                    if (codProdAg.trim().equalsIgnoreCase(codProd.trim())) {
                        pNuevoElemento = false;
                        cantidadAg += Integer.parseInt(cantidad.trim());
                        pListaElemento = new ArrayList();
                        pListaElemento.add(codProdAg.trim());
                        pListaElemento.add(cantidadAg + "");
                        pListaElemento.add(val_frac.trim());
                        pListaElemento.add(secResp.trim()); //ASOSA, 21.07.2010
                        pListaAgrupada.set(j, pListaElemento);
                    }
                }

                if (pNuevoElemento) {
                    pListaElemento = new ArrayList();
                    pListaElemento.add(codProd.trim());
                    pListaElemento.add(cantidad.trim() + "");
                    pListaElemento.add(val_frac.trim());
                    pListaElemento.add(secResp.trim()); //ASOSA, 21.07.2010
                    pListaAgrupada.add(pListaElemento);
                }
            }


        }

        log.info("pListaAgrupada:" + pListaAgrupada);
        String secRespaldo = ""; //ASOSA, 21.07.2010
        for (int i = 0; i < pListaAgrupada.size(); i++) {
            codProd = FarmaUtility.getValueFieldArrayList(pListaAgrupada, i, 0).trim();
            cantidad = FarmaUtility.getValueFieldArrayList(pListaAgrupada, i, 1).trim();
            val_frac = FarmaUtility.getValueFieldArrayList(pListaAgrupada, i, 2).trim();
            secRespaldo = FarmaUtility.getValueFieldArrayList(pListaAgrupada, i, 3).trim(); //ASOSA, 21.07.2010
            /*actualizaStkComprometidoProd(codProd, Integer.parseInt(cantidad),
                                         ConstantsInventario.INDICADOR_D,
                                         ConstantsInventario.TIP_OPERACION_RESPALDO_BORRAR,
                                         Integer.parseInt(cantidad.trim())*Integer.parseInt(val_frac.trim()),
                                         val_frac.trim());*/
            actualizaStkComprometidoProd_02(codProd, //ASOSA, 21.07.2010
                    0, ConstantsInventario.INDICADOR_D, ConstantsInventario.TIP_OPERACION_RESPALDO_BORRAR, 0,
                    val_frac.trim(), secRespaldo);

        }

        tableModelProductos.clearTable();
        //JMIRANDA 12.05.10
        limpiaVariables();
        VariablesRecepCiega.vTableModelProdTranf = null;

    }

}
