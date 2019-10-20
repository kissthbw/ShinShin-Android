package com.supermarket.shingshing.models;

import android.os.Parcel;
import android.os.Parcelable;

public class TiendaModel implements Parcelable {
    private int idCatalogoTienda;
    private String nombreTienda;
    private String imagenTienda;

    public TiendaModel() {}

    public int getIdCatalogoTienda() {
        return idCatalogoTienda;
    }

    public void setIdCatalogoTienda(int idCatalogoTienda) {
        this.idCatalogoTienda = idCatalogoTienda;
    }

    public String getNombreTienda() {
        return nombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
    }

    public String getImagenTienda() {
        return imagenTienda;
    }

    public void setImagenTienda(String imagenTienda) {
        this.imagenTienda = imagenTienda;
    }

    protected TiendaModel(Parcel in) {
        idCatalogoTienda = in.readInt();
        nombreTienda = in.readString();
        imagenTienda = in.readString();
    }

    public static final Creator<TiendaModel> CREATOR = new Creator<TiendaModel>() {
        @Override
        public TiendaModel createFromParcel(Parcel in) {
            return new TiendaModel(in);
        }

        @Override
        public TiendaModel[] newArray(int size) {
            return new TiendaModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idCatalogoTienda);
        dest.writeString(nombreTienda);
        dest.writeString(imagenTienda);
    }

    @Override
    public String toString() {
        return "TiendaModel{" +
                "idCatalogoTienda=" + idCatalogoTienda +
                ", nombreTienda='" + nombreTienda + '\'' +
                ", imagenTienda='" + imagenTienda + '\'' +
                '}';
    }
}
