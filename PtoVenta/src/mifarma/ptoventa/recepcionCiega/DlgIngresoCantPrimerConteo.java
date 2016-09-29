package mifarma.ptoventa.recepcionCiega;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
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


public class DlgIngresoCantPrimerConteo extends JDialog {
    private Frame myParentFrame;
    private static final Logger log = LoggerFactory.getLogger(DlgIngresoCantPrimerConteo.class);

    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelWhite pnlTitle1 = new JPanelWhite();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelWhite lblCodigoBarra_T = new JLabelWhite();
    private JLabelWhite lblCodigoBarra = new JLabelWhite();
    private JLabelWhite lblDescProducto_T = new JLabelWhite();
    private JLabelWhite lblDescProducto = new JLabelWhite();
    private JLabelWhite lblUnidad_T = new JLabelWhite();
    private JLabelWhite lblUnidad = new JLabelWhite();
    private JTextFieldSanSerif txtCantidad = new JTextFieldSanSerif();
    private JButtonLabel btnCantidad = new JButtonLabel();
    private JTextFieldSanSerif txtLote = new JTextFieldSanSerif();
    private JButtonLabel btnLote = new JButtonLabel();
    private JTextFieldSanSerif txtFechaVenc = new JTextFieldSanSerif();
    private JButtonLabel btnFechaVenc = new JButtonLabel();


    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgIngresoCantPrimerConteo() {
        this(null, "", false);
    }

