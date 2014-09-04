package com.gs.pos.util;

import com.gs.pos.configuracion.test.TestConfiguracion;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
   TestConfiguracion.class
})
public class AllTests {
    public AllTests() {
        super();
    }
}
