package mifarma.ptoventa.administracion.roles;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConnection;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;


import mifarma.ptoventa.administracion.roles.reference.ConstantsRolesTmp;
import mifarma.ptoventa.administracion.roles.reference.DBRolesTmp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2015 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgReportesRolesTmp.java<br>
 * <br>
 * Histórico de Creación/_fModificación<br>
 * CESAR     25.02.2015   Creación<br>
 * <br>
 * @author Cesar Huanes<br>
 * @version 1.0<br>
 *
 */

public class DlgReportesRolesTmp extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgReportesRolesTmp.class);
    private Frame myParentFrame;
    private FarmaTableModel tableModelComprobantesAnulados;
    private FarmaTableModel tableModelTransferencias;
    private FarmaTableModel tableModelCierreTurno;


    private GridLayout gridLayout1 = new GridLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JPanelHeader txtNumeroCaja = new JPanelHeader();
    private JButtonLabel btnDatosGenerales = new JButtonLabel();
  
    private JTextFieldSanSerif txtFecha = new JTextFieldSanSerif();
    private JLabelOrange lblNombre_T = new JLabelOrange();
    private JLabelOrange lblNombre = new JLabelOrange();
    private JPanelTitle pnlFormasPago = new JPanelTitle();
  
    private JScrollPane scrFormasPago = new JScrollPane();
   
    private JButtonLabel btnFormasPago = new JButtonLabel();
   
  
    private JLabelWhite lblSubTotalCuadra_T = new JLabelWhite();
    private JLabelWhite lblSubTotalCuadra = new JLabelWhite();
  
    private JLabelWhite lblMontoRegistradoSistema_T = new JLabelWhite();
    private JLabelWhite lblMontoRegistradoSistema = new JLabelWhite();
    private JPanelTitle pnlTotalFormasPago = new JPanelTitle();
    private JLabelWhite lblSubTotalFormaPago = new JLabelWhite();
    private JLabelWhite lblSubTotalFormaPago_T = new JLabelWhite();
    private JButton btnRegistrar = new JButton();
    private JButton btnEnviarReporte = new JButton();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JTable tblAnulados = new JTable();
   
    private JTable tblTransferencias = new JTable();
    private JTable tblCierreTurno = new JTable();
    private JLabelWhite lblMontoRegistradoCierreTurno = new JLabelWhite();
    private JLabelWhite lblMontoRegistradoCierreTurno_T = new JLabelWhite();
    private JPanelTitle pnlEfecRendido = new JPanelTitle();
    private JButtonLabel btnEfectivoRendido = new JButtonLabel();
    private JScrollPane scrEfectivoRendido = new JScrollPane();
    private JPanelTitle pnlEfecRecaudado = new JPanelTitle();
    private JButtonLabel btnEfectivoRecaudado = new JButtonLabel();
    private JScrollPane scrEfectivoRecaudado = new JScrollPane();
    private JPanelTitle pnlTotalEfecRecaudado = new JPanelTitle();
    private JLabelWhite lblSubTotalEfecRecaudado = new JLabelWhite();
    private JLabelWhite lblSubTotalEfecRecaudado_T = new JLabelWhite();
    private JPanelTitle pnlTotalEfecRendido = new JPanelTitle();
    private JLabelWhite lblSubTotalEfecRendido = new JLabelWhite();
    private JLabelWhite lblSubTotalEfecRendido_T = new JLabelWhite();
    private JLabelWhite lblDiferencia_T = new JLabelWhite();
    private JLabelWhite lblDiferencia = new JLabelWhite();
    private JButtonLabel btnDiaVenta = new JButtonLabel();
    private JTextArea txtSObs = new JTextArea();
    private JButtonLabel btnObs = new JButtonLabel();
    private JLabelOrange lblMsgVisado_T = new JLabelOrange();
   
    private JScrollPane jScrollPane1 = new JScrollPane();
  
    private JLabelWhite lblMslReclamoNavsat = new JLabelWhite();
   
    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgReportesRolesTmp() {
        this(null, "", false);
    }

    public DlgReportesRolesTmp(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(750, 600));
        this.setResizable(false);
        this.getContentPane().setLayout(gridLayout1);
        this.setTitle("Reportes Admin.Temporal");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        jPanelTitle1.setBounds(new Rectangle(10, 5, 720, 20));
        txtNumeroCaja.setBounds(new Rectangle(10, 30, 720, 30));
        txtNumeroCaja.setBackground(Color.white);
        btnDatosGenerales.setText("Reportes");
        btnDatosGenerales.setBounds(new Rectangle(10, 0, 110, 20));
        btnDatosGenerales.setMnemonic('D');
        btnDatosGenerales.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDatosGenerales_actionPerformed(e);
            }
        });
       
       
        txtFecha.setBounds(new Rectangle(100, 5, 85, 20));
        txtFecha.setDocument(new FarmaLengthText(10));
        txtFecha.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFecha_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFecha_keyReleased(e);
            }
        });
        lblNombre_T.setText("Nombre QF: ");
        lblNombre_T.setBounds(new Rectangle(315, 5, 75, 20));
        lblNombre.setBounds(new Rectangle(405, 5, 290, 20));
        pnlFormasPago.setBounds(new Rectangle(10, 65, 720, 20));
       
        scrFormasPago.setBounds(new Rectangle(10, 85, 720, 115));
      
        btnFormasPago.setText("Reporte de Pedidos Anulados");
        btnFormasPago.setBounds(new Rectangle(10, 0, 285, 20));
        btnFormasPago.setMnemonic('f');
        btnFormasPago.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnFormasPago_actionPerformed(e);
            }
        });
       
      
        lblSubTotalCuadra_T.setText("Sub Total : S/.");
        lblSubTotalCuadra_T.setBounds(new Rectangle(10, 5, 80, 15));
        lblSubTotalCuadra.setText("0.00");
        lblSubTotalCuadra.setBounds(new Rectangle(105, 5, 110, 15));
        lblSubTotalCuadra.setHorizontalAlignment(SwingConstants.RIGHT);
      
        lblMontoRegistradoSistema_T.setText("Monto Total Registrado por Sistema : S/.");
        lblMontoRegistradoSistema_T.setBounds(new Rectangle(350, 20, 230, 15));
        lblMontoRegistradoSistema.setBounds(new Rectangle(620, 20, 90, 15));
        lblMontoRegistradoSistema.setHorizontalAlignment(SwingConstants.RIGHT);
        lblMontoRegistradoSistema.setText("0.00");
        pnlTotalFormasPago.setBounds(new Rectangle(140, 200, 230, 25));
        lblSubTotalFormaPago.setText("0.00");
        lblSubTotalFormaPago.setBounds(new Rectangle(110, 5, 100, 15));
        lblSubTotalFormaPago.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSubTotalFormaPago_T.setText("Sub Total : S/.");
        lblSubTotalFormaPago_T.setBounds(new Rectangle(10, 5, 80, 15));
        btnRegistrar.setText("Buscar");
        btnRegistrar.setBounds(new Rectangle(200, 5, 85, 20));
        btnRegistrar.setMnemonic('s');
        btnRegistrar.setFont(new Font("SansSerif", 1, 11));
        btnRegistrar.setDefaultCapable(false);
        btnRegistrar.setFocusable(false);
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setRequestFocusEnabled(false);
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRegistrar_actionPerformed(e);
            }
        });
        
        btnEnviarReporte.setText("Enviar Reporte");
        btnEnviarReporte.setBounds(new Rectangle(100,550, 150, 20));
        lblF1.setText("[F1]Enviar Reporte");
        lblF1.setBounds(new Rectangle(200,550, 140, 20));
        btnEnviarReporte.setMnemonic('v');
        btnEnviarReporte.setFont(new Font("SansSerif", 1, 11));
        btnEnviarReporte.setDefaultCapable(false);
        btnEnviarReporte.setFocusable(false);
        btnEnviarReporte.setFocusPainted(false);
        btnEnviarReporte.setRequestFocusEnabled(false);
        btnEnviarReporte.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnEnviarReporte_actionPerformed(e);
            }
        });
       
        
     
        lblEsc.setBounds(new Rectangle(400,550,120, 20));
        lblEsc.setText("[ ESC ] Salir");
        tblAnulados.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblAnulados_keyPressed(e);
            }
        });
       
        tblTransferencias.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblTransferencias_keyPressed(e);
            }
        });
        tblCierreTurno.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblCierreTurno_keyPressed(e);
            }
        });
        lblMontoRegistradoCierreTurno.setText("0.00");
        lblMontoRegistradoCierreTurno.setBounds(new Rectangle(625, 5, 85, 15));
        lblMontoRegistradoCierreTurno.setHorizontalAlignment(SwingConstants.RIGHT);
        lblMontoRegistradoCierreTurno_T.setText("Monto Total Registrado por Cierres Turno : S/.");
        lblMontoRegistradoCierreTurno_T.setBounds(new Rectangle(350, 5, 265, 15));
        pnlEfecRendido.setBounds(new Rectangle(10,400, 720, 20));
        btnEfectivoRendido.setText("Reporte de Cierre de Turno");
        btnEfectivoRendido.setBounds(new Rectangle(10, 0, 310, 20));
        btnEfectivoRendido.setMnemonic('R');
        btnEfectivoRendido.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnEfectivoRendido_actionPerformed(e);
            }
        });
        scrEfectivoRendido.setBounds(new Rectangle(10, 420, 720, 115));
        pnlEfecRecaudado.setBounds(new Rectangle(10, 230, 720, 20));
        btnEfectivoRecaudado.setText("Reporte de Transferencias Recibidas y Emitidas");
        btnEfectivoRecaudado.setBounds(new Rectangle(10, 0, 280, 20));
        btnEfectivoRecaudado.setMnemonic('E');
        btnEfectivoRecaudado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnEfectivoRecaudado_actionPerformed(e);
            }
        });
        scrEfectivoRecaudado.setBounds(new Rectangle(10, 250, 720, 115));
        pnlTotalEfecRecaudado.setBounds(new Rectangle(140, 405, 230, 25));
        lblSubTotalEfecRecaudado.setText("0.00");
        lblSubTotalEfecRecaudado.setBounds(new Rectangle(100, 5, 105, 15));
        lblSubTotalEfecRecaudado.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSubTotalEfecRecaudado_T.setText("Sub Total : S/.");
        lblSubTotalEfecRecaudado_T.setBounds(new Rectangle(10, 5, 80, 15));
        pnlTotalEfecRendido.setBounds(new Rectangle(125, 585, 245, 25));
        lblSubTotalEfecRendido.setText("0.00");
        lblSubTotalEfecRendido.setBounds(new Rectangle(100, 5, 115, 15));
        lblSubTotalEfecRendido.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSubTotalEfecRendido_T.setText("Sub Total : S/.");
        lblSubTotalEfecRendido_T.setBounds(new Rectangle(10, 5, 80, 15));
      
        lblDiferencia_T.setText("Diferencia : S/.");
        lblDiferencia_T.setBounds(new Rectangle(570, 5, 85, 15));
        lblDiferencia.setText("0.00");
        lblDiferencia.setBounds(new Rectangle(665, 5, 65, 15));
        lblDiferencia.setHorizontalAlignment(SwingConstants.RIGHT);
        btnDiaVenta.setText("Ingrese Fecha :");
        btnDiaVenta.setBounds(new Rectangle(10, 5, 85, 20));
        btnDiaVenta.setForeground(new Color(255, 130, 14));
        btnDiaVenta.setMnemonic('d');
       
        txtSObs.setDocument(new FarmaLengthText(290));
        txtSObs.setRows(2);
        txtSObs.setSelectionEnd(200);
        txtSObs.setSelectionStart(200);
        txtSObs.setTabSize(20);
        txtSObs.setLineWrap(true);
        txtSObs.setWrapStyleWord(true);
        txtSObs.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtSObs_keyPressed(e);
            }
        });
        btnObs.setText("Observaciones :");
        btnObs.setMnemonic('b');
        btnObs.setBounds(new Rectangle(5, 15, 95, 25));
        btnObs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnObs_actionPerformed(e);
            }
        });
        
        lblMsgVisado_T.setBounds(new Rectangle(10, 0, 550, 25));
        lblMsgVisado_T.setFont(new Font("SansSerif", 1, 14));
        lblMsgVisado_T.setForeground(Color.white);
       
        lblMslReclamoNavsat.setText("Existen comprobantes por reclamar");
        lblMslReclamoNavsat.setBounds(new Rectangle(5, 10, 210, 20));
        lblMslReclamoNavsat.setFont(new Font("SansSerif", 1, 12));
       
        lblMslReclamoNavsat.setVisible(false);
      
        scrEfectivoRendido.getViewport();
        scrEfectivoRecaudado.getViewport();
        jScrollPane1.getViewport().add(txtSObs, null);
      
        pnlTotalEfecRecaudado.add(lblSubTotalEfecRecaudado, null);
        pnlTotalEfecRecaudado.add(lblSubTotalEfecRecaudado_T, null);
        pnlTotalEfecRendido.add(lblSubTotalEfecRendido, null);
        pnlTotalEfecRendido.add(lblSubTotalEfecRendido_T, null);
        pnlTotalFormasPago.add(lblSubTotalFormaPago, null);
        pnlTotalFormasPago.add(lblSubTotalFormaPago_T, null);
        txtNumeroCaja.add(btnDiaVenta, null);
        txtNumeroCaja.add(btnRegistrar, null);
       
        txtNumeroCaja.add(txtFecha, null);
      
        jPanelTitle1.add(btnDatosGenerales, null);
    
        scrEfectivoRecaudado.getViewport().add(tblTransferencias, null);
        jContentPane.add(scrEfectivoRecaudado, null);
        pnlEfecRecaudado.add(btnEfectivoRecaudado, null);
        jContentPane.add(pnlEfecRecaudado, null);
        scrEfectivoRendido.getViewport().add(tblCierreTurno, null);
        jContentPane.add(scrEfectivoRendido, null);
        pnlEfecRendido.add(btnEfectivoRendido, null);
        jContentPane.add(pnlEfecRendido, null);
        jContentPane.add(lblEsc, null);
        //jContentPane.add(btnEnviarReporte, null);
        jContentPane.add(lblF1, null);
              
        scrFormasPago.getViewport().add(tblAnulados, null);
        jContentPane.add(scrFormasPago, null);
       
        pnlFormasPago.add(btnFormasPago, null);
        jContentPane.add(pnlFormasPago, null);
        jContentPane.add(txtNumeroCaja, null);
        jContentPane.add(jPanelTitle1, null);
        this.getContentPane().add(jContentPane, null);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
       
       // FarmaConnection.closeConnection();
    
        initTableAnulados();
        initTableTrasnferencias();
        initTableCierreTurno();
       
        FarmaUtility.moveFocus(txtFecha);
     
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTableAnulados() {
        tableModelComprobantesAnulados =
                new FarmaTableModel(ConstantsRolesTmp.columsListaAnualdos, ConstantsRolesTmp.defaultListaAnuladosResumen,
                                    0);
        FarmaUtility.initSimpleList(tblAnulados, tableModelComprobantesAnulados,
                                    ConstantsRolesTmp.columsListaAnualdos);
                cargaCompAnulados();
    }

    private void cargaCompAnulados() {
        try {
            DBRolesTmp.lstAnuladosUsuario(tableModelComprobantesAnulados,
                                                                 txtFecha.getText().trim());
            if (tblAnulados.getRowCount() > 0)
                FarmaUtility.ordenar(tblAnulados, tableModelComprobantesAnulados, 0, FarmaConstants.ORDEN_ASCENDENTE);
         } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this,
                                     "Error al cargar Documentos anulados.\n" +
                    sql, null);
        }
    }

   


    private void initTableTrasnferencias() {
        tableModelTransferencias =
                new FarmaTableModel(ConstantsRolesTmp.columsListaTransferencias, ConstantsRolesTmp.defaultListaTransferencias,
                                    0);
        FarmaUtility.initSimpleList(tblTransferencias, tableModelTransferencias,
                                    ConstantsRolesTmp.columsListaTransferencias);
           cargaResumenTransferencias();
    }

    private void cargaResumenTransferencias() {
        try {
            DBRolesTmp.lstResumenTransferencias(tableModelTransferencias,
                                                                  txtFecha.getText().trim());
            if (tblTransferencias.getRowCount() > 0)
                FarmaUtility.ordenar(tblTransferencias, tableModelTransferencias, 0,
                                     FarmaConstants.ORDEN_ASCENDENTE);
          
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al cargar resumen  de Tranferencias.\n" +
                    sql, null);
        }

    }

    private void initTableCierreTurno() {
        tableModelCierreTurno =
                new FarmaTableModel(ConstantsRolesTmp.columsListaCierreTurnoSinVB, ConstantsRolesTmp.defaultCierreTurnoSinVB,
                                    0);
        FarmaUtility.initSimpleList(tblCierreTurno, tableModelCierreTurno,
                                    ConstantsRolesTmp.columsListaCierreTurnoSinVB);
              cargaResumenCierreTurno();
    }

    private void cargaResumenCierreTurno() {
        try {
                DBRolesTmp.lstCierreTurnoSinVB(tableModelCierreTurno,
                                                                   txtFecha.getText().trim()); 
            if (tblCierreTurno.getRowCount() > 0)
                FarmaUtility.ordenar(tblCierreTurno, tableModelCierreTurno, 0,
                                     FarmaConstants.ORDEN_ASCENDENTE);
            lblSubTotalEfecRendido.setText("" +
                                           FarmaUtility.formatNumber(FarmaUtility.sumColumTable(tblCierreTurno,
                                                                                                2)));
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al cargar los cierres de turno .\n" +
                    sql, null);
        }
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
    
            FarmaUtility.centrarVentana(this);
          
            FarmaUtility.moveFocus(txtFecha);
          
    
    }

    private void btnDatosGenerales_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFecha);
    }

    private void btnRegistrar_actionPerformed(ActionEvent e) {
        if (!validaFechaCierreDia())
            return;
        cargaCompAnulados();
        cargaResumenCierreTurno();
        cargaResumenTransferencias();
        
      
       
    }
    
    private void btnEnviarReporte_actionPerformed(ActionEvent e) {
     //se envia el reporte al correo electronico    
        
        
        
    if(!validaFechaCierreDia())
        return;
        
    if(enviaReporte()){
        FarmaUtility.showMessage(this, " Alerta enviada al Administrador. ",
                                 null);    
    }else{
        FarmaUtility.showMessage(this, "No se envio Alerta por problemas Tecnicos o de Comunicación",
                                 null);    
    }
    }
    
    

    private void btnFormasPago_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocusJTable(tblAnulados);
    }

   
    private void btnEfectivoRecaudado_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocusJTable(tblTransferencias);
    }

    private void btnEfectivoRendido_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocusJTable(tblCierreTurno);
    }

    private void txtSObs_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            e.consume();
        else
            chkKeyPressed(e);
    }

    private void btnObs_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtSObs);
        txtSObs.selectAll();
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
       
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
            cerrarVentana(false);
        }
        
       
          
        
    }

    private void txtFecha_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            btnRegistrar.doClick();
        }
        if (e.getKeyCode() == KeyEvent.VK_F1) {
            if(!validaFechaCierreDia())
                return;
                
            if(enviaReporte()){
                FarmaUtility.showMessage(this, " Alerta enviada al Administrador. ",
                                         null);    
            }else{
                FarmaUtility.showMessage(this, "No se envio Alerta por problemas Tecnicos o de Comunicación",
                                         null);    
            }
        }
        chkKeyPressed(e);
        
        
    }

    private void txtFecha_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFecha, e);
    }

    private void tblCierreTurno_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            
        } else
            chkKeyPressed(e);
    }

    private void tblAnulados_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
          
        } else
            chkKeyPressed(e);
    }

   

    private void tblTransferencias_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    private boolean validaFechaCierreDia() {
        if (!FarmaUtility.validaFecha(txtFecha.getText(), "dd/MM/yyyy") ||
            txtFecha.getText().length() != 10) {
            FarmaUtility.showMessage(this, "Ingrese la Fecha  correctamente", txtFecha);
            return false;
        }
        return true;
    }
  


 
    private boolean enviaReporte(){
     String fecReporte="";
     boolean flag=false;
        try {
           fecReporte=txtFecha.getText().trim();
            DBRolesTmp.enviarAlertaEmail(fecReporte);
            flag=true;
        } catch (SQLException e) {
            FarmaUtility.showMessage(this, "Error al enviar alerta al Administrador del Local \n : " + e.getMessage(),
                                     null); 
        }
       return flag; 
    }

    
}
