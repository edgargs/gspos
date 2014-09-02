package com.gs.pos.configuracion.test;

import com.gs.pos.configuracion.control.FacadeConfiguracion;

import org.junit.Assert;
import org.junit.Test;

public class TestConfiguracion {
    public TestConfiguracion() {
        super();
    }
    
    @Test
    public void testValidaLogin() throws Exception {
        FacadeConfiguracion facade = new FacadeConfiguracion();
        
        Assert.assertTrue(facade.validaLogin("ADMIN", "ADMIN"));        
    }
}
