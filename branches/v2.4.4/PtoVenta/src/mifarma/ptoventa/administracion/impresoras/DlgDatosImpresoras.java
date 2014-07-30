package mifarma.ptoventa.administracion.impresoras;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
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

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.impresoras.reference.DBImpresoras;
import mifarma.ptoventa.administracion.impresoras.reference.VariablesImpresoras;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgDatosImpresoras extends JDialog
{
    Frame myParentFrame;
    private static final Logger log = LoggerFactory.getLogger(DlgDatosImpresoras.class);
    private boolean existenDatos;
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelTitle pnlDatosImpresora = new JPanelTitle();
    private JLabelFunction lblAceptar = new JLabelFunction();
    private JLabelFunction lblSalir = new JLabelFunction();
    private JLabelWhite lblNroImpresora_T = new JLabelWhite();
    private JTextFieldSanSerif txtNroImpresora = new JTextFieldSanSerif();
    private JButtonLabel btnDescImpresora = new JButtonLabel();
    private JTextFieldSanSerif txtDescImpresora = new JTextFieldSanSerif();
    private JComboBox cmbComprobante = new JComboBox();
    private JComboBox cmbSerie = new JComboBox();
    private JTextFieldSanSerif txtNroComprobante = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtColaImpresion = new JTextFieldSanSerif();
    private JButtonLabel btnColaImp = new JButtonLabel();
    private JButtonLabel btnComprobante = new JButtonLabel();
    private JButtonLabel btnSerie = new JButtonLabel();
    private JButtonLabel btnNroComp = new JButtonLabel();
    private JComboBox cmbModelo = new JComboBox();
    private JButtonLabel btnModelo = new JButtonLabel();
    private JTextFieldSanSerif txtSerieImpr = new JTextFieldSanSerif();
    private JButtonLabel btnNSerie = new JButtonLabel();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgDatosImpresoras()
    {   this(null, "", false);
    }

    public DlgDatosImpresoras(Frame parent, String title, boolean modal)
    {   super(parent, title, modal);
        myParentFrame = parent;
        try
        {   jbInit();
            initialize();
        }
        catch (Exception e)
        {   log.error("",e);
        }
    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************
    
    private void jbInit() throws Exception
    {
        this.setSize(new Dimension(632, 174));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Impresora");
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                }
        });
        jContentPane.setLayout(null);
        jContentPane.setFocusable(false);
        pnlDatosImpresora.setBounds(new Rectangle(10, 10, 610, 105));
        pnlDatosImpresora.setFocusable(false);
        lblAceptar.setBounds(new Rectangle(405, 120, 100, 20));
        lblAceptar.setText("[F11] Aceptar");
        lblAceptar.setFocusable(false);
        lblSalir.setBounds(new Rectangle(515, 120, 105, 20));
        lblSalir.setText("[Esc] Salir");
        lblSalir.setFocusable(false);
        lblNroImpresora_T.setText("Nro. Impresora :");
        lblNroImpresora_T.setBounds(new Rectangle(10, 10, 100, 20));
        lblNroImpresora_T.setFocusable(false);
        txtNroImpresora.setBounds(new Rectangle(105, 10, 35, 20));
        txtNroImpresora.setEditable(false);
        btnDescImpresora.setText("Descripción :");
        btnDescImpresora.setBounds(new Rectangle(155, 10, 75, 20));
        btnDescImpresora.setBorder(BorderFactory.createLineBorder(Color.black,
                    1));
        btnDescImpresora.setMnemonic('d');
        btnDescImpresora.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnDescImpresora_actionPerformed(e);
            }
        });
        txtDescImpresora.setBounds(new Rectangle(230, 10, 200, 20));
        txtDescImpresora.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                    txtDescImpresora_keyPressed(e);
            }
        });
        cmbComprobante.setBounds(new Rectangle(105, 40, 110, 20));
        cmbComprobante.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                cmbComprobante_itemStateChanged(e);
            }
        });
        cmbComprobante.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                    cmbComprobante_keyPressed(e);
            }
        });
        cmbSerie.setBounds(new Rectangle(275, 40, 80, 20));
        cmbSerie.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                    cmbSerie_keyPressed(e);
            }
        });
        txtNroComprobante.setBounds(new Rectangle(475, 40, 125, 20));
        txtNroComprobante.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                txtNroComprobante_keyPressed(e);
            }
            public void keyTyped(KeyEvent e)
            {
                txtNroComprobante_keyTyped(e);
            }
        });
        txtColaImpresion.setBounds(new Rectangle(135, 70, 295, 20));
        txtColaImpresion.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                txtColaImpresion_keyPressed(e);
            }
        });
        btnColaImp.setText("Cola de impresión :");
        btnColaImp.setBounds(new Rectangle(15, 70, 110, 20));
        btnColaImp.setMnemonic('i');
        btnColaImp.setFocusable(false);
        btnColaImp.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnColaImp_actionPerformed(e);
            }
        });
        btnComprobante.setText("Comprobante :");
        btnComprobante.setBounds(new Rectangle(15, 40, 85, 20));
        btnComprobante.setMnemonic('c');
        btnComprobante.setFocusable(false);
        btnComprobante.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnComprobante_actionPerformed(e);
            }
        });
        btnSerie.setText("Serie :");
        btnSerie.setBounds(new Rectangle(225, 40, 45, 20));
        btnSerie.setMnemonic('s');
        btnSerie.setFocusable(false);
        btnSerie.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnSerie_actionPerformed(e);
            }
        });
        btnNroComp.setText("Nro. comprobante:");
        btnNroComp.setBounds(new Rectangle(365, 40, 105, 20));
        btnNroComp.setMnemonic('n');
        btnNroComp.setFocusable(false);
        btnNroComp.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnNroComp_actionPerformed(e);
            }
        });
        cmbModelo.setBounds(new Rectangle(505, 70, 95, 20));
        cmbModelo.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                cmbModelo_keyPressed(e);
            }
        });
        btnModelo.setText("Modelo:");
        btnModelo.setBounds(new Rectangle(445, 70, 55, 20));
        btnModelo.setMnemonic('m');
        btnModelo.setFocusable(false);
        btnModelo.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnModelo_actionPerformed(e);
            }
        });
        txtSerieImpr.setBounds(new Rectangle(490, 10, 110, 20));        
        txtSerieImpr.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                txtSerieImpr_keyPressed(e);
            }
        });
        txtSerieImpr.setEditable(false);
        btnNSerie.setText("Nº Serie :");
        btnNSerie.setBounds(new Rectangle(435, 10, 50, 20));
        btnNSerie.setMnemonic('e');
        btnNSerie.setFocusable(false);
        btnNSerie.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {
                btnNSerie_actionPerformed(e);
            }
        });
        jContentPane.add(lblSalir, null);
        jContentPane.add(lblAceptar, null);
        pnlDatosImpresora.add(btnNSerie, null);
        pnlDatosImpresora.add(txtSerieImpr, null);
        pnlDatosImpresora.add(btnModelo, null);
        pnlDatosImpresora.add(cmbModelo, null);
        pnlDatosImpresora.add(btnNroComp, null);
        pnlDatosImpresora.add(btnSerie, null);
        pnlDatosImpresora.add(btnComprobante, null);
        pnlDatosImpresora.add(btnColaImp, null);
        pnlDatosImpresora.add(txtColaImpresion, null);
        pnlDatosImpresora.add(txtNroComprobante, null);
        pnlDatosImpresora.add(cmbSerie, null);
        pnlDatosImpresora.add(cmbComprobante, null);
        pnlDatosImpresora.add(txtDescImpresora, null);
        pnlDatosImpresora.add(btnDescImpresora, null);
        pnlDatosImpresora.add(txtNroImpresora, null);
        pnlDatosImpresora.add(lblNroImpresora_T, null);
        jContentPane.add(pnlDatosImpresora, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        //AGREGADO 20/06/2006 ERIOS
        txtDescImpresora.setLengthText(30);
        txtNroComprobante.setLengthText(7);
        txtColaImpresion.setLengthText(120);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize()
    {
        FarmaVariables.vAceptar = false;
        existenDatos = existenDatosImpresora();
        initCombos();
        cargarDatos();
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initCombos()
    {
        initComboModelo();
        initCombo1();
        initCombo2();
    }

    private void initCombo1()
    {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        
        FarmaLoadCVL.loadCVLFromSP(cmbComprobante, "cmbComprobante","PTOVENTA_ADMIN_IMP.IMP_LISTA_TIPOS_COMPROBANTE(?)", parametros, false);
        //MODIFICADO 20/06/2006 ERIOS
        cmbComprobante.setEnabled(!existenDatos);
    }

    private void initCombo2()
    {
        ArrayList parametros2 = new ArrayList();
        parametros2.add(FarmaVariables.vCodGrupoCia);
        parametros2.add(FarmaVariables.vCodLocal);
        parametros2.add(VariablesImpresoras.vTipComp);
        parametros2.add(VariablesImpresoras.vNumSerie);
        
        FarmaLoadCVL.loadCVLFromSP(cmbSerie,
                                    "cmbSerie",
                                    "PTOVENTA_ADMIN_IMP.IMP_LISTA_SERIES_COMPROBANTE(?,?,?,?)", 
                                    parametros2,
                                    false, 
                                    true);
        //MODIFICADO 20/06/2006 ERIOS
        cmbSerie.setEnabled(!existenDatos);
    }
        
    /**
    * Inicializa el combo de Modelo de Ticketera
    * @author ERIOS
    * @since 17.06.2013
    */
    private void initComboModelo()
    {
        cmbModelo.removeAllItems();
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        FarmaLoadCVL.loadCVLFromSP(cmbModelo, 
                                   "cmbModelo",
                                   "PTOVENTA_ADMIN_IMP.IMP_LISTA_MODELOS(?,?)", 
                                   parametros, 
                                   false);        
        
        if(VariablesImpresoras.vTipComp.equals(ConstantsVentas.TIPO_COMP_TICKET))
        {
            try
            {   
                VariablesImpresoras.vModeloImpresora = DBImpresoras.getModeloImpresora(VariablesImpresoras.vSecImprLocal);
                cmbModelo.setSelectedItem(VariablesImpresoras.vModeloImpresora);                        
            }
            catch (Exception e)
            {   log.debug("", e);
                VariablesImpresoras.vModeloImpresora = "";
            }
            btnModelo.setVisible(true);
            cmbModelo.setVisible(true);
            btnNSerie.setVisible(true);
            txtSerieImpr.setVisible(true);
            
            //ERIOS 11.11.2013 Se habilita para el perfil operador
            if(FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_OPERADOR_SISTEMAS))
            {
                txtSerieImpr.setEditable(true);
            }
        }
        else
        {
            btnModelo.setVisible(false);
            cmbModelo.setVisible(false);
            btnNSerie.setVisible(false);
            txtSerieImpr.setVisible(false);
        }
    }

    private void this_windowOpened(WindowEvent e)
    {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtDescImpresora);
        //JCORTEZ 14.03.09 solo para tipo ticket no se podra modificar la cola de impresion
       /* if (VariablesImpresoras.vTipoComp.trim().equalsIgnoreCase(ConstantsPtoVenta.TIP_COMP_TICKET)){//tipo ticket solamente
            txtColaImpresion.setEditable(false);
        }else
            txtColaImpresion.setEditable(true);*/
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void txtDescImpresora_keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            if(existenDatos)
                FarmaUtility.moveFocus(txtNroComprobante);
            else
                FarmaUtility.moveFocus(cmbComprobante);
        }
        chkKeyPressed(e);
    }

    private void txtNroComprobante_keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtColaImpresion);
        }
        chkKeyPressed(e);
    }

    private void txtColaImpresion_keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtDescImpresora);
        }
        chkKeyPressed(e);
    }

    private void cmbComprobante_keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(cmbSerie);
        }
        chkKeyPressed(e);
    }

    private void cmbSerie_keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            FarmaUtility.moveFocus(txtNroComprobante);
        }
        chkKeyPressed(e);
    }

    private void cmbComprobante_itemStateChanged(ItemEvent e)
    {
        VariablesImpresoras.vTipComp = FarmaLoadCVL.getCVLCode("cmbComprobante", cmbComprobante.getSelectedIndex()).toString().trim();
        cmbSerie.removeAllItems();
        initCombo2();
        initComboModelo();
    }

    private void txtNroComprobante_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtNroComprobante, e);
    }

    private void btnDescImpresora_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtDescImpresora);
    }

    private void btnComprobante_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbComprobante);
    }

    private void btnSerie_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbSerie);
    }

    private void btnNroComp_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtNroComprobante);
    }

    private void btnColaImp_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtColaImpresion);
    }

    private void btnModelo_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbModelo);
    }

    private void cmbModelo_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtDescImpresora);
        }
        chkKeyPressed(e);
    }

    private void btnNSerie_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtSerieImpr);
    }
    
    private void txtSerieImpr_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtDescImpresora);
        }
        chkKeyPressed(e);
    }
    
    // **************************************************************************
	// Metodos auxiliares de eventos
	// **************************************************************************
	private void chkKeyPressed(KeyEvent e) {
		if (UtilityPtoVenta.verificaVK_F1(e)) // Reservado para ayuda
		{
		} else if (UtilityPtoVenta.verificaVK_F11(e)) {
                
                    boolean valor=true;
                    if (datosValidados()){
                        //JCORTEZ 14.03.09 solo para tipo ticket no se podra modificar la cola de impresion con una que ya exista
                        if (VariablesImpresoras.vTipoComp.trim().equalsIgnoreCase(ConstantsPtoVenta.TIP_COMP_TICKET)){
                            if(!validaRuta()){
                                FarmaUtility.showMessage(this,"No se puede asignar una ruta de impresion que ya existe.",txtColaImpresion);
                                valor=false;
                            }
                        }
                            if(valor)
                            if (JConfirmDialog.rptaConfirmDialog(this,"¿Está seguro que desea grabar los datos ?")) {
                                    try {
                                            if (existenDatos) {
                                                    actualizarImpresora();
                                            } else {
                                                    insertarImpresora();
                                                    actualizaNumeracionImpresoras();
                                            }

                                            FarmaUtility.aceptarTransaccion();
                                            FarmaUtility.showMessage(this,"La operación se realizó correctamente",txtDescImpresora);

                                    } catch (SQLException ex) {
                                            FarmaUtility.liberarTransaccion();
                                            FarmaUtility.showMessage(this,"Error al grabar datos de la impresora: \n"+ ex.getMessage(), txtDescImpresora);
                                            log.error("",ex);

                                    }
                                    cerrarVentana(true);
                            }
                }

		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			cerrarVentana(false);
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

    private void cargarDatos() {
        if (existenDatos) {
            txtNroImpresora.setText(VariablesImpresoras.vSecImprLocal.toUpperCase());
            txtDescImpresora.setText(VariablesImpresoras.vDescImprLocal.toUpperCase());
            txtNroComprobante.setText(VariablesImpresoras.vNumComp.toUpperCase());
            txtColaImpresion.setText(VariablesImpresoras.vRutaImpr.toUpperCase());
            cmbComprobante.setSelectedItem(VariablesImpresoras.vDescComp);
            cmbSerie.setSelectedItem(VariablesImpresoras.vNumSerie);
            txtDescImpresora.setEditable(false);
            txtSerieImpr.setText(VariablesImpresoras.vSerieImpresora);
            FarmaUtility.moveFocus(txtDescImpresora);
        }
    }

	private boolean datosValidados() {

		if (txtDescImpresora.getText().trim().length() == 0) {
			FarmaUtility.showMessage(this,"Ingrese la descripción de la impresora", txtDescImpresora);
			return false;
		}

		if (cmbComprobante.getSelectedItem().toString().trim().length() == 0) {
			FarmaUtility.showMessage(this,"Debe seleccionar un tipo de comprobante", cmbComprobante);
			return false;
		}

		if (cmbSerie.getSelectedItem().toString().trim().length() == 0) {
			FarmaUtility.showMessage(this, "Debe seleccionar una serie",cmbSerie);
			return false;
		}

		if (txtNroComprobante.getText().trim().length() == 0) {
			FarmaUtility.showMessage(this,"Debe ingresar el número de comprobante",txtNroComprobante);
			return false;
		}

		if (txtColaImpresion.getText().trim().length() == 0) {
			FarmaUtility.showMessage(this,"Debe ingresar la ruta de la impresora", txtColaImpresion);
			return false;
		}

		return true;
	}
        
        
        
        private boolean  validaRuta(){
        
            boolean result=true;
            try {
                    String exist=DBImpresoras.getExistRuta(ConstantsPtoVenta.TIP_COMP_TICKET,txtColaImpresion.getText().trim(),txtNroImpresora.getText().trim());
                        if(exist.equalsIgnoreCase("S"))
                        result=false;
            } catch (SQLException ex) {
                    result=false;
                    FarmaUtility.showMessage(this,"Error al validar ruta ingresada \n"+ ex.getMessage(), txtColaImpresion);
                    log.error("",ex);
            }
            return result;

        }

	private boolean existenDatosImpresora() {
		if (VariablesImpresoras.vSecImprLocal.equals("")) {
			return false;
		} else {
			return true;
		}
	}

    private void insertarImpresora() throws SQLException {
        //ERIOS 07.08.2013 Se agrega SerieImpr
        String vCodigoSerie = FarmaLoadCVL.getCVLCode("cmbSerie", cmbSerie.getSelectedIndex());
        String vCodigoComprobante = FarmaLoadCVL.getCVLCode("cmbComprobante", cmbComprobante.getSelectedIndex());
        String vCodigoModelo = cmbModelo.getSelectedItem().toString();
        String vSerieImpr = txtSerieImpr.getText();
        DBImpresoras.ingresaImpresora(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, vCodigoSerie,
                                      vCodigoComprobante, txtDescImpresora.getText().trim(),
                                      txtNroComprobante.getText().trim(), txtColaImpresion.getText().trim(),
                                      vCodigoModelo,vSerieImpr);
    }

    private void actualizarImpresora() throws SQLException {
        String vCodigoSerie = FarmaLoadCVL.getCVLCode("cmbSerie", cmbSerie.getSelectedIndex());
        String vCodigoComprobante = FarmaLoadCVL.getCVLCode("cmbComprobante", cmbComprobante.getSelectedIndex());
        String vCodigoModelo = cmbModelo.getSelectedItem().toString();
        String vSerieImpr = txtSerieImpr.getText();
        DBImpresoras.modificaImpresora(FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal,
                                       txtNroImpresora.getText().trim(), vCodigoSerie, vCodigoComprobante,
                                       txtDescImpresora.getText().trim(), txtNroComprobante.getText().trim(),
                                       txtColaImpresion.getText().trim(), vCodigoModelo,vSerieImpr);
    }

	private void actualizaNumeracionImpresoras() throws SQLException {   
			FarmaSearch.updateNumera(FarmaConstants.COD_NUMERA_IMPRESORA);
	}

}
