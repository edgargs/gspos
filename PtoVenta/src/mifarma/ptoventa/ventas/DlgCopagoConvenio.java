package mifarma.ptoventa.ventas;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenio;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;
import mifarma.ptoventa.ventas.reference.DBVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgFiltro.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCORTEZ      17.04.2008   Creación<br>
 * <br>
 * @author Jorge Luis Cortez<br>
 * @version 1.0<br>
 *
 */

public class DlgCopagoConvenio extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgCopagoConvenio.class);

    /* ************************************************************************ */
    /*                         DECLARACION PROPIEDADES                          */
    /* ************************************************************************ */

    private Frame myParentFrame;
    ArrayList parametros;
    private FarmaTableModel tableModelListaCopago;

    private JPanelWhite jContentPane = new JPanelWhite();
    private GridLayout gridLayout1 = new GridLayout();
    private JLabelFunction lblEnter = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel btnSelec = new JButtonLabel();
    private JScrollPane srcTipo = new JScrollPane();
    private JTable tblListaTipo = new JTable();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgCopagoConvenio() {
        this(null, "", false);
    }

    public DlgCopagoConvenio(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(329, 228));
        this.getContentPane().setLayout(gridLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Seleccione Copago de Convenio ");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });


        lblEnter.setBounds(new Rectangle(30, 180, 135, 20));
        lblEnter.setText("[ ENTER ] Elegir");
        lblEsc.setBounds(new Rectangle(175, 180, 140, 20));
        lblEsc.setText("[ ESC ] Escape");
        pnlTitle1.setBounds(new Rectangle(5, 10, 310, 25));
        btnSelec.setText("Seleccione Copago");
        btnSelec.setBounds(new Rectangle(10, 5, 125, 15));
        btnSelec.setMnemonic('S');
        btnSelec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnSelec_actionPerformed(e);
            }
        });
        srcTipo.setBounds(new Rectangle(5, 35, 310, 140));
        tblListaTipo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaTipo_keyPressed(e);
            }
        });


        jContentPane.add(lblEnter, null);
        jContentPane.add(lblEsc, null);
        srcTipo.getViewport().add(tblListaTipo, null);
        jContentPane.add(srcTipo, null);
        pnlTitle1.add(btnSelec, null);
        jContentPane.add(pnlTitle1, null);
        this.getContentPane().add(jContentPane, null);

    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        //cargar_cmbTipo();
        initTable();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */


    private void initTable() {
        tableModelListaCopago =
                new FarmaTableModel(ConstantsVentas.columnsListaCopago, ConstantsVentas.defaultValuesListaTipoCopago,
                                    0);
        FarmaUtility.initSimpleList(tblListaTipo, tableModelListaCopago, ConstantsVentas.columnsListaCopago);
        cargaLista();
    }

    /**
     * Carga los Items  al cmbTipo
     */
    /*private void cargar_cmbTipo(){
   //Ubicacion
   FarmaLoadCVL.loadCVLfromArrays(cmbTipo,ConstantsVentas.NOM_HASTABLE_CMBFILTROTIPO,
   ConstantsVentas.vCodColumna,ConstantsVentas.vDescColumna,true);
  }*/

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(tblListaTipo);
    }


    private void cmbTipo_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            //VariablesVentas.vCodFiltro = FarmaLoadCVL.getCVLCode(ConstantsVentas.NOM_HASTABLE_CMBFILTROTIPO,cmbTipo.getSelectedIndex());
            VariablesConvenioBTLMF.vValorSelCopago =
                    Double.parseDouble(tblListaTipo.getValueAt(tblListaTipo.getSelectedRow(), 1).toString());
            VariablesConvenio.vPorcCoPago = String.valueOf(VariablesConvenioBTLMF.vValorSelCopago);

            log.debug("Copago Variable : " + VariablesConvenioBTLMF.vValorSelCopago);
            cerrarVentana(true);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(true);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }


    private void tblListaTipo_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void cargaLista() {
        try {
            DBVentas.listaTipoCopago(VariablesConvenioBTLMF.vCodConvenio, tableModelListaCopago);
            /*if(tblListaTipo.getRowCount()>0)
      {
      FarmaUtility.ordenar(tblListaTipo,tableModelListaCopago,0,FarmaConstants.ORDEN_ASCENDENTE);
      }*/
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ha ocurrido un error al obtener listado de filtro \n" +
                    sql.getMessage(), tblListaTipo);
        }
    }

    private void btnSelec_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaTipo);
    }

}
