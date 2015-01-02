package mifarma.ptoventa.otros;


import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

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

import javax.swing.JDialog;

import mifarma.common.FarmaConnectionRemoto;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.otros.reference.DBOtros;
import mifarma.ptoventa.otros.reference.VariablesOtros;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgModMedidaPresion.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCALLO      21.10.2008   Creación<br>
 * <br>
 * @author Javier Callo Quispe<br>
 * @version 1.0<br>
 *
 */
public class DlgModMedidaPresion extends JDialog {
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(DlgModMedidaPresion.class);

    private Frame myParentFrame;

    private BorderLayout borderLayout1 = new BorderLayout();

    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader pnlHeader1 = new JPanelHeader();

    private JTextFieldSanSerif txtMaxSistolica = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtMinDiastolica = new JTextFieldSanSerif();

    private JLabelWhite lblDNI = new JLabelWhite();
    private JLabelWhite lblDNI_T = new JLabelWhite();
    private JLabelWhite lblNombCliente = new JLabelWhite();
    private JLabelWhite lblNombCliente_T = new JLabelWhite();
    private JLabelWhite lblEdad = new JLabelWhite();
    private JLabelWhite lblEdad_T = new JLabelWhite();
    private JLabelWhite lblSexo = new JLabelWhite();
    private JLabelWhite lblSexo_T = new JLabelWhite();

    private JButtonLabel btnMaxSistolica = new JButtonLabel();
    private JButtonLabel btnMinDiastolica = new JButtonLabel();

    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgModMedidaPresion() {
        this(null, "", false);
    }


