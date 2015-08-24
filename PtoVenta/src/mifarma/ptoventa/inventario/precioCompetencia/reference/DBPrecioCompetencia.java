package mifarma.ptoventa.inventario.precioCompetencia.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.common.FarmaDBUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo     : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DBPrecioCompetencia.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * CLARICO      09.12.2014   Creación<br>
 * <br>
 * @author Celso Larico Mullisaca<br>
 * @version 1.0<br>
 *
 */

public class DBPrecioCompetencia {
    private static final Logger log = LoggerFactory.getLogger(DBPrecioCompetencia.class);
    private static ArrayList parametros = new ArrayList();
    
    public DBPrecioCompetencia() {
        
    }
    
    public static void registrarPrecioCompetencia(String pCodProd,
                                                  String pCodLocal,
                                                  String pCodUsuario,
                                                  String pCodTipoCotizacion,
                                                  String pValFrac,
                                                  String pCantidad ,
                                                  String pPrecioUnitario,
                                                  String cCompetidor,
                                                  String pSustento ,
                                                  String pNumDoc,
                                                  String pFechaDocumento ,
                                                  String pArchivoSustento,
                                                  String pCondicion ,
                                                  String cNumNota,
                                                  String cMotivoNoImagen 
                                                    ) throws SQLException {
      parametros = new ArrayList();
      parametros.add(pCodProd);
      parametros.add(pCodLocal);
      parametros.add(pCodUsuario);
      parametros.add(pCodTipoCotizacion);
      parametros.add(new Double(pValFrac));
      parametros.add(new Integer(pCantidad));
      parametros.add(new Double(pPrecioUnitario));
      parametros.add(cCompetidor);
      parametros.add(pSustento);
      parametros.add(pNumDoc);
      parametros.add(pFechaDocumento);
      parametros.add(pArchivoSustento);
      parametros.add(pCondicion);
      parametros.add(cNumNota);
      parametros.add(cMotivoNoImagen);
      
      log.debug("invocando a PTOVENTA_PRECIO_COMPETENCIA.PREC_REGISTRA_COTIZACION(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?):"+parametros);
      FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_PRECIO_COMPETENCIA.PREC_REGISTRA_COTIZACION(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",parametros,false);
      }   
}
