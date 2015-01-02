package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.FacadeInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.recetario.DlgResumenRecetarioMagistral;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgMercaderiaDirectaLista extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgResumenRecetarioMagistral.class);

    FarmaTableModel tableModelDocumentosResumen;
    Frame myParentFrame;
    //boolean pAceptar = false;
    ArrayList arrayProductos = new ArrayList();
    ArrayList arrayDocumento = new ArrayList();

    String vEstadoNota;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JScrollPane scrResumenOrden = new JScrollPane();
    private JPanel pnlHeader = new JPanel();
    private JButton btnDocumResumen = new JButton();
    private JTable tblResumenOrden = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabel txtNombreDoc = new JLabel();
    private JLabelFunction lblEnter = new JLabelFunction();
    private FacadeInventario facadeInventario = new FacadeInventario();
    private JLabelWhite lblEstado = new JLabelWhite();
    private JLabelWhite txtEstado = new JLabelWhite();

    private final int COL_SEC_RECEP = 12;

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgMercaderiaDirectaLista() {
        this(null, "", false);
    }

    public DlgMercaderiaDirectaLista(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(705, 395));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Lista Ord. Comp. - Mercaderia Directa");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setLayout(null);
        jContentPane.setBackground(Color.white);
        jContentPane.setLayout(null);
        jContentPane.setSize(new Dimension(678, 395));
        jContentPane.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                jContentPane_focusLost(e);
            }
        });
        jContentPane.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jContentPane_keyPressed(e);
            }
        });
        scrResumenOrden.setBounds(new Rectangle(10, 45, 660, 235));
        scrResumenOrden.setBackground(new Color(255, 130, 14));
        scrResumenOrden.setBorder(BorderFactory.createLineBorder(SystemColor.windowText, 1));
        scrResumenOrden.setFocusable(false);
        pnlHeader.setBounds(new Rectangle(10, 20, 660, 25));
        pnlHeader.setBackground(new Color(255, 130, 14));
        pnlHeader.setLayout(null);
        btnDocumResumen.setText("Resumen Documento Ord. de Compra");
        btnDocumResumen.setBounds(new Rectangle(10, 0, 240, 25));
        btnDocumResumen.setBackground(new Color(255, 130, 14));
        btnDocumResumen.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnDocumResumen.setBorderPainted(false);
        btnDocumResumen.setContentAreaFilled(false);
        btnDocumResumen.setDefaultCapable(false);
        btnDocumResumen.setFocusPainted(false);
        btnDocumResumen.setFont(new Font("SansSerif", 1, 12));
        btnDocumResumen.setForeground(Color.white);
        btnDocumResumen.setHorizontalAlignment(SwingConstants.LEFT);
        btnDocumResumen.setMnemonic('r');
        btnDocumResumen.setRequestFocusEnabled(false);
        btnDocumResumen.setFocusable(false);

        btnDocumResumen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDocumResumen_actionPerformed(e);
            }
        });
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(580, 325, 85, 20));

        lblEsc.setFocusable(false);
        lblEsc.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                lblEsc_keyPressed(e);
            }
        });
        txtNombreDoc.setBounds(new Rectangle(170, 40, 77, 20));
        txtNombreDoc.setSize(new Dimension(77, 20));
        txtNombreDoc.setMinimumSize(new Dimension(4, 20));
        txtNombreDoc.setPreferredSize(new Dimension(4, 20));


        lblEnter.setBounds(new Rectangle(10, 305, 160, 20));
        lblEnter.setText("[ENTER ] Seleccionar");
        lblEnter.setFocusable(false);
        lblEnter.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                lblEnter_keyPressed(e);
            }
        });
        lblEstado.setText("ESTADO :");
        lblEstado.setBounds(new Rectangle(515, 40, 57, 20));
        lblEstado.setSize(new Dimension(57, 20));
        txtEstado.setBounds(new Rectangle(580, 40, 70, 20));
        txtEstado.setSize(new Dimension(70, 20));
        txtEstado.setText("ACTIVO");
        scrResumenOrden.getViewport();
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(lblEnter, null);
        jContentPane.add(lblEsc, null);
        scrResumenOrden.getViewport().add(tblResumenOrden, null);
        jContentPane.add(scrResumenOrden, null);

        pnlHeader.add(btnDocumResumen, null);
        jContentPane.add(pnlHeader, null);

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
        tableModelDocumentosResumen =
                new FarmaTableModel(ConstantsInventario.columnsListaDocumentos, ConstantsInventario.defaultListaDocumentos,
                                    0);
        FarmaUtility.initSimpleList(tblResumenOrden, tableModelDocumentosResumen,
                                    ConstantsInventario.columnsListaDocumentos);

    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void btnDocumResumen_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(btnDocumResumen);
    }

    private void lblEnter_keyPressed(KeyEvent e) {
        if (lblEnter.isEnabled() && e.getKeyCode() == KeyEvent.VK_ENTER) {
            cerrarVentana(false);
        } else if (lblEsc.isEnabled() && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(true);
        }

    }

    private void jContentPane_focusLost(FocusEvent e) {
        jContentPane.grabFocus();
    }

    private void jContentPane_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void lblEsc_keyPressed(KeyEvent e) {
        if (lblEsc.isEnabled() && e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(true);
    }

    private void this_windowOpened(WindowEvent e) {
        mostrarDatos();
        jContentPane.grabFocus();
        FarmaVariables.vAceptar = false;
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblResumenOrden, null, 0);
        if (lblEnter.isEnabled() && e.getKeyCode() == KeyEvent.VK_ENTER && tblResumenOrden.getSelectedRow() >= 0) {
            cargarRows();
        } else if (lblEsc.isEnabled() && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(true);
        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", btnDocumResumen);
    }
    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void mostrarDatos() {
        tableModelDocumentosResumen.clearTable();
        //tableModelDocumentosResumen.data = VariablesInventario.vArrayOrdenCompraDetalle;

        arrayDocumento =
                facadeInventario.listarDocumRecep(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodCia, FarmaVariables.vCodLocal,
                                                  VariablesInventario.vNumOrdenCompra,
                                                  VariablesInventario.vCodProveedor);
        if (arrayDocumento.size() > 0) {
            tableModelDocumentosResumen.data = arrayDocumento;
        } else {
            FarmaUtility.showMessage(this, "No tiene registros.", null);
            cerrarVentana(true);
        }

    }

    public void cargarRows() {
        VariablesInventario.vNumOrdenCompra =
                tableModelDocumentosResumen.getValueAt(tblResumenOrden.getSelectedRow(), 0).toString();
        VariablesInventario.vNumGuia =
                tableModelDocumentosResumen.getValueAt(tblResumenOrden.getSelectedRow(), 1).toString();
        VariablesInventario.vEstadoDoc =
                tableModelDocumentosResumen.getValueAt(tblResumenOrden.getSelectedRow(), 9).toString();
        VariablesInventario.vTipDocum =
                tableModelDocumentosResumen.getValueAt(tblResumenOrden.getSelectedRow(), 10).toString();
        VariablesInventario.vCodProv =
                tableModelDocumentosResumen.getValueAt(tblResumenOrden.getSelectedRow(), 11).toString();
        VariablesInventario.vSecRecepcion =
                tableModelDocumentosResumen.getValueAt(tblResumenOrden.getSelectedRow(), COL_SEC_RECEP).toString();
        cargarData();
    }

    private void cargarData() {
        DlgMercaderiaDirectaResumen dlgMercaderiaDirecta = new DlgMercaderiaDirectaResumen(myParentFrame, "", true);
        dlgMercaderiaDirecta.setFacade(facadeInventario);
        dlgMercaderiaDirecta.setVisible(true);

    }

    void setFacade(FacadeInventario facadeInventario) {
        this.facadeInventario = facadeInventario;
    }

}
