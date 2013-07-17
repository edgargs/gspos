package mifarma.ptoventa.inventariodiario;

import com.gs.mifarma.componentes.*;

import com.gs.mifarma.componentes.JButtonFunction;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;
import com.gs.mifarma.componentes.UpperCaseDocument;

import java.awt.*;
import java.awt.Label;
import java.awt.event.*;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.*;

import javax.swing.JButton;
import javax.swing.JLabel;

import mifarma.common.*;

import mifarma.ptoventa.*;
import mifarma.ptoventa.inventariodiario.reference.ConstantsInvDiario;
import mifarma.ptoventa.inventariodiario.reference.DBInvDiario;
import mifarma.ptoventa.inventariodiario.reference.VariablesInvDiario;
import mifarma.ptoventa.reference.*;
import mifarma.ptoventa.tomainventario.reference.*;

public class DlgIngresoTrabajadores extends JDialog {

    FarmaTableModel tableModelListaTrabajadores;
    Frame myParentFrame;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanelWhite jContentPane = new JPanelWhite();
    private JPanelHeader jPanelHeader1 = new JPanelHeader();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblListaTrabajadores = new JTable();
    private JButtonLabel btnRelacionProductos = new JButtonLabel();
    private JButtonLabel btnProductos = new JButtonLabel();
    private JTextFieldSanSerif txtProductos = new JTextFieldSanSerif();
    private JLabelFunction lblEscape = new JLabelFunction();
    private JPanelTitle jPanelTitle2 = new JPanelTitle();
    private JButtonLabel lblTotal = new JButtonLabel();
    private JLabelFunction lblF8 = new JLabelFunction();
    private UpperCaseDocument upperCaseDocument1 = new UpperCaseDocument();
    private JLabel lblTotalMonto = new JLabel();
    private JLabel jLabel1 = new JLabel();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JLabelFunction jLabelFunction2 = new JLabelFunction();
    private JLabelFunction jLabelFunction3 = new JLabelFunction();
    private JLabelFunction jLabelFunction4 = new JLabelFunction();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JLabelWhite lblDiferencia = new JLabelWhite();
    private JLabelFunction jLabelFunction5 = new JLabelFunction();

    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgIngresoTrabajadores() {
        this(null, "", false);
    }

