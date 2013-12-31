package mifarma.ptoventa.ventas;


import com.gs.mifarma.componentes.JLabelFunction;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mifarma.common.FarmaLengthText;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.DlgListaMaestros;
import mifarma.ptoventa.cliente.DlgBuscarDni;
import mifarma.ptoventa.cliente.reference.ConstantsCliente;
import mifarma.ptoventa.recetario.reference.DBRecetario;
import mifarma.ptoventa.recetario.reference.VariablesRecetario;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;
import org.apache.ibatis.logging.LogFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2013 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgDatosPacienteMedico.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      15.04.2013   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgRegistroPsicotropico extends JDialog {
    
    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */
    
    private static final Logger log = LoggerFactory.getLogger(DlgRegistroPsicotropico.class);
    
    private Frame myParentFrame;
    
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jPanel1 = new JPanel();
    private JPanel jPanel2 = new JPanel();
    private JButton btnDni = new JButton();
    private JButton btnPaciente = new JButton();
    private JButton btnTelefono = new JButton();
    private JButton btnCmp = new JButton();
    private JButton btnMedico = new JButton();
    private JTextField txtDni = new JTextField();
    private JTextField txtPaciente = new JTextField();
    private JTextField txtTelefono = new JTextField();
    private JTextField txtCmp = new JTextField();
    private JTextField txtMedico = new JTextField();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JTabbedPane jTabbedPane1 = new JTabbedPane();

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */
    
    public DlgRegistroPsicotropico() {
        this(null, "", false);
    }

    public DlgRegistroPsicotropico(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame=parent;
        try {
            jbInit();
            initVariables();
        } catch (Exception e) {
            log.error("",e);
        }
    }

    /* ************************************************************************ */
    /*                                  METODO jbInit                           */
    /* ************************************************************************ */
    
    private void jbInit() throws Exception {
        //this.setSize(new Dimension(498, 485));
        this.setSize(new Dimension(498, 270));
        this.getContentPane().setLayout(borderLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setForeground(Color.white);
        this.setTitle("Registro de Venta de Medicamentos Psicotrópicos");
        this.addWindowListener(new WindowAdapter() {

                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }

                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }
                });
        jPanel1.setLayout(null);
        jPanel1.setForeground(Color.white);
        jPanel1.setBackground(Color.white);
        jPanel2.setBounds(new Rectangle(15, 10, 465, 185));
        jPanel2.setBackground(new Color(43, 141, 39));
        jPanel2.setLayout(null);

        jPanel2.setForeground(new Color(0, 132, 0));
        jPanel2.setFocusable(false);
        btnDni.setText("* DNI :");
        btnDni.setBackground(Color.white);
        btnDni.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnDni.setBorderPainted(false);
        btnDni.setContentAreaFilled(false);
        btnDni.setDefaultCapable(false);
        btnDni.setFocusPainted(false);
        btnDni.setFont(new Font("SansSerif", 1, 11));
        btnDni.setForeground(Color.white);
        btnDni.setHorizontalAlignment(SwingConstants.LEFT);
        btnDni.setMnemonic('D');
        btnDni.setRequestFocusEnabled(false);
        btnDni.setBounds(new Rectangle(10, 13, 65, 25));

        btnDni.setFocusable(false);
        btnDni.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    btnDni_actionPerformed(e);
                }
                });
        btnPaciente.setText("* Paciente :");
        btnPaciente.setBackground(Color.white);
        btnPaciente.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnPaciente.setBorderPainted(false);
        btnPaciente.setContentAreaFilled(false);
        btnPaciente.setDefaultCapable(false);
        btnPaciente.setFocusPainted(false);
        btnPaciente.setFont(new Font("SansSerif", 1, 11));
        btnPaciente.setForeground(Color.white);
        btnPaciente.setHorizontalAlignment(SwingConstants.LEFT);
        btnPaciente.setRequestFocusEnabled(false);
        btnPaciente.setBounds(new Rectangle(10, 40, 65, 25));

        btnPaciente.setFocusable(false);
        btnPaciente.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnPaciente_actionPerformed(e);
                    }
                });
        btnTelefono.setText("* Fec. Receta:");
        btnTelefono.setBackground(Color.white);
        btnTelefono.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnTelefono.setBorderPainted(false);
        btnTelefono.setContentAreaFilled(false);
        btnTelefono.setDefaultCapable(false);
        btnTelefono.setFocusPainted(false);
        btnTelefono.setFont(new Font("SansSerif", 1, 11));
        btnTelefono.setForeground(Color.white);
        btnTelefono.setHorizontalAlignment(SwingConstants.LEFT);
        btnTelefono.setMnemonic('f');
        btnTelefono.setRequestFocusEnabled(false);
        btnTelefono.setBounds(new Rectangle(10, 75, 75, 25));

        btnTelefono.setFocusable(false);
        btnTelefono.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnTelefono_actionPerformed(e);
                    }
                });
        btnCmp.setText("* CMP :");
        btnCmp.setBackground(Color.white);
        btnCmp.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnCmp.setBorderPainted(false);
        btnCmp.setContentAreaFilled(false);
        btnCmp.setDefaultCapable(false);
        btnCmp.setFocusPainted(false);
        btnCmp.setFont(new Font("SansSerif", 1, 11));
        btnCmp.setForeground(Color.white);
        btnCmp.setHorizontalAlignment(SwingConstants.LEFT);
        btnCmp.setMnemonic('C');
        btnCmp.setRequestFocusEnabled(false);
        btnCmp.setBounds(new Rectangle(10, 105, 65, 25));

        btnCmp.setFocusable(false);
        btnCmp.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnCmp_actionPerformed(e);
                    }
                });
        btnMedico.setText("* Médico :");
        btnMedico.setBackground(Color.white);
        btnMedico.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnMedico.setBorderPainted(false);
        btnMedico.setContentAreaFilled(false);
        btnMedico.setDefaultCapable(false);
        btnMedico.setFocusPainted(false);
        btnMedico.setFont(new Font("SansSerif", 1, 11));
        btnMedico.setForeground(Color.white);
        btnMedico.setHorizontalAlignment(SwingConstants.LEFT);
        btnMedico.setRequestFocusEnabled(false);
        btnMedico.setBounds(new Rectangle(10, 130, 65, 25));

        btnMedico.setFocusable(false);
        btnMedico.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnMedico_actionPerformed(e);
                    }
                });
        txtDni.setBounds(new Rectangle(115, 15, 100, 20));
        txtDni.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtDni_keyPressed(e);
                    }

                public void keyReleased(KeyEvent e) {
                    txtDni_keyReleased(e);
                }
            });
        txtPaciente.setBounds(new Rectangle(115, 45, 315, 20));
        txtPaciente.setEditable(false);
        txtPaciente.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtPaciente_keyPressed(e);
                    }
                });
        txtTelefono.setBounds(new Rectangle(115, 75, 130, 20));
        txtTelefono.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtTelefono_keyPressed(e);
                    }

                    public void keyReleased(KeyEvent e) {
                        txtTelefono_keyReleased(e);
                    }
                });
        txtCmp.setBounds(new Rectangle(115, 105, 80, 20));
        txtCmp.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                    txtCmp_keyPressed(e);
                }
                });
        txtMedico.setBounds(new Rectangle(115, 135, 315, 20));
        txtMedico.setFocusable(false);
        txtMedico.setEditable(false);
        txtMedico.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtMedico_keyPressed(e);
                    }
                });


        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(390, 208, 90, 20));
        lblEsc.setFocusable(false);
        lblF11.setText("[ F11 ] Aceptar");
        //lblF11.setBounds(new Rectangle(285, 405, 90, 20));
        lblF11.setBounds(new Rectangle(285, 208, 90, 20));

        lblF11.setFocusable(false);
        jTabbedPane1.setBounds(new Rectangle(160, 210, 5, 5));
        jPanel2.add(txtMedico, null);
        jPanel2.add(txtCmp, null);
        jPanel2.add(txtTelefono, null);
        jPanel2.add(txtPaciente, null);
        jPanel2.add(txtDni, null);
        jPanel2.add(btnMedico, null);
        jPanel2.add(btnCmp, null);
        jPanel2.add(btnTelefono, null);
        jPanel2.add(btnPaciente, null);
        jPanel2.add(btnDni, null);
        jPanel1.add(jTabbedPane1, null);
        jPanel1.add(jPanel2, null);
        jPanel1.add(lblEsc, null);
        jPanel1.add(lblF11, null);
        this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    }

    private void initVariables()
    {   VariablesRecetario.vMapDatosPacienteMedico = new HashMap();
    }

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void btnPaciente_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtPaciente);
    }

    private void btnTelefono_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtTelefono);
    }

    private void btnCmp_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtCmp);
    }

    private void btnMedico_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtMedico);
    }

    private void txtPaciente_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(txtTelefono);
        }else {
            chkKeyPressed(e);
        }
    }

    private void txtTelefono_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(txtCmp);
        }else if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
            cerrarVentana(false);
        }
        chkKeyPressed(e);
    }

    private void txtTelefono_keyReleased(KeyEvent e){
        FarmaUtility.dateComplete(txtTelefono, e);
    }

    private void txtCmp_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            muestraBuscaMedico();
            FarmaUtility.moveFocus(txtDni);
        }else {
            chkKeyPressed(e);
        }
    }

    private void txtMedico_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            FarmaUtility.moveFocus(txtDni);
        }else {
            chkKeyPressed(e);
        }
    }

    private void txtDni_keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {   e.consume();
            FarmaUtility.moveFocus(txtTelefono);
        }
    }
    

    private void txtDni_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {   e.consume();
            mostrarBusquedaPaciente();
        }
        else
        {   chkKeyPressed(e);
        }
    }

    private void btnDni_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtDni);
    }    

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtDni);
    }
    
    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, 
                                 "Debe presionar la tecla ESC para cerrar la ventana.", 
                                 null);
    }
    
    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */
    
    private void chkKeyPressed(KeyEvent e)
    {   if(mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e))
        {   
            if( !txtDni.getText().trim().equals("")         &&
                !txtPaciente.getText().trim().equals("")    &&
                !txtTelefono.getText().trim().equals("")    &&
                !txtCmp.getText().trim().equals("")         &&
                !txtMedico.getText().trim().equals("")      ) {   
                boolean flag=true;
                Calendar fecha_actual = Calendar.getInstance();
                Calendar fecha_ingresada = (Calendar)fecha_actual.clone();
                try
                {   fecha_ingresada.setTime(FarmaUtility.getStringToDate(txtTelefono.getText().trim(),
                                                                         "dd/MM/yyyy"));
                }
                catch(Exception ex)
                {   //si no se puede obtener un Date, la fecha es incorrecta
                    FarmaUtility.showMessage(this, "ERROR: La fecha ingresada no es válida", null);
                    flag=false;
                }
                
                if(flag)
                {   Map tmpMapDatos = new HashMap();
                    tmpMapDatos.put("DNI", txtDni.getText().trim());
                    tmpMapDatos.put("PACIENTE", txtPaciente.getText().trim());
                    tmpMapDatos.put("FECHA", txtTelefono.getText().trim());
                    tmpMapDatos.put("CMP", txtCmp.getText().trim());
                    tmpMapDatos.put("MEDICO", txtMedico.getText().trim());
                    VariablesRecetario.vMapDatosPacienteMedico = tmpMapDatos;

                    //GRABAR DATOS
                    if(insertaDatosVentaRestringidos()){                        
                        cerrarVentana(true);
                    }else{
                        FarmaUtility.showMessage(this, 
                                                "No se pudo ingresar correctamente los datos",
                                                null);
                    }
                }
            }
            else
                FarmaUtility.showMessage(this, 
                                         "ERROR: Alguno de los campos se encuentra vacío", 
                                         null);
        }else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
    }

    private void cerrarVentana(boolean pAceptar){
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    /**
     * Abre el formulario de Búsqueda de Clientes
     * @author wvillagomez
     * @since 02.09.2013
     */
    private void mostrarBusquedaPaciente()
    {   DlgBuscarDni dlgBuscarDni = new DlgBuscarDni(myParentFrame, "", true);
        dlgBuscarDni.getTxt_buscar().setText(txtDni.getText().trim());
        dlgBuscarDni.setVisible(true);
        if (FarmaVariables.vAceptar)
        {   ArrayList array = (ArrayList)dlgBuscarDni.retornarResultado().get(0);
            
            String strDni = array.get(0).toString();
            String strPaciente = array.get(1).toString() + " " +
                                array.get(2).toString()  + " " +
                                array.get(3).toString();
            
            txtDni.setText(strDni);
            txtPaciente.setText(strPaciente.toUpperCase().trim());
        }
    }
    
    /**
     * Abre el formulario de Búsqueda de médicos
     * @author wvillagomez
     * @since 02.09.2013
     */
    private void muestraBuscaMedico() {
        DlgBuscaMedico dlgBuscaMedico = new DlgBuscaMedico(myParentFrame, "", true);
        dlgBuscaMedico.getTxtBusqueda().setText(txtCmp.getText().trim());
        dlgBuscaMedico.setVisible(true);
        String strCMP = VariablesVentas.vMatriListaMed;
        String strNombMed = VariablesVentas.vNombreListaMed;

        txtCmp.setText(strCMP);
        txtMedico.setText(strNombMed.toUpperCase());
    }
    
    /**
     * Graba en la BD un Registro Psicotropico
     * @author wvillagomez
     * @since 02.09.2013
     * @return boolean
     */
    private boolean insertaDatosVentaRestringidos(){
        boolean vRetorno = false;
      try{
          DBVentas.insertaDatosVentaRestringidos();
          vRetorno = true;
      }catch(SQLException sql){
        log.error("",sql);
      }
      return vRetorno;
    }

}


