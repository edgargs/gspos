package mifarma.ptoventa.campAcumulada;


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

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaJTable;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaRowEditorModel;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.campAcumulada.reference.ConstantsCampAcumulada;
import mifarma.ptoventa.campAcumulada.reference.DBCampAcumulada;
import mifarma.ptoventa.campAcumulada.reference.UtilityCampAcumulada;
import mifarma.ptoventa.campAcumulada.reference.VariablesCampAcumulada;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgRegDatosCliente.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCALLO      15.12.2008   Creación<br>
 * <br>
 * @author JCALLO<br>
 * @version 1.0<br>
 *
 */

public class DlgRegDatosCliente extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgRegDatosCliente.class);

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

    /* ************************************************************************ */
    /*                               CAMPOS DE LA GRILLA                        */
    /* ************************************************************************ */

    private JTextFieldSanSerif txtNomCliente = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtNumeroDocumento = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtApellidoMaterno = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtApellidoPaterno = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtEmail = new JTextFieldSanSerif();

    private JTextFieldSanSerif txtTelefono = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtCelular = new JTextFieldSanSerif();

    private JComboBox cmbSexo = new JComboBox();
    private String[] compDescripcion = { "MASCULINO", "FEMENINO" };
    private String[] compValor = { "M", "F" };

    private JTextFieldSanSerif txtFechNac = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtSexo = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtDireccion = new JTextFieldSanSerif();

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JScrollPane scrLista = new JScrollPane();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel btnLista = new JButtonLabel();
    //private JLabelFunction lblF1 = new JLabelFunction();

    private String mensajeESC = "";
    private String pCodigoCampoAux = "";

    /**
     */


    private final int COL_DNI = 0;
    private final int COL_NOM_CLI = 1;
    private final int COL_APE_PAT = 2;
    private final int COL_APE_MAT = 3;
    private final int COL_SEX_CLI = 4;
    private final int COL_FEC_NAC = 5;
    private final int COL_DIR_CLI = 6;
    private final int COL_TLF_CLI = 7;
    private final int COL_CEL_CLI = 8;
    private final int COL_EMAIL = 9;
    private JLabelFunction lblF2 = new JLabelFunction();


    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgRegDatosCliente() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public DlgRegDatosCliente(Frame parent, String title, boolean modal) {
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
        jContentPane.setSize(new Dimension(520, 272));
        this.setSize(new Dimension(530, 313));
        this.setDefaultCloseOperation(0);
        this.setTitle("Datos del Cliente ");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        lblEsc.setBounds(new Rectangle(420, 244, 95, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblF11.setBounds(new Rectangle(285, 244, 105, 20));
        lblF11.setText("[ F11 ] Aceptar");
        scrLista.setBounds(new Rectangle(10, 30, 505, 205));
        tblLista.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblLista_keyPressed(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(10, 10, 505, 20));
        btnLista.setText("Lista Datos");
        btnLista.setBounds(new Rectangle(5, 0, 105, 20));
        btnLista.setMnemonic('L');
        btnLista.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLista_actionPerformed(e);
            }
        });
        lblF2.setBounds(new Rectangle(20, 245, 125, 20));
        lblF2.setText("[ F2 ] Ingresar Dato");
        /*lblf1.setBounds(new Rectangle(20, 244, 120, 20));
        lblf1.setVisible(true);*/
        scrLista.getViewport().add(tblLista, null);
        pnlTitle1.add(btnLista, null);
        jContentPane.add(lblF2, null);
        //jContentPane.add(lblf1, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(scrLista, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);

        txtNomCliente.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtNombre_keyTyped(e);
            }
        });
        //txtNomCliente.setText("");

        txtApellidoPaterno.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtApellidoPaterno_keyTyped(e);
            }
        });

        txtApellidoMaterno.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtApellidoMaterno_keyTyped(e);
            }
        });


        txtNumeroDocumento.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtNumeroDocumento_keyTyped(e);
            }
        });

        txtTelefono.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtTelefono_keyTyped(e);
            }
        });

        txtCelular.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtCelular_keyTyped(e);
            }
        });

        txtDireccion.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtDireccion_keyTyped(e);
            }
        });

        txtFechNac.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtFechaNacimiento_keyTyped(e);
            }
        });

        txtSexo.addKeyListener(new KeyAdapter() {
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

    /* ********************************************************************** */
    /*                                  METODO initialize                     */
    /* ********************************************************************** */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        try {
            VariablesCampAcumulada.vDocValidos = DBCampAcumulada.obtenerParamDocIden();
        } catch (SQLException e) {
            log.debug("error : " + e);
            FarmaUtility.showMessage(this, "Error al obtener Parametro de Doc validos !", tblLista);
        }
        log.debug("longitud de docs validos : " + VariablesCampAcumulada.vDocValidos);
    }

    /* ********************************************************************** */
    /*                            METODOS INICIALIZACION                      */
    /* ********************************************************************** */

    private void initTable() {

        tableModel =
                new FarmaTableModel(ConstantsCampAcumulada.columnsListaDatosClienteCampAcumulada, ConstantsCampAcumulada.defaultValuesListaDatosClienteCampAcumulada,
                                    0, ConstantsCampAcumulada.editableListaDatosClienteCampAcumulada, null);
        rowEditorModel = new FarmaRowEditorModel();
        tblLista.setAutoCreateColumnsFromModel(false);
        tblLista.setModel(tableModel);
        tblLista.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        for (int k = 0; k < tableModel.getColumnCount(); k++) {
            TableColumn column =
                new TableColumn(k, ConstantsCampAcumulada.columnsListaDatosClienteCampAcumulada[k].m_width);
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
            //funcionF11();
        }
    }

    private void cargaCampos() {
        cargaLista();
        setTipoCampo();
        tblLista.repaint();
        //cargaDatosCliente();
        //  tblLista.setValueAt("11s",1,1);//VariablesConvenio.vNomCliente,posRowNomCli,COL_DATO);
        //tblLista.show();
    }

    private int buscaPosFila(String pCodigoCampo) {
        String codAux = "";
        for (int i = 0; i < tblLista.getRowCount(); i++) {
            codAux =
                    tableModel.getValueAt(i, 2).toString().trim(); // FarmaUtility.getValueFieldJTable(tblLista, i, 2);
            if (pCodigoCampo.trim().equalsIgnoreCase(codAux.trim()))
                return i;
        }
        return -1;
    }

    private void cargaLista() {
        try {
            DBCampAcumulada.listarCamposClienteCampAcumulacion(tableModel);
            if (tableModel.data.size() == 0) {
                cerrarVentana(true);
            }
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al cargar los campos.", tblLista);
        }
        /****/
        /**limpiando datos de columnda datos del cliente*/
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            tableModel.setValueAt("", i, 1);
        }
        /****/
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
        String vIndOblig;
        for (int i = 0; i < tblLista.getRowCount(); i++) {
            codigoCampo = tableModel.getValueAt(i, COL_COD).toString().trim();
            vTipoDato = tableModel.getValueAt(i, COL_TIPO_DATO).toString().trim();
            vIndSoloLec = tableModel.getValueAt(i, COL_SOLO_LECTURA).toString().trim();
            vIndOblig = tableModel.getValueAt(i, COL_IND_OBLI).toString().trim();

            if (vIndOblig.equals("S")) {
                VariablesCampAcumulada.vFlagMandatory = true;
            }

            if (i == 0) {
                if (codigoCampo.equals(ConstantsCampAcumulada.DNI_CLIENTE)) {
                    getTxtNumeroDocumento(i, vTipoDato, vIndSoloLec);
                    FarmaUtility.moveFocus(txtNumeroDocumento);
                } else if (codigoCampo.equals(ConstantsCampAcumulada.NOMBRE_CLIENTE)) {
                    getTxtNombre(i, vTipoDato, vIndSoloLec);
                    FarmaUtility.moveFocus(txtNomCliente);
                } else if (codigoCampo.equals(ConstantsCampAcumulada.APEPAT_CLIENTE)) {
                    getTxtApellidoPaterno(i, vTipoDato, vIndSoloLec);
                    FarmaUtility.moveFocus(txtApellidoPaterno);
                } else if (codigoCampo.equals(ConstantsCampAcumulada.APEMAT_CLIENTE)) {
                    getTxtApellidoPaterno(i, vTipoDato, vIndSoloLec);
                    FarmaUtility.moveFocus(txtApellidoMaterno);
                } else if (codigoCampo.equals(ConstantsCampAcumulada.SEXO_CLIENTE)) {
                    VariablesCampAcumulada.vSexoExists = true;
                    getCmbSexo(i);
                    FarmaUtility.moveFocus(cmbSexo);
                } else if (codigoCampo.equals(ConstantsCampAcumulada.FECHA_NAC_CLIENTE)) {
                    getTxtFechaNacimiento(i, vTipoDato, vIndSoloLec);
                    FarmaUtility.moveFocus(txtFechNac);
                } else if (codigoCampo.equals(ConstantsCampAcumulada.DIREC_CLIENTE)) {
                    getTxtDireccion(i, vTipoDato, vIndSoloLec);
                    FarmaUtility.moveFocus(txtDireccion);
                } else if (codigoCampo.equals(ConstantsCampAcumulada.TELEFONO_CLIENTE)) {
                    getTxtTelefono(i, vTipoDato, vIndSoloLec);
                    FarmaUtility.moveFocus(txtTelefono);
                } else if (codigoCampo.equals(ConstantsCampAcumulada.CELULAR_CLIENTE)) {
                    getTxtCelular(i, vTipoDato, vIndSoloLec);
                    FarmaUtility.moveFocus(txtCelular);
                } else if (codigoCampo.equals(ConstantsCampAcumulada.EMAIL_CLIENTE)) {
                    getTxtEmail(i, vTipoDato, vIndSoloLec);
                    FarmaUtility.moveFocus(txtApellidoMaterno);
                }
            } else {
                if (codigoCampo.equals(ConstantsCampAcumulada.DNI_CLIENTE)) {
                    getTxtNumeroDocumento(i, vTipoDato, vIndSoloLec);
                } else if (codigoCampo.equals(ConstantsCampAcumulada.NOMBRE_CLIENTE)) {
                    getTxtNombre(i, vTipoDato, vIndSoloLec);
                } else if (codigoCampo.equals(ConstantsCampAcumulada.APEPAT_CLIENTE)) {
                    getTxtApellidoPaterno(i, vTipoDato, vIndSoloLec);
                } else if (codigoCampo.equals(ConstantsCampAcumulada.APEMAT_CLIENTE)) {
                    getTxtApellidoMaterno(i, vTipoDato, vIndSoloLec);
                } else if (codigoCampo.equals(ConstantsCampAcumulada.SEXO_CLIENTE)) {
                    VariablesCampAcumulada.vSexoExists = true;
                    getCmbSexo(i);
                } else if (codigoCampo.equals(ConstantsCampAcumulada.FECHA_NAC_CLIENTE)) {
                    getTxtFechaNacimiento(i, vTipoDato, vIndSoloLec);
                } else if (codigoCampo.equals(ConstantsCampAcumulada.DIREC_CLIENTE)) {
                    getTxtDireccion(i, vTipoDato, vIndSoloLec);
                } else if (codigoCampo.equals(ConstantsCampAcumulada.TELEFONO_CLIENTE)) {
                    getTxtTelefono(i, vTipoDato, vIndSoloLec);
                } else if (codigoCampo.equals(ConstantsCampAcumulada.CELULAR_CLIENTE)) {
                    getTxtCelular(i, vTipoDato, vIndSoloLec);
                } else if (codigoCampo.equals(ConstantsCampAcumulada.EMAIL_CLIENTE)) {
                    getTxtEmail(i, vTipoDato, vIndSoloLec);
                }
            }
        }
    }

    private void getTxtNumeroDocumento(int i, String vTipoDato, String vIndSoloLec) {
        //txtNumeroDocumento.setLengthText(8);
        txtNumeroDocumento.setText("");
        createJTextField(txtNumeroDocumento, i, vTipoDato, vIndSoloLec);
    }

    private void getTxtApellidoPaterno(int i, String vTipoDato, String vIndSoloLec) {
        txtApellidoPaterno.setLengthText(30);
        txtApellidoPaterno.setText("");
        createJTextField(txtApellidoPaterno, i, vTipoDato, vIndSoloLec);
    }

    private void getTxtApellidoMaterno(int i, String vTipoDato, String vIndSoloLec) {
        txtApellidoMaterno.setLengthText(30);
        txtApellidoMaterno.setText("");
        createJTextField(txtApellidoMaterno, i, vTipoDato, vIndSoloLec);
    }

    private void getTxtTelefono(int i, String vTipoDato, String vIndSoloLec) {
        txtTelefono.setLengthText(10);
        txtTelefono.setText("");
        createJTextField(txtTelefono, i, vTipoDato, vIndSoloLec);
    }

    private void getTxtCelular(int i, String vTipoDato, String vIndSoloLec) {
        txtCelular.setLengthText(10);
        txtCelular.setText("");
        createJTextField(txtCelular, i, vTipoDato, vIndSoloLec);
    }

    private void getTxtFechaNacimiento(int i, String vTipoDato, String vIndSoloLec) {
        txtFechNac.setLengthText(10); //dd/mm/yyyy
        txtFechNac.setText("");
        createJTextField(txtFechNac, i, vTipoDato, vIndSoloLec);
    }

    private void getTxtNombre(int i, String vTipoDato, String vIndSoloLec) {
        txtNomCliente.setLengthText(30);
        txtNomCliente.setText("");
        createJTextField(txtNomCliente, i, vTipoDato, vIndSoloLec);
    }

    private void getTxtEmail(int i, String vTipoDato, String vIndSoloLec) {
        txtEmail.setLengthText(70);
        txtEmail.setText("");
        createJTextField(txtEmail, i, vTipoDato, vIndSoloLec);
    }

    private void getTxtDireccion(int i, String vTipoDato, String vIndSoloLec) {
        txtDireccion.setLengthText(100);
        txtDireccion.setText("");
        createJTextField(txtDireccion, i, vTipoDato, vIndSoloLec);
    }

    public void getCmbSexo(int i) {
        createJComboBox(cmbSexo, i);
    }

    private void createJTextField(JTextField pJTextField, int pRow, String pTipoCampo, String pInSoloLectura) {

        addKeyListenerToTextField(pJTextField, pTipoCampo, pInSoloLectura);
        defaultCellEditor = new DefaultCellEditor(pJTextField);
        rowEditorModel.addEditorForRow(pRow, defaultCellEditor);
    }

    /* ********************************************************************** */
    /* SECCION : CREACION DE CAMPOS JCOMBOBOX                                 */
    /* ********************************************************************** */

    private void createJComboBox(JComboBox pJComboBox, int pRow) {

        createJComboBoxAux(cmbSexo, "CMB_SEXO", compValor, compDescripcion, pRow);

        //addKeyListenerJComboBox(pJComboBox);
        //defaultCellEditor = new DefaultCellEditor(pJComboBox);
        //rowEditorModel.addEditorForRow(pRow,defaultCellEditor);
    }

    private void createJComboBoxAux(JComboBox pJComboBox, String pNameComboBox, String[] pValue, String[] pDescription,
                                    int pRow) {
        FarmaLoadCVL.loadCVLfromArrays(pJComboBox, pNameComboBox, pValue, pDescription, false);
        addKeyListenerJComboBox(pJComboBox);
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
                    else if (pTipoCampo.equalsIgnoreCase("F")) {
                        //FarmaUtility.dateComplete(pJTextField, e);
                        char keyChar = e.getKeyChar();
                        if (Character.isDigit(keyChar)) {
                            if ((pJTextField.getText().trim().length() == 2) ||
                                (pJTextField.getText().trim().length() == 5)) {
                                pJTextField.setText(pJTextField.getText().trim() + "/");
                            }
                        }
                    }
                }
            }

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    int vFila = tblLista.getSelectedRow();

                    pCodigoCampoAux =
                            tableModel.getValueAt(vFila, 2).toString().trim(); //FarmaUtility.getValueFieldJTable(tblLista,vFila,2);

                    //VERIFICAR SI EL ENTER SE TECLEO EN EL CAMPO DNI
                    if (pCodigoCampoAux.trim().equalsIgnoreCase(ConstantsCampAcumulada.DNI_CLIENTE)) {
                        String pCadena = pJTextField.getText().trim();
                        //log.info("entrando a buscar datos de cliente por dni");
                        if (UtilityCampAcumulada.validarDocIndentificacion(pCadena,
                                                                           VariablesCampAcumulada.vDocValidos)) {
                            buscaDatosCliente(pCadena);
                        } else {
                            muestraMensaje("Doc de Identificacion no valido !");
                            return;
                        }
                        //log.info("moviendo el cursor al siguiente texto o comnbo"+vFila);

                        pJTextField.setText(pJTextField.getText().toUpperCase());

                        /*
                                for(int i = 0; i<tblLista.getRowCount();i++){
                                	tblLista.changeSelection(i, 1, false, false);
                                }*/


                        if ((vFila + 1) == tblLista.getRowCount()) {
                            //log.info("move focus : 0 ");
                            moveFocusTo(0);
                        } else {
                            //log.info("move focus : "+(vFila+1));
                            moveFocusTo(vFila + 1);
                        }

                    } else {
                        pJTextField.setText(pJTextField.getText().toUpperCase());
                        if ((vFila + 1) == tblLista.getRowCount())
                            moveFocusTo(0);
                        else {
                            moveFocusTo(vFila + 1);
                        }

                    }
                } else
                    tblLista_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {

                if (pTipoCampo.equalsIgnoreCase("F")) {
                    FarmaUtility.dateComplete(pJTextField, e);
                    char keyChar = e.getKeyChar();
                    if (Character.isDigit(keyChar)) {
                        if ((pJTextField.getText().trim().length() == 2) ||
                            (pJTextField.getText().trim().length() == 5)) {
                            pJTextField.setText(pJTextField.getText().trim() + "/");
                        }
                    }
                }

                if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e) && pJTextField.isDisplayable()) {
                    pJTextField.selectAll();
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    /*if ((vFila + 1) < tblLista.getRowCount())
                                moveFocusTo(vFila);*/

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
                        int Fila = tblLista.getSelectedRow();

                        if ((Fila + 1) == tblLista.getRowCount()) {

                            moveFocusTo(0);
                            /*FarmaUtility.setearRegistro(tblLista,
                                                                tblLista.getSelectedRow(),
                                                                null, 0);*/
                        } else {
                            moveFocusTo(Fila + 1);

                            /*FarmaUtility.setearRegistro(tblLista,
                                                                tblLista.getSelectedRow() +
                                                                1, null, 0);*/
                        }
                    }
                }
            }

            public void keyReleased(KeyEvent e) {
                if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e) && pJComboBox.isDisplayable()) {
                    pJComboBox.setPopupVisible(true);
                    /*} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            if (pJComboBox.isPopupVisible()) {
                                pJComboBox.setPopupVisible(false);
                            }*/
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    e.consume();
                    tblLista_keyPressed(e);
                }
            }
        });

    }

    private void moveFocusTo(int pRow) {
        /*FarmaUtility.setearRegistro(tblLista, pRow, null, 0);
        tblLista.changeSelection(pRow, 1, false, false);*/

        tblLista.changeSelection(pRow, 1, false, false);

        String cod_campo = tableModel.getValueAt(pRow, 2).toString().trim();
        log.debug("CAMPO " + cod_campo + " - " + ConstantsCampAcumulada.DNI_CLIENTE);
        if (cod_campo.equalsIgnoreCase(ConstantsCampAcumulada.DNI_CLIENTE)) {
            FarmaUtility.moveFocus(txtNumeroDocumento);
        } else if (cod_campo.equalsIgnoreCase(ConstantsCampAcumulada.NOMBRE_CLIENTE)) {
            FarmaUtility.moveFocus(txtNomCliente);
        } else if (cod_campo.equalsIgnoreCase(ConstantsCampAcumulada.APEPAT_CLIENTE)) {
            FarmaUtility.moveFocus(txtApellidoPaterno);
        } else if (cod_campo.equalsIgnoreCase(ConstantsCampAcumulada.APEMAT_CLIENTE)) {
            FarmaUtility.moveFocus(txtApellidoMaterno);
        } else if (cod_campo.equalsIgnoreCase(ConstantsCampAcumulada.SEXO_CLIENTE)) {
            FarmaUtility.moveFocus(txtSexo);
        } else if (cod_campo.equalsIgnoreCase(ConstantsCampAcumulada.FECHA_NAC_CLIENTE)) {
            FarmaUtility.moveFocus(txtFechNac);
        } else if (cod_campo.equalsIgnoreCase(ConstantsCampAcumulada.DIREC_CLIENTE)) {
            FarmaUtility.moveFocus(txtDireccion);
        } else if (cod_campo.equalsIgnoreCase(ConstantsCampAcumulada.TELEFONO_CLIENTE)) {
            FarmaUtility.moveFocus(txtTelefono);
        } else if (cod_campo.equalsIgnoreCase(ConstantsCampAcumulada.CELULAR_CLIENTE)) {
            FarmaUtility.moveFocus(txtCelular);
        } else if (cod_campo.equalsIgnoreCase(ConstantsCampAcumulada.EMAIL_CLIENTE)) {
            FarmaUtility.moveFocus(txtEmail);
        }
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        initTable();
        tblLista.changeSelection(0, 1, false, false);

        /*** si ya se tiene tarjeta de fidelizacion cargada tonces buscar datos del cliente ***/
        if (VariablesFidelizacion.vNumTarjeta.trim().length() > 0) { //quiere decir que ya tiene tarjeta agregado
            txtNumeroDocumento.setText(VariablesFidelizacion.vDniCliente);
            buscaDatosCliente(VariablesFidelizacion.vDniCliente);
        }

        //Se validara los datos si presenta todos se cerrar la ventana
        tblLista.changeSelection(0, 0, true, false);
        if (isContieneDatosCampaña()) { //validando todos los datos obligatorios
            cargaVariables();
            cerrarVentana(true);
        }

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

    /* ********************************************************************** */
    /*                     METODOS AUXILIARES DE EVENTOS                      */
    /* ********************************************************************** */

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (FarmaVariables.vEconoFar_Matriz) {
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, null);
                return;
            }
            funcionF11();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            mensajeESC = "¿Está seguro salir de la ventana?";
            if (JOptionPane.showConfirmDialog(this, mensajeESC, "Mensaje de Confirmacion - Mifarma",
                                              JOptionPane.YES_NO_OPTION) == 0) {

                VariablesCampAcumulada.vIndExisteCliente = false;
                cerrarVentana(false);

            }
            if (tblLista.getRowCount() > 0) {


                FarmaUtility.moveFocus(tblLista);

            }

        }
    }

    public void txtNumeroDocumento_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtNumeroDocumento, e);
    }

    public void txtNombre_keyTyped(KeyEvent e) {
        FarmaUtility.admitirLetras(txtNomCliente, e);
    }

    public void txtApellidoPaterno_keyTyped(KeyEvent e) {
        FarmaUtility.admitirLetras(txtApellidoPaterno, e);
    }

    public void txtApellidoMaterno_keyTyped(KeyEvent e) {
        FarmaUtility.admitirLetras(txtApellidoMaterno, e);
    }

    private void txtSexo_keyTyped(KeyEvent e) {
        FarmaUtility.admitirLetras(txtSexo, e);
    }

    public void txtFechaNacimiento_keyTyped(KeyEvent e) {
        //log.info("TIPEOOOOOOOOOO EN FECHA DE NACIEMIENTO");
        //FarmaUtility.dateComplete(txtFechNac, e);
        char keyChar = e.getKeyChar();
        if (Character.isDigit(keyChar)) {
            if ((txtFechNac.getText().trim().length() == 2) || (txtFechNac.getText().trim().length() == 5)) {
                txtFechNac.setText(txtFechNac.getText().trim() + "/");
            }
        }
    }

    public void txtDireccion_keyTyped(KeyEvent e) {
        FarmaUtility.admitirLetrasNumeros(txtDireccion, e);
    }

    public void txtTelefono_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtTelefono, e);
    }

    public void txtCelular_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCelular, e);
    }

    public void txtEmail_keyTyped(KeyEvent e) {
        //FarmaUtility.admitirLetras(txtEmail, e);
    }


    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        if (!pAceptar) {
            this.limpiar();
        }
        this.setVisible(false);
        this.dispose();
    }

    private void limpiar() {
        VariablesCampAcumulada.vDataCliente = new ArrayList();
        VariablesCampAcumulada.vDniCliente = "";
        VariablesCampAcumulada.vApePatCliente = "";
        VariablesCampAcumulada.vApeMatCliente = "";
        VariablesCampAcumulada.vNomCliente = "";
        VariablesCampAcumulada.vFecNacimiento = "";
        VariablesCampAcumulada.vSexo = ""; //cmbSexo.getSelectedItem().toString().trim();//txtSexo.getText().trim();
        VariablesCampAcumulada.vDireccion = "";
        VariablesCampAcumulada.vTelefono = "";


        VariablesCampAcumulada.vIndEstado = "";
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void funcionF11() {

        //se agrego esta linea por si el usuario cambio a ultima hora el DNI
        if (txtNumeroDocumento.isEditable()) {
            log.info("VALIDANDO QUE DATOS YA QUE PUDO HABER CAMBIADO EL DNI EL USUARIO");
            buscaDatosCliente(txtNumeroDocumento.getText().trim());
        }


        tblLista.changeSelection(0, 0, true, false);
        if (validateMandatory()) { //validando todos los datos obligatorios
            cargaVariables();
            try {
                DBCampAcumulada.insertarCliente();
                FarmaUtility.aceptarTransaccion();
                if (VariablesCampAcumulada.vIndConexion.equals(FarmaConstants.INDICADOR_S)) {
                    DBCampAcumulada.insertarClienteMatriz(); //DUDA QUE PASA SI HAY ERROR EN MATRIZ--CREO QUE DEBERIA SER EL PROCEDIMIENTO AUTONOMO
                    FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
                }
                cerrarVentana(true);
            } catch (SQLException e) {
                FarmaUtility.liberarTransaccion();
                if (VariablesCampAcumulada.vIndConexion.equals(FarmaConstants.INDICADOR_S)) {
                    FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
                }
                log.error("", e);
                FarmaUtility.showMessage(this, "ERROR:" + e, txtNumeroDocumento);
            }
        }

    }

    private void buscaMatrizActualizaDatosLocal(String pDni) {

        ArrayList array = new ArrayList();

        try {

            DBCampAcumulada.getDatosExisteDNI_Matriz(array, pDni, FarmaConstants.INDICADOR_N);
            log.debug("Datos Cli en Matriz " + array);
            log.debug("Tam size:" + array.size());

            /**verificando si en matriz el dni es $**/
            if (array.size() > 0) {

                VariablesCampAcumulada.vDniCliente = FarmaUtility.getValueFieldArrayList(array, 0, COL_DNI).trim();
                VariablesCampAcumulada.vNomCliente = FarmaUtility.getValueFieldArrayList(array, 0, COL_NOM_CLI).trim();
                VariablesCampAcumulada.vApePatCliente =
                        FarmaUtility.getValueFieldArrayList(array, 0, COL_APE_PAT).trim();
                VariablesCampAcumulada.vApeMatCliente =
                        FarmaUtility.getValueFieldArrayList(array, 0, COL_APE_MAT).trim();
                VariablesCampAcumulada.vSexo = FarmaUtility.getValueFieldArrayList(array, 0, COL_SEX_CLI).trim();
                VariablesCampAcumulada.vFecNacimiento =
                        FarmaUtility.getValueFieldArrayList(array, 0, COL_FEC_NAC).trim();
                VariablesCampAcumulada.vDireccion = FarmaUtility.getValueFieldArrayList(array, 0, COL_DIR_CLI).trim();
                VariablesCampAcumulada.vTelefono = FarmaUtility.getValueFieldArrayList(array, 0, COL_TLF_CLI).trim();
                VariablesCampAcumulada.vCelular = FarmaUtility.getValueFieldArrayList(array, 0, COL_CEL_CLI).trim();
                VariablesCampAcumulada.vCelular = FarmaUtility.getValueFieldArrayList(array, 0, COL_EMAIL).trim();

                VariablesCampAcumulada.vIndEstado = "A";
                //Este metodo de se encargara de insertar y/o actualizar
                //insertarClienteFidelizacion
                DBCampAcumulada.insertarCliente();

            }
            VariablesCampAcumulada.vDniCliente = "";
            VariablesCampAcumulada.vNomCliente = "";
            VariablesCampAcumulada.vApePatCliente = "";
            VariablesCampAcumulada.vApeMatCliente = "";
            VariablesCampAcumulada.vSexo = "";
            VariablesCampAcumulada.vFecNacimiento = "";
            VariablesCampAcumulada.vDireccion = "";
            VariablesCampAcumulada.vTelefono = "";
            VariablesCampAcumulada.vCelular = "";
            VariablesCampAcumulada.vEmail = "";

        } catch (SQLException e) {
            log.error("", e);
        }
    }

    private void muestraMensaje(String string) {
        FarmaUtility.showMessage(this, string, null);
    }

    private boolean buscaDatosCliente(String pDNI) {
        if (UtilityCampAcumulada.validarDocIndentificacion(pDNI.trim(), VariablesCampAcumulada.vDocValidos)) {
            log.info("Datos del cliente " + VariablesCampAcumulada.vDataCliente);

            ArrayList vListaDatosDNI = new ArrayList();
            ArrayList vListaCampos = new ArrayList();
            String vDNI = "", vApePat = "", vApeMat = "", vNom = "", vFecNac = "", vSexCli = "", vDirCli =
                "", vTlfCli = "", vCelCli = "", vEma = "";

            try {
                DBCampAcumulada.getDatosExisteDNI(vListaDatosDNI, pDNI);

                if (vListaDatosDNI.size() < 1) { //si no hay datos del cliente
                    log.info("verificando disponibilidad de matriz");
                    VariablesCampAcumulada.vIndConexion =
                            FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,
                                                           FarmaConstants.INDICADOR_N);
                    log.info("VariablesOtros.vIndConexion:" + VariablesCampAcumulada.vIndConexion);
                    if (VariablesCampAcumulada.vIndConexion.trim().equalsIgnoreCase("S")) { //busca en matriz los datos
                        log.debug("busca en matriz e inserta");
                        buscaMatrizActualizaDatosLocal(pDNI);
                        vListaDatosDNI = new ArrayList();
                        DBCampAcumulada.getDatosExisteDNI(vListaDatosDNI, pDNI);
                    }
                }

                if (vListaDatosDNI.size() > 0) { //si hay datos de cliente
                    DBCampAcumulada.getCamposCliente(vListaCampos);
                    //log.debug("vListaCampos " + vListaCampos);
                    //log.debug("vListaDatosDNI " + vListaDatosDNI);

                    vDNI = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_DNI);
                    vNom = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_NOM_CLI);
                    vApePat = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_APE_PAT);
                    vApeMat = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_APE_MAT);
                    vSexCli = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_SEX_CLI);
                    vFecNac = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_FEC_NAC);
                    vDirCli = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_DIR_CLI);
                    vTlfCli = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_TLF_CLI);
                    vCelCli = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_CEL_CLI);
                    vEma = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_EMAIL);

                    int pos = -1;

                    if (!vDNI.equalsIgnoreCase("N")) { //si tiene el Nro de DNI
                        pos = buscaPosFila(ConstantsCampAcumulada.DNI_CLIENTE);
                        if (pos >= 0) {
                            tblLista.setValueAt(vDNI, pos, 1);
                            txtNumeroDocumento.setText(vDNI);
                            txtNumeroDocumento.setEditable(false);
                        }
                    }

                    if (!vNom.equalsIgnoreCase("N")) {
                        pos = buscaPosFila(ConstantsCampAcumulada.NOMBRE_CLIENTE);
                        if (pos >= 0) {
                            tblLista.setValueAt(vNom, pos, 1);
                            txtNomCliente.setText(vNom);
                            txtNomCliente.setEditable(true); //falta mejorar esto
                        }
                    }

                    if (!vApePat.equalsIgnoreCase("N")) {
                        pos = buscaPosFila(ConstantsCampAcumulada.APEPAT_CLIENTE);
                        if (pos >= 0) {
                            tblLista.setValueAt(vApePat, pos, 1);
                            txtApellidoPaterno.setText(vApePat);
                            txtApellidoPaterno.setEditable(true); //falta mejorar esto
                        }
                    }

                    if (!vApeMat.equalsIgnoreCase("N")) {
                        pos = buscaPosFila(ConstantsCampAcumulada.APEMAT_CLIENTE);
                        if (pos >= 0) {
                            tblLista.setValueAt(vApeMat, pos, 1);
                            txtApellidoMaterno.setText(vApeMat);
                            txtApellidoMaterno.setEditable(true); //falta mejorar esto
                        }
                    }

                    if (!vSexCli.equalsIgnoreCase("N")) {
                        pos = buscaPosFila(ConstantsCampAcumulada.SEXO_CLIENTE);
                        if (pos >= 0) {
                            FarmaLoadCVL.setSelectedValueInComboBox(cmbSexo, "CMB_SEXO", vSexCli);
                            tblLista.setValueAt(FarmaLoadCVL.getCVLDescription("CMB_SEXO", vSexCli), pos, 1);
                            cmbSexo.setEditable(false);
                            cmbSexo.setEnabled(false);
                            txtSexo.setEditable(false);
                        }
                    }

                    if (!vFecNac.equalsIgnoreCase("N")) {
                        pos = buscaPosFila(ConstantsCampAcumulada.FECHA_NAC_CLIENTE);
                        if (pos >= 0) {
                            tblLista.setValueAt(vFecNac, pos, 1);
                            txtFechNac.setText(vFecNac);
                            txtFechNac.setEditable(false);
                        }
                    }

                    if (!vDirCli.equalsIgnoreCase("N")) {
                        pos = buscaPosFila(ConstantsCampAcumulada.DIREC_CLIENTE);
                        if (pos >= 0) {
                            tblLista.setValueAt(vDirCli, pos, 1);
                            txtDireccion.setText(vDirCli);
                            txtDireccion.setEditable(false);
                        }
                    }

                    if (!vTlfCli.equalsIgnoreCase("N")) {
                        pos = buscaPosFila(ConstantsCampAcumulada.TELEFONO_CLIENTE);
                        if (pos >= 0) {
                            tblLista.setValueAt(vTlfCli, pos, 1);
                            txtTelefono.setText(vTlfCli);
                            txtTelefono.setEditable(false);
                        }
                    }

                    if (!vCelCli.equalsIgnoreCase("N")) {
                        pos = buscaPosFila(ConstantsCampAcumulada.CELULAR_CLIENTE);
                        if (pos >= 0) {
                            tblLista.setValueAt(vCelCli, pos, 1);
                            txtCelular.setText(vCelCli);
                            txtCelular.setEditable(false);
                        }
                    }

                    if (!vEma.equalsIgnoreCase("N")) {
                        pos = buscaPosFila(ConstantsCampAcumulada.EMAIL_CLIENTE);
                        if (pos >= 0) {
                            tblLista.setValueAt(vEma, pos, 1);
                            txtEmail.setText(vEma);
                            txtEmail.setEditable(false);
                        }
                    }

                    tblLista.repaint();
                    VariablesCampAcumulada.vIndExisteCliente = true;
                    return true;
                } else
                    return false;


            } catch (SQLException e) {
                log.error("", e);
            }

            return false;
        } else
            return false;
    }

    /**
     * metodo encargado de validar los campos que son obligatorios especificar para registrar al cliente
     * @author jcallo
     */
    private boolean validateMandatory() {
        boolean dataExists = true;
        String vIndObligatorio;
        String vCodCampo;
        String vDato;

        for (int i = 0; i < tblLista.getRowCount(); i++) {
            vIndObligatorio = tableModel.getValueAt(i, COL_IND_OBLI).toString().trim();
            if (vIndObligatorio.equalsIgnoreCase("S")) {
                vCodCampo = tableModel.getValueAt(i, COL_COD).toString().trim();
                vDato = tableModel.getValueAt(i, COL_DATO).toString().trim();
                if (vDato.length() == 0) {
                    dataExists = false;
                    moveFocusTo(i);
                    FarmaUtility.showMessage(this,
                                             "Campo " + findFieldDescription(vCodCampo) + " no tiene información.  Verifique !!!",
                                             null);
                    break;
                }

                if (vCodCampo.equals(ConstantsCampAcumulada.FECHA_NAC_CLIENTE)) {
                    if (!FarmaUtility.validaFecha(vDato, "dd/MM/yyyy")) {
                        dataExists = false;
                        moveFocusTo(i);
                        FarmaUtility.showMessage(this,
                                                 "Campo " + findFieldDescription(vCodCampo) + " contiene un fecha invalida.  Verifique !!!",
                                                 null);
                        break;
                    }
                }

                if (vCodCampo.equals(ConstantsCampAcumulada.DNI_CLIENTE)) {
                    if (!UtilityCampAcumulada.validarDocIndentificacion(vDato.trim(),
                                                                        VariablesCampAcumulada.vDocValidos)) {
                        dataExists = false;
                        moveFocusTo(i);
                        FarmaUtility.showMessage(this,
                                                 "Campo " + findFieldDescription(vCodCampo) + " es invalido.  Verifique !!!",
                                                 null);
                        break;
                    }
                }

                if (vCodCampo.equals(ConstantsCampAcumulada.EMAIL_CLIENTE)) {

                    if (!UtilityCampAcumulada.validarEmail(vDato.trim())) {
                        dataExists = false;
                        moveFocusTo(i);
                        FarmaUtility.showMessage(this,
                                                 "Campo " + findFieldDescription(vCodCampo) + " invalido.Verifique !!!",
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

    /**
     * metodo encargado de vargar los datos en las VariablesCampAcumulada para poder registrar los datos correctamente
     * @author jcallo
     * *
     */
    private void cargaVariables() {

        //log.info("dni"+txtNumeroDocumento.getText().trim());
        //log.info("apepat"+txtApellidoPaterno.getText().trim());

        VariablesCampAcumulada.vDniCliente = txtNumeroDocumento.getText().trim();
        VariablesCampAcumulada.vNomCliente = txtNomCliente.getText().trim();
        VariablesCampAcumulada.vApePatCliente = txtApellidoPaterno.getText().trim();
        VariablesCampAcumulada.vApeMatCliente = txtApellidoMaterno.getText().trim();
        VariablesCampAcumulada.vSexo = getSexoCliente();
        VariablesCampAcumulada.vFecNacimiento = txtFechNac.getText().trim();
        VariablesCampAcumulada.vDireccion = txtDireccion.getText().trim();
        VariablesCampAcumulada.vTelefono = txtTelefono.getText().trim();
        VariablesCampAcumulada.vCelular = txtCelular.getText().trim();
        VariablesCampAcumulada.vEmail = txtEmail.getText().trim();

        VariablesCampAcumulada.vIndEstado = "A";

        if (VariablesCampAcumulada.vNomCliente.trim().length() == 0)
            VariablesCampAcumulada.vNomCliente = "N";
        if (VariablesCampAcumulada.vApePatCliente.trim().length() == 0)
            VariablesCampAcumulada.vApePatCliente = "N";
        if (VariablesCampAcumulada.vApeMatCliente.trim().length() == 0)
            VariablesCampAcumulada.vApeMatCliente = "N";
        if (VariablesCampAcumulada.vSexo.trim().length() == 0)
            VariablesCampAcumulada.vSexo = "N";
        if (VariablesCampAcumulada.vFecNacimiento.trim().length() == 0)
            VariablesCampAcumulada.vFecNacimiento = "N";
        if (VariablesCampAcumulada.vDireccion.trim().length() == 0)
            VariablesCampAcumulada.vDireccion = "N";
        if (VariablesCampAcumulada.vTelefono.trim().length() == 0)
            VariablesCampAcumulada.vTelefono = "N";
        if (VariablesCampAcumulada.vCelular.trim().length() == 0)
            VariablesCampAcumulada.vCelular = "N";
        if (VariablesCampAcumulada.vEmail.trim().length() == 0)
            VariablesCampAcumulada.vEmail = "N";
    }

    private String getSexoCliente() {
        String tipoSexo;
        try {
            //log.info("indice del combo : "+cmbSexo.getSelectedIndex());
            tipoSexo = FarmaLoadCVL.getCVLCode("CMB_SEXO", cmbSexo.getSelectedIndex());
        } catch (ArrayIndexOutOfBoundsException e) {
            log.info("error :" + e);
            tipoSexo = "";
        }

        //log.info("sexo del cliente : "+tipoSexo);

        return tipoSexo;
    }


    /**
     * Verifica si presenta todos los campos para que cierre la ventana.
     */
    private boolean isContieneDatosCampaña() {
        boolean dataExists = true;
        String vIndObligatorio;
        String vCodCampo;
        String vDato;

        for (int i = 0; i < tblLista.getRowCount(); i++) {
            vIndObligatorio = tableModel.getValueAt(i, COL_IND_OBLI).toString().trim();
            if (vIndObligatorio.equalsIgnoreCase("S")) {
                vCodCampo = tableModel.getValueAt(i, COL_COD).toString().trim();
                vDato = tableModel.getValueAt(i, COL_DATO).toString().trim();
                if (vDato.length() == 0) {
                    dataExists = false;
                    moveFocusTo(i);
                    break;
                }

                if (vCodCampo.equals(ConstantsCampAcumulada.FECHA_NAC_CLIENTE)) {
                    if (!FarmaUtility.validaFecha(vDato, "dd/MM/yyyy")) {
                        dataExists = false;
                        moveFocusTo(i);

                        break;
                    }
                }

                if (vCodCampo.equals(ConstantsCampAcumulada.DNI_CLIENTE)) {
                    if (!UtilityCampAcumulada.validarDocIndentificacion(vDato.trim(),
                                                                        VariablesCampAcumulada.vDocValidos)) {
                        dataExists = false;
                        moveFocusTo(i);

                        break;
                    }
                }

                if (vCodCampo.equals(ConstantsCampAcumulada.EMAIL_CLIENTE)) {

                    if (!UtilityCampAcumulada.validarEmail(vDato.trim())) {
                        dataExists = false;
                        moveFocusTo(i);

                        break;
                    }
                }

            }
        }
        return dataExists;
    }


}
