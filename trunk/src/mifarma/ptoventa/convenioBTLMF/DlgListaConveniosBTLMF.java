package mifarma.ptoventa.convenioBTLMF;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaGridUtils;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.convenioBTLMF.reference.ConstantsConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.DBConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.UtilityConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.reference.ConstantsPtoVenta;

import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import mifarma.ptoventa.hilos.SubProcesosBTLMF;

/**
 * Copyright (c) 2011 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaConveniosBTLMF.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * FRAMIREZ      12.11.2011  Creación<br>
 * <br>
 * @author Fredy Ramirez Calderon<br>
 * @version 1.0<br>
 *
 */

public class DlgListaConveniosBTLMF extends JDialog {
    private Frame myParentFrame;
    FarmaTableModel tableModelListaconvenios;
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private GridLayout gridLayout1 = new GridLayout(); //  @jve:decl-index=0:
    private JPanelHeader pnlCliente = new JPanelHeader();
    private JTextField txtNombreConvenio = new JTextField();
    private JButtonLabel btnNombre = new JButtonLabel();
    private JPanelTitle pnlRelacionCliente = new JPanelTitle();
    private JButtonLabel btnRelacion = new JButtonLabel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblConvenios = new JTable();

    private JLabelFunction jLabelFunction1 = new JLabelFunction();
    private JLabelFunction jLabelFunction2 = new JLabelFunction();

    private ArrayList listaTodo = new ArrayList();

    // constantes de listado de convenios de clientes
    // 31.01.2008 dubilluz  creacion
    private int COLUMN_COD_CONV_ = 0;
    private int COLUMN_DESC_CONV = 1;
    private int COLUMN_RELACIONADO_CONV = 2;
    private int COLUMN_FLG_CREA_CLIEN_CONV = 3;


    public DlgListaConveniosBTLMF() {
        this(null, "", false);
    }

    public DlgListaConveniosBTLMF(Frame parent) {
        this(null, "", false);
        myParentFrame = parent;
        iniciarVariablesGlobales();
    }

