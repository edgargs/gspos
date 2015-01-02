package mifarma.ptoventa.fidelizacion;


import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.fidelizacion.reference.AuxiliarFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.UtilityFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgBusquedaMedicoCamp extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgBusquedaMedicoCamp.class);
    Frame myParentFrame;
    FarmaTableModel tableModel;
    private JPanelWhite pnlContent = new JPanelWhite();
    private JPanelHeader pnlBusqueda = new JPanelHeader();
    private JLabelWhite lblBuscar = new JLabelWhite();
    private JTextFieldSanSerif txtBuscar = new JTextFieldSanSerif();
    private JLabelFunction lblEnter = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();

    private JLabel lblMedico;
    private JLabel lblMsjCampana;
    private JLabel lblCli;
    private JDialog dlgUno;
    private JLabel lbllineaUno = new JLabel();
    private JLabel lbllineaDos = new JLabel();
    private JLabel lbllineaTres = new JLabel();
    private JLabel lbllineaCuatro = new JLabel();
    private JTabbedPane jTabbedPane1 = new JTabbedPane();
    private String pTipo = "";
    private JLabel lblSinComision = new JLabel();

    public DlgBusquedaMedicoCamp() {
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public DlgBusquedaMedicoCamp(Frame parent, String title, boolean modal, JLabel DlglblMedico,
                                 JLabel DlglblMsjCampana, JLabel DlglblCli, JDialog ObjDlg, String pTipo_in,
                                 JLabel lblDniComision) {
        super(parent, title, modal);
        myParentFrame = parent;
        lblMedico = DlglblMedico;
        lblMsjCampana = DlglblMsjCampana;
        lblCli = DlglblCli;
        dlgUno = ObjDlg;
        pTipo = pTipo_in;
        lblSinComision = lblDniComision;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(299, 108));
        this.setTitle("Buscar Médico");
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }

            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });
        pnlBusqueda.setBounds(new Rectangle(5, 5, 285, 50));
        lblBuscar.setText("Ingrese Numero CMP :");
        lblBuscar.setBounds(new Rectangle(5, 15, 125, 20));
        txtBuscar.setBounds(new Rectangle(135, 15, 135, 20));
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtBuscar_keyPressed(e);
            }

        });
        //txtBuscar.setLengthText(20);
        lblEnter.setBounds(new Rectangle(90, 60, 90, 20));
        lblEnter.setText("[Enter] Buscar");
        lblEsc.setBounds(new Rectangle(195, 60, 85, 20));
        lblEsc.setText("[ Esc ] Salir");
        lbllineaUno.setText("Médico actualmente ingresado :");
        lbllineaUno.setBounds(new Rectangle(10, 100, 275, 25));
        lbllineaUno.setFont(new Font("Dialog", 3, 11));
        lbllineaUno.setForeground(new Color(0, 132, 0));
        lbllineaDos.setText("CMP: 2223");
        lbllineaDos.setBounds(new Rectangle(10, 120, 265, 20));
        lbllineaDos.setFont(new Font("Dialog", 3, 11));
        lbllineaDos.setForeground(new Color(0, 132, 0));
        lbllineaTres.setText("Colegio Médico");
        lbllineaTres.setBounds(new Rectangle(10, 140, 270, 20));
        lbllineaTres.setFont(new Font("Dialog", 3, 11));
        lbllineaTres.setForeground(new Color(0, 132, 0));
        lbllineaCuatro.setText("Carlos Antonio Quispe");
        lbllineaCuatro.setBounds(new Rectangle(10, 160, 270, 20));
        lbllineaCuatro.setFont(new Font("Dialog", 3, 11));
        lbllineaCuatro.setForeground(new Color(0, 132, 0));
        pnlBusqueda.add(txtBuscar, null);
        pnlBusqueda.add(lblBuscar, null);
        pnlContent.add(lbllineaCuatro, null);
        pnlContent.add(lbllineaTres, null);
        pnlContent.add(lbllineaDos, null);
        pnlContent.add(lbllineaUno, null);
        pnlContent.add(pnlBusqueda, null);
        pnlContent.add(lblEsc, null);
        pnlContent.add(lblEnter, null);
        lbllineaUno.setVisible(false);
        lbllineaDos.setVisible(false);
        lbllineaTres.setVisible(false);
        lbllineaCuatro.setVisible(false);
        this.getContentPane().add(pnlContent, null);
        this.getContentPane().add(jTabbedPane1, null);
    }

    private void initialize() {
        FarmaUtility.moveFocus(txtBuscar);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", txtBuscar);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(txtBuscar);
        FarmaUtility.centrarVentana(this);
        evaluaIngreso();
    }

    public void cerrarVentana(boolean vResp) {
        FarmaVariables.vAceptar = vResp;
        this.setVisible(false);
        this.dispose();
    }

    private void txtBuscar_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            log.debug("ENTER ");
            eventoEnter(lblMedico, lblMsjCampana);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            eventoEscape();
        }
    }


    private void tblLista_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }


    public void limpiar() {
        txtBuscar.setText("");
        FarmaUtility.moveFocus(txtBuscar);
    }


    boolean isNumero(String pCadena) {
        int n;
        try {
            n = Integer.parseInt(pCadena.trim());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public void eventoEnter(JLabel lblObj, JLabel lblMsjCampana) {
        String pCadena = txtBuscar.getText().trim();
        if (pCadena.trim().length() > 0) {
            String pIngresoMedido = pCadena.trim();
            log.debug("pIngresoMedido:" + pIngresoMedido);
            if (pIngresoMedido.trim().length() > 0) {
                log.debug("valida si existe el medico");
                String pExisteMedico = UtilityFidelizacion.getExisteMedido(this, myParentFrame, pIngresoMedido.trim());
                log.debug("pExisteMedico:" + pExisteMedico);
                evaluaIngreso();

                if (pExisteMedico.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S)) {
                    if (VariablesFidelizacion.vNumTarjeta.trim().length() > 0) {
                        log.debug(">>> ya existe DNI ingresado");
                    } else {
                        log.debug(">>> NO EXISTE DNI ingresado");

                        AuxiliarFidelizacion.funcionF12("N", txtBuscar, this.myParentFrame, lblMsjCampana, lblCli,
                                                        dlgUno, pTipo, lblSinComision);
                    }
                    if (VariablesFidelizacion.vNumTarjeta.trim().length() > 0) {
                        log.debug(">>> BUSCA campañas para agregar las q tiene asociado ese tipo de colegio");
                        UtilityFidelizacion.agregaCampanaMedicoAuto(VariablesFidelizacion.vNumTarjeta,
                                                                    VariablesFidelizacion.V_TIPO_COLEGIO.trim(),
                                                                    VariablesFidelizacion.V_OLD_TIPO_COLEGIO);
                        //VariablesFidelizacion.vColegioMedico = pIngresoMedido.trim();
                        ///////////////////////////////////////////////
                        VariablesFidelizacion.vColegioMedico = VariablesFidelizacion.V_NUM_CMP;
                        ///////////////////////////////////////////////
                        cerrarVentana(true);
                        lblObj.setText(VariablesFidelizacion.V_NUM_CMP + "-" + VariablesFidelizacion.V_NOMBRE);
                        log.debug(">>> agrego campna..");
                    } else {
                        ///////////////////////////////////////////////
                        UtilityFidelizacion.limpiaVariablesMedico();
                        evaluaIngreso();
                        ///////////////////////////////////////////////
                    }
                } else {
                    if (VariablesFidelizacion.vColegioMedico.trim().length() > 0 &&
                        VariablesFidelizacion.vNumTarjeta.trim().length() > 0)
                        UtilityFidelizacion.quitarCampanaMedico(VariablesFidelizacion.vNumTarjeta,
                                                                VariablesFidelizacion.V_TIPO_COLEGIO);

                    // NO EXISTE EL MEDICO
                    if (pExisteMedico.trim().equalsIgnoreCase(FarmaConstants.INDICADOR_N)) {
                        FarmaUtility.showMessage(this, "No existe el Médico Seleccionado. Verifique!!", txtBuscar);
                    }

                    UtilityFidelizacion.limpiaVariablesMedico();

                }
            }
        } else {
            FarmaUtility.showMessage(this, "Debe ingresar el número de CMP.", txtBuscar);
        }

    }

    public void evaluaIngreso() {
        if (VariablesFidelizacion.V_NUM_CMP.trim().length() > 0) {
            this.setSize(new Dimension(301, 214));
            lbllineaDos.setText("      CMP: " + VariablesFidelizacion.V_NUM_CMP);
            lbllineaTres.setText("      " + VariablesFidelizacion.V_DESC_TIP_COLEGIO);
            lbllineaCuatro.setText("      " + VariablesFidelizacion.V_NOMBRE);
            lbllineaUno.setVisible(true);
            lbllineaDos.setVisible(true);
            lbllineaTres.setVisible(true);
            lbllineaCuatro.setVisible(true);
        } else {
            this.setSize(new Dimension(299, 108));
            lbllineaUno.setVisible(false);
            lbllineaDos.setVisible(false);
            lbllineaTres.setVisible(false);
            lbllineaCuatro.setVisible(false);
        }
    }

    public void eventoEscape() {
        if (VariablesFidelizacion.vNumTarjeta.trim().length() > 0) {
            //ESTA FIDELIZADO y desea DAR ESCAPE
            log.debug("--");

        } else {
            //NO SE FIDELIZO
            UtilityFidelizacion.limpiaVariablesMedico();
        }

        cerrarVentana(false);
    }

}
