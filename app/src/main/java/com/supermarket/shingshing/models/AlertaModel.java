package com.supermarket.shingshing.models;

public class AlertaModel {
    private String descripcion;
    private String precio;
    private String codigo;
    private int tipo;

    public AlertaModel(String descripcion, int tipo) {
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPrecio() {
        return "– Gana $5";
    }

    public String getCodigo() {
        return "XX / XX / XXXX";
    }

    public int getTipo() {
        return tipo;
    }
}
