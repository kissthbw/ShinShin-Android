package com.supermarket.shingshing.models;

import java.util.ArrayList;

public class ItemMedioBonificacionModel {
    private String nombreMedioBonificacion;
    private ArrayList<MedioBonificacionModel> list;

    public String getNombreMedioBonificacion() {
        return nombreMedioBonificacion;
    }

    public ArrayList<MedioBonificacionModel> getList() {
        return list;
    }

    @Override
    public String toString() {
        return "ItemMedioBonificacionModel{" +
                "nombreMedioBonificacion='" + nombreMedioBonificacion + '\'' +
                ", list=" + list +
                '}';
    }
}
