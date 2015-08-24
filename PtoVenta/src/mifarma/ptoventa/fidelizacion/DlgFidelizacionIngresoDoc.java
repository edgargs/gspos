package mifarma.ptoventa.fidelizacion;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import farmapuntos.bean.Afiliado;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.fidelizacion.reference.DBFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.puntos.DlgVerificaDocRedencionBonifica;
import mifarma.ptoventa.puntos.reference.ConstantsPuntos;
import mifarma.ptoventa.puntos.reference.FacadeAfiliado;
import mifarma.ptoventa.puntos.reference.UtilityPuntos;
import mifarma.ptoventa.puntos.reference.VariablesPuntos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgFidelizacionIngresoDoc extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgFidelizacionIngresoDoc.class);
    JPopupMenu popup = new JPopupMenu();
    ActionListener menuListener;
    ArrayList listaTarjetas = new ArrayList();
    ArrayList listaFormasDePago;
    String pCodFPTarjeta = "", pNombreTarjeta = "";
    Frame myParentFrame;
    FarmaTableModel tableModel;
    private JPanelWhite pnlContent = new JPanelWhite();
    private JPanelHeader pnlBusqueda = new JPanelHeader();
    private JLabelWhite lblBuscar = new JLabelWhite();
    private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();
    private JTable tblLista = new JTable();
    private JScrollPane srcLista = new JScrollPane();
    private JLabelFunction lblEnter = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private final int COL_DNI = 0;
    private final int COL_NOM_CLI = 1;
    private final int COL_APE_PAT = 2;
    private final int COL_APE_MAT = 3;
    private final int COL_SEX_CLI = 4;
    private final int COL_FEC_NAC = 5;
    private final int COL_DIR_CLI = 6;
    private final int COL_TLF_CLI = 7;
    private final int COL_CEL_CLI = 8;
    private final int COL_EMAIL = 9;
    private ButtonGroup grupoBotones;
    private JRadioButton rbEfectivo = new JRadioButton();
    private JPanel jPanel1 = new JPanel();
    private JTextArea jtaMensaje = new JTextArea();
    private JLabel lblTipoTarjeta = new JLabel();
    
    boolean vIsPresionaTeclaF12 = false;
    
    public DlgFidelizacionIngresoDoc() {
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public DlgFidelizacionIngresoDoc(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }
    
    public DlgFidelizacionIngresoDoc(Frame parent, String title, boolean modal,boolean vTeclaF12) {
        super(parent, title, modal);
        myParentFrame = parent;
        vIsPresionaTeclaF12 =vTeclaF12;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }    

    private void jbInit() throws Exception {
        this.setSize(new Dimension(314, 123));
        this.setTitle("Ingresar Documento de Identidad");
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }

            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        pnlBusqueda.setBounds(new Rectangle(10, 10, 285, 50));
        pnlBusqueda.setFocusable(false);
        lblBuscar.setText("Ingrese DNI o CE");
        lblBuscar.setBounds(new Rectangle(5, 15, 90, 20));
        txtBuscar.setBounds(new Rectangle(105, 15, 155, 20));
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtBuscar_keyPressed(e);
            }

            public void keyTyped(KeyEvent e) {
                txtBuscar_keyTyped(e);
            }
        });
        //txtBuscar.setLengthText(20);
        txtBuscar.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                txtBuscar_focusLost(e);
            }
        });
        tblLista.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblLista_keyPressed(e);
            }
        });
        lblTipoTarjeta.setVisible(false);
        srcLista.setBounds(new Rectangle(10, 190, 285, 25));
        lblEnter.setBounds(new Rectangle(105, 70, 95, 20));
        lblEnter.setText("[Enter] Buscar");
        lblEnter.setFocusable(false);
        lblF11.setBounds(new Rectangle(5, 225, 95, 20));
        lblF11.setText("[ F11 ] Aceptar");
        lblEsc.setBounds(new Rectangle(205, 70, 90, 20));
        lblEsc.setText("[ Esc ] Salir");
        lblEsc.setFocusable(false);
        rbEfectivo.setText("Efectivo");
        rbEfectivo.setBounds(new Rectangle(190, 15, 85, 25));
        rbEfectivo.setFont(new Font("SansSerif", 0, 10));
        rbEfectivo.setBackground(Color.white);
        //popup.setBounds(new Rectangle(190, 50, 85, 25));
        jPanel1.setBounds(new Rectangle(10, 130, 285, 100));
        jPanel1.setLayout(null);
        jPanel1.setBackground(Color.white);
        jPanel1.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        jtaMensaje.setBounds(new Rectangle(5, 5, 180, 90));
        jtaMensaje.setText("Seleccione una forma de pago" + "\n" +
                "para ser uso de mayores" + "\n" +
                "descuentos.");
        jtaMensaje.setFont(new Font("SansSerif", 1, 11));
        jtaMensaje.setForeground(new Color(185, 48, 48));
        //lblTipoTarjeta.setBounds(new Rectangle(10, 10, 95, 25));
        lblTipoTarjeta.setBounds(new Rectangle(190, 70, 95, 25));
        lblTipoTarjeta.setFont(new Font("Serif", 1, 10));
        lblTipoTarjeta.setForeground(new Color(0, 154, 0));
        grupoBotones = new ButtonGroup();
        grupoBotones.add(rbEfectivo);

        pnlBusqueda.add(txtBuscar, null);
        pnlBusqueda.add(lblBuscar, null);
        //pnlBusqueda.add(lblTipoTarjeta, null);
        jPanel1.add(lblTipoTarjeta, null);

        jPanel1.add(jtaMensaje, null);
        jPanel1.add(rbEfectivo, null);
        //jPanel1.add(popup);
        //popup.setVisible(false);
        pnlContent.add(jPanel1, null);
        pnlContent.add(lblF11, null);
        pnlContent.add(pnlBusqueda, null);
        srcLista.getViewport().add(tblLista, null);
        pnlContent.add(srcLista, null);
        pnlContent.add(lblEsc, null);
        pnlContent.add(lblEnter, null);
        this.getContentPane().add(pnlContent, null);
    }

    private void initialize() {
        initTable();

    }

    private void initTable() {
        
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
    }

    public void cerrarVentana(boolean vResp) {
        FarmaVariables.vAceptar = vResp;
        this.setVisible(vResp);
        this.dispose();
    }

    private void txtBuscar_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            funcionVkEnter();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }
    
    private void funcionVkEnter(){
        log.info("funcionVkEnter");
        String dni = txtBuscar.getText().trim();
        if (dni.length() == 8) {
            DlgVerificaDocRedencionBonifica dlgVerificaDocRedencionBonifica = new DlgVerificaDocRedencionBonifica(myParentFrame, 
                                                                                                                  "", 
                                                                                                                  true, 
                                                                                                                  "MOD");
            dlgVerificaDocRedencionBonifica.setIsRequiereTarjeta(false);
            dlgVerificaDocRedencionBonifica.setIsRequiereDni(true);
            dlgVerificaDocRedencionBonifica.setNroDocumento(dni);
            dlgVerificaDocRedencionBonifica.setMnto(true);
            dlgVerificaDocRedencionBonifica.setVisible(true);
            if (FarmaVariables.vAceptar) { 
                procesarAfiliado(dni);    
            }
        } else if (dni.length() == 9) {
            procesarAfiliado(dni);
        } else {
            FarmaUtility.showMessage(this, "Solo se permite ingresar DNI o CE", txtBuscar);
        }
    }
    
    private void procesarAfiliado(String dni) {
        try{
            Afiliado afiliado = null;
            VariablesFidelizacion.isFoundOrbis = false;
            if (UtilityPuntos.isActivoFuncionalidad()) {
                // paso 1
                afiliado = VariablesPuntos.frmPuntos.obtenerDatosAfiliadoSinTarjeta(dni, FarmaVariables.vUsuarioBD);
                //if(FarmaPuntosConstante.EXITO.equalsIgnoreCase(VariablesPuntos.frmPuntos.getTarjetaBean().getEstadoOperacion())){
                if(afiliado != null && afiliado.getDni().trim().length()!=0){
                        log.info("PROGRAMA DE PUNTOS [CONSULTA DE AFILIADO] CONSULTA CON EXITO\n"+
                                                "NOMBRE.AFILIADO : "+afiliado.getNombre()+"\n"+
                                                "APE.PAT.AFILIADO : "+afiliado.getApParterno()+"\n"+
                                                "APE.MAT.AFILIADO : "+afiliado.getApMarterno()+"\n"+
                                                "NRO DOCUMENTO : "+afiliado.getDni());
                        //VariablesFidelizacion.vNumTarjeta = pNroTarjeta;
                        //VariablesFidelizacion.vIndEstado = "A";
                        DBFidelizacion.insertarClienteFidelizacion(ConstantsPuntos.IND_PROCESO_ORBIS, 
                                                                    ConstantsPuntos.TRSX_ORBIS_ENVIADA, 
                                                                     afiliado);    
                        VariablesFidelizacion.isFoundOrbis = true;
                }else{
                            log.info("PROGRAMA DE PUNTOS [CONSULTA DE AFILIADO] CONSULTA DE AFILIADO ERROR");
                }
            }            
            // paso 2
            FacadeAfiliado facadeAfiliado = new FacadeAfiliado();
            afiliado = facadeAfiliado.obtenerClienteFidelizado(dni);
            
            if (afiliado == null) {
                FarmaUtility.showMessage(this, 
                                         "El documento " + dni + " no está afiliado", 
                                         null); 
                cerrarVentana(false);
            } else {            
                DlgFidelizacionClientesMnto dlgFidelizacionClientesMnto = new DlgFidelizacionClientesMnto(myParentFrame, "", true);
                dlgFidelizacionClientesMnto.setAfiliado(afiliado);
                dlgFidelizacionClientesMnto.setVisible(true);
                if (FarmaVariables.vAceptar) {
                    cerrarVentana(FarmaVariables.vAceptar);
                }                
            }
            
        } catch (Exception e) {
            log.error("",e);
        }
    }

    private void tblLista_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtBuscar_keyTyped(KeyEvent e) {
        FarmaUtility.admitirSoloDigitos(e, txtBuscar, 0, this);
    }

    private void txtBuscar_focusLost(FocusEvent e) {
        txtBuscar.grabFocus();
    }

    public void setVIsPresionaTeclaF12(boolean vIsPresionaTeclaF12) {
        this.vIsPresionaTeclaF12 = vIsPresionaTeclaF12;
    }

    public boolean isVIsPresionaTeclaF12() {
        return vIsPresionaTeclaF12;
    }

    /**
     * Carga las formas de pago para asi ampliar la pantalla
     */
    public boolean isNumero(String pCadena) {
        int n;
        try {
            n = Integer.parseInt(pCadena.trim());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    
    

}
