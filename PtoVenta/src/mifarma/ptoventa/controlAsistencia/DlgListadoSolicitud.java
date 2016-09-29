package mifarma.ptoventa.controlAsistencia;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
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
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.controlAsistencia.reference.ConstantsControlAsistencia;
import mifarma.ptoventa.controlAsistencia.reference.UtilityControlAsistencia;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2015 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgListadoSolicitud.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * CHUANES                    Creación<br>
 * EMAQUERA      15.10.2015   Modificacion<br>
 * <br>
 * @author CHUANES <br>
 * @version 1.0<br>
 *
 */
public class DlgListadoSolicitud  extends JDialog{
    private static final Logger log = LoggerFactory.getLogger(DlgListadoSolicitud.class);
    @SuppressWarnings("compatibility:398606826956210138")
    private static final long serialVersionUID = 1L;
    Frame myParentFrame;
    FarmaTableModel tableModel;

    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelTitle pnlHeaderRegistro = new JPanelTitle();
    private JLabelFunction lblCrear = new JLabelFunction();
    private JLabelFunction lblsc = new JLabelFunction();
    private JScrollPane srcListaSolicitud = new JScrollPane();
    private JTable tblListaSolicitud = new JTable();
    private JButtonLabel btnRegistro = new JButtonLabel();
    private JButtonLabel btnRandoFec = new JButtonLabel();
    private JButtonLabel btnEstado = new JButtonLabel();    
    private JButton btnBuscar = new JButton();
    private JTextFieldSanSerif txtFechaFin = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaIni = new JTextFieldSanSerif();
    private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
    private JComboBox cmbEstado = new JComboBox();
    Calendar fecha = Calendar.getInstance();
    private int dias = fecha.get(Calendar.DAY_OF_MONTH) - 1;

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */
    
    public DlgListadoSolicitud() {
       this(null, " ", false);
    }
    
