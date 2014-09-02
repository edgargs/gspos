package com.gs.pos.configuracion.test;

import com.gs.pos.configuracion.control.FacadeConfiguracion;

public class TestConfiguracion {
    public TestConfiguracion() {
        super();
    }
    
    public static void main(String[] args){
        FacadeConfiguracion facade = new FacadeConfiguracion();
        try {
            if(facade.validaLogin("ADMIN", "ADMIN")){
                System.out.println("OK");
            }else{
                System.err.println("ERROR");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
