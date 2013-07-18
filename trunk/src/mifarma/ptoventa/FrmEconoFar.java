package mifarma.ptoventa;

import com.gs.mifarma.componentes.JButtonLabel;
import mifarma.ptoventa.recepcionCiega.reference.ConstantsRecepCiega;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.BorderFactory;//  18122009
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import mifarma.common.DlgLogin;
import mifarma.common.FarmaConnection;
import mifarma.common.FarmaConstants;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;
import mifarma.ptoventa.administracion.DlgMovimientosCaja;
import mifarma.ptoventa.administracion.DlgProcesaViajero;
import mifarma.ptoventa.administracion.cajas.DlgListaCajas;
import mifarma.ptoventa.administracion.impresoras.DlgListaImpresoras;
import mifarma.ptoventa.administracion.impresoras.DlgListaImpresoraTermica;
import mifarma.ptoventa.administracion.impresoras.DlgListaIPSImpresora;
import mifarma.ptoventa.administracion.impresoras.DlgListaImpresoraTermCreaMod;
import mifarma.ptoventa.administracion.mantenimiento.DlgControlHoras;
import mifarma.ptoventa.administracion.mantenimiento.DlgParametros;
import mifarma.ptoventa.administracion.otros.DlgListaProbisa;
import mifarma.ptoventa.administracion.usuarios.DlgBuscaTrabajadorLocal;
import mifarma.ptoventa.administracion.usuarios.DlgCambioClave;
import mifarma.ptoventa.administracion.usuarios.DlgListaUsuarios;
import mifarma.ptoventa.caja.DlgAnularPedido;
import mifarma.ptoventa.caja.DlgAnularPedidoComprobante;
import mifarma.ptoventa.caja.DlgConfiguracionComprobante;
import mifarma.ptoventa.caja.DlgFormaPago;
import mifarma.ptoventa.caja.DlgMovCaja;
import mifarma.ptoventa.caja.DlgPedidosPendientesImpresion;
import mifarma.ptoventa.caja.DlgPruebaImpresora;
import mifarma.ptoventa.caja.DlgVerificacionComprobantes;
import mifarma.ptoventa.caja.DlgListaRemito;
import mifarma.ptoventa.caja.DlgControlSobres;
import mifarma.ptoventa.caja.DlgListaTicketsAnulados;
import mifarma.ptoventa.caja.DlgNuevoRemito;
import mifarma.ptoventa.caja.reference.ConstantsCaja;
import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.ce.DlgCierreCajaTurno;
import mifarma.ptoventa.ce.DlgHistoricoCierreDia;
import mifarma.ptoventa.cliente.DlgBuscaClienteJuridico;
import mifarma.ptoventa.cliente.reference.VariablesCliente;
import mifarma.ptoventa.controlingreso.DlgControlIngreso;
import mifarma.ptoventa.controlingreso.DlgHistoricoTemperatura;

import mifarma.ptoventa.inventario.DlgAjustesporFecha;
import mifarma.ptoventa.inventario.DlgGuiasIngresosRecibidas;
import mifarma.ptoventa.inventario.DlgGuiasRemision;
import mifarma.ptoventa.inventario.DlgKardex;
import mifarma.ptoventa.inventario.DlgListaPedidosEspeciales;
import mifarma.ptoventa.inventario.DlgPedidoReposicionAdicionalNuevo;
import mifarma.ptoventa.inventario.DlgPedidoReposicionRealizados;
import mifarma.ptoventa.inventario.DlgRecepcionProductosGuias;
import mifarma.ptoventa.inventario.DlgTransferenciasLocal;
import mifarma.ptoventa.inventario.DlgTransferenciasRealizadas;
import mifarma.ptoventa.inventario.transfDelivery.DlgListaTransfPendientes;
import mifarma.ptoventa.inventariociclico.DlgInicioInvCiclico;
import mifarma.ptoventa.inventariociclico.DlgListaTomasInventarioCiclico;
import mifarma.ptoventa.otros.DlgListaMedidaPresion;
import mifarma.ptoventa.recepcionCiega.DlgHistoricoRecepcion;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.reportes.DlgDetalleVentas;
import mifarma.ptoventa.reportes.DlgProdSinVtaNDias;
import mifarma.ptoventa.reportes.DlgProductoFaltaCero;
import mifarma.ptoventa.reportes.DlgProductosABC;
import mifarma.ptoventa.reportes.DlgRegistroVentas;
import mifarma.ptoventa.reportes.DlgUnidVtaLocal;
import mifarma.ptoventa.reportes.DlgVentasDiaMes;
import mifarma.ptoventa.reportes.DlgVentasPorHora;
import mifarma.ptoventa.reportes.DlgVentasPorProducto;
import mifarma.ptoventa.reportes.DlgVentasPorVendedor;
import mifarma.ptoventa.reportes.DlgVentasResumenPorDia;
import mifarma.ptoventa.reportes.reference.ConstantsReporte;
import mifarma.ptoventa.reportes.reference.VariablesReporte;
import mifarma.ptoventa.tomainventario.DlgInicioToma;
import mifarma.ptoventa.tomainventario.DlgListaHistoricoTomas;
import mifarma.ptoventa.tomainventario.DlgListaLaboratorios;
import mifarma.ptoventa.tomainventario.DlgListaTomasInventario;
import mifarma.ptoventa.ventas.DlgResumenPedido;
import mifarma.ptoventa.ventas.DlgResumenPedidoGratuito;
import mifarma.ptoventa.ventas.reference.UtilityVentas;
import mifarma.ptoventa.ventas.DlgConsultarRecargaCorrelativo;
import mifarma.ptoventa.ventas.DlgConsultarRecargaCorrelativo_AS;

import mifarma.ptoventa.ventas.DlgConsultaRecargaComprobante;
import mifarma.ptoventa.administracion.usuarios.DlgMantCarne;
import mifarma.ptoventa.caja.DlgPedidosPendientes;
import mifarma.ptoventa.caja.reference.DBCaja;
import mifarma.ptoventa.caja.reference.UtilityCaja;
import mifarma.ptoventa.administracion.fondoSencillo.reference.UtilityFondoSencillo;
import mifarma.ptoventa.administracion.fondoSencillo.DlgHistoricoFondoSencillo;
import mifarma.ptoventa.inventariodiario.DlgInicioInveDiario;

import mifarma.ptoventa.inventariodiario.DlgListaDiferenciasToma;
import mifarma.ptoventa.inventariodiario.DlgListaTomasInventarioDiario;

import mifarma.ptoventa.inventariodiario.DlgPedidoPendienteDiario;

import mifarma.ptoventa.ventas.DlgListaProdDIGEMID;
import mifarma.ptoventa.ventas.reference.VariablesVentas;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import mifarma.common.FarmaTableModel;

import mifarma.ptoventa.administracion.fondoSencillo.DlgListadoCajeros;
import mifarma.ptoventa.administracion.producto.DlgPrecioProdCambiados;
import mifarma.ptoventa.ce.reference.DBCajaElectronica;
import mifarma.ptoventa.fidelizacion.reference.UtilityFidelizacion;
import mifarma.ptoventa.recepcionCiega.DlgIngresoTransportista;
import mifarma.ptoventa.recepcionCiega.DlgListaTransportistas;
import mifarma.ptoventa.recepcionCiega.reference.UtilityRecepCiega;
import mifarma.ptoventa.inventario.reference.DBInventario;

import mifarma.ptoventa.hilos.SubProcesos;
import mifarma.ptoventa.ventas.reference.ConstantsVentas;

/**
 * Copyright (c) 2005 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : FrmEconoFar.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      27.12.2005   Creación<br>
 *         13.01.2010   Modificación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class FrmEconoFar extends JFrame  {

  //String tituloBaseFrame = "PRUEBA TEST v.5.4.1" ;
  String tituloBaseFrame=  "Punto de Venta - FarmaVenta  v1.2.7 - 27/06/2013";
 
  public int ind=0; 
    
  //ImageIcon imageIcono = new ImageIcon(FrmInkVenta.class.getResource("./images/icono.gif"));
  //ImageIcon imageLogo = new ImageIcon(FrmInkVenta.class.getResource("./images/logo.gif"));

  JLabel statusBar = new JLabel();
  JMenu mnuEconoFar_Caja = new JMenu();
  JMenuBar mnuPtoVenta = new JMenuBar();
  JPanel pnlEconoFar = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JMenu mnuEconoFar_Salir = new JMenu();
  JMenuItem mnuSalir_SalirSistema = new JMenuItem();
  JMenu mnuEconoFar_Ventas = new JMenu();
  JMenuItem mnuVentas_GenerarPedido = new JMenuItem();


 // JMenuItem mnuConfiguracionCaja_ReImprimirComprobante = new JMenuItem();
 // JMenuItem mnuVentas_CopiarPedido = new JMenuItem();

  JLabel lblLogo = new JLabel();
 // JMenu mnuCaja_Consultas = new JMenu();
  JMenuItem mnuConsultas_PedidosCobrados = new JMenuItem();
  private JMenuItem mnuCaja_CobrarPedido = new JMenuItem();
  private JMenuItem mnuCaja_AnularComprobante = new JMenuItem();
  private JMenuItem mnuCaja_AperturarCaja = new JMenuItem();
  private JMenuItem mnuCaja_CerrarCaja = new JMenuItem();
  private JMenu mnuEconoFar_Inventario = new JMenu();
  private JMenuItem mnuInventario_GuiaEntrada = new JMenuItem();
  private JMenuItem mnuInventario_Kardex = new JMenuItem();
  private JMenu mnuInventario_Transferencias = new JMenu();
  private JMenuItem mnuInventario_Transf_local = new JMenuItem();//  05.11.2008
  private JMenuItem mnuInventario_Transf_delivery = new JMenuItem();//  05.11.2008
  private JMenuItem mnuInventario_PedioReposicion = new JMenuItem();
  private JMenuItem mnuInventario_RecepcionProductos = new JMenuItem();
  private JMenu mnuCaja_MovimientosCaja = new JMenu();
  private JMenu mnuCaja_AnularVentas = new JMenu();
  private JMenuItem mnuCaja_PedidoCompleto = new JMenuItem();
  
  
  private JMenuItem mnuCaja_ConfigurarCaja = new JMenuItem();
  private JMenu mnuEconoFar_Administracion = new JMenu();
  private JMenu mnuEconoFar_TomaInventario = new JMenu();
  private JMenuItem mnuTomaInventario_Inicio = new JMenuItem();
  private JMenuItem mnuTomaInventario_Toma = new JMenuItem();
  private JMenuItem mnuTomaInventario_Historico = new JMenuItem();
  private JMenu mnuEconoFar_Reportes = new JMenu();
  private JMenuItem mnuReportes_RegistroVentas = new JMenuItem();
  //private JMenuItem mnuReportes_VentasVendedor = new JMenuItem();
  private JMenu mnuReportes_VentasVendedor = new JMenu();
    
  private JMenuItem mnuReportes_VentasVendedor_Total = new JMenuItem();//  24.11.2008
  private JMenuItem mnuReportes_VentasVendedor_Mezon = new JMenuItem();//  24.11.2008
  private JMenuItem mnuReportes_VentasVendedor_Delivery = new JMenuItem();//  24.11.2008
  private JMenuItem mnuReportes_VentasVendedor_Institucional = new JMenuItem();//  24.11.2008
  private JMenuItem mnuReportes_DetalleVentas = new JMenuItem();
  private JMenuItem mnuReportes_ResumenVentaDia = new JMenuItem();
  private JMenuItem mnuReportes_VentasProducto = new JMenuItem();
  private JMenuItem mnuReportes_VentasConvenio = new JMenuItem();
  private JMenuItem mnuReportes_VentasLinea = new JMenuItem();
  private JMenuItem mnuReportes_VentasHora = new JMenuItem();
  private JMenuItem mnuReportes_VentasDiaMes = new JMenuItem();
  private JMenuItem mnuCaja_CorreccionComprobantes = new JMenuItem();
    private JMenu mnuAdministracion_Usuarios = new JMenu();
    private JMenuItem mnuUsuarios_CambioUsuario = new JMenuItem();
    private JMenuItem mnuUsuarios_CambioClave = new JMenuItem();
    private JMenu mnuAdministracion_Mantenimiento = new JMenu();
    private JMenuItem mnuMantenimiento_Usuarios = new JMenuItem();
    private JMenuItem mnuMantenimiento_Cajas = new JMenuItem();
    private JMenuItem mnuMantenimiento_Impresoras = new JMenuItem();
  /**NUEVO!*/
  private JMenu mnuAdministracion_Otros = new JMenu();
  private JMenuItem mnuOtros_MantProbisa = new JMenuItem();
  
  private JMenuItem mnuTomaInVentario_ItemsXLab = new JMenuItem();
  private JMenu mnuAdministracion_MovCaja = new JMenu();
  private JMenuItem mnuMovCaja_RegistrarVentas = new JMenuItem();
  private JMenuItem mnuMovCaja_ConsultarMov = new JMenuItem();
  private JMenuItem mnuMantenimiento_Clientes = new JMenuItem();
  private JMenuItem mnuCaja_ReimpresionPedido = new JMenuItem();
  private JMenuItem mnuVentas_DistribucionGratuita = new JMenuItem();
  private JMenuItem mnuAdministracion_RegViajero = new JMenuItem();
  private JMenuItem mnuInventario_Guias = new JMenuItem();
  private JMenuItem mnuReportes_FaltaCero = new JMenuItem();
  private JMenuItem mnuCaja_PruebaImpresora = new JMenuItem();
  private JMenuItem mnuMantenimiento_Parametros = new JMenuItem();
  private JMenuItem mnuMantenimiento_Carne = new JMenuItem();
  private JMenuItem mnuReportes_ProductosABC = new JMenuItem();
  private JMenuItem mnuInventario_Ajustes = new JMenuItem();
  private JMenuItem mnuInventario_RecepcionLocales = new JMenuItem();
  private JMenu mnuEconofar_CajaElectronica = new JMenu();
  private JMenuItem mnuCE_CierreTurno = new JMenuItem();
  private JMenuItem mnuCE_CierreLocal = new JMenuItem();
  private JMenu mnuTomaInventario_Tradicional = new JMenu();
  private JMenuItem mnuInventario_Inicio = new JMenuItem();
  private JMenuItem mnuInventario_Toma = new JMenuItem();
  private JMenu mnuTomaInventario_Ciclico = new JMenu();
  private JMenuItem mnuInventarioCiclico_Inicio = new JMenuItem();
  private JMenuItem mnuInventarioCiclico_Toma = new JMenuItem();  
  
  // 
  private JMenu mnuTomaInventario_Diario = new JMenu();
  private JMenuItem mnuInventarioDiario_Inicio = new JMenuItem();
  private JMenuItem mnuInventarioDiario_Toma = new JMenuItem();  
  private JMenuItem mnuInventarioDiario_Diferencia = new JMenuItem();  
  
  private JMenuItem mnuAdministracion_ControlHoras = new JMenuItem();
  
  private JMenuItem mnuVentas_DeliveryAutomatico = new JMenuItem();
  private JMenuItem mnuVentas_MedidaPresion = new JMenuItem();
  private JMenu mnuVentas_Recargas = new JMenu();
  private JMenuItem mnuVentas_Correlativo = new JMenuItem();
  private JMenuItem mnuVentas_Comprobantes = new JMenuItem();


  /**NUEVO! 02/02/2007
   *  
   * */
  private JMenuItem mnuReportes_UnidVtaLocal = new JMenuItem();
  private JMenuItem mnuReportes_ProdSinVtaNDias = new JMenuItem();
  private JMenu mnuControIngreso = new JMenu();
    private JMenuItem mmuIngreso = new JMenuItem();
    private JMenuItem jMenuItem1 = new JMenuItem();


  // Variables para Pedidos De Reposicion Locales
  private JMenu mnuPedidosLocales = new JMenu();
  private JMenuItem mnuPedidoReposicion = new JMenuItem();
  private JMenuItem mnuPedidoAdicional  = new JMenuItem();
  private JMenuItem mnuPedidoEspecial   = new JMenuItem();
    private JMenuItem mmuIngresoTemperatura = new JMenuItem();
    private JMenuItem mnuMantenimiento_MaquinaIP = new JMenuItem();
    private JMenuItem jMenuItem2 = new JMenuItem();
    private JMenuItem mnuMantenimiento_ImpresoraTermica= new JMenuItem();
    private JMenuItem mnuCaja_ReimpresionTicketsAnulados = new JMenuItem();
    private JMenu mnuInventario_Mercaderia = new JMenu();
    private JMenuItem mnuInventario_Recepcion = new JMenuItem();
    private JMenuItem jMenuItem3 = new JMenuItem();

    //  11.12.09
    //private JMenu mnuPedidosLocales = new JMenu();
	private JButtonLabel btnRevertir = new JButtonLabel();
    private JTextFieldSanSerif txtRevertir = new JTextFieldSanSerif();
    private JLabel lblMensaje = new JLabel("EN PRUEBAS",JLabel.CENTER);
    private JMenuItem mnDigemid = new JMenuItem();
	private JMenuItem jMenuItem4 = new JMenuItem();
    
    private JMenuItem mnuIngTransportista = new JMenuItem();
    private JMenuItem mnuFondoSencillo = new JMenuItem();
	//private JMenu mnuCE_Prosegur = new JMenu();
    private JMenuItem mnuCaja_ControlSobresParciales = new JMenuItem();
    private JMenuItem mnuCE_Prosegur = new JMenuItem();
    private JMenuItem jMenuItem6 = new JMenuItem();
    private JMenu mnuProdCambiados = new JMenu();
    private JMenuItem mnuPrecioCambProd = new JMenuItem();

    //   ImageIcon imagen = new ImageIcon("C:/mifarma/mifarma.jpg");

    // JLabel etiqueta = new JLabel(imagen);
    //lblImagen.setIcon(imagen);

    public FrmEconoFar() {
    
    try {
        cargaVariablesBD();
      //this.setIconImage(imageIcono.getImage());
      jbInit();
      this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      initialize();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setJMenuBar(mnuPtoVenta);
    this.getContentPane().setLayout(borderLayout1);
    pnlEconoFar.setLayout(null);
    pnlEconoFar.setFont(new Font("SansSerif", 0, 11));
    pnlEconoFar.setBackground(Color.white);
    mnuEconoFar_Salir.setText("Salir");
    mnuEconoFar_Salir.setFont(new Font("SansSerif", 0, 11));
    mnuEconoFar_Salir.setMnemonic('s');
    mnuSalir_SalirSistema.setText("Salir del Sistema");
    mnuSalir_SalirSistema.setFont(new Font("SansSerif", 0, 11));
    mnuSalir_SalirSistema.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
                        mnuSalir_SalirSistema_actionPerformed(e);
                    }
      });
    mnuEconoFar_Ventas.setText("Ventas");
    mnuEconoFar_Ventas.setFont(new Font("SansSerif", 0, 11));
    mnuEconoFar_Ventas.setMnemonic('v');
    mnuVentas_GenerarPedido.setText("1. Generar Pedido");
    mnuVentas_GenerarPedido.setFont(new Font("SansSerif", 0, 11));
    mnuVentas_GenerarPedido.setMnemonic('1');
    mnuVentas_GenerarPedido.setActionCommand("Realizar Venta");
    mnuVentas_GenerarPedido.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
                        mnuVentas_GenerarPedido_actionPerformed(e);
                    }
      });
    this.setSize(new Dimension(800, 600));
