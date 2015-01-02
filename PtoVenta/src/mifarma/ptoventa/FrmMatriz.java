package mifarma.ptoventa;

import com.gs.mifarma.componentes.JPanelWhite;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import mifarma.common.FarmaVariables;

import mifarma.ptoventa.matriz.mantenimientos.productos.DlgListaLocalesAdicionales;
import mifarma.ptoventa.matriz.mantenimientos.productos.DlgListaProductosDist;
import mifarma.ptoventa.matriz.mantenimientos.productos.DlgListadoPedidoEspecial;
import mifarma.ptoventa.matriz.mantenimientos.productos.references.VariablesProducto;
import mifarma.ptoventa.retiro.DlgRetiros;
import mifarma.ptoventa.retiro.DlgRpteRetXTipo;
import mifarma.ptoventa.retiro.reference.VariablesRetiro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*Librerías de Eventos*/

/*Librerías de Componentes de GUI*/


/*Librerías del Framework MiFARMA*/

/*Librerías de Dialog*/


/**
 * Copyright (c) 2005 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : FrmMatriz.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LREQUE      13.02.2005   Creación<br>
 * <br>
 * @author Luis Reque Orellana<br>
 * @version 1.0<br>
 *
 */

public class FrmMatriz extends JFrame 
{
    private static final Logger log = LoggerFactory.getLogger(FrmMatriz.class); 
    
    String tituloBaseFrame = "Matriz - FarmaVenta v.13 - 24/05/2010";  
    BorderLayout borderLayout1 = new BorderLayout();
    
    private JPanelWhite pnlBlanco = new JPanelWhite();
    private JMenuBar mnuMatriz = new JMenuBar();
    
    /*Módulo Mantenimiento*/
    private JMenu mnuMatriz_Mantenimiento = new JMenu();
    
    //Agregado por DVELIZ 19.09.08
    private JMenu mnuMatriz_PVM = new JMenu();
    
    //Items del Módulo Mantenimiento
    private JMenuItem mnuMantenimiento_ProductosNuevos = new JMenuItem();
    private JMenuItem mnuMantenimiento_ProductosAdicionales = new JMenuItem();
    
    //Agregado por DVELIZ 19.09.08
    private JMenuItem mnuMantenimiento_ProductosEspeciales = new JMenuItem();
    
    /*Módulo Reportes*/
    
    /*Módulo Salir*/
    private JMenu mnuMatriz_Salir = new JMenu();
    //Items del Módulo Salir
    private JMenuItem mnuSalir_SalirSistema = new JMenuItem();
    private JMenu mnuMatriz_Retiro = new JMenu();
    private JMenuItem mnuRetiroVBAll = new JMenuItem();
    private JMenuItem mnuRetiroVB_RpteXTipo = new JMenuItem();
    
    public FrmMatriz()
    {
        try
        {
            jbInit();
            this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            //this.setTitle("jaajja");
            
            String addon = " Usu.Actual : ";
            addon = addon + FarmaVariables.vIdUsu;
            this.setTitle(tituloBaseFrame + " - Local : " + FarmaVariables.vDescCortaLocal + " / " + addon + " /  IP : " + FarmaVariables.vIpPc);
        }
        catch(Exception e)
        {   log.error("",e);
        }
    }
        
