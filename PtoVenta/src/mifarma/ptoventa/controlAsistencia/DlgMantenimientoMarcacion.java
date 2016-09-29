package mifarma.ptoventa.controlAsistencia;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.ce.reference.DBCajaElectronica;
import mifarma.ptoventa.controlAsistencia.reference.ConstantsControlAsistencia;
import mifarma.ptoventa.controlAsistencia.reference.DBControlAsistencia;
import mifarma.ptoventa.controlAsistencia.reference.UtilityControlAsistencia;

import mifarma.ptoventa.controlAsistencia.reference.VariablesControlAsistencia;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2015 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgMantenimientoMarcacion.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * CHUANES       04.09.2015   Creación<br>
 * EMAQUERA      15.10.2015   Modificacion<br>
 * <br>
 * @author Cesar Huanes<br>
 * @version 1.0<br>
 *
 */
public class DlgMantenimientoMarcacion extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgMantenimientoMarcacion.class);
    @SuppressWarnings("compatibility:398606826956210138")
    private static final long serialVersionUID = 1L;
    Frame myParentFrame;
    FarmaTableModel tableModel;
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JPanelTitle pnlDatos = new JPanelTitle();
    private JButtonLabel btnNomHorario = new JButtonLabel();
    private JButtonLabel btnFormatoFecha = new JButtonLabel();
    private JButtonLabel btnFormatoHora = new JButtonLabel();
    private JButtonLabel btnHoraSalida = new JButtonLabel();
    private JComboBox cmbMotivo = new JComboBox();
    private JButtonLabel btnJustificacion = new JButtonLabel();
    private JTextFieldSanSerif txtFechaSalida = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtHoraSalida = new JTextFieldSanSerif();
    private JLabelFunction lblRegistrar = new JLabelFunction();
    private JLabelFunction lblCancelar = new JLabelFunction();
    private JButtonLabel btnDescripcion = new JButtonLabel();

    private JTextArea txtDescripcion = new JTextArea();
    private String nombre = "";
    private String fechaEntrada = "";
    private String fechaSalidaSug = "";
    private String horaSalidaSug = "";
    private String codMotivo = "";
    private int cantMarcacion = 1;
    private String dni = "";
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JLabelWhite lblMsgEntrada = new JLabelWhite();
    private String fechaSalida = "";

    ArrayList lstTipoMotivoCmb=new ArrayList();
    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */
    
    public DlgMantenimientoMarcacion() {

    }

    public DlgMantenimientoMarcacion(Frame parent, 
                                     String title, 
                                     boolean modal, 
                                     String dni) {
        super(parent, title, modal);
        myParentFrame = parent;
        this.dni = dni;
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
        this.setSize(new Dimension(460, 450));
        this.getContentPane().setLayout(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Regularizaci\u00f3n de Marcaci\u00f3n de Salida");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jPanelWhite1.setLayout(null);
        jPanelWhite1.setBounds(new Rectangle(0, 10, 455, 410));

        pnlDatos.setBounds(new Rectangle(5, 10, 435, 315));
        pnlDatos.setBackground(Color.white);
        pnlDatos.setFont(new Font("SansSerif", 0, 11));
        pnlDatos.setLayout(null);
        pnlDatos.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));

        cmbMotivo.setBounds(new Rectangle(120, 175, 275, 20));
        cmbMotivo.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                cmbMotivo_keyPressed(e);

            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        cmbMotivo.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    cmbMotivoStateChanged();
                }
            }
        });

        btnNomHorario.setText("Fecha Salida :");
        btnNomHorario.setBounds(new Rectangle(10, 100, 105, 15));
        btnNomHorario.setForeground(new Color(255, 130, 14));
        btnNomHorario.setMnemonic('e');
        btnNomHorario.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
                FarmaUtility.moveFocus(txtFechaSalida);
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }
        });
        btnFormatoFecha.setText("DD/MM/YYYY");
        btnFormatoFecha.setBounds(new Rectangle(250, 100, 110, 15));
        btnFormatoFecha.setForeground(new Color(255, 130, 14));

        btnFormatoHora.setText("HH24:MI");
        btnFormatoHora.setBounds(new Rectangle(250, 140, 75, 15));
        btnFormatoHora.setForeground(new Color(255, 130, 14));

        btnHoraSalida.setText("Hora Salida :");
        btnHoraSalida.setBounds(new Rectangle(10, 140, 105, 15));
        btnHoraSalida.setForeground(new Color(255, 130, 14));
        btnHoraSalida.setMnemonic('h');
        btnHoraSalida.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
                FarmaUtility.moveFocus(txtHoraSalida);
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }
        });

        btnHoraSalida.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    lblHoraSalida_actionPerformed(e);
                }
            });
        lblRegistrar.setText("[F11]Aceptar");
        lblRegistrar.setBounds(new Rectangle(95, 335, 110, 25));
        lblRegistrar.setFont(new Font("SansSerif", 1, 11));
        lblRegistrar.setFocusable(true);
        lblRegistrar.setRequestFocusEnabled(false);
        lblRegistrar.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                btnRegistrar_mouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
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
        lblCancelar.setText("[Esc]Salir");
        lblCancelar.setBounds(new Rectangle(300, 335, 110, 25));
        lblCancelar.setFont(new Font("SansSerif", 1, 11));
        lblCancelar.setFocusable(true);
        lblCancelar.setRequestFocusEnabled(false);
        lblCancelar.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                btnCancelar_mouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
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

        txtFechaSalida.setBounds(new Rectangle(120, 100, 120, 20));
        txtFechaSalida.setFont(new Font("SansSerif", 0, 11));
        txtFechaSalida.setDocument(new FarmaLengthText(10));
        txtFechaSalida.addKeyListener(new KeyListener() {


            @Override
            public void keyPressed(KeyEvent e) {
                txtSalida_keyPreseed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                txtSalida_keyReleased(e);
            }

            @Override
            public void keyTyped(KeyEvent e) {
                    txtFechaSalida_keyTyped(e);
                }
        });

        txtHoraSalida.setBounds(new Rectangle(120, 140, 120, 20));
        txtHoraSalida.setFont(new Font("SansSerif", 0, 11));
        txtHoraSalida.setDocument(new FarmaLengthText(5));
        txtHoraSalida.addKeyListener(new KeyListener() {


            @Override
            public void keyPressed(KeyEvent e) {
                txtHoraSalida_keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                txtHoraSalida_keyReleased(e);
            }

            @Override
            public void keyTyped(KeyEvent e) {
                    txtHoraSalida_keyTyped(e);
                }
        });

        btnJustificacion.setText("Motivo:");
        btnJustificacion.setBounds(new Rectangle(10, 175, 105, 20));
        btnJustificacion.setForeground(new Color(255, 130, 14));
        btnJustificacion.setMnemonic('m');
        btnJustificacion.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
                FarmaUtility.moveFocus(cmbMotivo);
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        btnDescripcion.setText("Observación :");
        btnDescripcion.setBounds(new Rectangle(10, 215, 90, 20));
        btnDescripcion.setForeground(new Color(255, 130, 14));
        btnDescripcion.setMnemonic('o');

        btnDescripcion.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    lblDescripcion_actionPerformed(e);
                }
            });
        txtDescripcion.setBounds(new Rectangle(120, 220, 280, 40));
        txtDescripcion.setBorder(BorderFactory.createLineBorder(SystemColor.windowText, 1));
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtDescripcion_keyPressed(e);
            }
        });
        jPanelHeader1.setBounds(new Rectangle(0, 0, 435, 80));
        lblMsgEntrada.setBounds(new Rectangle(10, 10, 415, 60));
        jPanelHeader1.add(lblMsgEntrada, null);
        pnlDatos.add(jPanelHeader1, null);
        pnlDatos.add(txtDescripcion, null);
        pnlDatos.add(txtFechaSalida, null);
        pnlDatos.add(btnHoraSalida, null);
        pnlDatos.add(txtHoraSalida, null);
        pnlDatos.add(btnFormatoFecha, null);
        pnlDatos.add(btnFormatoHora, null);
        pnlDatos.add(btnNomHorario, null);
        pnlDatos.add(cmbMotivo, null);
        pnlDatos.add(btnJustificacion, null);
        pnlDatos.add(btnDescripcion, null);
        jPanelWhite1.add(lblRegistrar, null);
        jPanelWhite1.add(lblCancelar, null);
        jPanelWhite1.add(pnlDatos, null);
        this.getContentPane().add(jPanelWhite1, null);
    }

    /* ********************************************************************** */
    /*                        Método "initialize()"                           */
    /* ********************************************************************** */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        obtenerDatos();
        initMotivo();
        FarmaUtility.moveFocus(txtFechaSalida);
    }

    /* ********************************************************************** */
    /*                      Métodos de eventos                                */
    /* ********************************************************************** */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        RegularizacionAsistencia();
        if (txtFechaSalida.getText().trim().length() > 0){
            FarmaUtility.moveFocus(cmbMotivo);
        }else{
            FarmaUtility.moveFocus(txtFechaSalida);
        }
    }

    private void txtSalida_keyPreseed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            FarmaUtility.moveFocus(txtHoraSalida);
        }
        chkKeyPressed(e);
    }

    private void txtSalida_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaSalida, e);
    }

    private void cmbMotivo_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtDescripcion);
            txtDescripcion.setText(txtDescripcion.getText().trim());            
            txtDescripcion.selectAll();
            txtDescripcion.setLineWrap(true);
        }
        chkKeyPressed(e);
    }

    private void txtHoraSalida_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(cmbMotivo);
        }
        chkKeyPressed(e);
    }

    private void txtHoraSalida_keyReleased(KeyEvent e) {
        dateHourComplete(txtHoraSalida, e);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnCancelar_mouseClicked(MouseEvent e) {
        cerrarVentana(false);
    }

    private void btnRegistrar_mouseClicked(MouseEvent e) {
        grabarMarcacion();
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_F11) {
            grabarMarcacion();
        }
    }


    private void txtDescripcion_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            txtDescripcion.setText(txtDescripcion.getText().trim());
            FarmaUtility.moveFocus(txtFechaSalida);
        }
        chkKeyPressed(e);
    }

    private void lblHoraSalida_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtHoraSalida);
    }

    private void lblDescripcion_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtDescripcion);
    }

    /* ********************************************************************** */
    /*                   Métodos de lógica de negocio                         */
    /* ********************************************************************** */

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    
    private double obtenerDiferenciaHoras() {
        Date fechaInicial= UtilityControlAsistencia.obtenerStringToDate(this.getFechaEntrada() + ":00", 
                                                                        "/", 5);
        
        Date fechaFinal= UtilityControlAsistencia.obtenerStringToDate(txtFechaSalida.getText().trim() + 
                                                                      " " + 
                                                                      txtHoraSalida.getText().trim() + ":00", 
                                                                      "/", 5);        

        Calendar calFechaInicial=Calendar.getInstance(); 
        Calendar calFechaFinal=Calendar.getInstance(); 
        calFechaInicial.setTime(fechaInicial); 
        calFechaFinal.setTime(fechaFinal);
        
        return UtilityControlAsistencia.obtenerDiferenciaHoras(calFechaInicial, calFechaFinal);
    }

    private void grabarMarcacion() {
        if (validarDatos()) { //validamos el formato de la hora
            if (JConfirmDialog.rptaConfirmDialogDefaultNo(this,
                                                          "Fecha y Hora de Salida Ingresada: " + 
                                                          this.txtFechaSalida.getText().trim() +
                                                          " " + 
                                                          this.txtHoraSalida.getText().trim() +
                                                          ":00\n" +
                                                          "Quiere decir que estuvo laborando " +
                                                          obtenerDiferenciaHoras() +
                                                          " hrs contando su hora de refrigerio" +
                                                          "\n¿Esta seguro de registrar la marcación?")) {
                boolean flag=false;
                
                flag = UtilityControlAsistencia.grabaMarcacionFMarca(this.dni,
                                                                     this.getFechaEntrada().substring(0, 10),
                                                                     txtFechaSalida.getText().trim(),
                                                                     txtHoraSalida.getText().trim(),
                                                                     Integer.parseInt(this.getCodMotivo()),
                                                                     txtDescripcion.getText(),
                                                                     fechaSalida);

                if (flag){
                    FarmaUtility.showMessage(this, "Se Genero la Solicitud para Regularizar la Salida.\n", null);                
                    cerrarVentana(false);
                }else{
                    FarmaUtility.showMessage(this, "No se grabo la correctamente. Favor volverlo a intentar.\n"+
                                                   "Si el problema persiste comunicarse con Mesa de Ayuda.", null);
                }
            }
        }
    }
    
    private void cmbMotivoStateChanged() {
        int index = cmbMotivo.getSelectedIndex();
        if (index > 0) {
            String codMotivo = FarmaLoadCVL.getCVLCode("cmbMotivo", cmbMotivo.getSelectedIndex()).toString().trim();
            this.setCodMotivo(codMotivo);
        }
    }

    private void initMotivo() {
/*
        FarmaLoadCVL.loadCVLFromSP(cmbMotivo, "cmbMotivo", "FARMA_GRAL.GET_MAESTRO_DETALLE_LISTA(?)", parametros,
                                   false, true);
        ArrayList parametros = new ArrayList();
        parametros.add(ConstantsControlAsistencia.COD_SUBTIPO_SOLICITUD);
        
        FarmaLoadCVL.loadCVLFromSP(cmbMotivo, "cmbMotivo", "FARMA_GRAL.GET_MAESTRO_DETALLE_LISTA(?)", parametros,
                                   false, true);
*/        
        lstTipoMotivoCmb=UtilityControlAsistencia.listarSubTipoSolic(ConstantsControlAsistencia.VALOR_REG_SALIDA) ;
        cmbMotivo.removeAllItems();
        FarmaLoadCVL.loadCVLFromArrayList(cmbMotivo, "cmbMotivo", lstTipoMotivoCmb, false);
        
    }

    public boolean validarDatos() {
        boolean flag = true;
        
       // if ( permisoQf()) {//TODO
            String indCompara = "";
            String indFormato = "";
            String indRangoHoras = "";
            String msgRangoHoras = "";
            String maxRangoHoras = "";
            String pEntrada = this.getFechaEntrada();
            String pFechaSalida = txtFechaSalida.getText();
            String pHoraSalida = txtHoraSalida.getText();
            String fecha_Hora_salida = pFechaSalida + " " + pHoraSalida;
            
            if (txtFechaSalida.getText().trim().equalsIgnoreCase("")) {

                FarmaUtility.showMessage(this, "Ingrese Fecha de Salida", null);
                return false;

            } else if (!FarmaUtility.isFechaValida(this,
                                                  txtFechaSalida.getText().trim(),
                                                  "Ingrese Fecha de salida válida, verificar por favor")) {
                return false;

            }else if (txtHoraSalida.getText().trim().equalsIgnoreCase("")) {

                FarmaUtility.showMessage(this, "Ingrese Hora de Salida", null);
                return false;

            } else if (!FarmaUtility.isHoraMinutoValida(this,
                                                    txtHoraSalida.getText().trim(),
                                                    "Ingrese Hora de salida válida, verificar por favor")) {
                return false;

            }
            
            indCompara = UtilityControlAsistencia.comparaFechaFMarca(pEntrada, fecha_Hora_salida).trim();
            indFormato = UtilityControlAsistencia.validaFormatoFechaFMarca(fecha_Hora_salida).trim();
            indRangoHoras =
                    UtilityControlAsistencia.validaRangoHorasFMarca(pEntrada, fecha_Hora_salida, this.getCantMarcacion()).trim();
            msgRangoHoras = UtilityControlAsistencia.msgRangoHorasFMarca(this.getCantMarcacion());
            maxRangoHoras =
                    UtilityControlAsistencia.maxRangoHorasFMarca(pEntrada, fecha_Hora_salida, this.getCantMarcacion()).trim();

            log.info("Indicador de comparacion de fechas" + indCompara);
            if (cmbMotivo.getSelectedIndex() == -1 || cmbMotivo.getSelectedIndex() == 0) {
                FarmaUtility.showMessage(this, "Seleccionar Justificación", cmbMotivo);
                return false;

            } else if (indFormato.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {

                FarmaUtility.showMessage(this, "Formato no valido de fecha de salida", null);
                return false;

            } else if (indCompara.equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {

                FarmaUtility.showMessage(this, "Hora Inicio mayor a Hora Final", null);
                return false;

            } else if (indCompara.equalsIgnoreCase(ConstantsControlAsistencia.IND_COMPARA)) {

                FarmaUtility.showMessage(this, "Hora Inicio igual a Hora Final", null);
                return false;
            } 
            if(txtDescripcion.getText() == null || 
               (txtDescripcion.getText().trim().length() < 3 || txtDescripcion.getText().trim().length() > 200 )){
                FarmaUtility.showMessage(this, 
                                         "Favor de ingresar la Observación, debe tener minimo 3 y maximo 200 caracteres.",
                                         null);
                return false;
            }
        //}
        //else
        //return false;

        return flag;
    }

    private boolean permisoQf() {
        String numsec = FarmaVariables.vNuSecUsu;
        String idusu = FarmaVariables.vIdUsu;
        String nomusu = FarmaVariables.vNomUsu;
        String apepatusu = FarmaVariables.vPatUsu;
        String apematusu = FarmaVariables.vMatUsu;

        try {
            DlgLogin dlgLogin = new DlgLogin(myParentFrame, "Autorización del Q.F", true);
            dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
            dlgLogin.setVisible(true);
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
        } catch (Exception e) {
            FarmaVariables.vNuSecUsu = numsec;
            FarmaVariables.vIdUsu = idusu;
            FarmaVariables.vNomUsu = nomusu;
            FarmaVariables.vPatUsu = apepatusu;
            FarmaVariables.vMatUsu = apematusu;
            FarmaVariables.vAceptar = false;
            log.error("", e);
            FarmaUtility.showMessage(this, "Ocurrio un error al validar rol de usuariario \n : " + e.getMessage(),
                                     null);
        }
        return FarmaVariables.vAceptar;
    }

    private void obtenerDatos() {
        log.info("obteniendo datos usuario");
        ArrayList list = new ArrayList();
        UtilityControlAsistencia.getDataUsuario(list, this.dni);
        this.setNombre(FarmaUtility.getValueFieldArrayList(list, 0, 0));
        this.setFechaEntrada(FarmaUtility.getValueFieldArrayList(list, 0, 1));
        this.setFechaSalidaSug(FarmaUtility.getValueFieldArrayList(list, 0, 2));
        this.setHoraSalidaSug(FarmaUtility.getValueFieldArrayList(list, 0, 3));
        this.setCantMarcacion(Integer.parseInt(FarmaUtility.getValueFieldArrayList(list, 0, 4)));
        txtFechaSalida.setText(this.getFechaSalidaSug());
        txtHoraSalida.setText(this.getHoraSalidaSug());
        String iniHtml = "<html><head><title></title></head><body>";
        String mensaje = "Hola " + 
                         this.getNombre() + 
                         " tu última marcación de entrada fue " +
                         this.getFechaEntrada() + ":00; pero" +
                         " no marcaste tu salida, por favor proceder a regularizar dicha marcación";
        String finHtml = "</body></html>";
        lblMsgEntrada.setText(iniHtml + mensaje + finHtml);
    }

    public static void dateHourComplete(JTextField pJTextField, KeyEvent e) {
        char keyChar = e.getKeyChar();
        if (Character.isDigit(keyChar)) {
            if ((pJTextField.getText().trim().length() == 2)) {
                pJTextField.setText(pJTextField.getText().trim() + ":");
                if (pJTextField.getText().trim().length() == 5)
                    pJTextField.setText(pJTextField.getText().trim());
            }
        }

    }

    private void RegularizacionAsistencia(){
        String indMensaje = "";
        fechaSalida = "";
        String msg = "";
        String pieMsg = "\n¿Acepta?";
        String cabeceraMsg = "No ha marcado su salida del turno anterior.\n";        
        String mensajeTotal = UtilityControlAsistencia.obtieneIndMsje(this.dni,
                                                                 fechaSalida,
                                                                 msg);
        String[] pDatos = mensajeTotal.split("°°°");
        if (pDatos.length != 3) {
            FarmaUtility.showMessage(this, 
                                     "Error desconocido de Base de datos.\nComuniquese con mesa de ayuda", 
                                     null);
            cerrarVentana(false);
        }
        indMensaje = pDatos[0];
        fechaSalida = pDatos[1];
        msg = pDatos[2];
        log.info("IndMsje: "+indMensaje+"- descMsje:"+msg);
        switch(indMensaje) {
         case "1":
            FarmaUtility.showMessage(this, cabeceraMsg+
                                           msg, null);
            UtilityControlAsistencia.grabaRegularizacionSalida(this.dni,
                                                               fechaSalida);
            
            cerrarVentana(false);
            break;
        
        case "2":
           FarmaUtility.showMessage(this, cabeceraMsg+
                                           msg, null);
            UtilityControlAsistencia.grabaRegularizacionSalida(this.dni,
                                                               fechaSalida);
            
            cerrarVentana(false);
           break;
        
        case "3":
            if (!JConfirmDialog.rptaConfirmDialog(this,cabeceraMsg+
                                                        msg + 
                                                        pieMsg)){
                                                          
            }else{
                UtilityControlAsistencia.grabaRegularizacionSalida(this.dni,
                                                               fechaSalida);
                
                cerrarVentana(false);
            }         
            break;
        case "4":
            if (!JConfirmDialog.rptaConfirmDialog(this,cabeceraMsg+
                                                        msg  + 
                                                        pieMsg)){
                                                          
            }else{
                    UtilityControlAsistencia.grabaRegularizacionSalida(this.dni,
                                                               fechaSalida);
               
                cerrarVentana(false);
            }         
            break;
        case "9":
            FarmaUtility.showMessage(this, cabeceraMsg+
                                           msg, null);
            cerrarVentana(false);
            break;
        default:
            FarmaUtility.showMessage(this, cabeceraMsg+
                                           "Se ha presentado un error favor desconocido.\nComunicarse con Mesa de Ayuda.", null);
            cerrarVentana(false);
            log.info("No entro a ningun case...");
            break;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(String fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public String getFechaSalidaSug() {
        return fechaSalidaSug;
    }

    public void setFechaSalidaSug(String fechaSalidaSug) {
        this.fechaSalidaSug = fechaSalidaSug;
    }

    public String getHoraSalidaSug() {
        return horaSalidaSug;
    }

    public void setHoraSalidaSug(String horaSalidaSug) {
        this.horaSalidaSug = horaSalidaSug;
    }

    public String getCodMotivo() {
        return codMotivo;
    }

    public void setCodMotivo(String codMotivo) {
        this.codMotivo = codMotivo;
    }

    public int getCantMarcacion() {
        return cantMarcacion;
    }

    public void setCantMarcacion(int cantMarcacion) {
        this.cantMarcacion = cantMarcacion;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    private void txtFechaSalida_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtFechaSalida, e);
    }

    private void txtHoraSalida_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtHoraSalida, e);
    }
}
