package com.supermarket.shingshing.models;

import java.util.ArrayList;

public class ResponseDetalleTicketModel {
    private String message;
    private int code;
    private ArrayList<ProductoModel> productos;

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public ArrayList<ProductoModel> getProductos() {
        return productos;
    }

    @Override
    public String toString() {
        return "ResponseDetalleTicketModel{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", productos=" + productos +
                '}';
    }
}
