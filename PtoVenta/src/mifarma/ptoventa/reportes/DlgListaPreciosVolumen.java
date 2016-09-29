package mifarma.ptoventa.reportes;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConnection;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.main.DlgProcesar;
import mifarma.ptoventa.puntos.reference.UtilityPuntos;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import static mifarma.ptoventa.reference.UtilityPtoVenta.limpiaCadenaAlfanumerica;
import mifarma.ptoventa.reportes.reference.ConstantsReporte;
import mifarma.ptoventa.reportes.reference.DBReportes;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2016 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper  11.1.2.4.0g<br>
 * Nombre de la Aplicación : DlgListaPreciosVolumen.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * Rafael Bullon      26.01.2016   Creación
 * <br>
 * @author Rafael Bullon <br>
 * @version 1.0<br>
 *
 */

public class DlgListaPreciosVolumen extends JDialog {
    
    private static final Logger log = LoggerFactory.getLogger(DlgListaPreciosVolumen.class);
    private Frame myParentFrame;
    private JTable myJTable;
    private FarmaTableModel tableModelListaPrecioProductoVolumen;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();



    private JSeparator jSeparator2 = new JSeparator();
    private JLabel lblDescLab_Alter = new JLabel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JPanel jPanel1 = new JPanel();
    
    private JSeparator jSeparator1 = new JSeparator();
    private JPanel pnlIngresarProductos = new JPanel();
    private JTextField txtProducto = new JTextField();
    private JButton btnProducto = new JButton();
    private JTable tblProductos = new JTable();
    
 
    private JLabelFunction lblEsc = new JLabelFunction();

    private JPanel jPanel5 = new JPanel();

    private JLabelWhite lblUnidFracLoc = new JLabelWhite();
    private JPanelWhite jPanelWhite1 = new JPanelWhite();

    private JPanelHeader jPanelHeader1 = new JPanelHeader();


    private boolean vEjecutaAccionTeclaListado = false;

    private JButtonLabel lblMensajeCodBarra = new JButtonLabel();

    private boolean pasoTarjeta = false;

    private int contarCombinacion = 0; 

    private boolean isLectoraLazer, isCodigoBarra, isEnter;
    private long tiempoTeclaInicial ,tiempoTeclaFinal,OldtmpT2;
   

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListaPreciosVolumen() {
        this(null, "", false);
    }

    public DlgListaPreciosVolumen(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(735, 518));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Lista de Precios por Volumen");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setForeground(Color.white);
        this.setBackground(Color.white);
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
        jSeparator2.setBounds(new Rectangle(200, 0, 15, 20));
        jSeparator2.setBackground(Color.black);
        jSeparator2.setOrientation(SwingConstants.VERTICAL);
        lblDescLab_Alter.setBounds(new Rectangle(225, 0, 375, 20));
        lblDescLab_Alter.setFont(new Font("SansSerif", 1, 11));
        lblDescLab_Alter.setForeground(Color.white);
        jScrollPane1.setBounds(new Rectangle(15, 55, 700, 380));
        jScrollPane1.setBackground(new Color(255, 130, 14));
        jPanel1.setBounds(new Rectangle(15, 35, 700, 20));
        jPanel1.setBackground(new Color(255, 130, 14));
        jPanel1.setLayout(null);