//    this.setTitle("");
    this.setFont(new Font("SansSerif", 0, 11));
    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    this.addWindowListener(new WindowAdapter() {
        public void windowOpened(WindowEvent e) {
          this_windowOpened(e);
        }

        public void windowClosing(WindowEvent e)
        {
                        salir(e);
                    }

                    public void windowClosed(WindowEvent e) {
                        this_windowClosed(e);
                    }
                });
    mnuEconoFar_Caja.setText("Caja");
    mnuEconoFar_Caja.setFont(new Font("SansSerif", 0, 11));
    mnuEconoFar_Caja.setMnemonic('c');
    mnuPtoVenta.setFont(new Font("SansSerif", 0, 11));


        lblLogo.setBounds(new Rectangle(615, 395, 160, 145));
    lblLogo.setFont(new Font("SansSerif", 0, 11));
    lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
    mnuCaja_CobrarPedido.setText("3. Cobrar Pedido");
    mnuCaja_CobrarPedido.setFont(new Font("SansSerif", 0, 11));
        mnuCaja_CobrarPedido.setMnemonic('3');
    mnuCaja_CobrarPedido.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuCaja_CobrarPedido_actionPerformed(e);
        }
      });
    mnuCaja_AnularComprobante.setText("2. Anular Comprobante");
    mnuCaja_AnularComprobante.setFont(new Font("SansSerif", 0, 11));
    mnuCaja_AnularComprobante.setActionCommand("Anular Pedidos");
        mnuCaja_AnularComprobante.setMnemonic('2');
    mnuCaja_AnularComprobante.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                        mnuCaja_AnularComprobante_actionPerformed(e);
                    }
      });
    mnuCaja_AperturarCaja.setText("1. Aperturar Caja");
    mnuCaja_AperturarCaja.setFont(new Font("SansSerif", 0, 11));
        mnuCaja_AperturarCaja.setMnemonic('1');
    mnuCaja_AperturarCaja.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuCaja_AperturarCaja_actionPerformed(e);
        }
      });
    mnuCaja_CerrarCaja.setText("2. Cerrar Caja");
    mnuCaja_CerrarCaja.setFont(new Font("SansSerif", 0, 11));
        mnuCaja_CerrarCaja.setMnemonic('2');
    mnuCaja_CerrarCaja.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                        mnuCaja_CerrarCaja_actionPerformed(e);
                    }
      });
    mnuEconoFar_Inventario.setText("Inventario");
    mnuEconoFar_Inventario.setMnemonic('i');
    mnuEconoFar_Inventario.setFont(new Font("SansSerif", 0, 11));
    mnuInventario_GuiaEntrada.setText("1. Guia de Ingreso");
    mnuInventario_GuiaEntrada.setFont(new Font("SansSerif", 0, 11));
        mnuInventario_GuiaEntrada.setMnemonic('1');
    mnuInventario_GuiaEntrada.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuInventario_GuiaEntrada_actionPerformed(e);
        }
      });
    mnuInventario_Kardex.setText("2. Kardex");
    mnuInventario_Kardex.setFont(new Font("SansSerif", 0, 11));
        mnuInventario_Kardex.setMnemonic('2');
    mnuInventario_Kardex.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuInventario_Kardex_actionPerformed(e);
        }
      });
    
    mnuInventario_Transferencias.setText("3. Transferencias");
    mnuInventario_Transferencias.setFont(new Font("SansSerif", 0, 11));
    mnuInventario_Transferencias.setMnemonic('3');
    
    mnuInventario_Transf_local.setText("1. Transferencias Local");
    mnuInventario_Transf_local.setFont(new Font("SansSerif", 0, 11));
    mnuInventario_Transf_local.setMnemonic('1');
    mnuInventario_Transf_local.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              mnuInventario_Transf_local_actionPerformed(e);
            }
          });
    
    mnuInventario_Transf_delivery.setText("2. Transferencias Delivery");
    mnuInventario_Transf_delivery.setFont(new Font("SansSerif", 0, 11));
    mnuInventario_Transf_delivery.setMnemonic('2');
    mnuInventario_Transf_delivery.setEnabled(true);
    mnuInventario_Transf_delivery.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                        mnuInventario_Transf_delivery_actionPerformed(e);
                    }
      });
    
    mnuInventario_PedioReposicion.setText("5. Pedido Reposición");
    mnuInventario_PedioReposicion.setFont(new Font("SansSerif", 0, 11));
        mnuInventario_PedioReposicion.setMnemonic('5');
        mnuInventario_PedioReposicion.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    mnuInventario_PedioReposicion_actionPerformed(e);
                }
            });
        //  11.12.09 SE AGRUPA PARA RECEPCION DE MERCADERÍA
        mnuInventario_Mercaderia.setText("4. Recepcion Mercaderia");
                mnuInventario_Mercaderia.setDisplayedMnemonicIndex(0);
                mnuInventario_Mercaderia.setMnemonic('4');
                mnuInventario_Mercaderia.setFont(new Font("SansSerif", 0, 11));
                mnuInventario_Mercaderia.setFocusable(false);
                mnuInventario_Recepcion.setText("1. Recepcion Ciega ");
                mnuInventario_Recepcion.setFont(new Font("SansSerif", 0, 11));
                mnuInventario_Recepcion.setMnemonic('1');
                mnuInventario_Recepcion.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                mnuInventario_Recepcion_actionPerformed(e);
                            }
                        });
        mnuInventario_RecepcionProductos.setText("2. Recepción de Almacén");
        mnuInventario_RecepcionProductos.setFont(new Font("SansSerif", 0, 11));
            mnuInventario_RecepcionProductos.setMnemonic('2');
        mnuInventario_RecepcionProductos.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
                        jMenuItem4_actionPerformed(e);
            }
          });
    /*    
    mnuInventario_RecepcionProductos.setText("4. Recepción de Almacén");
    mnuInventario_RecepcionProductos.setFont(new Font("SansSerif", 0, 11));
        mnuInventario_RecepcionProductos.setMnemonic('4');
    mnuInventario_RecepcionProductos.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                    jMenuItem4_actionPerformed(e);
        }
      });*/
    mnuCaja_MovimientosCaja.setText("1. Movimientos de Caja");
    mnuCaja_MovimientosCaja.setFont(new Font("SansSerif", 0, 11));
        mnuCaja_MovimientosCaja.setMnemonic('1');

        
    mnuCaja_AnularVentas.setText("4. Anular Ventas");
    mnuCaja_AnularVentas.setFont(new Font("SansSerif", 0, 11));
        mnuCaja_AnularVentas.setMnemonic('4');
    mnuCaja_PedidoCompleto.setText("1. Pedido Completo");
    mnuCaja_PedidoCompleto.setFont(new Font("SansSerif", 0, 11));
        mnuCaja_PedidoCompleto.setMnemonic('1');
    mnuCaja_PedidoCompleto.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                        mnuCaja_PedidoCompleto_actionPerformed(e);
                    }
      });


/******/
 mnuPedidosLocales.setText("5. Pedidos Locales");
 mnuPedidosLocales.setFont(new Font("SansSerif", 0, 11));
 // 
 mnuPedidosLocales.setEnabled(true);
 mnuPedidosLocales.setMnemonic('4');
 mnuPedidoReposicion.setText("1. Pedido Reposición");
 mnuPedidoReposicion.setFont(new Font("SansSerif", 0, 11));
     mnuPedidoReposicion.setMnemonic('1');
 mnuPedidoReposicion.addActionListener(new ActionListener()
   {
     public void actionPerformed(ActionEvent e)
     {
                        mnuInventario_PedioReposicion_actionPerformed(e);
                    }
   });

        mnuPedidoAdicional.setText("2. PVM");
        
        mnuPedidoAdicional.setFont(new Font("SansSerif", 0, 11));
        mnuPedidoAdicional.setMnemonic('1');
        mnuPedidoAdicional.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
                        mnuPedidoAdicional_actionPerformed(e);
                    }
          });
  

        mnuPedidoEspecial.setText("3. Pedido Especial");
        
        mnuPedidoEspecial.setFont(new Font("SansSerif", 0, 11));
            mnuPedidoEspecial.setMnemonic('1');
        mnuPedidoEspecial.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
                        mnuPedidoEspecial_actionPerformed(e);
                    }
          });
        
/***********************************/

        mmuIngresoTemperatura.setText("2. Ingreso Temperatura");
        mmuIngresoTemperatura.setFont(new Font("SansSerif", 0, 11));
        mmuIngresoTemperatura.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        mmuIngresoTemperatura_actionPerformed(e);
                    }
                });
        mnuMantenimiento_MaquinaIP.setText("7. Maquina - IP");
        mnuMantenimiento_MaquinaIP.setFont(new Font("SansSerif", 0, 11));
        mnuMantenimiento_MaquinaIP.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        mnuMantenimiento_MaquinaIP_actionPerformed(e);
                    }
                });
        jMenuItem2.setText("6. Cobro Pedido Inv. Diario");
        jMenuItem2.setFont(new Font("SansSerif", 0, 11));
        jMenuItem2.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem2_actionPerformed(e);
                    }
                });
        //john 25/06/2009
        mnuMantenimiento_ImpresoraTermica.setText("8. Impresoras Térmicas");
            mnuMantenimiento_ImpresoraTermica.setFont(new Font("SansSerif", 0, 11));
            mnuMantenimiento_ImpresoraTermica.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                        mnuMantenimiento_ImpresoraTermica_actionPerformed(e);
                    }
                    });

        mnuCaja_ReimpresionTicketsAnulados.setText("8. Reimpresión de tickets anulados");
        mnuCaja_ReimpresionTicketsAnulados.setFont(new Font("SansSerif", 0, 
                                                            11));
        mnuCaja_ReimpresionTicketsAnulados.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        mnuCaja_ReimpresionTicketsAnulados_actionPerformed(e);
                    }
                });
        //  11.12.09 de 9 a 4
        /*mnuInventario_Mercaderia.setText("4. Recepcion Mercaderia");
        mnuInventario_Mercaderia.setDisplayedMnemonicIndex(0);
        mnuInventario_Mercaderia.setMnemonic('4');
        mnuInventario_Mercaderia.setFont(new Font("SansSerif", 0, 11));
        mnuInventario_Mercaderia.setFocusable(false);
        mnuInventario_Recepcion.setText(" 1. Recepcion Ciega ");
        mnuInventario_Recepcion.setFont(new Font("SansSerif", 0, 11));
        mnuInventario_Recepcion.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        mnuInventario_Recepcion_actionPerformed(e);
                    }
                });*/
        jMenuItem3.setText("jMenuItem3");
  btnRevertir.setText("Revertirx");
     //   btnRevertir.setVisible(false);
     //   btnRevertir.setBounds(new Rectangle(0, 520, 75, 15));
        btnRevertir.setMnemonic('x');
       
        btnRevertir.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnRevertir_actionPerformed(e);
                    }
                });
        txtRevertir.setBounds(new Rectangle(0, 540, 70, 10));

        lblMensaje.setText("EN PRUEBAS");
        lblMensaje.setBounds(new Rectangle(155, 20, 515, 60));
        lblMensaje.setFont(new Font("Serif", Font.BOLD, 60));
        lblMensaje.setVisible(false);
        mnDigemid.setText("7. Lista Precios Consumidor");
        mnDigemid.setFont(new Font("SansSerif", 0, 11));
        mnDigemid.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem4_DIGEMID(e);
                    }
                });
        mnuIngTransportista.setText("3. Ingreso Transportista");
        mnuIngTransportista.setMnemonic('3');
        mnuIngTransportista.setFont(new Font("SansSerif", 0, 11));
        mnuIngTransportista.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        mnuIngTransportista_actionPerformed(e);
                    }
                });
        mnuFondoSencillo.setText("7. Fondo de Sencillo");
        mnuFondoSencillo.setFont(new Font("SansSerif", 0, 11));
        mnuFondoSencillo.setMnemonic('7');        
        mnuFondoSencillo.setVisible(false);

        mnuFondoSencillo.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        mnuFondoSencillo_actionPerformed(e);
                    }
                });

        mnuCaja_ControlSobresParciales.setText("9. Control Sobres Parciales");
        mnuCaja_ControlSobresParciales.setFont(new Font("SansSerif", 0, 11));
        mnuCaja_ControlSobresParciales.setEnabled(false);
        mnuCaja_ControlSobresParciales.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        mnuCaja_ControlSobresParciales_actionPerformed(e);
                    }
                });
        mnuCE_Prosegur.setText("3. Prosegur");
        mnuCE_Prosegur.setFont(new Font("SansSerif", 0, 11));
        mnuCE_Prosegur.setEnabled(false);
        mnuCE_Prosegur.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        mnuCE_Prosegur_actionPerformed(e);
                    }
                });
        jMenuItem6.setText("jMenuItem6");
        mnuProdCambiados.setText("8. Producto");
        mnuProdCambiados.setFont(new Font("SansSerif", 0, 11));
        mnuProdCambiados.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jMenu1_actionPerformed(e);
                    }
                });
        mnuPrecioCambProd.setText("1. Precios cambiado");
        mnuPrecioCambProd.setFont(new Font("SansSerif", 0, 11));
        mnuPrecioCambProd.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        mnuPrecioCambProd_actionPerformed(e);
                    }
                });
        mnuCaja_ConfigurarCaja.setText("2. Configurar Caja");
    mnuCaja_ConfigurarCaja.setFont(new Font("SansSerif", 0, 11));
        mnuCaja_ConfigurarCaja.setMnemonic('2');
    mnuCaja_ConfigurarCaja.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuCaja_ConfigurarCaja_actionPerformed(e);
        }
      });
    mnuEconoFar_Administracion.setText("Administracion");
    mnuEconoFar_Administracion.setFont(new Font("SansSerif", 0, 11));
    mnuEconoFar_Administracion.setMnemonic('a');
    mnuEconoFar_Administracion.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuAdministracion_Mov_actionPerformed(e);
        }
      });
    mnuEconoFar_TomaInventario.setText("Toma Inventario");
    mnuEconoFar_TomaInventario.setFont(new Font("SansSerif", 0, 11));
    mnuEconoFar_TomaInventario.setMnemonic('t');
    mnuTomaInventario_Inicio.setText("1. Inicio");
    mnuTomaInventario_Inicio.setFont(new Font("SansSerif", 0, 11));
        mnuTomaInventario_Inicio.setMnemonic('1');
    mnuTomaInventario_Inicio.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuTomaInventario_Inicio_actionPerformed(e);
        }
      });
    mnuTomaInventario_Toma.setText("2. Toma");
    mnuTomaInventario_Toma.setFont(new Font("SansSerif", 0, 11));
        mnuTomaInventario_Toma.setMnemonic('2');
    mnuTomaInventario_Toma.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuTomaInventario_Toma_actionPerformed(e);
        }
      });
    mnuTomaInventario_Historico.setText("4. Historico");
    mnuTomaInventario_Historico.setFont(new Font("SansSerif", 0, 11));
        mnuTomaInventario_Historico.setMnemonic('4');
    mnuTomaInventario_Historico.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuTomaInventario_Historico_actionPerformed(e);
        }
      });


    mnuEconoFar_Reportes.setText("Reportes");
    mnuEconoFar_Reportes.setFont(new Font("SansSerif", 0, 11));
    mnuEconoFar_Reportes.setMnemonic('r');
    mnuReportes_RegistroVentas.setText("1. Registro de Ventas");
    mnuReportes_RegistroVentas.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_RegistroVentas.setMnemonic('1');
    mnuReportes_RegistroVentas.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                        jMenuItem1_actionPerformed(e);
                    }
      });
    mnuReportes_VentasVendedor.setText("2. Ventas por vendedor");
    mnuReportes_VentasVendedor.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_VentasVendedor.setMnemonic('2');
        /*
    mnuReportes_VentasVendedor.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e)
        {
          mnuReportes_VentasVendedor_actionPerformed(e);
        }
      }
  );*/    
    //--------------------------  26.11.08-----------------------
        mnuReportes_VentasVendedor_Total.setText("1. Ventas Totales");
        mnuReportes_VentasVendedor_Mezon.setText("2. Ventas Meson");
        mnuReportes_VentasVendedor_Delivery.setText("3. Ventas Delivery");
        mnuReportes_VentasVendedor_Institucional.setText("4. Ventas Institucional"); 
    
        mnuReportes_VentasVendedor_Total.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e){
                mnuReportes_VentasVendedor_Total_actionPerformed(e);
            }
            
            });
        mnuReportes_VentasVendedor_Mezon.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent e){
                mnuReportes_VentasVendedor_Mezon_actionPerformed(e);
                }  
                });
        mnuReportes_VentasVendedor_Delivery.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent e){
                mnuReportes_VentasVendedor_Delivery_actionPerformed(e);
                }

                  
                });
        mnuReportes_VentasVendedor_Institucional.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent e){
                mnuReportes_VentasVendedor_Institucional_actionPerformed(e);
                }


                   
                });
        
    
        mnuReportes_VentasVendedor_Total.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_VentasVendedor_Total.setMnemonic('1');
        
        
        mnuReportes_VentasVendedor_Mezon.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_VentasVendedor_Mezon.setMnemonic('2');
        
        
        mnuReportes_VentasVendedor_Delivery.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_VentasVendedor_Delivery.setMnemonic('3');
        
        mnuReportes_VentasVendedor_Institucional.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_VentasVendedor_Institucional.setMnemonic('4');
        
        //-----------------------------------------------------------------
   
   
   
    
    
    mnuReportes_DetalleVentas.setText("3. Detalle de ventas");
    mnuReportes_DetalleVentas.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_DetalleVentas.setMnemonic('3');
    mnuReportes_DetalleVentas.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuReportes_DetalleVentas_actionPerformed(e);
        }
      });
    mnuReportes_ResumenVentaDia.setText("4. Ventas resumen por día");
    mnuReportes_ResumenVentaDia.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_ResumenVentaDia.setMnemonic('4');
    mnuReportes_ResumenVentaDia.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuReportes_ResumenVentaDia_actionPerformed(e);
        }
      });
    mnuReportes_VentasProducto.setText("5. Ventas por producto");
    mnuReportes_VentasProducto.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_VentasProducto.setMnemonic('5');
    mnuReportes_VentasProducto.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                    mnuReportes_VentasProducto_actionPerformed(e);
        }
      });
    mnuReportes_VentasConvenio.setText("6. Ventas por convenio");
    mnuReportes_VentasConvenio.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_VentasConvenio.setMnemonic('6');
        mnuReportes_VentasConvenio.setEnabled(false);
    mnuReportes_VentasLinea.setText("7. Ventas por línea");
    mnuReportes_VentasLinea.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_VentasLinea.setMnemonic('7');
        mnuReportes_VentasLinea.setEnabled(false);
    mnuReportes_VentasHora.setText("8. Ventas por hora");
    mnuReportes_VentasHora.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_VentasHora.setMnemonic('8');
        mnuReportes_VentasHora.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    mnuReportes_VentasHora_actionPerformed(e);
                }
            });
    mnuReportes_VentasDiaMes.setText("9. Ventas por día del mes");
        mnuReportes_VentasDiaMes.setFont(new Font("SansSerif", 0, 11));
        mnuReportes_VentasDiaMes.setMnemonic('9');
    mnuReportes_VentasDiaMes.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                        mnuReportes_VentasDiaMes_actionPerformed(e);
                    }
      });
      
    mnuCaja_CorreccionComprobantes.setText("5. Corrección de comprobantes");
        mnuCaja_CorreccionComprobantes.setFont(new Font("SansSerif", 0, 11));
        mnuCaja_CorreccionComprobantes.setMnemonic('5');
    mnuCaja_CorreccionComprobantes.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuCaja_CorreccionComprobantes_actionPerformed(e);
        }
      });
    mnuTomaInVentario_ItemsXLab.setText("5. Items por laboratorio");
    mnuTomaInVentario_ItemsXLab.setFont(new Font("SansSerif", 0, 11));
    mnuTomaInVentario_ItemsXLab.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                        mnuTomaInVentario_ItemsXLab_actionPerformed(e);
                    }
      });
    mnuCaja_ReimpresionPedido.setText("6. Reimpresion de Pedido");
    mnuCaja_ReimpresionPedido.setFont(new Font("SansSerif", 0, 11));
    mnuCaja_ReimpresionPedido.setMnemonic('6');
    mnuCaja_ReimpresionPedido.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                        mnuCaja_ReimpresionPedido_actionPerformed(e);
                    }
      });
    mnuVentas_DistribucionGratuita.setText("2. Distribucion Gratuita");
    mnuVentas_DistribucionGratuita.setFont(new Font("SansSerif", 0, 11));
    mnuVentas_DistribucionGratuita.setMnemonic('2');
    mnuVentas_DistribucionGratuita.setEnabled(false);
    mnuVentas_DistribucionGratuita.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuVentas_DistribucionGratuita_actionPerformed(e);
        }
      });
    mnuInventario_Guias.setText("6. Correccion Guias");
    mnuInventario_Guias.setFont(new Font("SansSerif", 0, 11));
    mnuInventario_Guias.setMnemonic('6');
    mnuInventario_Guias.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuInventario_Guias_actionPerformed(e);
        }
      });
    mnuReportes_FaltaCero.setText("10. Productos Falta Cero");
    mnuReportes_FaltaCero.setFont(new Font("SansSerif", 0, 11));
    mnuReportes_FaltaCero.setMnemonic('0');
    mnuReportes_FaltaCero.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuReportes_FaltaCero_actionPerformed(e);
        }
      });
