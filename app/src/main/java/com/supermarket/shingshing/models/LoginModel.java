package com.supermarket.shingshing.models;

public class LoginModel {
    public static final int TIPO_CORREO = 1;
    public static final int TIPO_FACEBOOK = 2;
    public static final int TIPO_GOOGLE = 3;
    private int tipo;

    public LoginModel(int tipo) {
        this.tipo = tipo;
    }

    public int getTipo() {
        return tipo;
    }
}
