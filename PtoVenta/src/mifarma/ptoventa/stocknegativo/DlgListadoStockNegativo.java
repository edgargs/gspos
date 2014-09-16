package mifarma.ptoventa.stocknegativo;

import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Frame;

import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.text.DecimalFormat;

import java.util.ArrayList;

import java.util.Calendar;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.FarmaColumnData;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.recetario.reference.ConstantsRecetario;
import mifarma.ptoventa.recetario.reference.FacadeRecetario;
import mifarma.ptoventa.stocknegativo.reference.ConstantsStockNegativo;
import mifarma.ptoventa.stocknegativo.reference.FacadeStockNegativo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgListadoStockNegativo extends JDialog
{
    private static final Logger log = LoggerFactory.getLogger(DlgListadoStockNegativo.class);
    
    private JPanelWhite pnlFondo = new JPanelWhite();
    private Frame myParent;
    private CardLayout cardLayout1 = new CardLayout();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblF5 = new JLabelFunction();
    private JLabelWhite lblEstado = new JLabelWhite();
    private JLabelWhite lblSolicitud = new JLabelWhite();
    private JLabelWhite lblSolicitante = new JLabelWhite();
    private JLabelWhite lblQFAprobador = new JLabelWhite();
    private JLabelWhite lblFechaInicio = new JLabelWhite();
    private JLabelWhite lblFechaFin = new JLabelWhite();
    private JPanelHeader pnlHeader = new JPanelHeader();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JLabelWhite lblTitle = new JLabelWhite();
    
    private JTextFieldSanSerif txtSolicitud = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtSolicitante = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaInicio = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtAprobador = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaFin = new JTextFieldSanSerif();
    private JComboBox cmbEstado = new JComboBox();
    
    private FacadeStockNegativo facade = new FacadeStockNegativo();
    private JTable tbl_stockNeg = new JTable();
    private FarmaTableModel ftm_stockNeg;

    public DlgListadoStockNegativo()
    {   this(null, "", false);
    }

    public DlgListadoStockNegativo(Frame parent, String title, boolean modal)
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

    private void jbInit() throws Exception {
        this.setSize(new Dimension(763, 543));
        this.getContentPane().setLayout(cardLayout1);
        this.setTitle("Listado de Solicitudes de Stock Negativo");
        this.setDefaultCloseOperation(0);
        
        pnlFondo.setFocusable(false);
        tbl_stockNeg.setFocusable(false);
        jScrollPane1.setBounds(new Rectangle(10, 150, 735, 330));
        jScrollPane1.setFocusable(false);
        jScrollPane1.getViewport().add(tbl_stockNeg, null);
        
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(630, 485, 115, 30));
        lblEsc.setHorizontalTextPosition(SwingConstants.CENTER);
        lblEsc.setHorizontalAlignment(SwingConstants.CENTER);
        lblEsc.setFocusable(false);
        lblF11.setText("[ F11 ] Buscar");
        lblF11.setBounds(new Rectangle(10, 485, 125, 30));
        lblF11.setHorizontalAlignment(SwingConstants.CENTER);
        lblF11.setHorizontalTextPosition(SwingConstants.CENTER);
        lblF11.setFocusable(false);
        lblEstado.setText("Estado:");
        lblEstado.setBounds(new Rectangle(345, 15, 100, 15));
        lblEstado.setSize(new Dimension(100, 15));
        lblEstado.setFocusable(false);
        lblSolicitud.setText("Num. Solicitud:");
        lblSolicitud.setBounds(new Rectangle(30, 15, 85, 15));
        lblSolicitud.setFocusable(false);
        lblSolicitud.setDisplayedMnemonicIndex(0);
        lblSolicitante.setText("Solicitante:");
        lblSolicitante.setBounds(new Rectangle(30, 45, 85, 15));
        lblSolicitante.setFocusable(false);
        lblSolicitante.setDisplayedMnemonicIndex(0);
        lblQFAprobador.setText("Q.F. Aprobador:");
        lblQFAprobador.setBounds(new Rectangle(345, 45, 100, 15));
        lblQFAprobador.setSize(new Dimension(100, 15));
        lblQFAprobador.setFocusable(false);
        lblQFAprobador.setDisplayedMnemonicIndex(5);
        lblFechaInicio.setText("Fecha Inicio:");
        lblFechaInicio.setBounds(new Rectangle(30, 75, 85, 15));
        lblFechaInicio.setFocusable(false);
        lblFechaInicio.setDisplayedMnemonicIndex(6);
        lblFechaFin.setText("Fecha Fin:");
        lblFechaFin.setBounds(new Rectangle(345, 75, 100, 15));
        lblFechaFin.setFocusable(false);
        lblFechaFin.setDisplayedMnemonicIndex(6);
        pnlHeader.setBounds(new Rectangle(10, 10, 735, 110));
        pnlHeader.setLayout(null);

        pnlHeader.setFocusable(false);
        txtSolicitud.setBounds(new Rectangle(120, 15, 105, 20));
        txtSolicitud.setLengthText(10);

        txtSolicitud.setNextFocusableComponent(cmbEstado);
        txtSolicitud.setFocusAccelerator('n');
        txtSolicitud.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtSolicitud_keyPressed(e);
                }

                public void keyTyped(KeyEvent e) {
                    txtSolicitud_keyTyped(e);
                }
            });
        txtSolicitante.setBounds(new Rectangle(120, 45, 150, 20));
        txtSolicitante.setLengthText(15);

        txtSolicitante.setNextFocusableComponent(txtAprobador);
        txtSolicitante.setFocusAccelerator('s');
        txtSolicitante.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtSolicitante_keyPressed(e);
                }
            });
        txtFechaInicio.setBounds(new Rectangle(120, 75, 110, 20));
        txtFechaInicio.setLengthText(10);

        txtFechaInicio.setNextFocusableComponent(txtFechaFin);
        txtFechaInicio.setFocusAccelerator('i');
        txtFechaInicio.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtFechaInicio_keyPressed(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtFechaInicio_keyReleased(e);
                }
            });
        cmbEstado.setBounds(new Rectangle(445, 15, 125, 20));

        cmbEstado.setNextFocusableComponent(txtSolicitante);
        cmbEstado.addItem("Todos");
        cmbEstado.addItem("Generado");
        cmbEstado.addItem("Resuelto");
        
        cmbEstado.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    cmdEstado_keyPressed(e);
                }
            });
        lblTitle.setText("Listado de Solicitudes de Stock Negativo");
        lblTitle.setBounds(new Rectangle(30, 0, 405, 25));
        pnlTitle.setBounds(new Rectangle(10, 125, 735, 25));
        txtAprobador.setBounds(new Rectangle(445, 45, 155, 20));
        txtAprobador.setLengthText(15);

        txtAprobador.setNextFocusableComponent(txtFechaInicio);
        txtAprobador.setFocusAccelerator('a');
        txtAprobador.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtAprobador_keyPressed(e);
                }
            });
        txtFechaFin.setBounds(new Rectangle(445, 75, 110, 20));
        txtFechaFin.setSize(new Dimension(110, 20));
        txtFechaFin.setLengthText(10);

        txtFechaFin.setNextFocusableComponent(txtSolicitud);
        txtFechaFin.setFocusAccelerator('f');
        txtFechaFin.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtFechaFin_keyPressed(e);
                }

                public void keyReleased(KeyEvent e) {
                    txtFechaFin_keyReleased(e);
                }
            });
        lblF5.setText("[ F5 ] Ver detalle");
        lblF5.setBounds(new Rectangle(155, 485, 125, 30));
        lblF5.setHorizontalAlignment(SwingConstants.CENTER);
        lblF5.setHorizontalTextPosition(SwingConstants.CENTER);
        lblF5.setFocusable(false);
        pnlTitle.add(lblTitle, null);
        pnlFondo.add(pnlTitle, null);
        pnlFondo.add(lblF5, null);
        pnlFondo.add(pnlHeader, null);
        pnlFondo.add(lblF11, null);
        pnlFondo.add(lblEsc, null);
        pnlFondo.add(jScrollPane1, null);
        pnlHeader.add(txtFechaFin, null);
        pnlHeader.add(txtAprobador, null);
        pnlHeader.add(cmbEstado, null);
        pnlHeader.add(txtFechaInicio, null);
        pnlHeader.add(txtSolicitante, null);
        pnlHeader.add(txtSolicitud, null);
        pnlHeader.add(lblSolicitud, null);
        pnlHeader.add(lblEstado, null);
        pnlHeader.add(lblSolicitante, null);
        pnlHeader.add(lblQFAprobador, null);
        pnlHeader.add(lblFechaInicio, null);
        pnlHeader.add(lblFechaFin, null);
        this.getContentPane().add(pnlFondo, "pnlFondo");
        this.setResizable(false);
        FarmaUtility.centrarVentana(this);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */
    private void initialize()
    {   ftm_stockNeg = new FarmaTableModel(ConstantsStockNegativo.columnsListaStockNeg,
                                            ConstantsStockNegativo.defaultListaStockNeg,
                                            0);
        FarmaUtility.initSimpleList(tbl_stockNeg, 
                                    ftm_stockNeg,
                                    ConstantsStockNegativo.columnsListaStockNeg);
        
        //se indica la fecha final como el presente dia, y la inicial dos meses antes
        DecimalFormat f = new DecimalFormat("00");
        Calendar c = Calendar.getInstance();
        String fecFin = f.format(c.get(Calendar.DAY_OF_MONTH)) + "/" + 
                        f.format(c.get(Calendar.MONTH)+1) + "/" + 
                        c.get(Calendar.YEAR);
        c.add(Calendar.MONTH, -2);
        String fecIni = f.format(c.get(Calendar.DAY_OF_MONTH)) + "/" + 
                        f.format(c.get(Calendar.MONTH)+1) + "/" + 
                        c.get(Calendar.YEAR);
        
        txtFechaInicio.setText(fecIni);
        txtFechaFin.setText(fecFin);
        cmbEstado.setSelectedIndex(1);
        
        listarSolicitudes();
        tbl_stockNeg.grabFocus();
    }
    
    /* ************************************************************************ */
    /*                               Metodos de consulta                        */
    /* ************************************************************************ */
    
    private void listarSolicitudes()
    {   ArrayList<String> tmpArrayRow = null;
        ftm_stockNeg.clearTable();
        String tmpEst="";
        String estSel = cmbEstado.getSelectedItem().toString();
        
        if("Generado".equalsIgnoreCase(estSel))
            tmpEst = "G";
        else if("Resuelto".equalsIgnoreCase(estSel))
            tmpEst = "R";
        
        ArrayList<ArrayList<String>> resultado = facade.listadoSolStockNeg(txtSolicitud.getText(), 
                                                                           tmpEst, 
                                                                           txtSolicitante.getText(), 
                                                                           txtAprobador.getText(), 
                                                                           txtFechaInicio.getText(), 
                                                                           txtFechaFin.getText());
        for(int i = 0; resultado.size() > i; i++)
        {
            tmpArrayRow = new ArrayList<String>();
            ArrayList t = resultado.get(i);

            String tmpCodLocal      = t.get(0).toString().trim();
            String tmpIdSolicitud   = t.get(1).toString().trim();
            String tmpCodEstado     = t.get(2).toString().trim();
            String tmpCantItems     = t.get(3).toString().trim();
            String tmpUsuSolicitud  = t.get(4).toString().trim();
            String tmpUsuAprobador  = t.get(5).toString().trim();
            String tmpFecSolicitud  = t.get(6).toString().trim();
            
            tmpArrayRow.add(tmpCodLocal);
            tmpArrayRow.add(tmpIdSolicitud);
            tmpArrayRow.add(tmpCodEstado);
            tmpArrayRow.add(tmpCantItems);
            tmpArrayRow.add(tmpUsuSolicitud);
            tmpArrayRow.add(tmpUsuAprobador);
            tmpArrayRow.add(tmpFecSolicitud);
            
            ftm_stockNeg.insertRow(tmpArrayRow);
        }
        //Cuando se realiza una busqueda, si existe algun registro, se selecciona la primera fila
        if(tbl_stockNeg.getRowCount()>0 && tbl_stockNeg.getSelectedRow() == -1)
            tbl_stockNeg.setRowSelectionInterval(0, 0);
    }
    
    private void verDetalle(KeyEvent e)
    {   int rowTemp = tbl_stockNeg.getSelectedRow();
        
        String codSol = FarmaUtility.getValueFieldJTable(tbl_stockNeg,rowTemp,1);
        String estado = FarmaUtility.getValueFieldJTable(tbl_stockNeg,rowTemp,2);
        String fecha = FarmaUtility.getValueFieldJTable(tbl_stockNeg,rowTemp,6);
        
        DlgDetalleStockNegativo dlgDetalleStockNegativo = new DlgDetalleStockNegativo(myParent,"",true);
        dlgDetalleStockNegativo.setValores(codSol, estado, fecha);
        dlgDetalleStockNegativo.setVisible(true);
        listarSolicitudes();
        
        //luego de cerrar la ventana buscar el texto del registro modificado para ubicarse en la misma fila
        buscarTextoTabla(codSol,1);
    }
    
    private void buscarTextoTabla(String texto, int columna)
    {
        String vDescrip = "";
        for (int k = 0; k < tbl_stockNeg.getRowCount(); k++)
        {
            vDescrip = ((String)tbl_stockNeg.getValueAt(k, columna)).toUpperCase().trim();
            if (vDescrip.length() >= texto.length())
            {
                vDescrip = vDescrip.substring(0, texto.length());
                if (texto.equalsIgnoreCase(vDescrip))
                {
                    tbl_stockNeg.setRowSelectionInterval(k, k);
                    break;
                }
            }
        }
    }
    
    private void cerrarVentana(boolean pAceptar)
    {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }
    
    private boolean validar()
    {   
        boolean flag = true;

        Calendar fecha_inicial = Calendar.getInstance();
        Calendar fecha_final = (Calendar)fecha_inicial.clone();
        
        if(((txtFechaInicio.getText()==null || txtFechaInicio.getText().trim().equals("")) &&
            (txtFechaFin.getText()!=null && !txtFechaFin.getText().trim().equals(""))) ||
           ((txtFechaInicio.getText()!=null && !txtFechaInicio.getText().trim().equals("")) &&
            (txtFechaFin.getText()==null || txtFechaFin.getText().trim().equals(""))))
        {   
            FarmaUtility.showMessage(this, "ATENCIÓN: Para realizar busquedas de fecha \ndeberá llenar los dos campos de fecha o ninguno", null);
            flag=false;
        }
        if(flag)
        {
            if(txtFechaInicio.getText()!=null && !txtFechaInicio.getText().trim().equals(""))
            {   try
                {   fecha_inicial.setTime(FarmaUtility.getStringToDate(txtFechaInicio.getText().trim(),
                                                                         "dd/MM/yyyy"));
                }
                catch(Exception ex)
                {   //si no se puede obtener un Date, la fecha es incorrecta
                    FarmaUtility.showMessage(this, "ERROR: La fecha inicial no es valida", null);
                    flag=false;
                }
            }
        }
        if(flag)
        {   if(txtFechaFin.getText()!=null && !txtFechaFin.getText().trim().equals(""))
            {   try
                {   fecha_final.setTime(FarmaUtility.getStringToDate(txtFechaFin.getText().trim(),
                                                                         "dd/MM/yyyy"));
                }
                catch(Exception ex)
                {   //si no se puede obtener un Date, la fecha es incorrecta
                    FarmaUtility.showMessage(this, "ERROR: La fecha final no es valida", null);
                    flag=false;
                }
            }
        }
        if(flag)
        {   if(FarmaUtility.diferenciaEnDias(fecha_inicial, fecha_final) > 0)
            {   flag=false;
                FarmaUtility.showMessage(this, 
                                        "ERROR: La fecha inicial no debe ser mayor a la fecha final", 
                                        null);
            }
        }
        return flag;
    }
    
    // **************************************************************************
    //                             Metodos auxiliares de eventos
    // **************************************************************************
    private void chkKeyPressed(KeyEvent e)
    {
        if(!e.getSource().equals(cmbEstado))
            FarmaGridUtils.aceptarTeclaPresionada(e, tbl_stockNeg, null, 0);
        
        if(e.getKeyCode() == KeyEvent.VK_F11)
        {   if(validar())
                listarSolicitudes();
        }
        else if(e.getKeyCode() == KeyEvent.VK_F5)
        {   verDetalle(e);
        }
        else if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {   ((JComponent)e.getSource()).transferFocus();
        }
        else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {   cerrarVentana(false);
        }
    }

    private void txtSolicitud_keyPressed(KeyEvent e)
    {   chkKeyPressed(e);
    }

    private void txtSolicitante_keyPressed(KeyEvent e)
    {   chkKeyPressed(e);
    }

    private void txtFechaInicio_keyPressed(KeyEvent e)
    {   chkKeyPressed(e);
    }

    private void txtAprobador_keyPressed(KeyEvent e)
    {   chkKeyPressed(e);
    }

    private void txtFechaFin_keyPressed(KeyEvent e)
    {   chkKeyPressed(e);
    }

    private void cmdEstado_keyPressed(KeyEvent e)
    {   chkKeyPressed(e);
    }

    private void txtFechaInicio_keyReleased(KeyEvent e)
    {   FarmaUtility.dateComplete(txtFechaInicio, e);
    }

    private void txtFechaFin_keyReleased(KeyEvent e)
    {   FarmaUtility.dateComplete(txtFechaFin, e);
    }

    private void txtSolicitud_keyTyped(KeyEvent e)
    {   FarmaUtility.admitirDigitos(txtSolicitud, e);
    }
}
