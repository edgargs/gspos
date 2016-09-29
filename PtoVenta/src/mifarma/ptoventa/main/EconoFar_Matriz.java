package mifarma.ptoventa.main;


import com.gs.encripta.FarmaEncripta;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2005 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : EconoFar_Matriz.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * ERIOS      04.07.2006   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */
public class EconoFar_Matriz {
    private static final Logger log = LoggerFactory.getLogger(EconoFar_Matriz.class);
    String prop1, prop2;

    static JFrame myparent = new JFrame();

    /**
     * Frame principal de la Aplicación
     */
    public EconoFar_Matriz() {
        if (readFileProperties() && readFilePasswordProperties()) {
            JFrame frame = new FrmLocales_Matriz();
            frame.setVisible(true);
        }
    }

    public static void main(String[] args) {
        new EconoFar_Matriz();
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
            fis = this.getClass().getResourceAsStream("/PtoVentaServ.properties");
            //25.01.2008 ERIOS Se lee la ruta del parametro
            if (fis == null) {
                log.info("No leyo archivo");
                //archivo = new File("AdmCentralServ.properties");
                archivo = new File(prop1);
                fis = new FileInputStream(archivo);
                log.info("" + archivo);
            }
            if (fis != null) {
                properties = new Properties();
                properties.load(fis);
                FarmaVariables.vCodGrupoCia = properties.getProperty("CodigoGrupoCompania");
                //log.debug("FarmaVariables.vCodGrupoCia=" + FarmaVariables.vCodGrupoCia);
                FarmaVariables.vCodCia = properties.getProperty("CodigoCompania");
                FarmaVariables.vCodLocal = properties.getProperty("CodigoLocal");
                //log.debug("FarmaVariables.vCodLocal=" + FarmaVariables.vCodLocal);
                FarmaVariables.vImprReporte = properties.getProperty("ImpresoraReporte");
                //log.debug("FarmaVariables.vImprReporte=" + FarmaVariables.vImprReporte);
                FarmaVariables.vIPBD = properties.getProperty("IpServidor");
                /**
         * Se comento para cargarlos junto con la clave
         * @author dubilluz
         * @since  13.10.2007
         */
                /*
        FarmaVariables.vSID = properties.getProperty("SID");
          //log.debug("FarmaVariables.vSID=" + FarmaVariables.vSID);
        if(FarmaVariables.vSID == null)
          FarmaVariables.vSID = ConstantsPtoVenta.SID;

        //FarmaVariables.vUsuarioBD = FarmaEncripta.desencripta(properties.getProperty("UsuarioBD"));
        FarmaVariables.vUsuarioBD = properties.getProperty("UsuarioBD");
          //log.debug("FarmaVariables.vUsuarioBD=" + FarmaVariables.vUsuarioBD);
        if(FarmaVariables.vUsuarioBD == null)
          FarmaVariables.vUsuarioBD = ConstantsPtoVenta.USUARIO_BD;
       */
                log.info("cargo properties servidor ptoventa");

                propertiesServidorCorrecto = true;
            } else {
                FarmaUtility.showMessage(myparent,
                                         "Archivo de Configuracion del Servidor no Encontrado.\nPóngase en contacto con el área de sistemas.",
                                         null);
                log.info("NO cargo properties servidor ptoventa");
                propertiesServidorCorrecto = false;
            }
            if (propertiesServidorCorrecto && propertiesClienteCorrecto)
                return true;
            else
                return false;
        } catch (FileNotFoundException fnfException) {
            log.error("", fnfException);
            FarmaUtility.showMessage(myparent,
                                     "Archivo de Configuracion del Servidor no Encontrado.\nPóngase en contacto con el área de sistemas.",
                                     null);
        } catch (IOException ioException) {
            log.error("", ioException);
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
            fis = this.getClass().getResourceAsStream(FarmaConstants.RUTA_PROPERTIES_CLAVE);
            //25.01.2008 ERIOS Se lee la ruta del parametro
            if (fis == null) {
                log.info("No leyo archivo");
                //archivo = new File("ptoventaid.properties");
                archivo = new File(prop2);
                fis = new FileInputStream(archivo);
                log.info("" + archivo);
            }
            if (fis != null) {
                properties = new Properties();
                properties.load(fis);
                FarmaVariables.vClaveBD = FarmaEncripta.desencripta(properties.getProperty("ClaveBD"));
                log.info("0. FarmaVariables.vClaveBD=" + FarmaVariables.vClaveBD + ".");

                /**
         * Se obtiene el SID y USER de BD
         * @author dubilluz
         * @since  13.10.2007
         */
                FarmaVariables.vSID = properties.getProperty("SID");
                log.info("1. FarmaVariables.vSID=" + FarmaVariables.vSID + ".");
                if (FarmaVariables.vSID == null)
                    FarmaVariables.vSID = ConstantsPtoVenta.SID;

                log.info("2. FarmaVariables.vSID=" + FarmaVariables.vSID + ".");

                //FarmaVariables.vUsuarioBD = FarmaEncripta.desencripta(properties.getProperty("UsuarioBD"));
                FarmaVariables.vUsuarioBD = properties.getProperty("UsuarioBD");
                log.debug("1. FarmaVariables.vUsuarioBD=" + FarmaVariables.vUsuarioBD + ".");
                if (FarmaVariables.vUsuarioBD == null)
                    FarmaVariables.vUsuarioBD = ConstantsPtoVenta.USUARIO_BD;

                log.info("2. FarmaVariables.vUsuarioBD=" + FarmaVariables.vUsuarioBD + ".");

                log.info("cargo properties clave ptoventa.");
                propertiesClaveCorrecto = true;
            } else {
                FarmaUtility.showMessage(myparent,
                                         "Archivo de Configuracion de Clave no Encontrado.\nPóngase en contacto con el área de sistemas.",
                                         null);
                log.info("NO cargo properties clave ptoventa");
                propertiesClaveCorrecto = false;
            }
            if (propertiesClaveCorrecto)
                return true;
            else
                return false;
        } catch (FileNotFoundException fnfException) {
            log.error("", fnfException);
            FarmaUtility.showMessage(myparent,
                                     "Archivo de Configuracion de Clave no Encontrado.\nPóngase en contacto con el área de sistemas.",
                                     null);
        } catch (IOException ioException) {
            log.error("", ioException);
            FarmaUtility.showMessage(myparent,
                                     "Error al leer archivo de Configuracion de Clave.\nPóngase en contacto con el área de sistemas.",
                                     null);
        }
        myparent.dispose();
        return false;
    }

    /**
     * Constructor que recibe parametros de properties
     * @param arch1
     * @param arch2
     * @author Edgar Rios Navarro
     * @since 25.01.2008
     */
    public EconoFar_Matriz(String arch1, String arch2) {
        prop1 = arch1;
        prop2 = arch2;
        if (readFileProperties() && readFilePasswordProperties()) {
            JFrame frame = new FrmLocales_Matriz();
            frame.setVisible(true);
        }
    }
}