    public DlgListaConveniosBTLMF(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;

        try {
            iniciarVariablesGlobales();
            jbInit();
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void jbInit() throws Exception {

        this.getContentPane().removeAll();
        this.getContentPane().setLayout(gridLayout1);

        jPanelWhite1.setBounds(new Rectangle(0, 0, 617, 373));
        tblConvenios.setBounds(new Rectangle(0, 0, 455, 80));

        this.setTitle("Lista de Convenios");
        this.setBounds(new Rectangle(0, 0, 620, 373));
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }
                });
        pnlCliente.setBounds(new Rectangle(10, 10, 600, 40));
        txtNombreConvenio.setBounds(new Rectangle(128, 10, 295, 20));
        txtNombreConvenio.addKeyListener(new java.awt.event.KeyAdapter() {

                    public void keyPressed(KeyEvent e) {

                        txtNombreConvenio_keyPressed(e);

                    }

                    public void keyReleased(KeyEvent e) {
                        txtNombreConvenio_keyReleased(e);
                    }

                });
        btnNombre.setText("Código/Nombre :");
        btnNombre.setBounds(new Rectangle(20, 10, 102, 20));
        btnNombre.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // btnClienteJuridico_actionPerformed(e);
                    }
                });
        pnlRelacionCliente.setBounds(new Rectangle(10, 60, 600, 25));
        btnRelacion.setText("Relacion de Convenios");
        btnRelacion.setBounds(new Rectangle(10, 5, 150, 15));
        btnRelacion.setMnemonic('r');
        btnRelacion.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // btnRelacion_actionPerformed(e);
                    }
                });
        jScrollPane1.setBounds(new Rectangle(10, 85, 600, 225));
        jLabelFunction1.setBounds(new Rectangle(368, 312, 117, 19));
        jLabelFunction1.setText("[ ENTER ] Aceptar");
        jLabelFunction2.setBounds(new Rectangle(494, 312, 115, 20));
        jLabelFunction2.setText("[ ESC ] Cerrar");
        pnlCliente.add(txtNombreConvenio, null);
        pnlCliente.add(btnNombre, null);
        pnlRelacionCliente.add(btnRelacion, null);
        jScrollPane1.getViewport().add(tblConvenios, null);
        jPanelWhite1.add(jLabelFunction2, null);
        jPanelWhite1.add(jLabelFunction1, null);
        jPanelWhite1.add(jScrollPane1, null);
        jPanelWhite1.add(pnlRelacionCliente, null);
        jPanelWhite1.add(pnlCliente, null);
        this.getContentPane().add(jPanelWhite1, null);
    }

    private void initialize()
    {
        String res = FarmaUtility.getIndLineaOnLine(FarmaConstants.CONECTION_RAC,FarmaConstants.INDICADOR_S);
    	System.out.println("resCOnexionRac="+res);
        initTableListaConvenios();
        FarmaVariables.vAceptar = false;
    }

    private void initTableListaConvenios() {
        tableModelListaconvenios =
                new FarmaTableModel(ConstantsConvenioBTLMF.columnsListaConvenios,
                                    ConstantsConvenioBTLMF.defaultValuesListaConvenios,
                                    0);
        FarmaUtility.initSimpleList(tblConvenios, tableModelListaconvenios,
                                    ConstantsConvenioBTLMF.columnsListaConvenios);


    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        cargaListaConvenios();
        FarmaUtility.moveFocus(txtNombreConvenio);
    }

    int posicionBarra = 0;

    private void chkKeyPressed(KeyEvent e) {

    	String descripcionTemp = "";

    	int vFila = tblConvenios.getSelectedRow();
        if(vFila > -1)
        {
            VariablesConvenioBTLMF.listaDatos.clear();
            VariablesConvenioBTLMF.listaDatos.add(tableModelListaconvenios.data.get(vFila));
            descripcionTemp = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos,0,1);
        }



        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            limpiarVariablesGlobales();
            cerrarVentana(false);
        }
        else
        if (e.getKeyCode() == KeyEvent.VK_ENTER &&
              		txtNombreConvenio.getSelectedText() != null &&
              		txtNombreConvenio.getSelectedText().trim().equalsIgnoreCase(descripcionTemp.trim()))
         {

        	 System.out.println("Texto Seleccionado:"+txtNombreConvenio.getSelectedText());
             System.out.println("Texto Ingresado   :"+descripcionTemp.trim());

              iniciarVariablesGlobales();
              guardaRegistroConvenio();
         }
        else
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (UtilityConvenioBTLMF.esTarjetaConvenio(txtNombreConvenio.getText())) {
                irIngresoDatosConvenio(txtNombreConvenio);
            } else {
                busquaConvenio(e);
            }
        } else {

        	if (e.getKeyCode() != KeyEvent.VK_ENTER && e.getKeyCode() != KeyEvent.VK_LEFT && e.getKeyCode() != KeyEvent.VK_BACK_SPACE
             		  && e.getKeyCode() != KeyEvent.VK_CLEAR)
          	 {

	            System.out.println(" txtNombreConvenio_keyPresss: ");
	            FarmaGridUtils.aceptarTeclaPresionada(e, tblConvenios,
	                                                  txtNombreConvenio, 1);
	            posicionBarra = txtNombreConvenio.getCaret().getMark()+1;
          	 }
        }
    }

    public void keyReleased(KeyEvent e) {
        txtNombreConvenio_keyReleased(e);
    }

    public void busquaConvenio(KeyEvent g) {


        if (esCodigo(txtNombreConvenio.getText().trim())) {
            buscaPorCodigo(g);
        } else {
            buscaPorDescripcion(g);
        }

    }

    public void buscaPorCodigo(KeyEvent evento) {

        FarmaGridUtils.aceptarTeclaPresionada(evento, tblConvenios,
                                              txtNombreConvenio, 0);
        txtNombreConvenio.setText(completarCerosCodigo(txtNombreConvenio.getText()));
        FarmaGridUtils.buscarCodigo_KeyPressed(evento, this, tblConvenios,
                                               txtNombreConvenio, 0);
        FarmaUtility.moveFocus(txtNombreConvenio);



    }

    public void buscaPorDescripcion(KeyEvent evento) {
        FarmaGridUtils.aceptarTeclaPresionada(evento, tblConvenios,
                                              txtNombreConvenio, 1);
        jScrollPane1.getViewport().removeAll();

        //Filtramos por Descripcion.
//        UtilityConvenioBTLMF.filtraDescripcion(evento,
//                                               tableModelListaconvenios,
//                                               listaTodo, txtNombreConvenio,
//                                               1);

        UtilityConvenioBTLMF.filtraDescripcion2(evento,
                tableModelListaconvenios,
                listaTodo, txtNombreConvenio,
                1);

        //Compiamos en la tabla de la pantalla.
        FarmaUtility.ordenar(tblConvenios, tableModelListaconvenios, 1,
                             FarmaConstants.ORDEN_ASCENDENTE);
        jScrollPane1.getViewport().add(tblConvenios, null);
        FarmaUtility.moveFocus(txtNombreConvenio);

        System.out.println("tblConvenios :"+tblConvenios.getSelectedRowCount());

        if (tblConvenios.getSelectedRowCount() <= 0)
        {
        	 FarmaUtility.showMessage(this, "No se encontro ningun Convenio ",txtNombreConvenio);

        }
        else
        {
         FarmaGridUtils.showCell(tblConvenios, 0, 0);
         FarmaUtility.setearActualRegistro(tblConvenios, txtNombreConvenio, 1);
        }




    }


    public String completarCerosCodigo(String dato) {

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

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        VariablesConvenioBTLMF.vAceptar = pAceptar;

        this.setVisible(false);
        this.dispose();
    }

    private void txtNombreConvenio_keyPressed(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void txtNombreConvenio_keyReleased(KeyEvent e)
    {

//        if (e.getKeyCode() != KeyEvent.VK_ENTER ||
//            e.getKeyCode() != KeyEvent.VK_ESCAPE)
//        {
//            System.out.println(" txtNombreConvenio_keyReleased: ");
//            FarmaGridUtils.buscarDescripcion(e, tblConvenios,
//                                             txtNombreConvenio, 1);
//        }

        if (e.getKeyCode() != KeyEvent.VK_ENTER && e.getKeyCode() != KeyEvent.VK_LEFT && e.getKeyCode() != KeyEvent.VK_BACK_SPACE
      		  && e.getKeyCode() != KeyEvent.VK_CLEAR)
         {
            String descripcion = "";

      	  int vFila = -1;
     		   if(FarmaGridUtils.buscarDescripcion(e,tblConvenios,txtNombreConvenio,1))
     		   {
  		   		     vFila = tblConvenios.getSelectedRow();
  		          if(vFila > -1)
  		          {
  		              VariablesConvenioBTLMF.listaDatos.clear();
  		              VariablesConvenioBTLMF.listaDatos.add(tableModelListaconvenios.data.get(vFila));
  		              descripcion = FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.listaDatos,0,1);
  		          }
  		        txtNombreConvenio.setText(descripcion);
  		        txtNombreConvenio.select(posicionBarra,descripcion.length());
     		  }
         }

    }

    private void cargaListaConvenios() {
        try {
            DBConvenioBTLMF.listaConvenios(tableModelListaconvenios);

            listaTodo = listaTodo(tableModelListaconvenios);
            FarmaUtility.ordenar(tblConvenios, tableModelListaconvenios, 1,
                                 FarmaConstants.ORDEN_ASCENDENTE);


            if (tblConvenios.getRowCount() <= 0) {
                FarmaUtility.showMessage(this,
                                         "No se encontro ningun Convenio ",
                                         txtNombreConvenio);
                cerrarVentana(false);
            } else {
                FarmaGridUtils.showCell(tblConvenios, 0, 0);
                FarmaUtility.setearActualRegistro(tblConvenios,
                                                  txtNombreConvenio, 1);
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
            FarmaUtility.showMessage(this,
                                     "Ocurio un error al listar los medicos \n " +
                                     sql.getMessage(), txtNombreConvenio);
        }
    }

    private void guardaRegistroConvenio() {

        System.out.println("<<<<<<<<<<<Metodo: guardaRegistroConvenio>>>>>>>>>>>>>");

        int vFila = tblConvenios.getSelectedRow();
        if (vFila > -1) {
            VariablesConvenioBTLMF.vArrayList_ListaConvenio = new ArrayList();
            VariablesConvenioBTLMF.vArrayList_ListaConvenio.add(tableModelListaconvenios.data.get(vFila));
            if (VariablesConvenioBTLMF.vArrayList_ListaConvenio.size() == 1) {
                VariablesConvenioBTLMF.vCodConvenio =
                        FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.vArrayList_ListaConvenio,
                                                            0,
                                                            COLUMN_COD_CONV_);
                VariablesConvenioBTLMF.vNomConvenio =
                        FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.vArrayList_ListaConvenio,
                                                            0,
                                                            COLUMN_DESC_CONV);

                VariablesConvenioBTLMF.vCodConvenioRel =
                        FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.vArrayList_ListaConvenio,
                                                            0,
                                                            COLUMN_RELACIONADO_CONV);

                VariablesConvenioBTLMF.vFlgCreacionCliente =
                        FarmaUtility.getValueFieldArrayList(VariablesConvenioBTLMF.vArrayList_ListaConvenio,
                                                            0,
                                                            COLUMN_FLG_CREA_CLIEN_CONV);


                System.out.println("VariablesConvenio.vCodConvenio : " +
                                   VariablesConvenioBTLMF.vCodConvenio);

                System.out.println("VariablesConvenio.vNomConvenio : " +
                                   VariablesConvenioBTLMF.vNomConvenio);

                System.out.println("VariablesConvenio.vCodConvenioRel : " +
                                   VariablesConvenioBTLMF.vCodConvenioRel);

                System.out.println("VariablesConvenio.vFlgCreacionCliente : " +
                                   VariablesConvenioBTLMF.vFlgCreacionCliente);

                //cargarListaPrecConvenio();

                boolean existeConvenioRelacionado = false;


                /*if(!VariablesConvenioBTLMF.vCodConvenioRel.equalsIgnoreCase("N"))*/
                existeConvenioRelacionado = consultaConvenioRelacionado(VariablesConvenioBTLMF.vCodConvenioRel,this);

                if (existeConvenioRelacionado) {
                    //consulta con que pagara
                	  consultaDePago(myParentFrame);
                } else {
                    muestraMensaje();
                    muestraDatosConvenio();
                }
            }
        }
    }

    public boolean consultaConvenioRelacionado(String pCodConvRel,
                                               JDialog pDialogo) {
        boolean resultado = false;
        Map convRel =
            UtilityConvenioBTLMF.obtenerConvenio(pCodConvRel, pDialogo);
        VariablesConvenioBTLMF.vCodConvenioRel =
                (String)convRel.get(ConstantsConvenioBTLMF.COL_COD_CONVENIO_REL);


        if (VariablesConvenioBTLMF.vCodConvenioRel != null &&
            !VariablesConvenioBTLMF.vCodConvenioRel.trim().equals("")) {
            resultado = true;
            VariablesConvenioBTLMF.vCodConvenioRel =
                    (String)convRel.get(ConstantsConvenioBTLMF.COL_COD_CONVENIO);
            VariablesConvenioBTLMF.vNomConvenioRel =
                    (String)convRel.get(ConstantsConvenioBTLMF.COL_DES_CONVENIO);
            VariablesConvenioBTLMF.vFlgCreacionClienteRel =
                    (String)convRel.get(ConstantsConvenioBTLMF.COL_FLG_CREACION_CLIENTE);
            VariablesConvenioBTLMF.vFlgTipoConvenioRel =
                    (String)convRel.get(ConstantsConvenioBTLMF.COL_FLG_TIPO_CONVENIO);
        }

        System.out.println("vCodConvenioRel=" +
                           VariablesConvenioBTLMF.vCodConvenioRel);
        System.out.println("vNomConvenioRel=" +
                           VariablesConvenioBTLMF.vNomConvenioRel);
        System.out.println("vFlgCreacionClienteRel=" +
                           VariablesConvenioBTLMF.vFlgCreacionClienteRel);
        System.out.println("vFlgTipoConvenioRel=" +
                           VariablesConvenioBTLMF.vFlgTipoConvenioRel);

        return resultado;
    }


    private void consultaDePago(Frame myParentFrame)
    {

        System.out.println("---Mostrando la pantalla de Opcion convenio2-- CONTADO y CREDITO");
        DlgTipoConvenio d =
            new DlgTipoConvenio(myParentFrame, "Opcion Tipo de Convenio",
                                true);
        d.setLocationRelativeTo(myParentFrame);
        d.setVisible(true);

        System.out.println("Aceptar::" + VariablesConvenioBTLMF.vAceptar);
        if (VariablesConvenioBTLMF.vAceptar)
        {
            //actualizar las variables de convenios de acuerdo a la forma de pago Seleccionada
            //NO OLVIDAR..
            //SI PUSO ENTER Si debe de Mostrar Mensajes
            muestraMensaje();
            muestraDatosConvenio();
        }
    }

    private void muestraMensaje() {
        System.out.println("Ejecutamos la opción Enter");
        System.out.println("Muestra la pantalla de Mensajes de convenios");


        ArrayList htmlAbajo = new ArrayList();
        ArrayList htmlDerecho = new ArrayList();
        ArrayList htmlIzquierdo = new ArrayList();


        UtilityConvenioBTLMF.listaMensaje(htmlAbajo,
                                          VariablesConvenioBTLMF.vCodConvenio,
                                          ConstantsConvenioBTLMF.FLG_DOC_SOLUCION,
                                          this, null);
        UtilityConvenioBTLMF.listaMensaje(htmlDerecho,
                                          VariablesConvenioBTLMF.vCodConvenio,
                                          ConstantsConvenioBTLMF.FLG_DOC_VERIFICACION,
                                          this, null);
        UtilityConvenioBTLMF.listaMensaje(htmlIzquierdo,
                                          VariablesConvenioBTLMF.vCodConvenio,
                                          ConstantsConvenioBTLMF.FLG_DOC_RETENCION,
                                          this, null);

        System.out.println("htmlAbajo    : " + htmlAbajo.size());
        System.out.println("htmlDerecho  : " + htmlDerecho.size());
        System.out.println("htmlIzquierdo: " + htmlIzquierdo.size());

        if ((htmlAbajo.size() + htmlDerecho.size() + htmlIzquierdo.size()) !=
            0) {
            DlgMensajeBTLMF d =
                new DlgMensajeBTLMF(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN,
                                    true);
            d.setLocationRelativeTo(myParentFrame);
            d.setVisible(true);
        }

    }

    private void muestraDatosConvenio()
    {
        boolean solicitaDatos =
            UtilityConvenioBTLMF.indDatoConvenio(VariablesConvenioBTLMF.vCodConvenio,
                                                 this, txtNombreConvenio);
        System.out.println("Solicita DATOS >> " + solicitaDatos);

        boolean ingresoDatos = false;
        if (solicitaDatos)
        {
            ingresoDatos = true;
            DlgDatosConvenioBTLMF datos =  new DlgDatosConvenioBTLMF(myParentFrame, "Datos Convenio",true);
            datos.setVisible(true);
            if (VariablesConvenioBTLMF.vAceptar)
            {
            	cargarListaPrecConvenio();
                cerrarVentana(VariablesConvenioBTLMF.vAceptar);

            }
        }
        else
        {
        	if (VariablesConvenioBTLMF.vAceptar)
        	{
        	   cargarListaPrecConvenio();
        	   cerrarVentana(VariablesConvenioBTLMF.vAceptar);
        	}
        }
        System.out.println("Ingreso Datos>> " + ingresoDatos);
    }

    public boolean esCodigo(String dato) {

        boolean retorno = false;

        try {
            Integer.parseInt(dato);
            retorno = true;

        } catch (Exception e) {

            retorno = false;
        }

        return retorno;

    }


    public ArrayList listaTodo(FarmaTableModel tableModelListaconvenios) {
        System.out.println("<<<<<<<<<<<<Metodo: listaTodo  >>>>>>>>>>>>>>>");
        ArrayList lista = new ArrayList();


        String vCodigo = "";
        String vDescrip = "";
        String vConvRelacionado = "";
        String vFlagCreaCliente = "";


        for (int k = 0; k < tableModelListaconvenios.getRowCount(); k++) {


            ArrayList listaTemp = new ArrayList();
            listaTemp.add(tableModelListaconvenios.data.get(k));

            vCodigo = FarmaUtility.getValueFieldArrayList(listaTemp, 0, 0);
            vDescrip = FarmaUtility.getValueFieldArrayList(listaTemp, 0, 1);
            vConvRelacionado =
                    FarmaUtility.getValueFieldArrayList(listaTemp, 0, 2);
            vFlagCreaCliente =
                    FarmaUtility.getValueFieldArrayList(listaTemp, 0, 3);

//            System.out.println("vCodigo:" + vCodigo);
//            System.out.println("vDescripcion:" + vDescrip);
//            System.out.println("vConvRelacionado:" + vConvRelacionado);
//            System.out.println("vFlagCreaCliente:" + vFlagCreaCliente);


            String dato[] = new String[50];
            dato[0] = vCodigo;
            dato[1] = vDescrip;
            dato[2] = vConvRelacionado;
            dato[3] = vFlagCreaCliente;
            lista.add(dato);

        }


        return lista;

    }


    private void irIngresoDatosConvenio(JTextField jtexto) {


        if (UtilityConvenioBTLMF.existeTarjeta(jtexto.getText(), this)) {

            if (VariablesConvenioBTLMF.vCodConvenioAux != null) {
                if (UtilityConvenioBTLMF.existeConvenio(VariablesConvenioBTLMF.vCodConvenioAux,
                                                        this)) {
                    if (VariablesConvenioBTLMF.vCodCliente != null) {
                        if (UtilityConvenioBTLMF.existeCliente(VariablesConvenioBTLMF.vCodCliente,
                                                               this)) {
                            if (VariablesConvenioBTLMF.vDni != null) {
                                boolean existeConvenioRelacionado = false;
                                existeConvenioRelacionado =
                                        consultaConvenioRelacionado(VariablesConvenioBTLMF.vCodConvenioRel,
                                                                    this);
                                if (existeConvenioRelacionado) {
                                    consultaDePago(myParentFrame);
                                } else {
                                    muestraMensaje();
                                    muestraDatosConvenio();
                                }
                            } else {
                                FarmaUtility.showMessage(this,
                                                         "El cliente no tiene asignado el Nro DNI ",
                                                         jtexto);
                                VariablesConvenioBTLMF.vAceptar = false;
                            }
                        } else {
                            FarmaUtility.showMessage(this,
                                                     "El cliente benificiario no existe",
                                                     jtexto);
                            VariablesConvenioBTLMF.vAceptar = false;
                        }
                    } else {
                        FarmaUtility.showMessage(this,
                                                 "La tarjeta no esta asociado a un cliente",
                                                 jtexto);
                        VariablesConvenioBTLMF.vAceptar = false;
                    }
                } else {
                    FarmaUtility.showMessage(this, "No existe el convenio",
                                             jtexto);
                    VariablesConvenioBTLMF.vAceptar = false;
                }
            } else {
                FarmaUtility.showMessage(this,
                                         "La tarjeta no esta asociado a un convenio",
                                         jtexto);
                VariablesConvenioBTLMF.vAceptar = false;
            }

        } else {
            FarmaUtility.showMessage(this, "No existe la tarjeta", jtexto);
            VariablesConvenioBTLMF.vAceptar = false;

        }


    }


    public void irIngresoDatosConvenio2(JTextField jtexto) {
        if (UtilityConvenioBTLMF.existeTarjeta(jtexto.getText(), this)) {

            if (VariablesConvenioBTLMF.vCodConvenioAux != null) {
                if (UtilityConvenioBTLMF.existeConvenio(VariablesConvenioBTLMF.vCodConvenioAux,
                                                        this)) {
                    if (VariablesConvenioBTLMF.vCodCliente != null) {
                        if (UtilityConvenioBTLMF.existeCliente(VariablesConvenioBTLMF.vCodCliente,
                                                               this)) {
                            if (VariablesConvenioBTLMF.vDni != null) {
                                boolean existeConvenioRelacionado = false;
                                existeConvenioRelacionado =
                                        consultaConvenioRelacionado(VariablesConvenioBTLMF.vCodConvenioRel,
                                                                    this);
                                if (existeConvenioRelacionado) {


                                    consultaDePago(myParentFrame);
                                } else {
                                    muestraMensaje();
                                    muestraDatosConvenio();
                                }

                            } else {
                                VariablesConvenioBTLMF.vAceptar = false;
                            }
                        } else {
                            VariablesConvenioBTLMF.vAceptar = false;
                        }
                    } else {
                        VariablesConvenioBTLMF.vAceptar = false;
                    }
                } else {
                    VariablesConvenioBTLMF.vAceptar = false;

                }
            } else {

                VariablesConvenioBTLMF.vAceptar = false;
            }

        } else {
            VariablesConvenioBTLMF.vAceptar = false;

        }


    }


    public void iniciarVariablesGlobales() {
        VariablesConvenioBTLMF.vCodConvenioAux = null;
        VariablesConvenioBTLMF.vCodCliente = null;
        VariablesConvenioBTLMF.vCodConvenio = null;
        VariablesConvenioBTLMF.vNomConvenio = "";
        VariablesConvenioBTLMF.vFlgCreacionCliente = "";
        VariablesConvenioBTLMF.vNombre = "";
        VariablesConvenioBTLMF.vApellidoPat = "";
        VariablesConvenioBTLMF.vDni = "";

        VariablesConvenioBTLMF.vCodConvenioRel = "";
        VariablesConvenioBTLMF.vNomConvenioRel = "";
        VariablesConvenioBTLMF.vFlgCreacionClienteRel = "";
        VariablesConvenioBTLMF.vFlgTipoConvenioRel = "";

    }

    public void limpiarVariablesGlobales() {

    	VariablesConvenioBTLMF.vCodConvenio = "";
    	VariablesConvenioBTLMF.vCodCliente = "";
        VariablesConvenioBTLMF.vNomConvenio = "";
        VariablesConvenioBTLMF.vFlgCreacionCliente = "";
        VariablesConvenioBTLMF.vNombre = "";
        VariablesConvenioBTLMF.vApellidoPat = "";
        VariablesConvenioBTLMF.vDni = "";

        VariablesConvenioBTLMF.vCodConvenioRel = "";
        VariablesConvenioBTLMF.vNomConvenioRel = "";
        VariablesConvenioBTLMF.vFlgCreacionClienteRel = "";
        VariablesConvenioBTLMF.vFlgTipoConvenioRel = "";

    }
    public void cargarListaPrecConvenio() {

        System.out.println("<<<<<<<<Metodo:cargar lista precios convenios>>>>>>>");

        System.out.println("Inicio de Hilo");
        SubProcesosBTLMF subproceso = new SubProcesosBTLMF("CARGA_LISTA_PRECIOS_CONV",myParentFrame,this);
        subproceso.start();
        System.out.println("Fin de Hilo");
    }




}
