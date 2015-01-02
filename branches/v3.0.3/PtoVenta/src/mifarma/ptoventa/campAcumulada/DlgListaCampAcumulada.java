package mifarma.ptoventa.campAcumulada;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

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

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.campAcumulada.reference.ConstantsCampAcumulada;
import mifarma.ptoventa.campAcumulada.reference.DBCampAcumulada;
import mifarma.ptoventa.campAcumulada.reference.VariablesCampAcumulada;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaLocalesMot.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCALLO      15.12.2008   Creación<br>
 * <br>
 * @author Javier Callo Quispe<br>
 * @version 1.0<br>
 *
 */
public class DlgListaCampAcumulada extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    private static final Logger log = LoggerFactory.getLogger(DlgListaCampAcumulada.class);

    FarmaTableModel tableModelDisp;
    FarmaTableModel tableModelInsc;
    private Frame myParentFrame;

    private final int COL_DNI = 0;
    private final int COL_NOM_CLI = 1;
    private final int COL_APE_PAT = 2;
    private final int COL_APE_MAT = 3;
    private final int COL_SEX_CLI = 4;
    private final int COL_FEC_NAC = 5;
    private final int COL_DIR_CLI = 6;
    private final int COL_TLF_CLI = 7;
    private final int COL_CEL_CLI = 8;
    private final int COL_EMAIL = 9;

    private final int COL_FLAG = 0;
    private final int COL_DESC_CAMP = 1;
    private final int COL_FEC_INI = 2;
    private final int COL_FEC_FIN = 3;
    private final int COL_COD_CAMP = 4;
    private final int COL_MSG_CAMP = 5;


    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    private JPanelTitle pnlTitle2 = new JPanelTitle();
    private JPanelHeader pnlHeader = new JPanelHeader();
    private JPanelWhite pnlFooter = new JPanelWhite();
    private JLabelFunction lblEsc = new JLabelFunction();

    private JLabelFunction lblF5 = new JLabelFunction();

    private JLabelFunction lblF11 = new JLabelFunction();

    private JScrollPane scrCampDisponibles = new JScrollPane();
    private JTable tblCampAcumuDisponibles = new JTable();
    private JScrollPane scrCampInscritos = new JScrollPane();
    private JTable tblCampAcumuInscritos = new JTable();


    private JButtonLabel btnListaCampAcumuladas = new JButtonLabel();
    private JButtonLabel btnListaCampAcumuCliente = new JButtonLabel();

    private JButtonLabel btnCampAcumulada = new JButtonLabel();

    private JTextFieldSanSerif txtCampania = new JTextFieldSanSerif();

    private JTextArea txtAreaMensaje = new JTextArea();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgListaCampAcumulada() {
        this(null, "", false);
    }


    public DlgListaCampAcumulada(Frame parent, String title, boolean modal) {
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
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(borderLayout1);

        jContentPane.setSize(new Dimension(415, 410));
        this.setSize(new Dimension(744, 600));
        this.setTitle("Lista de Campañas de Acumulacion");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });

        pnlHeader.setBounds(new Rectangle(10, 5, 715, 35));

        pnlTitle1.setBounds(new Rectangle(10, 45, 715, 25));
        pnlTitle2.setBounds(new Rectangle(10, 255, 715, 25));

        pnlFooter.setBounds(new Rectangle(10, 385, 715, 155));

        scrCampDisponibles.setBounds(new Rectangle(10, 70, 715, 185));
        tblCampAcumuDisponibles.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblCampAcumuDisponibles_keyPressed(e);
            }
        });

        scrCampInscritos.setBounds(new Rectangle(10, 280, 715, 105));
        tblCampAcumuInscritos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblCampAcumuInscritos_keyPressed(e);
            }
        });

        btnCampAcumulada.setBounds(new Rectangle(10, 5, 70, 20));
        btnCampAcumulada.setText("Campaña: ");
        btnCampAcumulada.setMnemonic('C');
        btnCampAcumulada.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCampAcumulada_actionPerformed(e);
            }
        });

        btnListaCampAcumuladas.setBounds(new Rectangle(10, 5, 265, 15));
        btnListaCampAcumuladas.setText("Lista de campañas de acumulacion Disponible");
        btnListaCampAcumuladas.setMnemonic('L');
        btnListaCampAcumuladas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListaCampAcumuladas_actionPerformed(e);
            }
        });

        btnListaCampAcumuCliente.setBounds(new Rectangle(10, 5, 665, 15));
        btnListaCampAcumuCliente.setText("Detalle de campañas inscritas : ");
        btnListaCampAcumuCliente.setMnemonic('d');
        btnListaCampAcumuCliente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnListaCampAcumuCliente_actionPerformed(e);
            }
        });

        btnListaCampAcumuCliente.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnListaCampAcumuCliente_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                //btnListaCampAcumuCliente_keyReleased(e);
            }

            public void keyTyped(KeyEvent e) {
                //txtCampania_keyTyped(e);
            }
        });

        txtCampania.setText("");
        txtCampania.setBounds(new Rectangle(80, 5, 570, 20));
        txtCampania.setFont(new Font("SansSerif", 0, 11));
        txtCampania.setLengthText(300); //de acuerdo a la base de datos
        txtCampania.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtCampania_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtCampania_keyReleased(e);
            }

            public void keyTyped(KeyEvent e) {
                //txtCampania_keyTyped(e);
            }
        });

        txtAreaMensaje.setBounds(new Rectangle(0, 5, 715, 140));
        txtAreaMensaje.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtAreaMensaje_keyPressed(e);
            }
        });

        lblEsc.setBounds(new Rectangle(605, 545, 120, 20));
        lblEsc.setText("[ ESC ] Cerrar");


        lblF11.setBounds(new Rectangle(455, 545, 130, 20));
        lblF11.setText("[ F11 ] Aceptar");

        lblF5.setBounds(new Rectangle(305, 545, 130, 20));
        lblF5.setText("[ F5 ] Ver Todas");


        //pnlHeader.add(lblMensaje,null);

        pnlHeader.add(btnCampAcumulada, null);
        pnlHeader.add(txtCampania, null);

        pnlTitle1.add(btnListaCampAcumuladas, null);

        scrCampDisponibles.getViewport().add(tblCampAcumuDisponibles, null);

        txtAreaMensaje.setLineWrap(true);

        txtAreaMensaje.setEditable(false);
        txtAreaMensaje.setFont(new Font("SansSerif", 0, 20));
        txtAreaMensaje.setForeground(Color.red);
        txtAreaMensaje.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 1)); //new Color(255,66,0),1));


        jContentPane.add(lblF11, null);
        jContentPane.add(lblF5, null);
        jContentPane.add(lblEsc, null);
        jContentPane.add(scrCampDisponibles, null);
        scrCampInscritos.getViewport().add(tblCampAcumuInscritos, null);
        jContentPane.add(scrCampInscritos, null);
        jContentPane.add(pnlHeader, null);
        jContentPane.add(pnlTitle1, null);
        pnlTitle2.add(btnListaCampAcumuCliente, null);
        jContentPane.add(pnlTitle2, null);
        pnlFooter.add(txtAreaMensaje, null);
        jContentPane.add(pnlFooter, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        VariablesCampAcumulada.vListaCampanias = new ArrayList();
        initTable();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    public void initTable() {
        tableModelDisp =
                new FarmaTableModel(ConstantsCampAcumulada.columnsListaCampAcumuDisp, ConstantsCampAcumulada.defaultListaListaCampAcumuDisp,
                                    0);
        FarmaUtility.initSelectList(tblCampAcumuDisponibles, tableModelDisp,
                                    ConstantsCampAcumulada.columnsListaCampAcumuDisp);

        tableModelInsc =
                new FarmaTableModel(ConstantsCampAcumulada.columnsListaCampAcumuIns, ConstantsCampAcumulada.defaultListaListaCampAcumuIns,
                                    0);
        FarmaUtility.initSimpleList(tblCampAcumuInscritos, tableModelInsc,
                                    ConstantsCampAcumulada.columnsListaCampAcumuIns);

        /**cargar todas las campañas**/
        VariablesCampAcumulada.vFlagFiltro = true; //indicador de que tiene el filtro de cod_prod

        cargaCampañas();

    }

    private void cargaCampAcumulacion(String pCodProdFiltro) {
        //limpiando las tablas para agregar lo nuevo
        tableModelDisp.clearTable();
        //tableModelInsc.clearTable();
        ///VariablesCampAcumulada.vListaCampanias = new ArrayList();
        try {
            DBCampAcumulada.getCampAcumuladas(tableModelDisp, pCodProdFiltro);
            /*if( VariablesFidelizacion.vDniCliente.trim().length() > 0 ){
               cargarCampAcumuladaCliente(VariablesFidelizacion.vDniCliente.trim());
           }*/

            /* if(VariablesCampAcumulada.vListaCampanias.size()>0){
               String codCamp = "";
               for(int i=0 ; i < VariablesCampAcumulada.vListaCampanias.size() ; i++){
                   codCamp = VariablesCampAcumulada.vListaCampanias.get(i).toString();
                   log.info("codCamp:"+codCamp+"***");
                   for(int j=0; j < tableModelDisp.getRowCount() ; j++){
                       log.info("validando con :"+tableModelDisp.getValueAt(j,COL_COD_CAMP).toString().trim()+"***");
                       if(tableModelDisp.getValueAt(j,COL_COD_CAMP).toString().trim().equals(codCamp)){//
                           //tableModelDisp.setValueAt(new Boolean(true),j,0);
                           ArrayList fila = tableModelDisp.getRow(j);
                           fila.remove(0);//quitando el check
                           tableModelInsc.insertRow(fila);
                           tableModelDisp.deleteRow(j);
                           //j--;//disminuyendo en 1 el contador

                           break;
                       }
                   }
               }

               tblCampAcumuDisponibles.repaint();
               tblCampAcumuInscritos.repaint();
               VariablesCampAcumulada.vListaCampanias = new ArrayList();
           }   */

        } catch (SQLException sql) {
            log.error("", sql);
            log.info("HORROR");
        }
    }

    private void cargarCampAcumuladaCliente(String pDni) {

        try {
            DBCampAcumulada.getCampAcumuladaXCliente(tableModelInsc, pDni);
        } catch (SQLException sql) {
            log.error("", sql);
            log.info("HORROR");
        }
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        //lblDNI_T.setText(VariablesCtrlMotorizado.vAlias_Motorizado);
        //lblNombMotorizado_T.setText(VariablesCtrlMotorizado.vNombre_Motorizado);
        FarmaUtility.moveFocus(txtCampania);
        FarmaUtility.centrarVentana(this);
        cargaDatos();
        /** Coloca datos del cliente **/
        // lblCliente
        btnListaCampAcumuCliente.setText(btnListaCampAcumuCliente.getText() + " " +
                                         VariablesFidelizacion.vNomCliente.trim() + " " +
                                         VariablesFidelizacion.vApePatCliente.trim() + " " +
                                         VariablesFidelizacion.vApeMatCliente.trim());


    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", txtCampania);
    }

    private void tblCampAcumuDisponibles_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblCampAcumuInscritos_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtAreaMensaje_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }


    private void txtCampania_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblCampAcumuDisponibles, txtCampania, 1);
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN ||
            e.getKeyCode() == KeyEvent.VK_PAGE_UP || e.getKeyCode() == KeyEvent.VK_PAGE_DOWN ||
            e.getKeyCode() == KeyEvent.VK_ENTER) {

            if (e.getKeyCode() == KeyEvent.VK_ENTER && !VariablesCampAcumulada.vFlagDialogAbierto) {
                VariablesCampAcumulada.vFlagDialogAbierto = true;

                int row = tblCampAcumuDisponibles.getSelectedRow();
                if (row >= 0) {
                    String codCampAcumula = tableModelDisp.getValueAt(row, COL_COD_CAMP).toString().trim();
                    if (((Boolean)tableModelDisp.getValueAt(row, COL_FLAG)).booleanValue()) {
                        tableModelDisp.setValueAt(new Boolean(false), row, COL_FLAG);

                        if (VariablesCampAcumulada.vListaCampanias.contains(codCampAcumula)) {
                            VariablesCampAcumulada.vListaCampanias.remove(codCampAcumula);
                        }
                    } else {
                        tableModelDisp.setValueAt(new Boolean(true), row, COL_FLAG);
                        VariablesCampAcumulada.vListaCampanias.add(codCampAcumula);
                    }
                    tblCampAcumuDisponibles.repaint(); //repitando la tabla
                    log.info("cod_campnias : " + VariablesCampAcumulada.vListaCampanias);
                    txtCampania.setText(tableModelDisp.getValueAt(row, COL_DESC_CAMP).toString().trim());
                }

                VariablesCampAcumulada.vFlagDialogAbierto = false;
            }
            e.consume();
            int rowSel = tblCampAcumuDisponibles.getSelectedRow();
            if (rowSel >= 0) {
                log.debug("tableModelDisp.getValueAt(rowSel,5).toString().trim():" +
                          tableModelDisp.getValueAt(rowSel, 5).toString().trim());
                //log.debug("\nlerolero\nlero lero\n****");
                txtAreaMensaje.setText(tableModelDisp.getValueAt(rowSel, 5).toString().trim());
            }
        }
        chkKeyPressed(e);
    }


    private void txtCampania_keyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblCampAcumuDisponibles, txtCampania, 1);
        e.consume();
        int rowSel = tblCampAcumuDisponibles.getSelectedRow();
        if (rowSel >= 0) {
            log.debug("tableModelDisp.getValueAt(rowSel,5).toString().trim():" +
                      tableModelDisp.getValueAt(rowSel, COL_MSG_CAMP).toString().trim());
            //log.debug("\nlerolero\nlero lero\n****");
            txtAreaMensaje.setText(tableModelDisp.getValueAt(rowSel, 5).toString().trim());
        }
    }

    private void btnListaCampAcumuCliente_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, tblCampAcumuInscritos, null, 0);
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN ||
            e.getKeyCode() == KeyEvent.VK_PAGE_UP || e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            e.consume();
            int rowSel = tblCampAcumuInscritos.getSelectedRow();
            if (rowSel >= 0) {
                log.debug("tableModelDisp.getValueAt(rowSel,5).toString().trim():" +
                          tableModelInsc.getValueAt(rowSel, COL_MSG_CAMP - 1).toString().trim());
                //log.debug("\nlerolero\nlero lero\n****");
                txtAreaMensaje.setText(tableModelInsc.getValueAt(rowSel, COL_MSG_CAMP - 1).toString().trim());
            }
        }
        /*if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            cerrarVentana(false);
         }*/

        chkKeyPressed(e);
    }


    private void btnCampAcumulada_actionPerformed(ActionEvent e) {
        //log.debug("move focus txtCampania... del Acceso");
        tblCampAcumuInscritos.clearSelection();
        FarmaUtility.moveFocus(txtCampania);

    }


    private void btnListaCampAcumuladas_actionPerformed(ActionEvent e) {
        tblCampAcumuInscritos.clearSelection();
        FarmaUtility.moveFocus(txtCampania);
    }

    private void btnListaCampAcumuCliente_actionPerformed(ActionEvent e) {

        if (tableModelInsc.getRowCount() > 0) {
            FarmaUtility.moveFocus(btnListaCampAcumuCliente);
            FarmaGridUtils.showCell(tblCampAcumuInscritos, 0, 0);
            int rowSel = tblCampAcumuInscritos.getSelectedRow();
            if (rowSel >= 0) {
                log.debug("tableModelDisp.getValueAt(rowSel,5).toString().trim():" +
                          tableModelInsc.getValueAt(rowSel, COL_MSG_CAMP - 1).toString().trim());
                //log.debug("\nlerolero\nlero lero\n****");
                txtAreaMensaje.setText(tableModelInsc.getValueAt(rowSel, COL_MSG_CAMP - 1).toString().trim());
            }
        } else {
            FarmaUtility.showMessage(this, "No tiene Ninguna Campaña Inscrita !", txtCampania);
        }

    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {


        log.info("VariablesOtros.vFlagDialogAbierto:" + VariablesCampAcumulada.vFlagDialogAbierto);

        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e) &&
            !VariablesCampAcumulada.vFlagDialogAbierto) {

            VariablesCampAcumulada.vFlagDialogAbierto = true;

            if (VariablesCampAcumulada.vListaCampanias.size() > 0) { //lista de campañas asociadas
                FarmaVariables.vAceptar = false;
                DlgRegDatosCliente dlgRegDatosCliente = new DlgRegDatosCliente(myParentFrame, "", true);
                dlgRegDatosCliente.setVisible(true);
                if (FarmaVariables.vAceptar) {
                    //suponiendo caso que el cliente no tiene tarjeta
                    //buscar tarjeta en local, buscar en matriz
                    //si no tuviera tarjeta de fidelizacion mostrar dialogo para ingreso de tarjeta
                    if (VariablesFidelizacion.vNumTarjeta.trim().length() >
                        0) { //quiere decir que ya tiene e ingreso el numero de tarjeta al sistema

                        log.info("asociando clientes con las campanias");
                        //asociar cliente con campañas.
                        asociarClienteCampanias(VariablesCampAcumulada.vDniCliente,
                                                VariablesCampAcumulada.vListaCampanias);

                        /**para actualizar la informacion del cliente***/
                        // DUBILLUZ 15.05.2009
                        setDatosCliente();
                        /*
                    VariablesFidelizacion.vApeMatCliente = VariablesCampAcumulada.vApeMatCliente;
                    VariablesFidelizacion.vApePatCliente = VariablesCampAcumulada.vApePatCliente;
                    VariablesFidelizacion.vDataCliente = VariablesCampAcumulada.vDataCliente;
                    VariablesFidelizacion.vDireccion = VariablesCampAcumulada.vDireccion;
                    VariablesFidelizacion.vDniCliente = VariablesCampAcumulada.vDniCliente;
                    VariablesFidelizacion.vDocValidos = VariablesCampAcumulada.vDocValidos;
                    VariablesFidelizacion.vEmail = VariablesCampAcumulada.vEmail;
                    VariablesFidelizacion.vFecNacimiento = VariablesCampAcumulada.vFecNacimiento;
                    VariablesFidelizacion.vNomCliente = VariablesCampAcumulada.vNomCliente;
                    VariablesFidelizacion.vSexo = VariablesCampAcumulada.vSexo;
                    VariablesFidelizacion.vTelefono = VariablesCampAcumulada.vTelefono;
                    */
                        /**fin de actualizar informacion del cliente**/

                        cerrarVentana(false);
                    } else { //no ingreso tarjeta de fidelizacion aun
                        //buscar tarjetas de cliente en local , si no hay en local, buscar en matriz
                        log.info("OBTENIENDO NUMERO DE TARJETA DE CLIENTE SI ES QUE TIENE");
                        VariablesCampAcumulada.vNroTarjeta = getTarjetasCliente(VariablesCampAcumulada.vDniCliente);

                        if (VariablesCampAcumulada.vNroTarjeta.trim().length() > 0) {
                            log.info("VariablesCampAcumulada.vNroTarjeta:" + VariablesCampAcumulada.vNroTarjeta);

                            asociarClienteCampanias(VariablesCampAcumulada.vDniCliente,
                                                    VariablesCampAcumulada.vListaCampanias);

                            VariablesFidelizacion.vNumTarjeta = VariablesCampAcumulada.vNroTarjeta;

                            // DUBILLUZ 15.05.2009
                            setDatosCliente();
                            /*
                        VariablesFidelizacion.vApeMatCliente = VariablesCampAcumulada.vApeMatCliente;
                        VariablesFidelizacion.vApePatCliente = VariablesCampAcumulada.vApePatCliente;
                        VariablesFidelizacion.vDataCliente = VariablesCampAcumulada.vDataCliente;
                        VariablesFidelizacion.vDireccion = VariablesCampAcumulada.vDireccion;
                        VariablesFidelizacion.vDniCliente = VariablesCampAcumulada.vDniCliente;
                        VariablesFidelizacion.vDocValidos = VariablesCampAcumulada.vDocValidos;
                        VariablesFidelizacion.vEmail = VariablesCampAcumulada.vEmail;
                        VariablesFidelizacion.vFecNacimiento = VariablesCampAcumulada.vFecNacimiento;
                        VariablesFidelizacion.vNomCliente = VariablesCampAcumulada.vNomCliente;
                        VariablesFidelizacion.vSexo = VariablesCampAcumulada.vSexo;
                        VariablesFidelizacion.vTelefono = VariablesCampAcumulada.vTelefono;*/

                            cerrarVentana(true); //con true para que cargue las campañas de fidelizacion
                        } else {
                            FarmaVariables.vAceptar = false;
                            DlgIngresarNumeroTarjeta dlgIngNroTarjeta =
                                new DlgIngresarNumeroTarjeta(myParentFrame, "", true);
                            dlgIngNroTarjeta.setVisible(true);
                            if (FarmaVariables.vAceptar) { //se ingreso numero de tarjeta valido para poder usar
                                //asociar cliente con tarjeta.  1
                                //asociar cliente con campañas. 2
                                asociarClienteTarjetaCampanias(VariablesCampAcumulada.vDniCliente,
                                                               VariablesCampAcumulada.vNroTarjeta,
                                                               VariablesCampAcumulada.vListaCampanias);
                                //despues de haber asociado al cliente el numero de tarjeta y campanias
                                //asignar el numero de tarjeta del cliente en la clase de VariablesFidelizacion.vNumTarjeta
                                VariablesFidelizacion.vNumTarjeta = VariablesCampAcumulada.vNroTarjeta;

                                // DUBILLUZ 15.05.2009
                                setDatosCliente();
                                /*
                            VariablesFidelizacion.vApeMatCliente = VariablesCampAcumulada.vApeMatCliente;
                            VariablesFidelizacion.vApePatCliente = VariablesCampAcumulada.vApePatCliente;
                            VariablesFidelizacion.vDataCliente = VariablesCampAcumulada.vDataCliente;
                            VariablesFidelizacion.vDireccion = VariablesCampAcumulada.vDireccion;
                            VariablesFidelizacion.vDniCliente = VariablesCampAcumulada.vDniCliente;
                            VariablesFidelizacion.vDocValidos = VariablesCampAcumulada.vDocValidos;
                            VariablesFidelizacion.vEmail = VariablesCampAcumulada.vEmail;
                            VariablesFidelizacion.vFecNacimiento = VariablesCampAcumulada.vFecNacimiento;
                            VariablesFidelizacion.vNomCliente = VariablesCampAcumulada.vNomCliente;
                            VariablesFidelizacion.vSexo = VariablesCampAcumulada.vSexo;
                            VariablesFidelizacion.vTelefono = VariablesCampAcumulada.vTelefono;
                            */
                                cerrarVentana(true); //con true para que cargue las campañas de fidelizacion
                            } else {

                                cerrarVentana(false);
                            }
                        }

                    }
                }
            } else {
                FarmaUtility.showMessage(this, "Debe especificar al menos un campaña !", txtCampania);
            }


            /// int rowSel = tblCampAcumulada.getRowCount();

            // FarmaVariables.vAceptar = true;

            // if( rowSel >= 0 ){

            //FarmaVariables.vAceptar = false;

            /*                 DlgCantItemsImprimir dlgCantItemsImprimir = new DlgCantItemsImprimir(myParentFrame,"",true);
                 dlgCantItemsImprimir.setVisible(true);
                 if(FarmaVariables.vAceptar){
                         imprimirHistorial(VariablesOtros.vCantItemsImprimir);
                         cerrarVentana(true);
                 }*/
            // }

            VariablesCampAcumulada.vFlagDialogAbierto = false;

            //cerrarVentana(true);

        } else if (e.getKeyCode() == KeyEvent.VK_F5 && !VariablesCampAcumulada.vFlagDialogAbierto) {
            VariablesCampAcumulada.vFlagDialogAbierto = true;

            if (VariablesCampAcumulada.vFlagFiltro) {

                if (VariablesCampAcumulada.vListaCampanias.size() < 1 ||
                    com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                "Se van perder las campañas seleccionadas\nDesea continuar ?")) {

                    VariablesCampAcumulada.vFlagFiltro = false;
                    lblF5.setText("[ F5 ] Filtrar");
                    cargaCampAcumulacion("T"); //sin filtro MOSTRAR TODOS
                }

            } else { //con filtro delivery
                if (VariablesCampAcumulada.vListaCampanias.size() < 1 ||
                    com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                "Se van perder las campañas seleccionadas\nDesea continuar ?")) {

                    VariablesCampAcumulada.vFlagFiltro = true;
                    lblF5.setText("[ F5 ] Ver Todas");
                    cargaCampAcumulacion(VariablesCampAcumulada.vCodProdFiltro); //sin filtro
                }
            }

            /**JCALLO quitar de las campañas totales en las que este el cliente**/
            String cod_camp = "";
            for (int i = 0; i < tableModelInsc.getRowCount(); i++) { //campañas del cliente
                cod_camp = tableModelInsc.getValueAt(i, COL_COD_CAMP - 1).toString().trim();
                log.info("cod_camp = " + cod_camp);
                for (int k = 0; k < tableModelDisp.getRowCount(); k++) { //
                    if (tableModelDisp.getValueAt(k,
                                                  COL_COD_CAMP).toString().trim().equals(cod_camp)) { //quiere decir que encontro el inscrito en los disponibles
                        log.info("encontro en disponibles = " +
                                 tableModelDisp.getValueAt(k, COL_COD_CAMP).toString().trim());
                        tableModelDisp.deleteRow(k); //eliminando la fila de la lista de disponibles
                        break;
                    }
                }
            }
            if (tableModelInsc.getRowCount() > 0) {
                tblCampAcumuInscritos.clearSelection();
            }

            /**JCALLO fin quitar de las campañas totales en las que este el cliente**/

            /**LIMPIA ALGUNAS VARIABLES**/
            if (tableModelDisp.getRowCount() > 0) {
                int row = tblCampAcumuDisponibles.getSelectedRow();
                if (row >= 0) {
                    txtCampania.setText(tableModelDisp.getValueAt(row, COL_DESC_CAMP).toString().trim());
                    txtAreaMensaje.setText(tableModelDisp.getValueAt(row, COL_MSG_CAMP).toString().trim());
                } else {
                    txtCampania.setText("");
                    txtAreaMensaje.setText("");
                }
            } else {
                txtCampania.setText("");
                txtAreaMensaje.setText("");
            }
            btnCampAcumulada.doClick();
            VariablesCampAcumulada.vListaCampanias = new ArrayList();
            /**FIN LIMPIAR**/

            VariablesCampAcumulada.vFlagDialogAbierto = false;

        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) /*&&
                 !VariablesCampAcumulada.vFlagDialogAbierto*/ {
            /*VariablesCampAcumulada.vFlagDialogAbierto = true;

         int row = tblCampAcumuDisponibles.getSelectedRow();
         if( row >= 0 ){
             String codCampAcumula = tableModelDisp.getValueAt(row,COL_COD_CAMP).toString().trim();
             if( ( (Boolean)tableModelDisp.getValueAt(row,COL_FLAG) ).booleanValue()){
                 tableModelDisp.setValueAt(new Boolean(false),row,COL_FLAG);

                 if(VariablesCampAcumulada.vListaCampanias.contains(codCampAcumula)){
                     VariablesCampAcumulada.vListaCampanias.remove(codCampAcumula);
                 }
             }else{
                 tableModelDisp.setValueAt(new Boolean(true),row,COL_FLAG);
                 VariablesCampAcumulada.vListaCampanias.add(codCampAcumula);
             }
             tblCampAcumuDisponibles.repaint();//repitando la tabla
             log.info("cod_campnias : "+VariablesCampAcumulada.vListaCampanias);
             txtCampania.setText(tableModelDisp.getValueAt(row,COL_DESC_CAMP).toString().trim());
         }

         VariablesCampAcumulada.vFlagDialogAbierto = false;*/
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

    private void buscaMatrizActualizaDatosLocal(String pDni) {

        ArrayList array = new ArrayList();

        try {
            //CORREGIR ESTE METODO
            DBCampAcumulada.getDatosExisteDNI_Matriz(array, pDni, FarmaConstants.INDICADOR_N);
            log.debug("Datos Cli en Matriz " + array);
            log.debug("Tam size:" + array.size());

            if (array.size() > 0) {

                VariablesCampAcumulada.vDniCliente = FarmaUtility.getValueFieldArrayList(array, 0, COL_DNI).trim();
                VariablesCampAcumulada.vNomCliente = FarmaUtility.getValueFieldArrayList(array, 0, COL_NOM_CLI).trim();
                VariablesCampAcumulada.vApePatCliente =
                        FarmaUtility.getValueFieldArrayList(array, 0, COL_APE_PAT).trim();
                VariablesCampAcumulada.vApeMatCliente =
                        FarmaUtility.getValueFieldArrayList(array, 0, COL_APE_MAT).trim();
                VariablesCampAcumulada.vSexo = FarmaUtility.getValueFieldArrayList(array, 0, COL_SEX_CLI).trim();
                VariablesCampAcumulada.vFecNacimiento =
                        FarmaUtility.getValueFieldArrayList(array, 0, COL_FEC_NAC).trim();
                VariablesCampAcumulada.vDireccion = FarmaUtility.getValueFieldArrayList(array, 0, COL_DIR_CLI).trim();
                VariablesCampAcumulada.vTelefono = FarmaUtility.getValueFieldArrayList(array, 0, COL_TLF_CLI).trim();
                VariablesCampAcumulada.vCelular = FarmaUtility.getValueFieldArrayList(array, 0, COL_CEL_CLI).trim();
                VariablesCampAcumulada.vCelular = FarmaUtility.getValueFieldArrayList(array, 0, COL_EMAIL).trim();

                VariablesCampAcumulada.vIndEstado = "A";
                //Este metodo de se encargara de insertar y/o actualizar
                //insertarClienteFidelizacion
                DBCampAcumulada.insertarCliente();

            }
            VariablesCampAcumulada.vDniCliente = "";
            VariablesCampAcumulada.vNomCliente = "";
            VariablesCampAcumulada.vApePatCliente = "";
            VariablesCampAcumulada.vApeMatCliente = "";
            VariablesCampAcumulada.vSexo = "";
            VariablesCampAcumulada.vFecNacimiento = "";
            VariablesCampAcumulada.vDireccion = "";
            VariablesCampAcumulada.vTelefono = "";
            VariablesCampAcumulada.vCelular = "";
            VariablesCampAcumulada.vEmail = "";

        } catch (SQLException e) {
            FarmaUtility.liberarTransaccion();
            log.error("", e);
        }
    }

    private String getTarjetasCliente(String pDni) {

        String numeroTarjeta = "";

        ArrayList array = new ArrayList();
        try {

            DBCampAcumulada.getTarjetasClienteLocal(array, pDni);

            if (array.size() > 0) { //encontro tarjetas para el cliente
                log.info("encontro tarjetas del cliente en local : " + array);
                numeroTarjeta = ((ArrayList)array.get(0)).get(0).toString();
            } else { //no encontro tarjetas para el cliente en local tonces buscara en matriz

                log.info("verificar si hay conexion en matriz");
                VariablesCampAcumulada.vIndConexion =
                        FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_N);

                log.info("VariablesCampAcumulada.vIndConexion:" + VariablesCampAcumulada.vIndConexion);

                if (VariablesCampAcumulada.vIndConexion.trim().equalsIgnoreCase("S")) { //busca en matriz los datos
                    DBCampAcumulada.getTarjetasClienteMatriz(array, pDni, FarmaConstants.INDICADOR_N);
                }

                if (array.size() > 0) {
                    log.info("encontro tarjetas del cliente en matriz : " + array);
                    numeroTarjeta = ((ArrayList)array.get(0)).get(0).toString();
                }
            }

        } catch (SQLException e) {
            FarmaUtility.liberarTransaccion();
            log.error("", e);
        }

        return numeroTarjeta;
    }

    /**
     * metodo encargado de asociar clientes con las campañas de ventas acumuladas
     * @param pDni
     * @param lCampanias
     */
    private void asociarClienteCampanias(String pDni, ArrayList lCampanias) {
        String pIndLinea = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
        try {
            /**asociar cliente con campañas**/

            for (int i = 0; i < lCampanias.size(); i++) {
                DBCampAcumulada.asociarClienteConCampania(pDni, lCampanias.get(i).toString().trim());
                if (pIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                    DBCampAcumulada.asociarClienteConCampaniaMatriz(pDni, lCampanias.get(i).toString().trim());
            }
            FarmaUtility.aceptarTransaccion();
            if (pIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);

        } catch (SQLException e) {
            FarmaUtility.liberarTransaccion();
            if (pIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
            log.error("", e);
        }
    }

    /**
     * metodo encargado de asociar clientes con las tarjetas y las campañas de ventas acumuladas
     * @param pDni
     * @param pNroTarjeta
     * @param lCampanias
     */
    private void asociarClienteTarjetaCampanias(String pDni, String pNroTarjeta, ArrayList lCampanias) {
        String pIndLinea = "N";
        try {

            //obteniendo indicador de linea con matriz
            pIndLinea = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
            //asociar cliente con tarjeta en local
            DBCampAcumulada.asociarClienteConTarjeta(pDni, pNroTarjeta);
            //asociar cliente con tarjeta en matriz matriz
            if (pIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                DBCampAcumulada.asociarClienteConTarjetaMatriz(pDni, pNroTarjeta);

            //asociar cliente con campanias de acumulacion de compras
            for (int i = 0; i < lCampanias.size(); i++) {
                //cliente con campanias de acumulacion en local
                DBCampAcumulada.asociarClienteConCampania(pDni, lCampanias.get(i).toString().trim());
                //cliente con campanias de acumulacion en matriz
                if (pIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                    DBCampAcumulada.asociarClienteConCampaniaMatriz(pDni, lCampanias.get(i).toString().trim());
            }

            FarmaUtility.aceptarTransaccion();
            if (pIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);


        } catch (SQLException e) {
            //rollback en local
            FarmaUtility.liberarTransaccion();
            //rollback en matriz
            if (pIndLinea.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S))
                FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_S);
            log.error("", e);
        }

    }


    /*
     *
     *
     * */

    private void cargaDatos() {
        FarmaVariables.vAceptar = false;
        DlgRegDatosCliente dlgRegDatosCliente = new DlgRegDatosCliente(myParentFrame, "", true);
        dlgRegDatosCliente.setVisible(true);
        if (FarmaVariables.vAceptar) {
            //suponiendo caso que el cliente no tiene tarjeta
            //buscar tarjeta en local, buscar en matriz
            //si no tuviera tarjeta de fidelizacion mostrar dialogo para ingreso de tarjeta
            if (VariablesFidelizacion.vNumTarjeta.trim().length() >
                0) { //quiere decir que ya tiene e ingreso el numero de tarjeta al sistema

                log.info("asociando clientes con las campanias");
                //asociar cliente con campañas.
                asociarClienteCampanias(VariablesCampAcumulada.vDniCliente, VariablesCampAcumulada.vListaCampanias);

                /**para actualizar la informacion del cliente***/
                // DUBILLUZ 15.05.2009
                setDatosCliente();
                /*
                            VariablesFidelizacion.vApeMatCliente = VariablesCampAcumulada.vApeMatCliente;
                            VariablesFidelizacion.vApePatCliente = VariablesCampAcumulada.vApePatCliente;
                            VariablesFidelizacion.vDataCliente = VariablesCampAcumulada.vDataCliente;
                            VariablesFidelizacion.vDireccion = VariablesCampAcumulada.vDireccion;
                            VariablesFidelizacion.vDniCliente = VariablesCampAcumulada.vDniCliente;
                            VariablesFidelizacion.vDocValidos = VariablesCampAcumulada.vDocValidos;
                            VariablesFidelizacion.vEmail = VariablesCampAcumulada.vEmail;
                            VariablesFidelizacion.vFecNacimiento = VariablesCampAcumulada.vFecNacimiento;
                            VariablesFidelizacion.vNomCliente = VariablesCampAcumulada.vNomCliente;
                            VariablesFidelizacion.vSexo = VariablesCampAcumulada.vSexo;
                            VariablesFidelizacion.vTelefono = VariablesCampAcumulada.vTelefono;
                            */
                /**fin de actualizar informacion del cliente**/

                ///cerrarVentana(false); no cerrara la ventana...
            } else { //no ingreso tarjeta de fidelizacion aun
                //buscar tarjetas de cliente en local , si no hay en local, buscar en matriz
                log.info("OBTENIENDO NUMERO DE TARJETA DE CLIENTE SI ES QUE TIENE");
                VariablesCampAcumulada.vNroTarjeta = getTarjetasCliente(VariablesCampAcumulada.vDniCliente);

                if (VariablesCampAcumulada.vNroTarjeta.trim().length() > 0) {
                    log.info("VariablesCampAcumulada.vNroTarjeta:" + VariablesCampAcumulada.vNroTarjeta);

                    asociarClienteCampanias(VariablesCampAcumulada.vDniCliente,
                                            VariablesCampAcumulada.vListaCampanias);

                    VariablesFidelizacion.vNumTarjeta = VariablesCampAcumulada.vNroTarjeta;
                    // DUBILLUZ 15.05.2009
                    setDatosCliente();
                    /*
                                VariablesFidelizacion.vApeMatCliente = VariablesCampAcumulada.vApeMatCliente;
                                VariablesFidelizacion.vApePatCliente = VariablesCampAcumulada.vApePatCliente;
                                VariablesFidelizacion.vDataCliente = VariablesCampAcumulada.vDataCliente;
                                VariablesFidelizacion.vDireccion = VariablesCampAcumulada.vDireccion;
                                VariablesFidelizacion.vDniCliente = VariablesCampAcumulada.vDniCliente;
                                VariablesFidelizacion.vDocValidos = VariablesCampAcumulada.vDocValidos;
                                VariablesFidelizacion.vEmail = VariablesCampAcumulada.vEmail;
                                VariablesFidelizacion.vFecNacimiento = VariablesCampAcumulada.vFecNacimiento;
                                VariablesFidelizacion.vNomCliente = VariablesCampAcumulada.vNomCliente;
                                VariablesFidelizacion.vSexo = VariablesCampAcumulada.vSexo;
                                VariablesFidelizacion.vTelefono = VariablesCampAcumulada.vTelefono;
                                */
                    //cerrarVentana(true);//con true para que cargue las campañas de fidelizacion
                    ///no cerrara la ventana...
                } else {
                    FarmaVariables.vAceptar = false;
                    DlgIngresarNumeroTarjeta dlgIngNroTarjeta = new DlgIngresarNumeroTarjeta(myParentFrame, "", true);
                    dlgIngNroTarjeta.setVisible(true);
                    if (FarmaVariables.vAceptar) { //se ingreso numero de tarjeta valido para poder usar
                        //asociar cliente con tarjeta.  1
                        //asociar cliente con campañas. 2
                        asociarClienteTarjetaCampanias(VariablesCampAcumulada.vDniCliente,
                                                       VariablesCampAcumulada.vNroTarjeta,
                                                       VariablesCampAcumulada.vListaCampanias);
                        //despues de haber asociado al cliente el numero de tarjeta y campanias
                        //asignar el numero de tarjeta del cliente en la clase de VariablesFidelizacion.vNumTarjeta
                        VariablesFidelizacion.vNumTarjeta = VariablesCampAcumulada.vNroTarjeta;

                        // DUBILLUZ 15.05.2009
                        setDatosCliente();
                        /*
                                    VariablesFidelizacion.vApeMatCliente = VariablesCampAcumulada.vApeMatCliente;
                                    VariablesFidelizacion.vApePatCliente = VariablesCampAcumulada.vApePatCliente;
                                    VariablesFidelizacion.vDataCliente = VariablesCampAcumulada.vDataCliente;
                                    VariablesFidelizacion.vDireccion = VariablesCampAcumulada.vDireccion;
                                    VariablesFidelizacion.vDniCliente = VariablesCampAcumulada.vDniCliente;
                                    VariablesFidelizacion.vDocValidos = VariablesCampAcumulada.vDocValidos;
                                    VariablesFidelizacion.vEmail = VariablesCampAcumulada.vEmail;
                                    VariablesFidelizacion.vFecNacimiento = VariablesCampAcumulada.vFecNacimiento;
                                    VariablesFidelizacion.vNomCliente = VariablesCampAcumulada.vNomCliente;
                                    VariablesFidelizacion.vSexo = VariablesCampAcumulada.vSexo;
                                    VariablesFidelizacion.vTelefono = VariablesCampAcumulada.vTelefono;
                                    */
                        // cerrarVentana(true);//con true para que cargue las campañas de fidelizacion
                        //no cerrara la ventana...
                    } else {

                        //cerrarVentana(false);
                        log.debug("no ingreso tarjeta...");
                    }
                }

            }
        }

        cargaCampañas();

    }

    private void cargaCampañas() {
        cargaCampAcumulacion(VariablesCampAcumulada.vCodProdFiltro);

        /**cargar campañas de los clientes**/
        if (VariablesFidelizacion.vDniCliente.trim().length() > 0) {
            cargarCampAcumuladaCliente(VariablesFidelizacion.vDniCliente.trim());
        }

        /**quitar de las campañas totales en las que este el cliente**/
        String cod_camp = "";
        for (int i = 0; i < tableModelInsc.getRowCount(); i++) { //campañas del cliente
            cod_camp = tableModelInsc.getValueAt(i, COL_COD_CAMP - 1).toString().trim();
            log.info("cod_camp = " + cod_camp);
            for (int k = 0; k < tableModelDisp.getRowCount(); k++) { //
                if (tableModelDisp.getValueAt(k,
                                              COL_COD_CAMP).toString().trim().equals(cod_camp)) { //quiere decir que encontro el inscrito en los disponibles
                    log.info("encontro en disponibles = " +
                             tableModelDisp.getValueAt(k, COL_COD_CAMP).toString().trim());
                    tableModelDisp.deleteRow(k); //eliminando la fila de la lista de disponibles
                    break;
                }
            }
        }
    }

    /**
     * Coloca los valores de los clientes inscritos o que se obtuvo de
     * la campaña acumulada
     * @author Dubilluz
     * @since  15.05.2009
     */
    private void setDatosCliente() {

        log.debug("set de variables de campaña");

        VariablesFidelizacion.vApePatCliente =
                (VariablesCampAcumulada.vApePatCliente.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) ? "" :
                VariablesCampAcumulada.vApePatCliente.trim();


        VariablesFidelizacion.vApeMatCliente =
                (VariablesCampAcumulada.vApeMatCliente.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) ? "" :
                VariablesCampAcumulada.vApeMatCliente.trim();

        VariablesFidelizacion.vDataCliente =
                (VariablesCampAcumulada.vDataCliente.size() > 0) ? VariablesCampAcumulada.vDataCliente :
                VariablesFidelizacion.vDataCliente;

        VariablesFidelizacion.vDireccion =
                (VariablesCampAcumulada.vDireccion.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) ? "" :
                VariablesCampAcumulada.vDireccion.trim();

        VariablesFidelizacion.vDniCliente =
                (VariablesCampAcumulada.vDniCliente.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) ? "" :
                VariablesCampAcumulada.vDniCliente.trim();

        VariablesFidelizacion.vDocValidos =
                (VariablesCampAcumulada.vDocValidos.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) ? "" :
                VariablesCampAcumulada.vDocValidos.trim();

        VariablesFidelizacion.vEmail =
                (VariablesCampAcumulada.vEmail.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) ? "" :
                VariablesCampAcumulada.vEmail.trim();

        VariablesFidelizacion.vFecNacimiento =
                (VariablesCampAcumulada.vFecNacimiento.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) ? "" :
                VariablesCampAcumulada.vFecNacimiento.trim();

        VariablesFidelizacion.vNomCliente =
                (VariablesCampAcumulada.vNomCliente.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) ? "" :
                VariablesCampAcumulada.vNomCliente.trim();

        VariablesFidelizacion.vSexo =
                (VariablesCampAcumulada.vSexo.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) ? "" :
                VariablesCampAcumulada.vSexo.trim();

        VariablesFidelizacion.vTelefono =
                (VariablesCampAcumulada.vTelefono.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) ? "" :
                VariablesCampAcumulada.vTelefono.trim();

        /*
        VariablesFidelizacion.vApePatCliente = VariablesCampAcumulada.vApePatCliente;
        VariablesFidelizacion.vApePatCliente = VariablesCampAcumulada.vApePatCliente;
        VariablesFidelizacion.vDataCliente = VariablesCampAcumulada.vDataCliente;
        VariablesFidelizacion.vDireccion = VariablesCampAcumulada.vDireccion;
        VariablesFidelizacion.vDniCliente = VariablesCampAcumulada.vDniCliente;
        VariablesFidelizacion.vDocValidos = VariablesCampAcumulada.vDocValidos;
        VariablesFidelizacion.vEmail = VariablesCampAcumulada.vEmail;
        VariablesFidelizacion.vFecNacimiento = VariablesCampAcumulada.vFecNacimiento;
        VariablesFidelizacion.vNomCliente = VariablesCampAcumulada.vNomCliente;
        VariablesFidelizacion.vSexo = VariablesCampAcumulada.vSexo;
        VariablesFidelizacion.vTelefono = VariablesCampAcumulada.vTelefono;
        */
    }
}
