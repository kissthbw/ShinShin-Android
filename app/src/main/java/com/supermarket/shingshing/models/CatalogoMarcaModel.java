package com.supermarket.shingshing.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CatalogoMarcaModel implements Parcelable {
    private int idCatalogoMarca;
    private String nombreMarca;

    public CatalogoMarcaModel() {}

    public int getIdCatalogoMarca() {
        return idCatalogoMarca;
    }

    public void setIdCatalogoMarca(int idCatalogoMarca) {
        this.idCatalogoMarca = idCatalogoMarca;
    }

    public String getNombreMarca() {
        return nombreMarca;
    }

    public void setNombreMarca(String nombreMarca) {
        this.nombreMarca = nombreMarca;
    }

    public CatalogoMarcaModel(Parcel in) {
        idCatalogoMarca = in.readInt();
        nombreMarca = in.readString();
    }

    public static final Creator<CatalogoMarcaModel> CREATOR = new Creator<CatalogoMarcaModel>() {
        @Override
        public CatalogoMarcaModel createFromParcel(Parcel in) {
            return new CatalogoMarcaModel(in);
        }

        @Override
        public CatalogoMarcaModel[] newArray(int size) {
            return new CatalogoMarcaModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idCatalogoMarca);
        dest.writeString(nombreMarca);
    }

    @Override
    public String toString() {
        return "CatalogoMarcaModel{" +
                "idCatalogoMarca=" + idCatalogoMarca +
                ", nombreMarca='" + nombreMarca + '\'' +
                '}';
    }
}
