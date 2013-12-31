package mifarma.ptoventa.convenioBTLMF;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;
import mifarma.ptoventa.convenio.reference.VariablesConvenio;
import mifarma.ptoventa.convenioBTLMF.reference.ConstantsConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.DBConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.UtilityConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2011 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaPantallaBTLMF.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * FRAMIREZ      12.11.2011  Creación<br>
 * <br>
 * @author Fredy Ramirez Calderon<br>
 * @version 1.0<br>
 *
 */

public class DlgListaPantallaBTLMF extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(DlgListaPantallaBTLMF.class);

    private JDialog myParentFrame;
    FarmaTableModel tableModelListaDatos;

    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private GridLayout gridLayout1 = new GridLayout();
    private JPanelHeader pnlDatos = new JPanelHeader();

    private JTextFieldSanSerif txtNombre = new JTextFieldSanSerif();

    private JButtonLabel btnNombre = new JButtonLabel();
    private JPanelTitle pnlTituloLista = new JPanelTitle();
    private JButtonLabel btnTitulo = new JButtonLabel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblDatos = new JTable();

    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JLabelFunction jLabelFunction2 = new JLabelFunction();
    private JLabelFunction jLabelFunction3 = new JLabelFunction();


    private int COLUMN_DESCRIPCION = 1;
    private int COLUMN_CODIGO = 0;
    private int COLUMN_CODIGO_AUX = 2;
    private int COLUMN_NOMBRE_CLIENTE = 1;
    private int COLUMN_ESTADO = 3;


    private int COLUMN_LINEA_CREDITO = 2;

    private int COLUMN_NUM_POLIZA = 5;
    private int COLUMN_NUM_PLAN = 6;
    private int COLUMN_COD_ASEGURADO =7;
    private int COLUMN_NUM_ITEM = 8;
    private int COLUMN_PRT = 9;
    private int COLUMN_NUM_CONTRATO = 10;
    private int COLUMN_TIPO_ASEGURADO = 11;
    private int COLUMN_COD_CLIENTE = 12;

    private ArrayList listaTodo= new ArrayList();




    public DlgListaPantallaBTLMF() {
        this(null, "", false);
    }

    public DlgListaPantallaBTLMF(JDialog parent, String title, boolean modal)
    {
        super(parent, title, modal);
        myParentFrame = parent;
        try
        {
        	jbInit();
            initialize();

        } catch (Exception e)
        {
            log.error("",e);
        }

    }
    int posicionBarra = 0;

    private void jbInit() throws Exception {

    	this.getContentPane().removeAll();

    	Map convenio =  (Map)VariablesConvenioBTLMF.vDatosConvenio.get((VariablesConvenioBTLMF.vPaginaActual));


        this.getContentPane().setLayout(gridLayout1);

        jPanelWhite1.setBounds(new Rectangle(0, 0, 617, 373));
        tblDatos.setBounds(new Rectangle(0, 0, 455, 80));

        this.setTitle("Lista de "+convenio.get(ConstantsConvenioBTLMF.COL_NOMBRE_CAMPO));
    	btnNombre.setText(""+convenio.get(ConstantsConvenioBTLMF.COL_NOMBRE_CAMPO));
    	btnTitulo.setText("Relación de "+convenio.get(ConstantsConvenioBTLMF.COL_NOMBRE_CAMPO));

        this.setBounds(new Rectangle(0, 0, 620, 373));
        this.addWindowListener(new WindowAdapter()
        {
           public void windowOpened(WindowEvent e)
            {
              this_windowOpened(e);
            }
        });

        pnlDatos.setBounds(new Rectangle(10, 10, 600, 40));
        txtNombre.setBounds(new Rectangle(128, 10, 295, 20));
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(KeyEvent e)
        {
        	chkKeyPressed(e);

        }
        public void keyReleased(KeyEvent e)
        {
        	chkKeyReleased(e);
        }
        });

        btnNombre.setBounds(new Rectangle(20, 10, 102, 20));
        pnlTituloLista.setBounds(new Rectangle(10, 60, 600, 25));
        btnTitulo.setBounds(new Rectangle(10, 5, 250, 15));
        btnTitulo.setMnemonic('r');
        jScrollPane1.setBounds(new Rectangle(10, 85, 600, 225));


        if((VariablesConvenioBTLMF.vCodTipoCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_BENIFICIARIO))&&
      		!VariablesConvenioBTLMF.vFlgCreacionCliente.equals("N")&&
      		(VariablesConvenioBTLMF.vFlgCreacionCliente.equals(ConstantsConvenioBTLMF.FLG_CREACION_BENIF)||
      		 VariablesConvenioBTLMF.vFlgCreacionCliente.equals(ConstantsConvenioBTLMF.FLG_SOLICITUD_BENIF)))
       {

	        jLabelFunction1.setBounds(new Rectangle(245, 312, 117, 19));
	        jLabelFunction1.setText("[ ENTER ] Aceptar");
	        jLabelFunction3.setBounds(new Rectangle(368, 312, 117, 19));

	        if (VariablesConvenioBTLMF.vFlgCreacionCliente.equals(ConstantsConvenioBTLMF.FLG_SOLICITUD_BENIF))
	        {
	           jLabelFunction3.setText("[ F12 ] Solicitar");
	        }
	        else
	        {
	           jLabelFunction3.setText("[ F12 ] Crear");
	        }
	        jLabelFunction2.setBounds(new Rectangle(490, 312, 117, 19));
	        jLabelFunction2.setText("[ ESC ] Cerrar");
	        jPanelWhite1.add(jLabelFunction3, null);

    	}
        else
        {
        	jLabelFunction1.setBounds(new Rectangle(368, 312, 117, 19));
	        jLabelFunction1.setText("[ ENTER ] Aceptar");
	        jLabelFunction2.setBounds(new Rectangle(494, 312, 117, 19));
	        jLabelFunction2.setText("[ ESC ] Cerrar");

        }

        pnlDatos.add(txtNombre, null);
        pnlDatos.add(btnNombre, null);
        pnlTituloLista.add(btnTitulo,null);
        jScrollPane1.getViewport().add(tblDatos, null);
        jPanelWhite1.add(jLabelFunction2, null);
        jPanelWhite1.add(jLabelFunction1, null);
        jPanelWhite1.add(jScrollPane1, null);
        jPanelWhite1.add(pnlTituloLista, null);
        jPanelWhite1.add(pnlDatos, null);
        this.getContentPane().add(jPanelWhite1, null);
    }


    private void initialize() {
    	initTableLista();
        FarmaVariables.vAceptar = false;
    }


    private void initTableLista()
    {

    	if(VariablesConvenioBTLMF.vFlg_lista.equals("1"))
    	{
    		initTableListaAdicional();
    		cargaListaDatos(false);
    	}
    	else
    	if (VariablesConvenioBTLMF.vCodTipoCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_DIAGNOSTICO_UIE))
    	{
    		initTableListaDiagnostico();
    		cargaListaDatos(false);
    	}
    	else
    	if(VariablesConvenioBTLMF.vCodTipoCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_BENIFICIARIO))
    	{
    		VariablesConvenioBTLMF.vDescDiagnostico = VariablesConvenioBTLMF.vBuscarNombreBenif;
    		initTableListaBenificiario();
    		cargaListaDatos(false);
    	}
    	else
    	if(VariablesConvenioBTLMF.vCodTipoCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_REPARTIDOR))
    	{
    		initTableListaRepartidor();
    		cargaListaDatos(false);
    	}
    	else
    	if(VariablesConvenioBTLMF.vCodTipoCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_MEDICO))
    	{
    		initTableListaMedico();
    		cargaListaDatos(false);
    	}
    	else
    	if(VariablesConvenioBTLMF.vCodTipoCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_ORIG_RECETA))
    	{
    		initTableListaClinica();
    		cargaListaDatos(false);
    	}


    }

    private void initTableListaBenificiario()
    {

    	log.debug("initTableListaBenificiario:"+tableModelListaDatos);

    	if(tableModelListaDatos == null)
    	{
           tableModelListaDatos = new FarmaTableModel(ConstantsConvenioBTLMF.columnsListaBenificiario,
					                                  ConstantsConvenioBTLMF.defaultValuesListaBenificiario,
					                                  0);
         FarmaUtility.initSimpleList(tblDatos,tableModelListaDatos,ConstantsConvenioBTLMF.columnsListaBenificiario);

    	}
    }

    private void initTableListaDiagnostico()
    {

        log.debug("initTableListaDiagnostico:"+tableModelListaDatos);

    	if(tableModelListaDatos == null)
    	{
    	   tableModelListaDatos =
                new FarmaTableModel(ConstantsConvenioBTLMF.columnsListaDiagnostico,
                                    ConstantsConvenioBTLMF.defaultValuesListaDiagnostico,
                                    0);
           FarmaUtility.initSimpleList(tblDatos, tableModelListaDatos,ConstantsConvenioBTLMF.columnsListaDiagnostico);
    	}

    }

    private void initTableListaMedico()
    {

        log.debug("initTableListaMedico:"+tableModelListaDatos);

    	if(tableModelListaDatos == null)
    	{
    	   tableModelListaDatos =
                new FarmaTableModel(ConstantsConvenioBTLMF.columnsListaMedico,
                                    ConstantsConvenioBTLMF.defaultValuesListaMedico,
                                    0);
           FarmaUtility.initSimpleList(tblDatos, tableModelListaDatos,ConstantsConvenioBTLMF.columnsListaMedico);
    	}
    }

    private void initTableListaClinica()
    {

        log.debug("initTableListaClinica:"+tableModelListaDatos);

    	if(tableModelListaDatos == null)
    	{
    	   tableModelListaDatos =
                new FarmaTableModel(ConstantsConvenioBTLMF.columnsListaClinica,
                                    ConstantsConvenioBTLMF.defaultValuesListaClinica,
                                    0);
           FarmaUtility.initSimpleList(tblDatos, tableModelListaDatos,ConstantsConvenioBTLMF.columnsListaClinica);
    	}
    }

    private void initTableListaRepartidor()
    {

        log.debug("initTableListaRepartidor:"+tableModelListaDatos);

    	if(tableModelListaDatos == null)
    	{
    	   tableModelListaDatos =
                new FarmaTableModel(ConstantsConvenioBTLMF.columnsListaRepartidor,
                                    ConstantsConvenioBTLMF.defaultValuesListaRepartidor,
                                    0);
           FarmaUtility.initSimpleList(tblDatos, tableModelListaDatos,ConstantsConvenioBTLMF.columnsListaRepartidor);
    	}
    }


    private void initTableListaAdicional()
    {

        log.debug("initTableListaAdicional:"+tableModelListaDatos);

    	if(tableModelListaDatos == null)
    	{
    	   tableModelListaDatos =
                new FarmaTableModel(ConstantsConvenioBTLMF.columnsListaDatosAdic,
                                    ConstantsConvenioBTLMF.defaultValuesListaDatosAdic,
                                    0);
           FarmaUtility.initSimpleList(tblDatos, tableModelListaDatos,ConstantsConvenioBTLMF.columnsListaDatosAdic);
    	}
    }



    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtNombre);
    }

    private void chkKeyPressed(KeyEvent e)
    {
    	String descripcionTemp = "";

    	int vFila = tblDatos.getSelectedRow();
        if(vFila > -1)
        {
            VariablesConvenioBTLMF.listaDatos.clear();
            VariablesConvenioBTLMF.listaDatos.add(tableModelListaDatos.data.get(vFila));
            descripcionTemp = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos,0,COLUMN_DESCRIPCION);
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            cerrarVentana(false);
        }
        if (mifarma.ptoventa.reference.UtilityPtoVenta.verificaVK_F12(e) && VariablesConvenioBTLMF.vCodTipoCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_BENIFICIARIO))
        {
        	nuevoBenificiario();

			//e.setKeyChar('S');
			//e.setKeyCode(KeyEvent.VK_ENTER);
			//buscaPorDescripcion(e);
        }
        else
        if (e.getKeyCode() == KeyEvent.VK_ENTER &&
        		txtNombre.getSelectedText() != null &&
        		txtNombre.getSelectedText().equalsIgnoreCase(descripcionTemp.trim()))
        {
	       	 guardarRegistroSeleccionado();
        }
        else
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {

        	if (VariablesConvenioBTLMF.vCodTipoCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_DIAGNOSTICO_UIE))
        	{
        		if(esCodigoUIE(txtNombre.getText().trim()))
        		{
        			buscaPorCodigoUIE(e);
        		}
        		else
        		{
        			buscaPorDescripcion(e);
        		}
        	}
        	else
        	{
        		if(esCodigo(txtNombre.getText().trim()))
        		{
        			buscaPorCodigo(e);
        		}
        		else
        		{
                    buscaPorDescripcion(e);

        		}
        	}

        }
        else
        {
        	 if (e.getKeyCode() != KeyEvent.VK_ENTER && e.getKeyCode() != KeyEvent.VK_LEFT && e.getKeyCode() != KeyEvent.VK_BACK_SPACE
           		  && e.getKeyCode() != KeyEvent.VK_CLEAR)
        	 {
        		 FarmaGridUtils.aceptarTeclaPresionada(e,tblDatos,txtNombre,1);
            	 posicionBarra = txtNombre.getCaret().getMark()+1;
        	 }
        }

    }




    private void cerrarVentana(boolean pAceptar)
    {
    	VariablesConvenioBTLMF.vAceptar= pAceptar;
        this.setVisible(false);
        this.dispose();
    }



    private void chkKeyReleased(KeyEvent e)
    {

      if (e.getKeyCode() != KeyEvent.VK_ENTER && e.getKeyCode() != KeyEvent.VK_LEFT && e.getKeyCode() != KeyEvent.VK_BACK_SPACE
    		  && e.getKeyCode() != KeyEvent.VK_CLEAR)
       {
          String descripcion = "";

    	  int vFila = -1;
   		   if(FarmaGridUtils.buscarDescripcion(e,tblDatos,txtNombre,1))
   		   {
		   		     vFila = tblDatos.getSelectedRow();
		          if(vFila > -1)
		          {
		              VariablesConvenioBTLMF.listaDatos.clear();
		              VariablesConvenioBTLMF.listaDatos.add(tableModelListaDatos.data.get(vFila));
		              descripcion = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos,0,COLUMN_DESCRIPCION);
		          }
		          txtNombre.setText(descripcion);
		          txtNombre.select(posicionBarra,descripcion.length());
   		  }
       }
     }


    private void cargaListaDatos(boolean pConCheck)
    {


        try
        {

            DBConvenioBTLMF.listaDatos(tableModelListaDatos,pConCheck);
            FarmaUtility.ordenar(tblDatos, tableModelListaDatos, 1,FarmaConstants.ORDEN_ASCENDENTE);

            listaTodo = listaTodo(tableModelListaDatos);

            log.debug("Cantidad:"+tblDatos.getRowCount());

            if (tblDatos.getRowCount() <= 0)
            {
                FarmaUtility.showMessage(this,"No se encontro ningun Dato ",txtNombre);
                cerrarVentana(false);
            }
            else
            {
                FarmaGridUtils.showCell(tblDatos,0,0);
                FarmaUtility.setearActualRegistro(tblDatos,txtNombre,1);
            }

        }
        catch (SQLException sql)
        {
            log.error("",sql);
            FarmaUtility.showMessage(this,"Ocurio un error al listar datos del convenio \n " +sql.getMessage(), txtNombre);
        }
    }

    private void guardarRegistroSeleccionado()
    {

      	    VariablesConvenioBTLMF.listaDatos = new ArrayList();


            int vFila = tblDatos.getSelectedRow();
            if(vFila > -1)
            {

              VariablesConvenioBTLMF.listaDatos.clear();
              VariablesConvenioBTLMF.listaDatos.add(tableModelListaDatos.data.get(vFila));

              if(VariablesConvenioBTLMF.vCodTipoCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_DIAGNOSTICO_UIE))
              {
            	  VariablesConvenioBTLMF.vCodigo      = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos,0,COLUMN_CODIGO);
                  VariablesConvenioBTLMF.vDescripcion = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos,0,COLUMN_CODIGO);
                  VariablesConvenioBTLMF.vCodigoAux   = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos,0,COLUMN_CODIGO_AUX);


              }
              else
              {
            	  VariablesConvenioBTLMF.vCodigo      = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos,0,COLUMN_CODIGO);
                  VariablesConvenioBTLMF.vDescripcion = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos,0,COLUMN_DESCRIPCION);
                  VariablesConvenioBTLMF.vCodigoAux   = VariablesConvenioBTLMF.vCodigo;
              }

                  log.debug("vCodigoAux: "+VariablesConvenioBTLMF.vCodigoAux);

              if(VariablesConvenioBTLMF.vCodTipoCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_BENIFICIARIO))
              {
                  String dni = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos,0,COLUMN_CODIGO);
	              String nombreCliente = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos,0,COLUMN_NOMBRE_CLIENTE);

	              VariablesConvenioBTLMF.vLineaCredito = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos,0,COLUMN_LINEA_CREDITO);

	              String numPoliza     = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos,0,COLUMN_NUM_POLIZA);
	              String numPlan       = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos,0,COLUMN_NUM_PLAN);
	              String codAsegurado  = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos,0,COLUMN_COD_ASEGURADO);
	              String numItem       = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos,0,COLUMN_NUM_ITEM);
	              String prt           = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos,0,COLUMN_PRT);
	              String numContrado   = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos,0,COLUMN_NUM_CONTRATO);
	              String tipoAsegurado = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos,0,COLUMN_TIPO_ASEGURADO);
	              String codCliente    = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos,0,COLUMN_COD_CLIENTE);

	              VariablesConvenioBTLMF.vDni = dni;
	              log.debug("DNI:  "+dni);
	              log.debug("Nombre Cliente:  "+nombreCliente);

	              log.debug("CodCliente:  "+codCliente);
	              log.debug("linea Credito:  "+VariablesConvenioBTLMF.vLineaCredito);


	              VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_NUM_POLIZA,numPoliza);
	              VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_NUM_PLAN,numPlan);
	              VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_COD_ASEGURADO,codAsegurado);
	              VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_NUM_IEM,numItem);
	              VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_NOMB_CLIENTE,nombreCliente);
	              VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_PRT,prt);
	              VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_NUM_CONTRATO,numContrado);
	              VariablesConvenioBTLMF.listaDatosNoEditables.put(ConstantsConvenioBTLMF.CODIGO_TIPO_ASEGURADO,tipoAsegurado);
	              VariablesConvenioBTLMF.vCodCliente = codCliente;


                  //
	              VariablesConvenioBTLMF.vNomCliente = nombreCliente;
                  VariablesConvenio.vArrayList_DatosConvenio.clear();
                  VariablesConvenio.vArrayList_DatosConvenio.add(VariablesConvenioBTLMF.vCodConvenio);//0
                  VariablesConvenio.vArrayList_DatosConvenio.add(codCliente);//1
                  VariablesConvenio.vArrayList_DatosConvenio.add(nombreCliente);//2
                  VariablesConvenio.vArrayList_DatosConvenio.add(" ");//3
                  VariablesConvenio.vArrayList_DatosConvenio.add(" ");//4
                  VariablesConvenio.vArrayList_DatosConvenio.add(dni/*txtNumeroDocumento.getText()*/);//5
                  VariablesConvenio.vArrayList_DatosConvenio.add(" ");//6
                  VariablesConvenio.vArrayList_DatosConvenio.add(" ");//7
                  VariablesConvenio.vArrayList_DatosConvenio.add(" ");//8
                  VariablesConvenio.vArrayList_DatosConvenio.add(" ");//9
                  VariablesConvenio.vArrayList_DatosConvenio.add(" ");//10

                  //VariablesConvenio.vTextoCliente = " ";
              }

            }
            else
            if(VariablesConvenioBTLMF.vCodTipoCampo.equals(ConstantsConvenioBTLMF.COD_DATO_CONV_BENIFICIARIO))
            {
              obtieneBenificiario();
            }

           log.debug("CREACION CLIENTE:"+VariablesConvenioBTLMF.vCreacionCliente);
           if(VariablesConvenioBTLMF.vCreacionCliente != null && VariablesConvenioBTLMF.vCreacionCliente.equals(ConstantsConvenioBTLMF.FLG_SOLICITUD_BENIF))
           {
        	   FarmaUtility.showMessage(this,
    	               "Benificiario Pendiente de Aprobación \n" +
    	               VariablesConvenioBTLMF.vNombre +" " +
    	               VariablesConvenioBTLMF.vApellidoPat +" \n"+
    	               "DNI: " + VariablesConvenioBTLMF.vDni +""
    	               ,
    	               null);
           }
           else
           {
              cerrarVentana(true);
           }
     }
    private void obtieneBenificiario()
    {
 	   log.debug("<<<<<<<<<<<<Metodo: obtieneBenificiario>>>>>>>>>>>>DNI:"+VariablesConvenioBTLMF.vCodigo);
 	   Map benif = null;
 	   benif =  UtilityConvenioBTLMF.obtieneBenificiario(VariablesConvenioBTLMF.vCodConvenio,  VariablesConvenioBTLMF.vCodigo.trim() , this);
 	   if(benif.get(ConstantsConvenioBTLMF.COL_DNI) != null && !benif.get(ConstantsConvenioBTLMF.COL_DNI).equals("N"))
 	   {
 		   VariablesConvenioBTLMF.vDni             = (String)benif.get(ConstantsConvenioBTLMF.COL_DNI);
 		   VariablesConvenioBTLMF.vNombre          = (String)benif.get(ConstantsConvenioBTLMF.COL_DES_NOM_CLIENTE);
 		   VariablesConvenioBTLMF.vApellidoPat     = (String)benif.get(ConstantsConvenioBTLMF.COL_DES_APE_CLIENTE);
 		   VariablesConvenioBTLMF.vCreacionCliente = (String)benif.get(ConstantsConvenioBTLMF.COL_FLG_CREA_CLIENTE);
 	   }
    }
    private  boolean esCodigoUIE(String cadena)
    {
    	log.debug("<<<<<<<<<<<<Metodo: esCodigoUIE>>>>>>>>>>>>");
    	boolean retorno = false;
    	if(cadena != null && !cadena.trim().equals("") && cadena.trim().length() >= 3)
    	{
		   	char primerCaracter  = cadena.trim().charAt(0);
		   	char segundoCaracter = cadena.trim().charAt(1);
		   	char ultimoCaracter  = cadena.trim().charAt(cadena.length()-1);
		   	if(esLetra(primerCaracter) &&
		   	   esNumerico(segundoCaracter) &&
		   	   esNumerico(ultimoCaracter))
		   	{
		   		retorno = true;
		   	}
    	}
        return retorno;
    }
    public void buscaPorDescripcion(KeyEvent evento)
    {

    	log.debug("<<<<<<<<<<<<Metodo: buscaPorDescripcion>>>>>>>>>>>>"+txtNombre.getText());
    	FarmaGridUtils.aceptarTeclaPresionada(evento,tblDatos,txtNombre,1);
		jScrollPane1.getViewport().removeAll();
        //Filtramos por Descripcion.
		UtilityConvenioBTLMF.filtraDescripcion2(evento,
				                                  tableModelListaDatos,
				                                  listaTodo,
				                                  txtNombre,
				                                  1);
        //Compiamos en la tabla de la pantalla.
        FarmaUtility.ordenar(tblDatos, tableModelListaDatos, 1, FarmaConstants.ORDEN_ASCENDENTE);
        jScrollPane1.getViewport().add(tblDatos, null);
        FarmaUtility.moveFocus(txtNombre);
        FarmaGridUtils.showCell(tblDatos, 0, 0);
        FarmaUtility.setearActualRegistro(tblDatos,txtNombre, 1);
    }
    private void buscaPorCodigoUIE(KeyEvent evento)
    {
    	log.debug("<<<<<<<<<<<<Metodo: buscaPorCodigoUIE>>>>>>>>>>>>");
    	VariablesConvenioBTLMF.vAceptar= true;
    	FarmaGridUtils.aceptarTeclaPresionada(evento,tblDatos,txtNombre,0);
	    FarmaGridUtils.buscarCodigo_KeyPressed(evento,this,tblDatos,txtNombre,0);
	    FarmaUtility.moveFocus(txtNombre);

    }
    public void buscaPorCodigo(KeyEvent evento)
    {
    	log.debug("<<<<<<<<<<<<Metodo: buscaCodigo>>>>>>>>>>>>");
    	VariablesConvenioBTLMF.vAceptar= true;
    	FarmaGridUtils.aceptarTeclaPresionada(evento,tblDatos,txtNombre,0);
    	txtNombre.setText((txtNombre.getText()));
        FarmaGridUtils.buscarCodigo_KeyPressed(evento,this, tblDatos,txtNombre, 0);
        FarmaUtility.moveFocus(txtNombre);

    }
    public String completarCerosCodigo(String dato)
    {
      String codigo = "";
      String cero = "";
      if (dato != null)
      {
        int cantCero = 10 - dato.trim().length();
        for (int i = 0; i < cantCero; i++)
        {
           cero = cero + "0";
        }
      }
      codigo = cero + dato;
      return codigo;
    }

    public boolean esCodigo(String dato)
    {

    	boolean retorno = false;

    	try
    	{
    		Integer.parseInt(dato);
    		retorno = true;
    	}
    	catch(Exception e)
    	{

    		retorno = false;
    	}

    	return retorno;

    }

    public  ArrayList listaTodo(FarmaTableModel tableModelListaconvenios)
    {
		log.debug("<<<<<<<<<<<<Metodo: listaTodo  >>>>>>>>>>>>>>>");
		ArrayList  lista = new ArrayList();
		for (int k = 0; k < tableModelListaconvenios.getRowCount(); k++)
		{
			ArrayList listaTemp = new ArrayList();
			          listaTemp.add(tableModelListaconvenios.data.get(k));

	 		String  dato[] = new String[50];

		   		    dato[COLUMN_CODIGO]         = FarmaUtility.getValueFieldArrayList(listaTemp,0,COLUMN_CODIGO);
		   		    dato[COLUMN_DESCRIPCION]    = FarmaUtility.getValueFieldArrayList(listaTemp,0,COLUMN_DESCRIPCION);
		   		    dato[COLUMN_LINEA_CREDITO]  = FarmaUtility.getValueFieldArrayList(listaTemp,0,COLUMN_LINEA_CREDITO);
		   		    dato[COLUMN_ESTADO]         = FarmaUtility.getValueFieldArrayList(listaTemp,0,COLUMN_ESTADO);
		   		    dato[COLUMN_NOMBRE_CLIENTE] = FarmaUtility.getValueFieldArrayList(listaTemp,0,COLUMN_NOMBRE_CLIENTE);
				    dato[COLUMN_NUM_POLIZA]     = FarmaUtility.getValueFieldArrayList(listaTemp,0,COLUMN_NUM_POLIZA);
				    dato[COLUMN_NUM_PLAN]       = FarmaUtility.getValueFieldArrayList(listaTemp,0,COLUMN_NUM_PLAN);
				    dato[COLUMN_COD_ASEGURADO]  = FarmaUtility.getValueFieldArrayList(listaTemp,0,COLUMN_COD_ASEGURADO);
				    dato[COLUMN_NUM_ITEM]       = FarmaUtility.getValueFieldArrayList(listaTemp,0,COLUMN_NUM_ITEM);
				    dato[COLUMN_PRT]            = FarmaUtility.getValueFieldArrayList(listaTemp,0,COLUMN_PRT);
				    dato[COLUMN_NUM_CONTRATO]   = FarmaUtility.getValueFieldArrayList(listaTemp,0,COLUMN_NUM_CONTRATO);
				    dato[COLUMN_TIPO_ASEGURADO] = FarmaUtility.getValueFieldArrayList(listaTemp,0,COLUMN_TIPO_ASEGURADO);
				    dato[COLUMN_COD_CLIENTE]    = FarmaUtility.getValueFieldArrayList(listaTemp,0,COLUMN_COD_CLIENTE);


			lista.add(dato);
		}
	  return lista;
    }


    static  private boolean esLetra(char c)
    {
    	return  Pattern.matches("[a-zA-Z]",Character.toString(c));
    }
    static  private boolean esNumerico(char c)
    {
    	return  Pattern.matches("[0-9]",Character.toString(c));
    }


    private void nuevoBenificiario()
    {
 		log.debug("<<<<<<<<<<<<Metodo: nuevoBenificiario>>>>>>>>>>>>");
 	    DlgDatoBenificiarioBTLMF benifi = new DlgDatoBenificiarioBTLMF(this,"",true);
    	benifi.setVisible(true);
    	cargaListaDatos(false);
    	txtNombre.setText((VariablesConvenioBTLMF.vNombre+" "+VariablesConvenioBTLMF.vApellidoPat+" "+VariablesConvenioBTLMF.vApellidoMat).trim());
    	log.debug("Nombre:::"+txtNombre.getText());

    	if(VariablesConvenioBTLMF.vFlgCreacionCliente.equals("C")&& VariablesConvenioBTLMF.vAceptar)
    	{
    	   cerrarVentana(true);
    	}
    }


}