//    mnuAnularComprobante_CambioProducto.setEnabled(true);


    mnuCaja_PruebaImpresora.setText("7. Prueba Impresora");
    mnuCaja_PruebaImpresora.setFont(new Font("SansSerif", 0, 11));
    mnuCaja_PruebaImpresora.setMnemonic('7');
    mnuCaja_PruebaImpresora.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            mnuCaja_PruebaImpresora_actionPerformed(e);
          }
        });
    mnuReportes_ProductosABC.setText("11. Productos ABC");
    mnuReportes_ProductosABC.setFont(new Font("SansSerif", 0, 11));
    mnuReportes_ProductosABC.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuReportes_ProductosABC_actionPerformed(e);
        }
      });

    /**NUEVO REPORTE!!*/
    mnuReportes_UnidVtaLocal.setText("12. Unidad Venta Local");
    mnuReportes_UnidVtaLocal.setFont(new Font("SansSerif", 0, 11));
    mnuReportes_UnidVtaLocal.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuReportes_UnidVtaLocal_actionPerformed(e);
        }
      });

    /**NUEVO REPORTE!!*/
    mnuReportes_ProdSinVtaNDias.setText("13. Productos Sin Ventas en 90 Dias");
    mnuReportes_ProdSinVtaNDias.setFont(new Font("SansSerif", 0, 11));
    mnuReportes_ProdSinVtaNDias.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuReportes_ProdSinVtaNDias_actionPerformed(e);
        }
      });
    mnuControIngreso.setText("Control de Ingreso");
    mnuControIngreso.setMnemonic('d');
    mnuControIngreso.setFont(new Font("SansSerif", 0, 11));
    mnuControIngreso.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuControIngreso_actionPerformed(e);
        }
      });
    mmuIngreso.setText("1. Ingreso");
    mmuIngreso.setFont(new Font("SansSerif", 0, 11));
    mmuIngreso.setMnemonic('1');
    mmuIngreso.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuIngreso_actionPerformed(e);
        }
      });

        jMenuItem1.setText("jMenuItem1");
        mnuInventario_Ajustes.setText("7. Ajustes por Fecha");
    mnuInventario_Ajustes.setMnemonic('7');
    mnuInventario_Ajustes.setFont(new Font("SansSerif", 0, 11));
    mnuInventario_Ajustes.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuInventario_Ajustes_actionPerformed(e);
        }
      });
    mnuInventario_RecepcionLocales.setText("8. Recepcion Locales");
    mnuInventario_RecepcionLocales.setFont(new Font("SansSerif", 0, 11));
    mnuInventario_RecepcionLocales.setMnemonic('8');
    mnuInventario_RecepcionLocales.setEnabled(false);
    mnuInventario_RecepcionLocales.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuInventario_RecepcionLocales_actionPerformed(e);
        }
      });
    mnuEconofar_CajaElectronica.setText("Caja Electronica");
    mnuEconofar_CajaElectronica.setMnemonic('E');
    mnuEconofar_CajaElectronica.setFont(new Font("SansSerif", 0, 11));
    mnuCE_CierreTurno.setText("1. Cierre Turno");
    mnuCE_CierreTurno.setMnemonic('1');
    mnuCE_CierreTurno.setFont(new Font("SansSerif", 0, 11));
    mnuCE_CierreTurno.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                        mnuCE_CierreTurno_actionPerformed(e);
                    }
      });
    mnuCE_CierreLocal.setText("2. Cierre Local");
    mnuCE_CierreLocal.setMnemonic('2');
    mnuCE_CierreLocal.setFont(new Font("SansSerif", 0, 11));
    mnuCE_CierreLocal.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                        mnuCE_CierreLocal_actionPerformed(e);
                    }
      });
    mnuTomaInventario_Tradicional.setText("1. Tradicional");
    mnuTomaInventario_Tradicional.setFont(new Font("SansSerif", 0, 11));
    mnuTomaInventario_Tradicional.setMnemonic('1');
    mnuInventario_Inicio.setText("1.Inicio");
    mnuInventario_Inicio.setFont(new Font("SansSerif", 0, 11));
    mnuInventario_Inicio.setMnemonic('1');
    mnuInventario_Inicio.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuInventario_Inicio_actionPerformed(e);
        }
      });
    mnuInventario_Toma.setText("2. Toma");
    mnuInventario_Toma.setFont(new Font("SansSerif", 0, 11));
    mnuInventario_Toma.setMnemonic('2');
    mnuInventario_Toma.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuInventario_Toma_actionPerformed(e);
        }
      });
    mnuTomaInventario_Ciclico.setText("2. Cíclico");
    mnuTomaInventario_Ciclico.setFont(new Font("SansSerif", 0, 11));
    mnuTomaInventario_Ciclico.setMnemonic('2');
    mnuInventarioCiclico_Inicio.setText("1. Inicio");
    mnuInventarioCiclico_Inicio.setFont(new Font("SansSerif", 0, 11));
    mnuInventarioCiclico_Inicio.setMnemonic('1');
    mnuInventarioCiclico_Inicio.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuInventarioCiclico_Inicio_actionPerformed(e);
        }
      });
    mnuInventarioCiclico_Toma.setText("2. Toma");
    mnuInventarioCiclico_Toma.setFont(new Font("SansSerif", 0, 11));
    mnuInventarioCiclico_Toma.setMnemonic('2');
    mnuInventarioCiclico_Toma.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuInventarioCiclico_Toma_actionPerformed(e);
        }
      });
    
    // 
    //mnuTomaInventario_Diario.setEnabled(false);
    mnuTomaInventario_Diario.setText("3. Diario");
    mnuTomaInventario_Diario.setFont(new Font("SansSerif", 0, 11));
    mnuTomaInventario_Diario.setMnemonic('3');
    //mnuInventarioDiario_Inicio.setText("1. Inicio");
    //mnuInventarioDiario_Inicio.setFont(new Font("SansSerif", 0, 11));
    //mnuInventarioDiario_Inicio.setMnemonic('1');
    //mnuInventarioDiario_Inicio.addActionListener(new ActionListener()
    //  {
    //    public void actionPerformed(ActionEvent e)
    //    {
    //                    mnuInventarioDiario_Inicio_actionPerformed(e);
    //                }
    //  });
    mnuInventarioDiario_Toma.setText("1. Toma");
    mnuInventarioDiario_Toma.setFont(new Font("SansSerif", 0, 11));
    mnuInventarioDiario_Toma.setMnemonic('1');
    mnuInventarioDiario_Toma.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                        mnuInventarioDiario_Toma_actionPerformed(e);
                    }
      });
    mnuInventarioDiario_Diferencia.setText("2. Diferencia");
    mnuInventarioDiario_Diferencia.setFont(new Font("SansSerif", 0, 11));
    mnuInventarioDiario_Diferencia.setMnemonic('2');
    mnuInventarioDiario_Diferencia.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                        mnuInventarioDiario_Diferencia_actionPerformed(e);
                    }
      });

        
    
    
    mnuVentas_DeliveryAutomatico.setText("3. Delivery Automático");
    mnuVentas_DeliveryAutomatico.setMnemonic('3');
    mnuVentas_DeliveryAutomatico.setFont(new Font("SansSerif", 0, 11));
    mnuVentas_DeliveryAutomatico.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuVentas_DeliveryAutomatico_actionPerformed(e);
        }
      });
    
    mnuVentas_MedidaPresion.setText("4. Medida Presion");
    mnuVentas_MedidaPresion.setMnemonic('4');
    mnuVentas_MedidaPresion.setFont(new Font("SansSerif", 0, 11));
    mnuVentas_MedidaPresion.setEnabled(false);
    mnuVentas_MedidaPresion.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                        mnuVentas_MedidaPresion_actionPerformed(e);
                    }
      });
    
    
    
    mnuVentas_Recargas.setText("5. Recargas Telefónicas");
    mnuVentas_Recargas.setMnemonic('5');
    mnuVentas_Recargas.setFont(new Font("Sanserif",0,11));
    mnuVentas_Recargas.setEnabled(false);
    mnuVentas_Recargas.addActionListener(new ActionListener(){
                                                                  
       public void actionPerformed(ActionEvent e)
          {
          mnuVentas_Recargas_actionPerformed(e);
          }
    });
    
    
        mnuVentas_Correlativo.setText("1. Correlativo");
        mnuVentas_Correlativo.setMnemonic('1');
        mnuVentas_Correlativo.setFont(new Font("Sanserif",0,11));
        mnuVentas_Correlativo.setEnabled(true);
        mnuVentas_Correlativo.addActionListener(new ActionListener(){
        
        public void actionPerformed(ActionEvent e)
           {
           mnuVentas_Correlativo_actionPerformed(e);
           }
        }                                         
       );
        
        
        //SE DESHABILITO COMPROBANTE
        mnuVentas_Comprobantes.setText("2. Comprobante");
        mnuVentas_Comprobantes.setMnemonic('2');
        mnuVentas_Comprobantes.setFont(new Font("Sanserif",0,11));
        mnuVentas_Comprobantes.setEnabled(false);
        mnuVentas_Comprobantes.addActionListener(new ActionListener(){
                                                                      
           public void actionPerformed(ActionEvent e)
              {
                mnuVentas_Comprobantes_actionPerformed(e);
              }
        });
    
    mnuAdministracion_ControlHoras.setText("5. Control de Horas");
    mnuAdministracion_ControlHoras.setFont(new Font("SansSerif", 0, 11));
    mnuAdministracion_ControlHoras.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuAdministracion_ControlHoras_actionPerformed(e);
        }
      });
    mnuEconoFar_Administracion.setText("Administracion");
    mnuEconoFar_Administracion.setFont(new Font("SansSerif", 0, 11));
    mnuEconoFar_Administracion.setMnemonic('a');
    mnuEconoFar_Administracion.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuAdministracion_Mov_actionPerformed(e);
        }
      });
    mnuAdministracion_Usuarios.setText("1. Usuarios");
    mnuAdministracion_Usuarios.setFont(new Font("SansSerif", 0, 11));
    mnuAdministracion_Usuarios.setMnemonic('1');
    mnuUsuarios_CambioUsuario.setText("1. Cambiar de Usuario");
    mnuUsuarios_CambioUsuario.setFont(new Font("SansSerif", 0, 11));
    mnuUsuarios_CambioUsuario.setMnemonic('1');
    mnuUsuarios_CambioUsuario.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuUsuarios_CambioUsuario_actionPerformed(e);
        }
      });
    mnuUsuarios_CambioClave.setText("2. Modificar mi Clave");
    mnuUsuarios_CambioClave.setFont(new Font("SansSerif", 0, 11));
    mnuUsuarios_CambioClave.setMnemonic('2');
    mnuUsuarios_CambioClave.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuUsuarios_CambioClave_actionPerformed(e);
        }
      });
    mnuAdministracion_Mantenimiento.setText("2. Mantenimiento");
    mnuAdministracion_Mantenimiento.setFont(new Font("SansSerif", 0, 11));
    mnuAdministracion_Mantenimiento.setMnemonic('2');
    mnuMantenimiento_Usuarios.setText("1. Usuarios");
    mnuMantenimiento_Usuarios.setFont(new Font("SansSerif", 0, 11));
    mnuMantenimiento_Usuarios.setMnemonic('1');
    mnuMantenimiento_Usuarios.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuMantenimiento_Usuarios_actionPerformed(e);
        }
      });
    mnuMantenimiento_Cajas.setText("2. Cajas");
    mnuMantenimiento_Cajas.setFont(new Font("SansSerif", 0, 11));
    mnuMantenimiento_Cajas.setMnemonic('2');
    mnuMantenimiento_Cajas.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuMantenimiento_Cajas_actionPerformed(e);
        }
      });
    mnuMantenimiento_Impresoras.setText("3. Impresoras");
    mnuMantenimiento_Impresoras.setFont(new Font("SansSerif", 0, 11));
    mnuMantenimiento_Impresoras.setMnemonic('3');
    mnuMantenimiento_Impresoras.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuMantenimiento_Impresoras_actionPerformed(e);
        }
      });
    mnuMantenimiento_Clientes.setText("4. Clientes");
    mnuMantenimiento_Clientes.setFont(new Font("SansSerif", 0, 11));
    mnuMantenimiento_Clientes.setMnemonic('4');
    mnuMantenimiento_Clientes.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuMantenimiento_Clientes_actionPerformed(e);
        }
      });
    mnuMantenimiento_Parametros.setText("5. Parametros");
    mnuMantenimiento_Parametros.setFont(new Font("SansSerif", 0, 11));
    mnuMantenimiento_Parametros.setMnemonic('5');
    mnuMantenimiento_Parametros.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuMantenimiento_Parametros_actionPerformed(e);
        }
      });
    
    
        mnuMantenimiento_Carne.setText("6. Carné Sanidad");
        mnuMantenimiento_Carne.setFont(new Font("SansSerif", 0, 11));
        mnuMantenimiento_Carne.setMnemonic('6');
        mnuMantenimiento_Carne.setEnabled(false);
        mnuMantenimiento_Carne.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
                        mnuMantenimiento_Carne_actionPerformed(e);
                    }
          });
        
    
    mnuAdministracion_MovCaja.setText("3. Movimientos Caja");
    mnuAdministracion_MovCaja.setFont(new Font("SansSerif", 0, 11));
    mnuAdministracion_MovCaja.setMnemonic('3');
    mnuAdministracion_MovCaja.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jMenu1_actionPerformed(e);
        }
      });
    mnuMovCaja_RegistrarVentas.setText("Registrar Ventas");
    mnuMovCaja_RegistrarVentas.setFont(new Font("SansSerif", 0, 11));
    mnuMovCaja_RegistrarVentas.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuMovCaja_RegistrarVentas_actionPerformed(e);
        }
      });
    mnuMovCaja_ConsultarMov.setText("Consultar Movimientos");
    mnuMovCaja_ConsultarMov.setFont(new Font("SansSerif", 0, 11));
    mnuMovCaja_ConsultarMov.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuMovCaja_ConsultarMov_actionPerformed(e);
        }
      });
    mnuAdministracion_RegViajero.setText("4. Regenerar Viajero");
    mnuAdministracion_RegViajero.setFont(new Font("SansSerif", 0, 11));
    mnuAdministracion_RegViajero.setMnemonic('4');
    mnuAdministracion_RegViajero.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          mnuAdministracion_RegViajero_actionPerformed(e);
        }
      });

    mnuAdministracion_Otros.setText("6. Otros");
    mnuAdministracion_Otros.setFont(new Font("SansSerif", 0, 11));
    mnuAdministracion_Otros.setMnemonic('5');
    
    mnuOtros_MantProbisa.setText("1. Mant. PROBISA");
    mnuOtros_MantProbisa.setFont(new Font("SansSerif", 0, 11));
    mnuOtros_MantProbisa.setMnemonic('1');
    mnuOtros_MantProbisa.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        mnuOtros_MantProbisa_actionPerformed(e);
      }
    });
      
    statusBar.setText("");
   // mnuCaja_ConfiguracionCaja.add(mnuConfiguracionCaja_ReImprimirComprobante);
 // mnuCaja_Consultas.add(mnuConsultas_PedidosCobrados);
 // mnuInkVenta_Caja.add(mnuCaja_Consultas);
        mnuEconofar_CajaElectronica.add(mnuCE_CierreTurno);
    mnuEconofar_CajaElectronica.add(mnuCE_CierreLocal);
        mnuEconofar_CajaElectronica.add(mnuCE_Prosegur);
        mnuEconofar_CajaElectronica.add(mnuCE_Prosegur);
        mnuEconofar_CajaElectronica.add(mnuCE_Prosegur);
        mnuCaja_MovimientosCaja.add(mnuCaja_AperturarCaja);
        mnuCaja_MovimientosCaja.add(mnuCaja_CerrarCaja);
        mnuEconoFar_Caja.add(mnuCaja_MovimientosCaja);
        mnuEconoFar_Caja.add(mnuCaja_ConfigurarCaja);
        mnuEconoFar_Caja.add(mnuCaja_CobrarPedido);
        mnuCaja_AnularVentas.add(mnuCaja_PedidoCompleto);
        mnuCaja_AnularVentas.add(mnuCaja_AnularComprobante);
        mnuEconoFar_Caja.add(mnuCaja_AnularVentas);
        mnuEconoFar_Caja.add(mnuCaja_CorreccionComprobantes);
        mnuEconoFar_Caja.add(mnuCaja_ReimpresionPedido);

        mnuEconoFar_Caja.add(mnuCaja_PruebaImpresora);
        mnuEconoFar_Caja.add(mnuCaja_ReimpresionTicketsAnulados);
        mnuEconoFar_Caja.add(mnuCaja_ControlSobresParciales);
        mnuPtoVenta.add(mnuEconoFar_Ventas);
        mnuPtoVenta.add(mnuEconoFar_Caja);
        mnuEconoFar_Ventas.add(mnuVentas_GenerarPedido);
        mnuEconoFar_Ventas.add(mnuVentas_DistribucionGratuita);
        mnuEconoFar_Ventas.add(mnuVentas_DeliveryAutomatico);
        mnuEconoFar_Ventas.add(mnuVentas_MedidaPresion);
        mnuEconoFar_Ventas.add(mnuVentas_Recargas);

        //-- 
        mnuEconoFar_Ventas.add(jMenuItem2);
        mnuEconoFar_Ventas.add(mnDigemid);
        mnuVentas_Recargas.add(mnuVentas_Correlativo);
        mnuVentas_Recargas.add(mnuVentas_Comprobantes);


        // mnuInkVenta_Ventas.add(mnuVentas_CopiarPedido);
        mnuEconoFar_Inventario.add(mnuInventario_GuiaEntrada);
        mnuEconoFar_Inventario.add(mnuInventario_Kardex);

        mnuEconoFar_Inventario.add(mnuInventario_Transferencias);
        mnuEconoFar_Inventario.add(mnuInventario_Mercaderia);
        mnuInventario_Transferencias.add(mnuInventario_Transf_local);
        mnuInventario_Transferencias.add(mnuInventario_Transf_delivery);

        //mnuEconoFar_Inventario.add(mnuInventario_RecepcionProductos);
        //cambiar
        // mnuEconoFar_Inventario.add(mnuInventario_PedioReposicion);
        mnuEconoFar_Inventario.add(mnuPedidosLocales);
        mnuPedidosLocales.add(mnuPedidoReposicion);
        mnuPedidosLocales.add(mnuPedidoAdicional);
        mnuPedidosLocales.add(mnuPedidoEspecial);

        mnuEconoFar_Inventario.add(mnuInventario_Guias);
        mnuEconoFar_Inventario.add(mnuInventario_Ajustes);
        mnuEconoFar_Inventario.add(mnuInventario_RecepcionLocales);
        mnuInventario_Mercaderia.add(mnuInventario_Recepcion);
        mnuInventario_Mercaderia.add(mnuInventario_RecepcionProductos);
        mnuInventario_Mercaderia.add(mnuIngTransportista);
        mnuPtoVenta.add(mnuEconoFar_Inventario);
        mnuTomaInventario_Tradicional.add(mnuInventario_Inicio);
        mnuTomaInventario_Tradicional.add(mnuInventario_Toma);
        mnuEconoFar_TomaInventario.add(mnuTomaInventario_Tradicional);
        mnuTomaInventario_Ciclico.add(mnuInventarioCiclico_Inicio);
        mnuTomaInventario_Ciclico.add(mnuInventarioCiclico_Toma);
        mnuEconoFar_TomaInventario.add(mnuTomaInventario_Ciclico);

        // 
        //mnuTomaInventario_Diario.add(mnuInventarioDiario_Inicio);
        mnuTomaInventario_Diario.add(mnuInventarioDiario_Toma);
        mnuTomaInventario_Diario.add(mnuInventarioDiario_Diferencia);
        mnuEconoFar_TomaInventario.add(mnuTomaInventario_Diario);


        mnuEconoFar_TomaInventario.add(mnuTomaInventario_Historico);
        mnuEconoFar_TomaInventario.add(mnuTomaInVentario_ItemsXLab);
        mnuPtoVenta.add(mnuEconoFar_TomaInventario);
        mnuAdministracion_Usuarios.add(mnuUsuarios_CambioUsuario);
        mnuAdministracion_Usuarios.add(mnuUsuarios_CambioClave);
        mnuEconoFar_Administracion.add(mnuAdministracion_Usuarios);
        mnuAdministracion_Mantenimiento.add(mnuMantenimiento_Usuarios);
        mnuAdministracion_Mantenimiento.add(mnuMantenimiento_Cajas);
        mnuAdministracion_Mantenimiento.add(mnuMantenimiento_Impresoras);
        mnuAdministracion_Mantenimiento.add(mnuMantenimiento_Clientes);
        mnuAdministracion_Mantenimiento.add(mnuMantenimiento_Parametros);
        mnuAdministracion_Mantenimiento.add(mnuMantenimiento_Carne);
        mnuAdministracion_Mantenimiento.add(mnuMantenimiento_MaquinaIP);
        mnuAdministracion_Mantenimiento.add(mnuMantenimiento_ImpresoraTermica);
        mnuEconoFar_Administracion.add(mnuAdministracion_Mantenimiento);
        mnuAdministracion_MovCaja.add(mnuMovCaja_RegistrarVentas);
        mnuAdministracion_MovCaja.add(mnuMovCaja_ConsultarMov);
        mnuEconoFar_Administracion.add(mnuAdministracion_MovCaja);
        mnuEconoFar_Administracion.add(mnuAdministracion_RegViajero);
        mnuEconoFar_Administracion.add(mnuAdministracion_ControlHoras);
        mnuEconoFar_Administracion.add(mnuAdministracion_Otros);
        mnuEconoFar_Administracion.add(mnuFondoSencillo);
        //jMenu1.add(jMenuItem5);
        mnuProdCambiados.add(mnuPrecioCambProd);
        mnuEconoFar_Administracion.add(mnuProdCambiados);
        mnuAdministracion_Otros.add(mnuOtros_MantProbisa);

        //  26.11.08

        mnuReportes_VentasVendedor.add(mnuReportes_VentasVendedor_Total);
        mnuReportes_VentasVendedor.add(mnuReportes_VentasVendedor_Mezon);
        mnuReportes_VentasVendedor.add(mnuReportes_VentasVendedor_Delivery);
        mnuReportes_VentasVendedor.add(mnuReportes_VentasVendedor_Institucional);


        mnuPtoVenta.add(mnuEconoFar_Administracion);
        mnuEconoFar_Reportes.add(mnuReportes_RegistroVentas);
        mnuEconoFar_Reportes.add(mnuReportes_VentasVendedor);
        mnuEconoFar_Reportes.add(mnuReportes_DetalleVentas);
        mnuEconoFar_Reportes.add(mnuReportes_ResumenVentaDia);
        mnuEconoFar_Reportes.add(mnuReportes_VentasProducto);
        mnuEconoFar_Reportes.add(mnuReportes_VentasConvenio);
        mnuEconoFar_Reportes.add(mnuReportes_VentasLinea);
        mnuEconoFar_Reportes.add(mnuReportes_VentasHora);
        mnuEconoFar_Reportes.add(mnuReportes_VentasDiaMes);
        mnuEconoFar_Reportes.add(mnuReportes_FaltaCero);
        mnuEconoFar_Reportes.add(mnuReportes_ProductosABC);
        mnuEconoFar_Reportes.add(mnuReportes_UnidVtaLocal);
        mnuEconoFar_Reportes.add(mnuReportes_ProdSinVtaNDias);
        mnuPtoVenta.add(mnuEconoFar_Reportes);
        mnuPtoVenta.add(mnuEconofar_CajaElectronica);
        mnuControIngreso.add(mmuIngreso);
        mnuControIngreso.add(mmuIngresoTemperatura);
        mnuPtoVenta.add(mnuControIngreso);

        mnuEconoFar_Salir.add(mnuSalir_SalirSistema);
        mnuPtoVenta.add(mnuEconoFar_Salir);

        this.getContentPane().add(statusBar, BorderLayout.SOUTH);
        pnlEconoFar.add(lblMensaje, null);
        pnlEconoFar.add(txtRevertir, null);
        pnlEconoFar.add(btnRevertir, null);
        pnlEconoFar.add(lblLogo, null);
        this.getContentPane().add(pnlEconoFar, BorderLayout.CENTER);


    }

  private void initialize()
  {
      mnuCaja_ControlSobresParciales.setEnabled(false); // , 07.06.2010
      eliminaCodBarra();    /*JSANTIVANEZ 01.09.2010*/    
      eliminaBoletaTxt();
      
    muestraLogin();
    muestraUser();
    inicializa();
    //  19.01.10 Obtiene direccion Fiscal Matriz
    cargaDireccionFiscal();  
      obtieneIndDireMat();
      
      
    //panelhtml.setText("<html><body><table border=\"1\"><tr align=\"center\"> <img src=file://///10.11.1.238/MiFarma/mifarma.jpg width=\"300\" height=\"90\"></tr><tr>asa</tr></table></body></html>");    
      
  }

  void muestraLogin() {
      
    //if ( readFileProperties() ) 
    if(true)
    {
      /*
      if(!validaNamePc()){
        FarmaUtility.showMessage(this,"Error al obtener el Nombre de la PC.", null);
        salirSistema();
      }
      try{
        int cantSesiones = DBPtoVenta.obtieneCantidadSesiones(FarmaVariables.vNamePc, FarmaVariables.vUsuarioBD);
        if(cantSesiones > 1){
          FarmaUtility.showMessage(this,"Ya existe una aplicacion iniciada en esta PC.\nPor favor comunicarse con el area de Sistemas", null);
          salirSistema();
        }
      } catch(SQLException sql)
      {
        FarmaUtility.showMessage(this,"Error al obtener cantidad de Sesiones Abiertas.\nPor favor comunicarse con el area de Sistemas", null);
        salirSistema();
      }
      */
      obtieneInfoLocal();
      //obtener info del local
      if(!validaIpPc()){
        FarmaUtility.showMessage(this,"Error al obtener la IP de la PC.", null);
        salirSistema();
      }
    DlgLogin dlgLogin = new DlgLogin(this,ConstantsPtoVenta.MENSAJE_LOGIN,true);
    dlgLogin.setVisible(true);
      if (!FarmaVariables.vAceptar) {
        FarmaConnection.closeConnection();
        salirSistema();
      } else {
          
          //inicio 
          //lapaz   17.09.2010
          VariablesVentas.tableModelListaGlobalProductos = new FarmaTableModel(ConstantsVentas.columnsListaProductos,ConstantsVentas.defaultValuesListaProductos,0);
          
          System.out.println("Inicio de Hilo");
          // crear y nombrar a cada subproceso
                   
          SubProcesos subproceso1 = new SubProcesos("GET_PROD_VENTA" );
          SubProcesos subproceso2 = new SubProcesos("GET_PROD_ESPECIALES" );
          SubProcesos subproceso3 = new SubProcesos("CARGA_IMP_TERMICA" );
          System.err.println( "Iniciando subprocesos" );
            
          subproceso1.start();
          subproceso2.start();
          subproceso3.start();
            
          System.out.println("Fin de Hilo");
          //fin
          //lapaz   17.09.2010
          
          //  04.09.09 Se valida cambio de clave para el usuario
          String  valida="";
          try{ valida=DBPtoVenta.validaCambioClave(); }catch(SQLException x){ x.printStackTrace();}

          System.out.println("cambiar password :"+valida);
         if(valida.trim().equalsIgnoreCase("S")){
         
            FarmaUtility.showMessage(this,"Usted debera cambiar su clave para poder entrar el sistema.", null);
            DlgCambioClave dlgcambio= new   DlgCambioClave(this,"",true);
            dlgcambio.setVisible(true);
            
            if(FarmaVariables.vAceptar) {
                FarmaVariables.dlgLogin = dlgLogin;
                recuperaStock();
            }else{
                salirSistema();
            }
            
         } else{
             FarmaVariables.dlgLogin = dlgLogin;
             recuperaStock();
         }
       
      }
    } else salirSistema();
  }
  
  private void recuperaStock (){
  
      try {
        // RECUPERANDO STOCK COMPROMETIDO
        DBPtoVenta.ejecutaRespaldoStock("","",ConstantsPtoVenta.TIP_OPERACION_RESPALDO_EJECUTAR,0,0,"");
        System.out.println("RECUPERO STOCK COMPROMETIDO DESDE RESPALDO");
        FarmaUtility.aceptarTransaccion();
        verificaRolUsuario();
      } catch (SQLException sqlException) {
        FarmaUtility.liberarTransaccion();
        sqlException.printStackTrace();
        FarmaUtility.showMessage(this,"Error al recuperar Stock de Respaldo.\nPonganse en contacto con el area de Sistemas", null);
        salirSistema();
      }
  
  }

  private boolean validaNamePc(){
    FarmaVariables.vNamePc = FarmaUtility.getHostName();
    if(FarmaVariables.vNamePc.trim().length() == 0)
      return false;
    return true;
  }

  private boolean validaIpPc(){
    FarmaVariables.vIpPc = FarmaUtility.getHostAddress();
    if(FarmaVariables.vIpPc.trim().length() == 0)
      return false;
    return true;
  }

	private void muestraUser() {

		String addon = " Usu.Actual : ";
		addon = addon + FarmaVariables.vIdUsu;
		this.setTitle(tituloBaseFrame + " - Local : " + FarmaVariables.vDescCortaLocal + " / " + addon + " /  IP : " + FarmaVariables.vIpPc);
	}

	private void obtieneInfoLocal() {
		try {
			ArrayList infoLocal = DBPtoVenta.obtieneDatosLocal();
			FarmaVariables.vDescCortaLocal = ((String) ((ArrayList) infoLocal.get(0)).get(0)).trim();
			FarmaVariables.vDescLocal = ((String) ((ArrayList) infoLocal.get(0)).get(1)).trim();
			FarmaVariables.vTipLocal = ((String) ((ArrayList) infoLocal.get(0)).get(2)).trim();
			FarmaVariables.vTipCaja = ((String) ((ArrayList) infoLocal.get(0)).get(3)).trim();
      System.out.println("FarmaVariables.vTipCaja : " + FarmaVariables.vTipCaja);
			FarmaVariables.vNomCia = ((String) ((ArrayList) infoLocal.get(0)).get(4)).trim();
			FarmaVariables.vNuRucCia = ((String) ((ArrayList) infoLocal.get(0)).get(5)).trim();
      FarmaVariables.vMinutosPedidosPendientes = ((String) ((ArrayList) infoLocal.get(0)).get(6)).trim();
      FarmaVariables.vImprReporte = ((String) ((ArrayList) infoLocal.get(0)).get(7)).trim();
      FarmaVariables.vIndHabilitado = ((String) ((ArrayList) infoLocal.get(0)).get(8)).trim();
		    FarmaVariables.vDescCortaDirLocal = ((String) ((ArrayList) infoLocal.get(0)).get(9)).trim();
      System.out.println("FarmaVariables.vMinutosPedidosPendientes : " + FarmaVariables.vMinutosPedidosPendientes);
		} catch (SQLException sqlException) {

			// FarmaUtility.showMessage(this, "Error al obtener información del
			// Local / Compania RRHH - " + sqlException.getErrorCode() + ".",
			// obj);
			// FarmaUtility.showMessage(this, sqlException.getMessage() + ".",
			// obj);
			sqlException.printStackTrace();
		}
	}

  private void mnuSalir_SalirSistema_actionPerformed(ActionEvent e) {
    salirSistema();
  }


  private void mnuVentas_GenerarPedido_actionPerformed(ActionEvent e) {
    generarPedido();
  }
  
    

  private void generarPedido() {
    DlgResumenPedido dlgResumenPedido = new DlgResumenPedido(this,"",true);
    dlgResumenPedido.setVisible(true);
    dlgResumenPedido = null;
    while(FarmaVariables.vAceptar) {
        if(VariablesVentas.vIndPrecioCabeCliente.equalsIgnoreCase("S")){    //Inicio:   03.02.2010
            VariablesVentas.vIndPrecioCabeCliente="N";
            //System.out.println("REWREWREWRWEREWREWREW");
            ind=1;
            break;
        }else{
            if(ind!=1){
                System.out.println("Go Menu");
                generarPedido();
            }else{
                ind=0;
                break;        
            }
        }                                                                 //Fin:   03.02.2010
    }
    if(!FarmaVariables.vAceptar) 
         //if (VariablesCaja.vVerificaCajero)//si es true pedira no validar al administrador
              verificaRolUsuario_sinAdmin();
         //else 
           //   verificaRolUsuario();
  }


  private void mnuCaja_CobrarPedido_actionPerformed(ActionEvent e)
  {
    DlgFormaPago dlgFormaPago = new DlgFormaPago(this,"",true);
    dlgFormaPago.setVisible(true);
    if(!FarmaVariables.vAceptar) verificaRolUsuario();
  }

  private void mnuCaja_AnularComprobante_actionPerformed(ActionEvent e)
  {
    DlgAnularPedidoComprobante dlgAnularPedidoComprobante = new DlgAnularPedidoComprobante(this,"",true);
    dlgAnularPedidoComprobante.setVisible(true);
  }

 private void mnuCaja_AperturarCaja_actionPerformed(ActionEvent e)
  {
		VariablesCaja.vTipMovCaja = ConstantsCaja.MOVIMIENTO_APERTURA;
		DlgMovCaja dlgMovCaja = new DlgMovCaja(this, "", true);
		try {
			dlgMovCaja.validarParamsUser();
      dlgMovCaja.verificaAperturaCaja();
			dlgMovCaja.setVisible(true);
		} catch (SQLException ex) {
      String mensaje="";
      if(ex.getErrorCode()==20011) mensaje="El usuario no posee ninguna caja activa asociada.";
      else if(ex.getErrorCode()==20012) mensaje="La caja del usuario ya se encuentra aperturada.";
      else if(ex.getErrorCode()==20013) mensaje="La caja del usuario ya se encuentra cerrada.";
      else if(ex.getErrorCode()==20020) mensaje="No puede aperturar una caja cuando ya existe una fecha de \n"+
                                                "cierre de dia de venta registrada para el dia de hoy.";
      else mensaje=ex.getMessage();

			Object obj = new Object();
			obj = null;
			FarmaUtility.showMessage(dlgMovCaja,mensaje, obj);
			dlgMovCaja.dispose();
		}
  }

  private void mnuCaja_CerrarCaja_actionPerformed(ActionEvent e)
  {
		VariablesCaja.vTipMovCaja = ConstantsCaja.MOVIMIENTO_CIERRE;
		DlgMovCaja dlgMovCaja = new DlgMovCaja(this, "", true);
		try {
			dlgMovCaja.validarParamsUser();
			dlgMovCaja.setVisible(true);
		} catch (SQLException ex) {
		 String mensaje="";

        if(ex.getErrorCode()==20011){mensaje="El usuario no posee ninguna caja activa asociada.";}
        else if(ex.getErrorCode()==20012){mensaje="La caja del usuario ya se encuentra aperturada.";}
        else if(ex.getErrorCode()==20013){mensaje="La caja del usuario ya se encuentra cerrada.";}
        else {mensaje=ex.getMessage();}

			Object obj = new Object();
			obj = null;
			FarmaUtility.showMessage(dlgMovCaja, mensaje, obj);
			dlgMovCaja.dispose();
		}
  }

  private void mnuInventario_GuiaEntrada_actionPerformed(ActionEvent e)
  {
    DlgGuiasIngresosRecibidas dlgGuiasIngresosRecibidas = new DlgGuiasIngresosRecibidas(this,"",true);
    dlgGuiasIngresosRecibidas.setVisible(true);
  }


  private void mnuInventario_Transf_local_actionPerformed(ActionEvent e)
  {
    DlgTransferenciasRealizadas dlgTransferenciasRealizadas = new DlgTransferenciasRealizadas(this,"",true);
    dlgTransferenciasRealizadas.setVisible(true);
  }
  
    //  05.11.2008
    private void mnuInventario_Transf_delivery_actionPerformed(ActionEvent e)
    {
      DlgListaTransfPendientes dlgListaTranfPendientes = new DlgListaTransfPendientes(this,"",true);
      dlgListaTranfPendientes.setVisible(true);
    }

    private void mnuInventario_PedioReposicion_actionPerformed(ActionEvent e)
    {
        DlgPedidoReposicionRealizados dlgPedidoReposicionRealizados = new DlgPedidoReposicionRealizados(this,"",true);
        dlgPedidoReposicionRealizados.setVisible(true);
    }

  private void jMenuItem4_actionPerformed(ActionEvent e)
  {        
          DlgRecepcionProductosGuias dlgRecepcionProductosGuias = new DlgRecepcionProductosGuias(this,"",true);
          dlgRecepcionProductosGuias.setVisible(true);      
   
  }


  private void jMenuItem4_DIGEMID(ActionEvent e)
  {        

      DlgListaProdDIGEMID objDIGEMID=new DlgListaProdDIGEMID(this,"",true); //  04.02.2010
      objDIGEMID.setVisible(true);
  }
  private void mnuCaja_ConfigurarCaja_actionPerformed(ActionEvent e)
  {
    int indIpValida=0;
    try
    {
      indIpValida = DBPtoVenta.verificaIPValida();
      if( indIpValida == 0 )
        FarmaUtility.showMessage(this,"La estación actual no se encuentra autorizada para efectuar la operación. ", null);
      else{
        DlgConfiguracionComprobante dlgConfiguracionComprobante = new DlgConfiguracionComprobante(this, "", true);
        dlgConfiguracionComprobante.setVisible(true);
        FarmaVariables.vAceptar = false;
      }
    } catch(SQLException ex)
    {
      ex.printStackTrace();
      FarmaUtility.showMessage(this,"Error al validar IP de Configuracion de Comprobantes.\n" + ex, null);
      indIpValida=0;
    }
  }

  private void mnuCaja_PedidoCompleto_actionPerformed(ActionEvent e)
  {
    DlgAnularPedido dlgAnularPedido = new DlgAnularPedido(this,"",true);
    dlgAnularPedido.setVisible(true);
  }

  private void mnuAdministracion_Mov_actionPerformed(ActionEvent e)
  {
    DlgMovimientosCaja dlgMovimientosCaja = new DlgMovimientosCaja(this,"",true);
    dlgMovimientosCaja.setVisible(true);
  }

  private void mnuTomaInventario_Inicio_actionPerformed(ActionEvent e)
  {
    DlgInicioToma dlgInicioToma = new DlgInicioToma(this, "", true);
    dlgInicioToma.setVisible(true);
  }

  private void mnuTomaInventario_Toma_actionPerformed(ActionEvent e)
  {
    DlgListaTomasInventario dlgListaTomasInventario = new DlgListaTomasInventario(this, "", true);
    dlgListaTomasInventario.setVisible(true);
  }

  private void mnuTomaInventario_Historico_actionPerformed(ActionEvent e)
  {
    DlgListaHistoricoTomas dlgListaHistoricoTomas = new DlgListaHistoricoTomas(this, "", true);
    dlgListaHistoricoTomas.setVisible(true);
  }

  private void mnuReportes_RegistroVentas_actionPerformed(ActionEvent e)
  {
    DlgRegistroVentas dlgRegistroVentas = new DlgRegistroVentas(this, "", true);
    dlgRegistroVentas.setVisible(true);
  }

  private void mnuInventario_Kardex_actionPerformed(ActionEvent e)
  {
    DlgKardex dlgKardex = new DlgKardex(this,"",true);
    dlgKardex.setVisible(true);
  }

  private void jMenuItem1_actionPerformed(ActionEvent e)
  {
    DlgRegistroVentas dlgRegistroVentas = new DlgRegistroVentas(this, "", true);
    dlgRegistroVentas.setVisible(true);
  }

  private void this_windowOpened(WindowEvent e)
  {
    DlgProcesar dlgProcesar = new DlgProcesar(this, "", true);
    dlgProcesar.setVisible(true);
    if(FarmaVariables.vAceptar){
    /**
     * Colocara el valor de Dias de Base de Datos
     * @author :  
     * @since  : 21.08.2007
     */
    mnuReportes_ProdSinVtaNDias.setText("13. Productos Sin Ventas en "+
                                         VariablesPtoVenta.vNumeroDiasSinVentas.trim()
                                         + " Dias"); 
    }                                         
    
  }

    private void mnuUsuarios_CambioClave_actionPerformed(ActionEvent e)
    {
		DlgCambioClave dlgCambioClave = new DlgCambioClave(this, "", true);
		dlgCambioClave.setVisible(true);
    }

    private void mnuUsuarios_CambioUsuario_actionPerformed(ActionEvent e)
    {
		muestraLoginCambioUser();
		//verificaRolUsuario();
    this.repaint();
    }

    private void mnuMantenimiento_Usuarios_actionPerformed(ActionEvent e)
    {
                DlgListaUsuarios dlgListaUsuarios = new DlgListaUsuarios(this, "", true);
		dlgListaUsuarios.setVisible(true);
    }

    private void mnuMantenimiento_Cajas_actionPerformed(ActionEvent e)
    {
		DlgListaCajas dlgListaCajas = new DlgListaCajas(this, "", true);
		dlgListaCajas.setVisible(true);
    }

    private void mnuMantenimiento_Impresoras_actionPerformed(ActionEvent e)
    {
		DlgListaImpresoras dlgListaImpresoras = new DlgListaImpresoras(this,"", true);
		dlgListaImpresoras.setVisible(true);
    }

  private void mnuTomaInVentario_ItemsXLab_actionPerformed(ActionEvent e)
  { 
    DlgListaLaboratorios  dlgListaLaboratorios = new DlgListaLaboratorios(this,"", true);
		dlgListaLaboratorios.setVisible(true);
  }

  private void jMenu1_actionPerformed(ActionEvent e)
  {
  }

  private void mnuMovCaja_RegistrarVentas_actionPerformed(ActionEvent e)
  {
    VariablesPtoVenta.vTipOpMovCaja=ConstantsPtoVenta.TIP_OPERACION_MOV_CAJA_REGISTRAR_VENTA;
    DlgMovimientosCaja  dlgMovimientosCaja = new DlgMovimientosCaja(this,"", true);
		dlgMovimientosCaja.setVisible(true);
  }

  private void mnuMovCaja_ConsultarMov_actionPerformed(ActionEvent e)
  {
    VariablesPtoVenta.vTipOpMovCaja=ConstantsPtoVenta.TIP_OPERACION_MOV_CAJA_CONSULTA;
    DlgMovimientosCaja  dlgMovimientosCaja = new DlgMovimientosCaja(this,"", true);
		dlgMovimientosCaja.setVisible(true);
  }

  private void mnuCaja_CorreccionComprobantes_actionPerformed(ActionEvent e)
  {
      DlgVerificacionComprobantes  dlgVerificacionComprobantes = new DlgVerificacionComprobantes(this,"", true);
		dlgVerificacionComprobantes.setVisible(true);
  }

  private void mnuMantenimiento_Clientes_actionPerformed(ActionEvent e)
  {
  DlgBuscaClienteJuridico dlgBuscaClienteJuridico = new DlgBuscaClienteJuridico (this,"",true);
  VariablesCliente.vIndicadorCargaCliente = FarmaConstants.INDICADOR_N;
  dlgBuscaClienteJuridico.setVisible(true);
  }

  private void verificaRolUsuario() {
    muestraUser();
    validaOpcionesMenu(false);
    // Opciones comunes para todos los Roles
    mnuEconoFar_Administracion.setEnabled(true);
    mnuAdministracion_Usuarios.setEnabled(true);
    mnuUsuarios_CambioUsuario.setEnabled(true);
    mnuUsuarios_CambioClave.setEnabled(true);
     mnuIngTransportista.setEnabled(false);
    // 
    mnuVentas_MedidaPresion.setEnabled(false);
    mnuFondoSencillo.setVisible(false);
    
    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_VENDEDOR) ) {
        mnuEconoFar_Ventas.setEnabled(true);
        mnuVentas_GenerarPedido.setEnabled(true);
        // 
        mnuVentas_Recargas.setEnabled(true);
        mnuVentas_DeliveryAutomatico.setEnabled(true);
        
        
        mnuAdministracion_ControlHoras.setEnabled(false);
        
        // 
        mnuPedidosLocales.setEnabled(false);
        if(FarmaVariables.vEconoFar_Matriz){
            mnuPedidoAdicional.setVisible(false);
            mnuPedidoEspecial.setVisible(false);
        }
        
        // 
        mnuVentas_MedidaPresion.setEnabled(true);
        
        //  30.11.09
        mnuEconoFar_Inventario.setEnabled(true);
        
        mnuInventario_Mercaderia.setEnabled(true);
        
    }

    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_CAJERO) ) {
        
        // 
        mnuVentas_Recargas.setEnabled(true);
        
      mnuAdministracion_ControlHoras.setEnabled(false);
      mnuEconoFar_Caja.setEnabled(true);
        mnuCaja_MovimientosCaja.setEnabled(true);
          mnuCaja_AperturarCaja.setEnabled(true);
          mnuCaja_CerrarCaja.setEnabled(true);
        mnuCaja_ConfigurarCaja.setEnabled(true);
        mnuCaja_ReimpresionPedido.setEnabled(true);
        //mnuCaja_CorreccionComprobantes.setEnabled(true);
         mnuCaja_PruebaImpresora.setEnabled(true);
        mnuEconofar_CajaElectronica.setEnabled(true);
        mnuCE_CierreTurno.setEnabled(true);
         // 
         mnuPedidosLocales.setEnabled(false);
         if(FarmaVariables.vEconoFar_Matriz){
             mnuPedidoAdicional.setVisible(false);
             mnuPedidoEspecial.setVisible(false);
         }
         
    if ( FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_TRADICIONAL) )
        mnuCaja_CobrarPedido.setEnabled(true);
        // 
        // 
        mnuPedidosLocales.setEnabled(false);
        if(FarmaVariables.vEconoFar_Matriz){
            mnuPedidoAdicional.setVisible(false);
            mnuPedidoEspecial.setVisible(false);
        }
        
        // , 03.06.2010
        mnuCaja_ControlSobresParciales.setEnabled(validaIndicadorControlSobres());
    }

    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_ADMLOCAL) ) {
      validaOpcionesMenu(true);
        if (UtilityFondoSencillo.indActivoFondo()) {
          mnuFondoSencillo.setVisible(true);
        }
        mnDigemid.setEnabled(true);
      //12.11.2007 MOdificado
      mnuAdministracion_ControlHoras.setEnabled(false);
      mnuInventarioCiclico_Inicio.setEnabled(false);
        // 
        mnuPedidosLocales.setEnabled(true);
        if(FarmaVariables.vEconoFar_Matriz){
            mnuPedidoAdicional.setVisible(false);
            mnuPedidoEspecial.setVisible(false);
        }
      // 
      mnuVentas_MedidaPresion.setEnabled(true);
      // 
        mnuVentas_Recargas.setEnabled(true);
        
        //  18.02.09
        mnuMantenimiento_Carne.setEnabled(true);
        
        // MODULO CAJA ELECTRONICA
         //  16.01.09
        mnuCE_Prosegur.setEnabled(true);
        
        //  09.04.10
         /*mnuSobres.setEnabled(validaIndicadorControlSobres());
         mnuRemito.setEnabled(isLocalProsegur());*/
         
         // , 03.06.2010
        mnuCaja_ControlSobresParciales.setEnabled(validaIndicadorControlSobres());
        mnuCE_Prosegur.setEnabled(isLocalProsegur());
        
        
        //  12.02.09
        mmuIngresoTemperatura.setEnabled(true);
        
        //  31.11.09
        mnuInventario_Mercaderia.setEnabled(true);
        //  04.03.10
        if(UtilityFondoSencillo.indActivoFondo()){
            mnuFondoSencillo.setEnabled(true);
            }else{
              mnuFondoSencillo.setEnabled(false);
            }
        System.err.println("ConstantsRecepCiega.error_du:"+ConstantsRecepCiega.error_du);         
        /*if(ConstantsRecepCiega.error_du){
            mnuIngTransportista.setEnabled(true);
        }*/
        if(UtilityRecepCiega.indHabDatosTransp()){
            mnuIngTransportista.setEnabled(true);
        }
    }
    //  29.09.2010
    if(FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_CONTABILIDAD)||
        FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_LECTURA_REPORTES)
      ){
        mnuCE_Prosegur.setEnabled(isLocalProsegur());
    }
        
    
    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_AUDITORIA) ) {
      mnuEconoFar_TomaInventario.setEnabled(true); 
      mnuInventarioCiclico_Inicio.setEnabled(true);
      mnuAdministracion_ControlHoras.setEnabled(false);
      if(!FarmaVariables.vEconoFar_Matriz) {
      // MODULO INVENTARIO
      mnuEconoFar_Inventario.setEnabled(true);
        mnuInventario_GuiaEntrada.setEnabled(true);
        mnuInventario_Kardex.setEnabled(true);
        mnuInventario_Transferencias.setEnabled(true);
        mnuInventario_RecepcionProductos.setEnabled(true);
        mnuInventario_PedioReposicion.setEnabled(true);
        mnuInventario_Guias.setEnabled(true);
        mnuInventario_Ajustes.setEnabled(true);
        //Toma Inventario
        mnuTomaInventario_Tradicional.setEnabled(true);
        mnuTomaInventario_Ciclico.setEnabled(true);
        mnuTomaInventario_Historico.setEnabled(true);
        mnuTomaInVentario_ItemsXLab.setEnabled(true);
        //mnuInventario_RecepcionLocales.setEnabled(pValor);
          // 
          mnuPedidosLocales.setEnabled(true);
          mnuPedidoAdicional.setVisible(true);
          mnuPedidoEspecial.setVisible(true);
          
      } else 
      {
        mnuTomaInventario_Tradicional.setEnabled(false);
        mnuTomaInventario_Ciclico.setEnabled(true);
        mnuTomaInventario_Historico.setEnabled(true);
          // 
          mnuPedidosLocales.setEnabled(true);
          mnuPedidoAdicional.setVisible(false);
          mnuPedidoEspecial.setVisible(false);
      }
    }
