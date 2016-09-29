package mifarma.ptoventa.controlAsistencia;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.controlAsistencia.reference.UtilityControlAsistencia;
import mifarma.ptoventa.controlAsistencia.reference.ConstantsControlAsistencia;
import mifarma.ptoventa.controlAsistencia.grafico.ToolTipGenerator;

import object.ObjRowTime;

import org.com.du.JTimeTable;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import popup.AlertPopup;

/**
 * Copyright (c) 2015 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgGestionPlantilla.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * CHUANES                    Creación<br>
 * EMAQUERA      15.10.2015   Modificacion<br>
 * <br>
 * @author CHUANES <br>
 * @version 1.0<br>
 *
 */
public class DlgGestionPlantilla extends JDialog  {
    private static final Logger log = LoggerFactory.getLogger(DlgGestionPlantilla.class);
    private Frame myParentFrame;
    private GridLayout gridLayout1 = new GridLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JPanelHeader pnlHeader = new JPanelHeader();
    private JPanelHeader pnlRigth = new JPanelHeader();
    private JButtonLabel btnDatosGenerales = new JButtonLabel();
    private JTextFieldSanSerif txtDesCorta = new JTextFieldSanSerif();
    private JPanelTitle pnlLstRoles = new JPanelTitle();
    private JScrollPane scrLstRoles = new JScrollPane();
    private JButtonLabel btnLstRoles = new JButtonLabel();
    private JButton btnConfirmar = new JButton();
    private JButton btnNuevo = new JButton();
    private JButton btnCancelar = new JButton();
    private JButton btnAdicionarFila = new JButton();
    private JButton btnEliminarFila = new JButton();
    private JPanel jPanel1 = new JPanel();
    private JTabbedPane pstPrincipal = new JTabbedPane();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JScrollPane scrEfectivoRendido = new JScrollPane();
    private JButtonLabel btnSemana = new JButtonLabel();
    private JButtonLabel btnFecInicio = new JButtonLabel();
    private CardLayout cardLayout = new CardLayout();
    private JTimeTable tblHorario = new JTimeTable(UtilityControlAsistencia.getMaxHorasSemana(),
                                        new ArrayList());
    private IntervalCategoryDataset dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private boolean accionNuevo;
    private String codPlantilla = "";
    private String nomPlantilla = "";
    private JPanel pnlPlantilla = new JPanel();
    private JLabel txtCodigo = new JLabel();
    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public DlgGestionPlantilla(Frame parent, String title, boolean modal, boolean valor, String codPlantilla,
                               String nomPlantilla) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            this.setNuevo(valor);
            this.setCodPlantilla(codPlantilla);
            this.setNomPlantilla(nomPlantilla);
            
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
        this.setSize(new Dimension(700, 483));
        this.setResizable(false);
        this.getContentPane().setLayout(gridLayout1);
        this.setTitle("Mantenimiento de Plantilla");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });

        pnlTitle.setBounds(new Rectangle(5, 5, 675, 20));
        pnlHeader.setBounds(new Rectangle(5, 25, 675, 50));
        pnlHeader.setBackground(Color.WHITE);

        btnDatosGenerales.setText("Mantenimiento de Plantillas");
        btnDatosGenerales.setBounds(new Rectangle(10, 0, 200, 20));

        btnDatosGenerales.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        tblHorario.setBounds(new Rectangle(5, 75, 675, 300));
        tblHorario.deleteColumnUser();

        tblHorario.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                // changeDesCorta_MouseCliked();
            }
        });

        pnlPlantilla.setBounds(new Rectangle(495, 0, 180, 20));
        pnlPlantilla.setBackground(new Color(0, 82, 0));
        pnlPlantilla.setLayout(null);
        txtCodigo.setText("0000100");
        txtCodigo.setBounds(new Rectangle(95, 0, 85, 20));
        txtCodigo.setBackground(SystemColor.window);
        txtCodigo.setForeground(SystemColor.window);
        txtCodigo.setFont(new Font("SansSerif", 1, 12));
        pnlLstRoles.setBounds(new Rectangle(5, 160, 785, 20));
        scrLstRoles.setBounds(new Rectangle(5, 180, 785, 275));
        btnAdicionarFila.setText("A\u00f1adir[ + ]");
        btnAdicionarFila.setBounds(new Rectangle(430, 10, 110, 25));
        btnAdicionarFila.setMnemonic('a');
        btnAdicionarFila.setFont(new Font("SansSerif", 1, 11));
        btnAdicionarFila.setDefaultCapable(true);
        btnAdicionarFila.setBackground(Color.green);
        btnAdicionarFila.setFocusable(true);
        btnAdicionarFila.setFocusPainted(true);
        btnAdicionarFila.setRequestFocusEnabled(true);
        btnAdicionarFila.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnAdicionaFila_actionPerformed(e);
            }
        });
        btnAdicionarFila.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                btnAdicionar_keyPressed(e);
            }
        });

        pnlRigth.setBackground(SystemColor.window);
        pnlRigth.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                    pnlRigth_keyPressed(e);
                }
        });

        btnEliminarFila.setText("Eliminar[ - ]");
        btnEliminarFila.setBounds(new Rectangle(550, 10, 115, 25));
        btnEliminarFila.setMnemonic('e');
        btnEliminarFila.setFont(new Font("SansSerif", 1, 11));
        btnEliminarFila.setDefaultCapable(true);
        btnEliminarFila.setBackground(Color.RED);
        btnEliminarFila.setFocusable(true);
        btnEliminarFila.setFocusPainted(true);
        btnEliminarFila.setRequestFocusEnabled(true);
        btnEliminarFila.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnEliminaFila_actionPerformed(e);
            }
        });
        btnEliminarFila.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                btnEliminar_keyPressed(e);
            }
        });
        btnCancelar.setText("Cancelar");
        btnCancelar.setBounds(new Rectangle(200, 120, 100, 25));
        btnCancelar.setMnemonic('a');
        btnCancelar.setFont(new Font("SansSerif", 1, 11));
        btnCancelar.setDefaultCapable(true);
        btnCancelar.setFocusable(true);
        btnCancelar.setFocusPainted(true);
        btnCancelar.setRequestFocusEnabled(true);
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionCerrar(e);
            }
        });
        btnCancelar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                btnCancelar_keyPressed(e);
            }
        });

        btnConfirmar.setText("Confirmar");
        btnConfirmar.setBounds(new Rectangle(50, 120, 100, 25));
        btnConfirmar.setMnemonic('o');
        btnConfirmar.setFont(new Font("SansSerif", 1, 11));
        btnConfirmar.setDefaultCapable(true);
        btnConfirmar.setFocusable(true);
        btnConfirmar.setFocusPainted(true);
        btnConfirmar.setRequestFocusEnabled(true);
        btnConfirmar.setEnabled(true);
        btnConfirmar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRegistrar_actionPerformed(e);
            }
        });
        btnConfirmar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                btnRegistar_keyPressed(e);
            }

        });
        btnNuevo.setText("Nuevo");
        btnNuevo.setBounds(new Rectangle(50, 120, 100, 25));
        btnNuevo.setMnemonic('u');
        btnNuevo.setFont(new Font("SansSerif", 1, 11));
        btnNuevo.setDefaultCapable(false);
        btnNuevo.setFocusable(false);
        btnNuevo.setFocusPainted(false);
        btnNuevo.setRequestFocusEnabled(false);
        btnNuevo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //getCodigoPlantilla();
            }
        });
        btnLstRoles.setText("Listado de Plantilla ");
        btnLstRoles.setBounds(new Rectangle(10, 0, 285, 20));
        btnLstRoles.setMnemonic('f');
        btnLstRoles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        btnSemana.setText("N\u00ba Plantilla :");
        btnSemana.setBounds(new Rectangle(20, 0, 75, 20));


        btnSemana.setFont(new Font("SansSerif", 1, 12));
        btnFecInicio.setText("Descripión               :");
        btnFecInicio.setBounds(new Rectangle(10, 10, 120, 20));
        btnFecInicio.setForeground(new Color(255, 130, 14));
        btnFecInicio.setMnemonic('s');
        btnFecInicio.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                FarmaUtility.moveFocus(txtDesCorta);
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        jPanel1.setLayout(null);
        jPanel1.setBackground(SystemColor.window);
        jPanel1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                FarmaUtility.moveFocus(txtDesCorta);
            }
        });
        pstPrincipal.setBounds(new Rectangle(5, 5, 690, 405));
        pstPrincipal.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                setData();
                if (pstPrincipal.getSelectedIndex() == 0) {
                    lblF1.setText("<html>[F1] Ir a Datos</html>");
                    setLocationRelativeTo(null);
                    FarmaUtility.moveFocus(txtDesCorta);
                }
                else
                if (pstPrincipal.getSelectedIndex() == 1) {
                    setLocationRelativeTo(null);
                    lblF1.setText("<html>[F1] Ir a Gráfico</html>");
                    FarmaUtility.moveFocus(txtDesCorta);
                }      
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        lblF1.setText("<html>[F1] Ver Gr\u00e1fico</html>");
        lblF1.setBounds(new Rectangle(10, 415, 115, 30));
        lblF11.setText("<html>[F11] Aceptar</html>");

        lblF11.setBounds(new Rectangle(385, 415, 110, 30));
        lblF11.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                grabar();
            }
        });
        lblEsc.setText("<html>[Esc] Cerrar</html>");
        lblEsc.setBounds(new Rectangle(510, 415, 115, 30));
        lblEsc.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                jLabel2_mouseClicked(e);
            }
        });
        txtDesCorta.setBounds(new Rectangle(130, 10, 230, 20));
        txtDesCorta.setLengthText(50);
        txtDesCorta.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtDesCorta_keyPressed(e);

            }

            public void keyReleased(KeyEvent e) {
                txtDesCorta_keyReleased(e);
            }

            public void keyTyped(KeyEvent e) {
                    txtDesCorta_keyTyped(e);
                }
        });
        txtDesCorta.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                txtDesCorta_focusLost(e);
            }
        });
        pnlHeader.add(txtDesCorta, null);

        pnlHeader.add(btnFecInicio, null);
        pnlHeader.add(btnAdicionarFila, null);
        pnlHeader.add(btnEliminarFila, null);
        scrEfectivoRendido.getViewport();
        pnlTitle.add(btnDatosGenerales, null);
        pnlTitle.add(pnlPlantilla, null);
        pnlPlantilla.add(txtCodigo, null);
        pnlPlantilla.add(btnSemana, null);
        jPanel1.add(pnlTitle, null);
        jPanel1.add(pnlHeader, null);
        jPanel1.add(tblHorario, null);
        pnlLstRoles.add(btnLstRoles, null);
        //jPanel1.add(pnlLstRoles, null);

        //jPanel1.add(scrLstRoles, null);
        pstPrincipal.addTab("Datos", jPanel1);
        pstPrincipal.addTab("Gráfico", pnlRigth);
        jContentPane.add(lblEsc, null);
        jContentPane.add(lblF1, null);
        jContentPane.add(lblF11, null);
        jContentPane.add(pstPrincipal, null);
        jContentPane.add(scrEfectivoRendido, null);
        setData();
        this.getContentPane().add(jContentPane, null);
    }

    /* ************************************************************************ */
    /*                             METODO "initialize()"                        */
    /* ************************************************************************ */

    private void initialize() {
        inicializaDatos();
        lstGrilla();
        initCodigoPlantilla();
    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */
    
    public ChartPanel createChartPanel(JFreeChart chart) {
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBounds(new Rectangle(0, 0, 560, 530));
        chartPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4),
                                                                BorderFactory.createLineBorder(Color.black)));
        chartPanel.setRefreshBuffer(true);
        
        return chartPanel;
    }
    
    /* ************************************************************************ */
    /*                                  METODO DE EVENTOS                       */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.moveFocus(txtDesCorta);
        setLocationRelativeTo(null);        
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    public void actionCerrar(ActionEvent e) {
        cerrarVentana(true);
    }

    private void btnRegistrar_actionPerformed(ActionEvent e) {
        grabar();
    }
    
    private void txtDesCorta_keyPressed(KeyEvent e) {
        eventoPressed(e);
    }

    private void txtDesCorta_focusLost(FocusEvent e) {
        FarmaUtility.moveFocus(txtDesCorta);
    }
    
    /* ************************************************************************ */
    /*                       METODO DE LOGICA DE NEGOCIOS                       */
    /* ************************************************************************ */

    public void lstGrilla() {
        try {
            ArrayList vListaRoles = new ArrayList();
            ArrayList vListaRangoHoras = new ArrayList();
            ArrayList vListaRangoHorasRefrigerio = new ArrayList();
            ArrayList vListaFilaModify = new ArrayList();
            tblHorario.clear();
            if (this.isGrabarPlantilla()) {
                UtilityControlAsistencia.listaPlantillaDefecto(vListaFilaModify);
            } else {
                UtilityControlAsistencia.listarDetallePlantilla(codPlantilla, vListaFilaModify);
            }
            UtilityControlAsistencia.listarSeleccionRoles(vListaRoles);
            UtilityControlAsistencia.listarSeleccionRangoHoras(vListaRangoHoras);
            UtilityControlAsistencia.listarSeleccionRangoHorasRefrigerio(vListaRangoHorasRefrigerio);
            tblHorario.setListObjRole(vListaRoles);
            tblHorario.setListObjTimes(vListaRangoHoras);
            tblHorario.setListObjTimesRefrigerio(vListaRangoHorasRefrigerio);
            tblHorario.loadDataModify(vListaFilaModify);
            tblHorario.deleteColumnUser();
            dataset = createDataset();
            chart = createChart(dataset, null);
            chartPanel = createChartPanel(chart);
            pnlRigth.setLayout(cardLayout);
            pnlRigth.add(chartPanel);
        } catch (Exception e) {
            log.error("error al listar plantilla detalle " + e.getMessage());
            FarmaUtility.showMessage(this, "Error en listar detalle de horarios.\n" +
                    e.getMessage(), txtDesCorta);
        }
    }

    public void setData() {
        dataset = createDataset();
        chart = createChart(dataset, null);
        chartPanel = createChartPanel(chart);
        pnlRigth.removeAll();
        pnlRigth.setLayout(cardLayout);
        pnlRigth.add(chartPanel);
        pnlRigth.revalidate();
    }

    public void initCodigoPlantilla() {
        if (!this.isGrabarPlantilla()) {
            txtCodigo.setText(getCodPlantilla());
            txtDesCorta.setText(getNomPlantilla());
            pnlPlantilla.setVisible(true);
        }
        else
            pnlPlantilla.setVisible(false);
    }

    private void txtDesCorta_keyReleased(KeyEvent e) {
        if (e.getKeyChar() == '+' ||e.getKeyChar() == '-') {
            if(txtDesCorta.getText().length()>0)
             txtDesCorta.setText(txtDesCorta.getText().substring(0, txtDesCorta.getText().length()-1));
        } 
    }

    private void btnRegistar_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(btnCancelar);
        }
    }

    private void btnCancelar_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(btnAdicionarFila);
        }
    }

    private void btnAdicionar_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(btnEliminarFila);
        }
        eventoPressed(e);
    }

    private void btnEliminar_keyPressed(KeyEvent e) {
        eventoPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            FarmaUtility.moveFocus(txtDesCorta);
        }
    }

    private void btnAdicionaFila_actionPerformed(ActionEvent e) {
        adicionaFila();
    }

    private void adicionaFila() {
        tblHorario.addRow();
    }

    private void btnEliminaFila_actionPerformed(ActionEvent e) {
        tblHorario.deleteRow();

    }

    public boolean isValidaciones() {
        // validaciones
        if (!validaDatos()) {
            return false;
        } else if (!validaRol()) {
            return false;
        } else if (!UtilityControlAsistencia.isValidaListaRowTime(tblHorario.getListaFilasHorario())) {
            return false;
        } /*else if (!isCeldaNulla()) {
            return false;
        }*/
        return true;
    }

    public void grabar() {
        if (JConfirmDialog.rptaConfirmDialogDefaultNo(this, 
                                                      "¿Esta seguro de grabar la plantilla?")) 
        {
            if (isValidaciones()) {
                if (isGrabarPlantilla()) {
                    grabarPlantilla(txtDesCorta.getText().trim());
                } else {
                    modificarPlantilla(txtCodigo.getText(), txtDesCorta.getText().trim());
                }
            }
        }
    }

    public void grabarPlantilla(String desCorta) {
        try {
            UtilityControlAsistencia.grabarNuevaPlantilla(desCorta, tblHorario.getListaFilasHorario());
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this, "Se grabó la plantilla correctamente.", txtCodigo);
            cerrarVentana(true);
        } catch (Exception e) {
            FarmaUtility.liberarTransaccion();
            log.error(e.getMessage());
            FarmaUtility.showMessage(this, "No puede grabar la plantilla.\n"+
                                           e.getMessage(),
                                     txtCodigo);

        }
    }

    public void modificarPlantilla(String codPlantilla, String desCorta) {
        try {
            // Modificar Plantilla
            UtilityControlAsistencia.modificarPlantilla(codPlantilla, desCorta,tblHorario.getListaFilasHorario());
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this, "Se modificó la plantilla correctamente.", txtCodigo);
            cerrarVentana(true);
        } catch (Exception e) {
            FarmaUtility.liberarTransaccion();
            log.error(e.getMessage());
            FarmaUtility.showMessage(this, "No puede modificar la plantilla.\n"+
                                           e.getMessage().substring(10, e.getMessage().indexOf("ORA-06512")),
                                     txtCodigo);

        }
    }
    
    public boolean validaDatos() {

        if (txtDesCorta.getText().trim().toString().length() == 0) {
            FarmaUtility.moveFocus(txtDesCorta);
            FarmaUtility.showMessage(this, "Ingresar Descripción !", null);
            return false;
        }
        if (txtDesCorta.getText().trim().toString().length() > 0 &&
            txtDesCorta.getText().trim().toString().length() <= 4) {
            FarmaUtility.showMessage(this, "Descripción mayor a 4 caracteres.", null);
            return false;
        }
        return true;
    }

    private void inicializaDatos() {
        txtCodigo.setText("");
        txtDesCorta.setText("");
    }

    /**Valida si esta seleccionado un rol por fila***/
    private boolean validaRol() {
        ArrayList<ObjRowTime> vListaFilas = tblHorario.getListaFilasHorario();
        log.info("**** datos de muestra como esta en el momento ****");
        for (int i = 0; i < vListaFilas.size(); i++) {
            log.info("-Fila " + i);
            ObjRowTime vFila = vListaFilas.get(i);
            log.info("  Rol Cod y Nombre " + vFila.getVRol().getPCodRol() + " / " + vFila.getVRol().getPDescRol());
            String codRol = vFila.getVRol().getPCodRol();
            if (codRol.equalsIgnoreCase(ConstantsControlAsistencia.COD_ROL) || codRol.equalsIgnoreCase("")) {
                FarmaUtility.showMessage(this, "Debe tener todos los roles seleccionados.", null);
                return false;

            }

        }
        return true;
    }

    public boolean isCeldaNulla() {
        return UtilityControlAsistencia.isCeldaNullaValidaHorario(tblHorario.getListaFilasHorario(), 0);
    }

    /******************FUNCIONALIDAD DEL JFREE CHART**************************/
    private IntervalCategoryDataset createDataset() {
        TaskSeriesCollection dataset = new TaskSeriesCollection();
        TaskSeries am;
        ArrayList<ObjRowTime> vListaFilas = tblHorario.getListaFilasHorario();
        ;
        String[][] tabla = new String[vListaFilas.size()][ConstantsControlAsistencia.LOG_MAX];
        for (int i = 0; i < vListaFilas.size(); i++) {
        //for (int i =vListaFilas.size()-1; i >= 0; i--) {
            log.info("-Fila " + i);
            ObjRowTime vFila = vListaFilas.get(i);
            String nomRol = vFila.getVRol().getPDescRol().trim();
            String Dia1 = vFila.getVDiaD1().getPDesHora().trim();
            String Dia2 = vFila.getVDiaD2().getPDesHora().trim();
            String Dia3 = vFila.getVDiaD3().getPDesHora().trim();
            String Dia4 = vFila.getVDiaD4().getPDesHora().trim();
            String Dia5 = vFila.getVDiaD5().getPDesHora().trim();
            String Dia6 = vFila.getVDiaD6().getPDesHora().trim();
            String Dia7 = vFila.getVDiaD7().getPDesHora().trim();
            tabla[i][0] = nomRol;
            tabla[i][1] = Dia1;
            tabla[i][2] = Dia2;
            tabla[i][3] = Dia3;
            tabla[i][4] = Dia4;
            tabla[i][5] = Dia5;
            tabla[i][6] = Dia6;
            tabla[i][7] = Dia7;


        }

        for (int i = 0; i < tabla.length; i++) {
        //for (int i = tabla.length-1; i >= 0; i--) {
            String nomPerfil = tabla[i][0].toString();
            am = new TaskSeries("N°" + (i + 1) + nomPerfil); //
            Task t1 = null;
            for (int c = 0; c < ConstantsControlAsistencia.COD_LIMITE; c++) {
                String nomColumna = UtilityControlAsistencia.getNomColumnaValidaHorario(c);
                String hora = tabla[i][(c + 1)];
                if (hora.equalsIgnoreCase(ConstantsControlAsistencia.NOM_CESADO) ||
                    hora.equalsIgnoreCase(ConstantsControlAsistencia.NOM_DESCANSO) ||
                    hora.equalsIgnoreCase(ConstantsControlAsistencia.NOM_SUBSIDIO) ||
                    hora.equalsIgnoreCase(ConstantsControlAsistencia.NOM_VACACIONES) ||
                    hora.equalsIgnoreCase(ConstantsControlAsistencia.FORMT_HORA) || hora.equalsIgnoreCase("")) {
                    hora = ConstantsControlAsistencia.HORA_DEFAULT;
                }
                log.info("hora" + hora);
                int horaInicio = Integer.parseInt(hora.substring(0, 2));
                int minInicio = Integer.parseInt(hora.substring(3, 5));
                int horaFin = Integer.parseInt(hora.substring(6, 8));
                int minFin = Integer.parseInt(hora.substring(9, 11));
                t1 = new Task(nomColumna, date(0, 0),date(23, 59)); //persona 1
                //t1 = new Task(nomColumna, date(23, 59),date(0, 0)); //persona 1
                if (horaInicio == horaFin) {
                    t1.addSubtask(new Task("", date(horaInicio, minInicio), date(horaInicio, minFin))); //quimico
                } else {
                    if (horaFin > horaInicio) {
                        t1.addSubtask(new Task("", date(horaInicio, minInicio), date(horaFin, minFin))); //quimico
                    } else {
                        t1.addSubtask(new Task("", date(horaInicio, minInicio), date(23, 59))); //quimico
                        t1.addSubtask(new Task("", date(0, 0), date(horaFin, minFin))); //quimico
                    }
                }
                am.add(t1);
            }
            dataset.add(am);
        }
        return dataset;
    }

    private JFreeChart createChart(IntervalCategoryDataset dataSet, String title) {
        IntervalCategoryDataset xyDataset = dataSet;
        JFreeChart jFreeChart =
            ChartFactory.createGanttChart("Representación Grafica", "Semana", "Horas", xyDataset, true, true, false);
        CategoryPlot plot = jFreeChart.getCategoryPlot();
        plot.setOrientation(PlotOrientation.VERTICAL);

        plot.getRenderer().setBaseToolTipGenerator(new ToolTipGenerator("{0}, {1}: ",
                                                                        DateFormat.getTimeInstance(DateFormat.SHORT)));

        return jFreeChart;
    }

    private Date date(int hour, int minute) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(2015, Calendar.JUNE, 1, hour, minute, 0);
        return calendar.getTime();
    }

    private void eventoPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F1) {
            visualizarGrafico();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if(tblHorario.getListaFilasHorario().size()>0){
                if (JConfirmDialog.rptaConfirmDialogDefaultNo(this, 
                                                              "¿Desea salir de la pantalla?" +
                                                            "Asegúrese de haber grabado su trabajo")) {
                    cerrarVentana(false);
                }
            }
            else {
                cerrarVentana(false);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F11) {
            if(tblHorario.getListaFilasHorario().size()>0)
                 grabar();
             else{
                 AlertPopup vMensaje = new AlertPopup(tblHorario.getParent(),
                                                      "Por favor debe agregar una o más filas\n" +
                                                      "para poder grabar la plantilla\n" +
                                                      "Gracias.");
                 vMensaje.show();
             }
            
        }
    }

    private void jLabel2_mouseClicked(MouseEvent e) {
        cerrarVentana(false);
    }

    private void pnlRigth_keyPressed(KeyEvent e) {
        eventoPressed(e);
    }

    public boolean isGrabarPlantilla() {
        return accionNuevo;
    }

    public void setNuevo(boolean accion) {
        this.accionNuevo = accion;
    }

    public String getCodPlantilla() {
        return codPlantilla;
    }

    public void setCodPlantilla(String codPlantilla) {
        this.codPlantilla = codPlantilla;
    }

    public String getNomPlantilla() {
        return nomPlantilla;
    }

    public void setNomPlantilla(String nomPlantilla) {
        this.nomPlantilla = nomPlantilla;
    }

    private void visualizarGrafico() {
        if (pstPrincipal.getSelectedIndex() == 0) {
            pstPrincipal.setSelectedIndex(1);
            lblF1.setText("<html>[F1] Ir a Datos</html>");
            setLocationRelativeTo(null);
            setData();
        } else {
            pstPrincipal.setSelectedIndex(0);
            setLocationRelativeTo(null);
            lblF1.setText("<html>[F1] Ir a Gráfico</html>");
            FarmaUtility.moveFocus(txtDesCorta);
        }
    }

    private void txtDesCorta_keyTyped(KeyEvent e) {
        if (e.getKeyChar() == '+') {
            tblHorario.addRow();
            if(txtDesCorta.getText().length()>0)
            txtDesCorta.setText(txtDesCorta.getText());
        } else if (e.getKeyChar() == '-') {
            tblHorario.deleteRow();
            if(txtDesCorta.getText().length()>0)
            txtDesCorta.setText(txtDesCorta.getText());
        }
    }
}
