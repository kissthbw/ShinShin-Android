package com.supermarket.shingshing.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CatalogoMediosBonificacion implements Parcelable {
    private int idCatalogoMedioBonificacion;
    private String nombreMedioBonificacion;

    public int getIdCatalogoMedioBonificacion() {
        return idCatalogoMedioBonificacion;
    }

    public String getNombreMedioBonificacion() {
        return nombreMedioBonificacion;
    }

    protected CatalogoMediosBonificacion(Parcel in) {
        idCatalogoMedioBonificacion = in.readInt();
        nombreMedioBonificacion = in.readString();
    }

    public static final Creator<CatalogoMediosBonificacion> CREATOR = new Creator<CatalogoMediosBonificacion>() {
        @Override
        public CatalogoMediosBonificacion createFromParcel(Parcel in) {
            return new CatalogoMediosBonificacion(in);
        }

        @Override
        public CatalogoMediosBonificacion[] newArray(int size) {
            return new CatalogoMediosBonificacion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idCatalogoMedioBonificacion);
        dest.writeString(nombreMedioBonificacion);
    }

    @Override
    public String toString() {
        return "CatalogoMediosBonificacion{" +
                "idCatalogoMedioBonificacion=" + idCatalogoMedioBonificacion +
                ", nombreMedioBonificacion='" + nombreMedioBonificacion + '\'' +
                '}';
    }
}
