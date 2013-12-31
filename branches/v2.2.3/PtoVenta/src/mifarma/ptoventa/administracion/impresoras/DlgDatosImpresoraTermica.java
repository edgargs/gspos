package mifarma.ptoventa.administracion.impresoras;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import javax.swing.JLabel;
import javax.swing.JTextField;

import mifarma.common.*;
import mifarma.common.FarmaConstants;
import mifarma.ptoventa.administracion.impresoras.reference.DBImpresoras;
import mifarma.ptoventa.administracion.impresoras.reference.VariablesImpresoras;
import mifarma.ptoventa.reference.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//JMIRANDA 25/06/2009
public class DlgDatosImpresoraTermica extends JDialog {
    
    private static final Logger log = LoggerFactory.getLogger(DlgDatosImpresoraTermica.class);
    Frame myParentFrame;
    private boolean existenDatos;
    //variable para almacenar Descripcion
    private String vDescImpr = "";

    private JPanelWhite jContentPane = new JPanelWhite();

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelTitle pnlDatosImpresora = new JPanelTitle();

    private JLabelFunction lblAceptar = new JLabelFunction();

    private JLabelFunction lblSalir = new JLabelFunction();

    private JLabelWhite lblNroImpresora_T = new JLabelWhite();

    private JTextFieldSanSerif txtNroImpresora = new JTextFieldSanSerif();

    private JButtonLabel btnDescImpresora = new JButtonLabel();

    private JTextFieldSanSerif txtDescImpresora = new JTextFieldSanSerif();


    //private JTextFieldSanSerif txtColaImpresion = new JTextFieldSanSerif();
    private JButtonLabel btnColaImp = new JButtonLabel();
    private JButtonLabel btnTipoImpresora = new JButtonLabel();
    private JComboBox cmbTipoImpresora = new JComboBox();
    private JButtonLabel btnEstado = new JButtonLabel();
    private JComboBox cmbEstado = new JComboBox();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgDatosImpresoraTermica() {
        this(null, "", false);
    }

