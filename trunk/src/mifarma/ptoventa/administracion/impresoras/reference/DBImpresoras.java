package mifarma.ptoventa.administracion.impresoras.reference;

import java.sql.SQLException;
import java.util.ArrayList;

import mifarma.common.FarmaDBUtility;
import mifarma.common.FarmaTableModel;
import mifarma.common.FarmaVariables;

/**
 * Copyright (c) 2009 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DlgListaIPSImpresora.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCHAVEZ 26.06.2009 Modificación<br>
 * <br>
 * @version 1.0<br>
 * 
 */
public class DBImpresoras {
	public DBImpresoras() {
	}

	private static ArrayList parametros = new ArrayList();

	public static void getListaImpresoras(FarmaTableModel pTableModel)
			throws SQLException {
		pTableModel.clearTable();
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);

		FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
				"PTOVENTA_ADMIN_IMP.IMP_LISTA_IMPRESORAS(?,?)", parametros, false);
	}

    //JMIRANDA 25/06/2009 LISTA IMPRESORAS TERMICAS
    public static void getListaImpresorasTermicas(FarmaTableModel pTableModel)
                    throws SQLException {
            pTableModel.clearTable();
            parametros = new ArrayList();
            parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(FarmaVariables.vCodLocal);
    //cambiar REFer PACKAGE
            FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                            "PTOVENTA_ADMIN_IMP.IMP_F_CUR_LISTA_IMP_TERMICAS(?,?)", parametros, false);
    }
    
	public static void getListaAsigCajasImpresoras(FarmaTableModel pTableModel)
			throws SQLException {
		pTableModel.clearTable();
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);

		FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
				"PTOVENTA_ADMIN_IMP.IMP_LISTA_ASIGNACIONES_CAJA_IMPR(?,?)", parametros,
				false);
	}

	public static void ingresaImpresora(String pCodGrupoCia, String pCodLocal,
			String pNumSerieLocal, String pTipComp, String pDescImprLocal,
			String pNumComp, String pRutaImp) throws SQLException {

		parametros = new ArrayList();

		parametros.add(pCodGrupoCia);
		parametros.add(pCodLocal);
		parametros.add(pNumSerieLocal);
		parametros.add(pTipComp);
		parametros.add(pDescImprLocal);
		parametros.add(pNumComp);
		parametros.add(pRutaImp);
		parametros.add(FarmaVariables.vIdUsu);

		FarmaDBUtility.executeSQLStoredProcedure(null,
				"PTOVENTA_ADMIN_IMP.IMP_INGRESA_IMPRESORA(?,?,?,?,?,?,?,?)",
				parametros, false);

	}

