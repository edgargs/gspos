package mifarma.ptoventa.lectorHuella;


import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPFingerIndex;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPErrorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPErrorEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.capture.event.DPFPSensorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPSensorEvent;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import com.digitalpersona.onetouch.verification.DPFPVerification;

import com.digitalpersona.onetouch.verification.DPFPVerificationResult;

import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.lectorHuella.modelo.UsuarioFV;
import mifarma.ptoventa.lectorHuella.reference.UtilityLectorHuella;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgRegistroLectorHuella extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgRegistroLectorHuella.class);
    private Frame MyParentFrame;
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel pnlImagenHuella = new JPanel();
    private BorderLayout borderLayout2 = new BorderLayout();
    private JLabel lblImagenHuella = new JLabel();
    private JPanelTitle pnlDatosUsuario = new JPanelTitle();
    private JLabelWhite lblApPaterno = new JLabelWhite();
    private JLabelWhite lblApMaterno = new JLabelWhite();
    private JLabelWhite lblNombres = new JLabelWhite();
    private JLabelWhite lblDniUsuario = new JLabelWhite();
    private JLabelWhite lblSecUsuario = new JLabelWhite();
    private JTextFieldSanSerif txtApPaterno = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtApMaterno = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtNombres = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtDniUsuario = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtSecUsuario = new JTextFieldSanSerif();


    //Varible que permite iniciar el dispositivo de lector de huella conectado
    // con sus distintos metodos.
    private DPFPCapture lectorHuella = DPFPGlobal.getCaptureFactory().createCapture();

    //Varible que permite establecer las capturas de la huellas, para determina sus caracteristicas
    // y poder estimar la creacion de un template de la huella para luego poder guardarla
    private DPFPEnrollment Reclutador = DPFPGlobal.getEnrollmentFactory().createEnrollment();

    //Esta variable tambien captura una huella del lector y crea sus caracteristcas para auntetificarla
    // o verificarla con alguna guardada en la BD
    private DPFPVerification Verificador = DPFPGlobal.getVerificationFactory().createVerification();

    //Variable que para crear el template de la huella luego de que se hallan creado las caracteriticas
    // necesarias de la huella si no ha ocurrido ningun problema
    private DPFPTemplate template;
    public static String TEMPLATE_PROPERTY = "template";

    public DPFPFeatureSet featuresinscripcion;
    public DPFPFeatureSet featuresverificacion;
    private JPanel jPanel1 = new JPanel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTextArea txtResultados = new JTextArea();
    private JLabelFunction lblSalir = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    
    private boolean isRegistro = false;
    private String pSecUsuLocal;
    private JLabel lblIndicaciones = new JLabel();
    private int totalHuellas = 0;
    private UsuarioFV usuarioConsulta;
    private int multiplicaHuella = 4;
    private List<UsuarioFV> listaUsuarios;
    private DPFPFingerIndex posicionHuella;

    public DlgRegistroLectorHuella() {
        this(null, "", false, "", true, null);
    }

    public DlgRegistroLectorHuella(Frame parent, String title, boolean modal, String secUsuario, boolean isRegistro, DPFPFingerIndex posicionHuella) {
        super(parent, title, modal);
        try {
            this.isRegistro = isRegistro;
            this.pSecUsuLocal = secUsuario;
            MyParentFrame = parent;
            this.posicionHuella = posicionHuella;
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(510, 391));
        this.getContentPane().setLayout(borderLayout1);
        jContentPane.setFocusable(false);
        //this.setTitle("Lector de Huella");
        this.setFocusable(true);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }

            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        
        // IMAGEN DE HUELLA
        lblImagenHuella.setBackground(SystemColor.window);
        
        pnlImagenHuella.setBounds(new Rectangle(15, 35, 205, 180));
        pnlImagenHuella.setLayout(borderLayout2);
        pnlImagenHuella.setBorder((BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
                                                                                                    new Color(43, 141,
                                                                                                              39)),
                                                                    "Huella Digital",
                                                                    TitledBorder.DEFAULT_JUSTIFICATION,
                                                                    TitledBorder.DEFAULT_POSITION,
                                                                    new Font("SansSerif", 1, 12),
                                                                    new Color(43, 141, 39))));
        pnlImagenHuella.setOpaque(false);


        // DATOS DE USUARIO
        pnlImagenHuella.add(lblImagenHuella, BorderLayout.CENTER);
        jContentPane.add(lblIndicaciones, null);
        jContentPane.add(pnlImagenHuella, null);
        jContentPane.add(pnlDatosUsuario, null);
        jScrollPane1.getViewport().add(txtResultados, null);
        jPanel1.add(jScrollPane1, null);
        jContentPane.add(jPanel1, null);
        jContentPane.add(lblSalir, null);
        jContentPane.add(lblF11, null);
        lblDniUsuario.setText("DNI: ");
        lblDniUsuario.setBounds(new Rectangle(10, 25, 70, 20));
        lblDniUsuario.setForeground(new Color(255, 130, 14));

        txtDniUsuario.setBounds(new Rectangle(80, 25, 155, 20));
        txtDniUsuario.setEditable(false);
        txtDniUsuario.setFocusable(false);

        lblSecUsuario.setText("Codigo: ");
        lblSecUsuario.setBounds(new Rectangle(10, 55, 70, 20));
        lblSecUsuario.setForeground(new Color(255, 130, 14));

        txtSecUsuario.setBounds(new Rectangle(80, 55, 155, 20));
        txtSecUsuario.setEditable(false);
        txtSecUsuario.setFocusable(false);

        lblApPaterno.setText("Ap.Paterno:");
        lblApPaterno.setBounds(new Rectangle(10, 85, 70, 20));
        lblApPaterno.setForeground(new Color(255, 130, 14));

        txtApPaterno.setBounds(new Rectangle(80, 85, 155, 20));
        txtApPaterno.setEditable(false);
        txtApPaterno.setFocusable(false);

        lblApMaterno.setText("Ap.Materno:");
        lblApMaterno.setBounds(new Rectangle(10, 115, 70, 20));
        lblApMaterno.setForeground(new Color(255, 130, 14));

        txtApMaterno.setBounds(new Rectangle(80, 115, 155, 20));
        txtApMaterno.setEditable(false);
        txtApMaterno.setFocusable(false);

        lblNombres.setText("Nombre:");
        lblNombres.setBounds(new Rectangle(10, 145, 70, 20));
        lblNombres.setForeground(new Color(255, 130, 14));

        txtNombres.setBounds(new Rectangle(80, 145, 155, 20));
        txtNombres.setEditable(false);
        txtNombres.setFocusable(false);

        pnlDatosUsuario.setBounds(new Rectangle(235, 35, 250, 180));
        pnlDatosUsuario.setBorder((BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
                                                                                                    new Color(43, 141,
                                                                                                              39)),
                                                                    "Datos de Usuario",
                                                                    TitledBorder.DEFAULT_JUSTIFICATION,
                                                                    TitledBorder.DEFAULT_POSITION,
                                                                    new Font("SansSerif", 1, 12),
                                                                    new Color(43, 141, 39))));
        pnlDatosUsuario.setBackground(Color.white);

        pnlDatosUsuario.add(lblDniUsuario);
        pnlDatosUsuario.add(txtDniUsuario);
        pnlDatosUsuario.add(lblSecUsuario);
        pnlDatosUsuario.add(txtSecUsuario);

        pnlDatosUsuario.add(lblApPaterno);
        pnlDatosUsuario.add(txtApPaterno);
        pnlDatosUsuario.add(lblApMaterno);
        pnlDatosUsuario.add(txtApMaterno);
        pnlDatosUsuario.add(lblNombres);

        // PANEL DE RESULTADOS
        pnlDatosUsuario.add(txtNombres);
        txtResultados.setFocusable(false);
        txtResultados.setFont(new Font("Tahoma", 0, 9));

        txtResultados.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jScrollPane1.setBounds(new Rectangle(5, 15, 450, 80));
        jScrollPane1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jPanel1.setBounds(new Rectangle(20, 225, 460, 100));
        jPanel1.setLayout(null);
        jPanel1.setBorder((BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
                                                                                            new Color(43, 141, 39)),
                                                            "Estado", TitledBorder.DEFAULT_JUSTIFICATION,
                                                            TitledBorder.DEFAULT_POSITION,
                                                            new Font("SansSerif", 1, 12), new Color(43, 141, 39))));
        jPanel1.setOpaque(false);

        // BOTONES DE FUNCION
        lblSalir.setBounds(new Rectangle(270, 335, 85, 20));
        lblSalir.setText("[ESC] Cerrar");

        lblF11.setBounds(new Rectangle(365, 335, 105, 20));
        lblF11.setText("[F11] Grabar");

        lblIndicaciones.setText("jLabel1");
        lblIndicaciones.setBounds(new Rectangle(20, 15, 465, 15));
        this.getContentPane().add(jContentPane, null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                chkKeyPressed(e);
            }
        });
    }
    
    private void initialize(){
        if(!isRegistro){
            lblF11.setText("[F11] Continuar");
        }else{
            lblF11.setText("[F11] Grabar");
        }
        listaUsuarios = UtilityLectorHuella.obtenerListaUsuario();
    }
    
    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        iniciar();
        start();
        lblF11.setEnabled(false);
        cargarDatos(this.pSecUsuLocal);
        if(!isRegistro){
            boolean isTieneHuellaRegistrada = false;
            
            for (DPFPFingerIndex finger : DPFPFingerIndex.values()) {
                DPFPTemplate template = usuarioConsulta.getTemplate(finger);
                if (template != null) {
                    isTieneHuellaRegistrada = true;
                }
            }
            if(!isTieneHuellaRegistrada){
                FarmaUtility.showMessage(this, "Lector de Huellas:\n"+
                                               "Usuario no ha registrado aún su huella dactilar.\n"+
                                               "Registrelo mediante el menu de Mantenimiento/Usuario", null);
                cerrarVentana(false);
            }
        }else{
            multiplicaHuella = UtilityLectorHuella.obtenerCantidadRepiteHuella();
        }
    }
    
    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    
    private void cerrarVentana(boolean pAceptar) {
        //lectorHuella.stopCapture();
        stop();
        FarmaVariables.vAceptar = pAceptar;
        if(!pAceptar){
            pSecUsuLocal = "";
        }
        this.setVisible(false);
        this.dispose();
    }
    
    private void cargarDatos(String pSecUsu){
        usuarioConsulta = UtilityLectorHuella.obtenerUsuario(this, pSecUsu);
        boolean isCambia = false;
        if(usuarioConsulta==null){
            FarmaUtility.showMessage(this, "Lector de Huella:\n"+
                                           "No se pudo obtener datos de usuario, reintente.\n"+
                                           "Si persiste comuniquese con Mesa de Ayuda.", null);
            cerrarVentana(false);
        }else{
            if(isRegistro){
                if(usuarioConsulta.getTemplate(posicionHuella) != null ){
                    isCambia = JConfirmDialog.rptaConfirmDialog(this, "Lector de Huella:\nUsuario ya registro su huella dactilar.\n"+
                                                       "¿Desea Cambiarlo?");
                }else{
                    isCambia = true;
                }
            }else{
                isCambia = true;
            }
            
            if(isCambia){
                mostrarDatos(usuarioConsulta);
            }else{
                stop();
                enviarTexto("[INACTIVO]: Se desactivo lector de huella", "Lector de Huella desactivado.");
            }
        }
    }
    
    private void mostrarDatos(UsuarioFV usuario){
        txtSecUsuario.setText(usuario.getSecUsuLocal());
        txtDniUsuario.setText(usuario.getNroDocumento());
        txtApPaterno.setText(usuario.getApellidoPaterno());
        txtApMaterno.setText(usuario.getApellidoMaterno());
        txtNombres.setText(usuario.getNombres());
        pSecUsuLocal = usuario.getSecUsuLocal();
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }else if (UtilityPtoVenta.verificaVK_F11(e) && lblF11.isEnabled()) {
            if(isRegistro){
                registrarHuella();
            }else{
                cerrarVentana(true);
            }
        }
    }
    
    private void enviarTexto(String textoLog, String textoMuestra){
        String textoAnt = txtResultados.getText();
        if(textoAnt.trim().length()>0){
            textoAnt = textoAnt + "\n";
        }
        txtResultados.setText(textoAnt+textoLog);
        //txtResultados.setText(textoMuestra);
        log.info("[LECTOR DE HUELLAS]"+textoLog);
        if(textoMuestra != null){
            lblIndicaciones.setText(textoMuestra);
        }
    }
    
    private boolean isExisteHuella(){
        boolean encontrado = false;
        DPFPVerification matcher = DPFPGlobal.getVerificationFactory().createVerification();
        matcher.setFARRequested(DPFPVerification.MEDIUM_SECURITY_FAR);
        UsuarioFV usuarioEncontrado = null;
        for(int i=0;i<listaUsuarios.size();i++){
            UsuarioFV usuario = listaUsuarios.get(i);
            boolean isRealizaValidacion = true;
            if(!isRegistro && !usuario.getSecUsuLocal().equalsIgnoreCase(usuarioConsulta.getSecUsuLocal())){
                isRealizaValidacion = false;
            }
            if(isRealizaValidacion){
                for (DPFPFingerIndex finger : DPFPFingerIndex.values()) {
                    if(!(isRegistro && usuario.getSecUsuLocal().equalsIgnoreCase(usuarioConsulta.getSecUsuLocal()) && finger.ordinal()==posicionHuella.ordinal())){
                        DPFPTemplate template = usuario.getTemplate(finger);
                        if (template != null) {
                            DPFPVerificationResult result = matcher.verify(featuresverificacion, template);
                            if (result.isVerified()) {
                                usuarioEncontrado = usuario;
                                encontrado = true;
                            }
                        }
                    }
                }
            }
        }
        if(encontrado){
            FarmaUtility.showMessage(this, "Lector de Huella:\n"+
                                           "Huella escaneada, coincide con el usuario: \n"+
                                           usuarioEncontrado.getApellidoPaterno()+" "+usuarioEncontrado.getApellidoMaterno()+" "+usuarioEncontrado.getNombres(), null);
        }
        return encontrado;
    }
    
    private void registrarHuella(){
        try{
            if(!isExisteHuella()){
                UtilityLectorHuella.guardarHuella(template, pSecUsuLocal, posicionHuella.ordinal());
                FarmaUtility.showMessage(this, "Lector Huellas:\nSe registro su huella dactilar con exito.", null);
                Reclutador.clear();
                lblImagenHuella.setIcon(null);
                cerrarVentana(true);
            }else{
                Reclutador.clear();
                lblImagenHuella.setIcon(null);
                estadoHuellas();
                lblF11.setEnabled(false);
                start();
            }
        }catch(Exception ex){
            FarmaUtility.showMessage(this, "Lector Huellas:\nSe produjo un error\n"+ex.getMessage(), null);
        }
    }
    
    protected void iniciar() {
        lectorHuella.addDataListener(new DPFPDataAdapter() {
            @Override
            public void dataAcquired(final DPFPDataEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        enviarTexto("[CAPTURA] : La Huella Digital ha sido Capturada", null);
                        procesarCaptura(e.getSample());
                    }
                });
            }
        });

        lectorHuella.addReaderStatusListener(new DPFPReaderStatusAdapter() {
            @Override
            public void readerConnected(final DPFPReaderStatusEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        enviarTexto("[INICIO] : El Sensor de Huella Digital esta Activado o Conectado", null);
                        estadoHuellas();
                    }
                });
            }

            @Override
            public void readerDisconnected(final DPFPReaderStatusEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        //txtResultados.setText("El Sensor de Huella Digital esta Desactivado o no Conectado");
                        enviarTexto("[ERROR] : El Sensor de Huella Digital esta Desactivado o no Conectado",
                                    "El Sensor de Huella Digital esta Desactivado");
                    }
                });
            }
        });

        lectorHuella.addSensorListener(new DPFPSensorAdapter() {
            @Override
            public void fingerTouched(final DPFPSensorEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        //enviarTexto("El dedo ha sido colocado sobre el Lector de Huella");
                    }
                });
            }

            @Override
            public void fingerGone(final DPFPSensorEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        //enviarTexto("El dedo ha sido quitado del Lector de Huella");
                    }
                });
            }
        });

        lectorHuella.addErrorListener(new DPFPErrorAdapter() {
            public void errorReader(final DPFPErrorEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        enviarTexto("Error: "+e.getError(), e.toString());
                    }
                });
            }
        });
    }

    public void start() {
        try{
            lectorHuella.startCapture();
        }catch(Exception ex){
            FarmaUtility.showMessage(this, "Lector de Huellas:\n"+
                                           "Error al iniciar el lector, verifique que este conectado a la PC.\n"+
                                           "Reintente, si problema persiste comuniquese con Mesa de Ayuda.", null);
            cerrarVentana(false);
        }
    }

    public void stop() {
        lectorHuella.stopCapture();
        enviarTexto("[STOP] : No se está usando el Lector de Huella Dactilar ", null);
    }

    public DPFPTemplate getTemplate() {
        return template;
    }

    private void setTemplate(DPFPTemplate template) {
        DPFPTemplate old = this.template;
        this.template = template;
        firePropertyChange(TEMPLATE_PROPERTY, old, template);
    }

    public Image crearImagenHuella(DPFPSample sample) {
        return DPFPGlobal.getSampleConversionFactory().createImage(sample);
    }

    public void dibujarHuella(Image image) {
        lblImagenHuella.setIcon(new ImageIcon(image.getScaledInstance(lblImagenHuella.getWidth(),
                                                                      lblImagenHuella.getHeight(),
                                                                      Image.SCALE_DEFAULT)));
        repaint();
    }

    public void estadoHuellas() {
        if(isRegistro){
            String mensaje = "";
            if(totalHuellas == 0){
                totalHuellas = Reclutador.getFeaturesNeeded()/multiplicaHuella;
                mensaje = "Escanee su huella. 0 de " + totalHuellas;
            }else{
                mensaje = "Escanee la misma huella de nuevo. " + (totalHuellas - (Reclutador.getFeaturesNeeded()/multiplicaHuella)) + " de " + totalHuellas;
            }
            enviarTexto("[CAPTURA] : Muestra de Huellas Necesarias para registro --> " + Reclutador.getFeaturesNeeded(),
                        mensaje);
        }else{
            enviarTexto("[CAPTURA] :  Escaee su huella para realizar la busqueda",
                        "Escaee su huella para realizar la busqueda");
        }
    }

    public void procesarCaptura(DPFPSample sample) {
        // Procesar la muestra de la huella y crear un conjunto de características con el propósito de inscripción.
        featuresinscripcion = extraerCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);

        // Procesar la muestra de la huella y crear un conjunto de características con el propósito de verificacion.
        featuresverificacion = extraerCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);

        // Comprobar la calidad de la muestra de la huella y lo añade a su reclutador si es bueno
        if (featuresinscripcion != null){
            try {
                Reclutador.addFeatures(featuresinscripcion); // Agregar las caracteristicas de la huella a la plantilla a crear
                for(int k=0;k<(multiplicaHuella-1);k++){
                    Reclutador.addFeatures(featuresinscripcion);
                }
                // Dibuja la huella dactilar capturada.
                Image image = crearImagenHuella(sample);
                dibujarHuella(image);
                if(!isRegistro){
                    try{
                        if(!isExisteHuella()){
                            FarmaUtility.showMessage(this, "Lector de Huella:\n"+
                                                           "Huella no encontrada", null);
                            lblF11.setEnabled(false);
                            Reclutador.clear();
                            lblImagenHuella.setIcon(null);
                        }
                    }catch(Exception ex){
                        FarmaUtility.showMessage(this, ex.getMessage(), null);
                    }
                    estadoHuellas();
                }
 
            } catch (DPFPImageQualityException ex) {
                log.error("Error: " + ex.getMessage());
            }finally {
                estadoHuellas();
                // Comprueba si la plantilla se ha creado.
                switch (Reclutador.getTemplateStatus()) {
                case TEMPLATE_STATUS_READY: // informe de éxito y detiene  la captura de huellas
                    stop();
                    setTemplate(Reclutador.getTemplate());
                    enviarTexto("[CAPTURA] : La Plantilla de la Huella ha Sido Creada",
                                "Captura Exitosa, Pulse [F11] para Grabar");
                    lblF11.setEnabled(true);
                    break;

                case TEMPLATE_STATUS_FAILED: // informe de fallas y reiniciar la captura de huellas
                    Reclutador.clear();
                    stop();
                    estadoHuellas();
                    setTemplate(null);
                    FarmaUtility.showMessage(this,"La Plantilla de la Huella no pudo ser creada, Repita el Proceso", null);
                    start();
                    break;
                }
            }
        }
    }

    public DPFPFeatureSet extraerCaracteristicas(DPFPSample sample, DPFPDataPurpose purpose) {
        DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
        try {
            return extractor.createFeatureSet(sample, purpose);
        } catch (DPFPImageQualityException e) {
            return null;
        }
    }

    public void setPSecUsuLocal(String pSecUsuLocal) {
        this.pSecUsuLocal = pSecUsuLocal;
    }

    public String getPSecUsuLocal() {
        return pSecUsuLocal;
    }

    public void setPosicionHuella(DPFPFingerIndex posicionHuella) {
        this.posicionHuella = posicionHuella;
    }
}