    public DlgDatosImpresoraTermica(Frame parent, String title, 
                                    boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("",e);
        }

    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(632, 174));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Impresora Térmica");
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }
                });
        jContentPane.setLayout(null);
        pnlDatosImpresora.setBounds(new Rectangle(10, 10, 610, 105));
        lblAceptar.setBounds(new Rectangle(405, 120, 100, 20));
        lblAceptar.setText("[F11] Aceptar");
        lblSalir.setBounds(new Rectangle(515, 120, 105, 20));
        lblSalir.setText("[Esc] Salir");
        lblNroImpresora_T.setText("Nro. Impresora :");
        lblNroImpresora_T.setBounds(new Rectangle(10, 10, 100, 20));
        txtNroImpresora.setBounds(new Rectangle(115, 10, 65, 20));
        txtNroImpresora.setEditable(false);
        btnDescImpresora.setText("Descripción Impresora :");
        btnDescImpresora.setBounds(new Rectangle(195, 10, 140, 20));
        btnDescImpresora.setBorder(BorderFactory.createLineBorder(Color.black, 
                                                                  1));
        btnDescImpresora.setMnemonic('d');
        btnDescImpresora.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnDescImpresora_actionPerformed(e);
                    }
                });
        txtDescImpresora.setBounds(new Rectangle(335, 10, 265, 20));
        txtDescImpresora.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtDescImpresora_keyPressed(e);
                    }
                });
               
        btnTipoImpresora.setText("Tipo Impresora:");
            btnTipoImpresora.setMnemonic('t');
            btnTipoImpresora.setBounds(new Rectangle(10, 40, 95, 20));

        btnTipoImpresora.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnTipoImpresora_actionPerformed(e);
                    }
                });
        btnEstado.setText("Estado:");
            btnEstado.setMnemonic('e');
            btnEstado.setBounds(new Rectangle(295, 40, 50, 20));

        btnEstado.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnEstado_actionPerformed(e);
                    }
                });
        cmbTipoImpresora.setBounds(new Rectangle(115, 40, 165, 20));
        
        cmbTipoImpresora.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        cmbTipoImpresora_keyPressed(e);
                    }
                });
        cmbEstado.setBounds(new Rectangle(355, 40, 140, 20));

        cmbEstado.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        cmbEstado_keyPressed(e);
                    }
                });
        jContentPane.add(lblSalir, null);
        jContentPane.add(lblAceptar, null);
        
        pnlDatosImpresora.add(btnEstado, null);
        pnlDatosImpresora.add(cmbTipoImpresora, null);
        pnlDatosImpresora.add(btnTipoImpresora, null);
        
        //pnlDatosImpresora.add(txtColaImpresion, null);
        pnlDatosImpresora.add(txtDescImpresora, null);
        pnlDatosImpresora.add(btnDescImpresora, null);
        pnlDatosImpresora.add(txtNroImpresora, null);
        pnlDatosImpresora.add(lblNroImpresora_T, null);
        pnlDatosImpresora.add(cmbEstado, null);
        jContentPane.add(pnlDatosImpresora, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        //AGREGADO 20/06/2006 ERIOS
        txtDescImpresora.setLengthText(30);
        //txtColaImpresion.setLengthText(120);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        existenDatos = existenDatosImpresoraTerm();
        
        initCombos();
        cargarDatos();
        if (existenDatos == true) {
            txtDescImpresora.setEditable(true);
        }
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initCombos() {
        initComboMarca();
        initComboEstado();
    }

    private void initComboMarca() {
     
     ArrayList parametros = new ArrayList();
     FarmaLoadCVL.loadCVLFromSP(cmbTipoImpresora,ConstantsPtoVenta.NOM_HASTABLE_CMBMODELO_IMPRESORA,"Farma_Gral.GET_LISTA_MODELO_IMPRESORA", parametros,true, true);   
    
    }

    private void initComboEstado() {        

     String codigo[] = { "A", "I" };
     String valor[] = { "ACTIVO", "INACTIVO" };
     FarmaLoadCVL.loadCVLfromArrays(cmbEstado, "Estado", codigo, valor, true);
  
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtDescImpresora);
        //JCORTEZ 14.03.09 solo para tipo ticket no se podra modificar la cola de impresion
        /* if (VariablesImpresoras.vTipoComp.trim().equalsIgnoreCase(ConstantsPtoVenta.TIP_COMP_TICKET)){//tipo ticket solamente
            //txtColaImpresion.setEditable(false);
        }else
            //txtColaImpresion.setEditable(true);*/


    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void txtDescImpresora_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtDescImpresora.getText().trim().length()==0){
                FarmaUtility.moveFocus(txtDescImpresora);
            }
                FarmaUtility.moveFocus(cmbTipoImpresora);
        }
        chkKeyPressed(e);
    }
  

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) // Reservado para ayuda
        {
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {

            boolean valor = true;
            
            if (datosValidados()) {
                
                //JCORTEZ 14.03.09 solo para tipo ticket no se podra modificar la cola de impresion con una que ya exista
                           
                if (valor)
                       
                    if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this, 
                                                       "¿Está seguro que desea grabar los datos ?")) {
                        try {
                            if (existenDatos) {
                                //validar nombre Impresora para modificar
                                                                
                                if ( vDescImpr.equalsIgnoreCase(txtDescImpresora.getText()) ){
                                    //Se puede almacenar correctamente                                    
                                    actualizarImpresora();
                                    MensajeAceptarTran();
                                }
                                else{
                                    if (validaRuta())                    
                                    {                                            
                                        FarmaUtility.showMessage(this, 
                                          "No se puede asignar un Nombre de Impresora Existente.", 
                                           txtDescImpresora);
                                    }
                                    else{
                                        actualizarImpresora();
                                        MensajeAceptarTran();
                                    }
                                }
                                                                
                            } else {
                                
                                if (validaRuta())                    
                                {
                                    FarmaUtility.showMessage(this, 
                                                       "No se puede asignar un Nombre de Impresora Existente.", 
                                                        txtDescImpresora);
                                }
                                else{
                                        
                                insertarImpresora();
                                log.debug("Se estan insertando los datos \n");
                                actualizaNumeracionImpresoras();
                                    MensajeAceptarTran();    
                                    
                                }
                            }
                            
                        } catch (SQLException ex) {
                            FarmaUtility.liberarTransaccion();
                            FarmaUtility.showMessage(this, 
                                                     "Error al grabar datos de la impresora: \n" +
                                    ex.getMessage(), txtDescImpresora);
                            log.error("",ex);

                        }
                        //cerrarVentana(true);
                    }
            }

        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }

    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    private void MensajeAceptarTran(){
        FarmaUtility.aceptarTransaccion();
        FarmaUtility.showMessage(this, 
                                 "La operación se realizó correctamente", 
                                 txtDescImpresora);
        cerrarVentana(true); 
    }

    private void cargarDatos() {
        if (existenDatos) {  
            
            txtNroImpresora.setText(VariablesImpresoras.vSecImprLocalTerm.toUpperCase());
            txtDescImpresora.setText(VariablesImpresoras.vDescImprLocalTerm.toUpperCase());
            //JMIRANDA 01/07/09 se almacena nombre Impresora
            vDescImpr = VariablesImpresoras.vDescImprLocalTerm.toUpperCase();
            cmbEstado.setSelectedItem(VariablesImpresoras.vEstImprLocalTerm);
            cmbTipoImpresora.setSelectedItem(VariablesImpresoras.vMarcaImprLocalTerm);
            
            txtDescImpresora.setEditable(false);
           
            FarmaUtility.moveFocus(txtDescImpresora);
            
        }
    }

    private boolean datosValidados() {

        if (txtDescImpresora.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, 
                                     "Ingrese la descripción de la impresora", 
                                     txtDescImpresora);
            return false;
        }
      
        if (cmbEstado.getSelectedItem().toString().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Debe seleccionar una serie", 
                                     cmbEstado);
            return false;
        }
        if (cmbTipoImpresora.getSelectedItem().toString().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Debe seleccionar una serie", 
                                     cmbTipoImpresora);
            return false;
        }
 
        return true;
    }

    private boolean validaRuta() {
        boolean result = false;
        try {
            String exist = 
                DBImpresoras.getExistImpTermica( 
                                          txtDescImpresora.getText().trim());            
            if (exist.equalsIgnoreCase("S")){
                result = true; }
            
        } catch (SQLException ex) {
            result = false;
            FarmaUtility.showMessage(this, 
                                     "Error al validar Impresora ingresada \n" +
                    ex.getMessage(), txtDescImpresora);
            log.error("",ex);
        }
        return result;

    }

    private boolean existenDatosImpresoraTerm() {
        if (VariablesImpresoras.vSecImprLocalTerm.equals("") &&
            VariablesImpresoras.vDescImprLocalTerm.equals("") ) {
            return false;
        } else {
            return true;            
        }
    }
    
    private void insertarImpresora() throws SQLException {
        
        DBImpresoras.ingresaImpresoraTermica(FarmaVariables.vCodGrupoCia, 
                                FarmaVariables.vCodLocal, 
                                txtDescImpresora.getText().trim(),      
                                FarmaLoadCVL.getCVLCode(ConstantsPtoVenta.NOM_HASTABLE_CMBMODELO_IMPRESORA, 
                                cmbTipoImpresora.getSelectedIndex()), 
                                FarmaLoadCVL.getCVLCode("Estado", 
                                cmbEstado.getSelectedIndex())
                                );
        
    }

    private void actualizarImpresora() throws SQLException {
        DBImpresoras.modificaImpresoraTermica(FarmaVariables.vCodGrupoCia, 
                                       FarmaVariables.vCodLocal,
                                       txtNroImpresora.getText().trim(),
                                       txtDescImpresora.getText().trim(), 
            FarmaLoadCVL.getCVLCode(ConstantsPtoVenta.NOM_HASTABLE_CMBMODELO_IMPRESORA, 
                cmbTipoImpresora.getSelectedIndex()), 
            FarmaLoadCVL.getCVLCode("Estado", 
                cmbEstado.getSelectedIndex())                                                                                
                                       );
       
    }

    private void actualizaNumeracionImpresoras() throws SQLException {
        //FarmaSearch.updateNumera("073");
        FarmaSearch.updateNumera(FarmaConstants.COD_NUMERA_IMPRESORA_TERMICA);
    }


    private void btnDescImpresora_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtDescImpresora);
    }

    private void cmbTipoImpresora_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                FarmaUtility.moveFocus(cmbEstado);                            
        }
        chkKeyPressed(e);        
    }

    private void cmbEstado_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtDescImpresora);
        }
        chkKeyPressed(e);
    }

    private void btnTipoImpresora_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbTipoImpresora);
    }

    private void btnEstado_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbEstado);
    }
}
