package com.supermarket.shingshing.models;

import java.util.ArrayList;

public class ResponseHistorialRetiroModel {
    private String message;
    private int code;
    private ArrayList<HistorialBonificacionModel> historicoMediosBonificaciones;

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public ArrayList<HistorialBonificacionModel> getHistoricoMediosBonificaciones() {
        return historicoMediosBonificaciones;
    }
}
