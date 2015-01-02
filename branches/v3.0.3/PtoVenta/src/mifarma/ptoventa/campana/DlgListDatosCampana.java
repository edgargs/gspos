package mifarma.ptoventa.campana;


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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import mifarma.common.FarmaJTable;
import mifarma.common.FarmaRowEditorModel;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.campana.reference.ConstantsCampana;
import mifarma.ptoventa.campana.reference.DBCampana;
import mifarma.ptoventa.campana.reference.VariablesCampana;
import mifarma.ptoventa.ce.reference.ConstantsCajaElectronica;
import mifarma.ptoventa.convenio.DlgDatosConvenio;
import mifarma.ptoventa.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListDatosCampana.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * DVELIZ      14.08.2008   Creación<br>
 * <br>
 * @author Daniel Fernando Veliz La Rosa<br>
 * @version 1.0<br>
 *
 */

public class DlgListDatosCampana extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgDatosConvenio.class);

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    Frame myParentFrame;
    FarmaTableModel tableModel;

    private FarmaRowEditorModel rowEditorModel;
    private DefaultCellEditor defaultCellEditor;
    private ArrayList aCampos = new ArrayList();
    private FarmaJTable tblLista = new FarmaJTable();

    private final int COL_DATO = 1;
    private final int COL_COD = 2;
    private final int COL_TIPO_DATO = 3;
    private final int COL_SOLO_LECTURA = 4;
    private final int COL_IND_OBLI = 5;
    private final int COL_IND_BUS = 6;

    /* ************************************************************************ */
    /*                               CAMPOS DE LA GRILLA                        */
    /* ************************************************************************ */

    private JTextFieldSanSerif txtNomCliente = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtNumeroDocumento = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtApellidoMaterno = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtApellidoPaterno = new JTextFieldSanSerif();

    private JTextFieldSanSerif txtTelefono = new JTextFieldSanSerif();

    private JTextFieldSanSerif txtNumCMP = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtMedico = new JTextFieldSanSerif();
    private JComboBox cmbSexo = new JComboBox();
    private JTextFieldSanSerif txtFechNac = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtSexo = new JTextFieldSanSerif();

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JScrollPane scrLista = new JScrollPane();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel btnLista = new JButtonLabel();
    private JLabelFunction lblF1 = new JLabelFunction();

    //dveliz 26.08.08
    private String mensajeESC = "";

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgListDatosCampana() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    public DlgListDatosCampana(Frame parent, String title, boolean modal) {
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
        this.getContentPane().setLayout(borderLayout1);
        this.setSize(new Dimension(583, 306));
        this.setDefaultCloseOperation(0);
        this.setTitle("Datos de Convenio");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        lblEsc.setBounds(new Rectangle(465, 245, 95, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblF11.setBounds(new Rectangle(350, 245, 105, 20));
        lblF11.setText("[ F11 ] Aceptar");
        scrLista.setBounds(new Rectangle(10, 30, 555, 205));
        tblLista.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblLista_keyPressed(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(10, 10, 555, 20));
        btnLista.setText("Lista Datos");
        btnLista.setBounds(new Rectangle(5, 0, 105, 20));
        btnLista.setMnemonic('L');
        btnLista.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLista_actionPerformed(e);
            }
        });
        lblF1.setBounds(new Rectangle(20, 245, 125, 20));
        lblF1.setText("[ F1 ] Ingresar Dato");
        scrLista.getViewport().add(tblLista, null);
        pnlTitle1.add(btnLista, null);
        //jContentPane.add(lblF1, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(scrLista, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);

        txtNomCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtNombre_keyTyped(e);
            }
        });

        txtApellidoPaterno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtApellidoPaterno_keyTyped(e);
            }
        });

        txtApellidoMaterno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtApellidoMaterno_keyTyped(e);
            }
        });

        txtNumeroDocumento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtNumeroDocumento_keyTyped(e);
            }
        });

        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtTelefono_keyTyped(e);
            }
        });

        txtNumCMP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtNumeroCMP_keyTyped(e);
            }
        });

        txtMedico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtMedico_keyTyped(e);
            }
        });

        txtFechNac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtFechaNacimiento_keyTyped(e);
            }
        });

        txtSexo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtSexo_keyTyped(e);
            }


        });
        cmbSexo.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtSexo_keyTyped(e);
            }
        });
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        this.setTitle("Datos Campaña - " + VariablesCampana.vDescCamp);
        VariablesCampana.vNumIngreso = 0;
        VariablesCampana.vFlagMandatory = false;
        VariablesCampana.vSexoExists = false;
        //initTable();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {

        tableModel =
                new FarmaTableModel(ConstantsCampana.columnsListaDatosCampana, ConstantsCampana.defaultValuesListaDatosCampana,
                                    0, ConstantsCampana.editableListaDatosCampana, null);
        rowEditorModel = new FarmaRowEditorModel();
        tblLista.setAutoCreateColumnsFromModel(false);
        tblLista.setModel(tableModel);
        tblLista.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        for (int k = 0; k < tableModel.getColumnCount(); k++) {
            TableColumn column = new TableColumn(k, ConstantsCampana.columnsListaDatosCampana[k].m_width);
            tblLista.addColumn(column);
        }

        tblLista.setRowEditorModel(rowEditorModel);
        cargaCampos();
        // Verifica si no tiene campos para ingresar acepta el convenio
        if (tblLista.getRowCount() == 0) {
            if (FarmaVariables.vEconoFar_Matriz) {
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, null);
                return;
            }
            funcionF11();
        }
    }

    private void cargaCampos() {
        cargaLista();
        setTipoCampo();
        tblLista.repaint();
    }

    private void cargaLista() {
        try {
            DBCampana.listaCamposCampana(tableModel);
            if (tableModel.data.size() == 0) {
                cerrarVentana(true);
            }
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al cargar los campos.", tblLista);
        }
        aCampos = tableModel.data;
    }

    /**
     * Este procedimiento determinará el tipo de dato de cada campo segun
     * lo registrado en la BD.
     */
    private void setTipoCampo() {
        String codigoCampo = "";
        String vTipoDato;
        String vIndSoloLec;
        //Dveliz 26.08.08
        String vIndOblig;
        for (int i = 0; i < tblLista.getRowCount(); i++) {
            codigoCampo = tblLista.getValueAt(i, COL_COD).toString().trim();
            vTipoDato = FarmaUtility.getValueFieldJTable(tblLista, i, COL_TIPO_DATO);
            vIndSoloLec = FarmaUtility.getValueFieldJTable(tblLista, i, COL_SOLO_LECTURA);

            //dveliz 26.08.08
            vIndOblig = FarmaUtility.getValueFieldJTable(tblLista, i, COL_IND_OBLI);
            if (vIndOblig.equals("S")) {
                VariablesCampana.vFlagMandatory = true;
            }
            //fin dveliz

            //dveliz 27.08.08
            if (i == 0) {
                if (codigoCampo.equals(ConstantsCampana.DNI_CLIENTE)) {
                    getTxtNumeroDocumento(i, vTipoDato, vIndSoloLec);
                    FarmaUtility.moveFocus(txtNumeroDocumento);
                } else if (codigoCampo.equals(ConstantsCampana.APEPAT_CLIENTE)) {
                    getTxtApellidoPaterno(i, vTipoDato, vIndSoloLec);
                    FarmaUtility.moveFocus(txtApellidoPaterno);
                } else if (codigoCampo.equals(ConstantsCampana.APEMAT_CLIENTE)) {
                    getTxtApellidoMaterno(i, vTipoDato, vIndSoloLec);
                    FarmaUtility.moveFocus(txtApellidoMaterno);
                } else if (codigoCampo.equals(ConstantsCampana.NOMBRE_CLIENTE)) {
                    getTxtNombre(i, vTipoDato, vIndSoloLec);
                    FarmaUtility.moveFocus(txtNomCliente);
                } else if (codigoCampo.equals(ConstantsCampana.TELEFONO_CLIENTE)) {
                    getTxtTelefono(i, vTipoDato, vIndSoloLec);
                    FarmaUtility.moveFocus(txtTelefono);
                } else if (codigoCampo.equals(ConstantsCampana.FECHA_NAC_CLIENTE)) {
                    getTxtFechaNacimiento(i, vTipoDato, vIndSoloLec);
                    FarmaUtility.moveFocus(txtFechNac);
                } else if (codigoCampo.equals(ConstantsCampana.NUM_CMP)) {
                    getTxtNumCMP(i, vTipoDato, vIndSoloLec);
                    FarmaUtility.moveFocus(txtNumCMP);
                } else if (codigoCampo.equals(ConstantsCampana.NOMBRE_MEDICO)) {
                    getTxtMedico(i, vTipoDato, vIndSoloLec);
                    FarmaUtility.moveFocus(txtMedico);
                } else if (codigoCampo.equals(ConstantsCampana.SEXO_CLIENTE)) {
                    VariablesCampana.vSexoExists = true;
                    getCmbSexo(i);
                    FarmaUtility.moveFocus(cmbSexo);
                }
            } else {
                if (codigoCampo.equals(ConstantsCampana.DNI_CLIENTE)) {
                    getTxtNumeroDocumento(i, vTipoDato, vIndSoloLec);
                } else if (codigoCampo.equals(ConstantsCampana.APEPAT_CLIENTE)) {
                    getTxtApellidoPaterno(i, vTipoDato, vIndSoloLec);
                } else if (codigoCampo.equals(ConstantsCampana.APEMAT_CLIENTE)) {
                    getTxtApellidoMaterno(i, vTipoDato, vIndSoloLec);
                } else if (codigoCampo.equals(ConstantsCampana.NOMBRE_CLIENTE)) {
                    getTxtNombre(i, vTipoDato, vIndSoloLec);
                } else if (codigoCampo.equals(ConstantsCampana.TELEFONO_CLIENTE)) {
                    getTxtTelefono(i, vTipoDato, vIndSoloLec);
                } else if (codigoCampo.equals(ConstantsCampana.FECHA_NAC_CLIENTE)) {
                    getTxtFechaNacimiento(i, vTipoDato, vIndSoloLec);
                } else if (codigoCampo.equals(ConstantsCampana.NUM_CMP)) {
                    getTxtNumCMP(i, vTipoDato, vIndSoloLec);
                } else if (codigoCampo.equals(ConstantsCampana.NOMBRE_MEDICO)) {
                    getTxtMedico(i, vTipoDato, vIndSoloLec);
                } else if (codigoCampo.equals(ConstantsCampana.SEXO_CLIENTE)) {
                    VariablesCampana.vSexoExists = true;
                    getCmbSexo(i);
                }
            }
        }
    }

    private void getTxtNumeroDocumento(int i, String vTipoDato, String vIndSoloLec) {
        txtNumeroDocumento.setLengthText(10);
        createJTextField(txtNumeroDocumento, i, vTipoDato, vIndSoloLec);
    }

    private void getTxtApellidoPaterno(int i, String vTipoDato, String vIndSoloLec) {
        txtApellidoPaterno.setLengthText(30);
        createJTextField(txtApellidoPaterno, i, vTipoDato, vIndSoloLec);
    }

    private void getTxtApellidoMaterno(int i, String vTipoDato, String vIndSoloLec) {
        txtApellidoMaterno.setLengthText(30);
        createJTextField(txtApellidoMaterno, i, vTipoDato, vIndSoloLec);
    }

    private void getTxtTelefono(int i, String vTipoDato, String vIndSoloLec) {
        txtTelefono.setLengthText(10);
        createJTextField(txtTelefono, i, vTipoDato, vIndSoloLec);
    }

    private void getTxtFechaNacimiento(int i, String vTipoDato, String vIndSoloLec) {
        txtFechNac.setLengthText(11);
        createJTextField(txtFechNac, i, vTipoDato, vIndSoloLec);
    }

    private void getTxtNombre(int i, String vTipoDato, String vIndSoloLec) {
        txtNomCliente.setLengthText(30);
        createJTextField(txtNomCliente, i, vTipoDato, vIndSoloLec);
    }

    private void getTxtNumCMP(int i, String vTipoDato, String vIndSoloLec) {
        txtNumCMP.setLengthText(10);
        createJTextField(txtNumCMP, i, vTipoDato, vIndSoloLec);
    }

    private void getTxtMedico(int i, String vTipoDato, String vIndSoloLec) {
        txtMedico.setLengthText(100);
        createJTextField(txtMedico, i, vTipoDato, vIndSoloLec);
    }

    public void getCmbSexo(int i) {
        cmbSexo.addItem("");
        cmbSexo.addItem("MASCULINO");
        cmbSexo.addItem("FEMENINO");
        createJComboBox(cmbSexo, i);
    }

    private void createJTextField(JTextField pJTextField, int pRow, String pTipoCampo, String pInSoloLectura) {
        addKeyListenerToTextField(pJTextField, pTipoCampo, pInSoloLectura);
        defaultCellEditor = new DefaultCellEditor(pJTextField);
        rowEditorModel.addEditorForRow(pRow, defaultCellEditor);
    }

    private void createJComboBox(JComboBox pJComboBox, int pRow) {
        addKeyListenerJComboBox(pJComboBox);
        defaultCellEditor = new DefaultCellEditor(pJComboBox);
        rowEditorModel.addEditorForRow(pRow, defaultCellEditor);
    }

    /* ************************************************************************ */
    /* SECCION : CREACION DE CAMPOS JCOMBOBOX                                   */
    /* ************************************************************************ */


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
                    else if (pTipoCampo.equalsIgnoreCase("F"))
                        FarmaUtility.dateComplete(pJTextField, e);
                }
            }

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    int vFila = tblLista.getSelectedRow();

                    if ((vFila + 1) == tblLista.getRowCount())
                        //dveliz 27.08.08
                        moveFocusTo(0);
                    // fin dveliz
                    else
                        FarmaUtility.setearRegistro(tblLista, vFila + 1, null, 0);

                    pJTextField.setText(pJTextField.getText().toUpperCase());
                } else
                    tblLista_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                int vFila = tblLista.getSelectedRow();

                if (pTipoCampo.equalsIgnoreCase("F")) {
                    FarmaUtility.dateComplete(pJTextField, e);
                }

                if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e) && pJTextField.isDisplayable()) {
                    pJTextField.selectAll();
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if ((vFila + 1) < tblLista.getRowCount())
                        moveFocusTo(vFila);

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

    private void addKeyListenerToTextField2(final JTextField pJTextField, final String pTipoCampo,
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
                //log.info("keypressed : " + e.getKeyCode());
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    int vFila = tblLista.getSelectedRow();

                    if ((vFila + 1) == tblLista.getRowCount())
                        FarmaUtility.setearRegistro(tblLista, vFila, null, 0);
                    else
                        FarmaUtility.setearRegistro(tblLista, vFila + 1, null, 0);

                    pJTextField.setText(pJTextField.getText().toUpperCase());
                } else
                    tblLista_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {

                int vFila = tblLista.getSelectedRow();

                if (pTipoCampo.equalsIgnoreCase("F")) {
                    String codigoCampo = FarmaUtility.getValueFieldJTable(tblLista, vFila, COL_COD);
                    if (codigoCampo.trim().equals(ConstantsCajaElectronica.CAMPO_FECHA_OPERACION))
                        FarmaUtility.dateHourComplete(pJTextField, e);
                    else
                        FarmaUtility.dateComplete(pJTextField, e);
                }

                if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e) && pJTextField.isDisplayable()) {
                    pJTextField.selectAll();
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    moveFocusTo(vFila);
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

    private void addKeyListenerJComboBox(final JComboBox pJComboBox) {

        pJComboBox.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {

            }

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (pJComboBox.isPopupVisible())
                        pJComboBox.setPopupVisible(false);
                    else {
                        if ((tblLista.getSelectedRow() + 1) == tblLista.getRowCount()) {
                            FarmaUtility.setearRegistro(tblLista, tblLista.getSelectedRow(), null, 0);
                        } else {
                            FarmaUtility.setearRegistro(tblLista, tblLista.getSelectedRow() + 1, null, 0);
                        }
                    }
                }
            }

            public void keyReleased(KeyEvent e) {
                if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e) && pJComboBox.isDisplayable()) {
                    pJComboBox.setPopupVisible(true);
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (pJComboBox.isPopupVisible()) {
                        pJComboBox.setPopupVisible(false);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    e.consume();
                    tblLista_keyPressed(e);
                }
            }
        });

    }

    private void moveFocusTo(int pRow) {
        FarmaUtility.setearRegistro(tblLista, pRow, null, 0);
        tblLista.changeSelection(pRow, 1, false, false);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        initTable();
        //FarmaUtility.moveFocus(tblLista);
        tblLista.changeSelection(0, 1, false, false);
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

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        //FarmaGridUtils.aceptarTeclaPresionada(e, tblLista, null, 0);

        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {
            if (FarmaVariables.vEconoFar_Matriz) {
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, null);
                return;
            }
            checkComboBoxComponent();
        } /*else if (e.getKeyCode() == KeyEvent.VK_M)
      {
        if(FarmaVariables.vEconoFar_Matriz){
          FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,null);
          return;
        }
        checkComboBoxComponent();
      }*/ else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (FarmaVariables.vEconoFar_Matriz) {
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, null);
                return;
            }
            funcionF11();
            if (VariablesCampana.vNumIngreso == 1)
                cerrarVentana(true);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            // DVELIZ 26.08.08
            if (VariablesCampana.vFlagMandatory == true) {
                mensajeESC = "Si sale de la interfaz no se usara el cupon " + VariablesCampana.vCodCupon;
                VariablesCampana.vNumIngreso = 0;
                if (JOptionPane.showConfirmDialog(this, mensajeESC, "Mensaje de Confirmacion - Mifarma",
                                                  JOptionPane.YES_NO_OPTION) == 0) {
                    VariablesCampana.vFlag = false;
                    cerrarVentana(false);
                }
            } else {
                mensajeESC = "Seguro de salir de la interfaz de datos de cliente?";
                VariablesCampana.vNumIngreso = 1;
                if (JOptionPane.showConfirmDialog(this, mensajeESC, "Mensaje de Confirmacion - Mifarma",
                                                  JOptionPane.YES_NO_OPTION) == 0) {
                    funcionF11();
                    VariablesCampana.vFlag = false;
                    cerrarVentana(false);
                }
            }
            //Fin DVELIZ
        }
    }

    public void txtNombre_keyTyped(KeyEvent e) {
        FarmaUtility.admitirLetras(txtNomCliente, e);
    }

    public void txtApellidoPaterno_keyTyped(KeyEvent e) {
        FarmaUtility.admitirLetras(txtApellidoPaterno, e);
    }

    private void txtSexo_keyTyped(KeyEvent e) {
        FarmaUtility.admitirLetras(txtSexo, e);
    }

    public void txtApellidoMaterno_keyTyped(KeyEvent e) {
        FarmaUtility.admitirLetras(txtApellidoMaterno, e);
    }

    public void txtNumeroDocumento_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtNumeroDocumento, e);
    }

    public void txtNumeroCMP_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtNumCMP, e);
    }

    public void txtMedico_keyTyped(KeyEvent e) {
        FarmaUtility.admitirLetras(txtMedico, e);
    }

    public void txtTelefono_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtTelefono, e);
    }

    public void txtFechaNacimiento_keyTyped(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechNac, e);
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void funcionF11() {
        if (validateMandatory()) {
            try {
                String vSexo = "null";
                if (VariablesCampana.vSexoExists == true) {
                    vSexo = cmbSexo.getSelectedItem().toString().trim();
                }
                VariablesCampana.vDataCliente = new ArrayList();
                VariablesCampana.vDataCliente.add(VariablesCampana.vCodCampana);
                VariablesCampana.vDataCliente.add(VariablesCampana.vCodCupon);
                VariablesCampana.vDataCliente.add(VariablesCampana.vCodCli);

                VariablesCampana.vDataCliente.add(txtNumeroDocumento.getText().trim());
                VariablesCampana.vDataCliente.add(txtNomCliente.getText().trim());
                VariablesCampana.vDataCliente.add(txtApellidoPaterno.getText().trim());
                VariablesCampana.vDataCliente.add(txtApellidoMaterno.getText().trim());
                VariablesCampana.vDataCliente.add(txtTelefono.getText().trim());
                VariablesCampana.vDataCliente.add(txtNumCMP.getText().trim());
                VariablesCampana.vDataCliente.add(txtMedico.getText().trim());
                VariablesCampana.vDataCliente.add(vSexo);
                VariablesCampana.vDataCliente.add(txtFechNac.getText().trim());

                VariablesCampana.vListaCupones.add(VariablesCampana.vDataCliente);

                VariablesCampana.vNumIngreso = 1;

            } catch (Exception e) {
                log.error("", e);
            }

        } else {
            VariablesCampana.vFlag = false;
        }
    }

    private boolean validateMandatory() {
        boolean dataExists = true;
        String vIndObligatorio;
        String vCodCampo;
        String vDato;

        for (int i = 0; i < tblLista.getRowCount(); i++) {
            vIndObligatorio = FarmaUtility.getValueFieldJTable(tblLista, i, COL_IND_OBLI);

            if (vIndObligatorio.equalsIgnoreCase("S")) {
                vCodCampo = FarmaUtility.getValueFieldJTable(tblLista, i, COL_COD);
                vDato = FarmaUtility.getValueFieldJTable(tblLista, i, COL_DATO);

                if (vDato.length() == 0) {
                    dataExists = false;
                    moveFocusTo(i);
                    FarmaUtility.showMessage(this,
                                             "Campo " + findFieldDescription(vCodCampo) + " no tiene información.  Verifique !!!",
                                             null);
                    break;
                }

                if (vCodCampo.equals(ConstantsCampana.FECHA_NAC_CLIENTE)) {
                    if (!FarmaUtility.validaFecha(vDato, "dd/MM/yyyy")) {
                        dataExists = false;
                        moveFocusTo(i);
                        FarmaUtility.showMessage(this,
                                                 "Campo " + findFieldDescription(vCodCampo) + " contiene un fecha invalida.  Verifique !!!",
                                                 null);
                        break;
                    }
                }

            }
        }
        return dataExists;
    }

    private String findFieldDescription(String pFieldValue) {
        String fieldDescription = "NO DETERMINADO";
        for (int i = 0; i < aCampos.size(); i++) {
            if (((String)((ArrayList)aCampos.get(i)).get(2)).equalsIgnoreCase(pFieldValue)) {
                fieldDescription = ((String)((ArrayList)aCampos.get(i)).get(0)).trim();
                break;
            }
        }
        return fieldDescription;
    }

    private void checkComboBoxComponent() {
        String codigoCampo = "";
        codigoCampo = tblLista.getValueAt(tblLista.getSelectedRow(), 2).toString().trim();
        //tblLista.getEditorComponent().requestFocus();
        if (codigoCampo.equals(ConstantsCampana.SEXO_CLIENTE))
            cmbSexo.setPopupVisible(true);
    }
}