    public DlgModMedidaPresion(Frame parent, String title, boolean modal) {
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
        this.setSize(new Dimension(460, 180));
        this.setTitle("Modificar datos de medida de Presion");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });

        lblDNI.setText("DNI :");
        lblDNI.setBounds(new Rectangle(5, 5, 31, 20));
        lblDNI_T.setText("");
        lblDNI_T.setBounds(new Rectangle(35, 5, 106, 20));

        lblNombCliente.setText("Cliente :");
        lblNombCliente.setBounds(new Rectangle(5, 25, 51, 20));
        lblNombCliente_T.setText("");
        lblNombCliente_T.setBounds(new Rectangle(55, 25, 274, 20));

        lblEdad.setBounds(new Rectangle(6, 50, 50, 20));
        lblEdad.setText("Fec. Nac.:");
        lblEdad_T.setBounds(new Rectangle(60, 50, 64, 20));
        lblEdad_T.setText("");

        lblSexo.setBounds(new Rectangle(165, 50, 40, 20));
        lblSexo.setText("Sexo :");
        lblSexo_T.setBounds(new Rectangle(200, 50, 87, 20));
        lblSexo_T.setText("");


        jContentPane.setSize(new Dimension(456, 180));

        pnlHeader1.setBounds(new Rectangle(10, 10, 431, 105));

        btnMaxSistolica.setBounds(new Rectangle(5, 75, 80, 20));
        btnMaxSistolica.setText("Max. Sistolica :");
        btnMaxSistolica.setMnemonic('S');
        btnMaxSistolica.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnMaxSistolica_actionPerformed(e);
            }
        });

        btnMinDiastolica.setBounds(new Rectangle(182, 77, 89, 15));
        btnMinDiastolica.setText("Min. Diastolica :");
        btnMinDiastolica.setMnemonic('D');
        btnMinDiastolica.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnMinDiastolica_actionPerformed(e);
            }
        });

        txtMaxSistolica.setBounds(new Rectangle(89, 75, 52, 20));
        txtMaxSistolica.setText("");
        txtMaxSistolica.setLengthText(3);
        txtMaxSistolica.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtMaxSistolica_keyTyped(e);
            }

            public void keyPressed(KeyEvent e) {
                txtMaxSistolica_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtMaxSistolica_keyReleased(e);
            }
        });

        txtMinDiastolica.setBounds(new Rectangle(276, 75, 55, 19));
        txtMinDiastolica.setText("");
        txtMinDiastolica.setLengthText(3);
        txtMinDiastolica.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                txtMinDiastolica_keyTyped(e);
            }

            public void keyPressed(KeyEvent e) {
                txtMinDiastolica_keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                txtMinDiastolica_keyReleased(e);
            }
        });


        lblF11.setBounds(new Rectangle(202, 120, 120, 20));
        lblF11.setText("[ F11 ] Aceptar");

        lblEsc.setBounds(new Rectangle(329, 120, 110, 20));
        lblEsc.setText("[ ESC ] Cerrar");


        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);

        pnlHeader1.add(txtMaxSistolica, null);
        pnlHeader1.add(btnMaxSistolica, null);
        pnlHeader1.add(lblDNI, null);

        pnlHeader1.add(lblDNI_T, null);
        pnlHeader1.add(lblNombCliente, null);
        pnlHeader1.add(lblNombCliente_T, null);
        pnlHeader1.add(btnMinDiastolica, null);
        pnlHeader1.add(txtMinDiastolica, null);
        pnlHeader1.add(lblEdad, null);
        pnlHeader1.add(lblEdad_T, null);
        pnlHeader1.add(lblSexo, null);
        pnlHeader1.add(lblSexo_T, null);
        jContentPane.add(pnlHeader1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        FarmaVariables.vAceptar = false;
        initTable();
        cargaRangoValoresValidos();
        //cargan la variable indicador de linea con matriz
        VariablesOtros.vIndLineaMatriz =
                FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_N).trim();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    public void initTable() {

        cargarDatosCliente();
    }

    private void cargarDatosCliente() {

        log.debug("seteando valores:" + VariablesOtros.vDniCliente);
        lblDNI_T.setText(VariablesOtros.vDniCliente);
        lblNombCliente_T.setText(VariablesOtros.vNomCliente + " " + VariablesOtros.vApePatCliente + " " +
                                 VariablesOtros.vApeMatCliente);
        lblEdad_T.setText(VariablesOtros.vFecNacimiento);

        if (VariablesOtros.vSexo.equalsIgnoreCase("M")) {
            lblSexo_T.setText("MASCULINO");
        } else if (VariablesOtros.vSexo.equalsIgnoreCase("F")) {
            lblSexo_T.setText("FEMENINO");
        } else {
            lblSexo_T.setText("");
        }


        txtMaxSistolica.setText(VariablesOtros.vMaxSistolica);
        txtMinDiastolica.setText(VariablesOtros.vMinDiastolica);
    }

    private void cargaRangoValoresValidos() {
        try {
            VariablesOtros.vValMPValidos = DBOtros.getRangoValoresValidosMP();
        } catch (SQLException e) {
            //FarmaUtility.showMessage(this, "error al registrar la medida de Presion :"+e, txtDNI);
            FarmaUtility.showMessage(this, "error al buscar historico en Local :" + e, txtMaxSistolica);
        }
        log.info("rango de valores validos : " + VariablesOtros.vValMPValidos);
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        //lblDNI_T.setText(VariablesCtrlMotorizado.vAlias_Motorizado);
        //lblNombMotorizado_T.setText(VariablesCtrlMotorizado.vNombre_Motorizado);
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtMaxSistolica);
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void btnMaxSistolica_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtMaxSistolica);
    }

    private void btnMinDiastolica_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtMinDiastolica);
    }


    private void txtMaxSistolica_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtMaxSistolica, e);
    }

    private void txtMaxSistolica_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtMinDiastolica);
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        chkKeyPressed(e);
    }

    private void txtMaxSistolica_keyReleased(KeyEvent e) {
        //FarmaGridUtils.buscarDescripcion(e, tblListaLocales,txtMaxSistolica, COL_DESC);
    }


    private void txtMinDiastolica_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtMinDiastolica, e);
    }

    private void txtMinDiastolica_keyPressed(KeyEvent e) {
        //FarmaGridUtils.aceptarTeclaPresionada(e,tblListaLocales, txtMaxSistolica, 1);
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtMaxSistolica);
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cerrarVentana(false);
        chkKeyPressed(e);
    }

    private void txtMinDiastolica_keyReleased(KeyEvent e) {
        //FarmaGridUtils.buscarDescripcion(e, tblListaLocales,txtMaxSistolica, COL_DESC);
        //chkKeyPressed(e);
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    private void chkKeyPressed(KeyEvent e) {
        //FarmaGridUtils.aceptarTeclaPresionada(e,tblListaLocales,null,0);
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e)) {
            if (validarDatos()) {

                VariablesOtros.vMaxSistolica = txtMaxSistolica.getText();
                VariablesOtros.vMinDiastolica = txtMinDiastolica.getText();

                if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,
                                                                                "Desea Actualizar Datos de la Medida Presion ?")) {
                    actualizarMedidaPresion();
                    cerrarVentana(true);
                }
            }
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

    private boolean validarDatos() {
        txtMaxSistolica.setText(txtMaxSistolica.getText().trim());
        txtMinDiastolica.setText(txtMinDiastolica.getText().trim());

        boolean flag = true;
        int max = 0;
        int min = 0;

        try {
            max = Integer.parseInt(txtMaxSistolica.getText());
        } catch (NumberFormatException e) {
            //  flag = false;
            FarmaUtility.showMessage(this, "Valor no valido !", txtMaxSistolica);

            return false;
        }

        try {
            min = Integer.parseInt(txtMinDiastolica.getText());
        } catch (NumberFormatException e) {
            //flag = false;
            FarmaUtility.showMessage(this, "Valor no valido !", txtMinDiastolica);

            return false;
        }


        try {
            if (VariablesOtros.vValMPValidos != null && VariablesOtros.vValMPValidos.length() > 0) {
                String valMPValidos[] = VariablesOtros.vValMPValidos.split(",");
                int minimo = 10; //valores por defecto
                int maximo = 300; //valores por defecto
                if (valMPValidos.length >= 2) {
                    minimo = Integer.parseInt(valMPValidos[0]); //minimo
                    maximo = Integer.parseInt(valMPValidos[1]); //maximo
                }
                if (min < minimo || min > maximo) {
                    //flag=false;
                    FarmaUtility.showMessage(this,
                                             "Valor no valido\nesta fuera del rango permitido [" + VariablesOtros.vValMPValidos.trim() +
                                             "] !", txtMinDiastolica);
                    return false;
                }
                if (max < minimo || max > maximo) {
                    //flag=false;
                    FarmaUtility.showMessage(this,
                                             "Valor no valido\nesta fuera del rango permitido [" + VariablesOtros.vValMPValidos.trim() +
                                             "] !", txtMaxSistolica);
                    return false;
                }
            } else {
                //flag=false;
                FarmaUtility.showMessage(this, "rango de valores permitidos no cargado !", txtMaxSistolica);
                return false;
            }

            if (max < 0) {
                //flag = false;
                FarmaUtility.showMessage(this, "Valor no valido de Max. sistolica!", txtMaxSistolica);
                return false;
            }
            if (min < 0) {
                //flag = false;
                FarmaUtility.showMessage(this, "Valor no valido de Min. diastolica!", txtMinDiastolica);
                return false;
            }

            if (min > max) {
                //flag = false;
                FarmaUtility.showMessage(this, "Min. Diastolica no puede ser mayor a Max Sistolica!", txtMaxSistolica);
                return false;
            }

        } catch (NumberFormatException e) {
            flag = false;
            FarmaUtility.showMessage(this, "Valor no valido !", txtMinDiastolica);

        } catch (Exception e) {
            flag = false;
            FarmaUtility.showMessage(this, "Valor no valido !", txtMaxSistolica);
        }
        return flag;
    }

    private void actualizarMedidaPresion() {
        try {
            DBOtros.actualizarMedidaPresionEnLocal();

            if (VariablesOtros.vIndLineaMatriz.equals(FarmaConstants.INDICADOR_S)) {
                DBOtros.actualizarMedidaPresionEnMatriz(FarmaConstants.INDICADOR_N);
                //haciendo commit en matriz
                FarmaUtility.aceptarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_N);
            }
            //haciendo commit en local
            FarmaUtility.aceptarTransaccion();
        } catch (SQLException e) {
            FarmaUtility.liberarTransaccion();
            if (VariablesOtros.vIndLineaMatriz.equals(FarmaConstants.INDICADOR_S)) {
                FarmaUtility.liberarTransaccionRemota(FarmaConstants.CONECTION_MATRIZ, FarmaConstants.INDICADOR_N);
                FarmaConnectionRemoto.closeConnection();
            }
            FarmaUtility.showMessage(this, "error al actualizar la medida de Presion :" + e, txtMaxSistolica);
        }
    }


}
