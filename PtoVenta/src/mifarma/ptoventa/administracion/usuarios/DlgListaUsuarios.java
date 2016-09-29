package mifarma.ptoventa.administracion.usuarios;


import com.digitalpersona.onetouch.DPFPFingerIndex;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
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

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.usuarios.reference.ConstantsUsuarios;
import mifarma.ptoventa.administracion.usuarios.reference.DBUsuarios;
import mifarma.ptoventa.administracion.usuarios.reference.VariablesUsuarios;
import mifarma.ptoventa.lectorHuella.DlgRegistroLectorHuella;
import mifarma.ptoventa.lectorHuella.DlgSelectorHuella;
import mifarma.ptoventa.reference.ConstantsPtoVenta;

import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgListaUsuarios extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaUsuarios.class);

    Frame myParentFrame;

    FarmaTableModel tableModel;

    private JPanelWhite jContentPane = new JPanelWhite();

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelTitle pnlBusqueda = new JPanelTitle();

    private JPanelTitle pnlHeaderUsuarios = new JPanelTitle();

    private JLabelWhite lblLocal_T = new JLabelWhite();

    private JLabelWhite lblLocal = new JLabelWhite();

    private JButtonLabel btnApePaterno = new JButtonLabel();

    private JTextFieldSanSerif txtApePaterno = new JTextFieldSanSerif();


    private JScrollPane scrListaUsuarios = new JScrollPane();

    private JTable tblListausuarios = new JTable();

    private JLabelFunction lblCrear = new JLabelFunction();

    private JLabelFunction lblModificar = new JLabelFunction();

    private JLabelFunction lblRolesAsig = new JLabelFunction();

    private JLabelFunction lblActivDesactiv = new JLabelFunction();

    private JLabelFunction lblSalir = new JLabelFunction();

    private JButtonLabel btnRelacion = new JButtonLabel();
    private JLabelFunction lblF6 = new JLabelFunction();
    private JLabelFunction lblF9 = new JLabelFunction();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListaUsuarios() {
        this(null, "", false);
    }

    public DlgListaUsuarios(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(665, 435));
        this.getContentPane().setLayout(borderLayout1);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setLayout(null);
        pnlBusqueda.setBounds(new Rectangle(10, 10, 635, 30));
        pnlHeaderUsuarios.setBounds(new Rectangle(10, 45, 635, 25));
        lblLocal_T.setText("LOCAL:");
        lblLocal_T.setBounds(new Rectangle(5, 5, 55, 20));
        lblLocal.setText("000 - MATRIZ");
        lblLocal.setBounds(new Rectangle(65, 5, 155, 20));
        btnApePaterno.setText("APELLIDO PATERNO :");
        btnApePaterno.setBounds(new Rectangle(250, 5, 115, 20));
        btnApePaterno.setMnemonic('a');
        btnApePaterno.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnApePaterno_actionPerformed(e);
            }
        });
        txtApePaterno.setBounds(new Rectangle(370, 5, 215, 20));
        txtApePaterno.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                txtApePaterno_keyReleased(e);
            }

            public void keyPressed(KeyEvent e) {
                txtApePaterno_keyPressed(e);
            }
        });


        scrListaUsuarios.setBounds(new Rectangle(10, 70, 635, 280));
        tblListausuarios.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListausuarios_keyPressed(e);
            }
        });
        lblCrear.setBounds(new Rectangle(80, 355, 75, 20));
        lblCrear.setText("[F2] Crear");
        lblModificar.setBounds(new Rectangle(160, 355, 95, 20));
        lblModificar.setText("[F3] Modificar");
        lblRolesAsig.setBounds(new Rectangle(400, 355, 125, 20));
        lblRolesAsig.setText("[F5] Roles Asignados");
        lblActivDesactiv.setBounds(new Rectangle(260, 355, 135, 20));
        lblActivDesactiv.setText("[F4] Activar/Desactivar");
        lblSalir.setBounds(new Rectangle(530, 380, 115, 20));
        lblSalir.setText("[ESC] Cerrar");
        btnRelacion.setText("Relación de Usuarios :");
        btnRelacion.setBounds(new Rectangle(15, 5, 160, 15));
        btnRelacion.setMnemonic('r');
        btnRelacion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacion_actionPerformed(e);
            }
        });
        lblF6.setBounds(new Rectangle(530, 355, 115, 20));
        lblF6.setText("[F6] Ver Todos");
        lblF9.setBounds(new Rectangle(400, 380, 125, 20));
        lblF9.setText("[F9] Huella Dactilar");
        jContentPane.add(lblF6, null);
        jContentPane.add(lblSalir, null);
        jContentPane.add(lblActivDesactiv, null);
        jContentPane.add(lblRolesAsig, null);
        jContentPane.add(lblModificar, null);
        jContentPane.add(lblCrear, null);
        jContentPane.add(lblF9, null);
        scrListaUsuarios.getViewport().add(tblListausuarios, null);
        jContentPane.add(scrListaUsuarios, null);
        pnlHeaderUsuarios.add(btnRelacion, null);
        jContentPane.add(pnlHeaderUsuarios, null);
        jContentPane.add(pnlBusqueda, null);
        pnlBusqueda.add(txtApePaterno, null);
        pnlBusqueda.add(btnApePaterno, null);
        pnlBusqueda.add(lblLocal, null);
        pnlBusqueda.add(lblLocal_T, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
        VariablesUsuarios.vInactivo = false;
        tableModel =
                new FarmaTableModel(ConstantsUsuarios.columnsListaUsuarios, ConstantsUsuarios.defaultValuesListaUsuarios,
                                    0);
        FarmaUtility.initSimpleList(tblListausuarios, tableModel, ConstantsUsuarios.columnsListaUsuarios);
        evaluaTitulo();
        //cargaListaUsuarios();
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtApePaterno);
        mostrarDatos();
    }

    private void txtApePaterno_keyReleased(KeyEvent e) {
        chkKeyReleased(e);
    }

    private void txtApePaterno_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListausuarios, txtApePaterno, 1);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            if (tblListausuarios.getSelectedRow() >= 0) {
                if (!(FarmaUtility.findTextInJTable(tblListausuarios, txtApePaterno.getText().trim(), 0, 1))) {
                    FarmaUtility.showMessage(this, "Usuario No Encontrado según Criterio de Búsqueda !!!",
                                             txtApePaterno);
                    return;
                } else {
                    // FarmaUtility.setCheckValue(tblListaProductos, false);
                    // refrescarlistaProductosSeleccion();
                }
            }
        }

        chkKeyPressed(e);
    }

    private void tblListausuarios_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void chkKeyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblListausuarios, txtApePaterno, 1);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F1(e)) // Reservado para ayuda
        {
        } else if (UtilityPtoVenta.verificaVK_F2(e)) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, btnRelacion);
            else {
                VariablesUsuarios.limpiar();
                VariablesUsuarios.vF3 = false;
                VariablesUsuarios.vAccionCrear = true;
                DlgMantUsuarios dlgMantUsuarios = new DlgMantUsuarios(this.myParentFrame, "", true);
                dlgMantUsuarios.setVisible(true);
                if (FarmaVariables.vAceptar) {
                    //cargaListaUsuariosActivos();
                    VariablesUsuarios.vInactivo = false;
                    evaluaTitulo();
                    FarmaVariables.vAceptar = false;
                }
                VariablesUsuarios.vAccionCrear = false;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, btnRelacion);
            else if (tieneRegistroSeleccionado(tblListausuarios)) {
                /**
             * Valida accion al modificar datos de usuario
             * @author : JCORTEZ
             * @since  : 20.07.2007
             */
                VariablesUsuarios.vfocus = true;
                VariablesUsuarios.vF3 = true;
                cargarUsuarioSeleccionado();
                validarCodigoTrabajador();
                VariablesUsuarios.vModificarUsuario = validaCamposModificar(VariablesUsuarios.vCodTrab_RRHH);
                log.debug("VariablesUsuarios.vModificarUsuario " + VariablesUsuarios.vModificarUsuario);
                DlgMantUsuarios dlgMantUsuarios = new DlgMantUsuarios(this.myParentFrame, "", true);
                dlgMantUsuarios.setVisible(true);
                if (FarmaVariables.vAceptar) {
                    //cargaListaUsuariosActivos();
                    VariablesUsuarios.vInactivo = false;
                    evaluaTitulo();
                    FarmaVariables.vAceptar = false;
                }
                VariablesUsuarios.vModificarUsuario = "";
                log.debug("VariablesUsuarios.vModificarUsuario " + VariablesUsuarios.vModificarUsuario);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F4) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, btnRelacion);
            else {
                if (tieneRegistroSeleccionado(this.tblListausuarios)) {
                    if (JConfirmDialog.rptaConfirmDialog(this,
                                                                                    "¿Esta seguro de cambiar el estado al usuario?")) {
                        cargarUsuarioSeleccionado();
                        try {
                            /**
             * VALida si tiene caja asignada
             * @author : dubilluz
             * @since  : 11.07.2007
             */
                            String num_caja_pago = busca_Caja_Asignada().trim();
                            if (!num_caja_pago.equalsIgnoreCase("N")) { //usuario tiene caja asignada
                                if (!busca_cajeros_caja()) { //NO existe usuarios sin asigna caja (false)
                                    if (obtiene_estado_caja(num_caja_pago).equalsIgnoreCase("A")) {
                                        String evento = modifica_estado_caja(num_caja_pago.trim());
                                        if ((evento.trim()).equalsIgnoreCase("fallo"))
                                            return;
                                    }
                                } else {
                                    //SI existe usuarios sin asigna caja (true)
                                    FarmaUtility.showMessage(this,
                                                             "No se puede cambiar Estado, porque existen cajeros sin cajas asignadas.",
                                                             txtApePaterno);
                                    return;
                                }
                            }
                            eliminarSeleccionado();
                            FarmaUtility.aceptarTransaccion();
                            //cargaListaUsuariosActivos();
                            VariablesUsuarios.vInactivo = false;
                            evaluaTitulo();
                            FarmaUtility.showMessage(this, "La operación se realizó correctamente", txtApePaterno);

                        } catch (SQLException ex) {
                            FarmaUtility.liberarTransaccion();
                            //log.error("",ex);
                            String mensaje = "";

                            if (ex.getErrorCode() == 20015) {
                                mensaje = "No se puede inactivar a un usuario que este asignado a una caja.";
                            } /*else if(ex.getErrorCode()==20009){
                mensaje="No se puede inactivar , el usuario tiene asignada una caja aperturada";
              }*/
                            else {
                                mensaje = ex.getMessage();
                            }
                            FarmaUtility.showMessage(this, "Error al eliminar registro  : \n" +
                                    mensaje, txtApePaterno);
                        }
                    }
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {

            if (tieneRegistroSeleccionado(tblListausuarios)) {
                cargarUsuarioSeleccionado();
                DlgListaRolesAsignados dlgListaRolesAsignados =
                    new DlgListaRolesAsignados(this.myParentFrame, "", true);
                dlgListaRolesAsignados.setVisible(true);
                if (FarmaVariables.vAceptar) {
                    //cargaListaUsuariosActivos();
                    VariablesUsuarios.vInactivo = false;
                    evaluaTitulo();
                    FarmaVariables.vAceptar = false;
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F6) {
            evaluaTitulo();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_F9) {
            registrarHuella();
        }
    }
    
    private void registrarHuella(){
        String secUsu = FarmaUtility.getValueFieldArrayList(tableModel.data, tblListausuarios.getSelectedRow(), 0);
        //DlgRegistroLectorHuella dlg = new DlgRegistroLectorHuella(myParentFrame,"Busqueda de Huella Dactilar", true, "");
        //DlgRegistroLectorHuella dlg = new DlgRegistroLectorHuella(myParentFrame,"Registro Huella Dactilar", true, secUsu, true);
        DlgSelectorHuella dlg = new DlgSelectorHuella(myParentFrame, "", true);
        DPFPFingerIndex posicionHuella = dlg.getPosicionHuella();
        if(posicionHuella==null){
            dlg.setVisible(true);
        }
        posicionHuella = dlg.getPosicionHuella();
        if(posicionHuella != null){
            DlgRegistroLectorHuella dlgRegistro = new DlgRegistroLectorHuella(myParentFrame,"Registro Huella Dactilar", true, secUsu, true, posicionHuella);
            dlgRegistro.setVisible(true);
            
        }
        
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cargaListaUsuariosActivos() {
        try {
            DBUsuarios.getListaUsuarios(tableModel, ConstantsUsuarios.ESTADO_ACTIVO);

            if (tblListausuarios.getRowCount() > 0)
                FarmaUtility.ordenar(tblListausuarios, tableModel, 1, "asc");
            log.debug("se cargo la lista de usuarios");
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al obtener los usuarios. \n " + e.getMessage(), tblListausuarios);
        }
    }

    private void cargaListaTodosUsuarios() {
        try {
            DBUsuarios.getListaUsuarios(tableModel, ConstantsUsuarios.USUARIO_TODOS);

            if (tblListausuarios.getRowCount() > 0)
                FarmaUtility.ordenar(tblListausuarios, tableModel, 1, "asc");
            log.debug("se cargo la lista de usuarios");
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al obtener los usuarios. \n " + e.getMessage(), tblListausuarios);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void cargarUsuarioSeleccionado() {
        VariablesUsuarios.vSecUsuLocal =
                tblListausuarios.getValueAt(tblListausuarios.getSelectedRow(), 0).toString().trim();
        VariablesUsuarios.vLoginUsu =
                tblListausuarios.getValueAt(tblListausuarios.getSelectedRow(), 4).toString().trim();
        /*VariablesUsuarios.vClaveUsu = tblListausuarios.getValueAt(
				tblListausuarios.getSelectedRow(), 6).toString().trim();*/

        //Modificado por Dveliz 02.09.08
        try {
            VariablesUsuarios.vClaveUsu = DBUsuarios.obtenerClave(VariablesUsuarios.vSecUsuLocal);
        } catch (SQLException e) {
            log.error("", e);
            VariablesUsuarios.vClaveUsu = "";
        }
        VariablesUsuarios.vApePat =
                tblListausuarios.getValueAt(tblListausuarios.getSelectedRow(), 1).toString().trim();
        VariablesUsuarios.vApeMat =
                tblListausuarios.getValueAt(tblListausuarios.getSelectedRow(), 2).toString().trim();
        VariablesUsuarios.vNombres =
                tblListausuarios.getValueAt(tblListausuarios.getSelectedRow(), 3).toString().trim();
        VariablesUsuarios.vDireccion =
                tblListausuarios.getValueAt(tblListausuarios.getSelectedRow(), 6).toString().trim();
        VariablesUsuarios.vTelefono =
                tblListausuarios.getValueAt(tblListausuarios.getSelectedRow(), 7).toString().trim();
        VariablesUsuarios.vFecNac =
                tblListausuarios.getValueAt(tblListausuarios.getSelectedRow(), 8).toString().trim();
        VariablesUsuarios.vCodGrupoCia =
                tblListausuarios.getValueAt(tblListausuarios.getSelectedRow(), 9).toString().trim();
        VariablesUsuarios.vCoLocal =
                tblListausuarios.getValueAt(tblListausuarios.getSelectedRow(), 10).toString().trim();
        VariablesUsuarios.vCodTrab =
                tblListausuarios.getValueAt(tblListausuarios.getSelectedRow(), 11).toString().trim();
        VariablesUsuarios.vDNI = tblListausuarios.getValueAt(tblListausuarios.getSelectedRow(), 12).toString().trim();
        //se añadio el codigo de trabajador de RR.HH
        VariablesUsuarios.vCodTrab_RRHH =
                tblListausuarios.getValueAt(tblListausuarios.getSelectedRow(), 13).toString().trim();
    }

    private boolean tieneRegistroSeleccionado(JTable pTabla) {
        boolean rpta = false;

        if (pTabla.getSelectedRow() != -1) {
            rpta = true;
        }
        return rpta;
    }

    private void eliminarSeleccionado() throws SQLException {
        DBUsuarios.eliminaUsuario(VariablesUsuarios.vSecUsuLocal);
    }

    private void mostrarDatos() {
        lblLocal.setText(FarmaVariables.vCodLocal + " - " + FarmaVariables.vDescCortaLocal);

    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnApePaterno_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtApePaterno);
    }

    private void btnRelacion_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListausuarios);
    }

    private void evaluaTitulo() {
        if (!VariablesUsuarios.vInactivo) {
            this.setTitle("Listado de Usuarios Activos");
            lblF6.setText("[F6] Ver Todos");
            cargaListaUsuariosActivos();
            VariablesUsuarios.vInactivo = true;

        } else {
            this.setTitle("Listado de Todos los Usuarios");
            lblF6.setText("[F6] Ver Activos");
            cargaListaTodosUsuarios();
            VariablesUsuarios.vInactivo = false;
        }
    }

    /**
     * Busca si el El cajero tiene caja asignada
     * @author : dubilluz
     * @since  : 11.07.2007
     */
    private String busca_Caja_Asignada() {
        String sec_usuario = tblListausuarios.getValueAt(tblListausuarios.getSelectedRow(), 0).toString().trim();
        log.debug("Usuario a verficar " + sec_usuario);
        String resultado = "";
        try {
            resultado = DBUsuarios.buscaCajaAsignada(sec_usuario);
            log.debug("??numero de caja asignada>>>>" + resultado);

        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al obtener lista de usuarios. \n " + e.getMessage(), txtApePaterno);
        }
        return resultado;

    }

    /**
     * Verifica si los cajeros estan todos asignados a una caja
     * , si todos estan asignados retorna "TRUE"
     * @author : dubilluz
     * @since  : 11.07.2007
     */
    private boolean busca_cajeros_caja() {
        String sec_usuario = tblListausuarios.getValueAt(tblListausuarios.getSelectedRow(), 0).toString().trim();
        log.debug("Usuario a verficar " + sec_usuario);
        String resultado = "";
        try {
            resultado = DBUsuarios.buscaUsuSinCajaAsignada();
            log.debug("??Existen usuarios sin caja :>>>>" + resultado);

        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al obtener lista de usuarios. \n " + e.getMessage(), txtApePaterno);
        }
        if (resultado.equalsIgnoreCase("TRUE"))
            return true;
        else
            return false;
    }

    /**
     * Cambia el estado de la caja
     * @author : dubilluz
     * @since  : 12.07.2007
     */
    private String modifica_estado_caja(String num_caja_pago) {
        String evento = "exito";
        try {
            DBUsuarios.cambiaEstadoCaja(num_caja_pago);
            log.debug("Entro al modificar estado");
        } catch (SQLException e) {
            String mensaje = "";
            if (e.getErrorCode() == 20009) {
                mensaje = "No se puede inactivar , el usuario tiene una caja aperturada";
            }
            FarmaUtility.liberarTransaccion();
            FarmaUtility.showMessage(this, "Ocurrió un error en la transacción: \n" +
                    mensaje, txtApePaterno);
            evento = "fallo";
            //log.error("",e);
        }
        return evento;

    }

    /**
     * Obtiene el estado de la caja
     * @author : dubilluz
     * @since  : 12.07.2007
     */
    private String obtiene_estado_caja(String num_caja_pago) {
        String estado = "";
        try {
            estado = DBUsuarios.obtieneEstadoCaja(num_caja_pago);
            log.debug("Estado de la caja q esta asignado :  " + estado);
        } catch (SQLException e) {
            log.error("", e);
        }

        return estado.trim();

    }

    /**
     * Valida dias maximos para validar codigo de trabajador
     * @author : JCORTEZ
     * @since  : 3.07.2007
     */
    private void validarCodigoTrabajador() {
        try {
            String result = DBUsuarios.validaDiasMaximos(VariablesUsuarios.vLoginUsu);
            String codtraB = tblListausuarios.getValueAt(tblListausuarios.getSelectedRow(), 12).toString().trim();
            boolean focus = VariablesUsuarios.vfocus;
            /* if(focus)
       {*/
            if (codtraB.length() > 0) {
                if (result.equalsIgnoreCase("TRUE")) {
                    VariablesUsuarios.vActiCod = true;
                } else {
                    //FarmaUtility.showMessage(this, "Expiro el tiempo maximo para modificar el codigo de trabajador", null);
                    VariablesUsuarios.vActiCod = false;
                }
            } else {
                if (result.equalsIgnoreCase("TRUE")) {
                    VariablesUsuarios.vActiCod = true;
                } else {
                    FarmaUtility.showMessage(this, "Expiro el tiempo maximo para modificar el codigo de trabajador",
                                             null);
                    VariablesUsuarios.vActiCod = false;
                }
            }
            /*  }
      else
       {
        //sigue el flujo nrmal
       }*/

        } catch (SQLException e) {
            log.error("", e);
        }

    }

    /**
     * Consultara si podra modificar los campos del trabajador
     * @author dubilluz
     * @since  28.11.2007
     */
    public String validaCamposModificar(String pcod) {
        String valor = "";
        String existe = "";
        try {
            existe = DBUsuarios.getExisteUsuario(pcod.trim());
            if (existe.equals("S"))
                valor = "N";
            else
                valor = "S";
        } catch (SQLException e) {
            valor = "N";
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al consultar si modificara campos del trabajador.\n" +
                    e.getMessage(), null);
        }
        return valor;
    }


}
