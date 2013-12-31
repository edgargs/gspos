package mifarma.ptoventa.pinpad;

import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Frame;

import java.awt.Rectangle;

import java.awt.event.KeyAdapter;

import java.awt.event.KeyEvent;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.inventario.DlgTransferenciasRealizadas;
import mifarma.ptoventa.pinpad.reference.DBPinpad;
import mifarma.ptoventa.pinpad.reference.UtilityPinpad;
import mifarma.ptoventa.recetario.reference.UtilityRecetario;
import mifarma.ptoventa.recetario.reference.VariablesRecetario;
import mifarma.ptoventa.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgEleccionTranAnularPinpad extends JDialog {
    Frame myParentFrame;
    private static final Logger log = LoggerFactory.getLogger(DlgEleccionTranAnularPinpad.class);
    
    private JPanelWhite pnlFondo = new JPanelWhite();
    private CardLayout cardLayout1 = new CardLayout();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JPanel jPanel1 = new JPanel();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelOrange lblTipoTarjeta = new JLabelOrange();
    private JLabelOrange lblNumAutorizacion = new JLabelOrange();
    private JLabelOrange lblNumReferencia = new JLabelOrange();
    private JLabelOrange lblFecha = new JLabelOrange();
    private JTextFieldSanSerif txtNumAutorizacion = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtNumReferencia = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFecha = new JTextFieldSanSerif();
    private JLabelWhite lblTitle = new JLabelWhite();
    private JComboBox cmbTipoTarjeta = new JComboBox();

    private String monto = "";
    private String formaPago = "";
    
    public DlgEleccionTranAnularPinpad() {
        this(null, "", false);
    }

    public DlgEleccionTranAnularPinpad(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
        } catch (Exception e) {
            log.error("",e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(400, 291));
        this.getContentPane().setLayout(cardLayout1);
        this.setTitle("Anulación de Transacción de Pinpad");
        pnlFondo.setFocusable(false);
        pnlTitle.setBounds(new Rectangle(5, 10, 385, 25));
        pnlTitle.setFocusable(false);
        jPanel1.setBounds(new Rectangle(5, 35, 385, 190));
        jPanel1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(null);
        jPanel1.setFocusable(false);
        lblEsc.setText("[ ESC] Cerrar");
        lblEsc.setBounds(new Rectangle(285, 230, 105, 30));
        lblEsc.setHorizontalAlignment(SwingConstants.CENTER);
        lblEsc.setFocusable(false);
        lblTipoTarjeta.setText("Tipo Tarjeta:");
        lblTipoTarjeta.setBounds(new Rectangle(20, 30, 140, 20));
        lblTipoTarjeta.setFocusable(false);
        lblNumAutorizacion.setText("Num. Autorización (AP):");
        lblNumAutorizacion.setBounds(new Rectangle(20, 70, 135, 20));
        lblNumAutorizacion.setFocusable(false);
        lblNumReferencia.setText("Num. Referencia (REF):");
        lblNumReferencia.setBounds(new Rectangle(20, 105, 130, 20));
        lblNumReferencia.setFocusable(false);
        lblFecha.setText("Fecha:");
        lblFecha.setBounds(new Rectangle(20, 140, 125, 20));
        lblFecha.setFocusable(false);
        txtNumAutorizacion.setBounds(new Rectangle(160, 70, 180, 20));
        txtNumAutorizacion.setNextFocusableComponent(txtNumReferencia);
        txtNumAutorizacion.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtNumAutorizacion_keyPressed(e);
                }

                public void keyTyped(KeyEvent e) {
                    txtNumAutorizacion_keyTyped(e);
                }
            });
        txtNumAutorizacion.setLengthText(6);
        txtNumReferencia.setBounds(new Rectangle(160, 105, 180, 20));
        txtNumReferencia.setNextFocusableComponent(txtFecha);
        txtNumReferencia.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtNumReferencia_keyPressed(e);
                }

                public void keyTyped(KeyEvent e) {
                    txtNumReferencia_keyTyped(e);
                }
            });
        txtNumReferencia.setLengthText(4);
        txtFecha.setBounds(new Rectangle(160, 140, 120, 20));
        txtFecha.setNextFocusableComponent(cmbTipoTarjeta);
        txtFecha.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtFecha_keyPressed(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtFecha_keyReleased(e);
                }
            });
        txtFecha.setLengthText(10);
        lblTitle.setText("Ingrese los datos de la transacción a anular");
        lblTitle.setBounds(new Rectangle(25, 0, 260, 25));
        lblTitle.setFocusable(false);
        cmbTipoTarjeta.setBounds(new Rectangle(160, 30, 180, 20));
        cmbTipoTarjeta.setNextFocusableComponent(txtNumAutorizacion);
        cmbTipoTarjeta.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    cmbTipoTarjeta_keyPressed(e);
                }
            });
        cmbTipoTarjeta.addItem("Visa");
        cmbTipoTarjeta.addItem("Mastercard");
        lblF11.setText("[ F11 ] Anular");
        lblF11.setBounds(new Rectangle(5, 230, 105, 30));
        lblF11.setHorizontalAlignment(SwingConstants.CENTER);
        lblF11.setFocusable(false);
        pnlFondo.add(lblF11, null);
        pnlFondo.add(lblEsc, null);
        pnlFondo.add(jPanel1, null);
        pnlTitle.add(lblTitle, null);
        pnlFondo.add(pnlTitle, null);
        jPanel1.add(cmbTipoTarjeta, null);
        jPanel1.add(txtFecha, null);
        jPanel1.add(txtNumReferencia, null);
        jPanel1.add(txtNumAutorizacion, null);
        jPanel1.add(lblFecha, null);
        jPanel1.add(lblNumReferencia, null);
        jPanel1.add(lblNumAutorizacion, null);
        jPanel1.add(lblTipoTarjeta, null);
        this.getContentPane().add(pnlFondo, "pnlFondo");
        FarmaUtility.centrarVentana(this);
    }

    private void cmbTipoTarjeta_keyPressed(KeyEvent e)
    {   chkkeyPressed(e);
    }

    private void txtNumAutorizacion_keyPressed(KeyEvent e)
    {   chkkeyPressed(e);
    }

    private void txtNumReferencia_keyPressed(KeyEvent e)
    {   chkkeyPressed(e);
    }
    
    private void txtFecha_keyPressed(KeyEvent e)
    {   
        chkkeyPressed(e);
    }
    
    private void txtFecha_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFecha, e);
    }
    
    private void txtNumAutorizacion_keyTyped(KeyEvent e)
    {   //FarmaUtility.admitirDigitos(txtNumAutorizacion, e);
    }

    private void txtNumReferencia_keyTyped(KeyEvent e)
    {   FarmaUtility.admitirDigitos(txtNumReferencia, e);
    }
    
    private void chkkeyPressed(KeyEvent e)
    {   if(lblF11.isEnabled() && mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e))
        {   
            //18-Nov-2013 por recordarnos si concilia o no.
            if(validarCampos())
            {   //se establece la forma de pago
                String tipoTarj = "";
                if("Visa".equals(cmbTipoTarjeta.getSelectedItem().toString()))
                {   formaPago = ConstantsPtoVenta.FORPAG_VISA_PINPAD;
                    tipoTarj="V";
                }
                else if("Mastercard".equals(cmbTipoTarjeta.getSelectedItem().toString()))
                {   formaPago = ConstantsPtoVenta.FORPAG_MC_PINPAD;
                    tipoTarj="M";
                }
                else
                {   formaPago = "";
                    tipoTarj="";
                }
                //consultar la transaccion en BD
                monto = DBPinpad.obtenerMontoTransaccion(tipoTarj, txtNumReferencia.getText(), txtNumAutorizacion.getText(), txtFecha.getText());
                //llamar a la pantalla de anulacion
                if(monto!=null && !"FALSE".equals(monto))
                {   DlgAnularTransPinpad dlgAnularTransPinpad = new DlgAnularTransPinpad(myParentFrame,"",true);
                    dlgAnularTransPinpad.setValores("", 
                                                    UtilityPinpad.convCadNumDec(monto), 
                                                    txtFecha.getText(), 
                                                    txtNumReferencia.getText(),
                                                    formaPago,
                                                    false);
                    dlgAnularTransPinpad.setVisible(true);
                    if(FarmaVariables.vAceptar)
                    {   cerrarVentana(true);
                    }
                }
                else
                    FarmaUtility.showMessage(this, "Los datos ingresados no corresponden a una transacción", null);
            }
        }
        if(lblEsc.isEnabled() && e.getKeyCode()==KeyEvent.VK_ESCAPE)
        {   cerrarVentana(false);
        }
        if(lblEsc.isEnabled() && e.getKeyCode()==KeyEvent.VK_ENTER)
        {   ((JComponent)e.getSource()).transferFocus();
        }
    }
    
    private void cerrarVentana(boolean pAceptar)
    {   FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    
    private boolean validarCampos()
    {   boolean flag=true;
        if(!txtNumAutorizacion.getText().trim().equals("") &&
            !txtNumReferencia.getText().trim().equals("") &&
            !txtFecha.getText().trim().equals(""))
        {   
            Calendar fecha_ingresada = Calendar.getInstance();
            try
            {   fecha_ingresada.setTime(FarmaUtility.getStringToDate(txtFecha.getText().trim(),
                                                                     "dd/MM/yyyy"));
            }
            catch(Exception ex)
            {   //si no se puede obtener un Date, la fecha es incorrecta
                FarmaUtility.showMessage(this, "ERROR: La fecha ingresada no es valida", null);
                flag=false;
            }
        }
        else
        {   FarmaUtility.showMessage(this, 
                                    "ERROR: Alguno de los campos se encuentra vacio", 
                                    null);
            flag=false;
        }
        return flag;
    }

}
