package mifarma.ptoventa.recepcionCiega;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;

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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
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
public class DlgPorDevolver extends JDialog {
    /* ********************************************************************** */
    /* DECLARACION PROPIEDADES */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgPorDevolver.class);
    private int MAX_DIGITOS = 10;
    private boolean vRecepcion = false;
    private boolean vDevolucion = false;
    
    private JPanel jContentPane = new JPanel();
    private String pMotivo = "",pCodMotivo = "";
    
    Frame myParentFrame;
    private JPanelTitle pnlTitle1 = new JPanelTitle();

    private JLabelFunction lblEsc = new JLabelFunction();
    private ArrayList vListGrabar = new ArrayList();
    private JLabelFunction lblF11 = new JLabelFunction();

    private BorderLayout borderLayout1 = new BorderLayout();
    private JLabelWhite lblCodPromocion = new JLabelWhite();
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
    
    private ArrayList vListaPorDevolver =  new ArrayList();
    
    private ArrayList vListaPantallaOld = new ArrayList();
    private JEditorPane jepPorDevolver = new JEditorPane();
    private JScrollPane jScrollPane2 = new JScrollPane();
    /* ********************************************************************** */
    /* CONSTRUCTORES */
    /* ********************************************************************** */

    public DlgPorDevolver() {
        this(null, "", false);
    }

    public DlgPorDevolver(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(438, 544));
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
        pnlTitle1.setBounds(new Rectangle(5, 10, 420, 500));
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
        lblEsc.setBounds(new Rectangle(330, 445, 65, 40));
        //lblF11.setText("[F11] Grabar");
        lblF11.setText("<HTML><CENTER>[F11] Aceptar</CENTER></HTML>");
        lblF11.setBounds(new Rectangle(260, 445, 65, 40));

        //lblEnter.setText("[Enter] Agregar");
        lblCodPromocion.setText("[CodPromocion]");
        lblCodPromocion.setForeground(new Color(255, 130, 14));
        lblCodPromocion.setRequestFocusEnabled(false);
        lblCodPromocion.setFocusable(false);
        lblCodPromocion.setVisible(false);
        lblCodPromocion.setBounds(new Rectangle(0, 10, 105, 15));
        jScrollPane2.setBounds(new Rectangle(10, 110, 385, 330));
        //--Se cambio el tamaño de digitos
        //  12.09.2008 Dubilluz

        //INI ASOSA - 21/07/2014


        
        //pnlTitle1.add(jScrollPane1, new XYConstraints(115, 15, 195, 20));
        jScrollPane2.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    jScrollPane2_mouseClicked(e);
                }
            });
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
        jScrollPane1.setBounds(new Rectangle(10, 110, 500, 300));


        jScrollPane1.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    jScrollPane1_keyPressed(e);
                }
            });
        jPanelTitle1.setBounds(new Rectangle(10, 90, 385, 20));
        jLabelWhite1.setText("Lista de bandejas por Devolver");
        jLabelWhite1.setBounds(new Rectangle(5, 5, 175, 15));


        //INI ASOSA - 21/07/2014
        //FIN ASOSA - 25/07/2014
        jLabel1.setText("<html><h1>               Bandejas \"Por Devolver \".</h1>PRESIONE  [F11]  PARA AGREGARLAS.</html>");
        jLabel1.setBounds(new Rectangle(15, 5, 480, 60));
        jLabel1.setBackground(SystemColor.window);
        jLabel1.setForeground(SystemColor.window);
        jLabel1.setFont(new Font("Tahoma", 1, 11));
        jLabel1.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                    jLabel1_focusLost(e);
                }
            });
        jLabel1.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    jLabel1_keyPressed(e);
                }
            });
        jPanel1.setBounds(new Rectangle(10, 5, 400, 70));
        jPanel1.setLayout(null);
        jPanel1.setBackground(new Color(0, 99, 0));
        lblAccion.setText("Por Devolver");
        lblAccion.setBounds(new Rectangle(20, 0, 95, 20));
        lblAccion.setBackground(new Color(0, 66, 198));
        lblAccion.setForeground(SystemColor.window);
        lblAccion.setFont(new Font("Tahoma", 1, 12));
        jPanel3.setBounds(new Rectangle(240, 0, 130, 20));
        jPanel3.setLayout(null);
        jPanel3.setBackground(new Color(0, 99, 0));
        jepPorDevolver.setEditable(false);
        jepPorDevolver.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    jepPorDevolver_keyPressed(e);
                }
            });
        jPanel1.add(jLabel1, null);
        pnlTitle1.add(jPanel1, null);
        /*
        pnlTitle1.add(txtPrecintos, new XYConstraints(115, 70, 195, 20));
        */
        // AAMPUERO 14.04.2014
        pnlTitle1.add(lblCodPromocion, new XYConstraints(0, 10, 105, 15));
        pnlTitle1.add(lblEsc, null);
        pnlTitle1.add(lblF11, null);
        jScrollPane1.getViewport().add(tblBandeja, null);
        //pnlTitle1.add(jScrollPane1, null);
        pnlTitle1.add(jPanelTitle1, null);
        jScrollPane2.getViewport().add(jepPorDevolver, null);
        pnlTitle1.add(jScrollPane2, null);
        jPanelTitle1.add(jLabelWhite1, null);
        jPanel3.add(lblAccion, null);
        jPanelTitle1.add(jPanel3, null);
        jContentPane.add(pnlTitle1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Bandejas planificadas a devolver");
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
        FarmaUtility.moveFocus(jLabel1);
        validaCierraPantalla();
    }

    private void initTableListaBandejas() {
        tblmBandeja =
                new FarmaTableModel(ConstantsRecepCiega.columnsListaPorDevolver, ConstantsRecepCiega.defaultValuesListaPorDevolver,
                                    0);
        FarmaUtility.initSimpleList(tblBandeja, tblmBandeja, ConstantsRecepCiega.columnsListaPorDevolver);
        tblBandeja.setName(ConstantsRecepCiega.NAME_TABLA_BANDEJAS);
        vListaPorDevolver();
        if (tblmBandeja.getRowCount() > 0)
            FarmaUtility.ordenar(tblBandeja, tblmBandeja, COL_ORD_LISTA, FarmaConstants.ORDEN_ASCENDENTE);
        
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ********************************************************************** */
    /* METODOS AUXILIARES */
    /* ********************************************************************** */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F11) {
            confirmaAceptarPorDevolver();
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
    
    
    private void confirmaAceptarPorDevolver() {
        setVListaPorDevolver(tblmBandeja.data);
        cerrarVentana(true);
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
    public ArrayList getListBandejaFecha(ArrayList vLista, String pFecha) {
        String pFechaAc = "";
        ArrayList vListaFechaBandeja = new ArrayList();
        for (int i = 0; i < vLista.size(); i++) {
            pFechaAc = FarmaUtility.getValueFieldArrayList(vLista, i, 1);
            if (pFecha.trim().equalsIgnoreCase(pFechaAc)) {
                vListaFechaBandeja.add(FarmaUtility.getValueFieldArrayList(vLista, i, 0));
            }
        }
        return vListaFechaBandeja;
    }

    public ArrayList getFechaRecep(ArrayList vLista) {
        String pFechaAc = "";
        ArrayList vListaFecha = new ArrayList();
        boolean vExiste = false;
        for (int i = 0; i < vLista.size(); i++) {
            pFechaAc = FarmaUtility.getValueFieldArrayList(vLista, i, 1);
            vExiste = false;
            for (int j = 0; j < vListaFecha.size(); j++) {
                if (vListaFecha.get(j).toString().trim().equalsIgnoreCase(pFechaAc.trim())) {
                    vExiste = true;
                    break;
                }
            }
            if (!vExiste)
                vListaFecha.add(pFechaAc);
        }
        return vListaFecha;
    }

    public void cargaHtmlPorDevolver() {
        if (tblmBandeja.data.size() > 0) {
            jepPorDevolver.setContentType("text/html");
            String pCodHtml = "";
            pCodHtml = "<style type=\"text/css\">\n" +
                    "body {\n" +
                    "    font-family: 'trebuchet MS', 'Lucida sans', Arial;\n" +
                    "    color: #444;\n" +
                    "    margin: 10px auto;\n" +
                    "}\n" +
                    "table {\n" +
                    "    border-spacing: 0;\n" +
                    "    font-size: 10;\n" +
                    "}\n" +
                    "table td{\n" +
                    "    width: 50px;\n" +
                    "}\n" +
                    ".bordered td, .bordered th {\n" +
                    "    padding: 4px;\n" +
                    "    text-align: center;    \n" +
                    "}.bordered th {\n" +
                    "    background-color: #ffefd6;\n" +
                    "}" + "</style><div><table border=1 class=\"bordered\">" + "<tr class=\"style4\">" +
                    "<th >Fecha</th>" + "<th><b>Bandeja</b></th>" + "<th></th>" + "<th></th>" + "<th></th>" + "</tr>";
            int pCtdColBandeja = 4;
            ArrayList vListFecha = new ArrayList();
            vListFecha = getFechaRecep(tblmBandeja.data);
            for (int i = 0; i < vListFecha.size(); i++) {
                ArrayList vListBandeja = new ArrayList();
                String pFecha = vListFecha.get(i).toString().trim();
                vListBandeja = getListBandejaFecha(tblmBandeja.data, pFecha);
                if (vListBandeja.size() <= pCtdColBandeja) {
                    //sin colspan
                    pCodHtml = pCodHtml + "<tr>" + "<td>" + pFecha + "</td>";
                    int pos = 0;
                    for (int k = 0; k < vListBandeja.size(); k++) {
                        pCodHtml = pCodHtml + "<td>" + vListBandeja.get(k).toString().trim() + "</td>";
                        pos++;
                    }
                    for (int j = pos + 1; j <= pCtdColBandeja; j++) {
                        pCodHtml = pCodHtml + "<td></td>";
                    }
                    pCodHtml = pCodHtml + "</tr>";
                } else {

                    int pResto = 0;
                    if ((vListBandeja.size() % pCtdColBandeja) > 0)
                        pResto = 1;

                    pCodHtml =
                            pCodHtml + "<tr>" + "<td rowspan=" + ((int)(vListBandeja.size() / pCtdColBandeja) + pResto) +
                            ">" + pFecha + "</td>";
                    int pos = 0;
                    boolean pFinPrimera = false;
                    for (int k = 0; k < vListBandeja.size(); k++) {
                        pos++;
                        if (!pFinPrimera) {
                            pCodHtml = pCodHtml + "<td>" + vListBandeja.get(k).toString().trim() + "</td>";
                            if (pos == pCtdColBandeja) {
                                pCodHtml = pCodHtml + "</tr>";
                                pFinPrimera = true;
                                pos = 0;
                            }
                        } else {
                            if (pos == 1) {
                                pCodHtml =
                                        pCodHtml + "<tr>" + "<td>" + vListBandeja.get(k).toString().trim() + "</td>";
                            } else if (pos == pCtdColBandeja) {
                                pCodHtml = pCodHtml + "<td>" + vListBandeja.get(k).toString().trim() + "</td>";
                                pCodHtml = pCodHtml + "</tr>";
                                pFinPrimera = true;
                                pos = 0;
                            } else {
                                pCodHtml = pCodHtml + "<td>" + vListBandeja.get(k).toString().trim() + "</td>";
                            }
                        }
                    }
                    if (pos > 0) {
                        for (int j = pos + 1; j <= pCtdColBandeja; j++) {
                            pCodHtml = pCodHtml + "<td></td>";
                            if (j == pCtdColBandeja)
                                pCodHtml = pCodHtml + "</tr>";
                        }
                    } else {
                        pCodHtml = pCodHtml + "</tr>";
                    }
                }
            }

            pCodHtml += "</table></div>";
            log.info("" + pCodHtml);
            jepPorDevolver.setText(pCodHtml);
        } else
            jepPorDevolver.setText("");
    }

    private void vListaPorDevolver() {
        try {
            DBRecepCiega.getListaBandejasPorDevolver(tblmBandeja);
            cargaHtmlPorDevolver();
        } catch (SQLException sqle) {
            log.error("",sqle);
        }
    }

    public void setVListaPorDevolver(ArrayList vListaPorDevolver) {
        this.vListaPorDevolver = vListaPorDevolver;
    }

    public ArrayList getVListaPorDevolver() {
        return vListaPorDevolver;
    }


    public void setVListaPantallaOld(ArrayList vListaPantallaOld) {
        this.vListaPantallaOld = vListaPantallaOld;
    }

    public ArrayList getVListaPantallaOld() {
        return vListaPantallaOld;
    }

    private void validaCierraPantalla() {
        boolean vExite = false;
        ArrayList vFila = new ArrayList();
        ArrayList vListaNueva = new ArrayList();
        for(int i=0;i<tblmBandeja.data.size();i++){
            String pCodBandejaPorDevl = FarmaUtility.getValueFieldArrayList(tblmBandeja.data, i, 0);
            vExite = false;
            vFila = new ArrayList();
            for(int j=0;j<vListaPantallaOld.size();j++){
                String pCodOld = FarmaUtility.getValueFieldArrayList(vListaPantallaOld, j, 0);
                if(pCodBandejaPorDevl.trim().equalsIgnoreCase(pCodOld)){
                    // NO VA EN EL LISTADO porque ya existe
                    vExite = true;
                    break;
                }
            }
            if(!vExite){
                vFila = (ArrayList)tblmBandeja.data.get(i);
                vListaNueva.add(vFila);
            }
        }
        
        if(vListaNueva.size()==0) cerrarVentana(false);
        else {
            tblmBandeja.data.clear();
            for(int i=0;i<vListaNueva.size();i++){
            tblmBandeja.insertRow((ArrayList)vListaNueva.get(i));
            }
            tblBandeja.repaint();
        }
    }

    private void jScrollPane2_mouseClicked(MouseEvent e) {
        FarmaUtility.moveFocus(jLabel1);
    }

    private void jLabel1_focusLost(FocusEvent e) {
        FarmaUtility.moveFocus(jLabel1);
    }

    private void jLabel1_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }


    private void jepPorDevolver_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }
}
