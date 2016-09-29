package mifarma.ptoventa.controlAsistencia;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
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
 * Nombre de la Aplicación : DlgListadoInasistencia.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * EMAQUERA      28.10.2015   Creación<br>
 * <br>
 * @author EMAQUERA <br>
 * @version 1.0<br>
 *
 */
public class DlgListadoInasistencia  extends JDialog{
    private static final Logger log = LoggerFactory.getLogger(DlgListadoInasistencia.class);
    @SuppressWarnings("compatibility:398606826956210138")
    private static final long serialVersionUID = 1L;
    Frame myParentFrame;
    FarmaTableModel tableModel;

    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelTitle pnlHeaderRegistro = new JPanelTitle();
    private JLabelFunction lblsc = new JLabelFunction();
    private JScrollPane srcListaInasistencia = new JScrollPane();
    private JTable tblListaInasistencia = new JTable();
    private JButtonLabel btnRegistro = new JButtonLabel();
    private JButtonLabel btnRandoFec = new JButtonLabel();
    private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
    Calendar fecha = Calendar.getInstance();
    private int dias = fecha.get(Calendar.DAY_OF_MONTH) - 1;

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */
    
    public DlgListadoInasistencia() {
       this(null, " ", false);
    }
    
    public DlgListadoInasistencia(Frame parent, String title, boolean modal){
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
        this.setSize(new Dimension(522, 391));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Lista de Inasistencias");
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
        pnlHeaderRegistro.setBounds(new Rectangle(10, 65, 495, 25));
        lblsc.setBounds(new Rectangle(410, 335, 95, 20));
        lblsc.setText("[Esc]Salir");
        srcListaInasistencia.setBounds(new Rectangle(10, 90, 495, 235));
        tblListaInasistencia.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaSolicitud_keyPressed(e);
            }
        });
        btnRegistro.setText("Lista de Inasistencias Registradas");
        btnRegistro.setBounds(new Rectangle(10, 5, 225, 15));
        btnRegistro.setMnemonic('s');
        btnRegistro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRegistro_actionPerformed(e);
            }
        });
        pnlCriterioBusqueda.setBounds(new Rectangle(10, 10, 495, 50));

        btnRandoFec.setText("Lista del personal con inasistencia de los últimos 10 días");
        btnRandoFec.setBounds(new Rectangle(10, 15, 410, 20));

        pnlCriterioBusqueda.add(btnRandoFec, null);
        jContentPane.add(pnlCriterioBusqueda, null);
        srcListaInasistencia.getViewport().add(tblListaInasistencia, null);
        jContentPane.add(srcListaInasistencia, null);
        jContentPane.add(lblsc, null);
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
                new FarmaTableModel(ConstantsControlAsistencia.columnsListaInasistencia, ConstantsControlAsistencia.defaultListaInasistencia,
                                    0);
        FarmaUtility.initSimpleList(tblListaInasistencia, tableModel, ConstantsControlAsistencia.columnsListaInasistencia);
        listarInasistencia();
       
    }

    /* ********************************************************************** */
    /*                      Métodos de eventos                                */
    /* ********************************************************************** */    
    
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

    private void tblListaSolicitud_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }
    private void btnRegistro_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaInasistencia);
    }
    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);    
        validarLogin();
        FarmaUtility.moveFocus(tblListaInasistencia);       
    }

    /* ********************************************************************** */
    /*                   Métodos de lógica de negocio                         */
    /* ********************************************************************** */    

    private void listarInasistencia(){
        tableModel.clearTable();
        ArrayList lstInasistencia=new ArrayList();

        lstInasistencia=UtilityControlAsistencia.listarInasistencias();
        if(lstInasistencia.size()>0){
            tableModel.data= lstInasistencia; 
        }else{
            log.info("No existe datos de Inasistencias para mostrar.");  
        }
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