/*
 * Cambio Jquispe se agrego el rol de Asistente de Auditoria
 * 23/03/2010
 * */
    
    if ( FarmaVariables.dlgLogin.verificaRol(ConstantsPtoVenta.ROL_ASISTENTE_AUDITORIA) ) {
      mnuEconoFar_TomaInventario.setEnabled(true); 
      mnuInventarioCiclico_Inicio.setEnabled(true);
      mnuAdministracion_ControlHoras.setEnabled(false);
      if(!FarmaVariables.vEconoFar_Matriz) {
      // MODULO INVENTARIO
      mnuEconoFar_Inventario.setEnabled(true);
        mnuInventario_GuiaEntrada.setEnabled(true);
        mnuInventario_Kardex.setEnabled(true);
        mnuInventario_Transferencias.setEnabled(true);
        mnuInventario_RecepcionProductos.setEnabled(true);
        mnuInventario_PedioReposicion.setEnabled(true);
        mnuInventario_Guias.setEnabled(true);
        mnuInventario_Ajustes.setEnabled(true);
        //Toma Inventario
        mnuTomaInventario_Tradicional.setEnabled(true);
        mnuTomaInventario_Ciclico.setEnabled(true);
        mnuTomaInventario_Historico.setEnabled(true);
        mnuTomaInVentario_ItemsXLab.setEnabled(true);
        //mnuInventario_RecepcionLocales.setEnabled(pValor);
          // 
          mnuPedidosLocales.setEnabled(true);
          mnuPedidoAdicional.setVisible(true);
          mnuPedidoEspecial.setVisible(true);
          
      } else 
      {
        mnuTomaInventario_Tradicional.setEnabled(false);
        mnuTomaInventario_Ciclico.setEnabled(true);
        mnuTomaInventario_Historico.setEnabled(true);
          // 
          mnuPedidosLocales.setEnabled(true);
          mnuPedidoAdicional.setVisible(false);
          mnuPedidoEspecial.setVisible(false);
      }
    }
    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_OPERADOR_SISTEMAS) ) {
      validaOpcionesMenu(true);
        mnDigemid.setEnabled(true);
        if (UtilityFondoSencillo.indActivoFondo()) {
          mnuFondoSencillo.setVisible(true);
        }
//if(ConstantsRecepCiega.error_du){
          if(UtilityRecepCiega.indHabDatosTransp()){
            mnuIngTransportista.setEnabled(true);
        }
      mnuInventarioCiclico_Inicio.setEnabled(true);
         // 
         mnuPedidosLocales.setEnabled(true);
         if(FarmaVariables.vEconoFar_Matriz){
             mnuPedidoAdicional.setVisible(false);
             mnuPedidoEspecial.setVisible(false);
         }
         // 
         mnuVentas_MedidaPresion.setEnabled(false);
         // 
         mnuVentas_Recargas.setEnabled(true);

        // , 03.06.2010
        mnuCaja_ControlSobresParciales.setEnabled(validaIndicadorControlSobres());
        mnuCE_Prosegur.setEnabled(isLocalProsegur());
        
        /*if(ConstantsRecepCiega.error_du){
            mnuIngTransportista.setEnabled(true);
        }*/
        //  04.03.10
        if(UtilityFondoSencillo.indActivoFondo()){
            mnuFondoSencillo.setEnabled(true);
            }else{
              mnuFondoSencillo.setEnabled(false);
            }
                    
    }

    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_INVENTARIADOR) ) {
      mnuEconoFar_TomaInventario.setEnabled(true);
        mnuTomaInventario_Tradicional.setEnabled(true);
        mnuInventario_Toma.setEnabled(true);
        mnuTomaInventario_Ciclico.setEnabled(true);
        mnuInventarioCiclico_Inicio.setEnabled(false);
        mnuInventarioCiclico_Toma.setEnabled(true);
        mnuAdministracion_ControlHoras.setEnabled(false);
        mnuPedidosLocales.setEnabled(true);
        
         // 
         mnuPedidosLocales.setEnabled(true);
         if(FarmaVariables.vEconoFar_Matriz){
             mnuPedidoAdicional.setVisible(false);
             mnuPedidoEspecial.setVisible(false);
         }
    }




    
    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_LECTURA_REPORTES) )
    {
      // MODULO INVENTARIO
      mnuEconoFar_Inventario.setEnabled(true);
        mnuInventario_GuiaEntrada.setEnabled(true);
      // MODULO ADMINISTRACION
      mnuEconoFar_Administracion.setEnabled(true);
        mnuAdministracion_Usuarios.setEnabled(false);
          mnuUsuarios_CambioUsuario.setEnabled(false);
          mnuUsuarios_CambioClave.setEnabled(false);
        mnuAdministracion_MovCaja.setEnabled(true);
          mnuMovCaja_ConsultarMov.setEnabled(true);
      // MODULO REPORTES
      mnuEconoFar_Reportes.setEnabled(true);
        mnuReportes_RegistroVentas.setEnabled(true);
        mnuReportes_VentasVendedor.setEnabled(true);
        mnuReportes_DetalleVentas.setEnabled(true);
        mnuReportes_ResumenVentaDia.setEnabled(true);
        mnuReportes_VentasProducto.setEnabled(true);
  //      mnuReportes_VentasConvenio.setEnabled(pValor);
  //      mnuReportes_VentasLinea.setEnabled(pValor);
        mnuReportes_VentasHora.setEnabled(true);
        mnuReportes_VentasDiaMes.setEnabled(true);
        mnuReportes_FaltaCero.setEnabled(true);
      // MODULO CAJA ELECTRONICA
      mnuEconofar_CajaElectronica.setEnabled(true);
        mnuCE_CierreLocal.setEnabled(true);
        mnuCE_CierreTurno.setEnabled(true);
        mnuAdministracion_ControlHoras.setEnabled(false);
    
    }

    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_LECTURA_INVENTARIO) )
    {
      // MODULO INVENTARIO
    mnuEconoFar_Inventario.setEnabled(true);
      mnuInventario_GuiaEntrada.setEnabled(true);
      mnuInventario_Kardex.setEnabled(true);
      mnuInventario_Transferencias.setEnabled(true);
      mnuInventario_PedioReposicion.setEnabled(true);
      mnuAdministracion_ControlHoras.setEnabled(false);
      //mnuInventario_Guias.setEnabled(true);
       // 
        //mnuPedidosLocales.setEnabled(true);
    }
    /**Diego Ubilluz
     * Rol: Supervisor Ventas
     * @author:  
     * @since: 11.06.2007
     */
    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_SUPERVISOR_VENTAS) )
    {
       mnuEconoFar_Ventas.setEnabled(true);
       mnuVentas_DeliveryAutomatico.setEnabled(true);
       
       mnuEconoFar_Caja.setEnabled(true);
       mnuCaja_CorreccionComprobantes.setEnabled(true);
       
       mnuEconoFar_Inventario.setEnabled(true);
       mnuInventario_GuiaEntrada.setEnabled(true);
       mnuInventario_Kardex.setEnabled(true);
       mnuInventario_Transferencias.setEnabled(true);
       mnuInventario_PedioReposicion.setEnabled(true);
       mnuInventario_Ajustes.setEnabled(true);
       
       mnuInventario_RecepcionLocales.setEnabled(true);
       
       mnuEconoFar_Administracion.setEnabled(true);
       mnuAdministracion_MovCaja.setEnabled(true);
       mnuMovCaja_ConsultarMov.setEnabled(true);
       
       mnuEconoFar_Reportes.setEnabled(true);
       mnuReportes_RegistroVentas.setEnabled(true);
       mnuReportes_VentasVendedor.setEnabled(true);
       mnuReportes_DetalleVentas.setEnabled(true);
       mnuReportes_ResumenVentaDia.setEnabled(true);
       mnuReportes_VentasProducto.setEnabled(true);
       mnuReportes_VentasHora.setEnabled(true);
       mnuReportes_VentasDiaMes.setEnabled(true);
       mnuReportes_FaltaCero.setEnabled(true);
       mnuReportes_ProductosABC.setEnabled(true);
       mnuReportes_UnidVtaLocal.setEnabled(true);
       mnuReportes_ProdSinVtaNDias.setEnabled(true);
       
       mnuEconofar_CajaElectronica.setEnabled(true);
       mnuCE_CierreLocal.setEnabled(true);
       
       //agregado 
       //27.11.2007   modificacion
       mnuAdministracion_ControlHoras.setEnabled(true);
         // 
       /*  mnuPedidosLocales.setEnabled(true);
         if(FarmaVariables.vEconoFar_Matriz){
             mnuPedidoAdicional.setVisible(false);
             mnuPedidoEspecial.setVisible(false);
         }*/
    }
    
    
  }

  private void validaOpcionesMenu(boolean pValor) {
    // MODULO VENTAS
    mnuEconoFar_Ventas.setEnabled(pValor);
      mnuVentas_GenerarPedido.setEnabled(pValor);
      //mnuVentas_DistribucionGratuita.setEnabled(pValor);
      mnuVentas_DeliveryAutomatico.setEnabled(pValor);
    // MODULO CAJA
    mnuEconoFar_Caja.setEnabled(pValor);
      mnuCaja_MovimientosCaja.setEnabled(pValor);
        mnuCaja_AperturarCaja.setEnabled(pValor);
        mnuCaja_CerrarCaja.setEnabled(pValor);
      mnuCaja_ConfigurarCaja.setEnabled(pValor);
      mnuCaja_CobrarPedido.setEnabled(pValor);
      mnuCaja_AnularVentas.setEnabled(pValor);
        mnuCaja_PedidoCompleto.setEnabled(pValor);
        mnuCaja_AnularComprobante.setEnabled(pValor);
      mnuCaja_CorreccionComprobantes.setEnabled(pValor);
      mnuCaja_ReimpresionPedido.setEnabled(pValor);
      mnuCaja_PruebaImpresora.setEnabled(pValor);
      mnDigemid.setEnabled(pValor);
    // MODULO INVENTARIO
    mnuEconoFar_Inventario.setEnabled(pValor);
      mnuInventario_GuiaEntrada.setEnabled(pValor);
      mnuInventario_Kardex.setEnabled(pValor);
      mnuInventario_Transferencias.setEnabled(pValor);
      mnuInventario_RecepcionProductos.setEnabled(pValor);
      mnuInventario_PedioReposicion.setEnabled(pValor);
      mnuInventario_Guias.setEnabled(pValor);
      mnuInventario_Ajustes.setEnabled(pValor);
      mnuInventario_RecepcionLocales.setEnabled(pValor);
    // MODULO TOMA INVENTARIO
    mnuEconoFar_TomaInventario.setEnabled(pValor);
      mnuTomaInventario_Ciclico.setEnabled(pValor);
      mnuTomaInventario_Tradicional.setEnabled(pValor);
      mnuTomaInventario_Historico.setEnabled(pValor);
      mnuTomaInVentario_ItemsXLab.setEnabled(pValor);
    // MODULO ADMINISTRACION
    mnuEconoFar_Administracion.setEnabled(pValor);
      mnuAdministracion_Usuarios.setEnabled(pValor);
      	mnuUsuarios_CambioUsuario.setEnabled(pValor);
      	mnuUsuarios_CambioClave.setEnabled(pValor);
      mnuAdministracion_Mantenimiento.setEnabled(pValor);
      	mnuMantenimiento_Usuarios.setEnabled(pValor);
      	mnuMantenimiento_Cajas.setEnabled(pValor);
      	mnuMantenimiento_Impresoras.setEnabled(pValor);
      	mnuMantenimiento_Clientes.setEnabled(pValor);
        mnuMantenimiento_Parametros.setEnabled(pValor);
      mnuAdministracion_MovCaja.setEnabled(pValor);
      	mnuMovCaja_RegistrarVentas.setEnabled(pValor);
      	mnuMovCaja_ConsultarMov.setEnabled(pValor);
      mnuAdministracion_RegViajero.setEnabled(pValor);
      mnuAdministracion_ControlHoras.setEnabled(pValor);
      mnuAdministracion_Otros.setEnabled(pValor);
      mnuMantenimiento_MaquinaIP.setEnabled(pValor);
      
    // MODULO REPORTES
    mnuEconoFar_Reportes.setEnabled(pValor);
      mnuReportes_RegistroVentas.setEnabled(pValor);
      mnuReportes_VentasVendedor.setEnabled(pValor);
      mnuReportes_DetalleVentas.setEnabled(pValor);
      mnuReportes_ResumenVentaDia.setEnabled(pValor);
      mnuReportes_VentasProducto.setEnabled(pValor);
//      mnuReportes_VentasConvenio.setEnabled(pValor);
//      mnuReportes_VentasLinea.setEnabled(pValor);
      mnuReportes_VentasHora.setEnabled(pValor);
      mnuReportes_VentasDiaMes.setEnabled(pValor);
      mnuReportes_FaltaCero.setEnabled(pValor);
    // MODULO CAJA ELECTRONICA
    mnuEconofar_CajaElectronica.setEnabled(pValor);
      mnuCE_CierreTurno.setEnabled(pValor);
      mnuCE_CierreLocal.setEnabled(pValor);
      mnuCE_Prosegur.setEnabled(pValor);
   
      // MODULO CAMBIOS PRECIO PROD.
      mnuProdCambiados.setEnabled(pValor);
      mnuPrecioCambProd.setEnabled(pValor);
   
      // MODULO INGRESO TEMPERATURA
      mmuIngresoTemperatura.setEnabled(pValor);
      
      
      // MODULO INGRESO TEMPERATURA
      mmuIngresoTemperatura.setEnabled(pValor);
      
      //  02.03.10
            mnuFondoSencillo.setEnabled(pValor);
  }

  private void mnuCaja_ReimpresionPedido_actionPerformed(ActionEvent e)
  {
    DlgPedidosPendientesImpresion dlgPedidosPendientesImpresion = new DlgPedidosPendientesImpresion(this,"", true);
		dlgPedidosPendientesImpresion.setVisible(true);
  }


   void muestraLoginCambioUser() {

    DlgLogin dlgLogin = new DlgLogin(this,ConstantsPtoVenta.MENSAJE_LOGIN,true);
    dlgLogin.setVisible(true);
      if (FarmaVariables.vAceptar) {
          FarmaVariables.dlgLogin = dlgLogin;
          VariablesCaja.vVerificaCajero = true;
          verificaRolUsuario();
          if(FarmaVariables.vEconoFar_Matriz){
              mnuPedidoEspecial.setEnabled(false);
          }
              
      }
  }

  private void mnuVentas_DistribucionGratuita_actionPerformed(ActionEvent e)
  {
    distribucionGratuita();
  }

  private void distribucionGratuita() {
    DlgResumenPedidoGratuito dlgResumenPedidoGratuito = new DlgResumenPedidoGratuito(this,"",true);
    dlgResumenPedidoGratuito.setVisible(true);
    while(FarmaVariables.vAceptar) distribucionGratuita();
    if(!FarmaVariables.vAceptar) verificaRolUsuario();
  }

  private void mnuReportes_DetalleVentas_actionPerformed(ActionEvent e)
  {
    DlgDetalleVentas  dlgDetalleVentas = new DlgDetalleVentas(this,"", true);
		dlgDetalleVentas.setVisible(true);
  }

