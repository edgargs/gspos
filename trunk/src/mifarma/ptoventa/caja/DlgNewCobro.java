package mifarma.ptoventa.caja;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConnection;
import mifarma.common.FarmaConnectionRemoto;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLengthText;

import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.BeanDetaPago;
import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;

import mifarma.ptoventa.caja.reference.VariablesNewCobro;
import mifarma.ptoventa.convenio.reference.VariablesConvenio;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;
import mifarma.ptoventa.caja.DlgProcesarCobro;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import javax.swing.JTable;

import mifarma.ptoventa.caja.reference.UtilityNewCobro;
import mifarma.ptoventa.fidelizacion.reference.DBFidelizacion;

/**
 * Copyright (c) 2010 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgNewCobro.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ASOSA      29.01.2010   Creación<br>
 * <br>
 * @author Alfredo Sosa Dordán<br>
 * @version 1.0<br>
 *
 */
public class DlgNewCobro extends JDialog {
    private static final Log log = LogFactory.getLog(DlgNewCobro.class);
    /** Almacena el Objeto Frame de la Aplicación - Ventana Principal */
    private Frame myParentFrame;
    /**
    private String numpeddiario="";
    private String numpedvta="";
    private String indPedidoEncontrado="";
    private double montoTotal=0;
    private double montoDolar=0;
    private String tipoCambio="";
    private String codtipoCompPed="";
    private String desctipoCompPed="";
    private String indDistribGratel="";
    private String cantItemsPed="";
    private String indPedConv="";
    private String codconv="";
    private String codcli="";
    private String nomcli="";
    private String ruccli="";
    private String dircli="";
    private String tipoPed="";
    private double montoIngreso=0;
    private boolean flagPedCubierto=false;
    private boolean flagPedVirtual=false;
    private String nroTelf="";
    private ArrayList listDeta=new ArrayList();
    private String vlblMsjPedVirtual="";
    private String indPermitCampana="N";
    */
    
    private BeanDetaPago objDPB=null;
    private boolean indCerrarPantallaAnularPed=false;
    private boolean indCerrarPantallaCobrarPed=false;
    private double mayDifOpenTarj=0; //INI - ASOSA, 26.05.2010
    private double incrTarjDol=0; 
    private double montoAntesTarjeta=0;
    private String enviaCorreo="N"; //FIN - ASOSA, 26.05.2010
    
    JTable tabla01=new JTable();
    JTable tabla02=new JTable();
    JTextFieldSanSerif cajita=new JTextFieldSanSerif();
    
    private String moneda="S";
    
    private JPanelWhite pnlFondo = new JPanelWhite();
    private JPanelHeader pnlCabe = new JPanelHeader();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JLabelWhite jLabelWhite2 = new JLabelWhite();
    private JLabelWhite jLabelWhite3 = new JLabelWhite();
    private JPanel pnlDeta = new JPanel();
    private JTextFieldSanSerif txtsol = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtdol = new JTextFieldSanSerif();
    private JTextFieldSanSerif txttarj = new JTextFieldSanSerif();
    private JLabelOrange jLabelOrange4 = new JLabelOrange();
    private JLabelOrange jLabelOrange5 = new JLabelOrange();
    private JLabelOrange lbltipcambio = new JLabelOrange();
    private JLabelOrange lblvuelto = new JLabelOrange();
    private JLabelWhite lblcli = new JLabelWhite();
    private JLabelWhite lbltelf = new JLabelWhite();
    private JLabelWhite lblmon = new JLabelWhite();
    private JButtonLabel jButtonLabel1 = new JButtonLabel();
    private JButtonLabel jButtonLabel2 = new JButtonLabel();
    private JButtonLabel jButtonLabel3 = new JButtonLabel();
    private JButtonLabel jButtonLabel4 = new JButtonLabel();
    private JLabelOrange lblsaldo = new JLabelOrange();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JLabelFunction jLabelFunction2 = new JLabelFunction();
    private JLabelOrange lblmsgrec = new JLabelOrange();
    private JComboBox cmbMoneda = new JComboBox();

    public DlgNewCobro() {
        this(null, "", false);
    }

