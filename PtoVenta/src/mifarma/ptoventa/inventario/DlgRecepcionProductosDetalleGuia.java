package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
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
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.recepcionCiega.DlgListaTransportistas;
import mifarma.ptoventa.recepcionCiega.reference.DBRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.UtilityRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.VariablesRecepCiega;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.reportes.DlgOrdenar;
import mifarma.ptoventa.reportes.reference.VariablesReporte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgRecepcionProductosDetalleGuia extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgRecepcionProductosDetalleGuia.class);

    Frame myParentFrame;

    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelWhite jContentPane = new JPanelWhite();

    private JPanelHeader pnlHeader1 = new JPanelHeader();

    private JLabelWhite lblGuia_T = new JLabelWhite();

    private JLabelWhite lblFecha_T = new JLabelWhite();

    private JLabelWhite lblGuia = new JLabelWhite();

    private JLabelWhite lblFecha = new JLabelWhite();

    private JLabelWhite lblProductos_T = new JLabelWhite();

    private JLabelWhite lblItems_T = new JLabelWhite();

    private JLabelWhite lblItems = new JLabelWhite();

    private JLabelWhite lblProductos = new JLabelWhite();

    private JLabelWhite lblStock_T = new JLabelWhite();

    private JLabelWhite lblEstado_T = new JLabelWhite();

    private JLabelWhite lblEstado = new JLabelWhite();

    private JLabelWhite lblStock = new JLabelWhite();

    private JButtonLabel btnBuscar = new JButtonLabel();

    private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();

    private JPanelTitle pnlTitle1 = new JPanelTitle();

    private JScrollPane scrListaProductos = new JScrollPane();

    private JLabelFunction lblEsc = new JLabelFunction();

    private JLabelFunction lblF9 = new JLabelFunction();


    private JLabelFunction lblF2 = new JLabelFunction();

    private JButtonLabel btnRelacionProductos = new JButtonLabel();

    private JTable tblListaProductos = new JTable();
    private JLabelFunction lblF1 = new JLabelFunction();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgRecepcionProductosDetalleGuia() {
        this(null, "", false);
    }

    public DlgRecepcionProductosDetalleGuia(Frame parent, String title, boolean modal) {
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

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(780, 411));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Detalle de Guia");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlHeader1.setBounds(new Rectangle(10, 10, 755, 75));
        lblGuia_T.setText("Guía:");
        lblGuia_T.setBounds(new Rectangle(10, 5, 40, 15));
        lblFecha_T.setText("Fecha:");
        lblFecha_T.setBounds(new Rectangle(10, 25, 45, 15));
        lblGuia.setText("000001");
        lblGuia.setBounds(new Rectangle(55, 5, 105, 15));
        lblGuia.setFont(new Font("SansSerif", 0, 11));
        lblFecha.setText("09/01/2006 00:00:00");
        lblFecha.setBounds(new Rectangle(55, 25, 110, 15));
        lblFecha.setFont(new Font("SansSerif", 0, 11));
        lblProductos_T.setText("Productos:");
        lblProductos_T.setBounds(new Rectangle(175, 25, 65, 15));
        lblItems_T.setText("Items:");
        lblItems_T.setBounds(new Rectangle(175, 5, 60, 15));
        lblItems.setText("1");
        lblItems.setBounds(new Rectangle(225, 5, 70, 15));
        lblItems.setFont(new Font("SansSerif", 0, 11));
        lblProductos.setText("0");
        lblProductos.setBounds(new Rectangle(240, 25, 70, 15));
        lblProductos.setFont(new Font("SansSerif", 0, 11));
        lblStock_T.setText("Stock:");
        lblStock_T.setBounds(new Rectangle(325, 5, 45, 15));
        lblEstado_T.setText("Estado:");
        lblEstado_T.setBounds(new Rectangle(325, 25, 50, 15));
        lblEstado.setText("Pendiente");
        lblEstado.setBounds(new Rectangle(370, 25, 70, 15));
        lblEstado.setFont(new Font("SansSerif", 0, 11));
        lblStock.setText("Afecta Stock");
        lblStock.setBounds(new Rectangle(370, 5, 70, 15));
        lblStock.setFont(new Font("SansSerif", 0, 11));
        btnBuscar.setText("Buscar :");
        btnBuscar.setBounds(new Rectangle(10, 45, 55, 15));
        btnBuscar.setMnemonic('B');
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        txtBuscar.setBounds(new Rectangle(65, 45, 295, 20));
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtBuscar_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtBuscar_keyReleased(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(10, 90, 755, 25));
        scrListaProductos.setBounds(new Rectangle(10, 115, 755, 220));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(680, 350, 85, 20));
        lblF9.setText("[ F9 ] Afecta por Producto");
        lblF9.setBounds(new Rectangle(390, 350, 175, 20));
        lblF2.setText("[ F2 ] Afecta Página");
        //JMIRANDA 14.12.09
        lblF2.setVisible(true);
        lblF9.setVisible(true);
        lblF2.setBounds(new Rectangle(130, 350, 125, 20));
        btnRelacionProductos.setText("Relacion de Productos Seleccionados");
        btnRelacionProductos.setBounds(new Rectangle(5, 5, 215, 15));
        lblF1.setBounds(new Rectangle(15, 350, 100, 20));
        lblF1.setText("[ F1 ] Ordenar");
        pnlHeader1.add(txtBuscar, null);
        pnlHeader1.add(btnBuscar, null);
        pnlHeader1.add(lblStock, null);
        pnlHeader1.add(lblEstado, null);
        pnlHeader1.add(lblEstado_T, null);
        pnlHeader1.add(lblStock_T, null);
        pnlHeader1.add(lblProductos, null);
        pnlHeader1.add(lblItems, null);
        pnlHeader1.add(lblItems_T, null);
        pnlHeader1.add(lblProductos_T, null);
        pnlHeader1.add(lblFecha, null);
        pnlHeader1.add(lblGuia, null);
        pnlHeader1.add(lblFecha_T, null);
        pnlHeader1.add(lblGuia_T, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(lblF2, null);
        jContentPane.add(lblF9, null);
        jContentPane.add(lblEsc, null);
        scrListaProductos.getViewport().add(tblListaProductos, null);
        jContentPane.add(scrListaProductos, null);
        pnlTitle1.add(btnRelacionProductos, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(pnlHeader1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        mifarma.common.FarmaVariables.vAceptar = false;
        initTable();
        inicializa(); //JCHAVEZ 17122009
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsInventario.columnsListaProductosRecepcion, ConstantsInventario.defaultValuesListaProductosRecepcion,
                                    0);
        FarmaUtility.initSimpleList(tblListaProductos, tableModel, ConstantsInventario.columnsListaProductosRecepcion);
        tblListaProductos.getTableHeader().setReorderingAllowed(false);
        tblListaProductos.getTableHeader().setResizingAllowed(false);
        cargaListaProductos();
    }

    private void inicializa() {

        if (VariablesPtoVenta.vIndRecepCiega.equalsIgnoreCase("S")) { // JCHAVEZ 1712009 si el indicador esta en "S" estaactiva la reccepcion ciega
            //lblF2.setVisible(false);
            lblF9.setVisible(false);
        }
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void btnBuscar_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtBuscar);
    }

    private void txtBuscar_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaProductos, txtBuscar, 1);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            if (tblListaProductos.getSelectedRow() >= 0) {
                if (!(FarmaUtility.findTextInJTable(tblListaProductos, txtBuscar.getText().trim(), 0, 1))) {
                    FarmaUtility.showMessage(this, "Producto No Encontrado según Criterio de Búsqueda !!!", txtBuscar);
                    return;
                }
            }
        }
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtBuscar);
        mostrarDatos();
    }

    private void txtBuscar_keyReleased(KeyEvent e) {
        FarmaUtility.ponerMayuscula(e, txtBuscar);
        FarmaGridUtils.buscarDescripcion(e, tblListaProductos, txtBuscar, 1);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", txtBuscar);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            funcionEnter();
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {
            muestraVentaOrdenar();
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {
            funcionF2();
        } else if (e.getKeyCode() == KeyEvent.VK_F9) {
            if (lblF9.isVisible()) {
                funcionF9();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.setVisible(false);
        }
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cargaListaProductos() {
        try {
            DBInventario.getListaDetGuiasRecep(tableModel);
            if (tblListaProductos.getRowCount() > 0) {
                FarmaUtility.ordenar(tblListaProductos, tableModel, 12, FarmaConstants.ORDEN_ASCENDENTE);
                verificaGuiaCerrada();
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar la lista de productos : \n", txtBuscar);
            ;
        }
    }

    private void mostrarDatos() {
        lblGuia.setText(VariablesInventario.vNumGuia.trim());
        lblItems.setText("" + tblListaProductos.getRowCount());
        lblFecha.setText(VariablesInventario.vFecCreaNota);
        lblProductos.setText(VariablesInventario.vCantItems.trim());
        lblEstado.setText(VariablesInventario.vEstRecepcion);
    }

    private void cargarRegistroSeleccionado() {
        VariablesInventario.vSelectedRow = tblListaProductos.getSelectedRow();
        VariablesInventario.vCodProd =
                tblListaProductos.getValueAt(VariablesInventario.vSelectedRow, 0).toString().trim();
        VariablesInventario.vDescProd =
                tblListaProductos.getValueAt(VariablesInventario.vSelectedRow, 1).toString().trim();
        VariablesInventario.vDescUnidPresent =
                tblListaProductos.getValueAt(VariablesInventario.vSelectedRow, 2).toString().trim();
        VariablesInventario.vNomLab =
                tblListaProductos.getValueAt(VariablesInventario.vSelectedRow, 3).toString().trim();
        VariablesInventario.vCantGuia =
                tblListaProductos.getValueAt(VariablesInventario.vSelectedRow, 4).toString().trim();
        VariablesInventario.vValorFrac =
                tblListaProductos.getValueAt(VariablesInventario.vSelectedRow, 9).toString().trim();
        VariablesInventario.vStkFis =
                tblListaProductos.getValueAt(VariablesInventario.vSelectedRow, 10).toString().trim();
        VariablesInventario.vSecDetNota =
                tblListaProductos.getValueAt(VariablesInventario.vSelectedRow, 11).toString().trim();

    }

    private boolean tieneRegistroSeleccionado(JTable pTabla) {
        boolean rpta = false;
        if (pTabla.getSelectedRow() != -1) {
            rpta = true;
        }
        return rpta;
    }

    private void setearValores() {
        try {
            DBInventario.setearValores();
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al actualizar el registro.\n" +
                    sql.getMessage(), txtBuscar);
        }
    }

    private boolean esRegistroHabil() {
        boolean rpta = true;
        String est = "";
        est = tblListaProductos.getValueAt(tblListaProductos.getSelectedRow(), 7).toString().trim();
        if (est.equals("S")) {
            rpta = false;
            FarmaUtility.showMessage(this, "El producto ya se encuentra afectado", txtBuscar);
        }
        return rpta;
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void actualizarRegUnico() throws SQLException {
        cargarRegistroSeleccionado();
        DBInventario.actualizaRegistroUnico();
    }

    private void muestraVentaOrdenar() {
        DlgOrdenar dlgOrdenar = new DlgOrdenar(myParentFrame, "Ordenar", true);
        String[] IND_CAMPO = { "12", "1" };
        String[] IND_DESCRIP_CAMPO = { "Pagina", "Descripcion" };
        log.debug("Campo " + IND_DESCRIP_CAMPO[1]);
        VariablesReporte.vNombreInHashtable = ConstantsInventario.HASHTABLE_ORDENAR_RECEPCION;
        FarmaLoadCVL.loadCVLfromArrays(dlgOrdenar.getCmbCampo(), VariablesReporte.vNombreInHashtable, IND_CAMPO,
                                       IND_DESCRIP_CAMPO, true);
        dlgOrdenar.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
            FarmaUtility.ordenar(tblListaProductos, tableModel, VariablesReporte.vCampo, VariablesReporte.vOrden);
            tblListaProductos.repaint();
        }
    }

    private void verificaGuiaCerrada() {
        boolean verifica = true;
        for (int i = 0; i < tblListaProductos.getRowCount(); i++) {
            if (tblListaProductos.getValueAt(i, 7).toString().trim().equals("N")) {
                verifica = false;
                break;
            }
        }
        if (verifica) {
            FarmaUtility.showMessage(this, "Guía Cerrada", txtBuscar);
            cerrarVentana(true);
        }
    }


    /*
   private void ingresaCabeceraEntregas(){
       DlgDatosTransportistaInv objDTI=new DlgDatosTransportistaInv(myParentFrame,"",true);
       objDTI.setVisible(true);
         //GRABA DATOS CABECERA ENTREGA

       /////////////////----------------------
         //lo de este metodo
      if (FarmaVariables.vAceptar) {
       if(FarmaVariables.vEconoFar_Matriz)
         FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,txtBuscar);
       else
         if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                            "¿Está seguro de afectar la Guia?"))
         {
           try
           {
             //if (FarmaVariables.vAceptar) {
             VariablesRecepCiega.vNro_Recepcion = DBInventario.creaRecepEntrega();
             DBInventario.actualizaPagina();
               //JMIRANDA 09.02.10
               //Cambio para asociar las entregas en la tabla lgt_recep_entrega solo si no esta activado la RecepCiega
               DBInventario.asociaRecepEntrega(VariablesRecepCiega.vNro_Recepcion);
               //ingresa 1 porque se ha seleccionado 1 detalle
               DBInventario.actualizaCantGuias(1,VariablesRecepCiega.vNro_Recepcion);
             FarmaUtility.aceptarTransaccion();
             FarmaUtility.showMessage(this,
                                      "La operación se realizó correctamente. Guía Cerrada.",
                                      txtBuscar);
             cerrarVentana(true);

           }
           catch (SQLException sql)
           {
             FarmaUtility.liberarTransaccion();
             log.error("",sql);
             FarmaUtility.showMessage(this,
                                      "Ocurrió un error al afectar la página : \n" +
                                      sql.getMessage(), txtBuscar);
           }

           FarmaVariables.vAceptar = false;
         }
        }
         else {
                 log.debug("Cerro ventana ");
              }
      }
    */

    //JQUISPE 15.04.2010 CAMBIOS SIN RECEPCION CIEGA

    private void ingresaCabeceraEntregas() {
        DlgDatosTransportistaInv objDTI = new DlgDatosTransportistaInv(myParentFrame, "", true);
        objDTI.setVisible(true);
        //GRABA DATOS CABECERA ENTREGA

        /////////////////----------------------
        //lo de este metodo
        if (FarmaVariables.vAceptar) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, txtBuscar);
            else if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                 "¿Está seguro de afectar la Guia?")) {
                try {
                    //if (FarmaVariables.vAceptar) {
                    VariablesRecepCiega.vNro_Recepcion = DBInventario.creaRecepEntrega();
                    DBInventario.actualizaPagina();
                    //JMIRANDA 09.02.10
                    //Cambio para asociar las entregas en la tabla lgt_recep_entrega solo si no esta activado la RecepCiega
                    DBInventario.asociaRecepEntrega(VariablesRecepCiega.vNro_Recepcion);
                    //ingresa 1 porque se ha seleccionado 1 detalle
                    DBInventario.actualizaCantGuias(1, VariablesRecepCiega.vNro_Recepcion);
                    FarmaUtility.aceptarTransaccion();
                    FarmaUtility.showMessage(this, "La operación se realizó correctamente. Guía Cerrada.", txtBuscar);
                    cerrarVentana(true);

                } catch (SQLException sql) {
                    FarmaUtility.liberarTransaccion();

                    if (sql.getErrorCode() == 20010) {
                        FarmaUtility.showMessage(this,
                                                 sql.getMessage().substring(11, sql.getMessage().indexOf("ORA-06512")),
                                                 txtBuscar);
                    } else {
                        log.error("", sql);

                        FarmaUtility.showMessage(this, "Ocurrió un error al afectar la página : \n" +
                                sql.getMessage(), txtBuscar);
                    }
                }

                FarmaVariables.vAceptar = false;
            }
        } else {
            log.debug("Cerro ventana ");
        }


    }

    private void funcionF2() {
        if (VariablesPtoVenta.vIndRecepCiega.equalsIgnoreCase("N")) { //JCHAVEZ 1712009 solo si la recepcion ciega no esta activa
            boolean flag = false; //----
            flag = validaTipos();
            //----
            //ingreso cabecera
            if (flag) {
                if (UtilityRecepCiega.indHabDatosTransp()) {
                    asociaCabeceraEntregas();
                } else {
                    ingresaCabeceraEntregas();
                }
            } else {
                actualizaPagina();
            }

        } else {
            //JMIRANDA 18.03.2010 CON RECEP CIEGA ACTIVA
            boolean flag2 = false; //----
            flag2 = validaTipos();
            if (!flag2) {
                //Falso indica que si puede afectar ZREG, UB_L, ZTNL cuando está activa Recep Ciega
                actualizaPagina();
            } else {
                FarmaUtility.showMessage(this, "No puede afectar página para este tipo de guías\n" +
                        "Sólo podrá afectarla en la Recepción Ciega.", txtBuscar);
            }
        }
    }

    private boolean validaTipos() {
        boolean flag = false;
        if (VariablesInventario.vTipoPedRep.trim().equalsIgnoreCase(ConstantsInventario.TIPO_PED_REG_ZREG)) {
            flag = false;
        }
        if (VariablesInventario.vTipoPedRep.trim().equalsIgnoreCase(ConstantsInventario.TIPO_PED_REG_UB)) {
            //flag = false;}
            flag = true;
        }
        if (VariablesInventario.vTipoPedRep.trim().equalsIgnoreCase(ConstantsInventario.TIPO_PED_REG_UB_L)) {
            flag = false;
        }
        if (VariablesInventario.vTipoPedRep.trim().equalsIgnoreCase(ConstantsInventario.TIPO_PED_REG_ZTNL)) {
            flag = false;
        }
        if (VariablesInventario.vTipoPedRep.trim().equalsIgnoreCase("")) {
            flag = true;
        }
        return flag;
    }

    private void actualizaPagina() {
        if (FarmaVariables.vEconoFar_Matriz)
            FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, txtBuscar);
        else if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                             "¿Está seguro de afectar la Guia?")) {
            try {
                DBInventario.actualizaPagina();
                FarmaUtility.aceptarTransaccion();
                FarmaUtility.showMessage(this, "La operación se realizó correctamente. Guía Cerrada.", txtBuscar);
                cerrarVentana(true);
            } catch (SQLException sql) {
                FarmaUtility.liberarTransaccion();
                log.error("", sql);
                FarmaUtility.showMessage(this, "Ocurrió un error al afectar la página : \n" +
                        sql.getMessage(), txtBuscar);
            }
            FarmaVariables.vAceptar = false;
        }
    }

    private void funcionF9() {
        // por producto
        //JCHAVEZ 1712009 solo si la recepcion ciega no esta activa
        if (VariablesPtoVenta.vIndRecepCiega.equalsIgnoreCase("N")) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, txtBuscar);
            else if (tieneRegistroSeleccionado(tblListaProductos)) {
                if (esRegistroHabil()) {
                    if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                    "¿Esta seguro de afectar el producto?")) {
                        try {
                            actualizarRegUnico();
                            FarmaUtility.aceptarTransaccion();
                            FarmaUtility.showMessage(this, "La operación se realizó correctamente", txtBuscar);
                            cargaListaProductos();
                        } catch (SQLException sql) {
                            FarmaUtility.liberarTransaccion();
                            FarmaUtility.showMessage(this, "Ocurrió un error al afectar el producto : \n" +
                                    sql.getMessage(), txtBuscar);
                            log.error("", sql);
                        }
                    }
                }
            }
        }
    }

    private void funcionEnter() {
        if (VariablesPtoVenta.vIndRecepCiega.equalsIgnoreCase("N")) { //JCHAVEZ 1712009 solo si la recepcion ciega no esta activa
            //JMIRANDA 14.12.09 SE INHABILITA PORQUE DEBE AFECTARSE TODA LA GUIA
            mostrarIngresoCantidad();
        } else {
            //JMIRANDA 18.03.2010 SI RECEP CIEGA HABILITADA APLICA A ZREG, ZTNL, UB_L
            if (!validaTipos()) {
                //es valido Zreg
                mostrarIngresoCantidad();
            } else {
                FarmaUtility.showMessage(this, "Está función no está habilitada.", txtBuscar);
            }
        }
    }

    private void mostrarIngresoCantidad() {
        if (tieneRegistroSeleccionado(tblListaProductos)) {
            if (esRegistroHabil()) {
                cargarRegistroSeleccionado();
                DlgRecepcionProductosIngresoCantidad dlgRecepcionProductosIngresoCantidad =
                    new DlgRecepcionProductosIngresoCantidad(myParentFrame, "", true);
                dlgRecepcionProductosIngresoCantidad.setVisible(true);

                if (FarmaVariables.vAceptar) {
                    setearValores();
                    cargaListaProductos();
                    FarmaVariables.vAceptar = false;
                }
            }
        }
    }
    /*
    private void asociaCabeceraEntregas(){
        VariablesRecepCiega.vIndAsociaTransportista = true;
        DlgListaTransportistas dlgTrans = new DlgListaTransportistas(myParentFrame,"",true);
        dlgTrans.setVisible(true);
          //GRABA DATOS CABECERA ENTREGA
        /////////////////----------------------
          //lo de este metodo
       if (FarmaVariables.vAceptar) {
        if(FarmaVariables.vEconoFar_Matriz)
          FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,txtBuscar);
        else
          if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                             "¿Está seguro de afectar la Guia?"))
          {
            try
            {
              //if (FarmaVariables.vAceptar) {
              //VariablesRecepCiega.vNro_Recepcion = DBInventario.creaRecepEntrega();
              DBInventario.actualizaPagina();
                //JMIRANDA 09.02.10
                //Cambio para asociar las entregas en la tabla lgt_recep_entrega solo si no esta activado la RecepCiega
                DBInventario.asociaRecepEntrega(VariablesRecepCiega.vTran_NroRecepcion);
                //ingresa 1 porque se ha seleccionado 1 detalle
                //JMIRANDA 18.03.2010 SUMAR 1 A LA CANT OBTENIDA
                int vCantGuias = DBRecepCiega.getCantGuias(VariablesRecepCiega.vTran_NroRecepcion);
                DBInventario.actualizaCantGuias(vCantGuias+1,VariablesRecepCiega.vTran_NroRecepcion);
              FarmaUtility.aceptarTransaccion();
              FarmaUtility.showMessage(this,
                                       "La operación se realizó correctamente. Guía Cerrada.",
                                       txtBuscar);
              cerrarVentana(true);

            }
            catch (SQLException sql)
            {
                FarmaUtility.liberarTransaccion();

                    log.error("",sql);
                    FarmaUtility.showMessage(this,
                                             "Ocurrió un error al asignar guias.\n" +
                                              sql.getMessage(), txtBuscar);

            }
            FarmaVariables.vAceptar = false;
          }
         }
          else {
                  log.debug("Cerro ventana ");
               }
        }
     */

    private void asociaCabeceraEntregas() {
        VariablesRecepCiega.vIndAsociaTransportista = true;
        DlgListaTransportistas dlgTrans = new DlgListaTransportistas(myParentFrame, "", true);
        dlgTrans.setVisible(true);
        //GRABA DATOS CABECERA ENTREGA
        /////////////////----------------------
        //lo de este metodo
        if (FarmaVariables.vAceptar) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, txtBuscar);
            else if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                 "¿Está seguro de afectar la Guia?")) {
                try {
                    //if (FarmaVariables.vAceptar) {
                    //VariablesRecepCiega.vNro_Recepcion = DBInventario.creaRecepEntrega();
                    DBInventario.actualizaPagina();
                    //JMIRANDA 09.02.10
                    //Cambio para asociar las entregas en la tabla lgt_recep_entrega solo si no esta activado la RecepCiega
                    DBInventario.asociaRecepEntrega(VariablesRecepCiega.vTran_NroRecepcion);
                    //ingresa 1 porque se ha seleccionado 1 detalle
                    //JMIRANDA 18.03.2010 SUMAR 1 A LA CANT OBTENIDA
                    int vCantGuias = DBRecepCiega.getCantGuias(VariablesRecepCiega.vTran_NroRecepcion);
                    DBInventario.actualizaCantGuias(vCantGuias + 1, VariablesRecepCiega.vTran_NroRecepcion);
                    FarmaUtility.aceptarTransaccion();
                    FarmaUtility.showMessage(this, "La operación se realizó correctamente. Guía Cerrada.", txtBuscar);
                    cerrarVentana(true);

                } catch (SQLException sql) {
                    FarmaUtility.liberarTransaccion();

                    if (sql.getErrorCode() == 20010) {
                        FarmaUtility.showMessage(this,
                                                 sql.getMessage().substring(11, sql.getMessage().indexOf("ORA-06512")),
                                                 txtBuscar);
                    } else {
                        log.error("", sql);
                        FarmaUtility.showMessage(this, "Ocurrió un error al asignar guias.\n" +
                                sql.getMessage(), txtBuscar);
                    }
                }
                FarmaVariables.vAceptar = false;
            }
        } else {
            log.debug("Cerro ventana ");
        }
    }
}
