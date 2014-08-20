package mifarma.ptoventa.convenioBTLMF;


import com.gs.mifarma.componentes.JLabelFunction;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaLoadCVL;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.convenioBTLMF.reference.ConstantsConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.UtilityConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.ValidaDatoIngreso;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.hilos.SubProcesosBTLMF;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2011 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgDatosConvenioBTLMF.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * FRAMIREZ      12.11.2011  Creación<br>
 * <br>
 * @author Fredy Ramirez C.<br>
 * @version 1.0<br>
 *
 */

public class DlgDatosConvenioBTLMF extends JDialog {


	private static final Logger log = LoggerFactory.getLogger(DlgDatosConvenioBTLMF.class);

    Frame myParentFrame;
    FarmaTableModel tableModel;

    int altura = 0;
    int pagInicio = 0;
    int pagFinal = 0;

    JEditorPane contenDatosHtml = new JEditorPane();
    JPanel pnlIngresoDatos = new JPanel();
    JPanel pnlPie = new JPanel();
    JPanel pnlInstruccion = new JPanel();


    JLabel lblNombreDato  = new JLabel();
    JPanel pnlObjetoIngreso = new JPanel();
    JLabel lblInstruccion = new JLabel();
    JLabel lblInstruccionFinalizar = new JLabel();

    private JTable tblLista = new JTable();

    int count = 0;


    /*
    ********************************
      DATOS DE INGRESO DE CONVENIO
    ********************************
    INGRESO_TEXTO
      JTextField
    LISTA_PANTALLA
      ** ES PARA MOSTRAR EN OTRA VENTANA
      JTextField - Este Ingresara el Codigo de busqueda
      DlgPantallaBusqueda
                 - Este Tendra un TextField y una Tabla
    LISTA_COMBO
      JCombox - Que listara las opciones.
    PANTALLA_SELECCION - para diagnostico
      Tendra una pantalla Vacia para Seleccionar y llenar los datos.
             Solo para uso de Diagnostico UIE.
     * */
    //INGRESO_TEXTO
    JTextField txtIngresoTexto = new JTextField();


    //LISTA_PANTALLA
    JTextField txtBusqueda = new JTextField();
    //Invoca a la pantalla para la busqueda
    //LISTA_COMBO
    JComboBox cboLista = new JComboBox();
    //PANTALLA_SELECCION
    //JTextField
    //Tabla para seleccion multiple con CHECK.
    JScrollPane scrLista = new JScrollPane();

    String pCodCampoActual = "";
    String codigo = "";
    
    JLabel lblMensaje=new JLabel();



    // **************************************************************************
    // Constructores
    // **************************************************************************

    public DlgDatosConvenioBTLMF() {

    }

    public DlgDatosConvenioBTLMF(Frame parent, String title, boolean modal)
    {
        super(parent, title, modal);
        myParentFrame = parent;
        try
        {
            iniciarVariablesGlobales();
            initialize();
            jbInit();

        } catch (Exception e) {
            log.error("",e);
        }
    }
    // **************************************************************************
    // Método "jbInit()"
    // **************************************************************************
    private void jbInit() throws Exception
    {
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);



