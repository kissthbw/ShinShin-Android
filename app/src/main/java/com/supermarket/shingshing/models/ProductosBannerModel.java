package com.supermarket.shingshing.models;

import java.util.ArrayList;

public class ProductosBannerModel {
    private int code;
    private String message;
    private ArrayList<ProductoModel> productos;

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

    public ArrayList<ProductoModel> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<ProductoModel> productos) {
        this.productos = productos;
    }
}
