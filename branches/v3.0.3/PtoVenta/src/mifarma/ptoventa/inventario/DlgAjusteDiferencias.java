package mifarma.ptoventa.inventario;


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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgAjusteDiferencias extends JDialog {
    // **************************************************************************
    // Constructores
    // **************************************************************************
    private static final Logger log = LoggerFactory.getLogger(DlgAjusteDiferencias.class);

    Frame myParentFrame;

    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnlHeader1 = new JPanelHeader();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JLabelWhite lblProducto_T = new JLabelWhite();
    private JLabelWhite lblUnidadActual_T = new JLabelWhite();
    private JLabelWhite lblLaboratorio_T = new JLabelWhite();
    private JLabelWhite lblProducto = new JLabelWhite();
    private JLabelWhite lblUnidad = new JLabelWhite();
    private JLabelWhite lblLaboratorio = new JLabelWhite();
    private JButtonLabel btnNumeroEntrega = new JButtonLabel();
    private JLabelWhite lblCantGuia_T = new JLabelWhite();
    private JTextFieldSanSerif txtStockModifEntero = new JTextFieldSanSerif();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JTextFieldSanSerif txtNumeroEntrega = new JTextFieldSanSerif();
    private JLabelWhite lblStock_T = new JLabelWhite();
    private JLabelWhite lblStock = new JLabelWhite();
    private JLabelWhite lblUnidadVenta = new JLabelWhite();
    private JLabelWhite lblCantGuia = new JLabelWhite();
    private JLabelWhite lblCantAfectada_T = new JLabelWhite();
    private JLabelWhite lblCantAfectada = new JLabelWhite();
    private JButton btnBuscar = new JButton();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JLabelWhite jLabelWhite2 = new JLabelWhite();
    private JButtonLabel btnCantModif = new JButtonLabel();
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JPanelTitle jPanelTitle2 = new JPanelTitle();
    private JLabelWhite lblNumNotaT = new JLabelWhite();
    private JLabelWhite lblNumNota = new JLabelWhite();
    private JLabelWhite lblNumGuia_T = new JLabelWhite();
    private JLabelWhite lblNumGuia = new JLabelWhite();
    private JLabelWhite lblNumEntrega_T = new JLabelWhite();
    private JLabelWhite lblNumEntrega = new JLabelWhite();
    private JLabelWhite lblPos_T = new JLabelWhite();
    private JLabelWhite lblPos = new JLabelWhite();

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    public DlgAjusteDiferencias() {
        this(null, "", false);
    }

    public DlgAjusteDiferencias(Frame parent, String title, boolean modal) {
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
    // Método "initialize()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(459, 416));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Ajuste por Diferencias");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlHeader1.setBounds(new Rectangle(15, 15, 425, 115));
        pnlTitle1.setBounds(new Rectangle(15, 135, 425, 50));
        pnlTitle1.setBackground(Color.white);
        pnlTitle1.setBorder(BorderFactory.createLineBorder(new Color(250, 133, 14), 1));
        lblProducto_T.setText("Producto:");
        lblProducto_T.setBounds(new Rectangle(20, 15, 90, 15));
        lblUnidadActual_T.setText("Unidad Actual:");
        lblUnidadActual_T.setBounds(new Rectangle(20, 40, 90, 15));
        lblLaboratorio_T.setText("Laboratorio:");
        lblLaboratorio_T.setBounds(new Rectangle(20, 65, 90, 15));
        lblProducto.setBounds(new Rectangle(120, 15, 300, 15));
        lblProducto.setFont(new Font("SansSerif", 0, 11));
        lblUnidad.setBounds(new Rectangle(120, 40, 295, 15));
        lblUnidad.setFont(new Font("SansSerif", 0, 11));
        lblLaboratorio.setBounds(new Rectangle(120, 65, 300, 15));
        lblLaboratorio.setFont(new Font("SansSerif", 0, 11));
        btnNumeroEntrega.setText("Numero Entrega:");
        btnNumeroEntrega.setBounds(new Rectangle(15, 15, 105, 20));
        btnNumeroEntrega.setMnemonic('N');
        btnNumeroEntrega.setForeground(new Color(250, 133, 14));
        btnNumeroEntrega.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnNumeroEntrega_actionPerformed(e);
            }
        });
        lblCantGuia_T.setText("Guia :");
        lblCantGuia_T.setBounds(new Rectangle(10, 15, 40, 20));
        lblCantGuia_T.setForeground(new Color(255, 133, 14));
        txtStockModifEntero.setBounds(new Rectangle(330, 15, 45, 20));
        txtStockModifEntero.setEnabled(false);
        txtStockModifEntero.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtStockModif_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtStockModif_keyTyped(e);
            }
        });
        txtStockModifEntero.setLengthText(4);
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(350, 355, 90, 20));
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(235, 355, 90, 20));
        txtNumeroEntrega.setBounds(new Rectangle(120, 15, 165, 20));
        txtNumeroEntrega.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNumeroEntrega_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtNumeroEntrega_keyTyped(e);
            }
        });
        txtNumeroEntrega.setLengthText(10);
        lblStock_T.setText("Stock:");
        lblStock_T.setBounds(new Rectangle(20, 90, 90, 15));
        lblStock.setBounds(new Rectangle(120, 90, 40, 15));
        lblStock.setFont(new Font("SansSerif", 0, 11));
        lblUnidadVenta.setBounds(new Rectangle(185, 90, 140, 15));
        lblUnidadVenta.setFont(new Font("SansSerif", 0, 11));
        lblCantGuia.setBounds(new Rectangle(60, 15, 55, 20));
        lblCantGuia.setForeground(new Color(255, 133, 14));
        lblCantAfectada_T.setText("Afectada:");
        lblCantAfectada_T.setBounds(new Rectangle(125, 15, 55, 20));
        lblCantAfectada_T.setForeground(new Color(255, 133, 14));
        lblCantAfectada.setBounds(new Rectangle(190, 15, 60, 20));
        lblCantAfectada.setForeground(new Color(255, 133, 14));
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(300, 15, 90, 25));
        btnBuscar.setToolTipText("null");
        btnBuscar.setMnemonic('B');
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        jPanelTitle1.setBounds(new Rectangle(15, 25, 395, 55));
        jPanelTitle1.setBorder(BorderFactory.createLineBorder(new Color(250, 133, 14), 1));
        jPanelTitle1.setBackground(Color.white);
        jLabelWhite2.setText("Cantidad");
        jLabelWhite2.setBounds(new Rectangle(20, 10, 70, 15));
        jLabelWhite2.setToolTipText("null");
        jLabelWhite2.setForeground(new Color(255, 133, 14));
        btnCantModif.setText("Real:");
        btnCantModif.setBounds(new Rectangle(285, 15, 40, 20));
        btnCantModif.setMnemonic('R');
        btnCantModif.setForeground(new Color(255, 133, 14));
        btnCantModif.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCantModif_actionPerformed(e);
            }
        });
        jPanelHeader1.setBounds(new Rectangle(15, 190, 425, 60));
        jPanelTitle2.setBounds(new Rectangle(15, 255, 425, 90));
        jPanelTitle2.setBackground(Color.white);
        jPanelTitle2.setBorder(BorderFactory.createLineBorder(new Color(250, 133, 14), 1));
        lblNumNotaT.setBounds(new Rectangle(30, 10, 70, 15));
        lblNumNotaT.setText("N° Nota:");
        lblNumNota.setBounds(new Rectangle(110, 10, 70, 15));
        lblNumGuia_T.setText("N° Guia:");
        lblNumGuia_T.setBounds(new Rectangle(215, 10, 70, 15));
        lblNumGuia.setBounds(new Rectangle(295, 10, 70, 15));
        lblNumEntrega_T.setText("N° Entrega:");
        lblNumEntrega_T.setBounds(new Rectangle(30, 35, 70, 15));
        lblNumEntrega.setBounds(new Rectangle(110, 35, 70, 15));
        lblPos_T.setText("Pos:");
        lblPos_T.setBounds(new Rectangle(215, 35, 70, 15));
        lblPos.setBounds(new Rectangle(295, 35, 70, 15));
        jPanelTitle1.add(btnCantModif, null);
        jPanelTitle1.add(lblCantGuia, null);
        jPanelTitle1.add(lblCantGuia_T, null);
        jPanelTitle1.add(lblCantAfectada_T, null);
        jPanelTitle1.add(lblCantAfectada, null);
        jPanelTitle1.add(txtStockModifEntero, null);
        pnlTitle1.add(btnBuscar, null);
        pnlTitle1.add(txtNumeroEntrega, null);
        pnlTitle1.add(btnNumeroEntrega, null);
        jPanelTitle2.add(jLabelWhite2, null);
        jPanelTitle2.add(jPanelTitle1, null);
        jPanelHeader1.add(lblPos, null);
        jPanelHeader1.add(lblPos_T, null);
        jPanelHeader1.add(lblNumEntrega, null);
        jPanelHeader1.add(lblNumEntrega_T, null);
        jPanelHeader1.add(lblNumGuia, null);
        jPanelHeader1.add(lblNumGuia_T, null);
        jPanelHeader1.add(lblNumNota, null);
        jPanelHeader1.add(lblNumNotaT, null);
        jContentPane.add(jPanelTitle2, null);
        jContentPane.add(jPanelHeader1, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(pnlTitle1, null);
        pnlHeader1.add(lblUnidadVenta, null);
        pnlHeader1.add(lblStock, null);
        pnlHeader1.add(lblStock_T, null);
        pnlHeader1.add(lblLaboratorio, null);
        pnlHeader1.add(lblUnidad, null);
        pnlHeader1.add(lblProducto, null);
        pnlHeader1.add(lblLaboratorio_T, null);
        pnlHeader1.add(lblUnidadActual_T, null);
        pnlHeader1.add(lblProducto_T, null);
        jContentPane.add(pnlHeader1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initialize() {

    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtNumeroEntrega);
        mostrarDatos();
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnNumeroEntrega_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtNumeroEntrega);
    }

    private void txtNumeroEntrega_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            btnBuscar.doClick();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void txtNumeroEntrega_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtNumeroEntrega, e);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        buscar();
    }

    private void btnCantModif_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtStockModifEntero);
    }

    private void txtStockModif_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtStockModif_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtStockModifEntero, e);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (datosValidados())
                if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                "¿Está seguro que desea grabar el ajuste?")) {
                    try {
                        insertarAjusteKardex();
                        FarmaUtility.aceptarTransaccion();
                        FarmaUtility.showMessage(this, "El ajuste se guardó correctamente", txtStockModifEntero);
                    } catch (SQLException sql) {
                        FarmaUtility.liberarTransaccion();
                        log.error("", sql);
                        FarmaUtility.showMessage(this, "Ocurrió un error al guardar el ajuste: \n" +
                                sql.getMessage(), txtStockModifEntero);
                    }
                    cerrarVentana(true);
                }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void mostrarDatos() {
        lblProducto.setText(VariablesInventario.vDescProd);
        lblLaboratorio.setText(VariablesInventario.vNomLab);
        lblUnidad.setText(VariablesInventario.vDescUnidPresent);
        lblUnidadVenta.setText(VariablesInventario.vDescUnidFrac);
        lblStock.setText("" + VariablesInventario.vStock);
    }

    private boolean datosValidados() {
        boolean rpta = true;
        int cantGuia = Integer.parseInt(lblCantGuia.getText().trim());
        int cantAfect = Integer.parseInt(lblCantAfectada.getText().trim());
        if (txtStockModifEntero.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Ingrese una cantidad válida", txtStockModifEntero);
            return false;
        }
        if (!FarmaUtility.isInteger(txtStockModifEntero.getText().trim())) {
            FarmaUtility.showMessage(this, "Ingrese una cantidad válida", txtStockModifEntero);
            return false;
        }
        int cantEntera = Integer.parseInt(txtStockModifEntero.getText().trim());
        if (cantEntera > cantGuia) {
            FarmaUtility.showMessage(this, "No puede ingresar una cantidad mayor a la cantidad de la guía.",
                                     txtStockModifEntero);
            return false;
        }
        if (cantEntera == cantAfect) {
            FarmaUtility.showMessage(this, "La cantidad a modificar debe ser diferente a la cantidad afectada.",
                                     txtStockModifEntero);
            return false;
        }

        return rpta;
    }

    private void insertarAjusteKardex() throws SQLException {
        int cantAfect = Integer.parseInt(lblCantAfectada.getText().trim());
        int entero = Integer.parseInt(txtStockModifEntero.getText().trim());
        log.debug("entero : " + entero);
        int cantidad = entero - cantAfect;
        log.debug("cantidad : " + cantidad);
        DBInventario.ingresaAjusteDiferencia(ConstantsPtoVenta.MOT_KARDEX_AJUSTE_DIFERENCIA, cantidad,
                                             lblNumEntrega.getText().trim(), lblNumNota.getText().trim(),
                                             lblPos.getText().trim());
        VariablesInventario.vStk_ModEntero =
                (VariablesInventario.vStock / Integer.parseInt(VariablesInventario.vFrac)) + cantidad + "";
    }

    private void buscar() {
        String numeroEntrega = txtNumeroEntrega.getText().trim();
        if (numeroEntrega.length() != 10) {
            FarmaUtility.showMessage(this, "Debe ingresar el Numero Entrega correctamente.", txtNumeroEntrega);
        } else {
            try {
                ArrayList array = new ArrayList();
                String estado, indAjuste;
                DBInventario.consultaNumeroEntrega(array, numeroEntrega, VariablesInventario.vCodProd);
                if (array.size() > 0) {
                    array = (ArrayList)array.get(0);

                    estado = array.get(0).toString();
                    indAjuste = array.get(5).toString();
                    if (!estado.equalsIgnoreCase("C")) {
                        FarmaUtility.showMessage(this, "El pedido de recepción aún no está cerrado.",
                                                 txtNumeroEntrega);
                    } else if (indAjuste.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                        FarmaUtility.showMessage(this, "Este detalle ya tiene un ajuste, no podrá continuar.",
                                                 txtNumeroEntrega);
                    } else {
                        lblCantGuia.setText(array.get(1).toString());
                        lblCantAfectada.setText(array.get(2).toString());
                        lblNumNota.setText(array.get(3).toString());
                        lblPos.setText(array.get(4).toString());
                        lblNumGuia.setText(array.get(6).toString());
                        lblNumEntrega.setText(array.get(7).toString());

                        txtNumeroEntrega.setEnabled(false);
                        btnBuscar.setEnabled(false);
                        txtStockModifEntero.setEnabled(true);
                        FarmaUtility.moveFocus(txtStockModifEntero);
                    }
                } else {
                    FarmaUtility.showMessage(this, "No se ha encontrado información. ¡Verifique!", txtNumeroEntrega);
                }
            } catch (SQLException e) {
                log.error("", e);
                FarmaUtility.showMessage(this, "Ha ocurrido un error al consultar el Numero Entrega.\n" +
                        e.getMessage(), txtNumeroEntrega);
            }
        }
    }
}
