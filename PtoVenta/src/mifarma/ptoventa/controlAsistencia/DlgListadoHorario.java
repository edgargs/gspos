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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.controlAsistencia.reference.UtilityControlAsistencia;
import mifarma.ptoventa.controlAsistencia.reference.ConstantsControlAsistencia;
import mifarma.ptoventa.controlAsistencia.reference.DBControlAsistencia;

import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.com.du.JTimeTable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2015 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgListadoHorario.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * CHUANES                    Creación<br>
 * EMAQUERA      15.10.2015   Modificacion<br>
 * <br>
 * @author CHUANES <br>
 * @version 1.0<br>
 *
 */
public class DlgListadoHorario extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListadoHorario.class);
    private Frame myParentFrame;
    private FarmaTableModel tableModel;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle pnlHeaderListaHorario = new JPanelTitle();
    private JPanelTitle pnlHeaderListaHorarioDet = new JPanelTitle();
    private JLabelFunction lblCrear = new JLabelFunction();
    private JLabelFunction lblModificar = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblImprimir = new JLabelFunction();
    private JScrollPane scrListaTurnos = new JScrollPane();
    private JTable tblListaHorario = new JTable();
    private JTimeTable tblDetalleHorario = new JTimeTable(UtilityControlAsistencia.getMaxHorasSemana());
    private JButtonLabel btnTurnos = new JButtonLabel();
    private JButtonLabel btnTurnosAsignados = new JButtonLabel();
    private JButtonLabel btnIngresar = new JButtonLabel();
    private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();
    private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
    private JLabelFunction fnVerReporte = new JLabelFunction();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListadoHorario() {
        this(null, "", false);
    }

    public DlgListadoHorario(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(845, 535));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Lista de Horarios Local");
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
        pnlHeaderListaHorario.setBounds(new Rectangle(10, 50, 815, 25));
        pnlHeaderListaHorarioDet.setBounds(new Rectangle(10, 255, 815, 25));
        lblCrear.setBounds(new Rectangle(10, 470, 105, 20));
        lblCrear.setText("[F2] Nuevo");
        lblModificar.setBounds(new Rectangle(130, 470, 105, 20));
        lblModificar.setText("[F3] Modificar");
        lblImprimir.setBounds(new Rectangle(515, 470, 105, 20));
        lblImprimir.setText("[F4] Imprimir");

        lblEsc.setBounds(new Rectangle(660, 470, 95, 20));
        lblEsc.setText("[Esc]Salir");
        scrListaTurnos.setBounds(new Rectangle(10, 75, 815, 180));
        tblDetalleHorario.setBounds(new Rectangle(10, 280, 815, 180));
        tblListaHorario.addKeyListener(new KeyAdapter() {

            public void keyReleased(KeyEvent e) {
                tblListaHorario_keyReleased(e);
            }

            public void keyPressed(KeyEvent e) {
                tblListaPlantilla_keyPressed(e);
            }


        });

        btnTurnos.setText("Listado de Horarios");
        btnTurnos.setBounds(new Rectangle(10, 5, 140, 15));
        btnTurnos.setMnemonic('i');
        btnTurnos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnTurnos_actionPerformed(e);
            }
        });
        btnTurnosAsignados.setText("Detalle de Horarios ");
        btnTurnosAsignados.setBounds(new Rectangle(10, 5, 160, 15));
        btnTurnosAsignados.setMnemonic('r');

        pnlCriterioBusqueda.setBounds(new Rectangle(10, 10, 815, 40));

        fnVerReporte.setText("[F5] Ver Horario");
        fnVerReporte.setBounds(new Rectangle(255, 470, 100, 20));
        txtBuscar.setBounds(new Rectangle(165, 10, 415, 20));
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtBuscar_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtBuscar_keyReleased(e);
            }
        });
        txtBuscar.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                txtBuscar_focusLost(e);
            }
        });
        txtBuscar.setDocument(new FarmaLengthText(50));

        btnIngresar.setText("Buscar por Incio Semana:");
        btnIngresar.setBounds(new Rectangle(10, 10, 150, 20));
        btnIngresar.setMnemonic('u');
        btnIngresar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });

        pnlCriterioBusqueda.add(txtBuscar, null);
        pnlCriterioBusqueda.add(btnIngresar, null);
        jContentPane.add(fnVerReporte, null);
        jContentPane.add(pnlCriterioBusqueda, null);
        scrListaTurnos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrListaTurnos.getViewport().add(tblListaHorario, null);
        jContentPane.add(scrListaTurnos, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(tblDetalleHorario, null);
        jContentPane.add(lblModificar, null);
        jContentPane.add(lblCrear, null);
        jContentPane.add(lblImprimir, null);
        pnlHeaderListaHorario.add(btnTurnos, null);
        jContentPane.add(pnlHeaderListaHorario, null);
        pnlHeaderListaHorarioDet.add(btnTurnosAsignados, null);
        jContentPane.add(pnlHeaderListaHorarioDet, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTable();
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsControlAsistencia.columnsListaHorario, ConstantsControlAsistencia.defaultValuescolumnsListaHorario,
                                    0);
        FarmaUtility.initSimpleList(tblListaHorario, tableModel, ConstantsControlAsistencia.columnsListaHorario);
        tblListaHorario.getTableHeader().setReorderingAllowed(false);
        tblListaHorario.getTableHeader().setResizingAllowed(false);
        listarDetalleHorario();
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        validarLogin();
        FarmaUtility.moveFocus(txtBuscar);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtBuscar);
    }
    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************


    private void txtBuscar_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaHorario, txtBuscar, 1);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            if (tblListaHorario.getSelectedRow() >= 0) {
                if (!(FarmaUtility.findTextInJTable(tblListaHorario, txtBuscar.getText().trim(), 0, 1))) {
                    FarmaUtility.showMessage(this, "Plantilla No Encontrado según Criterio de Búsqueda !!!",
                                             txtBuscar);
                    return;
                }
            }
        }
        chkKeyPressed(e);
    }

    private void txtBuscar_keyReleased(KeyEvent e) {
        int row = tblListaHorario.getSelectedRow();
        String codHorario = "";
        if (row > -1) {
            codHorario = FarmaUtility.getValueFieldArrayList(tableModel.data, row, 0).toString().trim();
            listaHorarioDetalle(codHorario);
        }
    }

    private void tblListaPlantilla_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void chkKeyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F2(e)) {
            crearHorario();
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            modificarHorario();
        } else if (e.getKeyCode() == KeyEvent.VK_F4) {
            imprimirHorario();
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            verHorario();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void btnTurnos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaHorario);
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void listarDetalleHorario() {
        try {
            UtilityControlAsistencia.lstCabeceraHorario(tableModel);
            FarmaUtility.ordenar(tblListaHorario, tableModel, 0, "DESC");
            if (tblListaHorario.getRowCount() > 0) {
                String codHorario = FarmaUtility.getValueFieldArrayList(tableModel.data, 0, 0).trim();
                listaHorarioDetalle(codHorario);
                FarmaUtility.moveFocus(txtBuscar);
            } else {
                tblDetalleHorario.clear();
            }
        } catch (Exception e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al listar horarios \n" +
                    e.getMessage(), txtBuscar);
        }
    }

    private void listaHorarioDetalle(String codHorario) {
        try {
            ArrayList vListaRoles = new ArrayList();
            ArrayList vListaUsuarioRol = new ArrayList();
            ArrayList vListaRangoHoras = new ArrayList();
            ArrayList vListaRangoHorasRefrigerio = new ArrayList();
            ArrayList vListaFilaModify = new ArrayList();
            
            
            UtilityControlAsistencia.listarSeleccionRoles(vListaRoles);
            UtilityControlAsistencia.listarSeleccionUsuarios(vListaUsuarioRol);
            UtilityControlAsistencia.listarSeleccionRangoHoras(vListaRangoHoras);
            UtilityControlAsistencia.listarSeleccionRangoHorasRefrigerio(vListaRangoHorasRefrigerio);
            UtilityControlAsistencia.lstDataModifyHorario(codHorario, vListaFilaModify);
            
            tblDetalleHorario.clear();
            tblDetalleHorario.setListObjRole(vListaRoles);
            tblDetalleHorario.setListObjUser(vListaUsuarioRol);
            tblDetalleHorario.setListObjTimes(vListaRangoHoras);
            tblDetalleHorario.setListObjTimesRefrigerio(vListaRangoHorasRefrigerio);
            tblDetalleHorario.loadDataModify(vListaFilaModify);
            tblDetalleHorario.setEditable(false);
        } catch (Exception e) {
            log.error("error al listar plantilla detalle " + e.getMessage());
            FarmaUtility.showMessage(this, "Error en listar detalle de Horario.\n" +
                    e.getMessage(), txtBuscar);
        }
    }

    private void tblListaHorario_keyReleased(KeyEvent e) {
        if (tblListaHorario.getSelectedRow() > -1)
            listaHorarioDetalle(FarmaUtility.getValueFieldArrayList(tableModel.data, tblListaHorario.getSelectedRow(),
                                                                    0).trim());
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void crearHorario() {
        if (isValidoEstadoHorario()) {
            DlgGestionHorario pln = new DlgGestionHorario(myParentFrame, "", true, true, "", "", "","");
            pln.setVisible(true);
            listarDetalleHorario();
        }
    }

    public boolean isValidoEstadoHorario() {
        /*String resultado = "";
        for (int i = 0; i < tblListaHorario.getRowCount(); i++) {
            String codPlantilla = FarmaUtility.getValueFieldArrayList(tableModel.data, i, 0);
            try {
                resultado = DBControlAsistencia.verificaEstadoHorariohorariolocal(codPlantilla).toString().trim();
                if (resultado.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                    FarmaUtility.showMessage(this, "Existen horarios en proceso. \nDebe finalizarnos para continuar.",
                                             null);
                    return false;
                }
            } catch (Exception e) {
                log.info("" + e);
                FarmaUtility.showMessage(this, "Error al verificar el estado de la plantilla", null);
                return false;
            }
        }*/
        return true;
    }

    private void modificarHorario() {
        int row = tblListaHorario.getSelectedRow();
        if (row > -1) {
            String codHorario = FarmaUtility.getValueFieldArrayList(tableModel.data, row, 0).toString().trim();
            String fecInicio  = FarmaUtility.getValueFieldArrayList(tableModel.data, row, 1).toString().trim().substring(0, 10).trim();
            String fecFin     = FarmaUtility.getValueFieldArrayList(tableModel.data, row, 2).toString().trim().substring(0, 10).trim();
            String codPlantilla = FarmaUtility.getValueFieldArrayList(tableModel.data, row, 5).toString().trim();
            //if(UtilityControlAsistencia.isEditableHorario(codHorario)) {
            // dubilluz 25.10.2015
            // se quito la validacion por indicacion  
            DlgGestionHorario gestionHorario = new DlgGestionHorario(myParentFrame, "",true, false, 
                                                                     codHorario, fecInicio, fecFin,codPlantilla);
            gestionHorario.setVisible(true);
            /*}
            else
                FarmaUtility.showMessage(this,
                                         "El horario seleccionado no se puede modificar! n\n" +
                                         "Por que esta programado en esta semana.",
                                         null);*/
            listarDetalleHorario();
        } else {
            FarmaUtility.showMessage(this, "Debe seleccionar un horario para poder modificar.", null);
        }
    }

    private void imprimirHorario() {
        int row = tblListaHorario.getSelectedRow();
        if (row > -1) {
            String codHorario = FarmaUtility.getValueFieldArrayList(tableModel.data, row, 0).toString().trim();
            String fecInicio  = FarmaUtility.getValueFieldArrayList(tableModel.data, row, 1).toString().trim().substring(0, 10).trim();
            String fecFin     = FarmaUtility.getValueFieldArrayList(tableModel.data, row, 2).toString().trim().substring(0, 10).trim();
            DlgImprimeHorarioProgress imprimir = new DlgImprimeHorarioProgress(myParentFrame, "", true);
            imprimir.setImprimeReporte(codHorario, fecInicio, fecFin);
            imprimir.mostrar();
        } else {
            FarmaUtility.showMessage(this, "Seleccionar registro a Imprimir!", null);
        }
    }

    private void txtBuscar_focusLost(FocusEvent e) {
        FarmaUtility.moveFocus(txtBuscar);
    }

    private void verHorario() {

        int row = tblListaHorario.getSelectedRow();
        if (row > -1) {
            String codHorario = FarmaUtility.getValueFieldArrayList(tableModel.data, row, 0).toString().trim();
            String fecInicio  = FarmaUtility.getValueFieldArrayList(tableModel.data, row, 1).toString().trim().substring(0, 10).trim();
            String fecFin     = FarmaUtility.getValueFieldArrayList(tableModel.data, row, 2).toString().trim().substring(0, 10).trim();
            String codPlantilla = FarmaUtility.getValueFieldArrayList(tableModel.data, row, 5).toString().trim();
            DlgGestionHorario gestionHorario = new DlgGestionHorario(myParentFrame, "",true, false, 
                                                                     codHorario, fecInicio, fecFin,codPlantilla,
                                                                     true);
            gestionHorario.setVisible(true);
            listarDetalleHorario();
        } else {
            FarmaUtility.showMessage(this, "Debe seleccionar un horario para poder modificar.", null);
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
