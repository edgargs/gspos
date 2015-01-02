package com.gs.mifarma;

import com.gs.encripta.FarmaEncripta;

public class Utilitario {
    public Utilitario() {
        super();
    }

    public static void main(String[] args) {
        String desencriptado = FarmaEncripta.desencripta("ZnVua2VyYTc0MQ==");
        System.out.println(desencriptado);
    }
}
