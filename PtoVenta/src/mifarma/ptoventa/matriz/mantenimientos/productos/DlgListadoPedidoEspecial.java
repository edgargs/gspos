package mifarma.ptoventa.matriz.mantenimientos.productos;


import com.gs.mifarma.componentes.JButtonFunction;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaTableComparator;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.matriz.mantenimientos.productos.references.ConstantsProducto;
import mifarma.ptoventa.matriz.mantenimientos.productos.references.DBProducto;
import mifarma.ptoventa.matriz.mantenimientos.productos.references.VariablesProducto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//import mifarma.ptoventa.reference.*;

public class DlgListadoPedidoEspecial extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgListadoPedidoEspecial.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JScrollPane scrListaPedidos = new JScrollPane();
    private JTable tblListaPedidos = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF8 = new JLabelFunction();
    private JButtonLabel btnRelacionPedidos = new JButtonLabel();
    private JPanelHeader pnlCabecera = new JPanelHeader();
    private JTextFieldSanSerif txtFechaInicial = new JTextFieldSanSerif();
    private JLabelWhite lblFechaHasta = new JLabelWhite();
    private JTextFieldSanSerif txtFechaFin = new JTextFieldSanSerif();
    private JLabelWhite lblEstado = new JLabelWhite();
    private JComboBox cboEstado = new JComboBox();

    // DECLARACION DE CONSTANTES
    private int COL_ORDENAR = 7;
    private JButtonFunction btnBuscar = new JButtonFunction();
    private JButtonLabel btnFechaInicio = new JButtonLabel();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgListadoPedidoEspecial() {
        this(null, "", false);
    }

    public DlgListadoPedidoEspecial(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(759, 368));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Pedidos Especiales Realizados");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(5, 65, 745, 20));
        scrListaPedidos.setBounds(new Rectangle(5, 85, 745, 225));
        tblListaPedidos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaPedidos_keyPressed(e);
            }
        });
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(665, 315, 85, 20));
        lblF8.setText("[ F8 ] Ver Detalle");
        lblF8.setBounds(new Rectangle(550, 315, 105, 20));
        btnRelacionPedidos.setText("Relación de Pedidos ");
        btnRelacionPedidos.setBounds(new Rectangle(10, 0, 195, 20));
        btnRelacionPedidos.setMnemonic('R');
        btnRelacionPedidos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnRelacionPedidos_keyPressed(e);
            }
        });
        btnRelacionPedidos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionPedidos_actionPerformed(e);
            }
        });
        pnlCabecera.setBounds(new Rectangle(5, 5, 745, 55));


        txtFechaInicial.setBounds(new Rectangle(160, 20, 85, 20));
        txtFechaInicial.setLengthText(10);
        txtFechaInicial.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaInicial_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaInicial_keyReleased(e);
            }
        });
        lblFechaHasta.setText("Hasta");
        lblFechaHasta.setBounds(new Rectangle(255, 15, 30, 25));
        txtFechaFin.setBounds(new Rectangle(290, 20, 90, 20));
        txtFechaFin.setLengthText(10);

        txtFechaFin.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaFin_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaFin_keyReleased(e);
            }
        });
        lblEstado.setText("Estado");
        lblEstado.setBounds(new Rectangle(395, 20, 50, 20));
        cboEstado.setBounds(new Rectangle(440, 20, 140, 20));


        cboEstado.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cboEstado_keyPressed(e);
            }
        });
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(585, 20, 95, 20));
        btnBuscar.setMnemonic('b');
        btnBuscar.setFont(new Font("SansSerif", 1, 11));
        btnBuscar.setFocusPainted(false);

        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        btnFechaInicio.setText("Desde");
        btnFechaInicio.setMnemonic('D');
        btnFechaInicio.setBounds(new Rectangle(110, 20, 55, 15));
        btnFechaInicio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnFechaInicio_actionPerformed(e);
            }
        });
        jLabelFunction1.setBounds(new Rectangle(5, 315, 130, 20));
        jLabelFunction1.setText("[ F1 ] Pedido Matriz");
        pnlCabecera.add(btnFechaInicio, null);
        pnlCabecera.add(cboEstado, null);
        pnlCabecera.add(lblEstado, null);
        pnlCabecera.add(lblFechaHasta, null);
        pnlCabecera.add(txtFechaInicial, null);
        pnlCabecera.add(txtFechaFin, null);
        pnlCabecera.add(btnBuscar, null);
        jContentPane.add(jLabelFunction1, null);
        jContentPane.add(pnlCabecera, null);
        jContentPane.add(lblF8, null);
        jContentPane.add(lblEsc, null);
        scrListaPedidos.getViewport().add(tblListaPedidos, null);
        jContentPane.add(scrListaPedidos, null);
        pnlTitle1.add(btnRelacionPedidos, null);
        jContentPane.add(pnlTitle1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initTable();
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {


        tblListaPedidos.getTableHeader().setReorderingAllowed(false);
        tblListaPedidos.getTableHeader().setResizingAllowed(false);

        tableModel =
                new FarmaTableModel(ConstantsProducto.columnsListaPedEspeciales, ConstantsProducto.defaultValuesListaPedEspeciales,
                                    0);
        FarmaUtility.initSimpleList(tblListaPedidos, tableModel, ConstantsProducto.columnsListaPedEspeciales);
        cargar_cmbEstado();

    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void btnRelacionPedidos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaPedidos);
    }

    private void btnFechaInicio_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFechaInicial);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        busquedaPedidos();
    }

    private void btnRelacionPedidos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtFechaInicial);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void txtFechaInicial_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtFechaFin);
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        chkKeyPressed(e);
    }

    private void txtFechaFin_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(cboEstado);
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        chkKeyPressed(e);
    }

    private void cboEstado_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            btnBuscar.doClick();
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        chkKeyPressed(e);
    }

    private void txtFechaInicial_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaInicial, e);
    }

    private void txtFechaFin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaFin, e);
    }


    private void tblListaPedidos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {
            cargaListProdEspeciales(); //se carga en memoria
            nuevoPedidoMatriz();
        } else if (e.getKeyCode() == KeyEvent.VK_F8) {
            verDetallePedido();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
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

    private void busquedaPedidos() {
        if (validarCampos()) {
            txtFechaInicial.setText(txtFechaInicial.getText().trim().toUpperCase());
            txtFechaFin.setText(txtFechaFin.getText().trim().toUpperCase());
            String FechaInicio = txtFechaInicial.getText().trim();
            String FechaFin = txtFechaFin.getText().trim();
            if (FechaInicio.length() > 0 || FechaFin.length() > 0) {
                char primerkeyCharFI = FechaInicio.charAt(0);
                char ultimokeyCharFI = FechaInicio.charAt(FechaInicio.length() - 1);
                char primerkeyCharFF = FechaFin.charAt(0);
                char ultimokeyCharFF = FechaFin.charAt(FechaFin.length() - 1);

                if (!Character.isLetter(primerkeyCharFI) && !Character.isLetter(ultimokeyCharFI) &&
                    !Character.isLetter(primerkeyCharFF) && !Character.isLetter(ultimokeyCharFF)) {
                    cargaListaPedidos(FechaInicio, FechaFin);
                } else
                    FarmaUtility.showMessage(this, "Ingrese un formato valido de fechas", txtFechaInicial);
            } else
                FarmaUtility.showMessage(this, "Ingrese datos para la busqueda", txtFechaInicial);

        }
    }

    private void cargaListaPedidos(String pFechIni, String pFechFin) {
        String pEstado =
            FarmaLoadCVL.getCVLCode(ConstantsProducto.NOM_HASTABLE_CMBESTADO_PED_ESCP, cboEstado.getSelectedIndex());

        try {
            DBProducto.cargaListaPedidosEspeciales(tableModel, pFechIni, pFechFin, pEstado);

            if (tableModel.getRowCount() > 0) {
                //FarmaUtility.ordenar(tblListaPedidos, tableModel, COL_ORDENAR, FarmaConstants.ORDEN_DESCENDENTE);
                FarmaUtility.moveFocus(tblListaPedidos);
            } else
                FarmaUtility.moveFocus(txtFechaInicial);

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar la lista de pedidos : \n " + sql.getMessage(),
                                     btnRelacionPedidos);
        }
    }

    private void verDetallePedido() {
        if (tblListaPedidos.getSelectedRow() > -1) {

            int r = tblListaPedidos.getSelectedRow();
            /*VariablesProducto.vCodLocal  = "017";
        VariablesProducto.vNumPedEspecial  = "0000000003";*/

            VariablesProducto.vNumPedEspecial = FarmaUtility.getValueFieldJTable(tblListaPedidos, r, 1);
            VariablesProducto.vCodLocal = FarmaUtility.getValueFieldJTable(tblListaPedidos, r, 8);
            VariablesProducto.vEstPedEpsc = FarmaUtility.getValueFieldJTable(tblListaPedidos, r, 9);


            DlgDetallePedidoEspecial dlgDetallePedido = new DlgDetallePedidoEspecial(myParentFrame, "", true);
            dlgDetallePedido.setVisible(true);
            if (FarmaVariables.vAceptar) {
                busquedaPedidos();
            }
            VariablesProducto.vCodLocal = "";
            VariablesProducto.vNumPedEspecial = "";
            VariablesProducto.vEstPedEpsc = "";
        } else {
            FarmaUtility.showMessage(this, "Debe seleccionar un pedido", btnRelacionPedidos);
        }
    }

    private void cargarCabecera() {
        int pos = tblListaPedidos.getSelectedRow();
        /*if(pos>=0){
      /*VariablesProducto.vNumPedidoEspecial=tblListaPedidos.getValueAt(pos,0).toString();
      VariablesProducto.vFecEmi_esp=tblListaPedidos.getValueAt(pos,1).toString();
      VariablesProducto.vEstCab_esp=tblListaPedidos.getValueAt(pos,5).toString();*/
        //  }

    }

    private void cargar_cmbEstado() {
        ArrayList parametros = new ArrayList();
        FarmaLoadCVL.loadCVLFromSP(cboEstado, ConstantsProducto.NOM_HASTABLE_CMBESTADO_PED_ESCP,
                                   "PTOVENTA_MATRIZ_PED_ESPC.PED_ESPC_F_CUR_LISTA_ESTADOS", parametros, true, true);
    }

    private boolean validarCampos() {
        boolean retorno = true;
        if (!FarmaUtility.validarRangoFechas(this, txtFechaInicial, txtFechaFin, false, true, true, true))
            retorno = false;

        return retorno;
    }

    /**
     * Se crea un nuevo pedido especial en matriz
     * @AUTHOR
     * @SINCE
     * */
    private void nuevoPedidoMatriz() {

        //arreglos clone.
        VariablesProducto.vArrayProductosEspeciales.clear();
        VariablesProducto.vArrayProductosEspeciales = new ArrayList();

        VariablesProducto.vArrayListaProdsEsp.clear();
        VariablesProducto.vArrayListaProdsEsp = new ArrayList();

        VariablesProducto.vCodLocalDestino = "";
        VariablesProducto.vDescLocalDestino = "";

        VariablesProducto.vEsModificado = true;
        log.debug("F1 - VariablesInventario.vEsModificado " + VariablesProducto.vEsModificado);
        // limpiarTableModelEspecial();


        VariablesProducto.vFModificar = false;
        VariablesProducto.vFNuevo = true;


        DlgResumenPedEspecialMatriz dglResuPedidoEspecial = new DlgResumenPedEspecialMatriz(myParentFrame, "", true);
        dglResuPedidoEspecial.setVisible(true);

        if (FarmaVariables.vAceptar) {
            //obteniendo fecha actual
            Calendar calendario = Calendar.getInstance();
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            String fecFin = formato.format(calendario.getTime());
            calendario.add(Calendar.DAY_OF_YEAR, -30); //restando 30 dias
            String fecIni = formato.format(calendario.getTime());

            // txtFecIni.setText(fecIni);
            // txtFecFin.setText(fecFin);

            cargaListaPedidos(fecIni, fecFin);
        }


        log.debug("seteando el flag de cerrado del dialogo nuevo pedido especial");
        //VariablesInventario.vFlagF2Nuevo = false;


    }

    /**
     * Carga los productos para Pedido Especial
     * @AUTHOR JCORTEZ
     * @SINCE  04.02.2010
     */
    private void cargaListProdEspeciales() {
        try {
            String filtro = "%";
            VariablesProducto.tableModelEspecialMatriz.clearTable();
            DBProducto.cargaListaProdEspecialMatriz(VariablesProducto.tableModelEspecialMatriz, filtro);
            Collections.sort(VariablesProducto.tableModelEspecialMatriz.data, new FarmaTableComparator(2, true));

        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ha ocurrido un error al listar los productos.\n" +
                    e.getMessage(), txtFechaInicial);

        }

    }

}
