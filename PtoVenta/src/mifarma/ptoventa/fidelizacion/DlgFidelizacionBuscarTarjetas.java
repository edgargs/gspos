package mifarma.ptoventa.fidelizacion;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import farmapuntos.bean.Afiliado;
import farmapuntos.bean.TarjetaBean;

import farmapuntos.util.FarmaPuntosConstante;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.campAcumulada.reference.DBCampAcumulada;
import mifarma.ptoventa.campAcumulada.reference.VariablesCampAcumulada;
import mifarma.ptoventa.fidelizacion.reference.ConstantsFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.DBFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.FacadeFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.UtilityFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.puntos.DlgVerificaDocRedencionBonifica;
import mifarma.ptoventa.puntos.modelo.BeanAfiliado;
import mifarma.ptoventa.puntos.reference.ConstantsPuntos;
import mifarma.ptoventa.puntos.reference.DBPuntos;
import mifarma.ptoventa.puntos.reference.FacadePuntos;
import mifarma.ptoventa.puntos.reference.UtilityPuntos;
import mifarma.ptoventa.puntos.reference.VariablesPuntos;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgFidelizacionBuscarTarjetas extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgFidelizacionBuscarTarjetas.class);
    JPopupMenu popup = new JPopupMenu();
    ActionListener menuListener;
    ArrayList listaTarjetas = new ArrayList();
    ArrayList listaFormasDePago;
    String pCodFPTarjeta = "", pNombreTarjeta = "";
    Frame myParentFrame;
    FarmaTableModel tableModel;
    private JPanelWhite pnlContent = new JPanelWhite();
    private JPanelHeader pnlBusqueda = new JPanelHeader();
    private JLabelWhite lblBuscar = new JLabelWhite();
    private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();
    private JLabelWhite lblTipoDocumento = new JLabelWhite();
    private JTextFieldSanSerif txtTipoDocumento = new JTextFieldSanSerif();
    private JTable tblLista = new JTable();
    private JScrollPane srcLista = new JScrollPane();
    private JLabelFunction lblEnter = new JLabelFunction();
    
    private JLabel lblSeleccionTipoDoc = new JLabel();
    
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private final int COL_DNI = 0;
    private final int COL_NOM_CLI = 1;
    private final int COL_APE_PAT = 2;
    private final int COL_APE_MAT = 3;
    private final int COL_SEX_CLI = 4;
    private final int COL_FEC_NAC = 5;
    private final int COL_DIR_CLI = 6;
    private final int COL_TLF_CLI = 7;
    private final int COL_CEL_CLI = 8;
    private final int COL_EMAIL = 9;
    private ButtonGroup grupoBotones;
    private JRadioButton rbEfectivo = new JRadioButton();
    private JRadioButton rbTarjeta = new JRadioButton();
    private JPanel jPanel1 = new JPanel();
    private JTextArea jtaMensaje = new JTextArea();
    private JLabel lblTipoTarjeta = new JLabel();
    
    private boolean vIsPresionaTeclaF12 = false;
    private boolean isTarjetaCampania = false;
    
    private String codTipoDocumento = "";
    private boolean isMantenimientoCliente;
    
    public DlgFidelizacionBuscarTarjetas() {
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public DlgFidelizacionBuscarTarjetas(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }
    
    public DlgFidelizacionBuscarTarjetas(Frame parent, String title, boolean modal,boolean vTeclaF12) {
        super(parent, title, modal);
        myParentFrame = parent;
        vIsPresionaTeclaF12 =vTeclaF12;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }    

    private void jbInit() throws Exception {
        this.setSize(new Dimension(314, 170));
        this.setTitle("Buscar Tarjetas por DNI o CE");
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }

            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        pnlBusqueda.setBounds(new Rectangle(10, 10, 285, 56));
        pnlBusqueda.setFocusable(false);
        //lblBuscar.setText("Ingrese DNI o CE");
        lblBuscar.setText("Ingrese N° Doc.");
        lblBuscar.setBounds(new Rectangle(5, 30, 90, 20));
        txtBuscar.setBounds(new Rectangle(105, 30, 155, 20));
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtBuscar_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtBuscar_keyTyped(e);
            }
        });
        //txtBuscar.setLengthText(20);
        txtBuscar.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                txtBuscar_focusLost(e);
            }
        });
        
        lblTipoDocumento.setText("Tipo Documento");
        lblTipoDocumento.setBounds(new Rectangle(5, 5, 90, 20));
        txtTipoDocumento.setBounds(new Rectangle(105, 5, 155, 20));
        txtTipoDocumento.setEditable(false);
        txtTipoDocumento.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                
            }

            public void keyTyped(KeyEvent e) {
                
            }
        });
        //txtBuscar.setLengthText(20);
        txtTipoDocumento.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                
            }
        });
        
        
        tblLista.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblLista_keyPressed(e);
            }
        });
        lblTipoTarjeta.setVisible(false);
        srcLista.setBounds(new Rectangle(10, 275, 285, 25));
        lblEnter.setBounds(new Rectangle(55, 120, 95, 20));
        lblEnter.setText("[Enter] Buscar");
        lblEnter.setFocusable(false);
        
        lblSeleccionTipoDoc.setBounds(new Rectangle(40, 65, 240, 50));
        lblSeleccionTipoDoc.setText("<html>[ F1 ] SELECCIONA DNI<br>" +
                                    "[ F2 ] SELECCIONA CARNET EXTRANJERIA<br>[ F3 ] SELECCIONE DOCUMENTO OTRO PAIS");

        lblSeleccionTipoDoc.setFont(new Font("SansSerif", 1, 11));
        lblSeleccionTipoDoc.setForeground(new Color(214, 156, 0));
        lblF11.setBounds(new Rectangle(5, 255, 95, 20));
        lblF11.setText("[ F11 ] Aceptar");
        lblEsc.setBounds(new Rectangle(155, 120, 90, 20));
        lblEsc.setText("[ Esc ] Salir");
        lblEsc.setFocusable(false);
        rbEfectivo.setText("Efectivo");
        rbEfectivo.setBounds(new Rectangle(190, 15, 85, 25));
        rbEfectivo.setFont(new Font("SansSerif", 0, 10));
        rbEfectivo.setBackground(Color.white);
        rbTarjeta.setText("Tarjeta");
        rbTarjeta.setBounds(new Rectangle(190, 50, 85, 25));
        //popup.setBounds(new Rectangle(190, 50, 85, 25));
        rbTarjeta.setFont(new Font("SansSerif", 0, 10));
        rbTarjeta.setBackground(Color.white);
        rbTarjeta.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                rbTarjeta_keyReleased(e);
            }
        });
        jPanel1.setBounds(new Rectangle(10, 150, 285, 100));
        jPanel1.setLayout(null);
        jPanel1.setBackground(Color.white);
        jPanel1.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        jtaMensaje.setBounds(new Rectangle(5, 5, 180, 90));
        jtaMensaje.setText("Seleccione una forma de pago" + "\n" +
                "para ser uso de mayores" + "\n" +
                "descuentos.");
        jtaMensaje.setFont(new Font("SansSerif", 1, 11));
        jtaMensaje.setForeground(new Color(185, 48, 48));
        //lblTipoTarjeta.setBounds(new Rectangle(10, 10, 95, 25));
        lblTipoTarjeta.setBounds(new Rectangle(190, 70, 95, 25));
        lblTipoTarjeta.setFont(new Font("Serif", 1, 10));
        lblTipoTarjeta.setForeground(new Color(0, 154, 0));
        grupoBotones = new ButtonGroup();
        grupoBotones.add(rbEfectivo);
        grupoBotones.add(rbTarjeta);
        
        pnlBusqueda.add(txtTipoDocumento, null);
        pnlBusqueda.add(lblTipoDocumento, null);

        pnlBusqueda.add(txtBuscar, null);
        pnlBusqueda.add(lblBuscar, null);
        //pnlBusqueda.add(lblTipoTarjeta, null);

        jPanel1.add(lblTipoTarjeta, null);
        //jPanel1.add(popup);
        //popup.setVisible(false);
        jPanel1.add(jtaMensaje, null);
        jPanel1.add(rbEfectivo, null);
        jPanel1.add(rbTarjeta, null);
        pnlContent.add(jPanel1, null);
        pnlContent.add(lblF11, null);
        pnlContent.add(pnlBusqueda, null);
        srcLista.getViewport().add(tblLista, null);
        pnlContent.add(srcLista, null);
        pnlContent.add(lblEsc, null);
        pnlContent.add(lblEnter, null);


        pnlContent.add(lblSeleccionTipoDoc, null);
        this.getContentPane().add(pnlContent, null);

        menuListener = new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    seleccionTarjeta(event);
                }
            };

        popup.addPopupMenuListener(new PopupPrintListener());

    }

    private void initialize() {
        initTable();
        try {
            VariablesFidelizacion.vDocValidos = DBFidelizacion.obtenerParamDocIden();
            
            // SE SETEA POR DEFECTO EL CODIGO DE DNI.
            codTipoDocumento = ConstantsFidelizacion.COD_TIPO_DOC_DNI;
            mostrarTipoDocumento();
        } catch (SQLException e) {
            log.debug("error : " + e);
        }
    }
    
    private void mostrarTipoDocumento(){
        if(ConstantsFidelizacion.COD_TIPO_DOC_DNI.equalsIgnoreCase(codTipoDocumento)){
            txtTipoDocumento.setText("DNI");
        }else if(ConstantsFidelizacion.COD_TIPO_DOC_CARNET_EXTRANJERIA.equalsIgnoreCase(codTipoDocumento)){
            txtTipoDocumento.setText("CARNET EXTRANJERIA");
        }else if(ConstantsFidelizacion.COD_TIPO_DOC_DNI_EXTRANJERO.equalsIgnoreCase(codTipoDocumento)){
            txtTipoDocumento.setText("DNI EXTRANJERO");
        }
    }

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsFidelizacion.columnsDataTarjeta, ConstantsFidelizacion.defaultsDtaTarjeta,
                                    0);
        FarmaUtility.initSimpleList(tblLista, tableModel, ConstantsFidelizacion.columnsDataTarjeta);
    }


    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void this_windowOpened(WindowEvent e) {
        if(isVIsPresionaTeclaF12()){
            FarmaUtility.moveFocus(txtBuscar);
            FarmaUtility.centrarVentana(this);
            procesaFuncionalidadFid();
            if(!isTarjetaCampania){
                procesaFuncionPuntos();
            }
        }else{
            if(isMantenimientoCliente){
                FarmaUtility.moveFocus(txtBuscar);
                FarmaUtility.centrarVentana(this);
            }
        }
        
    }
    
    public void procesaFuncionalidadFid(){
        VariablesFidelizacion.auxDataCli.clear();
        cargaFormasDePagoCampanaUso();
        if (listaTarjetas.size() == 1) {
            //coloca a tarjeta nada mas aplica.
            lblTipoTarjeta.setVisible(true);
            lblTipoTarjeta.setText(FarmaUtility.getValueFieldArrayList(listaTarjetas, 0, 1));
            log.debug("coloca datos de unica tarjeta:" + FarmaUtility.getValueFieldArrayList(listaTarjetas, 0, 1));
        }
        log.debug("lblTipoTarjeta.getT_" + lblTipoTarjeta.getText());
        //17.06.2011
        if (VariablesFidelizacion.vDniCliente.trim().length() > 2) {
            txtBuscar.setText(VariablesFidelizacion.vDniCliente.trim());
        }        
    }
    
    public void procesaFuncionPuntos(){
        if(UtilityPuntos.isActivoFuncionalidad()){
            //TarjetaBean tarjetaCliente = VariablesPuntos.frmPuntos.getTarjetaBean();
            if(VariablesPuntos.frmPuntos!= null && VariablesPuntos.frmPuntos.getTarjetaBean()!=null){
                TarjetaBean tarjetaCliente = VariablesPuntos.frmPuntos.getTarjetaBean();
                if( FarmaPuntosConstante.EstadoTarjeta.ACTIVA.equalsIgnoreCase(tarjetaCliente.getEstadoTarjeta())||
                    FarmaPuntosConstante.EstadoTarjeta.BLOQUEADA_REDIMIR.equalsIgnoreCase(tarjetaCliente.getEstadoTarjeta())){
                    txtBuscar.setText(tarjetaCliente.getDni());
                    if(txtBuscar.getText().trim().length()>0){
                        funcionVkEnter(true); 
                    }
                }else{
                    if(FarmaPuntosConstante.NO_CONEXION_ORBIS.equalsIgnoreCase(tarjetaCliente.getEstadoOperacion())  && 
                        UtilityPuntos.isTarjetaValida(tarjetaCliente.getNumeroTarjeta().trim())){
                        String nroDocumento = "";
                        try{
                            nroDocumento = DBFidelizacion.getDniClienteFidelizado(tarjetaCliente.getNumeroTarjeta());
                        }catch(Exception ex){
                            log.info("PROGRAMA DE PUNTOS [CONSULTAR DNI DE TARJETA] NRO TARJETA: "+tarjetaCliente.getNumeroTarjeta());
                            log.error(" ", ex);
                        }
                        if( nroDocumento!= null && nroDocumento.trim().length() > 0 ){
                            txtBuscar.setText(nroDocumento);
                            funcionVkEnter(true); 
                        }
                    }
                }
            }else{
                //KMONCADA 20.07.2015 PARA EL CASO DE USO DE TARJETA EN CAMPAÑA
                if(VariablesPuntos.frmPuntos!= null && isTarjetaCampania){
                    try{
                        String nroDni = DBFidelizacion.getDniClienteFidelizado(VariablesFidelizacion.tmp_NumTarjeta_unica_Campana);
                        if(nroDni.trim().length()>0){
                            txtBuscar.setText(nroDni.trim());
                            funcionVkEnter(true); 
                        }
                    }catch(Exception ex){
                        log.error("",ex);
                    }
                }
            }
        }
    }

    public void cerrarVentana(boolean vResp) {
        
        if(isVIsPresionaTeclaF12()){
            log.debug("vResp" + vResp);
            log.debug("VariablesFidelizacion.vDniCliente:" + VariablesFidelizacion.vDniCliente);
            if (!vResp && VariablesFidelizacion.vDniCliente.trim().length() == 0) {
                FarmaVariables.vAceptar = vResp;
                this.setVisible(vResp);
                this.dispose();
            } else {
                if (vResp)
                    guardaVariablesFormaPagoUsoCampana();
                else if (VariablesFidelizacion.vDniCliente.trim().length() == 0)
                    limpiaVariablesFormaPago();
                log.debug("2 vIndUsoEfectivo:" + VariablesFidelizacion.vIndUsoEfectivo);
                log.debug("2 vIndUsoTarjeta:" + VariablesFidelizacion.vIndUsoTarjeta);
                log.debug("2 vCodFPagoTarjeta:" + VariablesFidelizacion.vCodFPagoTarjeta);
    
                if (dejaDarEvento()) {
                    FarmaVariables.vAceptar = vResp;
                    this.setVisible(vResp);
                    this.dispose();
                } else {
                    FarmaUtility.showMessage(this, "Debe de seleccionar una forma de Pago.", txtBuscar);
                }
            }
        }else{
            if(isMantenimientoCliente){
                FarmaVariables.vAceptar = vResp;
                this.setVisible(vResp);
                this.dispose();
            }
        }
    }

    public boolean dejaDarEvento() {
        if (listaFormasDePago.size() > 0) {
            if (VariablesFidelizacion.vIndUsoEfectivo.trim().equalsIgnoreCase("S") ||
                (VariablesFidelizacion.vIndUsoTarjeta.trim().equalsIgnoreCase("S") &&
                 VariablesFidelizacion.vCodFPagoTarjeta.trim().length() > 0))
                return true;
            else
                return false;
        }

        return true;
    }

    private void txtBuscar_keyPressed(KeyEvent e) {
        boolean actualizarTipoDocumento = false;
        if (UtilityPtoVenta.verificaVK_F1(e)) {
            actualizarTipoDocumento = true;
            codTipoDocumento = ConstantsFidelizacion.COD_TIPO_DOC_DNI;
        }
        if (UtilityPtoVenta.verificaVK_F2(e)) {
            actualizarTipoDocumento = true;
            codTipoDocumento = ConstantsFidelizacion.COD_TIPO_DOC_CARNET_EXTRANJERIA;
        }
        if (KeyEvent.VK_F3 == e.getKeyCode()) {
            actualizarTipoDocumento = true;
            codTipoDocumento = ConstantsFidelizacion.COD_TIPO_DOC_DNI_EXTRANJERO;
        }
        if(actualizarTipoDocumento){
            mostrarTipoDocumento();
        }
        
        if (UtilityPtoVenta.verificaVK_F11(e)) {
            if (UtilityFidelizacion.validarDocIdentidad(txtBuscar.getText().trim())) {
                FarmaUtility.moveFocus(tblLista);
            } else {
                FarmaUtility.showMessage(this, "Numero de DNI incorrecto", null);
                limpiar();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            fSeleccionButton();
        } else {
            chkKeyPressed(e);
        }
    }

    private void chkKeyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F11(e)) {
            /* if(tblLista.getSelectedRowCount()>0){
              setNumeroTarjeta();
              if(FarmaVariables.vAceptar) {
                    limpiar();
                    cerrarVentana(true);
                }
              else
                cerrarVentana(false);
          }*/
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            funcionVkEnter(false);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            txtBuscar.setText("");
        }

    }
    
    private void funcionVkEnter(boolean obtenerCodTipDocumento){
        //09.07.2015 ERIOS Control antimonos
        txtBuscar.setEnabled(false);
        if(obtenerCodTipDocumento){
            FacadeFidelizacion facade = new FacadeFidelizacion();
            codTipoDocumento = facade.getCodigoTipoDocumentoAfiliado(txtBuscar.getText().trim());
            if(codTipoDocumento == null ||(codTipoDocumento != null && codTipoDocumento.trim().length() == 0)){
                codTipoDocumento = UtilityFidelizacion.determinarTipoDocIndentidad(txtBuscar.getText().trim());
                if(codTipoDocumento == null ||(codTipoDocumento != null && codTipoDocumento.trim().length() == 0)){
                    FarmaUtility.showMessage(this, "Error al obtener el Tipo del Documento de Identidad, o no se pudo determinar.", txtBuscar);
                    return;
                }
            }
        }
        busquedaDoc();		
        txtBuscar.setEnabled(true);
    }
    
    private void busquedaDoc(){
        log.debug("JCORTEZ: ENTRA PARA BUSCAR");
        //JCORTEZ 02.07.09 Se valida un numero de valido de identificacion.
        if (UtilityFidelizacion.validarDocIdentidad(txtBuscar.getText().trim(), codTipoDocumento)) {
            if(isMantenimientoCliente){
                VariablesFidelizacion.Tip_doc = codTipoDocumento;
                procesoMantenimientoCliente();
            }else{
                if (isSeleccionFormaPago()) {
                    VariablesFidelizacion.Tip_doc = codTipoDocumento;
                    if(VariablesPuntos.frmPuntos != null && !isTarjetaCampania){
                        
                        TarjetaBean tarjetaBean = VariablesPuntos.frmPuntos.getTarjetaBean();
                        boolean consultarOrbis = false;
                        if(tarjetaBean == null){
                            consultarOrbis = true;
                        }
                        if(consultarOrbis){
                            //boolean respuesta = UtilityPuntos.consultarTarjetaOrbis(myParentFrame, this, txtBuscar, false, false);
                            int rspta = UtilityPuntos.consultarTarjetaOrbis(myParentFrame, this, txtBuscar, false, false);
                            //tarjetaBean = VariablesPuntos.frmPuntos.getTarjetaBean();
                            //if(tarjetaBean == null){
                            if(rspta == ConstantsPuntos.RECHAZA_AFILIACION_TARJ && !isTarjetaCampania){
                                // KMONCADA 25.06.2015 CUANDO CLIENTE RECHAZE TARJ PUNTOS NO TOMARA DNI.
                                log.info("PROGRAMA DE PUNTOS [CLIENTE NUEVO] : RECHAZO AFILIACION");
                                VariablesFidelizacion.limpiaVariables();
                                VariablesPuntos.frmPuntos.eliminarTarjetaBean();//Inicio [Desarrollo5] 09.10.2015
                                cerrarVentana(false);
                                return;
                            }else if(rspta == ConstantsPuntos.RECHAZA_AFILIACION_TARJ && isTarjetaCampania){
                                VariablesPuntos.frmPuntos.eliminarTarjetaBean();
                            }else if(rspta == ConstantsPuntos.TARJETA_ACTIVA && isTarjetaCampania){
                                VariablesPuntos.frmPuntos.eliminarTarjetaBean();
                            }else if(rspta == ConstantsPuntos.TARJETA_NUEVA && isTarjetaCampania){
                                VariablesPuntos.frmPuntos.eliminarTarjetaBean();
                            }//Fin [Desarrollo5] 09.10.2015
                        }else{
                            try{
                                boolean respuesta = UtilityPuntos.validaTarjetaAdicional(tarjetaBean, myParentFrame, this, txtBuscar.getText().trim());
                                if(!respuesta){
                                    return;
                                }
                                if(FarmaPuntosConstante.NO_CONEXION_ORBIS.equalsIgnoreCase(tarjetaBean.getEstadoOperacion())){
                                    String nroDocumento = DBFidelizacion.getDniClienteFidelizado(tarjetaBean.getNumeroTarjeta().trim());
                                    if( nroDocumento.trim().length() > 0 ){
                                        if(!DBPuntos.isTarjetaAsociada(txtBuscar.getText().trim(), tarjetaBean.getNumeroTarjeta().trim())){
                                            FarmaUtility.showMessage(this, "Programa Puntos:\nTarjeta no corresponde al Documento ingresado", txtBuscar);
                                            return;
                                        }
                                        
                                    }
                                }
                            }catch(Exception ex){
                                log.info("PROGRAMA DE PUNTOS : [ADICIONAL TARJETA] Error al consultar afiliado.");
                                log.error("", ex);
                            }
                        }
                        
                    }
                    buscarTarjetas();
                    log.debug("\n ENTRA PARTA BUSCAR  \n");
                } else {
                    FarmaUtility.showMessage(this, "Debe de seleccionar una forma de Pago.", txtBuscar);
                }
            }
        } else {
            FarmaUtility.showMessage(this, "Numero de Documento de Identidad no valido ", null);
            limpiar();
        }
    }
    
    private void procesoMantenimientoCliente(){
        if(ConstantsFidelizacion.COD_TIPO_DOC_DNI.equalsIgnoreCase(codTipoDocumento)){
            DlgVerificaDocRedencionBonifica dlgVerificaDocRedencionBonifica = new DlgVerificaDocRedencionBonifica(myParentFrame, 
                                                                                                                  "", 
                                                                                                                  true, 
                                                                                                                  "MOD");
            dlgVerificaDocRedencionBonifica.setIsRequiereTarjeta(false);
            dlgVerificaDocRedencionBonifica.setIsRequiereDni(true);
            dlgVerificaDocRedencionBonifica.setNroDocumento(txtBuscar.getText());
            dlgVerificaDocRedencionBonifica.setMnto(true);
            dlgVerificaDocRedencionBonifica.setVisible(true);
            if (FarmaVariables.vAceptar) { 
                consultarAfiliadoMonedero();
            }
        }else{
            if(ConstantsFidelizacion.COD_TIPO_DOC_DNI_EXTRANJERO.equalsIgnoreCase(codTipoDocumento)||
               ConstantsFidelizacion.COD_TIPO_DOC_CARNET_EXTRANJERIA.equalsIgnoreCase(codTipoDocumento)){
                
                consultarAfiliadoMonedero();
            }else{
                FarmaUtility.showMessage(this, "Tipo de Documento no se pudo determinar.", null);
                return;
            }
        }
    }
    
    private void consultarAfiliadoMonedero() {
        boolean isObtuvoClienteMonedero = false;
        String nroDocIdentidad = txtBuscar.getText().trim();
        try{
            Afiliado afiliado = null;
            // paso 1
            if (UtilityPuntos.isActivoFuncionalidad()) {
                afiliado = VariablesPuntos.frmPuntos.obtenerDatosAfiliadoSinTarjeta(nroDocIdentidad, UtilityPuntos.getDNI_USU());
                // afiliado = null NO CONEXION O ERROR
                // AFILIADO != NULL PERO DNI EN BLANCO NO EXISTE
                if(afiliado != null ){
                    if(afiliado.getDni().trim().length() > 0){
                        isObtuvoClienteMonedero = true;
                    }
                }else{
                    log.info("REINTENTADO CONSULTA A SERVIDOR MONEDERO");
                    afiliado = VariablesPuntos.frmPuntos.obtenerDatosAfiliadoSinTarjeta(nroDocIdentidad, UtilityPuntos.getDNI_USU());
                    if(afiliado != null && afiliado.getDni().trim().length()!=0){
                        isObtuvoClienteMonedero = true;
                    }else{
                        if(afiliado == null){
                            FarmaUtility.showMessage(this, "Programa Monedero :\nNo se pudo obtener información del cliente.\n"+
                                                           "Por Favor reintente, si problema persiste comuniquese con Mesa de Ayuda.", txtBuscar);
                            return;
                        }
                    }
                }
                
                if(isObtuvoClienteMonedero){
                    log.info("[PROGRAMA PUNTOS] : SE INSERTARA CLIENTE QUE EXISTE EN ORBIS AL LOCAL \n" +
                             "DNI:"+afiliado.getDni()+
                             "APE.PATERNO:"+afiliado.getApParterno()+
                             "APE.MATERNO:"+afiliado.getApMarterno()+
                             "NOMBRE:"+afiliado.getNombre()+
                             "FECHA NACIMIENTO:"+afiliado.getFechaNacimiento()+
                             "SEXO:"+afiliado.getGenero()+
                             "EMAIL:"+afiliado.getEmail());
                    //VariablesFidelizacion.vNumTarjeta = pNroTarjeta;
                    VariablesFidelizacion.vIndEstado = "A";
                    DBFidelizacion.insertarClienteFidelizacion(ConstantsPuntos.IND_PROCESO_ORBIS_ACT,
                                                                ConstantsPuntos.TRSX_ORBIS_ENVIADA,
                                                                 afiliado);
                }
            }
            // paso 2
            FacadePuntos facadeAfiliado = new FacadePuntos();
            afiliado = facadeAfiliado.obtenerClienteFidelizado(nroDocIdentidad);
            
            if (afiliado == null) {
                FarmaUtility.showMessage(this, "Fidelización:\nEl documento de identidad no se encuentra registrado en el local.", null);
                cerrarVentana(false);
            } else {            
                DlgFidelizacionClientes dlgFidelizacionClientesMnto = new DlgFidelizacionClientes(myParentFrame, "", true, "");
                dlgFidelizacionClientesMnto.setAfiliado(afiliado);
                dlgFidelizacionClientesMnto.setIsMantenimientoCliente(true);
                dlgFidelizacionClientesMnto.setIsClienteMonedero(true);
                dlgFidelizacionClientesMnto.setVisible(true);
                if (FarmaVariables.vAceptar) {
                    cerrarVentana(FarmaVariables.vAceptar);
                }                
            }
        } catch (Exception e) {
            log.error("",e);
        }
    }

    public void buscarTarjetas() {
        String vdni = txtBuscar.getText().trim();
        /*
        //dubilluz 19.07.2011 - inicio
        if(VariablesFidelizacion.tmp_NumTarjeta_unica_Campana.trim().length()>0){
         grabaTarjetaUnica(VariablesFidelizacion.tmp_NumTarjeta_unica_Campana.trim(),vdni);
        }
        //dubilluz 19.07.2011 - fin
        */
        try {


            //JCORTEZ No hay necesidad de validar
            //UtilityFidelizacion.validarConexionMatriz();
            //Se quita conexiones a matriz  agregado
            VariablesFidelizacion.vIndConexion = "N";
            VariablesFidelizacion.auxDataCli.clear();

            /*
             * no se realizara la busqueda en Matriz
             * dubilluz 26/07/2009
             * if (VariablesFidelizacion.vIndConexion.equals("S")) {
                log.debug("buscar en matriz al cliente");
                DBFidelizacion.buscarTarjetasXDNIMatriz(tableModel, vdni,VariablesFidelizacion.vIndConexion);
                log.debug("ver si tiene datos");
                if (tableModel.data.size() == 0) {
                    log.debug("buscar en local despues de buscar en matriz");
                    DBFidelizacion.buscarTarjetasXDNI(tableModel, vdni);
                }
            }else{*/
            String nroTarjetaLealtad = null;
            boolean isActualizaDatosAfiliado = false;
            if(UtilityPuntos.isActivoFuncionalidad()){
                TarjetaBean tarjetaPuntos = VariablesPuntos.frmPuntos.getTarjetaBean();
                if(tarjetaPuntos!=null){
                    if(UtilityPuntos.isTarjetaValida(tarjetaPuntos.getNumeroTarjeta())){
                        nroTarjetaLealtad = tarjetaPuntos.getNumeroTarjeta();
                    }
                    // KMONCADA 03.07.2015 VALIDA TIPO DE OPERACION PARA CONSULTAR POR AFILIADO EN ORBIS
                    try{
                        if(FarmaPuntosConstante.EstadoTarjeta.ACTIVA.equalsIgnoreCase(tarjetaPuntos.getEstadoTarjeta()) ||
                           FarmaPuntosConstante.EstadoTarjeta.BLOQUEADA_REDIMIR.equalsIgnoreCase(tarjetaPuntos.getEstadoTarjeta())
                        ){
                            isActualizaDatosAfiliado = true;
                            Afiliado afiliado = VariablesPuntos.frmPuntos.obtenerDatosAfiliado(vdni, UtilityPuntos.getDNI_USU());
                            if(afiliado != null && afiliado.getDni().trim().length()>0 ){
                                //BeanAfiliado afiliado = new BeanAfiliado(afiliadoFP);
                                //FacadeFidelizacion facade = new FacadeFidelizacion();
                                //afiliado.setCodTipoDocumento(facade.getCodigoTipoDocumentoAfiliado(afiliado.getDni()));
                                String tmp = VariablesFidelizacion.vNumTarjeta;
                                VariablesFidelizacion.vNumTarjeta = nroTarjetaLealtad;
                                VariablesFidelizacion.vIndEstado = "A";
                                log.info("[PROGRAMA PUNTOS] : SE INSERTARA CLIENTE QUE EXISTE EN ORBIS AL LOCAL \n" +
                                         "DNI:"+afiliado.getDni()+
                                         "APE.PATERNO:"+afiliado.getApParterno()+
                                         "APE.MATERNO:"+afiliado.getApMarterno()+
                                         "NOMBRE:"+afiliado.getNombre()+
                                         "FECHA NACIMIENTO:"+afiliado.getFechaNacimiento()+
                                         "SEXO:"+afiliado.getGenero()+
                                         "EMAIL:"+afiliado.getEmail());
                                DBFidelizacion.insertarClienteFidelizacion(ConstantsPuntos.IND_PROCESO_ORBIS_ACT, 
                                                                           ConstantsPuntos.TRSX_ORBIS_ENVIADA, 
                                                                           afiliado);
                                VariablesFidelizacion.vNumTarjeta = tmp;
                            }
                        }else{
                            if(FarmaPuntosConstante.EstadoTarjeta.INACTIVA.equalsIgnoreCase(tarjetaPuntos.getEstadoTarjeta())){
                                VariablesPuntos.frmPuntos.getTarjetaBean().setDni(vdni);
                            }
                        }
                    }catch(Exception ex){
                        log.info("PROGRAMA DE PUNTOS: ERROR AL REALIZAR ACTUALIZACION DE CLIENTE");
                        log.error(" ", ex);
                    }
                    
                }
            }
            log.info("buscar en local, nro tarjeta lealtad: "+nroTarjetaLealtad);
            DBFidelizacion.buscarTarjetasXDNI(tableModel, vdni, nroTarjetaLealtad );
/*             //KMONCADA 02.07.2015 ACTUALIZARA LOS DATOS QUE SE ENCUENTREN EN ORBIS
            if(isActualizaDatosAfiliado){
                try{
                    
                }catch(Exception ex){
                    log.info("PROGRAMA DE PUNTOS: ERROR AL REALIZAR ACTUALIZACION DE CLIENTE");
                    log.error(" ", ex);
                }
                
            } */
            //}

            VariablesFidelizacion.NumDniAux = "";
            //  if(tableModel.data.size()==0){
            if (tblLista.getRowCount() == 0) { // jcortez 02.07.09 evita error evento espace
                /*FarmaUtility.showMessage(this, "Este cliente no cuenta con tarjetas", null);
                limpiar();
                FarmaUtility.moveFocus(txtBuscar);*/

                /*
                  Logica para creacion y asignacion de nuevas tarjetas de fidelizacion
                 */

                VariablesFidelizacion.NumDniAux = vdni;

                log.debug("JCORTEZ: EL CLIENTE NO EXISTE");

                long tmpIni = System.currentTimeMillis();
                log.debug("JCORTEZ: INICIO GENRAR TARJ");
                String vNuevaTarjetaFidelizacion = UtilityFidelizacion.generaNuevaTarjeta(ConstantsFidelizacion.PREFIJO_TARJETA_FIDELIZACION, 
                                                                                          FarmaVariables.vCodLocal, 
                                                                                          nroTarjetaLealtad);
                long tmpFin = System.currentTimeMillis();
                log.debug("JCORTEZ: FIN GENRAR TARJ" + "DURACION: " + (tmpFin - tmpIni) + " milisegundos");

                setNumeroTarjeta(vNuevaTarjetaFidelizacion, nroTarjetaLealtad);
                if (FarmaVariables.vAceptar) {
                    limpiar();

                    ArrayList array = new ArrayList();

                    try {
                        log.debug("JCORTEZ: INICIO INGRESO DATOS EN MATRIZ");
                        long tmpIni1 = System.currentTimeMillis();

                        DBCampAcumulada.getDatosExisteDNI(array, vdni);
                        if (array.size() > 0) {
                            VariablesCampAcumulada.vDniCliente =
                                    FarmaUtility.getValueFieldArrayList(array, 0, COL_DNI).trim();
                            VariablesCampAcumulada.vNomCliente =
                                    FarmaUtility.getValueFieldArrayList(array, 0, COL_NOM_CLI).trim();
                            VariablesCampAcumulada.vApePatCliente =
                                    FarmaUtility.getValueFieldArrayList(array, 0, COL_APE_PAT).trim();
                            VariablesCampAcumulada.vApeMatCliente =
                                    FarmaUtility.getValueFieldArrayList(array, 0, COL_APE_MAT).trim();
                            VariablesCampAcumulada.vSexo =
                                    FarmaUtility.getValueFieldArrayList(array, 0, COL_SEX_CLI).trim();
                            VariablesCampAcumulada.vFecNacimiento =
                                    FarmaUtility.getValueFieldArrayList(array, 0, COL_FEC_NAC).trim();
                            VariablesCampAcumulada.vDireccion =
                                    FarmaUtility.getValueFieldArrayList(array, 0, COL_DIR_CLI).trim();
                            VariablesCampAcumulada.vTelefono =
                                    FarmaUtility.getValueFieldArrayList(array, 0, COL_TLF_CLI).trim();
                            VariablesCampAcumulada.vCelular =
                                    FarmaUtility.getValueFieldArrayList(array, 0, COL_CEL_CLI).trim();
                            VariablesCampAcumulada.vCelular =
                                    FarmaUtility.getValueFieldArrayList(array, 0, COL_EMAIL).trim();

                        }

                        //comentado
                        //UtilityFidelizacion.insertarTarjetaMatriz(vNuevaTarjetaFidelizacion, VariablesCampAcumulada.vDniCliente);

                        long tmpFin1 = System.currentTimeMillis();
                        log.debug("JCORTEZ: FIN INGRESO DATOS EN MATRIZ -----" + "DURACION: " + (tmpFin1 - tmpIni1) +
                                  " milisegundos");

                        VariablesCampAcumulada.vDniCliente = "";
                        VariablesCampAcumulada.vNomCliente = "";
                        VariablesCampAcumulada.vApePatCliente = "";
                        VariablesCampAcumulada.vApeMatCliente = "";
                        VariablesCampAcumulada.vSexo = "";
                        VariablesCampAcumulada.vFecNacimiento = "";
                        VariablesCampAcumulada.vDireccion = "";
                        VariablesCampAcumulada.vTelefono = "";
                        VariablesCampAcumulada.vCelular = "";
                        VariablesCampAcumulada.vEmail = "";

                    } catch (SQLException e) {
                        log.error("", e);
                    }
                    cerrarVentana(true);
                } else
                    cerrarVentana(false);
                /** fin veliz **/
            } else {

                //JCORTEZ 04.08.09 Se obtiene dni del cliente para cargar cupones emitidos
                VariablesVentas.dniListCupon = vdni.trim();

                log.debug("JCORTEZ: EL CLIENTE EXISTE");
                //log.debug("\n ENTRA PARTA BUSCAR 2 \n");
                FarmaUtility.moveFocus(tblLista);

                //FarmaUtility.moveFocus(tblLista);

                if (tblLista.getRowCount() > 0) {
                    tblLista.setRowSelectionInterval(0, 0);
                    long tiempoInicio = System.currentTimeMillis();
                    log.debug("JCORTEZ: INICIO CARGA DEL CLIENTE EXISTENTE");
                    setNumeroTarjeta(nroTarjetaLealtad);
                    long tiempoFin = System.currentTimeMillis();
                    log.debug("JCORTEZ: FIN CARGA DEL CLIENTE EXISTENTE -----" + "DURACION: " +
                              (tiempoFin - tiempoInicio) + " milisegundos");
                    if (FarmaVariables.vAceptar) {
                        limpiar();
                        cerrarVentana(true);
                    } else
                        cerrarVentana(false);
                }
            }

        } catch (SQLException e) {
            log.error("", e);
        }
    }

    private void tblLista_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtBuscar_keyTyped(KeyEvent e) {
        //FarmaUtility.admitirSoloDigitos(e, txtBuscar, 0, this);
    }

    private void setNumeroTarjeta(String pNroTarjetaLealtad) {

        String vdni = "";

        try {
            if (VariablesFidelizacion.auxDataCli.size() > 0) {
                log.debug("buscando tarjetas por dni: " + VariablesFidelizacion.auxDataCli);
                vdni = ((String)((ArrayList)VariablesFidelizacion.auxDataCli.get(0)).get(1)).trim();
                log.debug("dni: " + vdni);
                DBFidelizacion.buscarTarjetasXDNI(tableModel, vdni.trim(), pNroTarjetaLealtad);
                txtBuscar.setText(vdni);
                FarmaUtility.moveFocus(tblLista);
            }
        } catch (SQLException e) {
            log.error("", e);
        }

        int vRow = tblLista.getSelectedRow();
        String vCodTarjeta = "";
        //VariablesFidelizacion.vNumTarjeta = tblLista.getValueAt(vRow, 0).toString();
        //VariablesFidelizacion.vDniCliente = txtBuscar.getText().trim();
        if (VariablesFidelizacion.auxDataCli.size() > 0) {
            vCodTarjeta = ((String)((ArrayList)VariablesFidelizacion.auxDataCli.get(0)).get(0)).trim();
        } else {
            vCodTarjeta = tblLista.getValueAt(vRow, 0).toString();
            log.debug("validando la tarjeta:" + vCodTarjeta);
        }

        //Inicio
        //Se colocara la tarjeta en el local segun la inf de matriz
        //
        /*
        if(VariablesFidelizacion.vIndConexion.trim().equalsIgnoreCase("S"))
        {
            UtilityFidelizacion.creaTarjetaLocal(vCodTarjeta,VariablesFidelizacion.vIndConexion);
        }
        */
        //Fin

        log.debug("JCORTEZ: TRAE TARJETA DE MATRIZ A LOCAL");
        UtilityFidelizacion.creaTarjetaLocal(vCodTarjeta, VariablesFidelizacion.vIndConexion);

        UtilityFidelizacion.validaLecturaTarjeta(this, vCodTarjeta, myParentFrame);
        log.debug("****TARJETA******:" + vCodTarjeta);
    }

    /**
     * Metodo que valida la nueva tarjeta fidelizacion creada en el local para
     * que muestre la interfaz para el ingreso de datos del cliente.
     * @author  dveliz
     * @since   13.02.2009
     **/
    private void setNumeroTarjeta(String vNuevaTarjetaFidelizacion, String pNroTarjetaLealtad) {
        //FarmaUtility.showMessage(this, vNuevaTarjetaFidelizacion, null);
        String vCodTarjeta = vNuevaTarjetaFidelizacion;

        log.debug("JCORTEZ: VALIDAR DATOS DE CLIENTE");
        UtilityFidelizacion.validaLecturaTarjeta(this, vCodTarjeta, myParentFrame);

        //JCORTEZ 02.07.09
        if (VariablesFidelizacion.auxDataCli.size() > 0) { // sigue la creacion de la tarjeta, validacion, etc
            setNumeroTarjeta(pNroTarjetaLealtad);
            //buscarTarjetas();
        }

        log.debug("JCORTEZ: LUEGO DE VALIDAR DATOS DEL CLIENTE");
        log.debug("****TARJETA******:" + vCodTarjeta);
    }

    public void limpiar() {
        txtBuscar.setText("");
        FarmaUtility.moveFocus(txtBuscar);
    }

    public void fSeleccionButton() {
        //FarmaUtility.moveFocus(rbEfectivo);
        //rbEfectivo.
        if (rbEfectivo.isSelected()) {
            rbTarjeta.setSelected(true);
            rbEfectivo.setSelected(false);
            //reinicia
            lblTipoTarjeta.setVisible(false);
            pCodFPTarjeta = "";
            pNombreTarjeta = "";
            lblTipoTarjeta.setText("");
            //
            cargaCombo(true);
            if (listaTarjetas.size() == 0) {
                lblTipoTarjeta.setVisible(true);
                lblTipoTarjeta.setText("Todas las Tarjetas.");
            }
            if (listaTarjetas.size() == 1) {
                //coloca a tarjeta nada mas aplica.
                lblTipoTarjeta.setVisible(true);
                lblTipoTarjeta.setText(FarmaUtility.getValueFieldArrayList(listaTarjetas, 0, 1));
                log.debug("coloca datos de unica tarjeta al moverse:" +
                          FarmaUtility.getValueFieldArrayList(listaTarjetas, 0, 1));
            }

        } else {
            rbTarjeta.setSelected(false);
            rbEfectivo.setSelected(true);
            cargaCombo(false);
        }
    }

    public void cargaCombo(boolean tipo) {
        //obtiene datos de forma de pago.
        //
        log.debug("lista" + listaTarjetas);
        if (tipo && listaTarjetas.size() > 0) {
            String nombre, codigo;

            if (listaTarjetas.size() == 1) {
                codigo = FarmaUtility.getValueFieldArrayList(listaTarjetas, 0, 1);
                if (codigo.trim().equalsIgnoreCase(ConstantsFidelizacion.COD_FP_TARJETA_TOTAL))
                    nombre = "Todas las Tarjetas.";
                else
                    nombre = FarmaUtility.getValueFieldArrayList(listaTarjetas, 0, 2);

                lblTipoTarjeta.setText(nombre);
            } else {
                for (int i = 0; i < listaTarjetas.size(); i++) {
                    JMenuItem menuItem =
                        new JRadioButtonMenuItem(FarmaUtility.getValueFieldArrayList(listaTarjetas, i, 1));
                    menuItem.addActionListener(menuListener);
                    popup.add(menuItem);
                }
                popup.show(rbTarjeta, -45, 25);
                //jtaMensaje.setComponentPopupMenu(popup);
            }
        } else {
            lblTipoTarjeta.setVisible(false);
            popup.removeAll();
        }
    }


    private void seleccionTarjeta(ActionEvent e) {
        String aux = e.getActionCommand().toString();
        log.debug("Popup menu item [" + e.getActionCommand() + "] was pressed.");
        for (int i = 0; i < listaTarjetas.size(); i++) {
            if (FarmaUtility.getValueFieldArrayList(listaTarjetas, i, 1).trim().equalsIgnoreCase(aux)) {
                pCodFPTarjeta = FarmaUtility.getValueFieldArrayList(listaTarjetas, i, 0).trim();
                pNombreTarjeta = FarmaUtility.getValueFieldArrayList(listaTarjetas, i, 1).trim();
            }
        }
        log.debug("pCodFPTarjeta:" + pCodFPTarjeta);
        log.debug("pNombreTarjeta:" + pNombreTarjeta);
        lblTipoTarjeta.setText(pNombreTarjeta.trim());
        lblTipoTarjeta.setVisible(true);

    }

    private void rbTarjeta_keyReleased(KeyEvent e) {
        log.debug("tecla:" + e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            log.debug("Cerraron el Popup : " + pCodFPTarjeta);

            if (pCodFPTarjeta.trim().length() == 0) {
                rbTarjeta.setSelected(false);
                rbEfectivo.setSelected(true);
                cargaCombo(false);
            }
        }
    }

    private void txtBuscar_focusLost(FocusEvent e) {
        txtBuscar.grabFocus();
    }

    public void setVIsPresionaTeclaF12(boolean vIsPresionaTeclaF12) {
        this.vIsPresionaTeclaF12 = vIsPresionaTeclaF12;
    }

    public boolean isVIsPresionaTeclaF12() {
        return vIsPresionaTeclaF12;
    }

    public void setIsTarjetaCampania(boolean isTarjetaCampania) {
        this.isTarjetaCampania = isTarjetaCampania;
    }

    public void setIsMantenimientoCliente(boolean isMantenimientoCliente) {
        this.isMantenimientoCliente = isMantenimientoCliente;
    }

    public boolean isIsMantenimientoCliente() {
        return isMantenimientoCliente;
    }

    // An inner class to show when popup events occur

    class PopupPrintListener implements PopupMenuListener {
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            log.debug("Popup menu will be visible!");
        }

        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            log.debug("Popup menu will be invisible!");
        }

        public void popupMenuCanceled(PopupMenuEvent e) {
            log.debug("Popup menu is hidden!");
            log.debug("Cerraron el Popup : " + pCodFPTarjeta);

            if (pCodFPTarjeta.trim().length() == 0) {
                rbTarjeta.setSelected(false);
                rbEfectivo.setSelected(true);
                cargaCombo(false);
            }
        }
    }

    /**
     * Carga las formas de pago para asi ampliar la pantalla
     */

    public boolean isNumero(String pCadena) {
        int n;
        try {
            n = Integer.parseInt(pCadena.trim());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public void cargaFormasDePagoCampanaUso() {
        listaFormasDePago = new ArrayList();

        Map mapAux = new HashMap(); //mapa de la campania del listado de cupones
        String codCampCupon = "", campanaCupones = "";
        //ESTO ES PARA ELIMINAR LAS CAMPAÑAS AUTOMATIACAS YA CARGADAS
        //PORQUE AHORA SE PUEDE HACER F12 VARIAS VECES.
        for (int i = 0; i < VariablesVentas.vArrayList_Cupones.size(); i++) {
            mapAux = (HashMap)VariablesVentas.vArrayList_Cupones.get(i);
            codCampCupon = ((String)mapAux.get("COD_CAMP_CUPON")).trim() + "";
            if (isNumero(codCampCupon)) {
                if (i + 1 == VariablesVentas.vArrayList_Cupones.size())
                    campanaCupones += codCampCupon;
                else
                    campanaCupones += codCampCupon + "@";
            }
        }

        try {
            DBFidelizacion.cargaFormasPagoUsoXCampana(listaFormasDePago, campanaCupones);
            log.debug("listaFormasDePago.size():" + listaFormasDePago.size());
            if (listaFormasDePago.size() > 0) {
               
                lblEnter.setBounds(new Rectangle(55, 215, 95, 20));
                lblEsc.setBounds(new Rectangle(155, 215, 90, 20));
                jPanel1.setBounds(new Rectangle(10, 65, 285, 100));
                lblSeleccionTipoDoc.setBounds(new Rectangle(40, 165, 240, 50));
                this.setSize(new Dimension(314, 262));
                
                String pTipoFPEfectivo = "", pTipoFPTarjeta = "", pAux = "";
                listaTarjetas = new ArrayList();
                for (int i = 0; i < listaFormasDePago.size(); i++) {
                    log.debug("i" + i);
                    pAux = FarmaUtility.getValueFieldArrayList(listaFormasDePago, i, 4);
                    if (pAux.trim().equalsIgnoreCase("S")) {
                        pTipoFPEfectivo = "S";
                    } else {
                        pAux = FarmaUtility.getValueFieldArrayList(listaFormasDePago, i, 3);
                        if (pAux.trim().equalsIgnoreCase("S")) {
                            pTipoFPTarjeta = "S";
                            ArrayList fp = new ArrayList();
                            fp.add(FarmaUtility.getValueFieldArrayList(listaFormasDePago, i, 1));
                            fp.add(FarmaUtility.getValueFieldArrayList(listaFormasDePago, i, 2));
                            listaTarjetas.add(fp);
                            log.debug("listaTarjetas:" + listaTarjetas);
                        }
                    }
                }
                //
                if (pTipoFPEfectivo.trim().equalsIgnoreCase("S") || pTipoFPTarjeta.trim().equalsIgnoreCase("S")) {
                    rbEfectivo.setVisible(true);
                    rbTarjeta.setVisible(true);
                }
                log.debug("listaTarjetas:" + listaTarjetas.size());
                if (listaTarjetas.size() == 1) {
                    //coloca a tarjeta nada mas aplica.
                    lblTipoTarjeta.setVisible(true);
                    lblTipoTarjeta.setText(FarmaUtility.getValueFieldArrayList(listaTarjetas, 0, 1));
                    log.debug("coloca datos de unica tarjeta:" +
                              FarmaUtility.getValueFieldArrayList(listaTarjetas, 0, 1));
                    log.debug("get:" + lblTipoTarjeta.getText());
                }

            } else {
                this.setSize(new Dimension(314, 170));
                lblEnter.setBounds(new Rectangle(55, 120, 95, 20));
                lblEsc.setBounds(new Rectangle(155, 120, 90, 20));
                jPanel1.setBounds(new Rectangle(10, 150, 285, 100));
            }

        } catch (SQLException e) {
            log.error("", e);
            this.setSize(new Dimension(314, 170));
            lblEnter.setBounds(new Rectangle(55, 120, 95, 20));
            lblEsc.setBounds(new Rectangle(155, 120, 90, 20));
            jPanel1.setBounds(new Rectangle(10, 150, 285, 100));
        } finally {

        }
    }

    public boolean isSeleccionFormaPago() {
        boolean resultado = false;
        if (listaFormasDePago.size() > 0) {
            //validará que seleccion la forma de pago.
            if (rbEfectivo.isSelected())
                return true;

            if (rbTarjeta.isSelected()) {
                if (listaTarjetas.size() > 0) {
                    if (listaTarjetas.size() > 1) {
                        if (pCodFPTarjeta.trim().length() > 0)
                            return true;
                    } else {
                        //es porque solo hay una sola tarjeta.
                        pCodFPTarjeta = FarmaUtility.getValueFieldArrayList(listaTarjetas, 0, 0).trim();
                        pNombreTarjeta = FarmaUtility.getValueFieldArrayList(listaTarjetas, 0, 1).trim();
                        log.debug("pCodFPTarjeta:" + pCodFPTarjeta);
                        log.debug("pNombreTarjeta:" + pNombreTarjeta);
                        if (pCodFPTarjeta.trim().length() > 0)
                            return true;
                    }

                } else
                    return true;
            }
        } else
            resultado = true;

        return resultado;
    }

    public void guardaVariablesFormaPagoUsoCampana() {
        if (listaFormasDePago.size() > 0) {
            //Esto quiere decir que ES OBLIGATORIO la opcion de FORMAS DE PAGO
            //validará que seleccion la forma de pago.
            if (rbEfectivo.isSelected()) {
                VariablesFidelizacion.vIndUsoEfectivo = "S";
                VariablesFidelizacion.vIndUsoTarjeta = "N";
                VariablesFidelizacion.vCodFPagoTarjeta = "NULL";
                VariablesFidelizacion.vNamePagoTarjeta = "Efectivo";
            } else {
                VariablesFidelizacion.vIndUsoEfectivo = "N";
                if (rbTarjeta.isSelected()) {
                    VariablesFidelizacion.vIndUsoTarjeta = "S";
                    if (listaTarjetas.size() > 0) {


                        if (listaTarjetas.size() > 1) {
                            if (pCodFPTarjeta.trim().length() > 0) {
                                if (pCodFPTarjeta.trim().equalsIgnoreCase(ConstantsFidelizacion.COD_FP_TARJETA_TOTAL)) {
                                    VariablesFidelizacion.vCodFPagoTarjeta = "T";
                                    VariablesFidelizacion.vNamePagoTarjeta =
                                            lblTipoTarjeta.getText().trim().toUpperCase();
                                } else {
                                    VariablesFidelizacion.vCodFPagoTarjeta = pCodFPTarjeta.trim();
                                    VariablesFidelizacion.vNamePagoTarjeta =
                                            lblTipoTarjeta.getText().trim().toUpperCase();
                                }
                            }
                        } else {
                            VariablesFidelizacion.vCodFPagoTarjeta =
                                    FarmaUtility.getValueFieldArrayList(listaTarjetas, 0, 0).trim();
                            VariablesFidelizacion.vNamePagoTarjeta =
                                    FarmaUtility.getValueFieldArrayList(listaTarjetas, 0, 1).trim();
                        }
                    } else {
                        //NUNCA DEBE DE INGRESAR EN VACIO
                        FarmaUtility.showMessage(this, "Error de APLICACION.", txtBuscar);
                        //Todas las Tarjetas
                        VariablesFidelizacion.vCodFPagoTarjeta = "T";
                        VariablesFidelizacion.vNamePagoTarjeta = lblTipoTarjeta.getText().trim().toUpperCase();
                    }
                }
            }
            log.debug("FIN vIndUsoEfectivo:" + VariablesFidelizacion.vIndUsoEfectivo);
            log.debug("FIN vIndUsoTarjeta:" + VariablesFidelizacion.vIndUsoTarjeta);
            log.debug("FIN vCodFPagoTarjeta:" + VariablesFidelizacion.vCodFPagoTarjeta);

        }
    }

    public void limpiaVariablesFormaPago() {
        VariablesFidelizacion.vIndUsoEfectivo = "NULL";
        VariablesFidelizacion.vIndUsoTarjeta = "NULL";
        VariablesFidelizacion.vCodFPagoTarjeta = "NULL";
    }
}
