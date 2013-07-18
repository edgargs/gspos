package mifarma.ptoventa.fidelizacion;

import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.Checkbox;
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

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.JTextArea;

import javax.swing.event.PopupMenuEvent;

import javax.swing.event.PopupMenuListener;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.ventas.reference.*;
import mifarma.ptoventa.campAcumulada.reference.DBCampAcumulada;
import mifarma.ptoventa.campAcumulada.reference.VariablesCampAcumulada;
import mifarma.ptoventa.fidelizacion.reference.ConstantsFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.DBFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.UtilityFidelizacion;
import mifarma.ptoventa.fidelizacion.reference.VariablesFidelizacion;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgFidelizacionBuscarTarjetas extends JDialog {
    
    private static final Logger log = LoggerFactory.getLogger(DlgFidelizacionBuscarTarjetas.class);
    JPopupMenu popup = new JPopupMenu();
    ActionListener menuListener ;
    ArrayList listaTarjetas = new ArrayList();
    ArrayList listaFormasDePago;
    String pCodFPTarjeta = "",pNombreTarjeta = "";
    Frame myParentFrame;
    FarmaTableModel tableModel;
    private JPanelWhite         pnlContent      = new JPanelWhite();
    private JPanelHeader        pnlBusqueda     = new JPanelHeader();
    private JLabelWhite         lblBuscar       = new JLabelWhite();
    private JTextFieldSanSerif  txtBuscar       = new JTextFieldSanSerif();
    private JTable              tblLista        = new JTable();
    private JScrollPane         srcLista        = new JScrollPane();
    private JLabelFunction      lblEnter       = new JLabelFunction();
    private JLabelFunction lblF11 = new JLabelFunction();
    private JLabelFunction lblEsc = new JLabelFunction();
    private final int COL_DNI = 0;
    private final int COL_NOM_CLI = 1;
    private final int COL_APE_PAT = 2;
    private final int COL_APE_MAT = 3;
    private final int COL_SEX_CLI = 4;
    private final int COL_FEC_NAC = 5;
    private final int COL_DIR_CLI = 6;
    private final int COL_TLF_CLI = 7;
    private final int COL_CEL_CLI = 8;
    private final int COL_EMAIL = 9;
    private ButtonGroup grupoBotones;
    private JRadioButton rbEfectivo = new JRadioButton();
    private JRadioButton rbTarjeta = new JRadioButton();
    private JPanel jPanel1 = new JPanel();
    private JTextArea jtaMensaje = new JTextArea();
    private JLabel lblTipoTarjeta = new JLabel();

    public DlgFidelizacionBuscarTarjetas() {
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public DlgFidelizacionBuscarTarjetas(Frame parent, String title, boolean modal)
    {
      super(parent, title, modal);
      myParentFrame = parent;
      try
      {
        jbInit();
        initialize();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(314, 123));
        this.setTitle("Buscar Tarjetas por DNI");
        this.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }

                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }
                });
        pnlBusqueda.setBounds(new Rectangle(10, 10, 285, 50));
        lblBuscar.setText("Ingrese DNI");
        lblBuscar.setBounds(new Rectangle(5, 15, 70, 20));
        txtBuscar.setBounds(new Rectangle(80, 15, 155, 20));
        txtBuscar.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtBuscar_keyPressed(e);
                    }

                    public void keyTyped(KeyEvent e) {
                        txtBuscar_keyTyped(e);
                    }
                });
        //txtBuscar.setLengthText(20);
        tblLista.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        tblLista_keyPressed(e);
                    }
                });
        lblTipoTarjeta.setVisible(false);
        srcLista.setBounds(new Rectangle(10, 190, 285, 25));
        lblEnter.setBounds(new Rectangle(105, 70, 95, 20));
        lblEnter.setText("[Enter] Buscar");
        lblF11.setBounds(new Rectangle(5, 225, 95, 20));
        lblF11.setText("[ F11 ] Aceptar");
        lblEsc.setBounds(new Rectangle(205, 70, 90, 20));
        lblEsc.setText("[ Esc ] Salir");
        rbEfectivo.setText("Efectivo");
        rbEfectivo.setBounds(new Rectangle(190, 15, 85, 25));
        rbEfectivo.setFont(new Font("SansSerif", 0, 10));
        rbEfectivo.setBackground(Color.white);
        rbTarjeta.setText("Tarjeta");
        rbTarjeta.setBounds(new Rectangle(190, 50, 85, 25));
        //popup.setBounds(new Rectangle(190, 50, 85, 25));
        rbTarjeta.setFont(new Font("SansSerif", 0, 10));
        rbTarjeta.setBackground(Color.white);
        rbTarjeta.addKeyListener(new KeyAdapter() {
                    public void keyReleased(KeyEvent e) {
                        rbTarjeta_keyReleased(e);
                    }
                });
        jPanel1.setBounds(new Rectangle(10, 130, 285, 100));
        jPanel1.setLayout(null);
        jPanel1.setBackground(Color.white);
        jPanel1.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        jtaMensaje.setBounds(new Rectangle(5, 5, 180, 90));
        jtaMensaje.setText("Seleccione una forma de pago" +"\n"+
            "para ser uso de mayores" +"\n"+
            "descuentos.");
        jtaMensaje.setFont(new Font("SansSerif", 1, 11));
        jtaMensaje.setForeground(new Color(185, 48, 48));
        //lblTipoTarjeta.setBounds(new Rectangle(10, 10, 95, 25));
        lblTipoTarjeta.setBounds(new Rectangle(190, 70, 95, 25));
        lblTipoTarjeta.setFont(new Font("Serif", 1, 10));
        lblTipoTarjeta.setForeground(new Color(0, 154, 0));
        grupoBotones = new ButtonGroup();
        grupoBotones.add(rbEfectivo);
        grupoBotones.add(rbTarjeta);
        
        pnlBusqueda.add(txtBuscar, null);
        pnlBusqueda.add(lblBuscar, null);
        //pnlBusqueda.add(lblTipoTarjeta, null);
        jPanel1.add(lblTipoTarjeta, null);
        
        jPanel1.add(jtaMensaje, null);
        jPanel1.add(rbEfectivo, null);
        //jPanel1.add(popup);
        //popup.setVisible(false);
        jPanel1.add(rbTarjeta, null);
        pnlContent.add(jPanel1, null);
        pnlContent.add(lblF11, null);
        pnlContent.add(pnlBusqueda, null);
        srcLista.getViewport().add(tblLista, null);
        pnlContent.add(srcLista, null);
        pnlContent.add(lblEsc, null);
        pnlContent.add(lblEnter, null);
        this.getContentPane().add(pnlContent, null);
        
        menuListener = new ActionListener() {
              public void actionPerformed(ActionEvent event) {
                  seleccionTarjeta(event);
              }
            };
        
        popup.addPopupMenuListener(new PopupPrintListener());

    }

    private void initialize() {
        initTable();
        try{        
            VariablesFidelizacion.vDocValidos = DBFidelizacion.obtenerParamDocIden();
        }catch(SQLException e){
            log.debug("error : "+e);
        }
        
    }

    private void initTable() {
        tableModel = new FarmaTableModel(ConstantsFidelizacion.columnsDataTarjeta,
                        ConstantsFidelizacion.defaultsDtaTarjeta,0);
        FarmaUtility.initSimpleList(tblLista, tableModel, 
                        ConstantsFidelizacion.columnsDataTarjeta);
    }


    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, "Debe presionar la tecla ESC para cerrar la ventana.", null);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        VariablesFidelizacion.auxDataCli.clear();
        cargaFormasDePagoCampanaUso();
        if(listaTarjetas.size()==1){
            //coloca a tarjeta nada mas aplica.
            lblTipoTarjeta.setVisible(true);
            lblTipoTarjeta.setText(FarmaUtility.getValueFieldArrayList(listaTarjetas,0,1));
            System.out.println("coloca datos de unica tarjeta:"+FarmaUtility.getValueFieldArrayList(listaTarjetas,0,1));
        }
        System.out.println("lblTipoTarjeta.getT_"+lblTipoTarjeta.getText());
        //17.06.2011
        if(VariablesFidelizacion.vDniCliente.trim().length()>2){
           txtBuscar.setText(VariablesFidelizacion.vDniCliente.trim());
        }
    }
    
    public void cerrarVentana(boolean vResp){
        System.out.println("vResp"+vResp);
        System.out.println("VariablesFidelizacion.vDniCliente:"+VariablesFidelizacion.vDniCliente);
        if(!vResp&&VariablesFidelizacion.vDniCliente.trim().length()==0){
            FarmaVariables.vAceptar = vResp;
            this.setVisible(vResp);
            this.dispose();
        }
        else{
                if(vResp)
                  guardaVariablesFormaPagoUsoCampana();
                else
                    if(VariablesFidelizacion.vDniCliente.trim().length()==0)
                      limpiaVariablesFormaPago();
                System.out.println("2 vIndUsoEfectivo:"+VariablesFidelizacion.vIndUsoEfectivo);
                System.out.println("2 vIndUsoTarjeta:"+VariablesFidelizacion.vIndUsoTarjeta);
                System.out.println("2 vCodFPagoTarjeta:"+VariablesFidelizacion.vCodFPagoTarjeta);
                
                if(dejaDarEvento())
                {
                    FarmaVariables.vAceptar = vResp;
                    this.setVisible(vResp);
                    this.dispose();
                }
                else {
                    FarmaUtility.showMessage(this, "Debe de seleccionar una forma de Pago.", txtBuscar);
                }
        }
    }
    
    public boolean dejaDarEvento(){
        if(listaFormasDePago.size()>0){
            if(
               VariablesFidelizacion.vIndUsoEfectivo.trim().equalsIgnoreCase("S")||
               (VariablesFidelizacion.vIndUsoTarjeta.trim().equalsIgnoreCase("S")&&
               VariablesFidelizacion.vCodFPagoTarjeta.trim().length()>0
               )             )
                return true;
            else
                return false;
        }
             
        return true;   
    }
    
    private void txtBuscar_keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_F11)
        {
            if(UtilityFidelizacion.validarDocIndentificacion(txtBuscar.getText().trim())){
                FarmaUtility.moveFocus(tblLista);
            }else{
                FarmaUtility.showMessage(this, "Numero de DNI incorrecto", null);
                limpiar();               
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN){
            fSeleccionButton();
        }
        else{
            chkKeyPressed(e);
        }   
    }
    
    private void chkKeyPressed(KeyEvent e)
    {
      if(e.getKeyCode() == KeyEvent.VK_F11)
      {
        /* if(tblLista.getSelectedRowCount()>0){
              setNumeroTarjeta();
              if(FarmaVariables.vAceptar) {
                    limpiar();
                    cerrarVentana(true);
                }
              else
                cerrarVentana(false);
          }*/
      } 
      else if(e.getKeyCode() == KeyEvent.VK_ENTER)
      {
          log.debug(" : ENTRA PARA BUSCAR");
          //  02.07.09 Se valida un numero de valido de identificacion.
          if(UtilityFidelizacion.validarDocIndentificacion(txtBuscar.getText().trim())){
              if(isSeleccionFormaPago()) {
                  buscarTarjetas();
                  System.out.println("\n ENTRA PARTA BUSCAR  \n");
              }
              else {
                  FarmaUtility.showMessage(this, "Debe de seleccionar una forma de Pago.", txtBuscar);
              }
          }else FarmaUtility.showMessage(this, "Numero no valido", null);

      }
      else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
      {
        cerrarVentana(false);
      }
      else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
          txtBuscar.setText("");
      }
      
    }
    
    public void buscarTarjetas(){
        String vdni = txtBuscar.getText().trim();
        /*
        //  19.07.2011 - inicio
        if(VariablesFidelizacion.tmp_NumTarjeta_unica_Campana.trim().length()>0){
         grabaTarjetaUnica(VariablesFidelizacion.tmp_NumTarjeta_unica_Campana.trim(),vdni);
        }
        //  19.07.2011 - fin 
        */
        try{
            
            
            //  No hay necesidad de validar 
            //UtilityFidelizacion.validarConexionMatriz();  
            //Se quita conexiones a matriz  agregado
            VariablesFidelizacion.vIndConexion="N";
            VariablesFidelizacion.auxDataCli.clear();
            
            /*
             * no se realizara la busqueda en Matriz
             *   26/07/2009
             * if (VariablesFidelizacion.vIndConexion.equals("S")) {
                log.debug("buscar en matriz al cliente");
                DBFidelizacion.buscarTarjetasXDNIMatriz(tableModel, vdni,VariablesFidelizacion.vIndConexion);
                log.debug("ver si tiene datos");
                if (tableModel.data.size() == 0) {
                    log.debug("buscar en local despues de buscar en matriz");
                    DBFidelizacion.buscarTarjetasXDNI(tableModel, vdni);
                }
            }else{*/
                log.debug("buscar en local");
                DBFidelizacion.buscarTarjetasXDNI(tableModel, vdni);
            //}
            
          VariablesFidelizacion.NumDniAux="";
          //  if(tableModel.data.size()==0){
           if(tblLista.getRowCount()==0){ //   02.07.09 evita error evento espace
                /*FarmaUtility.showMessage(this, "Este cliente no cuenta con tarjetas", null);
                limpiar();
                FarmaUtility.moveFocus(txtBuscar);*/
                
                /**
                 * @author       
                 * @since       13.02.2009
                 * Logica para creacion y asignacion de nuevas tarjetas de fidelizacion
                 */
                
                VariablesFidelizacion.NumDniAux=vdni;
                
                log.debug(" : EL CLIENTE NO EXISTE");
                
                long tmpIni = System.currentTimeMillis();
                log.debug(" : INICIO GENRAR TARJ");
                 String vNuevaTarjetaFidelizacion = 
                        UtilityFidelizacion.generaNuevaTarjeta(ConstantsFidelizacion.PREFIJO_TARJETA_FIDELIZACION,
                                                               FarmaVariables.vCodLocal);
                long tmpFin = System.currentTimeMillis();                                                               
                log.debug(" : FIN GENRAR TARJ"+ "DURACION: "+(tmpFin - tmpIni)+" milisegundos");
                
              
                 setNumeroTarjeta(vNuevaTarjetaFidelizacion);
        

                 if(FarmaVariables.vAceptar)
                 {
                       limpiar();
                       
                       ArrayList array = new ArrayList();

                       try {
                           log.debug(" : INICIO INGRESO DATOS EN MATRIZ");
                           long tmpIni1 = System.currentTimeMillis();
                           
                           DBCampAcumulada.getDatosExisteDNI(array, vdni);
                           if (array.size() > 0) {
                               VariablesCampAcumulada.vDniCliente =
                                       FarmaUtility.getValueFieldArrayList(array, 0,
                                                                           COL_DNI).trim();
                               VariablesCampAcumulada.vNomCliente =
                                       FarmaUtility.getValueFieldArrayList(array, 0,
                                                                           COL_NOM_CLI).trim();
                               VariablesCampAcumulada.vApePatCliente =
                                       FarmaUtility.getValueFieldArrayList(array, 0,
                                                                           COL_APE_PAT).trim();
                               VariablesCampAcumulada.vApeMatCliente =
                                       FarmaUtility.getValueFieldArrayList(array, 0,
                                                                           COL_APE_MAT).trim();
                               VariablesCampAcumulada.vSexo =
                                       FarmaUtility.getValueFieldArrayList(array, 0,
                                                                           COL_SEX_CLI).trim();
                               VariablesCampAcumulada.vFecNacimiento =
                                       FarmaUtility.getValueFieldArrayList(array, 0,
                                                                           COL_FEC_NAC).trim();
                               VariablesCampAcumulada.vDireccion =
                                       FarmaUtility.getValueFieldArrayList(array, 0,
                                                                           COL_DIR_CLI).trim();
                               VariablesCampAcumulada.vTelefono =
                                       FarmaUtility.getValueFieldArrayList(array, 0,
                                                                           COL_TLF_CLI).trim();
                               VariablesCampAcumulada.vCelular =
                                       FarmaUtility.getValueFieldArrayList(array, 0,
                                                                           COL_CEL_CLI).trim();
                               VariablesCampAcumulada.vCelular =
                                       FarmaUtility.getValueFieldArrayList(array, 0,
                                                                           COL_EMAIL).trim();
                               
                           }
                           
                           //comentado
                           //UtilityFidelizacion.insertarTarjetaMatriz(vNuevaTarjetaFidelizacion, VariablesCampAcumulada.vDniCliente);
                            
                           long tmpFin1 = System.currentTimeMillis();                        
                           log.debug(" : FIN INGRESO DATOS EN MATRIZ -----" +
                            "DURACION: "+(tmpFin1 - tmpIni1)+" milisegundos");
                           
                           VariablesCampAcumulada.vDniCliente = "";
                           VariablesCampAcumulada.vNomCliente = "";
                           VariablesCampAcumulada.vApePatCliente = "";
                           VariablesCampAcumulada.vApeMatCliente = "";
                           VariablesCampAcumulada.vSexo = "";
                           VariablesCampAcumulada.vFecNacimiento = "";
                           VariablesCampAcumulada.vDireccion = "";
                           VariablesCampAcumulada.vTelefono = "";
                           VariablesCampAcumulada.vCelular = "";
                           VariablesCampAcumulada.vEmail = "";

                       } catch (SQLException e) {
                           e.printStackTrace();
                       }   
                       cerrarVentana(true);
                   }
                 else
                   cerrarVentana(false);
                /** fin veliz **/ 
            }
            else{
            
                //  04.08.09 Se obtiene dni del cliente para cargar cupones emitidos
                VariablesVentas.dniListCupon=vdni.trim();
                
                log.debug(" : EL CLIENTE EXISTE");
                //System.out.println("\n ENTRA PARTA BUSCAR 2 \n");
                FarmaUtility.moveFocus(tblLista);
                
                //FarmaUtility.moveFocus(tblLista);
                
                if(tblLista.getRowCount()>0){
                    tblLista.setRowSelectionInterval(0,0);
                    long tiempoInicio = System.currentTimeMillis();
                    log.debug(" : INICIO CARGA DEL CLIENTE EXISTENTE");
                    setNumeroTarjeta();
                    long tiempoFin = System.currentTimeMillis();
                    log.debug(" : FIN CARGA DEL CLIENTE EXISTENTE -----"+
                        "DURACION: "+(tiempoFin - tiempoInicio)+" milisegundos");
                    if(FarmaVariables.vAceptar) {
                          limpiar();
                          cerrarVentana(true);
                      }
                    else
                      cerrarVentana(false);
                }
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void tblLista_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }
    
    private void txtBuscar_keyTyped(KeyEvent e) {
        FarmaUtility.admitirSoloDigitos(e, txtBuscar, 0, this);
    }
    
    private void setNumeroTarjeta(){
    
        String vdni = "";
        
        try{
        if(VariablesFidelizacion.auxDataCli.size()>0){
            log.debug("buscando tarjetas por dni: "+VariablesFidelizacion.auxDataCli);
            vdni=((String)((ArrayList)VariablesFidelizacion.auxDataCli.get(0)).get(1)).trim();
            log.debug("dni: "+vdni);
        DBFidelizacion.buscarTarjetasXDNI(tableModel,vdni.trim());
        txtBuscar.setText(vdni);
        FarmaUtility.moveFocus(tblLista);
        }
        } catch (SQLException e) {
                           e.printStackTrace();
                       }
                       
        int vRow = tblLista.getSelectedRow();
        String vCodTarjeta="";
        //VariablesFidelizacion.vNumTarjeta = tblLista.getValueAt(vRow, 0).toString();
        //VariablesFidelizacion.vDniCliente = txtBuscar.getText().trim();
         if(VariablesFidelizacion.auxDataCli.size()>0){
            vCodTarjeta = ((String)((ArrayList)VariablesFidelizacion.auxDataCli.get(0)).get(0)).trim();
         }else{
         vCodTarjeta = tblLista.getValueAt(vRow, 0).toString();
        log.debug("validando la tarjeta:"+vCodTarjeta);
         }
        
        //Inicio
        //Se colocara la tarjeta en el local segun la inf de matriz
        //
        /*
        if(VariablesFidelizacion.vIndConexion.trim().equalsIgnoreCase("S"))
        {
            UtilityFidelizacion.creaTarjetaLocal(vCodTarjeta,VariablesFidelizacion.vIndConexion);            
        }
        */
        //Fin
        
        log.debug(" : TRAE TARJETA DE MATRIZ A LOCAL");
        UtilityFidelizacion.creaTarjetaLocal(vCodTarjeta,VariablesFidelizacion.vIndConexion); 
        
        UtilityFidelizacion.validaLecturaTarjeta(vCodTarjeta,myParentFrame);
        System.out.println("****TARJETA******:"+vCodTarjeta);
    }
    
    /**
     * Metodo que valida la nueva tarjeta fidelizacion creada en el local para 
     * que muestre la interfaz para el ingreso de datos del cliente.
     * @author   
     * @since   13.02.2009
    **/
    private void setNumeroTarjeta(String vNuevaTarjetaFidelizacion){
        //FarmaUtility.showMessage(this, vNuevaTarjetaFidelizacion, null);
        String vCodTarjeta = vNuevaTarjetaFidelizacion;
        
        log.debug(" : VALIDAR DATOS DE CLIENTE");
        UtilityFidelizacion.validaLecturaTarjeta(vCodTarjeta,myParentFrame);
        
        //  02.07.09 
        if(VariablesFidelizacion.auxDataCli.size()>0){ // sigue la creacion de la tarjeta, validacion, etc
         setNumeroTarjeta();
        //buscarTarjetas();
       }
        
        log.debug(" : LUEGO DE VALIDAR DATOS DEL CLIENTE");
        System.out.println("****TARJETA******:"+vCodTarjeta);
    }
    
    public void limpiar(){
        txtBuscar.setText("");
        FarmaUtility.moveFocus(txtBuscar);
    }

    public void fSeleccionButton(){
        //FarmaUtility.moveFocus(rbEfectivo);
        //rbEfectivo.
        if(rbEfectivo.isSelected()){
            rbTarjeta.setSelected(true);
            rbEfectivo.setSelected(false);
            //reinicia
            lblTipoTarjeta.setVisible(false);
            pCodFPTarjeta = "";
            pNombreTarjeta = "";
            lblTipoTarjeta.setText("");
            //
            cargaCombo(true);
            if(listaTarjetas.size()==0){
                lblTipoTarjeta.setVisible(true);
                lblTipoTarjeta.setText("Todas las Tarjetas.");
            }
            if(listaTarjetas.size()==1){
                //coloca a tarjeta nada mas aplica.
                lblTipoTarjeta.setVisible(true);
                lblTipoTarjeta.setText(FarmaUtility.getValueFieldArrayList(listaTarjetas,0,1));
                System.out.println("coloca datos de unica tarjeta al moverse:"+FarmaUtility.getValueFieldArrayList(listaTarjetas,0,1));
            }
            
        }
        else{
            rbTarjeta.setSelected(false);
            rbEfectivo.setSelected(true);
            cargaCombo(false);
        }
    }
    
    public void cargaCombo(boolean tipo){
        //obtiene datos de forma de pago.
        //
        System.out.println("lista"+listaTarjetas);
        if(tipo&&listaTarjetas.size()>0){
            String nombre,codigo;
            
            if(listaTarjetas.size()==1){
                codigo = FarmaUtility.getValueFieldArrayList(listaTarjetas,0,1);
                if(codigo.trim().equalsIgnoreCase(ConstantsFidelizacion.COD_FP_TARJETA_TOTAL))
                   nombre = "Todas las Tarjetas."; 
                else
                   nombre = FarmaUtility.getValueFieldArrayList(listaTarjetas,0,2);
                                
                lblTipoTarjeta.setText(nombre);
            }
            else{
                    for(int i=0;i<listaTarjetas.size();i++){
                        JMenuItem menuItem= new JRadioButtonMenuItem(FarmaUtility.getValueFieldArrayList(listaTarjetas,i,1));
                        menuItem.addActionListener(menuListener);
                        popup.add(menuItem);
                    }
                    popup.show(rbTarjeta,-45,25);
                    //jtaMensaje.setComponentPopupMenu(popup);
            }
        }
        else{
            lblTipoTarjeta.setVisible(false);
            popup.removeAll();
        }
    }
    
    
    private void seleccionTarjeta(ActionEvent e) {
        String aux = e.getActionCommand().toString();
        System.out.println("Popup menu item [" + e.getActionCommand() + "] was pressed.");
        for(int i=0;i<listaTarjetas.size();i++){
            if(FarmaUtility.getValueFieldArrayList(listaTarjetas,i,1).trim().equalsIgnoreCase(aux)){
                pCodFPTarjeta  = FarmaUtility.getValueFieldArrayList(listaTarjetas,i,0).trim();
                pNombreTarjeta = FarmaUtility.getValueFieldArrayList(listaTarjetas,i,1).trim();
            }
        }
        System.out.println("pCodFPTarjeta:"  + pCodFPTarjeta);
        System.out.println("pNombreTarjeta:" + pNombreTarjeta);
        lblTipoTarjeta.setText(pNombreTarjeta.trim());
        lblTipoTarjeta.setVisible(true);
        
    }

    private void rbTarjeta_keyReleased(KeyEvent e) {
        System.out.println("tecla:"+e.getKeyCode());
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            System.out.println("Cerraron el Popup : "+ pCodFPTarjeta);
            
            if(pCodFPTarjeta.trim().length()==0){
                rbTarjeta.setSelected(false);
                rbEfectivo.setSelected(true);
                cargaCombo(false);
            }
        }
    }
    
    // An inner class to show when popup events occur
    class PopupPrintListener implements PopupMenuListener {
      public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        System.out.println("Popup menu will be visible!");
      }

      public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        System.out.println("Popup menu will be invisible!");
      }

      public void popupMenuCanceled(PopupMenuEvent e) {
        System.out.println("Popup menu is hidden!");
          System.out.println("Cerraron el Popup : "+ pCodFPTarjeta);
          
          if(pCodFPTarjeta.trim().length()==0){
              rbTarjeta.setSelected(false);
              rbEfectivo.setSelected(true);
              cargaCombo(false);
          }
      }
    }
    
    /**
     * Carga las formas de pago para asi ampliar la pantalla
     */
    
    public boolean isNumero(String pCadena){
        int n;
        try {
            n = Integer.parseInt(pCadena.trim());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    
    public void cargaFormasDePagoCampanaUso(){
        listaFormasDePago = new ArrayList();
        
        Map mapAux = new HashMap();//mapa de la campania del listado de cupones
        String codCampCupon = "",campanaCupones = "";
        //ESTO ES PARA ELIMINAR LAS CAMPAÑAS AUTOMATIACAS YA CARGADAS
        //PORQUE AHORA SE PUEDE HACER F12 VARIAS VECES.
        for(int i=0;i<VariablesVentas.vArrayList_Cupones.size();i++){
            mapAux = (HashMap)VariablesVentas.vArrayList_Cupones.get(i);
            codCampCupon  = ((String)mapAux.get("COD_CAMP_CUPON")).trim()+"";
            if(isNumero(codCampCupon)) {
                if(i+1==VariablesVentas.vArrayList_Cupones.size())
                   campanaCupones += codCampCupon;  
                else
                   campanaCupones += codCampCupon + "@";
            }
        }
                
        try {
            DBFidelizacion.cargaFormasPagoUsoXCampana(listaFormasDePago,campanaCupones);
            System.out.println("listaFormasDePago.size():"+listaFormasDePago.size());
            if(listaFormasDePago.size()>0){
                lblEnter.setBounds(new Rectangle(105, 165, 95, 20));
                lblEsc.setBounds(new Rectangle(205, 165, 90, 20));
                jPanel1.setBounds(new Rectangle(10, 60, 285, 100));
                this.setSize(new Dimension(314, 215));
                this.show();
                
                /*
                FU.PORC_DCTO  || 'Ã' || 0
                               FU.COD_FORMA_PAGO || 'Ã' || 1
                               FP.DESC_CORTA_FORMA_PAGO || 'Ã' || 2
                               FP.IND_TARJ || 'Ã' || 3
                               FP.IND_FORMA_PAGO_EFECTIVO 4
                 * */
                String pTipoFPEfectivo = "",pTipoFPTarjeta = "",pAux = "";
                listaTarjetas = new ArrayList();
                for(int i=0;i<listaFormasDePago.size();i++){
                    System.out.println("i"+i);
                    pAux = FarmaUtility.getValueFieldArrayList(listaFormasDePago,i,4);
                    if(pAux.trim().equalsIgnoreCase("S")){
                        pTipoFPEfectivo = "S"; 
                    }
                    else{
                        pAux = FarmaUtility.getValueFieldArrayList(listaFormasDePago,i,3); 
                        if(pAux.trim().equalsIgnoreCase("S")){
                            pTipoFPTarjeta = "S"; 
                            ArrayList fp = new ArrayList();
                            fp.add(FarmaUtility.getValueFieldArrayList(listaFormasDePago,i,1));
                            fp.add(FarmaUtility.getValueFieldArrayList(listaFormasDePago,i,2));
                            listaTarjetas.add(fp);
                            System.out.println("listaTarjetas:"+listaTarjetas);
                        }
                    }
                }
                //
                if(pTipoFPEfectivo.trim().equalsIgnoreCase("S")||pTipoFPTarjeta.trim().equalsIgnoreCase("S")){
                    rbEfectivo.setVisible(true);
                    rbTarjeta.setVisible(true);
                }
                System.out.println("listaTarjetas:"+listaTarjetas.size());
                if(listaTarjetas.size()==1){
                    //coloca a tarjeta nada mas aplica.
                    lblTipoTarjeta.setVisible(true);
                    lblTipoTarjeta.setText(FarmaUtility.getValueFieldArrayList(listaTarjetas,0,1));
                    System.out.println("coloca datos de unica tarjeta:"+FarmaUtility.getValueFieldArrayList(listaTarjetas,0,1));
                    System.out.println("get:"+lblTipoTarjeta.getText());
                }
                
            }
            else{
                this.setSize(new Dimension(314, 123));
                lblEnter.setBounds(new Rectangle(105, 70, 95, 20));
                lblEsc.setBounds(new Rectangle(205, 70, 90, 20));
                jPanel1.setBounds(new Rectangle(10, 130, 285, 100));
                this.show();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            this.setSize(new Dimension(314, 123));
            lblEnter.setBounds(new Rectangle(105, 70, 95, 20));
            lblEsc.setBounds(new Rectangle(205, 70, 90, 20));
            jPanel1.setBounds(new Rectangle(10, 130, 285, 100));
            this.show();
        } finally {
            
        }
    }
    
    public boolean isSeleccionFormaPago(){
        boolean resultado = false;
        if(listaFormasDePago.size()>0){
            //validará que seleccion la forma de pago.
            if(rbEfectivo.isSelected()) return true;
            
            if(rbTarjeta.isSelected()){
                if(listaTarjetas.size()>0){
                    if(listaTarjetas.size()>1){
                        if(pCodFPTarjeta.trim().length()>0)
                            return true;
                    }
                    else {
                        //es porque solo hay una sola tarjeta.
                        pCodFPTarjeta  = FarmaUtility.getValueFieldArrayList(listaTarjetas,0,0).trim();
                        pNombreTarjeta = FarmaUtility.getValueFieldArrayList(listaTarjetas,0,1).trim();
                        System.out.println("pCodFPTarjeta:"  + pCodFPTarjeta);
                        System.out.println("pNombreTarjeta:" + pNombreTarjeta);
                        if(pCodFPTarjeta.trim().length()>0)
                            return true;
                    }
                    
                }
                else
                    return true;
            }
        }
        else
            resultado = true;
        
        return resultado;
    }
    
    public void guardaVariablesFormaPagoUsoCampana(){
        if(listaFormasDePago.size()>0){
            //Esto quiere decir que ES OBLIGATORIO la opcion de FORMAS DE PAGO
            //validará que seleccion la forma de pago.
            if(rbEfectivo.isSelected())
            {
                VariablesFidelizacion.vIndUsoEfectivo = "S";
                VariablesFidelizacion.vIndUsoTarjeta  = "N";
                VariablesFidelizacion.vCodFPagoTarjeta = "NULL";
                VariablesFidelizacion.vNamePagoTarjeta = "Efectivo";
            }
            else{
                VariablesFidelizacion.vIndUsoEfectivo = "N";
                    if(rbTarjeta.isSelected()){
                        VariablesFidelizacion.vIndUsoTarjeta  = "S";
                        if(listaTarjetas.size()>0){
                            
                            
                            if (listaTarjetas.size()>1) {
                                if(pCodFPTarjeta.trim().length()>0){
                                    if(pCodFPTarjeta.trim().equalsIgnoreCase(ConstantsFidelizacion.COD_FP_TARJETA_TOTAL)){
                                        VariablesFidelizacion.vCodFPagoTarjeta = "T";
                                        VariablesFidelizacion.vNamePagoTarjeta = lblTipoTarjeta.getText().trim().toUpperCase();
                                    }
                                    else{
                                        VariablesFidelizacion.vCodFPagoTarjeta = pCodFPTarjeta.trim();
                                        VariablesFidelizacion.vNamePagoTarjeta = lblTipoTarjeta.getText().trim().toUpperCase();
                                    }
                                }
                            }
                            else {
                                VariablesFidelizacion.vCodFPagoTarjeta = FarmaUtility.getValueFieldArrayList(listaTarjetas,0,0).trim();
                                VariablesFidelizacion.vNamePagoTarjeta = FarmaUtility.getValueFieldArrayList(listaTarjetas,0,1).trim();
                            }
                        }
                        else
                        {
                         //NUNCA DEBE DE INGRESAR EN VACIO
                         FarmaUtility.showMessage(this,"Error de APLICACION.",txtBuscar);
                         //Todas las Tarjetas
                         VariablesFidelizacion.vCodFPagoTarjeta = "T";
                         VariablesFidelizacion.vNamePagoTarjeta = lblTipoTarjeta.getText().trim().toUpperCase();   
                        }
                    }
            }
            System.out.println("FIN vIndUsoEfectivo:"+VariablesFidelizacion.vIndUsoEfectivo);
            System.out.println("FIN vIndUsoTarjeta:"+VariablesFidelizacion.vIndUsoTarjeta);
            System.out.println("FIN vCodFPagoTarjeta:"+VariablesFidelizacion.vCodFPagoTarjeta);
            
        }
    }
    
    public void limpiaVariablesFormaPago(){
        VariablesFidelizacion.vIndUsoEfectivo = "NULL";
        VariablesFidelizacion.vIndUsoTarjeta = "NULL";
        VariablesFidelizacion.vCodFPagoTarjeta = "NULL";
    }
    
}
