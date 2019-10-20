package com.supermarket.shingshing.models;

import java.util.ArrayList;

public class CatalogoTipoProductoModel {
    private int code;
    private String message;
    private ArrayList<TipoProductoModel> tipoProductos;

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

    public ArrayList<TipoProductoModel> getTipoProductos() {
        return tipoProductos;
    }

    public void setTipoProductos(ArrayList<TipoProductoModel> tipoProductos) {
        this.tipoProductos = tipoProductos;
    }
}