    public DlgNewCobro(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        this.myParentFrame=parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(439, 335));
        this.getContentPane().setLayout( null );
        this.setTitle("Cobro de Pedido");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }

                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }
                });
        pnlFondo.setBounds(new Rectangle(0, 0, 435, 310));
        pnlFondo.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        pnlFondo_keyPressed(e);
                    }
                });
        pnlCabe.setBounds(new Rectangle(5, 10, 425, 105));
        pnlCabe.setBackground(Color.white);
        pnlCabe.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        pnlCabe_keyPressed(e);
                    }
                });
        jLabelWhite1.setText("Cliente:");
        jLabelWhite1.setBounds(new Rectangle(10, 5, 70, 15));
        jLabelWhite1.setForeground(new Color(43, 141, 39));
        jLabelWhite1.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        jLabelWhite1_keyPressed(e);
                    }
                });
        jLabelWhite2.setText("Monto S/.");
        jLabelWhite2.setBounds(new Rectangle(10, 70, 100, 20));
        jLabelWhite2.setFont(new Font("SansSerif", 1, 22));
        jLabelWhite2.setForeground(new Color(43, 141, 39));
        jLabelWhite2.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        jLabelWhite2_keyPressed(e);
                    }
                });
        jLabelWhite3.setText("Teléfono:");
        jLabelWhite3.setBounds(new Rectangle(10, 25, 70, 15));
        jLabelWhite3.setForeground(new Color(43, 141, 39));
        jLabelWhite3.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        jLabelWhite3_keyPressed(e);
                    }
                });
        pnlDeta.setBounds(new Rectangle(5, 120, 425, 150));
        pnlDeta.setBackground(Color.white);
        pnlDeta.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlDeta.setLayout(null);
        pnlDeta.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        pnlDeta_keyPressed(e);
                    }
                });
        txtsol.setBounds(new Rectangle(95, 25, 105, 20));
        txtsol.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtsol_keyPressed(e);
                    }

                    public void keyTyped(KeyEvent e) {
                        txtsol_keyTyped(e);
                    }
                });
        txtsol.setDocument(new FarmaLengthText(6));
        txtdol.setBounds(new Rectangle(95, 50, 105, 20));
        txtdol.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtdol_keyPressed(e);
                    }

                    public void keyTyped(KeyEvent e) {
                        txtdol_keyTyped(e);
                    }
                });
        txtdol.setDocument(new FarmaLengthText(5));
        txttarj.setDocument(new FarmaLengthText(6));
        txttarj.setBounds(new Rectangle(95, 75, 105, 20));
        txttarj.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txttarj_keyPressed(e);
                    }

                    public void keyTyped(KeyEvent e) {
                        txttarj_keyTyped(e);
                    }
                });
        jLabelOrange4.setText(", Tipo Cambio:");
        jLabelOrange4.setBounds(new Rectangle(205, 55, 79, 15));
        jLabelOrange4.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        jLabelOrange4_keyPressed(e);
                    }
                });
        jLabelOrange5.setText("Vuelto S/.");
        jLabelOrange5.setBounds(new Rectangle(200, 115, 85, 20));
        jLabelOrange5.setFont(new Font("SansSerif", 1, 18));
        jLabelOrange5.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        jLabelOrange5_keyPressed(e);
                    }
                });
        lbltipcambio.setText("[TC]");
        lbltipcambio.setBounds(new Rectangle(295, 55, 40, 15));
        lbltipcambio.setForeground(new Color(43, 141, 39));
        lbltipcambio.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        lbltipcambio_keyPressed(e);
                    }
                });
        lblvuelto.setText("[VUELTO]");
        lblvuelto.setBounds(new Rectangle(290, 115, 95, 20));
        lblvuelto.setForeground(new Color(43, 141, 39));
        lblvuelto.setFont(new Font("SansSerif", 1, 18));
        lblvuelto.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        lblvuelto_keyPressed(e);
                    }
                });
        lblcli.setText("[CLIENTE]");
        lblcli.setBounds(new Rectangle(90, 5, 275, 15));
        lblcli.setSize(new Dimension(150, 15));
        lblcli.setForeground(new Color(43, 141, 39));
        lblcli.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        lblcli_keyPressed(e);
                    }
                });
        lbltelf.setText("[TELEFONO]");
        lbltelf.setBounds(new Rectangle(75, 25, 70, 15));
        lbltelf.setForeground(new Color(43, 141, 39));
        lbltelf.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        lbltelf_keyPressed(e);
                    }
                });
        lblmon.setText("[MONTO]");
        lblmon.setBounds(new Rectangle(110, 65, 150, 30));
        lblmon.setFont(new Font("SansSerif", 1, 22));
        lblmon.setForeground(new Color(43, 141, 39));
        lblmon.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        lblmon_keyPressed(e);
                    }
                });
        jButtonLabel1.setText("Soles S/.");
        jButtonLabel1.setBounds(new Rectangle(10, 30, 65, 15));
        jButtonLabel1.setForeground(new Color(255, 130, 14));
        jButtonLabel1.setMnemonic('s');
        jButtonLabel1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jButtonLabel1_actionPerformed(e);
                    }
                });
        jButtonLabel1.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        jButtonLabel1_keyPressed(e);
                    }
                });
        jButtonLabel2.setText("Dólares $");
        jButtonLabel2.setBounds(new Rectangle(10, 55, 65, 15));
        jButtonLabel2.setForeground(new Color(255, 130, 14));
        jButtonLabel2.setMnemonic('d');
        jButtonLabel2.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jButtonLabel2_actionPerformed(e);
                    }
                });
        jButtonLabel2.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        jButtonLabel2_keyPressed(e);
                    }
                });
        jButtonLabel3.setText("Tarjeta");
        jButtonLabel3.setBounds(new Rectangle(10, 80, 60, 15));
        jButtonLabel3.setForeground(new Color(255, 130, 14));
        jButtonLabel3.setMnemonic('t');
        jButtonLabel3.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        jButtonLabel3_keyPressed(e);
                    }
                });
        jButtonLabel3.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jButtonLabel3_actionPerformed(e);
                    }
                });
        jButtonLabel4.setText("Saldo S/.");
        jButtonLabel4.setBounds(new Rectangle(10, 115, 85, 20));
        jButtonLabel4.setForeground(new Color(255, 130, 14));
        jButtonLabel4.setFont(new Font("SansSerif", 1, 18));
        lblsaldo.setText("[SALDO]");
        lblsaldo.setBounds(new Rectangle(90, 115, 80, 20));
        lblsaldo.setForeground(new Color(43, 141, 39));
        lblsaldo.setFont(new Font("SansSerif", 1, 18));
        jLabelFunction1.setBounds(new Rectangle(255, 280, 117, 19));
        jLabelFunction1.setText("[ ESC ] Cerrar");
        jLabelFunction2.setBounds(new Rectangle(135, 280, 117, 19));
        jLabelFunction2.setText("[ F5 ] Ver Detalle");
        lblmsgrec.setText("[MENSAJE]");
        lblmsgrec.setBounds(new Rectangle(10, 45, 415, 15));
        lblmsgrec.setFont(new Font("SansSerif", 1, 12));
        lblmsgrec.setForeground(Color.red);
        cmbMoneda.setBounds(new Rectangle(210, 75, 95, 20));
        cmbMoneda.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        cmbMoneda_keyPressed(e);
                    }
                });
        pnlCabe.add(lblmsgrec, null);
        pnlCabe.add(lblmon, null);
        pnlCabe.add(lblcli, null);
        pnlCabe.add(jLabelWhite3, null);
        pnlCabe.add(jLabelWhite2, null);
        pnlCabe.add(jLabelWhite1, null);
        pnlCabe.add(lbltelf, null);
        pnlDeta.add(cmbMoneda, null);
        pnlDeta.add(lblsaldo, null);
        pnlDeta.add(jButtonLabel4, null);
        pnlDeta.add(jButtonLabel3, null);
        pnlDeta.add(lblvuelto, null);
        pnlDeta.add(lbltipcambio, null);
        pnlDeta.add(jLabelOrange5, null);
        pnlDeta.add(jLabelOrange4, null);
        pnlDeta.add(txttarj, null);
        pnlDeta.add(txtdol, null);
        pnlDeta.add(txtsol, null);
        pnlDeta.add(jButtonLabel1, null);
        pnlDeta.add(jButtonLabel2, null);
        pnlFondo.add(jLabelFunction2, null);
        pnlFondo.add(jLabelFunction1, null);
        pnlFondo.add(pnlDeta, null);
        pnlFondo.add(pnlCabe, null);
        this.getContentPane().add(pnlFondo, null);
    }
    
    private void initialize(){
        //HOLA
        FarmaVariables.vAceptar=false;
        //ADIOS
        try{
            VariablesNewCobro.docsValidos = DBFidelizacion.obtenerParamDocIden();
        }catch(SQLException e){
            System.out.println("ERROR en docs validos: "+e.getMessage());
        }
        cargarComboMon();
    }
    
    private void cargarComboMon()
    {
      FarmaLoadCVL.loadCVLfromArrays(cmbMoneda,FarmaConstants.HASHTABLE_MONEDA,FarmaConstants.MONEDAS_CODIGO,FarmaConstants.MONEDAS_DESCRIPCION,true);
    }

    private void this_windowOpened(WindowEvent e) {
        getIncremento(); //ASOSA, 26.05.2010
        /*JOptionPane.showMessageDialog(null,"2.133 "+FarmaUtility.formatNumber(2.133));
        JOptionPane.showMessageDialog(null,"2.135 "+FarmaUtility.formatNumber(2.135));
        JOptionPane.showMessageDialog(null,"2.136 "+FarmaUtility.formatNumber(2.136));
        JOptionPane.showMessageDialog(null,"2.133113 "+FarmaUtility.getDecimalNumber("2.133113"));
        JOptionPane.showMessageDialog(null,"2.135113 "+FarmaUtility.getDecimalNumber("2.135113"));
        JOptionPane.showMessageDialog(null,"2.136113 "+FarmaUtility.getDecimalNumber("2.136113"));*/
        /********HOLAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA***********/
        VariablesCaja.vIndPedidoConProdVirtual = false;
        VariablesFidelizacion.vRecalculaAhorroPedido = false;
        VariablesCaja.arrayDetFPCredito = new ArrayList();
        VariablesCaja.cobro_Pedido_Conv_Credito     = "N";
        VariablesCaja.uso_Credito_Pedido_N_Delivery = "N";
        VariablesCaja.arrayPedidoDelivery = new ArrayList();
        VariablesCaja.usoConvenioCredito = "";
        VariablesCaja.valorCredito_de_PedActual = 0.0;
        VariablesCaja.monto_forma_credito_ingresado = "0.00";
        /********ADIOSSSSSSSSSSSSSS******************************/
        UtilityNewCobro.inicializarVariables();      
        lblvuelto.setText("0.00");
        VariablesNewCobro.numpeddiario=VariablesCaja.vNumPedPendiente;
        VariablesNewCobro.numpedvta=VariablesVentas.vNum_Ped_Vta;
        FarmaUtility.centrarVentana(this);
        txtsol.setText(ConstantsCaja.msgIngreso);
        FarmaUtility.moveFocus(txtsol);
          if(!UtilityCaja.existeCajaUsuarioImpresora(this, null) || !UtilityCaja.validaFechaMovimientoCaja(this,null))
          {
            FarmaUtility.showMessage(this, "El Pedido será Anulado. Vuelva a generar uno nuevo.", null);
            try{
                DBCaja.anularPedidoPendiente(VariablesNewCobro.numpedvta);
                anularAcumuladoCanje();  
                //HOLA
                VariablesCaja.vCierreDiaAnul=false;
                //ADIOS
                FarmaUtility.aceptarTransaccion();
                FarmaUtility.showMessage(this, "Pedido Anulado Correctamente", null);
                cerrarVentana(true);
                return;
            } catch(SQLException sql)
            {
                FarmaUtility.liberarTransaccion();
                sql.printStackTrace();
                FarmaUtility.showMessage(this, "windowOpened - Error al Anular el Pedido.\n" + sql.getMessage(), null);
                cerrarVentana(true);
                return;
            }
          }
          buscaPedidoDiario();
          //HOLA
          VariablesCaja.vNumPedPendiente = "";
          VariablesCaja.vFecPedACobrar = "";
            FarmaVariables.vAceptar = false;
          //ADIOS
            
            //En caso la recarga sea al credito y ni bien se inicie se cobre
            String fpago="";
            try{
                fpago=DBCaja.determinarRecVirConvCred(VariablesNewCobro.numpedvta);
            }catch(SQLException exx){
                System.out.println("ERROR en cobroono: "+exx.getMessage());
            }
            if(!fpago.equalsIgnoreCase(FarmaConstants.INDICADOR_N)){
                evaluaPedidoProdVirtual(VariablesNewCobro.numpedvta);
                if(FarmaUtility.rptaConfirmDialogDefaultNo(this,"Desea hacer una recarga vitual al celular: \n"+VariablesNewCobro.nroTelf+
                                                                "\n"+VariablesNewCobro.vlblMsjPedVirtual)){
                    String list[]=fpago.split(",");
                    System.out.println("fpago: "+fpago);
                    System.out.println("list[0]: "+list[0]);
                    System.out.println("list[1]: "+list[1]);
                    VariablesNewCobro.montoSoles=VariablesNewCobro.montoTotal;
                    agregarDetalle(list[0],list[1],"SOLES",VariablesNewCobro.montoTotal,VariablesNewCobro.montoTotal,"01");
                    VariablesVentas.vProductoVirtual=false;
                }else{
                    UtilityNewCobro.inicializarVariables();
                    VariablesVentas.vProductoVirtual=false;
                    cerrarVentana(true);
                }
            }
    }
    
    private void cerrarVentana(boolean pAceptar)
    {
      FarmaVariables.vAceptar = pAceptar;
      //HOLA
      VariablesCaja.vNumPedVta = "";
      //ADIOS
      //ESTABA BIEN -- dubilluz 06.07.2010
      UtilityNewCobro.inicializarVariables(); 
      this.setVisible(false);
      this.dispose();
    }
    
    private void chkkeyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            eventoEscape();
        }else if(e.getKeyCode() == KeyEvent.VK_F5){
            DlgNewDetaPed obj=new DlgNewDetaPed(myParentFrame,"",true);
            obj.setVisible(true);
        }
    }
    
    private void anularAcumuladoCanje(){
        try{
            String pIndLineaMatriz = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
            log.debug("pIndLineaMatriz "+pIndLineaMatriz);
            boolean pRspCampanaAcumulad = UtilityCaja.realizaAccionCampanaAcumulada
                                   (
                                    pIndLineaMatriz,
                                    VariablesNewCobro.numpedvta,this,
                                    ConstantsCaja.ACCION_ANULA_PENDIENTE,
                                    null,
                                    FarmaConstants.INDICADOR_S//Aqui si liberara stock al regalo
                                    );
            
            System.out.println("pRspCampanaAcumulad "+pRspCampanaAcumulad);
            if (!pRspCampanaAcumulad)
            {
                System.out.println("Se recupero historico y canje  XXX");
                FarmaUtility.liberarTransaccion();
                FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                        FarmaConstants.INDICADOR_S);
            }
            FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_S);
            FarmaUtility.aceptarTransaccion();
            log.info("Pedido anulado sin quitar respaldo.");
            //JMIRANDA 05.07.2010
            cerrarVentana(false);
        } catch(Exception sql){
             FarmaUtility.liberarTransaccion();
                     FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                        FarmaConstants.INDICADOR_S);
        }finally{
            FarmaConnectionRemoto.closeConnection();
        }
    }
    
    private void buscaPedidoDiario()
    {
      ArrayList myArray = new ArrayList();
      VariablesNewCobro.numpeddiario = FarmaUtility.completeWithSymbol(VariablesNewCobro.numpeddiario, 4, "0", "I");
      try
      {
        DBCaja.obtieneInfoCobrarPedido(myArray, VariablesNewCobro.numpeddiario, VariablesCaja.vFecPedACobrar);       
        validaInfoPedido(myArray);
      } catch(SQLException sql)
      {
        log.error(null,sql);
        FarmaUtility.showMessage(this, "Error al obtener Información del Pedido.\n" + sql.getMessage(), null);
      }
    }
    
    private void validaInfoPedido(ArrayList pArrayList)
    {
      if(pArrayList.size() < 1)
      {
        FarmaUtility.showMessage(this, "El Pedido No existe o No se encuentra pendiente de pago", null);
        VariablesNewCobro.indPedidoEncontrado = FarmaConstants.INDICADOR_N;
          limpiarDatos();
        return;
      } else if(pArrayList.size() > 1)
      {
        FarmaUtility.showMessage(this, "Se encontro mas de un pedido.\n" +
          "Pónganse en contacto con el área de Sistemas.", null);
        VariablesNewCobro.indPedidoEncontrado = FarmaConstants.INDICADOR_N;
          limpiarDatos();
        return;
      } else
      {
          //HOLA
          VariablesCaja.vCodFormaPago = "";
          VariablesCaja.vDescFormaPago = "";
          VariablesCaja.vDescMonedaPago = "";
          VariablesCaja.vValMontoPagado = "";
          VariablesCaja.vValTotalPagado = "";
          //ADIOS
        VariablesNewCobro.indPedidoEncontrado = FarmaConstants.INDICADOR_S;
        muestraInfoPedido(pArrayList);
      }
    }
    
    private void muestraInfoPedido(ArrayList pArrayList)
    {
        VariablesNewCobro.numpedvta= ((String)((ArrayList)pArrayList.get(0)).get(0)).trim();
        if(!UtilityCaja.verificaEstadoPedido(this, VariablesNewCobro.numpedvta, ConstantsCaja.ESTADO_PENDIENTE, txtsol))
        {
            VariablesNewCobro.indPedidoEncontrado = FarmaConstants.INDICADOR_N;
            return;
        }
        FarmaUtility.liberarTransaccion();      
        String dinero01 = ((String)((ArrayList)pArrayList.get(0)).get(1)).trim();
        VariablesNewCobro.montoTotal=FarmaUtility.getDecimalNumber(dinero01);
        String dinero02 = ((String)((ArrayList)pArrayList.get(0)).get(2)).trim();
        dinero02 = FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(dinero02) + FarmaUtility.getRedondeo(FarmaUtility.getDecimalNumber(dinero02)));
        VariablesNewCobro.montoDolar=FarmaUtility.getDecimalNumber(dinero02);
        VariablesNewCobro.tipoCambio = ((String)((ArrayList)pArrayList.get(0)).get(3)).trim();
        lbltipcambio.setText(VariablesNewCobro.tipoCambio);
        VariablesNewCobro.codtipoCompPed = ((String)((ArrayList)pArrayList.get(0)).get(4)).trim();
        VariablesNewCobro.desctipoCompPed=((String)((ArrayList)pArrayList.get(0)).get(5)).trim();
        VariablesNewCobro.nomcli = ((String)((ArrayList)pArrayList.get(0)).get(6)).trim();
        VariablesNewCobro.ruccli = ((String)((ArrayList)pArrayList.get(0)).get(7)).trim();
        VariablesNewCobro.dircli = ((String)((ArrayList)pArrayList.get(0)).get(8)).trim();
        VariablesNewCobro.tipoPed =  ((String)((ArrayList)pArrayList.get(0)).get(9)).trim();
        if(VariablesNewCobro.codtipoCompPed.equalsIgnoreCase(ConstantsVentas.TIPO_COMP_FACTURA))
            jLabelWhite1.setText("Razón Social :");
        else
            jLabelWhite1.setText("Cliente :");
        VariablesNewCobro.indDistribGratel = ((String)((ArrayList)pArrayList.get(0)).get(11)).trim();
        //ASOSA VariablesCaja.vIndDeliveryAutomatico = ((String)((ArrayList)pArrayList.get(0)).get(12)).trim();
        //VariablesCaja.vIndDeliveryAutomatico=FarmaConstants.INDICADOR_N; //ASOSA, 02.07.2010, como esto no es para delivery entonces mejor defrente lo pongo como 'N'
		//JMIRANDA 05.07.2010
        VariablesCaja.vIndDeliveryAutomatico = ((String)((ArrayList)pArrayList.get(0)).get(12)).trim();
        VariablesNewCobro.cantItemsPed = ((String)((ArrayList)pArrayList.get(0)).get(13)).trim();
        //indicador de Convenio
        VariablesNewCobro.indPedConv = ((String)((ArrayList)pArrayList.get(0)).get(14)).trim();
        VariablesNewCobro.codconv = ((String)((ArrayList)pArrayList.get(0)).get(15)).trim();
        VariablesNewCobro.codcli = ((String)((ArrayList)pArrayList.get(0)).get(16)).trim();
        evaluaPedidoProdVirtual(VariablesNewCobro.numpedvta);
        if(VariablesNewCobro.indDistribGratel.equalsIgnoreCase(FarmaConstants.INDICADOR_S) ||
            VariablesNewCobro.montoIngreso >= VariablesNewCobro.montoTotal )
        {
            VariablesNewCobro.flagPedCubierto = true;
        } else
        {
            VariablesNewCobro.flagPedCubierto = false;
        }
        lblmon.setText(FarmaUtility.formatNumber(VariablesNewCobro.montoTotal));
        lblcli.setText(VariablesNewCobro.nomcli);
        lblsaldo.setText(lblmon.getText());
    }
    
    private void evaluaPedidoProdVirtual(String pNumPedido)
    {
        int cantProdVirtualesPed = 0;
        String tipoProd = "";
        cantProdVirtualesPed = cantidadProductosVirtualesPedido(pNumPedido);
        if( cantProdVirtualesPed <= 0 )
        {
            VariablesNewCobro.vlblMsjPedVirtual="";
            VariablesNewCobro.flagPedVirtual = false;
        } else{
            tipoProd = obtieneTipoProductoVirtual(pNumPedido);
            if(tipoProd.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_TARJETA))
                VariablesNewCobro.vlblMsjPedVirtual="El pedido contiene una Tarjeta Virtual. Si lo cobra, \n No podrá ser anulado.";
            else if(tipoProd.equalsIgnoreCase(ConstantsVentas.TIPO_PROD_VIRTUAL_RECARGA)){
                double cantMin=Double.parseDouble(time_max(pNumPedido));
                if(cantMin==0) VariablesNewCobro.vlblMsjPedVirtual="De ser cobrado este pedido no podra ser anulado";
                else VariablesNewCobro.vlblMsjPedVirtual="Recarga Virtual.Sólo podrá anularse dentro de "+ time_max(pNumPedido) +" minutos.";
            VariablesNewCobro.nroTelf=""+ num_telefono(pNumPedido);
            }else{
                VariablesNewCobro.vlblMsjPedVirtual="";
            }
            VariablesNewCobro.flagPedVirtual = true;
        }
        lblmsgrec.setText(VariablesNewCobro.vlblMsjPedVirtual);
        lbltelf.setText(VariablesNewCobro.nroTelf);
    }
    
    private int cantidadProductosVirtualesPedido(String pNumPedido)
    {
        int cant = 0;
        try{
            cant = DBCaja.obtieneCantProdVirtualesPedido(pNumPedido);
        } catch(SQLException ex){
            log.error(null,ex);
            cant = 0;
            FarmaUtility.showMessage(this,"Error al obtener cantidad de productos virtuales.\n" + ex.getMessage(), null);
        }
        return cant;
    }
    
    private String obtieneTipoProductoVirtual(String pNumPedido)
    {
        String tipoProd = "";
        try
        {
            tipoProd = DBCaja.obtieneTipoProductoVirtualPedido(pNumPedido);
        } catch(SQLException ex){
            log.error(null,ex);
            tipoProd = "";
            FarmaUtility.showMessage(this,"Error al obtener cantidad de productos virtuales.\n" + ex.getMessage(), null);
        }
        return tipoProd;
    }
    
    private String time_max(String pNumPedido)
    {
        String valor = "";
        try{
            valor = DBCaja.getTimeMaxAnulacion(pNumPedido);
        }catch(SQLException e)
        {
            log.error(null,e);
            FarmaUtility.showMessage(this,"Ocurrió un error al obtener tiempo máximo de anulacián de Producto Recarga Virtual.\n" + e.getMessage(),null);
        }
        return valor; 
    }
    
    private String num_telefono(String numPed)
    {
        String num_telefono = "";
        try{
            num_telefono = DBCaja.getNumeroRecarga(numPed);
        }catch(SQLException e)
        {
            log.error(null,e);
            FarmaUtility.showMessage(this,"Ocurrió un error al obtener el número de teléfono de recarga.\n" + e.getMessage(),null);
        }
        return num_telefono;     
    }
    
    private void verificaMontoPagadoPedido()
    {
        if( VariablesNewCobro.montoTotal > VariablesNewCobro.montoIngreso ){
            System.out.println("No Cubierto");
            VariablesNewCobro.flagPedCubierto = false;
            VariablesNewCobro.saldo=FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(VariablesNewCobro.montoTotal-VariablesNewCobro.montoIngreso));
            lblsaldo.setText(FarmaUtility.formatNumber(VariablesNewCobro.saldo));
            lblvuelto.setText("0.00");            
        }else{
            System.out.println("Cubierto");
            VariablesNewCobro.flagPedCubierto = true;
            VariablesNewCobro.saldo=0;
            lblsaldo.setText(FarmaUtility.formatNumber(VariablesNewCobro.saldo));
            lblvuelto.setText(FarmaUtility.formatNumber(VariablesNewCobro.montoIngreso-VariablesNewCobro.montoTotal));
        }
        VariablesNewCobro.vuelto=lblvuelto.getText();
    }

    private void txtsol_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(FarmaUtility.isDouble(txtsol.getText())){
                double monto=Double.parseDouble(txtsol.getText());
                if(monto==0){
                    jButtonLabel2.doClick();
                    FarmaUtility.moveFocus(txtdol);
                    txtdol.setEditable(true);
                    UtilityNewCobro.reemplazarObjeto("00001");
                    VariablesNewCobro.montoSoles=0;
                    VariablesNewCobro.montoIngreso=VariablesNewCobro.montoSoles+VariablesNewCobro.montoDolares+VariablesNewCobro.montoTarj;
                    VariablesNewCobro.saldo=FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(VariablesNewCobro.montoTotal-VariablesNewCobro.montoIngreso));
                    lblsaldo.setText(FarmaUtility.formatNumber(VariablesNewCobro.saldo));
                }else{
                    VariablesNewCobro.montoSoles=monto;
                    agregarDetalle("00001","EFECTIVO SOLES","SOLES",monto,monto,"01");
                }
            }else if(txtsol.getText().trim().equalsIgnoreCase(ConstantsCaja.msgIngreso) || txtsol.getText().trim().length()==0){
                jButtonLabel2.doClick();
                FarmaUtility.moveFocus(txtdol);
            }else{
                FarmaUtility.showMessage(this,"Ingrese cantidad de Soles válida",txtsol);
            }
        }else{
            chkkeyPressed(e);
        }
    }
    
    /*private void agregarDetalleSoles(){
        objDPB=new BeanDetaPago();
        objDPB.setCod_fp("00001");
        objDPB.setDesc_fp("EFECTIVO SOLES");
        objDPB.setCant("0");
        objDPB.setMoneda("SOLES");
        objDPB.setMonto(txtsol.getText());
        objDPB.setTotal(txtsol.getText());
        objDPB.setCod_moneda("01");
        objDPB.setComodin("0.00");
        objDPB.setNrotarj("");
        objDPB.setFecventarj("");
        objDPB.setNomclitarj("");
        objDPB.setCodconv("");
        listDeta.add(objDPB);
        montoIngreso=montoIngreso+Double.parseDouble(txtsol.getText());
        verificaMontoPagadoPedido();
        if(!flagPedCubierto){
            txtdol.setEnabled(true);
            FarmaUtility.moveFocus(txtdol);
        }
    }*/

    private void txtdol_keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(FarmaUtility.isDouble(txtdol.getText())){                 
                double monto=Double.parseDouble(txtdol.getText());
                if(monto==0){
                    UtilityNewCobro.reemplazarObjeto("00002");
                    VariablesNewCobro.montoDolares=0;
                    VariablesNewCobro.montoIngreso=VariablesNewCobro.montoSoles+VariablesNewCobro.montoDolares+VariablesNewCobro.montoTarj;
                    VariablesNewCobro.saldo=FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(VariablesNewCobro.montoTotal-VariablesNewCobro.montoIngreso));
                    lblsaldo.setText(FarmaUtility.formatNumber(VariablesNewCobro.saldo));
                    txttarj.setText(FarmaUtility.formatNumber(VariablesNewCobro.montoTotal-VariablesNewCobro.montoSoles-VariablesNewCobro.montoDolares));
                    //FarmaUtility.moveFocus(txttarj);
                    reseteaComboMoneda();
                }else{
                    double total=Double.parseDouble(txtdol.getText())*Double.parseDouble(lbltipcambio.getText());
                    VariablesNewCobro.montoDolares=total;
                    agregarDetalle("00002","EFECTIVO DOLARES","DOLARES",monto,total,"02");
                }
            }else if(txtdol.getText().trim().equalsIgnoreCase(ConstantsCaja.msgIngreso) || txtdol.getText().trim().length()==0){
                jButtonLabel3.doClick();
                //FarmaUtility.moveFocus(txttarj);
                reseteaComboMoneda();
            }else{
                FarmaUtility.showMessage(this,"Ingrese cantidad de Dólares válida",txtdol);
            }
        }else{
            chkkeyPressed(e);
        }
    }
    
    private void agregarDetalle(String codFP, 
                                String descFP,
                                String moneda,
                                double monto,
                                double total,
                                String codmoneda){
        UtilityNewCobro.reemplazarObjeto(codFP);
        objDPB=new BeanDetaPago();
        objDPB.setCod_fp(codFP);
        objDPB.setDesc_fp(descFP);
        objDPB.setCant("0");
        objDPB.setMoneda(moneda);
        objDPB.setMonto(FarmaUtility.formatNumber(monto));
        objDPB.setTotal(FarmaUtility.formatNumber(total));
        objDPB.setCod_moneda(codmoneda);
        objDPB.setComodin("0.00");
        objDPB.setNrotarj("");
        objDPB.setFecventarj("");
        objDPB.setNomclitarj("");
        objDPB.setCodconv("");
        objDPB.setDnix("");
        objDPB.setCodvou("");
        objDPB.setLote("");
        System.out.println("monto: "+monto);
        System.out.println("total: "+total);
        VariablesNewCobro.listDeta.add(objDPB);
        VariablesNewCobro.montoIngreso=VariablesNewCobro.montoSoles+VariablesNewCobro.montoDolares+VariablesNewCobro.montoTarj;        
        VariablesNewCobro.saldo=FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(VariablesNewCobro.montoTotal-VariablesNewCobro.montoIngreso));
        verificaMontoPagadoPedido();
        if(!VariablesNewCobro.flagPedCubierto && codmoneda.equalsIgnoreCase("01")){
            jButtonLabel2.doClick();
            FarmaUtility.moveFocus(txtdol);
        }
        if(!VariablesNewCobro.flagPedCubierto && codmoneda.equalsIgnoreCase("02")){
            jButtonLabel3.doClick();
            //FarmaUtility.moveFocus(txttarj);
            reseteaComboMoneda();
            txttarj.setText(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(""+(VariablesNewCobro.montoTotal-VariablesNewCobro.montoSoles-VariablesNewCobro.montoDolares))));           
        }
        if(VariablesNewCobro.flagPedCubierto){
            cobrar();
        }
    }
    
    private void cobrar(){
        try{
            DBCaja.grabaInicioFinProcesoCobroPedido(VariablesNewCobro.numpedvta,"I");
            System.out.println("Grabo el tiempo de inicio de cobro");
        }
        catch(SQLException sql){
            FarmaUtility.liberarTransaccion();
            sql.printStackTrace();            
            System.out.println("Error al grabar el tiempo de inicio de cobro");
        }
        procesar();
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    
    private void eventoEscape(){
        
        //se deja el indicador de impresio de cupon por pedido en N
        if(!VariablesNewCobro.numpedvta.equalsIgnoreCase("")){
            VariablesNewCobro.indPermitCampana=verificaPedidoCamp(VariablesNewCobro.numpedvta);
            if(VariablesNewCobro.indPermitCampana.trim().equalsIgnoreCase("S")){
                actualizaPedidoCupon("",VariablesNewCobro.numpedvta,"N","S");
            }
        }
        if ( FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL) )
        {
            if(indCerrarPantallaAnularPed && VariablesNewCobro.indPedidoEncontrado.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                //Se anulara el pedido 
                if(VariablesCaja.vIndDeliveryAutomatico.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                    /*if(FarmaUtility.rptaConfirmDialog(this, "El Pedido sera Anulado. Desea Continuar?")){
                        try{
                            DBCaja.anularPedidoPendiente(VariablesCaja.vNumPedVta);
                            FarmaUtility.aceptarTransaccion();
                            log.info("Pedido anulado.");
                            FarmaUtility.showMessage(this, "Pedido Anulado Correctamente", null);
                            cerrarVentana(true);
                        } catch(SQLException sql){
                            FarmaUtility.liberarTransaccion();
                            log.error(null,sql);
                            if(sql.getErrorCode()==20002)
                                FarmaUtility.showMessage(this, "El pedido ya fue anulado!!!", null); 
                            else if(sql.getErrorCode()==20003)
                                FarmaUtility.showMessage(this, "El pedido ya fue cobrado!!!", null); 
                            else    
                                FarmaUtility.showMessage(this, "Error al Anular el Pedido.\n" + sql.getMessage(), null);
                                cerrarVentana(true);
                        }
                    } */               
                }
            else{
                    System.out.println("SALIOSALIOSALIOSALIOSALIOSALIOSALIOSALIOSALIOSALIOSALIOSALIOSALIOSALIO");
                try{
                    UtilityCaja.liberaProdRegalo(VariablesNewCobro.numpedvta,
                                                      ConstantsCaja.ACCION_ANULA_PENDIENTE,
                                                          FarmaConstants.INDICADOR_S);
                    
                    //ESTE ES MEJOR - dubilluz 05.07.2010
                    DBCaja.anularPedidoPendienteSinRespaldo(VariablesNewCobro.numpedvta); //ASOSA, 02.07.2010
                    //ESTABA MAL
                    //DBCaja.anularPedidoPendienteSinRespaldo(VariablesCaja.vNumPedVta); //ASOSA, 02.07.2010 - faltaba indicar que la variable para enviar como parametro tendria que ser de la caja y ya no la de new cobro porque esta ya se limpio inmediatamente despues de cobrar
                    ///-- inicio de validacion de Campaña 
                    String pIndLineaMatriz = FarmaConstants.INDICADOR_N;
                    boolean pRspCampanaAcumulad = UtilityCaja.realizaAccionCampanaAcumulada
                                         (
                                          pIndLineaMatriz,
                                          VariablesNewCobro.numpedvta,this,
                                          ConstantsCaja.ACCION_ANULA_PENDIENTE,
                                          null,
                                          FarmaConstants.INDICADOR_S//Aqui si liberara stock al regalo
                                          );
                    
                    if (!pRspCampanaAcumulad)
                    {
                        FarmaUtility.liberarTransaccion();
                        FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                              FarmaConstants.INDICADOR_S);
                    }          
                    if(pIndLineaMatriz.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                        FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ,
                                                      FarmaConstants.INDICADOR_S);
                    }
                    
                    FarmaUtility.aceptarTransaccion();
                    log.info("Pedido anulado sin quitar respaldo.");
                    cerrarVentana(false);
                } catch(SQLException sql){
                    FarmaUtility.liberarTransaccion();
                    log.error(null,sql);
                    if(sql.getErrorCode()==20002)
                        FarmaUtility.showMessage(this, "El pedido ya fue anulado!!!", null); 
                    else if(sql.getErrorCode()==20003)
                        FarmaUtility.showMessage(this, "El pedido ya fue cobrado!!!", null); 
                    else    
                        FarmaUtility.showMessage(this, "Error al Anular el Pedido.\n" + sql.getMessage(), null);
                    cerrarVentana(true); //ASOSA, 02.07.2010, estaba en false
                }
            }
            
          } else cerrarVentana(false);
        } else cerrarVentana(false);      
    }
    
    /**
     * Se valida que el pedido tenga productos de campaña
     */
    private String verificaPedidoCamp(String numPed)
    {
      String resp = "";
      try
        {
          resp = DBCaja.verificaPedidoCamp(numPed);
        }catch(SQLException e)
        {
          e.printStackTrace();
          FarmaUtility.showMessage(this,"Ocurrió un error al validar pedido por campaña.\n" + e.getMessage(),null);
        }
       return resp;     
    }
    
    /**
     * Se actualiza el estado del pedido cupon para emision
     * */
    private void actualizaPedidoCupon(String codCamp,String vtaNumPed,String estado,String indtodos){
      try
          {
           DBCaja.actualizaIndImpre(codCamp,vtaNumPed,estado,indtodos);
           FarmaUtility.aceptarTransaccion();
          }catch(SQLException e)
          {
            //e.printStackTrace();
        log.error(null,e);
            FarmaUtility.showMessage(this,"Ocurrió un error al validar la forma de pago del pedido.\n" + e.getMessage(),null);
          }  
    }
    
    public void setIndPantallaCerrarAnularPed(boolean pValor)
    {
      this.indCerrarPantallaAnularPed = pValor;
    }
    
    public void setIndPantallaCerrarCobrarPed(boolean pValor)
    {
      this.indCerrarPantallaCobrarPed = pValor;
    }

    private void  txttarj_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(FarmaUtility.isDouble(txttarj.getText())){
                double monto=Double.parseDouble(txttarj.getText());
                if(moneda.equalsIgnoreCase("D")){
                    monto=FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(monto*FarmaUtility.getDecimalNumber(lbltipcambio.getText())));
                    VariablesNewCobro.montoTarj_02=FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(monto/Double.parseDouble(lbltipcambio.getText())));
                }
                if(moneda.equalsIgnoreCase("S")){
                    VariablesNewCobro.montoTarj_02=monto;
                }
                if(monto==0){
                    jButtonLabel1.doClick();
                    //FarmaUtility.moveFocus(txtsol);
                    UtilityNewCobro.reemplazarObjeto(VariablesNewCobro.codfpHOLA);
                    VariablesNewCobro.montoTarj=0;
                    VariablesNewCobro.montoIngreso=VariablesNewCobro.montoSoles+VariablesNewCobro.montoDolares+VariablesNewCobro.montoTarj;
                    VariablesNewCobro.saldo=FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(VariablesNewCobro.montoTotal-VariablesNewCobro.montoIngreso));
                    lblsaldo.setText(""+VariablesNewCobro.saldo);
                }else if((monto<=FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(VariablesNewCobro.montoTotal-VariablesNewCobro.montoSoles-VariablesNewCobro.montoDolares))) || 
                        (VariablesNewCobro.saldo==0 &&  FarmaUtility.getDecimalNumber(txttarj.getText())<=FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(VariablesNewCobro.montoTotal))) ||
                        (moneda.equals("D") &&
                         monto<=FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(VariablesNewCobro.montoTotal+mayDifOpenTarj-VariablesNewCobro.montoSoles-VariablesNewCobro.montoDolares))
                                      )
                        ){
                    /*JOptionPane.showMessageDialog(null,"monto: "+monto);
                    JOptionPane.showMessageDialog(null,"FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(VariablesNewCobro.montoTotal+0.1-VariablesNewCobro.montoSoles-VariablesNewCobro.montoDolares): "+FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(VariablesNewCobro.montoTotal+0.1-VariablesNewCobro.montoSoles-VariablesNewCobro.montoDolares)));*/
                    double plata=0;
                    plata=VariablesNewCobro.montoTarj;
                    VariablesNewCobro.montoTarj=monto;
                    monto=plata;
                    DlgTarjeCred objTC=new DlgTarjeCred(myParentFrame,"",true,moneda);
                    objTC.setVisible(true);
                    if(!FarmaVariables.vAceptar){
                        if(!UtilityNewCobro.buscaTarjeta()){
                            VariablesNewCobro.montoTarj=0;
                        }else{
                            VariablesNewCobro.montoTarj=monto;
                            txttarj.setText(FarmaUtility.formatNumber(VariablesNewCobro.montoTarj));
                        }
                    }
                    lblsaldo.setText(FarmaUtility.formatNumber(VariablesNewCobro.saldo));
                    if(Double.parseDouble(lblsaldo.getText())<0) lblsaldo.setText("0.00");
                    if(VariablesNewCobro.saldo==0) lblsaldo.setText(FarmaUtility.formatNumber(VariablesNewCobro.montoTotal));
                    if(VariablesNewCobro.flagPedCubierto){
                        lblvuelto.setText(""+VariablesNewCobro.vuelto);
                        cobrar();
                    }
                }else{
                    FarmaUtility.showMessage(this,
                                             "Ingrese como máximo "+FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(VariablesNewCobro.montoTotal-VariablesNewCobro.montoSoles-VariablesNewCobro.montoDolares)),
                                             txttarj);
                }
            }else if(txttarj.getText().trim().equalsIgnoreCase(ConstantsCaja.msgIngreso) || txttarj.getText().trim().length()==0){
                //FarmaUtility.moveFocus(txtsol);
                jButtonLabel1.doClick();
            }else{
                FarmaUtility.showMessage(this,"Ingrese cantidad válida",txtsol);
            }
        }else
            chkkeyPressed(e);
    }

    private void jLabelOrange1_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void jLabelOrange2_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void jLabelOrange4_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void jLabelOrange5_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void lbltipcambio_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void lblvuelto_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void pnlDeta_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void jLabelWhite1_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void jLabelWhite3_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void lblcli_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void lbltelf_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void jLabelWhite2_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void lblmon_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void pnlCabe_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void pnlFondo_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }


    private void irAlComienzo(){
        FarmaUtility.moveFocus(txtsol);
        VariablesNewCobro.listDeta.clear();
        VariablesNewCobro.montoIngreso=0;
        txtdol.setText("");
        VariablesNewCobro.saldo=0;
        txttarj.setText("");
    }

    private void jButtonLabel1_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void jButtonLabel2_actionPerformed(ActionEvent e) {
        if(txtdol.getText().length()==0) txtdol.setText(ConstantsCaja.msgIngreso);
        if(txttarj.getText().equalsIgnoreCase(ConstantsCaja.msgIngreso)) txttarj.setText("");
        if(txtsol.getText().equalsIgnoreCase(ConstantsCaja.msgIngreso)) txtsol.setText("");
        FarmaUtility.moveFocus(txtdol);
    }

    private void jButtonLabel2_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void txtsol_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtsol,e);
        /*int cant=0;
        String soles=txtsol.getText();
        System.out.println("dsadsad: "+"HOLA".substring(0,1));
        for(int k=0;k<soles.length();k++){
            System.out.println("DIGITO: "+soles.charAt(k));
            if(soles.charAt(k)=='.'){                
                cant=cant+1;
            }
        }
        System.out.println("cantidad: "+cant);
        if(cant>0){
            e.consume();
            cant=0;
        }*/
    }

    private void txtdol_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtdol,e);
    }
    
    private void procesar(){
          //verificar si es un pedido con convenio
         /* if (VariablesCaja.vIndPedidoConvenio.equalsIgnoreCase(FarmaConstants.INDICADOR_S) &&
              !VariablesConvenio.vCodCliente.equalsIgnoreCase("")) {
           //if(VariablesVentas.vEsPedidoDelivery){   //Se valida tipo delivery, aunque sea convenio.
              log.debug("***************COBRO PEDIDO TIPO DELIVERY**********************");
              procesoCobroDelivery();
          } else {*/
              log.debug("*Cobro de Pedido Normal");
              //la generacion de cupones no aplica convenios
              VariablesNewCobro.indPermitCampana = 
                      verificaPedidoCamp(VariablesNewCobro.numpedvta);
              if (VariablesNewCobro.indPermitCampana.trim().equalsIgnoreCase("S") && 
                  VariablesNewCobro.listDeta.size() > 0) {
                  if (validarFormasPagoCupones(VariablesNewCobro.numpedvta)) {
                      System.out.println("020202020202020200202020202020022");
                      /* Se valida las formas de pago de las campañas 
                       * de tipo Monto Descuento.
                       * Se verificara si puede permitir cobrar
                       */
                      cobrarPedido(); //procesar cobro de pedido
                      
                  }
                  System.out.println("010101010101010101010101010101");
              } else {
                  System.out.println("0303030303030303030030303030030303030");
                  cobrarPedido(); //procesar cobro de pedido
              }
              System.out.println("040404040040404040040404040040400404040400404");
              pedidoCobrado();
            //ESTABA MAL -- dubilluz 06.07.2010
            //UtilityNewCobro.inicializarVariables(); 
          //}
          //Si la variable indica que de escape y recalcule todo el ahorro del cliente
          if(VariablesFidelizacion.vRecalculaAhorroPedido){ //ASOSA, lo comento porque al parecer ya ni entraria jamas a este if
              eventoEscape();
          }
    }
    
    /**
     * Se valida los montos por productos que esten en una campaña para llevarse los cupones ganados
     * */
    private boolean validarFormasPagoCupones(String numPedVta){
        
        boolean valor=true;
        String codSel,codobtenido="";
        String monto1,monto2,descrip="",desccamp="",indAcep="",codCamp="",numPed="";
        ArrayList array=new ArrayList();
        int maxform=0,cant=0;
        
        if(VariablesNewCobro.listDeta.size()>0 && VariablesNewCobro.saldo==0){
        
          obtieneFormaPagoCampaña(array,numPedVta);
          System.out.println("array::: "+array);
          if(array.size()>0){   
            for (int j = 0; j < array.size(); j++){
              numPed=((String) ((ArrayList) array.get(j)).get(0)).trim();
              codobtenido=((String) ((ArrayList) array.get(j)).get(1)).trim();
              monto1=((String) ((ArrayList) array.get(j)).get(2)).trim();
              desccamp=((String) ((ArrayList) array.get(j)).get(3)).trim();
              indAcep=((String) ((ArrayList) array.get(j)).get(4)).trim();
              codCamp=((String) ((ArrayList) array.get(j)).get(5)).trim();
              
              for (int i = 0; i < VariablesNewCobro.listDeta.size(); i++){
                maxform=VariablesNewCobro.listDeta.size();//ultima forma de pago restar vuelto
                /*codSel=((String) tblDetallePago.getValueAt(i,0)).trim(); //ASOSA
                descrip=((String) tblDetallePago.getValueAt(i,1)).trim();*/
                  
                  BeanDetaPago objx=(BeanDetaPago)VariablesNewCobro.listDeta.get(i);
                  codSel=objx.getCod_fp();
                  descrip=objx.getDesc_fp();
                
                if(VariablesNewCobro.listDeta.size()>0){
                
                  if(codSel.trim().equalsIgnoreCase(codobtenido.trim())){
                    //monto2=((String) tblDetallePago.getValueAt(i,5)).trim(); //ASOSA
                      monto2=objx.getTotal();
                    
                    System.out.println("monto pagado :"+Double.parseDouble(monto2));
                    System.out.println("monto exacto :"+Double.parseDouble(monto1));
                    
                       if(maxform==i+1){
                         System.out.println("leyendo ultima forma de pago");
                         monto2=FarmaUtility.formatNumber(Double.parseDouble(monto2)-VariablesNewCobro.saldo);
                         System.out.println("supuesto pago sin vuelto: "+monto2);
                       }
                     
                      cant=cant+1;
                      if(cant==1)
                      actualizaPedidoCupon(codCamp,numPed,"S","N");
                      else if(cant>1)
                      actualizaPedidoCupon(codCamp,numPed,"N","N");
                      
                  }else{
                   System.out.println("forma pago diferente");
                   valor=true;
                  }
                }
              }
            }
          }
          procesaCampSinFormaPago(VariablesNewCobro.numpedvta);
        }else{
         valor=true;
        }
    return valor;
    }
    
    /**
    * Se obtiene la informacion de campaña por pedido
    * */
    private void obtieneFormaPagoCampaña(ArrayList array,String vtaNumPed){
    
     String result="";
     array.clear();
        try
          {
           DBCaja.getFormaPagoCampaña(array,vtaNumPed);
          }catch(SQLException e)
          {
            //e.printStackTrace();
          log.error(null,e);
            FarmaUtility.showMessage(this,"Ocurrió un error al validar la forma de pago del pedido.\n" + e.getMessage(),null);
          }
    }
    
    /**
    * Se valida la impresion de las campañas que no tengan forma de de pago relacionadas
    * */
    private void procesaCampSinFormaPago(String vtaNumPed){
      try
         {
          DBCaja.procesaCampSinFormaPago(vtaNumPed);
          FarmaUtility.aceptarTransaccion();
         }catch(SQLException e)
         {
           //e.printStackTrace();
        log.error(null,e);
           FarmaUtility.showMessage(this,"Ocurrió un error al procesar campañas sin forma de pago.\n" + e.getMessage(),null);
         }  
    }
    
    private void cobrarPedido()
    {
         UtilityNewCobro.igualarVariables();
        System.out.println("0505050505005055050500550505050505050");
         System.out.println("CANTIDAD DE ITEMSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS: "+VariablesNewCobro.listDeta.size());
        cajita.setText(VariablesNewCobro.numpeddiario);
          DlgProcesarCobroNew dlgproc = 
              new DlgProcesarCobroNew(myParentFrame, "", true, tabla01, 
                                   lblvuelto, tabla02, cajita,VariablesNewCobro.listDeta);
         System.out.println("0606060600606060606006060606060060600606060");
          dlgproc.setVisible(true);
          if (!FarmaVariables.vAceptar) {
              VariablesNewCobro.listDeta.clear();
              if (VariablesCaja.vCierreDiaAnul) {
                  anularAcumuladoCanje();
                  //HOLA
                  VariablesCaja.vCierreDiaAnul = false;
                  //ADIOS
              }
          }
         if(enviaCorreo.equalsIgnoreCase("S")) enviaCorreo(); //ASOSA, 26.05.2010
          /* Cierra la conexion si se utilizo credito */
         /* if (VariablesCaja.usoConvenioCredito.equalsIgnoreCase("S")) { //ASOSA, lo comento debido a que es para convenio credito
              FarmaConnection.closeConnection();
              FarmaConnection.anularConnection();
          }
        */
     }
    
    private void pedidoCobrado(){
    
          if(VariablesCaja.vIndPedidoCobrado){
                  log.info("pedido cobrado !");
              if ( FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_MULTIFUNCIONAL) && 
                          indCerrarPantallaCobrarPed ) {
                  //Se valida ingreso de sobre
                   System.out.println("VariablesCaja.vSecMovCaja-->"+VariablesCaja.vSecMovCaja);
                      if(validaIngresoSobre()){
                          //dubilluz 20.07.2010
                          //if(FarmaUtility.rptaConfirmDialog(this, "Existe efectivo suficiente. Desea ingresar sobres en su turno?")){
                          if(FarmaUtility.rptaConfirmDialog(this, "Ha excedido el importe máximo de dinero en su caja. \n" + 
                                                                  "Desea hacer entrega de un nuevo sobre?\n")){

                              mostrarIngresoSobres(); 
                          }
                      }
                  cerrarVentana(true);
              }
              limpiarDatos(); //ASOSA, a lo mejor se tendra que hacer algo parecido pero con mis variabes
              //limpiarPagos(); ASOSA
              //limpiaVariablesVirtuales(); ASOSA
              //FarmaUtility.moveFocus(txtNroPedido); ASOSA
              System.out.println("-********************LIMPIANDO VARIABLES***********************-");
          }
          
    }
    
    /**
     * Se valida el ingreso de sobre en local
     * */
    private boolean validaIngresoSobre() {
      boolean valor=false;
      String ind="";
      try
           {
           System.out.println("VariablesCaja.vSecMovCaja-->"+VariablesCaja.vSecMovCaja);
            ind=DBCaja.permiteIngreSobre(VariablesCaja.vSecMovCaja);
               System.out.println("indPermiteSobre-->"+ind);
            if(ind.trim().equalsIgnoreCase("S")){
             valor=true;
             }
            
           }catch (SQLException sql){
             valor=false;
             sql.printStackTrace();
             FarmaUtility.showMessage(this,"Ocurrió un error validar ingreso de sobre.\n"+sql.getMessage(),null);
           }
      return valor;
    }
    
    /**
     * Se da la opcion de ingresar sobre 
     * */
    private void mostrarIngresoSobres(){
        DlgIngresoSobre dlgsobre = new DlgIngresoSobre(myParentFrame,"",true);
        dlgsobre.setVisible(true);
        if(FarmaVariables.vAceptar){
             cerrarVentana(true); 
        }
    }
    
    private void limpiarDatos(){
      VariablesCaja.vIndPedidoSeleccionado = "N";
      VariablesCaja.vIndTotalPedidoCubierto = false;
      VariablesCaja.vIndPedidoCobrado = false;
      VariablesCaja.vIndPedidoConProdVirtual = false;
      VariablesCaja.vIndPedidoConvenio = "";
      VariablesConvenio.vCodCliente = "";
      VariablesConvenio.vCodConvenio = "" ;
      VariablesConvenio.vCodCliente = "" ; 
      VariablesConvenio.vValCredDis = 0.00;
    }

    private void jButtonLabel3_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void jButtonLabel3_actionPerformed(ActionEvent e) {
        if(txttarj.getText().length()==0) txttarj.setText(ConstantsCaja.msgIngreso);
        if(txtsol.getText().equalsIgnoreCase(ConstantsCaja.msgIngreso)) txtsol.setText("");
        if(txtdol.getText().equalsIgnoreCase(ConstantsCaja.msgIngreso)) txtdol.setText("");
        txttarj.setText(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(""+(VariablesNewCobro.montoTotal-VariablesNewCobro.montoSoles-VariablesNewCobro.montoDolares))));
        //FarmaUtility.moveFocus(txttarj);
        reseteaComboMoneda();
    }

    private void jButtonLabel1_actionPerformed(ActionEvent e) {
        if(txtsol.getText().length()==0) txtsol.setText(ConstantsCaja.msgIngreso);
        if(txtdol.getText().equalsIgnoreCase(ConstantsCaja.msgIngreso)) txtdol.setText("");
        if(txttarj.getText().equalsIgnoreCase(ConstantsCaja.msgIngreso)) txttarj.setText("");
        FarmaUtility.moveFocus(txtsol);
    }

    private void txttarj_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txttarj,e);
    }

    private void cmbMoneda_keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(cmbMoneda.getSelectedIndex()==0){
                moneda="S";
            }else if(cmbMoneda.getSelectedIndex()==1){
                VariablesNewCobro.montoTarj_02=FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(Double.parseDouble(txttarj.getText())/Double.parseDouble(lbltipcambio.getText())));
                montoAntesTarjeta=VariablesNewCobro.montoTarj_02;
                double monto=FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(VariablesNewCobro.montoTarj_02*FarmaUtility.getDecimalNumber(lbltipcambio.getText())));
                while(monto<FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(VariablesNewCobro.montoTotal-VariablesNewCobro.montoSoles-VariablesNewCobro.montoDolares))){ //ASOSA, 26.05.2010
                    VariablesNewCobro.montoTarj_02=VariablesNewCobro.montoTarj_02+incrTarjDol;
                    monto=FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(VariablesNewCobro.montoTarj_02*FarmaUtility.getDecimalNumber(lbltipcambio.getText())));
                    enviaCorreo="S";
                }                
                
                txttarj.setText(""+VariablesNewCobro.montoTarj_02);                
                moneda="D";
                
                //monto<=FarmaUtility.getDecimalNumber(FarmaUtility.formatNumber(VariablesNewCobro.montoTotal+0.1-VariablesNewCobro.montoSoles-VariablesNewCobro.montoDolares
            }
            FarmaUtility.moveFocus(txttarj);
        }else{
            chkkeyPressed(e);
        }
    }
    
    private void reseteaComboMoneda(){
        moneda="S";
        cmbMoneda.setSelectedIndex(0);
        FarmaUtility.moveFocus(cmbMoneda);
    }
    
    /**
     * Setea el monto maximo de diferencia y retorna el incremento
     * @author ASOSA
     * @since 26.05.2010
     * @return
     */
    private void getIncremento(){
        String ret="";
        try{
            ret=DBCaja.getPVTA_F_GET_TOLERA_DOLARES();
        }catch(SQLException e){
            e.printStackTrace();
            FarmaUtility.showMessage(this,"ERROR en obtener mayor diferencia",cmbMoneda);
        }
        String[] data=ret.split(";");
        incrTarjDol=Double.parseDouble(data[1]);
        mayDifOpenTarj=Double.parseDouble(data[0]);
    }
    
    private void enviaCorreo(){
        String monedaUsadaTarj=(moneda.equals("S"))?"Soles":"Dolares";
        try{
            DBCaja.enviarEmailDiferXRedondeo(VariablesNewCobro.numpedvta.toString(),
                                             FarmaUtility.formatNumber(VariablesNewCobro.montoSoles),
                                             FarmaUtility.formatNumber(VariablesNewCobro.montoDolares),
                                             FarmaUtility.formatNumber(montoAntesTarjeta)+" "+monedaUsadaTarj,
                                             FarmaUtility.formatNumber(VariablesNewCobro.montoTarj_02)+" "+monedaUsadaTarj,
                                             FarmaUtility.formatNumber(VariablesNewCobro.montoTarj_02-montoAntesTarjeta)+" "+monedaUsadaTarj);
        }catch(SQLException e){
            e.printStackTrace();
            FarmaUtility.showMessage(this,"ERROR en enviar correo de diferencias por redondeo",null);
        }
        enviaCorreo="N";
    }
    
}
