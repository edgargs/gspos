package mifarma.ptoventa.ventas;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;
import mifarma.ptoventa.main.FrmEconoFar;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2016 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgResumenBolsa.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS       25.04.2016   Creacion <br>
 * <br>
 * @author CCASTILLO<br>
 * @version 1.0<br>
 *
 */

public class DlgResumenBolsa extends JDialog implements KeyListener {
    /* ************************************************************************** */
    /*                           DECLARACION PROPIEDADES                          */
    /* ************************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgResumenBolsa.class);

    private Frame myParentFrame;
    private List listaBolsas;
    int LIM_MAX=20; 
    int[] cantbolsa=new int[LIM_MAX];
    String[] descbolsa=new String[LIM_MAX];

    
    private Icon optionIcon = new ImageIcon(FrmEconoFar.class.getResource("/mifarma/ptoventa/imagenes/alerta.jpg"));
    private JLabel dialogIcon = new JLabel(optionIcon);
    
    private URL url;
    
    //private Icon iconbolsa1 = new ImageIcon(FrmEconoFar.class.getResource("/mifarma/ptoventa/imagenes/bolsa_1.jpg"));
    private JLabel lblImgBolsa1 = new JLabel();
    private JLabelWhite btnCantBolsa1 = new JLabelWhite();
    private JLabel lblDescBolsa1 = new JLabel();
    
    //private Icon iconbolsa2 = new ImageIcon(FrmEconoFar.class.getResource("/mifarma/ptoventa/imagenes/bolsa_2.jpg"));
    private JLabel lblImgBolsa2 = new JLabel();
    private JLabelWhite btnCantBolsa2 = new JLabelWhite();
    private JLabel lblDescBolsa2 = new JLabel();
    
    //private Icon iconbolsa3 = new ImageIcon(FrmEconoFar.class.getResource("/mifarma/ptoventa/imagenes/bolsa_3.jpg"));
    private JLabel lblImgBolsa3 = new JLabel();
    private JLabelWhite btnCantBolsa3 = new JLabelWhite();
    private JLabel lblDescBolsa3 = new JLabel();
    
    //private Icon iconbolsa4 = new ImageIcon(FrmEconoFar.class.getResource("/mifarma/ptoventa/imagenes/bolsa_4.jpg"));
    private JLabel lblImgBolsa4 = new JLabel();
    private JLabelWhite btnCantBolsa4 = new JLabelWhite();
    private JLabel lblDescBolsa4 = new JLabel();
    
    //private Icon iconbolsa5 = new ImageIcon(FrmEconoFar.class.getResource("/mifarma/ptoventa/imagenes/bolsa_5.jpg"));
    private JLabel lblImgBolsa5 = new JLabel();
    private JLabelWhite btnCantBolsa5 = new JLabelWhite();
    private JLabel lblDescBolsa5 = new JLabel();
    
    //private Icon iconbolsa6 = new ImageIcon(FrmEconoFar.class.getResource("/mifarma/ptoventa/imagenes/bolsa_6.jpg"));
    private JLabel lblImgBolsa6 = new JLabel();
    private JLabelWhite btnCantBolsa6 = new JLabelWhite();
    private JLabel lblDescBolsa6 = new JLabel();
       
    
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JPanel jPanel1 = new JPanel();
    private JSeparator jSeparator1 = new JSeparator();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblEnter = new JLabelFunction();

    private JPanel jPanel5 = new JPanel();
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JLabel jLabel1 = new JLabel();
    
    
    private JPanel jPanelBolsas = new JPanel();

    /* ************************************************************************** */
    /*                              CONSTRUCTORES                                 */
    /* ************************************************************************** */

    public DlgResumenBolsa() {
        this(null, "", false,null);
    }

