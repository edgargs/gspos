package mifarma.ptoventa.caja;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;
import mifarma.ptoventa.FrmEconoFar;
import mifarma.ptoventa.caja.DlgDatosTipoTarjeta.JPanelImagenTransparente;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2015 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgDatosTarjetaNew.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * KMONCADA      04.09.2015   Creación<br>
 * <br>
 * @author KENNY MONCADA<br>
 * @version 1.0<br>
 *
 */
public class DlgDatosTarjetaNew extends JDialog {
    //Variables para el pago con tarjeta
    private static final Logger log = LoggerFactory.getLogger(DlgDatosTarjetaNew.class);

    private Frame myParentFrame;
    private JPanelWhite pnlCards = new JPanelWhite();
    private JPanelWhite pnlFondo = new JPanelWhite();
    private JPanel pnlInfo = new JPanel();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JLabelOrange lblMonto_T = new JLabelOrange();
    private JTextFieldSanSerif txtDescripcionTarjeta = new JTextFieldSanSerif();
    protected JTextFieldSanSerif txtNroTarjeta = new JTextFieldSanSerif();
    private JLabelOrange lblmon = new JLabelOrange();
    private JLabelOrange lblmoneda = new JLabelOrange();
    private JButtonLabel lblNroTarjeta = new JButtonLabel();
    private JButtonLabel lblTipoTarjeta = new JButtonLabel();
    private JLabelOrange lblmensaje = new JLabelOrange();
    private JButtonLabel lblCodVoucher = new JButtonLabel();
    private JTextFieldSanSerif txtCodVoucher = new JTextFieldSanSerif();
    private JLabelFunction lblEscape = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblF1 = new JLabelFunction();

    //KMONCADA 08.04.2015 VALIDA USO DE LECTORA DE BARRAS
    private JLabel lblVoucher = new JLabel();
    private JPanelImagenTransparente panImagenTarjeta;
    private PanelDeTeclado pnlAyuda;
    private JButtonLabel lblDescTarjeta = new JButtonLabel();
    private CardLayout cardLayout1 = new CardLayout();
    
    private int cantidadDigTarjetaSolicitar;

    public DlgDatosTarjetaNew() {
        this(null, "", false,null);
    }

    public DlgDatosTarjetaNew(Frame parent, String title, boolean modal, JPanelImagenTransparente panImagenTarjeta) {
        super(parent, title, modal);
        this.myParentFrame = parent;
        try {
            this.panImagenTarjeta = panImagenTarjeta;
            this.panImagenTarjeta.desactivaPanel();
            cantidadDigTarjetaSolicitar = DBCaja.getCantUltimosDigitosTarjetasASolicitar();
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }
    }
    
