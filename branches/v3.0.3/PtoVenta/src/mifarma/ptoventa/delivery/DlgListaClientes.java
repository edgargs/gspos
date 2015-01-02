package mifarma.ptoventa.delivery;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Color;
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

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.delivery.reference.ConstantsDelivery;
import mifarma.ptoventa.delivery.reference.DBDelivery;
import mifarma.ptoventa.delivery.reference.VariablesDelivery;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaClientes.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      11.04.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgListaClientes extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgListaClientes.class);


    enum Accion {
        INSERTA_CLIENTE,
        MODIFICA_CLIENTE,
        INSERTA_DIRECCION,
        MODIFICA_DIRECCION,
        INSERTA_DIRECCON_CLIENTE
    };

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    Frame myParentFrame;
    private FarmaTableModel tableModelClientes;
    private FarmaTableModel tableModelDirecciones;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction lblF12 = new JLabelFunction();
    private JScrollPane scrLista = new JScrollPane();
    private JTable tblListaClientes = new JTable();
    private JPanelTitle pnllTitle1 = new JPanelTitle();
    private JLabelFunction lblF3 = new JLabelFunction();
    private JLabelFunction lblF4 = new JLabelFunction();
    private JLabelFunction lblF5 = new JLabelFunction();
    private JButtonLabel btnListaClientes = new JButtonLabel();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JButtonLabel btnListaDirecciones = new JButtonLabel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblListaDirecciones = new JTable();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JLabelWhite lblTelefono = new JLabelWhite();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgListaClientes() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    public DlgListaClientes(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Lista Clientes");
        this.setSize(new Dimension(819, 430));
        this.setDefaultCloseOperation(0);
        this.setForeground(new Color(250, 130, 14));
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        lblEsc.setBounds(new Rectangle(705, 375, 100, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblF11.setBounds(new Rectangle(595, 375, 105, 20));
        lblF11.setText("[ F11 ] Aceptar");
        lblF1.setBounds(new Rectangle(5, 375, 160, 20));
        lblF1.setText("[ F1 ] Ingresar Télefono");
        lblF12.setBounds(new Rectangle(645, 340, 160, 20));
        lblF12.setText("[ F12 ] Dirección Principal");

        scrLista.setBounds(new Rectangle(5, 80, 800, 110));
        tblListaClientes.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaClientes_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                tblListaClientes_keyReleased(e);
            }
        });
        pnllTitle1.setBounds(new Rectangle(5, 55, 800, 25));
        lblF3.setBounds(new Rectangle(5, 340, 110, 20));
        lblF3.setText("[ F3 ] Crear Cliente");
        lblF4.setBounds(new Rectangle(140, 340, 135, 20));
        lblF4.setText("[ F4 ] Modificar Cliente");
        lblF5.setBounds(new Rectangle(285, 340, 135, 20));
        lblF5.setText("[ F5 ] Crear Direccion");
        btnListaClientes.setText("Lista Clientes");
        btnListaClientes.setBounds(new Rectangle(5, 0, 105, 25));
        btnListaClientes.setMnemonic('L');
        btnListaClientes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListaClientes_actionPerformed(e);
            }
        });
        jPanelTitle1.setBounds(new Rectangle(5, 195, 800, 25));
        btnListaDirecciones.setText("Direcciones");
        btnListaDirecciones.setBounds(new Rectangle(5, 5, 90, 15));
        btnListaDirecciones.setMnemonic('D');
        btnListaDirecciones.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListaDirecciones_actionPerformed(e);
            }
        });
        jScrollPane1.setBounds(new Rectangle(5, 220, 800, 105));
        tblListaDirecciones.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaDirecciones_keyPressed(e);
            }
        });
        jLabelFunction1.setBounds(new Rectangle(450, 340, 150, 20));
        jLabelFunction1.setText("[ F6 ] Modificar Direccion");
        jPanelHeader1.setBounds(new Rectangle(10, 10, 795, 30));
        jLabelWhite1.setText("TELEFONO : ");
        jLabelWhite1.setBounds(new Rectangle(15, 5, 70, 20));
        lblTelefono.setText("");
        lblTelefono.setBounds(new Rectangle(85, 5, 95, 20));
        jPanelHeader1.add(lblTelefono, null);
        jPanelHeader1.add(jLabelWhite1, null);
        jContentPane.add(jPanelHeader1, null);
        jContentPane.add(jLabelFunction1, null);
        jScrollPane1.getViewport().add(tblListaDirecciones, null);
        jContentPane.add(jScrollPane1, null);
        jPanelTitle1.add(btnListaDirecciones, null);
        jContentPane.add(jPanelTitle1, null);
        jContentPane.add(lblF5, null);
        jContentPane.add(lblF4, null);
        jContentPane.add(lblF3, null);
        pnllTitle1.add(btnListaClientes, null);
        jContentPane.add(pnllTitle1, null);
        scrLista.getViewport().add(tblListaClientes, null);
        jContentPane.add(scrLista, null);
        jContentPane.add(lblF11, null);

        jContentPane.add(lblF1, null);
        jContentPane.add(lblF12, null);
        jContentPane.add(lblEsc, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initTableDirecciones();
        initTableClientes();
        limpiaVariables();
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTableDirecciones() {
        tableModelDirecciones =
                new FarmaTableModel(ConstantsDelivery.columnsListaDireccionesConsulta, ConstantsDelivery.defaultValuesListaDireccionesConsulta,
                                    0);
        FarmaUtility.initSimpleList(tblListaDirecciones, tableModelDirecciones,
                                    ConstantsDelivery.columnsListaDireccionesConsulta);
    }

    private void initTableClientes() {
        tableModelClientes =
                new FarmaTableModel(ConstantsDelivery.columnsListaClientes, ConstantsDelivery.defaultValuesListaClientes,
                                    0);
        FarmaUtility.initSimpleList(tblListaClientes, tableModelClientes, ConstantsDelivery.columnsListaClientes);
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        cargaTelefono();
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
            if (!telefonoExiste()) {
                muestraMantClientes(Accion.INSERTA_DIRECCON_CLIENTE);
            } else {
                cargaListaClientes();
                cargaListaDirecciones();
            }

            FarmaUtility.moveFocus(tblListaClientes);
        } else {
            cerrarVentana(false);
        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnListaDirecciones_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaDirecciones);
    }

    private void btnListaClientes_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaClientes);
    }

    private void tblListaDirecciones_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            FarmaUtility.moveFocus(tblListaClientes);
        } else if (e.getKeyCode() == KeyEvent.VK_F12) {
            cambioDirPrincipalCliente();
        } else {
            chkKeyPressed(e);
        }
    }

    private void tblListaClientes_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            FarmaUtility.moveFocus(tblListaDirecciones);
        } else {
            chkKeyPressed(e);
        }
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_F3) {
            muestraMantClientes(Accion.INSERTA_DIRECCON_CLIENTE); //Accion.INSERTA_CLIENTE);
        } else if (e.getKeyCode() == KeyEvent.VK_F4) {
            if (tblListaClientes.getRowCount() <= 0)
                return;
            muestraMantClientes(Accion.MODIFICA_CLIENTE);
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            muestraMantClientes(Accion.INSERTA_DIRECCION);
        } else if (e.getKeyCode() == KeyEvent.VK_F6) {
            if (tblListaDirecciones.getRowCount() <= 0)
                return;
            muestraMantClientes(Accion.MODIFICA_DIRECCION);
        } else if (UtilityPtoVenta.verificaVK_F11(e)) {
            if (tblListaClientes.getRowCount() > 0) {


                cargaDatosCliente();
                cargaCabeceraDireccionTelefono();
                //muestraResumenPedido();
                cerrarVentana(true);

            } else if (tblListaClientes.getRowCount() <= 0) {
                //muestraResumenPedido();
                cerrarVentana(true);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_F1) {
            cargaTelefono();
            if (FarmaVariables.vAceptar) {
                FarmaVariables.vAceptar = false;
                if (!telefonoExiste()) {
                    muestraMantClientes(Accion.INSERTA_DIRECCON_CLIENTE);
                }
                cargaListaClientes();
                cargaListaDirecciones();
                FarmaUtility.moveFocus(tblListaClientes);
            }
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void muestraMantClientes(Accion pAccion) {

        switch (pAccion) {
        case INSERTA_CLIENTE:
            insertaCliente();
            break;
        case MODIFICA_CLIENTE:
            modificaCliente();
            break;
        case INSERTA_DIRECCION:
            insertaDireccion();
            break;
        case MODIFICA_DIRECCION:
            modificaDireccion();
            break;
        case INSERTA_DIRECCON_CLIENTE:
            insertaDireccionCliente();
            break;
        }
    }

    private void insertaCliente() {

        VariablesDelivery.vTipoAccion = ConstantsDelivery.ACCION_INSERTAR;
        VariablesDelivery.vTipoCampo = ConstantsDelivery.CAMPO_CLIENTE;

        //Valida exista direccion seleccionada
        /*
                if (tblListaDirecciones.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, "Debe crear una dirección primero.",tblListaDirecciones);
                return;
            }
        int direc = tblListaDirecciones.getSelectedRow();
        if(direc == -1){
            FarmaUtility.showMessage(this, "No ha seleccionado una dirección.",tblListaDirecciones);
            return;
        }
        VariablesDelivery.vCodigoDireccion = ((String)tblListaDirecciones.getValueAt(tblListaDirecciones.getSelectedRow(),3)).trim();
                */
        DlgMantClienteDireccionNew dlgMantClienteDireccionNew =
            new DlgMantClienteDireccionNew(myParentFrame, "", true);
        dlgMantClienteDireccionNew.setVisible(true);

        if (FarmaVariables.vAceptar) {
            if (!insertaDetalle())
                return;

            FarmaVariables.vAceptar = false;

            cargaListaClientes();
            FarmaUtility.moveFocus(tblListaClientes);
            FarmaUtility.findTextInJTable(tblListaClientes, VariablesDelivery.vCodCli.trim(), 0, 0);

        }
    }

    private void modificaCliente() {

        VariablesDelivery.vTipoAccion = ConstantsDelivery.ACCION_MODIFICAR;
        VariablesDelivery.vTipoCampo = ConstantsDelivery.CAMPO_CLIENTE;

        VariablesDelivery.vNombreCliente =
                ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(), 1)).trim();
        VariablesDelivery.vApellidoPaterno =
                ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(), 4)).trim();
        VariablesDelivery.vApellidoMaterno =
                ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(), 5)).trim();
        VariablesDelivery.vNumeroDocumento =
                ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(), 2)).trim();
        VariablesDelivery.vCodCli = ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(), 0)).trim();
        VariablesDelivery.vObsCli = ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(), 7)).trim();
        DlgMantClienteDireccionNew dlgMantClienteDireccionNew =
            new DlgMantClienteDireccionNew(myParentFrame, "", true);
        dlgMantClienteDireccionNew.setVisible(true);

        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
            cargaListaClientes();
            FarmaUtility.findTextInJTable(tblListaClientes, VariablesDelivery.vCodCli.trim(), 0, 0);
        }
    }

    private void insertaDireccion() {

        VariablesDelivery.vTipoAccion = ConstantsDelivery.ACCION_INSERTAR;
        VariablesDelivery.vTipoCampo = ConstantsDelivery.CAMPO_DIRECCION;

        VariablesDelivery.vCodCli =
                FarmaUtility.getValueFieldArrayList(tableModelClientes.data, tblListaClientes.getSelectedRow(), 0);
        VariablesDelivery.vNombreCliente =
                FarmaUtility.getValueFieldArrayList(tableModelClientes.data, tblListaClientes.getSelectedRow(), 1);

        DlgMantClienteDireccionNew dlgMantClienteDireccionNew =
            new DlgMantClienteDireccionNew(myParentFrame, "", true);
        dlgMantClienteDireccionNew.setVisible(true);

        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
            cargaListaDirecciones();
            FarmaUtility.findTextInJTable(tblListaDirecciones, VariablesDelivery.vCodigoDireccion.trim(), 3, 1);
        }
    }

    private void modificaDireccion() {
        VariablesDelivery.vTipoAccion = ConstantsDelivery.ACCION_MODIFICAR;
        VariablesDelivery.vTipoCampo = ConstantsDelivery.CAMPO_DIRECCION;

        DlgMantClienteDireccionNew dlgMantClienteDireccionNew =
            new DlgMantClienteDireccionNew(myParentFrame, "", true);

        VariablesDelivery.vCodigoDireccion =
                ((String)tblListaDirecciones.getValueAt(tblListaDirecciones.getSelectedRow(), 3)).trim();
        VariablesDelivery.vDireccion =
                ((String)tblListaDirecciones.getValueAt(tblListaDirecciones.getSelectedRow(), 1)).trim();
        VariablesDelivery.vReferencia =
                ((String)tblListaDirecciones.getValueAt(tblListaDirecciones.getSelectedRow(), 8)).trim();

        VariablesDelivery.vDptosCodigo =
                ((String)tblListaDirecciones.getValueAt(tblListaDirecciones.getSelectedRow(), 16)).trim();
        VariablesDelivery.vProviCodigo =
                ((String)tblListaDirecciones.getValueAt(tblListaDirecciones.getSelectedRow(), 17)).trim();
        VariablesDelivery.vDistCodigo =
                ((String)tblListaDirecciones.getValueAt(tblListaDirecciones.getSelectedRow(), 18)).trim();
        VariablesDelivery.vUrbanCodigo =
                ((String)tblListaDirecciones.getValueAt(tblListaDirecciones.getSelectedRow(), 19)).trim();
        VariablesDelivery.vUrbanizacion =
                ((String)tblListaDirecciones.getValueAt(tblListaDirecciones.getSelectedRow(), 15)).trim();


        VariablesDelivery.vCodCli = ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(), 0)).trim();
        dlgMantClienteDireccionNew.setVisible(true);

        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
            cargaListaDirecciones();
            FarmaUtility.findTextInJTable(tblListaDirecciones, VariablesDelivery.vCodigoDireccion.trim(), 3, 1);
        }
    }


    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    void cargaTelefono() {
        DlgBuscaTelefono dlgBuscaTelefono = new DlgBuscaTelefono(myParentFrame, "", true);
        dlgBuscaTelefono.setVisible(true);

        lblTelefono.setText(VariablesDelivery.vNumeroTelefono);
    }

    public void cargaListaDirecciones() {
        if (tblListaClientes.getRowCount() > 0) {
            String pCodCli =
                FarmaUtility.getValueFieldArrayList(tableModelClientes.data, tblListaClientes.getSelectedRow(), 0);
            try {
                DBDelivery.obtieneTelefonoDireccion(tableModelDirecciones, VariablesDelivery.vNumeroTelefono,
                                                    VariablesDelivery.vCampo, pCodCli);
                FarmaUtility.ordenar(tblListaDirecciones, tableModelDirecciones, 11, FarmaConstants.ORDEN_DESCENDENTE);

                /*if (tblListaDirecciones.getRowCount() <= 0) {
                    FarmaUtility.showMessage(this, "No se encontró ninguna dirección para esta búsqueda.",tblListaDirecciones);
                }*/
            } catch (SQLException e) {
                log.error("", e);
                FarmaUtility.showMessage(this, "Error al listar Direcciones", null);
                cerrarVentana(false);
            }
        }
    }

    public void cargaListaClientes() {
        try {
            log.debug(VariablesDelivery.vNumeroTelefono);
            DBDelivery.obtieneNombreDniCliente(tableModelClientes, VariablesDelivery.vNumeroTelefono,
                                               VariablesDelivery.vCampo);
            FarmaUtility.ordenar(tblListaClientes, tableModelClientes, 1, FarmaConstants.ORDEN_ASCENDENTE);

            if (tblListaClientes.getRowCount() <= 0) {
                muestraMantClientes(Accion.INSERTA_DIRECCON_CLIENTE);
            }
            /*
                FarmaUtility.showMessage(this, "No se encontro ningún cliente para esta Búsqueda", tblListaClientes);
            }
            */
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al listar Clientes", null);
            cerrarVentana(false);
        }
    }

    private void cargaCabeceraDireccionTelefono() {

        VariablesDelivery.vNumeroTelefonoCabecera =
                ((String)tblListaDirecciones.getValueAt(tblListaDirecciones.getSelectedRow(), 0)).trim();
        VariablesDelivery.vDireccion =
                ((String)tblListaDirecciones.getValueAt(tblListaDirecciones.getSelectedRow(), 1)).trim();
        VariablesDelivery.vReferencia =
                ((String)tblListaDirecciones.getValueAt(tblListaDirecciones.getSelectedRow(), 8)).trim();
        VariablesDelivery.vCodigoDireccion =
                ((String)tblListaDirecciones.getValueAt(tblListaDirecciones.getSelectedRow(), 3)).trim();
        VariablesDelivery.vNombreCalle =
                ((String)tblListaDirecciones.getValueAt(tblListaDirecciones.getSelectedRow(), 4)).trim();
        //VariablesVentas.vDir_Cli_Ped = VariablesDelivery.vNombreCalle;

        VariablesDelivery.vDptosCodigo =
                ((String)tblListaDirecciones.getValueAt(tblListaDirecciones.getSelectedRow(), 16)).trim();
        VariablesDelivery.vProviCodigo =
                ((String)tblListaDirecciones.getValueAt(tblListaDirecciones.getSelectedRow(), 17)).trim();
        VariablesDelivery.vDistCodigo =
                ((String)tblListaDirecciones.getValueAt(tblListaDirecciones.getSelectedRow(), 18)).trim();
        VariablesDelivery.vUrbanCodigo =
                ((String)tblListaDirecciones.getValueAt(tblListaDirecciones.getSelectedRow(), 19)).trim();
        VariablesDelivery.vUrbanizacion =
                ((String)tblListaDirecciones.getValueAt(tblListaDirecciones.getSelectedRow(), 15)).trim();
    }

    private boolean insertaDetalle() {

        try {
            DBDelivery.agregaDetalleClienteDireccion(VariablesDelivery.vCodigoDireccion, VariablesDelivery.vCodCli);
            FarmaUtility.aceptarTransaccion();
            return true;
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al insertar en el Detalle de Cliente Direccion - \n" +
                    sql, null);
            return false;
        }
    }

    private void cargaDatosCliente() {
        VariablesDelivery.vNombreCliente =
                ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(), 6)).trim();
        VariablesDelivery.vApellidoPaterno =
                ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(), 4)).trim();
        VariablesDelivery.vApellidoMaterno =
                ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(), 5)).trim();
        VariablesDelivery.vNumeroDocumento =
                ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(), 2)).trim();

        VariablesDelivery.vCodCli = ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(), 0)).trim();
        VariablesDelivery.vObsCli = ((String)tblListaClientes.getValueAt(tblListaClientes.getSelectedRow(), 7)).trim();
        VariablesVentas.vNom_Cli_Ped =
                VariablesDelivery.vNombreCliente.toUpperCase().trim() + " " + VariablesDelivery.vApellidoPaterno.toUpperCase().trim() +
                " " + VariablesDelivery.vApellidoMaterno.toUpperCase().trim();
    }

    private void limpiaVariables() {
        //VariablesDelivery.vNumeroTelefono = "";
        VariablesDelivery.vNombreInHashtable = "";
        VariablesDelivery.vNombreInHashtableVal = "";
        VariablesDelivery.vCampo = "";
        VariablesDelivery.vCantidadLista = "";
        VariablesDelivery.vDni_Apellido_Busqueda = "";
        VariablesDelivery.vTipoBusqueda = "";
        VariablesDelivery.vNumeroTelefonoCabecera = "";
        VariablesDelivery.vDireccion = "";
        VariablesDelivery.vDistrito = "";
        VariablesDelivery.vCodigoDireccion = "";
        VariablesDelivery.vInfoClienteBusqueda = new ArrayList();
        VariablesDelivery.vInfoClienteBusquedaApellidos = new ArrayList();
        VariablesDelivery.vCodCli = "";
        VariablesDelivery.vObsCli = "";
        VariablesDelivery.vCodDirTable = "";
        VariablesDelivery.vCodDirTmpTable = "";
        VariablesDelivery.vNombreCliente = "";
        VariablesDelivery.vApellidoPaterno = "";
        VariablesDelivery.vApellidoMaterno = "";
        VariablesDelivery.vTipoDocumento = "";
        VariablesDelivery.vTipoCliente = "";
        VariablesDelivery.vNumeroDocumento = "";
        VariablesDelivery.vTipoCalle = "";
        VariablesDelivery.vNombreCalle = "";
        VariablesDelivery.vNumeroCalle = "";
        VariablesDelivery.vNombreUrbanizacion = "";
        VariablesDelivery.vNombreDistrito = "";
        VariablesDelivery.vReferencia = "";
        VariablesDelivery.vNombreInterior = "";
        VariablesDelivery.vModificacionDireccion = "";
        VariablesDelivery.vTipoAccion = "";
        VariablesDelivery.vTipoCampo = "";
        VariablesDelivery.vCodTelefono = "";
        //VariablesVentas.vNumTarjCred = "";
        //VariablesVentas.vFecVencTarjCred = "" ;
        //VariablesVentas.vNomCliTarjCred = "" ;
        VariablesDelivery.vCodigoOperCobro = "";
        //VariablesVentas.vNumSecTarjCli = "";
        VariablesDelivery.vDni = "";
        VariablesDelivery.vDptosCodigo = "";
        VariablesDelivery.vProviCodigo = "";
        VariablesDelivery.vDistCodigo = "";
        VariablesDelivery.vUrbanCodigo = "";
        VariablesDelivery.vUrbanizacion = "";

    }

    private boolean telefonoExiste() {
        String valida = "";
        boolean vRetorno = false;
        try {
            valida = DBDelivery.buscaTelefono(VariablesDelivery.vNumeroTelefono, VariablesDelivery.vCampo);
            if (valida.equals("FALSE")) {
                vRetorno = true;
            }
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, e.getMessage(), null);
        }
        return vRetorno;
    }

    private void insertaDireccionCliente() {
        VariablesDelivery.vTipoAccion = ConstantsDelivery.ACCION_INSERTAR;
        VariablesDelivery.vTipoCampo = ConstantsDelivery.CAMPO_DIRECCION_CLIENTE;

        DlgMantClienteDireccionNew dlgMantClienteDireccionNew =
            new DlgMantClienteDireccionNew(myParentFrame, "", true);
        dlgMantClienteDireccionNew.setVisible(true);

        if (FarmaVariables.vAceptar) {
            //if(!insertaDetalle()) return;
            cargaListaClientes();
            cargaListaDirecciones();
            FarmaVariables.vAceptar = false;

        }
    }

    private void tblListaClientes_keyReleased(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_PAGE_UP) ||
            (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) ||
            e.getKeyCode() == KeyEvent.VK_ENTER) {
            cargaListaDirecciones();
        }
    }

    private void cambioDirPrincipalCliente() {

        if (tblListaClientes.getRowCount() > 0 && tblListaDirecciones.getRowCount() > 0) {
            int pCli = tblListaClientes.getSelectedRow();
            int pDir = tblListaDirecciones.getSelectedRow();
            try {
                String pCodDireccion = FarmaUtility.getValueFieldArrayList(tableModelDirecciones.data, pDir, 3);
                String pCodCli = FarmaUtility.getValueFieldArrayList(tableModelClientes.data, pCli, 0);
                DBDelivery.cambiaDirPrincipalCli(pCodDireccion, pCodCli);
                cargaListaDirecciones();
                FarmaUtility.showMessage(this, "Se cambió la Dirección Principal", null);
                //VariablesDelivery.vCodigoDireccion,
                //VariablesDelivery.vCodCli);
                FarmaUtility.aceptarTransaccion();
            } catch (Exception sql) {
                FarmaUtility.liberarTransaccion();
                log.error("", sql);
                FarmaUtility.showMessage(this, "Error al cambiar dirección principal \n" +
                        sql.getMessage(), null);

            }
        }
    }

}
