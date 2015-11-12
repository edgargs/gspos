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

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.recepcionCiega.reference.ConstantsRecepCiega;

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
public class DlgDiferenciasBandejas extends JDialog {
    /* ********************************************************************** */
    /* DECLARACION PROPIEDADES */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgDiferenciasBandejas.class);
    private int MAX_DIGITOS = 10;
    private boolean vRecepcion = false;
    private boolean vDevolucion = false;
    
    private JPanel jContentPane = new JPanel();
    private String pMotivo = "",pCodMotivo = "";
    
    Frame myParentFrame;
    private int vEstVigencia;
    private String FechaInicio = "";

    private JPanelTitle pnlTitle1 = new JPanelTitle();

    private JTextFieldSanSerif txtPrecintos = new JTextFieldSanSerif();

    // adcionado 15042014


    private JLabelFunction lblEsc = new JLabelFunction();
    private ArrayList vListGrabar = new ArrayList();
    private JLabelFunction lblF11 = new JLabelFunction();

    private BorderLayout borderLayout1 = new BorderLayout();

    private JLabelWhite lblCodPromocion = new JLabelWhite();
    private boolean flagExisteHojaResumen = false;
    //INI ASOSA - 21/07/2014

    private final int COL_ORD_LISTA = 1;
    
    
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblBandeja = new JTable();
    private FarmaTableModel tblmBandeja;
    
    private JLabel jLabel1 = new JLabel();
    private JPanel jPanel1 = new JPanel();
    public JLabel lblAccion = new JLabel();
    private JPanel jPanel3 = new JPanel();
    
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JTable tblBandeja2 = new JTable();
    private FarmaTableModel tblmBandeja2;    
    
    private String nroHojaRes = "";
    private JLabel jLabel2 = new JLabel();
    private JPanel jPanel2 = new JPanel();
    private JPanel jPanel4 = new JPanel();
    private JLabel jLabel3 = new JLabel();

    /* ********************************************************************** */
    /* CONSTRUCTORES */
    /* ********************************************************************** */

    public DlgDiferenciasBandejas() {
        this(null, "", false);
    }

    public DlgDiferenciasBandejas(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(538, 329));
        this.getContentPane().setLayout(borderLayout1);
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
        pnlTitle1.setBounds(new Rectangle(5, 10, 520, 240));
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
        //lblEsc.setText("[ESC] Cerrar");
        lblEsc.setText("<HTML><CENTER>[ESC] Cerrar</CENTER></HTML>");
        lblEsc.setBounds(new Rectangle(455, 255, 65, 40));
        //lblF11.setText("[F11] Grabar");
        lblF11.setText("<HTML><CENTER>[F11] Grabar</CENTER></HTML>");
        lblF11.setBounds(new Rectangle(370, 255, 65, 40));

        //lblEnter.setText("[Enter] Agregar");
        lblCodPromocion.setText("[CodPromocion]");
        lblCodPromocion.setForeground(new Color(255, 130, 14));
        lblCodPromocion.setRequestFocusEnabled(false);
        lblCodPromocion.setFocusable(false);
        lblCodPromocion.setVisible(false);
        lblCodPromocion.setBounds(new Rectangle(0, 10, 105, 15));
        //--Se cambio el tamaño de digitos
        //  12.09.2008 Dubilluz

        //INI ASOSA - 21/07/2014

        //pnlTitle1.add(jScrollPane1, new XYConstraints(115, 15, 195, 20));
        tblBandeja.setFont(new Font("SansSerif", 0, 12));
        //FIN ASOSA - 25/07/2014
        tblBandeja.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                    tblMotivo_focusLost(e);
                }
            });
        tblBandeja.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    tblMotivo_keyPressed(e);
                }
            });
        
        jScrollPane1.getViewport();
        jScrollPane1.setBounds(new Rectangle(10, 80, 500, 150));

        jScrollPane2.getViewport();
        jScrollPane2.setBounds(new Rectangle(20, 315, 500, 70));

        jLabel2.setText("Lista de bandejas Sobrantes ");
        jLabel2.setBounds(new Rectangle(5, 5, 245, 15));
        jLabel2.setFont(new Font("Tahoma", 1, 11));
        jLabel2.setForeground(SystemColor.window);
        jPanel2.setBounds(new Rectangle(10, 250, 500, 20));
        jPanel2.setLayout(null);
        jPanel2.setBackground(new Color(255, 130, 14));
        jPanel4.setBounds(new Rectangle(400, 0, 100, 20));
        jPanel4.setLayout(null);
        jPanel4.setBackground(new Color(0, 82, 0));
        jLabel3.setText("Sobrantes");
        jLabel3.setBounds(new Rectangle(20, 0, 65, 20));
        jLabel3.setFont(new Font("Tahoma", 1, 11));
        jLabel3.setBackground(SystemColor.window);
        jLabel3.setForeground(SystemColor.window);
        jScrollPane1.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    jScrollPane1_keyPressed(e);
                }
            });
        jPanelTitle1.setBounds(new Rectangle(10, 60, 500, 20));
        jLabelWhite1.setText("Lista de bandejas Faltantes");
        jLabelWhite1.setBounds(new Rectangle(5, 5, 175, 15));


        //INI ASOSA - 21/07/2014
        //FIN ASOSA - 25/07/2014
        jLabel1.setText("<html><center><h1>Faltan estas bandejas :</h1></center></html>");
        jLabel1.setBounds(new Rectangle(10, 10, 475, 25));
        jLabel1.setBackground(SystemColor.window);
        jLabel1.setForeground(SystemColor.window);
        jLabel1.setFont(new Font("Tahoma", 1, 11));
        jPanel1.setBounds(new Rectangle(10, 5, 500, 45));
        jPanel1.setLayout(null);
        jPanel1.setBackground(new Color(0, 99, 0));
        lblAccion.setText("Faltantes");
        lblAccion.setBounds(new Rectangle(20, 0, 70, 20));
        lblAccion.setBackground(new Color(0, 66, 198));
        lblAccion.setForeground(SystemColor.window);
        lblAccion.setFont(new Font("Tahoma", 1, 12));
        jPanel3.setBounds(new Rectangle(400, 0, 100, 20));
        jPanel3.setLayout(null);
        jPanel3.setBackground(new Color(0, 99, 0));
        jPanel1.add(jLabel1, null);
        jPanel4.add(jLabel3, null);
        jPanel2.add(jPanel4, null);
        jPanel2.add(jLabel2, null);
        pnlTitle1.add(jPanel2, null);
        pnlTitle1.add(jPanel1, null);
        /*
        pnlTitle1.add(txtPrecintos, new XYConstraints(115, 70, 195, 20));
        */
        // AAMPUERO 14.04.2014
        pnlTitle1.add(lblCodPromocion, new XYConstraints(0, 10, 105, 15));
        jScrollPane1.getViewport().add(tblBandeja, null);
        pnlTitle1.add(jScrollPane1, null);
        jScrollPane2.getViewport().add(tblBandeja2, null);
        pnlTitle1.add(jScrollPane2, null);
        jPanelTitle1.add(jLabelWhite1, null);
        jPanel3.add(lblAccion, null);
        jPanelTitle1.add(jPanel3, null);
        pnlTitle1.add(jPanelTitle1, null);
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Diferencias con la Hoja Resumen");
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
    
    public boolean isDiferencias()
    {
        if(tblBandeja.getRowCount()>0)
            return true;
        else 
            return false;
    }
    
    public void cargaTablasDiferencias(ArrayList vListaTotalHoja,ArrayList vListaIngresada){
        int pos = 0;
        ArrayList vFila=new ArrayList();
        
        ArrayList vSobrante = new ArrayList();
        ArrayList vFaltante = new ArrayList();
        
        // calculo de faltante
        for(int i=0;i<vListaTotalHoja.size();i++){
            String pBandHoja = FarmaUtility.getValueFieldArrayList(vListaTotalHoja,i,0);
            boolean vExiste = false;
            for(int j=0;j<vListaIngresada.size();j++){
                String pDato1 = FarmaUtility.getValueFieldArrayList(vListaIngresada, j, 0);
                String pDato2 = FarmaUtility.getValueFieldArrayList(vListaIngresada, j, 1);
                String pDato3 = FarmaUtility.getValueFieldArrayList(vListaIngresada, j, 2);
                if(pBandHoja.trim().equalsIgnoreCase(pDato1)||pBandHoja.trim().equalsIgnoreCase(pDato2)||pBandHoja.trim().equalsIgnoreCase(pDato3)){
                    vExiste = true;
                    break;
                }
            }
            if(!vExiste&&pBandHoja.trim().length()>0){
                ArrayList fila = new ArrayList();
                fila.add(pBandHoja);
                vFaltante.add(fila);
            }
        }
        // calculo de sobrante
            for(int j=0;j<vListaIngresada.size();j++){
                String pDato1 = FarmaUtility.getValueFieldArrayList(vListaIngresada, j, 0);
                String pDato2 = FarmaUtility.getValueFieldArrayList(vListaIngresada, j, 1);
                String pDato3 = FarmaUtility.getValueFieldArrayList(vListaIngresada, j, 2);
                boolean pExiste1 = false,pExiste2 = false,pExiste3 = false;
                for(int i=0;i<vListaTotalHoja.size();i++){
                    String pBandHoja = FarmaUtility.getValueFieldArrayList(vListaTotalHoja,i,0);
                    if(pBandHoja.trim().equalsIgnoreCase(pDato1))
                        pExiste1 = true;
                    if(pBandHoja.trim().equalsIgnoreCase(pDato2))
                       pExiste2 = true;                    
                    if(pBandHoja.trim().equalsIgnoreCase(pDato3))
                       pExiste3 = true;                                        
               }
            if(!pExiste1&&pDato1.trim().length()>0){
                ArrayList fila = new ArrayList();
                fila.add(pDato1);
                vSobrante.add(fila);
            }
            if(!pExiste2&&pDato2.trim().length()>0){
                ArrayList fila = new ArrayList();
                fila.add(pDato2);
                vSobrante.add(fila);
            }
            if(!pExiste3&&pDato3.trim().length()>0){
                ArrayList fila = new ArrayList();
                fila.add(pDato3);
                vSobrante.add(fila);
            }                
        }        
        
        //////////////////////////////////////////////
        vListGrabar = new ArrayList();
        for(int i=0;i<vFaltante.size();i++){
            if(pos==5){
              this.vListGrabar.add(vFila);    
              vFila = new ArrayList();
            pos =0;
            }
            vFila.add(((ArrayList)(vFaltante.get(i))).get(0).toString());
            pos++;
            
            if((i+1)==vFaltante.size()){
                //llego al final
                for(int Vpos=pos;Vpos<6;Vpos++)
                    vFila.add(" ");
                vListGrabar.add(vFila);    
            }
        }
        tblmBandeja.data.clear();

        for (int g = 0; g < vListGrabar.size(); g++)
            tblmBandeja.insertRow((ArrayList)vListGrabar.get(g));
        
        
        tblBandeja.repaint();

        /////////////////////////////////////////////////////////////////////
        vListGrabar = new ArrayList();
        vFila = new ArrayList();
        pos =0;
        for(int i=0;i<vSobrante.size();i++){
            if(pos==5){
              this.vListGrabar.add(vFila);    
              vFila = new ArrayList();
            pos =0;
            }
            vFila.add(((ArrayList)(vSobrante.get(i))).get(0).toString());
            pos++;
            
            if((i+1)==vSobrante.size()){
                //llego al final
                for(int Vpos=pos;Vpos<6;Vpos++)
                    vFila.add(" ");
                vListGrabar.add(vFila);    
            }
        }        
        tblmBandeja2.data.clear();
        
        
        for (int g = 0; g < vListGrabar.size(); g++)
            tblmBandeja2.insertRow((ArrayList)vListGrabar.get(g));
        
        
        
        ///////////////////////////////////////////////////////////////////
        tblBandeja2.repaint();
    }
    
    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(tblBandeja);
    }

    private void initTableListaBandejas() {
        tblmBandeja =
                new FarmaTableModel(ConstantsRecepCiega.columnsListaDifBandeja, ConstantsRecepCiega.defaultValuesListaDifBandeja,
                                    0);
        FarmaUtility.initSimpleList(tblBandeja, tblmBandeja, ConstantsRecepCiega.columnsListaDifBandeja);
        tblBandeja.setName(ConstantsRecepCiega.NAME_TABLA_BANDEJAS);
        if (tblmBandeja.getRowCount() > 0)
            FarmaUtility.ordenar(tblBandeja, tblmBandeja, COL_ORD_LISTA, FarmaConstants.ORDEN_ASCENDENTE);


        tblmBandeja2 =
                new FarmaTableModel(ConstantsRecepCiega.columnsListaDifBandeja, ConstantsRecepCiega.defaultValuesListaDifBandeja,
                                    0);
        FarmaUtility.initSimpleList(tblBandeja2, tblmBandeja2, ConstantsRecepCiega.columnsListaDifBandeja);
        tblBandeja2.setName(ConstantsRecepCiega.NAME_TABLA_BANDEJAS);
        if (tblmBandeja2.getRowCount() > 0)
            FarmaUtility.ordenar(tblBandeja2, tblmBandeja2, COL_ORD_LISTA, FarmaConstants.ORDEN_ASCENDENTE);
        
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ********************************************************************** */
    /* METODOS AUXILIARES */
    /* ********************************************************************** */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F11) {
            confirmaDiferencias();
        }else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
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
    private void tblMotivo_focusLost(FocusEvent e) {
        
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
    
    public void vActivaRecepcion(){
        vRecepcion = true;
        vDevolucion = false;
    }
    
    public void vActivaDevolucion(){
        vRecepcion = false;
        vDevolucion = true;
    }
    
    
    private void confirmaDiferencias() {
        boolean vRevIngreso = true;
        vRevIngreso =
            JConfirmDialog.rptaConfirmDialogDefaultNo(this,"¿Esta seguro de confirmar estas diferencias" +
                                                      "\npresentadas en la recepción de bandejas?");
        if(vRevIngreso){
            cerrarVentana(true);
        }
    }

    public void setVListGrabar(ArrayList vListGrabar) {
        int pos = 0;
        ArrayList vFila=new ArrayList();
        for(int i=0;i<vListGrabar.size();i++){
            if(pos==3){
              this.vListGrabar.add(vFila);    
              vFila = new ArrayList();
            pos =0;
            }
            vFila.add(((ArrayList)(vListGrabar.get(i))).get(0).toString());
            pos++;
            
            if((i+1)==vListGrabar.size()){
                //llego al final
                for(int Vpos=pos;Vpos<4;Vpos++)
                    vFila.add(" ");
                this.vListGrabar.add(vFila);    
            }
        }
    }

    public ArrayList getVListGrabar() {
        return vListGrabar;
    }

}
