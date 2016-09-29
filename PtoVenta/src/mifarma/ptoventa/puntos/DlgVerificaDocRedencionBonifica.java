package mifarma.ptoventa.puntos;


import com.gs.mifarma.componentes.JLabelFunction;


import farmapuntos.bean.TarjetaBean;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;


import mifarma.ptoventa.main.FrmEconoFar;
import mifarma.ptoventa.fidelizacion.reference.UtilityFidelizacion;
import mifarma.ptoventa.puntos.reference.ConstantsPuntos;
import mifarma.ptoventa.puntos.reference.DBPuntos;
import mifarma.ptoventa.puntos.reference.FacadePuntos;
import mifarma.ptoventa.puntos.reference.UtilityPuntos;
import mifarma.ptoventa.puntos.reference.VariablesPuntos;

import mifarma.ptoventa.reference.ConstantsPtoVenta;

import oracle.jdeveloper.layout.BoxLayout2;
import oracle.jdeveloper.layout.VerticalFlowLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgComplementarios1.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCORTEZ       08.04.2008   Creacion <br>
 * <br>
 * @author  Jorge Cortez Alvarez<br>
 * @version 1.0<br>
 *
 */

public class DlgVerificaDocRedencionBonifica extends JDialog {
    /* ************************************************************************** */
    /*                           DECLARACION PROPIEDADES                          */
    /* ************************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgVerificaDocRedencionBonifica.class);

    private Frame myParentFrame;

    private JPanel panContenedor = new JPanel();
    private JPanel panNorte = new JPanel();
    private JPanel panCentro = new JPanel();
    private JPanel panSur = new JPanel();
    private JPanel panEste = new JPanel();
    private JPanel panOeste = new JPanel();
    
    private JTextField txtTarjeta = new JTextField();
    private JButton btnTarjeta = new JButton();
    
    private JTextField txtDni = new JTextField();
    private JButton btnDni = new JButton();
    
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblEnter = new JLabelFunction();
    
    private JLabel lblTitulo = new JLabel();
    
    private String textTarjeta = "";
    private String textDni = "";
    private String tipoValidacion;
    private boolean isRequiereTarjeta;
    private boolean isRequiereDni;
   
    private String nroDocumento = "";
    private BorderLayout borderLayout1 = new BorderLayout();

    private boolean isCarnetExtranjeria = false;
    
    private boolean isValidoTarjeta = false;
    private boolean isValidoDni = false;
    private JLabel jLabel1 = new JLabel(new ImageIcon(FrmEconoFar.class.getResource("/mifarma/ptoventa/imagenes/DNI_01.jpg")));
    private JPanel pnlParche = new JPanel(); //ASOSA - 16/04/2015 - PTOSYAYAYAYA
    private boolean isMnto = false; //ASOSA - 29/04/2015 - PTOSYAYAYAYA
    private boolean isSolicitaDigitaDNI = false;


    /* ************************************************************************** */
    /*                              CONSTRUCTORES                                 */
    /* ************************************************************************** */

    public DlgVerificaDocRedencionBonifica() {
        
    }

    public DlgVerificaDocRedencionBonifica(Frame parent, String title, boolean modal, String tipoValidacion) {
        super(parent, title, modal);
        myParentFrame = parent;
        this.tipoValidacion = tipoValidacion;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }
    