/*
  private void mnuReportes_VentasVendedor_actionPerformed(ActionEvent e)
  {
    DlgVentasPorVendedor dlgVentasPorVendedor = new DlgVentasPorVendedor(this, "", true);
    dlgVentasPorVendedor.setVisible(true);
  }

*/
  //-----------------------------Items Reportes   26.11.08------------------------------------
  private void  mnuReportes_VentasVendedor_Total_actionPerformed(ActionEvent e){
      
       VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA = ConstantsReporte.ACCION_MOSTRAR_TOTALES;
       System.out.println("VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA :" +  VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA);
       
       
       DlgVentasPorVendedor dlgVentasPorVendedor = new DlgVentasPorVendedor(this, "", true);
       dlgVentasPorVendedor.setVisible(true);
       
       
   }
  
    private void mnuReportes_VentasVendedor_Mezon_actionPerformed(ActionEvent e) {
        
        VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA=ConstantsReporte.ACCION_MOSTRAR_MEZON;
        System.out.println("VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA :" +  VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA);
        DlgVentasPorVendedor dlgVentasPorVendedor = new DlgVentasPorVendedor(this, "", true);
        dlgVentasPorVendedor.setTitle("Ventas por Vendedor Mezon");
        dlgVentasPorVendedor.setVisible(true);
    
    }
  
    private void mnuReportes_VentasVendedor_Delivery_actionPerformed(ActionEvent e) {
    
        VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA=ConstantsReporte.ACCION_MOSTRAR_DELIVERY;
        System.out.println("VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA :" +  VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA);
        DlgVentasPorVendedor dlgVentasPorVendedor = new DlgVentasPorVendedor(this, "", true);
        dlgVentasPorVendedor.setTitle("Ventas por Vendedor Delivery");
        dlgVentasPorVendedor.setVisible(true);
        
        
    }
    
    private void mnuReportes_VentasVendedor_Institucional_actionPerformed(ActionEvent e) 
    {
        VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA=ConstantsReporte.ACCION_MOSTRAR_INSTITUCIONAL;
        System.out.println("VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA :" +  VariablesReporte.ACCION_MOSTRAR_TIPO_VENTA);
        DlgVentasPorVendedor dlgVentasPorVendedor = new DlgVentasPorVendedor(this, "", true);
        dlgVentasPorVendedor.setTitle("Ventas por Vendedor Institucional");
        dlgVentasPorVendedor.setVisible(true);
            
    }