//JMIRANDA  30/06/2009
    public static void ingresaImpresoraTermica(String pCodGrupoCia, String pCodLocal,
                    String pDescImprLocalTerm, String pTipoImprTermica, 
                    String pEstImprLocal) throws SQLException {
                
            parametros = new ArrayList();
        
            parametros.add(pCodGrupoCia);
            parametros.add(pCodLocal);
                       
            parametros.add(pDescImprLocalTerm);
            parametros.add(pTipoImprTermica);
            
            parametros.add(pEstImprLocal);
            parametros.add(FarmaVariables.vIdUsu);

            FarmaDBUtility.executeSQLStoredProcedure(null,
                            "PTOVENTA_ADMIN_IMP.IMP_P_INSERT_IMP_TERMICA(?,?,?,?,?,?)",
                            parametros, false);

    }
	public static void modificaImpresora(String pCodGrupoCia, String pCodLocal,
			String pSecImprLocal, String pNumSerieLocal, String pTipComp,
			String pDescImprLocal, String pNumComp, String pRutaImp)
			throws SQLException {

		parametros = new ArrayList();

		parametros.add(pCodGrupoCia);
		parametros.add(pCodLocal);
		parametros.add(new Integer(pSecImprLocal));
		parametros.add(pNumSerieLocal);
		parametros.add(pTipComp);
		parametros.add(pDescImprLocal);
		parametros.add(pNumComp);
		parametros.add(pRutaImp);
		parametros.add(FarmaVariables.vIdUsu);

		FarmaDBUtility.executeSQLStoredProcedure(null,
				"PTOVENTA_ADMIN_IMP.IMP_MODIFICA_IMPRESORA(?,?,?,?,?,?,?,?,?)",
				parametros, false);

	}
        
        //JMIRANDA 30/06/2009
        public static void modificaImpresoraTermica(String pCodGrupoCia, String pCodLocal,
                        String pSecImprLocTerm, String pDescImprLocalTerm,
                        String pTipoImprTermica,String pEstImprLocal)
        
                        throws SQLException {
    
                parametros = new ArrayList();
             /* (cod_grupo_cia, cod_local, sec_impr_loc_term, 
              desc_impr_local_term, tipo_impr_termica, 
              est_impr_local, fec_crea_impr_local, usu_crea_impr_local, 
              fec_mod_impr_local, usu_mod_impr_local) */
                parametros.add(pCodGrupoCia);
                parametros.add(pCodLocal);
                parametros.add(new Integer(pSecImprLocTerm));
                parametros.add(pDescImprLocalTerm);
                parametros.add(pTipoImprTermica);
                parametros.add(pEstImprLocal);                
                parametros.add(FarmaVariables.vIdUsu);
    
                FarmaDBUtility.executeSQLStoredProcedure(null,
                                "PTOVENTA_ADMIN_IMP.IMP_P_UPDATE_IMP_TERMICA(?,?,?,?,?,?,?)",
                                parametros, false);
    
        }

	public static void cambiaEstadoImpresora(String pCodGrupoCia,
			String pCodLocal, String pSecImprLocal) throws SQLException {

		parametros = new ArrayList();
		parametros.add(pCodGrupoCia);
		parametros.add(pCodLocal);
		parametros.add(new Integer(pSecImprLocal));
    parametros.add(FarmaVariables.vIdUsu);
		FarmaDBUtility.executeSQLStoredProcedure(null,
				"PTOVENTA_ADMIN_IMP.IMP_CAMBIA_ESTADO_IMPRESORA(?,?,?,?)", parametros,
				false);
	}

	public static void getListaTipComprobante(FarmaTableModel pTableModel)
			throws SQLException {
		pTableModel.clearTable();
		parametros = new ArrayList();
		parametros.add(FarmaVariables.vCodGrupoCia);
		parametros.add(FarmaVariables.vCodLocal);

		FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
				"PTOVENTA_ADMIN_IMP.IMP_LISTA_IMPRESORAS(?,?)", parametros, false);
	}
        
        
     public static String getExistRuta(String TipComp,String Ruta,String Secimpr) throws SQLException{
         
         parametros = new ArrayList();
         parametros.add(FarmaVariables.vCodGrupoCia);
         parametros.add(FarmaVariables.vCodLocal);
         parametros.add(TipComp);
         parametros.add(Ruta);
         parametros.add(new Integer(Secimpr));
         System.out.println(parametros);
         return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_IMP.IMP_GET_RUTA_EXIST(?,?,?,?,?)",parametros);
     }
    //JMIRANDA 26/06/2009
    public static String getExistImpTermica(String descImpTerm) throws SQLException{
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(descImpTerm);
                
        System.out.println(parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_ADMIN_IMP.IMP_F_VAR2_EXIST_IMP_TERMICA(?,?,?)",parametros);
    }
       
    /**
     * @author JCORTEZ 
     * @since 08.06.09
     * Se lista las IP registradas
     * */
    public static void getListaIPs(FarmaTableModel pTableModel)throws SQLException {
            pTableModel.clearTable();
            parametros = new ArrayList();
            parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(FarmaVariables.vCodLocal);
       FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_ADMIN_IMP.IMP_LISTA_IP(?,?)", parametros,false);
    }
    
    /**
     * @author JCORTEZ 
     * @since 08.06.09
     * Se ingresa una nueva IP para la relacion maquina - Ticket
     * */
    public static void ingresaIp(String IP) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(IP);
        FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_ADMIN_IMP.IMP_INGRESA_IP(?,?,?,?)",parametros, false);
    }
    
    /**
     * @author JCORTEZ 
     * @since 08.06.09
     * Se elimina IP para la relacion maquina - Ticket
     * */
    public static void eliminaIP(String Sec,String IP ) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(Sec);
        parametros.add(IP);
        FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_ADMIN_IMP.IMP_ELIMINA_IP(?,?,?,?,?)",parametros, false);
    }
    
    /**
     * @author JCORTEZ 
     * @since 08.06.09
     * Se lista las impresoras disponibles
     * */
    public static void getListaImpr(FarmaTableModel pTableModel,String SecIP)throws SQLException {
            pTableModel.clearTable();
            parametros = new ArrayList();
            parametros.add(FarmaVariables.vCodGrupoCia);
            parametros.add(FarmaVariables.vCodLocal);
            parametros.add(SecIP.trim());
        System.out.println("parametros -->"+parametros);
       FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_ADMIN_IMP.IMP_LISTA_IMP(?,?,?)", parametros,false);
       System.out.println("pTableModel -->"+pTableModel);
    }
    
    
    /**
     * @author JCORTEZ 
     * @since 08.06.09
     * Se actualiza la relacion maquina - Ticket
     * */
    public static void actualizaRelacion(String SecIp,String SecImpr,String NumSer,String TipComp) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(SecIp.trim());
        parametros.add(SecImpr.trim());
        parametros.add(NumSer.trim());
        parametros.add(TipComp.trim());
        System.out.println("Parametros -->"+parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_ADMIN_IMP.IMP_ACTUALIZA_IP(?,?,?,?,?,?,?)",parametros, false);

    }
    
    
    /**
     * @author JCORTEZ 
     * @since 08.06.09
     * Se elimina la relacion maquina - Ticket
     * */
    public static void quitarAsignacion(String SecIp) throws SQLException {

        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(SecIp.trim());
        System.out.println("Parametros -->"+parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_ADMIN_IMP.IMP_QUITAR_IMPR(?,?,?,?)",parametros, false);

    }
    
    /**
     * @author JCHAVEZ 
     * @since 25.06.09
     * Se lista las impresoras térmicas disponibles
     * */
    public static void getListaImpresoraTermica(FarmaTableModel pTableModel,String pSecIP) throws SQLException {
       pTableModel.clearTable();
       parametros = new ArrayList();
       parametros.add(FarmaVariables.vCodGrupoCia);
       parametros.add(FarmaVariables.vCodLocal);
       parametros.add(pSecIP.trim());    
       System.out.println("parametros -->"+parametros);
       FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_ADMIN_IMP.IMP_F_CUR_LISTA_IMP_TERMICA(?,?,?)", parametros,false);
       System.out.println("pTableModel -->"+pTableModel);
    }
    
    /**
     * @author JCHAVEZ 
     * @since 25.06.09
     * Se lista las impresoras térmicas disponibles
     * */
    public static void  actualizaRelacionIPImprTermica(String pSecIP, String pSecImpTerm ) throws SQLException{
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pSecIP.trim());
        parametros.add(pSecImpTerm.trim());
        System.out.println("ACTUALIZA RELACION IPxIMPTermica PTOVENTA_ADMIN_IMP.IMP_P_UPDATE_IP_IMP_TERMICA(?,?,?,?,?): "+parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_ADMIN_IMP.IMP_P_UPDATE_IP_IMP_TERMICA(?,?,?,?,?)",parametros, false);            
                
    }
}