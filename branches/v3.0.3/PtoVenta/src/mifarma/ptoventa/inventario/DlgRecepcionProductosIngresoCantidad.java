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
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JDialog;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.VariablesInventario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgRecepcionProductosIngresoCantidad extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgRecepcionProductosIngresoCantidad.class);

    Frame myParentFrame;

    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelWhite jContentPane = new JPanelWhite();

    private JPanelHeader pnlHeader1 = new JPanelHeader();

    private JPanelTitle pnlTitle1 = new JPanelTitle();

    private JLabelFunction lblEsc = new JLabelFunction();

    private JLabelFunction lblF11 = new JLabelFunction();

    private JLabelWhite lblStock_T = new JLabelWhite();

    private JLabelWhite lblFechaHoraActual = new JLabelWhite();

    private JLabelWhite lblUnidades_T = new JLabelWhite();

    private JLabelWhite lblUnidades = new JLabelWhite();

    private JLabelWhite lblCodigo_T = new JLabelWhite();

    private JLabelWhite lblDescripcion_T = new JLabelWhite();

    private JLabelWhite lblCodigo = new JLabelWhite();

    private JLabelWhite lblDescripcion = new JLabelWhite();

    private JLabelWhite lblUnidad = new JLabelWhite();

    private JLabelWhite lblUnidad_T = new JLabelWhite();

    private JLabelWhite lblValorFrac = new JLabelWhite();

    private JLabelWhite lblValorFrac_T = new JLabelWhite();

    private JLabelWhite lblLaboratorio_T = new JLabelWhite();

    private JLabelWhite lblLaboratorio = new JLabelWhite();

    private JLabelWhite lblGrupo_T = new JLabelWhite();

    private JLabelWhite lblGrupo = new JLabelWhite();

    private JLabelWhite blLinea_T = new JLabelWhite();

    private JLabelWhite lblLinea = new JLabelWhite();

    private JTextFieldSanSerif txtCantidad = new JTextFieldSanSerif();

    private JButtonLabel btnEntero = new JButtonLabel();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgRecepcionProductosIngresoCantidad() {
        this(null, "", false);
    }

    public DlgRecepcionProductosIngresoCantidad(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(443, 310));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Ingreso de Cantidad");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlHeader1.setBounds(new Rectangle(10, 10, 410, 40));
        pnlHeader1.setBackground(new Color(225, 130, 14));
        pnlTitle1.setBounds(new Rectangle(10, 60, 410, 185));
        pnlTitle1.setBackground(Color.white);
        pnlTitle1.setBorder(BorderFactory.createLineBorder(new Color(225, 130, 14), 1));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(330, 255, 90, 20));
        lblF11.setText("[ Enter ] Aceptar");
        lblF11.setBounds(new Rectangle(205, 255, 105, 20));
        lblStock_T.setText("Stock del Producto al:");
        lblStock_T.setBounds(new Rectangle(15, 10, 110, 15));
        lblStock_T.setFont(new Font("SansSerif", 0, 11));
        lblFechaHoraActual.setText("09/01/2006 11:13:56");
        lblFechaHoraActual.setBounds(new Rectangle(125, 10, 100, 15));
        lblUnidades_T.setText("Unidades:");
        lblUnidades_T.setBounds(new Rectangle(255, 10, 55, 15));
        lblUnidades_T.setFont(new Font("SansSerif", 0, 11));
        lblUnidades.setText("11");
        lblUnidades.setBounds(new Rectangle(305, 10, 95, 15));
        lblCodigo_T.setText("Código");
        lblCodigo_T.setBounds(new Rectangle(15, 15, 70, 15));
        lblCodigo_T.setForeground(new Color(225, 130, 14));
        lblDescripcion_T.setText("Descripción");
        lblDescripcion_T.setBounds(new Rectangle(130, 15, 70, 15));
        lblDescripcion_T.setForeground(new Color(225, 130, 14));
        lblCodigo.setText("112053 ");
        lblCodigo.setBounds(new Rectangle(15, 40, 90, 15));
        lblCodigo.setFont(new Font("SansSerif", 0, 11));
        lblCodigo.setForeground(new Color(225, 130, 14));
        lblDescripcion.setText("FLEXEN COMPUESTO ");
        lblDescripcion.setBounds(new Rectangle(130, 40, 270, 15));
        lblDescripcion.setFont(new Font("SansSerif", 0, 11));
        lblDescripcion.setForeground(new Color(225, 130, 14));
        lblUnidad.setText("CJA/10 TAB ");
        lblUnidad.setBounds(new Rectangle(15, 85, 200, 15));
        lblUnidad.setFont(new Font("SansSerif", 0, 11));
        lblUnidad.setForeground(new Color(225, 130, 14));
        lblUnidad_T.setText("Unidad");
        lblUnidad_T.setBounds(new Rectangle(15, 62, 70, 15));
        lblUnidad_T.setForeground(new Color(225, 130, 14));
        lblValorFrac.setText("1");
        lblValorFrac.setBounds(new Rectangle(250, 85, 90, 15));
        lblValorFrac.setFont(new Font("SansSerif", 0, 11));
        lblValorFrac.setForeground(new Color(225, 130, 14));
        lblValorFrac_T.setText("Valor Frac.");
        lblValorFrac_T.setBounds(new Rectangle(250, 62, 70, 15));
        lblValorFrac_T.setForeground(new Color(225, 130, 14));
        lblLaboratorio_T.setText("Laboratorio");
        lblLaboratorio_T.setBounds(new Rectangle(15, 158, 75, 15));
        lblLaboratorio_T.setForeground(new Color(225, 130, 14));
        lblLaboratorio.setText("FARMA");
        lblLaboratorio.setBounds(new Rectangle(120, 160, 270, 15));
        lblLaboratorio.setFont(new Font("SansSerif", 0, 11));
        lblLaboratorio.setForeground(new Color(225, 130, 14));
        lblGrupo_T.setText("Grupo");
        lblGrupo_T.setBounds(new Rectangle(15, 182, 75, 15));
        lblGrupo.setText("Grupo1");
        lblGrupo.setBounds(new Rectangle(120, 182, 70, 15));
        lblGrupo.setFont(new Font("SansSerif", 0, 11));
        blLinea_T.setText("Linea");
        blLinea_T.setBounds(new Rectangle(15, 205, 75, 15));
        lblLinea.setText("Linea1");
        lblLinea.setBounds(new Rectangle(120, 205, 70, 15));
        lblLinea.setFont(new Font("SansSerif", 0, 11));
        txtCantidad.setBounds(new Rectangle(15, 135, 60, 15));
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCantidad_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtCantidad_keyTyped(e);
            }
        });
        btnEntero.setText("Entero");
        btnEntero.setBounds(new Rectangle(15, 110, 60, 20));
        btnEntero.setForeground(new Color(255, 130, 14));
        btnEntero.setMnemonic('e');
        btnEntero.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnEntero_actionPerformed(e);
            }
        });
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        pnlTitle1.add(btnEntero, null);
        pnlTitle1.add(txtCantidad, null);
        pnlTitle1.add(lblLinea, null);
        pnlTitle1.add(blLinea_T, null);
        pnlTitle1.add(lblGrupo, null);
        pnlTitle1.add(lblGrupo_T, null);
        pnlTitle1.add(lblLaboratorio, null);
        pnlTitle1.add(lblLaboratorio_T, null);
        pnlTitle1.add(lblValorFrac_T, null);
        pnlTitle1.add(lblValorFrac, null);
        pnlTitle1.add(lblUnidad_T, null);
        pnlTitle1.add(lblUnidad, null);
        pnlTitle1.add(lblDescripcion, null);
        pnlTitle1.add(lblCodigo, null);
        pnlTitle1.add(lblDescripcion_T, null);
        pnlTitle1.add(lblCodigo_T, null);
        jContentPane.add(pnlTitle1, null);
        pnlHeader1.add(lblUnidades, null);
        pnlHeader1.add(lblUnidades_T, null);
        pnlHeader1.add(lblFechaHoraActual, null);
        pnlHeader1.add(lblStock_T, null);
        jContentPane.add(pnlHeader1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        mifarma.common.FarmaVariables.vAceptar = false;
        // initTable();
        mostrarDatos();
    };

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void txtFechaVenc_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtCantidad);
    }

    private void txtCantidad_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCantidad, e);
    }

    private void txtCantidad_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (datosValidados()) {
                //if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,"¿Está seguro se afectar el producto?"))
                {
                    this.actualizarCant();
                    cerrarVentana(true);
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.setVisible(false);
        }
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void mostrarDatos() {
        lblUnidades.setText(VariablesInventario.vStkFis);
        lblCodigo.setText(VariablesInventario.vCodProd);
        lblDescripcion.setText(VariablesInventario.vDescProd);
        lblUnidad.setText(VariablesInventario.vDescUnidPresent);
        lblValorFrac.setText(VariablesInventario.vValorFrac);
        lblLaboratorio.setText(VariablesInventario.vNomLab);

        try {
            lblFechaHoraActual.setText(FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA_HORA));
        } catch (SQLException sql) {
            lblFechaHoraActual.setText("");
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al obtener la fecha del sistema: \n" +
                    sql.getMessage(), txtCantidad);
        }

    }

    private boolean datosValidados() {
        boolean rpta = true;
        if (txtCantidad.getText().length() == 0) {
            rpta = false;
            FarmaUtility.showMessage(this, "Ingrese la cantidad", txtCantidad);
        } else if (Integer.parseInt(txtCantidad.getText()) > Integer.parseInt(VariablesInventario.vCantGuia)) {
            rpta = false;
            FarmaUtility.showMessage(this,
                                     "La cantidad no puede ser mayor a la cantidad de la guia: " + VariablesInventario.vCantGuia,
                                     txtCantidad);
        }
        return rpta;
    }

    private void actualizarCant() {
        VariablesInventario.vCantMov = txtCantidad.getText().trim();
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void btnEntero_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCantidad);
    }

}
