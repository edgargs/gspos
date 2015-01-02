package mifarma.ptoventa.fidelizacion;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Label;
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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import mifarma.common.FarmaJTable;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaRowEditorModel;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.campana.reference.VariablesCampana;
import mifarma.ptoventa.ce.reference.ConstantsCajaElectronica;
import mifarma.ptoventa.convenio.DlgDatosConvenio;
import mifarma.ptoventa.fidelizacion.reference.ConstantsFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.DBFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.UtilityFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.reference.ConstantsPtoVenta;


import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgFidelizacionClientes.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * DVELIZ      26.09.2008   Creación<br>
 * <br>_F
 * @author Daniel Fernando Veliz La Rosa<br>
 * @version 1.0<br>
 * 
 */

public class DlgFidelizacionClientes extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgFidelizacionClientes.class);

    boolean pEntro = false;    

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    boolean vPresionaTeclaEnter = true;
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
    private JTextFieldSanSerif txtEmail = new JTextFieldSanSerif();

    private JTextFieldSanSerif txtTelefono = new JTextFieldSanSerif();

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
    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction lbl = new JLabelFunction();

    private String mensajeESC = "";

    private String pCodTarjetaIngresado = "";
    
    private String pCodigoCampoAux = "";
    /*****/
    private final


    int COL_DNI = 0;
    private final int COL_APE_PAT = 1;
    private final int COL_APE_MAT = 2;
    private final int COL_NOM_CLI = 3;
    private final int COL_FEC_NAC = 4;
    private final int COL_SEX_CLI = 5;
    private final int COL_DIR_CLI = 6;
    private final int COL_TLF_CLI = 7;
    private final int COL_EMAIL = 8;
    private JLabelFunction lblf1 = new JLabelFunction();
    
    //JCORTEZ 03.07.09
    private boolean valor=false;


    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgFidelizacionClientes() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error("",e);
        }
    }

    public DlgFidelizacionClientes(Frame parent, String title, boolean modal, 
                                   String pCodTarjeta) {
        super(parent, title, modal);
        pCodTarjetaIngresado = pCodTarjeta.trim();

        //Agregado por DVELIZ 30.09.08
        VariablesFidelizacion.vNumTarjeta = pCodTarjeta.trim();
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("",e);
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(borderLayout1);
        this.setSize(new Dimension(583, 306));
        this.setDefaultCloseOperation(0);
        this.setTitle("Datos de Clientes : Fidelizacion");
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
        lblf1.setBounds(new Rectangle(10, 240, 120, 20));
        lblf1.setVisible(false);
        
        lbl.setBounds(new Rectangle(10, 245, 230, 25));
        lbl.setText("Presionar [F2] para escoger el género");
            
        scrLista.getViewport().add(tblLista, null);
        pnlTitle1.add(btnLista, null);
        //jContentPane.add(lblF1, null);
        //jContentPane.add(lblf1, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(scrLista, null);
        jContentPane.add(lbl, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);

        txtNomCliente.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        txtNombre_keyTyped(e);
                    }
                });

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

        txtDireccion.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        txtDireccion_keyTyped(e);
                    }
                });

        txtFechNac.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        txtFechaNacimiento_keyTyped(e);
                    }
                    public void keyReleased(KeyEvent e) {
                        txtFechaNacimiento_keyReleased(e);
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
        //this.setTitle("Datos de Cliente : Fidelización");
        /*VariablesCampana.vNumIngreso = 0;
      VariablesCampana.vFlagMandatory = false;
      VariablesCampana.vSexoExists = false;*/
        try{        
            VariablesFidelizacion.vDocValidos = DBFidelizacion.obtenerParamDocIden();
        }catch(SQLException e){
            log.debug("error : "+e);
        }
      log.debug("longitud de docs validos : "+VariablesFidelizacion.vDocValidos);
    }

    /* ********************************************************************** */
    /*                            METODOS INICIALIZACION                      */
    /* ********************************************************************** */

    private void initTable() {

        tableModel = 
                new FarmaTableModel(ConstantsFidelizacion.columnsListaDatosFidelizacion, 
                                    ConstantsFidelizacion.defaultValuesListaDatosFidelizacion, 
                                    0, 
                                    ConstantsFidelizacion.editableListaDatosFidelizacion, 
                                    null);
        rowEditorModel = new FarmaRowEditorModel();
        tblLista.setAutoCreateColumnsFromModel(false);
        tblLista.setModel(tableModel);
        tblLista.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        for (int k = 0; k < tableModel.getColumnCount(); k++) {
            TableColumn column = 
                new TableColumn(k, ConstantsFidelizacion.columnsListaDatosFidelizacion[k].m_width);
            tblLista.addColumn(column);
        }

        tblLista.setRowEditorModel(rowEditorModel);
        cargaCampos();
        // Verifica si no tiene campos para ingresar acepta el convenio
        if (tblLista.getRowCount() == 0) {
            if (FarmaVariables.vEconoFar_Matriz) {
                FarmaUtility.showMessage(this, 
                                         ConstantsPtoVenta.MENSAJE_MATRIZ, 
                                         null);
                return;
            }
            //funcionF11();
        }
        
        //JCORTEZ  24.07.09 Se setea el DNI
        String codCampo="";
        int pos=0;
        if(!VariablesFidelizacion.NumDniAux.equalsIgnoreCase("")){
            if(tblLista.getRowCount()>0){
                  for (int i = 0; i < tblLista.getRowCount(); i++) {
                      codCampo = FarmaUtility.getValueFieldJTable(tblLista, i, COL_COD);
                            if (codCampo.equals(ConstantsFidelizacion.DNI_CLIENTE)) {
                                pos = buscaPosFila(codCampo);
                                if (pos >= 0) {
                                        log.info("Set DNI_CLIENTE-->"+VariablesFidelizacion.NumDniAux);
                                        tblLista.setValueAt(VariablesFidelizacion.NumDniAux, pos, 1);
                                        moveFocusTo(i);
                                }
                            }
                  }
            
            }
        }
        VariablesFidelizacion.NumDniAux="";
    }

    private void cargaCampos() {
        cargaLista();
        setTipoCampo();
        tblLista.repaint();
        cargaDatosCliente();
        //  tblLista.setValueAt("11s",1,1);//VariablesConvenio.vNomCliente,posRowNomCli,COL_DATO);
        //tblLista.show();
    }

    private int buscaPosFila(String pCodigoCampo) {
        String codAux = "";
        for (int i = 0; i < tblLista.getRowCount(); i++) {
            codAux = FarmaUtility.getValueFieldJTable(tblLista, i, 2);
            if (pCodigoCampo.trim().equalsIgnoreCase(codAux.trim()))
                return i;
        }
        return -1;
    }

    private void cargaLista() {
        try {
            DBFidelizacion.listarCamposFidelizacion(tableModel);
            if (tableModel.data.size() == 0) {
                cerrarVentana(true);
            }
        } catch (SQLException e) {
            log.error("",e);
            FarmaUtility.showMessage(this, "Error al cargar los campos.", 
                                     tblLista);
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
            vTipoDato = 
                    FarmaUtility.getValueFieldJTable(tblLista, i, COL_TIPO_DATO);
            vIndSoloLec = 
                    FarmaUtility.getValueFieldJTable(tblLista, i, COL_SOLO_LECTURA);

            //dveliz 26.08.08
            vIndOblig = 
                    FarmaUtility.getValueFieldJTable(tblLista, i, COL_IND_OBLI);
            if (vIndOblig.equals("S")) {
                VariablesCampana.vFlagMandatory = true;
            }
            //fin dveliz

            //dveliz 27.08.08
            if (i == 0) {
                if (codigoCampo.equals(ConstantsFidelizacion.DNI_CLIENTE)) {
                    getTxtNumeroDocumento(i, vTipoDato, vIndSoloLec);
                    FarmaUtility.moveFocus(txtNumeroDocumento);
                } else if (codigoCampo.equals(ConstantsFidelizacion.APEPAT_CLIENTE)) {
                    getTxtApellidoPaterno(i, vTipoDato, vIndSoloLec);
                    FarmaUtility.moveFocus(txtApellidoPaterno);
                } 
                else if (codigoCampo.equals(ConstantsFidelizacion.APEMAT_CLIENTE)) {
                                    getTxtApellidoPaterno(i, vTipoDato, vIndSoloLec);
                                    FarmaUtility.moveFocus(txtApellidoMaterno);
                                } 
                else if (codigoCampo.equals(ConstantsFidelizacion.NOMBRE_CLIENTE)) {
                    getTxtNombre(i, vTipoDato, vIndSoloLec);
                    FarmaUtility.moveFocus(txtNomCliente);
                } 
                else if (codigoCampo.equals(ConstantsFidelizacion.EMAIL_CLIENTE)) {
                    getTxtEmail(i, vTipoDato, vIndSoloLec);
                    FarmaUtility.moveFocus(txtApellidoMaterno);
                } else if (codigoCampo.equals(ConstantsFidelizacion.TELEFONO_CLIENTE)) {
                    getTxtTelefono(i, vTipoDato, vIndSoloLec);
                    FarmaUtility.moveFocus(txtTelefono);
                } else if (codigoCampo.equals(ConstantsFidelizacion.FECHA_NAC_CLIENTE)) {
                    getTxtFechaNacimiento(i, vTipoDato, vIndSoloLec);
                    FarmaUtility.moveFocus(txtFechNac);
                } else if (codigoCampo.equals(ConstantsFidelizacion.DIREC_CLIENTE)) {
                    getTxtDireccion(i, vTipoDato, vIndSoloLec);
                    FarmaUtility.moveFocus(txtDireccion);
                } else if (codigoCampo.equals(ConstantsFidelizacion.SEXO_CLIENTE)) {
                    VariablesCampana.vSexoExists = true;
                    getCmbSexo(i);
                    FarmaUtility.moveFocus(cmbSexo);
                }
            } else {
                if (codigoCampo.equals(ConstantsFidelizacion.DNI_CLIENTE)) {
                    getTxtNumeroDocumento(i, vTipoDato, vIndSoloLec);
                } else if (codigoCampo.equals(ConstantsFidelizacion.APEPAT_CLIENTE)) {
                    getTxtApellidoPaterno(i, vTipoDato, vIndSoloLec);
                } else if (codigoCampo.equals(ConstantsFidelizacion.APEMAT_CLIENTE)) {
                    getTxtApellidoMaterno(i, vTipoDato, vIndSoloLec);
                } else if (codigoCampo.equals(ConstantsFidelizacion.NOMBRE_CLIENTE)) {
                    getTxtNombre(i, vTipoDato, vIndSoloLec);
                } else if (codigoCampo.equals(ConstantsFidelizacion.TELEFONO_CLIENTE)) {
                    getTxtTelefono(i, vTipoDato, vIndSoloLec);
                } else if (codigoCampo.equals(ConstantsFidelizacion.FECHA_NAC_CLIENTE)) {
                    getTxtFechaNacimiento(i, vTipoDato, vIndSoloLec);
                } else if (codigoCampo.equals(ConstantsFidelizacion.DIREC_CLIENTE)) {
                    getTxtDireccion(i, vTipoDato, vIndSoloLec);
                } else if (codigoCampo.equals(ConstantsFidelizacion.SEXO_CLIENTE)) {
                    VariablesCampana.vSexoExists = true;
                    getCmbSexo(i);
                }
                else if (codigoCampo.equals(ConstantsFidelizacion.EMAIL_CLIENTE)) {
                                    getTxtEmail(i, vTipoDato, vIndSoloLec);
                } 
            }
        }
    }

    private void getTxtNumeroDocumento(int i, String vTipoDato, 
                                       String vIndSoloLec) {
        //txtNumeroDocumento.setLengthText(8);
        createJTextField(txtNumeroDocumento, i, vTipoDato, vIndSoloLec);
    }

    private void getTxtApellidoPaterno(int i, String vTipoDato, 
                                       String vIndSoloLec) {
        txtApellidoPaterno.setLengthText(30);
        createJTextField(txtApellidoPaterno, i, vTipoDato, vIndSoloLec);
    }

    private void getTxtApellidoMaterno(int i, String vTipoDato, 
                                       String vIndSoloLec) {
        txtApellidoMaterno.setLengthText(30);
        createJTextField(txtApellidoMaterno, i, vTipoDato, vIndSoloLec);
    }

    private void getTxtTelefono(int i, String vTipoDato, String vIndSoloLec) {
        txtTelefono.setLengthText(10);
        createJTextField(txtTelefono, i, vTipoDato, vIndSoloLec);
    }

    private void getTxtFechaNacimiento(int i, String vTipoDato, 
                                       String vIndSoloLec) {
        //txtFechNac.setLengthText(11);
        txtFechNac.setLengthText(10);
        createJTextField(txtFechNac, i, vTipoDato, vIndSoloLec);
    }

    private void getTxtNombre(int i, String vTipoDato, String vIndSoloLec) {
        txtNomCliente.setLengthText(30);
        createJTextField(txtNomCliente, i, vTipoDato, vIndSoloLec);
    }

    private void getTxtEmail(int i, String vTipoDato, String vIndSoloLec) {
        txtEmail.setLengthText(70);
        createJTextField(txtEmail, i, vTipoDato, vIndSoloLec);
    }

    private void getTxtDireccion(int i, String vTipoDato, String vIndSoloLec) {
        txtDireccion.setLengthText(100);
        createJTextField(txtDireccion, i, vTipoDato, vIndSoloLec);
    }

    public void getCmbSexo(int i) {
        /* cmbSexo.addItem("");
        cmbSexo.addItem("MASCULINO");
        cmbSexo.addItem("FEMENINO");*/
        createJComboBox(cmbSexo, i);
    }

    private void createJTextField(JTextField pJTextField, int pRow, 
                                  String pTipoCampo, String pInSoloLectura) {

        addKeyListenerToTextField(pJTextField, pTipoCampo, pInSoloLectura);
        defaultCellEditor = new DefaultCellEditor(pJTextField);
        rowEditorModel.addEditorForRow(pRow, defaultCellEditor);
    }

    /* ********************************************************************** */
    /* SECCION : CREACION DE CAMPOS JCOMBOBOX                                 */
    /* ********************************************************************** */

    private void createJComboBox(JComboBox pJComboBox, int pRow) {

        createJComboBoxAux(cmbSexo, "CMB_SEXO", compValor, compDescripcion, 
                           pRow);

        //addKeyListenerJComboBox(pJComboBox);
        //defaultCellEditor = new DefaultCellEditor(pJComboBox);
        //rowEditorModel.addEditorForRow(pRow,defaultCellEditor);
    }

    private String getSexoCliente() {
        String tipoSexo;
        try {
            tipoSexo = 
                    FarmaLoadCVL.getCVLCode("CMB_SEXO", cmbSexo.getSelectedIndex());
        } catch (ArrayIndexOutOfBoundsException e) {
            tipoSexo = "";
        }

        return tipoSexo;
    }

    private void createJComboBoxAux(JComboBox pJComboBox, String pNameComboBox, 
                                    String[] pValue, String[] pDescription, 
                                    int pRow) {
        FarmaLoadCVL.loadCVLfromArrays(pJComboBox, pNameComboBox, pValue, 
                                       pDescription, false);
        addKeyListenerJComboBox(pJComboBox);
        defaultCellEditor = new DefaultCellEditor(pJComboBox);
        rowEditorModel.addEditorForRow(pRow, defaultCellEditor);
    }

    private void checkComboBoxComponent() {
        String codigoCampo = "";
        codigoCampo = 
                tblLista.getValueAt(tblLista.getSelectedRow(), 2).toString().trim();
        tblLista.getEditorComponent().requestFocus();
        if (codigoCampo.equals(ConstantsFidelizacion.SEXO_CLIENTE))
            cmbSexo.setPopupVisible(true);

    }
    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void addKeyListenerToTextField(final JTextField pJTextField, 
                                           final String pTipoCampo, 
                                           final String pInSoloLectura) {
        pJTextField.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        if (pInSoloLectura.equalsIgnoreCase("S")) {
                            e.consume();
                        } else {
                            if (pTipoCampo.equalsIgnoreCase("E"))
                                FarmaUtility.admitirDigitos(pJTextField, e);
                            else if (pTipoCampo.equalsIgnoreCase("D"))
                                FarmaUtility.admitirDigitosDecimales(pJTextField, 
                                                                     e);
                            else if (pTipoCampo.equalsIgnoreCase("F")){
                                // FarmaUtility.dateComplete(pJTextField, e);
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
                           
                                    log.info("1. Inicio Evento ENTER");
                                    long tmpIni, tmpFin;

                                    tmpIni = System.currentTimeMillis();
                                    log.info("tmpIni ENTER:" + tmpIni);


                                    e.consume();
                                    int vFila = tblLista.getSelectedRow();

                                    pCodigoCampoAux = 
                                            FarmaUtility.getValueFieldJTable(tblLista, 
                                                                             vFila, 
                                                                             2);

                                    if (pCodigoCampoAux.trim().equalsIgnoreCase(ConstantsFidelizacion.DNI_CLIENTE)) {
                                        long tmpIni_dni, tmpFin_dni;

                                        tmpIni_dni = 
                                                System.currentTimeMillis();
                                        log.info("tmpIni_dni ENTER:" + 
                                                 tmpIni_dni);

                                        ArrayList vDatos = new ArrayList();
                                        String pCadena = 
                                            pJTextField.getText().trim();
                                        log.info("Busqueda DNI si existe : " + 
                                                 pCadena);

                                        try {
                                            //JCORTEZ 02.07.09 obtiene tarjeta existente en local
                                            DBFidelizacion.buscarTarjetasDni(VariablesFidelizacion.auxDataCli, 
                                                                             pCadena);
                                            log.info("JCORTEZ TARJETA... " + 
                                                     VariablesFidelizacion.auxDataCli);
                                        } catch (Exception a) {
                                            log.error("",a);
                                        }

                                        //JCORTEZ 04.08.09 Se obtiene dni del cliente para cargar cupones emitidos
                                        VariablesVentas.dniListCupon = 
                                                pCadena.trim();

                                        if (!buscaDatosCliente(pCadena)) {
                                            log.info("2.Dentro de if !buscaDatosCliente(pCadena)");
                                            long tmpIni_2, tmpFin_2;

                                            tmpIni_2 = 
                                                    System.currentTimeMillis();
                                            log.info("tmpIni_2 ENTER:" + 
                                                     tmpIni_2);

                                            if ((vFila + 1) == 
                                                tblLista.getRowCount())
                                                //dveliz 27.08.08
                                                moveFocusTo(0);
                                            // fin dveliz
                                            else {
                                                long tmpIni_3, tmpFin_3;

                                                tmpIni_3 = 
                                                        System.currentTimeMillis();
                                                log.info("tmpIni_3 ENTER:" + 
                                                         tmpIni_3);

                                                FarmaUtility.setearRegistro(tblLista, 
                                                                            vFila + 
                                                                            1, 
                                                                            null, 
                                                                            0);
                                                tblLista.changeSelection(vFila + 
                                                                         1, 1, 
                                                                         false, 
                                                                         false);
                                                //dubilluz - 05.04.2010
                                                if(FarmaUtility.getValueFieldArrayList(tableModel.data,vFila + 
                                                                         1,0).trim().toUpperCase().indexOf("SEXO")!=-1)
                                                {
                                                    ((JComboBox)cmbSexo).requestFocus();
                                                    tblLista.changeSelection(vFila + 
                                                                         1, 1, false, false);
                                                    log.info("colquo foco aa");
                                                    //cmbSexo.show();
                                                    tblLista.repaint();
                                                    cmbSexo.setPopupVisible(true);                                                   
                                                }
                                                
                                                tmpFin_3 = 
                                                        System.currentTimeMillis();
                                                log.info("tmpFin_3 ENTER:" + 
                                                         tmpFin_3);
                                                log.info("Diferencia :" + 
                                                         (tmpFin_3 - 
                                                          tmpIni_3) + 
                                                         " milisegundos");
                                            }

                                            pJTextField.setText(pJTextField.getText().toUpperCase());


                                            tmpFin_2 = 
                                                    System.currentTimeMillis();
                                            log.info("tmpFin_2 ENTER:" + 
                                                     tmpFin_2);
                                            log.info("Diferencia dentro de !buscaDatosCliente(pCadena):" + 
                                                     (tmpFin_2 - tmpIni_2) + 
                                                     " milisegundos");

                                        } else {


                                             if ((vFila + 1) == tblLista.getRowCount())
                                                //dveliz 27.08.08
                                                moveFocusTo(0);
                                            // fin dveliz
                                            else{

                                                FarmaUtility.setearRegistro(tblLista,
                                                                            vFila + 1, null,
                                                                            0);
                                                    tblLista.changeSelection(vFila + 1, 1, false, false);
                                                }
                                            
                                            ////Se validara si tiene todos los datos completos para
                                            ///que simule el F11
                                            funcionGeneralF11();
                                            
                                            // Error de ENTER DOBLE
                                            /*
                                            for (int i = 0; 
                                                 i < tblLista.getRowCount(); 
                                                 i++) {
                                                if (i != vFila) {
                                                    FarmaUtility.setearRegistro(tblLista, 
                                                                                i, 
                                                                                null, 
                                                                                0);
                                                    tblLista.changeSelection(i, 
                                                                             1, 
                                                                             false, 
                                                                             false);
                                                    
                                                }
                                            }*/

                                        }


                                        /*try {
                                            DBFidelizacion.getDatosDNI(vDatos,
                                                                       pCadena);
                                            log.debug("Datos :"+vDatos);

                                        } catch (SQLException e) {

                                        }
                                        */
                                        tmpFin_dni = 
                                                System.currentTimeMillis();
                                        log.info("tmpFin_dni ENTER:" + 
                                                 tmpFin_dni);

                                        log.info("fin ValidacionDNICLIENTE: " + 
                                                 (tmpFin_dni - tmpIni_dni) + 
                                                 " milisegundos");
                                        if (txtNomCliente.isEditable())
                                            moveFocusTo(1);
                                    } else {
                                        if ((vFila + 1) == 
                                            tblLista.getRowCount())
                                            //dveliz 27.08.08
                                            moveFocusTo(0);
                                        // fin dveliz
                                        else {

                                            FarmaUtility.setearRegistro(tblLista, 
                                                                        vFila + 
                                                                        1, 
                                                                        null, 
                                                                        0);
                                            tblLista.changeSelection(vFila + 1, 
                                                                     1, false, 
                                                                     false);
                                            // dubilluz - 05.04.2010
                                            if(FarmaUtility.getValueFieldArrayList(tableModel.data,vFila+1,0).trim().toUpperCase().indexOf("SEXO")!=-1)
                                            {
                                                ((JComboBox)cmbSexo).requestFocus();
                                                //tblLista.changeSelection(vFila+1, 1, false, false);
                                                log.info("colquo foco");
                                                //cmbSexo.show();
                                                //tblLista.repaint();
                                                log.info("colquo foco BB");
                                                cmbSexo.setPopupVisible(true);                                                   
                                            }
                                            
                                        }

                                        pJTextField.setText(pJTextField.getText().toUpperCase());
                                    }
                                    
                                    tmpFin = System.currentTimeMillis();
                                    log.info("tmpFin ENTER:"+tmpFin);                            
                                    log.info("Dif Tiempo ENTER:"+(tmpFin-tmpIni)+" milisegundos");  
                                    
                                    
                            
                        } else
                            tblLista_keyPressed(e);
                    }

                    public void keyReleased(KeyEvent e) {
                        int vFila = tblLista.getSelectedRow();

                        

                        if (pTipoCampo.equalsIgnoreCase("F")) {
                            //FarmaUtility.dateComplete(pJTextField, e);
                            char keyChar = e.getKeyChar();
                            if (Character.isDigit(keyChar)) {
                                if ((pJTextField.getText().trim().length() == 2) || 
                                    (pJTextField.getText().trim().length() == 5)) {
                                    pJTextField.setText(pJTextField.getText().trim() + "/");
                                }
                            }                            
                        }

                        /*if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e) && 
                            pJTextField.isDisplayable()) {
                            pJTextField.selectAll();
                        } else 
                        */
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            if ((vFila + 1) < tblLista.getRowCount()){
                                moveFocusTo(vFila);
                            }

                        } else {
                            if (e.getKeyCode() != KeyEvent.VK_LEFT && 
                                e.getKeyCode() != KeyEvent.VK_RIGHT && 
                                e.getKeyCode() != KeyEvent.VK_DELETE && 
                                e.getKeyCode() != KeyEvent.VK_BACK_SPACE && 
                                e.getKeyCode() != KeyEvent.VK_HOME && 
                                (!Character.isLetter(e.getKeyChar()) && 
                                 !Character.isWhitespace(e.getKeyChar()) && 
                                 !Character.isDigit(e.getKeyChar())))
                                pJTextField.setText(pJTextField.getText().toUpperCase());

                        }
                    }
                });
    }

    private void addKeyListenerToTextField2(final JTextField pJTextField, 
                                            final String pTipoCampo, 
                                            final String pInSoloLectura) {


        pJTextField.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {

                        if (pInSoloLectura.equalsIgnoreCase("S")) {
                            e.consume();
                        } else {
                            if (pTipoCampo.equalsIgnoreCase("E"))
                                FarmaUtility.admitirDigitos(pJTextField, e);
                            else if (pTipoCampo.equalsIgnoreCase("D"))
                                FarmaUtility.admitirDigitosDecimales(pJTextField, 
                                                                     e);
                        }
                    }

                    public void keyPressed(KeyEvent e) {
                        //log.info("keypressed : " + e.getKeyCode());
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            e.consume();
                            int vFila = tblLista.getSelectedRow();

                            if ((vFila + 1) == tblLista.getRowCount())
                                FarmaUtility.setearRegistro(tblLista, vFila, 
                                                            null, 0);
                            else
                                FarmaUtility.setearRegistro(tblLista, 
                                                            vFila + 1, null, 
                                                            0);

                            pJTextField.setText(pJTextField.getText().toUpperCase());
                        } else
                            tblLista_keyPressed(e);
                    }

                    public void keyReleased(KeyEvent e) {

                        int vFila = tblLista.getSelectedRow();

                        if (pTipoCampo.equalsIgnoreCase("F")) {
                            String codigoCampo = 
                                FarmaUtility.getValueFieldJTable(tblLista, 
                                                                 vFila, 
                                                                 COL_COD);
                            if (codigoCampo.trim().equals(ConstantsCajaElectronica.CAMPO_FECHA_OPERACION))
                                FarmaUtility.dateHourComplete(pJTextField, e);
                            else{
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

                        /*if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e) && 
                            pJTextField.isDisplayable()) {
                            pJTextField.selectAll();
                        } else */if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            //moveFocusTo(vFila);
                        } else {
                            if (e.getKeyCode() != KeyEvent.VK_LEFT && 
                                e.getKeyCode() != KeyEvent.VK_RIGHT && 
                                e.getKeyCode() != KeyEvent.VK_DELETE && 
                                e.getKeyCode() != KeyEvent.VK_BACK_SPACE && 
                                e.getKeyCode() != KeyEvent.VK_HOME && 
                                (!Character.isLetter(e.getKeyChar()) && 
                                 !Character.isWhitespace(e.getKeyChar()) && 
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
                        log.info(" cmbSexo.getSelectedIndex()>>"+ cmbSexo.getSelectedIndex());
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            log.info(" E cmbSexo.getSelectedIndex()>>"+ cmbSexo.getSelectedIndex());
                            /*if (pJComboBox.isPopupVisible())
                                pJComboBox.setPopupVisible(false);
                            else */{
                                if ((tblLista.getSelectedRow() + 1) == 
                                    tblLista.getRowCount()) {
                                    FarmaUtility.setearRegistro(tblLista, 
                                                                tblLista.getSelectedRow(), 
                                                                null, 0);
                                    moveFocusTo(0); //ASOSA, 06.04.2010
                                } else {
                                    String tipoSexo = "";
                                    String DescripcionSexo = "";
                                    try {
                                        tipoSexo = 
                                                FarmaLoadCVL.getCVLCode("CMB_SEXO", cmbSexo.getSelectedIndex());
                                        DescripcionSexo = 
                                                FarmaLoadCVL.getCVLDescription("CMB_SEXO", tipoSexo);
                                        
                                    } catch (Exception v) {
                                        tipoSexo = "";
                                        DescripcionSexo = "";
                                    }
                                    log.info("tipoSexo>>"+tipoSexo);
                                    log.info("DescripcionSexo>>"+DescripcionSexo);
                                    log.info("setearRegistro>>"+"setearRegistro");
                                    txtSexo.setText(DescripcionSexo);
                                    pEntro = true;
                                    /*
                                    FarmaUtility.setearRegistro(tblLista, 
                                                                tblLista.getSelectedRow() + 
                                                                1, txtSexo, 0);
                                    //moveFocusTo(tblLista.getSelectedRow());
                                    tblLista.changeSelection(tblLista.getSelectedRow(), 
                                                             1, false, 
                                                             false);*/ //ASOSA, 06.04.2010
                                }
                            }
                        }
                    }

                    public void keyReleased(KeyEvent e) {
                        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e) && 
                            pJComboBox.isDisplayable()) {
                            pJComboBox.setPopupVisible(true);
                        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            /*
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
        FarmaUtility.setearRegistro(tblLista, pRow, null, 0);
        tblLista.changeSelection(pRow, 1, false, false);
        log.info("pRow--"+pRow);
        
        if(FarmaUtility.getValueFieldArrayList(tableModel.data,pRow,0).trim().toUpperCase().indexOf("SEXO")!=-1)
        {
            ((JComboBox)cmbSexo).requestFocus();
            tblLista.changeSelection(pRow, 1, false, false);
            log.info("colquo foco");
            log.info("colquo foco CC");
            //cmbSexo.show();
            tblLista.repaint();
            cmbSexo.setPopupVisible(true);               
        }
        
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        initTable();
        //FarmaUtility.moveFocus(tblLista);
        tblLista.changeSelection(0, 1, false, false);
        lbl.setVisible(false); 
        
        if(buscaPosFila(ConstantsFidelizacion.SEXO_CLIENTE)>=0)
           lbl.setVisible(true); 
        
        if(txtNumeroDocumento.isEditable())
            moveFocusTo(0);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, 
                                 "Debe presionar la tecla ESC para cerrar la ventana.", 
                                 null);
    }

    private void btnLista_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblLista);
        //moveFocusTo(0);
        //tblLista.changeSelection(0,1, false, false);
    }

    private void tblLista_keyPressed(KeyEvent e) {
        log.info("esta aqui");
        chkKeyPressed(e);
    }

    /* ********************************************************************** */
    /*                     METODOS AUXILIARES DE EVENTOS                      */
    /* ********************************************************************** */

    private void chkKeyPressed(KeyEvent e) {
        //FarmaGridUtils.aceptarTeclaPresionada(e, tblLista, null, 0);
        /* if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e))
       {
         checkComboBoxComponent();
       }
       else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e))
       {
         if(FarmaVariables.vEconoFar_Matriz){
           FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,null);
           return;
         }
         checkComboBoxComponent();
       }else if (e.getKeyCode() == KeyEvent.VK_M)
       {
         if(FarmaVariables.vEconoFar_Matriz){
           FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,null);
           return;
         }
         checkComboBoxComponent();
       }else*/

        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (FarmaVariables.vEconoFar_Matriz) {
                FarmaUtility.showMessage(this, 
                                         ConstantsPtoVenta.MENSAJE_MATRIZ, 
                                         null);
                return;
            }
            for(int i=0;i<tblLista.getRowCount();i++){
               /* if(i!=vFila)
                FarmaUtility.setearRegistro(tblLista, i, null, 0);*/
                    tblLista.changeSelection(i, 1, false, false);
            }
            funcionF11();
            //if(VariablesCampana.vNumIngreso==1)
            //cerrarVentana(true);            
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            mensajeESC = "¿Está seguro salir de la ventana?";
            if (JOptionPane.showConfirmDialog(this, mensajeESC, 
                                              "Mensaje de Confirmacion - Mifarma", 
                                              JOptionPane.YES_NO_OPTION) == 
                0) {
                VariablesFidelizacion.vNumTarjeta = "";
                VariablesFidelizacion.vIndExisteCliente = false;
                VariablesFidelizacion.auxDataCli.clear();//jcortez 
                cerrarVentana(false);
                
            }
            //FarmaUtility.
            if (tblLista.getRowCount() > 0) {
                /*String pCodCampo = FarmaUtility.getValueFieldJTable(tblLista,0,2);
               log.debug("pCodCampo " + pCodCampo);
               if(pCodCampo.trim().equalsIgnoreCase(ConstantsFidelizacion.DNI_CLIENTE))
               {
                  FarmaUtility.moveFocus(tblLista);
                   log.debug("en foco");
               }*/

                FarmaUtility.moveFocus(tblLista);

            }

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

    public void txtEmail_keyTyped(KeyEvent e) {
        //FarmaUtility.admitirLetras(txtEmail, e);
    }

    public void txtNumeroDocumento_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtNumeroDocumento, e);
    }

    public void txtDireccion_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtDireccion, e);
    }

    public void txtTelefono_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtTelefono, e);
    }
    
    public void txtFechaNacimiento_keyReleased(KeyEvent e){
        char keyChar = e.getKeyChar();
        if (Character.isDigit(keyChar)){
            if (txtFechNac.getText().trim().length() == 5) 
            {
                log.debug("released 1" + txtFechNac.getText().trim());
                txtFechNac.setText(txtFechNac.getText().trim() + "/19");
                log.debug("released 2 " + txtFechNac.getText().trim());
            } 
        }
        
    }
    
    public void txtFechaNacimiento_keyTyped(KeyEvent e) {
        //FarmaUtility.dateComplete(txtFechNac, e);
        char keyChar = e.getKeyChar();
        
        if (Character.isDigit(keyChar)) {
            /*if ((txtFechNac.getText().trim().length() == 2) || 
                (txtFechNac.getText().trim().length() == 5)) 
            {
                txtFechNac.setText(txtFechNac.getText().trim() + "/");
            }*/
            if (txtFechNac.getText().trim().length() == 2) 
            {
                log.debug("fecha 1 " + txtFechNac.getText().trim());
                txtFechNac.setText(txtFechNac.getText().trim() + "/");
                
            }
            else
            {
                log.debug("fecha 2 " + txtFechNac.getText().trim());
                /*if (txtFechNac.getText().trim().length() == 5) 
                {
                    log.debug("fecha 3 " + txtFechNac.getText().trim());
                    txtFechNac.setText(txtFechNac.getText().trim() + keyChar+"/19");
                    log.debug("fecha 3 " + txtFechNac.getText().trim());
                }*/
            }
            
        }        
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.limpiar();
        this.setVisible(false);
        this.dispose();
    }

    private void limpiar() {
        VariablesFidelizacion.vDataCliente = new ArrayList();
        VariablesFidelizacion.vDniCliente = "";
        VariablesFidelizacion.vApePatCliente = "";
        VariablesFidelizacion.vApeMatCliente = "";
        VariablesFidelizacion.vNomCliente = "";
        VariablesFidelizacion.vFecNacimiento = "";
        VariablesFidelizacion.vSexo = 
                ""; //cmbSexo.getSelectedItem().toString().trim();//txtSexo.getText().trim();               
        VariablesFidelizacion.vDireccion = "";
        VariablesFidelizacion.vTelefono = "";


        VariablesFidelizacion.vIndEstado = "";
        
        
        VariablesFidelizacion.Tip_doc = "";
        VariablesFidelizacion.Usu_Confir  = "";
        //------------------------------------------------------------------
        VariablesFidelizacion.vDatosFinalTerceraValidacion = new ArrayList();
        VariablesFidelizacion.vDniCliente_bk ="";
        VariablesFidelizacion.vNomCliente_bk ="";
        VariablesFidelizacion.vFecNacimiento_bk ="";
        VariablesFidelizacion.Num_Doc ="";
        
        
        
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void funcionF11() {

        /* log.debug("txtNumeroDocumento.getText() " +  txtNumeroDocumento.getText());
       String pRes = "";
        try {
            pRes = DBFidelizacion.buscaDNI(txtNumeroDocumento.getText(),pCodTarjetaIngresado);
        } catch (Exception e) {
            log.error("",e);
        }

        if(pRes.trim().equalsIgnoreCase("S")){
            FarmaUtility.showMessage(this, "Cliente asociado a esta tarjeta.", null );
            VariablesCampana.vNumIngreso = 1;
            VariablesCampana.vFlag = true;
            cerrarVentana(true);

        }
        else
         FarmaUtility.showMessage(this, "Tarjeta no esta asociado a este cliente Ingrese sus datos.", null );*/
        tblLista.changeSelection(0, 0, true, false);
        if (validateMandatory()){
            try {
                String vSexo = "null";
                if (VariablesFidelizacion.vSexoExists == true) {
                    vSexo = cmbSexo.getSelectedItem().toString().trim();
                }

                /*
               VariablesFidelizacion.vDniCliente = txtNumeroDocumento.getText().trim();
               VariablesFidelizacion.vApePatCliente = txtApellidoPaterno.getText().trim();
               VariablesFidelizacion.vApeMatCliente = txtApellidoMaterno.getText().trim();
               VariablesFidelizacion.vNomCliente = txtNomCliente.getText().trim();
               VariablesFidelizacion.vFecNacimiento = txtFechNac.getText().trim();
               VariablesFidelizacion.vSexo = getSexoCliente();
               VariablesFidelizacion.vDireccion = txtDireccion.getText().trim();
               VariablesFidelizacion.vTelefono = txtTelefono.getText().trim();


               VariablesFidelizacion.vIndEstado  = "A";*/
                cargaVariables();
                
                if(valor){//jcortez evitar guardar dni con letras o caracteres no validos
                /*
               if(JOptionPane.showConfirmDialog(this, "Esta seguro de grabar esta informacion",
                                    "Mensaje de Confirmación", JOptionPane.YES_NO_OPTION)==0){*/
                
                //JCORTEZ 02.07.09 Si no existe tarjeta para el Dni permite ingresar nueva tarjeta
                log.debug("TARJETA ENCONTRADA-->  "+VariablesFidelizacion.auxDataCli);
                 String formato = "";
                 if(VariablesFidelizacion.vNumTarjeta.trim().length()>6){
                    formato = VariablesFidelizacion.vNumTarjeta.substring(0, 5);
                 }
                 
                
                if(VariablesFidelizacion.auxDataCli.size()>0 && !formato.equals("99999"))
                {
                    log.debug("TARJETA ENCONTRADA-->  "+VariablesFidelizacion.auxDataCli);
                    FarmaUtility.showMessage(this, "Dni ya registrado. Se guardara y/o cargara el cliente!!!", null );
                     VariablesFidelizacion.vNumTarjeta=((String)((ArrayList)VariablesFidelizacion.auxDataCli.get(0)).get(0)).trim();
                    DBFidelizacion.insertarClienteFidelizacion();
                }
                else{
                            DBFidelizacion.insertarClienteFidelizacion();
                    }
                log.debug("jjccaalloo:VariablesFidelizacion.vDniCliente"+VariablesFidelizacion.vDniCliente);
                //FarmaUtility.showMessage(this, VariablesFidelizacion.vNumTarjeta +"-"+VariablesFidelizacion.vDniCliente, null );
                cerrarVentana(true);
                /*}else{
                   FarmaUtility.moveFocus(txtNumeroDocumento);
               }*/
                 }

            } catch (SQLException e) {
                log.error("",e);

            }
        }
    }

    private boolean validateMandatory() {
        boolean dataExists = true;
        String vIndObligatorio;
        String vCodCampo;
        String vDato;

        for (int i = 0; i < tblLista.getRowCount(); i++) {
            vIndObligatorio = 
                    FarmaUtility.getValueFieldJTable(tblLista, i, COL_IND_OBLI);

            if (vIndObligatorio.equalsIgnoreCase("S")) {
                vCodCampo = 
                        FarmaUtility.getValueFieldJTable(tblLista, i, COL_COD);
                vDato = 
                        FarmaUtility.getValueFieldJTable(tblLista, i, COL_DATO);

                if (vDato.length() == 0) {
                    dataExists = false;
                    moveFocusTo(i);
                    FarmaUtility.showMessage(this, 
                                             "Campo " + findFieldDescription(vCodCampo) + 
                                             " no tiene información.  Verifique !!!", 
                                             null);
                    break;
                }

                if (vCodCampo.equals(ConstantsFidelizacion.FECHA_NAC_CLIENTE)) {
                    if (!FarmaUtility.validaFecha(vDato, "dd/MM/yyyy")) {
                        dataExists = false;
                        moveFocusTo(i);
                        FarmaUtility.showMessage(this, 
                                                 "Campo " + findFieldDescription(vCodCampo) + 
                                                 " contiene un fecha invalida.  Verifique !!!", 
                                                 null);
                        break;
                    }
                }

                if (vCodCampo.equals(ConstantsFidelizacion.DNI_CLIENTE)) {
                    if ( !UtilityFidelizacion.validarDocIdentidad(vDato.trim()) ) {
                        dataExists = false;
                        moveFocusTo(i);
                        FarmaUtility.showMessage(this, 
                                                 "Campo " + findFieldDescription(vCodCampo) + 
                                                 " es invalido.  Verifique !!!", 
                                                 null);
                        break;
                    }

                    /*if (!UtilityFidelizacion.isNumericoBest(vDato)) {
                        dataExists = false;
                        moveFocusTo(i);
                        FarmaUtility.showMessage(this, 
                                                 "Campo " + findFieldDescription(vCodCampo) + 
                                                 " es invalido.  Verifique !!!", 
                                                 null);
                        break;
                    }*/

                }
                
                if (vCodCampo.equals(ConstantsFidelizacion.EMAIL_CLIENTE)) {
                    
                    if (!UtilityFidelizacion.validarEmail(vDato.trim())) {
                        dataExists = false;
                        moveFocusTo(i);
                        FarmaUtility.showMessage(this, 
                                                 "Campo " + findFieldDescription(vCodCampo) + 
                                                 " invalido.Verifique !!!", 
                                                 null);
                        break;
                    }
                }
                 

            }
        }
        
        
        log.debug("FINAL DE VALIDAR :"  +dataExists);
        
        if(dataExists){
          log.debug("***********************entro a validacion**********************");
            VariablesFidelizacion.vDniCliente = txtNumeroDocumento.getText().trim();
            VariablesFidelizacion.vNomCliente = txtNomCliente.getText().trim();
            VariablesFidelizacion.vFecNacimiento = txtFechNac.getText().trim();
            dataExists = validaDocumento();
            log.debug("salida :"  +dataExists);
            
        }
        return dataExists;
    }

    private String findFieldDescription(String pFieldValue) {
        String fieldDescription = "NO DETERMINADO";
        for (int i = 0; i < aCampos.size(); i++) {
            if (((String)((ArrayList)aCampos.get(i)).get(2)).equalsIgnoreCase(pFieldValue)) {
                fieldDescription = 
                        ((String)((ArrayList)aCampos.get(i)).get(0)).trim();
                break;
            }
        }
        return fieldDescription;
    }


    private void cargaVariables() {
        
        log.debug("qiere cc"+txtNumeroDocumento.getText().trim());
        log.debug("qiere cc"+txtApellidoPaterno.getText().trim());
       
        //JCORTEZ valida DNI
            valor=FarmaUtility.validateNumber(this,txtNumeroDocumento,"Número de documento no válido.",true);
        if(valor)  {
            try{
            //JCORTEZ 02.07.09 obtiene tarjeta existente en local
            DBFidelizacion.buscarTarjetasDni(VariablesFidelizacion.auxDataCli,txtNumeroDocumento.getText().trim());
               log.debug("JCORTEZ TARJETA... " + VariablesFidelizacion.auxDataCli);
            }catch(Exception a){
                log.error("",a);
            }
        VariablesFidelizacion.vDniCliente = 
                txtNumeroDocumento.getText().trim();
        VariablesFidelizacion.vApePatCliente = 
                txtApellidoPaterno.getText().trim();
        VariablesFidelizacion.vApeMatCliente = 
                txtApellidoMaterno.getText().trim();
        VariablesFidelizacion.vNomCliente = txtNomCliente.getText().trim();
        VariablesFidelizacion.vEmail = txtEmail.getText().trim();
        VariablesFidelizacion.vFecNacimiento = txtFechNac.getText().trim();
        VariablesFidelizacion.vSexo = getSexoCliente();
        VariablesFidelizacion.vDireccion = txtDireccion.getText().trim();
        VariablesFidelizacion.vTelefono = txtTelefono.getText().trim();
        VariablesFidelizacion.vIndEstado = "A";

        if (VariablesFidelizacion.vApePatCliente.trim().length() == 0)
            VariablesFidelizacion.vApePatCliente = "N";
        if (VariablesFidelizacion.vApeMatCliente.trim().length() == 0)
            VariablesFidelizacion.vApeMatCliente = "N";
        if (VariablesFidelizacion.vNomCliente.trim().length() == 0)
            VariablesFidelizacion.vNomCliente = "N";
        if (VariablesFidelizacion.vFecNacimiento.trim().length() == 0)
            VariablesFidelizacion.vFecNacimiento = "N";
        if (VariablesFidelizacion.vSexo.trim().length() == 0)
            VariablesFidelizacion.vSexo = "N";
        if (VariablesFidelizacion.vDireccion.trim().length() == 0)
            VariablesFidelizacion.vDireccion = "N";
        if (VariablesFidelizacion.vTelefono.trim().length() == 0)
            VariablesFidelizacion.vTelefono = "N";
        if (VariablesFidelizacion.vEmail.trim().length() == 0)
            VariablesFidelizacion.vEmail = "N";
        }
        
        
        //SE COLOCAN LOS VALORES FINALES SI ENTRO A LA PANTALLA DE VALIDACION DE CLIENTES.
        //dubilluz 20.10.2009
        log.info("VariablesFidelizacion.vDatosFinalTerceraValidacion:"+VariablesFidelizacion.vDatosFinalTerceraValidacion);
        if(VariablesFidelizacion.vDatosFinalTerceraValidacion != null)
        if(VariablesFidelizacion.vDatosFinalTerceraValidacion.size()>0){
            log.info("Coloca los valores que se ingresaron en la tercera validacion");
            VariablesFidelizacion.vDniCliente = (String)(VariablesFidelizacion.vDatosFinalTerceraValidacion.get(0));
            VariablesFidelizacion.vNomCliente = (String)(VariablesFidelizacion.vDatosFinalTerceraValidacion.get(1));
            VariablesFidelizacion.vFecNacimiento = (String)(VariablesFidelizacion.vDatosFinalTerceraValidacion.get(2));
        }
        
        
    }

    private void cargaDatosCliente(){
        // pCodTarjetaIngresado = 

        log.debug("Datos del cliente " + 
                           VariablesFidelizacion.vDataCliente);
        /*
        A.DNI_CLI           || 'Ã' ||
                A.APE_PAT_CLI       || 'Ã' ||
                A.APE_MAT_CLI       || 'Ã' ||
                A.NOM_CLI           || 'Ã' ||
                A.FEC_NAC_CLI       || 'Ã' ||
                A.SEXO_CLI          || 'Ã' ||
                A.DIR_CLI           || 'Ã' ||
                A.FONO_CLI
        * */
        //tblLista.setValueAt("11s",1,1);
        //int posRowNomCli = getPosNomCli(ConstantsConvenio.CAMPO_CODIGO_TRABAJADOR);
        ArrayList vListaDatosDNI = new ArrayList();
        ArrayList vListaCampos = new ArrayList();
        String vDNI = "", vApePat = "", vApeMat = "", vNom = "", vFecNac = 
            "", vSexCli = "", vDirCli = "", vTlfCli = "", vEma = "";

        try {
            DBFidelizacion.getDatosDNI(vListaDatosDNI, pCodTarjetaIngresado);
            DBFidelizacion.getCamposTarjeta(vListaCampos);
            log.debug("vListaCampos " + vListaCampos);
            log.debug("vListaDatosDNI " + vListaDatosDNI);
            /*
            private final int COL_DNI = 0;
            private final int COL_APE_PAT = 1;
            private final int COL_APE_MAT = 2;
            private final int COL_NOM_CLI = 3;
            private final int COL_FEC_NAC = 4;
            private final int COL_SEX_CLI = 5;
            private final int COL_DIR_CLI = 6;
            private final int COL_TLF_CLI = 7;  */
            vDNI = 
        FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_DNI);
            vApePat = 
                    FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_APE_PAT);
            vApeMat = 
                    FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_APE_MAT);
            vNom = 
        FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_NOM_CLI);
            vNom = 
        FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_NOM_CLI);
            vFecNac = 
                    FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_FEC_NAC);
            vSexCli = 
                    FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_SEX_CLI);
            vDirCli = 
                    FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_DIR_CLI);
            vTlfCli = 
                    FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_TLF_CLI);
            vEma = 
        FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_EMAIL);
            /*
            002     DNI_CLIENTE    -
            003     NOMBRE_CLIENTE
            004     APEPAT_CLIENTE
            005     EMAIL_CLIENTE
            006     TELEFONO_CLIENTE
            007     DIREC_CLIENTE
            008     SEXO_CLIENTE
            009     FECHA_NAC_CLIENTE
            * */
            int pos = -3;
            if (vListaDatosDNI.size() > 0) {
                if (!vDNI.equalsIgnoreCase("N")) {
                    pos = buscaPosFila(ConstantsFidelizacion.DNI_CLIENTE);
                    if (pos >= 0) {
                        tblLista.setValueAt(vDNI, pos, 1);
                        txtNumeroDocumento.setEditable(false);
                    }
                    ;
                } else {
                    pos = buscaPosFila(ConstantsFidelizacion.DNI_CLIENTE);
                    if (pos >= 0) {
                        tblLista.setValueAt("", pos, 1);
                        //txtNumeroDocumento.setEditable(false);
                    }
                    ;
                }


                if (!vApePat.equalsIgnoreCase("N")) {
                    pos = buscaPosFila(ConstantsFidelizacion.APEPAT_CLIENTE);
                    if (pos >= 0) {
                        tblLista.setValueAt(vApePat, pos, 1);
                        txtApellidoPaterno.setEditable(false);
                    }
                }

                if (!vApeMat.equalsIgnoreCase("N")) {
                    pos = buscaPosFila(ConstantsFidelizacion.APEMAT_CLIENTE);
                    if (pos >= 0) {
                        tblLista.setValueAt(vApeMat, pos, 1);
                        txtApellidoMaterno.setEditable(false);
                    }
                }

                if (!vNom.equalsIgnoreCase("N")) {
                    pos = buscaPosFila(ConstantsFidelizacion.NOMBRE_CLIENTE);
                    if (pos >= 0) {
                        tblLista.setValueAt(vNom, pos, 1);
                        txtNomCliente.setEditable(false);
                    }
                }

                if (!vEma.equalsIgnoreCase("N")) {
                    pos = buscaPosFila(ConstantsFidelizacion.EMAIL_CLIENTE);
                    if (pos >= 0) {
                        tblLista.setValueAt(vEma, pos, 1);
                        txtEmail.setEditable(false);
                    }
                }

                if (!vFecNac.equalsIgnoreCase("N")) {
                    pos = 
                        buscaPosFila(ConstantsFidelizacion.FECHA_NAC_CLIENTE);
                    if (pos >= 0) {
                        tblLista.setValueAt(vFecNac, pos, 1);
                        txtFechNac.setEditable(false);
                    }
                }

                if (!vSexCli.equalsIgnoreCase("N")) {
                    pos = buscaPosFila(ConstantsFidelizacion.SEXO_CLIENTE);
                    if (pos >= 0) {
                        tblLista.setValueAt(vSexCli, pos, 1);
                        txtSexo.setEditable(false);
                    }
                }

                if (!vDirCli.equalsIgnoreCase("N")) {
                    pos = buscaPosFila(ConstantsFidelizacion.DIREC_CLIENTE);
                    if (pos >= 0) {
                        tblLista.setValueAt(vDirCli, pos, 1);
                        txtDireccion.setEditable(false);
                    }
                }

                if (!vTlfCli.equalsIgnoreCase("N")) {
                    pos = buscaPosFila(ConstantsFidelizacion.TELEFONO_CLIENTE);
                    if (pos >= 0) {
                        tblLista.setValueAt(vTlfCli, pos, 1);
                        txtTelefono.setEditable(false);
                    }

                }

                //coloca el foto en el valor q  este editable y q se ingrese
                //String 
                //txtNumeroDocumento.setText(txtNumeroDocumento.getText().trim());
                //tblLista.setValueAt(,1,1);
            } else {
                pos = buscaPosFila(ConstantsFidelizacion.DNI_CLIENTE);
                if (pos >= 0) {
                    tblLista.setValueAt("", pos, 1);
                }
                ;
            }


        } catch (SQLException e) {
            log.error("",e);
        }        
    }

    private static void buscaMatrizActualizaDatosLocal(String pDni){

        ArrayList array = new ArrayList();
        
        try {
            DBFidelizacion.getDatosExisteDNI_Matriz(array,pDni, "N");
            log.debug("Datos Cli en Matriz "+ array);
            
            VariablesFidelizacion.vDniCliente = FarmaUtility.getValueFieldArrayList(array,0,0).trim();
            /**verificando si en matriz el dni es $**/
            if( !VariablesFidelizacion.vDniCliente.equalsIgnoreCase("$") ) {
                
                //VariablesFidelizacion.vNumTarjeta =  pDni.trim();   
                VariablesFidelizacion.vApePatCliente = FarmaUtility.getValueFieldArrayList(array,0,1).trim();
                VariablesFidelizacion.vApeMatCliente =FarmaUtility.getValueFieldArrayList(array,0,2).trim();
                VariablesFidelizacion.vNomCliente =FarmaUtility.getValueFieldArrayList(array,0,3).trim();
                VariablesFidelizacion.vFecNacimiento =FarmaUtility.getValueFieldArrayList(array,0,4).trim();
                VariablesFidelizacion.vSexo = FarmaUtility.getValueFieldArrayList(array,0,5).trim();
                VariablesFidelizacion.vDireccion = FarmaUtility.getValueFieldArrayList(array,0,6).trim();
                VariablesFidelizacion.vTelefono =FarmaUtility.getValueFieldArrayList(array,0,7).trim();
               
                VariablesFidelizacion.vIndEstado  = "A";
                //Este metodo de se encargara de insertar y/o actualizar
                //insertarClienteFidelizacion
                DBFidelizacion.insertarClienteLocal();
                
            }
            VariablesFidelizacion.vDniCliente = "";
            //VariablesFidelizacion.vNumTarjeta =  "";
            VariablesFidelizacion.vApePatCliente = "";
            VariablesFidelizacion.vApeMatCliente = "";
            VariablesFidelizacion.vNomCliente = "";
            VariablesFidelizacion.vFecNacimiento = "";
            VariablesFidelizacion.vSexo = "";
            VariablesFidelizacion.vDireccion = "";
            VariablesFidelizacion.vTelefono = "";
            
        } catch (SQLException e) {
            log.error("",e);
        }
    }
    private boolean buscaDatosCliente(String pDNI){
       long tmpIni,tmpFin;
       
       tmpIni = System.currentTimeMillis();
       log.info("tmpIni:"+tmpIni);
        
        if(UtilityFidelizacion.validarDocIdentidad(pDNI.trim())){
        log.info("Datos del cliente " + 
                           VariablesFidelizacion.vDataCliente);
        //NO VENDRA A MATRIZ
        //JCORTEZ 02.07.09 se vuelve a buscar en matriz
        // UtilityFidelizacion.validarConexionMatriz();  
         
         
        ArrayList vListaDatosDNI = new ArrayList();
        ArrayList vListaCampos = new ArrayList();
        String vDNI = "", vApePat = "", vApeMat = "", vNom = "", vFecNac = 
            "", vSexCli = "", vDirCli = "", vTlfCli = "", vEma = "";

        try {
            //JCORTEZ 05.10.09 Se obtiene datos de PBL_DNI_RED o FID_TARJETA
            DBFidelizacion.getDatosExisteDNI(vListaDatosDNI, pDNI);
            int pExist = -1 ;
            log.info("vListaDatosDNI:"+vListaDatosDNI);
            if (vListaDatosDNI.size() > 0) 
                pExist = FarmaUtility.getValueFieldArrayList(vListaDatosDNI,0,0).trim().indexOf("$");
            /*
            if (pExist!=-1) {
                if(VariablesFidelizacion.vIndConexion.trim().equalsIgnoreCase("S")){
                    log.info("busca en matriz e inserta");
                    buscaMatrizActualizaDatosLocal(pDNI);
                    vListaDatosDNI = new ArrayList();
                    DBFidelizacion.getDatosExisteDNI(vListaDatosDNI, pDNI);
                    if (vListaDatosDNI.size() > 0) 
                        pExist = FarmaUtility.getValueFieldArrayList(vListaDatosDNI,0,0).trim().indexOf("$");                    
                }
            }
            */
            log.info("pExist:"+pExist);
            if (pExist==-1) {
            
    
                DBFidelizacion.getCamposTarjeta(vListaCampos);
                log.debug("vListaCampos " + vListaCampos);
                log.debug("vListaDatosDNI " + vListaDatosDNI);

                vDNI = 
FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_DNI);
                vApePat = 
                        FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, 
                                                            COL_APE_PAT);
                vApeMat = 
                        FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, 
                                                            COL_APE_MAT);
                vNom = 
FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_NOM_CLI);
                vNom = 
FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_NOM_CLI);
                vFecNac = 
                        FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, 
                                                            COL_FEC_NAC);
                vSexCli = 
                        FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, 
                                                            COL_SEX_CLI);
                vDirCli = 
                        FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, 
                                                            COL_DIR_CLI);
                vTlfCli = 
                        FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, 
                                                            COL_TLF_CLI);
                vEma = 
FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_EMAIL);
                int pos = -3;
                if (vListaDatosDNI.size() > 0) {
                    if (!vDNI.equalsIgnoreCase("N")) {
                        pos = buscaPosFila(ConstantsFidelizacion.DNI_CLIENTE);
                        if (pos >= 0) {
                            tblLista.setValueAt(vDNI, pos, 1);
                            txtNumeroDocumento.setEditable(false);
                        }
                        ;
                    } else {
                        pos = buscaPosFila(ConstantsFidelizacion.DNI_CLIENTE);
                        if (pos >= 0) {
                            tblLista.setValueAt("", pos, 1);
                            //txtNumeroDocumento.setEditable(false);
                        }
                        ;
                    }


                    if (!vApePat.equalsIgnoreCase("N")) {
                        pos = 
buscaPosFila(ConstantsFidelizacion.APEPAT_CLIENTE);
                        if (pos >= 0) {
                            tblLista.setValueAt(vApePat, pos, 1);
                            txtApellidoPaterno.setEditable(false);
                        }
                    }

                    if (!vApeMat.equalsIgnoreCase("N")) {
                        pos = 
buscaPosFila(ConstantsFidelizacion.APEMAT_CLIENTE);
                        if (pos >= 0) {
                            tblLista.setValueAt(vApeMat, pos, 1);
                            txtApellidoMaterno.setEditable(false);
                        }
                    }

                        
                        if (!vNom.equalsIgnoreCase("N")) {
                            //nvl(nvl('@'||reniec.nombre,'&'||cliente.nombre),'N')
                            String pIndEditable = vNom.trim().substring(0,1);
                            pos = 
                                buscaPosFila(ConstantsFidelizacion.NOMBRE_CLIENTE);
                            if (pos >= 0) {
                                tblLista.setValueAt(vNom.substring(1), pos, 1);
                                if(pIndEditable.trim().equalsIgnoreCase("@"))
                                //sera editable si es por RENIEC                                
                                txtNomCliente.setEditable(true);
                                else
                                    if(pIndEditable.trim().equalsIgnoreCase("&"))
                                        //no se editara si es de cliente.
                                        txtNomCliente.setEditable(false);
                                    else
                                        txtNomCliente.setEditable(false);
                            }
                        }

                    if (!vEma.equalsIgnoreCase("N")) {
                        pos = 
buscaPosFila(ConstantsFidelizacion.EMAIL_CLIENTE);
                        if (pos >= 0) {
                            tblLista.setValueAt(vEma, pos, 1);
                            txtEmail.setEditable(false);
                        }
                    }

                    if (!vFecNac.equalsIgnoreCase("N")) {
                        pos = 
buscaPosFila(ConstantsFidelizacion.FECHA_NAC_CLIENTE);
                        if (pos >= 0) {
                            tblLista.setValueAt(vFecNac, pos, 1);
                            txtFechNac.setEditable(false);
                        }
                    }

                    if (!vSexCli.equalsIgnoreCase("N")) {
                        pos = buscaPosFila(ConstantsFidelizacion.SEXO_CLIENTE);
                        if (pos >= 0) {
                            tblLista.setValueAt(vSexCli, pos, 1);
                            txtSexo.setEditable(false);
                        }
                    }

                    if (!vDirCli.equalsIgnoreCase("N")) {
                        pos = 
buscaPosFila(ConstantsFidelizacion.DIREC_CLIENTE);
                        if (pos >= 0) {
                            tblLista.setValueAt(vDirCli, pos, 1);
                            txtDireccion.setEditable(false);
                        }
                    }

                    if (!vTlfCli.equalsIgnoreCase("N")) {
                        pos = 
buscaPosFila(ConstantsFidelizacion.TELEFONO_CLIENTE);
                        if (pos >= 0) {
                            tblLista.setValueAt(vTlfCli, pos, 1);
                            txtTelefono.setEditable(false);
                        }

                    }
                } else {
                    pos = buscaPosFila(ConstantsFidelizacion.DNI_CLIENTE);
                    if (pos >= 0) {
                        tblLista.setValueAt("", pos, 1);
                    }
                    ;
                }
                tblLista.repaint();
                VariablesFidelizacion.vIndExisteCliente = true;

                tmpFin = System.currentTimeMillis();      
                log.info("tmpFin: "+tmpFin);
                log.info("Dif Tiempo buscaDatosCliente() : "+(tmpFin - tmpIni)+" milisegundos");                
                return true;
            }
            else {
                
                tmpFin = System.currentTimeMillis();      
                log.info("tmpFin: "+tmpFin);
                log.info("Dif Tiempo buscaDatosCliente() : "+(tmpFin - tmpIni)+" milisegundos");
                
                return false;
            }


        } catch (SQLException e) {
            log.error("",e);
        }        
        tmpFin = System.currentTimeMillis();      
        log.info("tmpFin: "+tmpFin);
        log.info("Dif Tiempo buscaDatosCliente() : "+(tmpFin - tmpIni)+" milisegundos");
        return false;
    }
    else{
        tmpFin = System.currentTimeMillis();      
        log.info("tmpFin: "+tmpFin);
        log.info("Dif Tiempo buscaDatosCliente() : "+(tmpFin - tmpIni)+" milisegundos");
        return false;
        }
   }
    
    /**
     * @autor dubilluz
     * @since 04.10.2009
     * @return
     */
    public boolean validaDocumento(){
        boolean resultado = false;
        VariablesFidelizacion.vDniCliente_bk = txtNumeroDocumento.getText().trim();
        VariablesFidelizacion.vNomCliente_bk = txtNomCliente.getText().trim();
        VariablesFidelizacion.vFecNacimiento_bk = txtFechNac.getText().trim();
        boolean pValor = false;
        String pValidaReniec = "";
        try {
            
            pValidaReniec = DBFidelizacion.getIndValidaReniec().trim();
            log.info("pValidaReniec-->"+pValidaReniec);
            if(pValidaReniec.equalsIgnoreCase("S")) {
                log.debug("Valida Datos a RENIEC");
                pValor = DBFidelizacion.isValidaDocumento(VariablesFidelizacion.vDniCliente_bk, 
                                                 VariablesFidelizacion.vFecNacimiento_bk);
                
                log.info("pValor-->"+pValor);
                if(pValor){//El dni no esta en reniec entonces debe de validarse el documento
                    DlgFidelizacionValidaDocumento pDlgValida = 
                                new DlgFidelizacionValidaDocumento(myParentFrame,"",true);
                    pDlgValida.setVisible(true);
                    log.info("Cerrar pantalla.."+FarmaVariables.vAceptar);
                    if(FarmaVariables.vAceptar){
                        resultado = true;//el documento se valido
                    }
                    else{
                        //;
                        moveFocusTo(tblLista.getRowCount()-1);
                        resultado = false;
                            //o dio escape o cancelo validacion
                    }    
                }
                else
                    resultado = true;//el documento se valido
            }
            else{
                log.info("NO VALIDA Datos a RENIEC");
                resultado = true;
            }
            
        } catch (SQLException e) {
            resultado = true;
            log.info(e.getMessage());
        } finally {

        }
        
        return resultado;
    }
    
    public void funcionGeneralF11(){
        
        tblLista.changeSelection(0, 0, true, false);
        for(int i=0;i<tblLista.getRowCount();i++){
                tblLista.changeSelection(i, 1, false, false);
        }
        
        if(validaDatosCompletos()){
            log.info("Tiene datos Completos se fuerza a un F11");
            funcionF11();
        }

    }
    private boolean validaDatosCompletos() {
        boolean dataExists = true;
        String vIndObligatorio;
        String vCodCampo;
        String vDato;

        for (int i = 0; i < tblLista.getRowCount(); i++) {
            vIndObligatorio = 
                    FarmaUtility.getValueFieldJTable(tblLista, i, COL_IND_OBLI);

            if (vIndObligatorio.equalsIgnoreCase("S")) {
                vCodCampo = 
                        FarmaUtility.getValueFieldJTable(tblLista, i, COL_COD);
                vDato = 
                        FarmaUtility.getValueFieldJTable(tblLista, i, COL_DATO);

                if (vDato.length() == 0) {
                    dataExists = false;
                    moveFocusTo(i);

                    break;
                }

                if (vCodCampo.equals(ConstantsFidelizacion.FECHA_NAC_CLIENTE)) {
                    if (!FarmaUtility.validaFecha(vDato, "dd/MM/yyyy")) {
                        dataExists = false;
                        moveFocusTo(i);

                        break;
                    }
                }

                if (vCodCampo.equals(ConstantsFidelizacion.DNI_CLIENTE)) {
                    if ( !UtilityFidelizacion.validarDocIdentidad(vDato.trim()) ) {
                        dataExists = false;
                        moveFocusTo(i);

                        break;
                    }


                }
                
                if (vCodCampo.equals(ConstantsFidelizacion.EMAIL_CLIENTE)) {
                    
                    if (!UtilityFidelizacion.validarEmail(vDato.trim())) {
                        dataExists = false;
                        moveFocusTo(i);
                        break;
                    }
                }
                 

            }
        }
        
        
        log.debug("Tiene Datos Completos :"  +dataExists);

        return dataExists;
    }
    
}
