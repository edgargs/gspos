package mifarma.ptoventa.lectorHuella;

import com.digitalpersona.onetouch.DPFPFingerIndex;

import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;

import javax.swing.SwingConstants;

import mifarma.common.FarmaLoadCVL;

import mifarma.common.FarmaUtility;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.usuarios.DlgListaUsuarios;
import mifarma.ptoventa.lectorHuella.modelo.UsuarioFV;
import mifarma.ptoventa.lectorHuella.reference.UtilityLectorHuella;

import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgSelectorHuella extends JDialog {
    private Frame myParentFrame;
    private static final Logger log = LoggerFactory.getLogger(DlgSelectorHuella.class);
    
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();

    private List<Boolean[]> lstSolicitudes;
    
    private JButton btnDedoHuella = new JButton();
    private JButton btnMano = new JButton();
    private JComboBox cmbDedoHuella = new JComboBox();
    private JComboBox cmbMano = new JComboBox();
    
    private JLabelFunction lblF11 = new JLabelFunction();
    
    private DPFPFingerIndex posicionHuella = null;
    

    public DlgSelectorHuella() {
        this(null, "", false);
    }

    public DlgSelectorHuella(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(212, 142));
        this.getContentPane().setLayout(borderLayout1);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        this.setTitle("Selector de Huellas");
        jContentPane.setFocusable(true);

        btnDedoHuella.setText("Huella: ");
        btnDedoHuella.setBounds(new Rectangle(10, 55, 55, 20));
        btnDedoHuella.setBorderPainted(false);
        btnDedoHuella.setContentAreaFilled(false);
        btnDedoHuella.setDefaultCapable(false);
        btnDedoHuella.setFocusPainted(false);
        btnDedoHuella.setHorizontalAlignment(SwingConstants.LEFT);
        btnDedoHuella.setMnemonic('h');
        btnDedoHuella.setRequestFocusEnabled(false);
        btnDedoHuella.setFont(new Font("SansSerif", 1, 11));
        btnDedoHuella.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnDedoHuella.setActionCommand("Huella: ");
        btnDedoHuella.setForeground(new Color(255, 130, 14));
        btnDedoHuella.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //btnMonto_actionPerformed(e);
                    btnDedoHuella_actionPerformed(e);
                }
            });
        
        btnMano.setText("Mano: ");
        btnMano.setBounds(new Rectangle(10, 25, 55, 20));
        btnMano.setBorderPainted(false);
        btnMano.setContentAreaFilled(false);
        btnMano.setDefaultCapable(false);
        btnMano.setFocusPainted(false);
        btnMano.setHorizontalAlignment(SwingConstants.LEFT);
        btnMano.setMnemonic('m');
        btnMano.setRequestFocusEnabled(false);
        btnMano.setFont(new Font("SansSerif", 1, 11));
        btnMano.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnMano.setActionCommand("Mano: ");
        btnMano.setForeground(new Color(255, 130, 14));
        btnMano.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //btnMonto_actionPerformed(e);
                    btnMano_actionPerformed(e);
                }
            });
        
        cmbDedoHuella.setBounds(new Rectangle(70, 55, 125, 20));
        cmbDedoHuella.setName("CMBDEDOHUELLA");
        cmbDedoHuella.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                eventoKeyPressed(e);
            }
        });
        cmbDedoHuella.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    cmbDedoHuella_itemStateChanged(e);
                }
            });
        
        cmbMano.setBounds(new Rectangle(70, 25, 125, 20));
        cmbMano.setName("CMBMANO");
        cmbMano.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    cmbMano_itemStateChanged(e);
                }
            });
        cmbMano.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                eventoKeyPressed(e);
            }
        });
        
        lblF11.setBounds(new Rectangle(55, 90, 105, 20));
        lblF11.setText("[F11] Continuar");
        
        jContentPane.add(btnMano, null);
        jContentPane.add(cmbMano, null);
        jContentPane.add(btnDedoHuella, null);
        jContentPane.add(cmbDedoHuella, null);

        jContentPane.add(lblF11, null);
        this.getContentPane().add(jContentPane, null);
        //this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                //chkKeyPressed(e);
            }
        });
    }
    
    
    private void initialize(){
        cargarSeleccionMano();
    }
    
    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(cmbMano);
    }
    
    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    
    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    
    private void cargarSeleccionMano(){
        int cantSolicitaHuellaDer = 0;
        int cantSolicitaHuellaIzq = 0;
        
        lstSolicitudes = UtilityLectorHuella.obtieneHuellasSolicitar(this);
        
        for(int m=0; m < lstSolicitudes.get(0).length; m++){
            if(lstSolicitudes.get(0)[m]){
                cantSolicitaHuellaIzq++;
            }
        }
        
        for(int m=0; m < lstSolicitudes.get(1).length; m++){
            if(lstSolicitudes.get(1)[m]){
                cantSolicitaHuellaDer++;
            }
        }
        ArrayList lstManos = new ArrayList();
        ArrayList<String> mano;
        if(cantSolicitaHuellaIzq>0){
            mano = new ArrayList<String>();
            mano.add("0");
            mano.add("IZQUIERDA");
            lstManos.add(mano);
        }
        if(cantSolicitaHuellaDer>0){
            mano = new ArrayList<String>();
            mano.add("1");
            mano.add("DERECHA");
            lstManos.add(mano);
        }
        FarmaLoadCVL.loadCVLFromArrayList(cmbMano, cmbMano.getName(), lstManos, true);
        cargarSeleccionHuellaMano();
    }
    
    private void cargarSeleccionHuellaMano(){
        int indiceMano = Integer.parseInt(FarmaLoadCVL.getCVLCode(cmbMano.getName(), cmbMano.getSelectedIndex()));
        int posicionInicial = 0;
        ArrayList lstHuella = new ArrayList();
        ArrayList<String> filaHuella = new ArrayList<String>();
        //MANO DERECHA
        if(indiceMano==1){
            posicionInicial = 5;
        }
        
        for(int k = 0; k < 5; k++){
            filaHuella = new ArrayList<String>();
            switch(k+posicionInicial){
            case 0 : case 9:
                if(lstSolicitudes.get(indiceMano)[k]){
                    filaHuella.add(""+(k+posicionInicial));
                    filaHuella.add("MEÑIQUE");
                    lstHuella.add(filaHuella);
                }
                break;
            case 1 : case 8:
                if(lstSolicitudes.get(indiceMano)[k]){
                    filaHuella.add(""+(k+posicionInicial));
                    filaHuella.add("ANULAR");
                    lstHuella.add(filaHuella);
                }
                break;
            case 2 : case 7:
                if(lstSolicitudes.get(indiceMano)[k]){
                    filaHuella.add(""+(k+posicionInicial));
                    filaHuella.add("MEDIO");
                    lstHuella.add(filaHuella);
                }
                break;
            case 3 : case 6:
                if(lstSolicitudes.get(indiceMano)[k]){
                    filaHuella.add(""+(k+posicionInicial));
                    filaHuella.add("INDICE");
                    lstHuella.add(filaHuella);
                }
                break;
            case 4 : case 5:
                if(lstSolicitudes.get(indiceMano)[k]){
                    filaHuella.add(""+(k+posicionInicial));
                    filaHuella.add("PULGAR");
                    lstHuella.add(filaHuella);
                }
                break;
            }
            
        }
        FarmaLoadCVL.loadCVLFromArrayList(cmbDedoHuella, cmbDedoHuella.getName(), lstHuella, false);
        if(cmbMano.getItemCount() == 1 && cmbDedoHuella.getItemCount() == 2){
            cmbDedoHuella.setSelectedIndex(1);
            capturarHuellaSeleccionada();
        }else{
            btnDedoHuella.doClick();
        }
    }
    
    private void capturarHuellaSeleccionada(){
        if(cmbDedoHuella.getSelectedIndex() > 0){
            int indHuella = Integer.parseInt(FarmaLoadCVL.getCVLCode(cmbDedoHuella.getName(), cmbDedoHuella.getSelectedIndex()));
            posicionHuella = DPFPFingerIndex.values()[indHuella];
            cerrarVentana(true);
        }else{
            FarmaUtility.showMessage(this, "No ha seleccionado ninguna huella a capturar.", cmbDedoHuella);
            posicionHuella = null;
        }
    }

    private void btnDedoHuella_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbDedoHuella);
    }

    private void btnMano_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbMano);
    }

    private void cmbDedoHuella_itemStateChanged(ItemEvent e) {
    }

    private void eventoKeyPressed(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_ENTER && e.getSource() == cmbMano){
            cmbDedoHuella.removeAllItems();
            cargarSeleccionHuellaMano();
        }else if(UtilityPtoVenta.verificaVK_F11(e)){
            capturarHuellaSeleccionada();
        }else if(e.getKeyChar() == KeyEvent.VK_ESCAPE ){
            cerrarVentana(true);
        }
    }

    private void cmbMano_itemStateChanged(ItemEvent e) {
    }

    public DPFPFingerIndex getPosicionHuella() {
        return posicionHuella;
    }
}
