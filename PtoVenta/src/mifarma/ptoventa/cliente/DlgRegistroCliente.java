package mifarma.ptoventa.cliente;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Frame;

import java.awt.Rectangle;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import javax.swing.text.MaskFormatter;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.fidelizacion.reference.DBFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;
import mifarma.ptoventa.recetario.reference.UtilityRecetario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgRegistroCliente extends JDialog 
{
    private Frame myParentFrame;
    private static final Logger log = LoggerFactory.getLogger(DlgRegistroCliente.class); 

    private JPanelWhite jContentPane = new JPanelWhite();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelHeader jPanel1 = new JPanelHeader();
    private JPanelTitle pnlTitle1 = new JPanelTitle();
    
    private JButtonLabel btnLista = new JButtonLabel();
    
    private JTextField txtDniCliente = new JTextField();
    private JTextField txtApePaterno = new JTextField();
    private JTextField txtFechaNac = new JTextField();
    private JTextField txtApeMaterno = new JTextField();
    private JTextField txtNombre = new JTextField();
    private JTextField txtEmail = new JTextField();
    private JComboBox<String> cmbGenero = new JComboBox<String>();

    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelWhite lblDniCliente = new JLabelWhite();
    private JLabelWhite lblApePaterno = new JLabelWhite();
    private JLabelWhite lblFechaNac = new JLabelWhite();
    private JLabelWhite lblApeMaterno = new JLabelWhite();
    private JLabelWhite lblNombres = new JLabelWhite();
    private JLabelWhite lblEmail = new JLabelWhite();
    private JLabelWhite lblGenero = new JLabelWhite();

    private MaskFormatter mscFechaNac;

    public DlgRegistroCliente() {
        this(null, "", false);
    }

    public DlgRegistroCliente(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
        } catch (Exception e) {
            log.error("",e);
        }
    }

    private void jbInit() throws Exception
    {   this.setSize(new Dimension(502, 404));
        this.setTitle("Datos de Cliente");
        pnlTitle1.setBounds(new Rectangle(10, 10, 470, 20));

        pnlTitle1.setFocusable(false);
        btnLista.setText("Lista Datos");
        btnLista.setBounds(new Rectangle(5, 0, 105, 20));
        btnLista.setMnemonic('L');

        btnLista.setFocusable(false);
        pnlTitle1.add(btnLista, null);

        //mscFechaNac = new MaskFormatter("##/##/####");
        //mscFechaNac.setPlaceholderCharacter('_');
        //txtFechaNac = new JFormattedTextField(mscFechaNac);
        
        this.getContentPane().setLayout(borderLayout1);
        this.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    this_windowClosing(e);
                }

                public void windowOpened(WindowEvent e) {
                    this_windowOpened(e);
                }
            });
        jContentPane.setFocusable(false);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);

        lblEsc.setBounds(new Rectangle(380, 340, 95, 20));
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setFocusable(false);
        lblF11.setBounds(new Rectangle(265, 340, 105, 20));
        lblF11.setText("[ F11 ] Aceptar");

        lblF11.setFocusable(false);
        txtDniCliente.setBounds(new Rectangle(145, 15, 190, 20));
        txtDniCliente.setEditable(false);
        txtDniCliente.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtDniCliente_keyPressed(e);
                }
            });
        txtApePaterno.setBounds(new Rectangle(145, 50, 290, 20));
        txtApePaterno.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtApePaterno_keyPressed(e);
                }

                public void keyTyped(KeyEvent e) {
                    txtApePaterno_keyTyped(e);
                }
            });
        txtFechaNac.setBounds(new Rectangle(145, 190, 190, 20));
        txtFechaNac.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtFechaNac_keyPressed(e);
                }
            });
        lblDniCliente.setText("DNI Cliente: *");
        lblDniCliente.setBounds(new Rectangle(25, 15, 90, 20));
        lblApePaterno.setText("Apellido Paterno: *");
        lblApePaterno.setBounds(new Rectangle(25, 50, 105, 20));
        lblFechaNac.setText("Fecha Nacimiento: *");
        lblFechaNac.setBounds(new Rectangle(25, 190, 120, 20));
        lblFechaNac.setFocusable(false);
        jPanel1.setBounds(new Rectangle(15, 40, 465, 280));
        jPanel1.setFocusable(false);
        txtApeMaterno.setBounds(new Rectangle(145, 85, 290, 20));
        txtApeMaterno.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtApeMaterno_keyPressed(e);
                }

                public void keyTyped(KeyEvent e) {
                    txtApeMaterno_keyTyped(e);
                }
            });
        lblApeMaterno.setText("Apellido Materno: *");
        lblApeMaterno.setBounds(new Rectangle(25, 85, 105, 20));
        lblApeMaterno.setFocusable(false);
        txtNombre.setBounds(new Rectangle(145, 120, 290, 20));
        txtNombre.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtNombre_keyPressed(e);
                }

                public void keyTyped(KeyEvent e) {
                    txtNombre_keyTyped(e);
                }
            });
        lblNombres.setText("Nombres: *");
        lblNombres.setBounds(new Rectangle(25, 120, 95, 20));
        lblNombres.setFocusable(false);
        txtEmail.setBounds(new Rectangle(145, 155, 290, 20));
        txtEmail.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtEmail_keyPressed(e);
                }

                public void keyTyped(KeyEvent e) {
                    txtEmail_keyTyped(e);
                }
            });
        lblEmail.setText("E-mail:");
        lblEmail.setBounds(new Rectangle(25, 155, 95, 20));
        lblEmail.setFocusable(false);
        cmbGenero.setBounds(new Rectangle(145, 225, 125, 20));
        cmbGenero.addItem("Femenino");
        cmbGenero.addItem("Masculino");
        cmbGenero.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    cmbGenero_keyPressed(e);
                }
            });
        lblGenero.setText("Genero: *");
        lblGenero.setBounds(new Rectangle(25, 225, 95, 20));
        lblGenero.setFocusable(false);
        jPanel1.add(lblGenero, null);
        jPanel1.add(cmbGenero, null);
        jPanel1.add(lblEmail, null);
        jPanel1.add(txtEmail, null);
        jPanel1.add(lblNombres, null);
        jPanel1.add(txtNombre, null);
        jPanel1.add(lblApeMaterno, null);
        jPanel1.add(txtApeMaterno, null);
        jPanel1.add(lblDniCliente, null);
        jPanel1.add(lblApePaterno, null);
        jPanel1.add(lblFechaNac, null);
        jPanel1.add(txtFechaNac, null);
        jPanel1.add(txtApePaterno, null);
        jPanel1.add(txtDniCliente, null);
        jContentPane.add(jPanel1, null);
        jContentPane.add(pnlTitle1, null);

        jContentPane.add(lblF11, null);
        jContentPane.add(lblEsc, null);
        FarmaUtility.centrarVentana(this);
    }
    
    private void chkKeyPressed(KeyEvent e)
    {   if(mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e))
        {   String resValidacion = validarFormulario();
            if(resValidacion.length() == 0)
            {   if(insertarCliente())
                    cerrarVentana(true);
                else
                    FarmaUtility.showMessage(this, 
                                            "No se pudo ingresar correctamente la información del paciente.",
                                            null);
            }
            else
            {   FarmaUtility.showMessage(this, 
                                        resValidacion,
                                        null);
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {   cerrarVentana(false);
        }
        else if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {   ((JComponent)e.getSource()).transferFocus();
        }
    }
    
    public void cerrarVentana(boolean flag)
    {   FarmaVariables.vAceptar = flag;
        this.setVisible(false);
        this.dispose();
    }

    private void txtDniCliente_keyPressed(KeyEvent e)
    {   chkKeyPressed(e);
    }

    private void txtFechaNac_keyPressed(KeyEvent e)
    {   chkKeyPressed(e);
    }
    
    public void setDNI(String dni)
    {   txtDniCliente.setText(dni);
    }

    private void this_windowClosing(WindowEvent e)
    {   FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }
    
    private boolean insertarCliente()
    {   Map<String,String> parametros = new HashMap<String,String>();
        try
        {   parametros.put("dni", txtDniCliente.getText());
            parametros.put("nombre", txtNombre.getText());
            parametros.put("apePaterno", txtApePaterno.getText());
            parametros.put("apeMaterno", txtApeMaterno.getText());
            parametros.put("email", txtEmail.getText());
            parametros.put("genero", cmbGenero.getSelectedItem().toString().substring(0, 1));
            parametros.put("fecNac", txtFechaNac.getText());
            parametros.put("tipoDoc", "");

            DBFidelizacion.insertarCliente(parametros);
        }
        catch(Exception e)
        {   log.error("",e);
            return false;
        }
        
        return true;
        
    }

    private void this_windowOpened(WindowEvent e)
    {   txtApePaterno.grabFocus();
    }

    private void txtApePaterno_keyPressed(KeyEvent e)
    {   chkKeyPressed(e);
    }

    private void txtApeMaterno_keyPressed(KeyEvent e)
    {   chkKeyPressed(e);
    }

    private void txtNombre_keyPressed(KeyEvent e)
    {   chkKeyPressed(e);
    }

    private void txtEmail_keyPressed(KeyEvent e)
    {   chkKeyPressed(e);
    }

    private void cmbGenero_keyPressed(KeyEvent e)
    {   chkKeyPressed(e);
    }
    
    private String validarFormulario()
    {   //validar campos obligatorios vacios
        String respVacios = "";
        if(txtApePaterno.getText().length()<=0)
            respVacios = respVacios + "+ Apellido Paterno\n";
        if(txtApeMaterno.getText().length()<=0)
            respVacios = respVacios + "+ Apellido Materno\n";
        if(txtNombre.getText().length()<=0)
            respVacios = respVacios + "+ Nombre\n";
        if(txtFechaNac.getText().length()<=0)
            respVacios = respVacios + "+ Fecha Nacimiento\n";
        
        if(respVacios.length()>0)
            respVacios = "Los siguientes campos son obligatorios y se encuentran vacios:\n" + respVacios + "\n";
        
        //validar formatos de campos
        String respFormatos = "";
        if(txtEmail.getText().length()>0 && !UtilityRecetario.validarEmail(txtEmail.getText()))
            respFormatos = respFormatos + "+ E-mail\n";
        if(!UtilityRecetario.validarDDMMYYYY(txtFechaNac.getText()))
            respFormatos = respFormatos + "+ Fecha Nacimiento\n";
        
        if(respFormatos.length()>0)
            respFormatos = "Los siguientes campos poseen formatos incorrectos:\n" + respFormatos + "\n";
        
        return respVacios + respFormatos;
    }

    private void txtApePaterno_keyTyped(KeyEvent e)
    {   //validaciones de campo
        int limite = 50;
        if(txtApePaterno.getText().length() >= limite)
        {   txtApePaterno.setText(txtApePaterno.getText().substring(0, limite));
            e.consume();
        }
    }

    private void txtApeMaterno_keyTyped(KeyEvent e)
    {   //validaciones de campo
        int limite = 50;
        if(txtApeMaterno.getText().length() >= limite)
        {   txtApeMaterno.setText(txtApeMaterno.getText().substring(0, limite));
            e.consume();
        }
    }

    private void txtNombre_keyTyped(KeyEvent e)
    {   //validaciones de campo
        int limite = 50;
        if(txtNombre.getText().length() >= limite)
        {   txtNombre.setText(txtNombre.getText().substring(0, limite));
            e.consume();
        }
    }

    private void txtEmail_keyTyped(KeyEvent e)
    {   //validaciones de campo
        int limite = 100;
        if(txtEmail.getText().length() >= limite)
        {   txtEmail.setText(txtEmail.getText().substring(0, limite));
            e.consume();
        }
        //validación de forma
    }
}
