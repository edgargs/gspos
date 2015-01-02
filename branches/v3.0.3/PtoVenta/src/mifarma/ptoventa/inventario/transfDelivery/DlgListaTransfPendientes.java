package mifarma.ptoventa.inventario.transfDelivery;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

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

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.inventario.reference.ConstantsInventario;
import mifarma.ptoventa.inventario.reference.VariablesInventario;
import mifarma.ptoventa.inventario.transfDelivery.reference.ConstantsTranfDelivery;
import mifarma.ptoventa.inventario.transfDelivery.reference.DBTranfDelivery;
import mifarma.ptoventa.inventario.transfDelivery.reference.VariablesTranfDelivery;
import mifarma.ptoventa.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgListaTransfPendientes.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCALLO      20.10.2008   Creación<br>
 * <br>
 * @author JCALLO<br>
 * @version 1.0<br>
 *
 */
public class DlgListaTransfPendientes extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgListaTransfPendientes.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;
    private String filtro;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JScrollPane scrListaTransferencias = new JScrollPane();
    private JTable tblListaTransferencias = new JTable();
    private JButtonLabel btnRelacionTransferencias = new JButtonLabel();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF2 = new JLabelFunction();
    private JButtonLabel lblCantTransfereniasT = new JButtonLabel();
    private JButtonLabel lblCantTransferencias = new JButtonLabel();

    private final int COL_NUM_PED = 0;
    private final int COL_LOCAL_DEST = 1;
    private final int COL_CANT_ITEMS = 2;
    private final int COL_FEC_PEDIDO = 3;
    private final int COL_LOCAL = 4;
    private final int COL_SEC_GRUPO = 5;
    private final int COL_COD_LOCAL_DEST = 7;
    private final int COL_SEC_TRANS = 8;

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgListaTransfPendientes() {
        this(null, "", false);
    }

    public DlgListaTransfPendientes(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
            FarmaUtility.centrarVentana(this);
        } catch (Exception e) {
            log.error("", e);
        }

    }

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.setSize(new Dimension(564, 360));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Transferencia Delivery Pendientes");
        this.setDefaultCloseOperation(0);
        this.setBounds(new Rectangle(10, 10, 564, 360));
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        pnlTitle1.setBounds(new Rectangle(10, 10, 535, 25));
        scrListaTransferencias.setBounds(new Rectangle(10, 35, 535, 260));
        /*tblListaTransferencias.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          tblListaTransferencias_keyPressed(e);
        }
      });*/
        btnRelacionTransferencias.setText("Relacion de Transferencias Pendientes");
        btnRelacionTransferencias.setBounds(new Rectangle(5, 5, 225, 15));
        btnRelacionTransferencias.setMnemonic('R');
        btnRelacionTransferencias.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRelacionTransferencias_actionPerformed(e);
            }
        });
        btnRelacionTransferencias.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnRelacionTransferencias_keyPressed(e);
            }
        });

        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(450, 305, 95, 20));
        lblF2.setText("[ F2 ] Ver Transferencia");
        lblF2.setBounds(new Rectangle(10, 305, 180, 20));
        lblCantTransfereniasT.setBounds(new Rectangle(480, 5, 45, 15));
        lblCantTransferencias.setText("Total Transf. :");
        lblCantTransferencias.setBounds(new Rectangle(380, 5, 85, 15));
        scrListaTransferencias.getViewport().add(tblListaTransferencias, null);
        jContentPane.add(lblF2, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(scrListaTransferencias, null);
        pnlTitle1.add(lblCantTransferencias, null);
        pnlTitle1.add(lblCantTransfereniasT, null);
        pnlTitle1.add(btnRelacionTransferencias, null);
        jContentPane.add(pnlTitle1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initTable();
        FarmaVariables.vAceptar = false;
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        tableModel =
                new FarmaTableModel(ConstantsTranfDelivery.columnsListaTransDelivery, ConstantsTranfDelivery.defaultcolumnsListaTransDelivery,
                                    0);
        FarmaUtility.initSimpleList(tblListaTransferencias, tableModel,
                                    ConstantsTranfDelivery.columnsListaTransDelivery);
        filtro = "%";
        cargaListaTransferencias();
    }

    private void cargaListaTransferencias() {
        try {
            DBTranfDelivery.cargaListaTransfDeliveryPendientes(tableModel, filtro);
            lblCantTransfereniasT.setText("" + tblListaTransferencias.getRowCount());
            FarmaUtility.ordenar(tblListaTransferencias, tableModel, 0, FarmaConstants.ORDEN_DESCENDENTE);
        } catch (SQLException sql) {
            log.error("", sql);
            FarmaUtility.showMessage(this, "Ocurrió un error al listar las transferencias: \n" +
                    sql.getMessage(), btnRelacionTransferencias);
        }
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void btnRelacionTransferencias_actionPerformed(ActionEvent e) {
        //FarmaUtility.moveFocus(tblListaTransferencias);
    }

    private void btnRelacionTransferencias_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    /*private void tblListaTransferencias_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }*/

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(btnRelacionTransferencias);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        //FarmaGridUtils.aceptarTeclaPresionada(e,tblListaTransferencias,null,0);
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaTransferencias, null, 0);

        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {
            if (!FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_SUPERVISOR_VENTAS)) {
                if (FarmaVariables.vEconoFar_Matriz)
                    FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, btnRelacionTransferencias);
                else {
                    VariablesInventario.vArrayTransferenciaProductos = new ArrayList();
                    VariablesInventario.vHistoricoLote = true;
                    /*JCALLO 05.11.2008
      DlgTransferenciasNueva dlgTransferenciasNueva = new DlgTransferenciasNueva(myParentFrame,"",true);
      dlgTransferenciasNueva.setVisible(true);
					 */
                    if (FarmaVariables.vAceptar) {
                        cargaListaTransferencias();
                        FarmaVariables.vAceptar = false;
                    }
                }
            } else {
                FarmaUtility.showMessage(this, "No posee privilegios suficientes para acceder a esta opción", null);
            }
        } else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e) &&
                   !VariablesTranfDelivery.vFlagDialogAbierto) {
            VariablesTranfDelivery.vFlagTeclaFxxPresionado = true;

            int row = tblListaTransferencias.getSelectedRow();

            if (row < 0) {
                FarmaUtility.showMessage(this, "Seleccione el Pedido de Transferencia !", btnRelacionTransferencias);
            } else {

                VariablesTranfDelivery.vNumPedidoTransf = tableModel.getValueAt(row, COL_NUM_PED).toString().trim();
                VariablesTranfDelivery.vSecGrupo = tableModel.getValueAt(row, COL_SEC_GRUPO).toString().trim();
                VariablesTranfDelivery.vCodLocalDestino =
                        tableModel.getValueAt(row, COL_COD_LOCAL_DEST).toString().trim();
                VariablesTranfDelivery.vFecPedidoTransf = tableModel.getValueAt(row, COL_FEC_PEDIDO).toString().trim();
                VariablesTranfDelivery.vLocalDestino = tableModel.getValueAt(row, COL_LOCAL_DEST).toString().trim();
                VariablesTranfDelivery.vCodLocalDel = tableModel.getValueAt(row, COL_LOCAL).toString().trim();
                VariablesTranfDelivery.vSecTrans = tableModel.getValueAt(row, COL_SEC_TRANS).toString().trim();

                /* JCALLO 05.11.2008*/
                DlgTransfDeliveryDetalle dlgTransfDeliveryDetalle =
                    new DlgTransfDeliveryDetalle(myParentFrame, "", true);
                dlgTransfDeliveryDetalle.setVisible(true);

                //volver a cargar el listado pedidos de transf pendientes
                cargaListaTransferencias();
                /* --- */

                if (FarmaVariables.vAceptar) {
                    FarmaVariables.vAceptar = false;
                    if (tblListaTransferencias.getRowCount() > 0) {
                        FarmaGridUtils.showCell(tblListaTransferencias, VariablesInventario.vPos,
                                                VariablesInventario.vPos);
                    }
                }

            }

            VariablesTranfDelivery.vFlagTeclaFxxPresionado = false;

        } else if (e.getKeyCode() == KeyEvent.VK_F6) {
            filtrar();
        } else if (e.getKeyCode() == KeyEvent.VK_F7) {
            quitarFiltro();
        } else if (e.getKeyCode() == KeyEvent.VK_F8) {
            if (FarmaVariables.vEconoFar_Matriz)
                FarmaUtility.showMessage(this, ConstantsPtoVenta.MENSAJE_MATRIZ, btnRelacionTransferencias);
            else
                funcionF8();
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

    private void funcionF8() {
        /* JCALLO 05.11.2008
    DlgTransferenciasPorConfirmar dlgTransferenciasPorConfirmar = new DlgTransferenciasPorConfirmar(myParentFrame,"",true);
    dlgTransferenciasPorConfirmar.setVisible(true);
		 */
        if (FarmaVariables.vAceptar) {
            cargaListaTransferencias();
            FarmaVariables.vAceptar = false;
        }
    }


    private void filtrar() {
        if (tblListaTransferencias.getRowCount() > 0) {
            VariablesInventario.vTipoNota = ConstantsPtoVenta.TIP_NOTA_SALIDA;
            VariablesInventario.vNomInHashtableGuias = ConstantsInventario.NOM_HASTABLE_CMBFILTRO_TRANSF;
            /* JCALLO 05.11.2008
      DlgFiltroGuias dlgFiltroGuias = new DlgFiltroGuias(myParentFrame,"", true);
      dlgFiltroGuias.setVisible(true);
			 */
            if (FarmaVariables.vAceptar) {
                filtro = VariablesInventario.vCodFiltro;
                cargaListaTransferencias();
                FarmaVariables.vAceptar = false;
            }
        }
    }

    private void quitarFiltro() {
        filtro = "%";
        cargaListaTransferencias();
    }


}
