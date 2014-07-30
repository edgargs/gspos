package mifarma.ptoventa.ce;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.*;
import java.sql.*;
import mifarma.ptoventa.caja.reference.*;
import mifarma.ptoventa.reference.*;
import mifarma.ptoventa.ce.reference.*;
import java.awt.event.KeyListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaEliminacionCuadratura.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * PAULO      31.07.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class DlgListaEliminacionCuadratura
  extends JDialog
{
  /* ********************************************************************** */
  /*                        DECLARACION PROPIEDADES                         */
  /* ********************************************************************** */
  private static final Logger log = LoggerFactory.getLogger(DlgListaEliminacionCuadratura.class);

  Frame myParentFrame;
  FarmaTableModel tableModel;

  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanelWhite jContentPane = new JPanelWhite();
  private JLabelFunction lblEsc = new JLabelFunction();
  private JLabelFunction lblF5 = new JLabelFunction();
  private JScrollPane scrLista = new JScrollPane();
  private JTable tblListaEliminacion = new JTable();
  private JPanelTitle pnlTitle1 = new JPanelTitle();
  private JButtonLabel btnLista = new JButtonLabel();
  private JLabelFunction lblEnter = new JLabelFunction();
  private JLabelFunction lblF1 = new JLabelFunction();

  /* ********************************************************************** */
  /*                        CONSTRUCTORES                                   */
  /* ********************************************************************** */

  public DlgListaEliminacionCuadratura()
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

  public DlgListaEliminacionCuadratura(Frame parent, String title, boolean modal)
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
    this.setSize(new Dimension(514, 297));
    this.setDefaultCloseOperation(0);
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
    lblEsc.setBounds(new Rectangle(405, 245, 95, 20));
    lblEsc.setText("[ ESC ] Cerrar");
    lblF5.setBounds(new Rectangle(175, 245, 105, 20));
    lblF5.setText("[ F5 ] Eliminar");
    scrLista.setBounds(new Rectangle(10, 30, 490, 205));
    tblListaEliminacion.addKeyListener(new KeyAdapter()
      {
        public void keyPressed(KeyEvent e)
        {
          tblLista_keyPressed(e);
        }
      });
    pnlTitle1.setBounds(new Rectangle(10, 10, 490, 20));
    btnLista.setText("Lista");
    btnLista.setBounds(new Rectangle(5, 0, 105, 20));
    btnLista.setMnemonic('L');
    btnLista.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            btnLista_actionPerformed(e);
          }
        });
    lblEnter.setBounds(new Rectangle(10, 245, 155, 20));
    lblEnter.setText("[ ENTER ] Seleccionar");
    lblF1.setBounds(new Rectangle(290, 245, 105, 20));
    lblF1.setText("[ F1 ] Ingresar");
    scrLista.getViewport().add(tblListaEliminacion, null);
    pnlTitle1.add(btnLista, null);
    jContentPane.add(lblF1, null);
    jContentPane.add(lblEnter, null);
    jContentPane.add(pnlTitle1, null);
    jContentPane.add(scrLista, null);
    jContentPane.add(lblF5, null);
    jContentPane.add(lblEsc, null);
    this.getContentPane().add(jContentPane, BorderLayout.CENTER);
  }

  /* ************************************************************************ */
  /*                                  METODO initialize                       */
  /* ************************************************************************ */

  private void initialize()
  {
    FarmaVariables.vAceptar = false;
    VariablesCajaElectronica.listaEliminacion.clear();
    this.setTitle(VariablesCajaElectronica.vDescCuadratura);
    if(VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_DINERO_FALSO))
    {
      initTableDineroFalso();
    } else if (VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_ROBO))
    {
      initTableRobo(); 
    }else if (VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_COTIZA_COMPETENCIA_CAJERO))
    {
      initTableCotizacionCompetencia(); //ASOSA, 10.08.2010
    }
    else initTableOtrasCuadraturas();
    
  }

  /* ************************************************************************ */
  /*                            METODOS INICIALIZACION                        */
  /* ************************************************************************ */

  private void initTableDineroFalso()
  {
    tableModel = new FarmaTableModel(ConstantsCajaElectronica.columsListaEliminacionDineroFalso, ConstantsCajaElectronica.defaultListaEliminacionDineroFalso, 0);
    FarmaUtility.initSelectList(tblListaEliminacion, tableModel,ConstantsCajaElectronica.columsListaEliminacionDineroFalso);
  }
  
  private void initTableRobo()
  {
    tableModel = new FarmaTableModel(ConstantsCajaElectronica.columsListaEliminacionRobo, ConstantsCajaElectronica.defaultListaEliminacionRobo, 0);
    FarmaUtility.initSelectList(tblListaEliminacion, tableModel,ConstantsCajaElectronica.columsListaEliminacionRobo);
  }
  
  private void initTableOtrasCuadraturas()
  {
    tableModel = new FarmaTableModel(ConstantsCajaElectronica.columsListaEliminacionOtrasCuadraturas, ConstantsCajaElectronica.defaultListaEliminacionOtrasCuadraturas, 0);
    FarmaUtility.initSelectList(tblListaEliminacion, tableModel,ConstantsCajaElectronica.columsListaEliminacionOtrasCuadraturas);
  }
  
    private void initTableCotizacionCompetencia()
    {
        log.debug("BBBBBBBBBBB");
      tableModel = new FarmaTableModel(ConstantsCajaElectronica.columnsEliminacionCotizacionCompetencia_Turno, ConstantsCajaElectronica.defaultValuesEliminacionCotizacionCompetencia_Turno, 0);
      FarmaUtility.initSelectList(tblListaEliminacion, tableModel,ConstantsCajaElectronica.columnsEliminacionCotizacionCompetencia_Turno);
        log.debug("AAAAAAAAAAAAAaa");
    }



  /* ************************************************************************ */
  /*                            METODOS DE EVENTOS                            */
  /* ************************************************************************ */

  private void this_windowOpened(WindowEvent e)
  {
    if(tblListaEliminacion.getRowCount()>0)
      FarmaUtility.moveFocus(tblListaEliminacion);
    if(VariablesCajaElectronica.vTipCuadratura.equals(""))
      lblF1.setVisible(false);
    FarmaUtility.centrarVentana(this);
    cargaLista();
  }

  private void this_windowClosing(WindowEvent e)
  {
    FarmaUtility.showMessage(this, 
                             "Debe presionar la tecla ESC para cerrar la ventana.", 
                             null);
  }

  private void btnLista_actionPerformed(ActionEvent e)
  {
    FarmaUtility.moveFocusJTable(tblListaEliminacion);
  }

  private void tblLista_keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      if(tblListaEliminacion.getRowCount()<=0) return;
      /*if( VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_REGULARIZACION_ANULADO_PENDIENTE) ||
          VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_REGULARIZACION_COMP_MANUAL) )
      {
        FarmaUtility.showMessage(this, "No es posible realizar la operación.\nEsta cuadratura no puede ser eliminada.", tblListaEliminacion);
        return;
      }*/
      e.consume();
      UtilityCajaElectronica.getIndicadorVB(VariablesCajaElectronica.vSecMovCaja,ConstantsCajaElectronica.TIPO_VB_CAJERO);      
      if(VariablesCajaElectronica.vUsuarioCajero && VariablesCajaElectronica.vIndVBCajero.equals(FarmaConstants.INDICADOR_N)){  
      verificaCheckJTable();
      } else FarmaUtility.showMessage(this, "No es posible realizar esta operacion.", tblListaEliminacion);  
		}
    else chkKeyPressed(e);
  }
  
  /* ************************************************************************ */
  /*                     METODOS AUXILIARES DE EVENTOS                        */
  /* ************************************************************************ */

  private void chkKeyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_F5)
    {
      if(tblListaEliminacion.getRowCount()<=0) return;
      if(FarmaVariables.vEconoFar_Matriz){
        FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,tblListaEliminacion);
        return;
      }
      UtilityCajaElectronica.getIndicadorVB(VariablesCajaElectronica.vSecMovCaja,ConstantsCajaElectronica.TIPO_VB_CAJERO);
      if(VariablesCajaElectronica.vUsuarioCajero && VariablesCajaElectronica.vIndVBCajero.equals(FarmaConstants.INDICADOR_N))
      {
        funcionEliminar();
      } else FarmaUtility.showMessage(this, "No es posible realizar esta operacion.", tblListaEliminacion);
    }
    else if(mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F1(e))
    {
      if(lblF1.isVisible()){
        if(FarmaVariables.vEconoFar_Matriz){
          FarmaUtility.showMessage(this,ConstantsPtoVenta.MENSAJE_MATRIZ,tblListaEliminacion);
          return;
        }
        UtilityCajaElectronica.getIndicadorVB(VariablesCajaElectronica.vSecMovCaja,ConstantsCajaElectronica.TIPO_VB_CAJERO);
        if(VariablesCajaElectronica.vUsuarioCajero && VariablesCajaElectronica.vIndVBCajero.equals(FarmaConstants.INDICADOR_N)){
          if(VariablesCajaElectronica.vTipCuadratura.equals("01"))
          {
            cargaDatosCuadraturaIngreso();
          }
          else if(VariablesCajaElectronica.vTipCuadratura.equals("02"))
          {
            cargaListaCuadraturaIngreso();
          }else if(VariablesCajaElectronica.vTipCuadratura.equals("05")){
                cargaListaCotizacionCompetencia(); //ASOSA, 10.08.2010
            }
        } else FarmaUtility.showMessage(this, "No es posible realizar esta operacion.", tblListaEliminacion);
      }
    }
    else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      cerrarVentana(false);
    }
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
  
  private void funcionEliminar()
  {
    evaluaEliminacionMultiple();
  }
  
  private void cargaLista()
  {
    try
    {
      DBCajaElectronica.listaEliminacionCuadratura(tableModel,
                                                   VariablesCajaElectronica.vSecMovCaja,
                                                   VariablesCajaElectronica.vCodCuadratura);
      VariablesCajaElectronica.listaEliminacion.clear();                                           
      if(tblListaEliminacion.getRowCount()>0){
        FarmaUtility.ordenar(tblListaEliminacion,tableModel,1,FarmaConstants.ORDEN_DESCENDENTE);
        FarmaUtility.moveFocusJTable(tblListaEliminacion);
      } else FarmaUtility.moveFocus(tblListaEliminacion);
    } catch (SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this,"Ocurrio un error al listar \n" + sql.getMessage(),null);
    }
  }
  
  private void evaluaEliminacionMultiple()
  {
    log.debug("size : " + VariablesCajaElectronica.listaEliminacion.size());
    log.debug("array : " + VariablesCajaElectronica.listaEliminacion);
    if(VariablesCajaElectronica.listaEliminacion.size()>0)
    {
      obtieneVariablesEliminacion();
		}
  }
  
  private void obtieneVariablesEliminacion()
  {
    boolean funcionEliminar = false ; 
    if (com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,"¿Está seguro de eliminar la(s) cuadratura(s)?"))
    {
      if( VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_REGULARIZACION_ANULADO_PENDIENTE) ||
          VariablesCajaElectronica.vCodCuadratura.equalsIgnoreCase(ConstantsCajaElectronica.CUADRATURA_REGULARIZACION_COMP_MANUAL) )
      {
        if( !cargaLoginOper() )
        {
          FarmaUtility.liberarTransaccion();
          FarmaUtility.showMessage(this,"No se realizó la operación. Solo un usuario con Rol de\nOperador de Sistemas puede eliminar esta cuadratura.", tblListaEliminacion);
          return;
        }
      }
      try
      {
        for(int i=0; i<VariablesCajaElectronica.listaEliminacion.size();i++)
        {
          VariablesCajaElectronica.vSecMovCaja = (String)(((ArrayList)VariablesCajaElectronica.listaEliminacion.get(i)).get(0));
          VariablesCajaElectronica.vIndVBCajero = (String)(((ArrayList)VariablesCajaElectronica.listaEliminacion.get(i)).get(1)); 
          VariablesCajaElectronica.vSecCuadratura = (String)(((ArrayList)VariablesCajaElectronica.listaEliminacion.get(i)).get(2));
          if(VariablesCajaElectronica.vIndVBCajero.equalsIgnoreCase(FarmaConstants.INDICADOR_N))
          {
            if(!evaluaValidacionEliminacion()){
              log.debug("entro a false");
              FarmaUtility.showMessage(this,"No puede eliminar el registro. Primero elimine la(s)\ncuadratura(s) que han sido originadas por esta.",tblListaEliminacion);
            } else{
              log.debug("entro a true");
              eliminaCuadratura();
              funcionEliminar = true ; 
            }
          } else{
            FarmaUtility.showMessage(this, "No se puede eliminar el registro. El Movimiento de Caja ya cuenta con VB de Cajero.",tblListaEliminacion);
            break;
          }
        }
        if (funcionEliminar){
          FarmaUtility.aceptarTransaccion();
          cargaLista();
        }
      } catch (SQLException sql)
      {
        FarmaUtility.liberarTransaccion(); 
        log.error("",sql);
        FarmaUtility.showMessage(this,"Error al eliminar la(s) cuadratura(s)\n" + sql.getMessage(),tblListaEliminacion);
      }
    }
  }
  
  private void eliminaCuadratura() throws SQLException
      {
        DBCajaElectronica.eliminaCuadratura(VariablesCajaElectronica.vCodCuadratura,
                                            VariablesCajaElectronica.vSecMovCaja,
                                            VariablesCajaElectronica.vSecCuadratura );
    
  }
  
  private boolean evaluaValidacionEliminacion()
  {
    VariablesCajaElectronica.vValidaEliminacionCuadratura = 0;
    try
    {
      VariablesCajaElectronica.vValidaEliminacionCuadratura = DBCajaElectronica.validacionEliminacion(VariablesCajaElectronica.vCodCuadratura,
                                                                                                      VariablesCajaElectronica.vSecMovCaja,
                                                                                                      VariablesCajaElectronica.vSecCuadratura);
      log.debug("VariablesCajaElectronica.vValidaEliminacionCuadratura : " + VariablesCajaElectronica.vValidaEliminacionCuadratura);
     if( VariablesCajaElectronica.vValidaEliminacionCuadratura == 0 ) return true;
     else return false;
    } catch (SQLException sql)
    {
      log.error("",sql);
      FarmaUtility.showMessage(this,"Ocurrio un error al obtener informacion \n" + sql.getMessage(),null);
      return false;
    }
  }
  
  private void cargaDatosCuadraturaIngreso()
  {
    DlgDatosCuadratura dlgDatosCuadratura = new DlgDatosCuadratura(myParentFrame,"",true);
    dlgDatosCuadratura.setVisible(true);
    if(FarmaVariables.vAceptar){
      VariablesCajaElectronica.listaEliminacion.clear();
      cargaLista();
      FarmaVariables.vAceptar = false;
    }
  }
  
  private void cargaListaCuadraturaIngreso()
  {
    DlgListaCuadratura dlgListaCuadratura = new DlgListaCuadratura(myParentFrame,"",true);
    dlgListaCuadratura.setVisible(true);
    if(FarmaVariables.vAceptar){
      VariablesCajaElectronica.listaEliminacion.clear();
      cargaLista();
      FarmaVariables.vAceptar = false;
    } else cargaLista();
  }
  
        /**
         * Para listar campos CotizacionCompetencia
         * @author ASOSA
         * @since 10.08.2010
         */
        private void cargaListaCotizacionCompetencia()
        {
            DlgCotizacionCompetencia dlgCotizacionCompetencia = new DlgCotizacionCompetencia(myParentFrame,"",true);
            dlgCotizacionCompetencia.setVisible(true);
            if(FarmaVariables.vAceptar){
              VariablesCajaElectronica.listaEliminacion.clear();
              cargaLista();
              FarmaVariables.vAceptar = false;
            }
        }
  
  private void verificaCheckJTable()
  {
    cargaVariables();
    Boolean valor = (Boolean)(tblListaEliminacion.getValueAt(tblListaEliminacion.getSelectedRow(),0));
    seleccionaDeseleccionaRegistro();
  }
  
  private void seleccionaDeseleccionaRegistro()
  {
    Boolean valorTmp = (Boolean)(tblListaEliminacion.getValueAt(tblListaEliminacion.getSelectedRow(),0));
    FarmaUtility.setCheckValue(tblListaEliminacion,false);
    Boolean valor = (Boolean)(tblListaEliminacion.getValueAt(tblListaEliminacion.getSelectedRow(),0));
    operaRegistroSeleccionadoEnArrayList(valor);
  }
  
  private void operaRegistroSeleccionadoEnArrayList(Boolean valor)
  {
    ArrayList myArray = new ArrayList();
    myArray.add(VariablesCajaElectronica.vSecMovCaja);
    myArray.add(VariablesCajaElectronica.vIndVBCajero);
    myArray.add(VariablesCajaElectronica.vSecCuadratura);
    FarmaUtility.operaListaProd(VariablesCajaElectronica.listaEliminacion, myArray, valor, 2);
    log.debug("size : " + VariablesCajaElectronica.listaEliminacion.size());
    log.debug("array : " + VariablesCajaElectronica.listaEliminacion);
  }
  
  private void cargaVariables()
  {
    int row  = tblListaEliminacion.getSelectedRow();
    VariablesCajaElectronica.vSecMovCaja = tblListaEliminacion.getValueAt(row,ConstantsCajaElectronica.INDICE_COLUMNA_SEC_MOV_CAJA).toString().trim();
    VariablesCajaElectronica.vIndVBCajero = tblListaEliminacion.getValueAt(row,ConstantsCajaElectronica.INDICE_COLUMNA_IND_VB).toString().trim();  
    VariablesCajaElectronica.vSecCuadratura = tblListaEliminacion.getValueAt(row,ConstantsCajaElectronica.INDICE_COLUMNA_SEC_CUADRATURA).toString().trim();    
    log.debug("VariablesCajaElectronica.vSecMovCaja : " + VariablesCajaElectronica.vSecMovCaja);
    log.debug("VariablesCajaElectronica.vIndVBCajero : " + VariablesCajaElectronica.vIndVBCajero);
    log.debug("VariablesCajaElectronica.vSecCuadratura  : " + VariablesCajaElectronica.vSecCuadratura);
  }
  
  private boolean cargaLoginOper()
  {
    String numsec = FarmaVariables.vNuSecUsu ;
    String idusu = FarmaVariables.vIdUsu ;
    String nomusu = FarmaVariables.vNomUsu ;
    String apepatusu = FarmaVariables.vPatUsu ;
    String apematusu = FarmaVariables.vMatUsu ;
    
    try{
      DlgLogin dlgLogin = new DlgLogin(myParentFrame,ConstantsPtoVenta.MENSAJE_LOGIN,true);
      dlgLogin.setRolUsuario(FarmaConstants.ROL_OPERADOR_SISTEMAS);
      dlgLogin.setVisible(true);
      FarmaVariables.vNuSecUsu  = numsec ;
      FarmaVariables.vIdUsu  = idusu ;
      FarmaVariables.vNomUsu  = nomusu ;
      FarmaVariables.vPatUsu  = apepatusu ;
      FarmaVariables.vMatUsu  = apematusu ;
    } catch (Exception e)
    {
      FarmaVariables.vNuSecUsu  = numsec ;
      FarmaVariables.vIdUsu  = idusu ;
      FarmaVariables.vNomUsu  = nomusu ;
      FarmaVariables.vPatUsu  = apepatusu ;
      FarmaVariables.vMatUsu  = apematusu ;
      FarmaVariables.vAceptar = false;
      log.error("",e);
      FarmaUtility.showMessage(this,"Ocurrio un error al validar rol de usuariario \n : " + e.getMessage(),null);
    }
    return FarmaVariables.vAceptar;
  }
  
}
