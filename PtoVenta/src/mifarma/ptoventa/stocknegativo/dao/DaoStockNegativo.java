package mifarma.ptoventa.stocknegativo.dao;

import java.sql.SQLException;

import java.util.ArrayList;

import mifarma.ptoventa.reference.DAOTransaccion;


public interface DaoStockNegativo extends DAOTransaccion {
    public ArrayList<ArrayList<String>> listarSolicitudes(String numSolic, String estado, String solicitante,
                                                          String aprobador, String fechaIni,
                                                          String fechaFin) throws SQLException;

    public ArrayList<ArrayList<String>> listarDetSolicit(String numSolic) throws SQLException;

    public String regularizar(String codProd, String codProdReg, String cant, String numSol) throws SQLException;

    public ArrayList<ArrayList<String>> listarKardex(String codProd) throws SQLException;
}