    public DlgResumenBolsa(Frame parent, String title, boolean modal,List listaBolsas) {
        super(parent, title, modal);
        myParentFrame = parent;
        this.listaBolsas=listaBolsas;    
        try {
            this.addKeyListener(this);
            setFocusable(true);
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    /* ************************************************************************** */
    /*                              METODO jbInit                                 */
    /* ************************************************************************** */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(636, 460));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setBackground(Color.white);
        jContentPane.setLayout(null);
        jContentPane.setSize(new Dimension(623, 439));
        jContentPane.setForeground(Color.white);
        jScrollPane1.setBounds(new Rectangle(15, 160, 700, 165));
        jScrollPane1.setBackground(new Color(255, 130, 14));
        jPanel1.setBounds(new Rectangle(15, 140, 600, 20));
        jPanel1.setBackground(new Color(255, 130, 14));
        jPanel1.setLayout(null);
        jSeparator1.setBounds(new Rectangle(150, 0, 15, 20));
        jSeparator1.setBackground(Color.black);
        jSeparator1.setOrientation(SwingConstants.VERTICAL);
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(530, 405, 85, 20));
        lblEnter.setText("[ ENTER ] Seleccionar");
        lblEnter.setBounds(new Rectangle(15, 355, 145, 20));
        jPanel5.setBounds(new Rectangle(15, 375, 600, 20)); //posicion izq - posicion arriba - largo - alto
        jPanel5.setBackground(new Color(255, 130, 14));
        jPanel5.setLayout(null);
        jPanelHeader1.setBounds(new Rectangle(15, 15, 600, 95));
        jPanelHeader1.setBackground(Color.red);
        jLabel1.setText("<HTML><CENTER>ENTREGAR LAS SIGUIENTES BOLSAS</CENTER></HTML>");
        jLabel1.setBounds(new Rectangle(150, 10, 440, 75));
        jLabel1.setForeground(SystemColor.window);
        jLabel1.setFont(new Font("Tahoma", 1, 20));
        jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        /*jPanelBolsas.setBounds(new Rectangle(15, 160, 815, 180));
        jPanelBolsas.setBackground(new Color(255, 130, 140));
        jPanelBolsas.setLayout(null);*/
        url=FrmEconoFar.class.getResource("/mifarma/ptoventa/imagenes/bolsa_pro01.jpg");
        
        lblImgBolsa1.setIcon(ajustarImagen(url,ConstantsVentas.NUM_BOLSA_1));
        lblImgBolsa1.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        lblImgBolsa1.setBounds(new Rectangle(15, 190, 135, 135)); //posicion izq - posicion arriba - largo - alto
        btnCantBolsa1.setBounds(new Rectangle(15, 310, 135, 65));
        lblDescBolsa1.setBounds(new Rectangle(15, 170, 135, 30));
        lblImgBolsa1.setHorizontalAlignment(SwingConstants.CENTER);
        lblImgBolsa1.setVerticalAlignment(SwingConstants.CENTER);
        
        btnCantBolsa1.setForeground(SystemColor.black);
        btnCantBolsa1.setFont(new Font("Tahoma", 1, 12));
        btnCantBolsa1.setHorizontalTextPosition(SwingConstants.CENTER);
        btnCantBolsa1.setHorizontalAlignment(SwingConstants.CENTER);
        
        lblDescBolsa1.setForeground(SystemColor.black);
        lblDescBolsa1.setFont(new Font("Tahoma", 1, 12));
        lblDescBolsa1.setHorizontalTextPosition(SwingConstants.CENTER);
        lblDescBolsa1.setHorizontalAlignment(SwingConstants.CENTER);
        
    
        lblImgBolsa2.setIcon(ajustarImagen(url,ConstantsVentas.NUM_BOLSA_2));
        lblImgBolsa2.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        lblImgBolsa2.setHorizontalAlignment(SwingConstants.CENTER);
        lblImgBolsa2.setVerticalAlignment(SwingConstants.CENTER);
        lblImgBolsa2.setBounds(new Rectangle(150, 190, 130, 135));
        btnCantBolsa2.setBounds(new Rectangle(150, 310, 130, 65));
        lblDescBolsa2.setBounds(new Rectangle(150, 170, 130, 30));
        
        btnCantBolsa2.setForeground(SystemColor.black);
        btnCantBolsa2.setFont(new Font("Tahoma", 1, 12));
        btnCantBolsa2.setHorizontalTextPosition(SwingConstants.CENTER);
        btnCantBolsa2.setHorizontalAlignment(SwingConstants.CENTER);
        
