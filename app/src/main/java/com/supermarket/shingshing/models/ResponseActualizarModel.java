package com.supermarket.shingshing.models;

public class ResponseActualizarModel {
    private String message;
    private int code;
    private int id;
    private UsuarioModel usuario;

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public int getId() {
        return id;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }
}
