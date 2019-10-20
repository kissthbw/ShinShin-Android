package com.supermarket.shingshing.models;

import android.os.Parcel;
import android.os.Parcelable;

public class TipoProductoModel implements Parcelable {
    private int idCatalogoTipoProducto;
    private String nombreTipoProducto;

    public TipoProductoModel() {}

    public int getIdCatalogoTipoProducto() {
        return idCatalogoTipoProducto;
    }

    public void setIdCatalogoTipoProducto(int idCatalogoTipoProducto) {
        this.idCatalogoTipoProducto = idCatalogoTipoProducto;
    }

    public String getNombreTipoProducto() {
        return nombreTipoProducto;
    }

    public void setNombreTipoProducto(String nombreTipoProducto) {
        this.nombreTipoProducto = nombreTipoProducto;
    }

    public TipoProductoModel(Parcel in) {
        idCatalogoTipoProducto = in.readInt();
        nombreTipoProducto = in.readString();
    }

    public static final Creator<TipoProductoModel> CREATOR = new Creator<TipoProductoModel>() {
        @Override
        public TipoProductoModel createFromParcel(Parcel in) {
            return new TipoProductoModel(in);
        }

        @Override
        public TipoProductoModel[] newArray(int size) {
            return new TipoProductoModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idCatalogoTipoProducto);
        dest.writeString(nombreTipoProducto);
    }

    @Override
    public String toString() {
        return "TipoProductoModel{" +
                "idCatalogoTipoProducto=" + idCatalogoTipoProducto +
                ", nombreTipoProducto='" + nombreTipoProducto + '\'' +
                '}';
    }
}