    public DlgIngresoTrabajadores(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************

    private void jbInit() throws Exception {
        this.setSize(new Dimension(571, 410));
        this.getContentPane().setLayout(borderLayout1);
        this.setTitle("Listado de Trabajadores");
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }
                });
        jPanelHeader1.setBounds(new Rectangle(10, 10, 545, 45));
        jPanelHeader1.setLayout(null);
        jPanelTitle1.setBounds(new Rectangle(10, 55, 545, 25));
        jPanelTitle1.setLayout(null);
        jScrollPane1.setBounds(new Rectangle(10, 80, 545, 215));
        
        btnRelacionProductos.setText("Listado a Trabajadores Responsables");
        btnRelacionProductos.setBounds(new Rectangle(10, 0, 215, 25));
        btnRelacionProductos.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnRelacionProductos_actionPerformed(e);
                    }
                });
        btnProductos.setText("Trabajador");
        btnProductos.setMnemonic('p');
        btnProductos.setBounds(new Rectangle(15, 10, 65, 20));
        btnProductos.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnProductos_actionPerformed(e);
                    }
                });
        txtProductos.setBounds(new Rectangle(80, 10, 310, 25));
        txtProductos.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtProductos_keyPressed(e);
                    }

                    public void keyReleased(KeyEvent e) {
                        txtProductos_keyReleased(e);
                    }
                });
        lblEscape.setBounds(new Rectangle(445, 355, 110, 20));
        lblEscape.setText("[ ESCAPE ] Cerrar");
        jPanelTitle2.setBounds(new Rectangle(10, 295, 545, 25));
        jPanelTitle2.setLayout(null);
        lblTotal.setBounds(new Rectangle(560, 0, 95, 25));
        lblTotal.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnRelacionProductos_actionPerformed(e);
                    }
                });
        lblF8.setText("[ F8 ] Exportar a Excel");
        lblF8.setBounds(new Rectangle(405, 365, 170, 20));
        lblF8.setVisible(false);
        lblTotalMonto.setBounds(new Rectangle(425, 0, 90, 25));
        lblTotalMonto.setForeground(Color.white);
        lblTotalMonto.setFont(new Font("Dialog", 1, 12));
        jLabel1.setText("Total (S/.):");
        jLabel1.setBounds(new Rectangle(355, 0, 60, 25));
        jLabel1.setForeground(Color.white);
        jLabel1.setFont(new Font("Dialog", 1, 12));
        jLabelFunction1.setBounds(new Rectangle(330, 355, 105, 20));
        jLabelFunction1.setText("[ F11 ] Aceptar");
        jLabelFunction2.setBounds(new Rectangle(200, 330, 100, 20));
        jLabelFunction2.setText("[ F3 ] Agregar");
        jLabelFunction3.setBounds(new Rectangle(310, 330, 115, 20));
        jLabelFunction3.setText("[ F4 ] Modificar");
        jLabelFunction4.setBounds(new Rectangle(75, 330, 110, 20));
        jLabelFunction4.setText("[ Enter ] Buscar");
        jLabel2.setBounds(new Rectangle(350, 0, 90, 25));
        jLabel2.setText("Monto objetivo :");
        jLabel2.setFont(new Font("Dialog", 1, 12));
        jLabel2.setForeground(Color.white);
        jLabel3.setBounds(new Rectangle(440, 0, 95, 25));
        jLabel3.setFont(new Font("Dialog", 1, 13));
        jLabel3.setForeground(Color.white);
        jLabelWhite1.setText("Faltan S/.");
        jLabelWhite1.setBounds(new Rectangle(35, 0, 55, 25));
        lblDiferencia.setText("0.00");
        lblDiferencia.setBounds(new Rectangle(90, 0, 70, 25));
        jLabelFunction5.setBounds(new Rectangle(435, 330, 120, 20));
        jLabelFunction5.setText("[ F5 ] Eliminar");
        jContentPane.add(jLabelFunction5, null);
        jContentPane.add(jLabelFunction4, null);
        jContentPane.add(jLabelFunction2, null);
        jContentPane.add(jLabelFunction1, null);
        jContentPane.add(lblF8, null);
        jPanelTitle2.add(lblDiferencia, null);
        jPanelTitle2.add(jLabelWhite1, null);
        jPanelTitle2.add(jLabel1, null);
        jPanelTitle2.add(lblTotalMonto, null);
        jPanelTitle2.add(lblTotal, null);
        jContentPane.add(jPanelTitle2, null);
        jScrollPane1.getViewport().add(tblListaTrabajadores, null);
        jContentPane.add(jScrollPane1, null);
        jPanelTitle1.add(jLabel3, null);
        jPanelTitle1.add(jLabel2, null);
        jPanelTitle1.add(btnRelacionProductos, null);
        jContentPane.add(jPanelTitle1, null);
        jContentPane.add(jPanelHeader1, null);
        jContentPane.add(lblEscape, null);
        jContentPane.add(jLabelFunction3, null);
        jPanelHeader1.add(txtProductos, null);
        jPanelHeader1.add(btnProductos, null);
        this.getContentPane().add(jContentPane, BorderLayout.CENTER);
    }

    // **************************************************************************
    // Método "initialize()"
    // **************************************************************************

    private void initialize() {
        FarmaVariables.vAceptar = false;
        if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_AUDITORIA) && 
            FarmaVariables.vEconoFar_Matriz)
            lblF8.setVisible(true);
        initTableListaTrabajadores();
    }

    // **************************************************************************
    // Métodos de inicialización
    // **************************************************************************

    private void initTableListaTrabajadores() {
        tableModelListaTrabajadores = 
                new FarmaTableModel(ConstantsInvDiario.columnsListaTrabajadores, 
                                    ConstantsInvDiario.defaultValuesListaTrabajadores,
                                    0);

        FarmaUtility.initSimpleList(tblListaTrabajadores, 
                                    tableModelListaTrabajadores, 
                                    ConstantsInvDiario.columnsListaTrabajadores);
        
    }

    // **************************************************************************
    // Metodos de eventos   
    // **************************************************************************

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtProductos);
        limpiaVariables();
        
        if(VariablesInvDiario.vListaTrabParaDescuento.size()>0){
           tableModelListaTrabajadores.data = VariablesInvDiario.vListaTrabParaDescuento;     
            FarmaUtility.ordenar(tblListaTrabajadores, 
                                 tableModelListaTrabajadores, 2, 
                                 FarmaConstants.ORDEN_ASCENDENTE);
        }
        
        FarmaUtility.setearTextoDeBusqueda(tblListaTrabajadores, 
                                           txtProductos, 
                                           1);        
        //jLabel2.setVisible(false);
        //jLabel3.setVisible(false);
        jLabel3.setText(" ");
        jLabel2.setVisible(true);
        jLabel3.setVisible(true);
        if( VariablesInvDiario.vAccion.equalsIgnoreCase("A")){
            jLabel3.setText(" "+VariablesInvDiario.dTotalProds);
            lblDiferencia.setText(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(jLabel3.getText().trim())- FarmaUtility.getDecimalNumber(lblTotalMonto.getText().trim())));
            System.out.println(" 1. monto objetivo supuesto jlbl3: " + jLabel3.getText());
        }
        else{
            jLabel3.setText(VariablesInvDiario.vTotalPedido);
            double pParcial=0,pTotal=0;
            for(int i=0;i<tableModelListaTrabajadores.data.size();i++){
                pParcial= FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(tableModelListaTrabajadores.data,i,3));
                pTotal += pParcial;
            }
            System.out.println(" 2. monto objetivo supuesto jlbl3: " + jLabel3.getText());
            lblDiferencia.setText(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(jLabel3.getText().trim())- FarmaUtility.getDecimalNumber(lblTotalMonto.getText().trim())));
            //lblTotalMonto.setText(FarmaUtility.formatNumber(pTotal));            
        }
    
    }
    private void cerrarVentana(boolean pAceptar)
    {
      FarmaVariables.vAceptar = pAceptar;
      this.setVisible(false);
      this.dispose();
    }
    private void txtProductos_keyPressed(KeyEvent e) {
        FarmaGridUtils.aceptarTeclaPresionada(e, 
                                              tblListaTrabajadores, 
                                              txtProductos, 2);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    if(validaDNI(txtProductos.getText().trim()))
                       buscaTrabajador(txtProductos.getText().trim());
                }
        chkKeyPressed(e);
    }

    private void chkKeyPressed(KeyEvent e) {
        getAcumulado();
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.setVisible(false);
            VariablesInvDiario.vIndSeleccionado = false;
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            cargaSeleccionPersonal();
        }
        else if(e.getKeyCode() == KeyEvent.VK_F4){
            cambiarMonto();            
        }
        else if(e.getKeyCode() == KeyEvent.VK_F5){
            eliminaTrabajador();
        }
        else if(e.getKeyCode() == KeyEvent.VK_F11){
            concluyeSeleccion();
        }
    }

    private void btnProductos_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtProductos);
    }

    private void txtProductos_keyReleased(KeyEvent e) {
        chkKeyReleased(e);
    }

    private void chkKeyReleased(KeyEvent e) {
        FarmaGridUtils.buscarDescripcion(e, tblListaTrabajadores, 
                                         txtProductos, 2);
        getAcumulado();
        
    }

    // **************************************************************************
    // Metodos de lógica de negocio
    // **************************************************************************
    private void btnRelacionProductos_actionPerformed(ActionEvent e)
    {
        FarmaUtility.moveFocus(tblListaTrabajadores);
    }
    
    public void cambiarMonto(){
        //Cambiara la el monto
        int vFila = tblListaTrabajadores.getSelectedRow();

       if(vFila>-1){
        VariablesInvDiario.vDNI = FarmaUtility.getValueFieldArrayList(tableModelListaTrabajadores.data,vFila,0);
        VariablesInvDiario.vCodRRHH = FarmaUtility.getValueFieldArrayList(tableModelListaTrabajadores.data,vFila,1); 
        VariablesInvDiario.vNombreCompleto = FarmaUtility.getValueFieldArrayList(tableModelListaTrabajadores.data,vFila,2);
        VariablesInvDiario.vCodigoTrab= FarmaUtility.getValueFieldArrayList(tableModelListaTrabajadores.data,vFila,3);
        VariablesInvDiario.vMontoIngresado = FarmaUtility.ShowInput(this,
                                            "DNI: " +VariablesInvDiario.vDNI+"\n"+
                                            "" +VariablesInvDiario.vNombreCompleto+"\n"+
                                            "Ingrese el nuevo monto en soles");
        double dMonto = FarmaUtility.getDecimalNumber(VariablesInvDiario.vMontoIngresado);
        if(dMonto>0)
        {
            tableModelListaTrabajadores.setValueAt(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesInvDiario.vMontoIngresado)),
                                                   vFila,3);
            tblListaTrabajadores.repaint();
            System.out.println(tableModelListaTrabajadores.data);
            if (tableModelListaTrabajadores.getRowCount() > 0) {
              FarmaGridUtils.showCell(tblListaTrabajadores, 0,0);
            }
        }
        else
        {
          VariablesInvDiario.vNombreCompleto = "";
          VariablesInvDiario.vCodRRHH = "";
          VariablesInvDiario.vCodigoTrab= "";
          VariablesInvDiario.vMontoIngresado = "";
          VariablesInvDiario.vDNI = "";
          FarmaUtility.showMessage(this,"Ingrese un monto correcto.\n"+
                                          "Debe ser un número mayor que cero.",txtProductos);
        }
            
    }  
    }
    
    public void cargaSeleccionPersonal()
    {
        VariablesInvDiario.vListaTrabSeleccionados = tableModelListaTrabajadores.data;
        DlgListaTrabajadores dlgListaTrab = 
            new DlgListaTrabajadores(myParentFrame, "", true);
        dlgListaTrab.setVisible(true);
        if (FarmaVariables.vAceptar) {
            FarmaVariables.vAceptar = false;
            ArrayList vltrab =  new ArrayList();
            if(tableModelListaTrabajadores.data.size()>0)
               vltrab = tableModelListaTrabajadores.data;
            boolean agrega = true;
            for(int i=0;i<vltrab.size();i++)
            {
                if(FarmaUtility.getValueFieldArrayList(vltrab,i,1).trim().equalsIgnoreCase(VariablesInvDiario.vCodRRHH.trim()))
                {
                   agrega = false;
                   FarmaUtility.showMessage(this,"No puede repetir Trabajadores.",txtProductos); 
                }
                
            }
            
            if(agrega)
            {
                vltrab = new ArrayList();
                vltrab.add(VariablesInvDiario.vDNI);
                vltrab.add(VariablesInvDiario.vCodRRHH);
                vltrab.add(VariablesInvDiario.vNombreCompleto);
                vltrab.add(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesInvDiario.vMontoIngresado)));
                vltrab.add(VariablesInvDiario.vCodigoTrab);
                tableModelListaTrabajadores.insertRow(vltrab);
                FarmaUtility.ordenar(tblListaTrabajadores, 
                                     tableModelListaTrabajadores, 2, 
                                     FarmaConstants.ORDEN_ASCENDENTE);
            }
            
        }
    }
    
    public void eliminaTrabajador()
    {
        if(tblListaTrabajadores.getRowCount()>0){
            int vFila = tblListaTrabajadores.getSelectedRow();
            if(FarmaUtility.rptaConfirmDialog(this, "¿Seguro de quitar el descuento al personal?"))
            {
                tableModelListaTrabajadores.deleteRow(vFila);
                tblListaTrabajadores.repaint();
                if (tableModelListaTrabajadores.getRowCount() > 0) {
                  FarmaGridUtils.showCell(tblListaTrabajadores, 0,0);
                }
            }
        }
    }
    
    
    public boolean validaDNI(String pDNI_in){
        int pDNI = 0;
        if(pDNI_in.trim().length()<8)
            return false;
        try {
            pDNI = Integer.parseInt(pDNI_in);
            return true;
        } catch (Exception e) {
                return false;
        }
    }
    public void buscaTrabajador(String pDNI) {
        ArrayList vltrab =  new ArrayList();
        if(tableModelListaTrabajadores.data.size()>0)
           vltrab = (ArrayList)tableModelListaTrabajadores.data.clone();
        
        boolean pExiste = false;
        for(int i=0;i<vltrab.size();i++)
        {
            if(FarmaUtility.getValueFieldArrayList(vltrab,i,0).trim().equalsIgnoreCase(pDNI.trim()))
            {
               tblListaTrabajadores.setSelectionMode(i);
                pExiste = true;
            }
            
        }
        
        if(!pExiste){
            System.out.println("Buscando al trabajador... " + pDNI);
            String pDatos  ="";
            String[] pListaDatos = null;
            try {
                pDatos = DBInvDiario.getDatosTrabajador(pDNI);
                pListaDatos = pDatos.split("Ã");
                VariablesInvDiario.vDNI = pListaDatos[0].toString().trim();
                VariablesInvDiario.vNombreCompleto = pListaDatos[1].toString().trim();
                VariablesInvDiario.vCodRRHH = pListaDatos[2].toString().trim();
                VariablesInvDiario.vCodigoTrab= pListaDatos[3].toString().trim();
                System.out.println("VariablesInvDiario.vDNI: " + VariablesInvDiario.vDNI);
                System.out.println("VariablesInvDiario.vCodigoTrab: " + VariablesInvDiario.vCodigoTrab);
                System.out.println("VariablesInvDiario.vNombreCompleto: " + VariablesInvDiario.vNombreCompleto);
                System.out.println("VariablesInvDiario.vCodRRHH: " + VariablesInvDiario.vCodRRHH);
                System.out.println("VariablesInvDiario.vMontoIngresado: " + VariablesInvDiario.vMontoIngresado);
                
                VariablesInvDiario.vMontoIngresado = FarmaUtility.ShowInput(this,
                                                    "DNI: " +VariablesInvDiario.vDNI+"\n"+
                                                    "" +VariablesInvDiario.vNombreCompleto+"\n"+
                                                    "¿Ingrese el monto en soles?");
                double dMonto = FarmaUtility.getDecimalNumber(VariablesInvDiario.vMontoIngresado);
                if(dMonto>0)
                {

                    vltrab =  new ArrayList();
                    vltrab.add(VariablesInvDiario.vDNI);
                    vltrab.add(VariablesInvDiario.vCodRRHH);
                    vltrab.add(VariablesInvDiario.vNombreCompleto);
                    vltrab.add(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesInvDiario.vMontoIngresado)));
                    vltrab.add(VariablesInvDiario.vCodigoTrab);
                    tableModelListaTrabajadores.insertRow(vltrab);
                    FarmaUtility.ordenar(tblListaTrabajadores, 
                                         tableModelListaTrabajadores,2, 
                                         FarmaConstants.ORDEN_ASCENDENTE);                    
                    
                }
                else{
                    VariablesInvDiario.vNombreCompleto = "";
                    VariablesInvDiario.vCodRRHH = "";
                    VariablesInvDiario.vCodigoTrab= "";
                    VariablesInvDiario.vMontoIngresado = "";
                    VariablesInvDiario.vDNI = "";
                    FarmaUtility.showMessage(this,"Ingrese un monto correcto.\n"+
                                                  "Debe ser un número mayor que cero.",txtProductos);
                }


                
            } catch (SQLException e) {
              e.printStackTrace();
              FarmaUtility.showMessage(this,"Ocurrió un error al buscar al trabajador.\n"+
                                            e.getMessage(),txtProductos); 
            }
        }
        
        
    }

    public void concluyeSeleccion(){
        VariablesInvDiario.vListaTrabParaDescuento = (ArrayList)tableModelListaTrabajadores.data.clone();
        limpiaVariables();
        System.err.println("Trabajadores a Descontar: " +VariablesInvDiario.vListaTrabParaDescuento);
        
        if( VariablesInvDiario.vAccion.equalsIgnoreCase("A")){
          
            //Se guardar total ingresado para pemitir el ajuste
            VariablesInvDiario.dTotalDescTrab=FarmaUtility.getDecimalNumber(lblTotalMonto.getText().trim());
            System.err.println("Diferencia de montos al generar ajustes." +VariablesInvDiario.dTotalProds*-1+"/"+
                                   VariablesInvDiario.dTotalDescTrab);
            if((VariablesInvDiario.dTotalProds*-1)!=VariablesInvDiario.dTotalDescTrab){
                 System.err.println("ERROR: diferencia de montos al generar ajustes." +VariablesInvDiario.dTotalProds*-1+"/"+
                                        VariablesInvDiario.dTotalDescTrab);
                FarmaUtility.showMessage(this,"El monto total ingresado no coincide con el monto total "+
                                                  " de los productos. Verifique !!!",txtProductos); 
             }else{
                 if(FarmaUtility.rptaConfirmDialog(this,"Esta seguro de ingresar los trabajadores?")){
                   System.err.println("Trabajadores a Descontar: " +VariablesInvDiario.vListaTrabParaDescuento);
                     cerrarVentana(true);
                 }
             }
        }else
            cerrarVentana(true);
        
    }
    
    public void limpiaVariables(){
        VariablesInvDiario.vDNI = "";
        VariablesInvDiario.vCodigoTrab = "";
        VariablesInvDiario.vCodRRHH = "";
        VariablesInvDiario.vNombreCompleto = "";
        VariablesInvDiario.vMontoIngresado = "";
        VariablesInvDiario.vListaTrabSeleccionados = new ArrayList();
    }
    
    public void getAcumulado(){
        double pParcial=0,pTotal=0;
        for(int i=0;i<tableModelListaTrabajadores.data.size();i++){
            pParcial= FarmaUtility.getDecimalNumber(FarmaUtility.getValueFieldArrayList(tableModelListaTrabajadores.data,i,3));
            pTotal += pParcial;
        }
        lblTotalMonto.setText(FarmaUtility.formatNumber(pTotal));
        lblDiferencia.setText(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(jLabel3.getText().trim())+ FarmaUtility.getDecimalNumber(lblTotalMonto.getText().trim())));
        //lblDiferencia.setText(FarmaUtility.formatNumber(FarmaUtility.getDecimalNumber(VariablesInvDiario.vTotalPedido)- pTotal));
        
        
    }
    
    
}