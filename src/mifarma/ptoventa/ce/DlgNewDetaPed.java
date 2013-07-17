package mifarma.ptoventa.ce;

import com.gs.mifarma.componentes.JButtonFunction;
import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.Dimension;
import java.awt.Frame;

import java.awt.Rectangle;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;

import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.ce.reference.VariablesNewCobro;

public class DlgNewDetaPed extends JDialog {
    
    FarmaTableModel tableModelDetallePedido;
    Frame myParentFrame;
    
    
    private JPanelWhite pnlFondo = new JPanelWhite();
    private JPanelTitle pnltitulo = new JPanelTitle();
    private JLabelFunction lblsalir = new JLabelFunction();
    private JScrollPane srcLista = new JScrollPane();
    private JTable tblDetalle = new JTable();
    private JLabelWhite lblnumsped = new JLabelWhite();

    public DlgNewDetaPed() {
        this(null, "", false);
    }

    public DlgNewDetaPed(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(668, 278));
        this.getContentPane().setLayout( null );
        this.setTitle("Lista de Detalle");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }

                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }
                });
        pnlFondo.setBounds(new Rectangle(0, 0, 665, 255));
        pnlFondo.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        pnlFondo_keyPressed(e);
                    }
                });
        pnltitulo.setBounds(new Rectangle(5, 10, 650, 25));
        pnltitulo.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        pnltitulo_keyPressed(e);
                    }
                });
        lblsalir.setBounds(new Rectangle(530, 225, 117, 19));
        lblsalir.setText("[ ESC ] Cerrar");
        lblsalir.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        lblsalir_keyPressed(e);
                    }
                });
        srcLista.setBounds(new Rectangle(5, 35, 650, 180));
        srcLista.addKeyListener(new KeyAdapter() {
                    public void keyReleased(KeyEvent e) {
                        srcLista_keyReleased(e);
                    }

                    public void keyPressed(KeyEvent e) {
                        srcLista_keyPressed(e);
                    }
                });
        tblDetalle.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        tblDetalle_keyPressed(e);
                    }
                });
        lblnumsped.setText("jLabelWhite1");
        lblnumsped.setBounds(new Rectangle(10, 5, 315, 15));
        srcLista.getViewport().add(tblDetalle, null);
        pnlFondo.add(srcLista, null);
        pnlFondo.add(lblsalir, null);
        pnltitulo.add(lblnumsped, null);
        pnlFondo.add(pnltitulo, null);
        this.getContentPane().add(pnlFondo, null);
    }
    
    private void initialize(){
        initTablaDetalle();
    }
    
    private void initTablaDetalle(){
        tableModelDetallePedido = new FarmaTableModel(ConstantsCaja.columnsDetallePedidoNew,ConstantsCaja.defaultDetallePedidoNew, 0);
        FarmaUtility.initSimpleList(tblDetalle, tableModelDetallePedido,ConstantsCaja.columnsDetallePedidoNew);
        cargaDetallePedido();
    }
    
    private void cargaDetallePedido(){
        try {
                DBCaja.listaNewDetaPedido(tableModelDetallePedido,VariablesNewCobro.numpedvta);
                if (tblDetalle.getRowCount() > 0)
                        FarmaUtility.ordenar(tblDetalle, tableModelDetallePedido, 0,FarmaConstants.ORDEN_ASCENDENTE);
                System.out.println("se cargo la lista de detalle");
        } catch (SQLException e) {
                e.printStackTrace();
                FarmaUtility.showMessage(this,"Error al cargra lista detalle pedido. \n " + e.getMessage(),tblDetalle);
        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    
    private void cerrarVentana(boolean pAceptar) {
            FarmaVariables.vAceptar = pAceptar;
            this.setVisible(false);
            this.dispose();
    }
    
    private void chkkeyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            cerrarVentana(false);
        }
    }

    private void lblsalir_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void pnlFondo_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void lbllistadeta_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void pnltitulo_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void srcLista_keyReleased(KeyEvent e) {
        
    }

    private void this_windowOpened(WindowEvent e) {
        lblnumsped.setText("Correlativo: "+VariablesNewCobro.numpedvta+"      -      Num.Diario: "+VariablesNewCobro.numpeddiario);
        FarmaUtility.centrarVentana(this);
    }

    private void srcLista_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }

    private void tblDetalle_keyPressed(KeyEvent e) {
        chkkeyPressed(e);
    }
}
