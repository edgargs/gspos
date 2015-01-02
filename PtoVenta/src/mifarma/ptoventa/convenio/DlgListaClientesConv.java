package mifarma.ptoventa.convenio;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;

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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.FarmaConnectionRemoto;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.convenio.reference.ConstantsConvenio;
import mifarma.ptoventa.convenio.reference.DBConvenio;
import mifarma.ptoventa.convenio.reference.VariablesConvenio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2007 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaClientesConv.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      21.05.2007   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgListaClientesConv extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaClientesConv.class);

    private Frame myParentFrame;
    private FarmaTableModel tableModelLista;

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jContentPane = new JPanel();
    private JLabelFunction lblEnter = new JLabelFunction();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JPanel pnlRelacionFiltros = new JPanel();
    private JPanel pnlIngresarCliente = new JPanel();
    private JTextField txtDescripcion = new JTextField();
    private JButton btnDescripcion = new JButton();
    private JTable tbDependientes = new JTable();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JButtonLabel btnRelacionDependientes = new JButtonLabel();
    private JLabelFunction lblF1 = new JLabelFunction();

    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JButtonLabel lblCreditoActual = new JButtonLabel();
    private JButtonLabel lblCredito = new JButtonLabel();
    private JLabelFunction lblF5 = new JLabelFunction();


    private int posOld = 0;
    // constantes de listado de clientes
    // 31.01.2008 dubilluz  creacion
    private int COLUMN_COD_CLI = 0;
    private int COLUMN_COD_TRAB = 1;
    private int COLUMN_DESC_CLI = 2;
    private int COLUMN_DOC_CLI = 3;
    private int COLUMN_COD_TRAB_CONV = 4;


    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgListaClientesConv() {
        this(null, "", false);
    }

    public DlgListaClientesConv(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(609, 413));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Lista de Clientes por Convenio");
        this.setDefaultCloseOperation(0);
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
        lblEnter.setBounds(new Rectangle(370, 350, 130, 20));
        jScrollPane1.setBounds(new Rectangle(15, 80, 575, 220));
        jScrollPane1.setBackground(new Color(255, 130, 14));
        pnlRelacionFiltros.setBounds(new Rectangle(15, 60, 575, 20));
        pnlRelacionFiltros.setBackground(new Color(255, 130, 14));
        pnlRelacionFiltros.setLayout(null);
        pnlIngresarCliente.setBounds(new Rectangle(15, 10, 575, 40));
        pnlIngresarCliente.setBorder(BorderFactory.createLineBorder(new Color(255, 130, 14), 1));
        pnlIngresarCliente.setBackground(Color.white);
        pnlIngresarCliente.setLayout(null);
        jPanelHeader1.setBounds(new Rectangle(15, 300, 575, 35));
        lblCreditoActual.setText("REFRESCAR");
        lblCreditoActual.setVisible(false);
        lblCreditoActual.setFont(new Font("SansSerif", 1, 12));
        lblCreditoActual.setBounds(new Rectangle(475, 10, 70, 20));
        lblCredito.setText("Credito Actual :  S/. ");
        lblCredito.setVisible(false);
        lblCredito.setBounds(new Rectangle(330, 10, 145, 20));
        lblCredito.setFont(new Font("SansSerif", 1, 12));
        lblF5.setText("[ F5] Ver Credito Actual");
        lblF5.setVisible(false);
        lblF5.setBounds(new Rectangle(210, 350, 155, 20));
        pnlIngresarCliente.setForeground(new Color(255, 130, 14));
        txtDescripcion.setBounds(new Rectangle(105, 10, 425, 20));
        txtDescripcion.setFont(new Font("SansSerif", 1, 11));
        txtDescripcion.setForeground(new Color(32, 105, 29));
        txtDescripcion.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtDescripcion_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtDescripcion_keyReleased(e);
            }
        });
        btnDescripcion.setText("Descripcion :");
        btnDescripcion.setBounds(new Rectangle(15, 10, 85, 20));
        btnDescripcion.setMnemonic('d');
        btnDescripcion.setFont(new Font("SansSerif", 1, 11));
        btnDescripcion.setDefaultCapable(false);
        btnDescripcion.setRequestFocusEnabled(false);
        btnDescripcion.setBackground(new Color(50, 162, 65));
        btnDescripcion.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnDescripcion.setFocusPainted(false);
        btnDescripcion.setHorizontalAlignment(SwingConstants.LEFT);
        btnDescripcion.setContentAreaFilled(false);
        btnDescripcion.setBorderPainted(false);
        btnDescripcion.setForeground(new Color(255, 130, 14));
        btnDescripcion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDescripcion_actionPerformed(e);
            }
        });
        tbDependientes.setFont(new Font("SansSerif", 0, 12));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(505, 350, 85, 20));
        btnRelacionDependientes.setText("Relacion de Clientes");
        btnRelacionDependientes.setBounds(new Rectangle(10, 0, 150, 20));
        lblF1.setText("[F1 ] Seleccionar Dependiente");
        lblF1.setBounds(new Rectangle(15, 350, 190, 20));
        lblF1.setVisible(false);
        jScrollPane1.getViewport();
        pnlIngresarCliente.add(txtDescripcion, null);
        pnlIngresarCliente.add(btnDescripcion, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
        jPanelHeader1.add(lblCredito, null);
        jPanelHeader1.add(lblCreditoActual, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(lblF5, null);
        jContentPane.add(jPanelHeader1, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblEnter, null);
        jScrollPane1.getViewport().add(tbDependientes, null);
        jContentPane.add(jScrollPane1, null);
        pnlRelacionFiltros.add(btnRelacionDependientes, null);
        jContentPane.add(pnlRelacionFiltros, null);
        jContentPane.add(pnlIngresarCliente, null);
        //this.getContentPane().add(jContentPane, null);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        initTable();
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTable() {
        tableModelLista =
                new FarmaTableModel(ConstantsConvenio.columnsListaClientesConv, ConstantsConvenio.defaultValuesListaClientesConv,
                                    0);
        FarmaUtility.initSimpleList(tbDependientes, tableModelLista, ConstantsConvenio.columnsListaClientesConv);
        cargaListaClientes();
        cargaEnventosConvenioCredito();
    }

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtDescripcion);
        //Se consultara si el convenio es de Dependencia de clientes
        if (VariablesConvenio.vIndConvCliDependiente.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
            lblF1.setVisible(true);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void txtDescripcion_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tbDependientes, txtDescripcion, 2);
        //FarmaGridUtils.buscarCodigo_KeyPressed(e, this, tbDependientes, txtDescripcion, 0);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            if (tbDependientes.getSelectedRow() >= 0) {
                /**
      * Se filtra por tipo de campo
      * @author : JCORTEZ
      * @since  : 18.08.2008
      */
                log.debug("TIPO FILTRO CONSULTA :" + VariablesConvenio.vTipoFiltro);
                if (FarmaUtility.isInteger(txtDescripcion.getText().trim()) &&
                    txtDescripcion.getText().trim().length() < 7) {
                    if (!(FarmaUtility.findTextInJTable(tbDependientes, txtDescripcion.getText().trim(),
                                                        COLUMN_COD_TRAB, COLUMN_DESC_CLI))) {
                        FarmaUtility.showMessage(this, " Maestro No Encontrado según Criterio de Búsqueda !!!",
                                                 txtDescripcion);
                        return;
                    }
                } else if (FarmaUtility.isInteger(txtDescripcion.getText().trim()) &&
                           txtDescripcion.getText().trim().length() > 6) {
                    if (!(FarmaUtility.findTextInJTable(tbDependientes, txtDescripcion.getText().trim(),
                                                        COLUMN_DOC_CLI, COLUMN_DESC_CLI))) {
                        FarmaUtility.showMessage(this, " Maestro No Encontrado según Criterio de Búsqueda !!!",
                                                 txtDescripcion);
                        return;
                    }
                } else {
                    if (!(FarmaUtility.findTextInJTable(tbDependientes, txtDescripcion.getText().trim(),
                                                        COLUMN_DESC_CLI, COLUMN_DESC_CLI))) {
                        FarmaUtility.showMessage(this, " Maestro No Encontrado según Criterio de Búsqueda !!!",
                                                 txtDescripcion);
                        return;
                    }
                }

                /*if(VariablesConvenio.vTipoFiltro.equalsIgnoreCase(ConstantsConvenio.CAMPO_CODIGO_TRABAJADOR)){
          if (!(FarmaUtility.findTextInJTable(tbDependientes,txtDescripcion.getText().trim(),COLUMN_COD_TRAB,COLUMN_DESC_CLI)))
          {
            FarmaUtility.showMessage(this,"Maestro No Encontrado según Criterio de Búsqueda  !!!",txtDescripcion);
            return;
          }
        }else if((VariablesConvenio.vTipoFiltro.equalsIgnoreCase(ConstantsConvenio.CAMPO_NUMERO_DOCUMENTO))){
         if (!(FarmaUtility.findTextInJTable(tbDependientes, txtDescripcion.getText().trim(), COLUMN_DOC_CLI, COLUMN_DESC_CLI)))
          {
            FarmaUtility.showMessage(this,"Maestro No Encontrado según Criterio de Búsqueda !!!",txtDescripcion);
            return;
          }
         }else{
         if (!(FarmaUtility.findTextInJTable(tbDependientes, txtDescripcion.getText().trim(), COLUMN_DOC_CLI, COLUMN_DESC_CLI)))
          {
            FarmaUtility.showMessage(this,"Maestro No Encontrado según Criterio de Búsqueda !!!",txtDescripcion);
            return;
          }
         }*/
            }
        }

        chkKeyPressed(e);
    }

    private void txtDescripcion_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tbDependientes, txtDescripcion, 2);
        procesaCreditoConvenio();
    }

    private void btnDescripcion_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtDescripcion);
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            guardaValoresMaestro();
            cerrarVentana(true);
        } else // Se listaran los dependientes si el convenio lo permite.
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e)) {
            if (lblF1.isVisible()) {
                log.debug("Se listan los dependientes del cliente");
                VariablesConvenio.vCodClienteBusqueda =
                        FarmaUtility.getValueFieldJTable(tbDependientes, tbDependientes.getSelectedRow(),
                                                         COLUMN_COD_CLI);
                VariablesConvenio.vDatosClienteBusqueda =
                        FarmaUtility.getValueFieldJTable(tbDependientes, tbDependientes.getSelectedRow(),
                                                         COLUMN_DESC_CLI);

                DlgListaDependientesClientesConv dlgDependientes =
                    new DlgListaDependientesClientesConv(myParentFrame, "", true);
                dlgDependientes.setVisible(true);
                if (FarmaVariables.vAceptar) {
                    cerrarVentana(true);
                    guardaValoresMaestro();
                }

            }
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            if (lblF5.isVisible())
                funcionF5();
        }

    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void cargaListaClientes() {
        //String tipoMaestro = VariablesDelivery.vTipoMaestro;
        try {

            DBConvenio.cargaListaClienteConv(tableModelLista);
            FarmaUtility.ordenar(tbDependientes, tableModelLista, COLUMN_DESC_CLI, FarmaConstants.ORDEN_ASCENDENTE);
        } catch (SQLException e) {
            log.error("", e);
            FarmaUtility.showMessage(this, "Ha ocurrido un error al listar clientes.\n" +
                    e.getMessage(), txtDescripcion);
        }
    }

    private void guardaValoresMaestro() {

        VariablesConvenio.vCodCliente =
                FarmaUtility.getValueFieldJTable(tbDependientes, tbDependientes.getSelectedRow(), COLUMN_COD_CLI);
        VariablesConvenio.vCodTrab =
                FarmaUtility.getValueFieldJTable(tbDependientes, tbDependientes.getSelectedRow(), COLUMN_COD_TRAB);
        VariablesConvenio.vNomCliente =
                FarmaUtility.getValueFieldJTable(tbDependientes, tbDependientes.getSelectedRow(), COLUMN_DESC_CLI);

        VariablesConvenio.vNumDocIdent =
                FarmaUtility.getValueFieldJTable(tbDependientes, tbDependientes.getSelectedRow(), COLUMN_DOC_CLI);

        log.debug("VariablesConvenio.vCodCliente : " + VariablesConvenio.vCodCliente);
        log.debug("VariablesConvenio.vCodTrab : " + VariablesConvenio.vCodTrab);
        log.debug("VariablesConvenio.vNomCliente : " + VariablesConvenio.vNomCliente);
        log.debug("VariablesConvenio.vCodClienteDependiente : " + VariablesConvenio.vCodClienteDependiente);
        log.debug("VariablesConvenio.vCodClienteDependiente : " + VariablesConvenio.vNumDocIdent);


    }


    /**
     * Limpia el label de credito de cliente
     * @author dubilluz
     * @since  29.02.2008
     */
    private void procesaCreditoConvenio() {
        if (!VariablesConvenio.vPorcCoPago.trim().equalsIgnoreCase("0")) {
            if (tbDependientes.getRowCount() > 0) {
                int posNew = tbDependientes.getSelectedRow();
                if (posOld != posNew) {
                    lblCreditoActual.setText("REFRESCAR");
                }
            }
        }
    }

    /**
     * Habilita labels si el convenio da Credito
     * @author dubilluz
     * @since  29.02.2008
     */
    private void cargaEnventosConvenioCredito() {
        if (!VariablesConvenio.vPorcCoPago.trim().equalsIgnoreCase("0")) {
            log.debug("ss");
            lblCredito.setVisible(true);
            lblCreditoActual.setVisible(true);
            lblF5.setVisible(true);
        }

        log.debug("111");
    }

    /**
     * Muestra el credito Actual del Trabajador
     * @author dubilluz
     * @since  29.02.2008
     */
    private void funcionF5() {
        if (tbDependientes.getRowCount() > 0) {
            posOld = tbDependientes.getSelectedRow();
            String cod_cli = tbDependientes.getValueAt(tbDependientes.getSelectedRow(), 0).toString().trim();
            String monto = "0";
            String cod_convenio = VariablesConvenio.vCodConvenio;
            String credito_actual = "";
            String vIndLinea = "";
            try {

                //Se verifica si hay linea para validar el cupon en Matriz
                vIndLinea =
                        FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_N);


                if (vIndLinea.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                    credito_actual =
                            DBConvenio.validaCreditoCli(cod_convenio, cod_cli, monto, FarmaConstants.INDICADOR_S);
                else
                    FarmaUtility.showMessage(this, "No hay linea con matriz.\n No puede verificar el credito.",
                                             txtDescripcion);
            } catch (Exception e) {
                log.error("", e);
                FarmaUtility.showMessage(this, "Ha ocurrido un error al obtener el credito del cliente.\n" +
                        e.getMessage(), txtDescripcion);
                credito_actual = "000.00";
            } finally {
                if (vIndLinea.equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                    FarmaConnectionRemoto.closeConnection();
            }

            lblCreditoActual.setText(credito_actual.trim());
        }
    }

}
