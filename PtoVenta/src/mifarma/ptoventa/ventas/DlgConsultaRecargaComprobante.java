package mifarma.ptoventa.ventas;

import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;

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
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import mifarma.common.*;
import mifarma.ptoventa.*;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.caja.reference.VariablesVirtual;
import mifarma.ptoventa.ventas.reference.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgConsultaRecargaComprobante extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = 
        LoggerFactory.getLogger(DlgConsultaRecargaComprobante.class);

   
    Frame myParentFrame;
    private boolean vAceptar = false;
    private String numeroPedido = "";
    private String numeroComp = "";
    private String tipo = "";
    ButtonGroup grupo = new ButtonGroup();

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JPanel jPanel3 = new JPanel();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JPanelHeader pnlHeader = new JPanelHeader();
    private JTextField txtMonto = new JTextField();
    private JLabel jLabel4 = new JLabel();
    private JButton btnBuscar = new JButton();
    private JTextField txtNroComprobante = new JTextField();
    private JButton btnNroComprobante = new JButton();
    private JPanel jPanel1 = new JPanel();
    private JRadioButton rbtFactura = new JRadioButton();
    private JRadioButton rbtBoleta = new JRadioButton();
    private JComboBox cmbSerie = new JComboBox();
    private JLabel lblMensaje2 = new JLabel();
    private JLabel lblProveedor = new JLabel();
    private JLabel lblProveedor2 = new JLabel();
    private JLabel lblTelefono = new JLabel();
    private JLabel lblTelefono2 = new JLabel();
    private JLabel lblMontoR = new JLabel();
    private JLabel lblMontoR2 = new JLabel();
    private JLabel lblFechaAct = new JLabel();
    private JLabel lblFechaActV = new JLabel();
    private JLabel lblFecha = new JLabel();
    private JLabel lblValorFec = new JLabel();
    private JLabel lblComunicado = new JLabel();
    private JPanelHeader pnlMensaje = new JPanelHeader();
    private JLabel lblNumero = new JLabel();
    private JLabel lblNumeroValor = new JLabel();


    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgConsultaRecargaComprobante() {
        this(null, "", false);
    }

    public DlgConsultaRecargaComprobante(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("",e);
        }

    }

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(641, 453));
        this.getContentPane().setLayout(borderLayout1);
            this.setTitle("Consulta de  Recargas de Comprobantes");
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
        jContentPane.setSize(new Dimension(516, 354));
        jContentPane.setBackground(Color.white);
        jPanel3.setBounds(new Rectangle(15, 100, 605, 20));
        jPanel3.setBackground(new Color(255, 130, 14));
        jPanel3.setLayout(null);
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(535, 390, 85, 20));
        pnlHeader.setBounds(new Rectangle(15, 55, 605, 35));
        txtMonto.setBounds(new Rectangle(355, 5, 80, 25));
        txtMonto.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtMonto_keyPressed(e);
                    }

                    public void keyTyped(KeyEvent e) {
                        txtMonto_keyTyped(e);
                    }
                });
        jLabel4.setText("Monto :");
        jLabel4.setBounds(new Rectangle(300, 5, 55, 25));
        jLabel4.setFont(new Font("SansSerif", 1, 11));
        jLabel4.setForeground(Color.white);
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(445, 5, 90, 25));
        btnBuscar.setMnemonic('s');
        btnBuscar.setRequestFocusEnabled(false);
        btnBuscar.setDefaultCapable(false);
        btnBuscar.setFocusPainted(false);
        btnBuscar.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        btnBuscar_keyPressed(e);
                    }
                });
        btnBuscar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e)  {
                        btnBuscar_actionPerformed(e);
                    }
                });
        txtNroComprobante.setBounds(new Rectangle(215, 5, 70, 25));
        txtNroComprobante.setFont(new Font("SansSerif", 0, 12));
        txtNroComprobante.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtNroComprobante_keyPressed(e);
                    }

                    public void keyTyped(KeyEvent e) {
                        txtNroComprobante_keyTyped(e);
                    }
                });
        btnNroComprobante.setText("Nro. Comprobante :");
        btnNroComprobante.setBounds(new Rectangle(10, 10, 115, 15));
        btnNroComprobante.setMnemonic('n');
        btnNroComprobante.setBackground(new Color(255, 130, 14));
        btnNroComprobante.setBorderPainted(false);
        btnNroComprobante.setContentAreaFilled(false);
        btnNroComprobante.setDefaultCapable(false);
        btnNroComprobante.setFocusPainted(false);
        btnNroComprobante.setForeground(Color.white);
        btnNroComprobante.setHorizontalAlignment(SwingConstants.LEFT);
        btnNroComprobante.setFont(new Font("SansSerif", 1, 11));
        btnNroComprobante.setRequestFocusEnabled(false);
        btnNroComprobante.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 
                                                                    0));
        btnNroComprobante.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnNroComprobante_actionPerformed(e);
                    }
                });
        
        pnlMensaje.setBackground(Color.white);
        pnlMensaje.setBorder(BorderFactory.createTitledBorder(""));

        pnlMensaje.setBounds(new Rectangle(15, 120, 605, 255));
        //lblNumero.setText("lblNumero");
        lblNumero.setText("");
        lblNumero.setBounds(new Rectangle(25, 55, 85, 15));
        lblNumero.setForeground(new Color(43, 141, 29));
        lblNumero.setFont(new Font("Dialog", 1, 13));
        lblNumeroValor.setText("");
        //lblNumeroValor.setText("jLabel2");
        lblNumeroValor.setBounds(new Rectangle(165, 55, 105, 15));
        lblNumeroValor.setFont(new Font("Dialog", 1, 13));
        lblNumeroValor.setForeground(new Color(43, 141, 29));
        lblMensaje2.setHorizontalAlignment(SwingConstants.CENTER);
        //lblMensaje2.setText("lblMensaje2");
        lblMensaje2.setText("");
        lblMensaje2.setFont(new Font("SansSerif", 1, 14));
        lblMensaje2.setForeground(new Color(43, 141, 39));
        lblMensaje2.setBounds(new Rectangle(5, 15, 585, 20));


        lblTelefono.setForeground(new Color(43, 141, 39));
        lblTelefono.setText("");
        //lblTelefono.setText("lblTelefono");
        lblTelefono.setBounds(new Rectangle(25, 105, 90, 15));
        lblTelefono.setFont(new Font("SansSerif", 1, 13));
        
        lblTelefono2.setForeground(new Color(43, 141, 39));
        lblTelefono2.setText("");
        //lblTelefono2.setText("lblTelefono2");
        lblTelefono2.setBounds(new Rectangle(165, 105, 210, 15));
        lblTelefono2.setFont(new Font("SansSerif", 1, 13));
        
        lblMontoR.setText("");
        //lblMontoR.setText("lblMontoR");
        lblMontoR.setForeground(new Color(43, 141, 39));
        lblMontoR.setBounds(new Rectangle(25, 130, 90, 15));
        lblMontoR.setFont(new Font("SansSerif", 1, 13));
        lblMontoR.setBackground(new Color(0, 132, 66));
        
        lblMontoR2.setText("");
        //lblMontoR2.setText("lblMontoR2");
        lblMontoR2.setForeground(new Color(43, 141, 39));
        lblMontoR2.setBounds(new Rectangle(165, 130, 120, 15));
        lblMontoR2.setFont(new Font("SansSerif", 1, 13));
        lblMontoR2.setBackground(new Color(0, 132, 66));
        
        lblFechaAct.setText("");
        //lblFechaAct.setText("lblFechaAct");
        lblFechaAct.setBounds(new Rectangle(25, 180, 95, 15));
        lblFechaAct.setFont(new Font("Dialog", 1, 13));
        lblFechaAct.setForeground(new Color(0, 132, 66));
        //lblFechaActV.setText("lblFechaActV");
        lblFechaActV.setText("");
        lblFechaActV.setBounds(new Rectangle(165, 180, 165, 15));
        lblFechaActV.setForeground(new Color(0, 132, 66));
        lblFechaActV.setFont(new Font("Dialog", 1, 13));
        lblFechaActV.setBackground(new Color(0, 132, 66));
        
        //lblFecha.setText("lblFecha");
        lblFecha.setText("");
        lblFecha.setBounds(new Rectangle(25, 155, 95, 15));
        lblFecha.setFont(new Font("Dialog", 1, 13));
        lblFecha.setForeground(new Color(0, 132, 66));
        lblValorFec.setText("");
       // lblValorFec.setText("lblValorFec");
        lblValorFec.setBounds(new Rectangle(165, 155, 125, 15));
        lblValorFec.setForeground(new Color(0, 132, 66));
        lblValorFec.setFont(new Font("Dialog", 1, 13));
       // lblComunicado.setText("lblComunicado");
        lblComunicado.setText("");
        lblComunicado.setHorizontalAlignment(SwingConstants.CENTER);
        lblComunicado.setBounds(new Rectangle(10, 215, 585, 20));
        lblComunicado.setFont(new Font("Dialog", 1, 13));
        lblComunicado.setForeground(Color.red);
        
       // lblProveedor.setText("lblProveedor");
        lblProveedor.setText("");
        lblProveedor.setForeground(new Color(43, 141, 39));
        lblProveedor.setBounds(new Rectangle(25, 80, 90, 15));
        lblProveedor.setFont(new Font("Dialog", 1, 13));

       // lblProveedor2.setText("lblProveedor2");
        lblProveedor2.setText("");
        lblProveedor2.setForeground(new Color(43, 141, 39));
        lblProveedor2.setBounds(new Rectangle(165, 80, 90, 15));
        lblProveedor2.setFont(new Font("Dialog", 1, 13));

        pnlHeader.add(txtMonto, null);
        
        jPanel1.setBounds(new Rectangle(15, 15, 605, 35));
        jPanel1.setBorder(BorderFactory.createTitledBorder(""));
        jPanel1.setBackground(Color.white);
        jPanel1.setLayout(null);
        rbtFactura.setText("FACTURA");
        rbtFactura.setBounds(new Rectangle(280, 5, 115, 25));
        rbtFactura.setBackground(Color.white);
        rbtFactura.setFont(new Font("SansSerif", 1, 14));
        rbtFactura.setForeground(new Color(43, 141, 39));
        rbtFactura.setFocusPainted(false);
        rbtFactura.setRequestFocusEnabled(false);
        rbtFactura.setMnemonic('f');
        rbtFactura.addChangeListener(new ChangeListener() {
                    public void stateChanged(ChangeEvent e) {
                        rbtFactura_stateChanged(e);
                    }
                });
        rbtFactura.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        rbtBoleta_keyPressed(e);
                    }
                });
        rbtBoleta.setText("BOLETA");
        rbtBoleta.setBounds(new Rectangle(155, 5, 95, 25));
        rbtBoleta.setBackground(Color.white);
        rbtBoleta.setFont(new Font("SansSerif", 1, 14));
        rbtBoleta.setForeground(new Color(43, 141, 39));
        rbtBoleta.setFocusPainted(false);
        rbtBoleta.setRequestFocusEnabled(false);
        rbtBoleta.setMnemonic('b');
        rbtBoleta.addChangeListener(new ChangeListener() {
                    public void stateChanged(ChangeEvent e) {
                        rbtBoleta_stateChanged(e);
                    }
                });
        rbtBoleta.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        rbtBoleta_keyPressed(e);
                    }
                });
        cmbSerie.setBounds(new Rectangle(125, 5, 85, 25));
        cmbSerie.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        cmbSerie_keyPressed(e);
                    }
                });
        grupo.add(rbtBoleta);
        grupo.add(rbtFactura);

        pnlHeader.add(cmbSerie, null);
        pnlHeader.add(txtMonto, null);
        pnlHeader.add(jLabel4, null);
        pnlHeader.add(btnBuscar, null);
        pnlHeader.add(txtNroComprobante, null);
        pnlHeader.add(btnNroComprobante, null);
        jPanel1.add(rbtFactura, null);
        jPanel1.add(rbtBoleta, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        pnlMensaje.add(lblNumeroValor, null);
        pnlMensaje.add(lblNumero, null);
        pnlMensaje.add(lblComunicado, null);
        pnlMensaje.add(lblProveedor, null);
        pnlMensaje.add(lblProveedor2, null);
        pnlMensaje.add(lblTelefono, null);
        pnlMensaje.add(lblTelefono2, null);
        pnlMensaje.add(lblMontoR, null);
        pnlMensaje.add(lblMontoR2, null);
        pnlMensaje.add(lblFechaAct, null);
        pnlMensaje.add(lblFechaActV, null);
        pnlMensaje.add(lblFecha, null);
        pnlMensaje.add(lblValorFec, null);
        pnlMensaje.add(lblMensaje2, null);
        jContentPane.add(pnlMensaje, null);
        jContentPane.add(jPanel3, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(pnlHeader, null);
        jContentPane.add(jPanel1, null);
        //

        txtNroComprobante.setDocument(new FarmaLengthText(7));
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        VariablesCaja.vIndAnulacionConReclamoNavsat = false;
        LimpiarVariables();
        rbtBoleta.doClick();
    }



    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void rbtBoleta_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(cmbSerie);
    }

    private void btnNroComprobante_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbSerie);
    }

    private void txtNroComprobante_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            txtNroComprobante.setText(FarmaUtility.caracterIzquierda(txtNroComprobante.getText(), 
                                                                     7, "0"));
            FarmaUtility.moveFocus(txtMonto);
        } else
            chkKeyPressed(e);
    }

    private void txtNroComprobante_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtNroComprobante, e);
    }

    private void txtMonto_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            btnBuscar.doClick();
        } else
            chkKeyPressed(e);
    }

    private void txtMonto_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtMonto, e);
    }


    private void btnBuscar_actionPerformed(ActionEvent e) {
        
           if(validarCampos()){
               validarConexionADMCentral();
              log.info("buscarRecarga :");
                buscarRecarga();
           
        }
           else 
           {
               LimpiarVariables();
           }
    }  
    private void buscarRecarga(){
        ArrayList infoRecarga = new ArrayList();
        numeroComp = FarmaLoadCVL.getCVLCode("cmbSerie",cmbSerie.getSelectedIndex())+txtNroComprobante.getText().trim();
        tipo = (rbtBoleta.isSelected()) ? ConstantsVentas.TIPO_COMP_BOLETA: ConstantsVentas.TIPO_COMP_FACTURA;
       
        
        try{   
               numeroPedido = DBVentas.getNumeroPedidoRecargaComprobante(tipo,numeroComp,txtMonto.getText().trim());
                infoRecarga = DBVentas.verificaRecargaComprobante(tipo,numeroComp,txtMonto.getText().trim());
                log.info("numeroPedido :" + numeroPedido);
                
           String mensaje = "" , proveedor = "", telefono = "", monto = "" ,descripcion = "",nrocomprobante = "",fechavta = "", fechahoy ="";

             
            
                if (infoRecarga.size() > 0){

                   for (int i = 0; i < infoRecarga.size(); i++) {
                           log.info("INFORtamaño-----"+i+": "+ infoRecarga.size());
                           log.info("INFORecarga-----"+i+": "+ infoRecarga.get(i));
                       
                       /*
                        * PROVEEDOR
                          NOMBRE PRODUCTO
                          TELEFONO
                          MONTO 
                          NUMERO COMPROBANTE PAGO
                          FECHA DE VENTA
                          FECHA DE HOY
                         * */
                       
                              proveedor  = ((String) ((ArrayList) infoRecarga.get(i)).get(0)).trim();
                              descripcion  =  ((String) ((ArrayList) infoRecarga.get(i)).get(1)).trim();
                              telefono    = ((String) ((ArrayList) infoRecarga.get(i)).get(2)).trim();
                              monto    = ((String) ((ArrayList) infoRecarga.get(i)).get(3)).trim();
                        nrocomprobante = ((String) ((ArrayList) infoRecarga.get(i)).get(4)).trim();
                              fechavta = ((String) ((ArrayList) infoRecarga.get(i)).get(5)).trim(); 
                              fechahoy = ((String) ((ArrayList) infoRecarga.get(i)).get(6)).trim();

                                     
                        
                    }
                   
                
                 
                 
            if(VariablesCaja.vIndLineaADMCentral.equals(FarmaConstants.INDICADOR_S)){
                   log.info("HAY CONEXION A ADMCENTRAL");  
                   log.info("telefono :" +  telefono );
                   log.info("numeroPedido :" +  numeroPedido);
                                                             
                    //CARGANDO PARAMETROS
                     VariablesCaja.vNumeroCelular = telefono.trim();
                     VariablesCaja.vNumPedVta_Anul = numeroPedido;
                     
                     VariablesCaja.vRespuestaExito = obtieneRespuestaRecarga().trim(); 
                     log.info("VariablesCaja.vRespuestaExito :" + VariablesCaja.vRespuestaExito);
                     //VALIDANDO RESPUESTA SI EL NUMERO TELEFONICO EXISTE EN ADMCENTRAL
                     
                     if(VariablesCaja.vRespuestaExito.trim().equals(FarmaConstants.INDICADOR_N))
                     {
                         LimpiarVariables();
                         FarmaUtility.showMessage(this, "No se pudo obtener la respuesta de la Recarga", txtMonto);                         
                     }
                     else{
                         if(VariablesCaja.vRespuestaExito.trim().equals("-2"))
                         {
                             LimpiarVariables();
                             FarmaUtility.showMessage(this, "No hay respuesta de la Recarga.\n"+
                                                            "Inténtelo nuevamente.", txtMonto);
                         }
                         else{
                               if(VariablesCaja.vRespuestaExito.trim().equals("00")){            
                                     
                                     lblMensaje2.setText(" Mensaje : Recarga Exitosa  ");
                                     lblMensaje2.setForeground(new Color(43, 141, 39));
                                     lblNumero.setText("Num Pdta");
                                     lblNumeroValor.setText(numeroPedido);
                                     lblProveedor.setText("Proveedor"); 
                                     lblProveedor2.setText(proveedor);
                                     lblTelefono.setText("Teléfono");
                                     lblTelefono2.setText(telefono);
                                     lblMontoR.setText("Monto S/.");
                                     lblMontoR2.setText(monto);   
                                     lblFecha.setText("Fecha Ped.");
                                     lblValorFec.setText(fechavta);
                                     lblFechaAct.setText("Fecha Actual");
                                     lblFechaActV.setText(fechahoy);
                                     lblComunicado.setText("Le recordamos que tiene sólo 5 min. para anular pedido desde su generación.");
                                 }
                                 else{
                                     if (VariablesCaja.vRespuestaExito.trim().equals("0") || VariablesCaja.vRespuestaExito.trim().equals("-1")){     
                                                            LimpiarVariables();
                                                            FarmaUtility.showMessage(this, "Recarga no existe en Matriz", txtMonto);
                                                        }
                                                        
                                                        else  {
                                                            
                                                            
                                                            //mostrarMensajeError
                                                            //MOSTRAR MENSAJE DE ERROR 
                                                            String mensajeError="";
                                                             mensajeError = DBVentas.mostrarMensajeError(VariablesCaja.vRespuestaExito);
                                                           
                                                            lblMensaje2.setForeground(Color.red);
                                                            lblMensaje2.setText("Mensaje : " + "["+ VariablesCaja.vRespuestaExito.trim() +"]" + " - " + mensajeError.trim());
                                                            lblNumero.setText("Num Pdta");
                                                            lblNumeroValor.setText(numeroPedido);
                                                            lblProveedor.setText("Proveedor"); 
                                                            lblProveedor2.setText(proveedor);
                                                            lblTelefono.setText("Teléfono");
                                                            lblTelefono2.setText(telefono);
                                                            lblMontoR.setText("Monto S/.");
                                                            lblMontoR2.setText(monto);
                                                            lblFecha.setText("Fecha Ped.");
                                                            lblValorFec.setText(fechavta);
                                                            lblFechaAct.setText("Fecha Actual");
                                                            lblFechaActV.setText(fechahoy);

                                                        }                        
                                 }                         
                             }                             
                         }
                     }
                     
                //}
                    //No hay conexion com ADMCENTRAL
                    else {
                         FarmaUtility.showMessage(this, "No existe línea de conexión con ADMCentral.No se puede mostrar la consulta", txtMonto);
                          LimpiarVariables();
                         }
                     
                    
                }    
                else {
                    
                        FarmaUtility.showMessage(this, "No existe el Pedido.Verifique!", txtMonto);
                        LimpiarVariables();
                    }
            }
        catch(SQLException ex)
            {
                log.error("",ex);
                FarmaUtility.showMessage(this,"No es posible realizar la operación.",txtMonto);
            }
            
         
    }

 
           
        
            
            
    private void btnBuscar_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNroComprobante);
        } else
            chkKeyPressed(e);
    }

    private void btnCabecera_actionPerformed(ActionEvent e) {
       // FarmaUtility.moveFocus(btnCabecera);
    }

    private void btnCabecera_keyPressed(KeyEvent e) {
        //FarmaGridUtils.aceptarTeclaPresionada(e, tblCabeceraPedido, null, 0);

        chkKeyPressed(e);
    }

    private void btnDetalle_actionPerformed(ActionEvent e) {
        //FarmaUtility.moveFocus(btnDetalle);
    }

    private void btnDetalle_keyPressed(KeyEvent e) {
       // FarmaGridUtils.aceptarTeclaPresionada(e, tblDetallePedido, null, 0);
        chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtNroComprobante);
       // cargaLogin();
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, 
                                 "Debe presionar la tecla ESC para cerrar la ventana.", 
                                 null);
    }

    private void rbtBoleta_stateChanged(ChangeEvent e) {
        if (rbtBoleta.isSelected()) {
            cargaCombo(ConstantsVentas.TIPO_COMP_BOLETA);
        }
    }

    private void rbtFactura_stateChanged(ChangeEvent e) {
        if (rbtFactura.isSelected()) {
           cargaCombo(ConstantsVentas.TIPO_COMP_FACTURA);
        }
    }

    private void cmbSerie_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtNroComprobante);
        } else
            chkKeyPressed(e);
    }
    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (vAceptar) {
                
                
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
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


    public void cargaCombo(String doc)
    {
      cmbSerie.removeAllItems();
      {
        ArrayList parametros = new ArrayList(); 
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(doc);
        
        FarmaLoadCVL.loadCVLFromSP(cmbSerie,"cmbSerie","Ptoventa_Recarga.RE_F_GET_SERIE_RE(?,?,?)",parametros, true,true); 
        parametros = null;  
      }
    }
   
    private boolean validarCampos()
    {
     boolean retorno=true;
     if(!rbtBoleta.isSelected() && !rbtFactura.isSelected())
     {
       FarmaUtility.showMessage(this,"Debe seleccionar un tipo de Documento.",txtNroComprobante);
       retorno = false;
     }else if(cmbSerie.getSelectedIndex() < 0)
     {
       FarmaUtility.showMessage(this,"Debe indicar la serie del documento.",cmbSerie);
       retorno = false;
     }else if(txtNroComprobante.getText().trim().equals(""))
     {
       FarmaUtility.showMessage(this,"Debe ingresar el Correlativo.",txtNroComprobante);
       retorno = false;
     }else if(txtMonto.getText().trim().equals(""))
     {
       FarmaUtility.showMessage(this,"Debe ingresar el Monto.",txtMonto);
       retorno = false;
     }else if(!FarmaUtility.validateDecimal(this,txtMonto,"Debe ingresar un Monto Válido.",false))
     {
       retorno = false;
     }
     return retorno;
    }
    
   
    public void validarConexionADMCentral(){
        VariablesCaja.vIndLineaADMCentral = FarmaUtility.getIndLineaOnLine(
                                     FarmaConstants.CONECTION_ADMCENTRAL,
                                     FarmaConstants.INDICADOR_N);
                                        
        if(VariablesCaja.vIndLineaADMCentral.trim().
                        equalsIgnoreCase(FarmaConstants.INDICADOR_N)){
           log.debug("No existe línea ,se cerrará la conexión en ADMCentral ...");
           FarmaConnectionRemoto.closeConnection();
           //VariablesFidelizacion.vIndConexion = "";
           
          // muestraInterfazDatosCliente(pCadena.trim(),pParent);
        }
    }
    
   
    public String obtieneRespuestaRecarga(){
        String retorno = FarmaConstants.INDICADOR_N;
        try{
            retorno = DBCaja.obtCodigoRptaProdVirtual();
        }catch(SQLException e){
            log.error("",e);
        }
        return retorno;
    }
    
    
    private void LimpiarVariables(){
        
            lblMensaje2.setText("");
            lblProveedor.setText(""); 
            lblProveedor2.setText("");
            lblTelefono.setText("");
            lblTelefono2.setText("");
            lblMontoR.setText("");
            lblMontoR2.setText("");   
            lblFecha.setText("");
            lblValorFec.setText("");
            lblFechaAct.setText("");
            lblFechaActV.setText("");
            lblComunicado.setText("");
            lblNumero.setText("");
            lblNumeroValor.setText("");
            
        }
}
