package mifarma.ptoventa.controlAsistencia;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
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
import java.sql.SQLException;
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
import mifarma.ptoventa.reference.UtilityPtoVenta;
import org.com.du.JTimeTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2015 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgListadoPlantilla.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * CHUANES                    Creación<br>
 * EMAQUERA      15.10.2015   Modificacion<br>
 * <br>
 * @author CHUANES <br>
 * @version 1.0<br>
 *
 */
public class DlgListadoPlantilla extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListadoPlantilla.class);
    private Frame myParentFrame;
    private FarmaTableModel tableModel;
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelTitle pnlHeaderListaPlantilla = new JPanelTitle();
    private JPanelTitle pnlHeaderListaDetalle = new JPanelTitle();
    private JLabelFunction lblCrear = new JLabelFunction();
    private JLabelFunction lblModificar = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JScrollPane scrListaPlantilla = new JScrollPane();
    private JTable tblListaPlantilla = new JTable();
    private JButtonLabel btnPlantilla = new JButtonLabel();
    private JButtonLabel btnBusqueda = new JButtonLabel();
    private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();
    private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
    private JTimeTable tblHorario = new JTimeTable(UtilityControlAsistencia.getMaxHorasSemana());
    private JLabelFunction btnF12Duplicar = new JLabelFunction();
    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListadoPlantilla() {
        this(null, "", false);
    }

    public DlgListadoPlantilla(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
            if (tblListaPlantilla.getRowCount() > 0) {
                String codHorario = FarmaUtility.getValueFieldArrayList(tableModel.data, 0, 0).toString().trim();
                listarPlantillaDetalle(codHorario);
            }
        } catch (Exception e) {
            log.error("", e);
        }

    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(697, 494));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Lista Plantilla Horario");
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
        pnlHeaderListaPlantilla.setBounds(new Rectangle(10, 50, 675, 15));
        pnlHeaderListaDetalle.setBounds(new Rectangle(10, 215, 675, 25));
        lblCrear.setBounds(new Rectangle(10, 430, 105, 20));
        lblCrear.setText("[F2] Nuevo");
        lblModificar.setBounds(new Rectangle(130, 430, 105, 20));
        lblModificar.setText("[F3] Modificar");
        lblEsc.setBounds(new Rectangle(585, 430, 95, 20));
        lblEsc.setText("[Esc]Salir");
        scrListaPlantilla.setBounds(new Rectangle(10, 65, 675, 150));
        tblHorario.deleteColumnUser();
        tblHorario.setBounds(new Rectangle(10, 240, 675, 180));
        btnF12Duplicar.setText("[F12] Copiar");
        btnF12Duplicar.setBounds(new Rectangle(475, 430, 90, 20));
        tblListaPlantilla.addKeyListener(new KeyAdapter() {

            public void keyReleased(KeyEvent e) {
                tblListaPlantilla_keyReleased(e);
            }

            public void keyPressed(KeyEvent e) {
                tblListaPlantilla_keyPressed(e);
            }


        });
        btnPlantilla.setText("Detalle de Plantillas ");
        btnPlantilla.setBounds(new Rectangle(10, 5, 160, 15));
        btnPlantilla.setMnemonic('r');
        pnlCriterioBusqueda.setBounds(new Rectangle(10, 10, 675, 40));
        txtBuscar.setBounds(new Rectangle(125, 10, 325, 20));
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
        btnBusqueda.setText("B\u00fasqueda Plantilla");
        btnBusqueda.setBounds(new Rectangle(10, 10, 130, 20));
        btnBusqueda.setMnemonic('B');
        btnBusqueda.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        pnlCriterioBusqueda.add(txtBuscar, null);
        pnlCriterioBusqueda.add(btnBusqueda, null);
        jContentPane.add(btnF12Duplicar, null);
        jContentPane.add(pnlCriterioBusqueda, null);
        tblListaPlantilla.getTableHeader().setReorderingAllowed(false) ;
        tblListaPlantilla.getTableHeader().setResizingAllowed(false);
        scrListaPlantilla.getViewport().add(tblListaPlantilla, null);
        scrListaPlantilla.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jContentPane.add(scrListaPlantilla, null);
        jContentPane.add(tblHorario, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblModificar, null);
        jContentPane.add(lblCrear, null);
        jContentPane.add(pnlHeaderListaPlantilla, null);
        pnlHeaderListaDetalle.add(btnPlantilla, null);
        jContentPane.add(pnlHeaderListaDetalle, null);
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
                new FarmaTableModel(ConstantsControlAsistencia.columnsListaPlantilla, ConstantsControlAsistencia.defaultValuescolumnsListaPlantilla,
                                    0);
        FarmaUtility.initSimpleList(tblListaPlantilla, tableModel, ConstantsControlAsistencia.columnsListaPlantilla);
        tblListaPlantilla.getTableHeader().setReorderingAllowed(false) ;
        tblListaPlantilla.getTableHeader().setResizingAllowed(false);        
        listarPlantilla();
    }
    
    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
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
        e.consume();
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaPlantilla, txtBuscar, 1);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (tblListaPlantilla.getSelectedRow() >= 0) {
                if (!(FarmaUtility.findTextInJTable(tblListaPlantilla, txtBuscar.getText().trim(), 0, 1))) {
                    FarmaUtility.showMessage(this, "No existe la plantilla que desea buscar", txtBuscar);
                    return;
                }
            }
        }
        chkKeyPressed(e);
    }

    private void txtBuscar_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblListaPlantilla, txtBuscar, 1);
        listarPlantillaDetalle(FarmaUtility.getValueFieldArrayList(tableModel.data, tblListaPlantilla.getSelectedRow(),
                                                                0).trim());
    }

    private void chkKeyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F2(e)) {
            crearPlantilla();
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            modificarPlantilla();
        } else if (e.getKeyCode() == KeyEvent.VK_F12) {
            copiarPlantilla();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }
    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void listarPlantilla() {
        try {
            UtilityControlAsistencia.listarPlantillas(tableModel);
            FarmaUtility.ordenar(tblListaPlantilla, tableModel, 0, "DESC");
            if (tblListaPlantilla.getRowCount() > 0) {
                String codHorario = FarmaUtility.getValueFieldArrayList(tableModel.data, 0, 0).trim();
                listarPlantillaDetalle(codHorario);
                FarmaUtility.moveFocus(txtBuscar);
            } else {
                tblHorario.clear();
            }
        } catch (Exception e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al listar plantilla \n" +
                    e.getMessage(), txtBuscar);
        }
    }

    public void listarPlantillaDetalle(String codPlantilla) {

            try {
            ArrayList vListaRoles = new ArrayList();
            ArrayList vListaRangoHoras = new ArrayList();
            ArrayList vListaRangoHorasRefrigerio = new ArrayList();
            ArrayList vListaFilaModify = new ArrayList();
            tblHorario.clear();
            UtilityControlAsistencia.listarDetallePlantilla(codPlantilla, vListaFilaModify);
            UtilityControlAsistencia.listarSeleccionRoles(vListaRoles);
            UtilityControlAsistencia.listarSeleccionRangoHoras(vListaRangoHoras);
            UtilityControlAsistencia.listarSeleccionRangoHorasRefrigerio(vListaRangoHorasRefrigerio);
            tblHorario.setListObjRole(vListaRoles);
            tblHorario.setListObjTimes(vListaRangoHoras);
            tblHorario.setListObjTimesRefrigerio(vListaRangoHorasRefrigerio);
            tblHorario.loadDataModify(vListaFilaModify);
            tblHorario.deleteColumnUser();
            tblHorario.setEditable(false);

            
        } catch (Exception sqle) {
            log.error("Error al listar plantilla detalle ");
            FarmaUtility.showMessage(this, "Error en listar detalle de plantilla.\n"+sqle.getMessage(), txtBuscar);
        }

    }

    private void tblListaPlantilla_keyReleased(KeyEvent e) {
        if (tblListaPlantilla.getSelectedRow() > -1)
            listarPlantillaDetalle(FarmaUtility.getValueFieldArrayList(tableModel.data,
                                                                    tblListaPlantilla.getSelectedRow(), 0).trim());
    }

    private void tblListaPlantilla_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void crearPlantilla() {
        DlgGestionPlantilla pln = new DlgGestionPlantilla(myParentFrame, "", true, true, "", "");
        pln.setVisible(true);
        listarPlantilla();
    }

    private void modificarPlantilla() {
        int row = tblListaPlantilla.getSelectedRow();
        if (row > -1) {
            String pEstado = FarmaUtility.getValueFieldArrayList(tableModel.data, row, 5).trim();            
            if(pEstado.trim().equalsIgnoreCase("P"))
            {
                //no puede modificar una plantilla por autorizar.
                FarmaUtility.showMessage(this,"No puede modificar una plantilla por autorizar.", txtBuscar);
            }
            else
            {
                
                String codPlantilla = FarmaUtility.getValueFieldArrayList(tableModel.data, row, 0).trim();
                String nomPlantilla = FarmaUtility.getValueFieldArrayList(tableModel.data, row, 1).trim();
                DlgGestionPlantilla pln =
                    new DlgGestionPlantilla(myParentFrame, "", true, false, codPlantilla, nomPlantilla);
                pln.setVisible(true);
                listarPlantilla();
            }
        } else {
            FarmaUtility.showMessage(this, "Debe de seleccionar una plantilla.", null);
        }
        FarmaUtility.moveFocus(txtBuscar);
    }

    private void txtBuscar_focusLost(FocusEvent e) {
        FarmaUtility.moveFocus(txtBuscar);
    }

    private void copiarPlantilla() {
        int row = tblListaPlantilla.getSelectedRow();
        if (row > -1) {
            if (JConfirmDialog.rptaConfirmDialogDefaultNo(this, 
                                                          "¿Desea copiar la plantilla seleccionada?")) {
                String codPlantilla = FarmaUtility.getValueFieldArrayList(tableModel.data, row, 0).trim();
                try {
                    //
                    UtilityControlAsistencia.copiaPlantilla(codPlantilla);
                    FarmaUtility.aceptarTransaccion();
                    listarPlantilla();
                    FarmaUtility.ordenar(tblListaPlantilla, tableModel, 0, "DESC");
                    if (tblListaPlantilla.getRowCount() > 0) {
                        String codHorario = FarmaUtility.getValueFieldArrayList(tableModel.data, 0, 0).trim();
                        listarPlantillaDetalle(codHorario);
                        FarmaUtility.moveFocus(txtBuscar);
                    } else {
                        tblHorario.clear();
                    }
                } catch (Exception e) {
                    FarmaUtility.liberarTransaccion();
                    log.error("", e);
                    FarmaUtility.showMessage(this, "Error al listar plantilla \n" +
                            e.getMessage(), txtBuscar);
                }

            }

            
        } else {
            FarmaUtility.showMessage(this, "Debe de seleccionar una plantilla.", null);
        }
        FarmaUtility.moveFocus(txtBuscar);
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
