package com.supermarket.shingshing.models;

import android.os.Parcel;
import android.os.Parcelable;

public class MedioBonificacionModel implements Parcelable {
    private int idMediosBonificacion;
    private String aliasMedioBonificacion;
    private String cuentaMedioBonificacion;
    private String companiaMedioBonificacion;
    private String vigenciaMedioBonificacion;
    private String idCuentaMedioBonificacion;
    private int idTipo;
    private boolean estatus;
    private String banco;
    private CatalogoMediosBonificacion catalogoMediosBonificacion;
    private UsuarioModel usuario;

    public int getIdMediosBonificacion() {
        return idMediosBonificacion;
    }

    public String getAliasMedioBonificacion() {
        return aliasMedioBonificacion;
    }

    public String getCuentaMedioBonificacion() {
        return cuentaMedioBonificacion;
    }

    public String getCompaniaMedioBonificacion() {
        return companiaMedioBonificacion;
    }

    public String getVigenciaMedioBonificacion() {
        return vigenciaMedioBonificacion;
    }

    public String getIdCuentaMedioBonificacion() {
        return idCuentaMedioBonificacion;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public boolean isEstatus() {
        return estatus;
    }

    public String getBanco() {
        return banco;
    }

    public CatalogoMediosBonificacion getCatalogoMediosBonificacion() {
        return catalogoMediosBonificacion;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    protected MedioBonificacionModel(Parcel in) {
        idMediosBonificacion = in.readInt();
        aliasMedioBonificacion = in.readString();
        cuentaMedioBonificacion = in.readString();
        companiaMedioBonificacion = in.readString();
        vigenciaMedioBonificacion = in.readString();
        idCuentaMedioBonificacion = in.readString();
        idTipo = in.readInt();
        estatus = in.readByte() != 0;
        banco = in.readString();
        catalogoMediosBonificacion = in.readParcelable(CatalogoMediosBonificacion.class.getClassLoader());
        usuario = in.readParcelable(UsuarioModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idMediosBonificacion);
        dest.writeString(aliasMedioBonificacion);
        dest.writeString(cuentaMedioBonificacion);
        dest.writeString(companiaMedioBonificacion);
        dest.writeString(vigenciaMedioBonificacion);
        dest.writeString(idCuentaMedioBonificacion);
        dest.writeInt(idTipo);
        dest.writeByte((byte) (estatus ? 1 : 0));
        dest.writeString(banco);
        dest.writeParcelable(catalogoMediosBonificacion, flags);
        dest.writeParcelable(usuario, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MedioBonificacionModel> CREATOR = new Creator<MedioBonificacionModel>() {
        @Override
        public MedioBonificacionModel createFromParcel(Parcel in) {
            return new MedioBonificacionModel(in);
        }

        @Override
        public MedioBonificacionModel[] newArray(int size) {
            return new MedioBonificacionModel[size];
        }
    };

    @Override
    public String toString() {
        return "MedioBonificacionModel{" +
                "idMediosBonificacion=" + idMediosBonificacion +
                ", aliasMedioBonificacion='" + aliasMedioBonificacion + '\'' +
                ", cuentaMedioBonificacion='" + cuentaMedioBonificacion + '\'' +
                ", companiaMedioBonificacion='" + companiaMedioBonificacion + '\'' +
                ", vigenciaMedioBonificacion='" + vigenciaMedioBonificacion + '\'' +
                ", idCuentaMedioBonificacion='" + idCuentaMedioBonificacion + '\'' +
                ", idTipo=" + idTipo +
                ", estatus=" + estatus +
                ", banco='" + banco + '\'' +
                ", catalogoMediosBonificacion=" + catalogoMediosBonificacion +
                ", usuario=" + usuario +
                '}';
    }
}
