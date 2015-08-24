package mifarma.ptoventa;


import com.gs.mifarma.worker.JDialogProgress;

import java.awt.Frame;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.sql.SQLException;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.ce.reference.DBCajaElectronica;
import mifarma.ptoventa.cnx.FarmaVentaCnxUtility;
import mifarma.ptoventa.hilos.SubProcesos;
import mifarma.ptoventa.recaudacion.reference.FacadeRecaudacion;
import mifarma.ptoventa.reference.ConstantsPtoVenta;
import mifarma.ptoventa.reference.DBPtoVenta;
import mifarma.ptoventa.reference.VariablesPtoVenta;
import mifarma.ptoventa.ventas.reference.DBVentas;
import mifarma.ptoventa.ventas.reference.UtilityVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgProcesar extends JDialogProgress {

    private static final Logger log = LoggerFactory.getLogger(DlgProcesar.class);

    private static Frame myParentFrame;


    public DlgProcesar() {
        this(null, "", false);
    }

    public DlgProcesar(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        myParentFrame = parent;
    }

    public void cargaIndImpresionRojoTicket() {
        String pResultado = "";
        try {
            pResultado = DBVentas.getIndImprimeRojo();
            log.info("pResultado:" + pResultado);
            if (pResultado.trim().equalsIgnoreCase("S"))
                VariablesPtoVenta.vIndImprimeRojo = true;
            else
                VariablesPtoVenta.vIndImprimeRojo = false;
        } catch (SQLException err) {
            log.error("", err);
            VariablesPtoVenta.vIndImprimeRojo = false;
        }

        log.info("VariablesPtoVenta.vIndImprimeRojo:" + VariablesPtoVenta.vIndImprimeRojo);
    }

    private void cargaDestinatarioEmailErrorCobro() {
        String pResultado = "";
        try {
            pResultado = DBVentas.getDestinatarioErrorCobro();
            VariablesPtoVenta.vDestEmailErrorCobro = pResultado;
        } catch (SQLException err) {
            log.error("", err);
        }
    }

    private void cargaDestinatarioEmailErrorAnulacion() {
        String pResultado = "";
        try {
            pResultado = DBVentas.getDestinatarioErrorAnulacion();
            VariablesPtoVenta.vDestEmailErrorAnulacion = pResultado;
        } catch (SQLException err) {
            log.error("", err);
        }
    }

    private void cargaDestinatarioEmailErrorImpresion() {
        String pResultado = "";
        try {
            pResultado = DBVentas.getDestinatarioErrorImpresion();
            VariablesPtoVenta.vDestEmailErrorImpresion = pResultado;
        } catch (SQLException err) {
            log.error("", err);
        }
    }

    private void cargaIndVerStockLocales() {
        String pResultado = "";
        try {
            pResultado = DBVentas.getIndVerStockLocales();
            VariablesPtoVenta.vIndVerStockLocales = pResultado;
        } catch (SQLException err) {
            VariablesPtoVenta.vIndVerStockLocales = "N";
            log.error("", err);
        }
    }

    private void cargaIndVerRecetarioMagistral() {
        String pResultado = "";
        try {
            pResultado = DBVentas.getIndVerRecetarioMagis();
            VariablesPtoVenta.vIndVerReceMagis = pResultado;
        } catch (SQLException err) {
            log.error("", err);
            VariablesPtoVenta.vIndVerReceMagis = "N";
        }
    }

    /**
     * Grabas la imagenes del programa en el disco duro
     * @author ERIOS
     * @since 24.06.2013
     */
    private void grabarImagenesDisco() {
        try {
            String sufijoEmpresa = DBPtoVenta.obtieneRutaImagen();
            String carpetaRaiz = DBPtoVenta.getDirectorioRaiz();
            String carpetaImagenes = DBPtoVenta.getDirectorioImagenes();
            String carpetaComprobantes = DBPtoVenta.getDirectorioComprobantes();

            //Crear carpeta raiz
            Path dir = Paths.get(carpetaRaiz);
            if (Files.notExists(dir)) {
                Files.createDirectory(dir);
            }

            //Crear carpeta comprobantes
            dir = Paths.get(carpetaRaiz, carpetaComprobantes);
            if (Files.notExists(dir)) {
                Files.createDirectory(dir);
            }

            //Crear carpeta imagenes
            dir = Paths.get(carpetaRaiz, carpetaImagenes);
            if (Files.notExists(dir)) {
                Files.createDirectory(dir);
            }
            //Elimina el contenido del directorio
            //Verificar el metodo que borra codigos de barra
            /*if(Files.exists(dir)){
                try (DirectoryStream<Path> ds = Files.newDirectoryStream(dir) ){
                    for (Path p : ds) {
                        Files.delete(p);
                    }
                } catch (IOException e) {
                    log.error("",e);
                }
            }*/
            UtilityVentas.eliminaImagenesCodBarra();

            //Copiar imagenes
            Path archivo = Paths.get(carpetaRaiz, carpetaImagenes, "Logo" + sufijoEmpresa);
            URL u = FrmEconoFar.class.getResource("/mifarma/ptoventa/imagenes/Logo" + sufijoEmpresa);
            try (InputStream in = u.openStream()) {
                Files.copy(in, archivo, StandardCopyOption.REPLACE_EXISTING);
            }
            //consejos
            archivo = Paths.get(carpetaRaiz, carpetaImagenes, "consejo.jpg");
            u = FrmEconoFar.class.getResource("/mifarma/ptoventa/imagenes/consejo.jpg");
            try (InputStream in = u.openStream()) {
                Files.copy(in, archivo, StandardCopyOption.REPLACE_EXISTING);
            }
            //ERIOS 24.10.2014 Copiar imagenes de Facturacion Electronica
            archivo = Paths.get(carpetaRaiz, carpetaImagenes, "LogoE" + sufijoEmpresa);
            u = FrmEconoFar.class.getResource("/mifarma/ptoventa/imagenes/LogoE" + sufijoEmpresa);
            try (InputStream in = u.openStream()) {
                Files.copy(in, archivo, StandardCopyOption.REPLACE_EXISTING);
            }
            //INI ASOSA - 10/03/2015 - PTOSYAYAYAYA
            archivo = Paths.get(carpetaRaiz, carpetaImagenes, "logo-experto.jpg");
            u = FrmEconoFar.class.getResource("/mifarma/ptoventa/imagenes/logo-experto.jpg");
            try (InputStream in = u.openStream()) {
                Files.copy(in, archivo, StandardCopyOption.REPLACE_EXISTING);
            }
            //FIN ASOSA - 10/03/2015 - PTOSYAYAYAYA
            //INI ASOSA - 08/05/2015 - PTOSYAYAYAYA
            archivo = Paths.get(carpetaRaiz, carpetaImagenes, "imgPunto.jpg");
            u = FrmEconoFar.class.getResource("/mifarma/ptoventa/imagenes/imgPunto.jpg");
            try (InputStream in = u.openStream()) {
                Files.copy(in, archivo, StandardCopyOption.REPLACE_EXISTING);
            }
            //FIN ASOSA - 08/05/2015 - PTOSYAYAYAYA
        } catch (IOException | NullPointerException e) {
            log.error("Error al grabar imagenes al disco", e);
        } catch (SQLException e) {
            log.error("Error al recuperar informacion de la BBDD", e);
        }
    }

    /**
     * Indicador de servicios FarmaSix
     * @author ERIOS
     * @since 16.07.2013
     */
    public static void cargaIndServicioFarmaSix() {
        String pResultado = "";
        try {
            pResultado = DBPtoVenta.getIndServicioFarmaSix();
            VariablesPtoVenta.vIndFarmaSix = pResultado;
        } catch (SQLException err) {
            log.error("Error al ", err);
            VariablesPtoVenta.vIndFarmaSix = "N";
        }
    }

    /**
     * Indicador de Pinpad
     * @author ERIOS
     * @since 16.08.2013
     */
    public static void cargaIndPinpad() {
        String pResultado = "";
        try {
            pResultado = DBPtoVenta.getIndPinpad();
            VariablesPtoVenta.vIndPinpad = pResultado;
        } catch (SQLException err) {
            log.error("Error al ", err);
            VariablesPtoVenta.vIndPinpad = "N";
        }
    }

    /**
     * Indicador de Impresion url web
     * @author ERIOS
     * @since 16.08.2013
     */
    private void cargaIndImprWeb() {
        String pResultado = "";
        try {
            pResultado = DBPtoVenta.getIndImprWeb();
            VariablesPtoVenta.vIndImprWeb = pResultado.trim();
        } catch (SQLException err) {
            log.error("Error al ", err);
            VariablesPtoVenta.vIndImprWeb = "N";
        }
    }

    @Override
    public void ejecutaProceso() {

        try {
            VariablesPtoVenta.vNumeroDiasSinVentas = DBVentas.obtieneNumeroDiasSinVentas();
            cargaDestinatarioEmailErrorCobro();
            cargaDestinatarioEmailErrorAnulacion();
            cargaDestinatarioEmailErrorImpresion();
            cargaDestinatarioEmailErrorConexion();
            cargaIndVerStockLocales();

            cargaIndVerRecetarioMagistral();
            grabarImagenesDisco();
            cargaIndServicioFarmaSix();
            cargaIndPinpad();
            cargarIndTico(); /// INDICADOR TICO
            cargaIndImprWeb();

            //ERIOS 2.2.8 Salida de mensajes ocultos
            //SystemOutLogger.redirect();

            cargarUsuarioRemotoRAC();
            cargarUsuarioRemotoAPPS();
            cargarUsuarioRemotoMATRIZ();
            cargarUsuarioRemotoDELIVERY();

            Thread.sleep(1000);
            //ERIOS 2.3.3 Carga de listado de productos
            SubProcesos subproceso1 = new SubProcesos("GET_PROD_VENTA");
            subproceso1.start();
            while (subproceso1.isAlive()) {
                ;
            }
            log.info("Termino procesar");
        } catch (SQLException | InterruptedException err) {
            log.error("", err);
            FarmaUtility.showMessage(this, "Error al obtener informacion relevante de la aplicacion.", null);
        }
    }

    /**
     * Indicador de Conciliacion En Linea
     * @author ERIOS
     * @since 29.11.2013
     */
    public static String cargaIndConciliaconOnline() {
        String pResultado = "";
        try {
            pResultado = DBPtoVenta.getIndConciliaconOnline();
        } catch (SQLException err) {
            log.error("Error al ", err);
            pResultado = "N";
        }
        return pResultado;
    }

    /**
     * Se indica la version del sistema
     * @author ERIOS
     * @since 2.2.9
     */
    public static void setVersion() {
        try {
            DBPtoVenta.setVersion();
        } catch (Exception err) {
            log.error("", err);
        }
    }

    /**
     * Indicador de recaudacion centralizada
     * @author ERIOS
     * @since 28.05.2014
     * @return
     */
    public static int cargaIndRecaudacionCentralizada() {
        int pResultado = 0;
        try {
            pResultado = DBPtoVenta.getIndRecaudacionCentralizada();
        } catch (SQLException err) {
            log.error("Error al ", err);
            pResultado = 0;
        }
        return pResultado;
    }

    /**
     * Carga usuario por local para conexion al RAC
     * @author ERIOS
     * @since 2.4.4
     */
    private void cargarUsuarioRemotoRAC() {

        try {
            FacadeRecaudacion facadeRecaudacion = new FacadeRecaudacion();
            facadeRecaudacion.validarConexionRAC();

        } catch (Exception f) {
            log.error("", f);
        }
    }

    /**
     * Creación:    RHERRERA
     * Fecha:       15.09.2014
     * Descruoción: Método valida si el local es un Market o un Farma
     */

    private void cargarIndTico() {
        String pResultado = "";

        try {
            pResultado = DBCajaElectronica.getIndTico();
            VariablesPtoVenta.vIndTico = pResultado.trim();
            String indPadre = "";

            if (pResultado.equals("N")) {

                indPadre = DBCajaElectronica.getLocalPadre();
                VariablesPtoVenta.vIndLocalPadre = indPadre;

                if (indPadre.equals("S"))

                    DBCajaElectronica.getLocalTicoIP(VariablesPtoVenta.vLocarMarketH);


            } else {
                // 20.01.2015 rherrera obtien indicador si el tico cuenta con un farma padre
                String vIndFarma = DBCajaElectronica.getIndPadre();
                VariablesPtoVenta.vIndConFarma = vIndFarma;        
                    if (vIndFarma.equals("S"))                
                    FarmaVariables.vIpServidorTico = DBCajaElectronica.getLocalTico();
                    
            }

        } catch (SQLException err) {
            log.error("Error al ", err);
            VariablesPtoVenta.vIndTico = "N";
        }
    }

    /**
     * Indicador de mostrar columnas descuentos
     * @author ERIOS
     * @since 2.4.7
     * @return
     */
    public static boolean cargaIndMostrarColumnasDesc() {
        String pResultado = "";
        try {
            pResultado = DBPtoVenta.getIndMostrarColumnasDesc();
        } catch (SQLException err) {
            log.error("Error al ", err);
            pResultado = "S";
        }
        return (pResultado.equals(FarmaConstants.INDICADOR_S)) ? true : false;
    }

    /**
     * Conexion al servidor APPS
     * @author ERIOS
     * @since 09.12.2014
     */
    private void cargarUsuarioRemotoAPPS() {

        try {
            FarmaVentaCnxUtility facade = new FarmaVentaCnxUtility();

            FarmaVariables.conexionAPPS = facade.setBeanConexion(facade.getCnxRemotoAPPS());

            FarmaVariables.conexionAPPS.setUsuarioBD("LOC_" + FarmaVariables.vCodLocal);

        } catch (Exception f) {
            log.error("", f);
        }
    }

    private void cargarUsuarioRemotoMATRIZ() {

        try {
            FarmaVentaCnxUtility facade = new FarmaVentaCnxUtility();

            FarmaVariables.conexionMATRIZ = facade.setBeanConexion(facade.getCnxRemotoMATRIZ());

        } catch (Exception f) {
            log.error("", f);
        }
    }

    private void cargarUsuarioRemotoDELIVERY() {

        try {
            FarmaVentaCnxUtility facade = new FarmaVentaCnxUtility();

            FarmaVariables.conexionDELIVERY = facade.setBeanConexion(facade.getCnxRemotoDELIVERY());

        } catch (Exception f) {
            log.error("", f);
        }
    }

    /**
     * Indicador de pantalla de Garantizados
     * @author ERIOS
     * @since 12.01.2015
     * @return
     */
    public static String cargaIndActGarantizados() {
        String pResultado = "";
        try {
            pResultado = DBPtoVenta.getIndActGarantizados();
        } catch (SQLException err) {
            log.error("Error al ", err);
            pResultado = "N";
        }
        return pResultado;
    }
    
    private void cargaDestinatarioEmailErrorConexion() {
        String pResultado = "";
        try {
            pResultado = DBVentas.getDestinatarioFarmaEmail(ConstantsPtoVenta.FARMA_EMAIL_CONEXION);
            VariablesPtoVenta.vDestEmailErrorConexion = pResultado;
        } catch (Exception err) {
            log.error("", err);
        }
    }
}
