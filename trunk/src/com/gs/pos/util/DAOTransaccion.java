package com.gs.pos.util;

public interface DAOTransaccion {
    
    public void openConnection() throws Exception;
    
    public void commit() throws Exception;
    
    public void rollback() throws Exception;   
    
}
