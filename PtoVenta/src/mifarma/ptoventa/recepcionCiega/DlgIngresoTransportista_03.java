package mifarma.ptoventa.recepcionCiega;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FocusTraversalPolicy;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.DlgListaMaestros;
import mifarma.ptoventa.recepcionCiega.reference.ConstantsRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.DBRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.UtilityRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.VariablesRecepCiega;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import oracle.jdeveloper.layout.XYConstraints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2010 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgDatosTransportista_02.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ASOSA 05.04.2010 Creación<br>
 * <br>
 *
 * @author ALFREDO SOSA DORDAN<br>
 * @version 1.0<br>
 *
 * RECEP_BULTOS
 */
public class DlgIngresoTransportista_03 extends JDialog {
    /* ********************************************************************** */
    /* DECLARACION PROPIEDADES */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgIngresoTransportista_03.class);

    private JPanel jContentPane = new JPanel();
    private String vMotivoNoDevolucion = "";
    Frame myParentFrame;
    private int vEstVigencia;
    private String FechaInicio = "";

    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JTextFieldSanSerif txtPrecintos = new JTextFieldSanSerif();
    private boolean vIngresoHojaRes = false;

    // adcionado 15042014


    private JButtonLabel lblNombrePersonal = new JButtonLabel();

    


    private JLabelFunction lblEsc = new JLabelFunction();

    private JLabelFunction lblF11 = new JLabelFunction();
    
    private JLabelFunction lblF5 = new JLabelFunction();
    private JLabelFunction lblF1_R = new JLabelFunction();

    private BorderLayout borderLayout1 = new BorderLayout();

    private JLabelWhite lblCodPromocion = new JLabelWhite();
    private JLabelOrange jLabelOrange2 = new JLabelOrange();
    
    private JButtonLabel lblGlosa = new JButtonLabel();

    //INI ASOSA - 21/07/2014
    private JButtonLabel lblHojaRes = new JButtonLabel();
    private JButtonLabel lblCodValija = new JButtonLabel();
    
    
    private final int COL_ORD_LISTA = 1;
    private FarmaTableModel tblmBRecepcionadas;
    private FarmaTableModel tblmBDevueltas;    
    
    private boolean flagExisteHojaResumen = false;
    private String nroHojaRes = "";
    private String cantBandejas = "";
    private int cont = 0;
    //FIN ASOSA - 25/07/2014

    /*private JTextFv txtGlosa = new JTextFv("",100);    
    private JTextFv txtNombre = new JTextFv("",100);    
    private JTextFv txtPlaca = new JTextFv("",100);    
    private JTextFv txtHojaRes = new JTextFv("",100);    
    private JTextFv txtcodtrans = new JTextFv("",100);    
    private JTextFv txtnomtrans = new JTextFv("",100);    
    private JTextFv txtCodValija = new JTextFv("",100);*/
    private JTextFieldSanSerif txtGlosa = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtNombre = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtPlaca = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtHojaRes = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtcodtrans = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtnomtrans = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtCodValija = new JTextFieldSanSerif();
    
    private JButtonLabel jButtonLabel1 = new JButtonLabel();
    
    
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblBandejasRecepcionadas = new JTable();
    

    private JPanelTitle jPanelTitle2 = new JPanelTitle();
    private JLabelWhite jLabelWhite2 = new JLabelWhite();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JTable tblBandejasDevueltas = new JTable();


    private int MAX_DIGITOS = 10;
    private int LENGTH_COD_VALIJA_01 = 13;
    private int LENGTH_COD_VALIJA_02 = 14;
    private String ABREV_MFA = "MFA";
    private String ABREV_BTL = "BTL";
    private String ABREV_FASA = "FASA";
    

    
    private JPanel jPanel1 = new JPanel();
    private JPanel jPanel2 = new JPanel();
    private JPanel jPanel3 = new JPanel();
    private JTextField txtOcultaIngreso = new JTextField();
    private JLabelFunction lblLimpiar = new JLabelFunction();
    
    private ArrayList vBandejaIn = new ArrayList();
    private ArrayList vBandejaOut = new ArrayList();
    
    private ArrayList vBorraRecep = new ArrayList();
    private ArrayList vBorraDevol = new ArrayList();
    
    private boolean pNuevo = true;
    private boolean pModificar = false;
    private boolean pVisualizar = false;
    private boolean pDevolver = false;
    
    private JPanel pnlInformativo = new JPanel();
    private JLabel lblinformativo = new JLabel();
    
    private String pNumRecep = "X";
    
    public boolean vIsLazerIngreso = false;
    double vTiempoMaquina = 400; // MILISEGUNDOS
    long tmpT1,tmpT2,OldtmpT2;    
    
    ArrayList vListLazerRecep = new ArrayList(); 
    ArrayList vListLazerDevol = new ArrayList(); 
    
    
    String vHojaVisualiza =""; 
    
    /* ********************************************************************** */
    /* CONSTRUCTORES */
    /* ********************************************************************** */

    public DlgIngresoTransportista_03() {
        this(null, "", false);
    }

    public DlgIngresoTransportista_03(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();

        } catch (Exception e) {
            log.error("", e);
        }

    }

    /* ********************************************************************** */
    /* METODO JBINIT */
    /* ********************************************************************** */

    private void jbInit() throws Exception {

        ///////////////////////////////////////////////////////////
        this.getContentPane().setFocusTraversalPolicyProvider(true);
        this.getContentPane().setFocusTraversalPolicy(new FocusTraversalPolicy() 
                                                         {
          public Component getLastComponent(Container aContainer) {
             return null;
          }
          public Component getFirstComponent(Container aContainer) {
             return null;
          }
          public Component getDefaultComponent(Container aContainer) {
             return null;
          }
          public Component getComponentBefore(Container aContainer,
                       Component aComponent) {
             return null;
          }
          public Component getComponentAfter(Container aContainer,
                       Component aComponent) {
             return null;
          }
        });            
        ///////////////////////////////////////////////////////////
        this.setSize(new Dimension(798, 471));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Formulario de Ingreso Datos Transportista ");
        this.setFont(new Font("SansSerif", 0, 11));
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setBackground(Color.white);
        jContentPane.setFont(new Font("SansSerif", 0, 11));
        jContentPane.setLayout(null);
        jContentPane.setSize(new Dimension(470, 236));
        pnlTitle1.setBounds(new Rectangle(5, 10, 785, 430));
        pnlTitle1.setBackground(Color.white);
        pnlTitle1.setLayout(null);
        pnlTitle1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        /*
        txtPrecintos.setLengthText(6);
        txtPrecintos.setBounds(new Rectangle(155, 130, 135, 20));
        txtPrecintos.addKeyListener(new KeyAdapter() {

                    public void keyTyped(KeyEvent e) {
                        txtPrecintos_keyTyped(e);
                    }

                    public void keyPressed(KeyEvent e) {
                        txtPrecintos_keyPressed(e);
                    }
                });
        */
        lblNombrePersonal.setText("<html>Nombre Personal Transporte</html>");
        lblNombrePersonal.setForeground(new Color(255, 130, 14));
        lblNombrePersonal.setMnemonic('N');
        lblNombrePersonal.setBounds(new Rectangle(10, 35, 105, 35));
        //txtNombre.setLengthText(99);
        txtNombre.setBounds(new Rectangle(120, 45, 280, 20));
        txtNombre.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                    txtNombre_keyPressed(e);
                }

            public void keyTyped(KeyEvent e) {
                txtNombre_keyTyped(e);
            }
        });
        txtNombre.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    txtNombre_mouseClicked(e);
                }
            });
        lblEsc.setText("[ESC] Cerrar");
        lblEsc.setBounds(new Rectangle(680, 370, 90, 35));
        lblF11.setText("[F11] Aceptar");
        lblF11.setBounds(new Rectangle(540, 370, 90, 35));
        
        //lblF1.setText("[F5] Devolver Bandeja");
        lblF5.setText("<HTML><CENTER>[F5] Devolver<BR>Bandeja</CENTER></HTML>");
        lblF5.setBounds(new Rectangle(200, 370, 105, 35));

        lblF1_R.setText("[F1] Recepci\u00f3n Bandeja");
        lblF1_R.setText("<HTML><CENTER>[F1] Recepci\u00f3n<BR>Bandeja</CENTER></HTML>");
        lblF1_R.setBounds(new Rectangle(30, 370, 110, 35));


        lblCodPromocion.setText("[CodPromocion]");
        lblCodPromocion.setForeground(new Color(255, 130, 14));
        lblCodPromocion.setRequestFocusEnabled(false);
        lblCodPromocion.setFocusable(false);
        lblCodPromocion.setVisible(false);
        lblCodPromocion.setBounds(new Rectangle(0, 10, 105, 15));
        //--Se cambio el tamaño de digitos
        //  12.09.2008 Dubilluz
        jLabelOrange2.setText("Placa Unidad :");
        jLabelOrange2.setBounds(new Rectangle(10, 55, 125, 20));
        jLabelOrange2.setForeground(SystemColor.window);
        txtPlaca.setBounds(new Rectangle(125, 55, 240, 20));
        
        //txtPlaca.setLengthText(8);
        txtPlaca.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtPlaca_keyPressed(e);
                }
            });
        txtPlaca.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    txtPlaca_mouseClicked(e);
                }
            });
        lblGlosa.setText("Glosa :");
        lblGlosa.setBounds(new Rectangle(10, 80, 75, 15));
        lblGlosa.setForeground(new Color(255, 130, 14));
        //INI ASOSA - 21/07/2014
        lblHojaRes.setText("Nro de Hoja Resumen :");
        lblHojaRes.setBounds(new Rectangle(10, 10, 135, 15));
        lblHojaRes.setForeground(new Color(255, 130, 14));
        lblHojaRes.setMnemonic('H');
        lblHojaRes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblHojaRes_actionPerformed(e);
            }
        });


        lblCodValija.setText("Código de Valija devuelta :");
        lblCodValija.setBounds(new Rectangle(10, 20, 165, 15));
        lblCodValija.setForeground(new Color(255, 130, 14));
        lblCodValija.setMnemonic('V');
        lblCodValija.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblCodValija_actionPerformed(e);
            }
        });

        txtHojaRes.setBounds(new Rectangle(140, 10, 260, 20));
        txtHojaRes.setLengthText(10);
        txtHojaRes.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                    txtHojaRes_keyPressed(e);
                }

                public void keyTyped(KeyEvent e) {
                    txtHojaRes_keyTyped(e);
                }
            });

        txtHojaRes.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                    txtHojaRes_focusLost(e);
                }

            });
        txtHojaRes.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    txtHojaRes_mouseClicked(e);
                }
            });
        jScrollPane1.getViewport();
        jPanel1.add(txtnomtrans, null);
        jPanel1.add(txtcodtrans, null);
        jPanel1.add(jButtonLabel1, null);
        jPanel1.add(txtPlaca, null);
        jPanel1.add(jLabelOrange2, null);
        jScrollPane1.getViewport().add(tblBandejasRecepcionadas, null);
        jPanel2.add(jScrollPane1, null);
        jPanelTitle1.add(jLabelWhite1, null);
        jPanel2.add(jPanelTitle1, null);
        pnlInformativo.add(lblinformativo, null);
        pnlInformativo.setVisible(false);
        pnlTitle1.add(pnlInformativo, null);
        pnlTitle1.add(txtOcultaIngreso, null);
        pnlTitle1.add(jPanel3, null);
        pnlTitle1.add(jPanel2, null);
        pnlTitle1.add(jPanel1, null);
        pnlTitle1.add(txtGlosa, null);
        pnlTitle1.add(lblGlosa, null);
        //pnlTitle1.add(jScrollPane1, new XYConstraints(115, 15, 195, 20));
        tblBandejasRecepcionadas.setFont(new Font("SansSerif", 0, 12));
        tblBandejasRecepcionadas.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    tblBandejasRecepcionadas_mouseClicked(e);
                }
            });
        tblBandejasDevueltas.setFont(new Font("SansSerif", 0, 12));
        //FIN ASOSA - 25/07/2014
        tblBandejasDevueltas.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    tblBandejasDevueltas_mouseClicked(e);
                }
            });
        txtGlosa.setBounds(new Rectangle(55, 80, 345, 20));
        //txtGlosa.setLengthText(2000);
        txtGlosa.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtGlosa_keyPressed(e);
                }
            });
        txtGlosa.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    txtGlosa_mouseClicked(e);
                }
            });
        jButtonLabel1.setText("Empresa de Transporte:");
        jButtonLabel1.setBounds(new Rectangle(10, 15, 140, 15));
        jButtonLabel1.setMnemonic('e');
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
        txtcodtrans.setBounds(new Rectangle(150, 15, 35, 20));
        txtcodtrans.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtcodtrans_keyPressed(e);
                }
            });
        txtcodtrans.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    txtcodtrans_mouseClicked(e);
                }
            });
        txtnomtrans.setBounds(new Rectangle(190, 15, 175, 20));
        txtnomtrans.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtnomtrans_keyPressed(e);
                }
            });

        txtnomtrans.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    txtnomtrans_mouseClicked(e);
                }
            });
        jScrollPane1.setBounds(new Rectangle(10, 40, 355, 150));
        jPanelTitle1.setBounds(new Rectangle(10, 20, 355, 20));
        jLabelWhite1.setText("Lista de Bandejas / Bultos Recibidos");
        jLabelWhite1.setBounds(new Rectangle(5, 5, 370, 15));

        jScrollPane2.setBounds(new Rectangle(10, 65, 355, 120));
        jPanelTitle2.setBounds(new Rectangle(10, 45, 355, 20));
        jLabelWhite2.setText("Lista de Bandejas / Bultos Devueltos");
        jLabelWhite2.setBounds(new Rectangle(5, 5, 345, 15));


        jScrollPane2.getViewport().add(tblBandejasDevueltas, null);
        jPanel3.add(jScrollPane2, null);
        jPanelTitle2.add(jLabelWhite2, null);
        jPanel3.add(jPanelTitle2, null);
        jPanel3.add(txtCodValija, null);
        jPanel3.add(lblCodValija, null);
        jPanel3.setBorder(BorderFactory.createTitledBorder("Devolución"));
        jPanel3.setForeground(new Color(214, 107, 0));
        jPanel3.setBackground(SystemColor.window);

        txtCodValija.setBounds(new Rectangle(165, 20, 185, 20));
        txtCodValija.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtCodValija_keyPressed(e);
                }

                public void keyTyped(KeyEvent e) {
                    txtCodValija_keyTyped(e);
                }
            });
        //txtCodValija.setLengthText(14);
        txtCodValija.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    txtCodValija_mouseClicked(e);
                }
            });
        jPanel1.setBounds(new Rectangle(405, 10, 375, 90));
        jPanel1.setLayout(null);
        jPanel1.setBorder(BorderFactory.createLineBorder(SystemColor.windowText, 1));
        jPanel1.setBackground(new Color(0, 132, 0));
        //INI ASOSA - 21/07/2014
        //FIN ASOSA - 25/07/2014
        jPanel2.setBounds(new Rectangle(15, 115, 385, 250));
        jPanel2.setLayout(null);
        jPanel2.setBorder(BorderFactory.createTitledBorder("Recepción"));
        jPanel2.setForeground(new Color(214, 107, 0));
        jPanel2.setBackground(SystemColor.window);
        jPanel3.setBounds(new Rectangle(405, 115, 375, 250));
        jPanel3.setLayout(null);
        txtOcultaIngreso.setBounds(new Rectangle(140, 10, 260, 20));
        txtOcultaIngreso.setText("*************************");
        txtOcultaIngreso.setVisible(false);
        lblLimpiar.setText("[F9] Limpiar");
        lblLimpiar.setBounds(new Rectangle(375, 370, 100, 35));
        pnlInformativo.setBounds(new Rectangle(20, 310, 755, 40));
        pnlInformativo.setLayout(null);
        pnlInformativo.setBackground(new Color(198, 0, 0));
        lblinformativo.setText("<HTML><left>mensaje de alerta para que el vendedor lea</left></HTML>");
        lblinformativo.setBounds(new Rectangle(5, 5, 745, 30));
        lblinformativo.setForeground(SystemColor.window);
        lblinformativo.setFont(new Font("Tahoma", 1, 11));
        pnlTitle1.add(lblHojaRes, null);
        pnlTitle1.add(txtHojaRes, null);
        pnlTitle1.add(lblCodPromocion, new XYConstraints(0, 10, 105, 15));
        /*
        pnlTitle1.add(txtPrecintos, new XYConstraints(115, 70, 195, 20));
        */
        // AAMPUERO 14.04.2014
        pnlTitle1.add(lblNombrePersonal, new XYConstraints(15, 20, 90, 15));
        pnlTitle1.add(txtNombre, new XYConstraints(115, 15, 195, 20));
        pnlTitle1.add(lblEsc, null);
        pnlTitle1.add(lblF11, null);
        pnlTitle1.add(lblF5, null);
        pnlTitle1.add(lblF1_R, null);
        pnlTitle1.add(lblLimpiar, null);
        jContentPane.add(pnlTitle1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    /* ********************************************************************** */
    /* METODO INITIALIZE */
    /* ********************************************************************** */

    private void initialize() {
        //lblDescLocal.setText(FarmaVariables.vCodLocal.trim()+" - "+FarmaVariables.vDescCortaLocal.trim());
        //jquispe 05.05.2010 lee las veces que imprimira un voucher
        initTableListaBandejas(); //ASOSA - 21/07/2014
        VariablesRecepCiega.vNumImpresiones = UtilityRecepCiega.getNumImpresiones();
        cargarFecha();
    }

    /* ********************************************************************** */
    /* METODO DE INICIALIZACION */
    /* ********************************************************************** */

    /* ********************************************************************** */
    /* METODOS DE EVENTOS */
    /* ********************************************************************** */

    
    
    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        if(isPVisualizar()){
          cargaVisualizar(getVHojaVisualiza());
        }
        else
        {
            if(isPDevolver()){
                limpiaDatos();
                setFormDevolver();
                validaLoginQFLocal();
                limpiaDatos();
                setFormDevolver();
                //txtHojaRes.cambioTexto("Escriba aqui ...");
                FarmaUtility.moveFocus(txtHojaRes);
            }
            else{
            limpiaDatos();
            validaLoginQFLocal();
            limpiaDatos();
            //txtHojaRes.cambioTexto("Escriba aqui ...");
            FarmaUtility.moveFocus(txtHojaRes);
            }
       }
    }

    private void initTableListaBandejas() {
        tblmBRecepcionadas =
                new FarmaTableModel(ConstantsRecepCiega.columnsListaBandejas, ConstantsRecepCiega.defaultValuesListaBandejas,
                                    0);

        FarmaUtility.initSimpleList(tblBandejasRecepcionadas, tblmBRecepcionadas, ConstantsRecepCiega.columnsListaBandejas);
        
        tblmBDevueltas =
                new FarmaTableModel(ConstantsRecepCiega.columnsListaBandejas, ConstantsRecepCiega.defaultValuesListaBandejas,
                                    0);

        FarmaUtility.initSimpleList(tblBandejasDevueltas, tblmBDevueltas, ConstantsRecepCiega.columnsListaBandejas);
        
        
        tblBandejasDevueltas.setName(ConstantsRecepCiega.NAME_TABLA_BANDEJAS);
        if (tblmBDevueltas.getRowCount() > 0)
            FarmaUtility.ordenar(tblBandejasDevueltas, tblmBDevueltas, COL_ORD_LISTA, FarmaConstants.ORDEN_ASCENDENTE);
        
        
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ********************************************************************** */
    /* METODOS AUXILIARES */
    /* ********************************************************************** */

    private void chkKeyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F11(e)) {
            //obtiene datos
            if(!isPVisualizar())
            {
            if (datosValidados()
                ) 
            {   
                String pMensaje = "";
                if(isPDevolver())
                    pMensaje = "¿Está seguro que desea grabar la devolución?";
                else
                    pMensaje = "¿Está seguro que desea grabar la recepción?";
                    
                if (JConfirmDialog.rptaConfirmDialog(this, pMensaje)) {
                    grabaFormularioTransportista();
                }else
                    FarmaUtility.moveFocus(txtHojaRes);
            }
            }
            else{
                FarmaUtility.showMessage(this,"Acción no permitida porque esta en modo de visualización",txtHojaRes);
            }
        }else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if(isPVisualizar())
                cerrarVentana(false);
            else    
            if(JConfirmDialog.rptaConfirmDialogDefaultNo(this, "¿Está seguro que desea salir y no grabar ningún cambio ingresado?")) {
                cerrarVentana(false);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F1&&!isPVisualizar()&&!isPDevolver()) { 
            ingresoBandeja("R");
        } else if (e.getKeyCode() == KeyEvent.VK_F5&&!isPVisualizar()) { 
            if(isPDevolver())
               ingresoDevolucion();
            else
               ingresoBandeja("D");
        } else if (e.getKeyCode() == KeyEvent.VK_F9&&!isPVisualizar()) { 
            if(JConfirmDialog.rptaConfirmDialogDefaultNo(this, "¿Está seguro que desea borrar y no grabar ningún cambio?")) 
            reiniciar();
        }
    }

    /**
     * Obtener array de las bandejas agregadas para la hoja de resumen.
     * @author ASOSA
     * @since 25/07/2014
     * @return
     */
    private ArrayList obtenerArrayDeBandejasRecepcionadas() {
        ArrayList list = new ArrayList();
        for (int c = 0; c < tblmBRecepcionadas.getRowCount(); c++) {
            String codigo = FarmaUtility.getValueFieldArrayList(tblmBRecepcionadas.data, c, 0).toString().trim();
            if(codigo.trim().length()>0)
             list.add(codigo);
            
            codigo = FarmaUtility.getValueFieldArrayList(tblmBRecepcionadas.data, c, 1).toString().trim();
                        if(codigo.trim().length()>0)
                         list.add(codigo);
            
            codigo = FarmaUtility.getValueFieldArrayList(tblmBRecepcionadas.data, c, 2).toString().trim();
                        if(codigo.trim().length()>0)
                         list.add(codigo);
        }
        return list;
    }
    
    private ArrayList obtenerArrayDeBandejasDevueltas() {
        ArrayList list = new ArrayList();
        for (int c = 0; c < tblmBDevueltas.getRowCount(); c++) {
            String codigo = FarmaUtility.getValueFieldArrayList(tblmBDevueltas.data, c, 0).toString().trim();
            list.add(codigo);
            
            codigo = FarmaUtility.getValueFieldArrayList(tblmBDevueltas.data, c, 1).toString().trim();
                        if(codigo.trim().length()>0)
                         list.add(codigo);
            
            codigo = FarmaUtility.getValueFieldArrayList(tblmBDevueltas.data, c, 2).toString().trim();
                        if(codigo.trim().length()>0)
                         list.add(codigo);            
        }
        return list;
    }

    private ArrayList getListaBorraRecep() {
        ArrayList list = new ArrayList();
        for (int c = 0; c < vBorraRecep.size(); c++) {
            String codigo = FarmaUtility.getValueFieldArrayList(vBorraRecep, c, 0).toString().trim();
            if(codigo.trim().length()>0)
             list.add(codigo);
        }
        return list;
    }
    
    private ArrayList getListaBorraDevol() {
        ArrayList list = new ArrayList();
        for (int c = 0; c < vBorraDevol.size(); c++) {
            String codigo = FarmaUtility.getValueFieldArrayList(vBorraDevol, c, 0).toString().trim();
            if(codigo.trim().length()>0)
             list.add(codigo);
        }
        return list;
    }

    private void getDatosTransportista() {

        VariablesRecepCiega.vNombreTrans = txtNombre.getText().trim();

        VariablesRecepCiega.vPlacaUnidTrans = txtPlaca.getText().trim();
        VariablesRecepCiega.vCantBultos = "";
        VariablesRecepCiega.vCantBandejas = "";
        VariablesRecepCiega.vGlosa = txtGlosa.getText().trim();
        log.debug("VariablesRecepCiega.vNombreTrans " + VariablesRecepCiega.vNombreTrans);
        log.debug("VariablesRecepCiega.vHoraTrans " + VariablesRecepCiega.vHoraTrans);
        log.debug("VariablesRecepCiega.vPlacaUnidTrans " + VariablesRecepCiega.vPlacaUnidTrans);
        log.debug("VariablesRecepCiega.vCantBultos " + VariablesRecepCiega.vCantBultos);
        log.debug("VariablesRecepCiega.vCantPrecintos " + VariablesRecepCiega.vCantPrecintos);
    }


    /**
     * Se valida ingreso de quimico (Administrador Local)
     */
    private void validaLoginQFLocal() {
        //se guarda datos
        VariablesRecepCiega.vNuSecUsu = FarmaVariables.vNuSecUsu;
        VariablesRecepCiega.vIdUsu = FarmaVariables.vIdUsu;
        VariablesRecepCiega.vNomUsu = FarmaVariables.vNomUsu;
        VariablesRecepCiega.vPatUsu = FarmaVariables.vPatUsu;
        VariablesRecepCiega.vMatUsu = FarmaVariables.vMatUsu;

        DlgLogin dlgLogin = new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
        dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
        dlgLogin.setVisible(true);

        if (FarmaVariables.vAceptar) {

            if (!FarmaVariables.vNuSecUsu.equalsIgnoreCase(VariablesRecepCiega.vNuSecUsu)) {
                FarmaUtility.showMessage(this, "El usuario no es el mismo al logueado anteriormente. Verificar!!!",
                                         txtNombre);
                cerrarVentana(false);
            }

            // se sete datos originales
            FarmaVariables.vNuSecUsu = VariablesRecepCiega.vNuSecUsu;
            FarmaVariables.vIdUsu = VariablesRecepCiega.vIdUsu;
            FarmaVariables.vNomUsu = VariablesRecepCiega.vNomUsu;
            FarmaVariables.vPatUsu = VariablesRecepCiega.vPatUsu;
            FarmaVariables.vMatUsu = VariablesRecepCiega.vMatUsu;

            FarmaVariables.dlgLogin = dlgLogin;
            FarmaVariables.vAceptar = false;
        } else
            cerrarVentana(false);
    }



    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ********************************************************************** */
    /* METODOS DE LOGICA DE NEGOCIO */
    /* *********************************************************************** */


    private boolean vValidaBandejaRecepcionada(){
        boolean vRes = false;
        DlgDiferenciasBandejas dlgDif = new DlgDiferenciasBandejas(myParentFrame,"",true);
        dlgDif.cargaTablasDiferencias(vBandejaIn, tblmBRecepcionadas.data);
        dlgDif.setVisible(true);
        if (FarmaVariables.vAceptar) {
            vRes = true;
            //confirma diferencias de bandejas respecto a la hoja resumen
        }
        else{
            FarmaUtility.showMessage(this,"No se grabará la recepción porque no aceptó las diferencias indicadas.\n" +
                "Verifique los datos ingresados.\n" +
                "",txtHojaRes);
            vRes = false;
        }
        
        return vRes;
    }
    
    
    private boolean vValidaBandejaDevuelta(){
        boolean vRes = false;
        vMotivoNoDevolucion = "";
        if(tblmBDevueltas.data.size()==0){
            DlgMotivoNoDevBandeja dlgMotivo = new DlgMotivoNoDevBandeja(myParentFrame,"",true);
            dlgMotivo.setVisible(true);
            String pCodMotivo = dlgMotivo.getPCodMotivo();
            String pMotivo = dlgMotivo.getPMotivo();
            vMotivoNoDevolucion = pMotivo;
            if(FarmaVariables.vAceptar){
                vRes = true;
            }
            else{
                vRes = false;
            }
        }else
            vRes = true;
        
        return vRes;
    }

    private void cargarFecha() {
        try {
            FechaInicio = FarmaSearch.getFechaHoraBD(1);
        } catch (SQLException sql) {
            log.error("", sql);
        }
    }


    private void txtNombre_keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER&&!isPVisualizar()) {
            FarmaUtility.moveFocus(txtGlosa);
        }
        chkKeyPressed(e);
    }

    
    private void txtNombre_keyTyped(KeyEvent e) {

        FarmaUtility.admitirLetras(txtNombre, e);
    }

    private void txtGlosa_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER&&!isPVisualizar()) {
                if(isPDevolver()){
                    FarmaUtility.moveFocus(txtcodtrans);
                }
                else
                   FarmaUtility.moveFocus(txtHojaRes);
        }
        chkKeyPressed(e);
    }

    private void txtHojaRes_keyPressed(KeyEvent e) {
        
        setIsLazerTeclado(e,txtHojaRes);
        
        if (e.getKeyCode() == KeyEvent.VK_ENTER&&!isPVisualizar()) 
        {
            txtHojaRes.setText(FarmaUtility.completeWithSymbol(txtHojaRes.getText(), 10, "0", "I"));
            pIngresoHojaResumen();
        } else {
            chkKeyPressed(e);
        }
    }
    private void eliminarUltimoRegistro() {
        int cantidad = tblmBRecepcionadas.getRowCount();
        if (cantidad > 0) {
            tblmBRecepcionadas.deleteRow(cantidad - 1);
            tblBandejasRecepcionadas.repaint();
        } else {
            FarmaUtility.showMessage(this, "No hay bandejas para eliminar de la lista", null);
        }
    }

    private void limpiarLista() {
        tblmBRecepcionadas.clearTable();
        tblBandejasRecepcionadas.repaint();

        tblmBDevueltas.clearTable();
        tblBandejasDevueltas.repaint();
        
        txtcodtrans.setEditable(true);
        txtnomtrans.setEditable(true);
        txtPlaca.setEditable(true);        
        
        txtcodtrans.setText("");
        txtnomtrans.setText("");
        txtPlaca.setText("");
        
    }

    
    private boolean isValueText(String texto) {
        boolean flag = true;
        if (texto.trim().equals("")) {
            flag = false;
        }
        return flag;
    }
    
    private void txtcodtrans_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER&&!isPVisualizar()) {
            VariablesPtoVenta.vTipoMaestro = "15";
            validaCodigo(txtcodtrans, txtnomtrans, VariablesPtoVenta.vTipoMaestro);
            //}
            if (!txtcodtrans.getText().trim().equals("")) {
                FarmaUtility.moveFocus(txtPlaca);
            }
        } else {
            chkKeyPressed(e);
        }
    }

    private void validaCodigo(JTextField pJTextField_Cod, JTextField pJTextField_Desc, String pTipoMaestro) {
        if (pJTextField_Cod.getText().trim().length() > 0) {
            VariablesPtoVenta.vCodMaestro = pJTextField_Cod.getText().trim();
            ArrayList myArray = new ArrayList();
            buscaCodigoListaMaestro(myArray);

            if (myArray.size() == 0) {
                FarmaUtility.showMessage(this, "Codigo No Encontrado", pJTextField_Cod);
                FarmaVariables.vAceptar = false;
            } else if (myArray.size() == 1) {
                String codigo = ((String)((ArrayList)myArray.get(0)).get(0)).trim();
                String descripcion = ((String)((ArrayList)myArray.get(0)).get(1)).trim();
                pJTextField_Cod.setText(codigo);
                pJTextField_Desc.setText(descripcion);
                VariablesPtoVenta.vCodMaestro = codigo;
                FarmaVariables.vAceptar = true;
            } else {
                FarmaUtility.showMessage(this, "Se encontro mas de un registro.Verificar!!!", pJTextField_Cod);
                FarmaVariables.vAceptar = false;
            }
        } else {
            cargaListaMaestros(pTipoMaestro, pJTextField_Cod, pJTextField_Desc);
        }
    }

    private void buscaCodigoListaMaestro(ArrayList pArrayList) {
        try {
            DBPtoVenta.buscaCodigoListaMaestro(pArrayList, VariablesPtoVenta.vTipoMaestro,
                                               VariablesPtoVenta.vCodMaestro);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al buscar código en maestros :\n" +
                    sql.getMessage(), txtcodtrans);
        }
    }

    private void cargaListaMaestros(String pTipoMaestro, JTextField pJTextField_Cod, JTextField pJTextField_Desc) {
        VariablesPtoVenta.vTipoMaestro = pTipoMaestro;
        DlgListaMaestros dlgListaMaestros = new DlgListaMaestros(myParentFrame, "", true);
        dlgListaMaestros.setVisible(true);
        if (FarmaVariables.vAceptar) {
            pJTextField_Cod.setText(VariablesPtoVenta.vCodMaestro);
            pJTextField_Desc.setText(VariablesPtoVenta.vDescMaestro);
        }
    }

    private void jButtonLabel1_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtcodtrans);
    }

    private void jButtonLabel1_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtnomtrans_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    /**
     * Validar por ultima ves el codigo de empresa de transporte
     * @author ASOSA
     * @since 22.04.2010
     * @return
     */
    private boolean verificarCodigo() {
        ArrayList list02 = new ArrayList();
        VariablesPtoVenta.vTipoMaestro = "15";
        VariablesPtoVenta.vCodMaestro = txtcodtrans.getText().trim();
        buscaCodigoListaMaestro(list02);
        boolean flag = false;
        if (list02.size() == 0) {
            FarmaUtility.showMessage(this, "Codigo No Encontrado", txtcodtrans);
            FarmaVariables.vAceptar = false;
            flag = false;
        } else if (list02.size() == 1) {
            FarmaVariables.vAceptar = true;
            flag = true;
        } else {
            FarmaUtility.showMessage(this, "Se encontro mas de un registro.Verificar!!!", txtcodtrans);
            FarmaVariables.vAceptar = false;
            flag = false;
        }
        return flag;
    }

    private void lblHojaRes_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtHojaRes);
    }

    private void lblCodValija_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCodValija);
    }

    //INI ASOSA - 25/07/2014

    private void txtCodValija_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER&&!isPVisualizar()) {
            if (validarCodigoValija()) {
                FarmaUtility.moveFocus(txtGlosa);
            }
        } else {
            chkKeyPressed(e);
        }
    }

    private boolean validarCodigoValija() {
        boolean flag = false;
        String codigo = txtCodValija.getText().trim();
        if (codigo.length() != 0) { //si no ingrese valija paso al siguiente campo porque no es obligatorio.
            if (codigo.length() == LENGTH_COD_VALIJA_01 || codigo.length() == LENGTH_COD_VALIJA_02) {

                if (codigo.length() == LENGTH_COD_VALIJA_01) {
                    String letras = codigo.substring(0, 3);
                    String numeros = codigo.substring(3, 10);
                    log.info("letras: " + letras);
                    log.info("numeros: " + numeros);
                    if ((letras.equals(ABREV_MFA) || letras.equals(ABREV_BTL)) && (FarmaUtility.isInteger(numeros))) {
                        flag = true;
                    } else {
                        FarmaUtility.showMessage(this,
                                                 "Formato del código invalido. \nEjemplo:\n - MFA1234567890\n - BTL1234567890\n - FASA1234567890",
                                                 txtCodValija);
                    }
                }

                if (codigo.length() == LENGTH_COD_VALIJA_02) {
                    String letras = codigo.substring(0, 4);
                    String numeros = codigo.substring(4, 10);
                    log.info("letras: " + letras);
                    log.info("numeros: " + numeros);
                    if (letras.equals(ABREV_FASA) && (FarmaUtility.isInteger(numeros))) {
                        flag = true;
                    } else {
                        FarmaUtility.showMessage(this,
                                                 "Formato del código invalido. \nEjemplo:\n - MFA1234567890\n - BTL1234567890\n - FASA1234567890",
                                                 txtCodValija);
                    }
                }

            } else {
                FarmaUtility.showMessage(this,
                                         "El código de valija debe de tener " + LENGTH_COD_VALIJA_01 + " o " + LENGTH_COD_VALIJA_02 +
                                         " dígitos", txtCodValija);
            }
        } else {
            flag = true;
        }
        return flag;
    }


    private void txtCodValija_keyTyped(KeyEvent e) {
        FarmaUtility.admitirLetrasNumeros(txtCodValija, e);
    }

    //INI ASOSA - 30/07/2014


    private void limpiaDatos() {
        txtHojaRes.setText("");
        txtcodtrans.setText("");
        txtnomtrans.setText("");
        txtPlaca.setText("");
        txtNombre.setText("");
        txtGlosa.setText("");
        txtCodValija.setText("");
        
        txtcodtrans.setEnabled(false);
        txtnomtrans.setEnabled(false);
        txtPlaca.setEnabled(false);
        vBandejaIn = new ArrayList();
        vBandejaOut = new ArrayList();
        
        
        pnlInformativo.setVisible(false);
        setPNuevo(true);
        setPVisualizar(false);
        setPModificar(false);
        
        try {
            for (int i = 0; i <= tblmBDevueltas.data.size(); i++)
                tblmBDevueltas.deleteRow(0);
            tblmBDevueltas.data.clear();
        } catch (Exception e) {
            ;
        }
        
        try {
            for (int i = 0; i <= tblmBRecepcionadas.data.size(); i++)
                tblmBRecepcionadas.deleteRow(0);
            tblmBRecepcionadas.data.clear();
        } catch (Exception e) {
            ;
        }
        
        tblBandejasDevueltas.repaint();
        tblBandejasRecepcionadas.repaint();
        vIngresoHojaRes = false;
        pNumRecep = "X";
    }

    private void ingresoBandeja(String vTipo) {
        if (pIngresadoHojaRes()) {
            boolean vRecepcion = false;
            boolean vDevolucion = false;
            if (vTipo.trim().equalsIgnoreCase("R"))
                vRecepcion = true;
            else if (vTipo.trim().equalsIgnoreCase("D"))
                vRecepcion = true;
            //ACCION DE LO INGRESADO
            ArrayList vListaBandeja = new ArrayList();
            ArrayList vBorradoBandeja = new ArrayList();
            ArrayList vListLazer =  new ArrayList();
            String vCodValija = "";
            if (vTipo.trim().equalsIgnoreCase("R")) {
                DlgIngresoBandejaRecep dlgIngBandeja =
                    new DlgIngresoBandejaRecep(myParentFrame, "", true, txtHojaRes.getText());                
                dlgIngBandeja.vActivaRecepcion();
                dlgIngBandeja.setTitle("Recepción de Bandejas");
                dlgIngBandeja.lblAccion.setText("Recepción");
                dlgIngBandeja.setPNuevo(isPNuevo());
                dlgIngBandeja.setPModificar(isPModificar());
                dlgIngBandeja.setPVisualizar(isPVisualizar());
                dlgIngBandeja.setVListaBorra(vBorraRecep);
                //if(isPModificar()||isPVisualizar())
                //{
                dlgIngBandeja.setVListaIn(obtenerArrayDeBandejasRecepcionadas());
                //}
                dlgIngBandeja.setVisible(true);
                vListaBandeja = dlgIngBandeja.getVListGrabar();
                vBorradoBandeja = dlgIngBandeja.getVListaBorra();
                vListLazer = dlgIngBandeja.getListaBandejaLazer();
            } else if (vTipo.trim().equalsIgnoreCase("D")) {
                DlgIngresoBandejaDevol dlgIngBandejaDev =
                    new DlgIngresoBandejaDevol(myParentFrame, "", true, txtHojaRes.getText());                
                dlgIngBandejaDev.vActivaDevolucion();
                dlgIngBandejaDev.setTitle("Devolución de Bandejas");
                dlgIngBandejaDev.lblAccion.setText("Devolución");
                dlgIngBandejaDev.setPNuevo(isPNuevo());
                dlgIngBandejaDev.setPModificar(isPModificar());
                dlgIngBandejaDev.setPVisualizar(isPVisualizar());                
                dlgIngBandejaDev.setVListaBorra(vBorraDevol);
                //if(isPModificar()||isPVisualizar()){
                    dlgIngBandejaDev.setVListaIn(obtenerArrayDeBandejasDevueltas());
                //}                
                dlgIngBandejaDev.setVisible(true);
                vListaBandeja = dlgIngBandejaDev.getVListGrabar();
                vBorradoBandeja = dlgIngBandejaDev.getVListaBorra();
                vListLazer = dlgIngBandejaDev.getListaBandejaLazer();
                if(dlgIngBandejaDev.getPCodValija().trim().length()>0)
                 vCodValija = dlgIngBandejaDev.getPCodValija();
                else
                 vCodValija = txtCodValija.getText().trim();
            }

            if (FarmaVariables.vAceptar) {
                if (vTipo.trim().equalsIgnoreCase("R")) {
                    tblmBRecepcionadas.data.clear();
                    for (int g = 0; g < vListaBandeja.size(); g++)
                        tblmBRecepcionadas.insertRow((ArrayList)vListaBandeja.get(g));
                    tblBandejasRecepcionadas.repaint();
                    setVListLazerRecep(vListLazer);
                } else if (vTipo.trim().equalsIgnoreCase("D")) {
                    txtCodValija.setText(vCodValija);
                    txtCodValija.setEnabled(false);
                    txtCodValija.selectAll();
                    tblmBDevueltas.data.clear();
                    for (int g = 0; g < vListaBandeja.size(); g++)
                        tblmBDevueltas.insertRow((ArrayList)vListaBandeja.get(g));
                    tblBandejasDevueltas.repaint();
                    setVListLazerDevol(vListLazer);

                }
            }
        } else {
            FarmaUtility.showMessage(this, "Debe de ingresar una hoja resumen.", txtHojaRes);
        }
    }

    private void txtHojaRes_focusLost(FocusEvent e) {
       if(!vIngresoHojaRes)
         FarmaUtility.moveFocus(txtHojaRes);
    }

    private void pIngresoHojaResumen() {
        String pValorIn = txtHojaRes.getText();
        if (isValueText(txtHojaRes.getText())) {
            // 1- Valida el INGRESO si ya existia una HOJA RESUMEN
            boolean vRevIngreso = true;
            if (vIngresoHojaRes) {
                vRevIngreso =
                JConfirmDialog.rptaConfirmDialogDefaultNo(this, "La hoja de resumen ya fue ingresada, ¿Desea cambiarla, ya que debera de volver ingresar las bandejas?");
            }
            if (vRevIngreso) {                
                //limpiarLista();
                limpiaDatos();
                txtHojaRes.setText(pValorIn);
                // 2- Valida si existe la hoja de ingreso en alguna Recepcion y ya fue usada
                boolean vExisteEnRecepcion = false;
                String nroHojaRes = txtHojaRes.getText().trim();
                try {
                    vExisteEnRecepcion = UtilityRecepCiega.buscarHojaResumenGrabada(nroHojaRes,true);
                } catch (SQLException e) {
                    log.error("",e);
                }
                if (!vExisteEnRecepcion) {
                    // 3- Valida si existe la hoja de ingreso en maestro
                    boolean vExisteMaestro = false;
                    try {
                        vExisteMaestro = UtilityRecepCiega.isExisteHojaMaestro(nroHojaRes);
                    } catch (SQLException e) {
                        log.error("",e);
                    }
                    if (vExisteMaestro) {
                        vIngresoHojaRes = true;
                        //SE CONSULTAN LOS DATOS DE LA BANDEJA Y SE MUESTRAN LOS DATOS.
                        //CODIGO DE TRANSPORTISTA y NOMBRE y PLACA //
                        //Cargara en Memoria las bandejas recibidas a esa hoja de resumen.
                        try {
                            String pDataMaestroHoja[] = DBRecepCiega.vDatosHojaResumen(txtHojaRes.getText()).split("@");
                            if(pDataMaestroHoja.length==3){
                                txtcodtrans.setText(pDataMaestroHoja[0].toString().trim());
                                txtnomtrans.setText(pDataMaestroHoja[1].toString().trim());
                                txtPlaca.setText(pDataMaestroHoja[2].toString().trim());
                                //vBandejaIn
                                DBRecepCiega.getListaBandejaIn(vBandejaIn,txtHojaRes.getText());
                                txtcodtrans.setEnabled(true);
                                txtnomtrans.setEnabled(true);
                                txtPlaca.setEnabled(true);        
                                txtcodtrans.setEditable(false);
                                txtnomtrans.setEditable(false);
                                txtPlaca.setEditable(false);
                                txtcodtrans.selectAll();
                                txtnomtrans.selectAll();
                                txtPlaca.selectAll();     
                            }
                            else
                                FarmaUtility.showMessage(this, "No se pudo obtner los datos de la hoja resumen.", txtHojaRes);
                        } catch (Exception sqle) {
                            log.error("",sqle);
                        }
                        FarmaUtility.moveFocus(txtNombre);
                    } else {
                        if(UtilityRecepCiega.isValidoIngresarHojaNoExiste(txtHojaRes.getText().trim())){
                        txtOcultaIngreso.setVisible(true);
                        txtHojaRes.setVisible(false);
                        DlgReconfirmacionHojaResumen dlgRepetir = new DlgReconfirmacionHojaResumen(myParentFrame,"",true);
                        dlgRepetir.setVValorValidar(txtHojaRes.getText().trim());
                        dlgRepetir.setVisible(true);
                        if(FarmaVariables.vAceptar&&dlgRepetir.isIsValidoConfirmacion()){
                            if(isLoginQfLocal()){
                            vIngresoHojaRes = true;
                            // EL VALOR SI ES VALIDO Y CONFIRMADO POR EL QF.
                            activaIngresoManualTransportista();
                                txtOcultaIngreso.setVisible(false);
                                txtHojaRes.setVisible(true);
                            }
                            else{
                              vIngresoHojaRes = false;
                                txtOcultaIngreso.setVisible(false);
                                txtHojaRes.setVisible(true);
                              FarmaUtility.moveFocus(txtHojaRes);
                         }                        }
                        else{
                            vIngresoHojaRes=false;
                            txtOcultaIngreso.setVisible(false);
                            txtHojaRes.setVisible(true);
                            FarmaUtility.moveFocus(txtHojaRes);
                            vIsLazerIngreso = false;
                            txtHojaRes.setText("");
                            if (txtHojaRes.getText().length() == 0)
                            tmpT1 = System.currentTimeMillis();
                        }
                        }
                        else{
                            FarmaUtility.showMessage(this, "La hoja resumen ingresada no cumple con el rango permitido a ingresar.", txtHojaRes);
                            vIsLazerIngreso = false;
                            txtHojaRes.setText("");
                            if (txtHojaRes.getText().length() == 0)
                            tmpT1 = System.currentTimeMillis();
                            FarmaUtility.moveFocus(txtHojaRes);
                        }
                        
                     }
                 }
                 else {
                    //SI UNA HOJA DE RESUMEN YA EXISTE EN RECEPCION  DUBILLUZ 03.06.2015
                    //PERMITE MODIFICAR O CREA NUEVO
                    // 1 MODIFICA , 2 NUEVO
                    // 1 MODIFICA OBTENER EL NUMERO DE RECEPCION
                    // solo puede agregar no puede reducir
                    String pAccion = "";
                    try {
                        pAccion = UtilityRecepCiega.getAccionHojaExistente(nroHojaRes);
                    } catch (SQLException e) {
                        log.error("",e);
                    }
                    if(!pAccion.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)&&pAccion.trim().length()>10){
                        // TIENE cadena valida
                        String pList[] = pAccion.trim().split("@");
                        String pPaso = pList[0].trim();
                        if(pPaso.trim().equalsIgnoreCase("2")){
                            if(JConfirmDialog.rptaConfirmDialogDefaultNo(this,"La hoja resumen Nº "+nroHojaRes+ " ya está asociada a una recepción\n" +
                                                                         "¿Desea crear un nuevo ingreso de transportista porque ha recibido bandejas del mismo?")){
                                // si acepto crear una nueva
                                // primero limpiamos todo y continuamos con el ingreso de la misma.
                                limpiaDatos();
                                setPNuevo(true);
                                setPVisualizar(false);
                                setPModificar(false);
                                txtHojaRes.setText(nroHojaRes);
                                try {
                                    String pDataMaestroHoja[] = DBRecepCiega.vDatosHojaResumen(txtHojaRes.getText()).split("@");
                                    if(pDataMaestroHoja.length==3){
                                        txtcodtrans.setText(pDataMaestroHoja[0].toString().trim());
                                        txtnomtrans.setText(pDataMaestroHoja[1].toString().trim());
                                        txtPlaca.setText(pDataMaestroHoja[2].toString().trim());
                                        //vBandejaIn
                                        DBRecepCiega.getListaBandejaIn(vBandejaIn,txtHojaRes.getText());
                                        txtcodtrans.setEnabled(true);
                                        txtnomtrans.setEnabled(true);
                                        txtPlaca.setEnabled(true);        
                                        txtcodtrans.setEditable(false);
                                        txtnomtrans.setEditable(false);
                                        txtPlaca.setEditable(false);
                                        txtcodtrans.selectAll();
                                        txtnomtrans.selectAll();
                                        txtPlaca.selectAll();                                
                                    }
                                    else
                                        FarmaUtility.showMessage(this, "No se pudo obtner los datos de la hoja resumen.", txtHojaRes);
                                } catch (Exception sqle) {
                                    log.error("",sqle);
                                }
                                vIngresoHojaRes = true;
                                FarmaUtility.moveFocus(txtNombre);
                            }
                        }
                        else{
                            //ahora es accion 1 y por lo tanto podra modificar la recepcion de transportista.
                            //solo para agregar bandejas no podra modificar ningun valor
                            if(JConfirmDialog.rptaConfirmDialogDefaultNo(this,"La hoja resumen Nº "+nroHojaRes+ " ya está asociada a una recepción\n" +
                                                                            "¿Desea hacer cambios en la recepción?")){
                               limpiaDatos();
                               setPNuevo(false);
                               setPVisualizar(false);
                               setPModificar(true);
                               String pNumRecep = pList[1].trim();
                               String pFecha = pList[2].trim();
                               String pCodTransportista = pList[3].trim();
                               String pNameEmpTrans = pList[4].trim();
                               String pNombre = pList[5].trim();
                               String pGlosa = pList[6].trim();
                               String pCodValija = pList[7].trim();
                               ArrayList pListaBR = new ArrayList();
                               try {
                                    DBRecepCiega.getListaBandejasRecep(pListaBR, pNumRecep);
                                } catch (SQLException sqle) {
                                    log.error("",sqle);
                                }
                               ArrayList pListaBD = new ArrayList();
                               try {
                                    DBRecepCiega.getListaBandejasDevol(pListaBD, pNumRecep);
                                } catch (SQLException sqle) {
                                    log.error("",sqle);
                                }
                               // - load datos de modificar
                               loadRecepModifica(pNumRecep,nroHojaRes,pCodTransportista,pNameEmpTrans,
                                                 pNombre,pGlosa,pCodValija,pListaBR,pListaBD,pFecha);
                               
                           }
                        }
                    }
                    /*
                    FarmaUtility.showMessage(this, "El Nº Hoja Resumen ya fue asociada a una recepción, verifique sus datos.",
                                             txtHojaRes);*/
                }
            }
        } else {
            FarmaUtility.showMessage(this, "Debe de ingresar el número de hoja de resumen.", txtHojaRes);
        }        
    }
    
    private boolean isLoginQfLocal() {
        //se guarda datos
        VariablesRecepCiega.vNuSecUsu = FarmaVariables.vNuSecUsu;
        VariablesRecepCiega.vIdUsu = FarmaVariables.vIdUsu;
        VariablesRecepCiega.vNomUsu = FarmaVariables.vNomUsu;
        VariablesRecepCiega.vPatUsu = FarmaVariables.vPatUsu;
        VariablesRecepCiega.vMatUsu = FarmaVariables.vMatUsu;

        DlgLogin dlgLogin = new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
        dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
        dlgLogin.setVisible(true);

        if (FarmaVariables.vAceptar) {

            if (!FarmaVariables.vNuSecUsu.equalsIgnoreCase(VariablesRecepCiega.vNuSecUsu)) {
                FarmaUtility.showMessage(this, "El usuario no es el mismo al logueado anteriormente. Verificar!!!",
                                         txtNombre);
                return false;
            }

            // se sete datos originales
            FarmaVariables.vNuSecUsu = VariablesRecepCiega.vNuSecUsu;
            FarmaVariables.vIdUsu = VariablesRecepCiega.vIdUsu;
            FarmaVariables.vNomUsu = VariablesRecepCiega.vNomUsu;
            FarmaVariables.vPatUsu = VariablesRecepCiega.vPatUsu;
            FarmaVariables.vMatUsu = VariablesRecepCiega.vMatUsu;

            FarmaVariables.dlgLogin = dlgLogin;
            FarmaVariables.vAceptar = false;
        } else
            return false;
      
      return true;           
    }

    private void activaIngresoManualTransportista() {
        txtcodtrans.setEnabled(true);
        txtnomtrans.setEnabled(true);
        txtPlaca.setEnabled(true);
        FarmaUtility.moveFocus(txtcodtrans);
    }


    private void txtPlaca_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER&&!isPVisualizar()) {
            if(isPDevolver()){
                FarmaUtility.moveFocus(txtNombre);
            }
            else{
            FarmaUtility.moveFocus(txtNombre);
            txtcodtrans.setEnabled(false);
            txtnomtrans.setEnabled(false);
            txtPlaca.setEnabled(false);        
            txtcodtrans.selectAll();
            txtnomtrans.selectAll();
            txtPlaca.selectAll();
            }
        }
        chkKeyPressed(e);
    }

    private void txtHojaRes_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtHojaRes, e);
    }

    private void reiniciar() {
        if(isPDevolver()){
            limpiaDatos();
            setFormDevolver();
            validaLoginQFLocal();
            limpiaDatos();
            setFormDevolver();
            //txtHojaRes.cambioTexto("Escriba aqui ...");
            FarmaUtility.moveFocus(txtHojaRes);
        }
        else{
        limpiaDatos();
        validaLoginQFLocal();
        //limpiaDatos();
        FarmaUtility.moveFocus(txtHojaRes);
        }
    }
    
    public boolean pIngresadoHojaRes(){
        if(isPModificar()) return true;
        else
        return vIngresoHojaRes;
    }

    private boolean datosValidados() {
        boolean vresultado = true;
        if(isPDevolver()){
            if(tblBandejasDevueltas.getRowCount()==0){
                FarmaUtility.showMessage(this, "Debe de ingresar bandejas por devolver.", txtHojaRes);
                vresultado = false;
            }
        }
        else
        if(!pIngresadoHojaRes()&&!isPDevolver()){
            FarmaUtility.showMessage(this, "Debe de ingresar el número de hoja de resumen.", txtHojaRes);
            vresultado = false;
        }
        else
            if (txtcodtrans.getText().trim().length() < 1 ||
                txtnomtrans.getText().trim().length() < 1) {
                FarmaUtility.showMessage(this, "Debe ingresar el dato del transportista.", txtHojaRes);
                    vresultado = false;
                }
            else
            {
                if(txtNombre.getText().trim().length() < 1){
                    FarmaUtility.showMessage(this, "Debe ingresar el nombre del transportista.", txtNombre);
                    vresultado = false;
                }
                else{
                    if(!vValidaBandejaDevuelta()){
                        FarmaUtility.showMessage(this, "Debe seleccionar un motivo por no devolver bandejas.", txtNombre);
                        vresultado = false;    
                    }
                }
            }
        return vresultado;
    }

    private void txtNombre_mouseClicked(MouseEvent e) {
        mouseClicked(e);
    }
    
    private void mouseClicked(MouseEvent e) {
        FarmaUtility.moveFocus(txtHojaRes);
    }

    private void txtHojaRes_mouseClicked(MouseEvent e) {
        mouseClicked(e);
    }

    private void txtcodtrans_mouseClicked(MouseEvent e) {
        mouseClicked(e);
    }

    private void txtnomtrans_mouseClicked(MouseEvent e) {
        mouseClicked(e);
    }

    private void txtPlaca_mouseClicked(MouseEvent e) {
        mouseClicked(e);
    }

    private void txtGlosa_mouseClicked(MouseEvent e) {
        mouseClicked(e);
    }

    private void txtCodValija_mouseClicked(MouseEvent e) {
        mouseClicked(e);
    }

    private void tblBandejasRecepcionadas_mouseClicked(MouseEvent e) {
        mouseClicked(e);
    }

    private void tblBandejasDevueltas_mouseClicked(MouseEvent e) {
        mouseClicked(e);
    }

    private void grabaFormularioTransportista() {
        //obtiene datos transportista
        getDatosTransportista();
        try {
            ArrayList ltBandejaR = new ArrayList();
            ArrayList ltBandejaD = new ArrayList();
            ArrayList ltBorraR = new ArrayList();
            ArrayList ltBorraD = new ArrayList();
            ArrayList ltBandejaRLazer = new ArrayList();
            ArrayList ltBandejaDLazer = new ArrayList();
            ltBandejaR = obtenerArrayDeBandejasRecepcionadas();
            ltBandejaD = obtenerArrayDeBandejasDevueltas();
            ltBorraR = getListaBorraRecep();
            ltBorraD = getListaBorraDevol();
            ltBandejaRLazer = getVListLazerRecep();
            ltBandejaDLazer = getVListLazerDevol();            
            log.info("ltBandejaRLazer "+ltBandejaRLazer);
            log.info("ltBandejaDLazer "+ltBandejaDLazer);
            log.info("ltBandejaR "+ltBandejaR);
            log.info("ltBandejaD "+ltBandejaD);
            log.info("isVIsLazerIngreso "+isVIsLazerIngreso());
            String vNroRecep =
                DBRecepCiega.saveNvoRecepTransportista("0", 
                                                       txtcodtrans.getText().trim(), 
                                                       txtnomtrans.getText().trim(), 
                                                       txtHojaRes.getText().trim(),
                                                       txtCodValija.getText().trim(),
                                                       vMotivoNoDevolucion,
                                                       ltBandejaR,
                                                       ltBandejaD,
                                                       isPModificar(),
                                                       isPNuevo(),
                                                       getPNumRecep(),
                                                       ltBorraR,
                                                       ltBorraD,
                                                       ltBandejaRLazer,
                                                       ltBandejaDLazer,
                                                       isVIsLazerIngreso(),
                                                       isPDevolver()
                                                       ).trim();
            FarmaUtility.aceptarTransaccion();
            try {
                UtilityRecepCiega.imprimeVoucherTransportista_02(this, vNroRecep, txtNombre,false);
            } catch (Exception e1) {
                log.error("",e1);
            }            
            FarmaUtility.showMessage(this, "Se grabó correctamente la Recepción", txtHojaRes);
            
            cerrarVentana(true);
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this,
                                     "Error al ingresar los Datos del Transportista: " + sql.getMessage().substring(9,
                                                                                                                    sql.getMessage().indexOf("ORA",
                                                                                                                                             2)),
                                     txtNombre);
        }        
    }

    public void setPNuevo(boolean pNuevo) {
        this.pNuevo = pNuevo;
    }

    public boolean isPNuevo() {
        return pNuevo;
    }

    public void setPModificar(boolean pModificar) {
        this.pModificar = pModificar;
    }

    public boolean isPModificar() {
        return pModificar;
    }

    public void setPVisualizar(boolean pVisualizar) {
        this.pVisualizar = pVisualizar;
    }

    public boolean isPVisualizar() {
        return pVisualizar;
    }
    
    public void loadRecepModifica(String pNumRecep,String pHojaRes,String pCodTran,String pNombreEmpTrans,
                                  String pNombre,String pGlosa,String pValija,
                                  ArrayList vListaR,ArrayList vListaD,String pFecha){
        txtHojaRes.setText(pHojaRes);
        txtcodtrans.setText(pCodTran);
        txtnomtrans.setText(pNombreEmpTrans);
        txtNombre.setText(pNombre);
        txtGlosa.setText(pGlosa);
        txtCodValija.setText(pValija);
        loadRecepcionadas(vListaR);
        loadDevueltas(vListaD);
        setPNumRecep(pNumRecep);
        lblinformativo.setText("<HTML><left>Se  va  MOFICAR  la  recepción  Nº "+pNumRecep+"  del día   "+pFecha+". </left></HTML>");
        pnlInformativo.setVisible(true);
        FarmaUtility.moveFocus(txtHojaRes);
        //FarmaUtility.showMessage(this, "Debe de ingresar",txtGlosa);
    }
    
    public void loadRecepVisualiza(String pNumRecep,String pHojaRes,String pCodTran,String pNombreEmpTrans,
                                  String pNombre,String pGlosa,String pValija,
                                  ArrayList vListaR,ArrayList vListaD,String pFecha){
        txtHojaRes.setText(pHojaRes);
        txtcodtrans.setText(pCodTran);
        txtnomtrans.setText(pNombreEmpTrans);
        txtNombre.setText(pNombre);
        txtGlosa.setText(pGlosa);
        txtCodValija.setText(pValija);
        loadRecepcionadas(vListaR);
        loadDevueltas(vListaD);
        setPNumRecep(pNumRecep);
        lblinformativo.setText("<HTML><left>Solo puede Visualizar la  recepción  Nº "+pNumRecep+"  del día     "+pFecha+". </left></HTML>");
        pnlInformativo.setVisible(true);
        FarmaUtility.moveFocus(txtHojaRes);
        //FarmaUtility.showMessage(this, "Debe de ingresar",txtGlosa);
    }
    
    private void loadRecepcionadas(ArrayList vListGrabar) {
        int pos = 0;
        ArrayList vFila = new ArrayList();
        ArrayList vListaBandeja = new ArrayList();
        for (int i = 0; i < vListGrabar.size(); i++) {
            if (pos == 3) {
                vListaBandeja.add(vFila);
                vFila = new ArrayList();
                pos = 0;
            }
            vFila.add(((ArrayList)(vListGrabar.get(i))).get(0).toString());
            pos++;

            if ((i + 1) == vListGrabar.size()) {
                //llego al final
                for (int Vpos = pos; Vpos < 4; Vpos++)
                    vFila.add(" ");
                vListaBandeja.add(vFila);
            }
        }

        tblmBRecepcionadas.data.clear();
        for (int g = 0; g < vListaBandeja.size(); g++)
            tblmBRecepcionadas.insertRow((ArrayList)vListaBandeja.get(g));
        tblBandejasRecepcionadas.repaint();
    }

    private void loadDevueltas(ArrayList vListGrabar) {
        int pos = 0;
        ArrayList vFila = new ArrayList();
        ArrayList vListaBandeja = new ArrayList();
        for (int i = 0; i < vListGrabar.size(); i++) {
            if (pos == 3) {
                vListaBandeja.add(vFila);
                vFila = new ArrayList();
                pos = 0;
            }
            vFila.add(((ArrayList)(vListGrabar.get(i))).get(0).toString());
            pos++;

            if ((i + 1) == vListGrabar.size()) {
                //llego al final
                for (int Vpos = pos; Vpos < 4; Vpos++)
                    vFila.add(" ");
                vListaBandeja.add(vFila);
            }
        }

        tblmBDevueltas.data.clear();
        for (int g = 0; g < vListaBandeja.size(); g++)
            tblmBDevueltas.insertRow((ArrayList)vListaBandeja.get(g));
        tblBandejasDevueltas.repaint();
    }


    public void setPNumRecep(String pNumRecep) {
        this.pNumRecep = pNumRecep;
    }

    public String getPNumRecep() {
        return pNumRecep;
    }

    public void setVIsLazerIngreso(boolean vIsLazerHojaResumen) {
        this.vIsLazerIngreso = vIsLazerHojaResumen;
    }

    public boolean isVIsLazerIngreso() {
        return vIsLazerIngreso;
    }
    
    public void setIsLazerTeclado(KeyEvent e,Object jTexto){
    if (((JTextField)jTexto).getText().length() == 0)
        tmpT1 = System.currentTimeMillis();
    
    if (e.getKeyCode() == KeyEvent.VK_ENTER&&!isPVisualizar()) {
        vIsLazerIngreso = false;
        tmpT2 = System.currentTimeMillis();
        log.info("Tiem 2 " + (tmpT2));
        log.info("Tiem 1 " + (tmpT1));
        log.info("Tiempo de ingreso y ENTER " + (tmpT2 - tmpT1));
        if ((tmpT2 - tmpT1) <= vTiempoMaquina && ((JTextField)jTexto).getText().length() > 0) {
            vIsLazerIngreso = true;
            tmpT1 = 0;
            setVIsLazerIngreso(true);
            log.info("INGRESO CON LAZER");
            //FarmaUtility.showMessage(this, "Se ingreso con LAZER.", txtHojaRes);
        } else {
            vIsLazerIngreso = false;
            tmpT1 = 0;
            setVIsLazerIngreso(false);
            log.info("INGRESO CON TECLADO A MANO");
            //FarmaUtility.showMessage(this, "Se ingreso con Teclado a mano.", txtHojaRes);
        }
      }        
    }

    public void setVListLazerRecep(ArrayList vListLazerRecep) {
        this.vListLazerRecep = vListLazerRecep;
    }

    public ArrayList getVListLazerRecep() {
        return vListLazerRecep;
    }

    public void setVListLazerDevol(ArrayList vListLazerDevol) {
        this.vListLazerDevol = vListLazerDevol;
    }

    public ArrayList getVListLazerDevol() {
        return vListLazerDevol;
    }

    public void setVHojaVisualiza(String vNumRecepVisualiza) {
        this.vHojaVisualiza = vNumRecepVisualiza;
    }

    public String getVHojaVisualiza() {
        return vHojaVisualiza;
    }

    private void cargaVisualizar(String pNroHojaRes) {
        String pAccion = "";
        try {
            pAccion = UtilityRecepCiega.getAccionHojaExistente(pNroHojaRes);
        } catch (SQLException e) {
            log.error("",e);
        }
        if(!pAccion.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)&&pAccion.trim().length()>10){
            // TIENE cadena valida
            String pList[] = pAccion.trim().split("@");
            String pPaso = pList[0].trim();
            {

                    limpiaDatos();
                    setPNuevo(false);
                    setPVisualizar(true);
                    setPModificar(false);
                    String pNumRecep = pList[1].trim();
                    String pFecha = pList[2].trim();
                    String pCodTransportista = pList[3].trim();
                    String pNameEmpTrans = pList[4].trim();
                    String pNombre = pList[5].trim();
                    String pGlosa = pList[6].trim();
                    String pCodValija = pList[7].trim();
                    ArrayList pListaBR = new ArrayList();
                    try {
                         DBRecepCiega.getListaBandejasRecep(pListaBR, pNumRecep);
                     } catch (SQLException sqle) {
                         log.error("",sqle);
                     }
                    ArrayList pListaBD = new ArrayList();
                    try {
                         DBRecepCiega.getListaBandejasDevol(pListaBD, pNumRecep);
                     } catch (SQLException sqle) {
                         log.error("",sqle);
                     }
                    // - load datos de modificar
                    loadRecepVisualiza(pNumRecep,pNroHojaRes,pCodTransportista,pNameEmpTrans,
                                      pNombre,pGlosa,pCodValija,pListaBR,pListaBD,pFecha);
                    txtHojaRes.setEditable(false);
                    txtGlosa.setEditable(false);
                    txtCodValija.setEditable(false);
                    txtNombre.setEditable(false);
                    FarmaUtility.moveFocus(txtHojaRes);
            }
        }
    }

    public void setPDevolver(boolean pDevolver) {
        this.pDevolver = pDevolver;
    }

    public boolean isPDevolver() {
        return pDevolver;
    }

    private void setFormDevolver() {
        lblinformativo.setText("<HTML><left>DEVOLUCION DE BULTOS, por favor de ingresar el transportista y glosa.</left></HTML>");
        pnlInformativo.setVisible(true);
        txtHojaRes.setVisible(false);
        lblHojaRes.setVisible(false);
        jPanel2.setVisible(false);
        txtnomtrans.setEnabled(true);
        txtcodtrans.setEnabled(true);
        txtPlaca.setEnabled(true);
        lblF5.setVisible(true);
        lblLimpiar.setVisible(true);
        this.remove(jPanel2);
        this.remove(txtHojaRes);
        this.remove(lblF1_R);
        jPanel1.setBounds(new Rectangle(15, 115-100, 375, 90));
        lblNombrePersonal.setBounds(new Rectangle(15, 210-100, 105, 35));
        txtNombre.setBounds(new Rectangle(125, 225-100, 280, 20));
        lblGlosa.setBounds(new Rectangle(15, 260-100, 280, 20));
        txtGlosa.setBounds(new Rectangle(125, 260-100, 280, 20));
        jPanel3.setBounds(new Rectangle(405, 110-100, 375, 250));
        pnlInformativo.setBounds(new Rectangle(20, 310-100, 755, 40));
        lblF11.setBounds(new Rectangle(540, 370-100, 90, 35));
        lblF5.setBounds(new Rectangle(200, 370-100, 105, 35));
        lblLimpiar.setBounds(new Rectangle(375, 370-100, 100, 35));
        lblF11.setBounds(new Rectangle(540, 370-100, 90, 35));
        lblEsc.setBounds(new Rectangle(680, 370-100, 90, 35));
        this.setSize(new Dimension(798, 471-100));
        FarmaUtility.moveFocus(txtcodtrans);
        this.setTitle("Devolución de Bultos");
        FarmaUtility.centrarVentana(this);
        
    }
    private void ingresoDevolucion() {
            ArrayList vListaBandeja = new ArrayList();
            ArrayList vBorradoBandeja = new ArrayList();
            ArrayList vListLazer =  new ArrayList();
            String vCodValija = "";
                DlgIngresoBandejaDevol dlgIngBandejaDev =
                    new DlgIngresoBandejaDevol(myParentFrame, "", true, txtHojaRes.getText());                
                dlgIngBandejaDev.vActivaDevolucion();
                dlgIngBandejaDev.setTitle("Devolución de Bandejas");
                dlgIngBandejaDev.lblAccion.setText("Devolución");
                dlgIngBandejaDev.setPNuevo(isPNuevo());
                dlgIngBandejaDev.setPModificar(isPModificar());
                dlgIngBandejaDev.setPVisualizar(isPVisualizar());                
                dlgIngBandejaDev.setVListaBorra(vBorraDevol);
                dlgIngBandejaDev.setVListaIn(obtenerArrayDeBandejasDevueltas());
                dlgIngBandejaDev.setVisible(true);
                vListaBandeja = dlgIngBandejaDev.getVListGrabar();
                vBorradoBandeja = dlgIngBandejaDev.getVListaBorra();
                vListLazer = dlgIngBandejaDev.getListaBandejaLazer();
                if(dlgIngBandejaDev.getPCodValija().trim().length()>0)
                 vCodValija = dlgIngBandejaDev.getPCodValija();
                else
                 vCodValija = txtCodValija.getText().trim();
                
            if (FarmaVariables.vAceptar) {
                    txtCodValija.setText(vCodValija);
                    txtCodValija.setEnabled(false);
                    txtCodValija.selectAll();
                    tblmBDevueltas.data.clear();
                    for (int g = 0; g < vListaBandeja.size(); g++)
                        tblmBDevueltas.insertRow((ArrayList)vListaBandeja.get(g));
                    tblBandejasDevueltas.repaint();
                    setVListLazerDevol(vListLazer);
            }
    }
}
