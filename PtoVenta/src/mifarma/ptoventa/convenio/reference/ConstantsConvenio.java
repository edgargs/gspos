package mifarma.ptoventa.convenio.reference;

import javax.swing.JLabel;
import mifarma.common.*;

public class ConstantsConvenio 
{
  public ConstantsConvenio()
  {
  }
  
  public static final FarmaColumnData columnsListaConvenios[] = {
    new FarmaColumnData( "Codigo", 100, JLabel.CENTER ),
    new FarmaColumnData( "Descripcion", 250, JLabel.LEFT ),
    new FarmaColumnData( "", 0, JLabel.LEFT ),// Proc Descuento
    new FarmaColumnData( "", 0, JLabel.LEFT ),// Proc Copago
    new FarmaColumnData( "", 0, JLabel.LEFT ),// indicador de Cliente Dependencia
    new FarmaColumnData( "indSoloCred", 0, JLabel.LEFT )
  };
  public static final Object[] defaultValuesListaConvenios = {" ", " "," "," "," ", " "};

  public static final FarmaColumnData[] columnsListaDatosConvenio =
  { new FarmaColumnData("Desc.", 200, JLabel.LEFT), 
    new FarmaColumnData("Dato", 300, JLabel.RIGHT), 
    new FarmaColumnData("Codigo", 0, JLabel.CENTER), 
    new FarmaColumnData("TI_DATO", 0, JLabel.CENTER), 
    new FarmaColumnData("IN_SOLO_LECTURA", 0, JLabel.CENTER), 
    new FarmaColumnData("IN_OBLIGATORIO", 0, JLabel.CENTER), 
    new FarmaColumnData("IND_BUSQUEDA", 0, JLabel.CENTER) };

  public static final Object[] defaultValuesListaDatosConvenio =
  { " ", " ", " ", " ", " ", " ", " " };

  public static final Boolean[] editableListaDatosConvenio =
  { new Boolean(false), new Boolean(true), new Boolean(false), 
    new Boolean(false), new Boolean(false), new Boolean(false), 
    new Boolean(false) };
                                                             

  public static final String  CAMPO_CODIGO_TRABAJADOR = "001" ;
  public static final String  CAMPO_NOMBRE = "002" ;
  public static final String  CAMPO_APELLIDO_MATERNO = "003" ;
  public static final String  CAMPO_APELLIDO_PATERNO = "004" ;
  public static final String  CAMPO_NUMERO_DOCUMENTO = "005" ;
  public static final String  CAMPO_TELEFONO = "006" ;
  /**
   * Para el condigo del TRabajador Empresa
   * @author dubilluz
   * @since  17.08.2007
   */
  public static final String  CAMPO_CODIGO_TRABAJADOR_EMPRESA = "008" ;  
  public static final String  CAMPO_CODIGO_INTERNO = "007" ;
  public static final String  CAMPO_CODIGO_DEPENDIENTE = "009" ;
  public static final String  CAMPO_NOMBRE_DEPENDIENTE = "010" ;
  public static final String  CAMPO_NOMBRE_CONVENIO = "011" ;
  
  
    
  
  
  /**
   * Columnas del listado de clientes por convenio.
   * @author Edgar Rios Navarro
   * @since 21.05.2007
   */
  public static final FarmaColumnData columnsListaClientesConv[] =
  { new FarmaColumnData("Codigo", 80, JLabel.LEFT), 
    new FarmaColumnData("Codigo Trab.", 80, JLabel.LEFT), 
    new FarmaColumnData("Descripcion", 300, JLabel.LEFT),
    new FarmaColumnData("Doc. Ident.", 90, JLabel.LEFT),
    new FarmaColumnData("", 0, JLabel.LEFT)};// cod_trab};
                                                             
  /**
   * Valores por defecto del listado de clientes por convenio.
   * @author Edgar Rios Navarro
   * @since 21.05.2007
   */
  public static final Object[] defaultValuesListaClientesConv =
  { " ", " ", " ", " ", " " };                                                           
  
    
  /**
   * Columnas del listado de Dependientes clientes .
   * @author dubilluz
   * @since 30.01.2008
   */
  public static final FarmaColumnData columnsListaDepClientesConv[] =
  { new FarmaColumnData("Codigo", 80, JLabel.LEFT), 
    new FarmaColumnData("Codigo Trab.", 80, JLabel.LEFT), 
    new FarmaColumnData("Descripcion", 300, JLabel.LEFT),
    new FarmaColumnData("Doc. Ident.", 90, JLabel.LEFT),
    new FarmaColumnData("", 0, JLabel.LEFT)};// cod_trab                                                         
  /**
   * Valores por defecto del listado de clientes por convenio.
   * @author dubilluz
   * @since 30.01.2008
   */
  public static final Object[] defaultValuesListaDepClientesConv =
  { " ", " ", " "," "," "}; 

}