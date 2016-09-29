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
import javax.swing.JTextField;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.recepcionCiega.reference.ConstantsRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.UtilityRecepCiega;
import mifarma.ptoventa.reference.UtilityPtoVenta;

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
public class DlgIngresoBandejaDevol extends JDialog {
    /* ********************************************************************** */
    /* DECLARACION PROPIEDADES */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgIngresoBandejaDevol.class);
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
    private JLabelFunction lblF5 = new JLabelFunction();
    private JLabelFunction lblEnter = new JLabelFunction();

    private BorderLayout borderLayout1 = new BorderLayout();

    private JLabelWhite lblCodPromocion = new JLabelWhite();
    private boolean flagExisteHojaResumen = false;
    //INI ASOSA - 21/07/2014

    private final int COL_ORD_LISTA = 1;
    private FarmaTableModel tblmBandeja;
    
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblBandeja = new JTable();

    private JPanel jPanel2 = new JPanel();
    private JLabel jLabel1 = new JLabel();
    private JTextField txtBandeja = new JTextField();
    private JPanel jPanel1 = new JPanel();
    public JLabel lblAccion = new JLabel();
    private JPanel jPanel3 = new JPanel();
    
    
    private String nroHojaRes = "";
    private JTextField txtValija = new JTextField();
    private JLabel jLabel2 = new JLabel();
    private JLabelFunction lblF1 = new JLabelFunction();
    
    private String pCodValija = "";
    private ArrayList vListaIn = new ArrayList();

    private boolean pNuevo = false;
    private boolean pModificar = false;
    private boolean pVisualizar = false;
    private JLabelFunction lblPorDevolver = new JLabelFunction();
    private ArrayList vListaBorra = new ArrayList();
    
    public boolean vIsLazerIngreso = false;
    double vTiempoMaquina = 400; // MILISEGUNDOS
    long tmpT1,tmpT2,OldtmpT2;    
    /* ********************************************************************** */
    /* CONSTRUCTORES */
    /* ********************************************************************** */

    public DlgIngresoBandejaDevol() {
        this(null, "", false,"");
    }

    public DlgIngresoBandejaDevol(Frame parent, String title, boolean modal,String pHojaResumen) {
        super(parent, title, modal);
        myParentFrame = parent;
        nroHojaRes = pHojaResumen;
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
        this.setSize(new Dimension(416, 594));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Ingreso de  bandejas/bultos");
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
        pnlTitle1.setBounds(new Rectangle(5, 5, 400, 560));
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
        lblEsc.setBounds(new Rectangle(330, 460, 65, 50));
        //lblF11.setText("[F11] Grabar");
        lblF11.setText("<HTML><CENTER>[F11] Grabar</CENTER></HTML>");
        lblF11.setBounds(new Rectangle(250, 460, 65, 50));
        
        lblF5.setText("<HTML><CENTER>[F5] Borrar</CENTER></HTML>");
        lblF5.setBounds(new Rectangle(90, 460, 60, 50));
        //lblEnter.setText("[Enter] Agregar");
        lblEnter.setText("<HTML><CENTER>[Enter] Agregar</CENTER></HTML>");
        lblEnter.setBounds(new Rectangle(5, 460, 65, 50));            
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
        //pnlTitle1.add(jScrollPane1, new XYConstraints(115, 15, 195, 20));
        jPanelTitle1.add(jLabelWhite1, null);
        jPanel3.add(lblAccion, null);
        jPanelTitle1.add(jPanel3, null);
        jScrollPane1.getViewport().add(tblBandeja, null);
        jPanel2.add(jScrollPane1, null);
        jPanel2.add(jPanelTitle1, null);
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
        jScrollPane1.setBounds(new Rectangle(10, 40, 370, 310));
        jScrollPane1.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    jScrollPane1_keyPressed(e);
                }
            });
        jPanelTitle1.setBounds(new Rectangle(10, 20, 370, 20));
        jLabelWhite1.setText("Ingreso bandeja / bulto");
        jLabelWhite1.setBounds(new Rectangle(5, 5, 175, 15));


        //INI ASOSA - 21/07/2014
        //FIN ASOSA - 25/07/2014
        jPanel2.setBounds(new Rectangle(5, 95, 390, 360));
        jPanel2.setLayout(null);
        jPanel2.setBorder(BorderFactory.createTitledBorder("Ingrese el Número de Bandeja / Bulto"));
        jPanel2.setForeground(new Color(214, 107, 0));
        jPanel2.setBackground(SystemColor.window);
        jLabel1.setText("<html>Ingrese Bandeja/Bulto</html>");
        jLabel1.setBounds(new Rectangle(20, 40, 85, 30));
        jLabel1.setBackground(SystemColor.window);
        jLabel1.setForeground(SystemColor.window);
        jLabel1.setFont(new Font("Tahoma", 1, 11));
        txtBandeja.setBounds(new Rectangle(125, 45, 250, 20));
        txtBandeja.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                    jTextField1_focusLost(e);
                }
            });
        txtBandeja.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtBandeja_keyPressed(e);
                }

                public void keyTyped(KeyEvent e) {
                    txtBandeja_keyTyped(e);
                }
            });
        jPanel1.setBounds(new Rectangle(10, 5, 380, 80));
        jPanel1.setLayout(null);
        jPanel1.setBackground(new Color(0, 99, 0));
        lblAccion.setText("Recepci\u00f3n");
        lblAccion.setBounds(new Rectangle(10, 0, 85, 20));
        lblAccion.setBackground(new Color(0, 66, 198));
        lblAccion.setForeground(SystemColor.window);
        lblAccion.setFont(new Font("Tahoma", 1, 12));
        jPanel3.setBounds(new Rectangle(270, 0, 100, 20));
        jPanel3.setLayout(null);
        jPanel3.setBackground(new Color(0, 99, 0));
        txtValija.setBounds(new Rectangle(125, 10, 170, 20));
        txtValija.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtValija_keyPressed(e);
                }
            });
        jLabel2.setText("<html><center>C\u00f3digo de Valija</center></html>");
        jLabel2.setBounds(new Rectangle(20, 5, 95, 30));
        jLabel2.setForeground(SystemColor.window);
        jLabel2.setFont(new Font("Tahoma", 1, 11));
        /*
        pnlTitle1.add(txtPrecintos, new XYConstraints(115, 70, 195, 20));
        */
        // AAMPUERO 14.04.2014
        
        lblF1.setBounds(new Rectangle(170, 460, 65, 50));
        lblF1.setText("<HTML><CENTER>[F1] Ingresar Valija</CENTER></HTML>");
        lblPorDevolver.setText("<HTML><CENTER>[F8] Planificadas por Devolver</CENTER></HTML>");
        lblPorDevolver.setBounds(new Rectangle(5, 520, 145, 35));
        jPanel1.add(jLabel2, null);
        jPanel1.add(txtValija, null);
        jPanel1.add(txtBandeja, null);
        jPanel1.add(jLabel1, null);
        pnlTitle1.add(lblF1, null);
        pnlTitle1.add(jPanel1, null);
        pnlTitle1.add(jPanel2, null);
        pnlTitle1.add(lblCodPromocion, new XYConstraints(0, 10, 105, 15));
        pnlTitle1.add(lblEsc, null);
        pnlTitle1.add(lblF11, null);
        pnlTitle1.add(lblEnter,null);
        pnlTitle1.add(lblF5,null);
        pnlTitle1.add(lblPorDevolver, null);
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
        loadBandejas();
        getBandejasPorDevolver();
        FarmaUtility.moveFocus(txtValija);
    }

    private void initTableListaBandejas() {
        tblmBandeja =
                new FarmaTableModel(ConstantsRecepCiega.columnsListaIngresoBandeja, ConstantsRecepCiega.defaultValuesListaIngresoBandeja,
                                    0);
        FarmaUtility.initSimpleList(tblBandeja, tblmBandeja, ConstantsRecepCiega.columnsListaIngresoBandeja);
        tblBandeja.setName(ConstantsRecepCiega.NAME_TABLA_BANDEJAS);
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
        FarmaGridUtils.aceptarTeclaPresionada(e, tblBandeja, null, 0);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
            txtBandeja.setText(FarmaUtility.completeWithSymbol(txtBandeja.getText(), 10, "0", "I"));
            if(!txtBandeja.getText().trim().equalsIgnoreCase(FarmaUtility.completeWithSymbol("0", 10, "0", "I")))
            ingresoValorBandeja();
        }else if (UtilityPtoVenta.verificaVK_F1(e)) {
            FarmaUtility.moveFocus(txtValija);
        } 
        else if (UtilityPtoVenta.verificaVK_F11(e)) {
            grabarBandejas();
        } 
        else
        if (e.getKeyCode() == KeyEvent.VK_F8) {
                    consultaPorDevolver();
                } 
        else if (e.getKeyCode() == KeyEvent.VK_F5) {
            if(permiteBorrar(tblBandeja.getSelectedRow()))            
             borrarBandeja(tblBandeja.getSelectedRow());
            else
                FarmaUtility.showMessage(this, "No puede borrar esta bandeja.", txtBandeja);
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
    
    

    private void ingresoValorBandeja() {
        if (existeTextoCajaTexto(txtBandeja.getText())) {
                        if (validarCantidadDigitos()) {
                            if (existeTextoCajaTexto(txtBandeja.getText())) {
                                if (!validarRepetidos()) {
                                    if (flagExisteHojaResumen) {
                                        if(UtilityRecepCiega.isValidoBandeja(txtBandeja.getText())){
                                             if(UtilityRecepCiega.isValidoBandejaDevol(txtBandeja.getText())){
                                                agregarCodigoBandeja();
                                             }
                                             else
                                             FarmaUtility.showMessage(this,
                                                                      "La bulto ingresado no es el tipo correcto para devolver.",
                                                                      txtBandeja);
                                         } else {
                                            FarmaUtility.showMessage(this,
                                                                     "El número de bandeja no existe para la Hoja de Resumen ingresada",
                                                                     txtBandeja);
                                        }
                                    } else {
                                        agregarCodigoBandeja();
                                    }
                                } else {
                                    FarmaUtility.showMessage(this, "El número de bandeja ya fue ingresado", txtBandeja);
                                }
                            } else {
                                FarmaUtility.showMessage(this, "Ingrese número de Hoja de Resumen", txtBandeja);
                            }
                        } else {
                            FarmaUtility.showMessage(this, "El numero de bandeja debe tener " + MAX_DIGITOS + " digitos",
                                                     txtBandeja);
                        }
                    } else {
                        FarmaUtility.showMessage(this, "Ingrese número de Bandeja", txtBandeja);
                    }
    }

    private void jTextField1_focusLost(FocusEvent e) {
        
    }

    private void txtBandeja_keyPressed(KeyEvent e) {
        setIsLazerTeclado(e,txtBandeja);
        
        if (e.getKeyCode() == KeyEvent.VK_ENTER&&txtBandeja.getText().trim().length()==0) 
        { 
            FarmaUtility.moveFocus(txtValija);
        }
        else
          chkKeyPressed(e);
    }

    private void txtBandeja_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtBandeja, e);        
    }
    
    private boolean existeTextoCajaTexto(String texto) {
        boolean flag = true;
        if (texto.trim().equals("")) {
            flag = false;
        }
        return flag;
    }
    

    private boolean validarCantidadDigitos() {
        boolean flag = false;
        if (txtBandeja.getText().length() == MAX_DIGITOS) {
            flag = true;
        }
        return flag;
    }

    private boolean validarRepetidos() {
        boolean flag = false;
        String nroBandeja = txtBandeja.getText().trim();
        for (int c = 0; c < tblmBandeja.getRowCount(); c++) {
            String codigo = FarmaUtility.getValueFieldArrayList(tblmBandeja.data, c, 0).toString();
            if (codigo.equals(nroBandeja)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
    
    private void agregarCodigoBandeja() {
        String nroBandeja = txtBandeja.getText().trim();
        ArrayList lista = new ArrayList();
        lista.add(nroBandeja);
        lista.add("");
        lista.add("");
        lista.add("");
        lista.add("");
        lista.add("");
        if(isVIsLazerIngreso())
           lista.add(FarmaConstants.INDICADOR_S);
        else
           lista.add(FarmaConstants.INDICADOR_N);
        tblmBandeja.insertRow(lista);
        tblBandeja.repaint();
        txtBandeja.setText("");
        vOperaEliminados(nroBandeja,false);
    }

    private void grabarBandejas() {
        boolean vRevIngreso = true;
        //vRevIngreso = JConfirmDialog.rptaConfirmDialog(this,"¿Esta seguro de guardar los cambios?");
        //if(vRevIngreso){
        if(true){
            setVListGrabar((ArrayList)tblmBandeja.data.clone());
            setPCodValija(txtValija.getText().trim());
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

    private void borrarBandeja(int vPos) {
        if (vPos >= 0) {
            if (JConfirmDialog.rptaConfirmDialog(this, "¿Desea eliminar la bandeja seleccionada?")) {
            String pNumBandeja = FarmaUtility.getValueFieldArrayList(tblmBandeja.data,vPos, 0);
            tblmBandeja.deleteRow(vPos);
            tblBandeja.repaint();
            vOperaEliminados(pNumBandeja,true);
            }
        } else {
            FarmaUtility.showMessage(this, "No hay bandejas para eliminar de la lista.", txtBandeja);
        }
    }

    private void txtValija_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    FarmaUtility.moveFocus(txtBandeja);
                }else 
                   chkKeyPressed(e);
                }

    public void setPCodValija(String pCodValija) {
        this.pCodValija = pCodValija;
    }

    public String getPCodValija() {
        return pCodValija;
    }
    

    private void loadBandejas() {
        ArrayList vList = new ArrayList();
        vList = getVListaIn();
        tblmBandeja.data.clear();
        for (int g = 0; g < vList.size(); g++){
            ArrayList vFila = new ArrayList();
            vFila.add(vList.get(g));
            vFila.add("");
            vFila.add("");
            vFila.add("");
            vFila.add("");
            vFila.add("");
            vFila.add("N");
            tblmBandeja.insertRow(vFila);
        }
        tblBandeja.repaint();
    }

    public void setVListaIn(ArrayList vListaIn) {
        this.vListaIn = vListaIn;
    }

    public ArrayList getVListaIn() {
        return vListaIn;
    }

    private boolean permiteBorrar(int i) {
        if(isPVisualizar())
            return false;

        if(isPNuevo()) // nuevo si se puede modificar
            return true;        
        if(i>=0){
            ArrayList vList = new ArrayList();
            vList = getVListaIn();
            String pBandeja = FarmaUtility.getValueFieldArrayListW(tblmBandeja.data, i, 0);
            for(int a=0;a<vList.size();a++){
                String pLista = vList.get(a).toString();
                if(pLista.trim().equalsIgnoreCase(pBandeja.trim()))
                    return false;
            }
            return true;
        }
        else
            return false;
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

    private void getBandejasPorDevolver() {
        if(UtilityRecepCiega.existePorDevolver()){
            DlgPorDevolver dlgPor = new DlgPorDevolver(myParentFrame,"",true);
            dlgPor.setVListaPantallaOld(tblmBandeja.data);
            dlgPor.setVisible(true);
            if(FarmaVariables.vAceptar){
                // SI ACEPTO
                ArrayList vListaPorDevolver = dlgPor.getVListaPorDevolver();
                boolean vExiste = false;
                if(vListaPorDevolver.size()>0){
                    for (int g = 0; g < vListaPorDevolver.size(); g++){
                        ArrayList vFila = new ArrayList();
                        vFila= (ArrayList)vListaPorDevolver.get(g);
                        String pCodIn = vFila.get(0).toString();
                        for(int v = 0; v < tblmBandeja.data.size(); v++){
                            String pCodBandeja = FarmaUtility.getValueFieldArrayList(tblmBandeja.data,v,0);
                            if(pCodIn.trim().equalsIgnoreCase(pCodBandeja))
                                vExiste = true;
                        }
                        if(!vExiste)
                        tblmBandeja.insertRow(vFila);
                    }
                    tblBandeja.repaint();                
                    FarmaUtility.moveFocus(txtBandeja);             
                }
            }
        }
    }

    private void consultaPorDevolver() {
        if(UtilityRecepCiega.existePorDevolver()){
            DlgPorDevolver dlgPor = new DlgPorDevolver(myParentFrame,"",true);
            /*if(tblmBandeja.data.size()>0)
                FarmaUtility.showMessage(this," data a ver es mayor a cero",null);
            else
                FarmaUtility.showMessage(this," data a ver es menor a cero",null);*/
            dlgPor.setVListaPantallaOld(tblmBandeja.data);
            dlgPor.setVisible(true);
            if(FarmaVariables.vAceptar){
                // SI ACEPTO
                ArrayList vListaPorDevolver = dlgPor.getVListaPorDevolver();
                boolean vExiste = false;
                if(vListaPorDevolver.size()>0){
                    for (int g = 0; g < vListaPorDevolver.size(); g++){
                        ArrayList vFila = new ArrayList();
                        vFila= (ArrayList)vListaPorDevolver.get(g);
                        String pCodIn = vFila.get(0).toString();
                        for(int v = 0; v < tblmBandeja.data.size(); v++){
                            String pCodBandeja = FarmaUtility.getValueFieldArrayList(tblmBandeja.data,v,0);
                            if(pCodIn.trim().equalsIgnoreCase(pCodBandeja))
                                vExiste = true;
                        }
                        if(!vExiste)
                        tblmBandeja.insertRow(vFila);
                    }
                    tblBandeja.repaint();                
                    FarmaUtility.moveFocus(txtBandeja);
                }
            }
        
        }
        else
            FarmaUtility.showMessage(this, "No tiene bandejas planificadas por devolver.", txtBandeja);
    }
    
    public void vOperaEliminados(String pCadena,boolean vBorra){
        if(pCadena.trim().length()>0){
            if(vBorra){
                boolean vExiste = false;
                for(int i=0;i<vListaBorra.size();i++){
                    String pBandeja = FarmaUtility.getValueFieldArrayList(vListaBorra,i, 0).trim();
                    if(pBandeja.trim().equalsIgnoreCase(pCadena.trim())){
                        vExiste = true;
                        break;
                    }
                }
                if(!vExiste){
                ArrayList vFila = new ArrayList();
                vFila.add(pCadena);
                vListaBorra.add(vFila);
                }
            }
            else{
                boolean vExiste = false;
                int  pPos = 0;
                for(int i=0;i<vListaBorra.size();i++){
                    String pBandeja = FarmaUtility.getValueFieldArrayList(vListaBorra,i, 0).trim();
                    if(pBandeja.trim().equalsIgnoreCase(pCadena.trim())){
                        vExiste = true;
                        pPos = i;
                        break;
                    }
                }
                if(vExiste){
                 vListaBorra.remove(pPos);
                }
            }
        }
    }

    public void setVListaBorra(ArrayList vListaBorra) {
        this.vListaBorra = vListaBorra;
    }

    public ArrayList getVListaBorra() {
        return vListaBorra;
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
    
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
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
    

    
    public ArrayList getListaBandejaLazer() {
        ArrayList vLista = new ArrayList();
        for(int i=0;i<tblmBandeja.data.size();i++)
        {
            String pLazer = FarmaUtility.getValueFieldArrayList(tblmBandeja.data, i, 6);
            if(pLazer.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
               vLista.add(FarmaUtility.getValueFieldArrayList(tblmBandeja.data, i, 0));
        }
        return vLista;
    }
    
}
