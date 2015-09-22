package mifarma.common;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
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
import java.awt.event.InputEvent;
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
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
    private String CMB_TIPO_REG = "TIPO_REGISTRO";
    //CHUANES 24.02.2015
    //PERMITE SOLO EL USO DEL ESCANEO 
    boolean isLectoraLazer = false;
    boolean isEnter = false;
    static long  tmpT1,tmpT2,OldtmpT2;
    private boolean flag = true;
    double vTiempoMaquina = 200; // MILISEGUNDOS
    String indicador="";
    
    private FarmaColumnData columnsListaRegistro[] =
    { new FarmaColumnData("DNI", 70, JLabel.LEFT), new FarmaColumnData("Nombre", /*450*/280, JLabel.LEFT),
      new FarmaColumnData("Hora Ingreso", 100, JLabel.LEFT), new FarmaColumnData("Hora Salida", 100, JLabel.LEFT),
      new FarmaColumnData("Hora Ingreso(2)", 100, JLabel.LEFT),
      new FarmaColumnData("Hora Salida (2)", 100, JLabel.LEFT), new FarmaColumnData("orden", 0, JLabel.LEFT) };

    private Object[] defaultValuesListaRegistro = { " ", " ", " ", " ", " ", " ", " " };
    
 


    private String vCodCia = "";
    private String vCodTrab = "";
    private String vCodHor = "";
    private String vSugTipo = "";
    private String vIndicador="";//chuanes 11.03.2015
    private boolean  vflag=false;//chuanes 12.03.2015
    
    private String TIPO_ENTRADA = "01";
    private String TIPO_SALIDA = "02";
    
    private String vFechaVencCarne = "";
    private String vSecUsu = "";
    private String vExisteCarne = "N";
    private String vMensajeTiempoVencimiento = "";
    private String vEnviaAlerta = "N";
    
    public String MENSAJE_ROL = "Usted no cuenta con el rol adecuado.";
    public String ROL_QF_ADMINLOCAL = "011";
    public String ROL_CAJERO = "009";
    public String ROL_VENDEDOR = "010";

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
        String codigo[] = { "01", "02" }, valor[] = { "ENTRADA", "SALIDA" };
        FarmaLoadCVL.loadCVLfromArrays(cmbTipo, CMB_TIPO_REG, codigo, valor, true);
    }

    private void initTable() {
        tableModel =
                new FarmaTableModel(columnsListaRegistro, defaultValuesListaRegistro,
                                    0);
        FarmaUtility.initSimpleList(tblLista, tableModel, columnsListaRegistro);
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */


    private void btnTipo_actionPerformed(ActionEvent e) {
      FarmaUtility.moveFocus(cmbTipo);
      //  FarmaUtility.moveFocus(txtDni);
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
        chkKeyPressed(e);
    }
    private void txtDni_keyReleased(KeyEvent e) {
        
        //CHUANES 24.02.2015
        //PERMITE SOLO EL USO DEL ESCANEO 
        
        this.setIndicador(indicadorIngreso());
        if(this.getIndicador().toUpperCase().equalsIgnoreCase("S")){
        if (isTeclaPermitida(e)) {
            if (isLectoraLazer && isEnter && (OldtmpT2 != tmpT2)) {
                txtDni.selectAll();
                OldtmpT2 = tmpT2;
              
            }
        } else if ((e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
            txtDni.setText("");
          
            e.consume();
            limpiarDatos();
            FarmaUtility.showMessage(this, "Por favor escanear el numero de Dni", txtDni);
        } else
            txtDni.setText(""); 
            e.consume();
        }
            
    }

    private void txtDni_keyTyped(KeyEvent e) {
        
        //CHUANES 24.02.2015
        //PERMITE SOLO EL USO DEL ESCANEO 
        this.setIndicador(indicadorIngreso());
        if(this.getIndicador().toUpperCase().equalsIgnoreCase("S")){
        if(!(isTeclaPermitida(e))) {
            if((e.getKeyCode() == KeyEvent.VK_DELETE||e.getKeyCode() == KeyEvent.VK_BACK_SPACE)){
                    txtDni.setText("");
                    e.consume();
                limpiarDatos();
                FarmaUtility.showMessage(this, "Por favor escanear el numero de Dni", txtDni);
            }
            else

            e.consume();
        }
        }
    }

    private void txtDni_keyPressed(KeyEvent e) {
            
        //CHUANES 24.02.2015
        //PERMITE SOLO EL USO DEL ESCANEO 
        vflag=false;
        this.setIndicador(indicadorIngreso());
        if(this.getIndicador().toUpperCase().equalsIgnoreCase("S")){
            if(isTeclaPermitida(e)){
                if (isLectoraLazer) {
                    txtDni.setText("");
                    isLectoraLazer = false;
                }
                isEnter = false;
                isLectoraLazer = false;
                if (txtDni.getText().length() == 0)
                    tmpT1 = System.currentTimeMillis();
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                isLectoraLazer = false;
                tmpT2 = System.currentTimeMillis();
                log.info("Tiem 2 " + (tmpT2));
                log.info("Tiem 1 " + (tmpT1));
                log.info("Tiempo de ingreso y ENTER " + (tmpT2 - tmpT1));
                log.info("longitud " + (txtDni.getText().length() ));
                if ((tmpT2 - tmpT1) <= vTiempoMaquina && txtDni.getText().length() > 0 ) {
                   
                    isLectoraLazer = true;
                    isEnter = true;
                    tmpT1 = 0;
                 vIndicador="S";
                 vflag=true;
                if (buscaDni()) {
                    determinaTipoRegistro();
                  FarmaUtility.moveFocus(cmbTipo);
                   // FarmaUtility.moveFocus(txtDni);
                    //chkKeyPressed(e);
                } else {
                    FarmaUtility.moveFocus(txtDni);
                }
                
                }else {
                        isLectoraLazer = false;
                        txtDni.setText("");
                        tmpT1 = 0;
                        vflag=false;
                        limpiarDatos();
                        FarmaUtility.showMessage(this, 
                                "Por favor escanear la tarjeta con el lector de código de barras.\n" +
                                "No se permite el uso del teclado en esta función.", txtDni);
                    }
            } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                limpiarDatos();
                cerrarVentana(false);
            }
            
            } else if ((e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE )) {
                txtDni.setText("");
                e.consume();
                limpiarDatos();
                FarmaUtility.showMessage(this, "Por favor Escanear el numero de Dni", txtDni);
            }else{
                limpiarDatos();
                FarmaUtility.showMessage(this, "No permitido el uso de teclado", txtDni); 
                txtDni.setText(""); 

               
            }
        }else{
            if (isLectoraLazer) {
            txtDni.setText("");
            isLectoraLazer = false;
            }
            isEnter = false;
            isLectoraLazer = false;
            if (txtDni.getText().length() == 0)
            tmpT1 = System.currentTimeMillis();
            
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                isLectoraLazer = false;
                tmpT2 = System.currentTimeMillis();
                log.info("Tiem 2 " + (tmpT2));
                log.info("Tiem 1 " + (tmpT1));
                log.info("Tiempo de ingreso y ENTER " + (tmpT2 - tmpT1));
                log.info("longitud " + (txtDni.getText().length() ));
                if ((tmpT2 - tmpT1) <= vTiempoMaquina && txtDni.getText().length() > 0 ) {
                   vIndicador="S";
                }else{
                    vIndicador="N";
                }
                
                isLectoraLazer = true;
                isEnter = true;
                tmpT1 = 0;  
            if (buscaDni()) {
            determinaTipoRegistro();
            FarmaUtility.moveFocus(cmbTipo);
           // FarmaUtility.moveFocus(txtDni);
            //chkKeyPressed(e);
            } else {
            FarmaUtility.moveFocus(txtDni);
            }
            } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            limpiarDatos();
            cerrarVentana(false);
            }    
        }
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(txtDni);
        FarmaUtility.centrarVentana(this);

        //mostrarHora();
       // cargaListaRegistro();CHUANES/20/07/2015
        //cargarFecha();
    }

    /* ************************************************************************ */
    /*                      METODOS AUXILIARES DE EVENTOS                       */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if ((verificaVK_F11(e)) || (e.getKeyCode() == KeyEvent.VK_ENTER)) {
                this.setIndicador(indicadorIngreso());
                if(this.getIndicador().toUpperCase().equalsIgnoreCase("S")){
                    if(vflag){
                    grabarRegisto();
                    }else{
                    txtDni.setText("");
                    limpiarDatos();    
                    FarmaUtility.showMessage(this, 
                    "Por favor escanea la tarjeta con el lector de código de barras.\n" +
                    "No se permite el uso del teclado en esta función.", txtDni);
                    }    
                    }
                    
                    else{
                    grabarRegisto();
                }
                
                FarmaUtility.moveFocus(txtDni);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
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
            ArrayList vArrayInd = new ArrayList();

            try {
                getPersonal(vArrayAux, txtDni.getText().trim());

                if (vArrayAux.size() > 0) {
                    vArrayAux = (ArrayList)vArrayAux.get(0);
                    vNombre = vArrayAux.get(0).toString().trim();
                    vCodCia = vArrayAux.get(1).toString().trim();
                    vCodTrab = vArrayAux.get(2).toString().trim();
                    //VariablesControlIngreso.vCodHor = vArrayAux.get(3).toString().trim();
                    vSugTipo = vArrayAux.get(3).toString().trim();
                    vInd_Fiscalizado = vArrayAux.get(4).toString().trim();
                    retorno = true;
                } else {
                    vNombre =
                            "<html>PERSONAL NO REGISTRADO. <font size=-1><font color=black>Presione ESC para corregir.</font></font></html>";
                    vCodCia = "";
                    vCodTrab = "";
                    vCodHor = "";
                    vSugTipo = "";
                    vInd_Fiscalizado = "";
                    retorno = false;
                }

                //retorno = true;
            } catch (SQLException s) {
                retorno = false;
                vNombre = "Error al consultar, intente de nuevo.";
                vCodCia = "";
                vCodTrab = "";
                vCodHor = "";
                vSugTipo = "";
                vInd_Fiscalizado = "";
                log.error("", s);
                FarmaUtility.showMessage(this, "Ha ocurrido un error al consulta el DNI.\n" +
                        s.getMessage(), txtDni);
            } finally {
                lblPersonal.setText(vNombre);
                if (vInd_Fiscalizado.equalsIgnoreCase("N")) {
                    lblIndFiscalizado.setText("<html><font size=-1><font color=red>(NO FISCALIZADO)</font></font></html>");
                } else {
                    lblIndFiscalizado.setText("");
                }
            }
        }
        return retorno;
    }

    private boolean validaDni() {
        boolean retorno = true;
        String vDni = txtDni.getText().trim();
        if (vDni.length() < 8) {
            retorno = false;
            FarmaUtility.showMessage(this, "Debe ingresar un DNI valido. ¡Verifique!", txtDni);
        }
        return retorno;
    }

    private void determinaTipoRegistro() {
        if (vSugTipo.equals("")) {
            int hora = Integer.parseInt(lblHora.getText().substring(0, 2));

            if (hora < 12) {
                vSugTipo = TIPO_ENTRADA;
            } else {
                vSugTipo = TIPO_SALIDA;
            }
        }

        FarmaLoadCVL.setSelectedValueInComboBox(cmbTipo, CMB_TIPO_REG,
                                                vSugTipo);
    }

    private void limpiarDatos() {
        txtDni.setText("");
        //lblPersonal.setText("");
        lblPersonal.setText("<html><font size=-1><font color=black>Ingrese su DNI y presione ENTER.</font></font></html>");
        lblIndFiscalizado.setText("");
        FarmaLoadCVL.setSelectedValueInComboBox(cmbTipo, CMB_TIPO_REG,
                                                TIPO_ENTRADA);
        vCodCia = "";
        vCodTrab = "";
        vCodHor = "";
        vSugTipo = "";
        FarmaUtility.moveFocus(txtDni);
    }


    private void grabarRegisto() {
        String Dniaux = "";
        if (buscaDni()) {
            if (existeRegistro()) {
                String vTipo =
                    FarmaLoadCVL.getCVLCode(CMB_TIPO_REG, cmbTipo.getSelectedIndex());
                String vDescTipo = FarmaLoadCVL.getCVLDescription(CMB_TIPO_REG, vTipo);


                flag = true;
                
                if (flag) { //Verifica si es rol Administrador Local

                    Dniaux = txtDni.getText().trim();


                    String vAvisoTrabLocal = "";

                    //PTOVENTA_INGR_PERS pakete funcion VERIFICA_ROL_TRAB_LOCAL
                    //SI EL USUARIO  TIENE ROL DE CAJERO,VENDEDOR,QF DEBE APARECER EL MENSAJE

                    //---------------------------------------@Agregado por Asolis-------------------------------------------------------------------
                    if (ValidaRolTrabLocal()) {

                        if (vExisteCarne.trim().equalsIgnoreCase("S")) {
                            if (vFechaVencCarne.trim().equals("NV")) //No ha vencido el carne o no está proximo de vencer
                                vAvisoTrabLocal = "";


                            else {

                                if (vMensajeTiempoVencimiento.trim().equalsIgnoreCase("V"))
                                    vAvisoTrabLocal =
                                            "Le recordamos que su Carné de Sanidad caducó el :" + vFechaVencCarne;

                                else
                                    vAvisoTrabLocal =
                                            "Le recordamos que su Carné de Sanidad caduca el :" + vFechaVencCarne;
                            }


                        }

                        else {
                            vAvisoTrabLocal =
                                    "Le informamos que Usted no tiene Carné de Sanidad registrado en el Sistema";

                            vEnviaAlerta = "S";


                        }


                    }

                    else {
                        log.info("No tiene Rol de Administrador o Cajero o Vendedor");


                    }
                    //CHUANES 
                    //04/08/2015
                   if(isPendienteMarcarSalida()){
                         vAvisoTrabLocal="Existe Marcaciones pendientes de Salida por Registrar";
                                                 
                                                
                    
                    }
                    //CHUANES 
                    //04/08/2015
                   if(isPendienteJustInasistencia()){
                       vAvisoTrabLocal=" Existe Justificaciones de Inasistencias pendientes por Registar";
                      
                   }

                    //----------------------------------------------------------------------------------------------------------
                    if (JConfirmDialog.rptaConfirmDialog(this, vAvisoTrabLocal + "\n" +
                            "¿Está seguro de registrar su " + vDescTipo + "?")) {


                        try {
                            grabarRegistro(txtDni.getText().trim(), vTipo,
                                                            vCodCia,
                                                            vCodTrab,
                                                            vCodHor,vIndicador);//CHUANES 04.03.2015
                            //DBControlIngreso.validarIngreso(txtDni.getText().trim());
                            //DBControlIngreso.ActualizarDatosIngreso(txtDni.getText().trim());
                            FarmaUtility.aceptarTransaccion();
                            
                            //cargaListaRegistro();CHUANES 20/07/2015
                            cargaListaRegistroDni(txtDni.getText().trim());
                            if (tblLista.getRowCount() > 0) {
                                FarmaUtility.findTextInJTable(tblLista, txtDni.getText().trim(), 0, 0);
                            }


                            //-------------------------------@Agregado por Asolis-------------------------------------
                            //--------------@Envia Alerta al Registrar Ingreso si no tiene Carné Registrado
                            if (vTipo.equalsIgnoreCase(TIPO_ENTRADA)) {
                                //Envia Alerta.

                                if (vEnviaAlerta.equalsIgnoreCase("S")) {


                                    if (EnviaAlertaAlMarcarIngresoLocal()) {


                                        log.info("Alerta Satisfactoria");
                                    } else
                                        log.info("No se envió la alerta ");


                                }
                            }
                            //----------------------------------------------------------------------------------------------------------

                            limpiarDatos();
                            flag = true;

                            //JCORTEZ 17.08.09 Se generan cupones de regalo
                            if (ValidaRolQF(Dniaux))
                                generarCuponesRegalo(vTipo, Dniaux);

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
                    }else{
                       limpiarDatos(); 
                    }
                }
            }
        } else {
            FarmaUtility.moveFocus(txtDni);
        }
    }

    /**
     * Se ingreso temperatura
     * @author JCORTEZ
     * @since 12.02.2009
     * */
    private boolean existeRegistroTemp() {

        boolean valor = true;
        String result = "", SecUsu = "";
        try {
            SecUsu = getSecUsuLocal(txtDni.getText().trim());
            result = verificaIngrTemperatura(SecUsu);

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
            getRegistro(vArrayAux, txtDni.getText().trim());
            validaSalida(vArrayAux1, txtDni.getText().trim());

            if (vArrayAux.size() > 0) {
                FarmaUtility.showMessage(this, "Usted ya se ha registrado el dia de hoy", txtDni);
                retorno = false;
            } /*else
        {
          if(vArrayAux1.size()>0){
           if(cmbTipo.getSelectedIndex()==0){
             FarmaUtility.showMessage(this,"Usted debe registrar su salida",txtDni);
             cmbTipo.setSelectedIndex(1);
             retorno = false;
           }
          }else{
            if(cmbTipo.getSelectedIndex()==1){
              FarmaUtility.showMessage(this,"Usted debe registrar su entrada la primera vez",txtDni);
              cmbTipo.setSelectedIndex(0);
              retorno = false;
            }else{
              retorno = true;
            }
          }
        }*/
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
            cargaListaRegistrosTABLE(tableModel);
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
            cargaListaRegistrosDni(tableModel, pDni);
            lblItems.setText(tblLista.getRowCount() + "");
            
        }catch(SQLException e){
            log.error("ERROR AL CARGAR REGISTRO X DNI"+e.getMessage());
            FarmaUtility.showMessage(this, "Ha ocurrido un error al listar los registros.\n" +
                    e.getMessage(), txtDni);
        }
    }
    //CARGA LOGIN PARA VER EL HISTORIAL DE MARCACION DEL DIA
    //CHUANES 20/07/2015
    private void cargaLogin(){
        DlgLogin dlgLogin = new DlgLogin(myParentFrame,"Acceso al FarmaVenta", true);
        dlgLogin.setMarcacion(false);
        dlgLogin.setVisible(true);
        if (FarmaVariables.vAceptar) {
            if (dlgLogin.verificaRol(FarmaConstants.ROL_ADMLOCAL)){
              
                cargaListaRegistro();
                
            }else{
                FarmaUtility.showMessage(this, "El usuario no tiene asignado el rol adecuado!!!", null);
                FarmaVariables.dlgLogin=dlgLogin;
                chkVer.setSelected(false);
                return;  
            }
           
            FarmaVariables.vAceptar = false;
        }else{
            chkVer.setSelected(false);  
        }
        
    }
    //CHUANES 21/07/2015
    //verifica si es administrador de local y si le falta marcar salidas 
    private boolean isPendienteMarcarSalida(){
      int existeMarcaciones=0;
      boolean flag=false;
      String resultado="";
        try {
            resultado=validaRolAdministrador(txtDni.getText().trim());
            existeMarcaciones=existeMarcacionesPendientes();
            log.info("Es Administrador ?"+resultado);
            log.info("cantidad de marcaciones de salida "+existeMarcaciones);
            if(resultado.equalsIgnoreCase(FarmaConstants.INDICADOR_S) && existeMarcaciones>0 ){
                flag=true;
            }
                
        } catch (SQLException e) {
            log.info("Error al valida rol"+e.getMessage());
            flag=false;
        }
        return flag;
    }
    //CHUANES 04/08/2015
    //verificar regitrar justificaciones para el QF
    private boolean isPendienteJustInasistencia(){
        int existeInasistencias=0;
        boolean flag=false;
        String resultado="";
          try {
              resultado=validaRolAdministrador(txtDni.getText().trim());
              existeInasistencias=existeJustificacionesPendientes();
              log.info("Es Administrador ?"+resultado);
              log.info("cantidad de marcaciones de salida "+existeInasistencias);
              if(resultado.equalsIgnoreCase(FarmaConstants.INDICADOR_S) && existeInasistencias>0 ){
                  flag=true;
              }
                  
          } catch (SQLException e) {
              log.info("Error al valida rol"+e.getMessage());
              flag=false;
          }
          return flag;
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
            SecUsu = getSecUsuLocal(Dni);

            result = verificaRolUsuario(SecUsu, ROL_QF_ADMINLOCAL);
            if (result.equalsIgnoreCase("N"))
                valor = false;
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ha ocurrido un error al validar el rol de usuario .\n" +
                    e.getMessage(), txtDni);
        }
        return valor;
    }


    private boolean ValidaExistenciaCarne() {
        boolean valor = true;
        String result = "";

        try {
            result = verificaExistenciaCarne(vSecUsu);
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

    private boolean ValidaRolTrabLocal() {

        boolean valor = true;
        String result = "", SecUsu = "";
        try {
            SecUsu = getSecUsuLocal(txtDni.getText().trim());
            vSecUsu = SecUsu;

            //VERIFICA_ROL_TRAB_LOCAL
            result =
                    ValidaRolTrabLocal(SecUsu, ROL_CAJERO, ROL_VENDEDOR,
                                                        ROL_QF_ADMINLOCAL);
            if (result.equalsIgnoreCase("N")) {
                valor = false;
                vFechaVencCarne = "";
            }

            else //Para obtener fecha de vencimiento ,validar que tenga carné
            {
                if (ValidaExistenciaCarne()) {

                    vExisteCarne = "S";

                    String resultadoConsulta = "", resultadoMensaje = "";
                    resultadoConsulta = verificaFechaVenUsuarioCarneControlIngreso(SecUsu);
                    resultadoMensaje = verificaFechaVenUsuarioCarne(SecUsu);
                    vFechaVencCarne = resultadoConsulta;
                    vMensajeTiempoVencimiento = resultadoMensaje;


                }

                else
                    vExisteCarne = "N";


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

    private boolean EnviaAlertaAlMarcarIngresoLocal() {

        boolean retorno = false;


        try {
            enviaAlertaCarneUsuarioMarcaIngreso(vSecUsu);
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

        if (tipo.equalsIgnoreCase(TIPO_ENTRADA)) {
            try {

                generaCuponesRegalo(Dni);
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
        log.error("");
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
                        imprimeCuponRegalo(this, codCupon, dni); //IMPRIME
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
            exist = existCuponRegalo(Dniaux);
            if (exist.equalsIgnoreCase("N"))
                valor = true;
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ha ocurrido un error al obtener cupones regalo .\n" +
                    e.getMessage(), txtDni);
        }
        return valor;

    }
    public static boolean verificaVK_F11(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_F11) {
            return true;
        }
        int m = keyEvent.getModifiers();
        if ((m & (InputEvent.SHIFT_DOWN_MASK | InputEvent.SHIFT_MASK)) != 0) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_F1)
                return true;
        }
        /*if ((m & (InputEvent.CTRL_DOWN_MASK | InputEvent.CTRL_MASK)) != 0) {
          log.debug("ctrl ");
        }
        if ((m & (InputEvent.META_DOWN_MASK | InputEvent.META_MASK)) != 0) {
          log.debug("meta ");
        }
        if ((m & (InputEvent.ALT_DOWN_MASK | InputEvent.ALT_MASK)) != 0) {
          log.debug("alt ");
        }
        if ((m & (InputEvent.BUTTON1_DOWN_MASK | InputEvent.BUTTON1_MASK)) != 0) {
          log.debug("button1 ");
        }
        if ((m & (InputEvent.BUTTON2_DOWN_MASK | InputEvent.BUTTON2_MASK)) != 0) {
          log.debug("button2 ");
        }
        if ((m & (InputEvent.BUTTON3_DOWN_MASK | InputEvent.BUTTON3_MASK)) != 0) {
          log.debug("button3 ");
        }*/
        return false;
    }
    //CHUANES 24.02.2015
    //PERMITE SOLO EL USO DEL ESCANEO 
    private boolean isTeclaPermitida(KeyEvent e) {
      
        return (isNumero(e.getKeyChar())||(e.getKeyCode() == KeyEvent.VK_ENTER)||
                (e.getKeyCode() == KeyEvent.VK_ESCAPE) || e.getKeyCode() == KeyEvent.VK_F11
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
    
    
    public void getPersonal(ArrayList pArray, String pDni) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDni);
        //log.debug("",parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArray, "PTOVENTA_INGR_PERS.GET_PERSONAL(?,?,?)",
                                                          parametros);
    }
   /*
    * CHUANES
    * 04.03.2015
    * Modificación, adicion de un nuevo parametro,indicador
    * **/
   
    public void grabarRegistro(String pDni, String pTipo, String pCodCia, String pCodTrab,
                                      String pCodHora,String indicador) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDni);
        parametros.add(pTipo);
        parametros.add(pCodCia);
        parametros.add(pCodTrab);
        parametros.add(pCodHora);
        parametros.add(indicador);
        //log.debug("",parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INGR_PERS.GRABA_REG_PERSONAL(?,?,?,?,?,?,?,?)",
                                                 parametros, false);
    }

    public  void cargaListaRegistrosTABLE(FarmaTableModel pTableModel) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        //log.debug("",parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_INGR_PERS.GET_LISTA_REGISTROS(?,?)",
                                                 parametros, false);
    }
    //CHUANES 04/08/2015
    public static void cargaListaRegistrosDni(FarmaTableModel pTableModel ,String pDni)throws SQLException{
       ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDni);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_INGR_PERS.GET_LISTA_REGISTROS_DNI(?,?,?)",
                                                 parametros, false);    
    }
    /**
     *
     * @param pDni
     * @throws SQLException
     * @since
     */
    public void validarIngreso(String pDni) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(pDni);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_INGR_PERS.GET_VALIDA_TARDANZA(?)", parametros, false);
    }
    
    public String getSecUsuLocal(String Dni) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(Dni);
        log.debug("Obtiene sec_usu_local por dni " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INGR_PERS.GET_SEC_USU_X_DNI(?,?,?)", parametros);
    }
    
    /**
     * validar ingreso quimico en el dia
     * @param SecUsu
     * @param CodRol
     * @throws SQLException
     * @author Jorge Cortez Alvarez
     * @since 12.02.2009
     */
    public  String verificaIngrTemperatura(String SecUsu) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(SecUsu);
        log.debug("verifica ingreso de temperatura en el dia: " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INGR_PERS.VERIFICA_INGR_TEMP_USU(?,?,?)",
                                                           parametros);
    }
    
    public void getRegistro(ArrayList pArray, String pDni) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pDni);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArray, "PTOVENTA_INGR_PERS.TRA_EXIST_REGISTRO(?,?)",
                                                          parametros);
    }
    
    public void validaSalida(ArrayList pArray, String pDni) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pDni);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArray, "PTOVENTA_INGR_PERS.TRA_VALIDA_SALIDA(?,?)",
                                                          parametros);
    }
    
    public String verificaRolUsuario(String SecUsu, String CodRol) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(SecUsu);
        parametros.add(CodRol);
        log.debug("verifica que el usuario tenga el rol adecuado: " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INGR_PERS.VERIFICA_ROL_USU(?,?,?,?)", parametros);
    }
 
    public String ValidaRolTrabLocal(String SecUsu, String CodRolCajero, String CodRolVendedor,
                                            String CodRolAdministrador) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(SecUsu);
        parametros.add(CodRolCajero);
        parametros.add(CodRolVendedor);
        parametros.add(CodRolAdministrador);
        log.debug("verifica que el usuario tenga el rol  de trabajador de local : " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INGR_PERS.VERIFICA_ROL_TRAB_LOCAL(?,?,?,?,?,?)",
                                                           parametros);
    }   
    
    public String verificaExistenciaCarne(String pCodTrab) throws SQLException {

        String rpta = "";
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodTrab);

        log.debug("verificaExistenciaCarne" + parametros);
        rpta =
    FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_USU.USU_VERIFICA_EXISTENCIA_CARNE(?,?,?)", parametros);
        return rpta;

    }
    
    public String verificaFechaVenUsuarioCarneControlIngreso(String pCodTrab) throws SQLException {

        String rpta = "";
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodTrab);

        log.debug("verificaFechaVenUsuarioCarneControlIngreso" + parametros);
        rpta =
    FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_USU.USU_OBTIENE_FECVENC_PROX_CARNE(?,?,?)", parametros);
        return rpta;

    }
    
    public String verificaFechaVenUsuarioCarne(String pCodTrabRRhh) throws SQLException {

        String rpta = "";
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodTrabRRhh);

        log.debug("verificaFechaVenUsuarioCarne" + parametros);
        rpta =
    FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_USU.USU_OBTIENE_FECVENC_PROX(?,?,?)", parametros);
        return rpta;

    }
    
    public void enviaAlertaCarneUsuarioMarcaIngreso(String pCodTrab) throws SQLException {
        ArrayList parametros = new ArrayList();

        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodTrab);

        log.debug("****///////ENVIAR ALERTA" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_ADMIN_USU.USU_ALERTA_TRAB_S_CARNE_M_ING(?,?,?)",
                                                 parametros, false);
    }
    
    public String existCuponRegalo(String Dni) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(Dni);
        log.debug("invocando a PTOVENTA_CUPON.CUP_F_VERI_EXIST_CUP(?,?,?): " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CUPON.CUP_F_VERI_EXIST_CUP(?,?,?)", parametros);
    }
    
    public void imprimeCuponRegalo(JDialog pDialogo, String vCodeCupon, String Dni) {
        log.debug("invocando a ");
    }
    
    public void generaCuponesRegalo(String NumDni) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(NumDni);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_CUPON.CAJ_P_GENERA_CUPON_REGALO(?,?,?,?)", parametros,
                                                 false);
    }
    /**
     * CHUANES
     * 03.03.2015
     * Verifica  si permite la marcacion del control de ingreso en forma manual o electronica.
     * 
     * **/
    public String indIngreso() throws SQLException { 
        ArrayList parametros = new ArrayList();
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INGR_PERS.INDICADOR_MARCACION_INGRESO", parametros);
    }
    /**
     * CHUANES
     * 03.03.2015
     * Se obtiene el indicador de ingreso
     * 
     * **/

    public String indicadorIngreso(){
       
       String indicador="";
        try {
            indicador = this.indIngreso().trim();
        } catch (SQLException e) {
            FarmaUtility.showMessage(this, "Error al verificar la forma de marcación.\n" +
                    e.getMessage(), txtDni); 
        }
        return indicador; 
    }
    //CHUANES 24/08/2015
    public static String validaRolAdministrador(String pDni)throws SQLException{
         ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDni);
        log.debug("invocando a PTOVENTA_INGR_PERS.IS_ADMINISTRADOR_LOCAL(?,?,?,?): " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_INGR_PERS.IS_ADMINISTRADOR_LOCAL(?,?,?,?)", parametros);
        
    }
    //CHUANES 24/08/2015
    public static int existeMarcacionesPendientes(){
        ArrayList lstMarcacion=new ArrayList();  
        ArrayList   parametros = new ArrayList();
          parametros.add(FarmaVariables.vCodGrupoCia);
          parametros.add(FarmaVariables.vCodCia);
          parametros.add(FarmaVariables.vCodLocal); 
          try {
              FarmaDBUtility.executeSQLStoredProcedureArrayList(lstMarcacion, "PTOVENTA_MARCACION_HORARIO.LISTADO_MARCACION(?,?,?)",
                                                                parametros);
          } catch (Exception e) {
              log.error("",e.getMessage());
             
          }
        return lstMarcacion.size();
            
    }
    //CHUANES 24/08/2015
    public static int existeJustificacionesPendientes(){
        ArrayList lstInasistencias=new ArrayList();  
        ArrayList  parametros = new ArrayList();
          parametros.add(FarmaVariables.vCodGrupoCia);
          parametros.add(FarmaVariables.vCodLocal); 
          try {
              FarmaDBUtility.executeSQLStoredProcedureArrayList(lstInasistencias, "PTOVENTA_MARCACION_HORARIO.LISTADO_INASISTENCIAS(?,?)",
                                                                parametros);
          } catch (Exception e) {
              log.error("",e.getMessage());
             
          }
       return  lstInasistencias.size();
    }
    public String getIndicador() {
        return indicador;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }
    

}

