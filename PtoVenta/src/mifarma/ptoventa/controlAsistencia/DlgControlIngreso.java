package mifarma.ptoventa.controlAsistencia;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.table.TableModel;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import com.gs.mifarma.componentes.FarmaHora;
import com.gs.mifarma.componentes.JConfirmDialog;

import mifarma.ptoventa.administracion.usuarios.reference.DBUsuarios;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.controlAsistencia.reference.ConstantsControlAsistencia;
import mifarma.ptoventa.controlAsistencia.reference.DBControlAsistencia;
import mifarma.ptoventa.controlAsistencia.reference.UtilityControlAsistencia;
import mifarma.ptoventa.controlAsistencia.reference.VariablesControlAsistencia;
import mifarma.ptoventa.inventario.reference.UtilityInventario;
import mifarma.ptoventa.puntos.reference.UtilityPuntos;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgControlIngreso.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * PAULO      11.10.2007   Creación<br>
 * <br>
 * @author Paulo Cesar Ameghino Rojas<br>
 * @version 1.0<br>
 *
 */
public class DlgControlIngreso extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgControlIngreso.class);

    private Frame myParentFrame;
    private FarmaTableModel tableModel;
    //Se modifico el orden al añadir las dos columnas de ingreso y salida 2
    //23.11.2007  dubilluz modificacion
    private final int COL_ORD = 6;
    private BorderLayout borderLayout1 = new BorderLayout();
    private GridLayout gridLayout1 = new GridLayout();
    private BorderLayout borderLayout2 = new BorderLayout();
    private BorderLayout borderLayout3 = new BorderLayout();
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JLabelOrange lblHora_T = new JLabelOrange();
    private JPanelWhite jPanelWhite2 = new JPanelWhite();
    private JLabelWhite lblIndFiscalizado = new JLabelWhite();
    private JButtonLabel btnTipo = new JButtonLabel();
    private JLabelWhite lblMensaje = new JLabelWhite();
    private JButtonLabel btnHistoria = new JButtonLabel();
    private JCheckBox chkVer=new  JCheckBox();
    private JButtonLabel btnDni = new JButtonLabel();
    private JComboBox cmbTipo = new JComboBox();
    private JLabelOrange lblPersonal = new JLabelOrange();
    private JTextFieldSanSerif txtDni = new JTextFieldSanSerif();
    private JScrollPane scrLista = new JScrollPane();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JLabelWhite lblItems = new JLabelWhite();
    private JLabelWhite lblItems_T = new JLabelWhite();
    private JTable tblLista = new JTable();
    FarmaHora lblHora;
    //CHUANES 24.02.2015
    //PERMITE SOLO EL USO DEL ESCANEO 
    boolean isLectoraLazer, isCodigoBarra;
    boolean isEnter = false;
    static long tmpT1, tmpT2, OldtmpT2;
    private boolean flag = true;
    double vTiempoMaquina = 200; // MILISEGUNDOS
    String indicador="";
    private long tiempoTeclaInicial ,tiempoTeclaFinal;
    private boolean isDniIngreso = true;
    
    private String tipSugerido = "";
    private String desTipSugerido = "";
    
    private ArrayList listColaboradores;

    public DlgControlIngreso() {
        this(null, "", false);
    }

    public DlgControlIngreso(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(791, 578));
        this.getContentPane().setLayout(gridLayout1);
        this.setTitle("Control de Ingreso");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        lblHora_T.setText("HORA MIFARMA:");
        lblHora_T.setBounds(new Rectangle(265, 5, 180, 45));
        lblHora_T.setFont(new Font("SansSerif", 1, 20));        
        jPanelWhite2.setBounds(new Rectangle(15, 55, 750, 105));
        jPanelWhite2.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        lblIndFiscalizado.setBounds(new Rectangle(175, 40, 175, 25));
        lblIndFiscalizado.setForeground(Color.black);
        lblIndFiscalizado.setToolTipText("null");
        btnTipo.setText("Tipo:");
        btnTipo.setBounds(new Rectangle(10, 50, 30, 20));
        btnTipo.setForeground(new Color(255, 130, 14));
        btnTipo.setMnemonic('T');
        btnTipo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnTipo_actionPerformed(e);
            }
        });
        
        btnHistoria.setText("Ver Marcaciones:");
        btnHistoria.setBounds(new Rectangle(600, 50, 100, 20));
        btnHistoria.setForeground(new Color(255, 130, 14));
        btnHistoria.setMnemonic('e');
        btnHistoria.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnHistoria_actionPerformed(e);
            }
        });
        chkVer.setBounds(new Rectangle(700, 50, 20, 20));
        chkVer.setForeground(new Color(255, 130, 14));
        chkVer.addMouseListener(new MouseListener(){


                @Override
                public void mouseClicked(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    chkVer_MousePressed(e);  

                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
        lblMensaje.setText("<html>PRESIONE <FONT COLOR=RED><FONT SIZE=+1>'E'</FONT></FONT> PARA ENTRADA O <FONT COLOR=BLUE><FONT SIZE=+1>'S'</FONT></FONT> PARA SALIDA. LUEGO <FONT SIZE=+1>F11 O ENTER </FONT> PARA GRABAR EL REGISTRO.</html>");
        lblMensaje.setBounds(new Rectangle(10, 75, 660, 25));
        lblMensaje.setForeground(Color.black);
        lblMensaje.setToolTipText("null");
        btnDni.setText("DNI:");
        btnDni.setBounds(new Rectangle(10, 15, 30, 20));
        btnDni.setForeground(new Color(255, 130, 14));
        btnDni.setMnemonic('D');
        btnDni.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDni_actionPerformed(e);
            }
        });
        cmbTipo.setBounds(new Rectangle(40, 50, 120, 20));
        cmbTipo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbTipo_keyPressed(e);
            }
        });
        cmbTipo.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                cmbTipo_focusGained(e);
            }

            public void focusLost(FocusEvent e) {
                cmbTipo_focusLost(e);
            }
        });
       // cmbTipo.setEnabled(false);
        lblPersonal.setBounds(new Rectangle(175, 5, 575, 30));
        lblPersonal.setFont(new Font("SansSerif", 1, 25));
        txtDni.setBounds(new Rectangle(40, 15, 85, 20));
        txtDni.setLengthText(8);
       
        txtDni.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtDni_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtDni_keyTyped(e);
            }
            public void keyReleased(KeyEvent e){
                txtDni_keyReleased(e);
            }
        });
        scrLista.setBounds(new Rectangle(15, 185, 750, 350));
        jPanelTitle1.setBounds(new Rectangle(15, 165, 750, 20));
        lblItems.setBounds(new Rectangle(630, 0, 70, 20));
        lblItems_T.setText("Items:");
        lblItems_T.setBounds(new Rectangle(575, 0, 50, 20));
        scrLista.getViewport();
        jPanelTitle1.add(lblItems, null);
        jPanelTitle1.add(lblItems_T, null);
        jPanelWhite2.add(lblIndFiscalizado, null);
        jPanelWhite2.add(btnTipo, null);
        jPanelWhite2.add(lblMensaje, null);
        jPanelWhite2.add(btnDni, null);
        jPanelWhite2.add(cmbTipo, null);
        jPanelWhite2.add(lblPersonal, null);
        jPanelWhite2.add(txtDni, null);
        jPanelWhite2.add(btnHistoria, null);
        jPanelWhite2.add(chkVer, null);
        
        jPanelWhite1.add(jPanelTitle1, null);
        scrLista.getViewport().add(tblLista, null);
        jPanelWhite1.add(scrLista, null);
        jPanelWhite1.add(jPanelWhite2, null);
        jPanelWhite1.add(lblHora_T, null);
        this.getContentPane().add(jPanelWhite1, null);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        lblMensaje.setVisible(false);
        lblPersonal.setText("<html><font size=-1><font color=black>Ingrese su DNI y presione ENTER.</font></font></html>");
        initCombo();
        initTable();
        
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initCombo() {
        String codigo[] = { "E", "S" }, valor[] = { "ENTRADA", "SALIDA" };
        FarmaLoadCVL.loadCVLfromArrays(cmbTipo, ConstantsControlAsistencia.CMB_TIPO_REG, codigo, valor, true);
    }

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsControlAsistencia.columnsListaRegistro, ConstantsControlAsistencia.defaultValuesListaRegistro,
                                    0);
        FarmaUtility.initSimpleList(tblLista, tableModel, ConstantsControlAsistencia.columnsListaRegistro);
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */


    private void btnTipo_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbTipo);
       //FarmaUtility.moveFocus(txtDni);
    }
    private void btnHistoria_actionPerformed(ActionEvent e ){
       
        seleccionChkVer();
    }
    private void chkVer_MousePressed(MouseEvent e){
        tableModel.clearTable(); 
        if(!chkVer.isSelected()){
           cargaLogin();
        }
    }
  

    private void btnDni_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtDni);
    }

    private void cmbTipo_focusGained(FocusEvent e) {
        lblMensaje.setVisible(true);
    }

    private void cmbTipo_focusLost(FocusEvent e) {
        lblMensaje.setVisible(false);
    }

    private void cmbTipo_keyPressed(KeyEvent e) {
        boolean flag = grabarRegisto();
    }

    private void txtDni_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtDni, e);
    }
    private void txtDni_keyReleased(KeyEvent e) {
       
    }

    private void txtDni_keyPressed(KeyEvent e) {
        boolean isContinua = true;        
        isCodigoBarra = true;
        isEnter = false;
        if (tiempoTeclaInicial == 0){
            tiempoTeclaInicial = System.currentTimeMillis();
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            tiempoTeclaFinal = System.currentTimeMillis();
            isEnter = true;
            
            if (!this.isDniIngreso()) {  //LECTOR DE BARRAS
                
                int maxTiempoLectora = UtilityPuntos.obtieneTiempoMaximoLectora();
                isLectoraLazer = false;
                
                log.info("Tiem 2 " + (tiempoTeclaInicial));
                log.info("Tiem 1 " + (tiempoTeclaFinal));
                log.info("Tiempo de ingreso y ENTER " + (tiempoTeclaFinal - tiempoTeclaInicial));

                if ((tiempoTeclaFinal - tiempoTeclaInicial) <= maxTiempoLectora && txtDni.getText().length() > 0) {
                    isLectoraLazer = true;
                    tiempoTeclaInicial = 0;
                    log.info("ES CODIGO DE BARRA");
                    isCodigoBarra = true;                    
                } else {
                    isContinua = false;
                    txtDni.setText("");
                    FarmaUtility.showMessage(this,
                                             "Por favor escanea la tarjeta con el lector de código de barras.\n" +
                            "No se permite el uso del teclado.", txtDni);
                }
            }
            if (isContinua) {
                if (isDebeUsarNuevaMarcacion()) {
                    txtDni.setText("");
                    FarmaUtility.showMessage(this, 
                                             "Usted debe usar la nueva marcación", 
                                             txtDni);                    
                } else {
                    if (buscaDni()) {
                        FarmaLoadCVL.setSelectedValueInComboBox(cmbTipo, ConstantsControlAsistencia.CMB_TIPO_REG,
                                                                VariablesControlAsistencia.vSugTipo);
                        FarmaUtility.moveFocus(cmbTipo);
                    } else {
                        txtDni.setText("");
                        FarmaUtility.moveFocus(txtDni);
                    }
                }
                
            }
            
        } else {
            chkKeyPressed(e);
        }
    }
    
    private boolean isDebeUsarNuevaMarcacion(){
        boolean flag = false;
        String dni = txtDni.getText().trim();
        int cont = 0;
        for (int c = 0; c < listColaboradores.size(); c++) {
            ArrayList list2 = (ArrayList)listColaboradores.get(c);
            String dniUsuario = (String)list2.get(2);
            if (dniUsuario.trim().equals(dni)) {
                cont = cont + 1;
            }
        }
        if (cont == 1) {
            flag = true;
        }
        String indNewCtrlAsist = UtilityInventario.obtenerParametroString(ConstantsControlAsistencia.ID_TAB_IND_ACTIVO_NEW_CTRL_ASIST);
        if (!indNewCtrlAsist.equals(FarmaConstants.INDICADOR_S)) {
            flag = false;
        }
        return flag;
    }
    
    
    
    //CHUANES 24.02.2015
    //PERMITE SOLO EL USO DEL ESCANEO 
    private boolean isTeclaPermitida(KeyEvent e) {
      
        return (isNumero(e.getKeyChar())||(e.getKeyCode() == KeyEvent.VK_ENTER)||
                (e.getKeyCode() == KeyEvent.VK_ESCAPE) || UtilityPtoVenta.verificaVK_F11(e)
            );
    }
    //CHUANES 24.02.2015
    //PERMITE SOLO EL USO DEL ESCANEO 
    private boolean isNumero(char ca) {
        int numero  = 0;
        try {
            numero = Integer.parseInt(ca + "");
            return true;
        } catch (NumberFormatException nfe) {
            //nfe.printStackTrace();
            return false;
        }
        
        
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);  
        mostrarMensajeTemperatura();
        mostrarMensajeCarnetSanidad();
        FarmaUtility.moveFocus(txtDni);
        //mostrarHora();
    }
    
    private void mostrarMensajeTemperatura() {
        if (!existeRegistroTemp()) {
            FarmaUtility.showMessage(this, "Recuerde que debe registrar la temperatura.",
                                     null);
        }
    }

    /* ************************************************************************ */
    /*                      METODOS AUXILIARES DE EVENTOS                       */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }      
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private boolean buscaDni() {
        boolean retorno = false;
        
        if (validaDni()) {
            String vNombre = "";
            String vInd_Fiscalizado = "";
            ArrayList vArrayAux = new ArrayList();

            try {
                DBControlAsistencia.getPersonal(vArrayAux, txtDni.getText().trim());

                if (vArrayAux.size() > 0) {
                    vArrayAux = (ArrayList)vArrayAux.get(0);
                    vNombre = vArrayAux.get(0).toString().trim();
                    VariablesControlAsistencia.vCodCia = vArrayAux.get(1).toString().trim();
                    VariablesControlAsistencia.vCodTrab = vArrayAux.get(2).toString().trim();
                    
                    log.info("Tipo Sugerido" + this.getTipSugerido());
                    VariablesControlAsistencia.vSugTipo = this.getTipSugerido();
                    
                    if (this.getTipSugerido().equalsIgnoreCase(ConstantsControlAsistencia.ID_ENTRADA)) {
                        this.setDesTipSugerido("Entrada");
                    } else {
                        this.setDesTipSugerido("Salida");
                    }                   
                    
                    vInd_Fiscalizado = vArrayAux.get(4).toString().trim();
                    retorno = true;
                    
                } else {
                    vNombre =
                            "<html>PERSONAL NO REGISTRADO. <font size=-1><font color=black>Presione ESC para corregir.</font></font></html>";
                    VariablesControlAsistencia.vCodCia = "";
                    VariablesControlAsistencia.vCodTrab = "";
                    VariablesControlAsistencia.vCodHor = "";
                    VariablesControlAsistencia.vSugTipo = "";
                    vInd_Fiscalizado = "";
                    retorno = false;
                }

                //retorno = true;
            } catch (SQLException s) {
                retorno = false;
                vNombre = "Error al consultar, intente de nuevo.";
                VariablesControlAsistencia.vCodCia = "";
                VariablesControlAsistencia.vCodTrab = "";
                VariablesControlAsistencia.vCodHor = "";
                VariablesControlAsistencia.vSugTipo = "";
                vInd_Fiscalizado = "";
                log.error("", s);
                FarmaUtility.showMessage(this, "Ha ocurrido un error al consulta el DNI.\n" +
                        s.getMessage(), txtDni);
            } finally {
                if (vInd_Fiscalizado.equalsIgnoreCase("N")) {
                    lblIndFiscalizado.setText("<html><font size=-1><font color=red>(NO FISCALIZADO)</font></font></html>");
                } else {
                    lblIndFiscalizado.setText("");
                }
                lblPersonal.setText(vNombre);
            }
        }
        return retorno;
    }

    private boolean validaDni() {
        boolean retorno = true;
        String vDni = txtDni.getText().trim();
        String indMarcEntrSalida = "";
        if (vDni.length() < 8) {
            retorno = false;
            FarmaUtility.showMessage(this, "Debe ingresar un DNI valido. ¡Verifique!", txtDni);
        } else {
            indMarcEntrSalida = UtilityControlAsistencia.getIndMarcEntrSalida(vDni);
            if (indMarcEntrSalida.equals(ConstantsControlAsistencia.IND_REGULARIZAR)) {
                indMarcEntrSalida = ConstantsControlAsistencia.ID_SALIDA;
            }            
            this.setTipSugerido(indMarcEntrSalida);
        }
        return retorno;
    }
    
    private boolean llamarVentanaRegularizacion(){
        boolean flag = true;
        String dni = txtDni.getText().trim();
        DlgMantenimientoMarcacion dlgMantenimientoMarcacion = new DlgMantenimientoMarcacion(myParentFrame,"",true,dni);
        dlgMantenimientoMarcacion.setVisible(true);
        if (!FarmaVariables.vAceptar) {
            flag = false;
        }
        return flag;
    }


    private void limpiarDatos() {
        txtDni.setText("");
        lblPersonal.setText("<html><font size=-1><font color=black>Ingrese su DNI y presione ENTER.</font></font></html>");
        lblIndFiscalizado.setText("");
        FarmaLoadCVL.setSelectedValueInComboBox(cmbTipo, ConstantsControlAsistencia.CMB_TIPO_REG,
                                                ConstantsControlAsistencia.ID_ENTRADA);
        VariablesControlAsistencia.vCodCia = "";
        VariablesControlAsistencia.vCodTrab = "";
        VariablesControlAsistencia.vCodHor = "";
        VariablesControlAsistencia.vSugTipo = "";
        FarmaUtility.moveFocus(txtDni);
    }


    private boolean grabarRegisto() {
        
        UtilityControlAsistencia.obtieneDescMsjeAsistencia();
        
        boolean bRetorno = false;
        String Dniaux = "";
        if (buscaDni()) {
            if (existeRegistro()) {

                String vTipo = VariablesControlAsistencia.vSugTipo;
                String vDescTipo = this.getDesTipSugerido();

                Dniaux = txtDni.getText().trim();
                String vAvisoTrabLocal = "";

                if (JConfirmDialog.rptaConfirmDialog(this, vAvisoTrabLocal + "\n" +
                        "¿Está seguro de registrar su " + vDescTipo + "?")) {

                    try {
                        
                        DBControlAsistencia.grabarRegistro(txtDni.getText().trim(), vTipo,
                                                           VariablesControlAsistencia.vCodCia,
                                                           VariablesControlAsistencia.vCodTrab,
                                                           VariablesControlAsistencia.vCodHor,
                                                           VariablesControlAsistencia.vIndicador); //chuanes 03.03.2015

                        FarmaUtility.aceptarTransaccion();
                        limpiarDatos();
                        flag = true;

                        bRetorno = true;

                    } catch (SQLException s) {
                        FarmaUtility.liberarTransaccion();
                        if (s.getErrorCode() == 20001) {
                            FarmaUtility.showMessage(this,
                                                     "Usted no puede registrarse, ya que no es un trabajador fiscalizado",
                                                     txtDni);
                            lblIndFiscalizado.setText("");
                        } else if (s.getErrorCode() == 20002) { //13.11.2007  dubilluz  añadido
                            FarmaUtility.showMessage(this,
                                                     "No puede registrar su entrada porque ya existe un registro de entrada para el día de hoy.",
                                                     txtDni);
                        } else {
                            log.error("", s);
                            FarmaUtility.showMessage(this, "Ha ocurrido un error al grabar el registro.\n" +
                                    s.getMessage(), txtDni);
                        }
                    }
                } else {
                    limpiarDatos();
                    bRetorno = false;
                }
                
            }
        } else {
            FarmaUtility.moveFocus(txtDni);
        }

        return bRetorno;
    }

    /**
     * Se ingreso temperatura
     * @author JCORTEZ
     * @since 12.02.2009
     * */
    private boolean existeRegistroTemp() {

        boolean valor = true;
        String result = "";
        try {
            result = DBControlAsistencia.verificaIngrTemperatura();

            if (result.equalsIgnoreCase("N")) {
                valor = false;
            }
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ha ocurrido un error al validar registro de temperatura .\n" +
                    e.getMessage(), cmbTipo);
        }
        return valor;
    }


    private boolean existeRegistro() {

        boolean retorno = true;

        ArrayList vArrayAux = new ArrayList();
        ArrayList vArrayAux1 = new ArrayList();
        try {
            DBControlAsistencia.getRegistro(vArrayAux, txtDni.getText().trim());
            DBControlAsistencia.validaSalida(vArrayAux1, txtDni.getText().trim());

            if (vArrayAux.size() > 0) {
                FarmaUtility.showMessage(this, "Usted ya se ha registrado el dia de hoy", txtDni);
                retorno = false;
            } 
        } catch (SQLException s) {
            log.error("", s);
            FarmaUtility.showMessage(this, "Ha ocurrido un error al consulta el DNI.\n" +
                    s.getMessage(), txtDni);
        }
        return retorno;
    }

    private void mostrarHora() {
        lblHora = new FarmaHora();
        lblHora.setText("");
        lblHora.setBounds(new Rectangle(500, 10, 220, 35));
        lblHora.setFont(new Font("SansSerif", 1, 20));
        jPanelWhite1.add(lblHora, null);
        lblHora.start();
    }


    private void cargaListaRegistro() {
        try {
            tableModel.clearTable(); 
            DBControlAsistencia.cargaListaRegistros(tableModel);
            FarmaUtility.ordenar(tblLista, tableModel, COL_ORD, FarmaConstants.ORDEN_DESCENDENTE);
            lblItems.setText(tblLista.getRowCount() + "");
        } catch (SQLException s) {
            log.error("", s);
            FarmaUtility.showMessage(this, "Ha ocurrido un error al listar los registros.\n" +
                    s.getMessage(), txtDni);
        }
    }
  //FUNCIONALIDAD DEL CHECKBOX
    private void seleccionChkVer(){
        tableModel.clearTable();
        if(chkVer.isSelected()){
            chkVer.setSelected(false); 
        }else{
            chkVer.setSelected(true);
            cargaLogin();
        } 
    }

    //LISTA EL REGISTRO SOLO DEL USUARIO QUE MARCO SU HORARIO
    //CHUANES 20/07/2015
    private void cargaListaRegistroDni(String pDni){
        try{
            tableModel.clearTable();
            chkVer.setSelected(false);
            DBControlAsistencia.cargaListaRegistrosDni(tableModel, pDni);
            lblItems.setText(tblLista.getRowCount() + "");
            
        }catch(SQLException e){
            log.error("ERROR AL CARGAR REGISTRO X DNI"+e.getMessage());
            FarmaUtility.showMessage(this, "Ha ocurrido un error al listar los registros.\n" +
                    e.getMessage(), txtDni);
        }
    }

    private void cargaLogin(){
        DlgLogin dlgLogin = new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
        dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
        dlgLogin.setVisible(true);
        
        if (FarmaVariables.vAceptar) {
            FarmaVariables.dlgLogin = dlgLogin;
            FarmaVariables.vAceptar = false;
            cargaListaRegistro();
        }else{
            chkVer.setSelected(false);
        }
        
    }
   
    private void mostrarMensajeCarnetSanidad() {
        
        String vAvisoTrabLocal = "";
        
        listColaboradores = UtilityControlAsistencia.listarColaboradoresLocal();
        
        for (int c = 0; c < listColaboradores.size(); c++) {
            ArrayList list2 = (ArrayList)listColaboradores.get(c);
            String secUsu = (String)list2.get(0);
            String loginUsu = (String)list2.get(1);
            
            if (validarCarnetColaboradorLocal(secUsu)) {
                if (VariablesControlAsistencia.vExisteCarne.trim().equalsIgnoreCase("S")) {
                    if (VariablesControlAsistencia.vFechaVencCarne.trim().equals("NV")){ //No ha vencido el carne o no está proximo de vencer
                                vAvisoTrabLocal = vAvisoTrabLocal + "";


                    }else {

                        if (VariablesControlAsistencia.vMensajeTiempoVencimiento.trim().equalsIgnoreCase("V")) {
                            vAvisoTrabLocal = vAvisoTrabLocal +
                                    loginUsu + " - Carnet de Sanidad caducó el: " + VariablesControlAsistencia.vFechaVencCarne + "\n";

                        } else {
                            vAvisoTrabLocal = vAvisoTrabLocal +
                                    loginUsu + " - Carnet de Sanidad caduca el: " + VariablesControlAsistencia.vFechaVencCarne + "\n";
                        }
                    }
                    VariablesControlAsistencia.vEnviaAlerta = "N";
                } else {
                    vAvisoTrabLocal = vAvisoTrabLocal +
                            loginUsu + " - no tiene Carnet de Sanidad registrado en el Sistema\n";

                    VariablesControlAsistencia.vEnviaAlerta = "S";
                }
                
                if (VariablesControlAsistencia.vEnviaAlerta.equalsIgnoreCase("S")) {

                    if (enviarAlertaAlMarcarIngresoLocal(secUsu)) {
                        log.info("Alerta Satisfactoria");
                    } else
                        log.info("No se envió la alerta ");
                }
            }   
        }   
        if (!vAvisoTrabLocal.equals("")) {
            FarmaUtility.showMessage(this, 
                                     vAvisoTrabLocal, 
                                     txtDni);
        }        
    }

    private void cargarFecha() {
        try {
            String FechaInicio = FarmaSearch.getFechaHoraBD(1);
            //lblFechaSistema.setText(FechaInicio);
        } catch (SQLException sql) {
            log.error("", sql);
        }
    }

    private boolean ValidaRolQF(String Dni) {

        boolean valor = true;
        String result = "", SecUsu = "";
        try {
            SecUsu = DBControlAsistencia.getSecUsuLocal(Dni);

            result = DBControlAsistencia.verificaRolUsuario(SecUsu, ConstantsControlAsistencia.ROL_QF_ADMINLOCAL);
            if (result.equalsIgnoreCase("N"))
                valor = false;
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ha ocurrido un error al validar el rol de usuario .\n" +
                    e.getMessage(), txtDni);
        }
        return valor;
    }


    private boolean validarExistenciaCarnet(String secUsu) {
        boolean valor = true;
        String result = "";

        try {
            result = DBUsuarios.verificaExistenciaCarne(secUsu);
            //No tiene Carné Registrado
            if (result.trim().equalsIgnoreCase("0")) {
                valor = false;
            }

        }

        catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ha ocurrido un error al validar carne de usuario .\n" +
                    e.getMessage(), txtDni);
        }
        return valor;
    }

    private boolean validarCarnetColaboradorLocal(String secUsu) {

        boolean valor = true;
        String result = "";
        try {
            result =
                DBControlAsistencia.ValidaRolTrabLocal(secUsu, 
                                                    ConstantsControlAsistencia.ROL_CAJERO, 
                                                    ConstantsControlAsistencia.ROL_VENDEDOR,
                                                    ConstantsControlAsistencia.ROL_QF_ADMINLOCAL);
            if (result.equalsIgnoreCase("N")) {
                valor = false;
                VariablesControlAsistencia.vFechaVencCarne = "";
            }

            else //Para obtener fecha de vencimiento ,validar que tenga carné
            {
                if (validarExistenciaCarnet(secUsu)) {

                    VariablesControlAsistencia.vExisteCarne = "S";

                    String resultadoConsulta = "", resultadoMensaje = "";
                    resultadoConsulta = DBUsuarios.verificaFechaVenUsuarioCarneControlIngreso(secUsu);
                    resultadoMensaje = DBUsuarios.verificaFechaVenUsuarioCarne(secUsu);
                    VariablesControlAsistencia.vFechaVencCarne = resultadoConsulta;
                    VariablesControlAsistencia.vMensajeTiempoVencimiento = resultadoMensaje;

                } else {
                    VariablesControlAsistencia.vExisteCarne = "N";
                }
            }
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ha ocurrido un error al validar el rol de usuario .\n" +
                    e.getMessage(), txtDni);
        }
        return valor;
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private boolean enviarAlertaAlMarcarIngresoLocal(String secUsu) {
        boolean retorno = false;
        try {
            DBUsuarios.enviaAlertaCarneUsuarioMarcaIngreso(secUsu);
            retorno = true;

        }
        catch (SQLException sql) {
            log.error("", sql);
            retorno = false;
        }
        return retorno;
    }


    /**
     * Se genera  N° cupones de X Campaña cuando el usuario marque ingreso
     * @AUTHOR  JCORTEZ
     * @SINCE   17.08.09
     * */
    private boolean generarCupones(String tipo, String Dni) {

        boolean valor = false;
        String flag = "";

        if (tipo.equalsIgnoreCase(ConstantsControlAsistencia.ID_ENTRADA)) {
            try {

                DBControlAsistencia.generaCuponesRegalo(Dni);
                FarmaUtility.aceptarTransaccion();
                valor = true;
            } catch (SQLException e) {
                log.error("", e);
                FarmaUtility.showMessage(this, "Ha ocurrido un error al genera cupones regalo .\n" +
                        e.getMessage(), Dni);
            }
        }
        return valor;
    }

    /**
     * Se obtiene cupones generados
     * @AUTHOR  JCORTEZ
     * @SINCE   17.08.09
     * */
    private void obtieneCuponesRegalo(ArrayList cuponRegalos, String Dni) {
        try {

            DBControlAsistencia.obtieneCuponesRegalo(cuponRegalos, Dni);
        } catch (SQLException e) {
            if (e.getErrorCode() == 20099) {
                cuponRegalos.clear();

            } else {
                log.error("", e);
                FarmaUtility.showMessage(this, "Ha ocurrido un error al obtener cupones regalo .\n" +
                        e.getMessage(), Dni);
            }
        }
    }


    /***
     * valida impresion de cupones
     * @AUTHOR JCORTEZ
     * @SINCE 17.08.09
     * */
    private void generarCuponesRegalo(String vTipo, String Dniaux) {


        ArrayList cuponesRegalo = new ArrayList();
        String dni = Dniaux;
        String codCupon = "";
        if (existCuponesRegalos(Dniaux)) { //VERIFICA
            if (generarCupones(vTipo, dni)) { //GENERA
                obtieneCuponesRegalo(cuponesRegalo, dni); //OBTIENE

                if (cuponesRegalo.size() > 0) {
                    FarmaUtility.showMessage(this, "Se van a generar cupones de regalo.\nNo olvide recogerlos.",
                                             cmbTipo);
                    for (int i = 0; i < cuponesRegalo.size(); i++) {
                        codCupon = ((String)((ArrayList)cuponesRegalo.get(i)).get(0)).trim();
                        UtilityCaja.imprimeCuponRegalo(this, codCupon, dni); //IMPRIME
                    }
                }
            } else {

            }
        } else {

        }
    }

    /***
     * Valida primera generacion de cupones
     * @AUTHOR JCORTEZ
     * @SINCE 18.08.09
     * */
    private boolean existCuponesRegalos(String Dniaux) {
        String exist = "";
        boolean valor = false;
        try {
            exist = DBControlAsistencia.existCuponRegalo(Dniaux);
            if (exist.equalsIgnoreCase("N"))
                valor = true;
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ha ocurrido un error al obtener cupones regalo .\n" +
                    e.getMessage(), txtDni);
        }
        return valor;

    }
    
    public String indicadorIngreso(){
       
       String indicador="";
        try {
            indicador = DBControlAsistencia.indicadorIngreso().trim();
        } catch (SQLException e) {
            FarmaUtility.showMessage(this, "Error al verificar la forma de marcación.\n" +
                    e.getMessage(), txtDni); 
        }
        return indicador; 
    }

    public String getIndicador() {
        return indicador;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }

    public boolean isDniIngreso() {
        return isDniIngreso;
    }

    public void setDniIngreso(boolean dniIngreso) {
        isDniIngreso = dniIngreso;
    }

    public String getTipSugerido() {
        return tipSugerido;
    }

    public void setTipSugerido(String tipSugerido) {
        this.tipSugerido = tipSugerido;
    }

    public String getDesTipSugerido() {
        return desTipSugerido;
    }

    public void setDesTipSugerido(String desTipSugerido) {
        this.desTipSugerido = desTipSugerido;
    }
}