        lblDescBolsa2.setForeground(SystemColor.black);
        lblDescBolsa2.setFont(new Font("Tahoma", 1, 12));
        lblDescBolsa2.setHorizontalTextPosition(SwingConstants.CENTER);
        lblDescBolsa2.setHorizontalAlignment(SwingConstants.CENTER);
        
        lblImgBolsa3.setIcon(ajustarImagen(url,ConstantsVentas.NUM_BOLSA_3));
        lblImgBolsa3.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        lblImgBolsa3.setHorizontalAlignment(SwingConstants.CENTER);
        lblImgBolsa3.setVerticalAlignment(SwingConstants.CENTER);
        lblImgBolsa3.setBounds(new Rectangle(273, 190, 120, 135));
        btnCantBolsa3.setBounds(new Rectangle(273, 310, 120, 65));
        lblDescBolsa3.setBounds(new Rectangle(273, 170, 120, 30));
        
        btnCantBolsa3.setForeground(SystemColor.black);
        btnCantBolsa3.setFont(new Font("Tahoma", 1, 12));
        btnCantBolsa3.setHorizontalTextPosition(SwingConstants.CENTER);
        btnCantBolsa3.setHorizontalAlignment(SwingConstants.CENTER);
        
        lblDescBolsa3.setForeground(SystemColor.black);
        lblDescBolsa3.setFont(new Font("Tahoma", 1, 12));
        lblDescBolsa3.setHorizontalTextPosition(SwingConstants.CENTER);
        lblDescBolsa3.setHorizontalAlignment(SwingConstants.CENTER);
        
        lblImgBolsa4.setIcon(ajustarImagen(url,ConstantsVentas.NUM_BOLSA_4));
        lblImgBolsa4.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        lblImgBolsa4.setHorizontalAlignment(SwingConstants.CENTER);
        lblImgBolsa4.setVerticalAlignment(SwingConstants.CENTER);
        lblImgBolsa4.setBounds(new Rectangle(387, 190, 95, 135));
        btnCantBolsa4.setBounds(new Rectangle(387, 310, 95, 65));
        lblDescBolsa4.setBounds(new Rectangle(387, 170, 95, 30));
        
        btnCantBolsa4.setForeground(SystemColor.black);
        btnCantBolsa4.setFont(new Font("Tahoma", 1, 12));
        btnCantBolsa4.setHorizontalTextPosition(SwingConstants.CENTER);
        btnCantBolsa4.setHorizontalAlignment(SwingConstants.CENTER);
        
        lblDescBolsa4.setForeground(SystemColor.black);
        lblDescBolsa4.setFont(new Font("Tahoma", 1, 12));
        lblDescBolsa4.setHorizontalTextPosition(SwingConstants.CENTER);
        lblDescBolsa4.setHorizontalAlignment(SwingConstants.CENTER);
                
        lblImgBolsa5.setIcon(ajustarImagen(url,ConstantsVentas.NUM_BOLSA_5));
        lblImgBolsa5.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        lblImgBolsa5.setHorizontalAlignment(SwingConstants.CENTER);
        lblImgBolsa5.setVerticalAlignment(SwingConstants.CENTER);
        lblImgBolsa5.setBounds(new Rectangle(487, 190, 60, 135));
        btnCantBolsa5.setBounds(new Rectangle(487, 310, 60, 65));
        lblDescBolsa5.setBounds(new Rectangle(487, 170, 60, 30));
        
        btnCantBolsa5.setForeground(SystemColor.black);
        btnCantBolsa5.setFont(new Font("Tahoma", 1, 12));
        btnCantBolsa5.setHorizontalTextPosition(SwingConstants.CENTER);
        btnCantBolsa5.setHorizontalAlignment(SwingConstants.CENTER);
        
