package mifarma.ptoventa.ventas;

import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.*;

import java.util.*;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.*;

import mifarma.ptoventa.*;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.caja.reference.VariablesVirtual;
import mifarma.ptoventa.ventas.reference.*;
import mifarma.ptoventa.reference.*;
 import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import mifarma.ptoventa.caja.reference.UtilityCaja;

import org.slf4j.LoggerFactory;


public class DlgConsultarRecargaCorrelativo extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgConsultarRecargaCorrelativo.class);
   

    
    Frame myParentFrame;
    private boolean vAceptar = false;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JPanel jPanel4 = new JPanel();
    private JPanelHeader pnlHeader = new JPanelHeader();
    private JTextField txtMonto = new JTextField();
    private JLabel lblMonto = new JLabel();
    private JButton btnBuscar = new JButton();
    private JTextField txtCorrelativo = new JTextField();
    private JButton btnCorrelativo = new JButton();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblImprimir = new JLabelFunction();

    private JLabel lblMensaje2 = new JLabel();
    private JLabel lblProveedor = new JLabel();
    private JLabel lblProveedor2 = new JLabel();
    private JLabel lblTelefono = new JLabel();
    private JLabel lblTelefono2 = new JLabel();
    private JLabel lblMontoR = new JLabel();
    private JLabel lblMontoR2 = new JLabel();
    private JLabel lblFechaAct = new JLabel();
    private JLabel lblFechaActV = new JLabel();
    private JLabel lblComunicado = new JLabel();
    private JLabel lblCorrelativo = new JLabel();
    private JLabel lblValorCo = new JLabel();
    private JLabel lblFecha = new JLabel();
    private JLabel lblValorFec = new JLabel();
    private JPanelHeader pnlMensaje = new JPanelHeader();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgConsultarRecargaCorrelativo() {
        this(null, "", false);
    }

    public DlgConsultarRecargaCorrelativo(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(556, 409));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Consulta de Recarga por Correlativo");
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
        jContentPane.setSize(new Dimension(513, 307));
        jContentPane.setBackground(Color.white);
        jPanel4.setBounds(new Rectangle(0, 0, 530, 20));
        jPanel4.setBackground(new Color(255, 130, 14));
        jPanel4.setLayout(null);
        pnlHeader.setBounds(new Rectangle(10, 10, 530, 35));
        txtMonto.setBounds(new Rectangle(295, 5, 80, 25));
        txtMonto.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtMonto_keyPressed(e);
                    }

                    public void keyTyped(KeyEvent e) {
                        txtMonto_keyTyped(e);
                    }
                });
        lblMonto.setText("Monto :");
        lblMonto.setBounds(new Rectangle(240, 5, 55, 25));
        lblMonto.setFont(new Font("SansSerif", 1, 11));
        lblMonto.setForeground(Color.white);
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(405, 5, 90, 25));
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
                    public void actionPerformed(ActionEvent e) {
                        btnBuscar_actionPerformed(e);
                    }
                });
        txtCorrelativo.setBounds(new Rectangle(85, 5, 115, 25));
        txtCorrelativo.setFont(new Font("SansSerif", 0, 12));
        txtCorrelativo.addKeyListener(new KeyAdapter() {

                    public void keyTyped(KeyEvent e) {
                        txtCorrelativo_keyTyped(e);
                    }

                    public void keyPressed(KeyEvent e) {
                        txtCorrelativo_keyPressed(e);
                    }
                });
        btnCorrelativo.setText("Correlativo :");
        btnCorrelativo.setBounds(new Rectangle(10, 10, 70, 15));
        btnCorrelativo.setMnemonic('C');
        btnCorrelativo.setBackground(new Color(255, 130, 14));
        btnCorrelativo.setBorderPainted(false);
        btnCorrelativo.setContentAreaFilled(false);
        btnCorrelativo.setDefaultCapable(false);
        btnCorrelativo.setFocusPainted(false);
        btnCorrelativo.setForeground(Color.white);
        btnCorrelativo.setHorizontalAlignment(SwingConstants.LEFT);
        btnCorrelativo.setFont(new Font("SansSerif", 1, 11));
        btnCorrelativo.setRequestFocusEnabled(false);
        btnCorrelativo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnCorrelativo.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnCorrelativo_actionPerformed(e);
                    }
                });
        
        lblImprimir.setText("[ F10 ] Imprimir");
        lblImprimir.setBounds(new Rectangle(320, 350, 105, 20));
        
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(435, 350, 90, 20));


        pnlMensaje.setBackground(Color.white);
        pnlMensaje.setBorder(BorderFactory.createTitledBorder(""));

        pnlMensaje.setBounds(new Rectangle(10, 55, 530, 280));


        lblMensaje2.setText("");
        //lblMensaje2.setText("lblMensaje2");
        lblMensaje2.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensaje2.setBounds(new Rectangle(0, 25, 530, 25));
        lblMensaje2.setFont(new Font("SansSerif", 1, 14));
        lblMensaje2.setForeground(new Color(43, 141, 39));
        lblMensaje2.setHorizontalTextPosition(SwingConstants.CENTER);
        //lblProveedor.setText("lblProveedor");
        lblProveedor.setText("");
      
        lblProveedor.setForeground(new Color(43, 141, 39));
        lblProveedor.setBounds(new Rectangle(140, 100, 90, 15));
        lblProveedor.setFont(new Font("Dialog", 1, 13));
        lblProveedor2.setText("");
        //lblProveedor2.setText("lblProveedor2");
      
        lblProveedor2.setForeground(new Color(43, 141, 39));
        lblProveedor2.setBounds(new Rectangle(265, 100, 160, 15));
        lblProveedor2.setFont(new Font("Dialog", 1, 13));
       
        lblTelefono.setForeground(new Color(43, 141, 39));
        //lblTelefono.setText("lblTelefono");
        lblTelefono.setBounds(new Rectangle(140, 130, 90, 15));
        lblTelefono.setFont(new Font("Dialog", 1, 13));
        lblTelefono.setText("");
        
        lblTelefono2.setForeground(new Color(43, 141, 39));
        //lblTelefono2.setText("lblTelefono2");
        lblTelefono2.setText("");
        lblTelefono2.setBounds(new Rectangle(265, 130, 210, 15));
        lblTelefono2.setFont(new Font("Dialog", 1, 13));
      
        lblMontoR.setText("");
        //lblMontoR.setText("lblMontoR");
        lblMontoR.setForeground(new Color(43, 141, 39));
        lblMontoR.setBounds(new Rectangle(140, 160, 90, 15));
        lblMontoR.setFont(new Font("Dialog", 1, 13));
        lblMontoR.setBackground(new Color(0, 132, 66));
       
       
        lblMontoR2.setText("");
        //lblMontoR2.setText("lblMontoR2");
        lblMontoR2.setForeground(new Color(43, 141, 39));
        lblMontoR2.setBounds(new Rectangle(265, 160, 120, 15));
        lblMontoR2.setFont(new Font("Dialog", 1, 13));
        lblMontoR2.setBackground(Color.white);
        //lblFechaAct.setText("lblFechaAct");
        lblFechaAct.setText("");
        lblFechaAct.setBounds(new Rectangle(140, 190, 95, 15));
        lblFechaAct.setFont(new Font("Dialog", 1, 13));
        lblFechaAct.setForeground(new Color(0, 132, 66));
       // lblFechaActV.setText("lblFechaActV");
        lblFechaActV.setText("");
        lblFechaActV.setBounds(new Rectangle(265, 190, 165, 15));
        lblFechaActV.setForeground(new Color(0, 132, 66));
        lblFechaActV.setFont(new Font("Dialog", 1, 13));
        lblFechaActV.setBackground(new Color(0, 132, 66));
        //lblComunicado.setText("lblComunicado");
        lblComunicado.setText("");
        lblComunicado.setHorizontalAlignment(SwingConstants.CENTER);
        lblComunicado.setBounds(new Rectangle(0, 230, 530, 20));
        lblComunicado.setFont(new Font("Dialog", 1, 13));
        lblComunicado.setForeground(Color.red);
       // lblCorrelativo.setText("lblCorrelativo");
        lblCorrelativo.setText("");
        lblCorrelativo.setBounds(new Rectangle(35, 70, 90, 15));
        lblCorrelativo.setForeground(new Color(0, 132, 66));
        lblCorrelativo.setFont(new Font("Dialog", 1, 13));
        lblValorCo.setText("");
        //lblValorCo.setText("lblValorCo");
        lblValorCo.setBounds(new Rectangle(140, 70, 105, 15));
        lblValorCo.setFont(new Font("Dialog", 1, 13));
        lblValorCo.setForeground(new Color(0, 132, 66));
        lblFecha.setText("");
        //lblFecha.setText("lblFecha");
        lblFecha.setBounds(new Rectangle(265, 70, 85, 15));
        lblFecha.setFont(new Font("Dialog", 1, 13));
        lblFecha.setForeground(new Color(0, 132, 66));
        //lblValorFec.setText("lblValorFec");
        lblValorFec.setText("");
        lblValorFec.setBounds(new Rectangle(355, 70, 160, 15));
        lblValorFec.setForeground(new Color(0, 132, 66));
        lblValorFec.setFont(new Font("Dialog", 1, 13));
        pnlHeader.add(txtMonto, null);


        pnlHeader.add(lblMonto, null);
        pnlHeader.add(btnBuscar, null);
        pnlHeader.add(txtCorrelativo, null);
        pnlHeader.add(btnCorrelativo, null);
        //this.getContentPane().add(jContentPane, null);
        pnlMensaje.add(jPanel4, null);
        pnlMensaje.add(lblMensaje2, null);
        pnlMensaje.add(lblCorrelativo, null);
        pnlMensaje.add(lblValorCo, null);
        pnlMensaje.add(lblFecha, null);
        pnlMensaje.add(lblValorFec, null);
        pnlMensaje.add(lblProveedor, null);
        pnlMensaje.add(lblProveedor2, null);
        pnlMensaje.add(lblTelefono, null);
        pnlMensaje.add(lblTelefono2, null);
        pnlMensaje.add(lblMontoR, null);
        pnlMensaje.add(lblMontoR2, null);
        pnlMensaje.add(lblComunicado, null);
        pnlMensaje.add(lblFechaAct, null);
        pnlMensaje.add(lblFechaActV, null);
        jContentPane.add(pnlMensaje, null);
        jContentPane.add(pnlHeader, null);

        jContentPane.add(lblEsc, null);
        jContentPane.add(lblImprimir, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        txtCorrelativo.setDocument(new FarmaLengthText(10));
        
       
        
    }
    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
 
     
        //VariablesCaja.vIndAnulacionConReclamoNavsat = false;
        FarmaVariables.vAceptar = false;
        LimpiarVariables();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */


    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void btnCorrelativo_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCorrelativo);
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

    private void btnBuscar_actionPerformed(ActionEvent e)  {

        FarmaConnectionRemoto.closeConnection(); //  28092009 para asegurar el cierre de conexion a adm_central
       
        if(validarCampos())
        {
            //Agregado por   05.01.2009
            VariablesCaja.vIndLineaADMCentral = "N";//indicador de linea en N
            evaluaPedidoProdVirtual(txtCorrelativo.getText().trim());//verifica si es un pedido virtual
            if(VariablesVirtual.vConProductoVirtual){
                validarConexionADMCentral();//VariablesCaja.vIndLineaADMCentral
            }
            else
            {    FarmaUtility.showMessage(this,"Este pedido no corresponde a un producto virtual",txtCorrelativo); //  28092009
            }
            log.debug(" : antes de buscar pedido VariablesCaja.vIndLineaADMCentral:"+VariablesCaja.vIndLineaADMCentral);
            try 
            {
                buscarRecarga();
            } 
           catch(SQLException ex){
                ex.printStackTrace();
                FarmaUtility.showMessage(this,"Ocurrio un error al buscar el pedido  - \n" +ex.getMessage(),txtCorrelativo);
                        
            }
            
            catch(Exception x){
                 x.printStackTrace();
                 FarmaUtility.showMessage(this,"Ocurrio un error al buscar el pedido  - \n" +x.getMessage(),txtCorrelativo);
                         
             }
        }
        else {
            LimpiarVariables();
            
            }
     
    }

    private void btnBuscar_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtCorrelativo);
        } else
            chkKeyPressed(e);
    }

  

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtCorrelativo);
       // cargaLogin();
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, 
                                 "Debe presionar la tecla ESC para cerrar la ventana.", 
                                 null);
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
                                                     txtMonto);
                            VariablesCaja.vRespRecargaPmtros = false;
                        } else {
                            if (VariablesCaja.vRespuestaExito.trim().equals("-2")) {
                                LimpiarVariables();
                                FarmaUtility.showMessage(this, 
                                                         "No hay respuesta de la Recarga.\n" +
                                        "Inténtelo nuevamente.", txtMonto);
                                VariablesCaja.vRespRecargaPmtros = false;
                            } else {
                                if (VariablesCaja.vRespuestaExito.trim().equals("00")) {
    
                                    lblMensaje2.setText(" Mensaje : Recarga Exitosa  ");
                                    lblMensaje2.setForeground(new Color(43, 141, 
                                                                        39));
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
                                    lblComunicado.setText("Le recordamos que tiene sólo 5 min. para anular pedido desde su generación.");
                                    //vConProductoVirtual
                                    
                                      VariablesCaja.vRespRecargaPmtros = true;
                               
                                }
    
                                else {
                                    if (VariablesCaja.vRespuestaExito.trim().equals("0") || 
                                        VariablesCaja.vRespuestaExito.trim().equals("-1")) {
    
                                        FarmaUtility.showMessage(this, 
                                                                 "Recarga no existe en Matriz", 
                                                                 txtCorrelativo);
                                        LimpiarVariables();
                                        VariablesCaja.vRespRecargaPmtros = false;
                                    } else {
    
    
                                        //mostrarMensajeError
                                        //MOSTRAR MENSAJE DE ERROR 
                                        String mensajeError = 
                                            DBVentas.mostrarMensajeError(VariablesCaja.vRespuestaExito);
    
                                        lblMensaje2.setForeground(Color.red);
                                        lblMensaje2.setText("Mensaje : " + "[" + 
                                                            VariablesCaja.vRespuestaExito.trim() + 
                                                            "]" + " - " + 
                                                            mensajeError.trim());
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
                                        lblComunicado.setText("");
                                        VariablesCaja.vRespRecargaPmtros = true;
    
    
                                    }
                                }
                            }
                        }
                        log.debug(" indExitoRecarga : " + 
                                  VariablesCaja.vRespuestaExito);
                        VariablesCaja.vIndPedidoRecargaVirtual = "";
    
    
                    }
                    //No hay conexion com ADMCENTRAL
                    else {
                        FarmaUtility.showMessage(this, 
                                                 "No puede realizar la consulta porque la conexión esta lenta con Matriz", 
                                                 txtCorrelativo);
                        LimpiarVariables();
                        
                        VariablesCaja.vRespRecargaPmtros = false;
                    }
                  }
               
              }
       else{
           
               FarmaUtility.showMessage(this, "No existe el Pedido.Verifique!", txtCorrelativo);
               LimpiarVariables();
               VariablesCaja.vRespRecargaPmtros = false;
           }
        
            
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
      System.err.println(" : VariablesVirtual.vConProductoVirtual :" + VariablesVirtual.vConProductoVirtual);
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
        FarmaUtility.showMessage(this,"Error al obtener cantidad de productos virtuales.\n" + ex.getMessage(), null);
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
        
            lblMensaje2.setText("");
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
            lblComunicado.setText("");
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
                                 txtCorrelativo);
            
        
    }
}
