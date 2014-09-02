package com.gs.pos.configuracion.domain;

public class Usuario {
    
    private int id;
    private String login;
    private String clave;
    
    public Usuario() {
        super();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getClave() {
        return clave;
    }
}
