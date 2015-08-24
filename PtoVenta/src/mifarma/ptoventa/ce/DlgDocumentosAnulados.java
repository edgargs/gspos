package mifarma.ptoventa.ce;

import com.gs.mifarma.componentes.JButtonLabel;

import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;

import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import mifarma.ptoventa.administracion.roles.reference.VariablesRolesTmp;
import mifarma.ptoventa.ce.reference.ConstantsCajaElectronica;

import mifarma.ptoventa.ce.reference.DBCajaElectronica;

import mifarma.ptoventa.ce.reference.VariablesCajaElectronica;

import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgDocumentosAnulados extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgDocumentosAnulados.class);

    Frame myParentFrame;

    FarmaTableModel tableModelDocAnulados;

    private JPanelWhite jContentPane = new JPanelWhite();

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelTitle pnlHeaderListaImp = new JPanelTitle();
    private JButtonLabel btnImporte = new JButtonLabel();
    private JButtonLabel btnMonto = new JButtonLabel();
    private JPanelTitle pnlDiferenciaCierreLocal = new JPanelTitle();
    private JLabelFunction lblsc = new JLabelFunction();

    private JScrollPane scrListaImpresoras = new JScrollPane();

    private JTable tblListaDocAnulados = new JTable();

    private JButtonLabel btnRelacionImp = new JButtonLabel();

    
    public DlgDocumentosAnulados(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            if(VariablesRolesTmp.vTipo_Accion.equalsIgnoreCase("T")){
            jbInitCierreTurno();
            }
            if(VariablesRolesTmp.vTipo_Accion.equalsIgnoreCase("D")){
            jbInitCierreDia();
            }
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }
    private void jbInitCierreTurno() throws Exception {
        this.setSize(new Dimension(580, 370));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Documentos Anulados en el Cierre de Turno");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
       
        jContentPane.setLayout(null);
        pnlHeaderListaImp.setBounds(new Rectangle(10, 10, 550, 25));
        
        lblsc.setBounds(new Rectangle(400, 300, 95, 20));
        lblsc.setText("[Esc]Salir");
        btnImporte.setText("Importe Total : S./");
        btnImporte.setBounds(new Rectangle(370, 5, 100, 20));
        btnMonto.setText("0.00");
        btnMonto.setBounds(new Rectangle(500, 5, 100, 20));
        pnlDiferenciaCierreLocal.setBounds(new Rectangle(10, 260, 550, 30));
        pnlDiferenciaCierreLocal.setBackground(new Color(43, 141, 39));
        
        tblListaDocAnulados.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaDocAnulados_keyPressed(e);
            }
        });
        
        scrListaImpresoras.setBounds(new Rectangle(10, 35, 550, 215));
        
        btnRelacionImp.setText(" Documentos Anulados");
        btnRelacionImp.setBounds(new Rectangle(10, 5, 140, 15));
        btnRelacionImp.setMnemonic('r');
       
        
        scrListaImpresoras.getViewport().add(tblListaDocAnulados, null);
        
        jContentPane.add(scrListaImpresoras, null);
        jContentPane.add(lblsc, null);
        pnlDiferenciaCierreLocal.add(btnImporte,null);
        pnlDiferenciaCierreLocal.add(btnMonto,null);
        pnlHeaderListaImp.add(btnRelacionImp, null);
        jContentPane.add(pnlHeaderListaImp, null);
        jContentPane.add(pnlDiferenciaCierreLocal, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        
    }
    private void jbInitCierreDia() throws Exception {
        this.setSize(new Dimension(580, 370));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Documentos Anulados en el Cierre de Día");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
       
        jContentPane.setLayout(null);
        pnlHeaderListaImp.setBounds(new Rectangle(10, 10, 550, 25));
        
        lblsc.setBounds(new Rectangle(400, 300, 95, 20));
        lblsc.setText("[Esc]Salir");
        btnImporte.setText("Importe Total : S./");
        btnImporte.setBounds(new Rectangle(350, 5, 100, 20));
        btnMonto.setText("0.00");
        btnMonto.setBounds(new Rectangle(500, 5, 100, 20));
        pnlDiferenciaCierreLocal.setBounds(new Rectangle(10, 260, 550, 30));
        pnlDiferenciaCierreLocal.setBackground(new Color(43, 141, 39));
        
        tblListaDocAnulados.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaDocAnulados_keyPressed(e);
            }
        });
        
        scrListaImpresoras.setBounds(new Rectangle(10, 35, 550, 215));
        
        btnRelacionImp.setText(" Documentos Anulados");
        btnRelacionImp.setBounds(new Rectangle(10, 5, 140, 15));
        btnRelacionImp.setMnemonic('r');
       
        
        scrListaImpresoras.getViewport().add(tblListaDocAnulados, null);
        
        jContentPane.add(scrListaImpresoras, null);
        jContentPane.add(lblsc, null);
        pnlDiferenciaCierreLocal.add(btnImporte,null);
        pnlDiferenciaCierreLocal.add(btnMonto,null);
        pnlHeaderListaImp.add(btnRelacionImp, null);
        jContentPane.add(pnlHeaderListaImp, null);
        jContentPane.add(pnlDiferenciaCierreLocal, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        
    }
    
    
    
    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTable();
        setMontoTotal();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */
    private void initTable() {
        if(VariablesRolesTmp.vTipo_Accion.equalsIgnoreCase("T")){
        tableModelDocAnulados =
                new FarmaTableModel(ConstantsCajaElectronica.columnsListaAnulados, ConstantsCajaElectronica.defaultValuesListaAnulados,
                                    0);
        FarmaUtility.initSimpleList(tblListaDocAnulados, tableModelDocAnulados,
                                    ConstantsCajaElectronica.columnsListaAnulados);
        }
        if(VariablesRolesTmp.vTipo_Accion.equalsIgnoreCase("D")){
            tableModelDocAnulados =
                    new FarmaTableModel(ConstantsCajaElectronica.columnsListaAnulCierreDia, ConstantsCajaElectronica.defaultValuesListaAnulCierreDia,
                                        0);
            FarmaUtility.initSimpleList(tblListaDocAnulados, tableModelDocAnulados,
                                        ConstantsCajaElectronica.columnsListaAnulCierreDia);   
        }
        
        if(VariablesRolesTmp.vTipo_Accion.equalsIgnoreCase("D")){
        lstDocumentosAnulado();
        }
        if(VariablesRolesTmp.vTipo_Accion.equalsIgnoreCase("T")){
         lstDocAnuladosCierreTurno();
        } 
    }
    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */
    private void btnLista_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocusJTable(tblListaDocAnulados);
    }
    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocusJTable(tblListaDocAnulados);
        FarmaUtility.centrarVentana(this);
    }
    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    
    
    private void lstDocumentosAnulado(){
        try {
                       
            DBCajaElectronica.lstComprobantesAnulados(tableModelDocAnulados,VariablesCajaElectronica.vFechaCierreDia);
            if (tblListaDocAnulados.getRowCount() > 0)
                FarmaUtility.ordenar(tblListaDocAnulados, tableModelDocAnulados, 2, FarmaConstants.ORDEN_DESCENDENTE);
            FarmaUtility.moveFocusJTable(tblListaDocAnulados);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al cargar lista de Documentos Anulados.\n" +
                    sql, tblListaDocAnulados);
        }
    }
    private void lstDocAnuladosCierreTurno(){
        try {
            DBCajaElectronica.lstDocAnuladoCierreTurno(tableModelDocAnulados,VariablesCajaElectronica.vSecMovCaja);
            if (tblListaDocAnulados.getRowCount() > 0)
                FarmaUtility.ordenar(tblListaDocAnulados, tableModelDocAnulados, 2, FarmaConstants.ORDEN_DESCENDENTE);
            FarmaUtility.moveFocusJTable(tblListaDocAnulados);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Error al cargar lista de Documentos Anulados por Cierre de Turno.\n" +
                    sql, tblListaDocAnulados);
        }
        
    }
    private void tblListaDocAnulados_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }
    
    private void chkKeyPressed(KeyEvent e) {

        if (!VariablesPtoVenta.vEjecutaAccionTecla) {
            VariablesPtoVenta.vEjecutaAccionTecla = true;
           
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                cerrarVentana(false);
            } 
            VariablesPtoVenta.vEjecutaAccionTecla = false;
        }

    }
    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    
    private String getMontoTotal(){
        String resultado="";
        double subMonto=0.00;
        double impTotal=0.00;
        
        for (int i = 0; i < tblListaDocAnulados.getRowCount(); i++) {
            
            subMonto = Double.parseDouble(FarmaUtility.getValueFieldJTable(tblListaDocAnulados, i, 4));
    
            impTotal=subMonto+impTotal;
        }
        resultado=FarmaUtility.formatNumber(impTotal, 2);
        return resultado;    
        }
    private void setMontoTotal(){
        btnMonto.setText("");
        btnMonto.setText(this.getMontoTotal());
    }
     
     
  
   
    
   
}