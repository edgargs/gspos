package mifarma.ptoventa.reportes;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.electronico.UtilityImpCompElectronico;

import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reportes.reference.ConstantsReporte;
import mifarma.ptoventa.reportes.reference.DBReportes;
import mifarma.ptoventa.reportes.reference.VariablesReporte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgComprobantes extends JDialog {
    /* ************************************************************************ */
    /*                          DECLARACION PROPIEDADES                         */
    /* ************************************************************************ */
    private static final Logger log = LoggerFactory.getLogger(DlgComprobantes.class);

    FarmaTableModel tableModelComprobantes;
    FarmaTableModel tableModelDetalleComprobantes;
    private Frame myParentFrame;
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private GridLayout gridLayout1 = new GridLayout();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JScrollPane srcComprobantes = new JScrollPane();
    private JPanelTitle jPanelTitle2 = new JPanelTitle();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JScrollPane srcDetalleComprobante = new JScrollPane();
    private JTable tblComprobantes = new JTable();
    private JTable tblDetalleComprobantes = new JTable();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JLabelFunction lblReimprimir = new JLabelFunction();
    private JButtonLabel btnComprobantes = new JButtonLabel();
    private JButtonLabel btnDetalleComprobante = new JButtonLabel();

    /* ************************************************************************ */
    /*                          CONSTRUCTORES                                   */
    /* ************************************************************************ */

    public DlgComprobantes() {
        this(null, "", false);
    }

    public DlgComprobantes(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(746, 500));
        this.getContentPane().setLayout(gridLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Comprobantes del Pedido");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jPanelTitle1.setBounds(new Rectangle(5, 10, 720, 25));
        srcComprobantes.setBounds(new Rectangle(5, 30, 720, 155));
        jPanelTitle2.setBounds(new Rectangle(5, 195, 720, 25));
        jScrollPane2.setBounds(new Rectangle(0, 25, 685, 210));
        srcDetalleComprobante.setBounds(new Rectangle(5, 220, 720, 210));
        tblComprobantes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                tblComprobantes_keyReleased(e);
            }

            public void keyPressed(KeyEvent e) {
                tblComprobantes_keyPressed(e);
            }
        });
        tblDetalleComprobantes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblDetalleComprobantes_keyPressed(e);
            }
        });
        jLabelFunction1.setBounds(new Rectangle(605, 435, 117, 19));
        jLabelFunction1.setText("[ESC]Escape");

        lblReimprimir.setBounds(new Rectangle(455, 435, 117, 19));
        lblReimprimir.setText("[F11] Re-imprimir");

        btnComprobantes.setText("Comprobantes");
        btnComprobantes.setBounds(new Rectangle(10, 5, 100, 15));
        btnComprobantes.setMnemonic('c');
        btnComprobantes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnComprobantes_actionPerformed(e);
            }
        });
        btnDetalleComprobante.setText("Detalle del Comprobante");
        btnDetalleComprobante.setBounds(new Rectangle(10, 5, 160, 20));
        btnDetalleComprobante.setMnemonic('D');
        btnDetalleComprobante.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDetalleComprobante_actionPerformed(e);
            }
        });
        jPanelTitle2.add(btnDetalleComprobante, null);
        jPanelTitle2.add(jScrollPane2, null);
        srcDetalleComprobante.getViewport().add(tblDetalleComprobantes, null);
        jPanelWhite1.add(jLabelFunction1, null);
        jPanelWhite1.add(lblReimprimir, null);
        jPanelWhite1.add(srcDetalleComprobante, null);
        jPanelWhite1.add(jPanelTitle2, null);
        srcComprobantes.getViewport().add(tblComprobantes, null);
        jPanelWhite1.add(srcComprobantes, null);
        jPanelTitle1.add(btnComprobantes, null);
        jPanelWhite1.add(jPanelTitle1, null);
        this.getContentPane().add(jPanelWhite1, null);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initTableListaComprobantes();
        FarmaVariables.vAceptar = false;
        lblReimprimir.setVisible(false);
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTableListaComprobantes() {
        tableModelComprobantes =
                new FarmaTableModel(ConstantsReporte.columnsListaComprobantes, ConstantsReporte.defaultValuesListaComprobantes,
                                    0);
        FarmaUtility.initSimpleList(tblComprobantes, tableModelComprobantes,
                                    ConstantsReporte.columnsListaComprobantes);
    }

    private void initTableListaDetalleComprobantes() {
        tableModelDetalleComprobantes =
                new FarmaTableModel(ConstantsReporte.columnsListaComprobantesDetalle, ConstantsReporte.defaultValuesListaComprobantesDetalle,
                                    0);
        FarmaUtility.initSimpleList(tblDetalleComprobantes, tableModelDetalleComprobantes,
                                    ConstantsReporte.columnsListaComprobantesDetalle);
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(tblComprobantes);
        buscaComprobantes(VariablesReporte.vCorrelativo);
        log.debug("Correlativo" + VariablesReporte.vCorrelativo);
        listaDetalleComprobantes();

    }
    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() != KeyEvent.VK_ESCAPE) {
            if (tblComprobantes.getRowCount() > 0) {
                VariablesReporte.vNComprobante = ((String)tblComprobantes.getValueAt(tblComprobantes.getSelectedRow(), 1)).trim();
                log.info("variables reporte vNComprobante" + VariablesReporte.vNComprobante);
                cargaDetalleComprobantesVenta();
            }
        }
        
    }

    private void tblComprobantes_keyReleased(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblComprobantes_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
        // KMONCADA - REIMPRESION DE COMPROBANTE ELECTRONICO
        if (UtilityPtoVenta.verificaVK_F11(e) && lblReimprimir.isVisible()) {
            if (cargarLogeoAdmLocal()) {
                String nroPedidoVta = VariablesReporte.vCorrelativo;
                String secCompPago = (String)tableModelComprobantes.getValueAt(tblComprobantes.getSelectedRow(), 11);
                try {
                    UtilityImpCompElectronico impresionComprobante = new UtilityImpCompElectronico();
                    impresionComprobante.printDocumento(nroPedidoVta, secCompPago, true, false);
                    FarmaUtility.aceptarTransaccion();
                    FarmaUtility.showMessage(this, "Reimpresión realizada con exito.\nRecoja documento.",
                                             tblComprobantes);
                } catch (Exception ex) {
                    log.error(ex.getMessage());
                }
            }
        }
    }

    /** Permite mover el foco a la tabla comprobantesw y tabla detalle de comprobantes
     * @author: JCORTEZ
     * @since: 04/07/07
     */
    private void btnComprobantes_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblComprobantes);
    }

    private void btnDetalleComprobante_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblDetalleComprobantes);
    }

    private void tblDetalleComprobantes_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void listaDetalleComprobantes() {
        if (tblComprobantes.getRowCount() > 0) {
            initTableListaDetalleComprobantes();
            cargaDetalleComprobantesVenta();
        }
    }

    private void buscaComprobantes(String pCodigo) {
        VariablesReporte.vCorrelativo = pCodigo;
        cargaComprobantesVenta();
    }

    private void cargaComprobantesVenta() {
        try {
            log.info(VariablesReporte.vCorrelativo);
            DBReportes.obtieneComprobantes_Venta(tableModelComprobantes, VariablesReporte.vCorrelativo);
            if (tblComprobantes.getRowCount() > 0)
                FarmaUtility.ordenar(tblComprobantes, tableModelComprobantes, 0, FarmaConstants.ORDEN_ASCENDENTE);
        } catch (Exception sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar los Comprobantes Venta:\n " + sql.getMessage(), null);
            cerrarVentana(false);
        }
    }

    private void cargaDetalleComprobantesVenta() {
        try{
            String tipcCompr = ((String)tblComprobantes.getValueAt(tblComprobantes.getSelectedRow(), 10)).trim();
            log.info("Nro de comprobantes" + " " + VariablesReporte.vNComprobante);
            DBReportes.obtieneComprobantes_Venta_Detalle(tableModelDetalleComprobantes, VariablesReporte.vNComprobante, VariablesReporte.vCorrelativo, tipcCompr);
            FarmaUtility.ordenar(tblDetalleComprobantes, tableModelDetalleComprobantes, 0, FarmaConstants.ORDEN_ASCENDENTE);
            // KMONCADA 15.12.2014
            String flgCompElectronico = ((String)tblComprobantes.getValueAt(tblComprobantes.getSelectedRow(), 12)).trim();
            if ("1".equalsIgnoreCase(flgCompElectronico)){
                lblReimprimir.setVisible(true);
            }else{
                lblReimprimir.setVisible(false);
            }
        }catch (Exception sql){
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar los Detalles de los Comprobantes Venta :\n" + sql.getMessage(), null);
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /**
     * Pedir clave de operador para que pueda anular la recarga virtual.
     * @author ASOSA
     * @since 24/07/2014
     * @return
     */
    private boolean cargarLogeoAdmLocal() {
        String numsec = FarmaVariables.vNuSecUsu;
        String idusu = FarmaVariables.vIdUsu;
        String nomusu = FarmaVariables.vNomUsu;
        String apepatusu = FarmaVariables.vPatUsu;
        String apematusu = FarmaVariables.vMatUsu;

        try {
            DlgLogin dlgLogin = new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
            dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
            dlgLogin.setVisible(true);
        } catch (Exception e) {
            FarmaVariables.vAceptar = false;
            log.error("ERROR", e);
            FarmaUtility.showMessage(this, "Ocurrio un error al validar rol de usuario \n : " + e.getMessage(), null);
        } finally {
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
        }
        return FarmaVariables.vAceptar;
    }


}
