package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JButtonFunction;
import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

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

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgPedidoReposicionRealizados extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgPedidoReposicionRealizados.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;
    String tipFiltro = "N";

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JScrollPane scrListaPedidos = new JScrollPane();
    private JTable tblListaPedidos = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF8 = new JLabelFunction();
    private JButtonLabel btnRelacionPedidos = new JButtonLabel();
    private JLabelFunction lblF2 = new JLabelFunction();
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JButtonLabel lblFecha = new JButtonLabel();

    private JLabelWhite jLabelWhite2 = new JLabelWhite();
    private JButtonFunction btnBuscar = new JButtonFunction();
    private JTextField jTextField1 = new JTextField();
    private JTextField txtFecInicio = new JTextField();
    private JTextField txtFecFin = new JTextField();
    private JLabelFunction lblF9 = new JLabelFunction();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgPedidoReposicionRealizados() {
        this(null, "", false);
    }

    public DlgPedidoReposicionRealizados(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(438, 574));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Pedido de Reposición Realizados");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(5, 50, 420, 20));
        scrListaPedidos.setBounds(new Rectangle(5, 70, 420, 440));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(330, 520, 85, 20));
        lblF8.setText("[ F8 ] Ver Detalle");
        lblF8.setBounds(new Rectangle(105, 520, 105, 20));
        btnRelacionPedidos.setText("Relación de Pedidos de Reposición");
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
        lblF2.setText("[ F2 ] Nuevo");
        lblF2.setBounds(new Rectangle(10, 520, 90, 20));
        jPanelHeader1.setBounds(new Rectangle(5, 5, 420, 40));
        lblFecha.setText("Rango de Fechas :");
        lblFecha.setBounds(new Rectangle(5, 10, 110, 25));
        lblFecha.setMnemonic('D');
        lblFecha.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblFecha_actionPerformed(e);
            }
        });
        //jLabelWhite1.
        jLabelWhite2.setText("Fec Fin :");
        jLabelWhite2.setBounds(new Rectangle(160, 10, 55, 20));
        jLabelWhite2.setVisible(false);
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(325, 10, 85, 20));
        btnBuscar.setFont(new Font("Arial", 1, 11));
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        txtFecInicio.setBounds(new Rectangle(110, 10, 100, 20));
        txtFecInicio.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFecInicio_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFecInicio_keyReleased(e);
            }

            public void keyTyped(KeyEvent e) {
                txtFecInicio_keyTyped(e);
            }
        });

        txtFecFin.setBounds(new Rectangle(220, 10, 95, 20));
        txtFecFin.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFecFin_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFecFin_keyReleased(e);
            }

            public void keyTyped(KeyEvent e) {
                txtFecFin_keyTyped(e);
            }
        });
        lblF9.setBounds(new Rectangle(215, 520, 110, 20));
        lblF9.setText("[ F9 ] Quitar Filtro");
        jPanelHeader1.add(txtFecFin, null);
        jPanelHeader1.add(txtFecInicio, null);
        jPanelHeader1.add(btnBuscar, null);
        jPanelHeader1.add(jLabelWhite2, null);
        jPanelHeader1.add(lblFecha, null);
        jContentPane.add(lblF9, null);
        jContentPane.add(jPanelHeader1, null);
        jContentPane.add(lblF2, null);
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
        tableModel =
                new FarmaTableModel(ConstantsInventario.columnsListaPedidosPedidoReposicionRealizados, ConstantsInventario.defaultValuesListaPedidosPedidoReposicionRealizados,
                                    0);
        FarmaUtility.initSimpleList(tblListaPedidos, tableModel,
                                    ConstantsInventario.columnsListaPedidosPedidoReposicionRealizados);
        cargaListaPedidos();
        lblF9.setVisible(false);
        inicioValidacionCreacionPedReposicion();
        FarmaUtility.moveFocus(txtFecInicio);
    }

    private void cargaListaPedidos() {
        String fecIni = txtFecInicio.getText().trim();
        String fecFin = txtFecFin.getText().trim();
        log.debug("datos " + fecIni + " " + fecFin);
        try {

            DBInventario.cargaListaPedidoReposicion(tableModel, fecIni, fecFin, tipFiltro);
            FarmaUtility.ordenar(tblListaPedidos, tableModel, 0, FarmaConstants.ORDEN_DESCENDENTE);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar la lista de pedidos : \n " + sql.getMessage(),
                                     btnRelacionPedidos);
        }
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void btnRelacionPedidos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(btnRelacionPedidos);
    }

    private void btnRelacionPedidos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtFecInicio);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaPedidos, null, 0);

        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {
            if (lblF2.isVisible()) {
                if (!FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_SUPERVISOR_VENTAS)) {
                    if (FarmaVariables.vEconoFar_Matriz)
                        FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, btnRelacionPedidos);
                    else {
                        DlgPedidoReposicionNuevo dlgPedidoReposicionNuevo =
                            new DlgPedidoReposicionNuevo(myParentFrame, "", true);
                        dlgPedidoReposicionNuevo.setVisible(true);
                        if (FarmaVariables.vAceptar)
                            cargaListaPedidos();
                    }
                } else {
                    FarmaUtility.showMessage(this, "No posee privilegios suficientes para acceder a esta opción",
                                             null);
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F8) {
            verDetallePedido();
        } else if (e.getKeyCode() == KeyEvent.VK_F9) {
            txtFecInicio.setText("");
            txtFecFin.setText("");
            lblF9.setVisible(false);
            tipFiltro = "N";
            cargaListaPedidos();
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

    private void verDetallePedido() {
        if (tblListaPedidos.getSelectedRow() > -1) {
            cargarCabecera();
            DlgPedidoReposicionDetalle dlgPedidoReposicionDetalle =
                new DlgPedidoReposicionDetalle(myParentFrame, "", true);
            dlgPedidoReposicionDetalle.setVisible(true);
        } else {
            FarmaUtility.showMessage(this, "Debe seleccionar un pedido", btnRelacionPedidos);
        }
    }

    private void cargarCabecera() {
        int pos = tblListaPedidos.getSelectedRow();
        VariablesInventario.vNroPed_PedRep = tblListaPedidos.getValueAt(pos, 0).toString();
        VariablesInventario.vFecPed_PedRep = tblListaPedidos.getValueAt(pos, 1).toString();
        /* VariablesInventario.vRotProm_PedRep = tblListaPedidos.getValueAt(pos,3).toString();
    VariablesInventario.vMinDias_PedRep = tblListaPedidos.getValueAt(pos,4).toString();
    VariablesInventario.vMaxDias_PedRep = tblListaPedidos.getValueAt(pos,5).toString();*/
    }

    /**
     * Verifica Si se puede crear los pedido de reposicion
     */
    private void inicioValidacionCreacionPedReposicion() {
        String pIndCreaReposicion = "";
        lblF2.setVisible(false);
        try {
            pIndCreaReposicion = DBInventario.getIndPedReposicionAutomatico();

        } catch (SQLException sql) {
            pIndCreaReposicion = "N";
            log.error("", sql);
            FarmaUtility.showMessage(this,
                                     "Ocurrió un error al cargar parametro para crear PedRep: \n " + sql.getMessage(),
                                     btnRelacionPedidos);
        }

        if (pIndCreaReposicion.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            lblF2.setVisible(true);
    }

    private void txtFecInicio_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtFecInicio.getText().trim().length() > 9) {
                FarmaUtility.moveFocus(txtFecFin);
            }
        }
        chkKeyPressed(e);
    }

    private void txtFecFin_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtFecFin.getText().trim().length() > 9) {
                FarmaUtility.moveFocus(btnBuscar);
                tipFiltro = "S";
                btnBuscar.doClick();
            }
        }
        chkKeyPressed(e);
    }

    /*  private void btnBuscar_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
             log.debug("x");
        if(validarCampos()){
        cargaListaPedidos("S");
            log.debug("xx ");
        }
         }else
         chkKeyPressed(e);
    }*/


    /**
     * @AUTHOR JCORTEZ
     * @SINCE 16/10/2008
     * eventos fecha
     * */

    private void txtFecInicio_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFecInicio, e);
    }

    private void txtFecFin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFecFin, e);
    }

    private void txtFecInicio_keyTyped(KeyEvent e) {
        FarmaUtility.dateComplete(txtFecInicio, e);
    }

    private void txtFecFin_keyTyped(KeyEvent e) {
        FarmaUtility.dateComplete(txtFecFin, e);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        if (validarCampos()) {
            cargaListaPedidos();
            FarmaUtility.moveFocus(txtFecInicio);
            if (tblListaPedidos.getRowCount() > 0)
                lblF9.setVisible(true);
        }

    }

    /**
     * @AUTHOR JCORTEZ
     * @SINCE 16/10/2008
     * Se valida el ingreso de los campos fecha
     * */
    private boolean validarCampos() {
        boolean retorno = true;
        if (txtFecInicio.getText().trim().equals("")) {
            retorno = false;
            FarmaUtility.showMessage(this, "Ingrese Fecha de Inicio.", txtFecInicio);
        } else if (txtFecFin.getText().trim().equals("")) {
            retorno = false;
            FarmaUtility.showMessage(this, "Ingrese Fecha de Fin.", txtFecFin);
        } else if (!FarmaUtility.validaFecha(txtFecInicio.getText(), "dd/MM/yyyy")) {
            retorno = false;
            FarmaUtility.showMessage(this, "Formato Incorrecto de Fecha Inicio.", txtFecInicio);
        } else if (!FarmaUtility.validaFecha(txtFecFin.getText(), "dd/MM/yyyy")) {
            retorno = false;
            FarmaUtility.showMessage(this, "Formato Incorrecto de Fecha Fin.", txtFecFin);
        } else if (!FarmaUtility.validarRangoFechas(this, txtFecInicio, txtFecFin, false, true, true, true))
            retorno = false;

        return retorno;
    }

    private void lblFecha_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFecInicio);

    }
}
