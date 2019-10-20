package com.supermarket.shingshing.models;

import java.util.ArrayList;

public class AyudaModel {
    private String titulo;
    private ArrayList<String> subtitulos;

    public AyudaModel(String titulo, ArrayList<String> subtitulos) {
        this.titulo = titulo;
        this.subtitulos = subtitulos;
    }

    public String getTitulo() {
        return titulo;
    }

    public ArrayList<String> getSubtitulos() {
        return subtitulos;
    }
}
