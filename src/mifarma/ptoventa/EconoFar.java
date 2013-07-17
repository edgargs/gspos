package mifarma.ptoventa;

import com.gs.encripta.FarmaEncripta;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

import javax.swing.JFrame;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.reference.ConstantsPtoVenta;


/**
 * Copyright (c) 2005 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : EconoFar.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      27.12.2005   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class EconoFar {

    static JFrame myparent = new JFrame();

    String prop1, prop2, prop3;

    /**
     * Frame principal de la Aplicación
     */
    public EconoFar() {
        if (readFileProperties() && readFilePasswordProperties() &&
            //añadido dubilluz
            readFileServRemotosProperties()) {
            Frame frame = new FrmEconoFar();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension frameSize = frame.getSize();
            if (frameSize.height > screenSize.height) {
                frameSize.height = screenSize.height;
            }
            if (frameSize.width > screenSize.width) {
                frameSize.width = screenSize.width;
            }
            frame.setLocation((screenSize.width - frameSize.width) / 2, 
                              (screenSize.height - frameSize.height) / 2);
            frame.addWindowListener(new WindowAdapter() {
                        public void windowClosing(WindowEvent e) {
                            System.exit(0);
                        }
                    });
            frame.setVisible(true);
        }
        try {
            jbInit();
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //20.12.2007 ERIOS Se modifica el metodo para cargar desde el jar.
        if (args.length == 3) {
            System.out.println(args[0]);
            System.out.println(args[1]);
            System.out.println(args[2]);
            new EconoFar(args[0], args[1], args[2]);
        } else if (args.length == 
                   2) /* 25.01.2008 ERIOS Ejecuta Ptoventa_Matriz */
        {
            new EconoFar_Matriz(args[0], args[1]);
        } else {
            new EconoFar();
        }
    }

    /**
     * Realiza la lectura del archivo Properties para determinar el seteo de
     * variables
     */
    private boolean readFileProperties() {
        boolean propertiesServidorCorrecto = false;
        boolean propertiesClienteCorrecto = true;
        try {
            InputStream fis = null;
            Properties properties = null;
            File archivo = null;
            // LEE PROPERTIES DEL SERVIDOR
            fis = 
this.getClass().getResourceAsStream("/PtoVentaServ.properties");
            //20.12.2007 ERIOS Se lee la ruta del parametro
            if (fis == null) {
                System.out.println("No leyo archivo");
                //archivo = new File("AdmCentralServ.properties");
                archivo = new File(prop1);
                fis = new FileInputStream(archivo);
                System.out.println(archivo);
            }
            if (fis != null) {
                properties = new Properties();
                properties.load(fis);
                FarmaVariables.vCodGrupoCia = 
                        properties.getProperty("CodigoGrupoCompania");
                //System.out.println("FarmaVariables.vCodGrupoCia=" + FarmaVariables.vCodGrupoCia);
                FarmaVariables.vCodCia = 
                        properties.getProperty("CodigoCompania");
                FarmaVariables.vCodLocal = 
                        properties.getProperty("CodigoLocal");
                //System.out.println("FarmaVariables.vCodLocal=" + FarmaVariables.vCodLocal);
                FarmaVariables.vImprReporte = 
                        properties.getProperty("ImpresoraReporte");
                //System.out.println("FarmaVariables.vCodLocal=" + FarmaVariables.vCodLocal);
                FarmaVariables.vIPBD = properties.getProperty("IpServidor");

                /**
         * SE COMENTO PARA CARGAR EL SID Y USER DE PROPERTI ID
         * @AUTHOR DUBILLUZ
         * @SINCE  13.10.2007
         */
                /* FarmaVariables.vSID = properties.getProperty("SID");
          //System.out.println("FarmaVariables.vSID=" + FarmaVariables.vSID);
        if(FarmaVariables.vSID == null)
          FarmaVariables.vSID = ConstantsPtoVenta.SID;

        //FarmaVariables.vUsuarioBD = FarmaEncripta.desencripta(properties.getProperty("UsuarioBD"));
        FarmaVariables.vUsuarioBD = properties.getProperty("UsuarioBD");
          //System.out.println("FarmaVariables.vUsuarioBD=" + FarmaVariables.vUsuarioBD);
        if(FarmaVariables.vUsuarioBD == null)
          FarmaVariables.vUsuarioBD = ConstantsPtoVenta.USUARIO_BD;
         */

                System.out.println("cargo properties servidor ptoventa");
                propertiesServidorCorrecto = true;
            } else {
                FarmaUtility.showMessage(myparent, 
                                         "Archivo de Configuracion del Servidor no Encontrado.\nPóngase en contacto con el área de sistemas.", 
                                         null);
                System.out.println("NO cargo properties servidor ptoventa");
                propertiesServidorCorrecto = false;
            }
            if (propertiesServidorCorrecto && propertiesClienteCorrecto)
                return true;
            else
                return false;
        } catch (FileNotFoundException fnfException) {
            fnfException.printStackTrace();
            FarmaUtility.showMessage(myparent, 
                                     "Archivo de Configuracion del Servidor no Encontrado.\nPóngase en contacto con el área de sistemas.", 
                                     null);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            FarmaUtility.showMessage(myparent, 
                                     "Error al leer archivo de Configuracion.\nPóngase en contacto con el área de sistemas.", 
                                     null);
        }
        myparent.dispose();
        return false;
    }

    /**
     * Realiza la lectura del archivo Properties para determinar la clave
     * de conexion con BD
     */
    private boolean readFilePasswordProperties() {
        boolean propertiesClaveCorrecto = true;
        try {
            InputStream fis = null;
            Properties properties = null;
            File archivo = null;
            // LEE PROPERTIES DE LA CLAVE
            //fis = new FileInputStream(FarmaConstants.RUTA_PROPERTIES_CLAVE);
            fis = 
this.getClass().getResourceAsStream(FarmaConstants.RUTA_PROPERTIES_CLAVE);
            //20.12.2007 ERIOS Se lee la ruta del parametro
            if (fis == null) {
                System.out.println("No leyo archivo");
                //archivo = new File("ptoventaid.properties");
                archivo = new File(prop2);
                fis = new FileInputStream(archivo);
                System.out.println(archivo);
            }
            if (fis != null) {
                properties = new Properties();
                properties.load(fis);
                FarmaVariables.vClaveBD = 
                        FarmaEncripta.desencripta(properties.getProperty("ClaveBD"));
                System.out.println("cargo properties clave ptoventa");
                
                /**
         * Obtiene los valores de SID y USER de la BD
         * @author dubilluz
         * @since  13.10.2007
         */
                FarmaVariables.vSID = properties.getProperty("SID");
                //          System.out.println("FarmaVariables.vSID=" + FarmaVariables.vSID);
                if (FarmaVariables.vSID == null)
                    FarmaVariables.vSID = ConstantsPtoVenta.SID;

                //          System.out.println("2. FarmaVariables.vSID=" + FarmaVariables.vSID);

                //FarmaVariables.vUsuarioBD = FarmaEncripta.desencripta(properties.getProperty("UsuarioBD"));
                FarmaVariables.vUsuarioBD = 
                        properties.getProperty("UsuarioBD");
                //        System.out.println("FarmaVariables.vUsuarioBD=" + FarmaVariables.vUsuarioBD);
                if (FarmaVariables.vUsuarioBD == null)
                    FarmaVariables.vUsuarioBD = ConstantsPtoVenta.USUARIO_BD;

                //       System.out.println("2. FarmaVariables.vUsuarioBD=" + FarmaVariables.vUsuarioBD);

                propertiesClaveCorrecto = true;
            } else {
                FarmaUtility.showMessage(myparent, 
                                         "Archivo de Configuracion de Clave no Encontrado.\nPóngase en contacto con el área de sistemas.", 
                                         null);
                System.out.println("NO cargo properties clave ptoventa");
                propertiesClaveCorrecto = false;
            }
            if (propertiesClaveCorrecto)
                return true;
            else
                return false;
        } catch (FileNotFoundException fnfException) {
            fnfException.printStackTrace();
            FarmaUtility.showMessage(myparent, 
                                     "Archivo de Configuracion de Clave no Encontrado.\nPóngase en contacto con el área de sistemas.", 
                                     null);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            FarmaUtility.showMessage(myparent, 
                                     "Error al leer archivo de Configuracion de Clave.\nPóngase en contacto con el área de sistemas.", 
                                     null);
        }
        myparent.dispose();
        return false;
    }

    /**
     * Realiza la lectura del archivo Properties para determinar los valores de las bases de datos remotas
     */
    private boolean readFileServRemotosProperties() {
        boolean propertiesServidorCorrecto = false;
        boolean propertiesClienteCorrecto = true;
        try {
            InputStream fis = null;
            Properties properties = null;
            File archivo = null;
            // LEE PROPERTIES DEL SERVIDOR
            fis = 
this.getClass().getResourceAsStream("/PtoVentaServRemotos.properties");
            //20.12.2007 ERIOS Se lee la ruta del parametro
            if (fis == null) {
                System.out.println("No leyo archivo");
                //archivo = new File("ptoventaid.properties");
                archivo = new File(prop3);
                fis = new FileInputStream(archivo);
                System.out.println(archivo);
            }
            if (fis != null) {
                properties = new Properties();
                properties.load(fis);
                FarmaVariables.vIdUsuDBMatriz = 
                        properties.getProperty("UsuarioMatriz");
                //System.out.println("FarmaVariables.vIdUsuDBMatriz=" + FarmaVariables.vIdUsuDBMatriz);

                FarmaVariables.vClaveBDMatriz = 
                        FarmaEncripta.desencripta(properties.getProperty("ClaveMatriz"));
                //System.out.println("FarmaVariables.vClaveBDMatriz =" + FarmaVariables.vClaveBDMatriz );

                FarmaVariables.vIpServidorDBMatriz = 
                        properties.getProperty("IpServidorMatriz");
                //System.out.println("FarmaVariables.vIpServidorDBMatriz =" + FarmaVariables.vIpServidorDBMatriz );

                FarmaVariables.vSidDBMatriz = 
                        properties.getProperty("SidMatriz");
                //System.out.println("FarmaVariables.vSidDBMatriz =" + FarmaVariables.vSidDBMatriz );

                FarmaVariables.vIdUsuDBDelivery = 
                        properties.getProperty("UsuarioDelivery");
                //System.out.println("FarmaVariables.vIdUsuDBDelivery =" + FarmaVariables.vIdUsuDBDelivery );

                FarmaVariables.vClaveBDDelivery = 
                        FarmaEncripta.desencripta(properties.getProperty("ClaveDelivery"));
                //System.out.println("FarmaVariables.vClaveBDDelivery  =" + FarmaVariables.vClaveBDDelivery  );

                FarmaVariables.vIpServidorDBDelivery = 
                        properties.getProperty("IpServidorDelivery");
                //System.out.println("FarmaVariables.vIpServidorDBDelivery  =" + FarmaVariables.vIpServidorDBDelivery  );

                FarmaVariables.vSidDBDelivery = 
                        properties.getProperty("SidDelivery");
                //System.out.println("FarmaVariables.vSidDBDelivery   =" + FarmaVariables.vSidDBDelivery   );

                //Agregado por DVELIZ 15.12.2008
                FarmaVariables.vIdUsuDBADMCentral = 
                        properties.getProperty("UsuarioADMCentral");
                //System.out.println("FarmaVariables.vIdUsuDBADMCentral   =" + FarmaVariables.vIdUsuDBADMCentral   );

                FarmaVariables.vClaveBDADMCentral = 
                        FarmaEncripta.desencripta(properties.getProperty("ClaveAdmCentral"));
                //System.out.println("FarmaVariables.vClaveBDADMCentral   =" + FarmaVariables.vClaveBDADMCentral   );

                FarmaVariables.vIpServidorDBADMCentral = 
                        properties.getProperty("IpServidorADMCentral");
                //System.out.println("FarmaVariables.vIpServidorDBADMCentral   =" + FarmaVariables.vIpServidorDBADMCentral   );

                FarmaVariables.vSidDBADMCentral = properties.getProperty("SidADMCentral");


                
                
                
                
                //Agregado por FRAMIREZ 16.12.2011

                FarmaVariables.vIdUsuDBRac = properties.getProperty("UsuarioRAC");
                FarmaVariables.vClaveBDRac = FarmaEncripta.desencripta(properties.getProperty("ClaveRAC"));
                FarmaVariables.vIpServidorDBRac = properties.getProperty("IpServidorRAC");
                FarmaVariables.vSidDBRac = properties.getProperty("SidRAC");
                
                
                propertiesServidorCorrecto = true;
            } else {
                FarmaUtility.showMessage(myparent, 
                                         "Archivo de Configuracion del Servidor no Encontrado.\nPóngase en contacto con el área de sistemas.", 
                                         null);
                System.out.println("NO cargo properties servidor ptoventa");
                propertiesServidorCorrecto = false;
            }
            if (propertiesServidorCorrecto && propertiesClienteCorrecto)
                return true;
            else
                return false;
        } catch (FileNotFoundException fnfException) {
            fnfException.printStackTrace();
            FarmaUtility.showMessage(myparent, 
                                     "Archivo de Configuracion del Servidor no Encontrado.\nPóngase en contacto con el área de sistemas.", 
                                     null);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            FarmaUtility.showMessage(myparent, 
                                     "Error al leer archivo de Configuracion.\nPóngase en contacto con el área de sistemas.", 
                                     null);
        }
        myparent.dispose();
        return false;
    }

    /**
     * Constructor que recibe parametros de properties
     * @param arch1 Properties FarmaVenta
     * @param arch2 Properties Clave
     * @param arch3 Properties Servidor Remoto
     * @author Edgar Rios Navarro
     * @since 20.12.2007
     */
    public EconoFar(String arch1, String arch2, String arch3) {
        prop1 = arch1;
        prop2 = arch2;
        prop3 = arch3;
        if (readFileProperties() && readFilePasswordProperties() && 
            readFileServRemotosProperties()) {
            Frame frame = new FrmEconoFar();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension frameSize = frame.getSize();
            if (frameSize.height > screenSize.height) {
                frameSize.height = screenSize.height;
            }
            if (frameSize.width > screenSize.width) {
                frameSize.width = screenSize.width;
            }
            frame.setLocation((screenSize.width - frameSize.width) / 2, 
                              (screenSize.height - frameSize.height) / 2);
            frame.addWindowListener(new WindowAdapter() {
                        public void windowClosing(WindowEvent e) {
                            System.exit(0);
                        }
                    });
            frame.setVisible(true);
        }
    }

    private void jbInit() throws Exception {
        myparent.setSize(new Dimension(400, 300));
    }
}
