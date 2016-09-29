package mifarma.ptoventa.tomainventario;


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

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.DlgIngresarLote;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.tomainventario.reference.DBTomaInv;
import mifarma.ptoventa.tomainventario.reference.VariablesTomaInv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgIngresoCantFracCodBarra extends JDialog {
    Frame myParentFrame;

    FarmaTableModel tableModel;
    private static final Logger log = LoggerFactory.getLogger(DlgIngresoCantFracCodBarra.class);
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelWhite pnlTitle1 = new JPanelWhite();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelWhite lblCodigoBarra_T = new JLabelWhite();
    private JLabelWhite lblCodigoBarra = new JLabelWhite();
    private JLabelWhite lblDescProducto_T = new JLabelWhite();
    private JLabelWhite lblDescProducto = new JLabelWhite();
    private JLabelWhite lblUnidad_T = new JLabelWhite();
    private JLabelWhite lblValFraccion = new JLabelWhite();
    private JTextFieldSanSerif txtCantidad = new JTextFieldSanSerif();
    private JButtonLabel btnCantidad = new JButtonLabel();
    private JButtonLabel btnFraccion = new JButtonLabel();
    private JTextFieldSanSerif txtFraccion = new JTextFieldSanSerif();
    private JButtonLabel btnUnidad = new JButtonLabel();
    private JLabelWhite lblUnidadValFrac = new JLabelWhite();
    private JButtonLabel btnValFraccion = new JButtonLabel();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    // LTAVARA 15/01/16 PROYECTO MAYORISTA
    private JTextFieldSanSerif txtMasterPack = new JTextFieldSanSerif();
    private JButtonLabel btnMasterPack = new JButtonLabel();
    private JTextFieldSanSerif txtLote = new JTextFieldSanSerif();
    private JButtonLabel btnLote = new JButtonLabel();
    private JTextFieldSanSerif txtFechaLote = new JTextFieldSanSerif();
    private JButtonLabel btnFechaLote = new JButtonLabel();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgIngresoCantFracCodBarra() {
        this(null, "", false);
    }

    public DlgIngresoCantFracCodBarra(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(583, 295));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Ingreso de Cantidad");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(5, 5, 565, 225));
        pnlTitle1.setBackground(Color.white);
        pnlTitle1.setBorder(BorderFactory.createLineBorder(new Color(225, 130, 14), 1));
        lblF1.setBounds(new Rectangle(90, 235, 120, 20));
        lblF1.setText("[ F1 ] Registrar Lote");
        lblF11.setBounds(new Rectangle(220, 235, 110, 20));
        lblF11.setText("[ Enter ] Aceptar");
        lblEsc.setBounds(new Rectangle(340, 235, 110, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblCodigoBarra_T.setText("Código Barra:");
        lblCodigoBarra_T.setForeground(new Color(43, 141, 39));
        lblCodigoBarra_T.setBounds(new Rectangle(15, 20, 80, 15));
        lblCodigoBarra.setFont(new Font("SansSerif", 0, 11));
        lblCodigoBarra.setForeground(new Color(225, 130, 14));
        lblCodigoBarra.setText("");
        lblCodigoBarra.setBounds(new Rectangle(125, 20, 150, 20));
        lblDescProducto_T.setText("Producto:");
        lblDescProducto_T.setForeground(new Color(43, 141, 39));
        lblDescProducto_T.setBounds(new Rectangle(15, 45, 95, 20));
        lblDescProducto.setFont(new Font("SansSerif", 0, 11));
        lblDescProducto.setForeground(new Color(225, 130, 14));
        lblDescProducto.setText("producto");
        lblDescProducto.setBounds(new Rectangle(125, 50, 425, 15));

        lblUnidad_T.setText("Unidad");
        lblUnidad_T.setBounds(new Rectangle(10, 60, 95, 15));
        lblUnidad_T.setForeground(new Color(225, 130, 14));
        lblValFraccion.setText("ValFrac");
        lblValFraccion.setBounds(new Rectangle(125, 140, 200, 15));
        lblValFraccion.setFont(new Font("SansSerif", 0, 11));
        lblValFraccion.setForeground(new Color(225, 130, 14));
        btnMasterPack.setText("Master Pack:");
        btnMasterPack.setBounds(new Rectangle(12, 170, 80, 20));
        btnMasterPack.setForeground(new Color(255, 130, 14));
        btnMasterPack.setMnemonic('m');
        btnMasterPack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnMasterPack_actionPerformed(e);
            }
        });
        txtMasterPack.setBounds(new Rectangle(15, 195, 60, 20));
        txtMasterPack.setEnabled(false);
        txtMasterPack.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtMasterPack_keyPressed(e);
            }
        });
    
        txtCantidad.setBounds(new Rectangle(95,195,60,20));
        txtCantidad.setLengthText(4);
        txtCantidad.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCantidad_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtCantidad_keyTyped(e);
            }

        });
       
        btnCantidad.setText("Entero:");
        btnCantidad.setBounds(new Rectangle(95, 170, 55, 20));
        btnCantidad.setForeground(new Color(255, 130, 14));
        //btnCantidad.setBounds(new Rectangle(10, 90, 60, 20));
        // btnCantidad.setForeground(new Color(255, 130, 14));
       btnCantidad.setMnemonic('e');
        btnCantidad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCantidad_actionPerformed(e);
            }
        });

        btnFraccion.setText("Fracción:");
       // btnFraccion.setMnemonic('f');
        btnFraccion.setBounds(new Rectangle(170, 175, 55, 15));
        btnFraccion.setForeground(new Color(255, 130, 14));
        btnFraccion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnFraccion_actionPerformed(e);
            }
        });
        txtFraccion.setBounds(new Rectangle(170,195,60,20));
        txtFraccion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtFraccion_actionPerformed(e);
            }
        });
        txtFraccion.setLengthText(4);
        txtFraccion.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFraccion_keyPressed(e);
            }
        });
        btnUnidad.setText("Laboratorio:");
        btnUnidad.setForeground(new Color(43, 141, 39));
        btnUnidad.setBounds(new Rectangle(15, 75, 140, 20));
        //
        lblUnidadValFrac.setFont(new Font("SansSerif", 0, 11));
        lblUnidadValFrac.setForeground(new Color(225, 130, 14));
        lblUnidadValFrac.setText("");
        lblUnidadValFrac.setBounds(new Rectangle(155, 75, 245, 15));
        btnValFraccion.setText("Valor Fracción: ");
        btnValFraccion.setForeground(new Color(43, 141, 39));
        btnValFraccion.setBounds(new Rectangle(15, 140, 115, 15));

        txtLote.setBounds(new Rectangle(245,195,90,20));
        txtLote.setLengthText(100);
        txtLote.setEnabled(false);
        txtLote.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtLote_keyPressed(e);
            }
            });
        btnLote.setText("Lote:");
        btnLote.setBounds(new Rectangle(245, 175, 55, 15));
        btnLote.setForeground(new Color(255, 130, 14));
     //   btnLote.setMnemonic('l');
        btnLote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLote_actionPerformed(e);
            }

            });

        txtFechaLote.setBounds(new Rectangle(350,195,90,20));
        txtFechaLote.setLengthText(10);
        txtFechaLote.setEnabled(false);
        txtFechaLote.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtFechaLote_keyPressed(e);
            }

            });
        btnFechaLote.setText("Fecha Venci.:");
        btnFechaLote.setBounds(new Rectangle(350, 175, 80, 15));
        btnFechaLote.setForeground(new Color(255, 130, 14));
       // btnFechaLote.setMnemonic('h');
        btnFechaLote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnFechaLote_actionPerformed(e);
            }

            });
        jLabel1.setBounds(new Rectangle(120, 105, 215, 20));
        jLabel1.setForeground(new Color(225, 130, 14));
        jLabel2.setText("Unidad Venta :");
        jLabel2.setBounds(new Rectangle(15, 105, 120, 20));
        jLabel2.setForeground(new Color(43, 141, 39));
        jLabel2.setFont(new Font("Dialog", 1, 11));
        jContentPane.add(lblF1, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(pnlTitle1, null);
        pnlTitle1.add(jLabel2, null);
        pnlTitle1.add(jLabel1, null);
        pnlTitle1.add(btnValFraccion, null);
        pnlTitle1.add(btnUnidad, null);
        pnlTitle1.add(txtFraccion, null);
        pnlTitle1.add(btnFraccion, null);
        pnlTitle1.add(txtCantidad, null);
        pnlTitle1.add(lblValFraccion, null);
        pnlTitle1.add(lblUnidad_T, null);
        pnlTitle1.add(lblDescProducto, null);
        pnlTitle1.add(lblDescProducto_T, null);
        pnlTitle1.add(lblCodigoBarra, null);
        pnlTitle1.add(lblCodigoBarra_T, null);
        pnlTitle1.add(lblUnidadValFrac, null);
        pnlTitle1.add(txtMasterPack, null);
        pnlTitle1.add(btnMasterPack, null);
        pnlTitle1.add(txtLote, null);
        pnlTitle1.add(txtFechaLote, null);
        pnlTitle1.add(btnFechaLote, null);
        pnlTitle1.add(btnCantidad, null);
        pnlTitle1.add(btnLote, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);

        //oculto
        lblDescProducto.setVisible(true);
        lblUnidad_T.setVisible(false);
        lblValFraccion.setVisible(true);
        lblDescProducto_T.setVisible(true);
        lblF1.setVisible(false);
    }

    // **************************************************************************
    // Método "initialize()"
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
        if(UtilityPtoVenta.isLocalVentaMayorista() && 
           VariablesTomaInv.vValFracMasterPack>0 && 
           VariablesTomaInv.vIndActualizaProd.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)){
                     FarmaUtility.moveFocus(txtMasterPack);                                                                                   
                 }else
                 FarmaUtility.moveFocus(txtCantidad);
    }

    private void txtCantidad_keyTyped(KeyEvent e) {

    }

    private void txtCantidad_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            ingresoCantidadFraccion("ENTERO", txtFraccion.isEnabled());
        }
        chkKeyPressed(e);
    }
    
    private void txtMasterPack_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            ingresoCantidadFraccion("", txtFraccion.isEnabled());
        }
      chkKeyPressed(e);
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
        /*
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    ingresoCantidadFraccion();

            } else
            */
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            VariablesTomaInv.vIndIngresaProd = "N"; //no ingresa Producto
            limpiarVariables();
            FarmaVariables.vAceptar = false;
            this.setVisible(false);
        } else{  //Registrar lote - Local mayorista LTAVARA 25/01/2015
            if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e) ) {
                registrarLote();
            }
        }
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void registrarLote(){
        DlgIngresarLote ingresarLote = new DlgIngresarLote(myParentFrame, "Registrar Lote", true,txtLote.getText().trim(),true);
        ingresarLote.codprodxx = VariablesTomaInv.vCodProducto;
        ingresarLote.setVisible(true);
    }
    
    private void mostrarDatos() {
        ArrayList pDatosUpd = new ArrayList();

        //mostrar el boton de registrar lote cuando es local Mayorista
        if(UtilityPtoVenta.isLocalVentaMayorista()){
            lblF1.setVisible(true);
            
        }
       
   
        try {
            if (VariablesTomaInv.vIndActualizaProd.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                //actualiza Producto
                DBTomaInv.getInfoProd(pDatosUpd, VariablesTomaInv.vCodProductoTemp);
                VariablesTomaInv.vDescripcion = FarmaUtility.getValueFieldArrayList(pDatosUpd, 0, 1);
                VariablesTomaInv.vUnidadPresentacion = FarmaUtility.getValueFieldArrayList(pDatosUpd, 0, 2);
                VariablesTomaInv.vFraccionLocal = FarmaUtility.getValueFieldArrayList(pDatosUpd, 0, 3);
                VariablesTomaInv.vUnidadVenta = FarmaUtility.getValueFieldArrayList(pDatosUpd, 0, 4);
                VariablesTomaInv.vIndLoteMayorista = FarmaUtility.getValueFieldArrayList(pDatosUpd, 0, 6);//LTAVARA 15/01/16 PROYECTO M
                VariablesTomaInv.vCodBarraIngresado = VariablesTomaInv.vCodBarraTemp;
                
                VariablesTomaInv.vCodProducto = VariablesTomaInv.vCodProductoTemp;
                lblCodigoBarra.setText(VariablesTomaInv.vCodBarraIngresado);
                lblDescProducto.setText(VariablesTomaInv.vCodProducto + " - " + VariablesTomaInv.vDescripcion + " - " +
                                        VariablesTomaInv.vUnidadPresentacion);
                lblUnidadValFrac.setText(VariablesTomaInv.vNomLab);
                lblValFraccion.setText(VariablesTomaInv.vFraccionLocal);
                jLabel1.setText(VariablesTomaInv.vUnidadVenta.trim());
                txtCantidad.setText(VariablesTomaInv.vCantEnteraTemp);
                if (Integer.parseInt(VariablesTomaInv.vFraccionLocal.trim()) == 1) {
                    txtFraccion.setText("0");
                    txtFraccion.setEnabled(false);
                } else {
                    txtFraccion.setText(VariablesTomaInv.vCantFracTemp);
                    txtFraccion.setEnabled(true);
                }
                FarmaUtility.moveFocus(txtCantidad);
                // LTAVARA 15/01/2016 valida si es un local mayorista
                if(UtilityPtoVenta.isLocalVentaMayorista()){
                    // no permitir modificar el lote ni fecha
                    txtLote.setText(VariablesTomaInv.vLoteIngresadoTemp);
                    txtLote.setEnabled(false); 
                    txtFechaLote.setText(VariablesTomaInv.vFechaLoteIngresadoTemp);
                    
                /*if (VariablesTomaInv.vIndLoteMayorista.equals("S")){
                    txtLote.setText(VariablesTomaInv.vLoteIngresadoTemp);
                    txtLote.setEnabled(true); 
                    txtFechaLote.setText(VariablesTomaInv.vFechaLoteIngresadoTemp);
                }*/
                }
                    
            } else {
                //VariablesInventario.vCodProducto
                ArrayList pDatos = new ArrayList();
                //   try {

                DBTomaInv.getInfoProd(pDatos, VariablesTomaInv.vCodProducto);

                //VariablesTomaInv.vCodBarraIngresado = "";
                //VariablesTomaInv.vCodProducto = "";

                if (pDatos.size() == 1) {
                    /*
                   p.COD_PROD                   || 'Ã' || 0
                   NVL(P.DESC_PROD,' ')               || 'Ã' || 1
                   NVL(P.DESC_UNID_PRESENT,' ') || 'Ã' || 2
                   PL.VAL_FRAC_LOCAL            || 'Ã' || 3
                   NVL(PL.UNID_VTA,' ')      || 'Ã' || 4
                   lab.nom_lab 5
                     * */

                    VariablesTomaInv.vDescripcion = FarmaUtility.getValueFieldArrayList(pDatos, 0, 1);
                    VariablesTomaInv.vUnidadPresentacion = FarmaUtility.getValueFieldArrayList(pDatos, 0, 2);
                    VariablesTomaInv.vFraccionLocal = FarmaUtility.getValueFieldArrayList(pDatos, 0, 3);
                    VariablesTomaInv.vUnidadVenta = FarmaUtility.getValueFieldArrayList(pDatos, 0, 4);
                    VariablesTomaInv.vLaboratorio_Barra = FarmaUtility.getValueFieldArrayList(pDatos, 0, 5);
                    VariablesTomaInv.vIndLoteMayorista = FarmaUtility.getValueFieldArrayList(pDatos, 0, 6);//LTAVARA 15/01/16 PROYECTO M
                   
                    log.debug("VariablesTomaInv.vCodBarraIngresado :" + VariablesTomaInv.vCodBarraIngresado);
                    log.debug("VariablesTomaInv.vCodProducto :" + VariablesTomaInv.vCodProducto);
                    log.debug("VariablesTomaInv.vDescripcion :" + VariablesTomaInv.vDescripcion);
                    log.debug("VariablesTomaInv.vUnidadPresentacion :" + VariablesTomaInv.vUnidadPresentacion);
                    log.debug("VariablesTomaInv.vFraccionLocal :" + VariablesTomaInv.vFraccionLocal);
                    log.debug("VariablesTomaInv.vUnidadVenta :" + VariablesTomaInv.vUnidadVenta);
                    log.debug("VariablesTomaInv.vLaboratorio_Barra :" + VariablesTomaInv.vLaboratorio_Barra);

                    lblCodigoBarra.setText(VariablesTomaInv.vCodBarraIngresado);
                    lblDescProducto.setText(VariablesTomaInv.vCodProducto + " - " + VariablesTomaInv.vDescripcion +
                                            " - " + VariablesTomaInv.vUnidadPresentacion.trim());
                    lblUnidadValFrac.setText(VariablesTomaInv.vLaboratorio_Barra);
                    jLabel1.setText(VariablesTomaInv.vUnidadVenta.trim());
                    lblValFraccion.setText(VariablesTomaInv.vFraccionLocal.trim());

                    txtCantidad.setText("");
                    if (Integer.parseInt(VariablesTomaInv.vFraccionLocal.trim()) == 1) {
                        txtFraccion.setText("0");
                        txtFraccion.setEnabled(false);
                    } else {
                        txtFraccion.setText("");
                        txtFraccion.setEnabled(true);
                    }

                    //FarmaUtility.moveFocus(txtCantidad);
 
                    // LTAVARA 15/01/2016 valida si es un local mayorista
                    if(UtilityPtoVenta.isLocalVentaMayorista()){
                        //OBTENER LA CANTIDAD DE MASTER_PACK
                         try {
                             VariablesTomaInv.vValFracMasterPack = DBTomaInv.getValFracMasterPackProducto(VariablesTomaInv.vCodBarraIngresado, VariablesTomaInv.vCodProducto);
                         } catch (SQLException e) {
                             log.error("", e.getMessage());
                         }
                        if(VariablesTomaInv.vValFracMasterPack>0){
                         
                          txtMasterPack.setEnabled(true);
                          txtCantidad.setEnabled(false);
                          txtFraccion.setEnabled(false);
                          FarmaUtility.moveFocus(txtMasterPack);
                        }
                        if (VariablesTomaInv.vIndLoteMayorista.equals("S")){
                            txtLote.setEnabled(true);
                        }
                    }
                }

                /*}catch (SQLException sql) {

                 log.error("",sql);
                FarmaUtility.showMessage(this,"Ocurrió un error al obtener los datos del Producto.\n"+ sql.getMessage(),txtCantidad);
            }*/
            }
        } catch (SQLException sql) {

            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al obtener los datos del Producto.\n" +
                    sql.getMessage(), txtCantidad);
        }

    }

    private boolean isInteger(String pCadena) {
        int pNumero = 0;
        try {
            pNumero = Integer.parseInt(pCadena.trim());
            if (pNumero >= 0)
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        VariablesTomaInv.vIndActualizaProd = "N";
        this.setVisible(false);
        this.dispose();
    }

    private void ingresoCantidadFraccion(String pTipoIngreso, boolean pIndHablitadoFraccion) {
        String pCantidad, pFraccion,pLote;
        String pIndExisteLote="N";
        pCantidad = txtCantidad.getText().trim();
        pFraccion = txtFraccion.getText().trim();
        pLote=txtLote.getText().trim();
        String pMasterPack=txtMasterPack.getText().trim();
        Integer calculoMasterPack=0;
        

        if (pTipoIngreso.trim().equalsIgnoreCase("ENTERO") &&  
            VariablesTomaInv.vValFracMasterPack==0 //&&
            //VariablesTomaInv.vIndActualizaProd.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)
            ) {
            if (pCantidad.trim().length() <= 0 ) {
                FarmaUtility.showMessage(this, "Debe ingresar la cantidad.", txtCantidad);
                return;
            }
            if (!isInteger(pCantidad.trim()) ) {
                FarmaUtility.showMessage(this, "Cantidad ingresada es Incorrecta.\n¡Verifique!", txtCantidad);
                return;
            }


            if (!pIndHablitadoFraccion) {
                if (pCantidad.trim().equalsIgnoreCase("0") && pFraccion.trim().equalsIgnoreCase("0")) {
                    if (VariablesTomaInv.vIndActualizaProd.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                        boolean flagDel =
                            JConfirmDialog.rptaConfirmDialogDefaultNo(this, "Está seguro de eliminar el Producto.\n¡Verifique!");
                        if (flagDel) {
                            VariablesTomaInv.vIndEliminaProd = "S";
                           //cerrarVentana(false); // se comento por proyecto mayorista
                           //ltavara 02.02.16 Proyecto mayorista
                            cerrarVentana(true);
                            VariablesTomaInv.vCantEnteraIngresada = pCantidad.trim();
                            VariablesTomaInv.vCantFracIngresada = pFraccion.trim();
                            return;
                        } else
                            return;
                    } else {
                        FarmaUtility.showMessage(this,
                                                 "Ud. no puede ingresar un producto con Cantidad Entera y Fracción igual a Cero.",
                                                 txtCantidad);
                        return;
                    }
                }

                VariablesTomaInv.vCantEnteraIngresada = pCantidad.trim();
                VariablesTomaInv.vCantFracIngresada = pFraccion.trim();
                log.info("VariablesTomaInv.vCantEnteraIngresada:" + VariablesTomaInv.vCantEnteraIngresada);
                log.info("VariablesTomaInv.vCantFracIngresada:" + VariablesTomaInv.vCantFracIngresada);

                VariablesTomaInv.vIndIngresaProd = "S";
                log.info("vIndIngresaProd: " + VariablesTomaInv.vIndIngresaProd);
                if (!UtilityPtoVenta.isLocalVentaMayorista()) {
                cerrarVentana(true);
                }
            } else {
                FarmaUtility.moveFocus(txtFraccion);
            }
        } else if (pTipoIngreso.trim().equalsIgnoreCase("FRACCION") &&  
                   VariablesTomaInv.vValFracMasterPack==0 //&&
                   //  VariablesTomaInv.vIndActualizaProd.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N))
                         ) {
            if (pCantidad.trim().length() <= 0) {
                FarmaUtility.showMessage(this, "Debe ingresar la cantidad.", txtCantidad);
                return;
            }
            if (!isInteger(pCantidad.trim())) {
                FarmaUtility.showMessage(this, "Cantidad ingresada es Incorrecta.\n¡Verifique!", txtCantidad);
                return;
            }
            if (pCantidad.trim().equalsIgnoreCase("0") && pFraccion.trim().equalsIgnoreCase("0")) {
                if (VariablesTomaInv.vIndActualizaProd.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                    boolean flagDel =
                        JConfirmDialog.rptaConfirmDialogDefaultNo(this, "Está seguro de eliminar el Producto.\n¡Verifique!");
                    if (flagDel) {
                        VariablesTomaInv.vIndEliminaProd = "S";
                        //ltavara 02.02.16 Proyecto mayorista
                        cerrarVentana(true);
                        VariablesTomaInv.vCantEnteraIngresada = pCantidad.trim();
                        VariablesTomaInv.vCantFracIngresada = pFraccion.trim();
                        return;
                    } else
                        return;
                } else {
                    FarmaUtility.showMessage(this,
                                             "Ud. no puede ingresar un producto con Cantidad Entera y Fracción igual a Cero.",
                                             txtCantidad);
                    return;
                }
            }

            if (pFraccion.trim().length() <= 0) {
                FarmaUtility.showMessage(this, "Debe de ingresar cantidad en Fracción.", txtFraccion);
                return;
            }
            if (!isInteger(pFraccion.trim())) {
                FarmaUtility.showMessage(this, "Cantidad Fracción ingresada es Incorrecta.\n¡Verifique!", txtFraccion);
                return;
            } else if (pFraccion.trim().equalsIgnoreCase(VariablesTomaInv.vFraccionLocal)) {
                FarmaUtility.showMessage(this, "La cantidad de Fraccion no puede ser igual al Valor de Fraccion",
                                         txtFraccion);
                return;
            } else if (Integer.parseInt(pFraccion.trim()) > Integer.parseInt(VariablesTomaInv.vFraccionLocal)) {
                FarmaUtility.showMessage(this,
                                         "La cantidad de Fraccion ingresada no puede ser mayor al valor de Fraccion",
                                         txtFraccion);
                return;
            } 

            VariablesTomaInv.vCantEnteraIngresada = pCantidad.trim();
            VariablesTomaInv.vCantFracIngresada = pFraccion.trim();
            log.info("VariablesTomaInv.vCantEnteraIngresada:" + VariablesTomaInv.vCantEnteraIngresada);
            log.info("VariablesTomaInv.vCantFracIngresada:" + VariablesTomaInv.vCantFracIngresada);
            log.info("VariablesTomaInv.vLoteIngresado:" + VariablesTomaInv.vLoteIngresado);
            log.info("VariablesTomaInv.vFechaLoteIngresado:" + VariablesTomaInv.vFechaLoteIngresado);

            VariablesTomaInv.vIndIngresaProd = "S";
            log.info("vIndIngresaProd: " + VariablesTomaInv.vIndIngresaProd);
            if (!UtilityPtoVenta.isLocalVentaMayorista()) {
            cerrarVentana(true);
            }
        }
        

                //*********

        VariablesTomaInv.vCantEnteraIngresada = pCantidad.trim();
        VariablesTomaInv.vCantFracIngresada = pFraccion.trim();


        log.info("VariablesTomaInv.vCantEnteraIngresada:" + VariablesTomaInv.vCantEnteraIngresada);
        log.info("VariablesTomaInv.vCantFracIngresada:" + VariablesTomaInv.vCantFracIngresada);
        log.info("VariablesTomaInv.vLoteIngresado:" + VariablesTomaInv.vLoteIngresado);
        log.info("VariablesTomaInv.vFechaLoteIngresado:" + VariablesTomaInv.vFechaLoteIngresado);

        VariablesTomaInv.vIndIngresaProd = "S";
        log.info("vIndIngresaProd: " + VariablesTomaInv.vIndIngresaProd);
      /*  if (!UtilityPtoVenta.isLocalTipoVentaM()) {
        cerrarVentana(true);
        }*/
        //****
        
        //validar lote, fecha para local mayorista
        if (UtilityPtoVenta.isLocalVentaMayorista()) {
           //validar master pack cuando registra, no cuando modifica
            if (pMasterPack.trim().length() <= 0 && 
                VariablesTomaInv.vValFracMasterPack>0 &&
                VariablesTomaInv.vIndActualizaProd.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                FarmaUtility.showMessage(this, "Debe ingresar el Master pack.", txtMasterPack);
                return;    
            /*} else if(pMasterPack.trim().length() >0 && VariablesTomaInv.vValFracMasterPack>0){
                           FarmaUtility.moveFocus(txtLote);
                return;*/
            }  else if (pIndHablitadoFraccion 
                       && pFraccion.trim().length() <= 0 
                       &&VariablesTomaInv.vValFracMasterPack==0){
                FarmaUtility.moveFocus(txtFraccion);
                return;
            } else if (pLote.trim().length() <= 0 && VariablesTomaInv.vIndLoteMayorista.equals("S")) {
                FarmaUtility.showMessage(this, "Debe ingresar el lote.", txtLote);
                return;
            } else if (pLote.trim().length() >= 0 && VariablesTomaInv.vIndLoteMayorista.equals("S")) {
                try {
                    pIndExisteLote= DBTomaInv.getExisteLotePorProducto(VariablesTomaInv.vCodProducto,pLote);
                    log.info("existe lote:"+pIndExisteLote);
                    if (pIndExisteLote.equals("N")) {
                        FarmaUtility.showMessage(this, "El lote ingresado no existe", txtLote);
                        //KMONCADA 18.04.2016 SE CARGARA LA VENTANA DE REGISTRO DE LOTE SIN PRESIONAR LA TECLA F1
                        registrarLote();
                        if(!FarmaVariables.vAceptar){
                            return;
                        }else{
                            FarmaVariables.vAceptar = false;
                        }
                        //return;
                    }
                    VariablesTomaInv.vLoteIngresado=pLote;
                } catch (SQLException e) {
                    FarmaUtility.showMessage(this, "Ocurrio un error al validar el lote.", txtLote);
                    
                    log.info("error validar lote por producto" + e.getMessage());
            }
        }
            
            if(pMasterPack.trim().length() >0 && VariablesTomaInv.vValFracMasterPack>0){
                           calculoMasterPack=Integer.parseInt(pMasterPack) * VariablesTomaInv.vValFracMasterPack*Integer.parseInt(VariablesTomaInv.vFraccionLocal);
                           VariablesTomaInv.vCantEnteraIngresada =calculoMasterPack.toString();
                           FarmaUtility.moveFocus(txtLote);
            }
            
        cerrarVentana(true);
        }
    }

    /*private void ingresaFraccion(){
            if(pFraccion.trim().length()<=0){
                FarmaUtility.showMessage(this,"Debe de ingresar cantidad en Fracción.",txtFraccion);
               return;
            }

            if(!isInteger(pFraccion.trim())){
                FarmaUtility.showMessage(this,"Cantidad Fracción ingresada es Incorrecta.\n¡Verifique!",txtFraccion);
               return;
            }
    }*/

    private void limpiarVariables() {

    }

    private void txtFraccion_actionPerformed(ActionEvent e) {
     //  FarmaUtility.moveFocus(txtFraccion);
    }


    private void txtFraccion_keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            ingresoCantidadFraccion("FRACCION", txtFraccion.isEnabled());
        }
        chkKeyPressed(e);

    }
    private void btnMasterPack_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtMasterPack);
    }
    private void btnFraccion_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFraccion);
    }

    private void btnLote_actionPerformed(ActionEvent actionEvent) {
        FarmaUtility.moveFocus(txtLote);
    }
    private void txtLote_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            ingresoCantidadFraccion("Entero", txtFraccion.isEnabled());
        }
        chkKeyPressed(e);
    }

    private void txtFechaLote_keyPressed(KeyEvent keyEvent) {
    }
    private void btnFechaLote_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFechaLote);
    }


}
