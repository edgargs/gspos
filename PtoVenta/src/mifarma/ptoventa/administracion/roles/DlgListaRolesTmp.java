package mifarma.ptoventa.administracion.roles;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;


import mifarma.ptoventa.administracion.impresoras.reference.ConstantsImpresoras;

import mifarma.ptoventa.administracion.impresoras.reference.DBImpresoras;
import mifarma.ptoventa.administracion.roles.reference.ConstantsRolesTmp;
import mifarma.ptoventa.administracion.roles.reference.DBRolesTmp;
import mifarma.ptoventa.administracion.roles.reference.VariablesRolesTmp;
import mifarma.ptoventa.administracion.usuarios.reference.VariablesUsuarios;
import mifarma.ptoventa.caja.DlgOrdenar;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgListaRolesTmp extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaRolesTmp.class);
    @SuppressWarnings("compatibility:398606826956210138")
    private static final long serialVersionUID = 1L;
    Frame myParentFrame;

    FarmaTableModel tableModel;

    private JPanelWhite jContentPane = new JPanelWhite();

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelTitle pnlHeaderListaImp = new JPanelTitle();

    private JLabelFunction lblCrear = new JLabelFunction();

    private JLabelFunction lblModificar = new JLabelFunction();

    private JLabelFunction lblsc = new JLabelFunction();

    private JScrollPane scrListaImpresoras = new JScrollPane();

    private JTable tblListaRoles = new JTable();

    private JButtonLabel btnRelacionImp = new JButtonLabel();
    private JLabelFunction lblF4 = new JLabelFunction();
    private JLabelFunction lblF5 = new JLabelFunction();
    private JLabelFunction lblF6 = new JLabelFunction();
    private JButtonLabel btnRandoFec = new JButtonLabel();
    private JButton btnBuscar = new JButton();
    private JTextFieldSanSerif txtFechaFin = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaIni = new JTextFieldSanSerif();
    private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
  

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListaRolesTmp() {
        this(null, "", false);
    }

    public DlgListaRolesTmp(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(750, 400));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Asignación  de Administración Temporal");
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
        pnlHeaderListaImp.setBounds(new Rectangle(10,65, 700, 25));
        lblCrear.setBounds(new Rectangle(10, 340, 105, 20));
        lblCrear.setText("[F2] Nuevo");
        lblModificar.setBounds(new Rectangle(130, 340, 105, 20));
        lblModificar.setText("[F3] Modificar");
        lblF4.setBounds(new Rectangle(250, 340, 105, 20));
        lblF4.setText("[F4] Eliminar");
        lblF5.setBounds(new Rectangle(370, 340, 105, 20));
        lblF5.setText("[F5] Ver Reportes");
        lblF6.setBounds(new Rectangle(490, 340, 105, 20));
        lblF6.setText("[F6] Ordenar");
        lblsc.setBounds(new Rectangle(610, 340, 95, 20));
        lblsc.setText("[Esc]Salir");
        scrListaImpresoras.setBounds(new Rectangle(10, 95, 700, 235));
        tblListaRoles.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaRoles_keyPressed(e);
            }
        });
        btnRelacionImp.setText("Roles Temporales");
        btnRelacionImp.setBounds(new Rectangle(10, 5, 140, 15));
        btnRelacionImp.setMnemonic('r');
        btnRelacionImp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionImp_actionPerformed(e);
            }
        });
        pnlCriterioBusqueda.setBounds(new Rectangle(10, 10, 700,50));
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(475, 15, 95, 20));
        btnBuscar.setMnemonic('b');
        btnBuscar.setFont(new Font("SansSerif", 1, 11));
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               btnBuscar_actionPerformed(e);
            }
        });
        txtFechaFin.setBounds(new Rectangle(345, 15, 101, 19));
        txtFechaFin.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
               txtFechaFin_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
              txtFechaFin_keyReleased(e);
            }
        });
        txtFechaFin.setDocument(new FarmaLengthText(10));
        txtFechaIni.setBounds(new Rectangle(220, 15, 101, 19));
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
        btnRandoFec.setBounds(new Rectangle(110, 15, 100, 20));
        btnRandoFec.setMnemonic('f');
        btnRandoFec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               btnRandoFec_actionPerformed(e);
            }
        });
        pnlCriterioBusqueda.add(btnBuscar, null);
       
        pnlCriterioBusqueda.add(txtFechaFin, null);
        pnlCriterioBusqueda.add(txtFechaIni, null);
        pnlCriterioBusqueda.add(btnRandoFec, null);
        scrListaImpresoras.getViewport().add(tblListaRoles, null);
        jContentPane.add(pnlCriterioBusqueda, null);
        jContentPane.add(scrListaImpresoras, null);
        jContentPane.add(lblsc, null);
        jContentPane.add(lblModificar, null);
        jContentPane.add(lblCrear, null);
        jContentPane.add(lblF4, null);
        jContentPane.add(lblF5, null);
        jContentPane.add(lblF6, null);
        pnlHeaderListaImp.add(btnRelacionImp, null);
        jContentPane.add(pnlHeaderListaImp, null);
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
                new FarmaTableModel(ConstantsRolesTmp.columnsListaRolesTmp, ConstantsRolesTmp.defaultValuesListaRolTmp,
                                    0);
        FarmaUtility.initSimpleList(tblListaRoles, tableModel,
                                    ConstantsRolesTmp.columnsListaRolesTmp);
      listaRolesTemp();
    }
    

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        VariablesPtoVenta.vEjecutaAccionTecla = false;
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtFechaIni);
        
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************
    private void txtFechaIni_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtFechaFin);
        else
        chkKeyPressed(e);
    }

    private void txtFechaIni_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaIni, e);
    }

    private void txtFechaFin_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            btnBuscar.doClick();
        } else
            chkKeyPressed(e);
    }
    private void txtFechaFin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaFin, e);
    }
    private void btnBuscar_actionPerformed(ActionEvent e) {
      
        lstRangoFecha();
    }
    private void btnRandoFec_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFechaIni);
    }
    private void chkKeyPressed(KeyEvent e) {

        if (!VariablesPtoVenta.vEjecutaAccionTecla) {
            VariablesPtoVenta.vEjecutaAccionTecla = true;
           
            if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {
             nuevoRol();
            } else if (e.getKeyCode() == KeyEvent.VK_F3) {

                editRol();
            }  else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                cerrarVentana(false);
            } else if(e.getKeyCode() == KeyEvent.VK_F4){
                eliminaRegistro();
            }  else if(e.getKeyCode() == KeyEvent.VK_F5){
                 lstReportes();
            }  
                else if (e.getKeyCode() == KeyEvent.VK_F6) {
                           if (tblListaRoles.getRowCount() > 0)
                               ordenar();         
            }
            
           
            VariablesPtoVenta.vEjecutaAccionTecla = false;
        }

    }

    private void btnRelacionImp_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaRoles);
    }
    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void listaRolesTemp(){
        try {
            DBRolesTmp.lstRolesTemporal(tableModel);
            if (tblListaRoles.getRowCount() > 0)
               // FarmaUtility.ordenar(tblListaRoles, tableModel, 6, "asc");
            log.debug("se cargo la lista de de roles temporales");
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al obtener lista de roles temporales. \n " + e.getMessage(),
                                     tblListaRoles);
        }    
    }
    
   

    private void tblListaRoles_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowClosing(WindowEvent e) {
       
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    
    private void nuevoRol(){
        VariablesRolesTmp.vTipo_Accion="I";
        DlgMantenimientoRolTmp rolTmp = new  DlgMantenimientoRolTmp(myParentFrame,"",true);
        rolTmp.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
           listaRolesTemp();
        }
       
    }
    private void editRol()  {
        VariablesRolesTmp.vTipo_Accion="M";
        String dni="";
        String usuario="";
        String fechaInicio="";
        String fechaFin="";
      
        int  row=   tblListaRoles.getSelectedRow();
        if(row>-1){
        dni =tblListaRoles.getValueAt(row, 0).toString();
        usuario=tblListaRoles.getValueAt(row, 1).toString();
       
        fechaInicio=tblListaRoles.getValueAt(row, 2).toString();
        fechaFin=tblListaRoles.getValueAt(row, 3).toString();
        VariablesRolesTmp.vCodigo=dni;
        VariablesRolesTmp.vUsuario=usuario;
         VariablesRolesTmp.vFec_InicioForm=fechaInicio;
        VariablesRolesTmp.vFec_Inicio=fechaInicio.substring(0, 11).trim();
        VariablesRolesTmp.vFec_Fin=fechaFin;

       
        String caducado="";
        String vigFuturo="";
        try {
            caducado = DBRolesTmp.verificaVigenciaRol(dni, fechaInicio, fechaFin).trim();
            vigFuturo= DBRolesTmp.verificaVigenciaFuturoRol(dni, fechaInicio).trim();
            if(caducado.equalsIgnoreCase("TRUE")){
            FarmaUtility.showMessage(this, "El registro no esta vigente..!", null);    
            }else{
             //if(vigFuturo.equalsIgnoreCase("TRUE")){
             
                 cargaMantenimiento();
                 //FarmaUtility.showMessage(this, "A futuro..!", null);    
             //}else{
               //  cargaMantenimiento();  
             //}
                
            } 
        } catch (SQLException e) {
            FarmaUtility.showMessage(this, "Error al verificar la vigencia del Registro..!"+e.getMessage(), null);  
        }
        }else{
            FarmaUtility.showMessage(this, "Debe Seleccionar un Registro..!", null);  
        }
    }
    private void cargaMantenimiento(){
        DlgMantenimientoRolTmp rolTmp = new  DlgMantenimientoRolTmp(myParentFrame,"",true);
        rolTmp.setVisible(true);   
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
           listaRolesTemp();
        }
        
    }
    
    private void eliminaRegistro() {
      String elimina="";
      String vigFuturo="";
        try {
            String dni="";
            String usuario="";
            String fechaInicio="";
            String fechaFin="";
           
            int  row=   tblListaRoles.getSelectedRow();
            if(row>-1){
            dni =tblListaRoles.getValueAt(row, 0).toString();
            usuario=tblListaRoles.getValueAt(row, 1).toString();
            fechaInicio=tblListaRoles.getValueAt(row, 2).toString();
            fechaFin=tblListaRoles.getValueAt(row, 3).toString();
            
            vigFuturo= DBRolesTmp.verificaVigenciaFuturoRol(dni, fechaInicio).trim();
            if(vigFuturo.equalsIgnoreCase("TRUE")){
                if (JConfirmDialog.rptaConfirmDialogDefaultNo(this,
                                                                                "¿Desea Eliminar el Registro?")) {
              
                elimina = DBRolesTmp.eliminaRegistroRolTmp(dni, fechaInicio).trim();
                if(elimina.equalsIgnoreCase("TRUE")){
                FarmaUtility.aceptarTransaccion();
                listaRolesTemp();
                FarmaUtility.showMessage(this, "Registro eliminado..!", null);  
                 
                }else{
                FarmaUtility.showMessage(this, "No se elimino el registro seleccionado..!", null);     
                }
                
           
            }
            }else{
            FarmaUtility.showMessage(this, "Debe seleccionar un registro a Futuro..!", null);    
            
            }
            
            }else{
                FarmaUtility.showMessage(this, "Debe seleccionar un registro..!", null);     
            }
        } catch (SQLException e) {
            
            FarmaUtility.showMessage(this, "Error al eliminar el registro!"+e.getMessage(), null);
        }
    }
    
    private void lstReportes(){
        DlgReportesRolesTmp  lstRolesTmp  = new  DlgReportesRolesTmp(myParentFrame,"",true);
        
        lstRolesTmp.setVisible(true);
    }
    
    private void lstRolesRangoFecha(){
      String fecInicio="";
      String fecFin="";
      fecInicio=txtFechaIni.getText().trim();
      fecFin=txtFechaFin.getText().trim();
        try {
            tableModel.clearTable();
            DBRolesTmp.lstRolesRangoFecha(tableModel,fecInicio,fecFin);
            if (tblListaRoles.getRowCount() > 0)
            
            log.debug("se cargo la lista de de roles temporales");
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al  lista de roles por rango de fecha. \n " + e.getMessage(),
                                     tblListaRoles);
        }    
        
    }
    
    private void lstRangoFecha(){
        if (!validaDatosFecha())
            return;

       
        lstRolesRangoFecha();
        FarmaUtility.moveFocus(txtFechaIni);
        
    }
    private boolean validaDatosFecha() {
        boolean retorno = true;
       
          if (txtFechaIni.getText().trim().equals("")) {
               retorno = false;
               FarmaUtility.showMessage(this, "Ingrese Fecha de Inicio.", txtFechaIni);
           } else if (txtFechaFin.getText().trim().equals("")) {
               retorno = false;
               FarmaUtility.showMessage(this, "Ingrese Fecha de Fin.", txtFechaFin);
           } else if(!FarmaUtility.validaFecha(txtFechaIni.getText(), "dd/MM/yyyy")){
               retorno = false;
               FarmaUtility.showMessage(this, "Formato Incorrecto de Fecha de Inicio", txtFechaIni);
        } else  if (!FarmaUtility.validaFecha(txtFechaFin.getText(), "dd/MM/yyyy")) {
               retorno = false;
               FarmaUtility.showMessage(this, "Formato Incorrecto de Fecha Fin", txtFechaFin);
        
        } else if (!FarmaUtility.validarRangoFechas(this, txtFechaIni, txtFechaFin, false, true, true, true))
                   retorno = false;
            
        
        return retorno;
    }
    
    private void ordenar() {

        DlgOrdenaRolesTmp dlgOrdenRol = new DlgOrdenaRolesTmp(myParentFrame, "", true);
        dlgOrdenRol.setVisible(true);

        if (FarmaVariables.vAceptar) {
            FarmaUtility.ordenar(tblListaRoles, tableModel, Integer.parseInt(VariablesRolesTmp.vColumna),
                                 VariablesRolesTmp.vOrden);
            tblListaRoles.repaint();
         
        }

    }
    
    
}
