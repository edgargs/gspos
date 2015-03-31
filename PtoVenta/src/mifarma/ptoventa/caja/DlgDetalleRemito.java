package mifarma.ptoventa.caja;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

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

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgDetalleRemito extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgDetalleRemito.class);

    private FarmaTableModel tableModelLista;
    private FarmaTableModel tableModelLista2;
    private Frame myParentFrame;

    private final int COL_COD_VEND = 0;
    private final int COL_NOM_VEND = 1;
    private final int COL_TOT_VTA_CIGV = 2;
    private final int COL_TOT_VTA_SIGV = 3;
    private final int COL_GG = 4;
    private final int COL_G = 5;
    private final int COL_OTROS = 6;
    private final int COL_SERVICIOS = 7;
    private final int COL_PORCENTAJE = 8;
    private final int COL_CALCULADO = 9;
    private final int COL_TIPO_FILA = 11;
    private final int COL_SEC_USU = 12;
    private final int COL_ORDEN = 13;
    private final int COL_LOGIN = 14;


    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
    private JPanelTitle pnlTitulo = new JPanelTitle();
    private JButtonLabel btnPeriodo = new JButtonLabel();
    private JButtonLabel btnSobres = new JButtonLabel();


    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF10 = new JLabelFunction();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JButtonLabel btnCodRemito = new JButtonLabel();
    private JTable tblSobres = new JTable();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JLabelWhite lblFecha = new JLabelWhite();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JPanelTitle jPanelTitle2 = new JPanelTitle();
    private JLabelWhite jLabelWhite2 = new JLabelWhite();
    private JLabelWhite lblCantS = new JLabelWhite();
    private JLabelWhite jLabelWhite3 = new JLabelWhite();
    private JLabelWhite lblTotalSoles = new JLabelWhite();
    private JLabelWhite jLabelWhite4 = new JLabelWhite();
    private JLabelWhite lblCantD = new JLabelWhite();
    private JLabelWhite jLabelWhite5 = new JLabelWhite();
    private JLabelWhite lblTotalDolares = new JLabelWhite();

    public DlgDetalleRemito() {
        this(null, "", false);
    }

    public DlgDetalleRemito(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(766, 489));
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Detalle Remito");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });

        pnlCriterioBusqueda.setBounds(new Rectangle(5, 5, 745, 40));
        pnlTitulo.setBounds(new Rectangle(5, 50, 745, 20));
        btnPeriodo.setText("Número Remito :");
        btnPeriodo.setBounds(new Rectangle(50, 10, 100, 20));
        btnPeriodo.setMnemonic('N');
        btnSobres.setText("Lista de sobres que se asignarán al número de remito ingresado :");
        btnSobres.setBounds(new Rectangle(10, 0, 380, 20));
        btnSobres.setMnemonic('l');
        btnSobres.setToolTipText("null");


        btnSobres.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnSobres_actionPerformed(e);
            }
        });

        lblEsc.setBounds(new Rectangle(645, 435, 105, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        //lblF10.setBounds(new Rectangle(150, 370, 135, 20));
        //lblF10.setText("[ F8 ]Guardar Archivo");
        jScrollPane2.setBounds(new Rectangle(5, 70, 745, 305));
        btnCodRemito.setBounds(new Rectangle(165, 10, 115, 20));
        tblSobres.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblSobres_keyPressed(e);
            }
        });
        jLabelWhite1.setText("Fecha Creación :");
        jLabelWhite1.setBounds(new Rectangle(370, 10, 95, 20));
        lblFecha.setBounds(new Rectangle(475, 10, 170, 20));
        //jContentPane.add(lblF10, null);
        jPanelTitle1.setBounds(new Rectangle(5, 380, 1, 1));
        jPanelTitle2.setBounds(new Rectangle(5, 375, 745, 45));
        jPanelTitle2.setBackground(Color.white);
        jPanelTitle2.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        jLabelWhite2.setText("Nro Sobres(S/.)");
        jLabelWhite2.setBounds(new Rectangle(10, 5, 90, 30));
        jLabelWhite2.setForeground(Color.black);
        lblCantS.setText("0");
        lblCantS.setBounds(new Rectangle(100, 10, 80, 25));
        lblCantS.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblCantS.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCantS.setForeground(Color.black);
        lblCantS.setFont(new Font("SansSerif", 1, 13));
        jLabelWhite3.setText("Total Soles : S/.");
        jLabelWhite3.setBounds(new Rectangle(195, 10, 90, 25));
        jLabelWhite3.setForeground(Color.black);
        lblTotalSoles.setText("0");
        lblTotalSoles.setBounds(new Rectangle(280, 10, 85, 25));
        lblTotalSoles.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotalSoles.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblTotalSoles.setForeground(Color.black);
        lblTotalSoles.setFont(new Font("SansSerif", 1, 13));
        jLabelWhite4.setText("Nro Sobres Dólares :");
        jLabelWhite4.setBounds(new Rectangle(375, 10, 115, 25));
        jLabelWhite4.setForeground(Color.black);
        lblCantD.setText("0");
        lblCantD.setBounds(new Rectangle(485, 10, 75, 25));
        lblCantD.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCantD.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblCantD.setForeground(Color.black);
        lblCantD.setFont(new Font("SansSerif", 1, 13));
        jLabelWhite5.setText("Total Dolares : $");
        jLabelWhite5.setBounds(new Rectangle(570, 10, 90, 25));
        jLabelWhite5.setForeground(Color.black);
        lblTotalDolares.setText("0");
        lblTotalDolares.setBounds(new Rectangle(655, 10, 85, 25));
        lblTotalDolares.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotalDolares.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblTotalDolares.setForeground(Color.black);
        lblTotalDolares.setFont(new Font("SansSerif", 1, 13));
        jScrollPane2.getViewport().add(tblSobres, null);
        jPanelTitle2.add(lblTotalDolares, null);
        jPanelTitle2.add(jLabelWhite5, null);
        jPanelTitle2.add(lblCantD, null);
        jPanelTitle2.add(jLabelWhite4, null);
        jPanelTitle2.add(lblTotalSoles, null);
        jPanelTitle2.add(jLabelWhite3, null);
        jPanelTitle2.add(lblCantS, null);
        jPanelTitle2.add(jLabelWhite2, null);
        jContentPane.add(jPanelTitle2, null);
        jContentPane.add(jPanelTitle1, null);
        jContentPane.add(jScrollPane2, null);
        jContentPane.add(lblEsc, null);
        pnlTitulo.add(btnSobres, null);
        jContentPane.add(pnlTitulo, null);
        jContentPane.add(pnlCriterioBusqueda, null);
        pnlCriterioBusqueda.add(lblFecha, null);
        pnlCriterioBusqueda.add(jLabelWhite1, null);
        pnlCriterioBusqueda.add(btnCodRemito, null);
        pnlCriterioBusqueda.add(btnPeriodo, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ********************************************************************** */
    /*                            METODO INITIALIZE                           */
    /* ********************************************************************** */

    private void initialize() {
        initTableListaVentasVendedor();
    }

    /* ********************************************************************** */
    /*     METODO DE INICIALIZACION DE LOS OBJETOS JTABLE Y FARMATABLEMODEL   */
    /* ********************************************************************** */

    private void initTableListaVentasVendedor() {
        /* tableModelLista = new FarmaTableModel(ConstantsCaja.columnsListaFechasDet,ConstantsCaja.defaultListaFechasDet,0);
    FarmaUtility.initSimpleList(tblFechas,tableModelLista,ConstantsCaja.columnsListaFechasDet);
    listarFechaVentas();*/

        tableModelLista2 = new FarmaTableModel(ConstantsCaja.columnsListaSobres, ConstantsCaja.defaultListaSobres, 0);
        FarmaUtility.initSimpleList(tblSobres, tableModelLista2, ConstantsCaja.columnsListaSobres);


    }

    /* ********************************************************************** */
    /*      METODOS DE EVENTOS DE LOS OBJETOS DE LA CLASE Y DEL JDIALOG       */
    /* ********************************************************************** */


    private void txtFechaFin_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        chkKeyPressed(e);
    }


    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        // FarmaUtility.moveFocus(tblFechas);
        btnCodRemito.setText(VariablesCaja.NumRemito);
        lblFecha.setText(VariablesCaja.FecRemito);
        listaSobres(VariablesCaja.NumRemito);
    }


    private void btnListado_actionPerformed(ActionEvent e) {
        if (tblSobres.getRowCount() > 0) {
            FarmaUtility.moveFocus(tblSobres);
        }
    }

    private void btnSobres_actionPerformed(ActionEvent e) {

        if (tblSobres.getRowCount() > 0)
            FarmaUtility.moveFocus(tblSobres);
    }

    /*private void btnFechas_actionPerformed(ActionEvent e) {
        if(tblFechas.getRowCount()>0)
        FarmaUtility.moveFocus(tblFechas);
    }*/

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    /*
   private void tblFechas_keyReleased(KeyEvent e) {

     if(tblFechas.getSelectedRow()>-1){
         log.debug("Fecha.."+FarmaUtility.getValueFieldJTable(tblFechas,tblFechas.getSelectedRow(),0));
        listaSobres(FarmaUtility.getValueFieldJTable(tblFechas,tblFechas.getSelectedRow(),0));
     }
   }*/

    /* ********************************************************************** */
    /*                            METODOS AUXILIARES                          */
    /* ********************************************************************** */

    private void chkKeyPressed(KeyEvent e) {

        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {

        } else if (e.getKeyCode() == KeyEvent.VK_F9) {
            /*if(tblLista.getRowCount() <= 0)
      FarmaUtility.showMessage(this,"Debe realizar una busqueda antes de ordenar",txtNumRemito);
      else
      muestraVentaOrdenar();*/

        } else if (e.getKeyCode() == KeyEvent.VK_F8) {

        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F12(e)) {


        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);

        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {

        } else if (e.getKeyCode() == KeyEvent.VK_F5) {

        }
    }

    private void tblFechas_keyPressed(KeyEvent e) {

        chkKeyPressed(e);
    }

    private void tblSobres_keyPressed(KeyEvent e) {
        chkKeyPressed(e);

    }

    /**
     * se obtiene las fechas sobres no relacionado a remitos
     *
     * */
    /*private void listarFechaVentas(){
       try
       {
        DBCaja.getFecRemito(tableModelLista,VariablesCaja.NumRemito);
        if(tblFechas.getRowCount()>0)
           FarmaUtility.ordenar(tblFechas,tableModelLista,0,FarmaConstants.ORDEN_ASCENDENTE);

       }catch(SQLException sql)
           {
             log.error("",sql);
             FarmaUtility.showMessage(this, "Error al listar remito :\n" + sql.getMessage(),tblFechas);
           }
   }
   */
    private void listaSobres(String Fecha) {
        lblTotalDolares.setText("0");
        lblTotalSoles.setText("0");
        lblCantD.setText("0");
        lblCantS.setText("0");
        try {
            //DBCaja.getSobresFec(tableModelLista2,Fecha);
            DBCaja.getSobresRemito(tableModelLista2, Fecha);
            if (tblSobres.getRowCount() > 0) {
                FarmaUtility.ordenar(tblSobres, tableModelLista2, 0, FarmaConstants.ORDEN_ASCENDENTE);
                FarmaUtility.moveFocus(tblSobres);
                lblTotalDolares.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTableModel(tableModelLista2, 8),
                                                                  2));
                lblTotalSoles.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTableModel(tableModelLista2, 7),
                                                                2));

                lblCantD.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTableModel(tableModelLista2, 10), 0));
                lblCantS.setText(FarmaUtility.formatNumber(FarmaUtility.sumColumTableModel(tableModelLista2, 9), 0));

                // lblCantD.setText(""+Integer.parseInt(""+FarmaUtility.sumColumTableModel(tableModelLista2,10)));
                // lblCantS.setText(""+Integer.parseInt(""+FarmaUtility.sumColumTableModel(tableModelLista2,9)));
            }

        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al listar Sobres :\n" +
                    sql.getMessage(), tblSobres);
        }
    }


    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }


}
