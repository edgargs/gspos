package mifarma.ptoventa.fidelizacion;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import farmapuntos.bean.Afiliado;
import farmapuntos.bean.TarjetaBean;

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

import mifarma.common.FarmaJTable;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaRowEditorModel;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.fidelizacion.reference.ConstantsFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.DBFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.UtilityFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.lealtad.reference.FacadeLealtad;
import mifarma.ptoventa.puntos.reference.ConstantsPuntos;
import mifarma.ptoventa.puntos.reference.FacadePuntos;
import mifarma.ptoventa.puntos.reference.UtilityPuntos;
import mifarma.ptoventa.puntos.reference.VariablesPuntos;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;

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
 * ASOSA       03.02.2015   Modificación<br>
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
    private final int COL_VISIBLE = 6;  //ASOSA - 03/02/2015 - PTOSYAYAYAYA
    private Afiliado afiliado = new Afiliado();
    

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
    
    //INI ASOSA - 04/02/2015 - PTOSYAYAYAYA
    private JTextFieldSanSerif txtCelular = new JTextFieldSanSerif();
    private JComboBox cmbDepartamento = new JComboBox();
    private JTextFieldSanSerif txtDepartamento = new JTextFieldSanSerif();
    private JComboBox cmbProvincia = new JComboBox();
    private JTextFieldSanSerif txtProvincia = new JTextFieldSanSerif();
    private JComboBox cmbDistrito = new JComboBox();
    private JTextFieldSanSerif txtDistrito = new JTextFieldSanSerif();
    private JComboBox cmbTipoDireccion = new JComboBox();
    private JTextFieldSanSerif txtTipoDireccion = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtReferencias = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtTipoLugar = new JTextFieldSanSerif();
    private JComboBox cmbTipoLugar = new JComboBox();
    //FIN ASOSA - 04/02/2015 - PTOSYAYAYAYA

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


    private final int COL_DNI = 0;
    private final int COL_APE_PAT = 1;
    private final int COL_APE_MAT = 2;
    private final int COL_NOM_CLI = 3;
    private final int COL_FEC_NAC = 4;
    private final int COL_SEX_CLI = 5;
    private final int COL_DIR_CLI = 6;
    private final int COL_TLF_CLI = 7;
    private final int COL_EMAIL = 8;
    
    private final int COL_CEL = 9;
    private final int COL_DEPA = 10;
    private final int COL_PROV = 11;
    private final int COL_DIST = 12;
    private final int COL_TIPDIR = 13;
    private final int COL_TIPLUG = 14;
    private final int COL_REFER = 15;
    private JLabelFunction lblf1 = new JLabelFunction();

    //JCORTEZ 03.07.09
    private boolean valor = false;
    private JLabelOrange jLabelOrange1 = new JLabelOrange();
    private JLabelOrange jLabelOrange2 = new JLabelOrange();


    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgFidelizacionClientes() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public DlgFidelizacionClientes(Frame parent, String title, boolean modal, String pCodTarjeta) {
        super(parent, title, modal);
        pCodTarjetaIngresado = pCodTarjeta.trim();

        //Agregado por DVELIZ 30.09.08
        VariablesFidelizacion.vNumTarjeta = pCodTarjeta.trim();
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
        this.setSize(new Dimension(583, 373));
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

        jLabelOrange1.setText("(*) CAMPOS OBLIGATORIOS");
        jLabelOrange1.setBounds(new Rectangle(15, 280, 165, 15));
        jLabelOrange2.setText("[BARRA ESPACIADORA] = @");
        jLabelOrange2.setBounds(new Rectangle(15, 300, 170, 15));
        lbl.setBounds(new Rectangle(10, 245, 230, 25));
        lbl.setText("Presionar [F2] para desplegar el listado");

        scrLista.getViewport().add(tblLista, null);
        pnlTitle1.add(btnLista, null);
        //jContentPane.add(lblF1, null);
        //jContentPane.add(lblf1, null);
        jContentPane.add(jLabelOrange2, null);
        jContentPane.add(jLabelOrange1, null);
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

        txtEmail.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtEmail_keyPressed(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtEmail_keyReleased(e);
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
        
        txtCelular.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtCelular_keyTyped(e);
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

        
        //INI ASOSA - 04/02/2015 - PTOSYAYAYAYA
        cmbDepartamento.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                cmbDepartamento_itemStateChanged(e);
            }
        });
        cmbDepartamento.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbDepartamento_keyPressed(e);
            }
        });
        
        cmbProvincia.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                cmbProvincia_itemStateChanged(e);
            }
        });
        cmbProvincia.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbProvincia_keyPressed(e);
            }
        });
        
        
        
        cmbTipoLugar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbTipoLugar_keyPressed(e);
            }
        });
        
        //FIN ASOSA - 04/02/2015 - PTOSYAYAYAYA
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ********************************************************************** */
    /*                                  METODO initialize                     */
    /* ********************************************************************** */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        
        try {
            VariablesFidelizacion.vDocValidos = DBFidelizacion.obtenerParamDocIden();
            // KMONCADA 2015.02.12 CAMBIO PARA CARGA DE FORMULARIO SEGUN BEAN DE TABLA PBL_CLIENTE
            // OBTIENE EL DNI DEL CLIENTE
            String nroDni = DBFidelizacion.getDniClienteFidelizado(VariablesFidelizacion.vNumTarjeta);
            if(nroDni != null){
                FacadePuntos facadeAfiliado = new FacadePuntos();
                afiliado = facadeAfiliado.obtenerClienteFidelizado(nroDni);    
            }else{
                afiliado = new Afiliado();
            }
            
        } catch (Exception e) {
            log.debug("error : " + e);
        }
        log.debug("longitud de docs validos : " + VariablesFidelizacion.vDocValidos);
        
        
    }

    /* ********************************************************************** */
    /*                            METODOS INICIALIZACION                      */
    /* ********************************************************************** */

    private void initTable() {

        tableModel =
                new FarmaTableModel(ConstantsFidelizacion.columnsListaDatosFidelizacion, ConstantsFidelizacion.defaultValuesListaDatosFidelizacion,
                                    0, ConstantsFidelizacion.editableListaDatosFidelizacion, null);
        rowEditorModel = new FarmaRowEditorModel();
        tblLista.setAutoCreateColumnsFromModel(false);
        tblLista.setModel(tableModel);
        tblLista.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //tblLista.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        for (int k = 0; k < tableModel.getColumnCount(); k++) {
            TableColumn column = new TableColumn(k, ConstantsFidelizacion.columnsListaDatosFidelizacion[k].m_width);
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

        //JCORTEZ  24.07.09 Se setea el DNI
        String codCampo = "";
        int pos = 0;
        if (!VariablesFidelizacion.NumDniAux.equalsIgnoreCase("")) {
            if (tblLista.getRowCount() > 0) {
                for (int i = 0; i < tblLista.getRowCount(); i++) {
                    codCampo = FarmaUtility.getValueFieldJTable(tblLista, i, COL_COD);
                    if (codCampo.equals(ConstantsFidelizacion.DNI_CLIENTE)) {
                        pos = buscaPosFila(codCampo);
                        if (pos >= 0) {
                            log.info("Set DNI_CLIENTE-->" + VariablesFidelizacion.NumDniAux);
                            tblLista.setValueAt(VariablesFidelizacion.NumDniAux, pos, 1);
                            moveFocusTo(i);
                        }
                    }
                }

            }
        }
        VariablesFidelizacion.NumDniAux = "";
    }

    private void cargaCampos() {
        cargaLista();
        setTipoCampo();
        tblLista.repaint();
        cargaDatosCliente();

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
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al cargar los campos.", tblLista);
        }
        aCampos = tableModel.data;
    }
    
    /**
     * obtener un indice de fila por codigo de campo
     * @author ASOSA
     * @since 04/02/2015
     * @kind PTOSYAYAYAYA
     * @param codCampo
     * @return
     */
    public int getIndice(String codCampo) {
        int c = -1;
        for (int i = 0; i < tblLista.getRowCount(); i++) {
            String codigoCampo = tblLista.getValueAt(i, COL_COD).toString().trim();
            if (codigoCampo.equals(codCampo)){
                c = i;
                break;
            }            
        }
        return c;
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
        String vIndVisible;
            for (int i = 0; i < tableModel.data.size(); i++) {
            /*
              for (int i = 0; i < tblLista.getRowCount(); i++) { 
                codigoCampo = tblLista.getValueAt(i, COL_COD).toString().trim();
                vTipoDato = FarmaUtility.getValueFieldJTable(tblLista, i, COL_TIPO_DATO);
                vIndSoloLec = FarmaUtility.getValueFieldJTable(tblLista, i, COL_SOLO_LECTURA);
                //dveliz 26.08.08
                vIndOblig = FarmaUtility.getValueFieldJTable(tblLista, i, COL_IND_OBLI);
                vIndVisible = FarmaUtility.getValueFieldJTable(tblLista, i, COL_VISIBLE);   //ASOSA - 03/02/2015 - PTOSYAYAYAYA
            */
            codigoCampo = FarmaUtility.getValueFieldArrayList(tableModel.data, i, COL_COD);            
            vTipoDato = FarmaUtility.getValueFieldArrayList(tableModel.data, i,  COL_TIPO_DATO);
            vIndSoloLec = FarmaUtility.getValueFieldArrayList(tableModel.data, i,  COL_SOLO_LECTURA);            
            vIndOblig = FarmaUtility.getValueFieldArrayList(tableModel.data, i,  COL_IND_OBLI);
            vIndVisible = FarmaUtility.getValueFieldArrayList(tableModel.data, i, COL_VISIBLE);   
            /*
            if (vIndOblig.equals("S")) {
                VariablesCampana.vFlagMandatory = true;
            }
            */
            //fin dveliz

                if (i == 0) {
                    if (codigoCampo.equals(ConstantsFidelizacion.DNI_CLIENTE)) {
                        getTxtNumeroDocumento(i, vTipoDato, vIndSoloLec);
                        FarmaUtility.moveFocus(txtNumeroDocumento);
                    } else if (codigoCampo.equals(ConstantsFidelizacion.APEPAT_CLIENTE)) {
                        getTxtApellidoPaterno(i, vTipoDato, vIndSoloLec);
                        FarmaUtility.moveFocus(txtApellidoPaterno);
                    } else if (codigoCampo.equals(ConstantsFidelizacion.APEMAT_CLIENTE)) {
                        getTxtApellidoPaterno(i, vTipoDato, vIndSoloLec);
                        FarmaUtility.moveFocus(txtApellidoMaterno);
                    } else if (codigoCampo.equals(ConstantsFidelizacion.NOMBRE_CLIENTE)) {
                        getTxtNombre(i, vTipoDato, vIndSoloLec);
                        FarmaUtility.moveFocus(txtNomCliente);
                    } else if (codigoCampo.equals(ConstantsFidelizacion.EMAIL_CLIENTE)) {
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
                        getCmbSexo(i);
                        FarmaUtility.moveFocus(cmbSexo);
                    //INI ASOSA - 03/02/2015 - PTOSYAYAYAYA
                    } else if (codigoCampo.equals(ConstantsFidelizacion.CELULAR_CLIENTE)) {
                        getTxtCelular(i, vTipoDato, vIndSoloLec);
                        FarmaUtility.moveFocus(txtCelular);
                    } else if (codigoCampo.equals(ConstantsFidelizacion.DEPARTAMENTO)) {
                        getCmbDepartamento(i);
                        FarmaUtility.moveFocus(cmbDepartamento);
                    } else if (codigoCampo.equals(ConstantsFidelizacion.PROVINCIA)) {
                        getCmbProvincia(i);
                        FarmaUtility.moveFocus(cmbProvincia);
                    } else if (codigoCampo.equals(ConstantsFidelizacion.DISTRITO)) {
                        getCmbDistrito(i);
                        FarmaUtility.moveFocus(cmbDistrito);
                    } else if (codigoCampo.equals(ConstantsFidelizacion.TIPO_DIRECCION)) {
                        getCmbTipoDireccion(i);
                        FarmaUtility.moveFocus(cmbTipoDireccion);
                    } else if (codigoCampo.equals(ConstantsFidelizacion.TIPO_LUGAR)) {
                        getCmbTipoLugar(i);
                        FarmaUtility.moveFocus(cmbTipoLugar);
                    } else if (codigoCampo.equals(ConstantsFidelizacion.REFERENCIAS)) {
                        getTxtReferencias(i, vTipoDato, vIndSoloLec);
                        FarmaUtility.moveFocus(txtReferencias);
                    }
                    //FIN ASOSA - 03/02/2015 - PTOSYAYAYAYA
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
                        getCmbSexo(i);
                    } else if (codigoCampo.equals(ConstantsFidelizacion.EMAIL_CLIENTE)) {
                        getTxtEmail(i, vTipoDato, vIndSoloLec);
                    //INI ASOSA - 04/02/2015 - PTOSYAYAYAYA
                    } else if (codigoCampo.equals(ConstantsFidelizacion.CELULAR_CLIENTE)) {
                        getTxtCelular(i, vTipoDato, vIndSoloLec);                    
                    } else if (codigoCampo.equals(ConstantsFidelizacion.DEPARTAMENTO)) {                    
                        getCmbDepartamento(i);                    
                    } else if (codigoCampo.equals(ConstantsFidelizacion.PROVINCIA)) {                    
                        getCmbProvincia(i);                    
                    } else if (codigoCampo.equals(ConstantsFidelizacion.DISTRITO)) {                    
                        getCmbDistrito(i);                    
                    } else if (codigoCampo.equals(ConstantsFidelizacion.TIPO_DIRECCION)) {                    
                        getCmbTipoDireccion(i);                    
                    } else if (codigoCampo.equals(ConstantsFidelizacion.TIPO_LUGAR)) {                    
                        getCmbTipoLugar(i);
                    } else if (codigoCampo.equals(ConstantsFidelizacion.REFERENCIAS)) {
                        getTxtReferencias(i, vTipoDato, vIndSoloLec);                    
                    }
                    //FIN ASOSA - 04/02/2015 - PTOSYAYAYAYA
                }            
        }
    }

    private void getTxtNumeroDocumento(int i, String vTipoDato, String vIndSoloLec) {
        txtNumeroDocumento.setLengthText(9);
        
        String vDNI = afiliado.getDni().trim();
        if (vDNI.equals("")) {
            vIndSoloLec = "N";
        }
        
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
    
    //INI ASOSA - 04/02/2015- PTOSYAYAYAYA
    private void getTxtCelular(int i, String vTipoDato, String vIndSoloLec) {
        txtCelular.setLengthText(9);
        createJTextField(txtCelular, i, vTipoDato, vIndSoloLec);
    }
    
    private void getTxtReferencias(int i, String vTipoDato, String vIndSoloLec) {
        txtReferencias.setLengthText(30);
        createJTextField(txtReferencias, i, vTipoDato, vIndSoloLec);
    }
    

    public void getCmbSexo(int i) {
        createJComboBox(cmbSexo, 
                        i,
                        "CMB_SEXO",
                        compValor,
                        compDescripcion,
                        txtSexo);
    }
    
    public void getCmbDepartamento(int i) {
        ArrayList parametros = new ArrayList();
        createJComboBoxSP(cmbDepartamento, 
                        i,
                        "CMB_DEPARTAMENTO",
                        parametros,
                        "PTOVENTA_DEL_CLI.GET_DPTOS",
                        txtDepartamento);
    }
    
    public void cargarProvincias(int i){
        ArrayList parametros = new ArrayList();
        String vsCodDpto;
        vsCodDpto = FarmaLoadCVL.getCVLCode("CMB_DEPARTAMENTO", 
                                            cmbDepartamento.getSelectedIndex());
        parametros.add(vsCodDpto);
        createJComboBoxSP(cmbProvincia, 
                        i,
                        "CMB_PROVINCIA",
                        parametros,
                        "PTOVENTA_DEL_CLI.GET_PROVI(?)",
                        txtProvincia);
    }
    
    public void getCmbProvincia(int i) {
        cargarProvincias(i);
    }
    
    public void cargarDistrito(int i) {
        ArrayList parametros = new ArrayList();
        String vsCodDpto, vsCodProvi;
        vsCodDpto = FarmaLoadCVL.getCVLCode("CMB_DEPARTAMENTO", 
                                            cmbDepartamento.getSelectedIndex());
        vsCodProvi = FarmaLoadCVL.getCVLCode("CMB_PROVINCIA", 
                                             cmbProvincia.getSelectedIndex());
        
        parametros.add(vsCodDpto);
        parametros.add(vsCodProvi);
        createJComboBoxSP(cmbDistrito, 
                        i,
                        "CMB_DISTRITO",
                        parametros,
                        "PTOVENTA_DEL_CLI.GET_DIST(?,?)",
                        txtDistrito);
    }
    
    public void getCmbDistrito(int i) {
        cargarDistrito(i);
    }
    //FIN ASOSA - 04/02/2015- PTOSYAYAYAYA
    //INI ASOSA - 05/02/2015- PTOSYAYAYAYA
    public void getCmbTipoDireccion(int i) {
        ArrayList parametros = new ArrayList();
        parametros.add(15);
        createJComboBoxSP(cmbTipoDireccion, 
                        i,
                        "CMB_TIPO_DIRECCION",
                        parametros,
                        "PTOVENTA_FIDELIZACION.GET_MAE_DETALLE(?)",
                        txtTipoDireccion);
    }
    
    public void getCmbTipoLugar(int i) {
        ArrayList parametros = new ArrayList();
        parametros.add(16);
        createJComboBoxSP(cmbTipoLugar, 
                        i,
                        "CMB_TIPO_LUGAR",
                        parametros,
                        "PTOVENTA_FIDELIZACION.GET_MAE_DETALLE(?)",
                        txtTipoLugar);
    }
    //FIN ASOSA - 05/02/2015- PTOSYAYAYAYA
    private void createJTextField(JTextField pJTextField, int pRow, String pTipoCampo, String pInSoloLectura) {

        addKeyListenerToTextField(pJTextField, pTipoCampo, pInSoloLectura);
        defaultCellEditor = new DefaultCellEditor(pJTextField);
        rowEditorModel.addEditorForRow(pRow, defaultCellEditor);
    }

    /* ********************************************************************** */
    /* SECCION : CREACION DE CAMPOS JCOMBOBOX                                 */
    /* ********************************************************************** */

    private void createJComboBox(JComboBox pJComboBox, 
                                 int pRow, 
                                 String idCombo,
                                 String[] pValue, 
                                 String[] pDescription,
                                 JTextFieldSanSerif textBox) {

        createJComboBoxAux(pJComboBox, idCombo, pValue, pDescription, pRow, textBox);
    }
    
    //ASOSA - 04/02/2015- PTOSYAYAYAYA
    private void createJComboBoxSP(JComboBox pJComboBox, 
                                 int pRow, 
                                 String idCombo,
                                 ArrayList parametros,
                                 String nameStore,
                                 JTextFieldSanSerif textBox) {

        createJComboBoxAuxSP(pJComboBox, idCombo, parametros, nameStore, pRow, textBox);
    }

    private String getSexoCliente() {
        String tipoSexo;
        try {
            tipoSexo = FarmaLoadCVL.getCVLCode("CMB_SEXO", cmbSexo.getSelectedIndex());
        } catch (ArrayIndexOutOfBoundsException e) {
            tipoSexo = "";
        }

        return tipoSexo;
    }
    //ASOSA - 04/02/2015- PTOSYAYAYAYA - MODIFIED TOO
    private void createJComboBoxAux(JComboBox pJComboBox, String pNameComboBox, String[] pValue, String[] pDescription,
                                    int pRow, JTextFieldSanSerif textBox) {
        FarmaLoadCVL.loadCVLfromArrays(pJComboBox, pNameComboBox, pValue, pDescription, false);
        addKeyListenerJComboBox(pJComboBox, pNameComboBox, textBox);
        defaultCellEditor = new DefaultCellEditor(pJComboBox);
        rowEditorModel.addEditorForRow(pRow, defaultCellEditor);
    }
    
    //ASOSA - 04/02/2015- PTOSYAYAYAYA
    private void createJComboBoxAuxSP(JComboBox pJComboBox, String pNameComboBox, 
                                      ArrayList parametros,
                                      String nameStore,
                                      int pRow,
                                      JTextFieldSanSerif textBox) { 
        log.info("parametros: " + parametros);
        FarmaLoadCVL.unloadCVL(pJComboBox, pNameComboBox);
        FarmaLoadCVL.loadCVLFromSP(pJComboBox, pNameComboBox, nameStore,
                                   parametros, false, true);

        addKeyListenerJComboBox(pJComboBox, pNameComboBox, textBox);
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
                    pJTextField.setEditable(false);
                    e.consume();                   
                } else {
                    if (pTipoCampo.equalsIgnoreCase("E"))
                        FarmaUtility.admitirDigitos(pJTextField, e);
                    else if (pTipoCampo.equalsIgnoreCase("D"))
                        FarmaUtility.admitirDigitosDecimales(pJTextField, e);
                    else if (pTipoCampo.equalsIgnoreCase("F")) {                        
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

                    pCodigoCampoAux = FarmaUtility.getValueFieldJTable(tblLista, vFila, 2);

                    if (pCodigoCampoAux.trim().equalsIgnoreCase(ConstantsFidelizacion.DNI_CLIENTE)) {
                        long tmpIni_dni, tmpFin_dni;

                        tmpIni_dni = System.currentTimeMillis();
                        log.info("tmpIni_dni ENTER:" + tmpIni_dni);

                        String pCadena = pJTextField.getText().trim();
                        log.info("Busqueda DNI si existe : " + pCadena);

                        try {
                            //JCORTEZ 02.07.09 obtiene tarjeta existente en local
                            DBFidelizacion.buscarTarjetasDni(VariablesFidelizacion.auxDataCli, pCadena);
                            log.info("JCORTEZ TARJETA... " + VariablesFidelizacion.auxDataCli);
                        } catch (Exception a) {
                            log.error("", a);
                        }

                        //JCORTEZ 04.08.09 Se obtiene dni del cliente para cargar cupones emitidos
                        //VariablesVentas.dniListCupon = pCadena.trim();

                        if (!buscaDatosCliente(pCadena)) {
                            log.info("2.Dentro de if !buscaDatosCliente(pCadena)");
                            long tmpIni_2, tmpFin_2;

                            tmpIni_2 = System.currentTimeMillis();
                            log.info("tmpIni_2 ENTER:" + tmpIni_2);

                            if ((vFila + 1) == tblLista.getRowCount()){
                                moveFocusTo(0);
                            } else {
                                long tmpIni_3, tmpFin_3;

                                tmpIni_3 = System.currentTimeMillis();
                                log.info("tmpIni_3 ENTER:" + tmpIni_3);

                                FarmaUtility.setearRegistro(tblLista, vFila + 1, null, 0);
                                tblLista.changeSelection(vFila + 1, 1, false, false);
                                //dubilluz - 05.04.2010
                                /* El campo sexo debe comportarse como cualquier otro.
                                if (FarmaUtility.getValueFieldArrayList(tableModel.data, vFila + 1,
                                                                        0).trim().toUpperCase().indexOf("SEXO") !=
                                    -1) {
                                    ((JComboBox)cmbSexo).requestFocus();
                                    tblLista.changeSelection(vFila + 1, 1, false, false);
                                    log.info("colquo foco aa");
                                    //cmbSexo.show();
                                    tblLista.repaint();
                                    cmbSexo.setPopupVisible(true);
                                }
                                */

                                tmpFin_3 = System.currentTimeMillis();
                                log.info("tmpFin_3 ENTER:" + tmpFin_3);
                                log.info("Diferencia :" + (tmpFin_3 - tmpIni_3) + " milisegundos");
                            }

                            pJTextField.setText(pJTextField.getText().toUpperCase());


                            tmpFin_2 = System.currentTimeMillis();
                            log.info("tmpFin_2 ENTER:" + tmpFin_2);
                            log.info("Diferencia dentro de !buscaDatosCliente(pCadena):" + (tmpFin_2 - tmpIni_2) +
                                     " milisegundos");

                        } else {


                            if ((vFila + 1) == tblLista.getRowCount()){
                                moveFocusTo(0);
                            }else {

                                FarmaUtility.setearRegistro(tblLista, vFila + 1, null, 0);
                                tblLista.changeSelection(vFila + 1, 1, false, false);
                            }

                            ////Se validara si tiene todos los datos completos para
                            ///que simule el F11
                            funcionGeneralF11();

                           

                        }


                       
                        tmpFin_dni = System.currentTimeMillis();
                        log.info("tmpFin_dni ENTER:" + tmpFin_dni);

                        log.info("fin ValidacionDNICLIENTE: " + (tmpFin_dni - tmpIni_dni) + " milisegundos");
                        if (txtNomCliente.isEditable())
                            moveFocusTo(1);
                    } else {
                        
                        if ((vFila + 1) == tblLista.getRowCount())
                            moveFocusTo(0);
                        else {

                            FarmaUtility.setearRegistro(tblLista, vFila + 1, null, 0);
                            tblLista.changeSelection(vFila + 1, 1, false, false);
                            // dubilluz - 05.04.2010
                            /* El campo sexo debe comportarse como cualquier otro.
                            if (FarmaUtility.getValueFieldArrayList(tableModel.data, vFila + 1,
                                                                    0).trim().toUpperCase().indexOf("SEXO") != -1) {
                                ((JComboBox)cmbSexo).requestFocus();
                                //tblLista.changeSelection(vFila+1, 1, false, false);
                                log.info("colquo foco");
                                //cmbSexo.show();
                                //tblLista.repaint();
                                log.info("colquo foco BB");
                                cmbSexo.setPopupVisible(true);
                            }
*/

                        }

                        pJTextField.setText(pJTextField.getText().toUpperCase());
                        
                    }


                    tmpFin = System.currentTimeMillis();
                    log.info("tmpFin ENTER:" + tmpFin);
                    log.info("Dif Tiempo ENTER:" + (tmpFin - tmpIni) + " milisegundos");


                } else{
                    if (pInSoloLectura.equalsIgnoreCase("S")) {
                        pJTextField.setEditable(false);                        
                    }
                    tblLista_keyPressed(e);
                }
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

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if ((vFila + 1) < tblLista.getRowCount()) {
                        moveFocusTo(vFila);
                    }

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


    private void addKeyListenerJComboBox(final JComboBox pJComboBox,
                                         final String nameCombo,
                                         final JTextFieldSanSerif textBox) {

        pJComboBox.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {

            }

            public void keyPressed(KeyEvent e) {
                log.info(" combo>>" + pJComboBox.getSelectedIndex());
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    log.info(" combo>>" + pJComboBox.getSelectedIndex());
                    {
                        String codigo = "";
                        String descripcion = "";
                        try {
                            log.info("COMBO: " + nameCombo + " - index: " + pJComboBox.getSelectedIndex());
                            log.info("COMBO z: " + pJComboBox.hashCode());
                            
                            codigo = FarmaLoadCVL.getCVLCode(nameCombo, pJComboBox.getSelectedIndex());                            
                            descripcion = FarmaLoadCVL.getCVLDescription(nameCombo, codigo);
                        } catch (Exception v) {
                            codigo = "";
                            descripcion = "";
                            log.error("",v);
                        }
                        
                        
                        if ((tblLista.getSelectedRow() + 1) == tblLista.getRowCount()) {
                            FarmaUtility.setearRegistro(tblLista, tblLista.getSelectedRow(), null, 0);
                            //moveFocusTo(0); //ASOSA, 06.04.2010
                        } else {
                            
                            log.info("codigo>>" + codigo);
                            log.info("descripcion>>" + descripcion);
                            log.info("setearRegistro>>" + "setearRegistro");
                            textBox.setText(descripcion);
                            pEntro = true;                           
                        }
                        
                    }
                }
            }

            public void keyReleased(KeyEvent e) {
                if (UtilityPtoVenta.verificaVK_F2(e) && pJComboBox.isDisplayable()) {
                    pJComboBox.setPopupVisible(true);
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {

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
        log.info("pRow--" + pRow);
        /* El campo sexo debe comportarse como cualquier otro.
        if (FarmaUtility.getValueFieldArrayList(tableModel.data, pRow, 0).trim().toUpperCase().indexOf("SEXO") != -1) {
            ((JComboBox)cmbSexo).requestFocus();
            tblLista.changeSelection(pRow, 1, false, false);
            log.info("colquo foco");
            log.info("colquo foco CC");
            //cmbSexo.show();
            tblLista.repaint();
            cmbSexo.setPopupVisible(true);
        }
*/
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        initTable();
        //FarmaUtility.moveFocus(tblLista);
        tblLista.changeSelection(0, 1, false, false);
        lbl.setVisible(true);

/*
        if (buscaPosFila(ConstantsFidelizacion.SEXO_CLIENTE) >= 0)
            lbl.setVisible(true);
*/

        if (txtNumeroDocumento.isEditable())
            moveFocusTo(0);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnLista_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblLista);
    }

    private void tblLista_keyPressed(KeyEvent e) {
        log.info("esta aqui");
        chkKeyPressed(e);
    }

    /* ********************************************************************** */
    /*                     METODOS AUXILIARES DE EVENTOS                      */
    /* ********************************************************************** */

    private void chkKeyPressed(KeyEvent e) {

        if (UtilityPtoVenta.verificaVK_F11(e)) {
            
            log.info("txtNumeroDocumento: " + txtNumeroDocumento.getText());
            log.info("txtNomCliente: " + txtNomCliente.getText());
            log.info("txtTelefono: " + txtTelefono.getText());
            log.info("txtDireccion: " + txtDireccion.getText());
            log.info("txtSexo: " + FarmaLoadCVL.getCVLCode("CMB_SEXO", cmbSexo.getSelectedIndex())  + " - " + txtSexo.getText());
            log.info("txtFechNac: " + txtFechNac.getText());
            log.info("txtEmail: " + txtEmail.getText());
            log.info("txtCelular: " + txtCelular.getText());
            
            log.info("txtDepartamento: " + txtDepartamento.getText());
            log.info("txtProvincia: " + txtProvincia.getText());
            log.info("txtDistrito: " + txtDistrito.getText());
            log.info("txtTipoDireccion: " + txtTipoDireccion.getText());
            log.info("txtTipoLugar: " + txtTipoLugar.getText());
            log.info("txtReferencias: " + txtReferencias.getText());

            
            
            if (FarmaVariables.vEconoFar_Matriz) {
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, null);
                return;
            }

            for (int i = 0; i < tblLista.getRowCount(); i++) {            
                tblLista.changeSelection(i, 1, false, false);
            }
            
            funcionF11();
            
            //if(VariablesCampana.vNumIngreso==1)
            //cerrarVentana(true);
            
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            mensajeESC = "¿Está seguro salir de la ventana?";
            if(FarmaUtility.rptaConfirmDialog(this, mensajeESC)){
            /*if (JConfirmDialog.rptaConfirmDialog(this, mensajeESC, "Mensaje de Confirmacion - Mifarma",
                                              JOptionPane.YES_NO_OPTION) == 0) {*/
                VariablesFidelizacion.vNumTarjeta = "";
                VariablesFidelizacion.vIndExisteCliente = false;
                VariablesFidelizacion.auxDataCli.clear(); //jcortez
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

    public void txtNumeroDocumento_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtNumeroDocumento, e);
    }

    public void txtDireccion_keyTyped(KeyEvent e) {
        //FarmaUtility.admitirDigitos(txtDireccion, e);
    }

    public void txtTelefono_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtTelefono, e);
    }
    
    
    public void txtCelular_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCelular, e);
    }
    

    public void txtFechaNacimiento_keyReleased(KeyEvent e) {
        char keyChar = e.getKeyChar();
        if (Character.isDigit(keyChar)) {
            if (txtFechNac.getText().trim().length() == 5) {
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
            if (txtFechNac.getText().trim().length() == 2) {
                log.debug("fecha 1 " + txtFechNac.getText().trim());
                txtFechNac.setText(txtFechNac.getText().trim() + "/");

            } else {
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
        this.setVisible(pAceptar);
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
        VariablesFidelizacion.Usu_Confir = "";
        //------------------------------------------------------------------
        VariablesFidelizacion.vDatosFinalTerceraValidacion = new ArrayList();
        VariablesFidelizacion.vDniCliente_bk = "";
        VariablesFidelizacion.vNomCliente_bk = "";
        VariablesFidelizacion.vFecNacimiento_bk = "";
        VariablesFidelizacion.Num_Doc = "";


    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */
    
    
    private void setearAfiliado() {
        String vDNI = txtNumeroDocumento.getText();
        String vApePat = txtApellidoPaterno.getText();
        String vApeMat = txtApellidoMaterno.getText(); 
        String vNom = txtNomCliente.getText(); 
        //String vApellidos = UtilityFidelizacion.getApellidos(vNom);
        String vFecNac = txtFechNac.getText().trim();
        
        String vSexCli = FarmaLoadCVL.getCVLCode("CMB_SEXO", cmbSexo.getSelectedIndex()); 
        String vDirCli = txtDireccion.getText(); 
        String vTlfCli = txtTelefono.getText(); 
        String vEmail = txtEmail.getText();
        String vCelular = txtCelular.getText();
        String vDepartamento="";
        if(cmbDepartamento.getSelectedIndex()!= -1){
                vDepartamento = FarmaLoadCVL.getCVLCode("CMB_DEPARTAMENTO", cmbDepartamento.getSelectedIndex());
            }
        String vProvincia = "";
        if(cmbProvincia.getSelectedIndex() != -1){
            vProvincia = FarmaLoadCVL.getCVLCode("CMB_PROVINCIA", cmbProvincia.getSelectedIndex());    
        }
        String vDistrito = "";
        if(cmbDistrito.getSelectedIndex() != -1){
            vDistrito = FarmaLoadCVL.getCVLCode("CMB_DISTRITO", cmbDistrito.getSelectedIndex());    
        }
        String vTipoDireccion = "";
        if(cmbTipoDireccion.getSelectedIndex()!= -1){
            vTipoDireccion = FarmaLoadCVL.getCVLCode("CMB_TIPO_DIRECCION", cmbTipoDireccion.getSelectedIndex());    
        }
        String vTipoLugar = "";
        if(cmbTipoLugar.getSelectedIndex()!= -1){
            vTipoLugar = FarmaLoadCVL.getCVLCode("CMB_TIPO_LUGAR", cmbTipoLugar.getSelectedIndex());    
        }
        
        String vReferencia = txtReferencias.getText();
        

        Afiliado afiliado01 = new Afiliado();
        afiliado01.setDni(vDNI);
        afiliado01.setNombre(vNom);
        afiliado01.setApParterno(vApePat);
        afiliado01.setApMarterno(vApeMat);
        afiliado01.setFechaNacimiento(vFecNac);
        afiliado01.setGenero(vSexCli);
        afiliado01.setDireccion(vDirCli);
        afiliado01.setTelefono(vTlfCli);
        afiliado01.setEmail(vEmail);
        afiliado01.setCelular(vCelular);
        afiliado01.setDepartamento(vDepartamento);
        afiliado01.setProvincia(vProvincia);
        afiliado01.setDistrito(vDistrito);
        afiliado01.setTipoDireccion(vTipoDireccion);
        afiliado01.setReferencias(vReferencia);
        afiliado01.setTipoLugar(vTipoLugar);
        setAfiliado(afiliado01);

    }
    

    private void funcionF11() {
        boolean cierraVentana = false;
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
        
        tblLista.changeSelection(0, 1, true, false);
        log.info("ANTES: " + txtNumeroDocumento.getText());
        if (validateMandatory()) {
            try {
                String vSexo = "null";
                if (VariablesFidelizacion.vSexoExists == true) {
                    vSexo = cmbSexo.getSelectedItem().toString().trim();
                }

                setearAfiliado();   //ASOSA - 06/02/2015 - PTOSYAYAYAYA

                //cargaVariables();              
                // KMONCADA 2015.02.15 VARIABLE DE ESTADO DE CLIENTE
                VariablesFidelizacion.vIndEstado = "A";

                if (true) { //jcortez evitar guardar dni con letras o caracteres no validos

                    //JCORTEZ 02.07.09 Si no existe tarjeta para el Dni permite ingresar nueva tarjeta
                    log.debug("TARJETA ENCONTRADA-->  " + VariablesFidelizacion.auxDataCli);
                    String formato = "";
                    boolean isTarjeFidelizacion = false;
                    if (VariablesFidelizacion.vNumTarjeta.trim().length() > 6) {
                        isTarjeFidelizacion = UtilityFidelizacion.EsTarjetaFidelizacion(VariablesFidelizacion.vNumTarjeta);
                        //formato = VariablesFidelizacion.vNumTarjeta.substring(0, 5);
                    }
                    // KMONCADA 2015.02.12 
                    String indicadorLinea = ConstantsPuntos.TRSX_ORBIS_NO_APLICA;
                    //KMONCADA 30.06.2015 REGISTRO DE AFILIADO EN EL LOCAL SIN VARIABLES DE ORBIS
                    DBFidelizacion.insertarClienteFidelizacion(ConstantsPuntos.IND_PROCESO_ORBIS_DESACT, indicadorLinea, afiliado);
                    if(UtilityPuntos.isActivoFuncionalidad()){
                        //INI ASOSA - 06/02/2015 - PTOSYAYAYAYA
                        TarjetaBean obj = VariablesPuntos.frmPuntos.getTarjetaBean();
                        // KMONCADA 2015.02.13 SIEMPRE ENVIARA NUMERO DE TARJETA DEL OBJETO DE TARJETA
                        if (obj != null) {
                            //KMONCADA 25.06.2015 SE REALIZA IMPRESION DE VOUCHER
                            //UtilityFidelizacion.imprimirVoucherFid(txtNumeroDocumento.getText(), obj.getNumeroTarjeta());
                            boolean rsptaValida = UtilityPuntos.impresionVoucherAfiliacion(myParentFrame, this, obj.getNumeroTarjeta(), txtNumeroDocumento.getText());
                            if(rsptaValida){
                                if(UtilityPuntos.registrarCliente(obj.getNumeroTarjeta().trim(), afiliado)) {
                                    indicadorLinea = ConstantsPuntos.TRSX_ORBIS_ENVIADA;
                                }else{//KMONCADA 15.05.2015
                                    indicadorLinea = ConstantsPuntos.TRSX_ORBIS_PENDIENTE;
                                }
                                UtilityPuntos.actualizarEstadoEnvioAfiliacion(obj.getNumeroTarjeta(), txtNumeroDocumento.getText(), indicadorLinea);
                            }else{
                                cerrarVentana(false);
                                return ;
                            }
                        }
                        //FIN ASOSA - 06/02/2015 - PTOSYAYAYAYA
                    }
                    cierraVentana = true;
                    //if (VariablesFidelizacion.auxDataCli.size() > 0 && !formato.equals("99999")) {
                    if (VariablesFidelizacion.auxDataCli.size() > 0 && !isTarjeFidelizacion) {
                        log.debug("TARJETA ENCONTRADA-->  " + VariablesFidelizacion.auxDataCli);
                        FarmaUtility.showMessage(this, "Dni ya registrado. Se guardara y/o cargara el cliente!!!", null);
                        VariablesFidelizacion.vNumTarjeta = ((String)((ArrayList)VariablesFidelizacion.auxDataCli.get(0)).get(0)).trim();                        
                    } else {
                        VariablesFidelizacion.vNumTarjeta = pCodTarjetaIngresado;
                    }
                    
                    /*if(UtilityPuntos.isActivoFuncionalidad()){
                        //INI ASOSA - 06/02/2015 - PTOSYAYAYAYA
                        TarjetaBean obj = VariablesPuntos.frmPuntos.getTarjetaBean();
                        // KMONCADA 2015.02.13 SIEMPRE ENVIARA NUMERO DE TARJETA DEL OBJETO DE TARJETA
                        UtilityPuntos.registrarCliente(obj.getNumeroTarjeta().trim(), afiliado);
                        //FIN ASOSA - 06/02/2015 - PTOSYAYAYAYA
                    }*/
                    
                    FarmaUtility.showMessage(this, "Se registro el cliente", null);
                    log.debug("jjccaalloo:VariablesFidelizacion.vDniCliente" + VariablesFidelizacion.vDniCliente);
                    
                    //Impresion de VOUCHER adicional para que el cliente escriba datos que en un principio no quiso dar, what the hell ...???.
                    //INI ASOSA - 17/04/2015 - PTOSYAYAYAYA
                    /*
                    log.info("UtilityPuntos.isActivoFuncionalidad(): " + UtilityPuntos.isActivoFuncionalidad());
                    log.info("getAfiliado().getDni(): " + getAfiliado().getDni());
                    if (UtilityPuntos.isActivoFuncionalidad() 
                        && obj != null
                        && obj.getEstadoTarjeta().equals(FarmaPuntosConstante.EstadoTarjeta.INACTIVA)) {
                        log.info("ENTRO");
                        UtilityFidelizacion.imprimirVoucherFid(getAfiliado().getDni());
                        FarmaUtility.showMessage(this, 
                                                 "Recoger voucher de verificación de datos", 
                                                 null);
                    }
                    */
                    //FIN ASOSA - 17/04/2015 - PTOSYAYAYAYA
                    cerrarVentana(true);
                    
                }

            } catch (Exception e) {
                log.error("", e);
                FarmaUtility.showMessage(this,
                                         "Ocurrio un error al grabar al cliente"+e.getMessage(),
                                         null);
                FarmaUtility.liberarTransaccion();  //ASOSA - 17/02/2015 - PTOSYAYAYAYA
                if(cierraVentana)
                    cerrarVentana(false);
                
            }
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
                
                if (vCodCampo.equals(ConstantsFidelizacion.CELULAR_CLIENTE) && UtilityPuntos.isActivoFuncionalidad()) {
                    if (vDato.trim().length() == 0) {
                        vDato = "  ";
                    }
                }else{
                    if (vDato.trim().length() == 0) {
                        dataExists = false;
                        
                        moveFocusTo(i);
                        FarmaUtility.showMessage(this,
                                                 "Campo " + findFieldDescription(vCodCampo) + " no tiene información.  Verifique !!!",
                                                 null);
                        break;
                    }
                }

                if (vCodCampo.equals(ConstantsFidelizacion.FECHA_NAC_CLIENTE)) {
                    if (!FarmaUtility.validaFecha(vDato, "dd/MM/yyyy") || (vDato.length() < 10)) {
                        dataExists = false;
                        moveFocusTo(i);
                        FarmaUtility.showMessage(this,
                                                 "Campo " + findFieldDescription(vCodCampo) + " contiene un fecha invalida.  Verifique !!!",
                                                 null);
                        break;
                    }
                }
                
                if (vCodCampo.equals(ConstantsFidelizacion.DNI_CLIENTE)) {
                    if (!UtilityFidelizacion.validarDocIdentidad(vDato.trim())) {
                        dataExists = false;
                        moveFocusTo(i);
                        FarmaUtility.showMessage(this,
                                                 "Campo " + findFieldDescription(vCodCampo) + " es invalido.  Verifique !!!",
                                                 null);
                        break;
                    }

                }
                log.info("DESPUES: " + txtNumeroDocumento.getText());

                if (vCodCampo.equals(ConstantsFidelizacion.EMAIL_CLIENTE)) {

                    if (!UtilityFidelizacion.validarEmail(vDato.trim())) {
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


        log.debug("FINAL DE VALIDAR :" + dataExists);

        if (dataExists) {
            log.debug("***********************entro a validacion**********************");
            VariablesFidelizacion.vDniCliente = txtNumeroDocumento.getText().trim();
            VariablesFidelizacion.vNomCliente = txtNomCliente.getText().trim();
            VariablesFidelizacion.vFecNacimiento = txtFechNac.getText().trim();
            dataExists = validaDocumento();
            log.debug("salida :" + dataExists);

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
        //INI ASOSA - 12/02/2015 - PTOSYAYAYAYA
        fieldDescription = fieldDescription.replace("(*)", "");
        //FIN ASOSA - 12/02/2015 - PTOSYAYAYAYA
        return fieldDescription.trim();
    }


    private void cargaVariables() {

        log.info("qiere cc" + txtNumeroDocumento.getText());
        log.debug("qiere cc" + txtApellidoPaterno.getText().trim());

        //JCORTEZ valida DNI
        valor = FarmaUtility.validateNumber(this, txtNumeroDocumento, "Número de documento no válido.", true);
        if (valor) {
            try {
                //JCORTEZ 02.07.09 obtiene tarjeta existente en local
                DBFidelizacion.buscarTarjetasDni(VariablesFidelizacion.auxDataCli,
                                                 txtNumeroDocumento.getText().trim());
                log.debug("JCORTEZ TARJETA... " + VariablesFidelizacion.auxDataCli);
            } catch (Exception a) {
                log.error("", a);
            }
            VariablesFidelizacion.vDniCliente = txtNumeroDocumento.getText().trim();
            VariablesFidelizacion.vApePatCliente = txtApellidoPaterno.getText().trim();
            VariablesFidelizacion.vApeMatCliente = txtApellidoMaterno.getText().trim();
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
        log.info("VariablesFidelizacion.vDatosFinalTerceraValidacion:" +
                 VariablesFidelizacion.vDatosFinalTerceraValidacion);
        if (VariablesFidelizacion.vDatosFinalTerceraValidacion != null)
            if (VariablesFidelizacion.vDatosFinalTerceraValidacion.size() > 0) {
                log.info("Coloca los valores que se ingresaron en la tercera validacion");
                VariablesFidelizacion.vDniCliente =
                        (String)(VariablesFidelizacion.vDatosFinalTerceraValidacion.get(0));
                VariablesFidelizacion.vNomCliente =
                        (String)(VariablesFidelizacion.vDatosFinalTerceraValidacion.get(1));
                VariablesFidelizacion.vFecNacimiento =
                        (String)(VariablesFidelizacion.vDatosFinalTerceraValidacion.get(2));
            }


    }

    private void cargaDatosCliente() {
                                                                                        
        //ArrayList vListaDatosDNI = new ArrayList();
        
        String vDNI = "";
        String vApePat = ""; 
        String vApeMat = ""; 
        String vNom = ""; 
        String vFecNac = "";
        String vSexCli = ""; 
        String vDirCli = ""; 
        String vTlfCli = ""; 
        String vEmail = "";
        String vCelular = "";
        String vDepartamento = "";
        String vProvincia = "";
        String vDistrito = "";
        String vTipoDireccion = "";
        String vReferencia = "";
        String vTipoLugar = "";

        try {
            //DBFidelizacion.getDatosDNI(vListaDatosDNI, pCodTarjetaIngresado);            
            //log.debug("vListaDatosDNI " + vListaDatosDNI);
            
            //FALTA VALIDAR SI ES NULO CUANDO TENGA EL OBJETO
            /*
            vDNI = "123456789";//FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_DNI);
            vApePat = null;//FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_APE_PAT);
            vApeMat = null;//FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_APE_MAT);
            vNom = null;//FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_NOM_CLI);
            vFecNac = null;//FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_FEC_NAC);
            vSexCli = "M";//FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_SEX_CLI);
            vDirCli = null;//FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_DIR_CLI);
            vTlfCli = null;//FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_TLF_CLI);
            vEmail = null;//FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_EMAIL);
            vCelular = null;
            vDepartamento = null;
            vProvincia = null;
            vDistrito = null;
            vTipoDireccion = "01";
            vReferencia = null;
            vTipoLugar = "01";
*/
            
            vDNI = afiliado.getDni().trim();
            vApePat = afiliado.getApParterno().trim();
            vApeMat = afiliado.getApMarterno().trim();
            vNom = afiliado.getNombre().trim();
            vFecNac = afiliado.getFechaNacimiento().toString().trim();
            vSexCli = afiliado.getGenero().trim();
            vDirCli = afiliado.getDireccion().trim();
            vTlfCli = afiliado.getTelefono().trim();
            vEmail = afiliado.getEmail().trim();
            vCelular = afiliado.getCelular().trim();
            vDepartamento = afiliado.getDepartamento().trim();
            vProvincia = afiliado.getProvincia().trim();
            vDistrito = afiliado.getDistrito().trim();
            vTipoDireccion = afiliado.getTipoDireccion().trim();
            vReferencia = afiliado.getReferencias().trim();
            vTipoLugar = afiliado.getTipoLugar().trim();
            
            
            
            int pos = -3;
            if (true) { //Si el objeto sera nulo
                
                if (vDNI != null && !vDNI.equalsIgnoreCase("")) {
                    pos = buscaPosFila(ConstantsFidelizacion.DNI_CLIENTE);
                    if (pos >= 0) {
                        tblLista.setValueAt(vDNI, pos, 1);
                        //txtNumeroDocumento.setEditable(false);
                        txtNumeroDocumento.setText(vDNI);
                    };
                     
                } else {
                    pos = buscaPosFila(ConstantsFidelizacion.DNI_CLIENTE);
                    if (pos >= 0) {
                        tblLista.setValueAt("", pos, 1);
                        //txtNumeroDocumento.setEditable(false);
                        log.info("NUNCA DEBERIA ENTRAR ACA");
                    }
                    ;
                }

                if (vApePat != null && !vApePat.equalsIgnoreCase("")) {
                    pos = buscaPosFila(ConstantsFidelizacion.APEPAT_CLIENTE);
                    if (pos >= 0) {
                        tblLista.setValueAt(vApePat, pos, 1);
                        //txtApellidoPaterno.setEditable(false);
                        txtApellidoPaterno.setText(vApePat);
                    }
                }                

                if (vApeMat != null && !vApeMat.equalsIgnoreCase("")) {
                    pos = buscaPosFila(ConstantsFidelizacion.APEMAT_CLIENTE);
                    if (pos >= 0) {
                        tblLista.setValueAt(vApeMat, pos, 1);
                        //txtApellidoMaterno.setEditable(false);
                        txtApellidoMaterno.setText(vApeMat);
                    }
                }

                if (vNom != null && !vNom.equalsIgnoreCase("")) {
                    pos = buscaPosFila(ConstantsFidelizacion.NOMBRE_CLIENTE);
                    if (pos >= 0) {
                        tblLista.setValueAt(vNom, pos, 1);
                        //txtNomCliente.setEditable(false);
                        txtNomCliente.setText(vNom);
                    }
                }

                if (vEmail != null && !vEmail.equalsIgnoreCase("")) {
                    pos = buscaPosFila(ConstantsFidelizacion.EMAIL_CLIENTE);
                    if (pos >= 0) {
                        tblLista.setValueAt(vEmail, pos, 1);
                        //txtEmail.setEditable(false);
                        txtEmail.setText(vEmail);
                    }
                }

                if (vFecNac != null && !vFecNac.equalsIgnoreCase("")) {
                    pos = buscaPosFila(ConstantsFidelizacion.FECHA_NAC_CLIENTE);
                    if (pos >= 0) {
                        tblLista.setValueAt(vFecNac, pos, 1);
                        //txtFechNac.setEditable(false);
                        txtFechNac.setText(vFecNac);
                    }
                }

                if (vSexCli != null && !vSexCli.equalsIgnoreCase("")) {
                    pos = buscaPosFila(ConstantsFidelizacion.SEXO_CLIENTE);
                    if (pos >= 0) {                        
                        FarmaLoadCVL.setSelectedValueInComboBox(cmbSexo, "CMB_SEXO", vSexCli);
                        String texto = FarmaLoadCVL.getCVLDescription("CMB_SEXO", vSexCli);
                        tblLista.setValueAt(texto, pos, 1);
                        txtSexo.setEditable(false);                        
                    }
                }

                if (vDirCli != null && !vDirCli.equalsIgnoreCase("")) {
                    pos = buscaPosFila(ConstantsFidelizacion.DIREC_CLIENTE);
                    if (pos >= 0) {
                        tblLista.setValueAt(vDirCli, pos, 1);
                        //txtDireccion.setEditable(false);
                        txtDireccion.setText(vDirCli);
                    }
                }

                if (vTlfCli != null && !vTlfCli.equalsIgnoreCase("")) {
                    pos = buscaPosFila(ConstantsFidelizacion.TELEFONO_CLIENTE);
                    if (pos >= 0) {
                        tblLista.setValueAt(vTlfCli, pos, 1);
                        //txtTelefono.setEditable(false);
                        txtTelefono.setText(vTlfCli);
                    }

                }
                
                //INI ASOSA - 06/02/2015 - PTOSYAYAYAYA
                if (vCelular != null && !vCelular.equalsIgnoreCase("")) {
                    pos = buscaPosFila(ConstantsFidelizacion.CELULAR_CLIENTE);
                    if (pos >= 0) {
                        tblLista.setValueAt(vCelular, pos, 1);
                        //txtCelular.setEditable(false);                        
                        txtCelular.setText(vCelular);
                    }

                }
                
                if (vDepartamento != null && !vDepartamento.equalsIgnoreCase("")) {
                    pos = buscaPosFila(ConstantsFidelizacion.DEPARTAMENTO);
                    if (pos >= 0) {                        
                        FarmaLoadCVL.setSelectedValueInComboBox(cmbDepartamento, "CMB_DEPARTAMENTO", vDepartamento);
                        String texto = FarmaLoadCVL.getCVLDescription("CMB_DEPARTAMENTO", vDepartamento);
                        tblLista.setValueAt(texto, pos, 1);
                        txtDepartamento.setEditable(false);                        
                    }
                }
                
                if (vProvincia != null && !vProvincia.equalsIgnoreCase("")) {
                    pos = buscaPosFila(ConstantsFidelizacion.PROVINCIA);
                    if (pos >= 0) {
                        FarmaLoadCVL.setSelectedValueInComboBox(cmbProvincia, "CMB_PROVINCIA", vProvincia);
                        String texto = FarmaLoadCVL.getCVLDescription("CMB_PROVINCIA", vProvincia);
                        tblLista.setValueAt(texto, pos, 1);
                        txtProvincia.setEditable(false);                        
                    }
                }
                
                if (vDistrito != null && !vDistrito.equalsIgnoreCase("")) {
                    pos = buscaPosFila(ConstantsFidelizacion.DISTRITO);
                    if (pos >= 0) {
                        FarmaLoadCVL.setSelectedValueInComboBox(cmbDistrito, "CMB_DISTRITO", vDistrito);
                        String texto = FarmaLoadCVL.getCVLDescription("CMB_DISTRITO", vDistrito);
                        tblLista.setValueAt(texto, pos, 1);
                        txtDistrito.setEditable(false);                        
                    }
                }
                
                if (vTipoDireccion != null && !vTipoDireccion.equalsIgnoreCase("")) {
                    pos = buscaPosFila(ConstantsFidelizacion.TIPO_DIRECCION);
                    if (pos >= 0) {
                        FarmaLoadCVL.setSelectedValueInComboBox(cmbTipoDireccion, "CMB_TIPO_DIRECCION", vTipoDireccion);
                        String texto = FarmaLoadCVL.getCVLDescription("CMB_TIPO_DIRECCION", vTipoDireccion);
                        tblLista.setValueAt(texto, pos, 1);
                        txtTipoDireccion.setEditable(false);                        
                    }
                }
                
                if (vTipoLugar != null && !vTipoLugar.equalsIgnoreCase("")) {
                    pos = buscaPosFila(ConstantsFidelizacion.TIPO_LUGAR);
                    if (pos >= 0) {
                        FarmaLoadCVL.setSelectedValueInComboBox(cmbTipoLugar, "CMB_TIPO_LUGAR", vTipoLugar);
                        String texto = FarmaLoadCVL.getCVLDescription("CMB_TIPO_LUGAR", vTipoLugar);
                        tblLista.setValueAt(texto, pos, 1);
                        txtTipoLugar.setEditable(false);                        
                    }
                }
                
                if (vReferencia != null && !vReferencia.equalsIgnoreCase("")) {
                    pos = buscaPosFila(ConstantsFidelizacion.REFERENCIAS);
                    if (pos >= 0) {
                        tblLista.setValueAt(vTlfCli, pos, 1);
                        //txtReferencias.setEditable(false);
                        txtReferencias.setText(vReferencia);
                    }
                }
                //FIN ASOSA - 06/02/2015 - PTOSYAYAYAYA

            } else {
                pos = buscaPosFila(ConstantsFidelizacion.DNI_CLIENTE);
                if (pos >= 0) {
                    tblLista.setValueAt("", pos, 1);
                }
                ;
            }

        } catch (Exception e) {
            log.error("",e);
        }
    }


    private boolean buscaDatosCliente(String pDNI) {
        long tmpIni, tmpFin;

        tmpIni = System.currentTimeMillis();
        log.info("tmpIni:" + tmpIni);

        if (UtilityFidelizacion.validarDocIdentidad(pDNI.trim())) {
            log.info("Datos del cliente " + VariablesFidelizacion.vDataCliente);

            ArrayList vListaDatosDNI = new ArrayList();
            ArrayList vListaCampos = new ArrayList();
            String vDNI = "", vApePat = "", vApeMat = "", vNom = "", vFecNac = "", vSexCli = "", vDirCli =
                "", vTlfCli = "", vEma = "";
            
            String vCelular = "";
            String vDepartamento = "";
            String vProvincia = "";
            String vDistrito = "";
            String vTipoDireccion = "";
            String vReferencia = "";
            String vTipoLugar = "";

            try {
                //JCORTEZ 05.10.09 Se obtiene datos de PBL_DNI_RED o FID_TARJETA
                DBFidelizacion.getDatosExisteDNI(vListaDatosDNI, pDNI);
                int pExist = -1;
                log.info("vListaDatosDNI:" + vListaDatosDNI);
                if (vListaDatosDNI.size() > 0)
                    pExist = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, 0).trim().indexOf("$");
                
                log.info("pExist:" + pExist);
                if (pExist == -1) {


                    DBFidelizacion.getCamposTarjeta(vListaCampos);
                    log.debug("vListaCampos " + vListaCampos);
                    log.debug("vListaDatosDNI " + vListaDatosDNI);

                    vDNI = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_DNI);
                    vApePat = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_APE_PAT);
                    vApeMat = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_APE_MAT);
                    vNom = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_NOM_CLI);
                    vNom = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_NOM_CLI);
                    vFecNac = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_FEC_NAC);
                    vSexCli = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_SEX_CLI);
                    vDirCli = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_DIR_CLI);
                    vTlfCli = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_TLF_CLI);
                    vEma = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_EMAIL);
                    vCelular = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_CEL);
                    vDepartamento = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_DEPA);
                    vProvincia = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_PROV);
                    vDistrito = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_DIST);
                    vTipoDireccion = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_TIPDIR);
                    vReferencia = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_REFER);
                    vTipoLugar = FarmaUtility.getValueFieldArrayList(vListaDatosDNI, 0, COL_TIPLUG);
                    
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
                            //nvl(nvl('@'||reniec.nombre,'&'||cliente.nombre),'N')
                            String pIndEditable = vNom.trim().substring(0, 1);
                            pos = buscaPosFila(ConstantsFidelizacion.NOMBRE_CLIENTE);
                            if (pos >= 0) {
                                tblLista.setValueAt(vNom.substring(1), pos, 1);
                                if (pIndEditable.trim().equalsIgnoreCase("@"))
                                    //sera editable si es por RENIEC
                                    txtNomCliente.setEditable(true);
                                else if (pIndEditable.trim().equalsIgnoreCase("&"))
                                    //no se editara si es de cliente.
                                    txtNomCliente.setEditable(false);
                                else
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
                            pos = buscaPosFila(ConstantsFidelizacion.FECHA_NAC_CLIENTE);
                            if (pos >= 0) {
                                tblLista.setValueAt(vFecNac, pos, 1);
                                txtFechNac.setEditable(false);
                            }
                        }

                        if (!vSexCli.equalsIgnoreCase("N")) {
                            pos = buscaPosFila(ConstantsFidelizacion.SEXO_CLIENTE);
                            if (pos >= 0) {
                                FarmaLoadCVL.setSelectedValueInComboBox(cmbSexo, "CMB_SEXO", vSexCli);
                                String texto = FarmaLoadCVL.getCVLDescription("CMB_SEXO", vSexCli);
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
                        
                        //INI ASOSA - 20/04/2015 - PTOSYAYAYAYA
                        if (vCelular != null && !vCelular.equalsIgnoreCase("")) {
                            pos = buscaPosFila(ConstantsFidelizacion.CELULAR_CLIENTE);
                            if (pos >= 0) {
                                tblLista.setValueAt(vCelular, pos, 1);
                                //txtCelular.setEditable(false);                        
                                txtCelular.setText(vCelular);
                            }

                        }
                        
                        if (vDepartamento != null && !vDepartamento.equalsIgnoreCase("")) {
                            pos = buscaPosFila(ConstantsFidelizacion.DEPARTAMENTO);
                            if (pos >= 0) {                        
                                FarmaLoadCVL.setSelectedValueInComboBox(cmbDepartamento, "CMB_DEPARTAMENTO", vDepartamento);
                                String texto = FarmaLoadCVL.getCVLDescription("CMB_DEPARTAMENTO", vDepartamento);
                                tblLista.setValueAt(texto, pos, 1);
                                txtDepartamento.setEditable(false);                        
                            }
                        }
                        
                        if (vProvincia != null && !vProvincia.equalsIgnoreCase("")) {
                            pos = buscaPosFila(ConstantsFidelizacion.PROVINCIA);
                            if (pos >= 0) {
                                FarmaLoadCVL.setSelectedValueInComboBox(cmbProvincia, "CMB_PROVINCIA", vProvincia);
                                String texto = FarmaLoadCVL.getCVLDescription("CMB_PROVINCIA", vProvincia);
                                tblLista.setValueAt(texto, pos, 1);
                                txtProvincia.setEditable(false);                        
                            }
                        }
                        
                        if (vDistrito != null && !vDistrito.equalsIgnoreCase("")) {
                            pos = buscaPosFila(ConstantsFidelizacion.DISTRITO);
                            if (pos >= 0) {
                                FarmaLoadCVL.setSelectedValueInComboBox(cmbDistrito, "CMB_DISTRITO", vDistrito);
                                String texto = FarmaLoadCVL.getCVLDescription("CMB_DISTRITO", vDistrito);
                                tblLista.setValueAt(texto, pos, 1);
                                txtDistrito.setEditable(false);                        
                            }
                        }
                        
                        if (vTipoDireccion != null && !vTipoDireccion.equalsIgnoreCase("")) {
                            pos = buscaPosFila(ConstantsFidelizacion.TIPO_DIRECCION);
                            if (pos >= 0) {
                                FarmaLoadCVL.setSelectedValueInComboBox(cmbTipoDireccion, "CMB_TIPO_DIRECCION", vTipoDireccion);
                                String texto = FarmaLoadCVL.getCVLDescription("CMB_TIPO_DIRECCION", vTipoDireccion);
                                tblLista.setValueAt(texto, pos, 1);
                                txtTipoDireccion.setEditable(false);                        
                            }
                        }
                        
                        if (vTipoLugar != null && !vTipoLugar.equalsIgnoreCase("")) {
                            pos = buscaPosFila(ConstantsFidelizacion.TIPO_LUGAR);
                            if (pos >= 0) {
                                FarmaLoadCVL.setSelectedValueInComboBox(cmbTipoLugar, "CMB_TIPO_LUGAR", vTipoLugar);
                                String texto = FarmaLoadCVL.getCVLDescription("CMB_TIPO_LUGAR", vTipoLugar);
                                tblLista.setValueAt(texto, pos, 1);
                                txtTipoLugar.setEditable(false);                        
                            }
                        }
                        
                        if (vReferencia != null && !vReferencia.equalsIgnoreCase("")) {
                            pos = buscaPosFila(ConstantsFidelizacion.REFERENCIAS);
                            if (pos >= 0) {
                                tblLista.setValueAt(vTlfCli, pos, 1);
                                //txtReferencias.setEditable(false);
                                txtReferencias.setText(vReferencia);
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
                    log.info("tmpFin: " + tmpFin);
                    log.info("Dif Tiempo buscaDatosCliente() : " + (tmpFin - tmpIni) + " milisegundos");
                    return true;
                } else {

                    tmpFin = System.currentTimeMillis();
                    log.info("tmpFin: " + tmpFin);
                    log.info("Dif Tiempo buscaDatosCliente() : " + (tmpFin - tmpIni) + " milisegundos");

                    return false;
                }


            } catch (SQLException e) {
                log.error("", e);
            }
            tmpFin = System.currentTimeMillis();
            log.info("tmpFin: " + tmpFin);
            log.info("Dif Tiempo buscaDatosCliente() : " + (tmpFin - tmpIni) + " milisegundos");
            return false;
        } else {
            tmpFin = System.currentTimeMillis();
            log.info("tmpFin: " + tmpFin);
            log.info("Dif Tiempo buscaDatosCliente() : " + (tmpFin - tmpIni) + " milisegundos");
            return false;
        }
    }

    /**
     * @autor dubilluz
     * @since 04.10.2009
     * @return
     */
    public boolean validaDocumento() {
        boolean resultado = false;
        VariablesFidelizacion.vDniCliente_bk = txtNumeroDocumento.getText().trim();
        VariablesFidelizacion.vNomCliente_bk = txtNomCliente.getText().trim();
        VariablesFidelizacion.vFecNacimiento_bk = txtFechNac.getText().trim();
        boolean pValor = false;
        String pValidaReniec = "";
        try {

            pValidaReniec = DBFidelizacion.getIndValidaReniec().trim();
            log.info("pValidaReniec-->" + pValidaReniec);
            if (pValidaReniec.equalsIgnoreCase("S")) {
                log.debug("Valida Datos a RENIEC");
                pValor =
                        DBFidelizacion.isValidaDocumento(VariablesFidelizacion.vDniCliente_bk, VariablesFidelizacion.vFecNacimiento_bk);

                log.info("pValor-->" + pValor);
                if (pValor) { //El dni no esta en reniec entonces debe de validarse el documento
                    DlgFidelizacionValidaDocumento pDlgValida = new DlgFidelizacionValidaDocumento(myParentFrame, "", true);
                    pDlgValida.setVisible(true);
                    log.info("Cerrar pantalla.." + FarmaVariables.vAceptar);
                    if (FarmaVariables.vAceptar) {
                        resultado = true; //el documento se valido
                    } else {
                        //;
                        moveFocusTo(tblLista.getRowCount() - 1);
                        resultado = false;
                        //o dio escape o cancelo validacion
                    }
                } else
                    resultado = true; //el documento se valido
            } else {
                log.info("NO VALIDA Datos a RENIEC");
                resultado = true;
            }

        } catch (SQLException e) {
            resultado = true;
            log.error("",e);
        } finally {

        }

        return resultado;
    }

    public void funcionGeneralF11() {

        tblLista.changeSelection(0, 1, true, false);
        for (int i = 0; i < tblLista.getRowCount(); i++) {
            tblLista.changeSelection(i, 1, false, false);
        }

        if (validaDatosCompletos()) {
            log.info("Tiene datos Completos se fuerza a un F11");
            //funcionF11();
        }

    }

    private boolean validaDatosCompletos() {
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
                    if (!UtilityFidelizacion.validarDocIdentidad(vDato.trim())) {
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


        log.debug("Tiene Datos Completos :" + dataExists);

        return dataExists;
    }
    
    //INI ASOSA - 04/02/2015 - PTOSYAYAYAYA
    
    private void cmbDepartamento_itemStateChanged(ItemEvent e) {  
        int i = getIndice(ConstantsFidelizacion.PROVINCIA);
        if (i > -1) {
            cargarProvincias(i);
        }        
    }
       
    
    private void cmbDepartamento_keyPressed(KeyEvent e) {
        //if (e.getKeyCode() == KeyEvent.VK_ENTER)
            //FarmaUtility.moveFocus(cmbProvincia);
    }
    
    private void cmbTipoLugar_keyPressed(KeyEvent e) {
        String codigo = "";
        String descripcion = "";
        String nameCombo = "CMB_TIPO_LUGAR";
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            log.info("COMBO: " + nameCombo + " - index: " + cmbTipoLugar.getSelectedIndex());
            log.info("COMBO z: " + cmbTipoLugar.hashCode());
            
            codigo = FarmaLoadCVL.getCVLCode(nameCombo, cmbTipoLugar.getSelectedIndex());                            
            descripcion = FarmaLoadCVL.getCVLDescription(nameCombo, codigo);
        }            
    }
    
    
    private void cmbProvincia_itemStateChanged(ItemEvent e) {  
        int i = getIndice(ConstantsFidelizacion.DISTRITO);
        if (i > -1) {
            cargarDistrito(i);
        }
    }
    
    private void cmbProvincia_keyPressed(KeyEvent e) {
        //if (e.getKeyCode() == KeyEvent.VK_ENTER)
            //FarmaUtility.moveFocus(cmbDistrito);
    }
    
    //FIN ASOSA - 04/02/2015 - PTOSYAYAYAYA

    public Afiliado getAfiliado() {
        return afiliado;
    }

    public void setAfiliado(Afiliado afiliado) {
        this.afiliado = afiliado;
    }

    private void txtEmail_keyPressed(KeyEvent e) {
        /*
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            String text = txtEmail.getText().trim();
            int pos = text.indexOf("@");
            log.info("posicion: " + pos);            
            log.info("posicion: " + "asd".substring(0, 3-1));
            if (pos == -1) {
                if (text.equals("")) {
                    txtEmail.setText("");
                } else {
                    txtEmail.setText(text.substring(0, text.length()-1));
                }                
            }
        }
*/
    }

    private void txtEmail_keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            String text = txtEmail.getText().trim();
            int pos = text.indexOf("@");
            log.info("posicion: " + pos);            
            log.info("posicion: " + "asd".substring(0, 3-1));
            if (pos == -1) {
                if (text.equals("")) {
                    txtEmail.setText("");
                } else {
                    txtEmail.setText(text + "@");
                }                
            } else {
                txtEmail.setText(text.substring(0, text.length()));
            }
        }
    }
}
