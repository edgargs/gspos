package mifarma.ptoventa.administracion.impresoras;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;

import java.awt.BorderLayout;
import java.awt.Color;
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

import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.impresoras.reference.ConstantsImpresoras;
import mifarma.ptoventa.administracion.impresoras.reference.DBImpresoras;
import mifarma.ptoventa.administracion.impresoras.reference.VariablesImpresoras;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2009 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaIPSImpresora.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCORTEZ 04.06.2009 Creación<br>
 * JCHAVEZ 26.06.2009 Modificación<br>
 * <br>
 *
 * @author JORGE CORTEZ ALVAREZ<br>
 * @version 1.0<br>
 *
 */

public class DlgListaIPSImpresora extends JDialog {


    Frame myParentFrame;

    FarmaTableModel tableModel;

    private static final Logger log = LoggerFactory.getLogger(DlgListaIPSImpresora.class); //JCHAVEZ 06.07.2009.n

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JScrollPane scrListaUsuarios = new JScrollPane();
    private JPanel pnlRelacionFiltros = new JPanel();
    private JPanel pnlIngresarProductos = new JPanel();
    private JTextField txtIP = new JTextField();
    private JButton btnDescripcion = new JButton();
    private JTable tblMaestro = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JButtonLabel btnRelacionUsuarios = new JButtonLabel();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction lblF5 = new JLabelFunction();
    private JLabelFunction lblEnter = new JLabelFunction();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListaIPSImpresora() {
        this(null, "", false);
    }

