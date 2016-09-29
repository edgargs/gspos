package mifarma.ptoventa.caja;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.ce.reference.DBCajaElectronica;
import mifarma.ptoventa.ce.reference.UtilityCajaElectronica;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgListaRemito extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgListaRemito.class);
    private FarmaTableModel tableModelLista;
    private Frame myParentFrame;

    private final int COL_FEC_REM = 0;
    private final int COL_NUM_REM = 1;

    private final int COL_ORDEN = 8;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
    private JPanelTitle pnlTitulo = new JPanelTitle();
    private JPanelTitle pnlResultados = new JPanelTitle();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblRemitos = new JTable();
    private JButtonLabel btnPeriodo = new JButtonLabel();
    private JTextFieldSanSerif txtFechaIni = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaFin = new JTextFieldSanSerif();
    private JButton btnBuscar = new JButton();
    private JButtonLabel btnListado = new JButtonLabel();
    private JLabel lblRegsitros_T = new JLabel();
    private JLabel lblRegistros = new JLabel();


    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction lblF5 = new JLabelFunction();
    private JLabelFunction lblF2 = new JLabelFunction();
    private JLabelFunction lblF3 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF10 = new JLabelFunction();
    private JLabel lblRegistros1 = new JLabel();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();

    public DlgListaRemito() {
        this(null, "", false);
    }

    public DlgListaRemito(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(784, 489));
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Registro de Remitos");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlCriterioBusqueda.setBounds(new Rectangle(5, 5, 765, 35));
        pnlTitulo.setBounds(new Rectangle(5, 45, 765, 20));
        pnlResultados.setBounds(new Rectangle(5, 395, 765, 20));
        jScrollPane1.setBounds(new Rectangle(5, 65, 765, 330));
        tblRemitos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblVentasVendedor_keyPressed(e);
            }
        });
        btnPeriodo.setText("Periodo :");
        btnPeriodo.setBounds(new Rectangle(105, 10, 60, 20));
        btnPeriodo.setMnemonic('p');
        btnPeriodo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnPeriodo_actionPerformed(e);
            }
        });
        txtFechaIni.setBounds(new Rectangle(170, 10, 130, 20));
        txtFechaIni.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaIni_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaIni_keyReleased(e);
            }
        });
        txtFechaFin.setBounds(new Rectangle(320, 10, 130, 20));
        txtFechaFin.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaFin_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaFin_keyReleased(e);
            }
        });
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(475, 10, 95, 20));
        btnBuscar.setMnemonic('b');
        btnBuscar.setFont(new Font("SansSerif", 1, 11));
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        btnListado.setText("Listado de Registros de Remitos");
        btnListado.setBounds(new Rectangle(10, 0, 345, 20));
        btnListado.setMnemonic('l');
        btnListado.setToolTipText("null");
        btnListado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListado_actionPerformed(e);
            }
        });
        lblRegsitros_T.setText("Registros :");
        lblRegsitros_T.setBounds(new Rectangle(5, 0, 70, 20));
        lblRegsitros_T.setFont(new Font("SansSerif", 1, 11));
        lblRegsitros_T.setForeground(Color.white);
        lblRegistros.setText("0");
        lblRegistros.setBounds(new Rectangle(65, 0, 40, 20));
        lblRegistros.setFont(new Font("SansSerif", 1, 11));
        lblRegistros.setForeground(Color.white);
        lblRegistros.setHorizontalAlignment(SwingConstants.RIGHT);


        lblF1.setBounds(new Rectangle(5, 430, 125, 20));
        lblF1.setText("[ F1 ] Nuevo Remito");
        lblF5.setBounds(new Rectangle(370, 430, 115, 20));
        lblF5.setText("[ F5 ] Reimpresión");

        lblF2.setBounds(new Rectangle(140, 430, 105, 20));
        lblF2.setText("[ F2 ] Ver Detalle");
        lblF3.setBounds(new Rectangle(255, 430, 105, 20));
        lblF3.setText("[ F3 ] Ordenar");
        lblEsc.setBounds(new Rectangle(685, 430, 85, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        //lblF10.setBounds(new Rectangle(150, 370, 135, 20));
        //lblF10.setText("[ F8 ]Guardar Archivo");
        lblRegistros1.setText("0");
        lblRegistros1.setBounds(new Rectangle(-100, 0, 40, 20));
        lblRegistros1.setFont(new Font("SansSerif", 1, 11));
        lblRegistros1.setForeground(Color.white);
        lblRegistros1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelFunction1.setBounds(new Rectangle(495, 430, 135, 20));
        jLabelFunction1.setText("[ F6 ] Cambiar Codigo");
        jScrollPane1.getViewport().add(tblRemitos, null);
        //jContentPane.add(lblF10, null);
        jContentPane.add(jLabelFunction1, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF3, null);
        jContentPane.add(lblF2, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(lblF5, null);
        jContentPane.add(jScrollPane1, null);
        jContentPane.add(pnlResultados, null);
        pnlTitulo.add(btnListado, null);
        jContentPane.add(pnlTitulo, null);
        pnlResultados.add(lblRegistros1, null);
        pnlResultados.add(lblRegistros, null);
        pnlResultados.add(lblRegsitros_T, null);
        jContentPane.add(pnlCriterioBusqueda, null);
        pnlCriterioBusqueda.add(btnBuscar, null);
        pnlCriterioBusqueda.add(txtFechaFin, null);
        pnlCriterioBusqueda.add(txtFechaIni, null);
        pnlCriterioBusqueda.add(btnPeriodo, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ********************************************************************** */
    /*                            METODO INITIALIZE                           */
    /* ********************************************************************** */

    private void initialize() {
        initTableListaVentasVendedor();
        if (VariablesPtoVenta.vIndTico.equals("S") &&
            VariablesPtoVenta.vIndConFarma.equals("S")) { //20.01.2015 rherrera
            jLabelFunction1.setVisible(false);
            jLabelFunction1.setEnabled(false);
            lblF5.setVisible(false);
            lblF5.setEnabled(false);
        }
    }

    /* ********************************************************************** */
    /*     METODO DE INICIALIZACION DE LOS OBJETOS JTABLE Y FARMATABLEMODEL   */
    /* ********************************************************************** */

    private void initTableListaVentasVendedor() {
        tableModelLista =
                new FarmaTableModel(ConstantsCaja.columnsListaRemitos, ConstantsCaja.defaultValuesListaRemitos, 0);
        FarmaUtility.initSimpleList(tblRemitos, tableModelLista, ConstantsCaja.columnsListaRemitos);
        listarRemitos();
        if (tblRemitos.getRowCount() > 0) {
            String FechIni = "", FecFin = "";
            FechIni = FarmaUtility.getValueFieldJTable(tblRemitos, 0, 7).substring(0, 10);
            log.debug("FechIni:" + FechIni);
            FecFin = FarmaUtility.getValueFieldJTable(tblRemitos, 0, 7).substring(10, 20);
            txtFechaIni.setText(FechIni.trim());
            txtFechaFin.setText(FecFin.trim());
        }
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtFechaIni);
        lblRegistros.setText(tblRemitos.getRowCount() + "");
    }


    /* ********************************************************************** */
    /*      METODOS DE EVENTOS DE LOS OBJETOS DE LA CLASE Y DEL JDIALOG       */
    /* ********************************************************************** */

    private void txtFechaFin_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblRemitos, null, 2);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtFechaFin.getText().trim().length() > 1) {
                //FarmaUtility.moveFocus(btnBuscar);
                btnBuscar.doClick();
            }
        }
        chkKeyPressed(e);
    }

    private void tblVentasVendedor_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }


    private void btnPeriodo_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFechaIni);
    }

    private void btnListado_actionPerformed(ActionEvent e) {
        if (tblRemitos.getRowCount() > 0) {
            FarmaUtility.moveFocus(tblRemitos);
        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        if (validarCampos()) {
            listarRemitos();
        }
    }

    /* ********************************************************************** */
    /*                            METODOS AUXILIARES                          */
    /* ********************************************************************** */

    private void chkKeyPressed(KeyEvent e) {


        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {
            nuevoRemito();
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {
            int row = tblRemitos.getSelectedRow();
            if (row > -1)
                mostrarDetalle();
            /* else
       FarmaUtility.showMessage(this,"Debe seleccionar un registro",txtFechaIni);*/

        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            if (tblRemitos.getRowCount() > 0)
                ordenar();

        } else if (e.getKeyCode() == KeyEvent.VK_F5 && lblF5.isEnabled()) //rherrera: is enable
        {
            //FarmaUtility.showMessage(this, "En Desarrollo",txtFechaFin);
            int row = 0;
            row = tblRemitos.getSelectedRow();
            if (row > -1) {
                VariablesCaja.NumRemito = FarmaUtility.getValueFieldJTable(tblRemitos, row, 1);
                log.debug("VariablesCaja.NumRemito: " + VariablesCaja.NumRemito);
                UtilityCaja.imprimeVoucherRemito(this, VariablesCaja.NumRemito);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F6 && jLabelFunction1.isEnabled()) { //rherrera: is enable
            cambiarCodigoRemito(); //ASOSA, 09.08.2010
            listarRemitos(); //ASOSA, 13.08.2010
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.setVisible(false);
        }
    }

    /**
     * Se ordena el listado de remitos
     * */
    private void ordenar() {

        DlgOrdenar dlgordenar = new DlgOrdenar(myParentFrame, "", true);
        dlgordenar.setVisible(true);

        if (FarmaVariables.vAceptar) {
            FarmaUtility.ordenar(tblRemitos, tableModelLista, Integer.parseInt(VariablesCaja.vColumna),
                                 VariablesCaja.vOrden);
            tblRemitos.repaint();
            //FarmaUtility.setearPrimerRegistro(tblRemitos,txtFecha,2);
        }

    }

    private void listarRemitos() {

        String FechaIni = txtFechaIni.getText().trim();
        String FechaFin = txtFechaFin.getText().trim();

        try { // if(FechaIni.length()>0&&FechaFin.length()>0)
            DBCaja.getListaRemitos(tableModelLista, FechaIni, FechaFin);
            if (tblRemitos.getRowCount() > 0) {
                FarmaUtility.ordenar(tblRemitos, tableModelLista, COL_ORDEN, FarmaConstants.ORDEN_DESCENDENTE);
                FarmaUtility.moveFocus(txtFechaIni);
            } else {
                FarmaUtility.showMessage(this, "No se encontraron registros", txtFechaIni);
            }
            lblRegistros.setText(tblRemitos.getRowCount() + "");
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar remitos", txtFechaIni);
        }
    }

    private void nuevoRemito() {

        DlgNuevoRemito dlgnuevo = new DlgNuevoRemito(myParentFrame, "", true);
        dlgnuevo.setVisible(true);
        if (FarmaVariables.vAceptar) {
            txtFechaIni.setText("");
            txtFechaFin.setText("");
            listarRemitos();
        }
    }

    private void mostrarDetalle() {

        VariablesCaja.NumRemito =
                FarmaUtility.getValueFieldJTable(tblRemitos, tblRemitos.getSelectedRow(), COL_NUM_REM);
        VariablesCaja.FecRemito =
                FarmaUtility.getValueFieldJTable(tblRemitos, tblRemitos.getSelectedRow(), COL_FEC_REM);
        log.debug("VariablesCaja.NumRemito " + VariablesCaja.NumRemito);
        DlgDetalleRemito dlgdetalle = new DlgDetalleRemito(myParentFrame, "", true);
        dlgdetalle.setVisible(true);

        if (FarmaVariables.vAceptar) {

        }
    }

    /**
     *  Valida los campos ingresados()Cod,fecini,fecfin)
     */
    private boolean validarCampos() {

        boolean retorno = true;
        String fechaini = txtFechaIni.getText().trim();
        String fechafin = txtFechaFin.getText().trim();

        if (txtFechaIni.getText().length() < 1 || txtFechaIni.getText().length() < 10) {
            retorno = false;
            FarmaUtility.showMessage(this, "Formato de fecha inicio invalido", txtFechaIni);
        } else if (txtFechaFin.getText().length() < 1 || txtFechaFin.getText().length() < 10) {
            retorno = false;
            FarmaUtility.showMessage(this, "Formato de fecha fin  invalido", txtFechaFin);
        } else if (!FarmaUtility.isFechaValida(this, fechaini, "Ingrese una fecha correcta en fecha de inicio")) {
            retorno = false;
            FarmaUtility.moveFocus(txtFechaIni);
        } else if (!FarmaUtility.validarRangoFechas(this, txtFechaIni, txtFechaFin, false, true, true, true)) {
            retorno = false;
            FarmaUtility.moveFocus(txtFechaIni);
        }
        return retorno;
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void txtFechaIni_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaIni, e);
    }

    private void txtFechaFin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaFin, e);
    }

    private void txtFechaIni_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblRemitos, null, 2);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtFechaIni.getText().trim().length() > 1) {
                FarmaUtility.moveFocus(txtFechaFin);
            }
        }
        chkKeyPressed(e);
    }

    private void busqueda() {
        if (validarCampos()) {
            txtFechaIni.setText(txtFechaIni.getText().trim().toUpperCase());
            txtFechaFin.setText(txtFechaFin.getText().trim().toUpperCase());
            String FechaInicio = txtFechaIni.getText().trim();
            String FechaFin = txtFechaFin.getText().trim();
            if (FechaInicio.length() > 0 || FechaFin.length() > 0) {
                char primerkeyCharFI = FechaInicio.charAt(0);
                char ultimokeyCharFI = FechaInicio.charAt(FechaInicio.length() - 1);
                char primerkeyCharFF = FechaFin.charAt(0);
                char ultimokeyCharFF = FechaFin.charAt(FechaFin.length() - 1);

                if (!Character.isLetter(primerkeyCharFI) && !Character.isLetter(ultimokeyCharFI) &&
                    !Character.isLetter(primerkeyCharFF) && !Character.isLetter(ultimokeyCharFF)) {
                    log.debug("Buscando");
                    listarRemitos();
                } else
                    FarmaUtility.showMessage(this, "Ingrese un formato válido de fechas", txtFechaIni);
            } else
                FarmaUtility.showMessage(this, "Ingrese datos para la búsqueda", txtFechaIni);
        }
    }

    /**
     * Cambiar el codigo de remito
     * @author ASOSA
     * @since 09.08.2010
     */
    private void cambiarCodigoRemito() {
        String codRemiOLD = FarmaUtility.getValueFieldJTable(tblRemitos, tblRemitos.getSelectedRow(), COL_NUM_REM);
        String codRemiNEW = FarmaUtility.ShowInput(this, "Ingrese Codigo de nuevo remito");
        String precinto = " ";
        if (UtilityCajaElectronica.getIndImpreRemito_Matricial())
            precinto = FarmaUtility.ShowInput(this, "Ingrese Precinto");
        try {
            DBCajaElectronica.cambiarCodigoRemito(codRemiOLD, codRemiNEW, precinto);
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException e) {
            FarmaUtility.liberarTransaccion();
            log.error("", e);
            if (e.getErrorCode() == 20002)
                FarmaUtility.showMessage(this, e.getMessage().substring(10, e.getMessage().indexOf("ORA-06512")),
                                         null);
        }
    }

}
