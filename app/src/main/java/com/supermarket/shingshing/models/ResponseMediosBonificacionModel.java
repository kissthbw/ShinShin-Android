package com.supermarket.shingshing.models;

import java.util.ArrayList;

public class ResponseMediosBonificacionModel {
    private ArrayList<ItemMedioBonificacionModel> mediosBonificacion;

    public ArrayList<ItemMedioBonificacionModel> getMediosBonificacion() {
        return mediosBonificacion;
    }

    @Override
    public String toString() {
        return "ResponseMediosBonificacionModel{" +
                "mediosBonificacion=" + mediosBonificacion +
                '}';
    }
}