    public DlgListaIPSImpresora(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(792, 412));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Lista IPs");
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setBackground(Color.white);
        jContentPane.setLayout(null);
        jContentPane.setSize(new Dimension(623, 335));
        jContentPane.setForeground(Color.white);
        jContentPane.setFocusable(false);
        scrListaUsuarios.setBounds(new Rectangle(5, 80, 775, 270));
        scrListaUsuarios.setBackground(new Color(255, 130, 14));
        scrListaUsuarios.setFocusable(false);
        pnlRelacionFiltros.setBounds(new Rectangle(5, 60, 775, 20));
        pnlRelacionFiltros.setBackground(new Color(255, 130, 14));
        pnlRelacionFiltros.setLayout(null);
        pnlRelacionFiltros.setFocusable(false);
        pnlIngresarProductos.setBounds(new Rectangle(5, 10, 775, 40));
        pnlIngresarProductos.setBorder(BorderFactory.createLineBorder(new Color(255, 140, 14), 1));
        pnlIngresarProductos.setBackground(Color.white);
        pnlIngresarProductos.setLayout(null);
        pnlIngresarProductos.setForeground(Color.orange);
        pnlIngresarProductos.setFocusable(false);
        txtIP.setBounds(new Rectangle(85, 10, 240, 20));
        txtIP.setFont(new Font("SansSerif", 1, 11));
        txtIP.setForeground(new Color(32, 105, 29));
        txtIP.addKeyListener(new KeyAdapter() {

            public void keyReleased(KeyEvent e) {
                txtIP_keyReleased(e);
            }

            public void keyPressed(KeyEvent e) {
                txtIP_keyPressed(e);
            }
        });
        btnDescripcion.setText("Nueva IP :");
        btnDescripcion.setBounds(new Rectangle(15, 10, 65, 20));
        btnDescripcion.setMnemonic('N');
        btnDescripcion.setFont(new Font("SansSerif", 1, 11));
        btnDescripcion.setDefaultCapable(false);
        btnDescripcion.setRequestFocusEnabled(false);
        btnDescripcion.setBackground(new Color(50, 162, 65));
        btnDescripcion.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnDescripcion.setFocusPainted(false);
        btnDescripcion.setHorizontalAlignment(SwingConstants.LEFT);
        btnDescripcion.setContentAreaFilled(false);
        btnDescripcion.setBorderPainted(false);
        btnDescripcion.setForeground(new Color(255, 140, 14));
        btnDescripcion.setFocusable(false);
        btnDescripcion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDescripcion_actionPerformed(e);
            }
        });
        tblMaestro.setFont(new Font("SansSerif", 0, 12));
        tblMaestro.setFocusable(false);
        tblMaestro.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblMaestro_keyPressed(e);
            }
        });
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(660, 355, 120, 25));
        lblEsc.setFocusable(false);
        btnRelacionUsuarios.setText("Relacion de IPs asociadas :");
        btnRelacionUsuarios.setBounds(new Rectangle(10, 0, 190, 20));
        btnRelacionUsuarios.setMnemonic('r');
        btnRelacionUsuarios.setFocusable(false);
        btnRelacionUsuarios.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionUsuarios_actionPerformed(e);
            }
        });
        lblF1.setBounds(new Rectangle(5, 355, 125, 25));
        lblF1.setText("[ F1 ] Agregar IP");
        lblF1.setFocusable(false);
        lblF5.setBounds(new Rectangle(140, 355, 125, 25));
        lblF5.setText("[ F5 ] Eliminar IP");
        lblF5.setFocusable(false);
        lblEnter.setText("[ Enter ] Editar Impresoras");
        lblEnter.setBounds(new Rectangle(275, 355, 165, 25));
        scrListaUsuarios.getViewport();
        pnlIngresarProductos.add(txtIP, null);
        pnlIngresarProductos.add(btnDescripcion, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(lblEnter, null);
        jContentPane.add(lblF5, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(lblEsc, null);
        scrListaUsuarios.getViewport().add(tblMaestro, null);
        jContentPane.add(scrListaUsuarios, null);
        pnlRelacionFiltros.add(btnRelacionUsuarios, null);
        jContentPane.add(pnlRelacionFiltros, null);
        jContentPane.add(pnlIngresarProductos, null);
        // this.getContentPane().add(jContentPane, null);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        initTable();
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsImpresoras.columnsListaIPS, ConstantsImpresoras.defaultValuesListaIPS, 0);
        FarmaUtility.initSimpleList(tblMaestro, tableModel, ConstantsImpresoras.columnsListaIPS);
        cargarIPs();
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void tblMaestro_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {

        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtIP);
    }


    private void btnDescripcion_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtIP);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
        //        else if (e.getKeyCode() == KeyEvent.VK_ENTER)
        //        {
        //            FarmaGridUtils.aceptarTeclaPresionada(e, tblMaestro, txtIP, 1);
        //            // FarmaGridUtils.buscarCodigo_KeyPressed(e, this, tblMaestro,
        //            // txtDescripcion, 0);
        //            e.consume();
        //            if (tblMaestro.getSelectedRow() >= 0)
        //            {
        //                if (!(FarmaUtility.findTextInJTable(tblMaestro, txtIP.getText().trim(), 0, 1)))
        //                {
        //                    FarmaUtility.showMessage(this, "Maestro No Encontrado según Criterio de Búsqueda !!!", txtIP);
        //                    return;
        //                }
        //            }
        //        }
        //        //Asignar impresora de boletas
        //        else if (UtilityPtoVenta.verificaVK_F1(e))
        //        {
        //            if (tblMaestro.getSelectedRow() > -1)
        //            {
        //                VariablesImpresoras.vSecIP = tblMaestro.getValueAt(tblMaestro.getSelectedRow(),
        //                                                                    0).toString().trim();
        //                log.debug("VariablesImpresoras.vSecIP-->" + VariablesImpresoras.vSecIP);
        //                DlgListaImpresoraMaquina dlglista = new DlgListaImpresoraMaquina(this.myParentFrame, "", true);
        //                dlglista.setValores("B");
        //                dlglista.setVisible(true);
        //
        //                if (FarmaVariables.vAceptar)
        //                {   cargarIPs();
        //                }
        //            }
        //            else
        //                FarmaUtility.showMessage(this, "Debe seleccionar un registro.", tblMaestro);
        //        }
        //        //Quita impresora de boletas
        //        else if (UtilityPtoVenta.verificaVK_F2(e))
        //        {
        //            if (tblMaestro.getSelectedRow() > -1)
        //            {
        //                if (JConfirmDialog.rptaConfirmDialog(this, "Esta seguro de eliminar la asignacion?"))
        //                {
        //                    VariablesImpresoras.vSecIP = tblMaestro.getValueAt(tblMaestro.getSelectedRow(),
        //                                                                        0).toString().trim();
        //                    log.debug("VariablesImpresoras.vSecIP-->" + VariablesImpresoras.vSecIP);
        //                    quitarAsignacion("B");
        //                    cargarIPs();
        //                }
        //            }
        //            else
        //                FarmaUtility.showMessage(this, "Debe seleccionar un registro.", tblMaestro);
        //        }
        //elimina la direccion IP seleccionada
        else if (e.getKeyCode() == KeyEvent.VK_F5) {
            if (tblMaestro.getRowCount() > 0) {
                if (tblMaestro.getSelectedRow() > -1) {
                    if (JConfirmDialog.rptaConfirmDialog(this, "Esta seguro de eliminar la IP seleccionada?")) {
                        eliminarIP();
                    }
                } else
                    FarmaUtility.showMessage(this, "Debe seleccionar un registro.", tblMaestro);
            }
        }
        //añade la direccion IP seleccionada
        else if (UtilityPtoVenta.verificaVK_F1(e)) {
            // if(txtIP.getText().trim().equalsIgnoreCase("")){
            if (txtIP.getText().trim().length() == 0) {
                FarmaUtility.showMessage(this, "Debe ingresar una IP válida.", txtIP);
            } else {
                if (esUnaDireccionIPValida(txtIP.getText().trim())) {
                    if (validarPingIP(txtIP.getText().trim())) {
                        if (JConfirmDialog.rptaConfirmDialog(this, "Esta seguro de agregar la nueva IP?")) {
                            guardarIP();
                            cargarIPs();
                        }
                    } else {
                        FarmaUtility.showMessage(this, "La ip ingresada no esta disponible.", txtIP);
                    }
                } else {
                    FarmaUtility.showMessage(this, "La ip ingresada no es válida. Verifique!!!", txtIP);
                }
            }
        }
        //        //Asigna una impresora termica
        //        else if (e.getKeyCode() == KeyEvent.VK_F3) //JCHAVEZ 25.06.09.sn
        //        {
        //            if (tblMaestro.getSelectedRow() > -1)
        //            {
        //                VariablesImpresoras.vSecIP = tblMaestro.getValueAt(tblMaestro.getSelectedRow(),
        //                                                                    0).toString().trim();
        //                log.debug("VariablesImpresoras.vSecIP-->" + VariablesImpresoras.vSecIP);
        //                DlgListaImpresoraTermica dlglista = new DlgListaImpresoraTermica(this.myParentFrame, "", true);
        //                dlglista.setVisible(true);
        //
        //                if (FarmaVariables.vAceptar)
        //                {
        //                    cargarIPs();
        //                    //JCHAVEZ 06.07.2009.sn  para refrescar el nombre de la impresora termica y su tipo
        //                    //dubilluz 19.08.2010
        //                    if (!FarmaVariables.vEconoFar_Matriz)
        //                    {
        //                        UtilityVentas.carga_impresoras(myParentFrame);
        //                    }
        //                    //JCHAVEZ 06.07.2009.en
        //                }
        //
        //            }
        //        }
        //        //LLEIVA 28-Ene-2014 Quitar impresora termica
        //        else if (e.getKeyCode() == KeyEvent.VK_F4)
        //        {
        //        }
        //        //LLEIVA 28-Ene-2014 Asignar impresora de factura
        //        else if (e.getKeyCode() == KeyEvent.VK_F7)
        //        {
        //            if (tblMaestro.getSelectedRow() > -1)
        //            {
        //                VariablesImpresoras.vSecIP = tblMaestro.getValueAt(tblMaestro.getSelectedRow(),
        //                                                                    0).toString().trim();
        //                log.debug("VariablesImpresoras.vSecIP-->" + VariablesImpresoras.vSecIP);
        //                DlgListaImpresoraMaquina dlglista = new DlgListaImpresoraMaquina(this.myParentFrame, "", true);
        //                dlglista.setValores("F");
        //                dlglista.setVisible(true);
        //
        //                if (FarmaVariables.vAceptar)
        //                {   cargarIPs();
        //                }
        //            }
        //            else
        //                FarmaUtility.showMessage(this, "Debe seleccionar un registro.", tblMaestro);
        //        }
        //        //LLEIVA 28-Ene-2014 Quitar impresora factura
        //        else if (e.getKeyCode() == KeyEvent.VK_F8)
        //        {
        //            if (tblMaestro.getSelectedRow() > -1)
        //            {
        //                if (JConfirmDialog.rptaConfirmDialog(this, "Esta seguro de eliminar la asignacion de la impresora de facturas?"))
        //                {
        //                    VariablesImpresoras.vSecIP = tblMaestro.getValueAt(tblMaestro.getSelectedRow(),
        //                                                                        0).toString().trim();
        //                    log.debug("VariablesImpresoras.vSecIP-->" + VariablesImpresoras.vSecIP);
        //                    quitarAsignacion("F");
        //                    cargarIPs();
        //                }
        //            }
        //            else
        //                FarmaUtility.showMessage(this, "Debe seleccionar un registro.", tblMaestro);
        //        }
        //LLEIVA 28-Ene-2014 visualiza el detalle de la impresora
        else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            VariablesImpresoras.vSecIP = tblMaestro.getValueAt(tblMaestro.getSelectedRow(), 0).toString().trim();
            log.debug("VariablesImpresoras.vSecIP-->" + VariablesImpresoras.vSecIP);
            DlgEditarIPSImpresora dlgEditarIPSImpresora = new DlgEditarIPSImpresora(this.myParentFrame, "", true);
            dlgEditarIPSImpresora.setDatos(tblMaestro.getValueAt(tblMaestro.getSelectedRow(), 1).toString().trim());
            dlgEditarIPSImpresora.setVisible(true);
            cargarIPs();
        }
        //else
        //    FarmaUtility.showMessage(this, "Debe seleccionar un registro.", tblMaestro);
        //JCHAVEZ 25.06.09.en
    }


    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cargarIPs() {
        try {
            log.debug("prueba antes de cargas el table model de la lista de IPs");
            int fila = 0;
            if (tblMaestro.getRowCount() > 0)
                fila = tblMaestro.getSelectedRow();
            else
                fila = 0;
            DBImpresoras.getListaIPs(tableModel);
            tblMaestro.setRowSelectionInterval(fila, fila);
            log.debug("prueba despues de cargas el table model de la lista de IPs " + tableModel.data);
            /*   if(tblMaestro.getRowCount()>0)
                FarmaUtility.ordenar(tblMaestro, tableModel,0,FarmaConstants.ORDEN_ASCENDENTE);*/

        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al obtener de Ips. \n " + e.getMessage(), txtIP);
        }
    }

    private void guardaValoresUsuario() {


        // VariablesImpresoras.vTipoComp = tblListaImpresoras.getValueAt(tblListaImpresoras.getSelectedRow(), 7).toString().trim();
        //  VariablesImpresoras.vSecImpr = tblListaImpresoras.getValueAt(tblListaImpresoras.getSelectedRow(), 0).toString().trim();
        // VariablesImpresoras.vNumSerie = tblListaImpresoras.getValueAt(tblListaImpresoras.getSelectedRow(), 3).toString().trim();


        /* String ip,sec;
            int i;
            ip=txtIP.getText().trim();
            sec=tblMaestro.getValueAt(tblMaestro.getSelectedRow(), tblMaestro.getRowCount()).toString().trim();
            if(sec.equalsIgnoreCase("")){
            sec="1";
                i= Integer.parseInt(sec.trim());
            }else{
                i= Integer.parseInt(sec.trim());
                i++;
            }

	    ArrayList myArray = new ArrayList();
	     myArray.add(i+"");
	     myArray.add(ip);
	     myArray.add("Activo");
	     tableModel.insertRow(myArray);*/
        FarmaUtility.ordenar(tblMaestro, tableModel, 0, FarmaConstants.ORDEN_ASCENDENTE);

    }

    private void btnRelacionUsuarios_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblMaestro);
    }

    private void guardarIP() {
        String ip = txtIP.getText().trim();
        try {
            DBImpresoras.ingresaIp(ip);
            FarmaUtility.aceptarTransaccion();
            //FarmaUtility.showMessage(this,"Ingreso exitoso.",txtIP);
        } catch (SQLException sql) {
            if (sql.getErrorCode() == 20001) {
                FarmaUtility.showMessage(this, "El ip ingresado ya existe. Verifique!!!", txtIP);
            } else {
                log.error("", sql);
                FarmaUtility.showMessage(this, "Ocurrio un error guardar la IP.\n" +
                        sql.getMessage(), txtIP);
            }
        }
    }

    /**
     * El metodo valida que la ip este disponible en la red haciendo solo 1 itento de conexcion.
     * */
    public boolean validarPingIP(String ip) {
        boolean val = true;
        try {
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec("ping -n 1 " + ip);
            int status = p.waitFor();
            log.debug("***********VALIDANDO IP*************");
            log.debug(ip + " is " + "/ " + status + "-->" + (status == 0 ? "alive" : "dead"));
            if (status == 1)
                val = false;
        } catch (Exception l) {
            log.error("Error al verificar conexcion de IP.");
            log.error("", l);
            val = false;
        }
        return val;
    }

    /**
     * Este metodo devuelve true si la direccion ip pasada como parametro
     * es valida o false en caso contrario
     */
    public boolean esUnaDireccionIPValida(String dir) {

        //En java un StringTokenizer es una version mejorada del String
        //  q permite darle formato a las cadenas. Al llamar el constructor
        //  y pasar por ej. la direccion="255.192.168.1" y el String punto "." lo q
        //  hago es separar la ip y dejar a st={"255","192","168","1"}
        StringTokenizer st = new StringTokenizer(dir, ".");

        //ahora verifico q si el tamaño de st no es 4, osea, los 4 numeros
        //  q debe tener toda direcicon ip, digo q esta mal la direccion ip.
        if (st.countTokens() != 4) {
            return false;
        }

        //ahora sigo verificando, y le digo a st con el metodo nextTokens()
        //  q me de uno por uno sus elementos, es decir, los 4 numeros de las
        //  de la direcicon ip. Luego verifo q el numero tenga solo 1 o 3 digitos y
        //  q este entre 0 y 255. Y listo, eso es todo.
        while (st.hasMoreTokens()) {
            String nro = st.nextToken();
            if ((nro.length() > 3) || (nro.length() < 1)) {
                return false;
            }
            int nroInt = 0;
            try {
                nroInt = Integer.parseInt(nro);
            } catch (NumberFormatException s) {
                return false;
            }
            if ((nroInt < 0) || (nroInt > 255)) {
                return false;
            }
        }
        return true;
    }


    private void eliminarIP() {

        String Sec = tblMaestro.getValueAt(tblMaestro.getSelectedRow(), 0).toString();
        String IP = tblMaestro.getValueAt(tblMaestro.getSelectedRow(), 1).toString();
        try {
            DBImpresoras.eliminaIP(Sec + "", IP);
            FarmaUtility.aceptarTransaccion();
            cargarIPs();
            //  FarmaUtility.showMessage(this,"retiro exitoso.",txtIP);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrio un error al eliminar la IP.\n" +
                    sql.getMessage(), txtIP);
        }
    }

    private void txtIP_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblMaestro, txtIP, 1);
    }

    private void txtIP_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblMaestro, txtIP, 1);

        chkKeyPressed(e);
    }

    private void quitarAsignacion(String indTipoComp) {
        String Sec = tblMaestro.getValueAt(tblMaestro.getSelectedRow(), 0).toString();
        String IP = tblMaestro.getValueAt(tblMaestro.getSelectedRow(), 1).toString();
        try {
            DBImpresoras.quitarAsignacion(Sec + "", indTipoComp);
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this, "Actualización exitosa.", txtIP);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al eliminar asignación.\n" +
                    sql.getMessage(), txtIP);
        }
    }

    private void this_windowClosing(WindowEvent e) {
        /**
         * Agregado.
         * @author Rudy Llantoy
         * @since 17.05.2013
         */
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
}
