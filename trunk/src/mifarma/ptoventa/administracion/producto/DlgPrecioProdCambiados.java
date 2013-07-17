package mifarma.ptoventa.administracion.producto;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.Frame;

import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;

import java.awt.event.WindowEvent;

import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.administracion.producto.reference.ConstantsPrecios;
import mifarma.ptoventa.administracion.producto.reference.DBPrecios;
import mifarma.ptoventa.administracion.usuarios.reference.ConstantsUsuarios;
import mifarma.ptoventa.administracion.usuarios.reference.DBUsuarios;
import mifarma.ptoventa.administracion.usuarios.reference.VariablesUsuarios;
import mifarma.ptoventa.caja.reference.UtilityCaja;

import java.math.*;

import mifarma.ptoventa.administracion.producto.reference.VariablesPrecios;
import mifarma.ptoventa.reportes.reference.DBReportes;
import mifarma.ptoventa.reportes.reference.VariablesReporte;


public class DlgPrecioProdCambiados extends JDialog {
    private Frame myParentFrame;
    FarmaTableModel tableModel;
    
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JScrollPane scrListaPrecProdCamb = new JScrollPane();
    private JLabelFunction lblEsc = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JPanelTitle pnlHeaderPrec = new JPanelTitle();
    private JTable tblListaPrecProdCamb = new JTable();
    private JTextFieldSanSerif txtPrecios = new JTextFieldSanSerif();
    private JPanelTitle jPanelTitle1 = new JPanelTitle();
    private JButtonLabel jButtonLabel1 = new JButtonLabel();
    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JTextFieldSanSerif txtFechaInicio = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaFin = new JTextFieldSanSerif();
    private JButtonLabel jButtonLabel2 = new JButtonLabel();
    private JButtonLabel jButtonLabel3 = new JButtonLabel();
    private JButton btnBuscar = new JButton();
    private JButtonLabel jButtonLabel4 = new JButtonLabel();

    public DlgPrecioProdCambiados() {
        this(null, "", false);
    }

