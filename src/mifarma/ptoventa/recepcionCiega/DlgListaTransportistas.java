package mifarma.ptoventa.recepcionCiega;

import com.gs.mifarma.componentes.JButtonFunction;
import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JLabelFunction;
import com.gs.mifarma.componentes.JLabelWhite;
import com.gs.mifarma.componentes.JPanelHeader;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;
import com.gs.mifarma.componentes.JTextFieldSanSerif;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.*;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;

import mifarma.common.*;

import mifarma.ptoventa.caja.DlgFormaPago;
import mifarma.ptoventa.recepcionCiega.DlgConteoRecepMercaderia.*;
import mifarma.ptoventa.recepcionCiega.reference.*;
import mifarma.ptoventa.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2009 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgHistoricoRecepcion.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 *   16.11.2009 Creación<br>
 * <br>
 * 
 * @author JORGE CORTEZ ALVAREZ<br>
 * @version 1.0<br>
 * 
 */
public class DlgListaTransportistas extends JDialog {
    private static final Logger log = 
        LoggerFactory.getLogger(DlgListaTransportistas.class);
    private FarmaTableModel tableModelTransp;
    private Frame myParentFrame;

    private GridLayout gridLayout1 = new GridLayout();
    private JPanelWhite jPanelWhite1 = new JPanelWhite();
    private JPanelHeader pnlCriterioBusqueda = new JPanelHeader();
    private JButton btnBuscar = new JButton();
    private JTextFieldSanSerif txtFechaFin = new JTextFieldSanSerif();
    private JTextFieldSanSerif txtFechaIni = new JTextFieldSanSerif();
    private JButtonLabel btnPeriodo = new JButtonLabel();
    private JPanelTitle pnlTitulo = new JPanelTitle();
    private JButtonLabel btnListado = new JButtonLabel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable tblListaTransportista = new JTable();
    private JLabelFunction lblF1 = new JLabelFunction();
    private JLabelFunction jLabelFunction3 = new JLabelFunction();
    private JLabelFunction lblF3 = new JLabelFunction();
    private JPanelTitle pnlResultados = new JPanelTitle();
    private JLabel lblRegistros1 = new JLabel();
    private JLabel lblRegistros = new JLabel();
    private JLabel lblRegsitros_T = new JLabel();
    private JLabelWhite jLabelWhite1 = new JLabelWhite();
    private JLabelFunction lblF5 = new JLabelFunction();
    private JLabelFunction lblF8 = new JLabelFunction();
    private JLabelFunction lblF2 = new JLabelFunction();
    
    /*   17.03.2010 CONSTANTES DE COLUMNAS */
    private static final int COL_NRO_RECEP = 0;
    private static final int COL_FECHA = 1;
    private static final int COL_USU_CREA = 2;
    private static final int COL_NOM_TRANSP = 3;
    private static final int COL_PLACA = 4;
    private static final int COL_ESTADO = 5;
    private static final int COL_CANT_GUIAS = 6;
    private static final int COL_COD_ESTADO = 7;
    private static final int COL_ORDENAR = 8;
    
    private JLabelFunction jLabelFunction1 = 
        new JLabelFunction(); //FILTRO PARA ORDENAR    

    public DlgListaTransportistas() {
        this(null, "", false);
    }

