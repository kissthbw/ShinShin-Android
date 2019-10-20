package com.supermarket.shingshing.models;

import java.util.ArrayList;

public class ResponseTipoBancaria {
    private String message;
    private int code;
    private ArrayList<TipoBancariaModel> tiposBancarias;

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public ArrayList<TipoBancariaModel> getTiposBancarias() {
        return tiposBancarias;
    }

    @Override
    public String toString() {
        return "ResponseTipoBancaria{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", tiposBancarias=" + tiposBancarias +
                '}';
    }
}
