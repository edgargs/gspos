package mifarma.ptoventa.convenio;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.DefaultKeyboardFocusManager;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.event.*;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import mifarma.common.*;

import mifarma.ptoventa.DlgListaMaestros;
import mifarma.ptoventa.ce.reference.ConstantsCajaElectronica;
import mifarma.ptoventa.convenio.reference.*;
import mifarma.ptoventa.ce.reference.DBCajaElectronica;
import mifarma.ptoventa.ce.reference.VariablesCajaElectronica;
import mifarma.ptoventa.cliente.reference.ConstantsCliente;
import mifarma.ptoventa.cliente.reference.DBCliente;
import mifarma.ptoventa.reference.*;
import mifarma.ptoventa.caja.reference.*;
import mifarma.ptoventa.ventas.DlgListaProductos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgDatosEfectivoRendido.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      17.08.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 * 
 */
public class DlgDatosConvenio
  extends JDialog
{
  private static final Logger log = LoggerFactory.getLogger(DlgDatosConvenio.class);
  
  /* ********************************************************************** */
  /*                        DECLARACION PROPIEDADES                         */
  /* ********************************************************************** */

  Frame myParentFrame;
  FarmaTableModel tableModel;

  private FarmaRowEditorModel rowEditorModel;
  private DefaultCellEditor defaultCellEditor;
  private ArrayList aCampos = new ArrayList();
  private FarmaJTable tblLista = new FarmaJTable();
  
  /**
   * Columnas de la grilla
   * @author Edgar Rios Navarro
   * @since 13.06.2008
   */
  private final int COL_DATO = 1;
  private final int COL_COD = 2;
  private final int COL_TIPO_DATO = 3;
  private final int COL_SOLO_LECTURA = 4;
  private final int COL_IND_OBLI = 5;
  private final int COL_IND_BUS = 6;
  
  /* ************************************************************************ */
  /*                               CAMPOS DE LA GRILLA                        */
  /* ************************************************************************ */
  
  private JTextFieldSanSerif txtNomCliente = new JTextFieldSanSerif();      
  private JTextFieldSanSerif txtNumeroDocumento = new JTextFieldSanSerif();  
  private JTextFieldSanSerif txtApellidoMaterno = new JTextFieldSanSerif();  
  private JTextFieldSanSerif txtApellidoPaterno = new JTextFieldSanSerif();  
  private JTextFieldSanSerif txtTrabajador = new JTextFieldSanSerif();  
  private JTextFieldSanSerif txtTelefono = new JTextFieldSanSerif();  
  private JTextFieldSanSerif txtCodigoInternoCliente = new JTextFieldSanSerif();
  
  /**
   * Cod Trabajador Empresa
   * @author dubilluz
   * @since  17.08.2007
   */
  private JTextFieldSanSerif txtCodTrabajadorEmpresa = new JTextFieldSanSerif();
  JTextField vCodCliente = new JTextField();  
  JTextField vCodTrabajador = new JTextField();
  
  /**
   * Codigo y nombre del trabajador dependiente
   * @author JCORTEZ
   * @since  13.03.2008
   */
  private JTextFieldSanSerif txtCodTrabDependiente = new JTextFieldSanSerif();
  private JTextFieldSanSerif txtNomTrabDependiente = new JTextFieldSanSerif();
  
  JTextField vCodTrabajadorDependiente = new JTextField();
  
  /*JTextField vCodTrabDependiente = new JTextField();
  JTextField vNomTrabDependiente = new JTextField();*/
  
  /**
   * Textfield para muestra el nombre del convenio.
   * @author Edgar Rios Navarro
   * @since 27.05.2008
   */
  private JTextFieldSanSerif txtNomConvenio = new JTextFieldSanSerif();
    
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JLabelFunction lblF11 = new JLabelFunction();
  private JScrollPane scrLista = new JScrollPane();
  private JPanelTitle pnlTitle1 = new JPanelTitle();
  private JButtonLabel btnLista = new JButtonLabel();
  private JLabelFunction lblF1 = new JLabelFunction();

  /* ********************************************************************** */
  /*                        CONSTRUCTORES                                   */
  /* ********************************************************************** */

  public DlgDatosConvenio()
  {
    try
    {
      jbInit();
    }
    catch (Exception e)
    {
      log.error("",e);
    }

  }

  public DlgDatosConvenio(Frame parent, String title, boolean modal)
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
      log.error("",e);
    }
  }

  /* ************************************************************************ */
  /*                                  METODO jbInit                           */
  /* ************************************************************************ */

  private void jbInit()
    throws Exception
  {
    this.getContentPane().setLayout(borderLayout1);
    this.setSize(new Dimension(583, 306));
    this.setDefaultCloseOperation(0);
    this.setTitle("Datos de Convenio");
    this.addWindowListener(new WindowAdapter()
        {
          public void windowOpened(WindowEvent e)
          {
            this_windowOpened(e);
          }

          public void windowClosing(WindowEvent e)
          {
            this_windowClosing(e);
          }
        });
    lblEsc.setBounds(new Rectangle(465, 245, 95, 20));
    lblEsc.setText("[ ESC ] Cerrar");
    lblF11.setBounds(new Rectangle(350, 245, 105, 20));
    lblF11.setText("[ F11 ] Aceptar");
    scrLista.setBounds(new Rectangle(10, 30, 555, 205));
    tblLista.addKeyListener(new KeyAdapter()
        {
          public void keyPressed(KeyEvent e)
          {
            tblLista_keyPressed(e);
          }
        });
    pnlTitle1.setBounds(new Rectangle(10, 10, 555, 20));
    btnLista.setText("Lista Datos");
    btnLista.setBounds(new Rectangle(5, 0, 105, 20));
    btnLista.setMnemonic('L');
    btnLista.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            btnLista_actionPerformed(e);
          }
        });
    lblF1.setBounds(new Rectangle(20, 245, 125, 20));
    lblF1.setText("[ F1 ] Ingresar Dato");
    scrLista.getViewport().add(tblLista, null);
    pnlTitle1.add(btnLista, null);
    jContentPane.add(lblF1, null);
    jContentPane.add(pnlTitle1, null);
    jContentPane.add(scrLista, null);
    jContentPane.add(lblF11, null);
    jContentPane.add(lblEsc, null);
    
		txtTrabajador.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				txtTrabajador_keyTyped(e);
			}
		});
    
		txtApellidoPaterno.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				txtApellidoPaterno_keyTyped(e);
			}
		});
    
		txtApellidoMaterno.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				txtApellidoMaterno_keyTyped(e);
			}
		});
    
		txtNumeroDocumento.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				txtNumeroDocumento_keyTyped(e);
			}
		});

		txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				txtTelefono_keyTyped(e);
			}
		});

		txtCodigoInternoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				txtCodigoInternoCliente_keyTyped(e);
			}
		});
    
    
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
  }

  /* ************************************************************************ */
  /*                                  METODO initialize                       */
  /* ************************************************************************ */

  private void initialize()
  {
    FarmaVariables.vAceptar = false;
    this.setTitle("Datos Convenio - " + VariablesConvenio.vNomConvenio);
  }

  /* ************************************************************************ */
  /*                            METODOS INICIALIZACION                        */
  /* ************************************************************************ */

  private void initTable()
  {
    
    tableModel = new FarmaTableModel(ConstantsConvenio.columnsListaDatosConvenio, 
                                    ConstantsConvenio.defaultValuesListaDatosConvenio, 
                                    0,
                                    ConstantsConvenio.editableListaDatosConvenio,
                                     null);
    rowEditorModel = new FarmaRowEditorModel();
    tblLista.setAutoCreateColumnsFromModel(false);
    tblLista.setModel(tableModel);
    tblLista.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    tblLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    for (int k=0; k<tableModel.getColumnCount(); k++) {
      TableColumn column = new TableColumn(k, ConstantsConvenio.columnsListaDatosConvenio[k].m_width);
      tblLista.addColumn(column);
    }
    
    tblLista.setRowEditorModel(rowEditorModel);
    cargaCampos();
    // Verifica si no tiene campos para ingresar acepta el convenio
    if ( tblLista.getRowCount()==0 ){
      if(FarmaVariables.vEconoFar_Matriz){
        FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,null);
        return;
      }
      funcionF11();
    }
  }

  private void cargaCampos() 
  {
    cargaLista();
    setTipoCampo();
    /*if ( tblLista.getRowCount()>0 ) {
      for (int i=0; i<tblLista.getRowCount(); i++)
      {
        tblLista.setValueAt(tblLista.getValueAt(i,1).toString().trim(),i,1);
        tblLista.changeSelection(0,1,false,false);
      }
    }*/
    tblLista.repaint();
  }
    
  private void cargaLista()
  {
    try
    {
      DBConvenio.listaCamposConvenio(tableModel);      
    }catch(SQLException e)
    {
      log.error("",e);
      FarmaUtility.showMessage(this,"Error al cargar los campos.",tblLista);
    }
    aCampos = tableModel.data;
  }

  /**
   * Este procedimiento determinará el tipo de dato de cada campo segun lo registrado en la BD.
   */
  private void setTipoCampo() 
  {
    String codigoCampo = "";
    String vTipoDato;
    String vIndSoloLec;
    
    for (int i=0; i<tblLista.getRowCount(); i++) {
      codigoCampo = tblLista.getValueAt(i,COL_COD).toString().trim();
      vTipoDato = FarmaUtility.getValueFieldJTable(tblLista,i,COL_TIPO_DATO);
      vIndSoloLec = FarmaUtility.getValueFieldJTable(tblLista,i,COL_SOLO_LECTURA);
      
      if(codigoCampo.equals(ConstantsConvenio.CAMPO_CODIGO_TRABAJADOR)) 
      {
        getTxtCodigoTrabajador(i,vTipoDato,vIndSoloLec);
      }
      else if(codigoCampo.equals(ConstantsConvenio.CAMPO_NOMBRE)) 
      {
        getTxtTrabajador(i,vTipoDato,vIndSoloLec);
      }
      else if(codigoCampo.equals(ConstantsConvenio.CAMPO_APELLIDO_PATERNO)) 
      {
        getTxtApellidoPaterno(i,vTipoDato,vIndSoloLec);
      }
      else if(codigoCampo.equals(ConstantsConvenio.CAMPO_APELLIDO_MATERNO)) 
      {
        getTxtApellidoMaterno(i,vTipoDato,vIndSoloLec);
      }
      else if(codigoCampo.equals(ConstantsConvenio.CAMPO_NUMERO_DOCUMENTO)) 
      {
        getTxtNumeroDocumento(i,vTipoDato,vIndSoloLec);
      }
      else if(codigoCampo.equals(ConstantsConvenio.CAMPO_TELEFONO)) 
      {
        getTxtTelefono(i,vTipoDato,vIndSoloLec);
      }
      else if(codigoCampo.equals(ConstantsConvenio.CAMPO_CODIGO_INTERNO)) 
      {
        getTxtCodigoInternoCliente(i,vTipoDato,vIndSoloLec);
      }
      else if(codigoCampo.equals(ConstantsConvenio.CAMPO_CODIGO_TRABAJADOR_EMPRESA)) 
      {
        getTxtCodigoTrabajadorEmpresa(i,vTipoDato,vIndSoloLec);        
      }
      else if(codigoCampo.equals(ConstantsConvenio.CAMPO_CODIGO_DEPENDIENTE))
      { 
        getTxtCodigoTrabajadorDependiente(i,vTipoDato,vIndSoloLec);       
      }
      else if(codigoCampo.equals(ConstantsConvenio.CAMPO_NOMBRE_DEPENDIENTE))
      { 
        getTxtNombreTrabajadorDependiente(i,vTipoDato,vIndSoloLec);        
      }else if(codigoCampo.equals(ConstantsConvenio.CAMPO_NOMBRE_CONVENIO))
      { 
        getTxtNombreConvenio(i,vTipoDato,vIndSoloLec);       
      }
    }
  }
  
  /* ************************************************************************ */
  /* SECCION : CREACION DE CAMPOS JTEXTFIELD                                  */
  /* ************************************************************************ */
  
  private void getTxtCodigoTrabajador(int pRow, String pTipoCampo, String pInSoloLectura) {
    txtNomCliente.setEditable(false);
    addKeyListenerToTextField2(txtNomCliente,pTipoCampo,pInSoloLectura,ConstantsPtoVenta.LISTA_CLIENTES_CONVENIO,vCodTrabajador);
    defaultCellEditor = new DefaultCellEditor(txtNomCliente);
    rowEditorModel.addEditorForRow(pRow,defaultCellEditor);
  }
  
  /**
   * Para Cod_Trabajador
   * @author dubilluz
   * @since  17.08.2007
   */
  private void getTxtCodigoTrabajadorEmpresa(int pRow, String pTipoCampo, String pInSoloLectura) {
    txtCodTrabajadorEmpresa.setEditable(false);
    addKeyListenerToTextField3(txtCodTrabajadorEmpresa,txtNomCliente,txtNumeroDocumento,pTipoCampo,pInSoloLectura,ConstantsPtoVenta.LISTA_CLIENTES_CONVENIO,vCodTrabajador);
    defaultCellEditor = new DefaultCellEditor(txtCodTrabajadorEmpresa);
    rowEditorModel.addEditorForRow(pRow,defaultCellEditor);    
  }  
  
  private void getTxtCodigoInternoCliente(int pRow, String pTipoCampo, String pInSoloLectura) {
   txtCodigoInternoCliente.setLengthText(30);
   createJTextField(txtCodigoInternoCliente,pRow,pTipoCampo,pInSoloLectura);
  }
  
  private void getTxtTelefono(int pRow, String pTipoCampo, String pInSoloLectura) {
   txtTelefono.setLengthText(30);
   createJTextField(txtTelefono,pRow,pTipoCampo,pInSoloLectura);
  }  
      
  private void getTxtTrabajador(int pRow, String pTipoCampo, String pInSoloLectura) {
   txtTrabajador.setLengthText(30);
   createJTextField(txtTrabajador,pRow,pTipoCampo,pInSoloLectura);
  }

  private void getTxtApellidoPaterno(int pRow, String pTipoCampo, String pInSoloLectura) {
   txtApellidoPaterno.setLengthText(30);
   createJTextField(txtApellidoPaterno,pRow,pTipoCampo,pInSoloLectura);
  }

  private void getTxtApellidoMaterno(int pRow, String pTipoCampo, String pInSoloLectura) {
   txtApellidoMaterno.setLengthText(30);
   createJTextField(txtApellidoMaterno,pRow,pTipoCampo,pInSoloLectura);
  }
  
  //DNI antes del 14.03.2008
  /*private void getTxtNumeroDocumento(int pRow, String pTipoCampo, String pInSoloLectura) {
    txtNumeroDocumento.setLengthText(30);
    createJTextField(txtNumeroDocumento,pRow,pTipoCampo,pInSoloLectura);
  }*/
  
  /**
   * Para la busqueda por DNI
   * @author JCORTEZ
   * @since  14.03.2008
   */
  private void getTxtNumeroDocumento(int pRow, String pTipoCampo, String pInSoloLectura) {
    if(pInSoloLectura.equals("S"))
    {
      txtNumeroDocumento.setEditable(false);
    }
    
    addKeyListenerToTextField4(txtNumeroDocumento,txtNomCliente,txtCodTrabajadorEmpresa,
                               pTipoCampo,pInSoloLectura,
                               ConstantsPtoVenta.LISTA_CLIENTES_CONVENIO,vCodTrabajador);
    defaultCellEditor = new DefaultCellEditor(txtNumeroDocumento);
    rowEditorModel.addEditorForRow(pRow,defaultCellEditor);
  }
  
  /**
   * Para codigo del trabajador dependiente
   * @author JCORTEZ
   * @since  13.03.2008
   */
  private void getTxtCodigoTrabajadorDependiente(int pRow, String pTipoCampo, String pInSoloLectura) {
    txtCodTrabDependiente.setEditable(false);
    addKeyListenerToTextField5(txtCodTrabDependiente,txtNomTrabDependiente,txtNumeroDocumento,pTipoCampo,pInSoloLectura,ConstantsPtoVenta.LISTA_CLIENTES_CONVENIO,vCodTrabajadorDependiente);
    defaultCellEditor = new DefaultCellEditor(txtCodTrabDependiente);
    rowEditorModel.addEditorForRow(pRow,defaultCellEditor);
  }
   
  /**
   * Para  nombre del trabajador dependiente
   * @author JCORTEZ
   * @since  13.03.2008
   */
  private void getTxtNombreTrabajadorDependiente(int pRow, String pTipoCampo, String pInSoloLectura) {
    txtNomTrabDependiente.setEditable(false);
    createJTextField(txtNomTrabDependiente,pRow,pTipoCampo,pInSoloLectura);
  }
  
  /**
   * Se agrego este campo, para que el usuario descienda hacia la celda debajo.
   * Al parecer, este bug (no se muestra el foco en el editor de la celda al iniciar el dialogo)
   * se soluciona en JAVA 6.
   * @param pRow
   * @param pTipoCampo
   * @param pInSoloLectura
   * @author Edgar Rios Navarro
   * @since 27.05.2008
   */
  private void getTxtNombreConvenio(int pRow, String pTipoCampo, String pInSoloLectura) {
    txtNomConvenio.setEditable(false);
    tblLista.setValueAt(VariablesConvenio.vNomConvenio,pRow,1);
    createJTextField(txtNomConvenio,pRow,pTipoCampo,pInSoloLectura);
  }
  
  
  private void createJTextField(JTextField pJTextField,
                                int pRow,
                                String pTipoCampo,
                                String pInSoloLectura) {
    addKeyListenerToTextField(pJTextField,pTipoCampo,pInSoloLectura);
    defaultCellEditor = new DefaultCellEditor(pJTextField);
    rowEditorModel.addEditorForRow(pRow,defaultCellEditor);
  }
  
  /* ************************************************************************ */
  /* SECCION : CREACION DE CAMPOS JCOMBOBOX                                   */
  /* ************************************************************************ */

  /* ************************************************************************ */
  /*                            METODOS DE EVENTOS                            */
  /* ************************************************************************ */
  
   private void addKeyListenerToTextField(final JTextField pJTextField,
                                          final String pTipoCampo,
                                          final String pInSoloLectura) {
     pJTextField.addKeyListener(new KeyAdapter() {
       public void keyTyped(KeyEvent e) {
         if ( pInSoloLectura.equalsIgnoreCase("S") ) {
           e.consume();
         } else {
           if ( pTipoCampo.equalsIgnoreCase("E") )
             FarmaUtility.admitirDigitos(pJTextField,e);
           else if ( pTipoCampo.equalsIgnoreCase("D") )
             FarmaUtility.admitirDigitosDecimales(pJTextField,e);
         }
       }
       public void keyPressed(KeyEvent e) {
         if ( e.getKeyCode()==KeyEvent.VK_ENTER ) {
           e.consume();
           int vFila = tblLista.getSelectedRow();
           
           if ( (vFila+1)==tblLista.getRowCount() )
             FarmaUtility.setearRegistro(tblLista,vFila,null,0);
           else  FarmaUtility.setearRegistro(tblLista,vFila+1,null,0);
           
           pJTextField.setText(pJTextField.getText().toUpperCase());
         } else tblLista_keyPressed(e);
       }
       public void keyReleased(KeyEvent e) {
         int vFila = tblLista.getSelectedRow();
         
         if ( pTipoCampo.equalsIgnoreCase("F") )
         {
           String codigoCampo = FarmaUtility.getValueFieldJTable(tblLista,vFila, COL_COD);
           if(codigoCampo.trim().equals(ConstantsCajaElectronica.CAMPO_FECHA_OPERACION))
             FarmaUtility.dateHourComplete(pJTextField,e);
           else
             FarmaUtility.dateComplete(pJTextField,e);
         }
         
         if ( mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e) && pJTextField.isDisplayable() ) {
           pJTextField.selectAll();
         } else if ( e.getKeyCode()==KeyEvent.VK_ENTER ) 
         {
           String codigoCampo = FarmaUtility.getValueFieldJTable(tblLista, vFila, COL_COD);
           /*if( codigoCampo.trim().equals(ConstantsCajaElectronica.CAMPO_NUMERO_CUENTA) ){
            //cargaNumeroCuenta();
            //txtNumeroCuenta.setText(VariablesPtoVenta.vDescMaestro);
           }
           if(codigoCampo.trim().equals(ConstantsCajaElectronica.CAMPO_NOMBRE_PROVEEDOR)){
            log.debug("Entro keyReleased");
            txtProveedor.setText("");
            vCodProveedor.setText("");
          }*/
           moveFocusTo(vFila);
         } else {
           if ( e.getKeyCode()!=KeyEvent.VK_LEFT && 
                e.getKeyCode()!=KeyEvent.VK_RIGHT &&
                e.getKeyCode()!=KeyEvent.VK_DELETE &&
                e.getKeyCode()!=KeyEvent.VK_BACK_SPACE &&
                e.getKeyCode()!=KeyEvent.VK_HOME &&
                (!Character.isLetter(e.getKeyChar()) && !Character.isWhitespace(e.getKeyChar()) && !Character.isDigit(e.getKeyChar())) )
             pJTextField.setText(pJTextField.getText().toUpperCase());
         }
       }
     });
   }
  
  private void addKeyListenerToTextField2(final JTextField pJTextField,
                                         final String pTipoCampo,
                                         final String pInSoloLectura,
                                         final String pTipoMaestro,
                                         final JTextField pCodMaestro) {
    pJTextField.addKeyListener(new KeyAdapter() {
      public void keyTyped(KeyEvent e) {
        if ( pInSoloLectura.equalsIgnoreCase("S") ) {
          e.consume();
        } else {
          if ( pTipoCampo.equalsIgnoreCase("E") )
            FarmaUtility.admitirDigitos(pJTextField,e);
          else if ( pTipoCampo.equalsIgnoreCase("D") )
            FarmaUtility.admitirDigitosDecimales(pJTextField,e);
        }
      }
      public void keyPressed(KeyEvent e) {
        if ( e.getKeyCode()==KeyEvent.VK_ENTER ) {
          e.consume();
          int vFila = tblLista.getSelectedRow();
          
          if ( (vFila+1)==tblLista.getRowCount() )
            FarmaUtility.setearRegistro(tblLista,vFila,null,0);
          else  FarmaUtility.setearRegistro(tblLista,vFila+1,null,0);
          
          pJTextField.setText(pJTextField.getText().toUpperCase());
        } else tblLista_keyPressed(e);
      }
      public void keyReleased(KeyEvent e) {
        if ( mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e) && pJTextField.isDisplayable() ) {
         /* pJTextField.selectAll();
          //mostrar dialogo
          VariablesPtoVenta.vTipoMaestro = pTipoMaestro;
          //VariablesCajaElectronica.vCodEntidadFinanciera = getCodEntidadFinanciera();
          //VariablesCajaElectronica.vCodTipoMoneda = getTipoMoneda();
          /*if(VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_DELIVERY_PERDIDO))
          {
            VariablesCajaElectronica.vCodServicio = ConstantsCajaElectronica.CODIGO_SERVICIO_DELIVERY;  
          } else VariablesCajaElectronica.vCodServicio = getCodServicio();*/
         /* log.debug("Tipo Maestro: "+pTipoMaestro);
          DlgListaClientesConv dlgListaClientesConv = new DlgListaClientesConv(myParentFrame,"",true);
          dlgListaClientesConv.setVisible(true);
          if(FarmaVariables.vAceptar)
          {
            pCodMaestro.setText(VariablesConvenio.vCodCliente);
            pJTextField.setText(VariablesConvenio.vNomCliente);
            log.debug("VariablesConvenio.vCodCliente "+VariablesConvenio.vCodCliente);          
            //pCodMaestro.setText(VariablesPtoVenta.vCodMaestro);
            //pJTextField.setText(VariablesPtoVenta.vDescMaestro);
            //log.debug("Codigo Maestro: "+pCodMaestro.getText());
          }*/
        } else if ( e.getKeyCode()==KeyEvent.VK_ENTER ) {
          moveFocusTo(tblLista.getSelectedRow());
        } 
        else {
          if ( e.getKeyCode()!=KeyEvent.VK_LEFT && 
               e.getKeyCode()!=KeyEvent.VK_RIGHT &&
               e.getKeyCode()!=KeyEvent.VK_DELETE &&
               e.getKeyCode()!=KeyEvent.VK_BACK_SPACE &&
               e.getKeyCode()!=KeyEvent.VK_HOME &&
               (!Character.isLetter(e.getKeyChar()) && !Character.isWhitespace(e.getKeyChar()) && !Character.isDigit(e.getKeyChar())) )
            pJTextField.setText(pJTextField.getText().toUpperCase());
        }
      }
    });
  }
  
  /**
   * Para el Cod_Trabajador Empresa
   * @author dubilluz
   * @since  17.08.2007
   */
  private void addKeyListenerToTextField3(final JTextField pJTextField,
                                         //Añadido UBILLUZ
                                         final JTextField pJTextField2,
                                         final JTextField pJTextField3,
                                         final String pTipoCampo,
                                         final String pInSoloLectura,
                                         final String pTipoMaestro,
                                         final JTextField pCodMaestro) {
                        
    pJTextField.addKeyListener(new KeyAdapter() {
    
      public void keyTyped(KeyEvent e) {
      
        if ( pInSoloLectura.equalsIgnoreCase("S") ) {
          e.consume();
        } else {
          if ( pTipoCampo.equalsIgnoreCase("E") )
            FarmaUtility.admitirDigitos(pJTextField,e);
          else if ( pTipoCampo.equalsIgnoreCase("D") )
            FarmaUtility.admitirDigitosDecimales(pJTextField,e);
        }
      }
      public void keyPressed(KeyEvent e) {
        if ( e.getKeyCode()==KeyEvent.VK_ENTER ) {
          e.consume();
          int vFila = tblLista.getSelectedRow();
          
          if ( (vFila+1)==tblLista.getRowCount() )
            FarmaUtility.setearRegistro(tblLista,vFila,null,0);
          else  FarmaUtility.setearRegistro(tblLista,vFila+1,null,0);
          
          pJTextField.setText(pJTextField.getText().toUpperCase());
        } else tblLista_keyPressed(e);
      }
      public void keyReleased(KeyEvent e) {
        if ( mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e) && pJTextField.isDisplayable() ) {
          pJTextField.selectAll();
          
          VariablesConvenio.vTipoFiltro=ConstantsConvenio.CAMPO_CODIGO_TRABAJADOR;
          //log.debug("TIPO FILTRO :"+VariablesConvenio.vTipoFiltro);
          //mostrar dialogo
          DlgListaClientesConv dlgListaClientesConv = new DlgListaClientesConv(myParentFrame,"",true);
          dlgListaClientesConv.setVisible(true);
          VariablesConvenio.vTipoFiltro="";
          if(FarmaVariables.vAceptar)
          {
            pCodMaestro.setText(VariablesConvenio.vCodCliente);
            pJTextField.setText(VariablesConvenio.vCodTrab);
            pJTextField2.setText(VariablesConvenio.vNomCliente);
            pJTextField3.setText(VariablesConvenio.vNumDocIdent);
            
            //COLOCALNDO el NOMBRE EN LA CELDA QUE DEBE ESTAR EL NOMBRE POR EL
            //CAMPO DE CODIGO DE FORMULARIO
            int posRowNomCli = getPosNomCli(ConstantsConvenio.CAMPO_CODIGO_TRABAJADOR);
            tblLista.setValueAt(VariablesConvenio.vNomCliente,posRowNomCli,COL_DATO);
            String codigoCampo = "";
            int posRowNumDocCli;
            
            for (int i=0; i<tblLista.getRowCount(); i++) 
            {
              codigoCampo = FarmaUtility.getValueFieldJTable(tblLista, i, COL_COD);
              
              if(codigoCampo.equals(ConstantsConvenio.CAMPO_NUMERO_DOCUMENTO))
              {
                posRowNumDocCli = getPosNomCli(ConstantsConvenio.CAMPO_NUMERO_DOCUMENTO);
                tblLista.setValueAt(VariablesConvenio.vNumDocIdent,posRowNumDocCli,COL_DATO);
              }
              
              if(codigoCampo.equals(ConstantsConvenio.CAMPO_CODIGO_DEPENDIENTE))
              {
                int posRowCodCliDep = getPosNomCli(ConstantsConvenio.CAMPO_CODIGO_DEPENDIENTE);
                int posRowNomCliDep = getPosNomCli(ConstantsConvenio.CAMPO_NOMBRE_DEPENDIENTE);
                VariablesConvenio.vCodTrabDependiente="";
                VariablesConvenio.vNomTrabDependiente="";
                tblLista.setValueAt(VariablesConvenio.vCodTrabDependiente,posRowCodCliDep,COL_DATO);
                tblLista.setValueAt(VariablesConvenio.vNomTrabDependiente,posRowNomCliDep,COL_DATO);
              }              
            }
          }
        } else if ( e.getKeyCode()==KeyEvent.VK_ENTER ) {
          moveFocusTo(tblLista.getSelectedRow());
        } 
        else {
          if ( e.getKeyCode()!=KeyEvent.VK_LEFT && 
               e.getKeyCode()!=KeyEvent.VK_RIGHT &&
               e.getKeyCode()!=KeyEvent.VK_DELETE &&
               e.getKeyCode()!=KeyEvent.VK_BACK_SPACE &&
               e.getKeyCode()!=KeyEvent.VK_HOME &&
               (!Character.isLetter(e.getKeyChar()) && !Character.isWhitespace(e.getKeyChar()) && !Character.isDigit(e.getKeyChar())) )
            pJTextField.setText(pJTextField.getText().toUpperCase());
            pJTextField2.setText(pJTextField2.getText().toUpperCase());
            pJTextField3.setText(pJTextField3.getText().toUpperCase());            
        }        
      }
    });
  }
  
  /**
   * Para el Num_Doc_Iden del trabajador
   * @author JCORTEZ
   * @since  26.02.2008
   */
  private void addKeyListenerToTextField4(final JTextField pJTextField,
                                         final JTextField pJTextField2,
                                         final JTextField pJTextField3,
                                         final String pTipoCampo,
                                         final String pInSoloLectura,
                                         final String pTipoMaestro,
                                         final JTextField pCodMaestro) {
      
                                         
    pJTextField.addKeyListener(new KeyAdapter() {
    
      public void keyTyped(KeyEvent e) {      
        if ( pInSoloLectura.equalsIgnoreCase("S") ) {
          e.consume();
        } else {
          if ( pTipoCampo.equalsIgnoreCase("E") )
            FarmaUtility.admitirDigitos(pJTextField,e);
          else if ( pTipoCampo.equalsIgnoreCase("D") )
            FarmaUtility.admitirDigitosDecimales(pJTextField,e);
        }
      }
      public void keyPressed(KeyEvent e) {
        int vFila = tblLista.getSelectedRow();
        String vIndBusqueda = FarmaUtility.getValueFieldJTable(tblLista,vFila,COL_IND_BUS);
        
        if ( e.getKeyCode()==KeyEvent.VK_ENTER ) {
          if(vIndBusqueda.equalsIgnoreCase("T"))
          {
            /*
            Si el campo de documento de identidad permite la busqueda por texto,
            al preciosar ENTER se busca el cliente segun el numero ingresado. 
            */
            String vNumDoc = txtNumeroDocumento.getText().trim();
            
            cargaNombreClienteNumDoc(vNumDoc);
            //
            pCodMaestro.setText(VariablesConvenio.vCodCliente);
            pJTextField.setText(VariablesConvenio.vNumDocIdent);
            pJTextField2.setText(VariablesConvenio.vNomCliente);
            pJTextField3.setText(VariablesConvenio.vCodTrab);
            
            //COLOCALNDO el NOMBRE EN LA CELDA QUE DEBE ESTAR EL NOMBRE POR EL
            //CAMPO DE CODIGO DE FORMULARIO
            int posRowNomCli = getPosNomCli(ConstantsConvenio.CAMPO_CODIGO_TRABAJADOR);
            tblLista.setValueAt(VariablesConvenio.vNomCliente,posRowNomCli,COL_DATO);
            
            String codigoCampo = "";
            for (int i=0; i<tblLista.getRowCount(); i++) {
              codigoCampo = FarmaUtility.getValueFieldJTable(tblLista,vFila,COL_COD);
            
              if(codigoCampo.equals(ConstantsConvenio.CAMPO_NUMERO_DOCUMENTO)){
                int posRowTraCli = getPosNomCli(ConstantsConvenio.CAMPO_CODIGO_TRABAJADOR_EMPRESA);
                
                if(posRowTraCli > 0)
                {
                  tblLista.setValueAt(VariablesConvenio.vCodTrab,posRowTraCli,COL_DATO);
                }
              }
            }
            
            tblLista.repaint();
          }
          
          e.consume();
          if ( (vFila+1)==tblLista.getRowCount() )
            FarmaUtility.setearRegistro(tblLista,vFila,null,0);
          else  FarmaUtility.setearRegistro(tblLista,vFila+1,null,0);
          
          pJTextField.setText(pJTextField.getText().toUpperCase());
        } else tblLista_keyPressed(e);
      }
      public void keyReleased(KeyEvent e) {
        int vFila = tblLista.getSelectedRow();
        String vIndBusqueda = FarmaUtility.getValueFieldJTable(tblLista,vFila,COL_IND_BUS);
        
        if ( mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e) && pJTextField.isDisplayable() 
            && !vIndBusqueda.equalsIgnoreCase("T")) {
          pJTextField.selectAll();
          VariablesConvenio.vTipoFiltro=ConstantsConvenio.CAMPO_NUMERO_DOCUMENTO;
          //mostrar dialogo
          DlgListaClientesConv dlgListaClientesConv = new DlgListaClientesConv(myParentFrame,"",true);
          dlgListaClientesConv.setVisible(true);
          VariablesConvenio.vTipoFiltro="";
          if(FarmaVariables.vAceptar)
          {
            pCodMaestro.setText(VariablesConvenio.vCodCliente);
            pJTextField.setText(VariablesConvenio.vNumDocIdent);
            pJTextField2.setText(VariablesConvenio.vNomCliente);
            pJTextField3.setText(VariablesConvenio.vCodTrab);//cod_trab_empresa
            
            //COLOCALNDO el NOMBRE EN LA CELDA QUE DEBE ESTAR EL NOMBRE POR EL
            //CAMPO DE CODIGO DE FORMULARIO
            int posRowNomCli = getPosNomCli(ConstantsConvenio.CAMPO_CODIGO_TRABAJADOR);
            tblLista.setValueAt(VariablesConvenio.vNomCliente,posRowNomCli,COL_DATO);
            
            String codigoCampo = "";
            for (int i=0; i<tblLista.getRowCount(); i++) {
              codigoCampo = FarmaUtility.getValueFieldJTable(tblLista,vFila,COL_COD);
              
              if(codigoCampo.equals(ConstantsConvenio.CAMPO_NUMERO_DOCUMENTO)){
                int posRowTraCli = getPosNomCli(ConstantsConvenio.CAMPO_CODIGO_TRABAJADOR_EMPRESA);
                tblLista.setValueAt(VariablesConvenio.vCodTrab,posRowTraCli,COL_DATO);
              }
            }
           
            tblLista.repaint();
          }
          
        } else if ( mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e) && pJTextField.isDisplayable() 
                    && vIndBusqueda.equalsIgnoreCase("T")) {
          /*
          Si el campo de documento de identidad permite la busqueda por texto,
          al presionar F1 se borra el dato ingresado.
          */
          VariablesConvenio.vCodCliente = "";
          VariablesConvenio.vNumDocIdent = "";
          VariablesConvenio.vNomCliente = "";
          VariablesConvenio.vCodTrab = "";
          
          pCodMaestro.setText("");
          pJTextField.setText("");
          pJTextField2.setText("");
          pJTextField3.setText("");
          
          int posRow;
          
          posRow = getPosNomCli(ConstantsConvenio.CAMPO_CODIGO_TRABAJADOR);
          if(posRow > -1)
            tblLista.setValueAt("",posRow,COL_DATO);
          
          posRow = getPosNomCli(ConstantsConvenio.CAMPO_CODIGO_TRABAJADOR_EMPRESA);
          if(posRow > -1)
            tblLista.setValueAt("",posRow,COL_DATO);
          
          posRow = getPosNomCli(ConstantsConvenio.CAMPO_NUMERO_DOCUMENTO);
          if(posRow > -1)
            tblLista.setValueAt("",posRow,COL_DATO);
          
          txtNumeroDocumento.setEditable(true);
          tblLista.repaint();
        }
        else if ( e.getKeyCode()==KeyEvent.VK_ENTER ) {
          moveFocusTo(tblLista.getSelectedRow());
        } 
        else {
          if ( e.getKeyCode()!=KeyEvent.VK_LEFT && 
               e.getKeyCode()!=KeyEvent.VK_RIGHT &&
               e.getKeyCode()!=KeyEvent.VK_DELETE &&
               e.getKeyCode()!=KeyEvent.VK_BACK_SPACE &&
               e.getKeyCode()!=KeyEvent.VK_HOME &&
               (!Character.isLetter(e.getKeyChar()) && !Character.isWhitespace(e.getKeyChar()) && !Character.isDigit(e.getKeyChar())) )
            pJTextField.setText(pJTextField.getText().toUpperCase());
            pJTextField2.setText(pJTextField2.getText().toUpperCase());
            pJTextField3.setText(pJTextField3.getText().toUpperCase());
        }
      }
    });
  }

  /**
   * Para el codigo de trabajador dependiente
   * @author JCORTEZ
   * @since  13.03.2008
   */
  private void addKeyListenerToTextField5(final JTextField pJTextField,
                                         final JTextField pJTextField2,
                                         final JTextField pJTextField3,
                                         final String pTipoCampo,
                                         final String pInSoloLectura,
                                         final String pTipoMaestro,
                                         final JTextField pCodMaestro) {
      
                                         
    pJTextField.addKeyListener(new KeyAdapter() {
    
      public void keyTyped(KeyEvent e) {
        if ( pInSoloLectura.equalsIgnoreCase("S") ) {
          e.consume();
        } else {
          if ( pTipoCampo.equalsIgnoreCase("E") )
            FarmaUtility.admitirDigitos(pJTextField,e);
          else if ( pTipoCampo.equalsIgnoreCase("D") )
            FarmaUtility.admitirDigitosDecimales(pJTextField,e);
        }
      }
      public void keyPressed(KeyEvent e) {
        if ( e.getKeyCode()==KeyEvent.VK_ENTER ) {
          e.consume();
          int vFila = tblLista.getSelectedRow();
          
          if ( (vFila+1)==tblLista.getRowCount() )
            FarmaUtility.setearRegistro(tblLista,vFila,null,0);
          else  FarmaUtility.setearRegistro(tblLista,vFila+1,null,0);
          
          pJTextField.setText(pJTextField.getText().toUpperCase());
        } else tblLista_keyPressed(e);
      }
      public void keyReleased(KeyEvent e) {
        if ( mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e) && pJTextField.isDisplayable() ) {
          pJTextField.selectAll();
          //mostrar dialogo
          
          //se escoge el tipo de filtro
          //VariablesConvenio.vTipoFiltro=ConstantsConvenio.CAMPO_NUMERO_DOCUMENTO;
          
          //FALTA EL CODIGO DE TRABAJADOR
          VariablesConvenio.vCodClienteBusqueda= VariablesConvenio.vCodCliente;
                                            /* = FarmaUtility.getValueFieldJTable(tbDependientes,
                                                                                tbDependientes.getSelectedRow(),
                                                                                COLUMN_COD_CLI);*/
          VariablesConvenio.vDatosClienteBusqueda=VariablesConvenio.vNomCliente;
                                            /* = FarmaUtility.getValueFieldJTable(tbDependientes,
                                                                                tbDependientes.getSelectedRow(),
                                                                                COLUMN_DESC_CLI);*/
                                                                              
          pJTextField.selectAll();
          
          DlgListaDependientesClientesConv dlgDependientes = new DlgListaDependientesClientesConv(myParentFrame,"",true);
          dlgDependientes.setVisible(true);                     
          if(FarmaVariables.vAceptar)
          {
            pCodMaestro.setText(VariablesConvenio.vCodClienteDependiente);
            pJTextField.setText(VariablesConvenio.vCodTrabDependiente);
            pJTextField2.setText(VariablesConvenio.vNomTrabDependiente);
            pJTextField3.setText(VariablesConvenio.vNumDocIdent);
            
            //COLOCALNDO el NOMBRE EN LA CELDA QUE DEBE ESTAR EL NOMBRE POR EL
            //CAMPO DE CODIGO DE FORMULARIO
            int posRowNomCli = getPosNomCli(ConstantsConvenio.CAMPO_NOMBRE_DEPENDIENTE);
            tblLista.setValueAt(VariablesConvenio.vNomTrabDependiente,posRowNomCli,COL_DATO);
            
            tblLista.repaint();
          }
          
        } else if ( e.getKeyCode()==KeyEvent.VK_ENTER ) {
          moveFocusTo(tblLista.getSelectedRow());
        } 
        else {
          if ( e.getKeyCode()!=KeyEvent.VK_LEFT && 
               e.getKeyCode()!=KeyEvent.VK_RIGHT &&
               e.getKeyCode()!=KeyEvent.VK_DELETE &&
               e.getKeyCode()!=KeyEvent.VK_BACK_SPACE &&
               e.getKeyCode()!=KeyEvent.VK_HOME &&
               (!Character.isLetter(e.getKeyChar()) && !Character.isWhitespace(e.getKeyChar()) && !Character.isDigit(e.getKeyChar())) )
            pJTextField.setText(pJTextField.getText().toUpperCase());
            pJTextField2.setText(pJTextField2.getText().toUpperCase());
        }

      }
    });
  }
  
  
  private void moveFocusTo(int pRow) {
    FarmaUtility.setearRegistro(tblLista,pRow,null,0);
    tblLista.changeSelection(pRow,1,false,false);
  }
  
  private void this_windowOpened(WindowEvent e)
  {
    FarmaUtility.centrarVentana(this);
    initTable();
    FarmaUtility.moveFocus(tblLista);
    tblLista.changeSelection(0,1,false,false);    
  }

  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, 
                             "Debe presionar la tecla ESC para cerrar la ventana.", 
                             null);
  }

  private void btnLista_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocus(tblLista);    
  }

  private void tblLista_keyPressed(KeyEvent e)
  {
    chkKeyPressed(e);
  }
  
  /* ************************************************************************ */
  /*                     METODOS AUXILIARES DE EVENTOS                        */
  /* ************************************************************************ */

  private void chkKeyPressed(KeyEvent e)
  {
    //FarmaGridUtils.aceptarTeclaPresionada(e, tblLista, null, 0);
            
    if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e))
    {
      if(FarmaVariables.vEconoFar_Matriz){
        FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,null);
        return;
      }
      //checkComboBoxComponent();
    }else if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F11(e))
    {
      if(FarmaVariables.vEconoFar_Matriz){
        FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,null);
        return;
      }
      funcionF11();
    }
    else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      cerrarVentana(false);
    }
  }

  public void txtTrabajador_keyTyped(KeyEvent e)
  {
    FarmaUtility.admitirLetras(txtTrabajador, e);
  }

  public void txtApellidoPaterno_keyTyped(KeyEvent e)
  {
    FarmaUtility.admitirLetras(txtApellidoPaterno, e);
  }

  public void txtApellidoMaterno_keyTyped(KeyEvent e)
  {
    FarmaUtility.admitirLetras(txtApellidoMaterno, e);
  }

  public void txtNumeroDocumento_keyTyped(KeyEvent e)
  {
    FarmaUtility.admitirDigitos(txtNumeroDocumento, e);
  }

  public void txtTelefono_keyTyped(KeyEvent e)
  {
    FarmaUtility.admitirDigitos(txtTelefono, e);
  }

  public void txtCodigoInternoCliente_keyTyped(KeyEvent e)
  {
    FarmaUtility.admitirDigitos(txtCodigoInternoCliente, e);
  }
  
  private void cerrarVentana(boolean pAceptar)
  {
    FarmaVariables.vAceptar = pAceptar;
    this.setVisible(false);
    this.dispose();
  }

  /* ************************************************************************ */
  /*                     METODOS DE LOGICA DE NEGOCIO                         */
  /* ************************************************************************ */
  
  private void funcionF11()
  {
    if(validateMandatory() && validaIngresoNumDoc())
    {
      String pCodTrabajadorEmpresa =  getCampoTabla(ConstantsConvenio.CAMPO_CODIGO_TRABAJADOR_EMPRESA);
      
      VariablesConvenio.vArrayList_DatosConvenio.clear();
      VariablesConvenio.vArrayList_DatosConvenio.add(VariablesConvenio.vCodConvenio);
      VariablesConvenio.vArrayList_DatosConvenio.add(vCodTrabajador.getText());
      VariablesConvenio.vArrayList_DatosConvenio.add(txtNomCliente.getText());
      VariablesConvenio.vArrayList_DatosConvenio.add(txtApellidoPaterno.getText());
      VariablesConvenio.vArrayList_DatosConvenio.add(txtApellidoMaterno.getText());
      VariablesConvenio.vArrayList_DatosConvenio.add(txtNumeroDocumento.getText());
      VariablesConvenio.vArrayList_DatosConvenio.add(txtTelefono.getText());
      VariablesConvenio.vArrayList_DatosConvenio.add(txtCodigoInternoCliente.getText());
      VariablesConvenio.vArrayList_DatosConvenio.add(txtTrabajador.getText()+" "+
                                                     txtNomCliente.getText().trim()+" "+
                                                     txtApellidoPaterno.getText().trim()+" "+
                                                     txtApellidoMaterno.getText().trim());
      VariablesConvenio.vArrayList_DatosConvenio.add(pCodTrabajadorEmpresa);
      VariablesConvenio.vArrayList_DatosConvenio.add(VariablesConvenio.vCodClienteDependiente);
     
      VariablesConvenio.vTextoCliente = txtTrabajador.getText()+ 
                                        txtNomCliente.getText()+" "+
                                        txtApellidoPaterno.getText()+" "+
                                        txtApellidoMaterno.getText()+
                                        " ("+VariablesConvenio.vCodTrab+")";
      
      cerrarVentana(true);
    }
  }
 
  private boolean validateMandatory() 
  {
    boolean dataExists = true;
    String vIndObligatorio;
    String vCodCampo;
    String vDato;
    
    for (int i=0; i<tblLista.getRowCount(); i++) {
      vIndObligatorio = FarmaUtility.getValueFieldJTable(tblLista,i,COL_IND_OBLI);
      
      if ( vIndObligatorio.equalsIgnoreCase("S") ) {        
        vCodCampo = FarmaUtility.getValueFieldJTable(tblLista,i,COL_COD);
        vDato = FarmaUtility.getValueFieldJTable(tblLista,i,COL_DATO); 
        
        if ( ( vCodCampo.equalsIgnoreCase(ConstantsConvenio.CAMPO_CODIGO_TRABAJADOR) && getCodTrabajador().equals("")) 
            || vDato.length()==0 )
        {
            dataExists = false;
            moveFocusTo(i);
            FarmaUtility.showMessage(this, "Campo " + findFieldDescription(vCodCampo) +
                                     " no tiene información.  Verifique !!!", null );
            break;
          
        }        
     }
   }
   return dataExists;
  }
  
  private String findFieldDescription(String pFieldValue) 
  {
    String fieldDescription = "NO DETERMINADO";
    for (int i=0; i<aCampos.size(); i++) {
      if ( ((String)((ArrayList)aCampos.get(i)).get(2)).equalsIgnoreCase(pFieldValue) ) {
        fieldDescription = ((String)((ArrayList)aCampos.get(i)).get(0)).trim();
        break;
      }
    }
    return fieldDescription;
  }

  private String getCodTrabajador()
  {
    return vCodTrabajador.getText().trim();
  }  
  
  /**
   * Busca la Posicion Fila 
   * @author dubilluz
   * @since  17.08.2007
   */
  private int getPosNomCli(String pCodCampo)
  {
    String codTemp = "";
    for(int i=0 ; i<tblLista.getRowCount();i++){
      codTemp = FarmaUtility.getValueFieldJTable(tblLista,i,COL_COD);
      
      if(codTemp.equalsIgnoreCase(pCodCampo.trim()))
        return i;
    }
    return -1;
  }
  
  /**
   * Retorna el Valor de Donde esta el CAmpo con el codigo de Formulario
   * @author dubilluz
   * @since  17.07.2007
   */
  private String getCampoTabla(String pCodFormulario)
  { 
    int pos = getPosNomCli(pCodFormulario);
    
    if(pos!=-1)
      return  FarmaUtility.getValueFieldJTable(tblLista,pos,COL_DATO);
    else
      return " ";
  }

  /**
   * Carga el nombre del cliente.
   * @param pNumDocIden
   * @author Edgar Rios Navarro
   * @since 13.06.2008
   */
  private void cargaNombreClienteNumDoc(String pNumDocIden)
  {
    ArrayList info = new ArrayList();
    try
    {
      info = DBConvenio.obtenerNombreClienteNumdoc(pNumDocIden);
      if(info.size()>0)
      {
        VariablesConvenio.vCodCliente = FarmaUtility.getValueFieldArrayList(info,0,0);
        VariablesConvenio.vNumDocIdent = pNumDocIden; 
        VariablesConvenio.vNomCliente = FarmaUtility.getValueFieldArrayList(info,0,1);
        VariablesConvenio.vCodTrab = FarmaUtility.getValueFieldArrayList(info,0,2);  
        txtNumeroDocumento.setEditable(false);
      }else
      {
        VariablesConvenio.vCodCliente = "";
        VariablesConvenio.vNumDocIdent = ""; 
        VariablesConvenio.vNomCliente = "";
        VariablesConvenio.vCodTrab = "";
        
        FarmaUtility.showMessage(this,"No se encuentra el número ingresado.",null);
      }
    } catch(SQLException sql)
    {
      log.error(null,sql.fillInStackTrace());
      FarmaUtility.showMessage(this,"Ocurrio un error al buscar el numero de documento \n" + sql.getMessage(),tblLista);
    }
  }

  /**
   * Verifica la busqueda del documento.
   * @return
   * @author Edgar Rios Navarro
   * @since 13.06.2008
   */
  private boolean validaIngresoNumDoc()
  {
    boolean retorno = true;
    boolean valor = txtNumeroDocumento.isEditable();
    
    int pos = getPosNomCli(ConstantsConvenio.CAMPO_NUMERO_DOCUMENTO);
    
    if(valor & pos!=-1)
    {
      retorno = false;
      FarmaUtility.showMessage(this,"Verifique que el cliente haya sido encontrado en el sistema.",tblLista);
    }
    
    return retorno;
  }
}
