package com.supermarket.shingshing.models;

public class ResponseLoginModel {
    private int id;
    private int code;
    private String message;
    private int bonificacion;
    private UsuarioModel usuario;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getBonificacion() {
        return bonificacion;
    }

    public void setBonificacion(int bonificacion) {
        this.bonificacion = bonificacion;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "ResponseLoginModel{" +
                "id=" + id +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", bonificacion=" + bonificacion +
                ", usuario=" + usuario +
                '}';
    }
}
