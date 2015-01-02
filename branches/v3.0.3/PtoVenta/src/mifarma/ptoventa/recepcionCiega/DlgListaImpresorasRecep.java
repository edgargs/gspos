package mifarma.ptoventa.recepcionCiega;


import com.gs.mifarma.componentes.JLabelFunction;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.recepcionCiega.reference.ConstantsRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.DBRecepCiega;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgListaImpresorasRecep extends JDialog {

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgListaImpresorasRecep.class);

    FarmaTableModel tableModelImpresoras;
    Frame myParentFrame;
    private boolean esNotaCredito = false;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JPanel jPanel1 = new JPanel();
    private JButton btnListaUsuarioCaja = new JButton();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblListaImpresoras = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgListaImpresorasRecep() {
        this(null, "", false);
    }

    public DlgListaImpresorasRecep(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        this.esNotaCredito = esNotaCredito;
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
        this.setSize(new Dimension(446, 241));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Seleccione impresora");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setBackground(Color.white);
        jContentPane.setSize(new Dimension(435, 208));
        jContentPane.setLayout(null);
        jPanel1.setBounds(new Rectangle(20, 20, 390, 30));
        jPanel1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        jPanel1.setBackground(new Color(255, 130, 14));
        jPanel1.setLayout(null);
        btnListaUsuarioCaja.setText("Lista de impresoras disponibles :");
        btnListaUsuarioCaja.setBounds(new Rectangle(10, 5, 220, 20));
        btnListaUsuarioCaja.setMnemonic('l');
        btnListaUsuarioCaja.setBackground(new Color(255, 130, 14));
        btnListaUsuarioCaja.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnListaUsuarioCaja.setBorderPainted(false);
        btnListaUsuarioCaja.setContentAreaFilled(false);
        btnListaUsuarioCaja.setDefaultCapable(false);
        btnListaUsuarioCaja.setFocusPainted(false);
        btnListaUsuarioCaja.setHorizontalAlignment(SwingConstants.LEFT);
        btnListaUsuarioCaja.setRequestFocusEnabled(false);
        btnListaUsuarioCaja.setForeground(Color.white);
        btnListaUsuarioCaja.setFont(new Font("SansSerif", 1, 11));
        btnListaUsuarioCaja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnListaUsuarioCaja_keyPressed(e);
            }
        });
        btnListaUsuarioCaja.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListaUsuarioCaja_actionPerformed(e);
            }
        });
        jScrollPane1.setBounds(new Rectangle(20, 50, 390, 125));
        tblListaImpresoras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaImpresoras_keyPressed(e);
            }
        });
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(320, 185, 85, 20));
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(210, 185, 100, 20));
        jPanel1.add(btnListaUsuarioCaja, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jScrollPane1.getViewport();
        jScrollPane1.getViewport();
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(jPanel1, null);
        jScrollPane1.getViewport().add(tblListaImpresoras, null);
        jContentPane.add(jScrollPane1, null);
        //this.getContentPane().add(jContentPane, null);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTableImpresoras();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTableImpresoras() {
        tableModelImpresoras =
                new FarmaTableModel(ConstantsRecepCiega.columnsListaImpresoras, ConstantsRecepCiega.defaultValuesListaImpresoras,
                                    0);
        FarmaUtility.initSimpleList(tblListaImpresoras, tableModelImpresoras,
                                    ConstantsRecepCiega.columnsListaImpresoras);
        cargaListaImpresoras();
    }

    private void cargaListaImpresoras() {
        try {
            DBRecepCiega.getListaImpresorasDisp(tableModelImpresoras);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar la lista de impresoras : \n" +
                    sql.getMessage(), tblListaImpresoras);
        }
        if (tblListaImpresoras.getRowCount() > 0) {
            FarmaUtility.moveFocusJTable(tblListaImpresoras);
        }

    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void btnListaUsuarioCaja_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(btnListaUsuarioCaja);
    }

    private void btnListaUsuarioCaja_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(btnListaUsuarioCaja);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaImpresoras, null, 0);

        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (tieneRegistroSeleccionado(tblListaImpresoras)) {
                VariablesCaja.vSecImprLocalGuia =
                        tblListaImpresoras.getValueAt(tblListaImpresoras.getSelectedRow(), 0).toString().trim();
                VariablesCaja.vNumComp =
                        tblListaImpresoras.getValueAt(tblListaImpresoras.getSelectedRow(), 2).toString().trim();

                DlgModNroComp dlgModNroComp = new DlgModNroComp(this.myParentFrame, "", true);
                dlgModNroComp.setVisible(true);

                if (FarmaVariables.vAceptar) {
                    cerrarVentana(true);
                } else {
                    cerrarVentana(false);
                }

            } else {
                FarmaUtility.showMessage(this, "Debe selecciona un registro", tblListaImpresoras);
            }

        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void tblListaImpresoras_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private boolean tieneRegistroSeleccionado(JTable pTabla) {
        boolean rpta = false;

        if (pTabla.getSelectedRow() != -1) {
            rpta = true;
        }
        return rpta;
    }

}
