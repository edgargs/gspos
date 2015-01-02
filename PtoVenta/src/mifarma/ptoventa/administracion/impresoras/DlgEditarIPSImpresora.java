package mifarma.ptoventa.administracion.impresoras;

import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;

import java.awt.Label;
import java.awt.Rectangle;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.event.WindowAdapter;

import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.border.BevelBorder;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.impresoras.reference.DBImpresoras;
import mifarma.ptoventa.administracion.impresoras.reference.VariablesImpresoras;
import mifarma.ptoventa.reference.UtilityPtoVenta;
import mifarma.ptoventa.ventas.reference.UtilityVentas;

public class DlgEditarIPSImpresora extends JDialog
{
    
    Frame myParentFrame;
    private JPanelWhite pnlFondo = new JPanelWhite();
    private CardLayout cardLayout1 = new CardLayout();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JPanelHeader pnlBody = new JPanelHeader();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblF5 = new JLabelFunction();
    private JLabelWhite lblTitle = new JLabelWhite();
    private JLabelWhite lblImpBoleta = new JLabelWhite();
    private JLabelWhite lblImpFactura = new JLabelWhite();
    private JLabelWhite lblImpTermica = new JLabelWhite();
    private JLabelWhite lblIP = new JLabelWhite();
    private JLabelWhite lblIP_T = new JLabelWhite();
    private JLabel lblImpBol_T = new JLabel();
    private JLabel lblImpFac_T = new JLabel();
    private JLabel lblImpTer_T = new JLabel();

    public DlgEditarIPSImpresora() {
        this(null, "", false);
    }

