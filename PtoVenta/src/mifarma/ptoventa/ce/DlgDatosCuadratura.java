package mifarma.ptoventa.ce;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaJTable;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaRowEditorModel;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.DlgListaMaestros;
import mifarma.ptoventa.ce.reference.ConstantsCajaElectronica;
import mifarma.ptoventa.ce.reference.DBCajaElectronica;
import mifarma.ptoventa.ce.reference.VariablesCajaElectronica;
import mifarma.ptoventa.reference.ConstantsPtoVenta;

import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgDatosCuadratura.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      31.07.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgDatosCuadratura extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgDatosCuadratura.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private FarmaRowEditorModel rowEditorModel;
    private DefaultCellEditor defaultCellEditor;
    private ArrayList aCampos = new ArrayList();

    double monto, montoParcial, montoPerdido;

    /* ************************************************************************ */
    /*                               CAMPOS DE LA GRILLA                        */
    /* ************************************************************************ */

    //001
    private JComboBox cmbSerie = new JComboBox();
    private JTextFieldSanSerif txtSerie = new JTextFieldSanSerif();
    //002
    private JComboBox cmbTipoComp = new JComboBox();
    private String[] compDescripcion = { "BOLETA", "FACTURA", "TICKETERA" };
    private String[] compValor = { "01", "02", "05" };
    //003
    private JTextFieldSanSerif txtNumeroComp = new JTextFieldSanSerif();

    //004
    private JTextFieldSanSerif txtMonto = new JTextFieldSanSerif();
    //005
    private JTextFieldSanSerif txtVuelto = new JTextFieldSanSerif();
    //006
    private JComboBox cmbTipoBillete = new JComboBox();
    private String[] compDescTipoBillete = { "BILLETE", "MONEDA" };
    private String[] compValTipoBillete = { "01", "02" };
    //007
    private JComboBox cmbTipoMoneda = new JComboBox();
    private String[] compDescTipoMoneda = { "SOLES", "DOLARES" };
    private String[] compValTipoMoneda = { "01", "02" };
    //008
    private JComboBox cmbFormaPago = new JComboBox();

    //009
    private JTextFieldSanSerif txtSerieBillete = new JTextFieldSanSerif();

    /* ************************************************************************ */
    /*                         CREACION DE TABLE MODEL                          */
    /* ************************************************************************ */

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JScrollPane scrLista = new JScrollPane();
    private FarmaJTable tblLista = new FarmaJTable();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel btnLista = new JButtonLabel();
    private JLabelFunction lblF1 = new JLabelFunction();
    
    private JTextField txtProveedor = new JTextField();
    JTextField vCodProveedor = new JTextField();
    private JComboBox cmbEntidadFinanciera = new JComboBox();
    private JComboBox cmbServicio = new JComboBox();
    private JTextFieldSanSerif txtMontoPerdido = new JTextFieldSanSerif();
    private JTextField txtNumeroCuenta = new JTextField();
    JTextField vCodNumeroCuenta = new JTextField();
    private JTextFieldSanSerif txtMotivo = new JTextFieldSanSerif();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgDatosCuadratura() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    public DlgDatosCuadratura(Frame parent, String title, boolean modal) {
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
        this.getContentPane().setLayout(borderLayout1);
        this.setSize(new Dimension(417, 306));
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        lblEsc.setBounds(new Rectangle(305, 245, 95, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblF11.setBounds(new Rectangle(190, 245, 105, 20));
        lblF11.setText("[ F11 ] Aceptar");
        scrLista.setBounds(new Rectangle(10, 30, 390, 205));
        tblLista.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblLista_keyPressed(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(10, 10, 390, 20));
        btnLista.setText("Lista Datos");
        btnLista.setBounds(new Rectangle(5, 0, 105, 20));
        btnLista.setMnemonic('L');
        btnLista.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLista_actionPerformed(e);
            }
        });
        lblF1.setBounds(new Rectangle(10, 245, 135, 20));
        lblF1.setText("[ F1 ] Modificar Dato");
        scrLista.getViewport().add(tblLista, null);
        pnlTitle1.add(btnLista, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(scrLista, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        //
        txtNumeroComp.setLengthText(8);
        txtMonto.setLengthText(10);
        txtVuelto.setLengthText(10);
        txtSerieBillete.setLengthText(30);
        txtSerie.setLengthText(4); //Fac-Electronica 10.10.2014
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        this.setTitle("Datos Cuadratura - " + VariablesCajaElectronica.vDescCuadratura);
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsCajaElectronica.columnsListaCuadraturaDat, ConstantsCajaElectronica.defaultValuesListaCuadraturaDat,
                                    0, ConstantsCajaElectronica.editableListaCuadraturaDat, null);
        //FarmaUtility.initSimpleList(tblLista, tableModel,ConstantsCajaElectronica.columnsListaCuadraturaDat);
        rowEditorModel = new FarmaRowEditorModel();
        tblLista.setAutoCreateColumnsFromModel(false);
        tblLista.setModel(tableModel);
        tblLista.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        for (int k = 0; k < tableModel.getColumnCount(); k++) {
            TableColumn column = new TableColumn(k, ConstantsCajaElectronica.columnsListaCuadraturaDat[k].m_width);
            tblLista.addColumn(column);
        }
        tblLista.setRowEditorModel(rowEditorModel);
        cargaCampos();
    }

    private void cargaLista() {
        try {
            DBCajaElectronica.listaCuadraturaCampos(tableModel);
            FarmaUtility.ordenar(tblLista, tableModel, 2, FarmaConstants.ORDEN_ASCENDENTE);
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al cargar los campos.", tblLista);
        }
        aCampos = tableModel.data;
    }

    private void cargaCampos() {
        cargaLista();
        setTipoCampo();
        FarmaUtility.setearPrimerRegistro(tblLista, null, 0);
        if (tblLista.getRowCount() > 0) {
            for (int i = 0; i < tblLista.getRowCount(); i++) {
                tblLista.setValueAt(tblLista.getValueAt(i, 1).toString().trim(), i, 1);
                tblLista.changeSelection(0, 1, false, false);
            }
        }
        tblLista.repaint();
    }

    /***Este procedimiento determinará el tipo de dato de cada campo segun lo registrado en la BD. */
    private void setTipoCampo() {
        String codigoCampo = "";
        for (int i = 0; i < tblLista.getRowCount(); i++) {
            codigoCampo = tblLista.getValueAt(i, 2).toString().trim();
            if (codigoCampo.equals(ConstantsCajaElectronica.CAMPO_FORMA_PAGO))
                getCmbFormaPago(i, (String)tblLista.getValueAt(i, 4));
            else if (codigoCampo.equals(ConstantsCajaElectronica.CAMPO_TIPO_DINERO))
                getCmbTipoBillete(i, (String)tblLista.getValueAt(i, 4));
            else if (codigoCampo.equals(ConstantsCajaElectronica.CAMPO_TIPO_MONEDA))
                getCmbTipoMoneda(i, (String)tblLista.getValueAt(i, 4));
            else if (codigoCampo.equals(ConstantsCajaElectronica.CAMPO_SERIE_COMPROBANTE))
                //cmbSerie(i,(String)tblLista.getValueAt(i,4));
                txtSerie(i, (String)tblLista.getValueAt(i, 4), (String)tblLista.getValueAt(i, 4));
            else if (codigoCampo.equals(ConstantsCajaElectronica.CAMPO_TIPO_COMPROBANTE))
                cmbTipoComp(i, (String)tblLista.getValueAt(i, 4));
            else if (codigoCampo.equals(ConstantsCajaElectronica.CAMPO_NUMERO_COMPROBANTE))
                txtNumeroComp(i, (String)tblLista.getValueAt(i, 3), (String)tblLista.getValueAt(i, 4));
            else if (codigoCampo.equals(ConstantsCajaElectronica.CAMPO_IMPORTE))
                txtMonto(i, (String)tblLista.getValueAt(i, 3), (String)tblLista.getValueAt(i, 4));
            else if (codigoCampo.equals(ConstantsCajaElectronica.CAMPO_VUELTO))
                getTxtVuelto(i, (String)tblLista.getValueAt(i, 3), (String)tblLista.getValueAt(i, 4));
            else if (codigoCampo.equals(ConstantsCajaElectronica.CAMPO_SERIE_DINERO))
                getTxtSerieBillete(i, (String)tblLista.getValueAt(i, 3), (String)tblLista.getValueAt(i, 4));
            //KMONCADA 2015.03.23 CUADRATURA DE DELIVERY PERDIDO
            else if (codigoCampo.equals(ConstantsCajaElectronica.CAMPO_NOMBRE_PROVEEDOR))
                getTxtProveedor(i, (String)tblLista.getValueAt(i, 3), (String)tblLista.getValueAt(i, 4));
            else if (codigoCampo.equals(ConstantsCajaElectronica.CAMPO_ENTIDAD_FINANCIERA))
                getCmbEntidadFinanciera(i, (String)tblLista.getValueAt(i, 4));
            else if (codigoCampo.equals(ConstantsCajaElectronica.CAMPO_TIPO_SERVICIO))
                getCmbServicio(i, (String)tblLista.getValueAt(i, 4));
            else if (codigoCampo.equals(ConstantsCajaElectronica.CAMPO_MONTO_PERDIDO))
                getTxtMontoPerdido(i, (String)tblLista.getValueAt(i, 3), (String)tblLista.getValueAt(i, 4));
            else if (codigoCampo.equals(ConstantsCajaElectronica.CAMPO_NUMERO_CUENTA))
                getTxtNumeroCuenta(i, (String)tblLista.getValueAt(i, 3), (String)tblLista.getValueAt(i, 4));
            else if (codigoCampo.equals(ConstantsCajaElectronica.CAMPO_GLOSA))
                getTxtMotivo(i, (String)tblLista.getValueAt(i, 3), (String)tblLista.getValueAt(i, 4));
        }
    }
    
    private void getTxtMontoPerdido(int pRow, String pTipoCampo, String pInSoloLectura) {
        txtMontoPerdido.setLengthText(10);
        createJTextField(txtMontoPerdido, pRow, pTipoCampo, pInSoloLectura);
    }
    
    private void getTxtNumeroCuenta(int pRow, String pTipoCampo, String pInSoloLectura) {
        txtNumeroCuenta.setDocument(new FarmaLengthText(30));
        createJTextField(txtNumeroCuenta, pRow, pTipoCampo, pInSoloLectura);
    }
    
    private void getTxtMotivo(int pRow, String pTipoCampo, String pInSoloLectura) {
        txtMotivo.setLengthText(120);
        createJTextField(txtMotivo, pRow, pTipoCampo, pInSoloLectura);
    }
    
    /* ************************************************************************ */
    /* SECCION : CREACION DE CAMPOS JTEXTFIELD                                  */
    /* ************************************************************************ */
    
    private void getTxtProveedor(int pRow, String pTipoCampo, String pInSoloLectura) {
        txtProveedor.setEditable(false);
        addKeyListenerToTextField2(txtProveedor, pTipoCampo, pInSoloLectura, ConstantsPtoVenta.LISTA_PROVEEDOR_CE,
                                   vCodProveedor);
        defaultCellEditor = new DefaultCellEditor(txtProveedor);
        rowEditorModel.addEditorForRow(pRow, defaultCellEditor);
    }
    
    private void getCmbEntidadFinanciera(int pRow, String pInSoloLectura) {
        ArrayList parametros = new ArrayList();
        FarmaLoadCVL.loadCVLFromSP(cmbEntidadFinanciera, "CMB_ENTIDAD_FINANCIERA",
                                   "PTOVENTA_CE_ERN.CE_OBTIENE_FINANCIERA", parametros, false, true);
        addKeyListenerToComboBox(cmbEntidadFinanciera, pInSoloLectura);
        defaultCellEditor = new DefaultCellEditor(cmbEntidadFinanciera);
        rowEditorModel.addEditorForRow(pRow, defaultCellEditor);
    }
    
    private void getCmbServicio(int pRow, String pInSoloLectura) {
        ArrayList parametros = new ArrayList();
        FarmaLoadCVL.loadCVLFromSP(cmbServicio, "CMB_SERVICIO", "PTOVENTA_CE_ERN.CE_OBTIENE_SERVICIOS", parametros,
                                   false, true);
        addKeyListenerToComboBox(cmbServicio, pInSoloLectura);
        defaultCellEditor = new DefaultCellEditor(cmbServicio);
        rowEditorModel.addEditorForRow(pRow, defaultCellEditor);
    }
    
    private void addKeyListenerToTextField2(final JTextField pJTextField, final String pTipoCampo,
                                            final String pInSoloLectura, final String pTipoMaestro,
                                            final JTextField pCodMaestro) {
        pJTextField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (pInSoloLectura.equalsIgnoreCase("S")) {
                    e.consume();
                } else {
                    if (pTipoCampo.equalsIgnoreCase("E"))
                        FarmaUtility.admitirDigitos(pJTextField, e);
                    else if (pTipoCampo.equalsIgnoreCase("D"))
                        FarmaUtility.admitirDigitosDecimales(pJTextField, e);
                }
            }

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    if ((tblLista.getSelectedRow() + 1) == tblLista.getRowCount())
                        FarmaUtility.setearRegistro(tblLista, tblLista.getSelectedRow(), null, 0);
                    else
                        FarmaUtility.setearRegistro(tblLista, tblLista.getSelectedRow() + 1, null, 0);
                    pJTextField.setText(pJTextField.getText().toUpperCase());
                } else
                    tblLista_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e) && pJTextField.isDisplayable()) {
                    pJTextField.selectAll();
                    //mostrar dialogo
                    VariablesPtoVenta.vTipoMaestro = pTipoMaestro;
                    VariablesCajaElectronica.vCodEntidadFinanciera = getCodEntidadFinanciera();
                    VariablesCajaElectronica.vCodTipoMoneda = getTipoMoneda();
                    if (VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_DELIVERY_PERDIDO)) {
                        VariablesCajaElectronica.vCodServicio = ConstantsCajaElectronica.CODIGO_SERVICIO_DELIVERY;
                    } else
                        VariablesCajaElectronica.vCodServicio = getCodServicio();
                    log.debug("Tipo Maestro: " + pTipoMaestro);
                    /**
           * Se vario el listado de maestro si era el tipo de trabajador como esta en la primera condicion
           * mostraria el codigo de RR.HH, y para el resto la lista de maestro existente
           * @author dubilluz
           * @since  22.11.2007
           */
                    if (pTipoMaestro.equals(ConstantsPtoVenta.LISTA_TRABAJADOR) ||
                        pTipoMaestro.equals(ConstantsPtoVenta.LISTA_TRABAJADOR_LOCAL) ||
                        pTipoMaestro.equals(ConstantsPtoVenta.LISTA_CAJERO)) {

                        DlgListaTrabajadores dlgListaTrabajadores = new DlgListaTrabajadores(myParentFrame, "", true);
                        dlgListaTrabajadores.setVisible(true);
                        if (FarmaVariables.vAceptar) {
                            pCodMaestro.setText(VariablesPtoVenta.vCodMaestro);
                            pJTextField.setText(VariablesPtoVenta.vDescMaestro);
                            log.debug("Codigo Maestro: " + pCodMaestro.getText());
                        }

                    } else {
                        DlgListaMaestros dlgListaMaestros = new DlgListaMaestros(myParentFrame, "", true);
                        dlgListaMaestros.setVisible(true);
                        if (FarmaVariables.vAceptar) {
                            pCodMaestro.setText(VariablesPtoVenta.vCodMaestro);
                            pJTextField.setText(VariablesPtoVenta.vDescMaestro);
                            log.debug("Codigo Maestro: " + pCodMaestro.getText());
                        }
                    }


                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    moveFocusTo(tblLista.getSelectedRow());
                } else {
                    if (e.getKeyCode() != KeyEvent.VK_LEFT && e.getKeyCode() != KeyEvent.VK_RIGHT &&
                        e.getKeyCode() != KeyEvent.VK_DELETE && e.getKeyCode() != KeyEvent.VK_BACK_SPACE &&
                        e.getKeyCode() != KeyEvent.VK_HOME &&
                        (!Character.isLetter(e.getKeyChar()) && !Character.isWhitespace(e.getKeyChar()) &&
                         !Character.isDigit(e.getKeyChar())))
                        pJTextField.setText(pJTextField.getText().toUpperCase());
                }
            }
        });
    }
    
    private String getCodServicio() {
        String codigo;
        log.debug("entro a getCodServicio");
        try {
            log.debug("index : " + cmbServicio.getSelectedIndex());
            codigo = FarmaLoadCVL.getCVLCode("CMB_SERVICIO", cmbServicio.getSelectedIndex());
            log.debug("codigo : " + codigo);
        } catch (ArrayIndexOutOfBoundsException e) {
            log.debug("se cae en codigo");
            codigo = "";
        }

        return codigo;
    }
    
    private String getCodEntidadFinanciera() {
        String codigo;

        try {
            codigo = FarmaLoadCVL.getCVLCode("CMB_ENTIDAD_FINANCIERA", cmbEntidadFinanciera.getSelectedIndex());
        } catch (ArrayIndexOutOfBoundsException e) {
            codigo = "";
        }
        log.debug(codigo);
        return codigo;
    }
    
    private void txtNumeroComp(int pRow, String pTipoCampo, String pInSoloLectura) {
        createJTextField(txtNumeroComp, pRow, pTipoCampo, pInSoloLectura);
    }

    private void txtMonto(int pRow, String pTipoCampo, String pInSoloLectura) {
        createJTextField(txtMonto, pRow, pTipoCampo, pInSoloLectura);
    }

    private void getTxtVuelto(int pRow, String pTipoCampo, String pInSoloLectura) {
        createJTextField(txtVuelto, pRow, pTipoCampo, pInSoloLectura);
    }

    private void getTxtSerieBillete(int pRow, String pTipoCampo, String pInSoloLectura) {
        createJTextField(txtSerieBillete, pRow, pTipoCampo, pInSoloLectura);
    }

    private void txtSerie(int pRow, String pTipoCampo, String pInSoloLectura) {
        createJTextField(txtSerie, pRow, pTipoCampo, pInSoloLectura);
    }

    private void createJTextField(JTextField pJTextField, int pRow, String pTipoCampo, String pInSoloLectura) {
        addKeyListenerToTextField(pJTextField, pTipoCampo, pInSoloLectura);
        defaultCellEditor = new DefaultCellEditor(pJTextField);
        rowEditorModel.addEditorForRow(pRow, defaultCellEditor);
    }

    /* ************************************************************************ */
    /* SECCION : CREACION DE CAMPOS JCOMBOBOX                                   */
    /* ************************************************************************ */

    private void cmbTipoComp(int pRow, String pInSoloLectura) {
        createJComboBox(cmbTipoComp, "CMB_TIPO_COMP", compValor, compDescripcion, pRow, pInSoloLectura);
    }

    private void cmbSerie(int pRow, String pInSoloLectura) {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaLoadCVL.loadCVLFromSP(cmbSerie, "CMB_SERIE", "PTOVENTA_CE_ERN.CE_LISTA_SERIES_COMPROBANTE(?,?)",
                                   parametros, true, true);
        addKeyListenerToComboBox(cmbSerie, pInSoloLectura);
        defaultCellEditor = new DefaultCellEditor(cmbSerie);
        rowEditorModel.addEditorForRow(pRow, defaultCellEditor);
    }

    private void getCmbTipoBillete(int pRow, String pInSoloLectura) {
        createJComboBox(cmbTipoBillete, "CMB_TIPO_BILLETE", compValTipoBillete, compDescTipoBillete, pRow,
                        pInSoloLectura);
    }

    private void getCmbTipoMoneda(int pRow, String pInSoloLectura) {
        FarmaLoadCVL.loadCVLfromArrays(cmbTipoMoneda, "CMB_TIPO_MONEDA", compValTipoMoneda, compDescTipoMoneda, true);
        addKeyListenerToComboBox(cmbTipoMoneda, pInSoloLectura);
        defaultCellEditor = new DefaultCellEditor(cmbTipoMoneda);
        rowEditorModel.addEditorForRow(pRow, defaultCellEditor);
    }

    private void getCmbFormaPago(int pRow, String pInSoloLectura) {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaLoadCVL.loadCVLFromSP(cmbFormaPago, "CMB_FORMA_PAGO", "PTOVENTA_CE_ERN.CE_OBTIENE_FORMAS_PAGO(?,?)",
                                   parametros, true, true);
        addKeyListenerToComboBox(cmbFormaPago, pInSoloLectura);
        defaultCellEditor = new DefaultCellEditor(cmbFormaPago);
        rowEditorModel.addEditorForRow(pRow, defaultCellEditor);
    }

    private void createJComboBox(JComboBox pJComboBox, String pNameComboBox, String[] pValue, String[] pDescription,
                                 int pRow, String pInSoloLectura) {
        FarmaLoadCVL.loadCVLfromArrays(pJComboBox, pNameComboBox, pValue, pDescription, true);
        addKeyListenerToComboBox(pJComboBox, pInSoloLectura);
        defaultCellEditor = new DefaultCellEditor(pJComboBox);
        rowEditorModel.addEditorForRow(pRow, defaultCellEditor);
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void addKeyListenerToTextField(final JTextField pJTextField, final String pTipoCampo,
                                           final String pInSoloLectura) {
        pJTextField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (pInSoloLectura.equalsIgnoreCase("S")) {
                    e.consume();
                } else {
                    if (pTipoCampo.equalsIgnoreCase("E"))
                        FarmaUtility.admitirDigitos(pJTextField, e);
                    else if (pTipoCampo.equalsIgnoreCase("D"))
                        FarmaUtility.admitirDigitosDecimales(pJTextField, e);
                }
            }

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    if ((tblLista.getSelectedRow() + 1) == tblLista.getRowCount())
                        FarmaUtility.setearRegistro(tblLista, tblLista.getSelectedRow(), null, 0);
                    else
                        FarmaUtility.setearRegistro(tblLista, tblLista.getSelectedRow() + 1, null, 0);
                    pJTextField.setText(pJTextField.getText().toUpperCase());
                } else
                    tblLista_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                if (pTipoCampo.equalsIgnoreCase("F")) {
                    String codigoCampo = FarmaUtility.getValueFieldJTable(tblLista, tblLista.getSelectedRow(), 2);
                    if (codigoCampo.trim().equals(ConstantsCajaElectronica.CAMPO_FECHA_OPERACION))
                        FarmaUtility.dateHourComplete(pJTextField, e);
                    else
                        FarmaUtility.dateComplete(pJTextField, e);
                }
                
                if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e) && pJTextField.isDisplayable()) {
                    pJTextField.selectAll();
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String codigoCampo = FarmaUtility.getValueFieldJTable(tblLista, tblLista.getSelectedRow(), 2);
                    if (codigoCampo.trim().equals(ConstantsCajaElectronica.CAMPO_NUMERO_CUENTA)) {
                        cargaNumeroCuenta();
                        txtNumeroCuenta.setText(VariablesPtoVenta.vDescMaestro);
                    }
                    if (codigoCampo.trim().equals(ConstantsCajaElectronica.CAMPO_NOMBRE_PROVEEDOR)) {
                        log.debug("Entro keyReleased");
                        txtProveedor.setText("");
                        vCodProveedor.setText("");
                    }
                    moveFocusTo(tblLista.getSelectedRow());
                } else {
                    if (e.getKeyCode() != KeyEvent.VK_LEFT && e.getKeyCode() != KeyEvent.VK_RIGHT &&
                        e.getKeyCode() != KeyEvent.VK_DELETE && e.getKeyCode() != KeyEvent.VK_BACK_SPACE &&
                        e.getKeyCode() != KeyEvent.VK_HOME &&
                        (!Character.isLetter(e.getKeyChar()) && !Character.isWhitespace(e.getKeyChar()) &&
                         !Character.isDigit(e.getKeyChar())))
                        pJTextField.setText(pJTextField.getText().toUpperCase());
                }
            }
        });
    }

    private void moveFocusTo(int pRow) {
        FarmaUtility.setearRegistro(tblLista, pRow, null, 0);
        tblLista.changeSelection(pRow, 1, false, false);
    }

    private void addKeyListenerToComboBox(final JComboBox pJComboBox, final String pInSoloLectura) {
        pJComboBox.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (pInSoloLectura.equalsIgnoreCase("S")) {
                    e.consume();
                }
            }

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (pJComboBox.isPopupVisible())
                        pJComboBox.setPopupVisible(false);
                    else {
                        if ((tblLista.getSelectedRow() + 1) == tblLista.getRowCount()) {
                            FarmaUtility.setearRegistro(tblLista, tblLista.getSelectedRow(), null, 0);
                        } else
                            FarmaUtility.setearRegistro(tblLista, tblLista.getSelectedRow() + 1, null, 0);
                    }
                }
            }

            public void keyReleased(KeyEvent e) {
                if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e) && pJComboBox.isDisplayable()) {
                    pJComboBox.setPopupVisible(true);
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (pJComboBox.isPopupVisible()) {
                        pJComboBox.setPopupVisible(false);
                    } else {
                        moveFocusTo(tblLista.getSelectedRow());
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    e.consume();
                    tblLista_keyPressed(e);
                }
            }
        });
        
        pJComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                log.debug("Entro addItemListener ");
                String codigoCampo = FarmaUtility.getValueFieldJTable(tblLista, tblLista.getSelectedRow(), 2);
                log.debug("codigoCampo : " + codigoCampo);
                if (codigoCampo.trim().equals(ConstantsCajaElectronica.CAMPO_ENTIDAD_FINANCIERA) ||
                    codigoCampo.trim().equals(ConstantsCajaElectronica.CAMPO_TIPO_MONEDA)) {
                    log.debug("Entro addItemListener CAMPO_ENTIDAD_FINANCIERA");
                    VariablesPtoVenta.vCodMaestro = "";
                    VariablesPtoVenta.vDescMaestro = "";
                    tblLista.repaint();
                }
                if (codigoCampo.trim().equals(ConstantsCajaElectronica.CAMPO_TIPO_MONEDA)) {
                    log.debug("Entro addItemListener CAMPO_TIPO_MONEDA");
                    getTipoMoneda();
                }
            };
        });
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(tblLista);
        FarmaUtility.centrarVentana(this);
        initTable();
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnLista_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblLista);
    }

    private void tblLista_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void checkComboBoxComponent() {
        String codigoCampo = "";
        codigoCampo = tblLista.getValueAt(tblLista.getSelectedRow(), 2).toString().trim();
        tblLista.getEditorComponent().requestFocus();
        if (codigoCampo.equals(ConstantsCajaElectronica.CAMPO_SERIE_COMPROBANTE))
            cmbSerie.setPopupVisible(true);
        else if (codigoCampo.equals(ConstantsCajaElectronica.CAMPO_TIPO_COMPROBANTE))
            cmbTipoComp.setPopupVisible(true);
        else if (codigoCampo.equals(ConstantsCajaElectronica.CAMPO_TIPO_DINERO))
            cmbTipoBillete.setPopupVisible(true);
        else if (codigoCampo.equals(ConstantsCajaElectronica.CAMPO_TIPO_MONEDA))
            cmbTipoMoneda.setPopupVisible(true);
        else if (codigoCampo.equals(ConstantsCajaElectronica.CAMPO_FORMA_PAGO))
            cmbFormaPago.setPopupVisible(true);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        //FarmaGridUtils.aceptarTeclaPresionada(e, tblLista, null, 0);

        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {
            if (FarmaVariables.vEconoFar_Matriz) {
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, null);
                return;
            }
            checkComboBoxComponent();
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (FarmaVariables.vEconoFar_Matriz) {
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, null);
                return;
            }
            funcionF11();
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
    
    private String getCodProveedor() {
        return vCodProveedor.getText().trim();
    }
    
    private void funcionF11() {
        if (validateMandatory()) {
            String serie, tipoComp, tipoBillete, tipoMoneda, formaPago, proveedor;

            try {
                serie = getSerie();
                tipoComp = getTipoComprobante();
                tipoBillete = getTipoBillete();
                tipoMoneda = getTipoMoneda();
                formaPago = getFormaPago();
                proveedor = getCodProveedor();
                if (getMonto() && getMontoPerdido()) {

                    // Se validara si la cuadratura debe de ingresar obligatoriamente motivo
                    // dubilluz 01/12/2008
                    if (!ingresaMotivoCuadratura(VariablesCajaElectronica.vCodCuadratura))
                        return;

                    DBCajaElectronica.agregaDatosCuadratura(serie, tipoComp, txtNumeroComp.getText().trim(), monto,
                                                            getVuelto(), tipoBillete, tipoMoneda, formaPago,
                                                            txtSerieBillete.getText().trim(), proveedor, montoPerdido,
                                                            txtMotivo.getText().trim());
                    FarmaUtility.aceptarTransaccion();
                    FarmaUtility.showMessage(this, "Datos de Cuadratura grabados correctamente", tblLista);
                    cerrarVentana(true);
                }
            } catch (SQLException e) {
                FarmaUtility.liberarTransaccion();
                if (e.getErrorCode() > 20000) {
                    FarmaUtility.showMessage(this, e.getMessage().substring(10, e.getMessage().indexOf("ORA-06512")),
                                             tblLista);
                } else {
                    log.error("", e);
                    FarmaUtility.showMessage(this, "Error al grabar la cuadratura.\n" +
                            e, tblLista);
                }

            }

        }
    }

    private boolean validateMandatory() {
        boolean dataExists = true;
        for (int i = 0; i < tblLista.getRowCount(); i++) {
            if (((String)tblLista.getValueAt(i, 5)).trim().equalsIgnoreCase("S")) {
                if (((String)tblLista.getValueAt(i,
                                                 2)).trim().equalsIgnoreCase(ConstantsCajaElectronica.CAMPO_SERIE_DINERO)) {
                    String tipDinero = getTipoBillete();
                    if (tipDinero.equalsIgnoreCase(FarmaConstants.DINERO_BILLETE)) {
                        if (txtSerieBillete.getText().equalsIgnoreCase("")) {
                            dataExists = false;
                            moveFocusTo(i);
                            FarmaUtility.showMessage(this,
                                                     "Campo " + findFieldDescription(((String)tblLista.getValueAt(i,
                                                                                                                  2)).trim()) +
                                                     " no tiene información.  Verifique !!!", null);
                            break;
                        } else
                            continue;
                    } else
                        continue;
                }
                if (((String)tblLista.getValueAt(i, 1)).trim().length() == 0) {
                    dataExists = false;
                    moveFocusTo(i);
                    FarmaUtility.showMessage(this,
                                             "Campo " + findFieldDescription(((String)tblLista.getValueAt(i, 2)).trim()) +
                                             " no tiene información.  Verifique !!!", null);
                    break;
                }
            }
        }
        return dataExists;
    }

    private String findFieldDescription(String pFieldValue) {
        String fieldDescription = new String("NO DETERMINADO");
        for (int i = 0; i < aCampos.size(); i++) {
            if (((String)((ArrayList)aCampos.get(i)).get(2)).equalsIgnoreCase(pFieldValue)) {
                fieldDescription = ((String)((ArrayList)aCampos.get(i)).get(0)).trim();
                break;
            }
        }
        return fieldDescription;
    }

    private String getSerie() {
        String serie;
        try {
            serie = txtSerie.getText().trim();
            //FarmaLoadCVL.getCVLCode("CMB_SERIE",cmbSerie.getSelectedIndex());
        } catch (ArrayIndexOutOfBoundsException e) {
            serie = "";
        }

        return serie;
    }

    private String getTipoComprobante() {
        String tipoComp;
        try {
            tipoComp = FarmaLoadCVL.getCVLCode("CMB_TIPO_COMP", cmbTipoComp.getSelectedIndex());
        } catch (ArrayIndexOutOfBoundsException e) {
            tipoComp = "";
        }

        return tipoComp;
    }


    private String getTipoBillete() {
        String tipoBillete;
        try {
            tipoBillete = FarmaLoadCVL.getCVLCode("CMB_TIPO_BILLETE", cmbTipoBillete.getSelectedIndex());
        } catch (ArrayIndexOutOfBoundsException e) {
            tipoBillete = "";
        }

        return tipoBillete;
    }

    private String getTipoMoneda() {
        String tipoMoneda;
        try {
            tipoMoneda = FarmaLoadCVL.getCVLCode("CMB_TIPO_MONEDA", cmbTipoMoneda.getSelectedIndex());
        } catch (ArrayIndexOutOfBoundsException e) {
            tipoMoneda = "";
        }

        return tipoMoneda;
    }

    private String getFormaPago() {
        String formaPago;

        try {
            formaPago = FarmaLoadCVL.getCVLCode("CMB_FORMA_PAGO", cmbFormaPago.getSelectedIndex());
        } catch (ArrayIndexOutOfBoundsException e) {
            formaPago = "";
        }

        return formaPago;
    }

    private boolean getMonto() {
        boolean retorno;
        String txtMon = txtMonto.getText().trim();
        double dMon;
        if (txtMon.length() == 0) {
            retorno = false;
            FarmaUtility.showMessage(this, "Debe ingresar un monto.", tblLista);
        } else if (!FarmaUtility.isDouble(txtMon)) {
            retorno = false;
            FarmaUtility.showMessage(this, "Ingrese un monto correcto.", tblLista);
        } else {
            dMon = FarmaUtility.getDecimalNumber(txtMon);
            if (dMon == 0.0) {
                retorno = false;
                FarmaUtility.showMessage(this, "No puede ingresar el monto 0.0", tblLista);
            } else {
                retorno = true;
                monto = dMon;
            }
        }

        return retorno;
    }

    private double getVuelto() {
        double vuelto;

        vuelto = FarmaUtility.getDecimalNumber(txtVuelto.getText().trim());

        return vuelto;
    }

    /**
     *
     * @param pCodCuadratura
     */
    private boolean ingresaMotivoCuadratura(String pCodCuadratura) throws SQLException {
        String pObligaMotivo = DBCajaElectronica.isMotivoCuadratura(pCodCuadratura.trim());
        if (pObligaMotivo.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
            DlgMotivoCuadratura dlgMotivo = new DlgMotivoCuadratura(myParentFrame, "", true);
            dlgMotivo.setVisible(true);
            if (FarmaVariables.vAceptar) {
                return true;
            } else
                return false;
        }

        return true;
    }
    
    private boolean getMontoPerdido(){
        boolean retorno;
        String txtMonPer = txtMontoPerdido.getText().trim();
        double dMonPer;
        if (txtMonPer.length() == 0) {
            retorno = true;
            //FarmaUtility.showMessage(this,"Debe ingresar un monto.",tblLista);
        } else if (!FarmaUtility.isDouble(txtMonPer)) {
            retorno = false;
            FarmaUtility.showMessage(this, "Ingrese un monto correcto.", tblLista);
        } else {
            dMonPer = FarmaUtility.getDecimalNumber(txtMonPer);
            if (dMonPer == 0.0) {
                retorno = false;
                FarmaUtility.showMessage(this, "No puede ingresar el monto 0.0", tblLista);
            } else {
                retorno = true;
                montoPerdido = dMonPer;
            }
        }
        return retorno;
    }
    
    private void cargaNumeroCuenta() {
        ArrayList infoCuenta = new ArrayList();
        try {
            if (VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_DEP_VENTA)) {
                VariablesCajaElectronica.vCodEntidadFinanciera = getCodEntidadFinanciera();
                log.debug("VariablesCajaElectronica.vCodEntidadFinanciera : " +
                          VariablesCajaElectronica.vCodEntidadFinanciera);
                VariablesCajaElectronica.vCodTipoMoneda = getTipoMoneda();
                infoCuenta = DBCajaElectronica.obtenerNumeroCuenta();
                if (infoCuenta.size() > 0) {
                    VariablesPtoVenta.vCodMaestro = ((String)((ArrayList)infoCuenta.get(0)).get(0)).trim();
                    VariablesPtoVenta.vDescMaestro = ((String)((ArrayList)infoCuenta.get(0)).get(1)).trim();
                    log.debug("Numero de Cuenta :" + VariablesPtoVenta.vDescMaestro);
                }
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrio un error al buscar el numero de cuenta \n" +
                    sql.getMessage(), tblLista);
        }
    }

}