        jSeparator1.setBounds(new Rectangle(150, 0, 15, 20));
        jSeparator1.setBackground(Color.black);
        jSeparator1.setOrientation(SwingConstants.VERTICAL);
        pnlIngresarProductos.setBounds(new Rectangle(15, 5, 700, 30));
        pnlIngresarProductos.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnlIngresarProductos.setBackground(new Color(43, 141, 39));
        pnlIngresarProductos.setLayout(null);
        pnlIngresarProductos.setForeground(Color.orange);
        txtProducto.setBounds(new Rectangle(100, 5, 460, 20));
        txtProducto.setFont(new Font("SansSerif", 1, 11));
        txtProducto.setForeground(new Color(32, 105, 29));
        txtProducto.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtProducto_keyPressed(e);

            }

            public void keyReleased(KeyEvent e) {
                txtProducto_keyReleased(e);
            }


        });
        txtProducto.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                txtProducto_focusLost(e);
            }
        });
        btnProducto.setText("Producto");
        btnProducto.setBounds(new Rectangle(25, 5, 60, 20));
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

        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(565, 455, 150, 20));


        jPanel5.setBounds(new Rectangle(15, 435, 700, 20));
        jPanel5.setBackground(new Color(255, 130, 14));
        jPanel5.setLayout(null);

        lblUnidFracLoc.setBounds(new Rectangle(120, 0, 90, 20));
        lblUnidFracLoc.setForeground(new Color(43, 141, 39));
        jPanelWhite1.setBounds(new Rectangle(225, 25, 225, 20));
        jPanelWhite1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        jPanelHeader1.setBounds(new Rectangle(0, 0, 410, 30));
  
        lblMensajeCodBarra.setText("");
        lblMensajeCodBarra.setBounds(new Rectangle(285, 0, 160, 20));
        lblMensajeCodBarra.setFont(new Font("SansSerif", 1, 12));
        jPanelWhite1.add(lblUnidFracLoc, null);
        jScrollPane1.getViewport();
        pnlIngresarProductos.add(txtProducto, null);
        pnlIngresarProductos.add(btnProducto, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);


        jContentPane.add(jPanel5, null);
        jContentPane.add(lblEsc, null);
        jScrollPane1.getViewport().add(tblProductos, null);
        jContentPane.add(jScrollPane1, null);
        jPanel1.add(jSeparator1, null);
        jContentPane.add(jPanel1, null);
        jContentPane.add(pnlIngresarProductos, null);


    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        initTableListaPreciosProductos();
        
        setJTable(tblProductos);

        VariablesVentas.vPosNew = 0;
        VariablesVentas.vPosOld = 0;

        if (!FarmaVariables.vAceptar) {

            FarmaVariables.vAceptar = true;
        }
   
        FarmaVariables.vAceptar = false;
  
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTableListaPreciosProductos() {

        tableModelListaPrecioProductoVolumen =
                new FarmaTableModel(ConstantsReporte.columnsListaPrecProdVolumen, ConstantsReporte.defaultValuesListaProdPrecVolumen,
                                    0);
      
        obtenerListaPrecioVolumen();
        
        FarmaUtility.initSimpleList(tblProductos, tableModelListaPrecioProductoVolumen,
                                    ConstantsReporte.columnsListaPrecProdVolumen);
        tblProductos.setName(ConstantsVentas.NAME_TABLA_PRODUCTOS);

      /*  if (tableModelListaPrecioProductoVolumen.getRowCount() > 0)
            FarmaUtility.ordenar(tblProductos, tableModelListaPrecioProductoVolumen, 1,//COL_ORD_LISTA,
                                 FarmaConstants.ORDEN_ASCENDENTE);*/

    }

    public void iniciaProceso(boolean pInicializar) {

        for (int i = 0; i < tblProductos.getRowCount(); i++)
            tblProductos.setValueAt(new Boolean(false), i, 0);

        for (int i = 0; i < VariablesVentas.tableModelListaGlobalProductos.getRowCount(); i++)
            VariablesVentas.tableModelListaGlobalProductos.setValueAt(new Boolean(false), i, 0);

        if (pInicializar) {
            VariablesVentas.vArrayList_PedidoVenta = new ArrayList();
            for (int i = 0; i < VariablesVentas.vArrayList_ResumenPedido.size(); i++)
                VariablesVentas.vArrayList_PedidoVenta.add((ArrayList)VariablesVentas.vArrayList_ResumenPedido.get(i));
        }
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {

        VariablesVentas.vValorMultiplicacion = "1"; 

        FarmaConnection.closeConnection();
        DlgProcesar.setVersion();

        FarmaUtility.centrarVentana(this);
        vEjecutaAccionTeclaListado = false;
        VariablesVentas.vVentanaListadoProductos = true;

        FarmaUtility.moveFocus(txtProducto);

        if (VariablesVentas.vArrayList_PedidoVenta.size() == 0)
            VariablesVentas.vIndPedConProdVirtual = false;

        if (VariablesVentas.vKeyPress != null) {
            if (VariablesVentas.vCodBarra.trim().length() > 0) {
                txtProducto.setText(VariablesVentas.vCodBarra.trim());
                txtProducto_keyPressed(VariablesVentas.vKeyPress);
            } else if (VariablesVentas.vCodProdBusq.trim().length() > 0) {
                txtProducto.setText(VariablesVentas.vCodProdBusq.trim());
                txtProducto_keyPressed(VariablesVentas.vKeyPress);
            } else {
                txtProducto.setText(VariablesVentas.vKeyPress.getKeyChar() + "");
                txtProducto_keyReleased(VariablesVentas.vKeyPress);
            }
        }

        
    }


    private void txtProducto_keyPressed(KeyEvent e) {
        if (!(e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_ESCAPE ||
              e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT ||
              e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_HOME)) {
            e.consume();

        }
        
        if(isNumero(e.getKeyChar()) || e.getKeyCode() == KeyEvent.VK_ENTER ){
            if (isLectoraLazer) {
                isLectoraLazer = false;
            }
            isEnter = false;
            isLectoraLazer = false;
            if (tiempoTeclaInicial == 0){
                tiempoTeclaInicial = System.currentTimeMillis();
            }
            isCodigoBarra = true;
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                tiempoTeclaFinal = System.currentTimeMillis();
                
                if (!UtilityPuntos.isActivoFuncionalidad()) {
                    isLectoraLazer = false;
                    tiempoTeclaInicial = 0;
                    log.info("FUNCIONALIDAD DE PUNTOS NO ACTIVA");
                    isCodigoBarra = false;
                    txtProducto_keyPressed2(e);
                }else{
                    int maxTiempoLectora = UtilityPuntos.obtieneTiempoMaximoLectora();
                    isLectoraLazer = false;
                    
                    log.info("FUNCIONALIDAD DE PUNTOS ACTIVA");
                    
                    log.info("Tiem 2 " + (tiempoTeclaInicial));
                    log.info("Tiem 1 " + (tiempoTeclaFinal));
                    log.info("Tiempo de ingreso y ENTER " + (tiempoTeclaFinal - tiempoTeclaInicial));
                   
                    limpiaCadenaAlfanumerica(txtProducto);
                    boolean validaFinal = true;
                    for(int k=0;k<txtProducto.getText().toCharArray().length;k++){
                        validaFinal = validaFinal && isNumero(txtProducto.getText().toCharArray()[k]);
                    }
                    if ((tiempoTeclaFinal - tiempoTeclaInicial) <= maxTiempoLectora && txtProducto.getText().length() > 0 && validaFinal) {
                        isLectoraLazer = true;
                        tiempoTeclaInicial = 0;
                        log.info("ES CODIGO DE BARRA");
                        isCodigoBarra = true;
                        isEnter = true;
                    }                  
                }
            }
        }else{
            isCodigoBarra = false;
            log.info("FLUJO NORMAL");
            txtProducto_keyPressed2(e);
        }
    }
    
    

    private void txtProducto_keyPressed2(KeyEvent e) {
        if (!(e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_ESCAPE ||
              e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT ||
              e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_HOME)) {
            e.consume();
        }

        try {
            FarmaGridUtils.aceptarTeclaPresionada(e, myJTable, txtProducto, 1); //2           
            if (!vEjecutaAccionTeclaListado) {
                vEjecutaAccionTeclaListado = true;
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                
                    String vCadenaOriginal = txtProducto.getText().trim();
                    log.debug("!!!!!!!!!!!!!Cadena Original:" + vCadenaOriginal);
               
                    limpiaCadenaAlfanumerica(txtProducto);

                    isEnter = true;
                } else {
                    
                    vEjecutaAccionTeclaListado = false;
                    chkKeyPressed(e);

                    if (e.getKeyCode() == 18 && contarCombinacion == 0) {
                        contarCombinacion++;
                    }
  
                }
            }
        } catch (Exception exc) {
            log.error("", exc);
            log.debug("catch" + vEjecutaAccionTeclaListado);
        } finally {
            vEjecutaAccionTeclaListado = false;
        }

    }

    private boolean isNumero(char ca) {
        int numero  = 0;
        try {
            numero = Integer.parseInt(ca + "");
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private void txtProducto_keyReleased(KeyEvent e) {
    

            if (tblProductos.getRowCount() >= 0 && tableModelListaPrecioProductoVolumen.getRowCount() > 0 &&
                e.getKeyChar() != '+') {
                if (FarmaGridUtils.buscarDescripcion(e, myJTable, txtProducto, 0)||
                    (FarmaGridUtils.buscarDescripcion(e, myJTable, txtProducto, 1))||               
                    (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_PAGE_UP) ||
                    (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) ||
                    e.getKeyCode() == KeyEvent.VK_ENTER) {
                    VariablesVentas.vPosNew = tblProductos.getSelectedRow();
                    if (VariablesVentas.vPosOld == 0 && VariablesVentas.vPosNew == 0) {
                      
                        VariablesVentas.vPosOld = VariablesVentas.vPosNew;
                    } else {
                        if (VariablesVentas.vPosOld != VariablesVentas.vPosNew) {
                          
                            VariablesVentas.vPosOld = VariablesVentas.vPosNew;
                        }
                    }
                }
            }

    

        contarCombinacion = 0; 

        }  
   

    private void btnProducto_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtProducto);
    }



    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }


    private void chkKeyPressed(KeyEvent e) {
        try {
            if (!vEjecutaAccionTeclaListado) {
                vEjecutaAccionTeclaListado = true;
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                } else if (UtilityPtoVenta.verificaVK_F1(e)) {
                    vEjecutaAccionTeclaListado = false;
                    
                } else if (UtilityPtoVenta.verificaVK_F2(e)) {                    
                    VariablesVentas.vCantMesRimac = 0;
                    VariablesVentas.vDniRimac = "";
     

                    
                }  else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                  
                    cancelaOperacion();
               
                } else if (e.getKeyCode() == KeyEvent.VK_INSERT) { 
                    VariablesVentas.vIndPrecioCabeCliente = "S";
                    
                   
                    if (FarmaVariables.vAceptar) {
                        cancelaOperacion();
                        cerrarVentana(true);
                    }
                } 

            }

        } //try
        catch (Exception exc) {
            log.debug("catch" + vEjecutaAccionTeclaListado);
        } finally {
            vEjecutaAccionTeclaListado = false;
            log.debug(" finally: " + vEjecutaAccionTeclaListado);

        }

    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        VariablesVentas.vVentanaListadoProductos = false;
        VariablesVentas.vIndDireccionarResumenPed = pAceptar;
        VariablesVentas.vKeyPress = null;
        this.setVisible(false);
        this.dispose();
    }


    private void setJTable(JTable pJTable) {
        myJTable = pJTable;
        txtProducto.setText("");
        if (pJTable.getRowCount() > 0) {
            FarmaGridUtils.showCell(pJTable, 0, 0);
            FarmaUtility.setearActualRegistro(pJTable, txtProducto, 1);
        }
        FarmaUtility.moveFocus(txtProducto);
    }



    private void cancelaOperacion() {
        cerrarVentana(false);
    }

    private void obtenerListaPrecioVolumen(){        
            try {
                tableModelListaPrecioProductoVolumen.clearTable();
                tableModelListaPrecioProductoVolumen.fireTableDataChanged();
               DBReportes.cargaListaProdPrecioVolumen(tableModelListaPrecioProductoVolumen);
            } catch (SQLException sqlException) {
                log.error(null, sqlException);
                FarmaUtility.showMessage(this, "Error al obtener Lista de Productos Filtrado!!!", txtProducto);
            }        
        
        }

    public boolean isNumerico(String pCadena) {
        int numero = 0;
        boolean pRes = false;
        try {
            for (int i = 0; i < pCadena.length(); i++) {
                numero = Integer.parseInt(pCadena.charAt(i) + "");
                pRes = true;
            }
        } catch (NumberFormatException e) {
            pRes = false;
        }
        return pRes;
    }


    private void txtProducto_focusLost(FocusEvent e) {
        FarmaUtility.moveFocus(txtProducto);
    }

}
