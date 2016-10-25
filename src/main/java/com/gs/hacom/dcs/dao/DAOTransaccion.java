package com.gs.hacom.dcs.dao;

public interface DAOTransaccion {
    
    public void commit();
    
    public void rollback();
    
    public void openConnection();
}