    public DlgEditarIPSImpresora(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(591, 303));
        this.getContentPane().setLayout(cardLayout1);
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened(WindowEvent e) {
                    this_windowOpened(e);
                }
            });
        pnlFondo.setFocusable(false);
        pnlTitle.setBounds(new Rectangle(10, 10, 565, 25));
        pnlTitle.setFocusable(false);
        pnlBody.setBounds(new Rectangle(10, 35, 565, 195));
        pnlBody.setFocusable(false);
        lblEsc.setText("[ ESC ] Cerrar");
        lblEsc.setBounds(new Rectangle(440, 240, 135, 25));
        lblEsc.setFocusable(false);
        lblF11.setText("[ Enter ] Cambiar"); // kmoncada cambia tecla de seleccion de campo
        lblF11.setBounds(new Rectangle(10, 240, 125, 25));
        lblF11.setFocusable(false);
        lblTitle.setText("Detalle de Impresoras configuradas para la IP");
        lblTitle.setBounds(new Rectangle(20, 0, 305, 25));
        lblTitle.setFocusable(false);
        lblImpBoleta.setText("Impresora Boletas:");
        lblImpBoleta.setBounds(new Rectangle(25, 55, 120, 20));
        lblImpBoleta.setFocusable(false);
        lblImpBoleta.setSize(new Dimension(120, 20));
        lblImpFactura.setText("Impresora Facturas:");
        lblImpFactura.setBounds(new Rectangle(25, 95, 125, 20));
        lblImpFactura.setFocusable(false);
        lblImpTermica.setText("Impresora Térmica:");
        lblImpTermica.setBounds(new Rectangle(25, 135, 130, 20));
        lblImpTermica.setFocusable(false);
        lblIP.setText("IP:");
        lblIP.setBounds(new Rectangle(25, 20, 110, 20));
        lblIP.setFocusable(false);
        lblIP_T.setText("___.___.___.___");
        lblIP_T.setBounds(new Rectangle(155, 20, 185, 20));
        lblIP_T.setFocusable(false);
        lblImpBol_T.setBounds(new Rectangle(150, 55, 395, 20));
        lblImpBol_T.setBackground(Color.white);
        lblImpBol_T.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        lblImpBol_T.setOpaque(true);
        lblImpBol_T.setNextFocusableComponent(lblImpFac_T);
        lblImpBol_T.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    lblImpBol_T_focusGained(e);
                }

                public void focusLost(FocusEvent e) {
                    lblImpBol_T_focusLost(e);
                }
            });
        lblImpBol_T.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    lblImpBol_T_keyPressed(e);
                }
            });
        lblImpFac_T.setBounds(new Rectangle(150, 95, 395, 20));
        lblImpFac_T.setBackground(Color.white);
        lblImpFac_T.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        lblImpFac_T.setOpaque(true);
        lblImpFac_T.setNextFocusableComponent(lblImpTer_T);
        lblImpFac_T.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    lblImpFac_T_focusGained(e);
                }

                public void focusLost(FocusEvent e) {
                    lblImpFac_T_focusLost(e);
                }
            });
        lblImpFac_T.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    lblImpFac_T_keyPressed(e);
                }
            });
        lblImpTer_T.setBounds(new Rectangle(150, 135, 395, 20));
        lblImpTer_T.setBackground(Color.white);
        lblImpTer_T.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        lblImpTer_T.setOpaque(true);
        lblImpTer_T.setNextFocusableComponent(lblImpBol_T);
        lblImpTer_T.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    lblImpTer_T_focusGained(e);
                }

                public void focusLost(FocusEvent e) {
                    lblImpTer_T_focusLost(e);
                }
            });
        lblImpTer_T.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    lblImpTer_T_keyPressed(e);
                }
            });
        lblF5.setText("[ F5 ] Desasignar");
        lblF5.setBounds(new Rectangle(145, 240, 125, 25));

        pnlFondo.add(lblF5, null);
        pnlFondo.add(lblF11, null);
        pnlFondo.add(lblEsc, null);
        pnlBody.add(lblImpTer_T, null);
        pnlBody.add(lblImpFac_T, null);
        pnlBody.add(lblImpBol_T, null);
        pnlBody.add(lblIP_T, null);
        pnlBody.add(lblIP, null);
        pnlBody.add(lblImpTermica, null);
        pnlBody.add(lblImpFactura, null);
        pnlBody.add(lblImpBoleta, null);
        pnlFondo.add(pnlBody, null);
        pnlTitle.add(lblTitle, null);
        pnlFondo.add(pnlTitle, null);
        this.getContentPane().add(pnlFondo, "pnlFondo");
        FarmaUtility.centrarVentana(this);
        this.setResizable(false);
    }
    
    public void setDatos(String ip)
    {   lblIP_T.setText(ip);
    }
    
    private void cargarImpresoras()
    {   ArrayList temp = new ArrayList();
        try
        {
            DBImpresoras.getImpresorasIP(lblIP_T.getText(), temp);
            
            ArrayList temp2 = (ArrayList)temp.get(0);
            
            lblImpBol_T.setText(temp2.get(0).toString());
            lblImpFac_T.setText(temp2.get(1).toString());
            lblImpTer_T.setText(temp2.get(2).toString());
        }
        catch(Exception e)
        {   
        }
    }
    
    private void chkKeyPressed(KeyEvent e) {
        e.consume();

        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            cambiarFoco(e); // kmoncada control de teclado
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cerrarVentana(false);
        }
        /*else if (e.getKeyCode() == KeyEvent.VK_ENTER)
            {
                ((JComponent)e.getSource()).transferFocus();
            }*/
        else if (e.getKeyCode() == KeyEvent.VK_F5) {
            if (e.getSource() == lblImpBol_T) {
                if (JConfirmDialog.rptaConfirmDialog(this, "Esta seguro de eliminar la asignacion de la impresora de boletas?")) {
                    quitarAsignacion("B");
                    cargarImpresoras();
                }
            } else if (e.getSource() == lblImpFac_T) {
                if (JConfirmDialog.rptaConfirmDialog(this, "Esta seguro de eliminar la asignacion de la impresora de facturas?")) {
                    quitarAsignacion("F");
                    cargarImpresoras();
                }
            } else if (e.getSource() == lblImpTer_T) {
                if (JConfirmDialog.rptaConfirmDialog(this, "Esta seguro de eliminar la asignacion de la impresora termica?")) {
                    quitarAsignacion("T");
                    cargarImpresoras();
                }
            }
        } else if (e.getKeyCode() ==
                   KeyEvent.VK_ENTER) //UtilityPtoVenta.verificaVK_F11(e)) //e.getKeyCode() == KeyEvent.VK_F11)
        {
            if (e.getSource() == lblImpBol_T) {
                DlgListaImpresoraMaquina dlglista = new DlgListaImpresoraMaquina(this.myParentFrame, "", true);
                dlglista.setValores("B");
                dlglista.setVisible(true);
                cargarImpresoras();
            } else if (e.getSource() == lblImpFac_T) {
                DlgListaImpresoraMaquina dlglista = new DlgListaImpresoraMaquina(this.myParentFrame, "", true);
                dlglista.setValores("F");
                dlglista.setVisible(true);
                cargarImpresoras();
            } else if (e.getSource() == lblImpTer_T) {
                DlgListaImpresoraTermica dlglista = new DlgListaImpresoraTermica(this.myParentFrame, "", true);
                dlglista.setVisible(true);
                cargarImpresoras();
            }
        }
    }
    
    private void quitarAsignacion(String indTipoComp)
    {
        String Sec = VariablesImpresoras.vSecIP;
        String IP = lblIP_T.getText();
        try
        {
            DBImpresoras.quitarAsignacion(Sec + "", indTipoComp);
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this, "Actualización exitosa.", null);
        }
        catch (SQLException sql)
        {
            //log.error("",sql);
            FarmaUtility.showMessage(this, 
                                     "Ocurrió un error al eliminar asignación.\n" + sql.getMessage(), 
                                     null);
        }
    }
    
    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void this_windowOpened(WindowEvent e)
    {   cargarImpresoras();
        lblImpBol_T.grabFocus();
    }

    private void lblFocusGained(FocusEvent e)
    {   ((JComponent)e.getSource()).setBackground(Color.yellow);
    }
    
    private void lblFocusLost(FocusEvent e)
    {   ((JComponent)e.getSource()).setBackground(Color.white);
    }
    
    private void lblImpBol_T_focusGained(FocusEvent e)
    {   lblFocusGained(e);
    }

    private void lblImpFac_T_focusGained(FocusEvent e)
    {   lblFocusGained(e);
    }

    private void lblImpTer_T_focusGained(FocusEvent e)
    {   lblFocusGained(e);
    }

    private void lblImpBol_T_focusLost(FocusEvent e)
    {   lblFocusLost(e);
    }

    private void lblImpFac_T_focusLost(FocusEvent e)
    {   lblFocusLost(e);
    }

    private void lblImpTer_T_focusLost(FocusEvent e)
    {   lblFocusLost(e);
    }

    private void lblImpBol_T_keyPressed(KeyEvent e)
    {   chkKeyPressed(e);
    }

    private void lblImpFac_T_keyPressed(KeyEvent e)
    {   chkKeyPressed(e);
    }

    private void lblImpTer_T_keyPressed(KeyEvent e)
    {   chkKeyPressed(e);
    }

    // kmoncada permite realizar el cambio de foco entre los textfields
    private void cambiarFoco(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            (((JLabel)evt.getSource()).getNextFocusableComponent()).requestFocus();
        } else {
            if (evt.getKeyCode() == KeyEvent.VK_UP) {
                Component[] componentes = pnlBody.getComponents();
                int cont = 0;

                while (cont < componentes.length) {
                    if (componentes[cont] instanceof JLabel) {
                        Object origen = (JLabel)componentes[cont];
                        Object siguiente = ((JLabel)componentes[cont]).getNextFocusableComponent();
                        Object actual = ((JLabel)evt.getSource());
                        if (siguiente == actual) {
                            ((JLabel)componentes[cont]).requestFocus();
                            cont = componentes.length + 1;
                        }
                    }
                    cont++;
                }
            }
        }
    }
}