    public DlgIngresoCantPrimerConteo(Frame parent, String title, boolean modal) {
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

    private void jbInit() throws Exception {
        //if (VariablesPtoVenta.vTipoLocalVenta.equals(ConstantsPtoVenta.TIPO_LOCAL_VTA_MAYORISTA) &&
        //KMONCADA 05.08.2016 [PROYECTO M] FUNCION PARA VALIDAR LOCAL MAYORISTA
        if (UtilityPtoVenta.isLocalVentaMayorista() &&
              VariablesRecepCiega.vLastIndLoteMayorista.equals("S")){
            this.setSize(new Dimension(310, 284));
            pnlTitle1.setBounds(new Rectangle(10, 15, 280, 190));
        }else{
            this.setSize(new Dimension(310, 200));
            pnlTitle1.setBounds(new Rectangle(10, 15, 280, 120));
        }
        
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Ingreso de Cantidad - Primer Conteo");
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

        lblF11.setBounds(new Rectangle(55, 220, 110, 20));
        lblF11.setText("[ Enter ] Aceptar");
        lblEsc.setBounds(new Rectangle(180, 220, 110, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblCodigoBarra_T.setText("C�digo Barra");
        lblCodigoBarra_T.setForeground(new Color(225, 130, 14));
        lblCodigoBarra_T.setBounds(new Rectangle(40, 25, 80, 15));
        lblCodigoBarra.setFont(new Font("SansSerif", 0, 11));
        lblCodigoBarra.setForeground(new Color(225, 130, 14));
        lblCodigoBarra.setText("");
        lblCodigoBarra.setBounds(new Rectangle(130, 25, 135, 15));
        lblDescProducto_T.setText("Desc. Producto");
        lblDescProducto_T.setForeground(new Color(225, 130, 14));
        lblDescProducto_T.setBounds(new Rectangle(10, 35, 95, 15));
        lblDescProducto.setFont(new Font("SansSerif", 0, 11));
        lblDescProducto.setForeground(new Color(225, 130, 14));
        lblDescProducto.setText("producto");
        lblDescProducto.setBounds(new Rectangle(110, 35, 150, 15));

        lblUnidad_T.setText("Unidad");
        lblUnidad_T.setBounds(new Rectangle(10, 60, 95, 15));
        lblUnidad_T.setForeground(new Color(225, 130, 14));
        lblUnidad.setText("und");
        lblUnidad.setBounds(new Rectangle(110, 60, 90, 15));
        lblUnidad.setFont(new Font("SansSerif", 0, 11));
        lblUnidad.setForeground(new Color(225, 130, 14));
        txtCantidad.setBounds(new Rectangle(135, 65, 80, 20));
        txtCantidad.setLengthText(4);
        //txtCantidad.setBounds(new Rectangle(110, 90, 80, 20));
        txtCantidad.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCantidad_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtCantidad_keyTyped(e);
            }

            public void keyReleased(KeyEvent e) {
                txtCantidad_keyReleased(e);
            }
        });
        btnCantidad.setText("Cantidad");
        btnCantidad.setBounds(new Rectangle(40, 65, 60, 20));
        btnCantidad.setForeground(new Color(255, 130, 14));
        //btnCantidad.setBounds(new Rectangle(10, 90, 60, 20));
        // btnCantidad.setForeground(new Color(255, 130, 14));
        btnCantidad.setMnemonic('c');
        btnCantidad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCantidad_actionPerformed(e);
            }
        });
        
        txtLote.setBounds(new Rectangle(135, 105, 80, 20));
        txtLote.setLengthText(20);
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
        btnLote.setBounds(new Rectangle(40, 105, 60, 20));
        btnLote.setForeground(new Color(255, 130, 14));
        btnLote.setMnemonic('l');
        //btnLote.setVisible((VariablesPtoVenta.vTipoLocalVenta.equals(ConstantsPtoVenta.TIPO_LOCAL_VTA_MAYORISTA) ? true : false));
        //KMONCADA 05.08.2016 [PROYECTO M] FUNCION PARA VALIDAR LOCAL MAYORISTA
        btnLote.setVisible(UtilityPtoVenta.isLocalVentaMayorista());
        btnLote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //btnLote_actionPerformed(e);
            }
        });
        
        txtFechaVenc.setBounds(new Rectangle(135, 145, 80, 20));
        txtFechaVenc.setLengthText(30);
        //txtFechaVenc.setVisible((VariablesPtoVenta.vTipoLocalVenta.equals(ConstantsPtoVenta.TIPO_LOCAL_VTA_MAYORISTA) ? true : false));
        //KMONCADA 05.08.2016 [PROYECTO M] FUNCION PARA VALIDAR LOCAL MAYORISTA
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
        btnFechaVenc.setBounds(new Rectangle(40, 145, 80, 20));
        btnFechaVenc.setForeground(new Color(255, 130, 14));
        btnFechaVenc.setMnemonic('f');
        //btnFechaVenc.setVisible((VariablesPtoVenta.vTipoLocalVenta.equals(ConstantsPtoVenta.TIPO_LOCAL_VTA_MAYORISTA) ? true : false));
        //KMONCADA 05.08.2016 [PROYECTO M] FUNCION PARA VALIDAR LOCAL MAYORISTA
        btnFechaVenc.setVisible(UtilityPtoVenta.isLocalVentaMayorista());
        btnFechaVenc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //btnFechaVenc_actionPerformed(e);
            }
        });

        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        pnlTitle1.add(btnCantidad, null);
        pnlTitle1.add(txtCantidad, null);
        pnlTitle1.add(lblUnidad, null);
        pnlTitle1.add(lblUnidad_T, null);
        pnlTitle1.add(lblDescProducto, null);
        pnlTitle1.add(lblDescProducto_T, null);
        pnlTitle1.add(lblCodigoBarra, null);
        pnlTitle1.add(lblCodigoBarra_T, null);
        //if (VariablesPtoVenta.vTipoLocalVenta.equals(ConstantsPtoVenta.TIPO_LOCAL_VTA_MAYORISTA) &&
        //KMONCADA 05.08.2016 [PROYECTO M] FUNCION PARA VALIDAR LOCAL MAYORISTA
        if (UtilityPtoVenta.isLocalVentaMayorista() &&
              VariablesRecepCiega.vLastIndLoteMayorista.equals("S")){
            pnlTitle1.add(btnLote, null);
            pnlTitle1.add(txtLote, null);
            pnlTitle1.add(btnFechaVenc, null);
            pnlTitle1.add(txtFechaVenc, null);
        }        
        jContentPane.add(pnlTitle1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);

        //oculto
        lblDescProducto.setVisible(false);
        lblUnidad_T.setVisible(false);
        lblUnidad.setVisible(false);
        lblDescProducto_T.setVisible(false);
    }

    // **************************************************************************
    // M�todo "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        mostrarDatos();
    };
    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);

        FarmaUtility.moveFocus(txtCantidad);
    }

    private void txtCantidad_keyTyped(KeyEvent e) {
        /*
        if(e.getKeyChar() != '+')
        {
            FarmaUtility.admitirDigitos(txtCantidad,e);
        }
        */
        /* if(txtCantidad.getText().trim().length()>4){
            FarmaUtility.showMessage(this,"Cantidad Fuera de l�mite!",txtCantidad);
        }
    */
    }

    private void txtCantidad_keyPressed(KeyEvent e) {
        /*if(e.getKeyChar() != '+')
        {
           FarmaUtility.admitirDigitos(txtCantidad,e);
        }
        */
        //KMONCADA 05.08.2016 [PROYECTO M] FUNCION PARA VALIDAR LOCAL MAYORISTA
        //if (VariablesPtoVenta.vTipoLocalVenta.equals(ConstantsPtoVenta.TIPO_LOCAL_VTA_MAYORISTA) &&
        if (UtilityPtoVenta.isLocalVentaMayorista() &&
              VariablesRecepCiega.vLastIndLoteMayorista.equals("S")){
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                //KMONCADA 05.08.2016 [PROYECTO M] FUNCION PARA VALIDAR LOCAL MAYORISTA
                //if (VariablesPtoVenta.vTipoLocalVenta.equals(ConstantsPtoVenta.TIPO_LOCAL_VTA_MAYORISTA)){
                if (UtilityPtoVenta.isLocalVentaMayorista()){
                    FarmaUtility.moveFocus(txtLote);   
                }else{
                    
                }   FarmaUtility.moveFocus(lblF11);
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

                VariablesRecepCiega.vLastCant = txtCantidad.getText().trim();
                VariablesRecepCiega.vLastLote = txtLote.getText().toString().trim().toUpperCase();
                VariablesRecepCiega.vFechaVcto = txtFechaVenc.getText().trim();

                if (VariablesRecepCiega.vIndModificarCant) {
                    actualizaCantidad();
                    VariablesRecepCiega.vIndModificoCantActivo = true; //YA MODIFICO, YA NO PODRA MODIFICAR
                }

                cerrarVentana(false);
            }else {
                return;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            // boolean rpta = com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,"No se ingresar� o Modificar� este Producto, �Est� seguro?");
            //if(rpta == true){
            VariablesRecepCiega.vIndAgregaConteo = false;
            //VariablesRecepCiega.vLastCodBarra = "";
            VariablesRecepCiega.vIndModificoCantActivo = false;
            FarmaVariables.vAceptar = true;
            this.setVisible(false);
            /*
                }
                else{
                    return;
                }
                */
            //VariablesRecepCiega.vIndAgregaConteo = false;

        }
    }

    // **************************************************************************
    // Metodos de l�gica de negocio
    // **************************************************************************

    private void mostrarDatos() {
        //Se muestra el �ltimo codigo de barra, porque es el �nico que se puede modificar o eliminar.
        //   this.lblDescProducto.setText(VariablesRecepCiega.vDesc_Producto);
        //   this.lblUnidad.setText(VariablesRecepCiega.vUnidad);
        if (VariablesRecepCiega.vIndModificarCant) {
            this.lblCodigoBarra.setText(VariablesRecepCiega.vTempCodBarra);
            this.lblDescProducto.setText(VariablesRecepCiega.vTempDescProd);
            this.lblUnidad.setText(VariablesRecepCiega.vTempUndPresente);
            //this.txtCantidad.setText(VariablesRecepCiega.vTempCant);
            //this.txtCantidad.setText();
        } else {
            this.lblCodigoBarra.setText(VariablesRecepCiega.vLastCodBarra);
            this.lblDescProducto.setText(VariablesRecepCiega.vLastDesProd);
            this.lblUnidad.setText(VariablesRecepCiega.vLastUndPresente);
            this.txtCantidad.setText("1");
        }
    }

    private boolean datosValidados() {

        boolean rpta = true;
        VariablesRecepCiega.vIndAgregaConteo = true;
        VariablesRecepCiega.vIndEliminaFila = false;

        if (isInteger(txtCantidad.getText().trim())) {
            try {
                if (txtCantidad.getText().trim().length() > 4) {
                    FarmaUtility.showMessage(this, "Cantidad Fuera de L�mite", txtCantidad);
                    rpta = false;
                }
                if (txtCantidad.getText().trim().length() == 0) {
                    rpta = false;
                    FarmaUtility.showMessage(this, "�Ingrese cantidad correcta!", txtCantidad);
                }
                if (Integer.parseInt(txtCantidad.getText().trim()) < 0) {
                    FarmaUtility.showMessage(this, "No puede ingresar Cantidad Negativa", txtCantidad);
                    rpta = false;
                }
                
                //if (VariablesPtoVenta.vTipoLocalVenta.equals(ConstantsPtoVenta.TIPO_LOCAL_VTA_MAYORISTA) && rpta){
                //KMONCADA 05.08.2016 [PROYECTO M] FUNCION PARA VALIDAR LOCAL MAYORISTA
                if (UtilityPtoVenta.isLocalVentaMayorista() && rpta){
                    if (VariablesRecepCiega.vLastIndLoteMayorista.trim().toUpperCase().equals("S")){
                        rpta = validaDatosLote();                        
                    }                    
                }
                //validar si recien esta ingresando
                if (Integer.parseInt(txtCantidad.getText()) == 0) {
                    if (!VariablesRecepCiega.vIndModificarCant) {
                        if (JConfirmDialog.rptaConfirmDialog(this, "�Est� seguro que no desea ingresar este producto?")) {
                            log.debug("No ingresa dato");
                            //VariablesRecepCiega.vLastCodBarra = "";
                            VariablesRecepCiega.vIndAgregaConteo = false;
                            cerrarVentana(false);
                            rpta = true;
                        }
                    } else {
                        if (JConfirmDialog.rptaConfirmDialog(this, "�Est� seguro que desea eliminar este producto?")) {
                            log.debug("Eliminar");
                            //VariablesRecepCiega.vLastCodBarra = "";
                            //VariablesRecepCiega.vIndEliminaFila = true;
                            VariablesRecepCiega.vIndAgregaConteo = false;
                            eliminaFilaConteo();
                            log.debug("eliminaRegistro");

                            cerrarVentana(false);
                            rpta = true;
                        }
                    }
                    rpta = false;
                }


            } catch (Exception e) {
                rpta = false;
                VariablesRecepCiega.vIndAgregaConteo = false;
                log.error("", e);
            }
        } else {
            rpta = false;
            FarmaUtility.showMessage(this, "�Ingrese una cantidad correcta!", txtCantidad);
        }


        return rpta;
    }

    private boolean isInteger(String pCadena) {
        int pNumero = 0;
        try {
            pNumero = Integer.parseInt(pCadena.trim());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void actualizaCantidad() {

        try {
            DBRecepCiega.actualizaAuxConteo(VariablesRecepCiega.vSecRecepGuia, VariablesRecepCiega.vTempNroBloque,
                                            VariablesRecepCiega.vTempAuxSecConteo, VariablesRecepCiega.vLastCant,
                                            VariablesRecepCiega.vLote, VariablesRecepCiega.vFechaVcto);

            FarmaUtility.aceptarTransaccion();
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurri� un error al actualizar el registro.\n" +
                    sql.getMessage(), null);
        }

    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }


    private void txtCantidad_keyReleased(KeyEvent e) {
        /*if(e.getKeyChar() != '+')
        {
            FarmaUtility.admitirSoloDigitos(e,txtCantidad,0,this);
        }
       */
    }

    private void eliminaFilaConteo() {
        try {
            DBRecepCiega.eliminaAuxConteo(VariablesRecepCiega.vSecRecepGuia, VariablesRecepCiega.vNroBloque,
                                          VariablesRecepCiega.vTempAuxSecConteo);
            FarmaUtility.aceptarTransaccion();
            /* String pNroRecep,
                                        String pNroBloque,
                                        String pSecAuxConteo*/
        } catch (SQLException sql) {
            FarmaUtility.liberarTransaccion();
            log.error("", sql);
            //FarmaUtility.liberarTransaccion();
        }
    }
    
    private void txtLote_keyPressed(KeyEvent e) {        
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                txtFechaVenc.setText(DBRecepCiega.obtieneFechaVencimiento((txtLote.getText() == null ? "" : txtLote.getText())));
            } catch (SQLException f) {
                txtFechaVenc.setText("");
                log.error("ERROR: " + f);
            }            
            FarmaUtility.moveFocus(txtFechaVenc);        
        }else if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
            chkKeyPressed(e);
        }
    }
    
    private void txtFechaVenc_keyPressed(KeyEvent e) {        
        if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
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
            FarmaUtility.showMessage(this, "�Ingrese lote correcto!", txtLote);
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
            FarmaUtility.showMessage(this, "�Ingrese Fecha Vencimiento correcta!", txtFechaVenc);
        }else{
            try {
                if (!UtilityRecepCiega.validarFecha(FechaVenc)) {
                    rpta = false;
                    FarmaUtility.showMessage(this, "Formato de fecha inicial inv�lido", FechaVenc);
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
