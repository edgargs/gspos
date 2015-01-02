package mifarma.ptoventa.inventariodiario;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
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

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import mifarma.common.*;

import mifarma.ptoventa.inventariociclico.reference.ConstantsInvCiclico;
import mifarma.ptoventa.inventariociclico.reference.DBInvCiclico;
import mifarma.ptoventa.inventariociclico.reference.VariablesInvCiclico;
import mifarma.ptoventa.inventariodiario.reference.*;
import mifarma.ptoventa.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DlgIngresoCantidadInvDiarioConteo extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgIngresoCantidadInvDiarioConteo.class); 

    FarmaTableModel tableModelKardex;

    Frame myParentFrame;

    private JPanelWhite jContentPane = new JPanelWhite();

    private BorderLayout borderLayout2 = new BorderLayout();

    private JPanelWhite jPanelWhite1 = new JPanelWhite();

    private JLabel jLabel1 = new JLabel();


    private JLabel lblCodigo = new JLabel();

    private JLabel lblDescripcion = new JLabel();

    private JLabel jLabel5 = new JLabel();

    private JLabel lblUnidadPresentacion = new JLabel();

    private JLabel jLabel7 = new JLabel();

    private JLabel lblLaboratorio = new JLabel();

    private JButtonLabel jButtonLabel1 = new JButtonLabel();

    private JTextFieldSanSerif txtEntero = new JTextFieldSanSerif();

    private JTextFieldSanSerif txtHora = new JTextFieldSanSerif();

    private JLabelFunction jLabelFunction1 = new JLabelFunction();

    private JLabelFunction jLabelFunction2 = new JLabelFunction();
    private JLabel lblTValorFraccion = new JLabel();
    private JLabel lblValorFraccion = new JLabel();
    private JTextFieldSanSerif txtFraccion = new JTextFieldSanSerif();
    private JButtonLabel lblFraccion = new JButtonLabel();
    private JButtonLabel lblHora = new JButtonLabel();
    private JLabel lblUnidadVenta = new JLabel();
    private JLabel jLabel6 = new JLabel();
    private JScrollPane srcKArdex = new JScrollPane();
    private JTable tblKardex = new JTable();
    private JPanelTitle pnllTitle1 = new JPanelTitle();
    private JButtonLabel btnRelacionMovimiento = new JButtonLabel();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgIngresoCantidadInvDiarioConteo() {
        this(null, "", false);
    }

    public DlgIngresoCantidadInvDiarioConteo(Frame parent, String title, 
                                             boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("",e);
        }
    }

    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(590, 496));
        this.getContentPane().setLayout(borderLayout2);
        this.setTitle("Ingreso de Cantidad");
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }
                });
        jPanelWhite1.setBounds(new Rectangle(10, 15, 555, 175));
        jPanelWhite1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        jPanelWhite1.setLayout(null);
        jLabel1.setText("Codigo :");
        jLabel1.setBounds(new Rectangle(15, 15, 50, 15));
        jLabel1.setFont(new Font("SansSerif", 1, 11));
        jLabel1.setForeground(new Color(43, 141, 39));
        lblCodigo.setText("914005");
        lblCodigo.setBounds(new Rectangle(95, 15, 60, 15));
        lblCodigo.setFont(new Font("SansSerif", 1, 11));
        lblCodigo.setForeground(new Color(255, 130, 14));
        lblDescripcion.setText("ACTIVEL SINGLES");
        lblDescripcion.setBounds(new Rectangle(155, 15, 250, 15));
        lblDescripcion.setFont(new Font("SansSerif", 1, 11));
        lblDescripcion.setForeground(new Color(255, 130, 14));
        jLabel5.setText("Unid de Presentacion :");
        jLabel5.setBounds(new Rectangle(15, 40, 125, 15));
        jLabel5.setFont(new Font("SansSerif", 1, 11));
        jLabel5.setForeground(new Color(43, 141, 39));
        lblUnidadPresentacion.setText("CJA/20");
        lblUnidadPresentacion.setBounds(new Rectangle(155, 40, 170, 15));
        lblUnidadPresentacion.setFont(new Font("SansSerif", 1, 11));
        lblUnidadPresentacion.setForeground(new Color(255, 130, 14));
        jLabel7.setText("Laboratorio :");
        jLabel7.setBounds(new Rectangle(15, 115, 80, 15));
        jLabel7.setFont(new Font("SansSerif", 1, 11));
        jLabel7.setForeground(new Color(43, 141, 39));
        lblLaboratorio.setText("3M PERU S.A.");
        lblLaboratorio.setBounds(new Rectangle(155, 115, 240, 15));
        lblLaboratorio.setFont(new Font("SansSerif", 1, 11));
        lblLaboratorio.setForeground(new Color(255, 130, 14));
        jButtonLabel1.setText("Entero : ");
        jButtonLabel1.setBounds(new Rectangle(15, 140, 50, 20));
        jButtonLabel1.setForeground(new Color(255, 130, 14));
        jButtonLabel1.setMnemonic('e');
        jButtonLabel1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jButtonLabel1_actionPerformed(e);
                    }
                });
        txtEntero.setBounds(new Rectangle(70, 140, 60, 20));
        txtEntero.setDocument(new FarmaLengthText(5));
        txtEntero.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtEntero_keyPressed(e);
                    }

                    public void keyTyped(KeyEvent e) {
                        txtCantidad_keyTyped(e);
                    }
                });

        jLabelFunction1.setBounds(new Rectangle(335, 440, 105, 20));
        jLabelFunction1.setText("[ Enter ] Aceptar");
        jLabelFunction2.setBounds(new Rectangle(450, 440, 115, 20));
        jLabelFunction2.setText("[ ESCAPE ] Cerrar");
        lblTValorFraccion.setText("CJA/20");
        lblTValorFraccion.setBounds(new Rectangle(155, 90, 170, 15));
        lblTValorFraccion.setFont(new Font("SansSerif", 1, 11));
        lblTValorFraccion.setForeground(new Color(255, 130, 14));
        lblValorFraccion.setText("Valor Fraccion :");
        lblValorFraccion.setBounds(new Rectangle(15, 90, 85, 15));
        lblValorFraccion.setFont(new Font("SansSerif", 1, 11));
        lblValorFraccion.setForeground(new Color(43, 141, 39));

        txtFraccion.setBounds(new Rectangle(225, 140, 60, 20));
        txtFraccion.setDocument(new FarmaLengthText(5));
        txtFraccion.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtFraccion_keyPressed(e);
                    }

                    public void keyTyped(KeyEvent e) {
                        txtCantidad_keyTyped(e);
                    }
                });
        lblFraccion.setText("Fraccion :");
        lblFraccion.setBounds(new Rectangle(155, 140, 60, 20));
        lblFraccion.setForeground(new Color(255, 130, 14));
        lblFraccion.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jButtonLabel1_actionPerformed(e);
                    }
                });

        ///Hora
        txtHora.setBounds(new Rectangle(370, 140, 80, 20));
        txtHora.setDocument(new FarmaLengthText(8));
        txtHora.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtHora_keyPressed(e);
                    }
                    public void keyReleased(KeyEvent e)
                    {
                   txtHora_keyReleased(e);          
                     }
                });
        lblHora.setText("Hora :");
        lblHora.setBounds(new Rectangle(320, 140, 60, 20));
        lblHora.setForeground(new Color(255, 130, 14));
        lblHora.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jButtonLabelHora_actionPerformed(e);
                    }
                });

        //Hora

        lblUnidadVenta.setText("CJA/20");
        lblUnidadVenta.setBounds(new Rectangle(155, 65, 170, 15));
        lblUnidadVenta.setFont(new Font("SansSerif", 1, 11));
        lblUnidadVenta.setForeground(new Color(255, 130, 14));
        jLabel6.setText("Unid de Venta :");
        jLabel6.setBounds(new Rectangle(15, 65, 85, 15));
        jLabel6.setFont(new Font("SansSerif", 1, 11));
        jLabel6.setForeground(new Color(43, 141, 39));
        srcKArdex.setBounds(new Rectangle(10, 225, 555, 195));
        pnllTitle1.setBounds(new Rectangle(10, 200, 555, 25));
        btnRelacionMovimiento.setText("Relación de Movimientos del Producto");
        btnRelacionMovimiento.setBounds(new Rectangle(15, 5, 215, 15));
        btnRelacionMovimiento.setMnemonic('R');
        btnRelacionMovimiento.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnRelacionMovimiento_actionPerformed(e);
                    }
                });
        jPanelWhite1.add(jLabel6, null);
        jPanelWhite1.add(lblUnidadVenta, null);
        jPanelWhite1.add(lblFraccion, null);
        jPanelWhite1.add(lblHora, null);
        jPanelWhite1.add(txtHora, null);
        jPanelWhite1.add(txtFraccion, null);
        jPanelWhite1.add(lblValorFraccion, null);
        jPanelWhite1.add(lblTValorFraccion, null);
        jPanelWhite1.add(txtEntero, null);
        jPanelWhite1.add(jButtonLabel1, null);
        jPanelWhite1.add(lblLaboratorio, null);
        jPanelWhite1.add(jLabel7, null);
        jPanelWhite1.add(lblUnidadPresentacion, null);
        jPanelWhite1.add(jLabel5, null);
        jPanelWhite1.add(lblDescripcion, null);
        jPanelWhite1.add(lblCodigo, null);
        jPanelWhite1.add(jLabel1, null);
        srcKArdex.getViewport().add(tblKardex, null);
        pnllTitle1.add(btnRelacionMovimiento, null);
        jContentPane.add(pnllTitle1, null);
        jContentPane.add(srcKArdex, null);
        jContentPane.add(jLabelFunction2, null);
        jContentPane.add(jLabelFunction1, null);
        jContentPane.add(jPanelWhite1, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************	

    private void initialize() {
        initTable();
    }

    private void initTable() {
        tableModelKardex = 
                new FarmaTableModel(ConstantsInvDiario.columnsListaMovsKardex, 
                                    ConstantsInvDiario.defaultListaMovsKardex, 
                                    0);
        FarmaUtility.initSimpleList(tblKardex, tableModelKardex, 
                                    ConstantsInvDiario.columnsListaMovsKardex);
    }
    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        mostrarDatos();
        cargaListaMovimientos();
        if (VariablesInvDiario.vFraccion.equalsIgnoreCase("1")) {
            txtFraccion.setEditable(false);
            txtFraccion.setText("0");
        }
        FarmaUtility.moveFocus(txtEntero);
    }

    private void txtCantidad_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtEntero, e);
    }

    private void txtHora_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            chkKeyPressed(e);
        } else {
            chkKeyPressed(e);
        }
    }


    private void txtFraccion_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!txtHora.isEditable())
                chkKeyPressed(e);
            else
                FarmaUtility.moveFocus(txtHora);
        } else
            chkKeyPressed(e);
    }

    private void txtEntero_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!txtFraccion.isEditable())
                FarmaUtility.moveFocus(txtHora);
            //chkKeyPressed(e);
            else
                FarmaUtility.moveFocus(txtFraccion);
        } else
            chkKeyPressed(e);
    }


    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            e.consume();
            this.setVisible(false);
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (datosValidados()) {
                try {
                    ingresarCantidad();
                    FarmaUtility.aceptarTransaccion();
                    cerrarVentana(true);
                } catch (SQLException sql) {
                    FarmaUtility.liberarTransaccion();
                    log.error("",sql);
                    FarmaUtility.showMessage(this, 
                                             "Ocurrió un error al registrar la cantidad : \n" +
                            sql.getMessage(), txtEntero);
                    cerrarVentana(false);
                }
            }
        }
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************

    private void mostrarDatos() {
        lblCodigo.setText(VariablesInvDiario.vCodProd);
        lblDescripcion.setText(VariablesInvDiario.vDesProd);
        lblUnidadPresentacion.setText(VariablesInvDiario.vUnidPresentacion);
        lblUnidadVenta.setText(VariablesInvDiario.vUnidVta);
        lblLaboratorio.setText(VariablesInvDiario.vNomLab);
        txtEntero.setText(VariablesInvDiario.vCantEntera.trim());
        txtFraccion.setText(VariablesInvDiario.vCantFraccion.trim());
        lblTValorFraccion.setText(VariablesInvDiario.vFraccion.trim());
    }

    private void ingresarCantidad() throws SQLException {

        int entero = Integer.parseInt(txtEntero.getText().trim());
        int fraccion = Integer.parseInt(txtFraccion.getText().trim());
        String hora = txtHora.getText().trim();

        log.debug("entero : " + entero);
        log.debug("fraccion : " + fraccion);
        log.debug("Hora : " + hora);
        int cantidad = 
            entero * Integer.parseInt(VariablesInvDiario.vFraccion) + fraccion;
        String cantidadCompleta = "" + cantidad;
        log.debug("cantidad : " + cantidadCompleta);
        DBInvDiario.ingresaCantidadProdInvConteo(cantidadCompleta, hora,FarmaVariables.vIdUsu);
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private boolean datosValidados() {
        boolean rpta = true;
        if (txtEntero.getText().trim().length() == 0) {
            rpta = false;
            FarmaUtility.showMessage(this, "Ingrese la cantidad entera", 
                                     txtEntero);
        } else if (txtFraccion.getText().trim().length() == 0 && 
                   txtFraccion.isEditable()) {
            rpta = false;
            FarmaUtility.showMessage(this, "Ingrese la cantidad fraccion", 
                                     txtFraccion);
        } else if (txtFraccion.getText().trim().equalsIgnoreCase(VariablesInvDiario.vFraccion)) {
            rpta = false;
            FarmaUtility.showMessage(this, 
                                     "La cantidad de Fraccion no puede ser igual al Valor de Fraccion", 
                                     txtEntero);
        } else if (Integer.parseInt(txtFraccion.getText().trim()) > 
                   Integer.parseInt(VariablesInvDiario.vFraccion)) {
            rpta = false;
            FarmaUtility.showMessage(this, 
                                     "La cantidad de Fraccion ingresada no puede ser mayor al valor de Fraccion", 
                                     txtFraccion);
        }
         //valida hora
        else if (!validarCampos())
        {
	             rpta = false;
	             //FarmaUtility.showMessage(this, "La hora ingresada no es correcta", txtFraccion); 
        }

        return rpta;
    }

    private void jButtonLabel1_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtEntero);
    }

    private void jButtonLabelHora_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtEntero);
    }

    private void txtHora_keyReleased(KeyEvent e)
    {
      FarmaUtility.timeComplete(txtHora,e);
    }
  


    private void cargaListaMovimientos() {
        try {
            DBInvDiario.getListaMovsKardex(tableModelKardex);
            if (tblKardex.getRowCount() > 0) {
                FarmaUtility.ordenar(tblKardex, tableModelKardex, 1, 
                                     FarmaConstants.ORDEN_DESCENDENTE);
            }
        } catch (SQLException sql) {
            log.error("",sql);
            FarmaUtility.showMessage(this, 
                                     "Ocurrió un error al cargar la lista de movimientos : \n" +
                    sql.getMessage(), txtEntero);
        }
    }

    private void btnRelacionMovimiento_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocusJTable(tblKardex);
    }
 
    private boolean validarCampos()
    {
      boolean retorno = true;
       if(!FarmaUtility.isHoraMinutoValida(this,txtHora.getText(),"La hora ingresada no tiene el formato correcto"))
        retorno = false;
            
      return retorno;
    }

}
