package mifarma.ptoventa.stocknegativo;

import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Frame;

import java.awt.Rectangle;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.event.WindowAdapter;

import java.awt.event.WindowEvent;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.DlgMovimientoKardex;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.stocknegativo.reference.ConstantsStockNegativo;
import mifarma.ptoventa.stocknegativo.reference.FacadeStockNegativo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgDetalleStockNegativo extends JDialog
{
    
    private static final Logger log = LoggerFactory.getLogger(DlgDetalleStockNegativo.class);
    
    private Frame myParent;
    
    private String codSol;
    private String estado;
    private String fecha;
    
    private JPanelWhite pnlFondo = new JPanelWhite();
    
    private CardLayout cardLayout1 = new CardLayout();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JPanelHeader pnlHeader = new JPanelHeader();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelWhite lblCodSol = new JLabelWhite();
    private JLabelWhite lblDatoCodSol = new JLabelWhite();
    private JLabelWhite lblEstado = new JLabelWhite();
    private JLabelWhite lblDatoEstado = new JLabelWhite();
    private JLabelWhite lblFecha = new JLabelWhite();
    private JLabelWhite lblDatoFecha = new JLabelWhite();
    private JLabelWhite lblTitle = new JLabelWhite();
    
    private FacadeStockNegativo facade = new FacadeStockNegativo();
    private JTable tbl_detStockNeg = new JTable();
    private FarmaTableModel ftm_detStockNeg;
    
    public DlgDetalleStockNegativo()
    {   this(null, "", false);
    }

    public DlgDetalleStockNegativo(Frame parent, String title, boolean modal)
    {
        super(parent, title, modal);
        myParent = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("",e);
        }
    }
    
    public void setValores(String codSol, String estado, String fecha)
    {   this.codSol = codSol;
        this.estado = estado;
        this.fecha = fecha;
        
        lblDatoCodSol.setText(codSol);
        lblDatoEstado.setText(estado);
        lblDatoFecha.setText(fecha);
    }

    private void jbInit() throws Exception
    {
        this.setSize(new Dimension(775, 374));
        this.getContentPane().setLayout(cardLayout1);
        this.setTitle("Detalle de Solicitud de Stock Negativo");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened(WindowEvent e) {
                    this_windowOpened(e);
                }
            });
        pnlFondo.setFocusable(false);
        jScrollPane1.setBounds(new Rectangle(5, 110, 760, 200));
        lblEsc.setText("[ Esc ] Cerrar");
        lblEsc.setBounds(new Rectangle(635, 315, 130, 30));
        lblEsc.setHorizontalAlignment(SwingConstants.CENTER);
        lblEsc.setHorizontalTextPosition(SwingConstants.CENTER);
        lblEsc.setFocusable(false);
        lblF11.setText("[ F11 ] Sel. Producto");
        lblF11.setBounds(new Rectangle(5, 315, 145, 30));
        lblF11.setFocusable(false);
        lblF11.setEnabled(false);
        
        lblCodSol.setText("Num. Solicitud:");
        lblCodSol.setBounds(new Rectangle(30, 5, 95, 20));
        lblDatoCodSol.setText("-");
        lblDatoCodSol.setBounds(new Rectangle(130, 5, 125, 20));
        lblEstado.setText("Estado:");
        lblEstado.setBounds(new Rectangle(30, 35, 95, 20));
        lblDatoEstado.setText("-");
        lblDatoEstado.setBounds(new Rectangle(130, 35, 110, 20));
        lblFecha.setText("Fecha:");
        lblFecha.setBounds(new Rectangle(365, 35, 65, 20));

        lblDatoFecha.setText("-");
        lblDatoFecha.setBounds(new Rectangle(435, 35, 105, 20));
        lblTitle.setText("Listado de Productos anexos a la solicitud");
        lblTitle.setBounds(new Rectangle(25, 0, 370, 25));
        pnlTitle.setBounds(new Rectangle(5, 85, 760, 25));
        lblF1.setText("[ F1 ] Ver Kardex de Producto");
        lblF1.setBounds(new Rectangle(165, 315, 190, 30));
        pnlHeader.setBounds(new Rectangle(5, 10, 760, 70));
        jScrollPane1.getViewport().add(tbl_detStockNeg, null);
        tbl_detStockNeg.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e)
                {   tbl_detStockNeg_keyPressed(e);
                }
                
                public void keyReleased(KeyEvent e)
                {   tbl_detStockNeg_keyReleased(e);
                }
            });

        pnlHeader.add(lblDatoFecha, null);
        pnlHeader.add(lblFecha, null);
        pnlHeader.add(lblDatoEstado, null);
        pnlHeader.add(lblEstado, null);
        pnlHeader.add(lblDatoCodSol, null);
        pnlHeader.add(lblCodSol, null);
        pnlTitle.add(lblTitle, null);
        pnlFondo.add(pnlTitle, null);
        pnlFondo.add(lblF1, null);
        pnlFondo.add(pnlHeader, null);
        pnlFondo.add(lblF11, null);
        pnlFondo.add(lblEsc, null);
        pnlFondo.add(jScrollPane1, null);
        this.getContentPane().add(pnlFondo, "pnlFondo");
        this.setResizable(false);
        FarmaUtility.centrarVentana(this);
    }
    
    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */
    private void initialize()
    {   ftm_detStockNeg = new FarmaTableModel(ConstantsStockNegativo.columnsListaDetStockNeg,
                                            ConstantsStockNegativo.defaultListaDetStockNeg,
                                            0);
        FarmaUtility.initSimpleList(tbl_detStockNeg, 
                                    ftm_detStockNeg,
                                    ConstantsStockNegativo.columnsListaDetStockNeg);
        tbl_detStockNeg.grabFocus();
    }
    
    /* ************************************************************************ */
    /*                               Metodos de consulta                        */
    /* ************************************************************************ */
    
    private void listarDetSolicitudes()
    {   ArrayList<String> tmpArrayRow = null;
        ftm_detStockNeg.clearTable();
        ArrayList<ArrayList<String>> resultado = facade.listadoDetStockNeg(this.codSol);
        for(int i = 0; resultado.size() > i; i++)
        {
            tmpArrayRow = new ArrayList<String>();
            ArrayList tmp = resultado.get(i);
            String tmpCodProd  = tmp.get(0).toString().trim();
            String tmpDescProd = tmp.get(1).toString().trim();
            String tmpCantProd = tmp.get(2).toString().trim();

            String tmpUnidMed = tmp.get(3).toString().trim();
            String tmpStock = tmp.get(4).toString().trim();
            String tmpProdCruz = (tmp.get(5)==null) ? " " : tmp.get(5).toString().trim();
            String tmpProdCruzDesc = (tmp.get(6)==null) ? " " : tmp.get(6).toString().trim();
            
            tmpArrayRow.add(tmpCodProd);
            tmpArrayRow.add(tmpDescProd);
            tmpArrayRow.add(tmpCantProd);

            tmpArrayRow.add(tmpUnidMed);
            tmpArrayRow.add(tmpStock);
            tmpArrayRow.add(tmpProdCruz);
            tmpArrayRow.add(tmpProdCruzDesc);
            
            ftm_detStockNeg.insertRow(tmpArrayRow);
        }
        //Cuando se realiza una busqueda, si existe algun registro, se selecciona la primera fila
        if(tbl_detStockNeg.getRowCount()>0 && tbl_detStockNeg.getSelectedRow() == -1)
            tbl_detStockNeg.setRowSelectionInterval(0, 0);
        verificarRegularizar();
    }
    
    private void regularizar()
    {   
        String codProdAnt = FarmaUtility.getValueFieldJTable(tbl_detStockNeg, 
                                                            tbl_detStockNeg.getSelectedRow(),
                                                            0);
        String cant = FarmaUtility.getValueFieldJTable(tbl_detStockNeg, 
                                                        tbl_detStockNeg.getSelectedRow(),
                                                        2);
        String numSol = lblDatoCodSol.getText().trim();
        
        DlgRegularizarStockNegativo dlgRegularizarStockNegativo = new DlgRegularizarStockNegativo(myParent, "", true);
        dlgRegularizarStockNegativo.setValores(Integer.parseInt(cant));
        dlgRegularizarStockNegativo.setVisible(true);
        
        if(FarmaVariables.vAceptar)
        {   
            String codProd = dlgRegularizarStockNegativo.getCodProdSelecc();
            
            FacadeStockNegativo facade = new FacadeStockNegativo();
            String res = facade.regularizar(codProd, codProdAnt, cant, numSol);
            if("0".equalsIgnoreCase(res))
            {   FarmaUtility.showMessage(this, "Se regularizo correctamente la solicitud", this);
                listarDetSolicitudes();
            }
            else
            {   FarmaUtility.showMessage(this, "ERROR: No se pudo regularizar correctamente la solicitud. Intentelo nuevamente", this);
            }
        }
    }
    
    private void verificarRegularizar()
    {   //Si existe un producto cruzado, desabilitar el boton F11
        String codProdReg = FarmaUtility.getValueFieldJTable(tbl_detStockNeg, 
                                                             tbl_detStockNeg.getSelectedRow(),
                                                             5);
        if(codProdReg != null && !"".equalsIgnoreCase(codProdReg.trim()))
            lblF11.setEnabled(false);
        else
            lblF11.setEnabled(true);
    }
    
    private void verKardex()
    {   cargarRegistroSeleccionado();
        DlgMovimientoKardex dlgMovimientoKardex = new DlgMovimientoKardex(myParent, "", true);
        dlgMovimientoKardex.setVisible(true);
    }
    
    private void cargarRegistroSeleccionado()
    {
        String codProdAnt = FarmaUtility.getValueFieldJTable(tbl_detStockNeg, 
                                                            tbl_detStockNeg.getSelectedRow(),
                                                            0);
        FacadeStockNegativo facade = new FacadeStockNegativo();
        ArrayList<ArrayList<String>> resultado = facade.listarKardex(codProdAnt);
        
        for(int i = 0; resultado.size() > i; i++)
        {
            ArrayList tmp = resultado.get(i);
            VariablesInventario.vCodProd =  tmp.get(0).toString().trim();
            VariablesInventario.vDescProd = tmp.get(1).toString().trim();
            VariablesInventario.vDescUnidPresent = tmp.get(2).toString().trim();
            VariablesInventario.vNomLab = tmp.get(3).toString().trim();
            VariablesInventario.vCant = tmp.get(4).toString().trim();
            VariablesInventario.vCantFrac = tmp.get(5).toString().trim();
            VariablesInventario.vFrac = tmp.get(6).toString().trim();
            VariablesInventario.vIndProdVirtual = tmp.get(7).toString().trim();
        }
    }
    
    private void cerrarVentana(boolean pAceptar)
    {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    
    //**************************************************************************
    //                        Metodos auxiliares de eventos
    //**************************************************************************
    private void chkKeyPressed(KeyEvent e)
    {
        //FarmaGridUtils.aceptarTeclaPresionada(e, tbl_detStockNeg, null, 0);
        
        if(e.getKeyCode() == KeyEvent.VK_F1)
        {   verKardex();
        }
        if(e.getKeyCode() == KeyEvent.VK_F11 && lblF11.isEnabled())
        {   regularizar();
        }
        else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {   cerrarVentana(false);
        }
    }
    
    private void tbl_detStockNeg_keyPressed(KeyEvent e)
    {   chkKeyPressed(e);
    }
    
    private void tbl_detStockNeg_keyReleased(KeyEvent e)
    {   verificarRegularizar();
    }

    private void this_windowOpened(WindowEvent e)
    {   listarDetSolicitudes();
    }
}