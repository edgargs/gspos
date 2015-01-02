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
import javax.swing.JFrame;

import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgExcesoProducto extends JDialog {
    // **************************************************************************
    // Constructores
    // **************************************************************************
    private static final Logger log = LoggerFactory.getLogger(DlgExcesoProducto.class);

    Frame myParentFrame;

    FarmaTableModel tableModel;
    boolean update = false;

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
    private JButtonLabel btnNumEntrega = new JButtonLabel();
    private JTextFieldSanSerif txtNumeroEntrega = new JTextFieldSanSerif();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelWhite lblCantidad = new JLabelWhite();
    private JTextFieldSanSerif txtCantExcedente = new JTextFieldSanSerif();
    private JButtonLabel btnNumLote = new JButtonLabel();
    private JButtonLabel btnFecVec = new JButtonLabel();
    private JTextFieldSanSerif txtNumLote = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFecVenc = new JTextFieldSanSerif();

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    public DlgExcesoProducto() {
        this(null, "", false);
    }

    public DlgExcesoProducto(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
            mifarma.common.FarmaUtility.centrarVentana(this);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public DlgExcesoProducto(Frame parent, String title, boolean modal, boolean update) {
        super(parent, title, modal);
        myParentFrame = parent;
        this.update = update;
        try {
            jbInit();
            initialize();
            mifarma.common.FarmaUtility.centrarVentana(this);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(451, 324));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Ajuste de Producto");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlHeader1.setBounds(new Rectangle(10, 10, 425, 90));
        pnlTitle1.setBounds(new Rectangle(10, 110, 425, 140));
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
        btnNumEntrega.setText("Numero Entrega : ");
        btnNumEntrega.setBounds(new Rectangle(15, 15, 125, 20));
        btnNumEntrega.setMnemonic('N');
        btnNumEntrega.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnNumEntrega_actionPerformed(e);
            }
        });
        txtNumeroEntrega.setBounds(new Rectangle(165, 15, 140, 20));
        txtNumeroEntrega.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNumeroEntrega_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtNumeroEntrega_keyTyped(e);
            }
        });
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(340, 265, 90, 20));
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(235, 265, 90, 20));
        lblCantidad.setText("Cantidad Excedente : ");
        lblCantidad.setBounds(new Rectangle(15, 48, 135, 20));
        txtCantExcedente.setBounds(new Rectangle(165, 48, 125, 20));
        txtCantExcedente.setDocument(new FarmaLengthText(120));
        txtCantExcedente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCantExcedente_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtCantExcedente_keyTyped(e);
            }
        });
        btnNumLote.setText("Numero Lote : ");
        btnNumLote.setBounds(new Rectangle(15, 82, 120, 15));
        btnFecVec.setText("Fecha Vencimiento :");
        btnFecVec.setBounds(new Rectangle(15, 110, 120, 15));
        txtNumLote.setBounds(new Rectangle(165, 82, 125, 20));
        txtNumLote.setDocument(new FarmaLengthText(120));
        txtNumLote.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtNumLote_keyPressed(e);
            }
        });
        txtFecVenc.setBounds(new Rectangle(165, 110, 125, 20));
        txtFecVenc.setDocument(new FarmaLengthText(120));
        txtFecVenc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFecVenc_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtFecVenc_keyTyped(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFecVenc_keyReleased(e);
            }
        });
        pnlTitle1.add(txtFecVenc, null);
        pnlTitle1.add(txtNumLote, null);
        pnlTitle1.add(btnFecVec, null);
        pnlTitle1.add(btnNumLote, null);
        pnlTitle1.add(txtCantExcedente, null);
        pnlTitle1.add(lblCantidad, null);
        pnlTitle1.add(txtNumeroEntrega, null);
        pnlTitle1.add(btnNumEntrega, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(pnlHeader1, null);
        pnlHeader1.add(lblLaboratorio, null);
        pnlHeader1.add(lblUnidad, null);
        pnlHeader1.add(lblProducto, null);
        pnlHeader1.add(lblLaboratorio_T, null);
        pnlHeader1.add(lblUnidadActual_T, null);
        pnlHeader1.add(lblProducto_T, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //
        txtNumeroEntrega.setDocument(new FarmaLengthText(10));
        txtCantExcedente.setDocument(new FarmaLengthText(10));
        txtNumLote.setDocument(new FarmaLengthText(10));
        txtFecVenc.setDocument(new FarmaLengthText(10));
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initialize() {
        if (update) {
            txtNumeroEntrega.setText(VariablesInventario.vNumEntExcProd);
            txtCantExcedente.setText(VariablesInventario.vCantExcProd);
            txtNumLote.setText(VariablesInventario.vNumLotExcProd);
            txtFecVenc.setText(VariablesInventario.vFecVecExcProd);
        }
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

    private void txtNumeroEntrega_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtCantExcedente);
        } else {
            chkKeyPressed(e);
        }
    }

    private void txtNumeroEntrega_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtNumeroEntrega, e);
    }

    private void txtCantExcedente_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNumLote);
        } else {
            chkKeyPressed(e);
        }
    }

    private void txtCantExcedente_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtNumeroEntrega, e);
    }

    private void txtNumLote_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtFecVenc);
            txtNumLote.setText(txtNumLote.getText().toUpperCase());
        } else {
            chkKeyPressed(e);
        }
    }

    private void txtFecVenc_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNumeroEntrega);
        } else {
            chkKeyPressed(e);
        }
    }

    private void txtFecVenc_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtFecVenc, e);
    }

    private void txtFecVenc_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFecVenc, e);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (datosValidados()) {
                try {
                    if (update)
                        DBInventario.modificarExcesoProducto(VariablesInventario.vSecExcProd,
                                                             VariablesInventario.vCodProd, txtNumeroEntrega.getText(),
                                                             txtNumLote.getText(), txtFecVenc.getText(),
                                                             txtCantExcedente.getText());
                    else
                        DBInventario.agregarExcesoProducto(VariablesInventario.vCodProd, txtNumeroEntrega.getText(),
                                                           txtNumLote.getText(), txtFecVenc.getText(),
                                                           txtCantExcedente.getText());

                    FarmaUtility.aceptarTransaccion();
                    FarmaUtility.showMessage(this, "Se guardó el ajuste del producto", txtNumeroEntrega);
                    cerrarVentana(true);
                } catch (SQLException sql) {
                    FarmaUtility.liberarTransaccion();
                    FarmaUtility.showMessage(this, "Ocurrió un error al grabar el ajuste del producto: \n" +
                            sql.getMessage(), txtNumeroEntrega);
                    log.error("", sql);
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.cerrarVentana(false);
        }
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void mostrarDatos() {
        lblProducto.setText(VariablesInventario.vDescProd);
        lblLaboratorio.setText(VariablesInventario.vNomLab);
        lblUnidad.setText(VariablesInventario.vDescUnidPresent);
        //lblUnidadVenta.setText(VariablesInventario.vDescUnidFrac);
        //lblStock.setText(VariablesInventario.vCant);
    }

    private boolean datosValidados() {
        boolean rpta = true;

        if (txtCantExcedente.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Ingrese cantidad.", txtCantExcedente);
            return false;
        } else if (!FarmaUtility.isInteger(txtCantExcedente.getText().trim())) {
            FarmaUtility.showMessage(this, "Ingrese una cantidad válida", txtCantExcedente);
            return false;
        } else if (txtNumeroEntrega.getText().trim().length() > 0) {
            if (txtNumeroEntrega.getText().trim().length() < 10) {
                FarmaUtility.showMessage(this, "Ingrese Numero Entrega correctamente.", txtNumeroEntrega);
                return false;
            } else if (validaNumEntrega()) {
                FarmaUtility.showMessage(this, "Numero Entrega no es válido. Verifique", txtNumeroEntrega);
                return false;
            }
        } else if (txtFecVenc.getText().trim().length() > 0) {
            if (!FarmaUtility.validaFecha(txtFecVenc.getText(), "dd/MM/yyyy")) {
                FarmaUtility.showMessage(this, "Ingrese una Fecha válida.", txtFecVenc);
                return false;
            }
        }
        return rpta;
    }

    private boolean validaNumEntrega() {
        boolean retorno = true;
        try {
            int cantNumEntrega = DBInventario.getCantNumEntrega(txtNumeroEntrega.getText());
            if (cantNumEntrega > 0) {
                retorno = false;
            }
        } catch (SQLException e) {
            log.error("", e);
        }
        return retorno;
    }

    private void btnNumEntrega_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtNumeroEntrega);
    }

    /*	private void insertarAjusteKardex() throws SQLException {

		DBInventario.ingresaAjusteKardex(FarmaLoadCVL.getCVLCode(
				"cmbMotivoAjuste", cmbMotivoAjuste.getSelectedIndex()),
				txtNumeroEntrega.getText(),txtCantExcedente.getText().trim());
	}*/
}
