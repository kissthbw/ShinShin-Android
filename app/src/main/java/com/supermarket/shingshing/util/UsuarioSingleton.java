package com.supermarket.shingshing.util;

import com.supermarket.shingshing.models.UsuarioModel;

public class UsuarioSingleton {
    private static volatile UsuarioSingleton instancia;
    private UsuarioModel usuario;

    public UsuarioSingleton(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public synchronized static UsuarioModel getInstance(UsuarioModel usuario) {
        if (instancia != null) {
            destroyUsuario();
        }
        instancia = new UsuarioSingleton(usuario);

        return instancia.usuario;
    }

    public static UsuarioModel getUsuario() {
        return instancia != null ? instancia.usuario : null;
    }

    public static void destroyUsuario() {
        instancia = null;
    }
}
