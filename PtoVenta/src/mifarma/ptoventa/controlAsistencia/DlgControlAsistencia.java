package mifarma.ptoventa.controlAsistencia;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.controlAsistencia.reference.ConstantsControlAsistencia;
import mifarma.ptoventa.controlAsistencia.reference.DBControlAsistencia;
import mifarma.ptoventa.controlAsistencia.reference.VariablesControlAsistencia;

import mifarma.ptoventa.controlAsistencia.reference.UtilityControlAsistencia;
import mifarma.ptoventa.lectorHuella.DlgRegistroLectorHuella;
import mifarma.ptoventa.puntos.reference.UtilityPuntos;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import static mifarma.ptoventa.reference.UtilityPtoVenta.limpiaCadenaAlfanumerica;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2015 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgControlAsistencia.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * CHUANES                    Creación<br>
 * EMAQUERA   15.10.2015    Modificacion<br>
 * ASOSA      19.10.2015    Modificacion<br>
 * <br>
 * @author CHUANES <br>
 * @version 1.0<br>
 *
 */
public class DlgControlAsistencia extends JDialog implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(DlgControlAsistencia.class);
    @SuppressWarnings("compatibility:398606826956210138")
    private static final long serialVersionUID = 1L;
    Frame myParentFrame;

    FarmaTableModel tableModel;
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JPanelHeader pnlTitulo = new JPanelHeader();
    private JLabel lblHora = new JLabel();
    private JLabelOrange lblTitulo = new JLabelOrange();
    private JLabel lblImagen;
    private JPanelTitle pnlDatosUsuario = new JPanelTitle();
    private JPanelTitle pnlImagen = new JPanelTitle();

    private JButtonLabel lblCodigoEmpleado = new JButtonLabel();
    private JLabelWhite lblIndFiscalizado = new JLabelWhite();
    private JLabel lblMsgBienvenida = new JLabel();
    private JTextFieldSanSerif txtDni = new JTextFieldSanSerif();

    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();

    String hora, minutos, segundos, ampm;
    Calendar calendario;
    Thread h1;    
    boolean isLectoraLazer, isCodigoBarra;
    boolean isEnter = false;
    static long tmpT1, tmpT2, OldtmpT2;
    private boolean flag = true;
    private String tipSugerido = "";
    private String desTipSugerido = "";
    private boolean isDniIngreso = true;
    private boolean isHuella = true;
    private JButtonLabel lblInformacion = new JButtonLabel();
    private long tiempoTeclaInicial ,tiempoTeclaFinal;
    
    private String pSecUsuarioTmp;

    // **************************************************************************
    // Constructores
    // **************************************************************************
    
    public DlgControlAsistencia() {
        this(null, " ", false);
    }

    public DlgControlAsistencia(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initRbtnFormaActivacion();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(572, 403));
        this.getContentPane().setLayout(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Marcación de Entrada/Salida");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jPanelWhite1.setLayout(null);
        jPanelWhite1.setBounds(new Rectangle(0, 0, 570, 375));
        pnlTitulo.setBounds(new Rectangle(0, 10, 555, 60));

        pnlTitulo.setBackground(new Color(43, 141, 39));
        pnlTitulo.setLayout(null);
        pnlTitulo.setFont(new Font("SansSerif", 0, 11));

        lblTitulo.setText("Control de Marcación");

        lblTitulo.setFont(new Font("SansSerif", 1, 20));
        lblTitulo.setBounds(new Rectangle(140, 10, 250, 20));
        lblTitulo.setForeground(Color.white);
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        lblTitulo.setHorizontalTextPosition(JLabel.CENTER);

        pnlDatosUsuario.setBounds(new Rectangle(0, 75, 555, 180));
        pnlDatosUsuario.setBackground(Color.white);
        pnlDatosUsuario.setFont(new Font("SansSerif", 1, 20));
        pnlDatosUsuario.setLayout(null);
        pnlDatosUsuario.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));

        pnlImagen.setBounds(new Rectangle(0, 265, 555, 100));
        pnlImagen.setBackground(Color.white);
        pnlImagen.setFont(new Font("SansSerif", 1, 20));
        pnlImagen.setLayout(null);
        pnlImagen.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));

        lblCodigoEmpleado.setBounds(new Rectangle(10, 35, 120, 15));
        lblCodigoEmpleado.setForeground(new Color(255, 130, 14));
        lblCodigoEmpleado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblCodigoEmpleado_actionPerformed(e);
            }
        });

        Border border = LineBorder.createGrayLineBorder();
        lblHora.setBounds(new Rectangle(140, 10, 230, 50));
        lblHora.setFont(new Font("SansSerif", 1, 30));
        lblHora.setHorizontalTextPosition(JLabel.CENTER);
        lblHora.setHorizontalAlignment(JLabel.CENTER);
        lblHora.setBorder(border);

        ImageIcon imageIcono =
            new ImageIcon(DlgControlAsistencia.class.getResource("/mifarma/ptoventa/imagenes/barra.jpg"));

        lblImagen = new JLabel();
        lblImagen.setIcon(imageIcono);
        lblImagen.setBounds(new Rectangle(140, 65, 230, 30));
        lblImagen.setFont(new Font("SansSerif", 0, 11));

        lblMsgBienvenida.setBounds(new Rectangle(15, 35, 520, 20));
        lblMsgBienvenida.setFont(new Font("SansSerif", 1, 12));
        lblMsgBienvenida.setHorizontalAlignment(JLabel.CENTER);
        lblMsgBienvenida.setHorizontalTextPosition(JLabel.CENTER);

        lblMsgBienvenida.setForeground(SystemColor.window);

        txtDni.setBounds(new Rectangle(190, 85, 135, 20));
        txtDni.setFont(new Font("SansSerif", 0, 11));
        txtDni.setLengthText(8);
        txtDni.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                    txtDni_keyPressed(e);
                }

            public void keyTyped(KeyEvent e) {
                    txtDni_keyTyped(e);
                }

            public void keyReleased(KeyEvent e) {
                    txtDni_keyReleased(e);
                }
        });


        lblIndFiscalizado.setBounds(new Rectangle(15, 130, 180, 25));
        lblIndFiscalizado.setForeground(Color.black);
        lblIndFiscalizado.setToolTipText(null);

        lblF1.setText("[F1] Administrar");
        lblF1.setBounds(new Rectangle(450, 40, 100, 25));
        lblF1.setFont(new Font("SansSerif", 1, 12));
        lblF11.setText("[Enter]Aceptar");
        lblF11.setBounds(new Rectangle(450, 10, 100, 25));
        lblF11.setFont(new Font("SansSerif", 1, 12));
        lblEsc.setText("[Esc] Salir");
        lblEsc.setBounds(new Rectangle(450, 70, 100, 25));
        lblEsc.setFont(new Font("SansSerif", 1, 12));

        lblInformacion.setText("jButtonLabel1");
        lblInformacion.setBounds(new Rectangle(190, 60, 135, 20));
        lblInformacion.setMnemonic('d');
        lblInformacion.setForeground(new Color(255, 132, 0));
        lblInformacion.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    lblInformacion_actionPerformed(e);
                }
            });
        pnlTitulo.add(lblTitulo, null);
        pnlTitulo.add(lblMsgBienvenida, null);
        pnlDatosUsuario.add(txtDni, null);
        pnlDatosUsuario.add(lblCodigoEmpleado, null);

        pnlDatosUsuario.add(lblIndFiscalizado, null);
        pnlDatosUsuario.add(lblInformacion, null);
        jPanelWhite1.add(pnlDatosUsuario, null);
        jPanelWhite1.add(pnlTitulo, null);

        pnlImagen.add(lblF11, null);
        pnlImagen.add(lblF1, null);
        pnlImagen.add(lblEsc, null);
        pnlImagen.add(lblHora, null);
        pnlImagen.add(lblImagen, null);
        jPanelWhite1.add(pnlImagen, null);
        this.getContentPane().add(jPanelWhite1, null);

    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************
    
    private void initialize() {
        FarmaVariables.vAceptar = false;
        ejecutaHora();
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtDni); 
    }

    private void lblCodigoEmpleado_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtDni);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************
    
    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
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
                if (buscaDni()) {
                    FarmaUtility.moveFocus(txtDni);
                    if(validarHuellaDactilar()){
                        boolean isGrabo = grabarRegisto();
                        if (isGrabo) {
                            FarmaUtility.showMessage(this,
                                                     "Marcación registrada con éxito",
                                                     txtDni);
                        }
                    }else{
                        VariablesControlAsistencia.vCodCia = "";
                        VariablesControlAsistencia.vCodTrab = "";
                        VariablesControlAsistencia.vCodHor = "";
                        VariablesControlAsistencia.vSugTipo = "";
                        FarmaUtility.moveFocus(txtDni);
                    }
                } else {
                    FarmaUtility.moveFocus(txtDni);
                }
            }           
            
            txtDni.setText("");
            
        } else {
            chkKeyPressed(e);
        }        
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************
    
    private boolean validarHuellaDactilar(){
        boolean isValido = true;
        if(isHuella){
            if(pSecUsuarioTmp != null && pSecUsuarioTmp.trim().length()>=0){
                DlgRegistroLectorHuella dlgHuella = new DlgRegistroLectorHuella(myParentFrame, "Consulta Huella Dactilar", true, pSecUsuarioTmp, false, null);
                dlgHuella.setVisible(true);
                if(!pSecUsuarioTmp.equalsIgnoreCase(dlgHuella.getPSecUsuLocal())){
                    isValido = false;
                }
            }else{
                isValido = false;
            }
        }
        //pSecUsuarioTmp
        return isValido;
    }
    
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
                    pSecUsuarioTmp = vArrayAux.get(5).toString().trim();
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
            msgBienvenida(vDni);
            indMarcEntrSalida = UtilityControlAsistencia.getIndMarcEntrSalida(vDni);
            if (indMarcEntrSalida.equals(ConstantsControlAsistencia.IND_REGULARIZAR)) {
                retorno = llamarVentanaRegularizacion();
                indMarcEntrSalida = UtilityControlAsistencia.getIndMarcEntrSalida(vDni);
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


    private boolean isNumero(char ca) {
        int numero = 0;
        try {
            numero = Integer.parseInt(ca + "");
            return true;
        } catch (NumberFormatException nfe) {
            log.error("",nfe);
            return false;
        }


    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F1) {
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
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

    private boolean existeRegistro() {

        boolean retorno = true;

        ArrayList vArrayAux = new ArrayList();
        try {
            DBControlAsistencia.getRegistro(vArrayAux, txtDni.getText().trim());

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

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    public void calcula() {
        Calendar calendario = new GregorianCalendar();
        Date fechaHoraActual = new Date();
        
        calendario.setTime(fechaHoraActual);
        ampm = calendario.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";

        if (ampm.equals("PM")) {
            int h = calendario.get(Calendar.HOUR_OF_DAY) - 12;
            hora = h > 9 ? "" + h : "0" + h;
        } else {
            hora =calendario.get(Calendar.HOUR_OF_DAY) > 9 ? "" + calendario.get(Calendar.HOUR_OF_DAY) : "0" + 
                  calendario.get(Calendar.HOUR_OF_DAY);
        }
        minutos =calendario.get(Calendar.MINUTE) > 9 ? "" + calendario.get(Calendar.MINUTE) : "0" + 
                 calendario.get(Calendar.MINUTE);
        
        segundos =calendario.get(Calendar.SECOND) > 9 ? "" + calendario.get(Calendar.SECOND) : "0" + 
                  calendario.get(Calendar.SECOND);

    }

    @Override
    public void run() {
        Thread ct = Thread.currentThread();
        while (ct == h1) {
            calcula();
            lblHora.setText(hora + ":" + minutos + ":" + segundos + " " + ampm);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.error("", e);
            }
        }
    }

    public void ejecutaHora() {
        h1 = new Thread(this);
        h1.start();
    }

    public void msgBienvenida(String pDni) {
        String msg;
        msg = UtilityControlAsistencia.msgBienvenida(pDni);
        if (msg.equals("")) {
            FarmaUtility.showMessage(this, 
                                     "ERROR: DNI no encontrado", 
                                     txtDni);
        }
        this.lblMsgBienvenida.setText(msg);        
    }

    private void initRbtnFormaActivacion() {
        String indDniIngreso = UtilityControlAsistencia.indDniIngreso();
        String indHuella = UtilityControlAsistencia.indHuella();
        lblInformacion.setText(ConstantsControlAsistencia.INGRESARDNI);
        log.info("indDniIngreso: " + indDniIngreso);
        log.info("indHuella: " + indHuella);
        if (indDniIngreso.equals(FarmaConstants.INDICADOR_S)) {
            this.setDniIngreso(true);
        } else {            
            this.setDniIngreso(false);
        }        
        if (indHuella.equals(FarmaConstants.INDICADOR_S)) {
            this.setHuella(true);
        } else {
            this.setHuella(false);
        }
        FarmaUtility.moveFocus(txtDni);
    }

/*
    public String indicadorIngreso() {
        String indicador = "";
        try {
            indicador = DBControlAsistencia.indicadorIngreso().trim();
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al verificar la forma de marcación.\n" +
                    e.getMessage(), txtDni);
        }
        return indicador;
    }
*/

    private void limpiarDatos() {
        txtDni.setText("");
        VariablesControlAsistencia.vCodCia = "";
        VariablesControlAsistencia.vCodTrab = "";
        VariablesControlAsistencia.vCodHor = "";
        VariablesControlAsistencia.vSugTipo = "";
        FarmaUtility.moveFocus(txtDni);
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

    private boolean validaSolicitud() {
        String vDni = txtDni.getText().trim();
        String pCadena = "";
        try {
            pCadena = DBControlAsistencia.validaSolicitud(vDni);
        } catch (SQLException sqle) {
            log.error("", sqle);
        }
        
        if(pCadena.trim().equalsIgnoreCase("N"))
            return false;
        else{
            FarmaUtility.showMessage(this,pCadena,txtDni);
            return true;
        }
    }

    public boolean isDniIngreso() {
        return isDniIngreso;
    }

    public void setDniIngreso(boolean dniIngreso) {
        isDniIngreso = dniIngreso;
    }

    public boolean isHuella() {
        return isHuella;
    }

    public void setHuella(boolean huella) {
        isHuella = huella;
    }

    private void lblInformacion_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(lblInformacion);
    }
}
