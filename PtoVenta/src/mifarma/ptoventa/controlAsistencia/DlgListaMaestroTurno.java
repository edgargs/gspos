package mifarma.ptoventa.controlAsistencia;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.controlAsistencia.reference.ConstantsControlAsistencia;
import mifarma.ptoventa.controlAsistencia.reference.UtilityControlAsistencia;
import mifarma.ptoventa.controlAsistencia.grafico.UtilGraficoHorario;
import mifarma.ptoventa.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2015 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgListaMaestroTurno.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * CHUANES                    Creación<br>
 * EMAQUERA      15.10.2015   Modificacion<br>
 * <br>
 * @author CHUANES <br>
 * @version 1.0<br>
 *
 */
public class DlgListaMaestroTurno extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaMaestroTurno.class);
    @SuppressWarnings("compatibility:398606826956210138")
    private static final long serialVersionUID = 1L;
    Frame myParentFrame;
    FarmaTableModel tableModel;
    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelTitle pnlHeaderListaTurno = new JPanelTitle();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JScrollPane scrListaTurnos = new JScrollPane();
    private JTable tblListaTurno = new JTable();
    private JButtonLabel btnTurnos = new JButtonLabel();
    private JLabelFunction  lblAsignar=new JLabelFunction();
    private JButtonLabel btnRandoFec = new JButtonLabel();
    private JButton btnBuscar = new JButton();
    private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();
    private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
     TableRowSorter<TableModel> sorter=null; 
    private JTabbedPane jTabbedPane1 = new JTabbedPane();
    private JPanel pnlGrafico = new JPanel();
    private GridLayout gridLayout1 = new GridLayout();
    private CardLayout cardLayout = new CardLayout();
    
    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListaMaestroTurno() {
        this(null, "", false);
    }

    public DlgListaMaestroTurno(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(775, 520));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Asignación de Turno");
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
        pnlHeaderListaTurno.setBounds(new Rectangle(10, 65, 495, 25));
        lblAsignar.setBounds(new Rectangle(15, 425, 130, 40));
        String texto = "<html><body>[F5] Agregar<br> Turno Local</body></body></html>" ;

        lblAsignar.setText(texto);
        lblEsc.setBounds(new Rectangle(405, 425, 100, 40));
        String textoEscape = "<html><body>[Esc]Salir </body></body></html>" ;

        lblEsc.setText(textoEscape);
        scrListaTurnos.setBounds(new Rectangle(10, 95, 495, 325));
        tblListaTurno.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblListaTurno_keyPressed(e);
            }
        });
        btnTurnos.setText("Listado de Turnos");
        btnTurnos.setBounds(new Rectangle(10, 5, 140, 15));
        btnTurnos.setMnemonic('n');
        btnTurnos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnTurnos_actionPerformed(e);
            }
        });
        
        pnlCriterioBusqueda.setBounds(new Rectangle(10, 10, 495, 50));
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(475, 15, 95, 20));
        btnBuscar.setMnemonic('b');
        btnBuscar.setFont(new Font("SansSerif", 1, 11));
        btnBuscar.setFocusPainted(false);
       
        txtBuscar.setBounds(new Rectangle(220, 15, 200, 19));
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
               txtBuscar_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                    txtBuscar_keyReleased(e);
                }
           
        });
        txtBuscar.setDocument(new FarmaLengthText(11));
       
        btnRandoFec.setText("Ingresar turno:");
        btnRandoFec.setBounds(new Rectangle(110, 15, 100, 20));
        btnRandoFec.setMnemonic('t');
        btnRandoFec.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                       btnBuscar_actionPerformed(e);
                    }
                });
        pnlCriterioBusqueda.add(txtBuscar, null);
      
        pnlCriterioBusqueda.add(btnRandoFec, null);
        scrListaTurnos.getViewport().add(tblListaTurno, null);
        jContentPane.add(pnlCriterioBusqueda, null);
        jContentPane.add(scrListaTurnos, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblAsignar,null);
        pnlHeaderListaTurno.add(btnTurnos, null);
        jContentPane.add(pnlHeaderListaTurno, null);

        jTabbedPane1.addTab("Gráfico", pnlGrafico);
        jContentPane.add(jTabbedPane1, null);
        jTabbedPane1.setBounds(new Rectangle(510, 5, 250, 460));
        pnlGrafico.setLayout(gridLayout1);

        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;        
        initTable();
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTable() {
        tableModel =new FarmaTableModel(ConstantsControlAsistencia.columnsListaTurno, ConstantsControlAsistencia.defaultValuescolumnsListaTurno,0);
        FarmaUtility.initSimpleList(tblListaTurno, tableModel, ConstantsControlAsistencia.columnsListaTurno);
        listaMaestroTurno();
        sorter = new TableRowSorter<TableModel>(tableModel);
        tblListaTurno.setRowSorter(sorter);
        tblListaTurno.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cargaPorDefecto();      
    }
    
    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        VariablesPtoVenta.vEjecutaAccionTecla = false;
        FarmaUtility.centrarVentana(this);
       FarmaUtility.moveFocus(txtBuscar);
        
    }
    
    private void btnBuscar_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtBuscar);
    }
    
    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void txtBuscar_keyPressed(KeyEvent e) {
      
        if(e.getKeyCode()==KeyEvent.VK_UP ||e.getKeyCode()==KeyEvent.VK_DOWN)
        {
            FarmaGridUtils.aceptarTeclaPresionada(e, tblListaTurno, txtBuscar, 1); 
        } else{
            chkKeyPressed(e);
        }
    }
    
    private void txtBuscar_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblListaTurno, txtBuscar, 1);
        actualizaGrafico(tblListaTurno.getSelectedRow());        
    }
   
    private void chkKeyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_F5){
            asignarTurnoLocal();    
        }else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }  
    }

    private void btnTurnos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaTurno);       
    }

    
    private void this_windowClosing(WindowEvent e) {
       
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
       
    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void listaMaestroTurno(){
        UtilityControlAsistencia facade=new UtilityControlAsistencia();
               tableModel.data=facade.lstTurnoLocalTurno();
          if (tblListaTurno.getRowCount() > 0){
              
            log.debug("se cargo el maestro de turno");
          }
    }

    private void tblListaTurno_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
        actualizaGrafico(tblListaTurno.getSelectedRow());
    }

    public void asignarTurnoLocal(){
        String resultado="";
        int fila=tblListaTurno.getSelectedRow();
        String codigo="";
        String nomTurno="";
        if (fila>-1) {
            codigo=FarmaUtility.getValueFieldArrayList(tableModel.data,fila,0).trim();
            nomTurno=FarmaUtility.getValueFieldArrayList(tableModel.data,fila,1).trim();
            if (JConfirmDialog.rptaConfirmDialogDefaultNo(this, "¿Desea asignar (o agregar) un nuevo turno al local?")) {
                resultado=UtilityControlAsistencia.asignarTurnoLocalTurno(codigo);
                if(resultado.equalsIgnoreCase(FarmaConstants.INDICADOR_N)){
                    FarmaUtility.showMessage(this,"El turno seleccionado ya fue asignado a local ",tblListaTurno);   
                }
                if(resultado.equalsIgnoreCase(FarmaConstants.INDICADOR_S)){
                    FarmaUtility.aceptarTransaccion();
                    FarmaUtility.showMessage(this,"Se asigno el turno seleccionado al local.",tblListaTurno);    
                    cerrarVentana(true);
                }
            }
        }else{
            FarmaUtility.showMessage(this,"Seleccionar un registro.!" ,tblListaTurno);      
        }
    } 

    private void actualizaGrafico(int i) {
        UtilGraficoHorario vBorrado = new UtilGraficoHorario();
        ArrayList vLista = new ArrayList();
        vBorrado.setData(vLista);
        pnlGrafico.removeAll();
        pnlGrafico.setLayout(cardLayout);
        pnlGrafico.add(vBorrado.getGraficoHorario());        
        pnlGrafico.revalidate();
        
        
        if(i>=0){
            pnlGrafico.removeAll();
            
            UtilGraficoHorario vUtilGui = new UtilGraficoHorario();
            ArrayList vList = new ArrayList();
            vList.add(tableModel.data.get(i));
            vUtilGui.setData(vList);
            pnlGrafico.setLayout(cardLayout);
            pnlGrafico.add(vUtilGui.getGraficoHorario());
        }
    }

    private void cargaPorDefecto() {
        if(tblListaTurno.getRowCount()>0){
        Rectangle rect = tblListaTurno.getCellRect(0, 0, true);
        tblListaTurno.scrollRectToVisible(rect);
        tblListaTurno.clearSelection();
        tblListaTurno.setRowSelectionInterval(0, 0);
            actualizaGrafico(0);
        }
    }
}
