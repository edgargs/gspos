package mifarma.ptoventa.controlAsistencia;

import aurelienribon.tweenengine.Tween;
import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JConfirmDialog;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import java.awt.BorderLayout;
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.text.html.HTMLEditorKit;

import javax.swing.text.html.StyleSheet;

import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import layaoutanimacion.du.PanelReporteHorarioDia;
import layaoutanimacion.du.PanelReporteHorarioDia.Accessor;
import mifarma.ptoventa.controlAsistencia.reference.UtilityControlAsistencia;
import mifarma.ptoventa.controlAsistencia.reference.ConstantsControlAsistencia;
import mifarma.ptoventa.controlAsistencia.reference.DBControlAsistencia;
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
import layaoutanimacion.du.SLAnimator;
import layaoutanimacion.du.SLConfig;
import layaoutanimacion.du.SLKeyframe;
import layaoutanimacion.du.SLPanel;
import layaoutanimacion.du.SLSide;
/**
 * Copyright (c) 2015 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgGestionHorario.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * CHUANES                    Creación<br>
 * EMAQUERA      15.10.2015   Modificacion<br>
 * <br>
 * @author CHUANES <br>
 * @version 1.0<br>
 *
 */
public class DlgGestionHorario extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgGestionHorario.class);
    private Frame myParentFrame;
    private GridLayout gridLayout1 = new GridLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelTitle pnlTitle = new JPanelTitle();
    private JPanelHeader pnlHeader = new JPanelHeader();
    private JPanelHeader pnlRigth = new JPanelHeader();
    private JButtonLabel btnLstRoles = new JButtonLabel();
    private JButton btnAdicionarFila = new JButton();
    private JButton btnEliminarFila = new JButton();
    private JScrollPane scrEfectivoRendido = new JScrollPane();
    private JButtonLabel txtCodigoA = new JButtonLabel();
    private JButtonLabel btnFecInicio = new JButtonLabel();
    private JButtonLabel btnlFecFin = new JButtonLabel();
    private JButtonLabel btnPlantilla = new JButtonLabel();
    private IntervalCategoryDataset dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private CardLayout cardLayout = new CardLayout();
    private String codPlantilla = "";
    private JPanel pnlPrincipal = new JPanel();
    private JTabbedPane pstPrincipal = new JTabbedPane();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction lblSalir = new JLabelFunction();
    private JTimeTable tblHorario = new JTimeTable(UtilityControlAsistencia.getMaxHorasSemana());
    private JComboBox cmbPlantilla = new JComboBox();
    private JLabelFunction lblF11 = new JLabelFunction();
    private boolean vIndAccionNuevo;
    private String fecInicio = "";
    private String fecFin = "";
    private String codHorario = "";
    private JPanel pnlNroHorario = new JPanel();
    private JLabel txtCodigo = new JLabel();
    private JLabel txtFecInicio = new JLabel();
    private JLabel txtFecFin = new JLabel();

    private boolean vPresionoTeclaDireccional = false;
    private JPanel pnlReporte = new JPanel();

    
    SLPanel panel = new SLPanel();
    PanelReporteHorarioDia p1 = new PanelReporteHorarioDia("Lunes");
    PanelReporteHorarioDia p2 = new PanelReporteHorarioDia("Sabado");
    PanelReporteHorarioDia p3 = new PanelReporteHorarioDia("Martes a Viernes");
    PanelReporteHorarioDia p4 = new PanelReporteHorarioDia("Domingo");
    
    
    
    SLConfig mainCfg, p1Cfg, p2Cfg, p3Cfg, p4Cfg, p5Cfg;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JLabelFunction lblF2 = new JLabelFunction();
    boolean vFocoBoton = false;
    private JTextField txtPrueba = new JTextField();
    private boolean vSoloVisualizar=false;
    private boolean flagMod = true;

    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */
    public DlgGestionHorario(Frame parent, String title, boolean modal, boolean valor, String codHorario,
                             String fecInicio, String fecFin,String pCodPlantilla,
                             boolean vSoloVer) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            this.setCodHorario(codHorario);
            this.setFecInicio(fecInicio);
            this.setFecFin(fecFin);
            this.setAccion(valor);
            this.setCodPlantilla(pCodPlantilla);
            this.setVSoloVisualizar(vSoloVer);
            Tween.registerAccessor(PanelReporteHorarioDia.class, new PanelReporteHorarioDia.Accessor());
            SLAnimator.start();            
            jbInit();
            initialize();
        } catch (Exception e) {
            log.error("", e);
        }
    }    
    public DlgGestionHorario(Frame parent, String title, boolean modal, boolean valor, String codHorario,
                             String fecInicio, String fecFin,String pCodPlantilla) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            this.setCodHorario(codHorario);
            this.setFecInicio(fecInicio);
            this.setFecFin(fecFin);
            this.setAccion(valor);
            this.setCodPlantilla(pCodPlantilla);
            Tween.registerAccessor(PanelReporteHorarioDia.class, new PanelReporteHorarioDia.Accessor());
            SLAnimator.start();            
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
        this.setSize(new Dimension(908, 511));
        this.setResizable(false);
        this.getContentPane().setLayout(gridLayout1);
        this.setTitle("Creación/Modificación de Horario");
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }
        });

        pnlTitle.setBounds(new Rectangle(15, 0, 860, 30));
        pnlHeader.setBounds(new Rectangle(15, 30, 860, 45));
        pnlHeader.setBackground(Color.WHITE);

        btnAdicionarFila.setText("A\u00f1adir[ + ]");
        btnAdicionarFila.setBounds(new Rectangle(465, 10, 185, 25));
        btnAdicionarFila.setMnemonic('a');
        btnAdicionarFila.setFont(new Font("SansSerif", 1, 11));
        btnAdicionarFila.setDefaultCapable(true);
        btnAdicionarFila.setBackground(Color.green);
        btnAdicionarFila.setFocusable(true);
        btnAdicionarFila.setFocusPainted(true);
        btnAdicionarFila.setRequestFocusEnabled(true);

        btnAdicionarFila.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnAdicionarFila_actionPerformed(e);
                }
            });
        pnlRigth.setBackground(SystemColor.window);

        pnlRigth.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    pnlRigth_keyPressed(e);
                }
            });
        btnEliminarFila.setText("Eliminar[ - ]");
        btnEliminarFila.setBounds(new Rectangle(670, 10, 175, 25));
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

        tblHorario.setBounds(new Rectangle(15, 75, 860, 260));

        lblF11.setText("<html>[F11] Aceptar </html>");
        lblF11.setBounds(new Rectangle(670, 445, 105, 30));
        lblF11.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                accionHorario();
            }
        });
        pnlNroHorario.setBounds(new Rectangle(585, 0, 275, 30));
        pnlNroHorario.setBackground(new Color(0, 82, 0));
        pnlNroHorario.setLayout(null);
        txtCodigo.setText("0000000010");
        txtCodigo.setBounds(new Rectangle(125, 5, 110, 20));
        txtCodigo.setFont(new Font("SansSerif", 1, 15));
        txtCodigo.setForeground(SystemColor.window);
        txtFecInicio.setText("14/09/2015");
        txtFecInicio.setBounds(new Rectangle(80, 0, 90, 25));
        txtFecInicio.setFont(new Font("SansSerif", 1, 17));
        txtFecInicio.setForeground(SystemColor.window);
        txtFecFin.setText("25/09/2015");
        txtFecFin.setBounds(new Rectangle(245, 0, 85, 25));
        txtFecFin.setFont(new Font("SansSerif", 1, 17));
        txtFecFin.setForeground(SystemColor.window);
        pnlReporte.setLayout(borderLayout1);
        pnlReporte.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    pnlReporte_keyPressed(e);
                }
            });
        panel.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    panel_keyPressed(e);
                }
            });
        p1.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    p1_keyPressed(e);
                }
            });
        p2.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    p2_keyPressed(e);
                }
            });
        p3.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    p3_keyPressed(e);
                }
            });
        p4.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    p4_keyPressed(e);
                }
            });
        lblF2.setText("[F2] Reporte ");
        lblF2.setBounds(new Rectangle(150, 445, 115, 30));
        lblF2.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    jLabel1_keyPressed(e);
                }
            });
        lblF2.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                    jLabel1_focusLost(e);
                }
            });
        //txtPrueba.setVisible(false);
        txtPrueba.setBounds(new Rectangle(315, 455, 15, 15));
        txtPrueba.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        txtPrueba.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                    txtPrueba_focusLost(e);
                }
            });
        txtPrueba.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    txtPrueba_keyPressed(e);
                }
            });
        pnlReporte.add(panel, BorderLayout.CENTER);
        lblSalir.setText("<html>[Esc] Cerrar</html>");
        lblSalir.setBounds(new Rectangle(800, 445, 100, 30));
        lblSalir.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    jLabel2_mouseClicked(e);
                }
            });

        btnLstRoles.setText("Listado de Horarios ");
        btnLstRoles.setBounds(new Rectangle(10, 0, 285, 20));
        btnLstRoles.setMnemonic('f');

        txtCodigoA.setText("N\u00ba  Horario:");
        txtCodigoA.setBounds(new Rectangle(10, 5, 85, 20));

        txtCodigoA.setFont(new Font("SansSerif", 1, 15));
        btnFecInicio.setText("Desde :");
        btnFecInicio.setBounds(new Rectangle(10, 0, 60, 25));
        btnFecInicio.setLayout(new BorderLayout());

        btnFecInicio.setFont(new Font("SansSerif", 1, 17));
        btnlFecFin.setText("Hasta :");
        btnlFecFin.setBounds(new Rectangle(180, 0, 75, 25));

        btnlFecFin.setFont(new Font("SansSerif", 1, 17));
        btnPlantilla.setText("Seleccionar:");
        btnPlantilla.setBounds(new Rectangle(5, 10, 75, 20));
        btnPlantilla.setForeground(new Color(255, 130, 14));
        btnPlantilla.setMnemonic('s');
        btnPlantilla.addKeyListener(new KeyListener() {

                @Override
                public void keyTyped(KeyEvent e) {
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    FarmaUtility.moveFocus(cmbPlantilla);
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }
            });

        pnlPrincipal.setLayout(null);
        pnlPrincipal.setBackground(SystemColor.window);
        pstPrincipal.setBounds(new Rectangle(5, 5, 895, 435));
        pstPrincipal.addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
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

        lblF1.setBounds(new Rectangle(5, 445, 115, 30));
        lblF1.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {


                }
            });

        cmbPlantilla.setBounds(new Rectangle(90, 10, 305, 20));
        cmbPlantilla.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    cmbPlantilla_keyPressed(e);
                }

                public void keyTyped(KeyEvent e) {
                    cmbPlantilla_keyTyped(e);
                }
            });
        cmbPlantilla.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    cmbPlantilla_itemStateChanged(e);
                }
            });
        cmbPlantilla.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {

                    cmbPlantilla_focusLost(e);
                }
            });

        pnlHeader.add(btnPlantilla, null);
        pnlHeader.add(cmbPlantilla, null);
        pnlHeader.add(btnAdicionarFila, null);
        pnlHeader.add(btnEliminarFila, null);
        scrEfectivoRendido.getViewport();

        pnlPrincipal.add(pnlHeader, null);
        pnlNroHorario.add(txtCodigo, null);
        pnlNroHorario.add(txtCodigoA, null);
        pnlTitle.add(txtFecInicio, null);
        pnlTitle.add(pnlNroHorario, null);
        pnlTitle.add(btnFecInicio, null);
        pnlTitle.add(btnlFecFin, null);
        pnlTitle.add(txtFecFin, null);
        pnlPrincipal.add(pnlTitle, null);
        pnlPrincipal.add(tblHorario, null);
        jContentPane.add(txtPrueba, null);
        jContentPane.add(lblF2, null);
        jContentPane.add(pstPrincipal, null);

        jContentPane.add(lblF1, null);
        jContentPane.add(lblSalir, null);
        jContentPane.add(lblF11, null);
        pstPrincipal.addTab("Datos", pnlPrincipal);
        pstPrincipal.addTab("Gráfico", pnlRigth);
        pstPrincipal.addTab("Reporte", pnlReporte);
        jContentPane.add(scrEfectivoRendido, null);
        pstPrincipal.setEnabledAt(0, false);
        pstPrincipal.setEnabledAt(1, false);
        pstPrincipal.setEnabledAt(2, false);
        this.getContentPane().add(jContentPane, null);
        
        iniReporteHorario();    
    }

    /* ************************************************************************ */
    /*                             METODO "initialize()"                        */
    /* ************************************************************************ */
    
    private void initialize() {
        inicializaDatos();
        listaComboPlantilla();
        iniHtml();
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

    // **************************************************************************
    // Metodos de eventos
    // **************************************************************************
    
    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(cmbPlantilla);
        verificaListaDefault();
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    public void actionCerrar(ActionEvent e) {
        cerrarVentana(true);
    }

    private void cmbPlantilla_keyPressed(KeyEvent e) {
        eventoPressed(e);
    }

    private void cmbPlantilla_focusLost(FocusEvent e) {
        FarmaUtility.moveFocus(cmbPlantilla);
    }

    private void btnAdicionarFila_actionPerformed(ActionEvent e) {
        agregaFila();
    }

    private void btnEliminaFila_actionPerformed(ActionEvent e) {
        quitaFila();
    }

    // **************************************************************************
    // Metodos auxiliares de eventos
    // **************************************************************************

    private void jLabel2_mouseClicked(MouseEvent e) {
        cerrarVentana(false);
    }
    
    private void cmbPlantilla_keyTyped(KeyEvent e) {
        if (e.getKeyChar() == '+') {
            agregaFila();
        } else if (e.getKeyChar() == '-') {
            quitaFila();
        }
    }

    private void pnlRigth_keyPressed(KeyEvent e) {
        eventoPressed(e);
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************
                
    public void setDetalleHorario(String pCodHorario) {
        try {
            ArrayList vListaRoles = new ArrayList();
            ArrayList vListaUsuarioRol = new ArrayList();
            ArrayList vListaRangoHoras = new ArrayList();
            ArrayList vListaRangoHorasRefrigerio = new ArrayList();            
            ArrayList vListaFilaModify = new ArrayList();
            ArrayList vListaFilaPreCargaSolicitudAprobada = new ArrayList();
            UtilityControlAsistencia.listarSeleccionRoles(vListaRoles);
            UtilityControlAsistencia.listarSeleccionUsuarios(vListaUsuarioRol);
            UtilityControlAsistencia.listarSeleccionRangoHoras(vListaRangoHoras);
            UtilityControlAsistencia.listarSeleccionRangoHorasRefrigerio(vListaRangoHorasRefrigerio);            
            UtilityControlAsistencia.lstDataModifyHorario(pCodHorario,vListaFilaModify);
            UtilityControlAsistencia.getTurnoRangoFechaSolicitudAprobada(vListaFilaPreCargaSolicitudAprobada,
                                                                         txtFecInicio.getText().trim(),
                                                                         txtFecFin.getText().trim());
            tblHorario.clear();
            tblHorario.setListObjRole(vListaRoles);
            tblHorario.setListObjUser(vListaUsuarioRol);
            tblHorario.setListObjTimes(vListaRangoHoras);
            tblHorario.setListObjTimesRefrigerio(vListaRangoHorasRefrigerio);            
            tblHorario.loadFecIniFecFin(txtFecInicio.getText().trim(),txtFecFin.getText().trim());
            tblHorario.loadDataUsuSolicitudAprobada(vListaFilaPreCargaSolicitudAprobada);
            tblHorario.loadDataModify(vListaFilaModify);
        } catch (Exception e) {
            log.error("error al listar detalle de horario " + e.getMessage());
            FarmaUtility.showMessage(this, "Error en listar detalle de horario.\n" +
                    e.getMessage(), cmbPlantilla);
        }
    }

    public void setDataGrafico() {
        dataset = createDataset();
        chart = createChart(dataset, null);
        chartPanel = createChartPanel(chart);
        pnlRigth.removeAll(); //
        pnlRigth.setLayout(cardLayout);
        pnlRigth.add(chartPanel);
        pnlRigth.revalidate(); //
    }

    private void cambioPlantillaSeleccionada() {
        if (JConfirmDialog.rptaConfirmDialog(this, "¿Desea trabajar con la plantilla seleccionada?")) 
            //&&
            //tblHorario.getListaFilasHorario().size()>0) 
        {
            try {
                int index = cmbPlantilla.getSelectedIndex();
                if (index == 0) {
                    if (isGrabarNuevo()) {
                        tblHorario.clear();
                    } else {
                        setDetalleHorario(getCodHorario());
                    }
                } else if (index > 0) {
                    ArrayList vListaRoles = new ArrayList();
                    ArrayList vListaUsuarioRol = new ArrayList();
                    ArrayList vListaRangoHoras = new ArrayList();
                    ArrayList vListaRangoHorasRefrigerio = new ArrayList(); 
                    ArrayList vListaFilasDefault = new ArrayList();
                    ArrayList vListaFilaPreCargaSolicitudAprobada = new ArrayList();
                    setCodPlantilla(FarmaLoadCVL.getCVLCode("cmbPlantilla", cmbPlantilla.getSelectedIndex()).toString().trim());
                    UtilityControlAsistencia.listarSeleccionRoles(vListaRoles);
                    UtilityControlAsistencia.listarSeleccionUsuarios(vListaUsuarioRol);
                    UtilityControlAsistencia.listarSeleccionRangoHoras(vListaRangoHoras);
                    UtilityControlAsistencia.listarSeleccionRangoHorasRefrigerio(vListaRangoHorasRefrigerio);                                
                    UtilityControlAsistencia.lstDataDefaultHorario(getCodPlantilla(),vListaFilasDefault);
                    UtilityControlAsistencia.getTurnoRangoFechaSolicitudAprobada(vListaFilaPreCargaSolicitudAprobada,
                                                                                 txtFecInicio.getText().trim(),
                                                                                 txtFecFin.getText().trim());


                                        
                    
                    tblHorario.clear();
                    tblHorario.setListObjRole(vListaRoles);
                    tblHorario.setListObjUser(vListaUsuarioRol);
                    tblHorario.setListObjTimes(vListaRangoHoras);
                    tblHorario.setListObjTimesRefrigerio(vListaRangoHorasRefrigerio); 
                    tblHorario.loadFecIniFecFin(txtFecInicio.getText().trim(),txtFecFin.getText().trim());
                    tblHorario.loadDataUsuSolicitudAprobada(vListaFilaPreCargaSolicitudAprobada);
                    tblHorario.setDefaultList(vListaFilasDefault);
                    btnAdicionarFila.setEnabled(true);
                    btnEliminarFila.setEnabled(true);
                }

            } catch (Exception e) {
                log.error("Error cambioListaPlantilla " + e.getMessage());
                FarmaUtility.showMessage(this, "No se pudo cargar los datos de la plantilla seleccionada.",
                                         cmbPlantilla);
            }
        }

    }

    private void listaComboPlantilla() {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        FarmaLoadCVL.loadCVLFromSP(cmbPlantilla, "cmbPlantilla", "PTOVENTA_CONTROL_ASISTENCIA.CTRL_F_GET_CMBO_PLANTILLA(?,?)",
                                   parametros, false, true);
        FarmaLoadCVL.setSelectedValueInComboBox(cmbPlantilla,"cmbPlantilla",getCodPlantilla());
    }

    public boolean validacionesGrabarHorario(){
        if (!validaDatos())
            return false;
        else if (!validaRol()) //ok
            return false;
        else if (!isSelectedUsuario()) //que todos los usuarios esten selecionados
            return false;
        else if (!UtilityControlAsistencia.isUSuarioSolitudValidaHorario(txtFecInicio.getText().trim(),
                                                                         txtFecFin.getText().trim(),
                                                                         tblHorario.getListaFilasHorario()))
            return false;
        else if (!UtilityControlAsistencia.isValidaListaRowTime(tblHorario.getListaFilasHorario())) //ok
            return false;
        else if (!isMaxHorasSemana()) 
            return false;        
        return true;
    }
    public void accionHorario() {
        if (JConfirmDialog.rptaConfirmDialogDefaultNo(this, 
                                                      "¿Esta seguro de grabar la plantilla?")) 
        {
            if(validacionesGrabarHorario()){
                if(isGrabarNuevo())
                   grabarNuevoHorario();
                else    
                   modificarHorario(); 
            }
        }
    }

    public void grabarNuevoHorario() {
        try {
            String pCodPlantilla = FarmaLoadCVL.getCVLCode("cmbPlantilla",
                                                        cmbPlantilla.getSelectedIndex()).toString().trim();
            UtilityControlAsistencia.grabaHorarioLocal( txtFecInicio.getText(),txtFecFin.getText(),
                                                       pCodPlantilla,
                                                       tblHorario.getListaFilasHorario());
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this, "Se grabó el horario correctamente.", txtCodigo);
            cerrarVentana(true);
        } catch (Exception e) {
            FarmaUtility.liberarTransaccion();
            log.error(e.getMessage());
            FarmaUtility.showMessage(this, "No puede grabar el horario.\n"+
                                           e.getMessage(),
                                     txtCodigo);

        }
    }

    public void modificarHorario() {
        try {
            // Modificar Plantilla
            String pCodPlantilla = FarmaLoadCVL.getCVLCode("cmbPlantilla",
                                                        cmbPlantilla.getSelectedIndex()).toString().trim();
            UtilityControlAsistencia.modificarHorarioLocal(getCodHorario(),pCodPlantilla,tblHorario.getListaFilasHorario());
            FarmaUtility.aceptarTransaccion();
            FarmaUtility.showMessage(this, "Se modificó el horario correctamente.", txtCodigo);
            cerrarVentana(true);
        } catch (Exception e) {
            FarmaUtility.liberarTransaccion();
            log.error("", e);
            FarmaUtility.showMessage(this, "No puede modificar el horario.\n" + e.getMessage(), txtCodigo);
        }
    }

    public void setFechasNuevo() {
        try {
            ArrayList lstFechas = new ArrayList();
            UtilityControlAsistencia.getFechasNuevoHorario(lstFechas);
            for (int k = 0; k < lstFechas.size(); k++) {
                String fecInicio = ((ArrayList)lstFechas.get(k)).get(0).toString();
                String fecFin = ((ArrayList)lstFechas.get(k)).get(1).toString();
                txtFecInicio.setText("" + fecInicio);
                txtFecFin.setText("" + fecFin);
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public boolean validaDatos() {
        if (txtFecInicio.getText().trim().toString().length() == 0) {
            FarmaUtility.showMessage(this, "Falta Ingresar Fecha Inicio.", null);
            return false;
        } else if (txtFecFin.getText().trim().toString().length() == 0) {
            FarmaUtility.showMessage(this, "Falta Ingresar Fecha Fin !", null);
            return false;
        } else if (!FarmaUtility.validaFecha(txtFecInicio.getText(), "dd/MM/yyyy")) {
            FarmaUtility.showMessage(this, "Formato Incorrecto de Fecha Inicio.", txtFecInicio);
            return false;

        } else if (!FarmaUtility.validaFecha(txtFecFin.getText(), "dd/MM/yyyy")) {
            FarmaUtility.showMessage(this, "Formato Incorrecto de Fecha Fin.", txtFecInicio);
            return false;
        }
        return true;
    }

    private void inicializaDatos() {
        txtCodigoA.setText("");
        txtFecInicio.setText("");
        txtFecFin.setText("");
        if(isGrabarNuevo()) {
            setFechasNuevo();
            btnAdicionarFila.setEnabled(false);
            btnEliminarFila.setEnabled(false);
            pnlNroHorario.setVisible(false);
        } else {
            txtCodigoA.setText("N\u00ba  Horario:");
            txtCodigo.setText(getCodHorario());
            txtFecInicio.setText(getFecInicio());
            txtFecFin.setText(getFecFin());
            pnlNroHorario.setVisible(true);
            setDetalleHorario(getCodHorario());
        }
    }

    public boolean isGrabarNuevo() {
        return vIndAccionNuevo;
    }

    public void setAccion(boolean accion) {
        this.vIndAccionNuevo = accion;
    }

    public String getFecInicio() {
        return fecInicio;
    }

    public void setFecInicio(String fecInicio) {
        this.fecInicio = fecInicio;
    }

    public String getFecFin() {
        return fecFin;
    }

    public void setFecFin(String fecFin) {
        this.fecFin = fecFin;
    }

    public String getCodHorario() {
        return codHorario;
    }

    public void setCodHorario(String codHorario) {
        this.codHorario = codHorario;
    }

    public boolean validaRol() {
        boolean bResultado = false;
            try {
            bResultado = UtilityControlAsistencia.isCompletoRolesHorario(tblHorario.getListaFilasHorario());
        } catch (Exception e) {
            bResultado = false;
            log.error("", e);
        }
            return bResultado;
    }


    /**
     * Valida la cantidad maxima de horas por semana que debe tener un usuario
     * si este no tiene vacaciones, subsidio ni cesado.
     * 48 <= Sum Horas <= 55
     * Si "tiene vacaciones, subsidio ni cesado."
     *  NO SE VALIDA
     * ***/
    public boolean isMaxHorasSemana() {
        double vMaxHoraSemanal_MFA = 0;
        try {
            vMaxHoraSemanal_MFA = DBControlAsistencia.maxCantHorasSemanaGestionPlantilla();
            double vMaxHoraSemanalLegal_MFA =DBControlAsistencia.maxCantHorasSemanaLegal();
            return UtilityControlAsistencia.isValidaMaxHorasHorarioValidaHorario(vMaxHoraSemanal_MFA,vMaxHoraSemanalLegal_MFA,
                                                                                 tblHorario.getListaFilasHorario());
        } catch (Exception sqle) {
            log.error("", sqle);
            return false;
        }
    }
    public boolean isSelectedUsuario() {
        return UtilityControlAsistencia.isCompletoUsuariosHorario(tblHorario.getListaFilasHorario());
    }

    public boolean isCeldaNulla() {
        return UtilityControlAsistencia.isCeldaNullaValidaHorario(tblHorario.getListaFilasHorario(), 1);
    }

    public IntervalCategoryDataset createDataset() {
        TaskSeriesCollection dataset = new TaskSeriesCollection();
        TaskSeries am;
        ArrayList<ObjRowTime> vListaFilas = tblHorario.getListaFilasHorario();
        ;
        String[][] tabla = new String[vListaFilas.size()][ConstantsControlAsistencia.LOG_MAX];
        for (int i = 0; i < vListaFilas.size(); i++) {
            log.info("-Fila " + i);
            ObjRowTime vFila = vListaFilas.get(i);
            String nomUsu = vFila.getVUsu().getPNombre().trim();
            String Dia1 = vFila.getVDiaD1().getPDesHora().trim();
            String Dia2 = vFila.getVDiaD2().getPDesHora().trim();
            String Dia3 = vFila.getVDiaD3().getPDesHora().trim();
            String Dia4 = vFila.getVDiaD4().getPDesHora().trim();
            String Dia5 = vFila.getVDiaD5().getPDesHora().trim();
            String Dia6 = vFila.getVDiaD6().getPDesHora().trim();
            String Dia7 = vFila.getVDiaD7().getPDesHora().trim();
            tabla[i][0] = nomUsu;
            tabla[i][1] = Dia1;
            tabla[i][2] = Dia2;
            tabla[i][3] = Dia3;
            tabla[i][4] = Dia4;
            tabla[i][5] = Dia5;
            tabla[i][6] = Dia6;
            tabla[i][7] = Dia7;
        }

        for (int i = 0; i < tabla.length; i++) {
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
                t1 = new Task(nomColumna, date(0, 0), date(23, 59)); //persona 1
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
            ChartFactory.createGanttChart("Representación Grafica", "Semana", "Horas", xyDataset, true, true, true);
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

    public String getCodPlantilla() {
        return codPlantilla;
    }

    public void setCodPlantilla(String codPlantilla) {
        this.codPlantilla = codPlantilla;
    }

    private void eventoPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP||
            e.getKeyCode() == KeyEvent.VK_DOWN||
            e.getKeyCode() == KeyEvent.VK_RIGHT||
            e.getKeyCode() == KeyEvent.VK_LEFT
        ) vPresionoTeclaDireccional = true;
        else
            vPresionoTeclaDireccional = false;
            
        
        
        if (e.getKeyCode() == KeyEvent.VK_F1) {
            cambiaOpcionPanel();
        } 
        else if (e.getKeyCode() == KeyEvent.VK_F2) {
            cambiaOpcionPanelReporte();
        }
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if(tblHorario.getListaFilasHorario().size()>0){
                if (isVSoloVisualizar()) {
                    cerrarVentana(false);
                } else {
                    if (JConfirmDialog.rptaConfirmDialogDefaultNo(this, 
                                                                  "¿Desea salir de la pantalla?\n" +
                                                                "Asegúrese de haber grabado su trabajo")) {
                        cerrarVentana(false);
                    }
                }
            }
            else{
                cerrarVentana(false);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F11) {
            
            if(isVSoloVisualizar()){
                FarmaUtility.showMessage(this,"No puede modificar este horario, debe entrar con la opción modificar.", cmbPlantilla);
            }
            else{
                if(FarmaLoadCVL.getCVLCode("cmbPlantilla", cmbPlantilla.getSelectedIndex()).toString().trim().length()>0)
                {
                   if(tblHorario.getListaFilasHorario().size()>0)
                      accionHorario();
                    else{
                        AlertPopup vMensaje = new AlertPopup(tblHorario.getParent(),
                                                             "Por favor debe agregar 1 o más filas\n" +
                                                             "para poder grabar el horario\n" +
                                                             "Gracias.");
                        vMensaje.show();
                    }
                   
                   
                }
                else{
                    AlertPopup vMensaje = new AlertPopup(tblHorario.getParent(),
                                                         "Por favor debe seleccionar una plantilla\n" +
                                                         "para poder grabar el horario\n" +
                                                         "Gracias.");
                    vMensaje.show();
                }
            }
                
        }else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(this.isVisible())
               cambioPlantillaSeleccionada();
        }
        
    }


    private void cambiaOpcionPanel() {
        vFocoBoton=false;
        if (pstPrincipal.getSelectedIndex() == 0) {
            pstPrincipal.setSelectedIndex(1);
            lblF1.setText("<html>[F1] Ir a Datos</html>");
            lblF2.setText("<html>[F2] Reporte</html>");
            FarmaUtility.moveFocus(cmbPlantilla);
            setDataGrafico();
        } else {
            if (pstPrincipal.getSelectedIndex() == 2) {
                pstPrincipal.setSelectedIndex(0);
                lblF1.setText("<html>[F1] Ir a Gráfico</html>");
                lblF2.setText("<html>[F2] Reporte</html>");
                FarmaUtility.moveFocus(cmbPlantilla);    
            }
            else{
                pstPrincipal.setSelectedIndex(0);
                lblF1.setText("<html>[F1] Ir a Gráfico</html>");
                lblF2.setText("<html>[F2] Reporte</html>");
                FarmaUtility.moveFocus(cmbPlantilla);    
            }
            
        }        
        
    }

    private void verificaListaDefault() {
        if(getCodPlantilla().trim().length()==0&&isGrabarNuevo()){
        try {
                ArrayList vListaRoles = new ArrayList();
                ArrayList vListaUsuarioRol = new ArrayList();
                ArrayList vListaRangoHoras = new ArrayList();
                ArrayList vListaRangoHorasRefrigerio = new ArrayList();             
                ArrayList vListaFilaPreCargaSolicitudAprobada = new ArrayList();
                setCodPlantilla(FarmaLoadCVL.getCVLCode("cmbPlantilla",
                                                        cmbPlantilla.getSelectedIndex()).toString().trim());
                UtilityControlAsistencia.listarSeleccionRoles(vListaRoles);
                UtilityControlAsistencia.listarSeleccionUsuarios(vListaUsuarioRol);
                UtilityControlAsistencia.listarSeleccionRangoHoras(vListaRangoHoras);
                UtilityControlAsistencia.listarSeleccionRangoHorasRefrigerio(vListaRangoHorasRefrigerio);            
                UtilityControlAsistencia.getTurnoRangoFechaSolicitudAprobada(vListaFilaPreCargaSolicitudAprobada,
                                                                             txtFecInicio.getText().trim(),
                                                                             txtFecFin.getText().trim());
                tblHorario.clear();
                tblHorario.setListObjRole(vListaRoles);
                tblHorario.setListObjUser(vListaUsuarioRol);
                tblHorario.setListObjTimes(vListaRangoHoras);
                tblHorario.setListObjTimesRefrigerio(vListaRangoHorasRefrigerio);            
                tblHorario.loadFecIniFecFin(txtFecInicio.getText().trim(),txtFecFin.getText().trim());
                tblHorario.loadDataUsuSolicitudAprobada(vListaFilaPreCargaSolicitudAprobada);            

                

                btnAdicionarFila.setEnabled(true);
                btnEliminarFila.setEnabled(true);
            } catch (Exception sqle) {
                log.error("", sqle);
            }   
        } 
        //flagMod = UtilityControlAsistencia.isEditableHorario(this.getCodHorario()); POR MIENTRAS SE DECIDIO QUE IGUAL SE MODIFIQUE EL HORARIO
        if (isVSoloVisualizar()) {
            tblHorario.setEditable(false);
            /*
            if (!isVSoloVisualizar()) {
                FarmaUtility.showMessage(this, 
                                         "El horario esta en curso, no se podra modificar", 
                                         null);
                cerrarVentana(false);
            } 
            */
        }
    }

    private void cmbPlantilla_itemStateChanged(ItemEvent event) {
        if (event.getStateChange() == ItemEvent.SELECTED && !vPresionoTeclaDireccional) {
                 //Object item = event.getItem();
                 // do something with object
            if (!isVSoloVisualizar()) {
                if(this.isVisible())
                   cambioPlantillaSeleccionada();
            }             
        }
    }
    
    private void agregaFila(){
        if (!isVSoloVisualizar()) {
            if(FarmaLoadCVL.getCVLCode("cmbPlantilla", cmbPlantilla.getSelectedIndex()).toString().trim().length()>0)
            {
               tblHorario.addRow();
            }
            else{
                AlertPopup vMensaje = new AlertPopup(tblHorario.getParent(),
                                                     "Por favor debe seleccionar una plantilla\n" +
                                                     "para poder elaborar el horario\n" +
                                                     "Gracias.");
                vMensaje.show();
            }
        }
    }
    
    private void quitaFila(){
        if (!isVSoloVisualizar()) {
            if(FarmaLoadCVL.getCVLCode("cmbPlantilla", cmbPlantilla.getSelectedIndex()).toString().trim().length()>0)
            {
               tblHorario.deleteRow();
            }
            else{
                AlertPopup vMensaje = new AlertPopup(tblHorario.getParent(),
                                                     "Por favor debe seleccionar una plantilla\n" +
                                                     "para poder elaborar el horario\n" +
                                                     "Gracias.");
                vMensaje.show();
            }
        }
    }

    private void iniHtml() {
        /*cargaEditorPane(vEpUno);
        cargaEditorPane(vEpDos);
        cargaEditorPane(vEpTres);
        cargaEditorPane(vEpCuatro);
        cargaEditorPane(jepReporte);*/
        
        /*jepReporte.setEditorKit(new LargeHTMLEditorKit());
        jepReporte.getDocument().putProperty("ZOOM_FACTOR", new Double(0.1));
        cargaEditorPane(jepReporte);*/
    }


    private void disableActions() {
            p1.disableAction();
            p2.disableAction();
            p3.disableAction();
            p4.disableAction();
    }

    private void enableActions() {
            p1.enableAction();
            p2.enableAction();
            p3.enableAction();
            p4.enableAction();
    }

    private final Runnable p1Action = new Runnable() {@Override public void run() {
            disableActions();

            panel.createTransition()
                    .push(new SLKeyframe(p1Cfg, 0.8f)
                            .setEndSide(SLSide.LEFT, p2, p3, p4)
                            .setCallback(new SLKeyframe.Callback() {@Override public void done() {
                                p1.cambioZomm(true);
                                p1.setAction(p1BackAction);
                                p1.enableAction();
                            }}))
                    .play();
    }};

    private final Runnable p1BackAction = new Runnable() {@Override public void run() {
            disableActions();
            panel.createTransition()
                    .push(new SLKeyframe(mainCfg, 0.8f)
                            .setStartSide(SLSide.LEFT, p2, p3, p4)
                            .setCallback(new SLKeyframe.Callback() {@Override public void done() {
                                p1.cambioZomm(false);
                                p1.setAction(p1Action);
                                enableActions();
                            }}))
                    .play();
    }};
    
    private final Runnable p2Action = new Runnable() {@Override public void run() {
            disableActions();

            panel.createTransition()
                    .push(new SLKeyframe(p2Cfg, 0.8f)
                            .setEndSide(SLSide.LEFT, p1, p3, p4)
                            .setCallback(new SLKeyframe.Callback() {@Override public void done() {
                                    p2.cambioZomm(true);
                                    p2.setAction(p2BackAction);
                                    p2.enableAction();
                            }}))
                    .play();
    }};

    private final Runnable p2BackAction = new Runnable() {@Override public void run() {
            disableActions();
            panel.createTransition()
                    .push(new SLKeyframe(mainCfg, 0.8f)
                            .setStartSide(SLSide.LEFT, p1, p3, p4)
                            .setCallback(new SLKeyframe.Callback() {@Override public void done() {
                                    p2.cambioZomm(false);
                                    p2.setAction(p2Action);
                                    enableActions();
                            }}))
                    .play();
    }};

    
    private final Runnable p3Action = new Runnable() {@Override public void run() {
            disableActions();

            panel.createTransition()
                    .push(new SLKeyframe(p3Cfg, 0.8f)
                            .setEndSide(SLSide.LEFT, p1, p2, p4)
                            .setCallback(new SLKeyframe.Callback() {@Override public void done() {
                                    p3.cambioZomm(true);
                                    p3.setAction(p3BackAction);
                                    p3.enableAction();
                            }}))
                    .play();
    }};

    private final Runnable p3BackAction = new Runnable() {@Override public void run() {
            disableActions();
            panel.createTransition()
                    .push(new SLKeyframe(mainCfg, 0.8f)
                            .setStartSide(SLSide.LEFT, p1, p2, p4)
                            .setCallback(new SLKeyframe.Callback() {@Override public void done() {
                                    p3.cambioZomm(false);
                                    p3.setAction(p3Action);
                                    enableActions();
                            }}))
                    .play();
    }};    


    
    private final Runnable p4Action = new Runnable() {@Override public void run() {
            disableActions();

            panel.createTransition()
                    .push(new SLKeyframe(p4Cfg, 0.8f)
                            .setEndSide(SLSide.LEFT, p1, p2, p3)
                            .setCallback(new SLKeyframe.Callback() {@Override public void done() {
                                    p4.cambioZomm(true);
                                    p4.setAction(p4BackAction);
                                    p4.enableAction();
                            }}))
                    .play();
    }};

    private final Runnable p4BackAction = new Runnable() {@Override public void run() {
            disableActions();
            panel.createTransition()
                    .push(new SLKeyframe(mainCfg, 0.8f)
                            .setStartSide(SLSide.LEFT, p1, p2, p3)
                            .setCallback(new SLKeyframe.Callback() {@Override public void done() {
                                    p4.cambioZomm(false);
                                    p4.setAction(p4Action);
                                    enableActions();
                            }}))
                    .play();
    }};        
    private void cargaEditorPane(JEditorPane jepAux) {
        jepAux.setContentType("text/html");
        jepAux.setText(getHtmlDiaReporte());        
    }
    
    private String getHtmlDiaReporte(){

        String pCodHtml = " ";
        pCodHtml ="<style type=\"text/css\">\n" + 
        "body {\n" + 
        "    font-family: 'trebuchet MS', 'Lucida sans', Arial;\n" + 
        "    color: #444;    margin: 10px auto;\n" + 
        "}\n" + 
        "table {\n" + 
        "    border-spacing: 0;\n" + 
        "    font-size: 10;\n" + 
        "}\n" + 
        "table td{\n" + 
        "    width: 50px;\n" + 
        "}\n" + 
        ".bordered td, .bordered th {\n" + 
        "    padding: 4px;\n" + 
        "    text-align: center;  " +
            "\n" + 
        "}.bordered th {\n" + 
        "    background-color: #ffefd6;\n" + 
        "}</style><div >\n" + 
        "<table border=1 class=\"bordered\">\n" + 
        "<tr class=\"style4\"><th ><b></b></th><th colspan=\"16\"><b>LUNES</b></th></tr>\n" + 
        "<tr class=\"style4\"><th ><b>*</b></th><th><b>7:-8:</b></th><th ><b>8:-9:</b></th><th><b>9:-10:</b></th><th ><b>10:-11:</b></th><th><b>11:-12:</b></th>\n" + 
        "<th><b>12:-13:</b></th><th ><b>13:-14:</b></th><th><b>14:-15:</b></th><th ><b>15:-16:</b></th><th><b>16:-17:</b></th>\n" + 
        "<th><b>17:-18:</b></th><th ><b>18:-19:</b></th><th><b>19:-20:</b></th><th ><b>20:-21:</b></th><th><b>21:-22:</b></th><th><b>22:-23:</b></th>\n" + 
        "</tr>\n" + 
        "<tr class=\"style4\"><th>QF</th><th></th><th>X</th><th>X</th><th></th><th>X</th><th>X</th><th>X</th><th></th><th>X</th><th>X</th><th>X</th><th></th><th>X</th><th>X</th><th>X</th><th></th></tr>\n" + 
        "<tr class=\"style4\"><th>VD1</th><th>X</th><th>X</th><th>X</th><th>X</th><th>X</th><th>X</th><th>X</th><th>X</th><th>X</th><th>X</th><th>X</th><th>X</th><th>X</th><th>X</th><th>X</th><th>X</th></tr>\n" + 
        "<tr class=\"style4\"><th>VD2</th><th>X</th><th>X</th><th>X</th><th>X</th><th>X</th><th>X</th><th>X</th><th>X</th><th>X</th><th>X</th><th>X</th><th>X</th><th>X</th><th>X</th><th>X</th><th>X</th></tr>\n" + 
        "<tr class=\"style4\"><th>VD3</th><th>X</th><th></th><th>X</th><th>X</th><th>X</th><th></th><th>X</th><th>X</th><th></th><th>X</th><th>X</th><th></th><th>X</th><th>X</th><th></th><th>X</th></tr>\n" + 
        "<tr class=\"style4\"><th>VD4</th><th>X</th><th>X</th><th>X</th><th></th><th>X</th><th>X</th><th>X</th><th>X</th><th></th><th>X</th><th>X</th><th>X</th><th>X</th><th></th><th>X</th><th>X</th></tr>\n" + 
        "<tr class=\"style4\"><th>VD5</th><th>X</th><th></th><th></th><th >X</th><th>X</th><th >X</th><th>X</th><th >X</th><th>X</th><th >X</th><th>X</th><th >X</th><th>X</th><th >X</th><th>X</th><th>X</th></tr>\n" + 
        "<tr class=\"style4\"><th colspan=\"17\"></th></tr>\n" + 
        "<tr class=\"style4\"><th>Total VD</th><th>5</th><th>4</th><th>5</th><th >4</th><th>6</th><th >5</th><th>6</th><th >5</th><th>4</th><th >6</th><th>6</th><th >4</th><th>6</th><th >5</th><th>5</th><th>5</th></tr>\n" + 
        "<tr class=\"style4\"><th>Tickets</th><th>13</th><th>5</th><th>0</th><th >0</th><th>60</th><th >50</th><th>0</th><th >0</th><th>4</th><th >1</th><th>2</th><th >45</th><th>20</th><th >35</th><th>19</th><th>3</th></tr>\n" + 
        "<tr class=\"style4\"><th>Tickets /VD</th><th>2</th><th>1</th><th>0</th><th >0</th><th>10</th><th >10</th><th>0</th><th >0</th><th>1</th><th >0</th><th>0</th><th >12</th><th>3</th><th >7</th><th>3</th><th>0</th></tr>\n" + 
        "</table></div>";
        return pCodHtml;
    }


    private void iniReporteHorario() {
        p1.setAction(p1Action);
        p2.setAction(p2Action);
        p3.setAction(p3Action);
        p4.setAction(p4Action);
        mainCfg = new SLConfig(panel)
        .gap(10, 10)
        .row(1f).row(1f).col(1f).col(1f)
        .place(0, 0, p1)
        .place(1, 0, p2)
        .place(0, 1, p3)
        .place(1, 1, p4);
        p1Cfg = new SLConfig(panel)
                .gap(10, 10)
                .row(1f).col(1f)
                .place(0, 0, p1);
        
        p2Cfg = new SLConfig(panel)
                .gap(10, 10)
                .row(1f).col(1f)
                .place(0, 0, p2);

        p3Cfg = new SLConfig(panel)
                .gap(10, 10)
                .row(1f).col(1f)
                .place(0, 0, p3);
        
        p4Cfg = new SLConfig(panel)
                .gap(10, 10)
                .row(1f).col(1f)
                .place(0, 0, p4);
        panel.setTweenManager(SLAnimator.createTweenManager());
        panel.initialize(mainCfg);        
    }

    private void setDataGrafico(MouseEvent e) {
    }

    private void pnlReporte_keyPressed(KeyEvent e) {
        eventoPressed(e);
    }

    private void cambiaOpcionPanelReporte() {
        if (pstPrincipal.getSelectedIndex() == 0||pstPrincipal.getSelectedIndex() == 1) {
            vFocoBoton=true;
            FarmaUtility.moveFocus(lblF2);
            FarmaUtility.moveFocus(txtPrueba);
            pstPrincipal.setSelectedIndex(2);            
            lblF1.setText("<html>[F1] Ir a Datos</html>");
            lblF2.setText("<html>[F2] Ir a Gráfico</html>");
            setDataReporte();
        }else{
            if (pstPrincipal.getSelectedIndex() == 2) {
                pstPrincipal.setSelectedIndex(1);
                lblF1.setText("<html>[F1] Ir a Datos</html>");
                lblF2.setText("<html>[F2] Reporte</html>");
                FarmaUtility.moveFocus(cmbPlantilla);
                setDataGrafico();     
            }
            else{
                pstPrincipal.setSelectedIndex(0);
                lblF1.setText("<html>[F1] Ir a Gráfico</html>");
                lblF2.setText("<html>[F2] Reporte</html>");
                FarmaUtility.moveFocus(cmbPlantilla);    
            }
        }
        
    }

    private void setDataReporte() {
        ArrayList vListLunes = new ArrayList();
        ArrayList vListM_V = new ArrayList();
        ArrayList vListSabado = new ArrayList();
        ArrayList vListDomingo = new ArrayList();
        UtilityControlAsistencia.getDatosReporteHorario(txtCodigo.getText(),vListLunes,vListM_V,vListSabado,vListDomingo);
        p1.cargaReporte(vListLunes);
        p2.cargaReporte(vListSabado);
        p3.cargaReporte(vListM_V);
        p4.cargaReporte(vListDomingo);
    }

    private void panel_keyPressed(KeyEvent e) {
        eventoPressed(e);
    }

    private void p1_keyPressed(KeyEvent e) {
        eventoPressed(e);
    }

    private void p2_keyPressed(KeyEvent e) {
        eventoPressed(e);
    }

    private void p3_keyPressed(KeyEvent e) {
        eventoPressed(e);
    }

    private void p4_keyPressed(KeyEvent e) {
        eventoPressed(e);
    }

    private void jLabel1_keyPressed(KeyEvent e) {
        eventoPressed(e);
    }

    private void jLabel1_focusLost(FocusEvent e) {
        
    }

    private void txtPrueba_focusLost(FocusEvent e) {
        if(vFocoBoton){
            //txtPrueba.setText("cambio.. pruebas");
        FarmaUtility.moveFocus(txtPrueba);
        }
    }

    private void txtPrueba_keyPressed(KeyEvent e) {
        eventoPressed(e);
    }

    public void setVSoloVisualizar(boolean vSoloVisualizar) {
        this.vSoloVisualizar = vSoloVisualizar;
    }

    public boolean isVSoloVisualizar() {
        return vSoloVisualizar;
    }
}