//-------------------------------------------------------------------------------------------  
  private void mnuReportes_VentasProducto_actionPerformed(ActionEvent e)
  {
    DlgVentasPorProducto dlgVentasPorProducto = new DlgVentasPorProducto(this,"", true);
    dlgVentasPorProducto.setVisible(true);
        
     
  }

  private void mnuReportes_VentasDiaMes_actionPerformed(ActionEvent e)
  {
    VariablesReporte.vOrdenar = ConstantsReporte.vVentasDiaMes;
    DlgVentasDiaMes dlgVentasDiaMes=new DlgVentasDiaMes(this,"",true);
    dlgVentasDiaMes.setVisible(true);
  }

  private void mnuReportes_ResumenVentaDia_actionPerformed(ActionEvent e)
  {
    DlgVentasResumenPorDia  dlgVentasResumenPorDia = new DlgVentasResumenPorDia(this,"",true);
    dlgVentasResumenPorDia.setVisible(true);
  }

  private void mnuReportes_VentasHora_actionPerformed(ActionEvent e)
  {
    DlgVentasPorHora dlgVentasPorHora = new DlgVentasPorHora(this,"",true);
    dlgVentasPorHora.setVisible(true);
  }

  private void cargaVariablesBD()
  {
    //FarmaVariables.vUsuarioBD = ConstantsPtoVenta.USUARIO_BD;
    //FarmaVariables.vClaveBD = ConstantsPtoVenta.CLAVE_BD;
    //FarmaVariables.vSID = ConstantsPtoVenta.SID;
    FarmaVariables.vPUERTO = ConstantsPtoVenta.PUERTO;
  }
    
  private void mnuAdministracion_RegViajero_actionPerformed(ActionEvent e)
  {
   DlgProcesaViajero dlgProcesaViajero = new DlgProcesaViajero(this, "", true);
   dlgProcesaViajero.setVisible(true);
  }

  private void mnuInventario_Guias_actionPerformed(ActionEvent e)
  {
    DlgGuiasRemision dlgGuiasRemision = new DlgGuiasRemision(this,"",true);
    dlgGuiasRemision.setVisible(true);
  }

  private void mnuReportes_FaltaCero_actionPerformed(ActionEvent e)
  {
    DlgProductoFaltaCero dlgProductoFaltaCero = new DlgProductoFaltaCero(this,"",true);
    dlgProductoFaltaCero.setVisible(true);
  }

  private void mnuCaja_PruebaImpresora_actionPerformed(ActionEvent e)
  {
    DlgPruebaImpresora dlgPruebaImpresora = new DlgPruebaImpresora(this,"",true);
    dlgPruebaImpresora.setVisible(true);
  }

  private void mnuMantenimiento_Parametros_actionPerformed(ActionEvent e)
  {
    DlgParametros dlgParametros = new DlgParametros(this,"",true);
    dlgParametros.setVisible(true);
  }
  
  
    private void mnuMantenimiento_Carne_actionPerformed(ActionEvent e)
    {
        
     //DlgMantCarne dlgMantCarne = new DlgMantCarne(this,"",true);
      //dlgMantCarne.setVisible(true);
        
        
      DlgBuscaTrabajadorLocal dlgBuscaTrab = new DlgBuscaTrabajadorLocal(this,"",true);
      dlgBuscaTrab.setTitle("Lista de Trabajadores Local");
      dlgBuscaTrab.setVisible(true);
    }
    
  
  private void salirSistema()
  {
      eliminaCodBarra(); 
      eliminaBoletaTxt();
    if(FarmaVariables.vEconoFar_Matriz)
      this.dispose();
    else
      System.exit(0);    
  }

  private void salir(WindowEvent e)
  {
    salirSistema();
  }
  
  /**
  * Inicializa variables
  * @author  
  * @since 17.12.2009
  */
  private void inicializa(){
      try{
          VariablesPtoVenta.vIndRecepCiega = DBPtoVenta.obtieneIndicadorTipoRecepcionAlmacen();
      }
      catch(SQLException sql){
          sql.printStackTrace();
          FarmaUtility.showMessage(this,"Ocurrió un error al obtener el indicador del tipo de recepción de almacen : \n"+ sql.getMessage(), null);
      }
      if (VariablesPtoVenta.vIndRecepCiega.equalsIgnoreCase("S")){
          mnuInventario_Mercaderia.setEnabled(true);
          mnuInventario_RecepcionProductos.setEnabled(true);
          mnuInventario_Recepcion.setEnabled(true);
      }
      else{
          mnuInventario_Mercaderia.setEnabled(true);
          mnuInventario_RecepcionProductos.setEnabled(true);
          mnuInventario_Recepcion.setEnabled(false);
      }
      
      //pruebas de punto de venta
      //if (validaEsOperador()){
          if (validaCantidadPruebasCompleta()){
              lblMensaje.setVisible(true);
              pnlEconoFar.setBackground(Color.ORANGE);
              btnRevertir.setBackground(Color.ORANGE);
              txtRevertir.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
              txtRevertir.setBackground(Color.ORANGE);      
              btnRevertir.setForeground(Color.ORANGE); 
          }
      //}
  }
  
  private void mnuReportes_ProductosABC_actionPerformed(ActionEvent e)
  {
    DlgProductosABC dlgProductosABC = new DlgProductosABC(this,"",true);
    dlgProductosABC.setVisible(true);
  }

  private void mnuInventario_Ajustes_actionPerformed(ActionEvent e)
  {
    DlgAjustesporFecha dlgAjustesporFecha = new DlgAjustesporFecha(this,"",true);
    dlgAjustesporFecha.setVisible(true);
  }

  private void mnuInventario_RecepcionLocales_actionPerformed(ActionEvent e)
  {
    DlgTransferenciasLocal dlgTransferenciasLocal = new DlgTransferenciasLocal(this,"",true);
    dlgTransferenciasLocal.setVisible(true);
  }

  private void mnuCE_CierreTurno_actionPerformed(ActionEvent e)
  {
    DlgCierreCajaTurno dlgCierreCajaTurno = new DlgCierreCajaTurno(this, "", true);
    dlgCierreCajaTurno.setVisible(true);
    //verificaRolUsuario();
  }

  private void mnuCE_CierreLocal_actionPerformed(ActionEvent e)
  {
    /*DlgCierreDia dlgCierreDia = new DlgCierreDia(this, "", true);
    dlgCierreDia.setVisible(true);*/
    DlgHistoricoCierreDia dlgHistoricoCierreDia = new DlgHistoricoCierreDia(this, "", true);
    dlgHistoricoCierreDia.setVisible(true);
  }

  private void mnuInventarioCiclico_Toma_actionPerformed(ActionEvent e)
  {
    DlgListaTomasInventarioCiclico dlgListaTomasInventarioCiclico = new DlgListaTomasInventarioCiclico(this,"",true);
    dlgListaTomasInventarioCiclico.setVisible(true);
  }

  private void mnuInventario_Inicio_actionPerformed(ActionEvent e)
  {
    DlgInicioToma dlgInicioToma = new DlgInicioToma(this, "", true);
    dlgInicioToma.setVisible(true);
  }

  private void mnuInventario_Toma_actionPerformed(ActionEvent e)
  {
    DlgListaTomasInventario dlgListaTomasInventario = new DlgListaTomasInventario(this, "", true);
    dlgListaTomasInventario.setVisible(true);
  }

  private void mnuInventarioCiclico_Inicio_actionPerformed(ActionEvent e)
  {
    DlgInicioInvCiclico dlgInicioInvCiclico = new DlgInicioInvCiclico(this, "", true);
    dlgInicioInvCiclico.setVisible(true);
  }

  private void mnuVentas_DeliveryAutomatico_actionPerformed(ActionEvent e)
  {
    
  }
  
  private void mnuVentas_MedidaPresion_actionPerformed(ActionEvent e)
  {
    DlgListaMedidaPresion dlgListaMedidaPresion = new DlgListaMedidaPresion(this, "", true);
    dlgListaMedidaPresion.setVisible(true);
  }

    private void mnuVentas_Recargas_actionPerformed(ActionEvent e)
    {
        /*
      DlgListaMedidaPresion dlgListaMedidaPresion = new DlgListaMedidaPresion(this, "", true);
      dlgListaMedidaPresion.setVisible(true);
    */
    
    }

    private void mnuVentas_Correlativo_actionPerformed(ActionEvent e)
    {
      
        //DlgConsultarRecargaCorrelativo dlgRecargaCorrelativo = new DlgConsultarRecargaCorrelativo(this,"",true); //antes AS
        DlgConsultarRecargaCorrelativo_AS dlgRecargaCorrelativo = new DlgConsultarRecargaCorrelativo_AS(this,"Consulta de recarga por correlativo",true); // , 05.05.2010
        dlgRecargaCorrelativo.setVisible(true);
    
    }

    private void mnuVentas_Comprobantes_actionPerformed(ActionEvent e)
    {
      
        DlgConsultaRecargaComprobante dlgRecargaComprobante = new DlgConsultaRecargaComprobante(this,"",true);
        dlgRecargaComprobante.setVisible(true);
    }



  private void mnuAdministracion_ControlHoras_actionPerformed(ActionEvent e)
  {
    DlgControlHoras dlgControlHoras= new DlgControlHoras(this, "", true);
    dlgControlHoras.setVisible(true);
  
  }

  private void mnuOtros_MantProbisa_actionPerformed(ActionEvent e)
  {
    DlgListaProbisa dlgListaProbisa = new DlgListaProbisa(this,"",true);
    dlgListaProbisa.setVisible(true);
  }
  
  private void mnuReportes_UnidVtaLocal_actionPerformed(ActionEvent e)
  {
    DlgUnidVtaLocal dlgUnidVtaLocal = new DlgUnidVtaLocal(this,"",true);
    dlgUnidVtaLocal.setVisible(true); 
  }
  
  private void mnuReportes_ProdSinVtaNDias_actionPerformed(ActionEvent e)
  {
    DlgProdSinVtaNDias dlgProdSinVtaNDias = new DlgProdSinVtaNDias(this,"",true);
    dlgProdSinVtaNDias.setVisible(true);
  }

  private void mnuControIngreso_actionPerformed(ActionEvent e)
  {
  }

  private void mnuIngreso_actionPerformed(ActionEvent e)
  {
    DlgControlIngreso dlgControlIngreso = new DlgControlIngreso(this,"",true);
    dlgControlIngreso.setVisible(true);  
  }
  
  private void eliminaCodBarra(){
    try{
       UtilityVentas.eliminaImagenesCodBarra();
       }
    catch (Exception e) {
        e.printStackTrace();          
    }  
  }
  
  private void eliminaBoletaTxt(){
      try{
         UtilityVentas.eliminaArchivoTxt();
         }
      catch (Exception e) {
          e.printStackTrace();          
      }  
  }

    /**
     * @author Daniel Fernando Veliz La Rosa
     * @since  11.09.08
     * @param e
     */
    private void mnuPedidoAdicional_actionPerformed(ActionEvent e) {
        DlgPedidoReposicionAdicionalNuevo dlgPedidoAdicional = 
                        new DlgPedidoReposicionAdicionalNuevo(this,"",true);
       dlgPedidoAdicional.setVisible(true);
    }
    
    /**
     * @author Jorge Cortez
     * @since  17.09.08
     * @param e
     */
    private void mnuPedidoEspecial_actionPerformed(ActionEvent e)
      {
        DlgListaPedidosEspeciales dlglistaPE=new DlgListaPedidosEspeciales(this,"",true);
        dlglistaPE.setVisible(true);
      
      }


    /**
     * @author  
     * @since  11.02.2009
     */
    private void mmuIngresoTemperatura_actionPerformed(ActionEvent e) {
        DlgHistoricoTemperatura   dlghistorico=new DlgHistoricoTemperatura(this,"",true);
        dlghistorico.setVisible(true);
}
    
    private void mnuInventarioDiario_Inicio_actionPerformed(ActionEvent e) {
        
        DlgInicioInveDiario   dlgInicioInveDiario=new DlgInicioInveDiario(this,"",true);
        dlgInicioInveDiario.setVisible(true);
        
    }

    private void mnuInventarioDiario_Toma_actionPerformed(ActionEvent e) {
        
        DlgListaTomasInventarioDiario   dlgListaTomasInventarioDiario=new DlgListaTomasInventarioDiario(this,"",true);
        dlgListaTomasInventarioDiario.setVisible(true);
        
    }

    private void mnuInventarioDiario_Diferencia_actionPerformed(ActionEvent e) {
        
        if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_AUDITORIA) || validarAsistAudit()) {
        DlgListaDiferenciasToma dlgListaDiferenciasToma=new  DlgListaDiferenciasToma(this,"",true);
        dlgListaDiferenciasToma.setVisible(true);
        }
        else {
            FarmaUtility.showMessage(this, 
                                     "No posee privilegios suficientes para acceder a esta opción", 
                                     null);
        }
        
    }

    private void mnuMantenimiento_MaquinaIP_actionPerformed(ActionEvent e) {
    
        //  05.06.09
        DlgListaIPSImpresora DlgIP=new  DlgListaIPSImpresora(this,"",true);
        DlgIP.setVisible(true);
    
    }
    
    private void mnuMantenimiento_ImpresoraTermica_actionPerformed(ActionEvent e) {
        
        //  25/06/2009
        DlgListaImpresoraTermCreaMod dlgLstImprTermCreaMod=new  DlgListaImpresoraTermCreaMod(this,"",true);
        dlgLstImprTermCreaMod.setVisible(true);
        
    
    }
    

    private void jMenuItem2_actionPerformed(ActionEvent e) {
        
        DlgPedidoPendienteDiario dlgPedidosPendientes = new DlgPedidoPendienteDiario(this,"",true);
        dlgPedidosPendientes.setVisible(true);
        /*if(FarmaVariables.vAceptar)
        {
        
           indBorra=false;
           
          //System.out.println("VariablesCaja.vIndConvenio : "+VariablesCaja.vIndConvenio);
          //System.out.println("VariablesCaja.vCodConvenio : "+VariablesCaja.vCodConvenio);
          //System.out.println("VariablesCaja.vCodCliLocal : "+VariablesCaja.vCodCliLocal);
           
          cargaFormasPago(VariablesCaja.vIndConvenio,VariablesCaja.vCodConvenio,VariablesCaja.vCodCliLocal);
          

          txtNroPedido.setText("" + VariablesCaja.vNumPedPendiente);
          lblFecPed.setText("" + VariablesCaja.vFecPedACobrar);
          if(!validaPedidoDiario()) return;
          buscaPedidoDiario();
          
          
          
          FarmaVariables.vAceptar = false;
          //añadido 21.09.2007  
          System.out.println("VariablesCaja.cobro_Pedido_Conv_Credito : "+VariablesCaja.cobro_Pedido_Conv_Credito);
          if(VariablesCaja.cobro_Pedido_Conv_Credito.equalsIgnoreCase("N"))
          {
          System.out.println("VariablesCaja.cobro_Pedido_Conv_Credito : "+VariablesCaja.cobro_Pedido_Conv_Credito); 
          if(VariablesCaja.vIndPedidoSeleccionado.equalsIgnoreCase(FarmaConstants.INDICADOR_S)) btnFormaPago.doClick();
          }
          else{
          FarmaUtility.moveFocus(txtMontoPagado);
          btnMonto.doClick();
          System.out.println("Foco lo coloco en TxtMonto  2222");
          }
          
          verificaMontoPagadoPedido();// 

        }*/
    }

    private void mnuCaja_ReimpresionTicketsAnulados_actionPerformed(ActionEvent e) {
        //  06.07.2009
        DlgListaTicketsAnulados dlgListaTicketsAnulados=new  DlgListaTicketsAnulados(this,"",true);
        dlgListaTicketsAnulados.setVisible(true);
    }

    private void mnuInventario_Recepcion_actionPerformed(ActionEvent e) {        
            //  16.11.2009
            DlgHistoricoRecepcion dlgRecepcion=new  DlgHistoricoRecepcion(this,"",true);
            dlgRecepcion.setVisible(true);
        
       
    }


    private void btnRevertir_actionPerformed(ActionEvent e) {
        
        //  19.01.10 Se verificara indicador para validar proceso de pruebas.
        if(validaIndicadorPruebas()){
        if (validaEsOperador()){
            DlgLogin dlgLogin = new DlgLogin(this,ConstantsPtoVenta.MENSAJE_LOGIN,true);
            dlgLogin.setRolUsuario(FarmaConstants.ROL_OPERADOR_SISTEMAS);
            dlgLogin.setVisible(true);
                
            if ( FarmaVariables.vAceptar ){
               FarmaUtility.moveFocus(txtRevertir);            
            }   
        }
        }else
            FarmaUtility.showMessage(this,"La opción de Revertir no se encuentra activa.",null);       
        
    }
    
    /**
     * Se valida indicador de proceso (pbl_tab_gral)
     * @AUTHOR  
     * @SINCE 19.01.10
     * */
    private boolean validaIndicadorPruebas(){
    
        boolean resutl=false;
        String ind="";
        try {
            ind = DBPtoVenta.obtenerIndReverLocal();
            if((ind.trim()).equalsIgnoreCase("S")){
                resutl=true;
            }
        } catch (SQLException sql) {
            sql.printStackTrace();            
            System.out.println("ERROR en consultar indicador de proceso de :\n"+sql.getMessage());
        }
        return resutl;
    
    }
   

    private boolean validaEsOperador(){
        boolean vResultado=false;
        if (FarmaVariables.vNuSecUsu.equalsIgnoreCase(FarmaConstants.ROL_OPERADOR_SISTEMAS))
            vResultado=true;
        else
            vResultado=false;
        return vResultado;
    }
    
    /**
     * valida si han pasado los dos dias permitidos para revertir los cambios en el local
     * @author  
     * @since  13.01.2010
     * @return flag
     */
    private boolean validarPermiteRevertir(){
        boolean flag=false;
        String ind="";
        try {
            ind = DBPtoVenta.obtenerIndReverPermitido();
            if((ind.trim()).equalsIgnoreCase("S")){
                flag=true;
            }
        } catch (SQLException sql) {
            sql.printStackTrace();            
            System.out.println("ERROR en validar los dos dias que se permite el revertir:\n"+sql.getMessage());
        }
        return flag;
    }
    

    private boolean validaIndicadorRevertirLocal(){
        boolean vResultado=false;
        try{
            vResultado = DBPtoVenta.obtieneIndicadorRevertirLocal();
        }catch(SQLException sql){            
            sql.printStackTrace();
            FarmaUtility.showMessage(this,"Ocurrió un error al obtener el indicador de revertir del local. \n"+ sql.getMessage(), null);
            vResultado=false;        
        }
        return vResultado;
    }
    
    private void grabaInicioFinPrueba(String tipo){
        
        try{
            DBPtoVenta.grabaInicioFinPrueba(tipo);
            FarmaUtility.aceptarTransaccion();
        }catch(SQLException sql){
            if(sql.getErrorCode()==20000){
                sql.printStackTrace();
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this,"Ocurrió un error al grabar inicio fin. \n"+ sql.getMessage(), null);                
            } else{
                sql.printStackTrace();
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this,"Ocurrió un error al grabar inicio fin. \n"+ sql.getMessage(), null);                        
            }
            
        }
    }
    private boolean validaCantidadPruebas(){
        int cantidad=-1;
        try{
            cantidad=DBPtoVenta.obtieneCantidadPruebas();
        }catch(SQLException sql){           
            sql.printStackTrace();
            FarmaUtility.showMessage(this,"Ocurrió un error al verificar inicio de prueba. \n"+ sql.getMessage(), null);                                    
            
        }
        if (cantidad == 0)  return true;
        return false;
    }
    
    private boolean validaCantidadPruebasCompleta(){
        int cantidad=-1;
        try{
            cantidad=DBPtoVenta.obtieneCantidadPruebasCompletas();
        }catch(SQLException sql){           
            sql.printStackTrace();
            FarmaUtility.showMessage(this,"Ocurrió un error al verificar inicio y fin de prueba. \n"+ sql.getMessage(), null);                                    
            
        }
        if (cantidad == 1)  return true;
        return false;
    }
    private String obtieneFechaInicioPrueba(){
        String fecha ="";
        try{
            fecha = DBPtoVenta.obtieneFechaInicioDePruebas();
            System.out.println("fecha 1: "+fecha);
            if (!fecha.equalsIgnoreCase("N")){
                return fecha;            
            }else{ 
                fecha ="";
                return fecha;
            }    
        }catch(SQLException sql){           
            sql.printStackTrace();
            FarmaUtility.showMessage(this,"Ocurrió un error al obtener la fecha inicial de pruebas. \n"+ sql.getMessage(), null);                                    
            fecha="";
        }
        System.out.println("fecha 2: "+fecha);
        return fecha;
    }
    
    /**
     * Recupera la direccion Domicilio Fiscal
     * @author ERIOS
     * @since 06.06.2013
     */
    public void cargaDireccionFiscal(){        
        try {
            ArrayList lstDirecFiscal = DBPtoVenta.obtieneDireccionMatriz();
            VariablesPtoVenta.vDireccionMatriz = FarmaUtility.getValueFieldArrayList(lstDirecFiscal, 0, 0);
            VariablesPtoVenta.vDireccionCortaMatriz = FarmaUtility.getValueFieldArrayList(lstDirecFiscal, 0, 1);
        }
        catch (SQLException sql) {            
            System.out.println("Error al obtener la dirección de la Dirección Fiscal."+sql.getMessage());
        }
    }

    public void obtieneIndDireMat(){        
        try {
            VariablesPtoVenta.vIndDirMatriz = DBPtoVenta.obtieneIndDirMatriz();
            System.out.println("VariablesPtoVenta.vIndDirMatriz"+VariablesPtoVenta.vIndDirMatriz);
        }
        catch (SQLException sql) {            
            System.out.println("Error al obtener indicador de la Dirección Fiscal."+sql.getMessage());
        }
    }
    
        private void mnuIngTransportista_actionPerformed(ActionEvent e) {
        DlgListaTransportistas dlgListaTransp = new DlgListaTransportistas(this,"",true);
        dlgListaTransp.setVisible(true);
    }

