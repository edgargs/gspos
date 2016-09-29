package mifarma.ptoventa.main;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.IOException;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.controlAsistencia.DlgControlAsistencia;
import mifarma.ptoventa.controlAsistencia.DlgListaMaestroTurnoLocal;
import mifarma.ptoventa.controlAsistencia.DlgListadoHorario;
import mifarma.ptoventa.controlAsistencia.DlgListadoInasistencia;
import mifarma.ptoventa.controlAsistencia.DlgListadoPlantilla;
import mifarma.ptoventa.controlAsistencia.DlgListadoSolicitud;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.reportes.DlgRegistroVentas;
import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.ventas.reference.VariablesVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2015 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : FrmEconoFarCtrlAsist.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ASOSA      15.10.2015   Creación<br>
 * <br>
 * @author Alfredo Sosa Dordán<br>
 * @version 1.0<br>
 *
 */
public class FrmCtrlAsist extends JFrame {

    /* ********************************************************************** */
    /*                        DECLARACION PROPIEDADES                         */
    /* ********************************************************************** */

    private static final Logger log = LoggerFactory.getLogger(FrmCtrlAsist.class);

    private static String tituloBaseFrame = "Control de Asistencia";
    private int ind = 0;
    private JLabel statusBar = new JLabel();

    private JPanel pnlEconoFar = new JPanel();
    private BorderLayout borderLayoutCtrlAsist = new BorderLayout();

    private JMenuBar mnuPtoVenta = new JMenuBar();
    private JMenuItem mnuReportes_ConsistHorario = new JMenuItem();
    private JMenu mnuEconoFar_Solicitudes = new JMenu();
    private JMenuItem mnuSolicitudes_Inasistencias = new JMenuItem();
    private JMenuItem mnuSolicitudes_GestSolicitud = new JMenuItem();

    private JMenu mnuEconoFar_ControlIngreso = new JMenu();
    private JMenu mnuControlIngreso_GestHorario = new JMenu();
    private JMenuItem mnuControlIngreso_MarcacionES = new JMenuItem();
    private JMenuItem mnuGestHorario_Turno = new JMenuItem();
    private JMenuItem mnuGestHorario_Plantilla = new JMenuItem();
    private JMenuItem mnuGestHorario_Horario = new JMenuItem();
    private JMenu mnuEconoFar_Salir = new JMenu();
    private JMenuItem mnuSalir_SalirSistema = new JMenuItem();


    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */

