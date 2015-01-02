package mifarma.ptoventa.ventas;


import com.gs.mifarma.componentes.JButtonFunction;
import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelOrange;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.DlgProcesar;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2010 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgListaProdDIGEMID.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ASOSA      02.02.2010   Creación
 * @author Alfredo Sosa Dordán<br>
 * @version 1.0<br>
 *
 */
public class DlgListaProdDIGEMID extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgListaProdDIGEMID.class);

    private Frame myParentFrame;
    String tipo = "1";

    private FarmaTableModel tabmodProds;

    private JPanelWhite pnlFondo = new JPanelWhite();
    private JPanelHeader pnlHead = new JPanelHeader();
    private JButtonFunction btnbus = new JButtonFunction();
    private JTextFieldSanSerif txtprod = new JTextFieldSanSerif();
    private JButtonLabel btnprod = new JButtonLabel();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JButtonLabel jButtonLabel2 = new JButtonLabel();
    private JScrollPane srcLista = new JScrollPane();
    private JButtonFunction btnclose = new JButtonFunction();
    private JPanelTitle pnlpie = new JPanelTitle();
    private JTable tblProds = new JTable();
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JLabelOrange jLabelOrange1 = new JLabelOrange();
    private JEditorPane panelHTML = new JEditorPane();
    private JButtonLabel lblTipo = new JButtonLabel();
    private JComboBox cmbTipo = new JComboBox();
    private JLabelFunction btnF1 = new JLabelFunction();
    private JLabelFunction btnF2 = new JLabelFunction();

    public DlgListaProdDIGEMID() {
        this(null, "", false);
    }

    public DlgListaProdDIGEMID(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(744, 576));
        this.getContentPane().setLayout(null);
        this.setTitle("Lista de Productos y Precios");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlFondo.setBounds(new Rectangle(0, 0, 740, 550));
        pnlFondo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pnlFondo_keyPressed(e);
            }
        });
        pnlHead.setBounds(new Rectangle(5, 270, 720, 30));
        pnlHead.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pnlHead_keyPressed(e);
            }
        });
        btnbus.setText("Buscar");
        btnbus.setBounds(new Rectangle(625, 5, 90, 20));
        btnbus.setFont(new Font("SansSerif", 1, 12));
        btnbus.setMnemonic('b');
        btnbus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnbus_actionPerformed(e);
            }
        });
        btnbus.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnbus_keyPressed(e);
            }
        });
        txtprod.setBounds(new Rectangle(260, 5, 360, 20));
        txtprod.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtprod_keyPressed(e);
            }
        });
        btnprod.setText("Descripción: ");
        btnprod.setBounds(new Rectangle(180, 10, 75, 15));
        btnprod.setMnemonic('d');
        btnprod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnprod_actionPerformed(e);
            }
        });
        btnprod.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnprod_keyPressed(e);
            }
        });
        pnlTitle.setBounds(new Rectangle(5, 305, 720, 20));
        pnlTitle.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pnlTitle_keyPressed(e);
            }
        });
        jButtonLabel2.setText("Relación de Productos");
        jButtonLabel2.setBounds(new Rectangle(30, 0, 140, 15));
        jButtonLabel2.setMnemonic('r');
        jButtonLabel2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonLabel2_actionPerformed(e);
            }
        });
        jButtonLabel2.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jButtonLabel2_keyPressed(e);
            }
        });
        srcLista.setBounds(new Rectangle(5, 325, 720, 180));
        srcLista.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                srcLista_keyPressed(e);
            }
        });
        btnclose.setText("[ESC] Cerrar");
        btnclose.setBounds(new Rectangle(595, 520, 115, 20));
        btnclose.setFont(new Font("Arial Black", 0, 10));
        btnclose.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnclose_keyPressed(e);
            }
        });
        pnlpie.setBounds(new Rectangle(5, 505, 720, 10));
        pnlpie.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pnlpie_keyPressed(e);
            }
        });
        tblProds.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblProds_keyPressed(e);
            }
        });
        jPanelHeader1.setBounds(new Rectangle(430, 0, 245, 20));
        jLabelWhite1.setText("Lista de Precios Normal y Club MiFarma");
        jLabelWhite1.setBounds(new Rectangle(10, 5, 220, 15));
        jPanelWhite1.setBounds(new Rectangle(5, 10, 720, 255));
        jPanelWhite1.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        jLabelOrange1.setText("LISTA DE PRECIOS PARA EL CONSUMIDOR");
        jLabelOrange1.setBounds(new Rectangle(170, 10, 405, 15));
        jLabelOrange1.setFont(new Font("SansSerif", 1, 18));
        panelHTML.setBounds(new Rectangle(10, 25, 695, 215));
        lblTipo.setText("Tipo:");
        lblTipo.setBounds(new Rectangle(5, 10, 35, 15));
        lblTipo.setMnemonic('T');
        lblTipo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblTipo_actionPerformed(e);
            }
        });
        lblTipo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                lblTipo_keyPressed(e);
            }
        });
        cmbTipo.setBounds(new Rectangle(40, 5, 130, 20));
        cmbTipo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbTipo_keyPressed(e);
            }
        });
        btnF1.setBounds(new Rectangle(5, 520, 180, 20));
        btnF1.setText("[ F1 ] Ver principio activo");
        btnF2.setBounds(new Rectangle(195, 520, 150, 20));
        btnF2.setText("[ F2 ] Consultar por DCI");
        pnlHead.add(cmbTipo, null);
        pnlHead.add(lblTipo, null);
        pnlHead.add(btnprod, null);
        pnlHead.add(txtprod, null);
        pnlHead.add(btnbus, null);
        jPanelWhite1.add(panelHTML, null);
        jPanelWhite1.add(jLabelOrange1, null);
        pnlFondo.add(btnF2, null);
        pnlFondo.add(btnF1, null);
        pnlFondo.add(jPanelWhite1, null);
        pnlFondo.add(pnlpie, null);
        pnlFondo.add(btnclose, null);
        srcLista.getViewport().add(tblProds, null);
        pnlFondo.add(srcLista, null);
        pnlFondo.add(pnlTitle, null);
        pnlTitle.add(jButtonLabel2, null);
        jPanelHeader1.add(jLabelWhite1, null);
        pnlTitle.add(jPanelHeader1, null);
        pnlFondo.add(pnlHead, null);
        this.getContentPane().add(pnlFondo, null);
    }

    private void initialize() {
        initTableListaPreciosProductos();
        mostrarMensajeConsumidor();
        cargaCombo(); //ASOSA, 28.09.2010
        //permite que no se muevan las columnas de Jtable
        tblProds.getTableHeader().setReorderingAllowed(false);
        //permite que no se cambien el tamaño de la columna del Jtable
        tblProds.getTableHeader().setResizingAllowed(false);
    }

    private void mostrarMensajeConsumidor() {
        panelHTML.setContentType("text/html");
        try {
            String vHtml = DBVentas.getMensajeComsumidor();
            panelHTML.setText(vHtml);
            log.debug(vHtml);
        } catch (SQLException e) {
            log.error("Error al recuperar el mensaje consumidor", e);
        }
    }

    private void modificar() {
        //INI, ASOSA, 27.09.2010
        if (FarmaVariables.vCodGrupoCia.equalsIgnoreCase(ConstantsVentas.COD_CIA_BOL)) {
            jPanelHeader1.setVisible(false); //panel donde aparece la cabecera de lista de precios
        }
        //FIN, ASOSA, 27.09.2010
        //ERIOS 2.4.7 Mostrar columnas descuentos
        if (!DlgProcesar.cargaIndMostrarColumnasDesc()) {
            jPanelHeader1.setVisible(false);
            ConstantsVentas.columnsListProds[4].m_width = 0;
            ConstantsVentas.columnsListProds[5].m_width = 0;
            FarmaUtility.initSimpleListCleanColumns(tblProds, tabmodProds, ConstantsVentas.columnsListProds,
                                                    new ArrayList(), null, null, false);
        }

        FarmaUtility.moveFocus(cmbTipo); //ASOSA, 28.09.2010
        FarmaUtility.centrarVentana(this);
    }

    private void initTableListaPreciosProductos() {

        tabmodProds = new FarmaTableModel(ConstantsVentas.columnsListProds, ConstantsVentas.defaultValuesListProds, 0);
        FarmaUtility.initSimpleList(tblProds, tabmodProds, ConstantsVentas.columnsListProds);
    }

    private void btnprod_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtprod);
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(true);
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {
            mostrarPrincAct();
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {
            mostrarPorDCI();
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }


    private void txtprod_keyPressed(KeyEvent e) {


        ///////////////////

        FarmaGridUtils.aceptarTeclaPresionada(e, tblProds, txtprod, 0);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            btnbus.doClick();
        } else {
            chkKeyPressed(e);
        }
    }

    private void jButtonLabel2_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblProds);
    }

    private void pnlFondo_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnprod_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnbus_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void pnlHead_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void jButtonLabel2_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void pnlTitle_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void srcLista_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblProds_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void pnlpie_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnclose_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnbus_actionPerformed(ActionEvent e) {
        listarProdsForCliente();
    }

    private int getMinCaracteres() {
        int nValor = 0;
        try {
            nValor = Integer.parseInt(DBVentas.getNumCaracteres().trim());
        } catch (SQLException e) {
            log.error("", e);
            nValor = 4;
        }
        return nValor;
    }

    private void listarProdsForCliente() {
        String cadena = txtprod.getText();
        int nNumCaracteres = getMinCaracteres();
        if ((cadena.trim()).length() >= nNumCaracteres) {
            try {
                //DBVentas.getListaProdsCliente(tabmodProds,cadena,);
                DBVentas.getListaProdsCliente(tabmodProds, cadena, tipo); //ASOSA, 28.09.2010
                if (tblProds.getRowCount() > 0) {
                    setJTable(tblProds, txtprod);
                }
            } catch (SQLException e) {
                FarmaUtility.showMessage(this, "ERROR en listar Productos: \n" +
                        e.getMessage(), txtprod);
                log.error("", e);
            }
            if (tabmodProds.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, "No se encontraron resultado para la búsqueda", txtprod);
            }
        } else {
            FarmaUtility.showMessage(this,
                                     "Debe ingresar al menos " + nNumCaracteres + " caracteres del nombre para la búsqueda",
                                     txtprod);
        }
    }

    private void this_windowOpened(WindowEvent e) {
        modificar();
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    //INI - ASOSA, 28.09.2010

    private void lblTipo_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbTipo);
    }

    private void lblTipo_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }


    private void cargaCombo() {
        FarmaLoadCVL.loadCVLfromArrays(cmbTipo, ConstantsVentas.HASHTABLE_TIP_DIGEMID, ConstantsVentas.TIP_DIGEMID_COD,
                                       ConstantsVentas.TIP_DIGEMID_DESC, true);
    }

    private void cmbTipo_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtprod);
            tipo = FarmaLoadCVL.getCVLCode(ConstantsVentas.HASHTABLE_TIP_DIGEMID, cmbTipo.getSelectedIndex());
        } else {
            chkKeyPressed(e);
        }
    }
    //FIN - ASOSA, 28.09.2010

    //INI - ASOSA, 29.09.2010

    private void mostrarPrincAct() {
        if (tabmodProds.getRowCount() > 0) {
            if (tblProds.getSelectedRow() >= 0) {

                int POSICION = 0;
                if (FarmaVariables.vCodGrupoCia.equalsIgnoreCase(ConstantsVentas.COD_CIA_PERU)) {
                    POSICION = 6;
                } else if (FarmaVariables.vCodGrupoCia.equalsIgnoreCase(ConstantsVentas.COD_CIA_BOL)) {
                    POSICION = 4;
                }
                String codprod =
                    FarmaUtility.getValueFieldArrayList(tabmodProds.data, tblProds.getSelectedRow(), POSICION).trim();

                //JMIRANDA 05.10.2010 VERIFICAR SI ES FARMA
                if (UtilityVentas.getIndProdFarma(codprod, txtprod, this)) {
                    DlgPrincAct objPrincAct =
                        new DlgPrincAct(myParentFrame, "Lista de Principios Activos", true, codprod);
                    objPrincAct.setVisible(true);
                }
            } else {
                FarmaUtility.showMessage(this, "Elija un producto por favor", txtprod);
            }
        } else {
            FarmaUtility.showMessage(this, "No hay productos en la lista", cmbTipo);
        }
    }
    //FIN - ASOSA, 29.09.2010

    private void mostrarPorDCI() {
        int Col_Cod_Prod = 6;
        if (tabmodProds.getRowCount() > 0) {
            if (tblProds.getSelectedRow() >= 0) {
                VariablesVentas.vCodProdDCI =
                        FarmaUtility.getValueFieldArrayList(tabmodProds.data, tblProds.getSelectedRow(),
                                                            Col_Cod_Prod).trim();

                //JMIRANDA 05.10.2010 VERIFICAR SI ES FARMA
                if (UtilityVentas.getIndProdFarma(VariablesVentas.vCodProdDCI, txtprod, this)) {
                    DlgListaProdDigemidDCI mDlgAlter = new DlgListaProdDigemidDCI(myParentFrame, "", true);
                    mDlgAlter.setVisible(true);
                }
            } else {
                FarmaUtility.showMessage(this, "Elija un producto por favor", txtprod);
            }
        } else {
            FarmaUtility.showMessage(this, "No hay productos en la lista", cmbTipo);
        }
    }

    private void setJTable(JTable pJTable, JTextField pText) {
        if (pJTable.getRowCount() > 0) {
            FarmaGridUtils.showCell(pJTable, 0, 0);
            FarmaUtility.setearActualRegistro(pJTable, pText, 0);
        }
        FarmaUtility.moveFocus(pText);
    }
}