    public DlgListadoSolicitud(Frame parent, String title, boolean modal){
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
        this.setSize(new Dimension(750, 400));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Registro de solicitudes");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setLayout(null);
        pnlHeaderRegistro.setBounds(new Rectangle(10,65, 700, 25));
        lblCrear.setBounds(new Rectangle(10, 340, 105, 20));
        lblCrear.setText("[F2] Nuevo");
        lblsc.setBounds(new Rectangle(610, 340, 95, 20));
        lblsc.setText("[Esc]Salir");
        srcListaSolicitud.setBounds(new Rectangle(10, 90, 700, 235));
        tblListaSolicitud.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaSolicitud_keyPressed(e);
            }
        });
        btnRegistro.setText("Lista de Solicitudes Registradas");
        btnRegistro.setBounds(new Rectangle(10, 5, 225, 15));
        btnRegistro.setMnemonic('s');
        btnRegistro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRegistro_actionPerformed(e);
            }
        });
        pnlCriterioBusqueda.setBounds(new Rectangle(10, 10, 700,50));

        cmbEstado.setBounds(new Rectangle(435, 15, 100, 20));
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(560, 15, 95, 20));
        btnBuscar.setMnemonic('b');
        btnBuscar.setFont(new Font("SansSerif", 1, 11));
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               btnBuscar_actionPerformed(e);
            }
        });
        txtFechaFin.setBounds(new Rectangle(255, 15, 101, 19));
        txtFechaFin.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
               txtFechaFin_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
              txtFechaFin_keyReleased(e);
            }
        });
        txtFechaFin.setDocument(new FarmaLengthText(10));
        txtFechaIni.setBounds(new Rectangle(135, 15, 101, 19));
        txtFechaIni.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaIni_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaIni_keyReleased(e);
            }
        });
        txtFechaIni.setDocument(new FarmaLengthText(10));
        btnRandoFec.setText("Rango de Fechas");
        btnRandoFec.setBounds(new Rectangle(25, 15, 100, 20));
        btnRandoFec.setMnemonic('f');
        btnRandoFec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               btnRandoFec_actionPerformed(e);
            }
        });
        
        btnEstado.setText("Estado");
        btnEstado.setBounds(new Rectangle(380, 15, 65, 20));

        pnlCriterioBusqueda.add(btnEstado, null);
        pnlCriterioBusqueda.add(btnBuscar, null);
        pnlCriterioBusqueda.add(txtFechaFin, null);
        pnlCriterioBusqueda.add(txtFechaIni, null);
        pnlCriterioBusqueda.add(btnRandoFec, null);
        pnlCriterioBusqueda.add(cmbEstado, null);
        jContentPane.add(pnlCriterioBusqueda, null);
        srcListaSolicitud.getViewport().add(tblListaSolicitud, null);
        jContentPane.add(srcListaSolicitud, null);
        jContentPane.add(lblsc, null);
        jContentPane.add(lblCrear, null);
        pnlHeaderRegistro.add(btnRegistro, null);
        jContentPane.add(pnlHeaderRegistro, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ********************************************************************** */
    /*                        Método "initialize()"                           */
    /* ********************************************************************** */
    
    private void initialize() {
        initializeTable();
    }

    /* ********************************************************************** */
    /*                      Métodos de inicialización                         */
    /* ********************************************************************** */
    
    private void initializeTable() {
        tableModel =
                new FarmaTableModel(ConstantsControlAsistencia.columnsListaSolicitud, ConstantsControlAsistencia.defaultListaSolicitud,
                                    0);
        FarmaUtility.initSimpleList(tblListaSolicitud, tableModel, ConstantsControlAsistencia.columnsListaSolicitud);
        listarSolicitud(dias);
       
    }

    /* ********************************************************************** */
    /*                      Métodos de eventos                                */
    /* ********************************************************************** */
    
    private void txtFechaIni_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtFechaFin);
        } else {
            chkKeyPressed(e);
        }
    }

    private void txtFechaIni_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaIni, e);
    }

    private void txtFechaFin_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            btnBuscar.doClick();
        } else {
            chkKeyPressed(e);
        }
    }
    
    private void txtFechaFin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaFin, e);
    }
    
    private void chkKeyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F2(e)) {
            insertarPlantilla();
        }   else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }
    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    private void btnRandoFec_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFechaIni);
    }
    private void btnBuscar_actionPerformed(ActionEvent e) {
        buscar();
    }
    private void tblListaSolicitud_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }
    private void btnRegistro_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaSolicitud);
    }
    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtFechaIni);
        validarLogin();
        try {
            String fechaActual = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA);
            String fechaAtrasada = FarmaSearch.getFechaHoraAtrasadaBD(FarmaConstants.FORMATO_FECHA, dias);
            txtFechaIni.setText(fechaAtrasada);
            txtFechaFin.setText(fechaActual);
            carga_Combos();
        } catch (SQLException sql) {
            log.error("", e);
        }        
    }

    /* ********************************************************************** */
    /*                   Métodos de lógica de negocio                         */
    /* ********************************************************************** */    

    private void listarSolicitud(int pDias){
        tableModel.clearTable();
        ArrayList lstSolicitud=new ArrayList();

        lstSolicitud=UtilityControlAsistencia.listarSolicitudes(pDias);
        if(lstSolicitud.size()>0){
            tableModel.data= lstSolicitud; 
        }else{
            log.info("No existe datos de solicitud para mostrar.");  
        }
    }
    
    private void buscar(){
        if(!isValidaBusqueda()){
            return;
        }
        listarSolicitudFechas();
        FarmaUtility.moveFocus(tblListaSolicitud);
    }
    
    private void listarSolicitudFechas(){
        tableModel.clearTable();
        ArrayList lstSolicitud=new ArrayList();
        String pFechaInicio=txtFechaIni.getText().trim() ;
        String pFechaFin= txtFechaFin.getText().trim();         
        String pEstSol =FarmaLoadCVL.getCVLCode(ConstantsControlAsistencia.NOM_HASTABLE_CMBESTSOL, 
                                                           cmbEstado.getSelectedIndex());
        
        lstSolicitud=UtilityControlAsistencia.lstSolicRangoFecha(pFechaInicio, pFechaFin, pEstSol.trim());

        if(lstSolicitud.size()>0){        
            tableModel.data= lstSolicitud; 
        }else{
            log.info("No existe datos de solicitud para mostrar.");  
        }
    }
    
    private void insertarPlantilla(){
            DlgMantenimientoSolicitud solicitud=new   DlgMantenimientoSolicitud(myParentFrame,"",true);
            solicitud.setVisible(true);  
            
                listarSolicitud(dias);
    }
    
    private boolean isValidaBusqueda() {
        boolean retorno = true;
       if (txtFechaIni.getText().trim().equals("")) {
               retorno = false;
               FarmaUtility.showMessage(this, "Favor ingrese Fecha de Inicio.", txtFechaIni);
           } else if (txtFechaFin.getText().trim().equals("")) {
               retorno = false;
               FarmaUtility.showMessage(this, "Favor ingrese Fecha de Fin.", txtFechaFin);
           } else if(!FarmaUtility.validaFecha(txtFechaIni.getText(), "dd/MM/yyyy")){
               retorno = false;
               FarmaUtility.showMessage(this, "El formato ingresado en Fecha de Inicio es incorrecto. (DD/MM/YYYY)", txtFechaIni);
        } else  if (!FarmaUtility.validaFecha(txtFechaFin.getText(), "dd/MM/yyyy")) {
               retorno = false;
               FarmaUtility.showMessage(this, "El formato ingresado en Fecha de Fin es incorrecto. (DD/MM/YYYY)", txtFechaFin);
        
        } else if (!FarmaUtility.validarRangoFechas(this, txtFechaIni, txtFechaFin, false, true, true, true))
                   retorno = false;
                    
        return retorno;
    }

    private void carga_Combos(){
        FarmaLoadCVL.loadCVLfromArrays(cmbEstado, 
                                                   ConstantsControlAsistencia.NOM_HASTABLE_CMBESTSOL, 
                                                   ConstantsControlAsistencia.vCodEstadoSol, 
                                                   ConstantsControlAsistencia.vDescEstadoSol, 
                                                   true);
    }

    /**
     * Valida QF en el local
     * @author EMAQUERA
     * @since 13.11.2015
     */
    private void validarLogin() {
        if(!permisoQf()){
            cerrarVentana(false);
        }
    }  
    
    /**
     * Valida QF en el local
     * @author EMAQUERA
     * @since 13.11.2015
     * @return
     */
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
            //FarmaVariables.vNuSecUsu = numsec; //TODO
            //FarmaVariables.vIdUsu = idusu;
            //FarmaVariables.vNomUsu = nomusu;
            //FarmaVariables.vPatUsu = apepatusu;
            //FarmaVariables.vMatUsu = apematusu;
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

}
