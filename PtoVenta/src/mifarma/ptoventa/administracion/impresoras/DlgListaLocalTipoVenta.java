package mifarma.ptoventa.administracion.impresoras;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;

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

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.SwingConstants;

import mifarma.common.FarmaColumnData;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;

import mifarma.common.FarmaUtility;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.DlgListaMaestros;

import mifarma.ptoventa.administracion.impresoras.reference.DBImpresoras;
import mifarma.ptoventa.administracion.reference.UtilityAdministracion;
import mifarma.ptoventa.inventario.DlgIngresarLote;
import mifarma.ptoventa.inventario.reference.DBInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgListaLocalTipoVenta extends JDialog {
    
    private static final Logger log = LoggerFactory.getLogger(DlgListaLocalTipoVenta.class);

    private Frame myParentFrame;
    private FarmaTableModel tableModel;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JLabelFunction lblEnter = new JLabelFunction();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JPanel pnlRelacionFiltros = new JPanel();
    private JTable tblMaestro = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JButtonLabel btnRelacionMaestros = new JButtonLabel();
    private JButtonLabel jButtonLabel1 = new JButtonLabel();
    private String ipPc;
    
    public DlgListaLocalTipoVenta() {
        this(null, "", false);
    }

    public DlgListaLocalTipoVenta(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(343, 284));
        this.getContentPane().setLayout(borderLayout1);
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
        jContentPane.setSize(new Dimension(623, 335));
        jContentPane.setForeground(Color.white);
        lblEnter.setText("[ ENTER ] Seleccionar");
        lblEnter.setBounds(new Rectangle(95, 225, 130, 20));
        jScrollPane1.setBounds(new Rectangle(15, 35, 310, 180));
        jScrollPane1.setBackground(new Color(255, 130, 14));
        pnlRelacionFiltros.setBounds(new Rectangle(15, 15, 310, 20));
        pnlRelacionFiltros.setBackground(new Color(255, 130, 14));
        pnlRelacionFiltros.setLayout(null);
        tblMaestro.setFont(new Font("SansSerif", 0, 12));
        tblMaestro.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblMaestro_keyPressed(e);
            }
        });
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(240, 225, 85, 20));
        btnRelacionMaestros.setText("Seleccione el tipo de local venta:");
        btnRelacionMaestros.setBounds(new Rectangle(10, 0, 190, 20));
        btnRelacionMaestros.setMnemonic('r');
        jButtonLabel1.setText("jButtonLabel1");
        jScrollPane1.getViewport();
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblEnter, null);
        jScrollPane1.getViewport().add(tblMaestro, null);
        jContentPane.add(jScrollPane1, null);
        pnlRelacionFiltros.add(btnRelacionMaestros, null);
        jContentPane.add(pnlRelacionFiltros, null);
    }
    
    private void initialize() {
        // INICIALIZA TABLA
        FarmaColumnData columnas[] ={ new FarmaColumnData("Codigo", 50, JLabel.LEFT), 
                                                        new FarmaColumnData("Descripción", 220, JLabel.LEFT)
                                                       };

        tableModel = new FarmaTableModel(columnas, UtilityPtoVenta.obtenerDefaultValuesTabla(columnas.length), 0);
        FarmaUtility.initSimpleList(tblMaestro, tableModel, columnas);
    }
    
    private void this_windowOpened(WindowEvent e){
        FarmaUtility.centrarVentana(this);
        try {
            DBImpresoras.listarTipoLocalVenta(tableModel, FarmaVariables.vCodGrupoCia, FarmaVariables.vCodLocal, ipPc);
        } catch (Exception ex) {
            log.error("", ex);
        }
        if(tblMaestro.getRowCount()>0){
            FarmaUtility.moveFocus(tblMaestro);
        }else{
            FarmaUtility.showMessage(this, "No se ha encontrado informacion para mostrar.\nSi problema persiste, comuniquese con Mesa de Ayuda.", null);
            cerrarVentana(false);
        }
        
    }
    
    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    
    public void setIpPc(String ipPc){
        this.ipPc = ipPc;
    }
    
    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    
    private void tblMaestro_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }
    
    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(actualizarIP()){
                cerrarVentana(true);
            }
        }
    }
    
    private boolean actualizarIP(){
        boolean isActualizo = false;
        if(tblMaestro.getSelectedRow()!=-1){
            String codTipoLocalVenta = FarmaUtility.getValueFieldArrayList(tableModel.data, tblMaestro.getSelectedRow(), 0);
            isActualizo = UtilityAdministracion.actualizarIPTipoLocalVenta(this, ipPc, codTipoLocalVenta);
            
        }else{
            FarmaUtility.showMessage(this, "No ha seleccionado una opción", tblMaestro);
            isActualizo = false;
        }
        return isActualizo;
    }

    
}
