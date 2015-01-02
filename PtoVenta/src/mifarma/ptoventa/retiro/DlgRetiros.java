package mifarma.ptoventa.retiro;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

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

import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.retiro.reference.ConstantsRetiro;
import mifarma.ptoventa.retiro.reference.DBRetiro;
import mifarma.ptoventa.retiro.reference.VariablesRetiro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2009 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgRetiros <br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ASOSA 04.12.2009 Creación<br>
 * <br>
 * @author Alfredo Sosa Dordán<br>
 * @version 1.0<br>
 *
 */

public class DlgRetiros extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgRetiros.class);

    private JPanelWhite jPanelWhite2 = new JPanelWhite();
    private JPanelHeader pnlcabeceraparam = new JPanelHeader();
    private JButtonLabel btnini = new JButtonLabel();
    private JButtonLabel btnfin = new JButtonLabel();
    private JTextFieldSanSerif txtini = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtfin = new JTextFieldSanSerif();
    private JPanelTitle pnlcabeceralista = new JPanelTitle();
    private JButtonLabel btnlista = new JButtonLabel();
    private JScrollPane srclista = new JScrollPane();
    private JTable tblRetiros = new JTable();

    FarmaTableModel tableModel;
    private Frame myParentFrame;
    private JButtonLabel btncodlocal = new JButtonLabel();
    private JTextFieldSanSerif txtcodloc = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtnomlocal = new JTextFieldSanSerif();
    private JLabelFunction lblbuscar = new JLabelFunction();
    private JLabelFunction lblfvbcont = new JLabelFunction();
    private JLabelFunction lblfproc = new JLabelFunction();
    private JLabelFunction lblfvbdiario = new JLabelFunction();
    private JLabelFunction lblfvbturno = new JLabelFunction();


    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgRetiros() {
        this(null, "", false);
    }

    public DlgRetiros(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
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
        this.setSize(new Dimension(844, 356));
        this.getContentPane().setLayout(null);
        this.setBackground(new Color(247, 247, 247));
        this.setTitle("Retiros de VB ");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                this_keyPressed(e);
            }
        });
        jPanelWhite2.setBounds(new Rectangle(0, 0, 840, 330));
        jPanelWhite2.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jPanelWhite2_keyPressed(e);
            }
        });
        pnlcabeceraparam.setBounds(new Rectangle(10, 10, 820, 40));
        pnlcabeceraparam.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pnlcabeceraparam_keyPressed(e);
            }
        });
        btnini.setText("Inicio : ");
        btnini.setBounds(new Rectangle(10, 10, 45, 15));
        btnini.setMnemonic('i');
        btnini.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnini_actionPerformed(e);
            }
        });
        btnini.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnini_keyPressed(e);
            }
        });
        btnfin.setText("Fin : ");
        btnfin.setBounds(new Rectangle(155, 10, 30, 15));
        btnfin.setMnemonic('f');
        btnfin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnfin_actionPerformed(e);
            }
        });
        btnfin.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnfin_keyPressed(e);
            }
        });

        txtini.setBounds(new Rectangle(50, 10, 90, 20));
        txtini.setSize(new Dimension(90, 20));
        txtini.setMaximumSize(new Dimension(10, 10));
        txtini.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                txtini_keyReleased(e);
            }

            public void keyPressed(KeyEvent e) {
                txtini_keyPressed(e);
            }
        });
        txtini.setDocument(new FarmaLengthText(10));
        txtfin.setBounds(new Rectangle(180, 10, 80, 20));
        txtfin.setMaximumSize(new Dimension(10, 10));
        txtfin.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtfin_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtfin_keyReleased(e);
            }
        });
        txtfin.setDocument(new FarmaLengthText(10));
        pnlcabeceralista.setBounds(new Rectangle(10, 60, 820, 25));
        pnlcabeceralista.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pnlcabeceralista_keyPressed(e);
            }
        });
        btnlista.setText("Lista de Retiros de VB ");
        btnlista.setBounds(new Rectangle(5, 5, 215, 15));
        btnlista.setMnemonic('r');
        btnlista.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnlista_actionPerformed(e);
            }
        });
        btnlista.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnlista_keyPressed(e);
            }
        });
        srclista.setBounds(new Rectangle(10, 85, 820, 205));
        srclista.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                srclista_keyPressed(e);
            }
        });
        tblRetiros.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblRetiros_keyPressed(e);
            }
        });
        btncodlocal.setText("Local : ");
        btncodlocal.setBounds(new Rectangle(325, 10, 40, 15));
        btncodlocal.setMnemonic('l');
        btncodlocal.setPreferredSize(new Dimension(25, 15));
        btncodlocal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonLabel1_actionPerformed(e);
            }
        });
        btncodlocal.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btncodlocal_keyPressed(e);
            }
        });
        txtcodloc.setBounds(new Rectangle(365, 10, 30, 20));
        txtcodloc.setMaximumSize(new Dimension(3, 3));
        txtcodloc.setMinimumSize(new Dimension(3, 3));
        txtcodloc.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                //try {
                txtcodloc_keyPressed(e);
                //} catch (Exception f) {
                //
                //}
            }
        });
        txtcodloc.setDocument(new FarmaLengthText(3));
        txtnomlocal.setBounds(new Rectangle(400, 10, 150, 20));
        txtnomlocal.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtnomlocal_keyPressed(e);
            }
        });
        lblbuscar.setBounds(new Rectangle(560, 10, 105, 20));
        lblbuscar.setText("[ ENTER ] BUSCAR");
        lblbuscar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jLabelFunction1_keyPressed(e);
            }
        });
        lblfvbcont.setBounds(new Rectangle(10, 300, 125, 20));
        lblfvbcont.setText("[ F2 ] Retiro contable");
        lblfvbcont.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jLabelFunction1_keyPressed(e);
            }
        });
        lblfproc.setBounds(new Rectangle(655, 300, 175, 20));
        lblfproc.setText("[ F3 ] Procesar Retiros de VB");
        lblfproc.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jLabelFunction2_keyPressed(e);
            }
        });
        lblfvbdiario.setBounds(new Rectangle(140, 300, 117, 19));
        lblfvbdiario.setText("[ F5 ] Retiro diario");
        lblfvbdiario.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                lblfvbdiario_keyPressed(e);
            }
        });
        lblfvbturno.setBounds(new Rectangle(260, 300, 130, 20));
        lblfvbturno.setText("[ F7 ] Retiro de Turno");
        lblfvbturno.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                lblfvbturno_keyPressed(e);
            }
        });
        pnlcabeceraparam.add(lblbuscar, null);
        pnlcabeceraparam.add(txtnomlocal, null);
        pnlcabeceraparam.add(txtcodloc, null);
        pnlcabeceraparam.add(btncodlocal, null);
        pnlcabeceraparam.add(txtfin, null);
        pnlcabeceraparam.add(txtini, null);
        pnlcabeceraparam.add(btnfin, null);
        pnlcabeceraparam.add(btnini, null);
        srclista.getViewport().add(tblRetiros, null);
        jPanelWhite2.add(lblfvbturno, null);
        jPanelWhite2.add(lblfvbdiario, null);
        jPanelWhite2.add(lblfproc, null);
        jPanelWhite2.add(lblfvbcont, null);
        jPanelWhite2.add(srclista, null);
        pnlcabeceralista.add(btnlista, null);
        jPanelWhite2.add(pnlcabeceralista, null);
        jPanelWhite2.add(pnlcabeceraparam, null);
        this.getContentPane().add(jPanelWhite2, null);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initTable();
        modificarEtiquetas();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        tableModel = new FarmaTableModel(ConstantsRetiro.columnsListaRetiro, ConstantsRetiro.defaultListaRetiro, 0);
        FarmaUtility.initSimpleList(tblRetiros, tableModel, ConstantsRetiro.columnsListaRetiro);
    }

    public void modificarEtiquetas() {
        txtnomlocal.setEnabled(false);
    }


    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void txtini_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtini, e);
    }

    private void txtini_keyPressed(KeyEvent e) {
        /*String digito=String.valueOf(e.getKeyChar());
          if("1234567890".indexOf(digito)<0){
              log.debug("ddddddddddddd: "+digito);
              e.setKeyCode(0);
          }*/
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtfin);
        } else
            chkKeyPressed(e);
    }


    private void txtfin_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            VariablesRetiro.COD_LOCAL = " ";
            VariablesRetiro.FEC_INI = txtini.getText();
            VariablesRetiro.FEC_FIN = txtfin.getText();
            listarRetirosVB();
        } else
            chkKeyPressed(e);
    }

    private void txtfin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtfin, e);
    }

    private void btnbus_actionPerformed(ActionEvent e) {
        listarRetirosVB();
    }

    private void txtcodloc_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (validarCampos()) {
                String codloc = txtcodloc.getText().trim();
                if (!codloc.equals("")) {
                    if (validarCodigo(codloc)) {
                        buscarLocal();
                    } else {
                        txtcodloc.setText("");
                        FarmaUtility.showMessage(this, "Ingrese solo números en el codigo de local", txtcodloc);
                    }
                } else {
                    FarmaUtility.showMessage(this, "Ingrese Codigo de Local", txtcodloc);
                }
            }
        } else
            chkKeyPressed(e);
    }

    private void btnfin_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtfin);
    }

    private void btnini_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtini);
    }

    private void jButtonLabel1_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtcodloc);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(txtini);
        FarmaUtility.centrarVentana(this);
        VariablesRetiro.COD_LOCAL = "X";
        VariablesRetiro.FEC_INI = " ";
        VariablesRetiro.FEC_FIN = " ";
        listarRetirosVB();
    }

    private void tblRetiros_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnini_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnfin_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btncodlocal_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtnomlocal_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnbus_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void srclista_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnNuevoRetiro_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnProcesarRetiros_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnlista_actionPerformed(ActionEvent e) {
        if (tableModel.getRowCount() > 0) {
            FarmaUtility.moveFocus(tblRetiros);
        }
    }


    private void this_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void jLabelFunction1_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void jLabelFunction2_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnlista_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void jPanelWhite2_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void pnlcabeceraparam_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void pnlcabeceralista_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */


    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void chkKeyPressed(KeyEvent e) {
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F2(e)) {
            VariablesRetiro.TIP_CIERRE = "C";
            abrirNuevoReqRetiro();
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            VariablesRetiro.TIP_CIERRE = "D";
            abrirNuevoReqRetiro();
        } else if (e.getKeyCode() == KeyEvent.VK_F7) {
            VariablesRetiro.TIP_CIERRE = "T";
            abrirNuevoReqRetiro();
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            procesarRetiros();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    private void listarRetirosVB() {
        try {
            DBRetiro.getListaRetiros(tableModel);
            //FarmaUtility.aceptarTransaccion();
        } catch (SQLException e) {
            FarmaUtility.showMessage(this, "ERROR en listar Retiros VB: \n" +
                    e.getMessage(), txtcodloc);
            log.error("", e);
        }
        if (tableModel.getRowCount() <= 0) {
            FarmaUtility.showMessage(this, "No se encontraron resultado para la búsqueda", txtini);
        }
    }

    public void abrirNuevoReqRetiro() {
        String titulo = "";
        if (VariablesRetiro.TIP_CIERRE.equalsIgnoreCase("T")) {
            titulo = "Nuevo Retiro de VB de Turno";
        }
        if (VariablesRetiro.TIP_CIERRE.equalsIgnoreCase("D")) {
            titulo = "Nuevo Retiro de VB Diario";
        }
        if (VariablesRetiro.TIP_CIERRE.equalsIgnoreCase("C")) {
            titulo = "Nuevo Retiro de VB Contable";
        }
        DlgNuevoRetiro objnew01 = new DlgNuevoRetiro(myParentFrame, titulo, true);
        objnew01.setVisible(true);
        if (FarmaVariables.vAceptar) {
            VariablesRetiro.COD_LOCAL = "X";
            VariablesRetiro.FEC_INI = " ";
            VariablesRetiro.FEC_FIN = " ";
            listarRetirosVB();
        }
    }

    public void procesarRetiros() {
        DlgProcRetiros objDlgProc = new DlgProcRetiros(myParentFrame, "", true);
        objDlgProc.setVisible(true);
        if (FarmaVariables.vAceptar) {
            listarRetirosVB();
        }
    }

    public boolean validarCampos() {
        boolean flag = true;
        if (txtini.getText().trim().equals("")) {
            flag = false;
            FarmaUtility.showMessage(this, "Ingrese Fecha inicial", txtini);
        } else if (!FarmaUtility.validaFecha(txtini.getText(), "dd/MM/yyyy")) {
            flag = false;
            txtini.setText("");
            FarmaUtility.showMessage(this, "Ingrese la fecha inicial correctamente", txtini);
        } else if (txtfin.getText().trim().equals("")) {
            flag = false;
            FarmaUtility.showMessage(this, "Ingrese Fecha Final", txtfin);
        } else if (!FarmaUtility.validaFecha(txtfin.getText(), "dd/MM/yyyy")) {
            flag = false;
            txtfin.setText("");
            FarmaUtility.showMessage(this, "Ingrese la fecha final correctamente", txtfin);
        } else if (!FarmaUtility.validarRangoFechas(this, txtini, txtfin, false, true, true, true)) {
            flag = false;
            txtini.setText("");
            txtfin.setText("");
        }
        return flag;
    }

    public void buscarLocal() {
        if (txtcodloc.getText().trim() != "") {
            tableModel.clearTable();
            VariablesRetiro.COD_LOCAL = txtcodloc.getText();
            String nombrelocal = "";
            try {
                nombrelocal = DBRetiro.obtenerNombreLocal();
                //FarmaUtility.aceptarTransaccion();
            } catch (SQLException e) {
                //FarmaUtility.liberarTransaccion();
                log.error("", e);
                nombrelocal = "";
            }
            if (nombrelocal.trim() == "") {
                txtcodloc.setText("");
                FarmaUtility.showMessage(this, "No existe local con ese codigo", txtcodloc);
            } else {
                txtnomlocal.setText(nombrelocal);
                VariablesRetiro.COD_LOCAL = txtcodloc.getText().trim();
                VariablesRetiro.FEC_INI = txtini.getText();
                VariablesRetiro.FEC_FIN = txtfin.getText();
                listarRetirosVB();
            }
        } else {
            FarmaUtility.showMessage(this, "Ingrese Codigo de Local", txtcodloc);
        }
    }

    private boolean validarCodigo(String num) {
        boolean flag = true;
        if (!(FarmaUtility.isInteger(num) && Integer.parseInt(num) > 0))
            flag = false;
        return flag;
    }

    private void lblfvbdiario_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void lblfvbturno_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }
}
