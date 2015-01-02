package com.gs.mifarma;

import com.gs.encripta.FarmaEncripta;

public class Utilitario {
    public Utilitario() {
        super();
    }
    
    public static void main(String[] args){
        String desencriptado = FarmaEncripta.desencripta("ZGVsaXZlcnk=");                
        System.out.println(desencriptado);
    }
}
