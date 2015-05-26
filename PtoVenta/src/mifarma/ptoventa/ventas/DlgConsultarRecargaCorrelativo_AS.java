package mifarma.ptoventa.ventas;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
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
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import mifarma.common.FarmaConnectionRemoto;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.FacadeCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.caja.reference.UtilityRecargaVirtual;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.caja.reference.VariablesVirtual;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgConsultarRecargaCorrelativo_AS extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgConsultarRecargaCorrelativo_AS.class);
    Frame myParentFrame;
    private boolean vAceptar = false;

    private JPanelWhite pnlFondo = new JPanelWhite();
    private JPanelHeader pnlHeader = new JPanelHeader();
    private JPanelTitle jPanel4 = new JPanelTitle();
    private JPanel pnlContenido = new JPanel();
    private JButtonLabel btnCorrelativo = new JButtonLabel();
    private JTextFieldSanSerif txtCorrelativo = new JTextFieldSanSerif();
    private JButtonLabel lblMonto = new JButtonLabel();
    private JTextFieldSanSerif txtMonto = new JTextFieldSanSerif();
    private JButton btnBuscar = new JButton();
    private JEditorPane jEditorPane1 = new JEditorPane();
    private JLabel lblFechaAct = new JLabel();
    private JLabel lblFechaActV = new JLabel();
    private JLabel lblMontoR = new JLabel();
    private JLabel lblMontoR2 = new JLabel();
    private JLabel lblTelefono = new JLabel();
    private JLabel lblTelefono2 = new JLabel();
    private JLabel lblProveedor = new JLabel();
    private JLabel lblProveedor2 = new JLabel();
    private JLabel lblValorCo = new JLabel();
    private JLabel lblFecha = new JLabel();
    private JLabel lblCorrelativo = new JLabel();
    private JLabel lblValorFec = new JLabel();
    private JLabelFunction lblImprimir = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    //JMIRANDA 25.08.2011 Fijar Objeto para Focus
    private Object pObj = txtCorrelativo;

    //GFonseca 14/08/2013
    private FacadeCaja facadeCaja = new FacadeCaja();
    private CardLayout cardLayout1 = new CardLayout();


    public DlgConsultarRecargaCorrelativo_AS() {
        this(null, "", false);
    }

    public DlgConsultarRecargaCorrelativo_AS(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(556, 422));
        this.getContentPane().setLayout(cardLayout1);
        this.setBounds(new Rectangle(10, 10, 556, 422));
        //this.setContentPane(pnlFondo);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlHeader.setBounds(new Rectangle(10, 10, 530, 35));
        jPanel4.setBounds(new Rectangle(10, 55, 530, 20));
        jPanel4.setFocusable(false);
        btnCorrelativo.setText("Correlativo :");
        btnCorrelativo.setBounds(new Rectangle(10, 10, 70, 15));
        btnCorrelativo.setMnemonic('c');
        btnCorrelativo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCorrelativo_actionPerformed(e);
            }
        });
        btnCorrelativo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnCorrelativo_keyPressed(e);
            }
        });
        txtCorrelativo.setBounds(new Rectangle(85, 5, 115, 25));
        txtCorrelativo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCorrelativo_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtCorrelativo_keyTyped(e);
            }
        });
        lblMonto.setText("Monto :");
        lblMonto.setBounds(new Rectangle(240, 10, 45, 15));
        lblMonto.setMnemonic('m');
        txtMonto.setBounds(new Rectangle(295, 5, 80, 25));
        txtMonto.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtMonto_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtMonto_keyTyped(e);
            }
        });
        txtMonto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtMonto_actionPerformed(e);
            }
        });
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(405, 5, 90, 25));
        btnBuscar.setMnemonic('s');
        btnBuscar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnBuscar_keyPressed(e);
            }
        });
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        jEditorPane1.setBounds(new Rectangle(10, 175, 510, 105));
        jEditorPane1.setText("___");
        jEditorPane1.setSelectedTextColor(Color.red);
        jEditorPane1.setFocusable(false);
        jEditorPane1.setFont(new Font("SansSerif", 1, 12));
        jEditorPane1.setForeground(Color.white);
        jEditorPane1.setBackground(new Color(43, 141, 39));
        jEditorPane1.setMargin(new Insets(10, 10, 2, 1));
        lblFechaAct.setBounds(new Rectangle(135, 140, 95, 15));
        lblFechaAct.setFont(new Font("Dialog", 1, 13));
        lblFechaAct.setForeground(new Color(0, 132, 66));
        lblFechaAct.setMaximumSize(new Dimension(0, 0));
        lblFechaAct.setMinimumSize(new Dimension(0, 0));
        lblFechaAct.setPreferredSize(new Dimension(0, 0));
        lblFechaAct.setText("Fecha Actual:");
        lblFechaAct.setFocusable(false);
        lblFechaActV.setBounds(new Rectangle(260, 140, 165, 15));
        lblFechaActV.setBackground(new Color(0, 132, 66));
        lblFechaActV.setForeground(new Color(0, 132, 66));
        lblFechaActV.setFont(new Font("Dialog", 1, 13));
        lblFechaActV.setMaximumSize(new Dimension(0, 0));
        lblFechaActV.setMinimumSize(new Dimension(0, 0));
        lblFechaActV.setPreferredSize(new Dimension(0, 0));
        lblFechaActV.setText("___");
        lblFechaActV.setFocusable(false);
        lblMontoR.setBounds(new Rectangle(135, 110, 90, 15));
        lblMontoR.setFont(new Font("Dialog", 1, 13));
        lblMontoR.setForeground(new Color(0, 132, 66));
        lblMontoR.setMaximumSize(new Dimension(0, 0));
        lblMontoR.setMinimumSize(new Dimension(0, 0));
        lblMontoR.setPreferredSize(new Dimension(0, 0));
        lblMontoR.setText("Monto S/.:");
        lblMontoR.setFocusable(false);
        lblMontoR2.setBounds(new Rectangle(260, 110, 120, 15));
        lblMontoR2.setBackground(Color.white);
        lblMontoR2.setFont(new Font("Dialog", 1, 13));
        lblMontoR2.setForeground(new Color(0, 132, 66));
        lblMontoR2.setMaximumSize(new Dimension(0, 0));
        lblMontoR2.setMinimumSize(new Dimension(0, 0));
        lblMontoR2.setPreferredSize(new Dimension(0, 0));
        lblMontoR2.setText("___");
        lblMontoR2.setFocusable(false);
        lblTelefono.setBounds(new Rectangle(135, 80, 90, 15));
        lblTelefono.setFont(new Font("Dialog", 1, 13));
        lblTelefono.setForeground(new Color(0, 132, 66));
        lblTelefono.setMaximumSize(new Dimension(0, 0));
        lblTelefono.setMinimumSize(new Dimension(0, 0));
        lblTelefono.setPreferredSize(new Dimension(0, 0));
        lblTelefono.setText("Teléfono:");
        lblTelefono.setFocusable(false);
        lblTelefono2.setBounds(new Rectangle(260, 80, 210, 15));
        lblTelefono2.setFont(new Font("Dialog", 1, 13));
        lblTelefono2.setForeground(new Color(0, 132, 66));
        lblTelefono2.setMaximumSize(new Dimension(0, 0));
        lblTelefono2.setMinimumSize(new Dimension(0, 0));
        lblTelefono2.setPreferredSize(new Dimension(0, 0));
        lblTelefono2.setText("___");
        lblTelefono2.setFocusable(false);
        lblProveedor.setBounds(new Rectangle(135, 50, 90, 15));
        lblProveedor.setFont(new Font("Dialog", 1, 13));
        lblProveedor.setForeground(new Color(0, 132, 66));
        lblProveedor.setMaximumSize(new Dimension(0, 0));
        lblProveedor.setMinimumSize(new Dimension(0, 0));
        lblProveedor.setPreferredSize(new Dimension(0, 0));
        lblProveedor.setText("Proveedor:");
        lblProveedor.setFocusable(false);
        lblProveedor2.setBounds(new Rectangle(260, 50, 160, 15));
        lblProveedor2.setFont(new Font("Dialog", 1, 13));
        lblProveedor2.setForeground(new Color(0, 132, 66));
        lblProveedor2.setMaximumSize(new Dimension(0, 0));
        lblProveedor2.setMinimumSize(new Dimension(0, 0));
        lblProveedor2.setPreferredSize(new Dimension(0, 0));
        lblProveedor2.setText("___");
        lblProveedor2.setFocusable(false);
        lblValorCo.setBounds(new Rectangle(105, 15, 130, 15));
        lblValorCo.setFont(new Font("Dialog", 1, 13));
        lblValorCo.setForeground(new Color(0, 132, 66));
        lblValorCo.setMaximumSize(new Dimension(0, 0));
        lblValorCo.setMinimumSize(new Dimension(0, 0));
        lblValorCo.setPreferredSize(new Dimension(0, 0));
        lblValorCo.setText("___");
        lblValorCo.setFocusable(false);
        lblFecha.setBounds(new Rectangle(265, 15, 85, 15));
        lblFecha.setFont(new Font("Dialog", 1, 13));
        lblFecha.setForeground(new Color(0, 132, 66));
        lblFecha.setMaximumSize(new Dimension(0, 0));
        lblFecha.setMinimumSize(new Dimension(0, 0));
        lblFecha.setPreferredSize(new Dimension(0, 0));
        lblFecha.setText("Fecha Ped.:");
        lblFecha.setFocusable(false);
        lblCorrelativo.setBounds(new Rectangle(20, 15, 90, 15));
        lblCorrelativo.setFont(new Font("Dialog", 1, 13));
        lblCorrelativo.setForeground(new Color(0, 132, 66));
        lblCorrelativo.setMaximumSize(new Dimension(0, 0));
        lblCorrelativo.setMinimumSize(new Dimension(0, 0));
        lblCorrelativo.setPreferredSize(new Dimension(0, 0));
        lblCorrelativo.setText("Correlativo:");
        lblCorrelativo.setFocusable(false);
        lblValorFec.setBounds(new Rectangle(355, 15, 160, 15));
        lblValorFec.setFont(new Font("Dialog", 1, 13));
        lblValorFec.setForeground(new Color(0, 132, 66));
        lblValorFec.setMaximumSize(new Dimension(0, 0));
        lblValorFec.setMinimumSize(new Dimension(0, 0));
        lblValorFec.setPreferredSize(new Dimension(0, 0));
        lblValorFec.setText("___");
        lblValorFec.setFocusable(false);
        lblImprimir.setBounds(new Rectangle(320, 370, 105, 20));
        lblImprimir.setText("[ F10 ] Imprimir");
        lblEsc.setBounds(new Rectangle(435, 370, 90, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        pnlContenido.setBounds(new Rectangle(10, 75, 530, 290));
        pnlContenido.setLayout(null);
        pnlContenido.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        pnlContenido.setBackground(Color.white);
        pnlContenido.add(lblCorrelativo, null);
        pnlContenido.add(lblValorCo, null);
        pnlContenido.add(lblProveedor, null);
        pnlContenido.add(lblTelefono, null);
        pnlContenido.add(lblMontoR, null);
        pnlContenido.add(lblFechaAct, null);
        pnlContenido.add(lblProveedor2, null);
        pnlContenido.add(lblTelefono2, null);
        pnlContenido.add(lblMontoR2, null);
        pnlContenido.add(lblFechaActV, null);
        pnlContenido.add(lblFecha, null);
        pnlContenido.add(lblValorFec, null);
        pnlContenido.add(jEditorPane1, null);
        pnlFondo.add(pnlContenido, null);
        pnlFondo.add(lblEsc, null);
        pnlFondo.add(lblImprimir, null);
        pnlFondo.add(jPanel4, null);
        pnlFondo.add(pnlHeader, null);
        pnlHeader.add(btnBuscar, null);
        pnlHeader.add(txtMonto, null);
        pnlHeader.add(lblMonto, null);
        pnlHeader.add(txtCorrelativo, null);
        pnlHeader.add(btnCorrelativo, null);
        this.getContentPane().add(pnlFondo, "pnlFondo");
        txtCorrelativo.setDocument(new FarmaLengthText(10));
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        LimpiarVariables();
        setearObjetoFocus();
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */


    private void btnCorrelativo_actionPerformed(ActionEvent e) {
        if (validarMostrarCorrelativo())
            FarmaUtility.moveFocus(txtCorrelativo);
        else
            FarmaUtility.moveFocus(btnCorrelativo);
    }

    private void txtCorrelativo_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            txtCorrelativo.setText(FarmaUtility.caracterIzquierda(txtCorrelativo.getText(), 10, "0"));
            FarmaUtility.moveFocus(txtMonto);
        } else
            chkKeyPressed(e);
    }

    private void txtCorrelativo_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtCorrelativo, e);
    }

    private void txtMonto_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            //FarmaUtility.moveFocus(btnBuscar);
            btnBuscar.doClick();
        } else
            chkKeyPressed(e);
    }

    private void txtMonto_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitosDecimales(txtMonto, e);
    }

    private void txtMonto_actionPerformed(ActionEvent e) {
        //////////////
        //ejecutarBusqueda();
    }

    private void btnBuscar_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (validarMostrarCorrelativo())
                FarmaUtility.moveFocus(txtCorrelativo);
        } else
            chkKeyPressed(e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtCorrelativo);
        jEditorPane1.setEditable(false);

        if (this.isVisible() && !validarMostrarCorrelativo()) {
            mostrarCorrelativoXComprobante();
            FarmaUtility.moveFocus(btnCorrelativo);
        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", pObj);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (UtilityPtoVenta.verificaVK_F11(e)) {
            if (vAceptar) {

            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (UtilityPtoVenta.verificaVK_F10(e)) {
            //imprimir ticket
            imprimirTicket();
            //cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private boolean validarCampos() {
        boolean retorno = true;
        if (txtCorrelativo.getText().trim().equals("")) {
            FarmaUtility.showMessage(this, "Debe ingresar el Correlativo.", txtCorrelativo);
            retorno = false;
        } else if (txtMonto.getText().trim().equals("")) {
            FarmaUtility.showMessage(this, "Debe ingresar el Monto.", txtMonto);
            retorno = false;
        } else if (!FarmaUtility.validateDecimal(this, txtMonto, "Debe ingresar un Monto Válido.", false)) {
            retorno = false;
        }
        return retorno;
    }

    //GFonseca 14/08/2013. Se agregó la logica para verificar la recarga por el six.

    private void buscarRecarga() throws SQLException {
        ArrayList infoRecarga = new ArrayList();
        infoRecarga = DBVentas.verificaRecargaPedido(txtCorrelativo.getText().trim(), txtMonto.getText().trim());
        String mensaje = "", proveedor = "", telefono = "", monto = "", descripcion = "", fecha = "", fecha_actual =
            "";

        if (infoRecarga.size() > 0) { // si hay pedido
            for (int i = 0; i < infoRecarga.size(); i++) {
                log.info("INFORtamaño-----" + i + ": " + infoRecarga.size());
                log.info("INFORecarga-----" + i + ": " + infoRecarga.get(i));
                proveedor = ((String)((ArrayList)infoRecarga.get(i)).get(0)).trim();
                descripcion = ((String)((ArrayList)infoRecarga.get(i)).get(1)).trim();
                telefono = ((String)((ArrayList)infoRecarga.get(i)).get(2)).trim();
                monto = ((String)((ArrayList)infoRecarga.get(i)).get(3)).trim();
                fecha = ((String)((ArrayList)infoRecarga.get(i)).get(4)).trim();
                fecha_actual = ((String)((ArrayList)infoRecarga.get(i)).get(5)).trim();
            }

            lblProveedor2.setText(proveedor);
            lblTelefono2.setText(telefono);
            lblMontoR2.setText(monto);
            lblValorCo.setText(txtCorrelativo.getText().trim());
            lblValorFec.setText(fecha);
            lblFechaActV.setText(fecha_actual);

            if (VariablesVirtual.vConProductoVirtual) { //hay producto virtual
                log.info("VariablesCaja.vIndLineaADMCentral : " + VariablesCaja.vIndLineaADMCentral);
                log.debug("VariablesCaja.vIndLineaADMCentral : " + VariablesCaja.vIndLineaADMCentral);

                //Validando conexion con ADM
                //if (VariablesCaja.vIndLineaADMCentral.equals(FarmaConstants.INDICADOR_S))
                if ((new UtilityRecargaVirtual()).validarConexionRecarga()) {
                    //CARGANDO PARAMETROS
                    VariablesCaja.vNumeroCelular = telefono.trim();
                    VariablesCaja.vNumPedVta_Anul = txtCorrelativo.getText().trim();

                    //LLEIVA 26-Mar-2014 Se añadio el try-catch
                    try {
                        //VariablesCaja.vRespuestaExito = obtieneRespuestaRecarga().trim();
                        VariablesCaja.vRespuestaExito =
                                (new UtilityRecargaVirtual()).obtieneMensajeRecarga(txtCorrelativo.getText().trim(),
                                                                                    fecha);
                    } catch (Exception ex) {
                        log.debug("", ex);
                        VariablesCaja.vRespuestaExito = FarmaConstants.INDICADOR_N;
                    }

                    log.info("VariablesCaja.vRespuestaExito :" + VariablesCaja.vRespuestaExito);
                    //VALIDANDO RESPUESTA SI EL NUMERO TELEFONICO EXISTE EN ADMCENTRAL

                    //LLEIVA 26-Mar-2014 Si la respuesta es "N" o nulo no existe el dato en servidor central
                    if (VariablesCaja.vRespuestaExito == null ||
                        FarmaConstants.INDICADOR_N.equalsIgnoreCase(VariablesCaja.vRespuestaExito.trim())) {
                        //LimpiarVariables();
                        log.info("No se pudo obtener la respuesta de la Recarga");

                        jEditorPane1.setText("No se pudo obtener la respuesta de la Recarga");
                        VariablesCaja.vRespRecargaPmtros = false;
                    } else {
                        //LLEIVA 26-Mar-2014 Si retorna algun otro valor se muestra en la pantalla
                        /*******************************************************************************************************************/
                        //if (VariablesCaja.vRespuestaExito.trim().equals("-2"))
                        //{
                        //    //LimpiarVariables();
                        //    VariablesCaja.vRespRecargaPmtros = false;
                        //}
                        //else if(VariablesCaja.vRespuestaExito.trim().equals("00"))
                        //{
                        //    VariablesCaja.vRespRecargaPmtros = true;
                        //}
                        //else if(VariablesCaja.vRespuestaExito.trim().equals("0") ||
                        //        VariablesCaja.vRespuestaExito.trim().equals("-1"))
                        //{
                        //    //LimpiarVariables();
                        //    VariablesCaja.vRespRecargaPmtros = false;
                        //}
                        //else
                        //{
                        //    VariablesCaja.vRespRecargaPmtros = true;
                        //}

                        jEditorPane1.setText(VariablesCaja.vRespuestaExito);
                    }

                    /******************************************************************************************************************/
                    /*                    String indlabel = "";
                    String msg = "";
                    try
                    {
                        //INI ASOSA, 19.04.2010
                        indlabel = DBVentas.getIND_LABEL(VariablesCaja.vRespuestaExito);
                        msg = DBVentas.getMensajeRptaRecarga(VariablesCaja.vRespuestaExito);
                    }
                    catch (Exception ex)
                    {   log.debug("", ex);
                        indlabel = "N";
                    }

                    if(indlabel.equalsIgnoreCase("S"))
                    {
                        jEditorPane1.setContentType("text/html");
                        jEditorPane1.setText(msg);
                        double cantLineas=((double)obtenerLargoSoloMsg(msg))/64;
                        int cantLineasInt=(int)cantLineas+1;
                        log.debug("length msg: "+msg.length());
                        log.debug("CANTIDAD DE LINEAS float: "+cantLineas);
                        log.debug("CANTIDAD DE LINEAS int: "+cantLineasInt);
                        int alto=24*cantLineasInt;
                        pnlFondo.setSize(555,600);
                        jEditorPane1.setBounds(new Rectangle(20, 235, 510, alto));

                        if(cantLineasInt>1)
                        {
                            int cantidadMas=cantLineasInt-1;
                            int cantidadX=cantidadMas*20;

                            lblImprimir.setBounds(new Rectangle(320, 275+cantidadX, 105, 20));
                            lblEsc.setBounds(new Rectangle(435, 275+cantidadX, 90, 20));

                            pnlFondo.setSize(555,310+cantidadX);
                            this.setSize(new Dimension(556, 330+cantidadX));
                            pnlFondo.repaint();
                        }
                    }
                    else if(indlabel.equalsIgnoreCase("N"))
                    {
                        FarmaUtility.showMessage(this,msg.replaceAll("ººº","\n"),pObj);
                    }
                        //FIN ASOSA, 19.04.2010
                    log.debug(" indExitoRecarga : " +
                    VariablesCaja.vRespuestaExito);
                    VariablesCaja.vIndPedidoRecargaVirtual = "";
*/
                }
                //No hay conexion com ADMCENTRAL
                else {
                    FarmaUtility.showMessage(this,
                                             "No puede realizar la consulta debido a dificultades con la conexión a Matriz",
                            //txtCorrelativo);
                            pObj);
                    LimpiarVariables();
                    VariablesCaja.vRespRecargaPmtros = false;
                }
                //                }
            }
            //fin hay producto virtual
        }
        // fin si hay pedido
        else {
            FarmaUtility.showMessage(this, "No existe el Pedido.Verifique!", pObj);
            //LimpiarVariables();
            VariablesCaja.vRespRecargaPmtros = false;
        }
    }

    private int obtenerLargoSoloMsg(String msg) {
        int ini = msg.indexOf("<font face=Arial color=red>") + "<font face=Arial color=red>".length();
        int fin = msg.indexOf("</font>");
        String neomsg = msg.substring(ini, fin);
        log.debug("MENSAJE: " + msg.substring(ini, fin));
        return neomsg.length();
    }


    private void evaluaPedidoProdVirtual(String pNumPedido) {
        int cantProdVirtualesPed = 0;
        cantProdVirtualesPed = cantidadProductosVirtualesPedido(pNumPedido);
        if (cantProdVirtualesPed <= 0) {
            VariablesVirtual.vConProductoVirtual = false;
        } else {
            VariablesVirtual.vConProductoVirtual = true;
        }
        log.info("asolis: VariablesVirtual.vConProductoVirtual :" + VariablesVirtual.vConProductoVirtual);
    }

    private int cantidadProductosVirtualesPedido(String pNumPedido) {
        int cant = 0;
        try {
            cant = DBCaja.obtieneCantProdVirtualesPedido(pNumPedido);
        } catch (SQLException ex) {
            log.error("", ex);
            log.error("", ex);
            FarmaUtility.showMessage(this, "Error al obtener cantidad de productos virtuales.\n" +
                    ex.getMessage(), pObj);
        }
        return cant;
    }

    private void LimpiarVariables() {
        //lblProveedor.setText("");
        lblProveedor2.setText("");
        //lblTelefono.setText("");
        lblTelefono2.setText("");
        //lblMontoR.setText("");
        lblMontoR2.setText("");
        //lblCorrelativo.setText("");
        lblValorCo.setText("");
        //lblFecha.setText("");
        lblValorFec.setText("");
        //lblFechaAct.setText("");
        lblFechaActV.setText("");
        jEditorPane1.setText("");
    }

    private void imprimirTicket() {
        //if (VariablesCaja.vRespRecargaPmtros == true)
        //{
        int monto = Integer.parseInt(lblMontoR2.getText());
        String nroVentaPedido = lblValorCo.getText();
        UtilityCaja.imprimeTicket(this, nroVentaPedido, monto);
        //}
        //else
        //    FarmaUtility.showMessage(this,
        //                            "No existen datos para realizar la impresión",
        //                            pObj);
    }

    /**
     * Valida si se muestra la nueva versión para Imprimir o
     * no Imprimir Correlativo, así como usar pantalla para Ingresar Numero Comprobante y Monto Neto si es Negativo
     * @author JMIRANDA
     * @since 22.08.2011
     * @return true si imprime correlativo
     */
    private boolean validarMostrarCorrelativo() {
        boolean flag = true;
        //si la validacion siguiente es falsa no imprime y debe ingresar nro comprobante
        if (!UtilityVentas.getIndImprimeCorrelativo()) {
            txtCorrelativo.setEnabled(false);
            txtMonto.setEnabled(false);
            flag = false;
        }
        return flag;
    }

    private void mostrarCorrelativoXComprobante() {
        DlgConsultaXCorrelativo dlgConsulta = new DlgConsultaXCorrelativo(myParentFrame, "", true);
        dlgConsulta.setVisible(true);

        if (FarmaVariables.vAceptar) {
            txtCorrelativo.setText(VariablesVentas.vNumPedVta_new);
            txtMonto.setText(VariablesVentas.vMontoNeto_new);
            btnBuscar.doClick();
        } else
            FarmaUtility.moveFocus(btnCorrelativo);
    }

    private void btnCorrelativo_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        ejecutarBusqueda();
    }

    private void ejecutarBusqueda() {
        FarmaConnectionRemoto.closeConnection(); //JCHAVEZ 28092009 para asegurar el cierre de conexion a adm_central
        if (validarCampos()) {
            //Agregado por DVELIZ 05.01.2009
            VariablesCaja.vIndLineaADMCentral = "S"; //indicador de linea en N
            evaluaPedidoProdVirtual(txtCorrelativo.getText().trim()); //verifica si es un pedido virtual
            if (VariablesVirtual.vConProductoVirtual) {
                //validarConexionADMCentral();//VariablesCaja.vIndLineaADMCentral
                log.debug("asolis: ");
            } else {
                FarmaUtility.showMessage(this, "Este pedido no corresponde a un producto virtual",
                                         pObj); //JCHAVEZ 28092009
            }
            log.debug("asolis: antes de buscar pedido VariablesCaja.vIndLineaADMCentral:" +
                      VariablesCaja.vIndLineaADMCentral);
            try {
                this.setVisible(false);
                buscarRecarga();
                FarmaUtility.moveFocus(btnCorrelativo);
                this.setVisible(true);
            } catch (SQLException ex) {
                log.error("", ex);
                FarmaUtility.showMessage(this, "Ocurrió un error al buscar el pedido  - \n" +
                        ex.getMessage(), pObj);
            } catch (Exception x) {
                log.error("", x);
                FarmaUtility.showMessage(this, "Ocurrió un error al buscar el pedido  - \n" +
                        x.getMessage(), pObj);
            }
        } else {
            LimpiarVariables();
        }
    }

    //JMIRANDA 25.08.2011 Setear el Objeto para enfocar después de los mensajes.

    private void setearObjetoFocus() {
        //JMIRANDA 25.08.2011 verificar si se utiliza funcionalidad nueva
        if (validarMostrarCorrelativo())
            pObj = txtCorrelativo;
        else
            pObj = btnCorrelativo;
    }
}