    private void jbInit() throws Exception
    {
        /*Propiedades del Frame*/
        this.setSize(new Dimension(800,600));
        this.setJMenuBar(mnuMatriz);
        // this.setTitle("Matriz - FarmaVenta v5.4 - 01/10/2009");
    
        this.getContentPane().setLayout(borderLayout1);
        this.setFont(new Font("SansSerif", 0, 11));
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {   salir(e);
            }
        });
        this.getContentPane().add(pnlBlanco, BorderLayout.CENTER);
        
        /*MENÚ MANTENIMIENTOS*/
        
        /*Propiedades del Menú Mantenimientos*/
        mnuMatriz_Mantenimiento.setText("Mantenimientos");
        mnuMatriz_Mantenimiento.setFont(new Font("SansSerif", 0, 11));
        //mnuMatriz_Mantenimiento.add(mnuMantenimiento_ProductosAdicionales);
        mnuMatriz_Mantenimiento.setMnemonic('m');
        
        //Agregado por DVELIZ 19.09.08
        //mnuMatriz_PVM.setEnabled(false);
        mnuMatriz_PVM.setText("Promedio de Venta Mensual (PVM)");
        mnuMatriz_PVM.setFont(new Font("SansSerif", 0, 11));
        mnuMatriz_PVM.add(mnuMantenimiento_ProductosNuevos);
        mnuMatriz_PVM.add(mnuMantenimiento_ProductosAdicionales);
        mnuMatriz_PVM.add(mnuMantenimiento_ProductosEspeciales);
        mnuMatriz_PVM.setMnemonic('p');
        
        /*MenuItems de Menú Mantenimientos*/
        mnuMantenimiento_ProductosNuevos.setText("Pedido Distribución");
        mnuMantenimiento_ProductosNuevos.setFont(new Font("SansSerif", 0, 11));
        mnuMantenimiento_ProductosNuevos.setMnemonic('n');
        mnuMantenimiento_ProductosNuevos.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {   mnuMantenimiento_ProductosNuevos_actionPerformed(e);
            }
        });
        
        mnuMantenimiento_ProductosAdicionales.setText("Pedido Adicional");    
        mnuMantenimiento_ProductosAdicionales.setFont(new Font("SansSerif", 0, 11));
        mnuMantenimiento_ProductosAdicionales.setMnemonic('a');
        mnuMantenimiento_ProductosAdicionales.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {   mnuMantenimiento_ProductosAdicionales_actionPerformed(e);
            }
        });
        
        //Agregado por DVELIZ 19.09.08
        mnuMantenimiento_ProductosEspeciales.setText("Pedido Especial");    
        mnuMantenimiento_ProductosEspeciales.setFont(new Font("SansSerif", 0, 11));
        mnuMantenimiento_ProductosEspeciales.setMnemonic('e');
        mnuMantenimiento_ProductosEspeciales.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {   mnuMantenimiento_ProductosEspeciales_actionPerformed(e);
            }
        });
        /*MENÚ SALIR*/
        
        /*Propiedades del Menú Salir*/
        mnuMatriz_Salir.setText("Salir");
        mnuMatriz_Salir.setFont(new Font("SansSerif", 0, 11));
        mnuMatriz_Salir.setMnemonic('s');
        
        /*MenuItems de Menú Salir*/
        mnuMatriz_Salir.setBounds(new Rectangle(90, 0, 35, 23));
        mnuSalir_SalirSistema.setText("Salir del Sistema");
        mnuSalir_SalirSistema.setFont(new Font("SansSerif", 0, 11));
        mnuSalir_SalirSistema.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {   mnuSalir_SalirSistema_actionPerformed(e);
            }
        });
        /*Adiciona los menú a la barra de Menú*/
        mnuMatriz_Retiro.setText("Retiro de VB");
        mnuMatriz_Retiro.setBounds(new Rectangle(90, 0, 35, 23));
        mnuMatriz_Retiro.setFont(new Font("Dialog", 0, 11));
        mnuMatriz_Retiro.setMnemonic('R');
        mnuRetiroVBAll.setText("Retiros de VB");
        mnuRetiroVBAll.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {   mnuRetiroVB_Turno_actionPerformed(e);
            }
        });
        mnuRetiroVB_RpteXTipo.setText("Rpte de Retiros por Tipo");
        mnuRetiroVB_RpteXTipo.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        mnuRetiroVB_RpteXTipo_actionPerformed(e);
        }
        });
        mnuMatriz_Salir.add(mnuSalir_SalirSistema);
        mnuMatriz_Mantenimiento.add(mnuMatriz_PVM);
        mnuMatriz.add(mnuMatriz_Mantenimiento);
        mnuMatriz.add(mnuMatriz_Salir);
        mnuMatriz_Retiro.add(mnuRetiroVBAll);
        mnuMatriz_Retiro.add(mnuRetiroVB_RpteXTipo);
        mnuMatriz.add(mnuMatriz_Retiro);
    }
    
    /*Métodos de Manejo de Eventos*/
    
    private void mnuSalir_SalirSistema_actionPerformed(ActionEvent e)
    {   salirSistema();
    }
    
    private void salir(WindowEvent e)
    {   salirSistema();
    }
    
    private void salirSistema()
    {
        /*this.dispose();
        System.exit(0);*/
        if(FarmaVariables.vEconoFar_Matriz)
            this.dispose();
        else
            System.exit(0);
    }
    
    private void mnuMantenimiento_ProductosNuevos_actionPerformed(ActionEvent e)
    {
        //VariablesProducto.vOpcion = VariablesProducto.vOpcion_Prod_Nuevos;
        VariablesProducto.vIndNuevo= "";
        /* DlgListaProductos dlgListaProd = new DlgListaProductos(this,"",true);
        dlgListaProd.setVisible(true);*/
        
        /*DlgListadoPedidosDist dlgListadoPedidosDist = new DlgListadoPedidosDist(this,"",true);
        dlgListadoPedidosDist.setVisible(true);*/
        
        /**
        * @since 11.09.2008
        * @author JCORTEZ
        * */
        VariablesProducto.vTipoParametros=true;
        DlgListaProductosDist dlgproductos=new DlgListaProductosDist(this,"",true);
        dlgproductos.setVisible(true);
    }
    
    private void mnuMantenimiento_ProductosAdicionales_actionPerformed(ActionEvent e)
    {   DlgListaLocalesAdicionales dlgListaLocales = new DlgListaLocalesAdicionales(this,"",true);
        dlgListaLocales.setVisible(true);
    }
    
    private void mnuMantenimiento_ProductosEspeciales_actionPerformed(ActionEvent e)
    {   DlgListadoPedidoEspecial dlgListaPedido = new DlgListadoPedidoEspecial(this, "", true);
        dlgListaPedido.setVisible(true);
    }
    
    private void mnuRetiroVB_Turno_actionPerformed(ActionEvent e)
    {   DlgRetiros dlgretiro=new DlgRetiros(this,"",true);
        dlgretiro.setVisible(true);        
    }
    
    private void mnuRetiroVB_RpteXTipo_actionPerformed(ActionEvent e)
    {   DlgRpteRetXTipo objRpte=new DlgRpteRetXTipo(this,"",true);
        objRpte.setVisible(true);
    }
}