package mifarma.ptoventa.ventas;

import com.gs.mifarma.componentes.JButtonFunction;
import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import com.gs.mifarma.componentes.JTextFieldSanSerif;

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

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

import mifarma.common.FarmaConnectionRemoto;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLengthText;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.caja.reference.VariablesVirtual;

import mifarma.ptoventa.ventas.reference.DBVentas;

import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DlgConsultarRecargaCorrelativo_AS extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Log log = LogFactory.getLog(DlgConsultarRecargaCorrelativo_AS.class);
    Frame myParentFrame;
    private boolean vAceptar = false;
    
    private JPanelWhite pnlFondo = new JPanelWhite();
    private JPanelHeader pnlHeader = new JPanelHeader();
    private JPanelTitle jPanel4 = new JPanelTitle();
    private JButtonLabel btnCorrelativo = new JButtonLabel();
    private JTextFieldSanSerif txtCorrelativo = new JTextFieldSanSerif();
    private JButtonLabel lblMonto = new JButtonLabel();
    private JTextFieldSanSerif txtMonto = new JTextFieldSanSerif();
    private JButton btnBuscar = new JButton();
    private JEditorPane jEditorPane1 = new JEditorPane();
    private JLabel lblFechaAct = new JLabel();
    private JLabel lblFechaActV = new JLabel();
    private JLabel lblMontoR = new JLabel();
    private JLabel lblMontoR2 = new JLabel();
    private JLabel lblTelefono = new JLabel();
    private JLabel lblTelefono2 = new JLabel();
    private JLabel lblProveedor = new JLabel();
    private JLabel lblProveedor2 = new JLabel();
    private JLabel lblValorCo = new JLabel();
    private JLabel lblFecha = new JLabel();
    private JLabel lblCorrelativo = new JLabel();
    private JLabel lblValorFec = new JLabel();
    private JLabelFunction lblImprimir = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    //JMIRANDA 25.08.2011 Fijar Objeto para Focus
    private Object pObj = txtCorrelativo;

    public DlgConsultarRecargaCorrelativo_AS() {
        this(null, "", false);
    }

    public DlgConsultarRecargaCorrelativo_AS(Frame parent, String title, 
                                             boolean modal) {
        super(parent, title, modal);
        myParentFrame=parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(556, 635));
        this.getContentPane().setLayout( null );
        this.setBounds(new Rectangle(10, 10, 556, 330));
        //this.setContentPane(pnlFondo);
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }

                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }
                });
        pnlFondo.setBounds(new Rectangle(0, 0, 555, 305));
        pnlHeader.setBounds(new Rectangle(10, 10, 530, 35));
        jPanel4.setBounds(new Rectangle(10, 55, 530, 20));
        btnCorrelativo.setText("Correlativo :");
        btnCorrelativo.setBounds(new Rectangle(10, 10, 70, 15));
        btnCorrelativo.setMnemonic('c');
        btnCorrelativo.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnCorrelativo_actionPerformed(e);
                    }
                });
        btnCorrelativo.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        btnCorrelativo_keyPressed(e);
                    }
                });
        txtCorrelativo.setBounds(new Rectangle(85, 5, 115, 25));
        txtCorrelativo.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtCorrelativo_keyPressed(e);
                    }

                    public void keyTyped(KeyEvent e) {
                        txtCorrelativo_keyTyped(e);
                    }
                });
        lblMonto.setText("Monto :");
        lblMonto.setBounds(new Rectangle(240, 10, 45, 15));
        lblMonto.setMnemonic('m');
        txtMonto.setBounds(new Rectangle(295, 5, 80, 25));
        txtMonto.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtMonto_keyPressed(e);
                    }

                    public void keyTyped(KeyEvent e) {
                        txtMonto_keyTyped(e);
                    }
                });
        txtMonto.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        txtMonto_actionPerformed(e);
                    }
                });
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(405, 5, 90, 25));
        btnBuscar.setMnemonic('s');
        btnBuscar.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        btnBuscar_keyPressed(e);
                    }
                });
        btnBuscar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnBuscar_actionPerformed(e);
                    }
                });
        jEditorPane1.setBounds(new Rectangle(20, 235, 510, 25));
        lblFechaAct.setBounds(new Rectangle(150, 210, 95, 15));
        lblFechaAct.setFont(new Font("Dialog", 1, 13));
        lblFechaAct.setForeground(new Color(0, 132, 66));
        lblFechaAct.setMaximumSize(new Dimension(0, 0));
        lblFechaAct.setMinimumSize(new Dimension(0, 0));
        lblFechaAct.setPreferredSize(new Dimension(0, 0));
        lblFechaActV.setBounds(new Rectangle(275, 210, 165, 15));
        lblFechaActV.setBackground(new Color(0, 132, 66));
        lblFechaActV.setForeground(new Color(0, 132, 66));
        lblFechaActV.setFont(new Font("Dialog", 1, 13));
        lblFechaActV.setMaximumSize(new Dimension(0, 0));
        lblFechaActV.setMinimumSize(new Dimension(0, 0));
        lblFechaActV.setPreferredSize(new Dimension(0, 0));
        lblMontoR.setBounds(new Rectangle(150, 180, 90, 15));
        lblMontoR.setFont(new Font("Dialog", 1, 13));
        lblMontoR.setForeground(new Color(0, 132, 66));
        lblMontoR.setMaximumSize(new Dimension(0, 0));
        lblMontoR.setMinimumSize(new Dimension(0, 0));
        lblMontoR.setPreferredSize(new Dimension(0, 0));
        lblMontoR2.setBounds(new Rectangle(275, 180, 120, 15));
        lblMontoR2.setBackground(Color.white);
        lblMontoR2.setFont(new Font("Dialog", 1, 13));
        lblMontoR2.setForeground(new Color(0, 132, 66));
        lblMontoR2.setMaximumSize(new Dimension(0, 0));
        lblMontoR2.setMinimumSize(new Dimension(0, 0));
        lblMontoR2.setPreferredSize(new Dimension(0, 0));
        lblTelefono.setBounds(new Rectangle(150, 150, 90, 15));
        lblTelefono.setFont(new Font("Dialog", 1, 13));
        lblTelefono.setForeground(new Color(0, 132, 66));
        lblTelefono.setMaximumSize(new Dimension(0, 0));
        lblTelefono.setMinimumSize(new Dimension(0, 0));
        lblTelefono.setPreferredSize(new Dimension(0, 0));
        lblTelefono2.setBounds(new Rectangle(275, 150, 210, 15));
        lblTelefono2.setFont(new Font("Dialog", 1, 13));
        lblTelefono2.setForeground(new Color(0, 132, 66));
        lblTelefono2.setMaximumSize(new Dimension(0, 0));
        lblTelefono2.setMinimumSize(new Dimension(0, 0));
        lblTelefono2.setPreferredSize(new Dimension(0, 0));
        lblProveedor.setBounds(new Rectangle(150, 120, 90, 15));
        lblProveedor.setFont(new Font("Dialog", 1, 13));
        lblProveedor.setForeground(new Color(0, 132, 66));
        lblProveedor.setMaximumSize(new Dimension(0, 0));
        lblProveedor.setMinimumSize(new Dimension(0, 0));
        lblProveedor.setPreferredSize(new Dimension(0, 0));
        lblProveedor2.setBounds(new Rectangle(275, 120, 160, 15));
        lblProveedor2.setFont(new Font("Dialog", 1, 13));
        lblProveedor2.setForeground(new Color(0, 132, 66));
        lblProveedor2.setMaximumSize(new Dimension(0, 0));
        lblProveedor2.setMinimumSize(new Dimension(0, 0));
        lblProveedor2.setPreferredSize(new Dimension(0, 0));
        lblValorCo.setBounds(new Rectangle(150, 90, 105, 15));
        lblValorCo.setFont(new Font("Dialog", 1, 13));
        lblValorCo.setForeground(new Color(0, 132, 66));
        lblValorCo.setMaximumSize(new Dimension(0, 0));
        lblValorCo.setMinimumSize(new Dimension(0, 0));
        lblValorCo.setPreferredSize(new Dimension(0, 0));
        lblFecha.setBounds(new Rectangle(275, 90, 85, 15));
        lblFecha.setFont(new Font("Dialog", 1, 13));
        lblFecha.setForeground(new Color(0, 132, 66));
        lblFecha.setMaximumSize(new Dimension(0, 0));
        lblFecha.setMinimumSize(new Dimension(0, 0));
        lblFecha.setPreferredSize(new Dimension(0, 0));
        lblCorrelativo.setBounds(new Rectangle(45, 90, 90, 15));
        lblCorrelativo.setFont(new Font("Dialog", 1, 13));
        lblCorrelativo.setForeground(new Color(0, 132, 66));
        lblCorrelativo.setMaximumSize(new Dimension(0, 0));
        lblCorrelativo.setMinimumSize(new Dimension(0, 0));
        lblCorrelativo.setPreferredSize(new Dimension(0, 0));
        lblValorFec.setBounds(new Rectangle(365, 90, 160, 15));
        lblValorFec.setFont(new Font("Dialog", 1, 13));
        lblValorFec.setForeground(new Color(0, 132, 66));
        lblValorFec.setMaximumSize(new Dimension(0, 0));
        lblValorFec.setMinimumSize(new Dimension(0, 0));
        lblValorFec.setPreferredSize(new Dimension(0, 0));
        lblImprimir.setBounds(new Rectangle(320, 275, 105, 20));
        lblImprimir.setText("[ F10 ] Imprimir");
        lblEsc.setBounds(new Rectangle(435, 275, 90, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        pnlFondo.add(lblEsc, null);
        pnlFondo.add(lblImprimir, null);
        pnlFondo.add(lblValorFec, null);
        pnlFondo.add(lblCorrelativo, null);
        pnlFondo.add(lblFecha, null);
        pnlFondo.add(lblValorCo, null);
        pnlFondo.add(lblProveedor2, null);
        pnlFondo.add(lblProveedor, null);
        pnlFondo.add(lblTelefono2, null);
        pnlFondo.add(lblTelefono, null);
        pnlFondo.add(lblMontoR2, null);
        pnlFondo.add(lblMontoR, null);
        pnlFondo.add(lblFechaActV, null);
        pnlFondo.add(lblFechaAct, null);
        pnlFondo.add(jEditorPane1, null);
        pnlFondo.add(jPanel4, null);
        pnlFondo.add(pnlHeader, null);
        pnlHeader.add(btnBuscar, null);
        pnlHeader.add(txtMonto, null);
        pnlHeader.add(lblMonto, null);
        pnlHeader.add(txtCorrelativo, null);
        pnlHeader.add(btnCorrelativo, null);
        this.getContentPane().add(pnlFondo, null);
        txtCorrelativo.setDocument(new FarmaLengthText(10));
    }
    
    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        LimpiarVariables();
        setearObjetoFocus();
    }
    
    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */


    private void btnCorrelativo_actionPerformed(ActionEvent e) {
        if(validarMostrarCorrelativo())
        FarmaUtility.moveFocus(txtCorrelativo);
        else
            FarmaUtility.moveFocus(btnCorrelativo);
    }

    private void txtCorrelativo_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            txtCorrelativo.setText(FarmaUtility.caracterIzquierda(txtCorrelativo.getText(), 
                                                                  10, "0"));
            FarmaUtility.moveFocus(txtMonto);
        } else
            chkKeyPressed(e);
    }

    private void txtCorrelativo_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCorrelativo, e);
    }

    private void txtMonto_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            //FarmaUtility.moveFocus(btnBuscar);
            btnBuscar.doClick();
        } else
            chkKeyPressed(e);
    }

    private void txtMonto_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtMonto, e);
    }

    private void txtMonto_actionPerformed(ActionEvent e) {
        //////////////
        //ejecutarBusqueda();
    }

    private void btnBuscar_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(validarMostrarCorrelativo())
            FarmaUtility.moveFocus(txtCorrelativo);
            
                
        } else
            chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtCorrelativo);
        jEditorPane1.setEditable(false);
        
        if(this.isVisible() && !validarMostrarCorrelativo()){
                    mostrarCorrelativoXComprobante();
                    FarmaUtility.moveFocus(btnCorrelativo);   
        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, 
                                 "Debe presionar la tecla ESC para cerrar la ventana.", 
                                 pObj);
    }
    
    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */
    
    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F11) {
            if (vAceptar) {
               
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
        
        else if (e.getKeyCode() == KeyEvent.VK_F10 ){
            //imprimir ticket
                    imprimirTicket();
                   //cerrarVentana(false);
                    }else if(e.getKeyCode()==KeyEvent.VK_INSERT){
                        FarmaUtility.showMessage(this,"1234567890klñiopklñl12345647890EWQDSAEWQW1234567890KLÑIOPKLÑL1234567890ewqdsaewqr1234567890ewqEWQewqW",null);
                    }
    }
    
    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    
    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */
    
    private boolean validarCampos() {
        boolean retorno = true;
        if (txtCorrelativo.getText().trim().equals("")) {
            FarmaUtility.showMessage(this, "Debe ingresar el Correlativo.", 
                                     txtCorrelativo);
            retorno = false;
        } else if (txtMonto.getText().trim().equals("")) {
            FarmaUtility.showMessage(this, "Debe ingresar el Monto.", 
                                     txtMonto);
            retorno = false;
        } else if (!FarmaUtility.validateDecimal(this, txtMonto, 
                                                 "Debe ingresar un Monto Válido.", 
                                                 false)) {
            retorno = false;
        }
        return retorno;
    }

    private void buscarRecarga() throws SQLException {
       
          ArrayList infoRecarga = new ArrayList();
          infoRecarga = DBVentas.verificaRecargaPedido(txtCorrelativo.getText().trim(),txtMonto.getText().trim());
          String mensaje = "" , proveedor = "", telefono = "", monto = "" ,descripcion = "" ,fecha="",fecha_actual="";

       if (infoRecarga.size() > 0){

             for (int i = 0; i < infoRecarga.size(); i++) {
                     System.err.println("INFORtamaño-----"+i+": "+ infoRecarga.size());
                     System.err.println("INFORecarga-----"+i+": "+ infoRecarga.get(i));
                        proveedor  = ((String) ((ArrayList) infoRecarga.get(i)).get(0)).trim();
                        descripcion  =  ((String) ((ArrayList) infoRecarga.get(i)).get(1)).trim();
                        telefono    = ((String) ((ArrayList) infoRecarga.get(i)).get(2)).trim();
                        monto    = ((String) ((ArrayList) infoRecarga.get(i)).get(3)).trim();
                        fecha  = ((String) ((ArrayList) infoRecarga.get(i)).get(4)).trim();
                  fecha_actual  = ((String) ((ArrayList) infoRecarga.get(i)).get(5)).trim();
                 
                 
              }
                
                  if(VariablesVirtual.vConProductoVirtual){
                    System.err.println("VariablesCaja.vIndLineaADMCentral : " + 
                                       VariablesCaja.vIndLineaADMCentral);
                    log.debug("VariablesCaja.vIndLineaADMCentral : " + 
                              VariablesCaja.vIndLineaADMCentral);
                    //Validando conexion con ADM
                    if (VariablesCaja.vIndLineaADMCentral.equals(FarmaConstants.INDICADOR_S)) {
    
                        //CARGANDO PARAMETROS
                        VariablesCaja.vNumeroCelular = telefono.trim();
                        VariablesCaja.vNumPedVta_Anul = 
                                txtCorrelativo.getText().trim();
    
                        VariablesCaja.vRespuestaExito = 
                                obtieneRespuestaRecarga().trim();
                        System.err.println("VariablesCaja.vRespuestaExito :" + 
                                           VariablesCaja.vRespuestaExito);
                        //VALIDANDO RESPUESTA SI EL NUMERO TELEFONICO EXISTE EN ADMCENTRAL
    
                        if (VariablesCaja.vRespuestaExito.trim().equals(FarmaConstants.INDICADOR_N)) {
                            LimpiarVariables();
                            FarmaUtility.showMessage(this, 
                                                     "No se pudo obtener la respuesta de la Recarga", 
                                                    // txtMonto);
                                                     pObj);
                            VariablesCaja.vRespRecargaPmtros = false;
                        } else {
                            /*******************************************************************************************************************/
                                                         if (VariablesCaja.vRespuestaExito.trim().equals("-2")) {
                                                            LimpiarVariables();                                                            
                                                            VariablesCaja.vRespRecargaPmtros = false;
                                                        } else {
                                                            if (VariablesCaja.vRespuestaExito.trim().equals("00")) {
                                                                lblProveedor.setText("Proveedor");
                                                                lblProveedor2.setText(proveedor);
                                                                lblTelefono.setText("Teléfono");
                                                                lblTelefono2.setText(telefono);
                                                                lblMontoR.setText("Monto S/.");
                                                                lblMontoR2.setText(monto);
                                                                lblCorrelativo.setText("Correlativo");
                                                                lblValorCo.setText(txtCorrelativo.getText().trim());
                                                                lblFecha.setText("Fecha Ped.");
                                                                lblValorFec.setText(fecha);
                                                                lblFechaAct.setText("Fecha Actual");
                                                                lblFechaActV.setText(fecha_actual);
                                                                VariablesCaja.vRespRecargaPmtros = true;
                                                            }
                                                            else {
                                                                if (VariablesCaja.vRespuestaExito.trim().equals("0") || 
                                                                    VariablesCaja.vRespuestaExito.trim().equals("-1")) {
                                                                    LimpiarVariables();
                                                                    VariablesCaja.vRespRecargaPmtros = false;
                                                                } else {
                                                                    lblProveedor.setText("Proveedor");
                                                                    lblProveedor2.setText(proveedor);
                                                                    lblTelefono.setText("Teléfono");
                                                                    lblTelefono2.setText(telefono);
                                                                    lblMontoR.setText("Monto S/.");
                                                                    lblMontoR2.setText(monto);
                                                                    lblCorrelativo.setText("Correlativo");
                                                                    lblValorCo.setText(txtCorrelativo.getText().trim());
                                                                    lblFecha.setText("Fecha Ped.");
                                                                    lblValorFec.setText(fecha);
                                                                    lblFechaAct.setText("Fecha Actual");
                                                                    lblFechaActV.setText(fecha_actual);
                                                                    VariablesCaja.vRespRecargaPmtros = true;
                                                                }
                                                            }
                                                        }
                            /******************************************************************************************************************/
                            
                            //INI ASOSA, 19.04.2010
                            String indlabel=DBVentas.getIND_LABEL(VariablesCaja.vRespuestaExito);
                            String msg=DBVentas.getMensajeRptaRecarga(VariablesCaja.vRespuestaExito);
                            if(indlabel.equalsIgnoreCase("S")){
                                jEditorPane1.setContentType("text/html");
                                jEditorPane1.setText(msg);
                                double cantLineas=((double)obtenerLargoSoloMsg(msg))/64;
                                int cantLineasInt=(int)cantLineas+1;
                                System.out.println("length msg: "+msg.length());
                                System.out.println("CANTIDAD DE LINEAS float: "+cantLineas);
                                System.out.println("CANTIDAD DE LINEAS int: "+cantLineasInt);
                                int alto=24*cantLineasInt;
                                pnlFondo.setSize(555,600);
                                jEditorPane1.setBounds(new Rectangle(20, 235, 510, alto));
                                if(cantLineasInt>1){
                                    int cantidadMas=cantLineasInt-1;
                                    int cantidadX=cantidadMas*20;                                    
                                    //pnlMensaje.setBounds(new Rectangle(10, 55, 530, 280+cantidadX));                                    
                                    //pnlFondo.setBounds(new Rectangle(0, 0, 555, 305+cantidadX));
                                    //pnlFondo.setSize(555,600+cantidadX);
                                    //pnlFondo.setBounds(new Rectangle(0, 0, 555, (600+cantidadX)));
                                    lblImprimir.setBounds(new Rectangle(320, 275+cantidadX, 105, 20));
                                    lblEsc.setBounds(new Rectangle(435, 275+cantidadX, 90, 20));
                                   
                                    
                                    
                                    /*jContentPane.setLayout(null);
                                    jContentPane.setSize(new Dimension(551, 384+cantidadX));
                                    jContentPane.setBackground(Color.white);
                                    jContentPane.setActionMap(new ActionMap());
                                    jContentPane.setBounds(new Rectangle(0, 0, 551, 384+cantidadX));
                                    jContentPane.setEnabled(true);
                                    jContentPane.repaint(10, 10, 551, 384+cantidadX);
                                    jContentPane.repaint();*/
                                    pnlFondo.setSize(555,310+cantidadX);
                                    this.setSize(new Dimension(556, 330+cantidadX));
                                    //pnlFondo.setBounds(new Rectangle(0, 0, 555, (600+cantidadX)));
                                    pnlFondo.repaint();
                                }
                                
                            }else if(indlabel.equalsIgnoreCase("N")){
                                //FarmaUtility.showMessage(this,msg.replaceAll("ººº","\n"),txtCorrelativo);
                                FarmaUtility.showMessage(this,msg.replaceAll("ººº","\n"),pObj);
                            }
                            //FIN ASOSA, 19.04.2010
                        }     
                        log.debug(" indExitoRecarga : " + 
                                  VariablesCaja.vRespuestaExito);
                        VariablesCaja.vIndPedidoRecargaVirtual = "";

                    }
                    //No hay conexion com ADMCENTRAL
                    else {
                        FarmaUtility.showMessage(this, 
                                                 "No puede realizar la consulta porque la conexión esta lenta con Matriz", 
                                                 //txtCorrelativo);
                                                 pObj);
                        LimpiarVariables();
                        
                        VariablesCaja.vRespRecargaPmtros = false;
                    }
                  }
               
              }
       else{
               
               //FarmaUtility.showMessage(this, "No existe el Pedido.Verifique!", txtCorrelativo);
               FarmaUtility.showMessage(this, "No existe el Pedido.Verifique!", pObj);
               LimpiarVariables();
               VariablesCaja.vRespRecargaPmtros = false;
           }
        
       //FarmaUtility.showMessage(this,"hola: "+pnlFondo.getBounds(),null);    
    }
    
    private int obtenerLargoSoloMsg(String msg){
        int ini=msg.indexOf("<font face=Arial color=red>")+"<font face=Arial color=red>".length();
        int fin=msg.indexOf("</font>");
        String neomsg=msg.substring(ini,fin);
        System.out.println("MENSAJE: "+msg.substring(ini,fin));
        return neomsg.length();
    }
    

    private void evaluaPedidoProdVirtual(String pNumPedido)
    {
      int cantProdVirtualesPed = 0;
      cantProdVirtualesPed = cantidadProductosVirtualesPedido(pNumPedido);
      if( cantProdVirtualesPed <= 0 )
      {
            VariablesVirtual.vConProductoVirtual = false;
        
      } else
      {
            VariablesVirtual.vConProductoVirtual = true;
        
      }
      System.err.println("asolis: VariablesVirtual.vConProductoVirtual :" + VariablesVirtual.vConProductoVirtual);
    }
    
    private int cantidadProductosVirtualesPedido(String pNumPedido)
    {
      int cant = 0;
      try
      {
        cant = DBCaja.obtieneCantProdVirtualesPedido(pNumPedido);
      } catch(SQLException ex)
      {
        ex.printStackTrace();
        cant = 0;
        FarmaUtility.showMessage(this,"Error al obtener cantidad de productos virtuales.\n" + ex.getMessage(), pObj);
      }
      return cant;
    }

    /**
     * Metodo que sirve para validar que existe conexion en admCentral
     * @param pCadena
     * @param pParent
     */
    public void validarConexionADMCentral(){            
        
        VariablesCaja.vIndLineaADMCentral = FarmaUtility.getIndLineaOnLine(
                                     FarmaConstants.CONECTION_ADMCENTRAL,
                                     FarmaConstants.INDICADOR_N);
                                        
        if(VariablesCaja.vIndLineaADMCentral.trim().
                        equalsIgnoreCase(FarmaConstants.INDICADOR_N)){
           //System.out.println("No existe linea se cerrara la conexion en ADMCentral ...");
           System.err.println("***cerrar toda conexion remota ... " +
                        "debido a que no encontro linea con admcentral");
           FarmaConnectionRemoto.closeConnection();
           //FarmaUtility.showMessage(this,"No hay conexión con Matriz.\nInténtelo en unos minutos.", null);
        }
    }
    

    public String obtieneRespuestaRecarga(){
        String retorno = FarmaConstants.INDICADOR_N;
        try{
            retorno = DBCaja.obtCodigoRptaProdVirtual();
        }catch(SQLException e){
            e.printStackTrace();
        }        
        return retorno;
    }

    private void LimpiarVariables(){
            lblProveedor.setText(""); 
            lblProveedor2.setText("");
            lblTelefono.setText("");
            lblTelefono2.setText("");
            lblMontoR.setText("");
            lblMontoR2.setText("");   
            lblCorrelativo.setText("");
            lblValorCo.setText("");
            lblFecha.setText("");
            lblValorFec.setText("");
            lblFechaAct.setText("");
            lblFechaActV.setText("");
            jEditorPane1.setText("");
        }

    private void imprimirTicket() {
        
        
      if (VariablesCaja.vRespRecargaPmtros == true) {
           
             int monto = Integer.parseInt(lblMontoR2.getText());
             String nroVentaPedido    = lblValorCo.getText();
            
        
         
          UtilityCaja.imprimeTicket(this,nroVentaPedido,monto);
               
         }
       
       else
        FarmaUtility.showMessage(this, 
                                 "No existen datos para realizar la impresión", 
                                 pObj);
            
        
    }

    /**
     * Valida si se muestra la nueva versión para Imprimir o 
     * no Imprimir Correlativo, así como usar pantalla para Ingresar Numero Comprobante y Monto Neto si es Negativo
     * @author JMIRANDA
     * @since 22.08.2011
     * @return true si imprime correlativo
     */
    private boolean validarMostrarCorrelativo(){
        boolean flag = true;
        //si la validacion siguiente es falsa no imprime y debe ingresar nro comprobante
        if(!UtilityVentas.getIndImprimeCorrelativo()){
            txtCorrelativo.enable(false);
            txtMonto.enable(false);
            flag = false;
        }
        return flag;
    }
    
    private void mostrarCorrelativoXComprobante(){
        DlgConsultaXCorrelativo dlgConsulta = new DlgConsultaXCorrelativo(myParentFrame,"",true);
        dlgConsulta.setVisible(true);
        
        if(FarmaVariables.vAceptar){
            txtCorrelativo.setText(VariablesVentas.vNumPedVta_new);
            txtMonto.setText(VariablesVentas.vMontoNeto_new);
            btnBuscar.doClick();
            FarmaUtility.moveFocus(btnCorrelativo);
        }else
            FarmaUtility.moveFocus(btnCorrelativo);
    }

    private void btnCorrelativo_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        ejecutarBusqueda();
    }
    
    private void ejecutarBusqueda(){
        FarmaConnectionRemoto.closeConnection(); //JCHAVEZ 28092009 para asegurar el cierre de conexion a adm_central
        if(validarCampos())
        {
            //Agregado por DVELIZ 05.01.2009
            VariablesCaja.vIndLineaADMCentral = "N";//indicador de linea en N
            evaluaPedidoProdVirtual(txtCorrelativo.getText().trim());//verifica si es un pedido virtual
            if(VariablesVirtual.vConProductoVirtual){
                validarConexionADMCentral();//VariablesCaja.vIndLineaADMCentral
            }
            else
            {    FarmaUtility.showMessage(this,"Este pedido no corresponde a un producto virtual",pObj); //JCHAVEZ 28092009
            }
            log.debug("asolis: antes de buscar pedido VariablesCaja.vIndLineaADMCentral:"+VariablesCaja.vIndLineaADMCentral);
            try 
            {
                this.setVisible(false);
                buscarRecarga();
                this.setVisible(true);
            } 
           catch(SQLException ex){
                ex.printStackTrace();
                FarmaUtility.showMessage(this,"Ocurrió un error al buscar el pedido  - \n" +ex.getMessage(),pObj);
            }
            catch(Exception x){
                 x.printStackTrace();
                 FarmaUtility.showMessage(this,"Ocurrió un error al buscar el pedido  - \n" +x.getMessage(),pObj);
             }
        }
        else {
            LimpiarVariables();
            }
    }
    
    //JMIRANDA 25.08.2011 Setear el Objeto para enfocar después de los mensajes.
    private void setearObjetoFocus(){
        //JMIRANDA 25.08.2011 verificar si se utiliza funcionalidad nueva
               if(validarMostrarCorrelativo())
                   pObj = txtCorrelativo;
               else 
                   pObj = btnCorrelativo;
    }
}
