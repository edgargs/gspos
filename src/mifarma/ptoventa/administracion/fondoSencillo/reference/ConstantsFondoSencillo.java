/**
* @AUTHOR   
* @SINCE  24.02.2010
* */

package mifarma.ptoventa.administracion.fondoSencillo.reference;
import javax.swing.JLabel;

import mifarma.common.FarmaColumnData;
import mifarma.common.FarmaConstants;

public class ConstantsFondoSencillo {
    public ConstantsFondoSencillo() {
    }
    
    // Lista de Cajeros Disponibles para Asignar Fondo de Sencillo
    public static final FarmaColumnData columnsListaCajerosDisponibles[] = {
      new FarmaColumnData( "Sec. Usu.", 65, JLabel.LEFT ), //0
      new FarmaColumnData( "Nombres", 150, JLabel.LEFT ),  //1
      new FarmaColumnData( "Ape. Paterno", 115, JLabel.LEFT ),
      new FarmaColumnData( "Ape. Materno", 115, JLabel.LEFT ),      
      new FarmaColumnData( "Id. Usuario", 90, JLabel.LEFT )
            //
    }; 
    public static final Object[] defaultValuesListaCajerosDisponibles = {" "," "," "," "," "," "};
    
    //Lista de Cajeros que han devuelto Fondo de Sencillo
    public static final FarmaColumnData columnsListaHistorico[] = {
      new FarmaColumnData( "Fecha", 125, JLabel.LEFT),
      //new FarmaColumnData( "Sec. Fondo.", 80, JLabel.CENTER),
      new FarmaColumnData( "Tipo", 75, JLabel.LEFT),
      new FarmaColumnData( "Usu. Origen", 165, JLabel.LEFT ),
      new FarmaColumnData( "Usu. Destino", 165, JLabel.LEFT ),
     // new FarmaColumnData( "Login Usu.", 100, JLabel.LEFT ),      
      new FarmaColumnData( "Monto", 55, JLabel.RIGHT ),      
      new FarmaColumnData( "Estado", 80, JLabel.LEFT ),
      new FarmaColumnData( "Caja", 40, JLabel.CENTER ),
      new FarmaColumnData( "Turno", 45, JLabel.CENTER )
      //sec_fondo_sen 8
      //SEC_USU_destino 9
      //SEC_USU_origen 10
      //IND_TIPO_FONDO_SEN 11
      //ESTADO 12
     
      
      }; 
    public static final Object[] defaultValuesListaHistorico = {" "," "," "," "," "," "," ", " ", " ",
                                                                       " "," "," "," "," "};  
     
    public static final String TipoFondoAsigna = "A";
    public static final String TipoFondoDevuelve = "D";
    public static final String EstadoEmitido = "E";
    public static final String EstadoAceptado = "A";
    public static final String EstadoRechazado = "R";
    public static final String EstadoAnulado = "N";
        
    public static final String NOM_HASTABLE_CMBADM_LOCAL = "CMB_ADM_LOCAL";  
    public static final String NOM_HASTABLE_CMBFILTRO_HIST = "CMB_FILTRO_HIST";
    
    
    
    public static final FarmaColumnData columnsListaCajerosInicial[] = {
      new FarmaColumnData( "Nombres", 250, JLabel.LEFT ),  //0
      new FarmaColumnData( "Estado", 80, JLabel.CENTER ),    //1
      new FarmaColumnData( "Monto S/.", 60, JLabel.RIGHT),  //2      
      new FarmaColumnData( "Caja", 40, JLabel.RIGHT ),       //3
      new FarmaColumnData( "Turno", 45, JLabel.RIGHT ),      //4
      new FarmaColumnData( "Fecha", 125, JLabel.LEFT ),     //5
      new FarmaColumnData( "Adm. Local", 125, JLabel.LEFT ) //6
      //secUsuCajero 7
      //Estado 8
      
    }; 
    public static final Object[] defaultValuesListaCajerosInicial = {" "," "," "," "," "," "," "," "
                                                                        ," "," "," "," "," "};
}