        lblDescBolsa5.setForeground(SystemColor.black);
        lblDescBolsa5.setFont(new Font("Tahoma", 1, 12));
        lblDescBolsa5.setHorizontalTextPosition(SwingConstants.CENTER);
        lblDescBolsa5.setHorizontalAlignment(SwingConstants.CENTER);
        
        lblImgBolsa6.setIcon(ajustarImagen(url,ConstantsVentas.NUM_BOLSA_6));
        lblImgBolsa6.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        lblImgBolsa6.setHorizontalAlignment(SwingConstants.CENTER);
        lblImgBolsa6.setVerticalAlignment(SwingConstants.CENTER);
        lblImgBolsa6.setBounds(new Rectangle(557, 190, 55, 135));
        btnCantBolsa6.setBounds(new Rectangle(557, 310, 55, 65));
        lblDescBolsa6.setBounds(new Rectangle(557, 170, 55, 30));
        
        btnCantBolsa6.setForeground(SystemColor.black);
        btnCantBolsa6.setFont(new Font("Tahoma", 1, 12));
        btnCantBolsa6.setHorizontalTextPosition(SwingConstants.CENTER);
        btnCantBolsa6.setHorizontalAlignment(SwingConstants.CENTER);
        
        lblDescBolsa6.setForeground(SystemColor.black);
        lblDescBolsa6.setFont(new Font("Tahoma", 1, 12));
        lblDescBolsa6.setHorizontalTextPosition(SwingConstants.CENTER);
        lblDescBolsa6.setHorizontalAlignment(SwingConstants.CENTER);
        
        
        dialogIcon.setBounds(new Rectangle(10, 5, 135, 85));
        jScrollPane1.getViewport();
        //jPanel1.add(jSeparator1, null);
        jPanelHeader1.add(dialogIcon, null);
        jPanelHeader1.add(jLabel1, null);
        jContentPane.add(lblImgBolsa1, null);
        jContentPane.add(btnCantBolsa1, null);
        jContentPane.add(lblDescBolsa1, null);
        jContentPane.add(lblImgBolsa2, null);
        jContentPane.add(btnCantBolsa2, null);
        jContentPane.add(lblDescBolsa2, null);
        jContentPane.add(lblImgBolsa3, null);
        jContentPane.add(btnCantBolsa3, null);
        jContentPane.add(lblDescBolsa3, null);
        jContentPane.add(lblImgBolsa4, null);
        jContentPane.add(btnCantBolsa4, null);
        jContentPane.add(lblDescBolsa4, null);
        jContentPane.add(lblImgBolsa5, null);
        jContentPane.add(btnCantBolsa5, null);
        jContentPane.add(lblDescBolsa5, null);
        jContentPane.add(lblImgBolsa6, null);
        jContentPane.add(btnCantBolsa6, null);
        jContentPane.add(lblDescBolsa6, null);
        jContentPane.add(jPanelHeader1, null);
        //jScrollPane1.getViewport().add(tblProductos, null);
        //jContentPane.add(jScrollPane1, null);
        jContentPane.add(jPanel5, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(jPanel1, null);
        //jContentPane.add(jPanelBolsas, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        //this.getContentPane().add(jContentPane, null);
    }

