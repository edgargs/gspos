package mifarma.ptoventa.puntos;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import farmapuntos.bean.TarjetaBean;

import farmapuntos.util.FarmaPuntosConstante;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.convenioBTLMF.reference.DBConvenio;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenio;
import mifarma.ptoventa.convenioBTLMF.reference.UtilityConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.hilos.SubProcesosConvenios;
import mifarma.ptoventa.puntos.reference.ConstantsPuntos;
import mifarma.ptoventa.puntos.reference.UtilityPuntos;
import mifarma.ptoventa.puntos.reference.VariablesPuntos;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import static mifarma.ptoventa.reference.UtilityPtoVenta.limpiaCadenaAlfanumerica;
import static mifarma.ptoventa.reference.UtilityPtoVenta.limpiaCadenaAlfanumerica;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2015 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgBonificados.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * KMONCADA       19.03.2015   Creacion <br>
 * <br>
 * @author  Kenny Moncada<br>
 * @version 1.0<br>
 * VENTANA PARA RELACION DE PRODUCTOS BONIFICADOS Y PROMOCION DEL PROGRAMA DE PUNTOS
 *
 */
public class DlgBonificados extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgBonificados.class);

    private Frame myParentFrame;

    private FarmaTableModel tbModelLstBonificado;

    private String indProdCong = "";

    private final int COL_SELEC = 0;
    private final int COL_COD = 1;
    private final int COL_DESC = 2;
    private final int COL_IND_PROMO = 13;
    private final int COL_LAB = 4;
    private final int COL_CANT = 10;
    private final int COL_PROD_CONG = 6;
    private final int COL_PROD_HAB = 7;
    private final int COL_CANT_DISP = 5;
    private final int COL_MENSJ = 14;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction lblF5 = new JLabelFunction();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JPanel jPanel1 = new JPanel();
    private JLabel lblDescLab_Prod = new JLabel();
    private JSeparator jSeparator1 = new JSeparator();
    private JPanel pnlIngresarProductos = new JPanel();
    private JButton btnBuscar = new JButton();
    private JTextField txtProducto = new JTextField();
    private JButton btnProducto = new JButton();
    private JTable tblProductos = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();

    private JButtonLabel btnRelacionProductos = new JButtonLabel();
    private JPanel jPanel5 = new JPanel();
    private JLabelWhite lblMensaje = new JLabelWhite();
    private JLabelWhite lblMensajeEscaneo = new JLabelWhite();
    private JPanel pnlAtencion2 = new JPanel();
    private XYLayout xYLayout8 = new XYLayout();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    
    private ArrayList lstProductoBonificados = new ArrayList();
    private String textProducto = "";
    private boolean isLLevaPromociones = false;
    private XYLayout xYLayout1 = new XYLayout();

    /* ************************************************************************** */
    /*                              CONSTRUCTORES                                 */
    /* ************************************************************************** */

    public DlgBonificados() {
        this(null, "", false);
    }

    public DlgBonificados(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /* ************************************************************************** */
    /*                              METODO jbInit                                 */
    /* ************************************************************************** */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(668, 344));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Lista de Productos Bonificados");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jContentPane.setBackground(Color.white);
        jContentPane.setLayout(null);
        jContentPane.setSize(new Dimension(623, 439));
        jContentPane.setForeground(Color.white);
        lblF1.setText("[ F1 ] / + Continuar");
        lblF1.setBounds(new Rectangle(365, 290, 150, 20));
        
        lblF5.setText("[ F5 ] Deseleccionar Todos");
        lblF5.setBounds(new Rectangle(175, 290, 180, 20));
        
        jScrollPane1.setBounds(new Rectangle(10, 85, 645, 180));
        jScrollPane1.setBackground(new Color(255, 130, 14));
        jPanel1.setBounds(new Rectangle(10, 65, 645, 20));
        jPanel1.setBackground(new Color(255, 130, 14));
        jPanel1.setLayout(null);
        lblDescLab_Prod.setBounds(new Rectangle(160, 0, 345, 20));
        lblDescLab_Prod.setFont(new Font("SansSerif", 1, 11));
        lblDescLab_Prod.setForeground(Color.white);
        jSeparator1.setBounds(new Rectangle(150, 0, 15, 20));
        jSeparator1.setBackground(Color.black);
        jSeparator1.setOrientation(SwingConstants.VERTICAL);
        pnlIngresarProductos.setBounds(new Rectangle(10, 30, 645, 30));
        pnlIngresarProductos.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlIngresarProductos.setBackground(new Color(43, 141, 39));
        pnlIngresarProductos.setLayout(null);
        pnlIngresarProductos.setForeground(Color.orange);
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(585, 5, 90, 20));
        btnBuscar.setBackground(SystemColor.control);
        btnBuscar.setMnemonic('b');
        btnBuscar.setDefaultCapable(false);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setRequestFocusEnabled(false);
        btnBuscar.setFont(new Font("SansSerif", 1, 12));
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBuscar_actionPerformed(e);
            }
        });
        txtProducto.setBounds(new Rectangle(90, 5, 460, 20));
        txtProducto.setFont(new Font("SansSerif", 1, 15));
        txtProducto.setForeground(new Color(32, 105, 29));
        txtProducto.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtProducto_keyPressed(e);

            }

            public void keyReleased(KeyEvent e) {
                txtProducto_keyReleased(e);
            }

            public void keyTyped(KeyEvent e) {
                txtProducto_keyTyped(e);
            }
        });
        btnProducto.setText("Producto");
        btnProducto.setBounds(new Rectangle(10, 5, 60, 20));
        btnProducto.setMnemonic('p');
        btnProducto.setFont(new Font("SansSerif", 1, 11));
        btnProducto.setDefaultCapable(false);
        btnProducto.setRequestFocusEnabled(false);
        btnProducto.setBackground(new Color(50, 162, 65));
        btnProducto.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnProducto.setFocusPainted(false);
        btnProducto.setHorizontalAlignment(SwingConstants.LEFT);
        btnProducto.setContentAreaFilled(false);
        btnProducto.setBorderPainted(false);
        btnProducto.setForeground(Color.white);
        btnProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnProducto_actionPerformed(e);
            }
        });
        tblProductos.setFont(new Font("SansSerif", 0, 12));
        lblEsc.setText("[ ESC ] Volver Atrás");
        lblEsc.setBounds(new Rectangle(525, 290, 130, 20));
        btnRelacionProductos.setText("Relación de Productos");
        btnRelacionProductos.setBounds(new Rectangle(10, 0, 140, 20));
        jPanel5.setBounds(new Rectangle(10, 265, 645, 20));
        jPanel5.setBackground(new Color(255, 130, 14));
        jPanel5.setLayout(xYLayout1);
        lblMensaje.setFont(new Font("SansSerif", 1, 20));
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensaje.setForeground(Color.red);
        
        lblMensajeEscaneo.setFont(new Font("SansSerif", 1, 12));
        lblMensajeEscaneo.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensajeEscaneo.setForeground(Color.WHITE);
        lblMensajeEscaneo.setText("DEBE ESCANEAR EL CODIGO DE BARRA DEL PRODUCTO PARA SELECCIONARLO");
        
        pnlAtencion2.setFont(new Font("SansSerif", 0, 11));
        pnlAtencion2.setLayout(xYLayout8);
        pnlAtencion2.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlAtencion2.setBackground(new Color(255, 254, 254));
        pnlAtencion2.setBounds(new Rectangle(10, 0, 645, 25));
        //JPanel4.setBounds(new Rectangle(5, 45, 705, 65));
        //lblStockAdic.setSize(new Dimension(40, 15));
        jScrollPane1.getViewport();
        jPanel1.add(btnRelacionProductos, null);
        jPanel1.add(lblDescLab_Prod, null);
        jPanel1.add(jSeparator1, null);
        pnlIngresarProductos.add(btnBuscar, null);
        pnlIngresarProductos.add(txtProducto, null);
        pnlIngresarProductos.add(btnProducto, null);
        jPanel5.add(lblMensajeEscaneo, new XYConstraints(0, 0, 645, 20));
        jContentPane.add(jPanel5, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(lblF5, null);
        jScrollPane1.getViewport().add(tblProductos, null);
        jContentPane.add(jScrollPane1, null);
        jContentPane.add(jPanel1, null);
        jContentPane.add(pnlIngresarProductos, null);
        pnlAtencion2.add(lblMensaje, new XYConstraints(-1, -1, 610, 25));
        jContentPane.add(pnlAtencion2, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        //this.getContentPane().add(jContentPane, null);
        txtProducto.setEditable(false);
    }

    /* ************************************************************************** */
    /*                            METODO initialize                               */
    /* ************************************************************************** */

    private void initialize() {
        initTableListaComplementarios();
        btnBuscar.setVisible(false);
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************** */
    /*                          METODOS INICIALIZACION                            */
    /* ************************************************************************** */
    private void initTableListaComplementarios() {
        tbModelLstBonificado = new FarmaTableModel(ConstantsPuntos.columnsListaProductosBonificados, ConstantsPuntos.defaultValuesListaProductosBonificados, 0);
        FarmaUtility.initSelectList(tblProductos, tbModelLstBonificado, ConstantsPuntos.columnsListaProductosBonificados);
    }

    /* ************************************************************************** */
    /*                            METODOS DE EVENTOS                              */
    /* ************************************************************************** */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtProducto);
        tblProductos.getSelectionModel().setSelectionInterval(0,0);
        if (tblProductos.getRowCount() == 0) {
            cerrarVentana(false);
        }
        validarPromociones();
    }
    
    private void validarPromociones(){
        for(int i=0;i<tblProductos.getRowCount();i++){
            String indPromocion = FarmaUtility.getValueFieldArrayList(tbModelLstBonificado.data, i, COL_IND_PROMO);
            if("S".equalsIgnoreCase(indPromocion)){
                ((ArrayList)tbModelLstBonificado.data.get(i)).set(COL_SELEC, true);
                isLLevaPromociones = true;
            }
        }
        tblProductos.repaint();
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    // **************************************************************************
    // Marco Fajardo: Cambio realizado el 21/04/09 - evitar le ejecucion de 2 teclas a la vez
    // **************************************************************************
    
    private boolean isLectoraLaser;
    private boolean isEnter;
    private boolean isCodigoBarra;
    private long tiempoTeclaInicial;
    private long tiempoTeclaFinal;
    
    private boolean isNumero(char ca) {
        int numero  = 0;
        try {
            numero = Integer.parseInt(ca + "");
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
    
    private void txtProducto_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblProductos, txtProducto, COL_DESC);
        if(e.getKeyCode() != KeyEvent.VK_ENTER){
            textProducto = textProducto + e.getKeyChar();
        }
        if(isNumero(e.getKeyChar()) || e.getKeyCode() == KeyEvent.VK_ENTER ){
            if (isLectoraLaser) {
                isLectoraLaser = false;
            }
            isEnter = false;
            isLectoraLaser = false;
            if (tiempoTeclaInicial == 0){
                tiempoTeclaInicial = System.currentTimeMillis();
            }
            isCodigoBarra = true;
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                int maxTiempoLectora = UtilityPuntos.obtieneTiempoMaximoLectora();
                isLectoraLaser = false;
                tiempoTeclaFinal = System.currentTimeMillis();
                log.info("Tiem 2 " + (tiempoTeclaInicial));
                log.info("Tiem 1 " + (tiempoTeclaFinal));
                log.info("Tiempo de ingreso y ENTER " + (tiempoTeclaFinal - tiempoTeclaInicial));
                boolean validaFinal = true;
                for(int k=0;k<textProducto.length();k++){
                    validaFinal = validaFinal && isNumero(textProducto.toCharArray()[k]);
                }
                if ((tiempoTeclaFinal - tiempoTeclaInicial) <= maxTiempoLectora && textProducto.length() > 0 && validaFinal) {
                    isLectoraLaser = true;
                    tiempoTeclaInicial = 0;
                    log.info("ES CODIGO DE BARRA");
                    isCodigoBarra = true;
                    isEnter = true;
                    txtProducto.setText(textProducto);
                    buscarProducto(e);
                        //txtProducto_keyPressed2(e);
                    
                    
                } else {
                    isLectoraLaser = false;
                    tiempoTeclaInicial = 0;
                    log.info("FLUJO NORMAL1");
                    isCodigoBarra = false;
                    chkKeyPressed(e);
                    textProducto = "";
                    //txtProducto_keyPressed2(e);
                }
                //FarmaUtility.moveFocus(txtTarjeta);
            }
        }else{
            isCodigoBarra = false;
            log.info("FLUJO NORMAL2");
            //txtProducto_keyPressed2(e);
            chkKeyPressed(e);
            textProducto = "";
            if(tblProductos.getRowCount()==1){
                FarmaUtility.setearTextoDeBusqueda(tblProductos, txtProducto, COL_DESC);
            }
        }
    }
    
    
    private void buscarProducto(KeyEvent e){
        limpiaCadenaAlfanumerica(txtProducto);
        textProducto = txtProducto.getText().trim();
        try {
            e.consume();
            if (tblProductos.getSelectedRow() >= 0) {
                String productoBuscar = textProducto;//txtProducto.getText().trim().toUpperCase();

                if (productoBuscar.length() == 0)
                    return;
                String codigo = "";
                // revisando codigo de barra
                char primerkeyChar = productoBuscar.charAt(0);
                char segundokeyChar;
                if (productoBuscar.length() > 1)
                    segundokeyChar = productoBuscar.charAt(1);
                else
                    segundokeyChar = primerkeyChar;
                char ultimokeyChar = productoBuscar.charAt(productoBuscar.length() - 1);
                if (productoBuscar.length() > 6 &&
                    (!Character.isLetter(primerkeyChar) && (!Character.isLetter(segundokeyChar) &&
                                                            (!Character.isLetter(ultimokeyChar))))) {
                    VariablesVentas.vCodigoBarra = productoBuscar;
                    productoBuscar = DBVentas.obtieneCodigoProductoBarra();
                }
                if (productoBuscar.equalsIgnoreCase("000000")) {
                    FarmaUtility.showMessage(this,
                                             "No existe producto relacionado con el Codigo de Barra. Verifique!!!",
                                             txtProducto);
                    
                    return;
                }
                for (int k = 0; k < tblProductos.getRowCount(); k++) {
                    String indPromocion = FarmaUtility.getValueFieldArrayList(tbModelLstBonificado.data, k, COL_IND_PROMO);
                    codigo = ((String)tblProductos.getValueAt(k, COL_COD)).trim();
                    // KMONCADA 08.04.2015 VALIDA QUE NO SE CONSIDERE LOS PRODUCTOS EN PROMOCION
                    if (codigo.equalsIgnoreCase(productoBuscar) && !"S".equalsIgnoreCase(indPromocion)) {
                        FarmaGridUtils.showCell(tblProductos, k, 0);
                        break;
                    }
                }
                String actualCodigo = ((String)(tblProductos.getValueAt(tblProductos.getSelectedRow(), COL_COD))).trim();
                String actualProducto = ((String)(tblProductos.getValueAt(tblProductos.getSelectedRow(), COL_DESC))).trim().toUpperCase();
                // Asumimos que codigo de producto ni codigo de barra empiezan con letra
                if (Character.isLetter(primerkeyChar)) {
                    txtProducto.setText(actualProducto);
                    productoBuscar = actualProducto;
                }
                txtProducto.setText(txtProducto.getText().trim());
                // Comparando codigo y descripcion de la fila actual con el txtProducto
                if ((actualCodigo.equalsIgnoreCase(productoBuscar) ||
                     actualProducto.substring(0, productoBuscar.length()).equalsIgnoreCase(productoBuscar))) {
                    btnBuscar.doClick();
                    txtProducto.setText(actualProducto.trim());
                    txtProducto.selectAll();
                } else {
                    FarmaUtility.showMessage(this, "Producto No Encontrado según Criterio de Búsqueda !!!",
                                             txtProducto);
                    txtProducto.setText("");
                    textProducto = "";
                    return;
                }
                textProducto = "";
                txtProducto.setText("");
            }
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al buscar el Producto.\n" +
                    sql, txtProducto);
            textProducto = "";
            txtProducto.setText("");
        }
    }
    
    private void txtProducto_keyReleased(KeyEvent e) {

    }

    private void txtProducto_keyTyped(KeyEvent e) {
        
    }

    private void btnProducto_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtProducto);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        verificaCheckJTable();
    }

    /* ************************************************************************** */
    /*                       METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************** */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
        } else if (UtilityPtoVenta.verificaVK_F1(e) || e.getKeyChar() == '+') {
            cerrarVentana(true);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            isLLevaPromociones = false;
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            desactivarProductos();
        }else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP){
            mostrarMensaje();
        }
    }
    
    private void desactivarProductos(){
        for(int i=0;i<tblProductos.getRowCount();i++){
            String indPromocion = FarmaUtility.getValueFieldArrayList(tbModelLstBonificado.data, i, COL_IND_PROMO);
            if(!"S".equalsIgnoreCase(indPromocion)){
                ((ArrayList)tbModelLstBonificado.data.get(i)).set(COL_SELEC, false);
                ((ArrayList)tbModelLstBonificado.data.get(i)).set(COL_CANT, "0");
            }
        }
        tblProductos.repaint();
    }
    
    private void mostrarMensaje(){
        int rowSelect = tblProductos.getSelectedRow();
        if(rowSelect >= 0){
            String mensaje = FarmaUtility.getValueFieldArrayList(tbModelLstBonificado.data, rowSelect, COL_MENSJ);
            lblMensaje.setText(mensaje);
        }else{
            lblMensaje.setText("");
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************** */
    /*                       METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************** */

    

    private void muestraIngresoCantidad() {
        int vFila = tblProductos.getSelectedRow();
        DlgIngresoCantidadBonificado dlgIngresoBonificado = new DlgIngresoCantidadBonificado(myParentFrame, "", true);
        dlgIngresoBonificado.setCodProducto(FarmaUtility.getValueFieldJTable(tblProductos, vFila, COL_COD));
        dlgIngresoBonificado.setCantidadInicial(FarmaUtility.getValueFieldJTable(tblProductos, vFila, COL_CANT_DISP));
        dlgIngresoBonificado.setDescripcionProducto(FarmaUtility.getValueFieldJTable(tblProductos, vFila, COL_DESC));
        dlgIngresoBonificado.setNombreLaboratorio(FarmaUtility.getValueFieldJTable(tblProductos, vFila, COL_LAB));
        VariablesVentas.vIngresaCant_ResumenPed = false;
        dlgIngresoBonificado.setVisible(true);

        if (FarmaVariables.vAceptar) {
            String cantidadIngresada =  dlgIngresoBonificado.getCantidadIngresada();
            ((ArrayList)tbModelLstBonificado.data.get(vFila)).set(COL_SELEC, true);
            ((ArrayList)tbModelLstBonificado.data.get(vFila)).set(COL_CANT, cantidadIngresada);
            tblProductos.repaint();
        }

    }
    
    
    
    private void verificaCheckJTable() {
        int vFila = tblProductos.getSelectedRow();
        Boolean valor = (Boolean)(tblProductos.getValueAt(vFila, 0));
        String indPromocion = FarmaUtility.getValueFieldArrayList(tbModelLstBonificado.data, vFila, COL_IND_PROMO);
        if("S".equalsIgnoreCase(indPromocion)){
            FarmaUtility.showMessage(this, "Es un producto de promoción", txtProducto);
            return;
        }
        if (valor.booleanValue()) {
            ((ArrayList)tbModelLstBonificado.data.get(vFila)).set(COL_SELEC, false);
            ((ArrayList)tbModelLstBonificado.data.get(vFila)).set(COL_CANT, "0");
            tblProductos.repaint();
        } else {
            indProdCong = FarmaUtility.getValueFieldArrayList(tbModelLstBonificado.data, vFila, COL_PROD_CONG);
            VariablesVentas.vInd_Prod_Habil_Vta = FarmaUtility.getValueFieldArrayList(tbModelLstBonificado.data, vFila, COL_PROD_HAB);
            int cantidadDisponible = (int)Integer.parseInt(FarmaUtility.getValueFieldArrayList(tbModelLstBonificado.data, vFila, COL_CANT_DISP));
            
            if(cantidadDisponible < 1){
                //FarmaUtility.showMessage(this, "Fraccion del producto no aplica para este local", txtProducto);
                return;
            }
            if (!UtilityVentas.validaProductoTomaInventario(indProdCong)) {
                FarmaUtility.showMessage(this, "El Producto se encuentra Congelado por Toma de Inventario", txtProducto);
                return;
            }
            if (!UtilityVentas.validaProductoHabilVenta()) {
                FarmaUtility.showMessage(this, "El Producto NO se encuentra hábil para la venta. Verifique!!!",
                                         txtProducto);
                return;
            }
            
            VariablesVentas.vIndProdControlStock = true;
            VariablesVentas.vIndPedConProdVirtual = false;
            muestraIngresoCantidad();
            
        }

    }

   
    public void setLstProductoBonificados(ArrayList lstProductoBonificados){
        this.lstProductoBonificados = lstProductoBonificados;
        for(int i=0;i<this.lstProductoBonificados.size();i++){
            ArrayList aux = (ArrayList)this.lstProductoBonificados.get(i);
            aux.set(COL_SELEC, false);
        }
        tbModelLstBonificado.data = this.lstProductoBonificados;
    }
    
    public FarmaTableModel getTbModelLstBonificado(){
        return this.tbModelLstBonificado;
    }

    public boolean isIsLLevaPromociones() {
        return isLLevaPromociones;
    }
}
