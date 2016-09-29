package mifarma.ptoventa.inventario.precioCompetencia.reference;


import javax.swing.JLabel;
import mifarma.common.FarmaColumnData;

/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : ConstantsPrecioCompetencia.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * CLARICO      09.12.2014   Creación<br>
 * <br>
 * @author CLARICO<br>
 * @version 1.0<br>
 *
 */
public class ConstantsPrecioCompetencia {
    public ConstantsPrecioCompetencia() {
    }
    
    //DlgResumenCotizacion
    public static final FarmaColumnData[] columnsListaResumenCotizacion =
    {
      new FarmaColumnData("Codigo",50,JLabel.LEFT),
      new FarmaColumnData("Descripcion",180,JLabel.LEFT),
      new FarmaColumnData("Unidad",80,JLabel.LEFT),
      new FarmaColumnData("Laboratorio",150,JLabel.LEFT),
      new FarmaColumnData("Cant.",50,JLabel.RIGHT),
      new FarmaColumnData("Pre.Entero",60,JLabel.RIGHT),
      new FarmaColumnData("Total",50,JLabel.RIGHT),
      new FarmaColumnData("Imagen",50,JLabel.RIGHT),
      new FarmaColumnData("Val Frac",0,JLabel.RIGHT)
    };

    public static final Object[] defaultListaResumenCotizacion = {" ", " ", " "," ", " ", " ", " ", " "," "};
    
    
    public static final FarmaColumnData[] columnsListaProductosPorCotizar =
    { new FarmaColumnData( "Código", 50, JLabel.LEFT ),
      new FarmaColumnData( "Descripcion", 250, JLabel.LEFT ),
      new FarmaColumnData( "Unidad", 110, JLabel.LEFT ),
      new FarmaColumnData( "Laboratorio", 180, JLabel.LEFT ),
      new FarmaColumnData( "Tipo Cotización", 100, JLabel.LEFT )
    };

    public static final Object[] defaultcolumnsListaProductosPorCotizar = { 
      " ", " ", " ", " ", " "};
    
    public static final FarmaColumnData[] columnsSeleccionarListaProductosPorCotizar =
    { new FarmaColumnData("Sel", 25, JLabel.CENTER),
      new FarmaColumnData( "Código", 50, JLabel.LEFT ),
      new FarmaColumnData( "Descripcion", 245, JLabel.LEFT ),
      new FarmaColumnData( "Unidad", 110, JLabel.LEFT ),
      new FarmaColumnData( "Laboratorio", 180, JLabel.LEFT ),
      new FarmaColumnData( "Tipo Cotización", 100, JLabel.LEFT )
    };

    public static final Object[] defaultcolumnsSeleccionarListaProductosPorCotizar = { 
      " ", " ", " ", " ", " "," "};
    
    //DlgSeleccionarProductosNoCanasta
    public static final FarmaColumnData[] columnsListaProductosNoCanasta =
    {
      new FarmaColumnData("Sel",30,JLabel.LEFT),
      new FarmaColumnData("Codigo",50,JLabel.LEFT),
      new FarmaColumnData("Descripcion",250,JLabel.LEFT),
      new FarmaColumnData("Unidad",120,JLabel.LEFT),
      new FarmaColumnData("Laboratorio",210,JLabel.LEFT),
      new FarmaColumnData("Stock",0,JLabel.LEFT),
      new FarmaColumnData( "Precio", 0, JLabel.RIGHT ),
      new FarmaColumnData( "Zan", 0, JLabel.RIGHT ),
      new FarmaColumnData( "", 0, JLabel.LEFT ),//indicador producto congelado
      new FarmaColumnData( "", 0, JLabel.RIGHT )//valor de fraccion de local
    };

    public static final Object[] defaultListaProductosNoCanasta = { " ", " ", " ", " ", " ", " ", " ", " ", " ", " "};
    
    
    //
    public static final String NOM_HASHTABLE_CMBTIPOCOTIZACION = "cmbTipoCotizacion";
    public static final String NOM_HASHTABLE_CMBCOMPETIDOR = "cmbCompetidor";
    
    public static final String URL_UPLOAD_PHP = "http://10.85.8.27/cotPrecios/upload_from_java.php";
    public static final String CONDICION_COTIZAR = "COTIZAR";
    public static final String CONDICION_COMPRAR = "COMPRAR";
    public static final String SUSTENTO_ENCUESTA = "99";
    
    public static final String TIPO_COTIZACION_CONPRECIO = "1";
    public static final String TIPO_COTIZACION_NOVENDE = "2";
    public static final String TIPO_COTIZACION_SIN_STOCK = "3";
    
    public static final String CORREO_NOTIFICACION_TI = "clarico@mifarma.com.pe";
    
    public static final String RUTA_IMAGEN_SUSTENTO = "\\Mifarma\\imagenSustentoCompetencia\\";
    
}