        this.addWindowListener(new WindowAdapter()
            {
              public void windowOpened(WindowEvent e)
              {
                this_windowOpened(e);
              }
            });
        txtBusqueda.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                txtBusquedaKeyPressed(e);
            }
        });
        txtIngresoTexto.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e)
            {
                txtIngresoTextoKeyPressed(e);
            }
        });
        this.setTitle("Datos del Convenio: " +VariablesConvenioBTLMF.vNomConvenio.toUpperCase());
    }
    public Container getPanelNorte()
    {
        contenDatosHtml.removeAll();
        contenDatosHtml = new JEditorPane();
        contenDatosHtml.setLayout(null);

        String htmlText = "";
        String datoIngreso [];

        ArrayList listaCampos= null;

        htmlText = htmlText + "<table height = '1px'  width = '450px' border ='0' >";
        if (VariablesConvenioBTLMF.vDatosConvenio != null)
        {
             htmlText = htmlText +"<tr><td height = '1px'><p> ";

          for (int j = 0; j < VariablesConvenioBTLMF.vDatosConvenio.size(); j++)
          {
             String descripcion = "";
             String estilo = "";
             String codigo = "";

             Map convenio = (Map)VariablesConvenioBTLMF.vDatosConvenio.get(j);

             String codTipoCampo = (String)convenio.get(ConstantsConvenioBTLMF.COL_CODIGO_CAMPO);

             String flgLista = convenio.get(ConstantsConvenioBTLMF.COL_FLG_LISTA)!=null?(String)convenio.get(ConstantsConvenioBTLMF.COL_FLG_LISTA):"";

             log.debug("codTipoCampo :"+codTipoCampo);
             log.debug("flgLista     :"+flgLista);


             if (codTipoCampo.equals(ConstantsConvenioBTLMF.CODIGO_NUM_POLIZA))
             {
                 descripcion = (String)VariablesConvenioBTLMF.listaDatosNoEditables.get(ConstantsConvenioBTLMF.CODIGO_NUM_POLIZA);
                 convenio.put(ConstantsConvenioBTLMF.COL_VALOR_IN,descripcion);
                 VariablesConvenioBTLMF.vDatosConvenio.remove(j);
                 VariablesConvenioBTLMF.vDatosConvenio.add(j,convenio);
             }
             else
             if (codTipoCampo.equals(ConstantsConvenioBTLMF.CODIGO_NUM_PLAN))
             {
                 descripcion = (String)VariablesConvenioBTLMF.listaDatosNoEditables.get(ConstantsConvenioBTLMF.CODIGO_NUM_PLAN);
                 convenio.put(ConstantsConvenioBTLMF.COL_VALOR_IN,descripcion);
                 VariablesConvenioBTLMF.vDatosConvenio.remove(j);
                 VariablesConvenioBTLMF.vDatosConvenio.add(j,convenio);
             }
             else
             if (codTipoCampo.equals(ConstantsConvenioBTLMF.CODIGO_COD_ASEGURADO))
             {
                 descripcion = (String)VariablesConvenioBTLMF.listaDatosNoEditables.get(ConstantsConvenioBTLMF.CODIGO_COD_ASEGURADO);
                 convenio.put(ConstantsConvenioBTLMF.COL_VALOR_IN,descripcion);
                 VariablesConvenioBTLMF.vDatosConvenio.remove(j);
                 VariablesConvenioBTLMF.vDatosConvenio.add(j,convenio);
             }
             else
             if (codTipoCampo.equals(ConstantsConvenioBTLMF.CODIGO_NUM_IEM))
             {
                 descripcion = (String)VariablesConvenioBTLMF.listaDatosNoEditables.get(ConstantsConvenioBTLMF.CODIGO_NUM_IEM);
                 convenio.put(ConstantsConvenioBTLMF.COL_VALOR_IN,descripcion);
                 VariablesConvenioBTLMF.vDatosConvenio.remove(j);
                 VariablesConvenioBTLMF.vDatosConvenio.add(j,convenio);
             }
             else
             if (codTipoCampo.equals(ConstantsConvenioBTLMF.CODIGO_NOMB_CLIENTE))
             {
                 descripcion = (String)VariablesConvenioBTLMF.listaDatosNoEditables.get(ConstantsConvenioBTLMF.CODIGO_NOMB_CLIENTE);
                 convenio.put(ConstantsConvenioBTLMF.COL_VALOR_IN,descripcion);
                 VariablesConvenioBTLMF.vDatosConvenio.remove(j);
                 VariablesConvenioBTLMF.vDatosConvenio.add(j,convenio);
             }
             else
             if (codTipoCampo.equals(ConstantsConvenioBTLMF.CODIGO_PRT))
             {
                 descripcion = (String)VariablesConvenioBTLMF.listaDatosNoEditables.get(ConstantsConvenioBTLMF.CODIGO_PRT);
                 convenio.put(ConstantsConvenioBTLMF.COL_VALOR_IN,descripcion);
                 VariablesConvenioBTLMF.vDatosConvenio.remove(j);
                 VariablesConvenioBTLMF.vDatosConvenio.add(j,convenio);
             }
             else
             if (codTipoCampo.equals(ConstantsConvenioBTLMF.CODIGO_NUM_CONTRATO))
             {
                 descripcion = (String)VariablesConvenioBTLMF.listaDatosNoEditables.get(ConstantsConvenioBTLMF.CODIGO_NUM_CONTRATO);
                 convenio.put(ConstantsConvenioBTLMF.COL_VALOR_IN,descripcion);
                 VariablesConvenioBTLMF.vDatosConvenio.remove(j);
                 VariablesConvenioBTLMF.vDatosConvenio.add(j,convenio);
             }
             else
             if (codTipoCampo.equals(ConstantsConvenioBTLMF.CODIGO_TIPO_ASEGURADO))
             {
                 descripcion = (String)VariablesConvenioBTLMF.listaDatosNoEditables.get(ConstantsConvenioBTLMF.CODIGO_TIPO_ASEGURADO);
                 convenio.put(ConstantsConvenioBTLMF.COL_VALOR_IN,descripcion);
                 VariablesConvenioBTLMF.vDatosConvenio.remove(j);
                 VariablesConvenioBTLMF.vDatosConvenio.add(j,convenio);
             }
             if(convenio.get(ConstantsConvenioBTLMF.COL_VALOR_IN) != null)
             {
                 descripcion  = (String)convenio.get(ConstantsConvenioBTLMF.COL_VALOR_IN);

             }

             Map conv = (Map)VariablesConvenioBTLMF.vDatosConvenioIngresar.get(VariablesConvenioBTLMF.vPaginaActual);
             if(convenio.get(ConstantsConvenioBTLMF.COL_CODIGO_CAMPO).equals(conv.get(ConstantsConvenioBTLMF.COL_CODIGO_CAMPO)))
             {
                 estilo = "background-color:green;color:white";
             }

             if(descripcion == null)
                descripcion =" ";

             htmlText = htmlText + "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp <font style ='"+estilo+"' face='sans-serif' size='3' height='3px'><b> "+convenio.get(ConstantsConvenioBTLMF.COL_NOMBRE_CAMPO) + "</b>:</font>" ;
             htmlText = htmlText + "<font face ='sans-serif' size='2' height='2px'>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp "+descripcion.toUpperCase()+"<br></font>" ;

             //Guardamos el valor


           }
             htmlText = htmlText + "</td></tr>";
         }

        htmlText = htmlText + "</table>";
        contenDatosHtml.setContentType("text/html");
        contenDatosHtml.setText(htmlText);

        contenDatosHtml.setEditable(false);
        contenDatosHtml.setBorder(BorderFactory.createLineBorder(new Color(255,130,14),1));
        return contenDatosHtml;
    }


    private Container getPanelSur()
    {
        pnlPie = new JPanel();
        pnlPie.setBounds(new Rectangle(0,142,592,30));
        pnlPie.setBorder(BorderFactory.createLineBorder(new Color(255,130,14),1));
        pnlPie.setBackground(Color.white);
        JLabelFunction lblF8  = new JLabelFunction();
        JLabelFunction lblF9  = new JLabelFunction();
        JLabelFunction lblF11 = new JLabelFunction();

        lblF8.setText(" [Flecha Arriba] Atrás        ");
        lblF9.setText(" [Flecha Abajo]  Siguiente ");

        lblF11.setText(" [ENTER] Aceptar ");

        pnlPie.add(Box.createHorizontalGlue());
        pnlPie.add(lblF8);
        pnlPie.add(lblF9);
        pnlPie.add(lblF11);

        return pnlPie;
    }


    private void initialize()
    {
        VariablesConvenioBTLMF.vPaginaActual = 0;
        VariablesConvenioBTLMF.vDatosConvenioIngresar = new ArrayList();
        VariablesConvenioBTLMF.vDatosConvenio = new  ArrayList();

        VariablesConvenioBTLMF.vDatosConvenio = UtilityConvenioBTLMF.listaDatosConvenio(VariablesConvenioBTLMF.vCodConvenio,this,pnlObjetoIngreso);
        if (VariablesConvenioBTLMF.vDatosConvenio != null)
        {
            Map convenio;
            String pCreaObjeto = "",pEditable = "";
            for (int i = 0; i < VariablesConvenioBTLMF.vDatosConvenio.size();i++)
            {
                convenio = (Map)VariablesConvenioBTLMF.vDatosConvenio.get(i);
                pCreaObjeto = convenio.get(ConstantsConvenioBTLMF.COL_CREA_OBJ).toString().trim();
                pEditable   = convenio.get(ConstantsConvenioBTLMF.COL_EDITABLE).toString().trim();

                if((pCreaObjeto+pEditable).trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S+FarmaConstants.INDICADOR_S))
                {
                    VariablesConvenioBTLMF.vDatosConvenioIngresar.add(convenio);
                }

            }
            pagFinal = VariablesConvenioBTLMF.vDatosConvenioIngresar.size();

        }
    }

    // **************************************************************************
    // KEY PRESSED
    // **************************************************************************
    private void txtBusquedaKeyPressed(KeyEvent e)
    {

        log.debug("metodo:txtBusquedaKeyPressed");
        VariablesConvenioBTLMF.vPaginaActual = pagInicio;
        Map convenio2 = (Map)VariablesConvenioBTLMF.vDatosConvenioIngresar.get(VariablesConvenioBTLMF.vPaginaActual);

        String textoSeleccionado ="";

        if (convenio2.get(ConstantsConvenioBTLMF.COL_VALOR_IN) != null)
        {
            textoSeleccionado =   convenio2.get(ConstantsConvenioBTLMF.COL_VALOR_IN).toString();
        }

        log.debug("textoSeleccionado::::>"+textoSeleccionado);


        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
        	VariablesConvenioBTLMF.limpiarVariablesGlobales();
            cerrarVentana(false);
        }
        else
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
          {
             VariablesConvenioBTLMF.vPaginaActual = pagInicio;
             Map convenio = (Map)VariablesConvenioBTLMF.vDatosConvenioIngresar.get(VariablesConvenioBTLMF.vPaginaActual);
             String codTipoCampo  = convenio.get(ConstantsConvenioBTLMF.COL_CODIGO_CAMPO).toString().trim();
             String pTipoObjeto  = convenio.get(ConstantsConvenioBTLMF.COL_TIPO_OBJ).toString().trim();


             if(verificarIngresoDato(convenio))
             {
            	   log.debug("pTipoObjeto : "+pTipoObjeto);
            	   log.debug("codTipoCampo: "+codTipoCampo);


            	   if (pTipoObjeto.equals(ConstantsConvenioBTLMF.OBJ_IN_TEXTO)
                           && codTipoCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_FEC_VENCIMIENTO)
                          )
                   {
                     	  String fechaVecimientoReceta = txtIngresoTexto.getText();

                     	  String resp = UtilityConvenioBTLMF.esDiaVencimientoReceta(this, txtIngresoTexto, fechaVecimientoReceta);

                   }

                  cargarSiguienteDato();
                  editarDato();



             }


         }
        else
        if(e.getKeyCode() == KeyEvent.VK_ENTER && txtBusqueda.getSelectedText() != null && txtBusqueda.getSelectedText().trim().equalsIgnoreCase(textoSeleccionado.trim()))
           {
                VariablesConvenioBTLMF.vPaginaActual = pagInicio;
                Map convenio = (Map)VariablesConvenioBTLMF.vDatosConvenioIngresar.get(VariablesConvenioBTLMF.vPaginaActual);
                String codTipoCampo  = convenio.get(ConstantsConvenioBTLMF.COL_CODIGO_CAMPO).toString().trim();
                String pTipoObjeto  = convenio.get(ConstantsConvenioBTLMF.COL_TIPO_OBJ).toString().trim();


                if(verificarIngresoDato(convenio))
                {
               	   log.debug("pTipoObjeto : "+pTipoObjeto);
               	   log.debug("codTipoCampo: "+codTipoCampo);


               	   if (pTipoObjeto.equals(ConstantsConvenioBTLMF.OBJ_IN_TEXTO)
                              && codTipoCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_FEC_VENCIMIENTO)
                             )
                      {
                        	  String fechaVecimientoReceta = txtIngresoTexto.getText();

                        	  String resp = UtilityConvenioBTLMF.esDiaVencimientoReceta(this, txtIngresoTexto, fechaVecimientoReceta);

                      }

                     cargarSiguienteDato();
                     editarDato();



                }

                //Verificamos se ingreseo todo los datos
                if(verificarIngresoDatoFinal(convenio2))
                {



                    if(verificarIngresoDato(convenio2))
                    {
                           //en construccion
                        log.debug("Cerrammos la ventana de convenio");
                    	if(com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,"Ingreso todo los datos, desea Guardar?"))
                    	{
                         cerrarVentana(true);
                         VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF = true;
                    	}

                    }
                }

            }

        else
        if(e.getKeyCode() == KeyEvent.VK_ENTER && (txtBusqueda.getSelectedText() == null || !txtBusqueda.getSelectedText().trim().equalsIgnoreCase(textoSeleccionado.trim()))  )
        {

                VariablesConvenioBTLMF.vPaginaActual = pagInicio;
                Map convenio = (Map)VariablesConvenioBTLMF.vDatosConvenioIngresar.get(VariablesConvenioBTLMF.vPaginaActual);
                String codTipoCampo  = convenio.get(ConstantsConvenioBTLMF.COL_CODIGO_CAMPO).toString().trim();
                String pTipoObjeto  = convenio.get(ConstantsConvenioBTLMF.COL_TIPO_OBJ).toString().trim();

                if (pTipoObjeto.equals(ConstantsConvenioBTLMF.OBJ_LISTA_PANTALLA)
                    && codTipoCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_BENIFICIARIO)

                    && !VariablesConvenioBTLMF.vFlgDataRimac.equals("1")
                   )
                {
                  if(validarDato(convenio))
                  {
                     if(UtilityConvenioBTLMF.esTarjetaConvenio(txtBusqueda.getText()))
                     {
                        if(UtilityConvenioBTLMF.existeTarjeta(txtBusqueda.getText(),this))
                         {
                             //
                            if(VariablesConvenioBTLMF.vCodConvenioAux != null)
                            {
                                 if(VariablesConvenioBTLMF.vCodCliente != null)
                                  {
                                    //Verificamos si convenio esta relacion con la tarjeta
                                    if(VariablesConvenioBTLMF.vCodConvenioAux.trim().equals(VariablesConvenioBTLMF.vCodConvenio.trim()))
                                    {
                                       if(UtilityConvenioBTLMF.existeCliente(VariablesConvenioBTLMF.vCodCliente,this))
                                       {

                                           Map convennioTemp   = (Map)UtilityConvenioBTLMF.obtenerConvenio(VariablesConvenioBTLMF.vCodConvenio, this, myParentFrame);
                                     	     String flgValidaCredito    = convennioTemp.get(ConstantsConvenioBTLMF.COL_FLG_VALIDA_LINCRE_BENEF).toString();
                                          if(flgValidaCredito.equals("1") && codTipoCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_BENIFICIARIO))
                                           {
                                           	String indConexionRac = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_RAC,FarmaConstants.INDICADOR_S);
	                          		            if (indConexionRac.equals("S"))
	                          		            {
			                                           consultarSaldoCreditoBenif();
			                                           if(VariablesConvenioBTLMF.vDni != null)
			                                            {
			                                                cargarBenificiario();
			                                                ingresarDatos(convenio);
			                                                VariablesConvenioBTLMF.vAceptar = true;
			                                             }
			                                            else
			                                             {
			                                                FarmaUtility.showMessage(this,"El cliente no tiene asignado el Nro DNI ",txtBusqueda);
			                                                VariablesConvenioBTLMF.vAceptar = false;
			                                             }
	                          		            }
	                          		            else
	                          		            {
	                          		            	FarmaUtility.showMessage(this, "No puede validar la Linea de Crédito del Beneficiario. \n" +
	             				                           "Porque no hay una Conexión a la Base Datos del RAC. \n" +
	             				                           "", tblLista);
	                          		            }
                                           }
                          		           else
                          		            {

		                                           if(VariablesConvenioBTLMF.vDni != null)
		                                            {
		                                                cargarBenificiario();
		                                                ingresarDatos(convenio);
		                                                VariablesConvenioBTLMF.vAceptar = true;
		                                             }
		                                            else
		                                             {
		                                                FarmaUtility.showMessage(this,"El cliente no tiene asignado el Nro DNI ",txtBusqueda);
		                                                VariablesConvenioBTLMF.vAceptar = false;
		                                             }


                          		           }
                                       }
                                       else
                                       {
                                           FarmaUtility.showMessage(this,"El cliente benificiario no existe",txtBusqueda);
                                           VariablesConvenioBTLMF.vAceptar = false;
                                       }
                                    }
                                    else
                                    {
                                          FarmaUtility.showMessage(this,"La tarjeta no pertenece al convenio "+VariablesConvenioBTLMF.vNomConvenio,txtBusqueda);
                                          VariablesConvenioBTLMF.vAceptar = false;
                                    }

                                 }
                                 else
                                 {
                                     FarmaUtility.showMessage(this,"La tarjeta no esta asociado a un cliente",txtBusqueda);
                                     VariablesConvenioBTLMF.vAceptar = false;
                                 }
                            }
                            else
                            {

                               FarmaUtility.showMessage(this,"La tarjeta no esta asociado a un convenio",txtBusqueda);
                               VariablesConvenioBTLMF.vAceptar = false;
                            }

                         }
                         else
                         {
                             FarmaUtility.showMessage(this,"No existe la tarjeta",txtBusqueda);
                             VariablesConvenioBTLMF.vAceptar = false;

                         }

                     }
                     else
                     {
                         if(existeBenificiario())
                         {
                        	        Map convennioTemp   = (Map)UtilityConvenioBTLMF.obtenerConvenio(VariablesConvenioBTLMF.vCodConvenio, this, myParentFrame);
                       	     String flgValidaCredito    = convennioTemp.get(ConstantsConvenioBTLMF.COL_FLG_VALIDA_LINCRE_BENEF).toString();
                            if(flgValidaCredito.equals("1") && codTipoCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_BENIFICIARIO))
                             {
                             	String indConexionRac = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_RAC,FarmaConstants.INDICADOR_S);
            		            if (indConexionRac.equals("S"))
            		            {
	                              consultarSaldoCreditoBenif();
	                              cargarBenificiario();
	                              ingresarDatos(convenio);
            		            }
            		            else
            		            {
            		            	FarmaUtility.showMessage(this, "No puede validar la Linea de Crédito del Beneficiario. \n" +
 				                           "Porque no hay una Conexión a la base de datos del RAC. \n" +
 				                           "", tblLista);

            		            }
                             }
                            else
                            {
                              cargarBenificiario();
	                          ingresarDatos(convenio);
                            }

                         }
                         else
                         {
                             UtilityConvenioBTLMF.imprimirMensaje(txtBusqueda.getText(), this,txtBusqueda);

                             if(
                                ((VariablesConvenioBTLMF.vFlgCreacionCliente != null && VariablesConvenioBTLMF.vFlgCreacionCliente.trim().equals(ConstantsConvenioBTLMF.FLG_CREACION_BENIF)) ||
                                 VariablesConvenioBTLMF.vFlgCreacionCliente.trim().equals(ConstantsConvenioBTLMF.FLG_SOLICITUD_BENIF))
                                )
                             {
                                 nuevoBenificiario();
                                 log.debug("Benificiario:::"+VariablesConvenioBTLMF.vAceptar);
                                 if(VariablesConvenioBTLMF.vAceptar &&
                                    (VariablesConvenioBTLMF.vFlgCreacionCliente != null && VariablesConvenioBTLMF.vFlgCreacionCliente.trim().equals(ConstantsConvenioBTLMF.FLG_CREACION_BENIF))
                                    )
                                 {
                                	 log.debug("CodCliente:::"+VariablesConvenioBTLMF.vCodCliente);

                                    ingresarDatos(convenio);
                                 }
                             }
                         }
                      }

                      if(VariablesConvenioBTLMF.vAceptar)
                      {
                         cargarSiguienteDato();
                         editarDato();
                     }
                  }
                }
                else
                if (pTipoObjeto.equals(ConstantsConvenioBTLMF.OBJ_LISTA_PANTALLA)
                        && codTipoCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_MEDICO)
                       )
                    {
                      if(validarDato(convenio))
                      {
                         if(existeMedico())
                         {
                        	 cargarMedico();
                             ingresarDatos(convenio);
                             VariablesConvenioBTLMF.vAceptar = true;
                         }
                         else
                         {
                             FarmaUtility.showMessage(this,"No existe Médico",null);
                             log.debug("Medico:::"+VariablesConvenioBTLMF.vAceptar);
                             VariablesConvenioBTLMF.vAceptar = false;
                         }

                          if(VariablesConvenioBTLMF.vAceptar)
                          {
                             cargarSiguienteDato();
                             editarDato();
                         }
                      }
                    }
                else
                if (pTipoObjeto.equals(ConstantsConvenioBTLMF.OBJ_LISTA_PANTALLA)
                        && codTipoCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_DIAGNOSTICO_UIE)

                       )
                    {
                      if(validarDato(convenio))
                      {
                         if(existeDiagnostico())
                         {
                             cargarDiagnostico();
                             ingresarDatos(convenio);
                             VariablesConvenioBTLMF.vAceptar = true;
                         }
                         else
                         {
                              FarmaUtility.showMessage(this,"No existe Diagnostico",null);

                             log.debug("Diagnostico:::"+VariablesConvenioBTLMF.vAceptar);
                             VariablesConvenioBTLMF.vAceptar = false;
                         }

                          if(VariablesConvenioBTLMF.vAceptar)
                          {
                             cargarSiguienteDato();
                             editarDato();
                         }
                      }
                    }

                //Verificamos se ingreseo todo los datos
                if(verificarIngresoDatoFinal(convenio2))
                {



                    if(verificarIngresoDato(convenio2))
                    {
                           //en construccion
                        log.debug("Cerrammos la ventana de convenio");
                    	if(com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,"Ingreso todo los datos, desea Guardar?"))
                    	{
                         cerrarVentana(true);
                         VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF = true;
                    	}

                    }
                }
         }
        else
        if(mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F12(e))
        {
                VariablesConvenioBTLMF.vPaginaActual = pagInicio;
                Map convenio = (Map)VariablesConvenioBTLMF.vDatosConvenioIngresar.get(VariablesConvenioBTLMF.vPaginaActual);


                String codTipoCampo       = convenio.get(ConstantsConvenioBTLMF.COL_CODIGO_CAMPO).toString().trim();
                String flgLista           = convenio.get(ConstantsConvenioBTLMF.COL_FLG_LISTA) != null? convenio.get(ConstantsConvenioBTLMF.COL_FLG_LISTA).toString().trim():" ";
                String pTipoObjeto   	  = convenio.get(ConstantsConvenioBTLMF.COL_TIPO_OBJ).toString().trim();


                log.debug(">>>>>>>>>>>pTipoObjeto<<<<<<<<<<<"+pTipoObjeto);
                log.debug(">>>>>>>>>>>flgLista<<<<<<<<<<<"+flgLista);


                if (pTipoObjeto.equals(ConstantsConvenioBTLMF.OBJ_LISTA_PANTALLA) || flgLista.equals("1"))
                {
                     log.debug(">>>>>>>>>>>Muestra una Nueva Pantalla<<<<<<<<<<<"+flgLista);

                      boolean continua = true;

	                  if(codTipoCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_BENIFICIARIO))
	              	   {
	                	  VariablesConvenioBTLMF.vBuscarNombreBenif =txtBusqueda.getText();
		                  log.debug("Busqueda::"+VariablesConvenioBTLMF.vBuscarNombreBenif);
		                  if(VariablesConvenioBTLMF.vBuscarNombreBenif == null || VariablesConvenioBTLMF.vBuscarNombreBenif.trim().length()<3)
		                  {
		                	  FarmaUtility.showMessage(this, "Debe ingresar minimo 3 caracteres ",txtBusqueda);
		                	  VariablesConvenioBTLMF.vAceptar = false;
		                  }
		                  else
		                  {
		                   VariablesConvenioBTLMF.vCodTipoCampo= codTipoCampo;
	                       VariablesConvenioBTLMF.vFlg_lista= flgLista;
	                       DlgListaPantallaBTLMF dlg = new DlgListaPantallaBTLMF(myParentFrame," ",true);
	                       dlg.setVisible(true);
		                  }
	              	   }
	                   else
	                   {
	                      VariablesConvenioBTLMF.vCodTipoCampo= codTipoCampo;
	                      VariablesConvenioBTLMF.vFlg_lista= flgLista;
	                      DlgListaPantallaBTLMF dlg = new DlgListaPantallaBTLMF(myParentFrame," ",true);
	                      dlg.setVisible(true);
	                   }

                      if(VariablesConvenioBTLMF.vAceptar)
                      {

                    	  Map convennioTemp   = (Map)UtilityConvenioBTLMF.obtenerConvenio(VariablesConvenioBTLMF.vCodConvenio, this, myParentFrame);
                    	  String flgValidaCredito   = convennioTemp.get(ConstantsConvenioBTLMF.COL_FLG_VALIDA_LINCRE_BENEF).toString();
                         if(flgValidaCredito.equals("1") && codTipoCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_BENIFICIARIO))
                         {
                        	String indConexionRac = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_RAC,FarmaConstants.INDICADOR_S);
         		            if (indConexionRac.equals("S"))
         		            {
                              consultarSaldoCreditoBenif();
         		            }
         		            else
         		            {
         		            	FarmaUtility.showMessage(this, "No puede validar la Linea de Crédito del Beneficiario. \n" +
				                           "Porque no hay una Conexión a la Base de Datos del RAC. \n" +
				                           "", tblLista);

         		            	continua = false;
         		            }

                         }
                         if (continua)
                         {
	                         ingresarDatos(convenio);
	                         cargarActualDato();
	                         //cargarSiguienteDato();
	                         editarDato();
                         }
                     }
                }
        }
        else
        if(e.getKeyCode() == KeyEvent.VK_UP)
        {
            cargarAnteriorDato();
            editarDato();

        }

    }
    private void txtIngresoTextoKeyPressed(KeyEvent e)
    {
    	VariablesConvenioBTLMF.vPaginaActual = pagInicio;
        Map convenio = (Map)VariablesConvenioBTLMF.vDatosConvenioIngresar.get(VariablesConvenioBTLMF.vPaginaActual);


        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
        	VariablesConvenioBTLMF.limpiarVariablesGlobales();
            cerrarVentana(false);
        }
        else
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
        {

            VariablesConvenioBTLMF.vPaginaActual = pagInicio;

            log.debug("Pagina Actual:"+VariablesConvenioBTLMF.vPaginaActual) ;
            Map convenioActual = (Map)VariablesConvenioBTLMF.vDatosConvenioIngresar.get(VariablesConvenioBTLMF.vPaginaActual);
            String codTipoCampo  = convenioActual.get(ConstantsConvenioBTLMF.COL_CODIGO_CAMPO).toString().trim();
            String pTipoObjeto   = convenioActual.get(ConstantsConvenioBTLMF.COL_TIPO_OBJ).toString().trim();

            boolean continua =true;

            if(validarDato(convenioActual))
            {
                    ingresarDatos(convenioActual);
                 if(verificarIngresoDato(convenioActual))
                 {

                	 if (pTipoObjeto.equals(ConstantsConvenioBTLMF.OBJ_IN_TEXTO)
                             && codTipoCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_FEC_VENCIMIENTO)
                            )
                     {
                       	  String fechaVecimientoReceta = txtIngresoTexto.getText();

                       	  String resp = UtilityConvenioBTLMF.esDiaVencimientoReceta(this, txtIngresoTexto, fechaVecimientoReceta);

                       	  if (!resp.equals("S"))
                       	  {
                       		  continua = false;
                       	  }

                     }
                  if (continua)
                  {
                	 cargarSiguienteDato();
                     editarDato();
                  }
                }
            }

        }
        else
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
            {

                VariablesConvenioBTLMF.vPaginaActual = pagInicio;
                log.debug("Pagina Actual:"+VariablesConvenioBTLMF.vPaginaActual) ;
                Map convenioActual = (Map)VariablesConvenioBTLMF.vDatosConvenioIngresar.get(VariablesConvenioBTLMF.vPaginaActual);
                String codTipoCampo  = convenioActual.get(ConstantsConvenioBTLMF.COL_CODIGO_CAMPO).toString().trim();
                String pTipoObjeto   = convenioActual.get(ConstantsConvenioBTLMF.COL_TIPO_OBJ).toString().trim();

                boolean continua =true;

                if(validarDato(convenioActual))
                {
                        ingresarDatos(convenioActual);
                     if(verificarIngresoDato(convenioActual))
                     {

                    	 if (pTipoObjeto.equals(ConstantsConvenioBTLMF.OBJ_IN_TEXTO)
                                 && codTipoCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_FEC_VENCIMIENTO)
                                )
                         {
                           	  String fechaVecimientoReceta = txtIngresoTexto.getText();

                           	  String resp = UtilityConvenioBTLMF.esDiaVencimientoReceta(this, txtIngresoTexto, fechaVecimientoReceta);

                           	  if (!resp.equals("S"))
                           	  {
                           		  continua = false;
                           	  }

                         }
                      if (continua)
                      {
                    	 cargarSiguienteDato();
                         editarDato();

                      }
                    }
                 }

              //Verfica se ingreso todo los datos .........
                if(verificarIngresoDatoFinal(convenio))
                {

	               if(verificarIngresoDato(convenio))
	                    {
	                           //en construccion
	                        log.debug("Cerrammos la ventana de convenio");
	                    	if(com.gs.mifarma.componentes.JConfirmDialog.rptaConfirmDialog(this,"Ingreso todo los datos, desea Guardar?"))
	                    	{
	                         cerrarVentana(true);
	                         VariablesConvenioBTLMF.vHayDatosIngresadosConvenioBTLMF = true;
	                    	}

	                    }
                }
            }
        else
        if(e.getKeyCode() == KeyEvent.VK_UP)
        {

	            cargarAnteriorDato();
	            editarDato();

        }
    }




    private void cargaPrimerDato() {
    	 log.debug(">>>>>>>>>Metodo: cargaPrimerDato<<<<<<<<");

        if (VariablesConvenioBTLMF.vDatosConvenio != null)
        {
            Map convenio;
            String pCreaObjeto = "",pEditable = "";
            for (int i = 0; i < VariablesConvenioBTLMF.vDatosConvenio.size();i++)
            {
                convenio = (Map)VariablesConvenioBTLMF.vDatosConvenio.get(i);
                String pTipoObjeto  = convenio.get(ConstantsConvenioBTLMF.COL_TIPO_OBJ).toString().trim();
                pCreaObjeto 		= convenio.get(ConstantsConvenioBTLMF.COL_CREA_OBJ).toString().trim();
                pEditable   		= convenio.get(ConstantsConvenioBTLMF.COL_EDITABLE).toString().trim();
                if((pCreaObjeto+pEditable).trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S+FarmaConstants.INDICADOR_S))
                {
                  cargaPanelIngresoDatos(convenio);
                  actualizaTamanoPantalla();
                  capturaFocus(pTipoObjeto);
                  break;
                }
            }
        }

    }


    private void cargarSiguienteDato()
    {

        if(pagInicio == (pagFinal-1))
        {
          pagInicio = -1;
        }
        log.debug(">>>>>>>>>Metodo: cargarSiguienteDato<<<<<<<<");
        pagInicio = pagInicio + 1;
        VariablesConvenioBTLMF.vPaginaActual = pagInicio;
        log.debug("pagInicio:"+pagInicio+"pagFinal:"+pagFinal);

        Map convenio;
        convenio = (Map)VariablesConvenioBTLMF.vDatosConvenioIngresar.get(pagInicio);

        cargaPanelIngresoDatos(convenio);
        actualizaTamanoPantalla();
        String pTipoObjeto =(String)convenio.get(ConstantsConvenioBTLMF.COL_TIPO_OBJ);
        capturaFocus(pTipoObjeto);

        if(convenio.get(ConstantsConvenioBTLMF.COL_VALOR_IN) != null)
        txtIngresoTexto.setText(convenio.get(ConstantsConvenioBTLMF.COL_VALOR_IN).toString());
        txtIngresoTexto.selectAll();

        if(convenio.get(ConstantsConvenioBTLMF.COL_VALOR_IN) != null)
        txtBusqueda.setText(convenio.get(ConstantsConvenioBTLMF.COL_VALOR_IN).toString());
        txtBusqueda.selectAll();

    }

    private void cargarActualDato()
    {

        if(pagInicio == (pagFinal-1))
        {
          pagInicio = 0;
        }
        log.debug(">>>>>>>>>Metodo: cargarSiguienteDato<<<<<<<<");
        //pagInicio = pagInicio + 1;
        VariablesConvenioBTLMF.vPaginaActual = pagInicio;
        log.debug("pagInicio:"+pagInicio+"pagFinal:"+pagFinal);

        Map convenio;
        convenio = (Map)VariablesConvenioBTLMF.vDatosConvenioIngresar.get(pagInicio);

        //cargaPanelIngresoDatos(convenio);

        actualizaTamanoPantalla();
        String pTipoObjeto =(String)convenio.get(ConstantsConvenioBTLMF.COL_TIPO_OBJ);

        capturaFocus(pTipoObjeto);

        if(convenio.get(ConstantsConvenioBTLMF.COL_VALOR_IN) != null)
        txtIngresoTexto.setText(convenio.get(ConstantsConvenioBTLMF.COL_VALOR_IN).toString());
        txtIngresoTexto.selectAll();
        if(convenio.get(ConstantsConvenioBTLMF.COL_VALOR_IN) != null)
        txtBusqueda.setText(convenio.get(ConstantsConvenioBTLMF.COL_VALOR_IN).toString());
        txtBusqueda.selectAll();

    }

    private void cargarAnteriorDato()
    {
        log.debug(">>>>>>>>>Metodo: cargarAnteriorDato<<<<<<<<");
        int paginaActual = 0;
        paginaActual = pagInicio;
        Map convenio;


        convenio = (Map)VariablesConvenioBTLMF.vDatosConvenioIngresar.get(paginaActual);


        	 if(pagInicio == 0)
             {
                pagInicio = pagFinal;
             }
	        pagInicio = pagInicio - 1;
	        log.debug("pagInicio:"+pagInicio+"pagFinal:"+pagFinal);

	        VariablesConvenioBTLMF.vPaginaActual = pagInicio;

	        convenio = (Map)VariablesConvenioBTLMF.vDatosConvenioIngresar.get(pagInicio);


		        cargaPanelIngresoDatos(convenio);
		        actualizaTamanoPantalla();
		        String pTipoObjeto =(String)convenio.get(ConstantsConvenioBTLMF.COL_TIPO_OBJ);
		        capturaFocus(pTipoObjeto);

		        if(convenio.get(ConstantsConvenioBTLMF.COL_VALOR_IN) != null)
		        txtIngresoTexto.setText(convenio.get(ConstantsConvenioBTLMF.COL_VALOR_IN).toString());
		        txtIngresoTexto.selectAll();

		        if(convenio.get(ConstantsConvenioBTLMF.COL_VALOR_IN) != null)
		        txtBusqueda.setText(convenio.get(ConstantsConvenioBTLMF.COL_VALOR_IN).toString());
		        txtBusqueda.selectAll();



    }


    private void cargarListaCombox(String pCodigoCampo)
    {


        cboLista.removeAllItems();
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(VariablesConvenioBTLMF.vCodConvenio);
        parametros.add(pCodigoCampo);
        parametros.add("");

        log.debug("PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_LISTA_DATO("+parametros+")");
        log.debug("cboLista = "+cboLista);


        FarmaLoadCVL.loadCVLFromSP(cboLista,ConstantsConvenioBTLMF.NOM_HASTABLE_CONVENIO,
                                   "PTOVENTA_CONV_BTLMF.BTLMF_F_CUR_LISTA_DATO(?,?,?,?,?,?)", parametros,true, true);


    }




    private void this_windowOpened(WindowEvent e)
    {

      Map convenio =(Map)UtilityConvenioBTLMF.obtenerConvenio(VariablesConvenioBTLMF.vCodConvenio, this, myParentFrame);
      VariablesConvenioBTLMF.vFlgDataRimac = (String)convenio.get(ConstantsConvenioBTLMF.COL_FLG_DATA_RIMAC);

      actualizaTamanoPantalla();
      FarmaVariables.vAceptar = false;
      cargaPrimerDato();
      FarmaUtility.centrarVentana(this);

          log.debug("VariablesConvenioBTLMF.vCodConvenio ojo  = "+VariablesConvenioBTLMF.vCodConvenio);
          log.debug("VariablesConvenioBTLMF.vCodCliente  ojo  = "+VariablesConvenioBTLMF.vCodCliente);
          if (VariablesConvenioBTLMF.vCodCliente != null)
          {          if(UtilityConvenioBTLMF.existeCliente(VariablesConvenioBTLMF.vCodCliente,this))
			          {
					      if(VariablesConvenioBTLMF.vCodCliente != null && !VariablesConvenioBTLMF.vCodCliente.trim().equals(""))
					      {
					    	  Map convennioTemp   = (Map)UtilityConvenioBTLMF.obtenerConvenio(VariablesConvenioBTLMF.vCodConvenio, this, myParentFrame);
					    	  String flgValidaCredito   = convennioTemp.get(ConstantsConvenioBTLMF.COL_FLG_VALIDA_LINCRE_BENEF).toString();
					         if(flgValidaCredito.equals("1"))
					         {
					        	String indConexionRac = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_RAC,FarmaConstants.INDICADOR_S);
						            if (indConexionRac.equals("S"))
						            {
					                   consultarSaldoCreditoBenif();
					                   actualizaBenificiario();
						            }
						            else
						            {
						            	FarmaUtility.showMessage(this, "No puede validar la Linea de Crédito del Beneficiario. \n" +
					                           "Porque no hay una Conexión a la Base de Datos del RAC. \n" +
					                           "", tblLista);

						            }

					         }
					      }
			         }
					 else
					 {
			              UtilityConvenioBTLMF.imprimirMensaje(VariablesConvenioBTLMF.vDni, this,txtBusqueda);

			              if(
			                 ((VariablesConvenioBTLMF.vFlgCreacionCliente != null && VariablesConvenioBTLMF.vFlgCreacionCliente.trim().equals(ConstantsConvenioBTLMF.FLG_CREACION_BENIF)) ||
			                  VariablesConvenioBTLMF.vFlgCreacionCliente.trim().equals(ConstantsConvenioBTLMF.FLG_SOLICITUD_BENIF))
			                 )
			              {
			                  nuevoBenificiario();
			                  log.debug("Benificiario:::"+VariablesConvenioBTLMF.vAceptar);
			                  if(VariablesConvenioBTLMF.vAceptar &&
			                     (VariablesConvenioBTLMF.vFlgCreacionCliente != null && VariablesConvenioBTLMF.vFlgCreacionCliente.trim().equals(ConstantsConvenioBTLMF.FLG_CREACION_BENIF))
			                    )
			                  {
			                 	 log.debug("CodCliente:::"+VariablesConvenioBTLMF.vCodCliente);
			                     ingresarDatos(convenio);
			                  }
			              }

					 }
          }

    }
    private String getMensajePersonalizado(String pCampoMsj){
      String mensaje=null;
        if(pCampoMsj.contains("Benificiarios")){
            mensaje=pCampoMsj.substring(0,48);
            VariablesConvenioBTLMF.Mensaje="[F12] Busqueda por nombre y/o apellido de beneficiario";
        }else{
            mensaje=pCampoMsj;
            VariablesConvenioBTLMF.Mensaje="";
        }
        
        return mensaje;
    }

    private void cargaPanelIngresoDatos(Map aux)
    {
    	 log.debug(">>>>>>>>>Metodo: cargaPanelIngresoDatos<<<<<<<<");

        String pTipoCampo   = aux.get(ConstantsConvenioBTLMF.COL_CODIGO_CAMPO).toString().trim();
        String pTipoObjeto  = aux.get(ConstantsConvenioBTLMF.COL_TIPO_OBJ).toString().trim();
        String pNombreCampo = aux.get(ConstantsConvenioBTLMF.COL_NOMBRE_CAMPO).toString().trim();
        String pCampoMsj    = aux.get(ConstantsConvenioBTLMF.COL_CAMPO_MSJ).toString().trim();

        pCampoMsj=getMensajePersonalizado(pCampoMsj);
        String pMaxLongitud = aux.get(ConstantsConvenioBTLMF.COL_LONG_MAX).toString().trim();
        String pFlg_lista   = aux.get(ConstantsConvenioBTLMF.COL_FLG_LISTA)!=null?aux.get(ConstantsConvenioBTLMF.COL_FLG_LISTA).toString().trim():"";



        log.debug("aux:"+aux);

        log.debug("pTipoCampo:"+pTipoCampo);
        log.debug("pFlg_lista:"+pFlg_lista);

        pCodCampoActual = "";
        pCodCampoActual = aux.get(ConstantsConvenioBTLMF.COL_CODIGO_CAMPO).toString().trim();
        //Limpia Objetos del Panel Centro.
        lblNombreDato.setText("");
        pnlObjetoIngreso.removeAll();
        codigo = "";
        lblInstruccion.setText("");
        txtIngresoTexto.setText("");
        txtBusqueda.setText("");
        pnlIngresoDatos.removeAll();
        pnlObjetoIngreso.removeAll();
        //*********fin de limpiar objetos*****************//

        //Inicial los Objetos del Panel Centro
        lblNombreDato.setText(""+pNombreCampo+" :");
        lblNombreDato.setForeground(Color.WHITE);
        lblInstruccion.setForeground(Color.WHITE);
        pnlIngresoDatos.setBounds(new Rectangle(10,0,592,135));
        pnlIngresoDatos.setBackground(new Color(43, 141, 39));
        pnlIngresoDatos.setLayout(null);

        lblNombreDato.setFont(new Font("SansSerif", 1, 13));
        lblNombreDato.setBackground(Color.WHITE);
        lblNombreDato.setBounds(new Rectangle(22,10,250,20));
        pnlObjetoIngreso.setLayout(null);
        pnlObjetoIngreso.setBorder(BorderFactory.createLineBorder(new Color(43, 141, 39), 3));
        pnlObjetoIngreso.setBackground(new Color(43, 141, 39));
        pnlObjetoIngreso.setBounds(new Rectangle(25,30,610,40));

        lblInstruccion.setFont(new Font("SansSerif", 1, 13));
        lblInstruccion.setBackground(Color.WHITE);
        lblInstruccion.setBounds(new Rectangle(22,80,580,30));

        lblInstruccionFinalizar.setText("Presionar [ENTER]Aceptar, si lleno todo los datos del Convenio");
        lblInstruccionFinalizar.setFont(new Font("SansSerif", 1, 13));
        lblInstruccionFinalizar.setBackground(Color.BLACK);
        lblInstruccionFinalizar.setForeground(Color.RED);
        lblInstruccionFinalizar.setBounds(new Rectangle(22,105,580,30));
        
       


        //****************fin de inicializar***********************//

        if(pFlg_lista.equals("1"))
        {
        	pTipoObjeto = ConstantsConvenioBTLMF.OBJ_LISTA_PANTALLA;

        }

        if(pTipoObjeto.equalsIgnoreCase(ConstantsConvenioBTLMF.OBJ_IN_TEXTO))
        {

            txtIngresoTexto.setBounds(new Rectangle(50,10,200,20));
            txtIngresoTexto.setFont(new Font("SansSerif", 0, 11));
            pnlObjetoIngreso.add(txtIngresoTexto);
            lblInstruccion.setText(pCampoMsj);
            FarmaUtility.moveFocus(txtIngresoTexto);

            if(pTipoCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_FEC_VENCIMIENTO))
            {
                txtIngresoTexto.setDocument(new ValidaDatoIngreso(this,ConstantsConvenioBTLMF.CLASE_FECHA,10));
            }
            else
            {
                txtIngresoTexto.setDocument(new ValidaDatoIngreso(this,ConstantsConvenioBTLMF.CLASE_DATO_ADICIONAL,Integer.parseInt(pMaxLongitud)));
            }
        }

        if(pTipoObjeto.equalsIgnoreCase(ConstantsConvenioBTLMF.OBJ_LISTA_PANTALLA))
        {

            txtBusqueda.setBounds(new Rectangle(22,10,150,20));
            txtBusqueda.setFont(new Font("SansSerif", 0, 11));
            lblMensaje.setText(VariablesConvenioBTLMF.Mensaje);
            lblMensaje.setBackground(Color.GREEN);
            lblMensaje.setFont(new Font("SansSerif", 1, 13));
            lblMensaje.setBounds(new Rectangle(180,10,370,20));
            lblMensaje.setForeground(Color.WHITE);

            pnlObjetoIngreso.add(txtBusqueda);//aki
            pnlObjetoIngreso.add(lblMensaje);
            lblInstruccion.setText(pCampoMsj);
            FarmaUtility.moveFocus(txtBusqueda);


            if(pTipoCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_BENIFICIARIO))
            {
                txtBusqueda.setDocument(new ValidaDatoIngreso(this,ConstantsConvenioBTLMF.CLASE_DATO_ADICIONAL,50));
            }
            else
            {
                txtBusqueda.setDocument(new ValidaDatoIngreso(this,ConstantsConvenioBTLMF.CLASE_DATO_ADICIONAL,50));
            }
        }

        pnlInstruccion = new JPanel();
        pnlInstruccion.setBounds(new Rectangle(22,108,530,24));
        pnlInstruccion.setBackground(Color.WHITE);
        pnlInstruccion.add(lblInstruccionFinalizar);

        //agrega Objetos y Refresca la pantalla
        pnlIngresoDatos.add(lblNombreDato);
        pnlIngresoDatos.add(pnlObjetoIngreso);
        pnlIngresoDatos.add(lblInstruccion);
        pnlIngresoDatos.add(pnlInstruccion);



        //agrega Objetos y Refresca la pantalla
        this.getContentPane().removeAll();
        this.getContentPane().setLayout(null);
        this.getContentPane().add(getPanelNorte());
        this.getContentPane().add(pnlIngresoDatos);



        this.getContentPane().add(getPanelSur());

    }

    public void actualizaTamanoPantalla()
    {

        log.debug(">>>>>>>>>Metodo: actualizaTamanoPantalla<<<<<<<<");

        contenDatosHtml.setBounds(new Rectangle(0,0,592,(int)contenDatosHtml.getPreferredSize().getHeight()));
        pnlIngresoDatos.setBounds(new Rectangle(0,(int)(contenDatosHtml.getPreferredSize().getHeight())+5,592,135));
        pnlPie.setBounds(new Rectangle(0,142+(int)contenDatosHtml.getPreferredSize().getHeight(),592,30));



        altura =  (int)(contenDatosHtml.getSize().getHeight()+
                        pnlIngresoDatos.getSize().getHeight()+
                        pnlPie.getPreferredSize().getHeight());

        this.setSize(new Dimension(600,200+(int)contenDatosHtml.getPreferredSize().getHeight()));
        this.show();
    }




    private void capturaFocus(String pTipoObjeto)
    {

         if(pTipoObjeto.equalsIgnoreCase(ConstantsConvenioBTLMF.OBJ_IN_TEXTO))
         {
             FarmaUtility.moveFocus(txtIngresoTexto);
         }
         if(pTipoObjeto.equalsIgnoreCase(ConstantsConvenioBTLMF.OBJ_LISTA_COMBO))
         {
             FarmaUtility.moveFocus(cboLista);
         }
         if(pTipoObjeto.equalsIgnoreCase(ConstantsConvenioBTLMF.OBJ_LISTA_PANTALLA))
         {
             FarmaUtility.moveFocus(txtBusqueda);
         }
    }


    public void cargarPantalla()
    {
        log.debug(">>>>>>>>>Metodo: cargarPantalla<<<<<<<<");
        Map convenio;
        convenio = (Map)VariablesConvenioBTLMF.vDatosConvenioIngresar.get(VariablesConvenioBTLMF.vPaginaActual);
        cargaPanelIngresoDatos(convenio);
        actualizaTamanoPantalla();
        String tipoObjeto = (String)convenio.get(ConstantsConvenioBTLMF.COL_TIPO_OBJ);
        capturaFocus(tipoObjeto);

    }

    private void ingresarDatos(Map convenio)
    {

        log.debug(">>>>>>>>>Metodo: ingresarDatos<<<<<<<<");
        VariablesConvenioBTLMF.vCodTipoCampo = (String)convenio.get(ConstantsConvenioBTLMF.COL_CODIGO_CAMPO);
        for (int j = 0; j < VariablesConvenioBTLMF.vDatosConvenio.size(); j++)
        {
           Map map = (Map)VariablesConvenioBTLMF.vDatosConvenio.get(j);
           if(map.get(ConstantsConvenioBTLMF.COL_CODIGO_CAMPO).equals(VariablesConvenioBTLMF.vCodTipoCampo))
            {
                if(map.get(ConstantsConvenioBTLMF.COL_TIPO_OBJ).equals(ConstantsConvenioBTLMF.OBJ_IN_TEXTO)
                  &&!txtIngresoTexto.getText().trim().equals(""))
                {
                      map.put(ConstantsConvenioBTLMF.COL_VALOR_IN,txtIngresoTexto.getText());
                      VariablesConvenioBTLMF.vDatosConvenio.remove(j);
                      VariablesConvenioBTLMF.vDatosConvenio.add(j,map);
                }
                if((map.get(ConstantsConvenioBTLMF.COL_TIPO_OBJ).equals(ConstantsConvenioBTLMF.OBJ_LISTA_PANTALLA))
                  &&!VariablesConvenioBTLMF.vDescripcion.trim().equals(""))
                {
                      map.put(ConstantsConvenioBTLMF.COL_VALOR_IN,VariablesConvenioBTLMF.vDescripcion.trim());
                      map.put(ConstantsConvenioBTLMF.COL_COD_VALOR_IN,VariablesConvenioBTLMF.vCodigoAux);

                      VariablesConvenioBTLMF.vDatosConvenio.remove(j);
                      VariablesConvenioBTLMF.vDatosConvenio.add(j,map);
                }
            }
        }
    }


    private boolean validarDato(Map convenio)
    {
        log.debug(">>>>>>>>>Metodo: validarDato<<<<<<<<");
        boolean result = true;
        if (convenio.get(ConstantsConvenioBTLMF.COL_TIPO_OBJ).equals(ConstantsConvenioBTLMF.OBJ_LISTA_PANTALLA) &&
            convenio.get(ConstantsConvenioBTLMF.COL_CODIGO_CAMPO).equals(ConstantsConvenioBTLMF.COD_DATO_CONV_BENIFICIARIO)
            && txtBusqueda.getText().length() <= 11)
         {

	         if (txtBusqueda.getText().length() < 3)
	      	 {
	      		FarmaUtility.showMessage(this, "Para realizar la búsqueda por apellido debe ingresar como mínimo 3 caracteres ",txtBusqueda);
		        return  result = false;

	      	 }

        	 if(!validarDni(txtBusqueda.getText().trim()))
        	 {
               FarmaUtility.showMessage(this, "Ha ingresado un número de DNI Incorrecto"+" "+"\n Para búsqueda por apellido presionar [F12] ",txtBusqueda);
                return result = false;
        	 }
         }
        else
        {
          if(txtBusqueda.getText().trim().length() > 11 && !UtilityConvenioBTLMF.esTarjetaConvenio(txtBusqueda.getText()))
          {
              FarmaUtility.showMessage(this, "Invalido DNI/RUC/TARJETA ",txtBusqueda);
               return result = false;
          }
        }

        if (convenio.get(ConstantsConvenioBTLMF.COL_TIPO_OBJ).equals(ConstantsConvenioBTLMF.OBJ_IN_TEXTO)&&((String)convenio.get(ConstantsConvenioBTLMF.COL_CODIGO_CAMPO)).equals(ConstantsConvenioBTLMF.COD_DATO_CONV_FEC_VENCIMIENTO))
         {

           if(!FarmaUtility.validaFecha(txtIngresoTexto.getText().trim(), "dd/MM/yyyy"))
           {
              String fechaVencimiento = (String)convenio.get(ConstantsConvenioBTLMF.COL_NOMBRE_CAMPO);
              FarmaUtility.showMessage(this, "Ingrese la "+fechaVencimiento+" correctamente",txtIngresoTexto);
              result = false;
           }
         }
        return result;
    }


    private void cerrarVentana(boolean pAceptar)
    {
        VariablesConvenioBTLMF.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }


       private void editarDato()
    {

          log.debug(">>>>>>>>>Metodo: editarDato<<<<<<<<");


          if (VariablesConvenioBTLMF.vDatosConvenio != null)
          {
            for (int j = 0; j < VariablesConvenioBTLMF.vDatosConvenio.size(); j++)
            {
               String descripcion ="";
               Map convenio = (Map)VariablesConvenioBTLMF.vDatosConvenio.get(j);
               if(convenio.get(ConstantsConvenioBTLMF.COL_VALOR_IN) != null)
               {

                    descripcion  = (String)convenio.get(ConstantsConvenioBTLMF.COL_VALOR_IN);

               }
               Map conv = (Map)VariablesConvenioBTLMF.vDatosConvenioIngresar.get(VariablesConvenioBTLMF.vPaginaActual);
               if(convenio.get(ConstantsConvenioBTLMF.COL_CODIGO_CAMPO).equals(conv.get(ConstantsConvenioBTLMF.COL_CODIGO_CAMPO)))
               {
                   if(conv.get(ConstantsConvenioBTLMF.COL_TIPO_OBJ).equals(ConstantsConvenioBTLMF.OBJ_LISTA_COMBO)&&!descripcion.trim().equals(""))
                   {
                       seleccionarItemCombox(descripcion);
                   }
                   if(conv.get(ConstantsConvenioBTLMF.COL_TIPO_OBJ).equals(ConstantsConvenioBTLMF.OBJ_IN_TEXTO)&&!descripcion.trim().equals(""))
                   {
                       txtIngresoTexto.setText(descripcion);

                   }
               }

             }
           }

          count = 0;

    }

    private void seleccionarItemCombox(String descripcion)
    {

      log.debug("seleccionarItemCombox:");
      // Get number of items
      int num = cboLista.getItemCount();
      // Get items
      for (int i = 0; i < num; i++)
      {
        Object item = cboLista.getItemAt(i);
        if(item.toString().trim().equals(descripcion.trim()))
        {
             cboLista.setSelectedIndex(i);
             break;
        }
      }
   }

   private boolean existeBenificiario()
   {
       log.debug("<<<<<<<<<<<<Metodo: existeBenificiario>>>>>>>>>>>>");
       Map benif = null;
       boolean retorno = false;

       benif =  UtilityConvenioBTLMF.obtieneBenificiario(VariablesConvenioBTLMF.vCodConvenio, txtBusqueda.getText().trim(), this);

       log.debug("BenifDNI:"+benif.get(ConstantsConvenioBTLMF.COL_DNI));

       if(benif.get(ConstantsConvenioBTLMF.COL_DNI) != null && !benif.get(ConstantsConvenioBTLMF.COL_DNI).equals("N"))
       {
           retorno = true;
           VariablesConvenioBTLMF.vDni             = (String)benif.get(ConstantsConvenioBTLMF.COL_DNI);
           VariablesConvenioBTLMF.vNombre          = ((String)benif.get(ConstantsConvenioBTLMF.COL_DES_NOM_CLIENTE)).trim(); // 15.08.13 RLLANTOY siempre es NULO
           VariablesConvenioBTLMF.vNomCliente     = ((String)benif.get(ConstantsConvenioBTLMF.COL_DES_APE_CLIENTE)).trim();// CHUANES se cambio VariablesConvenioBTLMF.vApellidoPat por  VariablesConvenioBTLMF.vNomCliente 
           VariablesConvenioBTLMF.vCreacionCliente = (String)benif.get(ConstantsConvenioBTLMF.COL_FLG_CREA_CLIENTE);
           VariablesConvenioBTLMF.vCodCliente      = (String)benif.get(ConstantsConvenioBTLMF.COL_COD_CLIENTE);

           log.debug("Codigo Cliente :"+VariablesConvenioBTLMF.vCodCliente);

           String numPoliza     = (String)benif.get(ConstantsConvenioBTLMF.COL_NUM_POLIZA);
           String numPlan 	    = (String)benif.get(ConstantsConvenioBTLMF.COL_NUM_PLAN);
           String codAsegurado  = (String)benif.get(ConstantsConvenioBTLMF.COL_COD_ASEGURADO);
           String numItem       = (String)benif.get(ConstantsConvenioBTLMF.COL_NUM_IEM);
           String prt           = (String)benif.get(ConstantsConvenioBTLMF.COL_PRT);
           String numContrado   = (String)benif.get(ConstantsConvenioBTLMF.COL_NUM_CONTRATO);
           String tipoAsegurado = (String)benif.get(ConstantsConvenioBTLMF.COL_TIPO_ASEGURADO);

           VariablesConvenioBTLMF.vLineaCredito  = (String)benif.get(ConstantsConvenioBTLMF.COL_LCREDITO);
           VariablesConvenioBTLMF.vEstado        = (String)benif.get(ConstantsConvenioBTLMF.COL_ESTADO);


           VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_NUM_POLIZA,numPoliza);
           VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_NUM_PLAN,numPlan);
           VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_COD_ASEGURADO,codAsegurado);
           VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_NUM_IEM,numItem);
           VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_NOMB_CLIENTE,VariablesConvenioBTLMF.vNombre);
           VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_PRT,prt);
           VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_NUM_CONTRATO,numContrado);
           VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_TIPO_ASEGURADO,tipoAsegurado);
       }

       return retorno;
   }

   private boolean existeMedico()
   {
       log.debug("<<<<<<<<<<<<Metodo: existeMedico>>>>>>>>>>>>");
       Map benif = null;
       boolean retorno = false;
       benif =  UtilityConvenioBTLMF.obtieneMedico(VariablesConvenioBTLMF.vCodConvenio, (txtBusqueda.getText().trim()), this);
       if(benif.get(ConstantsConvenioBTLMF.COL_NUM_CMP) != null)
       {
           retorno = true;
           VariablesConvenioBTLMF.vCodigoMedico  = (String)benif.get(ConstantsConvenioBTLMF.COL_NUM_CMP);
           VariablesConvenioBTLMF.vNombreMedico        = (String)benif.get(ConstantsConvenioBTLMF.COL_DES_NOM_MEDICO);
           VariablesConvenioBTLMF.vApeMedico   = (String)benif.get(ConstantsConvenioBTLMF.COL_DES_APE_MEDICO);
           VariablesConvenioBTLMF.vDescripcion = VariablesConvenioBTLMF.vNombreMedico +"-"+VariablesConvenioBTLMF.vApeMedico;
       }
       return retorno;
   }

   private String completarCerosCodigo(String dato) {

       String codigo = "";
       String cero = "";

       if (dato != null) {
           int cantCero = 10 - dato.trim().length();
           for (int i = 0; i < cantCero; i++) {
               cero = cero + "0";
           }
       }

       codigo = cero + dato;

       return codigo;
   }


   private void cargarMedico()
   {
       log.debug("<<<<<<<<<<<<Metodo: cargarMedico>>>>>>>>>>>>");
       FarmaUtility.showMessage(this,
               "Médico \n" +
               VariablesConvenioBTLMF.vNombreMedico+ " "+VariablesConvenioBTLMF.vApeMedico+" \n"+
               "Código: " + VariablesConvenioBTLMF.vCodigoMedico,
               null);

       VariablesConvenioBTLMF.vAceptar = true;
       
       // 14.08.13 RLLANTOY soluciona el error de almacenar el valor en VariablesConvenioBTLMF.vCodigoAux cuando se ingresa el codigo del medico
       VariablesConvenioBTLMF.vCodigoAux = VariablesConvenioBTLMF.vCodigoMedico;
       
       txtIngresoTexto.setText(VariablesConvenioBTLMF.vNombreMedico +" "+VariablesConvenioBTLMF.vApeMedico);

   }

   private boolean existeDiagnostico()
   {
       log.debug("<<<<<<<<<<<<Metodo: existeDiagnostico>>>>>>>>>>>>");
       Map benif = null;
       boolean retorno = false;

       benif =  UtilityConvenioBTLMF.obtieneDiagnostico(VariablesConvenioBTLMF.vCodCIE10, txtBusqueda.getText().trim(), this);

       if(benif.get(ConstantsConvenioBTLMF.COL_COD_CIE_10) != null)
       {
           retorno = true;
           VariablesConvenioBTLMF.vCodCIE10        = (String)benif.get(ConstantsConvenioBTLMF.COL_COD_CIE_10);
           VariablesConvenioBTLMF.descripDiagno = (String)benif.get(ConstantsConvenioBTLMF.COL_DES_DIAGNOSTICO);//variable descripcion de mensaje personalizado
           VariablesConvenioBTLMF.vDescDiagnostico = VariablesConvenioBTLMF.vCodCIE10;//variable codigo cie que se setea en base de datos       
           VariablesConvenioBTLMF.vDescripcion =  VariablesConvenioBTLMF.vDescDiagnostico;
       }

       return retorno;
   }


   private void cargarDiagnostico()
   {   //CHUANES--MODIFICADO 28.03.2014
       log.debug("Carga Diagnostico"+VariablesConvenioBTLMF.vCodCIE10);
       FarmaUtility.showMessage(this,"Diagnóstico \n" +VariablesConvenioBTLMF.descripDiagno+" \n"+"CIE 10: " + VariablesConvenioBTLMF.vCodCIE10,null);
       VariablesConvenioBTLMF.vAceptar = true;
       //ALMACENAMOS EL CODIGO  DIAGNOSTICO EN LA VARIABLE COD AUX PARA QUE SE ENVIE A BASE DE DATOS
       VariablesConvenioBTLMF.vCodigoAux =VariablesConvenioBTLMF.codDiagnostico;
       codigo = VariablesConvenioBTLMF.vCodCIE10;
       txtIngresoTexto.setText(VariablesConvenioBTLMF.vCodCIE10);

   }

   private void cargarBenificiario()
   {
       log.debug("<<<<<<<<<<<<Metodo: cargarBenificiario>>>>>>>>>>>>"+VariablesConvenioBTLMF.vCreacionCliente);

      if(VariablesConvenioBTLMF.vCreacionCliente != null
       && VariablesConvenioBTLMF.vCreacionCliente.equals(ConstantsConvenioBTLMF.FLG_SOLICITUD_BENIF))
      {
           FarmaUtility.showMessage(this,
                   "Benificiario Pendiente de Aprobación \n" +
                   VariablesConvenioBTLMF.vNombre +" " +
                   VariablesConvenioBTLMF.vNomCliente +" \n"+
                   "DNI: " + VariablesConvenioBTLMF.vDni +""
                   ,
                   null);
           VariablesConvenioBTLMF.vAceptar = false;
      }
      else
      {
          FarmaUtility.showMessage(this,
                   "Bienvenido \n" +
                  // VariablesConvenioBTLMF.vNombre.trim() +" " +  15.08.13 RLLANTOY - Se comentó porque siempre es nulo
                   VariablesConvenioBTLMF.vNomCliente.trim() +" \n"+
                   "DNI: " + VariablesConvenioBTLMF.vDni+" \n"+
                   "LCREDITO:"+VariablesConvenioBTLMF.vLineaCredito+"\n"+
                   "ESTADO:"+VariablesConvenioBTLMF.vEstado+"",
                   null);
           VariablesConvenioBTLMF.vAceptar = true;
          
           // 14.08.13 RLLANTOY soluciona el error de almacenar el valor en VariablesConvenioBTLMF.vCodigoAux cuando se ingresa el DNI
           VariablesConvenioBTLMF.vCodigoAux = VariablesConvenioBTLMF.vDni;  
           
           VariablesConvenioBTLMF.vDescripcion = VariablesConvenioBTLMF.vNomCliente.trim();
           txtIngresoTexto.setText(VariablesConvenioBTLMF.vNomCliente.trim());
      }



   }

   private void nuevoBenificiario()
   {
        log.debug("<<<<<<<<<<<<Metodo: nuevoBenificiario>>>>>>>>>>>>");

        VariablesConvenioBTLMF.vDni = txtBusqueda.getText().trim();
        DlgDatoBenificiarioBTLMF benifi = new DlgDatoBenificiarioBTLMF(this,"",true);
           benifi.setVisible(true);

           log.debug("Nombre::"+VariablesConvenioBTLMF.vNombre +" "+ VariablesConvenioBTLMF.vNomCliente);
           VariablesConvenioBTLMF.vDescripcion = VariablesConvenioBTLMF.vNombre +" "+VariablesConvenioBTLMF.vNomCliente;

   }

   private boolean verificarIngresoDato(Map conv)
   {

       log.debug("<<<<<<<<<<<<Metodo: verificarIngresoDato>>>>>>>>>>>>");

       boolean retorno = true;

       log.debug("CONVENIO:"+conv);
       log.debug("COL_TIPO_OBJ:"+conv.get(ConstantsConvenioBTLMF.COL_TIPO_OBJ));
       log.debug("COL_VALOR_IN:"+conv.get(ConstantsConvenioBTLMF.COL_VALOR_IN));

       if(conv.get(ConstantsConvenioBTLMF.COL_TIPO_OBJ).equals(ConstantsConvenioBTLMF.OBJ_LISTA_PANTALLA)
           && (conv.get(ConstantsConvenioBTLMF.COL_VALOR_IN) == null
           || conv.get(ConstantsConvenioBTLMF.COL_VALOR_IN).toString().trim().equals("")))
       {
          FarmaUtility.showMessage(this,"Es obligatorio el ingreso del "+conv.get(ConstantsConvenioBTLMF.COL_NOMBRE_CAMPO),txtBusqueda);
          retorno = false;
       }

       if(conv.get(ConstantsConvenioBTLMF.COL_TIPO_OBJ).equals(ConstantsConvenioBTLMF.OBJ_IN_TEXTO)
               && (conv.get(ConstantsConvenioBTLMF.COL_VALOR_IN) == null
               || conv.get(ConstantsConvenioBTLMF.COL_VALOR_IN).toString().trim().equals("")))
       {
          FarmaUtility.showMessage(this,"Es obligatorio el ingreso del "+conv.get(ConstantsConvenioBTLMF.COL_NOMBRE_CAMPO),txtIngresoTexto);
          retorno = false;
       }

       log.debug(":"+retorno);

       return retorno;
    }


   private boolean verificarIngresoDatoFinal(Map conv)
   {

           log.debug("<<<<<<<<<<<<Metodo: verificarIngresoDatoFinal>>>>>>>>>>>>");

	       boolean retorno = true;
	       int cantDatoIngresados = 0;

	      for (int i = 0;i < VariablesConvenioBTLMF.vDatosConvenio.size(); i++)
	      {
	    	  Map dato = (Map)VariablesConvenioBTLMF.vDatosConvenio.get(i);

	    	  String pCreaObjeto = dato.get(ConstantsConvenioBTLMF.COL_CREA_OBJ).toString().trim();
	          String pEditable   = dato.get(ConstantsConvenioBTLMF.COL_EDITABLE).toString().trim();

	          if(dato.get(ConstantsConvenioBTLMF.COL_VALOR_IN) != null)
	          {
		           String codValorIn  = dato.get(ConstantsConvenioBTLMF.COL_VALOR_IN).toString();

			       if((pCreaObjeto+pEditable).trim().equalsIgnoreCase(FarmaConstants.INDICADOR_S+FarmaConstants.INDICADOR_S)
			           && codValorIn != null && !codValorIn.trim().equals(""))
			       {
			    	   cantDatoIngresados++;

			       }
	          }
	      }

	      log.debug("pagFinal			: "+pagFinal);
	      log.debug("cantDatoIngresados: "+cantDatoIngresados);

	      if (pagFinal != cantDatoIngresados)
	      {
	    	   retorno = false;
	      }

       return retorno;
   }



   private void actualizaBenificiario()
   {
     log.debug("<<<<<<<<Metodo:actualizaBenificiario>>>>>>>");

      VariablesConvenioBTLMF.vPaginaActual = pagInicio;
      log.debug("Pagina Actual:"+VariablesConvenioBTLMF.vPaginaActual) ;
      Map convenioActual = (Map)VariablesConvenioBTLMF.vDatosConvenioIngresar.get(VariablesConvenioBTLMF.vPaginaActual);

      VariablesConvenioBTLMF.vDescripcion = VariablesConvenioBTLMF.vNombre +" "+VariablesConvenioBTLMF.vApellidoPat;
         ingresarDatos(convenioActual);

         if(verificarIngresoDato(convenioActual))
       {
             cargarSiguienteDato();
             editarDato();
       }
   }

   public void iniciarVariablesGlobales()
   {
       VariablesConvenioBTLMF.listaDatosNoEditables = new HashMap();
       VariablesConvenioBTLMF.vDatosConvenio = null;
       VariablesConvenioBTLMF.vDatosConvenioIngresar = new ArrayList();
       VariablesConvenioBTLMF.vPaginaActual = 0;
   }

   private void consultarSaldoCreditoBenif()
   {

       log.debug("<<<<<<<<Metodo:consultarSaldoCreditoBenif>>>>>>>");

       log.debug("Inicio de Hilo");

          // crear y nombrar a cada subproceso
          SubProcesosBTLMF subproceso = new SubProcesosBTLMF("CONS_SALD_CREDITO_BENIF",myParentFrame,this);
          subproceso.mostrar();

       log.debug("Inicio de Fin");

   }

   private boolean validarDni(String cadena)
   {
	  boolean retorno = false;
	   if(cadena != null && cadena.length() > 0)
	   {
		   for (int i = 0; i < cadena.length() ; i++)
		   {
			  String  caracter = cadena.substring(0+i,i+1);

			   	boolean space = false;

		   	boolean numero  = Pattern.matches("[0-9]",caracter);
		       if(numero || space)
		       {
		       	retorno = true;
		       }
		       else
		       {
		    	 retorno = false;
		    	 break;
		       }
		  }
	  }
	  return retorno;
   }

}
