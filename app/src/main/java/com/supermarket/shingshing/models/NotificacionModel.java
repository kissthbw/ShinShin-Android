package com.supermarket.shingshing.models;

public class NotificacionModel {
    private String titulo;
    private AlertaModel alerta;

    public NotificacionModel(String titulo) {
        this.titulo = titulo;
        this.alerta = null;
    }

    public NotificacionModel(AlertaModel alerta) {
        this.alerta = alerta;
        this.titulo = null;
    }

    public String getTitulo() {
        return titulo;
    }

    public AlertaModel getAlerta() {
        return alerta;
    }
}
