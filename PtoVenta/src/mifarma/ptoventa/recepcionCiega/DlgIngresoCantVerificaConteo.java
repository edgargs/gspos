package mifarma.ptoventa.recepcionCiega;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JDialog;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.recepcionCiega.reference.DBRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.UtilityRecepCiega;
import mifarma.ptoventa.recepcionCiega.reference.VariablesRecepCiega;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgIngresoCantVerificaConteo extends JDialog {
    Frame myParentFrame;
    private static final Logger log = LoggerFactory.getLogger(DlgIngresoCantVerificaConteo.class);

    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelWhite pnlTitle1 = new JPanelWhite();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelOrange lblCodigoBarra_T = new JLabelOrange();
    private JLabelOrange lblCodigoBarra = new JLabelOrange();
    private JLabelOrange lblDescProducto_T = new JLabelOrange();
    private JLabelOrange lblDescProducto = new JLabelOrange();
    private JLabelOrange lblUnidad_T = new JLabelOrange();
    private JLabelOrange lblUnidad = new JLabelOrange();
    private JTextFieldSanSerif txtCantidad = new JTextFieldSanSerif();
    private JButtonLabel btnCantidad = new JButtonLabel();
    private JLabelOrange jLabelOrange1 = new JLabelOrange();
    private JLabelOrange lblCodigo = new JLabelOrange();
    
    private JTextFieldSanSerif txtLote = new JTextFieldSanSerif();
    private JButtonLabel btnLote = new JButtonLabel();
    private JTextFieldSanSerif txtFechaVenc = new JTextFieldSanSerif();
    private JButtonLabel btnFechaVenc = new JButtonLabel();


    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgIngresoCantVerificaConteo() {
        this(null, "", false);
    }

    public DlgIngresoCantVerificaConteo(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
            FarmaUtility.centrarVentana(this);
        } catch (Exception e) {
            log.error("", e);
        }

    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        //KMONCADA 05.08.2016 [PROYECTO M] FUNCION PARA VALIDAR LOCAL MAYORISTA
        //if (VariablesPtoVenta.vTipoLocalVenta.equals(ConstantsPtoVenta.TIPO_LOCAL_VTA_MAYORISTA) && 
        if (UtilityPtoVenta.isLocalVentaMayorista() && 
              VariablesRecepCiega.vLastIndLoteMayorista.equals("S")){
            this.setSize(new Dimension(310, 262));
            pnlTitle1.setBounds(new Rectangle(5, 5, 270, 180));
            lblF11.setBounds(new Rectangle(45, 185, 110, 20)); 
            lblEsc.setBounds(new Rectangle(165, 185, 110, 20));
        }else{
            this.setSize(new Dimension(310, 178));
            pnlTitle1.setBounds(new Rectangle(5, 5, 295, 110));
            lblF11.setBounds(new Rectangle(45, 185, 110, 20)); 
            lblEsc.setBounds(new Rectangle(165, 185, 110, 20));
        }
        
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Ingreso de Cantidad - Verificación de Conteo");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        
        pnlTitle1.setBackground(Color.white);
        pnlTitle1.setBorder(BorderFactory.createLineBorder(new Color(225, 130, 14), 1));
               
        lblF11.setText("[ ENTER ] Aceptar");        
        lblEsc.setText("[ ESC ] Cerrar");
        lblCodigoBarra_T.setText("Código Barra");
        lblCodigoBarra_T.setBounds(new Rectangle(10, 10, 80, 15));
        lblCodigoBarra_T.setVisible(false);
        lblCodigoBarra.setFont(new Font("SansSerif", 0, 11));
        lblCodigoBarra.setText("1234567891234");
        lblCodigoBarra.setBounds(new Rectangle(110, 10, 135, 15));
        lblCodigoBarra.setVisible(false);
        lblDescProducto_T.setText("Desc. Producto :");
        lblDescProducto_T.setBounds(new Rectangle(10, 30, 95, 15));
        lblDescProducto.setFont(new Font("SansSerif", 0, 11));
        lblDescProducto.setText("producto");
        lblDescProducto.setBounds(new Rectangle(110, 30, 180, 15));
        lblUnidad_T.setText("Unidad :");
        lblUnidad_T.setBounds(new Rectangle(10, 50, 95, 15));
        lblUnidad.setText("und");
        lblUnidad.setBounds(new Rectangle(110, 50, 180, 15));
        lblUnidad.setFont(new Font("SansSerif", 0, 11));
        txtCantidad.setBounds(new Rectangle(110, 75, 80, 20));
        txtCantidad.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCantidad_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtCantidad_keyTyped(e);
            }
        });
        txtCantidad.setLengthText(4);
        btnCantidad.setText("Cantidad");
        btnCantidad.setBounds(new Rectangle(10, 75, 60, 20));
        btnCantidad.setForeground(new Color(255, 130, 14));
        btnCantidad.setMnemonic('c');
        btnCantidad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCantidad_actionPerformed(e);
            }
        });
 
        txtLote.setBounds(new Rectangle(110, 105, 80, 20)); 
        txtLote.setLengthText(20);
        //KMONCADA 05.08.2016 [PROYECTO M] FUNCION PARA VALIDAR LOCAL MAYORISTA
        //txtLote.setVisible((VariablesPtoVenta.vTipoLocalVenta.equals(ConstantsPtoVenta.TIPO_LOCAL_VTA_MAYORISTA) ? true : false));
        txtLote.setVisible(UtilityPtoVenta.isLocalVentaMayorista());
        txtLote.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtLote_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtLote_keyTyped(e);
            }

            public void keyReleased(KeyEvent e) {
                txtLote_keyReleased(e);
            }
        });
        btnLote.setText("Lote");
        btnLote.setBounds(new Rectangle(10, 105, 60, 20));
        btnLote.setForeground(new Color(255, 130, 14));
        btnLote.setMnemonic('l');
        //KMONCADA 05.08.2016 [PROYECTO M] FUNCION PARA VALIDAR LOCAL MAYORISTA
        //btnLote.setVisible((VariablesPtoVenta.vTipoLocalVenta.equals(ConstantsPtoVenta.TIPO_LOCAL_VTA_MAYORISTA) ? true : false));
        btnLote.setVisible(UtilityPtoVenta.isLocalVentaMayorista());
        btnLote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //btnLote_actionPerformed(e);
            }
        });
        
        txtFechaVenc.setBounds(new Rectangle(110, 130, 80, 20));
        txtFechaVenc.setLengthText(30);
        //KMONCADA 05.08.2016 [PROYECTO M] FUNCION PARA VALIDAR LOCAL MAYORISTA
        //txtFechaVenc.setVisible((VariablesPtoVenta.vTipoLocalVenta.equals(ConstantsPtoVenta.TIPO_LOCAL_VTA_MAYORISTA) ? true : false));
        txtFechaVenc.setVisible(UtilityPtoVenta.isLocalVentaMayorista());
        txtFechaVenc.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaVenc_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtFechaVenc_keyReleased(e);
            }

            public void keyTyped(KeyEvent e) {
                txtFechaVenc_keyTyped(e);
            }
        });
        btnFechaVenc.setText("Fecha Venc.");
        btnFechaVenc.setBounds(new Rectangle(10, 130, 80, 20));
        btnFechaVenc.setForeground(new Color(255, 130, 14));
        btnFechaVenc.setMnemonic('f');
        //KMONCADA 05.08.2016 [PROYECTO M] FUNCION PARA VALIDAR LOCAL MAYORISTA
        //btnFechaVenc.setVisible((VariablesPtoVenta.vTipoLocalVenta.equals(ConstantsPtoVenta.TIPO_LOCAL_VTA_MAYORISTA) ? true : false));
        btnFechaVenc.setVisible(UtilityPtoVenta.isLocalVentaMayorista());
        btnFechaVenc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //btnFechaVenc_actionPerformed(e);
            }
        });
        
        jLabelOrange1.setText("Codigo :");
        jLabelOrange1.setBounds(new Rectangle(10, 10, 85, 15));
        lblCodigo.setText("[Codigo]");
        lblCodigo.setBounds(new Rectangle(110, 10, 180, 15));
        lblCodigo.setFont(new Font("SansSerif", 0, 11));
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);        
        pnlTitle1.add(lblCodigo, null);
        pnlTitle1.add(jLabelOrange1, null);
        pnlTitle1.add(btnCantidad, null);
        pnlTitle1.add(txtCantidad, null);
        pnlTitle1.add(lblUnidad, null);
        pnlTitle1.add(lblUnidad_T, null);
        pnlTitle1.add(lblDescProducto, null);
        pnlTitle1.add(lblDescProducto_T, null);
        pnlTitle1.add(lblCodigoBarra, null);
        pnlTitle1.add(lblCodigoBarra_T, null);
        //KMONCADA 05.08.2016 [PROYECTO M] FUNCION PARA VALIDAR LOCAL MAYORISTA
        //if (VariablesPtoVenta.vTipoLocalVenta.equals(ConstantsPtoVenta.TIPO_LOCAL_VTA_MAYORISTA) &&
        if (UtilityPtoVenta.isLocalVentaMayorista() &&
              VariablesRecepCiega.vLastIndLoteMayorista.equals("S")){
            pnlTitle1.add(btnLote, null);
            pnlTitle1.add(txtLote, null);
            pnlTitle1.add(btnFechaVenc, null);
            pnlTitle1.add(txtFechaVenc, null);
        }
        jContentPane.add(pnlTitle1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);        
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        mostrarDatos();
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtCantidad);
    }

    private void txtCantidad_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCantidad, e);
    }

    private void txtCantidad_keyPressed(KeyEvent e) {
        //KMONCADA 05.08.2016 [PROYECTO M] FUNCION PARA VALIDAR LOCAL MAYORISTA
        //if(VariablesPtoVenta.vTipoLocalVenta.equals(ConstantsPtoVenta.TIPO_LOCAL_VTA_MAYORISTA) &&
        if(UtilityPtoVenta.isLocalVentaMayorista() &&
             VariablesRecepCiega.vLastIndLoteMayorista.equals("S")){
            if (e.getKeyCode() == KeyEvent.VK_ENTER){
                FarmaUtility.moveFocus(txtLote);
            }else{
                chkKeyPressed(e);      
            }
        }else{            
          chkKeyPressed(e);
        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnCantidad_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCantidad);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (datosValidados()) {

                {
                    VariablesRecepCiega.vCantidadVerificaConteo = this.txtCantidad.getText().toString().trim();
                    
                    actualizaCantidad();
                    cerrarVentana(true);
                }
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            FarmaVariables.vAceptar = false;
            this.setVisible(false);
        }
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void mostrarDatos() {
        this.lblCodigoBarra.setText(VariablesRecepCiega.vCod_Barra);
        this.lblDescProducto.setText(VariablesRecepCiega.vDesc_Producto);
        this.lblUnidad.setText(VariablesRecepCiega.vUnidad);
        lblCodigo.setText(VariablesRecepCiega.vCodProd);
        this.txtLote.setText(VariablesRecepCiega.vLote);
        this.txtLote.setEditable((VariablesRecepCiega.vLote.equals("") ? true : false));
        this.txtFechaVenc.setText(VariablesRecepCiega.vFechaVcto);
        this.txtFechaVenc.setEditable((VariablesRecepCiega.vFechaVcto.equals("") ? true : false));
    }

    private boolean datosValidados() {
        boolean rpta = false;
        try {
            if (txtCantidad.getText().trim().length() == 0) {
                rpta = false;
                FarmaUtility.showMessage(this, "Ingrese la cantidad", txtCantidad);
            } else if (txtCantidad.getText().trim().length() > 6) {
                rpta = false;
                FarmaUtility.showMessage(this, "Ingrese la cantidad correcta", txtCantidad);
            } else if (FarmaUtility.isInteger(txtCantidad.getText().trim())) {
                if (Integer.parseInt(txtCantidad.getText().trim()) < 0) {
                    rpta = false;
                    FarmaUtility.showMessage(this, "Ingrese cantidad mayor a cero", txtCantidad);
                } else {
                    rpta = true;
                    //FarmaUtility.showMessage(this, "Ingrese cantidad correcta", txtCantidad);
                }
            //KMONCADA 05.08.2016 [PROYECTO M] FUNCION PARA VALIDAR LOCAL MAYORISTA
            //if (VariablesPtoVenta.vTipoLocalVenta.equals(ConstantsPtoVenta.TIPO_LOCAL_VTA_MAYORISTA) && rpta && Integer.parseInt(txtCantidad.getText().trim()) > 0){
            if (UtilityPtoVenta.isLocalVentaMayorista() && rpta && Integer.parseInt(txtCantidad.getText().trim()) > 0){
                        if (VariablesRecepCiega.vLastIndLoteMayorista.trim().toUpperCase().equals("S")){
                            rpta = validaDatosLote();                        
                        }
                    }
            }
            /*if (txtCantidad.getText().toString().trim().equalsIgnoreCase("0") ) {
            rpta = false;
            FarmaUtility.showMessage(this, "Ingrese la cantidad correcta", txtCantidad);
        } */
        } catch (Exception e) {
            rpta = false;
            FarmaUtility.showMessage(this, "Ingrese cantidad correcta", txtCantidad);
        }
        return rpta;
    }

    private void actualizaCantidad() {
        try {
            DBRecepCiega.actualizaCantidadProductoEnVerificacionConteo();
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al actualizar el registro.\n" +
                    sql.getMessage(), null);
        }

    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    
    private void txtFechaVenc_keyPressed(KeyEvent e) {        
        if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            chkKeyPressed(e);
        }
    }
    
    private void txtLote_keyPressed(KeyEvent e) {        
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtFechaVenc);        
        }else if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
            chkKeyPressed(e);
        }
    }
    
    private void txtLote_keyTyped(KeyEvent e) {        
        //chkKeyPressed(e);
    }
    
    private void txtFechaVenc_keyTyped(KeyEvent e) {        
        FarmaUtility.admitirDigitos(txtFechaVenc, e);
    }
    
    private void txtLote_keyReleased(KeyEvent e) {        
        //chkKeyPressed(e);
    }
    
    private void txtFechaVenc_keyReleased(KeyEvent e) {        
        FarmaUtility.dateComplete(txtFechaVenc, e);
    }
    
    public boolean validaDatosLote(){
        boolean rpta = true;
        if (txtLote.getText().trim().length() == 0) {
            rpta = false;
            FarmaUtility.showMessage(this, "¡Ingrese lote correcto!", txtLote);
        }else{
            VariablesRecepCiega.vLote = txtLote.getText().toString().trim().toUpperCase();
        }
        if (rpta) {
            rpta = validaDatosFechaVenc();
        }
        return rpta;
    }
    
    public boolean validaDatosFechaVenc(){
        boolean rpta = true;
        String FechaVenc = txtFechaVenc.getText().trim();
        
        if (FechaVenc.length()  == 0) {
            rpta = false;
            FarmaUtility.showMessage(this, "¡Ingrese Fecha Vencimiento correcta!", txtFechaVenc);
        }else{
            try {
                if (!UtilityRecepCiega.validarFecha(FechaVenc)) {
                    rpta = false;
                    FarmaUtility.showMessage(this, "Formato de fecha inicial inválido", FechaVenc);
                } else if (rpta) {
                    String sFechaActual =
                        new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());

                    Date dFechaVenc = FarmaUtility.getStringToDate(FechaVenc, "dd/MM/yyyy");
                    Date dFechaActual = FarmaUtility.getStringToDate(sFechaActual, "dd/MM/yyyy");
                    if (!dFechaActual.before(dFechaVenc)) {
                        rpta = false;
                        FarmaUtility.showMessage(this,
                                                 "la fecha de vencimiento debe ser mayor a la fecha actual",
                                                 FechaVenc);
                    }else{
                        VariablesRecepCiega.vFechaVcto = txtFechaVenc.getText();
                    }
                }                            
            } catch (Exception e) {
                rpta = false;
                log.error("", e);
            }
        }
        return rpta;
    }

}