/**
     * devuelve true si se trata de un asistente de auditoria
     * @author JQUISPE
     * @since  31.03.2010
     * @return
     */
    private boolean validarAsistAudit(){
        boolean flag=false;
        String ind="";
        try{
            ind=DBInventario.validarAsistenteAuditoria(FarmaVariables.vCodCia,FarmaVariables.vCodLocal,FarmaVariables.vNuSecUsu);
            if(ind.equalsIgnoreCase("S"))flag=true;
        }catch(SQLException sql){
            sql.printStackTrace();
            FarmaUtility.showMessage(this,"ERROR en validarAsistAudit \n : " + sql.getMessage(),null);
        }
        return flag;
    }
    /*
    private void mnuPtoVenta_ancestorAdded(AncestorEvent e) {
        if (UtilityFondoSencillo.indActivoFondo()) {
            DlgHistoricoFondoSencillo dlgHistorico = 
                new DlgHistoricoFondoSencillo(this, "", true);
            dlgHistorico.setVisible(true);
        } else {
            FarmaUtility.showMessage(this, 
                                     "Aún no se encuentra habilitada está opción en su Local.", 
                                     null);
        }
    }
    */
    private void mnuFondoSencillo_actionPerformed(ActionEvent e) {
        if (UtilityFondoSencillo.indActivoFondo()) {
            //  02.06.2010
            /*
            DlgHistoricoFondoSencillo dlgHistorico = 
                new DlgHistoricoFondoSencillo(this, "", true);
            dlgHistorico.setVisible(true);
            */
            DlgListadoCajeros dlgListado = new DlgListadoCajeros(this,"",true);
            dlgListado.setVisible(true);
        } else {
            FarmaUtility.showMessage(this, 
                                     "Aún no se encuentra habilitada está opción en su Local.", 
                                     null);
        }
    }
