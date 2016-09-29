package mifarma.ptoventa.controlAsistencia;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import com.toedter.calendar.JDateChooser;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.cnx.FarmaVentaCnxUtility;
import mifarma.ptoventa.controlAsistencia.reference.ConstantsControlAsistencia;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.controlAsistencia.reference.UtilityControlAsistencia;
import mifarma.ptoventa.controlAsistencia.util.UtilFtp;
import mifarma.ptoventa.controlAsistencia.util.ZipFile;
import mifarma.ptoventa.reference.BeanConexion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

 /**
  * Copyright (c) 2015 MIFARMA S.A.C.<br>
  * <br>
  * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
  * Nombre de la Aplicación : DlgMantenimientoSolicitud.java<br>
  * <br>
  * Histórico de Creación/Modificación<br>
  * CHUANES       02.09.2015   Creación<br>
  * EMAQUERA      15.10.2015   Modificacion<br>
  * <br>
  * @author Cesar Huanes<br>
  * @version 1.0<br>
  *
  */
public class DlgMantenimientoSolicitud extends JDialog  {
    
        private static final Logger log = LoggerFactory.getLogger(DlgMantenimientoSolicitud.class);
        @SuppressWarnings("compatibility:398606826956210138")
        private static final long serialVersionUID = 1L;
        private Frame myParentFrame;
        private JPanelWhite jPanelWhite1 = new JPanelWhite();
        private JPanelHeader pnlCabecera = new JPanelHeader();
        private JTextFieldSanSerif txtDocIden = new JTextFieldSanSerif();
        
        private JPanelTitle pnlDatos = new JPanelTitle();
        private JButtonLabel btnDni = new JButtonLabel();
        private JButtonLabel btnObservacion = new JButtonLabel();
        private JButtonLabel btnFecInicio = new JButtonLabel();
        private JButtonLabel btnFecFinal = new JButtonLabel();
        private JButtonLabel btnTipSolicitud = new JButtonLabel();
        private JButtonLabel btnDesSolicitud = new JButtonLabel();
        private JComboBox cmbSolicitud=new JComboBox();
        private JComboBox cmbTipSolicitud=new  JComboBox();
        private JTextFieldSanSerif txtRuta = new JTextFieldSanSerif();
    
        private JButton btnSeleccionar =new JButton();
        private JButtonLabel txtUsuario = new JButtonLabel();
        private JTextFieldSanSerif txtFecInicio = new JTextFieldSanSerif();
        private JTextFieldSanSerif txtFecFin = new JTextFieldSanSerif();
        private JTextArea txtObservacion =new JTextArea();
        private JLabelFunction lblEsc = new JLabelFunction();
        private JLabelFunction lblF1 = new JLabelFunction();
        private String docIden = "";
        private String nombreUsuario="";
        private String fechaInicio = "";
        private String fechaFin = "";
        private String tipSoli = "";
        private String codSolicitud = "";
        private String codTipoSolitud = "";
        private String observacion = "";
        private String nomArchivo = "";
        private String rutaArchivo = "";
        private int idFuncEmail=0;
        private String descripEmail="";
        private String codAprobEmail="";
        ArrayList lstSolicitudCmb=new ArrayList();
        ArrayList lstTipoSoliCmb=new ArrayList();
        String dirFtpRemoto,  usrFtp, pwdFtp,servidorFtp;
        int puertoFtp;
        private static BeanConexion conexionFtp;
    //private JTextField txtFechaHora_Nueva = new JTextField();
    private JDateChooser txtFechaHora_Nueva = new JDateChooser("dd/MM/yyyy HH:mm", "####/##/## ##:##", '_');

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */
        
    public DlgMantenimientoSolicitud() {
        this(null, " ", false);
    }

    public DlgMantenimientoSolicitud(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }
    
