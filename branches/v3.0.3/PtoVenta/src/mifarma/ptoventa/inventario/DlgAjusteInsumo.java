package mifarma.ptoventa.inventario;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingConstants;

import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.reference.UtilityPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgAjusteInsumo extends JDialog {
    // **************************************************************************
    // Constructores
    // **************************************************************************
    private static final Logger log = LoggerFactory.getLogger(DlgAjusteInsumo.class);

    Frame myParentFrame;

    FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelWhite jContentPane = new JPanelWhite();

    private JPanelHeader pnlHeader1 = new JPanelHeader();

    private JPanelTitle pnlTitle1 = new JPanelTitle();

    private JLabelWhite lblProducto_T = new JLabelWhite();

    private JLabelWhite lblUnidadActual_T = new JLabelWhite();

    private JLabelWhite lblLaboratorio_T = new JLabelWhite();

    private JLabelWhite lblProducto = new JLabelWhite();

    private JLabelWhite lblUnidad = new JLabelWhite();

    private JLabelWhite lblLaboratorio = new JLabelWhite();

    private JButtonLabel btnMotivoAjuste = new JButtonLabel();

    private JLabelWhite lblStockModif_T = new JLabelWhite();

    private JTextFieldSanSerif txtStockModifEntero = new JTextFieldSanSerif();


    private JLabelFunction lblEsc = new JLabelFunction();

    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelWhite lblStock_T = new JLabelWhite();
    private JLabelWhite lblStock = new JLabelWhite();
    private JLabelWhite lblUnidadVenta = new JLabelWhite();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();

    private JLabelWhite jLabelWhite2 = new JLabelWhite();
    private JTextFieldSanSerif txtStockFraccion = new JTextFieldSanSerif();

    private JLabelWhite lblCantSuge = new JLabelWhite();
    private JTextFieldSanSerif txtCantSuge = new JTextFieldSanSerif();

    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JLabelOrange jLabelOrange1 = new JLabelOrange();
    private JLabelOrange jLabelOrange2 = new JLabelOrange();

    /*******************************************************************/
    private JPanelWhite pnlWhite3 = new JPanelWhite();
    private JPanelTitle pnlTitle3 = new JPanelTitle();

    private JLabelWhite lblRotacion_T = new JLabelWhite();
    private JLabelWhite lblNoDias_T = new JLabelWhite();

    private JLabelWhite lbl120d = new JLabelWhite();
    private JLabelWhite lbl90d = new JLabelWhite();
    private JLabelWhite lbl60d = new JLabelWhite();
    private JLabelWhite lbl30d = new JLabelWhite();

    private JLabelWhite lbl120d_T = new JLabelWhite();
    private JLabelWhite lbl90d_T = new JLabelWhite();
    private JLabelWhite lbl60d_T = new JLabelWhite();
    private JLabelWhite lbl30d_T = new JLabelWhite();

    private String tipo_rep;


    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    public DlgAjusteInsumo() {
        this(null, "", false);
    }

    public DlgAjusteInsumo(Frame parent, String title, boolean modal) {
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
    // Método "initialize()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(467, 487));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Ajuste de Producto Insumo");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlHeader1.setBounds(new Rectangle(15, 15, 425, 115));
        pnlTitle1.setBounds(new Rectangle(15, 135, 425, 130));
        lblProducto_T.setText("Producto:");
        lblProducto_T.setBounds(new Rectangle(20, 15, 90, 15));
        lblUnidadActual_T.setText("Unidad Actual:");
        lblUnidadActual_T.setBounds(new Rectangle(20, 40, 90, 15));
        lblLaboratorio_T.setText("Laboratorio:");
        lblLaboratorio_T.setBounds(new Rectangle(20, 65, 90, 15));
        lblProducto.setBounds(new Rectangle(120, 15, 300, 15));
        lblProducto.setFont(new Font("SansSerif", 0, 11));
        lblUnidad.setBounds(new Rectangle(120, 40, 295, 15));
        lblUnidad.setFont(new Font("SansSerif", 0, 11));
        lblLaboratorio.setBounds(new Rectangle(120, 65, 300, 15));
        lblLaboratorio.setFont(new Font("SansSerif", 0, 11));
        btnMotivoAjuste.setText("Motivo de Ajuste para Producto Insumo");
        btnMotivoAjuste.setBounds(new Rectangle(15, 15, 335, 20));

        /*btnMotivoAjuste.setMnemonic('M');
		 btnMotivoAjuste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnMotivoAjuste_actionPerformed(e);
			}
		}); */
        lblStockModif_T.setText("Stock Físico :");
        lblStockModif_T.setBounds(new Rectangle(15, 45, 75, 20));
        txtStockModifEntero.setBounds(new Rectangle(220, 45, 45, 20));
        txtStockModifEntero.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtStockModif_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtStockModif_keyTyped(e);
            }
        });
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(325, 420, 90, 20));
        lblF11.setText("[ F11 ] Aceptar");
        lblF11.setBounds(new Rectangle(220, 420, 90, 20));


        lblStock_T.setText("Stock:");
        lblStock_T.setBounds(new Rectangle(20, 90, 90, 15));
        lblStock.setBounds(new Rectangle(120, 90, 40, 15));
        lblStock.setFont(new Font("SansSerif", 0, 11));
        lblUnidadVenta.setBounds(new Rectangle(185, 90, 140, 15));
        lblUnidadVenta.setFont(new Font("SansSerif", 0, 11));
        jLabelWhite1.setText("Entero :");
        jLabelWhite1.setBounds(new Rectangle(165, 45, 55, 20));

        lblCantSuge.setText("Ingrese cantidad que requiere reponer :");
        lblCantSuge.setBounds(new Rectangle(10, 85, 250, 20));
        txtCantSuge.setBounds(new Rectangle(240, 85, 60, 20));
        txtCantSuge.setText("0");
        txtCantSuge.setDocument(new FarmaLengthText(120));
        txtCantSuge.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCantSuge_keyPressed(e);
            }
        });

        /* *************************************************************/
        pnlWhite3.setBounds(new Rectangle(25, 295, 400, 20));
        pnlWhite3.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlTitle3.setBounds(new Rectangle(25, 275, 400, 20));


        lbl120d.setBounds(new Rectangle(160, 5, 55, 15));
        lbl120d.setForeground(Color.black);
        lbl120d.setHorizontalAlignment(SwingConstants.CENTER);
        lbl90d.setBounds(new Rectangle(215, 5, 55, 15));
        lbl90d.setForeground(Color.black);
        lbl90d.setHorizontalAlignment(SwingConstants.CENTER);
        lbl60d.setBounds(new Rectangle(280, 5, 55, 15));
        lbl60d.setForeground(Color.black);
        lbl60d.setHorizontalAlignment(SwingConstants.CENTER);
        lbl30d.setBounds(new Rectangle(340, 5, 60, 15));
        lbl30d.setForeground(Color.black);
        lbl30d.setHorizontalAlignment(SwingConstants.CENTER);
        lbl120d_T.setText("120 d.");
        lbl120d_T.setBounds(new Rectangle(155, 5, 60, 15));
        lbl120d_T.setHorizontalAlignment(SwingConstants.CENTER);
        lbl90d_T.setText("90 d.");
        lbl90d_T.setBounds(new Rectangle(215, 5, 60, 15));
        lbl90d_T.setHorizontalAlignment(SwingConstants.CENTER);
        lbl60d_T.setText("60 d.");
        lbl60d_T.setBounds(new Rectangle(275, 5, 60, 15));
        lbl60d_T.setHorizontalAlignment(SwingConstants.CENTER);
        lbl30d_T.setText("30 d.");
        lbl30d_T.setBounds(new Rectangle(340, 5, 60, 15));
        lbl30d_T.setHorizontalAlignment(SwingConstants.CENTER);

        lblRotacion_T.setText("Consumo Mensual");
        lblRotacion_T.setBounds(new Rectangle(5, 5, 115, 15));
        lblRotacion_T.setForeground(Color.black);
        lblNoDias_T.setText("Meses");
        lblNoDias_T.setBounds(new Rectangle(5, 5, 70, 15));

        pnlWhite3.add(lbl30d, null);
        pnlWhite3.add(lbl60d, null);
        pnlWhite3.add(lbl90d, null);
        pnlWhite3.add(lbl120d, null);
        pnlWhite3.add(lblRotacion_T, null);
        pnlTitle3.add(lbl30d_T, null);
        pnlTitle3.add(lbl60d_T, null);
        pnlTitle3.add(lbl90d_T, null);

        pnlTitle3.add(lbl120d_T, null);
        /* ************************************************************** */


        pnlTitle3.add(lblNoDias_T, null);
        jContentPane.add(pnlWhite3, null);
        jContentPane.add(pnlTitle3, null);
        jContentPane.add(lblF11, null);
        pnlTitle1.add(lblCantSuge, null);
        pnlTitle1.add(txtCantSuge, null);
        jLabelWhite2.setText("Fraccion");
        jLabelWhite2.setBounds(new Rectangle(285, 45, 55, 20));
        txtStockFraccion.setBounds(new Rectangle(345, 45, 45, 20));
        txtStockFraccion.setText("0");
        txtStockFraccion.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtStockFraccion_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtStockFraccion_keyTyped(e);
            }
        });
        pnlTitle1.add(txtStockFraccion, null);
        pnlTitle1.add(jLabelWhite2, null);
        pnlTitle1.add(jLabelWhite1, null);
        pnlTitle1.add(txtStockModifEntero, null);
        pnlTitle1.add(lblStockModif_T, null);
        pnlTitle1.add(btnMotivoAjuste, null);
        jPanelHeader1.setBounds(new Rectangle(20, 330, 425, 65));
        jPanelHeader1.setBackground(Color.white);

        jLabelOrange1.setText("LA CANTIDAD A INGRESAR DEBE SER IGUAL AL ");
        jLabelOrange1.setBounds(new Rectangle(10, 5, 405, 30));
        jLabelOrange1.setForeground(Color.red);
        jLabelOrange1.setFont(new Font("DialogInput", 1, 15));

        jLabelOrange2.setText("STOCK FÍSICO DEL LOCAL");
        jLabelOrange2.setBounds(new Rectangle(10, 35, 405, 20));
        jLabelOrange2.setForeground(Color.red);
        jLabelOrange2.setFont(new Font("DialogInput", 1, 15));

        jContentPane.add(lblEsc, null);
        jContentPane.add(pnlTitle1, null);
        pnlHeader1.add(lblUnidadVenta, null);
        pnlHeader1.add(lblStock, null);
        pnlHeader1.add(lblStock_T, null);
        pnlHeader1.add(lblLaboratorio, null);
        pnlHeader1.add(lblUnidad, null);
        pnlHeader1.add(lblProducto, null);
        pnlHeader1.add(lblLaboratorio_T, null);
        pnlHeader1.add(lblUnidadActual_T, null);
        pnlHeader1.add(lblProducto_T, null);
        jContentPane.add(pnlHeader1, null);
        jContentPane.add(jPanelHeader1, null);
        jPanelHeader1.add(jLabelOrange1, null);
        jPanelHeader1.add(jLabelOrange2, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initialize() {
        //initCmbMotivoAjuste();
        cargarMeses();
    };

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************
    /* 	private void btnMotivoAjuste_actionPerformed(ActionEvent e) {
		FarmaUtility.moveFocus(cmbMotivoAjuste);
	} */

    private void btnCancelar_actionPerformed(ActionEvent e) {
        this.setVisible(false);
    }

    private void txtFracModif_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtStockModif_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtStockFraccion.isEnabled())
                FarmaUtility.moveFocus(txtStockFraccion);
            else
                FarmaUtility.moveFocus(txtCantSuge);
        } else {
            chkKeyPressed(e);
        }
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtStockModifEntero);
        if (VariablesInventario.vCFraccion.equalsIgnoreCase("1")) {
            txtStockFraccion.setEnabled(false);
            txtStockFraccion.setText("0");
        }
        mostrarDatos();
    }

    private void cmbMotivoAjuste_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtStockModifEntero);
        } else {
            chkKeyPressed(e);
        }
    }

    private void txtStockModif_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtStockModifEntero, e);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F11(e)) {

            /*  if(!validaIngresoStock()&&
                    (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_ADMLOCAL))){// 08.08.2014 rherrera
                        FarmaUtility.showMessage(this,"ERROR: LA CANTIDAD A INGRESAR DEBE SER MENOR AL STOCK DEL SISTEMA",txtStockModifEntero);
                }else { */
            validaIngresoStock();
            if (datosValidados())
                if (JConfirmDialog.rptaConfirmDialog(this, "¿Está seguro que desea grabar el ajuste? \n" +
                        "   Entero: " + txtStockModifEntero.getText().trim() + "     Fracción: " +
                        txtStockFraccion.getText().trim())) {
                    try {
                        insertarAjusteKardex();
                        FarmaUtility.aceptarTransaccion();
                        VariablesInventario.vStk_ModEntero = txtStockModifEntero.getText().trim();
                        VariablesInventario.vStk_ModFrac = txtStockFraccion.getText().trim();
                        SimpleDateFormat a = new SimpleDateFormat("dd/MM/yyyy");
                        VariablesInventario.vFecIniMovKardex = a.format(Calendar.getInstance().getTime());


                        FarmaUtility.showMessage(this, "El ajuste se guardó correctamente", txtStockModifEntero);
                        tipo_rep = "";
                    } catch (SQLException sql) {
                        FarmaUtility.liberarTransaccion();
                        FarmaUtility.showMessage(this, "Ocurrió un error al guardar el ajuste: \n" +
                                sql.getMessage(), txtStockModifEntero);
                        log.error("", sql);
                    }
                    cerrarVentana(true);
                }
            //}
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.cerrarVentana(false);
        }
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************
    /* private void initCmbMotivoAjuste() {
		ArrayList parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		FarmaLoadCVL.loadCVLFromSP(cmbMotivoAjuste, "cmbMotivoAjuste","PTOVENTA_INV.INV_LISTA_MOT_AJUST_KRDX(?)", parametros, false);
	} */

    /* public void initCmbMotivoAjuste() { //06.08.2014 cambio a publico

	                ArrayList parametros = new ArrayList();
	                parametros.add(FarmaVariables.vCodGrupoCia);
	                parametros.add(tipo_rep); //rherrera 06.08.2014
	                //FarmaLoadCVL.loadCVLFromSP(cmbMotivoAjuste, "cmbMotivoAjuste","PTOVENTA_INV.INV_LISTA_MOT_AJUST_KRDX(?,?)", parametros, false);
	                log.debug("invocando a PTOVENTA_INV.INV_LISTA_MOT_AJUST_KRDX(?,?):"+parametros);
                        if (tipo_rep.equals(ConstantsCaja.TIPO_REPOSICION_INSUMOS))
	                    FarmaLoadCVL.loadCVLFromSP(cmbMotivoAjuste, "cmbMotivoAjuste","PTOVENTA_INV.INV_LISTA_MOT_AJUST_KRDX(?,?)", parametros, false);
	                       else
	                    FarmaLoadCVL.loadCVLFromSP(cmbMotivoAjuste, "cmbMotivoAjuste2","PTOVENTA_INV.INV_LISTA_MOT_AJUST_KRDX(?,?)", parametros, false);
	        } */

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void mostrarDatos() {
        lblProducto.setText(VariablesInventario.vDescProd);
        lblLaboratorio.setText(VariablesInventario.vNomLab);
        lblUnidad.setText(VariablesInventario.vDescUnidPresent);
        lblUnidadVenta.setText(VariablesInventario.vDescUnidFrac);
        lblStock.setText("" + VariablesInventario.vStock);
    }

    private boolean datosValidados() {
        boolean rpta = true;

        /* 	if (cmbMotivoAjuste.getSelectedIndex()==0) {
			FarmaUtility.showMessage(this, "Seleccione una opcion válida",
					cmbMotivoAjuste);
			return false;
		} */
        if (txtStockModifEntero.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Ingrese una cantidad válida del stock", txtStockModifEntero);
            return false;
        }
        if (!FarmaUtility.isInteger(txtStockModifEntero.getText().trim())) {
            FarmaUtility.showMessage(this, "Ingrese una cantidad válida del stock", txtStockModifEntero);
            return false;
        }
        if (txtStockFraccion.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Ingrese una cantidad válida a fraccionar", txtStockFraccion);
            return false;
        }
        if (!FarmaUtility.isInteger(txtStockFraccion.getText().trim())) {
            FarmaUtility.showMessage(this, "Ingrese una cantidad válida a fraccionar", txtStockFraccion);
            return false;
        }
        if (FarmaUtility.getDecimalNumber(txtStockModifEntero.getText().trim()) *
            Integer.parseInt(VariablesInventario.vFrac) +
            FarmaUtility.getDecimalNumber(txtStockFraccion.getText().trim()) ==
            FarmaUtility.getDecimalNumber(lblStock.getText().trim())) {
            /* FarmaUtility.showMessage(this, "La cantidad ingresada debe ser distinto del Stock Actual.",
					txtStockModifEntero);
			return false; */
            return JConfirmDialog.rptaConfirmDialog(this, "La cantidad ingresada es el mismo al Stock Actual \n" +
                    "¿Está seguro de continuar? ");

        }
        if (txtCantSuge.getText().trim().length() == 0) {
            FarmaUtility.showMessage(this, "Ingrese una cantidad válida a reponer", txtCantSuge);
            return false;
        }
        if (!FarmaUtility.isInteger(txtCantSuge.getText().trim())) {
            FarmaUtility.showMessage(this, "Ingrese una cantidad válida a reponer", txtCantSuge);
            return false;
        }


        return rpta;
    }

    private void insertarAjusteKardex() throws SQLException {
        log.debug("Integer.parseInt(VariablesInventario.vFrac) : " + Integer.parseInt(VariablesInventario.vFrac));
        // txtGlosa.setText(txtGlosa.getText().toUpperCase());
        int entero = Integer.parseInt(txtStockModifEntero.getText().trim());
        int fraccion = Integer.parseInt(txtStockFraccion.getText().trim());
        log.debug("entero : " + entero);
        log.debug("fraccion : " + fraccion);
        int cantidad = entero * Integer.parseInt(VariablesInventario.vFrac) + fraccion;
        String cantidadCompleta = "" + cantidad;
        log.debug("cantidad : " + cantidadCompleta);

        DBInventario.ingresaAjusteKardex( /* FarmaLoadCVL.getCVLCode(
    	                    "cmbMotivoAjuste", cmbMotivoAjuste.getSelectedIndex()) */
                tipo_rep, cantidadCompleta, ""); //txtGlosa.getText().trim().toUpperCase());

        /* 	    if (tipo_rep.equals(ConstantsCaja.TIPO_REPOSICION_INSUMOS))
	        DBInventario.ingresaAjusteKardex(FarmaLoadCVL.getCVLCode(
	                        "cmbMotivoAjuste", cmbMotivoAjuste.getSelectedIndex()),
	        cantidadCompleta,
	        txtGlosa.getText().trim().toUpperCase());
	           else
	               //rherrera 09.09.2014 -- cambio para insumos
                DBInventario.ingresaAjusteKardex(FarmaLoadCVL.getCVLCode(
                                "cmbMotivoAjuste2", cmbMotivoAjuste.getSelectedIndex()),
                cantidadCompleta,
                txtGlosa.getText().trim().toUpperCase()); */
    }


    private void txtCantSuge_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtStockModifEntero);
        } else {
            chkKeyPressed(e);
        }
    }


    private void txtStockFraccion_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtCantSuge);
        } else {
            chkKeyPressed(e);
        }
    }

    private void txtStockFraccion_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtStockFraccion, e);
    }

    public void validaIngresoStock() {

        boolean val = false;
        int entero = 0;
        int stock = 0;
        int fraccion = 0;
        try {
            entero = Integer.parseInt(txtStockModifEntero.getText().trim());
        } catch (Exception ex) {
            entero = 0;
        }


        try {
            fraccion = Integer.parseInt(txtStockFraccion.getText().trim());
        } catch (Exception ex) {
            fraccion = 0;
        }

        try {
            stock = Integer.parseInt(lblStock.getText().trim());
        } catch (Exception ex) {
            stock = 0;
        }

        int cantidad = entero * Integer.parseInt(VariablesInventario.vFrac) + fraccion;


        tipo_rep = "001"; //cantidad ingresada menor al stock //motivo SALIDA DE INSUMOS


        //tipo_rep="000" ;//cantidad ingresada mayor al stock //motivo ANULACION SALIDA DE INSUMOS


    }

    private void cargarMeses() {
        ArrayList lista = new ArrayList();
        ArrayList myLista = new ArrayList();
        try {
            DBInventario.cargarMeses(lista);
        } catch (SQLException f) {
            log.error("", f);
        }
        myLista = (ArrayList)lista.get(0);
        lbl30d_T.setText(myLista.get(0).toString());
        lbl60d_T.setText(myLista.get(1).toString());
        lbl90d_T.setText(myLista.get(2).toString());
        lbl120d_T.setText(myLista.get(3).toString());
    }

    public void mostrarDetalle(String cCodProd) {
        ArrayList lista = new ArrayList();
        try {
            DBInventario.cargaListaProductosPedido(lista, cCodProd);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al cargar la lista de productos : \n" +
                    sql.getMessage(), txtStockModifEntero);
            return;
        }

        if (lista.size() > 0) {
            lista = (ArrayList)lista.get(0);
            lbl30d.setText(lista.get(14).toString());
            lbl60d.setText(lista.get(15).toString());
            lbl90d.setText(lista.get(16).toString());
            lbl120d.setText(lista.get(17).toString());

        }


    }


}