    public DlgPrecioProdCambiados(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void initialize()
    {
      FarmaVariables.vAceptar = false;
      initTable();  
    }
    private void initTable()
    {
      tableModel = new FarmaTableModel(ConstantsPrecios.columnsListaPrecios, ConstantsPrecios.defaultValuesListaPrecios,0);
      FarmaUtility.initSimpleList(tblListaPrecProdCamb, tableModel,ConstantsPrecios.columnsListaPrecios);
        //permite que no se muevan las columnas de Jtable
        tblListaPrecProdCamb.getTableHeader().setReorderingAllowed(false);
        //permite que no se cambien el tama�o de la columna del Jtable
        tblListaPrecProdCamb.getTableHeader().setResizingAllowed(false);
      cargaListaPrecioProdCamb();
    }
    private void cargaListaPrecioProdCamb()
    {
      try
      {
        DBPrecios.obtieneListaPrecProdCamb(tableModel);

        if (tblListaPrecProdCamb.getRowCount() > 0)
        {
          FarmaUtility.ordenar(tblListaPrecProdCamb, tableModel, 
                               2, FarmaConstants.ORDEN_ASCENDENTE);
          //FarmaUtility.setearPrimerRegistro(tblListaPrecProdCamb,txtPrecios,2);
        }
       
      }
      catch (SQLException e)
      {
        e.printStackTrace();
        FarmaUtility.showMessage(this, 
                                 "Error al obtener los usuarios. \n " + e.getMessage(), 
                                 txtPrecios);
      }
    }
    private void jbInit() throws Exception {
        this.setSize(new Dimension(768, 375));
        this.getContentPane().setLayout( null );
        this.setTitle("Impresi�n stickers de precios cambiados");
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }

                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }
                });
        jPanelWhite1.setBounds(new Rectangle(0, 0, 765, 350));
        scrListaPrecProdCamb.setBounds(new Rectangle(20, 110, 725, 190));
        lblEsc.setBounds(new Rectangle(630, 315, 117, 19));
        lblEsc.setText("[ ESC ] Salir");
        lblF11.setBounds(new Rectangle(500, 315, 117, 19));
        lblF11.setText("[ F11 ] Imprimir");
        pnlHeaderPrec.setBounds(new Rectangle(20, 85, 725, 25));
        pnlHeaderPrec.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        pnlHeaderPrec_keyPressed(e);
                    }
                });
        txtPrecios.setBounds(new Rectangle(135, 10, 295, 20));
        txtPrecios.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtPrecios_keyPressed(e);
                    }
                });
        jPanelTitle1.setBounds(new Rectangle(20, 10, 725, 65));
        jButtonLabel1.setText("Descripci�n");
        jButtonLabel1.setBounds(new Rectangle(20, 15, 95, 15));
        jButtonLabel1.setMnemonic('d');
        jButtonLabel1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jButtonLabel1_actionPerformed(e);
                    }
                });
        jLabelFunction1.setBounds(new Rectangle(20, 315, 130, 20));
        jLabelFunction1.setText("[ F10 ] Ver Faltantes");
        txtFechaInicio.setBounds(new Rectangle(135, 35, 130, 20));
        txtFechaInicio.setLengthText(10);
        txtFechaInicio.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtFechaInicio_keyPressed(e);
                    }

                    public void keyReleased(KeyEvent e) {
                        txtFechaInicio_keyReleased(e);
                    }

                    public void keyTyped(KeyEvent e) {
                        txtFechaInicio_keyTyped(e);
                    }
                });
        txtFechaFin.setBounds(new Rectangle(300, 35, 130, 20));
        txtFechaFin.setLengthText(10);
        txtFechaFin.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtFechaFin_keyPressed(e);
                    }

                    public void keyReleased(KeyEvent e) {
                        txtFechaFin_keyReleased(e);
                    }

                    public void keyTyped(KeyEvent e) {
                        txtFechaFin_keyTyped(e);
                    }
                });
        jButtonLabel2.setText("Periodo:");
        jButtonLabel2.setBounds(new Rectangle(20, 40, 75, 15));
        jButtonLabel2.setMnemonic('p');
        jButtonLabel2.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jButtonLabel2_actionPerformed(e);
                    }
                });
        jButtonLabel3.setText("-");
        jButtonLabel3.setBounds(new Rectangle(280, 40, 30, 15));
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(585, 30, 80, 25));
        btnBuscar.setMnemonic('b');
        btnBuscar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnBuscar_actionPerformed(e);
                    }
                });
        jButtonLabel4.setText("Lista de Productos");
        jButtonLabel4.setBounds(new Rectangle(10, 5, 165, 15));
        jButtonLabel4.setMnemonic('L');
        jPanelTitle1.add(btnBuscar, null);
        jPanelTitle1.add(jButtonLabel3, null);
        jPanelTitle1.add(jButtonLabel2, null);
        jPanelTitle1.add(txtFechaInicio, null);
        jPanelTitle1.add(jButtonLabel1, null);
        jPanelTitle1.add(txtPrecios, null);
        jPanelTitle1.add(txtFechaFin, null);
        jPanelWhite1.add(jLabelFunction1, null);
        jPanelWhite1.add(jPanelTitle1, null);
        pnlHeaderPrec.add(jButtonLabel4, null);
        jPanelWhite1.add(pnlHeaderPrec, null);
        jPanelWhite1.add(lblF11, null);
        jPanelWhite1.add(lblEsc, null);
        scrListaPrecProdCamb.getViewport().add(tblListaPrecProdCamb, null);
        jPanelWhite1.add(scrListaPrecProdCamb, null);
        this.getContentPane().add(jPanelWhite1, null);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtPrecios);
        
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", txtFechaInicio);
    }

    private void pnlHeaderPrec_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }
    //JSANTIVANEZ 05.01.2010
    private void chkKeyPressed(KeyEvent e) 
    {
      FarmaGridUtils.aceptarTeclaPresionada(e,tblListaPrecProdCamb,txtPrecios,2);
      boolean flag=false;
      if(e.getKeyCode() == KeyEvent.VK_F11)
      {
        //cargaDatosPrecios();       
          System.out.println("total en tableModel: "+tableModel.data.size());
          
          if (tableModel.data.size()>0){
          
              double cantFilasDec=(double)tableModel.data.size() / 2;
              System.out.println("cantFilasDec= "+cantFilasDec);
              
            //double cantFilas = Math.round(cantFilasDec); 
            double cantFilas = Math.ceil(cantFilasDec); 
            System.out.println("cantFilas= "+cantFilas);
            
            int s = (int)cantFilas;
            
            int p=2;
            double cantGruposDec=(double)cantFilas/p;//p = cada 2 imprime
            System.out.println("cantGruposDec= "+cantGruposDec);
            
            //double cantGrupos = Math.round(cantGruposDec);
            double cantGrupos = Math.ceil(cantGruposDec);
            System.out.println("cantGrupos= "+cantGrupos);
            
             int r = (int)cantGrupos;
            
             //if (  cantGrupos>1 ){
             if (  cantGrupos>=1 ){
                for(int i=1; i<=r;i++){   
                    int r_inicio;
                    int r_fin;
                   
                    
                    if ( (s % 2) == 0 ){
                        
                        r_inicio=s-p*(i-1)-1;
                        
                        r_fin=r_inicio+p -1;
                        
                    }
                    else{    
                        if (i==r){
                            r_inicio=1;//es el primero                        
                        }    
                        else{
                            r_inicio=s-p*(i-1);
                            
                            if (i==1){//si es el primer grupo
                                if ( (s % 2) != 0 ){//y si el numero de filas(o la ultima fila) es impar
                                    p=1;//para el ultimo grupo(que es de tama�o 2) solo muestra p=1 fila.Para los demas casos muestra s=2
                                }
                            }    
                        } 
                        
                        
                        r_fin=r_inicio+p -1;//cada 2 imprime
                        if (r_fin>s)
                            r_fin=s;
                    }
                    System.out.println("2-p:" +p+" - r_inicio:"+r_inicio + " - r_fin:"+r_fin);
                    flag=UtilityCaja.pruebaImpresoraTermicaStk(this,tblListaPrecProdCamb,p*1.2,r_inicio,r_fin);
                    
                    p=2;
                }  
             }
             else{
                 System.out.println("2-cantFilas:" +cantFilas+" - 1,tableModel.data.size():"+tableModel.data.size());
                 flag=UtilityCaja.pruebaImpresoraTermicaStk(this,tblListaPrecProdCamb,cantFilas*1.2,1,tableModel.data.size());
             }
            
            txtPrecios.setText("");
            cerrarVentana(true);
            
             if (flag== true)
                FarmaUtility.showMessage(this, "Se realiz� la impresi�n de stickers, recoja la impresi�n.", txtFechaInicio);
          }
          else
            FarmaUtility.showMessage(this, "No existe registros para imprimir.", txtFechaInicio);
      }
      else  if(e.getKeyCode() == KeyEvent.VK_F10)
      {
          cargaFaltantes();
          
      }
      else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
      {
        VariablesUsuarios.vCodTrab = "";
        cerrarVentana(false);
      }
    }
    private void cargaFaltantes()
    {
        try
                  {            
                    DBPrecios.cargaListaProdCambiadosFaltantes(tableModel);
                  
                  if (tblListaPrecProdCamb.getRowCount() > 0)
                  {
                    FarmaUtility.ordenar(tblListaPrecProdCamb, tableModel, 
                                         2, FarmaConstants.ORDEN_ASCENDENTE);
                    //FarmaUtility.setearPrimerRegistro(tblListaPrecProdCamb,txtPrecios,2);
                  }
                  else{
                      FarmaUtility.showMessage(this, "No existe registros.", txtFechaInicio);
                  }
                  }
                  catch (SQLException e)
                  {
                  e.printStackTrace();
                  FarmaUtility.showMessage(this, 
                                           "Error al obtener los usuarios. \n " + e.getMessage(), 
                                           txtPrecios);
                  }
    }
    private void cerrarVentana(boolean pAceptar)
    {
      FarmaVariables.vAceptar = pAceptar;
      this.setVisible(false);
      this.dispose();
    }
    private void cargaDatosPrecios()
    {
      /*int vFilaSelect = tblListaPrecProdCamb.getSelectedRow();
      
      VariablesUsuarios.vCodTrab = FarmaUtility.getValueFieldJTable(tblListaPrecProdCamb,vFilaSelect,1);
      VariablesUsuarios.vCodTrab_RRHH = FarmaUtility.getValueFieldJTable(tblListaPrecProdCamb,vFilaSelect,3);
      
      */
    }

    private void txtPrecios_keyPressed(KeyEvent e) {
        //chkKeyPressed(e);
        
        
        
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        FarmaUtility.moveFocus(txtFechaInicio);
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
        cerrarVentana(false);    
        chkKeyPressed(e);
    }

    private void txtFechaInicio_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        FarmaUtility.moveFocus(txtFechaFin);
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
        cerrarVentana(false);    
        chkKeyPressed(e);
    }

    private void txtFechaFin_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        btnBuscar.doClick();
        
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
        cerrarVentana(false);    
        chkKeyPressed(e);
    }


    private void txtFechaInicio_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaInicio,e);
    }

    private void txtFechaFin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaFin,e);
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        busqueda();
    }
    private void busqueda()
    {
        txtPrecios.setText(txtPrecios.getText().trim().toUpperCase());
        String descProd=txtPrecios.getText().trim();
        if(validarCampos()){
          txtFechaInicio.setText(txtFechaInicio.getText().trim().toUpperCase());
          txtFechaFin.setText(txtFechaFin.getText().trim().toUpperCase());
          String FechaInicio = txtFechaInicio.getText().trim();
          String FechaFin = txtFechaFin.getText().trim();
          //if (FechaInicio.length() > 0 || FechaFin.length() > 0 )
          if (FechaInicio.length() > 0 || FechaFin.length() > 0 || descProd.length()>0)
          {
          char primerkeyCharFI = FechaInicio.charAt(0);
          char ultimokeyCharFI = FechaInicio.charAt(FechaInicio.length()-1);
          char primerkeyCharFF = FechaFin.charAt(0);
          char ultimokeyCharFF = FechaFin.charAt(FechaFin.length()-1);
          
            if ( !Character.isLetter(primerkeyCharFI) && !Character.isLetter(ultimokeyCharFI) &&
                 !Character.isLetter(primerkeyCharFF) && !Character.isLetter(ultimokeyCharFF)){
                  buscaRegistroProdCambiados(FechaInicio,FechaFin,descProd);
            }
            else
              FarmaUtility.showMessage(this,"Ingrese un formato valido de fechas",txtFechaFin); 
          }
          else
          FarmaUtility.showMessage(this,"Ingrese datos para la busqueda",txtFechaInicio);
        
        }
    }
    private void buscaRegistroProdCambiados(String pFechaInicio, String pFechaFin,String descProd)
    {
     VariablesPrecios.vFechaInicio = pFechaInicio;
     VariablesPrecios.vFechaFin = pFechaFin;
     VariablesPrecios.vDescrProd = descProd;
        
     cargaRegistroProdCambiados();
    }
    private void cargaRegistroProdCambiados()
    {
     try
     {
       System.out.println(VariablesPrecios.vFechaInicio);
       System.out.println(VariablesPrecios.vFechaFin);
       System.out.println(VariablesPrecios.vDescrProd);
       DBPrecios.cargaListaProdCambiados(tableModel,VariablesPrecios.vFechaInicio, VariablesPrecios.vFechaFin,VariablesPrecios.vDescrProd);
       
     } catch(SQLException sql)
     {
       sql.printStackTrace();
       FarmaUtility.showMessage(this, "Error al listar el registro de Ventas : \n"+sql.getMessage(),txtFechaInicio);
     }
    }
    private boolean validarCampos()
    {
      boolean retorno = true;
       if(!FarmaUtility.validarRangoFechas(this,txtFechaInicio,txtFechaFin,false,true,true,true))
        retorno = false;
            
      return retorno;
    }

    private void jButtonLabel1_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtPrecios);
    }

    private void jButtonLabel2_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFechaInicio);
    }

    private void txtFechaInicio_keyTyped(KeyEvent e) {
        FarmaUtility.admitirDigitos(txtFechaInicio, e);
    }

    private void txtFechaFin_keyTyped(KeyEvent e) {        
        FarmaUtility.admitirDigitos(txtFechaFin, e);
    }
}