/**
     * Se muestra remitos
     * @AUTHOR  
     * @SINCE 30.03.2010
     * */
    private void mnuRemito_actionPerformed(ActionEvent e) {
        DlgListaRemito dlgListaRem=new DlgListaRemito(this,"",true);
         dlgListaRem.setVisible(true);
    }

    /**
     * Se muestra sobres 
     * @AUTHOR  
     * @SINCE 30.03.2010
     * */
    private void mnuSobres_actionPerformed(ActionEvent e) {
        
        DlgControlSobres dlgcontrol=new DlgControlSobres(this,"",true);
         dlgcontrol.setVisible(true);
    }
    
    /**
     * Se valida el ingreso de control de sobre
     * @AUTHOR  
     * @SINCE 09.04.2010
     * */
    private boolean validaIndicadorControlSobres(){
        boolean vResultado=false;
        try{
            vResultado = DBCaja.obtieneIndicadorControlSobres();
        }catch(SQLException sql){            
            sql.printStackTrace();
            FarmaUtility.showMessage(this,"Ocurrió un error al obtener el indicador control sobre. \n"+ sql.getMessage(), null);
            vResultado=false;        
        }
        return vResultado;
    }
    
    /**
     * Se valida el ingreso de Remito
     * @AUTHOR  
     * @SINCE 09.04.2010
     * */
     private boolean isLocalProsegur(){
         String pIndCESeguridad = "";
            boolean pVisible = false;
            try {
                pVisible = DBCaja.obtieneIndicadorProsegur();

            } catch (SQLException sql) {
                pIndCESeguridad = FarmaConstants.INDICADOR_N;
                sql.printStackTrace();
                FarmaUtility.showMessage(this, 
                                         "Error al grabar forma pago pedido \n" +
                        sql.getMessage(), null);
            }

            return pVisible;
     }

/**
     * @author  
     * @since 03.06.2010
     * @param e
     */
    private void mnuCE_Prosegur_actionPerformed(ActionEvent e) {
        DlgListaRemito dlgListaRem=new DlgListaRemito(this,"",true);
         dlgListaRem.setVisible(true);
    }

/**
     * @author  
     * @since 03.06.2010
     * @param e
     */
    private void mnuCaja_ControlSobresParciales_actionPerformed(ActionEvent e) {
        DlgControlSobres dlgcontrol=new DlgControlSobres(this,"",true);
         dlgcontrol.setVisible(true);
    }

    private void this_windowClosed(WindowEvent e) {
        eliminaCodBarra();    /*JSANTIVANEZ 07.09.2010*/    
        eliminaBoletaTxt();
    }

    private void mnuPrecioCambProd_actionPerformed(ActionEvent e) {
        DlgPrecioProdCambiados dlgPrecioCambiad= new DlgPrecioProdCambiados(this,"",true);
        dlgPrecioCambiad.setVisible(true);
    }

  //*///
    
    
  private void verificaRolUsuario_sinAdmin() {
    muestraUser();
    validaOpcionesMenu(false);
    // Opciones comunes para todos los Roles
    mnuEconoFar_Administracion.setEnabled(true);
    mnuAdministracion_Usuarios.setEnabled(true);
    mnuUsuarios_CambioUsuario.setEnabled(true);
    mnuUsuarios_CambioClave.setEnabled(true);
     mnuIngTransportista.setEnabled(false);
    // 
    mnuVentas_MedidaPresion.setEnabled(false);
    mnuFondoSencillo.setVisible(false);
    
    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_VENDEDOR) ) {
        mnuEconoFar_Ventas.setEnabled(true);
        mnuVentas_GenerarPedido.setEnabled(true);
        // 
        mnuVentas_Recargas.setEnabled(true);
        mnuVentas_DeliveryAutomatico.setEnabled(true);
        
        
        mnuAdministracion_ControlHoras.setEnabled(false);
        
        // 
        mnuPedidosLocales.setEnabled(false);
        if(FarmaVariables.vEconoFar_Matriz){
            mnuPedidoAdicional.setVisible(false);
            mnuPedidoEspecial.setVisible(false);
        }
        
        // 
        mnuVentas_MedidaPresion.setEnabled(true);
        
        //  30.11.09
        mnuEconoFar_Inventario.setEnabled(true);
        
        mnuInventario_Mercaderia.setEnabled(true);
        
    }

    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_CAJERO) ) {
        
        // 
        mnuVentas_Recargas.setEnabled(true);
        
      mnuAdministracion_ControlHoras.setEnabled(false);
      mnuEconoFar_Caja.setEnabled(true);
        mnuCaja_MovimientosCaja.setEnabled(true);
          mnuCaja_AperturarCaja.setEnabled(true);
          mnuCaja_CerrarCaja.setEnabled(true);
        mnuCaja_ConfigurarCaja.setEnabled(true);
        mnuCaja_ReimpresionPedido.setEnabled(true);
        //mnuCaja_CorreccionComprobantes.setEnabled(true);
         mnuCaja_PruebaImpresora.setEnabled(true);
        mnuEconofar_CajaElectronica.setEnabled(true);
        mnuCE_CierreTurno.setEnabled(true);
         // 
         mnuPedidosLocales.setEnabled(false);
         if(FarmaVariables.vEconoFar_Matriz){
             mnuPedidoAdicional.setVisible(false);
             mnuPedidoEspecial.setVisible(false);
         }
         
    if ( FarmaVariables.vTipCaja.equalsIgnoreCase(ConstantsPtoVenta.TIP_CAJA_TRADICIONAL) )
        mnuCaja_CobrarPedido.setEnabled(true);
        // 
        // 
        mnuPedidosLocales.setEnabled(false);
        if(FarmaVariables.vEconoFar_Matriz){
            mnuPedidoAdicional.setVisible(false);
            mnuPedidoEspecial.setVisible(false);
        }
        
        // , 03.06.2010
        mnuCaja_ControlSobresParciales.setEnabled(validaIndicadorControlSobres());
    }

    /*  
     * se quito el rol admin  
     * */
    //  29.09.2010
    if(FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_CONTABILIDAD)||
        FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_LECTURA_REPORTES)
      ){
        mnuCE_Prosegur.setEnabled(isLocalProsegur());
    }
        
    
    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_AUDITORIA) ) {
      mnuEconoFar_TomaInventario.setEnabled(true); 
      mnuInventarioCiclico_Inicio.setEnabled(true);
      mnuAdministracion_ControlHoras.setEnabled(false);
      if(!FarmaVariables.vEconoFar_Matriz) {
      // MODULO INVENTARIO
      mnuEconoFar_Inventario.setEnabled(true);
        mnuInventario_GuiaEntrada.setEnabled(true);
        mnuInventario_Kardex.setEnabled(true);
        mnuInventario_Transferencias.setEnabled(true);
        mnuInventario_RecepcionProductos.setEnabled(true);
        mnuInventario_PedioReposicion.setEnabled(true);
        mnuInventario_Guias.setEnabled(true);
        mnuInventario_Ajustes.setEnabled(true);
        //Toma Inventario
        mnuTomaInventario_Tradicional.setEnabled(true);
        mnuTomaInventario_Ciclico.setEnabled(true);
        mnuTomaInventario_Historico.setEnabled(true);
        mnuTomaInVentario_ItemsXLab.setEnabled(true);
        //mnuInventario_RecepcionLocales.setEnabled(pValor);
          // 
          mnuPedidosLocales.setEnabled(true);
          mnuPedidoAdicional.setVisible(true);
          mnuPedidoEspecial.setVisible(true);
          
      } else 
      {
        mnuTomaInventario_Tradicional.setEnabled(false);
        mnuTomaInventario_Ciclico.setEnabled(true);
        mnuTomaInventario_Historico.setEnabled(true);
          // 
          mnuPedidosLocales.setEnabled(true);
          mnuPedidoAdicional.setVisible(false);
          mnuPedidoEspecial.setVisible(false);
      }
    }
  /*
  * Cambio Jquispe se agrego el rol de Asistente de Auditoria
  * 23/03/2010
  * */
    
    if ( FarmaVariables.dlgLogin.verificaRol(ConstantsPtoVenta.ROL_ASISTENTE_AUDITORIA) ) {
      mnuEconoFar_TomaInventario.setEnabled(true); 
      mnuInventarioCiclico_Inicio.setEnabled(true);
      mnuAdministracion_ControlHoras.setEnabled(false);
      if(!FarmaVariables.vEconoFar_Matriz) {
      // MODULO INVENTARIO
      mnuEconoFar_Inventario.setEnabled(true);
        mnuInventario_GuiaEntrada.setEnabled(true);
        mnuInventario_Kardex.setEnabled(true);
        mnuInventario_Transferencias.setEnabled(true);
        mnuInventario_RecepcionProductos.setEnabled(true);
        mnuInventario_PedioReposicion.setEnabled(true);
        mnuInventario_Guias.setEnabled(true);
        mnuInventario_Ajustes.setEnabled(true);
        //Toma Inventario
        mnuTomaInventario_Tradicional.setEnabled(true);
        mnuTomaInventario_Ciclico.setEnabled(true);
        mnuTomaInventario_Historico.setEnabled(true);
        mnuTomaInVentario_ItemsXLab.setEnabled(true);
        //mnuInventario_RecepcionLocales.setEnabled(pValor);
          // 
          mnuPedidosLocales.setEnabled(true);
          mnuPedidoAdicional.setVisible(true);
          mnuPedidoEspecial.setVisible(true);
          
      } else 
      {
        mnuTomaInventario_Tradicional.setEnabled(false);
        mnuTomaInventario_Ciclico.setEnabled(true);
        mnuTomaInventario_Historico.setEnabled(true);
          // 
          mnuPedidosLocales.setEnabled(true);
          mnuPedidoAdicional.setVisible(false);
          mnuPedidoEspecial.setVisible(false);
      }
    }
    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_OPERADOR_SISTEMAS) ) {
      validaOpcionesMenu(true);
        mnDigemid.setEnabled(true);
        if (UtilityFondoSencillo.indActivoFondo()) {
          mnuFondoSencillo.setVisible(true);
        }
  //if(ConstantsRecepCiega.error_du){
          if(UtilityRecepCiega.indHabDatosTransp()){
            mnuIngTransportista.setEnabled(true);
        }
      mnuInventarioCiclico_Inicio.setEnabled(true);
         // 
         mnuPedidosLocales.setEnabled(true);
         if(FarmaVariables.vEconoFar_Matriz){
             mnuPedidoAdicional.setVisible(false);
             mnuPedidoEspecial.setVisible(false);
         }
         // 
         mnuVentas_MedidaPresion.setEnabled(false);
         // 
         mnuVentas_Recargas.setEnabled(true);

        // , 03.06.2010
        mnuCaja_ControlSobresParciales.setEnabled(validaIndicadorControlSobres());
        mnuCE_Prosegur.setEnabled(isLocalProsegur());
        
        /*if(ConstantsRecepCiega.error_du){
            mnuIngTransportista.setEnabled(true);
        }*/
        //  04.03.10
        if(UtilityFondoSencillo.indActivoFondo()){
            mnuFondoSencillo.setEnabled(true);
            }else{
              mnuFondoSencillo.setEnabled(false);
            }
                    
    }

    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_INVENTARIADOR) ) {
      mnuEconoFar_TomaInventario.setEnabled(true);
        mnuTomaInventario_Tradicional.setEnabled(true);
        mnuInventario_Toma.setEnabled(true);
        mnuTomaInventario_Ciclico.setEnabled(true);
        mnuInventarioCiclico_Inicio.setEnabled(false);
        mnuInventarioCiclico_Toma.setEnabled(true);
        mnuAdministracion_ControlHoras.setEnabled(false);
        mnuPedidosLocales.setEnabled(true);
        
         // 
         mnuPedidosLocales.setEnabled(true);
         if(FarmaVariables.vEconoFar_Matriz){
             mnuPedidoAdicional.setVisible(false);
             mnuPedidoEspecial.setVisible(false);
         }
    }




    
    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_LECTURA_REPORTES) )
    {
      // MODULO INVENTARIO
      mnuEconoFar_Inventario.setEnabled(true);
        mnuInventario_GuiaEntrada.setEnabled(true);
      // MODULO ADMINISTRACION
      mnuEconoFar_Administracion.setEnabled(true);
        mnuAdministracion_Usuarios.setEnabled(false);
          mnuUsuarios_CambioUsuario.setEnabled(false);
          mnuUsuarios_CambioClave.setEnabled(false);
        mnuAdministracion_MovCaja.setEnabled(true);
          mnuMovCaja_ConsultarMov.setEnabled(true);
      // MODULO REPORTES
      mnuEconoFar_Reportes.setEnabled(true);
        mnuReportes_RegistroVentas.setEnabled(true);
        mnuReportes_VentasVendedor.setEnabled(true);
        mnuReportes_DetalleVentas.setEnabled(true);
        mnuReportes_ResumenVentaDia.setEnabled(true);
        mnuReportes_VentasProducto.setEnabled(true);
  //      mnuReportes_VentasConvenio.setEnabled(pValor);
  //      mnuReportes_VentasLinea.setEnabled(pValor);
        mnuReportes_VentasHora.setEnabled(true);
        mnuReportes_VentasDiaMes.setEnabled(true);
        mnuReportes_FaltaCero.setEnabled(true);
      // MODULO CAJA ELECTRONICA
      mnuEconofar_CajaElectronica.setEnabled(true);
        mnuCE_CierreLocal.setEnabled(true);
        mnuCE_CierreTurno.setEnabled(true);
        mnuAdministracion_ControlHoras.setEnabled(false);
    
    }

    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_LECTURA_INVENTARIO) )
    {
      // MODULO INVENTARIO
    mnuEconoFar_Inventario.setEnabled(true);
      mnuInventario_GuiaEntrada.setEnabled(true);
      mnuInventario_Kardex.setEnabled(true);
      mnuInventario_Transferencias.setEnabled(true);
      mnuInventario_PedioReposicion.setEnabled(true);
      mnuAdministracion_ControlHoras.setEnabled(false);
      //mnuInventario_Guias.setEnabled(true);
       // 
        //mnuPedidosLocales.setEnabled(true);
    }
    /**Diego Ubilluz
     * Rol: Supervisor Ventas
     * @author:  
     * @since: 11.06.2007
     */
    if ( FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_SUPERVISOR_VENTAS) )
    {
       mnuEconoFar_Ventas.setEnabled(true);
       mnuVentas_DeliveryAutomatico.setEnabled(true);
       
       mnuEconoFar_Caja.setEnabled(true);
       mnuCaja_CorreccionComprobantes.setEnabled(true);
       
       mnuEconoFar_Inventario.setEnabled(true);
       mnuInventario_GuiaEntrada.setEnabled(true);
       mnuInventario_Kardex.setEnabled(true);
       mnuInventario_Transferencias.setEnabled(true);
       mnuInventario_PedioReposicion.setEnabled(true);
       mnuInventario_Ajustes.setEnabled(true);
       
       mnuInventario_RecepcionLocales.setEnabled(true);
       
       mnuEconoFar_Administracion.setEnabled(true);
       mnuAdministracion_MovCaja.setEnabled(true);
       mnuMovCaja_ConsultarMov.setEnabled(true);
       
       mnuEconoFar_Reportes.setEnabled(true);
       mnuReportes_RegistroVentas.setEnabled(true);
       mnuReportes_VentasVendedor.setEnabled(true);
       mnuReportes_DetalleVentas.setEnabled(true);
       mnuReportes_ResumenVentaDia.setEnabled(true);
       mnuReportes_VentasProducto.setEnabled(true);
       mnuReportes_VentasHora.setEnabled(true);
       mnuReportes_VentasDiaMes.setEnabled(true);
       mnuReportes_FaltaCero.setEnabled(true);
       mnuReportes_ProductosABC.setEnabled(true);
       mnuReportes_UnidVtaLocal.setEnabled(true);
       mnuReportes_ProdSinVtaNDias.setEnabled(true);
       
       mnuEconofar_CajaElectronica.setEnabled(true);
       mnuCE_CierreLocal.setEnabled(true);
       
       //agregado 
       //27.11.2007   modificacion
       mnuAdministracion_ControlHoras.setEnabled(true);
         // 
       /*  mnuPedidosLocales.setEnabled(true);
         if(FarmaVariables.vEconoFar_Matriz){
             mnuPedidoAdicional.setVisible(false);
             mnuPedidoEspecial.setVisible(false);
         }*/
    }
    
    
  }
}
