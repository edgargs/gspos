package mifarma.ptoventa.delivery;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.delivery.reference.ConstantsDelivery;
import mifarma.ptoventa.delivery.reference.DBDelivery;
import mifarma.ptoventa.delivery.reference.VariablesDelivery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgListaClienteBusqueda extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaClienteBusqueda.class);

    private Frame myParentFrame;
    private FarmaTableModel tableModel;
    private GridLayout gridLayout1 = new GridLayout();
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblClientesBusqueda = new JTable();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JLabelFunction jLabelFunction2 = new JLabelFunction();

    public DlgListaClienteBusqueda() {
        this(null, "", false);
    }

    public DlgListaClienteBusqueda(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(391, 255));
        this.getContentPane().setLayout(gridLayout1);
        this.setTitle("Listado Clientes");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        jScrollPane1.setBounds(new Rectangle(10, 10, 365, 180));
        tblClientesBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblClientesBusqueda_keyPressed(e);
            }
        });
        jLabelFunction1.setBounds(new Rectangle(150, 200, 125, 20));
        jLabelFunction1.setText("[ Enter ] Seleccionar");
        jLabelFunction2.setBounds(new Rectangle(290, 200, 85, 20));
        jLabelFunction2.setText("[ ESC ] Cerrar");
        jScrollPane1.getViewport().add(tblClientesBusqueda, null);
        jPanelWhite1.add(jLabelFunction2, null);
        jPanelWhite1.add(jLabelFunction1, null);
        jPanelWhite1.add(jScrollPane1, null);
        this.getContentPane().add(jPanelWhite1, null);
    }

    private void initialize() {
        initTable();
        FarmaVariables.vAceptar = false;
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        cargaClientes();
    }

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsDelivery.columnsListaBusquedaApellido, ConstantsDelivery.defaultValuesListaBusquedaApellido,
                                    0);
        FarmaUtility.initSimpleList(tblClientesBusqueda, tableModel, ConstantsDelivery.columnsListaBusquedaApellido);
        //cargaListaClientesBusqueda();
    }

    private void cargaListaClientesBusqueda() {
        ArrayList myArray = new ArrayList();
        myArray.add("Ameghino Rojas Paulo Cesar ");
        myArray.add("42957766");
        tableModel.insertRow(myArray);
        myArray = new ArrayList();
        myArray.add("Ameghino Rojas Pamela ");
        myArray.add("42632545");
        tableModel.insertRow(myArray);
        myArray = new ArrayList();
        myArray.add("Ameghino Moreno Julio Daniel");
        myArray.add("06227718");
        tableModel.insertRow(myArray);
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            guardaRegistroCliente();
        } /*else if(e.getKeyCode() == KeyEvent.VK_F3){
      VariablesDelivery.vTipoAccionInsertarSoloCliente = ConstantsDelivery.ACCION_INSERTAR_SOLO_CLIENTE;
      if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,"¿Desea grabar el cliente en este momento?")) {
        muestraMantCliente();
      } else cerrarVentana(false);

      if(FarmaVariables.vAceptar)
      {
        cerrarVentana(true);
      }
    } */
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void tblClientesBusqueda_keyPressed(KeyEvent e) {
        chkKeyPressed(e);

    }

    public void cargaClientes() {
        try {
            log.debug(VariablesDelivery.vDni_Apellido_Busqueda);
            log.debug(VariablesDelivery.vTipoBusqueda);
            DBDelivery.cargaListaClientes(tableModel, VariablesDelivery.vDni_Apellido_Busqueda,
                                          VariablesDelivery.vTipoBusqueda);
            FarmaUtility.ordenar(tblClientesBusqueda, tableModel, 2, FarmaConstants.ORDEN_ASCENDENTE);
            if (tblClientesBusqueda.getRowCount() <= 0) {
                FarmaUtility.showMessage(this, "No se encontro ningun Cliente para esta Busqueda",
                                         tblClientesBusqueda);
                cerrarVentana(false);
                /*VariablesDelivery.vTipoAccionInsertarSoloCliente = ConstantsDelivery.ACCION_INSERTAR_SOLO_CLIENTE;
        log.debug(VariablesDelivery.vTipoAccionInsertarSoloCliente);
        if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,"¿Desea grabar el cliente en este momento?")) {
            muestraMantCliente();
        } else cerrarVentana(false);*/
            }
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Error al listar los Clientes", null);
            cerrarVentana(false);
        }
    }

    private void guardaRegistroCliente() {
        if (tblClientesBusqueda.getRowCount() > 0) {
            VariablesDelivery.vInfoClienteBusqueda.clear();
            VariablesDelivery.vInfoClienteBusqueda.add(tableModel.data.get(tblClientesBusqueda.getSelectedRow()));
            log.debug("", VariablesDelivery.vInfoClienteBusqueda);
            VariablesDelivery.vCodCli =
                    ((String)tblClientesBusqueda.getValueAt(tblClientesBusqueda.getSelectedRow(), 0)).trim();
            cerrarVentana(true);
        }
    }

    /*private void muestraMantCliente()
  {
    DlgMantClienteDireccion dlgMantClienteDireccion = new DlgMantClienteDireccion(myParentFrame,"",true);
    dlgMantClienteDireccion.setVisible(true);
  }*/
}
