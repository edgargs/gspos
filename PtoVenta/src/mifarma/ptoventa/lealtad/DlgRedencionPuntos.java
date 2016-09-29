package mifarma.ptoventa.lealtad;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JNumericField;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.lealtad.reference.FacadeLealtad;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Pantalla de ingreso de puntos a redimir.
 * @author ERIOS
 * @since 19.02.2015
 */
public class DlgRedencionPuntos extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgRedencionPuntos.class);

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JLabelFunction lblESC = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JNumericField txtCantidad = new JNumericField();
    private JButtonLabel lblCantidad = new JButtonLabel();
    private FacadeLealtad facadeLealtad;
    private String pNumPedVta;
    private String puntos = "000";
    private String monto = "0.00";
    private JLabelWhite lblPuntosMaximo_T = new JLabelWhite();
    private double puntosMaximo;
    private double montoMaximo;
    private JLabelWhite lblPuntosMaximo = new JLabelWhite();
    private String indPuntosRedimir;

    public DlgRedencionPuntos() {
        this(null, "", false, "");
    }

    public DlgRedencionPuntos(Frame parent, String title, boolean modal, String indPuntosRedimir) {
        super(parent, title, modal);
        try {
            setIndPuntosRedimir(indPuntosRedimir);
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(427, 169));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Redencion Puntos");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
            
            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jPanelTitle1.setBounds(new Rectangle(20, 15, 390, 75));
        lblESC.setBounds(new Rectangle(305, 100, 100, 20));
        lblESC.setText("[ ESC ] Cerrar");
        lblF11.setBounds(new Rectangle(190, 100, 100, 20));
        lblF11.setText("[ F11 ] Aceptar");
        txtCantidad.setBounds(new Rectangle(160, 20, 160, 20));            
        txtCantidad.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtCantidad_keyPressed(e);
                }
            });
        lblCantidad.setText("Puntos a canjear:");
        lblCantidad.setBounds(new Rectangle(35, 20, 105, 20));
        lblCantidad.setMnemonic('P');
        lblCantidad.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    lblCantidad_actionPerformed(e);
                }
            });
        lblPuntosMaximo_T.setText("M\u00e1ximo de puntos a canjear: ");
        lblPuntosMaximo_T.setBounds(new Rectangle(165, 45, 165, 20));
        lblPuntosMaximo.setText("00000");
        lblPuntosMaximo.setBounds(new Rectangle(330, 45, 40, 20));
        jPanelWhite1.add(lblF11, null);
        jPanelWhite1.add(lblESC, null);
        jPanelWhite1.add(jPanelTitle1, null);
        jPanelTitle1.add(lblPuntosMaximo, null);
        jPanelTitle1.add(lblPuntosMaximo_T, null);
        jPanelTitle1.add(lblCantidad, null);
        jPanelTitle1.add(txtCantidad, null);
        this.getContentPane().add(jPanelWhite1, BorderLayout.CENTER);
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        calculaPuntosRedimir();
        FarmaUtility.moveFocus(txtCantidad);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ENTER para cerrar la ventana.", this);
    }

    private void lblCantidad_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCantidad);
    }
    
    private void txtCantidad_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }
    
    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F11(e) || e.getKeyCode() == KeyEvent.VK_ENTER) {
            funcionF11();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            //if(com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, "Debe anotar el número de tarjeta y pin generados.\nEstá seguro de continuar?")){
            cerrarVentana(false);
            //}
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void funcionF11() {        
        if(FarmaUtility.validateDecimal(this, txtCantidad, "Ingrese cantidad valida.", true)){
            String strCantidad = txtCantidad.getText();
            double cantidad = FarmaUtility.getDecimalNumber(strCantidad);
            if(validaMultiploIngreso(cantidad)){
                if(validaMaximoIngreso(cantidad,puntosMaximo)){
                    if(facadeLealtad.validaRedencion(this,txtCantidad,pNumPedVta,cantidad,puntosMaximo,montoMaximo)){
                        try {
                            puntos = strCantidad;                
                            monto = FarmaUtility.formatNumber(facadeLealtad.cacularMonto(cantidad,puntosMaximo,montoMaximo));
                            cerrarVentana(true);
                        } catch (Exception ex) {
                            FarmaUtility.showMessage(this,"Ha ocurrido un error inesperado.\n"+ex.getMessage(),null);
                        }                
                    }
                 }
                else{
                    FarmaUtility.showMessage(this,"La cantidad ingresada debe ser menor o igual  "+puntosMaximo,null);
                }
            } 
            else{
                //MSJ 
                FarmaUtility.showMessage(this,"La cantidad ingresada debe ser múltiplo de "+facadeLealtad.getMultiploIngresoPtos().trim(),null);
            }
        }
    }

    public void setFacadeLealtad(FacadeLealtad facadeLealtad) {
        this.facadeLealtad = facadeLealtad;
    }

    public void setNumPedVta(String pNumPedVta) {
        this.pNumPedVta = pNumPedVta;
    }

    private void calculaPuntosRedimir() {
        String puntos = facadeLealtad.obtienePuntosRedimir(pNumPedVta,montoMaximo,puntosMaximo,false);
        if("E".equals(indPuntosRedimir)){
            double dblPuntos = FarmaUtility.getDecimalNumber(puntos);
            puntos = ((int)dblPuntos)+"";
        }
        txtCantidad.setText(puntos);
        //Muestra mensaje de puntos máximo:
        lblPuntosMaximo.setText(puntos);
    }

    public String getPuntos() {
        return puntos;
    }

    public String getMonto() {
        return monto;
    }

    public void setPuntosMaximo(double puntosMaximo) {
        this.puntosMaximo = puntosMaximo;
    }

    public void setMontoMaximo(double montoMaximo) {
        this.montoMaximo = montoMaximo;
    }

    private void setIndPuntosRedimir(String indPuntosRedimir) {
        this.indPuntosRedimir = indPuntosRedimir;
        
        txtCantidad.setMaxLength(10); //Set maximum length             
        
        if("D".equals(indPuntosRedimir)){
            ;
        }else{//"E"
            txtCantidad.setFormat(JNumericField.NUMERIC);
        }
    }

    private boolean validaMultiploIngreso(double vPuntos) {
        double vValorMultiplo = Double.parseDouble(facadeLealtad.getMultiploIngresoPtos().trim());
        if(Math.abs(vPuntos)%Math.abs(vValorMultiplo)>0){
            return false;
        }
        else
            return true;
    }

    private boolean validaMaximoIngreso(double vPuntos,double vMaxPermitido) {
        if(vPuntos>vMaxPermitido){
            return false;
        }
        else
            return true;
    }    
    
}
