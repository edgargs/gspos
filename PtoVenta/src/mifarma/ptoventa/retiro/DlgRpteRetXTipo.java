package mifarma.ptoventa.retiro;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaLoadCVL;
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
 * Nombre de la Aplicación : DlgRpteRetXTipo <br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ASOSA 10.12.2009 Creación<br>
 * <br>
 * @author Alfredo Sosa Dordán<br>
 * @version 1.0<br>
 *
 */

public class DlgRpteRetXTipo extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgRpteRetXTipo.class);

    private JPanelWhite pnlFondo = new JPanelWhite();
    private JPanelHeader pnlHeader = new JPanelHeader();
    private JPanelTitle pnlTitulo = new JPanelTitle();
    private JScrollPane srcLista = new JScrollPane();
    private JTable tblLista = new JTable();
    private JButtonLabel btnlfecini = new JButtonLabel();
    private JButtonLabel btnlfecfin = new JButtonLabel();
    private JButtonLabel btnlLocal = new JButtonLabel();
    private JTextFieldSanSerif txtfecini = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtfecfin = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtcodloc = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtnomloc = new JTextFieldSanSerif();
    private JButtonLabel btnlLista = new JButtonLabel();
    FarmaTableModel tableModel;
    private JLabelFunction lblENTER = new JLabelFunction();
    private JComboBox cmbInd = new JComboBox();
    private JButtonLabel btncombo = new JButtonLabel();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgRpteRetXTipo() {
        this(null, "", false);
    }

    public DlgRpteRetXTipo(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(801, 397));
        this.getContentPane().setLayout(null);
        this.setTitle("Rpte de Retiros de VB Procesados");
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
        pnlFondo.setBounds(new Rectangle(0, 0, 800, 375));
        pnlFondo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pnlFondo_keyPressed(e);
            }
        });
        pnlHeader.setBounds(new Rectangle(10, 10, 775, 40));
        pnlHeader.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pnlHeader_keyPressed(e);
            }
        });
        pnlTitulo.setBounds(new Rectangle(10, 55, 775, 25));
        pnlTitulo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pnlTitulo_keyPressed(e);
            }
        });
        srcLista.setBounds(new Rectangle(10, 80, 775, 275));
        srcLista.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                srcLista_keyPressed(e);
            }
        });
        tblLista.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                tblLista_keyPressed(e);
            }
        });
        btnlfecini.setText("Inicio : ");
        btnlfecini.setBounds(new Rectangle(220, 10, 40, 15));
        btnlfecini.setMnemonic('i');
        btnlfecini.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnlfecini_actionPerformed(e);
            }
        });
        btnlfecini.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnlfecini_keyPressed(e);
            }
        });
        txtfecini.setDocument(new FarmaLengthText(10));
        btnlfecfin.setText("Fin : ");
        btnlfecfin.setBounds(new Rectangle(350, 10, 30, 15));
        btnlfecfin.setMnemonic('f');
        btnlfecfin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnlfecfin_actionPerformed(e);
            }
        });
        btnlfecfin.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnlfecfin_keyPressed(e);
            }
        });
        txtfecfin.setDocument(new FarmaLengthText(10));
        btnlLocal.setText("Local : ");
        btnlLocal.setBounds(new Rectangle(590, 10, 40, 15));
        btnlLocal.setMnemonic('l');
        btnlLocal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnlLocal_actionPerformed(e);
            }
        });
        btnlLocal.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnlLocal_keyPressed(e);
            }
        });
        txtfecini.setBounds(new Rectangle(260, 10, 85, 20));
        txtfecini.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtfecini_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtfecini_keyReleased(e);
            }
        });
        txtfecfin.setBounds(new Rectangle(380, 10, 90, 20));
        txtfecfin.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtfecfin_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtfecfin_keyReleased(e);
            }
        });
        txtcodloc.setBounds(new Rectangle(630, 10, 30, 20));
        txtcodloc.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtcodloc_keyPressed(e);
            }
        });
        txtcodloc.setDocument(new FarmaLengthText(3));
        txtnomloc.setBounds(new Rectangle(660, 10, 110, 20));
        txtnomloc.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtnomloc_keyPressed(e);
            }
        });
        btnlLista.setText("Lista de Retiros de VB Procesados");
        btnlLista.setBounds(new Rectangle(15, 5, 200, 15));
        btnlLista.setMnemonic('v');
        btnlLista.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnlLista_actionPerformed(e);
            }
        });
        btnlLista.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btnlLista_keyPressed(e);
            }
        });
        lblENTER.setBounds(new Rectangle(475, 10, 117, 19));
        lblENTER.setText("[ ENTER ] BUSCAR");
        lblENTER.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                jLabelFunction1_keyPressed(e);
            }
        });
        cmbInd.setBounds(new Rectangle(90, 10, 125, 20));
        cmbInd.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                cmbInd_keyPressed(e);
            }
        });
        cmbInd.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                cmbInd_itemStateChanged(e);
            }
        });
        btncombo.setText("Indicador :");
        btncombo.setBounds(new Rectangle(20, 10, 65, 15));
        btncombo.setMnemonic('d');
        btncombo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                btncombo_keyPressed(e);
            }
        });
        btncombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btncombo_actionPerformed(e);
            }
        });
        srcLista.getViewport().add(tblLista, null);
        pnlFondo.add(srcLista, null);
        pnlTitulo.add(btnlLista, null);
        pnlFondo.add(pnlTitulo, null);
        pnlHeader.add(btncombo, null);
        pnlHeader.add(cmbInd, null);
        pnlHeader.add(lblENTER, null);
        pnlHeader.add(txtnomloc, null);
        pnlHeader.add(txtcodloc, null);
        pnlHeader.add(txtfecfin, null);
        pnlHeader.add(txtfecini, null);
        pnlHeader.add(btnlLocal, null);
        pnlHeader.add(btnlfecfin, null);
        pnlHeader.add(btnlfecini, null);
        pnlFondo.add(pnlHeader, null);
        this.getContentPane().add(pnlFondo, null);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        initTable();
        txtnomloc.setEnabled(false);
        cargaCombo();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    private void initTable() {
        tableModel = new FarmaTableModel(ConstantsRetiro.columnsRpteRetiros, ConstantsRetiro.defaultRpteRetiros, 0);
        FarmaUtility.initSimpleList(tblLista, tableModel, ConstantsRetiro.columnsRpteRetiros);
    }

    private void cargaCombo() {
        FarmaLoadCVL.loadCVLfromArrays(cmbInd, ConstantsRetiro.HASHTABLE_RETIROS_PROC,
                                       ConstantsRetiro.FILTROS_RETIROS_IND_VALUE,
                                       ConstantsRetiro.FILTROS_RETIROS_IND_DESC, true);
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void txtfecini_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtfecfin);
        } else
            chkKeyPressed(e);
    }

    private void txtfecini_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtfecini, e);
    }

    private void txtfecfin_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (cmbInd.getSelectedIndex() == 0) {
                VariablesRetiro.IND_PROCESADO = "S";
            } else if (cmbInd.getSelectedIndex() == 1) {
                VariablesRetiro.IND_PROCESADO = "N";
            } else if (cmbInd.getSelectedIndex() == 2) {
                VariablesRetiro.IND_PROCESADO = " ";
            }
            VariablesRetiro.FEC_INI = txtfecini.getText();
            VariablesRetiro.FEC_FIN = txtfecfin.getText();
            listarRetirosVB();
        } else
            chkKeyPressed(e);
    }

    private void txtfecfin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtfecfin, e);
    }

    private void btnlfecini_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtfecini);
    }

    private void btnlfecfin_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtfecfin);
    }

    private void btnlLocal_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtcodloc);
    }

    private void btnlLista_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblLista);
    }

    private void srcLista_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
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
                    FarmaUtility.showMessage(this, "Ingrese codigo de local", txtcodloc);
                }
            }
        } else
            chkKeyPressed(e);
    }

    private void btnbuscar_actionPerformed(ActionEvent e) {
        listarRetirosVB();
    }

    private void this_windowOpened(WindowEvent e) {
        btnlLocal.setVisible(false);
        txtcodloc.setVisible(false);
        txtnomloc.setVisible(false);
        lblENTER.setVisible(false);
        FarmaUtility.moveFocus(cmbInd);
        FarmaUtility.centrarVentana(this);
    }

    private void jLabelFunction1_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void this_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void pnlFondo_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void pnlHeader_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void pnlTitulo_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void tblLista_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnlLista_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnlfecini_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnlfecfin_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btnlLocal_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtnomloc_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void cmbInd_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtfecini);
        } else
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
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    public void listarRetirosVB() {
        try {
            DBRetiro.getlistaRpteRetiros(tableModel);
            //FarmaUtility.aceptarTransaccion();
        } catch (SQLException e) {
            //FarmaUtility.liberarTransaccion();
            log.error("", e);
        }
        if (tableModel.getRowCount() <= 0) {
            FarmaUtility.showMessage(this, "No se encontraron resultado para la búsqueda", txtfecini);
        }
    }

    public boolean validarCampos() {
        boolean flag = true;
        if (txtfecini.getText().trim().equals("")) {
            flag = false;
            FarmaUtility.showMessage(this, "Ingrese Fecha inicial", txtfecini);
        } else if (!FarmaUtility.validaFecha(txtfecini.getText(), "dd/MM/yyyy")) {
            flag = false;
            txtfecini.setText("");
            FarmaUtility.showMessage(this, "Ingrese la fecha inicial correctamente", txtfecini);
        } else if (txtfecfin.getText().trim().equals("")) {
            flag = false;
            FarmaUtility.showMessage(this, "Ingrese Fecha Final", txtfecfin);
        } else if (!FarmaUtility.validaFecha(txtfecfin.getText(), "dd/MM/yyyy")) {
            flag = false;
            txtfecfin.setText("");
            FarmaUtility.showMessage(this, "Ingrese la fecha final correctamente", txtfecfin);
        } else if (!FarmaUtility.validarRangoFechas(this, txtfecini, txtfecfin, false, true, true, true)) {
            flag = false;
            txtfecini.setText("");
            txtfecfin.setText("");
        }
        return flag;
    }

    public void buscarLocal() {
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
            FarmaUtility.showMessage(this, "No existe local con ese codigo", txtcodloc);
        } else {
            txtnomloc.setText(nombrelocal);
            listarRetirosVB();
        }
    }

    private boolean validarCodigo(String num) {
        boolean flag = true;
        if (!(FarmaUtility.isInteger(num) && Integer.parseInt(num) > 0))
            flag = false;
        return flag;
    }

    private void cmbInd_itemStateChanged(ItemEvent e) {
    }

    private void btncombo_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void btncombo_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(cmbInd);
    }
}
