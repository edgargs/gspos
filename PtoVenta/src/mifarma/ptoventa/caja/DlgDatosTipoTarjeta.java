package mifarma.ptoventa.caja;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;
import mifarma.ptoventa.main.FrmEconoFar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgDatosTipoTarjeta.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * KMONCADA      04.09.2015   Creación<br>
 * <br>
 * @author KENNY MONCADA<br>
 * @version 1.0<br>
 *
 */
public class DlgDatosTipoTarjeta extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgDatosTipoTarjeta.class);

    private Frame myParentFrame;
    private JPanelWhite pnlFondo = new JPanelWhite();
    private JPanel pnlInfo = new JPanel();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JTextFieldSanSerif txtNombreTarjeta = new JTextFieldSanSerif();
    private JButtonLabel lblNroTarjeta = new JButtonLabel();
    private JLabelFunction lblF11 = new JLabelFunction();

    private JPanel pnlTipoTarjeta = new JPanel();
    /*private JPanelImagenTransparente pnlAmex;
    private JPanelImagenTransparente pnlMasterCard;
    private JPanelImagenTransparente pnlVisa;
    private JPanelImagenTransparente pnlDiners;
    private JPanelImagenTransparente pnlCmr;*/

    private int cantidadLargo = 5;
    private List<JPanelImagenTransparente> lstPanelTarjetas = new ArrayList<JPanelImagenTransparente>();
    
    private List lstTarjetaLogo;


    public DlgDatosTipoTarjeta() {
        this(null, "", false);
    }

    public DlgDatosTipoTarjeta(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        this.myParentFrame = parent;
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(416, 243));
        this.getContentPane().setLayout(null);
        this.setTitle("Pago con Tarjeta");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        
        /*pnlVisa = new JPanelImagenTransparente(0);
        pnlMasterCard = new JPanelImagenTransparente(1);
        pnlAmex = new JPanelImagenTransparente(2);
        pnlDiners = new JPanelImagenTransparente(3);
        pnlCmr = new JPanelImagenTransparente(4);*/

        pnlFondo.setBounds(new Rectangle(0, 0, 415, 220));
        
        pnlInfo.setBounds(new Rectangle(10, 25, 390, 150));
        pnlInfo.setBackground(Color.white);
        pnlInfo.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlInfo.setLayout(null);
        pnlInfo.setFocusable(false);
        
        pnlTitle.setBounds(new Rectangle(10, 5, 390, 20));
        pnlTitle.setFocusable(false);
        
        jLabelWhite1.setText("Seleccione Tipo de Tarjeta");
        jLabelWhite1.setBounds(new Rectangle(5, 0, 160, 20));
        jLabelWhite1.setFocusable(false);
        
        txtNombreTarjeta.setBounds(new Rectangle(130, 120, 165, 20));
        txtNombreTarjeta.setEditable(false);
        txtNombreTarjeta.setFont(new Font("SansSerif", 1, 13));
        txtNombreTarjeta.setForeground(new Color(198, 0, 0));
        txtNombreTarjeta.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtnrotarj_keyTyped(e);
            }

            public void keyPressed(KeyEvent e) {
                txtnrotarj_keyPressed(e);
            }
        });

        lblNroTarjeta.setText("Tarjeta");
        lblNroTarjeta.setBounds(new Rectangle(70, 120, 60, 20));
        lblNroTarjeta.setForeground(new Color(255, 130, 14));
        lblNroTarjeta.setMnemonic('n');
        lblNroTarjeta.setFocusable(false);
        lblNroTarjeta.setFont(new Font("SansSerif", 1, 13));
        lblNroTarjeta.setHorizontalTextPosition(SwingConstants.CENTER);
        lblNroTarjeta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblNroTarjeta_actionPerformed(e);
            }
        });

        pnlTitle.add(jLabelWhite1, null);

        pnlFondo.add(lblF11, null);
        pnlFondo.add(pnlTitle, null);
        pnlFondo.add(pnlInfo, null);
        
        pnlTipoTarjeta.setBackground(SystemColor.window);
        pnlTipoTarjeta.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
                                                                                                  new Color(43,141,39)),
                                                                  "Tipo de Tarjeta",
                                                                  TitledBorder.DEFAULT_JUSTIFICATION,
                                                                  TitledBorder.DEFAULT_POSITION,
                                                                  new Font("SansSerif", 1, 12),
                                                                  new Color(43,141,39)));
        pnlTipoTarjeta.setLayout(null);

        pnlTipoTarjeta.setBounds(new Rectangle(10, 5, 370, 110));
        pnlTipoTarjeta.setOpaque(false);

        /*agregarPanelTarjeta(pnlVisa);
        agregarPanelTarjeta(pnlMasterCard);
        agregarPanelTarjeta(pnlAmex);
        agregarPanelTarjeta(pnlDiners);*/
        //agregarPanelTarjeta(pnlCmr); // KMONCADA 17.03.2016 CAMBIO SOLICITADO POR RCASTRO
        pnlInfo.add(pnlTipoTarjeta, null);
        pnlInfo.add(lblNroTarjeta, null);
        pnlInfo.add(txtNombreTarjeta, null);
        this.getContentPane().add(pnlFondo, null);
        lblF11.setText("[ ENTER ] Aceptar");
        lblF11.setFocusable(false);
        //lstPanelTarjetas.get(0).activaPanel(txtNombreTarjeta);

    }

    protected void agregarPanelTarjeta(JPanelImagenTransparente panel){
        int longPanel = 70;
        int altoPanel = 80;
        int cantidadPaneles = lstPanelTarjetas.size();
        int altoPanelContenedor = 30;
        int posX = 10;
        int posY = 20;
        int cantidadFila = (cantidadPaneles/cantidadLargo);
        posY = 20+(altoPanel*(cantidadFila));
        posX = 10+(longPanel*(cantidadPaneles%cantidadLargo));

        panel.setBounds(new Rectangle(posX, posY, longPanel, altoPanel));
        altoPanelContenedor = altoPanelContenedor + (altoPanel*(cantidadFila+1));
        pnlTipoTarjeta.setBounds(new Rectangle(10, 5, 370, altoPanelContenedor));
        pnlTipoTarjeta.add(panel, null);
        lstPanelTarjetas.add(panel);
        this.setSize(new Dimension(416, (133+altoPanelContenedor)));
        txtNombreTarjeta.setBounds(new Rectangle(130, altoPanelContenedor+10, 165, 20));
        lblNroTarjeta.setBounds(new Rectangle(70, altoPanelContenedor+10, 60, 20));
        pnlInfo.setBounds(new Rectangle(10, 25, 390, altoPanelContenedor+40));
        lblF11.setBounds(new Rectangle(145, altoPanelContenedor+75, 120, 25));
        pnlFondo.setBounds(new Rectangle(0, 0, 415, (110+altoPanelContenedor)));
        repaint();
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        for(int i=0; i<lstTarjetaLogo.size(); i++){
            Map mapTarjeta = (Map)lstTarjetaLogo.get(i);
            JPanelImagenTransparente pan = new JPanelImagenTransparente(mapTarjeta);
            agregarPanelTarjeta(pan);
        }
        lstPanelTarjetas.get(0).activaPanel(txtNombreTarjeta);
        txtNombreTarjeta.requestFocus();
    }
    
    private void teclasDireccionales(KeyEvent e){
        int posicionActual = -1;
        int posicionNueva = -1;
        int longitud = lstPanelTarjetas.size();
        for(int i = 0; i < longitud; i++){
            if( lstPanelTarjetas.get(i).getActivo() == 1){
                posicionActual=i;
                break;
            }
        }
        if(posicionActual!=-1){
            switch(e.getKeyCode()){
                case KeyEvent.VK_RIGHT :
                    posicionNueva = posicionActual + 1;
                    if((posicionNueva+1)>longitud){
                        posicionNueva = 0;
                    }
                    break;
                case KeyEvent.VK_LEFT :
                    posicionNueva = posicionActual - 1;
                    if(posicionNueva < 0){
                        posicionNueva = longitud - 1;
                    }
                    break;
                case KeyEvent.VK_DOWN :
                    posicionNueva = posicionActual + cantidadLargo;
                    if((posicionNueva+1)>longitud){
                        posicionNueva = posicionActual % cantidadLargo;
                    }
                    break;
                case KeyEvent.VK_UP :
                    posicionNueva = posicionActual - cantidadLargo;
                    if(posicionNueva < 0){
                        posicionNueva = (((longitud/cantidadLargo)*cantidadLargo) + posicionActual);
                        if((posicionNueva + 1) >longitud){
                            posicionNueva = posicionActual;
                        }
                    }
                    break;
            }
            
            if(posicionNueva!=-1){
                lstPanelTarjetas.get(posicionActual).desactivaPanel();
                lstPanelTarjetas.get(posicionNueva).activaPanel(txtNombreTarjeta);
                repaint();
            }
        }
    }
    
    private void txtnrotarj_keyTyped(KeyEvent e) {

    }
    
    private void txtnrotarj_keyPressed(KeyEvent e) {
        
        switch(e.getKeyCode()){
            case KeyEvent.VK_ENTER :
                cerrarVentana(true);
                break;
            case KeyEvent.VK_RIGHT :
            case KeyEvent.VK_LEFT :
            case KeyEvent.VK_DOWN :
            case KeyEvent.VK_UP :
                teclasDireccionales(e);
                break;
            case KeyEvent.VK_ESCAPE:
                cerrarVentana(false);
                break;
        }
    }
    
    private void lblNroTarjeta_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtNombreTarjeta);
    }
    
    public JPanel getPanelSeleccionado(){
        JPanelImagenTransparente panel = null;
        for(int i=0;i<lstPanelTarjetas.size();i++){
            if(lstPanelTarjetas.get(i).getActivo()==1){
                panel  = lstPanelTarjetas.get(i);
                break;
            }
        }
        return panel;
    }
    
    public class JPanelImagenTransparente extends JPanel{
        private JLabel lblContenido = new JLabel();
        private JPanel pnlTransparencia = new JPanel();
        private String nombrePanel;
        private int activo=0;
        private int indiceTransparencia=50;
        private String codFormaPago;
        private String rutaImagenAyuda;
        private String rutaImagenes = "/mifarma/ptoventa/imagenes/tarjetas/";
        
        public JPanelImagenTransparente(Map mapTarjeta){
            lblContenido = new JLabel(new ImageIcon(FrmEconoFar.class.getResource(rutaImagenes+((String)mapTarjeta.get("IMG_LOGO")))));
            this.nombrePanel = (String)mapTarjeta.get("NOM_TARJETA");
            this.codFormaPago = (String)mapTarjeta.get("COD_FORMA_PAGO");
            this.rutaImagenAyuda = rutaImagenes+((String)mapTarjeta.get("IMG_AYUDA"));
            pnlTransparencia.setBounds(0,0,70,80);
            lblContenido.setBounds(0,0,70,80);
            
            pnlTransparencia.setBackground(new Color(0,0,0,0));
            setSize(70, 80);
            setBackground(new Color(0,0,0,0));
            setLayout(null);
            
            add(pnlTransparencia, "b");
            add(lblContenido, "a");
        }
        
        public JPanelImagenTransparente(int posicionLogo){
            switch(posicionLogo){
                case 0 :    lblContenido = new JLabel(new ImageIcon(FrmEconoFar.class.getResource("/mifarma/ptoventa/imagenes/tarjetas/visa.jpg")));
                            this.nombrePanel = "VISA";
                            this.codFormaPago = "00003";
                            this.rutaImagenAyuda = "/mifarma/ptoventa/imagenes/tarjetas/visa_voucher.png";
                            break;
                case 1 :    lblContenido = new JLabel(new ImageIcon(FrmEconoFar.class.getResource("/mifarma/ptoventa/imagenes/tarjetas/mastercard.png")));
                            this.nombrePanel = "MASTERCARD";
                            this.codFormaPago = "00006";
                            this.rutaImagenAyuda = "/mifarma/ptoventa/imagenes/tarjetas/mastercard_voucher.png";
                            break;
                case 2 :    lblContenido = new JLabel(new ImageIcon(FrmEconoFar.class.getResource("/mifarma/ptoventa/imagenes/tarjetas/amex.png")));
                            this.nombrePanel = "AMERICAN EXPRESS";
                            this.codFormaPago = "00017";
                            this.rutaImagenAyuda = "/mifarma/ptoventa/imagenes/tarjetas/amex_voucher.png";
                            break;
                case 3 :    lblContenido = new JLabel(new ImageIcon(FrmEconoFar.class.getResource("/mifarma/ptoventa/imagenes/tarjetas/diners-club.png")));
                            this.nombrePanel = "DINERS CLUB";
                            this.codFormaPago = "00009";
                            this.rutaImagenAyuda = "/mifarma/ptoventa/imagenes/tarjetas/diners-club_voucher.png";
                            break;
                case 4 :    lblContenido = new JLabel(new ImageIcon(FrmEconoFar.class.getResource("/mifarma/ptoventa/imagenes/tarjetas/cmr.png")));
                            this.nombrePanel = "TARJETA CMR";
                            this.codFormaPago = "00024";
                            this.rutaImagenAyuda = "/mifarma/ptoventa/imagenes/tarjetas/cmr_voucher.png";
                            break;
            
            }
            
            pnlTransparencia.setBounds(0,0,70,80);
            lblContenido.setBounds(0,0,70,80);
            
            pnlTransparencia.setBackground(new Color(0,0,0,0));
            setSize(70, 80);
            setBackground(new Color(0,0,0,0));
            setLayout(null);
            
            add(pnlTransparencia, "b");
            add(lblContenido, "a");
            
        }
        
        public String getNombrePanel(){
            return nombrePanel;
        }
        
        public int getActivo(){
            return this.activo;
        }
        
        public String getCodFormaPago(){
            return this.codFormaPago;
        }
        
        public void desactivaPanel(){
            pnlTransparencia.setBackground(new Color(0,0,0,0));
            activo = 0;
        }
                
        public void activaPanel(JTextField campoTexto){
            pnlTransparencia.setBackground(new Color(0,0,0,indiceTransparencia));
            activo = 1;
            if(campoTexto!=null){
                campoTexto.setText(nombrePanel);
            }
        }
        
        public String getRutaImagenAyuda(){
            return this.rutaImagenAyuda;
        }
    }
    
    public void setLstTarjetaLogo(List lstTarjetaLogo){
        this.lstTarjetaLogo = lstTarjetaLogo;
    }
}