package mifarma.ptoventa.recetario;


import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.recetario.reference.FacadeRecetario;
import mifarma.ptoventa.recetario.reference.VariablesRecetario;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgListaGuiaProductoVirtualRM extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaGuiaProductoVirtualRM.class);

    private Frame myParentFrame;
    private FacadeRecetario facadeRecetario = new FacadeRecetario();
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel pnl_buscarProducto = new JPanel();
    private JPanelHeader pnl_guia = new JPanelHeader();

    private JScrollPane jScrollPane1 = new JScrollPane();
    private FarmaTableModel ftm_productoVirtual;
    private JTable tbl_productoVirtual = new JTable();

    private JLabelFunction lblEnter = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JTextField txt_busqueda = new JTextField();
    private JLabel lbl_busqueda = new JLabel();
    private JLabel lbl_etiqueta_guia = new JLabel();
    private JLabel lbl_guia = new JLabel();

    private String cod_guia = "";
    private String num_orden_prep = "";
    private Integer fila_seleccionada = null;
    private int COL_COD = 1;
    private final int COL_ORIG_PROD = 20;
    private JPanel pnl_error = new JPanel();
    private JLabel lbl_error = new JLabel();


    public DlgListaGuiaProductoVirtualRM() {
        this(null, "", false);
    }

    public DlgListaGuiaProductoVirtualRM(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(669, 501));
        this.setResizable(false);
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Listado de Productos Magistrales");
        this.setIconImage(myParentFrame.getIconImage());

        jScrollPane1.setBounds(new Rectangle(15, 85, 635, 320));
        jScrollPane1.getViewport().add(tbl_productoVirtual, null);

        lblEnter.setText("[ ENTER ] Crear Recetario");
        lblEnter.setBounds(new Rectangle(15, 440, 165, 25));
        lblEnter.setHorizontalAlignment(SwingConstants.CENTER);
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(535, 440, 115, 25));
        lblEsc.setHorizontalAlignment(SwingConstants.CENTER);
        pnl_buscarProducto.setBounds(new Rectangle(15, 45, 635, 30));
        pnl_buscarProducto.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        pnl_buscarProducto.setBackground(new Color(43, 141, 39));
        pnl_buscarProducto.setLayout(null);
        pnl_buscarProducto.setForeground(Color.orange);
        lbl_busqueda.setText("Producto");
        lbl_busqueda.setBounds(new Rectangle(25, 5, 60, 20));
        //lbl_busqueda.setMnemonic('p');
        lbl_busqueda.setFont(new Font("SansSerif", 1, 11));
        //lbl_busqueda.setDefaultCapable(false);
        lbl_busqueda.setRequestFocusEnabled(false);
        lbl_busqueda.setBackground(new Color(50, 162, 65));
        lbl_busqueda.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        //lbl_busqueda.setFocusPainted(false);
        lbl_busqueda.setHorizontalAlignment(SwingConstants.LEFT);
        //lbl_busqueda.setContentAreaFilled(false);
        //lbl_busqueda.setBorderPainted(false);
        lbl_busqueda.setForeground(Color.white);
        txt_busqueda.setBounds(new Rectangle(100, 5, 390, 20));
        txt_busqueda.setFont(new Font("SansSerif", 1, 11));
        txt_busqueda.setForeground(new Color(32, 105, 29));
        txt_busqueda.addKeyListener(new KeyAdapter() {
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
        txt_busqueda.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                txt_busqueda_focusLost(e);
            }
        });
        lbl_etiqueta_guia.setText("Cod. Guia");
        lbl_etiqueta_guia.setFont(new Font("SansSerif", 1, 11));
        lbl_etiqueta_guia.setBackground(new Color(50, 162, 65));
        lbl_etiqueta_guia.setBounds(new Rectangle(25, 5, 75, 20));
        lbl_etiqueta_guia.setForeground(Color.white);
        lbl_guia.setBounds(new Rectangle(100, 5, 390, 20));
        lbl_guia.setForeground(Color.white);
        pnl_error.setBounds(new Rectangle(15, 405, 635, 25));
        pnl_error.setBackground(new Color(255, 130, 14));
        pnl_error.setBorder(BorderFactory.createLineBorder(SystemColor.windowText, 1));
        lbl_error.setBackground(new Color(255, 127, 15));
        lbl_error.setFont(new Font("SansSerif", 1, 12));
        lbl_error.setForeground(SystemColor.window);
        pnl_guia.setBounds(new Rectangle(15, 10, 635, 30));
        pnl_buscarProducto.add(txt_busqueda, null);
        pnl_buscarProducto.add(lbl_busqueda, null);

        pnl_guia.add(lbl_guia, null);
        pnl_guia.add(lbl_etiqueta_guia, null);
        pnl_error.add(lbl_error, null);
        jContentPane.add(pnl_error, null);
        jContentPane.add(pnl_guia, null);
        jContentPane.add(pnl_buscarProducto, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblEnter, null);
        jContentPane.add(jScrollPane1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);

        FarmaUtility.centrarVentana(this);
    }

    private void initialize() {
        ftm_productoVirtual =
                new FarmaTableModel(ConstantsVentas.columnsListaProductos, ConstantsVentas.defaultValuesListaProductos,
                                    0);
        clonarListadoProductosVirtuales();
        FarmaUtility.initSelectList(tbl_productoVirtual, ftm_productoVirtual, ConstantsVentas.columnsListaProductos);
        if (ftm_productoVirtual.getRowCount() > 0)
            FarmaUtility.ordenar(tbl_productoVirtual, ftm_productoVirtual, 2, FarmaConstants.ORDEN_ASCENDENTE);
        pnl_error.setVisible(false);
        txt_busqueda.grabFocus();
    }

    /**
     * Para clonar el listado de productos original, obteniendo solo los productos virtuales.
     * @author Edgar Rios Navarro
     * @since 29.05.2008
     */
    private void clonarListadoProductosVirtuales() {
        ArrayList<ArrayList<Object>> arrayClone = new ArrayList<ArrayList<Object>>();
        Map<String, String> listadoProdVirtual = facadeRecetario.listaProdVirtual();
        for (int i = 0; i < VariablesVentas.tableModelListaGlobalProductos.data.size(); i++) {
            ArrayList<Object> aux =
                (ArrayList<Object>)((ArrayList)VariablesVentas.tableModelListaGlobalProductos.data.get(i)).clone();
            //el registro 13 posee el indicador de producto virtual
            if (aux.get(13).toString().equals("S")) {
                String temp = aux.get(1).toString();
                if (listadoProdVirtual.containsKey(temp)) {
                    arrayClone.add(aux);
                }
            }
        }
        ftm_productoVirtual.data = arrayClone;
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private String grabarGuiaRM() {
        String codRM = "";

        //se llenan los datos de la cabecera
        ArrayList<String> tmpArrayCab = new ArrayList<String>();
        tmpArrayCab.add(FarmaVariables.vCodGrupoCia);
        tmpArrayCab.add(FarmaVariables.vCodCia);
        tmpArrayCab.add(FarmaVariables.vCodLocal);
        tmpArrayCab.add(this.num_orden_prep);
        tmpArrayCab.add(FarmaVariables.vIdUsu);
        codRM = facadeRecetario.grabarGuiaRM(tmpArrayCab);
        return codRM;
    }

    public void setGuia(String num_guia, String num_orden_prep) {
        this.cod_guia = num_guia;
        this.num_orden_prep = num_orden_prep;
        lbl_guia.setText(cod_guia);
    }

    /****************************** EVENTOS *************************************/

    public void txtProducto_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tbl_productoVirtual, txt_busqueda, 2);

        pnl_error.setVisible(false);
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            e.consume();
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            if (tbl_productoVirtual.getRowCount() == 0)
                return;
            fila_seleccionada = tbl_productoVirtual.getSelectedRow();

            if (fila_seleccionada < 0)
                FarmaUtility.showMessage(this, "ERROR: Debe seleccionar un registro.", null);
            else if (JConfirmDialog.rptaConfirmDialogDefaultNo(this,
                                                               "¿Desea crear un recetario magistral con el producto virtual seleccionado?")) {
                String rm = grabarGuiaRM();
                if (rm == null || "".equals(rm) || "ERROR".equals(rm))
                    FarmaUtility.showMessage(this,
                                             "ERROR: No se pudo crear el recetario magistral anexo a la guia seleccionada.",
                                             null);
                else {
                    String[] valores = rm.split("Ã");

                    VariablesVentas.vCod_Prod =
                            FarmaUtility.getValueFieldJTable(tbl_productoVirtual, fila_seleccionada, COL_COD);
                    VariablesVentas.vDesc_Prod =
                            FarmaUtility.getValueFieldJTable(tbl_productoVirtual, fila_seleccionada, 2);
                    VariablesVentas.vNom_Lab =
                            FarmaUtility.getValueFieldJTable(tbl_productoVirtual, fila_seleccionada, 4);
                    VariablesVentas.vUnid_Vta =
                            FarmaUtility.getValueFieldJTable(tbl_productoVirtual, fila_seleccionada, 3);
                    VariablesVentas.vVal_Prec_Lista =
                            valores[1]; //FarmaUtility.getValueFieldJTable(tbl_productoVirtual, fila_seleccionada, 10);
                    VariablesVentas.vVal_Bono =
                            FarmaUtility.getValueFieldJTable(tbl_productoVirtual, fila_seleccionada, 7);
                    VariablesVentas.vPorc_Igv_Prod =
                            FarmaUtility.getValueFieldJTable(tbl_productoVirtual, fila_seleccionada, 11);
                    VariablesVentas.vTipoProductoVirtual =
                            FarmaUtility.getValueFieldJTable(tbl_productoVirtual, fila_seleccionada, 14);
                    VariablesVentas.vMontoARecargar_Temp = "0";
                    VariablesVentas.vNumeroARecargar = "";
                    VariablesVentas.vIndOrigenProdVta =
                            FarmaUtility.getValueFieldJTable(tbl_productoVirtual, fila_seleccionada, COL_ORIG_PROD);
                    VariablesVentas.vPorc_Dcto_2 = "0";
                    VariablesVentas.vIndTratamiento = FarmaConstants.INDICADOR_N;
                    VariablesVentas.vCantxDia = "";
                    VariablesVentas.vCantxDias = "";
                    VariablesVentas.vProductoVirtual = true;
                    VariablesRecetario.strCodigoRecetario = valores[0];
                    VariablesRecetario.strCant_Recetario = "1";
                    VariablesRecetario.strPrecioTotal = valores[1]; //VariablesVentas.vVal_Prec_Lista;
                    //LLEIVA 28/Junio/2013 - Se indica que se tiene que actualizar la Orden de Preparado en FASA
                    VariablesRecetario.strAccion = "A";

                    FarmaUtility.showMessage(this, "Se creo el recetario magistral anexo a la guia seleccionada.",
                                             null);
                    cerrarVentana(true);
                }
            }
        }
    }

    public void txtProducto_keyReleased(KeyEvent e) {
        if (e.getKeyCode() != KeyEvent.VK_UP && e.getKeyCode() != KeyEvent.VK_PAGE_UP &&
            e.getKeyCode() != KeyEvent.VK_DOWN && e.getKeyCode() != KeyEvent.VK_PAGE_DOWN &&
            e.getKeyCode() != KeyEvent.VK_ENTER && e.getKeyChar() != KeyEvent.CHAR_UNDEFINED &&
            e.getKeyCode() != KeyEvent.VK_ESCAPE &&
            txt_busqueda.getText().length() > 0) { //txt_busq.setBackground(color_orig_busq);
            boolean res = true;
            res = FarmaGridUtils.buscarDescripcion(e, tbl_productoVirtual, txt_busqueda, 2);
            e.consume();
        }
    }

    public void txtProducto_keyTyped(KeyEvent e) { //chkKeyPressed(e);
    }

    private void txt_busqueda_focusLost(FocusEvent e) {
        txt_busqueda.grabFocus();
    }
}