    public DlgListaTransportistas(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
        try {
            jbInit();
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(774, 413));
        this.getContentPane().setLayout(gridLayout1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Listado de Transportistas");
        this.addWindowListener(new WindowAdapter() {
                    public void windowOpened(WindowEvent e) {
                        this_windowOpened(e);
                    }

                    public void windowClosing(WindowEvent e) {
                        this_windowClosing(e);
                    }
                });
        pnlCriterioBusqueda.setBounds(new Rectangle(5, 5, 755, 40));
        btnBuscar.setText("Buscar");
        btnBuscar.setBounds(new Rectangle(410, 10, 95, 20));
        btnBuscar.setMnemonic('b');
        btnBuscar.setFont(new Font("SansSerif", 1, 11));
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnBuscar_actionPerformed(e);
                    }
                });
        txtFechaFin.setBounds(new Rectangle(295, 10, 101, 19));
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
        txtFechaIni.setBounds(new Rectangle(115, 10, 101, 19));
        txtFechaIni.setLengthText(10);
        txtFechaIni.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        txtFechaIni_keyPressed(e);
                    }

                    public void keyReleased(KeyEvent e) {
                        txtFechaIni_keyReleased(e);
                    }

                    public void keyTyped(KeyEvent e) {
                        txtFechaIni_keyTyped(e);
                    }
                });
        btnPeriodo.setText("Desde :");
        btnPeriodo.setBounds(new Rectangle(55, 10, 55, 20));
        btnPeriodo.setMnemonic('D');
        btnPeriodo.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnPeriodo_actionPerformed(e);
                    }
                });
        pnlTitulo.setBounds(new Rectangle(5, 50, 755, 20));
        btnListado.setText("Lista de Transportistas :");
        btnListado.setBounds(new Rectangle(10, 0, 200, 20));
        btnListado.setMnemonic('l');
        btnListado.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        btnListado_actionPerformed(e);
                    }
                });
        jScrollPane1.setBounds(new Rectangle(5, 70, 755, 265));
        tblListaTransportista.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        tblListaTransportista_keyPressed(e);
                    }
                });
        lblF1.setBounds(new Rectangle(5, 360, 165, 20));
        lblF1.setText("[ F1 ] Ingresar Transportista");
        //lblF1.setVisible(false);
        jLabelFunction3.setBounds(new Rectangle(675, 360, 85, 20));
        jLabelFunction3.setText("[ ESC ] Salir");
        lblF3.setBounds(new Rectangle(505, 360, 165, 20));
        lblF3.setText("[ F3 ] Asociar Transportista");
        //lblF3.setVisible(false);
        pnlResultados.setBounds(new Rectangle(5, 335, 755, 20));
        lblRegistros1.setText("0");
        lblRegistros1.setBounds(new Rectangle(-100, 0, 40, 20));
        lblRegistros1.setFont(new Font("SansSerif", 1, 11));
        lblRegistros1.setForeground(Color.white);
        lblRegistros1.setHorizontalAlignment(SwingConstants.RIGHT);
        lblRegistros.setText("0");
        lblRegistros.setBounds(new Rectangle(60, 0, 35, 20));
        lblRegistros.setFont(new Font("SansSerif", 1, 11));
        lblRegistros.setForeground(Color.white);
        lblRegistros.setHorizontalAlignment(SwingConstants.RIGHT);
        lblRegsitros_T.setText("Registros :");
        lblRegsitros_T.setBounds(new Rectangle(5, 0, 70, 20));
        lblRegsitros_T.setFont(new Font("SansSerif", 1, 11));
        lblRegsitros_T.setForeground(Color.white);
        jLabelWhite1.setText("Hasta :");
        jLabelWhite1.setBounds(new Rectangle(240, 10, 50, 20));

        lblF2.setBounds(new Rectangle(175, 360, 95, 20));
        lblF2.setText("[ F2 ] Imprimir");
        
        pnlResultados.add(lblRegistros1, null);
        pnlResultados.add(lblRegistros, null);
        pnlResultados.add(lblRegsitros_T, null);
        pnlCriterioBusqueda.add(jLabelWhite1, null);
        pnlCriterioBusqueda.add(btnBuscar, null);
        pnlCriterioBusqueda.add(txtFechaFin, null);
        pnlCriterioBusqueda.add(txtFechaIni, null);
        pnlCriterioBusqueda.add(btnPeriodo, null);
        
        jPanelWhite1.add(lblF2, null);
        jPanelWhite1.add(pnlResultados, null);
        jPanelWhite1.add(lblF3, null);
        jPanelWhite1.add(jLabelFunction3, null);
        jPanelWhite1.add(lblF1, null);
        jScrollPane1.getViewport().add(tblListaTransportista, null);
        jPanelWhite1.add(jScrollPane1, null);
        pnlTitulo.add(btnListado, null);
        jPanelWhite1.add(pnlTitulo, null);
        jPanelWhite1.add(pnlCriterioBusqueda, null);
        this.getContentPane().add(jPanelWhite1, null);
    }


    private void initialize() {
        initTableListaTransp();
        limpiarVariables();
        cargaListaTransp();       
    }

    private void initTableListaTransp() {
        tableModelTransp = 
                new FarmaTableModel(ConstantsRecepCiega.columnsListaTransp, 
                                    ConstantsRecepCiega.defaultValuesListaTrans, 
                                    0);
        FarmaUtility.initSimpleList(tblListaTransportista, tableModelTransp, 
                                    ConstantsRecepCiega.columnsListaTransp);
    }

    private void busqueda() {
        if (validarCampos()) {
            txtFechaIni.setText(txtFechaIni.getText().trim().toUpperCase());
            txtFechaFin.setText(txtFechaFin.getText().trim().toUpperCase());
            String FechaInicio = txtFechaIni.getText().trim();
            String FechaFin = txtFechaFin.getText().trim();
            if (FechaInicio.length() > 0 || FechaFin.length() > 0) {
                char primerkeyCharFI = FechaInicio.charAt(0);
                char ultimokeyCharFI = 
                    FechaInicio.charAt(FechaInicio.length() - 1);
                char primerkeyCharFF = FechaFin.charAt(0);
                char ultimokeyCharFF = FechaFin.charAt(FechaFin.length() - 1);

                if (!Character.isLetter(primerkeyCharFI) && 
                    !Character.isLetter(ultimokeyCharFI) && 
                    !Character.isLetter(primerkeyCharFF) && 
                    !Character.isLetter(ultimokeyCharFF)) {
                    buscaRegistroRecepcion(FechaInicio, FechaFin);
                } else
                    FarmaUtility.showMessage(this, 
                                             "Ingrese un formato valido de fechas", 
                                             txtFechaIni);
            } else
                FarmaUtility.showMessage(this, 
                                         "Ingrese datos para la busqueda", 
                                         txtFechaIni);

        }
    }

    private void limpiarVariables() {

        VariablesRecepCiega.vNombreTrans = "";
        VariablesRecepCiega.vHoraTrans = "";
        VariablesRecepCiega.vPlacaUnidTrans = "";
        VariablesRecepCiega.vCantBultos = "";
        VariablesRecepCiega.vCantPrecintos = "";

        VariablesRecepCiega.vNumIngreso = "";
        VariablesRecepCiega.vNumNotaEst = "";
        VariablesRecepCiega.vNumGuia = "";
        VariablesRecepCiega.vFecCreaNota = "";
        VariablesRecepCiega.vCantItems = "";
        VariablesRecepCiega.vCantProds = "";
        VariablesRecepCiega.vEstado = "";
    }

    private void buscaRegistroRecepcion(String pFechaInicio, 
                                        String pFechaFin) {
        cargaListaTransp(pFechaInicio, pFechaFin);
    }

    /**
     * Valida los campos ingresados()Cod,fecini,fecfin)
     */
    private boolean validarCampos() {

        boolean retorno = true, flag1 = true, flag2 = true;
        String fechaini = txtFechaIni.getText().trim();
        String fechafin = txtFechaFin.getText().trim();
        try {

            if (!UtilityRecepCiega.validarFecha(txtFechaIni.getText().trim()) || 
                !validaFecha(txtFechaIni.getText().trim(), "")) {
                flag1 = false;
                retorno = false;
                FarmaUtility.showMessage(this, 
                                         "Formato de fecha inicial inválido", 
                                         txtFechaIni);
                return retorno;
            } else if (!UtilityRecepCiega.validarFecha(txtFechaFin.getText().trim()) || 
                       !validaFecha(txtFechaFin.getText().trim(), "")) {
                flag1 = false;
                retorno = false;
                FarmaUtility.showMessage(this, 
                                         "Formato de fecha final inválido", 
                                         txtFechaFin);
                return retorno;
            } else if (!flag1 || !flag2) {
                retorno = false;
                FarmaUtility.showMessage(this, "Error", txtFechaIni);
            } else if (flag1 && flag2) {
                if (!FarmaUtility.valida_fecha_Inicial_Final(txtFechaIni.getText().trim(), 
                                                             txtFechaFin.getText().trim())) {
                    retorno = false;
                    FarmaUtility.showMessage(this, 
                                             "La fecha inicial es mayor a la fecha final", 
                                             txtFechaIni);
                    FarmaUtility.moveFocus(txtFechaIni);
                }
            }
        } catch (Exception e) {
            retorno = false;
            e.printStackTrace();
        }
        return retorno;
    }

    private void cargaListaTransp(String FechIni, String FechFin) {
        try {
            System.out.println(FechIni);
            System.out.println(FechFin);
            DBRecepCiega.getListaTranspFecha(tableModelTransp, 
                                                          FechIni, FechFin);
            
            //**
            FarmaUtility.ordenar(tblListaTransportista,tableModelTransp,COL_ORDENAR,FarmaConstants.ORDEN_DESCENDENTE);
            ArrayList rowsWithOtherColor = new ArrayList();
            for(int i = 0; i < tableModelTransp.data.size(); i++){
                if ( ((ArrayList)tableModelTransp.data.get(i)).get(COL_CANT_GUIAS).toString().trim().equalsIgnoreCase("0") )
                { //cantguias 8 es 0
                  rowsWithOtherColor.add(String.valueOf(i));
                }
            }
            FarmaUtility.initSimpleListCleanColumns(tblListaTransportista, tableModelTransp, 
                ConstantsRecepCiega.columnsListaTransp,rowsWithOtherColor,Color.white,Color.red,false);
            //**
            FarmaUtility.moveFocus(tblListaTransportista);
            lblRegistros.setText("" + tblListaTransportista.getRowCount());
            setJTable(tblListaTransportista, txtFechaIni);
        } catch (SQLException sql) {
            sql.printStackTrace();
            FarmaUtility.showMessage(this, "Error al listar Recepciones : \n" +
                    sql.getMessage(), null);
            //cerrarVentana(false);
        }
    }


    private void cargaListaTransp() {
        /**** */
        try {
            DBRecepCiega.getListaTransp(tableModelTransp);
            //**
            FarmaUtility.ordenar(tblListaTransportista,tableModelTransp,COL_ORDENAR,
                                 FarmaConstants.ORDEN_DESCENDENTE);
            ArrayList rowsWithOtherColor = new ArrayList();
            for(int i = 0; i < tableModelTransp.data.size(); i++){
                if ( ((ArrayList)tableModelTransp.data.get(i)).get(COL_CANT_GUIAS).toString().trim().equalsIgnoreCase("0") )
                {
                  rowsWithOtherColor.add(String.valueOf(i));
                }
            }
            FarmaUtility.initSimpleListCleanColumns(tblListaTransportista, tableModelTransp, 
                ConstantsRecepCiega.columnsListaTransp,rowsWithOtherColor,Color.white,Color.red,false); 
            //**
            //FarmaUtility.moveFocus(tblRecepcion);                        
            FarmaUtility.moveFocus(txtFechaIni);
            lblRegistros.setText("" + tblListaTransportista.getRowCount());
            setJTable(tblListaTransportista, txtFechaIni);
            //permite que no se muevan las columnas de Jtable
            tblListaTransportista.getTableHeader().setReorderingAllowed(false);
            //permite que no se cambien el tamaño de la columna del Jtable
            tblListaTransportista.getTableHeader().setResizingAllowed(false);            

        } catch (SQLException sql) {
            sql.printStackTrace();
            FarmaUtility.showMessage(this, 
                                     "Error al listar Recepciones del dia : \n" +
                    sql.getMessage(), null);
        }
    }

    private void cerrarVentana(boolean pAceptar) {
        FarmaVariables.vAceptar = pAceptar;
        this.setVisible(false);
        this.dispose();
    }

    private void txtFechaIni_keyPressed(KeyEvent e) {
        //  - 03.12.2009
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaTransportista, null, 0);

        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            FarmaUtility.moveFocus(txtFechaFin);
        //  02.12.09
        /*else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP){
        FarmaUtility.moveFocus(tblRecepcion);
    }*/
        chkKeyPressed(e);
    }

    private void txtFechaIni_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaIni, e);
    }

    private void txtFechaFin_keyPressed(KeyEvent e) {
        //  - 03.12.2009
        FarmaGridUtils.aceptarTeclaPresionada(e, tblListaTransportista, null, 0);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!VariablesRecepCiega.vIndAsociaTransportista){
                btnBuscar.doClick();
            }
            else{
                FarmaUtility.showMessage(this,"No puede realizar una búsqueda cuando esta asociando Entregas a Transportista.",txtFechaIni);
            }
        }
        //  02.12.09
        /*
   else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP){
          FarmaUtility.moveFocus(tblRecepcion);
   }
   */
        chkKeyPressed(e);
    }

    private void txtFechaFin_keyReleased(KeyEvent e) {
        FarmaUtility.dateComplete(txtFechaFin, e);
    }

    private void this_windowOpened(WindowEvent e) {
        FarmaUtility.centrarVentana(this);
        FarmaUtility.moveFocus(txtFechaIni);
        //lbllocal.setText(FarmaVariables.vDescLocal);
        // cargaLogin();
        //  02.12.09 VISUALIZAR BOTONES
        //visualizarBotones();
        verBotones();
   
    }

    private void btnBuscar_actionPerformed(ActionEvent e) {
        if (tblListaTransportista.getRowCount() == 0 || 
            tblListaTransportista.getRowCount() > 0) {
            busqueda();
        }
    }

    private void tblVentasPorDIa_keyReleased(KeyEvent e) {
        chkKeyPressed(e);
    }

    private void chkKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F1) { 
            if(lblF1.isVisible()){    
                nuevoIngreso();    
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F2) {
            if(lblF2.isVisible()){
                funcionF2();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            if(lblF3.isVisible()){                
                funcionF3(); 
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F8) {
           

        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            VariablesRecepCiega.vIndAsociaTransportista = false;
            cerrarVentana(false);
        }
    }

    private void this_windowClosing(WindowEvent e) {
        FarmaUtility.showMessage(this, 
                                 "Debe presionar la tecla ESC para cerrar la ventana.", 
                                 null);
    }

    private void btnPeriodo_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(txtFechaIni);
    }


    private void btnListado_actionPerformed(ActionEvent e) {
        FarmaUtility.moveFocus(tblListaTransportista);
    }

    private void tblListaTransportista_keyPressed(KeyEvent e) {

        //  02.12.09
        if (e.getKeyCode() == KeyEvent.VK_LEFT || 
            e.getKeyCode() == KeyEvent.VK_RIGHT) {
            FarmaUtility.moveFocus(txtFechaIni);
        }
        chkKeyPressed(e);
    }

    private void txtFechaIni_keyTyped(KeyEvent e) {

        FarmaUtility.admitirDigitos(txtFechaIni, e);
    }

    private void txtFechaFin_keyTyped(KeyEvent e) {

        FarmaUtility.admitirDigitos(txtFechaFin, e);
    }


    private void verConteo(String pEstado) {
        VariablesRecepCiega.vNro_Recepcion = VariablesRecepCiega.vNumIngreso;
        String pIndSegundoConteo = "";
        if (obtieneIndicadorAcceso().equalsIgnoreCase("C")) {


            // if (obtenerIndSegConteo().equalsIgnoreCase("N")) {
            //Se bloquea el registro
            try {
                UtilityRecepCiega.pBloqueoRecepcion(VariablesRecepCiega.vNro_Recepcion.trim());
                System.err.println("Bloquea.");
                pEstado = 
                        UtilityRecepCiega.pEstadoRecepcion(VariablesRecepCiega.vNro_Recepcion.trim());

                System.err.println("pEstado :" + pEstado);

                if (pEstado.trim().equalsIgnoreCase(ConstantsRecepCiega.EstadoRevisado)) {

                    if (
                        UtilityRecepCiega.updateEstadoRecep(ConstantsRecepCiega.EstadoVerificacion, 
                                                            VariablesRecepCiega.vNro_Recepcion.trim(),
                                                            this,
                                                            txtFechaIni)) {
                        FarmaUtility.aceptarTransaccion();
                        System.err.println("Se quito bloqueo y actualizo Estado");
    
                        DlgVerificacionConteo dlgVerifica = 
                            new DlgVerificacionConteo(myParentFrame, "", true);
                        dlgVerifica.setVisible(true);
                    }
                    else {
                        FarmaUtility.showMessage(this,"No se pudo modificar el estado en la Recepción.\n" + 
                                                                                                      "Vuelva a Intentarlo.\n",txtFechaIni);
                    }

                } else {
                    FarmaUtility.liberarTransaccion();
                    System.err.println("Para liberar el bloqueo...");

                    if (pEstado.trim().equalsIgnoreCase(ConstantsRecepCiega.EstadoVerificacion)) {
                        FarmaUtility.showMessage(this, 
                                                 "No puede ingresar a esta Opción.\n" +
                                "Ya existe un usuario que esta verificando.\n" +
                                "Sólo puede ingresar uno a la vez.\nGracias.", 
                                null);
                    } else { //COLOCAR MENSAJES
                        /*
                             * FarmaUtility.showMessage(this,
                                                     "La recepción ya fue verificada.",
                                                     null);
                            */
                        if (pEstado.trim().equalsIgnoreCase(ConstantsRecepCiega.EstadoAfectadoTotal) || 
                            pEstado.trim().equalsIgnoreCase(ConstantsRecepCiega.EstadoAfectadoParcial)) {
                            DlgDiferenciasFinales dlgDifFinal = 
                                new DlgDiferenciasFinales(myParentFrame, "", 
                                                          true);
                            dlgDifFinal.setVisible(true);
                        } else {
                            FarmaUtility.showMessage(this, 
                                                     "La recepción ya fue verificada.", 
                                                     null);
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                FarmaUtility.liberarTransaccion();
                FarmaUtility.showMessage(this, 
                                         "Ocurrió un error al ingresar a Verificación de Conteo.\n" +
                        e.getMessage(), null);
            } finally {
                FarmaUtility.liberarTransaccion();
                System.err.println("Para liberar el bloqueo...");
            }
            /*
            }
            else {
                FarmaUtility.showMessage(this,
                                        "No puede ingresar a esta Opción.\n"+
                                        "Ya existe un usuario que esta verificando.\n"+
                                        "Sólo puede ingresar uno a la vez.\nGracias.",
                                         null);


            }
            */


        } else if (obtieneIndicadorAcceso().equalsIgnoreCase("V")) {
            DlgDiferenciasFinales dlgDifFinal = 
                new DlgDiferenciasFinales(myParentFrame, "", true);
            dlgDifFinal.setVisible(true);
            if (FarmaVariables.vAceptar) {
                cargaListaTransp();
            }
        } else
            FarmaUtility.showMessage(this, 
                                     "Ud. no tiene el rol necesario para acceder a esta opción \n", 
                                     null);
        FarmaVariables.vAceptar = false;

    }

    private void verConteoRecepcion() {
        DlgConteoRecepMercaderia dlgConteo = 
            new DlgConteoRecepMercaderia(myParentFrame, "", true);
        dlgConteo.setVisible(true);
        if (FarmaVariables.vAceptar) {
            cargaListaTransp();
        }
    }

    private void verEntrega(int vFila) {
        System.out.println("VER INGRESO");

        VariablesRecepCiega.vTran_NroRecepcion = 
            FarmaUtility.getValueFieldArrayList(tableModelTransp.data,vFila,COL_NRO_RECEP);                
        //**
        VariablesRecepCiega.vTran_Fecha = 
            FarmaUtility.getValueFieldArrayList(tableModelTransp.data,vFila,COL_FECHA);
        
        VariablesRecepCiega.vTran_UsuCrea = 
            FarmaUtility.getValueFieldArrayList(tableModelTransp.data,vFila,COL_USU_CREA);
        
        VariablesRecepCiega.vTran_NomTransportista = 
            FarmaUtility.getValueFieldArrayList(tableModelTransp.data,vFila,COL_NOM_TRANSP);
        
        VariablesRecepCiega.vTran_Placa = 
            FarmaUtility.getValueFieldArrayList(tableModelTransp.data,vFila,COL_PLACA);
        
        VariablesRecepCiega.vTran_CodEstado = 
            FarmaUtility.getValueFieldArrayList(tableModelTransp.data,vFila,COL_COD_ESTADO);
        
        VariablesRecepCiega.vTran_Ordena = 
            FarmaUtility.getValueFieldArrayList(tableModelTransp.data,vFila,COL_ORDENAR);
        
        System.out.println("vTran_NroRecepcion :" + 
                           VariablesRecepCiega.vTran_NroRecepcion);
        System.out.println("vTran_Fecha :" + 
                           VariablesRecepCiega.vTran_Fecha);
        System.out.println("vTran_UsuCrea :" + 
                           VariablesRecepCiega.vTran_UsuCrea);
        System.out.println("vTran_NomTransportista :" + 
                           VariablesRecepCiega.vTran_NomTransportista);
        System.out.println("vTran_Placa :" + 
                           VariablesRecepCiega.vTran_Placa);
        System.out.println("vTran_CodEstado :" + 
                           VariablesRecepCiega.vTran_CodEstado);

        if (FarmaVariables.vAceptar) {
        }


    }

    private void nuevoIngreso() {
        DlgIngresoTransportista_02 dlgDatos = 
            new DlgIngresoTransportista_02(myParentFrame, "", true);
        dlgDatos.setVisible(true);
        if (FarmaVariables.vAceptar) {
            cargaListaTransp();
        }
    }

    private boolean validaIngreso() {
        String ind = "";
        boolean valor = false;
        try {
            ind = DBRecepCiega.permiteIngreso();
            if (ind.trim().equalsIgnoreCase("S"))
                valor = true;
        } catch (SQLException sql) {
            sql.printStackTrace();
            FarmaUtility.showMessage(this, 
                                     "Ocurrio un error al validar IP.\n" +
                    sql.getMessage(), txtFechaIni);
        }

        return valor;

    }

    private String obtieneIndicadorAcceso() {
        String result = "";
        System.out.println("FarmaVariables.vNuSecUsu : " + 
                           FarmaVariables.vNuSecUsu);

        try {
            result = 
                    DBRecepCiega.verificaRolUsuario(FarmaVariables.vNuSecUsu, FarmaConstants.ROL_ADMLOCAL);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            FarmaUtility.showMessage(this, 
                                     "Ha ocurrido un error al validar el rol de usuario .\n" +
                    e.getMessage(), null);
            result = "N";
            return result;
        }


    }

    private String validaIPParaVerificarConteo() {
        String vIP = "";
        try {
            vIP = DBRecepCiega.verificaIPVeriricarConteo();
            return vIP;
        } catch (SQLException sql) {
            sql.printStackTrace();
            FarmaUtility.showMessage(this, 
                                     "Ocurrió un error al validar el IP del usuario. \n", 
                                     null);
            vIP = "N";
            return vIP;
        }
    }


    /**
     * Se valida rol para mostrar opciones
     * @AUTHOR  
     * @SINCE 27.11.2009
     */
    private boolean validaRolUsu(String Rol) {
        String result = "";
        System.out.println("FarmaVariables.vNuSecUsu : " + 
                           FarmaVariables.vNuSecUsu);

        try {
            result = 
                    DBRecepCiega.verificaRolUsuarioRecep(FarmaVariables.vNuSecUsu, 
                                                         Rol);
            System.out.println("result : " + result);
            if (result.trim().equalsIgnoreCase("S"))
                return true;
            else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
            FarmaUtility.showMessage(this, 
                                     "Ha ocurrido un error al validar el rol de usuario .\n" +
                    e.getMessage(), null);
            result = "N";
            return false;
        }
    }

    //  01.12.09

    private void validaRol() {
        //se guarda datos
        VariablesRecepCiega.vNuSecUsu = FarmaVariables.vNuSecUsu;
        VariablesRecepCiega.vIdUsu = FarmaVariables.vIdUsu;
        VariablesRecepCiega.vNomUsu = FarmaVariables.vNomUsu;
        VariablesRecepCiega.vPatUsu = FarmaVariables.vPatUsu;
        VariablesRecepCiega.vMatUsu = FarmaVariables.vMatUsu;

        DlgLogin dlgLogin = 
            new DlgLogin(myParentFrame, ConstantsPtoVenta.MENSAJE_LOGIN, true);
        //dlgLogin.setRolUsuario(FarmaConstants.ROL_ADMLOCAL);
        dlgLogin.setVisible(true);

        if (FarmaVariables.vAceptar) {

            if (!FarmaVariables.vNuSecUsu.equalsIgnoreCase(VariablesRecepCiega.vNuSecUsu)) {
                FarmaUtility.showMessage(this, 
                                         "El usuario no es el mismo al logueado anteriormente. ¡Verificar!", 
                                         tblListaTransportista);
                FarmaVariables.vAceptar = false;
            } else {
                //  04.12.09
                if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_VENDEDOR)) {
                    FarmaVariables.vAceptar = true;
                } else if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_ADMLOCAL)) {
                    FarmaVariables.vAceptar = true;
                } else {
                    FarmaVariables.vAceptar = false;
                    FarmaUtility.showMessage(this, 
                                             "El usuario no tiene permiso. ¡Verificar!", 
                                             tblListaTransportista);
                }
            }
            // se sete datos originales
            FarmaVariables.vNuSecUsu = VariablesRecepCiega.vNuSecUsu;
            FarmaVariables.vIdUsu = VariablesRecepCiega.vIdUsu;
            FarmaVariables.vNomUsu = VariablesRecepCiega.vNomUsu;
            FarmaVariables.vPatUsu = VariablesRecepCiega.vPatUsu;
            FarmaVariables.vMatUsu = VariablesRecepCiega.vMatUsu;

        }
        /*
          *   - 04.12.2009
          * else
          cerrarVentana(false);
            */
    }

    //  01.12.09

    private void verificaRolUsuario() {
        if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_VENDEDOR)) {
            FarmaVariables.vAceptar = true;
        } else if (FarmaVariables.dlgLogin.verificaRol(FarmaConstants.ROL_ADMLOCAL)) {
            FarmaVariables.vAceptar = true;
        } else {
            //no debe ingresar
            FarmaVariables.vAceptar = false;
            FarmaUtility.showMessage(this, 
                                     "Ud. no tiene Permiso para ingresar a contar." + 
                                     "\nIngrese Usuario Correcto.", 
                                     tblListaTransportista);
            return;
        }
    }

    private String obtenerIndSegConteo() {
        String resultado = "";
        try {
            resultado = DBRecepCiega.obtenerSegConteo();
        } catch (SQLException e) {
            resultado = "";
            e.printStackTrace();
            FarmaUtility.showMessage(this, 
                                     "Ha ocurrido un error al obtener el indicador de verificación de conteo .\n" +
                    e.getMessage(), null);
        }
        return resultado;
    }

    private void visualizarBotones() {
        lblF1.setVisible(false);
        lblF2.setVisible(true);
        lblF3.setVisible(false);      
        
        if (validaRolUsu(FarmaConstants.ROL_ADMLOCAL)) {
            lblF1.setVisible(true);
           // lblF3.setVisible(true);      
            
        } else if (validaRolUsu(FarmaConstants.ROL_VENDEDOR)) {
            
        } else if (validaRolUsu(FarmaConstants.ROL_OPERADOR_SISTEMAS)) {
            lblF1.setVisible(true);
           // lblF3.setVisible(true);
        }

    }

    private void setJTable(JTable pJTable, JTextFieldSanSerif pText) {
        if (pJTable.getRowCount() > 0) {
            FarmaGridUtils.showCell(pJTable, 0, 0);
            FarmaUtility.setearActualRegistro(pJTable, null, 0);
        }
        FarmaUtility.moveFocus(pText);
    }

    public void actualizaListado() {

        if ((txtFechaIni.getText().trim().length() == 10 && 
             txtFechaFin.getText().trim().length() == 10)) {
            if (validarCampos()) {
                buscaRegistroRecepcion(txtFechaIni.getText().trim(), 
                                       txtFechaFin.getText().trim());
            }
        } else {
            cargaListaTransp();
        }

        visualizarBotones();
    }
 
    //  VALIDA FECHA

    private boolean validaFecha(String pFecha, String pHora) {
        //pFecha.trim().equalsIgnoreCase("");
        boolean flag = false;
        Date fec = null;
        if (pHora.trim().equalsIgnoreCase("")) {
            pHora = "00:00:00";
        }
        try {
            if (FarmaUtility.isFechaValida(pFecha)) {
                fec = FarmaUtility.obtiene_fecha(pFecha, pHora);
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    private void funcionF2(){        
        if(tblListaTransportista.getRowCount()== -1){
            FarmaUtility.showMessage(this,"No Existen Registros.",txtFechaIni);
            return;        
        }
        else{
            int vFila = tblListaTransportista.getSelectedRow();
            System.out.println("Fila: "+vFila);
            verEntrega(vFila);            
            //UtilityRecepCiega.imprimeVoucherTransportista(this,VariablesRecepCiega.vTran_NroRecepcion.trim(),txtFechaIni);
            UtilityRecepCiega.imprimeVoucherTransportista_02(this,VariablesRecepCiega.vTran_NroRecepcion.trim(),txtFechaIni);
        }
           
    }
    
    private void funcionF3(){        
        if(tblListaTransportista.getRowCount()== -1){
            FarmaUtility.showMessage(this,"No Existen Registros.",txtFechaIni);
            return;        
        }
        else{
            if (FarmaUtility.rptaConfirmDialog(this,"¿Está seguro de Asociar la Entrega a este Transportista?")) {
                int vFila = tblListaTransportista.getSelectedRow();
                System.out.println("Fila: "+vFila);
                verEntrega(vFila);             
                VariablesRecepCiega.vIndAsociaTransportista = false;
                cerrarVentana(true);
            }
            else {
                return;
            }
        }
    }
    
    private void verBotones(){
        if(VariablesRecepCiega.vIndAsociaTransportista){
            lblF3.setVisible(true);
            lblF1.setVisible(false);
            lblF2.setVisible(false);
        }
        else{
            lblF3.setVisible(false);
            lblF1.setVisible(true);
            lblF2.setVisible(true);   
            }
    }
}
