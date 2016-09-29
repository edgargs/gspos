package mifarma.ptoventa.lealtad;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import farmapuntos.bean.TarjetaBean;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JScrollPane;

import mifarma.common.FarmaJTable;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.lealtad.reference.ConstantsLealtad;
import mifarma.ptoventa.lealtad.reference.FacadeLealtad;
import mifarma.ptoventa.puntos.reference.VariablesPuntos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author ERIOS
 * @since 05.02.2015
 */
public class DlgListaAcumulaX1 extends JDialog {
    
    private static final Logger log = LoggerFactory.getLogger(DlgListaAcumulaX1.class);

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private Frame myParentFrame;
    private FarmaTableModel tableModel;

    private FarmaJTable tblLista = new FarmaJTable();

    private final int COL_DATO = 1;
    private final int COL_COD = 2;
    private final int COL_TIPO_DATO = 3;
    private final int COL_SOLO_LECTURA = 4;
    private final int COL_IND_OBLI = 5;
    private final int COL_IND_BUS = 6;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JScrollPane scrLista = new JScrollPane();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JButtonLabel btnLista = new JButtonLabel();
    private String pCodProd;
    private FacadeLealtad facadeLealtad;

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgListaAcumulaX1() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    public DlgListaAcumulaX1(Frame parent, String title, boolean modal) {
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
    /*                              METODO jbInit                               */
    /* ************************************************************************ */
    
    private void jbInit() throws Exception {
        this.getContentPane().setLayout(borderLayout1);
        this.setSize(new Dimension(583, 306));
        this.setDefaultCloseOperation(0);
        this.setTitle("Campañas para acumular");
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        lblEsc.setBounds(new Rectangle(465, 245, 95, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblF11.setBounds(new Rectangle(350, 245, 105, 20));
        lblF11.setText("[ F11 ] Aceptar");
        scrLista.setBounds(new Rectangle(10, 30, 555, 205));
        tblLista.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblLista_keyPressed(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(10, 10, 555, 20));
        btnLista.setText("Lista");
        btnLista.setBounds(new Rectangle(5, 0, 105, 20));
        btnLista.setMnemonic('L');
        btnLista.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLista_actionPerformed(e);
            }
        });
        scrLista.getViewport().add(tblLista, null);
        pnlTitle1.add(btnLista, null);
        
        jContentPane.add(pnlTitle1, null);
        jContentPane.add(scrLista, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);

        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        
        initTable();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsLealtad.columnsListaAcumula, ConstantsLealtad.defaultValuesListaAcumula,
                                    0);
        FarmaUtility.initSimpleList(tblLista, tableModel,
                                    ConstantsLealtad.columnsListaAcumula);
        
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        facadeLealtad.listaAcumulaX1(tableModel,pCodProd);
        FarmaUtility.moveFocusJTable(tblLista);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnLista_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblLista);
    }

    private void tblLista_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            funcionF11();            
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
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

    private void funcionF11() {
        int fila = tblLista.getSelectedRow();
        if(fila >= 0){
            String codCamp = FarmaUtility.getValueFieldJTable(tblLista, fila, 0);
            String pCodEqui = FarmaUtility.getValueFieldJTable(tblLista, fila, 2);
            String codMatrizAcu = FarmaUtility.getValueFieldJTable(tblLista, fila, 3);
            TarjetaBean tarjetaCliente = VariablesPuntos.frmPuntos.getTarjetaBean();
            facadeLealtad.inscribeAcumulaX1(this,pCodProd,codCamp,pCodEqui,codMatrizAcu,tarjetaCliente);
            cerrarVentana(true);
        }else{
            FarmaUtility.showMessage(this, "¡Debe seleccionar una campaña!", tblLista);
        }
    }

    public void setpCodProd(String pCodProd) {
        this.pCodProd = pCodProd;
    }

    public void setFacadeLealtad(FacadeLealtad facadeLealtad) {
        this.facadeLealtad = facadeLealtad;
    }
}
