package com.supermarket.shingshing.models;

public class TipoBancariaModel {
    private int idTipo;
    private String descripcionBancaria;

    public int getIdTipo() {
        return idTipo;
    }

    public String getDescripcionBancaria() {
        return descripcionBancaria;
    }

    @Override
    public String toString() {
        return "TipoBancariaModel{" +
                "idTipo=" + idTipo +
                ", descripcionBancaria='" + descripcionBancaria + '\'' +
                '}';
    }
}
