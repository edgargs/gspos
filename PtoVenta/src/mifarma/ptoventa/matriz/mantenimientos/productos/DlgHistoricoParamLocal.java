package mifarma.ptoventa.matriz.mantenimientos.productos;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

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

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.matriz.mantenimientos.productos.references.ConstantsProducto;
import mifarma.ptoventa.matriz.mantenimientos.productos.references.DBProducto;
import mifarma.ptoventa.matriz.mantenimientos.productos.references.VariablesProducto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgHistoricoParamLocal extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgHistoricoParamLocal.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JScrollPane scrListaPedidos = new JScrollPane();
    private JTable tblListaPedidos = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JButtonLabel btnRelacionPedidos = new JButtonLabel();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgHistoricoParamLocal() {
        this(null, "", false);
    }

    public DlgHistoricoParamLocal(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(487, 276));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Historico de Parametros");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(10, 10, 465, 20));
        scrListaPedidos.setBounds(new Rectangle(10, 30, 465, 185));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(390, 220, 85, 20));
        btnRelacionPedidos.setText("Relación de Locales ");
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
        scrListaPedidos.getViewport().add(tblListaPedidos, null);
        jContentPane.add(lblEsc, null);
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
                new FarmaTableModel(ConstantsProducto.columnsHistoParam, ConstantsProducto.defaultValuesHistoParam, 0);
        FarmaUtility.initSimpleList(tblListaPedidos, tableModel, ConstantsProducto.columnsHistoParam);
        cargarHistorico();
    }

    private void cargarHistorico() {
        try {
            DBProducto.cargaHistoricoParamLocal(tableModel, VariablesProducto.cCodProd);
            //FarmaUtility.ordenar(tblListaPedidos,tableModel,0,FarmaConstants.ORDEN_DESCENDENTE);
            //FarmaUtility.moveFocus(btnRelacionPedidos);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar el historico : \n " + sql.getMessage(),
                                     tblListaPedidos);
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

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaPedidos, null, 0);

        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {

        } else if (e.getKeyCode() == KeyEvent.VK_F8) {

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


}