    public FrmCtrlAsist() {

        try {
            cargaVariablesBD();
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

        this.getContentPane().setLayout(borderLayoutCtrlAsist);
        this.setSize(new Dimension(800, 600));
        this.setFont(new Font("SansSerif", 0, 11));
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                this_windowOpened(e);
            }

            public void windowClosing(WindowEvent e) {
                salir(e);
            }

            public void windowClosed(WindowEvent e) {
                this_windowClosed(e);
            }
        });

        pnlEconoFar.setLayout(null);
        pnlEconoFar.setFont(new Font("SansSerif", 0, 11));
        pnlEconoFar.setBackground(Color.white);
        mnuPtoVenta.setFont(new Font("SansSerif", 0, 11));
        this.setJMenuBar(mnuPtoVenta);
        mnuSolicitudes_GestSolicitud.setText("1. Gestion de Solicitudes");
        mnuSolicitudes_GestSolicitud.setFont(new Font("SansSerif", 0, 11));

        // MODULO REPORTES
        mnuSolicitudes_GestSolicitud.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuSolicitudes_GestSolicitud_actionPerformed(e);
                }
            });
        mnuPtoVenta.add(mnuEconoFar_ControlIngreso);
        mnuPtoVenta.add(mnuEconoFar_Solicitudes);
        mnuReportes_ConsistHorario.setText("1. Consistencia de Horario");
        mnuReportes_ConsistHorario.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_ConsistHorario.setMnemonic('1');
        mnuReportes_ConsistHorario.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuReportes_ConsistHorario_actionPerformed(e);
                }
            });
        mnuEconoFar_Solicitudes.setText("Solicitudes");
        mnuEconoFar_Solicitudes.setMnemonic('E');
        mnuEconoFar_Solicitudes.setFont(new Font("SansSerif", 0, 11));
        mnuSolicitudes_Inasistencias.setText("2. Inasistencias");
        mnuSolicitudes_Inasistencias.setMnemonic('2');
        mnuSolicitudes_Inasistencias.setFont(new Font("SansSerif", 0, 11));
        mnuSolicitudes_Inasistencias.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuSolicitudes_Inasistencias_actionPerformed(e);
                }
            });
        mnuEconoFar_Solicitudes.add(mnuSolicitudes_GestSolicitud);
        mnuEconoFar_Solicitudes.add(mnuSolicitudes_Inasistencias);

        // MODULO CONTROL INGRESO
        mnuEconoFar_ControlIngreso.setText("Control de Ingreso");
        mnuEconoFar_ControlIngreso.setMnemonic('d');
        mnuEconoFar_ControlIngreso.setFont(new Font("SansSerif", 0, 11));
        mnuEconoFar_ControlIngreso.add(mnuControlIngreso_GestHorario);
        mnuEconoFar_ControlIngreso.add(mnuControlIngreso_MarcacionES);
        mnuGestHorario_Turno.setText("1.Mantenimiento de Turnos");
        mnuGestHorario_Turno.setFont(new Font("SansSerif", 0, 11));
        mnuGestHorario_Turno.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuGestHorario_Turno_actionPerformed(e);
                }
            });
        mnuGestHorario_Plantilla.setText("2.Administración de Plantillas");
        mnuGestHorario_Plantilla.setFont(new Font("SansSerif", 0, 11));
        mnuGestHorario_Plantilla.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuGestHorario_Plantilla_actionPerformed(e);
                }
            });
        mnuGestHorario_Horario.setText("3.Administración de Horarios");
        mnuGestHorario_Horario.setFont(new Font("SansSerif", 0, 11));
        mnuGestHorario_Horario.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuGestHorario_Horario_actionPerformed(e);
                }
            });

        mnuControlIngreso_GestHorario.add(mnuGestHorario_Turno);
        mnuControlIngreso_GestHorario.add(mnuGestHorario_Plantilla);
        mnuControlIngreso_GestHorario.add(mnuGestHorario_Horario);
        mnuControlIngreso_GestHorario.setText("1. Gestion de Horario");
        mnuControlIngreso_GestHorario.setFont(new Font("SansSerif", 0, 11));
        mnuControlIngreso_MarcacionES.setText("2. Marcaci\u00f3n de Entrada/Salida");
        mnuControlIngreso_MarcacionES.setFont(new Font("SansSerif", 0, 11));
        mnuControlIngreso_MarcacionES.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    mnuControlIngreso_MarcacionES_actionPerformed(e);

                }
            });

        mnuEconoFar_Salir.setText("Salir");
        mnuEconoFar_Salir.setFont(new Font("SansSerif", 0, 11));
        mnuEconoFar_Salir.setMnemonic('s');
        mnuPtoVenta.add(mnuEconoFar_Salir);
        mnuSalir_SalirSistema.setText("Salir del Sistema");
        mnuSalir_SalirSistema.setFont(new Font("SansSerif", 0, 11));
        mnuSalir_SalirSistema.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mnuSalir_SalirSistema_actionPerformed(e);
                }
            });
        mnuEconoFar_Salir.add(mnuSalir_SalirSistema);

        statusBar.setText("Copyright (c) 2005 - 2015");
        statusBar.setHorizontalAlignment(SwingConstants.CENTER);
        this.getContentPane().add(statusBar, BorderLayout.SOUTH);
        this.getContentPane().add(pnlEconoFar, BorderLayout.CENTER);

    }

    /* ************************************************************************ */
    /*                                  METODO initialize                       */
    /* ************************************************************************ */

    private void initialize() {
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        readFileFarmaVentaProperties();
        cargaIcono();        
        obtenerInformacionLocal();
        muestraUser();
        inicializa();
    }

    /* ************************************************************************ */
    /*                            METODOS INICIALIZACION                        */
    /* ************************************************************************ */

    /* ************************************************************************ */
    /*                            METODOS DE EVENTOS                            */
    /* ************************************************************************ */

    private void this_windowOpened(WindowEvent e) {
        //DlgProcesar dlgProcesar = new DlgProcesar(this, "", true);
        //dlgProcesar.mostrar();
        DlgControlAsistencia asistencia=new DlgControlAsistencia(this,"",true);
        asistencia.setVisible(true);
        
    }

    private void mnuSalir_SalirSistema_actionPerformed(ActionEvent e) {
        salirSistema();
    }

    private void mnuReportes_ConsistHorario_actionPerformed(ActionEvent e) {
        DlgRegistroVentas dlgRegistroVentas = new DlgRegistroVentas(this, "", true);
        dlgRegistroVentas.setVisible(true);
    }

    private void mnuSolicitudes_Inasistencias_actionPerformed(ActionEvent e) {
        DlgListadoInasistencia dlgInasistencia=new DlgListadoInasistencia(this,"",true);
        dlgInasistencia.setVisible(true);        
    }

    private void mnuGestHorario_Plantilla_actionPerformed(ActionEvent e) {
        DlgListadoPlantilla dlgPlantilla=new DlgListadoPlantilla(this,"",true);
        dlgPlantilla.setVisible(true);
    }
    
    private void mnuGestHorario_Horario_actionPerformed(ActionEvent e) {
        DlgListadoHorario dlgHorario=new DlgListadoHorario(this,"",true);
        dlgHorario.setVisible(true);
    }
  
   
    private void mnuControlIngreso_MarcacionES_actionPerformed(ActionEvent e){
        DlgControlAsistencia asistencia=new DlgControlAsistencia(this,"",true);
        asistencia.setVisible(true);
    }
   
    
    private void mnuGestHorario_Turno_actionPerformed(ActionEvent e) {
        DlgListaMaestroTurnoLocal dlgTurno=new DlgListaMaestroTurnoLocal(this,"",true);
        dlgTurno.setVisible(true);
    }

    private void this_windowClosed(WindowEvent e) {
        UtilityVentas.eliminaCodBarra();
        UtilityVentas.eliminaBoletaTxt();
    }

    /* ************************************************************************ */
    /*                     METODOS AUXILIARES DE EVENTOS                        */
    /* ************************************************************************ */

    /* ************************************************************************ */
    /*                     METODOS DE LOGICA DE NEGOCIO                         */
    /* ************************************************************************ */

    void obtenerInformacionLocal() {
        
        obtieneInfoLocal();
        //obtener info del local
        if (!validaIpPc()) {
            FarmaUtility.showMessage(this, "Error al obtener la IP de la PC.", null);
            salirSistema();
        }
        
    }

    private boolean validaIpPc() {
        FarmaVariables.vIpPc = FarmaUtility.getHostAddress();
        if (FarmaVariables.vIpPc.trim().length() == 0)
            return false;
        return true;
    }

    private void muestraUser() {
        this.setTitle(tituloBaseFrame + 
                      " /  Local : " + 
                      FarmaVariables.vDescCortaLocal + 
                      " / " + 
                      "IP : " +
                      FarmaVariables.vIpPc);
		
		VariablesVentas.vInd_Carga_Prod_Vta = "N";
    }

    public void obtieneInfoLocal() {
        try {
            ArrayList infoLocal = DBPtoVenta.obtieneDatosLocal();
            FarmaVariables.vDescCortaLocal = ((String)((ArrayList)infoLocal.get(0)).get(0)).trim();
            FarmaVariables.vDescLocal = ((String)((ArrayList)infoLocal.get(0)).get(1)).trim();
            FarmaVariables.vTipLocal = ((String)((ArrayList)infoLocal.get(0)).get(2)).trim();
            FarmaVariables.vTipCaja = ((String)((ArrayList)infoLocal.get(0)).get(3)).trim();

            FarmaVariables.vNomCia = ((String)((ArrayList)infoLocal.get(0)).get(4)).trim();
            FarmaVariables.vNuRucCia = ((String)((ArrayList)infoLocal.get(0)).get(5)).trim();
            FarmaVariables.vMinutosPedidosPendientes = ((String)((ArrayList)infoLocal.get(0)).get(6)).trim();
            FarmaVariables.vImprReporte = ((String)((ArrayList)infoLocal.get(0)).get(7)).trim();
            FarmaVariables.vIndHabilitado = ((String)((ArrayList)infoLocal.get(0)).get(8)).trim();
            FarmaVariables.vDescCortaDirLocal = ((String)((ArrayList)infoLocal.get(0)).get(9)).trim();

            VariablesPtoVenta.vRazonSocialCia = DBPtoVenta.obtieneRazSoc();
            VariablesPtoVenta.vTelefonoCia = DBPtoVenta.obtieneTelfCia();
            VariablesPtoVenta.vNombreMarcaCia = DBPtoVenta.obtieneNombreMarcaCia();
        } catch (SQLException sqlException) {
            log.error("", sqlException);
        }
    }

    private void cargaVariablesBD() {
        FarmaVariables.vPUERTO = ConstantsPtoVenta.PUERTO;
    }

    private void salirSistema() {
        UtilityVentas.eliminaCodBarra();
        UtilityVentas.eliminaBoletaTxt();
        if (FarmaVariables.vEconoFar_Matriz)
            this.dispose();
        else
            System.exit(0);
    }

    private void salir(WindowEvent e) {
        salirSistema();
    }

    private void inicializa() {
        pnlEconoFar.setBackground(Color.WHITE);
    }

    /**
     * Obtiene indicador de Direccion Local
     * @author ERIOS
     * @since 12.09.2013
     */
    public void obtieneIndDireLocal() {
        try {
            VariablesPtoVenta.vIndDirLocal = DBPtoVenta.obtieneIndDirLocal();
        } catch (SQLException sql) {
            log.error("", sql);
        }
        log.debug("VariablesPtoVenta.vIndDirLocal=" + VariablesPtoVenta.vIndDirLocal);
    }

    /**
     * Obtiene indicador de Direccion Local
     * @author ERIOS
     * @since 12.09.2013
     */
    public static void obtieneIndRegistroVentaRestringida() {
        try {
            VariablesPtoVenta.vIndRegistroVentaRestringida = DBPtoVenta.obtieneIndRegistroVentaRestringida();
        } catch (SQLException sql) {
            log.error("", sql);
        }
        log.debug("VariablesPtoVenta.vIndRegistroVentaRestringida=" + VariablesPtoVenta.vIndRegistroVentaRestringida);
    }

    /**
     * Lee properties para recuperar la version del sistema
     * @author ERIOS
     * @since 19.02.2013
     * @return
     */
    private boolean readFileFarmaVentaProperties() {
        Properties prop = new Properties();
        try {
            prop.load(FrmCtrlAsist.class.getResourceAsStream("/farmaventa.properties"));
            FarmaVariables.vNombreModulo = "ControlAsistencia";
            FarmaVariables.vVersion = prop.getProperty("version");
            FarmaVariables.vCompilacion = prop.getProperty("compilacion");
            DlgProcesar.setVersion();

            tituloBaseFrame =
                    FarmaVariables.vNombreModulo + " " + FarmaVariables.vVersion + " - " + FarmaVariables.vCompilacion;
            log.info("Version: " + tituloBaseFrame);
        } catch (IOException e) {
            log.error("", e);
            return false;
        }
        return true;
    }

    /**
     * Lee y carga el icono del sistema.
     * @author ERIOS
     * @since 27.02.2013
     */
    private void cargaIcono() {
        String strRutaJpg = "";
        try {
            strRutaJpg = DBPtoVenta.obtieneRutaImagen();
        } catch (Exception e) {
            log.error("", e);
            log.debug("Problemas al carga el icono");
            strRutaJpg = "Mifarma.jpg";
        }
        ImageIcon imageIcono =
            new ImageIcon(FrmCtrlAsist.class.getResource("/mifarma/ptoventa/imagenes/Icono" + strRutaJpg));
        this.setIconImage(imageIcono.getImage());
    }

    private void mnuSolicitudes_GestSolicitud_actionPerformed(ActionEvent e) {
        DlgListadoSolicitud dlgListadoSolicitud=new DlgListadoSolicitud(this,"",true);
        dlgListadoSolicitud.setVisible(true);
    }
    
}
