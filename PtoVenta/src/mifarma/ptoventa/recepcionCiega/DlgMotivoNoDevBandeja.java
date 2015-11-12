package mifarma.ptoventa.recepcionCiega;


import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.recepcionCiega.reference.ConstantsRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.DBRecepCiega;

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
public class DlgMotivoNoDevBandeja extends JDialog {
    /* ********************************************************************** */
    /* DECLARACION PROPIEDADES */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgMotivoNoDevBandeja.class);

    private JPanel jContentPane = new JPanel();
    private String pMotivo = "",pCodMotivo = "";
    
    Frame myParentFrame;
    private int vEstVigencia;
    private String FechaInicio = "";

    private JPanelTitle pnlTitle1 = new JPanelTitle();

    private JTextFieldSanSerif txtPrecintos = new JTextFieldSanSerif();

    // adcionado 15042014


    private JLabelFunction lblEsc = new JLabelFunction();

    private JLabelFunction lblF11 = new JLabelFunction();

    private BorderLayout borderLayout1 = new BorderLayout();

    private JLabelWhite lblCodPromocion = new JLabelWhite();

    //INI ASOSA - 21/07/2014

    private final int COL_ORD_LISTA = 1;
    private FarmaTableModel tblmMotivo;
    
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblMotivo = new JTable();

    private JPanel jPanel2 = new JPanel();

    /* ********************************************************************** */
    /* CONSTRUCTORES */
    /* ********************************************************************** */

    public DlgMotivoNoDevBandeja() {
        this(null, "", false);
    }

    public DlgMotivoNoDevBandeja(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(417, 281));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Motivo no devoluci\u00f3n de bandejas/bultos");
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
        pnlTitle1.setBounds(new Rectangle(5, 10, 400, 240));
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

        // aampuero 14.04.2014


        lblEsc.setText("[ESC] Cerrar");
        lblEsc.setBounds(new Rectangle(305, 210, 85, 20));
        lblF11.setText("[Enter] Aceptar");
        lblF11.setBounds(new Rectangle(15, 210, 95, 20));
        lblCodPromocion.setText("[CodPromocion]");
        lblCodPromocion.setForeground(new Color(255, 130, 14));
        lblCodPromocion.setRequestFocusEnabled(false);
        lblCodPromocion.setFocusable(false);
        lblCodPromocion.setVisible(false);
        lblCodPromocion.setBounds(new Rectangle(0, 10, 105, 15));
        //--Se cambio el tamaño de digitos
        //  12.09.2008 Dubilluz

        //INI ASOSA - 21/07/2014


        jScrollPane1.getViewport();
        jScrollPane1.getViewport().add(tblMotivo, null);
        jPanel2.add(jScrollPane1, null);
        //pnlTitle1.add(jScrollPane1, new XYConstraints(115, 15, 195, 20));
        jPanelTitle1.add(jLabelWhite1, null);
        jPanel2.add(jPanelTitle1, null);
        tblMotivo.setFont(new Font("SansSerif", 0, 12));
        //FIN ASOSA - 25/07/2014

        tblMotivo.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                    tblMotivo_focusLost(e);
                }
            });
        tblMotivo.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    tblMotivo_keyPressed(e);
                }
            });
        jScrollPane1.setBounds(new Rectangle(10, 40, 370, 150));
        jScrollPane1.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    jScrollPane1_keyPressed(e);
                }
            });
        jPanelTitle1.setBounds(new Rectangle(10, 20, 370, 20));
        jLabelWhite1.setText("Presione ENTER para seleccionar el motivo deseado : ");
        jLabelWhite1.setBounds(new Rectangle(5, 5, 370, 15));


        //INI ASOSA - 21/07/2014
        //FIN ASOSA - 25/07/2014
        jPanel2.setBounds(new Rectangle(5, 5, 390, 200));
        jPanel2.setLayout(null);
        jPanel2.setBorder(BorderFactory.createTitledBorder("Seleccione el Motivo"));
        jPanel2.setForeground(new Color(214, 107, 0));
        jPanel2.setBackground(SystemColor.window);
        pnlTitle1.add(jPanel2, null);
        pnlTitle1.add(lblCodPromocion, new XYConstraints(0, 10, 105, 15));
        /*
        pnlTitle1.add(txtPrecintos, new XYConstraints(115, 70, 195, 20));
        */
        // AAMPUERO 14.04.2014
        pnlTitle1.add(lblEsc, null);
        pnlTitle1.add(lblF11, null);
        jContentPane.add(pnlTitle1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    /* ********************************************************************** */
    /* METODO INITIALIZE */
    /* ********************************************************************** */

    private void initialize() {
        initTableListaBandejas();
    }

    /* ********************************************************************** */
    /* METODO DE INICIALIZACION */
    /* ********************************************************************** */

    /* ********************************************************************** */
    /* METODOS DE EVENTOS */
    /* ********************************************************************** */

    
    
    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.showMessage(this,"Seleccione el motivo porque no ha podido devolver.",tblMotivo);
        FarmaUtility.moveFocus(tblMotivo);
        
    }

    private void initTableListaBandejas() {
        tblmMotivo =
                new FarmaTableModel(ConstantsRecepCiega.columnsListaMotivoNoDevolucion, ConstantsRecepCiega.defaultValuesListaMotivoNoDevolucion,
                                    0);
        FarmaUtility.initSimpleList(tblMotivo, tblmMotivo, ConstantsRecepCiega.columnsListaMotivoNoDevolucion);
        tblMotivo.setName(ConstantsRecepCiega.NAME_TABLA_BANDEJAS);
        if (tblmMotivo.getRowCount() > 0)
            FarmaUtility.ordenar(tblMotivo, tblmMotivo, COL_ORD_LISTA, FarmaConstants.ORDEN_ASCENDENTE);
        listaMotivos();
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ********************************************************************** */
    /* METODOS AUXILIARES */
    /* ********************************************************************** */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
            seleccionaMotivo();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ********************************************************************** */
    /* METODOS DE LOGICA DE NEGOCIO */
    /* *********************************************************************** */

    private void seleccionaMotivo() {
        setPCodMotivo("");
        setPMotivo("");
        int i = tblMotivo.getSelectedRow();
        if(i<0){
            FarmaUtility.showMessage(this,"Debe de seleccionar el motivo.",tblMotivo);
        }
        else{
            String pDescMotivo = FarmaUtility.getValueFieldArrayList(tblmMotivo.data, i, 0).toString();    
            String pCodMotivo  = FarmaUtility.getValueFieldArrayList(tblmMotivo.data, i, 1).toString();    
            //FarmaUtility.rptaConfirmDialog(this, "¿Estes el motivo ?")
            if(JConfirmDialog.rptaConfirmDialog(this,
                                                                           "¿Esta seguro del motivo selecciondo :\n "+
                                                                           pDescMotivo+" "+
                                                                           ""
                                                                           +"?"))
            {
               setPCodMotivo(pCodMotivo);
               setPMotivo(pDescMotivo);
               cerrarVentana(true);
            }
        }
        
    }

    private void listaMotivos() {
        try {
            DBRecepCiega.getListaMotivoNODevolucionBandeja(tblmMotivo);
            FarmaUtility.moveFocus(tblMotivo);
        } catch (SQLException sqle) {
            log.error("",sqle);
            FarmaUtility.showMessage(this, "Error al Listar los motivos.\n"+
                                           sqle.getMessage(), null);
        }
    }

    private void tblMotivo_focusLost(FocusEvent e) {
        FarmaUtility.moveFocus(tblMotivo);
    }

    public void setPMotivo(String pMotivo) {
        this.pMotivo = pMotivo;
    }

    public String getPMotivo() {
        return pMotivo;
    }

    public void setPCodMotivo(String pCodMotivo) {
        this.pCodMotivo = pCodMotivo;
    }

    public String getPCodMotivo() {
        return pCodMotivo;
    }

    private void jScrollPane1_keyPressed(KeyEvent e) {
        
    }

    private void tblMotivo_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }
}
