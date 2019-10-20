package com.supermarket.shingshing.models;

import java.util.ArrayList;

public class CatalogoTiendaModel {
    private int code;
    private String message;
    private ArrayList<TiendaModel> tiendas;

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

    public ArrayList<TiendaModel> getTiendas() {
        return tiendas;
    }

    public void setTiendas(ArrayList<TiendaModel> tiendas) {
        this.tiendas = tiendas;
    }
}