    /* ************************************************************************** */
    /*                            METODO initialize                               */
    /* ************************************************************************** */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        mostrarBolsas();
        repaintCantidad();
        repaintDescripcion();
    }
    
    private void mostrarBolsas(){
        int numeroBolsa=0;
        int cantidad=0;
        String descripcion="";
        log.info("*** mostrarBolsas ***");
        for (int i=1;i<listaBolsas.size();i++){ //OBTIENE RESULTADOS DESPUES DE LA CABECERA
         try{
            log.info("Código Bolsa : "+((String)((ArrayList)listaBolsas.get(i)).get(0)).trim()+" - "+
                        "Volumen : "+((String)((ArrayList)listaBolsas.get(i)).get(1)).trim()+" - "+
                        "Número  : "+((String)((ArrayList)listaBolsas.get(i)).get(2)).trim()+" - "+
                        "Cantidad : "+((String)((ArrayList)listaBolsas.get(i)).get(3)).trim()+" - "+
                        "Descripción : "+((String)((ArrayList)listaBolsas.get(i)).get(4)).trim());
                
                numeroBolsa=Integer.parseInt(((String)((ArrayList)listaBolsas.get(i)).get(2)).trim()); // tamaño en Número (1,2,3,4,5,6)
                cantidad=Integer.parseInt(((String)((ArrayList)listaBolsas.get(i)).get(3)).trim());    // cantidad de bolsas 
                descripcion=((String)((ArrayList)listaBolsas.get(i)).get(4)).trim();  // descripcion de la bolsa ejemplo 7"X10"
             
            }catch(Exception ex){
                log.info("**mostrarBolsas** Error en conversión");
                cantidad=0;
                descripcion="";
            }
            descbolsa[numeroBolsa]=descripcion;
            cantbolsa[numeroBolsa]+=cantidad; // Si se asigno una cantidad a un número de bolsa entonces sumar la nueva cantidad al número de bolsa
        }
    }
    
    private void repaintCantidad(){
        btnCantBolsa1.setText(evaluaCantidad(cantbolsa[ConstantsVentas.NUM_BOLSA_1]));
        btnCantBolsa2.setText(evaluaCantidad(cantbolsa[ConstantsVentas.NUM_BOLSA_2]));
        btnCantBolsa3.setText(evaluaCantidad(cantbolsa[ConstantsVentas.NUM_BOLSA_3]));
        btnCantBolsa4.setText(evaluaCantidad(cantbolsa[ConstantsVentas.NUM_BOLSA_4]));
        btnCantBolsa5.setText(evaluaCantidad(cantbolsa[ConstantsVentas.NUM_BOLSA_5]));
        btnCantBolsa6.setText(evaluaCantidad(cantbolsa[ConstantsVentas.NUM_BOLSA_6]));
    }
    
    private String evaluaCantidad(int cant){
        String valor="";  
        if(cant!=0){
            valor="<HTML><CENTER><h1>"+cant+"</h1></CENTER></HTML>";
        }else{
            valor="";
        }
        return valor;
    }
    
    private void repaintDescripcion(){
        lblDescBolsa1.setText(evaluaDescripcion(descbolsa[ConstantsVentas.NUM_BOLSA_1]));
        lblDescBolsa2.setText(evaluaDescripcion(descbolsa[ConstantsVentas.NUM_BOLSA_2]));
        lblDescBolsa3.setText(evaluaDescripcion(descbolsa[ConstantsVentas.NUM_BOLSA_3]));
        lblDescBolsa4.setText(evaluaDescripcion(descbolsa[ConstantsVentas.NUM_BOLSA_4]));
        lblDescBolsa5.setText(evaluaDescripcion(descbolsa[ConstantsVentas.NUM_BOLSA_5]));
        lblDescBolsa6.setText(evaluaDescripcion(descbolsa[ConstantsVentas.NUM_BOLSA_6]));
    }
    
    private String evaluaDescripcion(String desc){
        String valor="";  
        if(!(desc==null || desc.equals("") || desc=="")){
            valor="<HTML><CENTER>"+desc+"</CENTER></HTML>";
            valor=desc;
        }else{
            valor="";
        }
        return valor;
    }


    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);       
                                
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }


    /* ************************************************************************** */
    /*                       METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************** */

    
    private ImageIcon ajustarImagen(URL ico,int numero)
    {   
        int x=0,y=0;
        
        switch(numero){
            case 1:x=120;y=140;break;
            case 2:x=100;y=120;break;
            case 3:x=80;y=100;break;
            case 4:x=70;y=80;break;
            case 5:x=50;y=60;break;
            case 6:x=30;y=40;break;
            default: log.info("número de bolsa no encontrado");    
        }
        
        ImageIcon tmpIconAux = new ImageIcon(ico);
        ImageIcon tmpIcon = new ImageIcon(tmpIconAux.getImage().getScaledInstance(x, y, Image.SCALE_DEFAULT));
        return tmpIcon;
    }
    
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
    private void chkKeyPressed(KeyEvent e) {
         if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                cerrarVentana(true);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
}