    private void jbInit() throws Exception {
        this.setSize(new Dimension(365, 303));
        this.getContentPane().setLayout(null);
        this.setTitle("Pago con Tarjeta");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });

        pnlCards.setBounds(new Rectangle(0, 0, 365, 280));
        pnlCards.setLayout(cardLayout1);

        pnlAyuda=new PanelDeTeclado();
        pnlAyuda.setBackground(new Color(0,0,0,65));
        pnlAyuda.setBounds(0,0,410,280);
        pnlAyuda.setFocusable(true);
        pnlAyuda.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    p2_keyPressed(e);
                }
            });
        
        lblVoucher = new JLabel(new ImageIcon(FrmEconoFar.class.getResource(panImagenTarjeta.getRutaImagenAyuda())));
        
        pnlAyuda.add(lblVoucher,null);


        pnlInfo.setBounds(new Rectangle(10, 25, 340, 205));
        pnlInfo.setBackground(Color.white);
        pnlInfo.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlInfo.setLayout(null);
        pnlInfo.setFocusable(false);
        
        pnlTitle.setBounds(new Rectangle(10, 5, 340, 20));
        pnlTitle.setFocusable(false);
        
        jLabelWhite1.setText("Pago con tarjeta");
        jLabelWhite1.setBounds(new Rectangle(5, 0, 110, 20));
        jLabelWhite1.setFocusable(false);
        
        lblTipoTarjeta.setText("Tipo Tarjeta");
        lblTipoTarjeta.setBounds(new Rectangle(15, 20, 75, 15));
        lblTipoTarjeta.setForeground(new Color(255, 130, 14));
        lblTipoTarjeta.setFocusable(false);
        
        panImagenTarjeta.setLocation(115, 1);
        
        lblDescTarjeta.setText("Descipción");
        lblDescTarjeta.setBounds(new Rectangle(15, 80, 75, 20));
        lblDescTarjeta.setForeground(new Color(255, 130, 14));
        lblDescTarjeta.setMnemonic('D');
        lblDescTarjeta.setFocusable(false);
        lblDescTarjeta.setHorizontalTextPosition(SwingConstants.CENTER);
        
        txtDescripcionTarjeta.setBounds(new Rectangle(115, 80, 140, 20));
        txtDescripcionTarjeta.setEditable(false);
        txtDescripcionTarjeta.setText(panImagenTarjeta.getNombrePanel());
        
        lblNroTarjeta.setText("Nro. Tarjeta");
        lblNroTarjeta.setBounds(new Rectangle(15, 115, 75, 15));
        lblNroTarjeta.setForeground(new Color(255, 130, 14));
        lblNroTarjeta.setMnemonic('n');
        lblNroTarjeta.setFocusable(false);
        lblNroTarjeta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblNroTarjeta_actionPerformed(e);
            }
        });
        
        txtNroTarjeta.setBounds(new Rectangle(115, 110, 100, 20));
        txtNroTarjeta.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                txtnrotarj_keyPressed(e);
            }
        });
        txtNroTarjeta.setDocument (new LimitadorCaracteres(txtNroTarjeta,cantidadDigTarjetaSolicitar));
        
        lblmensaje.setText("Ultimos "+cantidadDigTarjetaSolicitar+" digitos");
        lblmensaje.setBounds(new Rectangle(220, 115, 105, 15));
        lblmensaje.setForeground(new Color(49, 141, 43));
        lblmensaje.setFocusable(false);
        
        lblCodVoucher.setText("Codigo Voucher");
        lblCodVoucher.setBounds(new Rectangle(15, 145, 95, 15));
        lblCodVoucher.setForeground(new Color(255, 130, 14));
        lblCodVoucher.setMnemonic('c');
        lblCodVoucher.setFocusable(false);
        lblCodVoucher.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblCodVoucher_actionPerformed(e);
            }
        });
        
        txtCodVoucher.setBounds(new Rectangle(115, 140, 135, 20));
        txtCodVoucher.setDocument(new LimitadorCaracteres(txtCodVoucher,20));
        txtCodVoucher.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                
            }

            public void keyPressed(KeyEvent e) {
                txtcodvou_keyPressed(e);
            }
        });
        
        lblMonto_T.setText("Monto");
        lblMonto_T.setBounds(new Rectangle(15, 170, 80, 20));
        lblMonto_T.setFont(new Font("SansSerif", 1, 18));
        lblMonto_T.setFocusable(false);
        
        lblmoneda.setText("S/.");
        lblmoneda.setBounds(new Rectangle(160, 170, 30, 20));
        lblmoneda.setForeground(new Color(49, 141, 43));
        lblmoneda.setFont(new Font("SansSerif", 1, 18));
        lblmoneda.setFocusable(false);

        lblmon.setText("[MONTO]");
        lblmon.setBounds(new Rectangle(195, 170, 90, 20));
        lblmon.setForeground(new Color(49, 141, 43));
        lblmon.setFont(new Font("SansSerif", 1, 18));
        lblmon.setFocusable(false);

        lblF1.setBounds(new Rectangle(10, 240, 100, 25));
        lblF1.setText("[ F1 ] Ayuda");
        lblF1.setFocusable(false);
        
        lblF11.setBounds(new Rectangle(140, 240, 100, 25));
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setFocusable(false);
        
        lblEscape.setBounds(new Rectangle(250, 240, 95, 25));
        lblEscape.setText("[ ESC ] Escape");
        lblEscape.setFocusable(false);

        pnlTitle.add(jLabelWhite1, null);
        
        pnlFondo.add(lblF11, null);
        pnlFondo.add(lblF1, null);
        pnlFondo.add(lblEscape, null);
        pnlFondo.add(pnlTitle, null);
        pnlFondo.add(pnlInfo, null);

        pnlInfo.add(panImagenTarjeta, null);
        pnlInfo.add(txtCodVoucher, null);
        pnlInfo.add(lblCodVoucher, null);
        pnlInfo.add(lblmensaje, null);
        pnlInfo.add(lblTipoTarjeta, null);
        pnlInfo.add(lblDescTarjeta, null);
        pnlInfo.add(txtDescripcionTarjeta, null);
        pnlInfo.add(lblNroTarjeta, null);
        pnlInfo.add(lblmon, null);
        pnlInfo.add(lblmoneda, null);
        pnlInfo.add(txtNroTarjeta, null);
        pnlInfo.add(lblMonto_T, null);
        pnlCards.add(pnlFondo, "pnlFondo");
        pnlCards.add(pnlAyuda, "pnlAyuda");
        getContentPane().add(pnlCards, null);

    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtNroTarjeta);
        lblmon.setText(VariablesCaja.vValMontoPagado);
    }
    
    private void chkkeyPressed(KeyEvent e) {
        if(UtilityPtoVenta.verificaVK_F1(e)) {
            CardLayout cardLayout = (CardLayout) pnlCards.getLayout();
            cardLayout.show(pnlCards, "pnlAyuda");
            pnlAyuda.requestFocus();
        }else{
            if (UtilityPtoVenta.verificaVK_F11(e)) {
                if (validaCampos()) {
                    VariablesCaja.vCodFormaPago = panImagenTarjeta.getCodFormaPago();
                    VariablesCaja.vDescFormaPago = panImagenTarjeta.getNombrePanel();
                    VariablesCaja.vNumTarjCred = "********"+txtNroTarjeta.getText();
                    VariablesCaja.vFecVencTarjCred = "";
                    VariablesCaja.vNomCliTarjCred = "";
                    VariablesCaja.vDNITarj = "";
                    VariablesCaja.vCodVoucher = txtCodVoucher.getText();
                    VariablesCaja.vCodLote = "";
                    cerrarVentana(true);
                }
            } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                cerrarVentana(false);
            }
        }
    }
    
    private void txtnrotarj_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtCodVoucher);
        } else {
            chkkeyPressed(e);
        }
    }

    private void txtcodvou_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(txtNroTarjeta);
        }else{
            chkkeyPressed(e);
        }
    }

    private boolean validaCampos() {
        boolean flag = true;
        String auxNroTarjeta = txtNroTarjeta.getText();
        String auxCodVoucher = txtCodVoucher.getText();
        if (auxNroTarjeta == null || (auxNroTarjeta!=null && auxNroTarjeta.trim().length()==0)) {
            flag = false;
            FarmaUtility.showMessage(this, "Ingrese Nro. de Tarjeta", txtNroTarjeta);
        }else{
            if(auxNroTarjeta.trim().length()<cantidadDigTarjetaSolicitar){
                flag = false;
                FarmaUtility.showMessage(this, "Ingrese Nro. de Tarjeta completo.", txtNroTarjeta);
            }else{
                if (auxCodVoucher == null || (auxCodVoucher!=null && auxCodVoucher.trim().length()==0)) {
                    flag = false;
                    FarmaUtility.showMessage(this, "Ingrese Codigo de Voucher", txtNroTarjeta);
                }
            }
        }
        return flag;
    }

    private void lblNroTarjeta_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtNroTarjeta);
    }

    private void lblCodVoucher_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCodVoucher);
    }

    private void p2_keyPressed(KeyEvent e) {
        CardLayout cardLayout = (CardLayout) pnlCards.getLayout();
        cardLayout.show(pnlCards, "pnlFondo");
        log.info("debe pasar para ptrp lado");
        FarmaUtility.moveFocus(txtNroTarjeta);
    }

    class LimitadorCaracteres extends PlainDocument{
        int longMaxima = 4;
        JTextField campo;
        public LimitadorCaracteres(JTextField campo, int longMaxima){
            super();
            this.campo = campo;
            this.longMaxima = longMaxima;
        }
       /**
        * Método al que llama el editor cada vez que se intenta insertar caracteres.
        * Sólo debemos verificar arg1, que es la cadena que se quiere insertar en el JTextField
        */
       public void insertString(int arg0, String arg1, AttributeSet arg2) throws BadLocationException{
           if(campo.getText()!=null && campo.getText().length()==longMaxima){
               return;
           }
           for (int i=0;i<arg1.length();i++)
              if (!Character.isDigit(arg1.charAt(i)))
                 return;

           super.insertString(arg0, arg1, arg2);
       }
    } 
    
    class PanelDeTeclado extends JPanel implements KeyListener, MouseListener {

        public PanelDeTeclado() {
            this.addMouseListener(this);
            this.addKeyListener(this);
        }

        public boolean isFocusable() {
            return true;
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseClicked(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
            this.requestFocus();
        }

        public void mouseExited(MouseEvent e) {
        }

        public void keyReleased(KeyEvent e) {
        }

        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
            
        }
    }
}