    /* ************************************************************************** */
    /*                              METODO jbInit                                 */
    /* ************************************************************************** */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(293, 410));
        this.setTitle("Documentos para Redimir/Bonificar");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        
        panContenedor.setBackground(SystemColor.window);
        panContenedor.setLayout(borderLayout1);
        panContenedor.setSize(new Dimension(623, 439));
        panContenedor.setForeground(Color.white);
        
        lblTitulo.setText("Documentos para Redimir/Bonificar");
        lblTitulo.setFont(new Font("SansSerif", 1, 13));

        jLabel1.setBounds(30, 145, 230, 105);    //ASOSA - 16/04/2015 - PTOSYAYAYAYA
        pnlParche.setBounds(new Rectangle(0, 100, 270, 195));
        pnlParche.setBackground(SystemColor.window);
        panNorte.setBackground(new Color(255,255,255));
        panNorte.add(lblTitulo, null);
        
        
        txtTarjeta.setFont(new Font("SansSerif", 1, 13));
        txtTarjeta.setForeground(new Color(32, 105, 29));
        txtTarjeta.setEditable(false);
        txtTarjeta.setBackground(new Color(214, 214, 214));
        txtTarjeta.setBounds(new Rectangle(20, 25, 225, 20));
        txtTarjeta.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtTarjetaKeyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });
        txtTarjeta.addFocusListener(new FocusAdapter(){
            public void focusGained(FocusEvent e){
                txtFieldFocusGained(e);
            }
            public void focusLost(FocusEvent e){
                txtFieldFocusLost(e);
            }
        });
        
        
        btnTarjeta.setText("Escanear Tarjeta: ");
        btnTarjeta.setMnemonic('t');
        btnTarjeta.setFont(new Font("SansSerif", 1, 11));
        btnTarjeta.setDefaultCapable(false);
        btnTarjeta.setRequestFocusEnabled(false);
        btnTarjeta.setBackground(new Color(50, 162, 65));
        btnTarjeta.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnTarjeta.setFocusPainted(false);
        btnTarjeta.setHorizontalAlignment(SwingConstants.LEFT);
        btnTarjeta.setContentAreaFilled(false);
        btnTarjeta.setBorderPainted(false);
        btnTarjeta.setForeground(SystemColor.window);
        btnTarjeta.setBounds(new Rectangle(5, 5, 240, 15));
        btnTarjeta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonActionPerformed(txtTarjeta);
            }
        });
        
        txtDni.setFont(new Font("SansSerif", 1, 13));
        txtDni.setForeground(new Color(32, 105, 29));
        txtDni.setEditable(false);
        txtDni.setBackground(new Color(214, 214, 214));
        txtDni.setEditable(false);
        txtDni.setBounds(new Rectangle(20, 70, 225, 20));
        txtDni.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtDniKeyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                
            }

            public void keyTyped(KeyEvent e) {
                
            }
        });
        txtDni.addFocusListener(new FocusAdapter(){
            public void focusGained(FocusEvent e){
                txtFieldFocusGained(e);
            }
            public void focusLost(FocusEvent e){
                txtFieldFocusLost(e);
            }
        });
        
        btnDni.setText("Escanear documento de identidad");
        btnDni.setMnemonic('d');
        btnDni.setFont(new Font("SansSerif", 1, 11));
        btnDni.setDefaultCapable(false);
        btnDni.setRequestFocusEnabled(false);
        btnDni.setBackground(new Color(50, 162, 65));
        btnDni.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnDni.setFocusPainted(false);
        btnDni.setHorizontalAlignment(SwingConstants.LEFT);
        btnDni.setContentAreaFilled(false);
        btnDni.setBorderPainted(false);
        btnDni.setForeground(SystemColor.window);
        btnDni.setBounds(new Rectangle(5, 50, 240, 15));
        btnDni.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonActionPerformed(txtDni);
            }
        });
        
        panCentro.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        panCentro.setBackground(new Color(43, 141, 39));
        panCentro.setLayout(null);
        panCentro.setForeground(Color.orange);

        //ASOSA - 16/04/2015 - PTOSYAYAYAYA
        //panCentro.add(jLabel1, null);
        panCentro.setBounds(new Rectangle(10, 28, 271, 50));
        pnlParche.add(jLabel1, null);
        panCentro.add(pnlParche, null);
        panCentro.add(btnTarjeta, null);
        panCentro.add(txtTarjeta, null);
        panCentro.add(btnDni, null);
        panCentro.add(txtDni, null);


        lblEnter.setText("[ Enter ] Aceptar");
        lblEnter.setBounds(new Rectangle(165, 5, 110, 20));
        lblEsc.setText("[ Esc ] Salir");
        lblEsc.setBounds(new Rectangle(45, 5, 110, 20));
        
        
        panSur.setBackground(new Color(255,255,255));
        panSur.setLayout(null);
        panSur.setBounds(new Rectangle(0, 200, 291, 35));
        panSur.setPreferredSize(new Dimension(20, 35));

        
        panSur.add(lblEsc, BorderLayout.CENTER);
        panSur.add(lblEnter, BorderLayout.CENTER);
        panEste.setBackground(new Color(255,255,255));
        panOeste.setBackground(new Color(255,255,255));
        
        panContenedor.add(panEste, BorderLayout.EAST);
        panContenedor.add(panOeste, BorderLayout.WEST);
        panContenedor.add(panNorte, BorderLayout.NORTH);
        panContenedor.add(panCentro, BorderLayout.CENTER);
        panContenedor.add(panSur, BorderLayout.SOUTH);
        this.getContentPane().add(panContenedor, BorderLayout.CENTER);
        
    }
    
    private void txtFieldFocusGained(FocusEvent e) {
        ((JTextField)e.getSource()).setBackground(new Color(255,255,255));
        ((JTextField)e.getSource()).setForeground(new Color(32,105,29));
        ((JTextField)e.getSource()).setBorder(BorderFactory.createLineBorder(SystemColor.windowText, 1));
    }

    private void txtFieldFocusLost(FocusEvent e) {
        ((JTextField)e.getSource()).setBackground(new Color(214, 214, 214));
        ((JTextField)e.getSource()).setForeground(Color.BLACK);
        ((JTextField)e.getSource()).setBorder(null);
    }
    
    /* ************************************************************************** */
    /*                            METODO initialize                               */
    /* ************************************************************************** */

    private void initialize() {
        FarmaVariables.vAceptar = false;
    }
    
    /* ************************************************************************** */
    /*                            METODOS DE EVENTOS                              */
    /* ************************************************************************** */

    private void this_windowOpened(WindowEvent e) {
        
        //FarmaUtility.moveFocus(txtProducto);
        if(tipoValidacion.equalsIgnoreCase(ConstantsPuntos.REDENCION_PTOS)){ // RENDICION
            this.setTitle("Documentos para Canjear");
            lblTitulo.setText("Documentos para Canjear");
        }else{
            if(tipoValidacion.equalsIgnoreCase(ConstantsPuntos.BONIFICACION_PRODUCTOS)){ // BONIFICA PROD
                this.setTitle("Documentos para Bonificar");
                lblTitulo.setText("Documentos para Bonificar");
            }else{
                if(tipoValidacion.equalsIgnoreCase(ConstantsPuntos.SOLICITA_TARJ_OFFLINE)){ // VENTA OFFLINE
                    this.setTitle("Documentos para venta");
                    lblTitulo.setText("SOLICITE TARJETA PUNTOS");
                    //FarmaUtility.showMessage(this, "Solicite la tarjeta del Programa de Puntos", txtTarjeta);
                } else {
                    if(tipoValidacion.equalsIgnoreCase(ConstantsPuntos.MNTO_FIDELIZACION)){ // MNTO DE FIDELIZADO ASOSA - 28/04/2015 - PTOSYAYAYAYA
                        this.setTitle("Verificación de DNI");
                        lblTitulo.setText("Verificación de DNI");
                    }else{
                        if(tipoValidacion.equalsIgnoreCase(ConstantsPuntos.VALIDA_TARJETA_ASOCIADA)){ // VENTA OFFLINE
                            this.setTitle("Documentos para venta");
                            lblTitulo.setText("SOLICITE TARJETA PUNTOS");
                            //FarmaUtility.showMessage(this, "Solicite la tarjeta del Programa de Puntos", txtTarjeta);
                        }else{
                            if(tipoValidacion.equalsIgnoreCase(ConstantsPuntos.BLOQUEO_TARJETA)){ // BLOQUEO DE TARJETA
                                this.setTitle("Bloqueo de Tarjeta Monedero");
                                lblTitulo.setText("Bloqueo de Tarjeta Monedero");
                            }
                        }
                    }
                }
            }
        }
        
        txtTarjeta.setText("");
        txtDni.setText("");
        
        txtTarjeta.setVisible(isRequiereTarjeta);
        btnTarjeta.setVisible(isRequiereTarjeta);
        txtDni.setVisible(isRequiereDni);
        btnDni.setVisible(isRequiereDni);
        txtTarjeta.setText(UtilityPuntos.enmascararTarjeta(textTarjeta));
        //txtDni.setText(textDni);
        maxTiempoLectora = UtilityPuntos.obtieneTiempoMaximoLectora();
        if(isRequiereTarjeta){
            txtTarjeta.requestFocus();
            txtTarjeta.setEditable(false);
            isValidoTarjeta = false;
        }else{
            isValidoTarjeta = true;
            if(isRequiereDni){
                txtDni.requestFocus();
            }
        }
        //KMONCADA 25.06.2015
        if(tipoValidacion.equalsIgnoreCase(ConstantsPuntos.BONIFICACION_PRODUCTOS) || tipoValidacion.equalsIgnoreCase(ConstantsPuntos.REDENCION_PTOS)){
            if(VariablesPuntos.frmPuntos != null && VariablesPuntos.frmPuntos.getTarjetaBean() != null){
                TarjetaBean tarjetaPuntos = VariablesPuntos.frmPuntos.getTarjetaBean();
                FacadePuntos facade = new FacadePuntos();
                if(facade.validacionDigitaDNI()){
                    if(tarjetaPuntos.getDeslizaTarjeta()){
                        isSolicitaDigitaDNI = true;
                    }else{
                        isSolicitaDigitaDNI = false;
                    }
                }else{
                    isSolicitaDigitaDNI = false;
                }
            }
        }
        
        if(isRequiereDni){
            isValidoDni = false;
            isCarnetExtranjeria = false;
            if(nroDocumento.trim().length() == 8){
                if(isSolicitaDigitaDNI){
                    btnDni.setText("Digite DNI");
                    txtDni.setEditable(true);
                }else{
                    btnDni.setText("Escanear DNI");
                    txtDni.setEditable(false);
                }
                //txtDni.setEditable(false);
            }else{
                //if(nroDocumento.trim().length() == 9){
                btnDni.setText("Digite Carnet de Extranjería");
                txtDni.setEditable(true);
                isCarnetExtranjeria = true;
                //}
            }
        }else{
            isValidoDni = true;
        }
        
        if(txtTarjeta.getText().length()>0){
           txtDni.requestFocus();
        }
        
        if (isRequiereDni && isRequiereTarjeta){
            this.setSize(new Dimension(293, 190));
        }else{
            this.setSize(new Dimension(293, 160));
            if(isRequiereDni){
                
                btnDni.setBounds(new Rectangle(5, 5, 240, 15));
                txtDni.setBounds(new Rectangle(20, 25, 225, 20));
            }
        }
        if (isRequiereDni && !isCarnetExtranjeria && !isSolicitaDigitaDNI )
            insertarImagenDni();    //ASOSA - 16/04/2015 - PTOSYAYAYAYA

        FarmaUtility.centrarVentana(this);
    }
    
    /**
     * Cambia tamaño del panel y posicion del label de la imagen para que encaje bien.
     * @author ASOSA
     * @since 16/04/2015
     * @type PTOSYAYAYAYA
     */
    private void insertarImagenDni () {
        /*
        if((isRequiereDni && !isRequiereTarjeta) || (isRequiereTarjeta && !isRequiereDni)){
            this.setBounds(10, 10, 305, 380);            
            pnlParche.setBounds(0, 55, 270, 225);
            jLabel1.setBounds(0, 55, 270, 210);
            log.info("primer if");
        }
        if(isRequiereTarjeta && isRequiereDni){
            this.setBounds(10, 10, 305, 420);            
            pnlParche.setBounds(0, 100, 270, 225);
            jLabel1.setBounds(0, 100, 270, 210);
            log.info("segundo if");
        }
        */
        if((isRequiereDni && !isRequiereTarjeta) || (isRequiereTarjeta && !isRequiereDni)){
            this.setBounds(10, 10, 305, 380);            
            pnlParche.setBounds(0, 55, 297, 247);
            jLabel1.setBounds(0, 55, 297, 231);
            log.info("primer if");
        }
        if(isRequiereTarjeta && isRequiereDni){
            this.setBounds(10, 10, 305, 420);            
            pnlParche.setBounds(0, 100, 297, 247);
            jLabel1.setBounds(0, 100, 297, 231);
            log.info("segundo if");
        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    // **************************************************************************
    // Marco Fajardo: Cambio realizado el 21/04/09 - evitar le ejecucion de 2 teclas a la vez
    // **************************************************************************
    
    private boolean isLectoraLaser;
    private boolean isEnter;
    private boolean isCodigoBarra;
    private long tiempoTeclaInicial;
    private long tiempoTeclaFinal;
    private int maxTiempoLectora;
    
    private boolean isNumero(char ca) {
        int numero  = 0;
        try {
            numero = Integer.parseInt(ca + "");
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
    
    private void txtTarjetaKeyPressed(KeyEvent e) {

        if(e.getKeyCode() != KeyEvent.VK_ENTER){
            textTarjeta = textTarjeta + e.getKeyChar();
        }
        if(isNumero(e.getKeyChar()) || e.getKeyCode() == KeyEvent.VK_ENTER ){
            if (isLectoraLaser) {
                isLectoraLaser = false;
            }
            isEnter = false;
            isLectoraLaser = false;
            if (tiempoTeclaInicial == 0){
                tiempoTeclaInicial = System.currentTimeMillis();
            }
            isCodigoBarra = true;
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                
                isLectoraLaser = false;
                tiempoTeclaFinal = System.currentTimeMillis();
                log.info("Tiem 2 " + (tiempoTeclaInicial));
                log.info("Tiem 1 " + (tiempoTeclaFinal));
                log.info("Tiempo de ingreso y ENTER " + (tiempoTeclaFinal - tiempoTeclaInicial));
                boolean validaFinal = true;
                for(int k=0;k<textTarjeta.length();k++){
                    validaFinal = validaFinal && isNumero(textTarjeta.toCharArray()[k]);
                }
                if ((tiempoTeclaFinal - tiempoTeclaInicial) <= maxTiempoLectora && textTarjeta.length() > 0 && validaFinal) {
                    isLectoraLaser = true;
                    tiempoTeclaInicial = 0;
                    log.info("ES CODIGO DE BARRA");
                    isCodigoBarra = true;
                    isEnter = true;
                    txtTarjeta.setText(UtilityPuntos.enmascararTarjeta(textTarjeta));
                    validarDatos(e);
                        //txtProducto_keyPressed2(e);
                    
                    
                } else {
                    isLectoraLaser = false;
                    tiempoTeclaInicial = 0;
                    log.info("FLUJO NORMAL");
                    isCodigoBarra = false;
                    chkKeyPressed(e);
                    txtTarjeta.setText("");
                    textTarjeta = "";
                    //txtProducto_keyPressed2(e);
                }
                //FarmaUtility.moveFocus(txtTarjeta);
            }
        }else{
            isCodigoBarra = false;
            log.info("FLUJO NORMAL");
            //txtProducto_keyPressed2(e);
            chkKeyPressed(e);
            txtTarjeta.setText("");
            textTarjeta = "";
        }
    }
    
    private void txtDniKeyPressed(KeyEvent e) {

        if(e.getKeyCode() != KeyEvent.VK_ENTER){
            textDni = textDni + e.getKeyChar();
        }
        if(isNumero(e.getKeyChar()) || e.getKeyCode() == KeyEvent.VK_ENTER ){
            if (isLectoraLaser) {
                isLectoraLaser = false;
            }
            isEnter = false;
            isLectoraLaser = false;
            if (tiempoTeclaInicial == 0){
                tiempoTeclaInicial = System.currentTimeMillis();
            }
            isCodigoBarra = true;
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                //int maxTiempoLectora = UtilityPuntos.obtieneTiempoMaximoLectora();
                isLectoraLaser = false;
                tiempoTeclaFinal = System.currentTimeMillis();
                log.info("Tiem 2 " + (tiempoTeclaInicial));
                log.info("Tiem 1 " + (tiempoTeclaFinal));
                log.info("Tiempo de ingreso y ENTER " + (tiempoTeclaFinal - tiempoTeclaInicial));
                boolean validaFinal = true;
                for(int k=0;k<textDni.length();k++){
                    validaFinal = validaFinal && isNumero(textDni.toCharArray()[k]);
                }
                if(textDni.length()==9 || isSolicitaDigitaDNI){
                    maxTiempoLectora = Integer.MAX_VALUE;
                }
                if ((tiempoTeclaFinal - tiempoTeclaInicial) <= maxTiempoLectora && textDni.length() > 0 && validaFinal) {
                    isLectoraLaser = true;
                    tiempoTeclaInicial = 0;
                    log.info("ES CODIGO DE BARRA");
                    isCodigoBarra = true;
                    isEnter = true;
                    txtDni.setText(textDni);
                    validarDatos(e);
                        //txtProducto_keyPressed2(e);
                    
                    
                } else {
                    isLectoraLaser = false;
                    tiempoTeclaInicial = 0;
                    log.info("FLUJO NORMAL");
                    isCodigoBarra = false;
                    if(isCarnetExtranjeria){
                        txtDni.setText(textDni);
                        validarDatos(e);
                    }else
                        chkKeyPressed(e);
                    txtDni.setText("");
                    textDni = "";
                    isValidoDni = false;
                    
                    //txtProducto_keyPressed2(e);
                }
                //FarmaUtility.moveFocus(txtTarjeta);
            }
        }else{
            isCodigoBarra = false;
            log.info("FLUJO NORMAL");
            //txtProducto_keyPressed2(e);
            chkKeyPressed(e);
            txtDni.setText("");
            textDni = "";
            isValidoDni = false;
            
        }
    }
    
    private void validarDatos(KeyEvent e){

        String nroDocumentoAux = "";
        
        if(isRequiereTarjeta){
            
            if(txtTarjeta.getText().trim().length()==0){
                txtTarjeta.setText("");
                textTarjeta = "";
                FarmaUtility.moveFocus(txtTarjeta);
                return ;
            }
            
            if(ConstantsPuntos.SOLICITA_TARJ_OFFLINE.equalsIgnoreCase(tipoValidacion)){
                if(!UtilityPuntos.isTarjetaValida(textTarjeta)){
                    FarmaUtility.showMessage(this, "Tarjeta Invalida.", txtTarjeta);
                    txtTarjeta.setText("");
                    textTarjeta = "";
                    FarmaUtility.moveFocus(txtTarjeta);
                    return;
                }else{
                    if(DBPuntos.validaTarjetaAsociada(nroDocumento, textTarjeta)){
                        FarmaUtility.showMessage(this, "Tarjeta no pertenece al cliente antes ingresado, verifique!!!", txtTarjeta);
                        txtTarjeta.setText("");
                        textTarjeta = "";
                        FarmaUtility.moveFocus(txtTarjeta);
                        return;
                    }
                }
            }else{
                if(ConstantsPuntos.BLOQUEO_TARJETA.equalsIgnoreCase(tipoValidacion)){
                    if(!UtilityPuntos.isTarjetaValida(textTarjeta)){
                        FarmaUtility.showMessage(this, "Tarjeta Invalida.", txtTarjeta);
                        txtTarjeta.setText("");
                        textTarjeta = "";
                        FarmaUtility.moveFocus(txtTarjeta);
                        return;
                    }
                    // KMONCADA 17.07.2015 TARJETA DE CLUB DE PROPIETARIOS Y/O CLUB DE DESCUENTOS NO SE BLOQUEAN
                    if(UtilityPuntos.isTarjetaOtroPrograma(textTarjeta, true)){
                        FarmaUtility.showMessage(this, "Este tipo de tarjeta no se puede bloquear.", txtTarjeta);
                        txtTarjeta.setText("");
                        textTarjeta = "";
                        FarmaUtility.moveFocus(txtTarjeta);
                        return;
                    }
                    FacadePuntos facade = new FacadePuntos();
                    boolean isSolicitaClave = facade.isSolicitaClaveQuimicoBloqueoTarjeta();
                    boolean isContinuaBloqueo = !isSolicitaClave;
                    if(isSolicitaClave){
                        isContinuaBloqueo = cargarLogeoAdmLocal();
                    }
                    if(isContinuaBloqueo){
                        UtilityPuntos.bloqueoTarjeta(this, textTarjeta, txtTarjeta);
                    }
                }else{
                    try{
                        nroDocumentoAux = UtilityPuntos.obtenerEstadoTarjeta(myParentFrame, this, textTarjeta, tipoValidacion, true, nroDocumento);
                    }catch(Exception ex){
                        log.error("", ex);
                        FarmaUtility.showMessage(this, ex.getMessage(), txtTarjeta);
                        txtTarjeta.setText("");
                        textTarjeta = "";
                        FarmaUtility.moveFocus(txtTarjeta);
                        return;
                    }
                    if(nroDocumentoAux != null){
                        if(!nroDocumentoAux.trim().equalsIgnoreCase(nroDocumento)){
                            FarmaUtility.showMessage(this, "Tarjeta no pertenece al cliente antes ingresado, verifique!!!", txtTarjeta);
                            txtTarjeta.setText("");
                            textTarjeta = "";
                            FarmaUtility.moveFocus(txtTarjeta);
                            return;
                        }
                        VariablesPuntos.frmPuntos.getTarjetaBean().setDeslizaTarjeta(true);
                    }else{
                        FarmaUtility.showMessage(this, "Error al consultar estado de tarjeta", txtTarjeta);
                        txtTarjeta.setText("");
                        textTarjeta = "";
                        FarmaUtility.moveFocus(txtTarjeta);
                        return;
                    }
                }
            }
        }
        
        if(isRequiereDni){
            if (!isMnto()) {    //ASOSA - 28/04/2015 - PTOSYAYAYAYA
                
                if(txtDni.getText().trim().length()==0){
                    txtDni.setText("");
                    textDni = "";
                    FarmaUtility.moveFocus(txtDni);
                    return ;
                }
                
                if (UtilityFidelizacion.validarDocIdentidad(textDni)) {
                    if(!nroDocumento.equalsIgnoreCase(textDni)){
                        FarmaUtility.showMessage(this, "Nro Documentos ingresados no coinciden, verifique!!!", txtDni);
                        txtDni.setText("");
                        textDni = "";
                        return;
                    }
                    VariablesPuntos.frmPuntos.getTarjetaBean().setEscaneaDNI(true);
                }else{
                    FarmaUtility.showMessage(this, "Nro Documento invalido, verifique!!!", txtDni);
                    txtDni.setText("");
                    textDni = "";
                    return;
                }
            //INI ASOSA - 28/04/2015 - PTOSYAYAYAYA
            } else {                
                if (nroDocumento.equalsIgnoreCase(textDni.trim())) {
                    FarmaUtility.showMessage(this, 
                                             "Validacion correcta.", 
                                             null);
                    cerrarVentana(true);
                } else {
                    FarmaUtility.showMessage(this, 
                                             "El documento escaneado no coincide, volver a ingresar", 
                                             null);                    
                    txtDni.setText("");
                    textDni = "";
                    return;
                }
            }
            //FIN ASOSA - 28/04/2015 - PTOSYAYAYAYA
        }  
        
        if(!ConstantsPuntos.BLOQUEO_TARJETA.equalsIgnoreCase(tipoValidacion)){
            if (!isMnto()) { //ASOSA - 28/04/2015 - PTOSYAYAYAYA
                FarmaUtility.showMessage(this, "Validacion correcta.", null);
            }        
        }
        cerrarVentana(true);
        /* limpiaCadenaAlfanumerica(txtProducto);
        textProducto = txtProducto.getText().trim();
        try {
            e.consume();
            if (UtilityFidelizacion.validarDocIdentidad(txtProducto.getText().trim())) {
                nroDocumento = textProducto.trim();
                cerrarVentana(true);
            }else{
                FarmaUtility.showMessage(this, "Nro Documento invalido.", txtProducto);
                txtProducto.setText("");
                textProducto = "";
            }
        } catch (Exception sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al leer documento.\n" + sql, txtProducto);
            textProducto = "";
            txtProducto.setText("");
        } */
    }
    
    private void buttonActionPerformed(JTextField objTransFocus) {
        FarmaUtility.moveFocus(objTransFocus);
    }


    /* ************************************************************************** */
    /*                       METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************** */

    
    private void chkKeyPressed(KeyEvent e) {
         if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    
    public void setNroDocumento(String nroDocumento){
        this.nroDocumento = nroDocumento;
    }


    public void setTextTarjeta(String textTarjeta) {
        this.textTarjeta = textTarjeta;
    }

    public String getTextTarjeta() {
        return textTarjeta;
    }

    public void setTextDni(String textDni) {
        this.textDni = textDni;
    }

    public String getTextDni() {
        return textDni;
    }

    public void setTipoValidacion(String tipoValidacion) {
        this.tipoValidacion = tipoValidacion;
    }

    public String getTipoValidacion() {
        return tipoValidacion;
    }

    public void setIsRequiereTarjeta(boolean isRequiereTarjeta) {
        this.isRequiereTarjeta = isRequiereTarjeta;
    }

    public boolean isIsRequiereTarjeta() {
        return isRequiereTarjeta;
    }

    public void setIsRequiereDni(boolean isRequiereDni) {
        this.isRequiereDni = isRequiereDni;
    }

    public boolean isIsRequiereDni() {
        return isRequiereDni;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public boolean isMnto() {
        return isMnto;
    }

    public void setMnto(boolean mnto) {
        isMnto = mnto;
    }
    
    private boolean cargarLogeoAdmLocal() {
        String numsec = FarmaVariables.vNuSecUsu;
        String idusu = FarmaVariables.vIdUsu;
        String nomusu = FarmaVariables.vNomUsu;
        String apepatusu = FarmaVariables.vPatUsu;
        String apematusu = FarmaVariables.vMatUsu;

        try {
            DlgLogin dlgLogin = new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
            dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
            dlgLogin.setVisible(true);
        } catch (Exception e) {
            FarmaVariables.vAceptar = false;
            log.error("ERROR", e);
            FarmaUtility.showMessage(this, "Ocurrio un error al validar rol de usuario \n : " + e.getMessage(), null);
        } finally {
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
        }
        return FarmaVariables.vAceptar;
    }
}
