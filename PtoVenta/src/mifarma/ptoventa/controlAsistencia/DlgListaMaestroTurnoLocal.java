package mifarma.ptoventa.controlAsistencia;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.controlAsistencia.reference.ConstantsControlAsistencia;
import mifarma.ptoventa.controlAsistencia.grafico.UtilGraficoHorario;
import mifarma.ptoventa.controlAsistencia.reference.UtilityControlAsistencia;

import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2015 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgListaMaestroTurnoLocal.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * CHUANES                    Creación<br>
 * EMAQUERA      15.10.2015   Modificacion<br>
 * <br>
 * @author CHUANES <br>
 * @version 1.0<br>
 *
 */
public class DlgListaMaestroTurnoLocal extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaMaestroTurnoLocal.class);
    @SuppressWarnings("compatibility:398606826956210138")
    private static final long serialVersionUID = 1L;
    Frame myParentFrame;
    FarmaTableModel tableModel;
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelTitle pnlHeaderListaTurnoLocal = new JPanelTitle();
    private JLabelFunction lblCrear = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JScrollPane scrListaTurnosAsignados = new JScrollPane();
    private JTable tblListaTurnoAsignados = new JTable();
    private JButtonLabel btnTurnosAsignados = new JButtonLabel();
    private JLabelFunction  lblDesasignar=new JLabelFunction();
    private JButtonLabel btnRandoFec = new JButtonLabel();
    private JButton btnBuscar = new JButton();
    private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();
    private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
    private JTabbedPane jTabbedPane1 = new JTabbedPane();
    private JPanel pnlGrafico = new JPanel();
    private GridLayout gridLayout1 = new GridLayout();
    private CardLayout cardLayout = new CardLayout();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgListaMaestroTurnoLocal() {
        this(null, "", false);
    }

    public DlgListaMaestroTurnoLocal(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(760, 540));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Turnos por Local");
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
      
        pnlHeaderListaTurnoLocal.setBounds(new Rectangle(10, 65, 490, 25));
        lblCrear.setBounds(new Rectangle(15, 470, 120, 25));
        lblCrear.setText("[F2] Nuevo Turno");
       
         
        lblDesasignar.setBounds(new Rectangle(185, 470, 130, 25));
        lblDesasignar.setText("[F4] Eliminar Turno"); 
        lblEsc.setBounds(new Rectangle(355, 470, 130, 25));
        lblEsc.setText("[Esc]Salir");
      
        scrListaTurnosAsignados.setBounds(new Rectangle(10, 100, 490, 360));
       
        tblListaTurnoAsignados.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaTurnoLocal_keyPressed(e);
            }
            public void keyReleased(KeyEvent e){
                tblListaTurnoLocal_keyReleased(e);  
            }
        });
      
        btnTurnosAsignados.setText("Lista de Turnos Asignados");
        btnTurnosAsignados.setBounds(new Rectangle(10, 5, 160, 15));
        btnTurnosAsignados.setMnemonic('r');
        btnTurnosAsignados.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnTurnosLocal_actionPerformed(e);
            }
        });
        
        pnlCriterioBusqueda.setBounds(new Rectangle(10, 10, 490, 50));
        jTabbedPane1.setBounds(new Rectangle(505, 5, 240, 490));
        pnlGrafico.setLayout(gridLayout1);
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(475, 15, 95, 20));
        btnBuscar.setMnemonic('b');
        btnBuscar.setFont(new Font("SansSerif", 1, 11));
        btnBuscar.setFocusPainted(false);
       
        txtBuscar.setBounds(new Rectangle(220, 15, 200, 19));
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
               txtBuscar_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                    txtBuscar_keyReleased(e);
                }
        });
        txtBuscar.setDocument(new FarmaLengthText(11));
       
        btnRandoFec.setText("Ingresar descripci\u00f3n turno:");
        btnRandoFec.setBounds(new Rectangle(55, 15, 155, 20));
        btnRandoFec.setMnemonic('t');
        btnRandoFec.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                       btnBuscar_actionPerformed(e);
                    }
                });
      

        pnlCriterioBusqueda.add(txtBuscar, null);
      
        pnlCriterioBusqueda.add(btnRandoFec, null);
     
        scrListaTurnosAsignados.getViewport().add(tblListaTurnoAsignados, null);
        jTabbedPane1.addTab("Gráfico", pnlGrafico);
        jContentPane.add(jTabbedPane1, null);
        jContentPane.add(pnlCriterioBusqueda, null);

        jContentPane.add(scrListaTurnosAsignados, null);


        jContentPane.add(lblEsc, null);
        jContentPane.add(lblCrear, null);
        jContentPane.add(lblDesasignar, null);
        pnlHeaderListaTurnoLocal.add(btnTurnosAsignados, null);

        jContentPane.add(pnlHeaderListaTurnoLocal, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ********************************************************************** */
    /*                        Método "initialize()"                           */
    /* ********************************************************************** */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTable();
    }

    /* ********************************************************************** */
    /*                      Métodos de inicialización                         */
    /* ********************************************************************** */

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsControlAsistencia.columnsListaTurno, ConstantsControlAsistencia.defaultValuescolumnsListaTurno,
                                    0);
        FarmaUtility.initSimpleList(tblListaTurnoAsignados, tableModel, ConstantsControlAsistencia.columnsListaTurno);
        listaMaestroTurnoLocal();
        cargaPorDefecto();
    }
    
    /* ********************************************************************** */
    /*                      Métodos de eventos                                */
    /* ********************************************************************** */

    private void this_windowOpened(WindowEvent e) {
        VariablesPtoVenta.vEjecutaAccionTecla = false;
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtBuscar);
        validarLogin();
    }
    private void btnBuscar_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtBuscar);
    }
    
    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************
    
    private void txtBuscar_keyPressed(KeyEvent e) {
     FarmaGridUtils.aceptarTeclaPresionada(e, tblListaTurnoAsignados, txtBuscar, 1);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            if (tblListaTurnoAsignados.getSelectedRow() >= 0) {
                if (!(FarmaUtility.findTextInJTable(tblListaTurnoAsignados, txtBuscar.getText().trim(), 0, 1))) {
                    FarmaUtility.showMessage(this, "Turno No Encontrado según Criterio de Búsqueda !!!",
                                             txtBuscar);
                    return;
                }
            }
        }         
            chkKeyPressed(e);
    }
    
    private void txtBuscar_keyReleased(KeyEvent e) {
        chkKeyReleased(e);
        actualizaGrafico(tblListaTurnoAsignados.getSelectedRow());
    }
   
    private void chkKeyPressed(KeyEvent e) {
            if (UtilityPtoVenta.verificaVK_F2(e)) {
                nuevoTurno();
            }else if(e.getKeyCode()==KeyEvent.VK_F4){
                desasignarTurno(); 
            }else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                cerrarVentana(false);
            }        
    }

    private void btnTurnosLocal_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaTurnoAsignados);
    }
    
    /* ********************************************************************** */
    /*                   Métodos de lógica de negocio                         */
    /* ********************************************************************** */

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void listaMaestroTurnoLocal(){
        UtilityControlAsistencia facade=new UtilityControlAsistencia();
          tableModel.clearTable();
          tableModel.data=facade.lstTurnoTurno();
          if (tblListaTurnoAsignados.getRowCount() > 0) {
              FarmaUtility.moveFocusJTable(tblListaTurnoAsignados);
            log.debug("se cargo el maestro de turno local");
          }       
    }
    private void tblListaTurnoLocal_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }
    private void tblListaTurnoLocal_keyReleased(KeyEvent e){
        actualizaGrafico(tblListaTurnoAsignados.getSelectedRow());
    }

    private void chkKeyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblListaTurnoAsignados, txtBuscar, 1);
    }
    private void this_windowClosing(WindowEvent e) {       
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    
    private void nuevoTurno(){
        DlgListaMaestroTurno turno = new  DlgListaMaestroTurno(myParentFrame,"",true);
        turno.setVisible(true);
        
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;        
            listaMaestroTurnoLocal();
        } 
    }
    
    public void desasignarTurno(){
       UtilityControlAsistencia facade=new  UtilityControlAsistencia();
        String codTurno="";
        String desTurno="";
        int fila=tblListaTurnoAsignados.getSelectedRow();
        if(fila>-1){
        codTurno=FarmaUtility.getValueFieldArrayList(tableModel.data,fila,0).trim();
        desTurno=FarmaUtility.getValueFieldArrayList(tableModel.data,fila,1).trim();
        if (JConfirmDialog.rptaConfirmDialogDefaultNo(this, "¿Desea eliminar el turno?")) {

            if(!facade.isTurnoGeneroPlantillaTurno(codTurno,desTurno)){
                //ANTES DE ELIMINAR EVALUAMOS Y EL REGISTRO YA GENERO PLANTILLA
                 return;  
            }    
            //AQUI DEBE IR EL METODO DE ELIMNIAR--PENDIENTE PARA EL MARTES
            if(!facade.isTurnoGeneroHorarioTurno(codTurno,desTurno)){
                //ANTES DE ELIMINAR EVALUAMOS Y EL REGISTRO YA GENERO PLANTILLA
                return; 
            }
            
            if(!facade.isDesasignarTurnoTurno(codTurno)){
             return;   
            }
            
               listaMaestroTurnoLocal();
               FarmaUtility.moveFocus(txtBuscar);          
            }     
        }else{
            FarmaUtility.showMessage(this, "Seleccionar Registro!", null);        
        } 
    }

    private void actualizaGrafico(int i) {
        UtilGraficoHorario vBorrado = new UtilGraficoHorario();
        ArrayList vLista = new ArrayList();
        vBorrado.setData(vLista);
        pnlGrafico.removeAll();
        pnlGrafico.setLayout(cardLayout);
        pnlGrafico.add(vBorrado.getGraficoHorario());        
        pnlGrafico.revalidate();
        
        if(i>=0){
            pnlGrafico.removeAll();
            UtilGraficoHorario vUtilGui = new UtilGraficoHorario();
            ArrayList vList = new ArrayList();
            vList.add(tableModel.data.get(i));
            vUtilGui.setData(vList);
            pnlGrafico.setLayout(cardLayout);
            pnlGrafico.add(vUtilGui.getGraficoHorario());
        }
    }

    private void cargaPorDefecto() {
        if(tblListaTurnoAsignados.getRowCount()>0){
        Rectangle rect = tblListaTurnoAsignados.getCellRect(0, 0, true);
        tblListaTurnoAsignados.scrollRectToVisible(rect);
        tblListaTurnoAsignados.clearSelection();
        tblListaTurnoAsignados.setRowSelectionInterval(0, 0);
            actualizaGrafico(0);
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