    /* ********************************************************************** */
    /*                        Método "jbInit()"                               */
    /* ********************************************************************** */
    private void jbInit() throws Exception {
    this.setSize(new Dimension(533, 364));
    this.getContentPane().setLayout(null);
    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    this.setTitle("Mantenimiento de Solicitudes");
    this.addWindowListener(new WindowAdapter() {
        public void windowOpened(WindowEvent e) {
            this_windowOpened(e);
        }

        public void windowClosing(WindowEvent e) {
            this_windowClosing(e);
        }
    });
    jPanelWhite1.setLayout(null);
    jPanelWhite1.setBounds(new Rectangle(0, 0, 530, 340));
    pnlCabecera.setBounds(new Rectangle(5, 5, 510, 30));
   
    pnlCabecera.setBackground(new Color(43, 141, 39));
    pnlCabecera.setLayout(null);
    pnlCabecera.setFont(new Font("SansSerif", 0, 11));
 
    btnDni.setText("Ingrese Dni :");
    btnDni.setBounds(new Rectangle(5, 5, 75, 20));
    btnDni.setForeground(Color.white);
    btnDni.setMnemonic('n');
    btnDni.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                btnDni_actionPerformed(e);
            }
        });
    pnlDatos.setBounds(new Rectangle(5, 45, 510, 235));
    pnlDatos.setBackground(Color.white);
    pnlDatos.setFont(new Font("SansSerif", 0, 11));
    pnlDatos.setLayout(null);
    pnlDatos.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));

    btnTipSolicitud.setText("Tipo Solicitud:");
    btnTipSolicitud.setBounds(new Rectangle(10, 15, 85, 20));
    btnTipSolicitud.setForeground(new Color(255, 130, 14));
    btnTipSolicitud.setMnemonic('t');
    btnTipSolicitud.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                FarmaUtility.moveFocus(cmbSolicitud);
            }
        }); 
    cmbSolicitud.setBounds(new Rectangle(95, 15, 130, 20));
    cmbSolicitud.addItemListener(new ItemListener() {
                            public void itemStateChanged(ItemEvent e) {
                                cmbSolicitud_itemStateChanged(e);
                            }
                        });
    cmbSolicitud.addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                cmbSolicitud_keyPressed(e);  
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
      
    btnDesSolicitud.setBounds(new Rectangle(230, 15, 105, 20));
    btnDesSolicitud.setForeground(new Color(255, 130, 14));
    btnDesSolicitud.setMnemonic('p');
    btnDesSolicitud.addActionListener(new ActionListener(){
    
            @Override
            public void actionPerformed(ActionEvent e) {
                FarmaUtility.moveFocus(cmbTipSolicitud);
            }
        });
    cmbTipSolicitud.setBounds(new Rectangle(340, 15, 155, 20));
    cmbTipSolicitud.addItemListener(new ItemListener() {
                                public void itemStateChanged(ItemEvent e) {
                                    cmbTipoSolicitud_itemStateChanged(e);
                                }
                            });  
    cmbTipSolicitud.addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                cmbTipoSolici_keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    btnFecInicio.setText("Fecha Inicio:");
    btnFecInicio.setBounds(new Rectangle(10, 55, 75, 20));
    btnFecInicio.setForeground(new Color(255, 130, 14));
    btnFecInicio.setMnemonic('e');
    btnFecInicio.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
           btnFecInicio_actionPerformed(e);
        }
    });
    btnFecFinal.setText("Fecha Final:");
    btnFecFinal.setBounds(new Rectangle(250, 55, 75, 20));
    btnFecFinal.setForeground(new Color(255, 130, 14));
    btnFecFinal.setMnemonic('F');
    btnFecFinal.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            btnFecFin_actionPerformed(e);
        }
    });

    txtDocIden.setBounds(new Rectangle(80, 5, 100, 20));
    txtDocIden.setFont(new Font("SansSerif", 0, 11));
    txtDocIden.setDocument(new FarmaLengthText(8));
    txtDocIden.addKeyListener(new KeyAdapter() {
        public void keyPressed(KeyEvent e) {
            txtDocIden_keyPressed(e);
        }
    });
    txtUsuario.setBounds(new Rectangle(250, 5, 250, 20));
    txtUsuario.setFont(new Font("SansSerif", 0, 11));
    txtUsuario.setBackground(Color.WHITE);
    
    txtFecInicio.setBounds(new Rectangle(95, 55, 130, 20));
    txtFecInicio.setFont(new Font("SansSerif", 0, 11));
  
    txtFecInicio.addKeyListener(new KeyAdapter() {
        public void keyPressed(KeyEvent e) {
            txtFecInicio_keyPressed(e);
        }
        public void keyReleased(KeyEvent e) {
            txtFecInicio_keyReleased(e);
        }
    });
    txtFecInicio.setDocument(new FarmaLengthText(10));
    txtFecFin.setBounds(new Rectangle(340, 55, 155, 20));
    txtFecFin.setFont(new Font("SansSerif", 0, 11));
  
    txtFecFin.addKeyListener(new KeyAdapter() {
        public void keyPressed(KeyEvent e) {
            txtFecFin_keyPressed(e);
        }
        public void keyReleased(KeyEvent e) {
            txtFecFin_keyReleased(e);
        }
    });
    txtFecFin.setDocument(new FarmaLengthText(10));

    lblEsc.setText("[ESC] Cerrar");
    lblEsc.setBounds(new Rectangle(410, 295, 90, 25));
    lblEsc.setFont(new Font("SansSerif", 1, 12));
    
    lblF1.setText("[F11] Aceptar");
    lblF1.setBounds(new Rectangle(280, 295, 90, 25));
    lblF1.setFont(new Font("SansSerif", 1, 12));

        txtFechaHora_Nueva.setBounds(new Rectangle(95, 55,130, 20));
        //txtFechaHora_Nueva.setPreferredSize(new Dimension(130, 20));
        txtFechaHora_Nueva.setFont(new Font("Verdana", Font.PLAIN, 10));

        txtFechaHora_Nueva.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtFechaHora_Nueva_keyPressed(e);
                }
            });
        Border border = BorderFactory.createLineBorder(Color.black);
    
    btnObservacion.setText("Observación");
    btnObservacion.setBounds(new Rectangle(10, 85, 90, 20));
    btnObservacion.setForeground(new Color(255, 130, 14));
    btnObservacion.setMnemonic('o');
    btnObservacion.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                FarmaUtility.moveFocus(txtObservacion);
            }
        });    
    txtObservacion.setBounds(new Rectangle(10, 115, 485, 70));
    txtObservacion.setForeground(new Color(255, 130, 14));
    txtObservacion.setBorder(border);
    
    txtObservacion.addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {
                validadCantidadDigitros();
            }

            @Override
            public void keyPressed(KeyEvent e) {
                txtObservacion_keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                validadCantidadDigitros(); 
            }
        });
    
    btnSeleccionar.setText("Buscar Documento");
    btnSeleccionar.setBounds(new Rectangle(345, 195, 145, 25));
    btnSeleccionar.setFont(new Font("SansSerif", 1, 11));
    btnSeleccionar.setMnemonic('u');
  
    btnSeleccionar.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                adjuntarArchivo();
            }
        });
    txtRuta.setText("");
    txtRuta.setBounds(new Rectangle(10, 195, 325, 25));
    txtRuta.setEnabled(false);
        pnlDatos.add(txtFecFin, null);
    pnlDatos.add(txtFecInicio, null);
    pnlDatos.add(btnFecFinal, null);
    pnlDatos.add(btnFecInicio, null);
        pnlDatos.add(btnObservacion, null);
    pnlDatos.add(txtObservacion,null);
    pnlDatos.add(txtRuta,null);
    
    pnlDatos.add(btnDesSolicitud, null);
    pnlDatos.add(btnTipSolicitud,null);
    pnlDatos.add(cmbTipSolicitud,null);
    pnlDatos.add(cmbSolicitud,null);

    pnlDatos.add(btnSeleccionar,null);
        pnlDatos.add(txtFechaHora_Nueva, null);
        pnlCabecera.add(txtDocIden, null);
    pnlCabecera.add(btnDni, null);
    pnlCabecera.add(txtUsuario, null);
    jPanelWhite1.add(lblF1, null);
    jPanelWhite1.add(lblEsc, null);
    jPanelWhite1.add(pnlDatos, null);
    jPanelWhite1.add(pnlCabecera, null);
    this.getContentPane().add(jPanelWhite1, null);
    }

    /* ********************************************************************** */
    /*                        Método "initialize()"                           */
    /* ********************************************************************** */
        
    private void initialize() {
        listaComboSolicitud();
    }        

    /* ********************************************************************** */
    /*                      Métodos de eventos                                */
    /* ********************************************************************** */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtDocIden);
        //permisoQf();
    }
    
    private void btnDni_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtDocIden);
    }

    private void btnFecInicio_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFecInicio);
    }

    private void btnFecFin_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFecFin);
    }
   
    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    
    private void txtFecInicio_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFecInicio, e);
    }

    private void txtFecFin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFecFin, e);
    }
    
    private void txtDocIden_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(!validarDni()){
                return;
            }else if(!buscarDni()){
              return;  
            }
            FarmaUtility.moveFocus(cmbSolicitud);            
        }
        chkKeyPressed(e);
    }
    
    private void cmbSolicitud_keyPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(this.getCodSolicitud().equalsIgnoreCase(ConstantsControlAsistencia.TIPO_VACACIONES)){
                FarmaUtility.moveFocus(txtFecInicio);
            }else{
                FarmaUtility.moveFocus(cmbTipSolicitud);    
            }
        }
        chkKeyPressed(e);        
    }
    
    private void cmbTipoSolici_keyPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtFecInicio);
        }
        chkKeyPressed(e);              
    }

    private void txtFecInicio_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(this.getCodSolicitud().equalsIgnoreCase(ConstantsControlAsistencia.TIPO_CESE)){
                FarmaUtility.moveFocus(txtObservacion);
            }else{
                FarmaUtility.moveFocus(txtFecFin);   
            }
           
           
        }
        chkKeyPressed(e);
    }

    private void txtFecFin_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
           
            FarmaUtility.moveFocus(txtObservacion);
        }
        chkKeyPressed(e);
    }
   
    private void txtObservacion_keyPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtDocIden);
        }
        chkKeyPressed(e);
    }
    
    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (UtilityPtoVenta.verificaVK_F11(e)) {
            grabar();
        }
          
    }
   
    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ********************************************************************** */
    /*                   Métodos de lógica de negocio                         */
    /* ********************************************************************** */
    
        private boolean buscarDni(){
        String dni=txtDocIden.getText().trim();
        ArrayList  lstDatoUsuario=new ArrayList() ;
       
        lstDatoUsuario=UtilityControlAsistencia.getDatosUsuario(dni);
        if(lstDatoUsuario.size()>0){
            String datos=((ArrayList)lstDatoUsuario.get(0)).get(0).toString();
            txtUsuario.setText(datos);
            this.setNombreUsuario(datos);
            FarmaUtility.moveFocus(cmbSolicitud);
            return true;
        }else{
            txtUsuario.setText("");          
            FarmaUtility.showMessage(this, "El Usuario ingresado no esta registrado en el local.", txtUsuario);
            FarmaUtility.moveFocus(txtDocIden);
            return false;
        }
    }
        
    private boolean validarDni(){
        String dni=txtDocIden.getText().trim();
        if(dni.length()!=8 ){
            txtUsuario.setText("");
            FarmaUtility.showMessage(this, "El Dni no tiene el formato correcto, debe ingresar 8 Digitos.", txtUsuario); 
            return false;
        }
        if(!UtilityPtoVenta.isNumeric(dni)){
            FarmaUtility.showMessage(this, "El Dni no tiene el formato correcto, solo ingresar numeros.", txtUsuario); 
           return false; 
        }
        return true;
    }
    
    private void listaComboSolicitud(){
        lstSolicitudCmb= UtilityControlAsistencia.listarTipoSolic();
        FarmaLoadCVL.loadCVLFromArrayList(cmbSolicitud, "cmbSolicitud", lstSolicitudCmb, true);
    }
    
    private void listaComboTipoSolicitud(String codSolicitud){      
        lstTipoSoliCmb=UtilityControlAsistencia.listarSubTipoSolic(codSolicitud) ;
        cmbTipSolicitud.removeAllItems();
        FarmaLoadCVL.loadCVLFromArrayList(cmbTipSolicitud, "cmbTipSolicitud", lstTipoSoliCmb, true);       
    }
    
    private void cmbSolicitud_itemStateChanged(ItemEvent e) {
        int indexSol=cmbSolicitud.getSelectedIndex();
        if(indexSol>-1){
            String codSolicitud=FarmaLoadCVL.getCVLCode("cmbSolicitud", indexSol).toString().trim();
            this.setCodSolicitud(codSolicitud);
            log.info("Cod Soli : "+codSolicitud);
            listaComboTipoSolicitud(codSolicitud);
            setTipoSolicitud(codSolicitud);
        }
    }
    
    private void  cmbTipoSolicitud_itemStateChanged(ItemEvent e ){
        int indexTipSol=cmbTipSolicitud.getSelectedIndex();
        if(indexTipSol>-1){
            String codTipSolicitud=FarmaLoadCVL.getCVLCode("cmbTipSolicitud", indexTipSol).toString().trim();
            log.info("Tip Cod Soli :"+codTipSolicitud);
            this.setCodTipoSolitud(codTipSolicitud);
        }
    }
    
    private void setTipoSolicitud(String codSolicitud){
               btnDesSolicitud.setText("");
               cmbTipSolicitud.setVisible(true);
               txtFecFin.setVisible(true);
               txtFecFin.setText("");
               btnFecFinal.setVisible(true);
               btnFecInicio.setText("Fecha Inicio:");
               txtFechaHora_Nueva.setVisible(false);
               txtRuta.setVisible(true);
               btnSeleccionar.setVisible(true);  
        if(codSolicitud.equalsIgnoreCase(ConstantsControlAsistencia.TIPO_VACACIONES)){
                cmbTipSolicitud.setVisible(false);
                this.setCodTipoSolitud("");
                txtFechaHora_Nueva.setVisible(false);
                btnFecFinal.setVisible(true);
                txtFecFin.setVisible(true);
                txtFecInicio.setVisible(true);
                btnFecInicio.setText("Fecha Inicio:");
        }else if(codSolicitud.equalsIgnoreCase(ConstantsControlAsistencia.TIPO_SUBSIDIO)){
                btnDesSolicitud.setText(ConstantsControlAsistencia.DES_TIPO_SUBSIDIO); 
                txtFecFin.setVisible(true);
                txtFechaHora_Nueva.setVisible(false);
                btnFecFinal.setVisible(true);
                txtFecInicio.setVisible(true);
                btnFecInicio.setText("Fecha Inicio:");
        }else if(codSolicitud.equalsIgnoreCase(ConstantsControlAsistencia.TIPO_CESE)){
                btnDesSolicitud.setText(ConstantsControlAsistencia.DES_TIPO_CESE);
                txtFecFin.setVisible(false);
                btnFecFinal.setVisible(false);
                btnFecInicio.setText("Fecha Cese:");
                txtFecInicio.setVisible(true);
                txtFechaHora_Nueva.setVisible(false);
        }else if(codSolicitud.equalsIgnoreCase(ConstantsControlAsistencia.TIPO_CORRECCION)){
                btnDesSolicitud.setText("Tipo de Marcación");
                txtFecFin.setVisible(false);
                btnFecFinal.setVisible(false);
                btnFecInicio.setText("Fecha Hora:");
                txtFecInicio.setVisible(false);
                txtFechaHora_Nueva.setVisible(true);
                txtRuta.setVisible(false);
                btnSeleccionar.setVisible(false);
        }
    }
    
    private void adjuntarArchivo(){
        JFileChooser fileChooser=new JFileChooser("C:\\");
        String filenames   = "";
        String directories = "";
        
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(true);
        int resultado=fileChooser.showOpenDialog(null);        
                
        if(resultado == JFileChooser.APPROVE_OPTION)
        {
           if(fileChooser.isMultiSelectionEnabled())
           {
               final File[] files = fileChooser.getSelectedFiles();
               if (files != null && files.length > 0){
                   for (int x = 0; x < files.length; x++)
                   { 
                      filenames = filenames + "|" + files[x].getName();
                      directories = directories + ";;;" +files[x].getAbsolutePath();
                   }//end of for loop
               System.out.println("You have selected these files:\n" +filenames);
               }
               this.txtRuta.setText(directories);
               log.info("filenames: "+filenames);
               log.info("directorios: "+directories);
               
               FarmaUtility.moveFocus(txtObservacion);               
           }
        } else if (resultado == JFileChooser.CANCEL_OPTION){
            log.info(" Usuario cancelo la eleccion de archivo(s).");
            this.txtRuta.setText("");
        }
    }
   
    public String getFechaDateChooser(JDateChooser vDate){
        String vValorFecha="";
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            vValorFecha = sdf.format(vDate.getDate());
        }catch(Exception e){
            vValorFecha="";
        };
        return vValorFecha.trim();
    }
    
    private void grabar(){
        
        if(getFechaDateChooser(txtFechaHora_Nueva).trim().length()>0&&txtFecInicio.getText().trim().length()==0)
            txtFecInicio.setText(getFechaDateChooser(txtFechaHora_Nueva).substring(0,10));
        
        if(!validarDni()){
            return;
        }
        
        if(!buscarDni()){
          return;  
        }
        
        if(!validarDatos()){
          return;  
        }
        
        if(!existeCruceFecha()){
          return;  
        }
        grabarSolicitud();
    }
  
    private void  grabarSolicitud(){
        try {
            docIden = txtDocIden.getText().trim();
            fechaInicio = txtFecInicio.getText().trim();
            fechaFin = txtFecFin.getText().trim();
            observacion = txtObservacion.getText();
            rutaArchivo = txtRuta.getText();
            nomArchivo = "";
            boolean flag = true;
            
            String pFechaMarcacionNueva = getFechaDateChooser(txtFechaHora_Nueva);
            if(pFechaMarcacionNueva.trim().length()>0&&fechaInicio.trim().length()==0)
                fechaInicio = pFechaMarcacionNueva.substring(0,10);
            
            if (!rutaArchivo.trim().equalsIgnoreCase("")) {
                nomArchivo = "X";
            }
            ;
            log.debug("Tipo Solicitud" + this.getCodTipoSolitud());
            log.debug("id usu --> " + FarmaVariables.vIdUsu);
            String numRegistroSol =
                UtilityControlAsistencia.grabarSolicitud(docIden, fechaInicio, fechaFin, this.getCodSolicitud(),
                                                         this.getCodTipoSolitud(), nomArchivo, observacion,
                                                         pFechaMarcacionNueva);
            //SI GRABO LOCAL
            if (numRegistroSol.length() == ConstantsControlAsistencia.IND_CODIGO) {
                

                log.debug("Ruta: " + rutaArchivo);

                if (!rutaArchivo.trim().equalsIgnoreCase("")) { //SI SELECCIONO ARCHIVO
                    String rutaOrigen = "";
                    String[] rutasArray = rutaArchivo.split(";;;");

                    String nomFile = FarmaVariables.vCodGrupoCia + FarmaVariables.vCodLocal + numRegistroSol;
                    String INPUT_FOLDER = "C:\\farmaventa\\" + nomFile;
                    String ZIPPED_FOLDER = "C:\\farmaventa\\" + nomFile + ".zip";
                    File directorio = new File(INPUT_FOLDER);
                    File diretory_zip = new File(ZIPPED_FOLDER);

                    if (rutasArray.length == 2) {
                        rutaOrigen = rutasArray[1];
                    } else {
                        directorio.mkdir();
                        InputStream inStream = null;
                        OutputStream outStream = null;

                        try {
                            for (int i = 1; i < rutasArray.length; i++) {
                                //System.out.println( rutasArray[i]+"\n");

                                System.out.println("ruta 1 : " + rutasArray[i]);
                                String extension = "";
                                log.info("Nombre completo del archivo " + rutasArray[i]);
                                int a = rutasArray[i].lastIndexOf('.');
                                if (a > 0) {
                                    extension = rutasArray[i].substring(a + 1, rutasArray[i].length());
                                }
                                File afile = new File(rutasArray[i]);
                                File bfile = new File(INPUT_FOLDER + "\\" + nomFile + "_" + i + "." + extension);

                                inStream = new FileInputStream(afile);
                                outStream = new FileOutputStream(bfile);

                                byte[] buffer = new byte[1024];

                                int length;
                                //copy the file content in bytes
                                while ((length = inStream.read(buffer)) > 0) {
                                    outStream.write(buffer, 0, length);
                                }

                                inStream.close();
                                outStream.close();

                                System.out.println("File is copied successful!");
                            }

                            //Se comprime el directorio
                            ZipFile.zip(INPUT_FOLDER, ZIPPED_FOLDER);
                            rutaOrigen = ZIPPED_FOLDER;

                        } catch (IOException e) {
                            log.error("", e);
                        }
                    }

                    cargarParametrosFtp();

                    boolean envioFtp =
                        UtilFtp.conectarFtp(rutaOrigen, this.getDirFtpRemoto(), numRegistroSol, this.getUsrFtp(),
                                            this.getPwdFtp(), this.getPuertoFtp(), this.getServidorFtp());
                    log.debug("Envio FTP ? =" + envioFtp);
                    if (envioFtp) {
                        //ENVIO CORREO ELECTRONICO
                        UtilityControlAsistencia.actualizaNomArchivo(numRegistroSol, rutaOrigen);
                        UtilityControlAsistencia.enviarCorreo(numRegistroSol);
                    } else {
                        UtilityControlAsistencia.solicitudIncompleta(numRegistroSol);
                        FarmaUtility.aceptarTransaccion();
                        FarmaUtility.showMessage(this, "Error al Generar Solicitud. Problemas con el FTP. \n"+
                                                       "Si el problema persiste comunicarse con Mesa Ayuda.", txtDocIden);
                        flag = false;
                        cerrarVentana(false);
                    }

                    directorio.delete();
                    diretory_zip.delete();
                } else {
                    UtilityControlAsistencia.enviarCorreo(numRegistroSol);
                    
                }
                
                if(flag){
                    FarmaUtility.showMessage(this, "Solicitud Generada Correctamente.", txtDocIden);
                    cerrarVentana(false);
                }
            } else {
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this, "Error al grabar solicitud.", txtDocIden);
            }
            FarmaUtility.aceptarTransaccion();
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this, "Error al grabar solicitud.\n"+e.getMessage(), txtDocIden);
        }
    }
    
    public void cargarParametrosFtp(){
        try {
            FarmaVentaCnxUtility facade = new FarmaVentaCnxUtility();
            conexionFtp = facade.setBeanConexion(UtilityControlAsistencia.getParametrosFTP());
            this.setServidorFtp(conexionFtp.getIPBD());
            this.setUsrFtp(conexionFtp.getUsuarioBD());
            this.setPwdFtp(conexionFtp.getClaveBD());
            this.setPuertoFtp(Integer.valueOf(conexionFtp.getPORT()));
            this.setDirFtpRemoto(conexionFtp.getServiceName());//SE USARA COMO DIRECTORIO REMOTO                       
        } catch (Exception f) {
            log.error("Error al setear parametros de conexion", f);
        }
    }
    
    private boolean validarDatos() {

        if (txtUsuario.getText().equalsIgnoreCase("")) {
            FarmaUtility.showMessage(this, "Favor ingrese Nombre de Usuario", txtUsuario);
            return false;
        }
        
        if (txtFecInicio.getText().trim().equals("")) {
            FarmaUtility.showMessage(this, "Favor ingrese la Fecha de Inicio.", txtFecInicio);
            return false;
        }        
        
        if(!FarmaUtility.validaFecha(txtFecInicio.getText(), "dd/MM/yyyy") ||
            txtFecInicio.getText().length() != 10){
               FarmaUtility.showMessage(this, "El formato ingresado en Fecha de Inicio es incorrecto. (DD/MM/YYYY)", txtFecInicio);
               return false;               
        }
        
        if(!(this.getCodSolicitud().equalsIgnoreCase(ConstantsControlAsistencia.TIPO_CESE) ||
             this.getCodSolicitud().equalsIgnoreCase(ConstantsControlAsistencia.TIPO_CORRECCION))) {
            
            if (txtFecFin.getText().trim().equals("")) {
                FarmaUtility.showMessage(this, "Favor ingrese la Fecha de Fin.", txtFecFin);
                return false;
            }
            
            if (!FarmaUtility.validaFecha(txtFecFin.getText(), "dd/MM/yyyy") ||
                 txtFecFin.getText().length() != 10) {
                FarmaUtility.showMessage(this, "Ingrese la Fecha Final correctamente", txtFecFin);
                return false;
            }
            
            if (!FarmaUtility.validarRangoFechas(this, txtFecInicio, txtFecFin, false, true, true, true)){
                return false;            
            }
        }

        if(this.getCodSolicitud().equalsIgnoreCase(ConstantsControlAsistencia.TIPO_CORRECCION)){
            if(UtilityControlAsistencia.isFechaCorrecion(txtFecInicio.getText())){
                FarmaUtility.showMessage(this, "La Fecha debe ser igual o menor a la Fecha Actual", txtFecInicio);
                return false;
            }            
        }else{
            if(UtilityControlAsistencia.isFechaIncioFuturo(txtFecInicio.getText())){
                FarmaUtility.showMessage(this, "La Fecha de Inicio debe ser igual o mayor a la Fecha Actual", txtFecInicio);
                return false;
            }
            
        }

        if(txtObservacion.getText() == null || 
           (txtObservacion.getText().length() < 3 || txtObservacion.getText().length() > 200 )){
            FarmaUtility.showMessage(this, "Favor ingresar la Observación, debe tener minimo 3 y maximo 200 caracteres.",
                                     txtObservacion);
            return false;
        }          
        
        return true;
    }
    
    private boolean existeCruceFecha(){
      if(UtilityControlAsistencia.existeCruceFecha(txtDocIden.getText().trim(),
                                                   txtFecInicio.getText(), txtFecFin.getText(),
                                                   this.getCodSolicitud())){
            FarmaUtility.showMessage(this, "No se puede generar la solicitud porque existe un cruce de fechas de este \n" +
                "tipo de solicitud con el  usuario "+this.getNombreUsuario(), txtDocIden); 
            return false;                                    
        }
      return true;
    }    
  
    public String getCodSolicitud() {
        return codSolicitud;
    }
    
    public void setCodSolicitud(String codSolicitud) {
        this.codSolicitud = codSolicitud;
    }

    public String getDocIden() {
        return docIden;
    }

    public void setDocIden(String docIden) {
        this.docIden = docIden;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getTipSoli() {
        return tipSoli;
    }

    public void setTipSoli(String tipSoli) {
        this.tipSoli = tipSoli;
    }

    public String getCodTipoSolitud() {
        return codTipoSolitud;
    }

    public void setCodTipoSolitud(String codTipoSolitud) {
        this.codTipoSolitud = codTipoSolitud;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public String getNomArchivo() {
        return nomArchivo;
    }

    public void setNomArchivo(String nomArchivo) {
        this.nomArchivo = nomArchivo;
    }
    
    private void validadCantidadDigitros(){
        String texto="";
        texto=txtObservacion.getText().toUpperCase();
        txtObservacion.setLineWrap(true);
        txtObservacion.setWrapStyleWord(true);
        if(txtObservacion.getText().length()<=ConstantsControlAsistencia.CANTIDAD_DIGITOS){
            txtObservacion.setText(texto);
        }else{
            this.getToolkit().beep();
            
            FarmaUtility.showMessage(this, "Cantidad  maxima 200 caracteres.",
                                     null);
            txtObservacion.setText(texto.substring(0,ConstantsControlAsistencia.CANTIDAD_DIGITOS));
        }
    }
   
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getDescripEmail() {
        return descripEmail;
    }

    public void setDescripEmail(String descripEmail) {
        this.descripEmail = descripEmail;
    }

    public String getCodAprobEmail() {
        return codAprobEmail;
    }

    public void setCodAprobEmail(String codAprobEmail) {
        this.codAprobEmail = codAprobEmail;
    }

    public int getIdFuncEmail() {
        return idFuncEmail;
    }

    public void setIdFuncEmail(int idFuncEmail) {
        this.idFuncEmail = idFuncEmail;
    }

    public String getDirFtpRemoto() {
        return dirFtpRemoto;
    }

    public void setDirFtpRemoto(String dirFtpRemoto) {
        this.dirFtpRemoto = dirFtpRemoto;
    }

    public String getUsrFtp() {
        return usrFtp;
    }

    public void setUsrFtp(String usrFtp) {
        this.usrFtp = usrFtp;
    }

    public String getPwdFtp() {
        return pwdFtp;
    }

    public void setPwdFtp(String pwdFtp) {
        this.pwdFtp = pwdFtp;
    }

    public String getServidorFtp() {
        return servidorFtp;
    }

    public void setServidorFtp(String servidorFtp) {
        this.servidorFtp = servidorFtp;
    }

    public int getPuertoFtp() {
        return puertoFtp;
    }

    public void setPuertoFtp(int puertoFtp) {
        this.puertoFtp = puertoFtp;
    }

    private void txtFechaHora_Nueva_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (UtilityPtoVenta.verificaVK_F11(e)) {
            grabar();
        }
    }
}
